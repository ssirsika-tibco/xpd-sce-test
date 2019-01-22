/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.editor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.MultiPageSelectionProvider;

import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.n2.globalsignal.resource.internal.Messages;
import com.tibco.xpd.n2.globalsignal.resource.ui.GsdElementComparator;
import com.tibco.xpd.n2.globalsignal.resource.ui.GsdLabelProvider;
import com.tibco.xpd.n2.globalsignal.resource.wizards.NewGlobalSignalCreationWizard;
import com.tibco.xpd.n2.globalsignal.resource.wizards.NewPayloadDataCreationWizard;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Implementation of the AbstractEObject section on the editor. Used to display
 * Global Signal Definition controls in a form editor.
 * 
 * @author sajain
 * @since Feb 10, 2015
 */
public class GsdEObjectSection extends AbstractFilteredTransactionalSection {

    private static final String GSD_FILE_EXTENSION = "gsd"; //$NON-NLS-1$

    private boolean performingSelection = false;

    /**
     * Implementation of the AbstractEObject section on the editor. Used to
     * display Global Signal Definition controls in a form editor.
     * 
     * @param eClass
     */
    public GsdEObjectSection() {

        super(GlobalSignalDefinitionPackage.eINSTANCE
                .getGlobalSignalDefinitions());
    }

