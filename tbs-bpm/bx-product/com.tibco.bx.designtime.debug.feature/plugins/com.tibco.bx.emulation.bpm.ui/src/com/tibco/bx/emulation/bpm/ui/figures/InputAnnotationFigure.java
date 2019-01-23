package com.tibco.bx.emulation.bpm.ui.figures;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.swt.graphics.Image;

import com.tibco.bx.emulation.ui.EmulationUIActivator;

public class InputAnnotationFigure extends AbstractAnnotationFigure{

	private IconImage activityPointFigure;
	
	public InputAnnotationFigure(IFigure base){
		super(base);
		StackLayout layout=new StackLayout();
        setLayoutManager(layout);
		activityPointFigure = new IconImage();
		activityPointFigure.setImage(getImage());
		add(activityPointFigure);
	}
	
	private Image getImage(){
		return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_INPUT);
	}

}
