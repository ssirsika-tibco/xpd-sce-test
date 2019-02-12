/**
 *
 * Copyright 2005 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.tibco.bx.xpdl2bpel.util.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDComponent;
import org.eclipse.xsd.XSDConcreteComponent;
import org.eclipse.xsd.XSDFeature;
import org.eclipse.xsd.XSDNamedComponent;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTypeDefinition;
import org.eclipse.xsd.ecore.XSDEcoreBuilder;
import org.w3c.dom.Element;

import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * TODO: - Implement support for the SDO XSD Schema annotations - Override the
 * default ecore type mappings
 * 
 * DONE: - Override the default XSDEcoreBuilder name mangling
 */
public class SDOXSDEcoreBuilder extends XSDEcoreBuilder {

    public SDOXSDEcoreBuilder(ExtendedMetaData extendedMetaData) {
        super(extendedMetaData);
    }

    @Override
    public EClassifier getEClassifier(XSDTypeDefinition xsdTypeDefinition) {
        EClassifier eClassifier = null;
        if (rootSchema.getSchemaForSchemaNamespace()
                .equals(xsdTypeDefinition.getTargetNamespace())) {
            eClassifier =
                    getBuiltInEClassifier(xsdTypeDefinition.getURI(),
                            xsdTypeDefinition.getName());
        } else {
            eClassifier = super.getEClassifier(xsdTypeDefinition);
        }
        return eClassifier;
    }

    @Override
    public EDataType getEDataType(
            XSDSimpleTypeDefinition xsdSimpleTypeDefinition) {
        EDataType eClassifier = null;
        if (rootSchema.getSchemaForSchemaNamespace()
                .equals(xsdSimpleTypeDefinition.getTargetNamespace())) {
            eClassifier =
                    (EDataType) getBuiltInEClassifier(xsdSimpleTypeDefinition.getURI(),
                            xsdSimpleTypeDefinition.getName());
        } else {
            eClassifier = super.getEDataType(xsdSimpleTypeDefinition);
        }
        return eClassifier;
    }

    @Override
    public EClass computeEClass(
            XSDComplexTypeDefinition xsdComplexTypeDefinition) {
        EClass eclass = super.computeEClass(xsdComplexTypeDefinition);
        String aliasNames =
                getEcoreAttribute(xsdComplexTypeDefinition.getElement(),
                        "aliasName");
        if (aliasNames != null) {
            SDOExtendedMetaData.INSTANCE.setAliasNames(eclass, aliasNames);
        }
        return eclass;
    }

    @Override
    protected EClassifier computeEClassifier(XSDTypeDefinition xsdTypeDefinition) {
        EClassifier eclassifier = super.computeEClassifier(xsdTypeDefinition);
        EClassifier etype = typeToTypeObjectMap.get(eclassifier);
        String aliasNames =
                getEcoreAttribute(xsdTypeDefinition.getElement(), "aliasName");
        if (aliasNames != null) {
            SDOExtendedMetaData.INSTANCE.setAliasNames(eclassifier, aliasNames);
            if (etype != null) {
                SDOExtendedMetaData.INSTANCE.setAliasNames(etype, aliasNames);
            }
        }
        return eclassifier;
    }

    @Override
    protected EDataType computeEDataType(
            XSDSimpleTypeDefinition xsdSimpleTypeDefinition) {
        EDataType edatatype = super.computeEDataType(xsdSimpleTypeDefinition);
        String aliasNames =
                getEcoreAttribute(xsdSimpleTypeDefinition.getElement(),
                        "aliasName");
        if (aliasNames != null) {
            SDOExtendedMetaData.INSTANCE.setAliasNames(edatatype, aliasNames);
        }
        return edatatype;
    }

    @Override
    protected EEnum computeEEnum(XSDSimpleTypeDefinition xsdSimpleTypeDefinition) {
        return null;
    }

    @Override
    protected EStructuralFeature createFeature(EClass eClass, String name,
            EClassifier type, XSDComponent xsdComponent, int minOccurs,
            int maxOccurs) {
        EStructuralFeature feature =
                super.createFeature(eClass,
                        name,
                        type,
                        xsdComponent,
                        minOccurs,
                        maxOccurs);
        feature.setName(name); // this is needed because super.createFeature()
                               // does EMF name mangling (toLower)
        if (xsdComponent != null) {
            String aliasNames =
                    getEcoreAttribute(xsdComponent.getElement(), "aliasName");
            if (aliasNames != null) {
                SDOExtendedMetaData.INSTANCE.setAliasNames(feature, aliasNames);
            }
        }
        return feature;
    }

