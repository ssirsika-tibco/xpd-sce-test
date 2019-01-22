/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.customer.api.iprocess.internal;

import org.eclipse.core.runtime.IConfigurationElement;

import com.tibco.xpd.customer.api.internal.Messages;
import com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution;
import com.tibco.xpd.customer.api.plugin.BusinessStudioCustomerApiPlugin;

/**
 * Wrapper for the iProcessBpmConversion extension Converter elements.
 * 
 * @author sajain
 * @since Mar 30, 2014
 */
public class IProcessToBPMConversionExtension {
    private static final String ATTR_ID = "ConverterId"; //$NON-NLS-1$

    private static final String ATTR_DESC = "ConversionDescription";//$NON-NLS-1$

    private static final String ATTR_CLASS = "ConverterClass"; //$NON-NLS-1$

    private static final String ATTR_ORDERING_PRIORITY = "OrderingPriority"; //$NON-NLS-1$

    private final IConfigurationElement element;

    private AbstractIProcessToBPMContribution contributedClass;

    /**
     * Constructor
     * 
     * @param element
     *            Extension configuration element.
     */
    public IProcessToBPMConversionExtension(IConfigurationElement element) {
        this.element = element;

        /*
         * Sid XPD-6230: Moved load of the contributed class to the constructor.
         * The introduction of new lifeCycleListener contributions suggested
         * that both THEY and the original Covnerter contributions should be
         * created ONCE per conversion, so we should create on construction
         * rather than each call to getAbstractIProcessToBPMContribution()
         */
        Object obj = null;

        try {
            obj = element.createExecutableExtension(ATTR_CLASS);
            if (obj instanceof AbstractIProcessToBPMContribution) {
                contributedClass = (AbstractIProcessToBPMContribution) obj;
            } else {
                throw (new Exception(
                        "Contributed class is not instanceof com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution")); //$NON-NLS-1$
            }
        } catch (Exception e) {
            BusinessStudioCustomerApiPlugin
                    .getDefault()
                    .getLogger()
                    .error(e,
                            String.format(Messages.IProcessToBPMConversionExtension_invalidContribution_shortDesc,
                                    element.getContributor().getName()));
        }

    }

    /**
     * Get id of the extension.
     * 
     * @return ID
     */
    public String getId() {
        String id = null;

        if (element != null) {
            id = element.getAttribute(ATTR_ID);
        }

        return id != null ? id : ""; //$NON-NLS-1$
    }

    /**
     * Get description of the extension.
     * 
     * @return Description
     */
    public String getDescription() {
        String desc = null;

        if (element != null) {
            desc = element.getAttribute(ATTR_DESC);
        }

        // return desc, fall back on id if none contributed (just in case */
        return desc != null && !desc.isEmpty() ? desc : getId();
    }

    /**
     * Get the class attribute of the extension
     * 
     * @return <code>AbstractIProcessToBPMContribution</code> object.
     */
    public AbstractIProcessToBPMContribution getAbstractIProcessToBPMContribution() {
        /*
         * Sid XPD-6230: Moved load of the contributed class to the constructor.
         * The introduction of new lifeCycleListener contributions suggested
         * that both THEY and the original Converter contributions should be
         * created ONCE per conversion, so we should create on construction
         * rather than each call to getAbstractIProcessToBPMContribution()
         */
        return contributedClass;
    }

    /**
     * Get Ordering priority of the extension.
     * 
     * @return Ordering priority return -1 on error
     */
    public int getOrderingPriority() {
        String op = null;

        if (element != null) {
            /*
             * Need to trim the ordering priority here as client could
             * mistakenly add a leading/trailing space which is difficult to
             * figure out (and then might keep on wondering why the priority is
             * being set to -1 and why they are getting an exception in the logs
             * :)).
             */
            op = element.getAttribute(ATTR_ORDERING_PRIORITY).trim();
        }

        /*
         * Need to make sure that Ordering priority is an integer.
         */
        if (op.matches("-?\\d+$")) { //$NON-NLS-1$
            return Integer.parseInt(op);
        } else {
            try {
                throw new Exception();
            } catch (Exception e) {
                BusinessStudioCustomerApiPlugin
                        .getDefault()
                        .getLogger()
                        .error(Messages.IProcessToBPMConversionExtension_invalidOrderingPriority_shortDesc);
                e.printStackTrace();
            }
        }
        return (-1);
    }

    @Override
    public String toString() {
        AbstractIProcessToBPMContribution contributionInstance =
                getAbstractIProcessToBPMContribution();

        if (contributionInstance != null) {
            return String
                    .format(Messages.IProcessToBPMConversionExtension_toString_msg,
                            getId(),
                            getDescription(),
                            getClass().toString(),
                            String.valueOf(getOrderingPriority()));
        }

        return super.toString();
    }
}
