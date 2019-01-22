/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.core.om.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.IActivityManager;
import org.eclipse.uml2.common.util.UML2Util;

import com.tibco.xpd.om.core.om.Group;
import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.Namespace;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.indexer.IndexerService;
import com.tibco.xpd.ui.util.NameUtil;

/**
 * Utility class for OM model.
 * <p>
 * <i>Created: 4 Feb 2008</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
/*
 * IMPLEMENTATION NOTES: This class reuses the
 * <code>org.eclipse.uml2.common.util.UML2Util</code> ({@link UML2Util}) class
 * to implement labels' externalization.
 */
public class OMUtil extends UML2Util {

    public final static String NAMESPACE_SEPARATOR = "::"; //$NON-NLS-1$

    public final static String LABEL_KEY_PREFIX = "_label_"; //$NON-NLS-1$

    public final static String OM_FILE_EXTENSION = "om"; //$NON-NLS-1$

    public final static String OM_META_FILE_EXTENSION = "meta.om"; //$NON-NLS-1$

    public final static String OM_SPECIAL_FOLDER_KIND = "om"; //$NON-NLS-1$

    public final static String HUMAN_RESOURCE_TYPE_ID = "omHumanResourceTypeID"; //$NON-NLS-1$

    /**
     * Prevents instantiation.
     */
    private OMUtil() {
    }

    /**
     * Provides qualified name for NamedElement.
     * 
     * @param namedElement
     *            the named element.
     * @return string qualified name containing all parent namespaces separated
     *         by NAMESPACE_SEPARATOR.
     */
    public static String getQualifiedName(NamedElement namedElement) {
        Namespace namespace = namedElement.getNamespace();
        String localName = namedElement.getName();
        localName = localName == null ? "" : localName; //$NON-NLS-1$
        if (namespace != null) {
            return namespace.getQualifiedName() + NAMESPACE_SEPARATOR
                    + localName;
        }
        return localName;
    }

    /**
     * Retrieves a label for this named element, localized.
     */
    /*
     * IMPLEMENTATION NOTES: Depends on ({@link UML2Util}).
     */
    public static String getLabel(NamedElement namedElement) {
        return namedElement.getLabel(true);
    }

    /**
     * Retrieves a label for this named element, localized if indicated.
     */
    /*
     * IMPLEMENTATION NOTES: Depends on ({@link UML2Util}).
     */
    public static String getLabel(NamedElement namedElement, boolean localize) {

        IActivityManager activityManager =
                PlatformUI.getWorkbench().getActivitySupport()
                        .getActivityManager();

        Set<?> enabledActivityIds = activityManager.getEnabledActivityIds();
        String qualifiedName = namedElement.getQualifiedName();

        if (enabledActivityIds.contains("com.tibco.xpd.om.solutiondesign")) { //$NON-NLS-1$
            return getString(namedElement,
                    getLabelKey(qualifiedName),
                    namedElement.getDisplayName()
                            + " (" + namedElement.getName() + ")", localize); //$NON-NLS-1$ //$NON-NLS-2$
        } else {
            return getString(namedElement,
                    getLabelKey(qualifiedName),
                    namedElement.getDisplayName(),
                    localize);
        }

    }

    /**
     * @param qualifiedName
     * @return
     */
    /*
     * IMPLEMENTATION NOTES: Depends on ({@link UML2Util}).
     */
    public static String getLabelKey(String qualifiedName) {
        return LABEL_KEY_PREFIX
                + getValidJavaIdentifier(isEmpty(qualifiedName) ? EMPTY_STRING
                        : qualifiedName.replace(':', '_'));
    }

    public static Properties generateLabels(Resource resource) {
        Properties result = new Properties();
        TreeIterator<Object> iterator =
                EcoreUtil.getAllContents(resource, false);
        while (iterator.hasNext()) {
            Object object = iterator.next();
            if (object instanceof NamedElement) {
                NamedElement namedElement = (NamedElement) object;
                String qualifiedName = namedElement.getQualifiedName();
                String labelKey = getLabelKey(qualifiedName);
                if (!EMPTY_STRING.equals(labelKey)) {
                    result.setProperty(labelKey, namedElement.getDisplayName());
                }
            }

        }
        return result;
    }

    public static String getID(EObject eObject) {
        Resource resource = eObject.eResource();
        if (resource != null) {
            return resource.getURIFragment(eObject);
        }
        return null;
    }

