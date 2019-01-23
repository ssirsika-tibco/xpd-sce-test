/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.importexport.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationCharsetEncodingProvider.EncodingForStream;

/**
 * Static Transformation engine that is used by the import and export wizards to
 * transform the input stream using the xslts provided. This engine supports
 * multiple xslts (chaining).
 * <p>
 * Call
 * <code>{@link #transform(ITransformationStylesheetsProvider, InputStream, OutputStream)}</code>
 * to transform the inputstream and set the result in the outputstream -
 * recommended for single file transformation only. For multi-file
 * transformation create an instance of this class and call
 * {@link #performTransformation(InputStream, OutputStream)
 * performTransformation} for each file to transform.
 * </p>
 * 
 * @author njpatel
 */
public class ImportExportTransformer {

    /**
     * This interface should be implemented by the class that will provide the
     * xslts to the <code>ImportExportTransformer</code>.
     * <p>
     * This interface has been superseded by
     * {@link ITransformationStylesheetsProvider2} that has an extra
     * getImportXsltURL() method to allow control provision of correct URL's for
     * imported xslts (via xsl:import / xsl:include statements in the main
     * xslt).
     * </p>
     */
    public interface ITransformationStylesheetsProvider {
        /**
         * Get a list of XSLTs to apply for transformation
         * 
         * @return Array of xslt location URLs
         */
        public URL[] getXslts();

        /**
         * Set the system identifier for the input source being transformed.
         * This will be used in resolving relative URL references in the input
         * source. See <code>InputSource</code>'s <code>setSystemId</code> and
         * <code>getSystemId</code> methods for more details.
         * 
         * @see InputSource#setSystemId(String)
         * 
         * @return
         */
        public String getSystemId();

        /**
         * Return a map of parameter name -> parameter values.
         * <p>
         * These will be passed to each transformation xslt.
         * 
         * @return null or map of param name to param value.
         */
        public Map<String, String> getXsltParameters();
    }

    /**
     * ITransformationStylesheetsProvider2
     * <p>
     * This interface supersedes {@link ITransformationStylesheetsProvider} and
     * has an extra getImportXsltURL() method to allow control provision of
     * correct URL's for imported xslts (via xsl:import / xsl:include statements
     * in the main xslt).
     * </p>
     * <p>
     * It also has provision for the xslts provider to supply a
     * message.properties URL to help in localisation of exports.
     * </p>
     */
    public interface ITransformationStylesheetsProvider2 extends
            ITransformationStylesheetsProvider {

        /**
         * The name of xslt parameter passed to the provider's xslt if it
         * returns a message.properties url for the given xslt. The value of
         * this parameter can be used to construct an instance of
         * ImportExportMessageProperties class via which it can then access
         * messages defined in the message.properties file.
         */
        public static final String MSGPROPS_ID_XSLT_PARAMNAME =
                "messagePropertiesId"; //$NON-NLS-1$

        /**
         * Given the href part of an xsl:import/include statement found in the
         * main transformation xslt, return a URL for it so that it can be
         * properly located.
         * 
         * @param href
         * @return
         */
        public URL getImportXsltURL(String href);

        /**
         * Given the URL of one of the xslts returned by
         * {@link ITransformationStylesheetsProvider#getXslts()} return the URL
         * to a message.properties file (if any).
         * <p>
         * If a message.properties URL is returned then the given XSLT will be
         * passed an extra xslt parameter variable called 'messagePropertiesId'.
         * <p>
         * The value of this parameter can be used to construct an instance of
         * ImportExportMessageProperties class via which it can then access
         * messages defined in the message.properties file.
         * <p>
         * See {@link ImportExportMessageProperties} for more details.
         * </p>
         * 
         * @param xsltURL
         * @return
         */
        public URL getMessagePropertiesURL(URL xsltURL);

    }

    /**
     * ITransformationStylesheetsProvider3
     * <p>
     * This interface has a method getCharsetEncoding() method to allow control
     * provision of correct charactor encoding for future transformation
     * purposes.
     * 
     * @deprecated Implement ITransformationCharsetEncodingProvider instead as
     *             this will tell you which of the streams it is asking for the
     *             encoding for.,
     */
    @Deprecated
    public interface ITransformationStylesheetsProvider3 {
        public String getCharsetEncoding();
    }

