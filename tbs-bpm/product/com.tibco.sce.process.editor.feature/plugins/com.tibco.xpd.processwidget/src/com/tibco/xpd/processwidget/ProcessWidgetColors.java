package com.tibco.xpd.processwidget;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.graphics.Color;

import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;
import com.tibco.xpd.processwidget.adapters.GraphicalColorAdapter;
import com.tibco.xpd.processwidget.parts.ModelAdapterEditPart;
import com.tibco.xpd.resources.ui.XpdColorRegistry;

/**
 * Place holder for various colour related utilities.
 * 
 * Currently this class... - Provides the default colours for diagram objects /
 * flows according to their type (by edit part). - Currently these are all
 * static.
 * 
 * TODO: Get colour for object in following priority (per type)... - Use widget
 * adapter to get user-custom colour for type. - Lookup preferences for user-set
 * default type for colour. - Use statically defined default colour.
 * 
 * 
 * 
 * @author aallway
 * 
 */
public final class ProcessWidgetColors {

    private static Map<ProcessWidgetType, ProcessWidgetColors> instances =
            new HashMap<ProcessWidgetType, ProcessWidgetColors>();

    /**
     * The base default colors for all flow node objects...
     */
    private static WidgetRGB baseFillColor = new WidgetRGB(255, 219, 74);

    private static WidgetRGB lighterBaseFillColor =
            new WidgetRGB(255, 230, 138);

    private static WidgetRGB darkerBaseFillColor = new WidgetRGB(223, 179, 0);

    private static WidgetRGB greyFillColor = new WidgetRGB(128, 128, 128);

    private static WidgetRGB baseLineColor = new WidgetRGB(0, 0, 128);

    private static WidgetRGB pageflowFillColor = new WidgetRGB(143, 191, 251);

    private static WidgetRGB lighterPageflowFillColor = new WidgetRGB(173, 208,
            251);

    private static WidgetRGB darkerPageflowFillColor = new WidgetRGB(82, 134,
            198);

    private static WidgetRGB businessServPageflowFillColor = new WidgetRGB(205,
            158, 233);

    private static WidgetRGB lighterBizServPageflowFillColor = new WidgetRGB(
            235, 170, 233);

    private static WidgetRGB darkerBizServPageflowFillColor = new WidgetRGB(
            155, 105, 222);

    private static WidgetRGB caseServPageflowFillColor = new WidgetRGB(166,
            208, 152);

    private static WidgetRGB lighterCaseServPageflowFillColor = new WidgetRGB(
            186, 221, 152);

    private static WidgetRGB darkerCaseServPageflowFillColor = new WidgetRGB(
            210, 158, 152);

    private static WidgetRGB serviceProcessFillColor = new WidgetRGB(168, 186,
            200);

    private static WidgetRGB lighterServiceProcessFillColor = new WidgetRGB(
            196, 205, 200);

    private static WidgetRGB darkerServiceProcessFillColor = new WidgetRGB(205,
            133, 200);

    private static WidgetRGB taskLibraryBaseLineColor =
            new WidgetRGB(0, 0, 128);

    private static WidgetRGB taskLibraryBaseFillColor = new WidgetRGB(255, 219,
            74);

    private static WidgetRGB lighterTaskLibraryFillColor = new WidgetRGB(255,
            230, 138);

    private static WidgetRGB darkerTaskLibraryFillColor = new WidgetRGB(223,
            179, 0);

    private static WidgetRGB decisionFlowBaseLineColor = new WidgetRGB(0, 0,
            128);

    private static WidgetRGB decisionFlowBaseFillColor = new WidgetRGB(130,
            196, 189);

    private static WidgetRGB lighterDecisionFlowFillColor = new WidgetRGB(220,
            255, 250);

    private static WidgetRGB darkerDecisionFlowFillColor = new WidgetRGB(223,
            179, 0);

    /**
     * Constants for the various colours for various types. Note: (Make sure
     * that the strings contain the word "Fill" for fill colors and "Border" for
     * border colors)
     */
    /** Task Types... */
    public static String TASK_FILL = "TaskFillColor"; //$NON-NLS-1$

    public static String TASK_LINE = "TaskLineColor"; //$NON-NLS-1$

    public static String USER_TASK_FILL = "UserTaskFillColor"; //$NON-NLS-1$

    public static String USER_TASK_LINE = "UserTaskLineColor"; //$NON-NLS-1$

    public static String SERVICE_TASK_FILL = "ServiceTaskFillColor"; //$NON-NLS-1$

    public static String SERVICE_TASK_LINE = "ServiceTaskLineColor"; //$NON-NLS-1$

    public static String RECEIVE_TASK_FILL = "ReceiveTaskFillColor"; //$NON-NLS-1$

    public static String RECEIVE_TASK_LINE = "ReceiveTaskLineColor"; //$NON-NLS-1$

    public static String SEND_TASK_FILL = "SendTaskFillColor"; //$NON-NLS-1$

    public static String SEND_TASK_LINE = "SendTaskLineColor"; //$NON-NLS-1$

    public static String MANUAL_TASK_FILL = "ManualTaskFillColor"; //$NON-NLS-1$

    public static String MANUAL_TASK_LINE = "ManualTaskLineColor"; //$NON-NLS-1$

    public static String SCRIPT_TASK_FILL = "ScriptTaskFillColor"; //$NON-NLS-1$

    public static String SCRIPT_TASK_LINE = "ScriptTaskLineColor"; //$NON-NLS-1$

    public static String SUBPROCESS_TASK_FILL = "SubProcessTaskFillColor"; //$NON-NLS-1$

    public static String SUBPROCESS_TASK_LINE = "SubProcessTaskLineColor"; //$NON-NLS-1$

    public static String EMBEDDEDSUBPROCESS_TASK_FILL =
            "EmbeddedSubProcessTaskFillColor"; //$NON-NLS-1$

    public static String EMBEDDEDSUBPROCESS_TASK_LINE =
            "EmbeddedSubProcessTaskLineColor"; //$NON-NLS-1$

    public static String REFERENCE_TASK_FILL = "ReferenceTaskFillColor"; //$NON-NLS-1$

    public static String REFERENCE_TASK_LINE = "ReferencerTaskLineColor"; //$NON-NLS-1$

    /** Event Types... */
    public static String START_EVENT_FILL = "StartEventFillColor"; //$NON-NLS-1$

    public static String START_EVENT_LINE = "StartEventLineColor"; //$NON-NLS-1$

    public static String INTERMEDIATE_EVENT_FILL = "EventFillColor"; //$NON-NLS-1$

    public static String INTERMEDIATE_EVENT_LINE = "EventLineColor"; //$NON-NLS-1$

    public static String END_EVENT_FILL = "EndEventFillColor"; //$NON-NLS-1$

    public static String END_EVENT_LINE = "EndEventLineColor"; //$NON-NLS-1$

