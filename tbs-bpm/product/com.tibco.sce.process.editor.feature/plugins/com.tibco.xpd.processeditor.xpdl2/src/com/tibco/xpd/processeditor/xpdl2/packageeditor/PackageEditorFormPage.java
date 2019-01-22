/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.packageeditor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.util.Policy;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.MultiPageSelectionProvider;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorConfigurationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorElementType;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.NotificationPropertyChangeEvent;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.TypeDeclaration;

/**
 * 
 * Package Form editor page.
 * 
 * @author rsomayaj
 * @since 3.3 (25 Feb 2010)
 */
public class PackageEditorFormPage extends FormPage {

    private FormToolkit formToolKit;

    private Form form;

    private HyperLinkListViewer procIfcLinksViewer;

    private HyperLinkListViewer procLinksViewer;

    private HyperLinkListViewer participantLinksViewer;

    private HyperLinkListViewer typeDeclarationLinksViewer;

    private HyperLinkListViewer dataFieldLinksViewer;

    private WorkingCopy workingCopy;

    private AddProcessIfcAction addInterfaceAction;

    private AddProcessAction addProcessAction;

    private AddPageflowAction addPageflowAction;

    private AddBusinessServiceAction addBizServAction;

    private AddCaseServiceAction addCaseServiceAction;

    private AddServiceProcessAction addServiceProcessAction;

    private AddServiceProcessIfcAction addServiceProcessIfcAction;

    private AddParticipantAction addParticipantAction;

    private AddTypeDeclarationAction addTypeDeclarationAction;

    /*
     * Data fields hyperlink list - this is hidden unless there actually are
     * datafields - this goes along with the policy of discouraging package
     * datafield usage.
     */
    private Section dataFieldsSection;

    private AddDataFieldAction addDataFieldAction;

    private Section participantSection;

    private Section typeDeclarationSection;

    private Section processSection;

    private Section procIfcSection;

    private Composite editorPageComposite;

    private ISelectionProvider selectionProvider;

    private WorkingCopyListener wcListener;

    private final PackageMouseListener pkgMouseListener;

    /**
     * @param editor
     * @param id
     * @param title
     */
    public PackageEditorFormPage(FormEditor editor, String id, String title,
            WorkingCopy workingCopy) {
        super(editor, id, title);
        this.workingCopy = workingCopy;
        IWorkbenchPartSite site = editor.getSite();
        if (site != null) {
            selectionProvider = site.getSelectionProvider();
        }

        wcListener = new WorkingCopyListener();
        workingCopy.addListener(wcListener);

        pkgMouseListener = new PackageMouseListener();
    }

    @Override
    protected void createFormContent(IManagedForm managedForm) {
        ScrolledForm scrolledForm = managedForm.getForm();

        formToolKit = new FormToolkit(scrolledForm.getDisplay());
        scrolledForm.getBody().setLayout(new FillLayout());

        form = formToolKit.createForm(scrolledForm.getBody());

        /*
         * XPD-1140: Show correct title to same as editor title (display name
         * (tokenname) ( + [Read-Only] as appropriate)
         */

        Package xpdlPackage = getXpdlPackage();
        if (xpdlPackage == null) {
            // XPD-1544: NPE Check while opening the XPDL file using the Package
            // editor.
            form.setText(Messages.PackageEditorFormPage_InvalidMarkersExistErr_shortdesc);
            return;
        }
        String title = WorkingCopyUtil.getText(xpdlPackage);
        if (workingCopy != null && workingCopy.isReadOnly()) {
            title += " " + Messages.PackageEditor_ReadOnly_label; //$NON-NLS-1$ 
        }
        form.setText(title);

        form.setImage(Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                .get(ProcessEditorConstants.IMG_PACKAGEFILE));

        /*
         * Set our own layout that always just is the same size as scrolled form
         * size - then any scroleld thing inside the scrolled form will be
         * constrained in size so it's scroll bars should function ok.
         */
        form.getBody().setLayout(new Layout() {
            @Override
            protected Point computeSize(Composite composite, int wHint,
                    int hHint, boolean flushCache) {
                return new Point(wHint, hHint);
            }

            @Override
            protected void layout(Composite composite, boolean flushCache) {
                Point size = composite.getSize();

                Control[] children = composite.getChildren();
                if (children != null && children.length > 0) {
                    int ysize = size.y / children.length;
                    int yoff = 0;

                    for (Control child : children) {
                        child.setBounds(0, yoff, size.x, ysize);
                        yoff += ysize;
                    }
                }
            }
        });

        formToolKit.decorateFormHeading(form);
        // form.setLayoutData(new GridData(GridData.FILL_BOTH));

        addProcessAction = new AddProcessAction(xpdlPackage);

        addPageflowAction = new AddPageflowAction(xpdlPackage);
        addBizServAction = new AddBusinessServiceAction(xpdlPackage);
        addCaseServiceAction = new AddCaseServiceAction(xpdlPackage);
        addServiceProcessAction = new AddServiceProcessAction(xpdlPackage);

        /* May as well select package when form header bar selected. */
        form.getHead().addMouseListener(pkgMouseListener);

        addInterfaceAction = new AddProcessIfcAction(xpdlPackage);
        addServiceProcessIfcAction =
                new AddServiceProcessIfcAction(xpdlPackage);

        addParticipantAction = new AddParticipantAction(xpdlPackage);

        addTypeDeclarationAction = new AddTypeDeclarationAction(xpdlPackage);

        addDataFieldAction = new AddDataFieldAction(xpdlPackage);

        /*
         * Add Action buttons to the toolbar
         */
        addButtonsToToolbar(getEnabledToolbarActionButtons());

        editorPageComposite = formToolKit.createComposite(form.getBody());

        editorPageComposite.setLayout(new Layout() {

            @Override
            protected Point computeSize(Composite composite, int wHint,
                    int hHint, boolean flushCache) {
                return new Point(wHint, hHint);
            }

            @Override
            protected void layout(Composite composite, boolean flushCache) {
                int xmargin = 5;
                int ymargin = 5;

                Control[] children = composite.getChildren();
                int ctrlIdx = 0;

                Point size = composite.getSize();

                List<Section> sectionsToHide = getSectionsToHide();
                /*
                 * get the visible sections,
                 */
                int totalVisibleSections = 5 - getSectionsToHide().size();
                /*
                 * Calculate the number of rows. we show 2 sections per row , so
                 * if total visible sections are 3 then the (3/2) = 1 and
                 * (3%2)=1 hence we will have 2 rows.
                 */
                int numRows =
                        (totalVisibleSections / 2) + (totalVisibleSections % 2);

                int width = (size.x - (xmargin * 3)) / 2;
                int height = (size.y - (ymargin * (numRows + 1))) / numRows;

                int y = ymargin;

                for (int r = 0; r < numRows; r++) {
                    int x = xmargin;
                    for (int c = 0; c < 2;) {
                        Control ctrl = children[ctrlIdx++];

                        if (sectionsToHide.contains(ctrl)) {
                            /*
                             * If a section should be hidden then set the height
                             * = -1 and dont increase the x-coordinate.
                             */
                            ctrl.setBounds(x, y, width, -1);

                        } else {
                            ctrl.setBounds(x, y, width, height);
                            x += width + xmargin;
                            c++;
                        }
                    }
                    y += height + ymargin;
                }
            }
        });

        createProcessSection(editorPageComposite);
        createParticipantsSection(editorPageComposite);
        createProcIfcSection(editorPageComposite);
        createTypeDeclarationsSection(editorPageComposite);
        createDataFieldsSection(editorPageComposite);
        Composite filler = formToolKit.createComposite(editorPageComposite);

        filler.addMouseListener(pkgMouseListener);
        editorPageComposite.addMouseListener(pkgMouseListener);

        /*
         * XPD-1140: Disallow create for read only working copy (i.e. from
         * repository copy etc)
         */
        enableActions();

        Package pkg = xpdlPackage;

        /*
         * Set the package as the initial selection of this editor so the
         * properties sections for the package are displayed.
         */
        if (selectionProvider != null && pkg != null) {
            selectionChanged(pkg);
        }

        return;
    }

