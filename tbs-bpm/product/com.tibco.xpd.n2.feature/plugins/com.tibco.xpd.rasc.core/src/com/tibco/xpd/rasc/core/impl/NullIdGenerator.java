/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.core.impl;

import java.math.BigInteger;

import com.tibco.bpm.dt.rasc.RascIdGenerator;

/**
 * As the SCE RASCs do not carry an identifier, this is an implementation of the
 * RascIdGenerator that always returns a <code>null</code> value.
 *
 * @author pwatson
 * @since 1 Mar 2019
 */
final class NullIdGenerator implements RascIdGenerator {
    /**
     * A static instance of the NULL RascIdGenerator.
     */
    public static final RascIdGenerator INSTANCE = new NullIdGenerator();

    private NullIdGenerator() {
    }

    /**
     * Always returns <code>null</code>.
     */
    @Override
    public BigInteger generateId() throws Exception {
        return null;
    }
}
