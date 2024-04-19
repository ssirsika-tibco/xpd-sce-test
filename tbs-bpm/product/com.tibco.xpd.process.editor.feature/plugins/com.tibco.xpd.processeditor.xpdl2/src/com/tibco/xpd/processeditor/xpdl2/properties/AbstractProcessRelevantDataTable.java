/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.ActivityManagerEvent;
import org.eclipse.ui.activities.IActivityManager;
import org.eclipse.ui.activities.IActivityManagerListener;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.analyst.resources.xpdl2.ReservedWords;
import com.tibco.xpd.analyst.resources.xpdl2.properties.general.BaseTypeSection;
import com.tibco.xpd.analyst.resources.xpdl2.properties.general.UIBasicTypes;
import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.bom.resources.ui.commonpicker.BOMTypeQuery;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.dataFields.DataFieldTable;
import com.tibco.xpd.processeditor.xpdl2.properties.general.TypeDeclarationPropertySection;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessRelevantDataUtil;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.BaseTableControl;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.resources.ui.picker.PickerTypeQuery;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdUtil;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeExtPointHelper;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeReference;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypesMergedInfo;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.Member;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Precision;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.Scale;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.ui.ComplexDataUIUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * AbstractProcessRelevantDataTable
 * 
 * 
 * @author bharge
 * @since 3.3 (19 Oct 2009)
 */
public abstract class AbstractProcessRelevantDataTable extends BaseTableControl {

    /**
     * @param parent
     * @param toolkit
     * @param b
     * @param object
     */
    public AbstractProcessRelevantDataTable(Composite parent,
            XpdToolkit toolkit, Object object, boolean b) {
        super(parent, toolkit, null, false);
    }

    protected Map<String, String> declaredTypeMap =
            new HashMap<String, String>();

    private NamedElement selected;

    private ProcessRelevantData object;

    /**
     * Get the input of this table.
     * 
     * @return
     */
    private EObject getInput() {
        return (EObject) (getViewer() != null ? getViewer().getInput() : null);
    }

    public String[] reloadDeclaredTypes() {
        Package pkg = null;
        String[] valuesArr = null;
        if (getInput() != null) {
            if (getInput() instanceof Package) {
                pkg = (Package) getInput();
            } else {
                pkg = Xpdl2ModelUtil.getPackage(getInput());
            }
        }
        if (pkg != null) {
            List<TypeDeclaration> typeDeclarations = pkg.getTypeDeclarations();
            if (typeDeclarations != null) {
                for (TypeDeclaration typeDeclaration : typeDeclarations) {
                    declaredTypeMap.put(typeDeclaration.getId(),
                            WorkingCopyUtil.getText(typeDeclaration));
                }
            }
        }
        Collection<String> values = declaredTypeMap.values();
        Iterator<String> iterator = values.iterator();
        valuesArr = new String[values.size()];
        for (int i = 0; iterator.hasNext(); i++) {
            valuesArr[i] = iterator.next();
        }
        return valuesArr;
    }

    /**
     * Create the data type model object according to the selection in the data
     * type selection cell drop down.
     * 
     * Sid ACE-1094 - moved from {@link ProcessRelevantDataUtil} as it should
     * have been here in the first place.
     * 
     * @param type
     * @return The DataType definition (with default configuration set up.
     */
    protected static DataType createNewDataType(String type) {
        if (type == null) {
            return null;
        }

        DataType dataType = null;


        if (type.equals(ProcessRelevantDataUtil.EXTERNAL_REFERENCE_TYPE)) {
            ExternalReference externalReference = Xpdl2Factory.eINSTANCE.createExternalReference();
            externalReference.setLocation("");//$NON-NLS-1$
            dataType = externalReference;
        } else if (ProcessRelevantDataUtil.CASE_REFERENCE_TYPE.equals(type)) {
            ExternalReference externalReference = Xpdl2Factory.eINSTANCE.createExternalReference();
            externalReference.setLocation("");//$NON-NLS-1$
            RecordType caseRefType = Xpdl2Factory.eINSTANCE.createRecordType();
            Member member = Xpdl2Factory.eINSTANCE.createMember();
            member.setExternalReference(externalReference);
            caseRefType.getMember().add(member);
            dataType = caseRefType;
        } else if (type.equals(ProcessRelevantDataUtil.TYPE_DECLARATION_TYPE)) {
            DeclaredType declaredType = Xpdl2Factory.eINSTANCE.createDeclaredType();
            declaredType.setTypeDeclarationId("");//$NON-NLS-1$
            dataType = declaredType;
            
		}
		/**
		 * ACE-7394 : Added the Void Type to the PSL Function return type.
		 */
		else if ((type.equals(ProcessRelevantDataUtil.VOID_REFERNCE_TYPE)))
		{
			return null;
        } else {
            /*
             * Sid ACE-1094 We now use UIBasicTypes for all basic types so creatign data type is
             * easy.
             */
            UIBasicTypes uiBasicTypes = UIBasicTypes.valueOf(type);
            
            if (uiBasicTypes != null) {
                return uiBasicTypes.cloneDefaultBasicType();
            }
        }
        return dataType;
    }

    protected class LabelColumn extends AbstractColumn {

        private final TextCellEditor editor;

        NamedElementCellEditorListener listener;

