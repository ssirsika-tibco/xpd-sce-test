/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui.internal.editor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.EMFCommandOperation;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.action.actions.global.ClipboardContentsHelper;
import org.eclipse.gmf.runtime.common.ui.action.actions.global.ClipboardManager;
import org.eclipse.gmf.runtime.common.ui.action.global.GlobalActionId;
import org.eclipse.gmf.runtime.common.ui.services.action.global.AbstractGlobalActionHandler;
import org.eclipse.gmf.runtime.common.ui.services.action.global.IGlobalActionContext;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.rest.schema.ui.internal.Messages;

/**
 * Handler to support copy and paste actions from the JsonSchemaEditor.
 * 
 * @author nwilson
 * @since 19 Feb 2015
 */
public class CopyPasteActionHandler extends AbstractGlobalActionHandler {

    /**
     * @see org.eclipse.gmf.runtime.common.ui.services.action.global.IGlobalActionHandler#canHandle(org.eclipse.gmf.runtime.common.ui.services.action.global.IGlobalActionContext)
     * 
     * @param context
     *            The action context.
     * @return true if we can handle this context.
     */
    @Override
    public boolean canHandle(IGlobalActionContext context) {
        boolean canHandle = false;
        if (GlobalActionId.CUT.equals(context.getActionId())
                || GlobalActionId.COPY.equals(context.getActionId())) {
            if (!context.getSelection().isEmpty()) {
                canHandle = true;
            }
        } else if (GlobalActionId.PASTE.equals(context.getActionId())) {
            canHandle =
                    ClipboardManager.getInstance()
                            .doesClipboardHaveData(LocalTransfer.getInstance(),
                                    ClipboardContentsHelper.getInstance());
        }
        return canHandle;
    }

    /**
     * @see org.eclipse.gmf.runtime.common.ui.services.action.global.IGlobalActionHandler#getCommand(org.eclipse.gmf.runtime.common.ui.services.action.global.IGlobalActionContext)
     * 
     * @param context
     *            The action context.
     * @return The command to perform the action.
     */
    @Override
    public ICommand getCommand(IGlobalActionContext context) {
        CompositeCommand cc = null;
        boolean isCopy = GlobalActionId.COPY.equals(context.getActionId());
        JsonSchemaEditor editor = getEditor(context);
        TransactionalEditingDomain ed =
                (TransactionalEditingDomain) editor.getEditingDomain();

        if (GlobalActionId.CUT.equals(context.getActionId()) || isCopy) {
            /*
             * get all the cut or copied elements
             */
            List<Object> curOrCopiedElements = getAllSelectedElements(context);

            if (!curOrCopiedElements.isEmpty()) {

                if (GlobalActionId.CUT.equals(context.getActionId())
                        && isRootTypeSelected(curOrCopiedElements)) {
                    /*
                     * do not allow CUT action on Root Type.
                     */
                    return null;
                }

                CopySource copySource =
                        new CopySource(curOrCopiedElements, isCopy);
                /*
                 * add the elements to clipboard.
                 */
                ClipboardManager cm = ClipboardManager.getInstance();
                cm.addToCache(copySource, LocalTransfer.getInstance());
                cm.flushCacheToClipboard();
                if (!isCopy) {
                    cc =
                            new CompositeCommand(
                                    Messages.CopyPasteActionHandler_CutAction);

                    /*
                     * as this was a cut action , remove all the selected
                     * elements
                     */
                    for (Object elementCut : curOrCopiedElements) {

                        Command command = editor.getRemoveCommand(elementCut);
                        if (command != null) {
                            cc.add(new EMFCommandOperation(ed, command));
                        }
                    }
                }
            }
        } else if (GlobalActionId.PASTE.equals(context.getActionId())) {
            Object clipboardContent =
                    ClipboardManager.getInstance()
                            .getClipboardContents(LocalTransfer.getInstance(),
                                    ClipboardContentsHelper.getInstance());
            if (clipboardContent instanceof CopySource) {
                CopySource copySource = (CopySource) clipboardContent;
                /*
                 * get all the elements to paste.
                 */
                List<Object> objsToPaste = copySource.getSource();

                cc =
                        new CompositeCommand(
                                Messages.CopyPasteActionHandler_PasteAction);

                for (Object source : objsToPaste) {

                    /*
                     * Sid XPD-7681 - Think we need to create a copy of pasteed
                     * sources ALWAYS (even for cut and paste) if we don't then
                     * a second PASTE fails because we're trying to add a second
                     * copy of SAME EMF object to model (and you get Operation
                     * Is Invalid on second paste after a cut)
                     */
                    // if (copySource.isCopy()) {
                    source = copyOf(source, editor);
                    // }

                    /*
                     * get the paste target.
                     */
                    Object target = getPasteTarget(context);

                    if (target != null) {
                        /*
                         * XPD-7802: no need to create unique names in Add
                         * command the copy-paste handler assigns new names as
                         * 'Copy_Of' old names.
                         */
                        Command command =
                                editor.getAddCommand(source, target, false);
                        if (command != null) {
                            cc.add(new EMFCommandOperation(ed, command));
                        }
                    }
                }
            }
        }
        return cc;
    }

