/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.participants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.DeleteAction;
import com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessRelevantDataTable;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessRelevantDataUtil;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.TableAddAction;
import com.tibco.xpd.resources.ui.components.actions.TableDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerAddAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteAction;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * ParticipantTable
 * 
 * 
 * @author bharge
 * @since 3.3 (26 Oct 2009)
 */
public class ParticipantTable extends AbstractProcessRelevantDataTable {
    private final EditingDomain editingDomain;

    private IContentProvider contentProvider;

    protected Map<String, String> typeNameMap = new HashMap<String, String>();

    /**
     * The owner may set this when the process is the actual input and it will
     * to show process participants even though we will only add new as package
     * level.
     */
    private Process subsiduaryProcessInput = null;

    /**
     * @param parent
     * @param toolkit
     * @param object
     * @param b
     */
    public ParticipantTable(Composite parent, XpdToolkit toolkit,
            EditingDomain editingDomain) {
        super(parent, toolkit, null, false);
        this.editingDomain = editingDomain;
    }

    /**
     * The owner may set this when the process is the actual input and it will
     * to show process participants even though we will only add new as package
     * level.
     * 
     * @param subsiduaryProcessInput
     *            the subsiduaryProcessInput to set
     */
    public void setSubsiduaryProcessInput(Process subsiduaryProcessInput) {
        this.subsiduaryProcessInput = subsiduaryProcessInput;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.resources.ui.components.BaseColumnViewerControl#
     * getViewerContentProvider()
     */
    @Override
    protected IContentProvider getViewerContentProvider() {
        if (contentProvider == null) {
            contentProvider = new IStructuredContentProvider() {

                @Override
                public Object[] getElements(Object inputElement) {
                    if (inputElement instanceof Package) {
                        List<Participant> partics =
                                new ArrayList<Participant>();
                        if (subsiduaryProcessInput != null) {
                            /*
                             * Show partics from subsiduary process.
                             * 
                             * This allows the owner of this table to show
                             * participants from the input package AND one
                             * process's participants as well.
                             */
                            partics.addAll(subsiduaryProcessInput
                                    .getParticipants());
                        }
                        partics.addAll(((Package) inputElement)
                                .getParticipants());

                        return partics.toArray();

                    } else if (inputElement instanceof Process) {
                        return ((Process) inputElement).getParticipants()
                                .toArray();
                    }
                    return new Object[0];
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
        return contentProvider;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#addColumns
     * (org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected void addColumns(ColumnViewer viewer) {
        new LabelColumn(editingDomain, viewer);
        if (CapabilityUtil.isDeveloperActivityEnabled()) {
            new NameColumn(editingDomain, viewer);
        }
        new TypeColumn(editingDomain, viewer);

        if (CapabilityUtil.isDeveloperActivityEnabled()) {
            setColumnProportions(new float[] { 0.3f, 0.25f, 0.2f, 0.3f });
        } else {
            setColumnProportions(new float[] { 0.3f, 0.2f, 0.3f });
        }
        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.resources.ui.components.BaseColumnViewerControl#
     * getMovableFeatures()
     */
    @Override
    protected Set<EStructuralFeature> getMovableFeatures() {
        Set<EStructuralFeature> features = super.getMovableFeatures();
        features.add(Xpdl2Package.eINSTANCE
                .getParticipantsContainer_Participants());
        return features;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createAddAction
     * (org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected ViewerAddAction createAddAction(ColumnViewer viewer) {
        return new TableAddAction(viewer, Messages.PropertiesSection_AddLabel,
                Messages.ParticipantsSection_AddParticipantsButton_tooltip) {

            @Override
            protected Object addRow(StructuredViewer viewer) {
                Object input = getInput();
                if (null != input) {
                    String firstCellVal = getNewRowFirstCellVal();
                    Participant participant = createFileTemplate(firstCellVal);
                    CompoundCommand cmd =
                            new CompoundCommand(
                                    Messages.ParticipantsSection_createParticipant_menu);
                    cmd.append(AddCommand.create(editingDomain,
                            getInput(),
                            Xpdl2Package.eINSTANCE.getParticipant(),
                            participant));
                    if (cmd.canExecute()) {
                        editingDomain.getCommandStack().execute(cmd);
                    }
                }
                return null;
            }

            protected String getNewRowFirstCellVal() {
                String propName =
                        Messages.ParticipantsSection_ParticipantName_value;
                Participant participant = createFileTemplate(propName);
                String uniqueDataFieldName =
                        getUniqueDataFieldName(propName, participant);
                if (uniqueDataFieldName != null
                        && !uniqueDataFieldName.equals(propName)) {
                    propName = uniqueDataFieldName;
                }
                return propName;
            }

            private Participant createFileTemplate(String participantName) {
                Xpdl2Factory fact = Xpdl2Factory.eINSTANCE;
                Participant input = fact.createParticipant();
                input.setName(NameUtil.getInternalName(participantName, false));
                Xpdl2ModelUtil.setOtherAttribute(input,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName(),
                        participantName);
                ParticipantTypeElem participantTypeElem =
                        fact.createParticipantTypeElem();

                /*
                 * Sid ACE-484 Default to Org Model Query instead of Role type
                 * (as the latter isn't supported in ACE).
                 */
                participantTypeElem
                        .setType(ParticipantType.RESOURCE_SET_LITERAL);

                input.setParticipantType(participantTypeElem);
                return input;
            }

            private String getUniqueDataFieldName(String baseName,
                    Participant participant) {
                String finalName = baseName;
                int idx = 1;
                while (Xpdl2ModelUtil
                        .getDuplicateDisplayParticipant(getInput(),
                                participant,
                                finalName) != null
                        || Xpdl2ModelUtil
                                .getDuplicateDisplayParticipant(getInput(),
                                        participant,
                                        NameUtil.getInternalName(finalName,
                                                false)) != null) {
                    idx++;
                    finalName = baseName + idx;
                }
                return finalName;
            }

        };
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.resources.ui.components.BaseColumnViewerControl#
     * createDeleteAction(org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected ViewerDeleteAction createDeleteAction(ColumnViewer viewer) {
        return new TableDeleteAction(viewer,
                Messages.PropertiesSection_DeleteLabel,
                Messages.ParticipantsSection_DeleteParticipantsButton_tooltip) {

            @Override
            protected void deleteRows(IStructuredSelection selection) {
                if (null != selection && !selection.isEmpty()) {
                    List<Participant> participantList =
                            new ArrayList<Participant>();
                    for (Iterator<?> iter = selection.iterator(); iter
                            .hasNext();) {
                        Object next = iter.next();
                        if (next instanceof Participant) {
                            participantList.add((Participant) next);
                        }
                    }
                    if (!participantList.isEmpty()) {
                        DeleteAction.deleteXpdlObject(Display.getCurrent()
                                .getActiveShell(), selection);
                    }
                }
            }

        };
    }

    /**
     * Get the input of this table.
     * 
     * @return
     */
    protected EObject getInput() {
        return (EObject) (getViewer() != null ? getViewer().getInput() : null);
    }

    protected Map<String, String> getTypes() {
        // Add basic types to the combo
        /*
         * Sid ACE-484 Only Org Model Query and System are valid in ACE.
         */
        String typeName;
        String typeLit;
        typeName = Messages.ParticipantsSection_OrganizationModelQuery_Label;
        typeLit = ParticipantType.RESOURCE_SET_LITERAL.getLiteral();
        typeNameMap.put(typeLit, typeName);
        typeName = Messages.ParticipantsSection_System_Label;
        typeLit = ParticipantType.SYSTEM_LITERAL.getLiteral();
        typeNameMap.put(typeLit, typeName);
        typeName = Messages.ParticipantsSection_ExternalReference_Label;
        typeLit = ProcessRelevantDataUtil.EXTERNAL_REFERENCE_TYPE;
        typeNameMap.put(typeLit, typeName);

        return typeNameMap;
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

    protected String getTypeName(String id) {
        String typeName = null;
        if (typeNameMap != null) {
            typeName = typeNameMap.get(id);
        }
        return typeName;
    }

    protected class TypeColumn extends AbstractColumn {
        private ComboBoxViewerCellEditor editor;

        /**
         * @param editingDomain
         * @param viewer
         * @param style
         * @param heading
         * @param width
         */
        public TypeColumn(EditingDomain editingDomain, ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.NONE,
                    Messages.DataFieldsSection_typeColumn_label, 120);
            Map<String, String> typeNameMap = getTypes();
            Object[] valuesArr = typeNameMap.values().toArray();
            if (valuesArr != null) {
                String[] strValuesArr = new String[valuesArr.length];
                for (int i = 0; i < valuesArr.length; i++) {
                    strValuesArr[i] = (String) valuesArr[i];
                }
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
            if (element instanceof Participant) {
                Participant participant = (Participant) element;
                String currentType = null;
                if (participant.getExternalReference() != null) {
                    currentType =
                            ProcessRelevantDataUtil.EXTERNAL_REFERENCE_TYPE;
                } else if (participant.getParticipantType() != null) {
                    ParticipantTypeElem participantType =
                            participant.getParticipantType();
                    currentType = participantType.getType().getLiteral();
                }

                int typeIndex = getTypeIndex((String) value);

                if (currentType != null) {
                    cmd =
                            new CompoundCommand(
                                    Messages.ParticipantsSection_createParticipantType_menu);
                    String newType = getTypeValue(typeIndex);
                    if (newType != null && !newType.equals(currentType)) {
                        EObject newDataType =
                                ProcessRelevantDataUtil
                                        .createNewParticipantType(newType);
                        ExternalReference externalReference = null;
                        ParticipantTypeElem participantTypeElem = null;
                        if (newDataType instanceof ExternalReference) {
                            externalReference = (ExternalReference) newDataType;
                        } else if (newDataType instanceof ParticipantTypeElem) {
                            participantTypeElem =
                                    (ParticipantTypeElem) newDataType;
                        }
                        cmd.append(SetCommand.create(editingDomain,
                                participant,
                                Xpdl2Package.eINSTANCE
                                        .getParticipant_ExternalReference(),
                                externalReference));
                        cmd.append(SetCommand.create(editingDomain,
                                participant,
                                Xpdl2Package.eINSTANCE
                                        .getParticipant_ParticipantType(),
                                participantTypeElem));
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
            if (element instanceof Participant) {
                Participant participant = (Participant) element;
                if (participant.getExternalReference() != null) {
                    text =
                            getTypeName(ProcessRelevantDataUtil.EXTERNAL_REFERENCE_TYPE);

                } else if (participant.getParticipantType() != null) {
                    ParticipantTypeElem participantType =
                            participant.getParticipantType();
                    text = getTypeName(participantType.getType().getLiteral());
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

    }

}
