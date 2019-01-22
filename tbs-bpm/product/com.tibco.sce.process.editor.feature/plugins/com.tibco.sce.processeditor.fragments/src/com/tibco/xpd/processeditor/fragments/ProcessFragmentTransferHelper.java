package com.tibco.xpd.processeditor.fragments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

import com.tibco.xpd.analyst.resources.xpdl2.utils.Xpdl2ProcessorUtil;
import com.tibco.xpd.destinations.ui.GlobalDestinationHelper.GlobalDestinationInfo;
import com.tibco.xpd.fragments.dnd.FragmentLocalSelectionTransfer;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.ConnectorGraphicsInfo;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.MessageFlow;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ResourceImpl;

public class ProcessFragmentTransferHelper {

    private static final String DEST_DELIMITER = "::"; //$NON-NLS-1$

    private static final String DESTINATION = "Destination"; //$NON-NLS-1$

    public final static String FRAG_PARENTPROCESS_GRAPHIC_INFO =
            "Fragment_ParentProcess_GraphicInfo"; //$NON-NLS-1$

    private final static String DUMMYPOOL_ID = "_Fragment_Dummy_Pool_"; //$NON-NLS-1$

    private final static String DUMMYLANE_ID = "_Fragment_Dummy_Lane_"; //$NON-NLS-1$

    private final static String FRAG_FEEDBACKRECT_ATTRNAME =
            "FragmentFeedbackRect_"; //$NON-NLS-1$

    private static final String FEEDBACK_RECT = "FEEDBACK_RECT"; //$NON-NLS-1$

    private static final String DEST_ENV = "DEST_ENV"; //$NON-NLS-1$    

    public static ProcessFragmentTransferHelper INSTANCE =
            new ProcessFragmentTransferHelper();

    public FragmentDataObject getFragmentData(
            IStructuredSelection structuredSelection) {
        if (structuredSelection.getFirstElement() instanceof List) {
            ArrayList<EObject> originalModelObjects =
                    (ArrayList<EObject>) structuredSelection.getFirstElement();
            Collection<EObject> modelObjects =
                    EcoreUtil.copyAll(originalModelObjects);

            // Each fragment is a separate package, so create it.
            // Package tempPackage = Xpdl2Factory.eINSTANCE.createPackage();
            // String strfragPackage =
            // Xpdl2ProcessorUtil.getResourceString(tempPackage);
            // if (strfragPackage != null) {
            // Package fragPackage =
            // Xpdl2ProcessorUtil.getFragmentPackage(strfragPackage);

            Package fragPackage = Xpdl2Factory.eINSTANCE.createPackage();
            Process process = Xpdl2Factory.eINSTANCE.createProcess();
            addFeedbackRects(process);
            addDestinationEnvironment(process);
            String processName = "Custom Fragment"; //$NON-NLS-1$
            process.setName(NameUtil.getInternalName(processName, false));
            Xpdl2ModelUtil
                    .setOtherAttribute(process, XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_DisplayName(), processName);
            /*
             * If model objects contains lanes then we need to create a dummy
             * pool to put them in.
             */
            Pool dummyPool = null;
            boolean hasPools = false;
            boolean hasLanes = false;

            for (Iterator iter = modelObjects.iterator(); iter.hasNext();) {
                Object obj = (Object) iter.next();

                if (obj instanceof Activity) {
                    process.getActivities().add((Activity) obj);

                } else if (obj instanceof Transition) {
                    process.getTransitions().add((Transition) obj);

                } else if (obj instanceof ActivitySet) {
                    process.getActivitySets().add((ActivitySet) obj);

                } else if (obj instanceof Artifact) {
                    // Artifact will need ext attr to say what
                    // fragment
                    // process
                    // it belongs to.
                    setArtifactProcess(process, (Artifact) obj);

                    fragPackage.getArtifacts().add((Artifact) obj);

                } else if (obj instanceof Association) {
                    // Association will need to say what fragment
                    // process it
                    // belongs to.
                    setAssociationProcess(process, (Association) obj);

                    fragPackage.getAssociations().add((Association) obj);

                } else if (obj instanceof MessageFlow) {
                    // Association will need to say what fragment
                    // process it
                    // belongs to.
                    setMessageFlowProcess(process, (MessageFlow) obj);

                    fragPackage.getMessageFlows().add((MessageFlow) obj);

                } else if (obj instanceof Pool) {
                    ((Pool) obj).setProcessId(process.getId());

                    fragPackage.getPools().add((Pool) obj);

                    hasPools = true;

                } else if (obj instanceof Lane) {
                    // Have to create a dummy pool for it to live
                    // in.
                    if (dummyPool == null) {
                        dummyPool = Xpdl2Factory.eINSTANCE.createPool();
                        dummyPool.setName(DUMMYPOOL_ID);
                        dummyPool.setProcessId(process.getId());

                        UniqueIdElement uid = (UniqueIdElement) dummyPool;
                        uid
                                .eSet(Xpdl2Package.eINSTANCE
                                        .getUniqueIdElement_Id(),
                                        getDummyPoolId(process));

                        fragPackage.getPools().add(dummyPool);
                    }

                    dummyPool.getLanes().add((Lane) obj);

                    hasLanes = true;

                } else if (obj instanceof Participant) {
                    process.getParticipants().add((Participant) obj);
                } else if (obj instanceof DataField) {
                    process.getDataFields().add((DataField) obj);
                } else if (obj instanceof FormalParameter) {
                    process.getFormalParameters().add((FormalParameter) obj);
                } else if (obj instanceof TypeDeclaration) {
                    fragPackage.getTypeDeclarations()
                            .add((TypeDeclaration) obj);
                }
            }

            fragPackage.getProcesses().add(process);
            if (!hasPools) {
                fixDiagramObjectHierarchy(process, modelObjects);
            }
            registerPackage(fragPackage);
            ImageData fragImageData = null;
            Image processDiagramImage =
                    Xpdl2ProcessEditorPlugin
                            .getProcessDiagramImage(fragPackage, process
                                    .getId(), modelObjects);
            if (processDiagramImage != null) {
                fragImageData = processDiagramImage.getImageData();
            }

            return new FragmentDataObject(Xpdl2ProcessorUtil
                    .getResourceString(fragPackage), fragImageData);
            // }
        }
        return null;

    }

