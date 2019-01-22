package com.tibco.xpd.script.model.internal.client;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;


public interface IModelScriptRelevantData extends IScriptRelevantData {
    
    JsClass getJsClass();

    EObject getModel();

    EObject getModelType();
    
    String getModelTypeName();
}
