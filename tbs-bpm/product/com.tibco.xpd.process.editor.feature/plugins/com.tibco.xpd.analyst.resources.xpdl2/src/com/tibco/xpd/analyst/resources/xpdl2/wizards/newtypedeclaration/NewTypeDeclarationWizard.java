package com.tibco.xpd.analyst.resources.xpdl2.wizards.newtypedeclaration;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.AbstractSpecialFolderFileSelectionPage;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.PackageSelectionPage;

/**
 * New Type Declaration wizard
 * 
 * @author njpatel
 */
public class NewTypeDeclarationWizard extends AbstractNewTypeDeclarationWizard {

    @Override
    protected EObject getEContainer(
            AbstractSpecialFolderFileSelectionPage locationPage) {
        return locationPage.getEContainer();
    }

    @Override
    protected AbstractSpecialFolderFileSelectionPage getFileSelectionPage() {
        return new PackageSelectionPage();
    }


}
