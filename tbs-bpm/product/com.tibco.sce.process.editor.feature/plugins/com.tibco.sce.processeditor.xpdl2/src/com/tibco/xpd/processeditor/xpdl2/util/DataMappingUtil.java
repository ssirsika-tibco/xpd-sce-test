/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.util;

import java.util.Collection;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utility to provide handle to source and target ends of a mappings. Also
 * provides commands to set command expressions.
 * 
 * @author nwilson
 */
public final class DataMappingUtil {
    /**
     * Private constructor.
     */
    private DataMappingUtil() {
    }

    /**
     * @param mapping
     * @return
     */
    public static String getTarget(DataMapping mapping) {
        String target = null;
        if (DirectionType.IN_LITERAL.equals(mapping.getDirection())) {
            target = mapping.getFormal();
        } else {
            Expression actual = mapping.getActual();
            if (actual != null) {
                target = actual.getText();
            }
        }
        return target;
    }

    /**
     * @param mapping
     * @return
     */
    public static String getScript(DataMapping mapping) {
        String script = null;
        if (DirectionType.IN_LITERAL.equals(mapping.getDirection())) {
            Expression actual = mapping.getActual();
            if (actual != null) {
                script = actual.getText();
            }
        } else {
            Object other =
                    Xpdl2ModelUtil.getOtherElement(mapping,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Expression());
            if (other instanceof Expression) {
                Expression expression = (Expression) other;
                if (expression != null) {
                    script = expression.getText();
                }
            } else {
                other =
                        Xpdl2ModelUtil.getOtherElement(mapping,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_Script());
                if (other instanceof ScriptInformation) {
                    Expression expression =
                            ((ScriptInformation) other).getExpression();
                    if (expression != null) {
                        script = expression.getText();
                    }
                } else {
                    script = mapping.getFormal();
                }
            }
        }
        return script;
    }

    /**
     * @param mapping
     * @return
     */
    public static String getGrammar(DataMapping mapping) {
        String grammar = null;
        if (DirectionType.IN_LITERAL.equals(mapping.getDirection())) {
            Expression actual = mapping.getActual();
            if (actual != null) {
                grammar = actual.getScriptGrammar();
            }
        } else {
            Object other =
                    Xpdl2ModelUtil.getOtherElement(mapping,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Expression());
            if (other instanceof Expression) {
                Expression expression = (Expression) other;
                if (expression != null) {
                    grammar = expression.getScriptGrammar();
                }
            } else {
                other =
                        Xpdl2ModelUtil.getOtherElement(mapping,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_Script());
                if (other instanceof ScriptInformation) {
                    Expression expression =
                            ((ScriptInformation) other).getExpression();
                    if (expression != null) {
                        grammar = expression.getScriptGrammar();
                    }
                } else {
                    Expression actual = mapping.getActual();
                    if (actual != null) {
                        grammar = actual.getScriptGrammar();
                    }
                }
            }
        }
        return grammar;
    }

    /**
     * @param editingDomain
     * @param ccmd
     * @param dataMapping
     * @param compositor
     */
    public static void getSetDataMappingExpressionCommand(
            EditingDomain editingDomain, CompoundCommand ccmd,
            DataMapping dataMapping, ScriptMappingCompositor compositor) {
        if (compositor instanceof SingleMappingCompositor) {
            SingleMappingCompositor single =
                    (SingleMappingCompositor) compositor;
            Collection<String> items = single.getSourceItemNames();
            int count = items.size();
            if (count == 0) {
                ccmd.append(RemoveCommand.create(editingDomain, dataMapping));
            } else {
                if (DirectionType.IN_LITERAL.equals(dataMapping.getDirection())) {
                    if (!compositor.getExpression().getText()
                            .equals(dataMapping.getActual().getText())) {
                        ccmd.append(SetCommand.create(editingDomain,
                                dataMapping,
                                Xpdl2Package.eINSTANCE.getDataMapping_Actual(),
                                compositor.getExpression()));
                    }
                } else {
                    if (count > 1) {
                        Expression expression = compositor.getExpression();
                        ccmd.append(Xpdl2ModelUtil
                                .getSetOtherElementCommand(editingDomain,
                                        dataMapping,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_Expression(),
                                        expression));
                    } else if (count == 1) {
                        Object other =
                                Xpdl2ModelUtil.getOtherElement(dataMapping,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_Expression());
                        if (other instanceof Expression) {
                            ccmd.append(Xpdl2ModelUtil
                                    .getRemoveOtherElementCommand(editingDomain,
                                            dataMapping,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_Expression(),
                                            other));
                        }
                        ccmd.append(SetCommand.create(editingDomain,
                                dataMapping,
                                Xpdl2Package.eINSTANCE.getDataMapping_Formal(),
                                compositor.getExpression().getText()));
                    }
                }
            }
        }
    }

    /**
     * Verify whether the mappings refers to a Script.
     * 
     * @param mapping
     * @return <code>Boolean.TRUE</code> if the mapping has a script involved.
     */
    public static boolean isScripted(DataMapping mapping) {
        boolean scripted = false;
        Object other =
                Xpdl2ModelUtil.getOtherElement(mapping,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script());
        if (other instanceof ScriptInformation) {
            scripted = true;
        }
        return scripted;
    }

    /**
     * Returns the <b>script</b> for the <b>mapping</b> passed as parameter.
     * 
     * @param mapping
     * @return <b>script for the mapping</b>
     */
    public static ScriptInformation getMappingScript(DataMapping mapping) {
        Object other =
                Xpdl2ModelUtil.getOtherElement(mapping,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script());
        if (other instanceof ScriptInformation) {
            return (ScriptInformation) other;
        }
        return null;
    }

    /**
     * Returns true if dataMapping is for InitialValueMapping
     * 
     * @param dataMapping
     * @return
     */
    public static boolean isInitialValueMapping(DataMapping dataMapping) {
        boolean isInitialValueMapping = false;
        Object other =
                Xpdl2ModelUtil.getOtherAttribute(dataMapping,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_InitialValueMapping());
        if (other instanceof Boolean) {
            Boolean bool = (Boolean) other;
            if (Boolean.TRUE.equals(bool)) {
                isInitialValueMapping = true;
            }
        }
        return isInitialValueMapping;
    }

}
