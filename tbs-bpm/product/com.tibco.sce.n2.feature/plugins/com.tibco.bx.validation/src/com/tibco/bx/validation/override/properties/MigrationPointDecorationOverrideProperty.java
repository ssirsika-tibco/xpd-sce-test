/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.bx.validation.override.properties;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.n2.resources.util.MigrationPointUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.advanced.AbstractValidationIssueOverrideProperty;
import com.tibco.xpd.xpdExtension.ValidationIssueOverrideType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * Advanced property to all suppression of process flow Migrtion Point
 * Decoration
 * <p>
 * Even though this is the validation issue override framework, it will work
 * equally well for supressing parts of UI related to it.
 * 
 * 
 * @author aallway
 * @since 25 May 2013
 */
public class MigrationPointDecorationOverrideProperty extends
        AbstractValidationIssueOverrideProperty {

    private Object[] migrationDecorationOverrideTypes = new Object[] {
            AbstractValidationIssueOverrideProperty.VALIDATION_OVERRIDE_UNSET,
            ValidationIssueOverrideType.SUPPRESS_UNTIL_MANUAL_REACTIVATION };

    private String[] migrationDecorationOverrideLabels = new String[] {
            Messages.MigrationPointDecorationOverrideProperty_Show_label,
            Messages.MigrationPointDecorationOverrideProperty_Hide_label };

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.advanced.AbstractValidationIssueOverrideProperty#getValidationIssueId()
     * 
     * @return
     */
    @Override
    protected String getValidationIssueId() {
        return MigrationPointUtil.BX_MIGRATION_POINT_DECORATION_SUPPRESSOR_ID;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.advanced.AbstractValidationIssueOverrideProperty#getDropDownItemLabels()
     * 
     * @return
     */
    @Override
    protected String[] getDropDownItemLabels() {
        return migrationDecorationOverrideLabels;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.advanced.AbstractValidationIssueOverrideProperty#getDropDownItemValues()
     * 
     * @return
     */
    @Override
    protected Object[] getDropDownItemValues() {
        return migrationDecorationOverrideTypes;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty#isApplicable(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
     */
    @Override
    public boolean isApplicable(EObject input) {
        /* Allow setting at process level or at package level. */
        if (input instanceof Process) {
            return MigrationPointUtil.doMigrationPointsApply((Process) input);

        } else if (input instanceof Package) {
            for (Process process : ((Package) input).getProcesses()) {
                if (MigrationPointUtil.doMigrationPointsApply(process)) {
                    return true;
                }
            }
        }

        return false;
    }

}
