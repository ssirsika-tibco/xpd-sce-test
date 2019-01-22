package com.tibco.bx.debug.bpm.ui.figures;

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.eclipse.draw2d.FigureListener;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.StackLayout;

import com.tibco.bx.debug.api.BreakWhen;
import com.tibco.xpd.processwidget.figures.ShapeWithDescriptionFigure;
import com.tibco.xpd.processwidget.figures.TaskFigure;

public class AnnotationFigure extends AbstractAnnotationFigure implements FigureListener{

	private BreakpointFigure breakpointFigure; 
	private CurrentSignalFigure currentSignalFigure;
	public AnnotationFigure(IFigure base , BreakWhen  breakWhen, boolean isBreakpointEnabled ,BreakWhen currentStatus){
		super(base); 
		StackLayout layout=new StackLayout();
        setLayoutManager(layout);
		base.addFigureListener(this);
		base.addPropertyChangeListener(this);
		currentSignalFigure = new CurrentSignalFigure(base, currentStatus);
        breakpointFigure = new BreakpointFigure(base, breakWhen, isBreakpointEnabled);
        if (base instanceof ShapeWithDescriptionFigure){
	        base.addLayoutListener(new LayoutListener.Stub(){
	
				@Override
				public void postLayout(IFigure container) {
					calculateBounds();
				}
	        	
	        });
        }else if(base instanceof TaskFigure){
        	 base.addLayoutListener(new LayoutListener.Stub(){
        			
        		 @Override
 				public void postLayout(IFigure container) {
 					calculateBounds();
 					AnnotationFigure.this.revalidate();
 				}
 	        	
 	        });
        }
        add(currentSignalFigure); 
        add(breakpointFigure);
        
	}
	
	public BreakWhen getBreakWhen() {
		return breakpointFigure.getBreakWhen();
	}
	public void setBreakWhen(BreakWhen breakWhen) {
		breakpointFigure.setBreakWhen(breakWhen);
		checkVisible();
	}
	
	
	public BreakWhen getCurrentStatus() {
		return currentSignalFigure.getCurrentStatus();
	}

	public void setCurrentStatus(BreakWhen currentStatus) {
		currentSignalFigure.setCurrentStatus(currentStatus);
		checkVisible();
	}

	public boolean isBreakpointEnabled() {
		return breakpointFigure.isBreakpointEnabled();
	}
	public void setBreakpointEnabled(boolean isBreakpointEnabled) {
		breakpointFigure.setBreakpointEnabled(isBreakpointEnabled);
	}
    
	public void figureMoved(IFigure figure) {
		refreshAllAnnotationFigures();
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if("HEADER_CLOSED".equals(event.getPropertyName())){ //$NON-NLS-1$
			super.propertyChange(event);
		}
	}
	
	private void refreshAllAnnotationFigures(){
		List<IFigure> list = this.getParent().getChildren();
		for(IFigure figure : list){
			if(figure instanceof AbstractAnnotationFigure && ((AbstractAnnotationFigure)figure).getBase().getParent() != null){
				((IAnnotationFigure)figure).calculateBounds();
			}
		}
	}
	
	protected void checkVisible(){
		if((breakpointFigure.getBreakWhen() == null && currentSignalFigure.getCurrentStatus() == null)){
			setVisible(false);
		}else{
			super.checkVisible();
		}	
	}
	
}
