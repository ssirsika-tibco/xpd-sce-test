/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityWebServiceReference;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.wizards.AddBindingWizard;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.ui.components.BaseTreeControl;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.ViewerAddAction;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.WsInbound;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Participant;

/**
 * The configuration component of web service resource type (acting as a service
 * provider). </br> TODO Refactor to section.
 * 
 * @author Jan Arciuchiewicz
 */
public class WsInboundComposite extends Composite {

    /* id of the preference page from plugin xml */
    private static final String PREFERENCE_PAGE_ID =
            "com.tibco.xpd.bpm.BpmServiceBinding"; //$NON-NLS-1$

    enum InBindingType {
        VIRTUAL(Messages.WsInboundComposite_VirtualizationEnum_name,
                XpdExtensionPackage.eINSTANCE.getWsVirtualBinding()), SOAP_OVER_HTTP(
                Messages.WsInboundComposite_SoapOverHttpEnum_name,
                XpdExtensionPackage.eINSTANCE.getWsSoapHttpInboundBinding()), SOAP_OVER_JMS(
                Messages.WsInboundComposite_SoapOverJMSEnum_name,
                XpdExtensionPackage.eINSTANCE.getWsSoapJmsInboundBinding());

        private String label;

        private EClass eClass;

        private InBindingType(String label, EClass eClass) {
            this.label = label;
            this.eClass = eClass;
        }

        public static InBindingType getByEClass(EClass eClass) {
            for (InBindingType bt : InBindingType.values()) {
                if (bt.eClass == eClass) {
                    return bt;
                }
            }
            return null;
        }
    };

    private WsInBindingsControl bindingControl;

    private final TabbedPropertySheetPage sheetPage;

    private WsBindingSection virtualSection;

    private WsBindingSection soapHttpSection;

    private WsBindingSection soapJmsSection;

    private ScrolledPageBook pageBook;

    private static final String BINDINGCONFIGDETAILS_HYPERLINK_HREF =
            "ServiceBindingConfigDetails.HyperLink"; //$NON-NLS-1$

    private static final String BINDINGCONFIGDETAILS_HYPERLINK_FORMATSTR =
            "<text><p>%s: (<a href='%s'>%s</a>)</p></text>"; //$NON-NLS-1$

    private FormText formTxtLinkProvParticBindingConfigDefaults;

    private final Map<InBindingType, WsBindingSection> bindingTypeToSection =
            new HashMap<InBindingType, WsBindingSection>();

    public WsInboundComposite(Composite parent, XpdFormToolkit toolkit,
            TabbedPropertySheetPage sheetPage) {
        super(parent, SWT.NONE);
        this.sheetPage = sheetPage;
        createControls(parent, toolkit);
    }

