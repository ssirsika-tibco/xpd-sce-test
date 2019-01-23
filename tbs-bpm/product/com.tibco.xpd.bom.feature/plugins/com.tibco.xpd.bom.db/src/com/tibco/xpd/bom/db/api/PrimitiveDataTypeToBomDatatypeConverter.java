/**
 * 
 */
package com.tibco.xpd.bom.db.api;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.datatools.modelbase.sql.datatypes.PrimitiveType;

import com.tibco.xpd.bom.dbimport.DBImportPlugin;
import com.tibco.xpd.bom.types.PrimitivesUtil;

/**
 * 
 * 
 * @author rsomayaj
 * @since 3.3 (24 Nov 2010)
 */
public class PrimitiveDataTypeToBomDatatypeConverter {

    public static String convert(int primitiveType, int scale) {
        String bomType = null;
        switch (primitiveType) {
        case PrimitiveType.BINARY:
        case PrimitiveType.BINARY_VARYING:
        case PrimitiveType.BINARY_LARGE_OBJECT:
        case PrimitiveType.BOOLEAN:
            bomType = PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME;
            break;
        case PrimitiveType.CHARACTER:
        case PrimitiveType.CHARACTER_LARGE_OBJECT:
        case PrimitiveType.CHARACTER_VARYING:
        case PrimitiveType.NATIONAL_CHARACTER:
        case PrimitiveType.NATIONAL_CHARACTER_LARGE_OBJECT:
        case PrimitiveType.NATIONAL_CHARACTER_VARYING:
            bomType = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            break;
        case PrimitiveType.BIGINT:
        case PrimitiveType.SMALLINT:
            bomType = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
            break;
        case PrimitiveType.DATE:
            bomType = PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME;
            break;
        case PrimitiveType.REAL:
        case PrimitiveType.DECIMAL:
        case PrimitiveType.DOUBLE_PRECISION:
        case PrimitiveType.FLOAT:
            bomType = PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME;
            break;
        case PrimitiveType.INTEGER:
        case PrimitiveType.INTERVAL:
        case PrimitiveType.NUMERIC:
            if (scale > 0) {
                bomType = PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME;
            } else {
                bomType = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
            }
            break;
        case PrimitiveType.TIME:
        case PrimitiveType.TIMESTAMP:
            bomType = PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME;
            break;
        // Default
        case PrimitiveType.XML_TYPE:
        case PrimitiveType.DATALINK:
            bomType = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            break;

        /*
         * Default text
         */
        default:
            // Unlikely to get here. If there was a problem resolving the column
            // datatype then this method would probably not be called anyway
            // since there would be no PrimitiveType to resolve.
            bomType = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            String message =
                    String.format("Business Object Model - Database import: Database column datatype could not be resoved. Default BOM datatype of Text has been applied"); //$NON-NLS-1$

            DBImportPlugin
                    .getDefault()
                    .getLogger()
                    .log(new Status(
                            IStatus.WARNING,
                            DBImportPlugin.PLUGIN_ID,
                            message,
                            null));
        }
        return bomType;
    }
}
