/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.bom.wsdltransform.builder;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Type;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.xsd.XSDNamedComponent;
import org.eclipse.xsd.XSDPackage;
import org.eclipse.xsd.XSDSchema;

import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;

/**
 * 
 * Provided a BOM data and a definition find the xsd element specific to the BOM
 * element
 * 
 * @author rsomayaj
 * 
 */
public class XSDRelevantData {

    private final EObject bomElement;

    private final EObject defnObj;

    private String targetNamespace;

    private String xsdElementName;

    /**
     * @param defnObj
     * @param bomElement
     * 
     */
    public XSDRelevantData(EObject defnObj, EObject bomElement) {
        this.defnObj = defnObj;
        this.bomElement = bomElement;
    }

    private String getNamespace() {
        Package umlPkg = ((Type) bomElement).getPackage();
        if (umlPkg != null) {
            return XSDUtil.getNamespaceForPackage(umlPkg);
        }
        return null;
    }

    /**
     * @param namespace
     * @param name
     * @param bomId
     * @return
     */
    private String getXsdElement(String namespace, String name) {
        if (defnObj instanceof Definition) {
            Definition definition = (Definition) defnObj;
            if (definition.getETypes() != null) {
                List schemas = definition.getETypes().getSchemas(namespace);
                for (Object object : schemas) {
                    if (object instanceof XSDSchema) {
                        XSDSchema schema = (XSDSchema) object;

                        EObject findInList =
                                findInList(schema.getTypeDefinitions(),
                                        XSDPackage.eINSTANCE
                                                .getXSDNamedComponent_Name(),
                                        name);
                        if (findInList == null) {
                            findInList =
                                    findInList(schema.getElementDeclarations(),
                                            XSDPackage.eINSTANCE
                                                    .getXSDNamedComponent_Name(),
                                            name);
                            if (findInList instanceof XSDNamedComponent) {
                                return ((XSDNamedComponent) findInList)
                                        .getName();
                            }
                        } else {
                            if (findInList instanceof XSDNamedComponent) {
                                return ((XSDNamedComponent) findInList)
                                        .getName();
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * @return the targetNamespace
     */
    public String getTargetNamespace() {
        if (targetNamespace == null) {
            if (defnObj instanceof Definition) {
                Definition definition = (Definition) defnObj;
                org.eclipse.wst.wsdl.Types types = definition.getETypes();

                if (types != null) {
                    List<XSDSchema> schemas = types.getSchemas(getNamespace());
                    // Looking for only the first schema with the same namespace
                    // -
                    // Need to check with Sid if there could be more.

                    if (!schemas.isEmpty()) {
                        XSDSchema schema = schemas.get(0);

                        targetNamespace = schema.getTargetNamespace();

                    }
                }

            }
        }
        return targetNamespace;
    }

    /**
     * @return the xsdElementName
     */
    public String getXsdElementName() {
        if (xsdElementName == null) {
            if (bomElement instanceof Type) {

                // Cannot be using the indexer to do this because you dont know
                // whether the indexer has been updated or not! It might be
                // better
                // to use the model itself. And moreover, this will need to be
                // stopped.

                // Just to confirm that such an element exists.

                String bomId =
                        ((Type) bomElement).eResource()
                                .getURIFragment(bomElement);
                xsdElementName =
                        getXsdElement(getNamespace(), ((Type) bomElement)
                                .getName());

            }
        }
        return xsdElementName;
    }

    /**
     * The item is indexed only if this returns true to check if the XSD
     * relevant data is valid.
     * 
     * @return
     */
    public boolean isValid() {
        return true;
    }

    /**
     * Return first element on the list that has given value on given feature
     * 
     * @param list
     *            list with objects that have structural feature 'attrib'
     * @param attrib
     *            structural feature that will be tested
     * @param value
     *            required value of the structural feature
     * @return first element that meets criteria or null
     */
    private EObject findInList(List<?> list, EStructuralFeature attrib,
            Object value) {
        if (!list.isEmpty() && value != null) {
            for (Iterator<?> iter = list.iterator(); iter.hasNext();) {
                EObject obj = (EObject) iter.next();
                Object cVal = obj.eGet(attrib);
                if (value.equals(cVal)) {
                    return obj;
                }
            }
        }
        return null;
    }

}
