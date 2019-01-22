/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.json.schema.doc;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.internal.impl.ClassImpl;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rest.json.schema.doc.xslt.messages.Messages;

/**
 * Helper class that provides helper methods to json2html.xsl which are required
 * for the JSON schema to html conversion.
 * 
 * @author kthombar
 * @since June 04, 2015
 */
public class JsonToXsltGeneratorHelper {

    private Map<String, Property> schemaPropertyQualifiedNameToPropertyMap;

    /**
 * 
 */
    public JsonToXsltGeneratorHelper() {
        /*
         * initialize the map.
         */
        schemaPropertyQualifiedNameToPropertyMap =
                new HashMap<String, Property>();
    }

    /**
     * Caches the Map 'schemaPropertyQualifiedNameToPropertyMap' with the JSON
     * element Id to the element so that later we can easily fetch the element
     * using its ID.
     * 
     * @param jsdFilePath
     *            the Workspace relative path of the JSON file whose
     *            documentation is being exported.
     * @return <code>null</code> as this is just a dummy method to cache to Map
     *         'schemaPropertyQualifiedNameToPropertyMap'
     */
    public String cacheSchemaProperties(String jsdFilePath) {

        IFile jsdFile =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getFile(new Path(jsdFilePath));

        if (jsdFile != null) {

            WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(jsdFile);

            if (workingCopy != null) {
                EObject rootElement = workingCopy.getRootElement();

                if (rootElement instanceof Package) {
                    Package pkg = (Package) rootElement;

                    EList<PackageableElement> packagedElements =
                            pkg.getPackagedElements();

                    for (PackageableElement packageableElement : packagedElements) {

                        EList<Element> ownedElements =
                                packageableElement.getOwnedElements();

                        for (Element element : ownedElements) {

                            if (element instanceof Property) {

                                Property prop = (Property) element;
                                /*
                                 * cache all the Elements in the JSON file.
                                 */
                                schemaPropertyQualifiedNameToPropertyMap
                                        .put(packageableElement.getName()
                                                + prop.getName(), prop);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 
     * @param schemaName
     *            the name of the Schema
     * @param propertyName
     *            the referenced property name
     * @return the Data Type of the Schema Property.
     */
    @SuppressWarnings("restriction")
    public String getSchemaPropertyDataType(String schemaName,
            String propertyName) {

        if (schemaName != null && !schemaName.isEmpty() && propertyName != null
                && !propertyName.isEmpty()
                && schemaPropertyQualifiedNameToPropertyMap != null
                && !schemaPropertyQualifiedNameToPropertyMap.isEmpty()) {

            String qualifiedPropertyName = schemaName + propertyName;

            Property property =
                    schemaPropertyQualifiedNameToPropertyMap
                            .get(qualifiedPropertyName);

            if (property != null && property.getType() != null) {

                /*
                 * We only use the internal BOM primitive types and so the names
                 * are predictable. But we should translate to human readable
                 * anyhow.
                 */
                String typeName = property.getType().getName();

                if ("Text".equals(typeName)) { //$NON-NLS-1$
                    return Messages.DataTypeString_label;
                } else if ("Boolean".equals(typeName)) { //$NON-NLS-1$
                    return Messages.DataTypeBoolean_label;
                } else if ("Date".equals(typeName)) { //$NON-NLS-1$
                    return Messages.DataTypeDate_label;
                } else if ("DateTimeTZ".equals(typeName)) { //$NON-NLS-1$
                    return Messages.DataTypeDateTimeTimeZone_label;
                } else if ("Decimal".equals(typeName)) { //$NON-NLS-1$
                    return Messages.DataTypeFloat_label;
                } else if ("Integer".equals(typeName)) { //$NON-NLS-1$
                    return Messages.DataTypeInteger_label;
                } else if ("Time".equals(typeName)) { //$NON-NLS-1$
                    return Messages.DataTypeDateTime_label;

                } else if (property.getType() instanceof ClassImpl) {

                    String type = ((ClassImpl) property.getType()).getName();

                    IFile file = WorkingCopyUtil.getFile(property.getType());

                    type = type + " (" //$NON-NLS-1$
                            + file.getFullPath() + ")"; //$NON-NLS-1$

                    return type;

                }
            }
        }
        return null;
    }
}
