/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.InitialParameterValue;
import com.tibco.xpd.xpdExtension.InitialValues;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class InitialValue {

    private Activity activity;

    private FormalParameter formal;

    /**
     * @param activity
     * @param formal
     */
    public InitialValue(Activity activity, FormalParameter formal) {
        this.activity = activity;
        this.formal = formal;
    }

    /**
     * @return
     */
    public String getValue() {
        String value = null;
        InitialParameterValue initial =
                getInitialParameterValue(activity, formal.getName());
        if (initial != null) {
            value = initial.getValue();
        }
        if (value == null) {
            Object other =
                    Xpdl2ModelUtil.getOtherElement(formal,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_InitialValues());
            if (other instanceof InitialValues) {
                InitialValues values = (InitialValues) other;
                List<?> tokens = values.getValue();
                List<DataMapping> mappings =
                        Xpdl2ModelUtil.getDataMappings(activity,
                                DirectionType.IN_LITERAL);
                for (DataMapping mapping : mappings) {
                    String target = DataMappingUtil.getTarget(mapping);
                    if (formal.getName().equals(target)) {
                        String script = DataMappingUtil.getScript(mapping);
                        if (tokens.contains(script)) {
                            value = script;
                        }
                    }
                }
            }
        }
        if (value == null) {
            // Use the first available value.
            Object other =
                    Xpdl2ModelUtil.getOtherElement(formal,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_InitialValues());
            if (other instanceof InitialValues) {
                InitialValues values = (InitialValues) other;
                List<?> tokens = values.getValue();
                if (tokens.size() > 0) {
                    Object token = tokens.get(0);
                    if (token instanceof String) {
                        value = (String) token;
                    }
                }
            }
        }
        return value;
    }

    /**
     * @param name
     * @return
     */
    public static InitialParameterValue getInitialParameterValue(
            Activity activity, String name) {
        InitialParameterValue initial = null;
        if (name != null) {
            List<?> others =
                    Xpdl2ModelUtil.getOtherElementList(activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_InitialParameterValue());
            for (Object other : others) {
                if (other instanceof InitialParameterValue) {
                    InitialParameterValue current =
                            (InitialParameterValue) other;
                    if (name.equals(current.getName())) {
                        initial = current;
                        break;
                    }
                }
            }
        }
        return initial;
    }

    @Override
    public String toString() {
        String name = formal == null ? "" : formal.getName(); //$NON-NLS-1$
        String message = formal == null ? "" : getValue(); //$NON-NLS-1$
        return String.format("%1$s: %2$s", name, message); //$NON-NLS-1$
    }

    /**
     * @return
     */
    public String[] getAllowedValues() {
        List<String> allowed = new ArrayList<String>();
        Object other =
                Xpdl2ModelUtil.getOtherElement(formal,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_InitialValues());
        if (other instanceof InitialValues) {
            InitialValues values = (InitialValues) other;
            List<?> tokens = values.getValue();
            for (Object next : tokens) {
                if (next instanceof String) {
                    allowed.add((String) next);
                }
            }
        }
        String[] allowedArray = new String[allowed.size()];
        return allowed.toArray(allowedArray);
    }

    /**
     * @param value
     * @return
     */
    public Command getSetValueCommand(String value) {
        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.InitialValue_SetInitialValueCommand);
        EditingDomain ed = WorkingCopyUtil.getEditingDomain(activity);
        if (ed != null) {
            DataMapping initialMapping = null;
            List<DataMapping> mappings =
                    Xpdl2ModelUtil.getDataMappings(activity,
                            DirectionType.IN_LITERAL);
            Object other =
                    Xpdl2ModelUtil.getOtherElement(formal,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_InitialValues());
            List<String> tokens = new ArrayList<String>();
            if (other instanceof InitialValues) {
                InitialValues values = (InitialValues) other;
                tokens = values.getValue();
            }
            for (DataMapping mapping : mappings) {
                String target = DataMappingUtil.getTarget(mapping);
                if (formal.getName().equals(target)) {
                    String script = DataMappingUtil.getScript(mapping);
                    if (tokens.contains(script)) {
                        initialMapping = mapping;
                    }
                }
            }
            if (initialMapping == null) {
                InitialParameterValue initial =
                        getInitialParameterValue(activity, formal.getName());
                if (initial == null) {
                    initial =
                            XpdExtensionFactory.eINSTANCE
                                    .createInitialParameterValue();
                    initial.setName(formal.getName());
                    initial.setValue(value);
                    cmd.append(Xpdl2ModelUtil.getAddOtherElementCommand(ed,
                            activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_InitialParameterValue(),
                            initial));
                } else {
                    cmd.append(SetCommand.create(ed,
                            initial,
                            XpdExtensionPackage.eINSTANCE
                                    .getInitialParameterValue_Value(),
                            value));
                }
            } else {
                String grammar = initialMapping.getActual().getScriptGrammar();
                Expression actual = Xpdl2ModelUtil.createExpression(value);
                actual.setScriptGrammar(grammar);
                cmd.append(SetCommand.create(ed,
                        initialMapping,
                        Xpdl2Package.eINSTANCE.getDataMapping_Actual(),
                        actual));
            }
        }
        return cmd;
    }

    /**
     * @return
     */
    public FormalParameter getFormal() {
        return formal;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if (obj instanceof InitialValue) {
            InitialValue other = (InitialValue) obj;
            if (activity != null && formal != null
                    && activity.equals(other.activity)
                    && formal.equals(other.formal)) {
                equal = true;
            }
        }
        return equal;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

}
