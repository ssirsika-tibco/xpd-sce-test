package com.tibco.bx.emulation.bpm.ui.figures;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureListener;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.TreeSearch;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.processwidget.figures.SequenceFlowFigure;

public class AssertionPointAnnotationFigure extends Figure implements IAnnotationFigure , FigureListener ,PropertyChangeListener{//, AncestorListener{

	private SequenceFlowFigure assertionPointFigure;
	//private OutlineFigure outlineFigure;
	private IFigure base;
	private static final int INTERVAL = 500;
	private boolean isAnimating;
	Runnable runnable =new Runnable(){

		public void run() {
			//Set up the timer for the animation
			if(isAnimating){
				animate();
				Display.getDefault().timerExec(INTERVAL * 3, this);
			}
		}
		
		public void animate() {
			/*activityPointFigure.setImage(null);
			activityPointFigure.getParent().getUpdateManager().performUpdate();
			try {
				Thread.sleep(INTERVAL);
			} catch (InterruptedException e) {
				EmulationUIActivator.log(e);
			}
			activityPointFigure.setImage(getImage());
			activityPointFigure.getParent().getUpdateManager().performUpdate();*/
		}

	};
	public AssertionPointAnnotationFigure(IFigure base){
		this.base = base; 
		StackLayout layout=new StackLayout();
        setLayoutManager(layout);
		base.addFigureListener(this);
		base.addPropertyChangeListener(this);
		assertionPointFigure = ((SequenceFlowFigure)base).getCopy();
		assertionPointFigure.setForegroundColor(getColor(200,0,0));
		add(assertionPointFigure);
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
		Rectangle r = new Rectangle();
		r.setLocation(base.getBounds().x + 2, base.getBounds().y + 2);
		r.setSize(base.getBounds().width - 5, base.getBounds().height - 5);
		assertionPointFigure.setConnectionRouter(((SequenceFlowFigure)base).getConnectionRouter());
		//assertionPointFigure.setPoints(((SequenceFlowFigure)base).getLineAnchorLinePoints());
		//base.translateToAbsolute(r);
		//translateToRelative(r);//then Translate to relative coordinates
		//assertionPointFigure.setBounds(r);
		assertionPointFigure.setLocation(r.getLocation().getCopy());
		assertionPointFigure.setSize(r.width, r.height);
		setBounds(r.getCopy().expand(5, 5));
		/*if(!isAnimating){
			isAnimating = true;
			Display.getDefault().asyncExec(runnable);
		}*/
		
	}

	public void figureMoved(IFigure arg0) {
		calculateBounds();
		
	}

	public void propertyChange(PropertyChangeEvent evt) {
		calculateBounds();
		
	}
	
	/**
	 * @see IFigure#findFigureAt(int, int, TreeSearch)
	 */
	public IFigure findFigureAt(int x, int y, TreeSearch search) {
		
		return null;
	}

	@Override
	public void erase() {
		isAnimating = false;
		super.erase();
	}
	
	protected Color getColor(int red, int green, int blue) {
		RGB rgb = new RGB(red,green,blue);
		Color result = null;
            if (PlatformUI.isWorkbenchRunning()) {
            	result= new Color(PlatformUI.getWorkbench().getDisplay(), rgb);
            } else {
            	result= new Color(Display.getDefault(), rgb);
            }

        return result;
    }
}
