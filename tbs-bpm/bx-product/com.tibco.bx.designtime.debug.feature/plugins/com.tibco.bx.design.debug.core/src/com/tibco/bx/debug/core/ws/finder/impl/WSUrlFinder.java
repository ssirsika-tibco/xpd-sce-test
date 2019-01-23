package com.tibco.bx.debug.core.ws.finder.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

public class WSUrlFinder extends AbstractFinder {

    private String url;
    
    
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    String getEndpoint(EObject startActivity, Map<?, ?> parameters) {
        return url;
    }

}
