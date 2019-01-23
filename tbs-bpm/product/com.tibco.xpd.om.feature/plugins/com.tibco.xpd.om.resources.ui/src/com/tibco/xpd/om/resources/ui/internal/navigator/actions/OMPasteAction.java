/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.internal.navigator.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.StrictCompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CopyCommand;
import org.eclipse.emf.edit.command.PasteFromClipboardCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.action.PasteAction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.om.core.om.CapabilityCategory;
import com.tibco.xpd.om.core.om.DynamicOrgReference;
import com.tibco.xpd.om.core.om.DynamicOrgUnit;
import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.core.om.OrgMetaModel;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgTypedElement;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitFeature;
import com.tibco.xpd.om.core.om.OrgUnitType;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.OrganizationType;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.PositionFeature;
import com.tibco.xpd.om.core.om.PositionType;
import com.tibco.xpd.om.core.om.PrivilegeCategory;
import com.tibco.xpd.om.core.om.ResourceType;
import com.tibco.xpd.om.core.om.provider.TransientItemProvider;
import com.tibco.xpd.om.resources.ui.clipboard.OMClipboardHelper;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Paste action for the OM elements in the project explorer.
 * 
 * @author njpatel
 * 
 */
public class OMPasteAction extends PasteAction {

    /**
     * OM paste action for the project explorer.
     * 
     * @param ed
     */
    public OMPasteAction(TransactionalEditingDomain ed) {
        super(ed);
    }

    @Override
    public void run() {
        // Recalculate the command as, if the selection hasn't changed then, the
        // previous command may get executed again
        command = createCommand(getStructuredSelection().toList());
        super.run();
    }

    @Override
    public Command createCommand(Collection<?> selection) {
        if (selection.size() == 1) {
            /*
             * Overridden to control the naming of the pasted elements in case
             * of name conflict (to replicate the behaviour in the OM editor).
             */
            Object owner = selection.iterator().next();
            // Cannot paste anything in the OrgModel
            if (!(owner instanceof OrgModel)) {
                return new PasteCommand(domain, owner);
            }
        }
        return UnexecutableCommand.INSTANCE;
    }

    /**
     * If the owner is a {@link TransientItemProvider} then its target will be
     * returned as the owner.
     * 
     * @param owner
     * @return
     */
    private Object getTrueOwner(Object owner) {
        if (owner instanceof TransientItemProvider) {
            owner = ((TransientItemProvider) owner).getTarget();
        } else if (owner instanceof CapabilityCategory
                || owner instanceof PrivilegeCategory) {
            /*
             * All privileges and capabilities are owned by the OrgModel and not
             * their respective category
             */
            owner = getOrgModel(((EObject) owner).eResource());
        }
        return owner;
    }

    /**
     * Get the OrganizationModel from the given resource.
     * 
     * @param resource
     * @return
     */
    private OrgModel getOrgModel(Resource resource) {
        OrgModel model = null;

        if (resource != null) {
            for (EObject content : resource.getContents()) {
                if (content instanceof OrgModel) {
                    model = (OrgModel) content;
                    break;
                }
            }
        }

        return model;
    }

    /**
     * Get the {@link OrgMetaModel} from the given resource.
     * 
     * @param resource
     * @return <code>OrgMetaModel</code> if found, <code>null</code> otherwise.
     */
    private OrgMetaModel getOrgMetaModel(Resource resource) {
        OrgModel model = getOrgModel(resource);
        if (model != null) {
            for (EObject content : model.eContents()) {
                if (content instanceof OrgMetaModel) {
                    return (OrgMetaModel) content;
                }
            }
        }
        return null;
    }

    /**
     * The OM paste command that extends the {@link PasteFromClipboardCommand}.
     * 
     * @author njpatel
     * 
     */
    private class PasteCommand extends PasteFromClipboardCommand {

        private final Object selection;

        private boolean confirmPaste = false;

        public PasteCommand(EditingDomain domain, Object owner) {
            super(domain, getTrueOwner(owner), null, CommandParameter.NO_INDEX,
                    false);
            selection = owner;
        }

