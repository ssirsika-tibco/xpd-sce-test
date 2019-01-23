package com.tibco.xpd.process.xpath.parser.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.wsdl.Definition;
import javax.wsdl.Types;
import javax.xml.namespace.QName;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.wst.wsdl.XSDSchemaExtensibilityElement;
import org.eclipse.xsd.XSDAttributeUse;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDComponent;
import org.eclipse.xsd.XSDConcreteComponent;
import org.eclipse.xsd.XSDModelGroup;
import org.eclipse.xsd.XSDParticleContent;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTypeDefinition;
import org.eclipse.xsd.impl.XSDAttributeUseImpl;
import org.eclipse.xsd.impl.XSDComplexTypeDefinitionImpl;
import org.eclipse.xsd.impl.XSDElementDeclarationImpl;
import org.eclipse.xsd.impl.XSDModelGroupImpl;
import org.eclipse.xsd.impl.XSDParticleImpl;
import org.jaxen.JaxenException;
import org.jaxen.dom.DOMXPath;
import org.jaxen.expr.AdditiveExpr;
import org.jaxen.expr.AllNodeStep;
import org.jaxen.expr.CommentNodeStep;
import org.jaxen.expr.DefaultXPathFactory;
import org.jaxen.expr.EqualityExpr;
import org.jaxen.expr.Expr;
import org.jaxen.expr.FilterExpr;
import org.jaxen.expr.FunctionCallExpr;
import org.jaxen.expr.LiteralExpr;
import org.jaxen.expr.LocationPath;
import org.jaxen.expr.LogicalExpr;
import org.jaxen.expr.MultiplicativeExpr;
import org.jaxen.expr.NumberExpr;
import org.jaxen.expr.PathExpr;
import org.jaxen.expr.Predicate;
import org.jaxen.expr.RelationalExpr;
import org.jaxen.expr.Step;
import org.jaxen.expr.TextNodeStep;
import org.jaxen.expr.UnaryExpr;
import org.jaxen.expr.UnionExpr;
import org.jaxen.expr.VariableReferenceExpr;
import org.jaxen.saxpath.Axis;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import antlr.collections.AST;

import com.tibco.xpd.implementer.script.BaseTypeUtil;
import com.tibco.xpd.implementer.script.WsdlUtil;
import com.tibco.xpd.implementer.script.XmlException;
import com.tibco.xpd.process.xpath.model.XPathConsts;
import com.tibco.xpd.process.xpath.parser.antlr.TCDefaultNumberExpr;
import com.tibco.xpd.process.xpath.parser.antlr.XPathTokenTypes;
import com.tibco.xpd.process.xpath.parser.validator.xpath.Messages;
import com.tibco.xpd.script.model.client.DefaultScriptRelevantData;
import com.tibco.xpd.script.model.client.DefaultUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.IUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsMethod;
import com.tibco.xpd.script.model.client.JsMethodParam;
import com.tibco.xpd.script.model.client.JsReference;
import com.tibco.xpd.script.model.internal.client.ContentAssistElement;
import com.tibco.xpd.script.model.internal.xpath.XPathUtil;

public class ProcessXPathUtil {

    //private static final String DATETIMEPATTERN = "dd/MM/yy HH:mm"; //$NON-NLS-1$ 

    private static DefaultXPathFactory xpathFactory;

    public static DefaultXPathFactory getXPathFactory() {
        if (xpathFactory == null) {
            xpathFactory = new DefaultXPathFactory();
        }
        return xpathFactory;
    }

    /**
     * Creates a variable reference XPath expression for a given AST tree. Antlr
     * does not support prefix in variable reference so prefix is always null
     * 
     * @param expression
     * @return VariableReferenceExpr
     */
    public static VariableReferenceExpr createXPathVariableRefExprFromAST(
            AST astExpr) throws JaxenException {
        VariableReferenceExpr finalVariableRefExpr = null;
        String variableName = null;
        String prefix = "";//$NON-NLS-1$
        // Get the factory
        DefaultXPathFactory xpathFactory = ProcessXPathUtil.getXPathFactory();
        if (astExpr != null && astExpr.getType() == XPathTokenTypes.IDENTIFIER) {
            variableName = astExpr.getText();
        }
        if (variableName != null) {
            try {
                finalVariableRefExpr =
                        xpathFactory.createVariableReferenceExpr(prefix,
                                variableName);
            } catch (JaxenException je) {
                je.printStackTrace();
                throw je;
            }
        }
        return finalVariableRefExpr;
    }

    /**
     * Creates a literal XPath expression for a given AST tree.
     * 
     * @param expression
     * @return VariableReferenceExpr
     */
    public static LiteralExpr createXPathLiteralExprFromAST(AST astExpr)
            throws JaxenException {
        LiteralExpr finalLiteralExpr = null;
        String literal = null;
        // Get the factory
        DefaultXPathFactory xpathFactory = ProcessXPathUtil.getXPathFactory();
        if (astExpr != null && astExpr.getType() == XPathTokenTypes.LITERAL) {
            literal = astExpr.getText();
        }
        if (literal != null) {
            try {
                finalLiteralExpr = xpathFactory.createLiteralExpr(literal);
            } catch (JaxenException je) {
                je.printStackTrace();
                throw je;
            }
        }
        return finalLiteralExpr;
    }

    /**
     * Creates a function call XPath expression for a given AST tree.
     * 
     * @param expression
     * @return VariableReferenceExpr
     */
    public static FunctionCallExpr createXPathFunctionCallExprFromAST(
            AST astExpr) throws JaxenException {
        FunctionCallExpr finalFunctionCallExpr = null;
        // Get the factory
        DefaultXPathFactory xpathFactory = ProcessXPathUtil.getXPathFactory();
        if (astExpr != null && astExpr.getType() == XPathTokenTypes.LEFT_PAREN) {
            AST functionNameAST = astExpr.getFirstChild();
            if (functionNameAST != null
                    && functionNameAST.getType() == XPathTokenTypes.IDENTIFIER) {
                finalFunctionCallExpr =
                        xpathFactory.createFunctionCallExpr(null,
                                functionNameAST.getText());
            }
        }
        return finalFunctionCallExpr;
    }

    /**
     * Creates a Number XPath expression for a given AST tree.
     * 
     * @param expression
     * @return VariableReferenceExpr
     */
    public static NumberExpr createXPathNumberExprFromAST(AST astExpr)
            throws JaxenException {
        NumberExpr finalNumberExpr = null;
        String number = null;
        // Get the factory
        if (astExpr != null && astExpr.getType() == XPathTokenTypes.NUMBER) {
            number = astExpr.getText();
        }
        if (number != null) {
            try {
                try {
                    int iNumber = Integer.parseInt(number);
                    finalNumberExpr = new TCDefaultNumberExpr(iNumber);
                    return finalNumberExpr;
                } catch (NumberFormatException nfe) {
                    // It is a double
                }
                double dNumber = Double.parseDouble(number);
                finalNumberExpr = new TCDefaultNumberExpr(dNumber);
            } catch (NumberFormatException nfe) {
                JaxenException je = new JaxenException(nfe.getMessage());
                throw je;
            }
        }
        return finalNumberExpr;
    }

    /**
     * Creates a processing-instruction step for a given AST tree.
     * 
     * @param expression
     * @return Step
     */
    public static Step createXPathProcessingInstructionStepFromAST(AST astExpr,
            int axis) throws JaxenException {
        Step processingInstructionStep = null;
        // Get the factory
        DefaultXPathFactory xpathFactory = ProcessXPathUtil.getXPathFactory();
        if (astExpr != null && astExpr.getType() == XPathTokenTypes.IDENTIFIER) {
            AST leftParen = astExpr.getNextSibling();
            if (leftParen != null
                    && leftParen.getType() == XPathTokenTypes.LEFT_PAREN) {
                AST nameAST = leftParen.getNextSibling();
                if (nameAST != null) {
                    String name = nameAST.getText();
                    processingInstructionStep =
                            xpathFactory
                                    .createProcessingInstructionNodeStep(axis,
                                            name);
                }
            }
        }
        return processingInstructionStep;
    }

