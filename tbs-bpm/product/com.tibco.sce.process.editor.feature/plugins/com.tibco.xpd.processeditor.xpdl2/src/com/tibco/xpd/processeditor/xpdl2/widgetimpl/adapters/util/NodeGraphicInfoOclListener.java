/**
 * 
 */
package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.util;

import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.ocl.OclBasedNotifier;
import com.tibco.xpd.xpdl2.util.ocl.OclQueryListener;

/**
 * OclListener that points to the coordinates of the NodeGraphicalInfo
 * 
 * @author wzurek
 */
public class NodeGraphicInfoOclListener extends OclBasedNotifier {

    public NodeGraphicInfoOclListener(OclQueryListener listener) {
        // We now use NO ToolId for standard XPDL usage graphics infos
        super("self.getNodeGraphicsInfoForTool('" + "" //$NON-NLS-1$ //$NON-NLS-2$
                + "')", Xpdl2Package.eINSTANCE.getGraphicalNode(), //$NON-NLS-1$
                listener);
    }
}
