package com.tibco.bx.emulation.ui.editor;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.ColumnLayout;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import com.tibco.bx.emulation.model.Assertion;
import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.model.InOutDataType;
import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.model.IntermediateInput;
import com.tibco.bx.emulation.model.MultiInput;
import com.tibco.bx.emulation.model.NamedElement;
import com.tibco.bx.emulation.model.Output;
import com.tibco.bx.emulation.model.Parameter;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.model.Testpoint;
import com.tibco.bx.emulation.ui.EmulationUIActivator;
import com.tibco.bx.emulation.ui.Messages;
import com.tibco.bx.emulation.ui.util.EmulationUIUtil;
import com.tibco.xpd.resources.WorkingCopy;

public class EmulationDetailPage extends FormPage {

	public EmulationDetailPage(EmulationDataEditor editor, String id, String title) {
		super(editor, id, title);
	}

	public void refresh() {
		IManagedForm managedForm = getManagedForm();
		createSections(managedForm);
		managedForm.getForm().getBody().layout();
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		ScrolledForm form = managedForm.getForm();
		ColumnLayout layout = new ColumnLayout();
		layout.topMargin = 0;
		layout.bottomMargin = 5;
		layout.leftMargin = 10;
		layout.rightMargin = 10;
		layout.horizontalSpacing = 10;
		layout.verticalSpacing = 10;
		layout.maxNumColumns = 4;
		layout.minNumColumns = 2;
		form.getBody().setLayout(layout);

		createSections(managedForm);

	}

	@Override
	public boolean isEditor() {
		return true;
	}

	private void createSections(IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();
		Control[] controls = form.getBody().getChildren();
		for (int i = 0; i < controls.length; i++) {
			controls[i].dispose();
		}
		WorkingCopy workingCopy = ((EmulationDataEditor) getEditor()).getWorkingCopy();
		EObject root = workingCopy.getRootElement();
		if (root instanceof EmulationData) {
			EList<ProcessNode> list = ((EmulationData) root).getProcessNodes();
			for (ProcessNode node : list) {
				createProcessNodeSection(managedForm, form.getBody(), node);
			}
		} else {
			// create error message
		}

	}

	private Section createProcessNodeSection(IManagedForm managedForm, Composite parent, ProcessNode processNode) {
		FormToolkit toolkit = managedForm.getToolkit();
		Section section = createSectionWithToolBar(managedForm, parent, processNode);
		Composite client = toolkit.createComposite(section);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.numColumns = 1;
		client.setLayout(layout);
		section.setClient(client);
		// get Input
		Input input = processNode.getInput();
		if (input != null) {
			createInoutComposite(managedForm, (Composite) section.getClient(), input,processNode);
		}
		// get IntermediateInputs
		if (processNode.getIntermediateInputs().size() > 0) {
			createIntermediateInputsComposite(managedForm, (Composite) section.getClient(), processNode);
		}

		// get Testpoints and Assertions
		createTestpointsAndAssertionsComposite(managedForm, (Composite) section.getClient(), processNode);
		// getOutput
		Output output = processNode.getOutput();
		if (output != null) {
			createInoutComposite(managedForm, (Composite) section.getClient(), output,null);
		}
		return section;
	}

