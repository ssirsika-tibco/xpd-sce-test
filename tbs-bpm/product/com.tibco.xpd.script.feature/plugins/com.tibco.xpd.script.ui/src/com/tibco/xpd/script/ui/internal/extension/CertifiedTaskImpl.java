package com.tibco.xpd.script.ui.internal.extension;

import java.util.ArrayList;
import java.util.List;

/**
 * Check if task implementation is certified to be used with TBStudio. List of
 * certified contributions is kept in internal list.
 * 
 * @author mmaciuki
 * 
 */
public class CertifiedTaskImpl {

    private final List<String> registeredIds;

    /**
     * Certified contributions. Format:<br>
     * [task_implementation_id]:[task_type] (eg. E-Mail:Service)<br>
     * or<br>
     * [task_implementation_id]:[task_type] (eg. "WebService:*")<br>
     * Latter format is used to certify implementation for any task type.
     */
    CertifiedTaskImpl() {
        registeredIds = new ArrayList<String>();
        registeredIds.add("DocumentOperations:Service"); //$NON-NLS-1$
        registeredIds.add("GlobalData:Service"); //$NON-NLS-1$
        registeredIds.add("E-Mail:Service"); //$NON-NLS-1$
        registeredIds.add("Database:Service"); //$NON-NLS-1$
        registeredIds.add("Java:Service"); //$NON-NLS-1$
        registeredIds.add("WebService:*"); //$NON-NLS-1$
        registeredIds.add("BW Service:Service"); //$NON-NLS-1$
        registeredIds.add("CIM Service:Service");//$NON-NLS-1$
        registeredIds.add("Unspecified:*"); //$NON-NLS-1$
        registeredIds.add("Orchestrator:Service"); //$NON-NLS-1$
        registeredIds.add("Order:Service"); //$NON-NLS-1$
        registeredIds.add("DecisionService:Service"); //$NON-NLS-1$
        registeredIds.add("ProcessForm:User"); //$NON-NLS-1$
        registeredIds.add("PageflowProcessForm:User"); //$NON-NLS-1$
        // XPD-288
        registeredIds.add("InvokeBusinessProcess:Send");//$NON-NLS-1$
        registeredIds.add("iProcessEAI:Service"); //$NON-NLS-1$
        // XPD-7072
        registeredIds.add("RESTService:*"); //$NON-NLS-1$
    }

    public boolean isCertified(String id, String type) {
        boolean result;
        StringBuilder sb;

        sb = new StringBuilder();
        sb.append(id);
        sb.append(":");sb.append(type);//$NON-NLS-1$
        result = registeredIds.contains(sb.toString());

        if (result == false) {
            sb = new StringBuilder();
            sb.append(id);
            sb.append(":*");//$NON-NLS-1$
            result = registeredIds.contains(sb.toString());
        }

        return result;
    }

}
