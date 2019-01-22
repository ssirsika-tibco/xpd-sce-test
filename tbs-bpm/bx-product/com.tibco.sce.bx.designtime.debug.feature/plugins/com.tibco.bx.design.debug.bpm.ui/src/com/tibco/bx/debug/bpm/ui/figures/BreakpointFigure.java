package com.tibco.bx.debug.bpm.ui.figures;

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Layer;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Image;

import com.tibco.bx.debug.api.BreakWhen;
import com.tibco.bx.debug.bpm.ui.DebugBPMUIActivator;
import com.tibco.xpd.processwidget.figures.ShapeWithDescriptionFigure;
import com.tibco.xpd.processwidget.figures.TaskFigure;

class BreakpointFigure extends Layer implements IAnnotationFigure{
	
	private IFigure base;
	private BreakWhen breakWhen;
	private boolean isBreakpointEnabled;
	private IconImage beforeBreakpoint;
	private IconImage afterBreakpoint;
	private Figure spaceLabel;
	public BreakpointFigure(IFigure base,BreakWhen breakWhen, boolean isBreakpointEnabled){
		this.base = base; 
		this.breakWhen = breakWhen;
		this.isBreakpointEnabled = isBreakpointEnabled;
		beforeBreakpoint=new IconImage();
		beforeBreakpoint.setSize(ImageSize, ImageSize);
        afterBreakpoint=new IconImage();
        afterBreakpoint.setSize(ImageSize, ImageSize);
        spaceLabel =new Figure();
        
        setImage();
        
        ToolbarLayout layout=new ToolbarLayout();
        layout.setVertical(true);
        layout.setStretchMinorAxis(false);
        setLayoutManager(layout);
        
        layout=new ToolbarLayout();
        layout.setVertical(false);
        layout.setStretchMinorAxis(false);
        layout.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);
        this.setLayoutManager(layout);
        
        add(beforeBreakpoint);
        add(spaceLabel);
        add(afterBreakpoint);
        
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
			return ((ShapeWithDescriptionFigure)base).getShape().getBounds();
		}else{
			return base.getBounds();
		}
    }
    
	public BreakWhen getBreakWhen() {
		return breakWhen;
	}

	public void setBreakWhen(BreakWhen breakWhen) {
		this.breakWhen = breakWhen;
		setImage();
	}

	public boolean isBreakpointEnabled() {
		return isBreakpointEnabled;
	}

	public void setBreakpointEnabled(boolean isBreakpointEnabled) {
		this.isBreakpointEnabled = isBreakpointEnabled;
		setImage();
	}

	private void setImage(){
		if(BreakWhen.BOTH.equals(breakWhen)){
			beforeBreakpoint.setImage(getImage());
			afterBreakpoint.setImage(getImage());
        }else if(breakWhen==null){
        	beforeBreakpoint.setImage(null);
        	afterBreakpoint.setImage(null);
        }else if(BreakWhen.ENTRY.equals(breakWhen)){
        	beforeBreakpoint.setImage(getImage());
        	afterBreakpoint.setImage(null);
        }else if(BreakWhen.RETURN.equals(breakWhen)){
        	beforeBreakpoint.setImage(null);
        	afterBreakpoint.setImage(getImage());
        }
	}
	
	private Image getImage(){
		if(DebugPlugin.getDefault().getBreakpointManager().isEnabled()){
			return DebugBPMUIActivator.getDefault().getImageRegistry().get(
					isBreakpointEnabled ? DebugBPMUIActivator.IMG_BREAKPOINT
							: DebugBPMUIActivator.IMG_BREAKPOINT_DISABLED);
		}else {
			return DebugBPMUIActivator.getDefault().getImageRegistry().get(
					isBreakpointEnabled ? DebugBPMUIActivator.IMG_BREAKPOINT_SKIP
							: DebugBPMUIActivator.IMG_BREAKPOINT_DISABLED_SKIP);
		}
	}
}
