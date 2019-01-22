package com.tibco.xpd.implementer.resources.xpdl2.properties.filter;

import org.eclipse.jface.viewers.IFilter;


public class ViewerFilterBuilder {

	public static final ViewerFilterBuilder INSTANCE = new ViewerFilterBuilder();
	private TaskViewerFilterImpl taskFilter;
	private EventViewerFilter eventFilter;
    private CatchThrowFilter throwCatchFilter;
	public static final Conjunction OR=new OrConjunction();
	public static final Conjunction AND=new AndConjunction();

	private ViewerFilterBuilder() {}

	public synchronized TaskViewerFilter getTaskFilter() {
		if(taskFilter==null){
			taskFilter=new TaskViewerFilterImpl();
		}
		return taskFilter;
	}

	public synchronized EventViewerFilter getEventFilter(){
		if(eventFilter==null){
			eventFilter=new EventViewerFilterImpl();
		}
		return eventFilter;
	}
	
	public synchronized CatchThrowFilter getCatchThrowFilter(){
	    if(throwCatchFilter == null){
	        throwCatchFilter = new CatchThrowFilterImpl();
	    }
	    return throwCatchFilter;
	}

	public IFilter getComposite(IFilter... filters) {
		return new CompositeFilterImpl(AND,filters);
	}

	public IFilter getComposite(Conjunction conjunction,IFilter... filters) {
		return new CompositeFilterImpl(conjunction,filters);
	}
}
