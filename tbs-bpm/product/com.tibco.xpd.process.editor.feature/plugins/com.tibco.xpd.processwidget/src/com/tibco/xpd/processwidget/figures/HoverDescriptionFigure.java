/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processwidget.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.analyst.resources.xpdl2.properties.description.DocumentationURLBrowserUtil;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.policies.HoverInfo;
import com.tibco.xpd.processwidget.policies.HoverInfo.HoverInfoProperty;

/**
 * @author wzurek
 */
public class HoverDescriptionFigure extends Figure {

    class HoverLayoutManager implements LayoutManager {

        private Dimension hyperlinkSize = new Dimension();

        @Override
        public Object getConstraint(IFigure child) {
            return null;
        }

        @Override
        public Dimension getMinimumSize(IFigure container, int wHint, int hHint) {
            return getPreferredSize(container, wHint, hHint);
        }

        @Override
        public Dimension getPreferredSize(IFigure container, int wHint,
                int hHint) {
            int maxWidth = wHint;
            int height = 0;
            if (info.getTitle() != null) {
                Dimension dim =
                        FigureUtilities.getTextExtents(info.getTitle(),
                                titleFont);
                height++;
                height += dim.height + textMargin * 2;
                height++;
                maxWidth = Math.max(maxWidth, dim.width);
            }
            if (info.getText() != null) {
                Dimension dim =
                        FigureUtilities
                                .getTextExtents(info.getText(), textFont);
                height++;
                height += dim.height + textMargin * 2;
                height++;
                maxWidth = Math.max(maxWidth, dim.width + textMargin * 2);
            }
            if (info.getProperties() != null && info.getProperties().size() > 0) {
                maxName = 0;
                maxValue = 0;

                for (int i = 0; i < info.getProperties().size(); i++) {
                    HoverInfoProperty p = info.getProperty(i);
                    Dimension d1 =
                            FigureUtilities
                                    .getTextExtents(p.name != null ? p.name
                                            : p.value, propNameFont);
                    maxName = Math.max(maxName, d1.width);
                    Dimension d2 =
                            FigureUtilities
                                    .getTextExtents(p.value != null ? p.value
                                            : "", //$NON-NLS-1$
                                            propValueFont);
                    maxValue = Math.max(maxValue, d2.width);
                    height++;
                    height += Math.max(d1.height, d2.height);
                }
                maxWidth =
                        Math.max(maxWidth, maxName + maxValue + textMargin * 3);
            }

            if (hyperlinkBlockFigure.isVisible()) {
                String finalText = hyperlinkTextFigure.getNominalText();

                hyperlinkSize = hyperlinkTextFigure.getTextExtents(finalText);

                int width = hyperlinkSize.width + (2 * textMargin);

                if (width > maxWidth && finalText.length() > 10) {
                    maxWidth = 500;

                    while (width > maxWidth && finalText.length() > 10) {
                        finalText =
                                finalText.substring(0, finalText.length() - 6)
                                        + "..."; //$NON-NLS-1$
                        width =
                                hyperlinkTextFigure.getTextExtents(finalText).width;
                        width += (2 * textMargin);
                    }

                    hyperlinkSize.width = width;
                }

                hyperlinkTextFigure.setText(finalText);

                if (width > maxWidth) {
                    maxWidth = width;
                }

                height += hyperlinkSize.height;
            }

            return new Dimension(maxWidth, height);
        }

        @Override
        public void layout(IFigure container) {
            Dimension size = container.getSize();

            Rectangle r =
                    new Rectangle(textMargin, size.height
                            - (hyperlinkSize.height + 4), size.width
                            - textMargin, hyperlinkSize.height);
            hyperlinkBlockFigure.setBounds(r);

        }

        @Override
        public void invalidate() {
            // do nothing...
        }

        @Override
        public void remove(IFigure child) {
            // do nothing...
        }

        @Override
        public void setConstraint(IFigure child, Object constraint) {
            // do nothing...
        }
    }

    private HoverInfo info;

    private Font titleFont;

    private Font textFont;

    private Font hyperlinkFont;

    private Font propNameFont;

    private Font propValueFont;

    private int textMargin = 6;

    private int maxName;

    private int maxValue;

    private FlowPage hyperlinkBlockFigure;

    private UnderlinedTextFlow hyperlinkTextFigure;

    /**
     * Moved from multiple referencers to here.
     */
    private static final String PROPERTY_VALUE = "property-value"; //$NON-NLS-1$

    private static final String PROPERTY_NAME = "property-name"; //$NON-NLS-1$

    private static final String TEXT = "text"; //$NON-NLS-1$

    private static final String TITLE = "title"; //$NON-NLS-1$

