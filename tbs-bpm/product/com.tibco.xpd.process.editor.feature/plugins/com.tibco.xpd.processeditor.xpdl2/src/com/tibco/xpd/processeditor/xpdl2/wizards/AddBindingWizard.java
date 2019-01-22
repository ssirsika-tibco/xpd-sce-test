/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.wizards;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.wsdl.Message;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Part;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityWebServiceReference;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.preferences.BpmBindingPreferenceUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.SoapBindingStyle;
import com.tibco.xpd.xpdExtension.WsInbound;
import com.tibco.xpd.xpdExtension.WsResource;
import com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding;
import com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Wizard for adding new binding types
 * 
 * @author bharge
 * @since 22 Mar 2012
 */
public class AddBindingWizard extends Wizard {

    protected String selection;

    protected WsInbound wsInBound;

    protected Participant participant;

    protected IFile wsdlFile;

    private static final String SLASH = "/"; //$NON-NLS-1$

    public enum AddBindingWizardBindingType {

        SOAP_OVER_HTTP(Messages.WsInboundComposite_SoapOverHttpEnum_name), SOAP_OVER_JMS(
                Messages.WsInboundComposite_SoapOverJMSEnum_name);

        private final String bindingWizardBindingType;

        private AddBindingWizardBindingType(String bindingWizardBindingType) {
            this.bindingWizardBindingType = bindingWizardBindingType;
        }

        public String getValue() {
            return bindingWizardBindingType;
        }
    }

    /**
     * @param wsInBound
     * @param participant
     * @param wsdlFile
     */
    public AddBindingWizard(WsInbound wsInBound, Participant participant,
            IFile wsdlFile) {
        this.wsInBound = wsInBound;
        this.participant = participant;
        this.wsdlFile = wsdlFile;
    }

    /**
     * 
     * @param participant
     * @return
     */
    private String getPortOrPortTypeName(Participant participant) {
        String portOrPortTypeName = null;

        if (null != participant) {
            List<ActivityWebServiceReference> webSvcRefs =
                    getIndexedWebServiceReferences(participant);

            for (ActivityWebServiceReference ref : webSvcRefs) {

                if (ref.getParticipantId().equals(participant.getId())) {
                    if (null != ref.getPortName()
                            && ref.getPortName().trim().length() > 0) {
                        portOrPortTypeName = ref.getPortName();
                    } else {
                        portOrPortTypeName = ref.getPortTypeName();
                    }
                    if (null != portOrPortTypeName) {
                        break;
                    }
                }
            }
        }

        return portOrPortTypeName;
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
                        ProcessUIUtil.ACTIVITY_WEBSERVICE_REF_INDEX_TYPE, null,
                        additionalAttributes);

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

    /**
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     * 
     * @return
     */
    @Override
    public boolean performFinish() {

        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();

        ed.getCommandStack().execute(new RecordingCommand(ed) {
            @Override
            protected void doExecute() {
                /*
                 * get participant shared resource from participant. get ws
                 * resource from participant shared resource. add wsinbound to
                 * ws resource.
                 */

                ParticipantSharedResource sharedResource =
                        (ParticipantSharedResource) Xpdl2ModelUtil
                                .getOtherElement(participant,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_ParticipantSharedResource());

                if (AddBindingWizardBindingType.SOAP_OVER_HTTP.getValue()
                        .equals(selection)) {

                    String portTypeName = getPortOrPortTypeName(participant);

                    String sharedResUriPath =
                            getSharedResUriPath(wsdlFile, portTypeName);

                    createSOAPHTTPProviderSharedRes(participant,
                            sharedResUriPath,
                            wsInBound);

                    if (null != sharedResource) {
                        WsResource wsResource = sharedResource.getWebService();
                        wsResource.setInbound(wsInBound);
                    }

                } else if (AddBindingWizardBindingType.SOAP_OVER_JMS.getValue()
                        .equals(selection)) {
                    createSOAPJMSProviderSharedRes(participant, wsInBound);

                    if (null != sharedResource) {
                        WsResource wsResource = sharedResource.getWebService();
                        wsResource.setInbound(wsInBound);
                    }
                }
            }

        });

        return true;
    }

