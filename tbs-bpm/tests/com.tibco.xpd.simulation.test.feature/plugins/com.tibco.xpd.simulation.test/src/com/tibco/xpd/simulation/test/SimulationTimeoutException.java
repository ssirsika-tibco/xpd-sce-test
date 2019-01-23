/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.simulation.test;

/**
 * Thrown if the running simulation reached its timeout.
 * 
 * @author Jan Arciuchiewicz
 */
public class SimulationTimeoutException extends RuntimeException {

    public SimulationTimeoutException() {
    }

    public SimulationTimeoutException(String message) {
        super(message);
    }

    public SimulationTimeoutException(Throwable cause) {
        super(cause);
    }

    public SimulationTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

}
