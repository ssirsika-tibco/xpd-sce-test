/**
 * 
 */
package com.tibco.xpd.om.modeler.custom.providers;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener;
import org.eclipse.gmf.runtime.common.ui.services.action.internal.contributionitem.ContributeToPopupMenuOperation;
import org.eclipse.gmf.runtime.common.ui.services.action.internal.contributionitem.IContributionItemProvider;
import org.eclipse.gmf.runtime.common.ui.util.IWorkbenchPartDescriptor;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.core.om.OrgMetaModel;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitFeature;
import com.tibco.xpd.om.core.om.OrgUnitType;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.OrganizationType;
import com.tibco.xpd.om.core.om.PositionFeature;
import com.tibco.xpd.om.core.om.provider.OMModelImages;
import com.tibco.xpd.om.modeler.custom.actions.CreateOMElementDiagramAction;
import com.tibco.xpd.om.modeler.custom.actions.OMActionIds;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgModelEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.OrgUnitSubEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.OrganizationSubEditPart;

/**
 * 
 * A contribution item provider that contributes the
 * CreateOMElementDiagramAction to the diagram context menu.
 * <P>
 * This has to be done programatically rather than through the
 * org.eclipse.gmf.runtime.common.ui.services.action.contributionItemProviders
 * extension point because the OrgUnitFeatures and PositionFeatures that need to
 * be shown on the context menu are not known until runtime.
 * <P>
 * Therefore we couldn't extend AbstractContributionItemProvider which reads XML
 * contributions. This is following the practice advised in the header comments
 * for AbstractContributionItemProvider
 * 
 * 
 * @author rgreen
 * 
 */
