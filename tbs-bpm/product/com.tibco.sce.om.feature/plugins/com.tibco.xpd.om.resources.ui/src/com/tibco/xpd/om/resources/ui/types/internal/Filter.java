package com.tibco.xpd.om.resources.ui.types.internal;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.tibco.xpd.om.resources.OMResourcesActivator;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.types.OMTypesFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.ui.types.ITypeFilterProvider;
import com.tibco.xpd.resources.ui.types.TypeInfo;
import com.tibco.xpd.resources.ui.types.TypeUtil;
import com.tibco.xpd.resources.ui.types.TypedItem;

/**
 * Filter Provider for the PickerDialog.
 * 
 * @author rassisi
 * 
 */
public class Filter implements ITypeFilterProvider {

    // Singleton instance of this class
    private final static Filter INSTANCE = new Filter();

    // private static URI typesURI = URI
    //            .createURI("pathmap://OM_TYPES/StandardOMTypes.meta.om"); //$NON-NLS-1$

    /**
     * Though the implementation can specify its own columns in the database,
     * some columns have to be always there. the 'internal_type' one of them.
     */
    public static final String ATTRIBUTE_TYPE = "internal_type"; //$NON-NLS-1$

    // private static Resource standardTypesResource = XpdResourcesPlugin
    // .getDefault().getEditingDomain().getResourceSet().getResource(
    // typesURI, true);
    //
    // private static ArrayList<OrgElementType> standardTypes;
    //
    // private static ArrayList<TypedItem> standardTypesTypedItems;
    //
    // private static ArrayList<String> standardAttributeTypes;
    //
    // private static ArrayList<TypedItem> standardAttributeTypeItems;
    //
    // private static HashMap<String, OrgElementType> standardTypesRegistry;
    //
    // static {
    //
    // standardTypes = new ArrayList<OrgElementType>();
    // standardTypesTypedItems = new ArrayList<TypedItem>();
    // standardTypesRegistry = new HashMap<String, OrgElementType>();
    // standardAttributeTypes = new ArrayList<String>();
    // standardAttributeTypeItems = new ArrayList<TypedItem>();
    //
    // List<AttributeType> list = AttributeType.VALUES;
    // for (AttributeType attributeType : list) {
    // standardAttributeTypes.add(attributeType.getLiteral());
    // TypedItem item = new TypedItem(OMTypesFactory.TYPE_ATTRIBUTE_TYPE);
    // item.setQualifiedName(attributeType.getLiteral());
    // item.setName(attributeType.getLiteral());
    // item.setData(attributeType.getLiteral());
    // standardAttributeTypeItems.add(item);
    // }
    //
    // TreeIterator<EObject> iter = standardTypesResource.getAllContents();
    // while (iter.hasNext()) {
    // Object o = iter.next();
    // if (o instanceof OrgElementType) {
    // standardTypes.add((OrgElementType) o);
    // }
    // }
    // for (OrgElementType ot : standardTypes) {
    // TypedItem item = new TypedItem(OMTypesFactory.getType(ot));
    // String label = ot.getLabel(true);
    // if (label == null || label.length() == 0) {
    // label = ot.getClass().getName();
    // }
    // item.setQualifiedName(label);
    // standardTypesTypedItems.add(item);
    // standardTypesRegistry.put(item.getTypeId(), ot);
    // }
    //
    // }

    // /**
    // * This Method is needed by the ComboCellEditor for Attribute Types
    // */
    // public static int getAttributeTypeIndex(String name) {
    // for (int index = 0; index < standardAttributeTypeItems.size(); index++) {
    // TypedItem item = standardAttributeTypeItems.get(index);
    // if (item.getName().equals(name)) {
    // return index;
    // }
    // }
    // return -1;
    // }

    private String providerId;

    public static Filter getDefault() {
        return INSTANCE;
    }

