/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.process.js.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.ReservedWords;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.process.js.model.util.ProcessUtil;
import com.tibco.xpd.processscriptlibrary.resource.indexer.ProcessScriptLibraryIndexProvider.IndexType;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.ITypeResolver;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.model.internal.client.ITypeResolution;
import com.tibco.xpd.script.model.internal.client.ITypeResolverProvider;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;
import com.tibco.xpd.script.ui.internal.AbstractScriptRelevantDataProvider;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Default implementation for javascript of the Abstract class
 * {@link AbstractScriptRelevantDataProvider}.
 * 
 * @author mtorres
 */
public class DefaultJavaScriptRelevantDataProvider extends
        AbstractScriptRelevantDataProvider {

    /**
     * At this level wraps all of the data provided by
     * {@link #getAssociatedProcessRelevantData()} in a new object "data" (as
     * per ACE requirements).
     * 
     * If a sub-class is handling process data content then it should ONLY
     * overwrite {@link #getAssociatedProcessRelevantData()} NOT
     * {@link #getScriptRelevantDataList()} here
     * 
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptRelevantDataProvider#getScriptRelevantDataList()
     *
     * @return
     */
    @Override
    public List<IScriptRelevantData> getScriptRelevantDataList() {
        List<ProcessRelevantData> processDataList =
                getAssociatedProcessRelevantData();

		List<IScriptRelevantData> results = new ArrayList<>();
        if (!processDataList.isEmpty()) {
            /*
             * Sid ACE-1317: Wrap the process data to be associated with this script context in a special "data" object.
             */
			results.add(AceScriptProcessDataWrapperFactory.getDefault()
					.createProcessDataWrapper(getDataWrapperObjectName(), processDataList));
        }

		IndexType indexType = getTargetProcessScriptLibraryIndexType();
		// ACE-7400 : Add 'bpmScript' specific relevant data.
		results.add(BpmScriptWrapperFactory.getDefault()
				.createBpmScriptWrapper(ReservedWords.PROCESS_SCRIPT_LIBRARY_WRAPPER_NAME, indexType));

		return results;
    }

	/**
	 * Return the {@link IndexType} for process script library used as target reference. Child classes can override to
	 * provide any specific {@link IndexType}.
	 * 
	 * @return {@link IndexType}
	 */
	protected IndexType getTargetProcessScriptLibraryIndexType()
	{
		/*
		 * Sid ACE-8291 If we follow the example of inclusion of bpm.process or bpm.workManager, then the basic process
		 * scripts (i.e script task, initiate, schedule etc NOT OpenUserTask etc) actually have bpm.process. Only
		 * Open/Close user task have bpm.workManager, so we should follow suit here and always return 'include
		 * process/any-target bpmScripts functions.
		 * 
		 * (A subclass CdsWorkManagerJavaScriptRelevantDataProvider deals with Open/Close etc user tasks scripts
		 */
		return IndexType.PSL_PE_PARAM_REF;
	}

    /**
     * Process data is wrapped in a data field descriptor object called "data". To use a different set of data and / or
     * put it in a different wrapper object then you can override his method and
     * {@link #getAssociatedProcessRelevantData()}
     * 
     * @return The process data wrapper object name (default is {@value #PROCESS_DATA_WRAPPER_OBJECT_NAME})
     */
    protected String getDataWrapperObjectName() {
        return ReservedWords.PROCESS_DATA_WRAPPER_OBJECT_NAME;
    }

    /**
     * Get the list of data that should be available for the current activity.
     * 
     * @return The lit of process data to be converted to script relevant data.
     */
    protected List<ProcessRelevantData> getAssociatedProcessRelevantData() {
        List<ProcessRelevantData> processDataList =
                new ArrayList<ProcessRelevantData>();
        Activity activity = getActivity();
        if (activity != null) {
            processDataList =
                    ProcessInterfaceUtil
                            .getAssociatedProcessRelevantDataForActivity(activity);
            /*
             * XPD-2142: Data Fields must not be shown in user defined scripts
             * content assist for api activities
             */
            if (ReplyActivityUtil.isReplyActivity(activity)) {
                boolean isSendOneWayRequest =
                        Xpdl2ModelUtil.isSendOneWayRequest(activity);
                if (!isSendOneWayRequest) {
                    List<ProcessRelevantData> prdList =
                            new ArrayList<ProcessRelevantData>();
                    for (Iterator iterator = processDataList.iterator(); iterator
                            .hasNext();) {
                        ProcessRelevantData processRelevantData =
                                (ProcessRelevantData) iterator.next();
                        if (processRelevantData instanceof FormalParameter) {
                            prdList.add(processRelevantData);
                        }
                    }
                    processDataList = prdList;
                }
            }

        } else if (getProcess() != null) {
            // Check if we are in scope of an embedded sub-process actvity.
            Activity embSubProcAct = null;

            EObject dataCont = getEObject();
            if (dataCont != null) {
                while (dataCont != null) {
                    if (dataCont instanceof ActivitySet) {
                        embSubProcAct =
                                Xpdl2ModelUtil
                                        .getEmbSubProcActivityForActSet(getProcess(),
                                                ((ActivitySet) dataCont)
                                                        .getId());
                        break;
                    }
                    dataCont = dataCont.eContainer();
                }
            }
            if (embSubProcAct != null) {
                processDataList.addAll(ProcessInterfaceUtil
                        .getAllAvailableRelevantDataForActivity(embSubProcAct));
            } else {
                processDataList.addAll(ProcessDataUtil
                        .getProcessRelevantData(getProcess()));
            }
        } else if (getProcessInterface() != null) {
            processDataList.addAll(ProcessDataUtil
                    .getProcessRelevantData(getProcessInterface()));
        } else if (getPackage() != null) {
            processDataList.addAll(getPackage().getDataFields());
        }
        return processDataList;
    }

    /*
     * Sid ACE-1317 Removed getComplexScriptRelevantDataList() as it was
     * redundant.
     */

    protected List<IScriptRelevantData> convertToScriptRelevantData(
            List<ProcessRelevantData> processDataList) {
        return ProcessUtil
                .convertToScriptRelevantData(processDataList,
                        getProject(),
                        readContributedDefinitionReaders(getProcessDestinationList(getProcess())));
    }

    protected Package getPackage() {
        if (getEObject() != null) {
            return Xpdl2ModelUtil.getPackage(getEObject());
        }
        return null;
    }

    protected Process getProcess() {
        if (getEObject() != null) {
            return Xpdl2ModelUtil.getProcess(getEObject());
        }
        return null;
    }

    protected ProcessInterface getProcessInterface() {
        if (getEObject() != null) {
            return ProcessInterfaceUtil.getProcessInterface(getEObject());
        }
        return null;
    }

    protected Activity getActivity() {
        if (getEObject() != null) {
            return Xpdl2ModelUtil.getParentActivity(getEObject());
        }
        return null;
    }

    protected EObject getEObject() {
        if (getContext() instanceof EObject) {
            return (EObject) getContext();
        }
        return null;
    }

    protected IProject getProject() {
        if (getEObject() != null) {
            return WorkingCopyUtil.getProjectFor(getEObject());
        }
        return null;
    }

    protected List<String> getProcessDestinationList(Process process) {
        List<String> processDestList =
                new ArrayList<String>(
                        DestinationUtil
                                .getEnabledValidationDestinations(process));

        if (processDestList == null || processDestList.isEmpty()) {
            processDestList = new ArrayList<String>();
            processDestList.add(ProcessJsConsts.JSCRIPT_DESTINATION);
        }
        return processDestList;
    }

    protected List<JsClassDefinitionReader> readContributedDefinitionReaders(
            List<String> processDestList) {
        List<JsClassDefinitionReader> jsClassProvider = Collections.EMPTY_LIST;
        try {
            jsClassProvider =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getJsClassDefinitionReader(processDestList,
                                    getScriptType(),
                                    ProcessJsConsts.JAVASCRIPT_GRAMMAR,
                                    ProcessJsConsts.JSCRIPT_DESTINATION);
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return jsClassProvider;
    }

    protected boolean isMappedScript() {
        if (getContext() instanceof DataMapping) {
            return true;
        } else if (getContext() instanceof EObject) {
            EObject context = (EObject) getContext();
            EObject contextParent = context.eContainer();
            while (contextParent != null) {
                if (contextParent instanceof DataMapping) {
                    return true;
                } else {
                    contextParent = contextParent.eContainer();
                }
            }
        }
        return false;
    }

    protected boolean isInputScript() {
        if (getContext() instanceof DataMapping) {
            DataMapping dm = (DataMapping) getContext();
            if (dm.getDirection() != null
                    && dm.getDirection().equals(DirectionType.IN_LITERAL)) {
                return true;
            }
        } else if (getContext() instanceof ScriptInformation) {
            ScriptInformation si = (ScriptInformation) getContext();
            if (si.getDirection() != null
                    && si.getDirection().equals(DirectionType.IN_LITERAL)) {
                return true;
            }
        }
        return false;
    }

    public List<ITypeResolver> getTypeResolvers() {
        List<ITypeResolver> typeResolvers = new ArrayList<ITypeResolver>();
        if (readContributedDefinitionReaders(getProcessDestinationList(getProcess())) != null
                && !readContributedDefinitionReaders(getProcessDestinationList(getProcess()))
                        .isEmpty()) {
            Set<ITypeResolverProvider> typeResolverProviders =
                    JScriptUtils
                            .getTypeResolverProviders(readContributedDefinitionReaders(getProcessDestinationList(getProcess())));
            if (typeResolverProviders != null
                    && !typeResolverProviders.isEmpty()) {
                typeResolvers
                        .addAll(JScriptUtils.getTypeResolvers(new ArrayList(
                                typeResolverProviders)));
            }
        }
        return typeResolvers;
    }

    public void addResolutionTypes(IScriptRelevantData type,
            boolean isMultiple, IScriptRelevantData genericContext) {
        if (type instanceof ITypeResolution) {
            List<IScriptRelevantData> resolveJavaScriptStringTypes =
                    JScriptUtils.resolveType(type,
                            getTypeResolvers(),
                            isMultiple,
                            genericContext);
            if (resolveJavaScriptStringTypes != null
                    && !resolveJavaScriptStringTypes.isEmpty()) {
                ((ITypeResolution) type)
                        .setTypesResolution(resolveJavaScriptStringTypes);
            }
        }
    }

    /*
     * Sid ACE-1317 Removed isInputMessage() is StartEventMessage() as they were
     * redundant.
     */
}
