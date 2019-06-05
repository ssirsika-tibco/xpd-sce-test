/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.script.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;
import com.tibco.xpd.script.sourceviewer.internal.contentassist.AbstractTibcoContentAssistProcessor;
import com.tibco.xpd.script.sourceviewer.internal.preferences.AbstractScriptCommonUIPreferenceNames;
import com.tibco.xpd.script.sourceviewer.internal.viewer.listeners.AbstractLineStyleListenerProvider;
import com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider;
import com.tibco.xpd.script.ui.internal.AbstractScriptRelevantDataProvider;
import com.tibco.xpd.script.ui.internal.IProcessJsDefinitionReader;
import com.tibco.xpd.script.ui.internal.IProcessModelDefinitionReader;
import com.tibco.xpd.script.ui.internal.IScriptGrammarFilter;
import com.tibco.xpd.script.ui.internal.Messages;
import com.tibco.xpd.script.ui.internal.ResourceMarkerAnnotationModelProvider;
import com.tibco.xpd.script.ui.internal.extension.CertifiedContributions;
import com.tibco.xpd.script.ui.internal.extension.ModelDefinitionReader;
import com.tibco.xpd.script.ui.internal.extension.ScriptGrammarDestinationBindingElement;
import com.tibco.xpd.script.ui.internal.extension.ScriptGrammarDestinationBindingElement.ClassContributor;
import com.tibco.xpd.script.ui.internal.extension.ScriptGrammarDestinationBindingElement.ModelContributor;
import com.tibco.xpd.script.ui.internal.extension.ScriptGrammarDestinationBindingElement.ScriptContextGrammarBinding;
import com.tibco.xpd.script.ui.internal.extension.ScriptGrammarDestinationBindingElement.ScriptContribution;
import com.tibco.xpd.script.ui.internal.extension.ScriptGrammarDestinationBindingElement.ScriptGrammarReferenceElement;
import com.tibco.xpd.script.ui.internal.extension.ScriptGrammarDestinationBindingElement.ScriptRelevantDataContributor;
import com.tibco.xpd.script.ui.internal.extension.ScriptGrammarDestinationBindingElement.ValidationStrategy;
import com.tibco.xpd.script.ui.internal.extension.ScriptGrammarElement;

/**
 * Utility to read the contributions to the extension points this plugin
 * exposes.
 * 
 * @author rsomayaj
 * 
 */
public class ScriptGrammarContributionsUtil {

    /**
     * Default INSTANCE to read
     */
    public static ScriptGrammarContributionsUtil INSTANCE =
            new ScriptGrammarContributionsUtil();

    private List<ScriptGrammarDestinationBindingElement> scriptGrammarDestinationBindingElements =
            Collections.EMPTY_LIST;

    private List<ScriptGrammarElement> scriptGrammarElements =
            Collections.EMPTY_LIST;

    private Map<String, ScriptGrammarElement> scriptGrammarElementsMap;

    // Map to store file extension vs ModelDefinitionReader
    private Map<String, Set<ModelDefinitionReader>> modelDefinitionReaders;

    private Map<String, Set<ScriptContextGrammarBinding>> destinationContextMap =
            new HashMap<String, Set<ScriptContextGrammarBinding>>();

    // Map for destination+context+grammarId vs List of JsClassDefinitionReader
    Map<String, List<JsClassDefinitionReader>> classDefinitionReaderMap =
            new HashMap<String, List<JsClassDefinitionReader>>();

    private ScriptGrammarContributionsUtil() {
        // NO ACCESS TO INSTATIATE
    }

    /**
     * Utility to find the ModelDefinitionReader for a model of a given file
     * extensions(for eg: uml, bom)
     * 
     * @param fileExtension
     * @return
     * @throws CoreException
     */
    private Map<String, Set<ModelDefinitionReader>> getModelDefinitionReader()
            throws CoreException {
        if (this.modelDefinitionReaders != null) {
            return this.modelDefinitionReaders;
        }
        IConfigurationElement[] configElements =
                Platform
                        .getExtensionRegistry()
                        .getConfigurationElementsFor(ScriptUIPlugin.PLUGIN_ID,
                                ModelDefinitionReader.EXT_POINT_MODEL_DEFINITION_READER);
        if (configElements.length > 0) {
            modelDefinitionReaders =
                    new HashMap<String, Set<ModelDefinitionReader>>();
        }
        for (IConfigurationElement configurationElement : configElements) {
            ModelDefinitionReader defnReader =
                    new ModelDefinitionReader(configurationElement);
            String[] fileExtensions = defnReader.getFileExtensions();
            for (String fileExtn : fileExtensions) {
                Set<ModelDefinitionReader> fileExtnDefnReaderList =
                        modelDefinitionReaders.get(fileExtn);
                if (fileExtnDefnReaderList == null) {
                    fileExtnDefnReaderList =
                            new HashSet<ModelDefinitionReader>();
                    modelDefinitionReaders
                            .put(fileExtn, fileExtnDefnReaderList);
                }
                fileExtnDefnReaderList.add(defnReader);
            }
        }
        return this.modelDefinitionReaders;
    }

