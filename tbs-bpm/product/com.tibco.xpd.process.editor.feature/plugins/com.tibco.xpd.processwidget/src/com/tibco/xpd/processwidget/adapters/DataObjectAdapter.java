/**
 * 
 */
package com.tibco.xpd.processwidget.adapters;

import org.eclipse.swt.graphics.ImageData;

/**
 * Adapter for EObject that represent BPMN's Data Object
 * 
 * @author wzurek
 */
public interface DataObjectAdapter extends BaseGraphicalNodeAdapter {


    /**
     * Set the state of the data object.
     * 
     * @return
     */
    public String getState();

    /**
     * Set the description of the data object.
     * 
     * @return
     */
    public String getExternalReference();

    /**
     * Get the image data for external reference icon (or null if there is none).
     * 
     */
    public ImageData getExternalReferenceImage();
    
    
}