    /**
     * Enable/disable actions according to readonly state.
     */
    private void enableActions() {
        boolean enabled = (workingCopy != null && !workingCopy.isReadOnly());
        addProcessAction.setEnabled(enabled);
        addPageflowAction.setEnabled(enabled);
        addBizServAction.setEnabled(enabled);
        addCaseServiceAction.setEnabled(enabled);
        addServiceProcessAction.setEnabled(enabled);
        addInterfaceAction.setEnabled(enabled);
        addServiceProcessIfcAction.setEnabled(enabled);
        addParticipantAction.setEnabled(enabled);
        addTypeDeclarationAction.setEnabled(enabled);
        addDataFieldAction.setEnabled(enabled);
    }

    /**
     * @param editorPageComposite
     */
    private void createProcIfcSection(Composite editorPageComposite) {
        procIfcSection =
                formToolKit.createSection(editorPageComposite, Section.EXPANDED
                        | Section.TITLE_BAR);

        procIfcSection.setText(Messages.PackageEditorFormPage_ProcIfcLabel);

        procIfcLinksViewer =
                new HyperLinkListViewer(procIfcSection, SWT.H_SCROLL
                        | SWT.V_SCROLL);

        procIfcLinksViewer.getControl().setLayoutData(new GridData());

        procIfcLinksViewer.setHyperLinkListener(new FormHyperLinkListener());
        procIfcLinksViewer
                .setContentProvider(new PackageContentHyperlinkContentProvider() {
                    @Override
                    protected EList<? extends EObject> getEObjectElements(
                            Package pkg) {
                        ProcessInterfaces processInterfaces =
                                ProcessInterfaceUtil.getProcessInterfaces(pkg);
                        if (processInterfaces != null) {
                            return processInterfaces.getProcessInterface();
                        }
                        return null;
                    };

                    /**
                     * @see com.tibco.xpd.processeditor.xpdl2.packageeditor.PackageEditorFormPage.PackageContentHyperlinkContentProvider#getCreateElementHyperLinks(com.tibco.xpd.xpdl2.Package)
                     * 
                     * @param arg0
                     * @return
                     */
                    @Override
                    protected List<FormHyperLinkItem> getCreateElementHyperLinks(
                            Package arg0) {

                        List<FormHyperLinkItem> hyperLinks =
                                new ArrayList<FormHyperLinkItem>();
                        /*
                         * Show process interface only if it is not excluded
                         */
                        if (shouldShowProcessEditorElement(arg0,
                                ProcessEditorElementType.process_interface)) {

                            FormHyperLinkItem createProcessIfc =
                                    new FormHyperLinkItem(
                                            Messages.PackageEditorFormPage_CreateProcIfc_label,
                                            Xpdl2ProcessEditorPlugin
                                                    .getDefault()
                                                    .getImageRegistry()
                                                    .get(ProcessEditorConstants.IMG_PROCESS_IFC_NEW)) {

                                        @Override
                                        public void performAction() {
                                            addInterfaceAction.run();
                                        }

                                    };

                            hyperLinks.add(createProcessIfc);
                        }

                        /*
                         * Show process interface only if it is not excluded
                         */
                        if (shouldShowProcessEditorElement(arg0,
                                ProcessEditorElementType.service_process_interface)) {

                            FormHyperLinkItem createServiceProcessIfc =
                                    new FormHyperLinkItem(
                                            Messages.PackageEditorFormPage_CreateServiceProcIfc_label,
                                            Xpdl2ProcessEditorPlugin
                                                    .getDefault()
                                                    .getImageRegistry()
                                                    .get(ProcessEditorConstants.IMG_SERVICE_PROCESS_IFC_NEW)) {

                                        @Override
                                        public void performAction() {
                                            addServiceProcessIfcAction.run();
                                        }

                                    };

                            hyperLinks.add(createServiceProcessIfc);
                        }

                        return hyperLinks;
                    };
                });

        procIfcLinksViewer.setLabelProvider(new HyperLinkLabelProvider());

        procIfcLinksViewer.setInput(getXpdlPackage());

        procIfcLinksViewer.getControl().addMouseListener(pkgMouseListener);

        procIfcSection.setClient(procIfcLinksViewer.getControl());
    }

