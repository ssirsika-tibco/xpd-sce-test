/*
 * Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved.
 */

package com.tibco.xpd.customer.api.iprocess.amxbpm.conversion;

/**
 * Information class detailing individual contributions to the
 * <code>com.tibco.xpd.customer.api.iProcessBpmConversion</code> extension
 * point.
 * <p>
 * This is provided to allow customer extension developers to see all
 * contributions' OrderingPriority, ConverterId and ConversionDescription
 * values. This my be useful when a customer extension developer wishes to
 * insert conversions before or after particular base, generic Business
 * Studio-provided conversion contributions.
 * </p>
 * <p>
 * Only the TIBCO-owned data and classes used or returned directly or indirectly
 * by the above class are considered public API. <br/>
 * Any other data, class or extension point contained with TIBCO-owned features
 * and plug-ins outside of the com.tibco.xpd.customer.api feature and plug-in
 * must be considered internal and private to TIBCO and are subject to change
 * without notice.
 * </p>
 * 
 * <p>
 * <b>Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved.</b>
 * </p>
 * 
 * @author TIBCO Software Inc
 * @since TIBCO Business Studio BPM Edition v3.8
 */
public class ConversionContributionInfo {
    private int orderingPriority;

    private String converterId;

    private String conversionDescription;

    ConversionContributionInfo(String converterId, int orderingPriority,
            String converterDescription) {
        this.converterId = converterId;
        this.orderingPriority = orderingPriority;
        this.conversionDescription = converterDescription;
    }

    /**
     * @return the orderingPriority of the contribution
     */
    public int getOrderingPriority() {
        return orderingPriority;
    }

    /**
     * @return the converterId of the contribution
     */
    public String getConverterId() {
        return converterId;
    }

    /**
     * @return the converterDescription of the contribution
     */
    public String getConverterDescription() {
        return conversionDescription;
    }

}