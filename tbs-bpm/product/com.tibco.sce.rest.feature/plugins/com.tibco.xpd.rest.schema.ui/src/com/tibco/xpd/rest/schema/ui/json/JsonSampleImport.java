/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui.json;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.tibco.xpd.rest.schema.ui.internal.Messages;
import com.tibco.xpd.rest.schema.ui.internal.RestSchemaUiPlugin;
import com.tibco.xpd.rest.schema.ui.internal.wizard.JsonSchemaImportMethod;

/**
 * Imports a JSON sample file creating a JSON schema.
 * 
 * @author nwilson
 * @since 13 Jan 2015
 */
public class JsonSampleImport {

    /**
     * Converts a given resource to a JSON schema file.
     * 
     * @param method
     *            The import method.
     * @param source
     *            The source resource.
     * @param target
     *            The target JSON Schema file.
     * @param ctx
     *            The runnable context in which to execute this import.
     * @throws IOException
     */
    public Resource schemaImport(JsonSchemaImportMethod method, Reader source,
            IPath target, IRunnableContext ctx) throws IOException {
        Resource resource = null;
        try {
            JsonSchemaCreator creator = new JsonSchemaCreator(target);
            JsonImportConvertor convertor = null;
            JsonParser parser = new JsonParser();
            JsonElement root = parser.parse(source);
            if (JsonSchemaImportMethod.JSON_SAMPLE.equals(method)) {
                convertor =
                        new JsonSampleImportConvertor(getRootName(target), root);
            } else if (JsonSchemaImportMethod.IETF_JSON_SCHEMA.equals(method)) {
                convertor = new JsonIetfImportConvertor(root);
            }
            if (convertor != null) {
                IRunnableWithProgress op =
                        creator.getCreateOperation(convertor);
                ctx.run(false, true, op);
            }
            resource = creator.getResource();
        } catch (InvocationTargetException | InterruptedException e) {
            RestSchemaUiPlugin.logError(e);
            throw new IOException(
                    Messages.JsonSampleImport_importExecutionError);
        } catch (JsonParseException e) {
            RestSchemaUiPlugin.logError(e);
            Throwable cause = e.getCause();
            if (cause != null) {
                throw new IOException(cause.getMessage());
            } else {
                throw new IOException(
                        Messages.JsonSampleImport_jsonParsingError);
            }
        }
        return resource;
    }

    /**
     * Returns a root element name based on the target path.
     * 
     * @param target
     *            The target path.
     * @return The root name.
     */
    private String getRootName(IPath target) {
        String name = JsonImportConvertor.ROOT;
        if (target != null) {
            IPath noExtension = target.removeFileExtension();
            if (noExtension != null) {
                String targetName = noExtension.lastSegment();
                if (targetName != null) {
                    name = targetName;
                }
            }
        }
        return name;

    }
}