    /**
     * 
     * @param participant
     * @param wsInBound
     */
    private void createSOAPJMSProviderSharedRes(Participant participant,
            WsInbound wsInBound) {

        Definition definition = getDefinition(wsdlFile);

        SoapBindingStyle bindingStyle = null;
        if (definition != null) {
            bindingStyle = getBindingStyle(definition);
        }

        WsSoapJmsInboundBinding wsSoapJmsInboundBindingDefault =
                XpdExtensionFactory.eINSTANCE
                        .createWsSoapJmsInboundBindingDefault();
        String preferedSoapVersion =
                BpmBindingPreferenceUtil.getSoapVersionPreference().getName();
        wsSoapJmsInboundBindingDefault.setSoapVersion(preferedSoapVersion);

        String baseName = wsSoapJmsInboundBindingDefault.getName();
        String finalName = baseName;

        int idx = 0;

        if (null != wsInBound.getSoapJmsBinding()) {

            EList<WsSoapJmsInboundBinding> soapJmsBinding =
                    wsInBound.getSoapJmsBinding();

            for (WsSoapJmsInboundBinding wsSoapJmsInboundBinding : soapJmsBinding) {
                if (finalName.equals(wsSoapJmsInboundBinding.getName())) {
                    idx++;
                    finalName = baseName + idx;
                }
            }
        }

        wsSoapJmsInboundBindingDefault.setName(finalName);

        wsSoapJmsInboundBindingDefault
                .setInboundConnectionFactoryConfiguration(wsSoapJmsInboundBindingDefault
                        .getInboundConnectionFactoryConfiguration());
        wsSoapJmsInboundBindingDefault
                .setInboundDestination(wsSoapJmsInboundBindingDefault
                        .getInboundDestination());
        wsSoapJmsInboundBindingDefault
                .setOutboundConnectionFactory(wsSoapJmsInboundBindingDefault
                        .getOutboundConnectionFactory());

        wsInBound.getSoapJmsBinding().add(wsSoapJmsInboundBindingDefault);

        /*
         * TODO: if binding style is null default it to rpc literal now. but
         * will have to change to document literal when we decide document
         * literal has to be the default binding style
         */
        if (null != bindingStyle
                && SoapBindingStyle.DOCUMENT_LITERAL.equals(bindingStyle)) {
            wsSoapJmsInboundBindingDefault
                    .setBindingStyle(SoapBindingStyle.DOCUMENT_LITERAL);
        } else {
            wsSoapJmsInboundBindingDefault
                    .setBindingStyle(SoapBindingStyle.RPC_LITERAL);
        }
    }

    /**
     * 
     * @param wsdlFile
     * @return
     */
    private Definition getDefinition(IFile wsdlFile) {
        Definition definition = null;
        if (wsdlFile != null) {
            WorkingCopy wc =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(wsdlFile);
            if (wc != null) {
                EObject eo = wc.getRootElement();
                if (eo instanceof Definition) {
                    definition = (Definition) eo;
                }
            }
        }
        return definition;
    }

    /**
     * 
     * @param definition
     * @return
     */
    public SoapBindingStyle getBindingStyle(Definition definition) {

        SoapBindingStyle bindingStyle = null;

        Map<?, ?> messages = definition.getMessages();

        for (Object next : messages.values()) {
            if (next instanceof Message) {
                Map<?, ?> parts = ((Message) next).getParts();

                for (Object nextPart : parts.values()) {
                    if (nextPart instanceof Part) {
                        Part part = (Part) nextPart;
                        if (part.getElementDeclaration() != null) {
                            // This is document style
                            if (bindingStyle == null) {
                                bindingStyle =
                                        SoapBindingStyle.DOCUMENT_LITERAL;
                            } else if (bindingStyle != SoapBindingStyle.DOCUMENT_LITERAL) {
                                // Cannot determine as mixture of styles
                                // used in the message part
                                return null;
                            }
                        } else if (part.getTypeDefinition() != null) {
                            // This is part style
                            if (bindingStyle == null) {
                                bindingStyle = SoapBindingStyle.RPC_LITERAL;
                            } else if (bindingStyle != SoapBindingStyle.RPC_LITERAL) {
                                // Cannot determine as mixture of styles
                                // used in the message part
                                return null;
                            }
                        } else {
                            // Part has no type or element definition so
                            // cannot determine binding style
                            return null;
                        }
                    }
                }
            }
        }

        return bindingStyle;
    }

