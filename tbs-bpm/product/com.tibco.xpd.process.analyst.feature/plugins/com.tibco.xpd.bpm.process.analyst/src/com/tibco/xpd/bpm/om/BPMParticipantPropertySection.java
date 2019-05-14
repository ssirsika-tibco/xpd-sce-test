package com.tibco.xpd.bpm.om;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;

import com.tibco.xpd.analyst.resources.xpdl2.propertytesters.XpdlFileContentPropertyTester;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessOrgModelUtil;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.newparticipant.NewBPMParticipantWizard;
import com.tibco.xpd.bpm.om.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.general.OrgModelQueryScriptSection;
import com.tibco.xpd.processeditor.xpdl2.properties.general.ParticipantPropertySection;
import com.tibco.xpd.processeditor.xpdl2.properties.general.SharedResourcesSection;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.properties.components.AbstractPickerControl;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.ParticipantsContainer;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.ui.ComplexDataUIUtil;
import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;
import com.tibco.xpd.xpdl2.edit.util.ControlUtils;
import com.tibco.xpd.xpdl2.edit.util.ProcessInternalViewUtil;

public class BPMParticipantPropertySection extends ParticipantPropertySection {

    private ScrolledPageBook book = null;

    private Button[] typeButtons;

    protected static final int LOCAL = 0;

    protected static final int EXTERNALREFERENCE = 1;

    protected static String[] TYPES = new String[] {
            Messages.BaseTypeSection_basicTypeCmb_label,
            Messages.BaseTypeSection_externalRefCmb_label };

    // Radio buttons for type selection
    protected Button[] btnTypes = null;

    // External reference controls
    private CLabel labelExternalRef = null;

    private ExternalRefCtrl externalRefCtrl;

    private OrgModelQueryScriptSection orgModelQueryScriptSection;

    private SharedResourcesSection sharedResourceSection;

    private static final String BLANK_INSTRUMENTATION = ""; //$NON-NLS-1$

    public final static String TYPECTRLS_SECTION = "participantTypes"; //$NON-NLS-1$

    public static final String QUERY_SCRIPT_SECTION = "queryScript"; //$NON-NLS-1$

    protected void createExternalReferencePage(Composite parent,
            XpdFormToolkit toolkit) {
        GridData gridData = null;

        parent.setLayout(new GridLayout(3, false));

        Label lblMsg =
                toolkit.createLabel(parent,
                        Messages.BaseTypeSection_setReferenceDomain_Participant_label);
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = 3;
        lblMsg.setLayoutData(gridData);
        ControlUtils.forceWidgetVisible(lblMsg, lblMsg.getText());

        labelExternalRef =
                toolkit.createCLabel(parent,
                        Messages.ParticipantPropertySection_definedExternal,
                        SWT.NONE);
        labelExternalRef.setLayoutData(new GridData());

        externalRefCtrl =
                new ExternalRefCtrl(parent, SWT.NONE, toolkit,
                        getEditingDomain());
        gridData = new GridData();
        gridData.horizontalSpan = 2;
        externalRefCtrl.setLayoutData(gridData);
        return;
    }

    protected Control createQueryScriptControl(Composite container,
            XpdFormToolkit toolkit) {
        if (getPropertySheetPage() != null) {
            orgModelQueryScriptSection =
                    new OrgModelQueryScriptSection(
                            Xpdl2Package.eINSTANCE.getParticipant());
            // orgModelQueryScriptSection.createControls(container, toolkit);

            orgModelQueryScriptSection.createControls(container,
                    getPropertySheetPage());
        }
        return container;
    }

    @Override
    protected void getExpandableHeaderController(
            ExpandableSectionStacker expandableHeaderController) {
        expandableHeaderController
                .addSection(SHARED_RESOURCE_SECTION,
                        Messages.ParticipantPropertySection_SharedResourceSection_label,
                        315,
                        true,
                        true);
        expandableHeaderController
                .addSection(QUERY_SCRIPT_SECTION,
                        Messages.ParticipantPropertySection_OrgModelQueryScriptSectionHeader_label,
                        SWT.DEFAULT,
                        true,
                        true);
        expandableHeaderController
                .addSection(REFERENCES_HEADER_ID,
                        Messages.ParticipantPropertySection_ReferenceSectionHeader_label,
                        60,
                        false,
                        true);
    }

