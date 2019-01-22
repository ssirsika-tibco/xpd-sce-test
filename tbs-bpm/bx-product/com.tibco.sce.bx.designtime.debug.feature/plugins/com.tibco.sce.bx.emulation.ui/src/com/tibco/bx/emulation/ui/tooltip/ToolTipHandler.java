package com.tibco.bx.emulation.ui.tooltip;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Widget;

public class ToolTipHandler {

	private ILabelProvider tipLabelProvider;
	private Shell tipShell;
	protected Label tipLabelImage, tipLabelText;
	private Widget hoverWidget;
	private Point hoverPosition;
	
	/**
	 * the control can only be a tree or a table
	 * @param control
	 * @param labelProvider
	 */
	public ToolTipHandler(Control control, ILabelProvider labelProvider){
		this.tipLabelProvider = labelProvider;
		Shell parentShell = control.getShell();
		tipShell = new Shell(parentShell.getDisplay(), SWT.ON_TOP | SWT.TOOL);
		createControls(tipShell);
		setListeners(control);
	}
	
	protected Control createControls(Composite composite) {
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		composite.setLayout(layout);
		final Display display = composite.getDisplay();
		composite.setBackground(display
				.getSystemColor(SWT.COLOR_INFO_BACKGROUND));
		tipLabelImage = new Label(composite, SWT.NONE);
		tipLabelImage.setForeground(display
				.getSystemColor(SWT.COLOR_INFO_FOREGROUND));
		tipLabelImage.setBackground(display
				.getSystemColor(SWT.COLOR_INFO_BACKGROUND));
		tipLabelImage.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
				| GridData.VERTICAL_ALIGN_CENTER));

		tipLabelText = new Label(composite, SWT.NONE);
		tipLabelText.setForeground(display
				.getSystemColor(SWT.COLOR_INFO_FOREGROUND));
		tipLabelText.setBackground(display
				.getSystemColor(SWT.COLOR_INFO_BACKGROUND));
		tipLabelText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
				| GridData.VERTICAL_ALIGN_CENTER));

		return null;
	}
	
	protected void setListeners(final Control control) {
		Listener listener = new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Dispose:
					if (tipShell != null)
						tipShell.dispose();
					break;
				case SWT.MouseExit:
				case SWT.KeyDown:
				case SWT.MouseDown:
				case SWT.MouseMove:
					if (tipShell.isVisible())
						tipShell.setVisible(false);
					hoverWidget = null;
					break;
				case SWT.MouseHover:
					Point point = new Point(e.x, e.y);
					Widget widget = e.widget;
					if (widget instanceof Table) {
						Table table = (Table) widget;
						widget = table.getItem(point);
					}
					if (widget instanceof Tree) {
						Tree tree = (Tree) widget;
						widget = tree.getItem(point);
					}
					if (widget == null) {
						tipShell.setVisible(false);
						hoverWidget = null;
						return;
					}
					if (widget == hoverWidget)
						return;

					hoverWidget = widget;
					setLables(widget);
					
					hoverPosition = control.toDisplay(point);
					tipShell.pack();
					setHoverLocation(tipShell, hoverPosition);
					tipShell.setVisible(true);
				}
			}
		};
		
		control.addListener(SWT.Dispose, listener);
		control.addListener(SWT.KeyDown, listener);
		control.addListener(SWT.MouseDown, listener);
		control.addListener(SWT.MouseExit, listener);
		control.addListener(SWT.MouseMove, listener);
		control.addListener(SWT.MouseHover, listener);
	}

	private void setLables(Widget widget){
		Object element = widget.getData();
		String text = tipLabelProvider.getText(element);
		Image image = tipLabelProvider.getImage(element);
		tipLabelText.setText(text != null ? text : ""); //$NON-NLS-1$
		tipLabelImage.setImage(image);
	}
	
	private void setHoverLocation(Shell shell, Point position) {
		Rectangle displayBounds = shell.getDisplay().getBounds();
		Rectangle shellBounds = shell.getBounds();
		shellBounds.x = Math.max(Math.min(position.x, displayBounds.width
				- shellBounds.width), 0);
		shellBounds.y = Math.max(Math.min(position.y + 16, displayBounds.height
				- shellBounds.height), 0);
		shell.setBounds(shellBounds);
	}
	
}
