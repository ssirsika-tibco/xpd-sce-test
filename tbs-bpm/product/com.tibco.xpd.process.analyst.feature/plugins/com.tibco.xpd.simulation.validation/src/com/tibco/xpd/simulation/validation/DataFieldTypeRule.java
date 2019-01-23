/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.validation;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.util.XpdlSearchUtil;

/**
 * Check data field types. There are only simple types allowed for simulation.
 * <p>
 * <i>Created: 9 May 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class DataFieldTypeRule extends ProcessValidationRule {

    /** Simulation data issue ID. */
    public static final String REFERENCE_TYPES_NOT_ALLOWED = "sim.referenceTypesNotAllowed"; //$NON-NLS-1$

    public static final String LOOP_IN_DECLARED_TYPES = "sim.loopInDeclaredTypes"; //$NON-NLS-1$
    private DataType DeclaredType;

    /**
     * @param process
     *            The process to check.
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(
     *      com.tibco.xpd.xpdl2.Process)
     */
    @SuppressWarnings("unchecked") 
    @Override
    public void validate(Process process) {
        Package xpdlPackage = process.getPackage();
        List packageDataFields = xpdlPackage.getDataFields();
        List processDataFields = process.getDataFields();
        for (Object next : packageDataFields) {
            DataField dataField = (DataField) next;
            if (!checkDataField(xpdlPackage, process, dataField)) {
                addIssue(REFERENCE_TYPES_NOT_ALLOWED, dataField);
            }
        }
        for (Object next : processDataFields) {
            DataField dataField = (DataField) next;
            if (!checkDataField(xpdlPackage, process, dataField)) {
                addIssue(REFERENCE_TYPES_NOT_ALLOWED, dataField);
            }
        }
    }

    /**
     * @param dataField
     *            The data field to check.
     * @return true if it has correct type.
     */
    private boolean checkDataField(Package xpdlPackage, Process process,
            DataField dataField) {
        DataType dataType = dataField.getDataType();
        if (dataType instanceof BasicType) {
            BasicType basicType = (BasicType) dataType;
            if (basicType.getType() != BasicTypeType.REFERENCE_LITERAL) {
                return true;
            }
        }
        // TODO JA: If declared types will be supported by simulation then
        // uncomment this.
        /*
         * else if (dataType instanceof DeclaredType) { Set<String> checkedIds =
         * new HashSet<String>(); return checkDeclaredType(xpdlPackage,
         * (DeclaredType) dataType, checkedIds); }
         */

        return false;
    }

    /**
     * @param xpdlPackage
     * @param dataType
     */
    private boolean checkDeclaredType(Package xpdlPackage,
            DeclaredType dataType, Set<String> checkedIds) {
        DeclaredType declaredType = (DeclaredType) dataType;
        String typeDeclarationId = declaredType.getTypeDeclarationId();
        TypeDeclaration typeDecl = XpdlSearchUtil.findTypeDeclaration(
                xpdlPackage, typeDeclarationId);
        if (typeDecl.getBasicType() instanceof BasicType) {
            BasicType basicType = (BasicType) typeDecl.getBasicType();
            if (basicType.getType() != BasicTypeType.REFERENCE_LITERAL) {
                return true;
            }
        } else if (typeDecl.getDeclaredType() instanceof DeclaredType) {
            checkedIds.add(typeDeclarationId);
            if (checkedIds.contains(typeDecl.getDeclaredType()
                    .getTypeDeclarationId())) {
                addIssue(LOOP_IN_DECLARED_TYPES, typeDecl.getDeclaredType());
                return true;
            }
            return checkDeclaredType(xpdlPackage, typeDecl.getDeclaredType(),
                    checkedIds);
        }
        return false;
    }

    @Override
    protected void validateFlowContainer(Process process, EList<Activity> activities, EList<Transition> transitions) {
    }

}
