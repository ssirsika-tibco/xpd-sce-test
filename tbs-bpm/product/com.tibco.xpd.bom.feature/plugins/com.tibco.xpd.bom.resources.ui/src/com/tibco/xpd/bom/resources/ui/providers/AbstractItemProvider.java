/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.resources.ui.providers;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.services.icon.IconService;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.VisibilityKind;

import com.tibco.xpd.bom.resources.firstclassprofiles.FirstClassProfileManager;
import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.bom.resources.ui.internal.properties.ActionDelegateCommandWrapper;
import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Base class for BOM specific adapters.
 * 
 * 
 * @author wzurek
 * 
 */
public abstract class AbstractItemProvider implements Adapter,
        IStructuredItemContentProvider, ITreeItemContentProvider,
        IItemLabelProvider, IItemPropertySource, IEditingDomainItemProvider {

    protected final String GENERAL_CATEGORY =
            Messages.TypeItemProvider_general_label;

    private final AdapterFactory adapterFactory;

    public AbstractItemProvider(AdapterFactory adapterFactory) {
        this.adapterFactory = adapterFactory;
    }

    public void notifyChanged(Notification notification) {
        // do nothing
    }

    public void setTarget(Notifier newTarget) {
        // do nothing
    }

    /**
     * the adapter can be on many targets, this method always return null.
     */
    public Notifier getTarget() {
        return null;
    }

    /**
     * check if the type is in hte factory supported types.
     */
    public boolean isAdapterForType(Object type) {
        return adapterFactory.isFactoryForType(type);
    }

    /**
     * Access to the adapter factory that created this adapter.
     * 
     * @return adapter factory
     */
    public AdapterFactory getAdapterFactory() {
        return adapterFactory;
    }

    /**
     * Default behaviour is to return target. Clients may override.
     */
    public Object getEditableValue(Object object) {
        return object;
    }

    /**
     * Default behaviour is to return direct EMF container. Clients may
     * override.
     */
    public Object getParent(Object object) {
        return ((EObject) object).eContainer();
    }

    /**
     * Compute using list produced by {@link #getChildren(Object)}.
     */
    public boolean hasChildren(Object object) {
        return !getChildren(object).isEmpty();
    }

    /**
     * Return the same as {@link #getChildren(Object)}.
     */
    public Collection<?> getElements(Object object) {
        return getChildren(object);
    }

    /**
     * Access to the particular property by the ID.
     */
    public final IItemPropertyDescriptor getPropertyDescriptor(Object object,
            Object propertyID) {
        for (IItemPropertyDescriptor pd : getPropertyDescriptors(object)) {
            if (propertyID.equals(pd.getId(object))) {
                return pd;
            }
        }
        return null;
    }

    /**
     * Create command. This implementation handles create child, set, add and
     * remove.
     */
    public Command createCommand(Object object, EditingDomain editingDomain,
            java.lang.Class<? extends Command> commandClass,
            CommandParameter commandParameter) {
        if (commandClass.equals(CreateChildCommand.class)) {
            return getCreateChildCommand(object,
                    editingDomain,
                    commandParameter);
        } else if (commandClass.equals(SetCommand.class)) {
            return getSetCommand(object, editingDomain, commandParameter);
        } else if (commandClass.equals(AddCommand.class)) {
            return getAddCommand(object, editingDomain, commandParameter);
        } else if (commandClass.equals(RemoveCommand.class)) {
            return getRemoveCommand(object, editingDomain, commandParameter);
        }
        return UnexecutableCommand.INSTANCE;
    }

    /**
     * Create the Remove command. This uses the EMD <code>RemoveCommand</code>.
     * 
     * @param object
     * @param editingDomain
     * @param parameter
     * @return
     */
    private Command getRemoveCommand(Object object,
            EditingDomain editingDomain, CommandParameter parameter) {

        return new RemoveCommand(editingDomain, parameter.getEOwner(),
                parameter.getEStructuralFeature(), parameter.getCollection());
    }

    /**
     * Create Add command. This uses the EMF <code>AddCommand</code>.
     * 
     * @param object
     * @param editingDomain
     * @param parameter
     * @return
     */
    protected Command getAddCommand(Object object, EditingDomain editingDomain,
            CommandParameter parameter) {

        return new AddCommand(editingDomain, parameter.getEOwner(), parameter
                .getEStructuralFeature(), parameter.getCollection(), parameter
                .getIndex());
    }

    /**
     * Create Set command. This uses the EMF <code>SetCommand</code>.
     * 
     * @param object
     * @param editingDomain
     * @param parameter
     * @return
     */
    protected Command getSetCommand(Object object, EditingDomain editingDomain,
            CommandParameter parameter) {
        Command cmd = null;

        if (parameter.getIndex() == CommandParameter.NO_INDEX) {
            cmd =
                    new SetCommand(editingDomain, parameter.getEOwner(),
                            parameter.getEStructuralFeature(), parameter
                                    .getValue());
        } else {
            cmd =
                    new SetCommand(editingDomain, parameter.getEOwner(),
                            parameter.getEStructuralFeature(), parameter
                                    .getValue(), parameter.getIndex());
        }

        return cmd;
    }

    /**
     * Create child command. This use GMF type factory to create child command.
     * 
     * @param object
     * @param editingDomain
     * @param commandParameter
     * @return
     */
    protected Command getCreateChildCommand(Object object,
            EditingDomain editingDomain, CommandParameter commandParameter) {

        CommandParameter innerParam;
        if (commandParameter.getValue() instanceof CommandParameter) {
            innerParam = (CommandParameter) commandParameter.getValue();
            if (innerParam.getFeature() instanceof IElementType) {

                IElementType childtype = (IElementType) innerParam.getFeature();
                IElementType parenttype =
                        ElementTypeRegistry.getInstance()
                                .getElementType((EObject) object);
                CreateElementRequest req =
                        new CreateElementRequest((EObject) object, childtype);
                ICommand cmd = parenttype.getEditHelper().getEditCommand(req);

                ActionDelegateCommandWrapper wrapper =
                        new ActionDelegateCommandWrapper(XpdResourcesPlugin
                                .getDefault().getEditingDomain(), cmd);
                wrapper.setImage(IconService.getInstance().getIcon(childtype));
                wrapper.setText(childtype.getDisplayName());
                return wrapper;
            }
        }
        return UnexecutableCommand.INSTANCE;
    }

    public Collection<?> getNewChildDescriptors(Object object,
            EditingDomain editingDomain, Object sibling) {
        return null;
    }

    /**
     * If this element has a first-class stereotype applied then get its image
     * if one is defined.
     * 
     * @param elem
     * @return Image if one is defined, <code>null</code> otherwise.
     * @since 3.3
     */
    protected Image getFirstClassStereotypeImage(Element elem) {
        Image img = null;
        Stereotype st = getFirstClassStereotype(elem);

        img = getStereotypeImage(st);

        return img;
    }

    /**
     * If this element has a first-class stereotype applied then get its image
     * if one is defined.
     * 
     * @param elem
     * @return Image if one is defined, <code>null</code> otherwise.
     * @since 3.3
     */
    protected Image getStereotypeImage(Stereotype st) {
        Image img = null;

        // Show image if visibility is not set to private
        if (st != null && isPublicStereotype(st)) {
            ImageDescriptor stereoImageDesc =
                    UML2ModelUtil.getStereotypeImageDescriptor(st);

            if (stereoImageDesc != null) {
                String location = UML2ModelUtil.getStereotypeImageLocation(st);

                img = Activator.getDefault().getImageRegistry().get(location);
                if (img == null) {
                    // The image is not yet in the Registry, so put it
                    // in
                    Activator.getDefault().getImageRegistry().put(location,
                            stereoImageDesc);
                    img =
                            Activator.getDefault().getImageRegistry()
                                    .get(location);
                }
            }
        }
        return img;
    }

    /**
     * Get the {@link Stereotype} if first-class stereotype is applied to the
     * element.
     * 
     * @param element
     * @return
     */
    protected Stereotype getFirstClassStereotype(Element element) {
        if (element != null
                && element.getModel() != null
                && FirstClassProfileManager.getInstance()
                        .isFirstClassProfileApplied(element.getModel())) {
            // use the stereotype's icon if it has one
            EList<Stereotype> stereos = element.getAppliedStereotypes();

            if (!stereos.isEmpty()) {
                return stereos.get(0);
            }
        }
        return null;
    }

    /**
     * Check if the given stereotype is marked as public.
     * 
     * @param stereotype
     * @return
     */
    protected boolean isPublicStereotype(Stereotype stereotype) {
        return stereotype != null
                && (!stereotype.isSetVisibility() || stereotype.getVisibility() != VisibilityKind.PRIVATE_LITERAL);
    }
}
