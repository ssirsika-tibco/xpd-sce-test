/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.procdoc;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.transform.TransformerConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.xml.sax.SAXException;

import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer;
import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationCharsetEncodingProvider;
import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationStylesheetsProvider;
import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ImportExportCachedInfo;
import com.tibco.xpd.xpdl2.Process;

/**
 * SvgProcessImageCreator
 * 
 * 
 * @author aallway
 * @since 3.3 (9 Sep 2009)
 */
public class SvgProcessImageCreator implements
        ITransformationStylesheetsProvider,
        ITransformationCharsetEncodingProvider {

    private static final String XSLT = "/xslts/svgImage/xpdl2svg.xslt"; //$NON-NLS-1$

    private static ImportExportCachedInfo cacheSVGTransformerInfo = null;

    private boolean cacheSVGTransformer = false;

    private Map<SvgParam, String> svgXsltParameters;

    private Process currentProcess = null;

    /**
     * 
     * @param targetFolder
     * @param svgXsltParameters
     *            See xpdl2svg.xslt for parameter definitions.
     */
    public SvgProcessImageCreator(Map<SvgParam, String> svgXsltParameters) {
        this.svgXsltParameters = svgXsltParameters;

    }

    /**
     * Transform given process to an output svg image.
     * 
     * @param process
     * @param destPath
     * @throws SAXException
     * @throws IOException
     * @throws TransformerConfigurationException
     */
    public void createSVGImage(Process process, File destFile)
            throws TransformerConfigurationException, IOException, SAXException {

        currentProcess = process;

        ImportExportTransformer transformer = null;

        if (cacheSVGTransformer) {
            if (cacheSVGTransformerInfo == null) {
                transformer = new ImportExportTransformer(this);

                cacheSVGTransformerInfo =
                        transformer.getImportExportCachedInfo();

            } else {
                transformer =
                        new ImportExportTransformer(this,
                                cacheSVGTransformerInfo);
            }
        } else {
            transformer = new ImportExportTransformer(this);
        }

        try {
            IFile pkgFile = WorkingCopyUtil.getFile(process);

            InputStream inStream = pkgFile.getContents();

            /*
             * Use an output stream with UTF-8 encoding to preserve valid
             * multi-byte characters etc.
             */
            FileOutputStream outStream = new FileOutputStream(destFile);

            BufferedOutputStream buffOutStream =
                    new BufferedOutputStream(outStream);

            transformer.performTransformation(inStream, buffOutStream);

            outStream.close();
            inStream.close();

        } catch (CoreException e) {
            throw new IOException(
                    "File I/O Error on source pkg / target svgt file."); //$NON-NLS-1$
        }

        return;
    }

    public String getSystemId() {
        return null;
    }

    public Map<String, String> getXsltParameters() {
        HashMap<String, String> paramsWithProcess =
                new HashMap<String, String>();

        paramsWithProcess.put(SvgParam.processId.toString(), currentProcess
                .getId());

        if (svgXsltParameters != null) {
            for (Entry<SvgParam, String> param : svgXsltParameters.entrySet()) {
                paramsWithProcess.put(param.getKey().toString(), param
                        .getValue());
            }
        }

        return paramsWithProcess;
    }

    public URL[] getXslts() {
        return new URL[] { getClass().getResource(XSLT) };
    }

    /**
     * SvgParam
     * <p>
     * Parameter names available for xpdl2svg.xslt. For detail on values see the
     * xslt.
     * <p>
     * 
     * @author aallway
     * @since 3.3 (9 Sep 2009)
     */
    public static enum SvgParam {
        processId,

        iconsFolder,

        javaScriptFile,

        cssFile,

        onload,

        onunload,

        onmouseover,

        onmouseout,

        onclick,

        onmousedown,

        onmouseup,

        onkeypress,

        onkeydown,

        onkeyup,

        gradientOrLighting,

        scale,

        noScaleFont,

        fontPtHeight,

        fixedEventSize,

        fixedGatewayWidth,

        fixedGatewayHeight,

        connectionDecoratorSize,

        standardArtifactFillColor,

        standardArtifactBorderColor,

        standardArtifactTextColor,

        standardActivityFillColor,

        standardActivityBorderColor,

        standardActivityTextColor,

        standardConnectionColor,

        graphicsInfoToolId,

        connectorGraphicsInfoToolId,

    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.importexport.utils.ImportExportTransformer.
     * ITransformationCharsetEncodingProvider
     * #getCharsetEncodingForStream(com.tibco
     * .xpd.ui.importexport.utils.ImportExportTransformer
     * .ITransformationCharsetEncodingProvider.EncodingForStream)
     */
    public String getCharsetEncodingForStream(
            EncodingForStream encodingForStream) {
        return "UTF-8"; //$NON-NLS-1$
    }
}
