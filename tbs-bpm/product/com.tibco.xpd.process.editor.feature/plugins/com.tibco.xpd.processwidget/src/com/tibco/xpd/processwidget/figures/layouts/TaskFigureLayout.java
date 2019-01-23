/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.processwidget.figures.layouts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.AbstractLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.Layer;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.LayerConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.TextLayout;

import com.tibco.xpd.processwidget.figures.TaskFigure;

/**
 * Task figure layout - moved from TaskFigure internal when changed to BPMN 2.0
 * look & feel.
 * <p>
 * <b>Note that the Studio Project (3.9.0) BPMN_20_TaskTextLayoutTest.zip</b>
 * contains a process and a pageflow and 2 images for what they look like in
 * terms of text layout and images. <b>Any changes to this layout should be
 * tried on this project to ensure that the original layout is not adversely
 * affected.</b>
 * </p>
 * <p>
 * <li>On layout this class creates a list of {@link TaskTextBlock}'s (which
 * contain a {@link TextLayout} with the text to paint and a location to paint
 * it).</li>
 * <li>On paint, the host figure (i.e. {@link TaskFigure}) simply has to iterate
 * this list see #getand paint each text block in the indicated location</li>
 * </p>
 * <br/>
 * <p>
 * The reason this strategy is that in BPMN 2.0 task icons are moved to the
 * top-left of the task figure. Therefore to maximise the space available for
 * the text we need wrap the text around that top-left icon.
 * </p>
 * <br/>
 * <p>
 * To do this we use the following approach...
 * <li>Check if the whole text, centred horizontally & vertically will overwrite
 * the icon.</li>
 * <li><b>If</b> doesn't intersect then a single {@link TaskTextBlock} is
 * created for the whole text.</li>
 * <br/>
 * <li><b>Otherwise</b> we align as much of the text as possible (word wrapped
 * if poss) to the right/bottom of the icon <b>centred</b> in the task</li>
 * <li>....<b>In other words</b> the right margin is set to the same margin
 * required for icon on the left so that the text appears centred.)</li>
 * <li>....<b>Even though</b> this restricts the space available for the top
 * line next to the icon, it is necessary as the rest of the text is t be
 * <b>centred</b> below the icon.</li>
 * <li>Then the <b>remaining text</b> is output in the space just below the
 * icon, thus the text appears to be wrapped-around the icon.</li>
 * <br/>
 * <li><b>Note</b> that this can appear odd with over sized icons that are
 * taller than the normal BPMN ones, but that is a minor use-case and the task
 * can be sized in such a way that the text appears beneath the icon entirely.</li>
 * </p>
 * <br/>
 * 
 * @author aallway
 * @since 3 Sept 2014
 */
public class TaskFigureLayout extends AbstractLayout {
    private TaskFigure taskFigure;

    public static int MARKER_SIZE = 16;

    public static int TOP_MARGIN = 4;

    public static int CONTENT_HORZ_MARGIN = 10;

    public static int CONTENT_BOTTOM_MARGIN = 10;

    public static int ICON_MARGIN = 2;

    public static int MARKERS_MARGIN = 2;

    public static int BOTTOM_MARGIN = 4;

    public static int BPMN20_ICON_MARGIN = 4;

    public static int BPMN20_NOMINAL_ICON_SIZE = 20;

    public static int BPMN20_TEXT_MARGIN = 8;

    private Rectangle textDirectEditBounds = new Rectangle(0, 0, 0, 0);

    private List<TaskTextBlock> taskTextLines = new ArrayList<TaskTextBlock>();

    public TaskFigureLayout(TaskFigure taskFigure) {
        this.taskFigure = taskFigure;

    }

    /**
     * The textLayout is used for both calculating how best to locate the text
     * so that it does not overlay the task image if there is one AND for
     * painting within the {@link TaskFigure}
     * 
     * @return the textLayouts
     */
    public List<TaskTextBlock> getTextBlocks() {
        return taskTextLines;
    }

    /**
     * @return the nominal bounds for the direct edit box (relative to task
     *         top-left)
     */
    public Rectangle getTextDirectEditBounds() {
        return textDirectEditBounds;
    }

    @Override
    protected Dimension calculatePreferredSize(IFigure container, int wHint,
            int hHint) {
        Dimension ms = taskFigure.getMinimumSize();
        return new Dimension(Math.max(ms.width, wHint), Math.max(ms.height,
                hHint));
    }

