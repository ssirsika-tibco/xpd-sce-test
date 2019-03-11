/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.core.exception;

import com.tibco.xpd.rasc.core.RascContributor;

/**
 * Wraps exceptions raised by RascContributors.
 *
 * @author pwatson
 * @since 26 Feb 2019
 */
public class RascContributionException extends RascGenerationException {
    private static final long serialVersionUID = 4998305131607437419L;

    private static final String MESSAGE =
            "RASC Contributor '%1$s' raised the exception: %2$s"; //$NON-NLS-1$

    /**
     * Wraps the given exception, with a message containing the contributor's
     * ID.
     * 
     * @param aContributor
     *            the RascContributor that raised the exception.
     * @param aCause
     *            the exception raised by the identified RascContributor.
     */
    public RascContributionException(RascContributor aContributor,
            Throwable aCause) {
        super(String.format(RascContributionException.MESSAGE,
                aContributor.getClass().getSimpleName(),
                aCause.getMessage()), aCause);
    }
}
