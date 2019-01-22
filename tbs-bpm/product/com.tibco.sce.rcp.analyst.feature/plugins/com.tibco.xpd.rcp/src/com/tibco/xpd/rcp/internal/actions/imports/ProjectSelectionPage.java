package com.tibco.xpd.rcp.internal.actions.imports;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.models.ProcessModelProvider;
import com.tibco.xpd.rcp.internal.overview.WorkspaceResourceLabelProvider;
import com.tibco.xpd.rcp.internal.resources.IRCPContainer;
import com.tibco.xpd.rcp.internal.resources.ProjectResource;

/**
 * Wizard page to select the target project, or create a new project.
 * 
 */
public class ProjectSelectionPage extends WizardPage {

    private final IRCPContainer resource;

    private IStructuredSelection selection;

    public ProjectSelectionPage(IRCPContainer resource) {
        super("select-project"); //$NON-NLS-1$
        this.resource = resource;
        setTitle(Messages.ProjectSelectionPage_title);
        setMessage(Messages.ProjectSelectionPage_shortdesc);
    }

    /**
     * Get the selected project resource.
     * 
     * @return
     */
    public ProjectResource getSelection() {
        return (ProjectResource) (selection != null ? selection
                .getFirstElement() : null);
    }

    /**
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     */
    @Override
    public void createControl(Composite parent) {
        Group root = new Group(parent, SWT.NONE);
        root.setText(Messages.ProjectSelectionPage_selectProject_group_label);
        FillLayout layout = new FillLayout();
        layout.marginHeight = 5;
        layout.marginWidth = 5;
        root.setLayout(layout);

        List<ProjectResource> businessProcessProjects =
                getBusinessProcessProjects();

        if (!businessProcessProjects.isEmpty()) {
            createProjectListViewer(root, businessProcessProjects);
            setPageComplete(true);
        } else {
            Composite section = new Composite(root, SWT.NONE);
            layout = new FillLayout();
            layout.marginHeight = 20;
            layout.marginWidth = 20;
            section.setLayout(layout);

            // Don't allow project selection as there are no suitable
            // projects
            Label lbl = new Label(section, SWT.WRAP);
            lbl.setText(Messages.ProjectSelectionPage_noBusinessProcessProject_message);

            setPageComplete(false);
        }

        setControl(root);
    }

    /**
     * Get all project resourcess that have the Business Process asset
     * configured.
     * 
     * @return
     */
    private List<ProjectResource> getBusinessProcessProjects() {
        List<ProjectResource> projects = new ArrayList<ProjectResource>();
        if (resource != null) {
            ProcessModelProvider provider =
                    new ProcessModelProvider(PlatformUI.getWorkbench()
                            .getActiveWorkbenchWindow());
            for (ProjectResource prRes : resource.getProjectResources()) {
                if (provider.hasActions(prRes.getProject())) {
                    projects.add(prRes);
                }
            }

            provider.dispose();
        }

        return projects;
    }

    /**
     * Create the project selection list.
     * 
     * @param root
     * @param projects
     * @return
     */
    private TableViewer createProjectListViewer(Composite root,
            List<ProjectResource> projects) {

        TableViewer viewer =
                new TableViewer(root, SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
        viewer.setContentProvider(new ArrayContentProvider());
        viewer.setLabelProvider(new WorkspaceResourceLabelProvider());
        viewer.setInput(projects.toArray());
        viewer.setSorter(new ViewerSorter() {
            @Override
            public int compare(Viewer viewer, Object e1, Object e2) {
                if (e1 instanceof ProjectResource
                        && e2 instanceof ProjectResource) {
                    return ((ProjectResource) e1).getName()
                            .compareTo(((ProjectResource) e2).getName());
                }
                return super.compare(viewer, e1, e2);
            }
        });

        if (!projects.isEmpty()) {
            selection = new StructuredSelection(projects.get(0));
            viewer.setSelection(selection);
        }

        viewer.addSelectionChangedListener(new ISelectionChangedListener() {

            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                selection = (IStructuredSelection) event.getSelection();
            }
        });

        return viewer;

    }

}