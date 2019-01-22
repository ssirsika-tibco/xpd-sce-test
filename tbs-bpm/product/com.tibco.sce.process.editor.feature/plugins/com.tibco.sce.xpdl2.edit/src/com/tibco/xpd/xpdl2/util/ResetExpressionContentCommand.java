/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.xpdl2.util;

import java.util.Collections;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.xpdl2.Expression;

/**
 * Command that will clear the Mixed content of a given expression and replace
 * with the given feature map entry.
 * 
 * @author aallway
 * @since 7 May 2015
 */
public class ResetExpressionContentCommand extends RecordingCommand {

    private Expression expression;

    private EStructuralFeature feature;

    private Object value;

    /**
     * * Construct command to reset the expression's content to the given
     * feature map entry.
     * 
     * @param domain
     * @param label
     * @param expression
     * @param feature
     * @param value
     */
    public ResetExpressionContentCommand(TransactionalEditingDomain domain,
            String label, Expression expression, EStructuralFeature feature,
            Object value) {
        super(domain);
        this.expression = expression;

        this.feature = feature;
        this.value = value;
    }

    /**
     * Construct command to reset the expression's textual content to the given
     * feature map entry.
     * 
     * @param domain
     * @param label
     * @param expression
     * @param value
     */
    public ResetExpressionContentCommand(TransactionalEditingDomain domain,
            String label, Expression expression, String value) {
        super(domain);
        this.expression = expression;

        this.feature = null;
        this.value = value;
    }

    /**
     * @see org.eclipse.emf.transaction.RecordingCommand#doExecute()
     * 
     */
    @Override
    protected void doExecute() {
        expression.getMixed().clear();

        if (feature == null && value instanceof String) {
            FeatureMapUtil.addText(expression.getMixed(), (String) value);

        } else {
            expression.getMixed()
                    .set(feature, Collections.singletonList(value));
        }
    }

}
