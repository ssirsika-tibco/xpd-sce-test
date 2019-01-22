/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.datamapper.scripts;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.datamapper.api.DataMapperUtils;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.extensions.IScriptGrammarConverter;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.ui.util.MessageDialogUtil;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * The Data Mapper grammar converter contribution for converting Data Mapper
 * user-defined script mappings to JavaScript.
 * <p>
 * This is only enabled when there are user-defined script mappings in the Data
 * Mapper
 * </p>
 * 
 * @author Ali/Sid
 * @since 6 Mar 2015
 */
public class DataMapperMappingScriptsToJavaScriptContribution implements
        IScriptGrammarConverter {

    private static final String DONT_ASK_ABOUT_DATAMAPPINGS_TO_JAVASCRIPT =
            "Dont_ask_about_datamappings_to_javascript_conversion2"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.IScriptGrammarConverter#convertScriptForGrammar(java.lang.String,
     *      java.lang.String, java.lang.String, org.eclipse.emf.ecore.EObject)
     * 
     * @param sourceGrammar
     * @param sourceScript
     * @param targetGrammar
     * @param expression
     * @return
     */
    @Override
    public String convertScriptForGrammar(String sourceGrammar,
            String sourceScript, String targetGrammar, Expression expression,
            String context) throws GrammarConversionCancelledException {

        /*
         * Sid XPD-7408. Uncomment this code to update data mapper script gen
         * test processes (adds converted script to activity description of each
         * data mapper task.
         */
        // if (true) {
        // com.tibco.xpd.xpdl2.Process process =
        // (com.tibco.xpd.xpdl2.Process) Xpdl2ModelUtil
        // .getAncestor(expression,
        // com.tibco.xpd.xpdl2.Process.class);
        // DataMapperJavascriptGenerator
        // .updateDataMapperScriptGenTestProcess(process);
        //            return "- all activity descriptions updated with javascript from converted data mappings- "; //$NON-NLS-1$
        // }

        /*
         * If converting from DataMapper to Javascript grammar, confirm if user
         * wants to convert if the DataMapper mappings have user-defined script
         * mappings.
         */
        if (ScriptGrammarFactory.JAVASCRIPT.equals(targetGrammar)
                && expression != null
                && ScriptGrammarFactory.DATAMAPPER.equals(sourceGrammar)) {

            int ret = IDialogConstants.YES_ID;

            /*
             * Only show dialog if there are user defined script mappings.
             */
            if (Display.getCurrent() != null && hasScriptMappings(expression)) {

                ret =
                        MessageDialogUtil
                                .openYesNoCancelQuestion(null,
                                        Messages.ProcessScriptUtil_dataMappingToJavascriptTitle,
                                        Messages.ProcessScriptUtil_dataMappingToJavascriptDesc,
                                        Messages.ProcessScriptUtil_dataMappingToJavascriptAlways,
                                        false,
                                        DONT_ASK_ABOUT_DATAMAPPINGS_TO_JAVASCRIPT,
                                        Xpdl2ProcessEditorPlugin.getDefault()
                                                .getPreferenceStore(),
                                        2);
            }

            if (ret == IDialogConstants.YES_ID) {
                if (sourceGrammar.equals(ScriptGrammarFactory.DATAMAPPER)
                        && targetGrammar
                                .equals(ScriptGrammarFactory.JAVASCRIPT)
                        && expression != null) {

                    ScriptDataMapper scriptDataMapper =
                            DataMapperUtils
                                    .getExistingScriptDataMapper(expression);

                    if (scriptDataMapper != null) {

                        /* XPD_7651 for debug only. */
                        // DataMapperJavascriptGeneratorOLD oldGen =
                        // new DataMapperJavascriptGeneratorOLD();
                        //
                        // String oldretu =
                        // oldGen.convertMappingsToJavascript(scriptDataMapper);

                        DataMapperJavascriptGenerator jsGenerator =
                                new DataMapperJavascriptGenerator();

                        String retu =
                                jsGenerator
                                        .convertMappingsToJavascript(scriptDataMapper);

                        return retu;
                    }
                }

            } else if (ret == IDialogConstants.NO_ID) {
                /*
                 * User elected not to convert to JavaScript.
                 */
                return ""; //$NON-NLS-1$

            } else {
                /*
                 * User cancelled.
                 */
                throw new GrammarConversionCancelledException();
            }
        }

        return ""; //$NON-NLS-1$
    }

    /**
     * @param expression
     * @return <code>true</code> if there are script mappings in teh contained
     *         ScriptDataMapper Element.
     */
    private boolean hasScriptMappings(Expression expression) {
        ScriptDataMapper sdm =
                DataMapperUtils.getExistingScriptDataMapper(expression);

        if (sdm != null) {
            if (!sdm.getUnmappedScripts().isEmpty()) {
                return true;
            }

            for (DataMapping dataMapping : sdm.getDataMappings()) {

                Object scriptInfo =
                        Xpdl2ModelUtil.getOtherElement(dataMapping,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_Script());

                if (scriptInfo != null) {
                    return true;
                }

            }
        }

        return false;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.IScriptGrammarConverter#isEnabled(org.eclipse.emf.ecore.EObject)
     * 
     * @param contextObject
     * @return
     */
    @Override
    public boolean isEnabled(EObject contextObject) {
        return true;
    }

}
