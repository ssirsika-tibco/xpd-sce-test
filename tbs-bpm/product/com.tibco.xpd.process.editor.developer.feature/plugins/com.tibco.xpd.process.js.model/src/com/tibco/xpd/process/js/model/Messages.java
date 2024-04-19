package com.tibco.xpd.process.js.model;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.process.js.model.messages"; //$NON-NLS-1$

    public static String Activator_Error_Reading_ClassContributor;

    public static String Activator_Error_Reading_Model_ModelContibution;

	public static String BpmScriptWrapperFactory_ParamTooltipDesc;

	public static String BpmScriptWrapperFactory_Operation_Description_1;

	public static String BpmScriptWrapperFactory_Operation_Description_2;

	public static String BpmScriptWrapperFactory_Operation_Description_3;

	public static String BpmScriptWrapperFactory_Parameter_description;

	public static String BpmScriptWrapperFactory_ReturnParamTooltipDesc;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
