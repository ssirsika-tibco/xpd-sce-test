package com.tibco.bx.debug.core.models.variable;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.tibco.bx.debug.api.IDebugConstants;
import com.tibco.bx.debug.common.model.VariableType;
import com.tibco.bx.debug.core.util.DateUtil;

public class BxPrimitiveType extends VariableType {

	private final static String NULL_ATTR = IDebugConstants.NULL_ATTR;

	public static BxPrimitiveType BOOLEAN = new BxPrimitiveType("Boolean") { //$NON-NLS-1$
		@Override
		public Object getValue(String valueString) {
			return NULL_ATTR.equals(valueString) ? null : Boolean.valueOf(valueString);
		}
	};
	public static BxPrimitiveType CHAR = new BxPrimitiveType("Char") { //$NON-NLS-1$
		@Override
		public Object getValue(String valueString) {
			return NULL_ATTR.equals(valueString) ? null : Character.valueOf(valueString.charAt(0));
		}
	};
	public static BxPrimitiveType BYTE = new BxPrimitiveType("Byte") { //$NON-NLS-1$
		@Override
		public Object getValue(String valueString) {
			return NULL_ATTR.equals(valueString) ? null : Byte.valueOf(valueString);
		}
	};
	public static BxPrimitiveType SHORT = new BxPrimitiveType("Short") { //$NON-NLS-1$
		@Override
		public Object getValue(String valueString) {
			return NULL_ATTR.equals(valueString) ? null : Byte.valueOf(valueString);
		}
	};
	public static BxPrimitiveType INTEGER = new BxPrimitiveType("Integer") { //$NON-NLS-1$
		@Override
		public Object getValue(String valueString) {
			return NULL_ATTR.equals(valueString) ? null : Integer.valueOf(valueString);
		}
	};
	public static BxPrimitiveType LONG = new BxPrimitiveType("Long") { //$NON-NLS-1$
		@Override
		public Object getValue(String valueString) {
			return NULL_ATTR.equals(valueString) ? null : Long.valueOf(valueString);
		}
	};
	public static BxPrimitiveType FLOAT = new BxPrimitiveType("Float") { //$NON-NLS-1$
		@Override
		public Object getValue(String valueString) {
			return NULL_ATTR.equals(valueString) ? null : Float.valueOf(valueString);
		}
	};
	public static BxPrimitiveType DOUBLE = new BxPrimitiveType("Double") { //$NON-NLS-1$
		@Override
		public Object getValue(String valueString) {
			return NULL_ATTR.equals(valueString) ? null : Double.valueOf(valueString);
		}
	};
	public static BxPrimitiveType DECIMAL = new BxPrimitiveType("Decimal") { //$NON-NLS-1$
		@Override
		public Object getValue(String valueString) {
			return NULL_ATTR.equals(valueString) ? null : new BigDecimal(valueString);
		}
	};
	public static BxPrimitiveType STRING = new BxPrimitiveType("String") { //$NON-NLS-1$
		@Override
		public Object getValue(String valueString) {
			return NULL_ATTR.equals(valueString) ? null : valueString;
		}
	};

	public static BxPrimitiveType PERFORMER = new BxPrimitiveType("Performer") { //$NON-NLS-1$
		@Override
		public Object getValue(String valueString) {
			return NULL_ATTR.equals(valueString) ? null : valueString;
		}
	};

	public static BxPrimitiveType BIGINTEGER = new BxPrimitiveType("BigInteger") { //$NON-NLS-1$
		@Override
		public Object getValue(String valueString) {
			return NULL_ATTR.equals(valueString) ? null : new BigInteger(valueString);
		}
	};

	public static BxPrimitiveType BIGDECIMAL = new BxPrimitiveType("BigDecimal") { //$NON-NLS-1$
		@Override
		public Object getValue(String valueString) {
			return NULL_ATTR.equals(valueString) ? null : new BigDecimal(valueString);
		}
	};

	public static BxPrimitiveType DATE = new BxPrimitiveType("XMLGregorianCalendar") { //$NON-NLS-1$
		@Override
		public Object getValue(String valueString) {
			return NULL_ATTR.equals(valueString) ? null : DateUtil.parse(valueString);
		}
	};

	public static BxPrimitiveType TIME = new BxPrimitiveType("XMLGregorianCalendar") { //$NON-NLS-1$
		@Override
		public Object getValue(String valueString) {
			return NULL_ATTR.equals(valueString) ? null : DateUtil.parse(valueString);
		}
	};

	public static BxPrimitiveType DATETIME = new BxPrimitiveType("XMLGregorianCalendar") { //$NON-NLS-1$
		@Override
		public Object getValue(String valueString) {
			return NULL_ATTR.equals(valueString) ? null : DateUtil.parse(valueString);
		}
	};

	public static BxPrimitiveType DATETIMEZ = new BxPrimitiveType("XMLGregorianCalendar") { //$NON-NLS-1$
		@Override
		public Object getValue(String valueString) {
			return NULL_ATTR.equals(valueString) ? null : DateUtil.parse(valueString);
		}
	};

	public static BxPrimitiveType XMLCALENDAR = new BxPrimitiveType("XMLGregorianCalendar") { //$NON-NLS-1$
		@Override
		public Object getValue(String valueString) {
			return NULL_ATTR.equals(valueString) ? null : DateUtil.createXMLGregorianCalendar(valueString);
		}
	};

	public static BxPrimitiveType DURATION = new BxPrimitiveType("Duration") { //$NON-NLS-1$
		@Override
		public Object getValue(String valueString) {
			return NULL_ATTR.equals(valueString) ? null : DateUtil.createDuration(valueString);
		}
	};

	public BxPrimitiveType(String typeString) {
		super(typeString);
	}
}
