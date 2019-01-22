/**
 * 
 */
package com.tibco.xpd.gmf.extensions.hoverinfo;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.ui.PlatformUI;

/**
 * The Tooltip figure, it ask HoverProvider for the info every time the tool-tip
 * is displayed.
 * 
 * @author wzurek
 */
public class TooltipFigure extends Figure {

	public HoverDescriptionFigure fig;

	public HoverProvider provider;

	private static final String PROPERTY_VALUE = "property-value"; //$NON-NLS-1$

	private static final String PROPERTY_NAME = "property-name"; //$NON-NLS-1$

	private static final String TEXT = "text"; //$NON-NLS-1$

	private static final String TITLE = "title"; //$NON-NLS-1$

	/** fonts used by the figure */
	private static FontRegistry registry;
	static {
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			public void run() {
				Font df = JFaceResources.getDialogFont();
				FontData fds = df.getFontData()[0];

				FontData titleFont = new FontData();
				titleFont.setName(fds.getName());
				titleFont.setLocale(fds.getLocale());
				titleFont.setHeight(8);
				titleFont.setStyle(SWT.BOLD);

				FontData textFont = new FontData();
				textFont.setName(fds.getName());
				textFont.setLocale(fds.getLocale());
				textFont.setHeight(fds.getHeight());
				textFont.setStyle(SWT.NORMAL);

				FontData propNameFont = new FontData();
				propNameFont.setName(fds.getName());
				propNameFont.setLocale(fds.getLocale());
				propNameFont.setHeight(fds.getHeight());
				propNameFont.setStyle(SWT.BOLD);

				FontData propValueFont = new FontData();
				propValueFont.setName(fds.getName());
				propValueFont.setLocale(fds.getLocale());
				propValueFont.setHeight(fds.getHeight());
				propValueFont.setStyle(SWT.NORMAL);

				registry = new FontRegistry();
				registry.put(TITLE, new FontData[] { titleFont });
				registry.put(TEXT, new FontData[] { textFont });
				registry.put(PROPERTY_NAME, new FontData[] { propNameFont });
				registry.put(PROPERTY_VALUE, new FontData[] { propValueFont });

			}
		});
	}

	public TooltipFigure(HoverProvider provider) {
		this.provider = provider;
		fig = new HoverDescriptionFigure();
		fig.setTitleFont(registry.get(TITLE));
		fig.setTextFont(registry.get(TEXT));
		fig.setPropNameFont(registry.get(PROPERTY_NAME));
		fig.setPropValueFont(registry.get(PROPERTY_VALUE));
	}

	public Dimension getPreferredSize(int wHint, int hHint) {
		fig.setInfo(provider.getHoverInfo());
		return fig.getPreferredSize();
	}

	public void paint(Graphics graphics) {
		fig.setInfo(provider.getHoverInfo());
		fig.paint(graphics);
	}

	public void setBounds(Rectangle rect) {
		fig.setBounds(rect);
	}

	public Rectangle getBounds() {
		return fig.getBounds();
	}
}
