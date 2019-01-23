package com.tibco.xpd.om.modeler.diagram.part;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gmf.runtime.common.ui.services.action.contributionitem.ContributionItemService;
import org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds;
import org.eclipse.gmf.runtime.diagram.ui.providers.DiagramContextMenuProvider;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.om.core.om.OrgMetaModel;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrganizationType;
import com.tibco.xpd.om.core.om.provider.OMModelImages;
import com.tibco.xpd.om.modeler.diagram.actions.custom.CreateOMElementDiagramAction;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgModelEditPart;
import com.tibco.xpd.om.modeler.subdiagram.actions.custom.OMActionIds;

/**
 * @generated
 */
public class DiagramEditorContextMenuProvider extends
        DiagramContextMenuProvider {

    /**
     * @generated
     */
    private IWorkbenchPart part;

    /**
     * @generated
     */
    private DeleteElementAction deleteAction;

    /**
     * @generated
     */
    public DiagramEditorContextMenuProvider(IWorkbenchPart part,
            EditPartViewer viewer) {
        super(part, viewer);
        this.part = part;
        deleteAction = new DeleteElementAction(part);
        deleteAction.init();
    }

    /**
     * @generated
     */
    @Override
    public void dispose() {
        if (deleteAction != null) {
            deleteAction.dispose();
            deleteAction = null;
        }
        super.dispose();
    }

    /**
     * @generated NOT
     */
    @Override
    public void buildContextMenu(final IMenuManager menu) {
        getViewer().flush();
        try {
            TransactionUtil.getEditingDomain((EObject) getViewer()
                    .getContents().getModel()).runExclusive(new Runnable() {

                @Override
                public void run() {
                    ContributionItemService
                            .getInstance()
                            .contributeToPopupMenu(DiagramEditorContextMenuProvider.this,
                                    part);
                    // TODO: Understand why GMF has generated this class
                    // and overridden the default GMF context menu
                    // provider:
                    // org.eclipse.gmf.runtime.diagram.ui.providers.
                    // DiagramContextMenuProvider

                    menu.remove(ActionIds.ACTION_DELETE_FROM_MODEL);
                    menu.appendToGroup("editGroup", deleteAction); //$NON-NLS-1$
                    buildDynamicMenu(menu);
                }
            });
        } catch (Exception e) {
            OrganizationModelDiagramEditorPlugin.getInstance()
                    .logError("Error building context menu", e); //$NON-NLS-1$
        }
    }

    /**
     * Adds contributions to the context menu programatically
     * 
     * @param popupMenu
     */
    protected void buildDynamicMenu(IMenuManager popupMenu) {
        final ISelection selection =
                part.getSite().getSelectionProvider().getSelection();

        if (selection instanceof IStructuredSelection) {
            IStructuredSelection sel = (IStructuredSelection) selection;
            if (sel.size() == 1) {
                Object firstElement = sel.getFirstElement();

                if (firstElement instanceof OrgModelEditPart) {
                    // Need to add an Actions to create
                    // 1. A standard untyped Organizations
                    // 2. Any typed Organizations
                    OrgModelEditPart ep = (OrgModelEditPart) firstElement;

                    CreateOMElementDiagramAction act =
                            createAction(OMModelImages.IMAGE_ORGANISATION,
                                    OMActionIds.ACTION_ADD_ORGANIZATION);
                    popupMenu.appendToGroup("addGroup", act); //$NON-NLS-1$

                    // XPD-5300 Add a Dynamic Organization option
                    act =
                            createAction(OMModelImages.IMAGE_DYNAMIC_ORGANISATION,
                                    OMActionIds.ACTION_ADD_DYNAMICORGANIZATION);
                    popupMenu.appendToGroup("addGroup", act); //$NON-NLS-1$

                    EObject elem = ep.resolveSemanticElement();

                    if (elem instanceof OrgModel) {
                        OrgModel om = (OrgModel) elem;

                        OrgMetaModel embeddedMetamodel =
                                om.getEmbeddedMetamodel();
                        if (embeddedMetamodel != null) {
                            EList<OrganizationType> organizationTypes =
                                    embeddedMetamodel.getOrganizationTypes();

                            for (OrganizationType organizationType : organizationTypes) {
                                Image img2 = null;
                                img2 =
                                        ExtendedImageRegistry.INSTANCE
                                                .getImage(OMModelImages
                                                        .getImageObject(OMModelImages.IMAGE_ORGANISATION_TYPE));
                                ImageDescriptor imgDesc2 =
                                        ImageDescriptor.createFromImage(img2);

                                CreateOMElementDiagramAction typeAct =
                                        new CreateOMElementDiagramAction(
                                                part.getSite().getPage(),
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
     * Create an action with the given information.
     * 
     * @param imgPath
     *            image path from {@link OMModelImages}
     * @param actionId
     *            action id from {@link OMActionIds}
     * @return
     */
    private CreateOMElementDiagramAction createAction(String imgPath,
            String actionId) {
        Image img = null;
        img =
                ExtendedImageRegistry.INSTANCE.getImage(OMModelImages
                        .getImageObject(imgPath));
        ImageDescriptor imgDesc = ImageDescriptor.createFromImage(img);

        CreateOMElementDiagramAction act =
                new CreateOMElementDiagramAction(part.getSite().getPage(),
                        actionId, imgDesc);

        act.init();
        return act;
    }
}
