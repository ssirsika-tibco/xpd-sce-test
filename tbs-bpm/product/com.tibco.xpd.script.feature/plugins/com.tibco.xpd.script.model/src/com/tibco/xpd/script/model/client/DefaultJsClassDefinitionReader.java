/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.script.model.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.FunctionBehavior;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.Activator;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.internal.client.DefaultJsClassMultiple;
import com.tibco.xpd.script.model.internal.client.DefaultJsEnumeration;
import com.tibco.xpd.script.model.internal.client.IContentAssistIconProvider;
import com.tibco.xpd.script.model.internal.client.IGlobalDataDefinitionReader;
import com.tibco.xpd.script.model.internal.client.ITypeResolverProvider;
import com.tibco.xpd.script.model.internal.client.JsEnumeration;
import com.tibco.xpd.script.model.jscript.JScriptTypeResolver;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.ui.util.NameUtil;

/**
 * 
 * @author mtorres
 * 
 */
public abstract class DefaultJsClassDefinitionReader implements
        JsClassDefinitionReader, ITypeResolverProvider,
        IGlobalDataDefinitionReader {

    private HashMap<String, JsClass> cachedClassMap = null;

    private HashMap<String, List<JsMethod>> cachedGlobalMethodMap = null;

    private HashMap<String, JsAttribute> cachedGlobalPropertyMap = null;

    private String destinationName = null;

    private String scriptType = null;

    private ITypeResolver typeResolver = null;

    @SuppressWarnings("unchecked")//$NON-NLS-1$
    protected void doLoadModel() {

        if (cachedClassMap != null || cachedGlobalMethodMap != null
                || cachedGlobalPropertyMap != null) {
            return;
        }

        List<URI> allUris = new ArrayList<URI>();

        URI uri = getURI();
        if (uri != null) {
            allUris.add(uri);
        }

        List<URI> uris = getURIs();

        if (uris != null && !uris.isEmpty()) {
            allUris.addAll(uris);
        }

        if (allUris.isEmpty()) {
            return;
        }

        ResourceSet resourceSet =
                Activator.getDefault().getScriptEditingDomain()
                        .getResourceSet();

        List<Class> allUmlClasses = new ArrayList<Class>();
        List<Enumeration> allUmlEnumerations = new ArrayList<Enumeration>();
        List<FunctionBehavior> allFunctionBehaviours =
                new ArrayList<FunctionBehavior>();
        for (URI loadedUri : allUris) {
            Resource resource = resourceSet.getResource(loadedUri, true);
            if (resource == null) {
                return;
            }
            Package umlPackage =
                    (Package) EcoreUtil.getObjectByType(resource.getContents(),
                            UMLPackage.Literals.PACKAGE);
            if (umlPackage == null) {
                return;
            }
            List<PackageableElement> packagedElements =
                    umlPackage.getPackagedElements();
            if (packagedElements == null) {
                return;
            }
            for (PackageableElement element : packagedElements) {
                if (element instanceof FunctionBehavior) {
                    allFunctionBehaviours.add((FunctionBehavior) element);
                } else if (element instanceof Class) {
                    allUmlClasses.add((Class) element);
                } else if (element instanceof Enumeration) {
                    allUmlEnumerations.add((Enumeration) element);
                }
            }
        }
        for (Class umlClass : allUmlClasses) {
            JsClass jsClass =
                    createJsClass(umlClass,
                            getMultipleClass(umlClass, allUmlClasses));
            String jsClassName = jsClass.getName();
            if (cachedClassMap == null) {
                cachedClassMap = new HashMap<String, JsClass>();
            }
            cachedClassMap.put(jsClassName, jsClass);
        }
        for (Enumeration umlEnumeration : allUmlEnumerations) {
            JsClass jsClass = createJsEnumeration(umlEnumeration);

            String jsClassName = jsClass.getName();

            String qualifiedNameForScripting =
                    NameUtil.formatQualifiedNameForScripting(umlEnumeration
                            .getQualifiedName());
            ((DefaultJsEnumeration) jsClass)
                    .setQualifiedName(qualifiedNameForScripting);

            // to use the qualified name
            jsClassName = jsClass.getName();

            if (cachedClassMap == null) {
                cachedClassMap = new HashMap<String, JsClass>();
            }
            cachedClassMap.put(jsClassName, jsClass);
        }
        for (FunctionBehavior umlFunctionBehaviour : allFunctionBehaviours) {
            if (umlFunctionBehaviour != null) {
                List<JsMethod> createdJsGlobalMethods =
                        createJsGlobalMethods(umlFunctionBehaviour);
                if (createdJsGlobalMethods != null
                        && !createdJsGlobalMethods.isEmpty()) {
                    for (JsMethod jsMethod : createdJsGlobalMethods) {
                        if (jsMethod != null) {
                            if (cachedGlobalMethodMap == null) {
                                cachedGlobalMethodMap =
                                        new HashMap<String, List<JsMethod>>();
                            }
                            String jsMethodName = jsMethod.getName();
                            if (jsMethodName != null) {
                                List<JsMethod> existingMethods =
                                        cachedGlobalMethodMap.get(jsMethodName);
                                if (existingMethods != null) {
                                    existingMethods.add(jsMethod);
                                } else {
                                    existingMethods = new ArrayList<JsMethod>();
                                    existingMethods.add(jsMethod);
                                    cachedGlobalMethodMap.put(jsMethodName,
                                            existingMethods);
                                }
                            }
                        }
                    }
                }
                List<JsAttribute> createdJsGlobalProperties =
                        createJsGlobalProperties(umlFunctionBehaviour);
                if (createdJsGlobalProperties != null
                        && !createdJsGlobalProperties.isEmpty()) {
                    for (JsAttribute jsAttribute : createdJsGlobalProperties) {
                        if (jsAttribute != null) {
                            if (cachedGlobalPropertyMap == null) {
                                cachedGlobalPropertyMap =
                                        new HashMap<String, JsAttribute>();
                            }
                            cachedGlobalPropertyMap.put(jsAttribute.getName(),
                                    jsAttribute);
                        }
                    }
                }
            }
        }
    }

    public Class getMultipleClass(Class umlClass, List<Class> allUmlClasses) {
        return JScriptUtils.getDefaultMultipleClass();
    }

    protected void registerResourceSet(ResourceSet resourceSet) {
    }

    @Override
    @SuppressWarnings("unchecked")//$NON-NLS-1$
    public List<String> getSupportedClassNames() {
        doLoadModel();
        if (cachedClassMap == null) {
            return Collections.EMPTY_LIST;
        }
        Set<String> classNameSet = cachedClassMap.keySet();
        if (classNameSet == null) {
            return Collections.EMPTY_LIST;
        }
        List<String> classNameList = new ArrayList<String>();
        for (String className : classNameSet) {
            classNameList.add(className);
        }
        return classNameList;
    }

    @Override
    public boolean isSupportedClass(String className) {
        doLoadModel();
        if (cachedClassMap == null) {
            return false;
        }
        return cachedClassMap.keySet().contains(className);
    }

    @Override
    @SuppressWarnings("unchecked")//$NON-NLS-1$
    public List<JsClass> getSupportedClasses() {
        doLoadModel();
        if (cachedClassMap == null) {
            return Collections.EMPTY_LIST;
        }
        Collection<JsClass> classCollection = cachedClassMap.values();
        if (classCollection == null) {
            return Collections.EMPTY_LIST;
        }
        List<JsClass> classList = null;
        for (JsClass eachClass : classCollection) {
            if (classList == null) {
                classList = new ArrayList<JsClass>();
            }
            classList.add(eachClass);
        }
        return classList;
    }

    @Override
    public JsClass getJsClass(String className) {
        doLoadModel();
        if (cachedClassMap == null) {
            return null;
        }
        return cachedClassMap.get(className);
    }

    protected abstract URI getURI();

    protected List<URI> getURIs() {
        return Collections.emptyList();
    }

    protected JsClass createJsClass(Class umlClass) {
        return createJsClass(umlClass, null);
    }

    protected JsClass createJsClass(Class umlClass, Class multipleClass) {
        if (isMultipleClass(umlClass)) {
            DefaultJsClassMultiple jsClass =
                    new DefaultJsClassMultiple(umlClass, multipleClass);
            if (!XpdResourcesPlugin.isInHeadlessMode()) {
                jsClass.setIcon(getIcon());
            }
            jsClass.setContentAssistIconProvider(getContentAssistIconProvider());
            return jsClass;
        } else {
            DefaultJsClass jsClass =
                    new DefaultJsClass(umlClass, multipleClass);
            if (!XpdResourcesPlugin.isInHeadlessMode()) {
                jsClass.setIcon(getIcon());
            }
            jsClass.setContentAssistIconProvider(getContentAssistIconProvider());
            return jsClass;
        }
    }

    protected JsEnumeration createJsEnumeration(Enumeration umlEnumeration) {
        DefaultJsEnumeration jsEnumeration =
                new DefaultJsEnumeration(umlEnumeration);
        jsEnumeration
                .setContentAssistIconProvider(getContentAssistIconProvider());
        return jsEnumeration;
    }

    protected List<JsMethod> createJsGlobalMethods(
            FunctionBehavior umlFunctionBehavior) {
        if (umlFunctionBehavior != null) {
            EList<Operation> allOperations =
                    umlFunctionBehavior.getAllOperations();
            if (allOperations != null) {
                List<JsMethod> globalMethods = new ArrayList<JsMethod>();
                for (Operation umlOperation : allOperations) {
                    if (umlOperation != null) {
                        globalMethods.add(createJsMethod(umlOperation));
                    }
                }
                return globalMethods;
            }
        }
        return Collections.emptyList();
    }

    protected List<JsAttribute> createJsGlobalProperties(
            FunctionBehavior umlFunctionBehavior) {
        if (umlFunctionBehavior != null) {
            EList<Property> allAttributes =
                    umlFunctionBehavior.getAllAttributes();
            if (allAttributes != null) {
                List<JsAttribute> globalAttributes =
                        new ArrayList<JsAttribute>();
                for (Property umlProperty : allAttributes) {
                    if (umlProperty != null) {
                        globalAttributes.add(createJsAttribute(umlProperty));
                    }
                }
                return globalAttributes;
            }
        }
        return Collections.emptyList();
    }

    protected JsMethod createJsMethod(Operation umlOperation) {
        DefaultJsMethod jsMethod = new DefaultJsMethod(umlOperation);
        jsMethod.setContentAssistIconProvider(getContentAssistIconProvider());
        return jsMethod;
    }

    protected JsAttribute createJsAttribute(Property umlProperty) {
        DefaultJsAttribute jsAttribute = new DefaultJsAttribute(umlProperty);
        jsAttribute
                .setContentAssistIconProvider(getContentAssistIconProvider());
        return jsAttribute;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getScriptType() {
        return scriptType;
    }

    public void setScriptType(String scriptType) {
        this.scriptType = scriptType;
    }

    private boolean isMultipleClass(Class umlClass) {
        if (umlClass != null) {
            EList<Constraint> ownedRules = umlClass.getOwnedRules();
            for (Constraint constraint : ownedRules) {
                if (JsConsts.MULTIPLE_CONSTRAINT_NAME.equals(constraint
                        .getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    protected abstract IContentAssistIconProvider getContentAssistIconProvider();

    @Override
    public ITypeResolver getTypeResolver() {
        if (typeResolver == null) {
            typeResolver = new JScriptTypeResolver();
            typeResolver.setSupportedJsClasses(getSupportedClasses());
        }
        return typeResolver;
    }

    protected HashMap<String, JsClass> getCachedClassMap() {
        return cachedClassMap;
    }

    @Override
    public List<JsMethod> getSupportedGlobalMethods() {
        doLoadModel();
        if (cachedGlobalMethodMap == null) {
            return Collections.emptyList();
        }
        Collection<List<JsMethod>> allGlobalMethods =
                cachedGlobalMethodMap.values();
        if (allGlobalMethods == null) {
            return Collections.emptyList();
        }
        List<JsMethod> globalMethodsList = null;
        for (List<JsMethod> jsMethods : allGlobalMethods) {
            if (jsMethods != null) {
                if (globalMethodsList == null) {
                    globalMethodsList = new ArrayList<JsMethod>();
                }
                globalMethodsList.addAll(jsMethods);
            }
        }
        return globalMethodsList;
    }

    @Override
    public List<JsAttribute> getSupportedGlobalProperties() {
        doLoadModel();
        if (cachedGlobalPropertyMap == null) {
            return Collections.emptyList();
        }
        Collection<JsAttribute> globalPropertyCollection =
                cachedGlobalPropertyMap.values();
        if (globalPropertyCollection == null) {
            return Collections.emptyList();
        }
        List<JsAttribute> attributeList = null;
        for (JsAttribute eachProperty : globalPropertyCollection) {
            if (attributeList == null) {
                attributeList = new ArrayList<JsAttribute>();
            }
            attributeList.add(eachProperty);
        }
        return attributeList;
    }

}