    /**
     * Creates the axis for a given AST tree.
     * 
     * @param expression
     * @return the axis
     */
    public static int createXPathAxisFromAST(AST astExpr) throws JaxenException {
        int axis = 1;
        String axisName = null;
        if (astExpr != null
                && astExpr.getType() == XPathTokenTypes.DOUBLE_COLON) {
            AST axisAST = astExpr.getFirstChild();
            if (axisAST != null) {
                axisName = axisAST.getText();
                if (axisName != null) {
                    axis = Axis.lookup(axisName);
                }
            }
        } else if (astExpr.getType() == XPathTokenTypes.AT) {
            axis = 1;
        } else {
            axis = 0;
        }
        if (axis == 0) {
            throw new JaxenException(
                    Messages.XPathVariableReferenceValidator_illegal_axis_name);
        }
        return axis;
    }

    /**
     * Creates the Name step for given AST tree.
     * 
     * @param expression
     * @return the Step
     */
    public static Step createXPathNameStepFromAST(int axis, String prefix,
            AST astExpr) throws JaxenException {
        Step nameStep = null;
        DefaultXPathFactory xpathFactory = ProcessXPathUtil.getXPathFactory();
        if (astExpr != null) {
            AST siblingAST = astExpr.getNextSibling();
            switch (astExpr.getType()) {
            case XPathTokenTypes.IDENTIFIER:
                if (siblingAST != null) {
                    // This is the prefix
                    if (siblingAST.getType() == XPathTokenTypes.COLON) {
                        AST nameAST = siblingAST.getNextSibling();
                        if (nameAST != null) {
                            nameStep =
                                    xpathFactory.createNameStep(axis,
                                            prefix,
                                            nameAST.getText());
                        }
                    }
                } else {
                    nameStep =
                            xpathFactory.createNameStep(axis,
                                    prefix,
                                    astExpr.getText());
                }
                break;
            case XPathTokenTypes.DOUBLE_COLON:
                if (siblingAST != null) {
                    if (prefix == null) {
                        nameStep =
                                xpathFactory.createNameStep(axis,
                                        prefix,
                                        siblingAST.getText());
                    } else {
                        AST prefixAST = siblingAST.getNextSibling();
                        if (prefixAST != null) {
                            AST nameAST = prefixAST.getNextSibling();
                            if (nameAST != null) {
                                nameStep =
                                        xpathFactory.createNameStep(axis,
                                                prefix,
                                                nameAST.getText());
                            }
                        }
                    }
                }
                break;
            case XPathTokenTypes.AT:
                axis = Axis.ATTRIBUTE;
                if (siblingAST != null) {
                    nameStep =
                            xpathFactory.createNameStep(axis,
                                    prefix,
                                    siblingAST.getText());
                }
                break;
            case XPathTokenTypes.STAR:
                axis = Axis.CHILD;
                nameStep = xpathFactory.createNameStep(axis, prefix, "*");//$NON-NLS-1$
                break;

            default:
                break;
            }
        }
        return nameStep;
    }

    public static IScriptRelevantData getXPathExpressionType(Expr expression,
            List<JsMethod> supportedMethods,
            Map<String, IScriptRelevantData> supportedScriptRelevantData,
            Part wsdlPart, Map<String, String> namespaces) {
        String sType = XPathConsts.XPATH_TYPE_UNDEFINED;
        IScriptRelevantData type =
                new DefaultScriptRelevantData(sType, sType, false);
        if (expression instanceof PathExpr) {
            if (ProcessXPathUtil
                    .isVariableReferenceWithPath((PathExpr) expression)) {
                type =
                        ProcessXPathUtil
                                .getVariableReferenceWithPathExpressionType((PathExpr) expression,
                                        supportedMethods,
                                        supportedScriptRelevantData,
                                        wsdlPart,
                                        namespaces);
            } else {
                sType = XPathConsts.XPATH_TYPE_UNDEFINED;
                type = new DefaultScriptRelevantData(sType, sType, false);
            }
        } else if (expression instanceof LocationPath) {
            LocationPath locationPathExpr = (LocationPath) expression;
            type =
                    getLocationExpressionType(supportedMethods,
                            locationPathExpr,
                            supportedScriptRelevantData,
                            wsdlPart,
                            namespaces);
        } else if (expression instanceof UnionExpr) {

            sType = XPathConsts.XPATH_TYPE_NODESET;
            type = new DefaultScriptRelevantData(sType, sType, false);

        } else if (expression instanceof LogicalExpr
                || expression instanceof EqualityExpr
                || expression instanceof RelationalExpr) {

            sType = XPathConsts.XPATH_TYPE_BOOLEAN;
            type = new DefaultScriptRelevantData(sType, sType, false);

        } else if (expression instanceof AdditiveExpr
                || expression instanceof MultiplicativeExpr) {
            sType = XPathConsts.XPATH_TYPE_NUMBER;
            type = new DefaultScriptRelevantData(sType, sType, false);

        } else if (expression instanceof NumberExpr) {

            NumberExpr numberExpr = (NumberExpr) expression;
            type = getNumberExpressionType(numberExpr);

        } else if (expression instanceof LiteralExpr) {
            sType = XPathConsts.XPATH_TYPE_STRING;
            type = new DefaultScriptRelevantData(sType, sType, false);

        } else if (expression instanceof VariableReferenceExpr) {
            VariableReferenceExpr variableReference =
                    (VariableReferenceExpr) expression;
            type =
                    ProcessXPathUtil
                            .getVariableReferenceExpressionType(variableReference,
                                    supportedScriptRelevantData,
                                    wsdlPart);
        } else if (expression instanceof FunctionCallExpr) {
            FunctionCallExpr functionCallExpr = (FunctionCallExpr) expression;
            type =
                    ProcessXPathUtil
                            .getFunctionCallExpressionType(functionCallExpr,
                                    supportedMethods);

        } else if (expression instanceof FilterExpr) {
            FilterExpr filterExpression = (FilterExpr) expression;
            type =
                    getFilterExpressionType(filterExpression,
                            supportedMethods,
                            supportedScriptRelevantData,
                            wsdlPart,
                            namespaces);
        } else if (expression instanceof UnaryExpr) {
            UnaryExpr unaryExpression = (UnaryExpr) expression;
            Expr expr = unaryExpression.getExpr();
            type =
                    ProcessXPathUtil.getXPathExpressionType(expr,
                            supportedMethods,
                            supportedScriptRelevantData,
                            wsdlPart,
                            namespaces);
        }
        return type;
    }

    private static IScriptRelevantData getNumberExpressionType(
            NumberExpr numberExpr) {
        String sType = XPathConsts.XPATH_TYPE_UNDEFINED;
        if (numberExpr != null) {
            String strNumber = numberExpr.getText();
            if (strNumber != null) {
                boolean isDouble = false;
                try {
                    Integer.parseInt(strNumber);
                    sType = XPathConsts.INTEGER;
                } catch (NumberFormatException nfe) {
                    // It is a double
                    isDouble = true;
                }
                if (isDouble) {
                    sType = XPathConsts.DECIMAL;
                }
            }
        }
        IScriptRelevantData type =
                new DefaultScriptRelevantData(sType, sType, false);
        return type;
    }

    private static IScriptRelevantData getFunctionCallExpressionType(
            FunctionCallExpr functionCallExpr, List<JsMethod> supportedMethods) {
        String sType = XPathConsts.XPATH_TYPE_UNDEFINED;
        IScriptRelevantData type =
                new DefaultScriptRelevantData(sType, sType, false);
        if (functionCallExpr != null) {
            String functionName = functionCallExpr.getFunctionName();
            if (functionName != null) {
                for (JsMethod jsMethod : supportedMethods) {
                    if (jsMethod != null
                            && jsMethod.getName().equals(functionName)) {
                        JsMethodParam returnType = jsMethod.getReturnType();
                        if (returnType != null) {
                            sType = returnType.getType();
                            type =
                                    new DefaultScriptRelevantData(sType, sType,
                                            jsMethod.isMultiple());
                        }
                    }
                }
            }
        }
        return type;
    }

