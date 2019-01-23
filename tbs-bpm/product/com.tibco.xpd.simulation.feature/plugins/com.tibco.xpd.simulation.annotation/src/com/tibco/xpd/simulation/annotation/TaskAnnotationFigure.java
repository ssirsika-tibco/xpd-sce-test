/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.annotation;

import java.text.DecimalFormat;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureListener;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageUtilities;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.processwidget.figures.XPDFigureUtilities;

/**
 * Figure to render the annotation graph for a process task. The user needs to
 * specify the size (number of columns on the graph), and opitonally the maximum
 * value for each column and the SLA value for each column, before the
 * annotation is rendered. The current values (params) can then be updated at
 * any time.
 * <p>
 * By default the maximum values and SLAs are set to 100 and the current value
 * is rendered as a blue bar graph overlayed with black text showing the value
 * as a percentage. The format of the overlay text can be changed with the
 * setNumberFormat() method allowing the user to display real values rather than
 * percentages. The maximum values and SLAs can also be changed. If an SLA is
 * exceeded the bar is rendered in red.
 * 
 * @author wzurek, nwilson
 */
public class TaskAnnotationFigure extends Figure implements FigureListener {

    /** Default text length. */
    private static final int TEXT_LENGTH = 10;

    /** Minimum annotation width. */
    private static final int MINIMUM_WIDTH = 30;

    /** Minimum annotation height. */
    private static final int MINIMUM_HEIGHT = 18;

    /** Bar 1 red. */
    private static final int R1 = 34;

    /** Bar 1 green. */
    private static final int G1 = 88;

    /** Bar 1 blue. */
    private static final int B1 = 179;

    /** Bar 2 red. */
    private static final int R2 = 96;

    /** Bar 2 green. */
    private static final int G2 = 143;

    /** Bar 2 blue. */
    private static final int B2 = 223;

    /** Bar 3 red. */
    private static final int R3 = 66;

    /** Bar 3 green. */
    private static final int G3 = 168;

    /** Bar 3 blue. */
    private static final int B3 = 166;

    /** Bar 4 red. */
    private static final int R4 = 164;

    /** Bar 4 green. */
    private static final int G4 = 219;

    /** Bar 4 blue. */
    private static final int B4 = 216;

    /** Bar 5 red. */
    private static final int R5 = 180;

    /** Bar 5 green. */
    private static final int G5 = 0;

    /** Bar 5 blue. */
    private static final int B5 = 0;

    /** Bar 6 red. */
    private static final int R6 = 255;

    /** Bar 6 green. */
    private static final int G6 = 180;

    /** Bar 6 blue. */
    private static final int B6 = 180;

    /** Column alpha setting. */
    private static final int COLUMN_ALPHA = 150;

    /** Default bar width. */
    private static final int DEAFULT_BAR_SIZE = 8;

    /** Maximum value (used to calculate the scale - #pixels per unit). */
    private double[] max;

    /** Current value for each of columns. */
    private double[] params;

    /** Agreed max optimum value (column turns red if param > slaMax. */
    private Double[] slaMax;

    /** Agreed min optimum value (column turns red if param < slaMin. */
    private Double[] slaMin;

    /** Maximum value reached by param since last call to setMaximums(). */
    private double[] tides;

    /** The figure size. */
    private final int size;

    /** The figure margin. */
    private static final int MARGIN = 2;

    /** Bottom of figure space. */
    private static final int SPACE = 3;

    /** Bar width. */
    private int barSize = DEAFULT_BAR_SIZE;

    /** Decimal display format. */
    private DecimalFormat format;

    /** The base figure to annotate. */
    private IFigure base;

    /** Space to allow for text. */
    private int textLength;

    /**
     * Bar colour class.
     */
    class BarColor {
        /** Dark colour. */
        private Color dark = null;

        /** Light colour. */
        private Color light = null;

        /**
         * @param dark Dark colour.
         * @param light Light colour.
         */
        public BarColor(Color dark, Color light) {
            this.dark = dark;
            this.light = light;
        }

        /**
         * Disposes of colour resources.
         */
        public void dispose() {
            if (dark != null) {
                dark.dispose();
            }
            if (light != null) {
                light.dispose();
            }
        }
    }

    /** Bar colour array. */
    private BarColor[] colors;

    /** Bar problem colour. */
    private BarColor problemColor = null;

    /**
     * @param base The base figure to be annotated.
     * @param size The number of columns in the graph.
     */
    public TaskAnnotationFigure(IFigure base, int size) {
        this.size = size;
        this.base = base;
        setMinimumSize(new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT));
        max = new double[size];
        params = new double[size];
        slaMax = new Double[size];
        slaMin = new Double[size];
        tides = new double[size];
        format = new DecimalFormat("##0"); //$NON-NLS-1$
        format.setPositiveSuffix(" %"); //$NON-NLS-1$
        textLength = TEXT_LENGTH;
        calculateBounds(true);
        base.addFigureListener(this);