    @Override
    public void layout(IFigure container) {
        taskTextLines = new ArrayList<TaskTextBlock>();

        layoutMarkers();
        layoutImageFigure();

        if (taskFigure.isContentsVisible()) {
            layoutEmbeddedSubflow();
        } else {
            layoutTaskBPMN20Text();
        }

    }

    /**
     * Layout the task image figure.
     * <p>
     * For anything bigger (custom icon) we will fit it to the task leaving
     * space for text.
     */
    private void layoutImageFigure() {
        Rectangle taskBounds = taskFigure.getBounds().getCopy();

        if (taskFigure.useLocalCoordinates()) {
            taskBounds.x = 0;
            taskBounds.y = 0;
        }

        Rectangle imageBounds =
                new Rectangle(taskBounds.x + BPMN20_ICON_MARGIN, taskBounds.y
                        + BPMN20_ICON_MARGIN, BPMN20_NOMINAL_ICON_SIZE,
                        BPMN20_NOMINAL_ICON_SIZE);

        /**
         * The maximum available space for icon (in case of large custom icons
         * we will shrink down to max width and height - enough room for one
         * line of text ish.
         */
        Rectangle imageAvailableBounds =
                new Rectangle(taskBounds.x + BPMN20_ICON_MARGIN, taskBounds.y
                        + BPMN20_ICON_MARGIN, taskBounds.width
                        - (2 * BPMN20_ICON_MARGIN), taskBounds.height - 20
                        - (2 * BPMN20_ICON_MARGIN));

        IFigure imageFigure = taskFigure.getIconFigure();

        if (imageFigure != null) {

            Dimension imgSize = imageFigure.getPreferredSize().getCopy();

            /* Scale width if necessary. */
            if (imgSize.width > imageAvailableBounds.width) {
                float ratio = ((float) imgSize.height / (float) imgSize.width);
                imgSize.width = imageAvailableBounds.width;
                imgSize.height = (int) (imgSize.width * ratio);
            }

            /*
             * Then scale height if necessary (in case it's still over after
             * scaling for width
             */
            if (imgSize.height > imageAvailableBounds.height) {
                float ratio = ((float) imgSize.width / (float) imgSize.height);
                imgSize.height = imageAvailableBounds.height;
                imgSize.width = (int) (imgSize.height * ratio);
            }

            imageBounds.width = imgSize.width;
            imageBounds.height = imgSize.height;
            imageFigure.setBounds(imageBounds);
        }

        /* Overlay the task reference image if required. */
        ImageFigure refImageFigure = taskFigure.getReferenceImageFigure();
        if (refImageFigure != null) {
            Dimension refImageSize = refImageFigure.getPreferredSize();

            Rectangle refImageBounds =
                    new Rectangle(
                            (imageBounds.x + imageBounds.width)
                                    - (refImageSize.width - (refImageSize.width / 3)),
                            (imageBounds.y + imageBounds.height)
                                    - (refImageSize.height - (refImageSize.height / 3)),
                            refImageSize.width, refImageSize.height);

            refImageFigure.setBounds(refImageBounds);
        }

    }

    /**
     * @return The bounds of the image figure relative to task figure or a zero
     *         sized rectangle if there is none. THis will include the
     *         reference-task image overlay if it is present.
     */
    public Rectangle getImageBoundsRelativeToParent() {
        IFigure imageFigure = taskFigure.getIconFigure();

        if (imageFigure != null) {
            Rectangle bounds = imageFigure.getBounds().getCopy();

            ImageFigure referenceImageFigure =
                    taskFigure.getReferenceImageFigure();
            if (referenceImageFigure != null) {
                Rectangle refBounds =
                        referenceImageFigure.getBounds().getCopy();

                bounds = bounds.union(refBounds);
            }

            imageFigure.translateToParent(bounds);
            return bounds;

        } else {
            ImageFigure referenceImageFigure =
                    taskFigure.getReferenceImageFigure();

            if (referenceImageFigure != null) {
                Rectangle bounds = referenceImageFigure.getBounds().getCopy();
                referenceImageFigure.translateToParent(bounds);

                return bounds;
            }
        }

        return new Rectangle(0, 0, 0, 0);
    }