    /**
     * @param editorPageComposite
     */
    private void createProcessSection(Composite editorPageComposite) {
        processSection =
                formToolKit.createSection(editorPageComposite, Section.EXPANDED
                        | Section.TITLE_BAR);

        processSection
                .setText(Messages.PackageEditorFormPage_ProcessHeader_label);

        procLinksViewer =
                new HyperLinkListViewer(processSection, SWT.H_SCROLL
                        | SWT.V_SCROLL);

        procLinksViewer.getControl().setLayoutData(new GridData());

        procLinksViewer.setHyperLinkListener(new FormHyperLinkListener());
        procLinksViewer
                .setContentProvider(new PackageContentHyperlinkContentProvider() {
                    @Override
                    protected EList<? extends EObject> getEObjectElements(
                            Package pkg) {
                        return pkg.getProcesses();
                    };

                    /**
                     * @see com.tibco.xpd.processeditor.xpdl2.packageeditor.PackageEditorFormPage.PackageContentHyperlinkContentProvider#getCreateElementHyperLinks(com.tibco.xpd.xpdl2.Package)
                     * 
                     * @param arg0
                     * @return
                     */
                    @Override
                    protected List<FormHyperLinkItem> getCreateElementHyperLinks(
                            Package arg0) {
                        List<FormHyperLinkItem> hyperLinks =
                                new ArrayList<FormHyperLinkItem>();
                        /*
                         * Show business process only if it is not excluded
                         */
                        if (shouldShowProcessEditorElement(arg0,
                                ProcessEditorElementType.business_process)) {

                            FormHyperLinkItem createProcess =
                                    new FormHyperLinkItem(
                                            Messages.PackageEditorFormPage_CreateProcess_label,
                                            Xpdl2ProcessEditorPlugin
                                                    .getDefault()
                                                    .getImageRegistry()
                                                    .get(ProcessEditorConstants.IMG_PROCESS_NEW)) {

                                        @Override
                                        public void performAction() {
                                            addProcessAction.run();
                                        }

                                    };
                            hyperLinks.add(createProcess);

                        }
                        /*
                         * Show pageflow process only if it is not excluded
                         */
                        if (shouldShowProcessEditorElement(arg0,
                                ProcessEditorElementType.pageflow_process)) {

                            /*
                             * SID XPD-2769 Only show pageflow creation if WRM
                             * feature s available (same as for pageflow
                             * generation menu items and user task property
                             * radio option.
                             */
                            if (Xpdl2ResourcesPlugin.isPageflowAvailable()) {
                                FormHyperLinkItem createPageflow =
                                        new FormHyperLinkItem(
                                                Messages.PackageEditorFormPage_CreatePageflow_label,
                                                Xpdl2ProcessEditorPlugin
                                                        .getDefault()
                                                        .getImageRegistry()
                                                        .get(ProcessEditorConstants.IMG_PAGEFLOW_PROCESS_NEW)) {

                                            @Override
                                            public void performAction() {
                                                addPageflowAction.run();
                                            }

                                        };

                                hyperLinks.add(createPageflow);

                            }
                        }
                        /*
                         * Show business service only if it is not excluded
                         */
                        if (shouldShowProcessEditorElement(arg0,
                                ProcessEditorElementType.business_service)) {

                            FormHyperLinkItem createBizServ =
                                    new FormHyperLinkItem(
                                            Messages.PackageEditorFormPage_CreateBusinessService_label,
                                            Xpdl2ProcessEditorPlugin
                                                    .getDefault()
                                                    .getImageRegistry()
                                                    .get(ProcessEditorConstants.IMG_BUSINESS_SERVICE_PAGEFLOW_PROCESS_NEW)) {

                                        @Override
                                        public void performAction() {

                                            addBizServAction.run();
                                        }

                                    };
                            hyperLinks.add(createBizServ);
                        }
                        /*
                         * Show case service only if it is not excluded
                         */
                        if (shouldShowProcessEditorElement(arg0,
                                ProcessEditorElementType.case_service)) {

                            FormHyperLinkItem createCaseServ =
                                    new FormHyperLinkItem(
                                            Messages.PackageEditorFormPage_CreateCaseService_label,
                                            Xpdl2ProcessEditorPlugin
                                                    .getDefault()
                                                    .getImageRegistry()
                                                    .get(ProcessEditorConstants.IMG_CASE_SERVICE_PAGEFLOW_PROCESS_NEW)) {

                                        @Override
                                        public void performAction() {

                                            addCaseServiceAction.run();
                                        }

                                    };
                            hyperLinks.add(createCaseServ);
                        }

                        /*
                         * Show Service Process only if it is not excluded
                         */
                        if (shouldShowProcessEditorElement(arg0,
                                ProcessEditorElementType.service_process)) {

                            FormHyperLinkItem createServiceProcess =
                                    new FormHyperLinkItem(
                                            Messages.PackageEditorFormPage_CreateServiceProcess_label,
                                            Xpdl2ProcessEditorPlugin
                                                    .getDefault()
                                                    .getImageRegistry()
                                                    .get(ProcessEditorConstants.IMG_SERVICE_PROCESS_NEW)) {

                                        @Override
                                        public void performAction() {

                                            addServiceProcessAction.run();
                                        }

                                    };
                            hyperLinks.add(createServiceProcess);
                        }

                        return hyperLinks;
                    }

                });

        procLinksViewer.setLabelProvider(new HyperLinkLabelProvider());

        procLinksViewer.setInput(getXpdlPackage());

        procLinksViewer.getControl().addMouseListener(pkgMouseListener);

        processSection.setClient(procLinksViewer.getControl());

    }

