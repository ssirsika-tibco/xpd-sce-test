/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.wsdl.ui.concrete;

import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Part;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.util.WsdlIndexerUtil.WsdlElementType;
import com.tibco.xpd.wsdl.Activator;
import com.tibco.xpd.wsdl.ui.internal.Messages;
import com.tibco.xpd.wsdl.ui.pickers.WsdlElementPicker;

/**
 * WSDL selection page - select the abstract WSDL for which this WSDL creates
 * the concrete bits.
 * 
 * @author rsomayaj
 * @since 3.3 (5 Jul 2010)
 */
public class WSDLSelectionPage extends AbstractXpdWizardPage {

    /**
     * Binding style option.
     */
    private enum BindingStyle {

        DOCUMENT("document"), //$NON-NLS-1$
        RPC("rpc"); //$NON-NLS-1$

        private String text;

        private BindingStyle(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    private BindingStyle selectedBindingStyle;

    /**
     * 
     */
    private static final String DEFAULT_SOAP_ADDRESS_LOCATION =
            "http://www.example.org/"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String CONCRETE_SUFFIX = "-Concrete"; //$NON-NLS-1$

    private Button rpcLit;

    private Button docLit;

    // public Text bindingTransportText;

    public Text txtSoapAddress;

    private IFile abstractWSDLFile;

    private Text txtAbstractWSDL;

    private Text txtConcreteWsdlName;

    private Label bindingStyleLabel;

    /**
     * @param pageName
     */
    protected WSDLSelectionPage(String pageName) {
        super(pageName);
        setTitle(Messages.WSDLSelectionPage_NewConcreteWsdl_label);
        setDescription(Messages.WSDLSelectionPage_DialogDesc_shortdesc);
    }

    /**
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     */
    @Override
    public void createControl(Composite parent) {
        Composite root = new Composite(parent, SWT.None);
        GridLayout layout = new GridLayout(2, false);
        layout.verticalSpacing = 10;
        root.setLayout(layout);
        Label lblAbstractWSDL = new Label(root, SWT.None);
        lblAbstractWSDL
                .setText(Messages.WSDLSelectionPage_LblAbstractWsdl_label);
        lblAbstractWSDL.setLayoutData(new GridData());

        Composite abstractWsdlControl = new Composite(root, SWT.NONE);
        abstractWsdlControl.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
                true, false));
        layout = new GridLayout(2, false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        abstractWsdlControl.setLayout(layout);

        txtAbstractWSDL = new Text(abstractWsdlControl, SWT.BORDER);
        txtAbstractWSDL.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        txtAbstractWSDL.setEditable(false);
        txtAbstractWSDL.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                abstractTextModified();
            }

        });

        Button btnSelectAbstractWSDL =
                new Button(abstractWsdlControl, SWT.PUSH);
        btnSelectAbstractWSDL.setLayoutData(new GridData());
        btnSelectAbstractWSDL
                .setText(Messages.WSDLSelectionPage_btnBrowse_label);

        btnSelectAbstractWSDL.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                WsdlElementPicker wsdlElementPicker =
                        new WsdlElementPicker(
                                getShell(),
                                new WsdlIndexerUtil.WsdlElementType[] { WsdlElementType.WSDL },
                                true, true);

                wsdlElementPicker
                        .setTitle(Messages.WSDLSelectionPage_WindowTitle_label);

                wsdlElementPicker.open();
                Object selectedObj = wsdlElementPicker.getFirstResult();
                if (selectedObj instanceof IndexerItem) {
                    String path =
                            ((IndexerItem) selectedObj)
                                    .get(IndexerServiceImpl.ATTRIBUTE_PATH);
                    IFile file =
                            ResourcesPlugin.getWorkspace().getRoot()
                                    .getFile(new Path(path));

                    if (file != null && file.isAccessible()) {
                        abstractWSDLFile = file;
                        txtAbstractWSDL.setText(abstractWSDLFile.getName());

                        // Determine the binding style and set controls
                        // appropriately
                        selectedBindingStyle = getBindingStyle(file);
                        updateBindingStyle(selectedBindingStyle);
                    }
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                // Do nothing
            }
        });

        Label lblConcreteWsdlName = new Label(root, SWT.None);
        lblConcreteWsdlName.setText(Messages.WSDLSelectionPage_lblConcreteWsdl);
        lblConcreteWsdlName.setLayoutData(new GridData());

        txtConcreteWsdlName = new Text(root, SWT.BORDER);
        txtConcreteWsdlName.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
                true, false));
        txtConcreteWsdlName.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                validatePage();
            }
        });

        if (abstractWSDLFile != null) {
            txtAbstractWSDL.setText(abstractWSDLFile.getName());
            abstractTextModified();
        }
        //
        // Add binding style and SOAP address controls
        createBindingControls(root);

        validatePage();

        setControl(root);
    }

    /**
     * Create controls to select binding style / SOAP address.
     * 
     * @param root
     */
    private void createBindingControls(Composite root) {

        // Port soap address location
        Label portSoapAddressLocation = new Label(root, SWT.NONE);
        GridData gd = new GridData();
        portSoapAddressLocation.setLayoutData(gd);
        portSoapAddressLocation
                .setText(Messages.ConcreteWSDLWizardPage_SoapAddr_label);

        txtSoapAddress = new Text(root, SWT.BORDER);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        txtSoapAddress.setLayoutData(gd);
        txtSoapAddress.setText(DEFAULT_SOAP_ADDRESS_LOCATION);
        txtSoapAddress.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                validatePage();
            }
        });

        bindingStyleLabel = new Label(root, SWT.NONE);
        bindingStyleLabel
                .setText(Messages.ConcreteWSDLWizardPage_BindingStyle_label);

        Composite radioBtnContainer = new Composite(root, SWT.NONE);
        radioBtnContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                false));
        RowLayout layout = new RowLayout(SWT.HORIZONTAL);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        layout.spacing = 20;
        radioBtnContainer.setLayout(layout);

        rpcLit = new Button(radioBtnContainer, SWT.RADIO);
        rpcLit.setText(Messages.ConcreteWSDLWizardPage_RPCLit_label);
        docLit = new Button(radioBtnContainer, SWT.RADIO);
        docLit.setText(Messages.ConcreteWSDLWizardPage_DocLit_label);

        // If an abstract wsdl has already been selected then try to determine
        // the binding style
        if (getAbstractWsdlFile() != null) {
            selectedBindingStyle = getBindingStyle(getAbstractWsdlFile());
        }

        updateBindingStyle(selectedBindingStyle);

        final SelectionAdapter bindingStyleListener = new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (e.item == rpcLit) {
                    selectedBindingStyle = BindingStyle.RPC;
                } else {
                    selectedBindingStyle = BindingStyle.DOCUMENT;
                }
            }
        };

        rpcLit.addSelectionListener(bindingStyleListener);
        docLit.addSelectionListener(bindingStyleListener);

        // Binding transport
        /*
         * Label bindingTransport = new Label(root, SWT.None);
         * bindingTransport.setLayoutData(new GridData()); bindingTransport
         * .setText(Messages.ConcreteWSDLWizardPage_BindingTrans_label);
         */
        /*
         * bindingTransportText = new Text(root, SWT.BORDER);
         * gridData.horizontalSpan = 2;
         * bindingTransportText.setLayoutData(gridData);
         * bindingTransportText.setText(DEFAULT_BINDING_TRANSPORT);
         */
    }

    /**
     * Update the binding style in the UI and enable/disable relevant controls
     * accordingly.
     * 
     * @param style
     */
    private void updateBindingStyle(BindingStyle style) {
        if (rpcLit != null && !rpcLit.isDisposed()) {
            if (style != null) {
                /*
                 * Managed to determine the binding style from the abstract WSDL
                 * so preset and disable the option to change
                 */
                rpcLit.setSelection(style == BindingStyle.RPC);
                docLit.setSelection(style == BindingStyle.DOCUMENT);
                rpcLit.setEnabled(false);
                docLit.setEnabled(false);
                bindingStyleLabel.setEnabled(false);
                setMessage(null, WARNING);
            } else {
                // Cannot determine the binding style so set the first and let
                // user select
                rpcLit.setSelection(true);
                docLit.setSelection(false);
                rpcLit.setEnabled(true);
                docLit.setEnabled(true);
                bindingStyleLabel.setEnabled(true);

                setMessage(Messages.WSDLSelectionPage_cannotDetermineBindingStyle_warning_message,
                        WARNING);

                // Update the binding style value - defaulted to RPC
                selectedBindingStyle = BindingStyle.RPC;
            }
        }
    }

    private void abstractTextModified() {
        String abstractWsdlName = abstractWSDLFile.getName();
        IPath fileWithoutExtn =
                new Path(abstractWsdlName).removeFileExtension();

        String concretePath = fileWithoutExtn.toString() + CONCRETE_SUFFIX;
        IPath concreteWithFileExtn =
                new Path(concretePath)
                        .addFileExtension(Activator.WSDL_FILE_EXTENSION);
        txtConcreteWsdlName.setText(concreteWithFileExtn.toString());
        validatePage();
    }

    private void validatePage() {
        String errorMsg = null;
        if (txtAbstractWSDL != null
                && txtAbstractWSDL.getText().trim().length() > 0) {
            if (txtConcreteWsdlName.getText().trim().length() > 0) {
                if (!txtConcreteWsdlName.getText()
                        .endsWith(Activator.WSDL_FILE_EXTENSION)) {
                    errorMsg =
                            Messages.WSDLSelectionPage_ErrWsdlFileNameEndsWsdl_shortdesc;
                } else {
                    String concreteWsdlName = txtConcreteWsdlName.getText();
                    IContainer parent = abstractWSDLFile.getParent();
                    if (parent.getFile(new Path(concreteWsdlName))
                            .isAccessible()) {
                        errorMsg =
                                Messages.WSDLSelectionPage_ErrFileNameExists_shortdesc;
                    }
                }
            } else {
                errorMsg =
                        Messages.WSDLSelectionPage_ConcreteWsdlFileNameNotEmpty_shortdesc;
            }
        } else {
            errorMsg = Messages.WSDLSelectionPage_ErrSelectWSDL_shortdesc;
        }

        if (errorMsg == null) {
            // Check SOAP address
            if (txtSoapAddress != null
                    && txtSoapAddress.getText().trim().length() == 0) {
                errorMsg =
                        Messages.WSDLSelectionPage_soapAddressCannotBeEmpty_error_message;
            }
        }

        if (errorMsg != null) {
            setPageComplete(false);
            setErrorMessage(errorMsg);
        } else {
            setErrorMessage(null);
            setPageComplete(true);
        }
        if (getContainer().getCurrentPage() != null) {
            getContainer().updateButtons();
        }
    }

    /**
     * @param selection
     */
    public void init(IStructuredSelection selection) {
        if (selection != null && selection.getFirstElement() instanceof IFile) {
            IFile file = (IFile) selection.getFirstElement();
            if (Activator.WSDL_FILE_EXTENSION.equals(file.getFileExtension())) {
                abstractWSDLFile = file;

            }
        }
    }

    /**
     * Determine the binding style from the abstract WSDL.
     * 
     * @param wsdlFile
     * @return DOCUMENT or RPC. <code>null</code> (cannot determine) if a
     *         mixture of styles is found in the message parts.
     */
    private BindingStyle getBindingStyle(IFile wsdlFile) {
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(wsdlFile);
        if (wc != null) {
            BindingStyle style = null;
            EObject eo = wc.getRootElement();

            if (eo instanceof Definition) {
                Map<?, ?> messages = ((Definition) eo).getMessages();

                for (Object next : messages.values()) {
                    if (next instanceof Message) {
                        Map<?, ?> parts = ((Message) next).getParts();

                        for (Object nextPart : parts.values()) {
                            if (nextPart instanceof Part) {
                                Part part = (Part) nextPart;
                                if (part.getElementDeclaration() != null) {
                                    // This is document style
                                    if (style == null) {
                                        style = BindingStyle.DOCUMENT;
                                    } else if (style != BindingStyle.DOCUMENT) {
                                        // Cannot determine as mixture of styles
                                        // used in the message part
                                        return null;
                                    }
                                } else if (part.getTypeDefinition() != null) {
                                    // This is part style
                                    if (style == null) {
                                        style = BindingStyle.RPC;
                                    } else if (style != BindingStyle.RPC) {
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
            }

            return style;
        }

        return null;
    }

    /**
     * @return
     */
    public IFile getAbstractWsdlFile() {
        return abstractWSDLFile;
    }

    /**
     * @return
     */
    public IPath getConcreteWsdlPath() {
        if (abstractWSDLFile != null) {
            IContainer abstractFileContainer = abstractWSDLFile.getParent();
            if (abstractFileContainer != null) {
                IFile concreteWsdl =
                        abstractFileContainer.getFile(new Path(
                                txtConcreteWsdlName.getText()));
                return concreteWsdl.getFullPath();
            }
        }
        return null;
    }

    /**
     * @return
     */
    public String getSoapAddress() {
        if (txtSoapAddress != null) {
            return txtSoapAddress.getText();
        }
        return null;
    }

    /**
     * Get the selected binding style.
     * 
     * @return "document" or "rpc".
     */
    public String getBindingStyle() {
        return selectedBindingStyle.toString();
    }
}