    /**
     * Get singleton instance of this class.
     * 
     * @return <code>BOMPickerProviderUtil</code>
     */
    public static Filter getInstance() {
        return INSTANCE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.bom.resources.ui.picker.IBOMPickerProvider#getContent(org
     * .eclipse.core.runtime.IProgressMonitor,
     * com.tibco.xpd.bom.resources.ui.picker.IBOMPickerProvider.BOMType)
     */
    public Set<TypedItem> getContent(IProgressMonitor monitor,
            IResource[] queryResources, TypeInfo queryType,
            Object[] contentToExclude) {
        Set<TypedItem> items = new HashSet<TypedItem>();

        items = getAllOrgElementTypes(monitor, queryType);
//        if (queryType.equals(OMTypesFactory.TYPE_LOCATION_TYPE)) {
//            items = getAllOrgElementTypes(monitor,
//                    OMTypesFactory.TYPE_LOCATION_TYPE);
//        } else if (queryType.equals(OMTypesFactory.TYPE_LOCATION)) {
//            items = getAllOrgElementTypes(monitor, OMTypesFactory.TYPE_LOCATION);
//        } else if (queryType.equals(OMTypesFactory.TYPE_ORGANIZATION_TYPE)) {
//            items = getAllOrgElementTypes(monitor,
//                    OMTypesFactory.TYPE_ORGANIZATION_TYPE);
//        } else if (queryType
//                .equals(OMTypesFactory.TYPE_ORG_UNIT_RELATIONSHIP_TYPE)) {
//            items = getAllOrgElementTypes(monitor,
//                    OMTypesFactory.TYPE_ORG_UNIT_RELATIONSHIP_TYPE);
//        } else if (queryType.equals(OMTypesFactory.TYPE_POSITION_TYPE)) {
//            items = getAllOrgElementTypes(monitor,
//                    OMTypesFactory.TYPE_POSITION_TYPE);
//        } else if (queryType.equals(OMTypesFactory.TYPE_ORG_UNIT_TYPE)) {
//            items = getAllOrgElementTypes(monitor,
//                    OMTypesFactory.TYPE_ORG_UNIT_TYPE);
//        } else if (queryType.equals(OMTypesFactory.TYPE_CAPABILITY)) {
//            items = getAllOrgElementTypes(monitor,
//                    OMTypesFactory.TYPE_CAPABILITY);
//        } else if (queryType.equals(OMTypesFactory.TYPE_PRIVILEGE)) {
//            items = getAllOrgElementTypes(monitor,
//                    OMTypesFactory.TYPE_PRIVILEGE);
//        } else if (queryType.equals(OMTypesFactory.TYPE_ATTRIBUTE_TYPE)) {
//            items = getAllAttributeTypes(monitor);
//        } else if (queryType.equals(OMTypesFactory.TYPE_ORG_UNIT)) {
//            items = getAllOrgElementTypes(monitor, OMTypesFactory.TYPE_ORG_UNIT);
//        } else if (queryType.equals(OMTypesFactory.TYPE_POSITION)) {
//            items = getAllOrgElementTypes(monitor, OMTypesFactory.TYPE_POSITION);
//        } else if (queryType.equals(OMTypesFactory.TYPE_GROUP)) {
//            items = getAllOrgElementTypes(monitor, OMTypesFactory.TYPE_GROUP);
//        } else if (queryType.equals(OMTypesFactory.TYPE_ATTRIBUTE)) {
//            items = getAllOrgElementTypes(monitor,
//                    OMTypesFactory.TYPE_ATTRIBUTE);
//        } else if (queryType.equals(OMTypesFactory.TYPE_ORG_META_MODEL)) {
//            items = getAllOrgElementTypes(monitor,
//                    OMTypesFactory.TYPE_ORG_META_MODEL);
//        } else if (queryType.equals(OMTypesFactory.TYPE_RESOURCE)) {
//            items
//        }

        if (contentToExclude != null) {
            for (Object excludeObject : contentToExclude) {
                items.remove(excludeObject);
            }
        }
        return items;
    }