    /**
     * @param editorPageComposite
     */
    private void createParticipantsSection(Composite editorPageComposite) {
        participantSection =
                formToolKit.createSection(editorPageComposite, Section.EXPANDED
                        | Section.TITLE_BAR);

        participantSection
                .setText(Messages.PackageEditorFormPage_Participants_label);

        participantLinksViewer =
                new HyperLinkListViewer(participantSection, SWT.H_SCROLL
                        | SWT.V_SCROLL);

        participantLinksViewer.getControl().setLayoutData(new GridData());

        participantLinksViewer
                .setHyperLinkListener(new FormHyperLinkSelectionListener(
                        selectionProvider));
        participantLinksViewer
                .setContentProvider(new PackageContentHyperlinkContentProvider() {
                    @Override
                    protected EList<? extends EObject> getEObjectElements(
                            Package pkg) {
                        return pkg.getParticipants();
                    };

                    /**
                     * @see com.tibco.xpd.processeditor.xpdl2.packageeditor.PackageEditorFormPage.PackageContentHyperlinkContentProvider#getCreateElementHyperLinks(com.tibco.xpd.xpdl2.Package)
                     * 
                     * @param arg0
                     * @return
                     */
                    @Override
                    protected List<FormHyperLinkItem> getCreateElementHyperLinks(
                            Package arg0) {
                        List<FormHyperLinkItem> hyperLinks =
                                new ArrayList<FormHyperLinkItem>();
                        /*
                         * Show participants only if it is not excluded
                         */
                        if (shouldShowProcessEditorElement(arg0,
                                ProcessEditorElementType.participant)) {

                            FormHyperLinkItem createPartic =
                                    new FormHyperLinkItem(
                                            Messages.PackageEditorFormPage_CreateNewParticipant_button,
                                            Xpdl2ProcessEditorPlugin
                                                    .getDefault()
                                                    .getImageRegistry()
                                                    .get(ProcessEditorConstants.IMG_PARTICIPANT_NEW)) {

                                        @Override
                                        public void performAction() {
                                            addParticipantAction.run();
                                        }

                                    };
                            hyperLinks.add(createPartic);

                        }
                        return hyperLinks;
                    };
                });

        participantLinksViewer.setLabelProvider(new HyperLinkLabelProvider());

        participantLinksViewer.setInput(getXpdlPackage());

        participantLinksViewer.getControl().addMouseListener(pkgMouseListener);

        participantSection.setClient(participantLinksViewer.getControl());

    }

    /**
     * @param editorPageComposite
     */
    private void createTypeDeclarationsSection(Composite editorPageComposite) {
        typeDeclarationSection =
                formToolKit.createSection(editorPageComposite, Section.EXPANDED
                        | Section.TITLE_BAR);

        typeDeclarationSection
                .setText(Messages.PackageEditorFormPage_TypeDeclarations_title);

        typeDeclarationLinksViewer =
                new HyperLinkListViewer(typeDeclarationSection, SWT.H_SCROLL
                        | SWT.V_SCROLL);

        typeDeclarationLinksViewer.getControl().setLayoutData(new GridData());

        typeDeclarationLinksViewer
                .setHyperLinkListener(new FormHyperLinkSelectionListener(
                        selectionProvider));
        typeDeclarationLinksViewer
                .setContentProvider(new PackageContentHyperlinkContentProvider() {
                    @Override
                    protected EList<? extends EObject> getEObjectElements(
                            Package pkg) {
                        return pkg.getTypeDeclarations();
                    };

                    /**
                     * @see com.tibco.xpd.processeditor.xpdl2.packageeditor.PackageEditorFormPage.PackageContentHyperlinkContentProvider#getCreateElementHyperLinks(com.tibco.xpd.xpdl2.Package)
                     * 
                     * @param arg0
                     * @return
                     */
                    @Override
                    protected List<FormHyperLinkItem> getCreateElementHyperLinks(
                            Package pkg) {

                        List<FormHyperLinkItem> hyperLinks =
                                new ArrayList<FormHyperLinkItem>();
                        /*
                         * Show type declarations only if it is not excluded
                         */
                        if (shouldShowProcessEditorElement(pkg,
                                ProcessEditorElementType.type_declaration)) {

                            FormHyperLinkItem createTypeDecl =
                                    new FormHyperLinkItem(
                                            Messages.PackageEditorFormPage_CreateNewType_button,
                                            Xpdl2ProcessEditorPlugin
                                                    .getDefault()
                                                    .getImageRegistry()
                                                    .get(ProcessEditorConstants.IMG_TYPEDECLARATION_NEW)) {

                                        @Override
                                        public void performAction() {
                                            addTypeDeclarationAction.run();
                                        }

                                    };
                            hyperLinks.add(createTypeDecl);

                        }
                        return hyperLinks;
                    };
                });

        typeDeclarationLinksViewer
                .setLabelProvider(new HyperLinkLabelProvider());

        typeDeclarationLinksViewer.setInput(getXpdlPackage());

        typeDeclarationLinksViewer.getControl()
                .addMouseListener(pkgMouseListener);

        typeDeclarationSection.setClient(typeDeclarationLinksViewer
                .getControl());

    }

