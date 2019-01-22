/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.providers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.IDataPickerProxyItem;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Provider helper class that provides the content for the Data picker.
 * 
 * @author Miguel Torres
 * 
 */
public final class DataFilterPickerProviderHelper implements
        IDataPickerProvider {

    // Singleton instance of this class
    private final static DataFilterPickerProviderHelper INSTANCE =
            new DataFilterPickerProviderHelper();

    /**
     * Private constructor. This class cannot be instantiated.
     */
    private DataFilterPickerProviderHelper() {

    }

    /**
     * Get singleton instance of this class.
     * 
     * @return <code>BOMPickerProviderUtil</code>
     */
    public static DataFilterPickerProviderHelper getInstance() {
        return INSTANCE;
    }

    /*
     * (non-Javadoc)
     */
    public IDataPickerProxyItem[] getContent(DataType type, EObject scope) {
        Set<IDataPickerProxyItem> items = new HashSet<IDataPickerProxyItem>();

        switch (type) {
        case DATAFIELD:
            items = getAllDataField(scope);
            break;

        case FORMALPARAMETER:
            items = getAllFormalParameters(scope);
            break;

        case DATAFIELD_FORMALPARAMETER:
            items = getAllDataFieldNFormalParam(scope);
            break;
        case PARTICIPANTS:
            items = getAllParticipants(scope);
            break;

        }

        return items.toArray(new IDataPickerProxyItem[items.size()]);
    }

    /*
     * (non-Javadoc)
     */
    public IDataPickerProxyItem getItem(URI uri, String name, EObject scope) {
        IDataPickerProxyItem item = null;
        if (uri != null && name != null) {
            EObject eo = ProcessUIUtil.getEObjectFrom(uri, scope);
            if (eo != null) {
                if (eo instanceof ProcessRelevantData) {
                    IProject project = WorkingCopyUtil.getProjectFor(scope);
                    String projectName = ""; //$NON-NLS-1$
                    if (project != null) {
                        projectName = project.getName();
                    }
                    WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(scope);
                    String path = ""; //$NON-NLS-1$
                    if (wc != null) {
                        path = ProcessUIUtil.createPath(wc);
                    }
                    item =
                            getProcessRelevantDataIndexerItem((ProcessRelevantData) eo,
                                    projectName,
                                    path);
                }
            }
        }
        return item;
    }

    /**
     * Get all Data fields from the model.
     * 
     * @param monitor
     *            progress monitor
     * @return set of <code>IDataPickerProxyItem</code> objects. Empty set if
     *         none found.
     */
    private Set<IDataPickerProxyItem> getAllDataField(EObject scope) {
        Set<IDataPickerProxyItem> items = new HashSet<IDataPickerProxyItem>();
        if (scope != null) {
            IProject project = WorkingCopyUtil.getProjectFor(scope);
            String projectName = ""; //$NON-NLS-1$
            if (project != null) {
                projectName = project.getName();
            }
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(scope);
            String path = ""; //$NON-NLS-1$
            if (wc != null) {
                path = ProcessUIUtil.createPath(wc);
            }
            List<ProcessRelevantData> prdList =
                    ProcessDataUtil.getProcessRelevantData(scope);
            List<ProcessRelevantData> dataFieldList =
                    new ArrayList<ProcessRelevantData>();
            if (prdList != null && !prdList.isEmpty()) {
                for (Iterator<ProcessRelevantData> iterator =
                        prdList.iterator(); iterator.hasNext();) {
                    Object obj = (Object) iterator.next();
                    if (obj instanceof DataField) {
                        dataFieldList.add((ProcessRelevantData) obj);
                    }
                }
                items
                        .addAll(getProcessRelevantDataIndexerItemList(dataFieldList,
                                projectName,
                                path));
            }
        }
        return items;
    }

    /**
     * Get all formal parameters from the model
     * 
     * @param monitor
     *            progress monitor
     * @return set of <code>IDataPickerProxyItem</code> objects. Empty set if
     *         none found.
     */
    private Set<IDataPickerProxyItem> getAllFormalParameters(EObject scope) {

        Set<IDataPickerProxyItem> items = new HashSet<IDataPickerProxyItem>();
        if (scope != null) {
            IProject project = WorkingCopyUtil.getProjectFor(scope);
            String projectName = ""; //$NON-NLS-1$
            if (project != null) {
                projectName = project.getName();
            }
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(scope);
            String path = ""; //$NON-NLS-1$
            if (wc != null) {
                path = ProcessUIUtil.createPath(wc);
            }
            List<ProcessRelevantData> prdList =
                    ProcessDataUtil.getProcessRelevantData(scope);
            List<ProcessRelevantData> formalParameterList =
                    new ArrayList<ProcessRelevantData>();
            if (prdList != null && !prdList.isEmpty()) {
                for (Iterator<ProcessRelevantData> iterator =
                        prdList.iterator(); iterator.hasNext();) {
                    Object obj = (Object) iterator.next();
                    if (obj instanceof FormalParameter) {
                        formalParameterList.add((ProcessRelevantData) obj);
                    }
                }
                items
                        .addAll(getProcessRelevantDataIndexerItemList(formalParameterList,
                                projectName,
                                path));
            }
        }
        return items;
    }

    /**
     * Get all data field and formal parameters from the model.
     * 
     * @param monitor
     *            progress monitor
     * @return set of <code>IDataPickerProxyItem</code> objects. Empty set if
     *         none found.
     */
    private Set<IDataPickerProxyItem> getAllDataFieldNFormalParam(EObject scope) {

        Set<IDataPickerProxyItem> items = new HashSet<IDataPickerProxyItem>();
        if (scope != null) {
            IProject project = WorkingCopyUtil.getProjectFor(scope);
            String projectName = ""; //$NON-NLS-1$
            if (project != null) {
                projectName = project.getName();
            }
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(scope);
            String path = ""; //$NON-NLS-1$
            if (wc != null) {
                path = ProcessUIUtil.createPath(wc);
            }
            List<ProcessRelevantData> prdList =
                    ProcessDataUtil.getProcessRelevantData(scope);
            if (prdList != null) {
                items.addAll(getProcessRelevantDataIndexerItemList(prdList,
                        projectName,
                        path));
            }
        }
        return items;
    }

    /**
     * Get all participants and data fields and parameters of type performer.
     * 
     * @param monitor
     *            progress monitor
     * @return set of <code>IDataPickerProxyItem</code> objects. Empty set if
     *         none found.
     */
    private Set<IDataPickerProxyItem> getAllParticipants(EObject scope) {

        Set<IDataPickerProxyItem> items = new HashSet<IDataPickerProxyItem>();
        if (scope != null) {
            String projectName =
                    DataFilterPickerProviderHelper.getProjectName(scope);
            String path = DataFilterPickerProviderHelper.getPath(scope);
            List<EObject> participantList =
                    ProcessDataUtil.getParticipants(scope);
            if (participantList != null) {
                items.addAll(getDataIndexerItemList(participantList,
                        projectName,
                        path));
            }
        }
        return items;
    }

    public static String getProjectName(EObject scope) {
        String projectName = ""; //$NON-NLS-1$
        if (scope != null) {
            IProject project = WorkingCopyUtil.getProjectFor(scope);
            if (project != null) {
                projectName = project.getName();
            }
        }
        return projectName;
    }

    public static String getPath(EObject scope) {
        String path = ""; //$NON-NLS-1$
        if (scope != null) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(scope);
            if (wc != null) {
                path = ProcessUIUtil.createPath(wc);
            }
        }
        return path;
    }

    public static Set<IDataPickerProxyItem> getDataIndexerItemList(
            List<EObject> elementList, EObject scope) {

        Set<IDataPickerProxyItem> items = new HashSet<IDataPickerProxyItem>();
        if (elementList != null && scope != null) {
            String projectName =
                    DataFilterPickerProviderHelper.getProjectName(scope);
            String path = DataFilterPickerProviderHelper.getPath(scope);
            if (projectName != null && path != null) {
                items =
                        DataFilterPickerProviderHelper
                                .getDataIndexerItemList(elementList,
                                        projectName,
                                        path);
            }
        }
        return items;
    }

    private static Set<IDataPickerProxyItem> getDataIndexerItemList(
            List<EObject> elementList, String projectName, String path) {

        Set<IDataPickerProxyItem> items = new HashSet<IDataPickerProxyItem>();
        if (elementList != null) {
            for (EObject element : elementList) {
                if (element instanceof ProcessRelevantData) {
                    items
                            .add(getProcessRelevantDataIndexerItem((ProcessRelevantData) element,
                                    projectName,
                                    path));
                } else if (element instanceof Participant) {
                    items.add(getParticipantIndexerItem((Participant) element,
                            projectName,
                            path));
                }
            }
        }
        return items;
    }

    private static String getDataIndexerItemName(EObject element) {
        String name = null;
        if (element instanceof ProcessRelevantData) {
            ProcessRelevantData prd = (ProcessRelevantData) element;
            name = prd.getName();
        } else if (element instanceof Participant) {
            Participant participant = (Participant) element;
            name = participant.getName();
        }
        return name;
    }

    private Set<IDataPickerProxyItem> getProcessRelevantDataIndexerItemList(
            List<ProcessRelevantData> prdList, String projectName, String path) {

        Set<IDataPickerProxyItem> items = new HashSet<IDataPickerProxyItem>();
        if (prdList != null) {
            for (ProcessRelevantData prd : prdList) {
                items.add(getProcessRelevantDataIndexerItem(prd,
                        projectName,
                        path));
            }
        }
        return items;
    }

    private static IDataPickerProxyItem getProcessRelevantDataIndexerItem(
            ProcessRelevantData prd, String projectName, String path) {
        String name = prd.getName();
        URI uri = ProcessUIUtil.getURI(prd, true);
        URI imageURI = ProcessDataUtil.getImageURI(prd);
        if (prd instanceof DataField) {
            name = ProcessUIUtil.getDataFieldQualifiedName(prd);
        } else {
            name = ProcessUIUtil.getFormalParameterQualifiedName(prd);
        }
        IDataPickerProxyItem item =
                DataFilterPickerProviderHelper.getInstance().new DataPickerItem(
                        name, uri, imageURI, prd);
        return item;
    }

    private static IDataPickerProxyItem getParticipantIndexerItem(
            Participant participant, String projectName, String path) {
        String name = ProcessUIUtil.getParticipantQualifiedName(participant);
        URI uri = ProcessUIUtil.getURI(participant, true);
        URI imageURI = null;
        imageURI =
                URI.createPlatformPluginURI(Xpdl2ResourcesPlugin.PLUGIN_ID
                        + "/" //$NON-NLS-1$
                        + Xpdl2ResourcesConsts.ICON_PARTICIPANT, true);
        IDataPickerProxyItem item =
                DataFilterPickerProviderHelper.getInstance().new DataPickerItem(
                        name, uri, imageURI, participant);
        return item;
    }

    /**
     * Implementation of the <code>IDataPickerProxyItem</code>
     * 
     * @author Miguel Torres
     * 
     */
    public class DataPickerItem implements IDataPickerProxyItem {

        private String qualifiedName;

        private String name;

        private URI uri;

        private URI imageUri;

        private Object item;

        /**
         * Constructor.
         * 
         * @param qualifiedName
         *            qualified name.
         * @param uri
         *            URI of the item.
         * @param imageUri
         *            URI of the image.
         */
        public DataPickerItem(String qualifiedName, URI uri, URI imageUri,
                Object item) {

            this.qualifiedName = qualifiedName;
            this.uri = uri;
            this.imageUri = imageUri;
            this.item = item;
        }

        /*
         * (non-Javadoc)
         */
        public Image getImage() {
            Image img = null;
            /*
             * XPD-2682 Changes to display icons based on Participant type, as
             * shown in Project Explorer View
             */
            if (item instanceof EObject) {
                img = WorkingCopyUtil.getImage((EObject) item);
            } else if (imageUri != null) {
                img = ExtendedImageRegistry.getInstance().getImage(imageUri);
            }

            return img;
        }

        /*
         * (non-Javadoc)
         */
        public String getName() {

            if (name == null) {
                if (item instanceof EObject) {
                    name = getDataIndexerItemName((EObject) item);
                }
                if (name == null) {
                    name = qualifiedName;
                }
            }
            return name;
        }

        /*
         * (non-Javadoc)
         */
        public String getQualifiedName() {
            return qualifiedName;
        }

        /*
         * (non-Javadoc)
         */
        public URI getURI() {
            return uri;
        }

        /*
         * (non-Javadoc)
         */
        public Object getItem() {
            return item;
        }

        @Override
        public int hashCode() {
            int code = super.hashCode();

            if (uri != null && qualifiedName != null) {
                code = uri.hashCode() + qualifiedName.hashCode();
            }

            return code;
        }

        @Override
        public boolean equals(Object obj) {
            boolean isEquals = false;

            if (obj == this) {
                isEquals = true;
            } else if (obj instanceof IDataPickerProxyItem) {
                IDataPickerProxyItem other = (IDataPickerProxyItem) obj;
                isEquals =
                        other.getQualifiedName().equals(getQualifiedName())
                                && other.getURI().equals(getURI());
            }

            return isEquals;
        }

    }

}
