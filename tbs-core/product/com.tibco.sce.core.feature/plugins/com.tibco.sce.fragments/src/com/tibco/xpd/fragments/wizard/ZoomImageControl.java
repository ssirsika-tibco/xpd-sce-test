/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.wizard;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.tibco.xpd.fragments.FragmentsActivator;
import com.tibco.xpd.fragments.internal.FragmentConsts;

/**
 * This class provides a scrollable control that displays the image provided by
 * the sub-class. It also allows scaling of the image via zoom in/out buttons.
 * <p>
 * This control is designed to be sub-classed by a class that provides the image
 * (via getImage()). getImage() is passed a 'hintSize' that allows the zoom
 * control to suggest an appropriate size for the image at the given scale. It
 * is up to the sub-class whether or not it wishes to obey this hint. This hint
 * size may be passed as null to get the image at it's full scale size.
 * </p>
 * <p>
 * <b>NOTE: This class deals with the disposal of the image provided by
 * sub-class</b>
 * </p>
 * 
 * @author aallway
 */
public abstract class ZoomImageControl extends Composite implements
		SelectionListener {

	// This is the scale of the WINDOW size in relation to the image.
	// Therefore 1.0 = fit image to window size.
	private double imgScale = 1.0;

	private Dimension sizeImgScaleBasedOn = null;

	private Label imageCtrl = null;

	private Image curImage = null;

	private Composite imageComposite = null;

	private ScrolledComposite imageScroller = null;

	private Button zoomIn;

	private Button zoomOut;

	private boolean firstSize = true;

	private boolean initialFitImageToControl = false;

	private CLabel descriptionLabel;

	/**
	 * Create a zoomable image control.
	 * 
	 * The image is obtained from the abstract getImage() method.
	 * 
	 * @param parent
	 * @param style
	 * @param fitImageToControl
	 */
	public ZoomImageControl(Composite parent, int style,
			boolean fitImageToControl, Dimension sizeHint) {
		super(parent, style);

		this.initialFitImageToControl = fitImageToControl;

		GridLayout iCCLayout = new GridLayout(3, false);
		iCCLayout.verticalSpacing = 0;
		iCCLayout.horizontalSpacing = 0;
		iCCLayout.marginHeight = 0;
		iCCLayout.marginWidth = 0;
		setLayout(iCCLayout);

		ImageRegistry ir = FragmentsActivator.getDefault().getImageRegistry();

		// Composite blanker = new Composite(this, SWT.NONE);
		// blanker.setBackground(ColorConstants.red);
		// GridData blankerData = new GridData(GridData.FILL_HORIZONTAL);
		// blankerData.heightHint = 2;
		// blanker.setLayoutData(blankerData);

		descriptionLabel = new CLabel(this, SWT.NONE);
		descriptionLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));

		zoomIn = new Button(this, SWT.PUSH);
		zoomIn.setImage(ir.get(FragmentConsts.IMG_ZOOMIN_ICON));
		zoomIn.addSelectionListener(this);
		zoomIn.setLayoutData(new GridData());

		zoomOut = new Button(this, SWT.PUSH);
		zoomOut.setImage(ir.get(FragmentConsts.IMG_ZOOMOUT_ICON));
		zoomOut.addSelectionListener(this);
		zoomOut.setLayoutData(new GridData());

		//
		// Create the scrolling container for the image.
		imageScroller = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL
				| SWT.BORDER);
		GridData scrollGridData = new GridData(GridData.FILL_BOTH);
		scrollGridData.horizontalSpan = 3;
		if (sizeHint != null) {
			scrollGridData.heightHint = sizeHint.height;
			scrollGridData.widthHint = sizeHint.width;
		}

		imageScroller.setLayoutData(scrollGridData);
		imageScroller.setBackground(ColorConstants.white);

		imageComposite = new Composite(imageScroller, SWT.NONE);
		imageComposite.setBackground(ColorConstants.white);

		GridLayout cmpLayout = new GridLayout(1, false);
		cmpLayout.marginHeight = 0;
		cmpLayout.marginWidth = 0;
		imageComposite.setLayout(cmpLayout);

		imageCtrl = new Label(imageComposite, SWT.NONE);
		imageCtrl.setBackground(ColorConstants.white);

		GridData imgLayout = new GridData(GridData.FILL_BOTH);
		imageCtrl.setLayoutData(imgLayout);

		imageScroller.setContent(imageComposite);

		imageScroller.addControlListener(new ControlListener() {
			public void controlMoved(ControlEvent e) {
			}

			public void controlResized(ControlEvent e) {
				if (firstSize) {
					firstSize = false;

					if (initialFitImageToControl) {
						fitImageToControl();
					} else {
						refreshImage(true);
					}
				} else {
					if (imgScale == 1.0 && initialFitImageToControl) {
						// If we were told to fit image to window initially and
						// image is still fit to window then grow image with
						// window.
						fitImageToControl();
					} else {
						// Reset the image scale in a way that makes the
						// apparent image size the same.

						Dimension newSize = getCtrlBounds().getSize();

						// Calc ratio between last size that image scale was
						// based on.
						if (newSize.width > 0 && sizeImgScaleBasedOn.width > 0) {
							double ratio = sizeImgScaleBasedOn.width
									/ (double) newSize.width;
							// change the image scale by that amount.
							imgScale *= ratio;

							sizeImgScaleBasedOn = newSize;

							if (imgScale <= 1.0) {
								// Once pic fits win size then keep doing so.
								fitImageToControl();
							}
						}
					}
				}
			}

		});
	}

	@Override
	public void setToolTipText(String string) {
		if (imageCtrl != null && !imageCtrl.isDisposed()) {
			imageCtrl.setToolTipText(string);
		}

		if (imageComposite != null && !imageComposite.isDisposed()) {
			imageComposite.setToolTipText(string);
		}

		if (imageScroller != null && !imageScroller.isDisposed()) {
			imageScroller.setToolTipText(string);
		}

		super.setToolTipText(string);
	}

	/**
	 * Return the image required for display at the given scale. The caller can
	 * return <b>null</b> if there is no current active image.
	 * <p>
	 * <b>Note: </b>This method may be called several times (once on
	 * initialisation and again on zoom (or forced caller refresh()).
	 * </p>
	 * 
	 * @param sizeHint
	 *            Hint of size to fit image to or <b>null</b> if full size image
	 *            required.
	 * @return Image or null if no current image.
	 */
	protected abstract Image createImage(Dimension sizeHint);

	protected String getDescription() {
		return null;
	}

	/**
	 * Return currently displayed image
	 * 
	 * @return Current image or <b>null</b> if none set.
	 */
	public Image getImage() {
		return curImage;
	}

	/**
	 * Refresh the image and scale to window size.
	 */
	public void fitImageToControl() {

		imgScale = 1.0;

		refreshImage(false);

	}

	public void refresh(boolean fitImageToControl) {
		if (fitImageToControl) {
			fitImageToControl();
		} else {
			refreshImage(true);

		}
	}

	/**
	 * Reset the image being displayed at the current scale.
	 */
	private void refreshImage(boolean noScale) {
		Image newImg;

		Rectangle ctrlBnds = getCtrlBounds();

		sizeImgScaleBasedOn = ctrlBnds.getSize();

		if (!noScale) {
			// Bounds to current scale.
			ctrlBnds.scale(imgScale);

			// Allow a small border to ensure image is slightly smaller than
			// boundary fot fit to control.
			int width = ctrlBnds.width - 10;
			int height = ctrlBnds.height - 10;

			if (width < 1) {
				width = 16;
			}
			if (height < 1) {
				height = 16;
			}
			newImg = createImage(new Dimension(width, height));

		} else {
			// Full size image required. Get the image then set our record of
			// the current scale accordingly.
			newImg = createImage(null);

			if (newImg != null) {
				org.eclipse.swt.graphics.Rectangle imgRC = newImg.getBounds();

				// base scale on the largest side.
				if (imgRC.width > imgRC.height) {
					imgScale = (double) imgRC.width / (double) ctrlBnds.width;
				} else {
					imgScale = (double) imgRC.height / (double) ctrlBnds.height;
				}
			} else {
				imgScale = 1.0;
			}

		}

		if (newImg != null) {
			org.eclipse.swt.graphics.Rectangle imgRC = newImg.getBounds();

			imageCtrl.setImage(newImg);
			imageComposite.setSize(imageComposite.computeSize(imgRC.width,
					imgRC.height));

			zoomOut.setEnabled(true);
			zoomIn.setEnabled(true);

		} else {
			imageCtrl.setImage(null);
			imageComposite.setSize(imageComposite.computeSize(1, 1));
			zoomOut.setEnabled(false);
			zoomIn.setEnabled(false);
		}

		// If image has changed then dispose the current one.
		if (newImg != curImage) {
			if (curImage != null) {
				if (!curImage.isDisposed()) {
					curImage.isDisposed();
				}
			}
			curImage = newImg;
		}

		descriptionLabel.setText(getDescription());

		// Get scroller to update scroll bars etc.
		imageScroller.layout();

		return;
	}

	private Rectangle getCtrlBounds() {
		org.eclipse.swt.graphics.Rectangle b = imageScroller.getBounds();

		return new Rectangle(b.x, b.y, b.width, b.height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.widgets.Widget#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();

		if (curImage != null) {
			if (!curImage.isDisposed()) {
				curImage.dispose();
				curImage = null;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse
	 * .swt.events.SelectionEvent)
	 */
	public void widgetDefaultSelected(SelectionEvent e) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt
	 * .events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {
		if (e.widget == zoomIn) {
			doZoomIn();

		} else if (e.widget == zoomOut) {
			doZoomOut();
		}

	}

	/**
	 * 
	 */
	private void doZoomOut() {
		if (imgScale > 0.199) {
			imgScale -= 0.1;

			// No need to go smaller than 'fit window'.
			// if (imgScale <= 1.0) {
			// imgScale = 1.0;
			// }

			refreshImage(false);
		}
	}

	/**
	 * 
	 */
	private void doZoomIn() {
		imgScale += 0.1;

		refreshImage(false);
	}

}