    public static IScriptRelevantData getLiteralExpressionType(
            LiteralExpr literalExpr) {
        String sType = XPathConsts.XPATH_TYPE_STRING;
        IScriptRelevantData type =
                new DefaultScriptRelevantData(sType, sType, false);
        if (literalExpr != null) {
            String literal = literalExpr.getLiteral();
            // Check if it is numeric
            try {
                Double.parseDouble(literal);
                try {
                    Integer.parseInt(literal);
                    sType = XPathConsts.INTEGER;
                    type = new DefaultScriptRelevantData(sType, sType, false);
                    return type;
                } catch (NumberFormatException e) {
                    // Ignore, it is not integer number
                }
                sType = XPathConsts.DECIMAL;
                type = new DefaultScriptRelevantData(sType, sType, false);

                return type;
            } catch (NumberFormatException e) {
                // Ignore, it is not decimal number
            }
            // Check if it is date time
            /*
             * SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
             * simpleDateFormat.applyPattern(DATETIMEPATTERN);
             * simpleDateFormat.setLenient(false); try {
             * simpleDateFormat.parse(literal); sType = XPathConsts.DATETIME;
             * type = new DefaultScriptRelevantData(sType, sType, false); return
             * type; } catch (Exception ex) { // Ignore, it is not date time }
             */
        }
        return type;
    }

    private static IScriptRelevantData getVariableReferenceExpressionType(
            VariableReferenceExpr variableRefExpr,
            Map<String, IScriptRelevantData> supportedScriptRelevantData,
            Part wsdlPart) {
        String sType = XPathConsts.XPATH_TYPE_UNDEFINED;
        IScriptRelevantData type =
                new DefaultScriptRelevantData(sType, sType, false);
        if (variableRefExpr != null) {
            String variableName = variableRefExpr.getVariableName();
            if (variableName != null) {
                IScriptRelevantData srd =
                        supportedScriptRelevantData.get(variableName);
                if (srd != null) {
                    type = srd;
                } else if (wsdlPart != null) {
                    String partName = null;
                    if (wsdlPart.getElementName() != null) {
                        partName = wsdlPart.getElementName().getLocalPart();
                    } else {
                        partName = wsdlPart.getName();
                    }
                    if (partName != null && partName.equals(variableName)) {
                        XSDTypeDefinition typeDefinition =
                                wsdlPart.getTypeDefinition();
                        if (typeDefinition != null) {
                            type =
                                    new DefaultScriptRelevantData(variableName,
                                            typeDefinition.getName(), false);
                        }
                    }
                }
            }
        }
        return type;
    }

    private static IScriptRelevantData getLocationExpressionType(
            List<JsMethod> supportedMethods, LocationPath locationPathExpr,
            Map<String, IScriptRelevantData> supportedScriptRelevantData,
            Part wsdlPart, Map<String, String> namespaces) {
        String sType = XPathConsts.XPATH_TYPE_UNDEFINED;
        IScriptRelevantData type =
                new DefaultScriptRelevantData(sType, sType, false);
        if (locationPathExpr != null) {
            // Try to get the type in the wsdl
            if (wsdlPart != null) {
                // get the last step
                Step lastStep = null;
                List steps = locationPathExpr.getSteps();
                if (steps != null && !steps.isEmpty()) {
                    lastStep = (Step) steps.get(steps.size() - 1);
                }
                if (lastStep instanceof TextNodeStep
                        || lastStep instanceof CommentNodeStep) {
                    sType = XPathConsts.XPATH_TYPE_STRING;
                    type = new DefaultScriptRelevantData(sType, sType, false);
                } else if (lastStep instanceof AllNodeStep) {
                    sType = XPathConsts.XPATH_TYPE_NODESET;
                    type = new DefaultScriptRelevantData(sType, sType, false);
                } else {
                    type =
                            ProcessXPathUtil
                                    .getLocationPathExpressionTypeAgainstWsdl(locationPathExpr,
                                            wsdlPart,
                                            supportedScriptRelevantData,
                                            supportedMethods,
                                            namespaces);
                }
            } else {
                // Get it in the supportedScriptRelevantData
                type =
                        ProcessXPathUtil
                                .getLocationPathExpressionTypeAgainstSupportedSRD(locationPathExpr,
                                        supportedMethods,
                                        supportedScriptRelevantData);
            }
        }
        return type;
    }

    private static IScriptRelevantData getLocationPathExpressionTypeAgainstSupportedSRD(
            LocationPath locationPathExpr, List<JsMethod> supportedMethods,
            Map<String, IScriptRelevantData> supportedScriptRelevantData) {
        String sType = XPathConsts.XPATH_TYPE_UNDEFINED;
        IScriptRelevantData type =
                new DefaultScriptRelevantData(sType, sType, false);
        if (locationPathExpr != null) {
            boolean isMultiple = false;
            List<Step> stepList = locationPathExpr.getSteps();
            if (stepList != null && !stepList.isEmpty()) {
                Iterator<Step> stepIterator = stepList.iterator();
                // Get the type of the last step
                Step firstStep = stepIterator.next();
                if (firstStep != null) {
                    IScriptRelevantData variableRelevantData =
                            supportedScriptRelevantData.get(XPathUtil
                                    .getStepName(firstStep));
                    if (variableRelevantData instanceof IUMLScriptRelevantData) {
                        JsClass jsClass =
                                ((IUMLScriptRelevantData) variableRelevantData)
                                        .getJsClass();
                        List<JsAttribute> attributeList =
                                jsClass.getAttributeList();
                        List<JsReference> referenceList =
                                jsClass.getReferenceList();
                        List<ContentAssistElement> elementList =
                                new ArrayList<ContentAssistElement>();
                        if (attributeList != null) {
                            elementList.addAll(attributeList);
                        }
                        if (referenceList != null) {
                            elementList.addAll(referenceList);
                        }
                        type =
                                ProcessXPathUtil
                                        .getVariableReferenceNextStepType(stepIterator,
                                                supportedMethods,
                                                supportedScriptRelevantData,
                                                elementList,
                                                isMultiple);
                    }
                }
            }
        }
        return type;
    }

    private static IScriptRelevantData getFilterExpressionType(
            FilterExpr filterExpression, List<JsMethod> supportedMethods,
            Map<String, IScriptRelevantData> supportedScriptRelevantData,
            Part wsdlPart, Map<String, String> namespaces) {
        Expr expr = filterExpression.getExpr();
        IScriptRelevantData type =
                ProcessXPathUtil.getXPathExpressionType(expr,
                        supportedMethods,
                        supportedScriptRelevantData,
                        wsdlPart,
                        namespaces);
        if (type != null) {
            List<Predicate> predicates = filterExpression.getPredicates();
            if (predicates != null) {
                boolean isMultipleElement = false;
                for (Predicate predicate : predicates) {
                    if (ProcessXPathUtil.isNumberPredicate(predicate,
                            wsdlPart,
                            supportedMethods,
                            supportedScriptRelevantData,
                            namespaces)) {
                        isMultipleElement = true;
                        break;
                    }
                }
                if (isMultipleElement) {
                    type.setIsArray(false);
                }
            }
        }
        return type;
    }

    private static IScriptRelevantData getVariableReferenceWithPathExpressionType(
            PathExpr pathExpr, List<JsMethod> supportedMethods,
            Map<String, IScriptRelevantData> supportedScriptRelevantData,
            Part wsdlPart, Map<String, String> namespaces) {
        String sType = XPathConsts.XPATH_TYPE_UNDEFINED;
        IScriptRelevantData type =
                new DefaultScriptRelevantData(sType, sType, false);
        if (pathExpr != null) {
            String variableExpressionStr = pathExpr.getText();
            if (variableExpressionStr != null
                    && variableExpressionStr.trim().startsWith("$")) {//$NON-NLS-1$
                String newExpressionStr =
                        "/" + variableExpressionStr.substring(1); //$NON-NLS-1$
                DOMXPath xpathExpression = null;
                try {
                    // TODO: Use our antlr parser, DOM parser
                    // works fine, but we don't control it
                    xpathExpression =
                            new org.jaxen.dom.DOMXPath(newExpressionStr);
                    type =
                            ProcessXPathUtil
                                    .getXPathExpressionType(xpathExpression
                                            .getRootExpr(),
                                            supportedMethods,
                                            supportedScriptRelevantData,
                                            wsdlPart,
                                            namespaces);
                } catch (JaxenException ex) {
                    // Do nothing this will return undefined type
                }
            }
        }
        return type;
    }