    /**
     * @param editorPageComposite
     */
    private void createDataFieldsSection(Composite editorPageComposite) {
        dataFieldsSection =
                formToolKit.createSection(editorPageComposite, Section.EXPANDED
                        | Section.TITLE_BAR);

        dataFieldsSection
                .setText(Messages.PackageEditorFormPage_DataFields_label);

        dataFieldLinksViewer =
                new HyperLinkListViewer(dataFieldsSection, SWT.H_SCROLL
                        | SWT.V_SCROLL);

        dataFieldLinksViewer.getControl().setLayoutData(new GridData());

        dataFieldLinksViewer
                .setHyperLinkListener(new FormHyperLinkSelectionListener(
                        selectionProvider));
        dataFieldLinksViewer
                .setContentProvider(new PackageContentHyperlinkContentProvider() {
                    @Override
                    protected EList<? extends EObject> getEObjectElements(
                            Package pkg) {
                        return pkg.getDataFields();
                    };

                    /**
                     * @see com.tibco.xpd.processeditor.xpdl2.packageeditor.PackageEditorFormPage.PackageContentHyperlinkContentProvider#getCreateElementHyperLinks(com.tibco.xpd.xpdl2.Package)
                     * 
                     * @param pkg
                     * @return
                     */
                    @Override
                    protected List<FormHyperLinkItem> getCreateElementHyperLinks(
                            Package pkg) {
                        List<FormHyperLinkItem> hyperLinks =
                                new ArrayList<FormHyperLinkItem>();
                        /*
                         * Show data fields only if it is not excluded
                         */
                        if (shouldShowProcessEditorElement(pkg,
                                ProcessEditorElementType.datafield)) {

                            FormHyperLinkItem createField =
                                    new FormHyperLinkItem(
                                            Messages.PackageEditorFormPage_CreateNewField_button,
                                            Xpdl2ProcessEditorPlugin
                                                    .getDefault()
                                                    .getImageRegistry()
                                                    .get(ProcessEditorConstants.IMG_DATAFIELD_NEW)) {

                                        @Override
                                        public void performAction() {
                                            addDataFieldAction.run();
                                        }

                                    };

                            hyperLinks.add(createField);
                        }
                        return hyperLinks;
                    };
                });

        dataFieldLinksViewer.setLabelProvider(new HyperLinkLabelProvider());

        dataFieldLinksViewer.setInput(getXpdlPackage());

        dataFieldLinksViewer.getControl().addMouseListener(pkgMouseListener);

        dataFieldsSection.setClient(dataFieldLinksViewer.getControl());

    }

    /**
     * 
     * @param pkg
     *            the package
     * @param processEditorElementType
     *            the Element under focus[if to show it or not]
     * @return <code>true</code> is the passed Process Editor Element should be
     *         visible in the Package editor, else return <code>false</code>
     */
    private boolean shouldShowProcessEditorElement(Package pkg,
            ProcessEditorElementType processEditorElementType) {

        Set<ProcessEditorElementType> excludedElementTypes =
                ProcessEditorConfigurationUtil.getExcludedElementTypes(pkg);

        return !excludedElementTypes.contains(processEditorElementType);
    }

    /**
     * @return
     */
    private Package getXpdlPackage() {
        /*
         * XPD-1140: PackageEditor loads and passes the working copy now.
         */
        if (null != workingCopy
                && workingCopy.getRootElement() instanceof Package) {
            return (Package) workingCopy.getRootElement();
        }

        return null;
    }

    @Override
    public void dispose() {
        if (dataFieldLinksViewer != null) {
            dataFieldLinksViewer.getControl()
                    .removeMouseListener(pkgMouseListener);
            dataFieldLinksViewer.dispose();
        }

        if (participantLinksViewer != null) {
            participantLinksViewer.getControl()
                    .removeMouseListener(pkgMouseListener);
            participantLinksViewer.dispose();
        }

        if (procLinksViewer != null) {
            procLinksViewer.getControl().removeMouseListener(pkgMouseListener);
            procLinksViewer.dispose();
        }

        if (procIfcLinksViewer != null) {
            procIfcLinksViewer.getControl()
                    .removeMouseListener(pkgMouseListener);
            procIfcLinksViewer.dispose();
        }

        if (typeDeclarationLinksViewer != null) {
            typeDeclarationLinksViewer.getControl()
                    .removeMouseListener(pkgMouseListener);
            typeDeclarationLinksViewer.dispose();
        }
        formToolKit.dispose();

        if (wcListener != null && workingCopy != null) {
            workingCopy.removeListener(wcListener);
            wcListener = null;
        }

        super.dispose();
    }

    /**
     * @see org.eclipse.ui.forms.editor.FormPage#setFocus()
     * 
     */
    @Override
    public void setFocus() {
        if (form != null) {
            form.setFocus();
        }
    }

    /**
     * @return the form
     */
    public Form getForm() {
        return form;
    }

    /**
     * 
     */
    public void doRefreshSections() {
        if (form.getBody() != null && !form.getBody().isDisposed()) {
            /*
             * XPD-1140: Disallow create for read only working copy (i.e. from
             * repository copy etc)
             */
            enableActions();

            procLinksViewer.refresh();
            procIfcLinksViewer.refresh();
            participantLinksViewer.refresh();
            typeDeclarationLinksViewer.refresh();
            dataFieldLinksViewer.refresh();

            Set<Action> actionsToAdd = getEnabledToolbarActionButtons();
            /*
             * Only add/remove toolbar buttons if they have changed.
             */
            if (shouldReAddToolbarButtons(actionsToAdd)) {
                /*
                 * Kapil- XPD-6664 :During refresh remove all the buttons from
                 * the toolbar and add only those buttons which are enabled.
                 * Note: the reason we remove all the buttons and add them again
                 * is because if we remove a button and then add it, it gets
                 * added to the end of the list (i.e. as the last button) which
                 * will disturb the order of the buttons in the UI, which is
                 * unexpected. Also we can also add a button using the
                 * inserBefore or insertAfter method, but for that we need to
                 * first make sure that the previous aor next button is not
                 * excluded.So the best way is to remove all the buttons on
                 * refresh and add the necessary buttons to the toolbar
                 */
                form.getToolBarManager().removeAll();

                addButtonsToToolbar(actionsToAdd);
                /*
                 * set the parent layout because we would want to remove the
                 * sections of the removed buttons as well.
                 */
                form.getBody().layout(true, true);
                editorPageComposite.layout(true, true);
            }
        }
    }

    /**
     * Adds the buttons to the toolbar
     * 
     * @param buttonsToAdd
     *            the Actions which should be added as buttons to the toolbar
     */
    private void addButtonsToToolbar(Set<Action> buttonsToAdd) {

        for (Action action : buttonsToAdd) {

            form.getToolBarManager().add(action);
        }

        form.getToolBarManager().update(true);
    }

