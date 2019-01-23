package com.tibco.xpd.om.modeler.subdiagram.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.ModelingAssistantProvider;
import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.SelectExistingElementForSourceOperation;
import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.SelectExistingElementForTargetOperation;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.DynamicOrgUnitEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.OrgUnitSubEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.OrganizationSubEditPart;
import com.tibco.xpd.om.modeler.subdiagram.part.Messages;
import com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelSubDiagramEditorPlugin;

/**
 * @generated
 */
public class OrganizationModelModelingAssistantProvider extends
        ModelingAssistantProvider {

    /**
     * @generated NOT
     */
    @Override
    public List getTypesForPopupBar(IAdaptable host) {
        IGraphicalEditPart editPart =
                (IGraphicalEditPart) host.getAdapter(IGraphicalEditPart.class);
        if (editPart instanceof OrgUnitSubEditPart) {
            List types = new ArrayList();
            types.add(OrganizationModelElementTypes.Position_2001);
            return types;
        }
        if (editPart instanceof OrganizationSubEditPart) {
            List types = new ArrayList();
            types.add(OrganizationModelElementTypes.OrgUnit_1001);

            /*
             * XPD-5300: If this is a dynamic Organization then Dynamic OrgUnit
             * is not allowed.
             */
            EObject eo = editPart.resolveSemanticElement();
            if (eo instanceof Organization && !((Organization) eo).isDynamic()) {
                types.add(OrganizationModelElementTypes.DynamicOrgUnit_1002);
            }
            return types;
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    @Override
    public List getRelTypesOnSource(IAdaptable source) {
        IGraphicalEditPart sourceEditPart =
                (IGraphicalEditPart) source
                        .getAdapter(IGraphicalEditPart.class);
        if (sourceEditPart instanceof OrgUnitSubEditPart) {
            List types = new ArrayList();
            types.add(OrganizationModelElementTypes.OrgUnitRelationship_3001);
            return types;
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated NOT
     */
    @Override
    public List getRelTypesOnTarget(IAdaptable target) {
        IGraphicalEditPart targetEditPart =
                (IGraphicalEditPart) target
                        .getAdapter(IGraphicalEditPart.class);
        if (targetEditPart instanceof OrgUnitSubEditPart
                || target instanceof DynamicOrgUnitEditPart) {
            List types = new ArrayList();
            types.add(OrganizationModelElementTypes.OrgUnitRelationship_3001);
            return types;
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated NOT
     */
    @Override
    public List getRelTypesOnSourceAndTarget(IAdaptable source,
            IAdaptable target) {
        IGraphicalEditPart sourceEditPart =
                (IGraphicalEditPart) source
                        .getAdapter(IGraphicalEditPart.class);
        IGraphicalEditPart targetEditPart =
                (IGraphicalEditPart) target
                        .getAdapter(IGraphicalEditPart.class);
        if (sourceEditPart instanceof OrgUnitSubEditPart) {
            List types = new ArrayList();
            // XPD-5300: Also allow dynamic org unit target
            if (targetEditPart instanceof OrgUnitSubEditPart
                    || targetEditPart instanceof DynamicOrgUnitEditPart) {
                types.add(OrganizationModelElementTypes.OrgUnitRelationship_3001);
            }
            return types;
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated NOT
     */
    @Override
    public List getTypesForSource(IAdaptable target,
            IElementType relationshipType) {
        IGraphicalEditPart targetEditPart =
                (IGraphicalEditPart) target
                        .getAdapter(IGraphicalEditPart.class);
        if (targetEditPart instanceof OrgUnitSubEditPart
                || targetEditPart instanceof DynamicOrgUnitEditPart) {
            List types = new ArrayList();
            if (relationshipType == OrganizationModelElementTypes.OrgUnitRelationship_3001) {
                types.add(OrganizationModelElementTypes.OrgUnit_1001);
            }
            return types;
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated NOT
     */
    @Override
    public List getTypesForTarget(IAdaptable source,
            IElementType relationshipType) {
        IGraphicalEditPart sourceEditPart =
                (IGraphicalEditPart) source
                        .getAdapter(IGraphicalEditPart.class);
        if (sourceEditPart instanceof OrgUnitSubEditPart) {
            List types = new ArrayList();
            if (relationshipType == OrganizationModelElementTypes.OrgUnitRelationship_3001) {
                types.add(OrganizationModelElementTypes.OrgUnit_1001);

                /*
                 * XPD-5300: If in a "static" Organization then allow creation
                 * of Dynamic OrgUnit.
                 */
                EObject eo = sourceEditPart.resolveSemanticElement();
                if (eo instanceof OrgUnit) {
                    Organization organization =
                            ((OrgUnit) eo).getOrganization();
                    if (organization != null && !organization.isDynamic()) {
                        types.add(OrganizationModelElementTypes.DynamicOrgUnit_1002);
                    }
                }

            }
            return types;
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    @Override
    public EObject selectExistingElementForSource(IAdaptable target,
            IElementType relationshipType) {
        return selectExistingElement(target,
                getTypesForSource(target, relationshipType));
    }

    /**
     * @generated
     */
    @Override
    public EObject selectExistingElementForTarget(IAdaptable source,
            IElementType relationshipType) {
        return selectExistingElement(source,
                getTypesForTarget(source, relationshipType));
    }

    /**
     * @generated
     */
    protected EObject selectExistingElement(IAdaptable host, Collection types) {
        if (types.isEmpty()) {
            return null;
        }
        IGraphicalEditPart editPart =
                (IGraphicalEditPart) host.getAdapter(IGraphicalEditPart.class);
        if (editPart == null) {
            return null;
        }
        Diagram diagram = (Diagram) editPart.getRoot().getContents().getModel();
        Collection elements = new HashSet();
        for (Iterator it = diagram.getElement().eAllContents(); it.hasNext();) {
            EObject element = (EObject) it.next();
            if (isApplicableElement(element, types)) {
                elements.add(element);
            }
        }
        if (elements.isEmpty()) {
            return null;
        }
        return selectElement((EObject[]) elements.toArray(new EObject[elements
                .size()]));
    }

    /**
     * @generated
     */
    protected boolean isApplicableElement(EObject element, Collection types) {
        IElementType type =
                ElementTypeRegistry.getInstance().getElementType(element);
        return types.contains(type);
    }

    /**
     * @generated
     */
    protected EObject selectElement(EObject[] elements) {
        Shell shell = Display.getCurrent().getActiveShell();
        ILabelProvider labelProvider =
                new AdapterFactoryLabelProvider(
                        OrganizationModelSubDiagramEditorPlugin.getInstance()
                                .getItemProvidersAdapterFactory());
        ElementListSelectionDialog dialog =
                new ElementListSelectionDialog(shell, labelProvider);
        dialog.setMessage(Messages.OrganizationModelModelingAssistantProviderMessage);
        dialog.setTitle(Messages.OrganizationModelModelingAssistantProviderTitle);
        dialog.setMultipleSelection(false);
        dialog.setElements(elements);
        EObject selected = null;
        if (dialog.open() == Window.OK) {
            selected = (EObject) dialog.getFirstResult();
        }
        return selected;
    }

    @Override
    public boolean provides(IOperation operation) {
        // Disable the "Existing Element" option on the pop-up.
        if (operation instanceof SelectExistingElementForSourceOperation) {
            return false;
        } else if (operation instanceof SelectExistingElementForTargetOperation) {
            return false;
        }

        return super.provides(operation);
    }
}
