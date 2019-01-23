/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.utils;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.utils.ActionUtil;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;

/**
 * Moved the know service task implementation type id's from teh rather obscure
 * {@link ActionUtil} to here.
 * 
 * @author aallway
 * @since 3 Aug 2011
 */
public class TaskImplementationTypeDefinitions {

    /** BW service identifier. */
    public static final String BW_SERVICE = "BW Service"; //$NON-NLS-1$

    /** Web service identifier. */
    public static final String WEB_SERVICE =
            ReplyActivityUtil.TASK_IMPL_TYPE_DEF_WEB_SERVICE;

    /** Web service identifier of version 2.0. */
    public static final String WEB_SERVICE_V2_0 = "Web Service"; //$NON-NLS-1$

    /** Java service */
    public static final String JAVA_SERVICE = "Java"; //$NON-NLS-1$

    /** Database */
    public static final String DATABASE_SERVICE = "Database"; //$NON-NLS-1$

    /** E-Mail */
    public static final String EMAIL_SERVICE = "E-Mail"; //$NON-NLS-1$

    /** Decision Service */
    public static final String DECISION_SERVICE = "DecisionService"; //$NON-NLS-1$

    /** Invoke business process. */
    public static final String INVOKE_BUSINESS_PROCESS =
            "InvokeBusinessProcess"; //$NON-NLS-1$

    /** Invoke Global Data operation */
    public static final String GLOBAL_DATA = "GlobalData"; //$NON-NLS-1$

    /* Invoke Document Operation */
    public static final String DOCUMENT_OPERATIONS = "DocumentOperations"; //$NON-NLS-1$

    /** XPD_7074 - REST Service task. */
    public static final String REST_SERVICE = "RESTService"; //$NON-NLS-1$
}