    /**
     * 
     * @param newActionsToAdd
     *            the new Actions which should be added to the toolbar
     * @return <code>true</code> if new buttons which should be added to the
     *         toolbar are different from the already present toolbar buttons,
     *         else if the buttons are same then return <code>false</code>
     */
    private boolean shouldReAddToolbarButtons(Set<Action> newActionsToAdd) {

        IContributionItem[] items = form.getToolBarManager().getItems();

        if (items.length != newActionsToAdd.size()) {
            /*
             * If the number of Actions differ then we need to add the buttons
             * for sure
             */
            return true;
        }

        /*
         * If the number of Actions are the same then check if any existing
         * action was removed
         */
        for (IContributionItem item : items) {

            if (item instanceof ActionContributionItem
                    && ((ActionContributionItem) item).getAction() != null) {

                IAction existingAction =
                        ((ActionContributionItem) item).getAction();

                boolean foundAction = false;

                for (Action newAction : newActionsToAdd) {

                    if (existingAction.equals(newAction)) {
                        foundAction = true;
                        break;
                    }
                }

                if (!foundAction) {
                    /*
                     * If an existing actions was not found it means we need a
                     * refresh
                     */
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Gets a list of all Sections to hide, a section will be hidden if the
     * element it creates is excluded by the ProcessEditorConfig and there are
     * no existing elements.
     * 
     * @return List of all the Sections to Hide.
     */
    private List<Section> getSectionsToHide() {

        List<Section> sectionsToHide = new ArrayList<Section>();

        Package xpdlPackage = getXpdlPackage();

        if (xpdlPackage != null) {
            /*
             * Get all the excluded process editor elements.
             */
            Set<ProcessEditorElementType> excludedElementTypes =
                    ProcessEditorConfigurationUtil
                            .getExcludedElementTypes(xpdlPackage);

            if (excludedElementTypes
                    .contains(ProcessEditorElementType.business_process)
                    && excludedElementTypes
                            .contains(ProcessEditorElementType.pageflow_process)
                    && excludedElementTypes
                            .contains(ProcessEditorElementType.business_service)
                    && excludedElementTypes
                            .contains(ProcessEditorElementType.case_service)
                    && excludedElementTypes
                            .contains(ProcessEditorElementType.service_process)) {

                EList<com.tibco.xpd.xpdl2.Process> processes =
                        xpdlPackage.getProcesses();

                if (processes.isEmpty()) {
                    /*
                     * Hide Process section
                     */
                    sectionsToHide.add(processSection);
                }
            }

            if (excludedElementTypes
                    .contains(ProcessEditorElementType.process_interface)
                    && excludedElementTypes
                            .contains(ProcessEditorElementType.service_process_interface)) {

                boolean hideProcessIfcSection = false;

                ProcessInterfaces processInterfaces =
                        ProcessInterfaceUtil.getProcessInterfaces(xpdlPackage);

                if (processInterfaces != null) {

                    EList<ProcessInterface> processInterface =
                            processInterfaces.getProcessInterface();

                    hideProcessIfcSection = processInterface.isEmpty();
                } else {

                    hideProcessIfcSection = true;
                }

                if (hideProcessIfcSection) {
                    /* Hide process interface section */
                    sectionsToHide.add(procIfcSection);
                }
            }

            if (excludedElementTypes
                    .contains(ProcessEditorElementType.participant)) {

                EList<Participant> participants = xpdlPackage.getParticipants();

                if (participants.isEmpty()) {
                    /* hide participant section */
                    sectionsToHide.add(participantSection);
                }
            }

            if (excludedElementTypes
                    .contains(ProcessEditorElementType.type_declaration)) {

                EList<TypeDeclaration> typeDeclarations =
                        xpdlPackage.getTypeDeclarations();

                if (typeDeclarations.isEmpty()) {
                    /* hide type declaration section */
                    sectionsToHide.add(typeDeclarationSection);
                }
            }

            /*
             * Simply hide data field sections if there are no data fields
             * present.
             */
            EList<DataField> dataFields = xpdlPackage.getDataFields();
            if (dataFields.isEmpty()) {
                sectionsToHide.add(dataFieldsSection);
            }

        }
        return sectionsToHide;
    }

    /**
     * Gets the necessary buttons which should be added to the toolbar. When we
     * say necessary that means that we check if a Element is not excluded via
     * the ProcessEditorConfiguration, in which case we should not add it to the
     * toolbar, also in case of Data field we additionally check if the Package
     * has any data-fields, if no then do not add data field button to toolbar.
     * <p>
     * We add the buttons in the following chronological order
     * <p>
     * 1. Business Process
     * <p>
     * 2. Pageflow Process
     * <p>
     * 3. Business Service
     * <p>
     * 4. Case Action(Service)
     * <p>
     * 5. Process Interface
     * <p>
     * 6. Participant
     * <p>
     * 7. Type Declaration
     * <p>
     * 8. Data Field
     * 
     * @return the buttons which should be added to the toolbar else empty Set
     *         if no buttons to add
     */
    private Set<Action> getEnabledToolbarActionButtons() {
        /*
         * Use LinkedHashSet because we need to maintain the exact order in
         * which the buttons should be added.
         */
        Set<Action> actionsToAdd = new LinkedHashSet<Action>();

        Package xpdlPackage = getXpdlPackage();

        if (xpdlPackage != null) {
            /*
             * Get all the excluded process editor elements.
             */
            Set<ProcessEditorElementType> excludedElementTypes =
                    ProcessEditorConfigurationUtil
                            .getExcludedElementTypes(xpdlPackage);

            if (!excludedElementTypes
                    .contains(ProcessEditorElementType.business_process)) {
                /* Dont show Business Process is it is excluded */
                actionsToAdd.add(addProcessAction);
            }

            /*
             * SID XPD-2769 Only show pageflow creation if WRM feature s
             * available (same as for pageflow generation menu items and user
             * task property radio option.
             */
            if (Xpdl2ResourcesPlugin.isPageflowAvailable()) {

                if (!excludedElementTypes
                        .contains(ProcessEditorElementType.pageflow_process)) {
                    /* Dont show Pageflow Process is it is excluded */
                    actionsToAdd.add(addPageflowAction);
                }

                if (!excludedElementTypes
                        .contains(ProcessEditorElementType.business_service)) {
                    /* Dont show Business Service is it is excluded */
                    actionsToAdd.add(addBizServAction);
                }

                if (!excludedElementTypes
                        .contains(ProcessEditorElementType.case_service)) {
                    /* Dont show Case Action is it is excluded */
                    actionsToAdd.add(addCaseServiceAction);
                }

                if (!excludedElementTypes
                        .contains(ProcessEditorElementType.service_process)) {
                    /* Dont show Service Process is it is excluded */
                    actionsToAdd.add(addServiceProcessAction);
                }
            }

            if (!excludedElementTypes
                    .contains(ProcessEditorElementType.process_interface)) {
                /* Dont show Process Interface is it is excluded */
                actionsToAdd.add(addInterfaceAction);
            }

            if (!excludedElementTypes
                    .contains(ProcessEditorElementType.service_process_interface)) {
                /* Dont show Process Interface is it is excluded */
                actionsToAdd.add(addServiceProcessIfcAction);
            }

            if (!excludedElementTypes
                    .contains(ProcessEditorElementType.participant)) {
                /* Dont show Participant is it is excluded */
                actionsToAdd.add(addParticipantAction);
            }

            if (!excludedElementTypes
                    .contains(ProcessEditorElementType.type_declaration)) {
                /* Dont show Type Declaration is it is excluded */
                actionsToAdd.add(addTypeDeclarationAction);
            }

            if (!xpdlPackage.getDataFields().isEmpty()) {
                /*
                 * Dont show Data fields is the package has no existing data
                 * fields
                 */
                if (!excludedElementTypes
                        .contains(ProcessEditorElementType.datafield)) {
                    /* Dont show Data fields is it is excluded */
                    actionsToAdd.add(addDataFieldAction);
                }
            }
        }

        return actionsToAdd;
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
    private void selectionChanged(Object newSelection) {
        StructuredSelection selection = new StructuredSelection(newSelection);
        selectionProvider.setSelection(selection);

        if (selectionProvider instanceof MultiPageSelectionProvider) {
            ((MultiPageSelectionProvider) selectionProvider)
                    .firePostSelectionChanged(new SelectionChangedEvent(
                            selectionProvider, selection));
        }
    }

    /**
     * Hyperlink item that opens the given EObject it's nominal editor.
     * 
     * @author aallway
     * @since 22 Dec 2010
     */
    private class OpenEObjectEditorHyperLinkItem extends FormHyperLinkItem {

        /**
         * @param data
         * @param label
         * @param image
         */
        public OpenEObjectEditorHyperLinkItem(EObject eObject) {
            super(eObject, WorkingCopyUtil.getText(eObject), WorkingCopyUtil
                    .getImage(eObject));
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.packageeditor.PackageEditorFormPage.FormHyperLinkItem#performAction()
         * 
         */
        @Override
        public void performAction() {
            ((PackageEditor) getEditor())
                    .gotoEObject(true, (EObject) getData());
        }
    }

    /**
     * Represents an item in the hyperlink tree.
     * 
     * 
     * @author aallway
     * @since 22 Dec 2010
     */
    private abstract class FormHyperLinkItem {
        private Object data;

        private String label;

        private Image image;

        /**
         * @param data
         * @param label
         * @param image
         */
        public FormHyperLinkItem(Object data, String label, Image image) {
            super();
            this.data = data;
            this.label = label;
            this.image = image;
        }

        /**
         * @param label
         * @param image
         */
        public FormHyperLinkItem(String label, Image image) {
            super();
            this.label = label;
            this.image = image;
        }

        /**
         * Perform the action associated with this hyperlink item.
         */
        public abstract void performAction();

        /**
         * @return the data
         */
        public Object getData() {
            return data;
        }

        /**
         * @return the label
         */
        public String getLabel() {
            return label;
        }

        /**
         * @return the image
         */
        public Image getImage() {
            return image;
        }

        /**
         * @see java.lang.Object#toString()
         * 
         * @return
         */
        @Override
        public String toString() {
            return getLabel();
        }

    }

    /**
     * Label provider for hyperlinks
     * 
     * @author aallway
     * @since 22 Dec 2010
     */
    private static class HyperLinkLabelProvider extends LabelProvider {
        @Override
        public Image getImage(Object element) {
            if (element instanceof FormHyperLinkItem) {
                return ((FormHyperLinkItem) element).getImage();
            }
            return null;
        }

        @Override
        public String getText(Object element) {
            if (element instanceof FormHyperLinkItem) {
                return ((FormHyperLinkItem) element).getLabel();
            } else if (element != null) {
                return element.toString();
            }

            return ""; //$NON-NLS-1$
        }
    }

    /**
     * Hyperlink listener that responds to {@link FormHyperLinkItem} activation
     * by executing it's {@link FormHyperLinkItem#performAction()} method
     * 
     * 
     * @author aallway
     * @since 22 Dec 2010
     */
    private static class FormHyperLinkListener extends HyperlinkAdapter {
        /**
         * @see org.eclipse.ui.forms.events.HyperlinkAdapter#linkActivated(org.eclipse.ui.forms.events.HyperlinkEvent)
         * 
         * @param e
         */
        @Override
        public void linkActivated(HyperlinkEvent e) {
            if (e.getHref() instanceof FormHyperLinkItem) {
                ((FormHyperLinkItem) e.getHref()).performAction();
            }
            return;
        }
    }

    /**
     * Hyperlink selection listener that extends the
     * {@link FormHyperLinkListener} and updates the selection provider with the
     * hyperlink clicked if item is {@link OpenEObjectEditorHyperLinkItem},
     * otherwise behaves like the superclass.
     * 
     * @author njpatel
     */
    private class FormHyperLinkSelectionListener extends FormHyperLinkListener {

        private final ISelectionProvider selectionProvider;

        public FormHyperLinkSelectionListener(
                ISelectionProvider selectionProvider) {
            this.selectionProvider = selectionProvider;
        }

        /**
         * @see org.eclipse.ui.forms.events.HyperlinkAdapter#linkActivated(org.eclipse.ui.forms.events.HyperlinkEvent)
         * 
         * @param e
         */
        @Override
        public void linkActivated(HyperlinkEvent e) {
            if (selectionProvider != null) {
                if (e.getHref() instanceof FormHyperLinkItem) {
                    Object data = ((FormHyperLinkItem) e.getHref()).getData();
                    if (data != null) {
                        /*
                         * If this is an open action then don't run its action
                         * but update the selection provider with the selection.
                         * This will ensure the property sections for this item
                         * are displayed.
                         */
                        if (e.getHref() instanceof OpenEObjectEditorHyperLinkItem) {
                            selectionChanged(data);
                            return;
                        }
                    }
                }
            }

            super.linkActivated(e);
        }

    }

    /**
     * Content provider for hyperlink lists. Allows sub-class to provide list of
     * EObjects that will be wrapped in {@link OpenEObjectEditorHyperLinkItem}'s
     * and the create new element of eobject kind hyperlink.
     * 
     * @author aallway
     * @since 22 Dec 2010
     */
    private abstract class PackageContentHyperlinkContentProvider implements
            IStructuredContentProvider {

        /**
         * @param pkg
         * @return The elements in the list.
         */
        protected abstract EList<? extends EObject> getEObjectElements(
                Package pkg);

        /**
         * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
         * 
         * @param inputElement
         * @return
         */
        @Override
        public Object[] getElements(Object inputElement) {
            if (inputElement instanceof Package) {
                Package pkg = (Package) inputElement;

                List<FormHyperLinkItem> objects =
                        new ArrayList<FormHyperLinkItem>();

                /* Add and sort all the objects. */
                EList<? extends EObject> contentElements =
                        getEObjectElements(pkg);
                if (contentElements != null) {
                    for (EObject element : contentElements) {
                        objects.add(createOpenObjectHyperLinkItem(element));
                    }

                    /*
                     * Sid SNA-1 Noticed that the package editor did not sort
                     * according to the same rules as project explorer ( eadline
                     * decimal place numerics were not sorted in expected order.
                     */
                    final Comparator comparator = Policy.getComparator();

                    Collections.sort(objects,
                            new Comparator<FormHyperLinkItem>() {
                                @Override
                                public int compare(FormHyperLinkItem o1,
                                        FormHyperLinkItem o2) {
                                    return comparator.compare(o1.getLabel(),
                                            o2.getLabel());
                                }
                            });

                }

                /*
                 * Insert the 'create new' hyper link.
                 * 
                 * XPD-1140: Disallow create for read only working copy (i.e.
                 * from repository copy etc)
                 */
                List<Object> finalList = new ArrayList<Object>();

                WorkingCopy workingCopy =
                        WorkingCopyUtil.getWorkingCopyFor(pkg);
                if (workingCopy != null && !workingCopy.isReadOnly()) {
                    List<FormHyperLinkItem> createElementHyperLinks =
                            getCreateElementHyperLinks(pkg);
                    if (createElementHyperLinks != null
                            && !createElementHyperLinks.isEmpty()) {
                        finalList.addAll(createElementHyperLinks);
                        finalList.add(HyperLinkListViewer.SEPARATOR);
                    }
                }

                finalList.addAll(objects);

                return finalList.toArray();
            }
            return new Object[0];
        }

        /**
         * @param pkg
         * @param objects
         */
        protected abstract List<FormHyperLinkItem> getCreateElementHyperLinks(
                Package pkg);

        /**
         * @param element
         * @return
         */
        protected FormHyperLinkItem createOpenObjectHyperLinkItem(
                EObject element) {
            return new OpenEObjectEditorHyperLinkItem(element);
        }

        @Override
        public void dispose() {
        }

        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }

    }

    private class WorkingCopyListener implements PropertyChangeListener {

        /**
         * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
         * 
         * @param evt
         */
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (WorkingCopy.CHANGED.equals(evt.getPropertyName())) {
                if (evt instanceof NotificationPropertyChangeEvent) {
                    // Only update sections that have been affected
                    updateViewer(((NotificationPropertyChangeEvent) evt)
                            .getNotifications());
                } else {
                    // Update all sections
                    procIfcLinksViewer.refresh();
                    procLinksViewer.refresh();
                    participantLinksViewer.refresh();
                    typeDeclarationLinksViewer.refresh();
                    dataFieldLinksViewer.refresh();
                }
            }

        }

        /**
         * @param notifications
         */
        private void updateViewer(Collection<Notification> notifications) {
            boolean isIfcChange = false, isProcessChange = false, isParticipantChange =
                    false, isTypeChange = false, isDataChange = false;

            for (Notification notification : notifications) {
                Object notifier = notification.getNotifier();
                if (notifier instanceof Process) {
                    isProcessChange = true;
                } else if (notifier instanceof ProcessInterface) {
                    isIfcChange = true;
                } else if (notifier instanceof Participant) {
                    isParticipantChange = true;
                } else if (notifier instanceof TypeDeclaration) {
                    isTypeChange = true;
                } else if (notifier instanceof DataField) {
                    /*
                     * Only interested in package level data field
                     */
                    if (((DataField) notifier).eContainer() instanceof Package) {
                        isDataChange = true;
                    }
                }
            }

            if (isIfcChange) {
                procIfcLinksViewer.refresh();
            }
            if (isProcessChange) {
                procLinksViewer.refresh();
            }
            if (isParticipantChange) {
                participantLinksViewer.refresh();
            }
            if (isDataChange) {
                dataFieldLinksViewer.refresh();
            }
            if (isTypeChange) {
                typeDeclarationLinksViewer.refresh();
            }
        }
    }

    private class PackageMouseListener extends MouseAdapter {
        /**
         * @see org.eclipse.swt.events.MouseAdapter#mouseUp(org.eclipse.swt.events.MouseEvent)
         * 
         * @param e
         */
        @Override
        public void mouseUp(MouseEvent e) {
            // Set the package as the selection so that the package property
            // sections are displayed
            if (selectionProvider != null) {
                Package xpdlPackage = getXpdlPackage();
                if (xpdlPackage != null) {
                    selectionChanged(xpdlPackage);
                }
            }
        }
    }
}