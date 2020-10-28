package com.tibco.xpd.script.model.jscript;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interval;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.internal.impl.ModelImpl;
import org.eclipse.uml2.uml.resource.UMLResource;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.model.Activator;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.DefaultJsAttribute;
import com.tibco.xpd.script.model.client.DefaultJsClass;
import com.tibco.xpd.script.model.client.DefaultJsExpression;
import com.tibco.xpd.script.model.client.DefaultJsExpressionMethod;
import com.tibco.xpd.script.model.client.DefaultJsMethod;
import com.tibco.xpd.script.model.client.DefaultScriptRelevantData;
import com.tibco.xpd.script.model.client.DefaultUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.IMultipleJsClassResolver;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.IScriptRelevantDataFactory;
import com.tibco.xpd.script.model.client.ITypeResolver;
import com.tibco.xpd.script.model.client.IUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.model.client.JsDataType;
import com.tibco.xpd.script.model.client.JsExpression;
import com.tibco.xpd.script.model.client.JsExpressionMethod;
import com.tibco.xpd.script.model.client.JsMethod;
import com.tibco.xpd.script.model.client.JsMethodParam;
import com.tibco.xpd.script.model.client.JsReference;
import com.tibco.xpd.script.model.client.globaldata.CaseJsMethodParam;
import com.tibco.xpd.script.model.client.globaldata.CaseRefJsClass;
import com.tibco.xpd.script.model.client.globaldata.CaseUMLScriptRelevantData;
import com.tibco.xpd.script.model.internal.client.IJsDataType;
import com.tibco.xpd.script.model.internal.client.IJsElementExt;
import com.tibco.xpd.script.model.internal.client.ITypeResolution;
import com.tibco.xpd.script.model.internal.client.ITypeResolverProvider;
import com.tibco.xpd.script.model.internal.client.IUMLElement;
import com.tibco.xpd.script.model.internal.jscript.JsContentAssistIconProvider;

public class JScriptUtils {

    private static Class defaultMultipleClass = null;

    private static Image defaultJavascriptIcon = null;

    private static ResourceSet definitionReaderResourceSet = null;

    private static JsContentAssistIconProvider jsContentAssistIconProvider =
            null;

    private static String[] ClassNamesCollision =
            new String[] { "Object", "Date" };//$NON-NLS-1$//$NON-NLS-2$

    private static Map<String, String> fqnPackageMapping = null;

    public static String CONTEXTLESS = "Contextless"; //$NON-NLS-1$

    public static boolean hasMoreJSChildren(JsExpression jsExpression) {
        boolean hasChildren = false;
        if (jsExpression != null && jsExpression.getNextExpression() != null) {
            hasChildren = true;
        }
        return hasChildren;
    }

    public static boolean multiplicityMatch(JsExpression jsExpression,
            boolean isMultiple) {
        boolean multiplicityMatch = false;
        if (jsExpression != null) {
            if (jsExpression.isArray() == isMultiple) {
                multiplicityMatch = true;
            }
        }
        return multiplicityMatch;
    }

    public static boolean isDataTypeMultiple(JsExpression jsExpression,
            boolean isMultiple) {
        if (jsExpression != null) {
            if (jsExpression.isArray()) {
                return false;
            } else {
                return isMultiple;
            }
        }
        return isMultiple;
    }

    public static boolean isJsExpressionMultiple(JsExpression jsExpression) {
        boolean isMultiple = false;
        if (jsExpression != null) {
            if (jsExpression.isArray()) {
                return true;
            }
        }
        return isMultiple;
    }

    public static JsClass getJsClass(String name, List<JsClass> jsClassList) {
        if (name != null && jsClassList != null) {
            for (Iterator<JsClass> iterator = jsClassList.iterator(); iterator
                    .hasNext();) {
                JsClass theJsClass = iterator.next();
                if (theJsClass != null && theJsClass.getName() != null
                        && theJsClass.getName().equals(name)) {
                    return theJsClass;
                }
            }
        }
        return null;
    }

    public static Class getReturnedClass(JsMethodParam jsMethodParam) {
        if (jsMethodParam != null && jsMethodParam.getUMLParameter() != null
                && jsMethodParam.getUMLParameter().getType() instanceof Class) {
            return (Class) jsMethodParam.getUMLParameter().getType();
        } else if (jsMethodParam instanceof IUMLElement
                && ((IUMLElement) jsMethodParam)
                        .getElement() instanceof Class) {
            return (Class) ((IUMLElement) jsMethodParam).getElement();
        } else if (jsMethodParam instanceof CaseJsMethodParam) {
            return ((CaseJsMethodParam) jsMethodParam).getUmlClass();
        }
        return null;
    }

    public static JsAttribute getJsAttribute(String name,
            List<JsAttribute> jsAttributeList) {
        if (name != null && jsAttributeList != null) {
            for (Iterator<JsAttribute> iterator =
                    jsAttributeList.iterator(); iterator.hasNext();) {
                JsAttribute theJsAttribute = iterator.next();
                if (theJsAttribute != null && theJsAttribute.getName() != null
                        && theJsAttribute.getName().equals(name)) {
                    return theJsAttribute;
                }
            }
        }
        return null;
    }

    public static boolean getNumberValue(String value) {
        try {
            double aDouble = Double.parseDouble(value);
            return true;
        } catch (Exception e) {
            // Do nothing, this just means that it is not a number
        }
        return false;
    }

    public static boolean isDecimal(String value) {
        if (isNumber(value) && value.contains(".")) {////$NON-NLS-1$
            return true;
        }
        return false;
    }

    public static boolean isNumber(String value) {
        try {
            double aDouble = Double.parseDouble(value);
            return true;
        } catch (Exception e) {
            // Do nothing, this just means that it is not a number
        }
        return false;
    }

    public static boolean isValidIndexType(String dataType) {
        if (dataType != null) {
            if (dataType.equals(JsConsts.INTEGER)
                    || dataType.equals(JsConsts.NUMBER)) {
                return true;
            }
        }
        return false;

    }

    public static IScriptRelevantData getIdentifierScriptRelevantDataType(
            String identifierName, List<JsClass> supportedJsClasses,
            List<JsAttribute> supportedGlobalProperties,
            Map<String, IScriptRelevantData> scriptRelevantDataMap,
            Map<String, IScriptRelevantData> localVariablesMap,
            Map<String, IScriptRelevantData> localMethodsMap,
            IScriptRelevantDataFactory scriptRelevantDataFactory,
            IScriptRelevantData genericContext) {
        IScriptRelevantData type = null;
        if (identifierName != null && scriptRelevantDataFactory != null) {
            if (JScriptUtils.isASupportedClass(identifierName,
                    supportedJsClasses)) {
                // Is a supported class
                JsClass jsClass = JScriptUtils.getJsClass(identifierName,
                        supportedJsClasses);
                // Check not needed but just to be on the safe side
                if (jsClass != null) {
                    boolean readOnly = false;
                    if (jsClass instanceof IJsElementExt) {
                        readOnly = ((IJsElementExt) jsClass).isReadOnly();
                    }
                    return scriptRelevantDataFactory
                            .createUMLScriptRelevantData(jsClass.getName(),
                                    jsClass.getName(),
                                    false,
                                    jsClass,
                                    genericContext,
                                    readOnly,
                                    null);
                }
            } else if (JScriptUtils.isAScriptRelevantData(identifierName,
                    scriptRelevantDataMap)) {
                // Is a scriptRelevantData class
                return scriptRelevantDataMap.get(identifierName);
            } else if (JScriptUtils.isALocalVariable(identifierName,
                    localVariablesMap)) {
                // Is a local variable
                return localVariablesMap.get(identifierName);
            } else if (localMethodsMap != null
                    && localMethodsMap.containsKey(identifierName)) {
                return localMethodsMap.get(identifierName);
            } else if (JScriptUtils.isASupportedGlobalProperty(identifierName,
                    supportedGlobalProperties)) {
                // Is a supported global property
                JsAttribute jsAttribute =
                        JScriptUtils.getJsAttribute(identifierName,
                                supportedGlobalProperties);
                if (jsAttribute != null) {
                    String dataType = JScriptUtils
                            .getJsAttributeBaseDataType(jsAttribute);
                    boolean isReadOnly =
                            JScriptUtils.isJsAttributeReadOnly(jsAttribute);
                    return scriptRelevantDataFactory.createScriptRelevantData(
                            jsAttribute.getName(),
                            dataType,
                            false,
                            genericContext,
                            isReadOnly,
                            jsAttribute);
                }
            }
        }
        return type;
    }

    public static IScriptRelevantData setReadOnly(IScriptRelevantData type,
            boolean isReadOnly) {
        if (type instanceof ITypeResolution) {
            ((ITypeResolution) type).setReadOnly(isReadOnly);
        }
        return type;
    }

    // public static IScriptRelevantData setAdditionalInfoLabelProvider(
    // IScriptRelevantData type,
    // IAdditionalInfoLabelProvider additionalInfoLabelProvider) {
    // if (type instanceof ITypeResolution) {
    // ((ITypeResolution) type)
    // .setAdditionalInfoLabelProvider(additionalInfoLabelProvider);
    // }
    // return type;
    // }

    public static JsDataType getScriptRelevantDataType(
            JsExpression jsExpression, List<JsClass> supportedJsClasses,
            Map<String, IScriptRelevantData> scriptRelevantDataMap,
            Map<String, IScriptRelevantData> localVariablesMap,
            Map<String, IScriptRelevantData> localMethodsMap) {
        JsDataType dataType = new JsDataType();
        dataType.setJsExpression(jsExpression);
        String name = jsExpression.getName();
        if (name != null) {
            if (JScriptUtils.isASupportedClass(name, supportedJsClasses)) {
                // Is a supported class
                JsClass jsClass =
                        JScriptUtils.getJsClass(name, supportedJsClasses);
                // Check not needed but just to be on the safe side
                if (jsClass != null) {
                    dataType = jsClass.getDataTypeForJSExpression(jsExpression,
                            supportedJsClasses);
                }
            } else if (JScriptUtils.isAScriptRelevantData(name,
                    scriptRelevantDataMap)) {
                // Is a scriptRelevantData class
                IScriptRelevantData scriptRelevantData =
                        scriptRelevantDataMap.get(name);
                dataType = JScriptUtils.getDataType(scriptRelevantData,
                        jsExpression,
                        supportedJsClasses);
            } else if (JScriptUtils.isALocalVariable(name, localVariablesMap)) {
                // Is a local variable
                IScriptRelevantData localVariable = localVariablesMap.get(name);
                dataType = JScriptUtils.getDataType(localVariable,
                        jsExpression,
                        supportedJsClasses);
            } else {
                // Treat Special cases like "blahblah".substring(0,4);
                if (isLiteralString(jsExpression.getName())) {
                    // Change the literal name for String
                    jsExpression.setName(JsConsts.STRING);
                    dataType = getScriptRelevantDataType(jsExpression,
                            supportedJsClasses,
                            scriptRelevantDataMap,
                            localVariablesMap,
                            localMethodsMap);
                } else if (isLiteralBoolean(jsExpression.getName())) {
                    // Change the literal name for boolean
                    jsExpression.setName(JsConsts.BOOLEAN);
                    dataType = getScriptRelevantDataType(jsExpression,
                            supportedJsClasses,
                            scriptRelevantDataMap,
                            localVariablesMap,
                            localMethodsMap);
                } else if (isNumber(jsExpression.getName())) {
                    jsExpression.setName(JsConsts.NUMBER);
                    // Change the literal name for number
                    dataType = getScriptRelevantDataType(jsExpression,
                            supportedJsClasses,
                            scriptRelevantDataMap,
                            localVariablesMap,
                            localMethodsMap);
                } else {
                    if (localMethodsMap != null && localMethodsMap
                            .containsKey(jsExpression.getName())) {
                        IScriptRelevantData methodType =
                                localMethodsMap.get(jsExpression.getName());
                        if (methodType != null) {
                            jsExpression.setName(
                                    JScriptUtils.resolveJavaScriptDataType(
                                            methodType.getType()));
                            dataType = getScriptRelevantDataType(jsExpression,
                                    supportedJsClasses,
                                    scriptRelevantDataMap,
                                    localVariablesMap,
                                    localMethodsMap);
                        }
                    } else
                        dataType.setUndefinedCause(
                                JsConsts.UNDEFINED_VARIABLE_DATA_TYPE_CAUSE);
                }
            }

        }
        return dataType;
    }

