package com.tibco.bx.debug.ui.propertypages;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;

import com.tibco.bx.debug.api.ConditionLanguage;
import com.tibco.bx.debug.core.models.BxBreakpoint;
import com.tibco.bx.debug.ui.DebugUIActivator;
import com.tibco.bx.debug.ui.Messages;
/**
 * Property page for configuring BxBreakpoints.
 */
public class BxBreakpointPage extends PropertyPage {
	
	protected Button fEnabledButton;
	protected Combo fLangPolicy;
	Button fEnableConditionButton;
	BreakpointConditionEditor fConditionEditor;
	protected List fErrorMessages= new ArrayList();
	
	private static final String XPATH = "XPath"; //$NON-NLS-1$
	private static final String JAVASCRIPT = "JavaScript"; //$NON-NLS-1$
	private static final String EMPTY_STRING = ""; //$NON-NLS-1$
	
	/**
	 * Store the breakpoint properties.
	 * @see org.eclipse.jface.preference.IPreferencePage#performOk()
	 */
	public boolean performOk() {
		IWorkspaceRunnable wr = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				doStore();
			}
		};
		try {
			ResourcesPlugin.getWorkspace().run(wr, null, 0, null);
		} catch (CoreException e) {
			DebugUIActivator.log(e);
		}
		return super.performOk();
	}
	
	/**
	 * Stores the values configured in this page. This method
	 * should be called from within a workspace runnable to
	 * reduce the number of resource deltas.
	 */
	protected void doStore() throws CoreException {
		BxBreakpoint breakpoint= getBreakpoint();
		boolean enabled= fEnabledButton.getSelection();
		breakpoint.setEnabled(enabled);
		//String language = fLangPolicy.getText();
		boolean enableCondition= fEnableConditionButton.getSelection();
		String condition = fConditionEditor.getCondition();
		if (breakpoint.isConditionEnabled() != enableCondition) {
			breakpoint.setConditionEnabled(enableCondition);
		}
		if (!condition.equals(breakpoint.getCondition())) {
			breakpoint.setCondition(condition);
		}
	}

	/**
	 * Creates the labels and editors displayed for the breakpoint.
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createContents(Composite parent) {
		noDefaultAndApplyButton();
		Composite mainComposite= createComposite(parent, 1);
		try {
			createLabels(mainComposite);
			createEnabledButton(mainComposite);
			createLangPolicyEditor(mainComposite);
			createConditionEditor(mainComposite);
		} catch (CoreException e) {
		}
		setValid(true);
		try {
			getShell().addShellListener(new ShellListener() {
				public void shellActivated(ShellEvent e) {
					Shell shell = (Shell) e.getSource();
					shell.setText(""); //$NON-NLS-1$
					shell.removeShellListener(this);
				}

				public void shellClosed(ShellEvent e) {
				}

				public void shellDeactivated(ShellEvent e) {
				}

				public void shellDeiconified(ShellEvent e) {
				}

				public void shellIconified(ShellEvent e) {
				}
			});
        } catch (Exception e) {
        }
		return mainComposite;
	}
	
	/**
	 * Creates the labels displayed for the breakpoint.
	 * 
	 * @param parent
	 * @throws CoreException
	 */
	protected void createLabels(Composite parent) throws CoreException {
		BxBreakpoint breakpoint= (BxBreakpoint) getElement();
		Composite labelComposite= createComposite(parent, 2);
		String name = (String)breakpoint.getMarker().getAttribute(IMarker.MESSAGE);
		if (name != null) {
			createLabel(labelComposite, Messages.getString("BxBreakpointPage.memberLabel")); //$NON-NLS-1$
			createLabel(labelComposite, name);
		}
	}

	/**
	 * Creates the editor for configuring the suspend policy (suspend
	 * VM or suspend thread) of the breakpoint.
	 * @param parent the composite in which the suspend policy
	 * 		editor will be created.
	 */
	private void createLangPolicyEditor(Composite parent) throws CoreException {
		BxBreakpoint breakpoint= getBreakpoint();
		Composite comp = createComposite(parent, 2);
		createLabel(comp, Messages.getString("BxBreakpointPage.languageLabel"));  //$NON-NLS-1$
		//boolean suspendThread= breakpoint.getSuspendPolicy() == BxBreakpoint.SUSPEND_THREAD;
		fLangPolicy = new Combo(comp, SWT.BORDER|SWT.READ_ONLY);
		fLangPolicy.add(JAVASCRIPT);
		fLangPolicy.add(XPATH);
		if(breakpoint.getConditionLanguage() == null || breakpoint.getConditionLanguage().equals(ConditionLanguage.JSCRIPT)){
			fLangPolicy.select(0);
		}else{
			fLangPolicy.select(1);
		}

	}

	/**
	 * Creates the button to toggle enablement of the breakpoint
	 * @param parent
	 * @throws CoreException
	 */
	protected void createEnabledButton(Composite parent) throws CoreException {
		fEnabledButton= createCheckButton(parent, Messages.getString("BxBreakpointPage.enabledLabel"));  //$NON-NLS-1$
		fEnabledButton.setSelection(getBreakpoint().isEnabled());
	}
	
	/**
	 * Returns the breakpoint that this preference page configures
	 * @return the breakpoint this page configures
	 */
	protected BxBreakpoint getBreakpoint() {
		return (BxBreakpoint) getElement();
	}
	
	/**
	 * Creates a fully configured text editor with the given initial value
	 * @param parent
	 * @param initialValue
	 * @return the configured text editor
	 */
	protected Text createText(Composite parent, String initialValue) {
		Composite textComposite = createComposite(parent, 2);
		Text text= new Text(textComposite, SWT.SINGLE | SWT.BORDER);
		text.setText(initialValue);
		text.setFont(parent.getFont());
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return text;
	}
	
	/**
	 * Creates a fully configured composite with the given number of columns
	 * @param parent
	 * @param numColumns
	 * @return the configured composite
	 */
	protected Composite createComposite(Composite parent, int numColumns) {
		Composite composit= new Composite(parent, SWT.NONE);
		composit.setFont(parent.getFont());
		GridLayout layout= new GridLayout();
		layout.numColumns= numColumns;
		layout.marginWidth= 0;
		layout.marginHeight= 0;
		composit.setLayout(layout);
		composit.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return composit;
	}

	/**
	 * Creates a fully configured check button with the given text.
	 * @param parent the parent composite
	 * @param text the label of the returned check button
	 * @return a fully configured check button
	 */
	protected Button createCheckButton(Composite parent, String text) {
		Button button= new Button(parent, SWT.CHECK | SWT.LEFT);
		button.setText(text);
		button.setFont(parent.getFont());
		button.setLayoutData(new GridData());
		return button;
	}

	/**
	 * Creates a fully configured label with the given text.
	 * @param parent the parent composite
	 * @param text the test of the returned label
	 * @return a fully configured label
	 */
	protected Label createLabel(Composite parent, String text) {
		Label label= new Label(parent, SWT.NONE);
		label.setText(text);
		label.setFont(parent.getFont());
		label.setLayoutData(new GridData());
		return label;
	}

	/**
	 * Creates the controls that allow the user to specify the breakpoint's
	 * condition
	 * @param parent the composite in which the condition editor should be created
	 * @throws CoreException if an exception occurs accessing the breakpoint
	 */
	private void createConditionEditor(Composite parent) throws CoreException {
		BxBreakpoint breakpoint = (BxBreakpoint) getBreakpoint();
		String label = Messages.getString("BxBreakpointPage.enableConditionLabel"); //$NON-NLS-1$
		Composite conditionComposite= new Group(parent, SWT.NONE);
		conditionComposite.setFont(parent.getFont());
		conditionComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		conditionComposite.setLayout(new GridLayout());
		fEnableConditionButton= createCheckButton(conditionComposite, label);
		fEnableConditionButton.setSelection(breakpoint.isConditionEnabled());
		fEnableConditionButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				fConditionEditor.setEnabled(fEnableConditionButton.getSelection());
			}
		});
		fConditionEditor = new BreakpointConditionEditor(conditionComposite, this);
		fConditionEditor.setEnabled(fEnableConditionButton.getSelection());
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.DialogPage#convertHeightInCharsToPixels(int)
	 */
	public int convertHeightInCharsToPixels(int chars) {
		return super.convertHeightInCharsToPixels(chars);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.DialogPage#convertWidthInCharsToPixels(int)
	 */
	public int convertWidthInCharsToPixels(int chars) {
		return super.convertWidthInCharsToPixels(chars);
	}
	
	/**
	 * Check to see if the breakpoint should be deleted.
	 */
	public boolean performCancel() {
		/*try {
			if (getBreakpoint().getMarker().getAttribute(ATTR_DELETE_ON_CANCEL) != null) {
			    // if this breakpoint is being created, delete on cancel
				getBreakpoint().delete();
			}
		} catch (CoreException e) {
			JDIDebugUIPlugin.errorDialog(PropertyPageMessages.JavaBreakpointPage_9, e.getStatus()); 
		}*/
		return super.performCancel();
	}
}
