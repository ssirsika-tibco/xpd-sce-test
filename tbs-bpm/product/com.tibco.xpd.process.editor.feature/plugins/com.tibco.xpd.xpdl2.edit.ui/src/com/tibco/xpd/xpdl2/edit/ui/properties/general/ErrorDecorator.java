/**
 *
 */
package com.tibco.xpd.xpdl2.edit.ui.properties.general;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;

class ErrorDecorator {
	private final HashMap<Control, Decorator> controls;

	private Decorator defaultDecorator=ColorDecorator.INSTANCE;

	public ErrorDecorator() {
		this.controls = new HashMap<Control,Decorator>();
	}

	void addControl(Control control) {
		this.controls.put(control, defaultDecorator);
	}

	void addControl(Control control, Decorator decorator) {
		this.controls.put(control, decorator);
	}

	void addControl(Control control, Decorator... decorators) {
		ErrorDecorator.CompositeDecorator d=new ErrorDecorator.CompositeDecorator(decorators);
		this.controls.put(control, d);
	}

	void removeControl(Control control) {
		this.controls.remove(control);
	}

	void decorate(){
		for (Map.Entry<Control, Decorator> control : controls.entrySet()) {
			control.getValue().decorate(control.getKey());
		}
	}

	void removeDecoration(){
		for (Map.Entry<Control, Decorator> control : controls.entrySet()) {
			control.getValue().removeDecoration(control.getKey());
		}
	}

	interface Decorator {
		void decorate(Control control);
		void removeDecoration(Control control);
	}

	public static class ColorDecorator implements Decorator {

		private final Map<Control,Color> orginals=new HashMap<Control, Color>();
		private Color redColor = new Color(null, 255, 0, 0);

		public static final ColorDecorator INSTANCE=new ColorDecorator();

		private ColorDecorator() {}

		public void decorate(Control control) {
			orginals.put(control, control.getForeground());
			control.setForeground(redColor);
		}

		public void removeDecoration(Control control) {
			control.setForeground(orginals.remove(control));
		}
	}

	public static class TooltipDecorator implements Decorator {

		private String orginalText;
		private String toolTip;

		public TooltipDecorator() {
			toolTip=""; //$NON-NLS-1$
		}

		public void decorate(Control control) {
			orginalText = control.getToolTipText();
			control.setToolTipText(getToolTipText());
		}

		public void removeDecoration(Control control) {
			control.setToolTipText(orginalText);
		}


		public String getToolTipText(){
			return toolTip;
		}

		public void setToolTipText(String toolTip) {
			this.toolTip = toolTip;
		}
	}

	public static class ImageDecorator implements Decorator {

		private Image orginalImage;
		private final Image image;

		public ImageDecorator() {
			this.image=Xpdl2UiPlugin.getDefault().getImageRegistry()
            .get(Xpdl2UiPlugin.IMG_ERROR);
		}

		public ImageDecorator(Image image){
			this.image = image;
		}

		public void decorate(Control control) {
			if(control instanceof CLabel) {
				orginalImage = ((CLabel)control).getImage();
				((CLabel)control).setImage(image);
			} else if(control instanceof Label) {
				orginalImage = ((Label)control).getImage();
				((Label)control).setImage(image);
			}
		}

		public void removeDecoration(Control control) {
			if(control instanceof CLabel) {
				((CLabel)control).setImage(orginalImage);
			} else if(control instanceof Label) {
				((Label)control).setImage(orginalImage);
			}
		}

	}

	public static class CompositeDecorator implements Decorator {

		private final Collection<Decorator> decorators;

		public CompositeDecorator(Collection<Decorator> decorators) {
			this.decorators = new ArrayList<Decorator>(decorators);
		}

		public CompositeDecorator(Decorator... decorators) {
			this.decorators = Arrays.asList(decorators);
		}

		public void decorate(Control control) {
			for (Decorator decorator : decorators) {
				decorator.decorate(control);
			}
		}

		public void removeDecoration(Control control) {
			for (Decorator decorator : decorators) {
				decorator.removeDecoration(control);
			}
		}
	}
}