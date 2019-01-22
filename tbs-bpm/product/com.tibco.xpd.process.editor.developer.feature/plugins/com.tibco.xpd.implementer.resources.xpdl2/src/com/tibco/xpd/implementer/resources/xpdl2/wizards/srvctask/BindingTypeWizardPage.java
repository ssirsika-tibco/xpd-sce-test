/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.wizards.srvctask;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IProject;
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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.uml2.uml.Classifier;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.SoapBindingStyle;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Wizard page to ask the user for binding type and target namespace
 * 
 * @author rsomayaj
 * @since 3.3 (17 Sep 2010)
 */
public class BindingTypeWizardPage extends AbstractXpdWizardPage {

    public enum WSDLType {
        ABSTRACT, CONCRETE;
    }

    private static final String DEFAULT_SOAP_ADDRESS =
            "http://www.example.org/"; //$NON-NLS-1$

    private String derivedWsdlNamespace;

    private SoapBindingStyle wsdlBindingType;

    private Button btnDocumentLiteral;

    private Button btnRpcLiteral;

    private Text txtTargetNamespace;

    private final Activity serviceActivity;

    private Button btnAbstractWsdl;

    private Button btnConcreteWsdl;

    private Text txtSoapAddress;

    /**
     * @param pageName
     * @param title
     * @param sel
     * @param titleImage
     */
    protected BindingTypeWizardPage(String pageName, String title,
            Activity serviceActivity) {
        super(pageName, title, null);
        this.serviceActivity = serviceActivity;
        setDescription(Messages.BindingTypeWizardPage_WizDesc_shortdesc);
        if (this.serviceActivity != null) {
            Process process = serviceActivity.getProcess();
            if (process != null) {
                derivedWsdlNamespace =
                        String.format("%1$s_%2$s_%3$s", //$NON-NLS-1$
                                Xpdl2ModelUtil.getDerivedWsdlNamespace(process
                                        .getPackage()),
                                serviceActivity.getName(),
                                serviceActivity.getId());
                wsdlBindingType = Xpdl2ModelUtil.getWsdlBindingStyle(process);
            }
        }

    }

    /**
     * @see org.eclipse.jface.wizard.WizardPage#validate()
     * 
     * @return
     */
    public boolean validate() {

        String errMsg = null;

        if (SoapBindingStyle.DOCUMENT_LITERAL.equals(getBindingType())) {
            if (doesContainMoreThanOneInOrOutParams()) {
                errMsg = Messages.BindingTypeWizardPage_WSICompErr_shortdesc;
            }
            /*
             * XPD-3972: for doc literal binding style wsdl check if a parameter
             * associated with the activity refers to a TLE from a generated bom
             * (or bom with xsd notation profile applied). if not then show the
             * validation message on this page complaining that Doc-literal type
             * WSDL cannot be generated
             */
            if (null == errMsg && refersToNonTLETypeFromGenBOM()) {
                errMsg =
                        Messages.BindingTypeWizardPage_docLitWsdlBindingTypeErr_shortdesc1;
            }
        } else if (SoapBindingStyle.RPC_LITERAL.equals(getBindingType())) {
            /*
             * XPD-3972: for rpc literal binding style wsdl check if a parameter
             * associated with the activity refers to a anonymous type in
             * generated bom (or bom with xsd notation profile applied). if yes,
             * then show the validation message on this page complaining that
             * rpc-literal type WSDL cannot be generated
             */
            boolean classifierAnonymousType = refersToAnonymousTypeInGenBOM();
            if (classifierAnonymousType) {
                errMsg =
                        Messages.BindingTypeWizardPage_rpcLitWsdlBindingTypeErr_shortdesc;
            }

        }

        if (errMsg == null) {
            // Check SOAP address
            if (txtSoapAddress != null
                    && txtSoapAddress.getText().trim().length() == 0) {
                errMsg =
                        Messages.BindingTypeWizardPage_soapAddressCannotBeEmpty_error_message;
            }
        }

        setErrorMessage(errMsg);
        setPageComplete(errMsg == null);
        return errMsg == null;
    }

