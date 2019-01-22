/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.databaseservice.properties;

import java.io.ByteArrayInputStream;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.datatools.sqltools.sqlbuilder.SQLBuilderEditor;
import org.eclipse.datatools.sqltools.sqlbuilder.input.ISQLBuilderEditorInput;
import org.eclipse.datatools.sqltools.sqlbuilder.input.SQLBuilderStorageEditorInput;

/**
 * @author NWilson
 * 
 */
public class EmbeddedSQLBuilderEditor extends SQLBuilderEditor {

    @Override
    public void doSave(IProgressMonitor progressMonitor) {
        ISQLBuilderEditorInput input =
                getSQLBuilder().getSQLBuilderEditorInput();
        if (input instanceof SQLBuilderStorageEditorInput) {
            SQLBuilderStorageEditorInput storageInput =
                    (SQLBuilderStorageEditorInput) input;
            IStorage storage = storageInput.getStorage();
            if (storage instanceof ISaveableStorage) {
                ISaveableStorage saveable = (ISaveableStorage) storage;
                String sql = getSQLBuilder().getSourceViewer().getText();
                saveable.setContents(new ByteArrayInputStream(sql.getBytes()));
                getSQLBuilder().setDirty(false);
                notifyContentChange();
            }
        }
    }

}
