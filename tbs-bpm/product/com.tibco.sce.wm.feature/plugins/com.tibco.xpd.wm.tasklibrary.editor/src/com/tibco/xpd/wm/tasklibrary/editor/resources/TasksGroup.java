package com.tibco.xpd.wm.tasklibrary.editor.resources;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.AbstractAssetGroup;
import com.tibco.xpd.wm.tasklibrary.editor.TaskLibraryEditorContstants;
import com.tibco.xpd.wm.tasklibrary.editor.TaskLibraryEditorPlugin;
import com.tibco.xpd.wm.tasklibrary.editor.internal.Messages;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Tasks (logical) group of the Task Library in project explorer
 * 
 * @author aallway
 * @since 3.2
 * 
 */
public class TasksGroup extends AbstractAssetGroup {

    private static final String TITLE = Messages.TasksGroup_Tasks_label;

    /**
     * Default constructor
     * 
     * @param parent
     */
    public TasksGroup(EObject parent) {
        super(parent);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.BpmArtefactGroup
     * #getFeature()
     */
    @Override
    public EStructuralFeature getFeature() {
        return Xpdl2Package.eINSTANCE.getFlowContainer_Activities();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.BpmArtefactGroup
     * #getText()
     */
    @Override
    public String getText() {
        return TITLE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.BpmArtefactGroup
     * #getImage()
     */
    @Override
    public Image getImage() {
        return TaskLibraryEditorPlugin.getDefault().getImageRegistry()
                .get(TaskLibraryEditorContstants.ICON_TASKGROUP);
    }

    @Override
    public EClass getReferenceType() {
        return Xpdl2Package.eINSTANCE.getActivity();
    }

}
