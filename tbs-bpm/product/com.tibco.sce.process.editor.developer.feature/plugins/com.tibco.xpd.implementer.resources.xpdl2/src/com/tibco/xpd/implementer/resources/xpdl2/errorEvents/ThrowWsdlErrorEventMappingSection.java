/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.errorEvents;

import java.util.List;

import javax.wsdl.Fault;
import javax.wsdl.Message;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.implementer.resources.xpdl2.properties.ActivityMessageWsdlItemProvider;
import com.tibco.xpd.implementer.resources.xpdl2.properties.BaseResponseSection;
import com.tibco.xpd.implementer.resources.xpdl2.properties.WebServiceScriptProperties;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.WsdlFilter.WsdlDirection;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.script.ui.internal.BaseScriptSection;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Mapping section for Throw Fault Message End Error Events - the fault message
 * MAY be auto-generated or MAY be selected from a user-defined WSDL operation
 * fault message.
 * 
 * @author aallway
 * @since 3.3
 */
public class ThrowWsdlErrorEventMappingSection extends BaseResponseSection {

    @Override
    protected String getTitle() {
        return Messages.ThrowWsdlErrorEventMappingSection_MapParamsToFaultMessage_description;
    }

    /**
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractActivityMappingProblemMarkerHandlingSection#createTargetContentProvider()
     */
    @Override
    protected ITreeContentProvider createTargetContentProvider() {
        return new ActivityMessageWsdlItemProvider(MappingDirection.OUT,
                DirectionType.IN_LITERAL, WsdlDirection.FAULT);
    }

    @Override
    protected BaseScriptSection getScriptSection() {
        return new WebServiceScriptProperties(getDirection()) {
            @Override
            protected Message getWsdlMessage() {
                Fault fault =
                        ThrowWsdlErrorEventUtil
                                .getWsdlFault((Activity) getInput());
                if (fault != null) {
                    return fault.getMessage();
                }
                return null;
            }
        };
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractMessageMappingOutSection#isValidTransfer(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param source
     * @param target
     * @return
     */
    @Override
    public boolean isValidTransfer(Object source, Object target) {
        boolean valid = false;

        if (super.isValidTransfer(source, target)) {
            if (getInput() instanceof Activity) {
                Activity act = (Activity) getInput();

                Activity activity = (Activity) getInput();
                if (!ThrowErrorEventUtil
                        .shouldGenerateMappingsForErrorEvent(activity)) {
                    valid = true;
                }
            }
        }
        return valid;
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractEditableMappingSection#shouldDisableEditGrammarViewer()
     * 
     * @return
     */
    @Override
    protected boolean shouldDisableEditGrammarViewer() {
        if (getInput() instanceof Activity) {
            Activity activity = (Activity) getInput();

            /*
             * DOn't allow anythiong to change when the throw error event is one
             * we are auto-generating mappings for.
             */
            if (ThrowErrorEventUtil
                    .shouldGenerateMappingsForErrorEvent(activity)) {
                return true;
            }

        }
        return super.shouldDisableEditGrammarViewer();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.AbstractTransactionalSection#shouldRefresh
     * (java.util.List)
     */
    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        boolean refresh = super.shouldRefresh(notifications);
        if (!refresh) {
            for (Notification notification : notifications) {
                if (notification.getNotifier() instanceof AssociatedParameters) {
                    Activity parentAct =
                            Xpdl2ModelUtil
                                    .getParentActivity((AssociatedParameters) notification
                                            .getNotifier());
                    if (parentAct != null && parentAct.equals(getInput())) {
                        refresh = true;
                        break;
                    }
                }

            }
        }

        return refresh;
    }

}
