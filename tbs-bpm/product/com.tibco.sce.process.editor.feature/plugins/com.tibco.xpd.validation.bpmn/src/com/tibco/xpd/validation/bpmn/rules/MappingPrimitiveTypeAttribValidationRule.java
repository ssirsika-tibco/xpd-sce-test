/**
 * 
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.WebServiceOperationUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * The {@link DataMapping} model has attribute to determine whether the process
 * data is primitive type or not. This is done using a pre-commit listener.
 * However, if the XPDL is imported, the pre-commit listener doesn't have an
 * opportunity to be triggered.
 * 
 * This validation rule is added to catch those scenarios so that anything
 * requiring these attributes doesn't miss out on this.
 * 
 * This probably is a destination specific contribution - and as long as it is
 * not required by standard BPMN will not be contributed to the BPMN validation
 * provider.
 * 
 * There is also a resolution contributed to the issue associated with this
 * rule. The issue goes through the entire process and resolves all possible
 * activities to contain this attribute.
 * 
 * 
 * @author rsomayaj
 * @since 3.5.0 (18 Nov 2010)
 */
public class MappingPrimitiveTypeAttribValidationRule extends
        ProcessValidationRule {

    /**
     * Issue is added at the process level.
     */
    private static final String DATAMAPPINGS_OUTDATED_ISSUE_ID =
            "bpmn.datamappingsAttribNotUpdated"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#
     * validateFlowContainer(com.tibco.xpd.xpdl2.Process,
     * org.eclipse.emf.common.util.EList, org.eclipse.emf.common.util.EList)
     */
    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);
        for (Activity activity : allActivitiesInProc) {
            if (WebServiceOperationUtil
                    .isWebServiceImplementationType(activity)) {
                List<DataMapping> inDataMappings =
                        Xpdl2ModelUtil.getDataMappings(activity,
                                DirectionType.IN_LITERAL);

                for (DataMapping dataMapping : inDataMappings) {
                    if (!DataMappingUtil.isScripted(dataMapping)) {
                        // IN MAPPINGS - SOURCE IS Processdata
                        Object otherAttribute =
                                Xpdl2ModelUtil
                                        .getOtherAttribute(dataMapping,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_SourcePrimitiveProperty());
                        if (otherAttribute == null) {
                            addIssue(DATAMAPPINGS_OUTDATED_ISSUE_ID, process);
                            return;
                        }
                    }
                }

                // OUT MAPPINGS
                List<DataMapping> outDataMappings =
                        Xpdl2ModelUtil.getDataMappings(activity,
                                DirectionType.OUT_LITERAL);

                for (DataMapping dataMapping : outDataMappings) {
                    if (!DataMappingUtil.isScripted(dataMapping)) {
                        // IN MAPPINGS - TARGET IS Processdata
                        Object otherAttribute =
                                Xpdl2ModelUtil
                                        .getOtherAttribute(dataMapping,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_TargetPrimitiveProperty());
                        if (otherAttribute == null) {
                            addIssue(DATAMAPPINGS_OUTDATED_ISSUE_ID, process);
                            return;
                        }
                    }
                }

            }
        }
    }
}
