/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.script.model.client;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;

import com.tibco.xpd.script.model.jscript.JScriptUtils;

/**
 * 
 * 
 * @author mtorres
 * 
 */
public abstract class AbstractUMLScriptRelevantData extends
        DefaultScriptRelevantData implements IUMLScriptRelevantData {

    private HashMap<String, JsClass> cachedClassMap = null;

    private String className = null;

    private Class multipleClass = null;

    private boolean loadModel = true;

    public abstract URI getURI();

    protected void doLoadModel() {
        if (cachedClassMap != null) {
            return;
        }
        URI uri = getURI();
        if (uri == null) {
            return;
        }
        ResourceSetImpl resourceSet = new ResourceSetImpl();
        resourceSet.getPackageRegistry().put(UMLPackage.eNS_URI,
                UMLPackage.eINSTANCE);
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
                .put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
        try {
            Resource resource = resourceSet.createResource(uri);
            resource.load(Collections.EMPTY_MAP);
            Package umlPackage =
                    (Package) EcoreUtil.getObjectByType(resource.getContents(),
                            UMLPackage.Literals.PACKAGE);
            List<PackageableElement> packagedElements =
                    umlPackage.getPackagedElements();
            if (packagedElements == null) {
                return;
            }
            for (PackageableElement element : packagedElements) {
                if (element instanceof Class) {
                    Class clElement = (Class) element;
                    JsClass jsClass =
                            createJsClass(clElement, getMultipleClass());
                    String jsClassName = jsClass.getName();
                    if (cachedClassMap == null) {
                        cachedClassMap = new HashMap<String, JsClass>();
                    }
                    cachedClassMap.put(jsClassName, jsClass);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JsClass getJsClass() {
        if (this.loadModel) {
            doLoadModel();
        }
        if (cachedClassMap == null || getClassName() == null) {
            return null;
        }
        return cachedClassMap.get(getClassName());
    }

    protected JsClass createJsClass(Class umlClass, Class multipleUmlClass) {
        DefaultJsClass jsClass = new DefaultJsClass(umlClass, multipleUmlClass);
        jsClass.setIcon(getIcon());
        jsClass.setContentAssistIconProvider(JScriptUtils
                .getJsContentAssistIconProvider());
        return jsClass;
    }

    private String getClassName() {
        return className;
    }

    @Override
    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public void addClass(Class umlClass) {
        addClass(umlClass, null);
    }

    public void addClass(Class umlClass, Class multipleClass) {
        if (umlClass != null) {
            JsClass jsClass = createJsClass(umlClass, multipleClass);
            String jsClassName = jsClass.getName();
            if (cachedClassMap == null) {
                cachedClassMap = new HashMap<String, JsClass>();
            }
            cachedClassMap.put(jsClassName, jsClass);
        }
    }

    public void addJsClass(JsClass jsClass) {
        String jsClassName = jsClass.getName();
        if (cachedClassMap == null) {
            cachedClassMap = new HashMap<String, JsClass>();
        }
        JsClass tempJsClass = cachedClassMap.get(jsClassName);
        if (tempJsClass == null) {
            cachedClassMap.put(jsClassName, jsClass);
        }
    }

    public void setLoadModel(boolean loadModel) {
        this.loadModel = loadModel;
    }

    @Override
    public String getType() {
		/* Sid ACE-8226 detect special system classes (like bpmScripts) and remove the system tag from the type name. */
		String typeName = this.getClassName();

		if (typeName.endsWith("$$"))
		{
			int i = typeName.indexOf("_$$");

			if (i > 0)
			{
				typeName = typeName.substring(0, i);
			}
		}
		return typeName;
    }

    public Class getMultipleClass() {
        if (multipleClass != null) {
            return multipleClass;
        }
        return JScriptUtils.getDefaultMultipleClass();
    }

    public void setMultipleClass(Class multipleClass) {
        this.multipleClass = multipleClass;
    }

    @Override
    public boolean isContextless() {
        if (getJsClass() != null && getJsClass().getUmlClass() != null) {
            return JScriptUtils.isContextlessClass(getJsClass().getUmlClass());
        }
        return super.isContextless();
    }

}
