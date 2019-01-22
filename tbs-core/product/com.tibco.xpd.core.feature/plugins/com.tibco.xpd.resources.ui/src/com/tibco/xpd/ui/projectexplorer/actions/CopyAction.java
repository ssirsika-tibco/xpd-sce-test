/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.projectexplorer.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.SelectionListenerAction;
import org.eclipse.ui.part.ResourceTransfer;

import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * Project Explorer copy action for the <code>SpecialFolder</code> objects.
 * 
 * @author njpatel
 * 
 */
public class CopyAction extends SelectionListenerAction {

    /**
     * The id of this action.
     */
    public static String ID = XpdResourcesUIActivator.ID + ".copyAction"; //$NON-NLS-1$

    // private final Shell shell;

    private final Clipboard clipboard;

    // public CopyAction(Shell shell, Clipboa {rd clipboard) {
    public CopyAction(Clipboard clipboard) {
        super(Messages.CopyAction_action);
        // Assert.isNotNull(shell);
        Assert.isNotNull(clipboard);
        // this.shell = shell;
        this.clipboard = clipboard;

        setId(ID);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this, "CopyHelpId"); //$NON-NLS-1$

    }

    @Override
    protected boolean updateSelection(IStructuredSelection selection) {
        boolean ret = super.updateSelection(selection);

        if (ret) {
            // Check that all selection resources are SpecialFolders
            if (ret = allSpecialFolders(selection)) {
                // Check that all special folders must have common parent
                ret = allHaveCommonParent(selection);
            }
        }

        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void run() {
        List resources = getSelectedResources();

        if (resources != null) {
            List<String> folderNames = new ArrayList<String>();
            StringBuffer buffer = new StringBuffer();
            int idx = 0;
            for (Object obj : resources) {
                IResource res = (IResource) obj;

                if (res.getLocation() != null) {
                    folderNames.add(res.getLocation().toOSString());
                }

                if (idx > 0) {
                    buffer.append('\n');
                }

                buffer.append(res.getName());
            }
            IResource[] resourceArray = (IResource[]) resources
                    .toArray(new IResource[resources.size()]);

            if (folderNames.size() > 0) {
                String[] folderNameArray = folderNames
                        .toArray(new String[folderNames.size()]);

                // Set up three transfers - resources, file and text
                clipboard.setContents(new Object[] { resourceArray,
                        folderNameArray, buffer.toString() },
                        new Transfer[] { ResourceTransfer.getInstance(),
                                FileTransfer.getInstance(),
                                TextTransfer.getInstance() });
            } else {
                // Set up two transfers - resources and text
                clipboard.setContents(new Object[] { resourceArray,
                        buffer.toString() },
                        new Transfer[] { ResourceTransfer.getInstance(),
                                FileTransfer.getInstance(),
                                TextTransfer.getInstance() });
            }
        }
    }

    /**
     * Check if all objects in the selection have a common parent. It will be
     * assumed that all objects in the selection are <code>SpecialFolder</code>.
     * 
     * @param selection
     * @return
     */
    private boolean allHaveCommonParent(IStructuredSelection selection) {
        if (selection != null) {
            boolean ret = true;
            IProject project = null;

            // Get parent of the first element
            if (selection.getFirstElement() instanceof SpecialFolder) {
                project = ((SpecialFolder) selection.getFirstElement())
                        .getProject();
            }

            if (project != null) {
                for (Iterator<?> iter = selection.iterator(); iter.hasNext()
                        && ret;) {
                    ret = (((SpecialFolder) iter.next()).getProject()
                            .equals(project));
                }
            }

            return ret;
        }
        return false;
    }

    /**
     * Check that all items in the selection are special folders
     * 
     * @param selection
     * @return
     */
    private boolean allSpecialFolders(IStructuredSelection selection) {

        if (selection != null) {
            boolean ret = true;
            for (Iterator<?> iter = selection.iterator(); iter.hasNext() && ret;) {
                ret = iter.next() instanceof SpecialFolder;
            }

            return ret;
        }

        return false;
    }

}
