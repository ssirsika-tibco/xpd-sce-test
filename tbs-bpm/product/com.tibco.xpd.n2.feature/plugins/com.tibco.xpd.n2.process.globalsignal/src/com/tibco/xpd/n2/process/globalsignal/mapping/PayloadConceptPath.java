/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.mapping;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;

/**
 * Wrapper that wraps {@link PayloadDataField} and can construct equivalent
 * PayloadConceptPath (via {@link #PayloadConceptPath(Activity, String)})
 * objects from the parent global signal activity and the payload name.
 * 
 * Sid ACE-118 Changed this class to extend {@link ConceptPath} as global signal
 * payload fields extends the standard XPDL {@link DataField} and therefore
 * things that need {@link ConceptPath} can equally use
 * {@link PayloadConceptPath} (for things like mapping script generation.
 * 
 * @author kthombar
 * @since Feb 13, 2015
 */
public class PayloadConceptPath extends ConceptPath {

    /**
     * the payload field to wrapped.
     */
    private PayloadDataField payLoadData;

    /**
     * 
     * @param payLoadData
     *            the payload field to wrap.
     */
    public PayloadConceptPath(PayloadDataField payLoadData) {
        super(payLoadData, ConceptUtil.getConceptClass(payLoadData));
        this.payLoadData = payLoadData;
    }

    /**
     * 
     * @return the wrapped {@link PayloadDataField}.
     */
    public PayloadDataField getPayloadDataField() {
        return this.payLoadData;
    }

    /**
     * 
     * @return <code>true</code> if the Payload Data Wrapped in the payload
     *         concept path has correlation enabled, else return
     *         <code>false</code>
     */
    public boolean isCorrelationPayloadData() {

        return (payLoadData != null) && payLoadData.isCorrelation();
    }

    /**
     * 
     * @return <code>true</code> if the Payload Data Wrapped in the payload
     *         concept path is mandatory, else return <code>false</code>
     */
    public boolean isMandatoryPayloadData() {

        return (payLoadData != null) && payLoadData.isMandatory();
    }

    /**
     * Sid ACE-1118 Switched constructor to static method (as a constructor
     * would have to have called a super() first which would not have been
     * possible.
     * 
     * @param globalSignalActivity
     *            the parent global signal activity
     * @param payloadName
     *            the payload name.
     */
    public static PayloadConceptPath getConceptPath(Activity globalSignalActivity, String payloadName) {

        if (globalSignalActivity != null && payloadName != null) {

            /*
             * get the referenced global signal
             */
            GlobalSignal referencedGlobalSignal =
                    GlobalSignalUtil
                            .getReferencedGlobalSignal(globalSignalActivity);

            if (referencedGlobalSignal != null) {
                /*
                 * get the payload data fields
                 */
                EList<PayloadDataField> payloadDataFields =
                        referencedGlobalSignal.getPayloadDataFields();

                for (PayloadDataField payloadDataField : payloadDataFields) {

                    if (payloadDataField != null
                            && payloadDataField.getName().equals(payloadName)) {
                        /*
                         * found necessary payload data.
                         */
                        return new PayloadConceptPath(payloadDataField);
                    }
                }
            }
        }
        return null;
    }

    /**
     * 
     * @return the Path of Payload Concept.
     */
    @Override
    public String getPath() {
        /**
         * Convert payload datafield path into a programmatic path string (for
         * use in problem marker). NOT a label
         */
        String path = null;
        if (payLoadData != null) {
            path = payLoadData.getName();
        }

        return path;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * @param obj
     * @return <code>true</code> if the passed and calling
     *         {@link PayloadConceptPath} are equal.
     */
    @Override
    public boolean equals(Object obj) {

        if (obj instanceof PayloadConceptPath) {

            PayloadConceptPath payloadConceptPath = (PayloadConceptPath) obj;

            if (this.getPayloadDataField() != null) {

                if (payloadConceptPath.getPayloadDataField() != null) {

                    DataField currentPayloadDataField =
                            this.getPayloadDataField();
                    DataField otherPayloadDataField =
                            payloadConceptPath.getPayloadDataField();

                    if (currentPayloadDataField != null) {
                        /*
                         * return true if the names of the payload data are
                         * eqeual.
                         */
                        return currentPayloadDataField.getName()
                                .equals(otherPayloadDataField.getName());

                    } else if (otherPayloadDataField == null) {

                        return true;
                    }
                }
            } else {
                if (payloadConceptPath.getPayloadDataField() == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return <code>true</code> if the payload data wrapped in payload concept
     *         path is an array else return <code>false</code>
     */
    @Override
    public boolean isArray() {

        return (payLoadData != null) && payLoadData.isIsArray();
    }
}
