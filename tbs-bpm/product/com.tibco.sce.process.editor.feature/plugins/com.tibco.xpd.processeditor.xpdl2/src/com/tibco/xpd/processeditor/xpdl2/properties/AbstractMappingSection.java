/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.xpd.destinations.ui.GlobalDestinationHelper;
import com.tibco.xpd.destinations.ui.GlobalDestinationHelper.GlobalDestinationInfo;
import com.tibco.xpd.mapper.ErrorSeverity;
import com.tibco.xpd.mapper.IErrorProvider;
import com.tibco.xpd.mapper.IMappingListener;
import com.tibco.xpd.mapper.IMappingTransferValidator;
import com.tibco.xpd.mapper.IMoveableMappingListener;
import com.tibco.xpd.mapper.ITransformSection;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.mapper.MapperLabelProvider;
import com.tibco.xpd.mapper.MapperUtil;
import com.tibco.xpd.mapper.MapperViewer;
import com.tibco.xpd.mapper.MapperViewerInput;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.mapper.MappingDelta;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.mapper.MappingError;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.util.MappingsTypeMatcherUtility;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.validation.IValidationListener;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.ValidationEvent;
import com.tibco.xpd.validation.rules.ValidationUtil;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Properties section to display a mapper viewer.
 * 
 * @author scrossle, nwilson
 */
