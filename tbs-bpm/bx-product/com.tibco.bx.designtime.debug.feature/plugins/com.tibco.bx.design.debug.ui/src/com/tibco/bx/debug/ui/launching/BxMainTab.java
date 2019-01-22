package com.tibco.bx.debug.ui.launching;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.progress.UIJob;

import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.launching.IBxLaunchAttribute;
import com.tibco.bx.debug.core.runtime.IConnection;
import com.tibco.bx.debug.core.runtime.IConnectionFactory;
import com.tibco.bx.debug.core.runtime.internal.ConnectionFactoryManager;
import com.tibco.bx.debug.core.runtime.internal.ModelTypeManager;
import com.tibco.bx.debug.core.runtime.internal.NodeDefinitionFactoryManager;
import com.tibco.bx.debug.core.ws.wss.util.WSConstants;
import com.tibco.bx.debug.ui.DebugUIActivator;
import com.tibco.bx.debug.ui.Messages;
import com.tibco.bx.debug.ui.dialogs.TAServerSelectionDialog;
import com.tibco.xpd.deploy.ConfigParameter;
import com.tibco.xpd.deploy.Server;

/**
 * Main Tab to specify the remote engine to run/debug.
 * 
 */
public class BxMainTab extends AbstractLaunchConfigurationTab implements ModifyListener {

	protected Label typeLabel;
	protected Label hostNameLabel;
	protected Label protocolLabel;
	protected Label portLabel;
	protected Label urlLabel;
	protected Label userLabel;
	protected Label passwordLabel;
	protected Label soapVerionLabel;

	protected Combo typeCombo;
	protected Combo hostNameCombo;
	protected Combo protocolCombo;
	protected Combo portCombo;

	protected Text userText;
	protected Text passwordText;
	protected Text urlText;
	
	protected Button soap11Button;
	protected Button soap12Button;

	protected Group processEngineGroup;
	protected Group availableEngineGroup;
	protected Group hostGroup;

	protected Button browseHostButton;
	protected Button editURLButton;
	protected Button connectButton;
	protected Button addEngineButton;
	protected Button removeEngineButton;