    /**
     * Implementation of the AbstractEObject section on the editor. Used to
     * display Global Signal Definition controls in a form editor.
     * 
     * @param editor
     */
    public GsdEObjectSection(FormEditor editor) {

        this();
        this.editor = editor;
        this.selectionProvider = editor.getSite().getSelectionProvider();
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {

        /*
         * Object to test must be an IFile with '.gsd' extension.
         */
        if (toTest instanceof IFile) {

            IFile file = (IFile) toTest;

            if (GSD_FILE_EXTENSION.equals(file.getFileExtension())) {
                return true;
            }
        }

        return false;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection#resollveInput(java.lang.Object)
     * 
     * @param object
     * @return
     */
    @Override
    protected EObject resollveInput(Object object) {

        /*
         * Resolve input to always have GlobalSignalDefinitions (which is the
         * root element in the gsd file) as our input.
         */

        if (object instanceof IFile) {

            IFile file = (IFile) object;

            if (GSD_FILE_EXTENSION.equals(file.getFileExtension())) {

                WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(file);

                if (wc != null) {

                    if (wc.getRootElement() instanceof GlobalSignalDefinitions) {

                        GlobalSignalDefinitions gsds =
                                (GlobalSignalDefinitions) (wc.getRootElement());

                        return gsds;
                    }
                }
            }
        } else if (object instanceof GlobalSignalDefinitions) {

            return (GlobalSignalDefinitions) object;
        } else if (object instanceof GlobalSignal) {
            System.out.println();
        }

        return null;
    }

    /**
     * Description text.
     */
    private Text descText;

    /**
     * Button to Add a Global Signal.
     */
    private Button addGlobalSignal;

    /**
     * Button to Remove a Global Signal.
     */
    private Button removeGlobalSignal;

    /**
     * Viewer to show all the Global Signals in the GSD.
     */
    private TableViewer globalSignalViewer;

    /**
     * Button to Add a Payload Data.
     */
    private Button addPayloadData;

    /**
     * Button to Remove a Payload Data.
     */
    private Button removePayloadData;

    /**
     * Viewer to show all the Payload Data in the selected Global Signal.
     */
    private TableViewer payloadDataViewer;

    /**
     * Form editor.
     */
    private FormEditor editor;

    /**
     * Selection provider.
     */
    private ISelectionProvider selectionProvider;

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#getSite()
     * 
     * @return
     */
    @Override
    protected IWorkbenchSite getSite() {
        IWorkbenchSite site = super.getSite();
        if (site == null) {
            site = editor.getSite();
        }
        return site;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {

        if (getGSD() != null) {

            /*
             * Get GSD.
             */
            GlobalSignalDefinitions gsds = getGSD();

            /*
             * First handle global signal viewer.
             */

            if (globalSignalViewer.getInput() != gsds) {

                globalSignalViewer.setInput(gsds);
            }

            globalSignalViewer.refresh();

            /*
             * Now payload data viewer.
             */
            if (globalSignalViewer.getSelection() instanceof StructuredSelection) {

                StructuredSelection structSel =
                        (StructuredSelection) globalSignalViewer.getSelection();

                if (structSel.toList() != null && !structSel.toList().isEmpty()) {

                    addPayloadData.setEnabled(true);
                    removePayloadData.setEnabled(true);

                    for (Object selElement : structSel.toList()) {

                        if (selElement instanceof GlobalSignal) {

                            GlobalSignal selectedGlobalSignal =
                                    (GlobalSignal) selElement;

                            if (payloadDataViewer.getInput() != selectedGlobalSignal) {

                                payloadDataViewer
                                        .setInput(selectedGlobalSignal);

                            }
                        }
                    }

                } else if (structSel.toList().isEmpty()) {

                    addPayloadData.setEnabled(false);
                    removePayloadData.setEnabled(false);
                }
            }

            payloadDataViewer.refresh();

            Collection<GlobalSignalDefinitions> gsdsCollection =
                    new ArrayList<GlobalSignalDefinitions>();

            gsdsCollection.add(gsds);

            setInput(gsdsCollection);

            if (gsds.getDescription() != null) {

                updateText(descText, gsds.getDescription());
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

        Composite form = toolkit.createComposite(parent);
        GridData gd = new GridData(GridData.FILL_BOTH);
        form.setLayoutData(gd);

        form.addFocusListener(descTextFocusListener);
        GridLayout layout = new GridLayout(2, true);
        layout.horizontalSpacing = 1;
        layout.verticalSpacing = 1;

        form.setLayout(layout);

        /*
         * Create general information section.
         */
        createGeneralInformationSection(form, toolkit);

        /*
         * Create Global Signals section.
         */
        createGlobalSignalsSection(form, toolkit);

        /*
         * Create payload data section.
         */
        createPayloadDataSection(form, toolkit);

        /*
         * Add selection changed listener on the editor.
         */
        editor.getSite().getSelectionProvider()
                .addSelectionChangedListener(selectionChangedListener);

        return form;
    }

    /**
     * Create Global Signals section.
     * 
     * @param form
     * @param toolkit
     */
    private void createGlobalSignalsSection(Composite form,
            XpdFormToolkit toolkit) {

        /*
         * Global signals section.
         */
        Section globalSignalsSection =
                toolkit.createSection(form, Section.DESCRIPTION
                        | Section.EXPANDED | Section.TITLE_BAR);

        /*
         * Layout data for global signals section.
         */
        GridData td = new GridData(SWT.LEFT | SWT.BOTTOM | GridData.FILL_BOTH);
        globalSignalsSection.setLayoutData(td);

        /*
         * Set description and text on global signals section.
         */
        globalSignalsSection
                .setText(Messages.GSDEObjectSection_GlobalSignalHeader_label);
        globalSignalsSection
                .setDescription(Messages.GSDEObjectSection_GlobalSignalDesc_longdesc);

        /*
         * Composite to contain the global signal viewer.
         */
        Composite composite = toolkit.createComposite(globalSignalsSection);
        composite.setLayout(new GridLayout(2, false));

        /*
         * Global signal viewer.
         */
        globalSignalViewer =
                new TableViewer(composite, SWT.MULTI | SWT.V_SCROLL
                        | SWT.BORDER);
        globalSignalViewer.getTable().setLayoutData(new GridData(SWT.FILL,
                SWT.FILL, true, true));
        globalSignalViewer.setContentProvider(globalSignalsContentProvider);
        globalSignalViewer.setLabelProvider(new GsdLabelProvider());

        /*
         * XPD-7537: Saket: Setting comparator on Global Signal viewer to sort
         * the global signals.
         */
        globalSignalViewer.setComparator(new GsdElementComparator());

        /*
         * Composite to contain the 'Add' and 'Remove' signal buttons.
         */
        Composite inButtons = toolkit.createComposite(composite);
        inButtons.setLayout(new GridLayout(1, false));
        inButtons.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));

        /*
         * Button to 'Add' a global signal.
         */
        addGlobalSignal = toolkit.createButton(inButtons, "", SWT.PUSH); //$NON-NLS-1$
        addGlobalSignal.setImage(ProcessWidgetPlugin.getDefault()
                .getImageRegistry().get(ProcessWidgetConstants.IMG_ADD));

        addGlobalSignal.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true,
                false));

        /*
         * Button to 'Remove' a global signal.
         */
        removeGlobalSignal = toolkit.createButton(inButtons, "", SWT.PUSH); //$NON-NLS-1$
        removeGlobalSignal.setImage(ProcessWidgetPlugin.getDefault()
                .getImageRegistry().get(ProcessWidgetConstants.IMG_DELETE));

        removeGlobalSignal.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true,
                false));

