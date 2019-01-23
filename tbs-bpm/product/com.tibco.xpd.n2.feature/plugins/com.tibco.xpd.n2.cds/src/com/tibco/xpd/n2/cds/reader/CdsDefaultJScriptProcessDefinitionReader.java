/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.n2.cds.reader;

import java.util.List;

import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.n2.cds.script.N2JScriptTypeResolver;
import com.tibco.xpd.n2.cds.utils.CDSUtils;
import com.tibco.xpd.process.js.model.JScriptProcessDefinitionReader;
import com.tibco.xpd.script.model.client.ITypeResolver;

/**
 * @author mtorres
 */
public class CdsDefaultJScriptProcessDefinitionReader extends JScriptProcessDefinitionReader {
    
    private ITypeResolver typeResolver = null;
    
    @Override
    public Class getMultipleClass(Class umlClass, List<Class> allUmlClasses) {        
        return CDSUtils.getDefaultCDSMultipleClass();
    }
    
    public ITypeResolver getTypeResolver() {
        if(typeResolver == null){
            typeResolver = new N2JScriptTypeResolver();
            typeResolver.setSupportedJsClasses(getSupportedClasses());
        }
        return typeResolver;
    }

}
