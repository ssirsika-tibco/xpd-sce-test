/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.importexport.pluginGenerator;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;
import java.util.Dictionary;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.Bundle;

import com.tibco.xpd.importexport.ImportExportPlugin;
import com.tibco.xpd.importexport.internal.Messages;
import com.tibco.xpd.importexport.pluginGenerator.IPluginData.IPluginDataProvider;
import com.tibco.xpd.importexport.pluginGenerator.IPluginData.ITreeViewerFilterProvider;
import com.tibco.xpd.importexport.pluginGenerator.IPluginData.IWizardDataProvider;
import com.tibco.xpd.xpdl2.edit.util.LocaleUtils;

/**
 * Create the new wizard plug-in jar file. A template jar file will be used as
 * input and a copy will be made with modifications to create a new plug-in jar
 * file.
 * <p>
 * The modifications include:
 * <ul>
 * <li>Create manifest.mf file. See
 * <code>{@link #createManifest(JarInputStream, JarOutputStream)}</code> for
 * more details.</li>
 * <li>Create a plugin.properties file to include all user strings. See
 * <code>{@link #createPluginProperties(JarOutputStream)}</code> for more
 * details.</li>
 * <li>Create an icon image file if one was provided by the user.</li>
 * <li>Insert the transformation xslt file.</li>
 * <li>Insert a schema file if one was provided by the user.</li>
 * </ul>
 * </p>
 * <p>
 * Use the following methods to:
 * <ul>
 * <li><code>{@link #checkPlugin()}</code> - to verify the plug-in, see
 * method for details.
 * <li><code>{@link #create(File)}</code> - to create the plug-in jar file.</li>
 * </ul>
 * </p>
 * 
 * @author njpatel
 */
public class PluginGenerator {

    /**
     * Exception raised to indicate that the plug-in with the given ID already
     * exists.
     * <p>
     * Use <code>{@link #getPluginId()}</code> to get the ID of the plug-in
     * that exists. Use <code>{@link #getPluginVersion()}</code> to get the
     * version of this plug-in, this can be <code>null</code> indicating that
     * the version was not located.
     * </p>
     * 
     * @author njpatel
     */
    public class PluginExistsException extends Exception {

        /**
         * Generated UID
         */
        private static final long serialVersionUID = -3367493278950877162L;

        private final String id;

        private final String version;

        /**
         * Plugin exists exception. Indicates that the plug-in with the id (and
         * version) already exists.
         * 
         * @param id -
         *            Id of the plug-in that exists.
         * @param version -
         *            Version of the plug-in that exists, this can be <b>null</b>.
         */
        public PluginExistsException(String id, String version) {
            this.id = id;
            this.version = version;
        }

        /**
         * Get the ID of the plug-in.
         * 
         * @return ID.
         */
        public String getPluginId() {
            return id;
        }

        /**
         * Get the Version of the plug-in.
         * 
         * @return Version of the plug-in, this can be <b>null</b> indicating
         *         no version was found.
         */
        public String getPluginVersion() {
            return version;
        }

    }

    private final Shell shell;

    private static final String PLUGIN_PROPERTIES = "plugin.properties"; //$NON-NLS-1$

    private static final String MANIFEST_MF = "META-INF/MANIFEST.MF"; //$NON-NLS-1$

    private static final String ICONS_FOLDER = "icons/"; //$NON-NLS-1$

    private static final String XSLT_FOLDER = "xslt/"; //$NON-NLS-1$

    private final URL pluginTemplate;

    private final IPluginData pluginData;

    /**
     * Create a new wizard plug-in jar file.
     */
    public PluginGenerator(Shell shell, URL pluginTemplate,
            IPluginData pluginData) {
        this.shell = shell;

        this.pluginTemplate = pluginTemplate;
        this.pluginData = pluginData;

        // Check input
        if (pluginTemplate == null || pluginData == null
                || pluginData.getPluginDataProvider() == null
                || pluginData.getTreeViewerFilterProvider() == null
                || pluginData.getWizardDataProvider() == null) {
            throw new NullPointerException(
                    "PluginGenerator doesn't have all the data required to create a plug-in"); //$NON-NLS-1$
        }
    }