        manageControl(addGlobalSignal);
        manageControl(removeGlobalSignal);

        /*
         * Add selection changed listener to the Global Signal viewer.
         */
        globalSignalViewer
                .addSelectionChangedListener(selectionChangedListener);
        Transfer[] transfers =
                new Transfer[] { LocalSelectionTransfer.getTransfer() };

        /*
         * Add drag support over global signal viewer.
         */
        globalSignalViewer.addDragSupport(DND.DROP_MOVE,
                transfers,
                new GlobalSignalDragSourceListener(globalSignalViewer));
        globalSignalsSection.setClient(composite);
    }

    /*
     * Content provider for global signals table viewer.
     */
    IStructuredContentProvider globalSignalsContentProvider =
            new IStructuredContentProvider() {

                @Override
                public Object[] getElements(Object inputElement) {

                    List<EObject> elements = new ArrayList<EObject>();

                    if (inputElement instanceof GlobalSignalDefinitions) {

                        /*
                         * Get the root element of the GSD file which is an
                         * instance of GlobalSignalDefinitions.
                         */
                        GlobalSignalDefinitions gsds =
                                (GlobalSignalDefinitions) inputElement;

                        /*
                         * Get all global signals from it and add to elements
                         * list.
                         */

                        elements.addAll(gsds.getGlobalSignals());

                    }

                    return elements.toArray();
                }

                @Override
                public void dispose() {
                    /*
                     * Do nothing
                     */
                }

                @Override
                public void inputChanged(Viewer viewer, Object oldInput,
                        Object newInput) {
                    /*
                     * Do nothing
                     */
                }

            };

    /*
     * Content provider for payload data table viewer
     */
    IStructuredContentProvider payloadDataContentProvider =
            new IStructuredContentProvider() {

                @Override
                public Object[] getElements(Object inputElement) {

                    List<EObject> elements = new ArrayList<EObject>();

                    if (inputElement instanceof GlobalSignal) {

                        elements.addAll(((GlobalSignal) inputElement)
                                .getPayloadDataFields());

                    }

                    return elements.toArray();
                }

                @Override
                public void dispose() {
                    /*
                     * Do nothing
                     */
                }

                @Override
                public void inputChanged(Viewer viewer, Object oldInput,
                        Object newInput) {
                    /*
                     * Do nothing
                     */
                }

            };

    /**
     * Create payload data section.
     * 
     * @param form
     * @param toolkit
     */
    private void createPayloadDataSection(Composite form, XpdFormToolkit toolkit) {

        /*
         * Payload Data section.
         */
        Section payloadDataSection =
                toolkit.createSection(form, Section.DESCRIPTION
                        | Section.EXPANDED | Section.TITLE_BAR);

        /*
         * Layout data for payload data section.
         */
        GridData td = new GridData(SWT.LEFT | SWT.BOTTOM | GridData.FILL_BOTH);
        payloadDataSection.setLayoutData(td);

        /*
         * Set description and text on payload section.
         */
        payloadDataSection
                .setText(Messages.GSDEObjectSection_PayloadDataHeader_label);
        payloadDataSection
                .setDescription(Messages.GSDEObjectSection_PayloadDataDesc_longdesc);

        /*
         * Composite to contain the payload data viewer.
         */
        Composite composite = toolkit.createComposite(payloadDataSection);
        composite.setLayout(new GridLayout(2, false));

        /*
         * Payload Data viewer.
         */
        payloadDataViewer =
                new TableViewer(composite, SWT.MULTI | SWT.V_SCROLL
                        | SWT.BORDER);
        payloadDataViewer.getTable().setLayoutData(new GridData(SWT.FILL,
                SWT.FILL, true, true));
        payloadDataViewer.setContentProvider(payloadDataContentProvider);
        payloadDataViewer.setLabelProvider(new GsdLabelProvider());

        /*
         * XPD-7537: Saket: Setting comparator on Payload Data viewer to sort
         * the Payload Data.
         */
        payloadDataViewer.setComparator(new GsdElementComparator());

        /*
         * Composite to contain the 'Add' and 'Remove' payload data buttons.
         */
        Composite inButtons = toolkit.createComposite(composite);
        inButtons.setLayout(new GridLayout(1, false));
        inButtons.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));

        /*
         * Button to 'Add' a Payload Data.
         */
        addPayloadData = toolkit.createButton(inButtons, "", SWT.PUSH); //$NON-NLS-1$
        addPayloadData.setImage(ProcessWidgetPlugin.getDefault()
                .getImageRegistry().get(ProcessWidgetConstants.IMG_ADD));

        addPayloadData.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true,
                false));

        addPayloadData.setEnabled(false);

        /*
         * Button to 'Remove' a Payload Data.
         */
        removePayloadData = toolkit.createButton(inButtons, "", SWT.PUSH); //$NON-NLS-1$

        removePayloadData.setImage(ProcessWidgetPlugin.getDefault()
                .getImageRegistry().get(ProcessWidgetConstants.IMG_DELETE));

        removePayloadData.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true,
                false));

        removePayloadData.setEnabled(false);

        manageControl(addPayloadData);
        manageControl(removePayloadData);

        /*
         * Add selection changed listener to the Payload Data viewer.
         */
        payloadDataViewer.addSelectionChangedListener(selectionChangedListener);
        Transfer[] transfers =
                new Transfer[] { LocalSelectionTransfer.getTransfer() };

        /*
         * Add drag support over payload data viewer.
         */
        payloadDataViewer.addDragSupport(DND.DROP_MOVE,
                transfers,
                new PayloadDataDragSourceListener(payloadDataViewer));
        payloadDataSection.setClient(composite);
    }

    /**
     * Create general information section.
     * 
     * @param form
     * @param toolkit
     */
    private void createGeneralInformationSection(Composite form,
            XpdFormToolkit toolkit) {

        /*
         * General section.
         */
        Section generalSection =
                toolkit.createSection(form, Section.DESCRIPTION
                        | Section.EXPANDED | Section.TITLE_BAR);

        generalSection
                .setDescription(Messages.GSDEObjectSection_GeneralSection_longdesc);

        GridData td = new GridData(GridData.FILL_BOTH);
        td.horizontalSpan = 2;

        generalSection.setLayoutData(td);

        generalSection
                .setText(Messages.GSDEObjectSection_GeneralSectionHeader_label);
        Composite composite = toolkit.createComposite(generalSection);

        GridLayout layout = new GridLayout(2, false);
        composite.setLayout(layout);

        /*
         * Description label.
         */
        Label descLabel =
                toolkit.createLabel(composite,
                        Messages.GSDEObjectSection_Desc_label);

        GridData gd =
                new GridData(GridData.FILL_VERTICAL
                        | GridData.VERTICAL_ALIGN_BEGINNING);

        descLabel.setLayoutData(gd);

        /*
         * Description text.
         */
        descText = toolkit.createText(composite, "", SWT.MULTI //$NON-NLS-1$
                | SWT.WRAP | SWT.V_SCROLL);

        descText.addFocusListener(descTextFocusListener);
        gd = new GridData(GridData.FILL_BOTH);
        descText.setLayoutData(gd);
        manageControl(descText);

        generalSection.setClient(composite);
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {

        if (obj == addGlobalSignal) {

            /*
             * Handle Add Global Signal Button.
             */
            openDialog(new NewGlobalSignalCreationWizard(), false);

        } else if (obj == removeGlobalSignal
                && globalSignalViewer.getSelection() != null) {

            /*
             * Handle Remove Global Signal Button.
             */
            CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.GSDEObjectSection_CmdRmvGlobalSignalMethod_label);

            if (globalSignalViewer.getSelection() instanceof StructuredSelection) {

                StructuredSelection structSel =
                        (StructuredSelection) globalSignalViewer.getSelection();

                if (structSel.toList() != null) {

                    for (Object selElement : structSel.toList()) {

                        if (selElement instanceof GlobalSignal) {

                            createRemoveGlobalSignalCommand((GlobalSignal) selElement,
                                    cmd);
                        }
                    }
                }
            }

            return cmd;

        } else if (obj == addPayloadData) {

            /*
             * Handle Add Payload Data Button.
             */
            openDialog(new NewPayloadDataCreationWizard(), true);

        } else if (obj == removePayloadData
                && payloadDataViewer.getSelection() != null) {

            /*
             * Handle Remove Payload Data Button.
             */
            CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.GSDEObjectSection_CmdRmvPayloadDataMethod_label);

            if (payloadDataViewer.getSelection() instanceof StructuredSelection) {

                StructuredSelection structSel =
                        (StructuredSelection) payloadDataViewer.getSelection();

                if (structSel.toList() != null) {

                    for (Object selElement : structSel.toList()) {

                        if (selElement instanceof PayloadDataField) {

                            createRemovePayloadDataCommand((PayloadDataField) selElement,
                                    cmd);
                        }
                    }
                }
            }

            return cmd;

        } else if (obj == descText) {

            /*
             * Handle GSD description text control.
             */
            if (getGSD() != null) {

                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.GSDEObjectSection_CmdSetGSDDesc_label);

                cmd.append(SetCommand.create(getEditingDomain(),
                        getGSD(),
                        GlobalSignalDefinitionPackage.eINSTANCE
                                .getGlobalSignalDefinitions_Description(),
                        descText.getText()));

                return cmd;
            }
        }
        return null;
    }

    /**
     * Return command to remove a global signal.
     * 
     * @param globalSignal
     * @param cmd
     * 
     * @return Command to remove a global signal.
     */
    private CompoundCommand createRemoveGlobalSignalCommand(
            GlobalSignal globalSignal, CompoundCommand cmd) {

        GlobalSignalDefinitions gsds = getGSD();

        if (gsds != null && cmd != null) {

            cmd.append(RemoveCommand.create(getEditingDomain(),
                    gsds,
                    GlobalSignalDefinitionPackage.eINSTANCE
                            .getGlobalSignalDefinitions_GlobalSignals(),
                    globalSignal));

        }
        return cmd;
    }

    /**
     * Return command to remove a payload data.
     * 
     * @param payloadData
     * @param cmd
     * 
     * @return Command to remove a payload data.
     */
    private CompoundCommand createRemovePayloadDataCommand(
            PayloadDataField payloadData, CompoundCommand cmd) {

        GlobalSignalDefinitions gsds = getGSD();

        if (gsds != null && cmd != null) {

            GlobalSignal parentGlobalSignal = null;

            for (GlobalSignal eachGlobalSignal : gsds.getGlobalSignals()) {

                if (eachGlobalSignal.getPayloadDataFields()
                        .contains(payloadData)) {

                    parentGlobalSignal = eachGlobalSignal;
                }
            }

            if (parentGlobalSignal != null) {

                cmd.append(RemoveCommand.create(getEditingDomain(),
                        parentGlobalSignal,
                        GlobalSignalDefinitionPackage.eINSTANCE
                                .getGlobalSignal_PayloadDataFields(),
                        payloadData));
            }
        }

        return cmd;
    }

    /*
     * Util to open the wizard when a "New..." Button is clicked.
     */
    private void openDialog(INewWizard wizard, boolean isPayloadData) {

        if (isPayloadData) {
            if (globalSignalViewer.getSelection() instanceof StructuredSelection) {

                StructuredSelection structSel =
                        (StructuredSelection) globalSignalViewer.getSelection();

                if (structSel.toList() != null) {

                    for (Object selElement : structSel.toList()) {

                        if (selElement instanceof GlobalSignal) {

                            GlobalSignal selectedGlobalSignal =
                                    (GlobalSignal) selElement;

                            wizard.init(PlatformUI.getWorkbench(),
                                    new StructuredSelection(
                                            selectedGlobalSignal));

                            break;
                        }
                    }
                }
            }

        } else {
            wizard.init(PlatformUI.getWorkbench(), new StructuredSelection(
                    getGSD()));
        }

        Shell shell = editor.getSite().getShell();
        WizardDialog dialog = new WizardDialog(shell, wizard);
        dialog.create();
        dialog.open();
    }

    private void doSelectionChanged(ISelection newSelection) {
        selectionProvider.setSelection(newSelection);

        if (selectionProvider instanceof MultiPageSelectionProvider) {
            ((MultiPageSelectionProvider) selectionProvider)
                    .firePostSelectionChanged(new SelectionChangedEvent(
                            selectionProvider, newSelection));
        }
    }

    /**
     * Selection changes listener.
     */
    ISelectionChangedListener selectionChangedListener =
            new ISelectionChangedListener() {

                @Override
                public void selectionChanged(SelectionChangedEvent event) {
                    /*
                     * Protect against recursing back into our own selection
                     * listeners.
                     */
                    if (!performingSelection) {
                        try {
                            performingSelection = true;

                            if (event.getSource() == globalSignalViewer) {
                                /*
                                 * Deselect items from start and intermediate
                                 * viewer.
                                 */
                                LocalSelectionTransfer.getTransfer()
                                        .setSelection(getSelection());

                                if (globalSignalViewer.getSelection() instanceof StructuredSelection) {

                                    StructuredSelection structSel =
                                            (StructuredSelection) globalSignalViewer
                                                    .getSelection();

                                    if (structSel.toList() != null
                                            && !structSel.toList().isEmpty()) {

                                        addPayloadData.setEnabled(true);
                                        removePayloadData.setEnabled(true);

                                        for (Object selElement : structSel
                                                .toList()) {

                                            if (selElement instanceof GlobalSignal) {

                                                GlobalSignal selectedGlobalSignal =
                                                        (GlobalSignal) selElement;

                                                if (payloadDataViewer
                                                        .getInput() != selectedGlobalSignal) {

                                                    payloadDataViewer
                                                            .setInput(selectedGlobalSignal);

                                                }
                                            }
                                        }

                                    } else if (structSel.toList().isEmpty()) {

                                        addPayloadData.setEnabled(false);
                                        removePayloadData.setEnabled(false);

                                    }

                                } else if (globalSignalViewer.getSelection() == null) {
                                    addPayloadData.setEnabled(false);
                                    removePayloadData.setEnabled(false);
                                }

                                payloadDataViewer.refresh();
                            }
                            if (event.getSource() == payloadDataViewer) {
                                /*
                                 * Deselect items from start and intermediate
                                 * viewer.
                                 */
                                LocalSelectionTransfer.getTransfer()
                                        .setSelection(getSelection());
                            }

                            ISelection selection = event.getSelection();

                            doSelectionChanged(selection);

                        } finally {
                            performingSelection = false;
                        }
                    }
                }

            };

    FocusListener descTextFocusListener = new FocusListener() {

        @Override
        public void focusLost(FocusEvent e) {
            /*
             * Do nothing
             */
        }

        @Override
        public void focusGained(FocusEvent e) {

            globalSignalViewer.getTable().deselectAll();
            payloadDataViewer.getTable().deselectAll();

            addPayloadData.setEnabled(false);
            removePayloadData.setEnabled(false);
            payloadDataViewer.setInput(null);

            doSelectionChanged(new StructuredSelection(getGSD()));
        }
    };

    /**
     * Casted object of the input
     */
    private GlobalSignalDefinitions getGSD() {

        if (getInput() instanceof IFile) {

            IFile file = (IFile) getInput();

            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(file);

            if (wc != null
                    && wc.getRootElement() instanceof GlobalSignalDefinitions) {

                GlobalSignalDefinitions gsds =
                        (GlobalSignalDefinitions) (wc.getRootElement());

                return gsds;
            }

        } else if (getInput() instanceof GlobalSignalDefinitions) {

            return (GlobalSignalDefinitions) (getInput());

        } else if (getInput() instanceof GlobalSignal) {

            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(getInput());

            if (wc != null
                    && wc.getRootElement() instanceof GlobalSignalDefinitions) {

                GlobalSignalDefinitions gsds =
                        (GlobalSignalDefinitions) (wc.getRootElement());

                return gsds;
            }
        }
        return null;
    }

}