    protected void createControls(Composite parent, XpdFormToolkit toolkit) {

        GridLayoutFactory.fillDefaults().applyTo(this);

        formTxtLinkProvParticBindingConfigDefaults =
                toolkit.createFormText(this, true);
        formTxtLinkProvParticBindingConfigDefaults
                .setText(String
                        .format(BINDINGCONFIGDETAILS_HYPERLINK_FORMATSTR,
                                Messages.WsInboundComposite_BindingList_label_1,
                                BINDINGCONFIGDETAILS_HYPERLINK_HREF,
                                Messages.WsInboundComposite_Provider_ServiceBindingConfigDetails_link),
                        true,
                        false);

        formTxtLinkProvParticBindingConfigDefaults
                .addHyperlinkListener(new IHyperlinkListener() {

                    @Override
                    public void linkExited(HyperlinkEvent e) {
                        // do nothing
                    }

                    @Override
                    public void linkEntered(HyperlinkEvent e) {
                        // do nothing
                    }

                    @Override
                    public void linkActivated(HyperlinkEvent e) {
                        PreferenceDialog preferenceDialog =
                                PreferencesUtil
                                        .createPreferenceDialogOn(getShell(),
                                                PREFERENCE_PAGE_ID,
                                                null,
                                                null);
                        if (null != preferenceDialog) {
                            preferenceDialog.open();
                        }
                    }
                });

        GridDataFactory.swtDefaults()
                .applyTo(formTxtLinkProvParticBindingConfigDefaults);

        final SashForm sashForm = new SashForm(this, SWT.HORIZONTAL);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(sashForm);

        final Composite lhsComposite = toolkit.createComposite(sashForm);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(lhsComposite);
        GridLayoutFactory.fillDefaults().numColumns(1).applyTo(lhsComposite);

        bindingControl = new WsInBindingsControl(lhsComposite, toolkit);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(bindingControl);

        // RHS
        final Composite rhsComposite = toolkit.createComposite(sashForm);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(rhsComposite);

        GridLayoutFactory.fillDefaults().applyTo(rhsComposite);

        pageBook = toolkit.createPageBook(rhsComposite, SWT.NONE);
        GridDataFactory.fillDefaults().indent(20, 0).grab(true, true)
                .applyTo(pageBook);

        createBindingTypeBookPages(pageBook, toolkit);
        bindingControl.getViewer()
                .addSelectionChangedListener(new ISelectionChangedListener() {
                    @Override
                    public void selectionChanged(SelectionChangedEvent event) {
                        if (event.getSelection() instanceof IStructuredSelection) {
                            IStructuredSelection s =
                                    (IStructuredSelection) event.getSelection();
                            Object first = s.getFirstElement();
                            if (first instanceof EObject) {
                                InBindingType type =
                                        InBindingType
                                                .getByEClass(((EObject) first)
                                                        .eClass());
                                if (type != null) {
                                    WsBindingSection bindingSection =
                                            getSectionForBindingType(type);
                                    if (bindingSection != null) {
                                        bindingSection.setInput(Collections
                                                .singleton(first));
                                        bindingSection.refresh();
                                        pageBook.showPage(type);
                                    }
                                    return;
                                }
                            }
                        }
                        pageBook.showEmptyPage();
                    }
                });
        sashForm.setWeights(new int[] { 40, 60 });

    }

    private Point createBindingTypeBookPages(ScrolledPageBook book,
            XpdToolkit toolkit) {
        Composite page = null;

        Point minSize = new Point(0, 0);

        for (InBindingType bindingType : InBindingType.values()) {
            page = toolkit.createComposite(book.getContainer());
            GridDataFactory.fillDefaults().grab(true, true).applyTo(page);
            // Create pages for each type
            switch (bindingType) {
            case VIRTUAL:
                createVirtualPage(page, toolkit);
                break;
            case SOAP_OVER_HTTP:
                createSoapHttpPage(page, toolkit);
                break;
            case SOAP_OVER_JMS:
                createSoapJmsPage(page, toolkit);
                break;
            default:
                Assert.isTrue(false, "Unknown binding type."); //$NON-NLS-1$
            }

            Point min = page.computeSize(SWT.DEFAULT, SWT.DEFAULT);
            if (min.x > minSize.x) {
                minSize.x = min.x;
            }
            if (min.y > minSize.y) {
                minSize.y = min.y;
            }

            book.registerPage(bindingType, page);
        }

        return minSize;
    }

    public void refresh() {
        // XXX: Probably not necessary.
        ColumnViewer viewer = bindingControl.getViewer();
        viewer.refresh();
        ISelection s = viewer.getSelection();
        if (s.isEmpty()) {
            WsInbound wsInbound = getInput();
            if (wsInbound != null) {
                if (!wsInbound.getSoapHttpBinding().isEmpty()) {
                    viewer.setSelection(new StructuredSelection(wsInbound
                            .getSoapHttpBinding().get(0)), true);
                } else if (!wsInbound.getSoapJmsBinding().isEmpty()) {
                    viewer.setSelection(new StructuredSelection(wsInbound
                            .getSoapJmsBinding().get(0)), true);
                } else if (wsInbound.getVirtualBinding() != null) {
                    viewer.setSelection(new StructuredSelection(wsInbound
                            .getVirtualBinding()),
                            true);
                }
            }
        }

        // virtualSection.refresh();
    }

    /**
     * @param page
     * @param toolkit
     */
    private void createVirtualPage(Composite page, XpdToolkit toolkit) {
        virtualSection =
                new WsVirtualBindingSection(
                        XpdExtensionPackage.eINSTANCE.getWsBinding());
        virtualSection.createControls(page, sheetPage);
        registerSectionForBindingType(InBindingType.VIRTUAL, virtualSection);
    }

