/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.worklistfacade.resource.editor;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.worklistfacade.model.WorkItemAttribute;
import com.tibco.xpd.worklistfacade.resource.util.Messages;
import com.tibco.xpd.worklistfacade.resource.workingcopy.WorkListFacadeWorkingCopy;

/**
 * Section to be used in {@link WorkListFacadeEditor} to edit Physical Work Item
 * Attribute's Display Label. The Section contains a Table for editing
 * {@link WorkItemAttribute}.Supports editing of Display Label for
 * {@link WorkItemAttribute} . WorkListFacadeEditorSection extends
 * {@link AbstractTransactionalSection}
 * 
 * @author aprasad
 * 
 */
public class WorkListFacadeEditorSection extends AbstractTransactionalSection {

    /**
     * Table to list Physical Work Item Attribute data combining Physical Work
     * Item Attribute details from {@link Property} and Display Label details
     * from {@link WorkItemAttribute}.
     */
    private WorkListFacadeTable workListFacadeTable;

    /**
     * Reference to parent {@link WorkListFacadeEditor}
     */
    private EditorPart editor;

    /**
     * Editor's header section.
     */
    private Section workItemAttributeEditSec;

    public WorkListFacadeEditorSection(EditorPart editor) {
        this.editor = editor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     */
    @Override
    protected void doRefresh() {
        setTableInput();
    }

    /**
     * Sets Input data to the table, if the table is not null.
     */
    private void setTableInput() {
        EObject input = getInput();

        if (workListFacadeTable != null) {
            workListFacadeTable.setInput(input);
        }

    }

    @Override
    protected IWorkbenchSite getSite() {
        // As this section is not embedded into of a property sheet, getSite()
        // will return null hence needs to be overriden
        if (editor != null)
            return editor.getSite();
        return super.getSite();
    }

    @Override
    protected Control doCreateControls(Composite parent,
            XpdFormToolkit toolkit) {

        workItemAttributeEditSec = toolkit.createSection(parent, Section.TITLE_BAR);

        /*
         * Sid XPD-8387 you should NEVER set layout data on controls that you
         * didn't set the layout of the parent for! In this case it blew the
         * whole workbench away. It's up to the owner of the parent to set
         * layout data on the children in that parent.
         */
        // workItemAttributeEditSec.setLayoutData(
        // new GridData(GridData.FILL, GridData.FILL, true, true, 2, 1));

        workItemAttributeEditSec.setBackground(
                workItemAttributeEditSec.getParent().getBackground());

        // create Seperator
        toolkit.createCompositeSeparator(workItemAttributeEditSec);
        workItemAttributeEditSec.setText(
                Messages.WorkListFacadeEditorSection_WorkItemAttributes_Section_Header);

        // Create internal container for the Section
        Composite secContainer =
                toolkit.createComposite(workItemAttributeEditSec, SWT.WRAP);
        secContainer.setBackground(secContainer.getParent().getBackground());

        // set gridlayout for the section containeter
        FillLayout fillLayout = new FillLayout();
        secContainer.setLayout(fillLayout);
        workListFacadeTable = new WorkListFacadeTable(secContainer, toolkit,
                getWorkingCopyEditingDomain());
        workItemAttributeEditSec.clientVerticalSpacing = 10;
        workItemAttributeEditSec.setClient(secContainer);
        workItemAttributeEditSec.setExpanded(true);
        // Set Input data to the table
        setTableInput();
        return parent;
    }

    /**
     * Gets the {@link EditingDomain} for the {@link WorkListFacadeWorkingCopy}
     * , associated with this Editor Section, returns null if not
     * {@link WorkListFacadeWorkingCopy} is associated, which can be the case
     * when there is no Work List Facade file in the Workspace.
     * 
     * @return Editing Domain of the WorkingCopy.
     */
    private EditingDomain getWorkingCopyEditingDomain() {

        WorkListFacadeWorkingCopy workingCopy =
                ((WorkListFacadeEditorInput) editor.getEditorInput())
                        .getWorkingCopy();

        return (workingCopy != null) ? workingCopy.getEditingDomain() : null;
    }

    /**
     * Sets focus to the WorkListFacade Table control.
     */
    public void setFocus() {
        if (workListFacadeTable != null) {
            workListFacadeTable.setFocus();
        }
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return null.
     */
    @Override
    protected Command doGetCommand(Object obj) {
        return null;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#disableControlsForReadOnlyInput(org.eclipse.swt.widgets.Control)
     *
     * @param control
     */
    @Override
    protected void disableControlsForReadOnlyInput(Control control) {
        // We need to suppress recursive control disablement as it is not reversible, so controls will not be re-enabled
        // when the working copy will change it's state to not-readOnly.
        // super.disableControlsForReadOnlyInput(control);
    }

    /**
     * Sets the readOnly status for this control.
     * 
     * @param isReadOnly
     *            if the control should be in readOnly state.
     */
    public void setReadOnly(boolean isReadOnly) {
        if (workListFacadeTable != null) {
            workListFacadeTable.setReadOnly(isReadOnly);
            String wflEditorHeader = isReadOnly ? String
                    .format("%1$s [%2$s]", //$NON-NLS-1$
                            Messages.WorkListFacadeEditorSection_WorkItemAttributes_Section_Header,
                            Messages.WorkListFacadeEditorSection_readOnly)
                    : Messages.WorkListFacadeEditorSection_WorkItemAttributes_Section_Header;
            workItemAttributeEditSec.setText(wflEditorHeader);
        }
    }
}