    @Override
    protected String getInstanceClassName(XSDTypeDefinition typeDefinition,
            EDataType baseEDataType) {
        String name =
                getEcoreAttribute(typeDefinition, "extendedInstanceClass");
        return (name != null) ? name : super
                .getInstanceClassName(typeDefinition, baseEDataType);
    }

    @Override
    protected String getEcoreAttribute(Element element, String attribute) {
        String sdoAttribute = null;

        if ("name".equals(attribute))
            sdoAttribute = "name";
        else if ("opposite".equals(attribute))
            sdoAttribute = "oppositeProperty";
        else if ("mixed".equals(attribute))
            sdoAttribute = "sequence";
        else if ("string".equals(attribute))
            sdoAttribute = "string";
        else if ("changeable".equals(attribute))
            sdoAttribute = "readOnly";
        else if ("aliasName".equals(attribute))
            sdoAttribute = "aliasName";

        if (sdoAttribute != null) {
            String value =
                    element != null
                            && element.hasAttributeNS("commonj.sdo/xml",
                                    sdoAttribute) ? element
                            .getAttributeNS("commonj.sdo/xml", sdoAttribute)
                            : null;
            if ("changeable".equals(attribute)) {
                if ("true".equals(value))
                    value = "false";
                else if ("false".equals(value))
                    value = "true";
            }
            return value;
        }

        if ("package".equals(attribute))
            sdoAttribute = "package";
        else if ("instanceClass".equals(attribute))
            sdoAttribute = "instanceClass";
        else if ("extendedInstanceClass".equals(attribute))
            sdoAttribute = "extendedInstanceClass";
        else if ("nestedInterfaces".equals(attribute))
            sdoAttribute = "nestedInterfaces";

        if (sdoAttribute != null) {
            return element != null
                    && element.hasAttributeNS("commonj.sdo/java", sdoAttribute) ? element
                    .getAttributeNS("commonj.sdo/java", sdoAttribute) : null;
        }

        return super.getEcoreAttribute(element, attribute);
    }

    @Override
    protected String getEcoreAttribute(
            XSDConcreteComponent xsdConcreteComponent, String attribute) {
        String value = super.getEcoreAttribute(xsdConcreteComponent, attribute);
        if ("package".equals(attribute) && value == null) {
            XSDSchema xsdSchema = (XSDSchema) xsdConcreteComponent;
            IProject project = WorkingCopyUtil.getProjectFor(xsdSchema, true);
            String nsURI = xsdSchema.getTargetNamespace();
            
            /*
             * Sid ACE-194 - we don't support message events / WSDL's in ACE
             */
             throw new RuntimeException("Unexpected unsupported message activity in source process.");
//
//            value = null;
//                    NamespaceURIToJavaPackageMapper
//                            .getJavaPackageNameFromNamespaceURI(project, nsURI);
//            if (value != null) {
//                // XPD-5145 The original method "getDefaultPackageName" in this
//                // class converted the package name to lower case with a comment
//                // that this was required in order to work with Axis.
//                value = value.toLowerCase();
//            }
        }
        return value;
    }

    @Override
    protected XSDTypeDefinition getEcoreTypeQNameAttribute(
            XSDConcreteComponent xsdConcreteComponent, String attribute) {
        if (xsdConcreteComponent == null)
            return null;
        String sdoAttribute = null;

        if ("reference".equals(attribute))
            sdoAttribute = "propertyType";
        if ("dataType".equals(attribute))
            sdoAttribute = "dataType";

        if (sdoAttribute != null) {
            Element element = xsdConcreteComponent.getElement();
            return element == null ? null
                    : getEcoreTypeQNameAttribute(xsdConcreteComponent,
                            element,
                            "commonj.sdo/xml",
                            sdoAttribute);
        }

        return super
                .getEcoreTypeQNameAttribute(xsdConcreteComponent, attribute);
    }