    /**
     * Reads the contributions to the "scriptGrammar" extension point
     * 
     * @return
     */
    public Map<String, ScriptGrammarElement> getContributedScriptGrammars() {
        /*if (scriptGrammarElementsMap != null) {
            return scriptGrammarElementsMap;
        }*/

        List<ScriptGrammarElement> scriptGrammarElements =
                getScriptGrammarElements();

        scriptGrammarElementsMap = new HashMap<String, ScriptGrammarElement>();

        for (ScriptGrammarElement scriptGrammarElement : scriptGrammarElements) {
            String scriptGrammarId = scriptGrammarElement.getGrammarId();
            // Only those grammars certified by Studio can be used.
            if (CertifiedContributions.getCertifiedScriptGramm()
                    .isCertified(scriptGrammarId)) {
                scriptGrammarElementsMap.put(scriptGrammarId,
                        scriptGrammarElement);
            }
        }

        return scriptGrammarElementsMap;
    }

    /**
     * Get all the contexts bindings associated with a certain destination.
     * 
     * @param destinationId
     * @return
     */
    public Set<ScriptContextGrammarBinding> getContextsAssociatedWithDestination(
            String destinationId) {
        if (destinationContextMap.get(destinationId) != null) {
            return destinationContextMap.get(destinationId);
        }
        Set<ScriptContextGrammarBinding> bindings =
                new HashSet<ScriptContextGrammarBinding>();
        List<ScriptGrammarDestinationBindingElement> scriptGrammarDestinationBindingElements =
                getScriptGrammarDestinationBindingElements();
        for (ScriptGrammarDestinationBindingElement scriptElement : scriptGrammarDestinationBindingElements) {
            if (destinationId.equals(scriptElement.getDestinationId())) {
                bindings
                        .addAll(scriptElement.getScriptContextGrammarBindings());
            }
        }
        destinationContextMap.put(destinationId, bindings);
        return bindings;
    }

    /**
     * For a given destination, go through all the contexts and find the
     * grammars that are associated. Returns the grammar element specific
     * details which include the EditorSection, content assist processor and
     * line-style provider
     * 
     * @param destinationId
     * @return
     */
    @SuppressWarnings("unchecked")
    public Set<ScriptGrammarElement> getGrammarsAssociatedWithDestination(
            String destinationId) {
        Set<ScriptGrammarElement> scriptGrammarsForGivenDestination =
                Collections.EMPTY_SET;
        if (destinationId != null) {
            scriptGrammarsForGivenDestination =
                    new HashSet<ScriptGrammarElement>();

            List<ScriptGrammarDestinationBindingElement> bindingElements =
                    getScriptGrammarDestinationBindingElements();

            Map<String, ScriptGrammarElement> contributedScriptGrammars =
                    getContributedScriptGrammars();
            for (ScriptGrammarDestinationBindingElement scriptGrammarDestinationBindingElement : bindingElements) {
                String scriptGrammarDestId =
                        scriptGrammarDestinationBindingElement
                                .getDestinationId();
                if (destinationId.equals(scriptGrammarDestId)) {
                    Collection<ScriptContextGrammarBinding> contextBindings =
                            scriptGrammarDestinationBindingElement
                                    .getScriptContextGrammarBindings();
                    for (ScriptContextGrammarBinding scriptContext : contextBindings) {

                        List<ScriptGrammarReferenceElement> grammarsForContext =
                                scriptContext.getGrammarsForContext();
                        for (ScriptGrammarReferenceElement refElement : grammarsForContext) {
                            String grammarReference =
                                    getGrammarFromScriptGrammarReference(refElement);
                            scriptGrammarsForGivenDestination
                                    .add(contributedScriptGrammars
                                            .get(grammarReference));
                        }
                    }
                }
            }
        }
        return scriptGrammarsForGivenDestination;
    }

