/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.bom.xsdtransform.internal.notify;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.Cursors;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListener;
import org.eclipse.emf.transaction.ResourceSetListenerImpl;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.xsdtransform.Activator;
import com.tibco.xpd.bom.xsdtransform.internal.Messages;

/**
 * Checks if there is a change to the BOM/UML semantic model with applied
 * XsdNotation profile which will destroy BOM-XSD compatibility.
 * 
 * @author Jan Arciuchiewicz
 */
public class BOMSemanticModelChangeTrigger extends ResourceSetListenerImpl
        implements ResourceSetListener {

    /**
     * 
     */
    private static final String GENERATED = "Generated";

    /** */
    private static final String XSD_NOTATION_PROFILE_NAME =
            "XsdNotationProfile"; //$NON-NLS-1$

    private static final List<EStructuralFeature> IGNORED_FEATURES = Arrays
            .asList(new EStructuralFeature[] {
                    UMLPackage.Literals.PACKAGE__PROFILE_APPLICATION,
                    UMLPackage.Literals.PROFILE_APPLICATION__APPLIED_PROFILE,
                    EcorePackage.Literals.EMODEL_ELEMENT__EANNOTATIONS });

    /**
     * {@inheritDoc}
     */
    @Override
    public Command transactionAboutToCommit(ResourceSetChangeEvent event)
            throws RollbackException {
        Command result = null;
        final Map<Model, Profile> xsdModels = new HashMap<Model, Profile>();
        final Set<EAnnotation> dbAnnotations = new HashSet<EAnnotation>();
        final Set<Model> dbModels = new HashSet<Model>();
        for (Notification notification : event.getNotifications()) {
            Model model = null;
            if (notification.getNotifier() instanceof Element) { // UML element
                if (!IGNORED_FEATURES.contains(notification.getFeature())) {
                    Element element = (Element) notification.getNotifier();
                    model = element.getModel();
                }

            } else if (isRestrictedTypeChange(notification)) {
                DynamicEObjectImpl dynEObject =
                        (DynamicEObjectImpl) notification.getNotifier();
                EStructuralFeature baseFeature =
                        dynEObject.eClass()
                                .getEStructuralFeature("base_Property"); //$NON-NLS-1$
                Object object =
                        dynEObject.dynamicGet(baseFeature.getFeatureID());
                if (object instanceof Element) {
                    model = ((Element) object).getModel();
                }
            }
            if (model != null) {
                Profile xsdNotationProfile =
                        model.getAppliedProfile(XSD_NOTATION_PROFILE_NAME);
                if (xsdNotationProfile != null) {
                    xsdModels.put(model, xsdNotationProfile);
                }
                /*
                 * If model has annotation that it has been generated from db -
                 * annotation source = DBGenerated
                 */
                List<EAnnotation> annotations = model.getEAnnotations();

                for (EAnnotation annotation : annotations) {
                    String source = annotation.getSource();
                    if (GENERATED.equals(source)) {
                        dbModels.add(model);
                        dbAnnotations.add(annotation);
                        break;
                    }
                }
            }
        }

        if (!xsdModels.isEmpty()) {

            if (displayModelChangeMessage(xsdModels.keySet())) {

                TransactionalEditingDomain ed =
                        TransactionUtil.getEditingDomain(xsdModels.keySet()
                                .iterator().next());
                /*
                 * XPD-4544: unapplying profile on large models might take some
                 * time. so we show busy cursor while the profile is being
                 * unapplied so the user knows some processing is going on
                 */
                Cursor oldCursor = null;
                boolean waitCursorSetOn = false;

                if (!waitCursorSetOn
                        && Thread.currentThread() == Display.getDefault()
                                .getThread()) {
                    oldCursor =
                            Display.getDefault().getActiveShell().getCursor();
                    waitCursorSetOn = true;
                }
                RecordingCommand cmd =
                        doUnapplyProfile(xsdModels,
                                oldCursor,
                                waitCursorSetOn,
                                ed);
                result = cmd;
            } else {
                throw new RollbackException(
                        new Status(
                                IStatus.CANCEL,
                                Activator.PLUGIN_ID,
                                Messages.BOMSemanticModelChangeTrigger_ChangesRolledBack_message));
            }
        } else if (!dbAnnotations.isEmpty()) {
            /*
             * If it is not an xsd notation model - it might be a db generated
             * bom.
             */
            if (displayModelChangeMessage(dbModels)) {
                final Model dbModel = dbModels.iterator().next();
                TransactionalEditingDomain ed =
                        TransactionUtil.getEditingDomain(dbModel);
                RecordingCommand cmd =
                        new RecordingCommand(
                                ed,
                                Messages.BOMSemanticModelChangeTrigger_RemoveXSDMetadata_label) {
                            @Override
                            protected void doExecute() {
                                dbModel.getEAnnotations().remove(dbAnnotations
                                        .iterator().next());
                            }
                        };
                result = cmd;
            } else {
                throw new RollbackException(
                        new Status(
                                IStatus.CANCEL,
                                Activator.PLUGIN_ID,
                                Messages.BOMSemanticModelChangeTrigger_ChangesRolledBack_message));
            }

        }

        return result;
    }

    /**
     * this method unapplies the profile on the model and shows busy cursor
     * while unapplying the profile as it might take some time for large models
     * 
     * @param xsdModels
     * @param oldCursor
     * @param waitCursorSetOn
     * @param ed
     * @return
     */
    private RecordingCommand doUnapplyProfile(
            final Map<Model, Profile> xsdModels, final Cursor oldCursor,
            final boolean waitCursorSetOn, TransactionalEditingDomain ed) {

        return new RecordingCommand(ed,
                Messages.BOMSemanticModelChangeTrigger_RemoveXSDMetadata_label) {
            @Override
            protected void doExecute() {
                try {
                    for (Model model : xsdModels.keySet()) {
                        /*
                         * if refactor rename is used it shows its own dialog
                         * which is different from the current thread and fails
                         * to get the active shell throwing SWTException
                         */
                        if (waitCursorSetOn) {
                            Display.getDefault().getActiveShell()
                                    .setCursor(Cursors.WAIT);
                        }
                        model.unapplyProfile(xsdModels.get(model));
                    }
                } finally {
                    /* If we showed wait cursor then turn it back off. */
                    if (waitCursorSetOn) {
                        Display.getDefault().getActiveShell()
                                .setCursor(oldCursor);
                    }
                }
            }
        };
    }

    /**
     * @return
     */
    private boolean isRestrictedTypeChange(Notification notification) {
        Object notifier = notification.getNotifier();
        if (notifier instanceof DynamicEObjectImpl) {
            EClass eClass = ((DynamicEObjectImpl) notifier).eClass();
            if (eClass != null
                    && PrimitivesUtil.RESTRICTED_TYPE_STEREOTYPE_NAME
                            .equals(eClass.getName())
                    && PrimitivesUtil.PRIMITIVE_TYPE_FACETS_PROFILE_NAME
                            .equals(eClass.getEPackage().getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Displays message about semantic model change to the user.
     * 
     * @param models
     *            the affected models.
     * @return 'true'if user agreed to proceed with changes, 'false' otherwise.
     */
    private boolean displayModelChangeMessage(Set<Model> models) {

        // Retrieve model names.
        Iterator<Model> i = models.iterator();
        Model first = i.next();
        StringBuilder sb =
                new StringBuilder().append('\'')
                        .append(PrimitivesUtil.getDisplayLabel(first))
                        .append('\'');
        while (i.hasNext()) {
            sb.append(", "); //$NON-NLS-1$
            sb.append('\'').append(PrimitivesUtil.getDisplayLabel(first))
                    .append('\'');
        }
        final String modelNames = sb.toString();

        final boolean[] answer = new boolean[1];
        TransactionalEditingDomain ed = TransactionUtil.getEditingDomain(first);
        Display.getDefault().syncExec(new Runnable() {
            @Override
            public void run() {
                String title =
                        Messages.BOMSemanticModelChangeTrigger_BOM_tiltle;
                String message =
                        String.format(Messages.BOMSemanticModelChangeTrigger_SemanticModelChange_longdesc,
                                modelNames);
                answer[0] =
                        MessageDialog.openQuestion(PlatformUI.getWorkbench()
                                .getActiveWorkbenchWindow().getShell(),
                                title,
                                message);
            }
        });
        return answer[0];
    }

    /**
     * Only pre-commit events, not post-commit events.
     */
    @Override
    public boolean isPrecommitOnly() {
        return true;
    }
}