    /**
     * Creates a copy of a UmlTreePropertyNode, Propery or Class.
     * 
     * @param source
     *            The object to copy.
     * @param editor
     * @return A copy of the object.
     */
    private Object copyOf(Object source, JsonSchemaEditor editor) {
        Object copy = null;
        if (source instanceof UmlTreePropertyNode) {
            UmlTreePropertyNode original = (UmlTreePropertyNode) source;

            Property copiedProperty = EcoreUtil.copy(original.getItem());
            copiedProperty.setName(getUniqueCopyOfName(copiedProperty,
                    editor,
                    copiedProperty.getName()));
            copy =
                    new UmlTreePropertyNode(original.getParent(),
                            copiedProperty);
        } else if (source instanceof Property) {
            Property copiedProperty = EcoreUtil.copy((Property) source);
            copiedProperty.setName(getUniqueCopyOfName(copiedProperty,
                    editor,
                    copiedProperty.getName()));
            copy = copiedProperty;
        } else if (source instanceof Class) {
            Class cls = EcoreUtil.copy((Class) source);
            cls.setName(getUniqueCopyOfName(cls, editor, cls.getName()));
            // Remove root annotation so we don't end up with two roots.
            cls.getEAnnotations().clear();
            copy = cls;
        }
        return copy;
    }

    /**
     * 
     * @param selection
     * @return <code>true</code> if the root Json Type is selected, elsre return
     *         <code>false</code>
     */
    private boolean isRootTypeSelected(List<Object> curOrCopiedElements) {

        for (Object object : curOrCopiedElements) {
            if (object instanceof Class) {

                Class cls = (Class) object;
                if (cls.getEAnnotation("root") != null) { //$NON-NLS-1$

                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 
     * @param copiedObj
     * @param editor
     * @param originalName
     * @return unique name of the json object being copy pasted.
     */
    private String getUniqueCopyOfName(Object copiedObj,
            JsonSchemaEditor editor, String originalName) {

        Set<String> existingNames = new HashSet<String>();

        EObject input = editor.getInput();

        if (input instanceof Package) {
            Package pkg = (Package) input;

            if (copiedObj instanceof Property) {
                for (PackageableElement element : pkg.getPackagedElements()) {
                    if (element instanceof Class) {
                        Class cls = (Class) element;
                        for (Property property : cls.getOwnedAttributes()) {
                            existingNames.add(property.getName());

                        }
                    }
                }
            } else if (copiedObj instanceof Class) {
                for (PackageableElement element : pkg.getPackagedElements()) {
                    if (element instanceof Class) {
                        existingNames.add(((Class) element).getName());
                    }
                }
            }
        }

        String finalName = originalName;
        if (existingNames.contains(finalName)) {
            // Try Copy_Of_ first.
            finalName =
                    String.format(Messages.CopyPasteActionHandler_CopyOf_name,
                            finalName);

            int idx = 1;

            while (existingNames.contains(finalName)) {
                // Already a CopyOf... use a sequence number.
                idx++;
                finalName =
                        String.format(Messages.CopyPasteActionHandler_CopyNOf_name,
                                idx)
                                + originalName;
            }
        }

        return finalName;
    }

    /**
     * @param context
     * @return
     */
    private JsonSchemaEditor getEditor(IGlobalActionContext context) {
        JsonSchemaEditor editor = null;
        IWorkbenchPart part = context.getActivePart();
        if (part instanceof JsonSchemaEditorPart) {
            JsonSchemaEditorPart jsep = (JsonSchemaEditorPart) part;
            editor = jsep.getJsonSchemaEditor();
        }
        return editor;
    }

    /**
     * 
     * @param context
     * @return List of all the selected elements, return empty list otherwise.
     */
    private List<Object> getAllSelectedElements(IGlobalActionContext context) {
        List<Object> selectedElements = new ArrayList<Object>();
        ISelection selection = context.getSelection();
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection ss = (IStructuredSelection) selection;
            for (Iterator<?> i = ss.iterator(); i.hasNext();) {

                selectedElements.add(i.next());

            }
        }
        return selectedElements;
    }

    /**
     * @param context
     * @return the target of the paste only if a single element is selected,
     *         else returns <code>null</code>
     */
    private Object getPasteTarget(IGlobalActionContext context) {
        Object target = null;
        ISelection selection = context.getSelection();
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection ss = (IStructuredSelection) selection;
            if (ss.size() == 1) {
                target = ss.getFirstElement();
            }
        }
        return target;
    }

    /**
     * Pojo to hold source object(s) and identify if this is a copy or cut.
     * 
     * @author nwilson
     * @since 25 Aug 2015
     */
    class CopySource {

        /*
         * XPD-7802: making this a list so that we can copy/cut paste multiple
         * elements.
         */
        /**
         * The source object.
         */
        private List<Object> sourceElements;

        /**
         * True for copy, false for cut.
         */
        private boolean copy;

        /**
         * @param source
         *            The source object.
         * @param copy
         *            True for copy, false for cut.
         */
        public CopySource(List<Object> sourceElements, boolean copy) {
            this.sourceElements = sourceElements;
            this.copy = copy;
        }

        /**
         * @return The source object.
         */
        public List<Object> getSource() {
            return sourceElements;
        }

        /**
         * @return True for copy, false for cut.
         */
        public boolean isCopy() {
            return copy;
        }
    }
}