    /**
     * @param page
     * @param toolkit
     */
    private void createSoapHttpPage(Composite page, XpdToolkit toolkit) {
        soapHttpSection =
                new WsSoapHttpInboundBindingSection(
                        XpdExtensionPackage.eINSTANCE.getWsBinding());
        soapHttpSection.createControls(page, sheetPage);
        registerSectionForBindingType(InBindingType.SOAP_OVER_HTTP,
                soapHttpSection);
    }

    /**
     * @param page
     * @param toolkit
     */
    private void createSoapJmsPage(Composite page, XpdToolkit toolkit) {
        soapJmsSection =
                new WsSoapJmsInboundBindingSection(
                        XpdExtensionPackage.eINSTANCE.getWsBinding());
        soapJmsSection.createControls(page, sheetPage);
        registerSectionForBindingType(InBindingType.SOAP_OVER_JMS,
                soapJmsSection);
    }

    public void setInput(WsInbound wsInbound) {
        bindingControl.setInput(wsInbound);
    }

    public WsInbound getInput() {
        return bindingControl.getInput();
    }

    private WsBindingSection getSectionForBindingType(InBindingType bindingType) {
        return bindingTypeToSection.get(bindingType);
    }

    private void registerSectionForBindingType(InBindingType bindingType,
            WsBindingSection section) {
        bindingTypeToSection.put(bindingType, section);
    }

    private static class WsInBindingsControl extends BaseTreeControl {
        public WsInBindingsControl(Composite parent, XpdToolkit toolkit) {
            super(parent, toolkit);
        }

        public void setInput(WsInbound wsInbound) {
            getViewer().setInput(wsInbound);
        }