    /** Interface Method Types */
    public static String INTERFACE_START_EVENT_FILL =
            "InterfaceStartEventFillColor"; //$NON-NLS-1$

    public static String INTERFACE_INTERMEDIATE_EVENT_FILL =
            "InterfaceIntermediateEventFillColor"; //$NON-NLS-1$

    /** Gateway Types... */
    public static String GATEWAY_FILL = "GatewayFillColor"; //$NON-NLS-1$

    public static String GATEWAY_LINE = "GatewayLineColor"; //$NON-NLS-1$

    public static String EXCLUSIVE_DATA_GATEWAY_FILL =
            "XORDataGatewayFillColor"; //$NON-NLS-1$

    public static String XORDATA_GATEWAY_LINE = "XORDataGatewayLineColor"; //$NON-NLS-1$

    public static String EXCLUSIVE_EVENT_GATEWAY_FILL =
            "XOREventGatewayFillColor"; //$NON-NLS-1$

    public static String XOREVENT_GATEWAY_LINE = "XOREventGatewayLineColor"; //$NON-NLS-1$

    public static String INCLUSIVE_GATEWAY_FILL = "ORGatewayFillColor"; //$NON-NLS-1$

    public static String OR_GATEWAY_LINE = "ORGatewayLineColor"; //$NON-NLS-1$

    public static String COMPLEX_GATEWAY_FILL = "ComplexGatewayFillColor"; //$NON-NLS-1$

    public static String COMPLEX_GATEWAY_LINE = "ComplexGatewayLineColor"; //$NON-NLS-1$

    public static String PARALLEL_GATEWAY_FILL = "ANDGatewayFillColor"; //$NON-NLS-1$

    public static String AND_GATEWAY_LINE = "ANDGatewayLineColor"; //$NON-NLS-1$

    /** Miscellaneous */
    public static String DATAOBJECT_FILL = "DataObjectFillColor"; //$NON-NLS-1$

    public static String DATAOBJECT_LINE = "DataObjectLineColor"; //$NON-NLS-1$

    public static String NOTE_FILL = "NoteFillColor"; //$NON-NLS-1$

    public static String NOTE_LINE = "NoteLineColor"; //$NON-NLS-1$

    public static String GROUP_LINE = "GroupLineColor"; //$NON-NLS-1$

    public static String LANE_FILL = "EvenLaneFillColor"; //$NON-NLS-1$

    public static String LANE_ALTERNATE_FILL = "AlternateLaneFillColor"; //$NON-NLS-1$

    public static String LANE_LINE = "EvenLaneLineColor"; //$NON-NLS-1$

    public static String POOL_HEADER_FILL = "PoolHeaderFillColor"; //$NON-NLS-1$

    public static String POOL_HEADER_LINE = "PoolHeaderLineColor"; //$NON-NLS-1$

    public static String UNCONTROLLED_SEQ_FLOW_LINE =
            "UncontrolledSeqFlowLineColor"; //$NON-NLS-1$

    public static String CONDITIONAL_SEQ_FLOW_LINE =
            "ConditionalSeqFlowLineColor"; //$NON-NLS-1$

    public static String DEFAULT_SEQ_FLOW_LINE = "DefaultSeqFlowLineColor"; //$NON-NLS-1$

    public static String MESSAGE_FLOW_LINE = "MessageFlowLineColor"; //$NON-NLS-1$

    public static String ASSOCIATION_LINE = "AssociationFlowLineColor"; //$NON-NLS-1$

    public static String DTABLE_TASK_FILL = "DtableTaskFillColor"; //$NON-NLS-1$

    public static String DTABLE_TASK_LINE = "DtableTaskLineColor"; //$NON-NLS-1$

    /**
     * TypeColor - Little type id to color class.
     * 
     */
    private static class TypeColor {
        public String typeID;

        public WidgetRGB defColor;

        public WidgetRGB prefColor;

        public TypeColor(String typeID, WidgetRGB color) {
            this.typeID = typeID;
            this.defColor = color;
            this.prefColor = null;
        }
    }

    /**
     * The ally defined default colours...
     */
    private static TypeColor[] businessProcessColors = {
    /** Task Types... */
    new TypeColor(TASK_FILL, baseFillColor),
            new TypeColor(TASK_LINE, baseLineColor),
            new TypeColor(USER_TASK_FILL, baseFillColor),
            new TypeColor(USER_TASK_LINE, baseLineColor),
            new TypeColor(SERVICE_TASK_FILL, baseFillColor),
            new TypeColor(SERVICE_TASK_LINE, baseLineColor),
            new TypeColor(RECEIVE_TASK_FILL, baseFillColor),
            new TypeColor(RECEIVE_TASK_LINE, baseLineColor),
            new TypeColor(SEND_TASK_FILL, baseFillColor),
            new TypeColor(SEND_TASK_LINE, baseLineColor),
            new TypeColor(MANUAL_TASK_FILL, baseFillColor),
            new TypeColor(MANUAL_TASK_LINE, baseLineColor),
            new TypeColor(SCRIPT_TASK_FILL, baseFillColor),
            new TypeColor(SCRIPT_TASK_LINE, baseLineColor),
            new TypeColor(SUBPROCESS_TASK_FILL, baseFillColor),
            new TypeColor(SUBPROCESS_TASK_LINE, baseLineColor),
            new TypeColor(EMBEDDEDSUBPROCESS_TASK_FILL, lighterBaseFillColor),
            new TypeColor(EMBEDDEDSUBPROCESS_TASK_LINE, baseLineColor),
            new TypeColor(REFERENCE_TASK_FILL, baseFillColor),
            new TypeColor(REFERENCE_TASK_LINE, baseLineColor),
            /** Event Types... */
            new TypeColor(START_EVENT_FILL, baseFillColor),
            new TypeColor(START_EVENT_LINE, baseLineColor),
            new TypeColor(INTERMEDIATE_EVENT_FILL, baseFillColor),
            new TypeColor(INTERMEDIATE_EVENT_LINE, baseLineColor),
            new TypeColor(END_EVENT_FILL, baseFillColor),
            new TypeColor(END_EVENT_LINE, baseLineColor),
            /** Interface Event Types */
            new TypeColor(INTERFACE_START_EVENT_FILL, greyFillColor),
            new TypeColor(INTERFACE_INTERMEDIATE_EVENT_FILL, greyFillColor),

            /** Gateway Types... */
            new TypeColor(EXCLUSIVE_DATA_GATEWAY_FILL, baseFillColor),
            new TypeColor(XORDATA_GATEWAY_LINE, baseLineColor),
            new TypeColor(EXCLUSIVE_EVENT_GATEWAY_FILL, baseFillColor),
            new TypeColor(XOREVENT_GATEWAY_LINE, baseLineColor),
            new TypeColor(INCLUSIVE_GATEWAY_FILL, baseFillColor),
            new TypeColor(OR_GATEWAY_LINE, baseLineColor),
            new TypeColor(COMPLEX_GATEWAY_FILL, baseFillColor),
            new TypeColor(COMPLEX_GATEWAY_LINE, baseLineColor),
            new TypeColor(PARALLEL_GATEWAY_FILL, baseFillColor),
            new TypeColor(AND_GATEWAY_LINE, baseLineColor),
            /** Miscellaneous */
            new TypeColor(DATAOBJECT_FILL, baseFillColor),
            new TypeColor(DATAOBJECT_LINE, baseLineColor),
            new TypeColor(NOTE_FILL, null),
            new TypeColor(NOTE_LINE, baseLineColor),
            new TypeColor(GROUP_LINE, baseLineColor),
            new TypeColor(LANE_FILL, new WidgetRGB(128, 155, 183)),
            new TypeColor(LANE_ALTERNATE_FILL, new WidgetRGB(128, 155, 183)),
            new TypeColor(LANE_LINE, baseLineColor),
            new TypeColor(POOL_HEADER_FILL, darkerBaseFillColor),
            new TypeColor(POOL_HEADER_LINE, baseLineColor),
            /** Connections... */
            new TypeColor(UNCONTROLLED_SEQ_FLOW_LINE, baseLineColor),
            new TypeColor(CONDITIONAL_SEQ_FLOW_LINE, baseLineColor),
            new TypeColor(DEFAULT_SEQ_FLOW_LINE, baseLineColor),
            new TypeColor(MESSAGE_FLOW_LINE, baseLineColor),
            new TypeColor(ASSOCIATION_LINE, baseLineColor)

    };

