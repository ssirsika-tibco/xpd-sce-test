/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

import com.tibco.xpd.implementer.nativeservices.java.JavaConstants;
import com.tibco.xpd.implementer.nativeservices.java.internal.Messages;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ClassType;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EAIJava;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.MethodType;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.utils.EAIJavaModelUtil;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.utils.JavaModelUtil;
import com.tibco.xpd.implementer.script.ActivityMessageUtil;
import com.tibco.xpd.implementer.script.FieldParserException;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ScriptInformationUtil;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public final class JavaServiceMappingUtil {
    public static final String SCRIPT_CONTEXT = "JavaServiceTask"; //$NON-NLS-1$

    private JavaServiceMappingUtil() {
    }

    public static DataMapping findDataMapping(Message message, Object target) {
        DataMapping dataMapping = null;

        if (message != null && target != null) {
            String name = getScriptName(target, MappingDirection.IN);

            if (name != null) {
                EList dataMappings = message.getDataMappings();

                if (dataMappings != null) {
                    for (Object next : dataMappings) {
                        DataMapping mapping = (DataMapping) next;

                        if (mapping.getFormal().equals(name)) {
                            dataMapping = mapping;
                            break;
                        }
                    }
                }
            }

        } else {
            throw new NullPointerException("message or target is null."); //$NON-NLS-1$
        }

        return dataMapping;
    }

    /**
     * Find the mapped parameters.
     * 
     * @param activity
     * @param namePattern
     * @param direction
     * @return
     * @throws FieldParserException
     */
    public static List<String> findMappedParameters(Activity activity,
            String namePattern, MappingDirection direction)
            throws FieldParserException {
        List<String> params = new ArrayList<String>();

        if (activity != null && namePattern != null && direction != null) {

            if (direction == MappingDirection.IN) {
                if (activity.getImplementation() instanceof Task) {
                    TaskService taskService =
                            ((Task) activity.getImplementation())
                                    .getTaskService();

                    if (taskService != null
                            && taskService.getMessageIn() != null) {
                        EList mappings =
                                taskService.getMessageIn().getDataMappings();

                        if (mappings != null) {
                            for (Object next : mappings) {
                                DataMapping mapping = (DataMapping) next;
                                if (mapping.getFormal().matches(namePattern)) {
                                    params.add(mapping.getFormal());
                                }
                            }
                        }
                    }
                }
            } else /* MappingDirection.OUT */{
                if (activity.getImplementation() instanceof Task) {
                    TaskService taskService =
                            ((Task) activity.getImplementation())
                                    .getTaskService();

                    if (taskService != null
                            && taskService.getMessageOut() != null) {
                        EList mappings =
                                taskService.getMessageOut().getDataMappings();

                        if (mappings != null) {
                            for (Object next : mappings) {
                                DataMapping mapping = (DataMapping) next;
                                String script =
                                        DataMappingUtil.getScript(mapping);
                                String target =
                                        DataMappingUtil.getTarget(mapping);
                                String grammar =
                                        DataMappingUtil.getGrammar(mapping);
                                if (script != null && target != null
                                        && grammar != null) {
                                    ScriptMappingCompositorFactory factory =
                                            ScriptMappingCompositorFactory
                                                    .getCompositorFactory(grammar,
                                                            DirectionType.OUT_LITERAL,
                                                            JavaServiceMappingUtil.SCRIPT_CONTEXT);
                                    if (factory != null) {
                                        ScriptMappingCompositor compositor =
                                                factory.getCompositor(activity,
                                                        target,
                                                        script);
                                        if (compositor instanceof SingleMappingCompositor) {
                                            SingleMappingCompositor scriptMapping =
                                                    (SingleMappingCompositor) compositor;

                                            Collection<String> parameterNames =
                                                    scriptMapping
                                                            .getSourceItemNames();

                                            if (parameterNames != null) {
                                                for (String parameterName : parameterNames) {
                                                    if (parameterName
                                                            .matches(namePattern)) {
                                                        params.add(parameterName);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            throw new NullPointerException("input is null."); //$NON-NLS-1$
        }

        return params;
    }

    /**
     * Check if the given param is an array type.
     * 
     * @param param
     *            <code>ProcessRelevantData</code> or
     *            <code>JavaMethodParameter</code>
     * @return
     */
    public static boolean isArray(Object param) {
        boolean array = false;

        if (param instanceof ConceptPath) {
            param = ((ConceptPath) param).getItem();
        }
        if (param instanceof ProcessRelevantData) {
            array = ((ProcessRelevantData) param).isIsArray();
        } else if (param instanceof JavaMethodParameter) {
            array = ((JavaMethodParameter) param).isArray();
        }

        return array;
    }

    public static String getScriptName(Object object, MappingDirection direction) {
        String name = ""; //$NON-NLS-1$
        if (object instanceof JavaMethodParameter) {
            name = ((JavaMethodParameter) object).getPath();
        } else {
            name =
            // JavaConstants.PROCESS_PREFIX
            // + JavaConstants.PARAMETER_SEPARATOR +
                    ActivityMessageUtil.getPath(object);
        }
        return name;
    }

    /**
     * Convert parameter name into form to appear in scripts.
     * 
     * @param paramName
     * @return
     */
    public static String getScriptParameterName(String paramName) {
        return JavaConstants.PROCESS_PREFIX + JavaConstants.PARAMETER_SEPARATOR
                + paramName;
    }

    /**
     * Get the highest array index mapped in this array parameter.
     * 
     * @param param
     * @param direction
     * @return Highest index mapped, -1 if no elements mapped
     * @throws FieldParserException
     */
    private static int getHighestArrayIndex(JavaMethodParameter param,
            MappingDirection direction) throws FieldParserException {
        int index = -1;
        if (param != null) {
            Activity activity = param.getActivity();

            if (activity != null) {
                String paramName =
                        (param instanceof JavaMethodReturnParameter ? JavaConstants.RESPONSE_PREFIX
                                : JavaConstants.PARAMETER_PREFIX)
                                + JavaConstants.PARAMETER_SEPARATOR
                                + param.getQualifiedElementName();

                List<String> mappedParams =
                        findMappedParameters(activity, escapePattern(paramName)
                                + "\\[\\d+\\](\\..*)?", //$NON-NLS-1$
                                direction);

                if (!mappedParams.isEmpty()) {
                    int maxIdx = 0;

                    for (String mappedParam : mappedParams) {
                        int pos =
                                mappedParam.indexOf('[', paramName.length()) + 1;

                        if (pos > 0) {
                            int idx =
                                    Integer.parseInt(mappedParam.substring(pos,
                                            mappedParam.indexOf(']', pos)));

                            maxIdx = maxIdx < idx ? idx : maxIdx;
                        }
                    }

                    index = maxIdx;
                }
            } else {
                throw new NullPointerException("activity is null."); //$NON-NLS-1$
            }

        } else {
            throw new NullPointerException("param is null."); //$NON-NLS-1$
        }

        return index;
    }

    /**
     * Resolve the parameter name given.
     * 
     * @param activity
     * @param direction
     * @param name
     * @return
     */
    public static JavaMethodParameter resolveParameter(Activity activity,
            MappingDirection direction, String name) {
        JavaMethodParameter parameter = null;

        // Remove the parameter prefix
        if (name.startsWith(JavaConstants.PARAMETER_PREFIX
                + JavaConstants.PARAMETER_SEPARATOR)) {
            name =
                    name.substring(JavaConstants.PARAMETER_PREFIX.length()
                            + JavaConstants.PARAMETER_SEPARATOR.length());
        } else if (direction == MappingDirection.OUT
                && name.startsWith(JavaConstants.RESPONSE_PREFIX
                        + JavaConstants.PARAMETER_SEPARATOR)) {
            name =
                    name.substring(JavaConstants.RESPONSE_PREFIX.length()
                            + JavaConstants.PARAMETER_SEPARATOR.length());

        }
        JavaMethod method = getJavaMethod(activity);
        if (method != null) {
            try {
                Collection<JavaMethodParameter> parameters =
                        getJavaMethodParameters(method, direction);

                // If this is a java bean path to a parameter then get the bean
                // and
                // find the parameter from it
                String[] paramElems = name.split("\\."); //$NON-NLS-1$
                int index = 0;
                if (paramElems.length > 0) {
                    parameter =
                            resolveParameter(paramElems,
                                    index,
                                    parameters,
                                    direction);
                }
                // Find the method parameter
                // for (JavaMethodParameter current : parameters) {
                // if (paramElems[0].equals(current.getName())) {
                // parameter = current;
                // break;
                // }
                // }

                // If this is a java bean reference then resolve it
                // if (paramElems.length > 1) {
                // parameter =
                // resolveParameter(parameter, direction, name
                // .substring(name.indexOf('.') + 1));
                // }
            } catch (FieldParserException e) {
                e.printStackTrace();
            } catch (JavaModelException e) {
                // Ignore, parameter won't resolve.
            }
        }
        return parameter;
    }

    /**
     * @param paramElems
     * @param index
     * @param parameters
     * @return
     */
    private static JavaMethodParameter resolveParameter(String[] paramElems,
            int index, Collection<JavaMethodParameter> parameters,
            MappingDirection direction) throws FieldParserException {
        JavaMethodParameter parameter = null;
        Collection<JavaMethodParameter> expanded =
                expandArrays(parameters, direction);
        for (JavaMethodParameter current : expanded) {
            parameter = resolveParameter(paramElems, index, current, direction);
            if (parameter != null) {
                break;
            }
        }
        return parameter;
    }

    /**
     * @param paramElems
     * @param index
     * @param current
     * @return
     * @throws FieldParserException
     */
    private static JavaMethodParameter resolveParameter(String[] paramElems,
            int index, JavaMethodParameter current, MappingDirection direction)
            throws FieldParserException {
        JavaMethodParameter parameter = null;
        String elem = paramElems[index];
        int open = elem.indexOf('[');
        int close = elem.indexOf(']');
        if (current.isArray() && open != -1 && close != -1 && close > open) {
            String baseElem = elem.substring(0, open);
            if (baseElem.equals(current.getElementName())) {
                Collection<JavaMethodParameter> children =
                        Arrays.asList(current.getChildren(direction));
                parameter =
                        resolveParameter(paramElems, index, children, direction);
            }
        } else {
            if (elem.equals(current.getName())) {
                index++;
                if (index == paramElems.length) {
                    parameter = current;
                } else {
                    Collection<JavaMethodParameter> children =
                            Arrays.asList(current.getChildren(direction));
                    parameter =
                            resolveParameter(paramElems,
                                    index,
                                    children,
                                    direction);
                }
            }
        }
        return parameter;
    }

    /**
     * Resolve the given qualified parameter from the JavaMethodParameter given.
     * This will be usually in cases of java bean parameters.
     * 
     * @param parameter
     * @param paramPath
     * @return
     * @throws FieldParserException
     */
    private static JavaMethodParameter resolveParameter(
            JavaMethodParameter parameter, MappingDirection direction,
            String paramPath) throws FieldParserException {
        JavaMethodParameter ret = null;

        if (parameter != null && paramPath != null) {
            String name;
            int pos = paramPath.indexOf('.');

            if (pos > 0) {
                name = paramPath.substring(0, pos);
                paramPath = paramPath.substring(pos + 1);
            } else {
                name = paramPath;
                paramPath = null;
            }

            JavaMethodParameter[] children = parameter.getChildren(direction);
            // Expand any arrays
            Collection<JavaMethodParameter> parameters =
                    expandArrays(Arrays.asList(children), direction);

            if (parameters != null) {
                for (JavaMethodParameter param : parameters) {
                    if (param.getName().equals(name)) {
                        ret = param;
                        break;
                    }
                }

                // If this is not the end parameter then carry on resolving
                if (paramPath != null) {
                    ret = resolveParameter(ret, direction, paramPath);
                }
            }
        }

        return ret;
    }

    /**
     * Get the POJO Method from the given activity.
     * 
     * @param activity
     * @return
     */
    public static JavaMethod getJavaMethod(Activity activity) {
        JavaMethod javaMethod = null;
        EAIJava eai = getEAIJava(activity);
        if (eai != null) {
            ClassType pojo = eai.getPojo();
            if (pojo != null) {
                MethodType methodType = pojo.getMethod();
                if (methodType != null) {
                    try {
                        IType classType =
                                JavaModelUtil.findClass(eai.getProject(),
                                        pojo.getName());
                        if (classType != null) {
                            IMethod method =
                                    classType
                                            .getMethod(methodType.getName(),
                                                    EAIJavaModelUtil
                                                            .getMethodParameterSignatures(methodType));
                            if (method != null && method.exists()) {
                                javaMethod =
                                        new JavaMethod(activity, classType,
                                                method);
                            }
                        }
                    } catch (JavaModelException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return javaMethod;
    }

    /**
     * Get the parameters of the given <i>method</i>.
     * 
     * @param method
     * @param direction
     * @return
     * @throws FieldParserException
     */
    public static Collection<JavaMethodParameter> getJavaMethodParameters(
            JavaMethod method, MappingDirection direction)
            throws FieldParserException, JavaModelException {
        Collection<JavaMethodParameter> javaParameters =
                new ArrayList<JavaMethodParameter>();
        JavaMethodParameter[] parameters = method.getParameters();

        if (parameters != null) {
            /*
             * If mapping input parameters then all params of the method should
             * be included, otherwise if mapping output parameters then add
             * return type and also any parameters that are complex types
             */
            if (direction == MappingDirection.IN) {
                javaParameters.addAll(Arrays.asList(parameters));

            } else {
                // Check for return value
                JavaMethodParameter returnParam = method.getReturnParam();

                if (returnParam.getType() != ParameterTypeEnum.VOID) {
                    javaParameters.add(returnParam);
                }

                /*
                 * If any method param is complex type then add to the mapper
                 * XPD-4202 Input complex types can be used as a place holder to
                 * return values from methods.
                 */
                for (JavaMethodParameter param : parameters) {
                    if (param.getType() == ParameterTypeEnum.CLASS) {
                        javaParameters.add(param);
                    }
                }
            }

            // Expand all arrays
            javaParameters = expandArrays(javaParameters, direction);
        }

        return javaParameters;
    }

    /**
     * Get the parameters of the given java parameter.
     * 
     * @param param
     * @param direction
     * @return
     * @throws FieldParserException
     */
    public static Collection<JavaMethodParameter> getJavaMethodParameters(
            JavaMethodParameter param, MappingDirection direction)
            throws FieldParserException {
        Collection<JavaMethodParameter> parameters =
                new ArrayList<JavaMethodParameter>();

        if (param != null) {
            JavaMethodParameter[] children = param.getChildren(direction);

            if (children != null) {
                parameters = expandArrays(Arrays.asList(children), direction);
            }
        }

        return parameters;
    }

    /**
     * Get the deepest parameters of the given java parameter.
     * 
     * @param param
     * @param direction
     * @return
     * @throws FieldParserException
     */
    public static Collection<JavaMethodParameter> getSuperDeepJavaMethodParameters(
            JavaMethodParameter param, MappingDirection direction,
            Collection<JavaMethodParameter> allParameters)
            throws FieldParserException {
        Collection<JavaMethodParameter> parameters =
                getJavaMethodParameters(param, direction);
        if (parameters != null && parameters.isEmpty()) {
            allParameters.add(param);
        } else {
            for (Iterator iterator = parameters.iterator(); iterator.hasNext();) {
                JavaMethodParameter javaMethodParameter =
                        (JavaMethodParameter) iterator.next();
                getSuperDeepJavaMethodParameters(javaMethodParameter,
                        direction,
                        allParameters);
            }
        }
        return allParameters;
    }

    /**
     * Get the <code>EAIJava</code> model from the <code>Activity</code> type.
     * 
     * @param activityl
     * @return
     */
    public static EAIJava getEAIJava(Activity activity) {
        EAIJava eai = null;
        if (activity != null) {
            Implementation implementation = activity.getImplementation();
            if (implementation instanceof Task) {
                TaskService taskService =
                        ((Task) implementation).getTaskService();
                if (taskService != null) {
                    eai = EAIJavaModelUtil.getEAIJavaObj(taskService);
                }
            }
        }

        return eai;
    }

    /**
     * Expand the array type parameters.
     * 
     * @param activity
     * @param parameters
     * @throws FieldParserException
     */
    private static Collection<JavaMethodParameter> expandArrays(
            final Collection<JavaMethodParameter> parameters,
            MappingDirection direction) throws FieldParserException {

        Collection<JavaMethodParameter> newList =
                new ArrayList<JavaMethodParameter>();

        if (parameters != null) {
            for (JavaMethodParameter param : parameters) {
                if (param.getParent() instanceof JavaMethodParameter
                        && ((JavaMethodParameter) param.getParent()).isArray()) {
                    Activity activity = param.getActivity();

                    if (activity != null) {
                        // Check if an array data field is mapped to
                        // this parameter
                        String paramName =
                                (param instanceof JavaMethodReturnParameter ? JavaConstants.RESPONSE_PREFIX
                                        : JavaConstants.PARAMETER_PREFIX)
                                        + JavaConstants.PARAMETER_SEPARATOR
                                        + param.getQualifiedElementName();

                        List<String> mappedParametes =
                                findMappedParameters(activity,
                                        escapePattern(paramName)
                                                + "\\[\\d+\\](\\..*)?", //$NON-NLS-1$
                                        direction);

                        if (!mappedParametes.isEmpty()) {
                            // Get the array index used and add one to
                            // create an empty element
                            int total =
                                    getHighestArrayIndex(param, direction) + 1;

                            for (int idx = 0; idx <= total; idx++) {
                                // Create an element of this parameter
                                JavaMethodParameter newParam =
                                        param.createElement(idx);

                                if (idx == total) {
                                    // This is the highest element of
                                    // this array parameter
                                    newParam.setIsHighestIndex(true);
                                }

                                newList.add(newParam);
                            }
                        } else {
                            newList.add(param);
                        }
                    } else {
                        throw new NullPointerException("Activity is null."); //$NON-NLS-1$
                    }
                } else {
                    // Not an array
                    newList.add(param);
                }
            }
        }

        return newList;
    }

    /**
     * Escape the given regular expression.
     * 
     * @param pattern
     * @return
     */
    private static String escapePattern(String pattern) {
        String escaped = pattern;

        if (pattern != null) {
            escaped = "\\Q" + pattern + "\\E"; //$NON-NLS-1$ //$NON-NLS-2$
        }

        return escaped;
    }

    /**
     * 
     * @param parameter
     *            The parameter
     * @return true if it is the only element in the array
     */
    public static boolean isOnlyElementInArray(JavaMethodParameter parameter) {
        if (parameter != null && JavaServiceMappingUtil.isArray(parameter)
                && parameter.isHighestIndex() && parameter.getIndex() == 0) {
            return true;
        }
        return false;
    }

    /**
     * @param param
     * @param direction
     * @return true if is the highest mapped array index
     * @throws FieldParserException
     */
    public static boolean isHighestMappedArrayIndex(JavaMethodParameter param,
            MappingDirection direction) throws FieldParserException {
        int highestMappedIndex = getHighestArrayIndex(param, direction);
        if (param.getIndex() == highestMappedIndex) {
            return true;
        }
        return false;
    }

    /**
     * @param ed
     * @param script
     * @param information
     * @param grammar
     * @return
     */
    public static Command getSetJavaServiceScriptCommand(EditingDomain ed,
            String script, ScriptInformation information, String grammar) {
        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.JavaServiceMappingUtil_EditScriptCommandLabel);
        EObject parent = information.eContainer();
        if (parent instanceof Activity) {
            Expression expression = Xpdl2ModelUtil.createExpression(script);
            expression.setScriptGrammar(grammar);
            cmd.append(SetCommand.create(ed,
                    information,
                    XpdExtensionPackage.eINSTANCE
                            .getScriptInformation_Expression(),
                    expression));
        } else if (parent == null) {
            Activity activity = information.getActivity();
            if (activity != null) {
                information.setActivity(null);
                information
                        .setName(ScriptInformationUtil
                                .getNextScriptName(activity,
                                        information.getDirection()));
                Expression expression = Xpdl2ModelUtil.createExpression(script);
                expression.setScriptGrammar(grammar);
                information.setExpression(expression);
                cmd.append(Xpdl2ModelUtil.getAddOtherElementCommand(ed,
                        activity,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script(),
                        information));
            }
        } else if (parent instanceof DataMapping) {
            DataMapping mapping = (DataMapping) parent;
            DirectionType direction = mapping.getDirection();
            Activity activity = getContainingActivity(parent);
            if (activity != null) {
                String target = DataMappingUtil.getTarget(mapping);
                if (target != null) {
                    ScriptMappingCompositorFactory factory =
                            ScriptMappingCompositorFactory
                                    .getCompositorFactory(grammar,
                                            direction,
                                            SCRIPT_CONTEXT);
                    if (factory != null) {
                        ScriptMappingCompositor compositor =
                                factory.getCompositor(activity, target);
                        if (compositor instanceof SingleMappingCompositor) {
                            SingleMappingCompositor single =
                                    (SingleMappingCompositor) compositor;
                            single.setScript(script);
                            Expression expression = single.getExpression();
                            Object feature;
                            Object owner;
                            if (DirectionType.IN_LITERAL.equals(direction)) {
                                feature =
                                        Xpdl2Package.eINSTANCE
                                                .getDataMapping_Actual();
                                owner = mapping;
                            } else {
                                feature =
                                        XpdExtensionPackage.eINSTANCE
                                                .getScriptInformation_Expression();
                                owner = information;
                            }
                            cmd.append(SetCommand.create(ed,
                                    owner,
                                    feature,
                                    expression));
                        }
                    }
                }
            }
        }
        return cmd;
    }

    /**
     * @param item
     *            The item to get the activity for.
     * @return The activity, or null.
     */
    private static Activity getContainingActivity(EObject item) {
        Activity activity = null;
        if (item instanceof Activity) {
            activity = (Activity) item;
        } else if (item != null) {
            EObject parent = item.eContainer();
            activity = getContainingActivity(parent);
        }
        return activity;
    }

    /**
     * @param javaMethodParameter
     * @return
     */
    public static boolean parentIsArray(JavaMethodParameter javaMethodParameter) {
        boolean isArray = false;
        Object parent = javaMethodParameter.getParent();
        if (parent instanceof JavaMethodParameter) {
            JavaMethodParameter parentParameter = (JavaMethodParameter) parent;
            if (parentParameter.isArray()) {
                isArray = true;
            }
        }
        return isArray;
    }
}