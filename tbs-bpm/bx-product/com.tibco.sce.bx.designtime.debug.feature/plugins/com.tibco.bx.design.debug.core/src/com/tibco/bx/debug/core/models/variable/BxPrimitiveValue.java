package com.tibco.bx.debug.core.models.variable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;

import com.tibco.bx.debug.api.IDebugConstants;
import com.tibco.bx.debug.common.model.ProcessVariable;
import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.Messages;
import com.tibco.bx.debug.core.models.IBxDebugTarget;
import com.tibco.bx.debug.core.util.DateUtil;

/**
 * A primitive value on a process debug target
 */
public class BxPrimitiveValue extends BxValue {

	public BxPrimitiveValue(ProcessVariable processVariable, IBxDebugTarget debugTarget) {
		super(processVariable, debugTarget);
	}

	public String getReferenceTypeName() {
		return type.toString();
	}

	public String getValueString() {
		XMLGregorianCalendar calendar = null;
		if (value == null) {
			return "null";//$NON-NLS-1$
		}
		if (value instanceof Date) {
			calendar = DateUtil.createXMLGregorianCalendar4Date((Date) value);
		} else if (value instanceof XMLGregorianCalendar) {
			calendar = (XMLGregorianCalendar) value;
		}
		if (BxPrimitiveType.TIME.equals(type)) {
			return DateUtil.formatLocaleTime(calendar.toGregorianCalendar().getTime());
		} else if (BxPrimitiveType.DATE.equals(type)) {
			return DateUtil.formatLocaleDate(calendar.toGregorianCalendar().getTime());
		} else if (BxPrimitiveType.DATETIME.equals(type)) {
			return DateUtil.formatLocaleDateTime(calendar.toGregorianCalendar().getTime());
		} else if (BxPrimitiveType.DATETIMEZ.equals(type)) {
			return DateUtil.formatLocaleDateTimeZone(calendar.toGregorianCalendar().getTime(), calendar.getTimeZone(0));
		} else if (BxPrimitiveType.DURATION.equals(type)) {
			return DateUtil.formatLocaleDuration((Duration) value);
		}
		return value.toString();
	}

	@Override
	public boolean verifyValue(String expression) throws DebugException {
		Object object = getValue(expression);
		return object != null;
	}

	public Object getValue(String expression) throws DebugException {
		try {
			if (BxPrimitiveType.STRING.equals(type)) {
				return IDebugConstants.NULL_ATTR.equals(expression) ? null : expression;
			} else if (BxPrimitiveType.BOOLEAN.equals(type)) {
				return IDebugConstants.NULL_ATTR.equals(expression) ? null : Boolean.valueOf(expression);
			} else if (BxPrimitiveType.CHAR.equals(type)) {
				return IDebugConstants.NULL_ATTR.equals(expression) ? null : Character.valueOf(expression.charAt(0));
			} else if (BxPrimitiveType.BYTE.equals(type)) {
				return IDebugConstants.NULL_ATTR.equals(expression) ? null : Byte.valueOf(expression);
			} else if (BxPrimitiveType.SHORT.equals(type)) {
				return IDebugConstants.NULL_ATTR.equals(expression) ? null : Short.valueOf(expression);
			} else if (BxPrimitiveType.INTEGER.equals(type)) {
				return IDebugConstants.NULL_ATTR.equals(expression) ? null : Integer.valueOf(expression);
			} else if (BxPrimitiveType.LONG.equals(type)) {
				return IDebugConstants.NULL_ATTR.equals(expression) ? null : Long.valueOf(expression);
			} else if (BxPrimitiveType.FLOAT.equals(type)) {
				return IDebugConstants.NULL_ATTR.equals(expression) ? null : Float.valueOf(expression);
			} else if (BxPrimitiveType.DOUBLE.equals(type)) {
				return IDebugConstants.NULL_ATTR.equals(expression) ? null : Double.valueOf(expression);
			} else if (BxPrimitiveType.DECIMAL.equals(type)) {
				return IDebugConstants.NULL_ATTR.equals(expression) ? null : new BigDecimal(expression);
			} else if (BxPrimitiveType.BIGDECIMAL.equals(type)) {
				return IDebugConstants.NULL_ATTR.equals(expression) ? null : new BigDecimal(expression);
			} else if (BxPrimitiveType.BIGINTEGER.equals(type)) {
				return IDebugConstants.NULL_ATTR.equals(expression) ? null : new BigInteger(expression);
			} else if (BxPrimitiveType.TIME.equals(type)) {
				expression = DateUtil.formatGregorianCalendarTime(expression);
				return IDebugConstants.NULL_ATTR.equals(expression) ? null : DateUtil.createXMLGregorianCalendar(expression);
			} else if (BxPrimitiveType.DATE.equals(type)) {
				expression = DateUtil.formatGregorianCalendarDate(expression);
				return IDebugConstants.NULL_ATTR.equals(expression) ? null : DateUtil.createXMLGregorianCalendar(expression);
			} else if (BxPrimitiveType.DATETIME.equals(type)) {
				expression = DateUtil.formatGregorianCalendarDateTime(expression);
				return IDebugConstants.NULL_ATTR.equals(expression) ? null : DateUtil.createXMLGregorianCalendar(expression);
			} else if (BxPrimitiveType.DATETIMEZ.equals(type)) {
				if (DateUtil.isValidZone(expression)) {
					expression = DateUtil.formatGregorianCalendarDateTimeZone(expression);
					return IDebugConstants.NULL_ATTR.equals(expression) ? null : DateUtil.createXMLGregorianCalendar(expression);
				}
			} else if (BxPrimitiveType.DURATION.equals(type)) {
				return IDebugConstants.NULL_ATTR.equals(expression) ? null : DateUtil.createDuration(expression);
			} else if (BxPrimitiveType.PERFORMER.equals(type)) {
				return IDebugConstants.NULL_ATTR.equals(expression) ? null : expression;
			}
			return null;
		} catch (Exception e) {
			throw new DebugException(new Status(IStatus.ERROR, DebugCoreActivator.PLUGIN_ID, IStatus.OK, String.format(Messages
					.getString("BxPrimitiveValue.invalidExpression"), new Object[] { expression }), e)); //$NON-NLS-1$
		}
	}

	@Override
	public boolean canModify() {
		return true;
	}

}
