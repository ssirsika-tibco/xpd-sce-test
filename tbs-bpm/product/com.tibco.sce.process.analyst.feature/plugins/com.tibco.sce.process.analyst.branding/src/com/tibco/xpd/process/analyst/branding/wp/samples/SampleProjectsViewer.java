/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.process.analyst.branding.wp.samples;

import java.util.Collection;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.intro.config.IIntroContentProvider;
import org.eclipse.ui.intro.config.IIntroContentProviderSite;

import com.tibco.xpd.process.analyst.branding.wp.samples.SampleProjects.Project;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

public class SampleProjectsViewer implements IIntroContentProvider {

	public static final String PLUGIN_ID = "com.tibco.xpd.process.analyst.branding"; //$NON-NLS-1$
	private IIntroContentProviderSite site;
	private static Logger logger=LoggerFactory.createLogger(PLUGIN_ID);

	public void init(IIntroContentProviderSite site){
		this.site = site;
		IExtensionRegistry registry=Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = registry.getExtensionPoint(PLUGIN_ID+".sampleProjects"); //$NON-NLS-1$
		IConfigurationElement[] configurationElements = extensionPoint.getConfigurationElements();
		for (IConfigurationElement element : configurationElements) {
//			String label= element.getAttribute("label");
			String projectId = element.getAttribute("id"); //$NON-NLS-1$
			if(!SampleProjects.INSTANCE.containsId(projectId)){
//				String contributorName = element.getNamespaceIdentifier();
//				logger.error("Not unique id found for contributed sample project: "+projectId+". Ignoring project labeled \""+label+"\" contributed by: "+contributorName+".");
//				continue;
				SampleProjects.INSTANCE.addProject(element);
			}
		}
	};

	public void createContent(String id, java.io.PrintWriter out) {

		Collection<Project> projects = SampleProjects.INSTANCE.getProjects();

		out.println("<ul id=\"eclipse-news\">"); //$NON-NLS-1$
		for (Project project : projects) {
			String projectId = project.getId();
			String projectLabel = project.getLabel();
			out.print("<li>"); //$NON-NLS-1$
			out.print("<a class=\"topicList\" href=\""); //$NON-NLS-1$
			out.print(createLink(projectId));
			out.print("\">"); //$NON-NLS-1$
			out.print(projectLabel);
			out.print("</a>"); //$NON-NLS-1$
			out.print("</li>"); //$NON-NLS-1$
		}
		out.println("</ul>"); //$NON-NLS-1$
	};

	private String createLink(String projectId){
		StringBuilder sb=new StringBuilder();
		sb.append("http://org.eclipse.ui.intro/runAction?pluginId="); //$NON-NLS-1$
		sb.append(PLUGIN_ID);
		sb.append("&amp;class="); //$NON-NLS-1$
		sb.append(SampleProjectsIntroAction.class.getName());
		sb.append("&amp;projectId="); //$NON-NLS-1$
		sb.append(projectId);
		return sb.toString();
	}

	public void dispose() {};
	public void createContent(String id, Composite parent, FormToolkit toolkit){};
}