    /**
     * Verify the Plug-in ID and version. This checks if a Plug-in with the
     * given ID (and version) already exists. If it does then a warning message
     * will be displayed for the user to select whether to progress.
     * 
     * @param pluginId -
     *            the plug-in ID
     * @throws <code>{@link PluginExistsException}</code> - thrown if a plugin
     *             with the same id/version is found.
     * 
     */
    public void checkPlugin() throws PluginExistsException {
        if (pluginData.getPluginDataProvider() != null) {
            String pluginId = pluginData.getPluginDataProvider().getPluginId();

            // Continue ID is available
            if (pluginId.length() > 0) {
                boolean idExists = false;
                String pluginVersion = null;

                // Get all plugins
                Bundle[] allBundles = ImportExportPlugin.getDefault()
                        .getPluginContext().getBundles();

                // Find a bundle with the matching ID - if any. If found try
                // match
                // the version number as well
                for (int x = 0; x < allBundles.length && !idExists; x++) {
                    if (allBundles[x].getSymbolicName().equalsIgnoreCase(
                            pluginId)) {
                        idExists = true;
                        // Get the version number
                        Dictionary header = allBundles[x].getHeaders();
                        pluginVersion = (String) header.get("Bundle-Version"); //$NON-NLS-1$
                    }
                }

                if (idExists) {
                    // Throw exception indicating that the plug-in exists
                    throw new PluginExistsException(pluginId, pluginVersion);
                }
            }
        }
    }

