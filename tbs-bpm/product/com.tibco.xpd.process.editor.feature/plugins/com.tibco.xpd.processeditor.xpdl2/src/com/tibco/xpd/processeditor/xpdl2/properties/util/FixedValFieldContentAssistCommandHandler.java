/**
 * 
 */
package com.tibco.xpd.processeditor.xpdl2.properties.util;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

/**
 * Content Assist command handler for FixedValueFieldAssistHelper (picks up
 * Ctrl+Space from eclipse system and displays con tent proposals list popup).
 * 
 * @author aallway
 * 
 */
public class FixedValFieldContentAssistCommandHandler extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		Control focused = Display.getCurrent().getFocusControl();

		if (focused != null) {
			Object object = focused
					.getData(FixedValueFieldAssistHelper.FIXEDVALUE_CONTENTASSIST_FIELD_HELPER);

			if (object instanceof FixedValueFieldAssistHelper) {
				FixedValueFieldAssistHelper fixValFieldHelper = (FixedValueFieldAssistHelper) object;

				fixValFieldHelper.openProposalPopup();
			}
		}

		return null;
	}

	public boolean isEnabled() {
		boolean isContentAssistField = isFocusedContentAssistField();
		return isContentAssistField;
	}

	public boolean isHandled() {
		boolean isContentAssistField = isFocusedContentAssistField();
		return isContentAssistField;
	}

	private boolean isFocusedContentAssistField() {
		Control focused = Display.getCurrent().getFocusControl();

		if (focused != null) {
			Object isContentAssist = focused
					.getData(FixedValueFieldAssistHelper.IS_FIXEDVALUE_CONTENTASSIST_FIELD);
			if (isContentAssist instanceof Boolean) {
				if (((Boolean) isContentAssist).booleanValue()) {
					return true;
				}
			}
		}

		return false;
	}

}