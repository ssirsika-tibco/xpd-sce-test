/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.event;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker.ISectionContentCreator;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Properties for the Associated Parameters, will be displayed for a Start and
 * Intermediate Event of trigger type None, and Message and for Receive tasks.
 * 
 * 
 * @author rsomayaj
 * 
 * 
 */
public abstract class AssociatedParameterSection extends
        AbstractFilteredTransactionalSection implements ISectionContentCreator {

    protected static final String ASSOCIATED_PARAMETER_SECTION_ID =
            "associatedParameterSectionId"; //$NON-NLS-1$

    protected AssociatedParameterTable assocParamTable;

    private ScrolledComposite scrolledContainer;

    private Composite root;

    private Composite tableContainer;

    private ExpandableSectionStacker sectionStacker;

    private Label tableLabel;

    private Button disableImplicitBtn;

    /**
     * @return <code>true</code> if DisableImplicitAssociation is set.
     */
    protected abstract boolean isDisableImplicitAssociation();

    /**
     * @param disableImplicitAssociation
     * @return Command to set the DisableImplicitAssociation (when setting
     *         Disable to <code>true</code> the explicit associated parameters
     *         should be removed).
     */
    protected abstract Command getSetDisableImplicitAssociationCommand(
            Boolean disableImplicitAssociation);

    protected abstract boolean showDisableImplicitButton();

    /**
     * @return the sectionStacker
     */
    public ExpandableSectionStacker getSectionStacker() {
        return sectionStacker;
    }

    public AssociatedParameterSection() {
        super(XpdExtensionPackage.eINSTANCE.getAssociatedParametersContainer());
    }

    public AssociatedParameterSection(EClass class1) {
        super(class1);
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {

        scrolledContainer = new ScrolledComposite(parent, SWT.V_SCROLL) {
            //
            // The normal scrolled composite layout has a VERY nastry habit of
            // always adding a bit the second time round which always seems to
            // push the buttons off the right edge (slightly).
            //
            // However, if we ask the children of the scrolled composite what
            // their preferred size is, it always is ok, so we'll use that
            // instead.
            @Override
            public Point computeSize(int wHint, int hHint, boolean changed) {

                Control[] children = getChildren();
                int maxPrefWidth = 0;
                for (int i = 0; i < children.length; i++) {
                    Control control = children[i];

                    Point s = control.computeSize(wHint, hHint, changed);

                    if (s.x > maxPrefWidth) {
                        maxPrefWidth = s.x;
                    }
                }

                Point sz = super.computeSize(wHint, hHint, changed);
                sz.x = maxPrefWidth;

                return sz;
            }

        };

        scrolledContainer.setExpandHorizontal(true);
        scrolledContainer.setExpandVertical(true);

        root = toolkit.createComposite(scrolledContainer);
        root.setLayout(new GridLayout(2, false));

        createCompositeAboveTable(root, toolkit);

        if (getSectionContainerType() == ContainerType.PROPERTYVIEW) {
            Composite expandables = toolkit.createComposite(root);
            GridData gd = new GridData(GridData.FILL_BOTH);
            gd.horizontalSpan = 2;
            gd.minimumWidth = 0;
            expandables.setLayoutData(gd);

            GridLayout gl = new GridLayout(1, false);
            expandables.setLayout(gl);

            sectionStacker = new ExpandableSectionStacker(getClass().getName());
            addStackerSections(sectionStacker);
            Control control =
                    sectionStacker.createExpandableSections(expandables,
                            toolkit,
                            this);

            GridData gd2 = new GridData(GridData.FILL_BOTH);
            control.setLayoutData(gd2);

        }

        scrolledContainer.setContent(root);

        Point prefSize = root.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        scrolledContainer.setMinSize(prefSize);

        setMinimumHeight(prefSize.y);

        return scrolledContainer;
    }

    /**
     * @param root2
     * @param toolkit
     */
    protected void createCompositeAboveTable(Composite root2,
            XpdFormToolkit toolkit) {
    }

    protected void addStackerSections(ExpandableSectionStacker stacker) {
        stacker.addSection(ASSOCIATED_PARAMETER_SECTION_ID,
                Messages.AssociatedParameterSection_ParametersSectionTitle,
                100,
                true,
                true);
    }

    @Override
    protected Command doGetCommand(Object obj) {
        if (obj == disableImplicitBtn) {
            return getSetDisableImplicitAssociationCommand(disableImplicitBtn
                    .getSelection());
        }
        return null;
    }

    @Override
    protected void doRefresh() {
        EObject input = getInput();
        if (input != null) {
            if (assocParamTable != null && assocParamTable.getViewer() != null) {
                assocParamTable.getViewer().cancelEditing();
                assocParamTable.getViewer().setInput(input);

                TableViewer tableViewer = assocParamTable.getTableViewer();
                TableColumn[] columns = tableViewer.getTable().getColumns();
                for (TableColumn tableColumn : columns) {
                    if (tableColumn
                            .getText()
                            .equals(Messages.AssociatedParamHandler_ParamNameHeader_label)
                            || tableColumn
                                    .getText()
                                    .equals(Messages.AssociatedParamHandler_ProcessDataName_label)) {
                        if (input instanceof Activity) {
                            Activity activity = (Activity) input;
                            String text = getParameterNameLabel(activity);
                            tableColumn.setText(text);
                            break;
                        }
                    }
                }
            }

            disableImplicitBtn.setSelection(isDisableImplicitAssociation());

            if (showDisableImplicitButton()) {
                if (!disableImplicitBtn.getVisible()) {
                    GridData gd = new GridData(GridData.FILL_HORIZONTAL);
                    disableImplicitBtn.setLayoutData(gd);
                    disableImplicitBtn.setVisible(true);
                    disableImplicitBtn.getParent().layout();
                }

            } else {
                if (disableImplicitBtn.getVisible()) {
                    GridData gd = new GridData(GridData.FILL_HORIZONTAL);
                    gd.heightHint = 0;
                    disableImplicitBtn.setLayoutData(gd);
                    disableImplicitBtn.setVisible(false);
                    disableImplicitBtn.getParent().layout();
                }
            }

            boolean enable = true;

            if (input instanceof Activity) {
                Activity activity = (Activity) input;

                if (ReplyActivityUtil.isReplyActivity(activity)) {
                    if (Xpdl2ModelUtil
                            .isGeneratedRequestActivity(ReplyActivityUtil
                                    .getRequestActivityForReplyActivity(activity))) {
                        enable = false;
                    }
                }
            }

            disableImplicitBtn.setEnabled(enable);

        }
    }

    private String getParameterNameLabel(Activity activity) {
        if (Xpdl2ModelUtil.isProcessAPIActivity(activity)) {
            return Messages.AssociatedParamHandler_ParamNameHeader_label;
        }
        return Messages.AssociatedParamHandler_ProcessDataName_label;
    }

    public void doRefreshTabs() {
        refreshTabs();
    }

    @Override
    public Control createExpandableSectionContent(String sectionId,
            Composite container, XpdFormToolkit toolkit) {
        Control control = null;
        if (ASSOCIATED_PARAMETER_SECTION_ID.equals(sectionId)) {

            tableContainer = toolkit.createComposite(container);
            GridLayout layout = new GridLayout(1, false);
            layout.marginHeight = 0;
            layout.marginWidth = 0;
            tableContainer.setLayout(layout);

            tableLabel =
                    toolkit.createLabel(tableContainer,
                            Messages.AssociatedParamHandler_SelectAssocData_longdesc);
            tableLabel.setForeground(ColorConstants.black);
            tableLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

            disableImplicitBtn =
                    toolkit.createButton(tableContainer,
                            Messages.AssociatedParameterSection_DisableImplicitAssoc_button,
                            SWT.CHECK);
            disableImplicitBtn.setLayoutData(new GridData(
                    GridData.FILL_HORIZONTAL));
            manageControl(disableImplicitBtn);

            assocParamTable =
                    new AssociatedParameterTable(tableContainer, toolkit,
                            getEditingDomain());
            assocParamTable.setLayoutData(new GridData(GridData.FILL_BOTH));

            control = tableContainer;

        }
        return control;
    }

    @Override
    public void expandableSectionStateChanged(String sectionId) {
        Point sz = root.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        scrolledContainer.setMinSize(sz);
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#canShowInWizard()
     * 
     * @return
     */
    @Override
    public boolean canShowInWizard() {
        return false;
    }

    /**
     * @return the tableLabel
     */
    public Label getTableLabel() {
        return tableLabel;
    }

    /**
     * Set the table label.
     * 
     * @param label
     */
    public void setTableLabel(String label) {
        if (label != null && tableLabel != null && !tableLabel.isDisposed()) {
            if (!label.equals(tableLabel.getText())) {
                tableLabel.setText(label);
                tableLabel
                        .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
                tableLabel.getParent().layout();
            }
        }
    }
}