    /**
     * The Pageflow default colours...
     */
    private static TypeColor[] pageflowProcessColors = {
            /** Task Types... */
            new TypeColor(TASK_FILL, pageflowFillColor),
            new TypeColor(TASK_LINE, baseLineColor),
            new TypeColor(USER_TASK_FILL, pageflowFillColor),
            new TypeColor(USER_TASK_LINE, baseLineColor),
            new TypeColor(SERVICE_TASK_FILL, pageflowFillColor),
            new TypeColor(SERVICE_TASK_LINE, baseLineColor),
            new TypeColor(RECEIVE_TASK_FILL, pageflowFillColor),
            new TypeColor(RECEIVE_TASK_LINE, baseLineColor),
            new TypeColor(SEND_TASK_FILL, pageflowFillColor),
            new TypeColor(SEND_TASK_LINE, baseLineColor),
            new TypeColor(MANUAL_TASK_FILL, pageflowFillColor),
            new TypeColor(MANUAL_TASK_LINE, baseLineColor),
            new TypeColor(SCRIPT_TASK_FILL, pageflowFillColor),
            new TypeColor(SCRIPT_TASK_LINE, baseLineColor),
            new TypeColor(SUBPROCESS_TASK_FILL, pageflowFillColor),
            new TypeColor(SUBPROCESS_TASK_LINE, baseLineColor),
            new TypeColor(EMBEDDEDSUBPROCESS_TASK_FILL,
                    lighterPageflowFillColor),
            new TypeColor(EMBEDDEDSUBPROCESS_TASK_LINE, baseLineColor),
            new TypeColor(REFERENCE_TASK_FILL, pageflowFillColor),
            new TypeColor(REFERENCE_TASK_LINE, baseLineColor),
            /** Event Types... */
            new TypeColor(START_EVENT_FILL, pageflowFillColor),
            new TypeColor(START_EVENT_LINE, baseLineColor),
            new TypeColor(INTERMEDIATE_EVENT_FILL, pageflowFillColor),
            new TypeColor(INTERMEDIATE_EVENT_LINE, baseLineColor),
            new TypeColor(END_EVENT_FILL, pageflowFillColor),
            new TypeColor(END_EVENT_LINE, baseLineColor),
            /** Interface Event Types */
            new TypeColor(INTERFACE_START_EVENT_FILL, greyFillColor),
            new TypeColor(INTERFACE_INTERMEDIATE_EVENT_FILL, greyFillColor),

            /** Gateway Types... */
            new TypeColor(EXCLUSIVE_DATA_GATEWAY_FILL, pageflowFillColor),
            new TypeColor(XORDATA_GATEWAY_LINE, baseLineColor),
            new TypeColor(EXCLUSIVE_EVENT_GATEWAY_FILL, pageflowFillColor),
            new TypeColor(XOREVENT_GATEWAY_LINE, baseLineColor),
            new TypeColor(INCLUSIVE_GATEWAY_FILL, pageflowFillColor),
            new TypeColor(OR_GATEWAY_LINE, baseLineColor),
            new TypeColor(COMPLEX_GATEWAY_FILL, pageflowFillColor),
            new TypeColor(COMPLEX_GATEWAY_LINE, baseLineColor),
            new TypeColor(PARALLEL_GATEWAY_FILL, pageflowFillColor),
            new TypeColor(AND_GATEWAY_LINE, baseLineColor),
            /** Miscellaneous */
            new TypeColor(DATAOBJECT_FILL, pageflowFillColor),
            new TypeColor(DATAOBJECT_LINE, baseLineColor),
            new TypeColor(NOTE_FILL, null),
            new TypeColor(NOTE_LINE, baseLineColor),
            new TypeColor(GROUP_LINE, baseLineColor),
            new TypeColor(LANE_FILL, new WidgetRGB(91, 179, 164)),
            new TypeColor(LANE_ALTERNATE_FILL, new WidgetRGB(91, 179, 164)),
            new TypeColor(LANE_LINE, baseLineColor),
            new TypeColor(POOL_HEADER_FILL, darkerPageflowFillColor),
            new TypeColor(POOL_HEADER_LINE, baseLineColor),
            /** Connections... */
            new TypeColor(UNCONTROLLED_SEQ_FLOW_LINE, baseLineColor),
            new TypeColor(CONDITIONAL_SEQ_FLOW_LINE, baseLineColor),
            new TypeColor(DEFAULT_SEQ_FLOW_LINE, baseLineColor),
            new TypeColor(MESSAGE_FLOW_LINE, baseLineColor),
            new TypeColor(ASSOCIATION_LINE, baseLineColor)

    };

