package com.tibco.xpd.processeditor.xpdl2.packageeditor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.resources.projectconfig.impl.ProjectConfigImpl;
import com.tibco.xpd.resources.projectconfig.wc.ProjectConfigWorkingCopy;

/**
 * Destination change listener which listens to change in project config and
 * refreshes all the open Package editors which belong to the same project whose
 * config is changed.
 * 
 * 
 * @author kthombar
 * @since 22-Sep-2014
 */
@SuppressWarnings("restriction")
public class ProcessPkgRefreshOnProjectChangeListener implements
        PropertyChangeListener {

    private final static String PROPERTY_CHANGED = "CHANGED"; //$NON-NLS-1$

    @Override
    public void propertyChange(PropertyChangeEvent event) {

        String propertyName = event.getPropertyName();

        /*
         * Go ahead only if the event property is changed and the source is
         * Project Working copy
         */
        if (propertyName != null && propertyName.equals(PROPERTY_CHANGED)
                && event.getSource() instanceof ProjectConfigWorkingCopy) {

            ProjectConfigWorkingCopy projectConfigCopy =
                    (ProjectConfigWorkingCopy) event.getSource();

            EObject rootElement = projectConfigCopy.getRootElement();

            if (rootElement instanceof ProjectConfigImpl) {

                ProjectConfigImpl projectConfig =
                        (ProjectConfigImpl) rootElement;
                /*
                 * Get the project for which this Property change event was
                 * called.
                 */
                IProject projectWhoseConfigIsChanged =
                        projectConfig.getProject();

                /*
                 * Refresh all the open package editors which belong to the
                 * package under this project.
                 */
                refreshAllOpenPackageEditors(projectWhoseConfigIsChanged);
            }
        }
    }

    /**
     * Refreshes all the open Package Editors which belong to the Project which
     * is passed asa parameter.
     * 
     * @param projectWhoseConfigIsChanged
     *            the Project whose config if changed.
     */
    private void refreshAllOpenPackageEditors(
            IProject projectWhoseConfigIsChanged) {
        /*
         * get all the open workbench windows
         */
        if (!PlatformUI.isWorkbenchRunning()) {
            return;
        }

        IWorkbenchWindow[] ww = PlatformUI.getWorkbench().getWorkbenchWindows();

        for (int i = 0; i < ww.length; i++) {

            /* Get all the pages in this workbench window */
            IWorkbenchPage[] ps = ww[i].getPages();

            for (int j = 0; j < ps.length; j++) {

                /*
                 * Get all the references to open editors in this page.
                 */
                IEditorReference[] es = ps[j].getEditorReferences();

                for (int k = 0; k < es.length; k++) {
                    /*
                     * Get the Editor part for the reference
                     */
                    final IEditorPart part = (IEditorPart) es[k].getPart(false);

                    if (part instanceof PackageEditor) {
                        /*
                         * Get the package editor from the editor part
                         */
                        final PackageEditor pkgEditor = (PackageEditor) part;

                        /*
                         * Get the parent Project of the open package editor.
                         */
                        IProject parentProjectOfOpenPkgEditor =
                                pkgEditor.getProject();

                        if (parentProjectOfOpenPkgEditor != null
                                && parentProjectOfOpenPkgEditor
                                        .equals(projectWhoseConfigIsChanged)) {
                            /*
                             * If the parent project of the open package editor
                             * is the same project whose destination is changed
                             * then refresh the package editor.
                             */
                            if (Display.getDefault() != null) {
                                Display.getDefault().asyncExec(new Runnable() {

                                    @Override
                                    public void run() {
                                        pkgEditor.refresh();
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }
    }
}
