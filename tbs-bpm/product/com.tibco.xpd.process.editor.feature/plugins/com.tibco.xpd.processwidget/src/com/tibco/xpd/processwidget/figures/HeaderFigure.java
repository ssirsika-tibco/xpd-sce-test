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

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.ImageUtilities;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.PrinterGraphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.processwidget.impl.SaveAsImageGraphics;

/**
 * @author wzurek
 */
public class HeaderFigure extends BaseLogExceptionFigure {

    private boolean bVisualsChanged = true; // Keep track if anything has

    public final static String CLOSED_PROPERTY_CHANGE = "HEADER_CLOSED"; //$NON-NLS-1$

    public final static int DEFAULT_MIN_HEIGHT = 60;

    private final static int MINSIZE_CY_MARGIN = 20;

    // changed since the last time we
    // created image.

    private double lastScale = 1; // Keep track of the scale last time we

    // created image

    private boolean imageIsForPrint = false;

    private Image image;

    private String text;

    private Color textColor = null;

    private Rectangle parentFillBounds = null;

    private ImageFigure closeButton = null;

    private boolean isClosed = false;

    private boolean isCloseable = false;

    protected HeaderFigureStyle headerFigureStyle;

    private boolean boldText = false;

    private boolean leftText = false;

    public HeaderFigure(HeaderFigureStyle headerFigureStyle) {
        this.headerFigureStyle = headerFigureStyle;

        if (HeaderFigureStyle.HORIZONTAL.equals(headerFigureStyle)) {
            setMinimumSize(new Dimension(60, 30));
        } else {
            setMinimumSize(new Dimension(30, DEFAULT_MIN_HEIGHT));
        }

        setText(""); //$NON-NLS-1$

        setupCloseButton();
    }

    public HeaderFigureStyle getHeaderFigureStyle() {
        return headerFigureStyle;
    }

    /**
     * @param isCloseable
     *            the isCloseable to set
     */
    public void setCloseable(boolean isCloseable) {
        this.isCloseable = isCloseable;

        closeButton.setVisible(isCloseable);

        if (isCloseable) {
            layout();
        }

    }

    private void setupCloseButton() {
        ImageRegistry ir = ProcessWidgetPlugin.getDefault().getImageRegistry();
        Image img;

        img = ir.get(ProcessWidgetConstants.IMG_MINUS_BUTTON);
        closeButton = new ImageFigure(img);

        closeButton.setVisible(false);
        add(closeButton);

        closeButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent me) {
            }

            @Override
            public void mouseDoubleClicked(MouseEvent me) {
            }

            @Override
            public void mousePressed(MouseEvent me) {
                setClosed(!isClosed);

                firePropertyChange(CLOSED_PROPERTY_CHANGE, !isClosed, isClosed);
            }

        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.draw2d.Figure#layout()
     */
    @Override
    protected void layout() {
        super.layout();

        if (closeButton != null) {
            int margin = 5;

            Image img = closeButton.getImage();
            org.eclipse.swt.graphics.Rectangle imgBnds = img.getBounds();
            int imgCX = imgBnds.width;
            int imgCY = imgBnds.height;

            Rectangle bnds = getBounds().getCopy();
            translateToAbsolute(bnds);

            int imgY;
            int imgX;

            if (HeaderFigureStyle.HORIZONTAL.equals(headerFigureStyle)) {
                imgX = bnds.x + margin;
                imgY = (bnds.y + (bnds.height / 2)) - (imgCX / 2);
            } else {
                imgY = bnds.y + margin;
                imgX = (bnds.x + (bnds.width / 2)) - (imgCX / 2);
            }

            Rectangle btnRect = new Rectangle(imgX, imgY, imgCX, imgCY);

            translateToRelative(btnRect);

            closeButton.setBounds(btnRect);
        }
    }

    @Override
    public void setParent(IFigure p) {
        super.setParent(p);

        if (p == null && image != null) {
            image.dispose();
            image = null;
        }
        bVisualsChanged = true;
    }

    public Rectangle getTextBounds() {
        Image img = getImage();

        if (img == null) {
            return new Rectangle(0, 0, 0, 0);
        }

        ImageData id = img.getImageData();

        return getTextBoundsFromImage(id.width, id.height);
    }

    private Rectangle getTextBoundsFromImage(int imageWidth, int imageHeight) {

        // Image is created already scaled up to current zoom level (but client
        // area won't be)
        // so to make sure we're working on 'even' grounds we need
        // to down-scale it's height/width properties.
        double scale = XPDFigureUtilities.getFigureScale(this);

        if (imageIsForPrint) {
            scale = 1;
        }

        int iWidth = (int) (imageWidth / scale);
        int iHeight = (int) (imageHeight / scale);

        Rectangle ca = getClientArea();

        int xoff;
        int yoff;
        if (HeaderFigureStyle.HORIZONTAL.equals(headerFigureStyle)
                && isLeftText()) {
            xoff = 25;
            yoff = (ca.height - 2 - iHeight) / 2;

        } else {
            xoff = (ca.width - 2 - iWidth) / 2;
            yoff = (ca.height - 2 - iHeight) / 2;
        }

        return new Rectangle(ca.x + 1 + xoff, ca.y + 1 + yoff, iWidth, iHeight);

    }

