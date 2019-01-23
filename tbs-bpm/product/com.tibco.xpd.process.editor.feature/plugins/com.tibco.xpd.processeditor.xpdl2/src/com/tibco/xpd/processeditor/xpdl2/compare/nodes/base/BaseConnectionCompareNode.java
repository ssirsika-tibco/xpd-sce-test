/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.compare.nodes.base;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.xpd.processeditor.xpdl2.compare.Messages;
import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.nodes.XpdPropertyInfoNode;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.ArtifactType;
import com.tibco.xpd.xpdl2.MessageFlow;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * 
 * @author aallway
 * @since 28 Oct 2010
 */
public abstract class BaseConnectionCompareNode extends
        ProcessDescendentNamedElementCompareNode {

    /**
     * @param namedElement
     * @param listIndex
     * @param feature
     * @param parentCompareNode
     * @param compareNodeFactory
     */
    public BaseConnectionCompareNode(NamedElement namedElement, int listIndex,
            EStructuralFeature feature, Object parentCompareNode,
            EMFCompareNodeFactory compareNodeFactory) {
        super(namedElement, listIndex, feature, parentCompareNode,
                compareNodeFactory);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.NamedElementCompareNode#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        /*
         * If connection transition has a name then use it. Otherwise list it as
         * "a" to "b"
         */
        String name = WorkingCopyUtil.getText(getEObject());
        if (name == null || name.length() == 0) {
            EObject source = getConnectionSourceObject();
            EObject target = getConnectionTargetObject();

            String sourceName = getObjectName(source);
            String targetName = getObjectName(target);

            name =
                    String
                            .format(Messages.BaseConnectionCompareNode_ConnectionNameFormat_label,
                                    sourceName,
                                    targetName);

        }

        return name;
    }

    /**
     * @param sourceOrTarget
     * 
     * @return the name of the source / target object
     */
    private String getObjectName(EObject sourceOrTarget) {
        String name = null;
        if (sourceOrTarget == null) {
            name = Messages.BaseConnectionCompareNode_Unknown_label;
        } else {
            if (sourceOrTarget instanceof Artifact
                    && ArtifactType.ANNOTATION_LITERAL
                            .equals(((Artifact) sourceOrTarget)
                                    .getArtifactType())) {
                name = ((Artifact) sourceOrTarget).getTextAnnotation();
                if (name != null) {
                    name = name.replaceAll("[ \t\r\n]", " "); //$NON-NLS-1$ //$NON-NLS-2$
                    if (name.length() > 50) {
                        name = name.substring(0, 50) + "...";//$NON-NLS-1$
                    }
                }

            } else if (sourceOrTarget instanceof NamedElement) {
                name =
                        Xpdl2ModelUtil
                                .getDisplayNameOrName((NamedElement) sourceOrTarget);
            }

            if (name == null || name.length() == 0) {
                if (sourceOrTarget instanceof Activity) {
                    Activity activity = (Activity) sourceOrTarget;

                    if (activity.getRoute() != null) {
                        name =
                                Messages.BaseConnectionCompareNode_UnnamedGateway_label;

                    } else if (activity.getEvent() != null) {
                        name =
                                Messages.BaseConnectionCompareNode_UnnamedEvent_label;
                    } else {
                        name =
                                Messages.BaseConnectionCompareNode_UnnamedTask_label;
                    }

                }
                /* Associaitons can connect to sequence / message flows. */
                else if (sourceOrTarget instanceof Transition) {
                    name =
                            Messages.BaseConnectionCompareNode_UnnamedSequenceFlow_label;

                } else if (sourceOrTarget instanceof MessageFlow) {
                    name =
                            Messages.BaseConnectionCompareNode_UnnamedMessageFlow_label;
                }

                /* Everything else is likely to be named. */
            }

            if (name == null || name.length() == 0) {
                name = Messages.BaseConnectionCompareNode_Unnamed_label;
            }
        }

        return name;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.NamedElementCompareNode#createInfoPropertyChildren()
     * 
     * @return
     */
    @Override
    protected Collection<XpdPropertyInfoNode> createInfoPropertyChildren() {
        Collection<XpdPropertyInfoNode> props =
                super.createInfoPropertyChildren();

        props.add(new XpdPropertyInfoNode(
                Messages.BaseConnectionCompareNode_From_label + " " //$NON-NLS-1$
                        + getObjectName(getConnectionSourceObject()), 15, this,
                this.getType(), "connectionSourceInfo")); //$NON-NLS-1$

        props.add(new XpdPropertyInfoNode(
                Messages.BaseConnectionCompareNode_To_label + " " //$NON-NLS-1$
                        + getObjectName(getConnectionTargetObject()), 16, this,
                this.getType(), "connectionSourceInfo")); //$NON-NLS-1$

        return props;
    }

    /**
     * @return source model object that connection is connected to.
     */
    protected abstract EObject getConnectionTargetObject();

    /**
     * @return target model object that connection is connected to.
     */
    protected abstract EObject getConnectionSourceObject();

}
