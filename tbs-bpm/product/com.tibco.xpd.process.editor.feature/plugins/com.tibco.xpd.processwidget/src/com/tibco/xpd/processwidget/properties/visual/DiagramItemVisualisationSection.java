/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.processwidget.properties.visual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.WidgetRGB;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;
import com.tibco.xpd.processwidget.adapters.GraphicalColorAdapter;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;
import com.tibco.xpd.processwidget.parts.ModelAdapterEditPart;
import com.tibco.xpd.processwidget.parts.TaskEditPart;
import com.tibco.xpd.processwidget.popup.actions.ResetToDefaultFormat;
import com.tibco.xpd.processwidget.popup.actions.RestoreFactoryDefaultFormat;
import com.tibco.xpd.processwidget.popup.actions.SetAsDefaultFormatForType;
import com.tibco.xpd.processwidget.popup.actions.SetTaskIconAction;
import com.tibco.xpd.processwidget.properties.AbstractWidgetPropertiesSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Activity visual section
 * 
 * @author wzurek
 */
public class DiagramItemVisualisationSection extends
        AbstractWidgetPropertiesSection implements IPropertyChangeListener {

    private ColorSelector fillColorSelector;

    private ColorSelector lineColorSelector;

    private Button resetToDefault;

    private Button setAsDefault;

    private Button restoreFactoryDefault;

    private Composite taskIconControls;

    private Button browseTaskIcon;

    private Text taskIconPath;

    private Composite root;

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout(2, false));

        GridData gd;
        GridLayout gl;
        Composite cmp;

        Label lineColorLabel =
                toolkit
                        .createLabel(root,
                                Messages.DiagramItemVisualisationSection_LineColor_label);
        cmp = toolkit.createComposite(root);
        cmp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        gl = new GridLayout(2, false);
        gl.marginTop = 0;
        gl.marginBottom = 0;
        gl.marginHeight = 0;
        cmp.setLayout(gl);
        lineColorSelector = new ColorSelector(cmp);
        lineColorSelector.addListener(this);
        toolkit.paintBordersFor(cmp);

        Label fillColorLabel =
                toolkit.createLabel(root,
                        Messages.ActivityVisualSection_COLOR,
                        SWT.NONE);
        cmp = toolkit.createComposite(root);
        cmp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        gl = new GridLayout(2, false);
        gl.marginTop = 0;
        gl.marginBottom = 0;
        gl.marginHeight = 0;
        cmp.setLayout(gl);
        fillColorSelector = new ColorSelector(cmp);
        fillColorSelector.addListener(this);
        toolkit.paintBordersFor(cmp);

        taskIconControls = toolkit.createComposite(root);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 2;
        taskIconControls.setLayoutData(gd);
        gl = new GridLayout(3, false);
        gl.marginWidth = gl.marginHeight = 0;
        taskIconControls.setLayout(gl);

        Label taskIconLabel =
                toolkit
                        .createLabel(taskIconControls,
                                Messages.DiagramItemVisualisationSection_TaskIcon_label);

        taskIconPath = toolkit.createText(taskIconControls, "", SWT.READ_ONLY); //$NON-NLS-1$
        gd = new GridData();
        gd.widthHint = 200;
        taskIconPath.setLayoutData(gd);

        browseTaskIcon =
                toolkit.createButton(taskIconControls, "...", SWT.PUSH); //$NON-NLS-1$
        browseTaskIcon.setLayoutData(new GridData());
        manageControl(browseTaskIcon);

        Composite fill1 = toolkit.createComposite(root);
        fill1.setLayoutData(new GridData());

        cmp = toolkit.createComposite(root);
        cmp.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL));
        cmp.setLayout(new GridLayout(1, false));

        resetToDefault =
                toolkit
                        .createButton(cmp,
                                Messages.DiagramItemVisualisationSection_ResetToDefaultFormat_label,
                                SWT.PUSH);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        resetToDefault.setLayoutData(gd);
        manageControl(resetToDefault);

        setAsDefault =
                toolkit
                        .createButton(cmp,
                                Messages.DiagramItemVisualisationSection_SetAsDefault_button,
                                SWT.PUSH);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        setAsDefault.setLayoutData(gd);
        manageControl(setAsDefault);

        restoreFactoryDefault =
                toolkit
                        .createButton(cmp,
                                Messages.DiagramItemVisualisationSection_RestoreFactory_button,
                                SWT.PUSH);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        restoreFactoryDefault.setLayoutData(gd);
        manageControl(restoreFactoryDefault);

        List<Control> hdrs = new ArrayList<Control>();
        hdrs.add(lineColorLabel);
        hdrs.add(fillColorLabel);
        hdrs.add(taskIconLabel);
        hdrs.add(fill1);

        Map<Control, Integer> adjustments = new HashMap<Control, Integer>();
        adjustments.put(taskIconLabel, -5);
        setSameGridDataWidth(hdrs, adjustments);

        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        Command cmd = null;

        ModelAdapterEditPart ep = getInput();

        if (getInput().getModelAdapter() instanceof GraphicalColorAdapter) {
            BaseProcessAdapter baseAdapter = getInput().getModelAdapter();
            GraphicalColorAdapter adp =
                    (GraphicalColorAdapter) getInput().getModelAdapter();

            ProcessWidgetColors colors =
                    ProcessWidgetColors.getInstance(getInput()
                            .getModelAdapter());

            if (obj == setAsDefault) {
                SetAsDefaultFormatForType.setAsDefaultFormatForType(ep);

            } else if (obj == restoreFactoryDefault) {
                cmd =
                        RestoreFactoryDefaultFormat
                                .getRestoreFactoryDefaultsCommand(getEditingDomain(),
                                        Collections.singletonList(ep));

            } else if (obj == resetToDefault) {
                cmd =
                        ResetToDefaultFormat
                                .getResetToDefaultFormatCommand(getEditingDomain(),
                                        Collections.singletonList(ep));
            } else if (obj == browseTaskIcon && ep instanceof TaskEditPart) {
                cmd =
                        SetTaskIconAction.pickTaskIcon(getEditingDomain(),
                                Collections.singletonList((TaskEditPart) ep));
            }
        }

        return cmd;
    }

    @Override
    protected void doRefresh() {
        // If controls have been disposed then unregister adapter
        if (fillColorSelector.getButton() == null
                || fillColorSelector.getButton().isDisposed()) {
            unregister();
            return;
        }

        boolean fillOk = false;
        boolean lineOk = false;

        ModelAdapterEditPart input = getInput();

        fillColorSelector.setEnabled(false);
        lineColorSelector.setEnabled(false);

        if (input.getModelAdapter() instanceof GraphicalColorAdapter) {
            resetToDefault.setEnabled(true);
            setAsDefault.setEnabled(true);
            restoreFactoryDefault.setEnabled(true);

            BaseProcessAdapter baseAdapter = getInput().getModelAdapter();
            GraphicalColorAdapter adapter = (GraphicalColorAdapter) baseAdapter;

            ProcessWidgetColors colors =
                    ProcessWidgetColors.getInstance(baseAdapter);

            WidgetRGB lineRGB =
                    colors.getGraphicalNodeColor(adapter, input
                            .getLineColorIDForPartType());

            lineColorSelector.setColorValue(lineRGB.getRGB());

            lineColorSelector.setEnabled(true);

            if (input instanceof BaseGraphicalEditPart) {

                WidgetRGB fillRGB =
                        colors.getGraphicalNodeColor(adapter, input
                                .getFillColorIDForPartType());
                if (fillRGB == null) {
                    fillRGB = new WidgetRGB(255, 255, 255);
                }
                fillColorSelector.setColorValue(fillRGB.getRGB());

                fillColorSelector.setEnabled(true);

            }

        } else {
            resetToDefault.setEnabled(false);
            setAsDefault.setEnabled(false);
            restoreFactoryDefault.setEnabled(false);
        }

        if (input instanceof TaskEditPart) {
            String iconPath =
                    ((TaskEditPart) input).getActivityAdapter().getTaskIcon();

            updateText(taskIconPath, iconPath);

            if (!taskIconControls.isVisible()) {
                GridData gd = new GridData(GridData.FILL_HORIZONTAL);
                gd.horizontalSpan = 2;
                taskIconControls.setLayoutData(gd);
                taskIconControls.setVisible(true);
                root.layout(true);
            }

        } else {
            updateText(taskIconPath, ""); //$NON-NLS-1$

            if (taskIconControls.isVisible()) {
                GridData gd = new GridData(GridData.FILL_HORIZONTAL);
                gd.horizontalSpan = 2;
                gd.heightHint = 1;
                taskIconControls.setLayoutData(gd);
                taskIconControls.setVisible(false);
                root.layout(true);
            }

        }

        return;
    }

    public void propertyChange(PropertyChangeEvent event) {
        EditingDomain ed = getEditingDomain();

        ModelAdapterEditPart input = getInput();

        if (input.getModelAdapter() instanceof GraphicalColorAdapter) {
            GraphicalColorAdapter adapter =
                    (GraphicalColorAdapter) getInput().getModelAdapter();

            if (adapter != null && ed != null) {
                WidgetRGB newWRGB = new WidgetRGB((RGB) event.getNewValue());
                String newColorStr = newWRGB.toString();

                Command cmd = null;
                if (event.getSource() == fillColorSelector) {
                    cmd = adapter.getSetFillColorCommand(ed, newColorStr);

                } else if (event.getSource() == lineColorSelector) {
                    cmd = adapter.getSetLineColorCommand(ed, newColorStr);

                }

                if (cmd != null) {
                    ed.getCommandStack().execute(cmd);
                }
            }
        }
    }

}
