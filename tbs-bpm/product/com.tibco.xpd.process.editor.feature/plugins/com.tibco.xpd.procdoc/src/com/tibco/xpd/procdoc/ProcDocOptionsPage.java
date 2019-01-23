/**
 * 
 */
package com.tibco.xpd.procdoc;

import java.util.Collection;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

import com.tibco.xpd.procdoc.internal.Messages;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

/**
 * @author aallway
 * 
 */
public class ProcDocOptionsPage extends AbstractXpdWizardPage implements SelectionListener {
	private static final String OPTION_SAVE_DEFAULTS = "saveDefaults"; //$NON-NLS-1$

	private List<ProcDocOption> options;

	private boolean inCreation = false;

	private Button restoreDefaults = null;

	private Button saveDefaults = null;

	private boolean saveDefaultsOnExit = false;

	private Group optionContainer = null;



	public ProcDocOptionsPage() {
		super("ProcDocOptions"); //$NON-NLS-1$
 
		setTitle(Messages.ProcDocOptionsPage_WizardPage_title);
		setDescription(Messages.ProcDocOptionsPage_WizardPage_description);

		options = ProcDocOption.loadProcDocOptions();

		IPreferenceStore prefStore = ProcdocPlugin.getDefault()
				.getPreferenceStore();
		saveDefaultsOnExit = prefStore.getBoolean(OPTION_SAVE_DEFAULTS);

	}

	/**
	 * Return a list of the procedure doc options.
	 * 
	 * @return
	 */
	public Collection<ProcDocOption> getProcDocOptions() {
		return options;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		inCreation = true;

		try {
			Composite root = new Composite(parent, SWT.NONE);

			root.setLayout(new GridLayout(1, false));

			optionContainer = new Group(root, SWT.NONE);
			optionContainer
					.setText(Messages.ProcDocOptionsPage_OptionalOut_label);
			GridData gridData = new GridData(GridData.FILL_BOTH);
			optionContainer.setLayoutData(gridData);
			GridLayout gridLayout = new GridLayout(1, false);
			optionContainer.setLayout(gridLayout);

			for (ProcDocOption option : options) {
				Button btn = new Button(optionContainer, SWT.CHECK);
				btn.setText(option.getDescription());
				btn.setData(option.getId());
				btn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				btn.addSelectionListener(this);
			}

			Group other = new Group(root, SWT.NONE);
			other.setText(Messages.ProcDocOptionsPage_StoredPref_label);
			other.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			other.setLayout(new GridLayout(2, false));

			saveDefaults = new Button(other, SWT.CHECK);
			saveDefaults.setText(Messages.ProcDocOptionsPage_SaveDefault_label);
			gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
			saveDefaults.setLayoutData(gridData);
			saveDefaults.addSelectionListener(this);

			restoreDefaults = new Button(other, SWT.PUSH);
			restoreDefaults
					.setText(Messages.ProcDocOptionsPage_RestoreDefault_label);
			gridData = new GridData(GridData.HORIZONTAL_ALIGN_END
					| GridData.FILL_HORIZONTAL);
			restoreDefaults.setLayoutData(gridData);
			restoreDefaults.addSelectionListener(this);

			setControl(root);

		} finally {
			inCreation = false;
		}

		setControlsFromOptions();

		return;
	}

	public void widgetDefaultSelected(SelectionEvent e) {
	}

	public void widgetSelected(SelectionEvent e) {
		if (!inCreation) {
			if (e.widget == saveDefaults) {
				saveDefaultsOnExit = ((Button) e.widget).getSelection();

			} else if (e.widget == restoreDefaults) {
				// Reset our options to coded defaults.
				options = ProcDocOption.getDefaultProcDocOptions();
				saveDefaultsOnExit = false;

				// Then save them to preferences.
				forceSavePreferences();

				// Now reset the control selections.
				setControlsFromOptions();

			} else if (e.widget.getData() instanceof String) {
				ProcDocOption option = getOption((String) e.widget.getData());
				if (option != null) {
					option.setOn(((Button) e.widget).getSelection());
				}
			}
		}
	}

	private void setControlsFromOptions() {
		inCreation = true;
		try {
			Control[] optionCtrls = optionContainer.getChildren();
			for (int i = 0; i < optionCtrls.length; i++) {
				Object data = optionCtrls[i].getData();
				if (data instanceof String) {

					ProcDocOption option = getOption((String) data);
					if (option != null) {
						((Button) optionCtrls[i]).setSelection(option.isOn());
					}
				}
			}

			saveDefaults.setSelection(saveDefaultsOnExit);

		} finally {
			inCreation = false;
		}
	}

	/**
	 * Return state of given proc doc option.
	 * 
	 * @param optionId
	 * @return true if option is on.
	 */
	public ProcDocOption getOption(String optionId) {
		ProcDocOption ret = null;

		if (options != null) {
			for (ProcDocOption option : options) {
				if (option.getId().equals(optionId)) {
					ret = option;
				}
			}
		}
		return ret;
	}

	/**
	 * Save the selected options to preferences (if the save defaults is set.
	 */
	public void saveOptionsToPreferences() {
		IPreferenceStore prefStore = ProcdocPlugin.getDefault()
				.getPreferenceStore();

		if (saveDefaultsOnExit) {
			forceSavePreferences();
		}

		// Always save the 'save defaults option.
		prefStore.setValue(OPTION_SAVE_DEFAULTS, saveDefaultsOnExit);
	}

	private void forceSavePreferences() {
		for (ProcDocOption option : options) {
			ProcDocOption.saveOptionToPreferences(option);
		}
	}

}
