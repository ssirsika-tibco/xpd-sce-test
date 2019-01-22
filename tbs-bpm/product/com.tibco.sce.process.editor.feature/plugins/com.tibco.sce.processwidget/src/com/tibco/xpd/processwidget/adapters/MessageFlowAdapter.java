/**
 * 
 */
package com.tibco.xpd.processwidget.adapters;

import org.eclipse.emf.ecore.EObject;

/**
 * Message flow adapter
 * 
 * @author wzurek
 */
public interface MessageFlowAdapter extends BaseConnectionAdapter {
    
    /**
     * Source of the message
     * 
     * @return
     */
    EObject getSourceNode();
    
    /**
     * Target of the message
     * 
     * @return
     */
    EObject getTargetNode();
}
