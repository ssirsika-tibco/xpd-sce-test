/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.event;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationField;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationFields;
import com.tibco.xpd.xpdExtension.CorrelationMode;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author NWilson
 * 
 */
public class CorrelationDataAssociationContentProvider implements
        IStructuredContentProvider {

    /**
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     * 
     * @param inputElement
     * @return
     */
    @Override
    public Object[] getElements(Object input) {
        Object[] results;
        List<AssociatedCorrelationField> elements =
                new ArrayList<AssociatedCorrelationField>();

        boolean isDisableImplicit = false;
        boolean isMessageStartActivity = false;

        if (input instanceof Activity) {
            Activity activity = (Activity) input;

            /*
             * XPD-6751: Saket: Modified
             * Xpdl2ModelUtil.isMessageProcessStartActivity(activity) to return
             * true for only those activities that are in the main process and
             * therefore we don't need to have
             * Xpdl2ModelUtil.isEventSubProcessStartEvent(activity) alongside
             * it.
             */
            isMessageStartActivity =
                    Xpdl2ModelUtil.isMessageProcessStartActivity(activity);

            Object other =
                    Xpdl2ModelUtil
                            .getOtherElement(activity,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_AssociatedCorrelationFields());
            if (other instanceof AssociatedCorrelationFields) {
                AssociatedCorrelationFields fields =
                        (AssociatedCorrelationFields) other;
                isDisableImplicit = fields.isDisableImplicitAssociation();
                elements = fields.getAssociatedCorrelationField();
            }
        }
        if (elements.size() == 0) {
            if (isDisableImplicit) {
                ImplicitAssociatedParamObject noImpliedParams =
                        new ImplicitAssociatedParamObject(
                                Messages.CorrelationDataAssociationContentProvider_NoImplicitFields,
                                "", "", false, null); //$NON-NLS-1$ //$NON-NLS-2$
                results =
                        new ImplicitAssociatedParamObject[] { noImpliedParams };

            } else {

                String defaultCorrelationMode =
                        isMessageStartActivity ? CorrelationMode.INITIALIZE
                                .getName() : CorrelationMode.CORRELATE
                                .getName();

                ImplicitAssociatedParamObject allImpliedParams =
                        new ImplicitAssociatedParamObject(
                                Messages.CorrelationDataAssociationContentProvider_AllFields,
                                defaultCorrelationMode,
                                "", //$NON-NLS-1$
                                false,
                                Xpdl2ProcessEditorPlugin
                                        .getDefault()
                                        .getImageRegistry()
                                        .get(ProcessEditorConstants.IMG_CORRELATION_FOLDER));
                results =
                        new ImplicitAssociatedParamObject[] { allImpliedParams };
            }

        } else {
            results = elements.toArray();
        }
        return results;
    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     * 
     */
    @Override
    public void dispose() {
    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param viewer
     * @param oldInput
     * @param newInput
     */
    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

}
