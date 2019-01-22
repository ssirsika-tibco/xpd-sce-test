/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.propertysection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.columns.LabelColumn;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.columns.NameColumn;
import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.resources.ui.components.actions.TableAddAction;
import com.tibco.xpd.resources.ui.components.actions.TableDeleteAction;
import com.tibco.xpd.ui.util.CapabilityUtil;

/**
 * BOM properties section for the {@link EnumerationLiteral}s section.
 * 
 * @author njpatel
 * 
 */
public class EnumLiteralsTabSection extends AbstractTableSection {

    private static final EReference FEATURE =
            UMLPackage.eINSTANCE.getEnumeration_OwnedLiteral();

    public EnumLiteralsTabSection() {
        super(Messages.EnumLiteralsTabSection_section_title, FEATURE);
    }

    @Override
    protected void addColumns(ColumnViewer viewer) {
        new LabelColumn(getEditingDomain(), viewer, true);
        if (CapabilityUtil.isDeveloperActivityEnabled()) {
            new NameColumn(getEditingDomain(), viewer, true);
        }
    }

    @Override
    protected TableAddAction getAddAction(ColumnViewer viewer) {
        return new TableAddAction(viewer,
                Messages.EnumLiteralsTabSection_add_action,
                Messages.EnumLiteralsTabSection_add_action_tooltip) {
            @Override
            protected Object addRow(StructuredViewer viewer) {
                EObject input = getInput();
                EnumerationLiteral literal = null;
                if (input instanceof Enumeration) {
                    literal = UMLFactory.eINSTANCE.createEnumerationLiteral();
                    literal.setName(UML2ModelUtil
                            .createUniqueEnumLitName((Enumeration) input));

                    Command cmd =
                            AddCommand.create(getEditingDomain(),
                                    input,
                                    FEATURE,
                                    literal);
                    ((AddCommand) cmd)
                            .setLabel(Messages.EnumLiteralsTabSection_addLiteral_message_label);
                    getEditingDomain().getCommandStack().execute(cmd);
                }
                return literal;
            }
        };
    }

    @Override
    protected TableDeleteAction getDeleteAction(ColumnViewer viewer) {
        return new TableDeleteAction(viewer,
                Messages.EnumLiteralsTabSection_remove_action,
                Messages.EnumLiteralsTabSection_remove_action_tooltip) {
            @Override
            protected void deleteRows(IStructuredSelection selection) {
                EObject input = getInput();

                if (input instanceof Enumeration && selection != null
                        && !selection.isEmpty()) {
                    Command cmd =
                            RemoveCommand.create(getEditingDomain(),
                                    input,
                                    FEATURE,
                                    selection.toList());
                    ((RemoveCommand) cmd)
                            .setLabel(selection.size() > 1 ? Messages.EnumLiteralsTabSection_removeMultipleLiterals_command_label
                                    : Messages.EnumLiteralsTabSection_removeSingleLiteral_command_label);
                    getEditingDomain().getCommandStack().execute(cmd);
                }
            }
        };
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     */
    public boolean select(Object toTest) {
        return resollveInput(toTest) instanceof Enumeration;
    }

}
