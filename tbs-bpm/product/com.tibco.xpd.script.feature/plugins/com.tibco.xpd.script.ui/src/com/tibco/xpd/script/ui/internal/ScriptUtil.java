package com.tibco.xpd.script.ui.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;

public class ScriptUtil {
    
    @SuppressWarnings("unchecked")//$NON-NLS-1$
    public static List<JsClass> getSupportedJsClasses(String destination,
            String scriptType, String defaultGrammar, String defaultDestination) {
        if (destination == null || destination.trim().length() < 1) {
            return Collections.EMPTY_LIST;
        }
        List<String> destList = new ArrayList<String>();
        destList.add(destination);
        return getSupportedJsClasses(destList,
                scriptType,
                defaultGrammar,
                defaultDestination);
    }

    @SuppressWarnings("unchecked")//$NON-NLS-1$
    private static List<JsClass> getSupportedJsClasses(
            List<String> destinationList, String scriptType,
            String defaultGrammar, String defaultDestination) {
        List<JsClass> supportedJsClassList = new ArrayList<JsClass>();
        List<JsClassDefinitionReader> jsClassDefinitionReader =
                Collections.EMPTY_LIST;
        try {
            jsClassDefinitionReader =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getJsClassDefinitionReader(destinationList,
                                    scriptType,
                                    defaultGrammar,
                                    defaultDestination);
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        if (jsClassDefinitionReader == null
                || jsClassDefinitionReader.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        for (JsClassDefinitionReader reader : jsClassDefinitionReader) {
            List<JsClass> tempSupportedJsClassList =
                    reader.getSupportedClasses();
            if (tempSupportedJsClassList != null) {
                supportedJsClassList.addAll(tempSupportedJsClassList);
            }
        }
        return supportedJsClassList;
    }
}
