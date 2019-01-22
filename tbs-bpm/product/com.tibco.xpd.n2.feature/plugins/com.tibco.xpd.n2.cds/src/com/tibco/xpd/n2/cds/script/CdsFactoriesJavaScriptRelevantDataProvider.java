/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.n2.cds.script;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Package;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.js.validation.tools.PackageScopeEnumCache;
import com.tibco.xpd.n2.cds.CDSActivator;
import com.tibco.xpd.n2.cds.CdsConsts;
import com.tibco.xpd.n2.cds.utils.CDSUtils;
import com.tibco.xpd.n2.scriptdescriptor.ScriptDescriptorGenerator;
import com.tibco.xpd.process.js.model.DefaultJavaScriptRelevantDataProvider;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsMethod;
import com.tibco.xpd.script.model.internal.client.DefaultJsEnumeration;
import com.tibco.xpd.script.model.internal.client.JsEnumeration;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * @author mtorres
 */
public class CdsFactoriesJavaScriptRelevantDataProvider extends
        DefaultJavaScriptRelevantDataProvider {

    private Map<String, List<Class>> factoryClassesMap = null;

    private static final String CREATE_PREPEND_NAME = "create";//$NON-NLS-1$

    private static final String FACTORY_TEMPLATE_CLASSNAME = "factory";//$NON-NLS-1$

    /**
     * Cache of enumerations in process package scope paired as <name,Set of
     * enumerations with given name>
     */
    private Map<String, Set<Enumeration>> enumerationsInProcesspackageScope;

    /*
     * XPD-2064: Keep track of Enumeration by name, to detect and handle
     * ambiguity caused by multiple enumerations with same name,in xpdl package
     * scope. XPD-4728: Using set to collect unique Enumerations
     */
    private Map<String, Set<Enumeration>> enumerationsMap = null;

    @Override
    public List<IScriptRelevantData> getScriptRelevantDataList() {
        List<JsClass> jsClasses = getJsClasses();
        if (jsClasses != null && !jsClasses.isEmpty()) {
            List<IScriptRelevantData> scriptRelevantDataList =
                    new ArrayList<IScriptRelevantData>();
            for (JsClass supportedClass : jsClasses) {
                IScriptRelevantData scriptRelevantData =
                        JScriptUtils.getScriptRelevantData(supportedClass,
                                supportedClass.getName(),
                                false);
                if (scriptRelevantData != null) {
                    scriptRelevantDataList.add(scriptRelevantData);
                }
            }
            return scriptRelevantDataList;
        }
        return Collections.emptyList();
    }

    private List<JsClass> createFactoryJsClasses(
            String factoryTemplateClassName, List<Package> referencedBomPackages) {
        Set<String> bomPackageFactoryNames =
                getBomFactoryPackageNames(factoryTemplateClassName,
                        referencedBomPackages);
        if (bomPackageFactoryNames != null) {
            List<JsClass> jsClassList = new ArrayList<JsClass>();
            for (String factoryName : bomPackageFactoryNames) {
                List<JsMethod> createFactoryMethods =
                        createFactoryMethods(factoryName);
                jsClassList
                        .add(createJsClass(factoryName, createFactoryMethods));
            }
            return jsClassList;
        }
        return Collections.emptyList();
    }

    private List<JsMethod> createFactoryMethods(String factoryName) {
        // Find classes for factoryName
        if (factoryClassesMap != null) {
            List<Class> factoryClasses = factoryClassesMap.get(factoryName);
            if (factoryClasses != null) {
                List<JsMethod> factoryMethods = new ArrayList<JsMethod>();
                for (Class factoryClass : factoryClasses) {
                    String methodName =
                            CREATE_PREPEND_NAME + factoryClass.getName();
                    if (methodName != null) {
                        // Create return type
                        DefaultJsFactoryMethodParam param =
                                new DefaultJsFactoryMethodParam(
                                        factoryClass.getName(),
                                        JScriptUtils.getFQType(factoryClass),
                                        true, false, false, 1, 1, factoryClass);
                        DefaultJsFactoryMethod method =
                                new DefaultJsFactoryMethod(methodName,
                                        Collections.EMPTY_LIST, param, false,
                                        false, "");//$NON-NLS-1$
                        method.setIcon(getIcon());
                        method.setContentAssistIconProvider(CDSUtils
                                .getCdsContentAssistIconProvider());
                        factoryMethods.add(method);
                    }
                }
                return factoryMethods;
            }
        }
        return Collections.emptyList();
    }

    public List<JsClass> getJsClasses() {
        Map<String, JsClass> jsClassesMap = getJsClassesMap();
        if (jsClassesMap == null) {
            return Collections.emptyList();
        }
        Collection<JsClass> classCollection = jsClassesMap.values();
        if (classCollection == null) {
            return Collections.emptyList();
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

    public Image getIcon() {
        Image image = null;
        if (!XpdResourcesPlugin.isInHeadlessMode()) {
            image =
                    CDSActivator.getDefault().getImageRegistry()
                            .get(CdsConsts.CDS_FACTORY);
        }
        return image;
    }

    protected JsClass createJsClass(String className, List<JsMethod> methodList) {
        DefaultJsFactoryClass jsClass =
                new DefaultJsFactoryClass(className, methodList,
                        Collections.EMPTY_LIST, Collections.EMPTY_LIST,
                        className);
        jsClass.setIcon(getIcon());
        jsClass.setContentAssistIconProvider(CDSUtils
                .getCdsContentAssistIconProvider());
        return jsClass;
    }

    protected JsEnumeration createJsEnumeration(Enumeration umlEnumeration) {
        DefaultJsEnumeration jsEnumeration =
                new DefaultJsEnumeration(umlEnumeration);
        jsEnumeration.setContentAssistIconProvider(CDSUtils
                .getCdsContentAssistIconProvider());
        return jsEnumeration;
    }

    private Set<String> getBomFactoryPackageNames(String templateClassName,
            List<Package> referencedBomPackages) {
        if (referencedBomPackages == null) {
            referencedBomPackages = Collections.emptyList();
        }
        loadFactoryClassesMap(referencedBomPackages, templateClassName);
        if (factoryClassesMap != null) {
            return factoryClassesMap.keySet();
        }
        return Collections.emptySet();
    }

    // GlobalData - Refactor moved to CDSUtils
    private void loadFactoryClassesMap(List<Package> bomPackages,
            String templateClassName) {
        factoryClassesMap = new HashMap<String, List<Class>>();
        enumerationsMap = new LinkedHashMap<String, Set<Enumeration>>();
        for (Package bomPackage : bomPackages) {
            if (bomPackage != null) {
                String factoryName =
                        getFactoryName(bomPackage, templateClassName);
                if (factoryName != null) {
                    List<Class> packageClasses = new ArrayList<Class>();
                    EList<EObject> contents = bomPackage.eContents();
                    if (contents != null) {
                        for (EObject object : contents) {
                            if (object instanceof Class) {
                                Class class1 = (Class) object;
                                packageClasses.add(class1);
                            } else if (object instanceof Enumeration) {
                                /*
                                 * XPD-2064: Handle the use of ambiguous
                                 * enumerations in script, when 2 enumerations
                                 * with same name exist in the xpdl package
                                 * scope.
                                 */
                                Enumeration enumeration = (Enumeration) object;
                                Set<Enumeration> enums =
                                        enumerationsMap.get(enumeration
                                                .getName());
                                if (enums == null) {
                                    enums = new HashSet<Enumeration>();
                                    enumerationsMap.put(enumeration.getName(),
                                            enums);
                                }
                                enums.add(enumeration);
                            }
                        }
                        factoryClassesMap.put(factoryName, packageClasses);
                    }
                }
            }
        }
    }

    private synchronized Map<String, JsClass> getJsClassesMap() {
        Map<String, JsClass> jsClassesMap = new HashMap<String, JsClass>();
        List<Package> referencedBomPackages =
                CDSUtils.getReferencedBomPackages(getActivity(),
                        getProcess(),
                        getPackage(),
                        getAssociatedProcessRelevantData());
        if (referencedBomPackages != null && !referencedBomPackages.isEmpty()) {
            addFactoryClasses(referencedBomPackages, jsClassesMap);
            // This call to add enumeration must be after addFactoryClasses
            // method
            // because the enumerations are loaded then.
            addEnumerations(referencedBomPackages, jsClassesMap);
        }
        return jsClassesMap;
    }

    private void addFactoryClasses(List<Package> referencedBomPackages,
            Map<String, JsClass> jsClassesMap) {
        List<JsClass> factoryJsClasses =
                createFactoryJsClasses(FACTORY_TEMPLATE_CLASSNAME,
                        referencedBomPackages);
        if (factoryJsClasses != null) {
            for (JsClass jsClass : factoryJsClasses) {
                String jsClassName = jsClass.getName();
                jsClassesMap.put(jsClassName, jsClass);
            }
        }
    }

    private void addEnumerations(List<Package> referencedBomPackages,
            Map<String, JsClass> jsClassesMap) {
        /*
         * XPD-2064: Handle the ambiguous enumerations in script, when 2
         * enumerations with same name exist in the xpdl package scope.
         */
        if (enumerationsMap != null && !enumerationsMap.isEmpty()) {
            for (String enumName : enumerationsMap.keySet()) {
                // get list of enumerations with this name
                Set<Enumeration> enumerationsList =
                        enumerationsMap.get(enumName);
                for (Enumeration enumeration : enumerationsList) {
                    boolean multipleEnumWithSameNameExists = false;
                    JsEnumeration jsEnumeration =
                            createJsEnumeration(enumeration);
                    String jsEnumerationName = jsEnumeration.getName();
                    if (jsEnumeration instanceof DefaultJsEnumeration) {
                        ((DefaultJsEnumeration) jsEnumeration)
                                .setQualifiedName(ProcessDataUtil
                                        .getQualifiedNameForScripting((enumeration)));
                        // to use the qualified name
                        jsEnumerationName = jsEnumeration.getName();
                    }
                    jsClassesMap.put(jsEnumerationName, jsEnumeration);
                    multipleEnumWithSameNameExists =
                            (enumerationsList.size() > 1) ? true
                                    : multipleEnumWithNameExists(enumName);
                    if (!multipleEnumWithSameNameExists) {
                        /*
                         * In case of unambiguous situation, There another entry
                         * for unqualified name.
                         */
                        // create entry for default
                        JsEnumeration defaultJsEnumeration =
                                createJsEnumeration(enumeration);
                        jsClassesMap.put(defaultJsEnumeration.getName(),
                                defaultJsEnumeration);

                    }
                }

            }
        }
    }

    /**
     * This method checks for existence of multiple enumerations with given
     * name, in the current xpdl package scope.Returns true if multiple
     * enumerations with given name exists in scope.This methods checks the
     * {@link PackageScopeEnumCache} , for the current Process package to
     * determine the existence of multiple enumerations with same name in
     * package scope.
     * 
     * @param enumName
     * @return
     */
    private boolean multipleEnumWithNameExists(String enumName) {
        // if local data provider cache is empty
        if (enumerationsInProcesspackageScope == null) {
            com.tibco.xpd.xpdl2.Package processPackage = getPackage();
            if (processPackage == null) {
                Process process = getProcess();
                if (process == null && getActivity() != null) {
                    process = getActivity().getProcess();
                }
                if (process != null && process.getPackage() != null) {
                    processPackage = process.getPackage();
                }
            }
            // check for the cache
            if (processPackage != null) {
                // Should always be set for validations
                PackageScopeEnumCache packageScopeEnumCache =
                        (PackageScopeEnumCache) getCustomPropertyClass(PackageScopeEnumCache.class);
                if (packageScopeEnumCache == null) {
                    // This stage is true only for content-assist and
                    // refactoring
                    packageScopeEnumCache =
                            new PackageScopeEnumCache(processPackage);
                }
                // get cache for the current Process package
                enumerationsInProcesspackageScope =
                        packageScopeEnumCache.getEnumCache();

            }

        }
        if (enumerationsInProcesspackageScope.get(enumName) != null
                && enumerationsInProcesspackageScope.get(enumName).size() > 1) {
            return true;
        }
        return false;
    }

    private String getFactoryName(Package pkg, String templateClassName) {
        if (pkg != null && templateClassName != null) {
            /*
             * Sid XPD_3641: Switch to using non-indexer-hitting get package
             * name
             */
            return ScriptDescriptorGenerator.getFactoryForPackage(pkg);
        }
        return null;
    }

    @Override
    protected List<IScriptRelevantData> convertToScriptRelevantData(
            List<ProcessRelevantData> processDataList) {
        return CDSUtils
                .convertToScriptRelevantData(processDataList,
                        getProject(),
                        readContributedDefinitionReaders(getProcessDestinationList(getProcess())));
    }

}
