/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.databaseservice.properties;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.datatools.sqltools.sqlbuilder.input.SQLBuilderStorageEditorInput;

/**
 * @author NWilson
 * 
 */
public class EmbeddedSQLBuilderStorageEditorInput extends
        SQLBuilderStorageEditorInput {
    private String url;

    public EmbeddedSQLBuilderStorageEditorInput(String url, IStorage storage) {
        super(storage);
        this.url = url;
    }

    @Override
    public String getSQL() {
        String sql = ""; //$NON-NLS-1$
        IStorage storage = getStorage();
        if (storage != null) {
            try {
                InputStream is = storage.getContents();
                StringBuilder builder = new StringBuilder();
                byte[] buffer = new byte[128];
                int count = 0;
                try {
                    while ((count = is.read(buffer)) != -1) {
                        String next = new String(buffer, 0, count);
                        builder.append(next);
                    }
                    sql = builder.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
        return sql;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if (obj instanceof EmbeddedSQLBuilderStorageEditorInput) {
            EmbeddedSQLBuilderStorageEditorInput other =
                    (EmbeddedSQLBuilderStorageEditorInput) obj;
            if (url != null && url.equals(other.url)) {
                equal = true;
            }
        }
        return equal;
    }

    @Override
    public int hashCode() {
        int code = 0;
        if (url != null) {
            code = url.hashCode();
        }
        return code;
    }

}