    /**
     * 
     * @param participant
     * @param sharedResUriPath
     * @param wsInBound
     */
    private void createSOAPHTTPProviderSharedRes(Participant participant,
            String sharedResUriPath, WsInbound wsInBound) {

        Definition definition = getDefinition(wsdlFile);

        SoapBindingStyle bindingStyle = null;
        if (definition != null) {
            bindingStyle = getBindingStyle(definition);
        }

        WsSoapHttpInboundBinding wsSoapHttpInboundBindingDefault =
                XpdExtensionFactory.eINSTANCE
                        .createWsSoapHttpInboundBindingDefault();
        String preferedSoapVersion =
                BpmBindingPreferenceUtil.getSoapVersionPreference().getName();
        wsSoapHttpInboundBindingDefault.setSoapVersion(preferedSoapVersion);
        String baseName = wsSoapHttpInboundBindingDefault.getName();
        String finalName = baseName;

        int idx = 0;

        if (null != wsInBound.getSoapHttpBinding()) {

            EList<WsSoapHttpInboundBinding> soapHttpBinding =
                    wsInBound.getSoapHttpBinding();

            for (WsSoapHttpInboundBinding wsSoapHttpInboundBinding : soapHttpBinding) {
                if (finalName.equals(wsSoapHttpInboundBinding.getName())) {
                    idx++;
                    finalName = baseName + idx;
                }
            }
        }

        wsSoapHttpInboundBindingDefault.setName(finalName);
        wsSoapHttpInboundBindingDefault.setEndpointUrlPath(sharedResUriPath);

        wsInBound.getSoapHttpBinding().add(wsSoapHttpInboundBindingDefault);
        /*
         * TODO: if binding style is null default it to rpc literal now. but
         * will have to change to document literal when we decide document
         * literal has to be the default binding style
         */
        if (null != bindingStyle
                && SoapBindingStyle.DOCUMENT_LITERAL.equals(bindingStyle)) {
            wsSoapHttpInboundBindingDefault
                    .setBindingStyle(SoapBindingStyle.DOCUMENT_LITERAL);
        } else {
            wsSoapHttpInboundBindingDefault
                    .setBindingStyle(SoapBindingStyle.RPC_LITERAL);

        }
    }

    /**
     * 
     * @param wsdlFile
     * @param sharedResUriPath
     * @return
     */

    private static String getSharedResUriPath(IFile wsdlFile,
            String sharedResUriPath) {

        String wsdlNameWithoutExtn =
                getWsdlNameWithoutSpacesWithoutExtn(wsdlFile);

        Path path = new Path(SLASH);
        Path wsdlNamePath = (Path) path.append(wsdlNameWithoutExtn);

        Path sharedResPath = new Path(sharedResUriPath);
        if (!sharedResPath.isPrefixOf(path)) {
            sharedResPath = (Path) path.append(sharedResUriPath);
        }

        /*
         * XPD-1766: composite requires the URI to be in format /A/B i.e. /<wsdl
         * file name>/<port type name> or <port name>. for both abstract and
         * concrete operations (XPD-2915) the endpoint url must have the
         * required format
         */
        sharedResUriPath =
                wsdlNamePath.toPortableString()
                        + sharedResPath.toPortableString();
        return sharedResUriPath;
    }

