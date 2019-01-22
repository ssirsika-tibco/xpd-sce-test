/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.script.ui.internal.extension;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;

import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;
import com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider;
import com.tibco.xpd.script.ui.internal.AbstractScriptRelevantDataProvider;
import com.tibco.xpd.script.ui.internal.IScriptGrammarFilter;
import com.tibco.xpd.script.ui.internal.ResourceMarkerAnnotationModelProvider;

/**
 * 
 * ScriptGrammarDestinationBindingElement
 * 
 * @author rsomayaj
 */
public class ScriptGrammarDestinationBindingElement {

    public static final String EXTN_POINT_SCRIPT_GRAMMAR_DEST_BINDING =
            "scriptGrammarDestinationBinding"; //$NON-NLS-1$

    public static final String ATT_ID = "id"; //$NON-NLS-1$

    public static final String ATT_ICON = "icon"; //$NON-NLS-1$

    public static final String ATT_MODEL = "model"; //$NON-NLS-1$

    public static final String ATT_NAME = "name"; //$NON-NLS-1$

    public static final String ATT_DESTINATION_ID = "destinationId"; //$NON-NLS-1$

    public static final String ELE_SCRIPT_GRAMMAR = "grammar"; //$NON-NLS-1$

    public static final String ATT_GRAMMAR_ID = "grammarId"; //$NON-NLS-1$

    public static final String ELE_SCRIPT_TYPE_CONTEXT = "scriptTypeContext"; //$NON-NLS-1$

    public static final String ATT_TYPE = "type"; //$NON-NLS-1$

    public static final String ATT_ISDEFAULT = "isDefault"; //$NON-NLS-1$ 

    public static final String ATT_SELECTABLE = "selectable"; //$NON-NLS-1$ 

    public static final String ELE_SCRIPT_GRAMMAR_FILTER =
            "scriptGrammarFilter"; //$NON-NLS-1$

    public static final String ELEMENT_SCRIPTGRAMMAR_BINDING =
            "scriptGrammarBinding"; //$NON-NLS-1$

    public static final String ELEMENT_SCRIPTCONTEXT_GRAMMAR_BINDING =
            "scriptContextGrammarBinding"; //$NON-NLS-1$

    public static final String ELEMENT_VALIDATION_STRATEGY =
            "validationStrategy"; //$NON-NLS-1$

    public static final String ELEMENT_CLASS_CONTRIBUTOR = "classContributor"; //$NON-NLS-1$

    public static final String ELEMENT_MODEL_CONTRIBUTOR = "modelContributor"; //$NON-NLS-1$

    public static final String ELEMENT_SCRIPT_RELEVANTDATA_CONTRIBUTOR =
            "scriptRelevantDataContributor"; //$NON-NLS-1$

    public static final String ELEMENT_SCRIPT_BINDING_GRAMMAR = "grammar"; //$NON-NLS-1$

    public static final String ELEMENT_SCRIPT_CONTEXT_BINDING =
            "scriptContextBinding"; //$NON-NLS-1$

    public static final String ELEMENT_SCRIPT_GRAMMAR_FILTER =
            "scriptGrammarFilter"; //$NON-NLS-1$

    public static final String ELEMENT_CONTEXT_REFERENCE = "contextReference"; //$NON-NLS-1$

    public static final String ATT_SCRIPT_DETAILS_PROVIDER =
            "scriptDetailsProvider"; //$NON-NLS-1$

    public static final String ATT_SCRIPT_INFO_PROVIDER = "scriptInfoProvider"; //$NON-NLS-1$

    public static final String ATT_SAVE_COMMAND_PROVIDER =
            "saveCommandProvider"; //$NON-NLS-1$

    public static final String ATT_RESOURCE_MARKER_ANNOTATIO_PROVIDER =
            "resourceMarkerAnnotationProvider"; //$NON-NLS-1$

    public static final String ATT_FILTER = "filter"; //$NON-NLS-1$ 

    public static final String ATT_CONTEXT = "context"; //$NON-NLS-1$

    public static final String ATT_CONTEXT_TYPE = "contextType"; //$NON-NLS-1$

    public static final String ATT_CLASS = "class"; //$NON-NLS-1$

