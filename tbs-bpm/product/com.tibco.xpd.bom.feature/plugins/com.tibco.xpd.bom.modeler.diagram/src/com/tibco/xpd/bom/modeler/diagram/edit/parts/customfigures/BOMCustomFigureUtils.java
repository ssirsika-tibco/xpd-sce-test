package com.tibco.xpd.bom.modeler.diagram.edit.parts.customfigures;

import org.eclipse.draw2d.GridLayout;

public class BOMCustomFigureUtils {

    public static GridLayout createBOMWrapLabelLayout(){        
        GridLayout gl = new GridLayout();      
        gl.numColumns = 1;
        gl.marginHeight = 12;
        gl.marginWidth = 0;
        return gl;  
    }
    
}
