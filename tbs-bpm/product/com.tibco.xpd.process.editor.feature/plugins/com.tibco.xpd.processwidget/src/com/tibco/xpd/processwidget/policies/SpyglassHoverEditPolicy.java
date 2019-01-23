/**
 * 
 */
package com.tibco.xpd.processwidget.policies;

import org.eclipse.gef.Request;
import org.eclipse.gef.editpolicies.GraphicalEditPolicy;

/**
 * @author wzurek
 *
 */
public class SpyglassHoverEditPolicy extends GraphicalEditPolicy {

    public void showSourceFeedback(Request request) {
        //System.out.println(request.getType());
    }
    
    public boolean understandsRequest(Request req) {
        //System.out.println(req.getType());
        
        
        
        
        return super.understandsRequest(req);
    }
}
