/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.wizards.pages;

import org.eclipse.core.resources.IContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.widgets.Text;

/**
 * The Package information page on the "New BPM/SOA Project" and the
 * "New XPDL Package" wizard needs to update its name from the values entered in
 * the previous page. This requires the Package Information page to be aware of
 * what the previous pages are.
 * 
 * The Package information page adds a listener on the Text widget in which the
 * user enters the Package file name and uses the folder in the package file is
 * the package file is created.
 * 
 * @author rsomayaj
 * 
 */
public interface IPackageTextAndContainerPage extends IWizardPage {

    /**
     * Text Widget which is used to enter the name of the XPDL package on both
     * the New BPM/SOA Project wizard and the New XPDL Package wizard.
     * 
     * @return
     */
    Text getTxtPackageFile();

    /**
     * The Process Package special folder, in which the XPDL Package is created.
     * 
     * @return
     */
    IContainer getPackagesFolderContainer();
}
