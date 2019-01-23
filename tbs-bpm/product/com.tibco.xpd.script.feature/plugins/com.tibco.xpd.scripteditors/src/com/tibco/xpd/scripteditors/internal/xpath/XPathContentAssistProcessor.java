/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.scripteditors.internal.xpath;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.swt.graphics.Image;
import org.jaxen.JaxenException;
import org.jaxen.dom.DOMXPath;
import org.jaxen.expr.Expr;
import org.jaxen.expr.LocationPath;
import org.jaxen.expr.PathExpr;
import org.jaxen.expr.Predicate;
import org.jaxen.expr.Step;
import org.jaxen.expr.VariableReferenceExpr;
import org.jaxen.saxpath.Axis;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.IUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.model.client.JsMethod;
import com.tibco.xpd.script.model.client.JsReference;
import com.tibco.xpd.script.model.internal.xpath.XPathAbreviationsDefinitionReader;
import com.tibco.xpd.script.model.internal.xpath.XPathUtil;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.script.sourceviewer.internal.contentassist.AbstractTibcoContentAssistProcessor;
import com.tibco.xpd.script.sourceviewer.internal.contentassist.CustomCompletionProposal;
import com.tibco.xpd.script.sourceviewer.internal.contentassist.MyCompletionStringNode;
import com.tibco.xpd.script.sourceviewer.internal.contentassist.MyFollowClass;
import com.tibco.xpd.script.sourceviewer.internal.preferences.AbstractScriptCommonUIPreferenceNames;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;
import com.tibco.xpd.scripteditors.ScriptEditorsPlugin;
import com.tibco.xpd.scripteditors.internal.xpath.contentassist.XPathContentAssistConstants;
import com.tibco.xpd.scripteditors.internal.xpath.contentassist.XPathContentAssistUtils;

/**
 * XPath Content Assist Processor
 * 
 * @author rsomayaj
 * 
 */