    /**
     * @return The absolute image bounds (or 0,0,0,0 if no image).
     */
    public Rectangle getImageAbsoluteBounds() {
        IFigure imageFigure = taskFigure.getIconFigure();

        if (imageFigure != null) {
            Rectangle bounds = imageFigure.getBounds().getCopy();

            imageFigure.translateToAbsolute(bounds);

            return bounds;
        }

        return new Rectangle(0, 0, 0, 0);
    }

    /**
     * Layout the task markers
     */
    private void layoutMarkers() {
        Rectangle taskBounds = taskFigure.getBounds().getCopy();

        if (taskFigure.useLocalCoordinates()) {
            taskBounds.x = 0;
            taskBounds.y = 0;
        }

        int o =
                taskBounds.x + taskBounds.width / 2
                        - taskFigure.getMarkers().size() * MARKER_SIZE / 2;
        int height =
                taskBounds.y + taskBounds.height - MARKER_SIZE - BOTTOM_MARGIN;

        if (taskFigure.isTransactional()) {
            height -= TaskFigure.TRANSACTIONAL_BORDER_SIZE;
        }
        for (Iterator iter = taskFigure.getMarkers().iterator(); iter.hasNext();) {
            IFigure img = (IFigure) iter.next();
            img.setBounds(new Rectangle(o, height, MARKER_SIZE, MARKER_SIZE));
            o += MARKER_SIZE + 2;
        }

        if (taskFigure.getEmbeddedSubProcOpenCloseButton() != null) {
            Rectangle cb = new Rectangle(0, 0, MARKER_SIZE, MARKER_SIZE);

            cb.x = taskBounds.right() - MARKER_SIZE - 10;
            cb.y = taskBounds.y + 3;

            taskFigure.getEmbeddedSubProcOpenCloseButton().setBounds(cb);

        }
    }

