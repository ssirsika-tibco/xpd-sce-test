/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.implementer.script;

/**
 * TaskSendInvokeBusinessProcessAdapter
 * 
 * 
 * @author bharge
 * @since 3.3 (10 Mar 2010)
 */
public class TaskSendInvokeBusinessProcessAdapter extends
        TaskSendMessageAdapter {

    public static String XPDEXT_INVOKEBUSINESSPROCESS_LITERAL =
            "InvokeBusinessProcess"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.implementer.script.TaskSendMessageAdapter#
     * getXpdExtImplementationType()
     */
    @Override
    protected String getXpdExtImplementationType() {
        return XPDEXT_INVOKEBUSINESSPROCESS_LITERAL;
        // return TaskServiceMessageAdapter.XPDEXT_WEBSVC_LITERAL;
    }

}
