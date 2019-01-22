/**
 * FragmentThumbnailListLayout.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2007
 */
package com.tibco.xpd.fragments.internal.thumbnail;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

/**
 * ImageContainerLayout
 * 
 * Special layout for the image container lays out in rows and columns according
 * to image item width / height.
 */
public class FragmentThumbnailListLayout extends Layout {

	public int imageItemWidth = 32;

	public int imageItemHeight = 32;

	public int extraForLabel = 0;

	public int marginWidth = 10;

	public int marginHeight = 10;

	private Point lastComputeSize = new Point(0, 0);

	private int lastLayoutNumCols = 1;

	private int lastLayoutNumRows = 1;

	private FragmentThumbnailList fragmentThumbnailList;

	/**
     * 
     */
	public FragmentThumbnailListLayout(
			FragmentThumbnailList fragmentThumbnailList) {
		this.fragmentThumbnailList = fragmentThumbnailList;
	}

	// public int getMinimumWidth() {
	// // Min width is one column.
	// return imageItemWidth + (2 * marginWidth);
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swt.widgets.Layout#computeSize(org.eclipse.swt.widgets.Composite
	 * , int, int, boolean)
	 */
	@Override
	protected Point computeSize(Composite composite, int wHint, int hHint,
			boolean flushCache) {

		// We can afford to be fairly simplistic.
		Point size = new Point(0, 0);

		Control[] children = composite.getChildren();

		if (wHint < 1) {
			// Shouldn't be passed SWT.DEFAULT but just in case!
			wHint = imageItemWidth + (2 * marginWidth);
		}

		int numCols = getNumColumns(wHint);

		int numRows = getNumRows(children, numCols);

		size.x = (numCols * (imageItemWidth + marginWidth)) + marginWidth;
		size.y = (numRows * (imageItemHeight + marginHeight + extraForLabel))
				+ marginHeight;

		lastComputeSize = new Point(size.x, size.y);

		return size;
	}

	/**
	 * Get number of columns used in last layout.
	 * 
	 * @return
	 */
	public int getNumberOfColumns() {
		return lastLayoutNumCols;
	}

	/**
	 * Get number of rows used in last layout.
	 * 
	 * @return
	 */
	public int getNumberOfRows() {
		return lastLayoutNumRows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swt.widgets.Layout#layout(org.eclipse.swt.widgets.Composite,
	 * boolean)
	 */
	@Override
	protected void layout(Composite composite, boolean flushCache) {
		int y = 0;

		// Layout the thumbs
		Control[] children = composite.getChildren();

		if (children != null) { // && children.length > 1) {

			lastLayoutNumCols = getNumColumns(lastComputeSize.x);

			lastLayoutNumRows = getNumRows(children, lastLayoutNumCols);

			// For each row...
			int childIdx = 0;
			for (int row = 0; row < lastLayoutNumRows
					&& childIdx < children.length; row++, y += marginHeight
					+ imageItemHeight + extraForLabel) {

				// For each column in row.
				int x = marginWidth;

				for (int col = 0; col < lastLayoutNumCols
						&& childIdx < children.length; col++, childIdx++, x += marginWidth
						+ imageItemWidth) {
					children[childIdx].setBounds(x, y, imageItemWidth,
							imageItemHeight + extraForLabel);
				}

			}
		}

		Rectangle rc = composite.getBounds();
		rc.width = lastComputeSize.x;
		rc.height = lastComputeSize.y;

		composite.setBounds(rc);
	}

	/**
	 * @param children
	 * @param numCols
	 * @return
	 */
	private int getNumRows(Control[] children, int numCols) {
		int numRows = (children.length - 1) / numCols;
		if (((children.length - 1) % numCols) != 0) {
			numRows++;
		}
		return numRows > 0 ? numRows : 1;
	}

	/**
	 * @param bnds
	 * @return
	 */
	private int getNumColumns(int width) {
		width -= marginWidth; // Allow for final right hand margin in
		// calc.

		int numCols = width / (imageItemWidth + marginWidth);
		if (numCols == 0) {
			numCols = 1;
		}
		return numCols;
	}

}
