package com.tibco.xpd.om.modeler.subdiagram.part;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gmf.runtime.common.ui.services.action.contributionitem.ContributionItemService;
import org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds;
import org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction;
import org.eclipse.gmf.runtime.diagram.ui.providers.DiagramContextMenuProvider;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.om.core.om.Feature;
import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitFeature;
import com.tibco.xpd.om.core.om.OrgUnitType;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.OrganizationType;
import com.tibco.xpd.om.core.om.PositionFeature;
import com.tibco.xpd.om.core.om.provider.OMModelImages;
import com.tibco.xpd.om.modeler.subdiagram.actions.custom.CreateOMElementDiagramAction;
import com.tibco.xpd.om.modeler.subdiagram.actions.custom.OMActionIds;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.OrgUnitSubEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.OrganizationSubEditPart;

/**
 * @generated
 */
public class DiagramEditorContextMenuProvider extends
        DiagramContextMenuProvider {

    private static final int MAX_MENU_LIST_SIZE_ORG_FEATURES = 1;

    private static final int MAX_MENU_LIST_SIZE_ORGUNIT_FEATURES = 7;

    private static final String OM_FEATURE_GROUP = "addGroup";

    // A HashMap to be used to cache our dynamically created actions
    private final Map<String, DiagramAction> actionMap;

    private CreateOMElementDiagramAction addOrgUnitAction;

    private CreateOMElementDiagramAction addDynamicOrgUnitAction;

    private CreateOMElementDiagramAction addPositionAction;

    /**
     * @generated
     */
    private IWorkbenchPart part;

    /**
     * @generated
     */
    private DeleteElementAction deleteAction;

    /**
     * @generated NOT
     */
    public DiagramEditorContextMenuProvider(IWorkbenchPart part,
            EditPartViewer viewer) {
        super(part, viewer);
        this.part = part;
        deleteAction = new DeleteElementAction(part);
        deleteAction.init();

        actionMap = new HashMap<String, DiagramAction>();

    }

    /**
     * @generated NOT
     */
    @Override
    public void dispose() {
        if (deleteAction != null) {
            deleteAction.dispose();
            deleteAction = null;
        }

        if (addOrgUnitAction != null) {
            addOrgUnitAction.dispose();
            addOrgUnitAction = null;
        }

        if (addDynamicOrgUnitAction != null) {
            addDynamicOrgUnitAction.dispose();
            addDynamicOrgUnitAction = null;
        }

        if (addPositionAction != null) {
            addPositionAction.dispose();
            addPositionAction = null;
        }

        for (DiagramAction dAction : actionMap.values()) {
            dAction.dispose();
        }

        actionMap.clear();

        super.dispose();
    }

    /**
     * @see org.eclipse.jface.action.MenuManager#getMenuText()
     * 
     * @return
     */
    @Override
    public String getMenuText() {
        // TODO Auto-generated method stub
        return super.getMenuText();
    }

    /**
     * @generated NOT
     */
    @Override
    public void buildContextMenu(final IMenuManager menu) {
        getViewer().flush();

        final ISelection selection =
                part.getSite().getSelectionProvider().getSelection();

        try {
            TransactionUtil.getEditingDomain((EObject) getViewer()
                    .getContents().getModel()).runExclusive(new Runnable() {

                @Override
                public void run() {
                    ContributionItemService
                            .getInstance()
                            .contributeToPopupMenu(DiagramEditorContextMenuProvider.this,
                                    part);
                    menu.remove(ActionIds.ACTION_DELETE_FROM_MODEL);
                    menu.appendToGroup("editGroup", deleteAction); //$NON-NLS-1$

                    if (selection instanceof IStructuredSelection) {
                        IStructuredSelection sel =
                                (IStructuredSelection) selection;
                        if (sel.size() == 1) {
                            Object firstElement = sel.getFirstElement();

                            if (firstElement instanceof OrganizationSubEditPart) {

                                if (addOrgUnitAction != null) {
                                    addOrgUnitAction.dispose();
                                    addOrgUnitAction = null;
                                }

                                addOrgUnitAction =
                                        createAction(OMModelImages.IMAGE_ORG_UNIT,
                                                OMActionIds.ACTION_ADD_ORGUNIT);
                                menu.appendToGroup("addGroup", addOrgUnitAction); //$NON-NLS-1$

                                /*
                                 * XPD-5300: If in a "static" organization then
                                 * add option to create Dynamic OrgUnit.
                                 */
                                EObject element =
                                        ((OrganizationSubEditPart) firstElement)
                                                .resolveSemanticElement();
                                if (element instanceof Organization
                                        && !((Organization) element)
                                                .isDynamic()) {
                                    if (addDynamicOrgUnitAction != null) {
                                        addDynamicOrgUnitAction.dispose();
                                        addDynamicOrgUnitAction = null;
                                    }

                                    addDynamicOrgUnitAction =
                                            createAction(OMModelImages.IMAGE_DYNAMIC_ORG_UNIT,
                                                    OMActionIds.ACTION_ADD_DYNAMICORGUNIT);
                                    menu.appendToGroup("addGroup", addDynamicOrgUnitAction); //$NON-NLS-1$
                                }

                                OrganizationSubEditPart ep =
                                        (OrganizationSubEditPart) firstElement;
                                buildOrganizationElements(menu, ep);

                            } else if (firstElement instanceof OrgUnitSubEditPart) {
                                if (addPositionAction != null) {
                                    addPositionAction.dispose();
                                    addPositionAction = null;
                                }

                                addPositionAction =
                                        createAction(OMModelImages.IMAGE_POSITION,
                                                OMActionIds.ACTION_ADD_POSITION);
                                menu.appendToGroup("addGroup", addPositionAction); //$NON-NLS-1$

                                OrgUnitSubEditPart ep =
                                        (OrgUnitSubEditPart) firstElement;
                                buildOrgUnitElements(menu, ep);
                            }
                        }
                    }
                }

                /**
                 * @return
                 * 
                 */
                private CreateOMElementDiagramAction createAction(
                        String imgPath, String actionId) {
                    Image img;
                    ImageDescriptor imgDesc;
                    img =
                            ExtendedImageRegistry.INSTANCE
                                    .getImage(OMModelImages
                                            .getImageObject(imgPath));
                    imgDesc = ImageDescriptor.createFromImage(img);

                    CreateOMElementDiagramAction action =
                            new CreateOMElementDiagramAction(part.getSite()
                                    .getPage(), actionId, imgDesc);

                    action.init();

                    return action;
                }
            });
        } catch (Exception e) {
            OrganizationModelSubDiagramEditorPlugin.getInstance()
                    .logError("Error building context menu", e); //$NON-NLS-1$
        }
    }

    /**
     * 
     * Returns list of OrgUnitFeatures for the passed in type.
     * 
     * @param OrgElementType
     *            type
     * @return EList<OrgUnitFeature> lstOrgUnitFeatures
     */
    private EList<OrgUnitFeature> getOrgUnitFeatures(OrgElementType type) {
        EList<OrgUnitFeature> lstOrgUnitFeatures = null;

        if (type instanceof OrganizationType) {
            OrganizationType orgType = (OrganizationType) type;
            lstOrgUnitFeatures = orgType.getOrgUnitFeatures();
        }

        return lstOrgUnitFeatures;

    }

    /**
     * 
     * Adds to the context menu of an OrgUnit selection programatically.
     * Dynamically creates actions to add new OM features (subunits or
     * positions) defined for the selected OrgUnit.
     * 
     * @param IMenuManager
     *            popupMenu
     * @param OrgUnitSubEditPart
     *            ep
     */
    private void buildOrgUnitElements(IMenuManager popupMenu,
            OrgUnitSubEditPart ep) {
        String groupName = "addGroup";
        IMenuManager parent = popupMenu;

        // First, add the basic untyped Position create action
        Image img = null;
        img =
                ExtendedImageRegistry.INSTANCE.getImage(OMModelImages
                        .getImageObject(OMModelImages.IMAGE_POSITION));
        ImageDescriptor imgDesc = ImageDescriptor.createFromImage(img);

        EObject elem = ep.resolveSemanticElement();

        if (elem instanceof OrgUnit) {
            OrgUnit ou = (OrgUnit) elem;
            OrgElementType type = ou.getType();

            if (type != null && type instanceof OrgUnitType) {
                OrgUnitType ouType = (OrgUnitType) type;
                // Has a type so now go and fetch all the Features
                // 1. OrgUnit Features
                EList<OrgUnitFeature> orgUnitFeatures =
                        ouType.getOrgUnitFeatures();

                EList<PositionFeature> positionFeatures =
                        ouType.getPositionFeatures();

                int numContributions =
                        orgUnitFeatures.size() + positionFeatures.size();

                if (numContributions >= MAX_MENU_LIST_SIZE_ORGUNIT_FEATURES) {
                    // Add a submenu for this OrgUnit type
                    Image imgOrgUnitTypes =
                            ExtendedImageRegistry.INSTANCE
                                    .getImage(OMModelImages
                                            .getImageObject(OMModelImages.IMAGE_ORG_UNIT_TYPES_GROUP));
                    ImageDescriptor id =
                            ImageDescriptor.createFromImage(imgOrgUnitTypes);

                    MenuManager mm =
                            new MenuManager(type.getDisplayName(), id,
                                    type.getName());
                    popupMenu.appendToGroup(groupName, mm);
                    mm.add(new GroupMarker(OM_FEATURE_GROUP));
                    parent = mm;
                }

                img =
                        ExtendedImageRegistry.INSTANCE
                                .getImage(OMModelImages
                                        .getImageObject(OMModelImages.IMAGE_ORG_UNIT_FEATURE));
                imgDesc = ImageDescriptor.createFromImage(img);

                for (OrgUnitFeature orgUnitFeature : orgUnitFeatures) {
                    if (orgUnitFeature.getFeatureType() != null) {
                        DiagramAction featureAct =
                                getCreateAction(OMActionIds.ACTION_ADD_ORGUNIT,
                                        orgUnitFeature,
                                        ou);
                        parent.appendToGroup(OM_FEATURE_GROUP, featureAct);

                    }
                }

                // 2. Position Features
                for (PositionFeature posFeature : positionFeatures) {
                    DiagramAction act =
                            getCreateAction(OMActionIds.ACTION_ADD_POSITION,
                                    posFeature);
                    parent.appendToGroup(OM_FEATURE_GROUP, act);
                }

            }
        }

    }

    /**
     * 
     * Adds to the context menu of the Organization diagram canvas
     * programatically. Dynamically creates actions to add new OM features
     * (OrgUnits) defined for the diagram's Organization.
     * 
     * @param IMenuManager
     *            popupMenu
     * @param OrganizationSubEditPart
     *            ep
     */
    private void buildOrganizationElements(IMenuManager popupMenu,
            OrganizationSubEditPart ep) {

        String groupName = "addGroup";
        Image img = null;
        img =
                ExtendedImageRegistry.INSTANCE.getImage(OMModelImages
                        .getImageObject(OMModelImages.IMAGE_ORG_UNIT));
        ImageDescriptor imgDesc = ImageDescriptor.createFromImage(img);

        // Iterate through the OrgUnit features if this is a typed Organization.
        EObject elem = ep.resolveSemanticElement();
        if (elem instanceof Organization) {
            Organization org = (Organization) elem;
            OrgElementType type = org.getType();

            Image imgOrgUnitFeature =
                    ExtendedImageRegistry.INSTANCE
                            .getImage(OMModelImages
                                    .getImageObject(OMModelImages.IMAGE_ORG_UNIT_FEATURE));
            imgDesc = ImageDescriptor.createFromImage(imgOrgUnitFeature);

            if (type != null) {
                // Has a type so now go and fetch all the Features
                EList<OrgUnitFeature> orgUnitFeatures =
                        getOrgUnitFeatures(type);

                // To many items for main context menu so create a subgroup
                HashSet<OrgUnitType> setOrgUnitTypes =
                        new HashSet<OrgUnitType>();

                for (OrgUnitFeature orgUnitFeature : orgUnitFeatures) {
                    // How many submenus do we need? Need to collect all the
                    // features and get their types. Use a hasheset so we
                    // don't get duplicates
                    OrgUnitType featureType = orgUnitFeature.getFeatureType();
                    setOrgUnitTypes.add(featureType);
                }

                if (setOrgUnitTypes.size() > MAX_MENU_LIST_SIZE_ORG_FEATURES) {
                    // Need to create submenus for each type

                    Image imgOrgUnitTypes =
                            ExtendedImageRegistry.INSTANCE
                                    .getImage(OMModelImages
                                            .getImageObject(OMModelImages.IMAGE_ORG_UNIT_TYPES_GROUP));
                    ImageDescriptor id =
                            ImageDescriptor.createFromImage(imgOrgUnitTypes);

                    for (OrgUnitType orgUnitType : setOrgUnitTypes) {
                        MenuManager mm =
                                new MenuManager(orgUnitType.getDisplayName(),
                                        id, orgUnitType.getName());
                        popupMenu.appendToGroup(groupName, mm);
                        mm.add(new GroupMarker(OM_FEATURE_GROUP));

                        for (OrgUnitFeature orgUnitFeature : orgUnitFeatures) {
                            if (orgUnitFeature.getFeatureType() == orgUnitType) {
                                DiagramAction featureAct =
                                        getCreateAction(OMActionIds.ACTION_ADD_ORGUNIT,
                                                orgUnitFeature);

                                mm.appendToGroup(OM_FEATURE_GROUP, //$NON-NLS-1$
                                        featureAct);
                            }
                        }

                    }
                } else {
                    // Just display each action on the main context menu
                    for (OrgUnitFeature orgUnitFeature : orgUnitFeatures) {
                        DiagramAction featureAct =
                                getCreateAction(OMActionIds.ACTION_ADD_ORGUNIT,
                                        orgUnitFeature);

                        popupMenu.appendToGroup(groupName, //$NON-NLS-1$
                                featureAct);

                    }
                }
            }

        }
    }

    /**
     * 
     * Creates and action to add an OM element with the feature passed in. The
     * action is added to a cache so that it can be reused.
     * 
     * 
     * @param String
     *            actionId
     * @param Feature
     *            feature
     * @return DiagramAction
     */
    private DiagramAction getCreateAction(String actionId, Feature feature) {
        return getCreateAction(actionId, feature, null);
    }

    /**
     * Creates and action to add an OM element with the feature passed in. The
     * action is added to a cache so that it can be reused.
     * 
     * The OrgUnit feature parameter can be null or if defined will be used in
     * the creation of a sub OrgUnit where it is used as the parent.
     * 
     * @param String
     *            actionId
     * @param Feature
     *            feature
     * @param OrgUnit
     *            ou
     * @return DiagramAction
     */
    private DiagramAction getCreateAction(String actionId, Feature feature,
            OrgUnit ou) {

        IWorkbenchPage page = part.getSite().getPage();
        CreateOMElementDiagramAction action = null;
        Image img = null;

        if (actionId.equals(OMActionIds.ACTION_ADD_ORGUNIT)) {
            img =
                    ExtendedImageRegistry.INSTANCE
                            .getImage(OMModelImages
                                    .getImageObject(OMModelImages.IMAGE_ORG_UNIT_FEATURE));
        } else if (actionId.equals(OMActionIds.ACTION_ADD_POSITION)) {
            img =
                    ExtendedImageRegistry.INSTANCE
                            .getImage(OMModelImages
                                    .getImageObject(OMModelImages.IMAGE_POSITION_FEATURE));
        }

        if (img != null) {
            ImageDescriptor imgDesc = ImageDescriptor.createFromImage(img);
            String fragment = feature.eResource().getURIFragment(feature);

            if (fragment != null) {
                if (actionMap.containsKey(fragment)) {
                    action =
                            (CreateOMElementDiagramAction) actionMap
                                    .get(fragment);
                    action.dispose();
                    action = null;
                    actionMap.remove(fragment);
                }
            }

            if (ou != null) {
                action =
                        new CreateOMElementDiagramAction(page, actionId,
                                imgDesc, feature, ou);
            } else if (feature != null) {
                action =
                        new CreateOMElementDiagramAction(page, actionId,
                                imgDesc, feature);
            } else {
                action =
                        new CreateOMElementDiagramAction(page, actionId,
                                imgDesc);
            }

            action.init();
            actionMap.put(fragment, action);

        }

        return action;

    }
}
