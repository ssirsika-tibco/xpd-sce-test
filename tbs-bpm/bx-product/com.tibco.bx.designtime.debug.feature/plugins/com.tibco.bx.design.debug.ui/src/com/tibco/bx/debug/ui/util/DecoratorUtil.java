package com.tibco.bx.debug.ui.util;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.PlatformUI;

import com.tibco.bx.debug.core.models.IBxStackFrame;
import com.tibco.bx.debug.ui.DebugUIActivator;

public class DecoratorUtil {
	public static final int TOP_LEFT = 0x1;
	public static final int TOP_RIGHT = 0x2;
	public static final int BOTTOM_LEFT = 0x4;
	public static final int BOTTOM_RIGHT = 0x8;
	
	private static LocalResourceManager resourceManager = new LocalResourceManager(
			JFaceResources.getResources(PlatformUI.getWorkbench().getDisplay()));
	
	public static Image addCurrentDecorator(IBxStackFrame stackFrame, Image image) {
		if (!stackFrame.isCurrent()) {
			return image;
		}
		return addDecorator(image, "icons/current_decorator.gif", TOP_LEFT);//$NON-NLS-1$
	}
	
	/**
	 * add a decorator into source
	 * 
	 * @param source
	 *            : source Image
	 * @param decoratorPath
	 *            : decorator Image path
	 * @param position
	 *            : decorator position
	 * @see {@link BxModelPresentation#TOP_LEFT}
	 * @see {@link BxModelPresentation#TOP_RIGHT}
	 * @see {@link BxModelPresentation#BOTTOM_LEFT}
	 * @see {@link BxModelPresentation#BOTTOM_RIGHT}
	 * @return
	 */
	public static Image addDecorator(Image source, String decoratorPath,
			int position) {
		if (source != null) {
			Rectangle bounds = source.getBounds();
			Point size = new Point(bounds.width, bounds.height);
			ImageDescriptor descriptors = DebugUIActivator
					.imageDescriptorFromPlugin(DebugUIActivator.PLUGIN_ID,
							decoratorPath);
			DecorationOverlayIcon icon = null;
			switch (position) {
			case TOP_LEFT:
				icon = new DecorationOverlayIcon(source,
						new ImageDescriptor[] { descriptors }, size);
				break;
			case TOP_RIGHT:
				icon = new DecorationOverlayIcon(source, new ImageDescriptor[] {
						null, descriptors }, size);
				break;
			case BOTTOM_LEFT:
				icon = new DecorationOverlayIcon(source, new ImageDescriptor[] {
						null, null, descriptors }, size);
				break;
			case BOTTOM_RIGHT:
				icon = new DecorationOverlayIcon(source, new ImageDescriptor[] {
						null, null, null, descriptors }, size);
				break;

			default:
				break;
			}
			source = resourceManager.createImage(icon);
		}
		return source;
	}

}
