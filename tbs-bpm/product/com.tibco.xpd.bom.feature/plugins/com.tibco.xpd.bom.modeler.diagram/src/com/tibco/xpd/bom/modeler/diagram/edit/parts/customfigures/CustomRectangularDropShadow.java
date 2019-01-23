package com.tibco.xpd.bom.modeler.diagram.edit.parts.customfigures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.draw2d.ui.figures.RectangularDropShadow;

/*
*
* Drop Shadow Helper Class to help draw shadow on borders
*/
public class CustomRectangularDropShadow extends RectangularDropShadow {

    
    private boolean showRHS = false;
    private boolean showBottom = false;
    private boolean showBoth = true;
    
    
   /**
    * 
    */
   public CustomRectangularDropShadow() {
       super();
       // TODO Auto-generated constructor stub
   }
   
   
   /**
    * 
    */
   public CustomRectangularDropShadow(boolean showRHS, boolean showBottom) {
       super();
       // TODO Auto-generated constructor stub
       
       this.showRHS = showRHS;
       this.showBottom = showBottom;
       this.showBoth = false;
       
   }   

   
   /**
    * @override
    * 
    * draw a shadow around given rectangle @rect
    * @param figure
    * @param g
    * @param rect the rectangle to draw the shadow around
    */
   public void drawShadow(IFigure figure, Graphics g, Rectangle rect){
       
       if (!showBoth){
           if (showBottom){
               //TODO: MAKE THIS VISIBLE!
               //drawBottomLeftShadow(figure, g, rect);

               // bottom
               drawBottomShadow(figure, g, rect);
               
               // bottom right
               drawBottomRightShadow(figure, g, rect);
           }
           
           if (showRHS){
               // bottom right
               drawBottomRightShadow(figure, g, rect);

               // right
               drawRightShadow(figure, g, rect);

               // top right
               drawTopRightShadow(figure, g, rect);
           }  
           
       }
       else {
           //TODO: MAKE THIS VISIBLE!
           //drawBottomLeftShadow(figure, g, rect);

           // bottom
           drawBottomShadow(figure, g, rect);

           // bottom right
           drawBottomRightShadow(figure, g, rect);

           // right
           drawRightShadow(figure, g, rect);

           // top right
           drawTopRightShadow(figure, g, rect);
           
       }

   }
   

}

