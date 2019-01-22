/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.wizards;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.wizards.newtypedeclaration.AbstractNewTypeDeclarationWizard;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.AbstractSpecialFolderFileSelectionPage;
import com.tibco.xpd.wm.tasklibrary.editor.resources.TaskLibraryFactory;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 *
 * @author aallway
 * @since 
 */
public class NewTypeDeclarationWizard extends AbstractNewTypeDeclarationWizard {

    @Override
    protected EObject getEContainer(
            AbstractSpecialFolderFileSelectionPage locationPage) {
        EObject container = locationPage.getEContainer();

        if (container instanceof Package) {
            return container;
        }

        return Xpdl2ModelUtil.getPackage(container);
    }

    @Override
    protected AbstractSpecialFolderFileSelectionPage getFileSelectionPage() {
        return new TaskLibraryAndProjectSelectionPage();
    }

}