    /**
     * Given a destination id and a script grammar, this method returns a list
     * of Script Context Bindings.
     * 
     * @param destinationId
     * @param grammar
     * @return
     */
    public List<ScriptContextGrammarBinding> getContextsForGivenGrammarAndDestination(
            String destinationId, String grammar) {
        List<ScriptGrammarDestinationBindingElement> destinationBindingElements =
                getScriptGrammarDestinationBindingElements();

        List<ScriptContextGrammarBinding> scriptContextBindings =
                new ArrayList<ScriptContextGrammarBinding>();

        for (ScriptGrammarDestinationBindingElement scriptGrammarDestinationBindingElement : destinationBindingElements) {
            if (scriptGrammarDestinationBindingElement.getDestinationId()
                    .equals(destinationId)) {
                Collection<ScriptContextGrammarBinding> contextBindings =
                        scriptGrammarDestinationBindingElement
                                .getScriptContextGrammarBindings();

                for (ScriptContextGrammarBinding scriptContextGrammarBinding : contextBindings) {
                    List<ScriptGrammarReferenceElement> grammarsForContext =
                            scriptContextGrammarBinding.getGrammarsForContext();
                    for (ScriptGrammarReferenceElement grammarRefElement : grammarsForContext) {
                        if (getGrammarFromScriptGrammarReference(grammarRefElement)
                                .equals(grammar)) {
                            scriptContextBindings
                                    .add(scriptContextGrammarBinding);
                        }
                    }
                }
            }
        }
        return scriptContextBindings;
    }

    /**
     * Returns a list of script grammars for a given Context for a given
     * Destination
     * 
     * @param destinationId
     * @param scriptContextReference
     * @return
     */
    public Set<ScriptGrammarReferenceElement> getGrammarForGivenContextAndDestination(
            String destinationId, String scriptContextReference) {
        Set<ScriptContextGrammarBinding> contextsAssociatedWithDestination =
                getContextsAssociatedWithDestination(destinationId);

        Set<ScriptGrammarReferenceElement> scriptGrammarReferenceElements =
                new HashSet<ScriptGrammarReferenceElement>();

        for (ScriptContextGrammarBinding contextBinding : contextsAssociatedWithDestination) {
            if (contextBinding.getScriptContextReference()
                    .equals(scriptContextReference)) {
                scriptGrammarReferenceElements.addAll(contextBinding
                        .getGrammarsForContext());
            }
        }
        return scriptGrammarReferenceElements;
    }

    /**
     * Retrieve the list of destinations that contribute to
     * scriptGrammarDestinationBinding extension point.
     * 
     * @return
     */
    public Set<String> getContributedDestinations() {
        Set<String> destinations = new HashSet<String>();
        List<ScriptGrammarDestinationBindingElement> destinationBindingElements =
                getScriptGrammarDestinationBindingElements();

        for (ScriptGrammarDestinationBindingElement scriptGrammarDestinationBindingElement : destinationBindingElements) {
            destinations.add(scriptGrammarDestinationBindingElement
                    .getDestinationId());
        }
        return destinations;
    }

    /**
     * Returns the grammar id for a given scriptGrammarReference element.
     * 
     * @param scriptGrammarReferenceElement
     * @return
     */
    private String getGrammarFromScriptGrammarReference(
            ScriptGrammarReferenceElement scriptGrammarReferenceElement) {
        return scriptGrammarReferenceElement.getGrammar();
    }

    /**
     * Returns the script info provider for a given destination, context and
     * grammar.
     * 
     * @param destination
     * @param scriptContext
     * @param grammarId
     * @return
     * @throws CoreException
     */
    public AbstractScriptInfoProvider getScriptInfoProvider(String destination,
            String scriptContext, String grammarId) throws CoreException {
        // Gets the list of context bindings for a given destination id
        Set<ScriptContextGrammarBinding> scriptContextBindingElements =
                getScriptContextBindingElement(destination, scriptContext);
        AbstractScriptInfoProvider returnElement = null;
        if (!scriptContextBindingElements.isEmpty()) {
            List<ScriptGrammarReferenceElement> scriptGrammarReferenceElementFromScriptContexts =
                    getScriptGrammarReferenceElementFromScriptContexts(scriptContextBindingElements,
                            grammarId);
            // Assuming that there should be only one scriptGrammarRefElement
            // for a given Destination and given context
            ScriptGrammarReferenceElement scriptGrammarReferenceElement;
            if (!scriptGrammarReferenceElementFromScriptContexts.isEmpty()) {
                scriptGrammarReferenceElement =
                        scriptGrammarReferenceElementFromScriptContexts.get(0);
                returnElement =
                        scriptGrammarReferenceElement.getScriptInfoProvider();
            }
        }
        return returnElement;
    }

