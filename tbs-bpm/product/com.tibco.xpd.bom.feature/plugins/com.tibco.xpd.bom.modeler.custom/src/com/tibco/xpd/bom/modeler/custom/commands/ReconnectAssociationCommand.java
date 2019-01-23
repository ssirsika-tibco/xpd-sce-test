/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.commands;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;


/**
 * Command to reconnect the association using the Properties pickers.
 * 
 * @author rsomayaj
 * 
 */
public class ReconnectAssociationCommand extends RecordingCommand {

	public enum AssociationTypeKind {
		SOURCE, TARGET;
	}

	private Class newType;

	private Association association;

	private AssociationTypeKind associationKind;

	public ReconnectAssociationCommand(TransactionalEditingDomain domain) {
		super(domain);
	}

	public ReconnectAssociationCommand(
			TransactionalEditingDomain editingDomain,
			org.eclipse.uml2.uml.Class newType, Association association,
			AssociationTypeKind associationType) {
		this(editingDomain);

		this.newType = newType;

		this.association = association;

		this.associationKind = associationType;
	}

	@Override
	protected void doExecute() {
		// semantic part
		Class sourceType = null, targetType = null;
		Property sourceEnd = null, targetEnd = null;
		org.eclipse.uml2.uml.Package ownerPackage = (org.eclipse.uml2.uml.Package) association
				.getOwner();
		List<Property> memberEnds = association.getMemberEnds();
		// Assuming the first member end will always be the source end.
		sourceEnd = memberEnds.get(0);
		sourceType = (Class) sourceEnd.getType();

		targetEnd = memberEnds.get(1);
		targetType = (Class) targetEnd.getType();
		// if the source connector end has been changed.
		if (AssociationTypeKind.SOURCE.equals(associationKind)) {
			// if bidirectional
			if (UML2ModelUtil.isAssociationBiDirectional(association)
					|| sourceType.getOwnedAttributes().contains(targetEnd)) {
				sourceType.getOwnedAttributes().remove(targetEnd);
				newType.getOwnedAttributes().add(targetEnd);
			}

			sourceEnd.setType(newType);

		} else {
			// if bidirectional
			if (UML2ModelUtil.isAssociationBiDirectional(association)
					|| targetType.getOwnedAttributes().contains(sourceEnd)) {
				targetType.getOwnedAttributes().remove(sourceEnd);
				newType.getOwnedAttributes().add(sourceEnd);
			}
			targetEnd.setType(newType);

		}
		changeView();

	}

	private boolean doesContain(Property property) {
		return association.getOwnedEnds().contains(property);
	}

	private void changeView() {
		EStructuralFeature feature = null;
		if (AssociationTypeKind.SOURCE.equals(associationKind))
			feature = NotationPackage.eINSTANCE.getEdge_Source();
		else
			feature = NotationPackage.eINSTANCE.getEdge_Target();
		if (!getReferencingViews(association).isEmpty()
				&& !getReferencingViews(newType).isEmpty())
			ViewUtil.setPropertyValue((View) getReferencingViews(association)
					.iterator().next(), feature, NotationPackage.eINSTANCE
					.getEdge(), getReferencingViews(newType).iterator().next());
	}

	private Collection getReferencingViews(EObject object) {
		return (EMFCoreUtil
				.getReferencers(object,
						new EReference[] { NotationPackage.eINSTANCE
								.getView_Element() }));
	}
}