        @Override
        public void doExecute() {
            boolean shouldPaste = true;

            /*
             * If pasting a typed element with a type set into an external
             * resource then we need to ask the user whether they wish to carry
             * on as the type will be removed.
             */
            if (confirmPaste) {
                shouldPaste =
                        OMClipboardHelper
                                .shouldContinuePasteIntoExternalResource(XpdResourcesPlugin
                                        .getStandardDisplay().getActiveShell());
            }

            if (shouldPaste) {
                super.doExecute();
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.edit.command.PasteFromClipboardCommand#prepare()
         */
        @Override
        protected boolean prepare() {
            // Create a strict compound command to do a copy and then
            // add the result
            command = new StrictCompoundCommand();
            confirmPaste = false;

            // Create a command to copy the clipboard.
            final Command copyCommand =
                    CopyCommand.create(domain, domain.getClipboard());
            command.append(copyCommand);

            // Create a proxy that will create an add command.
            command.append(new CommandWrapper() {
                protected Collection<Object> copy;

                @Override
                protected Command createCommand() {
                    copy = new ArrayList<Object>(copyCommand.getResult());

                    if (copy != null && owner != null) {
                        /*
                         * If the copy contains human resources then make sure
                         * the right human resource type are set
                         */
                        updateHumanResources(copy);

                        /*
                         * XPD-7211: Post paste the references to dynamic
                         * orginations were broken, hence we need to handle this
                         * special case.
                         */
                        List<DynamicOrgReference> dynamicOrgReferencesToAdd =
                                getAndFixDynamicOrgReferences(copy);

                        Resource resource = getTypeResource(copy);
                        if (resource != null && owner instanceof EObject
                                && ((EObject) owner).eResource() != resource) {
                            confirmPaste = true;
                            removeTypes(copy);
                        }

                        /*
                         * If OrgUnit or Positions are being pasted then check
                         * that the feature (if any) applied are valid for the
                         * target, if not reset the feature
                         */
                        updateFeatures(owner, copy);

                        // Update the name of the pasted elements in case
                        // there is a name conflict
                        updateName(copy);
                        EStructuralFeature feature = getFeature(owner, copy);
                        if (feature != null && canPaste(selection, feature)) {
                            CompoundCommand ccmd = new CompoundCommand();

                            Command cmd =
                                    AddCommand.create(domain,
                                            owner,
                                            feature,
                                            copy,
                                            index);

                            ccmd.append(cmd);
                            /*
                             * If this is a paste in a PrivilegeCategory or a
                             * CapabilityCategory then we need to additionally
                             * add the new Capability/Privilege as a member of
                             * the target.
                             */
                            EStructuralFeature catFeature =
                                    selection instanceof CapabilityCategory ? OMPackage.eINSTANCE
                                            .getCapabilityCategory_Members()
                                            : selection instanceof PrivilegeCategory ? OMPackage.eINSTANCE
                                                    .getPrivilegeCategory_Members()
                                                    : null;
                            if (catFeature != null) {

                                ccmd.append(AddCommand.create(domain,
                                        selection,
                                        catFeature,
                                        copy));

                            }

                            if (!dynamicOrgReferencesToAdd.isEmpty()) {
                                /*
                                 * get the org model as the dynamic org
                                 * references will be added to OrgModel
                                 */
                                OrgModel orgModel =
                                        getParentOrgModel((EObject) owner);
                                /*
                                 * get the Dynamic Org Reference feature
                                 */
                                EStructuralFeature dynamicOrgRefFeature =
                                        getFeature(orgModel,
                                                dynamicOrgReferencesToAdd);

                                if (orgModel != null) {
                                    for (DynamicOrgReference dynamicOrgReference : dynamicOrgReferencesToAdd) {
                                        /*
                                         * Add the Dynamic Org References to the
                                         * Org Model.
                                         */
                                        ccmd.append(AddCommand.create(domain,
                                                orgModel,
                                                dynamicOrgRefFeature,
                                                dynamicOrgReference));
                                    }
                                }
                            }

                            return ccmd;
                        }
                    }
                    return UnexecutableCommand.INSTANCE;
                }

                /**
                 * 
                 * @param omEntity
                 *            the om entity
                 * @return the Parent OrgModel of the passed omEntity else if
                 *         not found return <code>null</code>
                 */
                private OrgModel getParentOrgModel(EObject omEntity) {
                    if (omEntity != null) {
                        if (omEntity instanceof OrgModel) {
                            return (OrgModel) omEntity;
                        } else {
                            return getParentOrgModel(omEntity.eContainer());
                        }
                    }
                    return null;
                }

            });

            boolean result;
            if (optimize) {
                // This will determine canExecute as efficiently as
                // possible.
                result = optimizedCanExecute();
            } else {
                // This will actually execute the copy command in order
                // to check if the add can execute.
                result = command.canExecute();
            }

            return result;
        }

        /**
         * Checks if the pasted Organization has any Dynamic Org units in it, if
         * yes then creates a new 'DynamicOrgReference' and updates the
         * references in DynamicOrgUnit.
         * 
         * @param copy
         *            the copied objects.
         * 
         * @return list of 'DynamicOrgReference' that are created so that the
         *         OrgModel can add them as child elements.
         */
        private List<DynamicOrgReference> getAndFixDynamicOrgReferences(
                Collection<Object> copy) {

            List<DynamicOrgReference> dynamicOrgReferences =
                    new ArrayList<DynamicOrgReference>();

            for (Object eachCopiedElement : copy) {

                if (eachCopiedElement instanceof Organization
                        && owner instanceof OrgModel) {

                    Organization organization =
                            (Organization) eachCopiedElement;
                    EList<OrgUnit> units = organization.getUnits();

                    for (OrgUnit orgUnit : units) {

                        if (orgUnit instanceof DynamicOrgUnit) {

                            DynamicOrgReference dynamicOrgReference =
                                    getAndFixDynamicOrgReference((DynamicOrgUnit) orgUnit);

                            if (dynamicOrgReference != null) {
                                dynamicOrgReferences.add(dynamicOrgReference);
                            }
                        }
                    }
                } else if (eachCopiedElement instanceof DynamicOrgUnit
                        && owner instanceof Organization) {

                    DynamicOrgReference dynamicOrgReference =
                            getAndFixDynamicOrgReference((DynamicOrgUnit) eachCopiedElement);

                    if (dynamicOrgReference != null) {
                        dynamicOrgReferences.add(dynamicOrgReference);
                    }
                }
            }

            return dynamicOrgReferences;
        }

        /**
         * creates a new 'DynamicOrgReference' and updates the references in
         * DynamicOrgUnit
         * 
         * @param dynamicOrgUnit
         *            the dynamic org unit that is being copied.
         * @return 'DynamicOrgReference' that is created so that the OrgModel
         *         can add them as child elements.
         */
        private DynamicOrgReference getAndFixDynamicOrgReference(
                DynamicOrgUnit dynamicOrgUnit) {
            DynamicOrgReference createDynamicOrgReference = null;
            /*
             * get the Dynamic Org Reference from the Dynamic Org unit.
             */
            DynamicOrgReference dynamicOrganization =
                    dynamicOrgUnit.getDynamicOrganization();

            if (dynamicOrganization != null) {
                Organization toOrg = dynamicOrganization.getTo();

                /*
                 * Create new 'DynamicOrgReference' as we would want to add new
                 * Dynamic Reference.
                 */
                createDynamicOrgReference =
                        OMFactory.eINSTANCE.createDynamicOrgReference();

                createDynamicOrgReference.setTo(toOrg);
                createDynamicOrgReference.setFrom(dynamicOrgUnit);
                /*
                 * update the dynamic org unit reference
                 */
                dynamicOrgUnit
                        .setDynamicOrganization(createDynamicOrgReference);
            }
            return createDynamicOrgReference;
        }

        /**
         * If pasteing into a different resource then update the human resources
         * so that they are of the type defined in the target's schema.
         * 
         * @param copy
         */
        private void updateHumanResources(Collection<Object> copy) {
            if (copy != null) {
                ResourceType humanResourceType = null;
                for (Object elem : copy) {
                    if (elem instanceof com.tibco.xpd.om.core.om.Resource) {
                        com.tibco.xpd.om.core.om.Resource resource =
                                (com.tibco.xpd.om.core.om.Resource) elem;
                        OrgElementType type = resource.getType();

                        if (type instanceof ResourceType) {
                            if (humanResourceType == null) {
                                humanResourceType = getHumanResourceType();
                            }

                            if (((ResourceType) type).isHumanResourceType()
                                    && !type.equals(humanResourceType)) {
                                resource.setType(humanResourceType);
                            }
                        }
                    }
                }
            }
        }

        /**
         * Get the Human Resource Type from the target's model.
         * 
         * @return human resource type or <code>null</code> if one is not found.
         */
        private ResourceType getHumanResourceType() {
            if (owner instanceof EObject
                    && ((EObject) owner).eResource() != null) {
                OrgModel model = getOrgModel(((EObject) owner).eResource());
                if (model != null) {
                    return model.getHumanResourceType();
                }
            }
            return null;
        }

        /**
         * Update the features of any OrgUnit or Position elements being pasted:
         * If the target is not of the correct type then the feature will be
         * removed.
         * 
         * @param owner
         * @param copy
         */
        private void updateFeatures(Object owner, Collection<Object> copy) {
            // If there are no copy objects then quit here
            if (copy == null || copy.isEmpty()) {
                return;
            }

            if (owner instanceof OrgTypedElement) {
                OrgElementType containerType =
                        ((OrgTypedElement) owner).getType();
                for (Object obj : copy) {
                    if (obj instanceof OrgUnit || obj instanceof Position) {
                        if (obj instanceof OrgUnit) {
                            OrgUnitFeature feature =
                                    ((OrgUnit) obj).getFeature();

                            if (feature != null
                                    && feature.eContainer() != containerType) {
                                resetFeature((OrgUnit) obj);
                            }
                        } else /* position */{
                            PositionFeature feature =
                                    ((Position) obj).getFeature();
                            if (feature != null
                                    && feature.eContainer() != containerType) {
                                resetFeature((Position) obj);
                            }
                        }
                    }
                }
            } else if (owner instanceof OrganizationType
                    || owner instanceof OrgUnitType) {
                Resource targetResource = ((EObject) owner).eResource();

                if (targetResource != null) {
                    OrgMetaModel metaModel = getOrgMetaModel(targetResource);

                    /*
                     * If the copied object has a type and this type is not from
                     * the target resource then re-assign the type to the
                     * corresponding type in the target resource. If there isn't
                     * one then set it to null.
                     */
                    for (Object obj : copy) {
                        if (obj instanceof OrgUnitFeature) {
                            OrgUnitFeature orgUnitFeature =
                                    (OrgUnitFeature) obj;
                            OrgUnitType type = orgUnitFeature.getFeatureType();

                            if (type != null
                                    && !targetResource.equals(type.eResource())) {
                                OrgUnitType localType = null;

                                if (metaModel != null) {
                                    for (OrgUnitType ouType : metaModel
                                            .getOrgUnitTypes()) {
                                        if (type.getName()
                                                .equals(ouType.getName())) {
                                            localType = ouType;
                                            break;
                                        }
                                    }
                                }

                                orgUnitFeature.setFeatureType(localType);
                            }

                        } else if (obj instanceof PositionFeature) {
                            PositionFeature posFeature = (PositionFeature) obj;
                            PositionType type = posFeature.getFeatureType();

                            if (type != null
                                    && !targetResource.equals(type.eResource())) {
                                PositionType localType = null;

                                if (metaModel != null) {
                                    for (PositionType posType : metaModel
                                            .getPositionTypes()) {
                                        if (type.getName()
                                                .equals(posType.getName())) {
                                            localType = posType;
                                            break;
                                        }
                                    }
                                }

                                posFeature.setFeatureType(localType);
                            }
                        }
                    }
                }
            }
        }

        /**
         * Reset the feature of the given {@link OrgUnit}. This will also reset
         * features of its children.
         * 
         * @param unit
         */
        private void resetFeature(OrgUnit unit) {
            if (unit != null) {
                unit.setFeature(null);
                EList<Position> positions = unit.getPositions();
                if (positions != null) {
                    for (Position position : positions) {
                        resetFeature(position);
                    }
                }
                EList<OrgUnit> units = unit.getSubUnits();
                if (units != null) {
                    for (OrgUnit orgUnit : units) {
                        resetFeature(orgUnit);
                    }
                }
            }
        }

        /**
         * Reset the feature of the given {@link Position}.
         * 
         * @param position
         */
        private void resetFeature(Position position) {
            if (position != null) {
                position.setFeature(null);
            }
        }

        /**
         * Remove {@link OrgElementType types} of {@link OrgTypedElement} pasted
         * elements - this will happen if paste has happened in another resource
         * (types are part of internal schema).
         * 
         * @param copy
         */
        private void removeTypes(Collection<Object> copy) {
            if (copy != null) {
                for (Object obj : copy) {
                    if (obj instanceof OrgTypedElement
                            && !(obj instanceof OrgUnit || obj instanceof Position)) {
                        ((OrgTypedElement) obj).setType(null);
                    }
                }
            }
        }

        /**
         * Check if the objects being copied have any OrgTypedElements with a
         * type set. If it has then get its resource.
         * 
         * @param copy
         * @return {@link Resource} or <code>null</code> if no typed elements
         *         found that have a type set.
         */
        private Resource getTypeResource(Collection<Object> copy) {
            if (copy != null) {
                for (Object obj : copy) {
                    if (obj instanceof OrgTypedElement) {
                        OrgElementType type = ((OrgTypedElement) obj).getType();
                        if (type != null) {
                            return type.eResource();
                        }
                    }
                }
            }
            return null;
        }

        private boolean canPaste(Object selection, EStructuralFeature feature) {
            if (selection instanceof TransientItemProvider) {
                TransientItemProvider provider =
                        (TransientItemProvider) selection;
                Collection<?> descriptors =
                        provider.getNewChildDescriptors(provider.getTarget(),
                                getEditingDomain(),
                                null);

                if (descriptors != null) {
                    for (Object object : descriptors) {
                        if (object instanceof CommandParameter
                                && feature == ((CommandParameter) object)
                                        .getFeature()) {
                            return true;
                        }
                    }
                }
            } else if (selection instanceof PrivilegeCategory) {
                /*
                 * If selection is PrivilegeCategory and the feature is
                 * privileges of the OrgModel then allow paste as the category
                 * is the virtual container of the privileges
                 */
                return feature == OMPackage.eINSTANCE.getOrgModel_Privileges();
            } else if (selection instanceof CapabilityCategory) {
                /*
                 * If selection is CapabilityCategory and the feature is
                 * categories of the OrgModel then allow paste as the category
                 * is the virtual container of the categories
                 */
                return feature == OMPackage.eINSTANCE
                        .getOrgModel_Capabilities();
            } else if (selection instanceof EObject) {
                return true;
            }

            return false;
        }

        /**
         * Update the names of the pasted elements if they clash with existing
         * elements.
         * 
         * @param copiedObjs
         */
        private void updateName(Collection<Object> copiedObjs) {
            if (owner instanceof EObject && copiedObjs != null
                    && !copiedObjs.isEmpty()) {
                for (Object obj : copiedObjs) {
                    if (obj instanceof NamedElement) {
                        NamedElement namedElem = (NamedElement) obj;
                        String name = getUniqueName((EObject) owner, namedElem);

                        if (name != null
                                && !name.equals(namedElem.getDisplayName())) {
                            namedElem.setDisplayName(name);
                        }
                    }
                }
            }
        }

        /**
         * Get unique name for the element being pasted.
         * 
         * @param owner
         * @param elem
         * @return
         */
        private String getUniqueName(EObject owner, NamedElement elem) {
            String name = elem.getDisplayName();

            if (owner != null && elem != null) {
                EList<EObject> contents = owner.eContents();
                if (contents != null && !contents.isEmpty()) {
                    int idx = 1;
                    boolean isUnique = false;

                    while (!isUnique) {
                        isUnique = true;
                        for (EObject content : contents) {
                            if (content instanceof NamedElement
                                    && elem.eClass() == content.eClass()) {
                                if (name.equals(((NamedElement) content)
                                        .getDisplayName())) {
                                    isUnique = false;
                                    break;
                                }
                            }
                        }

                        if (!isUnique) {
                            name =
                                    String.format(Messages.OMPasteAction_paste_nameCollision_label,
                                            idx++,
                                            elem.getDisplayName());
                        }
                    }
                }
            }

            return name;
        }

        /**
         * Get the feaure of the target to paste into.
         * 
         * @param owner
         * @param copy
         * @return
         */
        private EStructuralFeature getFeature(Object owner, Collection<?> copy) {
            if (owner instanceof EObject && copy != null && !copy.isEmpty()) {
                Object elem = copy.iterator().next();
                if (elem instanceof EObject) {
                    EList<EStructuralFeature> features =
                            ((EObject) owner).eClass()
                                    .getEAllStructuralFeatures();

                    if (features != null) {
                        EClass elemClass = ((EObject) elem).eClass();

                        for (EStructuralFeature feat : features) {
                            /*
                             * XPD-7211: Adding 'elemClass.getESuperTypes()
                             * .contains(feat.getEType()' check over here as
                             * there might be scenario where the Feature is the
                             * super type/super class of the element. e.g In
                             * case of Organization the eStructuralFeatures are
                             * 'OrgUnit', DynamicOrgIdentifiers etc etc... so
                             * Organization does not have DynamicOrgUnit as its
                             * EStructuralFeatue however 'DynamicOrgUnit' is a
                             * sub type of 'OrgUnit' and hence copy paste of
                             * Dynamic Org unit to Organization did not work.
                             */
                            if (elemClass == feat.getEType()
                                    || elemClass.getESuperTypes()
                                            .contains(feat.getEType())) {
                                return feat;
                            }
                        }
                    }
                }
            }
            return null;
        }
    }
}
