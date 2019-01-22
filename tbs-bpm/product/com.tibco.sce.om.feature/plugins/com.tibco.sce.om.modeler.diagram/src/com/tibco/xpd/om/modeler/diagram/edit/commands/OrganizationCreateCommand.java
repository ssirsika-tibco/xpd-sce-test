package com.tibco.xpd.om.modeler.diagram.edit.commands;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.OrganizationType;
import com.tibco.xpd.om.core.om.provider.OrgModelItemProvider;
import com.tibco.xpd.om.core.om.util.OMUtil;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrganizationEditPart;
import com.tibco.xpd.om.modeler.subdiagram.part.custom.IOrganizationModelDiagramConstants;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * @generated
 */
public class OrganizationCreateCommand extends CreateElementCommand {

    private OrganizationType orgType;

    private boolean isDynamic = false;

    /**
     * @generated NOT
     */
    public OrganizationCreateCommand(CreateElementRequest req) {
        super(req);

        Object parameter =
                req.getParameter(IOrganizationModelDiagramConstants.OMCreationToolOrgElementType);

        if (parameter instanceof OrganizationType) {
            orgType = (OrganizationType) parameter;
        }

        Object value = req.getParameter(OrganizationEditPart.DYNAMIC);
        if (value instanceof Boolean) {
            isDynamic = (Boolean) value;
        }
    }

    /**
     * @generated
     */
    @Override
    protected EObject getElementToEdit() {
        EObject container =
                ((CreateElementRequest) getRequest()).getContainer();
        if (container instanceof View) {
            container = ((View) container).getElement();
        }
        return container;
    }

    /**
     * @generated
     */
    @Override
    protected EClass getEClassToEdit() {
        return OMPackage.eINSTANCE.getOrgModel();
    }

    @Override
    protected EObject doDefaultElementCreation() {
        EObject eo = super.doDefaultElementCreation();

        if (eo instanceof Organization) {
            Organization org = (Organization) eo;
            org.setDynamic(isDynamic);
            if (orgType != null) {
                String defaultName = ""; //$NON-NLS-1$
                org.setType(orgType);
                EObject container = org.eContainer();

                if (container instanceof OrgModel) {
                    OrgModel om = (OrgModel) container;
                    defaultName =
                            OMUtil.getDefaultName(orgType.getDisplayName(),
                                    OMUtil.getDisplayNamesArray(om
                                            .getOrganizations()));
                }

                org.setDisplayName(defaultName);

            } else {

                org.setDisplayName(getUniqueOrgName(org));
            }

        }

        return eo;

    }

    private String getUniqueOrgName(Organization org) {
        String defaultName = ""; //$NON-NLS-1$
        OrgModel om = (OrgModel) org.eContainer();

        IEditingDomainItemProvider provider =
                (IEditingDomainItemProvider) XpdResourcesPlugin.getDefault()
                        .getAdapterFactory()
                        .adapt(om, IEditingDomainItemProvider.class);

        if (provider instanceof OrgModelItemProvider) {
            OrgModelItemProvider orgProvider = (OrgModelItemProvider) provider;

            // All to get this string!
            String prefix;

            if (org.isDynamic()) {
                prefix =
                        orgProvider
                                .getString("_UI_DefaultName_DynamicOrganisation_label"); //$NON-NLS-1$

            } else {
                prefix =
                        orgProvider
                                .getString("_UI_DefaultName_Organisation_label"); //$NON-NLS-1$
            }

            defaultName =
                    OMUtil.getDefaultName(prefix,
                            OMUtil.getDisplayNamesArray(om.getOrganizations()));

        }

        return defaultName;
    }

}