    /**
     * Returns the EObject for a given id
     * 
     * @param id
     * @return
     */
    public static EObject getEObjectByID(String id) {
        EObject retObj = null;

        if (id != null) {
            IndexerService service =
                    XpdResourcesPlugin.getDefault().getIndexerService();

            IndexerItemImpl criteria = new IndexerItemImpl();

            criteria.set("id", id); //$NON-NLS-1$

            Collection<IndexerItem> items =
                    service
                            .query("com.tibco.xpd.om.resources.indexing.omIndexer", criteria); //$NON-NLS-1$

            Iterator<IndexerItem> iter = items.iterator();
            if (iter.hasNext()) {
                IndexerItem indexerItem = iter.next();
                URI uri = URI.createURI(indexerItem.getURI());
                if (uri != null) {
                    retObj =
                            XpdResourcesPlugin.getDefault().getEditingDomain()
                                    .getResourceSet().getEObject(uri, true);
                }
            }
        }
        return retObj;
    }

    /**
     * Returns the URI for a given id
     * 
     * @param id
     * @return
     */
    public static URI getURIByID(String id) {
        URI retURI = null;

        IndexerService service =
                XpdResourcesPlugin.getDefault().getIndexerService();

        IndexerItemImpl criteria = new IndexerItemImpl();

        criteria.set("id", id); //$NON-NLS-1$

        Collection<IndexerItem> items =
                service
                        .query("com.tibco.xpd.om.resources.indexing.omIndexer", criteria); //$NON-NLS-1$

        Iterator<IndexerItem> iter = items.iterator();
        if (iter.hasNext()) {
            IndexerItem indexerItem = iter.next();
            retURI = URI.createURI(indexerItem.getURI());
        }

        return retURI;
    }

    /**
     * @param modelElement
     * @return
     */
    public static String getURI(EObject modelElement) {
        Resource modelElementResource = modelElement.eResource();
        URI uri =
                modelElementResource.getURI()
                        .appendFragment(modelElementResource
                                .getURIFragment(modelElement));
        String uriToString = uri.toString();
        return uriToString;

    }

    /**
     * This method encapsulates the strategy for creating default names.
     * 
     * @param prefix
     * @param elements
     * @return
     */
    public static String getDefaultName(String prefix,
            Collection<String> elements) {
        for (int i = 1;; i++) {
            if (!elements.contains(prefix + i)) {
                return prefix + i;
            }
        }
    }

    /**
     * Returns an unique name for a NamedElement.
     * 
     * The EObject passed in is used to traverse up the model hierarchy to find
     * the top level OrgModel, which is then used as the starting point to
     * collect all current names to compare for uniqueness
     * 
     * @param prefix
     * @param EObject
     *            element
     * @return String uniqueName
     */
    public static String getDefaultNamedElementName(String prefix,
            EObject element) {
        String name = ""; //$NON-NLS-1$

        OrgModel model = OMUtil.getModel(element);

        TreeIterator<Object> iter =
                EcoreUtil.getAllProperContents(model, false);
        List<NamedElement> orgUnitsList = new ArrayList<NamedElement>();
        List<String> names = new ArrayList<String>();

        if (iter != null) {
            while (iter.hasNext()) {
                Object next = iter.next();
                if (next instanceof NamedElement) {
                    orgUnitsList.add((NamedElement) next);
                }
            }
        }

        for (NamedElement ou : orgUnitsList) {
            names.add(ou.getName());
        }

        int idx = 1;
        name = prefix + idx;

        while (names.contains(name)) {
            name = prefix + ++idx;
        }

        return name;
    }

    /**
     * 
     * Using the EObject passed in as the starting point, this method recurses
     * up the model hierarchy to find the containing OrgModel
     * 
     * 
     * @param EObject
     *            element
     * @return OrgModel
     */
    public static OrgModel getModel(EObject element) {
        OrgModel om = null;

        if (element != null) {
            EObject container = element.eContainer();
            if (container != null) {
                if (!(container instanceof OrgModel)) {
                    om = getModel(container);
                } else {
                    om = (OrgModel) container;
                }
            }
        }

        return om;
    }

