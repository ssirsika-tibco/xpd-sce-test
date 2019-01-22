/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.processinterface.editor.editors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.graphics.Image;
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

import com.tibco.xpd.analyst.processinterface.editor.ProcessInterfaceEditorConstants;
import com.tibco.xpd.analyst.processinterface.editor.ProcessInterfaceEditorPlugin;
import com.tibco.xpd.analyst.processinterface.editor.dnd.EventsDropTargetListener;
import com.tibco.xpd.analyst.processinterface.editor.dnd.ParameterDragSourceListener;
import com.tibco.xpd.analyst.processinterface.editor.properties.InterfaceTreeNode;
import com.tibco.xpd.analyst.processinterface.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.newparameter.NewParameterWizard;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.processinterfaces.NewIntermediateMethodWizard;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.processinterfaces.NewStartMethodWizard;
import com.tibco.xpd.processeditor.xpdl2.properties.general.ServiceProcessDeploymentTargetSection;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.projectexplorer.providers.ProjectExplorerLabelProvider;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resolvers.Xpdl2FieldOrParamDeleter;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Implementation of the AbstractEObject section on the editor. Used to display
 * process interface controls in a form editor
 * 
 * @author rsomayaj
 * 
 * 
 */
public class ProcessInterfaceEObjectSection extends
        AbstractFilteredTransactionalSection {

    protected Label nameText;

    private Text descText;

    private Button addStartEvent;

    private Button removeStartEvent;

    private Button addIntermediateEvent;

    private Button removeIntermediateEvent;

    private Button addFormalParameter;

    private Button removeFormalParameter;

    private TableViewer formalParametersViewer;

    private TreeViewer intermediateEventsViewer;

    private TreeViewer startEventsViewer;

    private ProjectExplorerLabelProvider labelProvider;

    private FormEditor editor;

    private ISelectionProvider selectionProvider;

    ServiceProcessDeploymentTargetSection deploymentTargetSection;

    private Label deploymentTargetsLabel;

    private Composite deploymentTargetsGroup;

    /*
     * Prevent recursion when setting selection (we listen to AND set recursion
     * :o(
     */
    private boolean performingSelection = false;

    private static String INTERFACE_FOLDER = "interface.folder.marker"; //$NON-NLS-1$

    private static String ERRORS_FOLDER = "errors.folder.marker"; //$NON-NLS-1$

    private HashMap<EObject, InterfaceTreeNode> treeNodeMap =
            new HashMap<EObject, InterfaceTreeNode>();

    public ProcessInterfaceEObjectSection() {

        super(XpdExtensionPackage.eINSTANCE.getProcessInterface());
        deploymentTargetSection =
                new ServiceProcessDeploymentTargetSection(
                        Messages.ProcessInterfaceEObjectSection_DeploymentTarget_desc_text);
    }

    public ProcessInterfaceEObjectSection(FormEditor editor) {
        this();
        this.editor = editor;
        this.selectionProvider = editor.getSite().getSelectionProvider();
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#setInput(java.util.Collection)
     * 
     * @param items
     */
    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);
        deploymentTargetSection.setInput(items);
    }

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

        labelProvider = new ProjectExplorerLabelProvider() {
            @Override
            public String getText(Object element) {
                if (element instanceof InterfaceTreeNode) {
                    Object realEl = ((InterfaceTreeNode) element).getValue();

                    if (realEl == INTERFACE_FOLDER) {
                        return Messages.ProcessInterfaceEObjectSection_InterfaceHeader_label;
                    }
                    if (realEl == ERRORS_FOLDER) {
                        return Messages.ProcessInterfaceEObjectSection_ErrorsFolder_label;
                    }

                    return super.getText(realEl);
                }
                return super.getText(element);

            }

            @Override
            public Image getImage(Object element) {
                if (element instanceof InterfaceTreeNode) {
                    Object realEl = ((InterfaceTreeNode) element).getValue();

                    if (realEl == INTERFACE_FOLDER) {
                        return ProcessInterfaceEditorPlugin
                                .getDefault()
                                .getImageRegistry()
                                .get(ProcessInterfaceEditorConstants.IMG_INTERFACEPARAMINOUT);
                    }
                    if (realEl == ERRORS_FOLDER) {
                        return ProcessInterfaceEditorPlugin.getDefault()
                                .getImageRegistry()
                                .get(ProcessInterfaceEditorConstants.IMG_ERROR);
                    }
                    if (realEl instanceof AssociatedParameter) {
                        return super
                                .getImage(ProcessInterfaceUtil
                                        .getProcessRelevantDataFromAssociatedParam((AssociatedParameter) realEl));
                    }

                    return super.getImage(realEl);
                }
                return super.getImage(element);
            }
        };

        createGeneralInformationSection(form, toolkit);
        createStartEventsSection(form, toolkit);
        createFormalParametersSection(form, toolkit);
        createIntermediateEventsSection(form, toolkit);
        editor.getSite().getSelectionProvider()
                .addSelectionChangedListener(selectionChangedListener);
        return form;
    }

    @SuppressWarnings("Unchecked")
    @Override
    protected Command doGetCommand(Object obj) {

        if (obj == addStartEvent) {

            openDialog(new NewStartMethodWizard());
        } else if (obj == addIntermediateEvent) {

            openDialog(new NewIntermediateMethodWizard());
        } else if (obj == addFormalParameter) {

            openDialog(new NewParameterWizard());
        } else if (obj == removeStartEvent
                && startEventsViewer.getSelection() != null) {

            List<EObject> toDelete = new ArrayList<EObject>();

            TreeSelection selection =
                    (TreeSelection) startEventsViewer.getSelection();
            for (Iterator iterator = selection.iterator(); iterator.hasNext();) {
                Object toDel = iterator.next();

                if (toDel instanceof InterfaceTreeNode
                        && ((InterfaceTreeNode) toDel).getValue() instanceof EObject) {

                    toDelete.add((EObject) ((InterfaceTreeNode) toDel)
                            .getValue());
                }
            }

            CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.ProcessInterfaceEObjectSection_CmdRmvStartMethod_label);
            cmd.append(RemoveCommand.create(getEditingDomain(), toDelete));
            return cmd;

        } else if (obj == removeIntermediateEvent
                && intermediateEventsViewer.getSelection() != null) {

            List<EObject> toDelete = new ArrayList<EObject>();

            TreeSelection selection =
                    (TreeSelection) intermediateEventsViewer.getSelection();
            for (Iterator iterator = selection.iterator(); iterator.hasNext();) {

                Object toDel = iterator.next();
                if (toDel instanceof InterfaceTreeNode
                        && ((InterfaceTreeNode) toDel).getValue() instanceof EObject) {

                    toDelete.add((EObject) ((InterfaceTreeNode) toDel)
                            .getValue());
                }
            }

            CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.ProcessInterfaceEObjectSection_CmdRmvStartMethod_label);
            cmd.append(RemoveCommand.create(getEditingDomain(), toDelete));
            return cmd;

        } else if (obj == removeFormalParameter
                && formalParametersViewer.getSelection() != null) {

            CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.ProcessInterfaceEObjectSection_CmdRmvParameterMethod_label);
            if (formalParametersViewer.getSelection() instanceof StructuredSelection) {

                StructuredSelection structSel =
                        (StructuredSelection) formalParametersViewer
                                .getSelection();
                if (structSel.toList() != null) {

                    for (Object selElement : structSel.toList()) {

                        if (selElement instanceof FormalParameter) {

                            createRemoveFormalParameterReferencesCommand((FormalParameter) selElement,
                                    cmd);
                        }
                    }
                }
            }
            cmd.append(RemoveCommand.create(getEditingDomain(),
                    ((StructuredSelection) formalParametersViewer
                            .getSelection()).toList()));
            return cmd;

        } else if (obj == descText) {

            CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.ProcessInterfaceEObjectSection_CmdSetProcessDesc_label);
            Description procIfcDesc = getProcessInterface().getDescription();
            if (procIfcDesc == null) {

                procIfcDesc = Xpdl2Factory.eINSTANCE.createDescription();
                cmd.append(SetCommand.create(getEditingDomain(),
                        getProcessInterface(),
                        Xpdl2Package.eINSTANCE
                                .getDescribedElement_Description(),
                        procIfcDesc));

            }

            cmd.append(SetCommand.create(getEditingDomain(),
                    procIfcDesc,
                    Xpdl2Package.eINSTANCE.getDescription_Value(),
                    descText.getText()));
            return cmd;
        }
        return null;
    }

    // TODO: This code is duplicated in
    // com.tibco.xpd.analyst.resources.xpdl2.projectexplorer
    // .actions.DeleteAction Needs refactoring
    private CompoundCommand createRemoveFormalParameterReferencesCommand(
            FormalParameter formalParameter, CompoundCommand cmd) {
        ProcessInterface processInterface = getProcessInterface();
        if (processInterface != null && cmd != null) {
            //
            // If the formal parameter is in a process interface
            // then we must remove references to it from any
            // start/intermediate method in the interface.
            //
            Xpdl2FieldOrParamDeleter deleter = new Xpdl2FieldOrParamDeleter();

            /*
             * XPD-5427: Use new Xpdl2FieldOrParamDeleter method that deals with
             * all the objects that may contain references internally.
             */
            Command c =
                    deleter.getDeleteDataReferencesCommand(getEditingDomain(),
                            formalParameter);

            if (c != null) {
                cmd.append(c);
            }

        }
        return cmd;
    }

    /**
     * @param processesAndInterfaces
     *            The process.
     * @param subFlow
     *            The sub-flow activity.
     * @return true if the sub-flow references one of the processes/process
     *         interfaces.
     */
    private boolean isReferenced(List<EObject> processesAndInterfaces,
            SubFlow subFlow) {
        EObject invokedProcess = getSubprocessOrInterface(subFlow);

        for (EObject procOrIfc : processesAndInterfaces) {
            if (procOrIfc.equals(invokedProcess)) {
                return true;
            }
        }
        return false;
    }

    private EObject getSubprocessOrInterface(SubFlow sf) {

        EObject procOrIfc = null;
        Activity act = sf.getActivity();
        String procOrIntfcId = sf.getProcessId();
        String packageRefId = sf.getPackageRefId();

        if (packageRefId == null) {

            Process process = act.getProcess();
            Package xpdlPkg = process.getPackage();
            procOrIfc = xpdlPkg.getProcess(procOrIntfcId);
            if (procOrIfc == null) {

                /* Not a process, see if its a process interface */
                procOrIfc =
                        ProcessInterfaceUtil.getProcessInterface(xpdlPkg,
                                procOrIntfcId);
            }

        } else {
            /* Get the location of the external reference */
            Xpdl2WorkingCopyImpl wc =
                    (Xpdl2WorkingCopyImpl) WorkingCopyUtil
                            .getWorkingCopyFor(act);
            if (wc != null) {
                String location = wc.getExternalPackageLocation(packageRefId);
                if (location != null) {

                    IResource src = wc.getEclipseResources().get(0);
                    IProject project = src.getProject();

                    if (project != null) {
                        XpdProjectResourceFactory fact =
                                XpdResourcesPlugin.getDefault()
                                        .getXpdProjectResourceFactory(project);

                        if (fact != null) {
                            IResource res =
                                    fact.resolveResourceReference(src,
                                            location,
                                            Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND);
                            if (res != null) {
                                WorkingCopy externalWc =
                                        fact.getWorkingCopy(res);

                                if (externalWc != null) {
                                    Package externalPkg =
                                            (Package) externalWc
                                                    .getRootElement();

                                    if (externalPkg != null) {
                                        procOrIfc =
                                                externalPkg
                                                        .getProcess(procOrIntfcId);
                                        if (procOrIfc == null) {
                                            procOrIfc =
                                                    ProcessInterfaceUtil
                                                            .getProcessInterface(externalPkg,
                                                                    procOrIntfcId);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return procOrIfc;
    }

    @Override
    protected void doRefresh() {

        if (getProcessInterface() != null) {

            ProcessInterface processInterface = getProcessInterface();
            formalParametersViewer.setInput(processInterface);
            startEventsViewer.setInput(processInterface);
            intermediateEventsViewer.setInput(processInterface);
            /* XPD-1140: show proper display name (token name) */
            nameText.setText(WorkingCopyUtil.getText(processInterface));
            if (processInterface.getDescription() != null) {

                updateText(descText, processInterface.getDescription()
                        .getValue());
            }
            if (null != deploymentTargetSection) {

                deploymentTargetSection.refresh();
            }
        }
    }

    private void createGeneralInformationSection(Composite form,
            XpdFormToolkit toolkit) {
        Section generalSection =
                toolkit.createSection(form, Section.DESCRIPTION
                        | Section.EXPANDED | Section.TITLE_BAR);
        generalSection
                .setDescription(Messages.ProcessInterfaceEObjectSection_GeneralSection_longdesc);
        GridData td = new GridData(GridData.FILL_BOTH);

        generalSection.setLayoutData(td);
        generalSection
                .setText(Messages.ProcessInterfaceEObjectSection_GeneralSectionHeader_label);
        Composite composite = toolkit.createComposite(generalSection);

        GridLayout layout = new GridLayout(2, false);
        composite.setLayout(layout);

        nameLabel =
                toolkit.createLabel(composite,
                        Messages.ProcessInterfaceEObjectSection_Name_label);
        nameLabel.setLayoutData(new GridData());

        nameText = toolkit.createLabel(composite, ""); //$NON-NLS-1$
        nameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
                | GridData.VERTICAL_ALIGN_BEGINNING));

        /*
         * Begin - Service Process Interface - deployment configuration related
         * controls
         */
        ProcessInterface processInterface = getProcessInterface();
        boolean isServiceProcessInterface =
                Xpdl2ModelUtil.isServiceProcessInterface(processInterface);
        if (isServiceProcessInterface) {

            deploymentTargetsLabel =
                    toolkit.createLabel(composite,
                            Messages.ProcessInterfaceEObjectSection_DeploymentTarget_label);
            deploymentTargetsLabel.setLayoutData(new GridData(
                    GridData.VERTICAL_ALIGN_BEGINNING));

            deploymentTargetsGroup =
                    toolkit.createComposite(composite, SWT.BORDER);

            GridData gd2 = new GridData(GridData.FILL_HORIZONTAL);
            deploymentTargetsGroup.setLayoutData(gd2);

            GridLayout fl = new GridLayout(1, false);
            fl.marginHeight = 0;
            deploymentTargetsGroup.setLayout(fl);

            Control deployTargetsControl =
                    deploymentTargetSection
                            .createControls(deploymentTargetsGroup, toolkit);
            deployTargetsControl
                    .setLayoutData(new GridData(GridData.FILL_BOTH));
        }
        /*
         * End of Service Process Interface deployment configuration related
         * controls
         */

        Label descLabel =
                toolkit.createLabel(composite,
                        Messages.ProcessInterfaceEObjectSection_Desc_label);
        GridData gd =
                new GridData(GridData.FILL_VERTICAL
                        | GridData.VERTICAL_ALIGN_BEGINNING);
        descLabel.setLayoutData(gd);
        descText = toolkit.createText(composite, "", SWT.MULTI //$NON-NLS-1$
                | SWT.WRAP | SWT.V_SCROLL);
        descText.addFocusListener(descTextFocusListener);
        gd = new GridData(GridData.FILL_BOTH);
        gd.minimumHeight = 25;

        descText.setLayoutData(gd);
        manageControl(descText);

        generalSection.setClient(composite);
    }

    private void createStartEventsSection(Composite form, XpdFormToolkit toolkit) {
        Section startEventSection =
                toolkit.createSection(form, Section.DESCRIPTION
                        | Section.EXPANDED | Section.TITLE_BAR);

        GridData td = new GridData(GridData.FILL_BOTH);
        startEventSection.setLayoutData(td);
        startEventSection
                .setText(Messages.ProcessInterfaceEObjectSection_StartEventHeader_label);
        startEventSection
                .setDescription(Messages.ProcessInterfaceEObjectSection_StartEventsDesc_longdesc);
        Composite composite = toolkit.createComposite(startEventSection);
        composite.setLayout(new GridLayout(2, false));
        startEventsViewer = new TreeViewer(composite,/* SWT.MULTI | */
        SWT.V_SCROLL | SWT.BORDER | SWT.SINGLE);
        startEventsViewer.getTree().setLayoutData(new GridData(SWT.FILL,
                SWT.FILL, true, true));
        startEventsViewer.setContentProvider(startEventContentProvider);
        startEventsViewer.setLabelProvider(labelProvider);
        startEventsViewer.addSelectionChangedListener(selectionChangedListener);
        EventsDropTargetListener eventsDropTargetListener =
                new EventsDropTargetListener(startEventsViewer) {

                    @Override
                    protected boolean performDropCommand(EObject targetEvent,
                            List<FormalParameter> srcParams) {
                        return doAddParamsCommand(targetEvent, srcParams);
                    }
                };

        startEventsViewer.addDropSupport(DND.DROP_MOVE,
                new Transfer[] { LocalSelectionTransfer.getTransfer() },
                eventsDropTargetListener);

        Composite inButtons = toolkit.createComposite(composite);
        inButtons.setLayout(new GridLayout(1, false));
        inButtons.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
        addStartEvent =
                toolkit.createButton(inButtons,
                        Messages.ProcessInterfaceEObjectSection_NewBtn_label,
                        SWT.PUSH);
        addStartEvent.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true,
                false));
        removeStartEvent =
                toolkit.createButton(inButtons,
                        Messages.ProcessInterfaceEObjectSection_RemoveBtn_label,
                        SWT.PUSH);
        removeStartEvent.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true,
                false));
        manageControl(addStartEvent);
        manageControl(removeStartEvent);
        startEventSection.setClient(composite);
    }

    private void createIntermediateEventsSection(Composite form,
            XpdFormToolkit toolkit) {
        Section intermediateEventsSection =
                toolkit.createSection(form, Section.DESCRIPTION
                        | Section.EXPANDED | Section.TITLE_BAR);

        GridData td = new GridData(SWT.RIGHT | SWT.BOTTOM | GridData.FILL_BOTH);
        intermediateEventsSection.setLayoutData(td);
        intermediateEventsSection
                .setText(Messages.ProcessInterfaceEObjectSection_IntermediateEventsHeader_label);
        intermediateEventsSection
                .setDescription(Messages.ProcessInterfaceEObjectSection_IntermediateMethodsDesc_longdesc);
        Composite composite =
                toolkit.createComposite(intermediateEventsSection);
        composite.setLayout(new GridLayout(2, false));
        intermediateEventsViewer =
                new TreeViewer(composite, SWT.MULTI | SWT.V_SCROLL | SWT.BORDER);
        intermediateEventsViewer.getTree().setLayoutData(new GridData(SWT.FILL,
                SWT.FILL, true, true));
        intermediateEventsViewer
                .setContentProvider(intermediateEventsContentProvider);
        intermediateEventsViewer.setLabelProvider(labelProvider);
        intermediateEventsViewer
                .addSelectionChangedListener(selectionChangedListener);

        EventsDropTargetListener eventsDropTargetListener =
                new EventsDropTargetListener(intermediateEventsViewer) {

                    @Override
                    protected boolean performDropCommand(EObject targetEvent,
                            List<FormalParameter> srcParams) {

                        return doAddParamsCommand(targetEvent, srcParams);
                    }
                };

        intermediateEventsViewer.addDropSupport(DND.DROP_MOVE,
                new Transfer[] { LocalSelectionTransfer.getTransfer() },
                eventsDropTargetListener);

        Composite inButtons = toolkit.createComposite(composite);
        inButtons.setLayout(new GridLayout(1, false));
        inButtons.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
        addIntermediateEvent =
                toolkit.createButton(inButtons,
                        Messages.ProcessInterfaceEObjectSection_NewBtn_label,
                        SWT.PUSH);
        addIntermediateEvent.setLayoutData(new GridData(SWT.FILL, SWT.NONE,
                true, false));
        removeIntermediateEvent =
                toolkit.createButton(inButtons,
                        Messages.ProcessInterfaceEObjectSection_RemoveBtn_label,
                        SWT.PUSH);
        removeIntermediateEvent.setLayoutData(new GridData(SWT.FILL, SWT.NONE,
                true, false));
        manageControl(addIntermediateEvent);
        manageControl(removeIntermediateEvent);
        intermediateEventsSection.setClient(composite);
    }

    private void createFormalParametersSection(Composite form,
            XpdFormToolkit toolkit) {
        Section formalParametersSection =
                toolkit.createSection(form, Section.DESCRIPTION
                        | Section.EXPANDED | Section.TITLE_BAR);

        GridData td = new GridData(SWT.LEFT | SWT.BOTTOM | GridData.FILL_BOTH);
        formalParametersSection.setLayoutData(td);

        formalParametersSection
                .setText(Messages.ProcessInterfaceEObjectSection_FormalParamHeader_label);
        formalParametersSection
                .setDescription(Messages.ProcessInterfaceEObjectSection_ParamDesc_longdesc);
        Composite composite = toolkit.createComposite(formalParametersSection);
        composite.setLayout(new GridLayout(2, false));
        formalParametersViewer =
                new TableViewer(composite, SWT.MULTI | SWT.V_SCROLL
                        | SWT.BORDER);
        formalParametersViewer.getTable().setLayoutData(new GridData(SWT.FILL,
                SWT.FILL, true, true));
        formalParametersViewer
                .setContentProvider(formalParametersContentProvider);
        formalParametersViewer.setLabelProvider(labelProvider);
        Composite inButtons = toolkit.createComposite(composite);
        inButtons.setLayout(new GridLayout(1, false));
        inButtons.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
        addFormalParameter =
                toolkit.createButton(inButtons,
                        Messages.ProcessInterfaceEObjectSection_NewBtn_label,
                        SWT.PUSH);
        addFormalParameter.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true,
                false));
        removeFormalParameter =
                toolkit.createButton(inButtons,
                        Messages.ProcessInterfaceEObjectSection_RemoveBtn_label,
                        SWT.PUSH);
        removeFormalParameter.setLayoutData(new GridData(SWT.FILL, SWT.NONE,
                true, false));
        manageControl(addFormalParameter);
        manageControl(removeFormalParameter);

        formalParametersViewer
                .addSelectionChangedListener(selectionChangedListener);
        Transfer[] transfers =
                new Transfer[] { LocalSelectionTransfer.getTransfer() };
        formalParametersViewer.addDragSupport(DND.DROP_MOVE,
                transfers,
                new ParameterDragSourceListener(formalParametersViewer));
        formalParametersSection.setClient(composite);
    }

    private boolean doAddParamsCommand(EObject targetEvent,
            List<FormalParameter> srcParams) {

        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(Messages.ProcessInterfaceEObjectSection_CmdAddAssociatedParam_label);

        EList assocParams = null;

        if (targetEvent instanceof StartMethod) {
            StartMethod startMethod = (StartMethod) targetEvent;

            assocParams = startMethod.getAssociatedParameters();

        } else if (targetEvent instanceof IntermediateMethod) {
            IntermediateMethod interMethod = (IntermediateMethod) targetEvent;

            assocParams = interMethod.getAssociatedParameters();
        }

        if (assocParams == null) {
            return false;
        }

        Set<String> existingAssocParamNames = new HashSet<String>();
        for (Iterator iterator = assocParams.iterator(); iterator.hasNext();) {
            AssociatedParameter assocParam =
                    (AssociatedParameter) iterator.next();

            existingAssocParamNames.add(assocParam.getFormalParam());
        }

        boolean someAdded = false;
        List<AssociatedParameter> associatedParams =
                new ArrayList<AssociatedParameter>();
        for (FormalParameter param : srcParams) {
            if (!existingAssocParamNames.contains(param.getName())) {
                // This parameter is not there already, add it.
                someAdded = true;

                AssociatedParameter assocParam =
                        ProcessInterfaceUtil.createAssociatedParam(param);

                cmd.append(AddCommand
                        .create(getEditingDomain(),
                                targetEvent,
                                XpdExtensionPackage.eINSTANCE
                                        .getAssociatedParametersContainer_AssociatedParameters(),
                                assocParam));

                // cmd.append(new AddDataMappingCommand(getEditingDomain(),
                // targetEvent, assocParam));

                associatedParams.add(assocParam);
            }
        }

        if (someAdded) {
            getEditingDomain().getCommandStack().execute(cmd);
        }

        expandNode(targetEvent, associatedParams);

        return true;
    }

    /**
     * @param targetEvent
     * @param associatedParams
     */
    private void expandNode(EObject targetEvent,
            List<AssociatedParameter> associatedParams) {
        if (targetEvent instanceof StartMethod) {
            startEventsViewer.setExpandedState(treeNodeMap.get(targetEvent),
                    true);
        } else if (targetEvent instanceof IntermediateMethod) {
            intermediateEventsViewer.setExpandedState(treeNodeMap
                    .get(targetEvent), true);
        }
    }

    /**
     * Sid XPD-5460: Selecting items in package editor does not change input on
     * property sheet. This appears to have started happening since moved to
     * Eclipse Indigo platform.
     * 
     * The FormEditor's standard selection provider has some failings in that it
     * implements IPostSelectionProvider but doesn't actually do anything with
     * post selection listeners that are added to it when we call
     * setSelection().
     * 
     * THis means that things like the slection service for proeprty sheets add
     * themselves as post-selection listeners, but when we do a setSelection()
     * the FormEditor's MultiPageSelectionProvider only publishes the selection
     * event to its selection listeners NOT its post-selection listeners.
     * 
     * So we have had to add this explicit call to the fire event to
     * post-selection listeners in...
     * 
     * @param newSelection
     */
    private void doSelectionChanged(ISelection newSelection) {
        selectionProvider.setSelection(newSelection);

        if (selectionProvider instanceof MultiPageSelectionProvider) {
            ((MultiPageSelectionProvider) selectionProvider)
                    .firePostSelectionChanged(new SelectionChangedEvent(
                            selectionProvider, newSelection));
        }
    }

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

                            if (event.getSource() == startEventsViewer) {
                                // Deselect items from intermediate viewer and
                                // formal
                                // params
                                intermediateEventsViewer.getTree()
                                        .deselectAll();
                                formalParametersViewer.getTable().deselectAll();
                            }
                            if (event.getSource() == intermediateEventsViewer) {
                                // Deselect items from start and parameters
                                // viewer
                                startEventsViewer.getTree().deselectAll();
                                formalParametersViewer.getTable().deselectAll();
                            }
                            if (event.getSource() == formalParametersViewer) {
                                // Deselect items from start and intermediate
                                // viewer.
                                startEventsViewer.getTree().deselectAll();
                                intermediateEventsViewer.getTree()
                                        .deselectAll();
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
            // Do nothing
        }

        @Override
        public void focusGained(FocusEvent e) {
            startEventsViewer.getTree().deselectAll();
            intermediateEventsViewer.getTree().deselectAll();
            formalParametersViewer.getTable().deselectAll();

            doSelectionChanged(new StructuredSelection(getProcessInterface()));
        }
    };

    // Content provider for start events content provider.
    ITreeContentProvider startEventContentProvider =
            new ITreeContentProvider() {

                @Override
                public Object[] getChildren(Object parentElement) {
                    return ((InterfaceTreeNode) parentElement).getChildren();
                }

                @Override
                public Object getParent(Object element) {
                    return ((InterfaceTreeNode) element).getParent();
                }

                @Override
                public boolean hasChildren(Object element) {
                    return ((InterfaceTreeNode) element).hasChildren();
                }

                @Override
                public Object[] getElements(Object inputElement) {
                    List<InterfaceTreeNode> nodes =
                            new ArrayList<InterfaceTreeNode>();

                    if (inputElement instanceof ProcessInterface) {
                        ProcessInterface processInterface =
                                (ProcessInterface) inputElement;

                        for (StartMethod method : processInterface
                                .getStartMethods()) {
                            floodTreeElements(nodes, method);
                        }
                    }

                    return nodes.toArray();
                }

                @Override
                public void dispose() {
                    // Do nothing

                }

                @Override
                public void inputChanged(Viewer viewer, Object oldInput,
                        Object newInput) {
                    // Do nothing
                }

            };

    /**
     * @param nodes
     * @param method
     */
    private void floodTreeElements(List<InterfaceTreeNode> nodes,
            InterfaceMethod method) {
        InterfaceTreeNode methodNode = new InterfaceTreeNode(method);
        treeNodeMap.put(method, methodNode);
        methodNode.setParent(null);

        InterfaceTreeNode paramFolder = new InterfaceTreeNode(INTERFACE_FOLDER);
        paramFolder.setParent(methodNode);

        InterfaceTreeNode errorsFolder = new InterfaceTreeNode(ERRORS_FOLDER);
        errorsFolder.setParent(methodNode);

        methodNode.setChildren(new InterfaceTreeNode[] { paramFolder,
                errorsFolder });

        List<InterfaceTreeNode> assocParamNodes =
                new ArrayList<InterfaceTreeNode>();
        for (AssociatedParameter param : method.getAssociatedParameters()) {

            InterfaceTreeNode paramNode = new InterfaceTreeNode(param);
            paramNode.setParent(paramFolder);
            assocParamNodes.add(paramNode);
        }

        paramFolder.setChildren(assocParamNodes
                .toArray(new InterfaceTreeNode[assocParamNodes.size()]));

        List<InterfaceTreeNode> errorNodes = new ArrayList<InterfaceTreeNode>();
        for (ErrorMethod errorMethod : method.getErrorMethods()) {

            InterfaceTreeNode errorNode = new InterfaceTreeNode(errorMethod);
            errorNode.setParent(errorsFolder);
            errorNodes.add(errorNode);
        }

        errorsFolder.setChildren(errorNodes
                .toArray(new InterfaceTreeNode[errorNodes.size()]));

        nodes.add(methodNode);
    }

    // Content provider for intermediate methods table viewer.
    ITreeContentProvider intermediateEventsContentProvider =
            new ITreeContentProvider() {

                @Override
                public Object[] getChildren(Object parentElement) {
                    return ((InterfaceTreeNode) parentElement).getChildren();
                }

                @Override
                public Object getParent(Object element) {
                    return ((InterfaceTreeNode) element).getParent();
                }

                @Override
                public boolean hasChildren(Object element) {
                    return ((InterfaceTreeNode) element).hasChildren();
                }

                @Override
                public Object[] getElements(Object inputElement) {
                    List<InterfaceTreeNode> nodes =
                            new ArrayList<InterfaceTreeNode>();

                    if (inputElement instanceof ProcessInterface) {
                        ProcessInterface processInterface =
                                (ProcessInterface) inputElement;

                        for (IntermediateMethod method : processInterface
                                .getIntermediateMethods()) {
                            floodTreeElements(nodes, method);
                        }
                    }

                    return nodes.toArray();
                }

                @Override
                public void dispose() {
                    // Do nothing

                }

                @Override
                public void inputChanged(Viewer viewer, Object oldInput,
                        Object newInput) {
                    // Do nothing
                }

            };

    // Content provider for formal parameters table viewer
    IStructuredContentProvider formalParametersContentProvider =
            new IStructuredContentProvider() {

                @Override
                public Object[] getElements(Object inputElement) {
                    List<EObject> elements = new ArrayList<EObject>();
                    if (inputElement instanceof ProcessInterface) {
                        ProcessInterface processInterface =
                                (ProcessInterface) inputElement;
                        elements.addAll(processInterface.getFormalParameters());
                    }

                    return elements.toArray();
                }

                @Override
                public void dispose() {
                    // Do nothing
                }

                @Override
                public void inputChanged(Viewer viewer, Object oldInput,
                        Object newInput) {
                    // Do nothing
                }

            };

    private Label nameLabel;

    // Casted object of the input
    private ProcessInterface getProcessInterface() {
        if (getInput() != null && getInput() instanceof ProcessInterface) {
            return (ProcessInterface) getInput();
        }
        return null;
    }

    // Util to open the wizard when a "New..." Button is clicked.
    private void openDialog(INewWizard wizard) {
        wizard.init(PlatformUI.getWorkbench(), new StructuredSelection(
                getProcessInterface()));
        Shell shell = editor.getSite().getShell();
        WizardDialog dialog = new WizardDialog(shell, wizard);
        dialog.create();
        dialog.open();
    }

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
}