    public void setText(String text) {
        this.text = text;
        bVisualsChanged = true;

        // Make sure image gets re-created immediately.
        getImage();

        invalidate();
        repaint();
    }

    /**
     * Get the header text image. If hdr text / some visual aspect has changed
     * since last time that the image was last created then it is re-created.
     * 
     * @return Image Header text image.
     */
    public Image getImage() {
        // If anything has changed since last created then re-create
        double currScale = XPDFigureUtilities.getFigureScale(this);

        if ((bVisualsChanged || lastScale != currScale)) {
            if (getParent() != null) {
                if (image != null) {
                    image.dispose();
                }
                image = createImage(text);
                if (image != null) {
                    ImageData id = image.getImageData();

                    if (HeaderFigureStyle.HORIZONTAL.equals(headerFigureStyle)) {
                        setPreferredSize(Math.max(minSize.width, id.width
                                + (2 * MINSIZE_CY_MARGIN)),
                                Math.max(minSize.height, id.height));

                    } else {
                        setPreferredSize(Math.max(minSize.width, id.width),
                                Math.max(minSize.height, id.height
                                        + (2 * MINSIZE_CY_MARGIN)));
                    }

                } else {
                    setPreferredSize(minSize);
                }

                // Reset change-check stuff so we don't recreate image until
                // necessary.
                lastScale = currScale;
                bVisualsChanged = false;

            } else {
                setPreferredSize(minSize);
            }
        }

        return (image);
    }

    /**
     * Code copied from ImageUtilities.getRotatedImageOfString which contains a
     * problem, see coments inline.
     * <p>
     * Original Javadoc:<br>
     * Returns a new Image with the given String rotated left (by 90 degrees).
     * The String will be rendered using the provided colors and fonts. The
     * client is responsible for disposing the returned Image. Strings cannot
     * contain newline or tab characters. This method MUST be invoked from the
     * user-interface (Display) thread.
     * 
     * @param string
     * @return
     */
    private Image createImage(String string) {

        if (string == null || string.length() == 0) {
            string = " "; //$NON-NLS-1$
        }

        Display display = Display.getCurrent();
        if (display == null)
            SWT.error(SWT.ERROR_THREAD_INVALID_ACCESS);

        // Scale fonts...
        FontData fd = getFont().getFontData()[0];
        if (boldText) {
            fd.setStyle(SWT.BOLD);
        }

        double scale = XPDFigureUtilities.getFigureScale(this);
        if (!imageIsForPrint) {
            int newHeight = (int) (scale * fd.getHeight());
            if (newHeight <= 0) {
                // Scaled lower than can go - forget it!
                return (null);
            }
            fd.setHeight(newHeight);
        }

        Font scaledFont = new Font(display, fd);
        FontMetrics metrics = FigureUtilities.getFontMetrics(scaledFont);
        Dimension strSize =
                FigureUtilities.getStringExtents(string, scaledFont);

        // SID DI:24885 - Height UTB set to Ascent + Descent, which isn't enough
        // (need to include Leading too and getHeight does this).
        // This caused image to be too small for text being put in it.
        Image srcImage =
                new Image(display, strSize.width
                        + metrics.getAverageCharWidth(), metrics.getHeight());

        // Oyginal:
        // Image srcImage = new Image(display, strSize.width,
        // metrics.getAscent());
        // It will cut out lower part of the text

        GC gc = new GC(srcImage);
        gc.setFont(scaledFont);

        if (textColor == null) {
            gc.setForeground(getForegroundColor());
        } else {
            gc.setForeground(textColor);
        }

        // Gradient fill behind text...
        // The gradient pattern brush must be offset so that it starts from
        // where edge of
        // whole swim-lane is rather than start of image (i.e. negative relative
        // to text bounds.
        org.eclipse.swt.graphics.Rectangle imageBnds = srcImage.getBounds();
        org.eclipse.swt.graphics.Rectangle fillBnds =
                new org.eclipse.swt.graphics.Rectangle(imageBnds.x,
                        imageBnds.y, imageBnds.width, imageBnds.height);

        // Get text image bounds within header (swap width/height because we
        // haven't rotated it yet.
        Rectangle tb;

        if (HeaderFigureStyle.HORIZONTAL.equals(headerFigureStyle)) {
            tb = getTextBoundsFromImage(imageBnds.width, imageBnds.height);
        } else {
            tb = getTextBoundsFromImage(imageBnds.height, imageBnds.width);
        }

        if (parentFillBounds != null) {
            Pattern p;
            if (HeaderFigureStyle.HORIZONTAL.equals(headerFigureStyle)) {
                fillBnds.x = 0;
                fillBnds.y =
                        ((int) (parentFillBounds.y * scale) - (int) (tb.y * scale));
                fillBnds.height = (int) (parentFillBounds.height * scale);
                fillBnds.width = (int) (parentFillBounds.width * scale);

                p =
                        new Pattern(
                                null,
                                fillBnds.x,
                                fillBnds.y,
                                fillBnds.x,
                                fillBnds.y + fillBnds.height,
                                getBackgroundColor(),
                                ProcessWidgetColors
                                        .getGradientEndColor(getBackgroundColor()));

            } else {
                // Text bounds co-ords are as if the header is already vertical
                // but our image is horizontal, this is why we swap the x/y
                fillBnds.x = 0;
                fillBnds.y =
                        ((int) (parentFillBounds.x * scale) - (int) (tb.x * scale));
                fillBnds.width = (int) (parentFillBounds.height * scale);
                fillBnds.height = (int) (parentFillBounds.width * scale);
                p =
                        new Pattern(
                                null,
                                fillBnds.x,
                                fillBnds.y,
                                fillBnds.x,
                                fillBnds.y + fillBnds.height,
                                getBackgroundColor(),
                                ProcessWidgetColors
                                        .getGradientEndColor(getBackgroundColor()));

            }

            gc.setBackgroundPattern(p);

            gc.fillRectangle(imageBnds);

            gc.setBackgroundPattern(null);
            p.dispose();
        } else {
            gc.setBackground(getBackgroundColor());
            gc.fillRectangle(imageBnds);
        }

        // SID DI:24885 - was removing 'space above accent' from yoffset making
        // it negative, making it clip!
        gc.drawString(string, 0, 0, true); // - metrics.getLeading());

        Image result;
        if (HeaderFigureStyle.HORIZONTAL.equals(headerFigureStyle)) {
            result = srcImage;
        } else {
            result = ImageUtilities.createRotatedImage(srcImage);
            srcImage.dispose();
        }
        gc.dispose();

        scaledFont.dispose();
        return result;
    }