        public WsInbound getInput() {
            return (WsInbound) getViewer().getInput();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected Set<EStructuralFeature> getMovableFeatures() {
            Set<EStructuralFeature> features = super.getMovableFeatures();
            XpdExtensionPackage p = XpdExtensionPackage.eINSTANCE;
            features.add(p.getWsInbound_VirtualBinding());
            features.add(p.getWsInbound_SoapHttpBinding());
            features.add(p.getWsInbound_SoapJmsBinding());
            return features;
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createAddAction(org.eclipse.jface.viewers.ColumnViewer)
         * 
         * @param viewer
         * @return
         */
        @Override
        protected ViewerAddAction createAddAction(ColumnViewer viewer) {
            return new ViewerAddAction(viewer) {
                /**
                 * @see org.eclipse.jface.action.Action#run()
                 * 
                 */
                @Override
                public void run() {

                    /*
                     * XPD-3627 : asking the user to save the process if the
                     * wsdl is not available. (WSDL will not be available in
                     * case of generated services if xpdl is not saved or clean
                     * build is not done)
                     */
                    Participant participant = getParticipant(getInput());
                    IFile wsdlFile = getWsdlFile(participant);

                    if (null == wsdlFile) {
                        WorkingCopy workingCopy =
                                WorkingCopyUtil.getWorkingCopyFor(participant);
                        /*
                         * this is generally for the case when wsdl is not
                         * generated
                         */
                        if (workingCopy.isWorkingCopyDirty()) {
                            MessageDialog dialog =
                                    new MessageDialog(
                                            Display.getDefault()
                                                    .getActiveShell(),
                                            Messages.AddBindingWizard_saveProcessTitle,
                                            null,
                                            Messages.AddBindingWizard_saveProcess_message,
                                            3,
                                            new String[] {
                                                    Messages.AddBindingWizard_saveProcess_button,
                                                    IDialogConstants.NO_LABEL },
                                            0);
                            if (dialog.open() == 0) {
                                if (null != workingCopy) {
                                    try {
                                        workingCopy.save();
                                        /*
                                         * get the wsdl file once the working
                                         * copy is saved
                                         */
                                        wsdlFile = getWsdlFile(participant);
                                    } catch (IOException e) {
                                    }
                                }
                            }
                        } else {
                            /* this is for the case when wsdl is deleted */
                            MessageDialog
                                    .openInformation(Display.getDefault()
                                            .getActiveShell(),
                                            Messages.AddBindingWizard_noWsdlAvailableTitle,
                                            Messages.AddBindingWizard_noWsdlAvailable_message);
                        }
                    }
                    /*
                     * XPD-3627 : do not show the wizard if the wsdl is not
                     * available
                     */
                    if (null != wsdlFile) {
                        AddBindingWizard addBindingWizard =
                                new AddBindingWizard(getInput(), participant,
                                        wsdlFile);
                        addBindingWizard
                                .setWindowTitle(Messages.AddBindingWizard_addBinding_Title);
                        WizardDialog wizardDialog =
                                new WizardDialog(PlatformUI.getWorkbench()
                                        .getActiveWorkbenchWindow().getShell(),
                                        addBindingWizard);
                        wizardDialog.open();
                    }

                }

            };

        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected Set<EStructuralFeature> getDeletableFeatures() {
            Set<EStructuralFeature> features = super.getDeletableFeatures();
            XpdExtensionPackage p = XpdExtensionPackage.eINSTANCE;
            features.add(p.getWsInbound_SoapHttpBinding());
            features.add(p.getWsInbound_SoapJmsBinding());
            return features;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void createActions(ColumnViewer viewer) {
            /*
             * we want to support the add delete actions
             */
            super.createActions(viewer);
        }

        /**
         * 
         * @param wsInbound
         * @return
         */
        private Participant getParticipant(WsInbound wsInbound) {
            Participant participant = null;
            if (null != wsInbound.eContainer()
                    && null != wsInbound.eContainer().eContainer()) {
                EObject eContainer =
                        wsInbound.eContainer().eContainer().eContainer();
                if (eContainer instanceof Participant) {
                    participant = (Participant) eContainer;
                }
            }
            return participant;
        }

        /**
         * 
         * @param participant
         * @return
         */
        private IFile getWsdlFile(Participant participant) {
            IFile wsdlFile = null;

            if (null != participant) {
                List<ActivityWebServiceReference> webSvcRefs =
                        getIndexedWebServiceReferences(participant);

                for (ActivityWebServiceReference ref : webSvcRefs) {

                    if (ref.getParticipantId().equals(participant.getId())) {
                        String wsdlFileLocation = ref.getWsdlFileLocation();

                        if (null != wsdlFileLocation) {
                            IProject project =
                                    WorkingCopyUtil.getProjectFor(participant);

                            wsdlFile =
                                    SpecialFolderUtil
                                            .resolveSpecialFolderRelativePath(project,
                                                    "wsdl", wsdlFileLocation, true); //$NON-NLS-1$
                            if (null != wsdlFile) {
                                break;
                            }

                        }
                    }
                }
            }

            return wsdlFile;
        }

        /**
         * 
         * @param participant
         * @return
         */
        private List<ActivityWebServiceReference> getIndexedWebServiceReferences(
                Participant participant) {

            List<ActivityWebServiceReference> webSvcRefs =
                    new ArrayList<ActivityWebServiceReference>();

            Map<String, String> additionalAttributes =
                    new HashMap<String, String>();

            additionalAttributes
                    .put(ProcessUIUtil.WEBSERVICE_REF_COLUMN_ENDPOINT_PARTICIPANT_ID,
                            participant.getId());

            IndexerItem criteria =
                    new IndexerItemImpl(null,
                            ProcessUIUtil.ACTIVITY_WEBSERVICE_REF_INDEX_TYPE,
                            null, additionalAttributes);

            Collection<IndexerItem> result =
                    XpdResourcesPlugin
                            .getDefault()
                            .getIndexerService()
                            .query(ProcessUIUtil.ACTIVITY_WEBSERVICE_REF_INDEX_ID,
                                    criteria);

            if (result != null && !result.isEmpty()) {
                for (IndexerItem item : result) {
                    ActivityWebServiceReference ref =
                            ActivityWebServiceReference
                                    .createActivityWebServiceReference(item);
                    if (ref != null) {
                        webSvcRefs.add(ref);
                    }
                }
            }

            return webSvcRefs;
        }

    }

}