    /**
     * @param scriptContextBindingElements
     * @param grammarId
     * @return
     */
    private List<ScriptGrammarReferenceElement> getScriptGrammarReferenceElementFromScriptContexts(
            Collection<ScriptContextGrammarBinding> scriptContextBindingElements,
            String grammarId) {

        List<ScriptGrammarReferenceElement> scriptGrammarRefElements =
                new ArrayList<ScriptGrammarReferenceElement>();
        for (ScriptContextGrammarBinding scriptContextGrammarBinding : scriptContextBindingElements) {
            List<ScriptGrammarReferenceElement> grammarsForContext =
                    scriptContextGrammarBinding.getGrammarsForContext();
            for (ScriptGrammarReferenceElement scriptGrammarReferenceElement : grammarsForContext) {
                if (scriptGrammarReferenceElement.getGrammar()
                        .equals(grammarId)) {
                    scriptGrammarRefElements.add(scriptGrammarReferenceElement);
                }
            }
        }
        return scriptGrammarRefElements;
    }

    /**
     * 
     * @param destination
     * @param scriptContext
     * @return
     */
    private Set<ScriptContextGrammarBinding> getScriptContextBindingElement(
            String destination, String scriptContext) {
        // Gets all the script grammar destination binding elements
        List<ScriptGrammarDestinationBindingElement> scriptGrammarDestinationElements =
                getScriptGrammarDestinationBindingElements();
        // New Set to add all the script context grammar bindings to
        Set<ScriptContextGrammarBinding> scriptContextGrammarBindings =
                new HashSet<ScriptContextGrammarBinding>();

        // For each of the script grammar destination binding elements, get
        // those whose destination matches the given one, find the script
        // contexts that are contributed with the destination, and match those
        // to the script context provided
        for (ScriptGrammarDestinationBindingElement destElement : scriptGrammarDestinationElements) {
            if (destination.equals(destElement.getDestinationId())) {
                Collection<ScriptContextGrammarBinding> contextsAssociatedWithDestination =
                        destElement.getScriptContextGrammarBindings();
                for (ScriptContextGrammarBinding scriptContextBinding : contextsAssociatedWithDestination) {
                    if (scriptContext.equals(scriptContextBinding
                            .getScriptContextReference())) {
                        scriptContextGrammarBindings.add(scriptContextBinding);
                    }
                }
            }
        }
        return scriptContextGrammarBindings;
    }

    /**
     * @return
     */
    private List<ScriptGrammarDestinationBindingElement> getScriptGrammarDestinationBindingElements() {
        if (!scriptGrammarDestinationBindingElements.isEmpty()) {
            return scriptGrammarDestinationBindingElements;
        }
        IConfigurationElement[] scriptGrammarDestinationBindings =
                Platform
                        .getExtensionRegistry()
                        .getConfigurationElementsFor(ScriptUIPlugin.PLUGIN_ID,
                                ScriptGrammarDestinationBindingElement.EXTN_POINT_SCRIPT_GRAMMAR_DEST_BINDING);
        scriptGrammarDestinationBindingElements =
                new ArrayList<ScriptGrammarDestinationBindingElement>();
        for (IConfigurationElement configElement : scriptGrammarDestinationBindings) {
            ScriptGrammarDestinationBindingElement destElement =
                    new ScriptGrammarDestinationBindingElement(configElement);
            scriptGrammarDestinationBindingElements.add(destElement);
        }
        return scriptGrammarDestinationBindingElements;
    }

    /**
     * @return
     */
    private List<ScriptGrammarElement> getScriptGrammarElements() {
        /* if (!scriptGrammarElements.isEmpty()) {
             return scriptGrammarElements;
         }*/
        IConfigurationElement[] configurationElements =
                Platform
                        .getExtensionRegistry()
                        .getConfigurationElementsFor(ScriptUIPlugin.PLUGIN_ID,
                                ScriptGrammarElement.SCRIPT_GRAMMAR_EXTENSION_POINT_NAME);
        scriptGrammarElements = new ArrayList<ScriptGrammarElement>();
        for (IConfigurationElement configElement : configurationElements) {
            ScriptGrammarElement scriptGrammarElement =
                    new ScriptGrammarElement(configElement);
            scriptGrammarElements.add(scriptGrammarElement);
        }
        return scriptGrammarElements;

    }

    /**
     * Retrieve the Content Assist Processor for a given grammar.
     * 
     * @param grammarId
     * @return
     * @throws CoreException
     */
    public AbstractTibcoContentAssistProcessor getContentAssistProcessor(
            String grammarId) throws CoreException {
        AbstractTibcoContentAssistProcessor contentAssistProcessor = null;
        Map<String, ScriptGrammarElement> contributedScriptGrammars =
                getContributedScriptGrammars();
        ScriptGrammarElement scriptGrammarElement =
                contributedScriptGrammars.get(grammarId);

        if (scriptGrammarElement != null) {
            contentAssistProcessor =
                    scriptGrammarElement.getContentAssistProcessor();
        }
        return contentAssistProcessor;

    }

