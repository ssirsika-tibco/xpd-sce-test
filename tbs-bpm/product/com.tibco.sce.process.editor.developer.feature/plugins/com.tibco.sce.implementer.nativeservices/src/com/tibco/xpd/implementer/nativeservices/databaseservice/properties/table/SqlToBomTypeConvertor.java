/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.databaseservice.properties.table;

import java.sql.Types;

import com.tibco.xpd.bom.types.PrimitivesUtil;

/**
 * @author NWilson
 * 
 */
public class SqlToBomTypeConvertor {

    public static String convert(int sqlType, int scale) {
        String bomType = null;
        switch (sqlType) {
        case Types.BOOLEAN:
        case Types.BIT:
            bomType = PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME;
            break;
        case Types.VARCHAR:
        case Types.NVARCHAR:
            bomType = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            break;
        case Types.CHAR:
            bomType = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            break;
        case Types.INTEGER:
        case Types.TINYINT:
        case Types.SMALLINT:
        case Types.BIGINT:
            bomType = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
            break;
        case Types.DECIMAL:
        case Types.FLOAT:
        case Types.REAL:
        case Types.DOUBLE:
            bomType = PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME;
            break;
        case Types.DATE:
            bomType = PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME;
            break;
        case Types.TIME:
            bomType = PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME;
            break;
        case Types.TIMESTAMP:
            bomType = PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME;
            break;
        case Types.NUMERIC:
            switch (scale) {
            case 0:
                bomType = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
                break;
            default:
                bomType = PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME;
                break;
            }
            break;
        default:
            bomType = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
        }
        return bomType;
    }
}
