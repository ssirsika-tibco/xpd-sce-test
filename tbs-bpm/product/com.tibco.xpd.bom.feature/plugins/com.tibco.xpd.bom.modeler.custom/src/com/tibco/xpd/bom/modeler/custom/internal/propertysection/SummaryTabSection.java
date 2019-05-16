package com.tibco.xpd.bom.modeler.custom.internal.propertysection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.EMFOperationCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.SummaryInfoUtils;
import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.columns.LabelColumn;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.columns.NameColumn;
import com.tibco.xpd.resources.ui.components.actions.TableAddAction;
import com.tibco.xpd.resources.ui.components.actions.TableDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.TableMoveDownAction;
import com.tibco.xpd.resources.ui.components.actions.TableMoveUpAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerMoveDownAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerMoveUpAction;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.util.CapabilityUtil;

/**
 * Class to supply the Summary section in the properties tab, allows the user to
 * define what is deemed to be a summary attribute
 * 
 */
public class SummaryTabSection extends AbstractTableSection {

    /**
     * Construct the new section with an appropriate title
     */
    public SummaryTabSection() {
        super(Messages.SummaryTabSection_section_title, null);
    }

    /**
     * @see com.tibco.xpd.bom.modeler.custom.internal.propertysection.AbstractTableSection#addColumns(org.eclipse.jface.viewers.ColumnViewer)
     * 
     * @param viewer
     */
    @Override
    protected void addColumns(ColumnViewer viewer) {
        // Add columns for both the label and the name, make the columns a bit
        // wider than normal as we only have 2 of them
        new LabelColumn(getEditingDomain(), viewer, false, 250);
        if (CapabilityUtil.isDeveloperActivityEnabled()) {
            new NameColumn(getEditingDomain(), viewer, false, 250);
        }
    }

    /**
     * @see com.tibco.xpd.bom.modeler.custom.internal.propertysection.AbstractTableSection#getContentProvider()
     * 
     * @return
     */
    @Override
    protected IContentProvider getContentProvider() {
        // Create a content provider that returns all of the attributes that
        // should be displayed in the summary section and in the correct order
        return new IStructuredContentProvider() {
            @Override
            public Object[] getElements(Object inputElement) {
                ArrayList<Object> items = new ArrayList<Object>();
                if (inputElement instanceof Class) {
                    Class caseClass = (Class) inputElement;
                    // Get the summary information that is already selected
                    // This is an ordered array, so convert the names to the
                    // appropriate properties
                    List<String> summaryArray =
                            SummaryInfoUtils.getSummaryArray(caseClass);

                    // Now match each attribute already in the summary with
                    // the ones on the actual case class
                    for (String attribName : summaryArray) {
                        for (Property prop : caseClass.getAllAttributes()) {
                            if (attribName.equals(prop.getName())) {
                                items.add(prop);
                                // Now that we have found the attribute that
                                // matches the one we were searching for, stop
                                // the loop for this attribute name
                                break;
                            }
                        }
                    }
                }
                return items.toArray();
            }

            @Override
            public void dispose() {
            }

            @Override
            public void inputChanged(Viewer viewer, Object oldInput,
                    Object newInput) {
            }
        };
    }

    /**
     * @see com.tibco.xpd.bom.modeler.custom.internal.propertysection.AbstractTableSection#getMoveUpAction(org.eclipse.jface.viewers.ColumnViewer)
     * 
     * @param viewer
     * @return
     */
    @Override
    protected ViewerMoveUpAction getMoveUpAction(ColumnViewer viewer) {
        // Create the action that is used to move an entry up the summary
        // information
        return new TableMoveUpAction(viewer,
                Messages.OperationArgumentsTable_moveUp_action,
                Messages.OperationArgumentsTable_moveUp_action_tooltip) {

            @Override
            protected void moveUp(Object paramObject) {
                // Get the details of the property being moved up
                if (paramObject instanceof Property) {
                    final Property prop = (Property) paramObject;
                    EObject classInput = getInput();
                    if (classInput instanceof Class) {
                        final Class caseClass = (Class) classInput;
                        // Create the command that will move the attribute up
                        AbstractTransactionalCommand icmd =
                                new AbstractTransactionalCommand(
                                        (TransactionalEditingDomain) getEditingDomain(),
                                        Messages.OperationArgumentsTable_moveUp_action,
                                        null) {
                                    @Override
                                    protected CommandResult doExecuteWithResult(
                                            IProgressMonitor monitor,
                                            IAdaptable info)
                                            throws ExecutionException {
                                        SummaryInfoUtils.moveUp(caseClass,
                                                prop.getName());
                                        return CommandResult
                                                .newOKCommandResult();
                                    }
                                };

                        EMFOperationCommand cmd =
                                new EMFOperationCommand(
                                        (TransactionalEditingDomain) getEditingDomain(),
                                        icmd);
                        getEditingDomain().getCommandStack().execute(cmd);
                    }
                }
            }
        };
    }

