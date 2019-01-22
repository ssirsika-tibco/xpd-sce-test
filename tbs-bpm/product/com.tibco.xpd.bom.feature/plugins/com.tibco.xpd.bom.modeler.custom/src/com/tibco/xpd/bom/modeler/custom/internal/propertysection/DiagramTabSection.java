/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.propertysection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.workspace.EMFOperationCommand;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.custom.internal.diagram.NewDiagramWizard;
import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.BOMImages;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.ViewerAction;
import com.tibco.xpd.resources.ui.components.actions.TableAddAction;
import com.tibco.xpd.resources.ui.components.actions.TableDeleteAction;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.gmf.AbstractGMFWorkingCopy;

/**
 * The {@link Diagram} tab properties section. This allows the additions,
 * deletion and opening of additions diagrams in a BOM.
 * 
 * @author njpatel
 * 
 */
public class DiagramTabSection extends AbstractTableSection {

    public DiagramTabSection() {
        super(Messages.DiagramTabSection_title, null);
    }

    @Override
    protected void addColumns(ColumnViewer viewer) {
        new DiagramNameColumn(getEditingDomain(), viewer);
    }

    @Override
    protected Collection<ViewerAction> getAdditionalActions(ColumnViewer viewer) {
        Set<ViewerAction> actions = new HashSet<ViewerAction>();
        actions.add(new OpenAction(viewer));
        return actions;
    }

    @Override
    protected TableAddAction getAddAction(ColumnViewer viewer) {
        return new TableAddAction(viewer, Messages.DiagramTabSection_addDiagram_action, Messages.DiagramTabSection_addDiagram_action_tooltip) {

            @Override
            protected Object addRow(StructuredViewer viewer) {
                NewDiagramWizard wizard = new NewDiagramWizard();
                wizard.hideFileSelectionPage();
                wizard.init(PlatformUI.getWorkbench(), new StructuredSelection(
                        getInputFile()));
                WizardDialog dlg =
                        new WizardDialog(viewer.getControl().getShell(), wizard);
                if (dlg.open() == WizardDialog.OK) {
                    return wizard.getNewDiagram();
                }
                return null;
            }

        };
    }

    /**
     * Get the file being edited.
     * 
     * @return
     */
    private IFile getInputFile() {
        EObject input = getInput();
        if (input != null) {
            return WorkingCopyUtil.getFile(input);
        }
        return null;
    }

    @Override
    protected TableDeleteAction getDeleteAction(ColumnViewer viewer) {
        return new TableDeleteAction(viewer, Messages.DiagramTabSection_deleteDiagram_action,
                Messages.DiagramTabSection_deleteDiagram_action_tooltip) {

            @Override
            protected void deleteRows(IStructuredSelection selection) {
                ICommand result = null;

                if (!selection.isEmpty()) {
                    if (selection.size() == 1) {
                        result =
                                getDeleteCommand((Diagram) selection
                                        .getFirstElement());
                    } else {
                        CompositeCommand ccmd =
                                new CompositeCommand(Messages.DiagramTabSection_deleteDiagram_command_label);

                        for (Iterator<?> iter = selection.iterator(); iter
                                .hasNext();) {
                            ccmd.add(getDeleteCommand((Diagram) iter.next()));
                        }
                        result = ccmd;
                    }
                }

                if (result != null) {
                    getEditingDomain().getCommandStack()
                            .execute(new EMFOperationCommand(XpdResourcesPlugin
                                    .getDefault().getEditingDomain(), result));
                }
            }

            /**
             * Get the command to delete the given Diagram.
             * 
             * @param diagram
             * @return
             */
            private ICommand getDeleteCommand(Diagram diagram) {
                return ElementTypeRegistry.getInstance()
                        .getElementType(diagram)
                        .getEditCommand(new DestroyElementRequest(diagram,
                                false));
            }

        };
    }

    @Override
    public boolean select(Object toTest) {
        EObject input = resollveInput(toTest);
        return input instanceof Model;
    }

    @Override
    protected boolean canMove() {
        return false;
    }

    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        boolean shouldRefresh = super.shouldRefresh(notifications);