    /**
     * The Business Service Pageflow default colours...
     */
    private static TypeColor[] bizServPageflowProcessColors =
            {
                    /** Task Types... */
                    new TypeColor(TASK_FILL, businessServPageflowFillColor),
                    new TypeColor(TASK_LINE, baseLineColor),
                    new TypeColor(USER_TASK_FILL, businessServPageflowFillColor),
                    new TypeColor(USER_TASK_LINE, baseLineColor),
                    new TypeColor(SERVICE_TASK_FILL,
                            businessServPageflowFillColor),
                    new TypeColor(SERVICE_TASK_LINE, baseLineColor),
                    new TypeColor(RECEIVE_TASK_FILL,
                            businessServPageflowFillColor),
                    new TypeColor(RECEIVE_TASK_LINE, baseLineColor),
                    new TypeColor(SEND_TASK_FILL, businessServPageflowFillColor),
                    new TypeColor(SEND_TASK_LINE, baseLineColor),
                    new TypeColor(MANUAL_TASK_FILL,
                            businessServPageflowFillColor),
                    new TypeColor(MANUAL_TASK_LINE, baseLineColor),
                    new TypeColor(SCRIPT_TASK_FILL,
                            businessServPageflowFillColor),
                    new TypeColor(SCRIPT_TASK_LINE, baseLineColor),
                    new TypeColor(SUBPROCESS_TASK_FILL,
                            businessServPageflowFillColor),
                    new TypeColor(SUBPROCESS_TASK_LINE, baseLineColor),
                    new TypeColor(EMBEDDEDSUBPROCESS_TASK_FILL,
                            lighterBizServPageflowFillColor),
                    new TypeColor(EMBEDDEDSUBPROCESS_TASK_LINE, baseLineColor),
                    new TypeColor(REFERENCE_TASK_FILL,
                            businessServPageflowFillColor),
                    new TypeColor(REFERENCE_TASK_LINE, baseLineColor),
                    /** Event Types... */
                    new TypeColor(START_EVENT_FILL,
                            businessServPageflowFillColor),
                    new TypeColor(START_EVENT_LINE, baseLineColor),
                    new TypeColor(INTERMEDIATE_EVENT_FILL,
                            businessServPageflowFillColor),
                    new TypeColor(INTERMEDIATE_EVENT_LINE, baseLineColor),
                    new TypeColor(END_EVENT_FILL, businessServPageflowFillColor),
                    new TypeColor(END_EVENT_LINE, baseLineColor),
                    /** Interface Event Types */
                    new TypeColor(INTERFACE_START_EVENT_FILL, greyFillColor),
                    new TypeColor(INTERFACE_INTERMEDIATE_EVENT_FILL,
                            greyFillColor),

                    /** Gateway Types... */
                    new TypeColor(EXCLUSIVE_DATA_GATEWAY_FILL,
                            businessServPageflowFillColor),
                    new TypeColor(XORDATA_GATEWAY_LINE, baseLineColor),
                    new TypeColor(EXCLUSIVE_EVENT_GATEWAY_FILL,
                            businessServPageflowFillColor),
                    new TypeColor(XOREVENT_GATEWAY_LINE, baseLineColor),
                    new TypeColor(INCLUSIVE_GATEWAY_FILL,
                            businessServPageflowFillColor),
                    new TypeColor(OR_GATEWAY_LINE, baseLineColor),
                    new TypeColor(COMPLEX_GATEWAY_FILL,
                            businessServPageflowFillColor),
                    new TypeColor(COMPLEX_GATEWAY_LINE, baseLineColor),
                    new TypeColor(PARALLEL_GATEWAY_FILL,
                            businessServPageflowFillColor),
                    new TypeColor(AND_GATEWAY_LINE, baseLineColor),
                    /** Miscellaneous */
                    new TypeColor(DATAOBJECT_FILL,
                            businessServPageflowFillColor),
                    new TypeColor(DATAOBJECT_LINE, baseLineColor),
                    new TypeColor(NOTE_FILL, null),
                    new TypeColor(NOTE_LINE, baseLineColor),
                    new TypeColor(GROUP_LINE, baseLineColor),
                    new TypeColor(LANE_FILL, new WidgetRGB(91, 179, 164)),
                    new TypeColor(LANE_ALTERNATE_FILL, new WidgetRGB(91, 179,
                            164)),
                    new TypeColor(LANE_LINE, baseLineColor),
                    new TypeColor(POOL_HEADER_FILL,
                            darkerBizServPageflowFillColor),
                    new TypeColor(POOL_HEADER_LINE, baseLineColor),
                    /** Connections... */
                    new TypeColor(UNCONTROLLED_SEQ_FLOW_LINE, baseLineColor),
                    new TypeColor(CONDITIONAL_SEQ_FLOW_LINE, baseLineColor),
                    new TypeColor(DEFAULT_SEQ_FLOW_LINE, baseLineColor),
                    new TypeColor(MESSAGE_FLOW_LINE, baseLineColor),
                    new TypeColor(ASSOCIATION_LINE, baseLineColor)

            };

    /**
     * The Case Service Pageflow default colours...
     */
    private static TypeColor[] caseServPageflowProcessColors = {
            /** Task Types... */
            new TypeColor(TASK_FILL, caseServPageflowFillColor),
            new TypeColor(TASK_LINE, baseLineColor),
            new TypeColor(USER_TASK_FILL, caseServPageflowFillColor),
            new TypeColor(USER_TASK_LINE, baseLineColor),
            new TypeColor(SERVICE_TASK_FILL, caseServPageflowFillColor),
            new TypeColor(SERVICE_TASK_LINE, baseLineColor),
            new TypeColor(RECEIVE_TASK_FILL, caseServPageflowFillColor),
            new TypeColor(RECEIVE_TASK_LINE, baseLineColor),
            new TypeColor(SEND_TASK_FILL, caseServPageflowFillColor),
            new TypeColor(SEND_TASK_LINE, baseLineColor),
            new TypeColor(MANUAL_TASK_FILL, caseServPageflowFillColor),
            new TypeColor(MANUAL_TASK_LINE, baseLineColor),
            new TypeColor(SCRIPT_TASK_FILL, caseServPageflowFillColor),
            new TypeColor(SCRIPT_TASK_LINE, baseLineColor),
            new TypeColor(SUBPROCESS_TASK_FILL, caseServPageflowFillColor),
            new TypeColor(SUBPROCESS_TASK_LINE, baseLineColor),
            new TypeColor(EMBEDDEDSUBPROCESS_TASK_FILL,
                    lighterCaseServPageflowFillColor),
            new TypeColor(EMBEDDEDSUBPROCESS_TASK_LINE, baseLineColor),
            new TypeColor(REFERENCE_TASK_FILL, caseServPageflowFillColor),
            new TypeColor(REFERENCE_TASK_LINE, baseLineColor),
            /** Event Types... */
            new TypeColor(START_EVENT_FILL, caseServPageflowFillColor),
            new TypeColor(START_EVENT_LINE, baseLineColor),
            new TypeColor(INTERMEDIATE_EVENT_FILL, caseServPageflowFillColor),
            new TypeColor(INTERMEDIATE_EVENT_LINE, baseLineColor),
            new TypeColor(END_EVENT_FILL, caseServPageflowFillColor),
            new TypeColor(END_EVENT_LINE, baseLineColor),
            /** Interface Event Types */
            new TypeColor(INTERFACE_START_EVENT_FILL, greyFillColor),
            new TypeColor(INTERFACE_INTERMEDIATE_EVENT_FILL, greyFillColor),

            /** Gateway Types... */
            new TypeColor(EXCLUSIVE_DATA_GATEWAY_FILL,
                    caseServPageflowFillColor),
            new TypeColor(XORDATA_GATEWAY_LINE, baseLineColor),
            new TypeColor(EXCLUSIVE_EVENT_GATEWAY_FILL,
                    caseServPageflowFillColor),
            new TypeColor(XOREVENT_GATEWAY_LINE, baseLineColor),
            new TypeColor(INCLUSIVE_GATEWAY_FILL, caseServPageflowFillColor),
            new TypeColor(OR_GATEWAY_LINE, baseLineColor),
            new TypeColor(COMPLEX_GATEWAY_FILL, caseServPageflowFillColor),
            new TypeColor(COMPLEX_GATEWAY_LINE, baseLineColor),
            new TypeColor(PARALLEL_GATEWAY_FILL, caseServPageflowFillColor),
            new TypeColor(AND_GATEWAY_LINE, baseLineColor),
            /** Miscellaneous */
            new TypeColor(DATAOBJECT_FILL, caseServPageflowFillColor),
            new TypeColor(DATAOBJECT_LINE, baseLineColor),
            new TypeColor(NOTE_FILL, null),
            new TypeColor(NOTE_LINE, baseLineColor),
            new TypeColor(GROUP_LINE, baseLineColor),
            new TypeColor(LANE_FILL, new WidgetRGB(91, 179, 164)),
            new TypeColor(LANE_ALTERNATE_FILL, new WidgetRGB(91, 179, 164)),
            new TypeColor(LANE_LINE, baseLineColor),
            new TypeColor(POOL_HEADER_FILL, darkerCaseServPageflowFillColor),
            new TypeColor(POOL_HEADER_LINE, baseLineColor),
            /** Connections... */
            new TypeColor(UNCONTROLLED_SEQ_FLOW_LINE, baseLineColor),
            new TypeColor(CONDITIONAL_SEQ_FLOW_LINE, baseLineColor),
            new TypeColor(DEFAULT_SEQ_FLOW_LINE, baseLineColor),
            new TypeColor(MESSAGE_FLOW_LINE, baseLineColor),
            new TypeColor(ASSOCIATION_LINE, baseLineColor)

    };