    /**
     * Layout the task BPMN 2.0 styleee.
     * <p>
     * <b>Note that the Studio Project (3.9.0)
     * BPMN_20_TaskTextLayoutTest.zip</b> contains a process and a pageflow and
     * 2 images for what they look like in terms of text layout and images.
     * <b>Any changes to this layout should be tried on this project to ensure
     * that the original layout is not adversely affected.</b>
     * </p>
     * <p>
     * To do this we use the following approach...
     * <li>Check if the whole text, centred horizontally & vertically will
     * overwrite the icon.</li>
     * <li><b>If</b> doesn't intersect then a single {@link TaskTextBlock} is
     * created for the whole text.</li>
     * <br/>
     * <li><b>Otherwise</b> we align as much of the text as possible (word
     * wrapped if poss) to the right/bottom of the icon <b>centred</b> in the
     * task</li>
     * <li>....<b>In other words</b> the right margin is set to the same margin
     * required for icon on the left so that the text appears centred.)</li>
     * <li>....<b>Even though</b> this restricts the space available for the top
     * line next to the icon, it is necessary as the rest of the text is t be
     * <b>centred</b> below the icon.</li>
     * <li>Then the <b>remaining text</b> is output in the space just below the
     * icon, thus the text appears to be wrapped-around the icon.</li>
     * <br/>
     * <li><b>Note</b> that this can appear odd with over sized icons that are
     * taller than the normal BPMN ones, but that is a minor use-case and the
     * task can be sized in such a way that the text appears beneath the icon
     * entirely.</li>
     * </p>
     */
    private void layoutTaskBPMN20Text() {
        taskTextLines.clear();

        /*
         * Store text location relative to top-left of task (rather than
         * absolute) as this prevents odd issues when scrolling and changing
         * zooms.
         */
        Rectangle taskBounds = taskFigure.getBounds().getCopy();

        taskBounds.x = 0;
        taskBounds.y = 0;

        Rectangle imageBounds = getImageBoundsRelativeToParent();

        String wholeTaskText = taskFigure.getText();

        if (wholeTaskText == null || wholeTaskText.length() == 0) {
            textDirectEditBounds = taskBounds.getCopy();

            textDirectEditBounds.x += BPMN20_TEXT_MARGIN;
            textDirectEditBounds.y += BPMN20_TEXT_MARGIN;
            textDirectEditBounds.width -= 2 * BPMN20_TEXT_MARGIN;
            textDirectEditBounds.height -= 2 * BPMN20_TEXT_MARGIN;

            return;
        }

        /**
         * First work out whether text at nominal max width will cross over
         * vertically with image figure if it were given all the space available
         * and centered vertically
         */
        TaskTextBlock textBlock1 =
                new TaskTextBlock(taskFigure.getFont(), SWT.CENTER);
        taskTextLines.add(textBlock1);

        textBlock1.textLayout.setText(wholeTaskText);

        /*
         * The maximum available bounds for the text (whole i.e. whole activity)
         */
        Rectangle maxAvailableBounds = taskBounds.getCopy();

        maxAvailableBounds.x += BPMN20_TEXT_MARGIN;
        maxAvailableBounds.y += BPMN20_TEXT_MARGIN;
        maxAvailableBounds.width -= 2 * BPMN20_TEXT_MARGIN;
        maxAvailableBounds.height -= 2 * BPMN20_TEXT_MARGIN;

        textBlock1.textLayout.setWidth(maxAvailableBounds.width);

        Rectangle textBounds = textBlock1.getActualTextBounds();

        textBlock1.x = maxAvailableBounds.x;

        if (textBounds.height < maxAvailableBounds.height) {
            /* Centre vertically */
            textBlock1.y =
                    maxAvailableBounds.y
                            + ((maxAvailableBounds.height - textBounds.height) / 2);
        } else {
            textBlock1.y = maxAvailableBounds.y;
        }

        /*
         * If text does not intersect image, then job is done. Else we'll try
         * and make it not.
         */
        if (textBlock1.getActualTextBounds().intersects(imageBounds)) {

            /**
             * Ok there isn't enough room so we will centre as much on the top
             * line and make its bottom level with the bottom of icon.
             * 
             * Then we will use a second text layout to draw the rest of text as
             * we'll have more space under the icon.
             */
            int imageIndent =
                    (imageBounds.x - taskBounds.x) + imageBounds.width;

            int availableCenteredWidth = taskBounds.width - (2 * imageIndent);

            textBlock1.textLayout
                    .setWidth(availableCenteredWidth > 0 ? availableCenteredWidth
                            : 11);

            /**
             * Ok, everything that fits on line 1 will stay in textBlock1
             * alongside icon.
             * 
             * Everything else will go in text block 2 which will have full
             * width just under icon.
             */

            /*
             * **NOTE** lineOffsets contains start of each line char + last is
             * length of text (hence length > 2 instead of > 1 for checking if
             * there are 2 lines)
             */
            int[] lineOffsets = textBlock1.textLayout.getLineOffsets();

            if (lineOffsets.length > 2) {
                /*
                 * Ok there is at least 2 lines (else we'll leave it as is).
                 * 
                 * Calculate and save the line spacing to preserve it when we
                 * separate into 2 separate blocks.
                 */
                String firstLineText =
                        textBlock1.textLayout.getText().substring(0,
                                lineOffsets[1]);

                String restOfText = null;

                org.eclipse.swt.graphics.Rectangle firstLineBounds =
                        textBlock1.textLayout.getLineBounds(0);

                org.eclipse.swt.graphics.Rectangle secondLineBounds =
                        textBlock1.textLayout.getLineBounds(1);

                int lineSpacing = secondLineBounds.y - firstLineBounds.y;

                /*
                 * Set first text block to just first line of text then Align
                 * first line with bottom of icon.
                 */
                textBlock1.textLayout.setText(firstLineText.trim());

                textBlock1.x = taskBounds.x + imageIndent;
                textBlock1.y =
                        (imageBounds.y + imageBounds.height)
                                - (firstLineBounds.height - 3);

                /*
                 * If available width looks too small even for a single
                 * character then ditch first block and add everything to second
                 * block.
                 */
                if (firstLineText.length() <= 1) {
                    restOfText = wholeTaskText;

                    taskTextLines.remove(textBlock1);

                } else {
                    /*
                     * There is room for at least some text next to pic so lay
                     * out first block
                     */
                    restOfText = wholeTaskText.substring(lineOffsets[1]);

                }

                /**
                 * Create second text block (full width under icon)
                 * */
                TaskTextBlock textBlock2 =
                        new TaskTextBlock(taskFigure.getFont(), SWT.CENTER);
                taskTextLines.add(textBlock2);

                textBlock2.textLayout.setText(restOfText.trim());
                textBlock2.textLayout.setWidth(maxAvailableBounds.width);

                textBlock2.x = maxAvailableBounds.x;
                textBlock2.y = textBlock1.y + lineSpacing;

            }
        }

        /* Calculate the overall text bounds for direct edit. */
        textDirectEditBounds = null;

        for (TaskTextBlock textBlock : taskTextLines) {
            Rectangle b = textBlock.getAllowedTextBounds();

            if (textDirectEditBounds == null) {
                textDirectEditBounds = b;
            } else {
                textDirectEditBounds = textDirectEditBounds.getUnion(b);
            }

        }

    }

