/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.script;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;

/**
 * @author nwilson
 */
public abstract class ScriptMappingCompositorFactory {

    /** The default context. */
    public static final String DEFAULT = "_default"; //$NON-NLS-1$

    /** Grammar id to compositor factory mapping for input. */
    private static Map<String, Map<String, ScriptMappingCompositorFactory>> in;

    /** Grammar id to compositor factory mapping for output. */
    private static Map<String, Map<String, ScriptMappingCompositorFactory>> out;

    /**
     * @param grammarId
     *            The script grammar id.
     * @param direction
     *            The mapping direction.
     * @return The compositor, or null if one is not found.
     */
    public static ScriptMappingCompositorFactory getCompositorFactory(
            String grammarId, DirectionType direction) {
        return getCompositorFactory(grammarId, direction, DEFAULT);
    }

    /**
     * @param grammarId
     *            The script grammar id.
     * @param direction
     *            The mapping direction.
     * @param context
     *            The context of the compositor.
     * @return The compositor, or null if one is not found.
     */
    public static ScriptMappingCompositorFactory getCompositorFactory(
            String grammarId, DirectionType direction, String context) {
        ScriptMappingCompositorFactory factory = null;
        Map<String, Map<String, ScriptMappingCompositorFactory>> factories = null;
        if (DirectionType.IN_LITERAL.equals(direction)) {
            if (in == null) {
                in = getCompositorFactories(DirectionType.IN_LITERAL
                        .getLiteral());
            }
            factories = in;
        } else if (DirectionType.OUT_LITERAL.equals(direction)) {
            if (out == null) {
                out = getCompositorFactories(DirectionType.OUT_LITERAL
                        .getLiteral());
            }
            factories = out;
        }
        if (factories != null) {
            Map<String, ScriptMappingCompositorFactory> grammarToFactory = factories
                    .get(context);
            if (grammarToFactory != null) {
                factory = grammarToFactory.get(grammarId);
            }
        }
        return factory;
    }

    /**
     * @param direction
     *            The direction of compositor factories to get.
     * @return A map of grammar id to compositor factories.
     */
    private static Map<String, Map<String, ScriptMappingCompositorFactory>> getCompositorFactories(
            String direction) {
        Map<String, Map<String, ScriptMappingCompositorFactory>> factories;
        factories = new HashMap<String, Map<String, ScriptMappingCompositorFactory>>();
        IExtensionPoint extensionPoint = Platform.getExtensionRegistry()
                .getExtensionPoint(Xpdl2ProcessEditorPlugin.ID,
                        "scriptMappingCompositor"); //$NON-NLS-1$
        IConfigurationElement[] configs = extensionPoint
                .getConfigurationElements();
        for (IConfigurationElement config : configs) {
            String grammar = config.getAttribute("grammar"); //$NON-NLS-1$
            String direction2 = config.getAttribute("direction"); //$NON-NLS-1$
            String context = config.getAttribute("context"); //$NON-NLS-1$
            if (direction != null && direction.equals(direction2)) {
                try {
                    Object executable = config
                            .createExecutableExtension("factory"); //$NON-NLS-1$
                    if (executable instanceof ScriptMappingCompositorFactory) {
                        Map<String, ScriptMappingCompositorFactory> grammarToFactory = factories
                                .get(context);
                        if (grammarToFactory == null) {
                            grammarToFactory = new HashMap<String, ScriptMappingCompositorFactory>();
                            factories.put(context, grammarToFactory);
                        }
                        ScriptMappingCompositorFactory factory;
                        factory = (ScriptMappingCompositorFactory) executable;
                        grammarToFactory.put(grammar, factory);
                    }
                } catch (CoreException e) {
                    Logger log = LoggerFactory
                            .createLogger(Xpdl2ProcessEditorPlugin.ID);
                    if (log != null) {
                        log.error(e);
                    }
                }
            }
        }
        return factories;
    }

    /**
     * @param activity
     *            The activity containing the mapping.
     * @param target
     *            The target field name.
     * @return The ScriptMappingCompositor.
     */
    public abstract ScriptMappingCompositor getCompositor(Activity activity,
            String target);

    /**
     * @param activity
     *            The activity containing the mapping.
     * @param target
     *            The target field name.
     * @param script
     *            The existing script.
     * @return The ScriptMappingCompositor.
     */
    public abstract ScriptMappingCompositor getCompositor(Activity activity,
            String target, String script);
}
