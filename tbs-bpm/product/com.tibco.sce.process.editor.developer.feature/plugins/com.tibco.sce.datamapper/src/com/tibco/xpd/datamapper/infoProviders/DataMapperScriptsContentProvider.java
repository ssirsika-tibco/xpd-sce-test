/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.datamapper.infoProviders;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.datamapper.internal.Messages;
import com.tibco.xpd.datamapper.scripts.IScriptDataMapperProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptInformationComparator;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Script provider for Process Data Mapper user defined scripts.
 * 
 * @author Ali
 * @since 21 Jan 2015
 */
public class DataMapperScriptsContentProvider implements
        IStructuredContentProvider {

    public static final String JSCRIPT_DESTINATION = "javaScript1.x"; //$NON-NLS-1$

    private IScriptDataMapperProvider sdmProvider;

    private DirectionType direction;

    /**
     * @param mappingContext
     * @param isInputMapping
     *            true if this is for input mappings.
     */
    public DataMapperScriptsContentProvider(
            IScriptDataMapperProvider sdmProvider, boolean isInputMapping) {
        this.sdmProvider = sdmProvider;
        direction =
                isInputMapping ? DirectionType.IN_LITERAL
                        : DirectionType.OUT_LITERAL;
    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     * 
     */
    @Override
    public void dispose() {
    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param viewer
     * @param oldInput
     * @param newInput
     */
    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

    /**
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     * 
     * @param inputElement
     * @return
     */
    @Override
    public Object[] getElements(Object inputElement) {
        Set<ScriptInformation> scripts =
                new TreeSet<ScriptInformation>(
                        new ScriptInformationComparator());
        if (inputElement instanceof Activity) {

            Activity activity = (Activity) inputElement;

            ScriptInformation details = getDefaultNewScript(activity);
            scripts.add(details);

            ScriptDataMapper scriptDataMapper =
                    sdmProvider.getScriptDataMapper(activity);
            if (scriptDataMapper != null) {
                // get scripts that aren't mapped yet
                List<ScriptInformation> unammpedScriptsList =
                        scriptDataMapper.getUnmappedScripts();
                for (ScriptInformation unammpedScript : unammpedScriptsList) {
                    scripts.add(unammpedScript);
                }

                // get mapped scripts
                List<DataMapping> mappings = scriptDataMapper.getDataMappings();
                for (DataMapping mapping : mappings) {
                    Object other =
                            Xpdl2ModelUtil.getOtherElement(mapping,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_Script());
                    if (other instanceof ScriptInformation) {
                        ScriptInformation information =
                                (ScriptInformation) other;
                        scripts.add(information);
                    }
                }
            }
        }
        return scripts.toArray();
    }

    /**
     * Creates and return default new JavaScript script
     * 
     * @param activity
     * 
     * @return ScriptInformation
     */
    private ScriptInformation getDefaultNewScript(Activity activity) {
        ScriptInformation details =
                XpdExtensionFactory.eINSTANCE.createScriptInformation();
        details.setName(Messages.DataMapperScriptsContentProvider_NewScriptLabel);
        details.setActivity(activity);
        Expression expression = Xpdl2ModelUtil.createExpression(""); //$NON-NLS-1$
        expression.setScriptGrammar(ScriptGrammarFactory.JAVASCRIPT);
        details.setExpression(expression);
        details.setDirection(direction);
        return details;
    }

    /**
     * @param expression
     * @return
     */
    public static ScriptDataMapper getExistingScriptDataMapper(
            Expression expression) {
        if (expression != null && !expression.getMixed().isEmpty()) {

            EList<?> scriptDataMapperList =
                    (EList<?>) expression
                            .getMixed()
                            .get(XpdExtensionPackage.eINSTANCE.getDocumentRoot_ScriptDataMapper(),
                                    true);

            // we expect to have one such element in the expression
            if (scriptDataMapperList.size() == 1
                    && scriptDataMapperList.get(0) != null) {
                return (ScriptDataMapper) scriptDataMapperList.get(0);
            }
        }
        return null;
    }
}
