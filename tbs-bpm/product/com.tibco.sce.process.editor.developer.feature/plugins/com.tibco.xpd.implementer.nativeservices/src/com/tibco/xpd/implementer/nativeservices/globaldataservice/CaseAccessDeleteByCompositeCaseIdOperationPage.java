/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.globaldataservice;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.implementer.nativeservices.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.util.ContentAssistText;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.CaseAccessOperationsType;
import com.tibco.xpd.xpdExtension.CompositeIdentifierType;
import com.tibco.xpd.xpdExtension.DeleteByCompositeIdentifiersType;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.ExternalReference;

/**
 * Delete Case Object by Composite Identifier operation properties section of
 * the Global Data Task.
 * 
 * @author njpatel
 */
public class CaseAccessDeleteByCompositeCaseIdOperationPage extends
        AbstractOperationPage<CaseAccessOperationsType> {

    private CaseIdAndFieldPathControls deleteCompositeIdControls;

    /*
     * The last case class selected in the case access section (used to
     * determine when to update the case identifier text controls for delete by
     * composite id
     */
    private Class lastSelectCaseAccessClass;

    private final EditingDomain editingDomain;

    /**
     * Delete Case Object by Composite Identifier operation properties section
     * of the Global Data Task.
     * 
     * @param editingDomain
     * @param section
     */
    public CaseAccessDeleteByCompositeCaseIdOperationPage(
            EditingDomain editingDomain, GlobalDataTaskServiceSection section) {
        super(Messages.CaseAccessDeleteByCompositeCaseIdOperationPage_label,
                section);
        this.editingDomain = editingDomain;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.globaldataservice.AbstractOperationPage#reset()
     * 
     */
    @Override
    public void reset() {
        lastSelectCaseAccessClass = null;
        super.reset();
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.globaldataservice.AbstractOperationPage#update(org.eclipse.emf.ecore.EObject)
     * 
     * @param opType
     */
    @Override
    public void update(CaseAccessOperationsType opType) {

        DeleteByCompositeIdentifiersType identifiersType =
                opType.getDeleteByCompositeIdentifiers();

        if (identifiersType != null) {
            /*
             * we create and update controls only if the user selects
             * "Delete by Composite identifiers" in the drop down
             */

            ExternalReference extref = opType.getCaseClassExternalReference();
            if (extref != null) {
                Class caseClass =
                        getReferencedClass(WorkingCopyUtil.getProjectFor(opType),
                                extref);

                if (caseClass != null && lastSelectCaseAccessClass != caseClass) {
                    lastSelectCaseAccessClass = caseClass;

                    List<Property> identifiers =
                            getCaseCompositeIdentifiers(caseClass);

                    if (deleteCompositeIdControls != null) {
                        // Set the input for this section first
                        deleteCompositeIdControls
                                .setCaseAccessOperationsType(opType);
                        // Clear the previously created controls for the case
                        // identifiers
                        deleteCompositeIdControls.reset();

                        // Recreate new controls for the new case identifiers
                        boolean isFirst = true;
                        for (int idx = 0; idx < identifiers.size(); idx++) {
                            deleteCompositeIdControls.addControl(identifiers
                                    .get(idx), isFirst);
                            isFirst = false;
                        }
                        deleteCompositeIdControls.update();

                        if (identifiersType != null) {
                            for (CompositeIdentifierType type : identifiersType
                                    .getCompositeIdentifier()) {
                                deleteCompositeIdControls.refreshValue(type);
                            }
                        }
                    }
                }
            } else {
                /*
                 * XPD-7648: Noticed a refresh issue when we switched between
                 * 'Delete by composite identifier' (with case class selected)
                 * and other types (with no case class selected), the decorator
                 * controls were not getting updated/refreshed.
                 */
                lastSelectCaseAccessClass = null;
            }
        } else {
            /*
             * XPD-6095: If the identifier type is not
             * "Delete by Composite Identifier" then reset the controls and also
             * set the lastSelectedCaseClass to null so that when the user next
             * time selects "Delete by Composite Identifier" the controls are
             * created.
             */
            lastSelectCaseAccessClass = null;
        }

        if (lastSelectCaseAccessClass == null) {
            /*
             * reset the controls
             */
            deleteCompositeIdControls.reset();
        }

    }

    /**
     * Get composite case identifier properties from the given case class.
     * 
     * @param caseClass
     * @return
     */
    private List<Property> getCaseCompositeIdentifiers(Class caseClass) {
        List<Property> caseIdentifiers = new ArrayList<Property>();
        /*
         * XPD-6028: Saket: caseClass.getOwnedAttribute(String, Type) doesn't
         * consider the attributes in super class. Hence we need to fetch all
         * the attributes to take into consideration the attributes from super
         * classes as well.
         */
        for (Property property : caseClass.getAllAttributes()) {
            if (BOMGlobalDataUtils.isCompositeCID(property)) {
                caseIdentifiers.add(property);
            }
        }

        return caseIdentifiers;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.globaldataservice.AbstractOperationPage#getCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, java.lang.Object)
     * 
     * @param editingDomain
     * @param opType
     * @param control
     * @return
     */
    @Override
    public Command getCommand(EditingDomain editingDomain,
            CaseAccessOperationsType opType, Object control) {
        // Nothing to do here - the controls here have their own handler.
        return null;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.globaldataservice.AbstractOperationPage#createPage(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    public Control createPage(Composite parent, XpdFormToolkit toolkit) {
        GridLayout gl;

        Composite delCompIdCtrlsContainer = toolkit.createComposite(parent);

        gl = new GridLayout(1, false);
        gl.marginWidth = 0;
        gl.marginHeight = 0;
        delCompIdCtrlsContainer.setLayout(gl);

        Control c =
                createWrappedDescriptionText(toolkit,
                        delCompIdCtrlsContainer,
                        Messages.GlobalDataTaskServiceSection_deleteCaseObjUsingCompositeCaseId_longdesc);
        c.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Composite deleteByCaseIdSubSection =
                toolkit.createComposite(delCompIdCtrlsContainer);
        deleteByCaseIdSubSection.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));

        gl = new GridLayout(2, false);
        gl.marginTop = gl.marginHeight;
        gl.marginWidth = 0;
        gl.marginHeight = 0;
        deleteByCaseIdSubSection.setLayout(gl);
        deleteCompositeIdControls =
                new CaseIdAndFieldPathControls(deleteByCaseIdSubSection,
                        toolkit);

        return delCompIdCtrlsContainer;
    }

    /**
     * Controls the dynamic part of the Delete Case Object by Composite
     * Identifiers section.
     * 
     * 
     * @author aallway
     * @since 13 Nov 2013
     */
    private class CaseIdAndFieldPathControls implements FocusListener {
        private Composite parent;

        private XpdFormToolkit toolkit;

        private static final String CASE_ID = "caseId"; //$NON-NLS-1$

        /**
         * Label and content assist control that represents a single case
         * identifier mapping controls.
         */
        private class ControlGroup {
            private Label cacDeleteCaseIdName;

            private ContentAssistText cacDeleteFieldPath;

            private void dispose() {
                cacDeleteCaseIdName.dispose();
                cacDeleteFieldPath.dispose();
            }
        }

        private final List<ControlGroup> controls;

        private CaseAccessOperationsType caseAccessOp;

        public CaseIdAndFieldPathControls(Composite parent,
                XpdFormToolkit toolkit) {
            this.parent = parent;
            this.toolkit = toolkit;
            this.controls = new ArrayList<ControlGroup>();
        }

        /**
         * Refresh the value of the given type in the UI.
         * 
         * @param type
         */
        public void refreshValue(CompositeIdentifierType type) {
            String identifiername = type.getIdentifiername();
            if (identifiername != null) {
                ControlGroup control = null;
                for (ControlGroup c : controls) {
                    if (identifiername.equals(c.cacDeleteFieldPath.getText()
                            .getData(CASE_ID))) {
                        control = c;
                        break;
                    }
                }
                if (control != null) {
                    updateText(control.cacDeleteFieldPath.getText(),
                            type.getFieldPath());
                }
            }
        }

        /**
         * Remove the previously created controls in this group.
         */
        public void reset() {
            for (ControlGroup control : controls) {
                control.cacDeleteFieldPath.getText().removeFocusListener(this);
                control.dispose();
            }
            controls.clear();
            parent.update();
        }

        /**
         * Updates the control to show the newly added control groups.
         */
        public void update() {
            parent.layout();
        }

        /**
         * Add a control for the given case id property.
         * 
         * @param caseIdProperty
         * @param first
         *            <code>true</code> when setting the first case id control
         *            (this controls the label)
         */
        public void addControl(Property caseIdProperty, boolean first) {
            String propertyLabel =
                    PrimitivesUtil.getDisplayLabel(caseIdProperty);
            String labText =
                    first ? String
                            .format(Messages.GlobalDataTaskServiceSection_whereCaseIdIs_label,
                                    propertyLabel)
                            : String.format(Messages.GlobalDataTaskServiceSection_andCaseIdValueIs_label,
                                    propertyLabel);

            ControlGroup controlGroup = new ControlGroup();
            controlGroup.cacDeleteCaseIdName =
                    toolkit.createLabel(parent, labText);
            controlGroup.cacDeleteCaseIdName.setLayoutData(new GridData());

            controlGroup.cacDeleteFieldPath =
                    createContentAssistText(parent,
                            toolkit,
                            new LocalDataProposalProvider(true));

            controlGroup.cacDeleteFieldPath.setLayoutData(new GridData(
                    GridData.FILL_HORIZONTAL));

            controlGroup.cacDeleteFieldPath.getText().setData(CASE_ID,
                    caseIdProperty.getName());

            /*
             * Use local listener to modify model rather than manage control as
             * this section is dynamic, i.e. controls are added and removed so
             * need to unregister listeners.
             */
            controlGroup.cacDeleteFieldPath.getText().addFocusListener(this);

            controls.add(controlGroup);
        }

        /**
         * Set the input of this section.
         * 
         * @param caseAccessOp
         */
        public void setCaseAccessOperationsType(
                CaseAccessOperationsType caseAccessOp) {
            this.caseAccessOp = caseAccessOp;
        }

        /**
         * @see org.eclipse.swt.events.FocusListener#focusGained(org.eclipse.swt.events.FocusEvent)
         * 
         * @param e
         */
        @Override
        public void focusGained(FocusEvent e) {
            // Do nothing
        }

        /**
         * @see org.eclipse.swt.events.FocusListener#focusLost(org.eclipse.swt.events.FocusEvent)
         * 
         * @param e
         */
        @Override
        public void focusLost(FocusEvent e) {
            /*
             * Update model with change
             */
            if (caseAccessOp != null) {
                Command cmd = null;
                DeleteByCompositeIdentifiersType identifiersType =
                        caseAccessOp.getDeleteByCompositeIdentifiers();

                if (identifiersType != null) {
                    EList<CompositeIdentifierType> compositeIdentifiers =
                            identifiersType.getCompositeIdentifier();

                    Widget widget = e.widget;
                    if (widget instanceof Text) {
                        String fieldPath = ((Text) widget).getText().trim();
                        String caseId = (String) widget.getData(CASE_ID);
                        if (caseId != null) {
                            CompositeIdentifierType type =
                                    findCompositeIdentifierType(caseId,
                                            compositeIdentifiers);

                            if (type != null) {
                                // Update the value if it has changed
                                if (!fieldPath.equals(type.getFieldPath())) {
                                    cmd =
                                            SetCommand
                                                    .create(editingDomain,
                                                            type,
                                                            XpdExtensionPackage.eINSTANCE
                                                                    .getCompositeIdentifierType_FieldPath(),
                                                            fieldPath);
                                }
                            } else {
                                type =
                                        XpdExtensionFactory.eINSTANCE
                                                .createCompositeIdentifierType();
                                type.setFieldPath(fieldPath);
                                type.setIdentifiername(caseId);

                                cmd =
                                        AddCommand
                                                .create(editingDomain,
                                                        identifiersType,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDeleteByCompositeIdentifiersType_CompositeIdentifier(),
                                                        type);
                            }
                        }
                    }
                }

                if (cmd != null) {
                    editingDomain.getCommandStack().execute(cmd);
                }
            }
        }

        /**
         * Find the identifier type with the given name from the list.
         * 
         * @param identName
         * @param types
         * @return
         */
        private CompositeIdentifierType findCompositeIdentifierType(
                String identName, EList<CompositeIdentifierType> types) {
            for (CompositeIdentifierType type : types) {
                if (identName.equals(type.getIdentifiername())) {
                    return type;
                }
            }
            return null;
        }
    }

}