    /**
     * The Case Service Pageflow default colours...
     */
    private static TypeColor[] serviceProcessColors =
            {
                    /** Task Types... */
                    new TypeColor(TASK_FILL, serviceProcessFillColor),
                    new TypeColor(TASK_LINE, baseLineColor),
                    new TypeColor(USER_TASK_FILL, serviceProcessFillColor),
                    new TypeColor(USER_TASK_LINE, baseLineColor),
                    new TypeColor(SERVICE_TASK_FILL, serviceProcessFillColor),
                    new TypeColor(SERVICE_TASK_LINE, baseLineColor),
                    new TypeColor(RECEIVE_TASK_FILL, serviceProcessFillColor),
                    new TypeColor(RECEIVE_TASK_LINE, baseLineColor),
                    new TypeColor(SEND_TASK_FILL, serviceProcessFillColor),
                    new TypeColor(SEND_TASK_LINE, baseLineColor),
                    new TypeColor(MANUAL_TASK_FILL, serviceProcessFillColor),
                    new TypeColor(MANUAL_TASK_LINE, baseLineColor),
                    new TypeColor(SCRIPT_TASK_FILL, serviceProcessFillColor),
                    new TypeColor(SCRIPT_TASK_LINE, baseLineColor),
                    new TypeColor(SUBPROCESS_TASK_FILL, serviceProcessFillColor),
                    new TypeColor(SUBPROCESS_TASK_LINE, baseLineColor),
                    new TypeColor(EMBEDDEDSUBPROCESS_TASK_FILL,
                            lighterServiceProcessFillColor),
                    new TypeColor(EMBEDDEDSUBPROCESS_TASK_LINE, baseLineColor),
                    new TypeColor(REFERENCE_TASK_FILL, serviceProcessFillColor),
                    new TypeColor(REFERENCE_TASK_LINE, baseLineColor),
                    /** Event Types... */
                    new TypeColor(START_EVENT_FILL, serviceProcessFillColor),
                    new TypeColor(START_EVENT_LINE, baseLineColor),
                    new TypeColor(INTERMEDIATE_EVENT_FILL,
                            serviceProcessFillColor),
                    new TypeColor(INTERMEDIATE_EVENT_LINE, baseLineColor),
                    new TypeColor(END_EVENT_FILL, serviceProcessFillColor),
                    new TypeColor(END_EVENT_LINE, baseLineColor),
                    /** Interface Event Types */
                    new TypeColor(INTERFACE_START_EVENT_FILL, greyFillColor),
                    new TypeColor(INTERFACE_INTERMEDIATE_EVENT_FILL,
                            greyFillColor),

                    /** Gateway Types... */
                    new TypeColor(EXCLUSIVE_DATA_GATEWAY_FILL,
                            serviceProcessFillColor),
                    new TypeColor(XORDATA_GATEWAY_LINE, baseLineColor),
                    new TypeColor(EXCLUSIVE_EVENT_GATEWAY_FILL,
                            serviceProcessFillColor),
                    new TypeColor(XOREVENT_GATEWAY_LINE, baseLineColor),
                    new TypeColor(INCLUSIVE_GATEWAY_FILL,
                            serviceProcessFillColor),
                    new TypeColor(OR_GATEWAY_LINE, baseLineColor),
                    new TypeColor(COMPLEX_GATEWAY_FILL, serviceProcessFillColor),
                    new TypeColor(COMPLEX_GATEWAY_LINE, baseLineColor),
                    new TypeColor(PARALLEL_GATEWAY_FILL,
                            serviceProcessFillColor),
                    new TypeColor(AND_GATEWAY_LINE, baseLineColor),
                    /** Miscellaneous */
                    new TypeColor(DATAOBJECT_FILL, serviceProcessFillColor),
                    new TypeColor(DATAOBJECT_LINE, baseLineColor),
                    new TypeColor(NOTE_FILL, null),
                    new TypeColor(NOTE_LINE, baseLineColor),
                    new TypeColor(GROUP_LINE, baseLineColor),
                    new TypeColor(LANE_FILL, new WidgetRGB(91, 179, 164)),
                    new TypeColor(LANE_ALTERNATE_FILL, new WidgetRGB(91, 179,
                            164)),
                    new TypeColor(LANE_LINE, baseLineColor),
                    new TypeColor(POOL_HEADER_FILL,
                            darkerServiceProcessFillColor),
                    new TypeColor(POOL_HEADER_LINE, baseLineColor),
                    /** Connections... */
                    new TypeColor(UNCONTROLLED_SEQ_FLOW_LINE, baseLineColor),
                    new TypeColor(CONDITIONAL_SEQ_FLOW_LINE, baseLineColor),
                    new TypeColor(DEFAULT_SEQ_FLOW_LINE, baseLineColor),
                    new TypeColor(MESSAGE_FLOW_LINE, baseLineColor),
                    new TypeColor(ASSOCIATION_LINE, baseLineColor)

            };

