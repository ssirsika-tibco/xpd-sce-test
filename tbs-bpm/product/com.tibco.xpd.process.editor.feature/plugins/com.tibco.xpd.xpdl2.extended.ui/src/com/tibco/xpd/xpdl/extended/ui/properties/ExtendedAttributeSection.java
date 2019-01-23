package com.tibco.xpd.xpdl.extended.ui.properties;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap.Entry;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonNavigator;

import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.TextFieldVerifier;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtAttribute;
import com.tibco.xpd.xpdExtension.XpdExtAttributes;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl.extended.ui.Messages;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExtendedAttributesContainer;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.MessageFlow;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.LateExecuteCompoundCommand;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public class ExtendedAttributeSection extends
        AbstractFilteredTransactionalSection implements TextFieldVerifier {

    private static final String ATTRIBUTE_NAME = Messages.attributeName;

    private static final String[] FILTERS =
            {
                    "XPD", "WorkflowProcessSimulationData", "ActivitySimulationData", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                    "StartSimulationData", "SplitSimulationData", "TransitionSimulationData", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 
                    "CreatedBy", "FormatVersion", "Destination", "location" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

    private TableViewer ea;

    private Button add;

    private Button remove;

    private Button up;

    private Button down;

    private Text name;

    private Text value;

    private Button escape;

    private Text body;

    private Color enabled;

    private Color disabled;

    private ExtendedAttribute attribute;

    private XpdExtAttribute other;

    private boolean implementedParameter;

    private Composite root;

    boolean isAdd = false;

    public ExtendedAttributeSection() {
        super(Xpdl2Package.eINSTANCE.getExtendedAttributesContainer());
        setShowInWizard(true);
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout(2, false));

        Composite left = toolkit.createComposite(root);
        left.setLayout(new GridLayout(2, false));
        left.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        Composite right = toolkit.createComposite(root);
        right.setLayout(new GridLayout(2, false));
        right.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        Label eaLabel = toolkit.createLabel(left, Messages.eaLabel);
        eaLabel.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 2,
                1));

        Table eaTable =
                toolkit.createTable(left,
                        SWT.SINGLE | SWT.FULL_SELECTION,
                        "tableInstrumentationName");//$NON-NLS-1$
        eaTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        ea = new TableViewer(eaTable);
        ea.setContentProvider(new ExtendedAttributeContentProvider());
        ea.setLabelProvider(new ExtendedAttributeLabelProvider());
        ea.addSelectionChangedListener(new ISelectionChangedListener() {
            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                attribute = null;
                other = null;
                IStructuredSelection selection =
                        (IStructuredSelection) event.getSelection();
                if (selection.size() == 1) {
                    Object first = selection.getFirstElement();
                    if (first instanceof ExtendedAttribute) {
                        attribute = (ExtendedAttribute) first;
                        other = null;
                    } else if (first instanceof XpdExtAttribute) {
                        attribute = null;
                        other = (XpdExtAttribute) first;
                    }
                }
                refresh();
            }
        });

        Composite buttons = toolkit.createComposite(left);
        buttons.setLayout(new GridLayout(1, false));
        buttons.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));

        add = toolkit.createButton(buttons, Messages.add, SWT.PUSH);
        add.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

        remove = toolkit.createButton(buttons, Messages.remove, SWT.PUSH);
        remove.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

        up = toolkit.createButton(buttons, Messages.up, SWT.PUSH);
        up.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

        down = toolkit.createButton(buttons, Messages.down, SWT.PUSH);
        down.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

        Label nameLabel = toolkit.createLabel(right, Messages.nameLabel);
        nameLabel
                .setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, false, false));

        name = toolkit.createText(right, ""); //$NON-NLS-1$
        name.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

        Label valueLabel = toolkit.createLabel(right, Messages.valueLabel);
        valueLabel.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, false,
                false));

        value = toolkit.createText(right, ""); //$NON-NLS-1$
        value.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

        Label escapeLabel = toolkit.createLabel(right, Messages.escapeLabel);
        escapeLabel.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, false,
                false));

        escape = toolkit.createButton(right, "", SWT.CHECK); //$NON-NLS-1$
        escape.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

        Label bodyLabel = toolkit.createLabel(right, Messages.bodyLabel);
        bodyLabel.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));

        body = toolkit.createText(right, "", SWT.MULTI | SWT.H_SCROLL //$NON-NLS-1$
                | SWT.V_SCROLL);
        body.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        add.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                addNewAttribute();
                refreshForWizard();
            }
        });

        remove.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                removeAttribute();
                refreshForWizard();
            }
        });

        up.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                moveAttribute(-1);
                refreshForWizard();
            }
        });

        down.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                moveAttribute(1);
                refreshForWizard();
            }
        });

        enabled =
                PlatformUI.getWorkbench().getDisplay()
                        .getSystemColor(SWT.COLOR_WHITE);
        disabled =
                PlatformUI.getWorkbench().getDisplay()
                        .getSystemColor(SWT.COLOR_GRAY);

        manageControl(name);
        manageControl(value);
        manageControl(escape);
        manageControl(body);

        return root;
    }

    @Override
    public Command doGetCommand(Object obj) {
        Command command = null;
        Object input = getInput();
        if (input instanceof ExtendedAttributesContainer && attribute != null) {
            EditingDomain ed = getEditingDomain();
            if (obj == name) {
                // command =
                // SetCommand.create(ed, attribute, Xpdl2Package.eINSTANCE
                // .getExtendedAttribute_Name(), name.getText());

                CompoundCommand cmd = new LateExecuteCompoundCommand();
                cmd.append(SetCommand.create(ed,
                        attribute,
                        Xpdl2Package.eINSTANCE.getExtendedAttribute_Name(),
                        name.getText()));
                command = cmd;

            } else if (obj == value) {
                if (value.getText() == null || value.getText().length() == 0) {
                    command =
                            SetCommand.create(ed,
                                    attribute,
                                    Xpdl2Package.eINSTANCE
                                            .getExtendedAttribute_Value(),
                                    SetCommand.UNSET_VALUE);

                } else {
                    command =
                            SetCommand.create(ed,
                                    attribute,
                                    Xpdl2Package.eINSTANCE
                                            .getExtendedAttribute_Value(),
                                    value.getText());
                }
            } else if (obj == escape || obj == body) {
                FeatureMap map = attribute.getMixed();
                CompoundCommand cmd = new CompoundCommand();
                for (Iterator<?> iter = map.iterator(); iter.hasNext();) {
                    FeatureMap.Entry entry = (FeatureMap.Entry) iter.next();
                    cmd.append(RemoveCommand
                            .create(ed, attribute, Xpdl2Package.eINSTANCE
                                    .getExtendedAttribute_Mixed(), entry));
                }
                if (escape.getSelection()) {
                    FeatureMap.Entry entry =
                            FeatureMapUtil.createEntry(XMLTypePackage.eINSTANCE
                                    .getXMLTypeDocumentRoot_CDATA(), body
                                    .getText());
                    cmd.append(AddCommand
                            .create(ed, attribute, Xpdl2Package.eINSTANCE
                                    .getExtendedAttribute_Mixed(), entry));
                } else {
                    FeatureMap.Entry entry =
                            FeatureMapUtil.createEntry(XMLTypePackage.eINSTANCE
                                    .getXMLTypeDocumentRoot_Text(), body
                                    .getText());
                    cmd.append(AddCommand
                            .create(ed, attribute, Xpdl2Package.eINSTANCE
                                    .getExtendedAttribute_Mixed(), entry));
                }
                command = cmd;
            }
        } else {
            if (input instanceof OtherElementsContainer && other != null) {
                EditingDomain ed = getEditingDomain();
                if (obj == name) {
                    command =
                            SetCommand.create(ed,
                                    other,
                                    XpdExtensionPackage.eINSTANCE
                                            .getXpdExtAttribute_Name(),
                                    name.getText());
                } else if (obj == value) {
                    if (value.getText() == null
                            || value.getText().length() == 0) {
                        command =
                                SetCommand.create(ed,
                                        other,
                                        XpdExtensionPackage.eINSTANCE
                                                .getXpdExtAttribute_Value(),
                                        SetCommand.UNSET_VALUE);
                    } else {
                        command =
                                SetCommand.create(ed,
                                        other,
                                        XpdExtensionPackage.eINSTANCE
                                                .getXpdExtAttribute_Value(),
                                        value.getText());
                    }
                } else if (obj == escape || obj == body) {
                    FeatureMap map = other.getMixed();
                    CompoundCommand cmd = new CompoundCommand();
                    for (Iterator<?> iter = map.iterator(); iter.hasNext();) {
                        FeatureMap.Entry entry = (FeatureMap.Entry) iter.next();
                        cmd.append(RemoveCommand.create(ed,
                                other,
                                XpdExtensionPackage.eINSTANCE
                                        .getXpdExtAttribute_Mixed(),
                                entry));
                    }
                    if (escape.getSelection()) {
                        FeatureMap.Entry entry =
                                FeatureMapUtil
                                        .createEntry(XMLTypePackage.eINSTANCE
                                                .getXMLTypeDocumentRoot_CDATA(),
                                                body.getText());
                        cmd.append(AddCommand.create(ed,
                                other,
                                XpdExtensionPackage.eINSTANCE
                                        .getXpdExtAttribute_Mixed(),
                                entry));
                    } else {
                        FeatureMap.Entry entry =
                                FeatureMapUtil
                                        .createEntry(XMLTypePackage.eINSTANCE
                                                .getXMLTypeDocumentRoot_Text(),
                                                body.getText());
                        cmd.append(AddCommand.create(ed,
                                other,
                                XpdExtensionPackage.eINSTANCE
                                        .getXpdExtAttribute_Mixed(),
                                entry));
                    }
                    command = cmd;
                }
            }
        }
        return command;
    }

    private void refreshForWizard() {
        if (getSectionContainerType() == ContainerType.WIZARD) {
            refresh();
        }
    }

    private void addNewAttribute() {
        Object input = getInput();
        if (input instanceof ExtendedAttributesContainer) {
            ExtendedAttributesContainer container =
                    (ExtendedAttributesContainer) input;
            EditingDomain ed = getEditingDomain();

            ExtendedAttribute att =
                    Xpdl2Factory.eINSTANCE.createExtendedAttribute();
            int i = getNextIndex(container);
            String name = ATTRIBUTE_NAME + i;
            att.setName(name);
            Command command =
                    AddCommand
                            .create(ed,
                                    container,
                                    Xpdl2Package.eINSTANCE
                                            .getExtendedAttributesContainer_ExtendedAttributes(),
                                    att);
            if (command.canExecute()) {
                isAdd = true;
                ed.getCommandStack().execute(command);
            }
        } else if (input instanceof OtherElementsContainer) {
            OtherElementsContainer container = (OtherElementsContainer) input;
            CompoundCommand command = new CompoundCommand();
            EditingDomain ed = getEditingDomain();

            XpdExtAttributes attributes = getXpdExtAttributes(container);
            if (attributes == null) {
                attributes =
                        XpdExtensionFactory.eINSTANCE.createXpdExtAttributes();
                command.append(Xpdl2ModelUtil.getSetOtherElementCommand(ed,
                        container,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ExtendedAttributes(),
                        attributes));
            }

            XpdExtAttribute att =
                    XpdExtensionFactory.eINSTANCE.createXpdExtAttribute();
            int i = getNextIndex(container);
            String name = ATTRIBUTE_NAME + i;
            att.setName(name);
            command.append(AddCommand.create(ed,
                    attributes,
                    XpdExtensionPackage.eINSTANCE
                            .getXpdExtAttributes_Attributes(),
                    att));
            if (command.canExecute()) {
                isAdd = true;
                ed.getCommandStack().execute(command);
            }
            System.out.print(""); //$NON-NLS-1$
        }
    }

    private XpdExtAttributes getXpdExtAttributes(
            OtherElementsContainer container) {
        XpdExtAttributes attributes = null;
        Object element =
                Xpdl2ModelUtil.getOtherElement(container,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ExtendedAttributes());
        if (element instanceof XpdExtAttributes) {
            attributes = (XpdExtAttributes) element;
        }
        return attributes;
    }

    private void removeAttribute() {
        Object input = getInput();
        if (input instanceof ExtendedAttributesContainer && attribute != null
                && isEditable(attribute)) {
            ExtendedAttributesContainer container =
                    (ExtendedAttributesContainer) input;
            EditingDomain ed = getEditingDomain();
            Command command =
                    RemoveCommand
                            .create(ed,
                                    container,
                                    Xpdl2Package.eINSTANCE
                                            .getExtendedAttributesContainer_ExtendedAttributes(),
                                    attribute);
            if (command.canExecute()) {
                ed.getCommandStack().execute(command);
            }
        } else if (input instanceof OtherElementsContainer && other != null
                && isEditable(other)) {
            CompoundCommand command = new CompoundCommand();
            OtherElementsContainer container = (OtherElementsContainer) input;
            XpdExtAttributes attributes = getXpdExtAttributes(container);
            EditingDomain ed = getEditingDomain();
            command.append(RemoveCommand.create(ed,
                    attributes,
                    XpdExtensionPackage.eINSTANCE
                            .getXpdExtAttributes_Attributes(),
                    other));
            if (attributes.getAttributes().size() == 1) {
                command.append(Xpdl2ModelUtil.getSetOtherElementCommand(ed,
                        container,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ExtendedAttributes(),
                        null));
            }
            if (command.canExecute()) {
                ed.getCommandStack().execute(command);
            }
        }
    }

    private void moveAttribute(int i) {
        Object input = getInput();
        if (input instanceof ExtendedAttributesContainer && attribute != null) {
            ExtendedAttributesContainer container =
                    (ExtendedAttributesContainer) input;
            EditingDomain ed = getEditingDomain();
            int index =
                    container.getExtendedAttributes().indexOf(attribute) + i;
            Command command =
                    MoveCommand
                            .create(ed,
                                    container,
                                    Xpdl2Package.eINSTANCE
                                            .getExtendedAttributesContainer_ExtendedAttributes(),
                                    attribute,
                                    index);
            if (command.canExecute()) {
                ed.getCommandStack().execute(command);
            }
        } else if (input instanceof OtherElementsContainer && other != null) {
            OtherElementsContainer container = (OtherElementsContainer) input;
            XpdExtAttributes attributes = getXpdExtAttributes(container);
            if (attributes != null) {
                EditingDomain ed = getEditingDomain();
                int index = attributes.getAttributes().indexOf(other) + i;
                Command command =
                        MoveCommand.create(ed,
                                attributes,
                                XpdExtensionPackage.eINSTANCE
                                        .getXpdExtAttributes_Attributes(),
                                other,
                                index);
                if (command.canExecute()) {
                    ed.getCommandStack().execute(command);
                }
            }
        }
    }

    private int getNextIndex(ExtendedAttributesContainer container) {
        int index = 0;
        List<?> attributes = container.getExtendedAttributes();
        for (Iterator<?> i = attributes.iterator(); i.hasNext();) {
            Object next = i.next();
            if (next instanceof ExtendedAttribute) {
                ExtendedAttribute att = (ExtendedAttribute) next;
                String name = att.getName();
                if (name != null && name.startsWith(ATTRIBUTE_NAME)) {
                    try {
                        index =
                                Math.max(index, Integer.parseInt(name
                                        .substring(ATTRIBUTE_NAME.length())));
                    } catch (NumberFormatException e) {
                        // ignore
                    }
                }
            }
        }
        return index + 1;
    }

    private int getNextIndex(OtherElementsContainer container) {
        int index = 0;
        XpdExtAttributes attributes = getXpdExtAttributes(container);
        if (attributes != null) {
            for (Object next : attributes.getAttributes()) {
                XpdExtAttribute att = (XpdExtAttribute) next;
                String name = att.getName();
                if (name != null && name.startsWith(ATTRIBUTE_NAME)) {
                    try {
                        index =
                                Math.max(index, Integer.parseInt(name
                                        .substring(ATTRIBUTE_NAME.length())));
                    } catch (NumberFormatException e) {
                        // ignore
                    }
                }
            }
        }
        return index + 1;
    }

    @Override
    protected void doRefresh() {
        Object input = getInput();
        if (input != null) {
            if (input instanceof ExtendedAttributesContainer) {
                ExtendedAttributesContainer container =
                        (ExtendedAttributesContainer) input;
                ea.setInput(container);
                other = null;
            } else if (isOther(input)
                    && input instanceof OtherElementsContainer) {
                OtherElementsContainer owner = (OtherElementsContainer) input;
                EStructuralFeature elementFeature =
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ExtendedAttributes();
                Object element =
                        Xpdl2ModelUtil.getOtherElement(owner, elementFeature);
                if (element instanceof XpdExtAttributes) {
                    ea.setInput(element);
                } else {
                    ea.setInput(null);
                }
                attribute = null;
            }
            // XPD-55 : begin
            if (null != ea.getTable()) {
                int selectionIndex = ea.getTable().getSelectionIndex();
                int cnt = ea.getTable().getItemCount();

                if (isAdd || selectionIndex < 0) {
                    // make the last element in the table as selected
                    selectionIndex = cnt - 1;
                }
                // only set the selection if the table is not empty
                if (selectionIndex >= 0)
                    ea.setSelection(new StructuredSelection(ea
                            .getElementAt(selectionIndex)));
            }
            // XPD-55 : end
        } else {
            attribute = null;
            other = null;
            ea.setInput(null);
        }
        if (attribute != null) {
            boolean editable = isEditable(attribute);
            updateText(name, attribute.getName() == null ? "" : attribute //$NON-NLS-1$
                    .getName());
            name.setEditable(editable);
            // XPD-55 : begin
            if (isAdd) {
                isAdd = false;
                name.setFocus();
                name.setSelection(0, attribute.getName().length());
            }
            // XPD-55 : end
            name.setBackground(editable ? enabled : disabled);
            updateText(value, attribute.getValue() == null ? "" : attribute //$NON-NLS-1$
                    .getValue());
            value.setEditable(editable);
            value.setBackground(editable ? enabled : disabled);
            Body content = getBodyText(attribute);
            escape.setSelection(content.isCData);
            escape.setEnabled(editable);
            updateText(body, content.text);
            body.setEditable(editable);
            body.setBackground(editable ? enabled : disabled);
        } else if (other != null) {
            boolean editable = isEditable(other);
            updateText(name, other.getName() == null ? "" : other //$NON-NLS-1$
                    .getName());
            name.setEditable(editable);
            if (isAdd) {
                isAdd = false;
                name.setFocus();
                name.setSelection(0, other.getName().length());
            }
            name.setBackground(editable ? enabled : disabled);
            updateText(value, other.getValue() == null ? "" : other //$NON-NLS-1$
                    .getValue());
            value.setEditable(editable);
            value.setBackground(editable ? enabled : disabled);
            Body content = getBodyText(other);
            escape.setSelection(content.isCData);
            escape.setEnabled(editable);
            updateText(body, content.text);
            body.setEditable(editable);
            body.setBackground(editable ? enabled : disabled);
        } else {
            updateText(name, ""); //$NON-NLS-1$
            name.setEditable(false);
            name.setBackground(disabled);
            updateText(value, ""); //$NON-NLS-1$
            value.setEditable(false);
            value.setBackground(disabled);
            escape.setSelection(false);
            escape.setEnabled(false);
            updateText(body, ""); //$NON-NLS-1$
            body.setEditable(false);
            body.setBackground(disabled);
        }
        disableAll(root, !implementedParameter);
    }

    private void disableAll(Control control, boolean disabled) {
        if (control instanceof Composite) {
            if (((Composite) control).getChildren().length > 0) {
                for (Control childControl : ((Composite) control).getChildren()) {
                    disableAll(childControl, disabled);
                }
            }
        }
        control.setEnabled(disabled);
    }

    private boolean isEditable(XpdExtAttribute att) {
        return true;
    }

    private boolean isEditable(ExtendedAttribute att) {
        boolean editable = true;
        String name = att.getName();
        for (int i = 0; i < FILTERS.length; i++) {
            if (FILTERS[i].equals(name)) {
                editable = false;
                break;
            }
        }
        return editable;
    }

    private Body getBodyText(ExtendedAttribute att) {
        return new Body(att);
    }

    private Body getBodyText(XpdExtAttribute att) {
        return new Body(att);
    }

    class Body {
        private boolean isCData = false;

        private String text = ""; //$NON-NLS-1$

        public Body(ExtendedAttribute att) {
            FeatureMap mixed = att.getMixed();
            getBodyText(mixed);
        }

        public Body(XpdExtAttribute att) {
            FeatureMap mixed = att.getMixed();
            getBodyText(mixed);
        }

        private void getBodyText(FeatureMap mixed) {
            if (mixed != null) {
                if (mixed.size() == 1) {
                    Object item = mixed.get(0);
                    if (item instanceof Entry) {
                        Entry entry = (Entry) item;
                        isCData = FeatureMapUtil.isCDATA(entry);
                        Object value = entry.getValue();
                        if (value != null) {
                            text = value.toString();
                        }
                    }
                }
            }
        }

    }

    @Override
    public boolean select(Object object) {
        EObject eo = null;
        if (object instanceof EObject) {
            eo = (EObject) object;
        } else if (object instanceof IAdaptable) {
            eo = (EObject) ((IAdaptable) object).getAdapter(EObject.class);
        }
        if (eo != null) {
            if (eo instanceof ExtendedAttributesContainer || isOther(eo)) {
                return true;
            }
        }
        return false;
    }

    private boolean isOther(Object eo) {
        if (eo instanceof FormalParameter || eo instanceof MessageFlow
                || eo instanceof Pool || eo instanceof Lane) {
            return true;
        }
        return false;
    }

    @Override
    public void verifyText(Event event) {
        Text textControl = ((Text) event.widget);
        if (textControl == name) {
            String t =
                    textControl.getText(0, event.start - 1)
                            + event.text
                            + textControl.getText(event.end,
                                    textControl.getCharCount() - 1);

            if ("".equals(t)) { //$NON-NLS-1$
                event.doit = true;
                return;
            }

            if (t.contains(" ")) { //$NON-NLS-1$
                event.doit = false;
            }
        }
    }

    @Override
    public void setInput(IWorkbenchPart part, ISelection selection) {
        super.setInput(part, selection);
        implementedParameter = false;
        if (selection != null
                && selection instanceof IStructuredSelection
                && ((IStructuredSelection) selection).getFirstElement() instanceof FormalParameter
                && part instanceof CommonNavigator) {
            TreeItem[] selectionList =
                    ((CommonNavigator) part).getCommonViewer().getTree()
                            .getSelection();
            for (TreeItem item : selectionList)
                if (item.getParentItem().getParentItem().getData() instanceof com.tibco.xpd.xpdl2.Process
                        && ((EObject) item.getData()).eContainer() instanceof ProcessInterface)
                    implementedParameter = true;
        }
    }
}