public class XPathContentAssistProcessor extends
        AbstractTibcoContentAssistProcessor {

    List<JsMethod> abreviationMethods = null;

    private static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    public XPathContentAssistProcessor() {
        super();
    }

    @Override
    public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
            int documentOffset) {
        String text = viewer.getDocument().get();
        String string =
                XPathContentAssistUtils.getXPathStartText(text, documentOffset);
        String prefix = ""; //$NON-NLS-1$
        String rest = ""; //$NON-NLS-1$
        ICompletionProposal[] result =
                getProposalForConfiguredClasses(viewer,
                        string,
                        prefix,
                        documentOffset);
        ICompletionProposal templates[] =
                templateComputeCompletionProposals(viewer, documentOffset);
        if (result == null || result.length == 0) {
            return templates;
        }
        if (templates == null) {
            return result;
        }
        return result;
    }

    @Override
    protected Template[] getTemplates(String contextTypeId) {
        return ScriptEditorsPlugin.getDefault().getXPathTemplateStore()
                .getTemplates();
    }

    @Override
    protected String getStringToComplete(ITextViewer viewer,
            int documentPosition, Node node) {
        String text = viewer.getDocument().get();
        String prefix =
                XPathContentAssistUtils.getXPathStartText(text,
                        documentPosition);
        return prefix;
    }

    /**
     * given the existing string to complete, return a list of completions
     * 
     * @param estring
     *            the string in the document at which the cursor is at the end
     *            of and which we are to complete
     * @param docPosition
     *            the position in the document that the cursor and the end of
     *            the estring are at.
     */
    @SuppressWarnings("unchecked")//$NON-NLS-1$
    @Override
    protected Vector<CustomCompletionProposal> getProposalVector(
            ITextViewer viewer, String oestring, int docpos,
            MyFollowClass myFollowClass) {
        Vector<CustomCompletionProposal> retval =
                new Vector<CustomCompletionProposal>();
        if (oestring != null) {
            if (isReferenceStart(oestring)) {
                myFollowClass = new MyFollowClass(START_CLASS);
                // Add the supported Variables
                addScriptRelevantData(myFollowClass);
                // Add the complex variables
                addComplexScriptRelevantData(myFollowClass);
                getProposalXPathVector(retval, myFollowClass, oestring, docpos);
            } else {
                myFollowClass = new MyFollowClass(START_CLASS);
                // Add the supported Variables
                addScriptRelevantData(myFollowClass);
                // Add the complex variables
                addComplexScriptRelevantData(myFollowClass);
                addFunctions(myFollowClass);
                getProposalXPathVector(retval, myFollowClass, oestring, docpos);
            }
        }
        return retval;
    }

    private void getProposalXPathVector(
            Vector<CustomCompletionProposal> retval,
            MyFollowClass myFollowClass, String oestring, int docpos) {
        Iterator<MyCompletionStringNode> myCompNodeItr =
                myFollowClass.getCompletionNodes().iterator();
        if (oestring != null) {
            if (oestring.trim().equals("$") //$NON-NLS-1$
                    || oestring.trim().equals("/") //$NON-NLS-1$
                    || oestring.trim().equals("//")) {//$NON-NLS-1$
                getProposalXPathVectorSimpleVariables(retval,
                        myFollowClass,
                        "", docpos); //$NON-NLS-1$
            } else if (oestring.trim().equals("") //$NON-NLS-1$
                    || (!oestring.trim().startsWith("$") //$NON-NLS-1$
                            && !oestring.trim().startsWith("/") && !oestring //$NON-NLS-1$
                            .trim().startsWith("//"))) {//$NON-NLS-1$
                getProposalXPathVectorSimpleVariables(retval,
                        myFollowClass,
                        oestring,
                        docpos); //$NON-NLS-1$
            } else {
                try {
                    String extraOestring = null;
                    if (oestring.endsWith("/") //$NON-NLS-1$
                            || oestring.endsWith("@") //$NON-NLS-1$
                            || oestring.endsWith("::")) { //$NON-NLS-1$
                        if (oestring.endsWith("/")) {//$NON-NLS-1$
                            extraOestring = oestring + "@*";//$NON-NLS-1$
                        }
                        oestring = oestring + "*";//$NON-NLS-1$
                    }
                    DOMXPath xpathExpression = new DOMXPath(oestring);
                    if (xpathExpression != null) {
                        Expr rootExpression = xpathExpression.getRootExpr();
                        getProposalForExpression(rootExpression,
                                retval,
                                oestring,
                                myFollowClass,
                                docpos,
                                false);
                    }
                    // This is to add the attributes to the list
                    if (extraOestring != null) {
                        DOMXPath extraXpathExpression =
                                new DOMXPath(extraOestring);
                        if (extraXpathExpression != null) {
                            Expr rootExpression =
                                    extraXpathExpression.getRootExpr();
                            getProposalForExpression(rootExpression,
                                    retval,
                                    extraOestring,
                                    myFollowClass,
                                    docpos,
                                    true);
                        }
                    }
                } catch (JaxenException e) {
                    // TODO: handle exception
                }
            }
        }
    }

    private void getProposalForExpression(Expr expression,
            Vector<CustomCompletionProposal> retval, String oestring,
            MyFollowClass myFollowClass, int docpos, boolean addAt) {
        if (expression instanceof PathExpr) {
            PathExpr pathExpression = (PathExpr) expression;
            Expr filterExpression = pathExpression.getFilterExpr();
            if (filterExpression instanceof VariableReferenceExpr) {
                VariableReferenceExpr variableReferenceExpr =
                        (VariableReferenceExpr) filterExpression;
                getProposalXPathVectorComplexVariables(retval,
                        myFollowClass,
                        oestring,
                        variableReferenceExpr.getVariableName(),
                        pathExpression.getLocationPath(),
                        docpos,
                        false,
                        addAt);
            }
        } else if (expression instanceof VariableReferenceExpr) {
            VariableReferenceExpr variableReferenceExpr =
                    (VariableReferenceExpr) expression;
            String variableName = variableReferenceExpr.getVariableName();
            if (variableName != null) {
                getProposalXPathVectorSimpleVariables(retval,
                        myFollowClass,
                        variableName,
                        docpos);
            }
        } else if (expression instanceof LocationPath) {
            LocationPath locationPathExpr = (LocationPath) expression;
            String variableName = null;
            List<Step> stepList = locationPathExpr.getSteps();
            if (stepList != null && !stepList.isEmpty()) {
                Step firstStep = stepList.iterator().next();
                if (firstStep != null) {
                    variableName = XPathUtil.getStepName(firstStep);
                }
            }
            if (variableName != null) {
                getProposalXPathVectorComplexVariables(retval,
                        myFollowClass,
                        oestring,
                        variableName,
                        locationPathExpr,
                        docpos,
                        true,
                        addAt);
            }
        }
    }

    protected MyFollowClass addFunctions(MyFollowClass myFollowClass) {
        Collection<JsClass> classList = readContributedClass();
        for (JsClass jsClass : classList) {
            List<JsMethod> methodList = jsClass.getMethodList();
            if (methodList != null) {
                for (JsMethod jsMethod : methodList) {
                    MyCompletionStringNode myCompletionStringNode =
                            new MyCompletionStringNode(jsMethod);
                    myFollowClass
                            .addMyCompletionStringNode(myCompletionStringNode);
                }
            }
        }
        return myFollowClass;
    }

    private boolean isReferenceStart(String oestring) {
        boolean isVariableReference = false;
        if (oestring != null && oestring.length() > 0) {
            oestring = oestring.trim();
            if (oestring.startsWith("$") || oestring.startsWith("/") //$NON-NLS-1$ //$NON-NLS-2$
                    || oestring.startsWith("//")) { //$NON-NLS-1$
                return true;
            }
        }
        return isVariableReference;
    }

    private void getProposalXPathVectorSimpleVariables(
            Vector<CustomCompletionProposal> retval,
            MyFollowClass myFollowClass, String oestring, int docpos) {
        Iterator<MyCompletionStringNode> myCompNodeItr =
                myFollowClass.getCompletionNodes().iterator();
        // Get the elements inside the start class
        while (myCompNodeItr.hasNext()) {
            MyCompletionStringNode myCompNode = myCompNodeItr.next();
            if (myCompNode != null) {
                String cs = myCompNode.getCompletionString();
                String additionalInfo = myCompNode.getComment();
                if (cs != null
                        && (cs.toUpperCase().startsWith(oestring.trim()
                                .toUpperCase())
                                || cs.equals("") || oestring.trim().equals(""))) { //$NON-NLS-1$ //$NON-NLS-2$
                    CustomCompletionProposal cap =
                            new CustomCompletionProposal(cs, docpos
                                    - oestring.length(), oestring.length(),
                                    cs.length(), myCompNode.getImage(),
                                    myCompNode.getCompletionString(), null,
                                    additionalInfo);
                    retval.add(cap);
                }
            }
        }
    }

    private void getProposalXPathVectorComplexVariables(
            Vector<CustomCompletionProposal> retval,
            MyFollowClass myFollowClass, String oestring, String variableName,
            LocationPath locationPath, int docpos, boolean fullLocationPath,
            boolean addAt) {
        myFollowClass = new MyFollowClass(START_CLASS);
        if (variableName != null) {
            // Search the variable among scriptRelevantData
            IScriptRelevantData scriptRelevantData = null;
            if (getScriptDataAsMap() != null) {
                scriptRelevantData = getScriptDataAsMap().get(variableName);
            }
            if (scriptRelevantData instanceof IUMLScriptRelevantData) {
                IUMLScriptRelevantData iUMLScriptRelevantData =
                        (IUMLScriptRelevantData) scriptRelevantData;
                LocationPath preparedLocationPath =
                        prepareSimpleLocationPathExpr(fullLocationPath,
                                locationPath);
                getNextProposalXPathVector(retval,
                        myFollowClass,
                        iUMLScriptRelevantData,
                        preparedLocationPath,
                        docpos,
                        addAt);
            } else {
                // Search the variable among complex nodes
                Node complexScriptRelevantData = null;
                if (getComplexScriptDataAsMap() != null) {
                    complexScriptRelevantData =
                            getComplexScriptDataAsMap().get(variableName);
                    if (complexScriptRelevantData != null) {
                        Expr preparedLocationPath =
                                prepareComplexLocationPathExpr(fullLocationPath,
                                        variableName,
                                        locationPath);
                        String strToReplace =
                                getStringToReplace(oestring, locationPath);
                        if (preparedLocationPath != null
                                && strToReplace != null) {
                            getNextProposalXPathVector(retval,
                                    myFollowClass,
                                    complexScriptRelevantData,
                                    preparedLocationPath,
                                    docpos,
                                    strToReplace,
                                    addAt);
                        }
                    }
                }
            }
        }
    }

    private String getStringToReplace(String oestring, LocationPath locationPath) {
        String strToReplace = null;
        if (locationPath != null && oestring != null) {
            List<Step> stepList = locationPath.getSteps();
            if (stepList != null && !stepList.isEmpty()) {
                Step lastStep = stepList.get(stepList.size() - 1);
                if (lastStep != null) {
                    String stepName = XPathUtil.getStepName(lastStep);
                    if (stepName != null) {
                        if (oestring.endsWith(stepName)) {
                            strToReplace = stepName;
                        }
                    }
                }
            }
        }
        return strToReplace;
    }

    private LocationPath prepareSimpleLocationPathExpr(
            boolean fullLocationPath, LocationPath locationPath) {
        if (!fullLocationPath) {
            return locationPath;
        }
        LocationPath preparedLocationPathExpr = null;
        try {
            preparedLocationPathExpr =
                    XPathUtil.getXPathFactory().createAbsoluteLocationPath();
            if (locationPath != null) {
                List<Step> locationSteps = locationPath.getSteps();
                if (locationSteps != null) {
                    Iterator iterator = locationSteps.iterator();
                    // Jump the first element
                    if (iterator.hasNext()) {
                        iterator.next();
                    }
                    for (; iterator.hasNext();) {
                        Step step = (Step) iterator.next();
                        preparedLocationPathExpr.addStep(step);
                    }
                }
            }
        } catch (JaxenException ex) {

        }
        return preparedLocationPathExpr;
    }

    private LocationPath prepareComplexLocationPathExpr(
            boolean fullLocationPath, String rootName, LocationPath locationPath) {
        LocationPath preparedLocationPathExpr = null;
        try {
            preparedLocationPathExpr =
                    XPathUtil.getXPathFactory().createAbsoluteLocationPath();
            if (!fullLocationPath) {
                Step rootStep =
                        XPathUtil.getXPathFactory().createNameStep(Axis.CHILD,
                                "", rootName);//$NON-NLS-1$S
                preparedLocationPathExpr.addStep(rootStep);
            }
            if (locationPath != null) {
                List<Step> locationSteps = locationPath.getSteps();
                if (locationSteps != null) {
                    for (Iterator iterator = locationSteps.iterator(); iterator
                            .hasNext();) {
                        Step step = (Step) iterator.next();
                        if (iterator.hasNext()) {
                            // Remove the predicates
                            step.getPredicates().clear();
                            preparedLocationPathExpr.addStep(step);
                        } else {
                            // Add a * or @* as last step to retrieve all the
                            // results
                            Step lastStep =
                                    XPathUtil.getXPathFactory()
                                            .createNameStep(step.getAxis(),
                                                    "", "*");//$NON-NLS-1$S//$NON-NLS-2$S
                            List<Predicate> predicateList =
                                    step.getPredicates();
                            for (Predicate predicate : predicateList) {
                                lastStep.addPredicate(predicate);
                            }
                            preparedLocationPathExpr.addStep(lastStep);
                        }
                    }
                }
            }
        } catch (JaxenException ex) {

        }
        return preparedLocationPathExpr;
    }

    private void getNextProposalXPathVector(
            Vector<CustomCompletionProposal> retval,
            MyFollowClass myFollowClass, Node complexScriptRelevantData,
            Expr expression, int docpos, String strToReplace, boolean addAt) {
        int strToReplaceLength = strToReplace.length();
        if (strToReplace.equals("*")) {//$NON-NLS-1$
            strToReplace = "";//$NON-NLS-1$
            strToReplaceLength--;
        }
        if (complexScriptRelevantData != null && expression != null) {
            List pathNodeResults =
                    XPathUtil
                            .getXPathExpressionEvaluationNodeResults(complexScriptRelevantData,
                                    expression.getText(),
                                    getNamespaces());
            for (Object object : pathNodeResults) {
                if (object instanceof Element) {
                    Element element = (Element) object;
                    String cs = element.getNodeName();
                    if (cs != null
                            && (cs.toUpperCase()
                                    .startsWith(strToReplace.toUpperCase()) || strToReplace
                                    .equals(""))) {//$NON-NLS-1$
                        if (addAt) {
                            cs = "@" + cs;//$NON-NLS-1$
                        }
                        CustomCompletionProposal cap =
                                new CustomCompletionProposal(cs, docpos
                                        - strToReplaceLength,
                                        strToReplaceLength, cs.length(),
                                        XPathContentAssistUtils
                                                .getIconForNode(element), cs,
                                        null, "");//$NON-NLS-1$
                        retval.add(cap);
                    }
                }
            }
            if (!addAt) {
                addMyFollowAbreviations(myFollowClass);
                addCompletionStringsToVector(retval,
                        myFollowClass,
                        docpos,
                        strToReplaceLength);
            }
        }
    }

    private void getNextProposalXPathVector(
            Vector<CustomCompletionProposal> retval,
            MyFollowClass myFollowClass,
            IUMLScriptRelevantData iUMLScriptRelevantData,
            LocationPath locationPath, int docpos, boolean addAt) {
        int strToReplaceLength = 0;
        if (iUMLScriptRelevantData != null && locationPath != null) {
            JsClass jsClass = iUMLScriptRelevantData.getJsClass();
            List<Step> steps = locationPath.getSteps();
            if (jsClass != null && steps != null) {
                Iterator<Step> stepIterator = steps.iterator();
                while (stepIterator.hasNext()) {
                    Step step = stepIterator.next();
                    if (step != null) {
                        if (step.getAxis() == Axis.CHILD) {
                            String stepName = XPathUtil.getStepName(step);
                            if (stepIterator.hasNext()) {
                                JsReference jsReference =
                                        JScriptUtils.getJsReference(jsClass,
                                                stepName);
                                if (jsReference != null) {
                                    jsClass =
                                            jsReference.getReferencedJsClass();
                                } else {
                                    break;
                                }
                            } else {
                                if (stepName != null) {
                                    strToReplaceLength = stepName.length();
                                    if (stepName.equals("*")) {//$NON-NLS-1$
                                        stepName = "";//$NON-NLS-1$
                                        strToReplaceLength--;
                                    }
                                }
                                List<JsReference> jsReferenceList =
                                        JScriptUtils
                                                .getMatchingJsReference(jsClass,
                                                        stepName);
                                if (jsReferenceList != null) {
                                    for (JsReference jsReference : jsReferenceList) {
                                        addMyFollowJsReference(myFollowClass,
                                                jsReference);
                                    }
                                }
                                if (!addAt) {
                                    addMyFollowAbreviations(myFollowClass);
                                }
                            }
                        } else if (step.getAxis() == Axis.ATTRIBUTE) {
                            String stepName = XPathUtil.getStepName(step);
                            if (stepIterator.hasNext()) {
                                JsReference jsReference =
                                        JScriptUtils.getJsReference(jsClass,
                                                stepName);
                                if (jsReference != null) {
                                    jsClass =
                                            jsReference.getReferencedJsClass();
                                } else {
                                    break;
                                }
                            } else {
                                if (stepName != null) {
                                    strToReplaceLength = stepName.length();
                                    if (stepName.equals("*")) {//$NON-NLS-1$
                                        stepName = "";//$NON-NLS-1$
                                        strToReplaceLength--;
                                    }
                                }
                                List<JsAttribute> jsAttributeList =
                                        JScriptUtils
                                                .getMatchingJsAttribute(jsClass,
                                                        stepName);
                                if (jsAttributeList != null) {
                                    for (JsAttribute jsAttribute : jsAttributeList) {
                                        addMyFollowJsAttribute(myFollowClass,
                                                jsAttribute,
                                                addAt);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        addCompletionStringsToVector(retval,
                myFollowClass,
                docpos,
                strToReplaceLength);
    }

    private void addCompletionStringsToVector(
            Vector<CustomCompletionProposal> retval,
            MyFollowClass myFollowClass, int docpos, int strToReplaceLength) {
        Iterator<MyCompletionStringNode> myCompNodeItr =
                myFollowClass.getCompletionNodes().iterator();
        // Get the elements inside the start class
        while (myCompNodeItr.hasNext()) {
            MyCompletionStringNode myCompNode = myCompNodeItr.next();
            if (myCompNode != null) {
                String cs = myCompNode.getCompletionString();
                String additionalInfo = myCompNode.getComment();
                CustomCompletionProposal cap =
                        new CustomCompletionProposal(cs, docpos
                                - strToReplaceLength, strToReplaceLength,
                                cs.length(), myCompNode.getImage(),
                                myCompNode.getCompletionString(), null,
                                additionalInfo);
                retval.add(cap);
            }
        }
    }

    private void addMyFollowJsAttribute(MyFollowClass myFollowClass,
            JsAttribute attribute, boolean includeAt) {
        if (attribute != null && myFollowClass != null) {
            myFollowClass.addMyCompletionStringNode(new MyCompletionStringNode(
                    attribute, includeAt));
        }
    }

    private void addMyFollowJsReference(MyFollowClass myFollowClass,
            JsReference reference) {
        if (reference != null && myFollowClass != null) {
            myFollowClass.addMyCompletionStringNode(new MyCompletionStringNode(
                    reference));
        }
    }

    private void addMyFollowAbreviations(MyFollowClass myFollowClass) {
        if (abreviationMethods == null) {
            XPathAbreviationsDefinitionReader definitionReader =
                    new XPathAbreviationsDefinitionReader();
            List<JsClass> topAbreviationClasses =
                    definitionReader.getSupportedClasses();
            if (topAbreviationClasses != null
                    && !topAbreviationClasses.isEmpty()) {
                abreviationMethods = new ArrayList<JsMethod>();
                for (JsClass jsClass : topAbreviationClasses) {
                    if (jsClass != null && jsClass.getMethodList() != null) {
                        abreviationMethods.addAll(jsClass.getMethodList());
                    }
                }
            }
        }
        if (abreviationMethods != null && !abreviationMethods.isEmpty()) {
            for (JsMethod abreviationMethod : abreviationMethods) {
                myFollowClass
                        .addMyCompletionStringNode(new MyCompletionStringNode(
                                abreviationMethod));
            }
        }
    }

    @Override
    protected void addCustomCompletionString(MyFollowClass myStartFollowClass) {
        // Do nothing
    }

    @Override
    protected Image getImage(Template template) {
        Image image =
                ScriptEditorsPlugin.getDefault().getImageRegistry()
                        .get(XPathContentAssistConstants.Function);
        return image;
    }

    @Override
    protected String getArrayContentAssistName(String name) {
        return name;
    }

    @Override
    protected TemplateContextType getContextType(ITextViewer viewer,
            IRegion region) {
        return ScriptEditorsPlugin
                .getDefault()
                .getXPathContextTypeRegistry()
                .getContextType(XPathContentAssistConstants.XPATH_TEMPLATE_CONTEXT);
    }

    @Override
    protected Map<String, IScriptRelevantData> getLocalScriptDataMap() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Map<String, IScriptRelevantData> getGlobalScriptDataMap() {
        // TODO Auto-generated method stub
        return null;
    }

    protected MyFollowClass getStartMyFollowClass() {
        myStartFollowClass = new MyFollowClass(START_CLASS);
        if (myStartFollowClass == null) {
            return myStartFollowClass;
        }
        addCustomCompletionString(myStartFollowClass);
        return myStartFollowClass;
    }

    protected Collection<JsClass> readXPathContributedClass() {
        Collection<JsClass> allSupporteClasses = new ArrayList<JsClass>();
        List<JsClassDefinitionReader> jsClassProvider =
                getClassDefinitionReaders();
        for (JsClassDefinitionReader classDefinitionReader : jsClassProvider) {
            if (classDefinitionReader != null) {
                Collection<JsClass> supportedClasses =
                        classDefinitionReader.getSupportedClasses();
                allSupporteClasses.addAll(supportedClasses);
            }
        }
        return allSupporteClasses;
    }

    /**
     * @see com.tibco.xpd.script.sourceviewer.internal.contentassist.AbstractTibcoContentAssistProcessor#getGrammarType()
     * 
     * @return
     */
    @Override
    protected String getGrammarType() {
        return XPathContentAssistConstants.XPATH_GRAMMAR;
    }

    /**
     * @see com.tibco.xpd.script.sourceviewer.internal.contentassist.AbstractTibcoContentAssistProcessor#getNodeImage(org.w3c.dom.Node)
     * 
     * @param node
     * @return
     */
    @Override
    protected Image getNodeImage(Node node) {
        return XPathContentAssistUtils.getIconForNode(node);
    }

    /**
     * @see com.tibco.xpd.script.sourceviewer.internal.contentassist.AbstractTibcoContentAssistProcessor#getCommonUIPreferenceNames()
     * 
     * @return
     * @throws CoreException
     */
    @Override
    protected AbstractScriptCommonUIPreferenceNames getCommonUIPreferenceNames() {
        try {
            return ScriptGrammarContributionsUtil.INSTANCE
                    .getScriptCommonUIPreferenceNames(getGrammarType());
        } catch (CoreException e) {
            LOG.error(e);
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.script.sourceviewer.internal.contentassist.AbstractTibcoContentAssistProcessor#getPreferenceStore()
     * 
     * @return
     */
    @Override
    protected IPreferenceStore getPreferenceStore() {
        return ScriptEditorsPlugin.getDefault().getPreferenceStore();
    }
}
