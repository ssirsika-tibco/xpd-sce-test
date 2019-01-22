/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.process.datamapper.scriptdata;

import com.tibco.xpd.process.datamapper.common.AbstractExpressionScriptDataMapperProvider;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.xpdExtension.Audit;
import com.tibco.xpd.xpdExtension.AuditEvent;
import com.tibco.xpd.xpdExtension.AuditEventType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;

/**
 * ScriptDataMapper element provider for Task scripts (process manager / work
 * manager scripts).
 * 
 * @author nwilson
 * @since 13 Apr 2015
 */
public class AuditEventScriptDataMapperProvider extends
        AbstractExpressionScriptDataMapperProvider {

    private AuditEventType auditEventType;

    /**
     */
    public AuditEventScriptDataMapperProvider(AuditEventType auditEventType,
            String mapperContext) {
        super(mapperContext, DirectionType.IN_LITERAL);
        this.auditEventType = auditEventType;
    }

    /**
     * @see com.tibco.xpd.process.datamapper.common.AbstractExpressionScriptDataMapperProvider#getExpression(java.lang.Object)
     * 
     * @param contextInputObject
     * @return
     */
    @Override
    protected Expression getExpression(Object contextInputObject) {
        Expression information = null;

        if (contextInputObject instanceof Activity) {
            Activity activity = (Activity) contextInputObject;

            AuditEvent auditEvent = null;
            Audit audit = TaskObjectUtil.getAudit(activity);
            if (audit != null) {
                for (AuditEvent event : audit.getAuditEvent()) {
                    if (auditEventType.equals(event.getType())) {
                        auditEvent = event;
                        break;
                    }
                }
            }
            if (auditEvent != null) {
                // get scriptDataMapper from AuditEvent information element
                information = auditEvent.getInformation();
            }
        }
        return information;
    }

}