    /**
     * The Task Library default colours...
     */
    private static TypeColor[] taskLibraryColors =
            {
                    /** Task Types... */
                    new TypeColor(TASK_FILL, taskLibraryBaseFillColor),
                    new TypeColor(TASK_LINE, taskLibraryBaseLineColor),
                    new TypeColor(USER_TASK_FILL, taskLibraryBaseFillColor),
                    new TypeColor(USER_TASK_LINE, taskLibraryBaseLineColor),
                    new TypeColor(SERVICE_TASK_FILL, taskLibraryBaseFillColor),
                    new TypeColor(SERVICE_TASK_LINE, taskLibraryBaseLineColor),
                    new TypeColor(RECEIVE_TASK_FILL, taskLibraryBaseFillColor),
                    new TypeColor(RECEIVE_TASK_LINE, taskLibraryBaseLineColor),
                    new TypeColor(SEND_TASK_FILL, taskLibraryBaseFillColor),
                    new TypeColor(SEND_TASK_LINE, taskLibraryBaseLineColor),
                    new TypeColor(MANUAL_TASK_FILL, taskLibraryBaseFillColor),
                    new TypeColor(MANUAL_TASK_LINE, taskLibraryBaseLineColor),
                    new TypeColor(SCRIPT_TASK_FILL, taskLibraryBaseFillColor),
                    new TypeColor(SCRIPT_TASK_LINE, taskLibraryBaseLineColor),
                    new TypeColor(SUBPROCESS_TASK_FILL,
                            taskLibraryBaseFillColor),
                    new TypeColor(SUBPROCESS_TASK_LINE,
                            taskLibraryBaseLineColor),
                    new TypeColor(EMBEDDEDSUBPROCESS_TASK_FILL,
                            lighterTaskLibraryFillColor),
                    new TypeColor(EMBEDDEDSUBPROCESS_TASK_LINE,
                            taskLibraryBaseLineColor),
                    new TypeColor(REFERENCE_TASK_FILL, taskLibraryBaseFillColor),
                    new TypeColor(REFERENCE_TASK_LINE, taskLibraryBaseLineColor),
                    /** Event Types... */
                    new TypeColor(START_EVENT_FILL, taskLibraryBaseFillColor),
                    new TypeColor(START_EVENT_LINE, taskLibraryBaseLineColor),
                    new TypeColor(INTERMEDIATE_EVENT_FILL,
                            taskLibraryBaseFillColor),
                    new TypeColor(INTERMEDIATE_EVENT_LINE,
                            taskLibraryBaseLineColor),
                    new TypeColor(END_EVENT_FILL, taskLibraryBaseFillColor),
                    new TypeColor(END_EVENT_LINE, taskLibraryBaseLineColor),
                    /** Interface Event Types */
                    new TypeColor(INTERFACE_START_EVENT_FILL, greyFillColor),
                    new TypeColor(INTERFACE_INTERMEDIATE_EVENT_FILL,
                            greyFillColor),

                    /** Gateway Types... */
                    new TypeColor(EXCLUSIVE_DATA_GATEWAY_FILL,
                            taskLibraryBaseFillColor),
                    new TypeColor(XORDATA_GATEWAY_LINE,
                            taskLibraryBaseLineColor),
                    new TypeColor(EXCLUSIVE_EVENT_GATEWAY_FILL,
                            taskLibraryBaseFillColor),
                    new TypeColor(XOREVENT_GATEWAY_LINE,
                            taskLibraryBaseLineColor),
                    new TypeColor(INCLUSIVE_GATEWAY_FILL,
                            taskLibraryBaseFillColor),
                    new TypeColor(OR_GATEWAY_LINE, taskLibraryBaseLineColor),
                    new TypeColor(COMPLEX_GATEWAY_FILL,
                            taskLibraryBaseFillColor),
                    new TypeColor(COMPLEX_GATEWAY_LINE,
                            taskLibraryBaseLineColor),
                    new TypeColor(PARALLEL_GATEWAY_FILL,
                            taskLibraryBaseFillColor),
                    new TypeColor(AND_GATEWAY_LINE, taskLibraryBaseLineColor),
                    /** Miscellaneous */
                    new TypeColor(DATAOBJECT_FILL, taskLibraryBaseFillColor),
                    new TypeColor(DATAOBJECT_LINE, taskLibraryBaseLineColor),
                    new TypeColor(NOTE_FILL, null),
                    new TypeColor(NOTE_LINE, taskLibraryBaseLineColor),
                    new TypeColor(GROUP_LINE, taskLibraryBaseLineColor),
                    new TypeColor(LANE_FILL, new WidgetRGB(128, 128, 192)),
                    new TypeColor(LANE_ALTERNATE_FILL, new WidgetRGB(180, 180,
                            216)),
                    new TypeColor(LANE_LINE, taskLibraryBaseLineColor),
                    new TypeColor(POOL_HEADER_FILL, darkerTaskLibraryFillColor),
                    new TypeColor(POOL_HEADER_LINE, taskLibraryBaseLineColor),
                    /** Connections... */
                    new TypeColor(UNCONTROLLED_SEQ_FLOW_LINE,
                            taskLibraryBaseLineColor),
                    new TypeColor(CONDITIONAL_SEQ_FLOW_LINE,
                            taskLibraryBaseLineColor),
                    new TypeColor(DEFAULT_SEQ_FLOW_LINE,
                            taskLibraryBaseLineColor),
                    new TypeColor(MESSAGE_FLOW_LINE, taskLibraryBaseLineColor),
                    new TypeColor(ASSOCIATION_LINE, taskLibraryBaseLineColor)

            };

