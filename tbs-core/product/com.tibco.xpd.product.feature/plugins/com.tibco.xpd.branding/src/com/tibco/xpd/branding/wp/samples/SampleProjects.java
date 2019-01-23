/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.branding.wp.samples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeSet;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.ui.intro.IIntroSite;

import com.tibco.xpd.branding.BrandingPlugin;
import com.tibco.xpd.branding.wp.samples.postinstall.PostInstallAction;

class SampleProjects {
    final static SampleProjects INSTANCE = new SampleProjects();
    private final Collection<Project> projects;
    private final Map<String, Project> projectsById;

    private SampleProjects() {
	projects = new TreeSet<Project>(new ProjectComparator());
	projectsById = new HashMap<String, Project>();
    }

    void addProject(IConfigurationElement element) {
	Project p = new Project(element);
	projects.add(p);
	projectsById.put(p.getId(), p);
    }

    boolean contains(Project project) {
	return projects.contains(project);
    }

    Project getProject(String projectId) {
	return projectsById.get(projectId);
    }

    boolean containsId(String projectId) {
	return projectsById.containsKey(projectId);
    }

    Collection<Project> getProjects() {
	return projects;
    }

    enum InstallationType {
	ARCHIVE, WIZARD;
    }

    class ProjectComparator implements Comparator<Project> {
	public int compare(Project p1, Project p2) {
	    int result;
	    String order1 = p1.getOrder();
	    String order2 = p2.getOrder();
	    result = order1.compareTo(order2);
	    if (result == 0) {
		result = p1.getLabel().compareTo(p2.getLabel());
	    }
	    return result;
	}
    }

    class InstallStepComparator implements Comparator<PostInstallStep> {
	public int compare(PostInstallStep s1, PostInstallStep p2) {
	    int result;

	    String order1 = s1.getOrder();
	    String order2 = p2.getOrder();
	    result = order1.compareTo(order2);

	    return result;
	}
    }

    public class PostInstallStep {

	private String order;
	private final IConfigurationElement configurationElement;

	PostInstallStep(IConfigurationElement configurationElement) {
	    this.configurationElement = configurationElement;
	    String attribute = this.configurationElement.getAttribute("order"); //$NON-NLS-1$
	    if (attribute == null) {
		this.order = "ZZZZZZZZ"; //$NON-NLS-1$
	    } else {
		this.order = attribute;
	    }
	}

	String getOrder() {
	    return order;
	}

	public IConfigurationElement getConfigurationElement() {
	    return configurationElement;
	}

	public void execute(IIntroSite site) {
	    try {
		PostInstallAction result = (PostInstallAction) (configurationElement
		        .createExecutableExtension("class")); //$NON-NLS-1$

		Properties params = new Properties();
		IConfigurationElement[] children = configurationElement
		        .getChildren("parameter"); //$NON-NLS-1$
		for (IConfigurationElement propertyElement : children) {
		    String key = propertyElement.getAttribute("name"); //$NON-NLS-1$
		    String value = propertyElement.getAttribute("value"); //$NON-NLS-1$
		    params.setProperty(key, value);
		}
		result.run(site, params);
	    } catch (Exception e) {
		BrandingPlugin.logError(e);
	    }
	}
    }

    class Project {
	private static final String DEFAULT_ORDER_VALUE = "ZZZZZZ"; //$NON-NLS-1$

	private final String id;
	private final String label;
	private final String description;
	private final InstallationType installationType;
	private final String namespaceId;
	private final String archiveFile;
	private final String projectName;
	private final String order;
	private final String capability;

	private Collection<PostInstallStep> postInstallSteps;

	public Project(IConfigurationElement element) {
	    this.id = element.getAttribute("id"); //$NON-NLS-1$
	    this.label = element.getAttribute("label"); //$NON-NLS-1$
	    this.projectName = element.getAttribute("projectName"); //$NON-NLS-1$

	    if (element.getAttribute("welcomeCategory") == null) { //$NON-NLS-1$
		this.capability = "invalid"; //$NON-NLS-1$
	    } else {
		this.capability = element.getAttribute("welcomeCategory"); //$NON-NLS-1$
	    }

	    this.order = (element.getAttribute("order") == null) ? DEFAULT_ORDER_VALUE : element.getAttribute("order"); //$NON-NLS-1$ //$NON-NLS-2$
	    this.namespaceId = element.getNamespaceIdentifier();
	    IConfigurationElement[] children = element
		    .getChildren("description"); //$NON-NLS-1$
	    if (children.length > 0) {
		this.description = (children[0].getValue() == null) ? "" : children[0].getValue(); //$NON-NLS-1$
	    } else {
		this.description = ""; //$NON-NLS-1$
	    }

	    if (element.getChildren("archive").length != 0) { //$NON-NLS-1$
		IConfigurationElement archive = element.getChildren("archive")[0]; //$NON-NLS-1$
		installationType = InstallationType.ARCHIVE;
		archiveFile = archive.getAttribute("file"); //$NON-NLS-1$
	    } else {
		IConfigurationElement wizard = element.getChildren("wizard")[0]; //$NON-NLS-1$
		installationType = InstallationType.WIZARD;
		archiveFile = null;
	    }

	    postInstallSteps = revealPostInstallSteps(element);
	}

	private Collection<PostInstallStep> revealPostInstallSteps(
	        IConfigurationElement element) {
	    List<PostInstallStep> result = new ArrayList<PostInstallStep>();

	    IConfigurationElement[] steps = element.getChildren("post-install"); //$NON-NLS-1$
	    for (IConfigurationElement step : steps) {
		result.add(new PostInstallStep(step));
	    }

	    Collections.sort(result, new InstallStepComparator());

	    return result;
	}

	String getOrder() {
	    return order;
	}

	public String getNamespaceId() {
	    return namespaceId;
	}

	public InstallationType getInstallationType() {
	    return installationType;
	}

	public String getId() {
	    return id;
	}

	public String getLabel() {
	    return label;
	}

	public String getDescription() {
	    return description;
	}

	public String getCapability() {
	    return capability;
	}

	@Override
	public int hashCode() {
	    final int PRIME = 31;
	    int result = 1;
	    result = PRIME * result + ((id == null) ? 0 : id.hashCode());
	    return result;
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj)
		return true;
	    if (obj == null)
		return false;
	    if (getClass() != obj.getClass())
		return false;
	    final Project other = (Project) obj;
	    if (id == null) {
		if (other.id != null)
		    return false;
	    } else if (!id.equals(other.id))
		return false;
	    return true;
	}

	public String getArchiveFile() {
	    return archiveFile;
	}

	public String getProjectName() {
	    return projectName;
	}

	public Collection<PostInstallStep> getPostInstallSteps() {
	    return Collections.unmodifiableCollection(postInstallSteps);
	}
    }
}