    /**
     * Create the new wizard's plug-in jar file. This will be created at the
     * given folder location.
     * 
     * @param fPath
     * @throws IOException
     * @throws InterruptedException
     */
    public void create(File fPath) throws IOException, InterruptedException {

        if (fPath == null) {
            throw new NullPointerException("Folder path is null."); //$NON-NLS-1$
        } else if (!fPath.isDirectory()) {
            throw new IllegalArgumentException("Given path is not a folder."); //$NON-NLS-1$
        }
        JarInputStream jInputStream = null;
        JarOutputStream jOutputStream = null;

        try {
            if (pluginTemplate != null) {
                // Get the import template jar
                InputStream iTemplateStream = pluginTemplate.openStream();

                // Check if template was loaded
                if (iTemplateStream == null) {
                    throw new FileNotFoundException(pluginTemplate.getFile());
                }

                jInputStream = new JarInputStream(iTemplateStream);
                JarEntry jEntry;

                // Make sure that fPath is a folder
                if (fPath.isDirectory()) {
                    IPluginDataProvider pluginDataProvider = pluginData
                            .getPluginDataProvider();
                    IWizardDataProvider wizardDataProvider = pluginData
                            .getWizardDataProvider();

                    File fNewPlugin = new File(
                            fPath,
                            pluginDataProvider.getPluginId()
                                    + "_" + pluginDataProvider.getPluginVersion() + ".jar"); //$NON-NLS-1$ //$NON-NLS-2$

                    /*
                     * If plug-in exists ask user if they wish to over-write it.
                     * If they don't then quit here
                     */
                    if (fNewPlugin.exists()) {
                        if (!askUser(
                                Messages.PluginGenerator_pluginExistsDialogTitle,
                                String
                                        .format(
                                                Messages.PluginGenerator_pluginExists_message,
                                                fNewPlugin.getName())))
                            throw new InterruptedException();
                    }

                    // Create the new plugin output jar stream
                    FileOutputStream fout = new FileOutputStream(fNewPlugin);
                    jOutputStream = new JarOutputStream(fout);
                    // Get current time to timestamp the jar entries
                    long time = new Date().getTime();

                    // Update the new plugin jar file with user provided data
                    while ((jEntry = jInputStream.getNextJarEntry()) != null) {

                        if (!jEntry.isDirectory()) {
                            // create / init the zip entry
                            JarEntry entry = new JarEntry(jEntry.getName());
                            entry.setTime(time);
                            jOutputStream.putNextEntry(entry);

                            try {
                                // Copy files that don't need modification to
                                // template
                                if (!jEntry.getName().equalsIgnoreCase(
                                        MANIFEST_MF)) {
                                    copyInputToOutputStream(jInputStream,
                                            jOutputStream);
                                }
                            } finally {
                                // Close the current entry
                                jOutputStream.closeEntry();
                            }
                        }
                    }

                    /*
                     * Create a manifest file
                     */
                    JarEntry entry = new JarEntry(MANIFEST_MF);
                    entry.setTime(time);
                    jOutputStream.putNextEntry(entry);
                    createManifest(jInputStream, jOutputStream);
                    jOutputStream.closeEntry();

                    /*
                     * If an icon has been selected then add to the import
                     * plugin jar file
                     */
                    if (wizardDataProvider.getWizardIconFile() != null) {
                        addFileToJar(jOutputStream, ICONS_FOLDER
                                + wizardDataProvider.getWizardIconFile()
                                        .getName(), wizardDataProvider
                                .getWizardIconFile(), time);
                    }

                    // Add xslt to the jar
                    addFileToJar(jOutputStream, XSLT_FOLDER
                            + wizardDataProvider.getXsltFile().getName(),
                            wizardDataProvider.getXsltFile(), time);

                    // If a schema has been specified then add that to the xslt
                    // folder
                    if (wizardDataProvider.getSchemaFile() != null) {
                        addFileToJar(jOutputStream, XSLT_FOLDER
                                + wizardDataProvider.getSchemaFile().getName(),
                                wizardDataProvider.getSchemaFile(), time);
                    }

                    // Add the plugin properties file
                    createPluginProperties(jOutputStream);
                }
            }
        } finally {
            // Close the IO streams
            try {
                if (jInputStream != null)
                    jInputStream.close();

                if (jOutputStream != null)
                    jOutputStream.close();

            } catch (IOException e) {
                // Do nothing
            }
        }
    }

    /**
     * Create the Manifest.mf file. This will update the following with the data
     * provided by the user in the wizard:
     * <ul>
     * <li>Bundle name</li>
     * <li>Bundle symbolic name (id)</li>
     * <li>Bundle version</li>
     * <li>Bundle vendor (this can be blank)</li>
     * </ul>
     * 
     * @param iStream
     * @param oStream
     * @throws IOException
     */
    private void createManifest(JarInputStream iStream, JarOutputStream oStream)
            throws IOException {
        IPluginDataProvider pluginDataProvider = pluginData
                .getPluginDataProvider();

        // Get manifest from the template
        Manifest manifest = iStream.getManifest();

        // Update the attributes for the new plug-in
        Attributes attrs = manifest.getMainAttributes();
        attrs.putValue("Bundle-Name", pluginDataProvider.getPluginName()); //$NON-NLS-1$
        attrs.putValue("Bundle-SymbolicName", pluginDataProvider.getPluginId() //$NON-NLS-1$
                + ";singleton:=true"); //$NON-NLS-1$
        attrs.putValue("Bundle-Version", pluginDataProvider.getPluginVersion()); //$NON-NLS-1$
        attrs.putValue("Bundle-Vendor", pluginDataProvider.getPluginVendor()); //$NON-NLS-1$

        // Write manifest to new plug-in jar
        manifest.write(oStream);
    }

