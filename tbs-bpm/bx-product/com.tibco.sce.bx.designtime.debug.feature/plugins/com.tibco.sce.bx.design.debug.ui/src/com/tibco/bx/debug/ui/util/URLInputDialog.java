package com.tibco.bx.debug.ui.util;

import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wst.wsdl.Operation;

import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.Messages;
import com.tibco.bx.debug.core.ws.finder.FinderFactory;
import com.tibco.bx.debug.core.ws.util.XMLUtils;

public class URLInputDialog extends InputDialog implements ModifyListener {

	private Button validateButton;
	private Button okButton;
	private Button cancelButton;
	private Text urlText;
	private Label infoLabel;

	private String wsdlUrl;
	private EObject startActivity;
	private Operation operation;

	private static final String DIALOG_TITLE = Messages.getString("URLInputDialog.dialog.title"); //$NON-NLS-1$
	private static final String DIALOG_MESSAGE = Messages.getString("URLInputDialog.dialog.message"); //$NON-NLS-1$
	private static final String INVALIAD_URL_MESSAGE = Messages.getString("URLInputDialog.validator.text"); //$NON-NLS-1$
	private static final String URL_ACCESS_FAILED_MESSAGE = Messages.getString("URLInputDialog.urlfailed.text"); //$NON-NLS-1$
	private static final String URL_ACCESS_SUCESS_MESSAGE = Messages.getString("URLInputDialog.urlsuccess.text"); //$NON-NLS-1$
	private static final String VALIDATING_MESSAGE = Messages.getString("URLInputDialog.validation.text"); //$NON-NLS-1$
	
	public URLInputDialog(Shell parentShell, String dialogTitle, String dialogMessage, String initialValue, IInputValidator validator) {
		super(parentShell, dialogTitle, dialogMessage, initialValue, validator);
	}

	public URLInputDialog(Shell parentShell) {
		super(parentShell, DIALOG_TITLE, DIALOG_MESSAGE, "", null); //$NON-NLS-1$
	}

	public EObject getStartActivity() {
		return startActivity;
	}

	public void setStartActivity(EObject startActivity) {
		this.startActivity = startActivity;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite root = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		setLayout(layout, 10);
		root.setLayout(layout);
		Label messageLabel = new Label(root, SWT.NONE);
		messageLabel.setText(DIALOG_MESSAGE);
		urlText = new Text(root, SWT.BORDER | SWT.SINGLE);
		urlText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		urlText.addModifyListener(this);
		infoLabel = new Label(root, SWT.NONE);
		infoLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		infoLabel.setText(INVALIAD_URL_MESSAGE);
		return root;
	}

	private void setLayout(GridLayout layout, int spacing) {
		layout.horizontalSpacing = spacing;
		layout.verticalSpacing = spacing;
		layout.marginHeight = spacing;
		layout.marginWidth = spacing;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		GridLayout layout = new GridLayout(3, true);
		setLayout(layout, 5);
		parent.setLayout(layout);
		validateButton = new Button(parent, SWT.PUSH);
		validateButton.setText(Messages.getString("URLInputDialog.button.validate.label")); //$NON-NLS-1$
		validateButton.setEnabled(false);
		validateButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		validateButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
			}

			@Override
			public void widgetSelected(SelectionEvent event) {
				validatePressed(wsdlUrl);
			}

		});
		okButton = new Button(parent, SWT.PUSH);
		okButton.setText(Messages.getString("URLInputDialog.button.ok.label")); //$NON-NLS-1$
		okButton.setEnabled(false);
		okButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		okButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
			}

			@Override
			public void widgetSelected(SelectionEvent event) {
				okPressed();
			}

		});
		cancelButton = new Button(parent, SWT.PUSH);
		cancelButton.setText(Messages.getString("URLInputDialog.button.cancel.label")); //$NON-NLS-1$
		cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		cancelButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
			}

			@Override
			public void widgetSelected(SelectionEvent event) {
				cancelPressed();
			}

		});
	}

	private void validatePressed(String url) {
		try {
			validateButton.setEnabled(false);
			infoLabel.setText(VALIDATING_MESSAGE);
			Operation operation = FinderFactory.instance.findWSDLOperationFromUrl(startActivity, wsdlUrl);
			if (operation != null) {
				setOperation(operation);
				infoLabel.setText(URL_ACCESS_SUCESS_MESSAGE);
				okButton.setEnabled(operation != null);
				setWsdlUrl(XMLUtils.getWSLocation(wsdlUrl));
			}else{
				infoLabel.setText(URL_ACCESS_FAILED_MESSAGE);
			}
		} catch (CoreException e) {
			DebugCoreActivator.log(e);
		} finally{
			validateButton.setEnabled(true);
		}
	}

	@Override
	public void modifyText(ModifyEvent event) {
		wsdlUrl = urlText.getText().trim();
		if (isValidUrl(wsdlUrl)) {
			infoLabel.setText(""); //$NON-NLS-1$
			validateButton.setEnabled(true);
		} else {
			infoLabel.setText(INVALIAD_URL_MESSAGE);
			validateButton.setEnabled(false);
		}
		okButton.setEnabled(false);
	}

	private boolean isValidUrl(String value) {
		boolean valid = true;
		Pattern pattern = Pattern.compile("https?\\:\\/\\/(www\\.)?[-\\w\\d]+(\\.\\w{0,3})*(:\\d+)?(\\/[\\w\\d]+)*.*"); //$NON-NLS-1$
		if (!pattern.matcher(value).find()) {
			valid = false;
		}
		return valid;
	}

	public void setWsdlUrl(String wsdlUrl) {
		this.wsdlUrl = wsdlUrl;
	}

}