	protected TableViewer availableEngineViewer;
	private List<RemoteEngineElement> enginesList = new ArrayList<RemoteEngineElement>();
	private List<RemoteEngineElement> hList = new ArrayList<RemoteEngineElement>();
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(org.eclipse
	 * .swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		final Composite root = new Composite(parent, SWT.NONE);
		setControl(root);
		GridLayout layout = new GridLayout();
		root.setLayout(layout);

		processEngineGroup = new Group(root, SWT.FLAT);
		layout = new GridLayout();
		layout.numColumns = 6;
		layout.makeColumnsEqualWidth = true;
		processEngineGroup.setLayout(layout);
		processEngineGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		processEngineGroup.setText(Messages.getString("BxMainTab.processEngineGroup.title")); //$NON-NLS-1$

		// left part
		final Composite leftComp = new Composite(processEngineGroup, SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 5;
		leftComp.setLayoutData(gridData);
		layout = new GridLayout(2, false);
		leftComp.setLayout(layout);

		// available engines
		availableEngineGroup = new Group(leftComp, SWT.FLAT);
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		availableEngineGroup.setLayoutData(gridData);
		availableEngineGroup.setLayout(new GridLayout(1, true));
		availableEngineGroup.setText(Messages.getString("BxMainTab.availableEngineGroup.title")); //$NON-NLS-1$

		availableEngineViewer = new TableViewer(availableEngineGroup);
		availableEngineViewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
		availableEngineViewer.setContentProvider(new IStructuredContentProvider() {

			@Override
			public Object[] getElements(Object element) {
				return ((List) element).toArray();
			}

			@Override
			public void dispose() {

			}

			@Override
			public void inputChanged(Viewer arg0, Object arg1, Object arg2) {

			}

		});

		availableEngineViewer.setLabelProvider(new ILabelProvider() {

			@Override
			public Image getImage(Object element) {
				RemoteEngineElement e = (RemoteEngineElement) element;
				Image img = DebugUIActivator.getRegisteredImage(e.getType());
				return img;
			}

			@Override
			public String getText(Object element) {
				RemoteEngineElement ele = (RemoteEngineElement) element;
				if ("".equals(ele.getType()) && "".equals(ele.getHostName()) && "".equals(ele.getProtocol()) && "".equals(ele.getPort()) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
						&& "".equals(ele.getEndpointURI()) && "".equals(ele.getUrl())) { //$NON-NLS-1$ //$NON-NLS-2$
					return Messages.getString("BxMainTab.newItem.label"); //$NON-NLS-1$
				}
				return ele.getUrl();
			}

			@Override
			public void addListener(ILabelProviderListener listener) {

			}

			@Override
			public void dispose() {

			}

			@Override
			public boolean isLabelProperty(Object arg0, String arg1) {
				return true;
			}

			@Override
			public void removeListener(ILabelProviderListener listener) {

			}

		});
		availableEngineViewer.setInput(enginesList);
		availableEngineViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				showEngine();
			}

		});

		typeLabel = new Label(leftComp, SWT.NONE);
		typeLabel.setText(Messages.getString("BxMainTab.processEngineType.label")); //$NON-NLS-1$
		GridData labelGridData = new GridData();
		labelGridData.horizontalIndent = 3;
		typeLabel.setLayoutData(labelGridData);
		
		typeCombo = new Combo(leftComp, SWT.BORDER | SWT.READ_ONLY);
		typeCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		typeCombo.setItems(ModelTypeManager.getModelNames());
		typeCombo.addModifyListener(this);
		
		hostGroup = new Group(leftComp, SWT.BORDER);
		hostGroup.setText(Messages.getString("BxMainTab.hostInfoGroup.title")); //$NON-NLS-1$
		hostGroup.setLayout(new GridLayout(2, false));
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		hostGroup.setLayoutData(gridData);

		hostNameLabel = new Label(hostGroup, SWT.NONE);
		hostNameLabel.setText(Messages.getString("BxMainTab.processHostName.label")); //$NON-NLS-1$
		Composite subComposite = new Composite(hostGroup, SWT.NONE);
		layout = new GridLayout(2, false);
		layout.verticalSpacing = 0;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		subComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		subComposite.setLayout(layout);
		hostNameCombo = new Combo(subComposite, SWT.BORDER);
		hostNameCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		hList = LaunchUtil.readRemoteEngines();
		hostNameCombo.setItems(LaunchUtil.getUniqueHostNames(hList));
		hostNameCombo.addModifyListener(this);
		browseHostButton = new Button(subComposite, SWT.PUSH);
		browseHostButton.setText(Messages.getString("BxMainTab.browseButton.label")); //$NON-NLS-1$
		browseHostButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TAServerSelectionDialog dialog = new TAServerSelectionDialog(leftComp.getShell());
				int ret = dialog.open();
				if (ret == Dialog.OK) {
					Object[] result = dialog.getResult();
					if (result != null && result.length >= 1 && result[0] instanceof Server) {
						getConfigFromServer((Server) result[0]);
					}
				}
			}
		});
		
		protocolLabel = new Label(hostGroup, SWT.NONE);
		protocolLabel.setText(Messages.getString("BxMainTab.processProtocol.label")); //$NON-NLS-1$
		protocolCombo = new Combo(hostGroup, SWT.BORDER | SWT.READ_ONLY);
		protocolCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		protocolCombo.setItems(DebugCoreActivator.getDefault().getConnectionTypes());
		protocolCombo.addModifyListener(this);

		portLabel = new Label(hostGroup, SWT.NONE);
		portLabel.setText(Messages.getString("BxMainTab.processPort.label")); //$NON-NLS-1$
		portCombo = new Combo(hostGroup, SWT.BORDER);
		portCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		portCombo.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				String inStr = e.text;
				if (inStr.length() > 0) {
					try {
						Integer.parseInt(inStr);
						String old = ((Combo) e.getSource()).getText();
						String result = old.substring(0, e.start) + inStr + old.substring(e.end);
						int port = Integer.parseInt(result);
						if (port > 65535 || port < 0) {
							e.doit = false;
						} else {
							e.doit = true;
						}
					} catch (Exception ep) {
						e.doit = false;
					}
				}

			}
		});
		portCombo.addModifyListener(this);
		
		urlLabel = new Label(hostGroup, SWT.NONE);
		urlLabel.setText("URL:"); //$NON-NLS-1$
		
		subComposite = new Composite(hostGroup, SWT.NONE);
		layout = new GridLayout(2, false);
		layout.verticalSpacing = 0;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		subComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		subComposite.setLayout(layout);
		urlText = new Text(subComposite, SWT.BORDER | SWT.READ_ONLY);
		urlText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		urlText.addModifyListener(this);
		editURLButton = new Button(subComposite, SWT.PUSH);
		editURLButton.setText(Messages.getString("BxMainTab.editURLButton.label")); //$NON-NLS-1$
		editURLButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				InputDialog dlg = new InputDialog(Display.getCurrent().getActiveShell(), Messages.getString("BxMainTab.editURLDialog.title"), //$NON-NLS-1$
						Messages.getString("BxMainTab.editURLDialog.message"), getCurrentURL(), new URLValidator()); //$NON-NLS-1$
				if (dlg.open() == Window.OK) {
					updateFields(dlg.getValue());
				}
			}
		});
		
		userLabel = new Label(leftComp, SWT.NONE);
		userLabel.setText(Messages.getString("BxMainTab.processUsername.label")); //$NON-NLS-1$
		userLabel.setLayoutData(labelGridData);
		userText = new Text(leftComp, SWT.BORDER);
		userText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		userText.addModifyListener(this);

		passwordLabel = new Label(leftComp, SWT.NONE);
		passwordLabel.setText(Messages.getString("BxMainTab.processPassword.label")); //$NON-NLS-1$
		passwordLabel.setLayoutData(labelGridData);
		passwordText = new Text(leftComp, SWT.BORDER | SWT.PASSWORD);
		passwordText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		passwordText.addModifyListener(this);

		soapVerionLabel = new Label(leftComp, SWT.NONE);
		soapVerionLabel.setText(Messages.getString("BxMainTab.soapVersion.label")); //$NON-NLS-1$
		soapVerionLabel.setLayoutData(labelGridData);

		subComposite = new Composite(leftComp, SWT.NONE);
		layout = new GridLayout(2, false);
		layout.verticalSpacing = 0;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		subComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		subComposite.setLayout(layout);

		soap11Button = new Button(subComposite, SWT.RADIO);
		soap11Button.setText(WSConstants.SOAP11);
		soap11Button.setLayoutData(new GridData(GridData.BEGINNING));
		soap11Button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				radioChanged();
			}

		});

		soap12Button = new Button(subComposite, SWT.RADIO);
		soap12Button.setText(WSConstants.SOAP12);
		soap12Button.setLayoutData(new GridData(GridData.BEGINNING));
		soap12Button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				radioChanged();
			}

		});

		// right part
		Composite rightComp = new Composite(processEngineGroup, SWT.NONE);
		layout = new GridLayout(1, false);
		layout.marginWidth = 10;
		rightComp.setLayout(layout);
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 0;
		gridData.verticalIndent = 5;
		rightComp.setLayoutData(gridData);

		addEngineButton = new Button(rightComp, SWT.PUSH);
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gridData.widthHint = 80;
		addEngineButton.setLayoutData(gridData);
		addEngineButton.setText(Messages.getString("BxMainTab.addButton.label")); //$NON-NLS-1$
		addEngineButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				addEngine();
			}
		});

		removeEngineButton = new Button(rightComp, SWT.NONE);
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		gridData.widthHint = 80;
		removeEngineButton.setLayoutData(gridData);
		removeEngineButton.setText(Messages.getString("BxMainTab.removeButton.label")); //$NON-NLS-1$
		removeEngineButton.setEnabled(!enginesList.isEmpty());
		removeEngineButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				removeEngine();
			}
		});

		connectButton = new Button(rightComp, SWT.NONE);
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		gridData.widthHint = 80;
		connectButton.setLayoutData(gridData);
		connectButton.setText(Messages.getString("BxMainTab.connectButton.label")); //$NON-NLS-1$
		connectButton.setEnabled(false);
		connectButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				doConnection();
			}
		});

		setFormEnabled(false);

	}
	
	private void updateFields(String urlValue) {
		try {
			URL url = new URL(urlValue);
			hostNameCombo.setText(url.getHost());
			protocolCombo.setText(url.getProtocol());
			int port = url.getPort() == -1 ? 80 : url.getPort();
			portCombo.setText(String.valueOf(port));
			urlText.setText(urlValue);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	private String generateURL() {
		String urlValue = getCurrentURL();
		String protocol = getProtocol();
		String host = getHostName();
		String port = getPort();
		String path = NodeDefinitionFactoryManager.getDefaultEndpointURI(getType());
//		if (!"".equals(urlValue)) { //$NON-NLS-1$
//			try {
//				URL url = new URL(urlValue);
////				path = url.getPath();
//			} catch (MalformedURLException e) {
//			}
//		}
		if ("80".equals(port) || "".equals(port)) { //$NON-NLS-1$ //$NON-NLS-2$
			urlValue = String.format("%s://%s%s", protocol, host, path); //$NON-NLS-1$
		} else {
			urlValue = String.format("%s://%s:%s%s", protocol, host, port, path); //$NON-NLS-1$
		}
		return urlValue;
	}

	private void getConfigFromServer(Server server) {
		EList<ConfigParameter> list = server.getServerConfig().getConfigParameters();
		for (ConfigParameter parameter : list) {
			String key = parameter.getKey();
			if (TASConstants.TAS_SERVER_URL.equals(key)) {
				String url = parameter.getValue().toLowerCase();
				String hostName = null;
				if (url.startsWith(TASConstants.HTTP_PREFIX)) {
					hostName = url.substring(TASConstants.HTTP_PREFIX.length(), url.length());
				} else if (url.startsWith(TASConstants.HTTPS_PREFIX)) {
					hostName = url.substring(TASConstants.HTTPS_PREFIX.length(), url.length());
				}
				if (hostName != null) {
					int index = hostName.indexOf(":");//$NON-NLS-1$
					hostNameCombo.setText(hostName.substring(0, index == -1 ? hostName.length() : index));
				}
			}
		}
	}

	public void modifyText(ModifyEvent e) {
//		saveEngineButton.setEnabled(false);
		connectButton.setEnabled(true);
		setErrorMessage(null);
		Object source = e.getSource();
		if (source == typeCombo) {
			if ("".equals(typeCombo.getText())) { //$NON-NLS-1$
				connectButton.setEnabled(false);
				setErrorMessage(Messages.getString("BxMainTab.typeEmpty.error")); //$NON-NLS-1$
				setValid(false);
			} else {
				checkHostInfo();
				urlText.setText(generateURL());
			}
		}
		if (source == hostNameCombo || source == protocolCombo || source == portCombo) {
//			portCombo.setItems(LaunchUtil.getUniquePorts(hList, getHostName(), getProtocol()));
			userText.setText(""); //$NON-NLS-1$
			passwordText.setText(""); //$NON-NLS-1$
			urlText.setText(generateURL());
			if ("".equals(getHostName()) || "".equals(getProtocol())) { //$NON-NLS-1$
				connectButton.setEnabled(false);
				setErrorMessage(Messages.getString("BxMainTab.hostEmpty.error")); //$NON-NLS-1$
				setValid(false);
			} else {
				checkType();
			}
		}
		if (enginesList.isEmpty()) {
			removeEngineButton.setEnabled(false);
		} else {
			removeEngineButton.setEnabled(true);
		}

	}
	
	private void checkType() {
		if ("".equals(typeCombo.getText())) { //$NON-NLS-1$
			connectButton.setEnabled(false);
			setErrorMessage(Messages.getString("BxMainTab.typeEmpty.error")); //$NON-NLS-1$
			setValid(false);
		} else {
			connectButton.setEnabled(true);
			setValid(true);
		}
	}
	
	private void checkHostInfo(){
		if ("".equals(getHostName()) || "".equals(getProtocol())) { //$NON-NLS-1$ //$NON-NLS-2$
			connectButton.setEnabled(false);
			setErrorMessage(Messages.getString("BxMainTab.hostEmpty.error")); //$NON-NLS-1$
			setValid(false);
		} else {
			connectButton.setEnabled(true);
			setValid(true);
		}
	}
	
	private void showEngine() {
		IStructuredSelection selection = (IStructuredSelection) availableEngineViewer.getSelection();
		setFormEnabled(selection.size() == 1);
		RemoteEngineElement element = (RemoteEngineElement) selection.getFirstElement();
		boolean flag = true;
		if (element != null) {
			if ("".equals(element.getType())) { //$NON-NLS-1$
				typeCombo.setItems(ModelTypeManager.getModelNames());
			} else {
				typeCombo.setText(ModelTypeManager.getModelName(element.getType()));
			}
			hostNameCombo.setText(element.getHostName());
			if ("".equals(element.getProtocol())) { //$NON-NLS-1$
				protocolCombo.setItems(DebugCoreActivator.getDefault().getConnectionTypes());
			} else {
				protocolCombo.setText(element.getProtocol());
			}
			portCombo.setText(element.getPort());
			urlText.setText(element.getUrl());
			userText.setText(element.getUsername() == null ? "" : element.getUsername()); //$NON-NLS-1$
			passwordText.setText(element.getPassword() == null ? "" : element.getPassword()); //$NON-NLS-1$

			if (WSConstants.SOAP11.equals(element.getSoapVersion()) || "".equals(element.getSoapVersion())) { //$NON-NLS-1$
				soap11Button.setSelection(true);
				soap12Button.setSelection(false);
			} else {
				soap11Button.setSelection(false);
				soap12Button.setSelection(true);
			}
			flag = !isEmpty(element);
		} else {
			List<RemoteEngineElement> engines = (List) availableEngineViewer.getInput();
			for (RemoteEngineElement ele : engines) {
				flag = flag && !isEmpty(ele);
			}
		}
		refreshButtons();
		setValid(flag);
	}

	private boolean isEmpty(RemoteEngineElement element) {
		if (element != null) {
			return element.getType().isEmpty()
					&& element.getHostName().isEmpty()
					&& element.getProtocol().isEmpty()
					&& element.getPort().isEmpty()
					&& element.getUrl().isEmpty()
					&& element.getUsername().isEmpty()
					&& element.getPassword().isEmpty();
		}
		return true;
	}
	
	private void setFormEnabled(boolean enabled) {
		typeLabel.setEnabled(enabled);
		typeCombo.setEnabled(enabled);
		hostNameLabel.setEnabled(enabled);
		hostNameCombo.setEnabled(enabled);
		browseHostButton.setEnabled(enabled);
		protocolLabel.setEnabled(enabled);
		protocolCombo.setEnabled(enabled);
		portLabel.setEnabled(enabled);
		portCombo.setEnabled(enabled);
		urlText.setEnabled(enabled);
		editURLButton.setEnabled(enabled);
		userLabel.setEnabled(enabled);
		userText.setEnabled(enabled);
		passwordLabel.setEnabled(enabled);
		passwordText.setEnabled(enabled);
		soapVerionLabel.setEnabled(enabled);
		soap11Button.setEnabled(enabled);
		soap12Button.setEnabled(enabled);
	}

	private boolean isEngineValid(RemoteEngineElement element) {
		boolean isValid = true;
		String url = element.getUrl();
		for (RemoteEngineElement ree : enginesList) {
			if (ree != element && url.equals(ree.getUrl())) {
				isValid = false;
				MessageDialog.openError(getShell(),
						Messages.getString("BxMainTab.existErrorDialog.title"), Messages.getString("BxMainTab.existErrorDialog.message")); //$NON-NLS-1$ //$NON-NLS-2$
				resetEngine();
				break;
			}
		}
		return isValid;
	}

	private void addEngine() {
		RemoteEngineElement element = new RemoteEngineElement();
		enginesList.add(element);
		availableEngineViewer.setInput(enginesList);
		availableEngineViewer.setSelection(new StructuredSelection(element));
		availableEngineViewer.refresh();
		setFormEnabled(true);
		resetEngine();
	}

	private void resetEngine() {
		typeCombo.setItems(ModelTypeManager.getModelNames());
		hostNameCombo.setText(""); //$NON-NLS-1$
		protocolCombo.setItems(DebugCoreActivator.getDefault().getConnectionTypes());
		portCombo.setText(""); //$NON-NLS-1$
		urlText.setText(""); //$NON-NLS-1$
		userText.setText(""); //$NON-NLS-1$
		passwordText.setText(""); //$NON-NLS-1$
		soap11Button.setSelection(true);
		soap12Button.setSelection(false);
	}

	private void setRemoteEngineElement(RemoteEngineElement element) {
		element.setType(getType());
		element.setHostName(getHostName());
		element.setProtocol(getProtocol());
		element.setPort(getPort());
		element.setEndpointURI(getEndpointURI());
		element.setUsername(getUsername());
		element.setPassword(getPassword());
		element.setUrl(getCurrentURL());
		element.setSoapVersion(getSoapVersion());
	}

	private void resetRemoteEngineElement(RemoteEngineElement element) {
		element.setType(""); //$NON-NLS-1$
		element.setHostName(""); //$NON-NLS-1$
		element.setProtocol(""); //$NON-NLS-1$
		element.setPort(""); //$NON-NLS-1$
		element.setEndpointURI(""); //$NON-NLS-1$
		element.setUsername(""); //$NON-NLS-1$
		element.setPassword(""); //$NON-NLS-1$
		element.setUrl(""); //$NON-NLS-1$
		element.setSoapVersion(WSConstants.SOAP11); 
	}

	private void saveEngine() {
		IStructuredSelection selection = (IStructuredSelection) availableEngineViewer.getSelection();
		RemoteEngineElement element = (RemoteEngineElement) selection.getFirstElement();
		if (element == null) {
			return;
		} else {
			setRemoteEngineElement(element);
			if (!isEngineValid(element)) {
				resetRemoteEngineElement(element);
			}
			availableEngineViewer.setSelection(new StructuredSelection(element));
			availableEngineViewer.refresh();
			refreshButtons();
			setValid(!enginesList.isEmpty());
		}

	}

	private void removeEngine() {
		IStructuredSelection selection = (IStructuredSelection) availableEngineViewer.getSelection();
		for (Object obj : selection.toList()) {
			RemoteEngineElement element = (RemoteEngineElement) obj;
			if (element != null && enginesList.contains(element)) {
				enginesList.remove(element);
			}
		}
		availableEngineViewer.refresh();
		setFormEnabled(false);
		resetEngine();
		refreshButtons();
		setValid(!enginesList.isEmpty());
	}

	private void refreshButtons() {
		removeEngineButton.setEnabled(!enginesList.isEmpty());
		connectButton.setEnabled(false);
		this.isValid = true;
		if (enginesList.isEmpty()) {
			getLaunchConfigurationDialog();
		}
	}

	private void doConnection() {
		UIJob job = new UIJob(Messages.getString("BxMainTab.connecting")) { //$NON-NLS-1$
			public IStatus runInUIThread(IProgressMonitor monitor) {
				Cursor old = BxMainTab.this.getShell().getCursor();
				try {
					Cursor cursor = Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT);
					BxMainTab.this.getShell().setCursor(cursor);
					Map<String, Object> connectionParameters = new HashMap<String, Object>();
					Map<String, String> hostInfoMap = genHostInfoMap();
					connectionParameters.put(IBxLaunchAttribute.MODEL_TYPE, getType());
					connectionParameters.put(IBxLaunchAttribute.ENGINE_ADDRESS, hostInfoMap.get(IBxLaunchAttribute.ENGINE_ADDRESS));
					connectionParameters.put(IBxLaunchAttribute.ENGINE_PORT, hostInfoMap.get(IBxLaunchAttribute.ENGINE_PORT));
					connectionParameters.put(IBxLaunchAttribute.ENDPOINT_URI, hostInfoMap.get(IBxLaunchAttribute.ENDPOINT_URI));
					connectionParameters.put(IBxLaunchAttribute.URL, hostInfoMap.get(IBxLaunchAttribute.URL));
					connectionParameters.put(IBxLaunchAttribute.USER_NAME, getUsername());
					connectionParameters.put(IBxLaunchAttribute.PASSWORD, getPassword());
					connectionParameters.put(IBxLaunchAttribute.SOAP_VERSION, getSoapVersion());

					IConnectionFactory connectionFactory = ConnectionFactoryManager.getConnectionFactory(hostInfoMap.get(IBxLaunchAttribute.PROTOCOL));
					IConnection connection = connectionFactory.createConnection(connectionParameters);
					if (connection.isValid()) {
						connection.connect();
						BxMainTab.this.getShell().setCursor(old);
						MessageDialog.openInformation(connectButton.getShell(),
								Messages.getString("BxMainTab.successTitle"), Messages.getString("BxMainTab.successDescription")); //$NON-NLS-1$ //$NON-NLS-2$
						connection.disconnect();
						saveEngine();
					} else {
						BxMainTab.this.getShell().setCursor(old);
						String message = Messages.getString("BxMainTab.failedDescription"); //$NON-NLS-1$
						ErrorDialog.openError(null, Messages.getString("BxMainTab.failedTitle"), //$NON-NLS-1$
								message, DebugUIActivator.newStatus(null, Messages.getString("BxMainTab.invaildURL"))); //$NON-NLS-1$
						connectButton.setEnabled(true);
					}

				} catch (CoreException e) {
					BxMainTab.this.getShell().setCursor(old);
					String message = Messages.getString("BxMainTab.failedDescription"); //$NON-NLS-1$
					ErrorDialog.openError(null, Messages.getString("BxMainTab.failedTitle"), message, //$NON-NLS-1$
							e.getStatus());
					setValid(false);
					connectButton.setEnabled(true);
				} catch (MalformedURLException e) {
					BxMainTab.this.getShell().setCursor(old);
					String message = Messages.getString("BxMainTab.failedDescription"); //$NON-NLS-1$
					ErrorDialog.openError(null, Messages.getString("BxMainTab.failedTitle"), message, //$NON-NLS-1$
							DebugUIActivator.newStatus(e, e.getMessage()));
					setValid(false);
					connectButton.setEnabled(true);
				}
				// updateLaunchConfigurationDialog();
				return Status.OK_STATUS;
			}
		};
		job.schedule();
	}

	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
	}

	public void initializeFrom(ILaunchConfiguration configuration) {
		enginesList.clear();
		try {
			int engineSize = configuration.getAttribute(IBxLaunchAttribute.ENGINE_SIZE, new Integer(0));
			for (int i = 0; i < engineSize; i++) {
				String modelType = configuration.getAttribute(IBxLaunchAttribute.MODEL_TYPE + i, ""); //$NON-NLS-1$
				String hostName = configuration.getAttribute(IBxLaunchAttribute.ENGINE_ADDRESS + i, ""); //$NON-NLS-1$
				String port = configuration.getAttribute(IBxLaunchAttribute.ENGINE_PORT + i, ""); //$NON-NLS-1$
				String protocol = configuration.getAttribute(IBxLaunchAttribute.CONNECTIONFACTORY_TYPE + i, ""); //$NON-NLS-1$
				String endpointURI = configuration.getAttribute(IBxLaunchAttribute.ENDPOINT_URI + i, ""); //$NON-NLS-1$
				String url = configuration.getAttribute(IBxLaunchAttribute.URL + i, ""); //$NON-NLS-1$
				String username = configuration.getAttribute(IBxLaunchAttribute.USER_NAME + i, ""); //$NON-NLS-1$
				String password = configuration.getAttribute(IBxLaunchAttribute.PASSWORD + i, ""); //$NON-NLS-1$
				String soapVersion = configuration.getAttribute(IBxLaunchAttribute.SOAP_VERSION + i, ""); //$NON-NLS-1$
				RemoteEngineElement element = new RemoteEngineElement(modelType, hostName, protocol, port, endpointURI, url, username, password,
						soapVersion);
				if (modelType != null && !"".equals(modelType)) { //$NON-NLS-1$
					enginesList.add(element);
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		if (enginesList != null) {
			availableEngineViewer.setInput(enginesList);
			availableEngineViewer.refresh();
			refreshButtons();
			setValid(!enginesList.isEmpty());
		} else {
			String message = Messages.getString("BxMainTab.cannotReadSettings"); //$NON-NLS-1$
			ErrorDialog.openError(null, Messages.getString("BxMainTab.errorTitle"), message, //$NON-NLS-1$
					DebugUIActivator.newStatus(new Exception(), message));
		}

	}

	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		if (isValid) {
			for (int i = 0; i < enginesList.size(); i++) {
				RemoteEngineElement element = enginesList.get(i);
				configuration.setAttribute(IBxLaunchAttribute.MODEL_TYPE + i, element.getType());
				configuration.setAttribute(IBxLaunchAttribute.CONNECTIONFACTORY_TYPE + i, element.getProtocol());
				configuration.setAttribute(IBxLaunchAttribute.ENGINE_ADDRESS + i, element.getHostName());
				configuration.setAttribute(IBxLaunchAttribute.ENGINE_PORT + i, element.getPort());
				configuration.setAttribute(IBxLaunchAttribute.ENDPOINT_URI + i, element.getEndpointURI());
				configuration.setAttribute(IBxLaunchAttribute.URL + i, element.getUrl());
				configuration.setAttribute(IBxLaunchAttribute.USER_NAME + i, element.getUsername());
				configuration.setAttribute(IBxLaunchAttribute.PASSWORD + i, element.getPassword());
				configuration.setAttribute(IBxLaunchAttribute.SOAP_VERSION + i, element.getSoapVersion());
				if (hList.contains(element)) {
					hList.remove(element);
				}
				hList.add(0, element);
				LaunchUtil.saveRemoteEngines(hList);
			}
		}
		configuration.setAttribute(IBxLaunchAttribute.ENGINE_SIZE, enginesList.size());
	}

	private boolean isValid = false;

	private void setValid(boolean valid) {
		this.isValid = this.isValid && valid;
		updateLaunchConfigurationDialog();
	}

	public boolean isValid(ILaunchConfiguration launchConfig) {
		return isValid;
	}

	public boolean canSave() {
		return true;
	}

	public String getName() {
		return Messages.getString("BxMainTab.mainTabLabel"); //$NON-NLS-1$
	}

	public Image getImage() {
		return DebugUIActivator.getRegisteredImage(DebugUIActivator.IMG_SERVER);
	}

	private String getType() {
		return ModelTypeManager.getModelType(typeCombo.getText());
	}
	
	private String getHostName() {
		return hostNameCombo.getText().trim();
	}

	private String getProtocol() {
		return protocolCombo.getText().trim();
	}

	private String getPort() {
		return portCombo.getText().trim();
	}
	
	protected String getCurrentURL(){
		return urlText.getText().trim();
	}

	private String getEndpointURI() {
		String endpointURI = NodeDefinitionFactoryManager.getDefaultEndpointURI(getType());
		String urlValue = getCurrentURL();
		try {
			URL url = new URL(urlValue);
			endpointURI = url.getPath();
		} catch (MalformedURLException e) {
		}
		return endpointURI;
	}

	private String getUsername() {
		return userText.getText().trim();
	}

	private String getPassword() {
		return passwordText.getText().trim();
	}

	private String getSoapVersion() {
		if (soap11Button.getSelection()) {
			return WSConstants.SOAP11;
		}
		return WSConstants.SOAP12;
	}
	
	private void radioChanged() {
		connectButton.setEnabled(true);
	}
	
	private Map<String, String> genHostInfoMap() throws MalformedURLException {
		Map<String, String> hostInfoMap = new HashMap<String, String>();
		String url = getCurrentURL();
		URL currentURL = new URL(url);
		String protocol = currentURL.getProtocol();
		String host = currentURL.getHost();
		int port = currentURL.getPort();
		String portValue = String.valueOf(port == -1? 80 : port);
		String path = currentURL.getPath();
		hostInfoMap.put(IBxLaunchAttribute.PROTOCOL, protocol);
		hostInfoMap.put(IBxLaunchAttribute.ENGINE_ADDRESS, host);
		hostInfoMap.put(IBxLaunchAttribute.ENGINE_PORT, portValue);
		hostInfoMap.put(IBxLaunchAttribute.ENDPOINT_URI, path);
		hostInfoMap.put(IBxLaunchAttribute.URL, url);
		return hostInfoMap;
	}
	
	class URLValidator implements IInputValidator {

		@Override
		public String isValid(String newText) {
			boolean valid = true;
			try {
				URL url = new URL(newText);
				if ("".equals(url.getHost())) { //$NON-NLS-1$
					valid = false;
				} else if (!"".equals(url.getAuthority()) && url.getAuthority().endsWith(":")) { //$NON-NLS-1$ //$NON-NLS-2$
					valid = false;
				}
			} catch (MalformedURLException e) {
				valid = false;
			}
			if (!valid) {
				return Messages.getString("BxMainTab.editURLDialog.error"); //$NON-NLS-1$
			}
			return null;
		}

	}

}
