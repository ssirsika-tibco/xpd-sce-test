/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.process.js.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.ui.internal.IProcessJsDefinitionReader;
import com.tibco.xpd.script.ui.internal.IProcessModelDefinitionReader;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.process.js.model"; //$NON-NLS-1$

    public static final String CLASS_CONTRIBUTION = "classContribution"; //$NON-NLS-1$

    public static final String CLASS_CONTRIBUTOR = "classContributor"; //$NON-NLS-1$

    public static final String MODEL_CONTRIBUTOR = "modelContributor"; //$NON-NLS-1$

    public static final String DESTINATION_ENVIRONMENT =
            "destinationEnvironment"; //$NON-NLS-1$

    public static final String CLASS_NAME = "className"; //$NON-NLS-1$

    public static final String CLASS = "class"; //$NON-NLS-1$

    public static final String FILE_EXTENSIONS = "fileExtensions"; //$NON-NLS-1$

    public static final String PRIORITY = "priority"; //$NON-NLS-1$

    public static final String MODEL_NAME = "model"; //$NON-NLS-1$

    public static final String ICON = "icon"; //$NON-NLS-1$

    public static final String MODEL_DEFINITION_READERS_CONTRIBUTION =
            "modelDefinitionReaders"; //$NON-NLS-1$

    private List<ClassContributor> classContributorList = null;

    private Map<String, IProcessModelDefinitionReader> modelDefinitionReaderMap =
            null;

    public static final String JSCRIPT_DESTINATION =
            ProcessJsConsts.JSCRIPT_DESTINATION;

    public static final String SCRIPT_TYPE = "type"; //$NON-NLS-1$

    public static final String GRAMMAR_TYPE = "grammarType"; //$NON-NLS-1$

    public static final String CONTEXT_TYPE = "contextType"; //$NON-NLS-1$   

    public Logger logger = XpdResourcesPlugin.getDefault().getLogger();

    // The shared instance
    private static Activator plugin;

    /**
     * The constructor
     */
    public Activator() {
        plugin = this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static Activator getDefault() {
        return plugin;
    }

    private synchronized void readClassDefinitionReader() {
        if (classContributorList == null) {
            IExtensionRegistry extensionRegistry =
                    Platform.getExtensionRegistry();
            IConfigurationElement[] configurationElementsFor =
                    extensionRegistry.getConfigurationElementsFor(PLUGIN_ID,
                            CLASS_CONTRIBUTION);
            classContributorList = new ArrayList<ClassContributor>();
            for (IConfigurationElement element : configurationElementsFor) {
                try {
                    if (element != null && element.getName() != null) {
                        if (element.getName().equals(CLASS_CONTRIBUTOR)) {
                            readClassContributors(element, classContributorList);
                        } else if (element.getName().equals(MODEL_CONTRIBUTOR)) {
                            readModelContributors(element, classContributorList);
                        }
                    }
                } catch (CoreException e) {
                    String errorMsg =
                            Messages.Activator_Error_Reading_ClassContributor;
                    logger.error(e, errorMsg);
                }
            }
        }
    }

    private void readClassContributors(IConfigurationElement element,
            List<ClassContributor> classContributorList) throws CoreException {
        // Get the destination environments for the validation strategy class
        IConfigurationElement[] destinationElementsFor =
                element.getChildren(DESTINATION_ENVIRONMENT);
        // Get the Grammar Type
        String elementGrammarType = element.getAttribute(GRAMMAR_TYPE);
        if (destinationElementsFor != null) {
            for (IConfigurationElement destinationElement : destinationElementsFor) {
                if (destinationElement != null) {
                    // Get the context types for the destination environment of
                    // the class
                    String tempDest =
                            destinationElement
                                    .getAttribute(DESTINATION_ENVIRONMENT);
                    ClassContributor classContributor =
                            getClassContributor(tempDest, elementGrammarType);
                    Map<String, List<JsClassDefinitionReader>> classDefMap =
                            null;
                    if (classContributor != null) {
                        classDefMap = classContributor.getDefinitionReaderMap();
                    } else {
                        classDefMap =
                                new HashMap<String, List<JsClassDefinitionReader>>();
                        classContributor =
                                new ClassContributor(tempDest,
                                        elementGrammarType, classDefMap);
                    }
                    IConfigurationElement[] contextTypeElementsFor =
                            destinationElement.getChildren(CONTEXT_TYPE);
                    for (IConfigurationElement scriptContextElement : contextTypeElementsFor) {
                        if (scriptContextElement != null) {
                            String tempScriptType =
                                    scriptContextElement
                                            .getAttribute(SCRIPT_TYPE);
                            Object object =
                                    element
                                            .createExecutableExtension(CLASS_NAME);
                            if (object instanceof IProcessJsDefinitionReader) {
                                IProcessJsDefinitionReader tempProvider =
                                        (IProcessJsDefinitionReader) object;
                                tempProvider.setDestinationName(tempDest);
                                tempProvider.setScriptType(tempScriptType);
                                List<JsClassDefinitionReader> existingDefinitionReaders =
                                        classDefMap.get(tempScriptType);
                                if (existingDefinitionReaders == null) {
                                    existingDefinitionReaders =
                                            new ArrayList<JsClassDefinitionReader>();
                                }
                                existingDefinitionReaders.add(tempProvider);
                                classDefMap.put(tempScriptType,
                                        existingDefinitionReaders);
                                if (!classContributorList
                                        .contains(classContributor)) {
                                    classContributorList.add(classContributor);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void readModelContributors(IConfigurationElement element,
            List<ClassContributor> classContributorList) throws CoreException {
        // Get the destination environments for the validation strategy class
        IConfigurationElement[] destinationElementsFor =
                element.getChildren(DESTINATION_ENVIRONMENT);
        // Get the Grammar Type
        String elementGrammarType = element.getAttribute(GRAMMAR_TYPE);
        if (destinationElementsFor != null) {
            for (IConfigurationElement destinationElement : destinationElementsFor) {
                if (destinationElement != null) {
                    // Get the context types for the destination environment of
                    // the class
                    String tempDest =
                            destinationElement
                                    .getAttribute(DESTINATION_ENVIRONMENT);
                    ClassContributor classContributor =
                            getClassContributor(tempDest, elementGrammarType);
                    Map<String, List<JsClassDefinitionReader>> classDefMap =
                            null;
                    if (classContributor != null) {
                        classDefMap = classContributor.getDefinitionReaderMap();
                    } else {
                        classDefMap =
                                new HashMap<String, List<JsClassDefinitionReader>>();
                        classContributor =
                                new ClassContributor(tempDest,
                                        elementGrammarType, classDefMap);
                    }
                    IConfigurationElement[] contextTypeElementsFor =
                            destinationElement.getChildren(CONTEXT_TYPE);
                    for (IConfigurationElement scriptContextElement : contextTypeElementsFor) {
                        if (scriptContextElement != null) {
                            String tempScriptType =
                                    scriptContextElement
                                            .getAttribute(SCRIPT_TYPE);
                            URI modelUri = null;
                            String model = element.getAttribute(MODEL_NAME);
                            if (model != null) {
                                Bundle bundle =
                                        Platform.getBundle(element
                                                .getNamespaceIdentifier());
                                if (bundle != null) {
                                    URL entry = bundle.getEntry(model);
                                    if (entry != null) {
                                        modelUri =
                                                URI.createURI(entry
                                                        .toExternalForm());
                                    }
                                }
                            }
                            Image icon = null;
                            String iconPath = element.getAttribute(ICON);
                            // Get the icon descriptor
                            if (iconPath != null) {
                                ImageDescriptor imageDescriptor =
                                        AbstractUIPlugin
                                                .imageDescriptorFromPlugin(element
                                                        .getNamespaceIdentifier(),
                                                        iconPath);
                                icon = imageDescriptor.createImage();
                            }
                            if (modelUri != null) {
                                IProcessModelDefinitionReader tempProvider =
                                        getModelDefinitionReader(modelUri
                                                .fileExtension());
                                if (tempProvider != null) {
                                    tempProvider.setUri(modelUri);
                                    tempProvider.setImage(icon);
                                    tempProvider.setDestinationName(tempDest);
                                    tempProvider.setScriptType(tempScriptType);
                                    List<JsClassDefinitionReader> existingDefinitionReaders =
                                            classDefMap.get(tempScriptType);
                                    if (existingDefinitionReaders == null) {
                                        existingDefinitionReaders =
                                                new ArrayList<JsClassDefinitionReader>();
                                    }
                                    existingDefinitionReaders.add(tempProvider);
                                    classDefMap.put(tempScriptType,
                                            existingDefinitionReaders);
                                    if (!classContributorList
                                            .contains(classContributor)) {
                                        classContributorList
                                                .add(classContributor);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private IProcessModelDefinitionReader getModelDefinitionReader(
            String fileExtension) {
        if (modelDefinitionReaderMap == null) {
            readModelDefinitionReader();
        }
        // Get the model from the map
        if (fileExtension != null) {
            IProcessModelDefinitionReader definitionReader =
                    modelDefinitionReaderMap.get(fileExtension);
            if (definitionReader == null) {
                // Return the default model
                definitionReader = new DefaultModelProcessDefinitionReader();
            }
            return definitionReader;
        }
        return null;
    }

    private synchronized void readModelDefinitionReader() {
        if (modelDefinitionReaderMap == null) {
            modelDefinitionReaderMap =
                    new HashMap<String, IProcessModelDefinitionReader>();
            IExtensionRegistry extensionRegistry =
                    Platform.getExtensionRegistry();
            IConfigurationElement[] configurationElementsFor =
                    extensionRegistry.getConfigurationElementsFor(PLUGIN_ID,
                            MODEL_DEFINITION_READERS_CONTRIBUTION);
            List<ModelDefinitionReaderContributor> modelDefinitionReaderContributors =
                    new ArrayList<ModelDefinitionReaderContributor>();
            for (IConfigurationElement element : configurationElementsFor) {
                if (element != null) {
                    try {
                        Object object =
                                element.createExecutableExtension(CLASS);
                        if (object instanceof IProcessModelDefinitionReader) {
                            IProcessModelDefinitionReader definitionReader =
                                    (IProcessModelDefinitionReader) object;
                            String fileExtensions =
                                    element.getAttribute(FILE_EXTENSIONS);
                            if (fileExtensions != null) {
                                String[] fileExtensionsArr =
                                        getFileExtensionArr(fileExtensions);
                                if (fileExtensionsArr != null) {
                                    String priority =
                                            element.getAttribute(PRIORITY);
                                    if (priority != null) {
                                        Priority priorityEnum =
                                                Priority.valueOf(priority);
                                        for (String extension : fileExtensionsArr) {
                                            modelDefinitionReaderContributors
                                                    .add(new ModelDefinitionReaderContributor(
                                                            definitionReader,
                                                            extension.trim(),
                                                            priorityEnum));
                                        }
                                    }
                                }
                            }
                        }
                    } catch (CoreException e) {
                        String errorMsg =
                                Messages.Activator_Error_Reading_Model_ModelContibution;
                        logger.error(e, errorMsg);
                    }
                }
            }
            // Choose the readers with higher priority
            if (modelDefinitionReaderContributors != null
                    && !modelDefinitionReaderContributors.isEmpty()) {
                for (ModelDefinitionReaderContributor modelDefinitionReaderContributor : modelDefinitionReaderContributors) {
                    if (modelDefinitionReaderContributor != null
                            && isHighestPossiblePriority(modelDefinitionReaderContributors,
                                    modelDefinitionReaderContributor)) {
                        modelDefinitionReaderMap
                                .put(modelDefinitionReaderContributor
                                        .getExtension(),
                                        modelDefinitionReaderContributor
                                                .getModelDefinitionReader());
                    }
                }
            }
        }
    }

    private boolean isHighestPossiblePriority(
            List<ModelDefinitionReaderContributor> allModelDefinitionReaderContributors,
            ModelDefinitionReaderContributor modelDefinitionReaderContributor) {
        String extension = modelDefinitionReaderContributor.getExtension();
        Priority priority = modelDefinitionReaderContributor.getPriority();
        for (ModelDefinitionReaderContributor contributor : allModelDefinitionReaderContributors) {
            if (contributor != null && contributor.getExtension() != null
                    && contributor.getExtension().equals(extension)) {
                if (contributor.getPriority().getPriorityDegree() > priority
                        .getPriorityDegree()) {
                    return false;
                }
            }
        }
        return true;
    }

    private String[] getFileExtensionArr(String fileExtensions) {
        if (fileExtensions != null) {
            return fileExtensions.split(","); //$NON-NLS-1$
        }
        return null;
    }

    /*@SuppressWarnings("unchecked")//$NON-NLS-1$
    public List<JsClassDefinitionReader> getJsClassDefinitionReader(
            List<String> destinations, String scriptType, String grammarType,
            String defaultDestination) {
        if (classContributorList == null) {
            readClassDefinitionReader();
        }
        List<JsClassDefinitionReader> toReturn =
                new ArrayList<JsClassDefinitionReader>();
        if (destinations == null || destinations.size() < 1) {
            Map<String, List<JsClassDefinitionReader>> jsClassDefMap =
                getClassDefinitionMap(defaultDestination,
                        grammarType);
            if (jsClassDefMap != null) {
                List<JsClassDefinitionReader> jsClassReaders =
                        jsClassDefMap.get(scriptType);
                if (jsClassReaders != null) {
                    toReturn.addAll(jsClassReaders);
                }
            }
            return toReturn;
        }
        for (String eachDestination : destinations) {
            Map<String, List<JsClassDefinitionReader>> classDefMap =
                    getClassDefinitionMap(eachDestination,
                            grammarType);
            if (classDefMap != null) {
                List<JsClassDefinitionReader> classDefReaders =
                        classDefMap.get(scriptType);
                if (classDefReaders != null) {
                    toReturn.addAll(classDefReaders);
                }
            }
        }
        return toReturn;
    }
    
    */
    private ClassContributor getClassContributor(String destination,
            String grammarType) {
        if (classContributorList != null) {
            for (ClassContributor actualClassContributor : classContributorList) {
                if (actualClassContributor != null) {
                    String contributorDestination =
                            actualClassContributor.getDestination();
                    if (destination != null && contributorDestination != null
                            && destination.equals(contributorDestination)) {
                        String contributorGrammarType =
                                actualClassContributor.getGrammarType();
                        if (grammarType != null
                                && contributorGrammarType != null
                                && grammarType.equals(contributorGrammarType)) {
                            return actualClassContributor;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * This method returns the list of definition readers for destinations which
     * are not passed in the list. So, definition readers for all destinations
     * are returned except for ones which are passed as parameter
     * 
     * @param destinations
     * @return
     */
    public List<JsClassDefinitionReader> getOtherJsClassDefinitionReader(
            List<String> destinations, String scriptType, String grammarType) {
        if (classContributorList == null) {
            readClassDefinitionReader();
        }
        List<JsClassDefinitionReader> toReturn =
                new ArrayList<JsClassDefinitionReader>();
        for (ClassContributor classContributor : classContributorList) {
            if (classContributor != null) {
                if (classContributor.grammarType.equals(grammarType)) {
                    String tempDest = classContributor.getDestination();
                    boolean b = destinations.contains(tempDest);
                    if (!b) {
                        Map<String, List<JsClassDefinitionReader>> classDefMap =
                                classContributor.getDefinitionReaderMap();
                        List<JsClassDefinitionReader> tempReaders = null;
                        if (classDefMap != null) {
                            tempReaders = classDefMap.get(scriptType);
                        }
                        if (tempReaders != null) {
                            toReturn.addAll(tempReaders);
                        }
                    }
                }
            }
        }
        return toReturn;
    }

    public List<JsClassDefinitionReader> getJsClassDefinitionReadersForDestination(
            String destination, String scriptType, String grammarType) {
        if (classContributorList == null) {
            readClassDefinitionReader();
        }
        Map<String, List<JsClassDefinitionReader>> classDefMap =
                getClassDefinitionMap(destination, grammarType);
        List<JsClassDefinitionReader> readers = null;
        if (classDefMap != null) {
            readers = classDefMap.get(scriptType);
        }

        return readers;
    }

    private Map<String, List<JsClassDefinitionReader>> getClassDefinitionMap(
            String destination, String grammarType) {
        ClassContributor classContributor =
                getClassContributor(destination, grammarType);
        Map<String, List<JsClassDefinitionReader>> classDefMap = null;
        classDefMap = new HashMap<String, List<JsClassDefinitionReader>>();
        if (classContributor != null) {
            Map<String, List<JsClassDefinitionReader>> definitionReaderMap =
                    classContributor.getDefinitionReaderMap();
            if (definitionReaderMap != null) {
                Set<String> keySet = definitionReaderMap.keySet();
                for (String key : keySet) {
                    List<JsClassDefinitionReader> classDefinitionReaderList =
                            classDefMap.get(key);
                    if (classDefinitionReaderList != null) {
                        List<JsClassDefinitionReader> list =
                                definitionReaderMap.get(key);
                        for (JsClassDefinitionReader jsClassDefinitionReader : list) {
                            if (!classDefinitionReaderList
                                    .contains(jsClassDefinitionReader)) {
                                classDefinitionReaderList
                                        .add(jsClassDefinitionReader);
                            }
                        }
                    } else {
                        classDefMap.put(key, definitionReaderMap.get(key));
                    }
                }
            }
        }
        return classDefMap;
    }

    class ClassContributor {

        private String destination;

        private String grammarType;

        private Map<String, List<JsClassDefinitionReader>> definitionReaderMap;

        public ClassContributor(String destination, String grammarType,
                Map<String, List<JsClassDefinitionReader>> definitionReaderMap) {
            this.destination = destination;
            this.grammarType = grammarType;
            this.definitionReaderMap = definitionReaderMap;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public String getGrammarType() {
            return grammarType;
        }

        public void setGrammarType(String grammarType) {
            this.grammarType = grammarType;
        }

        public Map<String, List<JsClassDefinitionReader>> getDefinitionReaderMap() {
            if (definitionReaderMap == null) {
                definitionReaderMap =
                        new HashMap<String, List<JsClassDefinitionReader>>();
            }
            return definitionReaderMap;
        }

        public void setDefinitionReaderMap(
                Map<String, List<JsClassDefinitionReader>> definitionReaderMap) {
            this.definitionReaderMap = definitionReaderMap;
        }

    }

    class ModelDefinitionReaderContributor {

        private IProcessModelDefinitionReader modelDefinitionReader = null;

        private String extension = null;

        private Priority priority = null;

        public ModelDefinitionReaderContributor(
                IProcessModelDefinitionReader modelDefinitionReader,
                String extension, Priority priority) {
            this.modelDefinitionReader = modelDefinitionReader;
            this.extension = extension;
            this.priority = priority;
        }

        public IProcessModelDefinitionReader getModelDefinitionReader() {
            return modelDefinitionReader;
        }

        public String getExtension() {
            return extension;
        }

        public Priority getPriority() {
            return priority;
        }

    }

    enum Priority {

        HIGHEST(4), HIGH(3), MEDIUM(2), LOW(1), LOWEST(0);

        private final int priorityDegree;

        Priority(int priorityDegree) {
            this.priorityDegree = priorityDegree;
        }

        public int getPriorityDegree() {
            return priorityDegree;
        }
    }

}