    public static final String ATT_GRAMMAR = "grammar"; //$NON-NLS-1$

    private static final String ELEMENT_SCRIPT_CONTRIBUTRION =
            "scriptContribution"; //$NON-NLS-1$

    private static final String ATT_SCRIPT_CONTEXT = "scriptContext"; //$NON-NLS-1$

    private IConfigurationElement configElement = null;

    Collection<ScriptContextGrammarBinding> scriptGrammarBindings =
            Collections.EMPTY_LIST;

    private Collection<ScriptContribution> scriptContributions =
            Collections.EMPTY_LIST;

    private static Map<String, Image> modelContributorIconRegistry =
            new HashMap<String, Image>();

    /**
     * Default constructor
     * 
     * @param configElement
     */
    public ScriptGrammarDestinationBindingElement(
            IConfigurationElement configElement) {
        this.configElement = configElement;
    }

    /**
     * Get the extension configuration element
     * 
     * @return
     */
    public IConfigurationElement getConfigElement() {
        return configElement;
    }

    /**
     * Other contributions such as classContributor, modelDefinitionReader,
     * scriptRelevantData, and validationStrategy are contributed and
     * consolidated through scriptContributions
     * 
     * @return
     */
    public Collection<ScriptContribution> getScriptContributions() {
        if (!scriptContributions.isEmpty()) {
            return scriptContributions;
        }

        if (configElement != null) {
            IConfigurationElement[] configElements =
                    configElement.getChildren(ELEMENT_SCRIPT_CONTRIBUTRION);
            scriptContributions = new ArrayList<ScriptContribution>();
            for (IConfigurationElement configElement : configElements) {
                ScriptContribution scriptContribution =
                        new ScriptContribution(configElement);
                scriptContributions.add(scriptContribution);
            }
            return scriptContributions;
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * 
     * @return
     */
    public Collection<ScriptContextGrammarBinding> getScriptContextGrammarBindings() {
        if (!scriptGrammarBindings.isEmpty()) {
            return scriptGrammarBindings;
        }
        if (configElement != null) {
            IConfigurationElement[] configElements =
                    configElement
                            .getChildren(ELEMENT_SCRIPTCONTEXT_GRAMMAR_BINDING);
            scriptGrammarBindings =
                    new ArrayList<ScriptContextGrammarBinding>();
            for (IConfigurationElement configElement : configElements) {
                ScriptContextGrammarBinding scriptGrammarBinding =
                        new ScriptContextGrammarBinding(configElement);
                scriptGrammarBindings.add(scriptGrammarBinding);
            }
            return scriptGrammarBindings;
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * Get the <i>name</i> set in this extension
     * 
     * @return Extension name
     */
    public String getName() {
        if (configElement == null) {
            return null;
        }
        return getAttribute(ATT_NAME);
    }

    /**
     * Get the <i>destination</i> set in this extension
     * 
     * @return Destination name
     */
    public String getDestinationId() {
        if (configElement == null) {
            return null;
        }
        return configElement.getAttribute(ATT_DESTINATION_ID);
    }

    /**
     * Get the given attribute from the configuration element
     * 
     * @param attr
     * @return Attribute
     */
    private String getAttribute(String attr) {
        if (configElement != null) {
            return configElement.getAttribute(attr);
        }
        return null;
    }

    /**
     * 
     * @return
     */
    public String getId() {
        return configElement.getAttribute(ATT_ID);
    }

    /**
     * @author rsomayaj
     * 
     */
    public class ModelContributor {
        private String id = null;

        private String name = null;

        // Model is a resource
        private org.eclipse.emf.common.util.URI modelURI = null;

        private String grammar = null;

        private Image icon = null;

        private boolean iconLoaded = false;

        private IConfigurationElement modelContributorConfigElement;

        /**
         * 
         */
        public ModelContributor(IConfigurationElement configurationElement) {
            this.modelContributorConfigElement = configurationElement;
        }

        /**
         * @return the id
         */
        public String getId() {
            if (id == null) {
                if (this.modelContributorConfigElement != null) {
                    id = modelContributorConfigElement.getAttribute(ATT_ID);
                }
            }
            return id;
        }

        /**
         * @return the grammar
         */
        public String getGrammar() {
            if (grammar == null) {
                if (this.modelContributorConfigElement != null) {
                    grammar =
                            modelContributorConfigElement
                                    .getAttribute(ATT_GRAMMAR_ID);
                }
            }
            return grammar;
        }

        /**
         * @return the icon
         * 
         *         This is a resource. Need to load this resource
         */
        public Image getIcon() {
            if (!iconLoaded) {

                iconLoaded = true;
                if (this.modelContributorConfigElement != null) {
                    String iconPath =
                            modelContributorConfigElement
                                    .getAttribute(ATT_ICON);
                    if (iconPath != null) {
                        Image img =
                                modelContributorIconRegistry
                                        .get(getIconClassifier(modelContributorConfigElement
                                                .getNamespaceIdentifier(),
                                                iconPath));
                        if (img != null) {
                            icon = img;
                        } else {
                            // if the img is not in the registry yet - Get the
                            // icon descriptor
                            ImageDescriptor imageDescriptor =
                                    AbstractUIPlugin
                                            .imageDescriptorFromPlugin(modelContributorConfigElement
                                                    .getNamespaceIdentifier(),
                                                    iconPath);
                            if (imageDescriptor != null) {
                                icon = imageDescriptor.createImage();
                                // Add the image to the registry
                                modelContributorIconRegistry
                                        .put(getIconClassifier(modelContributorConfigElement
                                                .getNamespaceIdentifier(),
                                                iconPath),
                                                icon);
                            }
                        }
                    }
                }
            }

            return icon;
        }

        private String getIconClassifier(String namespaceIdentifier,
                String iconPath) {
            return namespaceIdentifier + "." + iconPath;
        }

        /**
         * @return the model
         * 
         *         This is a resource
         */
        public URI getModel() {
            if (modelURI == null) {
                if (this.modelContributorConfigElement != null) {
                    String modelName =
                            modelContributorConfigElement
                                    .getAttribute(ATT_MODEL);
                    if (modelName != null) {
                        Bundle bundle =
                                Platform
                                        .getBundle(modelContributorConfigElement
                                                .getNamespaceIdentifier());
                        if (bundle != null) {
                            URL bundleEntry = bundle.getEntry(modelName);
                            if (bundleEntry != null) {
                                modelURI =
                                        org.eclipse.emf.common.util.URI
                                                .createURI(bundleEntry
                                                        .toExternalForm());
                            }
                        }
                    }
                }
            }
            return modelURI;
        }

        /**
         * @return the name
         */
        public String getName() {
            if (name == null) {
                if (this.modelContributorConfigElement != null) {
                    name = modelContributorConfigElement.getAttribute(ATT_NAME);
                }
            }
            return name;
        }

    }

    /**
     * This is to represent schema element called classContributor.
     * 
     * @author rsomayaj
     * 
     *         TODO - UNFINISHED
     */
    public class ClassContributor {
        private IConfigurationElement classContributorConfigElement;

        private String id = null;

        private String name = null;

        private JsClassDefinitionReader classDefinitionReader;

        private String grammarId = null;

        /**
         * 
         */
        public ClassContributor(
                IConfigurationElement classContributorConfigElement) {
            this.classContributorConfigElement = classContributorConfigElement;
        }

        /**
         * @return the id
         */
        public String getId() {
            if (id == null) {
                if (classContributorConfigElement != null) {
                    id = classContributorConfigElement.getAttribute(ATT_ID);
                }
            }
            return id;
        }

        public String getGrammarId() {
            if (grammarId == null) {
                if (classContributorConfigElement != null) {
                    grammarId =
                            classContributorConfigElement
                                    .getAttribute(ATT_GRAMMAR_ID);
                }
            }
            return grammarId;
        }

        /**
         * @return the name
         */
        public String getName() {
            if (name == null) {
                if (classContributorConfigElement != null) {
                    name = classContributorConfigElement.getAttribute(ATT_NAME);
                }
            }
            return name;
        }

        /**
         * @return the classDefinitionReader
         */
        public JsClassDefinitionReader getClassDefinitionReader()
                throws CoreException {
            if (classDefinitionReader != null) {
                return classDefinitionReader;
            }
            if (classContributorConfigElement != null) {
                String classDefnReaderStr =
                        classContributorConfigElement.getAttribute(ATT_CLASS);
                if (classDefnReaderStr != null) {
                    Object executableExtension =
                            classContributorConfigElement
                                    .createExecutableExtension(ATT_CLASS);
                    if (executableExtension instanceof JsClassDefinitionReader) {
                        classDefinitionReader =
                                (JsClassDefinitionReader) executableExtension;
                    }
                }
            }

            return classDefinitionReader;
        }

    }

    public class ScriptRelevantDataContributor {
        private String id = null;

        private String name = null;

        private String grammar = null;

        private final IConfigurationElement scriptRelevantDataContributorConfigElement;

        /**
         * 
         */
        public ScriptRelevantDataContributor(
                IConfigurationElement scriptRelevantDataContributorConfigElement) {
            this.scriptRelevantDataContributorConfigElement =
                    scriptRelevantDataContributorConfigElement;

        }

        /**
         * @return the grammar
         */
        public String getGrammar() {
            if (grammar == null) {
                if (this.scriptRelevantDataContributorConfigElement != null) {
                    grammar =
                            scriptRelevantDataContributorConfigElement
                                    .getAttribute(ATT_GRAMMAR_ID);
                }
            }
            return grammar;
        }

        /**
         * @return the id
         */
        public String getId() {
            if (id == null) {
                if (this.scriptRelevantDataContributorConfigElement != null) {
                    id =
                            scriptRelevantDataContributorConfigElement
                                    .getAttribute(ATT_ID);
                }
            }
            return id;
        }

        /**
         * @return the name
         */
        public String getName() {
            if (name == null) {
                if (this.scriptRelevantDataContributorConfigElement != null) {
                    name =
                            scriptRelevantDataContributorConfigElement
                                    .getAttribute(ATT_NAME);
                }
            }

            return name;
        }

        /**
         * @return the scriptRelevantDataProvider
         * @throws CoreException
         */
		public AbstractScriptRelevantDataProvider getScriptRelevantDataProvider()
				throws CoreException {
			AbstractScriptRelevantDataProvider scriptRelevantDataProvider = null;
			if (this.scriptRelevantDataContributorConfigElement != null) {
				String className = scriptRelevantDataContributorConfigElement
						.getAttribute(ATT_CLASS);
				if (className != null) {
					Object executableExtension = scriptRelevantDataContributorConfigElement
							.createExecutableExtension(ATT_CLASS);
					if (executableExtension instanceof AbstractScriptRelevantDataProvider) {
						scriptRelevantDataProvider = (AbstractScriptRelevantDataProvider) executableExtension;
					}
				}
			}
			return scriptRelevantDataProvider;
		}
    }

    /**
     * 
     * @author rsomayaj
     * 
     */
    public class ValidationStrategy {
        private String id = null;

        private String name = null;

        private String grammarId = null;

        private IValidationStrategy validationStrategyClass = null;

        private List<String> contextReferences = null;

        private IConfigurationElement validationStrategyConfigElement;

        /**
         * 
         */
        public ValidationStrategy(IConfigurationElement configElement) {
            this.validationStrategyConfigElement = configElement;
        }

        /**
         * @return the id
         */
        public String getId() {
            if (id == null) {
                if (this.validationStrategyConfigElement != null) {
                    id = validationStrategyConfigElement.getAttribute(ATT_ID);
                }
            }
            return id;
        }

        /**
         * @return the name
         */
        public String getName() {
            if (name == null) {
                if (this.validationStrategyConfigElement != null) {
                    name =
                            validationStrategyConfigElement
                                    .getAttribute(ATT_NAME);
                }
            }
            return name;
        }

        /**
         * @return the grammarId
         */
        public String getGrammarId() {
            if (grammarId == null) {
                if (this.validationStrategyConfigElement != null) {
                    grammarId =
                            validationStrategyConfigElement
                                    .getAttribute(ATT_GRAMMAR_ID);
                }
            }
            return grammarId;
        }

        /**
         * @return the validationStrategyClass
         */
        public IValidationStrategy getValidationStrategyClass()
                throws CoreException {
            if (validationStrategyClass == null) {
                // if (validationStrategyClass != null) {
                // return validationStrategyClass;
                // }
                if (this.validationStrategyConfigElement != null) {
                    String validationStrategyClass =
                            validationStrategyConfigElement
                                    .getAttribute(ATT_CLASS);

                    if (validationStrategyClass != null) {
                        Object validationStrategyClassObj =
                                validationStrategyConfigElement
                                        .createExecutableExtension(ATT_CLASS);
                        if (validationStrategyClassObj instanceof IValidationStrategy) {
                            this.validationStrategyClass =
                                    (IValidationStrategy) validationStrategyClassObj;
                        }
                    }
                }
            }
            return validationStrategyClass;
        }

        /**
         * @return the contextReferences
         */
        public List<String> getContextReferences() {
            if (contextReferences == null) {
                contextReferences = new ArrayList<String>();
                if (validationStrategyConfigElement != null) {
                    IConfigurationElement[] contextReferenceElements =
                            validationStrategyConfigElement
                                    .getChildren(ELEMENT_CONTEXT_REFERENCE);

                    for (IConfigurationElement contextReference : contextReferenceElements) {
                        String contextRef =
                                contextReference.getAttribute(ATT_CONTEXT);
                        if (contextRef != null) {
                            contextReferences.add(contextRef);
                        }
                    }
                }
            }
            return contextReferences;
        }
    }

    /**
     * 
     * @author rsomayaj
     * 
     */
    public class ScriptContextGrammarBinding {

        /**
         * 
         */
        private static final String ELEMENT_DEFAULT_GRAMMAR = "defaultGrammar"; //$NON-NLS-1$

        /**
         * 
         */
        private static final String ELEMENT_SCRIPT_GRAMMAR = "grammar"; //$NON-NLS-1$

        /**
         * 
         */
        private static final String ATT_SCRIPT_CONTEXT_REFERENCE =
                "scriptContextReference"; //$NON-NLS-1$

        private IConfigurationElement scriptContextGrammarBindingElement;

        private String scriptContextReference;

        private List<ScriptGrammarReferenceElement> grammarsForContext =
                Collections.EMPTY_LIST;

        /**
         * 
         */
        public ScriptContextGrammarBinding(
                IConfigurationElement scriptContextGrammarBinding) {
            this.scriptContextGrammarBindingElement =
                    scriptContextGrammarBinding;
        }

        /**
         * @return the grammarsForContext
         */
        public List<ScriptGrammarReferenceElement> getGrammarsForContext() {
            if (!grammarsForContext.isEmpty()) {
                return grammarsForContext;
            }
            if (scriptContextGrammarBindingElement != null) {
                // There are two different elements - one that contributes to
                // the default script grammar and the other contributes to the
                // remaining grammars
                grammarsForContext =
                        new ArrayList<ScriptGrammarReferenceElement>();
                IConfigurationElement[] defaultGrammarConfigurationElements =
                        scriptContextGrammarBindingElement
                                .getChildren(ELEMENT_DEFAULT_GRAMMAR);
                if (defaultGrammarConfigurationElements.length > 0) {
                    ScriptGrammarReferenceElement defaultGrammarReferenceElement =
                            new ScriptGrammarReferenceElement(
                                    defaultGrammarConfigurationElements[0],
                                    Boolean.TRUE);
                    grammarsForContext.add(defaultGrammarReferenceElement);
                }

                IConfigurationElement[] configurationElements =
                        scriptContextGrammarBindingElement
                                .getChildren(ELEMENT_SCRIPT_GRAMMAR);
                for (IConfigurationElement configElement : configurationElements) {
                    ScriptGrammarReferenceElement scriptGrammarReferenceElement =
                            new ScriptGrammarReferenceElement(configElement,
                                    false);
                    grammarsForContext.add(scriptGrammarReferenceElement);
                }
                return grammarsForContext;
            }
            return Collections.EMPTY_LIST;
        }

        /**
         * @return the scriptContextReference
         */
        public String getScriptContextReference() {
            if (scriptContextReference != null) {
                return scriptContextReference;
            }
            if (scriptContextGrammarBindingElement != null) {
                scriptContextReference =
                        scriptContextGrammarBindingElement
                                .getAttribute(ATT_SCRIPT_CONTEXT_REFERENCE);
            }
            return scriptContextReference;
        }
    }

    /**
     * @author rsomayaj
     * 
     */
    public class ScriptGrammarReferenceElement {
        private final IConfigurationElement scriptGrammarReferenceConfigElement;

        private String grammar;

        private IScriptGrammarFilter scriptGrammarFilter;

        private Boolean defaultGrammar;

        private AbstractScriptInfoProvider scriptInfoProvider;

        private ResourceMarkerAnnotationModelProvider resourceMarkerAnnotationModelProvider;

        private Boolean selectable;

        /**
         * 
         */
        public ScriptGrammarReferenceElement(
                IConfigurationElement configElement, Boolean isDefault) {
            this.scriptGrammarReferenceConfigElement = configElement;
            defaultGrammar = isDefault;
        }

        public Boolean isDefault() {
            return defaultGrammar;
        }

        /**
         * @return the grammar
         */
        public String getGrammar() {
            if (this.scriptGrammarReferenceConfigElement != null) {
                grammar =
                        scriptGrammarReferenceConfigElement
                                .getAttribute(ATT_ID);
            }
            return grammar;
        }

        /**
         * @return true if user is allowed to select grammar if it's not the
         *         currently selected grammar in the model.
         */
        public boolean isSelectable() {
            if (selectable != null) {
                return selectable;
            }

            if (scriptGrammarReferenceConfigElement != null) {
                selectable = Boolean.TRUE;
                String s =
                        scriptGrammarReferenceConfigElement
                                .getAttribute(ATT_SELECTABLE);
                if (s != null && s.length() > 0) {
                    selectable = Boolean.parseBoolean(s);
                }
            }
            return selectable;
        }

        /**
         * @return the scriptGrammarFilter
         * @throws CoreException
         */
        public IScriptGrammarFilter getScriptGrammarFilter()
                throws CoreException {
            if (scriptGrammarFilter != null) {
                return scriptGrammarFilter;
            }
            if (scriptGrammarReferenceConfigElement != null) {
                IConfigurationElement[] children =
                        scriptGrammarReferenceConfigElement
                                .getChildren(ELEMENT_SCRIPT_GRAMMAR_FILTER);
                if (children.length > 0) {
                    String filterAttrib = children[0].getAttribute(ATT_FILTER);
                    if (filterAttrib != null) {
                        Object executableExtension =
                                children[0]
                                        .createExecutableExtension(ATT_FILTER);
                        if (executableExtension instanceof IScriptGrammarFilter) {
                            this.scriptGrammarFilter =
                                    (IScriptGrammarFilter) executableExtension;
                        }
                    }
                }
            }

            return scriptGrammarFilter;
        }

        /**
         * @return the scriptInfoProvider
         * @throws CoreException
         */
        public AbstractScriptInfoProvider getScriptInfoProvider()
                throws CoreException {
            if (scriptInfoProvider != null) {
                return scriptInfoProvider;
            }

            if (scriptGrammarReferenceConfigElement != null) {
                String scriptInfoProviderStr =
                        scriptGrammarReferenceConfigElement
                                .getAttribute(ATT_SCRIPT_INFO_PROVIDER);
                if (scriptInfoProviderStr != null) {
                    Object executableExtension =
                            scriptGrammarReferenceConfigElement
                                    .createExecutableExtension(ATT_SCRIPT_INFO_PROVIDER);
                    if (executableExtension instanceof AbstractScriptInfoProvider) {
                        scriptInfoProvider =
                                (AbstractScriptInfoProvider) executableExtension;
                    }
                }
            }
            return scriptInfoProvider;
        }

        /**
         * @return the resourceMarkerAnnotationModelProvider
         * @throws CoreException
         */
        public ResourceMarkerAnnotationModelProvider getResourceMarkerAnnotationModelProvider()
                throws CoreException {
            if (resourceMarkerAnnotationModelProvider != null) {
                return resourceMarkerAnnotationModelProvider;
            }

            if (scriptGrammarReferenceConfigElement != null) {
                String resourceMarkerAnnotationProviderStr =
                        scriptGrammarReferenceConfigElement
                                .getAttribute(ATT_RESOURCE_MARKER_ANNOTATIO_PROVIDER);
                if (resourceMarkerAnnotationProviderStr != null) {
                    Object executableExtension =
                            scriptGrammarReferenceConfigElement
                                    .createExecutableExtension(ATT_RESOURCE_MARKER_ANNOTATIO_PROVIDER);
                    if (executableExtension instanceof ResourceMarkerAnnotationModelProvider) {
                        resourceMarkerAnnotationModelProvider =
                                (ResourceMarkerAnnotationModelProvider) executableExtension;
                    }
                }
            }
            return resourceMarkerAnnotationModelProvider;
        }
    }

    /**
     * @author rsomayaj
     * 
     */
    public class ScriptContribution {
        private String scriptContext;

        private List<ClassContributor> classContributors =
                Collections.EMPTY_LIST;

        private List<ModelContributor> modelContributors =
                Collections.EMPTY_LIST;

        private List<ScriptRelevantDataContributor> scriptRelevantDataContributors =
                Collections.EMPTY_LIST;

        private final IConfigurationElement scriptContributionConfigElement;

        /**
         * @param configElement
         */
        public ScriptContribution(IConfigurationElement configElement) {
            scriptContributionConfigElement = configElement;
        }

        /**
         * @return the scriptContext
         */
        public String getScriptContext() {
            if (scriptContributionConfigElement != null) {
                scriptContext =
                        scriptContributionConfigElement
                                .getAttribute(ATT_SCRIPT_CONTEXT);
            }
            return scriptContext;
        }

        /**
         * 
         * @return
         */
		public List<ValidationStrategy> getValidationStrategy() {
			/*
			 * if (!validationStrategies.isEmpty()) { return
			 * validationStrategies; }
			 */
			List<ValidationStrategy> validationStrategies = new ArrayList<ValidationStrategy>();
			if (scriptContributionConfigElement != null) {
				IConfigurationElement[] configElements = scriptContributionConfigElement
						.getChildren(ELEMENT_VALIDATION_STRATEGY);
				for (IConfigurationElement configElement : configElements) {
					validationStrategies.add(new ValidationStrategy(
							configElement));
				}
			}
			return validationStrategies;
		}

        /**
         * @return
         */
        public List<ClassContributor> getClassContributors() {
            if (!classContributors.isEmpty()) {
                return classContributors;
            }

            if (scriptContributionConfigElement != null) {
                IConfigurationElement[] configElements =
                        scriptContributionConfigElement
                                .getChildren(ELEMENT_CLASS_CONTRIBUTOR);
                classContributors = new ArrayList<ClassContributor>();
                for (IConfigurationElement configElement : configElements) {
                    classContributors.add(new ClassContributor(configElement));
                }
                return classContributors;
            }
            return Collections.EMPTY_LIST;
        }

        /**
         * @return
         */
        public List<ModelContributor> getModelContributors() {
            if (!modelContributors.isEmpty()) {
                return modelContributors;
            }

            if (scriptContributionConfigElement != null) {
                IConfigurationElement[] configElements =
                        scriptContributionConfigElement
                                .getChildren(ELEMENT_MODEL_CONTRIBUTOR);
                modelContributors = new ArrayList<ModelContributor>();

                for (IConfigurationElement configElement : configElements) {
                    modelContributors.add(new ModelContributor(configElement));
                }
                return modelContributors;
            }
            return Collections.EMPTY_LIST;
        }

        /**
         * @return
         */
        public List<ScriptRelevantDataContributor> getScriptRelevantDataContributors() {
            if (!scriptRelevantDataContributors.isEmpty()) {
                return scriptRelevantDataContributors;
            }
            if (scriptContributionConfigElement != null) {
                IConfigurationElement[] configElements =
                        scriptContributionConfigElement
                                .getChildren(ELEMENT_SCRIPT_RELEVANTDATA_CONTRIBUTOR);
                scriptRelevantDataContributors =
                        new ArrayList<ScriptRelevantDataContributor>();

                for (IConfigurationElement configElement : configElements) {
                    scriptRelevantDataContributors
                            .add(new ScriptRelevantDataContributor(
                                    configElement));
                }
                return scriptRelevantDataContributors;
            }
            return Collections.EMPTY_LIST;
        }
    }

}
