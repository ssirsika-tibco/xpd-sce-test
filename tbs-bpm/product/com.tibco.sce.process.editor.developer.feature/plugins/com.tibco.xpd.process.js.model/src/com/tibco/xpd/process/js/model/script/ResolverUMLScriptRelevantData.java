package com.tibco.xpd.process.js.model.script;

import java.net.URL;

import org.eclipse.emf.common.util.URI;

import com.tibco.xpd.process.js.model.Activator;
import com.tibco.xpd.script.model.client.DefaultUMLScriptRelevantData;

public class ResolverUMLScriptRelevantData extends DefaultUMLScriptRelevantData{

    private static final String FILE_NAME = "/model/java/PojoData.uml"; //$NON-NLS-1$
   
    public URI getURI() {  
        URL entry = Activator.getDefault().getBundle().getEntry(
                ResolverUMLScriptRelevantData.FILE_NAME);
        return URI.createURI(entry.toExternalForm());
    }

}
