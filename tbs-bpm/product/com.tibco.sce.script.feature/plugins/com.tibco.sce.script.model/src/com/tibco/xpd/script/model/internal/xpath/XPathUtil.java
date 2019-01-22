/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.script.model.internal.xpath;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jaxen.JaxenException;
import org.jaxen.XPath;
import org.jaxen.expr.AllNodeStep;
import org.jaxen.expr.CommentNodeStep;
import org.jaxen.expr.DefaultXPathFactory;
import org.jaxen.expr.Expr;
import org.jaxen.expr.LocationPath;
import org.jaxen.expr.NameStep;
import org.jaxen.expr.ProcessingInstructionNodeStep;
import org.jaxen.expr.Step;
import org.jaxen.expr.TextNodeStep;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;


/**
 * @author mtorres
 *
 */
public class XPathUtil {
    
    private static DefaultXPathFactory xpathFactory;
    
    private static XPathContentAssistIconProvider xpathContentAssistIconProvider = null;
    
    private static final String XPATH_CHILD_PREFIX = "child";//$NON-NLS-1$

    public static DefaultXPathFactory getXPathFactory() {
        if (xpathFactory == null) {
            xpathFactory = new DefaultXPathFactory();
        }
        return xpathFactory;
    }
    
    public static String getStepName(Step step) {
        String stepName = null;
        if (step instanceof NameStep) {
            NameStep nameStep = (NameStep) step;
            stepName = nameStep.getLocalName();
        } else if (step instanceof ProcessingInstructionNodeStep) {
            ProcessingInstructionNodeStep processingIntructionNodeStep =
                    (ProcessingInstructionNodeStep) step;
            stepName = processingIntructionNodeStep.getName();
        } else if (step instanceof TextNodeStep) {
            stepName = "text"; // //$NON-NLS-1$
        } else if (step instanceof CommentNodeStep) {
            stepName = "comment"; // //$NON-NLS-1$
        } else if (step instanceof AllNodeStep) {
            stepName = "node"; // //$NON-NLS-1$
        }
        return stepName;
    }
    
    public static String getStepPrefix(Step step) {
        String stepPrefix = null;
        if (step instanceof NameStep) {
            NameStep nameStep = (NameStep) step;
            stepPrefix = nameStep.getPrefix();
        } else{
            stepPrefix = XPATH_CHILD_PREFIX;
        }
        return stepPrefix;
    }
    
    public static Step createStepWithoutPredicates(Step step) {
        Step stepWP = null;
        try {
            if (step instanceof NameStep) {
                NameStep nameStep = (NameStep) step;
                String prefix = nameStep.getPrefix();
                if(prefix != null && prefix.equals("")){
                    prefix = null;
                }
                stepWP =
                        XPathUtil.getXPathFactory().createNameStep(
                                nameStep.getAxis(), prefix,
                                nameStep.getLocalName());
            } else if (step instanceof ProcessingInstructionNodeStep) {
                ProcessingInstructionNodeStep processingIntructionNodeStep =
                        (ProcessingInstructionNodeStep) step;
                stepWP =
                        XPathUtil.getXPathFactory()
                                .createProcessingInstructionNodeStep(
                                        processingIntructionNodeStep.getAxis(),
                                        processingIntructionNodeStep.getName());
            } else if (step instanceof TextNodeStep) {
                TextNodeStep textNodeStep = (TextNodeStep) step;
                stepWP =
                        XPathUtil.getXPathFactory().createTextNodeStep(
                                textNodeStep.getAxis());
            } else if (step instanceof CommentNodeStep) {
                CommentNodeStep commentNodeStep = (CommentNodeStep) step;
                stepWP =
                        XPathUtil.getXPathFactory().createCommentNodeStep(
                                commentNodeStep.getAxis());
            } else if (step instanceof AllNodeStep) {
                AllNodeStep allNodeStep = (AllNodeStep) step;
                stepWP =
                        XPathUtil.getXPathFactory().createAllNodeStep(
                                allNodeStep.getAxis());
            }
        } catch (JaxenException ex) {
            //ignore
        }
        return stepWP;
    }
    
