/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.resource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.part.IPageSite;

import com.tibco.xpd.processeditor.xpdl2.actions.DeleteSeparationOfDutiesAction;
import com.tibco.xpd.processeditor.xpdl2.actions.SelectTasksForSODGroupAction;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.SashSection;
import com.tibco.xpd.processeditor.xpdl2.util.ResourcePatternsActivityPicker;
import com.tibco.xpd.processeditor.xpdl2.util.SeparationOfDutiesCommandActions;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.projectexplorer.decorator.OverlayImageDescriptor;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.ProcessResourcePatterns;
import com.tibco.xpd.xpdExtension.SeparationOfDutiesActivities;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public abstract class AbstractSeparationOfDutiesSection extends
        AbstractTaskGroupSection implements SashSection {

    /** The menu manager. */
    private MenuManager sodMenuManager;

    /** The menu id. */
    private static final String SOD_MENU_ID = "separationOfDutiesContextMenu"; //$NON-NLS-1$

    /**
     * @param activityOrProcessClass
     */
    public AbstractSeparationOfDutiesSection(EClass activityOrProcessClass) {
        super(activityOrProcessClass);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.resource.AbstractTaskGroupSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Control control = super.doCreateControls(parent, toolkit);

        sodMenuManager = new MenuManager("", SOD_MENU_ID); //$NON-NLS-1$
        sodMenuManager.setRemoveAllWhenShown(true);
        sodMenuManager.addMenuListener(new IMenuListener() {
            @Override
            public void menuAboutToShow(IMenuManager manager) {
                fillSODContextMenu(manager);
            }
        });

        Menu sodContextMenu = sodMenuManager.createContextMenu(parent);
        resourcePatternsTreeViewer.getTree().setMenu(sodContextMenu);

        IWorkbenchSite site = getSite();
        if (site instanceof IViewSite) {
            IViewSite viewSite = (IViewSite) site;
            viewSite.registerContextMenu(SOD_MENU_ID,
                    sodMenuManager,
                    resourcePatternsTreeViewer);
        } else if (site instanceof IPageSite) {
            IPageSite pageSite = (IPageSite) site;
            pageSite.registerContextMenu(SOD_MENU_ID,
                    sodMenuManager,
                    resourcePatternsTreeViewer);
        }

        return control;
    }

    /**
     * @param manager
     *            The menu manager.
     */
    protected void fillSODContextMenu(IMenuManager manager) {
        manager.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
        manager.add(new SelectTasksForSODGroupAction(this));
        manager.add(new Separator());
        manager.add(new DeleteSeparationOfDutiesAction(this));
        manager.add(new GroupMarker("separationMenuGroup")); //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.resource.AbstractTaskGroupSection#createTaskGroup()
     * 
     * @return
     */
    @Override
    public Command createTaskGroup(Object input,
            ProcessResourcePatterns resourcePatterns, EditingDomain ed) {
        CompoundCommand cCmd =
                new CompoundCommand(getCreateNewTaskGroupMessage());
        Activity inputActivity = null;
        Process process = null;
        if (input instanceof Activity) {
            inputActivity = (Activity) input;
            process = inputActivity.getProcess();
        } else if (input instanceof Process) {
            process = (Process) input;
        }

        if (null != process) {
            Object[] selectedActivities =
                    ResourcePatternsActivityPicker.pickActivities(input);

            if (null != selectedActivities && selectedActivities.length > 0) {

                Set<Activity> pickedActivities = new HashSet<Activity>();
                for (Object o : selectedActivities) {
                    if (o instanceof Activity) {
                        pickedActivities.add((Activity) o);
                    }
                }

                /*
                 * When editing task group for slected activity ensure that that
                 * activity is always included in group.
                 */
                if (inputActivity != null) {
                    if (!pickedActivities.contains(inputActivity)) {
                        pickedActivities.add(inputActivity);
                    }
                }

                if (resourcePatterns == null) {
                    /**
                     * Add resource patterns if it's not already there.
                     */
                    resourcePatterns =
                            XpdExtensionFactory.eINSTANCE
                                    .createProcessResourcePatterns();
                    cCmd.append(Xpdl2ModelUtil.getAddOtherElementCommand(ed,
                            process,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ProcessResourcePatterns(),
                            resourcePatterns));
                }

                SeparationOfDutiesActivities sepOfDutiesActivities =
                        XpdExtensionFactory.eINSTANCE
                                .createSeparationOfDutiesActivities();
                sepOfDutiesActivities.setName(getCreateNewTaskGroupMessage());

                cCmd.append(AddCommand
                        .create(ed,
                                resourcePatterns,
                                XpdExtensionPackage.eINSTANCE
                                        .getProcessResourcePatterns_SeparationOfDutiesActivities(),
                                sepOfDutiesActivities));

                /**
                 * adding the activity reference to the model for the tasks
                 * selected from the picker
                 */
                for (Object object : pickedActivities) {
                    if (object instanceof Activity) {
                        Activity newActivity = (Activity) object;
                        ActivityRef activityRef =
                                XpdExtensionFactory.eINSTANCE
                                        .createActivityRef();
                        activityRef.setIdRef(newActivity.getId());
                        sepOfDutiesActivities.getActivityRef().add(activityRef);
                    }
                }

                sepOfDutiesActivities.setName(getGroupName(pickedActivities));
                return cCmd;
            }
        }

        return UnexecutableCommand.INSTANCE;
    }

    class SeparationOfDutiesContentProvider implements ITreeContentProvider {

        /**
         * @param inputElement
         * @return
         * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
         */
        @Override
        public Object[] getElements(Object inputElement) {
            Object[] elements = new Object[0];
            EStructuralFeature feature =
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_ProcessResourcePatterns();
            if (inputElement instanceof Activity) {
                Activity activity = (Activity) inputElement;
                Object other =
                        Xpdl2ModelUtil.getOtherElement(activity.getProcess(),
                                feature);
                if (other instanceof ProcessResourcePatterns) {
                    ProcessResourcePatterns patterns =
                            (ProcessResourcePatterns) other;
                    List<SeparationOfDutiesActivities> allGroups =
                            patterns.getSeparationOfDutiesActivities();

                    List<SeparationOfDutiesActivities> currentSelectionGroup =
                            new ArrayList<SeparationOfDutiesActivities>();

                    for (SeparationOfDutiesActivities separationOfDutiesActivities : allGroups) {
                        EList<ActivityRef> activityRefList =
                                separationOfDutiesActivities.getActivityRef();
                        for (ActivityRef activityRef : activityRefList) {
                            if (activityRef.getIdRef().equals(activity.getId())) {
                                currentSelectionGroup
                                        .add(separationOfDutiesActivities);
                                break;
                            }
                        }
                    }
                    elements = currentSelectionGroup.toArray();
                }
            } else if (inputElement instanceof Process) {
                // get all (do not filter on activity)
                Process process = (Process) inputElement;
                Object other = Xpdl2ModelUtil.getOtherElement(process, feature);
                if (other instanceof ProcessResourcePatterns) {
                    ProcessResourcePatterns patterns =
                            (ProcessResourcePatterns) other;
                    List<SeparationOfDutiesActivities> allGroups =
                            patterns.getSeparationOfDutiesActivities();
                    elements = allGroups.toArray();
                }
            }
            return elements;
        }

        /**
         * @param parentElement
         * @return
         * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
         */
        @Override
        public Object[] getChildren(Object parentElement) {
            Object[] children = new Object[0];
            if (parentElement instanceof Process) {
                Process process = (Process) parentElement;
                EStructuralFeature feature =
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ProcessResourcePatterns();
                Object other = Xpdl2ModelUtil.getOtherElement(process, feature);
                if (other instanceof ProcessResourcePatterns) {
                    ProcessResourcePatterns patterns =
                            (ProcessResourcePatterns) other;
                    List<?> separations =
                            patterns.getSeparationOfDutiesActivities();
                    children = separations.toArray();
                }
            } else if (parentElement instanceof SeparationOfDutiesActivities) {
                SeparationOfDutiesActivities separation =
                        (SeparationOfDutiesActivities) parentElement;
                List<?> refs = separation.getActivityRef();
                children = refs.toArray();
            }
            return children;
        }

        /**
         * @param element
         * @return
         * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
         */
        @Override
        public Object getParent(Object element) {
            Object parent = null;
            if (element instanceof ActivityRef) {
                ActivityRef ref = (ActivityRef) element;
                parent = ref.eContainer();
            } else if (parent instanceof SeparationOfDutiesActivities) {
                SeparationOfDutiesActivities separation =
                        (SeparationOfDutiesActivities) parent;
                parent = separation.eContainer().eContainer();
            }
            return parent;
        }

        /**
         * @param element
         * @return
         * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
         */
        @Override
        public boolean hasChildren(Object element) {
            boolean hasChildren = false;
            if (element instanceof Process) {
                Process process = (Process) element;
                EStructuralFeature feature =
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ProcessResourcePatterns();
                Object other = Xpdl2ModelUtil.getOtherElement(process, feature);
                if (other instanceof ProcessResourcePatterns) {
                    ProcessResourcePatterns patterns =
                            (ProcessResourcePatterns) other;
                    List<?> separations =
                            patterns.getSeparationOfDutiesActivities();
                    if (separations.size() != 0) {
                        hasChildren = true;
                    }
                }
            } else if (element instanceof SeparationOfDutiesActivities) {
                SeparationOfDutiesActivities separation =
                        (SeparationOfDutiesActivities) element;
                List<?> refs = separation.getActivityRef();
                if (refs.size() != 0) {
                    hasChildren = true;
                }
            }
            return hasChildren;
        }

        /**
         * 
         * @see org.eclipse.jface.viewers.IContentProvider#dispose()
         */
        @Override
        public void dispose() {
        }

        /**
         * @param viewer
         * @param oldInput
         * @param newInput
         * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }
    }

    static class SeparationOfDutiesLabelProvider implements ILabelProvider,
            ILabelDecorator {
        private static Image taskGroupImg = Xpdl2UiPlugin.getDefault()
                .getImageRegistry().get(Xpdl2UiPlugin.IMG_TASK_GROUP);

        private static Image errorImg = Xpdl2UiPlugin.getDefault()
                .getImageRegistry().get(Xpdl2UiPlugin.IMG_TASK_GROUP_ERROR);

        private static Image errorTaskGroupImg = new OverlayImageDescriptor(
                taskGroupImg.getImageData(), errorImg.getImageData())
                .createImage();

        /**
         * @param element
         * @return
         * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
         */
        @Override
        public Image getImage(Object element) {
            Image image = null;
            ImageRegistry ir = Xpdl2UiPlugin.getDefault().getImageRegistry();
            if (element instanceof Process) {
                image = WorkingCopyUtil.getImage((Process) element);
            } else if (element instanceof SeparationOfDutiesActivities) {
                image = taskGroupImg;
            } else if (element instanceof ActivityRef) {
                ActivityRef ref = (ActivityRef) element;
                Activity activity = ref.getActivity();
                if (activity != null) {
                    image = WorkingCopyUtil.getImage(activity);
                }
            }
            return image;
        }

        /**
         * @param element
         * @return
         * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
         */
        @Override
        public String getText(Object element) {
            String text = ""; //$NON-NLS-1$
            if (element instanceof NamedElement) {
                text = WorkingCopyUtil.getText((NamedElement) element);
            } else if (element instanceof ActivityRef) {
                Activity activity = ((ActivityRef) element).getActivity();
                if (activity != null) {
                    /*
                     * XPD-5562: Saket: Changing the way we fetch the activity
                     * text as WorkingCopyUtil.getText(activity) returns the
                     * string in "label(name)" format and just "label" for
                     * business analyst mode which is the standard behaviour.
                     */
                    text = WorkingCopyUtil.getText(activity);
                }
            }
            return text;
        }

        /**
         * @param listener
         * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
         */
        @Override
        public void addListener(ILabelProviderListener listener) {
        }

        /**
         * 
         * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
         */
        @Override
        public void dispose() {
        }

        /**
         * @param element
         * @param property
         * @return
         * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object,
         *      java.lang.String)
         */
        @Override
        public boolean isLabelProperty(Object element, String property) {
            return false;
        }

        /**
         * @param listener
         * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
         */
        @Override
        public void removeListener(ILabelProviderListener listener) {
        }

        /**
         * @see org.eclipse.jface.viewers.ILabelDecorator#decorateImage(org.eclipse.swt.graphics.Image,
         *      java.lang.Object)
         * 
         * @param image
         * @param element
         * @return
         */
        @Override
        public Image decorateImage(Image image, Object element) {
            /**
             * Check for validation problem errors and decorate if there are any
             */
            if (element instanceof EObject) {
                EObject eo = (EObject) element;
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(eo);
                if (wc != null) {
                    try {
                        int sev =
                                wc.getSeverity(eo, WorkingCopyUtil.getFile(eo));
                        if (sev == IMarker.SEVERITY_ERROR) {
                            return errorTaskGroupImg;
                        }
                    } catch (CoreException e) {
                    }
                }
            }
            return image;
        }

        /**
         * @see org.eclipse.jface.viewers.ILabelDecorator#decorateText(java.lang.String,
         *      java.lang.Object)
         * 
         * @param text
         * @param element
         * @return
         */
        @Override
        public String decorateText(String text, Object element) {
            return text;
        }

    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.resource.AbstractTaskGroupSection#addTaskGroup(org.eclipse.jface.viewers.ISelection,
     *      org.eclipse.emf.common.command.CompoundCommand)
     * 
     * @param selection
     * @param cmd
     * @return
     */
    @Override
    protected Command doEditGroup(ISelection selection) {
        CompoundCommand cmd = new CompoundCommand(getSelectTaskGroupMessage());

        SeparationOfDutiesCommandActions commandActions =
                new SeparationOfDutiesCommandActions(getInput());

        commandActions.addTasksToGroup(selection, cmd);

        return cmd;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.resource.AbstractTaskGroupSection#deleteTaskGroup(org.eclipse.jface.viewers.ISelection,
     *      org.eclipse.emf.common.command.CompoundCommand)
     * 
     * @param selection
     * @param cmd
     * @return
     */
    @Override
    protected Command doDeleteTaskGroupOrTaskFromGroup(ISelection selection) {
        CompoundCommand cmd = new CompoundCommand(getRemoveMessage());

        SeparationOfDutiesCommandActions commandActions =
                new SeparationOfDutiesCommandActions(getInput());

        commandActions.deleteTasksOrGroup(selection, cmd);
        return cmd;
    }

    @Override
    public IContentProvider getContentProvider() {
        return new SeparationOfDutiesContentProvider();
    }

    @Override
    public ILabelProvider getLabelProvider() {
        return new SeparationOfDutiesLabelProvider();
    }

    @Override
    public String getDescText() {
        return Messages.SeparationOfDutiesSashSection_SeparationOfDutiesSectionDescription;
    }

    @Override
    public String getCreateNewTaskGroupMessage() {
        return Messages.SeparationOfDutiesSashSection_CreateNewTaskGroupCommand;
    }

    @Override
    public String getRemoveMessage() {
        return Messages.SeparationOfDutiesSashSection_RemoveSeparationOfDutiesCommand;
    }

    @Override
    public String getSelectTaskGroupMessage() {
        return Messages.SeparationOfDutiesSashSection_SelectTaskGroupCommand;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.resource.AbstractTaskGroupSection#getStringFormat()
     * 
     * @return
     */
    @Override
    public String getStringFormat() {
        return Messages.SeparationOfDutiesCommand_SeparationOfDutiesMessage;
    }
}
