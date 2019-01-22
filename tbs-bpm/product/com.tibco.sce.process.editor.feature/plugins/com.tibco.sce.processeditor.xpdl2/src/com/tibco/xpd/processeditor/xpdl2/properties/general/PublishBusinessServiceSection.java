/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.processeditor.xpdl2.actions.CloseOpenProcessEditorCommand;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper.FixedValueFieldChangedListener;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper.FixedValueFieldProposalProvider;
import com.tibco.xpd.processeditor.xpdl2.util.PageflowUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.ResetDefaultActivityColourCommand;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.BusinessServicePublishType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Publish Business Service Section basically provides controls to publish a
 * pageflow process into business service and provides a text control to set the
 * business service category. This section is used from Pageflow property
 * section and Business Service property section.
 * 
 * @author bharge
 * @since 1 Aug 2014
 */
public class PublishBusinessServiceSection extends AbstractTransactionalSection {

    private Button publish;

    private DecoratedField category;

    /*
     * Sid XPD_8227 New option for configuring biz service for mobile or desktop
     * target.
     */
    private Button targetMobile;

    private Button targetDesktop; /* Default */

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#setInput(org.eclipse.ui.IWorkbenchPart,
     *      org.eclipse.jface.viewers.ISelection)
     * 
     * @param part
     * @param selection
     */
    @Override
    public void setInput(IWorkbenchPart part, ISelection selection) {

        super.setInput(part, selection);
    }

    @Override
    protected EObject resollveInput(Object object) {

        if (object instanceof EditPart) {

            return (EObject) ((EditPart) object).getAdapter(EObject.class);
        }

        return super.resollveInput(object);
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {

        if (getInput() instanceof Process && publish != null
                && !publish.isDisposed()) {
            Process process = (Process) getInput();
            Object value =
                    Xpdl2ModelUtil
                            .getOtherAttribute(process,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_PublishAsBusinessService());
            Text categoryField = (Text) category.getControl();
            if (value instanceof Boolean
                    && ((Boolean) value).booleanValue() == true) {
                publish.setSelection(true);
                categoryField.setEnabled(true);
                categoryField.setEditable(true);
                categoryField.setBackground(Display.getDefault()
                        .getSystemColor(SWT.COLOR_WHITE));

                /*
                 * Sid XPD_8227 New option for configuring biz service for
                 * mobile or desktop target.
                 */
                BusinessServicePublishType publishType =
                        (BusinessServicePublishType) Xpdl2ModelUtil
                                .getOtherAttribute(process,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_BusinessServicePublishType());

                if (BusinessServicePublishType.MOBILE.equals(publishType)) {
                    targetDesktop.setSelection(false);
                    targetMobile.setSelection(true);
                } else {
                    targetMobile.setSelection(false);
                    targetDesktop.setSelection(true);
                }
                targetDesktop.setEnabled(true);
                targetMobile.setEnabled(true);

            } else {
                publish.setSelection(false);
                categoryField.setEnabled(false);
                categoryField.setEditable(false);
                categoryField.setBackground(Display.getDefault()
                        .getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));

                /*
                 * Sid XPD_8227 New option for configuring biz service for
                 * mobile or desktop target.
                 */
                targetDesktop.setSelection(false);
                targetMobile.setSelection(false);
                targetDesktop.setEnabled(false);
                targetMobile.setEnabled(false);

            }

            value =
                    Xpdl2ModelUtil.getOtherAttribute(process,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_BusinessServiceCategory());
            if (value == null) {
                value = ""; //$NON-NLS-1$
            }
            if (value instanceof String) {
                String text = (String) value;
                updateText(categoryField, text);
            }

        }
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {

        GridLayoutFactory.swtDefaults().applyTo(parent);
        Composite businessServices = toolkit.createComposite(parent);

        GridDataFactory.fillDefaults().grab(true, false)
                .applyTo(businessServices);

        GridLayout businessServicesLayout = new GridLayout(2, false);
        businessServicesLayout.marginHeight = 0;
        businessServicesLayout.marginWidth = 0;
        businessServices.setLayout(businessServicesLayout);

        publish =
                toolkit.createButton(businessServices,
                        Messages.PageflowPropertySection_PubishLabel,
                        SWT.CHECK,
                        "publish-checkbox"); //$NON-NLS-1$
        publish.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2,
                1));

        manageControl(publish);

        /*
         * Sid XPD_8227 New option for configuring biz service for mobile or
         * desktop target.
         */
        Label targetPlatformLabel =
                toolkit.createLabel(businessServices,
                        Messages.PublishBusinessServiceSection_TargetDevice_label);
        targetPlatformLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
                false, false));

        Composite radioGroup = toolkit.createComposite(businessServices);
        radioGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
                false));
        GridLayout rgLayout = new GridLayout(3, false);
        radioGroup.setLayout(rgLayout);

        targetDesktop =
                toolkit.createButton(radioGroup,
                        Messages.PublishBusinessServiceSection_TargetDeviceDesktop_button,
                        SWT.RADIO);
        targetDesktop.setLayoutData(new GridData());
        manageControl(targetDesktop);

        targetMobile =
                toolkit.createButton(radioGroup,
                        Messages.PublishBusinessServiceSection_TargetDeviceMobile_button,
                        SWT.RADIO);
        targetMobile.setLayoutData(new GridData());
        manageControl(targetMobile);

        Composite rgFiller = toolkit.createComposite(radioGroup);
        GridData rgFGD = new GridData(GridData.FILL_HORIZONTAL);
        rgFGD.heightHint = 1;
        rgFiller.setLayoutData(rgFGD);

        Label categoryLabel =
                toolkit.createLabel(businessServices,
                        Messages.PageflowPropertySection_CategoryLabel);
        categoryLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
                false));

        //
        // Add content assisted text control for referenced event entry.
        FixedValueFieldProposalProvider proposalProvider =
                new FixedValueFieldAssistHelper.FixedValueFieldProposalProvider() {
                    @Override
                    public Object[] getProposals() {
                        Object[] proposals = new Object[0];
                        if (((Text) category.getControl()).isEnabled()) {
                            com.tibco.xpd.xpdl2.Process process =
                                    (com.tibco.xpd.xpdl2.Process) getInput();

                            List<String> categories =
                                    PageflowUtil.getBusinessCategories(process);
                            Set<String> set = new TreeSet<String>(categories);
                            proposals = set.toArray();
                        }
                        return proposals;
                    }
                };

        FixedValueFieldAssistHelper refTaskHelper =
                new FixedValueFieldAssistHelper(toolkit, businessServices,
                        proposalProvider, false);

        refTaskHelper
                .addValueChangedListener(new FixedValueFieldChangedListener() {

                    @Override
                    public void fixedValueFieldChanged(Object newValue) {
                        if (newValue instanceof String) {
                            String text = (String) newValue;
                            ((Text) category.getControl()).setText(text);
                        }
                    }

                });

        category = refTaskHelper.getDecoratedField();

        category.getControl().setData(XpdFormToolkit.INSTRUMENTATION_DATA_NAME,
                "BusinessCategory"); //$NON-NLS-1$
        category.getLayoutControl()
                .setData(XpdFormToolkit.INSTRUMENTATION_DATA_NAME,
                        "assistBusinessCategory"); //$NON-NLS-1$

        GridDataFactory.fillDefaults().grab(true, false)
                .applyTo(category.getLayoutControl());

        category.getLayoutControl().setBackground(parent.getBackground());

        manageControl((Text) category.getControl());

        return businessServices;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {

        if (getInput() instanceof Process) {
            Process process = (Process) getInput();
            EditingDomain ed = getEditingDomain();

            if (publish != null && !publish.isDisposed() && obj == publish) {
                return getSetPublishedCommand(ed,
                        process,
                        publish.getSelection());

            } else if (category != null && obj == category.getControl()) {
                String categoryName =
                        ((Text) category.getControl()).getText().trim();

                return getSetCategoryNameCommand(ed, process, categoryName);

            }
            /*
             * Sid XPD_8227 New option for configuring biz service for mobile or
             * desktop target. (null is default = Desktop.
             */
            else if (targetDesktop != null && obj == targetDesktop) {
                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.PublishBusinessServiceSection_SetTargetToDesktop_menu);

                cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                        process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_BusinessServicePublishType(),
                        null));

                return cmd;

            } else if (targetDesktop != null && obj == targetMobile) {
                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.PublishBusinessServiceSection_SetTargetToMobile_menu);

                cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                        process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_BusinessServicePublishType(),
                        BusinessServicePublishType.MOBILE));

                return cmd;

            }

        }
        return null;
    }

    /**
     * @param ed
     * @param process
     * @param categoryName
     * @return Command to set the category name.
     */
    private Command getSetCategoryNameCommand(EditingDomain ed,
            Process process, String categoryName) {
        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.PageflowPropertySection_SetBusinessCategoryCommand);
        String old =
                (String) Xpdl2ModelUtil.getOtherAttribute(process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_BusinessServiceCategory());

        if (categoryName != null && !categoryName.equals(old)) {
            cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                    process,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_BusinessServiceCategory(),
                    categoryName));
            return cmd;
        }
        return null;
    }

    /**
     * 
     * @param ed
     * @param process
     * @param isPublished
     * @return Command to set/unset pageflow as business service.
     */
    private Command getSetPublishedCommand(EditingDomain ed, Process process,
            boolean isPublished) {
        Boolean shouldPublish = new Boolean(isPublished);

        CompoundCommand cmd = new CloseOpenProcessEditorCommand(process);
        cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                process,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_PublishAsBusinessService(),
                shouldPublish ? shouldPublish : null));

        /**
         * XPD-1026: business service category should be defaulted to project
         * name / process package name when publish as business service is
         * checked
         */
        if (shouldPublish) {
            cmd.setLabel(Messages.PageflowPropertySection_SetPublishCommand);
            IProject project = WorkingCopyUtil.getProjectFor(process);
            String defaultCategory =
                    Xpdl2ModelUtil.getBusinessServiceDefaultCategory(project
                            .getName(), process.getPackage().getName());
            cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                    process,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_BusinessServiceCategory(),
                    defaultCategory));

            /**
             * Convert any activity that has default colour for pageflow process
             * to use default colour for business service.
             */
            ProcessWidgetColors businessServiceColours =
                    ProcessWidgetColors
                            .getInstance(ProcessWidgetType.BUSINESS_SERVICE);
            ProcessWidgetColors pageflowColours =
                    ProcessWidgetColors
                            .getInstance(ProcessWidgetType.PAGEFLOW_PROCESS);

            Collection<Activity> allActivitiesInProc =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);

            for (Activity activity : allActivitiesInProc) {
                cmd.append(new ResetDefaultActivityColourCommand(ed, activity,
                        pageflowColours, businessServiceColours));
            }

        } else {
            cmd.setLabel(Messages.PageflowPropertySection_UnsetPublishCommand);
            cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                    process,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_BusinessServiceCategory(),
                    null));
            /**
             * Convert any activity that has default colour for business service
             * to use default colour for pageflow process.
             */
            ProcessWidgetColors businessServiceColours =
                    ProcessWidgetColors
                            .getInstance(ProcessWidgetType.BUSINESS_SERVICE);
            ProcessWidgetColors pageflowColours =
                    ProcessWidgetColors
                            .getInstance(ProcessWidgetType.PAGEFLOW_PROCESS);

            Collection<Activity> allActivitiesInProc =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);

            for (Activity activity : allActivitiesInProc) {
                cmd.append(new ResetDefaultActivityColourCommand(ed, activity,
                        businessServiceColours, pageflowColours));
            }
        }
        return cmd;
    }

}
