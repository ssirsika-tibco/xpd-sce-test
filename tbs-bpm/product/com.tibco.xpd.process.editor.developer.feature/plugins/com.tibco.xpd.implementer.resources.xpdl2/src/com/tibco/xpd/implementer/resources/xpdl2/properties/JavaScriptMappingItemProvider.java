/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.xpdExtension.CorrelationDataMappings;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author rsomayaj
 * 
 */
public class JavaScriptMappingItemProvider
        extends BaseJavaScriptMappingItemProvider {

    private final boolean wsdlInput;

    /**
     * @param wsdlInput
     * 
     */
    public JavaScriptMappingItemProvider(MappingDirection mappingDirection,
            boolean wsdlInput) {
        super(mappingDirection);
        this.wsdlInput = wsdlInput;
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.BaseJavaScriptMappingItemProvider#getScriptContext()
     * 
     * @return
     */
    @Override
    public String getScriptContext() {
        return ScriptMappingCompositorFactory.DEFAULT;
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.BaseJavaScriptMappingItemProvider#resolveParameter(com.tibco.xpd.xpdl2.Activity,
     *      com.tibco.xpd.mapper.MappingDirection, java.lang.String)
     * 
     * @param activity
     * @param direction2
     * @param formalName
     * @return
     */
    @Override
    public ConceptPath resolveParameter(Activity activity,
            MappingDirection direction, String formalName) {
        String name = formalName;
        if (direction == MappingDirection.IN && formalName
                .startsWith(BaseJavaScriptMappingItemProvider.PARAMETER_PREFIX
                        + BaseJavaScriptMappingItemProvider.PARAMETER_SEPARATOR)) {

            /*
             * Sid: XPD-????: Only strip "parameter.", "response." or "process."
             * off of JAVA service tasks NOT off other task types (otherwise if
             * WSDL input/output parts match these prefixes then they get
             * stripped and it breaks mappings.
             * 
             * As far as I can tell this code is never called for JavaService
             * anyway (I think that there is roughly a copy of this in
             * JavaServiceMappingUtil() but at this late stage in project I
             * didn't want to break anything so left it here.
             */
            if (TaskImplementationTypeDefinitions.JAVA_SERVICE
                    .equals(TaskObjectUtil
                            .getTaskImplementationExtensionId(activity))) {
                name = formalName.substring(
                        BaseJavaScriptMappingItemProvider.PARAMETER_PREFIX
                                .length()
                                + BaseJavaScriptMappingItemProvider.PARAMETER_SEPARATOR
                                        .length());
            }
        } else if (direction == MappingDirection.OUT && formalName
                .startsWith(BaseJavaScriptMappingItemProvider.RESPONSE_PREFIX
                        + BaseJavaScriptMappingItemProvider.PARAMETER_SEPARATOR)) {
            /*
             * Sid: XPD-????: Only strip "parameter.", "response." or "process."
             * off of JAVA service tasks NOT off other task types (otherwise if
             * WSDL input/output parts match these prefixes then they get
             * stripped and it breaks mappings.
             * 
             * As far as I can tell this code is never called for JavaService
             * anyway (I think that there is roughly a copy of this in
             * JavaServiceMappingUtil() but at this late stage in project I
             * didn't want to break anything so left it here.
             */
            if (TaskImplementationTypeDefinitions.JAVA_SERVICE
                    .equals(TaskObjectUtil
                            .getTaskImplementationExtensionId(activity))) {
                name = formalName.substring(
                        BaseJavaScriptMappingItemProvider.RESPONSE_PREFIX
                                .length()
                                + BaseJavaScriptMappingItemProvider.PARAMETER_SEPARATOR
                                        .length());
            }
        }

        return null;

    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.BaseJavaScriptMappingItemProvider#shouldBeEditable(com.tibco.xpd.xpdl2.Activity,
     *      com.tibco.xpd.xpdl2.DataMapping, java.lang.Object, java.lang.Object)
     * 
     * @param activity
     * @param mapping
     * @param formal2
     * @param data
     * @return
     */
    @Override
    protected boolean shouldBeEditable(Activity activity, DataMapping mapping,
            Object formal2, Object data) {

        // Mappings needs to be edit-able only if they are co-relation data
        // mappings.
        if (Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {
            EObject container = mapping.eContainer();
            if (container instanceof CorrelationDataMappings) {
                return true;
            }
            return false;
        } else {
            boolean replyActivity = ReplyActivityUtil.isReplyActivity(activity);
            if (replyActivity) {
                Activity requestActivityForReplyActivity = ReplyActivityUtil
                        .getRequestActivityForReplyActivity(activity);
                if (Xpdl2ModelUtil.isGeneratedRequestActivity(
                        requestActivityForReplyActivity)) {
                    return false;
                }
            } else if (ThrowErrorEventUtil
                    .isThrowFaultMessageErrorEvent(activity)) {
                if (ThrowErrorEventUtil
                        .shouldGenerateMappingsForErrorEvent(activity)) {
                    return false;
                }
            }
        }
        return true;
    }

}
