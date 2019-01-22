/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.core.help;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.swt.browser.Browser;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.core.help.internal.DefineHelpCommands.HelpActionType;
import com.tibco.xpd.core.help.internal.DocFileUtils;
import com.tibco.xpd.core.help.internal.Messages;
import com.tibco.xpd.core.help.internal.preferences.XpdHelpPreferences;
import com.tibco.xpd.core.help.internal.preferences.XpdHelpPreferences.OpenMode;
import com.tibco.xpd.core.help.views.XpdHelpView;

/**
 * Service showing for Studio help.
 * 
 * @author jarciuch
 * @since 30 Jul 2014
 */
public class XpdHelpService {

    private static final String DEFAULT_OFFLINE_INDEX_PATH = "html/index.html"; //$NON-NLS-1$

    private static XpdHelpService INSTANCE = new XpdHelpService();

    private static String XPD_SHARRED_BROWSER_ID = "xpdSharedBrowserId"; //$NON-NLS-1$

    private XpdHelpService() {

    }

    /**
     * @return gets the instance of this service.
     */
    public static XpdHelpService getInstance() {
        return INSTANCE;
    }

    /**
     * Shows help content for the product help content extension. The content
     * will be shown in the xpd help view or a browser depending on preferences.
     * Also, depending on content access strategy preference and availability of
     * offline documentation the online or offline version of the product
     * documentation will be displayed.
     * 
     * @param workbenchWindow
     *            the workbench window to display help.
     * @param productHelpContent
     *            the product help content to be displayed.
     */
    public void showHelpContent(IWorkbenchWindow workbenchWindow,
            ProductHelpContent productHelpContent) {
        String helpContentUrl = getHelpContentUrl(productHelpContent);
        OpenMode openMode = XpdHelpPreferences.getHelpContentOpenPreference();
        switch (openMode) {
        case IN_HELP_VIEW:
            openInHelpView(workbenchWindow, helpContentUrl);
            break;
        case IN_BROWSER:
            String browserId =
                    new StringBuilder('_')
                            .append(productHelpContent.getProductId())
                            .append('_')
                            .append(HelpActionType.WEB_HELP.toString())
                            .toString();
            openInBrowser(workbenchWindow, helpContentUrl, browserId);
            break;
        default:
            openInHelpView(workbenchWindow, helpContentUrl);
            break;
        }
    }

    /**
     * Shows product main documentation page (always online).
     * 
     * @param workbenchWindow
     *            the workbench window to display help.
     * @param productHelpContent
     *            the product help content to be displayed.
     */
    public void showProductHelpPage(IWorkbenchWindow workbenchWindow,
            ProductHelpContent productHelpContent) {
        String productHelpPageUrl = productHelpContent.getProductHelpUrl();
        OpenMode openMode = XpdHelpPreferences.getProductPageOpenPreference();
        switch (openMode) {
        case IN_HELP_VIEW:
            openInHelpView(workbenchWindow, productHelpPageUrl);
            break;
        case IN_BROWSER:
            String browserId =
                    new StringBuilder('_')
                            .append(productHelpContent.getProductId())
                            .append('_')
                            .append(HelpActionType.PRODUCT_HELP.toString())
                            .toString();
            openInBrowser(workbenchWindow, productHelpPageUrl, browserId);
            break;
        default:
            openInHelpView(workbenchWindow, productHelpPageUrl);
            break;
        }

    }

    /**
     * Checks if offline help content is accessible for the product help
     * content.
     */
    public boolean isOfflineHelpAccessible(ProductHelpContent productHelpContent) {
        File offlineFile = getOfflineContnetFile(productHelpContent);
        return offlineFile.exists();
    }

    /**
     * Returns the default offline absolute folder for the product help content.
     */
    public File getDefaulOfflineFolder(ProductHelpContent productHelpContent) {
        return new File(XpdHelpPreferences.getBaseOfflineFolder(),
                productHelpContent.getOfflineFolderName());
    }

    /**
     * Returns a file handle to the starting page of the offline documentation
     * for the product (it may not exist (if for example offline content was not
     * yet downloaded)).
     */
    public File getOfflineContnetFile(ProductHelpContent productHelpContent) {
        File offlineFolder = getDefaulOfflineFolder(productHelpContent);
        String offlineIndexPath = productHelpContent.getOfflineIndexPath();
        if (offlineIndexPath != null) {
            return new File(offlineFolder, offlineIndexPath);
        }
        return new File(offlineFolder, DEFAULT_OFFLINE_INDEX_PATH);
    }

