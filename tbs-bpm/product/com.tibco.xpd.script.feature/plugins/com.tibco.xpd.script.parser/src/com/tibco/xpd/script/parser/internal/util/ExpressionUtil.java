package com.tibco.xpd.script.parser.internal.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import antlr.Token;
import antlr.collections.AST;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.DefaultJsExpression;
import com.tibco.xpd.script.model.client.DefaultJsExpressionMethod;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.model.client.JsDataType;
import com.tibco.xpd.script.model.client.JsExpression;
import com.tibco.xpd.script.model.client.JsExpressionMethod;
import com.tibco.xpd.script.model.jscript.JScriptDefinitionReader;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.validator.jscript.AntlrExprImpl;

public class ExpressionUtil {

    @SuppressWarnings("unchecked")//$NON-NLS-1$
    public static List<String> getSupportedClasses() {
        List<String> allSupportedClassNames = new ArrayList<String>();
        List<JsClassDefinitionReader> classDefinitionReaders =
                getClassDefintionReader();
        for (JsClassDefinitionReader jsClassDefinitionReader : classDefinitionReaders) {
            List<String> supportedClassNames =
                    jsClassDefinitionReader.getSupportedClassNames();
            if (supportedClassNames != null) {
                allSupportedClassNames.addAll(supportedClassNames);
            }
        }
        return allSupportedClassNames;
    }

    @SuppressWarnings("unchecked")//$NON-NLS-1$
    public static List<JsClass> getSupportedJsClasses() {
        List<JsClass> allSupportedClasses = new ArrayList<JsClass>();
        List<JsClassDefinitionReader> classDefinitionReaders =
                getClassDefintionReader();
        for (JsClassDefinitionReader jsClassDefinitionReader : classDefinitionReaders) {
            List<JsClass> supportedClasses =
                    jsClassDefinitionReader.getSupportedClasses();
            if (supportedClasses != null) {
                allSupportedClasses.addAll(supportedClasses);
            }
        }
        return allSupportedClasses;
    }

    private static JScriptDefinitionReader classDefinitionReader = null;

    public static List<JsClassDefinitionReader> getClassDefintionReader() {
        if (classDefinitionReader == null) {
            classDefinitionReader = new JScriptDefinitionReader();
        }
        return Collections
                .singletonList((JsClassDefinitionReader) classDefinitionReader);
    }