    /**
     * Retrieve the Line Style Listener Provider for a given grammar.
     * 
     * @param grammarId
     * @return
     * @throws CoreException
     */
    public AbstractLineStyleListenerProvider getLineStyleListenerProvider(
            String grammarId) throws CoreException {
        AbstractLineStyleListenerProvider lineStyleListenerProvider = null;
        Map<String, ScriptGrammarElement> contributedScriptGrammars =
                getContributedScriptGrammars();
        ScriptGrammarElement scriptGrammarElement =
                contributedScriptGrammars.get(grammarId);

        if (scriptGrammarElement != null) {
            lineStyleListenerProvider =
                    scriptGrammarElement.getLineStyleListenerProvider();
        }
        return lineStyleListenerProvider;
    }

    /**
     * Utility to retrieve the Script UI Preference names. This reads the
     * contributions to the content assist processor and returns preference
     * names specific to the grammar
     * 
     * @param grammarId
     * @return
     * @throws CoreException
     */
    public AbstractScriptCommonUIPreferenceNames getScriptCommonUIPreferenceNames(
            String grammarId) throws CoreException {
        AbstractScriptCommonUIPreferenceNames scriptPreferenceNames = null;
        Map<String, ScriptGrammarElement> contributedScriptGrammars =
                getContributedScriptGrammars();
        ScriptGrammarElement scriptGrammarElement =
                contributedScriptGrammars.get(grammarId);
        if (scriptGrammarElement != null) {
            scriptPreferenceNames =
                    scriptGrammarElement.getScriptCommonUIPreferenceNames();
        }
        return scriptPreferenceNames;

    }

    /**
     * @param destinationList
     * @param scriptContext
     * @param grammarId
     * @param defaultDestination
     * @return
     * @throws CoreException
     */
    public List<IValidationStrategy> getValidationStrategy(
            List<String> destinationList, String scriptContext,
            String grammarId, String defaultDestination) throws CoreException {
        List<ScriptGrammarDestinationBindingElement> scriptGrammarDestinationBindingElements =
                getScriptGrammarDestinationBindingElements();
        List<IValidationStrategy> validationStrategies =
                new ArrayList<IValidationStrategy>();

        Collection<String> destinations = destinationList;
        if (destinationList.isEmpty() && defaultDestination != null) {
            destinations = Collections.singletonList(defaultDestination);
        }

        // else if (!destinations.contains(defaultDestination)) {
        // destinations.add(defaultDestination);
        // }
        for (String destination : destinations) {
            for (ScriptGrammarDestinationBindingElement element : scriptGrammarDestinationBindingElements) {
                if (destination.equals(element.getDestinationId())) {
                    Collection<ScriptContribution> scriptContributions =
                            element.getScriptContributions();
                    for (ScriptContribution scriptContribution : scriptContributions) {
                        if (scriptContribution.getScriptContext()
                                .equals(scriptContext)) {
                            List<ValidationStrategy> validationStrategy =
                                    scriptContribution.getValidationStrategy();
                            for (ValidationStrategy strategy : validationStrategy) {
                                if (grammarId.equals(strategy.getGrammarId())) {
                                    IValidationStrategy validationStrategyClass =
                                            strategy
                                                    .getValidationStrategyClass();
                                    validationStrategyClass.getErrorList()
                                            .clear();
                                    validationStrategyClass
                                            .setDestinationName(destination);
                                    validationStrategyClass
                                            .setScriptType(scriptContext);
                                    validationStrategies
                                            .add(validationStrategyClass);
                                }
                            }
                        }
                    }
                }
            }
        }
        return validationStrategies;

    }

    /**
     * This classes finds the class definition readers for a given destination,
     * context, and grammar
     * 
     * If the destinations list is empty, expect to provide the list of class
     * definition readers for the default destination provided.
     * 
     * @param destinationList
     * @param scriptType
     * @param xpathGrammar
     * @param xpathDestination
     * @return
     * @throws CoreException
     */
    public synchronized List<JsClassDefinitionReader> getJsClassDefinitionReader(
            List<String> destinationList, String scriptContext,
            String grammarId, String defaultDestination) throws CoreException {
        List<ScriptGrammarDestinationBindingElement> scriptGrammarDestinationBindingElements =
                getScriptGrammarDestinationBindingElements();
        List<JsClassDefinitionReader> classDefinitionReaders =
                new ArrayList<JsClassDefinitionReader>();

        Collection<String> destinations = destinationList;
        if (destinationList.isEmpty() && defaultDestination != null) {
            destinations = Collections.singletonList(defaultDestination);
        }

        for (String destination : destinations) {
            List<JsClassDefinitionReader> cachedClassDefnReader =
                    classDefinitionReaderMap
                            .get(getClassDefinitionReaderClassifier(destination,
                                    scriptContext,
                                    grammarId));
            if (cachedClassDefnReader != null) {
                classDefinitionReaders.addAll(cachedClassDefnReader);
            } else {
                List<JsClassDefinitionReader> classDefinitionReadersForDestContextGrammarId =
                        getClassDefinitionReaders(destination,
                                scriptContext,
                                grammarId,
                                scriptGrammarDestinationBindingElements);
                classDefinitionReaderMap
                        .put(getClassDefinitionReaderClassifier(destination,
                                scriptContext,
                                grammarId),
                                classDefinitionReadersForDestContextGrammarId);
                classDefinitionReaders
                        .addAll(classDefinitionReadersForDestContextGrammarId);
            }
        }
        return classDefinitionReaders;
    }