    private static String getOrgModelElementDisplayName(
            ParticipantsContainer participantsContainer, Participant participant) {
        String displayName = null;

        ExternalReference externalReference =
                participant.getExternalReference();
        if (externalReference != null) {
            try {
                displayName =
                        ProcessOrgModelUtil
                                .getReferencedOrgModelEntitySimpleName(participantsContainer,
                                        participant);
            } catch (IllegalArgumentException e) {
            }
        }

        return displayName;
    }

    @Override
    public boolean select(Object toTest) {
        Object item = getBaseSelectObject(toTest);
        if (item instanceof Participant) {
            Participant participant = (Participant) item;
            if ((XpdlFileContentPropertyTester.isXpdlFileContent(participant))
                    || NewBPMParticipantWizard.NEW_BPM_PARTICIPANT_ID
                            .equals(participant.getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doRefresh() {

        Participant participant = (Participant) getInput();
        if (participant != null) {

            for (Button element : btnTypes) {
                element.setSelection(false);
            }

            externalRefCtrl.setValue(null);
            boolean isLocal = participant.getExternalReference() == null;

            if (isLocal) {
                btnTypes[LOCAL].setSelection(true);

                ParticipantType val =
                        participant.getParticipantType() == null ? null
                                : participant.getParticipantType().getType();
                for (Button element : typeButtons) {
                    if (element != null) {
                        EEnumLiteral buttVal =
                                (EEnumLiteral) element
                                        .getData(XpdFormToolkit.VALUE_DATA);
                        if ((val == null && buttVal == null)
                                || (val != null && val.equals(buttVal
                                        .getInstance()))) {
                            element.setSelection(true);
                        } else {
                            element.setSelection(false);
                        }
                    }
                }

                // if (expandableHeaderController != null) {
                if (ParticipantType.RESOURCE_SET_LITERAL.equals(val)) {
                    enableExpandSection(orgModelQueryScriptSection,
                            QUERY_SCRIPT_SECTION,
                            true);
                } else {
                    enableExpandSection(orgModelQueryScriptSection,
                            QUERY_SCRIPT_SECTION,
                            false);
                }
                if (ParticipantType.SYSTEM_LITERAL.equals(val)) {
                    enableExpandSection(sharedResourceSection,
                            SHARED_RESOURCE_SECTION,
                            true);
                } else {
                    enableExpandSection(sharedResourceSection,
                            SHARED_RESOURCE_SECTION,
                            false);
                }
                // }
                book.showPage(new Integer(LOCAL));
            } else {
                btnTypes[EXTERNALREFERENCE].setSelection(true);
                externalRefCtrl
                        .setValue((ParticipantsContainer) getInputContainer(),
                                participant);
                enableExpandSection(orgModelQueryScriptSection,
                        QUERY_SCRIPT_SECTION,
                        false);
                enableExpandSection(sharedResourceSection,
                        SHARED_RESOURCE_SECTION,
                        false);
                // Show the page
                book.showPage(new Integer(EXTERNALREFERENCE));
            }
        }
        return;
    }

    @Override
    public void setInput(IWorkbenchPart part, ISelection selection) {
        super.setInput(part, selection);

        if (orgModelQueryScriptSection != null) {
            ArrayList<Object> arrayList = null;
            if (selection instanceof IStructuredSelection) {
                IStructuredSelection structuredSelection =
                        (IStructuredSelection) selection;
                Object firstElement = structuredSelection.getFirstElement();
                arrayList = new ArrayList<Object>();
                arrayList.add(firstElement);
                orgModelQueryScriptSection.setInput(arrayList);
            }
        }
        if (sharedResourceSection != null) {
            sharedResourceSection.setInput(part, selection);
        }
    }

    @Override
    public Command doGetCommand(Object obj) {
        if (obj instanceof Button) {
            Button btn = (Button) obj;
            Participant participant = (Participant) getInput();

            // Update the declaration type to the selected option
            if (btn.equals(btnTypes[LOCAL])
                    && participant.getExternalReference() != null) {
                // Set the new selection

                /*
                 * Sid ACE-1197 - should use base method to set the base type
                 * and then append any ext-ref specific stuff to that.
                 */
                CompoundCommand compCmd = new CompoundCommand();
                compCmd.setLabel(
                        Messages.ParticipantPropertySection_SetType_menu);

                /*
                 * Sid ACE-484 Default to Org Model Query instead of Role type
                 * (as the latter isn't supported in ACE).
                 */
                Command baseCmd = getSetBaseParticipantTypeCommand(participant,
                        ParticipantType.RESOURCE_SET_LITERAL);
                if (baseCmd != null) {
                    compCmd.append(baseCmd);
                }

                Command cmd = SetCommand.create(getEditingDomain(),
                        getInput(),
                        Xpdl2Package.eINSTANCE
                                .getParticipant_ExternalReference(),
                        null);

                compCmd.append(cmd);

                book.showPage(new Integer(LOCAL));
                return compCmd;

            } else if (btn.equals(btnTypes[EXTERNALREFERENCE])
                    && participant.getExternalReference() == null) {
                ExternalReference externalReference =
                        Xpdl2Factory.eINSTANCE.createExternalReference();
                Command cmd =
                        SetCommand.create(getEditingDomain(),
                                getInput(),
                                Xpdl2Package.eINSTANCE
                                        .getParticipant_ExternalReference(),
                                externalReference);
                CompoundCommand compCmd = new CompoundCommand();
                compCmd.setLabel(Messages.ParticipantPropertySection_SetExternalReference_menu);
                compCmd.append(cmd);

                // Even though this is an external reference it MUST still have
                // a participant type element otherwise the xpdl is not valid
                // for schema).
                ParticipantTypeElem typeElem =
                        Xpdl2Factory.eINSTANCE.createParticipantTypeElem();
                typeElem.setType(ParticipantType.RESOURCE_LITERAL);

                compCmd.append(SetCommand
                        .create(getEditingDomain(),
                                participant,
                                Xpdl2Package.eINSTANCE
                                        .getParticipant_ParticipantType(),
                                typeElem));

                book.showPage(new Integer(EXTERNALREFERENCE));
                return compCmd;
            }
        }
        return super.doGetCommand(obj);

    }

    /**
     * Picker control to set the external reference.
     * 
     * @author njpatel
     */
    private class ExternalRefCtrl extends AbstractPickerControl<Object> {

        private final Object UNRESOLVED = new Object();

        private final Image errIcon;

        private Object value;

        public ExternalRefCtrl(Composite parent, int style,
                XpdFormToolkit toolkit, EditingDomain editingDomain) {
            super(parent, style, toolkit, editingDomain, false);
            setBrowseTooltip(Messages.ParticipantPropertySection_selectReference_browse_tooltip);
            // Only allow hyperlink in properties view
            setHyperlinkActive(getSectionContainerType() == ContainerType.PROPERTYVIEW);

            errIcon =
                    Xpdl2UiPlugin.getDefault().getImageRegistry()
                            .get(Xpdl2UiPlugin.IMG_ERROR);

            setLabelProvider(new LabelProvider() {
                @Override
                public String getText(Object element) {
                    String txt = null;
                    if (element != null) {
                        return getOrgModelElementDisplayName((ParticipantsContainer) getInputContainer(),
                                (Participant) getInput());
                    }
                    return txt != null ? txt : ""; //$NON-NLS-1$
                }
            });
        }

        @Override
        protected Object doBrowse(Control control) {
            IProject contextProject = null;
            if (getInputContainer() != null) {
                contextProject =
                        WorkingCopyUtil.getProjectFor(getInputContainer());
            }
            return ComplexDataUIUtil.getOMElementFromPicker(control.getShell(),
                    contextProject,
                    null);
        }

        @Override
        protected Command getClearValueCommand(EditingDomain editingDomain,
                Object value) {
            // Clear button not shown so this will not get called
            return null;
        }

        @Override
        protected Command getSetValueCommand(EditingDomain editingDomain,
                Object value) {
            if (value instanceof EObject) {
                return BPMProcessOrgModelUtil
                        .getSetOrgModelParticipantRefCommand(getEditingDomain(),
                                (ParticipantsContainer) getInputContainer(),
                                (Participant) getInput(),
                                (EObject) value);
            }
            return null;
        }

        @Override
        public void setValue(Object value) {
            labelExternalRef.setImage(null);
            this.value = value;
            if (value == UNRESOLVED) {
                value = null;
            } else if (value != null) {
                if (value instanceof EObject && ((EObject) value).eIsProxy()) {
                    this.value = UNRESOLVED;
                    value = null;
                }
            }

            super.setValue(value);
        }

        /**
         * Set the value of this control.
         * 
         * @param container
         * @param participant
         */
        public void setValue(ParticipantsContainer container,
                Participant participant) {
            if (container != null
                    && participant != null
                    && ProcessOrgModelUtil
                            .hasReferencedOrgModelEntity(container, participant)) {
                IndexerItem item =
                        ProcessOrgModelUtil
                                .getReferencedOrgModelEntity(container,
                                        participant);
                if (item != null) {
                    String uriStr = item.getURI();
                    if (uriStr != null && getEditingDomain() != null) {
                        EObject eo =
                                getEditingDomain()
                                        .getResourceSet()
                                        .getEObject(URI.createURI(uriStr), true);
                        if (eo != null) {
                            setValue(eo);
                        }
                    }
                } else {
                    // Unresolved reference
                    setValue(UNRESOLVED);
                }
            } else {
                setValue(null);
            }
        }

        @Override
        protected String getClearText() {

            if (getSectionContainerType() == ContainerType.PROPERTYVIEW) {
                labelExternalRef.setImage(errIcon);
                if (this.value == UNRESOLVED) {
                    labelExternalRef
                            .setToolTipText(Messages.ParticipantPropertySection_CannotResolveExternalRef_tooltip);
                    return Messages.BaseTypeSection_UnresolvedReference;
                }

                labelExternalRef
                        .setToolTipText(Messages.ParticipantPropertySection_externalReferenceNotSet_tooltip);
            }
            return Messages.ParticipantPropertySection_noReferenceSet_shortdesc;
        }

        @Override
        protected void hyperlinkActivated(Object value) {
            if (value instanceof EObject) {
                if (!openInEditor(value)) {
                    selectInProjectExplorer(value);
                }
            }
        }
    }

    @Override
    protected void getTypeCtrlsExpandableHeaderController(
            ExpandableSectionStacker expandableHeaderController) {
        expandableHeaderController
                .addSection(TYPECTRLS_SECTION,
                        Messages.ParticipantPropertySection_FieldTypeSectionHeader_label,
                        SWT.DEFAULT,
                        true,
                        false);
    }

    @Override
    public Control createExpandableSectionContent(String sectionId,
            Composite container, XpdFormToolkit toolkit) {
        if (TYPECTRLS_SECTION.equals(sectionId)) {
            return createTypesControl(container, toolkit);
        } else if (SHARED_RESOURCE_SECTION.equals(sectionId)) {
            return createSharedResourceControl(container, toolkit);
        } else if (QUERY_SCRIPT_SECTION.equals(sectionId)) {
            return createQueryScriptControl(container, toolkit);
        } else if (REFERENCES_HEADER_ID.equals(sectionId)) {
            return createExtraControls(container, toolkit);
        }
        return null;
    }

    /**
     * Create types option controls
     * 
     * @param parent
     * @param toolkit
     */
    private Control createTypesControl(Composite parent, XpdFormToolkit toolkit) {
        GridData gridData = null;

        Composite cmpTypes = toolkit.createComposite(parent);

        cmpTypes.setLayout(new GridLayout(3, false));
        cmpTypes.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        // Create the types radio buttons
        btnTypes = new Button[TYPES.length];

        // Add the first types option
        btnTypes[0] =
                toolkit.createButton(cmpTypes,
                        TYPES[0],
                        SWT.RADIO,
                        BLANK_INSTRUMENTATION);
        manageControl(btnTypes[0]);

        // Add separator
        gridData = new GridData(GridData.FILL_VERTICAL);
        gridData.verticalSpan = TYPES.length;
        toolkit.createSeparator(cmpTypes, SWT.VERTICAL).setLayoutData(gridData);

        // Add the book control (bookMinSizeComp is there to tell wizard / prop
        // sheet min size we want this page book to be else page book always
        // allows itself to be shrunk to 10x10
        Composite bookMinSizeComp = toolkit.createComposite(cmpTypes);
        GridLayout gl = new GridLayout(1, false);
        gl.marginHeight = 0;
        gl.marginWidth = 0;
        bookMinSizeComp.setLayout(gl);
        // We'll set layout data on bookMinSizeCompo when we know preferred size
        // of all pages.

        book = toolkit.createPageBook(bookMinSizeComp, SWT.NONE);

        // Add the rest of the types option
        for (int idx = 1; idx < TYPES.length; idx++) {
            btnTypes[idx] =
                    toolkit.createButton(cmpTypes,
                            TYPES[idx],
                            SWT.RADIO,
                            BLANK_INSTRUMENTATION);
            manageControl(btnTypes[idx]);
        }

        // Create the type declaration options pages
        Point minSize = createTypeOptionPages(toolkit);

        // Book should fill it's container composite.
        gridData = new GridData(GridData.FILL_BOTH);
        book.setLayoutData(gridData);

        // book container composite should be min size for pages.
        // This ensure that the effect parent of book is not told that min size
        // of book is 10x10.
        gridData = new GridData(GridData.FILL_BOTH);
        gridData.verticalSpan = TYPES.length;
        gridData.widthHint = minSize.x;
        gridData.heightHint = minSize.y;
        bookMinSizeComp.setLayoutData(gridData);

        return cmpTypes;
    }

    /**
     * Create type options pages
     * 
     * @param toolkit
     */
    private Point createTypeOptionPages(XpdFormToolkit toolkit) {
        Composite page = null;

        Point minSize = new Point(0, 0);

        for (int idx = 0; idx < TYPES.length; idx++) {
            page = toolkit.createComposite(book.getContainer());

            // Create pages for each type declaration type
            switch (idx) {
            case LOCAL:
                createBasicTypePage(page, toolkit);
                break;

            case EXTERNALREFERENCE:
                createExternalReferencePage(page, toolkit);
                break;
            }

            Point min = page.computeSize(SWT.DEFAULT, SWT.DEFAULT);
            if (min.x > minSize.x) {
                minSize.x = min.x;
            }
            if (min.y > minSize.y) {
                minSize.y = min.y;
            }

            book.registerPage(new Integer(idx), page);
        }

        return minSize;
    }

    /**
     * Create the basic type options page
     * 
     * @param parent
     * @param toolkit
     */
    private void createBasicTypePage(Composite parent, XpdFormToolkit toolkit) {
        GridLayout layout = new GridLayout(getNumberOfColumns(), false);
        layout.marginHeight = 0;
        parent.setLayout(layout);

        Composite basicTypeComposite = toolkit.createComposite(parent);
        layout = new GridLayout(2, false);
        layout.marginHeight = 0;
        basicTypeComposite.setLayout(layout);

        GridData gridData =
                new GridData(GridData.FILL_HORIZONTAL
                        | GridData.VERTICAL_ALIGN_BEGINNING);
        basicTypeComposite.setLayoutData(gridData);

        toolkit.createLabel(basicTypeComposite,
                Messages.ParticipantPropertySection_type_label);
        Composite radio = toolkit.createComposite(basicTypeComposite);
        radio.setLayout(new GridLayout(3, false));
        radio.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        EAttribute attrib =
                Xpdl2Package.eINSTANCE.getParticipantTypeElem_Type();

        EEnum type = (EEnum) attrib.getEType();
        int i = 0;
        typeButtons = new Button[type.getELiterals().size()];
        List<EEnumLiteral> orderedTypes = getOrderedTypes(type);
        for (EEnumLiteral enumeration : orderedTypes) {
            typeButtons[i] =
                    toolkit.createRadioButon(radio,
                            null,
                            attrib,
                            enumeration,
                            BLANK_INSTRUMENTATION);
            typeButtons[i].setText(ProcessInternalViewUtil
                    .getParticipantTypeLabel(enumeration.getInstance()));
            manageControl(typeButtons[i]);
            i++;
        }
    }

    /**
     * @param type
     * @return
     */
    private List<EEnumLiteral> getOrderedTypes(EEnum type) {
        /*
         * Sid ACE-484 Reduce the list to only those supported by ACE. (System,
         * Org model query)
         */
        List<EEnumLiteral> literals = new ArrayList<EEnumLiteral>();
        int[] types =
                new int[] { ParticipantType.RESOURCE_SET,
                        ParticipantType.SYSTEM };
        for (int count : types) {
            EEnumLiteral enumLiteral = type.getELiterals().get(count);
            literals.add(enumLiteral);
        }
        if (literals.isEmpty()) {
            return type.getELiterals();
        }
        return literals;
    }

    /**
     * @param container
     * @param toolkit
     * @return
     */
    private Control createSharedResourceControl(Composite container,
            XpdFormToolkit toolkit) {
        if (getPropertySheetPage() != null) {
            sharedResourceSection =
                    new SharedResourcesSection(
                            Xpdl2Package.eINSTANCE.getParticipant());
            sharedResourceSection.createControls(container,
                    getPropertySheetPage());
        }
        return container;
    }
}
