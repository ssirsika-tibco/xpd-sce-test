package com.tibco.xpd.bom.modeler.diagram.internal.clipboard;

import java.util.Hashtable;
import java.util.Map;

import org.eclipse.gmf.runtime.common.ui.services.action.global.AbstractGlobalActionHandlerProvider;
import org.eclipse.gmf.runtime.common.ui.services.action.global.IGlobalActionHandler;
import org.eclipse.gmf.runtime.common.ui.services.action.global.IGlobalActionHandlerContext;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;

public class BOMGlobalActionHandlerProvider extends
        AbstractGlobalActionHandlerProvider {

    /**
     * List for handlers.
     */
    private Map<IWorkbenchPart, BOMGlobalActionHandler> handlerList = new Hashtable<IWorkbenchPart, BOMGlobalActionHandler>();

    @Override
    public IGlobalActionHandler getGlobalActionHandler(
            final IGlobalActionHandlerContext context) {

        /* Create the handler */
        if (!getHandlerList().containsKey(context.getActivePart())) {
            getHandlerList().put(context.getActivePart(),
                    new BOMGlobalActionHandler());

            /*
             * Register as a part listener so that the cache can be cleared when
             * the part is disposed
             */
            context.getActivePart().getSite().getPage().addPartListener(
                    new IPartListener() {

                        private IWorkbenchPart localPart = context
                                .getActivePart();

                        /**
                         * @see org.eclipse.ui.IPartListener#partActivated(IWorkbenchPart)
                         */
                        public void partActivated(IWorkbenchPart part) {
                            // Do nothing
                        }

                        /**
                         * @see org.eclipse.ui.IPartListener#partBroughtToTop(IWorkbenchPart)
                         */
                        public void partBroughtToTop(IWorkbenchPart part) {
                            // Do nothing
                        }

                        /**
                         * @see org.eclipse.ui.IPartListener#partClosed(IWorkbenchPart)
                         */
                        public void partClosed(IWorkbenchPart part) {
                            /* Remove the cache associated with the part */
                            if (part != null && part == localPart
                                    && getHandlerList().containsKey(part)) {
                                getHandlerList().remove(part);
                                localPart.getSite().getPage()
                                        .removePartListener(this);
                                localPart = null;
                            }
                        }

                        /**
                         * @see org.eclipse.ui.IPartListener#partDeactivated(IWorkbenchPart)
                         */
                        public void partDeactivated(IWorkbenchPart part) {
                            // Do nothing
                        }

                        /**
                         * @see org.eclipse.ui.IPartListener#partOpened(IWorkbenchPart)
                         */
                        public void partOpened(IWorkbenchPart part) {
                            // Do nothing
                        }
                    });
        }

        return (BOMGlobalActionHandler) getHandlerList().get(
                context.getActivePart());
    }

    /**
     * Returns the handlerList.
     * 
     * @return Hashtable
     */
    private Map<IWorkbenchPart, BOMGlobalActionHandler> getHandlerList() {
        return handlerList;
    }

}