    /**
     * @param destination
     * @param scriptContext
     * @param grammarId
     * @param scriptGrammarDestinationBindingElements
     * @param classDefinitionReaders
     * @throws CoreException
     */
    private List<JsClassDefinitionReader> getClassDefinitionReaders(
            String destination,
            String scriptContext,
            String grammarId,
            List<ScriptGrammarDestinationBindingElement> scriptGrammarDestinationBindingElements)
            throws CoreException {
        List<JsClassDefinitionReader> classDefinitionReaders =
                new ArrayList<JsClassDefinitionReader>();
        for (ScriptGrammarDestinationBindingElement element : scriptGrammarDestinationBindingElements) {
            if (destination.equals(element.getDestinationId())) {
                Collection<ScriptContribution> scriptContributions =
                        element.getScriptContributions();

                for (ScriptContribution scriptContribution : scriptContributions) {
                    if (scriptContribution.getScriptContext()
                            .equals(scriptContext)) {
                        List<ClassContributor> classContributors =
                                scriptContribution.getClassContributors();
                        for (ClassContributor classContributor : classContributors) {
                            if (grammarId.equals(classContributor
                                    .getGrammarId())) {
                                JsClassDefinitionReader classDefinitionReader =
                                        classContributor
                                                .getClassDefinitionReader();
                                if (classDefinitionReader instanceof IProcessJsDefinitionReader) {
                                    ((IProcessJsDefinitionReader) classDefinitionReader)
                                            .setDestinationName(destination);
                                    ((IProcessJsDefinitionReader) classDefinitionReader)
                                            .setScriptType(scriptContext);
                                }
                                classDefinitionReaders
                                        .add(classDefinitionReader);
                            }
                        }

                        List<ModelContributor> modelContributors =
                                scriptContribution.getModelContributors();
                        for (ModelContributor modelContributor : modelContributors) {
                            if (grammarId.equals(modelContributor.getGrammar())) {
                                URI modelURI = modelContributor.getModel();
                                if (modelURI != null) {
                                    // Model Definition readers are instantiated
                                    // individually for given file extension.
                                    IProcessModelDefinitionReader modelDefinitionReader =
                                            getModelDefinitionReader(modelURI
                                                    .fileExtension());
                                    modelDefinitionReader
                                            .setDestinationName(destination);
                                    modelDefinitionReader
                                            .setImage(modelContributor
                                                    .getIcon());
                                    modelDefinitionReader
                                            .setScriptType(scriptContext);
                                    modelDefinitionReader.setUri(modelURI);
                                    classDefinitionReaders
                                            .add(modelDefinitionReader);
                                } else {
                                    List<String> additionalAttributes =
                                            new ArrayList<String>();
                                    additionalAttributes.add(modelContributor
                                            .getName());
                                    String error =
                                            String
                                                    .format(Messages.ScriptGrammarContributionsUtil_Model_Contribution_UnResolved,
                                                            additionalAttributes
                                                                    .toArray());
                                    ScriptUIPlugin.getDefault().getLogger()
                                            .error(error);
                                }
                            }
                        }
                    }
                }
            }

        }
        return classDefinitionReaders;
    }

    /**
     * Returns the <code>IProcessModelDefinitionReader with the highest priority
     * 
     * @param fileExtension
     * @return
     * @throws CoreException
     */
    private IProcessModelDefinitionReader getModelDefinitionReader(
            String fileExtension) throws CoreException {
        Set<ModelDefinitionReader> modelDefinitionReaders =
                getModelDefinitionReader().get(fileExtension);
        ModelDefinitionReader readerWithHighestPriority =
                getModelDefinitionReaderWithHighestPriority(modelDefinitionReaders);

        if (readerWithHighestPriority != null) {
            // Instantiation happens on createExecutableExtension
            return readerWithHighestPriority.getModelDefinitionReader();
        }
        return null;
    }

