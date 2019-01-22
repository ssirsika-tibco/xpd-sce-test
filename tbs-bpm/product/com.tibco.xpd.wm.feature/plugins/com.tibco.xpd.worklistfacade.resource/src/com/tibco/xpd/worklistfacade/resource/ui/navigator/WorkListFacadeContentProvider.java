package com.tibco.xpd.worklistfacade.resource.ui.navigator;

import java.beans.PropertyChangeEvent;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.PartInitException;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.ui.projectexplorer.providers.AbstractWorkingCopySaveablesContentProvider;
import com.tibco.xpd.worklistfacade.resource.WorkListFacadeResourcePlugin;
import com.tibco.xpd.worklistfacade.resource.workingcopy.WorkListFacadeWorkingCopy;

/**
 * The Navigator Content provider for the WorkListFacade Asset. To be noted that
 * the Navigator/Project Explorer does not support the next level expansion of
 * the WorkListFacade file.
 * 
 * @author aprasad
 * 
 */
public class WorkListFacadeContentProvider extends
        AbstractWorkingCopySaveablesContentProvider {

    // Special folders handled by this content provider
    private static final String[] KINDS =
            new String[] { WorkListFacadeResourcePlugin.WLF_SPECIAL_FOLDER_KIND };

    public WorkListFacadeContentProvider() {

    }

    /*
     * (non-Javadoc) returns null, as the WorkListFacade file will not be
     * expanded to next level in the Project Explorer.
     * 
     * @see com.tibco.xpd.ui.projectexplorer.providers.
     * AbstractSpecialFoldersContentProvider#doGetChildren(java.lang.Object)
     */
    @Override
    protected Object[] doGetChildren(Object parentElement) {
        /*
         * WorkListFacade file is not expanded to next level in the Project
         * Explorer/Navigator, hence no implementation is required, return null.
         */
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.projectexplorer.providers.
     * AbstractSpecialFoldersContentProvider#doGetParent(java.lang.Object)
     */
    @Override
    protected Object doGetParent(Object element) {
        // As the facade file is does not expand to next level of elements, we
        // do not need EObject handling here.
        return null;
    }

    @Override
    protected void doGetPipelinedChildren(Object parent, Set theCurrentChildren) {

        // Register all WLF working copies for the given project - this will
        // allow the saveables for this working copies to be set
        if (parent instanceof IProject) {
            XpdProjectResourceFactory resourceFactory =
                    XpdResourcesPlugin.getDefault()
                            .getXpdProjectResourceFactory((IProject) parent);

            if (resourceFactory != null) {
                WorkingCopy[] workingCopies =
                        resourceFactory.getWorkingCopies();
                for (WorkingCopy wc : workingCopies) {
                    // Only interested in WorkListFacade working copies
                    if (wc instanceof WorkListFacadeWorkingCopy) {
                        registerWorkingCopy(wc);
                    }
                }
            }
        }

        /*
         * Expansion of WorkListFacade File to next level is not supported in
         * the Navigator/Project Explorer hence File is not handled here.
         */
    }

    @Override
    protected boolean doHasChildren(Object element) {
        boolean hasChildren = false;
        if (element instanceof IFile) {
            /*
             * Expansion of WorkListFacade File to next level is not supported
             * in the Navigator/Project Explorer hence return false.
             */
            hasChildren = false;
        } else {
            hasChildren = getChildren(element).length > 0;
        }
        return hasChildren;
    }

    @Override
    protected boolean doRefresh(WorkingCopy wc, PropertyChangeEvent evt) {
        return true;
    }

    @Override
    public String[] getSpecialFolderKindInclusion() {
        return KINDS;
    }

    /**
     * Open editor and select the given <code>EObject</code>. If the editor is
     * already open then this will bring the editor to the front and select the
     * object.
     * 
     * @param eObject
     *            object to select in editor.
     * @throws PartInitException
     */
    private void openEditor(EObject eObject) throws PartInitException {
    }

    @Override
    public void dispose() {
        super.dispose();
    }

}
