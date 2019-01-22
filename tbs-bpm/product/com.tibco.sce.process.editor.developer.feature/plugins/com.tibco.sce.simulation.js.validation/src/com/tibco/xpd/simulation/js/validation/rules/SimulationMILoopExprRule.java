/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.simulation.js.validation.rules;

import com.tibco.xpd.js.validation.rules.AbstractMILoopExprRule;

/**
 * This class only provides the error and warning IDs whereas the actual
 * validation is abstracted out in super classes, which enables reusing the rule
 * for different destinations. Additional validation can be performed in this
 * class, if required, by implementing
 * {@link #performAdditionalValidation(com.tibco.xpd.xpdl2.Expression, com.tibco.xpd.xpdl2.UniqueIdElement)}
 * 
 * 
 * @author agondal
 * @since 17 Sep 2013
 */
public class SimulationMILoopExprRule extends AbstractMILoopExprRule {
    /** The issue id. */
    private static final String ERROR_ID = "simulation.miLoopExprScript"; //$NON-NLS-1$                                  

    private static final String WARNING_ID =
            "simulation.warning.miLoopExprScript"; //$NON-NLS-1$

    @Override
    protected String getErrorId() {
        return ERROR_ID;
    }

    @Override
    protected String getWarningId() {
        return WARNING_ID;
    }

}