    /**
     * Add the given file to the plug-in jar file without modification.
     * 
     * @param jOutputStream
     * @param szEntryName
     * @param fileToInsert
     * @throws IOException
     */
    private void addFileToJar(JarOutputStream jOutputStream,
            String szEntryName, File fileToInsert, long timeStamp)
            throws IOException {
        JarEntry entry = new JarEntry(szEntryName);
        entry.setTime(timeStamp);
        jOutputStream.putNextEntry(entry);

        InputStream iStream = new FileInputStream(fileToInsert);

        try {
            // Copy the input stream to the output stream
            copyInputToOutputStream(iStream, jOutputStream);
        } catch (IOException e) {
            String szErrMsg = String.format(
                    Messages.PluginGenerator_unableToInsertIntoPlugin_message, fileToInsert
                            .getName(), e.getLocalizedMessage());

            throw new IOException(szErrMsg);

        } finally {
            jOutputStream.closeEntry();

            if (iStream != null) {
                try {
                    iStream.close();
                } catch (IOException e) {
                    ; // Do nothing, closing stream
                }
            }
        }
    }

    /**
     * Create a plugin.properties file. This will include externalised strings
     * from the plugin.xml file and also user defined strings that the generated
     * import wizard will use.
     * <p>
     * The created plugin properties file will contain the following keys with
     * the corresponding values provided by the user in the generator wizard:
     * <ul>
     * <li><b>wizard.name</b> - The title of the wizard used both in the
     * import wizard selection and also as the main title of the import wizard.</li>
     * <li><b>wizard.description</b> - The description of the wizard used both
     * in the import wizard selection and also as the main description of the
     * import wizard.</li>
     * <li><b>wizard.icon</b> - The location of the icon to be used in the
     * import wizards listing. This is an optional entry.</li>
     * <li><b>wizard.category</b> - The wizard category in the import/export
     * selection.</li>
     * <li><b>wizard.fileExtensionFilter</b> - String of file extensions. This
     * will be used by the file viewer used in selecting the files to import.</li>
     * <li><b>wizard.xsl</b> - The location of the transformation file to be
     * used for import.</li>
     * <li><b>wizard.outputFileExt</b> - The file extension of the output file
     * this wizard will generate.</li>
     * <li><b>wizard.specialFolderFilter</b> - The special folder filter for
     * the tree viewers in the wizards.</li>
     * <li><b>wizard.fileExtFilter</b> - The file filter in the tree viewers
     * in the wizards.</li>
     * <li><b>wizard.exportFolder</b> - For the export wizard, this will be
     * the workspace export folder.
     * <li>
     * </ul>
     * </p>
     * 
     * 
     * @param jarOutputStream
     * @throws IOException
     */
    private void createPluginProperties(JarOutputStream jarOutputStream)
            throws IOException {
        IPluginDataProvider pluginDataProvider = pluginData
                .getPluginDataProvider();
        IWizardDataProvider wizardDataProvider = pluginData
                .getWizardDataProvider();
        ITreeViewerFilterProvider treeFilterProvider = pluginData
                .getTreeViewerFilterProvider();

        JarEntry entry = new JarEntry(PLUGIN_PROPERTIES);
        jarOutputStream.putNextEntry(entry);
        StringBuffer buffer = new StringBuffer();

        /*
         * Create the plugin properties file
         */
        // Add wizard ID
        buffer.append(IPluginData.PROP_WIZARD_ID + "=" //$NON-NLS-1$
                + pluginDataProvider.getPluginId() + ".wizard\r\n"); //$NON-NLS-1$

        // Add wizard title
        String wizardTitle = LocaleUtils.escapeNonAsciiChars(wizardDataProvider.getWizardTitle());
        buffer.append(IPluginData.PROP_WIZARD_NAME + "=" //$NON-NLS-1$
                + wizardTitle + "\r\n"); //$NON-NLS-1$

        // Add wizard description
        String wizardDescription = LocaleUtils.escapeNonAsciiChars(wizardDataProvider.getWizardDescription()); 
        if (wizardDataProvider.getWizardDescription() != null) {
            buffer.append(IPluginData.PROP_WIZARD_DESCRIPTION
                    + "=" + wizardDescription + "\r\n"); //$NON-NLS-1$ //$NON-NLS-2$
        }

        // Add the wizard icon
        if (wizardDataProvider.getWizardIconFile() != null) {
            buffer
                    .append(IPluginData.PROP_ICON
                            + "=" //$NON-NLS-1$
                            + ICONS_FOLDER
                            + wizardDataProvider.getWizardIconFile().getName()
                            + "\r\n"); //$NON-NLS-1$
        }

        // Add the wizard category
        if (wizardDataProvider.getWizardCategoryId() != null) {
            buffer.append(IPluginData.PROP_WIZARD_CATEGORY
                    + "=" + wizardDataProvider.getWizardCategoryId() + "\r\n"); //$NON-NLS-1$ //$NON-NLS-2$
        }

        // Add output file extension
        buffer.append(IPluginData.PROP_OUTPUT_FILE_EXT + "=" //$NON-NLS-1$
                + wizardDataProvider.getOutputFileExt() + "\r\n"); //$NON-NLS-1$

        // Add the xslt
        buffer.append(IPluginData.PROP_XSL + "=" + XSLT_FOLDER //$NON-NLS-1$
                + wizardDataProvider.getXsltFile().getName() + "\r\n"); //$NON-NLS-1$

        // Add the special folder kind filter string
        if (treeFilterProvider.getTreeViewSpecialFolderFilter() != null) {
            buffer.append(IPluginData.PROP_SPECIALFOLDER_FILTER
                    + "=" //$NON-NLS-1$
                    + treeFilterProvider.getTreeViewSpecialFolderFilter()
                    + "\r\n"); //$NON-NLS-1$
        }

        // Add the file extension filter for the tree viewer
        if (treeFilterProvider.getTreeViewFileExtensionFilter() != null) {
            buffer.append(IPluginData.PROP_FILE_EXT_FILTER
                    + "=" //$NON-NLS-1$
                    + treeFilterProvider.getTreeViewFileExtensionFilter()
                    + "\r\n"); //$NON-NLS-1$
        }

        // Add the export workspace folder
        if (wizardDataProvider.getExportWorkspaceFolder() != null) {
            buffer.append(IPluginData.PROP_EXPORT_FOLDER + "=" //$NON-NLS-1$
                    + wizardDataProvider.getExportWorkspaceFolder() + "\r\n"); //$NON-NLS-1$
        }

        /*
         * Add the properties file to the plug-in
         */
        ByteArrayInputStream inStream = new ByteArrayInputStream(buffer
                .toString().getBytes());

        if (inStream != null) {
            // Insert the properties file into the jar
            copyInputToOutputStream(inStream, jarOutputStream);

            // Close the entry
            jarOutputStream.closeEntry();

            // Close input stream
            inStream.close();
        }
    }

    /**
     * Copies the contents of the input stream to the output stream
     * 
     * @param iStream
     * @param oStream
     * @throws IOException
     */
    private void copyInputToOutputStream(InputStream iStream,
            OutputStream oStream) throws IOException {
        byte[] buffer = new byte[512];
        int nBytesRead = 0;

        // Copy the contents of the input stream to the output stream
        while ((nBytesRead = iStream.read(buffer)) != -1) {
            oStream.write(buffer, 0, nBytesRead);
        }
    }

    /**
     * Display a question dialog to the user with the given message.
     * 
     * @param szTitle
     * @param szMsg
     * @return <code>true</code> if the user presses the OK button,
     *         <code>false</code> otherwise
     * 
     * </pre>
     */
    private boolean askUser(final String szTitle, final String szMsg) {
        final boolean bRet[] = new boolean[] { false };

        shell.getDisplay().syncExec(new Runnable() {
            public void run() {
                bRet[0] = MessageDialog.openQuestion(shell, szTitle, szMsg);
            }
        });

        return bRet[0];
    }
}