    /**
     * Gets the model definition reader with the highest priority
     * 
     * @param modelDefinitionReaders
     */
    private ModelDefinitionReader getModelDefinitionReaderWithHighestPriority(
            Set<ModelDefinitionReader> modelDefinitionReaders) {
        ModelDefinitionReader modelDefnReader = null;
        for (ModelDefinitionReader modelDefinitionReader : modelDefinitionReaders) {
            if (modelDefnReader == null) {
                modelDefnReader = modelDefinitionReader;
            } else if (modelDefinitionReader.getPriority()
                    .isGreaterThan(modelDefnReader.getPriority())) {
                modelDefnReader = modelDefinitionReader;
            }
        }
        return modelDefnReader;
    }

    /**
     * This method provides the list model specific data(in an independent way)
     * which can be used by the script editor.
     * 
     * @param destinationList
     * @param scriptContext
     * @param grammarId
     * @param objectContext
     *            not to be confused with script context
     * @param defaultDestination
     * @return
     * @throws CoreException
     */
    public List<IScriptRelevantData> getScriptRelevantData(
            List<String> destinationList, String scriptContext,
            String grammarId, Object objectContext, String defaultDestination)
            throws CoreException {
        //XPD-4936 :Performance improvement for scripts validations
        List<AbstractScriptRelevantDataProvider> scriptRelevantDataProviders =
                getScriptRelevantDataProviders(destinationList,
                        scriptContext,
                        grammarId,
                        objectContext,
                        defaultDestination);

        List<IScriptRelevantData> scriptRelevantData =
                new ArrayList<IScriptRelevantData>();

        for (AbstractScriptRelevantDataProvider provider : scriptRelevantDataProviders) {
            scriptRelevantData.addAll(provider.getScriptRelevantDataList());
        }

        return scriptRelevantData;
    }

    /**
     * This method provides the list model specific script relevant data
     * providers data(in an independent way) which can be used by the script
     * editor.
     * <p>
     * Each data provider is ready loaded with destination, object context and
     * script context.
     * 
     * @param destinationList
     * @param scriptContext
     * @param grammarId
     * @param objectContext
     *            not to be confused with script context
     * @param defaultDestination
     * @return data providers list.
     * @throws CoreException
     */
    public List<AbstractScriptRelevantDataProvider> getScriptRelevantDataProviders(
            List<String> destinationList, String scriptContext,
            String grammarId, Object objectContext, String defaultDestination)
            throws CoreException {
        List<ScriptGrammarDestinationBindingElement> scriptGrammarDestinationBindingElements =
                getScriptGrammarDestinationBindingElements();
        List<AbstractScriptRelevantDataProvider> scriptRelevantDataProviders =
                new ArrayList<AbstractScriptRelevantDataProvider>();

        Collection<String> destinations = destinationList;
        if (destinationList.isEmpty() && defaultDestination != null) {
            destinations = Collections.singletonList(defaultDestination);
        }
        for (String destination : destinations) {
            for (ScriptGrammarDestinationBindingElement element : scriptGrammarDestinationBindingElements) {
                if (destination.equals(element.getDestinationId())) {
                    Collection<ScriptContribution> scriptContributions =
                            element.getScriptContributions();
                    for (ScriptContribution scriptContribution : scriptContributions) {
                        if (scriptContribution.getScriptContext()
                                .equals(scriptContext)) {
                            List<ScriptRelevantDataContributor> scriptRelevantDataContributors =
                                    scriptContribution
                                            .getScriptRelevantDataContributors();
                            for (ScriptRelevantDataContributor scriptRelevantDataContributor : scriptRelevantDataContributors) {
                                if (grammarId
                                        .equals(scriptRelevantDataContributor
                                                .getGrammar())) {
                                    AbstractScriptRelevantDataProvider scriptRelevantDataProvider =
                                            scriptRelevantDataContributor
                                                    .getScriptRelevantDataProvider();
                                    scriptRelevantDataProvider
                                            .setDestinationName(destination);
                                    scriptRelevantDataProvider
                                            .setContext(objectContext);
                                    scriptRelevantDataProvider
                                            .setScriptType(scriptContext);
                                    scriptRelevantDataProviders
                                            .add(scriptRelevantDataProvider);
                                }
                            }
                        }
                    }
                }
            }
        }
        return scriptRelevantDataProviders;
    }

