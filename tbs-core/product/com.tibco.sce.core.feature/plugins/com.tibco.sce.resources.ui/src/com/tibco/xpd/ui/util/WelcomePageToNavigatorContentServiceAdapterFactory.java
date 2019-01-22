/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.ui.util;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.navigator.resources.workbench.TabbedPropertySheetTitleProvider;
import org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyTitle;
import org.eclipse.ui.intro.config.CustomizableIntroPart;
import org.eclipse.ui.navigator.INavigatorContentService;
import org.eclipse.ui.navigator.resources.ProjectExplorer;

/**
 * On first startup of Studio (or any startup that displays the Welcome page
 * first), the properties view title bar is blank.
 * <p>
 * This is caused by {@link TabbedPropertySheetTitleProvider} which asks the
 * <b>currently active view part</b> to adapt to INavigatorContentService.
 * Trouble is that on initial start of studio when the welcome page is
 * displayed, the currently active view part is teh welcome page NOT the project
 * explorer. The Welcome page part ({@link CustomizableIntroPart}) does not
 * adapt to {@link INavigatorContentService} and therefore the
 * descriptionProvider for teh {@link TabbedPropertySheetTitleProvider} is set
 * to <code>null</code>.
 * <p>
 * This in turn causes the image and text given to the
 * {@link TabbedPropertyTitle} to be <code>null</code> which in turn causes
 * {@link TabbedPropertyTitle}'s paint listener to draw no background on the
 * title bar - and hence it is blank.
 * <p>
 * <b>This {@link WelcomePageToNavigatorContentServiceAdapterFactory} class
 * fixes the problem by being contributed to act as an adapter on behalf of
 * {@link CustomizableIntroPart} when it is asked for
 * {@link INavigatorContentService}. In this case we look for the project
 * explorer part and <b>force</b> the navigator content service to be loaded
 * from that.
 * 
 * @author aallway
 * @since 14 Dec 2011
 */
public class WelcomePageToNavigatorContentServiceAdapterFactory implements
        IAdapterFactory {

    /**
     * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object,
     *      java.lang.Class)
     * 
     * @param adaptableObject
     * @param adapterType
     * @return
     */
    @SuppressWarnings("restriction")
    @Override
    public Object getAdapter(Object adaptableObject, Class adapterType) {
        if (adaptableObject instanceof CustomizableIntroPart
                && adapterType == INavigatorContentService.class) {
            IWorkbench workbench = PlatformUI.getWorkbench();
            if (workbench != null) {
                IWorkbenchWindow activeWorkbenchWindow =
                        workbench.getActiveWorkbenchWindow();
                if (activeWorkbenchWindow != null) {
                    IWorkbenchPage activePage =
                            activeWorkbenchWindow.getActivePage();
                    if (activePage != null) {
                        IViewReference viewReference =
                                activePage
                                        .findViewReference(ProjectExplorer.VIEW_ID);
                        if (viewReference != null) {
                            IWorkbenchPart part = viewReference.getPart(false);
                            if (part != null) {
                                return part.getAdapter(adapterType);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
     * 
     * @return
     */
    @Override
    public Class[] getAdapterList() {
        return new Class[] { INavigatorContentService.class };
    }
}
