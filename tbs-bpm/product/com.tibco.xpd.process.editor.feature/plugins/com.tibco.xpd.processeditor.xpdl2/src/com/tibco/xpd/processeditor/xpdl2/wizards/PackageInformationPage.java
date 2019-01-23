package com.tibco.xpd.processeditor.xpdl2.wizards;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;


/**
 * Wizard page to collect all package information, including the package header
 * content
 * <p>
 * Main content moved to {@link AbstractXpdlPackageInformationPage} for re-use
 * in task library wizard.
 * 
 * @author njpatel, allway
 * 
 */
public class PackageInformationPage extends AbstractXpdlPackageInformationPage {

    @Override
    protected String getDefaultPackageName() {
        return Xpdl2ResourcesConsts.DEFAULT_PACKAGE_NAME;
    }

    @Override
    protected String getPackageFileExtension() {
        return Xpdl2ResourcesConsts.DEFAULT_PACKAGE_FILENAME_EXT;
    }

    @Override
    protected String getPackageUIHeaderInfoLabel() {
        return Messages.PackageInformationPage_5;
    }

    @Override
    protected String getPackageUILabelNameLabel() {
        return Messages.PackageInformationPage_DisplayName;
    }

    @Override
    protected String getPackageUINameGroupLabel() {
        return Messages.PackageInformationPage_3;
    }

    @Override
    protected String getPackageUINameLabel() {
        return Messages.PackageInformationPage_4;
    }
    
}
