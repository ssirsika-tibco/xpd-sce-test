/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.diagram.edit.policies.UMLBaseItemSemanticEditPolicy;
import com.tibco.xpd.bom.modeler.diagram.part.Messages;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.types.api.BomTypesUtil;

/**
 * @generated
 */
public class AssociationCreateCommand extends CreateElementCommand {

    private Stereotype stereo;

    /**
     * @generated
     */
    private final EObject source;

    /**
     * @generated
     */
    private final EObject target;

    /**
     * @generated
     */
    private Package container;

    /**
     * @generated NOT
     */
    public AssociationCreateCommand(CreateRelationshipRequest request,
            EObject source, EObject target) {
        super(request);

        stereo =
                (Stereotype) request
                        .getParameter(BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.STEREOTYPE);

        this.source = source;
        this.target = target;
        if (request.getContainmentFeature() == null) {
            setContainmentFeature(UMLPackage.eINSTANCE
                    .getPackage_PackagedElement());
        }

        // Find container element for the new link.
        // Climb up by containment hierarchy starting from the source
        // and return the first element that is instance of the container class.
        for (EObject element = source; element != null; element =
                element.eContainer()) {
            if (element instanceof Package) {
                container = (Package) element;
                super.setElementToEdit(container);
                break;
            }
        }
    }

    /**
     * @generated NOT
     */
    @Override
    public boolean canExecute() {
        if (source == null && target == null) {
            return false;
        }
        if (source != null && (source instanceof PrimitiveType)) {
            return false;
        }
        if (target != null && (target instanceof PrimitiveType)) {
            return false;
        }
        if (source != null && (source instanceof Enumeration)) {
            return false;
        }
        if (target != null && (target instanceof Enumeration)) {
            return false;
        }

        return canExecuteGen();
    }

    /**
     * @generated
     */
    public boolean canExecuteGen() {
        if (source == null && target == null) {
            return false;
        }
        if (source != null && false == source instanceof Type) {
            return false;
        }
        if (target != null && false == target instanceof Type) {
            return false;
        }
        if (getSource() == null) {
            return true; // link creation is in progress; source is not defined
            // yet
        }
        // target may be null here but it's possible to check constraint
        if (getContainer() == null) {
            return false;
        }
        return UMLBaseItemSemanticEditPolicy.LinkConstraints
                .canCreateAssociation_3002(getContainer(),
                        getSource(),
                        getTarget());
    }

    /**
     * @generated NOT TODO- document where generated not is set
     */
    @Override
    protected EObject doDefaultElementCreation() {

        EReference containment = getContainmentFeature();
        Association newElement = null;

        if (containment != null) {
            EObject element = getElementToEdit();

            Type targetType = getTarget();
            Type sourceType = getSource();

            String sourceName;
            String targetName;

            if (targetType != sourceType) {
                sourceName = generateName(targetType, sourceType.getName());
                targetName = generateName(sourceType, targetType.getName());
            } else {
                sourceName =
                        generateName(targetType,
                                String.format(Messages.AssociationCreateCommand_source_label,
                                        sourceType.getName()));
                targetName =
                        generateName(sourceType,
                                String.format(Messages.AssociationCreateCommand_target_label,
                                        targetType.getName()));
            }

            boolean sourceNavigable = true;

            if (getRequest()
                    .getParameter(BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.SOURCE_IS_NAVIGABLE) != null) {
                sourceNavigable =
                        Boolean.parseBoolean(BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.SOURCE_IS_NAVIGABLE);
            }

            newElement =
                    targetType.createAssociation(sourceNavigable,
                            AggregationKind.NONE_LITERAL,
                            sourceName,
                            0,
                            1,
                            sourceType,
                            true,
                            AggregationKind.NONE_LITERAL,
                            targetName,
                            0,
                            1);
            /* Set default labels for association's ends. */
            BomTypesUtil.setAssociationEndDefaultLabel(newElement
                    .getMemberEnd(sourceName, getSource()), false);
            BomTypesUtil.setAssociationEndDefaultLabel(newElement
                    .getMemberEnd(targetName, getTarget()), false);
        }

        return newElement;
    }

    /**
     * Generate unique attribute name
     * 
     * @param owner
     *            owner of the attribute (uniquness scope)
     * @param baseName
     *            type of the attribute (for deriving the name)
     * @return
     */
    private String generateName(Type owner, String baseName) {

        // make the first character a lower case
        if (baseName.length() > 1 && Character.isUpperCase(baseName.charAt(0))) {
            baseName =
                    Character.toLowerCase(baseName.charAt(0))
                            + baseName.substring(1);
        }

        String name = baseName;
        if (owner instanceof Classifier) {
            EList<Property> attribs = ((Classifier) owner).getAllAttributes();
            boolean duplicate = false;
            int i = 1;
            do {
                duplicate = false;
                for (Property property : attribs) {
                    if (name.equals(property.getName())) {
                        duplicate = true;
                        break;
                    }
                }
                if (duplicate) {
                    name = baseName + i;
                    i++;
                }
            } while (duplicate);
        }
        return name;
    }

    /**
     * @generated
     */
    @Override
    protected EClass getEClassToEdit() {
        return UMLPackage.eINSTANCE.getPackage();
    }

    /**
     * @generated
     */
    @Override
    protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
            IAdaptable info) throws ExecutionException {
        if (!canExecute()) {
            throw new ExecutionException(
                    "Invalid arguments in create link command"); //$NON-NLS-1$
        }
        return super.doExecuteWithResult(monitor, info);
    }

    /**
     * @generated
     */
    @Override
    protected ConfigureRequest createConfigureRequest() {
        ConfigureRequest request = super.createConfigureRequest();
        request.setParameter(CreateRelationshipRequest.SOURCE, getSource());
        request.setParameter(CreateRelationshipRequest.TARGET, getTarget());
        return request;
    }

    /**
     * @generated
     */
    @Override
    protected void setElementToEdit(EObject element) {
        throw new UnsupportedOperationException();
    }

    /**
     * @generated
     */
    protected Type getSource() {
        return (Type) source;
    }

    /**
     * @generated
     */
    protected Type getTarget() {
        return (Type) target;
    }

    /**
     * @generated
     */
    public Package getContainer() {
        return container;
    }
}
