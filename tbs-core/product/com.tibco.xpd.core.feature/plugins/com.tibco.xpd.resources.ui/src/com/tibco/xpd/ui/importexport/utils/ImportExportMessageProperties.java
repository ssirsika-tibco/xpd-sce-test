/**
 * ImportExportMessageProperties.java
 *
 * 
 *
 * @author aallway
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.ui.importexport.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationStylesheetsProvider2;

/**
 * This class provides access to messages via the message.properties file
 * provided to ImportExportTransformer by a given implementation of
 * {@link ITransformationStylesheetsProvider2}.
 * <p>
 * In order to make localisation of the output of an Export xslt more simple
 * (!). The individual xxxExportWizard can implement the
 * {@link ITransformationStylesheetsProvider2#getMessagePropertiesURL(URL)} to
 * return a URL to a message properties file (using FileLocator to ensure
 * correct bundle localisation).
 * </p>
 * <p>
 * Having done so, then ImportExportTransformer will pass an extra xsl parameter
 * variable to the provid3er's xslt stylesheet. The parameter is called
 * messsagePropertiesId that is an identifier that the xslt can use to construct
 * an ImportExportProperties instance.
 * </p>
 * <p>
 * This is how to set up to use message.properties from xslt...
 * <li>Ensure that your AbstractExportWizard subclass implements
 * {@link ITransformationStylesheetsProvider2} and returns a valid URL for the
 * message.properties in your plugin bundle from the getMessagePropertiesURL()
 * method.</li>
 * 
 * <li>Ensure that you have java extensions enabled in xslt namespace in the
 * main xsl:stylesheet element...</li>
 * <br/>
 * <code>&lt;xsl:stylesheet xmlns:java="http://xml.apache.org/xslt/java" ....</code>
 * 
 * <li>Declare the messagePropertiesId parameter that will be passed to your
 * XSLT (if your xslts provider has provided a URL via
 * getMessagePropertiesURL())...Under xsl:stylesheet</li>
 * <br/> <code>
 * &lt;xsl:param name=&quot;messagePropertiesId&quot;/&gt;
 * </code>
 * 
 * <li>Construct an instance of the ImportExportMessageProperties class as an
 * xslt stylesheet global variable (passing the messagePropertiesId parameter
 * value that has been passed to your xslt...</li>
 * <br/> <code>
 * &lt;!-- The following assignment of param to temporary variable is required to ensure that evaluation of creation of class is not done UNTIL xslt is run (rather than on compile which will fail) --&gt 
 * <br/>
 * &lt;xsl:variable name="msgPropsId"&gt;&lt;xsl:value-of select="$messagePropertiesId"/&gt;&jt;/xsl:variable&gt;
 * <br/>
 * &lt;xsl:variable name=&quot;msgProps&quot; select=&quot;java:com.tibco.xpd.ui.importexport.utils.ImportExportMessageProperties.new($msgPropsId)&quot;/&gt;
 * </code>
 * 
 * <li>You xslt can now access messages defined in the message.properties
 * identified by you xslts provider in any XPath expression...</li>
 * <br/> <code>
 * &lt;xsl:value-of select=&quot;java:get($msgProps, 'MessageKey')&quot;/&gt;
 * </code>
 * 
 * <li>Or to create a string with variable parts (where message in properties
 * meets String.format() requirements...</li>
 * <br/> <code>
 * &lt;xsl:value-of select=&quot;java:format($msgProps, 'MessageKey', 'Arg 1')&quot;/&gt;
 * </code> <br/> <code>
 * Between one and 10 String Arg parameters can be passed after the message key.
 * </p>
 * 
 */
public class ImportExportMessageProperties {

    /**
     * MsgPropsCacheEntry
     * <p>
     * Data class representing one entry in the message properties cache.
     * <p>
     * It links the loaded Properties with the URL it came from and a unique Id
     * that can be used to construct an ImportExportMessageProperties wrapper
     * for the properties.
     */
    private static class MsgPropsCacheEntry {
        URL msgURL;

        Properties msgProps;

        String msgPropsId;
    }

    /**
     * List of currently cached message properties.
     */
    private static List<MsgPropsCacheEntry> messagePropertiesCache =
            new ArrayList<MsgPropsCacheEntry>();

    /**
     * Create (or return already cached) ImportExportMessageProperties instance
     * of for the given URL.
     * 
     * @param msgURL
     * @return Identifier key that can be used to construct an
     *         ImportExportMessageProperties object.
     */
    static String getMessagePropertiesId(URL msgURL) {
        // Check for an entry already cached for given URL.
        for (MsgPropsCacheEntry entry : messagePropertiesCache) {
            if (entry.msgURL.equals(msgURL)) {
                return entry.msgPropsId;
            }
        }

        Properties msgProps = null;

        try {
            InputStream msgStream = msgURL.openStream();

            msgProps = new Properties();
            msgProps.load(msgStream);

            msgStream.close();

        } catch (IOException e) {
            // Couldn't open message.properties stream
            XpdResourcesUIActivator.getDefault().getLogger().error(e,
                    "Cannot access ImportExport message properties file."); //$NON-NLS-1$
        }

        // Cache it (whether or not load succeeded, at least then the xslt can
        // use the message wrapper and receive "unknown msg" type strings back.
        MsgPropsCacheEntry entry = new MsgPropsCacheEntry();
        entry.msgURL = msgURL;
        entry.msgProps = msgProps;
        entry.msgPropsId =
                (messagePropertiesCache.size() + 1) + ":" + msgURL.getPath(); //$NON-NLS-1$

        messagePropertiesCache.add(entry);

        return entry.msgPropsId;
    }

    // --------------------------------------------------------------
    // The ImportExportMessagesProperties class itself
    // This wraps up the message.properties that the xslts provider chose to go
    // with the xslt and provides methods to get a message from the properties
    // file or perform the equivalent of a String.format using the properties
    // defined string as the first parameter followed by up to 10 String 'argv's

    private String msgPropertiesId;

    private MsgPropsCacheEntry msgPropsCacheEntry;

    /**
     * @param msgPropertiesId
     *            Cached message properties Id - this must be an Id returned by
     *            the getMessagePropertiesId() method.
     */
    public ImportExportMessageProperties(String msgPropertiesId) {
        this.msgPropertiesId = msgPropertiesId;
        this.msgPropsCacheEntry = null;

        // Look for the previously cached message properties file with the given
        // id.
        for (MsgPropsCacheEntry entry : messagePropertiesCache) {
            if (entry.msgPropsId.equals(msgPropertiesId)) {
                msgPropsCacheEntry = entry;
                break;
            }
        }

        if (msgPropsCacheEntry == null) {
            XpdResourcesUIActivator
                    .getDefault()
                    .getLogger()
                    .error("ImportExportMessageProperties: No message properties cache entry for requested id: " + msgPropertiesId); //$NON-NLS-1$
        }

        return;
    }

    /**
     * @param key
     */
    private String internalGetMessage(String key) {
        String ret = null;
        if (msgPropsCacheEntry != null && msgPropsCacheEntry.msgProps != null) {
            ret = msgPropsCacheEntry.msgProps.getProperty(key);
        }

        return ret;
    }

    private String errorMessage(String key) {
        if (msgPropsCacheEntry == null) {
            return "No message properties avaliable for id '" + msgPropertiesId
                    + "'";
        } else if (msgPropsCacheEntry.msgProps == null) {
            return "Load properties '" + msgPropsCacheEntry.msgURL
                    + "' failed.";

        } else {
            return "Unknown Message Key '" + key + "'";
        }
    }

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

}
