/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.providers;

import java.util.Collection;

import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.DefaultPojoJsClass;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.JavaMethod;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.JavaMethodParameter;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.ParameterTypeEnum;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.IUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider;
import com.tibco.xpd.xpdExtension.ScriptInformation;

/**
 * Mapping Rule info provider for JavaMethodParameter and
 * JavaMethodReturnParameter mapping content.
 * 
 * @author aallway
 * @since 3.3 (17 Jun 2010)
 */
public class JavaMethodMappingRuleInfoProvider extends
        MappingRuleContentInfoProvider {

    /**
     * @param contentProvider
     */
    public JavaMethodMappingRuleInfoProvider(
            ITreeContentProvider contentProvider) {
        super(contentProvider);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider
     * #getObjectPath(java.lang.Object)
     */
    @Override
    public String getObjectPath(Object objectFromMappingOrContent) {
        String path = null;

        if (objectFromMappingOrContent != null) {
            if (objectFromMappingOrContent instanceof JavaMethodParameter) {
                path =
                        ((JavaMethodParameter) objectFromMappingOrContent)
                                .getPath();

            } else if (objectFromMappingOrContent instanceof ScriptInformation) {
                path =
                        ((ScriptInformation) objectFromMappingOrContent)
                                .getName();
            } else if (objectFromMappingOrContent instanceof JavaMethod) {
                JavaMethod javaMethod = (JavaMethod) objectFromMappingOrContent;
                path = javaMethod.getContainer().getFullyQualifiedName() + "." //$NON-NLS-1$
                        + javaMethod.getName();
            } else if (objectFromMappingOrContent instanceof ConceptPath) {
                path = ((ConceptPath) objectFromMappingOrContent).getPath();
            } else {
                log("Unexpected content type: " + objectFromMappingOrContent.getClass()); //$NON-NLS-1$
            }
        }

        return path != null ? path : ""; //$NON-NLS-1$    
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider
     * #getObjectPathDescription(java.lang.Object)
     */
    @Override
    public String getObjectPathDescription(Object objectFromMappingOrContent) {
        String path = null;

        if (objectFromMappingOrContent != null) {
            if (objectFromMappingOrContent instanceof JavaMethodParameter) {
                path =
                        ((JavaMethodParameter) objectFromMappingOrContent)
                                .getQualifiedElementName();

            } else if (objectFromMappingOrContent instanceof JavaMethod) {
                JavaMethod javaMethod = (JavaMethod) objectFromMappingOrContent;
                path = javaMethod.getContainer().getFullyQualifiedName() + "." //$NON-NLS-1$
                        + javaMethod.getName();
            } else if (objectFromMappingOrContent instanceof ScriptInformation) {
                path =
                        ((ScriptInformation) objectFromMappingOrContent)
                                .getName();
            } else if (objectFromMappingOrContent instanceof ConceptPath) {
                path = ((ConceptPath) objectFromMappingOrContent).getPath();
            } else {
                log("Unexpected content type: " + objectFromMappingOrContent.getClass()); //$NON-NLS-1$
            }
        }

        return path != null ? path : ""; //$NON-NLS-1$    
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider
     * #isMultiInstance(java.lang.Object)
     */
    @Override
    public boolean isMultiInstance(Object objectInTree) {
        if (objectInTree != null) {
            if (objectInTree instanceof JavaMethodParameter) {
                return isJavaMethodParameterMultiInstance((JavaMethodParameter) objectInTree);
            } else if (objectInTree instanceof IScriptRelevantData) {
                boolean isMultiInstance = false;
                IScriptRelevantData type = (IScriptRelevantData) objectInTree;
                if (type instanceof IUMLScriptRelevantData) {
                    IUMLScriptRelevantData iumlType =
                            (IUMLScriptRelevantData) type;
                    JsClass jsClass = iumlType.getJsClass();
                    if (jsClass instanceof DefaultPojoJsClass) {
                        JavaMethodParameter containerClass =
                                ((DefaultPojoJsClass) jsClass)
                                        .getContainerClass();
                        isMultiInstance =
                                isJavaMethodParameterMultiInstance(containerClass);
                    } else {
                        isMultiInstance = type.isArray();
                    }
                } else {
                    isMultiInstance = type.isArray();
                }
                return isMultiInstance;
            } else if (!(objectInTree instanceof JavaMethod)) {
                log("Unexpected content type: " + objectInTree.getClass()); //$NON-NLS-1$
            }
        }
        return false;
    }

    private boolean isJavaMethodParameterMultiInstance(
            JavaMethodParameter javaMethodParameter) {
        if (javaMethodParameter != null) {
            if (javaMethodParameter.isArray()) {
                return true;
            } else if (javaMethodParameter.getType() != null
                    && ParameterTypeEnum.CLASS.equals(javaMethodParameter
                            .getType())) {

                if (javaMethodParameter.getClass()
                        .isAssignableFrom(Collection.class)) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider
     * #getMinimumInstances(java.lang.Object)
     */
    @Override
    public int getMinimumInstances(Object objectInTree) {
        if (objectInTree != null) {
            if (objectInTree instanceof JavaMethodParameter) {
                JavaMethodParameter javaMethodParameter =
                        (JavaMethodParameter) objectInTree;

                if (javaMethodParameter.getParent() instanceof JavaMethod) {
                    /*
                     * All method top level parameters are required.
                     */
                    return 1;
                }

                /*
                 * Everything other parameter will be the java bean getter /
                 * setter methods that are optional.
                 */
                return 0;

            } else if (objectInTree instanceof JavaMethod) {
                /*
                 * JavaMethod is artificial but all it's (child) parameters are
                 * required.
                 */
                return 1;

            } else if (!(objectInTree instanceof ScriptInformation)) {
                log("Unexpected content type: " + objectInTree.getClass()); //$NON-NLS-1$
            }
        }

        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider
     * #getMaximumInstances(java.lang.Object)
     */
    @Override
    public int getMaximumInstances(Object objectInTree) {
        if (objectInTree != null) {
            if (objectInTree instanceof JavaMethodParameter) {
                JavaMethodParameter javaMethodParameter =
                        (JavaMethodParameter) objectInTree;

                if (javaMethodParameter.isArray()) {
                    return -1; /* No maximum. */

                } else if (ParameterTypeEnum.CLASS.equals(javaMethodParameter
                        .getType())) {

                    if (javaMethodParameter.getClass()
                            .isAssignableFrom(Collection.class)) {
                        return -1; /* No maximum */
                    }
                }

            } else if (!(objectInTree instanceof ScriptInformation)
                    && !(objectInTree instanceof JavaMethod)) {
                log("Unexpected content type: " + objectInTree.getClass()); //$NON-NLS-1$
            }
        }
        return 1; /* Not an array or list so 1's the max. */
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider
     * #getInstanceIndex(java.lang.Object)
     */
    @Override
    public int getInstanceIndex(Object objectInTree) {
        if (objectInTree != null) {
            if (objectInTree instanceof JavaMethodParameter) {
                JavaMethodParameter javaMethodParameter =
                        (JavaMethodParameter) objectInTree;

                return javaMethodParameter.getIndex();

            } else if (!(objectInTree instanceof ScriptInformation)
                    && !(objectInTree instanceof JavaMethod)) {
                log("Unexpected content type: " + objectInTree.getClass()); //$NON-NLS-1$
            }
        }
        return -1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider
     * #isReadOnlyTarget(java.lang.Object)
     */
    @Override
    public boolean isReadOnlyTarget(Object objectInTree) {
        if (objectInTree != null) {
            if (objectInTree instanceof JavaMethodParameter) {
                return false;

            } else if ((objectInTree instanceof ScriptInformation)) {
                return true;
            } else if ((objectInTree instanceof JavaMethod)) {
                return true;
            } else {
                log("Unexpected content type: " + objectInTree.getClass()); //$NON-NLS-1$
            }
        }

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider
     * #isSimpleTypeContent(java.lang.Object)
     */
    @Override
    public boolean isSimpleTypeContent(Object objectInTree) {
        if (objectInTree != null) {
            if (objectInTree instanceof JavaMethodParameter) {
                JavaMethodParameter javaMethodParameter =
                        (JavaMethodParameter) objectInTree;

                if (!ParameterTypeEnum.CLASS.equals(javaMethodParameter
                        .getType()) && !javaMethodParameter.isArray()) {
                    return true;
                }

            } else if (!(objectInTree instanceof ScriptInformation)
                    && !(objectInTree instanceof JavaMethod)) {
                log("Unexpected content type: " + objectInTree.getClass()); //$NON-NLS-1$
            }
        }

        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider#isNullSimpleContentAllowed(java.lang.Object)
     * 
     * @param objectInTree
     * @return
     */
    @Override
    public boolean isNullSimpleContentAllowed(Object objectInTree) {
        // Do nothing
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider
     * #isArtificalObject(java.lang.Object)
     */
    @Override
    public boolean isArtificialObject(Object objectInTree) {
        if (objectInTree instanceof JavaMethod) {
            /*
             * JavaMethod is artificial but all it's (child) parameters are
             * required.
             */
            return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider
     * #isChoiceObject(java.lang.Object)
     */
    @Override
    public boolean isChoiceObject(Object objectFromMappingOrContent) {
        return false;
    }

    /**
     * @param msg
     */
    private void log(String msg) {
        // XpdResourcesPlugin.getDefault().getLogger().error(msg);
        throw new RuntimeException(msg);
    }

}
