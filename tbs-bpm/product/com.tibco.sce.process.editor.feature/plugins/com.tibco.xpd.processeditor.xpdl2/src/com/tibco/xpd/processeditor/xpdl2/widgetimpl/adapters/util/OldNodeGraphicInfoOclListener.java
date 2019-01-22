/**
 * 
 */
package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.util;

import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;
import com.tibco.xpd.xpdl2.util.ocl.OclBasedNotifier;
import com.tibco.xpd.xpdl2.util.ocl.OclQueryListener;

/**
 * OclListener that points to the coordinates of the NodeGraphicalInfo
 * 
 * @author wzurek
 */
public class OldNodeGraphicInfoOclListener extends OclBasedNotifier {

    public OldNodeGraphicInfoOclListener(OclQueryListener listener) {
        // We now use NO ToolId for standard XPDL usage graphics infos
        // THis one checks for changes to old ones.
        super(
                "self.getNodeGraphicsInfoForTool('" + Xpdl2ModelUtil.STUDIO_SPECIFIC_TOOL_ID //$NON-NLS-1$
                        + "')", Xpdl2Package.eINSTANCE.getGraphicalNode(), //$NON-NLS-1$
                listener);
    }
}