	private ExpandableComposite createInoutComposite(IManagedForm managedForm, Composite parent, InOutDataType data,ProcessNode processNode) {
		FormToolkit toolkit = managedForm.getToolkit();
		ExpandableComposite inoutComposite = createExpandableComposite(managedForm, parent);
		inoutComposite.setExpanded(true);
		inoutComposite
				.setText(data instanceof Input ? Messages.getString("EmulationDetailPage.inputComposite.title") : Messages.getString("EmulationDetailPage.outputComposite.title"));//$NON-NLS-1$ //$NON-NLS-2$
		Layout parentLayout = parent.getLayout();
		if (parentLayout instanceof GridLayout) {
			inoutComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}
		Composite iolient = toolkit.createComposite(inoutComposite);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginBottom = 10;
		layout.numColumns = 1;
		iolient.setLayout(layout);
		inoutComposite.setClient(iolient);

		Hyperlink hyperlink = createEmulationElementLink(managedForm, iolient, data);
		hyperlink.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		if (data.getParameters().size() > 0) {
			Composite parametersComposite = createParametersComposite(managedForm, iolient, data);
			parametersComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}

		if (data.getSoapMessage() != null && data.getSoapMessage().length() > 0) {
			String label = data instanceof Input ? Messages.getString("EmulationDetailPage.SOAP.REQUEST")//$NON-NLS-1$
					: Messages.getString("EmulationDetailPage.SOAP.RESPONSE"); //$NON-NLS-1$
			Composite soapMessageComposite = createSoapMessageComposite(managedForm, iolient, data.getSoapMessage(), label);
			soapMessageComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}
		if(processNode !=null && processNode.getMultiInputNodes().size()>0){
			createMultiInputsComposite(managedForm,iolient,processNode);
		}
		return inoutComposite;
	}

	private ExpandableComposite createIntermediateInputsComposite(IManagedForm managedForm, Composite parent, ProcessNode processNode) {
		FormToolkit toolkit = managedForm.getToolkit();
		ExpandableComposite imComposite = createExpandableComposite(managedForm, (Composite) parent);
		imComposite.setExpanded(true);
		imComposite.setText(Messages.getString("EmulationDetailPage.intermediateInputsComposite.title"));//$NON-NLS-1$
		Composite pclient = toolkit.createComposite(imComposite);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginBottom = 10;
		layout.numColumns = 1;
		pclient.setLayout(layout);
		imComposite.setClient(pclient);

		for (IntermediateInput intermediateInput : processNode.getIntermediateInputs()) {
			createNamedElementWithTextComposite(managedForm, pclient, intermediateInput, intermediateInput.getRequestMessage(), Messages
					.getString("EmulationDetailPage.SOAP.REQUEST")); //$NON-NLS-1$
		}
		return imComposite;
	}

	
	
	private Composite createMultiInputsComposite(IManagedForm managedForm, Composite parent, ProcessNode processNode){
		FormToolkit toolkit = managedForm.getToolkit();
		Composite pclient = toolkit.createComposite(parent);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginBottom = 10;
		layout.numColumns = 1;
		pclient.setLayout(layout);
		
		for(MultiInput data : processNode.getMultiInputNodes()){
			Hyperlink hyperlink = createEmulationElementLink(managedForm, pclient, data);
			hyperlink.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			if (data.getParameters().size() > 0) {
				Composite parametersComposite = createParametersComposite(managedForm, pclient, data);
				parametersComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			}

			if (data.getSoapMessage() != null && data.getSoapMessage().length() > 0) {
				String label = data instanceof Input ? Messages.getString("EmulationDetailPage.SOAP.REQUEST")//$NON-NLS-1$
						: Messages.getString("EmulationDetailPage.SOAP.RESPONSE"); //$NON-NLS-1$
				Composite soapMessageComposite = createSoapMessageComposite(managedForm, pclient, data.getSoapMessage(), label);
				soapMessageComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			}
		}
		
		return parent;
	}
	private Composite createParametersComposite(IManagedForm managedForm, Composite parent, InOutDataType data){
		FormToolkit toolkit = managedForm.getToolkit();
		TableWrapLayout wtLayout = new TableWrapLayout();
		wtLayout.numColumns = 1;
		Composite composite = toolkit.createComposite(parent);
		composite.setLayout(wtLayout);
		for (Parameter parameter : data.getParameters()) {
			// TODO it's not right if the variable name changed
			Label name = toolkit.createLabel(composite, parameter.getName()
					+ Messages.getString("EmulationDetailPage.soapMessageComposite.semicolon"), SWT.READ_ONLY | SWT.WRAP);//$NON-NLS-1$
			TableWrapData twData = new TableWrapData(TableWrapData.FILL);
			twData.maxWidth = 100;
			name.setLayoutData(twData);
			Text value = toolkit.createText(composite, parameter.getValue(), SWT.READ_ONLY | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL);
			twData = new TableWrapData(TableWrapData.FILL);
			twData.maxWidth = 400;
			twData.maxHeight = 100;
			twData.grabHorizontal = true;
			value.setLayoutData(twData);
		}

		return composite;
	}

