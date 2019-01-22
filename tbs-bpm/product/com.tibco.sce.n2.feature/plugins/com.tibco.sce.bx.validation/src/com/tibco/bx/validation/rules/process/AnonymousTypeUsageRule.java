/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

import java.util.Collection;

import org.eclipse.core.resources.IProject;
import org.eclipse.uml2.uml.Classifier;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeReference;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * This validation is to ensure that Anonymous types in a BOM are not used to
 * create data fields or parameters
 * 
 * @author bharge
 * @since 16 Aug 2012
 */
public class AnonymousTypeUsageRule extends ProcessValidationRule {

    private static final String ANONYMOUS_TYPE_ISSUE_ID =
            "bx.anonymousTypeCannotBeUsedForDatafieldOrParam"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     */
    @Override
    public void validate(Process process) {

        if (null != process) {
            Collection<ProcessRelevantData> allProcessRelevantData =
                    ProcessInterfaceUtil.getAllDataDefinedInProcess(process);

            for (ProcessRelevantData prd : allProcessRelevantData) {
                IProject project = WorkingCopyUtil.getProjectFor(prd);
                DataType dataType = prd.getDataType();

                if (dataType instanceof ExternalReference) {
                    ExternalReference extReference =
                            (ExternalReference) dataType;
                    ComplexDataTypeReference complexDataTypeRef =
                            ProcessUIUtil
                                    .xpdl2RefToComplexDataTypeRef(extReference);

                    if (null != complexDataTypeRef && null != project) {
                        Classifier refClassifier =
                                ProcessUIUtil
                                        .getReferencedClassifier(complexDataTypeRef,
                                                project);

                        if (refClassifier instanceof Classifier) {
                            Boolean classifierTopLevelElement =
                                    XSDUtil.isClassifierTopLevelElement(refClassifier);
                            /*
                             * top level element and is anonymous type then
                             * allow. not a top level element but is anonymous
                             * type then disallow
                             */
                            Boolean classifierAnonymousType =
                                    XSDUtil.isClassifierAnonymousType(refClassifier);

                            if (!classifierTopLevelElement
                                    && classifierAnonymousType) {
                                addIssue(ANONYMOUS_TYPE_ISSUE_ID, prd);
                            }
                        }
                    }
                }
            }
        }
    }

}
