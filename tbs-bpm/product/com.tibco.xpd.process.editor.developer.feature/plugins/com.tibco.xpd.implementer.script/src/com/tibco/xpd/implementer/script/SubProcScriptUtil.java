/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.script;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ScriptInformationUtil;
import com.tibco.xpd.processeditor.xpdl2.util.SubProcUtil;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public final class SubProcScriptUtil {

    /**
     * Private constructor.
     */
    private SubProcScriptUtil() {
    }

    /**
     * @param ed
     *            The editing domain.
     * @param script
     *            The script.
     * @param information
     *            The script information.
     * @param grammar
     *            The script grammar.
     * @return The command to set the script.
     */
    public static Command getSetSubProcScriptCommand(EditingDomain ed,
            String script, ScriptInformation information, String grammar) {
        CompoundCommand cmd = new CompoundCommand(Messages.SubProcScriptUtil_EditScriptCommand);
        EObject parent = information.eContainer();
        if (parent instanceof Activity) {
            Expression expression = Xpdl2ModelUtil.createExpression(script);
            expression.setScriptGrammar(grammar);
            cmd.append(SetCommand.create(ed, information,
                    XpdExtensionPackage.eINSTANCE
                            .getScriptInformation_Expression(), expression));
        } else if (parent == null) {
            Activity activity = information.getActivity();
            if (activity != null) {
                information.setActivity(null);
                information.setName(ScriptInformationUtil.getNextScriptName(
                        activity, information.getDirection()));
                Expression expression = Xpdl2ModelUtil.createExpression(script);
                expression.setScriptGrammar(grammar);
                information.setExpression(expression);
                cmd.append(Xpdl2ModelUtil.getAddOtherElementCommand(ed,
                        activity, XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_Script(), information));
            }
        } else if (parent instanceof DataMapping) {
            DataMapping mapping = (DataMapping) parent;
            DirectionType direction = mapping.getDirection();
            Activity activity = getContainingActivity(parent);
            if (activity != null) {
                String target = DataMappingUtil.getTarget(mapping);
                if (target != null) {
                    ScriptMappingCompositorFactory factory = ScriptMappingCompositorFactory
                            .getCompositorFactory(grammar, direction,
                                    SubProcUtil.SCRIPT_CONTEXT);
                    if (factory != null) {
                        ScriptMappingCompositor compositor = factory
                                .getCompositor(activity, target);
                        if (compositor instanceof SingleMappingCompositor) {
                            SingleMappingCompositor multiple = (SingleMappingCompositor) compositor;
                            multiple.setScript(script);
                            Expression expression = multiple.getExpression();
                            Object feature;
                            Object owner;
                            if (DirectionType.IN_LITERAL.equals(direction)) {
                                feature = Xpdl2Package.eINSTANCE
                                        .getDataMapping_Actual();
                                owner = mapping;
                            } else {
                                feature = XpdExtensionPackage.eINSTANCE
                                        .getScriptInformation_Expression();
                                owner = information;
                            }
                            cmd.append(SetCommand.create(ed, owner, feature,
                                    expression));
                        }
                    }
                }
            }
        }
        return cmd;
    }

    /**
     * @param item
     *            The item to get the activity for.
     * @return The activity, or null.
     */
    private static Activity getContainingActivity(EObject item) {
        Activity activity = null;
        if (item instanceof Activity) {
            activity = (Activity) item;
        } else if (item != null) {
            EObject parent = item.eContainer();
            activity = getContainingActivity(parent);
        }
        return activity;
    }

}
