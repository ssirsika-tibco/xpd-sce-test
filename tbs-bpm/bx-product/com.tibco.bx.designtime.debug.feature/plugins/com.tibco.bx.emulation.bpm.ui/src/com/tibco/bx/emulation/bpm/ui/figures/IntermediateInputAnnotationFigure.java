package com.tibco.bx.emulation.bpm.ui.figures;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Image;

import com.tibco.bx.emulation.ui.EmulationUIActivator;

public class IntermediateInputAnnotationFigure extends AbstractAnnotationFigure{

	private IconImage activityPointFigure;
	private Label label;
	public IntermediateInputAnnotationFigure(IFigure base, int num){
		super(base);
		ToolbarLayout layout=new ToolbarLayout();
        layout.setVertical(true);
        layout.setStretchMinorAxis(false);
        setLayoutManager(layout);
        
        layout=new ToolbarLayout();
        layout.setVertical(true);
        layout.setStretchMinorAxis(false);
        layout.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);
        setLayoutManager(layout);
		activityPointFigure = new IconImage();
		activityPointFigure.setImage(getImage());
		if(num > 1){
			label = new Label(String.valueOf(num));
			add(label);
		}
		add(activityPointFigure);
	}
	
	private Image getImage(){
		return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_INTERMEDIATEINPUT);
	}

	@Override
	protected Point getTopLeft(Rectangle rectangle) {
		Point point = super.getTopLeft(rectangle);
		if(label != null)
			point.y -= label.getPreferredSize().height;
		return point;
	}
	
	@Override
	protected Dimension getDimension(Rectangle rectangle){
		boolean isIn = isInCollapsedTask();
		Dimension dimension = null;
		if(isIn){
			dimension = new Dimension(0, 0);
		}else{
			int height = label == null ? 0 : label.getPreferredSize().height;
			dimension = new Dimension(rectangle.width, ImageSize + height);
		}
			
		return dimension;
	}
}