    /**
     * 
     * @param wsdlFile
     * @return
     */
    private static String getWsdlNameWithoutSpacesWithoutExtn(IFile wsdlFile) {
        Path path = new Path(wsdlFile.getName());
        String wsdlName = path.removeFileExtension().toPortableString();
        /*
         * encode any special characters appropriately. eg. replace spaces with
         * %20
         */
        org.eclipse.emf.common.util.URI wsdlURI =
                org.eclipse.emf.common.util.URI.createURI(wsdlName, true);
        return wsdlURI.toString();
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     * 
     */
    @Override
    public void addPages() {
        addPage(new SelectBindingPage("")); //$NON-NLS-1$
    }

    /**
     * 
     * 
     * @author bharge
     * @since 22 Mar 2012
     */
    public class SelectBindingPage extends AbstractXpdWizardPage {

        private TableViewer viewer;

        /**
         * @param string
         */
        public SelectBindingPage(String string) {
            super(string);
            setTitle(Messages.AddBindingWizard_binding_type_title);
            setImageDescriptor(Xpdl2ProcessEditorPlugin
                    .getImageDescriptor(ProcessEditorConstants.ICON_BINDINGTYPE_WIZARD));
            setMessage(Messages.AddBindingWizard_select_binding_type_message);
            setPageComplete(false);
        }

        /**
         * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
         * 
         * @param parent
         */
        @Override
        public void createControl(Composite parent) {
            Composite root = new Composite(parent, 0);
            root.setLayout(new GridLayout(1, false));

            Label label = new Label(root, 0);
            label.setText(Messages.AddBindingWizard_listOfBindings);

            this.viewer = new TableViewer(root);
            this.viewer.addDoubleClickListener(new IDoubleClickListener() {

                @Override
                public void doubleClick(DoubleClickEvent event) {
                    AddBindingWizard.SelectBindingPage.this
                            .closeOnDoubleClick();
                }
            });
            this.viewer
                    .addSelectionChangedListener(new ISelectionChangedListener() {
                        @Override
                        public void selectionChanged(SelectionChangedEvent event) {

                            AddBindingWizard.SelectBindingPage.this
                                    .setPageComplete(!(event.getSelection()
                                            .isEmpty()));

                            Object element =
                                    ((IStructuredSelection) event
                                            .getSelection()).getFirstElement();

                            if (element instanceof AddBindingWizardBindingType) {
                                AddBindingWizardBindingType bindingType =
                                        (AddBindingWizardBindingType) element;
                                AddBindingWizard.this.selection =
                                        bindingType.getValue();

                            }

                        }
                    });

            this.viewer.setContentProvider(new ArrayContentProvider());
            this.viewer.setLabelProvider(new BindingTypeLabelProvier());

            this.viewer.setInput(AddBindingWizardBindingType.values());

            GridData gd = new GridData(1808);
            gd.widthHint = 20;
            gd.heightHint = 20;
            this.viewer.getControl().setLayoutData(gd);

            setControl(root);
        }

        protected void closeOnDoubleClick() {
            ISelection currentSelection = this.viewer.getSelection();

            if (!currentSelection.isEmpty()) {
                setPageComplete(true);
                Object element =
                        ((IStructuredSelection) currentSelection)
                                .getFirstElement();

                if (element instanceof AddBindingWizardBindingType) {
                    AddBindingWizardBindingType bindingType =
                            (AddBindingWizardBindingType) element;
                    AddBindingWizard.this.selection = bindingType.getValue();
                    AddBindingWizard.this.performFinish();

                    if (getContainer() instanceof WizardDialog) {
                        WizardDialog wizardDialog =
                                (WizardDialog) getContainer();
                        wizardDialog.close();
                    }

                }
            }
        }

    }

    /**
     * 
     * 
     * @author bharge
     * @since 23 Mar 2012
     */
    public class BindingTypeLabelProvier extends LabelProvider implements
            IBaseLabelProvider {

        /**
         * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        public String getText(Object element) {

            if (element instanceof AddBindingWizardBindingType) {
                AddBindingWizardBindingType bindingType =
                        (AddBindingWizardBindingType) element;
                if (AddBindingWizardBindingType.SOAP_OVER_HTTP.getValue()
                        .equals(bindingType.getValue())) {
                    return AddBindingWizardBindingType.SOAP_OVER_HTTP
                            .getValue();
                } else if (AddBindingWizardBindingType.SOAP_OVER_JMS.getValue()
                        .equals(bindingType.getValue())) {
                    return AddBindingWizardBindingType.SOAP_OVER_JMS.getValue();
                }
            }

            return ""; //$NON-NLS-1$
        }

        /**
         * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        public Image getImage(Object element) {
            if (element instanceof AddBindingWizardBindingType) {
                return Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                        .get(ProcessEditorConstants.ICON_SOAP_PROVIDER_DEF);
            }
            return null;
        }

    }

}
