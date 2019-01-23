/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.utils;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ImageHyperlink;

import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.RCPImages;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.actions.ICancellableAction;
import com.tibco.xpd.rcp.internal.actions.NewProjectAction;
import com.tibco.xpd.rcp.internal.actions.OpenFileAction;
import com.tibco.xpd.rcp.internal.actions.OpenFolderAction;
import com.tibco.xpd.rcp.internal.actions.OpenProjectFromSVNURLAction;
import com.tibco.xpd.resources.ui.components.BaseXpdToolkit;

/**
 * Dialog that will present to the user when the RCP starts. This will allow the
 * user to create new project or open an existing project.
 * 
 * @author njpatel
 * 
 */
public class SplashDialog extends Dialog {

    private final Color bgColor;

    private final Font font;

    private ImageHyperlink newProject;

    private Hyperlink closeLink;

    private ImageHyperlink openMAA;

    private ImageHyperlink openFolderLocation;

    private final LinkListener linkListener;

    private Action actionToRunOnClose;

    private final Image logoImg;

    private ImageHyperlink openProjectsFromSVNURL;

    private final Toolkit toolkit;

    public SplashDialog(Shell parentShell) {
        super(parentShell);
        setShellStyle(SWT.APPLICATION_MODAL | SWT.TOOL);
        bgColor = new Color(parentShell.getDisplay(), 203, 225, 252);
        toolkit = new Toolkit(bgColor);
        font = new Font(parentShell.getDisplay(), "Arial", 10, SWT.BOLD); //$NON-NLS-1$
        linkListener = new LinkListener();
        logoImg =
                RCPActivator
                        .getImageDescriptor("launcher/icons/tibco48-32.png") //$NON-NLS-1$
                        .createImage();
        /*
         * Don't block on open otherwise the UI thread will be locked during
         * startup and other threads (builder in particular) will be held up
         * when requiring the UI thread ultimately leading to a deadlock.
         */
        setBlockOnOpen(false);
    }

    @Override
    protected Control createContents(Composite parent) {
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout(2, false));
        initializeDialogUnits(root);

        Composite logoContainer = toolkit.createComposite(root);
        GridData data = new GridData(SWT.CENTER, SWT.TOP, false, true);
        data.verticalIndent = 10;
        data.horizontalIndent = 5;
        logoContainer.setLayoutData(data);
        logoContainer.setLayout(new FillLayout());

        CLabel logo = toolkit.createCLabel(logoContainer, ""); //$NON-NLS-1$
        logo.setImage(logoImg);

        Composite linkContainer = toolkit.createComposite(root);
        linkContainer
                .setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        linkContainer.setLayout(new GridLayout());

        newProject =
                createHyperlink(linkContainer,
                        RCPActivator.getDefault().getImageRegistry()
                                .get(RCPImages.NEW_PROJECT.getPath()),
                        Messages.SplashDialog_createNewProject_hyperlink_label1);
        newProject.addHyperlinkListener(linkListener);

        openMAA =
                createHyperlink(linkContainer,
                        RCPActivator.getDefault().getImageRegistry()
                                .get(RCPImages.MAA.getPath()),
                        Messages.SplashDialog_openExistingMAA_hyperlink_label);
        openMAA.addHyperlinkListener(linkListener);

        openFolderLocation =
                createHyperlink(linkContainer,
                        PlatformUI.getWorkbench().getSharedImages()
                                .getImage(ISharedImages.IMG_OBJ_FOLDER),
                        Messages.SplashDialog_openFromFolderLocation_label);
        openFolderLocation.addHyperlinkListener(linkListener);

        openProjectsFromSVNURL =
                createHyperlink(linkContainer,
                        RCPActivator.getDefault().getImageRegistry()
                                .get(RCPImages.SVN.getPath()),
                        Messages.SplashDialog_openFromSVN_label);
        openProjectsFromSVNURL.addHyperlinkListener(linkListener);

        closeLink = new Hyperlink(linkContainer, SWT.NONE);
        toolkit.adapt(closeLink);
        closeLink.setText(Messages.SplashDialog_close_hyperlink_label);
        closeLink.setUnderlined(true);
        data = new GridData(SWT.RIGHT, SWT.FILL, true, false);
        data.verticalIndent = 10;
        closeLink.setLayoutData(data);
        closeLink.addHyperlinkListener(linkListener);

        return root;
    }

    /**
     * Create a hyperlink with the given image and text.
     * 
     * @param parent
     * @param img
     * @param text
     * @return
     */
    private ImageHyperlink createHyperlink(Composite parent, Image img,
            String text) {
        ImageHyperlink link = new ImageHyperlink(parent, SWT.NONE);
        link.setUnderlined(true);
        link.setFont(font);
        link.setImage(img);
        link.setText(text);
        GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
        data.horizontalIndent = 10;
        data.verticalIndent = 8;
        link.setLayoutData(data);
        toolkit.adapt(link);

        return link;
    }

    @Override
    public boolean close() {
        bgColor.dispose();
        font.dispose();
        logoImg.dispose();

        return super.close();
    }

    /**
     * Hyperlink listener to run the selected action.
     */
    private class LinkListener extends HyperlinkAdapter {
        @Override
        public void linkActivated(HyperlinkEvent e) {
            Widget widget = e.widget;
            actionToRunOnClose = null;
            if (widget == newProject) {
                actionToRunOnClose =
                        new NewProjectAction(PlatformUI.getWorkbench()
                                .getActiveWorkbenchWindow(), true);
            } else if (widget == openMAA) {
                actionToRunOnClose =
                        new OpenFileAction(PlatformUI.getWorkbench()
                                .getActiveWorkbenchWindow());
            } else if (widget == openFolderLocation) {
                actionToRunOnClose =
                        new OpenFolderAction(PlatformUI.getWorkbench()
                                .getActiveWorkbenchWindow());
            } else if (widget == openProjectsFromSVNURL) {
                actionToRunOnClose =
                        new OpenProjectFromSVNURLAction(PlatformUI
                                .getWorkbench().getActiveWorkbenchWindow());
            }

            // If there is an action to run it
            if (actionToRunOnClose != null) {

                /*
                 * Temporarily hide the dialog. This is necessary as otherwise
                 * if another dialog is shown (for example the progress dialog)
                 * during the running of the selected action then it will look
                 * ugly to have this dialog partially showing behind the later
                 * dialog.
                 */
                getShell().setAlpha(50);
                actionToRunOnClose.run();

                if (actionToRunOnClose instanceof ICancellableAction
                        && ((ICancellableAction) actionToRunOnClose)
                                .isCancelled()) {
                    /*
                     * The user cancelled the subsequent action so don't close
                     * this splash dialog.
                     */
                    getShell().setAlpha(255);
                    return;
                }
            }

            // Close the splash dialog
            close();
        }
    }

    /**
     * Local toolkit to create the SWT controls for this dialog.
     */
    private static class Toolkit extends BaseXpdToolkit {
        private final Color bgColor;

        /**
         * 
         */
        public Toolkit(Color bgColor) {
            this.bgColor = bgColor;
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.BaseXpdToolkit#adapt(org.eclipse.swt.widgets.Composite)
         * 
         * @param composite
         */
        @Override
        public void adapt(Composite composite) {
            composite.setBackground(bgColor);
        }
    }
}
