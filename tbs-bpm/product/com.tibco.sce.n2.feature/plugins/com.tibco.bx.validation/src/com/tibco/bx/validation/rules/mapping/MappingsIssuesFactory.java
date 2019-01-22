/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping;

import org.eclipse.uml2.uml.Classifier;

import com.tibco.bx.validation.rules.mapping.handlers.BomToBomSimpleMappingsHandler;
import com.tibco.bx.validation.rules.mapping.handlers.BomToProcessSimpleMappingsHandler;
import com.tibco.bx.validation.rules.mapping.handlers.IntfScriptMappingsIssuesHandler;
import com.tibco.bx.validation.rules.mapping.handlers.IntfSimpleMappingsIssuesHandler;
import com.tibco.bx.validation.rules.mapping.handlers.ProcessToBomSimpleMappingsHandler;
import com.tibco.bx.validation.rules.mapping.handlers.ProcessToProcessSimpleMappingsHandler;
import com.tibco.bx.validation.rules.mapping.handlers.ScriptToBomMappingsHandler;
import com.tibco.bx.validation.rules.mapping.handlers.ScriptToProcessMappingsHandler;
import com.tibco.bx.validation.rules.mapping.helpers.BaseScriptToConceptPathMappingsHelper;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.xpdl2.Activity;

/**
 * 
 * 
 * @author bharge
 * @since 9 Nov 2011
 */
public class MappingsIssuesFactory {

    public final static MappingsIssuesFactory INSTANCE =
            new MappingsIssuesFactory();

    /**
     * @param activity
     * @param source
     * @param target
     * @return a handler class to compute the mappings issues (or
     *         <code>null</code> if cannot find a handler). <br>
     *         The different handlers returned are - <br>
     *         <strong> {@link ScriptToBomMappingsHandler} </strong><br>
     *         <strong> {@link ScriptToProcessMappingsHandler}<strong>
     */
    public IntfScriptMappingsIssuesHandler getMappingsCompatibilityHandler(
            Activity activity, IScriptRelevantData type, ConceptPath target) {

        Classifier sourceType =
                BaseScriptToConceptPathMappingsHelper
                        .getSourceScriptTypeClassifier(type);
        Classifier targetType = target.getType();

        String sourceTypeStr =
                BaseScriptToConceptPathMappingsHelper.getSourceTypeStr(type,
                        sourceType);

        if (null != sourceTypeStr) {

            if (null != targetType) {

                /**
                 * Handle all script to BOM type mappings (including mixed
                 * prim->Class)
                 */
                return new ScriptToBomMappingsHandler(activity, type, target);

            } else {
                /** this would be script to process mapping */
                return new ScriptToProcessMappingsHandler(activity, type,
                        target);
            }
        }

        return null;
    }

    /**
     * @param activity
     * @param source
     * @param target
     * @return a handler class to compute the mappings issues (or
     *         <code>null</code> if cannot find a handler). <br>
     *         The different handlers returned are - <br>
     *         <strong> {@link BomToBomSimpleMappingsHandler} </strong><br>
     *         <strong> {@link ProcessToBomSimpleMappingsHandler} </strong><br>
     *         <strong> {@link BomToProcessSimpleMappingsHandler} </strong><br>
     *         <strong> {@link ProcessToProcessSimpleMappingsHandler} </strong>
     */
    public IntfSimpleMappingsIssuesHandler getMappingsCompatibilityHandler(
            Activity activity, ConceptPath source, ConceptPath target) {

        Classifier sourceType = source.getType();
        Classifier targetType = target.getType();

        if (sourceType != null && targetType != null) {
            /** This is the case when it is a BOM to BOM Type mapping */
            return new BomToBomSimpleMappingsHandler(activity, source, target);

        } // else {

        if (sourceType == null && targetType != null) {
            /**
             * This would be process data to BOM data. source = Process data
             * target = wsdl data
             */
            return new ProcessToBomSimpleMappingsHandler(activity, source,
                    target);

        } // else
        if (sourceType != null && targetType == null) {
            /**
             * This would be BOM data to process data. source = wsdl target =
             * process
             */
            return new BomToProcessSimpleMappingsHandler(activity, source,
                    target);

        } // else
        if (sourceType == null && targetType == null) {
            /**
             * Can get here when doing request activity correlation mapping for
             * generated start event (both sides may be formal params).
             * 
             * So just compare basic types.
             */
            return new ProcessToProcessSimpleMappingsHandler(activity, source,
                    target);
            // TODO :Don't know whether it will reach here, because there
            // can't be Process to Process data types mapped for WS
        }
        // }

        return null;

    }
}