    /**
     * @see com.tibco.xpd.bom.modeler.custom.internal.propertysection.AbstractTableSection#getMoveDownAction(org.eclipse.jface.viewers.ColumnViewer)
     * 
     * @param viewer
     * @return
     */
    @Override
    protected ViewerMoveDownAction getMoveDownAction(ColumnViewer viewer) {
        // Create the action that is used to move an entry down the summary
        // information
        return new TableMoveDownAction(viewer,
                Messages.OperationArgumentsTable_moveDown_action,
                Messages.OperationArgumentsTable_moveDown_action_tooltip) {

            @Override
            protected void moveDown(Object paramObject) {
                // Get the property that is to be moved down
                if (paramObject instanceof Property) {
                    final Property prop = (Property) paramObject;
                    EObject classInput = getInput();
                    if (classInput instanceof Class) {
                        final Class caseClass = (Class) classInput;
                        // Create the command that will move the attribute down
                        AbstractTransactionalCommand icmd =
                                new AbstractTransactionalCommand(
                                        (TransactionalEditingDomain) getEditingDomain(),
                                        Messages.OperationArgumentsTable_moveDown_action,
                                        null) {
                                    @Override
                                    protected CommandResult doExecuteWithResult(
                                            IProgressMonitor monitor,
                                            IAdaptable info)
                                            throws ExecutionException {
                                        SummaryInfoUtils.moveDown(caseClass,
                                                prop.getName());
                                        return CommandResult
                                                .newOKCommandResult();
                                    }
                                };

                        EMFOperationCommand cmd =
                                new EMFOperationCommand(
                                        (TransactionalEditingDomain) getEditingDomain(),
                                        icmd);
                        getEditingDomain().getCommandStack().execute(cmd);
                    }
                }
            }
        };
    }

    /**
     * @see com.tibco.xpd.bom.modeler.custom.internal.propertysection.AbstractTableSection#getAddAction(org.eclipse.jface.viewers.ColumnViewer)
     * 
     * @param viewer
     * @return
     */
    @Override
    protected TableAddAction getAddAction(ColumnViewer viewer) {
        // Create the action that is used to add an entry to the summary
        // information
        return new TableAddAction(viewer,
                Messages.AttributesTabSection_add_action,
                Messages.AttributesTabSection_add_action_tooltip) {

            @Override
            protected Object addRow(StructuredViewer viewer) {
                EObject input = getInput();
                if (input != null && input instanceof Class) {
                    final Class caseClass = (Class) input;
                    // Create the dialog that is used to prompt the user for the
                    // attribute to add to the summary
                    SummaryAttributeSelectionDialog attributeSelectionDialog =
                            new SummaryAttributeSelectionDialog(viewer
                                    .getControl().getShell(), caseClass);
                    attributeSelectionDialog.open();
                    // Get what the user selected in the dialog
                    final Object selectedAttrib =
                            attributeSelectionDialog.getFirstResult();
                    if (selectedAttrib instanceof Property) {
                        // Create the command that will add the attribute
                        AbstractTransactionalCommand icmd =
                                new AbstractTransactionalCommand(
                                        (TransactionalEditingDomain) getEditingDomain(),
                                        Messages.AttributesTabSection_add_action,
                                        null) {
                                    @Override
                                    protected CommandResult doExecuteWithResult(
                                            IProgressMonitor monitor,
                                            IAdaptable info)
                                            throws ExecutionException {
                                        SummaryInfoUtils
                                                .addSummaryValue(caseClass,
                                                        ((Property) selectedAttrib)
                                                                .getName());
                                        return CommandResult
                                                .newOKCommandResult();
                                    }
                                };

                        EMFOperationCommand cmd =
                                new EMFOperationCommand(
                                        (TransactionalEditingDomain) getEditingDomain(),
                                        icmd);
                        getEditingDomain().getCommandStack().execute(cmd);

                        if (cmd.getResult() != null
                                && !cmd.getResult().isEmpty()) {
                            return cmd.getResult().iterator().next();
                        }
                    }
                }
                return null;
            }
        };
    }