public class OMCustomDynamicContributionItemProvider extends AbstractProvider
        implements IContributionItemProvider {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.common.ui.services.action.internal.contributionitem
     * .
     * IContributionItemProvider#contributeToActionBars(org.eclipse.ui.IActionBars
     * , org.eclipse.gmf.runtime.common.ui.util.IWorkbenchPartDescriptor)
     */
    public void contributeToActionBars(IActionBars actionBars,
            IWorkbenchPartDescriptor workbenchPartDescriptor) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.common.ui.services.action.internal.contributionitem
     * .
     * IContributionItemProvider#contributeToPopupMenu(org.eclipse.jface.action.
     * IMenuManager, org.eclipse.ui.IWorkbenchPart)
     */
    public void contributeToPopupMenu(IMenuManager popupMenu,
            IWorkbenchPart workbenchPart) {
        ISelection selection = workbenchPart.getSite().getSelectionProvider()
                .getSelection();

        if (selection instanceof IStructuredSelection) {
            IStructuredSelection sel = (IStructuredSelection) selection;
            if (sel.size() == 1) {
                Object firstElement = sel.getFirstElement();

                if (firstElement instanceof OrganizationSubEditPart) {
                    // Need to add an Actions to create
                    // 1. A standard untyped OrgUnit
                    // 2. Any OrgUnitFeatures
                    OrganizationSubEditPart ep = (OrganizationSubEditPart) firstElement;
                    Image img = null;
                    img = ExtendedImageRegistry.INSTANCE.getImage(OMModelImages
                            .getImageObject(OMModelImages.IMAGE_ORG_UNIT));
                    ImageDescriptor imgDesc = ImageDescriptor
                            .createFromImage(img);

                    CreateOMElementDiagramAction act = new CreateOMElementDiagramAction(
                            workbenchPart.getSite().getPage(),
                            OMActionIds.ACTION_ADD_ORGUNIT, imgDesc);

                    act.init();
                    //popupMenu.appendToGroup("addGroup", act); //$NON-NLS-1$

                    EObject elem = ep.resolveSemanticElement();

                    if (elem instanceof Organization) {
                        Organization org = (Organization) elem;
                        OrgElementType type = org.getType();

                        if (type != null) {
                            // Has a type so now go and fetch all the Features
                            EList<OrgUnitFeature> orgUnitFeatures = getOrgUnitFeatures(type);

                            for (OrgUnitFeature orgUnitFeature : orgUnitFeatures) {
                                if (orgUnitFeature.getFeatureType() != null) {
                                    img = ExtendedImageRegistry.INSTANCE
                                            .getImage(OMModelImages
                                                    .getImageObject(OMModelImages.IMAGE_ORG_UNIT_FEATURE));
                                    imgDesc = ImageDescriptor
                                            .createFromImage(img);

                                    CreateOMElementDiagramAction featureAct = new CreateOMElementDiagramAction(
                                            workbenchPart.getSite().getPage(),
                                            OMActionIds.ACTION_ADD_ORGUNIT,
                                            imgDesc, orgUnitFeature);
                                    featureAct.init();
                                    //popupMenu.appendToGroup("addGroup", //$NON-NLS-1$
                                    // featureAct);
                                }
                            }

                        }

                    }

                }

                if (firstElement instanceof OrgUnitSubEditPart) {
                    // Need to add Actions to create
                    // 1. A standard untyped Position
                    // 2. Any OrgUnitFeatures (which will be subunits i.e. have
                    // a parent)
                    // 2. Any PositionFeatures
                    OrgUnitSubEditPart ep = (OrgUnitSubEditPart) firstElement;
                    Image img = null;
                    img = ExtendedImageRegistry.INSTANCE.getImage(OMModelImages
                            .getImageObject(OMModelImages.IMAGE_POSITION));
                    ImageDescriptor imgDesc = ImageDescriptor
                            .createFromImage(img);
                    CreateOMElementDiagramAction act = new CreateOMElementDiagramAction(
                            workbenchPart.getSite().getPage(),
                            OMActionIds.ACTION_ADD_POSITION, imgDesc);
                    act.init();
                    //popupMenu.appendToGroup("addGroup", act); //$NON-NLS-1$

                    EObject elem = ep.resolveSemanticElement();

                    if (elem instanceof OrgUnit) {
                        OrgUnit ou = (OrgUnit) elem;
                        OrgElementType type = ou.getType();

                        if (type != null && type instanceof OrgUnitType) {
                            OrgUnitType ouType = (OrgUnitType) type;
                            // Has a type so now go and fetch all the Features
                            // 1. OrgUnit Features
                            EList<OrgUnitFeature> orgUnitFeatures = ouType
                                    .getOrgUnitFeatures();

                            for (OrgUnitFeature orgUnitFeature : orgUnitFeatures) {
                                if (orgUnitFeature.getFeatureType() != null) {
                                    img = ExtendedImageRegistry.INSTANCE
                                            .getImage(OMModelImages
                                                    .getImageObject(OMModelImages.IMAGE_ORG_UNIT_FEATURE));
                                    imgDesc = ImageDescriptor
                                            .createFromImage(img);

                                    CreateOMElementDiagramAction featureAct = new CreateOMElementDiagramAction(
                                            workbenchPart.getSite().getPage(),
                                            OMActionIds.ACTION_ADD_ORGUNIT,
                                            imgDesc, orgUnitFeature, ou);
                                    featureAct.init();
                                    //popupMenu.appendToGroup("addGroup", //$NON-NLS-1$
                                    // featureAct);
                                }
                            }

                            // 2. Position Features
                            EList<PositionFeature> positionFeatures = ouType
                                    .getPositionFeatures();

                            for (PositionFeature posFeature : positionFeatures) {
                                img = ExtendedImageRegistry.INSTANCE
                                        .getImage(OMModelImages
                                                .getImageObject(OMModelImages.IMAGE_POSITION_FEATURE));
                                imgDesc = ImageDescriptor.createFromImage(img);

                                CreateOMElementDiagramAction featureAct = new CreateOMElementDiagramAction(
                                        workbenchPart.getSite().getPage(),
                                        OMActionIds.ACTION_ADD_POSITION,
                                        imgDesc, posFeature);
                                featureAct.init();
                                //popupMenu.appendToGroup("addGroup", featureAct); //$NON-NLS-1$
                            }

                        }
                    }

                }

                if (firstElement instanceof OrgModelEditPart) {
                    // Need to add an Actions to create
                    // 1. A standard untyped Organizations
                    // 2. Any typed Organizations
                    OrgModelEditPart ep = (OrgModelEditPart) firstElement;
                    Image img = null;
                    img = ExtendedImageRegistry.INSTANCE.getImage(OMModelImages
                            .getImageObject(OMModelImages.IMAGE_ORGANISATION));
                    ImageDescriptor imgDesc = ImageDescriptor
                            .createFromImage(img);

                    CreateOMElementDiagramAction act = new CreateOMElementDiagramAction(
                            workbenchPart.getSite().getPage(),
                            OMActionIds.ACTION_ADD_ORGANIZATION, imgDesc);

                    act.init();
                    popupMenu.appendToGroup("addGroup", act); //$NON-NLS-1$

                    EObject elem = ep.resolveSemanticElement();

                    if (elem instanceof OrgModel) {
                        OrgModel om = (OrgModel) elem;

                        OrgMetaModel embeddedMetamodel = om
                                .getEmbeddedMetamodel();
                        if (embeddedMetamodel != null) {
                            EList<OrganizationType> organizationTypes = embeddedMetamodel
                                    .getOrganizationTypes();

                            for (OrganizationType organizationType : organizationTypes) {
                                Image img2 = null;
                                img2 = ExtendedImageRegistry.INSTANCE
                                        .getImage(OMModelImages
                                                .getImageObject(OMModelImages.IMAGE_ORGANISATION_TYPE));
                                ImageDescriptor imgDesc2 = ImageDescriptor
                                        .createFromImage(img2);

                                CreateOMElementDiagramAction typeAct = new CreateOMElementDiagramAction(
                                        workbenchPart.getSite().getPage(),
                                        OMActionIds.ACTION_ADD_ORGANIZATION,
                                        imgDesc2, organizationType);

                                typeAct.init();
                                popupMenu.appendToGroup("addGroup", typeAct); //$NON-NLS-1$

                            }
                        }
                    }

                }

            }
        }

    }

    /**
     * @param om
     * @param type
     * @return
     */
    private EList<OrgUnitFeature> getOrgUnitFeatures(OrgElementType type) {
        EList<OrgUnitFeature> lstOrgUnitFeatures = null;

        if (type instanceof OrganizationType) {
            OrganizationType orgType = (OrganizationType) type;
            lstOrgUnitFeatures = orgType.getOrgUnitFeatures();
        }

        return lstOrgUnitFeatures;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.common.ui.services.action.internal.contributionitem
     * .
     * IContributionItemProvider#disposeContributions(org.eclipse.gmf.runtime.common
     * .ui.util.IWorkbenchPartDescriptor)
     */
    public void disposeContributions(
            IWorkbenchPartDescriptor workbenchPartDescriptor) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.common.ui.services.action.internal.contributionitem
     * .IContributionItemProvider#updateActionBars(org.eclipse.ui.IActionBars,
     * org.eclipse.gmf.runtime.common.ui.util.IWorkbenchPartDescriptor)
     */
    public void updateActionBars(IActionBars actionBars,
            IWorkbenchPartDescriptor workbenchPartDescriptor) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.gmf.runtime.common.core.service.IProvider#
     * addProviderChangeListener
     * (org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener)
     */
    public void addProviderChangeListener(IProviderChangeListener listener) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.common.core.service.IProvider#provides(org.eclipse
     * .gmf.runtime.common.core.service.IOperation)
     */
    @SuppressWarnings("restriction")
    public boolean provides(IOperation operation) {
        if (operation instanceof ContributeToPopupMenuOperation) {

            ContributeToPopupMenuOperation op = (ContributeToPopupMenuOperation) operation;
            IWorkbenchPart workbenchPart = op.getWorkbenchPart();

            ISelection selection = workbenchPart.getSite()
                    .getSelectionProvider().getSelection();

            if (selection instanceof IStructuredSelection) {
                IStructuredSelection sel = (IStructuredSelection) selection;
                if (sel.size() == 1) {
                    Object firstElement = sel.getFirstElement();

                    if (firstElement instanceof OrganizationSubEditPart
                            || firstElement instanceof OrgUnitSubEditPart
                            || firstElement instanceof OrgModelEditPart) {
                        return true;
                    }
                }
            }

        }

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.gmf.runtime.common.core.service.IProvider#
     * removeProviderChangeListener
     * (org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener)
     */
    public void removeProviderChangeListener(IProviderChangeListener listener) {
        // TODO Auto-generated method stub

    }

}