    /**
     * @param destinations
     * @param scriptContext
     * @param grammarId
     * @param objectContext
     * @param defaultDestination
     * @return
     * @throws CoreException
     */
    public List getComplexScriptRelevantData(List<String> destinationList,
            String scriptContext, String grammarId, EObject objectContext,
            String defaultDestination) throws CoreException {
        Collection<String> destinations = destinationList;
        List<ScriptGrammarDestinationBindingElement> complexScriptRelevantData =
                new ArrayList<ScriptGrammarDestinationBindingElement>();
        if (destinationList.isEmpty() && defaultDestination != null) {
            destinations = Collections.singletonList(defaultDestination);
        }

        // else if (!destinations.contains(defaultDestination)) {
        // destinations.add(defaultDestination);
        // }
        for (String destination : destinations) {
            for (ScriptGrammarDestinationBindingElement element : scriptGrammarDestinationBindingElements) {
                if (destination.equals(element.getDestinationId())) {
                    Collection<ScriptContribution> scriptContributions =
                            element.getScriptContributions();
                    for (ScriptContribution scriptContribution : scriptContributions) {
                        if (scriptContribution.getScriptContext()
                                .equals(scriptContext)) {
                            List<ScriptRelevantDataContributor> scriptRelevantDataContributors =
                                    scriptContribution
                                            .getScriptRelevantDataContributors();
                            for (ScriptRelevantDataContributor scriptRelevantDataContributor : scriptRelevantDataContributors) {
                                if (grammarId
                                        .equals(scriptRelevantDataContributor
                                                .getGrammar())) {
                                    AbstractScriptRelevantDataProvider scriptRelevantDataProvider =
                                            scriptRelevantDataContributor
                                                    .getScriptRelevantDataProvider();
                                    scriptRelevantDataProvider
                                            .setDestinationName(destination);
                                    scriptRelevantDataProvider
                                            .setContext(objectContext);
                                    scriptRelevantDataProvider
                                            .setScriptType(grammarId);

                                    /*
                                     * Sid ACE-1317 Removed
                                     * getComplexScriptRelevantDataList() as it
                                     * was redundant.
                                     */


                                }
                            }
                        }
                    }
                }
            }
        }
        return complexScriptRelevantData;
    }

    /**
     * @param enabledDestinations
     * @param scriptContext
     * @param grammarId
     * @return
     * @throws CoreException
     */
    public ResourceMarkerAnnotationModelProvider getResourceMarkerAnnotationModelProvider(
            Collection<String> enabledDestinations, String scriptContext,
            String grammarId) throws CoreException {
        Set<ScriptContextGrammarBinding> scriptContextBindingElements =
                new HashSet<ScriptContextGrammarBinding>();
        for (String destination : enabledDestinations) {
            scriptContextBindingElements
                    .addAll(getScriptContextBindingElement(destination,
                            scriptContext));
        }
        for (ScriptContextGrammarBinding scriptContextGrammarBinding : scriptContextBindingElements) {
            List<ScriptGrammarReferenceElement> grammarsForContext =
                    scriptContextGrammarBinding.getGrammarsForContext();
            for (ScriptGrammarReferenceElement gramRefElement : grammarsForContext) {
                if (grammarId.equals(gramRefElement.getGrammar())) {
                    return gramRefElement
                            .getResourceMarkerAnnotationModelProvider();
                }
            }
        }

        return null;
    }

    /**
     * @param destination
     * @param scriptContext
     * @return
     */
    public String getDefaultGrammarForGivenContextInADestination(
            String destination, String scriptContext) {
        return null;
    }

    /**
     * @param supportedGrammars
     * @throws CoreException
     */
    public Collection<IScriptGrammarFilter> getScriptGrammarFilters(
            List<String> supportedGrammars) throws CoreException {
        List<ScriptGrammarDestinationBindingElement> destinationBindingElements =
                getScriptGrammarDestinationBindingElements();
        List<IScriptGrammarFilter> grammarFilters =
                new ArrayList<IScriptGrammarFilter>();
        for (ScriptGrammarDestinationBindingElement element : destinationBindingElements) {
            Collection<ScriptContextGrammarBinding> scriptContextGrammarBindings =
                    element.getScriptContextGrammarBindings();
            for (String supportedGrammar : supportedGrammars) {
                List<ScriptGrammarReferenceElement> scriptGrammarReferenceElementFromScriptContexts =
                        getScriptGrammarReferenceElementFromScriptContexts(scriptContextGrammarBindings,
                                supportedGrammar);
                // Assuming that there should be only one
                // scriptGrammarRefElement for a given destination
                if (!scriptGrammarReferenceElementFromScriptContexts.isEmpty()) {
                    ScriptGrammarReferenceElement scriptGrammarReferenceElementForSpecificGrammar =
                            scriptGrammarReferenceElementFromScriptContexts
                                    .get(0);
                    IScriptGrammarFilter scriptGrammarFilter =
                            scriptGrammarReferenceElementForSpecificGrammar
                                    .getScriptGrammarFilter();
                    if (scriptGrammarFilter != null) {
                        grammarFilters.add(scriptGrammarFilter);
                    }
                }

            }
        }
        return grammarFilters;
    }

    private String getClassDefinitionReaderClassifier(String destination,
            String context, String grammarId) {
        return destination + context + grammarId;
    }
}
