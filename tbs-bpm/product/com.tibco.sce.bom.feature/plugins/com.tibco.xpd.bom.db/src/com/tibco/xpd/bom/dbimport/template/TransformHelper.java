/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.bom.dbimport.template;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.datatools.modelbase.sql.datatypes.CharacterStringDataType;
import org.eclipse.datatools.modelbase.sql.datatypes.DateDataType;
import org.eclipse.datatools.modelbase.sql.datatypes.ExactNumericDataType;
import org.eclipse.datatools.modelbase.sql.datatypes.NumericalDataType;
import org.eclipse.datatools.modelbase.sql.datatypes.PredefinedDataType;
import org.eclipse.datatools.modelbase.sql.datatypes.TimeDataType;
import org.eclipse.datatools.modelbase.sql.tables.Column;
import org.eclipse.datatools.modelbase.sql.tables.PersistentTable;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;

import com.tibco.xpd.bom.db.api.PrimitiveDataTypeToBomDatatypeConverter;
import com.tibco.xpd.bom.dbimport.DBImportPlugin;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Utility that provides helper methods to the OAW transform.
 * 
 * @author rsomayaj
 * 
 */
public class TransformHelper {

    private static final ResourceSet rset = XpdResourcesPlugin.getDefault()
            .getEditingDomain().getResourceSet();

    /**
     * @param temp
     * @return
     */
    public static String traceMe(String temp) {
        // Activator.getDefault().getLogger().debug(temp);
        System.out.println(temp);
        return ""; //$NON-NLS-1$
    }

    public static Class createClass(PersistentTable table, Model model) {
        Class ownedClass = UMLFactory.eINSTANCE.createClass();
        model.getPackagedElements().add(ownedClass);
        PrimitivesUtil.setDisplayLabel(ownedClass, table.getName());
        return ownedClass;
    }

    public static Property createAttrib(Column column, Class cls) {
        Property attribute = UMLFactory.eINSTANCE.createProperty();
        cls.getOwnedAttributes().add(attribute);
        PrimitivesUtil.setDisplayLabel(attribute, column.getName());
        org.eclipse.datatools.modelbase.sql.datatypes.DataType dataType =
                column.getDataType();
        if (dataType instanceof PredefinedDataType) {
            PredefinedDataType predefinedDataType =
                    (PredefinedDataType) dataType;

            int scale = 0;
            if (predefinedDataType instanceof ExactNumericDataType) {
                scale = ((ExactNumericDataType) predefinedDataType).getScale();
            }
            String bomType =
                    PrimitiveDataTypeToBomDatatypeConverter
                            .convert(predefinedDataType.getPrimitiveType()
                                    .getValue(), scale);
            TransactionalEditingDomain editingDomain =
                    XpdResourcesPlugin.getDefault().getEditingDomain();
            PrimitiveType type =
                    PrimitivesUtil.getStandardPrimitiveTypeByName(editingDomain
                            .getResourceSet(), bomType);
            attribute.setType(type);
        } else {
            String message =
                    String.format("Business Object Model - Database import: Database column datatype could not be resolved. No datatype has been applied. Column-name:%s", //$NON-NLS-1$
                            column.getName()); 
            DBImportPlugin
                    .getDefault()
                    .getLogger()
                    .log(new Status(IStatus.WARNING, DBImportPlugin.PLUGIN_ID,
                            message, null));
        }
        return attribute;
    }

    public static DataType getCharacterStringType(
            CharacterStringDataType characterStringDataType, Property attrib) {
        PrimitiveType primitiveType =
                PrimitivesUtil.getStandardPrimitiveTypeByName(rset,
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);

        int length = characterStringDataType.getLength();
        if (length > 0) {
            PrimitivesUtil.setFacetPropertyValue(primitiveType,
                    PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH,
                    length,
                    attrib);
        }
        return primitiveType;
    }

    public static DataType getIntegerDataType(
            NumericalDataType numericDataType, Property attrib) {
        if (numericDataType instanceof ExactNumericDataType) {
            if (((ExactNumericDataType) numericDataType).getScale() > 0) {
                // Identifying Float
                PrimitiveType primitiveType =
                        PrimitivesUtil.getStandardPrimitiveTypeByName(rset,
                                PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);

                int precision = numericDataType.getPrecision();
                if (precision > 0) {
                    PrimitivesUtil.setFacetPropertyValue(primitiveType,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH,
                            precision,
                            attrib);
                }
                int scale = ((ExactNumericDataType) numericDataType).getScale();
                if (precision > 0) {
                    PrimitivesUtil.setFacetPropertyValue(primitiveType,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES,
                            scale,
                            attrib);
                }
                return primitiveType;
            }
        }

        PrimitiveType primitiveType =
                PrimitivesUtil.getStandardPrimitiveTypeByName(rset,
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);

        int precision = numericDataType.getPrecision();
        if (precision > 0) {
            PrimitivesUtil.setFacetPropertyValue(primitiveType,
                    PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH,
                    precision,
                    attrib);
        }
        return primitiveType;
    }

    public static DataType getDateDataType(DateDataType dateDataType,
            Property attrib) {
        return PrimitivesUtil.getStandardPrimitiveTypeByName(rset,
                PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME);
    }

    public static DataType getTimeDataType(TimeDataType timeDataType,
            Property attrib) {
        return PrimitivesUtil.getStandardPrimitiveTypeByName(rset,
                PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME);
    }

    public static Association createAssociation(Class originalCls,
            Class refClass, String assocName) {

        // Names on the ends should be changed to fk_name_index.

        // The usual case is when an attribute name and a table name is the
        // same.
        // In this scenario the relationship for foreign key is drawn with an
        // association although this may raise XSD errors.
        String end1Name =
                getUniquePropertyName(originalCls, "fk_" + refClass.getName());
        String end2Name =
                getUniquePropertyName(originalCls,
                        "fk_" + originalCls.getName());
        Association association =
                originalCls.createAssociation(true,
                        AggregationKind.NONE_LITERAL,
                        end1Name,
                        0,
                        1,
                        refClass,
                        true,
                        AggregationKind.NONE_LITERAL,
                        end2Name,
                        0,
                        -1);
        association.setName(assocName);
        return association;

    }

    private static String getUniquePropertyName(Class cls, String suggestedName) {
        List<Property> ownedAttributes = cls.getOwnedAttributes();
        String retName = suggestedName;
        int count = 1;
        for (Property property : ownedAttributes) {
            if (property.getName().equals(retName)) {
                retName = retName + count++;
            }
        }

        return retName;
    }
}