    public static LocationPath createLocationPathWithoutPredicates(
            LocationPath expression) {
        LocationPath locationPath = null;
        try{
            if (expression != null) {
                if (expression.isAbsolute()) {
                    locationPath =
                            XPathUtil.getXPathFactory()
                                    .createAbsoluteLocationPath();
                } else {
                    locationPath =
                            XPathUtil.getXPathFactory()
                                    .createRelativeLocationPath();
                }
                List<Step> stepList = expression.getSteps();
                if(stepList != null){
                    for (Step step : stepList) {
                        Step newStep = XPathUtil.createStepWithoutPredicates(step);
                        locationPath.addStep(newStep);
                    }
                }
            }
        }catch (JaxenException e) {
            // TODO: handle exception
        }
        return locationPath;
    }

    
    public static List getXPathExpressionEvaluationNodeResults(Node xmlDocNode,
            String xpathExpressionStr, Map<String, String> namespaces) {
        List results = new ArrayList();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        try {
            if (xmlDocNode != null && xpathExpressionStr != null) {
                DocumentBuilder builder = factory.newDocumentBuilder();
                XPath xpathExpression =
                        new org.jaxen.dom.DOMXPath(xpathExpressionStr);
                if (namespaces != null) {
                    Set<String> keys = namespaces.keySet();
                    for (String key : keys) {
                        if (key != null) {
                            String namespaceUrl = namespaces.get(key);
                            if (namespaceUrl != null) {
                                xpathExpression.addNamespace(key, namespaceUrl);
                            }
                        }
                    }
                }
                Object result = xpathExpression.evaluate(xmlDocNode);
                if (result instanceof List) {
                    results = new ArrayList();
                    List allResults = (List) result;
                    for (Object obj : allResults) {
                        if (obj instanceof Node) {
                            if (XPathUtil.isLastStepPrefixCorrect(
                                    (org.jaxen.dom.DOMXPath) xpathExpression,
                                    (Node) obj)) {
                                results.add(obj);
                            }
                        }
                    }
                }
                if (results == null) {
                    results = new ArrayList();
                }
            }
        } catch (Exception e) {
            // Something went wrong
        }
        return results;
    }
    
    
    public static List getXPathExpressionEvaluationNodeResults(
            String xmlDocStr, String xpathExpressionStr,
            Map<String, String> namespaces) {
        List results = new ArrayList();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        try {
            if (xmlDocStr != null && xpathExpressionStr != null) {
                DocumentBuilder builder = factory.newDocumentBuilder();
                Reader stringReader = new StringReader(xmlDocStr);
                InputSource data = new InputSource(stringReader);
                Node doc = builder.parse(data);
                XPath xpathExpression =
                        new org.jaxen.dom.DOMXPath(xpathExpressionStr);
                if (namespaces != null) {
                    Set<String> keys = namespaces.keySet();
                    for (String key : keys) {
                        if (key != null) {
                            String namespaceUrl = namespaces.get(key);
                            if (namespaceUrl != null) {
                                xpathExpression.addNamespace(key, namespaceUrl);
                            }
                        }
                    }
                }
                Object result = xpathExpression.evaluate(doc);
                if (result instanceof List) {
                    results = new ArrayList();
                    List allResults = (List) result;
                    for (Object obj : allResults) {
                        if (obj instanceof Node) {
                            if (XPathUtil.isLastStepPrefixCorrect(
                                    (org.jaxen.dom.DOMXPath) xpathExpression,
                                    (Node) obj)) {
                                results.add(obj);
                            }
                        }
                    }
                }
                if (results == null) {
                    results = new ArrayList();
                }
            }
        } catch (Exception e) {
            // Something went wrong
        }
        return results;
    }
    
    private static boolean isLastStepPrefixCorrect(
            org.jaxen.dom.DOMXPath xpathExpression,
            Node element) {
        boolean correct = true;
        if (xpathExpression != null) {
            Expr expr = xpathExpression.getRootExpr();
            if (expr instanceof LocationPath) {
                // Get the last step
                LocationPath locationPath = (LocationPath) expr;
                List steps = locationPath.getSteps();
                if (steps != null && !steps.isEmpty()) {
                    Step lastStep = (Step) steps.get(steps.size() - 1);
                    if (lastStep != null) {
                        String lastStepName = XPathUtil.getStepName(lastStep);
                        if (lastStepName != null && !lastStepName.equals("*")) {//$NON-NLS-1$
                            String xpathPrefix =
                                    XPathUtil.getStepPrefix(lastStep);
                            String elementPrefix = element.getPrefix();
                            if (xpathPrefix != null && elementPrefix != null) {
                                if (!xpathPrefix.equals(elementPrefix)) {
                                    correct = false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return correct;
    }
    

    public static XPathContentAssistIconProvider getXPathContentAssistIconProvider() {
        if(xpathContentAssistIconProvider == null){
            xpathContentAssistIconProvider = new XPathContentAssistIconProvider();
        }
        return xpathContentAssistIconProvider;
    }
    
}