    private boolean doesContainMoreThanOneInOrOutParams() {
        if (serviceActivity != null) {
            List<AssociatedParameter> activityAssociatedParameters =
                    ProcessInterfaceUtil
                            .getActivityAssociatedParameters(serviceActivity);
            if (activityAssociatedParameters.isEmpty()) {
                List<ProcessRelevantData> associatedProcessRelevantDataForActivity =
                        ProcessInterfaceUtil
                                .getAssociatedProcessRelevantDataForActivity(serviceActivity);
                int inCount = 0;
                for (ProcessRelevantData processRelevantData : associatedProcessRelevantDataForActivity) {
                    if (processRelevantData instanceof FormalParameter) {
                        FormalParameter formalParameter =
                                (FormalParameter) processRelevantData;
                        if (ModeType.IN_LITERAL.equals(formalParameter
                                .getMode())
                                || ModeType.INOUT_LITERAL
                                        .equals(formalParameter.getMode())) {
                            inCount++;
                        }
                    } else if (processRelevantData instanceof DataField) {
                        inCount++;
                    }
                    if (inCount > 1) {
                        return true;
                    }
                }
            } else {
                int inCount = 0;
                for (AssociatedParameter associatedParameter : activityAssociatedParameters) {
                    if (ModeType.IN_LITERAL.equals(associatedParameter
                            .getMode())
                            || ModeType.INOUT_LITERAL
                                    .equals(associatedParameter.getMode())) {
                        inCount++;
                    }
                    if (inCount > 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /***
     * 
     * @return true if the parameter referencing a classifier is NOT marked as
     *         TLE and belongs to an XSD generated BOM, false otherwise
     */
    private boolean refersToNonTLETypeFromGenBOM() {

        Classifier referencedClassifier = null;

        Collection<ActivityInterfaceData> activityInterfaceData =
                ActivityInterfaceDataUtil
                        .getActivityInterfaceData(serviceActivity);

        for (ActivityInterfaceData interfaceData : activityInterfaceData) {
            ProcessRelevantData processRelevantData = interfaceData.getData();

            if (processRelevantData.getDataType() instanceof ExternalReference) {
                ExternalReference externalReference =
                        (ExternalReference) processRelevantData.getDataType();
                IProject project =
                        WorkingCopyUtil.getProjectFor(processRelevantData);

                referencedClassifier =
                        (Classifier) ProcessUIUtil
                                .getReferencedClassifier(externalReference,
                                        project);

                if (null != referencedClassifier) {
                    /*
                     * XPD-5919: Saket: Need to take the user defined BOM use
                     * case into account since this method should return false
                     * ONLY if a generated BOM type that is not Top Level
                     * Element is associated with the task.
                     */

                    if (XSDUtil
                            .doesClassifierBelongToXsdGeneratedBom(referencedClassifier)
                            && !XSDUtil
                                    .isClassifierTopLevelElement(referencedClassifier)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /***
     * 
     * @return true if the parameter referencing classifier represents an
     *         anonymous type, false otherwise
     */
    private boolean refersToAnonymousTypeInGenBOM() {

        boolean classifierAnonymousType = false;

        Classifier referencedClassifier = null;

        Collection<ActivityInterfaceData> activityInterfaceData =
                ActivityInterfaceDataUtil
                        .getActivityInterfaceData(serviceActivity);

        for (ActivityInterfaceData interfaceData : activityInterfaceData) {
            ProcessRelevantData processRelevantData = interfaceData.getData();

            if (processRelevantData.getDataType() instanceof ExternalReference) {
                ExternalReference externalReference =
                        (ExternalReference) processRelevantData.getDataType();
                IProject project =
                        WorkingCopyUtil.getProjectFor(processRelevantData);

                referencedClassifier =
                        (Classifier) ProcessUIUtil
                                .getReferencedClassifier(externalReference,
                                        project);
                if (null != referencedClassifier) {
                    classifierAnonymousType =
                            XSDUtil.isClassifierAnonymousType(referencedClassifier);
                    if (classifierAnonymousType) {
                        break;
                    }
                }
            }
        }

        return classifierAnonymousType;
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

        createLabel(root, Messages.BindingTypeWizardPage_lblTgtNs_label);

        txtTargetNamespace = new Text(root, SWT.BORDER);
        GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
        data.widthHint = 200;
        txtTargetNamespace.setLayoutData(data);

        if (derivedWsdlNamespace != null) {
            txtTargetNamespace.setText(derivedWsdlNamespace);
        }

        /*
         * Create the binding style controls
         */
        createBindingStyleControl(root);

        /*
         * Create WSDL type controls
         */
        createWsdlTypeControls(root);

        validate();

        setControl(root);

    }

    /**
     * Create the controls to select the WSDL type.
     * 
     * @param parent
     */
    private void createWsdlTypeControls(Composite parent) {
        createLabel(parent, Messages.BindingTypeWizardPage_wsdlType_label);

        Composite radioControl = new Composite(parent, SWT.NONE);
        radioControl
                .setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        radioControl.setLayout(new RowLayout(SWT.HORIZONTAL));

        btnAbstractWsdl = new Button(radioControl, SWT.RADIO);
        btnAbstractWsdl
                .setText(Messages.BindingTypeWizardPage_abstract_radio_label);
        btnAbstractWsdl.setSelection(true);

        btnConcreteWsdl = new Button(radioControl, SWT.RADIO);
        btnConcreteWsdl
                .setText(Messages.BindingTypeWizardPage_concrete_radio_label);
        btnConcreteWsdl.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                lblSoapAddress.setEnabled(btnConcreteWsdl.getSelection());
                txtSoapAddress.setEnabled(btnConcreteWsdl.getSelection());
            }
        });

        Group grp = new Group(parent, SWT.NONE);
        grp.setText(Messages.BindingTypeWizardPage_concreteWsdl_group_label);
        GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
        data.horizontalSpan = 2;
        grp.setLayoutData(data);
        grp.setLayout(new GridLayout(2, false));

        lblSoapAddress =
                createLabel(grp,
                        Messages.BindingTypeWizardPage_soapAddress_label);
        txtSoapAddress = new Text(grp, SWT.BORDER);
        txtSoapAddress.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                false));
        txtSoapAddress.setText(DEFAULT_SOAP_ADDRESS);

        lblSoapAddress.setEnabled(false);
        txtSoapAddress.setEnabled(false);

        txtSoapAddress.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                validate();
            }
        });
    }

    /**
     * Create the controls to select the binding style.
     * 
     * @param parent
     */
    private void createBindingStyleControl(Composite parent) {
        createLabel(parent, Messages.BindingTypeWizardPage_lblBindingType_label);

        Composite radioControl = new Composite(parent, SWT.NONE);
        radioControl
                .setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        radioControl.setLayout(new RowLayout(SWT.HORIZONTAL));

        btnDocumentLiteral = new Button(radioControl, SWT.RADIO);
        btnDocumentLiteral
                .setText(Messages.BindingTypeWizardPage_btnDocLit_label);
        btnDocumentLiteral.addSelectionListener(selListener);
        btnRpcLiteral = new Button(radioControl, SWT.RADIO);
        btnRpcLiteral.setText(Messages.BindingTypeWizardPage_btnRPCLit_label);
        btnRpcLiteral.addSelectionListener(selListener);

        switch (wsdlBindingType.getValue()) {
        case SoapBindingStyle.DOCUMENT_LITERAL_VALUE:
            btnDocumentLiteral.setSelection(true);
            btnRpcLiteral.setSelection(false);
            break;
        case SoapBindingStyle.RPC_LITERAL_VALUE:
            btnDocumentLiteral.setSelection(false);
            btnRpcLiteral.setSelection(true);
            break;
        default:
            break;
        }
    }

    /**
     * Create a label with specific width hint.
     * 
     * @param parent
     * @param text
     * @return
     */
    private Label createLabel(Composite parent, String text) {
        Label lbl = new Label(parent, SWT.NONE);
        GridData data = new GridData();
        data.widthHint = 125;
        lbl.setLayoutData(data);
        lbl.setText(text);
        return lbl;
    }

    SelectionListener selListener = new SelectionListener() {

        @Override
        public void widgetSelected(SelectionEvent e) {
            validate();

        }

        @Override
        public void widgetDefaultSelected(SelectionEvent e) {
            validate();
        }
    };

    private Label lblSoapAddress;

    /**
     * @return
     */
    public SoapBindingStyle getBindingType() {
        if (btnRpcLiteral.getSelection()) {
            return SoapBindingStyle.RPC_LITERAL;
        }
        // Default BindingType.DOC_LIT
        return SoapBindingStyle.DOCUMENT_LITERAL;
    }

    /**
     * @return the derivedWsdlNamespace
     */
    public String getWsdlNamespace() {
        return txtTargetNamespace.getText();
    }

    /**
     * Get the SOAP address.
     * 
     * @return
     */
    public String getSOAPAddress() {
        return txtSoapAddress.getText();
    }

    /**
     * Get the type of WSDL to create
     * 
     * @return ABSTRACT or CONCRETE.
     */
    public WSDLType getWsdlType() {
        if (btnConcreteWsdl.getSelection()) {
            return WSDLType.CONCRETE;
        }
        return WSDLType.ABSTRACT;
    }
}
