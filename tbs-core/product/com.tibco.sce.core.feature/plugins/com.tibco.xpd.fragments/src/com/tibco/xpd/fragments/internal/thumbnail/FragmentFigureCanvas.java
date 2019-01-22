/**
 * FragmentFigureCanvas.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.fragments.internal.thumbnail;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.fragments.dnd.FragmentLocalSelectionTransfer;
import com.tibco.xpd.fragments.internal.FragmentsViewPart;
import com.tibco.xpd.fragments.internal.dnd.FragmentDragSourceAdapter;

/**
 * FragmentFigureCanvas
 * 
 */
public class FragmentFigureCanvas extends FigureCanvas {

    private ScaledImageFigure imageFigure;

    private DragSource dragSource;

    private Image fragmentImage;

    private Image thumbnailImage;

    /**
	 * 
	 */
    public FragmentFigureCanvas(Composite parent, int style,
            IFragment fragment, FragmentsViewPart viewPart) {
        super(parent, style);

        fragmentImage = fragment.createImage(getDisplay());

        if (fragmentImage != null) {
            imageFigure = new ScaledImageFigure(fragmentImage);
            setContents(imageFigure);

            if (viewPart != null && !isDisposed()) {
                // Set up image control as a drag source.
                dragSource =
                        new DragSource(this, DND.DROP_COPY | DND.DROP_MOVE);
                dragSource
                        .setTransfer(new Transfer[] { FragmentLocalSelectionTransfer
                                .getTransfer() });
                dragSource.addDragListener(new FragmentDragSourceAdapter(this,
                        viewPart.getSite().getSelectionProvider()));
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.widgets.Widget#dispose()
     */
    @Override
    public void dispose() {
        if (dragSource != null) {
            dragSource.dispose();
            dragSource = null;
        }

        if (fragmentImage != null) {
            fragmentImage.dispose();
            fragmentImage = null;
        }

        if (thumbnailImage != null) {
            thumbnailImage.dispose();
            thumbnailImage = null;
        }

        super.dispose();
    }

    private class ScaledImageFigure extends Figure {
        private Image image;

        public ScaledImageFigure(Image img) {
            this.image = img;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.draw2d.Figure#paint(org.eclipse.draw2d.Graphics)
         */
        @Override
        public void paint(Graphics graphics) {

            if (thumbnailImage != null) {
                graphics.drawImage(thumbnailImage, 0, 0);
                return;
            }

            org.eclipse.draw2d.geometry.Rectangle rc = getBounds();

            graphics.setBackgroundColor(getBackground());
            graphics.fillRectangle(rc);

            // Scale the image down to our size.
            Dimension sz = getSize();

            if (sz.width > 0 && sz.height > 0) {
                ImageData imgData = image.getImageData();
                // Maintain the original aspect ratio
                // base scale on the largest side.
                double imageScaleX = (double) sz.width / (double) imgData.width;
                double imageScaleY =
                        (double) sz.height / (double) imgData.height;

                double imgScale;

                if (imageScaleY < imageScaleX) {
                    imgScale = imageScaleY;
                } else {
                    imgScale = imageScaleX;
                }

                if (imgScale > 1.0) {
                    imgScale = 1.0;
                }

                int finalWidth = (int) (imgData.width * imgScale);
                int finalHeight = (int) (imgData.height * imgScale);

                //
                // There is (was) a defect in JVM that meant that fragment
                // thumbnails were blank when jvm args had -Xmx1024m (or
                // higher). For some reason with 1gig Xmx, AND
                // graphics.setInterpolation(SWT.HIGH) then graphics.drawImage()
                // fails (on the 3rd of initial 3 paints that we get.
                //
                // Below is a workaround for this in that we create a brand new
                // image the size of the thumbnail; create a new in-memory GC
                // for the thumbnail and then DO THE SHRINK into that.
                //
                // Then when we graphics.drawImage() into the screen GC we don't
                // need to set high interpolation and it works ok.

                // Create an in-memory thumbnail image and GC
                thumbnailImage =
                        new Image(Display.getDefault(), finalWidth, finalHeight);
                GC thumbGC = new GC(thumbnailImage);

                // Windows image looks rubbish without interpolation=high
                // Linux looks rubbish with interpolation = high.
                if (System.getProperty("os.name").toUpperCase().startsWith( //$NON-NLS-1$
                "WIN")) {//$NON-NLS-1$
                    thumbGC.setInterpolation(SWT.HIGH);
                }

                thumbGC.drawImage(image,
                        0,
                        0,
                        imgData.width,
                        imgData.height,
                        0,
                        0,
                        finalWidth,
                        finalHeight);

                //
                // Copy the thumbnail into the screen graphics.
                // int destY = (sz.height / 2) - (finalHeight / 2);

                graphics.drawImage(thumbnailImage, 0, 0);

                //
                // Dispose the thumbnail image and GC.
                thumbGC.dispose();
            }
        }
    }
}
