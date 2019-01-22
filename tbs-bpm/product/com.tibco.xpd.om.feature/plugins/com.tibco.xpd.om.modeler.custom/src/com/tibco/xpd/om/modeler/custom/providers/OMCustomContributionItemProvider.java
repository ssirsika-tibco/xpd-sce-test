/**
 * 
 */
package com.tibco.xpd.om.modeler.custom.providers;

import org.eclipse.gmf.runtime.common.ui.services.action.contributionitem.AbstractContributionItemProvider;
import org.eclipse.gmf.runtime.common.ui.util.IWorkbenchPartDescriptor;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.xpd.om.modeler.custom.actions.OMActionIds;
import com.tibco.xpd.om.modeler.custom.actions.ShowHideBadgeDiagramAction;

/**
 * 
 * This ContributionItemProvider contributes to the diagram popup menu. In
 * effect, it is appended to the main GMF diagram contributor:<br/><br/>
 * 
 * <em>org.eclipse.gmf.runtime.diagram.ui.providers.DiagramContextMenuProvider</em>
 * 
 * <br/><br/>Look in the plugin.xml at the<br/><br/>
 * 
 * <em>org.eclipse.gmf.runtime.common.ui.services.action.contributionItemProviders</em>
 * 
 * <br/><br/>extension point contribution to see its definition.
 * 
 * 
 * @author rgreen
 * 
 */
public class OMCustomContributionItemProvider extends
        AbstractContributionItemProvider {

    protected IAction createAction(String actionId,
            IWorkbenchPartDescriptor partDescriptor) {
        IWorkbenchPage workbenchPage = partDescriptor.getPartPage();

        if (actionId.equals(OMActionIds.ACTION_SHOW_HIDE_BADGE)) { //$NON-NLS-1$
            return new ShowHideBadgeDiagramAction(workbenchPage);
        }

        // Comment this out for now. Shows how you can create
        // CreateOMElementDiagramAction fro plugin.xml contributions.
        /*
         * if (actionId.equals(OMActionIds.ACTION_ADD_ORGUNIT)) { //$NON-NLS-1$
         * 
         * Image img = null; img =
         * ExtendedImageRegistry.INSTANCE.getImage(OMModelImages
         * .getImageObject(OMModelImages.IMAGE_ORG_UNIT)); ImageDescriptor
         * imgDesc = ImageDescriptor.createFromImage(img);
         * 
         * return new CreateOMElementDiagramAction(workbenchPage,
         * OMActionIds.ACTION_ADD_ORGUNIT, imgDesc, null); }
         * 
         * if (actionId.equals(OMActionIds.ACTION_ADD_POSITION)) { //$NON-NLS-1$
         * 
         * Image img = null; img =
         * ExtendedImageRegistry.INSTANCE.getImage(OMModelImages
         * .getImageObject(OMModelImages.IMAGE_POSITION)); ImageDescriptor
         * imgDesc = ImageDescriptor.createFromImage(img);
         * 
         * return new CreateOMElementDiagramAction(workbenchPage,
         * OMActionIds.ACTION_ADD_POSITION, imgDesc, null); }
         */

        return super.createAction(actionId, partDescriptor);
    }

}
