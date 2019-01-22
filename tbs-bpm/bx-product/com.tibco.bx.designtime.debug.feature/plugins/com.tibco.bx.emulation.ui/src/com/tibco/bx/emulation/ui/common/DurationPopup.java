package com.tibco.bx.emulation.ui.common;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Spinner;

import com.tibco.bx.emulation.ui.Messages;

public class DurationPopup extends Window implements FocusListener {

	private Button positiveCheck;
	private Button okButton;
	private Spinner yearSpinner;
	private Spinner monthSpinner;
	private Spinner daySpinner;
	private Spinner hourSpinner;
	private Spinner minuteSpinner;
	private Spinner secondSpinner;
	private Control control;

	private Duration duration;

	public DurationPopup(Control control) {
		super(control.getShell());
		this.control = control;
		setShellStyle(SWT.ON_TOP | SWT.TOOL | SWT.APPLICATION_MODAL | SWT.CLOSE);
		setBlockOnOpen(true);
	}

	@Override
	protected Control createContents(Composite paramComposite) {
		Composite localComposite = new Composite(paramComposite, SWT.NONE);
		yearSpinner = createPeriodTextControl(localComposite, Messages.getString("DurationPopup.yearLabel")); //$NON-NLS-1$
		hourSpinner = createPeriodTextControl(localComposite, Messages.getString("DurationPopup.hourLabel")); //$NON-NLS-1$
		monthSpinner = createPeriodTextControl(localComposite, Messages.getString("DurationPopup.monthLabel")); //$NON-NLS-1$
		minuteSpinner = createPeriodTextControl(localComposite, Messages.getString("DurationPopup.minLabel")); //$NON-NLS-1$
		daySpinner = createPeriodTextControl(localComposite, Messages.getString("DurationPopup.dayLabel")); //$NON-NLS-1$
		secondSpinner = createPeriodTextControl(localComposite, Messages.getString("DurationPopup.secLabel")); //$NON-NLS-1$
		new Label(localComposite, SWT.NONE).setText(Messages.getString("DurationPopup.isPositiveLabel")); //$NON-NLS-1$
		positiveCheck = new Button(localComposite, SWT.CHECK);
		okButton = new Button(localComposite, SWT.PUSH);
		okButton.setText(Messages.getString("DurationPopup.okButtonText")); //$NON-NLS-1$
		GridData gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.horizontalSpan = 2;
		gd.horizontalAlignment = SWT.END;
		okButton.setLayoutData(gd);
		okButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				getCurrentDuration();
			}

		});
		initDuration(duration);
		localComposite.setTabList(new Control[] { yearSpinner, monthSpinner, daySpinner, hourSpinner, minuteSpinner, secondSpinner, positiveCheck,
				okButton });
		localComposite.pack(true);
		return localComposite;
	}

	private Spinner createPeriodTextControl(Composite parent, String label) {
		GridLayout layout = new GridLayout(4, false);
		parent.setLayout(layout);
		Label lab = new Label(parent, SWT.NONE);
		lab.setText(label);
		
		Spinner spinner = new Spinner(parent, SWT.BORDER | SWT.FULL_SELECTION);
		spinner.setDigits(0);
		spinner.setIncrement(1);
		spinner.setPageIncrement(5);
		spinner.setMinimum(0);
		spinner.setMaximum(Integer.MAX_VALUE);
		spinner.setLayoutData(new GridData());
		spinner.addFocusListener(this);
		return spinner;
	}

	private void getCurrentDuration() {
		try {
			duration = DatatypeFactory.newInstance().newDuration(isPositive(), yearSpinner.getSelection(), monthSpinner.getSelection(),
					daySpinner.getSelection(), hourSpinner.getSelection(), minuteSpinner.getSelection(), secondSpinner.getSelection());
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		close();
	}

	public Button getPositiveCheck() {
		return positiveCheck;
	}

	public void setPositiveCheck(int positive) {
		positiveCheck.setSelection(positive >= 0);
	}

	private boolean isPositive() {
		return positiveCheck.getSelection();
	}

	public void setDuration(Duration durationDate) {
		duration = durationDate;
	}

	public Duration getSelection() {
		return duration;
	}

	@Override
	protected Point getInitialLocation(Point paramPoint) {
		Point localPoint = null;
		if (control != null) {
			Rectangle localRectangle = control.getBounds();
			if (control.getParent() != null) {
				localPoint = control.getParent().toDisplay(localRectangle.x, localRectangle.y);
				localPoint = new Point(localPoint.x + localRectangle.width, localPoint.y);
			}
		}
		return localPoint != null ? localPoint : super.getInitialLocation(paramPoint);
	}

	@Override
	protected Layout getLayout() {
		FillLayout localFillLayout = new FillLayout();
		localFillLayout.marginHeight = 0;
		localFillLayout.marginHeight = 0;
		return localFillLayout;
	}

	private void initDuration(Duration duration) {
		setPositiveCheck(duration.getSign());
		yearSpinner.setSelection(duration.getYears());
		monthSpinner.setSelection(duration.getMonths());
		daySpinner.setSelection(duration.getDays());
		hourSpinner.setSelection(duration.getHours());
		minuteSpinner.setSelection(duration.getMinutes());
		secondSpinner.setSelection(duration.getSeconds());
		yearSpinner.setFocus();
	}

	@Override
	public void focusGained(FocusEvent event) {
	}

	@Override
	public void focusLost(FocusEvent event) {
		if (event.getSource() instanceof Spinner) {
			Spinner spinner = (Spinner) event.getSource();
			String value = spinner.getText().trim();
			if ("".equals(value)) { //$NON-NLS-1$
				spinner.setSelection(0);
			} else {
				spinner.setSelection(Integer.parseInt(value));
			}
		}
	}

}
