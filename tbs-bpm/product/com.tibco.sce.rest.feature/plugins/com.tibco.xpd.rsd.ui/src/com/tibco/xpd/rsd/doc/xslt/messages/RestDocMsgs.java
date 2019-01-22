package com.tibco.xpd.rsd.doc.xslt.messages;

import java.lang.reflect.Field;

/**
 * Messages for REST documentation export.
 * 
 * 
 * @author kthombar
 * @since June 04, 2015
 */
public class RestDocMsgs {

    public String get(String key) {
        String msg = internalGetMessage(key);
        if (msg == null) {
            return errorMessage(key);
        }
        return msg;
    }

    public String format(String key, String arg1) {
        String format = internalGetMessage(key);
        if (format == null) {
            return errorMessage(key);
        }

        return String.format(format, arg1);
    }

    public String format(String key, String arg1, String arg2) {
        String format = internalGetMessage(key);
        if (format == null) {
            return errorMessage(key);
        }

        return String.format(format, arg1, arg2);
    }

    public String format(String key, String arg1, String arg2, String arg3) {
        String format = internalGetMessage(key);
        if (format == null) {
            return errorMessage(key);
        }

        return String.format(format, arg1, arg2, arg3);
    }

    public String format(String key, String arg1, String arg2, String arg3,
            String arg4) {
        String format = internalGetMessage(key);
        if (format == null) {
            return errorMessage(key);
        }

        return String.format(format, arg1, arg2, arg3, arg4);
    }

    public String format(String key, String arg1, String arg2, String arg3,
            String arg4, String arg5) {
        String format = internalGetMessage(key);
        if (format == null) {
            return errorMessage(key);
        }

        return String.format(format, arg1, arg2, arg3, arg4, arg5);
    }

    public String format(String key, String arg1, String arg2, String arg3,
            String arg4, String arg5, String arg6) {
        String format = internalGetMessage(key);
        if (format == null) {
            return errorMessage(key);
        }

        return String.format(format, arg1, arg2, arg3, arg4, arg5, arg6);
    }

    public String format(String key, String arg1, String arg2, String arg3,
            String arg4, String arg5, String arg6, String arg7) {
        String format = internalGetMessage(key);
        if (format == null) {
            return errorMessage(key);
        }

        return String.format(format, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
    }

    public String format(String key, String arg1, String arg2, String arg3,
            String arg4, String arg5, String arg6, String arg7, String arg8) {
        String format = internalGetMessage(key);
        if (format == null) {
            return errorMessage(key);
        }

        return String.format(format,
                arg1,
                arg2,
                arg3,
                arg4,
                arg5,
                arg6,
                arg7,
                arg8);
    }

    public String format(String key, String arg1, String arg2, String arg3,
            String arg4, String arg5, String arg6, String arg7, String arg8,
            String arg9) {
        String format = internalGetMessage(key);
        if (format == null) {
            return errorMessage(key);
        }

        return String.format(format,
                arg1,
                arg2,
                arg3,
                arg4,
                arg5,
                arg6,
                arg7,
                arg8,
                arg9);
    }

    public String format(String key, String arg1, String arg2, String arg3,
            String arg4, String arg5, String arg6, String arg7, String arg8,
            String arg9, String arg10) {
        String format = internalGetMessage(key);
        if (format == null) {
            return errorMessage(key);
        }

        return String.format(format,
                arg1,
                arg2,
                arg3,
                arg4,
                arg5,
                arg6,
                arg7,
                arg8,
                arg9,
                arg10);
    }

    private String internalGetMessage(String key) {
        try {
            Field fld = Messages.class.getDeclaredField(key);

            Object val;
            val = fld.get(null);
            if (val instanceof String) {
                return (String) val;
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;

    }

    private String errorMessage(String key) {
        String err = "UNKOWN_Msg_" + key; //$NON-NLS-1$
        System.out.println("RestDocMsgs: Invalid msg requested: " + key); //$NON-NLS-1$
        return err;
    }

}