    /**
     * Downloads and unpack a zipped version of documentation into the
     * offlineDocFolder. If the offlineDocFolder already exists then it will be
     * replaced (message dialog will be shown to get use's consent).
     * 
     * @param downloadUrl
     *            the URL to the file containing zipped offline help content.
     * @param offlineDocFolder
     *            the absolute offline documentaion folder to be used (it will
     *            be created if doesn't exist).
     * @param monitor
     *            the progress monitor (to show progress and allow cancellation)
     * @return status of the operation.
     */
    public IStatus downloadOfflineDocs(String downloadUrl,
            File offlineDocFolder, IProgressMonitor monitor) {
        monitor.beginTask(Messages.XpdHelpService_downloadingOfflineHelp_message,
                100);
        File tmpFile = getDownloadTmpFile(offlineDocFolder);
        try {
            IStatus downloadFileStatus =
                    DocFileUtils.downloadFile(downloadUrl,
                            tmpFile,
                            new SubProgressMonitor(monitor, 70));

            if (downloadFileStatus.getSeverity() == IStatus.CANCEL
                    || downloadFileStatus.getSeverity() == Status.ERROR) {
                return downloadFileStatus;
            }

            monitor.worked(1);
            monitor.setTaskName(Messages.XpdHelpService_removingOfflineFolder_message);
            if (offlineDocFolder.exists()) {
                if (!deleteDirectory(offlineDocFolder)) {
                    return new Status(
                            Status.ERROR,
                            CoreHelpActivator.PLUGIN_ID,
                            String.format(Messages.XpdHelpService_couldNotRemoveFolder_message,
                                    offlineDocFolder));
                }
            }
            monitor.worked(4);

            IStatus extractStatus =
                    DocFileUtils.extract(tmpFile,
                            offlineDocFolder,
                            new SubProgressMonitor(monitor, 24));
            if (extractStatus.getSeverity() == IStatus.CANCEL
                    || extractStatus.getSeverity() == Status.ERROR) {
                return extractStatus;
            }
            return Status.OK_STATUS;
        } finally {
            if (tmpFile.exists()) {
                tmpFile.delete();
            }
            monitor.worked(1);
            monitor.done();
        }
    }

    /**
     * @return the default product help content if more then one available or
     *         'null' if none are present.
     */
    public ProductHelpContent getDefaultProductHelpContent() {
        List<ProductHelpContent> cs =
                XpdHelpContribManager.getInstance().getProductHelpContents();
        if (!cs.isEmpty()) {
            return cs.iterator().next();
        }
        return null;
    }

    private String getOfflinePrefferedHelpContentUrl(
            ProductHelpContent productHelpContent) {
        File offFile = getOfflineContnetFile(productHelpContent);
        if (offFile.exists()) {
            return offFile.toURI().toString();
        }
        return productHelpContent.getProductHelpContentUrl();
    }

    private String getHelpContentUrl(ProductHelpContent productHelpContent) {
        switch (XpdHelpPreferences.getAccessStrategy()) {
        case PREFER_OFFLINE:
            return getOfflinePrefferedHelpContentUrl(productHelpContent);
        case ALWAYS_ONLINE:
            return productHelpContent.getProductHelpContentUrl();
        }
        return getOfflinePrefferedHelpContentUrl(productHelpContent);
    }

    private void openInHelpView(IWorkbenchWindow workbenchWindow, String url) {
        IViewPart view = null;
        try {
            view = workbenchWindow.getActivePage().showView(XpdHelpView.ID);
        } catch (PartInitException e) {
            CoreHelpActivator.getDefault().logError(e);
        }
        if (view instanceof XpdHelpView) {
            XpdHelpView xpdHelpView = (XpdHelpView) view;
            Browser browser = xpdHelpView.getBrowser();
            if (browser != null) {
                browser.setUrl(url);
            }
        }
    }

    private void openInBrowser(IWorkbenchWindow workbenchWindow, String url,
            String browserId) {
        try {
            URL _url = new URL(url);
            PlatformUI.getWorkbench().getBrowserSupport()
                    .createBrowser(XPD_SHARRED_BROWSER_ID + browserId)
                    .openURL(_url);
        } catch (PartInitException e) {
            CoreHelpActivator.getDefault().logError(e);
        } catch (MalformedURLException e) {
            CoreHelpActivator.getDefault().logError(e);
        }
    }

    private File getDownloadTmpFile(File offlineDocFolder) {
        File tmpFile =
                new File(offlineDocFolder.getAbsoluteFile() + "-tmp.zip"); //$NON-NLS-1$
        File tmpParent = tmpFile.getParentFile();
        if (!tmpParent.exists()) {
            tmpParent.mkdirs();
        }
        return tmpFile;
    }

    private boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }

}