    /**
     * Get all OrgElementTypes from the database.
     * 
     * @param monitor
     *            progress monitor
     * @return set of <code>TypedItem</code> objects. Empty set if none found.
     */
    @SuppressWarnings("deprecation")
    private Set<TypedItem> getAllOrgElementTypes(IProgressMonitor monitor,
            TypeInfo typeInfo) {
        if (monitor != null) {
            monitor
                    .beginTask(
                            Messages.OMPickerDialog_gettingResults_monitor_shortdesc,
                            2);
        }

        Set<TypedItem> result = new HashSet<TypedItem>();

        HashMap<String, String> criteria = new HashMap<String, String>();
        criteria.put(ATTRIBUTE_TYPE, typeInfo.getTypeId());
        Collection<IndexerItem> indexerItems = XpdResourcesPlugin.getDefault()
                .getIndexerService().query(OMResourcesActivator.OM_INDEXER_ID,
                        criteria);

        for (IndexerItem indexerItem : indexerItems) {
            result.add(OMTypesFactory.createTypedItem(indexerItem));
        }

        // for (TypedItem standardType : standardTypesTypedItems) {
        // result.add(standardType);
        // }

        if (monitor != null) {
            monitor.worked(1);
        }
        return result;
    }

    /**
     * Get all OrgElementTypes from the database.
     * 
     * @param monitor
     *            progress monitor
     * @return set of <code>TypedItem</code> objects. Empty set if none found.
     */
    @SuppressWarnings("deprecation")
    private Set<TypedItem> getAllAttributeTypes(IProgressMonitor monitor) {

        if (monitor != null) {
            monitor
                    .beginTask(
                            Messages.OMPickerDialog_gettingResults_monitor_shortdesc,
                            2);
        }

        Set<TypedItem> result = new HashSet<TypedItem>();

        // for (TypedItem standardType : standardAttributeTypeItems) {
        // result.add(standardType);
        // }

        if (monitor != null) {
            monitor.worked(1);
        }
        return result;
    }

    /**
     * @param modelElement
     * @return
     */
    public static String getURI(EObject modelElement) {
        Resource modelElementResource = modelElement.eResource();
        URI uri = modelElementResource.getURI().appendFragment(
                modelElementResource.getURIFragment(modelElement));
        String uriToString = uri.toString();
        return uriToString;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.types.ITypeFilterProvider#matchItem(java.lang
     * .Object)
     */
    public boolean matchItem(Object item) {
        Set<TypeInfo> typeInfos = TypeUtil.getRegisteredTypes(providerId);
        TypeInfo typeInfo = OMTypesFactory.getType(item);
        Iterator<TypeInfo> it = typeInfos.iterator();
        while (it.hasNext()) {
            if (it.equals(typeInfo)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return
     */
    public String getProviderId() {
        return providerId;
    }

    /**
     * @param providerId
     */
    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    /**
     * Get the project config of the project with the given name
     * 
     * @param projectName
     *            project to get config of
     * @return <code>ProjectConfig</code>, <code>null</code> if failed to access
     *         it
     */
    public static ProjectConfig getProjectConfig(String projectName) {
        IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
                projectName);

        return getProjectConfig(project);
    }

    /**
     * Get the project config of the given project
     * 
     * @param projectName
     *            project to get config of
     * @return <code>ProjectConfig</code>, <code>null</code> if failed to access
     *         it
     */
    @SuppressWarnings("nls")
    public static ProjectConfig getProjectConfig(IProject project) {
        ProjectConfig pc = XpdResourcesPlugin.getDefault().getProjectConfig(
                project);
        return pc;
    }

    /**
     * @return
     */
    // public static ArrayList<TypedItem> getStandardTypesTypedItems() {
    // return standardTypesTypedItems;
    // }
    /**
     * @param item
     * @return
     */
    // public static OrgElementType getStandardType(TypedItem item) {
    // return standardTypesRegistry.get(item.getTypeId());
    // }
    /**
     * @param index
     * @return
     */
    // public static String getAttributeType(int index) {
    // return standardAttributeTypes.get(index);
    // }
    /**
     * @return
     */
    // public static String[] getStandardAttributesStringArray() {
    // String[] result = new String[standardAttributeTypes.size()];
    // int i = 0;
    // for (String type : standardAttributeTypes) {
    // result[i++] = type;
    // }
    // return result;
    // }
}