    public Color getTextColor() {
        return textColor;
    }

    public String getText() {
        return text;
    }

    @Override
    public void setBackgroundColor(Color bg) {
        super.setBackgroundColor(bg);
        bVisualsChanged = true;
    }

    @Override
    public void setForegroundColor(Color bg) {
        super.setForegroundColor(bg);
        bVisualsChanged = true;
    }

    @Override
    protected void paintFigure(Graphics gr) {

        // Undo the scaling currently set on the graphics object
        gr.pushState();

        if (gr instanceof PrinterGraphics || gr instanceof SaveAsImageGraphics) {
            // If printing, rebuild image if last time we built wasn't.
            if (!imageIsForPrint) {
                bVisualsChanged = true;
                imageIsForPrint = true;
            }
            Rectangle b = getTextBounds();
            gr.drawImage(getImage(), b.x, b.y);
        } else {
            if (imageIsForPrint) {
                imageIsForPrint = false;
                bVisualsChanged = true;
            }
            gr.scale(1 / lastScale);
            Rectangle b = getTextBounds();

            gr.drawImage(getImage(),
                    (int) (b.x * lastScale),
                    (int) (b.y * lastScale));
            gr.scale(lastScale);
        }

        // then reset it
        gr.popState();

        return;
    }

    public void setParentFillBounds(Rectangle bnds) {
        parentFillBounds = bnds.getCopy();
        bVisualsChanged = true;
    }

    /**
     * @param isClosed
     *            the isClosed to set
     */
    public void setClosed(boolean isClosed) {
        this.isClosed = isClosed;

        ImageRegistry ir = ProcessWidgetPlugin.getDefault().getImageRegistry();
        Image img;

        if (isClosed) {
            img = ir.get(ProcessWidgetConstants.IMG_PLUS_BUTTON);
        } else {
            img = ir.get(ProcessWidgetConstants.IMG_MINUS_BUTTON);
        }
        closeButton.setImage(img);
    }

    /**
     * @return wether the header is closed
     */
    public boolean isClosed() {
        return isClosed;
    }

    /**
     * @return the boldText
     */
    public boolean isBoldText() {
        return boldText;
    }

    /**
     * @param boldText
     *            the boldText to set
     */
    public void setBoldText(boolean boldText) {
        this.boldText = boldText;
    }

    /**
     * @return the leftText
     */
    public boolean isLeftText() {
        return leftText;
    }

    /**
     * @param leftText
     *            the leftText to set
     */
    public void setLeftText(boolean leftText) {
        this.leftText = leftText;
    }

    /**
     * Free any resources created by this figure.
     */
    public void dispose() {
        if (image != null) {
            image.dispose();
            image = null;
        }
    }

}