        if (!shouldRefresh) {
            // Also listen out for diagram add, remove and change of name
            for (Notification notification : notifications) {
                Object notifier = notification.getNotifier();
                int event = notification.getEventType();
                Object feature = notification.getFeature();

                if ((event == Notification.SET && notifier instanceof Diagram && feature == NotationPackage.eINSTANCE
                        .getDiagram_Name())
                        || (notifier instanceof Resource && ((event == Notification.ADD && notification
                                .getNewValue() instanceof Diagram) || (event == Notification.REMOVE && notification
                                .getOldValue() instanceof Diagram)))) {
                    shouldRefresh = true;
                    break;
                }
            }
        }

        return shouldRefresh;
    }

    @Override
    protected IContentProvider getContentProvider() {
        return new IStructuredContentProvider() {

            @Override
            public Object[] getElements(Object inputElement) {
                Object[] elements = null;
                if (inputElement instanceof Model) {
                    WorkingCopy wc =
                            WorkingCopyUtil
                                    .getWorkingCopyFor((EObject) inputElement);
                    if (wc instanceof AbstractGMFWorkingCopy) {
                        elements =
                                getUserDefinedDiagrams((AbstractGMFWorkingCopy) wc);
                    }

                }
                return elements != null ? elements : new Object[0];
            }

            @Override
            public void dispose() {
                // Nothing to do
            }

            @Override
            public void inputChanged(Viewer viewer, Object oldInput,
                    Object newInput) {
                // Nothing to do
            }
        };
    }

    /**
     * Get the user-defined diagrams from the given model (working copy).
     * 
     * @param wc
     * @return
     */
    private Object[] getUserDefinedDiagrams(AbstractGMFWorkingCopy wc) {
        List<Diagram> userDefined = new ArrayList<Diagram>();

        for (Diagram diagram : wc.getDiagrams()) {
            if (BomUIUtil.isUserDiagram(diagram)) {
                userDefined.add(diagram);
            }
        }

        return userDefined.toArray();
    }

    /**
     * The name column for the Diagrams table.
     */
    private class DiagramNameColumn extends AbstractColumn {

        private TextCellEditor editor;

        public DiagramNameColumn(EditingDomain ed, ColumnViewer viewer) {
            super(ed, viewer, Messages.DiagramTabSection_nameColumn_label, 350);
            editor = new TextCellEditor((Composite) viewer.getControl());
        }

        @Override
        protected CellEditor getCellEditor(Object element) {
            return editor;
        }

        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            Command cmd = null;
            if (element instanceof Diagram && value instanceof String
                    && ((String) value).length() > 0) {
                cmd =
                        SetCommand.create(getEditingDomain(),
                                element,
                                NotationPackage.eINSTANCE.getDiagram_Name(),
                                value);
            }
            return cmd;
        }

        @Override
        protected String getText(Object element) {
            // TODO Auto-generated method stub
            return element instanceof Diagram ? ((Diagram) element).getName()
                    : ""; //$NON-NLS-1$
        }

        @Override
        protected Object getValueForEditor(Object element) {
            return getText(element);
        }
    }

    private class OpenAction extends ViewerAction {

        private IStructuredSelection selection;

        public OpenAction(StructuredViewer viewer) {
            super(viewer, Messages.DiagramTabSection_openDiagram_action, Activator.getDefault()
                    .getImageRegistry().getDescriptor(BOMImages.DIAGRAM));
            setAccelerator(SWT.CTRL | 'o');
            setToolTipText(Messages.DiagramTabSection_openDiagram_action_tooltip);
            setEnabled(false);
        }

        @Override
        public void run() {
            IWorkbenchPage page =
                    getSite().getWorkbenchWindow().getActivePage();
            if (selection != null && !selection.isEmpty()) {
                try {
                    for (Iterator<?> iter = selection.iterator(); iter
                            .hasNext();) {
                        BomUIUtil.openEditor(page, (Diagram) iter.next());
                    }
                } catch (PartInitException e) {
                    ErrorDialog
                            .openError(getShell(),
                                    Messages.DiagramTabSection_openDiagram_errorDialog_title,
                                    Messages.DiagramTabSection_openDiagram_errorDialog_message,
                                    new Status(
                                            IStatus.ERROR,
                                            com.tibco.xpd.bom.modeler.custom.Activator.PLUGIN_ID,
                                            e.getLocalizedMessage(), e));
                }
            }
        }

        @Override
        public void selectionChanged(IStructuredSelection selection) {
            this.selection = selection;
            setEnabled(selection != null && !selection.isEmpty());
        }

    }
}
