/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper;

import com.tibco.xpd.implementer.resources.xpdl2.mappings.JavaScriptMapperTreeItem;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.extensions.AbstractTypeMatcher;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.util.MapperTreeItem;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Java service task, mappings type matcher.
 * 
 * @author rsomayaj
 * @since 3.3 (5 Jul 2010)
 */
public class JavaServiceJSTypeMatcher extends AbstractTypeMatcher {

    /**
     * 
     */
    private static final String JAVA_IMP_TYPE = "Java"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractTypeMatcher#getNormalizedPath(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param contextObject
     * @param mappingObject
     * @return
     */
    @Override
    public MapperTreeItem getNormalizedPath(Object contextObject,
            Object mappingObject) {

        if (mappingObject instanceof ConceptPath) {
            return new JavaScriptMapperTreeItem(contextObject,
                    (ConceptPath) mappingObject);
        }

        if (mappingObject instanceof JavaMethodParameter) {
            return new JavaServiceMapperTreeItem(contextObject, mappingObject);
        }

        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractTypeMatcher#isApplicable(java.lang.Object,
     *      com.tibco.xpd.mapper.MappingDirection)
     * 
     * @param contextObject
     * @param mappingDirection
     * @return
     */
    @Override
    public boolean isApplicable(Object contextObject,
            MappingDirection mappingDirection) {
        if (contextObject instanceof Activity) {
            Activity activity = (Activity) contextObject;
            TaskType taskType = TaskObjectUtil.getTaskType(activity);
            if (TaskType.SERVICE_LITERAL.equals(taskType)) {
                TaskService taskService =
                        ((Task) activity.getImplementation()).getTaskService();
                Object impType =
                        Xpdl2ModelUtil.getOtherAttribute(taskService,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ImplementationType());
                if (JAVA_IMP_TYPE.equals(impType)) {
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractTypeMatcher#typesMatch(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param sourceObject
     * @param targetObject
     * @return
     */
    @Override
    public boolean typesMatch(Object sourceObject, Object targetObject) {
        if (sourceObject instanceof ConceptPath) {
            String srcName = ((ConceptPath) sourceObject).getBaseName();
            if (targetObject instanceof JavaMethodParameter) {
                String tgtName = ((JavaMethodParameter) targetObject).getName();
                if (srcName.equalsIgnoreCase(tgtName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
