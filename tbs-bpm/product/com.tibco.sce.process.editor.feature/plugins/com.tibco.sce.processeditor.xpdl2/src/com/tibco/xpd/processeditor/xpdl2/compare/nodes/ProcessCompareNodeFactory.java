/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.compare.nodes;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.jface.resource.ImageRegistry;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.processeditor.xpdl2.compare.Messages;
import com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.ActivityCompareNode;
import com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.NamedElementCompareNode;
import com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.NamedElementListCompareNode;
import com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.UniqueIdElementCompareNode;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.nodes.emf.WrappedEListCompareNode;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.MessageFlow;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * EMFCompareNodeFactory for creating compare nodes specialised to the process
 * model.
 * 
 * @author aallway
 * @since 4 Oct 2010
 */
public class ProcessCompareNodeFactory extends EMFCompareNodeFactory {

    /**
     * @param compareNodeContentType
     */
    public ProcessCompareNodeFactory(String compareNodeContentType) {
        super(compareNodeContentType);
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory#createForEObject(org.eclipse.emf.ecore.EObject,
     *      org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
     * 
     * @param eObject
     * @param listIndex
     * @param feature
     * @param parentCompareNode
     * 
     * @return Specialises certain elements for process packages.
     */
    @Override
    public Object createForEObject(EObject eObject, int listIndex,
            EStructuralFeature feature, Object parentCompareNode) {
        Object compareNode = null;

        /*
         * Create specialised nodes for packages and processes.
         */
        if (eObject instanceof Package) {
            compareNode =
                    new PackageCompareNode((Package) eObject, listIndex,
                            feature, parentCompareNode, this);

        } else if (eObject instanceof Process) {
            compareNode =
                    new ProcessCompareNode((Process) eObject, listIndex,
                            feature, parentCompareNode, this);
        }
        /*
         * Create specialised node for Lane (it will contain the activities
         * instead of process.
         */
        else if (eObject instanceof Lane) {
            compareNode =
                    new LaneCompareNode((Lane) eObject, listIndex, feature,
                            parentCompareNode, this);
        }

        /*
         * Specialise for tasks.
         */
        else if (eObject instanceof Activity) {
            if (((Activity) eObject).getBlockActivity() != null) {
                compareNode =
                        new EmbeddedSubProcCompareNode((Activity) eObject,
                                listIndex, feature, parentCompareNode, this);

            } else if (TaskObjectUtil.getTaskTypeStrict((Activity) eObject) != null) {
                compareNode =
                        new TaskActivityCompareNode((Activity) eObject,
                                listIndex, feature, parentCompareNode, this);

            } else if (((Activity) eObject).getEvent() != null) {
                compareNode =
                        new EventActivityCompareNode((Activity) eObject,
                                listIndex, feature, parentCompareNode, this);

            } else if (((Activity) eObject).getRoute() != null) {
                compareNode =
                        new GatewayActivityCompareNode((Activity) eObject,
                                listIndex, feature, parentCompareNode, this);

            } else {
                compareNode =
                        new ActivityCompareNode((Activity) eObject, listIndex,
                                feature, parentCompareNode, this);
            }
        } else if (eObject instanceof Artifact) {
            compareNode =
                    new ArtifactCompareNode((Artifact) eObject, listIndex,
                            feature, parentCompareNode, this);
        }
        /*
         * Specialise for flows.
         */
        else if (eObject instanceof Transition) {
            compareNode =
                    new SequenceFlowCompareNode((Transition) eObject,
                            listIndex, feature, parentCompareNode, this);

        } else if (eObject instanceof MessageFlow) {
            compareNode =
                    new MessageFlowCompareNode((MessageFlow) eObject,
                            listIndex, feature, parentCompareNode, this);

        } else if (eObject instanceof Association) {
            compareNode =
                    new AssociationCompareNode((Association) eObject,
                            listIndex, feature, parentCompareNode, this);

        }
        /*
         * Specialise for data.
         */
        else if (eObject instanceof ProcessRelevantData) {
            compareNode =
                    new ProcessRelevantDataCompareNode(
                            (ProcessRelevantData) eObject, listIndex, feature,
                            parentCompareNode, this);
        } else if (eObject instanceof TypeDeclaration) {
            compareNode =
                    new TypeDeclarationCompareNode((TypeDeclaration) eObject,
                            listIndex, feature, parentCompareNode, this);
        } else if (eObject instanceof Participant) {
            compareNode =
                    new ParticipantCompareNode((Participant) eObject,
                            listIndex, feature, parentCompareNode, this);
        }
        /*
         * Specialise for process interface events.
         */
        else if (eObject instanceof InterfaceMethod) {
            compareNode =
                    new InterfaceMethodCompareNode((InterfaceMethod) eObject,
                            listIndex, feature, parentCompareNode, this);
        } else if (eObject instanceof ErrorMethod) {
            compareNode =
                    new InterfaceErrorMethodCompareNode((ErrorMethod) eObject,
                            listIndex, feature, parentCompareNode, this);
        }
        /*
         * If it's a named element then use compare node that uses label for
         * labelling the object .
         */
        else if (eObject instanceof NamedElement) {
            compareNode =
                    new NamedElementCompareNode((NamedElement) eObject,
                            listIndex, feature, parentCompareNode, this);
        }
        /*
         * Otherwise if its just a unique id element then use compare node that
         * uses it's unique id for 'equivalent model object in tree' comparison.
         */
        else if (eObject instanceof UniqueIdElement) {
            compareNode =
                    new UniqueIdElementCompareNode((UniqueIdElement) eObject,
                            listIndex, feature, parentCompareNode, this);
        }
        /*
         * Use default factory.
         */
        else {
            compareNode =
                    super.createForEObject(eObject,
                            listIndex,
                            feature,
                            parentCompareNode);
        }

        return compareNode;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory#createForEList(org.eclipse.emf.common.util.EList,
     *      org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
     * 
     * @param eList
     * @param feature
     * @param parentCompareNode
     * @return
     */
    @Override
    public Object createForEList(EList eList, EStructuralFeature feature,
            Object parentCompareNode) {

        Object compareNode = null;

        ImageRegistry ir = Xpdl2ResourcesPlugin.getDefault().getImageRegistry();

        if (feature == Xpdl2Package.eINSTANCE.getPackage_Processes()) {
            compareNode =
                    new NamedElementListCompareNode(eList, feature,
                            parentCompareNode, this,
                            Messages.ProcessCompareNodeFactory_Processes_label,
                            ir.get(Xpdl2ResourcesConsts.ICON_PROCESS));

        } else if (feature == Xpdl2Package.eINSTANCE
                .getDataFieldsContainer_DataFields()) {
            compareNode =
                    new NamedElementListCompareNode(
                            eList,
                            feature,
                            parentCompareNode,
                            this,
                            Messages.ProcessCompareNodeFactory_DataFields_label,
                            ir.get(Xpdl2ResourcesConsts.ICON_DATAFIELD));

        } else if (feature == Xpdl2Package.eINSTANCE
                .getParticipantsContainer_Participants()) {
            compareNode =
                    new NamedElementListCompareNode(
                            eList,
                            feature,
                            parentCompareNode,
                            this,
                            Messages.ProcessCompareNodeFactory_Participants_label,
                            ir.get(Xpdl2ResourcesConsts.ICON_PARTICIPANT));

        } else if (feature == Xpdl2Package.eINSTANCE
                .getPackage_TypeDeclarations()) {
            compareNode =
                    new NamedElementListCompareNode(eList, feature,
                            parentCompareNode, this,
                            Messages.ProcessCompareNodeFactory_TypeDecl_label,
                            ir.get(Xpdl2ResourcesConsts.ICON_TYPEDECLARATION));

        } else if (feature == Xpdl2Package.eINSTANCE
                .getFormalParametersContainer_FormalParameters()) {
            compareNode =
                    new NamedElementListCompareNode(eList, feature,
                            parentCompareNode, this,
                            Messages.ProcessCompareNodeFactory_Params_label,
                            ir.get(Xpdl2ResourcesConsts.ICON_FORMALPARAMETER));

        } else if (feature == Xpdl2Package.eINSTANCE
                .getFlowContainer_Activities()) {

            compareNode =
                    new WrappedEListCompareNode(
                            eList,
                            feature,
                            parentCompareNode,
                            this,
                            Messages.ProcessCompareNodeFactory_Activities_label,
                            ir.get(Xpdl2ResourcesConsts.ICON_TASK_FOLDER));

        } else if (feature == Xpdl2Package.eINSTANCE.getPackage_Pools()) {
            compareNode =
                    new NamedElementListCompareNode(eList, feature,
                            parentCompareNode, this,
                            Messages.ProcessCompareNodeFactory_Pools_label, ir
                                    .get(Xpdl2ResourcesConsts.IMG_POOL));

        } else if (feature == Xpdl2Package.eINSTANCE.getPool_Lanes()) {
            compareNode =
                    new NamedElementListCompareNode(eList, feature,
                            parentCompareNode, this,
                            Messages.ProcessCompareNodeFactory_Lanes_label, ir
                                    .get(Xpdl2ResourcesConsts.IMG_LANE));

        } else if (feature == Xpdl2Package.eINSTANCE.getPackage_Artifacts()) {
            compareNode =
                    new WrappedEListCompareNode(eList, feature,
                            parentCompareNode, this,
                            Messages.ProcessCompareNodeFactory_Artifacts_label,
                            ir.get(Xpdl2ResourcesConsts.IMG_DATAOBJECT));

        } else if (feature == Xpdl2Package.eINSTANCE
                .getFlowContainer_Transitions()) {
            compareNode =
                    new WrappedEListCompareNode(
                            eList,
                            feature,
                            parentCompareNode,
                            this,
                            Messages.ProcessCompareNodeFactory_SeqFlows_label,
                            ir
                                    .get(Xpdl2ResourcesConsts.ICON_SEQUENCEFLOW_FOLDER));

        } else if (feature == Xpdl2Package.eINSTANCE.getPackage_MessageFlows()) {
            compareNode =
                    new WrappedEListCompareNode(
                            eList,
                            feature,
                            parentCompareNode,
                            this,
                            Messages.ProcessCompareNodeFactory_MessageFlows_label,
                            ir
                                    .get(Xpdl2ResourcesConsts.ICON_MESSAGEFLOW_FOLDER));

        } else if (feature == Xpdl2Package.eINSTANCE.getPackage_Associations()) {
            compareNode =
                    new WrappedEListCompareNode(
                            eList,
                            feature,
                            parentCompareNode,
                            this,
                            Messages.ProcessCompareNodeFactory_Associations_label,
                            ir
                                    .get(Xpdl2ResourcesConsts.ICON_ASSOCIATIONS_FOLDER));

        }
        /*
         * NOTE that ProcessInterface list is missing here because it is created
         * manually by PackageCompareNode (becuase it is included as direct
         * content of package rather than it's natural position under
         * otherElements
         */
        else if (feature == XpdExtensionPackage.eINSTANCE
                .getProcessInterface_StartMethods()) {
            compareNode =
                    new WrappedEListCompareNode(
                            eList,
                            feature,
                            parentCompareNode,
                            this,
                            Messages.ProcessCompareNodeFactory_StartEvents_label,
                            ir
                                    .get(Xpdl2ResourcesConsts.ICON_INTERFACE_STARTEVENT));

        } else if (feature == XpdExtensionPackage.eINSTANCE
                .getProcessInterface_IntermediateMethods()) {
            compareNode =
                    new WrappedEListCompareNode(
                            eList,
                            feature,
                            parentCompareNode,
                            this,
                            Messages.ProcessCompareNodeFactory_IntermediateEvents_label,
                            ir
                                    .get(Xpdl2ResourcesConsts.ICON_INTERFACE_INTERMEDIATEEVENT));

        } else if (feature == XpdExtensionPackage.eINSTANCE
                .getInterfaceMethod_ErrorMethods()) {
            compareNode =
                    new WrappedEListCompareNode(
                            eList,
                            feature,
                            parentCompareNode,
                            this,
                            Messages.ProcessCompareNodeFactory_Error,
                            ir
                                    .get(Xpdl2ResourcesConsts.ICON_INTERFACE_ERROREVENT));

        }

        /* Fall back on default node. */
        if (compareNode == null) {
            compareNode =
                    super.createForEList(eList, feature, parentCompareNode);
        }

        return compareNode;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory#createForFeatureMap(org.eclipse.emf.ecore.util.FeatureMap,
     *      org.eclipse.emf.ecore.EObject,
     *      org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
     * 
     * @param featureMap
     * @param parent
     * @param feature
     * @param parentCompareNode
     * @return
     */
    @Override
    public Object createForFeatureMap(FeatureMap featureMap, EObject parent,
            EStructuralFeature feature, Object parentCompareNode) {
        if (feature == Xpdl2Package.eINSTANCE
                .getOtherElementsContainer_OtherElements()) {
            /*
             * Create a specialised feature map for Package/##otherElements that
             * excludes ProcessInterface element as this is managed manually
             * (process interface list is faked to be directly under package)
             */
            return new PackageOtherElementsCompareNode(featureMap, parent,
                    feature, parentCompareNode, this);

        }
        return super.createForFeatureMap(featureMap,
                parent,
                feature,
                parentCompareNode);
    }

    /**
     * Decide what is atomic node.
     * <p>
     * The children of an atomic node cannot be selected for direct comparison
     * in merge content view unless they are themselves atomic. i.e. the merge
     * content viewer's input will be set to the nearest available atomic input
     * ancestor of the dbl-clicked entry in the structure diff viewer (top view
     * of compare editor).
     * 
     * @see com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory#isAtomic(org.eclipse.emf.ecore.EStructuralFeature,
     *      java.lang.Object,
     *      com.tibco.xpd.resources.ui.compare.nodes.emf.EObjectCompareNode)
     * 
     * @param feature
     * @param value
     * @param parentModelObject
     * @return <code>true</code> if object is atomic.
     */
    @Override
    public boolean isAtomic(EStructuralFeature feature, Object value,
            Object parentModelObject) {
        if (value instanceof EObject) {
            return isEObjectAtomic((EObject) value);
        } else if (value instanceof EList) {
            return isEListAtomic((EList) value, feature);
        }

        /* By default we want nothing ot be atomic (not copyable in isolation). */
        return false;
    }

    /**
     * 
     * @param object
     * 
     * @return <code>true</code> if object is selectable in it's own right (and
     *         hence can be copied in it's own right if it is also editable).
     */
    private boolean isEObjectAtomic(EObject object) {

        if (object instanceof Package || object instanceof Process
                || object instanceof ProcessInterface) {
            return true;

        } else if (object instanceof Participant
                || object instanceof TypeDeclaration
                || object instanceof DataField
                || object instanceof FormalParameter) {
            return true;

        } else if (object instanceof Pool || object instanceof Lane) {
            return true;

        } else if (object instanceof Activity || object instanceof ActivitySet
                || object instanceof InterfaceMethod
                || object instanceof ErrorMethod || object instanceof Artifact) {
            return true;

        } else if (object instanceof Transition
                || object instanceof MessageFlow
                || object instanceof Association) {
            return true;

        }
        return false;
    }

    /**
     * @param list
     * @param feature
     * @return <code>true</code> if list is selectable in it's own right (and
     *         hence can be copied in it's own right if it is also editable)
     */
    private boolean isEListAtomic(EList list, EStructuralFeature feature) {
        if (feature == Xpdl2Package.eINSTANCE
                .getParticipantsContainer_Participants()) {
            return true;
        } else if (feature == Xpdl2Package.eINSTANCE
                .getDataFieldsContainer_DataFields()) {
            return true;
        } else if (feature == Xpdl2Package.eINSTANCE
                .getPackage_TypeDeclarations()) {
            return true;
        } else if (feature == Xpdl2Package.eINSTANCE
                .getFormalParametersContainer_FormalParameters()) {
            return true;
        } else if (feature == Xpdl2Package.eINSTANCE.getPackage_MessageFlows()) {
            return true;
        } else if (feature == Xpdl2Package.eINSTANCE.getPackage_Pools()) {
            return true;
        } else if (feature == Xpdl2Package.eINSTANCE.getPool_Lanes()) {
            return true;
        } else if (feature == Xpdl2Package.eINSTANCE.getPackage_Associations()) {
            return true;
        } else if (feature == Xpdl2Package.eINSTANCE.getPackage_Artifacts()) {
            return true;
        }

        else if (feature == Xpdl2Package.eINSTANCE.getPackage_Processes()) {
            return true;
        } else if (feature == Xpdl2Package.eINSTANCE
                .getFlowContainer_Activities()) {
            return true;
        } else if (feature == Xpdl2Package.eINSTANCE
                .getFlowContainer_Transitions()) {
            return true;
        }

        else if (feature == XpdExtensionPackage.eINSTANCE
                .getProcessInterfaces_ProcessInterface()) {
            return true;
        } else if (feature == XpdExtensionPackage.eINSTANCE
                .getProcessInterface_StartMethods()) {
            return true;
        } else if (feature == XpdExtensionPackage.eINSTANCE
                .getProcessInterface_IntermediateMethods()) {
            return true;
        } else if (feature == XpdExtensionPackage.eINSTANCE
                .getInterfaceMethod_ErrorMethods()) {
            return true;
        }

        return false;
    }

}
