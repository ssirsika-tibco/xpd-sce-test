/**
 * 
 */
package com.tibco.xpd.wsdlgen.validations;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.SoapBindingStyle;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validation to complain to the user if a user param
 * 
 * @author rsomayaj
 * @since 3.3 (28 Oct 2010)
 */
public class ProcAPIActsRefElemGenBomClassifiers extends ProcessValidationRule {

    private static final String CANNOT_EXPORT_AS_ELEMENT_ISSUE_ID =
            "bpmn.wsdlgenElemExportTLEError";

    private static final String CANNOT_USE_PARAM_FOR_RPC_LITERAL =
            "bpmn.wsdlgenRpcTypeExportError";

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
        SoapBindingStyle wsdlBindingType =
                Xpdl2ModelUtil.getWsdlBindingStyle(process);

        // If the process generated Doc-literal based parts
        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);
        for (Activity activity : allActivitiesInProc) {
            if (Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {
                List<FormalParameter> associatedProcessRelevantDataForActivity =
                        ProcessInterfaceUtil
                                .getAssociatedFormalParameters(activity);
                for (ProcessRelevantData processRelevantData : associatedProcessRelevantDataForActivity) {
                    if (processRelevantData.getDataType() instanceof ExternalReference) {
                        ExternalReference externalReference =
                                (ExternalReference) processRelevantData
                                        .getDataType();

                        IProject project =
                                WorkingCopyUtil
                                        .getProjectFor(processRelevantData);

                        if (project != null) {
                            EObject referencedClassifier =
                                    ProcessUIUtil
                                            .getReferencedClassifier(externalReference,
                                                    project);

                            if (referencedClassifier instanceof Classifier) {
                                Classifier classifier =
                                        (Classifier) referencedClassifier;
                                // If the BOM is imported from an XSD then
                                // we need to check if the complex type
                                // referred has any top level elements.

                                // If not, we should complain that this
                                // cannot be exported as Document Literal
                                if (SoapBindingStyle.DOCUMENT_LITERAL
                                        .equals(wsdlBindingType)) {
                                    if (XSDUtil
                                            .doesClassifierBelongToXsdGeneratedBom(classifier)) {
                                        List<Object> topLevelElements =
                                                XSDUtil
                                                        .getTopLevelElements(classifier);

                                        if (topLevelElements == null
                                                || topLevelElements.isEmpty()) {
                                            addIssue(CANNOT_EXPORT_AS_ELEMENT_ISSUE_ID,
                                                    activity);
                                        }

                                    }
                                    // Else if it is not an XSD generated BOM
                                    // and if the below API method return false,
                                    // complain that Doc-literal type WSDL
                                    // cannot be generated
                                    else if (!BOMUtils
                                            .isExportAsTLE(classifier)) {

                                        addIssue(CANNOT_EXPORT_AS_ELEMENT_ISSUE_ID,
                                                activity);
                                        break;
                                    }
                                } else {
                                    // If it is not a DOC-LITERAL
                                    if (XSDUtil
                                            .doesClassifierBelongToXsdGeneratedBom(classifier)) {
                                        if (XSDUtil
                                                .isClassifierAnonymousType(classifier)) {
                                            addIssue(CANNOT_USE_PARAM_FOR_RPC_LITERAL,
                                                    activity);
                                        }
                                    }
                                }
                            }
                        }

                    }
                }

            }
        }
    }
}
