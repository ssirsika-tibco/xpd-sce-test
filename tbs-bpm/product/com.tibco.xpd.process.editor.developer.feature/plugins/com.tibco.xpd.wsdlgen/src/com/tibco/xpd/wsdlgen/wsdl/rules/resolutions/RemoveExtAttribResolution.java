/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.wsdlgen.wsdl.rules.resolutions;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.wsdl.Definition;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.wsdl.Activator;
import com.tibco.xpd.wsdl.ui.WsdlUIPlugin;

/**
 * Resolution for the issue which says that the file is not derived but contains
 * XPD ext attributes which considers it generated.
 * 
 * The quick fix removes the Ext attributes, and lets the WSDL to BOM builder
 * run through to generate the BOM artifacts.
 * 
 * @author rsomayaj
 * @since 3.3 (15 Mar 2010)
 */
public class RemoveExtAttribResolution extends AbstractWorkingCopyResolution {

    private static final Logger LOG =
            XpdResourcesPlugin.getDefault().getLogger();

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#run(org.eclipse.core.resources.IMarker)
     * 
     * @param marker
     * @throws ResolutionException
     * @throws PartInitException
     */
    @Override
    public void run(IMarker marker) {
        IEditorReference[] editorReferences =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage().getEditorReferences();
        IResource resource = marker.getResource();
        if (resource != null
                && resource.getFileExtension()
                        .equals(Activator.WSDL_FILE_EXTENSION)) {
            boolean editorIsOpen = false;
            for (IEditorReference editorReference : editorReferences) {
                try {
                    IEditorInput editorInput = editorReference.getEditorInput();
                    if (editorInput instanceof IFileEditorInput) {
                        IFileEditorInput fileEditorInput =
                                (IFileEditorInput) editorInput;
                        if (fileEditorInput.getFile().equals(resource)) {
                            editorIsOpen = true;

                            PlatformUI.getWorkbench()
                                    .getActiveWorkbenchWindow().getActivePage()
                                    .closeEditor(editorReference
                                            .getEditor(true),
                                            true);
                            super.run(marker);
                        }
                    }
                } catch (PartInitException e) {
                    LOG.error(e);
                } catch (ResolutionException e) {
                    LOG.error(e);
                }

            }

            if (!editorIsOpen) {
                try {
                    super.run(marker);
                } catch (ResolutionException e) {
                    LOG.error(e);
                }
            }
        }

    }

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     * @param target
     * @param marker
     * @return
     * @throws ResolutionException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Definition) {
            Definition defn = (Definition) target;

            if (null != defn.getElement()) {
                return new RemoveTibexAttribCommand(
                        (TransactionalEditingDomain) editingDomain, defn);
            }
        }
        return null;
    }

    class RemoveTibexAttribCommand extends RecordingCommand {

        private final Definition defn;

        private String tibexAttrib;

        private final EditingDomain editingDomain;

        /**
         * 
         */
        public RemoveTibexAttribCommand(
                TransactionalEditingDomain editingDomain, Definition defn) {
            super(editingDomain);
            this.editingDomain = editingDomain;
            this.defn = defn;

        }

        /**
         * @see org.eclipse.emf.common.command.AbstractCommand#canExecute()
         * 
         * @return
         */
        @Override
        public boolean canExecute() {
            return editingDomain != null;
        }

        /**
         * @see org.eclipse.emf.transaction.RecordingCommand#doExecute()
         * 
         */
        @Override
        protected void doExecute() {

            boolean shouldSave = false;
            /*
             * Remove tibex:XPDL
             */
            tibexAttrib =
                    defn.getElement()
                            .getAttribute(WsdlUIPlugin.TIBEX_XPDL_PLACEHOLDER);

            if (tibexAttrib != null) {
                defn.getElement()
                        .removeAttribute(WsdlUIPlugin.TIBEX_XPDL_PLACEHOLDER);
                shouldSave = true;
            }

            /*
             * Remove tibex:process
             */
            tibexAttrib =
                    defn.getElement().getAttribute(WsdlUIPlugin.TIBEX_PROCESS);

            if (tibexAttrib != null) {
                defn.getElement().removeAttribute(WsdlUIPlugin.TIBEX_PROCESS);
                shouldSave = true;
            }

            /*
             * Remove tibex:ServiceTask
             */
            tibexAttrib =
                    defn.getElement()
                            .getAttribute(WsdlUIPlugin.TIBEX_SERVICE_TASK);

            if (tibexAttrib != null) {
                defn.getElement()
                        .removeAttribute(WsdlUIPlugin.TIBEX_SERVICE_TASK);
                shouldSave = true;
            }
            if (shouldSave) {
                try {
                    defn.eResource().save(Collections.EMPTY_MAP);
                } catch (IOException e) {
                    LOG.error("Cannot save WSDL resource"); //$NON-NLS-1$
                }
            }

        }
    }
}
