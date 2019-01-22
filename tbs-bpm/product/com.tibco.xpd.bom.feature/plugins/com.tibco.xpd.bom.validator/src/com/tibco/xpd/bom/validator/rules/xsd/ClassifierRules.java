package com.tibco.xpd.bom.validator.rules.xsd;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.resources.utils.BOMProfileUtils;
import com.tibco.xpd.bom.validator.XsdIssueIds;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * 
 * XSD rules to be run on UML2 Classifier objects.
 * 
 * 
 * @author rgreen
 * 
 */
public class ClassifierRules implements IValidationRule {

    private static final String EXTENSION = "extension"; //$NON-NLS-1$

    private static final String XSD_FINAL = "xsdFinal"; //$NON-NLS-1$

    private static final String XSD_BASED_CLASS = "XsdBasedClass"; //$NON-NLS-1$

    private static final String RESTRICTION = "restriction"; //$NON-NLS-1$

    private static final String ALL = "#all"; //$NON-NLS-1$

    private static final String XSD_SIMPLE_TYPE_FINAL = "xsdSimpleTypeFinal"; //$NON-NLS-1$

    private static final String XSD_BASED_PRIMITIVE_TYPE =
            "XsdBasedPrimitiveType"; //$NON-NLS-1$

    @Override
    public Class<?> getTargetClass() {
        return Classifier.class;
    }

    @Override
    public void validate(IValidationScope scope, Object o) {
        if (o instanceof Classifier) {
            Classifier clr = (Classifier) o;

            // Check if Classifier has a generalization. If so make sure
            // that, if the specific is from an XSD derived BOM, that
            // it does not have the "final" stereotype set.
            EList<Generalization> generalizations = clr.getGeneralizations();

            if (generalizations != null && !generalizations.isEmpty()) {
                Classifier general = generalizations.get(0).getGeneral();

                if (!BOMProfileUtils.isXSDProfileApplied(general.getModel())) {
                    return;
                }
                List<Stereotype> appliedStereotypes =
                        general.getAppliedStereotypes();
                if (appliedStereotypes == null) {
                    return;
                }
                for (Stereotype stereo : appliedStereotypes) {

                    if (general instanceof PrimitiveType) {
                        if (XSD_BASED_PRIMITIVE_TYPE.equals(stereo.getName())) {
                            Object value =
                                    general.getValue(stereo,
                                            XSD_SIMPLE_TYPE_FINAL);
                            if (value != null && value instanceof String) {
                                String strValue = (String) value;
                                if (strValue.equals(ALL)
                                        || strValue.contains(RESTRICTION)) {
                                    scope
                                            .createIssue(XsdIssueIds.CLASSIFIER_GENERALIZATION_OF_FINAL_TYPE,
                                                    clr.getQualifiedName(),
                                                    clr
                                                            .eResource()
                                                            .getURIFragment(clr));
                                }
                            }
                        }
                    } else if (general instanceof org.eclipse.uml2.uml.Class) {
                        if (XSD_BASED_CLASS.equals(stereo.getName())) {
                            Object value = general.getValue(stereo, XSD_FINAL);
                            if (value != null && value instanceof String) {
                                String strValue = (String) value;
                                if (strValue.equals(ALL)
                                        || strValue.contains(EXTENSION)) {
                                    scope
                                            .createIssue(XsdIssueIds.CLASSIFIER_GENERALIZATION_OF_FINAL_TYPE,
                                                    clr.getQualifiedName(),
                                                    clr
                                                            .eResource()
                                                            .getURIFragment(clr));
                                }
                            }
                        }
                    }

                }

            }
        }
    }
}