    /**
     * The Decision Flow default colours...
     */
    private static TypeColor[] decisionFlowColors =
            {
                    /** Task Types... */
                    /*
                     * SID Re-Instated all colors because one day we may require
                     * the other object types.
                     */
                    new TypeColor(TASK_FILL, decisionFlowBaseFillColor),
                    new TypeColor(TASK_LINE, decisionFlowBaseLineColor),
                    new TypeColor(USER_TASK_FILL, decisionFlowBaseFillColor),
                    new TypeColor(USER_TASK_LINE, decisionFlowBaseLineColor),
                    new TypeColor(DTABLE_TASK_FILL, decisionFlowBaseFillColor),
                    new TypeColor(DTABLE_TASK_LINE, decisionFlowBaseLineColor),
                    new TypeColor(RECEIVE_TASK_FILL, decisionFlowBaseFillColor),
                    new TypeColor(RECEIVE_TASK_LINE, decisionFlowBaseLineColor),
                    new TypeColor(SEND_TASK_FILL, decisionFlowBaseFillColor),
                    new TypeColor(SEND_TASK_LINE, decisionFlowBaseLineColor),
                    new TypeColor(MANUAL_TASK_FILL, decisionFlowBaseFillColor),
                    new TypeColor(MANUAL_TASK_LINE, decisionFlowBaseLineColor),
                    new TypeColor(SCRIPT_TASK_FILL, decisionFlowBaseFillColor),
                    new TypeColor(SCRIPT_TASK_LINE, decisionFlowBaseLineColor),
                    new TypeColor(SUBPROCESS_TASK_FILL,
                            decisionFlowBaseFillColor),
                    new TypeColor(SUBPROCESS_TASK_LINE,
                            decisionFlowBaseLineColor),
                    new TypeColor(EMBEDDEDSUBPROCESS_TASK_FILL,
                            lighterDecisionFlowFillColor),
                    new TypeColor(EMBEDDEDSUBPROCESS_TASK_LINE,
                            decisionFlowBaseLineColor),
                    new TypeColor(REFERENCE_TASK_FILL,
                            decisionFlowBaseFillColor),
                    new TypeColor(REFERENCE_TASK_LINE,
                            decisionFlowBaseLineColor),
                    /** Event Types... */
                    new TypeColor(START_EVENT_FILL, decisionFlowBaseFillColor),
                    new TypeColor(START_EVENT_LINE, decisionFlowBaseLineColor),
                    new TypeColor(INTERMEDIATE_EVENT_FILL,
                            decisionFlowBaseFillColor),
                    new TypeColor(INTERMEDIATE_EVENT_LINE,
                            decisionFlowBaseLineColor),
                    new TypeColor(END_EVENT_FILL, decisionFlowBaseFillColor),
                    new TypeColor(END_EVENT_LINE, decisionFlowBaseLineColor),
                    /** Interface Event Types */
                    new TypeColor(INTERFACE_START_EVENT_FILL, greyFillColor),
                    new TypeColor(INTERFACE_INTERMEDIATE_EVENT_FILL,
                            greyFillColor),

                    /** Gateway Types... */
                    new TypeColor(EXCLUSIVE_DATA_GATEWAY_FILL,
                            decisionFlowBaseFillColor),
                    new TypeColor(XORDATA_GATEWAY_LINE,
                            decisionFlowBaseLineColor),
                    new TypeColor(EXCLUSIVE_EVENT_GATEWAY_FILL,
                            decisionFlowBaseFillColor),
                    new TypeColor(XOREVENT_GATEWAY_LINE,
                            decisionFlowBaseLineColor),
                    new TypeColor(INCLUSIVE_GATEWAY_FILL,
                            decisionFlowBaseFillColor),
                    new TypeColor(OR_GATEWAY_LINE, decisionFlowBaseLineColor),
                    new TypeColor(COMPLEX_GATEWAY_FILL,
                            decisionFlowBaseFillColor),
                    new TypeColor(COMPLEX_GATEWAY_LINE,
                            decisionFlowBaseLineColor),
                    new TypeColor(PARALLEL_GATEWAY_FILL,
                            decisionFlowBaseFillColor),
                    new TypeColor(AND_GATEWAY_LINE, decisionFlowBaseLineColor),
                    /** Miscellaneous */
                    new TypeColor(DATAOBJECT_FILL, decisionFlowBaseFillColor),
                    new TypeColor(DATAOBJECT_LINE, decisionFlowBaseLineColor),
                    new TypeColor(NOTE_FILL, null),
                    new TypeColor(NOTE_LINE, decisionFlowBaseLineColor),
                    new TypeColor(GROUP_LINE, decisionFlowBaseLineColor),
                    new TypeColor(LANE_FILL, new WidgetRGB(128, 155, 183)),
                    new TypeColor(LANE_ALTERNATE_FILL, new WidgetRGB(128, 155,
                            183)),
                    new TypeColor(LANE_LINE, decisionFlowBaseLineColor),
                    new TypeColor(POOL_HEADER_FILL, darkerBaseFillColor),
                    new TypeColor(POOL_HEADER_LINE, decisionFlowBaseLineColor),
                    /** Connections... */
                    new TypeColor(UNCONTROLLED_SEQ_FLOW_LINE,
                            decisionFlowBaseLineColor),
                    new TypeColor(CONDITIONAL_SEQ_FLOW_LINE,
                            decisionFlowBaseLineColor),
                    new TypeColor(DEFAULT_SEQ_FLOW_LINE,
                            decisionFlowBaseLineColor),
                    new TypeColor(MESSAGE_FLOW_LINE, decisionFlowBaseLineColor),
                    new TypeColor(ASSOCIATION_LINE, decisionFlowBaseLineColor)

            };

    private TypeColor[] typeColors;

    /**
     * Preference Key...
     */
    private String typeColorsPreferencePrefix;

    public static ProcessWidgetColors getInstance() {
        return getInstance(ProcessWidgetType.BPMN_PROCESS);
    }

    public static ProcessWidgetColors getInstance(ModelAdapterEditPart editPart) {
        if (editPart != null) {
            return getInstance(editPart.getModelAdapter());
        }
        return getInstance();
    }

    public static ProcessWidgetColors getInstance(BaseProcessAdapter adapter) {
        if (adapter != null) {
            return getInstance(adapter.getProcessType());
        }
        return getInstance();
    }

    public static ProcessWidgetColors getInstance(ProcessWidgetType processType) {
        if (processType == null) {
            processType = ProcessWidgetType.BPMN_PROCESS;
        }

        ProcessWidgetColors colors = instances.get(processType);
        if (colors == null) {
            colors = new ProcessWidgetColors(processType);
            instances.put(processType, colors);
        }
        return colors;
    }

