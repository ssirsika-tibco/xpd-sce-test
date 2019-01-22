package com.tibco.xpd.processeditor.xpdl2.quickSearchContribution;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchPartReference;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.DiagramDropObjectUtils;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.Xpdl2GatewayAdapter;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.Xpdl2SequenceFlowAdapter;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.processwidget.adapters.SequenceFlowType;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchLabelProvider;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.ArtifactType;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.MessageFlow;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * ProcessEditorQuickSearchLabelProvider
 * 
 */
public class ProcessEditorQuickSearchLabelProvider extends
        AbstractQuickSearchLabelProvider {
    public static final String ELEMENT_PATH_SEPARATOR = "/"; //$NON-NLS-1$

    /**
     * @param partRef
     */
    public ProcessEditorQuickSearchLabelProvider(IWorkbenchPartReference partRef) {
        super(partRef);
    }

    @Override
    public String getText(Object element) {
        String ret = null;
        NamedElement namedEl = null;

        if (element instanceof Artifact) {
            Artifact art = (Artifact) element;

            if (ArtifactType.ANNOTATION_LITERAL.equals(art.getArtifactType())) {
                ret = art.getTextAnnotation();
                return (ret == null) ? "" : ret; //$NON-NLS-1$
            }

            namedEl = art;

        } else if (element instanceof NamedElement) {
            namedEl = (NamedElement) element;
        }

        if (namedEl != null) {
            ret = Xpdl2ModelUtil.getDisplayNameOrName((NamedElement) element);
        }

        if (ret == null) {
            ret = ""; //$NON-NLS-1$
        }

        return ret;
    }

    @Override
    public Image getImage(Object element) {
        Image img = null;

        if (element instanceof Activity) {
            Activity act = (Activity) element;

            if (ProcessEditorQuickSearchContribution.isTask(act)) {
                img =
                        DiagramDropObjectUtils
                                .getImageForTaskType(TaskObjectUtil
                                        .getTaskType(act));
            } else if (ProcessEditorQuickSearchContribution.isEvent(act)) {
                img =
                        DiagramDropObjectUtils
                                .getImageForEventType(EventObjectUtil
                                        .getFlowType(act), EventObjectUtil
                                        .getEventTriggerType(act));
            } else if (ProcessEditorQuickSearchContribution.isGateway(act)) {
                img =
                        DiagramDropObjectUtils
                                .getImageForGatewayType(Xpdl2GatewayAdapter
                                        .getGatewayType(act));

            }
        } else if (element instanceof Artifact) {
            Artifact art = (Artifact) element;

            if (ArtifactType.DATA_OBJECT_LITERAL.equals(art.getArtifactType())) {
                img =
                        ProcessWidgetPlugin
                                .getDefault()
                                .getImageRegistry()
                                .get(ProcessWidgetConstants.IMG_TOOL_DATA_OBJECT_16);
            } else if (ArtifactType.ANNOTATION_LITERAL.equals(art
                    .getArtifactType())) {
                img =
                        ProcessWidgetPlugin.getDefault().getImageRegistry()
                                .get(ProcessWidgetConstants.IMG_TOOL_NOTE_16);
            } else if (ArtifactType.GROUP_LITERAL.equals(art.getArtifactType())) {
                img =
                        ProcessWidgetPlugin.getDefault().getImageRegistry()
                                .get(ProcessWidgetConstants.IMG_TOOL_GROUP_16);
            }

        } else if (element instanceof Transition) {

            SequenceFlowType type =
                    Xpdl2SequenceFlowAdapter.getFlowType((Transition) element);

            if (SequenceFlowType.CONDITIONAL_LITERAL.equals(type)) {
                img =
                        ProcessWidgetPlugin
                                .getDefault()
                                .getImageRegistry()
                                .get(ProcessWidgetConstants.IMG_TOOL_FLOWCONDITIONAL_16);
            } else if (SequenceFlowType.DEFAULT_LITERAL.equals(type)) {
                img =
                        ProcessWidgetPlugin
                                .getDefault()
                                .getImageRegistry()
                                .get(ProcessWidgetConstants.IMG_TOOL_FLOWDEFAULT_16);
            } else {
                img =
                        ProcessWidgetPlugin.getDefault().getImageRegistry()
                                .get(ProcessWidgetConstants.IMG_TOOL_FLOW_16);
            }

        } else if (element instanceof MessageFlow) {
            img =
                    ProcessWidgetPlugin.getDefault().getImageRegistry()
                            .get(ProcessWidgetConstants.IMG_TOOL_MESSAGE_16);

        } else if (element instanceof Association) {
            img =
                    ProcessWidgetPlugin
                            .getDefault()
                            .getImageRegistry()
                            .get(ProcessWidgetConstants.IMG_TOOL_ASSOCIATION_16);

        }

        return img;
    }

    @Override
    public String getElementPath(Object element) {
        if (element instanceof Activity || element instanceof Artifact) {
            NamedElement actOrArt = (NamedElement) element;

            return getActivityOrArtifactElementPath(actOrArt);

        } else if (element instanceof Transition) {
            Transition tr = (Transition) element;

            Activity to = tr.getFlowContainer().getActivity(tr.getTo());
            Activity from = tr.getFlowContainer().getActivity(tr.getFrom());

            return getFromToElementPath(from, to);

        } else if (element instanceof MessageFlow) {
            MessageFlow flow = (MessageFlow) element;

            Package p = flow.getPackage();

            NamedElement from = p.findNamedElement(flow.getSource());
            NamedElement to = p.findNamedElement(flow.getTarget());

            return getFromToElementPath(from, to);

        } else if (element instanceof Association) {
            Association assoc = (Association) element;

            Package p = assoc.getPackage();

            NamedElement from = p.findNamedElement(assoc.getSource());
            NamedElement to = p.findNamedElement(assoc.getTarget());

            return getFromToElementPath(from, to);
        }

        return ""; //$NON-NLS-1$
    }

    /**
     * Return the element path for the given activity or artifact.
     * <p>
     * Including pool, lane and embedded sub-proc(s).
     * 
     * @param actOrArt
     * 
     * @return the element path for the given activity or artifact.
     */
    private String getActivityOrArtifactElementPath(NamedElement actOrArt) {
        Process process;

        if (actOrArt instanceof Activity) {
            process = ((Activity) actOrArt).getProcess();
        } else {
            process = Xpdl2ModelUtil.getProcess(actOrArt);
        }

        StringBuilder str = new StringBuilder();

        boolean first = true;
        EObject container = null;

        while (true) {
            String addName = ""; //$NON-NLS-1$

            container = Xpdl2ModelUtil.getContainer(actOrArt);

            if (container instanceof ActivitySet) {
                actOrArt =
                        Xpdl2ModelUtil.getEmbSubProcActivityForActSet(process,
                                ((ActivitySet) container).getId());
                if (actOrArt != null) {
                    addName = Xpdl2ModelUtil.getDisplayNameOrName(actOrArt);
                }

            } else {
                break;
            }

            if (first) {
                str.insert(0, addName);
            } else {
                str.insert(0, addName + ELEMENT_PATH_SEPARATOR);
            }

            first = false;
        }

        if (container instanceof Lane) {
            if (first) {
                str.insert(0, Xpdl2ModelUtil
                        .getDisplayNameOrName((Lane) container));
                first = false;
            } else {
                str.insert(0, Xpdl2ModelUtil
                        .getDisplayNameOrName((Lane) container)
                        + ELEMENT_PATH_SEPARATOR);
            }

            Pool pool = Xpdl2ModelUtil.getParentPool(container);
            if (pool != null) {
                if (first) {
                    str.insert(0, Xpdl2ModelUtil.getDisplayNameOrName(pool));
                    first = false;
                } else {
                    str.insert(0, Xpdl2ModelUtil.getDisplayNameOrName(pool)
                            + ELEMENT_PATH_SEPARATOR);
                }
            }
        }

        return str.toString();
    }

    /**
     * For a connection type element get the "From xxx to xxx" element path
     * string.
     * 
     * @param from
     * @param to
     * @return the "From xxx to xxx" element path string
     */
    private String getFromToElementPath(NamedElement from, NamedElement to) {
        String fromStr;

        if (from != null) {
            String name = Xpdl2ModelUtil.getDisplayNameOrName(from);
            if (name != null && name.length() != 0) {
                fromStr = name;
            } else {
                fromStr = String.format("<%s>", getElementTypeName(from)); //$NON-NLS-1$
            }

        } else {
            fromStr =
                    String
                            .format("<%s>", //$NON-NLS-1$
                                    Messages.ProcessEditorQuickSearchLabelProvider_Unknown_label);
        }

        String toStr;

        if (to != null) {
            String name = Xpdl2ModelUtil.getDisplayNameOrName(to);
            if (name != null && name.length() != 0) {
                toStr = name;
            } else {
                toStr = String.format("<%s>", getElementTypeName(to)); //$NON-NLS-1$
            }

        } else {
            toStr =
                    String
                            .format("<%s>", //$NON-NLS-1$
                                    Messages.ProcessEditorQuickSearchLabelProvider_Unknown_label);

        }

        return String
                .format(Messages.ProcessEditorQuickSearchLabelProvider_FromToFormat_shortdesc,
                        fromStr,
                        toStr);
    }

    @Override
    public String getElementTypeName(Object element) {
        if (element instanceof Activity) {
            Activity act = (Activity) element;

            if (ProcessEditorQuickSearchContribution.isTask(act)) {
                return Messages.ProcessEditorQuickSearchLabelProvider_Task_label;
            } else if (ProcessEditorQuickSearchContribution.isEvent(act)) {
                return Messages.ProcessEditorQuickSearchLabelProvider_Event_label;
            } else if (ProcessEditorQuickSearchContribution.isGateway(act)) {
                return Messages.ProcessEditorQuickSearchLabelProvider_Gateway_label;
            }
        } else if (element instanceof Artifact) {
            Artifact art = (Artifact) element;

            if (ArtifactType.DATA_OBJECT_LITERAL.equals(art.getArtifactType())) {
                return Messages.ProcessEditorQuickSearchLabelProvider_DataObject_label;
            } else if (ArtifactType.ANNOTATION_LITERAL.equals(art
                    .getArtifactType())) {
                return Messages.ProcessEditorQuickSearchLabelProvider_TextAnnot_label;
            } else if (ArtifactType.GROUP_LITERAL.equals(art.getArtifactType())) {
                return Messages.ProcessEditorQuickSearchLabelProvider_Group_label;
            }
        } else if (element instanceof Transition) {
            return Messages.ProcessEditorQuickSearchLabelProvider_SeqFlow_label;
        } else if (element instanceof MessageFlow) {
            return Messages.ProcessEditorQuickSearchLabelProvider_MsgFlow_label;
        } else if (element instanceof Association) {
            return Messages.ProcessEditorQuickSearchLabelProvider_Association_label;
        }

        return Messages.ProcessEditorQuickSearchLabelProvider_Unknown_label;

    }
}