    private void registerPackage(Package fragPackage) {
        Xpdl2ResourceImpl res =
                new Xpdl2ResourceImpl(Xpdl2ProcessorUtil.XML_URI);
        res.getContents().add(fragPackage);

        res.getDefaultSaveOptions().put(XMLResource.OPTION_EXTENDED_META_DATA,
                Boolean.TRUE);
        res.getDefaultLoadOptions().put(XMLResource.OPTION_EXTENDED_META_DATA,
                Boolean.TRUE);

        res.eAdapters().add(new Xpdl2ProcessorUtil.FragmentAdapter());
    }

    /**
     * @param process
     * @param dndTransferObjects2
     */
    private static void fixDiagramObjectHierarchy(Process process,
            Collection<EObject> fragmentObjects) {

        // We can safely add this here to cope with older versions
        Pool pool = Xpdl2Factory.eINSTANCE.createPool();
        pool.eSet(Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(),
                getDummyPoolId(process));
        pool.setProcessId(process.getId());

        Lane lane = Xpdl2Factory.eINSTANCE.createLane();
        String dummyLaneId = getDummyLaneId(process);
        lane.eSet(Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(), dummyLaneId);
        NodeGraphicsInfo nodeGraphicsInfo =
                Xpdl2Factory.eINSTANCE.createNodeGraphicsInfo();
        lane.getNodeGraphicsInfos().add(nodeGraphicsInfo);
        setLaneHeight(fragmentObjects, lane);

        pool.getLanes().add(lane);
        replaceLaneId(process, fragmentObjects, lane);
        process.getPackage().getPools().add(pool);

        return;
    }

    /**
     * 
     * @param graphicalNodes
     * @param lane
     */
    public static void setLaneHeight(Collection<EObject> graphicalNodes,
            Lane lane) {
        org.eclipse.swt.graphics.Rectangle rect =
                new org.eclipse.swt.graphics.Rectangle(0, 0, 0, 0);
        org.eclipse.swt.graphics.Rectangle r = null;
        for (Object obj : graphicalNodes) {
            if (obj instanceof GraphicalNode) {
                r = Xpdl2ModelUtil.getObjectBounds((GraphicalNode) obj);
                rect = rect.union(r);
            }
        }
        if (!(lane.getNodeGraphicsInfos().isEmpty())) {
            NodeGraphicsInfo ng = lane.getNodeGraphicsInfos().get(0);
            ng.setHeight(rect.height + 100);
        }
    }

    /**
     * @param process
     * @param fragmentObjects
     * @param lane
     */
    private static void replaceLaneId(Process process,
            final Collection<EObject> fragmentObjects, final Lane lane) {
        HashSet<String> actSetIds = new HashSet<String>();
        for (EObject eo : fragmentObjects) {
            if (eo instanceof ActivitySet) {
                actSetIds.add(((ActivitySet) eo).getId());
            }
        }
        // Replace the lane id in activities and artifacts.
        for (EObject eo : fragmentObjects) {
            if (eo instanceof GraphicalNode) {
                NodeGraphicsInfo gn =
                        Xpdl2ModelUtil.getNodeGraphicsInfo((GraphicalNode) eo);
                if (gn != null) {
                    String laneId = gn.getLaneId();
                    if (laneId != null) {
                        if (actSetIds.contains(laneId)) {
                            // Artifacts have laneid =
                            // actset id, if the given
                            // activity set IS in the dnd
                            // transfer objects
                            // then we do not need to place
                            // the artifact in our
                            // dummy lane.
                            continue;
                        }
                    }

                    // Top level activity or artifact (not
                    // it act included
                    // activity set) must have dummy lane
                    // id.
                    gn.setLaneId(lane.getId());

                }
            }
        }

    }

