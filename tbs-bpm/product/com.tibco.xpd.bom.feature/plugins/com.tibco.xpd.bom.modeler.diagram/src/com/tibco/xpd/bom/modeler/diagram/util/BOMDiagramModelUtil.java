/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.bom.modeler.diagram.util;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.emf.core.util.CrossReferenceAdapter;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;

import com.tibco.xpd.bom.modeler.diagram.part.BOMDiagramEditorPlugin;

/**
 * BOM Diagram View Model utility methods
 * 
 * Sid ACE-8366 Added.
 * 
 * @author aallway
 * @since 12 Jul 2024
 */
public class BOMDiagramModelUtil
{
	public enum BOMDiagramViewModelType
	{
		clazz("1002"), // Class node view model
		enumeration("1004"); // Enumeration node view model
		// TBD Add further enums as required.

		private String modelTypeString;

		/**
		 * @param string
		 */
		BOMDiagramViewModelType(String modelTypeString)
		{
			this.modelTypeString = modelTypeString;
		}

		/**
		 * Get the Type String to be used in the actual View.type value seen in the BOM model
		 * 
		 * @return the modelTypeString
		 */
		public String getModelTypeString()
		{
			return modelTypeString;
		}

	}

	/**
	 * Get the diagram view element model from the given BOM semantic model element.
	 * 
	 * i.e. get the diagram 'Node' element from a UML Class object and so on.
	 * 
	 * @param domain
	 * @param elementFromModel
	 * 
	 * @return Diagram view element model or <code>null</code> if cannot be found.
	 */
	public static View getDiagramElementFromModel(EditingDomain domain, EObject elementFromModel)
	{
		ResourceSet resourceSet = domain.getResourceSet();

		if (resourceSet != null)
		{
			CrossReferenceAdapter referenceAdapter = CrossReferenceAdapter.getCrossReferenceAdapter(resourceSet);

			if (referenceAdapter != null)
			{
				Set< ? > referencers = referenceAdapter.getInverseReferencers(elementFromModel,
						NotationPackage.eINSTANCE.getView_Element(), null);

				for (Object ref : referencers)
				{
					if (ref instanceof View)
					{
						return (View) ref;

					}
				}
			}
		}
		return null;
	}

	/**
	 * Create a new Diagram View Node in the given contained.
	 * 
	 * @param container
	 * @param elementFromModel
	 *            Semantic model element such as a {@link Class} or {@link Enumeration}
	 * @param preferencesHint
	 * 
	 * @return new diagram view node model element
	 */
	public static Node createDiagramNode(View container, EObject elementFromModel, BOMDiagramViewModelType type)
	{
		return ViewService.createNode(container, elementFromModel, type.getModelTypeString(),
				BOMDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
	}

}
