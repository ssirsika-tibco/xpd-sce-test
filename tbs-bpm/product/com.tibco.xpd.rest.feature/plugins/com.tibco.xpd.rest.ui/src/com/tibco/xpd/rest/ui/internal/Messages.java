/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.ui.internal;

import org.eclipse.osgi.util.NLS;

/**
 *
 *
 * @author jarciuch
 * @since 4 Aug 2015
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.rest.ui.internal.messages"; //$NON-NLS-1$
		
    public static String RestAssetWizardPage_CreateInitialRsd_label;

    public static String RestAssetWizardPage_DescriptorDetails_label;

    public static String RestAssetWizardPage_FilaName_label;

    public static String RestAssetWizardPage_RestServices_desc;

    public static String RestAssetWizardPage_RestServices_title;
    
    public static String RestServicesUtil_DefaultRsdFile_name;

    public static String RestServicesUtil_RestSf_name;

	public static String RestAssetWizardPage_ImportSwaggerServices_label;
	
	public static String RestAssetWizardPage_Files_label;
	
	public static String RestAssetWizardPage_Browse_label;
	
	public static String RestAssetWizardPage_ImportSwagger_from_url_label;
	
	public static String RestAssetWizardPage_URL_label;
	
	public static String RestAssetWizardPage_InvalidURL_desc;
	
	public static String RestAssetWizardPage_MissingFileName_desc;
	
	public static String RestAssetWizardPage_MissingURLFileName_desc;
	
	public static String RestAssetWizardPage_MissingURL_desc;
	
	public static String RestAssetWizardPage_InvalidURLContent_desc;
	
	public static String RestAssetWizardPage_MissingSwaggerContent_desc;
	
	public static String ImportSwaggerWizard_pageDescription;

	public static String ImportSwaggerWizard_pageTitle;

	public static String ImportSwaggerWizard_windowTitle;

	public static String SwaggerFilePage_Description;
	
	public static String RestAssetWizardPage_ImportSwagger_from_content_label;

	public static String RestAssetWizardPage_InvalidContent_desc;

	public static String RestAssetWizardPage_Content_label;

	public static String RestAssetWizardPage_InvalidFileName_desc;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
