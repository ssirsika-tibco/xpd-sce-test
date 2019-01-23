/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import java.util.Collection;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessRelevantDataUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Precision;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Set the length of the integer fields to maximum integer length
 * <code>ProcessRelevantDataUtil.MAX_NUMBERTYPE_PRECISION</code> for all process
 * relevant data and type declarations in the package
 * 
 * @author bharge
 * 
 */
public class SetBasicTypeLengthForAllDataResolution extends
        AbstractWorkingCopyResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     * @param target
     * @param marker
     * @return
     * @throws ResolutionException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        CompoundCommand cmd = new CompoundCommand();
        Package pckg = Xpdl2ModelUtil.getPackage(target);

        if (target instanceof ProcessRelevantData
                || target instanceof TypeDeclaration) {
            setMaxIntegerLengthForAllData(editingDomain, pckg, cmd);
        }

        return cmd;

    }

    /**
     * @param editingDomain
     * @param pckg
     * @param cmd
     */
    private void setMaxIntegerLengthForAllData(EditingDomain editingDomain,
            Package pckg, CompoundCommand cmd) {

        EList<TypeDeclaration> typeDeclarations = pckg.getTypeDeclarations();

        for (TypeDeclaration typeDeclaration : typeDeclarations) {
            BasicType bType = typeDeclaration.getBasicType();

            if (BasicTypeType.INTEGER_LITERAL.equals(bType.getType())) {
                Precision newPrecision =
                        Xpdl2Factory.eINSTANCE.createPrecision();
                newPrecision
                        .setValue(ProcessRelevantDataUtil.MAX_NUMBERTYPE_PRECISION);
                cmd.append(SetCommand.create(editingDomain,
                        bType,
                        Xpdl2Package.eINSTANCE.getBasicType_Precision(),
                        newPrecision));
            }
        }

        Collection<ProcessRelevantData> allDataInPackage =
                ProcessInterfaceUtil.getAllDataInPackage(pckg);

        for (ProcessRelevantData processRelevantData : allDataInPackage) {
            DataType dataType = processRelevantData.getDataType();

            if (dataType instanceof BasicType) {

                BasicType bType = (BasicType) dataType;

                if (BasicTypeType.INTEGER_LITERAL.equals(bType.getType())) {
                    Precision newPrecision =
                            Xpdl2Factory.eINSTANCE.createPrecision();
                    newPrecision
                            .setValue(ProcessRelevantDataUtil.MAX_NUMBERTYPE_PRECISION);
                    cmd.append(SetCommand.create(editingDomain,
                            bType,
                            Xpdl2Package.eINSTANCE.getBasicType_Precision(),
                            newPrecision));
                }
            }
        }

    }

}
