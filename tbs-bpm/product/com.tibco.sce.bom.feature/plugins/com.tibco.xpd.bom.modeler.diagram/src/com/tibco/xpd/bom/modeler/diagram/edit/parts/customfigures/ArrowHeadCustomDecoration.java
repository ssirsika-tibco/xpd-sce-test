package com.tibco.xpd.bom.modeler.diagram.edit.parts.customfigures;

import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;

/**
 * @author rgreen
 *
 */
public class ArrowHeadCustomDecoration extends PolylineDecoration {

    public ArrowHeadCustomDecoration(IMapMode mapMode) {

        PointList pl = new PointList();
        pl.addPoint(mapMode.DPtoLP(-1), mapMode.DPtoLP(1));
        pl.addPoint(mapMode.DPtoLP(0), mapMode.DPtoLP(0));
        pl.addPoint(mapMode.DPtoLP(-1), mapMode.DPtoLP(-1));
        this.setTemplate(pl);
        this.setScale(mapMode.DPtoLP(7), mapMode.DPtoLP(3));

    }

}
