/**
 * 
 */
package com.tibco.xpd.processwidget.actions;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.IActionFilter;

import com.tibco.xpd.processwidget.parts.BaseConnectionEditPart;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;

/**
 * @author aallway
 *
 */
public class WidgetAdapterFactory implements IAdapterFactory {

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object, java.lang.Class)
	 */
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if ((adaptableObject instanceof BaseGraphicalEditPart || adaptableObject instanceof BaseConnectionEditPart) && adapterType == IActionFilter.class) {
			return (new WidgetActionFilter());
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
	 */
	public Class[] getAdapterList() {
		// TODO Auto-generated method stub
		return new Class[] {IActionFilter.class};
	}

}