	private Composite createSoapMessageComposite(IManagedForm managedForm, Composite parent, String soapMessage, String label) {
		FormToolkit toolkit = managedForm.getToolkit();
		TableWrapLayout wtLayout = new TableWrapLayout();
		wtLayout.numColumns = 1;
		Composite composite = toolkit.createComposite(parent);
		composite.setLayout(wtLayout);
		Label name = toolkit.createLabel(composite,
				label + Messages.getString("EmulationDetailPage.soapMessageComposite.semicolon"), SWT.READ_ONLY | SWT.WRAP);//$NON-NLS-1$
		TableWrapData twData = new TableWrapData(TableWrapData.FILL);
		twData.maxWidth = 50;
		name.setLayoutData(twData);
		TextViewer soapViewer = new TextViewer(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.READ_ONLY);
		toolkit.adapt(soapViewer.getControl(), true, true);
		twData = new TableWrapData(TableWrapData.FILL);
		twData.maxWidth = 350;
		twData.maxHeight = 100;
		twData.grabHorizontal = true;
		soapViewer.getControl().setLayoutData(twData);
		soapViewer.setDocument(new Document());
		soapViewer.getDocument().set(soapMessage);
		return composite;
	}

	private Composite createTestpointsAndAssertionsComposite(IManagedForm managedForm, Composite parent, ProcessNode processNode) {
		FormToolkit toolkit = managedForm.getToolkit();
		ExpandableComposite taComposite = createExpandableComposite(managedForm, (Composite) parent);
		taComposite.setExpanded(true);
		taComposite.setText(Messages.getString("EmulationDetailPage.testpointsAndAssertionsComposite.title"));//$NON-NLS-1$
		Composite pclient = toolkit.createComposite(taComposite);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginBottom = 10;
		layout.numColumns = 1;
		pclient.setLayout(layout);
		taComposite.setClient(pclient);
		EList<Testpoint> list = processNode.getTestpoints();
		for (Testpoint testpoint : list) {
			createNamedElementWithTextComposite(managedForm, pclient, testpoint, testpoint.getExpression(), Messages
					.getString("EmulationDetailPage.TESTPOINT.EXPRESS"));//$NON-NLS-1$
		}
		EList<Assertion> aList = processNode.getAssertions();
		for (Assertion assertion : aList) {
			createEmulationElementLink(managedForm, pclient, assertion);
		}

		return taComposite;
	}

