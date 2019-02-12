/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.pe.datamapper.refactor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.implementer.nativeservices.script.AbstractMappingJavaScriptProcessFieldResolver;
import com.tibco.xpd.n2.pe.internal.Messages;
import com.tibco.xpd.process.datamapper.refactor.ProcessDataMapperDataReferenceUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Process data field reference resolver for user defined mapping scripts in
 * Data Mapper scenarios whose source content is provided by a process data
 * contributor.
 * 
 * @author aallway
 * @since 15 Jun 2015
 */
public class ProcessDataMapperScriptFieldRefResolver
        extends AbstractMappingJavaScriptProcessFieldResolver {

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getMappingInReferenceContextLabel(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected String getMappingInReferenceContextLabel(Activity activity) {
        return Messages.ProcessDataMapperScriptFieldRefResolver_ProcessDataMapperInMappingScriptRefContext_label;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getMappingOutReferenceContextLabel(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected String getMappingOutReferenceContextLabel(Activity activity) {
        return Messages.ProcessDataMapperScriptFieldRefResolver_ProcessDataMapperOutMappingScriptRefContext_label;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#isInterestingActivity(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected boolean isInterestingActivity(Activity activity) {
        if (!getProcessDataSourceScriptDataMapperElements(activity).isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getActivityScriptInformationList(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected List<ScriptInformation> getActivityScriptInformationList(
            Activity activity) {
        List<ScriptInformation> scriptInfoElements =
                new ArrayList<ScriptInformation>();

        Collection<ScriptDataMapper> sdms =
                getProcessDataSourceScriptDataMapperElements(activity);

        for (ScriptDataMapper sdm : sdms) {
            /*
             * Check mapped scripts first, we are only interested in scripts for
             * mappings whose source data is contributed by the known process
             * data contributors to data mapper.
             * 
             * This var keeps track of whether we could ascertain this fact (as
             * we will only process unmapped scripts IF we are sure that source
             * data contributor is process data)
             */
            boolean isProcessDataSourceContributor = false;

            for (DataMapping dataMapping : sdm.getDataMappings()) {
                isProcessDataSourceContributor = true;

                /* Add script info if there is one. */
                ScriptInformation scriptInfo =
                        (ScriptInformation) Xpdl2ModelUtil.getOtherElement(
                                dataMapping,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_Script());

                if (scriptInfo != null) {
                    scriptInfoElements.add(scriptInfo);
                }
            }

            /*
             * Now deal with unmapped scripts BUT only IF we are sure that
             * source data contributor is process data)
             */
            if (isProcessDataSourceContributor) {
                EList<ScriptInformation> unmappedScripts =
                        sdm.getUnmappedScripts();
                if (!unmappedScripts.isEmpty()) {
                    scriptInfoElements.addAll(unmappedScripts);
                }
            }
        }

        return scriptInfoElements;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getDataReferenceContext(com.tibco.xpd.xpdl2.Activity,
     *      com.tibco.xpd.xpdExtension.ScriptInformation)
     * 
     * @param activity
     * @param scriptInformation
     * @return
     */
    @Override
    protected DataReferenceContext getDataReferenceContext(Activity activity,
            ScriptInformation scriptInformation) {

        /*
         * Any scriptinfo we provide will be under a script data mapper element
         * so we use that to get the correct context.
         */

        ScriptDataMapper scriptDataMapper = (ScriptDataMapper) Xpdl2ModelUtil
                .getAncestor(scriptInformation, ScriptDataMapper.class);
        if (scriptDataMapper != null) {
            DataReferenceContext dataReferenceContext =
                    ProcessDataMapperDataReferenceUtil
                            .getDataReferenceContext(scriptDataMapper, true);

            if (dataReferenceContext != null) {
                DataReferenceContext mapScriptContext =
                        new DataReferenceContext(
                                dataReferenceContext.getContextId(),
                                dataReferenceContext.getLabel() + " (" //$NON-NLS-1$
                                        + Messages.ProcessDataMapperScriptFieldRefResolver_MappingScriptLabel
                                        + ")", //$NON-NLS-1$
                                dataReferenceContext.getSortingKey());

                return mapScriptContext;
            }
        }

        return super.getDataReferenceContext(activity, scriptInformation);
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#shouldHandleDataMappingSrcTgt(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected boolean shouldHandleDataMappingSrcTgt(Activity activity) {
        /* We deal with dataMapping src/target ourselves. */
        return false;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getTaskName()
     * 
     * @return
     */
    @Override
    protected String getTaskName() {
        return ProcessScriptContextConstants.DATA_MAPPER_PE_MAPPING_SCRIPTS;

    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getScript(com.tibco.xpd.xpdExtension.ScriptInformation)
     * 
     * @param scriptInformation
     * @return
     */
    @Override
    protected String getScript(ScriptInformation scriptInformation) {
        if (scriptInformation.eContainer() instanceof ScriptDataMapper) {
            return scriptInformation.getExpression().getText();
        }
        return super.getScript(scriptInformation);
    }

    /**
     * @param activity
     * @return A list of ScriptDataMapper elements under the given activity that
     *         have process data content on source side of mappings
     */
    private Collection<ScriptDataMapper> getProcessDataSourceScriptDataMapperElements(
            Activity activity) {

        List<ScriptDataMapper> sdms = new ArrayList<ScriptDataMapper>();

        /*
         * Find any ScriptDataMapper elements, these are what we are interested
         * in
         */
        TreeIterator<EObject> eAllContents = activity.eAllContents();

        while (eAllContents.hasNext()) {
            EObject eo = eAllContents.next();

            if (eo instanceof ScriptDataMapper) {
                ScriptDataMapper scriptDataMapper = (ScriptDataMapper) eo;

                /*
                 * Check if source of mapping content is process data related
                 * (we're only interested in the source as we're just doing
                 * scripts).
                 */
                DataReferenceContext dataReferenceContext =
                        ProcessDataMapperDataReferenceUtil
                                .getDataReferenceContext(scriptDataMapper,
                                        true);

                if (dataReferenceContext != null) {
                    sdms.add(scriptDataMapper);
                }
            }
        }

        return sdms;
    }

}
