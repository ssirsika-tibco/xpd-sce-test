/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.bom.modeler.custom.terminalstates;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;

/**
 * Utility class for getting and setting terminal state properties on a case
 * attribute.
 *
 * @author nwilson
 * @since 10 Apr 2019
 */
public class TerminalStateProperties {

    public static final String BOM_TERMINAL_STATES = "terminalStates"; //$NON-NLS-1$

    /**
     * Gets a list of terminal states for a case attribute property.
     * 
     * @param property
     *            The case attribute property.
     * @return The configured terminal states.
     */
    @SuppressWarnings("unchecked")
    public EList<EnumerationLiteral> getTerminalStates(Property property) {
        EList<EnumerationLiteral> states = null;
        Stereotype stereotype = BOMGlobalDataUtils.getCaseStateStereotype();
        EObject stereotypeApplication =
                property.getStereotypeApplication(stereotype);
        if (stereotypeApplication != null
                && isValueSet(stereotypeApplication, BOM_TERMINAL_STATES)) {
            Object value = property.getValue(stereotype, BOM_TERMINAL_STATES);
            if (value instanceof EList) {
                states = (EList<EnumerationLiteral>) value;
            }
        }
        return states;
    }

    /**
     * Get a command that will add a terminal state to a case attribute.
     * 
     * @param domain
     *            The editing domain.
     * @param property
     *            The case attribute property.
     * @param state
     *            The state to add.
     * @return The command to add the value.
     */
    public Command getAddTerminalStateCommand(EditingDomain domain,
            Property property, EnumerationLiteral state) {
        return new RecordingCommand((TransactionalEditingDomain) domain) {
            @Override
            protected void doExecute() {
                EList<EnumerationLiteral> states = getTerminalStates(property);
                if (states == null) {
                    Stereotype stereotype =
                            BOMGlobalDataUtils.getCaseStateStereotype();
                    states = new BasicEList<>();
                    states.add(state);
                    property.setValue(stereotype, BOM_TERMINAL_STATES, states);

                } else {
                    states.add(state);
                }
            }
        };
    }

    /**
     * Get a command that will remove a terminal state from a case attribute.
     * 
     * @param domain
     *            The editing domain.
     * @param property
     *            The case attribute property.
     * @param state
     *            The state to add.
     * @return The command to add the value.
     */
    public Command getRemoveTerminalStateCommand(EditingDomain domain,
            Property property, EnumerationLiteral state) {
        return new RecordingCommand((TransactionalEditingDomain) domain) {
            @Override
            protected void doExecute() {
                EList<EnumerationLiteral> states = getTerminalStates(property);
                if (states != null) {
                    states.remove(state);
                }
            }
        };
    }

    /**
     * Check if the given property is set in the Stereotype Application.
     * 
     * @param stereotypeApplication
     * @param property
     * @return <code>false</code> if the value is not set, <code>true</code>
     *         otherwise.
     */
    private static boolean isValueSet(EObject stereotypeApplication,
            String property) {
        if (stereotypeApplication != null) {
            EStructuralFeature feature = stereotypeApplication.eClass()
                    .getEStructuralFeature(property);
            if (feature != null) {
                return stereotypeApplication.eIsSet(feature);
            }
        }
        return true;
    }
}
