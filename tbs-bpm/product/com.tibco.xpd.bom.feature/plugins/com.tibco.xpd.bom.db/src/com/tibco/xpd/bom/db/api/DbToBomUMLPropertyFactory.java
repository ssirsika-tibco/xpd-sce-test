/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.bom.db.api;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.datatools.modelbase.sql.datatypes.CharacterStringDataType;
import org.eclipse.datatools.modelbase.sql.datatypes.DataType;
import org.eclipse.datatools.modelbase.sql.datatypes.FixedPrecisionDataType;
import org.eclipse.datatools.modelbase.sql.datatypes.PredefinedDataType;
import org.eclipse.datatools.modelbase.sql.datatypes.PrimitiveType;
import org.eclipse.datatools.modelbase.sql.query.ValueExpressionColumn;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;

import com.tibco.xpd.bom.dbimport.DBImportPlugin;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Singleton factory class that creates a UML2 Property and sets as the
 * ownedAttribute of the specified Class
 * 
 * @author rgreen
 * 
 */
public class DbToBomUMLPropertyFactory {

    private static DbToBomUMLPropertyFactory INSTANCE = null;

    private DbToBomUMLPropertyFactory() {
    }

    public static DbToBomUMLPropertyFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DbToBomUMLPropertyFactory();
        }
        return INSTANCE;
    }

    /**
     * 
     * @param dbColumn
     * @param parentClass
     * @return Property
     * 
     */
    public Property addPropertyToClass(ValueExpressionColumn dbColumn,
            Class parentClass) {

        DataType dbType = dbColumn.getDataType();
        String name = dbColumn.getName();

        // Create the Property and set essentials
        Property prop = UMLFactory.eINSTANCE.createProperty();
        prop.setName(name);
        prop.setLower(0);
        parentClass.getOwnedAttributes().add(prop);
        PrimitivesUtil.setDisplayLabel(prop, name);

        // Now sort out its type
        boolean isTypeStudioSupported = true;
        String warningStr = null;

        if ((dbType != null) && (dbType instanceof PredefinedDataType)) {
            PredefinedDataType predefinedDbType = (PredefinedDataType) dbType;

            int type = predefinedDbType.getPrimitiveType().getValue();

            switch (type) {
            case PrimitiveType.BINARY:
            case PrimitiveType.BINARY_VARYING:
            case PrimitiveType.BINARY_LARGE_OBJECT:
            case PrimitiveType.BOOLEAN:
                setBoolean(dbType, prop);
                break;
            case PrimitiveType.CHARACTER:
            case PrimitiveType.CHARACTER_LARGE_OBJECT:
            case PrimitiveType.CHARACTER_VARYING:
            case PrimitiveType.NATIONAL_CHARACTER:
            case PrimitiveType.NATIONAL_CHARACTER_LARGE_OBJECT:
            case PrimitiveType.NATIONAL_CHARACTER_VARYING:
                setText(dbType, prop);
                break;
            case PrimitiveType.BIGINT:
            case PrimitiveType.SMALLINT:
                setInteger(dbType, prop);
                break;
            case PrimitiveType.DATE:
                setDate(dbType, prop);
                break;
            case PrimitiveType.REAL:
            case PrimitiveType.DECIMAL:
            case PrimitiveType.DOUBLE_PRECISION:
            case PrimitiveType.FLOAT:
                setDecimal(dbType, prop);
                break;
            case PrimitiveType.INTEGER:
            case PrimitiveType.INTERVAL:
            case PrimitiveType.NUMERIC:
                setIntegerOrDecimal(dbType, prop);
                break;
            case PrimitiveType.TIME:
            case PrimitiveType.TIMESTAMP:
                setTime(dbType, prop);
                break;
            // Default
            case PrimitiveType.XML_TYPE:
            case PrimitiveType.DATALINK:
                setText(dbType, prop);
                break;
            // Default text
            default:
                isTypeStudioSupported = false;
                warningStr =
                        "Business Object Model - Database import: Database column datatype could not be resolved. Default BOM datatype of Text has been applied. Column-name: %s"; //$NON-NLS-1$

                // resolve to default data type: Text
                setText(dbType, prop);
            }
        } else {
            isTypeStudioSupported = false;
            warningStr =
                    "Business Object Model - Database import: Database column datatype could not be resolved. No datatype has been applied. Column-name: %s"; //$NON-NLS-1$           
        }

        if (!isTypeStudioSupported) {
            String message = String.format(warningStr, dbColumn.getName());
            DBImportPlugin
                    .getDefault()
                    .getLogger()
                    .log(new Status(IStatus.WARNING, DBImportPlugin.PLUGIN_ID,
                            message, null));
        }

        return prop;
    }

    /**
     * Set the Property to type Time
     * 
     * @param dbType
     * @param prop
     */
    private void setTime(DataType dbType, Property prop) {
        org.eclipse.uml2.uml.PrimitiveType stdPT =
                PrimitivesUtil
                        .getStandardPrimitiveTypeByName(XpdResourcesPlugin
                                .getDefault().getEditingDomain()
                                .getResourceSet(),
                                PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME);

        prop.setType(stdPT);
    }

    /**
     * Set the Property to type Integer or decimal (based on whether there are
     * decimal places defined.
     * 
     * @param dbType
     * @param prop
     */
    private void setIntegerOrDecimal(DataType dbType, Property prop) {
        int scale = 0;

        if (dbType instanceof FixedPrecisionDataType) {
            FixedPrecisionDataType fixedDataType =
                    (FixedPrecisionDataType) dbType;
            scale = fixedDataType.getScale();
        }

        org.eclipse.uml2.uml.PrimitiveType stdPT = null;

        if (scale > 0) {
            // Create Decimal
            stdPT =
                    PrimitivesUtil
                            .getStandardPrimitiveTypeByName(XpdResourcesPlugin
                                    .getDefault().getEditingDomain()
                                    .getResourceSet(),
                                    PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);

            // Set subtype to fixed point
            PrimitivesUtil.setFacetPropertyValue(stdPT,
                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE,
                    PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT,
                    prop);

            PrimitivesUtil.setFacetPropertyValue(stdPT,
                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES,
                    scale,
                    prop);

        } else {
            // create Integer
            stdPT =
                    PrimitivesUtil
                            .getStandardPrimitiveTypeByName(XpdResourcesPlugin
                                    .getDefault().getEditingDomain()
                                    .getResourceSet(),
                                    PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        }

        prop.setType(stdPT);

    }

    /**
     * Set the Property to type Decimal
     * 
     * @param dbType
     * @param prop
     */
    private void setDecimal(DataType dbType, Property prop) {

        org.eclipse.uml2.uml.PrimitiveType stdPT =
                PrimitivesUtil
                        .getStandardPrimitiveTypeByName(XpdResourcesPlugin
                                .getDefault().getEditingDomain()
                                .getResourceSet(),
                                PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);

        prop.setType(stdPT);

    }

    /**
     * Set the Property to type Date or DateTime
     * 
     * @param dbType
     * @param prop
     */
    private void setDate(DataType dbType, Property prop) {

        org.eclipse.uml2.uml.PrimitiveType stdPT = null;

        // At the moment it seems that the only way to differentiate a SQL
        // Server DATETIME datatype is through its name
        if (dbType.getName().equals("DATETIME")) {
            stdPT =
                    PrimitivesUtil
                            .getStandardPrimitiveTypeByName(XpdResourcesPlugin
                                    .getDefault().getEditingDomain()
                                    .getResourceSet(),
                                    PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME);
        } else {
            stdPT =
                    PrimitivesUtil
                            .getStandardPrimitiveTypeByName(XpdResourcesPlugin
                                    .getDefault().getEditingDomain()
                                    .getResourceSet(),
                                    PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME);
        }

        prop.setType(stdPT);
    }

    private void setInteger(DataType dbType, Property prop) {

        org.eclipse.uml2.uml.PrimitiveType stdPT =
                PrimitivesUtil
                        .getStandardPrimitiveTypeByName(XpdResourcesPlugin
                                .getDefault().getEditingDomain()
                                .getResourceSet(),
                                PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);

        prop.setType(stdPT);
    }

    /**
     * Set the Property to type Text
     * 
     * @param dbType
     * @param prop
     */
    private void setText(DataType dbType, Property prop) {
        org.eclipse.uml2.uml.PrimitiveType stdPT =
                PrimitivesUtil
                        .getStandardPrimitiveTypeByName(XpdResourcesPlugin
                                .getDefault().getEditingDomain()
                                .getResourceSet(),
                                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);

        // Set any facet restrictions
        if (dbType instanceof CharacterStringDataType) {
            CharacterStringDataType stringType =
                    (CharacterStringDataType) dbType;

            // Get the string length if defined
            int length = stringType.getLength();

            PrimitivesUtil.setFacetPropertyValue(stdPT,
                    PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH,
                    length,
                    prop);

            // TODO: IF NO LENGTH SPECIFIED SHOULD WE AVOID PICKING UP THE
            // DEFAULT "50" FROM THE BASE PRIMITIVETYPE?

        } else {
            // Unset the default text length
            PrimitivesUtil.setFacetPropertyValue(stdPT,
                    PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH,
                    -1,
                    prop);
        }

        prop.setType(stdPT);
    }

    /**
     * Set the Property to type Boolean
     * 
     * @param dbType
     * @param prop
     */
    private void setBoolean(DataType dbType, Property prop) {
        org.eclipse.uml2.uml.PrimitiveType stdPT =
                PrimitivesUtil
                        .getStandardPrimitiveTypeByName(XpdResourcesPlugin
                                .getDefault().getEditingDomain()
                                .getResourceSet(),
                                PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);

        prop.setType(stdPT);
    }

}
