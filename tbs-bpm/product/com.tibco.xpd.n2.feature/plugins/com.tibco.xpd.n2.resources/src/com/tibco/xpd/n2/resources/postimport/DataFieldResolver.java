/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.resources.postimport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

import antlr.Token;
import antlr.TokenStreamException;

/**
 * Used to resolve field references during the parsing of the script. For performance, maintains a local cache of
 * previous field resolution results (positive and negative). Thus, a new DataFieldResolver will be required for each script
 * to be parsed.
 *
 * @author pwatson
 * @since 7 Aug 2019
 */
class DataFieldResolver {

    // used to record failed lookups
    private static ConceptPath NULL_PATH = new ConceptPath(null, null);

    // the process in which the script resides.
    private Process process;

    // the, optional, activity in which the script resides - determines the scope of available fields
    private Activity activity;

    // the Expression whose script is currently being refactored
    private Expression expression;

    // the BOM models referenced by the script's project
    private final Collection<Model> bomModels;

    // a cache of previous look-ups
    private final Map<String, ConceptPath> resolved = new HashMap<>();

    public DataFieldResolver(Package aPackage) throws CoreException {
        IProject project = WorkingCopyUtil.getProjectFor(aPackage);

        // look-up all BOM models referenced by the project
        Collection<IProject> referencedProjects = Xpdl2ModelUtil.getAllReferencedProjects(project);
        bomModels = new HashSet<>();
        for (IProject refProject : referencedProjects) {
            bomModels.addAll(BomInspector.getBomModels(refProject));
        }
    }

    /**
     * Sets the Expression currently being processed. This will determine the scope of data fields that can be resolved.
     * 
     * @param aValue
     *            the Expression containing the script currently being refactored.
     */
    public void setExpression(Expression aValue) {
        expression = aValue;
        process = Xpdl2ModelUtil.getProcess(aValue);
        activity = Xpdl2ModelUtil.getParentActivity(aValue);
    }

    /**
     * Returns all fields (data types), from all referenced BOM models, that are assignment-compatible with the given
     * class. If no suitable fields are found, the result will be an empty collection.
     * 
     * @param <T>
     *            the possible classes allowed.
     * @param aRequiredClass
     *            the class to which those in the resulting collection will be assignment-compatible.
     * @return the collection of resulting fields.
     */
    public <T extends PackageableElement> Collection<T> getBOMDataTypes(final Class<T> aRequiredClass) {
        Collection<T> result = new ArrayList<>();
        for (Model model : bomModels) {
            result.addAll(BomInspector.getFields(model, aRequiredClass));
        }

        return result;
    }

    /**
     * Get all the data in scope of the activity (if it's a script under an activity) else the process.
     * 
     * @return The data in scope of the current expression.
     */
    public Collection<ProcessRelevantData> getInScopeData() {
        /*
         * If the expression is in a conditional flow, then we need to get the data available in it's container.
         */
        Transition seqFlowParent = (Transition) Xpdl2ModelUtil.getAncestor(expression, Transition.class);
        if (seqFlowParent != null) {
            return ProcessInterfaceUtil.getAllAvailableRelevantDataForSequenceFlow(seqFlowParent);
        }

        /* If the expression is in an activity get all the data available to it. */
        if (activity != null) {
            return ProcessInterfaceUtil.getAllAvailableRelevantDataForActivity(activity);
        }

        /* Fall back on all data in process. */
        if (process != null) {
            return ProcessInterfaceUtil.getAllProcessRelevantData(process);
        }

        return Collections.emptyList();
    }

    /**
     * Resolve the field referenced by the parser token at the given index.
     * 
     * @param aParser
     *            the parser from which tokens are traversed.
     * @param aIndex
     *            the index of the field name token to be resolved.
     * @return the resolved field reference, or null if not resolved.
     * @throws TokenStreamException
     */
    public ConceptPath resolve(JScriptParser aParser, int aIndex) throws TokenStreamException {
        // if we couldn't resolve the activity or process
        if ((activity == null) && (process == null)) {
            return null; // we can't resolve the field
        }

        StringBuilder pathBuilder = new StringBuilder();
        Token token = aParser.LT(aIndex);
        while (token != null) {
            int tokenType = token.getType();

            // if we've backed into a method call
            if (tokenType == JScriptTokenTypes.RPAREN) {
                return null; // we can't resolve the field
            }

            // if it's not a path element or delimiter
            if ((tokenType != JScriptTokenTypes.DOT) && (tokenType != JScriptTokenTypes.IDENT)) {
                break; // stop loop
            }

            pathBuilder.insert(0, token.getText());

            // are we at the first token
            if (aIndex <= 1) {
                break; // stop loop
            }
            token = aParser.LT(--aIndex);
        }

        String path = pathBuilder.toString();

        // if no path constructed
        if (path.isEmpty()) {
            return null; // we can't resolve the field
        }

        // look up from local cache first
        ConceptPath result = resolved.get(path);
        if (result == null) {
            // call the util and cache result if found
            result = (activity != null) ? ConceptUtil.resolveConceptPath(activity, path, true)
                    : ConceptUtil.resolveConceptPath(process, path, true);
            if (result == null) {
                // record failed look-ups to prevent trying again
                result = DataFieldResolver.NULL_PATH;
            }

            resolved.put(path, result);
        }

        return (result == DataFieldResolver.NULL_PATH) ? null : result;
    }
}
