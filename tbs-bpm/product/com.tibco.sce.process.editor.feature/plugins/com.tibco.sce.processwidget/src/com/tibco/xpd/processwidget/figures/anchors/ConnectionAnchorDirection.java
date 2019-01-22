package com.tibco.xpd.processwidget.figures.anchors;

import org.eclipse.draw2d.geometry.Point;

/**
 * Connection anchor interface extension (DI:24908)
 * <p>
 * Adds the ability for iPS anchors to specify what direction the transition line exiting/entering
 * a given anchor point should go in.
 *  
 * @author aallway
 *
 */
public interface ConnectionAnchorDirection {
	
	/**
	 * Return the direction in which a transition line should leave/enter an anchor point.
	 * 
	 * This can then be used by BpmnFlowRouter to better decide how to angle lines between 2 points.
	 * 
	 * NOTE: From the perspective of exiting (source Anchor) then the direction should be given
	 * 			as a perpective to "Exits to the xxxxx" 
	 * 			i.e. EAST = exits anchor in easterly direction (exits right side of object).
	 * 
	 * 		 From the perspective of entering (target anchor) then the directio should be given
	 * 			as a perspective of "Enters from the xxxx" 
	 * 			i.e. EAST = enters anchor from the east (enters right side of object).
	 * 
	 * NOTE2:	Use AnchorDirectionHelper class for general anchor direction (i.e. side of anchor) 
	 * 			for when you want the anchor side to be decided on relative position of any point away
	 * 			from centre of a rectangle (i.e. imagine drawing diagonal lines from centre of object bounding
	 * 			to each corner, if reference point positioned in between the centre to top right angle and the
	 * 			centre to bottom right angle then the direction is EAST and so on.
	 * 
	 * @param	anchorOrReferencePoint	Absolute Position of anchor or reference (first point outside of object).
	 *  
	 * @return int One of PositionConstants.NORTH/EAST/SOUTH/WEST or NONE if cannot tell.
	 */
	public int getConnectionAnchorDirection (Point anchorOrReferencePoint);
}