    private ProcessWidgetColors(ProcessWidgetType processType) {

        if (ProcessWidgetType.BUSINESS_SERVICE.equals(processType)) {

            typeColorsPreferencePrefix = "BusinessService"; //$NON-NLS-1$
            typeColors = bizServPageflowProcessColors;
        } else if (ProcessWidgetType.CASE_SERVICE.equals(processType)) {

            typeColorsPreferencePrefix = "CaseService"; //$NON-NLS-1$
            typeColors = caseServPageflowProcessColors;
        } else if (ProcessWidgetType.PAGEFLOW_PROCESS.equals(processType)) {

            typeColorsPreferencePrefix = "PageFlow"; //$NON-NLS-1$
            typeColors = pageflowProcessColors;
        } else if (ProcessWidgetType.SERVICE_PROCESS.equals(processType)) {

            typeColorsPreferencePrefix = "ServiceProcess"; //$NON-NLS-1$
            typeColors = serviceProcessColors;
        } else if (ProcessWidgetType.TASK_LIBRARY.equals(processType)) {

            typeColorsPreferencePrefix = "TaskLibrary"; //$NON-NLS-1$
            typeColors = taskLibraryColors;
        } else if (ProcessWidgetType.DECISION_FLOW.equals(processType)) {

            typeColorsPreferencePrefix = "DecisionsFlow"; //$NON-NLS-1$
            typeColors = decisionFlowColors;
        } else {

            typeColorsPreferencePrefix = "TypeColor"; //$NON-NLS-1$
            typeColors = businessProcessColors;
        }

        // Overload the base default colours with any found in preferences...
        IPreferenceStore prefStore =
                ProcessWidgetPlugin.getDefault().getPreferenceStore();

        for (int i = 0; i < typeColors.length; i++) {
            // Build preference key for type's color.

            String val =
                    prefStore
                            .getString(getTypeColorPreferenceName(typeColors[i].typeID));

            if (val != null && !val.equals("")) { //$NON-NLS-1$
                typeColors[i].prefColor = new WidgetRGB(val);
            }
        }
    }

    /**
     * Get the color for the given GraphicalNodeColorAdapter (object on diagram
     * that supports color).
     * 
     * First checks the adapter to see if model object has color set.
     * 
     * If fromAdapterOnly is false then returns default for typeID.
     * 
     * If you pass an invalid colour id then this function will return magenta
     * (unless fromAdapterOnly = true).
     * 
     * @param adapter
     *            Adapter for model object to get existing defined colour or
     *            null if want default colour for type
     * @param typeID
     *            One of ProcessWidgetColors.xxx_FILL
     * @param fromAdapterOnly
     *            If colour not defined in adapter model object then DON'T
     *            return default/preference colour.
     * 
     */
    public WidgetRGB getGraphicalNodeColor(GraphicalColorAdapter adapter,
            String typeID, boolean fromAdapterOnly) {
        WidgetRGB ret = null;

        // Check whether dealing with a fill or border color.
        String tmp = (typeID == null) ? "" : typeID.toUpperCase(); //$NON-NLS-1$

        boolean bFound = false;

        // Get colour from model.
        if (adapter != null) {
            if (tmp.indexOf("FILL") >= 0) { //$NON-NLS-1$
                String fillStr = adapter.getFillColor();
                if (fillStr != null && !fillStr.equals("")) { //$NON-NLS-1$
                    ret = new WidgetRGB(fillStr);
                }
            } else {
                String borderStr = adapter.getLineColor();
                if (borderStr != null && !borderStr.equals("")) { //$NON-NLS-1$
                    ret = new WidgetRGB(borderStr);
                }
            }
        }

        // Return now if caller doesn't want to fall back on defaults...
        if (fromAdapterOnly) {
            return (ret);
        }

        // If not there get from defaults.
        if (ret == null && !tmp.equals("")) { //$NON-NLS-1$
            for (int i = 0; i < typeColors.length; i++) {
                if (typeColors[i].typeID.equals(typeID)) {
                    // Found the type we are looking for,
                    // If userhas set new default preference for it then use
                    // that.
                    // If not, use the default.
                    if (typeColors[i].prefColor != null) {
                        ret = typeColors[i].prefColor;
                        bFound = true;
                        break;
                    } else {
                        ret = typeColors[i].defColor;
                        bFound = true;
                        break;
                    }
                }
            }
        }

        // If all else fails (such as caller passed in a duff ID
        // Then return magenta to make it obvious during testing.
        if (!bFound && ret == null) {
            ret = new WidgetRGB(255, 0, 255);
        }

        return (ret);
    }

    /**
     * Get the color for the given GraphicalNodeColorAdapter (object on diagram
     * that supports color).
     * 
     * First checks the adapter to see if model object has color set else
     * returns default for typeID.
     * 
     * If you pass an invalid colour id then this function will return magenta
     * (unless fromAdapterOnly = true).
     * 
     * @param adapter
     *            Adapter for model object to get existing defined colour or
     *            null if want default colour for type
     * @param typeID
     *            One of ProcessWidgetColors.xxx_FILL
     * 
     * @see <li>
     *      {@link com.tibco.xpd.processwidget.ProcessWidgetColors#getGraphicalNodeColor(GraphicalColorAdapter adapter, String typeID, boolean fromAdapterOnly)}
     *      </li>
     * 
     */
    public WidgetRGB getGraphicalNodeColor(GraphicalColorAdapter adapter,
            String typeID) {
        return (getGraphicalNodeColor(adapter, typeID, false));
    }

    /**
     * Set the stored-in-preferences default color for the given id.
     * 
     * @param typeID
     *            Type id string
     * @param rgb
     *            If null then the preference store for id is removed - hence
     *            restore to factory default
     */
    public void setDefaultForType(String typeID, WidgetRGB rgb) {

        // Make sure a valid id has been passed.
        if (typeID == null) {
            return;
        }

        TypeColor typeColor = null;
        int typeColorIdx = 0;
        for (typeColorIdx = 0; typeColorIdx < typeColors.length; typeColorIdx++) {
            if (typeColors[typeColorIdx].typeID.equals(typeID)) {
                // Found the type we are looking for,
                // If userhas set new default preference for it then use that.
                // If not, use the default.
                typeColor = typeColors[typeColorIdx];
                break;
            }
        }

        if (typeColor != null) {
            IPreferenceStore prefStore =
                    ProcessWidgetPlugin.getDefault().getPreferenceStore();

            String prefName = getTypeColorPreferenceName(typeColor.typeID);
            if (rgb != null) {
                // Save the preference and load into our cache.
                prefStore.setValue(prefName, rgb.toString());
                typeColor.prefColor = new WidgetRGB(rgb.toString());
            } else {
                // Delete the preference and our cached entry.
                prefStore.setValue(prefName, ""); //$NON-NLS-1$
                typeColor.prefColor = null;
            }
        }
    }

    /**
     * Return the 'end of gradient' colour for the given fill color.
     * 
     * Currently this is always white; But if we wish we can auto-calc based on
     * given color later on.
     * 
     * @param fillColor
     *            Start of gradient fill colour.
     * 
     * @return End of gradient color for fill color - allocated in
     *         ProcessWidgetPlugin color registry - DO NOT DISPOSE!!
     */
    public static Color getGradientEndColor(Color fillColor) {
        // For the moment always return white,
        // we could if we wanted return lighter version of that passed.
        return XpdColorRegistry.getDefault().mixColors(fillColor,
                ColorConstants.white,
                0.08);
        // WidgetRGB white = new WidgetRGB(255, 255, 255);
        //
        // return white.getColor();
    }

    private String getTypeColorPreferenceName(String typeID) {
        return new String(typeColorsPreferencePrefix + "_" + typeID); //$NON-NLS-1$
    }

}
