package com.tibco.xpd.implementer.resources.xpdl2.properties.filter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.xpd.implementer.resources.xpdl2.Messages;

public abstract class ChainingViewerFilter extends ViewerFilter implements ChainingFilter{

	private ViewerFilter otherFilter;

	protected synchronized ViewerFilter getOtherFilter() {
		return otherFilter;
	}

	@Override
	public final boolean select(Viewer viewer, Object parentElement,
			Object element) {
		boolean result=false;
		result=doSelect(viewer, parentElement, element);
		if(result==false && getOtherFilter()!=null){
			result = getOtherFilter().select(viewer, parentElement, element);
		}
		return result;
	}

	protected abstract boolean doSelect(Viewer viewer, Object parentElement, Object element);

	public final synchronized ViewerFilter chain(ViewerFilter otherFilter){
		if(otherFilter==null){
			throw new NullPointerException(Messages.ChainingViewerFilter_FilterCannotBeNull_message);
		}
		this.otherFilter = otherFilter;
		return this;
	}

}