        public LabelColumn(EditingDomain editingDomain, ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.NONE,
                    Messages.PropertiesSection_DisplayNameLabel, 90);
            editor = new TextCellEditor((Composite) viewer.getControl());
            listener = new NamedElementCellEditorListener(editor);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getShowImage()
         */
        @Override
        protected boolean getShowImage() {
            return true;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getImage(java
         * .lang.Object)
         */
        @Override
        protected Image getImage(Object element) {
            final AdapterFactoryLabelProvider labelProvider;
            if (getInput() != null && element instanceof NamedElement) {
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(getInput());
                if (wc != null) {
                    labelProvider =
                            new AdapterFactoryLabelProvider(
                                    wc.getAdapterFactory());
                    return labelProvider.getImage(element);
                }
            }
            return super.getImage(element);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor
         * (java.lang.Object)
         */
        @Override
        protected CellEditor getCellEditor(Object element) {
            if (element instanceof TypeDeclaration
                    || element instanceof ProcessRelevantData
                    || element instanceof Participant) {
                editor.setValidator(new DisplayNameValidator());
                editor.addListener(listener);
                if (element instanceof ProcessRelevantData) {
                    object = (ProcessRelevantData) element;
                }
                return editor;
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand
         * (java.lang.Object, java.lang.Object)
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            Command cmd = null;
            if (element instanceof NamedElement && editor.isValueValid()) {
                NamedElement namedElement = (NamedElement) element;
                Object obj =
                        Xpdl2ModelUtil.getOtherAttribute(namedElement,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_DisplayName());

                if (obj == null
                        || (obj instanceof String && !((String) obj)
                                .equals(value))) {
                    cmd =
                            Xpdl2ModelUtil
                                    .getSetOtherAttributeCommand(getEditingDomain(),
                                            namedElement,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_DisplayName(),
                                            value);
                    /*
                     * XPD-5368: Saket- Ensuring that the implementations of
                     * AbstractColumn.getSetValueCommand() return the command
                     * and do not execute it.
                     */
                }
            }
            return cmd;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java
         * .lang.Object)
         */
        @Override
        protected String getText(Object element) {
            if (element instanceof NamedElement) {
                NamedElement namedElement = (NamedElement) element;
                selected = namedElement;
                String text =
                        (String) Xpdl2ModelUtil.getOtherAttribute(namedElement,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_DisplayName());
                return text != null ? text : ""; //$NON-NLS-1$
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getValueForEditor
         * (java.lang.Object)
         */
        @Override
        protected Object getValueForEditor(Object element) {
            return getText(element);
        }

    }

    class NamedElementCellEditorListener implements ICellEditorListener {
        private TextCellEditor editor;

        public NamedElementCellEditorListener(TextCellEditor editor) {
            this.editor = editor;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.ICellEditorListener#applyEditorValue()
         */
        @Override
        public void applyEditorValue() {
            editor.getControl().setToolTipText(null);
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.ICellEditorListener#cancelEditor()
         */
        @Override
        public void cancelEditor() {
            editor.getControl().setToolTipText(null);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.ICellEditorListener#editorValueChanged(
         * boolean, boolean)
         */
        @Override
        public void editorValueChanged(boolean oldValidState,
                boolean newValidState) {
            if (!newValidState) {
                editor.getControl().setToolTipText(editor.getErrorMessage());
                editor.getControl().setForeground(ColorConstants.red);

            } else {
                editor.getControl().setToolTipText(null);
                editor.getControl()
                        .setForeground(ColorConstants.listForeground);
            }
        }

    }

    protected NamedElement getNamedElement() {
        return selected;
    }

    class DisplayNameValidator implements ICellEditorValidator {

        @Override
        public String isValid(Object value) {
            String err = null;
            NamedElement named = getNamedElement();
            if (value instanceof String && named != null) {
                String newName = (String) value;
                err = verifyDisplayName(named, newName, true);
            }
            return err;
        }

    }

    class NameValidator implements ICellEditorValidator {

        @Override
        public String isValid(Object value) {
            String err = null;
            NamedElement named = getNamedElement();
            if (value instanceof String && named != null) {
                String newName = (String) value;
                if (null == err) {
                    err = verifyName(named, newName);
                }
            }
            return err;
        }

    }

    /**
     * @param text
     */
    private String verifyDisplayName(NamedElement element, String text,
            boolean updateName) {
        String err = null;
        String nameErr = null;
        boolean changed = false;
        if (updateName) {
            changed = namesMatch(element);
            nameErr = updateNameFromDisplayName(element, text);
        }

        EObject duplicate;
        if (text.length() == 0 && Xpdl2ModelUtil.shouldHaveDisplayName(element)) {
            err = Messages.NamedElementSection_LabelEmpty;
        } else if (!text.trim().equals(text)) {
            err = Messages.NamedElementSection_LeadingTrailingSpaces1;
        } else {
            if ((duplicate =
                    Xpdl2ModelUtil.getDuplicateDisplayNameObject(element
                            .eContainer(), element, text)) != null) {
                err = getDuplicateNameMessage(duplicate);
            }
        }
        if (err == null && changed && !(nameErr == null)) {
            String origErr = err;
            err = Messages.NamedElementSection_InvalidGeneratedNameMessage;
            if (origErr != null) {
                err += " (" + origErr + ")."; //$NON-NLS-1$ //$NON-NLS-2$
            }
        }
        return err;
    }

    protected boolean namesMatch(NamedElement element) {
        return element.getName().equals(NameUtil.getInternalName(Xpdl2ModelUtil
                .getDisplayName(element), !allowLeadingNumerics()));
    }

    protected String updateNameFromDisplayName(NamedElement element, String text) {
        String err = null;
        if (namesMatch(element)) {
            String nameText =
                    NameUtil.getInternalName(text, !allowLeadingNumerics());
            err = verifyName(element, nameText);
        }
        return err;
    }

    /**
     * Verify that the name text of the named element is valid and return the
     * error text if it's invalid (<code>null</code> otherwise).
     * 
     * @param element
     * @param nameText
     * 
     * @return The error text if it's invalid (<code>null</code> otherwise).
     */
    protected String verifyName(NamedElement element, String nameText) {
        String err = null;
        EObject duplicate;

        /*
         * XPD-6949: Saket: We don't care about whether the element container
         * should have a token name or not. We need to call
         * Xpdl2ModelUtil.shouldHaveTokenName() for the element itself.
         */
        if ((nameText == null || nameText.length() == 0)
                && (requiresTokenName(element))) {
            err = Messages.NamedElementSection_NameEmpty;
        } else if (!NameUtil.isValidName(nameText, allowLeadingNumerics())) {
            if (allowLeadingNumerics()) {
                err = Messages.NamedElementSection_invalidNameErrorMessage;
            } else {
                err =
                        Messages.NamedElementSection_invalidNameNumericErrorMessage;
            }
        } else if ((duplicate =
                Xpdl2ModelUtil.getDuplicateNameObject(element.eContainer(),
                        element,
                        nameText)) != null) {
            err = getDuplicateNameMessage(duplicate);
        } else {
            if (object instanceof ProcessRelevantData) {

                if (ReservedWords.isReservedWord(nameText)) {
                    err = Messages.NamedElementSection_NameReservedWord;

                } else {
                    /*
                     * Sid ACE-118 also prevent names with reserved prefixes.
                     */
                    String reservedPrefix = ReservedWords.getReservedPrefix(nameText);

                    if (reservedPrefix != null) {
                        err = String.format(
                                Messages.NamedElementSection__ReservedPrefix_longdesc,
                                nameText);
                    }
                }
            }
        }
        return err;
    }

    /**
     * @return <code>true</code> if we need to verify the name. Override this
     *         method in subclasses if we know that the element requires token
     *         name.
     */
    protected boolean requiresTokenName(EObject element) {

        return Xpdl2ModelUtil.shouldHaveTokenName(element);
    }

    protected String getDuplicateNameMessage(EObject duplicate) {
        return Messages.NamedElementSection_NameNotUnique;
    }

    protected boolean allowLeadingNumerics() {
        return true;
    }

    protected class NameColumn extends AbstractColumn {
        private final TextCellEditor editor;

		private String					columnInitialText		= "";

		private boolean					disableColumn			= false;

        NameColumnCellEditorListener listener;

        private TableActivityListener tableManagerListener = null;

		public NameColumn(EditingDomain editingDomain, ColumnViewer viewer, boolean disableColumn,
				String columnInitialText)
		{
			this(editingDomain, viewer);

			/**
			 * ACE-7394 : Introduced the option of columnInitialText, to set Initial Text to column instead of reading
			 * from model.
			 */
			this.columnInitialText = columnInitialText;
			this.disableColumn = disableColumn;

			/**
			 * ACE-7394 : Introduced the option of disable column, 
			 * and If column is disabled , clear the listener.
			 */
			if (disableColumn)
			{
				this.listener = null;
				getEditor().getControl().setEnabled(false);
			}
		}

        public NameColumn(EditingDomain editingDomain, ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.NONE,
                    Messages.NamedElementPropertiesSection_NameLabel, 80);
            editor = new TextCellEditor((Composite) viewer.getControl()) {
                /**
                 * Introduced this anonymous class to include a label inside a
                 * cell before the text part to show error marker icon when the
                 * value of name is being duplicated. The label is shown/hidden
                 * as required in the listener.
                 */

                /**
                 * @see org.eclipse.jface.viewers.TextCellEditor#createControl(org.eclipse.swt.widgets.Composite)
                 * 
                 * @param parent
                 * @return
                 */
                @Override
                protected Control createControl(Composite parent) {
                    /**
                     * create a composite control with a label to in
                     */
                    Composite subComposite = new Composite(parent, SWT.NONE);
                    FormLayout formlayout = new FormLayout();
                    subComposite.setLayout(formlayout);
                    Image image =
                            ProcessWidgetPlugin.getDefault().getImageRegistry()
                                    .get(ProcessWidgetConstants.IMG_ERROR_ICON);
                    Label label = new Label(subComposite, SWT.NONE);
                    label.setImage(image);
                    label.pack();
                    label.setVisible(false);
                    FormData formData = new FormData(0, 0);
                    formData.left = new FormAttachment(0);
                    label.setLayoutData(formData);

                    Control control = super.createControl(subComposite);
                    formData = new FormData(190, 20);
                    formData.left = new FormAttachment(label, 0);
                    control.setLayoutData(formData);

                    return subComposite;
                }
            };

            listener = new NameColumnCellEditorListener(editor);

            IActivityManager activityManager =
                    PlatformUI.getWorkbench().getActivitySupport()
                            .getActivityManager();
            if (activityManager != null) {
                tableManagerListener = new TableActivityListener(this);
                activityManager
                        .addActivityManagerListener(tableManagerListener);
            }

        }

		/**
		 * @return the editor
		 */
		public TextCellEditor getEditor()
		{
			return editor;
		}

        @Override
        public void dispose() {
            IActivityManager activityManager =
                    PlatformUI.getWorkbench().getActivitySupport()
                            .getActivityManager();
            if (activityManager != null) {
                activityManager
                        .removeActivityManagerListener(tableManagerListener);
            }
            super.dispose();
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor
         * (java.lang.Object)
         */
        @Override
        protected CellEditor getCellEditor(Object element) {

        	
			/**
			 * ACE-7394 : Introduced the option of disable column, so when column is disabled, the cell editor should
			 * return null to stop editing.
			 */
			if (disableColumn)
			{
				return null;
			}

            if (element instanceof TypeDeclaration
                    || element instanceof ProcessRelevantData
                    || element instanceof Participant) {
                editor.setValidator(new NameValidator());
				editor.addListener(listener);
                if (element instanceof ProcessRelevantData) {
                    object = (ProcessRelevantData) element;
                }
                return editor;
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand
         * (java.lang.Object, java.lang.Object)
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            CompoundCommand cmd = null;
            if (element instanceof NamedElement && editor.isValueValid()) {
                NamedElement namedElement = (NamedElement) element;
                if (namedElement.getName() != null
                        && !namedElement.getName().equals(value)) {
                    cmd = new CompoundCommand();

                    cmd.append(SetCommand.create(getEditingDomain(),
                            namedElement,
                            Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                            value));
                    /*
                     * XPD-5368: Saket- Ensuring that the implementations of
                     * AbstractColumn.getSetValueCommand() return the command
                     * and do not execute it.
                     */
                }
            }
            return cmd;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java
         * .lang.Object)
         */
        @Override
        protected String getText(Object element) {
            if (element instanceof NamedElement) {
                NamedElement namedElement = (NamedElement) element;
                selected = namedElement;

				/**
				 * ACE-7394 : Introduced the option of columnInitialText, so when ever columnInitialText is set return
				 * it instead of reading from model.
				 */
				return (columnInitialText != null && columnInitialText != "") ? columnInitialText
						: namedElement.getName();
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getValueForEditor
         * (java.lang.Object)
         */
        @Override
        protected Object getValueForEditor(Object element) {
            return getText(element);
        }

		/*
		 * ACE-7394 : Chaitanya For PSL Functions Tables
		 * 
		 * 1. Parameters Table (i.e. PslFunctionParameterTable.java )
		 * 2. Return Type Table (i.e. PslFunctionReturnTypeTable.java )
		 * 
		 * we decided to show the type icons for the Name Column, so added method for getImage.
		 * 
		 * (non-Javadoc)
		 * 
		 * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getImage(java .lang.Object)
		 */
		@Override
		protected Image getImage(Object element)
		{
			final AdapterFactoryLabelProvider labelProvider;

			// When Type set to void type (no return) do not return image.
			if (getInput() != null && element instanceof ProcessRelevantData)
			{
				ProcessRelevantData processRelevantData = (ProcessRelevantData) element;

				if (((ProcessRelevantData) element).getDataType() == null)
				{
					return null;
				}
			}

			if (getInput() != null && element instanceof NamedElement)
			{
				WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(getInput());
				if (wc != null)
				{
					labelProvider = new AdapterFactoryLabelProvider(wc.getAdapterFactory());
					return labelProvider.getImage(element);
				}
			}

			return super.getImage(element);
		}

        class NameColumnCellEditorListener implements ICellEditorListener {
            private TextCellEditor editor;

            public NameColumnCellEditorListener(TextCellEditor editor) {
                this.editor = editor;
            }

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.jface.viewers.ICellEditorListener#applyEditorValue()
             */
            @Override
            public void applyEditorValue() {
                Composite composite = (Composite) editor.getControl();
                Control[] control = composite.getChildren();
                Label label = (Label) control[0];
                Text text = (Text) control[1];
                label.setVisible(false);
                label.setToolTipText(null);
                text.setToolTipText(null);
                text.setForeground(ColorConstants.listForeground);
            }

            /*
             * (non-Javadoc)
             * 
             * @see org.eclipse.jface.viewers.ICellEditorListener#cancelEditor()
             */
            @Override
            public void cancelEditor() {
                Composite composite = (Composite) editor.getControl();
                Control[] control = composite.getChildren();
                Label label = (Label) control[0];
                Text text = (Text) control[1];
                label.setVisible(false);
                label.setToolTipText(null);
                text.setToolTipText(null);
                text.setForeground(ColorConstants.listForeground);
            }

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.jface.viewers.ICellEditorListener#editorValueChanged(
             * boolean, boolean)
             */
            @Override
            public void editorValueChanged(boolean oldValidState,
                    boolean newValidState) {
                Composite composite = (Composite) editor.getControl();
                Control[] control = composite.getChildren();
                Label label = (Label) control[0];
                Text text = (Text) control[1];
                if (!newValidState) {
                    /**
                     * If the new value of the cell conflicts with existing data
                     * field name, show error icon and highlight the name to
                     * indicate the user of the duplicate value
                     */
                    label.setToolTipText(editor.getErrorMessage());
                    FormData formData = new FormData(8, 20);
                    formData.left = new FormAttachment(0);
                    label.setLayoutData(formData);
                    label.setVisible(true);
                    text.setToolTipText(editor.getErrorMessage());
                    text.setForeground(ColorConstants.red);
                    formData = new FormData(190, 20);
                    formData.left = new FormAttachment(label, 0);
                    text.setLayoutData(formData);
                } else {
                    /**
                     * reset the controls if the new value is valid
                     */
                    label.setToolTipText(null);
                    FormData formData = new FormData(0, 0);
                    formData.left = new FormAttachment(0);
                    label.setLayoutData(formData);
                    text.setToolTipText(null);
                    label.setVisible(false);
                    text.setForeground(ColorConstants.listForeground);
                    formData = new FormData(190, 20);
                    formData.left = new FormAttachment(label, 0);
                    text.setLayoutData(formData);
                }
                composite.layout(composite.getChildren());
            }
        }
    }

    class TableActivityListener implements IActivityManagerListener {
        private NameColumn nameColumn;

        /**
         * @param nameColumn
         */
        public TableActivityListener(NameColumn nameColumn) {
            this.nameColumn = nameColumn;
        }

        @Override
        public void activityManagerChanged(
                ActivityManagerEvent activityManagerEvent) {
            // if developer activity is enabled show name column
            // else hide name column
            if (CapabilityUtil.isDeveloperActivityEnabled()) {
                setVisible(nameColumn, true);
            } else {
                setVisible(nameColumn, false);
            }
        }

        private void setVisible(AbstractColumn column, boolean visible) {
            if (column != null
                    && column.getColumn() instanceof TableViewerColumn) {
                TableColumn theColumn =
                        ((TableViewerColumn) column.getColumn()).getColumn();
                if (theColumn != null) {
                    if (visible) {
                        theColumn.setWidth(column.getInitialWidth());
                        theColumn.setResizable(true);
                    } else {
                        theColumn.setWidth(0);
                        theColumn.setResizable(false);
                    }
                }
            }
        }
    }
    
	/**
	 * ACE-7966 : Chaitanya Add Parameter description editing facility for PSL Parameter table. </br>
	 * 
	 * Class for representating {@link Description} object in {@link DataFieldTable} as column.
	 *
	 * @author cbabar
	 * @since Mar 27, 2024
	 */
	protected class DescriptionColumn extends AbstractColumn
	{
		private final TextCellEditor		editor;


		public DescriptionColumn(EditingDomain editingDomain, ColumnViewer viewer)
		{
			super(editingDomain, viewer, SWT.NONE, Messages.NamedElementPropertiesSection_DescriptionLabel, 80);
			editor = new TextCellEditor((Composite) viewer.getControl());
		}

		/**
		 * @return the editor
		 */
		public TextCellEditor getEditor()
		{
			return editor;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor (java.lang.Object)
		 */
		@Override
		protected CellEditor getCellEditor(Object element)
		{
				if (element instanceof ProcessRelevantData)
				{
					object = (ProcessRelevantData) element;
					return editor;
				}

			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand (java.lang.Object,
		 * java.lang.Object)
		 */
		@Override
		protected Command getSetValueCommand(Object element, Object value)
		{
			CompoundCommand cmd = null;
			if (element instanceof ProcessRelevantData)
			{
				ProcessRelevantData prd = (ProcessRelevantData) element;
				Description descriptionElement = prd.getDescription();

				String currentValue = (String) value; // i.e. 'value' represents currentValue
				String previousValue = descriptionElement != null ? descriptionElement.getValue() : null;

				Description newDescriptionElement = null;

				/**
				 * Check whether the value has changed from null/empty to not null/empty OR value has changed
				 * to create a new description object.
				 */
				if (!XpdUtil.safeEquals(previousValue, currentValue))
				{
					newDescriptionElement = createNewDescription(currentValue);
					cmd = new CompoundCommand();
					cmd.append(SetCommand.create(getEditingDomain(), prd,
							Xpdl2Package.eINSTANCE.getDescribedElement_Description(), newDescriptionElement));
				}


			}
			return cmd;
		}


		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java .lang.Object)
		 */
		@Override
		protected String getText(Object element)
		{
			if (element instanceof ProcessRelevantData)
			{
				ProcessRelevantData prd = (ProcessRelevantData) element;
				Description descriptionElement = prd.getDescription();
				String desc = descriptionElement != null ? descriptionElement.getValue() : null;
				return desc != null ? desc : ""; //$NON-NLS-1$
			}
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getValueForEditor (java.lang.Object)
		 */
		@Override
		protected Object getValueForEditor(Object element)
		{
			return getText(element);
		}

		/**
		 * Function to create a new description object {@link Description} from the passed value.
		 * 
		 * @param value
		 * @return Returns the newely created description object {@link Description} from the passed value or else
		 *         returns null if passed value is null.
		 */
		private Description createNewDescription(String value)
		{
			if (value == null)
			{
				return null;
			}
			Description description = Xpdl2Factory.eINSTANCE.createDescription();
			description.setValue(value);

			return description;
		}
	}


    protected abstract class AbstractTypeColumn extends AbstractColumn {
        private ComboBoxViewerCellEditor editor;

        Map<String, String> typeNameMap = new LinkedHashMap<String, String>();

        public AbstractTypeColumn(EditingDomain editingDomain,
                ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.NONE,
                    Messages.DataFieldsSection_TypeColumn_label, 60);

            Map<String, String> typeNameMap = getTypes();

            Object[] valuesArr = typeNameMap.values().toArray();
            if (valuesArr != null) {
                String[] strValuesArr = new String[valuesArr.length];
                for (int i = 0; i < valuesArr.length; i++) {
                    strValuesArr[i] = (String) valuesArr[i];
                }


				/**
				 * ACE-7394 : Chaitanya : Sorted the list of Types present under Type Coloumn drop down.
				 * 
				 * 
				 * Ideally it was expected to already haven been sorted, but since as part of ACE-7394 we have added new
				 * Void Type return, just to make sure the list of Types still remains alphabetically sorted.
				 */
				Arrays.sort(strValuesArr);

                /*
                 * XPD-6789: Saket: This editor should be read only.
                 */
                editor =
                        new ComboBoxViewerCellEditor(
                                (Composite) viewer.getControl(), SWT.READ_ONLY);
                editor.setContenProvider(new ArrayContentProvider());
                editor.setLabelProvider(new LabelProvider());
                editor.setInput(strValuesArr);
            }
        }

        /**
         * @return
         */
		protected Map<String, String> getTypes()
		{
            // Add basic types to the combo, NOTE: If new one added, order
            // alphabetically
            String typeName;
            String typeLit;

            // ***********************************************************
            // NOTE: THESE TYPE NAMES ARE DELIBERATLY NON TRANSLATEABLE
            // It is currently intentional that all languages use the same type
            // name
            // terminology.
            // Sid / Tim 14/01/2009
            // ***********************************************************

            /*
             * XPD-7263: The human readable names(for basic types) are moved to
             * ProcessDataUtil.getBasicTypeLabel() so that they can be
             * consistently used at all places.
             */
            /*
             * Sid ACE-1094 - use UIBasicTypes so that we can distinguish betwen
             * fixed and floating point numbers.
             * 
             * Also, re-ordered the list into something more sensible
             * (alphabetic basic types - then with BOM/CaseRef/TypeDecl tagged
             * on)
             */

            typeNameMap.put(UIBasicTypes.Boolean.name(),
                    ProcessDataUtil
                            .getBasicTypeLabel(UIBasicTypes.Boolean.getDefaultBasicType()));

            typeNameMap.put(UIBasicTypes.Date.name(),
                    ProcessDataUtil
                            .getBasicTypeLabel(UIBasicTypes.Date.getDefaultBasicType()));

            typeNameMap.put(UIBasicTypes.DateTime.name(),
                    ProcessDataUtil.getBasicTypeLabel(UIBasicTypes.DateTime.getDefaultBasicType()));

            typeNameMap.put(UIBasicTypes.FixedPointNumber.name(),
                    ProcessDataUtil
                            .getBasicTypeLabel(UIBasicTypes.FixedPointNumber.getDefaultBasicType()));

            typeNameMap.put(UIBasicTypes.FloatingPointNumber.name(),
                    ProcessDataUtil
                            .getBasicTypeLabel(UIBasicTypes.FloatingPointNumber.getDefaultBasicType()));


            /*
             * Sid ACE-484 - suppress Integer type for ACE.
             */
            if (!BaseTypeSection.suppressAceUnsupportedTypes) {
                typeNameMap.put(UIBasicTypes.Integer.name(),
                        ProcessDataUtil.getBasicTypeLabel(UIBasicTypes.Integer.getDefaultBasicType()));
            }


            typeNameMap.put(UIBasicTypes.Performer.name(),
                    ProcessDataUtil.getBasicTypeLabel(UIBasicTypes.Performer.getDefaultBasicType()));


            typeNameMap.put(UIBasicTypes.String.name(),
                    ProcessDataUtil.getBasicTypeLabel(UIBasicTypes.String.getDefaultBasicType()));

            typeNameMap.put(UIBasicTypes.Time.name(),
                    ProcessDataUtil
                            .getBasicTypeLabel(UIBasicTypes.Time.getDefaultBasicType()));

            /* Sid ACE-1192 - added URI data type. */
            typeNameMap.put(UIBasicTypes.URI.name(),
                    ProcessDataUtil.getBasicTypeLabel(UIBasicTypes.URI.getDefaultBasicType()));

            typeName = "BOM Type"; //$NON-NLS-1$
            typeLit = ProcessRelevantDataUtil.EXTERNAL_REFERENCE_TYPE;
            typeNameMap.put(typeLit, typeName);

            typeName = "Case Ref Type"; //$NON-NLS-1$
            typeLit = ProcessRelevantDataUtil.CASE_REFERENCE_TYPE;
            typeNameMap.put(typeLit, typeName);

            typeName = "Type Declaration"; //$NON-NLS-1$
            typeLit = ProcessRelevantDataUtil.TYPE_DECLARATION_TYPE;
            typeNameMap.put(typeLit, typeName);

            // ***********************************************************
            // NOTE: THE ABOVE TYPE NAMES ARE DELIBERATLY NON TRANSLATEABLE
            // It is currently intentional that all languages use the same type
            // name
            // terminology.
            // Sid / Tim 14/01/2009
            // ***********************************************************

            return typeNameMap;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor
         * (java.lang.Object)
         */
        @Override
        protected CellEditor getCellEditor(Object element) {
            if (element instanceof TypeDeclaration
                    || element instanceof ProcessRelevantData) {
                return editor;
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java
         * .lang.Object)
         */
        @Override
        protected String getText(Object element) {
            String text = null;
            if (element instanceof TypeDeclaration) {
                TypeDeclaration typeDeclaration = (TypeDeclaration) element;
                if (typeDeclaration.getBasicType() != null) {
                    BasicType basicType = typeDeclaration.getBasicType();
                    text = ProcessDataUtil.getBasicTypeLabel(basicType);

                } else if (typeDeclaration.getExternalReference() != null) {
                    text =
                            getTypeName(ProcessRelevantDataUtil.EXTERNAL_REFERENCE_TYPE);
                } else if (typeDeclaration.getDeclaredType() != null) {
                    text =
                            getTypeName(ProcessRelevantDataUtil.TYPE_DECLARATION_TYPE);

                } else if (typeDeclaration.getRecordType() != null) {
                    /*
                     * Sid ACE-1094 - didn't used to handled case ref's for type
                     * declaration.
                     */
                    text = getTypeName(ProcessRelevantDataUtil.CASE_REFERENCE_TYPE);

                }
            }
            if (element instanceof ProcessRelevantData) {
                ProcessRelevantData prd = (ProcessRelevantData) element;
                if (prd.getDataType() instanceof BasicType) {
                    BasicType basicType = (BasicType) prd.getDataType();
                    text = ProcessDataUtil.getBasicTypeLabel(basicType);

                } else if (prd.getDataType() instanceof ExternalReference) {
                    text =
                            getTypeName(ProcessRelevantDataUtil.EXTERNAL_REFERENCE_TYPE);
                } else if (prd.getDataType() instanceof RecordType) {
                    text =
                            getTypeName(ProcessRelevantDataUtil.CASE_REFERENCE_TYPE);
                } else if (prd.getDataType() instanceof DeclaredType) {
                    text =
                            getTypeName(ProcessRelevantDataUtil.TYPE_DECLARATION_TYPE);
                }
				/**
				 * ACE-7394 : Added the Void Type to the PSL Function return type.
				 */
				else if (prd.getDataType() == null)
				{
					text = getTypeName(ProcessRelevantDataUtil.VOID_REFERNCE_TYPE);
				}
            }
            return text != null ? text : ""; //$NON-NLS-1$
        }

        private String getTypeName(String id) {
            String typeName = null;
            if (typeNameMap != null) {
                typeName = typeNameMap.get(id);
            }
            return typeName;
        }

        protected int getTypeIndex(String type) {
            int typeIndex = 0;
            if (typeNameMap != null) {
                Collection<String> values = typeNameMap.values();
                if (values.contains(type)) {
                    for (Iterator<String> iterator = values.iterator(); iterator
                            .hasNext();) {
                        String typeName = iterator.next();
                        if (typeName != null && typeName.equals(type)) {
                            break;
                        }
                        typeIndex++;
                    }
                }
            }
            return typeIndex;
        }

        protected String getTypeValue(int index) {
            String typeValue = null;
            if (typeNameMap != null) {
                ArrayList<String> values =
                        new ArrayList<String>(typeNameMap.keySet());
                typeValue = values.get(index);
            }
            return typeValue;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getValueForEditor
         * (java.lang.Object)
         */
        @Override
        protected Object getValueForEditor(Object element) {
            return getText(element);
        }

    }

    protected abstract class AbstractLengthColumn extends AbstractColumn {
        private final TextCellEditor editor;

        public AbstractLengthColumn(EditingDomain editingDomain,
                ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.NONE,
                    Messages.DataFieldsSection_lengthColumn_label, 50);
            editor = new TextCellEditor((Composite) viewer.getControl());
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor
         * (java.lang.Object)
         */
        @Override
        protected CellEditor getCellEditor(Object element) {
            if (element instanceof TypeDeclaration
                    || element instanceof ProcessRelevantData) {
                return editor;
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java
         * .lang.Object)
         */
        @Override
        protected String getText(Object element) {
            String text = null;
            if (element instanceof TypeDeclaration) {
                TypeDeclaration typeDeclaration = (TypeDeclaration) element;
                if (typeDeclaration.getBasicType() != null) {
                    BasicType basicType = typeDeclaration.getBasicType();

                    /*
                     * Sid ACE-192 Don't allow length set on URI fields.
                     */
                    UIBasicTypes uiBasicType = UIBasicTypes.fromBasicType(basicType);

                    if (UIBasicTypes.String.equals(uiBasicType)) {
                        Length length = basicType.getLength();
                        if (length != null) {
                            text = length.getValue();
                        }
                    } else {
                        Precision precision = basicType.getPrecision();
                        if (precision != null) {
                            text = Short.toString(precision.getValue());
                        }
                    }
                }
            }
            if (element instanceof ProcessRelevantData) {
                ProcessRelevantData prd = (ProcessRelevantData) element;
                if (prd.getDataType() instanceof BasicType) {
                    BasicType basicType = (BasicType) prd.getDataType();
                    /*
                     * Sid ACE-192 Don't allow length set on URI fields.
                     */
                    UIBasicTypes uiBasicType = UIBasicTypes.fromBasicType(basicType);

                    if (UIBasicTypes.String.equals(uiBasicType)) {
                        Length length = basicType.getLength();
                        if (length != null) {
                            text = length.getValue();
                        } else {
                            text = ""; //$NON-NLS-1$
                        }
                    } else {
                        Precision precision = basicType.getPrecision();
                        if (precision != null) {
                            text = Short.toString(precision.getValue());
                        }
                    }
                }
            }
            if (null == text) {
                text =
                        com.tibco.xpd.xpdl2.edit.ui.internal.Messages.BaseTypeSection_NotApplicable_text;
            }
            return text;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getValueForEditor
         * (java.lang.Object)
         */
        @Override
        protected Object getValueForEditor(Object element) {
            return getText(element);
        }

    }

    protected abstract class AbstractDecimalPlacesColumn extends AbstractColumn {
        private final TextCellEditor editor;

        public AbstractDecimalPlacesColumn(EditingDomain editingDomain,
                ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.NONE,
                    Messages.DataFieldsSection_decimalPlacesColumn_label, 85);
            editor = new TextCellEditor((Composite) viewer.getControl());
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor
         * (java.lang.Object)
         */
        @Override
        protected CellEditor getCellEditor(Object element) {
            if (element instanceof TypeDeclaration
                    || element instanceof ProcessRelevantData) {
                return editor;
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java
         * .lang.Object)
         */
        @Override
        protected String getText(Object element) {
            String text = null;
            if (element instanceof TypeDeclaration) {
                TypeDeclaration typeDeclaration = (TypeDeclaration) element;
                if (typeDeclaration.getBasicType() != null) {
                    BasicType basicType = typeDeclaration.getBasicType();
                    Scale scale = basicType.getScale();
                    if (scale != null) {
                        text = Short.toString(scale.getValue());
                    }
                }
            }
            if (element instanceof ProcessRelevantData) {
                ProcessRelevantData prd = (ProcessRelevantData) element;
                if (prd.getDataType() instanceof BasicType) {
                    BasicType basicType = (BasicType) prd.getDataType();
                    Scale scale = basicType.getScale();
                    if (scale != null) {
                        text = Short.toString(scale.getValue());
                    }
                }
            }
            if (text == null) {
                text =
                        com.tibco.xpd.xpdl2.edit.ui.internal.Messages.BaseTypeSection_NotApplicable_text;
            }
            return text;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getValueForEditor
         * (java.lang.Object)
         */
        @Override
        protected Object getValueForEditor(Object element) {
            return getText(element);
        }

    }

    /**
     * @param text
     * @param externalReference
     * @param project
     * @return
     */
    protected String getComplextDataTypeName(String text,
            ExternalReference externalReference, IProject project) {
        ComplexDataTypesMergedInfo complexTypesInfo = getComplexTypesInfo();
        if (complexTypesInfo != null) {

            ComplexDataTypeReference complexDataTypeRef =
                    xpdl2RefToComplexDataTypeRef(externalReference);

            text =
                    complexTypesInfo
                            .getComplexDataTypeDescriptiveName(complexDataTypeRef,
                                    project);
        }

        return text;
    }

    /**
     * @return the complexTypesInfo
     */
    public ComplexDataTypesMergedInfo getComplexTypesInfo() {
        ComplexDataTypesMergedInfo _complexTypesInfo = null;
        if (_complexTypesInfo == null) {
            _complexTypesInfo =
                    ComplexDataTypeExtPointHelper
                            .getAllComplexDataTypesMergedInfo();
        }
        return _complexTypesInfo;
    }

    /**
     * Convert an xpdl2 External reference to a complex data type extension
     * point reference.
     * 
     * @param extRef
     * @return
     */
    private ComplexDataTypeReference xpdl2RefToComplexDataTypeRef(
            ExternalReference extRef) {

        String nameSpace = extRef.getNamespace();
        String location = extRef.getLocation();
        String xRef = extRef.getXref();

        // Must have at least some infop defined.
        int length = (nameSpace == null ? 0 : nameSpace.length());
        length += (location == null ? 0 : location.length());
        length += (xRef == null ? 0 : xRef.length());
        if (length == 0) {
            return null;
        }

        return new ComplexDataTypeReference(location, xRef, nameSpace);
    }

    protected class ExternalReferenceColumn extends AbstractColumn {
        private final ExternalRefPickerCellEditor editor;

        public ExternalReferenceColumn(EditingDomain editingDomain,
                ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.NONE,
                    Messages.DataFieldsSection_externalReferenceColumn_label,
                    90);
            editor =
                    new ExternalRefPickerCellEditor(
                            (Composite) viewer.getControl());
        }

        public ExternalReferenceColumn(EditingDomain editingDomain,
                ColumnViewer viewer, String heading, int width) {
            super(editingDomain, viewer, SWT.NONE,
                    Messages.DataFieldsSection_bomTypeColumn_label, 90);
            editor =
                    new ExternalRefPickerCellEditor(
                            (Composite) viewer.getControl());
        }

        /**
         * Set the BOM type filter employed by this data picker.
         * 
         * The default is BOMTypeQuery.CLASS_TYPE, BOMTypeQuery.PRIMITIVE_TYPE, BOMTypeQuery.ENUMERATION_TYPE,
         * BOMTypeQuery.CASE_CLASS_TYPE, BOMTypeQuery.GLOBAL_CLASS_TYPE
         * 
         * @param bomTypeFilter
         *            Set of string constants as defined in {@link BOMTypeQuery} of each type to appear on picker.
         */
        public void setBOMTypeFilter(String[] bomTypeFilter) {
            editor.setBOMTypeFilter(bomTypeFilter);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor
         * (java.lang.Object)
         */
        @Override
        protected CellEditor getCellEditor(Object element) {
            if (element instanceof TypeDeclaration
                    || element instanceof ProcessRelevantData) {
                return editor;
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand
         * (java.lang.Object, java.lang.Object)
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            CompoundCommand cmd = null;
            if (element != null) {
                if (value instanceof ComplexDataTypeReference) {
                    ComplexDataTypeReference complexDataTypeReference =
                            (ComplexDataTypeReference) value;
                    ExternalReference extReference =
                            Xpdl2Factory.eINSTANCE.createExternalReference();
                    extReference.setLocation(complexDataTypeReference
                            .getLocation());
                    extReference.setXref(complexDataTypeReference.getXRef());
                    extReference.setNamespace(complexDataTypeReference
                            .getNameSpace());
                    cmd = new CompoundCommand(); // Messages.DataFieldsSection_setDataTypeExternalRef_menu
                    if (element instanceof TypeDeclaration) {
                        TypeDeclaration typeDeclaration =
                                (TypeDeclaration) element;

                        cmd.append(TypeDeclarationPropertySection
                                .getClearTypeDeclarationTypeCommand(getEditingDomain(),
                                        typeDeclaration));

                        cmd.append(SetCommand
                                .create(getEditingDomain(),
                                        typeDeclaration,
                                        Xpdl2Package.eINSTANCE
                                                .getTypeDeclaration_ExternalReference(),
                                        extReference));
                    }
                    if (element instanceof ProcessRelevantData) {
                        ProcessRelevantData prd = (ProcessRelevantData) element;
                        cmd.append(SetCommand.create(getEditingDomain(),
                                prd,
                                Xpdl2Package.eINSTANCE
                                        .getProcessRelevantData_DataType(),
                                extReference));
                    }
                }
            }
            return cmd;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java
         * .lang.Object)
         */
        @Override
        protected String getText(Object element) {
            String text = null;
            if (element instanceof TypeDeclaration) {
                TypeDeclaration typeDeclaration = (TypeDeclaration) element;
                if (typeDeclaration.getExternalReference() != null) {
                    ExternalReference externalReference =
                            typeDeclaration.getExternalReference();
                    if (externalReference.getXref() == null) {
                        text = "";//$NON-NLS-1$
                    } else {
                        String location = externalReference.getLocation();
                        ComplexDataTypeReference complex =
                                new ComplexDataTypeReference(location,
                                        externalReference.getXref(),
                                        externalReference.getNamespace());
                        IProject project =
                                WorkingCopyUtil.getProjectFor(typeDeclaration);
                        Class clss =
                                ConceptUtil.getComplexDataTypeModel(complex,
                                        project);
                        // XPD-715: begin - it is not a class but an enumeration
                        text =
                                getComplextDataTypeName(text,
                                        externalReference,
                                        project);
                        // XPD-715: end
                        if (clss != null) {
                            text = clss.getName();
                        } else {
                            if (null == text || text.length() == 0)
                                text =
                                        com.tibco.xpd.xpdl2.edit.ui.internal.Messages.BaseTypeSection_UnresolvedReference;
                        }
                    }
                }
            }
            if (element instanceof ProcessRelevantData) {
                ProcessRelevantData prd = (ProcessRelevantData) element;
                if (prd.getDataType() instanceof ExternalReference) {
                    ExternalReference externalReference =
                            (ExternalReference) prd.getDataType();
                    if (externalReference.getXref() == null) {
                        text = "";//$NON-NLS-1$
                    } else {
                        String location = externalReference.getLocation();
                        ComplexDataTypeReference complex =
                                new ComplexDataTypeReference(location,
                                        externalReference.getXref(),
                                        externalReference.getNamespace());
                        IProject project = WorkingCopyUtil.getProjectFor(prd);
                        Class clss =
                                ConceptUtil.getComplexDataTypeModel(complex,
                                        project);
                        // XPD-715: begin - it is not a class but an enumeration
                        text =
                                getComplextDataTypeName(text,
                                        externalReference,
                                        project);
                        // XPD-715: end
                        if (clss != null) {
                            text = clss.getName();
                        } else {
                            if (null == text || text.length() == 0)
                                text =
                                        com.tibco.xpd.xpdl2.edit.ui.internal.Messages.BaseTypeSection_UnresolvedReference;
                        }
                    }
                }
            }
            if (text == null) {
                text =
                        com.tibco.xpd.xpdl2.edit.ui.internal.Messages.BaseTypeSection_NotApplicable_text;
            }
            return text;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getValueForEditor
         * (java.lang.Object)
         */
        @Override
        protected Object getValueForEditor(Object element) {
            return getText(element);
        }

    }

    /**
     * 
     * 
     * @author bharge
     * @since 30 Nov 2012
     */
    protected class CaseRefTypeColumn extends AbstractColumn {

        private final CaseOrGlobalRefPickerCellEditor editor;

        /**
         * @param editingDomain
         * @param viewer
         */
        public CaseRefTypeColumn(EditingDomain editingDomain,
                ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.NONE,
                    Messages.DataFieldsSection_caseReferenceColumn_label, 90);
            editor =
                    new CaseOrGlobalRefPickerCellEditor(
                            (Composite) viewer.getControl());

        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor
         * (java.lang.Object)
         */
        @Override
        protected CellEditor getCellEditor(Object element) {
            if (element instanceof TypeDeclaration
                    || element instanceof ProcessRelevantData) {
                return editor;
            }
            return null;
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessRelevantDataTable.ExternalReferenceColumn#getSetValueCommand(java.lang.Object,
         *      java.lang.Object)
         * 
         * @param element
         * @param value
         * @return
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {

            CompoundCommand cmd = null;
            if (null != element) {
                if (value instanceof ComplexDataTypeReference) {
                    ComplexDataTypeReference complexDataTypeReference =
                            (ComplexDataTypeReference) value;

                    ExternalReference extReference =
                            Xpdl2Factory.eINSTANCE.createExternalReference();
                    extReference.setLocation(complexDataTypeReference
                            .getLocation());
                    extReference.setXref(complexDataTypeReference.getXRef());
                    extReference.setNamespace(complexDataTypeReference
                            .getNameSpace());

                    Member member = Xpdl2Factory.eINSTANCE.createMember();
                    member.setExternalReference(extReference);

                    RecordType caseRefType =
                            Xpdl2Factory.eINSTANCE.createRecordType();
                    caseRefType.getMember().add(member);

                    cmd = new CompoundCommand();
                    if (element instanceof TypeDeclaration) {
                        TypeDeclaration typeDeclaration =
                                (TypeDeclaration) element;

						/*
						 * Sid ACE-7602 Found that setting case ref type on type declaration DIDN'T unset the current
						 * basic-type model. So fixed.
						 */
						cmd.append(TypeDeclarationPropertySection.getClearTypeDeclarationTypeCommand(getEditingDomain(),
								typeDeclaration));

                        cmd.append(SetCommand.create(getEditingDomain(),
                                typeDeclaration,
                                Xpdl2Package.eINSTANCE
                                        .getTypeDeclaration_RecordType(),
                                caseRefType));
                    }
                    if (element instanceof ProcessRelevantData) {
                        ProcessRelevantData prd = (ProcessRelevantData) element;
                        cmd.append(SetCommand.create(getEditingDomain(),
                                prd,
                                Xpdl2Package.eINSTANCE
                                        .getProcessRelevantData_DataType(),
                                caseRefType));
                    }
                }
            }
            return cmd;
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessRelevantDataTable.ExternalReferenceColumn#getText(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        protected String getText(Object element) {

            String text = null;
            if (element instanceof TypeDeclaration) {
                TypeDeclaration typeDeclaration = (TypeDeclaration) element;
                RecordType caseRefType = typeDeclaration.getRecordType();
                if (null != caseRefType) {

                    Member member = caseRefType.getMember().get(0);
                    ExternalReference externalReference =
                            member.getExternalReference();

                    if (externalReference.getXref() == null) {
                        text = "";//$NON-NLS-1$
                    } else {
                        String location = externalReference.getLocation();
                        ComplexDataTypeReference complex =
                                new ComplexDataTypeReference(location,
                                        externalReference.getXref(),
                                        externalReference.getNamespace());
                        IProject project =
                                WorkingCopyUtil.getProjectFor(typeDeclaration);
                        Class clss =
                                ConceptUtil.getComplexDataTypeModel(complex,
                                        project);
                        // XPD-715: begin - it is not a class but an enumeration
                        text =
                                getComplextDataTypeName(text,
                                        externalReference,
                                        project);
                        // XPD-715: end
                        if (clss != null) {
                            text = clss.getName();
                        } else {
                            if (null == text || text.length() == 0)
                                text =
                                        com.tibco.xpd.xpdl2.edit.ui.internal.Messages.BaseTypeSection_UnresolvedReference;
                        }
                    }
                }
            }
            if (element instanceof ProcessRelevantData) {
                ProcessRelevantData prd = (ProcessRelevantData) element;
                if (prd.getDataType() instanceof RecordType) {

                    RecordType caseRefType = (RecordType) prd.getDataType();
                    Member member = caseRefType.getMember().get(0);
                    ExternalReference externalReference =
                            member.getExternalReference();

                    if (externalReference.getXref() == null) {
                        text = "";//$NON-NLS-1$
                    } else {
                        String location = externalReference.getLocation();
                        ComplexDataTypeReference complex =
                                new ComplexDataTypeReference(location,
                                        externalReference.getXref(),
                                        externalReference.getNamespace());
                        IProject project = WorkingCopyUtil.getProjectFor(prd);
                        Class clss =
                                ConceptUtil.getComplexDataTypeModel(complex,
                                        project);
                        // XPD-715: begin - it is not a class but an enumeration
                        text =
                                getComplextDataTypeName(text,
                                        externalReference,
                                        project);
                        // XPD-715: end
                        if (clss != null) {
                            text = clss.getName();
                        } else {
                            if (null == text || text.length() == 0)
                                text =
                                        com.tibco.xpd.xpdl2.edit.ui.internal.Messages.BaseTypeSection_UnresolvedReference;
                        }
                    }
                }
            }
            if (text == null) {
                text =
                        com.tibco.xpd.xpdl2.edit.ui.internal.Messages.BaseTypeSection_NotApplicable_text;
            }
            return text;
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessRelevantDataTable.ExternalReferenceColumn#getValueForEditor(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        protected Object getValueForEditor(Object element) {
            return getText(element);
        }
    }

    /**
     * 
     * 
     * @author bharge
     * @since 3 Dec 2012
     */
    private class CaseOrGlobalRefPickerCellEditor extends
            ExternalRefPickerCellEditor {

        /**
         * @param parent
         */
        public CaseOrGlobalRefPickerCellEditor(Composite parent) {
            super(parent);
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessRelevantDataTable.ExternalRefPickerCellEditor#openDialogBox(org.eclipse.swt.widgets.Control)
         * 
         * @param cellEditorWindow
         * @return
         */
        @Override
        protected Object openDialogBox(Control cellEditorWindow) {

            Object result = null;
            ComplexDataTypeReference newComplexDataTypeRef = null;
            IProject project = WorkingCopyUtil.getProjectFor(getInput());

            PickerTypeQuery[] queries =
                    new PickerTypeQuery[] { new BOMTypeQuery(project,
                            BOMTypeQuery.CASE_CLASS_TYPE,
                            BOMTypeQuery.GLOBAL_CLASS_TYPE) };
            // XPD-3129:using project filter , restricts the picker to Classes
            // from BOMs in same Project only, which is not desired.
            IFilter[] filters = new IFilter[] {};

			/* Sid ACE-7602 setup current selection as initial selection. */
			Object initialSelection = null;
			Object firstElement = ((StructuredSelection) getViewer().getSelection()).getFirstElement();
			initialSelection = BasicTypeConverterFactory.INSTANCE.getBaseType(firstElement, false);

            result =
                    PickerService
                            .getInstance()
                            .openSinglePickerDialog(cellEditorWindow.getShell(),
                                    queries,
                                    null,
                                    null,
                                    null,
									filters, initialSelection);

            // XPD-3129: add project reference if required
            if (result instanceof Type
                    && ComplexDataUIUtil
                            .checkProjectDependencies(cellEditorWindow
                                    .getShell(),
                                    project,
                                    (Type) result,
                                    ((Type) result).getName(),
                                    com.tibco.xpd.xpdl2.edit.ui.internal.Messages.PickerUtil_pickGlobalType_label)) {
                Classifier classifier = (Classifier) result;
                ComplexDataTypesMergedInfo complexDataTypesMergedInfo =
                        getComplexTypesInfo();

                newComplexDataTypeRef =
                        ComplexDataUIUtil
                                .resolveSelectionToReference(classifier,
                                        project,
                                        complexDataTypesMergedInfo);
            }
            if (newComplexDataTypeRef != null) {
                result = newComplexDataTypeRef;
            }
            return result;

        }
    }

    private class ExternalRefPickerCellEditor extends DialogCellEditor {
        private ComplexDataTypesMergedInfo _complexTypesInfo = null;

        /* Sid ACE-5361 allow consumer to set filter. */
        private String[] bomTypeFilter = { BOMTypeQuery.CLASS_TYPE, BOMTypeQuery.PRIMITIVE_TYPE,
                BOMTypeQuery.ENUMERATION_TYPE, BOMTypeQuery.CASE_CLASS_TYPE, BOMTypeQuery.GLOBAL_CLASS_TYPE };

        public ExternalRefPickerCellEditor(Composite parent) {
            super(parent);
        }

        /**
         * Set the BOM type filter employed by this data picker.
         * 
         * The default is BOMTypeQuery.CLASS_TYPE, BOMTypeQuery.PRIMITIVE_TYPE, BOMTypeQuery.ENUMERATION_TYPE,
         * BOMTypeQuery.CASE_CLASS_TYPE, BOMTypeQuery.GLOBAL_CLASS_TYPE
         * 
         * @param bomTypeFilter
         *            Set of string constants as defined in {@link BOMTypeQuery} of each type to appear on picker.
         */
        public void setBOMTypeFilter(String[] bomTypeFilter) {
            this.bomTypeFilter = bomTypeFilter;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.DialogCellEditor#openDialogBox(org.eclipse
         * .swt.widgets.Control)
         */
        @Override
        protected Object openDialogBox(Control cellEditorWindow) {

            Object result = null;
            ComplexDataTypeReference newComplexDataTypeRef = null;
            IProject project = WorkingCopyUtil.getProjectFor(getInput());
            ComplexDataTypesMergedInfo complexTypesInfo = getComplexTypesInfo();
            
            /* Sid ACE-5361 allow consumer to set filter. */
            PickerTypeQuery[] queries = new PickerTypeQuery[] { new BOMTypeQuery(project, bomTypeFilter) };
            
            // XPD-3129:using project filter , restricts the picker to Classes
            // from BOMs in same Project only, which is not desired.
            IFilter[] filters = new IFilter[] {};

            /* Sid ACE-7602 setup current selection as initial selection. */
			Object initialSelection = null;
			Object firstElement = ((StructuredSelection) getViewer().getSelection()).getFirstElement();
			initialSelection = BasicTypeConverterFactory.INSTANCE.getBaseType(firstElement, false);

            result =
                    PickerService
                            .getInstance()
                            .openSinglePickerDialog(cellEditorWindow.getShell(),
                                    queries,
                                    null,
                                    null,
                                    null,
									filters,
									initialSelection);

            // XPD-3129: add project reference if required
            if (result instanceof Type
                    && ComplexDataUIUtil
                            .checkProjectDependencies(cellEditorWindow
                                    .getShell(),
                                    project,
                                    (Type) result,
                                    ((Type) result).getName(),
                                    com.tibco.xpd.xpdl2.edit.ui.internal.Messages.PickerUtil_pickComplexType_label)) {
                Classifier classifier = (Classifier) result;

                newComplexDataTypeRef =
                        ComplexDataUIUtil
                                .resolveSelectionToReference(classifier,
                                        project,
                                        complexTypesInfo);
            }
            if (newComplexDataTypeRef != null) {
                result = newComplexDataTypeRef;
            }

            return result;
        }

        /**
         * @return the complexTypesInfo
         */
        protected ComplexDataTypesMergedInfo getComplexTypesInfo() {
            if (_complexTypesInfo == null) {
                _complexTypesInfo =
                        ComplexDataTypeExtPointHelper
                                .getAllComplexDataTypesMergedInfo();
            }
            return _complexTypesInfo;
        }
    }

    protected abstract class AbstractTypeDeclarationColumn extends
            AbstractColumn {

        /**
         * @return the editor
         */
        public ComboBoxViewerCellEditor getEditor() {
            return editor;
        }

        private final ComboBoxViewerCellEditor editor;

        public AbstractTypeDeclarationColumn(EditingDomain editingDomain,
                ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.NONE,
                    Messages.DataFieldsSection_typeDeclarationColumn_label, 90);
            /*
             * XPD-6789: Saket: This editor should be read only.
             */
            editor =
                    new ComboBoxViewerCellEditor(
                            (Composite) viewer.getControl(), SWT.READ_ONLY);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor
         * (java.lang.Object)
         */
        @Override
        protected CellEditor getCellEditor(Object element) {
            if (element instanceof TypeDeclaration
                    || element instanceof ProcessRelevantData) {
                editor.setInput(getViewer().getInput());
                reloadDeclaredTypes();
                return editor;
            }
            return null;
        }

        protected int getDeclaredTypeIndex(String id) {
            int typeIndex = 0;
            if (declaredTypeMap != null) {
                Set<String> keySet = declaredTypeMap.keySet();
                if (keySet.contains(id)) {
                    for (Iterator<String> iterator = keySet.iterator(); iterator
                            .hasNext();) {
                        String typeId = iterator.next();
                        if (typeId != null && typeId.equals(id)) {
                            break;
                        }
                        typeIndex++;
                    }
                }
            }
            return typeIndex;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java
         * .lang.Object)
         */
        @Override
        protected String getText(Object element) {
            String text = null;
            if (element instanceof TypeDeclaration) {
                TypeDeclaration typeDeclaration = (TypeDeclaration) element;
                if (typeDeclaration.getDeclaredType() != null) {
                    DeclaredType declaredType =
                            typeDeclaration.getDeclaredType();
                    String typeDeclarationId =
                            declaredType.getTypeDeclarationId();
                    if (typeDeclarationId == null
                            || typeDeclarationId.equals("")) {//$NON-NLS-1$
                        text = "";//$NON-NLS-1$
                    } else {
                        text = getDeclaredTypeName(typeDeclarationId);
                    }
                } else {
                    text =
                            com.tibco.xpd.xpdl2.edit.ui.internal.Messages.BaseTypeSection_NotApplicable_text;
                }
            }
            if (element instanceof ProcessRelevantData) {
                ProcessRelevantData prd = (ProcessRelevantData) element;
                if (prd.getDataType() instanceof DeclaredType) {
                    DeclaredType declaredType =
                            (DeclaredType) prd.getDataType();
                    String typeDeclarationId =
                            declaredType.getTypeDeclarationId();
                    if (typeDeclarationId == null
                            || typeDeclarationId.equals("")) {//$NON-NLS-1$
                        text = "";//$NON-NLS-1$
                    } else {
                        text = getDeclaredTypeName(typeDeclarationId);
                    }
                } else {
                    text =
                            com.tibco.xpd.xpdl2.edit.ui.internal.Messages.BaseTypeSection_NotApplicable_text;
                }
            }
            if (text == null) {
                text =
                        com.tibco.xpd.xpdl2.edit.ui.internal.Messages.BaseTypeSection_UnresolvedReference;
            }
            return text;
        }

        protected String getDeclaredTypeName(String id) {
            String declaredTypeName = null;
            if (declaredTypeMap != null) {
                declaredTypeName = declaredTypeMap.get(id);
            }
            return declaredTypeName;
        }

        protected String getDeclaredTypeValue(int index) {
            String typeDeclarationValue = null;
            if (declaredTypeMap != null && index != -1) {
                ArrayList<String> values =
                        new ArrayList<String>(declaredTypeMap.keySet());
                typeDeclarationValue = values.get(index);
            }
            return typeDeclarationValue;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getValueForEditor
         * (java.lang.Object)
         */
        @Override
        protected Object getValueForEditor(Object element) {
            /*
             * XPD-4692: Values for type declaration column are expected to be
             * TypeDeclaration (not the name of the type declaration. If we
             * don't return this for given input element then the cell editor
             * will be blank when ser clicks on it.
             */
            if (element instanceof TypeDeclaration) {

                DeclaredType declaredType =
                        ((TypeDeclaration) element).getDeclaredType();

                Package pkg =
                        Xpdl2ModelUtil.getPackage((TypeDeclaration) element);

                if (declaredType != null && pkg != null) {
                    return pkg.getTypeDeclaration(declaredType
                            .getTypeDeclarationId());

                }
            } else if (element instanceof ProcessRelevantData) {
                ProcessRelevantData prd = (ProcessRelevantData) element;
                if (prd.getDataType() instanceof DeclaredType) {
                    DeclaredType declaredType =
                            (DeclaredType) prd.getDataType();

                    String typeDeclarationId =
                            declaredType.getTypeDeclarationId();

                    Package pkg = Xpdl2ModelUtil.getPackage(prd);

                    if (typeDeclarationId != null && pkg != null) {
                        return pkg.getTypeDeclaration(typeDeclarationId);
                    }
                }
            }

            return ""; //$NON-NLS-1$
        }

    }

}
