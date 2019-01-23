package com.tibco.bx.debug.bpm.ui.figures;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.TreeSearch;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import com.tibco.xpd.processwidget.figures.LaneContentFigure;
import com.tibco.xpd.processwidget.figures.LaneFigure;
import com.tibco.xpd.processwidget.figures.LaneHeaderFigure;
import com.tibco.xpd.processwidget.figures.PoolFigure;
import com.tibco.xpd.processwidget.figures.PoolHeaderFigure;
import com.tibco.xpd.processwidget.figures.ShapeWithDescriptionFigure;
import com.tibco.xpd.processwidget.figures.TaskFigure;

public class AbstractAnnotationFigure extends Figure implements IAnnotationFigure, PropertyChangeListener{//, AncestorListener{

	private IFigure base;
	
	LaneHeaderFigure laneHeaderFigure;
	PoolHeaderFigure poolHeaderFigure;
	public AbstractAnnotationFigure(IFigure base) {
		this.base = base;
		laneHeaderFigure = getLaneHeaderFigure(base);
		if(laneHeaderFigure != null){
			laneHeaderFigure.addPropertyChangeListener(this);
			poolHeaderFigure = getPoolHeaderFigure(laneHeaderFigure);
			if(poolHeaderFigure != null){
				poolHeaderFigure.addPropertyChangeListener(this);
			}
		}
	}

	public IFigure getBase() {
		return base;
	}

	public void setBase(IFigure base) {
		this.base = base;
	}
	
	/**
     * Re-do layout.
     * 
     * @see org.eclipse.draw2d.Figure#layout()
     */
    protected void layout() {
        calculateBounds();
        super.layout();
    }
    
    /**
     * 
     */
    public void calculateBounds() {
		// Translate to absolute coordinates
		Rectangle r = Rectangle.SINGLETON;
		r.setBounds(getBaseBounds().getCopy());
		base.translateToAbsolute(r);
		translateToRelative(r);//then Translate to relative coordinates
		
		boolean isIn = isInCollapsedTask();
		Dimension d = getDimension(r);
		Point topLeft = getTopLeft(r);
		setBounds(new Rectangle(topLeft, d));
		List<IFigure> children = getChildren();
		for (IFigure element :  children) {
			if (element instanceof IAnnotationFigure) {
				if(!isIn)
					((IAnnotationFigure) element).calculateBounds();
				else
					element.setSize(0, 0);
			}
		}
		checkVisible();
		
	}

    protected Point getTopLeft(Rectangle rectangle){
    	Point topLeft = rectangle.getTopLeft();
		topLeft.y -= ImageSize;
		return topLeft;
    }
    
    protected Dimension getDimension(Rectangle rectangle){
    	boolean isIn = isInCollapsedTask();
		Dimension dimension = null;
		if(isIn)
			dimension = new Dimension(0, 0);
		else
			dimension = new Dimension(rectangle.width, ImageSize);
		return dimension;
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
    
    protected void checkVisible(){
    	if(isInCollapsedLane()){
    		setVisible(false);
    	}else if(!isInCollapsedTask()){
			setVisible(true);
		}else{
			setVisible(false);
		}	
	}
  
	/**
	 * @see IFigure#findFigureAt(int, int, TreeSearch)
	 */
	public IFigure findFigureAt(int x, int y, TreeSearch search) {
		
		return null;
	}
	
	/**
	 * @see org.eclipse.draw2d.Figure#isEnabled()
	 */
	public boolean isEnabled() {
		return true;
	}

	protected boolean isInCollapsedTask(){	
		IFigure parent = base.getParent();
		while(parent != null){
			if(parent instanceof TaskFigure){
				return !((TaskFigure)parent).isContentsVisible();
			}
			parent = parent.getParent();
		}
		return false;
	}
	
	protected boolean isInCollapsedLane(){	
		if(laneHeaderFigure != null && laneHeaderFigure.isClosed() ||
				poolHeaderFigure != null && poolHeaderFigure.isClosed())
			return true;
		return false;
	}
	
	private LaneHeaderFigure getLaneHeaderFigure(IFigure figure){
		IFigure parent = figure.getParent();
		if(parent == null){
			return null;
		}else if(parent instanceof LaneContentFigure){
			LaneFigure laneFigure = (LaneFigure)parent.getParent();
			return laneFigure.getHeaderFigure();
		}
		return getLaneHeaderFigure(parent);
	}

	private PoolHeaderFigure getPoolHeaderFigure(IFigure figure){
		IFigure parent = figure.getParent();
		if(parent == null){
			return null;
		}else if(parent instanceof PoolFigure){
			PoolHeaderFigure headerFigure = (PoolHeaderFigure)((PoolFigure)parent).getHeaderFigure();
			return headerFigure;
		}
		return getPoolHeaderFigure(parent);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if("HEADER_CLOSED".equals(event.getPropertyName())){ //$NON-NLS-1$
			calculateBounds();
		}
	}
}
