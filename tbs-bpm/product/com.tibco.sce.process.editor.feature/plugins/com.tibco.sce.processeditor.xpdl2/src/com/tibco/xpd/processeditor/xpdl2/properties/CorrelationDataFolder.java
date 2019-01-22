/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties;

import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.fields.DataFieldImageProvider;
import com.tibco.xpd.processeditor.xpdl2.fields.DataFieldTextProvider;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.xpdl2.Process;

public class CorrelationDataFolder implements DataFieldTextProvider,
        DataFieldImageProvider {

    private Process process;

    public CorrelationDataFolder(Process process) {
        this.process = process;
    }

    public Process getProcess() {
        return process;
    }

    public String getId() {
        return null;
    }

    public String getName() {
        return Messages.AssociatedParameterItemProvider_CorrelationDataFolderName;
    }

    public Image getImage() {
        return Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                .get(ProcessEditorConstants.IMG_CORRELATION_FOLDER);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CorrelationDataFolder;
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

}