    private static final String HYPER = "hyper"; //$NON-NLS-1$

    public static FontRegistry registry;
    static {
        if (PlatformUI.isWorkbenchRunning()) {
            PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
                @Override
                public void run() {
                    registry = new FontRegistry();

                    Font df = JFaceResources.getDialogFont();
                    FontData fds = df.getFontData()[0];

                    FontData titleFont = new FontData();
                    titleFont.setName(fds.getName());
                    titleFont.setLocale(fds.getLocale());
                    titleFont.setHeight(8);
                    titleFont.setStyle(SWT.BOLD);
                    registry.put(TITLE, new FontData[] { titleFont });

                    FontData textFont = new FontData();
                    textFont.setName(fds.getName());
                    textFont.setLocale(fds.getLocale());
                    textFont.setHeight(fds.getHeight());
                    textFont.setStyle(SWT.NORMAL);
                    registry.put(TEXT, new FontData[] { textFont });

                    FontData propNameFont = new FontData();
                    propNameFont.setName(fds.getName());
                    propNameFont.setLocale(fds.getLocale());
                    propNameFont.setHeight(fds.getHeight());
                    propNameFont.setStyle(SWT.BOLD);
                    registry.put(PROPERTY_NAME, new FontData[] { propNameFont });

                    FontData propValueFont = new FontData();
                    propValueFont.setName(fds.getName());
                    propValueFont.setLocale(fds.getLocale());
                    propValueFont.setHeight(fds.getHeight());
                    propValueFont.setStyle(SWT.NORMAL);
                    registry.put(PROPERTY_VALUE,
                            new FontData[] { propValueFont });

                    FontData hyperlinkFont = new FontData();
                    hyperlinkFont.setName(fds.getName());
                    hyperlinkFont.setLocale(fds.getLocale());
                    hyperlinkFont.setHeight(fds.getHeight());
                    hyperlinkFont.setStyle(SWT.UNDERLINE_SINGLE);
                    registry.put(HYPER, new FontData[] { hyperlinkFont });

                }
            });
        }
    }

    public HoverDescriptionFigure() {

        addDocUrlHyperlink();

        setLayoutManager(new HoverLayoutManager());
        setBackgroundColor(ColorConstants.tooltipBackground);
        setForegroundColor(ColorConstants.tooltipForeground);

        if (registry != null) {
            this.setTitleFont(registry.get(TITLE));
            this.setTextFont(registry.get(TEXT));
            this.setPropNameFont(registry.get(PROPERTY_NAME));
            this.setPropValueFont(registry.get(PROPERTY_VALUE));
            this.setHyperlinkFont(registry.get(HYPER));
        }
    }

    /**
     * Add hyperlink figure for documentation URL.
     */
    private void addDocUrlHyperlink() {
        hyperlinkBlockFigure = new FlowPage();
        this.add(hyperlinkBlockFigure);

        hyperlinkTextFigure =
                new UnderlinedTextFlow(
                        Messages.HoverDescriptionFigure_OpenDocUrl_hyperlink);
        hyperlinkTextFigure.setForegroundColor(ColorConstants.darkBlue);
        hyperlinkTextFigure.setCursor(Cursors.HAND);

        hyperlinkBlockFigure.add(hyperlinkTextFigure);

        hyperlinkBlockFigure.setVisible(false);

        hyperlinkTextFigure.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent me) {
                EditPart hostEditPart = info.getHostEditPart();
                String documentationURL = info.getDocumentationURL();

                if (hostEditPart != null && hostEditPart.getModel() != null
                        && documentationURL != null
                        && documentationURL.length() > 0) {

                    DocumentationURLBrowserUtil
                            .launchBrowserForElement(hostEditPart.getModel(),
                                    documentationURL);
                }
            }

            @Override
            public void mousePressed(MouseEvent me) {
            }

            @Override
            public void mouseDoubleClicked(MouseEvent me) {
            }
        });
    }

    /**
     * @see org.eclipse.draw2d.Figure#useLocalCoordinates()
     * 
     * @return
     */
    @Override
    protected boolean useLocalCoordinates() {
        return true;
    }

    /**
     * Paint the round rectangle background
     */
    @Override
    public void paintFigure(Graphics gr) {
        gr.pushState();
        try {
            Rectangle bnds = getClientArea().getCopy();
            bnds.width--;
            bnds.height--;
            gr.fillRectangle(bnds);
            // gr.drawRectangle(bnds);

            Dimension dim = new Dimension();
            int middle = bnds.x + bnds.width / 2;
            int top = bnds.y + 1;
            if (info.getTitle() != null) {
                FigureUtilities.getTextExtents(info.getTitle(), titleFont, dim);
                int left = middle - dim.width / 2;
                gr.setFont(titleFont);
                gr.drawText(info.getTitle(), left, top);
                top += dim.height + 1;
            }
            if (info.getText() != null) {
                if (info.getTitle() != null) {
                    gr.drawLine(bnds.x, top, bnds.x + bnds.width, top);
                    top += 2;
                }
                top += textMargin;
                gr.setFont(textFont);
                FigureUtilities.getTextExtents(info.getText(), textFont, dim);
                gr.drawText(info.getText(), bnds.x + textMargin, top);
                top += dim.height + textMargin;

            }

            if (info.getProperties() != null && !info.getProperties().isEmpty()) {
                if (info.getTitle() != null || info.getText() != null) {
                    gr.drawLine(bnds.x, top, bnds.x + bnds.width, top);
                    top += 2;
                }
                Dimension d1 = new Dimension();
                Dimension d2 = new Dimension();
                middle = bounds.x + textMargin + maxName;
                for (int i = 0; i < info.getProperties().size(); i++) {
                    HoverInfoProperty p = info.getProperty(i);
                    FigureUtilities
                            .getTextExtents(p.name != null ? p.name : "", propNameFont, d1); //$NON-NLS-1$
                    FigureUtilities.getTextExtents(p.value != null ? p.value
                            : "", propValueFont, d2); //$NON-NLS-1$

                    gr.setFont(propNameFont);
                    gr.drawText(p.name != null ? p.name : "", middle - d1.width, top); //$NON-NLS-1$
                    gr.setFont(propValueFont);
                    gr.drawText(p.value != null ? p.value : "", middle + textMargin, top); //$NON-NLS-1$

                    top += 2 + Math.max(d1.height, d2.height);
                }
            }

            if (hyperlinkBlockFigure.isVisible()) {
                gr.drawLine(bnds.x, top, bnds.x + bnds.width, top);
                top += 2;
            }

        } finally {
            gr.popState();
        }
    }

    public void setPropNameFont(Font propNameFont) {
        this.propNameFont = propNameFont;
    }

    public void setPropValueFont(Font propValueFont) {
        this.propValueFont = propValueFont;
    }

    public void setTextFont(Font textFont) {
        this.textFont = textFont;
    }

    public void setTitleFont(Font titleFont) {
        this.titleFont = titleFont;
    }

    public void setHyperlinkFont(Font font) {
        this.hyperlinkFont = font;
    }

    public void setInfo(HoverInfo hoverInfo) {
        info = hoverInfo;

        String docUrl = info.getDocumentationURL();
        if (docUrl != null && docUrl.length() > 0) {
            hyperlinkTextFigure.setFont(hyperlinkFont);
            hyperlinkTextFigure
                    .setNominalText(Messages.HoverDescriptionFigure_OpenDocUrl_hyperlink
                            + " " + docUrl); //$NON-NLS-1$
            hyperlinkBlockFigure.setVisible(true);
        } else {
            hyperlinkTextFigure
                    .setNominalText(Messages.HoverDescriptionFigure_OpenDocUrl_hyperlink);
            hyperlinkBlockFigure.setVisible(false);
        }
    }

    /**
     * Text flow that underline's it's text.
     * 
     * @author aallway
     * @since 25 Oct 2012
     */
    private final class UnderlinedTextFlow extends TextFlow {

        String nominalText = ""; //$NON-NLS-1$ 

        /**
         * @param text
         */
        private UnderlinedTextFlow(String text) {
            super(text);
        }

        /**
         * @see org.eclipse.draw2d.text.TextFlow#paintText(org.eclipse.draw2d.Graphics,
         *      java.lang.String, int, int, int)
         * 
         * @param g
         * @param draw
         * @param x
         * @param y
         * @param bidiLevel
         */
        @Override
        protected void paintText(Graphics g, String draw, int x, int y,
                int bidiLevel) {
            super.paintText(g, draw, x, y, bidiLevel);

            Dimension textExtents = getTextExtents(getText());

            int maxWidth = this.getParent().getSize().width;
            if (textExtents.width > maxWidth) {
                textExtents.width = maxWidth;
            }

            int textY = y + (textExtents.height - 1);
            g.drawLine(x, textY, x + textExtents.width, textY);
        }

        public Dimension getTextExtents(String text) {
            Dimension textExtents =
                    FigureUtilities.getTextExtents(text, getFont());

            return textExtents;
        }

        /**
         * @param nominalText
         *            the nominalText to set
         */
        public void setNominalText(String nominalText) {
            this.nominalText = nominalText;
        }

        /**
         * @return the nominalText
         */
        public String getNominalText() {
            return nominalText;
        }
    }

}
