/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.xpdl2.resources;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerService;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;
import com.tibco.xpd.xpdl2.edit.ui.internal.Messages;
import com.tibco.xpd.xpdl2.edit.util.PackageVersionProblemException;
import com.tibco.xpd.xpdl2.edit.util.XsltUtils;
import com.tibco.xpd.xpdl2.resources.XpdlMigrationInjectorHelper.MigrationCommandInjector;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * XML Parser
 * 
 * @author njpatel
 * 
 */
class ParseXML {

    private static DocumentBuilderFactory fact;

    private static DocumentBuilder docBuilder;

    private static boolean bFirstPass = true;

    public ParseXML() throws ParserConfigurationException {
        DoSetup();
    }

    /**
     * Parse the XML file and create Document
     * 
     * @param fileName
     * @return Document
     * @throws IOException
     * @throws SAXException
     */
    public Document parse(File file) throws SAXException, IOException {
        Document document = null;

        if (file != null) {
            /*
             * Originally the file was passed to the "parse" method below but if
             * there is an exception during the parsing it will leave a file
             * handle open, which means that the file cannot be deleted. Changed
             * this to passing input stream so that we can close even if there
             * is an error (XPD-4403).
             */
            FileInputStream inStream = new FileInputStream(file);
            try {
                document = docBuilder.parse(inStream);
            } catch (SAXException e) {
                throw new SAXException("Failed to parse: " + file.getAbsolutePath(), e); //$NON-NLS-1$
            } finally {
                inStream.close();
            }
        }

        return document;
    }

    /**
     * Setup the Document Factory and the Builder
     * 
     * @throws ParserConfigurationException
     */
    private static void DoSetup() throws ParserConfigurationException {
        if (bFirstPass) {
            bFirstPass = false;

            // Create DOM factory...
            fact = DocumentBuilderFactory.newInstance();

            // Create DOM builder...
            docBuilder = fact.newDocumentBuilder();
        }
    }
}

/**
 * 
 * If the given IResource is not of the latest format version it will be
 * migrated to the new version. Each step in format version can have a
 * transformation stylesheet which will be applied in sequence to this IResource
 * to update it.
 * <p>
 * Static method <code>migrate</code> of this class can be used for the
 * migration of a single resource.
 * </p>
 * 
 * @author njpatel
 * 
 */
public class XpdlMigrate {

    /**
     * The name of the xpdl2:ExtendedAttribute in which the current
     * FormatVersion of the file is stored.
     */
    public static final String FORMAT_VERSION_ATT_NAME = "FormatVersion"; //$NON-NLS-1$

    /**
     * The name of the xpdl2:ExtendedAttribute in which the Original
     * FormatVersion of the file is stored (i.e .the current format version at
     * the time the xpdl file was created.
     * <p>
     * As this feature was only introduced in Studio v3.5.20 (FormatVersion=14),
     * this version will be 13 or higher (i.e. prior to 3.5.20 original was not
     * stored so can preserve the FormatVersion from a xpdl migrated from
     * FormatVersion=13).
     */
    public static final String ORIGINAL_FORMAT_VERSION_ATT_NAME =
            "OriginalFormatVersion"; //$NON-NLS-1$

