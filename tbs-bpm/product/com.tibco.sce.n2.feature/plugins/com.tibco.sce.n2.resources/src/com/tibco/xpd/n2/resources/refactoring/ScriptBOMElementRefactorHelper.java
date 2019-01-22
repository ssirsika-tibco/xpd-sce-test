/*
 * Copyright (c) TIBCO Software Inc. 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.n2.resources.refactoring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.js.validation.tools.PackageScopeEnumCache;
import com.tibco.xpd.process.js.parser.util.ScriptRefactorParserUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.parser.antlr.JScriptRefactorParser;
import com.tibco.xpd.script.parser.internal.refactoring.IRefactoringStrategy;
import com.tibco.xpd.script.parser.internal.refactoring.RefactorResult;
import com.tibco.xpd.script.parser.internal.refactoring.RefactoringInfo;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;
import com.tibco.xpd.script.parser.validator.ISymbolTable;
import com.tibco.xpd.script.parser.validator.SymbolTable;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;
import com.tibco.xpd.script.ui.internal.AbstractScriptRelevantDataProvider;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * @author mtorres
 * 
 */
public class ScriptBOMElementRefactorHelper {

    /**
     * Returns the activities and seq flows that can possibly reference the
     * modified Object
     * 
     * @param eObject
     * @return
     */
    public static List<EObject> getReferencingScriptContainerCandidates(
            EObject eObject) {
        if (eObject != null) {
            IProject project = WorkingCopyUtil.getProjectFor(eObject);
            if (project != null) {
                IPath relativePath =
                        SpecialFolderUtil.getSpecialFolderRelativePath(eObject);
                Set<String> referencingProcesses =
                        ProcessUIUtil.queryReferencingXpdls(project,
                                relativePath.toString(),
                                false);
                if (referencingProcesses != null) {
                    List<EObject> referencingScriptContainerCandidates =
                            new ArrayList<EObject>();
                    for (String referencingProcess : referencingProcesses) {
                        URI uri = URI.createURI(referencingProcess);
                        String xpdlPath = null;
                        if (uri != null) {
                            if (uri.isFile()) {
                                xpdlPath = uri.toFileString();
                            } else {
                                xpdlPath = uri.toPlatformString(true);
                            }
                        }
                        if (uri != null) {
                            IFile file =
                                    ResourcesPlugin.getWorkspace().getRoot()
                                            .getFile(new Path(xpdlPath));
                            if (file != null && file.exists()) {
                                WorkingCopy workingCopy =
                                        WorkingCopyUtil.getWorkingCopy(file);
                                if (workingCopy != null) {
                                    EObject rootElement =
                                            workingCopy.getRootElement();
                                    if (rootElement instanceof Package) {
                                        Package xpdlPackage =
                                                (Package) rootElement;
                                        EList<Process> processes =
                                                xpdlPackage.getProcesses();
                                        if (processes != null
                                                && !processes.isEmpty()) {
                                            for (Process process : processes) {
                                                if (process != null) {
                                                    Collection<Activity> activities =
                                                            Xpdl2ModelUtil
                                                                    .getAllActivitiesInProc(process);

                                                    if (activities != null
                                                            && !activities
                                                                    .isEmpty()) {
                                                        for (Activity activity : activities) {
                                                            if (activity != null
                                                                    && ProcessScriptUtil
                                                                            .isActivityWithScriptGrammarOfType(activity,
                                                                                    JsConsts.JAVASCRIPT_GRAMMAR)) {
                                                                referencingScriptContainerCandidates
                                                                        .add(activity);
                                                            }
                                                        }
                                                    }
                                                    EList<Transition> transitions =
                                                            process.getTransitions();
                                                    if (transitions != null
                                                            && !transitions
                                                                    .isEmpty()) {
                                                        for (Transition transition : transitions) {
                                                            if (transition != null
                                                                    && ProcessScriptUtil
                                                                            .isSeqFlowWithScriptType(transition,
                                                                                    JsConsts.JAVASCRIPT_GRAMMAR)) {
                                                                referencingScriptContainerCandidates
                                                                        .add(transition);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return referencingScriptContainerCandidates;
                }
            }
        }
        return Collections.emptyList();
    }

    @SuppressWarnings("restriction")
    public static String replaceReferenceChangesForElement(String strScript,
            String oldName, String newName, EObject element,
            EObject scriptContainer, String scriptType,
            IVarNameResolver varNameResolver) {

        JScriptRefactorParser scriptParser =
                getScriptParser(strScript,
                        scriptContainer,
                        scriptType,
                        varNameResolver,
                        new RefactoringInfo(element, oldName, newName),
                        null);
        replaceRefChangesForElement(strScript, scriptParser);
        return null;
    }

    /**
     * Replaces the element references in the script with the changes.This
     * method additionally takes a cache of Enumerations, in Package Scope.
     * 
     * @param strScript
     * @param oldName
     * @param newName
     * @param element
     * @param scriptContainer
     * @param scriptType
     * @param varNameResolver
     * @param pScopeEnumCache
     * @return
     */
    @SuppressWarnings("restriction")
    public static String replaceReferenceChangesForElement(String strScript,
            String oldName, String newName, EObject element,
            EObject scriptContainer, String scriptType,
            IVarNameResolver varNameResolver,
            PackageScopeEnumCache pScopeEnumCache) {
        
        JScriptRefactorParser scriptParser =
                getScriptParser(strScript,
                        scriptContainer,
                        scriptType,
                        varNameResolver,
                        new RefactoringInfo(element, oldName, newName),
                        pScopeEnumCache);
        return replaceRefChangesForElement(strScript, scriptParser);
    }

    /**
     * Replace the reference in the script for the changed element.
     * 
     * @param strScript
     * @param scriptParser
     */
    private static String replaceRefChangesForElement(String strScript,
            JScriptRefactorParser scriptParser) {
        if (scriptParser != null) {
            List<RefactorResult> refactorResultList =
                    ScriptRefactorParserUtil
                            .getRefactorResultList(scriptParser);
            if (refactorResultList != null && !refactorResultList.isEmpty()) {
                Map<String, RefactoringInfo> old2NewNameMap =
                        new HashMap<String, RefactoringInfo>();
                for (RefactorResult refactorResult : refactorResultList) {
                    if (refactorResult != null
                            && refactorResult.getRefactoringInfos() != null
                            && !refactorResult.getRefactoringInfos().isEmpty()) {
                        for (RefactoringInfo refactoringInfo : refactorResult
                                .getRefactoringInfos()) {
                            if (refactoringInfo != null
                                    && refactoringInfo.getOldValue() != null
                                    && refactoringInfo.getNewValue() != null) {
                                old2NewNameMap.put(refactoringInfo
                                        .getOldValue(), refactoringInfo);
                            }
                        }
                    }
                }
                if (!old2NewNameMap.isEmpty()) {
                    return ScriptRefactorParserUtil
                            .replaceDataRefByName(strScript, old2NewNameMap);
                }
            }
            ISymbolTable symbolTable = scriptParser.getSymbolTable();
            if (symbolTable instanceof SymbolTable) {
                ((SymbolTable) symbolTable).dispose();
            }
            symbolTable = null;
            scriptParser = null;
        }
        return null;
    }

    
    
    private static JScriptRefactorParser getScriptParser(String strScript,
            EObject scriptContainer, String scriptType,
            IVarNameResolver varNameResolver, RefactoringInfo refactoringInfo,
            PackageScopeEnumCache packageScopeEnumCache) {
        List<String> destinationList =
                getProcessDestinationList(Xpdl2ModelUtil
                        .getProcess(scriptContainer));

        // TODO This should be contributed
        IRefactoringStrategy refactoringStrategy =
                new N2JScriptRefactoringStrategy();
        refactoringStrategy.setDestinationName("n2pe1.x"); //$NON-NLS-1$
        refactoringStrategy.setScriptType(scriptType);

        List<IRefactoringStrategy> refactoringStrategyList =
                Collections.singletonList(refactoringStrategy);

        ISymbolTable symbolTable = new SymbolTable();
        Map<String, IScriptRelevantData> scriptRelevantDataMap =
                new HashMap<String, IScriptRelevantData>();
        try {

            // XPD-4936: Performance Improvement for scripts, using cache for
            // Enumerations in process Package Scope.
            List<AbstractScriptRelevantDataProvider> dataProviders =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getScriptRelevantDataProviders(destinationList,
                                    scriptType,
                                    JsConsts.JAVASCRIPT_GRAMMAR,
                                    scriptContainer,
                                    JsConsts.JSCRIPT_DESTINATION);
            List<IScriptRelevantData> scriptRelevantDataList =
                    new LinkedList<IScriptRelevantData>();

            for (AbstractScriptRelevantDataProvider dataProvider : dataProviders) {
                // ONLY use cache which should be initialised by now, no need to
                // create here.
                if (packageScopeEnumCache != null) {
                    dataProvider
                            .setCustomPropertyClass(PackageScopeEnumCache.class,
                                    packageScopeEnumCache);
                }
                scriptRelevantDataList.addAll(dataProvider
                        .getScriptRelevantDataList());
            }
            if (scriptRelevantDataList != null
                    && !scriptRelevantDataList.isEmpty()) {
                for (IScriptRelevantData scriptRelevantData : scriptRelevantDataList) {
                    if (scriptRelevantData != null) {
                        scriptRelevantDataMap.put(scriptRelevantData.getName(),
                                scriptRelevantData);
                    }
                }
            }
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        symbolTable.setScriptRelevantDataTypeMap(scriptRelevantDataMap);

        List<RefactoringInfo> refactoringInfoList =
                new ArrayList<RefactoringInfo>();

        // Parsing the script and get the list of variable in use
        return ScriptRefactorParserUtil.refactorScript(strScript,
                destinationList,
                refactoringStrategyList,
                symbolTable,
                varNameResolver,
                refactoringInfoList,
                scriptType,
                refactoringInfo);
    }

    private static List<String> getProcessDestinationList(Process process) {
        List<String> processDestList =
                new ArrayList<String>(
                        DestinationUtil
                                .getEnabledValidationDestinations(process));

        if (processDestList == null) {
            processDestList = new ArrayList<String>();
            processDestList.add(JsConsts.JSCRIPT_DESTINATION);
        }
        return processDestList;
    }
}
