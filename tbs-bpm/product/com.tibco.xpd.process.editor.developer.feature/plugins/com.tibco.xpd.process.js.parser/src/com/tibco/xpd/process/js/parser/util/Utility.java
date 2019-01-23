package com.tibco.xpd.process.js.parser.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;

import antlr.collections.AST;

import com.tibco.xpd.process.js.model.Activator;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.IUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.parser.Messages;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;

public class Utility {

    private static List<String> ipmClasses = new ArrayList<String>();

    private static List<String> jsClasses = new ArrayList<String>();
    static {
        ipmClasses.add("IPEConversionUtil"); //$NON-NLS-1$
        ipmClasses.add("IPEEnvironmentUtil"); //$NON-NLS-1$

        jsClasses.add("Math"); //$NON-NLS-1$
        jsClasses.add("String"); //$NON-NLS-1$
    }

    public static List<JsClass> getSupportedJsClasses(String destination,
            String scriptType) {
        if (destination == null || destination.trim().length() < 1) {
            return Collections.emptyList();
        }
        List<String> destList = new ArrayList<String>();
        destList.add(destination);
        return getSupportedJsClasses(destList, scriptType);
    }

    public static List<JsClass> getSupportedJsClasses(
            List<String> destinationList, String scriptType) {
        List<JsClass> supportedJsClassList = new ArrayList<JsClass>();
        List<JsClassDefinitionReader> jsClassDefinitionReader =
                Collections.emptyList();
        try {
            jsClassDefinitionReader =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getJsClassDefinitionReader(destinationList,
                                    scriptType,
                                    ProcessJsConsts.JAVASCRIPT_GRAMMAR,
                                    ProcessJsConsts.JSCRIPT_DESTINATION);
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        if (jsClassDefinitionReader == null
                || jsClassDefinitionReader.isEmpty()) {
            return Collections.emptyList();
        }
        for (JsClassDefinitionReader reader : jsClassDefinitionReader) {
            List<JsClass> tempSupportedJsClassList =
                    reader.getSupportedClasses();
            if (tempSupportedJsClassList != null) {
                supportedJsClassList.addAll(tempSupportedJsClassList);
            }
        }
        return supportedJsClassList;
    }

    public static List<String> getSupportedClasses(String destination,
            String scriptType) {
        if (destination == null || destination.trim().length() < 1) {
            return Collections.emptyList();
        }
        List<String> destList = new ArrayList<String>();
        destList.add(destination);
        return getSupportedClasses(destList, scriptType);
    }

    public static List<String> getSupportedClasses(
            List<String> destinationList, String scriptType) {
        List<String> supportedClassNames = new ArrayList<String>();
        List<JsClassDefinitionReader> jsClassDefinitionReader =
                Collections.emptyList();
        try {
            jsClassDefinitionReader =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getJsClassDefinitionReader(destinationList,
                                    scriptType,
                                    ProcessJsConsts.JAVASCRIPT_GRAMMAR,
                                    ProcessJsConsts.JSCRIPT_DESTINATION);
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        if (jsClassDefinitionReader == null
                || jsClassDefinitionReader.isEmpty()) {
            return Collections.emptyList();
        }
        for (JsClassDefinitionReader reader : jsClassDefinitionReader) {
            List<String> tempSupportedClassNames =
                    reader.getSupportedClassNames();
            if (tempSupportedClassNames != null) {
                supportedClassNames.addAll(tempSupportedClassNames);
            }
        }
        if (supportedClassNames.isEmpty()) {
            supportedClassNames = getDummySupportedClasses(destinationList);
        }

        return supportedClassNames;
    }

    private static List<String> getDummySupportedClasses(
            List<String> destinationList) {
        List<String> supportedClassList = new ArrayList<String>();
        for (String strDest : destinationList) {
            if (strDest
                    .equals(Consts.IPM_DEST_FOR_EXPLICIT_JSCLASS_SUPPORT_OR_TRANSLATION)) {
                supportedClassList.addAll(ipmClasses);
            } else if (strDest.equals(ProcessJsConsts.JSCRIPT_DESTINATION)) {
                supportedClassList.addAll(jsClasses);
            }
        }
        return supportedClassList;
    }

    /**
     * Retunrs all classes which are configured for all other destination
     * environments other than iPE and iPM. These classes are assumed to be not
     * supported by the iPE and iPM destination environment so we can comment
     * the method call on these classes.
     * 
     * @return
     */
    public static List<String> getUnSupportedClasses(
            List<String> destinationList, String scriptType) {
        List<String> unSupportedClassNames = new ArrayList<String>();
        Activator default1 = Activator.getDefault();
        if (default1 != null) {
            List<JsClassDefinitionReader> jsClassDefinitionReader =
                    default1.getOtherJsClassDefinitionReader(destinationList,
                            scriptType,
                            ProcessJsConsts.JAVASCRIPT_GRAMMAR);
            for (JsClassDefinitionReader reader : jsClassDefinitionReader) {
                List<String> suppotedClassNames =
                        reader.getSupportedClassNames();
                unSupportedClassNames.addAll(suppotedClassNames);
            }
        } else {
            unSupportedClassNames = new ArrayList<String>();
            unSupportedClassNames.add("Math"); //$NON-NLS-1$
            unSupportedClassNames.add("String"); //$NON-NLS-1$
        }

        return unSupportedClassNames;
    }

    public static List<JsClassDefinitionReader> getClassDefintionReader(
            String destinationName, String scriptType) {
        List<JsClassDefinitionReader> classDefintionReader =
                Collections.emptyList();
        try {
            classDefintionReader =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getJsClassDefinitionReader(Collections.EMPTY_LIST,
                                    scriptType,
                                    ProcessJsConsts.JAVASCRIPT_GRAMMAR,
                                    destinationName);
        } catch (CoreException e) {
            throw new IllegalStateException(Messages.Utility_8);
        }
        if (classDefintionReader.isEmpty()) {
            throw new IllegalStateException(Messages.Utility_8);
        }
        return classDefintionReader;
    }

    public static String getPropertyDataType(String className, String property,
            String destinationName, String scriptType) {
        List<JsClassDefinitionReader> classDefintionReaders =
                getClassDefintionReader(destinationName, scriptType);
        return getPropertyDataType(className, property, classDefintionReaders);
    }

    public static String getPropertyDataType(String className, String property,
            List<JsClassDefinitionReader> classDefintionReaders) {
        if (classDefintionReaders == null || classDefintionReaders.isEmpty()) {
            return null;
        }
        List<JsClass> allSupportedClasses = new ArrayList<JsClass>();

        for (JsClassDefinitionReader jsClassDefinitionReader : classDefintionReaders) {
            List<JsClass> supportedClasses =
                    jsClassDefinitionReader.getSupportedClasses();
            if (supportedClasses != null) {
                allSupportedClasses.addAll(supportedClasses);
            }
        }

        JsClass tempClass = null;
        for (JsClass eachClass : allSupportedClasses) {
            String tempClassName = eachClass.getName();
            if (tempClassName.equals(className)) {
                tempClass = eachClass;
                break;
            }
        }
        if (tempClass == null) {
            return null;
        }
        List<JsAttribute> attributeList = tempClass.getAttributeList();
        if (attributeList == null) {
            return null;
        }
        JsAttribute tempAttribute = null;
        for (JsAttribute attribute : attributeList) {
            String attribName = attribute.getName();
            if (attribName.equals(property)) {
                tempAttribute = attribute;
                break;
            }
        }
        if (tempAttribute == null) {
            return null;
        }
        String dataType = tempAttribute.getDataType();
        return dataType;
    }

    public static boolean isDotOperatorAllowed(IScriptRelevantData varType) {
        if (varType instanceof IUMLScriptRelevantData) {
            return true;
        }
        return false;
    }

    /**
     * Utility method to find out whether script is empty or not
     * 
     * @param astTree
     * @return
     */
    public static boolean isEmptyASTTree(AST astTree) {
        //
        // Grab script elements until we find a non-empty one.
        //
        AST nextElement = astTree;
        while (nextElement != null) {
            if (JScriptTokenTypes.EMPTY_STAT != nextElement.getType()) {
                // Found a non-empty node.
                return false;
            }

            nextElement = nextElement.getNextSibling();
        }

        return true;
    }

}
