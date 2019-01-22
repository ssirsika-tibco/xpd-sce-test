package com.tibco.xpd.processwidget.adapters;

import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorObjectType;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.internal.Messages;

/**
 * Contants for type od the flow
 */
public class SequenceFlowType {

    static final public int UNCONTROLLED = 1;

    static final public int CONDITIONAL = 2;

    static final public int DEFAULT = 3;

    static final public SequenceFlowType DEFAULT_LITERAL =
            new SequenceFlowType(DEFAULT);

    static final public SequenceFlowType CONDITIONAL_LITERAL =
            new SequenceFlowType(CONDITIONAL);

    static final public SequenceFlowType UNCONTROLLED_LITERAL =
            new SequenceFlowType(UNCONTROLLED);

    static final public SequenceFlowType[] TYPES = new SequenceFlowType[] {
            UNCONTROLLED_LITERAL, CONDITIONAL_LITERAL, DEFAULT_LITERAL };

    private final int type;

    private final String name;

    /**
     * The private constructor
     */
    private SequenceFlowType(int type) {
        this.type = type;
        switch (type) {
        case DEFAULT:
            name = Messages.SequenceFlowType_Default_label;
            break;
        case CONDITIONAL:
            name = Messages.SequenceFlowType_Conditional_label;
            break;
        case UNCONTROLLED:
            name = Messages.SequenceFlowType_Uncontrolled_label;
            break;
        default:
            throw new IllegalArgumentException();
        }
    }

    public int getValue() {
        return type;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SequenceFlowType) {
            return ((SequenceFlowType) obj).type == type;
        }
        return super.equals(obj);
    }

    /**
     * Returns the ProcessWidgetColors.ID used as the default FILL Color for
     * this task type.
     * 
     * @return
     */
    public String getGetDefaultLineColorId() {
        String colorID = null;

        switch (getValue()) {
        case SequenceFlowType.CONDITIONAL:
            colorID = ProcessWidgetColors.CONDITIONAL_SEQ_FLOW_LINE;
            break;
        case SequenceFlowType.DEFAULT:
            colorID = ProcessWidgetColors.DEFAULT_SEQ_FLOW_LINE;
            break;
        case SequenceFlowType.UNCONTROLLED:
        default:
            colorID = ProcessWidgetColors.UNCONTROLLED_SEQ_FLOW_LINE;
            break;
        }

        return colorID;
    }

    /**
     * @return {@link ProcessEditorObjectType} for this sequence flow type
     */
    public ProcessEditorObjectType getEditorObjectType() {
        switch (getValue()) {
        case SequenceFlowType.CONDITIONAL:
            return ProcessEditorObjectType.sequenceflow_conditional;
        case SequenceFlowType.DEFAULT:
            return ProcessEditorObjectType.sequenceflow_default;
        case SequenceFlowType.UNCONTROLLED:
        default:
            return ProcessEditorObjectType.sequenceflow_uncontrolled;
        }
    }

}