    private static boolean hasNumberPredicates(Step step, Part wsdlPart,
            List<JsMethod> supportedMethods,
            Map<String, IScriptRelevantData> supportedScriptRelevantData,
            Map<String, String> namespaces) {
        boolean numberPredicates = false;
        if (step != null) {
            List<Predicate> predicates = step.getPredicates();
            if (predicates != null) {
                for (Predicate predicate : predicates) {
                    if (ProcessXPathUtil.isNumberPredicate(predicate,
                            wsdlPart,
                            supportedMethods,
                            supportedScriptRelevantData,
                            namespaces)) {
                        numberPredicates = true;
                        break;
                    }
                }
            }
        }
        return numberPredicates;
    }

    public static boolean isPositionPredicate(Predicate predicate) {
        boolean positionPredicate = false;
        if (predicate != null) {
            Expr expr = predicate.getExpr();
            if (expr instanceof EqualityExpr) {
                EqualityExpr equalityExpr = (EqualityExpr) expr;
                Expr lhsExpr = equalityExpr.getLHS();
                if (lhsExpr instanceof FilterExpr) {
                    FilterExpr filterExpr = (FilterExpr) lhsExpr;
                    Expr functionExpr = filterExpr.getExpr();
                    if (functionExpr instanceof FunctionCallExpr) {
                        FunctionCallExpr functionCallExpr =
                                (FunctionCallExpr) functionExpr;
                        if (functionCallExpr.getFunctionName() != null
                                && functionCallExpr.getFunctionName()
                                        .equals("position")) {//$NON-NLS-1$
                            Expr rhsExpr = equalityExpr.getRHS();
                            if (rhsExpr instanceof FilterExpr) {
                                FilterExpr rhsFilterExpr = (FilterExpr) rhsExpr;
                                Expr numberExpr = rhsFilterExpr.getExpr();
                                if (numberExpr instanceof NumberExpr) {
                                    positionPredicate = true;
                                }
                            } else if (rhsExpr instanceof UnaryExpr) {
                                UnaryExpr unaryExpr = (UnaryExpr) rhsExpr;
                                Expr unaryExpr2 = unaryExpr.getExpr();
                                if (unaryExpr2 instanceof FilterExpr) {
                                    FilterExpr filterUnaryExpr =
                                            (FilterExpr) unaryExpr2;
                                    Expr numberExpr = filterUnaryExpr.getExpr();
                                    if (numberExpr instanceof NumberExpr) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (expr instanceof UnaryExpr) {
                UnaryExpr unaryExpr = (UnaryExpr) expr;
                Expr unaryExpr2 = unaryExpr.getExpr();
                if (unaryExpr2 instanceof FilterExpr) {
                    FilterExpr filterExpr = (FilterExpr) unaryExpr2;
                    Expr numberExpr = filterExpr.getExpr();
                    if (numberExpr instanceof NumberExpr) {
                        return true;
                    }
                }
            } else if (expr instanceof FilterExpr) {
                FilterExpr filterExpr = (FilterExpr) expr;
                Expr numberExpr = filterExpr.getExpr();
                if (numberExpr instanceof NumberExpr) {
                    positionPredicate = true;
                }
            }
        }
        return positionPredicate;
    }

    public static NumberExpr getPositionPredicateValue(Predicate predicate) {
        NumberExpr positionPredicateValue = null;
        if (predicate != null) {
            Expr expr = predicate.getExpr();
            if (expr instanceof EqualityExpr) {
                EqualityExpr equalityExpr = (EqualityExpr) expr;
                Expr lhsExpr = equalityExpr.getLHS();
                if (lhsExpr instanceof FilterExpr) {
                    FilterExpr filterExpr = (FilterExpr) lhsExpr;
                    Expr functionExpr = filterExpr.getExpr();
                    if (functionExpr instanceof FunctionCallExpr) {
                        FunctionCallExpr functionCallExpr =
                                (FunctionCallExpr) functionExpr;
                        if (functionCallExpr.getFunctionName() != null
                                && functionCallExpr.getFunctionName()
                                        .equals("position")) {//$NON-NLS-1$
                            Expr rhsExpr = equalityExpr.getRHS();
                            if (rhsExpr instanceof FilterExpr) {
                                FilterExpr rhsFilterExpr = (FilterExpr) rhsExpr;
                                Expr numberExpr = rhsFilterExpr.getExpr();
                                if (numberExpr instanceof NumberExpr) {
                                    NumberExpr number = (NumberExpr) numberExpr;
                                    positionPredicateValue = number;
                                }
                            } else if (rhsExpr instanceof UnaryExpr) {
                                UnaryExpr unaryExpr = (UnaryExpr) rhsExpr;
                                Expr unaryExpr2 = unaryExpr.getExpr();
                                if (unaryExpr2 instanceof FilterExpr) {
                                    FilterExpr filterUnaryExpr =
                                            (FilterExpr) unaryExpr2;
                                    Expr numberExpr = filterUnaryExpr.getExpr();
                                    if (numberExpr instanceof NumberExpr) {
                                        NumberExpr number =
                                                (NumberExpr) numberExpr;
                                        try {
                                            NumberExpr newNumberExpr =
                                                    XPathUtil
                                                            .getXPathFactory()
                                                            .createNumberExpr(-number
                                                                    .getNumber()
                                                                    .doubleValue());
                                            positionPredicateValue =
                                                    newNumberExpr;
                                        } catch (JaxenException e) {
                                            // Ignore
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (expr instanceof UnaryExpr) {
                UnaryExpr unaryExpr = (UnaryExpr) expr;
                Expr unaryExpr2 = unaryExpr.getExpr();
                if (unaryExpr2 instanceof FilterExpr) {
                    FilterExpr filterExpr = (FilterExpr) unaryExpr2;
                    Expr numberExpr = filterExpr.getExpr();
                    if (numberExpr instanceof NumberExpr) {
                        NumberExpr number = (NumberExpr) numberExpr;
                        try {
                            NumberExpr newNumberExpr =
                                    XPathUtil.getXPathFactory()
                                            .createNumberExpr(-number
                                                    .getNumber().doubleValue());
                            positionPredicateValue = newNumberExpr;
                        } catch (JaxenException e) {
                            // Ignore
                        }
                    }
                }
            } else if (expr instanceof FilterExpr) {
                FilterExpr filterExpr = (FilterExpr) expr;
                Expr numberExpr = filterExpr.getExpr();
                if (numberExpr instanceof NumberExpr) {
                    NumberExpr number = (NumberExpr) numberExpr;
                    positionPredicateValue = number;
                }
            }
        }
        return positionPredicateValue;
    }

    private static boolean isNumberPredicate(Predicate predicate,
            Part wsdlPart, List<JsMethod> supportedMethods,
            Map<String, IScriptRelevantData> supportedScriptRelevantData,
            Map<String, String> namespaces) {
        boolean numberPredicate = false;
        Expr expression = predicate.getExpr();
        if (expression != null) {
            IScriptRelevantData predicateType =
                    ProcessXPathUtil.getXPathExpressionType(expression,
                            supportedMethods,
                            supportedScriptRelevantData,
                            wsdlPart,
                            namespaces);
            if (predicateType != null) {
                String sPredicateType = predicateType.getType();
                if (sPredicateType != null
                        && (sPredicateType
                                .equals(XPathConsts.XPATH_TYPE_NUMBER)
                                || sPredicateType.equals(XPathConsts.DECIMAL)
                                || sPredicateType.equals(XPathConsts.INT)
                                || sPredicateType.equals(XPathConsts.INTEGER)
                                || sPredicateType.equals(XPathConsts.DECIMAL)
                                || sPredicateType.equals(XPathConsts.DOUBLE) || sPredicateType
                                    .equals(XPathConsts.FLOAT))) {
                    numberPredicate = true;
                }
            }
        }
        return numberPredicate;
    }

    private static IScriptRelevantData getWsdlComponentType(
            XSDComponent wsdlComponent) {
        String sType = XPathConsts.XPATH_TYPE_UNDEFINED;
        IScriptRelevantData type =
                new DefaultScriptRelevantData(sType, sType, false);
        if (wsdlComponent instanceof XSDElementDeclarationImpl) {
            XSDElementDeclarationImpl elementDeclarationImpl =
                    (XSDElementDeclarationImpl) wsdlComponent;
            Object eContainer = null;
            boolean multiple = false;
            String sElementTypeDefinition = XPathConsts.XPATH_TYPE_UNDEFINED;
            if (elementDeclarationImpl.isElementDeclarationReference()) {
                XSDElementDeclarationImpl resolvedElementDeclarationImpl =
                        (XSDElementDeclarationImpl) elementDeclarationImpl
                                .getResolvedElementDeclaration();
                XSDTypeDefinition elementTypeDefinition =
                        resolvedElementDeclarationImpl.getTypeDefinition();
                if (elementTypeDefinition != null) {
                    sElementTypeDefinition = elementTypeDefinition.getName();
                    eContainer = elementDeclarationImpl.eContainer();
                }
            } else {
                XSDTypeDefinition elementTypeDefinition =
                        elementDeclarationImpl.getTypeDefinition();
                if (elementTypeDefinition != null) {
                    sElementTypeDefinition = elementTypeDefinition.getName();
                    eContainer = elementDeclarationImpl.eContainer();
                    if (sElementTypeDefinition == null
                            && elementTypeDefinition.getSimpleType() != null) {
                        XSDSimpleTypeDefinition simpleTypeDefinition =
                                elementTypeDefinition.getSimpleType();
                        XSDSimpleTypeDefinition primitiveTypeDefinition =
                                simpleTypeDefinition
                                        .getPrimitiveTypeDefinition();
                        if (primitiveTypeDefinition != null) {
                            sElementTypeDefinition =
                                    primitiveTypeDefinition.getName();
                            eContainer = primitiveTypeDefinition.eContainer();
                        }
                    }
                }
            }
            if (eContainer instanceof XSDParticleImpl) {
                XSDParticleImpl container = (XSDParticleImpl) eContainer;
                int maxOccurs = container.getMaxOccurs();
                if (maxOccurs == -1 || maxOccurs > 1) {
                    multiple = true;
                }
            }
            if (sElementTypeDefinition != null) {
                type =
                        new DefaultScriptRelevantData(sElementTypeDefinition,
                                sElementTypeDefinition, multiple);
            }
        } else if (wsdlComponent instanceof XSDAttributeUseImpl) {
            XSDAttributeUseImpl attributeUseImpl =
                    (XSDAttributeUseImpl) wsdlComponent;
            XSDTypeDefinition elementTypeDefinition =
                    attributeUseImpl.getAttributeDeclaration()
                            .getTypeDefinition();
            if (elementTypeDefinition != null) {
                String sElementTypeName = elementTypeDefinition.getName();
                boolean multiple = false;
                Object eContainer = attributeUseImpl.eContainer();
                if (eContainer instanceof XSDParticleImpl) {
                    XSDParticleImpl container = (XSDParticleImpl) eContainer;
                    int maxOccurs = container.getMaxOccurs();
                    if (maxOccurs == -1 || maxOccurs > 1) {
                        multiple = true;
                    }
                }
                String sElementTypeDefinition = sElementTypeName;
                // Convert to the base supported type or a comma separated list
                // for unions
                if (elementTypeDefinition instanceof XSDSimpleTypeDefinition) {
                    XSDSimpleTypeDefinition simple =
                            (XSDSimpleTypeDefinition) elementTypeDefinition;
                    Set<XSDSimpleTypeDefinition> types =
                            BaseTypeUtil.getSupportedSimple(simple);
                    StringBuilder typeNames = new StringBuilder();
                    for (XSDSimpleTypeDefinition nextType : types) {
                        if (typeNames.length() != 0) {
                            typeNames.append(',');
                        }
                        typeNames.append(nextType.getName());
                    }
                    sElementTypeDefinition = typeNames.toString();
                }
                if (sElementTypeDefinition != null) {
                    type =
                            new DefaultScriptRelevantData(sElementTypeName,
                                    sElementTypeDefinition, multiple);
                }
            }
        }
        return type;
    }

    private static IScriptRelevantData getVariableReferenceNextStepType(
            Iterator<Step> stepIterator, List<JsMethod> supportedMethods,
            Map<String, IScriptRelevantData> supportedScriptRelevantData,
            List<ContentAssistElement> elementList, boolean isMultiple) {
        String sType = XPathConsts.XPATH_TYPE_UNDEFINED;
        IScriptRelevantData type =
                new DefaultScriptRelevantData(sType, sType, isMultiple);
        ContentAssistElement element = null;
        if (stepIterator.hasNext()) {
            Step currentStep = stepIterator.next();
            if (currentStep != null) {
                boolean stillHasNext = stepIterator.hasNext();
                if (!stillHasNext
                        && (currentStep instanceof TextNodeStep || currentStep instanceof CommentNodeStep)) {
                    sType = XPathConsts.XPATH_TYPE_STRING;
                    type = new DefaultScriptRelevantData(sType, sType, false);
                } else if (!stillHasNext && currentStep instanceof AllNodeStep) {
                    sType = XPathConsts.XPATH_TYPE_NODESET;
                    type =
                            new DefaultScriptRelevantData(sType, sType,
                                    isMultiple);
                } else {
                    element =
                            ProcessXPathUtil.getElement(elementList,
                                    XPathUtil.getStepName(currentStep));
                    boolean hasNumberPredicates =
                            ProcessXPathUtil.hasNumberPredicates(currentStep,
                                    null,
                                    supportedMethods,
                                    supportedScriptRelevantData,
                                    new HashMap<String, String>());
                    if (element instanceof JsAttribute) {
                        if (!stepIterator.hasNext()) {
                            sType = ((JsAttribute) element).getDataType();
                            boolean isElementMultiple =
                                    ((JsAttribute) element).isMultiple();
                            if (isMultiple != true && !hasNumberPredicates) {
                                isMultiple = isElementMultiple;
                            }
                            type =
                                    new DefaultScriptRelevantData(sType, sType,
                                            isMultiple);
                        } else {
                            Step nextStep = stepIterator.next();
                            if (!stepIterator.hasNext()
                                    && (nextStep instanceof TextNodeStep
                                            || nextStep instanceof CommentNodeStep || nextStep instanceof AllNodeStep)) {
                                sType = XPathConsts.XPATH_TYPE_STRING;
                                type =
                                        new DefaultScriptRelevantData(sType,
                                                sType, false);
                            } else if (!stepIterator.hasNext()
                                    && nextStep instanceof AllNodeStep) {
                                sType = XPathConsts.XPATH_TYPE_NODESET;
                                type =
                                        new DefaultScriptRelevantData(sType,
                                                sType, false);
                            }
                        }
                    } else if (element instanceof JsReference) {
                        JsReference jsReference = (JsReference) element;
                        JsClass jsClass = jsReference.getReferencedJsClass();
                        if (jsClass != null) {
                            boolean isElementMultiple =
                                    jsReference.isMultiple();
                            if (isMultiple != true && !hasNumberPredicates) {
                                isMultiple = isElementMultiple;
                            }
                            if (stepIterator.hasNext()) {
                                List<JsAttribute> attributeList =
                                        jsClass.getAttributeList();
                                List<JsReference> referenceList =
                                        jsClass.getReferenceList();
                                elementList =
                                        new ArrayList<ContentAssistElement>();
                                if (attributeList != null) {
                                    elementList.addAll(attributeList);
                                }
                                if (referenceList != null) {
                                    elementList.addAll(referenceList);
                                }
                                type =
                                        getVariableReferenceNextStepType(stepIterator,
                                                supportedMethods,
                                                supportedScriptRelevantData,
                                                elementList,
                                                isMultiple);
                            } else {
                                type =
                                        new DefaultUMLScriptRelevantData(
                                                jsReference.getName(),
                                                jsClass.getName(), isMultiple,
                                                jsClass);
                            }
                        } else {
                            sType = XPathConsts.XPATH_TYPE_UNDEFINED;
                            type =
                                    new DefaultScriptRelevantData(sType, sType,
                                            isMultiple);
                        }
                    } else {
                        sType = XPathConsts.XPATH_TYPE_UNDEFINED;
                        type =
                                new DefaultScriptRelevantData(sType, sType,
                                        isMultiple);
                    }
                }
            }
        }
        return type;
    }

    public static boolean isVariableReferenceWithPath(PathExpr pathExpr) {
        boolean isVariableReferenceWithPath = false;
        if (pathExpr != null) {
            Expr expr = pathExpr.getFilterExpr();
            if (expr instanceof VariableReferenceExpr) {
                return true;
            } else if (expr instanceof FilterExpr) {
                FilterExpr filterExpr = (FilterExpr) expr;
                Expr filterExprExpr = filterExpr.getExpr();
                if (filterExprExpr instanceof FilterExpr) {
                    Expr filterExprExprExpr =
                            ((FilterExpr) filterExprExpr).getExpr();
                    if (filterExprExprExpr instanceof VariableReferenceExpr) {
                        return true;
                    }
                } else if (filterExprExpr instanceof VariableReferenceExpr) {
                    return true;
                }
            }
        }
        return isVariableReferenceWithPath;
    }

    public static ContentAssistElement getElement(
            List<ContentAssistElement> elementList, String elementName) {
        ContentAssistElement element = null;
        for (ContentAssistElement jsElement : elementList) {
            if (jsElement instanceof JsAttribute) {
                JsAttribute jsAttribute = (JsAttribute) jsElement;
                if (jsAttribute.getName().equals(elementName)) {
                    element = jsElement;
                    return element;
                }
            } else if (jsElement instanceof JsReference) {
                JsReference jsReference = (JsReference) jsElement;
                if (jsReference.getName().equals(elementName)) {
                    element = jsElement;
                    return element;
                }
            }
        }
        return element;
    }

    public static XSDComponent getWsdlFirstAttribute(
            List<XSDComponent> componentList, String attributeName) {
        XSDComponent attribute = null;
        for (XSDComponent component : componentList) {
            if (attributeName != null) {
                XSDComponent tempElement =
                        getMatchWsdlComponent(component, attributeName, null);
                if (tempElement != null
                        && tempElement instanceof XSDAttributeUse) {
                    attribute = tempElement;
                    break;
                }
            }
        }
        return attribute;
    }

    public static XSDComponent getWsdlFirstElement(
            List<XSDComponent> componentList, String elementName) {
        XSDComponent element = null;
        for (XSDComponent component : componentList) {
            if (elementName != null) {
                XSDComponent tempElement =
                        getMatchWsdlComponent(component, elementName, null);
                if (tempElement != null
                        && !(tempElement instanceof XSDAttributeUse)) {
                    element = tempElement;
                    break;
                }
            }
        }
        return element;
    }

    public static XSDComponent getWsdlFirstComponent(
            List<XSDComponent> componentList, String elementName) {
        XSDComponent element = null;
        for (XSDComponent component : componentList) {
            if (elementName != null) {
                XSDComponent tempElement =
                        getMatchWsdlComponent(component, elementName, null);
                if (tempElement != null) {
                    element = tempElement;
                    break;
                }
            }
        }
        return element;
    }

    public static boolean isNodePresentInComplexDataList(
            Map<String, IScriptRelevantData> scriptRelevantDataMap,
            String nodeName) {
        boolean isNodePresent = false;
        if (scriptRelevantDataMap != null) {
            Set<String> keySet = scriptRelevantDataMap.keySet();
            if (keySet != null) {
                for (String key : keySet) {
                    IScriptRelevantData scriptRelevantData =
                            scriptRelevantDataMap.get(key);
                    if (isNodePresentInComplexData(scriptRelevantData, nodeName)) {
                        return true;
                    }
                }
            }
        }
        return isNodePresent;
    }

    private static boolean isNodePresentInComplexData(
            IScriptRelevantData scriptRelevantData, String nodeName) {
        boolean isNodePresent = false;
        if (scriptRelevantData != null) {
            if (scriptRelevantData instanceof IUMLScriptRelevantData) {
                IUMLScriptRelevantData iUMLScriptRelevantData =
                        (IUMLScriptRelevantData) scriptRelevantData;
                if (iUMLScriptRelevantData.getName().equals(nodeName)) {
                    return true;
                } else {
                    // Look in the attributes and references
                    JsClass jsClass = iUMLScriptRelevantData.getJsClass();
                    List<String> jsClassNamesHierarchy =
                            new ArrayList<String>();
                    if (isNodePresentInJsClassAndChildren(jsClass,
                            nodeName,
                            jsClassNamesHierarchy)) {
                        return true;
                    }
                }
            } else if (scriptRelevantData.getName().equals(nodeName)) {
                return true;
            }
        }
        return isNodePresent;
    }

    private static boolean isNodePresentInJsClassAndChildren(JsClass jsClass,
            String nodeName, List<String> jsClassNameHierarchy) {
        boolean isNodePresent = false;
        if (jsClass != null
                && !jsClassNameHierarchy.contains(jsClass.getName())) {
            jsClassNameHierarchy.add(jsClass.getName());
            // Check in the attributes
            List<JsAttribute> attributeList = jsClass.getAttributeList();
            if (attributeList != null && !attributeList.isEmpty()) {
                for (JsAttribute jsAttribute : attributeList) {
                    if (jsAttribute.getName().equals(nodeName)) {
                        return true;
                    }
                }

            }
            List<JsReference> referenceList = jsClass.getReferenceList();
            if (referenceList != null && !referenceList.isEmpty()) {
                for (JsReference jsReference : referenceList) {
                    if (jsReference.getName().equals(nodeName)) {
                        return true;
                    } else {
                        JsClass referencedJsClass =
                                jsReference.getReferencedJsClass();
                        if (isNodePresentInJsClassAndChildren(referencedJsClass,
                                nodeName,
                                jsClassNameHierarchy)) {
                            return true;
                        }
                    }
                }
            }
        }
        return isNodePresent;
    }

    private static XSDComponent getMatchWsdlComponent(XSDComponent component,
            String elementName, List<XSDComponent> componentList) {
        if (componentList == null) {
            componentList = new ArrayList<XSDComponent>();
        }
        componentList.add(component);
        if (component instanceof XSDAttributeUseImpl) {
            XSDAttributeUseImpl attribute = (XSDAttributeUseImpl) component;
            if (attribute.getAttributeDeclaration() != null) {
                String name = attribute.getAttributeDeclaration().getName();
                if (name != null && name.equals(elementName)) {
                    return attribute;
                }
            }
        } else if (component instanceof XSDParticleImpl) {
            XSDParticleImpl particle = (XSDParticleImpl) component;
            XSDParticleContent particleContent = particle.getContent();
            if (particleContent instanceof XSDModelGroupImpl) {
                XSDModelGroupImpl modelGroupImpl =
                        (XSDModelGroupImpl) particleContent;
                List<XSDConcreteComponent> concreteComponentList =
                        modelGroupImpl.getXSDContents();
                if (concreteComponentList != null) {
                    for (XSDConcreteComponent concreteComponent : concreteComponentList) {
                        if (!componentList.contains(concreteComponent)) {
                            XSDComponent result =
                                    getMatchWsdlComponent((XSDComponent) concreteComponent,
                                            elementName,
                                            componentList);
                            if (result != null) {
                                return result;
                            }
                        }
                    }
                }
            } else if (particleContent instanceof XSDElementDeclarationImpl) {
                XSDElementDeclarationImpl elementDeclarationImpl =
                        (XSDElementDeclarationImpl) particleContent;
                XSDElementDeclarationImpl currentElementDeclaration;
                if (elementDeclarationImpl.isElementDeclarationReference()) {
                    XSDElementDeclarationImpl resolvedElementDeclarationImpl =
                            (XSDElementDeclarationImpl) elementDeclarationImpl
                                    .getResolvedElementDeclaration();
                    String elementDeclarationName =
                            resolvedElementDeclarationImpl.getName();
                    if (elementDeclarationName != null
                            && elementDeclarationName.equals(elementName)) {
                        return elementDeclarationImpl;
                    }
                    currentElementDeclaration = resolvedElementDeclarationImpl;
                } else {
                    String elementDeclarationName =
                            elementDeclarationImpl.getName();
                    if (elementDeclarationName != null
                            && elementDeclarationName.equals(elementName)) {
                        return elementDeclarationImpl;
                    }
                    currentElementDeclaration = elementDeclarationImpl;
                }
                if (currentElementDeclaration != null) {
                    XSDTypeDefinition elementTypeDefinition =
                            currentElementDeclaration.getType();
                    if (elementTypeDefinition instanceof XSDComplexTypeDefinition) {
                        List<XSDComponent> elementComponentList =
                                WsdlUtil.getTypeChildren(elementTypeDefinition);
                        if (elementComponentList != null) {
                            for (XSDComponent elementComponent : elementComponentList) {
                                if (!componentList
                                        .contains(currentElementDeclaration)) {
                                    XSDComponent result =
                                            getMatchWsdlComponent(elementComponent,
                                                    elementName,
                                                    componentList);
                                    if (result != null) {
                                        return result;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else if (component instanceof XSDComplexTypeDefinitionImpl) {
            XSDComplexTypeDefinitionImpl complexTypeDefinitionImpl =
                    (XSDComplexTypeDefinitionImpl) component;
            List<XSDConcreteComponent> concreteComponentList =
                    complexTypeDefinitionImpl.getXSDContents();
            if (concreteComponentList != null) {
                for (XSDConcreteComponent concreteComponent : concreteComponentList) {
                    if (!componentList.contains(concreteComponent)) {
                        XSDComponent result =
                                getMatchWsdlComponent((XSDComponent) concreteComponent,
                                        elementName,
                                        componentList);
                        if (result != null) {
                            return result;
                        }
                    }
                }
            }
        }
        return null;
    }

    public static boolean isRootNode(Part wsdlPart, Step step) {
        boolean isRootNode = false;
        if (wsdlPart != null && step != null) {
            String nodeName = XPathUtil.getStepName(step);
            QName qName = wsdlPart.getElementName();
            String partName = null;
            if (qName != null && qName.getLocalPart() != null
                    && qName.getLocalPart().equals(nodeName)) {
                isRootNode = true;
            } else if (wsdlPart.getElementName() != null) {
                partName = wsdlPart.getElementName().getLocalPart();
                if (partName != null && partName.equals(nodeName)) {
                    isRootNode = true;
                }
            } else {
                partName = wsdlPart.getName();
                if (partName != null && partName.equals(nodeName)) {
                    isRootNode = true;
                }
            }
        }
        return isRootNode;
    }

    public static List<XSDComponent> getWsdlComponents(Part wsdlPart) {
        List<XSDComponent> components = new ArrayList<XSDComponent>();
        if (wsdlPart != null) {
            XSDTypeDefinition type = WsdlUtil.getTypeDefinition(wsdlPart);
            if (type != null) {
                components = WsdlUtil.getTypeChildren(type);
            }
        }
        if (components == null) {
            components = new ArrayList<XSDComponent>();
        }
        return components;
    }

    private static IScriptRelevantData getLocationPathExpressionTypeAgainstWsdl(
            LocationPath expression, Part wsdlPart,
            Map<String, IScriptRelevantData> supportedScriptRelevantData,
            List<JsMethod> supportedMethods, Map<String, String> namespaces) {
        String sType = XPathConsts.XPATH_TYPE_UNDEFINED;
        boolean isArray = false;
        IScriptRelevantData type =
                new DefaultScriptRelevantData(sType, sType, isArray);
        String xmlDocStr = ProcessXPathUtil.getXmlDocStr(wsdlPart);
        if (expression != null && wsdlPart != null && xmlDocStr != null) {
            boolean isMultiple =
                    ProcessXPathUtil.isLocationExpressionMultiple(expression,
                            wsdlPart,
                            supportedMethods,
                            supportedScriptRelevantData,
                            namespaces);
            LocationPath expressionWithoutPredicates =
                    XPathUtil.createLocationPathWithoutPredicates(expression);
            String text = "";//$NON-NLS-1$
            if (expression != null) {
                text = expressionWithoutPredicates.getText();
            }
            List pathNodeResults =
                    XPathUtil
                            .getXPathExpressionEvaluationNodeResults(xmlDocStr,
                                    text,
                                    namespaces);
            // Resolve the result node with the element in the schema
            if (pathNodeResults != null) {
                for (Object obj : pathNodeResults) {
                    // Take the type of the first result
                    if (obj instanceof Node) {
                        Node node = (Node) obj;
                        if (node instanceof Text) {
                            sType = XPathConsts.XPATH_TYPE_STRING;
                            type.setName(sType);
                            type.setType(sType);
                        } else {
                            XSDComponent wsdlComponent =
                                    resolveNodeInSchema(node, wsdlPart);
                            type =
                                    ProcessXPathUtil
                                            .getWsdlComponentType(wsdlComponent);
                        }
                        if (type != null) {
                            type.setIsArray(isMultiple);
                        }
                        break;
                    }
                }
            }
        }
        return type;
    }

    private static boolean isLocationExpressionMultiple(
            LocationPath locationPathExpr, Part wsdlPart,
            List<JsMethod> supportedMethods,
            Map<String, IScriptRelevantData> supportedScriptRelevantData,
            Map<String, String> namespaces) {
        boolean isMultiple = false;
        if (locationPathExpr != null) {
            List<Step> stepList = locationPathExpr.getSteps();
            if (stepList != null) {
                if (wsdlPart != null) {
                    isMultiple =
                            isLocationExpressionMultipleAgainstWsdl(stepList,
                                    wsdlPart,
                                    supportedMethods,
                                    supportedScriptRelevantData,
                                    namespaces);
                } else {
                    isMultiple =
                            isLocationExpressionMultipleAgainstSupportedSRD(stepList,
                                    supportedMethods,
                                    supportedScriptRelevantData);
                }
            }
        }
        return isMultiple;
    }

    private static boolean isLocationExpressionMultipleAgainstSupportedSRD(
            List<Step> stepList, List<JsMethod> supportedMethods,
            Map<String, IScriptRelevantData> supportedScriptRelevantData) {
        boolean isMultiple = false;
        if (stepList != null && !stepList.isEmpty()) {
            Iterator<Step> stepIterator = stepList.iterator();
            // Get the type of the last step
            Step firstStep = stepIterator.next();
            if (firstStep != null) {
                IScriptRelevantData variableRelevantData =
                        supportedScriptRelevantData.get(XPathUtil
                                .getStepName(firstStep));
                if (variableRelevantData instanceof IUMLScriptRelevantData) {
                    JsClass jsClass =
                            ((IUMLScriptRelevantData) variableRelevantData)
                                    .getJsClass();
                    List<JsAttribute> attributeList =
                            jsClass.getAttributeList();
                    List<JsReference> referenceList =
                            jsClass.getReferenceList();
                    List<ContentAssistElement> elementList =
                            new ArrayList<ContentAssistElement>();
                    if (attributeList != null) {
                        elementList.addAll(attributeList);
                    }
                    if (referenceList != null) {
                        elementList.addAll(referenceList);
                    }
                    if (variableRelevantData.isArray()
                            && !ProcessXPathUtil.hasNumberPredicates(firstStep,
                                    null,
                                    supportedMethods,
                                    supportedScriptRelevantData,
                                    new HashMap<String, String>())) {
                        return true;
                    }
                    while (stepIterator.hasNext()) {
                        List<Step> newStepList = new ArrayList<Step>();
                        Step currentStep = stepIterator.next();
                        newStepList.add(currentStep);
                        IScriptRelevantData type =
                                ProcessXPathUtil
                                        .getVariableReferenceNextStepType(newStepList
                                                .iterator(),
                                                supportedMethods,
                                                supportedScriptRelevantData,
                                                elementList,
                                                isMultiple);
                        if (type.isArray()
                                && !ProcessXPathUtil
                                        .hasNumberPredicates(currentStep,
                                                null,
                                                supportedMethods,
                                                supportedScriptRelevantData,
                                                new HashMap<String, String>())) {
                            return true;
                        }
                    }
                }
            }
        }
        return isMultiple;
    }

    private static boolean isLocationExpressionMultipleAgainstWsdl(
            List<Step> stepList, Part wsdlPart,
            List<JsMethod> supportedMethods,
            Map<String, IScriptRelevantData> supportedScriptRelevantData,
            Map<String, String> namespaces) {
        boolean isMultiple = false;
        for (Step step : stepList) {
            String nodeName = XPathUtil.getStepName(step);
            if (nodeName != null) {
                if (wsdlPart != null) {
                    List<XSDComponent> componentList =
                            ProcessXPathUtil.getWsdlComponents(wsdlPart);
                    XSDComponent resolvedElement = null;
                    if (step.getAxis() == Axis.ATTRIBUTE) {
                        resolvedElement =
                                ProcessXPathUtil
                                        .getWsdlFirstAttribute(componentList,
                                                nodeName);
                    } else {
                        resolvedElement =
                                ProcessXPathUtil
                                        .getWsdlFirstComponent(componentList,
                                                nodeName);
                    }
                    if (resolvedElement != null) {
                        IScriptRelevantData type =
                                ProcessXPathUtil
                                        .getWsdlComponentType(resolvedElement);
                        boolean areGroupParentsMultiple =
                                areModelGroupParentsMultiple(resolvedElement);
                        if (type != null
                                && (type.isArray() || areGroupParentsMultiple)) {
                            if (!ProcessXPathUtil.hasNumberPredicates(step,
                                    wsdlPart,
                                    supportedMethods,
                                    supportedScriptRelevantData,
                                    namespaces)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return isMultiple;
    }

    private static boolean areModelGroupParentsMultiple(XSDComponent component) {
        boolean areGroupMultiple = false;
        EObject parentGroup = null;
        if (component != null) {
            EObject eObject = component.eContainer();
            if (eObject instanceof XSDParticleImpl) {
                parentGroup = eObject.eContainer();
            }
            while (parentGroup instanceof XSDModelGroup) {
                EObject modelGroupContainer = parentGroup.eContainer();
                if (modelGroupContainer == null) {
                    return areGroupMultiple;
                }
                if (modelGroupContainer instanceof XSDParticleImpl) {
                    XSDParticleImpl groupElement =
                            (XSDParticleImpl) modelGroupContainer;
                    int maxOccurs = groupElement.getMaxOccurs();
                    if (maxOccurs == -1 || maxOccurs > 1) {
                        return true;
                    }
                }
                parentGroup = modelGroupContainer.eContainer();
            }
        }
        return areGroupMultiple;
    }

    private static XSDComponent resolveNodeInSchema(Node node, Part wsdlPart) {
        XSDComponent component = null;
        if (node != null && node.getLocalName() != null && wsdlPart != null) {
            List<XSDComponent> componentList =
                    ProcessXPathUtil.getWsdlComponents(wsdlPart);
            if (componentList != null) {
                component =
                        ProcessXPathUtil.getWsdlFirstComponent(componentList,
                                node.getLocalName());
            }
        }
        return component;
    }

    public static String getXmlDocStr(Part wsdlPart) {
        String xmlDoc = null;
        if (wsdlPart != null) {
            QName qName = wsdlPart.getElementName();
            XSDTypeDefinition type = WsdlUtil.getTypeDefinition(wsdlPart);
            if (qName != null && qName.getLocalPart() != null) {
                try {
                    xmlDoc = WsdlUtil.generateXml(qName.getLocalPart(), type);
                } catch (XmlException e) {
                    e.printStackTrace();
                }
            } else {
                String partName = null;
                if (wsdlPart.getElementName() != null) {
                    partName = wsdlPart.getElementName().getLocalPart();
                    if (partName != null) {
                        try {
                            xmlDoc = WsdlUtil.generateXml(partName, type);
                        } catch (XmlException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    partName = wsdlPart.getName();
                    if (partName != null) {
                        try {
                            xmlDoc = WsdlUtil.generateXml(partName, type);
                        } catch (XmlException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return xmlDoc;
    }

    public static boolean isStepAttribute(Step step) {
        if (step != null && step.getAxis() == Axis.ATTRIBUTE) {
            return true;
        }
        return false;
    }

    public static Node getRootNode(Part wsdlPart, Map<String, String> namespaces) {
        Node rootNode = null;
        if (wsdlPart != null) {
            String xmlDocStr = ProcessXPathUtil.getXmlDocStr(wsdlPart);
            if (xmlDocStr != null) {
                List pathNodeResults =
                        XPathUtil
                                .getXPathExpressionEvaluationNodeResults(xmlDocStr,
                                        "/", namespaces);//$NON-NLS-1$
                if (pathNodeResults != null && !pathNodeResults.isEmpty()) {
                    Object obj = pathNodeResults.get(0);
                    if (obj instanceof Document) {
                        Document document = (Document) obj;
                        rootNode = document;
                    }
                }
            }
        }
        return rootNode;
    }

    public static boolean isNumberDecimal(NumberExpr numberExpr) {
        boolean isDecimal = false;
        if (numberExpr != null) {
            String numberStr = numberExpr.getText();
            try {
                Double.parseDouble(numberStr);
                isDecimal = true;
            } catch (NumberFormatException e) {
                // Ignore
            }
        }
        return isDecimal;
    }

    public static boolean isNumberExpression(Expr expression) {
        boolean isNumeric = false;
        NumberExpr numberExpr =
                ProcessXPathUtil.getNumberExpression(expression);
        if (numberExpr != null) {
            isNumeric = true;
        }
        return isNumeric;
    }

    public static NumberExpr getNumberExpression(Expr expression) {
        NumberExpr numberExpr = null;
        if (expression instanceof FilterExpr) {
            FilterExpr filterExpr = (FilterExpr) expression;
            Expr expr = filterExpr.getExpr();
            if (expr instanceof NumberExpr) {
                return (NumberExpr) expr;
            } else if (expr instanceof FilterExpr) {
                FilterExpr filterExprEpr = (FilterExpr) expr;
                Expr exprExpr = filterExprEpr.getExpr();
                if (exprExpr instanceof NumberExpr) {
                    return (NumberExpr) exprExpr;
                }
            }
        } else if (expression instanceof NumberExpr) {
            return (NumberExpr) expression;
        }
        return numberExpr;
    }

    public static boolean isLiteralExpression(Expr expression) {
        boolean isLiteral = false;
        LiteralExpr literalExpr =
                ProcessXPathUtil.getLiteralExpression(expression);
        if (literalExpr != null) {
            isLiteral = true;
        }
        return isLiteral;
    }

    public static LiteralExpr getLiteralExpression(Expr expression) {
        LiteralExpr literalExpr = null;
        if (expression instanceof FilterExpr) {
            FilterExpr filterExpr = (FilterExpr) expression;
            Expr expr = filterExpr.getExpr();
            if (expr instanceof LiteralExpr) {
                return (LiteralExpr) expr;
            } else if (expr instanceof FilterExpr) {
                FilterExpr filterExprEpr = (FilterExpr) expr;
                Expr exprExpr = filterExprEpr.getExpr();
                if (exprExpr instanceof LiteralExpr) {
                    return (LiteralExpr) exprExpr;
                }
            }
        } else if (expression instanceof LiteralExpr) {
            return (LiteralExpr) expression;
        }
        return literalExpr;
    }

    public static Map<String, String> getPartNamespaces(Part wsdlPart) {
        Map<String, String> namespaces = new HashMap<String, String>();
        Definition definition = getDefinition(wsdlPart);
        if (definition != null && definition.getNamespaces() != null) {
            namespaces.putAll(definition.getNamespaces());
        }
        Types types = definition.getTypes();
        if (types != null) {
            List<?> extensibilityElements = types.getExtensibilityElements();
            if (extensibilityElements != null) {
                for (Object extenElement : extensibilityElements) {
                    if (extenElement instanceof XSDSchemaExtensibilityElement) {
                        XSDSchemaExtensibilityElement schemaExten =
                                (XSDSchemaExtensibilityElement) extenElement;
                        XSDSchema schema = schemaExten.getSchema();
                        namespaces
                                .putAll(schema.getQNamePrefixToNamespaceMap());
                    }
                }
            }
        }
        return namespaces;
    }

    /**
     * Recurses up from the given EObject to find the root Definition object.
     * 
     * @param eo
     *            The next EObject to check.
     * @return The root Definition element.
     */
    private static Definition getDefinition(EObject eo) {
        Definition definition = null;
        if (eo instanceof Definition) {
            definition = (Definition) eo;
        } else if (eo != null) {
            EObject parent = eo.eContainer();
            definition = getDefinition(parent);
        }
        return definition;
    }

}