        colors = new BarColor[size];
        for (int i = 0; i < colors.length; i++) {
            if ((i % 2) == 0) {
                colors[i] =
                        new BarColor(new Color(null, R1, G1, B1), new Color(
                                null, R2, G2, B2));
            } else {
                colors[i] =
                        new BarColor(new Color(null, R3, G3, B3), new Color(
                                null, R4, G4, B4));
            }
        }

        problemColor =
                new BarColor(new Color(null, R5, G5, B5), new Color(null, R6,
                        G6, B6));

        setForegroundColor(ColorConstants.darkBlue);
    }

    /**
     * @return The current values for the graph columns.
     */
    public double[] getParams() {
        return params;
    }

    /**
     * @param params The current values for the graph columns.
     */
    public void setParams(double[] params) {
        if (params.length != size) {
            throw new IllegalArgumentException();
        }

        this.params = params;

        for (int i = 0; i < params.length; i++) {
            if (params[i] > tides[i]) {
                tides[i] = params[i];
            }
        }

        invalidate();
    }

    /**
     * @param slaMax The current maximum SLA values for the graph columns.
     * @param slaMin The current minimum SLA values for the graph columns.
     */
    public void setSLA(Double[] slaMax, Double[] slaMin) {
        if (slaMax.length != size || slaMin.length != size) {
            throw new IllegalArgumentException();
        }

        this.slaMax = slaMax;
        this.slaMin = slaMin;
        invalidate();
    }

    /**
     * @param max The current maximum values for the graph columns.
     */
    public void setMaximums(double[] max) {
        if (max.length != size) {
            throw new IllegalArgumentException();
        }

        this.max = max;

        invalidate();
    }

    /**
     * @param format The format for rendering current value overlay text.
     */
    public void setNumberFormat(DecimalFormat format) {
        this.format = format;
        calculateBounds(true);
        invalidate();
    }

    /**
     * Get image of given text painted vertically ** NOTE: You must call
     * image.dispose() on returned image.
     * 
     * @param text The text to convert to an image.
     * @param isBold true to use bold text.
     * @return image or null if font size has been scaled to virtually nothing.
     */
    private Image getVerticalTextImage(String text, boolean isBold) {
        Image textImage = null;

        Font font = getFont();
        if (font != null) {
            FontData fd = font.getFontData()[0];

            if (fd != null) {
                double scale = XPDFigureUtilities.getFigureScale(this);
                int newHeight = (int) (scale * fd.getHeight());
                if (newHeight > 0) {
                    fd.setHeight(newHeight);

                    if (isBold) {
                        fd.setStyle(SWT.BOLD);
                    }

                    Font scaledFont = new Font(null, fd);

                    FontMetrics metrics =
                            FigureUtilities.getFontMetrics(scaledFont);
                    Dimension strSize =
                            FigureUtilities.getStringExtents(text, scaledFont);

                    // SID DI:24885 - Height UTB set to Ascent + Descent, which
                    // isn't enough (need to include Leading too and getHeight
                    // does this).
                    // This caused image to be too small for text being put in
                    // it.
                    Image srcImage =
                            new Image(null, strSize.width, metrics.getAscent()
                                    + metrics.getDescent());

                    GC gc = new GC(srcImage);
                    gc.setFont(scaledFont);

                    gc.setBackground(getBackgroundColor());
                    gc.fillRectangle(srcImage.getBounds());
                    gc.setForeground(getForegroundColor());

                    gc.drawString(text, 0,
                            (int) -(metrics.getDescent() - (2 * scale)));
                    textImage = ImageUtilities.createRotatedImage(srcImage);
                    gc.dispose();
                    srcImage.dispose();

                    scaledFont.dispose();

                    int imageWidth = textImage.getImageData().width;
                    int imageHeight = textImage.getImageData().height;
                    barSize =
                            (int) ((float) (imageWidth + (float) (2 * scale)) / scale);

                    if ((int) ((float) imageHeight / scale) > textLength) {
                        textLength = (int) ((float) imageHeight / scale);
                    }

                }
            }
        }

        return (textImage);
    }

    /**
     * @param gr The graphics context.
     * @see org.eclipse.draw2d.Figure#paintFigure(org.eclipse.draw2d.Graphics)
     */
    protected void paintFigure(Graphics gr) {

        Rectangle ca = getClientArea();

        gr.pushState();

        int currAlpha = gr.getAlpha();
        int columnAlpha = COLUMN_ALPHA;

        gr.setBackgroundColor(ColorConstants.gray);
        gr.fillRectangle(ca.x, (ca.y + ca.height - SPACE) + 1, ca.width,
                SPACE - 1);
        int offset = MARGIN;
        for (int i = 0; i < size; i++) {

            int ah = ca.height - SPACE - textLength;
            double height =
                    params != null ? Math.ceil((params[i] / max[i]) * (ah)) : 0;

            if (params != null && max != null && tides != null) {
                // if (sla != null) {

                gr.setAlpha(columnAlpha);

                // Draw text numeric value at top of columns.
                Image textImage =
                        getVerticalTextImage(format.format(params[i]), true);
                if (textImage != null) {
                    double scale = XPDFigureUtilities.getFigureScale(this);
                    gr.scale(1 / scale);

                    int ypos =
                            (int) ((float) (ca.y + textLength - 2) * scale)
                                    - textImage.getImageData().height;
                    if (ypos < (int) ((float) (ca.y + 2) * scale)) {
                        ypos = (int) ((float) (ca.y + 2) * scale);
                    }
                    gr.drawImage(textImage,
                            (int) ((float) (offset + ca.x) * scale), ypos);
                    gr.scale(scale);

                    textImage.dispose();
                }

                // Draw column...
                if ((slaMax[i] == null || slaMax[i].doubleValue() >= params[i])
                        && (slaMin[i] == null || slaMin[i].doubleValue() <= params[i])) {
                    gr.setBackgroundColor(colors[i].dark);
                    gr.setForegroundColor(colors[i].light);

                } else {
                    gr.setBackgroundColor(problemColor.dark);
                    gr.setForegroundColor(problemColor.light);
                }

                if (params[i] > 0) {
                    gr.fillGradient(offset + ca.x, ca.y + ah + textLength
                            - (int) height, barSize, (int) height, true);
                }

                // Max-optimum level line at top of columns
                gr.setAlpha(currAlpha);

                if (slaMax[i] != null) {
                    int slaHeight =
                            ca.y
                                    + ah
                                    + textLength
                                    - (int) Math
                                            .ceil((slaMax[i].doubleValue() / max[i])
                                                    * (ah));

                    Color currColor = gr.getForegroundColor();
                    gr.setForegroundColor(ColorConstants.gray);

                    gr.drawLine(offset + ca.x - 1, slaHeight, offset + ca.x
                            + barSize, slaHeight);
                    gr.setForegroundColor(currColor);
                }
                if (slaMin[i] != null) {
                    int slaHeight =
                            ca.y
                                    + ah
                                    + textLength
                                    - (int) Math
                                            .ceil((slaMin[i].doubleValue() / max[i])
                                                    * (ah));

                    Color currColor = gr.getForegroundColor();
                    gr.setForegroundColor(ColorConstants.gray);

                    gr.drawLine(offset + ca.x - 1, slaHeight, offset + ca.x
                            + barSize, slaHeight);
                    gr.setForegroundColor(currColor);
                }
                // If tide > current value, draw tide mark.
                if (tides[i] > 0) {
                    int tideHgt = (int) Math.ceil((tides[i] / max[i]) * (ah));

                    if (tideHgt >= height) {
                        gr.setAlpha(columnAlpha);
                        if ((slaMax[i] == null || slaMax[i].doubleValue() >= tides[i])
                                && (slaMin[i] == null || slaMin[i]
                                        .doubleValue() <= tides[i])) {
                            gr.setBackgroundColor(colors[i].dark);

                        } else {
                            gr.setBackgroundColor(problemColor.dark);
                        }

                        int ypos =
                                Math
                                        .max(ca.y + ah + textLength - tideHgt,
                                                ca.y);
                        gr.fillRectangle(offset + ca.x, ypos, barSize, 2);

                        gr.setAlpha(currAlpha);
                    }
                }

                /*
                 * } else { gr.setBackgroundColor(colors[i].dark);
                 * gr.setForegroundColor(colors[i].light);
                 * gr.fillGradient(offset + ca.x, ca.y + ah + textLength - (int)
                 * height, barSize, (int) height, true); }
                 */

                gr.setBackgroundColor(getBackgroundColor());
                gr.setForegroundColor(getForegroundColor());
                gr.setAlpha(currAlpha);
            }
            offset += barSize + SPACE;
        }
        gr.popState();
    }

    /**
     * @param doSetBounds true to change the annotation figure bounds.
     */
    private void calculateBounds(boolean doSetBounds) {
        Rectangle b = base.getBounds().getCopy();
        // base.translateToParent(b);

        Image textImage = getVerticalTextImage("1234 ", true); //$NON-NLS-1$
        if (textImage != null) {
            textImage.dispose();
        }

        Dimension min = getMinimumSize();
        Point topRight = b.getTopRight();
        topRight.y -= textLength;
        Dimension d =
                new Dimension((MARGIN * 2) + (size * barSize)
                        + (SPACE * (size - 1)), Math.max(min.height, b.height)
                        + textLength);
        if (doSetBounds) {
            setBounds(new Rectangle(topRight, d));
        }
    }

    /**
     * Re-do layout.
     * 
     * @see org.eclipse.draw2d.Figure#layout()
     */
    protected void layout() {
        calculateBounds(true);
        super.layout();
    }

    /**
     * Callback to indicate that the base figure has moved.
     * 
     * @param base The base figure.
     * @see org.eclipse.draw2d.FigureListener#figureMoved(
     *      org.eclipse.draw2d.IFigure)
     */
    public void figureMoved(IFigure base) {
        calculateBounds(true);
    }

    /**
     * Dispose of reources.
     */
    public void dispose() {
        for (int i = 0; i < colors.length; i++) {
            colors[i].dispose();
        }
        problemColor.dispose();
    }
}
