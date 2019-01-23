/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.bom.resources.ui.internal.clipboard;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.emf.clipboard.core.OverridePasteChildOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.PasteChildOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.PasteTarget;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;

import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Paste child operation for <code>Profile</code> annotation. The profile
 * annotation will include profile URIs of the profiles that are included in the
 * copied objects. If these profiles are not applied to target model then the
 * user will be asked whether to continue as stereotypes from these profiles
 * will be discarded in the pasted elements.
 * <p>
 * Special case: If the base primitive type profile is not applied then it will
 * be applied to the target model, the user will not be made aware of this.
 * </p>
 * 
 * @author njpatel
 * 
 */
public class ProfilePasteOperation extends OverridePasteChildOperation {

    /**
     * Paste <code>Profile</code> application operation.
     * 
     * @param overriddenChildPasteOperation
     */
    public ProfilePasteOperation(
            PasteChildOperation overriddenChildPasteOperation) {
        super(overriddenChildPasteOperation);
    }

    @Override
    public void paste() throws Exception {

        if (isCancelled()) {
            throwCancelException();
        }

        EAnnotation profileAnnot = (EAnnotation) getEObject();
        TransactionalEditingDomain ed = getEditingDomain();

        if (ed != null) {
            Model model = getTargetModel();

            if (model != null) {
                Set<Profile> profilesNotApplied = new HashSet<Profile>();
                for (EObject eo : profileAnnot.getContents()) {
                    if (eo instanceof EAnnotation) {
                        String uriStr = ((EAnnotation) eo).getSource();

                        if (uriStr != null) {
                            URI uri = URI.createURI(uriStr);
                            /*
                             * If this is the base primitive types profile and
                             * is not applied to the model then do so, otherwise
                             * report to user that profile has not been applied
                             */
                            EObject eObject = ed.getResourceSet().getEObject(
                                    uri, true);

                            if (eObject instanceof Profile) {
                                Profile profile = (Profile) eObject;
                                if (!model.isProfileApplied(profile)) {
                                    if (uri
                                            .trimFragment()
                                            .toString()
                                            .equals(
                                                    PrimitivesUtil.BOM_PRIMITIVE_TYPES_FACETS_PROFILE_URI)) {
                                        model.applyProfile(profile);
                                    } else {

                                        profilesNotApplied.add(profile);
                                    }
                                }
                            }
                        }
                    }
                }

                if (!profilesNotApplied.isEmpty()) {
                    String msg;
                    if (profilesNotApplied.size() == 1) {
                        Profile profile = profilesNotApplied.iterator().next();
                        msg = String
                                .format(
                                        Messages.ProfilePasteOperation_profileNotApplied_error_message,
                                        profile.getLabel() != null ? profile
                                                .getLabel()
                                                : profile.getQualifiedName() != null ? profile
                                                        .getQualifiedName()
                                                        : profile.getName());
                    } else {
                        String profiles = ""; //$NON-NLS-1$
                        for (Iterator<Profile> iter = profilesNotApplied
                                .iterator(); iter.hasNext();) {
                            Profile profile = iter.next();
                            if (profiles.length() > 0) {
                                profiles += ", "; //$NON-NLS-1$
                            }
                            profiles += "'"; //$NON-NLS-1$
                            profiles += profile.getLabel() != null ? profile
                                    .getLabel()
                                    : profile.getQualifiedName() != null ? profile
                                            .getQualifiedName()
                                            : profile.getName();
                            profiles += "'"; //$NON-NLS-1$
                        }
                        msg = String
                                .format(
                                        Messages.ProfilePasteOperation_multipleProfilesNotApplied_error_message,
                                        profiles);
                    }

                    if (!MessageDialog
                            .openQuestion(
                                    Display.getCurrent().getActiveShell(),
                                    Messages.ProfilePasteOperation_profile_errorDlg_title,
                                    msg)) {
                        throwCancelException();
                    }
                }
            }
        }
    }

    /**
     * Get the transactional editing domain.
     * 
     * @return transactional editing domain
     */
    private TransactionalEditingDomain getEditingDomain() {
        Model model = getTargetModel();
        TransactionalEditingDomain ed = null;

        if (model != null) {
            EditingDomain editingDomain = WorkingCopyUtil
                    .getEditingDomain(model);

            if (editingDomain instanceof TransactionalEditingDomain) {
                ed = (TransactionalEditingDomain) editingDomain;
            }
        }
        return ed;
    }

    /**
     * Get target <code>Model</code>.
     * 
     * @return <code>Model</code>.
     */
    private Model getTargetModel() {
        PasteTarget parentTarget = getParentTarget();
        EObject eo = (EObject) parentTarget.getObject();

        while (eo != null && !(eo instanceof Model)) {
            if (eo instanceof Element) {
                eo = ((Element) eo).getModel();
            } else {
                eo = eo.eContainer();
            }
        }

        return (Model) eo;
    }

    @Override
    public PasteTarget getParentTarget() {
        EObject parentEObject = getParentEObject();
        // Return the parent semantic model
        if (parentEObject instanceof View) {
            parentEObject = ((View) parentEObject).getElement();
        }

        return new PasteTarget(parentEObject);
    }
}