    /**
     * Layout the embedded sub-process figure
     * 
     * @param container
     */
    private void layoutEmbeddedSubflow() {
        Rectangle taskBounds = taskFigure.getBounds().getCopy();

        /*
         * Painting uses same coord system as task.
         * 
         * Figure layout coord system can be different. So we create 2 Points
         * and offset them appropriately to begin with and then we can apply the
         * same changes to both to keep them in synch
         */
        Point topLeftForPainting = new Point(0, 0);

        /*
         * Layout out children depends on whether task figure is using
         * local-coord system for its children
         */
        if (taskFigure.useLocalCoordinates()) {
            taskBounds.x = 0;
            taskBounds.y = 0;
        }

        Point topLeftForChildren = new Point(taskBounds.x, taskBounds.y);

        topLeftForChildren.y += TOP_MARGIN;
        topLeftForPainting.y += TOP_MARGIN;
        topLeftForChildren.x += CONTENT_HORZ_MARGIN;
        topLeftForPainting.x += CONTENT_HORZ_MARGIN;

        int prefWidth = taskBounds.width - (2 * CONTENT_HORZ_MARGIN);

        if (taskFigure.isTransactional()) {
            topLeftForChildren.y += TaskFigure.TRANSACTIONAL_BORDER_SIZE;
            topLeftForPainting.y += TaskFigure.TRANSACTIONAL_BORDER_SIZE;
            topLeftForChildren.x += TaskFigure.TRANSACTIONAL_BORDER_SIZE;
            topLeftForPainting.x += TaskFigure.TRANSACTIONAL_BORDER_SIZE;
            prefWidth -= TaskFigure.TRANSACTIONAL_BORDER_SIZE * 2;
        }

        /* Adjust for open / close button. */
        int prefTextWidth = prefWidth;

        Dimension textActualSize = new Dimension(prefTextWidth, 0);

        if (taskFigure.getEmbeddedSubProcOpenCloseButton() != null) {
            Rectangle openBtnBounds =
                    taskFigure.getEmbeddedSubProcOpenCloseButton().getBounds()
                            .getCopy();

            prefTextWidth -= openBtnBounds.width;

            /* Will Reset these later if we have text */
            textActualSize.width = prefTextWidth;
            textActualSize.height = openBtnBounds.height;
        }

        /**
         * Reset the text layout. The height will set itself according to
         * wrapping to the available width
         */
        taskTextLines.clear();

        String text = taskFigure.getText();

        if (text != null && text.length() > 0) {

            TaskTextBlock textBlock =
                    new TaskTextBlock(taskFigure.getFont(), SWT.LEFT);
            textBlock.textLayout.setWidth(prefTextWidth > 0 ? prefTextWidth
                    : 10);

            textBlock.x = topLeftForPainting.x;
            textBlock.y = topLeftForPainting.y;

            textDirectEditBounds =
                    new Rectangle(taskBounds.x + textBlock.x, taskBounds.y
                            + textBlock.y, textBlock.textLayout.getWidth(),
                            taskBounds.height
                                    - (topLeftForPainting.y - taskBounds.y)
                                    - TaskFigure.TRANSACTIONAL_BORDER_SIZE);

            textBlock.textLayout.setText(text != null ? text : ""); //$NON-NLS-1$

            taskTextLines.add(textBlock);

            Rectangle textBounds = textBlock.getActualTextBounds();
            textActualSize.width = textBounds.width;
            textActualSize.height = textBounds.height;

        }

        /**
         * Now see where we have to locate the embedded sub-proces content
         * 'window'
         */
        topLeftForChildren.y += textActualSize.height;
        topLeftForChildren.y += TOP_MARGIN;

        Dimension ps = new Dimension();
        ps.width = prefWidth;
        ps.height =
                taskBounds.height
                        - textActualSize.height
                        - TOP_MARGIN
                        * 2
                        - CONTENT_BOTTOM_MARGIN
                        - (taskFigure.getMarkers().isEmpty() ? 0 : MARKER_SIZE
                                + MARKERS_MARGIN)
                        - (taskFigure.isTransactional() ? TaskFigure.TRANSACTIONAL_BORDER_SIZE * 2
                                : 0);

        Rectangle contentBounds =
                new Rectangle(new Point(topLeftForChildren.x,
                        topLeftForChildren.y), ps);

        Dimension contentPrefSize =
                taskFigure.getContentFigure().getPreferredSize().getCopy();

        // Adjust content size to leave a margin between
        // contained objects and edge.
        contentPrefSize.width += CONTENT_HORZ_MARGIN;
        contentPrefSize.height += CONTENT_BOTTOM_MARGIN;

        // Set the size of the content figure AND the scale
        // so that all of it's contents always fit inside
        // completely.
        double scale = 1;
        if (contentBounds.width < contentPrefSize.width) {
            scale =
                    (double) contentBounds.width
                            / (double) contentPrefSize.width;
        }
        if (contentBounds.height < contentPrefSize.height) {
            scale =
                    Math.min(scale, (double) contentBounds.height
                            / (double) contentPrefSize.height);
        }

        if (scale < 0) {
            // Allowing the scale go negative causes exceptions (for getting
            // font for neg scale!) so make sure it doesn't!
            scale = 0.001;
        }
        taskFigure.getContentFigure().setScale(scale);

        taskFigure.getContentFigure().setBounds(contentBounds);

        Layer pl =
                taskFigure.getContentFigure()
                        .getLayer(LayerConstants.PRIMARY_LAYER);
        pl.getLayoutManager().layout(pl);

        taskFigure.getContentFigure().getLayoutManager()
                .layout(taskFigure.getContentFigure());

    }