	private Section createSectionWithToolBar(IManagedForm managedForm, Composite parent, NamedElement element) {
		final ScrolledForm form = managedForm.getForm();
		FormToolkit toolkit = managedForm.getToolkit();

		Section section = toolkit.createSection(parent, Section.TWISTIE | Section.TITLE_BAR | Section.CLIENT_INDENT | Section.EXPANDED);
		section.setText(element.getName());
		Layout parentLayout = parent.getLayout();
		if (parentLayout instanceof GridLayout) {
			section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}
		Composite client = toolkit.createComposite(section);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.numColumns = 1;
		client.setLayout(layout);
		section.setClient(client);
		section.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(false);
			}
		});

		createToolBar(section, element);
		return section;
	}

	private ToolBar createToolBar(ExpandableComposite expandableComposite, final NamedElement element) {
		ToolBar toolBar = new ToolBar(expandableComposite, SWT.FLAT | SWT.HORIZONTAL);
		ToolItem toolItem = new ToolItem(toolBar, SWT.PUSH);
		toolItem.setImage(getImage(element));
		toolItem.setToolTipText(Messages.getString("EmulationDetailPage.toolbar.tooltip"));//$NON-NLS-1$
		toolItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				try {
					IEditorPart editorPart = EmulationUIUtil.openEmulationDiagramEditor(element);
					if (editorPart != null) {
						EmulationUIUtil.goToEmulationElement(editorPart, element);
					}
				} catch (Exception e) {
					EmulationUIActivator.log(e);
				}
			}
		});
		expandableComposite.setTextClient(toolBar);
		return toolBar;
	}

	private Composite createNamedElementWithTextComposite(IManagedForm managedForm, Composite parent, NamedElement element, String text, String label) {
		FormToolkit toolkit = managedForm.getToolkit();
		Composite composite = toolkit.createComposite(parent);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.numColumns = 1;
		composite.setLayout(layout);

		createEmulationElementLink(managedForm, composite, element);
		Composite tComposite = toolkit.createComposite(composite);
		tComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		TableWrapLayout wtLayout = new TableWrapLayout();
		wtLayout.bottomMargin = 2;
		wtLayout.leftMargin = 0;
		wtLayout.rightMargin = 0;
		tComposite.setLayout(wtLayout);
		// composite.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));

		Composite soapComposite = createSoapMessageComposite(managedForm, tComposite, text, label);
		TableWrapData twData = new TableWrapData(TableWrapData.FILL);
		twData.maxWidth = 400;
		twData.maxHeight = 200;
		twData.grabHorizontal = true;
		soapComposite.setLayoutData(twData);

		return composite;
	}

	private ExpandableComposite createExpandableComposite(IManagedForm managedForm, Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		final ScrolledForm form = managedForm.getForm();
		ExpandableComposite expandableComposite = toolkit.createExpandableComposite(parent, ExpandableComposite.TREE_NODE
				| ExpandableComposite.CLIENT_INDENT);
		Layout parentLayout = parent.getLayout();
		if (parentLayout instanceof GridLayout) {
			expandableComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}
		expandableComposite.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		return expandableComposite;
	}

	private Hyperlink createEmulationElementLink(IManagedForm managedForm, Composite parent, final NamedElement element) {
		FormToolkit toolkit = managedForm.getToolkit();
		ImageHyperlink hyperlink = toolkit.createImageHyperlink(parent, SWT.WRAP);
		hyperlink.setImage(getImage(element));
		hyperlink.setText(element.getName());
		hyperlink.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				try {
					IEditorPart editorPart = EmulationUIUtil.openEmulationDiagramEditor(element);
					if (editorPart != null) {
						EmulationUIUtil.goToEmulationElement(editorPart, element);
					}
				} catch (Exception exception) {
					MessageDialog.openError(EmulationDetailPage.this.getSite().getShell(), Messages
							.getString("OpenProcessActionProvider.error.title")//$NON-NLS-1$
							, Messages.getString("OpenProcessActionProvider.error.message"));//$NON-NLS-1$ 
				}
			}
		});
		return hyperlink;
	}

	private Image getImage(Object element) {
		if (element instanceof ProcessNode) {
			return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_PROCESSNODE);
		} else if (element instanceof Input) {
			return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_INPUT);
		} else if (element instanceof Output) {
			return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_OUTPUT);
		} else if (element instanceof IntermediateInput) {
			return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_INTERMEDIATEINPUT);
		} else if(element instanceof MultiInput){
			return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_INPUT);
		}else if (element instanceof Testpoint) {
			return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_TESTPOINT);
		} else if (element instanceof Assertion) {
			if (((Assertion) element).isAccessible()) {
				return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_ASSERTION_EN);
			} else {
				return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_ASSERTION_DIS);
			}
		}
		return null;
	}

}
