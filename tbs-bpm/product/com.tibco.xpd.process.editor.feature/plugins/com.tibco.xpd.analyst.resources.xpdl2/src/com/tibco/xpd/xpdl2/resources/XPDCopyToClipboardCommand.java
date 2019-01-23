/**
 * XPDCopyToClipboardCommand.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.xpdl2.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AbstractOverrideableCommand;
import org.eclipse.emf.edit.command.CopyCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.action.ProcessClipboardUtils;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.utils.ActionUtil;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.utils.ActionUtil.ProjectReference;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * XPDCopyToClipboardCommand
 * <p>
 * The EMF CopyToClipboardCommand class includes copying of objects to clipboard
 * in the Undo Stack.
 * </p>
 * 
 * <p>
 * This means that if you do a Copy to clipboard, then undo, the clipboard will
 * contain the data prior to the copy in the clipboard.
 * </p>
 * 
 * <p>
 * This non-standard behaviour. So the XPD class prevents this from happening.
 * </p>
 * 
 * <p>
 * We could have just sub-classed CopyToClipboard and overridden doUndo() /
 * doRedo() to prevent undo / redo from having any affect. However
 * CopyToClipboard also keeps a copy of previous clipboard content which is a
 * bit of a waste as we will never use it.
 * </p>
 * 
 * <p>
 * Therefore this is a complete copy of EMF CopyToClipboardCommand code with the
 * undo / redo and keep previous clipboard content stuff removed.
 * </p>
 * 
 */
public class XPDCopyToClipboardCommand extends AbstractOverrideableCommand
        implements AbstractCommand.NonDirtying {

    /**
     * This caches the label.
     */
    protected static final String LABEL =
            Messages.XPDCopyToClipboardCommand_label;

    /**
     * This caches the description.
     */
    protected static final String DESCRIPTION =
            Messages.XPDCopyToClipboardCommand_message;

    private Collection<ProjectReference> filteredProjectsRef = null;

    /**
     * This constructs a command that copies the given collection of objects to
     * the clipboard.
     * 
     * @param domain
     * @param sourceObject
     * @param sourceContext
     *            The source context process / process package for the original
     *            objects (use {@link #calculateSourceContextObject(Collection)}
     *            on the original objects located in the source model to get the
     *            value for this.
     */
    public XPDCopyToClipboardCommand(EditingDomain domain,
            Collection collection, EObject sourceContext) {
        super(domain, LABEL, DESCRIPTION);
        // XPD-3033 filter project references and actual copy objects
        filteredProjectsRef = new ArrayList();
        Collection filteredCopyObjects = new ArrayList<Object>();
        ActionUtil.filterProjectReferencesAndOtherObjects(filteredCopyObjects,
                filteredProjectsRef,
                collection);
        this.sourceObjects = filteredCopyObjects;
        this.sourceContext = sourceContext;
    }

    /**
     * This is the collection of objects to be copied to the clipboard.
     */
    protected Collection sourceObjects;

    /**
     * Source context object (the process / package that these objects were
     * originally copied from.
     */
    private EObject sourceContext;

    /**
     * This is the command that does the actual copying.
     */
    protected Command copyCommand;

    /**
     * This creates a command that copies the given object to the clipboard.
     */
    public static Command create(EditingDomain domain, Object owner,
            EObject sourceContext) {
        return new XPDCopyToClipboardCommand(domain,
                Collections.singleton(owner), sourceContext);
    }

    /**
     * This returns the collection of objects to be copied to the clipboard.
     */
    public Collection getSourceObjects() {
        return sourceObjects;
    }

    @Override
    protected boolean prepare() {
        copyCommand = CopyCommand.create(domain, sourceObjects);
        return copyCommand.canExecute();
    }

    @Override
    public void doExecute() {

        copyCommand.execute();
        Collection copiedObjects = copyCommand.getResult();
        // XPD-3033 if required reference exists , add them to clip board.
        if (filteredProjectsRef != null && !filteredProjectsRef.isEmpty()) {
            copiedObjects.addAll(filteredProjectsRef);
        }
        ProcessClipboardUtils.setClipboard(copiedObjects, sourceContext, null);
    }

    /**
     * @param originalSourceObjects
     *            source objects in their original context (prior to creating
     *            copying)
     * 
     * @return Source process (if there is only one) else package for the given
     *         objects.
     */
    public static EObject calculateSourceContextObject(
            Collection originalSourceObjects) {
        /*
         * Get the source process or package being copied. If there is only one
         * process involved then this will be process else we will use package.
         */
        EObject sourceContext = null;

        Process sourceProcess = null;
        Package sourcePackage = null;

        for (Iterator iterator = originalSourceObjects.iterator(); iterator
                .hasNext();) {
            Object o = iterator.next();

            if (o instanceof EObject) {
                Process pr = Xpdl2ModelUtil.getProcess((EObject) o);

                if (pr != null) {
                    if (sourceProcess == null) {
                        /* First found! */
                        sourceProcess = pr;
                        sourcePackage = sourceProcess.getPackage();

                    } else if (!sourceProcess.equals(pr)) {
                        /*
                         * DIfferent process involved - just use the package. as
                         * context
                         */
                        sourceProcess = null;
                        break;
                    }
                } else {
                    /*
                     * Not content under a process, just use the package as
                     * context.
                     */
                    sourcePackage = Xpdl2ModelUtil.getPackage((EObject) o);
                    if (sourcePackage != null) {
                        sourceProcess = null;
                        break;
                    }
                }
            }
        }

        sourceContext = sourceProcess != null ? sourceProcess : sourcePackage;
        return sourceContext;
    }

    @Override
    public void doUndo() {
    }

    @Override
    public void doRedo() {
    }

    @Override
    public Collection doGetResult() {
        return copyCommand.getResult();
    }

    @Override
    public Collection doGetAffectedObjects() {
        return copyCommand.getAffectedObjects();
    }

    @Override
    public void doDispose() {
        if (copyCommand != null)
            copyCommand.dispose();
    }

    /**
     * This gives an abbreviated name using this object's own class' name,
     * without package qualification, followed by a space separated list of
     * <tt>field:value</tt> pairs.
     */
    @Override
    public String toString() {
        return String.format(Messages.XPDCopyToClipboardCommand_shortdesc,
                domain.toString(),
                sourceObjects.toString());
    }

}