public abstract class AbstractMappingSection extends
        AbstractFilteredTransactionalSection implements
        IMoveableMappingListener, IErrorProvider, IValidationListener {

    /**
     * 
     */
    private static final String DELETE_MAPPINGS_TEXT = "deleteMappings"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String AUTOMAP_TEXT = "Auto-map"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String DELETE_MAPPINGS_ACTION_ID = "DeleteMappings"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String MAPPING_AUTO_MAP_ACTION_ID = "MappingAutoMap"; //$NON-NLS-1$

    private MappingDirection direction;

    /** The help context id. */
    private static final String HELP_CONTEXT =
            "com.tibco.xpd.implementer.resources.xpdl2.mapping"; //$NON-NLS-1$

    private static final int LAYOUT_COLUMNS = 2;

    /** The mapper viewer container. */
    private Composite container;

    /** The mapping viewer. */
    private MapperViewer mapperViewer;

    /** The transform section. */
    private ITransformSection transform;

    private boolean cellEditorEnabled = false;

    private long lastChangeSelectionTime = 0;

    private ISelection lastSelection;

    private MapperViewerInput mapperInput;

    private ActionContributionItem autoMapAction;

    private ActionContributionItem deleteMappingsAction;

    /**
     * Constructor.
     */
    public AbstractMappingSection(MappingDirection direction) {
        super(Xpdl2Package.eINSTANCE.getActivity());

        this.direction = direction;
        setShowInWizard(false);

        ValidationActivator.getDefault().addValidationListener(this);
    }

    /**
     * 
     * @see com.tibco.xpd.ui.properties.AbstractEObjectSection#dispose()
     */
    @Override
    public void dispose() {
        aboutToBeHidden();
        ValidationActivator.getDefault().removeValidationListener(this);
        super.dispose();
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#aboutToBeShown()
     * 
     */
    @Override
    public void aboutToBeShown() {
        if (getPropertySheetPage() != null) {
            showAutoMapButtons();
        }
    }

    /**
     * Show alignemtn buttons in the view toolbar.
     */
    protected void showAutoMapButtons() {
        IActionBars actionBars =
                getPropertySheetPage().getSite().getActionBars();

        if (actionBars != null) {
            IToolBarManager manager = actionBars.getToolBarManager();
            ActionContributionItem automapItem = getAutomapContribution();
            ActionContributionItem deleteMappingsItem =
                    getDeleteMappingsContribution();

            boolean shouldShowEditActions = true;

            /**
             * XPD-1074: Not display the Delete All Mappings button to the user
             * for Generated request activities or reply activities which reply
             * to generated request activities.
             */
            EObject object = getInput();
            if (object instanceof Activity) {
                shouldShowEditActions =
                        !(Xpdl2ModelUtil
                                .isGeneratedRequestActivity((Activity) object));
            } else if (object instanceof InterfaceMethod) {
                shouldShowEditActions = false;
            }

            if (shouldShowEditActions && object instanceof Activity) {

                boolean isReplyActivity =
                        ReplyActivityUtil.isReplyActivity((Activity) object);
                if (isReplyActivity) {
                    Activity requestActivityForReplyActivity =
                            ReplyActivityUtil
                                    .getRequestActivityForReplyActivity((Activity) object);
                    if (Xpdl2ModelUtil
                            .isGeneratedRequestActivity(requestActivityForReplyActivity)) {
                        shouldShowEditActions = false;
                    }
                }
            }

            /**
             * Add the delete mappings item to the menu manager if it should be
             * shown(Based on the criteria mentioned above
             */
            if (shouldShowEditActions) {
                if (deleteMappingsItem != null) {
                    manager.add(deleteMappingsItem);
                }
                if (automapItem != null) {
                    manager.add(automapItem);
                }
            }
            actionBars.updateActionBars();
        }
    }

    /**
     * Hide alignment buttons in the Property View toolbar.
     */
    protected void hideAutomapButtons() {
        IActionBars actionBars =
                getPropertySheetPage().getSite().getActionBars();

        // Remove the horizontal and vertical alignment controls in the
        // toolbar
        if (actionBars != null) {
            IToolBarManager manager = actionBars.getToolBarManager();
            boolean update = false;

            while (manager.remove(MAPPING_AUTO_MAP_ACTION_ID) != null) {
                update = true;
            }

            while (manager.remove(DELETE_MAPPINGS_ACTION_ID) != null) {
                update = true;
            }

            if (update) {
                // Update the action bar as it has changed
                actionBars.updateActionBars();
            }
        }
    }

    /**
     * Button to do auto-mapping
     * 
     * @return Horizontal Layout <code>ActionContributionItem</code>
     */
    protected ActionContributionItem getAutomapContribution() {
        if (autoMapAction == null) {
            Action action = new Action(AUTOMAP_TEXT, IAction.AS_PUSH_BUTTON) {
                @Override
                public void run() {
                    autoMapPressed();
                }
            };
            action.setId(MAPPING_AUTO_MAP_ACTION_ID);
            action.setToolTipText(Messages.AbstractMappingSection_AutomapTooltip_label);
            action.setImageDescriptor(Xpdl2ProcessEditorPlugin
                    .getImageDescriptor(ProcessEditorConstants.IMG_AUTOMAP));
            autoMapAction = new ActionContributionItem(action);
        }

        return autoMapAction;
    }

    /**
     * Button to delete all mappings from the section.
     * 
     * @return Horizontal Layout <code>ActionContributionItem</code>
     */
    protected ActionContributionItem getDeleteMappingsContribution() {
        if (deleteMappingsAction == null) {
            Action action =
                    new Action(DELETE_MAPPINGS_TEXT, IAction.AS_PUSH_BUTTON) {
                        @Override
                        public void run() {
                            deleteMappingsPressed();
                        }
                    };
            action.setId(DELETE_MAPPINGS_ACTION_ID);
            action.setToolTipText(Messages.AbstractMappingSection_DeleteMappingsTooltip_label);
            action.setImageDescriptor(Xpdl2ProcessEditorPlugin
                    .getImageDescriptor(ProcessEditorConstants.IMG_DELETE));
            deleteMappingsAction = new ActionContributionItem(action);
        }

        return deleteMappingsAction;
    }

    /**
     * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#aboutToBeHidden()
     * 
     */
    @Override
    public void aboutToBeHidden() {
        if (getPropertySheetPage() != null) {
            hideAutomapButtons();
        }
    }

    @Override
    public EObject getInput() {
        return super.getInput();
    }

    /**
     * @param parent
     *            The parent composite.
     * @param tk
     *            The toolkit for creating widgets.
     * @return The root control.
     * @see com.tibco.xpd.ui.properties.AbstractEObjectSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit tk) {

        /*
         * Create composite rather than use toolkit to work around the problem
         * of the focus being set on the script grammar combo when the mapping
         * section is selected. This causes a problem when, for instance, the
         * mouse-wheel is operated inadvertently which changes the grammar type
         * and breaks all mappings.
         */
        container = new Composite(parent, SWT.NONE) {
            @Override
            public boolean setFocus() {

                if (mapperViewer != null && mapperViewer.getControl() != null
                        && !mapperViewer.getControl().isDisposed()) {
                    if (mapperViewer.getControl().setFocus()) {
                        return true;
                    }
                }
                return super.setFocus();
            };
        };

        tk.adapt(container);
        tk.paintBordersFor(container);

        GridLayout gl = new GridLayout(LAYOUT_COLUMNS, false);
        gl.marginHeight = 0;
        container.setLayout(gl);

        Control headerLabel = createHeaderLabelControls(container, tk);
        headerLabel.setLayoutData(new GridData(GridData.FILL, GridData.FILL,
                true, false));

        Composite header = tk.createComposite(container);
        header.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false,
                false));
        header.setLayout(new GridLayout());
        createHeaderControls(header, tk);

        transform = getTransformSection();

        createMapperViewer(container);

        return container;
    }

    /**
     * Create the controls to populate the header label section of the screen.
     * 
     * @param parent
     * @param tk
     * 
     * @return Control that was created.
     */
    protected Control createHeaderLabelControls(Composite parent,
            XpdFormToolkit tk) {
        Label label = tk.createLabel(parent, getTitle());
        return label;
    }

    private void deleteMappingsPressed() {

        IStructuredContentProvider mappingContent =
                getContentProvider().getMappingContent();
        Object[] elements = mappingContent.getElements(getInput());
        List<Mapping> mappingsToDelete = new ArrayList<Mapping>();

        for (Object object : elements) {
            if (object instanceof Mapping) {
                mappingsToDelete.add((Mapping) object);
            }
        }

        CompoundCommand cmd = new CompoundCommand();
        // XPD-4275: Use new Interface [IMappingCommandFactory2] , with some
        // generic implementation for mapping update functions.
        IMappingCommandFactory2 commandFactory2 = getMappingCommandFactory2();
        Command command = null;
        for (Mapping mapping : mappingsToDelete) {
            command = null;
            // XPD-4275: Use the old factory[IMappingCommandFactory] ONLY when
            // the new[IMappingCommandFactory2] is not available.
            if (commandFactory2 == null) {
                IMappingCommandFactory commandFactory =
                        getMappingCommandFactory();
                // XPD-4275: As part of Deprecation Some implementations have
                // been modified to NOT use the IMappingCommandFactory anymore
                // hence will return null.
                if (commandFactory != null) {
                    command =
                            commandFactory
                                    .getRemoveMappingCommand(getEditingDomain(),
                                            getInput(),
                                            mapping.getSource(),
                                            mapping.getTarget());
                }
            } else {
                command =
                        commandFactory2
                                .getRemoveMappingCommand(getEditingDomain(),
                                        getInput(),
                                        mapping);
            }
            if (command != null) {
                cmd.append(command);
            }

        }

        if (getEditingDomain() != null && cmd.canExecute()) {
            getEditingDomain().getCommandStack().execute(cmd);
        }
    }

    /**
     * Listener when the auto-map button is pressed.
     */
    private void autoMapPressed() {
        Set<String> enabledGlobalDestinations = getEnabledGlobalDestinations();
        String scriptGrammar = getScriptGrammar();
        List<Mapping> typeMatchMappings =
                MappingsTypeMatcherUtility
                        .getTypeMatchMappings(enabledGlobalDestinations,
                                scriptGrammar,
                                getInput(),
                                getContentProvider().getTargetContent(),
                                getContentProvider().getSourceContent(),
                                getDirection(),
                                getExistingMappings());
        CompoundCommand cmd = new CompoundCommand();
        // XPD-4275: Use new Interface [IMappingCommandFactory2] , with some
        // generic implementation for mapping update functions.
        IMappingCommandFactory2 commandFactory2 = getMappingCommandFactory2();
        Command command = null;
        for (Mapping mapping : typeMatchMappings) {
            command = null;
            // XPD-4275: Use the old factory[IMappingCommandFactory] ONLY when
            // the new[IMappingCommandFactory2] is not available.
            if (commandFactory2 == null) {
                IMappingCommandFactory commandFactory =
                        getMappingCommandFactory();
                // XPD-4275: As part of Deprecation Some implementations have
                // been modified to NOT use the IMappingCommandFactory anymore
                // hence will return null.
                if (commandFactory != null) {
                    command =
                            commandFactory
                                    .getAddMappingCommand(getEditingDomain(),
                                            getInput(),
                                            mapping.getSource(),
                                            mapping.getTarget());
                }
            } else {
                command =
                        commandFactory2
                                .getAddMappingCommand(getEditingDomain(),
                                        getInput(),
                                        mapping.getSource(),
                                        mapping.getTarget());
            }
            if (command != null) {
                cmd.append(command);
            }
        }

        if (getEditingDomain() != null) {
            getEditingDomain().getCommandStack().execute(cmd);
        }

    }

    /**
     * 
     */
    private String getScriptGrammar() {
        String scriptGrammar = null;
        if (getInput() instanceof Activity) {
            Activity activity = (Activity) getInput();
            DirectionType directionType = null;
            if (MappingDirection.IN.equals(direction)) {
                directionType = DirectionType.IN_LITERAL;
            } else if (MappingDirection.OUT.equals(direction)) {
                directionType = DirectionType.OUT_LITERAL;
            }
            scriptGrammar =
                    ScriptGrammarFactory.getScriptGrammar(activity,
                            directionType);
            if (scriptGrammar == null) {
                scriptGrammar =
                        ScriptGrammarFactory.getDefaultScriptGrammar(activity);
            }
        }
        return scriptGrammar;
    }

    /**
     * @return
     */
    private Set<String> getEnabledGlobalDestinations() {
        Set<String> globalDestinationIds = new HashSet<String>();
        Collection<GlobalDestinationInfo> enabledGlobalDestinationsInfo =
                GlobalDestinationHelper
                        .getEnabledGlobalDestinationsInfo(Xpdl2ModelUtil
                                .getProcess(getInput()));
        for (GlobalDestinationInfo globalDestinationInfo : enabledGlobalDestinationsInfo) {
            globalDestinationIds.add(globalDestinationInfo.getId());
        }
        return globalDestinationIds;
    }

    /**
     * @return list of mappings that already exist
     */
    private List<Mapping> getExistingMappings() {
        List<Mapping> mappings = new ArrayList<Mapping>();
        if (getContentProvider() != null) {
            IStructuredContentProvider mappingContent =
                    getContentProvider().getMappingContent();
            if (mappingContent != null) {
                Object[] elements = mappingContent.getElements(getInput());
                for (Object object : elements) {
                    if (object instanceof Mapping) {
                        Mapping mapping = (Mapping) object;
                        mappings.add(mapping);
                    }
                }
            }
        }
        return mappings;
    }

    /**
     * Override to add viewer filters to the mapper.
     * 
     * @param mapperViewer2
     */
    protected void addViewerFilters(MapperViewer mapperViewer) {
    }

    /**
     * @param container
     */
    private void createMapperViewer(Composite container) {
        mapperViewer = doCreateMapperViewer(container);
        IWorkbenchSite site = getSite();
        mapperViewer.setSite(site);
        mapperViewer.getControl().setData(FormToolkit.KEY_DRAW_BORDER,
                FormToolkit.TREE_BORDER);
        mapperViewer.getControl().setLayoutData(new GridData(GridData.FILL,
                GridData.FILL, true, true, LAYOUT_COLUMNS, 1));

        mapperViewer.setContentProvider(getContentProvider());
        mapperViewer.setLabelProvider(getLabelProvider());
        mapperViewer.setTransferValidator(getTransferValidator());
        mapperViewer.addMappingListener(getMappingListener());
        mapperViewer.setTransformSection(transform);
        mapperViewer.setErrorProvider(this);
        // mapperViewer.setId(MapperUtil.getMapperId(getDirection(), (Activity)
        // getInput()));

        getMapperViewer().getSourceViewer()
                .addSelectionChangedListener(new ISelectionChangedListener() {

                    @Override
                    public void selectionChanged(SelectionChangedEvent event) {
                        ISelection selection =
                                getMapperViewer().getSourceViewer()
                                        .getSelection();
                        if (selection == null
                                || !selection.equals(lastSelection)) {
                            lastChangeSelectionTime =
                                    System.currentTimeMillis();
                        }
                        lastSelection = selection;
                    }

                });
        getMapperViewer().getSourceViewer().getTree()
                .addMouseListener(new MouseAdapter() {

                    private long MIN_RECLICK_TIME = 500;

                    @Override
                    public void mouseUp(MouseEvent e) {
                        if (e.button == 1) {
                            //
                            // If the left button is pressed on the already
                            // selected
                            // item then edit.
                            long sinceLast =
                                    System.currentTimeMillis()
                                            - lastChangeSelectionTime;

                            TreeItem treeItemUnderMouse =
                                    getMapperViewer().getSourceViewer()
                                            .getTree()
                                            .getItem(new Point(e.x, e.y));

                            Object clickedObject = null;
                            if (treeItemUnderMouse != null) {
                                clickedObject = treeItemUnderMouse.getData();
                            }
                            TreeItem[] selection =
                                    getMapperViewer().getSourceViewer()
                                            .getTree().getSelection();
                            if (clickedObject != null && selection.length == 1
                                    && clickedObject == selection[0].getData()
                                    && sinceLast > MIN_RECLICK_TIME) {
                                cellEditorEnabled = true;
                                getMapperViewer().getSourceViewer()
                                        .editElement(clickedObject, 0);
                                cellEditorEnabled = false;
                            }

                        }
                    }

                });
        setEditingSupport(mapperViewer);
        addViewerFilters(mapperViewer);

    }

    /**
     * Gives sub-class opportunity to sub-class MapperViewer if necessary. The
     * setting up of the mapper viewer (the view site, input etc is done in THIS
     * class so the sub-class does not have to.
     * 
     * @param container
     * @return The MappverViewer
     */
    protected MapperViewer doCreateMapperViewer(Composite container) {
        return new MapperViewer(container);
    }

    /**
     * Default implementation supports ScriptInformation editing.
     * 
     * @param mapperViewer
     *            The mapper viewer.
     */
    protected void setEditingSupport(MapperViewer mapperViewer) {
        EditingSupport editingSupport =
                new ScriptInformationEditingSupport(
                        mapperViewer.getSourceViewer());
        mapperViewer.setSourceEditingSupport(editingSupport);
    }

    /**
     * @param enable
     */
    protected void setMapperEnabled(boolean enable) {
        if (mapperViewer == null && enable) {
            createMapperViewer(container);
            mapperViewer.setContentProvider(getContentProvider());
            mapperViewer.setInput(mapperInput);
        } else if (mapperViewer != null && !enable) {
            mapperViewer.getControl().dispose();
            mapperViewer = null;
        }
    }

    /**
     * @param information
     * @param text
     */
    protected void updateName(ScriptInformation information, String value) {
        String oldValue = information.getName();
        // XPD-7804 Prevented empty script names.
        String newValue = value.trim();
        if (!newValue.equals(oldValue) && newValue.length() > 0) {
            CompoundCommand command =
                    new CompoundCommand(
                            Messages.AbstractMappingSection_EditScriptNameCommand);
            Activity activity = (Activity) getInput();
            EditingDomain ed = WorkingCopyUtil.getEditingDomain(activity);
            if (ed != null) {
                if (information.eContainer() == null) {
                    if (activity != null) {
                        information.setName(newValue);
                        command.append(Xpdl2ModelUtil
                                .getAddOtherElementCommand(ed,
                                        activity,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_Script(),
                                        information));
                    }
                } else {
                    command.append(SetCommand.create(ed,
                            information,
                            Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                            newValue));
                }
                if (command != null && command.canExecute()) {
                    ed.getCommandStack().execute(command);
                }
            }
        }
    }

    protected void updateInitialValue(InitialValue initial, String value) {
        String oldValue = initial.getValue();
        if (!value.equals(oldValue)) {
            Command command = initial.getSetValueCommand(value);
            Activity activity = (Activity) getInput();
            EditingDomain ed = WorkingCopyUtil.getEditingDomain(activity);
            if (ed != null && command != null && command.canExecute()) {
                ed.getCommandStack().execute(command);
            }
        }
    }

    /**
     * Default implementation does nothing. Override to add header controls. The
     * layout on the header composite should be set.
     * 
     * @param header
     *            The header composite.
     */
    protected void createHeaderControls(Composite header, XpdFormToolkit tk) {
    }

    /**
     * @return null.
     */
    protected ITransformSection getTransformSection() {
        return null;
    }

    /**
     * @return The current transform section.
     */
    protected ITransformSection getCurrentTransformSection() {
        return transform;
    }

    /**
     * @return The mapping listener.
     */
    protected IMappingListener getMappingListener() {
        return this;
    }

    /**
     * @return The mapping transfer validator.
     */
    protected abstract IMappingTransferValidator getTransferValidator();

    /**
     * @return Mapper Label provider for this section.
     */
    protected abstract MapperLabelProvider getLabelProvider();

    /**
     * @return The content provider for this section.
     */
    protected abstract MapperContentProvider getContentProvider();

    /**
     * Creates a new help control that provides access to context help.
     * <p>
     * The <code>TrayDialog</code> implementation of this method creates the
     * control, registers it for selection events including selection, Note that
     * the parent's layout is assumed to be a <code>GridLayout</code> and the
     * number of columns in this layout is incremented. Subclasses may override.
     * </p>
     * 
     * @param parent
     *            the parent composite
     * @return the help control
     */
    protected Control createHelpControl(Composite parent) {
        Image helpImage = JFaceResources.getImage(Dialog.DLG_IMG_HELP);
        if (helpImage != null) {
            return createHelpImageButton(parent, helpImage);
        }
        return null;
    }

    /**
     * @param parent
     *            The parent component.
     * @param image
     *            The help image.
     * @return The help control.
     */
    private ToolBar createHelpImageButton(Composite parent, Image image) {
        ToolBar toolBar = new ToolBar(parent, SWT.FLAT | SWT.NO_FOCUS);
        toolBar.setBackground(parent.getBackground());
        toolBar.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
        final Cursor cursor = new Cursor(parent.getDisplay(), SWT.CURSOR_HAND);
        toolBar.setCursor(cursor);
        toolBar.addDisposeListener(new DisposeListener() {
            @Override
            public void widgetDisposed(DisposeEvent e) {
                cursor.dispose();
            }
        });
        ToolItem item = new ToolItem(toolBar, SWT.NONE);
        item.setImage(image);
        item.setToolTipText(Messages.AbstractMappingSection_HelpButtonTooltip);
        item.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                helpPressed();
            }
        });
        return toolBar;
    }

    /**
     * Toggles the help for the mapping component.
     */
    private void helpPressed() {
        PlatformUI.getWorkbench().getHelpSystem().displayHelp(HELP_CONTEXT);
    }

    /**
     * @return The title to display above the mapper.
     */
    protected abstract String getTitle();

    /**
     * @param element
     *            The element to get the command for.
     * @return The command.
     * @see com.tibco.xpd.ui.properties.AbstractEObjectSection#doGetCommand(java.lang.Object)
     */
    @Override
    public Command doGetCommand(Object element) {
        return null;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     */
    @Override
    protected void doRefresh() {
        if (isInputSafe()) {
            if (mapperViewer != null) {
                mapperViewer.setId(MapperUtil.getMapperId(getDirection(),
                        getInput()));
                mapperViewer.refresh(null);
            }

            /*
             * Disabled automap button if there are no applicable typoe matcher
             * contributions.
             */
            if (autoMapAction != null) {
                boolean hasAutoMappers =
                        MappingsTypeMatcherUtility
                                .hasEnabledAutoMappers(getEnabledGlobalDestinations(),
                                        getScriptGrammar(),
                                        getInput(),
                                        getDirection());

                autoMapAction.getAction().setEnabled(hasAutoMappers);
            }
        }
    }

    /**
     * Return true if we have input and it's not in the process of being
     * deleted.
     * 
     * @return
     */
    private boolean isInputSafe() {
        if (getInput() != null && Xpdl2ModelUtil.getPackage(getInput()) != null) {
            return true;
        }
        return false;
    }

    /**
     * @param mapperInput
     *            The mapper viewer input.
     */
    protected void setMapperViewerInput(MapperViewerInput mapperInput) {
        this.mapperInput = mapperInput;
        if (mapperViewer != null) {
            mapperViewer.setInput(mapperInput);
        }
    }

    /**
     * @param contentProvider
     *            The mapper viewer content provider.
     */
    // protected void setMapperViewerContentProvider(
    // MapperContentProvider contentProvider) {
    // mapperContentProvider = contentProvider;
    // if (mapperViewer != null) {
    // mapperViewer.setContentProvider(contentProvider);
    // }
    // }
    /**
     * @return the mapperViewer
     */
    protected MapperViewer getMapperViewer() {
        return mapperViewer;
    }

    /**
     * 
     * @return The mapping command factory.
     * @deprecated This method is deprecated use getMappingCommandFactory2()
     *             instead.See getMappingCommandFactory2() for more details.
     */
    @Deprecated
    protected abstract IMappingCommandFactory getMappingCommandFactory();

    /**
     * This will be used in preference to getMappingCommandFactory() method, if
     * this returns a valid factory, it will be used.The old
     * getMappingCommandFactory() will not be used if
     * getMappingCommandFactory2() returns NOT-Null.By default this method
     * returns null, so the extending classes should implement this method to
     * return the appropriate factory.
     * 
     * @return The mapping command factory, with remove method that takes the
     *         mapping to be deleted.
     */
    protected IMappingCommandFactory2 getMappingCommandFactory2() {
        return null;
    }

    /**
     * @param mapping
     *            The mapping to add.
     * @see com.tibco.xpd.mapper.IMappingListener#mappingAdded(com.tibco.xpd.mapper.Mapping)
     */
    @Override
    public void mappingAdded(Mapping mapping) {
        EditingDomain editingDomain = getEditingDomain();
        // XPD-4275: Use new Interface [IMappingCommandFactory2] , with some
        // generic implementation for mapping update functions.
        IMappingCommandFactory2 commandFactory2 = getMappingCommandFactory2();
        Command command = null;
        // XPD-4275: Use the old factory[IMappingCommandFactory] ONLY when
        // the new[IMappingCommandFactory2] is not available.
        if (commandFactory2 == null) {
            IMappingCommandFactory commandFactory = getMappingCommandFactory();
            // XPD-4275: As part of Deprecation Some implementations have been
            // modified to NOT use the IMappingCommandFactory anymore hence will
            // return null.
            if (commandFactory != null) {
                command =
                        commandFactory.getAddMappingCommand(editingDomain,
                                getInput(),
                                mapping.getSource(),
                                mapping.getTarget());
            }
        } else {
            command =
                    commandFactory2.getAddMappingCommand(editingDomain,
                            getInput(),
                            mapping.getSource(),
                            mapping.getTarget());
        }

        if (command != null && command.canExecute()) {
            editingDomain.getCommandStack().execute(command);
        }
    }

    /**
     * @param mapping
     *            The mapping to remove.
     * @see com.tibco.xpd.mapper.IMappingListener#mappingRemoved(com.tibco.xpd.mapper.Mapping)
     */
    @Override
    public void mappingRemoved(Mapping mapping) {
        EditingDomain editingDomain = getEditingDomain();
        // XPD-4275: Use new Interface [IMappingCommandFactory2] , with some
        // generic implementation for mapping update functions.
        IMappingCommandFactory2 commandFactory2 = getMappingCommandFactory2();
        Command command = null;
        // XPD-4275: Use the old factory[IMappingCommandFactory] ONLY when
        // the new[IMappingCommandFactory2] is not available.
        if (commandFactory2 == null) {
            IMappingCommandFactory commandFactory = getMappingCommandFactory();
            // XPD-4275: As part of Deprecation Some implementations have been
            // modified to NOT use the IMappingCommandFactory anymore hence will
            // return null.
            if (commandFactory != null) {
                command =
                        commandFactory.getRemoveMappingCommand(editingDomain,
                                getInput(),
                                mapping.getSource(),
                                mapping.getTarget());
            }
        } else {
            command =
                    commandFactory2.getRemoveMappingCommand(editingDomain,
                            getInput(),
                            mapping);
        }
        if (command != null && command.canExecute()) {
            editingDomain.getCommandStack().execute(command);
        }
    }

    /**
     * Sid XPD-7601 - We now implement {@link IMoveableMappingListener} so that
     * we can handle moving mappings without losing extension attributes etc
     * 
     * @see com.tibco.xpd.mapper.IMoveableMappingListener#mappingMoved(com.tibco.xpd.mapper.Mapping,
     *      com.tibco.xpd.mapper.Mapping)
     * 
     * @param before
     * @param after
     */
    @Override
    public void mappingMoved(Mapping before, Mapping after) {
        IMappingCommandFactory2 commandFactory2 = getMappingCommandFactory2();

        /*
         * If subclass handles IMoveableMappingCommandFactory then do a move,
         * else do a delete and add
         */
        if (commandFactory2 instanceof IMoveableMappingCommandFactory) {
            Command moveMappingCommand =
                    ((IMoveableMappingCommandFactory) commandFactory2)
                            .getMoveMappingCommand(getEditingDomain(),
                                    getInput(),
                                    before,
                                    after);

            if (moveMappingCommand != null && moveMappingCommand.canExecute()) {
                getEditingDomain().getCommandStack()
                        .execute(moveMappingCommand);
            }

        } else {
            /*
             * Existing command factories will be asked to remove and add as
             * they always did (only that the separate remove and add used to
             * directed from Mapper itself (via the old IMappingListener
             * interface.
             */
            mappingRemoved(before);
            mappingAdded(after);
        }
    }

    /**
     * 
     * @see com.tibco.xpd.mapper.IMappingListener#mappingChanged(java.util.Collection)
     * 
     * @param changes
     * @deprecated This method is never called by the Mapper which always does
     *             remove and re-add
     */
    @Override
    @Deprecated
    public void mappingChanged(Collection<MappingDelta> changes) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.mapper.IErrorProvider#getSeverityForMapping(com.tibco.xpd
     * .mapper.Mapping)
     */
    @Override
    public final ErrorSeverity getSeverityForMappingSource(Mapping mapping) {
        DataMappingProblemMarkerList mappingProblemMarkerList =
                new DataMappingProblemMarkerList(mapping, true);

        return mappingProblemMarkerList.getMaxSeverity();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.mapper.IErrorProvider#getSeverityForMapping(com.tibco.xpd
     * .mapper.Mapping)
     */
    @Override
    public final ErrorSeverity getSeverityForMappingTarget(Mapping mapping) {
        DataMappingProblemMarkerList mappingProblemMarkerList =
                new DataMappingProblemMarkerList(mapping, false);

        return mappingProblemMarkerList.getMaxSeverity();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.mapper.IErrorProvider#getErrorsForMappingSource(com.tibco
     * .xpd .mapper.Mapping)
     */
    @Override
    public final MappingError getErrorsForMappingSource(Mapping mapping) {
        DataMappingProblemMarkerList mappingProblemMarkerList =
                new DataMappingProblemMarkerList(mapping, true);

        Collection<String> messages = mappingProblemMarkerList.getMessages();

        MappingError error = new MappingError(mapping);
        for (String message : messages) {
            error.addMessage(message);
        }

        return error;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.mapper.IErrorProvider#getErrorsForMappingTarget(com.tibco
     * .xpd .mapper.Mapping)
     */
    @Override
    public final MappingError getErrorsForMappingTarget(Mapping mapping) {
        DataMappingProblemMarkerList mappingProblemMarkerList =
                new DataMappingProblemMarkerList(mapping, false);

        Collection<String> messages = mappingProblemMarkerList.getMessages();

        MappingError error = new MappingError(mapping);
        for (String message : messages) {
            error.addMessage(message);
        }

        return error;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.mapper.IErrorProvider#getSeverityForSourceObject(java.lang
     * .Object)
     */
    @Override
    public final ErrorSeverity getSeverityForSourceObject(
            Object sourceTreeContentObject) {
        MappingSourceContentProblemMarkerList mappingSourceContentProblemMarkerList =
                new MappingSourceContentProblemMarkerList(
                        sourceTreeContentObject);

        return mappingSourceContentProblemMarkerList.getMaxSeverity();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.mapper.IErrorProvider#getErrorsForSourceObject(java.lang
     * .Object)
     */
    @Override
    public final Collection<String> getErrorsForSourceObject(
            Object sourceTreeContentObject) {
        MappingSourceContentProblemMarkerList mappingSourceContentProblemMarkerList =
                new MappingSourceContentProblemMarkerList(
                        sourceTreeContentObject);

        return mappingSourceContentProblemMarkerList.getMessages();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.mapper.IErrorProvider#getSeverityForTargetObject(java.lang
     * .Object)
     */
    @Override
    public final ErrorSeverity getSeverityForTargetObject(
            Object targetTreeContentObject) {
        MappingTargetContentProblemMarkerList mappingTargetContentProblemMarkerList =
                new MappingTargetContentProblemMarkerList(
                        targetTreeContentObject);

        return mappingTargetContentProblemMarkerList.getMaxSeverity();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.mapper.IErrorProvider#getErrorsForTargetObject(java.lang
     * .Object)
     */
    @Override
    public final Collection<String> getErrorsForTargetObject(
            Object targetTreeContentObject) {
        MappingTargetContentProblemMarkerList mappingTargetContentProblemMarkerList =
                new MappingTargetContentProblemMarkerList(
                        targetTreeContentObject);
        return mappingTargetContentProblemMarkerList.getMessages();
    }

    /**
     * This class has standard ability to look for validation problem markers
     * raised against the section input object that have the
     * {@link MapperContentProvider#DATAMAPPING_SOURCE_URI_ISSUEINFO} property
     * set as additional info in the marker.
     * <p>
     * The sub-class should provide the value that would be placed in this
     * additional info IF the given source object was the subject of the
     * problem.
     * <p>
     * This is then used to marry-up the markers with the specific
     * Mapper.mappings.
     * 
     * @param source
     *            object of the mapping
     * 
     * @return The source path for the given mapping.
     */
    protected abstract String getProblemMarkerDataMappingSourcePath(
            Object source);

    /**
     * This class has standard ability to look for validation problem markers
     * raised against the section input object that have the
     * {@link MapperContentProvider#DATAMAPPING_TARGET_URI_ISSUEINFO} property
     * set as additional info in the marker.
     * <p>
     * The sub-class should provide the value that would be placed in this
     * additional info IF the given source object was the subject of the
     * problem.
     * <p>
     * This is then used to marry-up the markers with the specific
     * Mapper.mappings.
     * 
     * @param target
     *            object of the mapping
     * 
     * @return The target path for the given mapping.
     */
    protected abstract String getProblemMarkerDataMappingTargetPath(
            Object target);

    /**
     * This class has standard ability to look for validation problem markers
     * raised against the section input object that have the
     * {@link MapperContentProvider#DATAMAPPING_TARGET_URI_ISSUEINFO} property
     * set as additional info in the marker.
     * <p>
     * The sub-class should provide a list of the URI's for all the current
     * mappings in the form that would be placed in the valdation problem
     * marker's LOCATION attribute.
     * <p>
     * This is then used to marry-up the markers with the specific
     * Mapper.mappings.
     * 
     * @param dataMappingPath
     *            as returned by #getProblemMarkerDataMappingSourcePath() or
     *            getProblemMarkerDataMappingTargetPath()
     * @param whether
     *            dataMappingPath is the source or target of path.
     * 
     * @return The list of existing data mapping uri's - this is used when
     *         evaluating the existence of problem markers for data mappings
     */
    protected abstract Collection<String> getProblemMarkerDataMappingURIs(
            String dataMappingPath, boolean isSourcePath);

    /**
     * @return The validation problem markers for the current section input.
     */
    private Collection<IMarker> getProblemMarkersForInput() {
        Collection<IMarker> markers = null;

        /*
         * Process widget has markers cached so in the very likely circumstance
         * that editor exists for this activity (which is nearly always true
         * except for when activity is selected in project explorer, which is
         * only for task library currently) get the markers from cache.
         */
        EObject input = getInput();
        if (input != null) {
            Adapter adapter =
                    new AdapterFactoryImpl().adapt(input,
                            ProcessWidgetConstants.ADAPTER_TYPE);
            if (adapter instanceof BaseProcessAdapter) {
                BaseProcessAdapter bpAdapter = (BaseProcessAdapter) adapter;

                List<IMarker> problemMarkerList =
                        bpAdapter.getProblemMarkerList(false);
                if (problemMarkerList != null) {
                    markers = problemMarkerList;
                }
            }

            if (markers == null) {
                /*
                 * Get markers the old fashioned way if couldn't access via
                 * diagram editro adapters.
                 */
                WorkingCopy workingCopyFor =
                        WorkingCopyUtil.getWorkingCopyFor(getInput());
                if (workingCopyFor != null) {
                    List<IResource> eclipseResources =
                            workingCopyFor.getEclipseResources();
                    if (eclipseResources != null) {
                        IResource resource = eclipseResources.get(0);
                        if (resource != null) {
                            try {
                                IMarker[] markerArray =
                                        resource.findMarkers(XpdConsts.VALIDATION_MARKER_TYPE,
                                                true,
                                                IResource.DEPTH_INFINITE);
                                if (markerArray != null) {
                                    markers = Arrays.asList(markerArray);
                                }
                            } catch (CoreException e) {
                                Xpdl2ProcessEditorPlugin
                                        .getDefault()
                                        .getLogger()
                                        .error(e,
                                                "Cannot set problem marker on mapping"); //$NON-NLS-1$
                            }
                        }
                    }
                }
            }
        }

        if (markers == null) {
            markers = Collections.emptyList();
        }

        return markers;
    }

    /**
     * @param event
     *            The validation event that occurred.
     */
    @Override
    public void validationEvent(ValidationEvent event) {
        // XPD-6835 Added filter to only refresh if the validation resource
        // matches the input resource.
        IResource resource = event.getResource();
        EObject in = getInput();
        if (in != null && resource != null
                && resource.equals(WorkingCopyUtil.getFile(in))) {
            if (isInputSafe()) {
                if (mapperViewer != null) {
                    Display.getDefault().asyncExec(new Runnable() {
                        @Override
                        public void run() {
                            if (mapperViewer != null) {
                                mapperViewer.refresh();
                                if (mapperViewer.getControl() instanceof Composite
                                        && !((Composite) mapperViewer.getControl()).isDisposed()) {
                                    ((Composite) mapperViewer.getControl())
                                            .layout();
                                }
                            }
                        }
                    });
                }
            }
        }

        return;
    }

    /**
     * MappingTargetContentProblemMarkerList
     * <p>
     * Problem marker list helper for mapper target tree content items
     * 
     * @author aallway
     * @since 3.3 (16 Dec 2009)
     */
    protected class MappingSourceContentProblemMarkerList extends
            AbstractFilteredProblemMarkerList {

        private String sourcePath;

        private String sectionInputObjectUri;

        private Collection<String> uris;

        protected MappingSourceContentProblemMarkerList(Object targetContent) {
            super();

            if (targetContent != null) {
                /*
                 * XPD-7075: Previously we were using wrong method(and were
                 * getting the target path), rather we should use
                 * "getProblemMarkerDataMappingSourcePath" which is the correct
                 * method.
                 */
                sourcePath =
                        getProblemMarkerDataMappingSourcePath(targetContent);
            }

            if (getInput() != null && getInput().eResource() != null) {
                sectionInputObjectUri =
                        getInput().eResource().getURIFragment(getInput());
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection
         * .AbstractFilteredProblemMarkerList
         * #isInterestingMarker(org.eclipse.core.resources.IMarker)
         */
        @Override
        protected boolean isInterestingMarker(IMarker marker)
                throws CoreException {

            if (marker != null && marker.exists() && sourcePath != null
                    && sourcePath.length() > 0 && sectionInputObjectUri != null
                    && sectionInputObjectUri.length() > 0) {
                Object location = marker.getAttribute(IMarker.LOCATION);
                if (location != null) {
                    String uri = location.toString();

                    if (sectionInputObjectUri.equals(uri)) {
                        Properties properties =
                                ValidationUtil.getAdditionalInfo(marker);
                        if (properties != null) {
                            String markerTargetContentPath =
                                    properties
                                            .getProperty(MapperContentProvider.SOURCE_URI_ISSUEINFO);
                            if (sourcePath.equals(markerTargetContentPath)) {
                                if (sourcePath.equals(markerTargetContentPath)) {
                                    /*
                                     * Sid XPD-7748: With the advent of data
                                     * mapper we can now have problem markers on
                                     * soruce and target in multiple scenarios
                                     * with the same direction. So now we need
                                     * to give the sub-class a chance to filter
                                     * whether to show problem markers on itself
                                     * to a finer grain of detail.
                                     */
                                    if (isProblemMarkerApplicable(marker,
                                            properties)) {
                                        return true;
                                    }

                                }
                            }
                        }
                    }
                }
            }
            return false;
        }

    }

    /**
     * MappingTargetContentProblemMarkerList
     * <p>
     * Problem marker list helper for mapper target tree content items
     * 
     * @author aallway
     * @since 3.3 (16 Dec 2009)
     */
    protected class MappingTargetContentProblemMarkerList extends
            AbstractFilteredProblemMarkerList {

        private String targetPath;

        private String sectionInputObjectUri;

        private Collection<String> uris;

        protected MappingTargetContentProblemMarkerList(Object targetContent) {
            super();

            if (targetContent != null) {
                targetPath =
                        getProblemMarkerDataMappingTargetPath(targetContent);
            }

            if (getInput() != null && getInput().eResource() != null) {
                sectionInputObjectUri =
                        getInput().eResource().getURIFragment(getInput());
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection
         * .AbstractFilteredProblemMarkerList
         * #isInterestingMarker(org.eclipse.core.resources.IMarker)
         */
        @Override
        protected boolean isInterestingMarker(IMarker marker)
                throws CoreException {
            if (marker != null && marker.exists() && targetPath != null
                    && targetPath.length() > 0 && sectionInputObjectUri != null
                    && sectionInputObjectUri.length() > 0) {
                Object location = marker.getAttribute(IMarker.LOCATION);
                if (location != null) {
                    String uri = location.toString();

                    if (sectionInputObjectUri.equals(uri)) {
                        Properties properties =
                                ValidationUtil.getAdditionalInfo(marker);
                        if (properties != null) {

                            String markerTargetContentPath =
                                    properties
                                            .getProperty(MapperContentProvider.TARGET_URI_ISSUEINFO);

                            if (targetPath.equals(markerTargetContentPath)) {
                                /*
                                 * Sid XPD-7748: With the advent of data mapper
                                 * we can now have problem markers on soruce and
                                 * target in multiple scenarios with the same
                                 * direction. So now we need to give the
                                 * sub-class a chance to filter whether to show
                                 * problem markers on itself to a finer grain of
                                 * detail.
                                 */
                                if (isProblemMarkerApplicable(marker,
                                        properties)) {
                                    return true;
                                }

                            }
                        }
                    }
                }
            }
            return false;
        }

    }

    /**
     * DataMappingProblemMarkerList
     * <p>
     * Problem marker list helper for data mappings
     * 
     * @author aallway
     * @since 3.3 (16 Dec 2009)
     */
    protected class DataMappingProblemMarkerList extends
            AbstractFilteredProblemMarkerList {

        private String path;

        private Collection<String> uris;

        private boolean isMappingSourceProblem;

        private Mapping mapping;

        protected DataMappingProblemMarkerList(Mapping mapping,
                boolean isMappingSourceProblem) {
            super();
            this.mapping = mapping;

            this.isMappingSourceProblem = isMappingSourceProblem;
            if (isMappingSourceProblem) {
                path =
                        getProblemMarkerDataMappingSourcePath(mapping
                                .getSource());
            } else {
                path =
                        getProblemMarkerDataMappingTargetPath(mapping
                                .getTarget());
            }
            if (path != null && path.length() > 0) {
                uris =
                        getProblemMarkerDataMappingURIs(path,
                                isMappingSourceProblem);
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection
         * .AbstractFilteredProblemMarkerList
         * #isInterestingMarker(org.eclipse.core.resources.IMarker)
         */
        @Override
        protected boolean isInterestingMarker(IMarker marker)
                throws CoreException {
            if (marker != null && marker.exists() && path != null
                    && path.length() > 0 && uris != null) {

                Object location = marker.getAttribute(IMarker.LOCATION);

                if (location != null) {
                    String uri = location.toString();
                    Properties properties =
                            ValidationUtil.getAdditionalInfo(marker);
                    if (properties != null) {
                        String addInfoPath;
                        if (isMappingSourceProblem) {
                            addInfoPath =
                                    properties
                                            .getProperty(MapperContentProvider.DATAMAPPING_SOURCE_URI_ISSUEINFO);
                        } else {
                            addInfoPath =
                                    properties
                                            .getProperty(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO);
                        }

                        if (uris.contains(uri)) {
                            /*
                             * Sid XPD-7399. If THIS mapping's path is not same
                             * as that in the problem marker, then check if
                             * there are any virtual mappings for this same
                             * mapping model element that match the path. If so
                             * we're interested because we want to shown the
                             * marker on both the virtual mapping AND it's
                             * physical mapping that implies the virtual mapping
                             */
                            if (path.equals(addInfoPath)
                                    || hasChildVirtualMappingWithPath(addInfoPath)) {
                                /*
                                 * Only interesting if the mapping direction is
                                 * undefined in marker or is the same as this
                                 * section.
                                 */
                                if (isMarkerCorrectMappingDirection(properties)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
            return false;
        }

        /**
         * @param addInfoPath
         * @return <code>true</code> If there are any virtual mappings for the
         *         same data mapping model as the actual mapping we were
         *         constructed with
         */
        private boolean hasChildVirtualMappingWithPath(String addInfoPath) {
            boolean virtualChildMatchesPath = false;

            if (mapperViewer != null) {
                for (Mapping otherMapping : mapperViewer.getMappings()) {

                    if ((otherMapping != mapping && !mapping.isVirtual())
                            && otherMapping.isVirtual()
                            && mapping.getMappingModel()
                                    .equals(otherMapping.getMappingModel())) {
                        String mappingPath;

                        if (isMappingSourceProblem) {
                            mappingPath =
                                    getProblemMarkerDataMappingSourcePath(otherMapping
                                            .getSource());
                        } else {
                            mappingPath =
                                    getProblemMarkerDataMappingTargetPath(otherMapping
                                            .getTarget());
                        }

                        if (mappingPath != null
                                & mappingPath.equals(addInfoPath)) {
                            virtualChildMatchesPath = true;
                            break;
                        }
                    }
                }
            }
            return virtualChildMatchesPath;
        }

    }

    /**
     * AbstractFilteredProblemMarkerList
     * 
     * 
     * @author aallway
     * @since 3.3 (16 Dec 2009)
     */
    protected abstract class AbstractFilteredProblemMarkerList {

        /**
         * @param marker
         * @return true if it is a marker youare interested in.
         */
        protected abstract boolean isInterestingMarker(IMarker marker)
                throws CoreException;

        /**
         * @return The maximum severity of problem markers that are filtered in.
         */
        protected ErrorSeverity getMaxSeverity() {
            /*
             * Occasionally we catch markers in the middle of update/recache,
             * attempt to prevent this by trying again.
             */
            int failedCount = 0;
            long timeStart = System.currentTimeMillis();
            CoreException lastException = null;

            ErrorSeverity severity = ErrorSeverity.NONE;

            while (true) {
                Collection<IMarker> markers = getProblemMarkersForInput();
                if (!markers.isEmpty()) {
                    boolean failed = false;
                    for (IMarker marker : markers) {
                        /*
                         * Go thru the markers looking for any that are
                         * interesting to the sub-class.
                         */
                        try {
                            if (isInterestingMarker(marker)) {
                                Integer markerSeverity =
                                        (Integer) marker
                                                .getAttribute(IMarker.SEVERITY);
                                if (IMarker.SEVERITY_ERROR == markerSeverity) {
                                    severity = ErrorSeverity.ERROR;
                                    break;
                                } else if (IMarker.SEVERITY_WARNING == markerSeverity) {
                                    severity = ErrorSeverity.WARNING;
                                }
                            }

                        } catch (CoreException e) {
                            lastException = e;
                            failed = true;
                            break;
                        }

                    } /* Next marker */

                    if (failed) {
                        if (failedCount++ <= 5
                                && (System.currentTimeMillis() - timeStart) < 1500) {
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            // Go round again.
                            continue;
                        }
                    }
                }

                /*
                 * If we get here we've either succeeeded or we have failed max
                 * times.
                 */
                break;

            }

            if (failedCount > 5) {
                Xpdl2ProcessEditorPlugin
                        .getDefault()
                        .getLogger()
                        .info("Problem (probably temporary) getting problem markers for mapping (" + lastException.getLocalizedMessage() + ")"); //$NON-NLS-1$ //$NON-NLS-2$
                return null;
            }

            return severity;
        }

        protected Collection<String> getMessages() {
            List<String> messages = new ArrayList<String>();

            /*
             * Occasionally we catch markers in the middle of update/recache,
             * attempt to prevent this by trying again.
             */
            int failedCount = 0;
            long timeStart = System.currentTimeMillis();
            CoreException lastException = null;

            while (true) {
                Collection<IMarker> markers = getProblemMarkersForInput();
                if (!markers.isEmpty()) {
                    /*
                     * Get the list of location uri's for our data mappings.
                     */
                    boolean failed = false;
                    for (IMarker marker : markers) {
                        try {
                            if (isInterestingMarker(marker)) {
                                Object msgAttr =
                                        marker.getAttribute(IMarker.MESSAGE);

                                if (msgAttr != null) {
                                    String message = msgAttr.toString();

                                    /*
                                     * Strip the "(<location>)" off of the end
                                     * of the problem marker message - user has
                                     * already selected that activity the
                                     * location describes so doesn't need it!)
                                     */
                                    if (message != null && message.length() > 0) {
                                        int idx = message.lastIndexOf('(');
                                        if (idx > 0) {
                                            message = message.substring(0, idx);
                                        }

                                        messages.add(message);
                                    }
                                }
                            }
                        } catch (CoreException e) {
                            lastException = e;
                            failed = true;
                            break;
                        }
                    } /* Next marker */

                    if (failed) {
                        if (failedCount++ <= 5
                                && (System.currentTimeMillis() - timeStart) < 1500) {
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            // Go round again.
                            continue;
                        }
                    }
                }

                /*
                 * If we get here we've either succeeded or we have failed max
                 * times.
                 */
                break;
            }

            if (failedCount > 5) {
                Xpdl2ProcessEditorPlugin
                        .getDefault()
                        .getLogger()
                        .info("Problem (probably temporary) getting problem markers for mapping (" + lastException.getLocalizedMessage() + ")"); //$NON-NLS-1$ //$NON-NLS-2$
            }

            return messages;
        }

    }

    /**
     * 
     * ScriptInformationEditingSupport
     * 
     * 
     */
    class ScriptInformationEditingSupport extends EditingSupport {

        private TreeViewer viewer;

        /**
         * @param viewer
         */
        public ScriptInformationEditingSupport(TreeViewer viewer) {
            super(viewer);
            this.viewer = viewer;
        }

        /**
         * @param element
         * @return
         * @see org.eclipse.jface.viewers.EditingSupport#canEdit(java.lang.Object)
         */
        @Override
        protected boolean canEdit(Object element) {
            boolean can = false;
            if (cellEditorEnabled
                    && (element instanceof ScriptInformation || element instanceof InitialValue)) {
                can = true;
            }
            return can;
        }

        /**
         * @param element
         * @return
         * @see org.eclipse.jface.viewers.EditingSupport#getCellEditor(java.lang.Object)
         */
        @Override
        protected CellEditor getCellEditor(Object element) {
            CellEditor editor = null;
            if (element instanceof ScriptInformation) {
                editor = new TextCellEditor(viewer.getTree());
            } else if (element instanceof InitialValue) {
                InitialValue value = (InitialValue) element;
                String[] items = value.getAllowedValues();
                editor = new ComboBoxCellEditor(viewer.getTree(), items);
            }
            return editor;
        }

        /**
         * @param element
         * @return
         * @see org.eclipse.jface.viewers.EditingSupport#getValue(java.lang.Object)
         */
        @Override
        protected Object getValue(Object element) {
            Object value = null;
            if (element instanceof ScriptInformation) {
                value = ((ScriptInformation) element).getName();
            } else if (element instanceof InitialValue) {
                InitialValue initial = (InitialValue) element;
                String[] items = initial.getAllowedValues();
                String current = initial.getValue();
                if (current != null) {
                    for (int i = 0; i < items.length; i++) {
                        if (current.equals(items[i])) {
                            value = new Integer(i);
                            break;
                        }
                    }
                }
            }
            return value;
        }

        /**
         * @param element
         * @param value
         * @see org.eclipse.jface.viewers.EditingSupport#setValue(java.lang.Object,
         *      java.lang.Object)
         */
        @Override
        protected void setValue(Object element, Object value) {
            if (element instanceof ScriptInformation) {
                if (value instanceof String) {
                    ScriptInformation information = (ScriptInformation) element;
                    updateName(information, (String) value);
                }
            } else if (element instanceof InitialValue) {
                if (value instanceof Integer) {
                    InitialValue initial = (InitialValue) element;
                    int index = ((Integer) value).intValue();
                    String[] items = initial.getAllowedValues();
                    updateInitialValue(initial, items[index]);
                }
            }
        }
    }

    /**
     * @return The mapping direction.
     */
    protected MappingDirection getDirection() {
        return direction;
    }

    /**
     * Check whether the given problem marker is applicable to this section's
     * content / mapping scenario.
     * <p>
     * This controls whether a problem marker will appear or not in this mapping
     * scenario context.
     * <p>
     * The problem marker will already have been checked as to whether it is
     * applicable to the section input.
     * <p>
     * Default implementation checks the mappng direction contenxt of section
     * with the mapping direction in marker additional info.
     * 
     * @param marker
     * @param problemMarkerAdditionalInfo
     * @return <code>true</code> if the marker is applicable to this section.
     */
    protected boolean isProblemMarkerApplicable(IMarker marker,
            Properties problemMarkerAdditionalInfo) {

        /*
         * Default is to use old method (compare mapping direction in marker)
         * Only interesting if the mapping direction is undefined in marker or
         * is the same as this section.
         */
        return isMarkerCorrectMappingDirection(problemMarkerAdditionalInfo);
    }

    /**
     * * Check Problem Marker for optional mapping-direction info
     * <p>
     * Prior to this, whether a problem marker was shown on source/target
     * content was identified by the Actvity-URI and objectPath (i.e.
     * conceptPath name etc) alone.
     * <p>
     * That means if Mapping-In section on a task has a target with exactly the
     * same "path" (i.e. parameter name) as a target in the Mapping-Out section
     * then both sections will show a problem marker even though it only belongs
     * on one of them.
     * 
     * @param properties
     * @return <code>true</code> if the
     *         {@link MapperContentProvider#MAPPING_DIRECTION_ISSUEINFO}
     *         optional mapping direction is NOT defined in marker OR if it is
     *         defined and is the same as the mapping direction for this mapping
     *         section
     */
    protected boolean isMarkerCorrectMappingDirection(Properties properties) {
        String markerMappingDirection =
                properties
                        .getProperty(MapperContentProvider.MAPPING_DIRECTION_ISSUEINFO);
        if (markerMappingDirection == null
                || markerMappingDirection.length() == 0
                || markerMappingDirection.equals(direction.name())) {
            return true;
        }
        return false;
    }
}
