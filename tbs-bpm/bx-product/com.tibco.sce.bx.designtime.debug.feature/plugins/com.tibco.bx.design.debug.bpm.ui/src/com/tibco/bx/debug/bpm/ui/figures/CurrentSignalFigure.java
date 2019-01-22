package com.tibco.bx.debug.bpm.ui.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.Layer;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.tibco.bx.debug.api.BreakWhen;
import com.tibco.bx.debug.bpm.ui.DebugBPMUIActivator;
import com.tibco.xpd.processwidget.figures.ShapeWithDescriptionFigure;
import com.tibco.xpd.processwidget.figures.TaskFigure;

class CurrentSignalFigure extends Layer implements IAnnotationFigure{
	
	private IFigure base;
    BreakWhen currentStatus;
	private ImageFigure beforeBreakpoint;
	private ImageFigure afterBreakpoint;
	private Figure spaceLabel;
	int num;
	private static final int TIMES = 5;
	private static final int INTERVAL = 300;
	Runnable runnable =new Runnable(){

		public void run() {
			//Set up the timer for the animation
			if(num < TIMES){
				num++;
				animate();
				Display.getDefault().timerExec(INTERVAL, this);
			}
		}
		
		public void animate(){
				  if(currentStatus != null){
					    removeImage();
						afterBreakpoint.getParent().getUpdateManager().performUpdate();
						try {
							Thread.sleep(INTERVAL);
						} catch (InterruptedException e) {
							DebugBPMUIActivator.log(e);
						}
						setImage();
			        	afterBreakpoint.getParent().getUpdateManager().performUpdate();
					} 
			  }
    	
    };
	public CurrentSignalFigure(IFigure base,BreakWhen currentStatus){
		this.base = base; 
		this.currentStatus = currentStatus;
		beforeBreakpoint=new IconImage();
        afterBreakpoint=new IconImage();
        spaceLabel =new Figure();
        
        setImage();
        
        ToolbarLayout layout=new ToolbarLayout();
        layout.setVertical(true);
        layout.setStretchMinorAxis(false);
        setLayoutManager(layout);
        
        Figure shape = new Figure();
        layout=new ToolbarLayout();
        layout.setVertical(false);
        layout.setStretchMinorAxis(false);
        layout.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);
        shape.setLayoutManager(layout);
        
        shape.add(beforeBreakpoint);
        shape.add(spaceLabel);
        shape.add(afterBreakpoint);
        
        add(shape);
        this.setOpaque(false);
	}
	
	 /**
     * 
     */
    public void calculateBounds() {
    	spaceLabel.setSize(getBaseBounds().width - ImageSize*2, IAnnotationFigure.ImageSize);
    }

    private Rectangle getBaseBounds(){
    	if(base instanceof TaskFigure){
			//If it the figure of a embedded SubProcess, get HandleBounds
			return ((TaskFigure)base).getHandleBounds();
		}else if (base instanceof ShapeWithDescriptionFigure){
			return ((ShapeWithDescriptionFigure)base).getShape().getBounds().getCopy();
		}else{
			return base.getBounds().getCopy();
		}
    }
    
	public BreakWhen getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(BreakWhen currentStatus) {
		this.currentStatus = currentStatus;
		setImage();
		if(currentStatus !=null){
			num = 0;
			//Display.getDefault().asyncExec(runnable);
		}
	}

	private void setImage(){
		if(BreakWhen.ENTRY.equals(currentStatus)){
        	beforeBreakpoint.setImage(getImage());
        	afterBreakpoint.setImage(null);
        }else if(BreakWhen.RETURN.equals(currentStatus)){
        	beforeBreakpoint.setImage(null);
        	afterBreakpoint.setImage(getImage());
        }else{
        	removeImage();
        }
	}
	private void removeImage(){
		beforeBreakpoint.setImage(null);
    	afterBreakpoint.setImage(null);
	}
	private Image getImage(){
		return DebugBPMUIActivator.getDefault().getImageRegistry().get(DebugBPMUIActivator.IMG_HIGHLIGHT);
	}
	
}
