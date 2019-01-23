/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.propertysection;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.EMFOperationCommand;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.emf.type.core.ClientContextManager;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.columns.LabelColumn;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.columns.MultiplicityColumn;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.columns.NameColumn;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.columns.StereotypesColumn;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.columns.TypeColumn;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.custom.BOMEditPartUtils;
import com.tibco.xpd.bom.resources.firstclassprofiles.FirstClassProfileManager;
import com.tibco.xpd.bom.resources.utils.BOMProfileUtils;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.components.actions.TableAddAction;
import com.tibco.xpd.resources.ui.components.actions.TableDeleteAction;
import com.tibco.xpd.ui.util.CapabilityUtil;

/**
 * BOM properties section for the {@link Property Attributes} of a {@link Class}
 * 
 * 
 * @author njpatel
 * 
 */
public class AttributesTabSection extends AbstractTableSection {

    private static final EReference FEATURE = UMLPackage.eINSTANCE
            .getStructuredClassifier_OwnedAttribute();

    private StereotypesColumn stereotypesColumn;

    private IEditingDomainItemProvider provider;

    public AttributesTabSection() {
        super(Messages.AttributesTabSection_section_title, FEATURE);
    }

    @Override
    protected void addColumns(ColumnViewer viewer) {
        new LabelColumn(getEditingDomain(), viewer, true);
        if (CapabilityUtil.isDeveloperActivityEnabled()) {
            new NameColumn(getEditingDomain(), viewer, true);
        }
        new TypeColumn(getEditingDomain(), viewer,
                Messages.AttributesTabSection_type_column_title, 280, true) {

            @Override
            protected Type getType(Object element) {
                Type type = null;

                if (element instanceof TypedElement) {
                    type = ((TypedElement) element).getType();
                }
                return type;
            }

            @Override
            protected Command getSetValueCommand(Object element, Object value) {
                Command cmd = null;
                if (element instanceof TypedElement) {
                    cmd =
                            SetCommand
                                    .create(getEditingDomain(),
                                            element,
                                            UMLPackage.eINSTANCE
                                                    .getTypedElement_Type(),
                                            value != null ? value
                                                    : SetCommand.UNSET_VALUE);
                }
                return cmd;
            }

            @Override
            protected CellEditor getCellEditor(Object element) {
                if (element instanceof Property
                        && ((Property) element).getAssociation() != null) {
                    // This is an association property so cannot change type
                    return null;
                }
                return super.getCellEditor(element);
            }

        };

        new MultiplicityColumn(getEditingDomain(), viewer) {

            @Override
            protected MultiplicityElement getMultiplicityElement(Object element) {
                return (MultiplicityElement) (element instanceof MultiplicityElement ? element
                        : null);
            }
        };

        stereotypesColumn = new StereotypesColumn(getEditingDomain(), viewer);

    }

    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);

        EObject input = getInput();
        // Hide the stereotype column if this is a first-class profile model
        if (input instanceof Element && stereotypesColumn != null) {
            setVisible(stereotypesColumn,
                    !FirstClassProfileManager.getInstance()
                            .isFirstClassProfileApplied(((Element) input)
                                    .getModel()));
        }

        if (input != null) {
            provider =
                    (IEditingDomainItemProvider) XpdResourcesPlugin
                            .getDefault().getAdapterFactory()
                            .adapt(input, IEditingDomainItemProvider.class);
        }
    }

    @Override
    protected TableAddAction getAddAction(ColumnViewer viewer) {
        return new TableAddAction(viewer,
                Messages.AttributesTabSection_add_action,
                Messages.AttributesTabSection_add_action_tooltip) {

            @Override
            protected Object addRow(StructuredViewer viewer) {
                EObject input = getInput();
                if (provider != null && input instanceof Class) {
                    Collection<?> descriptors =
                            provider.getNewChildDescriptors(input,
                                    getEditingDomain(),
                                    null);
                    for (Object desc : descriptors) {
                        if (desc instanceof CommandParameter) {
                            CommandParameter param = (CommandParameter) desc;

                            if (param.getFeature() instanceof IHintedType
                                    && ((IHintedType) param.getFeature())
                                            .getEClass() == UMLPackage.eINSTANCE
                                            .getProperty()) {
                                Command cmd =
                                        CreateChildCommand
                                                .create(getEditingDomain(),
                                                        input,
                                                        desc,
                                                        Collections
                                                                .singletonList(input));
                                getEditingDomain().getCommandStack()
                                        .execute(cmd);

                                if (cmd.getResult() != null
                                        && !cmd.getResult().isEmpty()) {
                                    return cmd.getResult().iterator().next();
                                }
                            }
                        }
                    }
                }
                return null;
            }
        };
    }

    @Override
    protected TableDeleteAction getDeleteAction(ColumnViewer viewer) {
        return new TableDeleteAction(viewer,
                Messages.AttributesTabSection_remove_action,
                Messages.AttributesTabSection_remove_action_tooltip) {
            @Override
            protected void deleteRows(IStructuredSelection selection) {
                EObject input = getInput();
                if (input instanceof Class && selection != null
                        && !selection.isEmpty()) {
                    IClientContext context =
                            ClientContextManager.getInstance()
                                    .getClientContextFor(input);
                    IElementType classType =
                            ElementTypeRegistry
                                    .getInstance()
                                    .getElementType(UMLPackage.eINSTANCE.getClass_(),
                                            context);
                    Iterator<?> iter = selection.iterator();
                    CompositeCommand cc =
                            new CompositeCommand(
                                    selection.size() > 1 ? Messages.AttributesTabSection_removeMultipleAttributes_command_label
                                            : Messages.AttributesTabSection_removeSingleAttribute_command_label);
                    while (iter.hasNext()) {
                        DestroyElementRequest req =
                                new DestroyElementRequest(
                                        (EObject) iter.next(), false);
                        req.setClientContext(context);
                        cc.add(classType.getEditCommand(req));
                    }
                    EMFOperationCommand opCmd =
                            new EMFOperationCommand(
                                    (TransactionalEditingDomain) getEditingDomain(),
                                    cc);

                    if (opCmd.canExecute()) {
                        getEditingDomain().getCommandStack().execute(opCmd);
                    }
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

        boolean select = false;

        EObject obj = resollveInput(toTest);

        if (obj instanceof Class) {
            select = true;
        } else if (obj instanceof PrimitiveType) {
            if (BOMProfileUtils.isXSDProfileApplied(((PrimitiveType) obj)
                    .getModel())) {
                select = true;
            }
        }

        return select;
    }

    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        if (notifications != null) {
            for (Notification notification : notifications) {
                if (BOMEditPartUtils.isStereotypeBeingSet(notification)) {
                    Object featureObjectAffected =
                            BOMEditPartUtils
                                    .getFeatureObjectAffected(notification);
                    /*
                     * The object being affected will be an attribute so check
                     * if it belongs to the input (Class)
                     */
                    if (featureObjectAffected instanceof EObject
                            && ((EObject) featureObjectAffected).eContainer() == getInput()) {
                        return true;
                    }
                }
            }
        }
        return super.shouldRefresh(notifications);
    }
}
