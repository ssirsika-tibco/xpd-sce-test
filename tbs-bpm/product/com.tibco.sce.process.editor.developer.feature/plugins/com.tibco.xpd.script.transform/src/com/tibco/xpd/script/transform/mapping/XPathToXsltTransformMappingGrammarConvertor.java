/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.script.transform.mapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.w3c.dom.Document;

import com.tibco.xpd.processeditor.xpdl2.properties.script.MappingGrammarConvertor;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.transform.internal.Messages;
import com.tibco.xpd.script.transform.properties.XSLTTransformEditorSection;
import com.tibco.xpd.script.transform.util.TransformDataMappingUtil;
import com.tibco.xpd.script.transform.util.TransformUtil;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author mtorres
 */
public class XPathToXsltTransformMappingGrammarConvertor implements
        MappingGrammarConvertor {

    /**
     * @param activity
     * @param direction
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.MappingGrammarConvertor#getConvertGrammarCommand(com.tibco.xpd.xpdl2.Activity,
     *      com.tibco.xpd.xpdl2.DirectionType)
     */
    public Command getConvertGrammarCommand(Activity activity,
            DirectionType direction) {
        CompoundCommand cmd = new CompoundCommand();
        String xslt = null;
        
        try {
            if (DirectionType.IN_LITERAL.equals(direction)) {
                Document doc =
                        TransformDataMappingUtil.generateInputXslt(activity);
                if (doc == null) {
                    xslt = ""; //$NON-NLS-1$
                } else {
                    try {
                        xslt = TransformUtil.toXmlString(doc, true);
                    } catch (TransformerConfigurationException e) {
                        e.printStackTrace();
                    } catch (TransformerException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (GenerationException e) {
            e.printStackTrace();
        }
        if (xslt != null) {
            ScriptInformation information = null;
            List<ScriptInformation> scripts =
                    new ArrayList<ScriptInformation>();
            List<?> others =
                    Xpdl2ModelUtil.getOtherElementList(activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Script());
            for (Object other : others) {
                if (other instanceof ScriptInformation) {
                    ScriptInformation current = (ScriptInformation) other;
                    if (direction.equals(current.getDirection())) {
                        if (current.getName() == null) {
                            information = current;
                        } else {
                            scripts.add(current);
                        }
                    }
                }
            }
            EditingDomain ed = WorkingCopyUtil.getEditingDomain(activity);

            // Remove current data mappings.
            List<DataMapping> mappings =
                    TransformUtil.getDataMappings(activity);
            if (mappings.size() != 0 || scripts.size() != 0) {
                if (!MessageDialog
                        .openConfirm(
                                null,
                                Messages.XPathToXsltTransformMappingGrammarConvertor_ConvertToXsltWarningMessageTitle,
                                Messages.XPathToXsltTransformMappingGrammarConvertor_ConvertToXsltWarningMessage)) {
                    cmd.append(UnexecutableCommand.INSTANCE);
                }
            }
            if (mappings.size() > 0) {
                cmd.append(RemoveCommand.create(ed, mappings));
            }
            for (ScriptInformation script : scripts) {
                cmd.append(Xpdl2ModelUtil.getRemoveOtherElementCommand(ed,
                        activity, XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_Script(), script));
            }

            if (information == null) {
                information =
                        XpdExtensionFactory.eINSTANCE.createScriptInformation();
                information.setDirection(direction);
                Expression expression = Xpdl2ModelUtil.createExpression(xslt);
                expression.setScriptGrammar(XSLTTransformEditorSection.SCRIPT_GRAMMAR);
                information.setExpression(expression);
                cmd.append(Xpdl2ModelUtil.getAddOtherElementCommand(ed,
                        activity, XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_Script(), information));
            } else {
                Expression expression = Xpdl2ModelUtil.createExpression(xslt);
                expression.setScriptGrammar(XSLTTransformEditorSection.SCRIPT_GRAMMAR);
                cmd
                        .append(SetCommand.create(ed, information,
                                XpdExtensionPackage.eINSTANCE
                                        .getScriptInformation_Expression(),
                                expression));
                cmd.append(SetCommand.create(ed, information,
                        XpdExtensionPackage.eINSTANCE
                                .getScriptInformation_Reference(),
                        Boolean.FALSE));
            }

        }
        return cmd;
    }

    /**
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.MappingGrammarConvertor#isGrammarSet()
     */
    public boolean isGrammarSet() {
        return true;
    }

}
