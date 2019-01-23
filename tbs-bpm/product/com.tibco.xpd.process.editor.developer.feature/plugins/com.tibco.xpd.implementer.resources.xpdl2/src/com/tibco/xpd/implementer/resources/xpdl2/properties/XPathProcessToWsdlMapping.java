/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.xsd.XSDTypeDefinition;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.implementer.script.ActivityMessageUtil;
import com.tibco.xpd.implementer.script.IWsdlPath;
import com.tibco.xpd.implementer.script.WsdlUtil;
import com.tibco.xpd.process.xpath.model.ProcessXPathConsts;
import com.tibco.xpd.process.xpath.model.XPathConsts;
import com.tibco.xpd.process.xpath.parser.util.XPathScriptParserUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.JavaScriptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.client.DefaultScriptRelevantData;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class XPathProcessToWsdlMapping implements SingleMappingCompositor {

    /** The script grammar. */
    private static final String SCRIPT_GRAMMAR = "XPath"; //$NON-NLS-1$

    /** The activity. */
    private Activity activity;

    /** The target. */
    private String target;

    /** The script. */
    private String script;

    /**
     * @param activity
     *            The activity.
     * @param target
     *            The target.
     */
    public XPathProcessToWsdlMapping(Activity activity, String target) {
        this.activity = activity;
        this.target = target;
    }

    /**
     * @param activity
     *            The activity.
     * @param target
     *            The target.
     * @param script
     *            The current script.
     */
    public XPathProcessToWsdlMapping(Activity activity, String target,
            String script) {
        this(activity, target);
        this.script = script;
    }

    /**
     * @param source
     *            The source parameter.
     * @param target
     *            The target parameter.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor#
     *      addMapping(java.lang.Object, java.lang.Object)
     */
    public void addMapping(Object source, Object target) {
        if (script == null) {
            script = ""; //$NON-NLS-1$
        }
        if (script.length() != 0) {
            script += " + "; //$NON-NLS-1$
        }
        script += ActivityMessageUtil.getPath(source);
    }

    /**
     * @return true if the script contains any valid mappings.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor#
     *      containsMappings()
     */
    public boolean containsMappings() {
        boolean valid = false;
        if (getSourceItemNames().size() > 0) {
            valid = true;
        }
        return valid;
    }

    /**
     * @return The full script as an Expression.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor#
     *      getExpression()
     */
    public Expression getExpression() {
        Expression actual = Xpdl2ModelUtil.createExpression(script == null ? "" //$NON-NLS-1$
                : script);
        actual.setScriptGrammar(SCRIPT_GRAMMAR);
        return actual;
    }

    /**
     * @return The target.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor#
     *      getTarget()
     */
    public String getTarget() {
        return target;
    }

    /**
     * @param source
     *            The source parameter.
     * @param target
     *            The target parameter.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor#
     *      removeMapping(java.lang.Object, java.lang.Object)
     */
    public void removeMapping(Object source, Object target) {
        String parameter = ActivityMessageUtil.getPath(source);
        script = JavaScriptUtil.removeParameter(script, parameter);
    }

    /**
     * @param target
     *            The target.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor#setTarget(java.lang.String)
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * @return The script.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor#getScript()
     */
    public String getScript() {
        return script;
    }

    /**
     * @param isInput
     *            true if input parameters, false if output.
     * @return The source items.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor#getSourceItems()
     */
    public Collection<Object> getSourceItems(boolean isInput) {
        Collection<Object> items = new ArrayList<Object>();
        for (String name : getSourceItemNames()) {
            ConceptPath path = ConceptUtil.resolveConceptPath(activity, name);
            items.add(path);
        }
        return items;
    }

    /**
     * @return A collection of source item names.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor#getSourceItemNames()
     */
    public Collection<String> getSourceItemNames() {
        Collection<String> items = new ArrayList<String>();
        StringTokenizer tokenizer = new StringTokenizer(script, "+"); //$NON-NLS-1$
        while (tokenizer.hasMoreTokens()) {
            items.add(tokenizer.nextToken().trim());
        }
        // List<String> destinationList = getProcessDestinationList(activity
        // .getProcess());
        // ISymbolTable symbolTable = new XPathSymbolTable();
        // List<IValidationStrategy> validationStrategyList =
        // getValidationStrategyList(activity
        // .getProcess());
        // Map<String, List<ErrorMessage>> validationErrorMap = new
        // HashMap<String, List<ErrorMessage>>();
        // Map<String, List<ErrorMessage>> validationWarningMap = new
        // HashMap<String, List<ErrorMessage>>();
        // Part wsdlPart = null;
        // boolean isWsdlSupported = false;
        // IScriptRelevantData mappingType = getMappingType();
        // XPathParser parser =
        // XPathScriptParserUtil.validateXPathScript(script,
        // destinationList, symbolTable, validationStrategyList,
        // validationErrorMap, validationWarningMap, wsdlPart,
        // isWsdlSupported, mappingType);
        // items = parser.getSymbolTable().getVariablesInUse();
        return items;
    }

    private IScriptRelevantData getMappingType() {
        String sType = XPathConsts.XPATH_TYPE_UNDEFINED;
        IScriptRelevantData type =
                new DefaultScriptRelevantData(sType, sType, false);
        IWsdlPath wsdlPath = WsdlUtil.resolveParameter(activity, target, true);
        if (wsdlPath != null) {
            XSDTypeDefinition wsdlType = wsdlPath.getType();
            if (wsdlType != null && wsdlType.getName() != null) {
                type =
                        new DefaultScriptRelevantData(wsdlType.getName(),
                                wsdlType.getName(), false);
            }
        }
        return type;
    }

    protected List<IValidationStrategy> getValidationStrategyList(
            Process process) {
        List<String> processDestList = getProcessDestinationList(process);
        List<IValidationStrategy> validationStrategy = Collections.EMPTY_LIST;
        try {
            validationStrategy =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getValidationStrategy(processDestList,
                                    XPathScriptParserUtil.WEBSERVICE_TASK,
                                    ProcessXPathConsts.XPATH_GRAMMAR,
                                    ProcessXPathConsts.XPATH_DESTINATION);
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return validationStrategy;
    }

    protected List<String> getProcessDestinationList(Process process) {
        List<String> processDestList =
                new ArrayList<String>(DestinationUtil
                        .getEnabledValidationDestinations(process));

        if (processDestList == null || processDestList.isEmpty()) {
            processDestList = new ArrayList<String>();
            processDestList.add(XPathScriptParserUtil.XPATH_DESTINATION);
        }
        return processDestList;
    }

    /**
     * @param script
     *            The script.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor#setScript(java.lang.String)
     */
    public void setScript(String script) {
        this.script = script;
    }

    /**
     * @param source
     *            The source item to remove from the mapping.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor#
     *      removeAllMappingsFrom(java.lang.Object)
     */
    public void removeAllMappingsFrom(Object source) {
        String parameter = ActivityMessageUtil.getPath(source);
        script = JavaScriptUtil.removeParameter(script, parameter);
    }

}
