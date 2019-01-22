/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.ui.projectexplorer.dropassist;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.ui.navigator.CommonDropAdapter;

import com.tibco.xpd.resources.projectconfig.SpecialFolder;

/**
 * Project explorer drop assist that allows for dropping resources into a
 * special folder. This should be used by any navigator contributor that
 * contributes special folders.
 * <p>
 * NOTE: In order for files and folders to be dropped in a special folder the
 * navigator contents possibleChildren should include
 * <code>org.eclipse.core.resources.IFolder</code> and
 * <code>org.eclipse.core.resources.IFile</code>.
 * </p>
 * <p>
 * Since 3.0.1 this extends {@link WCResourceDropAssist} that will check if any
 * resources being dragged is managed by a <code>WorkingCopy</code> and is
 * marked as dirty; if it is then the user will be allowed to save before
 * continuing with the drop, or asked to save before attempting the drag again.
 * </p>
 * 
 * @see WCResourceDropAssist
 * 
 * @author njpatel
 * 
 */
public class SpecialFolderDropAssist extends WCResourceDropAssist {

    @Override
    public IStatus validateDrop(Object target, int operation,
            TransferData transferType) {

        // If the drop target is a SpecialFolder then get the underlying IFolder
        if (target instanceof SpecialFolder) {
            target = ((SpecialFolder) target).getFolder();
        }

        return super.validateDrop(target, operation, transferType);
    }

    @Override
    public IStatus handleDrop(CommonDropAdapter dropAdapter,
            DropTargetEvent dropTargetEvent, Object target) {

        // If the drop target is a SpecialFolder then get the underlying IFolder
        if (target instanceof SpecialFolder) {
            target = ((SpecialFolder) target).getFolder();
        }

        return super.handleDrop(dropAdapter, dropTargetEvent, target);
    }
}
