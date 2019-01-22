/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.script;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.script.ui.internal.BaseScriptSection;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Abstract Script Property Section which has implementation for retrieving the
 * enabled destinations for the XPDL process
 * 
 * @author rsomayaj
 * 
 */
public abstract class BaseProcessScriptSection extends BaseScriptSection {

    /**
     * 
     */
    public static final String BPMN1_VALIDATION_DESTINATION = "bpmn1"; //$NON-NLS-1$

    /**
     * @param inputType
     */
    public BaseProcessScriptSection(EClass inputType) {
        super(inputType);
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.BaseScriptSection#getEnabledDestinations(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
     */
    @Override
    public Collection<String> getEnabledDestinations(EObject input) {
        Collection<String> enabledDestinations = Collections.EMPTY_LIST;
        com.tibco.xpd.xpdl2.Process process = Xpdl2ModelUtil.getProcess(input);
        if (process == null) {
            /**
             * if a data field is created at package level then it would not
             * have a process associated with it which results in process=null
             * and hence the data field script drop down will not have any
             * grammar added to it . hence we check if the parent of the data
             * field is package and add grammar to the data field script drop
             * down
             */
            EObject object = getInput();
            EObject parent = null;
            if (object instanceof ProcessRelevantData
                    || object instanceof Participant) {
                parent = Xpdl2ModelUtil.getPackage(object);
                if (parent instanceof com.tibco.xpd.xpdl2.Package) {
                    Set<String> enabledValidationDestinations =
                            DestinationUtil
                                    .getEnabledValidationDestinations((com.tibco.xpd.xpdl2.Package) parent);
                    enabledValidationDestinations
                            .add(BPMN1_VALIDATION_DESTINATION);
                    return enabledValidationDestinations;
                }

            }
            if (null == parent) {
                return Collections.singleton(BPMN1_VALIDATION_DESTINATION);
            }
        } else {
            enabledDestinations =
                    DestinationUtil.getEnabledValidationDestinations(process);

            // BPMN1 destination is not added to the list of destinations. By
            // default, this should be added.
            if (!enabledDestinations.contains(BPMN1_VALIDATION_DESTINATION)) {
                enabledDestinations.add(BPMN1_VALIDATION_DESTINATION);
            }
        }
        return enabledDestinations;
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.BaseScriptSection#getMarkerContainerId()
     * 
     * @return
     */
    @Override
    protected String getMarkerContainerId() {
        if (getEditorInput() instanceof UniqueIdElement) {
            return ((UniqueIdElement) getEditorInput()).getId();
        }
        return super.getMarkerContainerId();
    }
    
    @Override
    public abstract String getCurrentSetScriptGrammarId();
}
