/**
 * Xpdl2BaseArtifactAdapter.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters;

import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;


/**
 * Xpdl2BaseArtifactAdapter
 *
 * Provides the all-artifact type common commands.
 */
public abstract class Xpdl2BaseArtifactAdapter extends Xpdl2BaseGraphicalNodeAdapter {

    /* (non-Javadoc)
     * @see com.tibco.xpd.processwidget.adapters.BaseGraphicalNodeAdapter#getContainer()
     */
    public Object getContainer() {
        Object container = Xpdl2ModelUtil.getContainer((Artifact)getTarget());
        return container;
    }

}

