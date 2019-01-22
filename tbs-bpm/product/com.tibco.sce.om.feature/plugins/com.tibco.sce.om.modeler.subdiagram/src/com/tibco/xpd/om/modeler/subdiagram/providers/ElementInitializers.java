package com.tibco.xpd.om.modeler.subdiagram.providers;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.modeler.subdiagram.expressions.OrganizationModelOCLFactory;
import com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelSubDiagramEditorPlugin;

/**
 * @generated
 */
public class ElementInitializers {
	/**
	 * @generated
	 */
	public static void init_OrgUnitRelationship_3001(
			OrgUnitRelationship instance) {
		try {
			Object value_0 = OrganizationModelOCLFactory.getExpression("true",
					OMPackage.eINSTANCE.getOrgUnitRelationship()).evaluate(
					instance);
			instance.setIsHierarchical(((Boolean) value_0).booleanValue());
		} catch (RuntimeException e) {
			OrganizationModelSubDiagramEditorPlugin.getInstance().logError(
					"Element initialization failed", e); //$NON-NLS-1$						
		}
	}

}
