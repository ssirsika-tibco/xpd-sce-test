package com.tibco.bx.emulation.ui.common;

import javax.xml.datatype.Duration;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.bx.debug.core.util.DateUtil;
import com.tibco.xpd.resources.ui.components.DialogCellWithClearEditor;

public class DurationCellEditor extends DialogCellWithClearEditor {

	private Duration currentDate;

	public DurationCellEditor(Composite parent) {
		super(parent);
	}

	@Override
	protected Object doGetValue() {
		if (currentDate != null) {
			return currentDate.toString();
		}
		return ""; //$NON-NLS-1$
	}

	@Override
	protected void doSetValue(Object value) {
		if (value instanceof Duration) {
			currentDate = (Duration) value;
		} else {
			currentDate = null;
		}

		super.doSetValue(currentDate != null ? currentDate.toString() : ""); //$NON-NLS-1$
	}

	@Override
	protected Object openDialogBox(Control cellEditorWindow) {
		DurationPopup popup = new DurationPopup(cellEditorWindow);
		Duration duration = currentDate;
		if (duration == null) {
			duration = DateUtil.createDuration();
		}
		popup.setDuration(duration);

		if (popup.open() == DurationPopup.OK) {
			duration = popup.getSelection();
			currentDate = duration;
		} else {
			duration = null;
		}

		return duration;
	}

	@Override
	protected Object getClearValue() {
		currentDate = null;
		return super.getClearValue();
	}

}
