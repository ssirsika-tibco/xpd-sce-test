package com.tibco.xpd.ui.propertytester;

import java.util.List;

import org.eclipse.swt.graphics.Image;

/**
 * Interface for the navigator (logical) groups in the 
 * Project Explorer
 * 
 * @author njpatel
 *
 */
public interface INavigatorGroup {
	
	/**
	 * Get the text of this group
	 * 
	 * @return
	 */
	public String getText();
	
	/**
	 * Get the image of this group
	 * 
	 * @return
	 */
	public Image getImage();
	
	/**
	 * Get the children of this group
	 * 
	 * @return List of EObjects
	 */
	public List getChildren();
	
	/**
	 * Determine if this group has children
	 * 
	 * @return <em>true</em> if it has children, <em>false</em> otherwise.
	 */
	public boolean hasChildren();
	
	/**
	 * Get the parent of this group
	 * 
	 * @return
	 */
	public Object getParent();
	
	/**
	 * Dispose.
	 */
	public void dispose();
	
}
