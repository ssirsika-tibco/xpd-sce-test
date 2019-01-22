package com.tibco.bx.debug.bpm.ui.figures;

import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.geometry.Dimension;

public class IconImage extends ImageFigure{
	public Dimension getPreferredSize(int wHint, int hHint) {
		return new Dimension(IAnnotationFigure.ImageSize ,IAnnotationFigure.ImageSize);
	}
}
