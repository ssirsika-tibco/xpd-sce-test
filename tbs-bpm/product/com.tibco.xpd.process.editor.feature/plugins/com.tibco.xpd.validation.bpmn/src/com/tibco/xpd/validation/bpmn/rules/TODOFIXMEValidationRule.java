/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * TODOFIXMEValidationRule - 1. gives an info level validation message for a
 * text annotation or any named element that starts with TODO and allows to
 * escalate to warning or error level from preferences page. 2. gives a error
 * message for a text annotation or any named element that starts with FIXME
 * 
 * 
 * @author bharge
 * @since 3.3 (17 Jun 2010)
 */
public class TODOFIXMEValidationRule extends PackageValidationRule {

    private static final String TEXT_ANNOTATION_ISSUE_ID =
            "bpmn.textAnnotationIssueId"; //$NON-NLS-1$

    private static final String FIXME_ISSUE_ID = "bpmn.fixmeIssueId"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule#validate(com
     * .tibco.xpd.xpdl2.Package)
     */
    @Override
    public void validate(Package pckg) {
        if (null != pckg) {

            TreeIterator<EObject> allContents = pckg.eAllContents();

            for (Iterator iterator = allContents; iterator.hasNext();) {
                EObject element = (EObject) iterator.next();
                String name = null;

                if (element instanceof Artifact) {
                    name = ((Artifact) element).getTextAnnotation();
                } else {
                    if (element instanceof NamedElement) {
                        name =
                                Xpdl2ModelUtil
                                        .getDisplayName((NamedElement) element);
                    }
                }
                if (null != name && name.length() > 0) {
                    List<String> messages = new ArrayList<String>();
                    messages.add(name);
                    if (name.startsWith(Messages.Annotation_OR_NamedElement_TODO)) {
                        addIssue(TEXT_ANNOTATION_ISSUE_ID, element, messages);
                    }
                    if (name.startsWith(Messages.Annotaion_OR_NamedElement_FIXME)) {
                        addIssue(FIXME_ISSUE_ID, element, messages);
                    }
                }
            }
        }
    }
}