    /**
     * The Format version is our own version that increments whenever a major
     * change the model/content of model is made. It is independent of the xpdl
     * model schema version and business studio version.
     * <p>
     * THIS IS THE ONE AND ONLY PLACE THAT VALUE OF THE "FormatVersion" extended
     * attribute should be defined.
     * <p>
     * This should then be utilised by all code that creates/assigns the version
     * (such as package generation from template code).
     * <p>
     * <b>Previous Format versions</b>
     * <li>4 - Studio version 3.0</li>
     * <li>6 - Studio version 3.1 (format version 5 was an unpublished
     * intermediate format)</li>
     * <li>7 - Studio version 3.2</li>
     * <li>8 - Studio version 3.3.0 (alpha 2)</li>
     * <li>9 - Studio version 3.5.0 (V16 build 27/01/2011)</li>
     * <li>10 - Studio version 3.5.2 (V8 build 15/07/2011-ish)</li>
     * <li>11 - Studio version 3.5.3 (V18 build)</li>
     * <li>12 - Studio version 3.5.5</li>
     * <li>13 - Studio version 3.5.10 (V05)</li>
     * <li>14 - Studio version 3.5.20</li>
     * <li>15 - Studio version 3.6.0</li>
     * <li>15 - Studio version 3.7.0 (@ V02)</li>
     * <li>16 - Studio version 3.8.0</li>
     * <li>17 - Studio version 3.9.0</li>
     * <li>18 - Studio version 4.0.0 (V02)</li>
     * <li>19 - Studio version 4.1.0 (V12)</li>
     * <li>20 - Studio version 4.2.0 (V03)</li>
     * <li>21 - Studio version 4.3.0 (V01)</li>
     * <li>22 - Studio version 4.3.0 (V09)</li>
     * ==============================================
     * <li>1000 - Studio Container Edition 5.0.0 (V95) (marks the transition
     * between BPM and SCE Studio - and leaves a gap between this and AMX BPM -
     * therefore future AMX BPM releases with incremented formatversion numbers
     * will still migrate to ACE).</i>
     * 
     * Note that this is defined as "nn".toString() to prevent Java compiler
     * statically compiling the value into referencing code (otherwise
     * referencing code will use old version until it is recompiled, and if its
     * in a different feature (like decisions add-in) then that won't always
     * happen.
     */
    public static final String FORMAT_VERSION_ATT_VALUE = "1000".toString(); //$NON-NLS-1$

    /*
     * Sid ACE-1592 DO NOT CHANGE - this is the initial formatVersion for all
     * ACE XPDL's This can be used to control migrations that must run only once
     * when migrating from AMX BPM projects
     */
    public static final int ACE_INITIAL_FORMAT_VERSION = 1000;

    private static final int nCurrentFormatVersion = Integer.parseInt(FORMAT_VERSION_ATT_VALUE);

    private static String XPDL_V1_URI = "http://www.wfmc.org/2002/XPDL1.0"; //$NON-NLS-1$

    /*
     * ------------------------------------------------- The migration xslt file
     * name prefix and extension
     * ------------------------------------------------- The filename convention
     * is as follows - for migration between: Format Version 0 and 1
     * "MigrateStudioXPDL_0.xslt" will be applied, Format Version 1 and 2
     * "MigrateStudioXPDL_1.xslt" will be applied, and so on... If there is no
     * need for transformation between two versions then a migration xslt for
     * that version should not be produced as the migration code below will skip
     * any version steps that there is no corresponding xslt for. If migrating
     * from Format version 0 to current version 4 (for example) then the xslts
     * for format version 0 to 1, 1 to 2, 2 to 3 and 3 to 4 will be applied in a
     * chain (assuming all format version steps have a corresponding xslt).
     */
    private static final String MIGRATION_XSLT_PREFIX =
            "/xslts/migration/MigrateStudioXPDL_"; //$NON-NLS-1$

    private static final String MIGRATION_XSLT_EXT = ".xslt"; //$NON-NLS-1$

    private SAXTransformerFactory saxTransformerFactory;

    private XMLReader xmlReader;

    /** Map of the xslt resource file to it's pre-compiled templates */
    private final Map<String, Templates> templatesMap;

    /**
     * Map of formatVersion to the extra xslts injected before standard one via
     * extension point.
     */
    private final Map<Integer, List<Templates>> templatesInjectedBefore;

    /**
     * Map of formatVersion to the extra xslts injected after standard one via
     * extension point.
     */
    private final Map<Integer, List<Templates>> templatesInjectedAfter;

    public XpdlMigrate() {
        // Instantiate a transformer factory
        TransformerFactory tFactory = TransformerFactory.newInstance();

        templatesMap = new HashMap<String, Templates>();

        templatesInjectedBefore = new HashMap<Integer, List<Templates>>();
        templatesInjectedAfter = new HashMap<Integer, List<Templates>>();

        // Determine whether the transformer factory supports the
        // use of
        // SAXSource and SAXResult
        if (tFactory.getFeature(SAXSource.FEATURE)
                && tFactory.getFeature(SAXResult.FEATURE)) {
            saxTransformerFactory = (SAXTransformerFactory) tFactory;
            tFactory.setErrorListener(new TransformerErrorListener());
            setXMLReader();
        } else {
            throw new NullPointerException(
                    "xpdlMigrate: Unable to create SAXTransformerFactory."); //$NON-NLS-1$
        }
    }

