/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.refactor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;

import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.xpdExtension.CorrelationDataMappings;
import com.tibco.xpd.xpdExtension.SignalData;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Factory for change in payload data mapping references.
 * 
 * @author sajain
 * @since May 18, 2015
 */
public class PayloadDataMappingReferenceChangeFactory {

    /**
     * Activity referencing payload data.
     */
    private Activity activity;

    /**
     * The reference change.
     */
    private List<PayloadDataMappingReferenceChange> changes;

    /**
     * Rename arguments.
     */
    private RenameArguments args = null;

    /**
     * Element being refactored.
     */
    private EObject element = null;

    /**
     * Factory for GSD reference change in scripts.
     * 
     * @param activity
     * @param args
     * @param element
     */
    public PayloadDataMappingReferenceChangeFactory(Activity activity,
            RenameArguments args, EObject element) {

        this.activity = activity;
        this.args = args;
        this.element = element;
    }

    /**
     * Get changes in payload data mapping references.
     * 
     * @return changes in payload data mapping references.
     */
    public List<PayloadDataMappingReferenceChange> getChanges() {

        if (changes == null && activity != null && activity.getEvent() != null) {

            changes = new ArrayList<PayloadDataMappingReferenceChange>();

            TransactionalEditingDomain editingDomain =
                    XpdResourcesPlugin.getDefault().getEditingDomain();

            TriggerResultSignal trs = null;

            if (activity.getEvent() instanceof IntermediateEvent) {

                IntermediateEvent intermediateEvent =
                        (IntermediateEvent) (activity.getEvent());

                trs = intermediateEvent.getTriggerResultSignal();

            } else if (activity.getEvent() instanceof StartEvent) {

                StartEvent startEvent = (StartEvent) (activity.getEvent());

                trs = startEvent.getTriggerResultSignal();

            }

            if (trs != null) {

                Object sdObj =
                        Xpdl2ModelUtil.getOtherElement(trs,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_SignalData());

                if (sdObj instanceof SignalData) {

                    SignalData sd = (SignalData) sdObj;

                    List<DataMapping> allDataMappings =
                            new ArrayList<DataMapping>();

                    CorrelationDataMappings cdm = sd.getCorrelationMappings();

                    if (cdm != null) {
                        allDataMappings.addAll(cdm.getDataMappings());
                    }

                    allDataMappings.addAll(sd.getDataMappings());

                    for (DataMapping eachDataMapping : allDataMappings) {

                        if (element instanceof PayloadDataField) {

                            PayloadDataField pdf = (PayloadDataField) element;

                            if (eachDataMapping.getFormal()
                                    .equals(pdf.getName())) {

                                changes.add(new PayloadDataMappingReferenceChange(
                                        eachDataMapping.getFormal(), args,
                                        element, eachDataMapping, editingDomain));
                            }

                        }

                    }

                }

            }

        }

        return changes;
    }
}