    /**
     * ITransformationCharsetEncodingProvider
     * <p>
     * This interface has a method getInputCharsetEncoding() method to allow
     * control provision of correct charactor encoding for future transformation
     * purposes.
     * <p>
     * And also a getOutputCharsetEncoding().
     * <p>
     * <b>NOTE: this interface will be used in preference to
     * ITransformationStylesheetsProvider3.getChrsetEncoding()
     * 
     * @since 3.2
     */
    public interface ITransformationCharsetEncodingProvider {
        public enum EncodingForStream {
            XSLT, INPUT, OUTPUT
        }

        public String getCharsetEncodingForStream(
                EncodingForStream encodingForStream);
    }

    /**
     * ITransformProgressMonitorSupport
     * <p>
     * Abstract stylesheets provider class to bring together the 3 previous
     * interface version.
     * <p>
     * The subclass will be expected to implement all the original interface
     * methods from ITransformationStylesheetsProvider, then this abstract class
     * will provide default (null) implementations for the other interface
     * methods.
     * 
     * @author aallway
     */
    public interface ITransformProgressMonitorSupport {

        /**
         * The number of expected subtasks for the progress monitor for the
         * given xslt, these will be used to update the progress monitor (the
         * xslt can used the transformMonitor xslt parameter (which is an object
         * of class TransformMonitor) to 'tick' subtasks off the progressMonitor
         * using monitorStartSubTask() and monitorSubTaskDone.
         * <p>
         * By default there will be 1 task per xslt. The subclass can implement
         * this method in order to provide extra progress granularity.
         * 
         * @return The number of expected subtasks for the progress monitor for
         *         the given xslt.
         */
        int getMonitorSubTaskCount();

        /**
         * @return A leader string for the sub-tasks of the transform or null if
         *         not required.
         */
        String getSubTaskLeader();

    }

    /**
     * ImportExportTransformerXsltURIResolver
     * <p>
     * URI resolver for the sax transformer factory.
     * <p>
     * Used in the resolution of <xsl:import href="fred"/> xslt imports, it
     * delegates the responsibility of converting the href in xslt into an
     * actual file resource URL to use at runtime.
     * <p>
     * This URIResolver is used when the importexmport xslts provider supports
     * {@link ITransformationStylesheetsProvider2} in which case, we delegate
     * responsibility of creating a URL from an xsl:import href to the xslts
     * provider.
     * </p>
     */
    private static class ImportExportTransformerXsltURIResolver implements
            URIResolver {
        private ITransformationStylesheetsProvider2 xsltsProvider2;

        public ImportExportTransformerXsltURIResolver(
                ITransformationStylesheetsProvider2 xsltsProvider2) {
            super();
            this.xsltsProvider2 = xsltsProvider2;
        }

        @Override
        public Source resolve(String href, String base)
                throws TransformerException {

            URL importXsltUrl = xsltsProvider2.getImportXsltURL(href);
            if (importXsltUrl != null) {

                try {
                    InputStream importXsltStream = importXsltUrl.openStream();

                    if (importXsltStream != null) {
                        Reader reader =
                                getInputStreamReader(xsltsProvider2,
                                        importXsltStream,
                                        EncodingForStream.XSLT);
                        return new StreamSource(reader);
                    }

                } catch (IOException e) {
                    throw new TransformerException(e);
                }

            }

            return null;
        }

    }

    /**
     * TransformMonitor
     * <p>
     * Passed to transform xslt's to allow them to participate in progesss
     * monitoring.
     * 
     * @author aallway
     */
    private class TransformMonitor {
        private IProgressMonitor monitor;

        private String leader;

        public TransformMonitor(IProgressMonitor monitor, String leader) {
            this.monitor = monitor;
            this.leader = leader;
        }

        public String monitorStartSubTask(String subTaskName)
                throws OperationCanceledException {
            if (monitor != null) {
                if (monitor.isCanceled()) {
                    throw new OperationCanceledException();
                }

                if (leader != null && leader.length() > 0) {
                    monitor.subTask(leader + " - " + subTaskName); //$NON-NLS-1$
                } else {
                    monitor.subTask(subTaskName);
                }
            }
            return ""; //$NON-NLS-1$
        }