    public static String getPropertyDataType(String className, String property,
            JsClassDefinitionReader classDefinitionReader) {
        if (classDefinitionReader == null) {
            return null;
        }
        List<JsClass> suppotedClasses =
                classDefinitionReader.getSupportedClasses();
        if (suppotedClasses == null) {
            return null;
        }
        JsClass tempClass = null;
        for (JsClass eachClass : suppotedClasses) {
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

    /**
     * Some strParamterType starts with double quotes, so removing them.
     * 
     * @param strParameterType
     * @return
     */
    public static String massagePassedString(String strParameterType) {
        String doubleQuotes = "\""; //$NON-NLS-1$
        String singleQuotes = "'"; //$NON-NLS-1$
        if (strParameterType != null
                && strParameterType.startsWith(doubleQuotes)
                && strParameterType.endsWith(doubleQuotes)) {
            strParameterType =
                    strParameterType
                            .substring(1, strParameterType.length() - 1);
            return strParameterType;
        } else if (strParameterType != null
                && strParameterType.startsWith(singleQuotes)
                && strParameterType.endsWith(singleQuotes))
            strParameterType =
                    strParameterType
                            .substring(1, strParameterType.length() - 1);
        return strParameterType;
    }
    
    public static IScriptRelevantData getTypeForAstExpression(AST expr,
            List<String> symbolTableKeyWords,
            Map<String, IScriptRelevantData> supportedScriptRelevantDataMap,
            List<JsClass> supportedJsClasses,
            Map<String, IScriptRelevantData> localVariablesMap,
            Map<String, IScriptRelevantData> localMethodsMap) {
        if (expr != null) {
            JsExpression jsExpression =
                    ExpressionUtil.getJsExpressionFromAST(expr,
                            symbolTableKeyWords,
                            supportedScriptRelevantDataMap);
            if (jsExpression != null) {
                JsDataType scriptRelevantDataType =
                        JScriptUtils.getScriptRelevantDataType(jsExpression,
                                supportedJsClasses,
                                supportedScriptRelevantDataMap,
                                localVariablesMap,
                                localMethodsMap);
                if (scriptRelevantDataType != null) {
                    return scriptRelevantDataType.getType();
                }
            }
        }
        return null;
    }

    public static JsExpression getJsExpressionFromAST(AST expression,
            List<String> keywords,
            Map<String, IScriptRelevantData> scriptRelevantDataMap) {
        JsExpression jsExpression = getJsExpressionFromAST(expression);
        jsExpression =
                parseObjectJsExpression(jsExpression, scriptRelevantDataMap);
        if (jsExpression != null && keywords != null) {
            String expressionName = jsExpression.getName();
            if (scriptRelevantDataMap != null
                    && !scriptRelevantDataMap.containsKey(expressionName)) {
                if (expressionName != null && keywords != null
                        && keywords.contains(expressionName)) {
                    jsExpression = jsExpression.getNextExpression();
                    return jsExpression;
                }
            }
        }
        return jsExpression;
    }

    private static JsExpression getJsExpressionFromAST(AST expression) {
        JsExpression jsExpression = new DefaultJsExpression();
        if (expression != null) {
            if (expression.getType() == JScriptTokenTypes.PLUS) {
                jsExpression = getJsPlusExpression(expression);
                jsExpression.setName(parseJsPlusExpressionName(jsExpression
                        .getName()));
            } else if (expression.getType() == JScriptTokenTypes.IDENT) {
                AST childExpression = expression.getFirstChild();
                if (childExpression == null) {
                    // It is the end of the tree
                    jsExpression.setName(expression.getText());
                }
            } else if (expression.getType() == JScriptTokenTypes.DOT) {
                AST childExpression = expression.getFirstChild();
                jsExpression = getJsExpressionFromAST(childExpression);
                JsExpression lastExpression = jsExpression.getLastExpression();
                JsExpression nextExpression = new DefaultJsExpression();
                AST nextSibling = childExpression.getNextSibling();
                if (nextSibling != null) {
                    nextExpression.setName(nextSibling.getText());
                    if (lastExpression == null) {
                        jsExpression.setNextExpression(nextExpression);
                    } else {
                        lastExpression.setNextExpression(nextExpression);
                    }
                }
            } else if (expression.getType() == JScriptTokenTypes.INDEX_OP) {
                AST childExpression = expression.getFirstChild();
                jsExpression = getJsExpressionFromAST(childExpression);
                JsExpression lastExpression = jsExpression.getLastExpression();
                AST nextSibling = childExpression.getNextSibling();
                if (nextSibling != null) {
                    JsExpression arrayJsExpression =
                            getJsExpressionFromAST(nextSibling);
                    if (lastExpression == null) {
                        jsExpression.setIsArray(true);
                        jsExpression.setArrayExpression(arrayJsExpression);
                    } else {
                        lastExpression.setIsArray(true);
                        lastExpression.setArrayExpression(arrayJsExpression);
                    }
                }
            } else if (expression.getType() == JScriptTokenTypes.EXPR) {
                AST childExpression = expression.getFirstChild();
                jsExpression = getJsExpressionFromAST(childExpression);
            } else if (expression.getType() == JScriptTokenTypes.METHOD_CALL) {
                // This can be the dot in the method or the method call if it is
                // a local method call
                AST childExpression = expression.getFirstChild();
                if (childExpression != null) {
                    if (childExpression.getType() == JScriptTokenTypes.DOT) {
                        AST methodExpression = childExpression.getFirstChild();
                        jsExpression = getJsExpressionFromAST(methodExpression);
                        JsExpression lastExpression =
                                jsExpression.getLastExpression();
                        if (lastExpression == null) {
                            jsExpression
                                    .setNextExpression(getJsMethodExpressionFromAST(childExpression));
                        } else {
                            lastExpression
                                    .setNextExpression(getJsMethodExpressionFromAST(childExpression));
                        }
                    } else if (childExpression.getType() == JScriptTokenTypes.IDENT) {
                        return getJsMethodExpressionFromAST(childExpression);
                    }
                }
            } else {
                jsExpression = new DefaultJsExpression();
                jsExpression.setName(expression.getText());
            }
        }
        return jsExpression;
    }

    // TODO: return the right expression concatenated, at the
    // moment only returns """", 1 or JsConsts.UNDEFINED_DATA_TYPE
    private static String parseJsPlusExpressionName(String expressionName) {
        String parsedExpression = "";
        String expressionTypes = JsConsts.UNDEFINED_DATA_TYPE;
        boolean areAnyStrings = false;
        if (expressionName != null) {
            String[] plusExpressions = expressionName.split("#&#");
            if (plusExpressions != null) {
                for (int i = 0; i < plusExpressions.length; i++) {
                    String tempExpression = plusExpressions[i];
                    if (tempExpression != null) {
                        tempExpression = tempExpression.trim();
                        if (JScriptUtils.isLiteralString(tempExpression)) {
                            expressionTypes = JsConsts.STRING;
                            areAnyStrings = true;
                        } else if (JScriptUtils.isNumber(tempExpression)) {
                            if (!areAnyStrings) {
                                expressionTypes = JsConsts.NUMBER;
                            }
                        } else {
                            expressionTypes = JsConsts.UNDEFINED_DATA_TYPE;
                            break;
                        }
                    }
                }
            }
        }
        if (expressionTypes.equals(JsConsts.STRING)) {
            parsedExpression = "\"\"";
        } else if (expressionTypes.equals(JsConsts.NUMBER)) {
            parsedExpression = "1";
        }
        return parsedExpression;
    }

    private static JsExpression parseObjectJsExpression(
            JsExpression jsExpression,
            Map<String, IScriptRelevantData> scriptRelevantDataMap) {
        if (jsExpression != null) {
            JsExpression arrayExpression = jsExpression.getArrayExpression();
            if (arrayExpression != null) {
                String fullArrayExpression =
                        jsExpression.getName()
                                + "[" + arrayExpression.getName() + "]";//$NON-NLS-1$//$NON-NLS-1$
                if (fullArrayExpression != null) {
                    IScriptRelevantData scripRelevantData =
                            scriptRelevantDataMap.get(fullArrayExpression);
                    if (scripRelevantData != null) {
                        jsExpression.setName(fullArrayExpression);
                        jsExpression.setArrayExpression(null);
                        jsExpression.setIsArray(false);
                    }
                }
            }
        }
        return jsExpression;
    }

    private static JsExpression getJsPlusExpression(AST expression) {
        JsExpression plusJsExpression = new DefaultJsExpression();
        if (expression != null) {
            if (expression.getType() == JScriptTokenTypes.STRING_LITERAL
                    || expression.getType() == JScriptTokenTypes.NUM_INT
                    || expression.getType() == JScriptTokenTypes.NUM_LONG
                    || expression.getType() == JScriptTokenTypes.NUM_FLOAT
                    || expression.getType() == JScriptTokenTypes.NUM_DOUBLE) {
                AST childExpression = expression.getFirstChild();
                if (childExpression == null) {
                    // It is the end of the tree
                    plusJsExpression.setName(expression.getText());
                }
            } else if (expression.getType() == JScriptTokenTypes.PLUS) {
                AST childExpression = expression.getFirstChild();
                if (childExpression != null) {
                    AST siblingExpression = childExpression.getNextSibling();
                    plusJsExpression = getJsPlusExpression(childExpression);
                    plusJsExpression.setName(plusJsExpression.getName()
                            + "#&#" + siblingExpression.getText());//$NON-NLS-1$
                }
            }
        }
        return plusJsExpression;
    }

    private static JsExpressionMethod getJsMethodExpressionFromAST(
            AST expression) {
        JsExpressionMethod jsExpressionMethod = new DefaultJsExpressionMethod();
        if (expression != null) {
            if (expression.getType() == JScriptTokenTypes.IDENT) {
                AST childExpression = expression.getFirstChild();
                if (childExpression == null) {
                    // Add the parameters to the method expression
                    AST parametersExpression = expression.getNextSibling();
                    jsExpressionMethod =
                            getJsMethodExpressionFromAST(parametersExpression);
                    jsExpressionMethod.setName(expression.getText());
                }
            }
            if (expression.getType() == JScriptTokenTypes.DOT) {
                AST childExpression = expression.getFirstChild();
                if (childExpression != null) {
                    AST methodExpression = childExpression.getNextSibling();
                    if (jsExpressionMethod != null) {
                        // Add the parameters to the method expression
                        AST parametersExpression = expression.getNextSibling();
                        jsExpressionMethod =
                                getJsMethodExpressionFromAST(parametersExpression);
                        jsExpressionMethod.setName(methodExpression.getText());
                    }
                }
            } else if (expression.getType() == JScriptTokenTypes.ELIST) {
                AST childExpression = expression.getFirstChild();
                if (childExpression != null) {
                    jsExpressionMethod =
                            getJsMethodExpressionFromAST(childExpression);
                }
            } else if (expression.getType() == JScriptTokenTypes.EXPR) {
                AST childExpression = expression.getFirstChild();
                // This is the parameter expression
                if (childExpression != null) {
                    JsExpression paramExpression =
                            getJsExpressionFromAST(childExpression);
                    // Add more parameters
                    AST nextParameterExpression = expression.getNextSibling();
                    if (nextParameterExpression != null) {
                        jsExpressionMethod =
                                getJsMethodExpressionFromAST(nextParameterExpression);
                    } else {
                        jsExpressionMethod =
                                getJsMethodExpressionFromAST(childExpression);
                    }
                    if (paramExpression != null) {
                        jsExpressionMethod
                                .insertMethodParameter(paramExpression);
                    }
                }

            }
        }

        return jsExpressionMethod;
    }

    public static List<JsExpression> getArrayIndexExpressionList(
            JsExpression jsExpression) {
        List<JsExpression> arrayIndexExpressionList =
                new ArrayList<JsExpression>();
        if (jsExpression != null) {
            JsExpression arrayIndexExpression =
                    jsExpression.getArrayExpression();
            if (arrayIndexExpression != null) {
                arrayIndexExpressionList.add(arrayIndexExpression);
            }
            if (JScriptUtils.hasMoreJSChildren(jsExpression)) {
                JsExpression nextExpression = jsExpression.getNextExpression();
                if (nextExpression != null) {
                    arrayIndexExpressionList
                            .addAll(getArrayIndexExpressionList(nextExpression));
                }
            }
        }
        return arrayIndexExpressionList;
    }

    public static String getJsExpressionName(JsExpression jsExpression) {
        String name = null;
        if (jsExpression != null) {
            name = jsExpression.getName();
        }
        return name;
    }
    
    public static IExpr createExpr(AST expr , Token token){
        return new AntlrExprImpl(expr, token);
    }


    protected static Map<String, Set<String>> EQUALITY_OPERATOR_TYPES_MAP;
    
    /**
     * @return the Map that provides relationship between incompatible Equality
     *         operator types.
     */
    public static Map<String, Set<String>> getInCompatibleEqualityOperatorTypesMap() {
        if (EQUALITY_OPERATOR_TYPES_MAP != null) {
            return EQUALITY_OPERATOR_TYPES_MAP;
        }
        EQUALITY_OPERATOR_TYPES_MAP = new HashMap<String, Set<String>>();

        return EQUALITY_OPERATOR_TYPES_MAP;
    }

}