    private void setXMLReader() {
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
                } catch (SAXException e1) {
                    Xpdl2UiPlugin.getDefault().getLogger().error(e);
                } catch (ParserConfigurationException e1) {
                    Xpdl2UiPlugin.getDefault().getLogger().error(e);
                }
            }
        }
    }

    /**
     * Transform the input resource. The Format Version of the file will be
     * compared to the current Format Version of Studio. If the file is not of
     * the latest version it will be run through transformations to update it.
     * 
     * The given IResource will be updated with the migrated xpdl.
     * 
     * @param IResource
     * 
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws TransformerConfigurationException
     * @throws CoreException
     * @throws PackageVersionProblemException
     */
    @SuppressWarnings("restriction")
    public void transform(IResource inputResource)
            throws SAXException, IOException, ParserConfigurationException,
            TransformerConfigurationException, CoreException,
            PackageVersionProblemException {
        transform(inputResource, true);
    }

    /**
     * Transform the input resource. The Format Version of the file will be
     * compared to the current Format Version of Studio. If the file is not of
     * the latest version it will be run through transformations to update it.
     * 
     * The given IResource will be updated with the migrated xpdl.
     * <p>
     * The caller can choose only to perform the xslt part of the migration and
     * leave the post-migration command injection til later.
     * <p>
     * This is useful in situations where the file to be migrated cannot (for
     * some reason such as it doesn't have .xpdl extension) be loaded into a
     * working copy which is required if injected commands are to be executed. *
     * 
     * @param IResource
     * @param performPostMigrateCommandInjection
     * 
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws TransformerConfigurationException
     * @throws CoreException
     * @throws PackageVersionProblemException
     */
    @SuppressWarnings("restriction")
    public void transform(IResource inputResource,
            boolean performPostMigrateCommandInjection)
            throws SAXException, IOException, ParserConfigurationException,
            TransformerConfigurationException, CoreException,
            PackageVersionProblemException {
        // System.out.println("==> MIGRATE: " + inputResource.getName());
        // //$NON-NLS-1$
        if (saxTransformerFactory != null && xmlReader != null) {
            int formatVer = -1;
            Thread current = Thread.currentThread();
            ClassLoader oldLoader = current.getContextClassLoader();
            try {
                current.setContextClassLoader(getClass().getClassLoader());
                if (inputResource instanceof IFile) {
                    // Get the input resource
                    IFile inputFile = (IFile) inputResource;

                    // Get the format version of the file
                    formatVer = getFileFormatVersion(
                            inputFile.getLocation().toFile());

                    /*
                     * Migrate if a valid file version was returned and the file
                     * is not up-to-date
                     */
                    if (formatVer >= 0 && formatVer < nCurrentFormatVersion) {
                        // This vector will hold the transformer handlers
                        InputStreamReader reader = null;

                        Vector<TransformerHandler> handlers =
                                getTransformerHandlers(formatVer);

                        // If we have transformers then apply them
                        if (!handlers.isEmpty()) {
                            ByteArrayOutputStream oStream = null;
                            InputSource inputSource = null;

                            // Get the input source and open a reader to support
                            // non ascii chars also
                            FileInputStream inpStream = new FileInputStream(
                                    new File(inputFile.getLocationURI()));
                            reader = new InputStreamReader(inpStream,
                                    Charset.forName("UTF-8")); //$NON-NLS-1$

                            inputSource = new InputSource(reader);

                            // Optimised packages are set to read only, user has
                            // decided to migrate so override.
                            boolean isReadOnly = inputFile.isReadOnly();

                            // Catch and throw exceptions here (despite the
                            // function throwing exceptions) so that the
                            // IO stream can be closed in case there was an
                            // exception
                            try {
                                // Create the (byte array) output stream to
                                // store the transformed model
                                oStream = new ByteArrayOutputStream();
                                OutputStreamWriter writer =
                                        new OutputStreamWriter(oStream,
                                                Charset.forName("UTF-8")); //$NON-NLS-1$

                                // Set the content handler of the reader to the
                                // first transformer handler
                                xmlReader.setContentHandler(handlers.get(0));
                                // Set the result of the last transformer
                                // handler list to the output file stream
                                handlers.lastElement()
                                        .setResult(new StreamResult(writer));

                                // transform the file
                                xmlReader.parse(inputSource);

                                // The newly created outputstream needs to be
                                // fed back to the input resource to overwrite
                                // the old content
                                ByteArrayInputStream newStream =
                                        new ByteArrayInputStream(
                                                oStream.toByteArray());

                                if (isReadOnly) {
                                    inputFile.setReadOnly(false);
                                }

                                // Pretty sure that the oStream that we have
                                // created newStream from is already in UTF-8
                                // format because we used an OutputStreamWriter.
                                //
                                // If it does work then try uncmonneting reader3
                                // and using that for setContents instead.
                                // reader3 =
                                // new InputStreamReader(newStream,
                                // Charset.forName("UTF-8")); //$NON-NLS-1$

                                inputFile.setContents(newStream,
                                        IResource.FORCE,
                                        new NullProgressMonitor());

                                // Try to unload the resource to prevent using
                                // an old cached one
                                //
                                // USe wqorking copy's own forceCleanup() which
                                // is safer because it stops listening and
                                // doesn't cause deadlock issues with ui /
                                // progress thread.
                                WorkingCopy wc = XpdResourcesPlugin.getDefault()
                                        .getWorkingCopy(inputResource);
                                if (wc instanceof Xpdl2WorkingCopyImpl) {
                                    ((Xpdl2WorkingCopyImpl) wc).forceCleanup();
                                }

                                /*
                                 * After executing the migration execute any
                                 * command injectors.
                                 */
                                if (performPostMigrateCommandInjection) {
                                    executeEndOfMigrationCommands(
                                            inputResource, formatVer);
                                }

                                // Force reindexing of the file (only if this
                                // migration is not happening during import of a
                                // project).
                                if (wc != null
                                        && !XpdResourcesPlugin.getDefault()
                                                .isProjectsImportInProgress()) {
                                    IndexerService service = XpdResourcesPlugin
                                            .getDefault().getIndexerService();
                                    if (service instanceof IndexerServiceImpl) {
                                        ((IndexerServiceImpl) service)
                                                .index(wc);
                                    }
                                }

                            } catch (FileNotFoundException e) {
                                throw e;
                            } catch (SAXException e) {
                                throw e;
                            } catch (IOException e) {
                                throw e;
                            } finally {
                                // Dispose the static map
                                XsltUtils.dispose();
                                // Close the IO stream
                                try {
                                    if (oStream != null) {
                                        oStream.close();
                                        if (reader != null) {
                                            reader.close();
                                        }

                                        if (isReadOnly) {
                                            inputFile.setReadOnly(true);
                                        }
                                    }
                                } catch (IOException e) {
                                    // Do nothing
                                }
                            }
                        }
                    } else {
                        // If the file version returned was -1 or greater than
                        // the
                        // current version then the input file is invalid
                        if (formatVer < 0
                                || formatVer > nCurrentFormatVersion) {
                            throw new PackageVersionProblemException(
                                    Messages.XpdlMigrate_incorrectPAckageVersion_label
                                            + formatVer);
                        }
                    }
                }
            } finally {
                current.setContextClassLoader(oldLoader);
            }
        }

        // System.out.println("<== MIGRATE: " + inputResource.getName());
        // //$NON-NLS-1$

        return;
    }

    /**
     * After executing the migration execute any command injectors.
     * 
     * @param xpdlResource
     * @param originalFormatVersion
     *            format-version prior to the migration thru XSLTs
     */
    public static void executeEndOfMigrationCommands(IResource xpdlResource, int originalFormatVersion) {

        WorkingCopy wc =
                XpdResourcesPlugin.getDefault().getWorkingCopy(xpdlResource);

        if (wc instanceof Xpdl2WorkingCopyImpl
                && wc.getRootElement() instanceof Package) {
            Package pkg = (Package) wc.getRootElement();

            Collection<MigrationCommandInjector> endOfMigrationCommandInjectors =
                    XpdlMigrationInjectorHelper
                            .getEndOfMigrationCommandInjectors();

            boolean commandsExecuted = false;

            for (MigrationCommandInjector commandInjector : endOfMigrationCommandInjectors) {
                Command command =
                        commandInjector.getCommand(wc.getEditingDomain(), pkg, originalFormatVersion);

                try {
                    if (command != null) {
                        if (command.canExecute() && Xpdl2ModelUtil
                                .executeCommandOutsideTransaction(
                                        wc.getEditingDomain(),
                                        command)) {
                            commandsExecuted = true;

                        } else {
                            Xpdl2ResourcesPlugin.getDefault().getLogger().error(
                                    "End of migration contributed command failed (Contributor: " //$NON-NLS-1$
                                            + commandInjector.getContributorId()
                                            + ": " + commandInjector.getClass() //$NON-NLS-1$
                                                    .getCanonicalName()
                                            + ")."); //$NON-NLS-1$
                        }
                    }
                } catch (Exception e) {
                    Xpdl2ResourcesPlugin.getDefault().getLogger().error(e,
                            "End of migration contributed command failed (Contributor: " //$NON-NLS-1$
                                    + commandInjector.getContributorId() + ": " //$NON-NLS-1$
                                    + commandInjector.getClass()
                                            .getCanonicalName()
                                    + ")."); //$NON-NLS-1$
                }
            }

            if (commandsExecuted) {
                try {
                    wc.save();
                } catch (IOException e) {
                    Xpdl2ResourcesPlugin.getDefault().getLogger().error(e,
                            "Failed to save after end of migration command changes."); //$NON-NLS-1$
                }
            }
        }

        return;
    }

    /**
     * Get the transformer handlers for the migration of a file with the given
     * format version.
     * 
     * @param formatVersion
     *            model's current fragment version
     * @return vector of <code>TransformerHandler</code>s.
     * @throws TransformerConfigurationException
     * @throws IOException
     */
    private Vector<TransformerHandler> getTransformerHandlers(int formatVersion)
            throws TransformerConfigurationException, IOException {
        String migrateXSLT;
        Vector<TransformerHandler> handlers = new Vector<TransformerHandler>();
        /*
         * Create a transformer handler for each migration version stylesheet
         */
        for (int incrementingFormatVersion =
                formatVersion; incrementingFormatVersion < nCurrentFormatVersion; incrementingFormatVersion++) {

            /*
             * Include any xslt's injected into the migration sequence.
             * 
             * Note that we use "incrementingFormatVersion+1" because the
             * formatVersion comes in at "FormatVersion that xpdl current IS".
             * 
             * We are just about to run the
             * MigrateStudioXPDL_<currentFormatVersion>.xslt which will migrate
             * to incrementingFormatVersion+1 and so on (e.g.
             * MigrateStudioXPDL_7.xslt migrates from V7 to V8).
             * 
             * The MigrationINjector contribution contributes to
             * "before/after migrate to a particular version" So therefore
             * before running MigrateStudioXPDL_7.xslt we should inject the
             * contributions to "Before migration to V8" (hence
             * incrementingFormatVersion+1).
             */
            injectContrubutedMigrationXslts(incrementingFormatVersion + 1,
                    true,
                    handlers);

            // Generate the stylesheet file name for this
            // version
            migrateXSLT = MIGRATION_XSLT_PREFIX + incrementingFormatVersion
                    + MIGRATION_XSLT_EXT;
            Templates templates = templatesMap.get(migrateXSLT);

            if (templates == null) {
                // Create the templates
                InputStream xsltStream = null;
                try {
                    // Load the xslt resource
                    xsltStream = getClass().getResourceAsStream(migrateXSLT);
                    if (xsltStream != null) {
                        // Create templates
                        templates = saxTransformerFactory
                                .newTemplates(new StreamSource(xsltStream));
                        templatesMap.put(migrateXSLT, templates);
                    }
                } finally {
                    if (xsltStream != null) {
                        xsltStream.close();
                    }
                    xsltStream = null;
                }
            }

            /*
             * Add the handlers.
             */
            createAndChainXsltTemplateHandler(templates, handlers);

            /*
             * Include any xslt's injected into the migration sequence.
             * 
             * Note that we use "incrementingFormatVersion+1" because the
             * formatVersion comes in at "FormatVersion that xpdl current IS".
             * 
             * We have just run the
             * MigrateStudioXPDL_<currentFormatVersion>.xslt which will migrate
             * to incrementingFormatVersion+1 and so on (e.g.
             * MigrateStudioXPDL_7.xslt migrates from V7 to V8).
             * 
             * The MigrationInjector contribution contributes to
             * "before/after migrate to a particular version" So therefore after
             * running MigrateStudioXPDL_7.xslt we inject the contributions to
             * "After migration to V8" (hence incrementingFormatVersion+1).
             */
            injectContrubutedMigrationXslts(incrementingFormatVersion + 1,
                    false,
                    handlers);
        }
        return handlers;
    }

    /**
     * Load and inject the contributed migration xslts into the list of
     * transform handlers.
     * 
     * @param formatVersion
     * @param before
     * @param handlers
     * @throws TransformerConfigurationException
     * @throws IOException
     */
    private void injectContrubutedMigrationXslts(Integer formatVersion,
            boolean before, Vector<TransformerHandler> handlers)
            throws TransformerConfigurationException, IOException {
        /* See if we have the list of templates already cached. */
        List<Templates> templates;

        if (before) {
            templates = templatesInjectedBefore.get(formatVersion);

        } else {
            templates = templatesInjectedAfter.get(formatVersion);
        }

        if (templates == null) {
            /*
             * Get the xslts injected before/after this format version.
             */
            Collection<InputStream> injectedXslts = XpdlMigrationInjectorHelper
                    .getInjectedXslts(formatVersion, before);

            templates = new ArrayList<Templates>();

            for (InputStream xsltStream : injectedXslts) {
                Templates injectedTemplates;
                try {
                    injectedTemplates = saxTransformerFactory
                            .newTemplates(new StreamSource(xsltStream));
                    if (injectedTemplates != null) {
                        templates.add(injectedTemplates);
                    }
                } finally {
                    if (xsltStream != null) {
                        xsltStream.close();
                    }
                    xsltStream = null;
                }
            }

            /* Cache the templates. */
            if (before) {
                templatesInjectedBefore.put(formatVersion, templates);
            } else {
                templatesInjectedAfter.put(formatVersion, templates);
            }
        }

        /*
         * Then finally create and chain the transformers for the templates.
         */
        for (Templates template : templates) {
            createAndChainXsltTemplateHandler(template, handlers);
        }

        return;
    }

    /**
     * Create and add the transform handler for the given xslt template to the
     * chain of migration handlers.
     * 
     * @param templates
     * @param handlers
     * @throws TransformerConfigurationException
     */
    private void createAndChainXsltTemplateHandler(Templates templates,
            Vector<TransformerHandler> handlers)
            throws TransformerConfigurationException {
        if (templates != null) {
            TransformerHandler handler =
                    saxTransformerFactory.newTransformerHandler(templates);
            /*
             * If a transformer handler has already been added to the list then
             * make this transformer handler the result of the last one (to
             * chain it)
             */
            if (handlers.size() > 0) {
                TransformerHandler prevHandler = handlers.lastElement();
                prevHandler.setResult(new SAXResult(handler));
            }

            // add this transformer to the list
            handlers.add(handler);
        }
    }

    /**
     * Looks up the Format Version of the given file.<br>
     * 
     * @param inputFile
     * @return Format Version (integer)
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     */
    public static int getFileFormatVersion(File inputFile)
            throws SAXException, IOException, ParserConfigurationException {

        int nFormatVer = -1;
        ParseXML parser = new ParseXML();

        // An inputstream or inputsource could have been passed to the parser
        // but the parser will
        // close this stream once finished with it.
        Document doc = parser.parse(inputFile);

        if (doc != null) {

            // Find the package node.
            Node xpdlPackage = null;

            NodeList nl = doc.getChildNodes();

            for (int i = 0; i < nl.getLength(); i++) {
                Node child = nl.item(i);

                if (child != null) {
                    String elName = getLocalName(child);

                    if (elName != null) {
                        xpdlPackage = child;
                    }
                }

            }

            if (xpdlPackage != null) {
                Node child = null;
                String szExtendedAttributes = "ExtendedAttributes"; //$NON-NLS-1$

                // Get the child nodes of package
                NodeList packageChildren = xpdlPackage.getChildNodes();

                // Scan through the children of the package node
                for (int nPackageChild =
                        0; nPackageChild < packageChildren.getLength()
                                && nFormatVer < 0; nPackageChild++) {
                    // If extended attributes found then get the format version
                    child = packageChildren.item(nPackageChild);
                    String localName = getLocalName(child);
                    if (szExtendedAttributes.equals(localName)) {
                        // Find the format version
                        NodeList extAttrChildren = child.getChildNodes();
                        boolean bErr = false;
                        Node node = null;

                        for (int nExtAttrChild =
                                0; nExtAttrChild < extAttrChildren.getLength()
                                        && nFormatVer < 0; nExtAttrChild++) {
                            child = extAttrChildren.item(nExtAttrChild);
                            // get the attributes of this node
                            NamedNodeMap attrs = child.getAttributes();

                            if (attrs != null
                                    && attrs.getNamedItem("Name") != null) { //$NON-NLS-1$
                                // Get the Name attribute
                                node = attrs.getNamedItem("Name"); //$NON-NLS-1$
                                // Is the attribute value FormatVersion
                                String formatVersionAtt =
                                        FORMAT_VERSION_ATT_NAME;
                                if (node.getNodeValue()
                                        .equals(formatVersionAtt)) {
                                    // Get the value attribute
                                    if ((node = attrs
                                            .getNamedItem("Value")) != null) { //$NON-NLS-1$
                                        try {
                                            nFormatVer = Integer.parseInt(
                                                    node.getNodeValue());
                                        } catch (NumberFormatException e) {
                                            // return error
                                            bErr = true;
                                            break;
                                        }
                                    }
                                }
                            }
                        }

                        // If error in reading the format version then return -1
                        if (bErr) {
                            nFormatVer = -1;
                            break;
                        } else if (nFormatVer < 0) {
                            // If no format version was found then set version
                            // to 0
                            nFormatVer = 0;
                        }
                    }
                }
            }
        }

        return nFormatVer;
    }

    /**
     * For some reasons Node.getLocalName() does not return name without prefix,
     * so we'll do our own!
     * 
     * @param node
     * @return
     */
    private static String getLocalName(Node node) {

        String localName = ""; //$NON-NLS-1$

        String elName = node.getNodeName();

        if (elName != null) {
            if (elName.contains(":")) { //$NON-NLS-1$
                localName = elName.substring(elName.indexOf(":") + 1); //$NON-NLS-1$
            } else {
                localName = elName;
            }
        }
        return localName;
    }

    /**
     * Migrate the given resource to the current Format Version. The IResource
     * passed in will be updated with the migrated content.
     * <p>
     * NOTE: This is not recommended for migration of multiple resources. To
     * migrate multiple resources create an instance of this class and call
     * {@link #transform(IResource)} for each resource.
     * </p>
     * 
     * @param inputResource
     * @throws TransformerConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws CoreException
     * @throws PackageVersionProblemException
     */
    public static void migrate(IResource inputResource)
            throws TransformerConfigurationException, SAXException, IOException,
            ParserConfigurationException, CoreException,
            PackageVersionProblemException {
        XpdlMigrate xpdlMigrate = new XpdlMigrate();
        xpdlMigrate.transform(inputResource);
    }

    /**
     * As per {@link #migrate(IResource)} except the caller can choose only to
     * perform the xslt part of the migration and leave the post-migration
     * command injection til later.
     * <p>
     * This is useful in situations where the file to be migrated cannot (for
     * some reason such as it doesn't have .xpdl extension) be loaded into a
     * working copy which is required if injected commands are to be executed.
     * 
     * @param inputResource
     * @param performPostMigrateCommandInjection
     * 
     * @throws TransformerConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws CoreException
     * @throws PackageVersionProblemException
     */
    public static void migrate(IResource inputResource,
            boolean performPostMigrateCommandInjection)
            throws TransformerConfigurationException, SAXException, IOException,
            ParserConfigurationException, CoreException,
            PackageVersionProblemException {
        XpdlMigrate xpdlMigrate = new XpdlMigrate();
        xpdlMigrate.transform(inputResource,
                performPostMigrateCommandInjection);
    }

    /**
     * Transformer error listener that will log all errors/warnings.
     * 
     * @author njpatel
     * 
     * @since 3.1
     */
    private class TransformerErrorListener implements ErrorListener {

        public TransformerErrorListener() {
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
            XpdResourcesUIActivator.getDefault().getLogger().error(exception,
                    Messages.XpdlMigrate_migrationError_shortdesc);
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
            XpdResourcesUIActivator.getDefault().getLogger().error(exception,
                    Messages.XpdlMigrate_migrationFatalError_shortdesc);
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
            XpdResourcesUIActivator.getDefault().getLogger().warn(exception,
                    Messages.XpdlMigrate_migrationWarning_shortdesc);
        }
    }
}