    /**
     * @param elements
     * @return
     */
    public static Collection<String> getDisplayNamesArray(Collection<?> elements) {
        Iterator<?> it = elements.iterator();
        ArrayList<String> displayNames = new ArrayList<String>();
        while (it.hasNext()) {
            Object eObject = it.next();
            if (eObject instanceof NamedElement) {
                displayNames.add(((NamedElement) eObject).getDisplayName());
            }
        }
        return displayNames;
    }

    /**
     * @param source
     * @param target
     * @return
     */
    public static boolean isOrgUnitInHierarchy(OrgUnit source, OrgUnit target) {
        OrgUnit parentOrgUnit = OMUtil.getParentOrgUnit(source);

        while (parentOrgUnit != null) {
            if (parentOrgUnit == target) {
                return true;
            } else {
                parentOrgUnit = OMUtil.getParentOrgUnit(parentOrgUnit);
            }
        }

        return false;
    }

    /**
     * @param ou
     * @return
     */
    public static OrgUnit getParentOrgUnit(OrgUnit ou) {
        OrgUnitRelationship rel = ou.getIncomingHierachicalRelationship();
        if (rel != null && rel.getFrom() != null) {
            return rel.getFrom();
        }
        return null;
    }

    /**
     * Get all the {@link Group}s under the given object.
     * 
     * @param eo
     *            {@link OrgModel} or <code>Group</code>.
     * @return Collection of <code>Group</code>s or empty collection if none
     *         found.
     */
    public static Collection<Group> getAllGroups(EObject eo) {
        Set<Group> groups = new HashSet<Group>();

        if (eo instanceof OrgModel) {
            EList<Group> mainGroups = ((OrgModel) eo).getGroups();
            if (mainGroups != null) {
                for (Group group : mainGroups) {
                    groups.addAll(getAllGroups(group));
                }
            }
        } else if (eo instanceof Group) {
            groups.add((Group) eo);
            EList<Group> subGroups = ((Group) eo).getSubGroups();
            if (subGroups != null) {
                for (Group group : subGroups) {
                    groups.addAll(getAllGroups(group));
                }
            }
        }

        return groups;
    }

    /**
     * Check if the given relationship has a default display name and name.
     * 
     * @param relationship
     * @return
     */
    public static boolean hasDefaultName(OrgUnitRelationship relationship) {
        if (relationship != null) {
            // Check if the display name is default
            String toCompare =
                    createOrgUnitRelationshipName(relationship, true);

            if (toCompare != null
                    && toCompare.equals(relationship.getDisplayName())) {
                // Check if the name is default
                toCompare = createOrgUnitRelationshipName(relationship, false);
                return toCompare != null
                        && toCompare.equals(relationship.getName());
            }
        }

        return false;
    }

    /**
     * Create the name/display name of this {@link OrgUnitRelationship}.
     * 
     * @see #createOrgUnitRelationshipDisplayName(String, String, boolean)
     * @param relationship
     * @param isLabel
     *            <code>true</code> if the display name (label) is required and
     *            <code>false</code> if the name of the relationship is
     *            required.
     * @return
     */
    public static String createOrgUnitRelationshipName(
            OrgUnitRelationship relationship, boolean isLabel) {
        if (relationship != null && relationship.getFrom() != null
                && relationship.getTo() != null) {
            return createOrgUnitRelationshipName(relationship.getFrom()
                    .getName(), relationship.getTo().getName(), relationship
                    .isIsHierarchical(), isLabel);
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * Create the {@link OrgUnitRelationship} name/display name using the
     * information given.
     * 
     * @see #createOrgUnitRelationshipDisplayName(OrgUnitRelationship)
     * 
     * @param from
     *            name of the {@link OrgUnit} the relationship originates
     * @param to
     *            name of the OrgUnit the relationship terminates
     * @param isHierarchical
     *            <code>true</code> if this is a hierarchical relationship
     *            (currently not used for anything).
     * @param isLabel
     *            <code>true</code> if the display name (label) is required and
     *            <code>false</code> if the name of the relationship is
     *            required.
     * 
     * @return
     */
    public static String createOrgUnitRelationshipName(String from, String to,
            boolean isHierarchical, boolean isLabel) {
        if (from != null && to != null) {
            String label = from + "-" + to; //$NON-NLS-1$

            if (!isLabel) {
                // Get the name based on the label
                label = NameUtil.getInternalName(label, false);
            }

            return label;
        }
        return ""; //$NON-NLS-1$
    }

}