    public static String getLiteralStringContent(String expression) {
        String literalContent = ""; //$NON-NLS-1$
        if (isLiteralString(expression)) {
            String stringContent =
                    expression.substring(1, expression.length() - 1);
            if (stringContent != null) {
                return stringContent;
            }
        }
        return literalContent;
    }

    public static boolean isLiteralNull(IScriptRelevantData type) {
        if (type != null && type.getType() != null
                && type.getType().equals(JsConsts.NULL)) {
            return true;
        }
        return false;
    }

    public static boolean isLiteralBoolean(String expression) {
        if (expression != null
                && (expression.equals("true") || expression.equals("false"))) {//$NON-NLS-1$//$NON-NLS-2$
            return true;
        }
        return false;
    }

    public static boolean isLiteralString(String expression) {
        boolean isLiteralString = false;
        if (expression != null) {
            if (expression.length() > 1) {
                // get the first character
                String first = expression.substring(0, 1);
                // get the last character
                String last = expression.substring(expression.length() - 1,
                        expression.length());
                if (first != null && last != null
                        && (first.equals("\"") || first.equals("'")) //$NON-NLS-1$ //$NON-NLS-2$
                        && (last.equals("\"") || last.equals("'"))) { //$NON-NLS-1$ //$NON-NLS-2$
                    String stringContent =
                            expression.substring(1, expression.length() - 1);
                    if (stringContent != null) {
                        stringContent = stringContent.replaceAll("\\\"", ""); //$NON-NLS-1$ //$NON-NLS-2$
                        stringContent = stringContent.replaceAll("'", ""); //$NON-NLS-1$ //$NON-NLS-2$
                        if (stringContent != null
                                && (stringContent.contains("\"") //$NON-NLS-1$
                                        || stringContent.contains("'"))) { //$NON-NLS-1$
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        return true;
                    }
                }
            }
        }
        return isLiteralString;
    }

    private static IScriptRelevantData resolveStringType(String name,
            String type, boolean isArray, List<JsClass> jsClasses,
            Class multipleClass) {
        IScriptRelevantData scriptRelevantData =
                new DefaultScriptRelevantData(name, type, isArray);
        if (jsClasses != null) {
            for (Iterator<JsClass> iterator = jsClasses.iterator(); iterator
                    .hasNext();) {
                JsClass jsClass = iterator.next();
                if (jsClass != null && jsClass.getName() != null
                        && jsClass.getName().equals(type)) {
                    if (multipleClass != null
                            && jsClass instanceof IMultipleJsClassResolver) {
                        // Create a new jsClass with the correct multipleClass
                        IMultipleJsClassResolver multipleJsClassResolver =
                                (IMultipleJsClassResolver) jsClass;
                        Class existingMultipleClass =
                                multipleJsClassResolver.getMultipleClass();
                        if (existingMultipleClass == null
                                || !existingMultipleClass
                                        .equals(multipleClass)) {
                            try {
                                Object copy = multipleJsClassResolver.clone();
                                if (copy instanceof IMultipleJsClassResolver) {
                                    IMultipleJsClassResolver multipleJsClassResolverCopy =
                                            (IMultipleJsClassResolver) copy;
                                    multipleJsClassResolverCopy
                                            .setMultipleClass(multipleClass);
                                    if (copy instanceof JsClass) {
                                        IUMLScriptRelevantData umlScriptRelevantData =
                                                (IUMLScriptRelevantData) getScriptRelevantData(
                                                        (JsClass) copy,
                                                        name,
                                                        isArray);
                                        return umlScriptRelevantData;
                                    }
                                }
                            } catch (CloneNotSupportedException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    IUMLScriptRelevantData umlScriptRelevantData =
                            (IUMLScriptRelevantData) getScriptRelevantData(
                                    jsClass,
                                    name,
                                    isArray);
                    if (umlScriptRelevantData instanceof ITypeResolution) {
                        ITypeResolution typeResolution = (ITypeResolution) umlScriptRelevantData;
                        typeResolution.setExtendedInfo(jsClass);
                    }
                    return umlScriptRelevantData;
                }
            }
        }
        return scriptRelevantData;
    }

    public static IScriptRelevantData resolveJavaScriptNotMultipleArrayType(
            String name, boolean isArray, List<JsClass> jsClasses) {
        return resolveJavaScriptNotMultipleArrayType(name,
                isArray,
                jsClasses,
                null);
    }

    public static IScriptRelevantData resolveJavaScriptNotMultipleArrayType(
            String name, boolean isArray, List<JsClass> jsClasses,
            Class multipleClass) {
        return resolveStringType(name,
                JsConsts.OBJECT,
                isArray,
                jsClasses,
                multipleClass);
    }

    public static IScriptRelevantData resolveJavaScriptStringType(String name,
            String type, boolean isArray, List<JsClass> jsClasses) {
        return resolveJavaScriptStringType(name,
                type,
                isArray,
                jsClasses,
                null,
                null);
    }

    public static IScriptRelevantData resolveJavaScriptStringType(String name,
            String type, boolean isArray, List<JsClass> jsClasses,
            Class multipleClass, Map<String, String> typeMap) {
        if (type != null) {
            type = resolveJavaScriptDataType(type, typeMap);
        }
        return resolveStringType(name, type, isArray, jsClasses, multipleClass);
    }

    private static Map<String, String> javaScriptTypeMap =
            new HashMap<String, String>();
    static {
        javaScriptTypeMap.put(JsConsts.CHARACTER, JsConsts.STRING);
        javaScriptTypeMap.put(JsConsts.STRINGLC, JsConsts.STRING);
        javaScriptTypeMap.put(JsConsts.TEXT, JsConsts.STRING);
        javaScriptTypeMap.put(JsConsts.CHAR, JsConsts.STRING);
        javaScriptTypeMap.put(JsConsts.INTEGER, JsConsts.NUMBER);
        javaScriptTypeMap.put(JsConsts.INT, JsConsts.NUMBER);
        javaScriptTypeMap.put(JsConsts.FLOAT, JsConsts.NUMBER);
        javaScriptTypeMap.put(JsConsts.PFLOAT, JsConsts.NUMBER);
        javaScriptTypeMap.put(JsConsts.DOUBLE, JsConsts.NUMBER);
        javaScriptTypeMap.put(JsConsts.PDOUBLE, JsConsts.NUMBER);
        javaScriptTypeMap.put(JsConsts.LONG, JsConsts.NUMBER);
        javaScriptTypeMap.put(JsConsts.PLONG, JsConsts.NUMBER);
        javaScriptTypeMap.put(JsConsts.SHORT, JsConsts.NUMBER);
        javaScriptTypeMap.put(JsConsts.DECIMAL, JsConsts.NUMBER);
        javaScriptTypeMap.put(JsConsts.BYTE, JsConsts.UNDEFINED_DATA_TYPE);
        javaScriptTypeMap.put(JsConsts.PBYTE, JsConsts.UNDEFINED_DATA_TYPE);
    }

    public static String resolveJavaScriptDataType(String actualDataType) {
        return JScriptUtils.resolveJavaScriptDataType(actualDataType, null);
    }

    public static String resolveJavaScriptDataType(String actualDataType,
            Map<String, String> typeMap) {
        if (typeMap == null) {
            typeMap = getDefaultJavaScriptTypeMap();
        }
        String toReturn = typeMap.get(actualDataType);
        if (toReturn == null) {
            toReturn = actualDataType;
        }
        return toReturn;
    }

    public static JsDataType getDataType(IScriptRelevantData scriptRelevantData,
            JsExpression jsExpression, List<JsClass> supportedJsClasses) {
        JsDataType dataType = new JsDataType();
        dataType.setJsExpression(jsExpression);
        if (scriptRelevantData != null) {
            if (scriptRelevantData instanceof IUMLScriptRelevantData) {
                IUMLScriptRelevantData umlScriptRelevantData =
                        (IUMLScriptRelevantData) scriptRelevantData;
                JsClass jsClass = umlScriptRelevantData.getJsClass();
                if (jsClass != null) {
                    if (jsExpression != null) {
                        // Script Editor enhancements for TIBCO Forms
                        // If array, arryExpression will be there, this change
                        // is to support expressions like getControls().control1
                        if (!JScriptUtils.multiplicityMatch(jsExpression,
                                umlScriptRelevantData.isArray())
                                && (jsExpression.getArrayExpression() != null ||
                                // this check is for the studio scripts to work
                                // as before
                                        (umlScriptRelevantData instanceof DefaultUMLScriptRelevantData))) {
                            if (umlScriptRelevantData.isArray()) {
                                String arrayClassName = JsConsts.ARRAY;
                                Class multipleClass = null;
                                if (jsClass instanceof IMultipleJsClassResolver) {
                                    IMultipleJsClassResolver multipleClassResolver =
                                            (IMultipleJsClassResolver) jsClass;
                                    if (multipleClassResolver
                                            .getMultipleJsClass() != null
                                            && multipleClassResolver
                                                    .getMultipleJsClass()
                                                    .getName() != null) {
                                        arrayClassName = multipleClassResolver
                                                .getMultipleJsClass().getName();
                                        multipleClass = multipleClassResolver
                                                .getMultipleClass();
                                    }
                                }
                                scriptRelevantData = JScriptUtils
                                        .resolveJavaScriptStringType(
                                                scriptRelevantData.getName(),
                                                arrayClassName,
                                                true,
                                                supportedJsClasses,
                                                multipleClass,
                                                null);
                                if (scriptRelevantData != null
                                        && scriptRelevantData instanceof IUMLScriptRelevantData) {
                                    IUMLScriptRelevantData umlRelevantData =
                                            (IUMLScriptRelevantData) scriptRelevantData;
                                    jsClass = umlRelevantData.getJsClass();
                                }
                            } else {
                                dataType.setUndefinedCause(
                                        JsConsts.ARRAY_NOT_EXPECTED_DATA_TYPE_CAUSE);
                                return dataType;
                            }
                        }
                        dataType =
                                jsClass.getDataTypeForJSExpression(jsExpression,
                                        supportedJsClasses);
                    }
                }
            } else {
                String type = scriptRelevantData.getType();
                if (type != null && type.equals(JsConsts.UNDEFINED_DATA_TYPE)) {
                    dataType.setUndefinedCause(
                            JsConsts.UNDEFINED_DATA_TYPE_CAUSE);
                } else {
                    if (!JScriptUtils.hasMoreJSChildren(jsExpression)) {
                        if (scriptRelevantData.getType() != null) {
                            dataType.setType(scriptRelevantData);
                        }
                    } else {
                        JsExpression nextExpression =
                                jsExpression.getNextExpression();
                        dataType.setJsExpression(nextExpression);
                        if (nextExpression instanceof JsExpressionMethod) {
                            dataType.setUndefinedCause(
                                    JsConsts.UNKNOWN_METHOD_DATA_TYPE_CAUSE);
                        } else {
                            dataType.setUndefinedCause(
                                    JsConsts.UNKNOWN_PROPERTY_DATA_TYPE_CAUSE);
                        }
                    }
                }
            }
        } else {
            dataType.setUndefinedCause(
                    JsConsts.UNDEFINED_VARIABLE_DATA_TYPE_CAUSE);
        }
        return dataType;
    }

    /**
     * @param name
     * @param jsClasses
     * @return true if the given name is a contributed static class.
     */
    public static boolean isASupportedClass(String name,
            List<JsClass> jsClasses) {
        if (jsClasses != null) {
            JsClass jsClass = JScriptUtils.getJsClass(name, jsClasses);
            if (jsClass != null) {
                return true;
            }
        }

        return false;
    }

    public static boolean isEObjectType(IScriptRelevantData type) {
        if (type != null) {
            IScriptRelevantData resolvedType =
                    JScriptUtils.resolveGenericType(type);
            if (resolvedType != null && resolvedType.getType() != null
                    && resolvedType.getType().equals(JsConsts.BOM_CLASS)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAnonymousComplexType(IScriptRelevantData type) {

        IUMLScriptRelevantData umlScriptRelevantData = null;

        if (type instanceof IUMLScriptRelevantData) {
            umlScriptRelevantData = (IUMLScriptRelevantData) type;
        } else if (type instanceof DefaultScriptRelevantData) {
            DefaultScriptRelevantData defaultScriptRelevantData =
                    (DefaultScriptRelevantData) type;
            if (defaultScriptRelevantData
                    .getGenericContextType() instanceof IUMLScriptRelevantData) {
                umlScriptRelevantData =
                        (IUMLScriptRelevantData) defaultScriptRelevantData
                                .getGenericContextType();
            }
        }

        if (null != umlScriptRelevantData) {
            JsClass jsClass = umlScriptRelevantData.getJsClass();
            if (jsClass != null && jsClass.getUmlClass() != null) {
                Iterator<Stereotype> stereoTypeIter = jsClass.getUmlClass()
                        .getAppliedStereotypes().iterator();
                while (stereoTypeIter.hasNext()) {
                    Stereotype stereotype = stereoTypeIter.next();
                    if (stereotype.getName().equals(XSD_BASED_CLASS)) {
                        Object value = jsClass.getUmlClass()
                                .getValue(stereotype, XSD_ANON_TYPE);
                        if (value != null) {
                            return Boolean.valueOf(value.toString());
                        }
                    }
                }
            }
        }
        return false;
    }

    // public static boolean isFactoryScriptRelevantData(IScriptRelevantData
    // type){
    // if (type != null && type instanceof IUMLScriptRelevantData) {
    // IUMLScriptRelevantData umlScriptRelevantData =
    // (IUMLScriptRelevantData) type;
    // JsClass jsClass = umlScriptRelevantData.getJsClass();
    // if (jsClass instanceof DefaultJsClass) {
    // return ((DefaultJsClass) jsClass).isFactoryJsClass();
    // }
    // }
    // return false;
    // }

    /**
     * 
     * @param type
     * @return <code>true</code> if the given script relevant data represents a
     *         class from the BOM model <code>false</code> otherwise.
     */
    public static boolean isDynamicComplexType(IScriptRelevantData type) {
        return JScriptUtils.isDynamicComplexType(type,
                new ArrayList<JsClass>());
    }

    /**
     * 
     * @param type
     * @param supportedJsClasses
     * @return <code>true</code> if the given script relevant data represents a
     *         class from the BOM model <code>false</code> otherwise.
     */
    public static boolean isDynamicComplexType(IScriptRelevantData type,
            List<JsClass> supportedJsClasses) {
        if (type != null && type instanceof IUMLScriptRelevantData) {
            IUMLScriptRelevantData umlScriptRelevantData =
                    (IUMLScriptRelevantData) type;
            JsClass jsClass = umlScriptRelevantData.getJsClass();
            if (jsClass != null && jsClass.getUmlClass() != null) {
                WorkingCopy wc = WorkingCopyUtil
                        .getWorkingCopyFor(jsClass.getUmlClass());
                if (wc != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isDynamicComplexType(Classifier umlClass) {
        if (umlClass != null) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(umlClass);
            if (wc != null) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPrimitiveType(IScriptRelevantData type) {
        if (!JScriptUtils.isDynamicComplexType(type)) {
            return true;
        }
        return false;
    }

    public static String getFQType(IScriptRelevantData type) {
        if (type != null) {
            if (type instanceof IScriptRelevantData) {
                IScriptRelevantData scriptRelevantData = type;
                if (scriptRelevantData instanceof DefaultScriptRelevantData) {
                    /*
                     * TODO: remove this code if all types that a text list can
                     * have is to be allowed (to be added) to ID/URI list
                     */
                    if (JsConsts.ID
                            .equalsIgnoreCase(scriptRelevantData.getName())
                            || JsConsts.URI.equalsIgnoreCase(
                                    scriptRelevantData.getName())) {
                        Object extendedInfo =
                                ((DefaultScriptRelevantData) scriptRelevantData)
                                        .getExtendedInfo();
                        if (extendedInfo instanceof DefaultJsAttribute) {
                            Element element =
                                    ((DefaultJsAttribute) extendedInfo)
                                            .getElement();
                            if (element instanceof Property) {
                                Property property = (Property) element;
                                if (!scriptRelevantData.getName()
                                        .equalsIgnoreCase(
                                                property.getType().getName())) {
                                    return scriptRelevantData.getName();
                                }
                                return property.getType().getName();
                            }
                        }
                    }
                }
                /*
                 * XPD-3129 Global Data - CaseUMLScriptRelevantData is used for
                 * Reference Type Global Data which does not have a uml
                 * representation in BOM
                 */
                if (scriptRelevantData instanceof CaseUMLScriptRelevantData) {
                    return ((CaseUMLScriptRelevantData) scriptRelevantData)
                            .getType();

                }
                if (scriptRelevantData instanceof DefaultUMLScriptRelevantData) {

                    JsClass jsClass =
                            ((DefaultUMLScriptRelevantData) scriptRelevantData)
                                    .getJsClass();
                    if (jsClass instanceof CaseRefJsClass) {

                        return ((CaseRefJsClass) jsClass).getType();
                    } else if (jsClass != null
                            && jsClass.getUmlClass() != null) {

                        return JScriptUtils.getFQType(jsClass.getUmlClass());
                    } else if (jsClass instanceof IJsDataType
                            && ((IJsDataType) jsClass).getDataType() != null) {

                        return JScriptUtils.getFQType(
                                ((IJsDataType) jsClass).getDataType());
                    }
                }
            }
            return type.getType();
        }
        return null;
    }

    /***
     * 
     * @param classifier
     * @return returns the fully qualified name of the classifier <br>
     *         or the classifier name
     */
    public static String getFQType(Classifier classifier) {
        if (classifier != null) {
            Package umlPackage = classifier.getPackage();
            if (umlPackage != null
                    && JScriptUtils.isDynamicComplexType(classifier)) {
                String packageQualifier = getPackageQualifier(umlPackage);
                if (packageQualifier != null) {
                    return packageQualifier + "." + classifier.getName();//$NON-NLS-1$
                }
            } else {
                return classifier.getName();
            }
        }
        return null;
    }

    /**
     * Get fully qualified name of the given package.
     * 
     * @param parentPackage
     * @return
     */
    public static String getPackageQualifier(Package bomPackage) {
        String qualifier = bomPackage.getName();

        if (bomPackage.eContainer() instanceof Package) {
            Package parentPackage = (Package) bomPackage.eContainer();
            qualifier = parentPackage.getName() + "." + qualifier; //$NON-NLS-1$
            if (parentPackage.eContainer() instanceof Package) {
                String parent = getPackageQualifier(
                        (Package) parentPackage.eContainer());

                if (parent.length() > 0) {
                    qualifier = parent + "." + qualifier; //$NON-NLS-1$
                }
            }
        }

        return qualifier;
    }

    public static boolean isASupportedGlobalProperty(String name,
            List<JsAttribute> jsGlobalProperties) {
        if (jsGlobalProperties != null) {
            JsAttribute jsAttribute =
                    JScriptUtils.getJsAttribute(name, jsGlobalProperties);
            if (jsAttribute != null) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAScriptRelevantData(String name,
            Map<String, IScriptRelevantData> scriptRelevantDataMap) {
        if (scriptRelevantDataMap != null) {
            IScriptRelevantData scriptRelevantData =
                    scriptRelevantDataMap.get(name);
            if (scriptRelevantData != null) {
                return true;
            }
        }
        return false;
    }

    public static boolean isALocalVariable(String name,
            Map<String, IScriptRelevantData> localVariablesMap) {
        if (localVariablesMap != null && name != null) {
            IScriptRelevantData localVariables = localVariablesMap.get(name);
            if (localVariables != null) {
                return true;
            }
        }
        return false;
    }

    public static boolean isALocalMethod(String name,
            Map<String, IScriptRelevantData> localMethodsMap) {
        if (localMethodsMap != null && name != null) {
            IScriptRelevantData localMethods = localMethodsMap.get(name);
            if (localMethods != null) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAKeyWord(String name, List<String> keyWordList) {
        if (keyWordList != null && name != null) {
            if (keyWordList.contains(name)) {
                return true;
            }
        }
        return false;
    }

    public static int findCloseBracketsPos(String aString, int startIndx,
            Character openBracket, Character closeBracket) {
        int indxCloseBracketsPos = -1;
        if (aString.length() > startIndx) {
            int indxNextOpen = aString.indexOf(openBracket, startIndx);
            int indxNextClose = aString.indexOf(closeBracket, startIndx);
            if (indxNextOpen != -1 && indxNextClose > indxNextOpen) {
                indxCloseBracketsPos = findCloseBracketsPos(aString,
                        indxNextClose + 1,
                        openBracket,
                        closeBracket);
            } else {
                indxCloseBracketsPos = indxNextClose;
            }
        }
        return indxCloseBracketsPos;
    }

    public static boolean isVariableDefined(
            Map<String, IScriptRelevantData> localVariablesMap,
            Map<String, IScriptRelevantData> scriptRelevantDataMap,
            List<String> keyWordList, String varName) {
        if (varName != null) {
            if (isAKeyWord(varName, keyWordList)) {
                return true;
            } else if (isALocalVariable(varName, localVariablesMap)) {
                return true;
            } else if (isAScriptRelevantData(varName, scriptRelevantDataMap)) {
                return true;
            }
        }
        return false;
    }

    public static IScriptRelevantData getVariableType(
            Map<String, IScriptRelevantData> localVariablesMap,
            Map<String, IScriptRelevantData> scriptRelevantDataMap,
            String varName) {
        IScriptRelevantData variableType = new DefaultScriptRelevantData(
                varName, JsConsts.UNDEFINED_DATA_TYPE, false);
        if (varName != null) {
            if (isALocalVariable(varName, localVariablesMap)) {
                variableType = localVariablesMap.get(varName);
            } else if (isAScriptRelevantData(varName, scriptRelevantDataMap)) {
                variableType = scriptRelevantDataMap.get(varName);
            }
        }
        return variableType;
    }

    public static boolean needsQuotes(String name) {
        if (name != null && name.length() > 0
                && (Character.isDigit(name.charAt(0))
                        || (name.indexOf(' ') >= 0))) {
            return true;
        }
        return false;
    }

    public static IScriptRelevantData getScriptRelevantData(JsClass jsClass,
            String variableName, boolean isArray) {
        // Script Editor enhancements for TIBCO Forms
        IScriptRelevantData isrd =
                jsClass.getScriptRelevantData(variableName, isArray);
        if (isrd == null) {

            isrd = new DefaultUMLScriptRelevantData(variableName,
                    jsClass.getName(), isArray, jsClass);
        }
        return isrd;
    }

    /**
     * This method works out the datatype of the passed variableName, it also
     * goes and looks out for other classes which might not present in
     * supportedJsClasses.
     * 
     * @param variableName
     * @param passedJsClass
     * @param supportedJsClasses
     * @param isArray
     * @return
     */
    @SuppressWarnings("unchecked")
    public static IScriptRelevantData evaluateScriptRelevantData(
            String variableName, JsClass passedJsClass,
            List<JsClass> supportedJsClasses, boolean isArray) {
        IScriptRelevantData scriptRelevantData = null;
        String className = passedJsClass.getName();
        if (className != null) {
            if (className.equalsIgnoreCase(JsConsts.INTEGER)
                    || className.equalsIgnoreCase(JsConsts.INT)
                    || className.equalsIgnoreCase(JsConsts.DOUBLE)
                    || className.equalsIgnoreCase(JsConsts.FLOAT)) {
                className = JsConsts.NUMBER;
            }
        }
        // checking it in supported classes
        JsClass foundJsClass = null;
        for (Iterator<JsClass> iterator =
                supportedJsClasses.iterator(); iterator.hasNext();) {
            JsClass jsClass = iterator.next();
            if (jsClass != null && jsClass.getName() != null
                    && jsClass.getName().equals(className)) {
                foundJsClass = jsClass;
            }
        }
        if (foundJsClass != null) {
            // Script Editor enhancements for TIBCO Forms
            // scriptRelevantData = new
            // DefaultUMLScriptRelevantData(variableName,
            // foundJsClass.getName(), isArray, foundJsClass);
            scriptRelevantData =
                    getScriptRelevantData(foundJsClass, variableName, isArray);
            return scriptRelevantData;
        }
        Class referredClass = null;
        Class umlClass = passedJsClass.getUmlClass();
        Image umlClassIcon = passedJsClass.getIcon();
        if (umlClass != null) {
            Package ownerPackage = umlClass.getPackage();
            if (ownerPackage != null) {
                List<PackageableElement> packagedElements =
                        ownerPackage.getPackagedElements();
                if (packagedElements != null) {
                    for (PackageableElement element : packagedElements) {
                        if (element instanceof Class) {
                            // checking whether the class referred is there in
                            // the
                            // package [which should be the owner].
                            if (((Class) element).getName().equals(className)) {
                                referredClass = (Class) element;
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (referredClass != null) {
            Class multipleClass = null;
            if (passedJsClass instanceof IMultipleJsClassResolver) {
                IMultipleJsClassResolver multipleJsClassResolver =
                        (IMultipleJsClassResolver) passedJsClass;
                multipleClass = multipleJsClassResolver.getMultipleClass();
            }
            DefaultJsClass tempJsClass =
                    new DefaultJsClass(referredClass, multipleClass);
            if (umlClassIcon != null) {
                tempJsClass.setIcon(umlClassIcon);
            }
            scriptRelevantData = new DefaultUMLScriptRelevantData(variableName,
                    tempJsClass.getName(), isArray, tempJsClass);
            return scriptRelevantData;
        }
        scriptRelevantData =
                new DefaultScriptRelevantData(variableName, className, isArray);
        return scriptRelevantData;
    }

    /**
     * This method works out the datatype of the passed variableName, it also
     * goes and looks out for other classes which might not present in
     * supportedJsClasses.
     * 
     * @param variableName
     * @param paramType
     * @param supportedJsClasses
     * @param isArray
     * @return
     */
    public static IScriptRelevantData evaluateScriptRelevantData(
            String variableName, Type paramType,
            List<JsClass> supportedJsClasses, boolean isArray) {
        return evaluateScriptRelevantData(variableName,
                paramType,
                supportedJsClasses,
                isArray,
                null);
    }

    /**
     * This method works out the datatype of the passed variableName, it also
     * goes and looks out for other classes which might not present in
     * supportedJsClasses.
     * 
     * @param variableName
     * @param paramType
     * @param supportedJsClasses
     * @param isArray
     * @return
     */
    public static IScriptRelevantData evaluateScriptRelevantData(
            String variableName, Type paramType,
            List<JsClass> supportedJsClasses, boolean isArray,
            Class multipleClass) {
        IScriptRelevantData scriptRelevantData = null;
        String className = paramType.getName();
        if (className != null) {
            if (className.equalsIgnoreCase(JsConsts.INTEGER)
                    || className.equalsIgnoreCase(JsConsts.INT)
                    || className.equalsIgnoreCase(JsConsts.DOUBLE)
                    || className.equalsIgnoreCase(JsConsts.FLOAT)) {
                className = JsConsts.NUMBER;
            }
        }
        // checking it in supported classes
        JsClass foundJsClass = null;
        Image defaultJscriptIcon = null;
        for (Iterator<JsClass> iterator =
                supportedJsClasses.iterator(); iterator.hasNext();) {
            JsClass jsClass = iterator.next();
            if (jsClass != null && jsClass.getName() != null
                    && jsClass.getName().equals(className)) {
                foundJsClass = jsClass;
            }
        }
        if (foundJsClass != null) {
            // Script Editor enhancements for TIBCO Forms
            // scriptRelevantData = new
            // DefaultUMLScriptRelevantData(variableName,
            // foundJsClass.getName(), isArray, foundJsClass);
            scriptRelevantData =
                    getScriptRelevantData(foundJsClass, variableName, isArray);
            return scriptRelevantData;
        }
        Class referredClass = null;
        Element ownerPackage = paramType.getPackage();
        if (ownerPackage != null) {
            EList ownedElements = ownerPackage.getOwnedElements();
            // checking whether the class referred is there in the package
            // [which
            // should be the owner].
            if (ownedElements != null) {
                for (Object ownedElement : ownedElements) {
                    if (ownedElement instanceof Class) {
                        if (((Class) ownedElement).getName()
                                .equals(className)) {
                            referredClass = (Class) ownedElement;
                            break;
                        }
                    }
                }
            }
        }
        if (referredClass != null) {
            DefaultJsClass tempJsClass =
                    new DefaultJsClass(referredClass, multipleClass);
            tempJsClass.setIcon(JScriptUtils.getDefaultJavascriptIcon());
            scriptRelevantData = new DefaultUMLScriptRelevantData(variableName,
                    tempJsClass.getName(), isArray, tempJsClass);
            return scriptRelevantData;
        }
        scriptRelevantData =
                new DefaultScriptRelevantData(variableName, className, isArray);
        return scriptRelevantData;
    }

    public static boolean isValidScriptRelevantName(String name) {
        boolean validName = false;
        if (name != null) {
            if (!hasLeadingNumberInName(name) && !hasSpacesInName(name)) {
                validName = true;
            }
        }
        return validName;

    }

    /**
     * Check if the name starts with a number and raise an issue in that case
     * 
     * @param ipmData
     */
    private static boolean hasLeadingNumberInName(String name) {
        boolean hasLeadingNumber = false;
        if (name != null && name.length() > 0) {
            char firstChar = name.charAt(0);
            if (Character.isDigit(firstChar)) {
                hasLeadingNumber = true;
            }
        }
        return hasLeadingNumber;
    }

    /**
     * Check if the name has a space on it and raise an issue in that case
     * 
     * @param ipmData
     */
    private static boolean hasSpacesInName(String name) {
        boolean hasSpaces = false;
        if (name != null && name.contains(" ")) { //$NON-NLS-1$
            hasSpaces = true;
        }
        return hasSpaces;
    }

    public static JsAttribute getJsAttribute(JsClass jsClass,
            String attributeName) {
        JsAttribute jsAttribute = null;
        if (jsClass != null && attributeName != null) {
            List<JsAttribute> attributeList = jsClass.getAttributeList();
            if (attributeList != null && !attributeList.isEmpty()) {
                for (JsAttribute attribute : attributeList) {
                    if (attribute.getName() != null
                            && attribute.getName().equals(attributeName)) {
                        return attribute;
                    }
                }
            }
        }
        return jsAttribute;
    }

    public static List<JsAttribute> getMatchingJsAttribute(JsClass jsClass,
            String attributeName) {
        List<JsAttribute> jsAttributeList = new ArrayList<JsAttribute>();
        if (jsClass != null && attributeName != null) {
            List<JsAttribute> attributeList = jsClass.getAttributeList();
            if (attributeList != null && !attributeList.isEmpty()) {
                for (JsAttribute attribute : attributeList) {
                    if (attribute.getName() != null
                            && attribute.getName().startsWith(attributeName)) {
                        jsAttributeList.add(attribute);
                    }
                }
            }
        }
        return jsAttributeList;
    }

    public static JsReference getJsReference(JsClass jsClass,
            String referenceName) {
        JsReference jsReference = null;
        if (jsClass != null && referenceName != null) {
            List<JsReference> referenceList = jsClass.getReferenceList();
            if (referenceList != null && !referenceList.isEmpty()) {
                for (JsReference reference : referenceList) {
                    if (reference.getName() != null
                            && reference.getName().equals(referenceName)) {
                        return reference;
                    }
                }
            }
        }
        return jsReference;
    }

    public static List<JsReference> getMatchingJsReference(JsClass jsClass,
            String referenceName) {
        List<JsReference> jsReferenceList = new ArrayList<JsReference>();
        if (jsClass != null && referenceName != null) {
            List<JsReference> referenceList = jsClass.getReferenceList();
            if (referenceList != null && !referenceList.isEmpty()) {
                for (JsReference reference : referenceList) {
                    if (reference.getName() != null
                            && reference.getName().startsWith(referenceName)) {
                        jsReferenceList.add(reference);
                    }
                }
            }
        }
        return jsReferenceList;
    }

    public static Image getDefaultJavascriptIcon() {
        if (defaultJavascriptIcon == null) {
            defaultJavascriptIcon = Activator.getDefault().getImageRegistry()
                    .get(JsConsts.JS_CLASS);
        }
        return defaultJavascriptIcon;
    }

    public static Class getDefaultMultipleClass() {
        if (defaultMultipleClass == null) {
            URL entry = Activator.getDefault().getBundle()
                    .getEntry(JsConsts.JAVASCRIPT_MODEL_FILE_NAME);
            if (entry != null) {
                URI uri = URI.createURI(entry.toExternalForm());
                ResourceSetImpl resourceSet = new ResourceSetImpl();
                resourceSet.getPackageRegistry().put(UMLPackage.eNS_URI,
                        UMLPackage.eINSTANCE);
                resourceSet.getResourceFactoryRegistry()
                        .getExtensionToFactoryMap()
                        .put(UMLResource.FILE_EXTENSION,
                                UMLResource.Factory.INSTANCE);
                try {
                    Resource resource = resourceSet.createResource(uri);
                    resource.load(Collections.EMPTY_MAP);
                    Package umlPackage = (Package) EcoreUtil.getObjectByType(
                            resource.getContents(),
                            UMLPackage.Literals.PACKAGE);
                    List<PackageableElement> packagedElements =
                            umlPackage.getPackagedElements();
                    if (packagedElements == null) {
                        return null;
                    }
                    for (PackageableElement element : packagedElements) {
                        if (element instanceof Class) {
                            Class umlClass = (Class) element;
                            if (umlClass.getName() != null && umlClass.getName()
                                    .equals(JsConsts.ARRAY)) {
                                defaultMultipleClass = umlClass;
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return defaultMultipleClass;
    }

    public static boolean isAnEMethod(JsMethod method) {
        if (method != null && method.getReturnType() != null
                && method.getReturnType().getType() != null
                && method.getReturnType().getType().equals(JsConsts.E)) {
            return true;
        }
        return false;
    }

    public static boolean isObjectSubType(Property prop, String subtype) {

        // Determine the base primitive type. This is one of the BOM base types.
        Type type = prop.getType();
        if (type instanceof PrimitiveType) {

            PrimitiveType pt = (PrimitiveType) type;
            PrimitiveType basePrimitiveType =
                    JScriptUtils.getBasePrimitiveType(pt);

            if (basePrimitiveType != null) {
                ResourceSet rSet = XpdResourcesPlugin.getDefault()
                        .getEditingDomain().getResourceSet();

                // Confirm that the base type is Object
                if (basePrimitiveType == JScriptUtils
                        .getStandardPrimitiveTypeByName(rSet,
                                JsConsts.OBJECT)) {

                    Object value = JScriptUtils.getFacetPropertyValue(pt,
                            JScriptUtils.BOM_PRIMITIVE_FACET_OBJECT_SUBTYPE,
                            prop);

                    if (value != null && value instanceof EnumerationLiteral) {
                        String litName = ((EnumerationLiteral) value).getName();
                        if (litName.equals(subtype)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 
     * @param property
     * @return
     */
    public static String getObjectSubType(Property property) {
        String subType = null;

        // Determine the base primitive type. This is one of the BOM base types.
        Type type = property.getType();

        if (type instanceof PrimitiveType) {
            PrimitiveType pt = (PrimitiveType) type;
            PrimitiveType basePrimitiveType =
                    JScriptUtils.getBasePrimitiveType(pt);

            if (basePrimitiveType != null) {
                ResourceSet rSet = XpdResourcesPlugin.getDefault()
                        .getEditingDomain().getResourceSet();

                // Confirm that the base type is Object
                if (basePrimitiveType == JScriptUtils
                        .getStandardPrimitiveTypeByName(rSet,
                                JsConsts.OBJECT)) {

                    Object value = JScriptUtils.getFacetPropertyValue(pt,
                            JScriptUtils.BOM_PRIMITIVE_FACET_OBJECT_SUBTYPE,
                            property);

                    if (value != null && value instanceof EnumerationLiteral) {
                        String litName = ((EnumerationLiteral) value).getName();

                        if (litName.equals(
                                JScriptUtils.OBJECT_SUBTYPE_XSD_ANYSIMPLETYPE)
                                || litName.equals(
                                        JScriptUtils.OBJECT_SUBTYPE_XSD_ANY)
                                || litName.equals(
                                        JScriptUtils.OBJECT_SUBTYPE_XSD_ANYATTRIBUTE)
                                || litName.equals(
                                        JScriptUtils.OBJECT_SUBTYPE_XSD_ANYTYPE)) {
                            subType = litName;
                        }
                    }
                }
            }
        }
        return subType != null ? subType : ""; //$NON-NLS-1$
    }

    public static boolean isXsdAny(IScriptRelevantData type) {
        if (type != null && type.getType() != null) {
            if (type instanceof ITypeResolution) {
                Object extendedInfo =
                        ((ITypeResolution) type).getExtendedInfo();
                if (extendedInfo instanceof IUMLElement) {
                    Element element = ((IUMLElement) extendedInfo).getElement();
                    if (element instanceof Property) {
                        return isObjectSubType((Property) element,
                                JScriptUtils.OBJECT_SUBTYPE_XSD_ANY);
                    }
                }
            }
        }
        return false;
    }

    public static boolean isXsdAnyType(IScriptRelevantData type) {
        if (type != null && type.getType() != null) {
            if (type instanceof ITypeResolution) {
                Object extendedInfo =
                        ((ITypeResolution) type).getExtendedInfo();
                if (extendedInfo instanceof IUMLElement) {
                    Element element = ((IUMLElement) extendedInfo).getElement();
                    if (element instanceof Property) {
                        return isObjectSubType((Property) element,
                                JScriptUtils.OBJECT_SUBTYPE_XSD_ANYTYPE);
                    }
                }
            }
        }
        return false;
    }

    public static boolean isXsdAnySimpleType(IScriptRelevantData type) {
        if (type != null && type.getType() != null) {
            if (type instanceof ITypeResolution) {
                Object extendedInfo =
                        ((ITypeResolution) type).getExtendedInfo();
                if (extendedInfo instanceof IUMLElement) {
                    Element element = ((IUMLElement) extendedInfo).getElement();
                    if (element instanceof Property) {
                        return isObjectSubType((Property) element,
                                JScriptUtils.OBJECT_SUBTYPE_XSD_ANYSIMPLETYPE);
                    }
                }
            }
        }
        return false;
    }

    public static boolean isXsdAnyAttribute(IScriptRelevantData type) {
        if (type != null && type.getType() != null) {
            if (type instanceof ITypeResolution) {
                Object extendedInfo =
                        ((ITypeResolution) type).getExtendedInfo();
                if (extendedInfo instanceof IUMLElement) {
                    Element element = ((IUMLElement) extendedInfo).getElement();
                    if (element instanceof Property) {
                        return isObjectSubType((Property) element,
                                JScriptUtils.OBJECT_SUBTYPE_XSD_ANYATTRIBUTE);
                    }
                }
            }
        }
        return false;
    }

    public static DataType getDataType(IScriptRelevantData type) {
        if (type instanceof IUMLScriptRelevantData) {
            IUMLScriptRelevantData umlType = (IUMLScriptRelevantData) type;
            JsClass jsClass = umlType.getJsClass();
            if (jsClass instanceof IJsDataType) {
                DataType dataType = ((IJsDataType) jsClass).getDataType();
                if (dataType != null) {
                    return dataType;
                }
            }
        }

        if (type instanceof ITypeResolution) {

            Object extendedInfo = ((ITypeResolution) type).getExtendedInfo();

            if (extendedInfo instanceof IUMLElement) {
                Element element = ((IUMLElement) extendedInfo).getElement();

                if (element instanceof Property) {
                    Property property = (Property) element;
                    Type srcScriptType = property.getType();

                    if (srcScriptType instanceof PrimitiveType) {
                        return (org.eclipse.uml2.uml.DataType) srcScriptType;
                    }
                }
            }
        }
        return null;
    }

    public static ResourceSet getDefinitionReaderResourceSet() {
        if (definitionReaderResourceSet == null) {
            definitionReaderResourceSet = new ResourceSetImpl();
        }
        return definitionReaderResourceSet;
    }

    public static JsExpression cloneJsExpression(JsExpression jsExpression) {
        if (jsExpression != null) {
            if (jsExpression instanceof JsExpressionMethod) {
                DefaultJsExpressionMethod clone =
                        new DefaultJsExpressionMethod();
                clone.setArrayExpression(jsExpression.getArrayExpression());
                clone.setIsArray(jsExpression.isArray());
                clone.setName(jsExpression.getName());
                clone.setNextExpression(jsExpression.getNextExpression());
                clone.setMethodParameterList(((JsExpressionMethod) jsExpression)
                        .getMethodParameterList());
                return clone;
            } else {
                DefaultJsExpression clone = new DefaultJsExpression();
                clone.setArrayExpression(jsExpression.getArrayExpression());
                clone.setIsArray(jsExpression.isArray());
                clone.setName(jsExpression.getName());
                clone.setNextExpression(jsExpression.getNextExpression());
                return clone;
            }
        }
        return null;
    }

    public static JsContentAssistIconProvider getJsContentAssistIconProvider() {
        if (jsContentAssistIconProvider == null) {
            jsContentAssistIconProvider = new JsContentAssistIconProvider();
        }
        return jsContentAssistIconProvider;
    }

    public static Map<String, String> getDefaultJavaScriptTypeMap() {
        return javaScriptTypeMap;
    }

    public static List<JsClass> getAllSupportedJsClasses(
            List<JsClassDefinitionReader> classDefinitionReaders) {
        if (classDefinitionReaders != null
                && !classDefinitionReaders.isEmpty()) {
            List<JsClass> allSupportedClasses = new ArrayList<JsClass>();
            for (JsClassDefinitionReader jsClassDefinitionReader : classDefinitionReaders) {
                List<JsClass> supportedClasses =
                        jsClassDefinitionReader.getSupportedClasses();
                if (supportedClasses != null) {
                    allSupportedClasses.addAll(supportedClasses);
                }
            }
            return allSupportedClasses;
        }
        return Collections.emptyList();
    }

    public static List<IScriptRelevantData> resolveType(
            IScriptRelevantData type, List<ITypeResolver> typeResolvers,
            boolean isMultiple, IScriptRelevantData genericContext) {
        return JScriptUtils.resolveTypeForContext(type,
                typeResolvers,
                isMultiple,
                genericContext);
    }

    public static List<IScriptRelevantData> resolveType(JsAttribute jsAttribute,
            List<ITypeResolver> typeResolvers, boolean isMultiple,
            IScriptRelevantData genericContext) {
        return JScriptUtils.resolveTypeForContext(jsAttribute,
                typeResolvers,
                isMultiple,
                genericContext);
    }

    public static List<IScriptRelevantData> resolveType(JsReference jsReference,
            List<ITypeResolver> typeResolvers, boolean isMultiple,
            IScriptRelevantData genericContext) {
        return JScriptUtils.resolveTypeForContext(jsReference,
                typeResolvers,
                isMultiple,
                genericContext);
    }

    public static List<IScriptRelevantData> resolveType(JsMethod jsMethod,
            List<ITypeResolver> typeResolvers, boolean isMultiple,
            IScriptRelevantData genericContext) {
        return JScriptUtils.resolveTypeForContext(jsMethod,
                typeResolvers,
                isMultiple,
                genericContext);
    }

    private static List<IScriptRelevantData> resolveTypeForContext(
            Object context, List<ITypeResolver> typeResolvers,
            boolean isMultiple, IScriptRelevantData genericContext) {

        if (context != null && typeResolvers != null
                && !typeResolvers.isEmpty()) {
            List<IScriptRelevantData> types =
                    new ArrayList<IScriptRelevantData>();
            for (ITypeResolver typeResolver : typeResolvers) {
                List<IScriptRelevantData> resolvedTypes = typeResolver
                        .resolveType(context, isMultiple, genericContext);
                if (resolvedTypes != null && !resolvedTypes.isEmpty()) {
                    types.addAll(resolvedTypes);
                }
            }
            return types;
        }
        return null;
    }

    public static Set<ITypeResolverProvider> getTypeResolverProviders(
            List<JsClassDefinitionReader> classDefinitionReaders) {
        if (classDefinitionReaders != null
                && !classDefinitionReaders.isEmpty()) {
            Set<ITypeResolverProvider> typeResolverProviders =
                    new HashSet<ITypeResolverProvider>();
            for (JsClassDefinitionReader classDefinitionReader : classDefinitionReaders) {
                if (classDefinitionReader instanceof ITypeResolverProvider) {
                    typeResolverProviders
                            .add((ITypeResolverProvider) classDefinitionReader);
                }
            }
            return typeResolverProviders;
        }
        return Collections.emptySet();
    }

    public static Set<ITypeResolver> getTypeResolvers(
            List<ITypeResolverProvider> typeResolverProviders) {
        if (typeResolverProviders != null && !typeResolverProviders.isEmpty()) {
            Set<ITypeResolver> typeResolvers = new HashSet<ITypeResolver>();
            for (ITypeResolverProvider typeResolverProvider : typeResolverProviders) {
                ITypeResolver typeResolver =
                        typeResolverProvider.getTypeResolver();
                if (typeResolver != null) {
                    typeResolvers.add(typeResolver);
                }
            }
            return typeResolvers;
        }
        return Collections.emptySet();
    }

    public static boolean isGenericType(String type) {
        if (type != null && type.equals(JsConsts.E)) {
            return true;
        }
        return false;
    }

    public static boolean isContextlessType(String type,
            List<JsClass> supportedJsClasses) {
        if (type != null && supportedJsClasses != null) {
            JsClass jsClass = JScriptUtils.getJsClass(type, supportedJsClasses);
            if (jsClass != null && jsClass.getUmlClass() != null
                    && JScriptUtils.isContextlessClass(jsClass.getUmlClass())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isContextlessType(IScriptRelevantData type) {
        if (type instanceof ITypeResolution) {
            return ((ITypeResolution) type).isContextless();
        }
        return false;
    }

    public static IScriptRelevantData getCurrentGenericContext(
            IScriptRelevantData type) {
        if (type instanceof ITypeResolution) {
            return ((ITypeResolution) type).getGenericContextType();
        }
        return null;
    }

    public static boolean isMultipleClass(String className) {
        if (className != null) {
            if (className.equals("Array")) { //$NON-NLS-1$ //$NON-NLS-2$
                return true;
            }
        }
        return false;
    }

    public static boolean isStaticMethod(JsMethod method) {
        boolean isStaticMethod = false;
        if (method instanceof IJsElementExt) {
            isStaticMethod = ((IJsElementExt) method).isStatic();
        }
        return isStaticMethod;
    }

    public static String getContextParameter(JsMethod jsMethod) {
        if (jsMethod instanceof DefaultJsMethod) {
            DefaultJsMethod dJsMethod = (DefaultJsMethod) jsMethod;
            Operation method = dJsMethod.getMethod();
            if (method != null && !method.getEAnnotations().isEmpty()) {
                for (EAnnotation eAnnotation : method.getEAnnotations()) {
                    String source = eAnnotation.getSource();
                    if (source != null
                            && source.startsWith("contextParameter=")) {//$NON-NLS-1$
                        return source.replaceFirst("contextParameter=", "");//$NON-NLS-1$//$NON-NLS-2$
                    }
                }
            }
        }
        return null;
    }

    public static boolean isContextlessClass(Class umlClass) {
        if (umlClass != null) {
            return umlClass.getOwnedRule(CONTEXTLESS) != null;
        }
        return false;
    }

    public static boolean isToString(String text) {
        if (text.equals("toString")) { //$NON-NLS-1$
            return true;
        }
        return false;
    }

    public static boolean isAbstract(JsClass jsClass) {
        if (jsClass != null) {
            Class umlClass = jsClass.getUmlClass();
            if (umlClass != null) {
                return umlClass.isAbstract();
            }
        }
        return false;
    }

    public static boolean containsStaticFeatures(JsClass jsClass) {
        if (jsClass != null) {
            List<JsAttribute> attributeList = jsClass.getAttributeList();
            if (attributeList != null && !attributeList.isEmpty()) {
                for (JsAttribute jsAttribute : attributeList) {
                    if (jsAttribute instanceof IJsElementExt) {
                        if (((IJsElementExt) jsAttribute).isStatic()) {
                            return true;
                        }
                    }
                }
            }
            List<JsMethod> methodList = jsClass.getMethodList();
            if (methodList != null && !methodList.isEmpty()) {
                for (JsMethod jsMethod : methodList) {
                    if (jsMethod instanceof IJsElementExt) {
                        if (((IJsElementExt) jsMethod).isStatic()) {
                            return true;
                        }
                    }
                }
            }
            List<JsReference> referenceList = jsClass.getReferenceList();
            if (referenceList != null && !referenceList.isEmpty()) {
                for (JsReference jsReference : referenceList) {
                    if (jsReference instanceof IJsElementExt) {
                        if (((IJsElementExt) jsReference).isStatic()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean isJsAttributeReadOnly(JsAttribute jsAttribute) {
        if (jsAttribute instanceof IJsElementExt) {
            return ((IJsElementExt) jsAttribute).isReadOnly();
        }
        return false;
    }

    public static String getJsAttributeBaseDataType(JsAttribute jsAttribute) {
        if (jsAttribute instanceof IJsElementExt) {
            return ((IJsElementExt) jsAttribute).getBaseDataType();
        } else if (jsAttribute != null) {
            return jsAttribute.getDataType();
        }
        return null;
    }

    public static String getJsMethodParamBaseDataType(
            JsMethodParam jsMethodParam) {
        if (jsMethodParam instanceof IJsElementExt) {
            return ((IJsElementExt) jsMethodParam).getBaseDataType();
        } else if (jsMethodParam != null) {
            return jsMethodParam.getType();
        }
        return null;
    }

    /**
     * @param primitiveType
     * @return
     */
    public static String getBasePrimitiveDataType(PrimitiveType primitiveType) {
        String baseDataType = JScriptUtils.getUmlName(primitiveType);
        while (primitiveType != null
                && primitiveType.getGeneralizations() != null
                && !primitiveType.getGeneralizations().isEmpty()) {
            Generalization generalization =
                    primitiveType.getGeneralizations().iterator().next();
            primitiveType = null;
            if (generalization != null) {
                Classifier general = generalization.getGeneral();
                if (general instanceof PrimitiveType) {
                    primitiveType = (PrimitiveType) general;
                    baseDataType = primitiveType.getName();
                }
            }
        }
        return baseDataType;
    }

    public static String getJsReferenceBaseDataType(JsReference jsReference) {
        if (jsReference instanceof IJsElementExt) {
            return ((IJsElementExt) jsReference).getBaseDataType();
        } else if (jsReference != null) {
            return jsReference.getDataType();
        }
        return null;
    }

    /**
     * Sid ACE-4683 Get the repeating parameter from the method parameters (if any).
     * 
     * @param jsMethod
     * @return The the repeating parameter from the method parameters, if any, else <code>null</code>
     */
    public static JsMethodParam getRepeatingParameter(JsMethod jsMethod) {
        if (jsMethod != null) {
            List<JsMethodParam> parameterType = jsMethod.getParameterType();
            if (parameterType != null && !parameterType.isEmpty()) {

                /*
                 * Sid ACE-4683 We used to only look a FIRST parameter to see if it was a repeating one. That's no good
                 * for methods like array.splice(n,n,p...). So check ALL the parameters.
                 */
                for (JsMethodParam jsMethodParam : parameterType) {
                    if (isRepeatingParameter(jsMethodParam)) {
                        return jsMethodParam;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Sid ACE-4683 check if given parameter is a repeating-param
     * 
     * @param jsMethodParam
     * 
     * @return <code>true</code> if given parameter is a repeating-param
     */
    public static boolean isRepeatingParameter(JsMethodParam jsMethodParam) {
        if (jsMethodParam != null && jsMethodParam.getUMLParameter() != null) {
            Parameter parameter = jsMethodParam.getUMLParameter();
            ValueSpecification upperValue = parameter.getUpperValue();
            if (upperValue instanceof Interval) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasRepeatingInputParameters(JsMethod jsMethod) {
        /*
         * Sid ACE-4683 We used to only look a FIRST parameter to see if it was a repeating one. That's no good for
         * methods like array.splice(n,n,p...). So check ALL the parameters.
         */
        return getRepeatingParameter(jsMethod) != null;
    }

    public static int getUpperMaxRepeatingInputParameters(JsMethod jsMethod) {
        int upperMaxRepeatingParams = 0;
        if (jsMethod != null) {
            /* Get the repeating parameter no matter WHICH param that is - DON'T assume it's always the first one! */
            JsMethodParam jsMethodParam = getRepeatingParameter(jsMethod);
            if (jsMethodParam != null && jsMethodParam.getUMLParameter() != null) {
                Parameter parameter = jsMethodParam.getUMLParameter();
                ValueSpecification upperValue = parameter.getUpperValue();
                if (upperValue instanceof Interval) {
                    Interval upperInterval = (Interval) upperValue;
                    String name = upperInterval.getName();
                    if (name != null) {
                        if (name.equals("*")) {//$NON-NLS-1$
                            return -1;
                        } else {
                            try {
                                int upperIntervalInt = Integer.parseInt(name);
                                upperMaxRepeatingParams = upperMaxRepeatingParams + upperIntervalInt;
                            } catch (NumberFormatException e) {

                            }
                        }
                    }
                }
            }

        }
        return upperMaxRepeatingParams;
    }

    public static int getLowerRepeatingInputParameters(JsMethod jsMethod) {
        int lowerRepeatingParams = 0;
        if (jsMethod != null) {
            /* Get the repeating parameter no matter WHICH param that is - DON'T assume it's always the first one! */
            JsMethodParam jsMethodParam = getRepeatingParameter(jsMethod);
            if (jsMethodParam != null && jsMethodParam.getUMLParameter() != null) {
                Parameter parameter = jsMethodParam.getUMLParameter();
                ValueSpecification lowerValue = parameter.getLowerValue();
                if (lowerValue instanceof Interval) {
                    Interval lowerInterval = (Interval) lowerValue;
                    String name = lowerInterval.getName();
                    if (name != null) {
                        try {
                            int lowerIntervalInt = Integer.parseInt(name);
                            lowerRepeatingParams = lowerRepeatingParams + lowerIntervalInt;
                        } catch (NumberFormatException e) {

                        }
                    }
                }
            }
        }
        return lowerRepeatingParams;
    }

    // public static boolean isPrimitiveType(JsAttribute jsAttribute){
    // if(getUmlType(jsAttribute) != null){
    // return true;
    // }
    // return false;
    // }

    public static String getUmlType(Type type) {
        if (type != null && type.getName() != null) {
            String name = type.getName();
            EObject container = type.eContainer();
            if (container instanceof ModelImpl) {
                return JScriptUtils.getFQN(name,
                        ((ModelImpl) container).getName());
            }
            return name;
        }
        return null;
    }

    public static String getUmlName(Class umlClass) {
        if (umlClass != null && umlClass.getName() != null) {
            String name = umlClass.getName();
            EObject container = umlClass.eContainer();
            if (container instanceof ModelImpl) {
                return JScriptUtils.getFQN(name,
                        ((ModelImpl) container).getName());
            }
            return name;
        }
        return null;
    }

    public static String getUmlName(DataType umlDataType) {
        if (umlDataType != null && umlDataType.getName() != null) {
            String name = umlDataType.getName();
            EObject container = umlDataType.eContainer();
            if (container instanceof ModelImpl) {
                return JScriptUtils.getFQN(name,
                        ((ModelImpl) container).getName());
            }
            return name;
        }
        return null;
    }

    /**
     * Gets the JavaScript UML element's content assist comment.
     * <p>
     * Nominally this will be picked up from the associated .properties file
     * matching the UML file name, using a message key that matches the
     * qualified UML name of the UML element. ("Package::Class::Propterty) with
     * : replaced by underscore.
     * <p>
     * For Operations we may have more than one operation with same name (i.e.
     * overloaded methods) - in which case the standard mechanism for looking
     * for resource bundle .properties file message matching the qualified name
     * for the element could be ambiguous.
     * 
     * So we have added a special clause whereby, if we don't specify the
     * message and key according to just the method name in the properties file
     * BUT DO specify the key as
     * 
     * - <qualified methodname>__paramTypeName__paramTypeName
     * 
     * Then we will use that. The parameter type names INCLUDE return paramter
     * AND must be specified in the message key in the same order as declared in
     * the UML
     * 
     * For List / Array type parameters append "[]" to the parameter type name.
     * </p>
     * <p>
     * Finally, if all else fails, we fall back on any defined "comment"
     * elements under the given UML element.
     * 
     * @param element
     * 
     * @return The JavaScript UML element's content assist comment
     */
    public static String getUmlElementComment(NamedElement element) {
        String toReturn;

        /*
         * Look at element label alone, UML will load from resource bundle
         * .properties file for a message matching the qualified name of the UML
         * element.
         */
        toReturn = element.getLabel();

        if (toReturn == null || toReturn.equals(element.getName())
                && (element instanceof Operation)) {
            /*
             * For Operations we may have more than one operation with same name
             * (i.e. overloaded methods) - in which case the standard mechanism
             * for looking for resource bundle .properties file message matching
             * the qualified name for the element could be ambiguous.
             * 
             * So we have added a special clause whereby, if we don't specify
             * the message and key according to just the method name in the
             * properties file BUT DO specify the key as
             * 
             * <qualified methodname>__paramTypeName__paramTypeName
             * 
             * Then we will use that. The parameter type names INCLUDE return
             * paramter AND must be specified in the message key in the same
             * order as declared in the UML
             * 
             * For List / Array type parameters append "[]" to the parameter
             * type name.
             */
            Operation operation = (Operation) element;

            if (operation.getQualifiedName() != null
                    && !operation.getOwnedParameters().isEmpty()) {
                String msgKey = "_label_" + //$NON-NLS-1$
                        MyUML2Util.getValidJavaIdentifier(
                                operation.getQualifiedName().replace(':', '_'));

                /*
                 * Tag on each parameter type name in turn (including return
                 * parameters).
                 */
                for (Parameter parameter : operation.getOwnedParameters()) {
                    if (parameter.getType() != null) {
                        msgKey += "__" + parameter.getType().getName(); //$NON-NLS-1$

                        if (parameter.getUpper() != 1) {
                            msgKey += "[]"; //$NON-NLS-1$
                        }
                    }
                }

                toReturn = MyUML2Util.myGetString(operation, msgKey, "", true); //$NON-NLS-1$

                if (toReturn != null && toReturn.length() > 0) {
                    return toReturn;
                }
            }

        }

        /*
         * If the default name is returned (it means that no message property
         * replacement was found) then look for an explicitly defined comment in
         * the UML.
         */
        if (toReturn == null || toReturn.equals(element.getName())) {
            EList<Comment> ownedComments = element.getOwnedComments();
            if (ownedComments != null && !ownedComments.isEmpty()) {
                toReturn = "";//$NON-NLS-1$
                for (Comment comment : ownedComments) {
                    String body = comment.getBody();
                    if (body != null) {
                        toReturn += body;
                    }
                }
            }
        }

        return toReturn;
    }

    private static String getFQN(String name, String containerName) {
        if (fqnPackageMapping == null) {
            fqnPackageMapping = new HashMap<String, String>();
            fqnPackageMapping.put("BomPrimitiveTypes", "bom."); //$NON-NLS-1$ //$NON-NLS-2$
        }
        if (name != null && containerName != null
                && JScriptUtils.isClassNameCollision(name)) {
            String fqnPackage = fqnPackageMapping.get(containerName);
            if (fqnPackage != null) {
                return fqnPackage + name;
            } else {
                return name;
            }
        }
        return name;
    }

    private static boolean isClassNameCollision(String className) {
        if (className != null) {
            for (String classNameCollision : ClassNamesCollision) {
                if (classNameCollision.equals(className)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isSubType(IScriptRelevantData subType,
            IScriptRelevantData type) {
        IScriptRelevantData resolvedSubType =
                JScriptUtils.resolveGenericType(subType);
        IScriptRelevantData resolvedType =
                JScriptUtils.resolveGenericType(type);
        if (resolvedSubType instanceof IUMLScriptRelevantData
                && resolvedType instanceof IUMLScriptRelevantData) {
            JsClass subTypeJsClass =
                    ((IUMLScriptRelevantData) resolvedSubType).getJsClass();
            JsClass typeJsClass =
                    ((IUMLScriptRelevantData) resolvedType).getJsClass();
            if (subTypeJsClass != null && subTypeJsClass.getUmlClass() != null
                    && typeJsClass != null
                    && typeJsClass.getUmlClass() != null) {
                Class subTypeUmlClass = subTypeJsClass.getUmlClass();
                Class typeUmlClass = typeJsClass.getUmlClass();
                return JScriptUtils.isSubType(subTypeUmlClass, typeUmlClass);
            }
        }
        return false;
    }

    /**
     * 
     * @param subType
     * @param type
     * @return <code>true</code> if 1st param (subType) is sub-type of 2nd param
     *         (type)
     */
    public static boolean isSubType(Class subType, Class type) {
        if (subType != null && type != null) {
            List<Classifier> generals = new ArrayList<Classifier>();
            addGenerals(subType, generals);
            if (generals != null && generals.contains(type)) {
                return true;
            }
        }
        return false;
    }

    private static void addGenerals(Classifier type,
            List<Classifier> allGenerals) {
        if (allGenerals != null) {
            EList<Classifier> typeGenerals = type.getGenerals();
            if (typeGenerals != null && !typeGenerals.isEmpty()) {
                for (Classifier typeGeneral : typeGenerals) {
                    if (!allGenerals.contains(typeGeneral)) {
                        allGenerals.add(typeGeneral);
                        addGenerals(typeGeneral, allGenerals);
                    }
                }
            }
        }
    }

    public static IScriptRelevantData resolveGenericType(
            IScriptRelevantData dataType) {
        if (dataType != null && dataType.getType() != null
                && JScriptUtils.isGenericType(dataType.getType())) {
            if (dataType instanceof ITypeResolution) {
                IScriptRelevantData genericContextType =
                        ((ITypeResolution) dataType).getGenericContextType();
                if (genericContextType != null) {
                    if (!genericContextType.isArray() && JScriptUtils
                            .isDynamicComplexType(genericContextType)) {
                        return genericContextType;
                    }
                }
            }
        }
        return dataType;
    }

    public static Object getExtendedInfo(
            IScriptRelevantData scriptRelevantData) {
        if (scriptRelevantData instanceof ITypeResolution) {
            return ((ITypeResolution) scriptRelevantData).getExtendedInfo();
        }
        return null;
    }

    public static boolean isXsdDerivedObject(IScriptRelevantData type) {
        if (JScriptUtils.isXsdAny(type) || JScriptUtils.isXsdAnyType(type)
                || JScriptUtils.isXsdAnySimpleType(type)
                || JScriptUtils.isXsdAnyAttribute(type)) {
            return true;
        }
        return false;
    }

    /*************
     * METHODS COPIED FROM
     * com.tibco.xpd.bom.xsdtransform.exports.template.TransformHelper due to
     * dependency problems
     ****/

    /**
     * Returns whether the data type has got a Union associated with it
     * 
     * @param dataType
     * @return
     */
    public static boolean isUnion(DataType dataType) {
        Stereotype xsdBasedUnionSteroType =
                getAppliedStereotype(dataType, JScriptUtils.XSD_BASED_UNION);
        return xsdBasedUnionSteroType != null;
    }

    /*************
     * METHODS COPIED FROM
     * com.tibco.xpd.bom.xsdtransform.exports.template.TransformHelper due to
     * dependency problems
     ****/
    /**
     * @param model
     * @return boolean
     */
    public static boolean isXSDProfileApplied(Model model) {

        if (model == null) {
            return false;
        }

        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();

        Resource res = ed.loadResource(
                "pathmap://XSD_NOTATION_TYPES/XsdNotation.profile.uml"); //$NON-NLS-1$
        Profile conceptProfile = null;

        if (res != null) {
            EObject object = res.getContents().get(0);

            if (object instanceof Profile) {
                conceptProfile = (Profile) object;
            }
        }

        if (model.getAppliedProfiles().contains(conceptProfile)) {
            return true;
        } else {
            return false;
        }

    }

    /*************
     * METHODS COPIED FROM
     * com.tibco.xpd.bom.xsdtransform.exports.template.TransformHelper due to
     * dependency problems
     ****/
    /**
     * Fetches all the Primitives and Enumerations that represent the dataTypes
     * Union members
     * 
     * @param dataType
     * @return
     */
    public static List<DataType> getUnionMemberTypes(DataType dataType) {
        List<DataType> lstMemberTypes = new ArrayList<DataType>();

        if (JScriptUtils.isXSDProfileApplied(dataType.getModel())) {
            EList<Property> ownedAttributes = dataType.getOwnedAttributes();

            for (Property property : ownedAttributes) {
                Type type = property.getType();

                if (type != null && type instanceof DataType) {
                    lstMemberTypes.add((DataType) type);
                }
            }
        }

        return lstMemberTypes;

    }

    /*************
     * METHODS COPIED FROM
     * com.tibco.xpd.bom.xsdtransform.exports.template.TransformHelper due to
     * dependency problems
     ****/
    /**
     * Returns true if the supplied Class has the XsdNotation stereotype value
     * corresponding to the xsd construct "Restriction of another ComplexType"
     * set to true
     * 
     * @param cl
     * @return
     */
    public static boolean isClassXsdComplexTypeRestriction(Class cl) {
        boolean isSet = false;

        if (JScriptUtils.isXSDProfileApplied(cl.getModel())) {
            Stereotype appliedStereotype =
                    cl.getAppliedStereotype("XsdNotationProfile" //$NON-NLS-1$
                            + "::" + "XsdBasedClass"); //$NON-NLS-1$ //$NON-NLS-2$

            if (appliedStereotype != null) {
                Object value =
                        cl.getValue(appliedStereotype, "xsdIsRestriction"); //$NON-NLS-1$

                if (value instanceof Boolean) {
                    isSet = ((Boolean) value).booleanValue();
                }
            }
        }

        return isSet;

    }

    /**
     * XPD-8147 Checks the supplied Property for the isXsdAttribute stereotype
     * which signifies whether the property was originally an XSD Attribute
     * (rather than XSD Element)
     * 
     * @param prop
     * @return boolean
     */
    public static boolean isPropertyXsdAttribute(Property prop) {
        Stereotype appliedStereotype = prop.getAppliedStereotype(
                "XsdNotationProfile" + "::" + "XsdBasedProperty");

        if (appliedStereotype != null
                && JScriptUtils.isXSDProfileApplied(prop.getModel())) {

            Object xsdPropertyTypeObj =
                    prop.getValue(appliedStereotype, "xsdIsAttribute");

            if (xsdPropertyTypeObj instanceof Boolean) {

                return (Boolean) xsdPropertyTypeObj;

            }
        }

        return false;
    }

    /**
     * check if this is a generated class from Schema that extends a superclass
     * by restriction
     * 
     * @param type
     * @return
     */
    public static boolean isRestrictionOverride(Class type) {

        if (type instanceof Class) {
            return JScriptUtils.isClassXsdComplexTypeRestriction(type);
        }

        return false;
    }

    /**
     * @param propertyType
     * @return
     */
    public static Classifier getPropertyClass(Type propertyType) {
        Classifier clss = null;
        if (propertyType instanceof Class) {
            clss = (Class) propertyType;
        } else if (propertyType instanceof PrimitiveType) {
            clss = (PrimitiveType) propertyType;
        } else if (propertyType instanceof Enumeration) {
            clss = (Enumeration) propertyType;
        }
        return clss;
    }

    public static Property getJsAttributeProperty(JsAttribute jsAttribute) {

        if (jsAttribute instanceof IUMLElement) {
            Element element = ((IUMLElement) jsAttribute).getElement();
            if (element instanceof Property) {
                return (Property) element;
            }
        }

        return null;
    }

    /**
     * 
     * Delegates to isMultiInstancePropertyBasedOnXSDRestriction() as
     * multiplicity could be dependent on XsdNotation stereotype values.
     * 
     * @param jsAttribute
     * @param childClass
     * @return
     */
    public static boolean isMultiple(JsAttribute jsAttribute,
            Class childClass) {
        boolean isMultiple = false;
        Property property = JScriptUtils.getJsAttributeProperty(jsAttribute);

        if (null != property) {
            isMultiple = isMultiInstancePropertyBasedOnXSDRestriction(property);
            if (isMultiple) {
                return isMultiple;
            }
        }
        return jsAttribute.isMultiple();
    }

    public static DataType getUnionDataType(JsAttribute jsAttribute) {
        if (jsAttribute instanceof IUMLElement) {
            Element element = ((IUMLElement) jsAttribute).getElement();
            if (element instanceof Property) {
                Property property = (Property) element;
                if (property.getType() instanceof DataType) {
                    DataType dataType = (DataType) property.getType();
                    return dataType;
                }
            }
        }
        return null;
    }

    public static boolean isUnion(JsAttribute jsAttribute) {
        DataType dataType = JScriptUtils.getUnionDataType(jsAttribute);
        if (null != dataType) {
            return JScriptUtils.isUnion(dataType);
        }
        return false;
    }

    /**
     * convert to case uml script relevant data
     * 
     * @param umlClass
     * @param jsClass
     * @param isArray
     * @return CaseUMLScriptRelevantData
     */
    public static CaseUMLScriptRelevantData convertToCaseUMLScriptRelevantData(
            Class umlClass, JsClass jsClass, boolean isArray) {

        if (jsClass instanceof CaseRefJsClass) {

            CaseUMLScriptRelevantData caseUMLScriptRelevantData =
                    new CaseUMLScriptRelevantData(jsClass.getName(),
                            jsClass.getName(), isArray, jsClass,
                            ((CaseRefJsClass) jsClass).getType());
            // caseUMLScriptRelevantData.setIcon(icon);
            caseUMLScriptRelevantData.addClass(umlClass, null);
            caseUMLScriptRelevantData.setAdditionalInfo(
                    JScriptUtils.getUmlElementComment(umlClass));
            /*
             * We don't need to load the model, since we are passing the class
             * to be loaded
             */
            caseUMLScriptRelevantData.setLoadModel(false);
            return caseUMLScriptRelevantData;
        }
        return null;
    }

    /**
     * convert to case uml script relevant data
     * 
     * @param umlClass
     * @return CaseUMLScriptRelevantData
     */
    public static CaseUMLScriptRelevantData convertToCaseUMLScriptRelevantData(
            Class umlClass) {

        CaseRefJsClass caseRefJsClass = new CaseRefJsClass(umlClass);
        CaseUMLScriptRelevantData caseUMLScriptRelevantData =
                convertToCaseUMLScriptRelevantData(umlClass,
                        caseRefJsClass,
                        false);
        return caseUMLScriptRelevantData;
    }

    public static IUMLScriptRelevantData convertToUMLScriptRelevantData(
            Class umlClass, final ILabelProvider labelProvider) {

        IUMLScriptRelevantData uMLScriptRelevantData =
                convertToUMLScriptRelevantData(umlClass, false, labelProvider);
        uMLScriptRelevantData.setIcon(labelProvider.getImage(umlClass));
        return uMLScriptRelevantData;
    }

    public static IUMLScriptRelevantData convertToUMLScriptRelevantData(
            Class umlClass, boolean isArray,
            final ILabelProvider labelProvider) {

        DefaultJsClass jsClass = new DefaultJsClass(umlClass) {

            @Override
            protected JsAttribute createJsAttribute(Property property) {
                DefaultJsAttribute jsAttribute =
                        new DefaultJsAttribute(property, multipleClass);
                jsAttribute.setIcon(labelProvider.getImage(property));
                return jsAttribute;
            }
        };
        jsClass.setContentAssistIconProvider(
                JScriptUtils.getJsContentAssistIconProvider());
        DefaultUMLScriptRelevantData umlScriptRelevantData =
                new DefaultUMLScriptRelevantData(umlClass.getName(),
                        jsClass.getName(), isArray, jsClass);
        return umlScriptRelevantData;
    }

    /**
     * Applied stereotype method on the Classifier wasn't returning expected
     * values.
     * 
     * @param cls
     * @param stereotypeName
     * @return
     */
    private static Stereotype getAppliedStereotype(Classifier cls,
            String stereotypeName) {

        List<Stereotype> appliedStereotypes = cls.getAppliedStereotypes();
        for (Stereotype stereotype : appliedStereotypes) {
            if (stereotype.getName().equals(stereotypeName)) {
                return stereotype;
            }
        }
        return null;
    }

    private static final String XSD_BASED_UNION = "XsdBasedUnion"; //$NON-NLS-1$

    private static final String TYPES_PATHMAP = "pathmap://BOM_TYPES/"; //$NON-NLS-1$

    private static final String BOM_PRIMITIVE_TYPES_FACETS_PROFILE_URI =
            TYPES_PATHMAP + "PrimitiveTypeFacets." //$NON-NLS-1$
                    + UMLResource.PROFILE_FILE_EXTENSION; // ;

    /** Name of the restricted */
    private static final String RESTRICTED_TYPE_STEREOTYPE_NAME =
            "RestrictedType"; //$NON-NLS-1$

    private static final String BOM_PRIMITIVE_FACET_OBJECT_SUBTYPE =
            "objectSubType"; //$NON-NLS-1$

    private static final String BOM_PRIMITIVE_TYPES_LIBRARY_URI = TYPES_PATHMAP
            + "BomPrimitiveTypes." + UMLResource.LIBRARY_FILE_EXTENSION; //$NON-NLS-1$

    private static final String OBJECT_SUBTYPE_XSD_ANY = "xsdAny"; //$NON-NLS-1$

    private static final String OBJECT_SUBTYPE_XSD_ANYTYPE = "xsdAnyType"; //$NON-NLS-1$

    private static final String OBJECT_SUBTYPE_XSD_ANYATTRIBUTE =
            "xsdAnyAttribute"; //$NON-NLS-1$

    private static final String OBJECT_SUBTYPE_XSD_ANYSIMPLETYPE =
            "xsdAnySimpleType"; //$NON-NLS-1$

    public static final String XSD_BASED_CLASS = "XsdBasedClass"; //$NON-NLS-1$

    public static final String XSD_ANON_TYPE = "xsdIsAnonType"; //$NON-NLS-1$

    /**
     * SID XPD-1605: Calling resourceSet.getResource() was frequently hitting
     * ConcurrentModificationException's
     * <p>
     * This was because {@link ResourceSetImpl#getResource(URI, boolean)}
     * iterates thru a list which can change (namely because during start of
     * Bom2Xsd build we may delete xsd's previously generated from the BOM's;
     * Then we start the transformation of Bom2Xsd; Then the working copy (on a
     * different thread) receives notification of the delete and u8nloads the
     * Resource - this changes the resource set!
     * <p>
     * This method negates this problem by catching concurrent modification
     * exception and retrying.
     * 
     * @param resourceSet
     * 
     * @return Resource (as per {@link ResourceSet#getResource(URI, boolean)}
     */
    private static Resource getResource(ResourceSet resourceSet, URI uri,
            boolean loadOnDemand) {

        while (true) {
            try {
                Resource ret = resourceSet.getResource(uri, loadOnDemand);

                return ret;

            } catch (ConcurrentModificationException cme) {
                // System.out
                // .println(PrimitivesUtil.class.getName()
                // +
                // ".getResource(): Caught ConcurrentModificationException
                // getting resource; waiting 0.5 seconds before retry.");
                try {
                    Thread.currentThread().sleep(500);
                } catch (InterruptedException e) {
                    return null;
                }
            }
        }
    }

    /**
     * Compute most base primitive type, or return pt if there is no base type
     * or the hierarchy contains loops.
     * 
     * @param pt
     * @return
     */
    public static PrimitiveType getBasePrimitiveType(PrimitiveType pt) {
        PrimitiveType base = pt;
        HashSet<PrimitiveType> visited = new HashSet<PrimitiveType>();
        while (!base.getGenerals().isEmpty()) {
            if (visited.contains(base)) {
                // loop in the hierarchy
                return pt;
            }
            visited.add(base);

            Object gen = base.getGenerals().get(0);

            if (gen instanceof PrimitiveType) {
                base = (PrimitiveType) base.getGenerals().get(0);
            }

        }
        return base;
    }

    /**
     * Returns BOM standard primitive with provided name or null.
     * 
     * @param rs
     *            resource set.
     * @param name
     *            the local name of the primitive type.
     * @return BOM standard primitive with provided name or null.
     */
    private static PrimitiveType getStandardPrimitiveTypeByName(ResourceSet rs,
            String name) {
        Resource res = getResource(rs,
                URI.createURI(BOM_PRIMITIVE_TYPES_LIBRARY_URI),
                true);
        if (res != null) {
            EList<EObject> contents = res.getContents();
            for (EObject root : contents) {
                if (root instanceof Model) {
                    PackageableElement type =
                            ((Model) root).getPackagedElement(name);
                    if (type instanceof PrimitiveType) {
                        return (PrimitiveType) type;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Retrieves the value of the facet from the primitive type's application
     * context. If the value was not overwritten in the context of the type
     * application then it will be retrieved from the primitive type.
     * 
     * @param type
     *            the primitive type which value we want to check.
     * @param propertyName
     *            the name of property which value is to be retrieved.
     * @param context
     *            the property to which the primitive type is applied.
     * @return the value of the facet in application context.
     */
    private static Object getFacetPropertyValue(PrimitiveType type,
            String propertyName, Property context) {
        if (context != null) {
            Stereotype stereotype = getFacetsStereotype(
                    getPackageResourceSet(type.getPackage()));
            EObject stereotypeApplication =
                    context.getStereotypeApplication(stereotype);
            // Get the value if it is actually set, otherwise get the value from
            // the
            // primitive type
            if (stereotypeApplication != null
                    && isValueSet(stereotypeApplication, propertyName)) {
                Object value = context.getValue(stereotype, propertyName);
                if (value != null) {
                    return value;
                }
            }
        }
        return getFacetPropertyValue(type, propertyName);
    }

    private static ResourceSet getPackageResourceSet(Package umlPackage) {
        if (umlPackage == null) {
            // MR 42752 - RS - When a BOM class is deleted, added to avoid a NPE
            return null;
        }
        if (umlPackage.eResource() != null
                && umlPackage.eResource().getResourceSet() != null) {
            return umlPackage.eResource().getResourceSet();
        }
        throw new IllegalStateException(
                "Package have to be connected to a resource and a valid resource set."); //$NON-NLS-1$
    }

    /**
     * Get the RestrictedType stereotype.
     * 
     * @since 3.3
     * 
     * @param rs
     * @return
     */
    private static Stereotype getFacetsStereotype(ResourceSet rs) {
        Profile facetProfile = (Profile) loadUmlResource(rs,
                BOM_PRIMITIVE_TYPES_FACETS_PROFILE_URI);
        return facetProfile.getOwnedStereotype(RESTRICTED_TYPE_STEREOTYPE_NAME);
    }

    private static Package loadUmlResource(ResourceSet rs, String uriString) {
        Package umlPackage = null;
        try {
            URI uri = URI.createURI(uriString);
            Resource resource = getResource(rs, uri, true);

            umlPackage =
                    (Package) EcoreUtil.getObjectByType(resource.getContents(),
                            UMLPackage.Literals.PACKAGE);
        } catch (WrappedException we) {
            // TODO LOG
            throw we;
        }
        return umlPackage;
    }

    /**
     * Check if the given property is set in the Stereotype Application.
     * 
     * @param stereotypeApplication
     * @param property
     * @return <code>false</code> if the value is not set, <code>true</code>
     *         otherwise.
     */
    private static boolean isValueSet(EObject stereotypeApplication,
            String property) {
        if (stereotypeApplication != null) {
            EStructuralFeature feature = stereotypeApplication.eClass()
                    .getEStructuralFeature(property);
            if (feature != null) {
                return stereotypeApplication.eIsSet(feature);
            }
        }
        return true;
    }

    /**
     * Retrieves the facet's value of primitive type or if not set (null) then
     * from the type's ancestor(s).
     * 
     * @param type
     *            the primitive type for which the value is retrieved.
     * @param property
     *            the property of which value is to be retrieved.
     * @return the value of the primitive type stereotype.
     */
    private static Object getFacetPropertyValue(PrimitiveType type,
            String propertyName) {

        HashSet<PrimitiveType> visited = new HashSet<PrimitiveType>();
        PrimitiveType base = type;

        while (base != null) {

            // avoid hierarchy loops
            if (visited.contains(base)) {
                return null;
            }
            visited.add(base);

            // Get the super class, if any
            Classifier superClass = getSuper(base);
            PrimitiveType superPType =
                    (PrimitiveType) (superClass instanceof PrimitiveType
                            ? superClass
                            : null);

            Package umlPackage = base.getPackage();
            if (umlPackage == null) {
                // reference to detached type, it is only temporary situation.
                return null;
            }
            Stereotype stereotype =
                    getFacetsStereotype(getPackageResourceSet(umlPackage));

            if (!base.isStereotypeApplied(stereotype)) {
                // stereotype is not applied
                // continue to climb up in the hierarchy
                // // applyFacetsProfile(umlPackage);
            } else {
                // Check if the property is set on this primitive type
                boolean isSet =
                        isValueSet(base.getStereotypeApplication(stereotype),
                                propertyName);

                /*
                 * If the value is set then return the value, otherwise get the
                 * super-type's value (if this primitive type has no super-type
                 * then return its value).
                 */
                if (isSet || superPType == null) {
                    Object value = base.getValue(stereotype, propertyName);
                    if (value != null) {
                        return value;
                    }
                }
            }
            // check the superclass for the value
            base = superPType;
        }
        return null;
    }

    /**
     * Get the superclass of the given Classifier.
     * 
     * @param classifier
     * @return superclass or <code>null</code> if classifier is not a subclass.
     */
    private static Classifier getSuper(Classifier classifier) {
        EList<Classifier> generals = classifier.getGenerals();
        if (!generals.isEmpty()) {
            return generals.get(0);
        }
        return null;
    }

    /*************
     * METHODS COPIED FROM com.tibco.xpd.bom.xsdtransform.api.XSDUtil due to
     * dependency problems
     ****/
    /**
     * 
     * Determines whether the supplied property upper multiplicity is unbounded.
     * It is possible that the property's parent class is a representation of
     * the ComplexType Restriction XML Schema construct. In which case the
     * hierarchy of generalized classes will be examined to determine the
     * correct multiplicity. Should only really need to call this when the
     * supplied Property's upper bound multiplicity is 1.
     * 
     * @param prop
     * @return
     */
    public static boolean isMultiInstancePropertyBasedOnXSDRestriction(
            Property prop) {
        boolean isMulti = false;
        if (prop.getUpper() == 1) {
            Element owner = prop.getOwner();

            if (owner instanceof Class) {
                Class cl = (Class) owner;

                if (isClassXsdComplexTypeRestriction(cl)) {
                    // We have identified this property as being contained in a
                    // complex type restriction. We need to check identify the
                    // parent Class/Property that is being restricted as we
                    // still need to support n or * multiplicity (i.e. a List)
                    // even is we are restricting to a single value. This is due
                    // to behaviour in BDS/EMF code generation

                    // Collect the whole hierarchy of Classes so that we can
                    // work from the topmost parent down, searching for the
                    // first definition of the restricted Property.
                    // We can then read its multiplicity to see if it is
                    // multi-instance.
                    String propName = prop.getName();
                    List<Class> lstSuperClasses = new ArrayList<Class>();
                    collectSuperClasses(cl, lstSuperClasses);

                    if (!lstSuperClasses.isEmpty()) {
                        // Reverse the array so that we can start iteration from
                        // the top-most super Class
                        Collections.reverse(lstSuperClasses);

                        boolean found = false;
                        for (Class clazz : lstSuperClasses) {
                            EList<Property> ownedAttributes =
                                    clazz.getOwnedAttributes();

                            for (Property superProp : ownedAttributes) {
                                if (superProp.getName().equals(propName)) {
                                    // FOUND THE TOP-MOST PROPERTY
                                    int upper = superProp.getUpper();
                                    if (upper > 1 || upper == -1) {
                                        // We've found our multiplicity
                                        isMulti = true;
                                        found = true;
                                        break;
                                    }
                                }
                            }
                            if (found) {
                                break;
                            }
                        }
                    }
                }
            }
        } else if (prop.getUpper() > 1 || prop.getUpper() == -1) {
            isMulti = true;
        }

        return isMulti;
    }

    /*************
     * METHODS COPIED FROM com.tibco.xpd.bom.xsdtransform.api.XSDUtil due to
     * dependency problems
     ****/
    /**
     * Collects the classes in the genearlized hierarchy.
     * 
     * @param cl
     * @param lstSuperClasses
     */
    private static void collectSuperClasses(Class cl,
            List<Class> lstSuperClasses) {

        if (!cl.getGenerals().isEmpty()) {
            Classifier classifier = cl.getGenerals().get(0);

            if (classifier instanceof Class) {
                lstSuperClasses.add((Class) classifier);
                collectSuperClasses((Class) classifier, lstSuperClasses);
            }
        }
    }

    /*********************************************************************************************************************************/

    /**
     * This class is to support the necessity to fetch custom keyed strings from
     * the UML resource bundle .properties file (when we need to override
     * default name translation.
     * <p>
     * Had to do this as the necessary fetch resource bundle string methods in
     * UML2Util are protected rather than public.
     * 
     * @author aallway
     * @since 4 Mar 2014
     */
    private static class MyUML2Util extends UML2Util {

        public static String myGetString(EObject eObject, String key,
                String defaultString, boolean localize) {
            return getString(eObject, key, defaultString, localize);
        }
    }

}
