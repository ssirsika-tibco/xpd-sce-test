/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui.internal.editor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.ui.components.BaseColumnViewerControl;
import com.tibco.xpd.resources.ui.components.actions.CollapseAllAction;
import com.tibco.xpd.resources.ui.components.actions.ExpandAllAction;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rest.schema.JsonSchemaUtil;
import com.tibco.xpd.rest.schema.JsonSchemaWorkingCopy;
import com.tibco.xpd.rest.schema.ui.internal.Messages;
import com.tibco.xpd.rest.schema.ui.internal.RestSchemaImage;
import com.tibco.xpd.rest.schema.ui.internal.RestSchemaUiPlugin;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * JSON Schema Editor composite. This extends AbstractTransactionalSection in
 * order to take advatage of the support for managing controls, refreshing and
 * running commands.
 * 
 * @author nwilson
 * @since 27 Nov 2014
 */
public class JsonSchemaEditor extends AbstractTransactionalSection implements
        ISelectionProvider, CommandViewerActionProvider {

    private static final String H_WEIGHT_1 = "HWeight1"; //$NON-NLS-1$

    private static final String H_WEIGHT_2 = "HWeight2"; //$NON-NLS-1$

    private JsonSchemaEditorPart editor;

    private TreeViewer schema;

    private TreeViewer classes;

    private CommandViewerAction addPropertyAction;

    private CommandViewerAction addClassAction;

    private CommandViewerAction addClassPropertyAction;

    private CommandViewerAction switchRootClassAction;

    private SashForm sash;

    private TreeViewer focussed;

    private boolean isReadOnly;

    private Form rootForm;

    private SchemaOverviewTreeControl schemaTreeControl;

    private SchemaClassesTreeControl classesTreeControl;

    private final static JsonSchemaUtil JSON_SCHEMA_UTIL = new JsonSchemaUtil();

    /** Represents the type of switch between root and non-root type that is available for a selection */
    private enum SwitchType {
        NONE(Messages.JsonSchemaEditor_SetAsPrivateOrPublic), //
        TO_ROOT(Messages.JsonSchemaEditor_SetAsPublic), //
        TO_NON_ROOT(Messages.JsonSchemaEditor_SetAsPrivate);

        /** The label used for action button or command name. */
        String label;

        SwitchType(String label) {
            this.label = label;
        }

        String getLabel() {
            return label;
        }
    };

    public JsonSchemaEditor(JsonSchemaEditorPart editor) {
        this.editor = editor;
    }

    @Override
    public void addCommandViewerActions(Control source,
            IContributionManager manager, StructuredViewer viewer) {
        if (source instanceof SchemaOverviewTreeControl) {
            // Contribute actions to the schema overview control.
            addPropertyAction = new CommandViewerAction(viewer, this);
            addPropertyAction
                    .setToolTipText(Messages.JsonSchemaEditor_addPropertyTooltip);
            addPropertyAction.setImageDescriptor(RestSchemaUiPlugin
                    .getDefault()
                    .getImageDescriptor(RestSchemaImage.ADD_JSON_PROPERTY));
            addPropertyAction.setEnabled(false);
            manager.appendToGroup(BaseColumnViewerControl.ADD_ACTIONS_END_MARKER,
                    addPropertyAction);
        } else if (source instanceof SchemaClassesTreeControl) {
            // Contribute actions to the schema classes control.
            addClassAction = new CommandViewerAction(viewer, this);
            addClassAction
                    .setToolTipText(Messages.JsonSchemaEditor_addClassTooltip);
            addClassAction.setImageDescriptor(RestSchemaUiPlugin.getDefault()
                    .getImageDescriptor(RestSchemaImage.ADD_JSON_CLASS));
            manager.appendToGroup(BaseColumnViewerControl.ADD_ACTIONS_END_MARKER,
                    addClassAction);
            addClassPropertyAction = new CommandViewerAction(viewer, this);
            addClassPropertyAction
                    .setToolTipText(Messages.JsonSchemaEditor_addPropertyTooltip);
            addClassPropertyAction.setImageDescriptor(RestSchemaUiPlugin
                    .getDefault()
                    .getImageDescriptor(RestSchemaImage.ADD_JSON_PROPERTY));
            manager.appendToGroup(BaseColumnViewerControl.ADD_ACTIONS_END_MARKER,
                    addClassPropertyAction);
            addClassPropertyAction.setEnabled(false);

            switchRootClassAction = new CommandViewerAction(viewer, this);
            switchRootClassAction.setToolTipText(SwitchType.NONE.getLabel());
            switchRootClassAction.setImageDescriptor(
                    RestSchemaUiPlugin.getDefault().getImageDescriptor(RestSchemaImage.JSON_SWITCH_ROOT));
            manager.appendToGroup(BaseColumnViewerControl.ADD_ACTIONS_END_MARKER, switchRootClassAction);
            switchRootClassAction.setEnabled(false);
        }
    }

    /**
     * @param parent
     *            The parent composite to attatch this editor to.
     * @param toolkit
     *            The toolkit used to create controls.
     */
    @Override
    public Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        rootForm = toolkit.createForm(parent);
        rootForm.setText(Messages.JsonSchemaEditor_title);
        toolkit.getFormToolkit().decorateFormHeading(rootForm);
        Composite root = rootForm.getBody();

        GridLayout layout = new GridLayout();
        root.setLayout(layout);

        // Create the Sash form to split the editor horizontally.
        sash = new SashForm(root, SWT.HORIZONTAL);
        sash.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        // The left section will contain the schema overview.
        Section ls =
                toolkit.createSection(sash, Section.TITLE_BAR
                        | Section.EXPANDED);
        ls.setText(Messages.JsonSchemaEditor_schemaHeader);

        Composite left = toolkit.createComposite(ls);
        left.setLayout(new GridLayout());
        ls.setClient(left);

        // The right section will contain the schema classes.
        Section rs =
                toolkit.createSection(sash, Section.TITLE_BAR
                        | Section.EXPANDED);
        rs.setText(Messages.JsonSchemaEditor_classesHeader);
        Composite right = toolkit.createComposite(rs);
        right.setLayout(new GridLayout(2, false));
        rs.setClient(right);

        // Create the left section schema overview control.
        schemaTreeControl =
                new SchemaOverviewTreeControl(left, toolkit, this);
        schemaTreeControl.init();
        schema = schemaTreeControl.getTreeViewer();
        schemaTreeControl.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                true));
        schema.setContentProvider(new UmlTreeContentProvider());
        schema.setLabelProvider(new UmlJsonSchemaLabelProvider());

        // Create the right section schema classes control.
        classesTreeControl =
                new SchemaClassesTreeControl(right, toolkit, this);
        classesTreeControl.init();
        classes = classesTreeControl.getTreeViewer();
        classesTreeControl.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                true));
        classes.setContentProvider(new UmlClassContentProvider());
        classes.setLabelProvider(new UmlJsonSchemaLabelProvider());

        // Set up listeners
        ISelectionChangedListener listener = new ISelectionChangedListener() {

            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                Object source = event.getSource();
                if (source instanceof TreeViewer) {
                    focussed = (TreeViewer) source;
                }
                updateButtons();
            }
        };
        schema.addSelectionChangedListener(listener);
        classes.addSelectionChangedListener(listener);

        createSectionToolbar(ls, schema);
        createSectionToolbar(rs, classes);

        new JsonSchemaDragDropReorderHandler(schema);
        new JsonSchemaDragDropReorderHandler(classes);

        // Load and previously saved dialog settings (sash position).
        loadSettings();

        return root;
    }

    /**
     * Adds expand/collapse all buttons to the section header.
     * 
     * @param section
     *            The section to add controls to.
     * @param viewer
     *            The viewer to expand/collapse.
     */
    private void createSectionToolbar(Section section, TreeViewer viewer) {
        ToolBarManager toolBarManager = new ToolBarManager(SWT.FLAT);
        ToolBar toolbar = toolBarManager.createControl(section);
        toolBarManager.add(new ExpandAllAction(viewer));
        toolBarManager.add(new CollapseAllAction(viewer));
        toolBarManager.update(true);
        section.setTextClient(toolbar);
    }

    /**
     * @param element
     *            The element to add.
     * @param target
     *            The selected target.
     * @param createUniqueName
     *            True to create unique names (for copy). Should be false for
     *            cut.
     * @return The command to add the element.
     */
    protected Command getAddCommand(Object node, Object targetNode,
            boolean createUniqueName) {
        Command cmd = null;
        Object element = getUmlElement(node);
        Object target = getUmlElement(targetNode);
        if (element instanceof Property) {
            Property property = (Property) element;
            if (createUniqueName) {
                property.setName(createUniquePropertyName(property.getName()));
            }
            int index = 0;
            Class parentClass = null;
            if (target instanceof Property) {
                parentClass = ((Property) target).getClass_();
                index = parentClass.getOwnedAttributes().indexOf(target) + 1;
            } else if (target instanceof Class) {
                parentClass = (Class) target;
            }
            if (parentClass != null) {
                CompoundCommand cc = new CompoundCommand();
                EStructuralFeature feature =
                        UMLPackage.eINSTANCE
                                .getStructuredClassifier_OwnedAttribute();
                cc.append(new AddCommand(getEditingDomain(), parentClass,
                        feature, element, index));
                cmd = cc;

                /*
                 * Sid XPD-7861. Don't leave a 'trap' variable for next refresh
                 * as this causes odd behaviours. instead asynchExec() resetting
                 * of the selection to newly added element which will run after
                 * the command is executed.
                 */
                Display.getDefault().asyncExec(new SetSelectionOnAddedProperty(
                        element));
            }

        } else if (element instanceof Class) {
            Class cls = (Class) element;
            if (createUniqueName) {
                cls.setName(createUniqueClassName(cls.getName()));
            }
            if (target instanceof Class) {
                Package pkg = getPackage();
                if (pkg != null) {
                    CompoundCommand cc =
                            new CompoundCommand(
                                    Messages.JsonSchemaEditor_addClassCommand);
                    cc.append(new AddCommand(getWorkingCopyEditingDomain(),
                            pkg, UMLPackage.eINSTANCE
                                    .getPackage_PackagedElement(), element));
                    cmd = cc;

                    /*
                     * Sid XPD-7861. Don't leave a 'trap' variable for next
                     * refresh as this causes odd behaviours. instead
                     * asynchExec() resetting of the selection to newly added
                     * element which will run after the command is executed.
                     */
                    Display.getDefault()
                            .asyncExec(new SetSelectionOnAddedProperty(element));
                }

            } else if (target instanceof Property) {
                Package pkg = getPackage();
                // Only set the property type if the Class is still in the
                // Package, not if it was cut.
                if (pkg != null && pkg.getPackagedElements().contains(element)) {
                    Property property = (Property) target;
                    CompoundCommand cc = new CompoundCommand();
                    cc.append(new SetCommand(getEditingDomain(), property,
                            UMLPackage.eINSTANCE.getTypedElement_Type(),
                            element));
                    cmd = cc;
                }
            }
        }
        return cmd;
    }

    /**
     * @param source
     *            The element to remove.
     * @return The command to remove the element.
     */
    protected Command getRemoveCommand(Object node) {
        Command cmd = null;
        Object element = getUmlElement(node);
        if (element instanceof Property) {
            cmd =
                    getRemovePropertyCommand(Collections
                            .singletonList((Property) element));
        } else if (element instanceof Class) {
            cmd =
                    getRemoveClassCommand(Collections
                            .singletonList((Class) element));
        }
        return cmd;
    }

    /**
     * Creates a command to move a Property object to a new location in the
     * schema file reparenting the object if necessary.
     * 
     * @param source
     *            The object to move.
     * @param target
     *            The target object to move to.
     * @param location
     *            The relative location to the target object (on, after or
     *            before).
     * @return The command to perform the move.
     */
    protected Command getReparentCommand(Object sourceNode, Object targetNode,
            int location) {
        Command cmd = null;
        EditingDomain ed = getEditingDomain();
        Object source = getUmlElement(sourceNode);
        Object target = getUmlElement(targetNode);
        if (source instanceof Property && target instanceof NamedElement) {
            Property child = (Property) source;
            Class parent = null;
            int index = -1;
            if (location == TreeDragDropReorderHandler.LOCATION_ON) {
                parent = getUmlClass((NamedElement) target);
                index = parent.getOwnedAttributes().size();
            } else if (location == TreeDragDropReorderHandler.LOCATION_AFTER) {
                if (target instanceof Property) {
                    parent = ((Property) target).getClass_();
                    index = parent.getOwnedAttributes().indexOf(target) + 1;
                } else if (target instanceof Class) {
                    parent = (Class) target;
                }
            } else if (location == TreeDragDropReorderHandler.LOCATION_BEFORE) {
                if (target instanceof Property) {
                    parent = ((Property) target).getClass_();
                    index = parent.getOwnedAttributes().indexOf(target);
                } else if (target instanceof Class) {
                    parent = (Class) target;
                }
            }
            if (parent != null) {
                CompoundCommand cc = new CompoundCommand();
                EStructuralFeature feature =
                        UMLPackage.eINSTANCE
                                .getStructuredClassifier_OwnedAttribute();

                if (parent.equals(child.eContainer())) {
                    int currentIndex =
                            parent.getOwnedAttributes().indexOf(child);
                    if (currentIndex > (index - 1)) {
                        cc.append(new MoveCommand(ed, parent, feature, child,
                                index));
                        cmd = cc;
                    } else if (currentIndex < (index + 1)) {
                        cc.append(new MoveCommand(ed, parent, feature, child,
                                index - 1));
                        cmd = cc;
                    }
                } else {
                    cc.append(new RemoveCommand(ed, child.eContainer(),
                            feature, child));
                    cc.append(new AddCommand(ed, parent, feature, child, index));
                    cmd = cc;
                }
            }
        } else if (source instanceof Class && target instanceof Property) {
            Class cls = (Class) source;
            Property property = (Property) target;
            Class parent = property.getClass_();
            if (location == TreeDragDropReorderHandler.LOCATION_ON) {
                CompoundCommand cc = new CompoundCommand();
                cc.append(new SetCommand(ed, property, UMLPackage.eINSTANCE
                        .getTypedElement_Type(), cls));
                cmd = cc;
            } else {
                int index = parent.getOwnedAttributes().indexOf(property);
                if (location == TreeDragDropReorderHandler.LOCATION_AFTER) {
                    index++;
                }
                Property newProperty = UMLFactory.eINSTANCE.createProperty();
                newProperty.setName(createUniquePropertyName());
                newProperty.setType(cls);
                CompoundCommand cc =
                        new CompoundCommand(
                                Messages.JsonSchemaEditor_addPropertyCommand);
                cc.append(new AddCommand(ed, parent, UMLPackage.eINSTANCE
                        .getStructuredClassifier_OwnedAttribute(), newProperty,
                        index));
                cmd = cc;
            }
        }
        return cmd;
    }

    /**
     * Executes a command in the current editing domain.
     * 
     * @param cmd
     *            The command to execute.
     */
    protected boolean execute(Command cmd) {
        boolean executed = false;
        if (cmd != null && cmd.canExecute()) {
            EditingDomain ed = getEditingDomain();
            ed.getCommandStack().execute(cmd);
            executed = true;
        }
        return executed;
    }

    /**
     * Update the button enabled states based on the current selection.
     */
    protected void updateButtons() {
        List<Object> schemaSelection = getSelection(schema, Object.class);
        List<NamedElement> classesSelection =
                getSelection(classes, NamedElement.class);
        int schemaCount = schemaSelection.size();
        int classesCount = classesSelection.size();

        boolean addPropertyEnabled = false;
        if (schemaCount == 1) {
            Object node = schemaSelection.get(0);
            if (node instanceof UmlTreePropertyNode) {
                UmlTreePropertyNode umlNode = (UmlTreePropertyNode) node;
                if (!umlNode.hasAncestorOfSameType()) {
                    Object element = getUmlElement(umlNode);
                    if (element instanceof NamedElement) {
                        NamedElement named = (Property) element;
                        if (getUmlClass(named) != null) {
                            addPropertyEnabled = true;
                        }
                    }
                }
            } else {
                addPropertyEnabled = true;
            }
        }


        addPropertyAction.setEnabled(addPropertyEnabled && !isReadOnly());
        addClassPropertyAction.setEnabled(classesCount == 1 && !isReadOnly());
        addClassAction.setEnabled(!isReadOnly());
        
        SwitchType switchType = getAllowedRootSwichType(classesSelection);
        switchRootClassAction.setToolTipText(switchType.getLabel());
        switchRootClassAction.setEnabled(switchType != SwitchType.NONE && !isReadOnly());
    }

    /**
     * Determines the type of class root/non-root switch based on selected objects.
     * 
     * @param selectedObjects
     *            the selection.
     * @return the type of allowed switch for the selection.
     */
    private SwitchType getAllowedRootSwichType(List<?> selectedObjects) {
        SwitchType switchType = SwitchType.NONE;
        for (Object elem : selectedObjects) {
            if (elem instanceof Class) {
                Class cls = (Class) elem;
                boolean isRoot = JSON_SCHEMA_UTIL.isRootClass(cls);
                if (switchType == SwitchType.NONE) {
                    switchType = (isRoot) ? SwitchType.TO_NON_ROOT: SwitchType.TO_ROOT;
                } else if ((switchType == SwitchType.TO_ROOT && isRoot)
                        || (switchType == SwitchType.TO_NON_ROOT && !isRoot)) {
                    return SwitchType.NONE;
                }
            } else {
                return SwitchType.NONE;
            }
        }
        return switchType;
    }


    @Override
    protected IWorkbenchSite getSite() {
        // As this section is not embedded into a property sheet, getSite()
        // will return null hence needs to be overriden
        if (editor != null)
            return editor.getSite();
        return super.getSite();
    }

    /**
     * @param pkg
     *            The JSON Schema root package element.
     */
    public void setInput(Package pkg) {
        super.setInput(Collections.singleton(pkg));
        schema.setInput(pkg);
        classes.setInput(pkg);
    }

    /**
     * Refreshes the controls in the main and details sections of the editor.
     * 
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     */
    @Override
    protected void doRefresh() {
        schema.refresh();
        classes.refresh();
        updateButtons();

        /*
         * Sid XPD-7861. Don't leave a 'trap' variable (utb newSelection) for
         * next refresh as this causes odd behaviours. instead asynchExec()
         * resetting of the selection to newly added element which will run
         * after the command is executed.
         */
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#shouldRefresh(java.util.List)
     * 
     * @param notifications
     * @return
     */
    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        /**
         * Sid XPD-7823. Need to do refresh on ANY JSON package changing, not
         * just our own (else when we have a reference to a type in another
         * schema then if that type gets change we won't reflect the changes in
         * our 'root element complete tree view'.
         */
        if (super.shouldRefresh(notifications)) {
            return true;
        }

        EObject input = getInput();
        if (input != null && notifications != null && !notifications.isEmpty()) {
            for (Notification notification : notifications) {
                if (!notification.isTouch()) {
                    Object notifier = notification.getNotifier();

                    if (notifier instanceof EObject) {
                        WorkingCopy wc =
                                WorkingCopyUtil
                                        .getWorkingCopyFor((EObject) notifier);

                        if (wc instanceof JsonSchemaWorkingCopy) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;

    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     *            The control that triggered the command.
     * @return The resulting command.
     */
    @Override
    protected Command doGetCommand(Object obj) {
        Command cmd = null;
        Package pkg = getPackage();
        if (pkg != null) {
            if (obj == addClassAction) {
                String name = createUniqueClassName();
                Class cls = UMLFactory.eINSTANCE.createClass();
                cls.setName(name);
                CompoundCommand cc =
                        new CompoundCommand(
                                Messages.JsonSchemaEditor_addClassCommand);
                cc.append(new AddCommand(getWorkingCopyEditingDomain(), pkg,
                        UMLPackage.eINSTANCE.getPackage_PackagedElement(), cls));
                cmd = cc;

                /*
                 * Sid XPD-7861. Don't leave a 'trap' variable for next refresh
                 * as this causes odd behaviours. instead asynchExec() resetting
                 * of the selection to newly added element which will run after
                 * the command is executed.
                 */
                Display.getDefault().asyncExec(new SetSelectionOnAddedProperty(
                        cls));

            } else if (obj == addPropertyAction) {
                cmd = getAddPropertyCommand(schema);
            } else if (obj == addClassPropertyAction) {
                cmd = getAddPropertyCommand(classes);
            } else if (obj == switchRootClassAction) {
                cmd = getSwitchRootCommand(classes);
            }
        }
        return cmd;
    }

    /**
     * Adds a new property based on the current viewer and selection.
     * 
     * @param source
     *            The source viewer.
     * @return The command to add a new property.
     */
    private Command getAddPropertyCommand(Viewer source) {
        Command cmd = null;
        Class parentClass = null;
        List<NamedElement> selected = getSelection(source, NamedElement.class);
        if (selected.size() == 1) {
            NamedElement element = selected.get(0);
            if (source == schema) {
                parentClass = getUmlClass(element);
            } else if (element instanceof Class) {
                parentClass = (Class) element;
            } else if (element instanceof Property) {
                parentClass = ((Property) element).getClass_();
            }
        }
        if (parentClass != null) {
            TransactionalEditingDomain ed =
                    (TransactionalEditingDomain) getWorkingCopyEditingDomain();
            ResourceSet rs = ed.getResourceSet();
            final Property property = UMLFactory.eINSTANCE.createProperty();
            property.setName(createUniquePropertyName());
            final DataType type =
                    PrimitivesUtil.getStandardPrimitiveTypeByName(rs,
                            PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
            final Class owner = parentClass;
            cmd =
                    new RecordingCommand(ed,
                            Messages.JsonSchemaEditor_addPropertyCommand) {

                        @Override
                        protected void doExecute() {
                            owner.getOwnedAttributes().add(property);
                            property.setType(type);
                        }
                    };

            /*
             * Sid XPD-7861. Don't leave a 'trap' variable for next refresh as
             * this causes odd behaviours. instead asynchExec() resetting of the
             * selection to newly added element which will run after the command
             * is executed.
             */
            Display.getDefault().asyncExec(new SetSelectionOnAddedProperty(
                    property));
        }
        return cmd;
    }

    /**
     * Adds a new property based on the current viewer and selection.
     * 
     * @param source
     *            The source viewer.
     * @return The command to add a new property.
     */
    private Command getSwitchRootCommand(Viewer source) {
        Command cmd = null;
        List<NamedElement> selected = getSelection(source, NamedElement.class);
        SwitchType switchType = getAllowedRootSwichType(selected);
        if (switchType != SwitchType.NONE) {
            TransactionalEditingDomain ed = (TransactionalEditingDomain) getWorkingCopyEditingDomain();
            cmd = new RecordingCommand(ed, switchType.getLabel()) {
                @Override
                protected void doExecute() {
                    for (NamedElement elem: selected) {
                        if (elem instanceof Class) {
                            final Class cls = (Class) elem;
                            JSON_SCHEMA_UTIL.setClassAsRoot(cls, !JSON_SCHEMA_UTIL.isRootClass(cls));
                        }
                        
                    }
                }
            };
        }
        return cmd;
    }

    /**
     * Takes a NamedElement (Class or Property). For Class it returns the Class
     * itself. For Property it returns the Class assigned to the Property or
     * null if the Property has a primitve type.
     * 
     * @param element
     *            The UML NamedElement to get the UML Class for.
     * @return the UML Class associated with the element parameter or null.
     */
    private Class getUmlClass(NamedElement element) {
        Class parent = null;
        if (element instanceof Class) {
            parent = (Class) element;
        } else if (element instanceof Property) {
            Property property = (Property) element;
            Type type = property.getType();
            if (type instanceof Class) {
                parent = (Class) type;
            }
        }
        return parent;
    }

    /**
     * @return The editor input as a UML Package.
     */
    private Package getPackage() {
        Package pkg = null;
        EObject input = getInput();
        if (input instanceof Package) {
            pkg = (Package) input;
        }
        return pkg;
    }

    /**
     * @return The command to remove the currently selected Classes.
     */
    private CompoundCommand getRemoveClassCommand(List<Class> toRemove) {
        Package pkg = getPackage();
        CompoundCommand cc =
                new CompoundCommand(
                        Messages.JsonSchemaEditor_removeClassCommand);
        cc.append(new RemoveCommand(getWorkingCopyEditingDomain(), pkg,
                UMLPackage.eINSTANCE.getPackage_PackagedElement(), toRemove));
        return cc;
    }

    /**
     * @return The command to remove the currently selected Property elements.
     */
    private CompoundCommand getRemovePropertyCommand(List<Property> toRemove) {
        CompoundCommand cc =
                new CompoundCommand(
                        Messages.JsonSchemaEditor_removePropertyCommand);
        for (Property property : toRemove) {
            cc.append(new RemoveCommand(getWorkingCopyEditingDomain(), property
                    .getOwner(), UMLPackage.eINSTANCE
                    .getStructuredClassifier_OwnedAttribute(), property));
        }
        return cc;
    }

    /**
     * @param provider
     *            The selection provider.
     * @param cls
     *            The class type to get from the selection.
     * @return A list of selected items matching the specified class.
     */
    private <T> List<T> getSelection(ISelectionProvider provider,
            java.lang.Class<T> cls) {
        List<T> results = new ArrayList<>();
        ISelection selection = provider.getSelection();
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection ss = (IStructuredSelection) selection;
            Iterator<?> i = ss.iterator();
            while (i.hasNext()) {
                Object node = i.next();
                if (cls.isInstance(node)) {
                    results.add(cls.cast(node));
                } else {
                    Object next = getUmlElement(node);
                    if (cls.isInstance(next)) {
                        results.add(cls.cast(next));
                    }
                }
            }
        }
        return results;
    }

    /**
     * @param element
     *            The element to unwrap.
     * @return The unwrapped UML element.
     */
    private Object getUmlElement(Object element) {
        Object el = element;
        if (el instanceof UmlTreePropertyNode) {
            el = ((UmlTreePropertyNode) element).getItem();
        }
        return el;
    }

    /**
     * Creates a new unique class name based on the string "NewClass" plus the
     * next unused integer.
     * 
     * @return a unique class name within this file.
     */
    private String createUniqueClassName() {
        String baseName = Messages.JsonSchemaEditor_newClassBaseName;
        return createUniqueClassName(baseName);
    }

    /**
     * Creates a new unique class name based on the given string plus the next
     * unused integer.
     * 
     * @param baseName
     *            The base name to use.
     * @return a unique class name within this file.
     */
    private String createUniqueClassName(String baseName) {
        String base = baseName.replaceAll("\\d*$", ""); //$NON-NLS-1$//$NON-NLS-2$
        EObject input = getInput();
        Set<Integer> used = new HashSet<>();
        if (input instanceof Package) {
            Package pkg = (Package) input;
            for (PackageableElement element : pkg.getPackagedElements()) {
                String name = element.getName();
                if (name.startsWith(base)) {
                    try {
                        used.add(Integer.parseInt(name.substring(base.length())));
                    } catch (NumberFormatException e) {
                        // Ignore
                    }
                }
            }
        }
        if (!base.equals(baseName)) {
            try {
                int current =
                        Integer.parseInt(baseName.substring(base.length()));
                if (!used.contains(current)) {
                    // It's already unique and ends in digits, use it
                    return baseName;
                }
            } catch (NumberFormatException e) {
                // Ignore
            }
        }
        int i = 1;
        while (used.contains(Integer.valueOf(i))) {
            i++;
        }
        return base + i;
    }

    /**
     * Creates a new unique property name based on the string "newProperty" plus
     * the next unused integer.
     * 
     * @return a unique property name within this file.
     */
    private String createUniquePropertyName() {
        String baseName = Messages.JsonSchemaEditor_newPropertyBaseName;
        return createUniquePropertyName(baseName);
    }

    /**
     * Creates a new unique property name based on the given string plus the
     * next unused integer.
     * 
     * @param baseName
     *            The base name to use.
     * @return a unique property name within this file.
     */
    private String createUniquePropertyName(String baseName) {
        String base = baseName.replaceAll("\\d*$", ""); //$NON-NLS-1$//$NON-NLS-2$
        EObject input = getInput();
        Set<Integer> used = new HashSet<>();
        if (input instanceof Package) {
            Package pkg = (Package) input;
            for (PackageableElement element : pkg.getPackagedElements()) {
                if (element instanceof Class) {
                    Class cls = (Class) element;
                    for (Property property : cls.getOwnedAttributes()) {
                        String name = property.getName();
                        if (name.startsWith(base)) {
                            try {
                                used.add(Integer.parseInt(name.substring(base
                                        .length())));
                            } catch (NumberFormatException e) {
                                // Ignore
                            }
                        }
                    }
                }
            }
        }
        if (!base.equals(baseName)) {
            try {
                int current =
                        Integer.parseInt(baseName.substring(base.length()));
                if (!used.contains(current)) {
                    // It's already unique and ends in digits, use it
                    return baseName;
                }
            } catch (NumberFormatException e) {
                // Ignore
            }
        }
        int i = 1;
        while (used.contains(Integer.valueOf(i))) {
            i++;
        }
        return base + i;
    }

    /**
     * @return The working copy editing domain.
     */
    public EditingDomain getWorkingCopyEditingDomain() {
        return editor.getWorkingCopy().getEditingDomain();
    }

    /**
     * Sets focus to the previously focussed tree control. This defaults to the
     * schema control if there was no previous focus.
     */
    public void setFocus() {
        if (focussed == null) {
            focussed = schema;
        }
        focussed.getTree().setFocus();
    }

    /**
     * @see org.eclipse.jface.viewers.ISelectionProvider#addSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
     * 
     * @param listener
     *            The listener to add.
     */
    @Override
    public void addSelectionChangedListener(ISelectionChangedListener listener) {
        schema.addSelectionChangedListener(listener);
        classes.addSelectionChangedListener(listener);
    }

    /**
     * @see org.eclipse.jface.viewers.ISelectionProvider#removeSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
     * 
     * @param listener
     *            The listener to remove.
     */
    @Override
    public void removeSelectionChangedListener(
            ISelectionChangedListener listener) {
        schema.removeSelectionChangedListener(listener);
        classes.removeSelectionChangedListener(listener);
    }

    /**
     * @see org.eclipse.jface.viewers.ISelectionProvider#setSelection(org.eclipse.jface.viewers.ISelection)
     * 
     * @param selection
     *            The selection to set.
     */
    @Override
    public void setSelection(ISelection selection) {
        focussed = classes;
        setFocus();
        classes.setSelection(selection);
    }

    /**
     * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#getSelection()
     * 
     * @return
     */
    @Override
    public ISelection getSelection() {
        if (focussed == schema) {
            return schema.getSelection();
        } else if (focussed == classes) {
            return classes.getSelection();
        }
        return super.getSelection();
    }

    /**
     * Loads the sash location from the settings.
     */
    public void loadSettings() {
        IDialogSettings section = getSettings();
        int[] weights = new int[2];
        try {
            weights[0] = section.getInt(H_WEIGHT_1);
            weights[1] = section.getInt(H_WEIGHT_2);
        } catch (NumberFormatException e) {
            weights[0] = 60;
            weights[1] = 40;
        }
        sash.setWeights(weights);
    }

    /**
     * Stores the sash location to the settings.
     */
    public void saveSettings() {
        IDialogSettings section = getSettings();
        int[] weights = sash.getWeights();
        if (weights.length == 2) {
            section.put(H_WEIGHT_1, weights[0]);
            section.put(H_WEIGHT_2, weights[1]);
        }
    }

    /**
     * @return The editor dialog settings.
     */
    private IDialogSettings getSettings() {
        IDialogSettings settings =
                RestSchemaUiPlugin.getDefault().getDialogSettings();
        String sectionName = getClass().getName();
        IDialogSettings section = settings.getSection(sectionName);
        if (section == null) {
            section = settings.addNewSection(getClass().getName());
        }
        return section;
    }

    /**
     * Drag and drop handler supporting the UML Class and Property elements used
     * in the JSON Schema editor.
     * 
     * @author nwilson
     * @since 17 Feb 2015
     */
    class JsonSchemaDragDropReorderHandler extends TreeDragDropReorderHandler {
        /**
         * @param viewer
         */
        public JsonSchemaDragDropReorderHandler(StructuredViewer viewer) {
            super(viewer);
        }

        @Override
        public boolean isValidTarget(Object source, Object target, int location) {
            boolean valid = false;
            Object sourceItem = getUmlElement(source);
            Object targetItem = getUmlElement(target);
            if (sourceItem instanceof Property
                    && targetItem instanceof NamedElement) {
                if (location == TreeDragDropReorderHandler.LOCATION_ON) {
                    if (targetItem instanceof Class || getViewer() == schema) {
                        valid = getUmlClass((NamedElement) targetItem) != null;
                    }
                } else if (location == TreeDragDropReorderHandler.LOCATION_AFTER
                        || location == TreeDragDropReorderHandler.LOCATION_BEFORE) {
                    valid = targetItem instanceof Property;
                }
            } else if (sourceItem instanceof Class
                    && targetItem instanceof Property) {
                valid = true;
            }
            return valid;
        }

        @Override
        public boolean move(Object source, Object target, int location) {
            boolean moved = false;
            Command cmd =
                    getReparentCommand(getUmlElement(source),
                            getUmlElement(target),
                            location);
            if (cmd != null) {
                moved = execute(cmd);
            }
            return moved;
        }
    }

    /**
     * Sid XPD-7861: Runnable that can be used to aysnchExec() setting the
     * selection on currently selected tree control after a new property (or
     * something to select) is selected.
     * <p>
     * This is a rework of one of the changes made in XPD-7099 that used to hold
     * a newSelection field in main class and set that up ready for next
     * doRefresh() as it created and returned the command to add a property.
     * This caused other issues in copy/paste handling, the fix for which then
     * caused this regression (XPD-7861, selection changes to newly added
     * property all the time when editing in property sheet of a different
     * selection).
     * 
     * @author aallway
     * @since 14 Sep 2015
     */
    private class SetSelectionOnAddedProperty implements Runnable {

        private Object newSelection;

        /**
         * @param newSelection
         */
        public SetSelectionOnAddedProperty(Object newSelection) {
            super();
            this.newSelection = newSelection;
        }

        /**
         * @see java.lang.Runnable#run()
         * 
         */
        @Override
        public void run() {
            if (newSelection != null) {
                if (schema != null && schema.getControl() != null
                        && !schema.getControl().isDisposed()
                        && focussed == schema) {
                    if (newSelection instanceof Property) {
                        Property property = (Property) newSelection;
                        ISelection selection = schema.getSelection();
                        if (selection instanceof StructuredSelection) {
                            StructuredSelection ss =
                                    (StructuredSelection) selection;
                            if (ss.size() == 1) {
                                Object parent = ss.getFirstElement();
                                UmlTreePropertyNode parentNode = null;
                                if (parent instanceof UmlTreePropertyNode) {
                                    parentNode = (UmlTreePropertyNode) parent;
                                }
                                UmlTreePropertyNode node =
                                        new UmlTreePropertyNode(parentNode,
                                                property);

                                schema.setSelection(new StructuredSelection(
                                        node));
                            }
                        }
                    }

                } else if (classes != null && classes.getControl() != null
                        && !classes.getControl().isDisposed()
                        && focussed == classes) {
                    classes.setSelection(new StructuredSelection(newSelection));
                }
            }
        }
    }

    /**
     * Sets read-only state for the control.
     * 
     * @param readOnly
     *            the read-only state
     */
    public void setReadOnly(boolean readOnly) {
        this.isReadOnly = readOnly;

        // Pass the information to the ViewerControls to update actions.
        if (schemaTreeControl != null) {
            schemaTreeControl.setReadOnly(readOnly);
        }
        if (classesTreeControl != null) {
            classesTreeControl.setReadOnly(readOnly);
        }

        // Update main editor header text.
        if (rootForm != null) {
            rootForm.setText(getEditorHeaderLabel());
        }
    }

    /**
     * Gets current read-only state of the control.
     * 
     * @return read-only state of the control.
     */
    public boolean isReadOnly() {
        return this.isReadOnly;
    }

    /**
     * Returns the main header text.
     */
    private String getEditorHeaderLabel() {
        if (isReadOnly) {
            return String.format("%1$s [%2$s]", Messages.JsonSchemaEditor_title, Messages.JsonSchemaEditor_ReadOnly); //$NON-NLS-1$
        }
        return Messages.JsonSchemaEditor_title;
    }

}
