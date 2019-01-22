/**
 * 
 */
package com.tibco.xpd.wsdlgen.validations;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.xpdl2.rules.InterfaceBaseValidationRule;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.SoapBindingStyle;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validation to complain to the user if a user param
 * 
 * @author rsomayaj
 * @since 3.3 (28 Oct 2010)
 */
public class ProcIfcActsRefElemGenBomClassifiers extends
        InterfaceBaseValidationRule {

    private static final String CANNOT_EXPORT_AS_ELEMENT_ISSUE_ID =
            "bpmn.wsdlgenElemExportTLEError";

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.xpdl2.rules.InterfaceBaseValidationRule#validate
     * (com.tibco.xpd.xpdExtension.ProcessInterface)
     */
    @Override
    public void validate(ProcessInterface processInterface) {
        SoapBindingStyle bindingStyle =
                Xpdl2ModelUtil.getWsdlBindingStyle(processInterface);
        if (SoapBindingStyle.DOCUMENT_LITERAL.equals(bindingStyle)) {
            // If the process generated Doc-literal based parts
            Collection<InterfaceMethod> ifcMessageMethods =
                    ProcessInterfaceUtil.getIfcMessageMethods(processInterface);
            for (InterfaceMethod ifcMethod : ifcMessageMethods) {
                List<FormalParameter> interfaceMethodAssociatedFormalParameters =
                        ProcessInterfaceUtil
                                .getInterfaceMethodAssociatedFormalParameters(ifcMethod);
                for (FormalParameter formalParam : interfaceMethodAssociatedFormalParameters) {
                    if (formalParam.getDataType() instanceof ExternalReference) {
                        ExternalReference externalReference =
                                (ExternalReference) formalParam.getDataType();

                        IProject project =
                                WorkingCopyUtil.getProjectFor(formalParam);

                        if (project != null) {
                            EObject referencedClassifier =
                                    ProcessUIUtil
                                            .getReferencedClassifier(externalReference,
                                                    project);

                            if (referencedClassifier instanceof Classifier) {
                                // Unfortunately, there is no way to test
                                // whether the rule is triggered.
                                if (!BOMUtils
                                        .isExportAsTLE((Classifier) referencedClassifier)) {
                                    addIssue(CANNOT_EXPORT_AS_ELEMENT_ISSUE_ID,
                                            ifcMethod);
                                    break;
                                }
                            }
                        }

                    }
                }

            }
        }
    }

}
