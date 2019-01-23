/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.dependency.visualization.internal.layout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.eclipse.zest.layouts.LayoutEntity;
import org.eclipse.zest.layouts.algorithms.HorizontalShift;
import org.eclipse.zest.layouts.dataStructures.InternalNode;
import org.eclipse.zest.layouts.dataStructures.InternalRelationship;

/**
 * Extended horizontal shift algorithm to increase the gap between graph nodes. 
 *
 * @author ssirsika
 * @since 21-Oct-2015
 */
@SuppressWarnings("unchecked")
public class ExtendedHorizontalShift extends HorizontalShift {

	private static final double DELTA = 10;
	private static final double VSPACING = 5;

	/**
	 * @param styles
	 */
	public ExtendedHorizontalShift(int styles) {
		super(styles);
	}

	/**
	 * @see org.eclipse.zest.layouts.algorithms.HorizontalShift#applyLayoutInternal(org.eclipse.zest.layouts.dataStructures.InternalNode[], org.eclipse.zest.layouts.dataStructures.InternalRelationship[], double, double, double, double)
	 *
	 */
	@Override
	protected void applyLayoutInternal(InternalNode[] entitiesToLayout, InternalRelationship[] relationshipsToConsider, double boundsX, double boundsY, double boundsWidth, double boundsHeight) {

		ArrayList row = new ArrayList();
		for (int i = 0; i < entitiesToLayout.length; i++) {
			addToRowList(entitiesToLayout[i], row);
		}

		int heightSoFar = 0;

		Collections.sort(row, new Comparator() {

			@Override
			public int compare(Object arg0, Object arg1) {
				List a0 = (List) arg0;
				List a1 = (List) arg1;
				LayoutEntity node0 = ((InternalNode) a0.get(0)).getLayoutEntity();
				LayoutEntity node1 = ((InternalNode) a1.get(0)).getLayoutEntity();
				return (int) (node0.getYInLayout() - (node1.getYInLayout()));
			}

		});

		Iterator iterator = row.iterator();
		while (iterator.hasNext()) {
			List currentRow = (List) iterator.next();
			Collections.sort(currentRow, new Comparator() {
				@Override
				public int compare(Object arg0, Object arg1) {
					return (int) (((InternalNode) arg1).getLayoutEntity().getYInLayout() - ((InternalNode) arg0).getLayoutEntity().getYInLayout());
				}
			});
			Iterator iterator2 = currentRow.iterator();
			int i = 0;
			int width = (int) ((boundsWidth / 2) - currentRow.size() * 75);

			heightSoFar += ((InternalNode) currentRow.get(0)).getLayoutEntity().getHeightInLayout() + VSPACING * 8;
			while (iterator2.hasNext()) {
				InternalNode currentNode = (InternalNode) iterator2.next();

				double location = width + 80 * ++i;
				currentNode.setLocation(location, heightSoFar);
				width += currentNode.getLayoutEntity().getWidthInLayout();
			}
		}
	}

	/**
	 * same implementation as in {@link HorizontalShift}
	 * @param node
	 * @param list
	 */
	private void addToRowList(InternalNode node, ArrayList list) {
		double layoutY = node.getLayoutEntity().getYInLayout();

		for (int i = 0; i < list.size(); i++) {
			List currentRow = (List) list.get(i);
			InternalNode currentRowNode = (InternalNode) currentRow.get(0);
			double currentRowY = currentRowNode.getLayoutEntity().getYInLayout();
			//double currentRowHeight = currentRowNode.getLayoutEntity().getHeightInLayout();
			if (layoutY >= (currentRowY - DELTA) && layoutY <= currentRowY + DELTA) {
				currentRow.add(node);
				//list.add(i, currentRow);
				return;
			}
		}
		List newRow = new ArrayList();
		newRow.add(node);
		list.add(newRow);
	}

}
