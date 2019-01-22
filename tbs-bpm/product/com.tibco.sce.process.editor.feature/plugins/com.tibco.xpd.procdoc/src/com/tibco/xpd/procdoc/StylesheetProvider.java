/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.procdoc;

import java.net.URL;
import java.util.Map;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;

import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationStylesheetsProvider2;
import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationStylesheetsProvider3;

/**
 * @author nwilson
 */
public class StylesheetProvider implements ITransformationStylesheetsProvider2, ITransformationStylesheetsProvider3 {

    private static final String XSLT = "/xslts/xpdl2html.xsl"; //$NON-NLS-1$

    private static final String MESSAGES_PROPERTIES = "messages.properties"; //$NON-NLS-1$

    private URL[] xslts;
    
    private Map<String, String> xsltParameters = null;

    /**
     * @return
     * @see com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationStylesheetsProvider#getSystemId()
     */
    public String getSystemId() {
        return null;
    }

    /**
     * @return
     * @see com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationStylesheetsProvider#getXsltParameters()
     */
    public Map<String, String> getXsltParameters() {
        return xsltParameters;
    }

    public void setXsltParameters(Map<String, String> xsltParameters) {
		this.xsltParameters = xsltParameters;
	}
    
    /**
     * @return
     * @see com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationStylesheetsProvider#getXslts()
     */
    public URL[] getXslts() {
        if (xslts == null) {
            xslts = new URL[] { getClass().getResource(XSLT) };
        }

        return xslts;
    }

    /**
     * @param href
     * @return
     * @see com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationStylesheetsProvider2#getImportXsltURL(java.lang.String)
     */
    public URL getImportXsltURL(String href) {
        return null;
    }

    /**
     * @param xsltURL
     * @return
     * @see com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationStylesheetsProvider2#getMessagePropertiesURL(java.net.URL)
     */
    public URL getMessagePropertiesURL(URL xsltURL) {
        // By default return a URL to a message.properties file in the same
        // location as the xslt file.
        String fileName = xsltURL.getFile();

        int lastSlash = fileName.lastIndexOf("/"); //$NON-NLS-1$
        if (lastSlash >= 0) {
            fileName = fileName.substring(0, lastSlash);
        } else {
            fileName = ""; //$NON-NLS-1$
        }
        
        fileName = fileName + "/" + MESSAGES_PROPERTIES; //$NON-NLS-1$

        if (fileName.startsWith("/")) { //$NON-NLS-1$
            fileName = "$nl$" + fileName; //$NON-NLS-1$
            
        } else if (!fileName.startsWith("$nl$")) { //$NON-NLS-1$
            fileName = "$nl$/" + fileName; //$NON-NLS-1$
        }

        return FileLocator.find(ProcdocPlugin.getDefault().getBundle(),
                new Path(fileName),
                null);
    }

    /**
     * @see com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationStylesheetsProvider3#getCharsetEncoding()
     *
     * @return
     */
    public String getCharsetEncoding() {
        return "UTF-8"; //$NON-NLS-1$
    }
}
