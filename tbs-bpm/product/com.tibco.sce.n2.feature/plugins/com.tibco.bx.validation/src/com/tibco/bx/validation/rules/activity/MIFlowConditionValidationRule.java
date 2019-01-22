package com.tibco.bx.validation.rules.activity;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.validation.xpdl2.rules.ActivityValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Loop;
import com.tibco.xpd.xpdl2.LoopMultiInstance;
import com.tibco.xpd.xpdl2.LoopType;
import com.tibco.xpd.xpdl2.MIFlowConditionType;
import com.tibco.xpd.xpdl2.MIOrderingType;

public class MIFlowConditionValidationRule extends ActivityValidationRule {

	public static final String ISSUE_UNSUPPORTED_FLOW_CONDITION = "n2pe.unsupportedFlowCondition"; //$NON-NLS-1$

	@Override
	public void validate(Activity activity) {
		boolean parallelMILoop = false;
		boolean serialMILoop = false;
		int miFlowConditionType = -1;
		
		Loop loop = activity.getLoop();
		if (loop != null) {
			LoopType loopType = loop.getLoopType();
			if (loopType.getValue() == LoopType.MULTI_INSTANCE) {
				LoopMultiInstance loopMI = loop.getLoopMultiInstance();
				if (loopMI != null) {
					if (loopMI.getMIOrdering().getValue() == MIOrderingType.PARALLEL) {
						parallelMILoop = true;
					} else {
						serialMILoop = true;
					}
	                miFlowConditionType = loopMI.getMIFlowCondition().getValue();
				}
			}
        } else if (activity.getStartQuantity() != null && activity.getStartQuantity().intValue()==-1) {
            //Studio 2.x used extended attributes to set loop parameters
            List<ExtendedAttribute> list = activity.getExtendedAttributes();
            if (list!=null && list.size()>0) {
                for (ExtendedAttribute attr: list) {
                    if ("MI_Ordering".equals(attr.getName())) { //$NON-NLS-1$
                        if ("Parallel".equals(attr.getValue())) { //$NON-NLS-1$
                            parallelMILoop = true;
                        } else {
                            serialMILoop = true;
                        }
                    } else if ("MI_FlowCondition".equals(attr.getName())) { //$NON-NLS-1$
                        String type = attr.getValue();
                        if ("All".equals(type)) miFlowConditionType = MIFlowConditionType.ALL; //$NON-NLS-1$
                        if ("One".equals(type)) miFlowConditionType = MIFlowConditionType.ONE; //$NON-NLS-1$
                        if ("None".equals(type)) miFlowConditionType = MIFlowConditionType.NONE; //$NON-NLS-1$
                        if ("Complex".equals(type)) miFlowConditionType = MIFlowConditionType.COMPLEX; //$NON-NLS-1$
                        if ("Immediate".equals(type)) miFlowConditionType = MIFlowConditionType.COMPLEX+10; //$NON-NLS-1$
                    }
                }
            }
		}

        if (parallelMILoop || serialMILoop) {
            switch (miFlowConditionType) {
            case MIFlowConditionType.COMPLEX+10: // immediate. matches work pattern WCP12
            case MIFlowConditionType.ONE:
            case MIFlowConditionType.ALL:
                break;
                case MIFlowConditionType.NONE:
                    // this is equivalent to wrapping looping activity and all that follows with forEach
                    // can be achieved by putting everything into an embedded subprocess that is marked MIFlowConditionType.ALL
                	addError(MIFlowConditionType.NONE_LITERAL.getName(), activity);
                    break;
                case MIFlowConditionType.COMPLEX:
                	addError(MIFlowConditionType.COMPLEX_LITERAL.getName(), activity);
                    break;
                default:
                	addError(String.valueOf(miFlowConditionType), activity);
            }
        }
	}

	private void addError(String message, EObject obj) {
		List<String> messages = new ArrayList<String>();
		messages.add(message);
		this.addIssue(ISSUE_UNSUPPORTED_FLOW_CONDITION, obj, messages);
	}

}
