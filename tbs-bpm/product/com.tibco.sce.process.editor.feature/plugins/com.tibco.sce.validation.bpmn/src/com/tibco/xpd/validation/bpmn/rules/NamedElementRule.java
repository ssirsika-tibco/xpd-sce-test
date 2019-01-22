/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdExtension.RetainFamiliarActivities;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.SeparationOfDutiesActivities;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Application;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.ArtifactType;
import com.tibco.xpd.xpdl2.Category;
import com.tibco.xpd.xpdl2.DataObject;
import com.tibco.xpd.xpdl2.ExternalPackage;
import com.tibco.xpd.xpdl2.Group;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Page;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.ResourceCosts;
import com.tibco.xpd.xpdl2.TypeDeclaration;

/**
 * @author NWilson
 * 
 */
public class NamedElementRule extends PackageValidationRule {

    private static final String INVALID = "bpmn.invalidNamedElementName"; //$NON-NLS-1$

    private static final String NUMERIC = "bpmn.invalidNamedElementNameNumeric"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule#
     *      validate(com.tibco.xpd.xpdl2.Package)
     * 
     * @param pckg
     *            The package.
     */
    @Override
    public void validate(Package pckg) {
        if (CapabilityUtil.isDeveloperActivityEnabled()) {
            Iterator<EObject> i = pckg.eAllContents();
            while (i.hasNext()) {
                EObject eo = i.next();
                NamedElement named = isProperNamedElement(eo);
                if (named != null) {
                    boolean noLeadingNumerics =
                            named instanceof ProcessRelevantData
                                    || named instanceof TypeDeclaration;
                    String name = named.getName();
                    if (!NameUtil.isValidName(name, !noLeadingNumerics)) {
                        List<String> messages = new ArrayList<String>();
                        messages.add(name);
                        addIssue(noLeadingNumerics ? NUMERIC : INVALID,
                                named,
                                messages);
                    }
                }
            }
        }
    }

    public static NamedElement isProperNamedElement(EObject eo) {
        if (eo instanceof NamedElement) {
            if (
            // Participants are a special case as they represent 'other-worldy'
            // things that may have non alpha characters.
            !(eo instanceof Participant)
                    //
                    // There are some things that are named elements that
                    // probably should not be, excluded these too.
                    && !(eo instanceof ExternalPackage)
                    && !(eo instanceof Message)
                    && !(eo instanceof com.tibco.xpd.xpdl2.Object)
                    && !(eo instanceof Page)
                    && !(eo instanceof ResourceCosts)
                    && !(eo instanceof ScriptInformation)
                    && !(eo instanceof SeparationOfDutiesActivities)
                    && !(eo instanceof RetainFamiliarActivities)

                    // The parent Artifact element is the real named element.
                    && !(eo instanceof DataObject)
                    && !(eo instanceof Group)

                    // also things that ARE probably properly NamedElement's but
                    // we do not currently given access too in UI so whay
                    // complain about them cos user cannot do anythings about
                    // it.
                    && !(eo instanceof ActivitySet)
                    && !(eo instanceof Application)
                    && !(eo instanceof Category)

                    /* SID XPD-1802 Ignore name for GroupBox's */
                    && !(eo instanceof Artifact && ArtifactType.GROUP_LITERAL
                            .equals(((Artifact) eo).getArtifactType()))
            /* -- */
            ) {
                return (NamedElement) eo;
            }
        }

        return null;
    }
}