    /**
     * Override default EMF behavior so that the name is not mangled.
     */
    @Override
    protected String validName(String name, int casing, String prefix) {
        return name;
    }

    /**
     * Override default EMF name mangling for anonymous types (simple and
     * complex)
     */
    @Override
    protected String validAliasName(XSDTypeDefinition xsdTypeDefinition,
            boolean isUpperCase) {
        return getAliasName(xsdTypeDefinition);
    }

    protected String getAliasName(XSDNamedComponent xsdNamedComponent) {
        String result = xsdNamedComponent.getName();
        if (result == null) {
            XSDConcreteComponent container = xsdNamedComponent.getContainer();
            if (container instanceof XSDNamedComponent) {
                result = getAliasName((XSDNamedComponent) container);
            }
        }
        return result;
    }

    @Override
    protected XSDTypeDefinition getEffectiveTypeDefinition(
            XSDComponent xsdComponent, XSDFeature xsdFeature) {
        if (xsdFeature == null)
            return super.getEffectiveTypeDefinition(xsdComponent, xsdFeature);

        XSDTypeDefinition typeDef =
                getEcoreTypeQNameAttribute(xsdComponent, "dataType");

        String isString = getEcoreAttribute(xsdComponent, xsdFeature, "string");
        if ("true".equalsIgnoreCase(isString)) {
            typeDef =
                    xsdFeature.resolveSimpleTypeDefinition(rootSchema
                            .getSchemaForSchemaNamespace(), "string");
        }
        if (typeDef == null)
            typeDef = xsdFeature.getType();
        return typeDef;
    }

    // Code below here to provide common URI to java packagname

    public static String uncapNameStatic(String name) {
        if (name.length() == 0) {
            return name;
        } else {
            String lowerName = name.toLowerCase();
            int i;
            for (i = 0; i < name.length(); i++) {
                if (name.charAt(i) == lowerName.charAt(i)) {
                    break;
                }
            }
            if (i > 1 && i < name.length()
                    && !Character.isDigit(name.charAt(i))) {
                --i;
            }
            return name.substring(0, i).toLowerCase() + name.substring(i);
        }
    }

    protected static String validNameStatic(String name, int casing,
            String prefix) {
        List parsedName = parseNameStatic(name, '_');
        StringBuffer result = new StringBuffer();
        for (Iterator i = parsedName.iterator(); i.hasNext();) {
            String nameComponent = (String) i.next();
            if (nameComponent.length() > 0) {
                if (result.length() > 0 || casing == UPPER_CASE) {
                    result.append(Character.toUpperCase(nameComponent.charAt(0)));
                    result.append(nameComponent.substring(1));
                } else {
                    result.append(nameComponent);
                }
            }
        }

        return result.length() == 0 ? prefix
                : Character.isJavaIdentifierStart(result.charAt(0)) ? casing == LOWER_CASE ? uncapNameStatic(result
                        .toString()) : result.toString()
                        : prefix + result;
    }

    protected static List parseNameStatic(String sourceName, char separator) {
        List result = new ArrayList();
        if (sourceName != null) {
            StringBuffer currentWord = new StringBuffer();
            boolean lastIsLower = false;
            for (int index = 0, length = sourceName.length(); index < length; ++index) {
                char curChar = sourceName.charAt(index);
                if (!Character.isJavaIdentifierPart(curChar)) {
                    curChar = separator;
                }
                if (Character.isUpperCase(curChar)
                        || (!lastIsLower && Character.isDigit(curChar))
                        || curChar == separator) {
                    if (lastIsLower && currentWord.length() > 1
                            || curChar == separator && currentWord.length() > 0) {
                        result.add(currentWord.toString());
                        currentWord = new StringBuffer();
                    }
                    lastIsLower = false;
                } else {
                    if (!lastIsLower) {
                        int currentWordLength = currentWord.length();
                        if (currentWordLength > 1) {
                            char lastChar =
                                    currentWord.charAt(--currentWordLength);
                            currentWord.setLength(currentWordLength);
                            result.add(currentWord.toString());
                            currentWord = new StringBuffer();

                            currentWord.append(lastChar);
                        }
                    }
                    lastIsLower = true;
                }

                if (curChar != separator) {
                    currentWord.append(curChar);
                }
            }

            result.add(currentWord.toString());
        }
        return result;
    }

}