    /**
     * Class representing a single block of text. Used so that
     * {@link TaskFigureLayout} can communicate the blocks of text and their
     * start coordinates to {@link TaskFigure} paint routines (becuase for BPMN
     * 2.0 layout we need to have separate blocks that wrap around the icon ).
     * <p>
     * Location co-ord system is up to creator but nominally should be relative
     * to task top left.
     * 
     * @author aallway
     * @since 26 Jul 2014
     */
    public static class TaskTextBlock {
        /** X Coordinate of text (nominally relative to task top left. */
        public int x = 0;

        /** Y Coordinate of text (nominally relative to task top left. */
        public int y = 0;

        public TextLayout textLayout;

        public TaskTextBlock(Font font, int swtAlignmentStyle) {
            super();
            textLayout = new TextLayout(null);

            if (font != null) {
                textLayout.setFont(font);
            }

            textLayout.setAlignment(swtAlignmentStyle);
        }

        /**
         * @return Get the bounds of the text (as opposed to getAllowedBounds()
         *         which will return the allowed width + actual height).
         */
        public Rectangle getActualTextBounds() {
            String lineText = textLayout.getText();

            org.eclipse.swt.graphics.Rectangle bounds =
                    textLayout.getBounds(0, lineText.length());

            return new Rectangle(x + bounds.x, y + bounds.y, bounds.width,
                    bounds.height);
        }

        /**
         * @return Get the bounds of the text constrained by its allowed width.
         *         The width will ALLOWED width and the height will be the
         *         height as it is width width constrained. (as opposed to
         *         getActualTextBounds() which will return actual bounds of the
         *         text itself).
         */
        public Rectangle getAllowedTextBounds() {
            org.eclipse.swt.graphics.Rectangle bounds = textLayout.getBounds();

            return new Rectangle(x, y, bounds.width, bounds.height);
        }

        /**
         * @see java.lang.Object#toString()
         * 
         * @return
         */
        @Override
        public String toString() {
            Rectangle b = getActualTextBounds();
            return String
                    .format("TaskTextBlock('%s', %d, %d) (Actual Text Bounds: %d, %d, %d, %d)", //$NON-NLS-1$
                            textLayout.getText(),
                            x,
                            y,
                            b.x,
                            b.y,
                            b.width,
                            b.height);
        }

    }

}