    /**
     * @see com.tibco.xpd.bom.modeler.custom.internal.propertysection.AbstractTableSection#getDeleteAction(org.eclipse.jface.viewers.ColumnViewer)
     * 
     * @param viewer
     * @return
     */
    @Override
    protected TableDeleteAction getDeleteAction(ColumnViewer viewer) {
        return new TableDeleteAction(viewer,
                Messages.AttributesTabSection_remove_action,
                Messages.AttributesTabSection_remove_action_tooltip) {
            @Override
            protected void deleteRows(IStructuredSelection selection) {
                EObject input = getInput();
                if (input instanceof Class && selection != null
                        && !selection.isEmpty()) {
                    final Class caseClass = (Class) input;
                    Iterator<?> iter = selection.iterator();
                    CompositeCommand cc =
                            new CompositeCommand(
                                    selection.size() > 1 ? Messages.AttributesTabSection_removeMultipleAttributes_command_label
                                            : Messages.AttributesTabSection_removeSingleAttribute_command_label);
                    while (iter.hasNext()) {
                        final Object selectedAttrib = iter.next();
                        if (selectedAttrib instanceof Property) {
                            // Create the command that will delete the attribute
                            AbstractTransactionalCommand icmd =
                                    new AbstractTransactionalCommand(
                                            (TransactionalEditingDomain) getEditingDomain(),
                                            Messages.AttributesTabSection_add_action,
                                            null) {
                                        @Override
                                        protected CommandResult doExecuteWithResult(
                                                IProgressMonitor monitor,
                                                IAdaptable info)
                                                throws ExecutionException {
                                            SummaryInfoUtils
                                                    .removeSummaryValue(caseClass,
                                                            ((Property) selectedAttrib)
                                                                    .getName());
                                            return CommandResult
                                                    .newOKCommandResult();
                                        }
                                    };
                            cc.add(icmd);
                        }
                    }
                    EMFOperationCommand opCmd =
                            new EMFOperationCommand(
                                    (TransactionalEditingDomain) getEditingDomain(),
                                    cc);

                    if (opCmd.canExecute()) {
                        getEditingDomain().getCommandStack().execute(opCmd);
                    }
                }
            }

            @Override
            protected boolean canDelete(IStructuredSelection selection) {
                // Check the base class first to see if this one can be deleted
                boolean canDelete = super.canDelete(selection);

                // Only check specifics if we thing we are in a state to delete
                if (canDelete != false) {
                    // Can not delete case state and case identifiers
                    Iterator<?> iter = selection.iterator();
                    while (iter.hasNext()) {
                        Object nextAttr = iter.next();
                        if (nextAttr instanceof Property) {
                            Property prop = (Property) nextAttr;
                            if (GlobalDataProfileManager.getInstance()
                                    .isAutoCaseIdentifier(prop)
                                    || GlobalDataProfileManager.getInstance()
                                            .isCID(prop)
                                    || GlobalDataProfileManager.getInstance()
                                            .isCompositeCaseIdentifier(prop)
                                    || GlobalDataProfileManager.getInstance()
                                            .isCaseState(prop)) {
                                canDelete = false;
                                break;
                            }
                        }
                    }
                }

                return canDelete;
            }
        };
    }

    /**
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {
        // This method will return true if the tab is to be displayed,
        // False if it should not be displayed
        boolean select = false;
        EObject obj = resollveInput(toTest);

        if (obj instanceof Class) {
            // Check to see if this is a case class, as only case classes can
            // have summary details applied to them
            if (GlobalDataProfileManager.getInstance().isCase((Class) obj)) {
                select = true;
            }
        }

        return select;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#shouldRefresh(java.util.List)
     * 
     * @param notifications
     * @return
     */
    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        if (notifications != null) {
            return true;
        }
        return super.shouldRefresh(notifications);
    }

    /**
     * @see com.tibco.xpd.bom.modeler.custom.internal.propertysection.AbstractTableSection#canMove()
     */
    @Override
    protected boolean canMove() {
        // The elements are not supposed to be moved by the user. They will
        // automatically be sorted according to the attributes order in the
        // class. See: {@link SummaryTabSection#doCreateControls(Composite,
        // XpdFormToolkit)}
        return false;
    }

    /**
     * @see com.tibco.xpd.bom.modeler.custom.internal.propertysection.AbstractTableSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    @Override
    protected Control doCreateControls(Composite parent,
            XpdFormToolkit toolkit) {
        Control aControl = super.doCreateControls(parent, toolkit);
        // Sort attributes according to their order in its class.
        getTable().getViewer().setComparator(new ViewerComparator() {
            @Override
            public void sort(Viewer viewer, Object[] elements) {
                sortByInClassOrder(elements);
            }

        });
        return aControl;

    }

    /**
     * Sort in place the array of properties according to ther order in the
     * owning class.
     * 
     * @param elements
     *            the array to sort.
     */
    private void sortByInClassOrder(Object[] elements) {
        if (elements.length > 0 && elements[0] instanceof Property) {
            List<?> current = new ArrayList<>(Arrays.asList(elements));
            Class ownerClass = (Class) ((Property) elements[0]).getOwner();
            int i = 0;
            for (Property prop : ownerClass.getAllAttributes()) {
                if (current.contains(prop)) {
                    elements[i] = prop;
                    i++;
                }
            }
        }
    }

}
