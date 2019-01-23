package com.tibco.xpd.bpm.om;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.util.OMUtil;
import com.tibco.xpd.bpm.om.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.participants.ParticipantTable;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantsContainer;
import com.tibco.xpd.xpdl2.edit.ui.ComplexDataUIUtil;

public class BPMParticipantTable extends ParticipantTable {

    private final EditingDomain editingDomain;

    public BPMParticipantTable(Composite parent, XpdToolkit toolkit,
            EditingDomain editingDomain) {
        super(parent, toolkit, editingDomain);
        this.editingDomain = editingDomain;
        createContents(parent, toolkit, null);
    }

    @Override
    protected void addColumns(ColumnViewer viewer) {
        new LabelColumn(editingDomain, viewer);
        if (CapabilityUtil.isDeveloperActivityEnabled()) {
            new NameColumn(editingDomain, viewer);
        }
        new TypeColumn(editingDomain, viewer);
        new ExternalRefColumn(editingDomain, viewer);

        if (CapabilityUtil.isDeveloperActivityEnabled()) {
            setColumnProportions(new float[] { 0.3f, 0.25f, 0.2f, 0.3f });
        } else {
            setColumnProportions(new float[] { 0.3f, 0.2f, 0.3f });
        }
        return;
    }

    @Override
    protected EObject getInput() {
        return (EObject) (getViewer() != null ? getViewer().getInput() : null);
    }

    private class ExternalRefColumn extends AbstractColumn {
        private final ExternalRefPickerCellEditor editor;

        /**
         * @param editingDomain
         * @param viewer
         * @param style
         * @param heading
         * @param width
         */
        public ExternalRefColumn(EditingDomain editingDomain,
                ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.NONE,
                    Messages.DataFieldsSection_externalReferenceColumn_label,
                    150);
            editor =
                    new ExternalRefPickerCellEditor((Composite) viewer
                            .getControl());
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
            if (element instanceof Participant) {
                Participant participant = (Participant) element;
                if (participant.getExternalReference() != null) {
                    return editor;
                }
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
            if (element instanceof Participant) {
                Participant participant = (Participant) element;
                ExternalReference externalReference =
                        participant.getExternalReference();
                if (value instanceof EObject && externalReference != null) {
                    cmd =
                            new CompoundCommand(
                                    Messages.ParticipantsSection_SetExternalReference_menu);

                    cmd.append(BPMProcessOrgModelUtil
                            .getSetOrgModelParticipantRefCommand(editingDomain,
                                    (ParticipantsContainer) getInput(),
                                    participant,
                                    (EObject) value));
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
            if (element instanceof Participant) {
                Participant participant = (Participant) element;
                if (participant.getExternalReference() != null) {
                    ExternalReference externalReference =
                            participant.getExternalReference();
                    if (externalReference.getXref() == null) {
                        text = "";//$NON-NLS-1$
                    } else {
                        EObject refObject = null;
                        try {
                            refObject =
                                    OMUtil.getEObjectByID(externalReference
                                            .getXref());
                        } catch (Exception e) {

                        }
                        if (refObject != null
                                && externalReference.getXref() != null) {
                            IProject referencingProject =
                                    WorkingCopyUtil.getProjectFor(participant);
                            IFile referencedFile =
                                    WorkingCopyUtil.getFile(refObject);
                            IProject referencedProject = null;
                            if (referencedFile != null) {
                                referencedProject = referencedFile.getProject();
                            }
                            boolean isProjectReferenced = false;
                            try {
                                if (referencingProject != null
                                        && referencedProject != null
                                        && ProjectUtil
                                                .isProjectReferenced(referencingProject,
                                                        referencedProject)) {
                                    isProjectReferenced = true;
                                }
                            } catch (CoreException e) {
                                // Do nothing
                            }
                            if (isProjectReferenced) {
                                try {
                                    text =
                                            ((NamedElement) refObject)
                                                    .getDisplayName();
                                } catch (Exception e) {

                                }
                            } else {

                                text = ""; //$NON-NLS-1$
                            }

                        } else {
                            text = ""; //$NON-NLS-1$
                        }
                    }
                }
                if (text == null) {
                    text = ""; //$NON-NLS-1$
                }
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

        private class ExternalRefPickerCellEditor extends DialogCellEditor {

            public ExternalRefPickerCellEditor(Composite parent) {
                super(parent);
            }

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.jface.viewers.DialogCellEditor#openDialogBox(org.
             * eclipse.swt.widgets.Control)
             */
            @Override
            protected Object openDialogBox(Control cellEditorWindow) {
                Object result = null;
                result =
                        ComplexDataUIUtil
                                .getOMElementFromPicker(cellEditorWindow
                                        .getShell(), WorkingCopyUtil
                                        .getProjectFor(getInput()), null);
                return result;
            }

        }
    }
}
