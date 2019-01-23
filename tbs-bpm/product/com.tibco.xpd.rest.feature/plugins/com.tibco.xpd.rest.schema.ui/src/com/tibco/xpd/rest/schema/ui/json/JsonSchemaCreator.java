/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Helper class to create JSON Schema UML EMF model files.
 * 
 * @author nwilson
 * @since 13 Jan 2015
 */
public class JsonSchemaCreator {

    private IPath path;

    private Resource resource;

    /**
     * @param path
     *            The JSON Schema file path.
     */
    public JsonSchemaCreator(IPath path) {
        this.path = path;
    }

    /**
     * Used to obtain a runnable opration that will use the default
     * NewJsonFileConvertor class to create a new JSON Schema file with just a
     * default root Class.
     * 
     * @return A runnable operation to create the JSON Schema file.
     */
    public IRunnableWithProgress getCreateOperation() {
        return getCreateOperation(new NewJsonFileConvertor());
    }

    /**
     * Used to obtain a runnable opration that will use the provided convertor
     * class to create a new JSON Schema file. This is used to populate the
     * schema with data from an imported resource.
     * 
     * @param convertor
     *            The convertor to use to create the JSON Schema contents.
     * @return A runnable operation to create the JSON Schema file.
     */
    public IRunnableWithProgress getCreateOperation(
            final JsonImportConvertor convertor) {
        IRunnableWithProgress op = new WorkspaceModifyOperation(null) {

            @Override
            protected void execute(IProgressMonitor monitor)
                    throws CoreException, InterruptedException {
                TransactionalEditingDomain ed =
                        XpdResourcesPlugin.getDefault().getEditingDomain();
                URI uri = URI.createPlatformResourceURI(path.toString(), true);
                final ResourceSet rs = ed.getResourceSet();
                resource = rs.createResource(uri);
                RecordingCommand cmd = new RecordingCommand(ed) {

                    @Override
                    protected void doExecute() {
                        // Load primitives UML into resource set.
                        URI uri =
                                URI.createURI(PrimitivesUtil.BOM_PRIMITIVE_TYPES_FACETS_PROFILE_URI);
                        Resource primitives = rs.getResource(uri, true);
                        Profile primitivesProfile =
                                (Profile) EcoreUtil.getObjectByType(primitives
                                        .getContents(),
                                        UMLPackage.Literals.PROFILE);

                        Package root = UMLFactory.eINSTANCE.createPackage();
                        root.applyProfile(primitivesProfile);

                        resource.getContents().add(root);
                        if (convertor != null) {
                            convertor.convert(rs, root);
                        }
                        Map<String, String> saveOptions = new HashMap<>();
                        saveOptions.put(XMLResource.OPTION_ENCODING, "UTF-8"); //$NON-NLS-1$
                        saveOptions
                                .put(Resource.OPTION_SAVE_ONLY_IF_CHANGED,
                                        Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);
                        try {
                            resource.save(saveOptions);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                if (cmd.canExecute()) {
                    ed.getCommandStack().execute(cmd);
                }
            }
        };
        return op;
    }

    public Resource getResource() {
        return resource;
    }
}
