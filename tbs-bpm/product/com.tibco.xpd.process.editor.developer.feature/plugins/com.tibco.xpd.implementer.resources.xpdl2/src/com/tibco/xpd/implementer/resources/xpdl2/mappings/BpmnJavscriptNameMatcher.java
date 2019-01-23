/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.mappings;

import java.util.StringTokenizer;

import com.tibco.xpd.implementer.script.TaskSendInvokeBusinessProcessAdapter;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.extensions.AbstractTypeMatcher;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.util.MapperTreeItem;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Javascript BPMN Destination type matcher. This class is used by the auto-map
 * functionality on the mapper screen. This type matcher applies to
 * "Input to Service" direction of the mappings.
 * 
 * @author rsomayaj
 * @since 3.3 (11 Jun 2010)
 */
public class BpmnJavscriptNameMatcher extends AbstractTypeMatcher {

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
        // Expect both the target and the source to be ConceptPaths
        ConceptPath srcCp = getConceptPath(sourceObject);
        String sourceStr = getString(srcCp);
        ConceptPath tgtCp = getConceptPath(targetObject);
        String targetStr = getString(tgtCp);

        // Names matched
        if (sourceStr != null && targetStr != null) {
            if (sourceStr.equalsIgnoreCase(targetStr)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param sourceObject
     * @return
     */
    protected ConceptPath getConceptPath(Object obj) {
        if (obj instanceof ConceptPath) {
            return (ConceptPath) obj;
        }
        return null;
    }

    /**
     * @param sourceObject
     * @return
     */
    protected String getString(ConceptPath conceptPath) {
        if (conceptPath != null) {
            String sourceStr =
                    removeArrayBraces(getLastToken(conceptPath.getPath()));
            return sourceStr;
        }
        return null;
    }

    /**
     * @param lastToken
     * @return
     */
    protected String removeArrayBraces(String token) {
        StringBuilder strBuilder = new StringBuilder(token);
        int indOpenBracket = strBuilder.indexOf("["); //$NON-NLS-1$
        if (indOpenBracket != -1) {
            strBuilder.delete(indOpenBracket, strBuilder.indexOf("]") + 1); //$NON-NLS-1$
        }
        return strBuilder.toString();
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

            /*
             * Really and truly, we should be able to handle ALL JavaScript
             * mappings (because all of our javascript mappers use concept
             * paths.
             * 
             * Given that by the time we get here we already know it's an
             * activity that has some kind of mapping on, we can just return
             * true;
             */
            return true;
        }

        return false;
    }

    /**
     * @param activity
     * @return true if the given activity is a Send Task with its
     *         xpdExt:ImplementationType is
     *         <code>TaskSendInvokeBusinessProcessAdapter.XPDEXT_INVOKEBUSINESSPROCESS_LITERAL</code>
     */
    private boolean isInvokeBusinessProcessActivity(Activity activity) {
        if (TaskType.SEND_LITERAL.equals(TaskObjectUtil.getTaskType(activity))) {
            TaskSend taskSend =
                    ((Task) activity.getImplementation()).getTaskSend();
            Object implementationType =
                    Xpdl2ModelUtil.getOtherAttribute(taskSend,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ImplementationType());
            if (TaskSendInvokeBusinessProcessAdapter.XPDEXT_INVOKEBUSINESSPROCESS_LITERAL
                    .equals(implementationType)) {
                return true;
            }

        }
        return false;
    }

    /**
     * @param string
     *            to tokenize
     * @return
     */
    protected String getLastToken(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, "."); //$NON-NLS-1$
        String lastToken = null;
        do {
            lastToken = stringTokenizer.nextToken();
        } while (stringTokenizer.hasMoreTokens());
        return lastToken;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractTypeMatcher#getNormalizedPath(java.lang.Object)
     * 
     * @param mappingObject
     * @return {@link MapperTreeItem}
     */
    @Override
    public MapperTreeItem getNormalizedPath(Object contextObject,
            Object mappingObject) {
        if (mappingObject instanceof ConceptPath) {
            return new JavaScriptMapperTreeItem(contextObject,
                    (ConceptPath) mappingObject);
        }
        return null;

    }
}
