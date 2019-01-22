/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.resources;

import java.util.Collection;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.gmf.runtime.notation.Diagram;

import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;

public class OMResourceUtil {

    /**
     * @param project
     * @return
     */
    public static EList<SpecialFolder> getOMSpecialFolders(IProject project) {
        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        EList<SpecialFolder> omFolders =
                config.getSpecialFolders()
                        .getFoldersOfKind(OMResourcesActivator.OM_SPECIAL_FOLDER_KIND);
        return omFolders;
    }

    /**
     * @param element
     * @return text to search and display against when carrying out a
     *         quicksearch (c.f.
     *         <code>AbstractOmEditorQuickSearchLabelProvider</code>)
     */
    public static String getSearchLabelText(Object element) {

        String label = null;

        if (element instanceof NamedElement) {
            NamedElement nElement = (NamedElement) element;
            label = nElement.getDisplayName();
            if ((label == null) || (label.trim().length() == 0)) {
                label = nElement.getName();
            }
        }

        return label;

    }

    /**
     * Get the {@link Diagram} of the given semantic element.
     * 
     * @param semanticElement
     * @return
     */
    public static Diagram getDiagram(EObject semanticElement) {
        if (semanticElement != null) {
            ECrossReferenceAdapter adapter =
                    ECrossReferenceAdapter
                            .getCrossReferenceAdapter(semanticElement);
            if (adapter != null) {
                Collection<Setting> references =
                        adapter.getInverseReferences(semanticElement);

                if (references != null) {
                    for (Setting ref : references) {
                        if (ref.getEObject() instanceof Diagram) {
                            return (Diagram) ref.getEObject();
                        }
                    }
                }
            }
        }

        return null;
    }

}