    /**
     * @param process
     * @param obj
     */
    public static void setArtifactProcess(Process process, Artifact art) {
        // Easiest place to store this is in our own
        // connector graphics info in something convenient.
        NodeGraphicsInfo gi =
                Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(art,
                        FRAG_PARENTPROCESS_GRAPHIC_INFO);
        gi.setLaneId(process.getId());
    }

    public static String getArtifactProcess(Artifact art) {
        NodeGraphicsInfo gi =
                Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(art,
                        FRAG_PARENTPROCESS_GRAPHIC_INFO);
        if (gi != null) {
            String procId = gi.getLaneId();
            if (procId != null) {
                return procId;
            }
        }
        return null;
    }

    /**
     * Associations are package level but need to be associated with particular
     * fragment process.
     * 
     * 
     * @param process
     * @param obj
     */
    public static void setAssociationProcess(Process process, Association ass) {
        // Easiest place to store this is in our own
        // connector graphics info in something convenient.
        ConnectorGraphicsInfo gi =
                Xpdl2ModelUtil.getOrCreateConnectorGraphicsInfo(ass,
                        FRAG_PARENTPROCESS_GRAPHIC_INFO);
        gi.setStyle(process.getId());
    }

    /**
     * Message flows are package level but need to be associated with particular
     * fragment process.
     * 
     * 
     * @param process
     * @param obj
     */
    public static void setMessageFlowProcess(Process process,
            MessageFlow msgFlow) {
        // Easiest place to store this is in our own
        // connector graphics info in something convenient.
        ConnectorGraphicsInfo gi =
                Xpdl2ModelUtil.getOrCreateConnectorGraphicsInfo(msgFlow,
                        FRAG_PARENTPROCESS_GRAPHIC_INFO);
        gi.setStyle(process.getId());
    }

    /**
     * Get dummy pool id.
     */
    private static String getDummyPoolId(Process process) {
        return DUMMYPOOL_ID + process.getId();
    }

    /**
     * Get lane id.
     */
    private static String getDummyLaneId(Process process) {
        return DUMMYLANE_ID + process.getId();
    }

    private void addDestinationEnvironment(Process process) {
        Collection<GlobalDestinationInfo> destinationEnvironments =
                getDestinationEnvironments();
        if (destinationEnvironments instanceof Collection) {
            List<ExtendedAttribute> extAttrs = process.getExtendedAttributes();
            for (GlobalDestinationInfo globalDestInfo : destinationEnvironments) {
                ExtendedAttribute extAttr =
                        Xpdl2Factory.eINSTANCE.createExtendedAttribute();
                extAttr.setName(DESTINATION);
                extAttr.setValue(globalDestInfo.getId() + DEST_DELIMITER
                        + globalDestInfo.getName());
                extAttrs.add(extAttr);
            }
        } else {
            Object destObj =
                    FragmentLocalSelectionTransfer.getTransfer()
                            .getProperties().get(DEST_ENV);
            if (destObj instanceof HashMap) {
                HashMap destMap = (HashMap) destObj;
                Iterator it = destMap.keySet().iterator();
                List<ExtendedAttribute> extAttrs =
                        process.getExtendedAttributes();

                while (it.hasNext()) {
                    String str = (String) it.next();
                    ExtendedAttribute extAttr =
                            Xpdl2Factory.eINSTANCE.createExtendedAttribute();
                    extAttr.setName(DESTINATION);
                    extAttr.setValue(str + DEST_DELIMITER + destMap.get(str));
                    extAttrs.add(extAttr);
                }
            }
        }
    }

    private Collection<GlobalDestinationInfo> getDestinationEnvironments() {
        Object object =
                FragmentLocalSelectionTransfer.getTransfer().getProperties()
                        .get(DEST_ENV);
        if (object instanceof Collection) {
            return (Collection) object;
        }

        return null;
    }

    private void addFeedbackRects(Process process) {
        //
        // Add extended attributes for all the feedback rectangles.
        // These are fragment process ext attrs "FragmentFeedbackRect_'n'"
        Collection<Rectangle> feedbackRects = getFeedbackRects();
        if (feedbackRects != null) {
            EList extAttrs = process.getExtendedAttributes();

            int i = 1;

            for (Rectangle rc : feedbackRects) {
                ExtendedAttribute extAttr =
                        Xpdl2Factory.eINSTANCE.createExtendedAttribute();

                extAttr.setName(FRAG_FEEDBACKRECT_ATTRNAME + i);
                i++;
                extAttr.setValue(rc.x + "," + rc.y + "," + rc.width + "," //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        + rc.height);

                extAttrs.add(extAttr);
            }
        }
    }

    private Collection<Rectangle> getFeedbackRects() {

        Object object =
                FragmentLocalSelectionTransfer.getTransfer().getProperties()
                        .get(FEEDBACK_RECT);
        if (object instanceof Collection) {
            return (Collection) object;
        }
        return null;
    }

}
