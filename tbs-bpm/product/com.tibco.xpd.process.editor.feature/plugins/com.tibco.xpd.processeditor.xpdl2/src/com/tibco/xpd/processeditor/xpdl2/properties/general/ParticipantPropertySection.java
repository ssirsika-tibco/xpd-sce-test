/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.general;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker.ISectionContentCreator;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;
import com.tibco.xpd.xpdl2.edit.ui.internal.Messages;
import com.tibco.xpd.xpdl2.edit.ui.properties.general.ObjectReferencesFolder;
import com.tibco.xpd.xpdl2.edit.ui.properties.general.ObjectReferencesItem;
import com.tibco.xpd.xpdl2.edit.ui.properties.general.ObjectReferencesSection;
import com.tibco.xpd.xpdl2.resolvers.Xpdl2ParticipantReferenceResolver;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Participant properties - 2 page books for local and external refs
 * 
 * @author glewis
 * 
 */
public abstract class ParticipantPropertySection extends
        AbstractFilteredTransactionalSection implements ISectionContentCreator {

    private Composite root;

    private ObjectReferencesSection referencesSection = null;

    private ScrolledComposite scrolledContainer;

    private ExpandableSectionStacker expandableHeaderController;

    private Control expanablesContainer;

    public static final String SHARED_RESOURCE_SECTION = "sharedResource"; //$NON-NLS-1$

    public static final String REFERENCES_HEADER_ID = "references"; //$NON-NLS-1$

    /**
     * Participant properties
     */
    public ParticipantPropertySection() {
        super(Xpdl2Package.eINSTANCE.getParticipant());

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.BaseXpdSection#doGetCommand(java.lang.Object)
     */
    @Override
    public Command doGetCommand(Object obj) {
        Participant participant = (Participant) getInput();
        EStructuralFeature feat =
                (EStructuralFeature) ((Widget) obj)
                        .getData(XpdFormToolkit.FEATURE_DATA);
        if (feat == Xpdl2Package.eINSTANCE.getParticipantTypeElem_Type()) {
            ParticipantTypeElem currentType = participant.getParticipantType();
            ParticipantType participantType =
                    (ParticipantType) ((EEnumLiteral) ((Widget) obj)
                            .getData("VALUE")).getInstance();//$NON-NLS-1$

            /*
             * If the type hasn't actually changed then there is nothing to do.
             */
            if (participantType.equals(currentType.getType())) {
                return null;
            }

            /*
             * Sid ACE-1197 Refactored set type command for re-use by sub-class
             */
            return getSetBaseParticipantTypeCommand(participant,
                    participantType);
        }

        return null;
    }

    /**
     * Create the command for setting participant to a Basic type (does not
     * cover external reference)
     * 
     * @param participant
     * @param participantType
     * 
     * @return The command for setting participant to a Basic type
     */
    protected Command getSetBaseParticipantTypeCommand(Participant participant,
            ParticipantType participantType) {
        ParticipantTypeElem typeElem =
                Xpdl2Factory.eINSTANCE.createParticipantTypeElem();
        typeElem.setType(participantType);

        CompoundCommand compCmd = new CompoundCommand();
        compCmd.setLabel(Messages.ParticipantPropertySection_SetType_menu);

        compCmd.append(SetCommand.create(getEditingDomain(),
                participant,
                Xpdl2Package.eINSTANCE.getParticipant_ParticipantType(),
                typeElem));
        ParticipantTypeElem existingParticipantTypeElem =
                participant.getParticipantType();

        if (!(ParticipantType.RESOURCE_SET_LITERAL
                .equals(existingParticipantTypeElem.getType()))
                && ParticipantType.RESOURCE_SET_LITERAL
                        .equals(participantType)) {
            Expression participantQuery =
                    Xpdl2Factory.eINSTANCE.createExpression();
            participantQuery.setScriptGrammar("RQL"); //$NON-NLS-1$

            Command setParticipantQueryCmd =
                    Xpdl2ModelUtil
                            .getSetOtherElementCommand(getEditingDomain(),
                                    typeElem,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ParticipantQuery(),
                                    participantQuery);

            compCmd.append(setParticipantQueryCmd);
        } else if (!ParticipantType.RESOURCE_SET_LITERAL
                .equals(participantType)) {
            Object objParticipantQ =
                    Xpdl2ModelUtil.getOtherElement(typeElem,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ParticipantQuery());
            if (objParticipantQ instanceof Expression) {
                compCmd.append(Xpdl2ModelUtil
                        .getSetOtherElementCommand(getEditingDomain(),
                                typeElem,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ParticipantQuery(),
                                null));
            }
        }
        return compCmd;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.BaseXpdSection#doCreateControls(org.eclipse
     * .swt.widgets.Composite, com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        scrolledContainer =
                toolkit.createScrolledComposite(parent, SWT.V_SCROLL
                        | SWT.H_SCROLL);

        scrolledContainer.setExpandHorizontal(true);
        scrolledContainer.setExpandVertical(true);

        root = toolkit.createComposite(scrolledContainer);
        GridLayout gl = new GridLayout(2, false);
        gl.marginLeft = 4;
        gl.marginWidth = 0;
        root.setLayout(gl);

        // Expandable section header preferences.
        String sectPrefId =
                getSectionContainerType()
                        + "." + "ExpandableParticipantSection6"; //$NON-NLS-1$ //$NON-NLS-2$
        expandableHeaderController = new ExpandableSectionStacker(sectPrefId);

        getTypeCtrlsExpandableHeaderController(expandableHeaderController);

        if (getSectionContainerType() != ContainerType.WIZARD) {
            getExpandableHeaderController(expandableHeaderController);
        }

        expanablesContainer =
                expandableHeaderController.createExpandableSections(root,
                        toolkit,
                        this);

        GridData gd =
                new GridData(GridData.FILL_BOTH | GridData.GRAB_VERTICAL
                        | GridData.GRAB_HORIZONTAL);
        expanablesContainer.setLayoutData(gd);

        // set contents of sections on to the scrolled composite
        scrolledContainer.setContent(root);

        Point prefSize = root.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        scrolledContainer.setMinSize(prefSize);

        setMinimumHeight(prefSize.y);

        return scrolledContainer;
    }

    protected abstract void getTypeCtrlsExpandableHeaderController(
            ExpandableSectionStacker expandableHeaderController);

    protected abstract void getExpandableHeaderController(
            ExpandableSectionStacker expandableHeaderController);

    /**
     * @param container
     * @param toolkit
     */
    protected Control createExtraControls(Composite container,
            XpdFormToolkit toolkit) {
        if (getSectionContainerType() == ContainerType.PROPERTYVIEW) {
            Composite referencesContainer = toolkit.createComposite(container);

            GridData layoutData =
                    new GridData(GridData.FILL_BOTH | GridData.GRAB_VERTICAL);
            layoutData.horizontalSpan = 2;
            referencesContainer.setLayoutData(layoutData);

            GridLayout gl = new GridLayout(2, false);
            gl.marginHeight = 0;
            gl.marginLeft = gl.marginWidth;
            gl.marginWidth = 0;

            referencesContainer.setLayout(gl);

            referencesSection = new ObjectReferencesSection();

            Composite comp =
                    referencesSection.createControls(referencesContainer,
                            toolkit);
            comp.setLayoutData(new GridData(GridData.FILL_BOTH));

            return referencesContainer;
        }

        return null;
    }

    protected void enableExpandSection(
            AbstractTransactionalSection insideSection, String sectionId,
            boolean enable) {
        /*
         * Sid XPD-1709: Do not expand/contract sections behind the stacker's
         * back - this causes layout problems.
         * 
         * We also should not re-expand sections that the user has specifically
         * collapsed it.
         */
        if (expandableHeaderController != null) {
            Section section =
                    expandableHeaderController.getExpandableSection(sectionId);

            if (section != null) {

                if (enable) {

                    if (!section.isEnabled()) {
                        insideSection.setInput(Collections
                                .singleton(getInput()));
                    } else {
                        insideSection.refresh();
                    }

                    section.setEnabled(true);

                    boolean storedExpansionState =
                            expandableHeaderController
                                    .getStoredExpansionState(sectionId);
                    if (expandableHeaderController.isExpanded(sectionId) != storedExpansionState) {
                        expandableHeaderController.setExpansionState(sectionId,
                                storedExpansionState,
                                false);
                    }

                } else {
                    section.setEnabled(false);

                    if (expandableHeaderController.isExpanded(sectionId) != false) {
                        expandableHeaderController.setExpansionState(sectionId,
                                false,
                                false);
                    }
                }
            }
        }
    }

    protected int getNumberOfColumns() {
        return 1;
    }

    protected void addBasicTypeExtras(Composite composite,
            XpdFormToolkit toolkit) {
    };

    public Composite getRoot() {
        return root;
    }

    @Override
    public void setInput(Collection<?> items) {

        super.setInput(items);

        loadParticipantReferences();

        // For new participant - Reset default name to something unique.
        // Ensure name is unique at the very start as we now check for
        // uniqueness we don't want user to be shown an error by default!
        Participant participant = (Participant) getInput();
        if (participant != null && participant.eContainer() == null) {
            String baseName = participant.getName();
            String finalName = baseName;

            EObject container = getInputContainer();
            if (container != null) {
                int idx = 1;
                while (Xpdl2ModelUtil.getDuplicateDisplayParticipant(container,
                        participant,
                        finalName) != null
                        || Xpdl2ModelUtil.getDuplicateParticipant(container,
                                participant,
                                NameUtil.getInternalName(finalName, false)) != null) {
                    idx++;
                    finalName = baseName + idx;
                }
                participant.setName(NameUtil.getInternalName(finalName, false));
                Xpdl2ModelUtil.setOtherAttribute(participant,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName(),
                        finalName);
            }
        }
    }

    private void loadParticipantReferences() {
        if (referencesSection != null) {
            ImageRegistry ir = Xpdl2UiPlugin.getDefault().getImageRegistry();

            // Map of process object to the process folder for it.
            HashMap<Process, ObjectReferencesFolder> processFolders =
                    new HashMap<Process, ObjectReferencesFolder>();
            // Map of process object to the activity folder for it.
            HashMap<Process, ObjectReferencesFolder> activityFolders =
                    new HashMap<Process, ObjectReferencesFolder>();

            Participant participant = (Participant) getInput();
            if (participant != null) {
                // Fetch all the participant references according to scope of
                // field.
                Set<EObject> referenceObjects =
                        Xpdl2ParticipantReferenceResolver
                                .getReferencingObjects(participant);

                // Now add all the items into list.
                // Creating a tree of process/activity folders.
                for (Iterator<?> iter = referenceObjects.iterator(); iter
                        .hasNext();) {
                    EObject obj = (EObject) iter.next();

                    if (obj instanceof Activity) {
                        Activity act = (Activity) obj;

                        // Add folder for process if necessary.
                        Process proc = act.getProcess();
                        if (proc != null) {
                            // Create the folder for this process if we haven't
                            // already.
                            ObjectReferencesFolder procFolder =
                                    processFolders.get(proc);
                            if (procFolder == null) {
                                procFolder = new ObjectReferencesFolder(proc);
                                processFolders.put(proc, procFolder);
                            }

                            // Create the activity folder for this process if
                            // not exists.
                            ObjectReferencesFolder actFolder =
                                    activityFolders.get(proc);
                            if (actFolder == null) {
                                actFolder =
                                        new ObjectReferencesFolder(
                                                Messages.BaseFieldOrParamPropertySection_Tasks_label,
                                                ir.get(Xpdl2UiPlugin.IMG_TASKDRAWER));

                                activityFolders.put(proc, actFolder);

                                procFolder.addChild(actFolder);
                            }

                            actFolder.addChild(ObjectReferencesItem.create(act,
                                    getSite()));
                        }
                    } else {
                        continue;
                    }
                }

            }

            // Sort the list by process and set the content of tree viewer.
            List<ObjectReferencesFolder> sortList =
                    new ArrayList<ObjectReferencesFolder>(processFolders.size());
            sortList.addAll(processFolders.values());

            Collections.sort(sortList,
                    new Comparator<ObjectReferencesFolder>() {

                        @Override
                        public int compare(ObjectReferencesFolder o1,
                                ObjectReferencesFolder o2) {
                            return o1.getName().compareTo(o2.getName());
                        }
                    });

            referencesSection.setContent(sortList);
        }
    }

    /**
     * Returns help context for sections to show individual help details
     * 
     * @return
     */
    public String getHelpContextID() {
        return "com.tibco.xpd.process.analyst.doc.Participant2"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.ui.properties.ExpandableSectionStacker.ISectionContentCreator#createExpandableSectionContent(java.lang.String,
     *      org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param sectionId
     * @param container
     * @param toolkit
     * @return
     */
    @Override
    public abstract Control createExpandableSectionContent(String sectionId,
            Composite container, XpdFormToolkit toolkit);

    /**
     * @see com.tibco.xpd.ui.properties.ExpandableSectionStacker.ISectionContentCreator#expandableSectionStateChanged(java.lang.String)
     * 
     * @param sectionId
     */
    @Override
    public void expandableSectionStateChanged(String sectionId) {
        Point sz = root.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        scrolledContainer.setMinSize(sz);
    }

    // MR 38533 - begin
    @Override
    public void setInput(IWorkbenchPart part, ISelection selection) {
        super.setInput(part, selection);
    }

}