        public String monitorSubTaskDone() throws OperationCanceledException {
            if (monitor != null) {
                if (monitor.isCanceled()) {
                    throw new OperationCanceledException();
                }

                monitor.worked(1);
            }
            return ""; //$NON-NLS-1$
        }

    }

    /**
     * Can't pass TransformMonitor instance direct to xslt as a param so it has
     * to access it via a unique id that we pass out to it as transformMonitorId
     */
    private static Map<String, TransformMonitor> transformMonitorCache =
            new HashMap<String, TransformMonitor>();

    /**
     * Name of the xslt parameter for the TransformMonitor created for each
     * transform. Use this with the methods.. monitorStartSubTask() and
     * monitorSubTaskDone(). The xslts provider must implement
     * {@link ITransformProgressMonitorSupport}
     */
    private static final String TRANSFORM_MONITOR_PARAM_NAME =
            "transformMonitorId";

    public static String monitorStartSubTask(String transformMonitorId,
            String subTaskName) throws OperationCanceledException {
        TransformMonitor tm = transformMonitorCache.get(transformMonitorId);
        if (tm != null) {
            tm.monitorStartSubTask(subTaskName);
        }
        return ""; //$NON-NLS-1$
    }

    public static String monitorSubTaskDone(String transformMonitorId)
            throws OperationCanceledException {
        TransformMonitor tm = transformMonitorCache.get(transformMonitorId);
        if (tm != null) {
            tm.monitorSubTaskDone();
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * Owner's Class responsible for providing details of the xslt's to run etc
     */
    private final ITransformationStylesheetsProvider xsltsProvider;

    private XMLReader xmlReader;

    private boolean initComplete = false;

    private final TransformerErrorListener errorListener;

    private Map<Templates, URL> templatesUrlMap;

    private List<Templates> templates;

    private SAXTransformerFactory transformerFactory;

    /**
     * The caller can request cached info about the import/export xslts.
     * <p>
     * This can then be used to construct subsequent import export transformers
     * without recompiling all the import export transformers.
     * 
     * @author aallway
     * @since 3.2
     */
    public static class ImportExportCachedInfo {
        private Map<Templates, URL> templatesUrlMap;

        private List<Templates> templates;

        private SAXTransformerFactory transformerFactory;

        private TransformerErrorListener errorListener;

        private XMLReader xmlReader;
    }

    /**
     * Returns an input stream reader and encodes it in either the system
     * default charset or the providers default charset if defined.
     * 
     * @param tempTransProvider
     * @param inStream
     * 
     * @return reader
     */
    public static Reader getInputStreamReader(
            ITransformationStylesheetsProvider tempTransProvider,
            InputStream inStream) {
        return getInputStreamReader(tempTransProvider,
                inStream,
                EncodingForStream.INPUT);
    }

    /**
     * Returns an input stream reader and encodes it in either the system
     * default charset or the providers default charset if defined.
     * 
     * @param tempTransProvider
     * @param inStream
     * @param encodingForStream
     * 
     * @return reader
     * @since 3.2
     */
    public static Reader getInputStreamReader(
            ITransformationStylesheetsProvider tempTransProvider,
            InputStream inStream, EncodingForStream encodingForStream) {
        String charSetEncoding = null;

        if (tempTransProvider instanceof ITransformationCharsetEncodingProvider) {
            // If possible use the interface that allows separate input and
            // output encoding.
            charSetEncoding =
                    ((ITransformationCharsetEncodingProvider) tempTransProvider)
                            .getCharsetEncodingForStream(encodingForStream);

        } else if (tempTransProvider instanceof ITransformationStylesheetsProvider3) {
            charSetEncoding =
                    ((ITransformationStylesheetsProvider3) tempTransProvider)
                            .getCharsetEncoding();
        }
        BufferedReader bufferedReader = null;

        if (charSetEncoding == null) {
            InputStreamReader reader = new InputStreamReader(inStream);
            bufferedReader = new BufferedReader(reader);
        } else {
            InputStreamReader reader =
                    new InputStreamReader(inStream,
                            Charset.forName(charSetEncoding));
            bufferedReader = new BufferedReader(reader);
        }
        return bufferedReader;
    }

    /**
     * Returns an output stream writer and encodes it in either the system
     * default charset or the providers default charset if defined.
     * 
     * @param tempTransProvider
     * @param outStream
     * @return
     */
    public static Writer getOutputStreamWriter(
            ITransformationStylesheetsProvider tempTransProvider,
            OutputStream outStream) {
        BufferedWriter bufWriter = null;
        String charSetEncoding = null;
        if (tempTransProvider instanceof ITransformationCharsetEncodingProvider) {
            // If possible use the interface that allows separate input and
            // output encoding.
            charSetEncoding =
                    ((ITransformationCharsetEncodingProvider) tempTransProvider)
                            .getCharsetEncodingForStream(EncodingForStream.OUTPUT);

        } else if (tempTransProvider instanceof ITransformationStylesheetsProvider3) {
            charSetEncoding =
                    ((ITransformationStylesheetsProvider3) tempTransProvider)
                            .getCharsetEncoding();
        }

        if (charSetEncoding == null) {
            OutputStreamWriter writer = new OutputStreamWriter(outStream);
            bufWriter = new BufferedWriter(writer);
        } else {
            OutputStreamWriter writer =
                    new OutputStreamWriter(outStream,
                            Charset.forName(charSetEncoding));
            bufWriter = new BufferedWriter(writer);
        }
        return bufWriter;
    }

    /**
     * Transform the <i>inStream</i> with the transformation stylesheet provided
     * by the <i>provider</i>. The result of the transformation will be placed
     * in the <i>outStream</i>.
     * <p>
     * <b>NOTE:</b> If multiple files are going to be transformed then this
     * method is not recommended. Instead, create an instance of this class and
     * call {@link #performTransformation(InputStream, OutputStream)
     * performTransformation} on each file to transform.
     * </p>
     * 
     * @param provider
     *            stylesheet provider
     * @param inStream
     *            input stream to transform
     * @param outStream
     *            result output stream
     * @throws TransformerConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static void transform(ITransformationStylesheetsProvider provider,
            InputStream inStream, OutputStream outStream)
            throws TransformerConfigurationException, IOException, SAXException {

        ImportExportTransformer transformer =
                new ImportExportTransformer(provider);

        transformer.performTransformation(inStream, outStream);
    }

    /**
     * Constructor. The transformation engine will be initialised using the
     * XSLTs provided by the transformation stylesheet provider. If this fails
     * then method
     * <code>{@link #performTransformation(InputStream, OutputStream)}</code>
     * will do nothing.
     * 
     * @param provider
     *            stylesheet provider (can also optionally implement
     *            ITransformationStylesheetsProvider2,
     *            ITransformationStylesheetsProvider3,
     *            ITransformationStylesheetsProvider4 and
     *            ITransformProgressMonitorSupport. See these interfaces for
     *            more details.
     * @throws IOException
     * @throws TransformerConfigurationException
     * @throws SAXException
     */
    public ImportExportTransformer(ITransformationStylesheetsProvider provider)
            throws TransformerConfigurationException, IOException, SAXException {
        this.xsltsProvider = provider;

        errorListener = new TransformerErrorListener();
        transformerFactory = createTransformerFactory(errorListener);

        if (transformerFactory != null) {
            setXMLReader();

            try {
                templates = createTemplates(transformerFactory, xsltsProvider);

                // Indicate that the initialisation of this transformation
                // engine is complete
                initComplete = true;

            } catch (TransformerConfigurationException e) {
                // Return the cause of the transformation error if possible
                List<IStatus> errorLog = errorListener.getErrorLog();
                if (!errorLog.isEmpty()) {
                    throw new TransformerConfigurationException(
                            e.getLocalizedMessage(), e.getLocator(),
                            new CoreException(new MultiStatus(
                                    XpdResourcesUIActivator.ID, IStatus.ERROR,
                                    errorLog.toArray(new IStatus[errorLog
                                            .size()]), e.getLocalizedMessage(),
                                    e)));
                } else {
                    throw e;
                }
            }
        } else {
            throw new NullPointerException(
                    "Failed to create SAXTransformerFactory."); //$NON-NLS-1$
        }
    }

    /**
     * @throws SAXException
     */
    private void setXMLReader() throws SAXException {

        try {
            xmlReader = XMLReaderFactory.createXMLReader();
        } catch (SAXException e) {
            // If unable to create an instance, let's try to use
            // the XMLReader from JAXP
            if (xmlReader == null) {
                SAXParserFactory spf = SAXParserFactory.newInstance();
                spf.setNamespaceAware(true);
                try {
                    xmlReader = spf.newSAXParser().getXMLReader();
                } catch (ParserConfigurationException e1) {
                    XpdResourcesUIActivator.getDefault().getLogger().error(e);
                }
            }
            XpdResourcesUIActivator.getDefault().getLogger().error(e);
        }
    }

    /**
     * COnstruct an import export transformer using the previously cached and
     * compiled information from a previous ImportExportTransformer instance.
     * 
     * @param provider
     * @param cachedInfo
     * @throws TransformerConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public ImportExportTransformer(ITransformationStylesheetsProvider provider,
            ImportExportCachedInfo cachedInfo)
            throws TransformerConfigurationException, IOException, SAXException {
        this.xsltsProvider = provider;
        this.errorListener = cachedInfo.errorListener;
        this.templates = cachedInfo.templates;
        this.templatesUrlMap = cachedInfo.templatesUrlMap;
        this.transformerFactory = cachedInfo.transformerFactory;
        this.xmlReader = cachedInfo.xmlReader;
        this.initComplete = true;
    }

    /**
     * The input stream will be transformed using the transform handlers created
     * in the constructor to the output stream
     * 
     * @param inStream
     *            input stream to transform
     * @param outStream
     *            result of the transformation
     * @throws SAXException
     * @throws IOException
     * @throws TransformerConfigurationException
     */
    public void performTransformation(InputStream inStream,
            OutputStream outStream) throws IOException, SAXException,
            TransformerConfigurationException {
        performTransformation(new NullProgressMonitor(), inStream, outStream);
        return;
    }

    /**
     * The input stream will be transformed using the transform handlers created
     * in the constructor to the output stream
     * 
     * @param monitor
     *            progress monitor
     * @param inStream
     *            input stream to transform
     * @param outStream
     *            result of the transformation
     * @throws SAXException
     * @throws IOException
     * @throws TransformerConfigurationException
     */
    public void performTransformation(IProgressMonitor monitor,
            InputStream inStream, OutputStream outStream) throws IOException,
            SAXException, TransformerConfigurationException {

        if (initComplete && inStream != null && outStream != null) {
            Reader reader =
                    getInputStreamReader(xsltsProvider,
                            inStream,
                            EncodingForStream.INPUT);
            Writer writer = getOutputStreamWriter(xsltsProvider, outStream);
            InputSource source = new InputSource(reader);
            if (xsltsProvider.getSystemId() != null) {
                source.setSystemId(xsltsProvider.getSystemId());
            }

            Map<String, String> xsltParams = xsltsProvider.getXsltParameters();

            //
            // Add a TransformMonitor to the list of params.
            // The xslt can use the transformMonitorId param to access the
            // monitorSubTaskName/SubTaskDone static methods.
            //
            String transformMonitorId = null;
            if (monitor != null) {
                if (xsltsProvider instanceof ITransformProgressMonitorSupport) {
                    ITransformProgressMonitorSupport prov =
                            (ITransformProgressMonitorSupport) xsltsProvider;

                    TransformMonitor transformMonitor =
                            new TransformMonitor(monitor,
                                    prov.getSubTaskLeader());

                    transformMonitorId =
                            "TransformMonitor." + transformMonitor.hashCode(); //$NON-NLS-1$
                    transformMonitorCache.put(transformMonitorId,
                            transformMonitor);

                    xsltParams.put(TRANSFORM_MONITOR_PARAM_NAME,
                            transformMonitorId);

                    int numSubTasks = prov.getMonitorSubTaskCount();
                    if (numSubTasks < 1) {
                        numSubTasks = 1;
                    }
                    monitor.beginTask("", numSubTasks);
                }
            }

            Vector<TransformerHandler> vHandlers =
                    createTransformerHandlers(transformerFactory,
                            templates,
                            xsltParams);

            if (vHandlers != null) {
                try {
                    /*
                     * Load the xml reader with the transformation. Set the
                     * first transformer handler as the content of this reader.
                     * The result of the last transformer handler in the chain
                     * will be set to the output stream of the new resource
                     * being created.
                     */
                    xmlReader.setContentHandler(vHandlers.get(0));

                    /*
                     * Set the result of the last transformer handler to the
                     * output file
                     */
                    vHandlers.lastElement().setResult(new StreamResult(writer));

                    // Transform the input source - the output of the
                    // transformation will be put into the output stream
                    //xmlReader.setFeature("http://apache.org/xml/features/allow-java-encodings", true); //$NON-NLS-1$
                    xmlReader.parse(source);

                } finally {
                    reader.close();
                    writer.close();
                }

                vHandlers.clear();
                vHandlers = null;
            }

            if (transformMonitorId != null) {
                transformMonitorCache.remove(transformMonitorId);
            }
        }

        if (monitor != null) {
            monitor.done();
        }

    }

    /**
     * Create the SAX transformer factory.
     * 
     * @param listener
     *            error listener to register with the factory.
     * @return {@link SAXTransformerFactory} or <code>null</code> if the
     *         transformer factory does not support SAX feature.
     */
    private SAXTransformerFactory createTransformerFactory(
            ErrorListener listener) {
        SAXTransformerFactory saxTFactory = null;

        TransformerFactory tFactory = TransformerFactory.newInstance();

        // Determine whether the transformer factory supports the use of
        // SAXSource and SAXResult
        if (tFactory.getFeature(SAXSource.FEATURE)
                && tFactory.getFeature(SAXResult.FEATURE)) {
            // Cast the transformer factory to the SAX transformer
            // factory
            saxTFactory = (SAXTransformerFactory) tFactory;

            if (listener != null) {
                // Register error listener
                saxTFactory.setErrorListener(listener);
            }
            /*
             * If the xsltsProvider supports the extended interface that can
             * help resolve xsl:imports then setup a URI resolver to delegate
             * "import href resolution to URL" to the xslts provider.
             */
            if (xsltsProvider instanceof ITransformationStylesheetsProvider2) {

                // This version can handle the resolution of hrefs to an
                // actual URL.
                ITransformationStylesheetsProvider2 xslProv2 =
                        (ITransformationStylesheetsProvider2) xsltsProvider;

                ImportExportTransformerXsltURIResolver uriResolver =
                        new ImportExportTransformerXsltURIResolver(xslProv2);

                saxTFactory.setURIResolver(uriResolver);
            }
        }

        return saxTFactory;
    }

    /**
     * Pre-compile the xslts supplied by the provider.
     * 
     * @param factory
     *            transformer factory
     * @param provider
     *            xslt provider
     * @return list of {@link Templates} for each xslt provided.
     * @throws IOException
     * @throws TransformerConfigurationException
     */
    private List<Templates> createTemplates(SAXTransformerFactory factory,
            ITransformationStylesheetsProvider provider) throws IOException,
            TransformerConfigurationException {
        List<Templates> templates = new ArrayList<Templates>();
        templatesUrlMap = new HashMap<Templates, URL>();

        URL[] xsltUrls = provider.getXslts();

        if (factory != null && xsltUrls != null) {
            for (URL url : xsltUrls) {
                InputStream xsltStream = url.openStream();

                try {
                    if (xsltStream != null) {
                        Reader reader =
                                getInputStreamReader(provider,
                                        xsltStream,
                                        EncodingForStream.XSLT);
                        Templates template =
                                factory.newTemplates(new StreamSource(reader));
                        templates.add(template);
                        templatesUrlMap.put(template, url);
                    }
                } finally {
                    xsltStream.close();
                }
            }
        }

        return templates;
    }

    /**
     * Initialise the transformation stylesheets provided by the resource
     * handler and create a list of transformer handlers.
     * 
     * @param monitor
     * @return Vector of <code>TransformerHandler</code> objects for each
     *         stylesheet being applied to the transformation. If no stylesheets
     *         are defined then <b>null</b> will be returned.
     * @throws IOException
     * @throws TransformerConfigurationException
     * @throws Exception
     */
    private Vector<TransformerHandler> createTransformerHandlers(
            SAXTransformerFactory saxTFactory, Collection<Templates> templates,
            Map<String, String> xsltParams) throws IOException,
            TransformerConfigurationException {

        Vector<TransformerHandler> vHandlers = null;
        // Clear the error log
        errorListener.clearErrorLog();

        if (saxTFactory != null && templates != null && !templates.isEmpty()) {
            vHandlers = new Vector<TransformerHandler>();

            try {
                for (Templates template : templates) {
                    // Create the transformer handler for the given template
                    TransformerHandler handler =
                            saxTFactory.newTransformerHandler(template);

                    if (handler != null) {
                        /*
                         * SID: DO NOT use our own URI Resolver for actually
                         * running the xslt (only for loading it to resolve
                         * imports).
                         * 
                         * I initially tried doing so BUT when there was an
                         * <xsl:document statement in the xslt things went
                         * horribly wrong. When our URI resolver (correctly I
                         * think) returned a StreamSource object for the
                         * xsl:document identified file, then it worked BUT it
                         * seemed to replace the 'current input context' for the
                         * xslt and hence everything after the xsl:document
                         * statement had lost it's concept of the current
                         * element in the main document.
                         */
                        handler.getTransformer().setURIResolver(null);

                        // Add any parameters to the xslt transformer
                        // that
                        // the provider wants to pass into xslt.
                        if (xsltParams != null) {
                            for (Iterator<Entry<String, String>> iter =
                                    xsltParams.entrySet().iterator(); iter
                                    .hasNext();) {
                                Entry<String, String> param = iter.next();

                                handler.getTransformer()
                                        .setParameter(param.getKey(),
                                                param.getValue());
                            }
                        }

                        /*
                         * If the xslts provider wants to provide us with a
                         * message.properties URL for this xslt file then set up
                         * cache of message properties and pass an Id that it
                         * can use to construct an instanceof
                         * ImportExportMessageProperties class via which it can
                         * access messages from its message .properties file.
                         */
                        String msgPropsId =
                                "<No message.properties URL provided.>"; //$NON-NLS-1$
                        if (xsltsProvider instanceof ITransformationStylesheetsProvider2) {
                            URL url =
                                    templatesUrlMap != null ? templatesUrlMap
                                            .get(template) : null;

                            if (url != null) {
                                URL msgURL =
                                        ((ITransformationStylesheetsProvider2) xsltsProvider)
                                                .getMessagePropertiesURL(url);
                                if (msgURL != null) {
                                    msgPropsId =
                                            ImportExportMessageProperties
                                                    .getMessagePropertiesId(msgURL);
                                    if (msgPropsId == null) {
                                        msgPropsId =
                                                "<Load properties from provided URL failed (" + msgURL.getPath() + ")>"; //$NON-NLS-1$ //$NON-NLS-2$
                                    }
                                }
                            }
                        }

                        handler.getTransformer()
                                .setParameter(ITransformationStylesheetsProvider2.MSGPROPS_ID_XSLT_PARAMNAME,
                                        msgPropsId);

                        /*
                         * If a transformer handler has already been added to
                         * the list then make this transformer handler the
                         * result of the last one (to chain it)
                         */
                        if (vHandlers.size() > 0) {
                            TransformerHandler tPrevHandler =
                                    vHandlers.lastElement();
                            tPrevHandler.setResult(new SAXResult(handler));
                        }

                        // add this transformer to the list
                        vHandlers.add(handler);
                    }
                }
            } catch (TransformerConfigurationException e) {
                // Return the cause of the transformation error if possible
                List<IStatus> errorLog = errorListener.getErrorLog();
                if (!errorLog.isEmpty()) {
                    throw new TransformerConfigurationException(
                            e.getLocalizedMessage(), e.getLocator(),
                            new CoreException(new MultiStatus(
                                    XpdResourcesUIActivator.ID, IStatus.ERROR,
                                    errorLog.toArray(new IStatus[errorLog
                                            .size()]), e.getLocalizedMessage(),
                                    e)));
                } else {
                    throw e;
                }
            }

        }

        return vHandlers;
    }

    /**
     * Transformer error listener that will log all errors/warnings.
     * 
     * @author njpatel
     * 
     * @since 3.1
     */
    private class TransformerErrorListener implements ErrorListener {

        /** Logs the transformer errors/warnings */
        private final List<IStatus> errorLog;

        public TransformerErrorListener() {
            errorLog = new ArrayList<IStatus>();
        }

        /**
         * Get the transformer error log.
         * 
         * @return list of IStatus error/warnings, empty list if no
         *         errors/warnings reported by the transformer.
         */
        public List<IStatus> getErrorLog() {
            return errorLog;
        }

        /**
         * Clear the error log.
         */
        public void clearErrorLog() {
            errorLog.clear();
        }

        /*
         * (non-Javadoc)
         * 
         * @seejavax.xml.transform.ErrorListener#error(javax.xml.transform.
         * TransformerException)
         */
        @Override
        public void error(TransformerException exception)
                throws TransformerException {
            String msg = exception.getLocalizedMessage();
            IStatus status;
            if (msg != null) {
                msg =
                        String.format(Messages.ImportExportTransformer_transformationError_pattern_message,
                                msg);
                status =
                        new Status(IStatus.ERROR, XpdResourcesUIActivator.ID,
                                msg, null);
            } else {
                msg =
                        Messages.ImportExportTransformer_transformationError_title;
                status =
                        new Status(IStatus.ERROR, XpdResourcesUIActivator.ID,
                                msg, exception);
            }
            XpdResourcesUIActivator.getDefault().getLogger()
                    .error(exception, msg);

            errorLog.add(status);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * javax.xml.transform.ErrorListener#fatalError(javax.xml.transform.
         * TransformerException)
         */
        @Override
        public void fatalError(TransformerException exception)
                throws TransformerException {
            String msg = exception.getLocalizedMessage();
            IStatus status;
            if (msg != null) {
                msg =
                        String.format(Messages.ImportExportTransformer_transformationFatalError_pattern_message,
                                msg);
                status =
                        new Status(IStatus.ERROR, XpdResourcesUIActivator.ID,
                                msg, null);
            } else {
                msg =
                        Messages.ImportExportTransformer_transformationFatalError_title;
                status =
                        new Status(IStatus.ERROR, XpdResourcesUIActivator.ID,
                                msg, exception);
            }
            XpdResourcesUIActivator.getDefault().getLogger()
                    .error(exception, msg);

            errorLog.add(status);
        }

        /*
         * (non-Javadoc)
         * 
         * @seejavax.xml.transform.ErrorListener#warning(javax.xml.transform.
         * TransformerException)
         */
        @Override
        public void warning(TransformerException exception)
                throws TransformerException {
            String msg = exception.getLocalizedMessage();
            IStatus status;

            if (msg != null) {
                msg =
                        String.format(Messages.ImportExportTransformer_transformationWarning_pattern_message,
                                msg);
                status =
                        new Status(IStatus.ERROR, XpdResourcesUIActivator.ID,
                                msg, null);
            } else {
                msg =
                        Messages.ImportExportTransformer_transformationWarning_title;
                status =
                        new Status(IStatus.ERROR, XpdResourcesUIActivator.ID,
                                msg, exception);
            }
            XpdResourcesUIActivator.getDefault().getLogger()
                    .warn(exception, msg);

            errorLog.add(status);
        }
    }

    /**
     * After construction - The caller can request cached info about the
     * import/export xslts.
     * <p>
     * This can then be used to construct subsequent import export transformers
     * without recompiling all the import export transformers.
     * 
     * @return The compiled info.
     */
    public ImportExportCachedInfo getImportExportCachedInfo() {
        ImportExportCachedInfo info = new ImportExportCachedInfo();
        info.templates = templates;
        info.templatesUrlMap = templatesUrlMap;
        info.transformerFactory = transformerFactory;
        info.errorListener = errorListener;
        info.xmlReader = xmlReader;
        return info;
    }

}
