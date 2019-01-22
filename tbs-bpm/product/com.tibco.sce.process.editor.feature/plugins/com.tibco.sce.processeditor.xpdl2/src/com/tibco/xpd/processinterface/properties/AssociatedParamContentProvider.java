/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processinterface.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.event.ImplicitAssociatedParamObject;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParametersContainer;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Content provider for the Tree viewer displayed in the property section
 * showing the associated parameters. Associated parameters are contained within
 * start methods, intermediate methods and activities.
 * 
 * @author rsomayaj
 * 
 */
public class AssociatedParamContentProvider implements
        IStructuredContentProvider {

    private final static String ALL_INTERFACE_PARAMETERS_LABEL =
            Messages.AssociatedParamContentProvider_AllInterfaceParamatersLabel;

    private final static String ALL_PROCESS_PARAMETERS_LABEL =
            Messages.AssociatedParamContentProvider_AllProcessParamatersLabel;

    private final static String ALL_PROCESS_DATA_LABEL =
            Messages.AssociatedParamContentProvider_AllProcessDataLabel;

    private final static String NO_PROCESS_DATA_LABEL =
            Messages.AssociatedParamContentProvider_NoProcessData_label;

    private final static String NO_INTERFACE_DATA_LABEL =
            Messages.AssociatedParamContentProvider_NoInterfaceData_label;

    /**
     * @param inputElement
     *            The input element to get the content for.
     * @return An array of content objects.
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#
     *      getElements(java.lang.Object)
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object[] getElements(Object input) {
        List<Object> elements = new ArrayList<Object>();

        if (input != null) {
            boolean isDisableImplicit = false;
            List<AssociatedParameter> associatedParamList = null;

            if (input instanceof AssociatedParametersContainer) {
                isDisableImplicit =
                        ProcessInterfaceUtil
                                .isImplicitAssociationDisabled((AssociatedParametersContainer) input);
                associatedParamList =
                        ((AssociatedParametersContainer) input)
                                .getAssociatedParameters();

                if (isDisableImplicit && associatedParamList.isEmpty()) {
                    elements.add(new ImplicitAssociatedParamObject(
                            NO_INTERFACE_DATA_LABEL, "", //$NON-NLS-1$
                            "", //$NON-NLS-1$
                            false, null));

                } else if (associatedParamList.isEmpty()) {
                    elements.add(new ImplicitAssociatedParamObject(
                            ALL_INTERFACE_PARAMETERS_LABEL,
                            "", //$NON-NLS-1$
                            "", //$NON-NLS-1$
                            false,
                            Xpdl2ResourcesPlugin
                                    .getDefault()
                                    .getImageRegistry()
                                    .get(Xpdl2ResourcesConsts.ICON_INTERFACE_FORMAL_PARAM_INOUT)));
                } else {
                    elements.addAll(associatedParamList);
                }

            } else if (input instanceof Activity) {
                Activity activity = (Activity) input;

                boolean eventImplemented =
                        ProcessInterfaceUtil.isEventImplemented(activity);
                AssociatedParametersContainer implementedMethod = null;
                if (eventImplemented) {
                    implementedMethod =
                            ProcessInterfaceUtil.getImplementedMethod(activity);
                    if (implementedMethod == null) {
                        implementedMethod =
                                ProcessInterfaceUtil
                                        .getImplementedErrorMethod(activity);
                    }
                }

                /*
                 * Sid XPD-2087: Take implicit associaiton disabled flag into
                 * account.
                 */
                if (implementedMethod != null) {
                    isDisableImplicit =
                            ProcessInterfaceUtil
                                    .isImplicitAssociationDisabled(implementedMethod);
                } else {
                    isDisableImplicit =
                            ProcessInterfaceUtil
                                    .isImplicitAssociationDisabled(activity);
                }

                associatedParamList =
                        ProcessInterfaceUtil
                                .getActivityAssociatedParameters(activity);

                boolean processAPIActivity =
                        Xpdl2ModelUtil.isProcessAPIActivity(activity);

                if (eventImplemented) {
                    if (isDisableImplicit
                            && (implementedMethod == null || implementedMethod
                                    .getAssociatedParameters().isEmpty())) {
                        elements.add(new ImplicitAssociatedParamObject(
                                NO_INTERFACE_DATA_LABEL, "", //$NON-NLS-1$
                                "", //$NON-NLS-1$
                                false, null));

                    } else if (implementedMethod != null
                            && implementedMethod.getAssociatedParameters()
                                    .isEmpty()) {
                        elements.add(new ImplicitAssociatedParamObject(
                                ALL_INTERFACE_PARAMETERS_LABEL,
                                "", //$NON-NLS-1$
                                "", //$NON-NLS-1$
                                false,
                                Xpdl2ResourcesPlugin
                                        .getDefault()
                                        .getImageRegistry()
                                        .get(Xpdl2ResourcesConsts.ICON_INTERFACE_FORMAL_PARAM_INOUT)));
                    }

                    if (!associatedParamList.isEmpty()) {
                        elements.addAll(associatedParamList);
                    }

                } else if (processAPIActivity) {
                    if (isDisableImplicit && associatedParamList.isEmpty()) {
                        elements.add(new ImplicitAssociatedParamObject(
                                NO_PROCESS_DATA_LABEL, "", //$NON-NLS-1$
                                "", //$NON-NLS-1$
                                false, null));

                    } else if (associatedParamList.isEmpty()) {
                        elements.add(new ImplicitAssociatedParamObject(
                                ALL_PROCESS_PARAMETERS_LABEL,
                                "", //$NON-NLS-1$
                                "", //$NON-NLS-1$
                                false,
                                Xpdl2ResourcesPlugin
                                        .getDefault()
                                        .getImageRegistry()
                                        .get(Xpdl2ResourcesConsts.IMG_FORMALPARAM_INOUT)));
                    } else {
                        elements.addAll(associatedParamList);
                    }
                } else {
                    if (isDisableImplicit && associatedParamList.isEmpty()) {
                        elements.add(new ImplicitAssociatedParamObject(
                                NO_PROCESS_DATA_LABEL, "", //$NON-NLS-1$
                                "", //$NON-NLS-1$
                                false, null));

                    } else if (associatedParamList.isEmpty()) {
                        elements.add(new ImplicitAssociatedParamObject(
                                ALL_PROCESS_DATA_LABEL,
                                "", //$NON-NLS-1$
                                "", //$NON-NLS-1$
                                false,
                                Xpdl2ResourcesPlugin
                                        .getDefault()
                                        .getImageRegistry()
                                        .get(Xpdl2ResourcesConsts.ICON_DATAFIELD)));
                    } else {
                        elements.addAll(associatedParamList);
                    }
                }

            }

        }

        return elements.toArray();
    }

    @Override
    public void dispose() {
    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        /* Just a little debugging for the new ActivityInterfaceDataUtil. */
        if (false) {
            System.out
                    .println("ACtivtyInterfaceData:\n================================="); //$NON-NLS-1$
            if (newInput instanceof Activity) {
                Collection<ActivityInterfaceData> activityInterfaceData =
                        ActivityInterfaceDataUtil
                                .getActivityInterfaceData((Activity) newInput);
                for (ActivityInterfaceData ifcData : activityInterfaceData) {
                    System.out.println("  " + ifcData.toString()); //$NON-NLS-1$
                }
            } else if (newInput instanceof AssociatedParametersContainer) {
                Collection<ActivityInterfaceData> activityInterfaceData =
                        ActivityInterfaceDataUtil
                                .getInterfaceEventInterfaceData((AssociatedParametersContainer) newInput);
                for (ActivityInterfaceData ifcData : activityInterfaceData) {
                    System.out.println("  " + ifcData.toString()); //$NON-NLS-1$
                }

            }
            System.out.println("=================================\n\n"); //$NON-NLS-1$
        }
    }
}