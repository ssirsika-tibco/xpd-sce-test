/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.script;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;

/**
 * @author nwilson
 */
public class MappingGrammarConvertorContributionManager {

    private Map<String, Map<String, MappingGrammarConvertor>> convertors;

    public MappingGrammarConvertorContributionManager() {
        convertors =
                new HashMap<String, Map<String, MappingGrammarConvertor>>();
        IExtensionPoint extensionPoint =
                Platform.getExtensionRegistry().getExtensionPoint(
                        Xpdl2ProcessEditorPlugin.ID, "mappingGrammarConvertor"); //$NON-NLS-1$
        IConfigurationElement[] configs =
                extensionPoint.getConfigurationElements();
        for (IConfigurationElement config : configs) {
            String from = config.getAttribute("from"); //$NON-NLS-1$
            String to = config.getAttribute("to"); //$NON-NLS-1$
            try {
                Object o = config.createExecutableExtension("class"); //$NON-NLS-1$
                if (o instanceof MappingGrammarConvertor) {
                    MappingGrammarConvertor convertor =
                            (MappingGrammarConvertor) o;
                    Map<String, MappingGrammarConvertor> tos =
                            convertors.get(from);
                    if (tos == null) {
                        tos = new HashMap<String, MappingGrammarConvertor>();
                        convertors.put(from, tos);
                    }
                    tos.put(to, convertor);
                }
            } catch (CoreException e) {
                Xpdl2ProcessEditorPlugin.getDefault().getLogger().error(e);
            }
        }
    }

    public MappingGrammarConvertor getMappingGrammarConvertor(String from,
            String to) {
        MappingGrammarConvertor convertor = null;
        Map<String, MappingGrammarConvertor> tos = convertors.get(from);
        if (tos != null) {
            convertor = tos.get(to);
        }
        return convertor;
    }
}
