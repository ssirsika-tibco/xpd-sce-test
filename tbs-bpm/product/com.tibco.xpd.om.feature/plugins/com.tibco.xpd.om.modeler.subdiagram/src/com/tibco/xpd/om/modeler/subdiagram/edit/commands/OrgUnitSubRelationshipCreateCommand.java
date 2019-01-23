package com.tibco.xpd.om.modeler.subdiagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;

import com.tibco.xpd.om.core.om.DynamicOrgUnit;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitFeature;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.core.om.OrgUnitRelationshipType;
import com.tibco.xpd.om.core.om.OrgUnitType;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.util.OMUtil;
import com.tibco.xpd.om.modeler.subdiagram.edit.policies.OrganizationModelBaseItemSemanticEditPolicy;
import com.tibco.xpd.om.modeler.subdiagram.part.custom.IOrganizationModelDiagramConstants;
import com.tibco.xpd.om.modeler.subdiagram.providers.OrganizationModelElementTypes;

/**
 * @generated
 */
public class OrgUnitSubRelationshipCreateCommand extends CreateElementCommand {

    boolean isHierarchical = true;

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
    private Organization container;

    private OrgUnitRelationshipType relType;

    /**
     * @generated NOT
     */
    public OrgUnitSubRelationshipCreateCommand(
            CreateRelationshipRequest request, EObject source, EObject target) {

        super(request);

        Object parameter =
                request.getParameter(IOrganizationModelDiagramConstants.OMCreationToolOrgElementType);

        if (parameter instanceof OrgUnitRelationshipType) {
            relType = (OrgUnitRelationshipType) parameter;
        }

        String str =
                IOrganizationModelDiagramConstants.OMCreationToolIsHierarchicalRel; //$NON-NLS-1$

        if (request.getParameter(str) != null) {
            this.isHierarchical = (Boolean) request.getParameter(str);
        }

        this.source = source;
        this.target = target;
        if (request.getContainmentFeature() == null) {
            setContainmentFeature(OMPackage.eINSTANCE
                    .getOrganization_OrgUnitRelationships());
        }

        // Find container element for the new link.
        // Climb up by containment hierarchy starting from the source
        // and return the first element that is instance of the container class.
        for (EObject element = source; element != null; element =
                element.eContainer()) {
            if (element instanceof Organization) {
                container = (Organization) element;
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
        if (source != null && false == source instanceof OrgUnit) {
            return false;
        }
        if (target != null && false == target instanceof OrgUnit) {
            return false;
        }

        // Out-going connections from a dynamic orgUnit is not allowed
        if (isHierarchical && source instanceof DynamicOrgUnit) {
            return false;
        }

        // If the target has a feature set then it cannot be the target of this
        // relationship
        if (isHierarchical && target instanceof OrgUnit) {
            OrgUnitFeature tFeature = ((OrgUnit) target).getFeature();
            OrgUnitType sType = ((OrgUnit) source).getOrgUnitType();
            if (tFeature != null
                    && (sType != null && !sType.getOrgUnitFeatures()
                            .contains(tFeature))) {
                return false;
            }
        }

        if (getSource() == null) {
            return true; // link creation is in progress; source is not defined
            // yet
        }
        // target may be null here but it's possible to check constraint
        if (getContainer() == null) {
            return false;
        }
        return OrganizationModelBaseItemSemanticEditPolicy.LinkConstraints
                .canCreateOrgUnitRelationship_3001(getContainer(),
                        getSource(),
                        getTarget(),
                        isHierarchical);
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand#
     * doDefaultElementCreation()
     */
    @Override
    protected EObject doDefaultElementCreation() {
        OrgUnitRelationship newElement =
                (OrgUnitRelationship) doDefaultElementCreationGen();
        newElement.setIsHierarchical(isHierarchical);
        newElement.setDisplayName(OMUtil
                .createOrgUnitRelationshipName(newElement, true));

        if (relType != null) {
            newElement.setType(relType);
        }

        return newElement;
    }

    /**
     * @generated
     */
    protected EObject doDefaultElementCreationGen() {
        OrgUnitRelationship newElement =
                OMFactory.eINSTANCE.createOrgUnitRelationship();
        getContainer().getOrgUnitRelationships().add(newElement);
        newElement.setFrom(getSource());
        newElement.setTo(getTarget());
        OrganizationModelElementTypes.init_OrgUnitRelationship_3001(newElement);
        return newElement;
    }

    /**
     * @generated
     */
    @Override
    protected EClass getEClassToEdit() {
        return OMPackage.eINSTANCE.getOrganization();
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
    protected OrgUnit getSource() {
        return (OrgUnit) source;
    }

    /**
     * @generated
     */
    protected OrgUnit getTarget() {
        return (OrgUnit) target;
    }

    /**
     * @generated
     */
    public Organization getContainer() {
        return container;
    }
}
