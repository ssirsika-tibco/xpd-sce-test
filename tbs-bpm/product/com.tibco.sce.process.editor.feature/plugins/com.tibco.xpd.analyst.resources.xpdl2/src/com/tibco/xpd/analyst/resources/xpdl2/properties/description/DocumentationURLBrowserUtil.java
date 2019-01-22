/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.properties.description;

import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Simple util for launching interal / external browser (as per user's eclipse
 * preference setting
 * 
 * @author aallway
 * @since 25 Oct 2012
 */
public class DocumentationURLBrowserUtil {

    public static void launchBrowserForElement(Object namedElementObject,
            String documentationURL) {

        NamedElement namedElement = null;
        if (namedElementObject instanceof NamedElement) {
            namedElement = (NamedElement) namedElementObject;

        } else if (namedElementObject instanceof IAdaptable) {
            Object eo =
                    ((IAdaptable) namedElementObject).getAdapter(EObject.class);
            if (eo instanceof NamedElement) {
                namedElement = (NamedElement) eo;
            }
        }

        String name = Xpdl2ModelUtil.getDisplayNameOrName(namedElement);
        String namedElementPath = getNamedElementPath(namedElement);

        try {
            IWorkbenchBrowserSupport support =
                    PlatformUI.getWorkbench().getBrowserSupport();
            if (support != null) {
                IWebBrowser browser =
                        support.createBrowser(IWorkbenchBrowserSupport.STATUS,
                                "Process.Doc.Browser." + documentationURL, //$NON-NLS-1$
                                name,
                                String.format("Documentation for: %1$s", //$NON-NLS-1$
                                        namedElementPath));

                if (browser != null) {
                    browser.openURL(new URL(documentationURL));
                }
            }

        } catch (Exception e) {
            Xpdl2ResourcesPlugin
                    .getDefault()
                    .getLogger()
                    .error(e,
                            "Could not open documentation URL: " + documentationURL); //$NON-NLS-1$
        }

    }

    /**
     * @param namedElement
     * @return /' separate label path from package down to named element.
     */
    private static String getNamedElementPath(NamedElement namedElement) {
        StringBuilder pathToObject = new StringBuilder();

        pathToObject.append(Xpdl2ModelUtil.getDisplayNameOrName(namedElement));

        NamedElement parent =
                (NamedElement) Xpdl2ModelUtil.getAncestor(namedElement
                        .eContainer(), NamedElement.class);

        while (parent != null && !(parent instanceof Package)) {
            String label = Xpdl2ModelUtil.getDisplayNameOrName(parent);
            if (label != null && label.length() > 0) {
                pathToObject.insert(0, " / "); //$NON-NLS-1$
                pathToObject.insert(0, label);
            }

            parent =
                    (NamedElement) Xpdl2ModelUtil.getAncestor(parent
                            .eContainer(), NamedElement.class);
        }

        IFile file = WorkingCopyUtil.getFile(namedElement);
        if (file != null) {
            String label = file.getName();
            if (label != null && label.length() > 0) {
                pathToObject.insert(0, " / "); //$NON-NLS-1$
                pathToObject.insert(0, label);
            }
        }
        return pathToObject.toString();
    }
}
