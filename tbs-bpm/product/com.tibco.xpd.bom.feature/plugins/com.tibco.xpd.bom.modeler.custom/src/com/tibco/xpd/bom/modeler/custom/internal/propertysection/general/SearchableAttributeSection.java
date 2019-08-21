package com.tibco.xpd.bom.modeler.custom.internal.propertysection.general;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.util.UMLUtil;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.globaldata.resources.SummaryInfoUtils;
import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.AbstractGeneralSection;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Handles the addition of the search-able tick box for attributes on Case and
 * Global classes
 * 
 */
public class SearchableAttributeSection extends AbstractGeneralSection {

    private Button searchCheck = null;

    private Button summaryCheck = null;

    @Override
    protected boolean shouldDisplay(EObject eo) {

        // Only display the search-able data if dealing with a Global Data
        // related class
        if (eo instanceof Property) {
            Property prop = (Property) eo;
            // Hide for ANY caseId or caseState properties.
            if (BOMGlobalDataUtils.isCID(prop)
                    || BOMGlobalDataUtils.isCaseState(prop)) {
                return false;
            }
            // Do not want to display search-able for properties (i.e.
            // multiplicity) on associations
            if (prop.getAssociation() == null) {
                Class class_ = prop.getClass_();
                if ((class_ != null) && (BOMGlobalDataUtils.isCaseClass(class_)
                        || BOMGlobalDataUtils.isGlobalClass(class_))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        final Property prop = getSemanticInput();
        if (prop == null) {
            return null;
        }
        // Global data : Search-able tick-box attribute for attribute
        if (obj == searchCheck) {
            // Get Stereotype for Search-able
            boolean existingValue = false;
            final Stereotype serchableStereotype = GlobalDataProfileManager.getInstance()
                    .getStereotype(StereotypeKind.SEARCHABLE);

            // Check if the stereotype is already applied
            Object value = getPropertyTaggedValue(prop,
                    StereotypeKind.SEARCHABLE,
                    BOMResourcesPlugin.ModelGlobalDataProfile_attribute_Searchable);
            existingValue = (value instanceof Boolean)
                    ? ((Boolean) value).booleanValue()
                    : false;

            // Check if the value has changed
            if (searchCheck != null
                    && searchCheck.getSelection() != existingValue) {
                return new RecordingCommand(
                        (TransactionalEditingDomain) getEditingDomain()) {
                    @Override
                    protected void doExecute() {
                        // If ticked then enable the stereotype
                        if (searchCheck.getSelection()) {
                            if (!prop.getAppliedStereotypes().contains(serchableStereotype)) {
                                prop.applyStereotype(serchableStereotype);
                            }
                            prop.setValue(serchableStereotype,
                                    BOMResourcesPlugin.ModelGlobalDataProfile_attribute_Searchable,
                                    true);
                        } else {
                            // Remove the stereotype if not enabled
                            if (prop.getAppliedStereotypes().contains(serchableStereotype)) {
                                prop.unapplyStereotype(serchableStereotype);
                            }
                        }
                    }
                };
            }
        }
        // Global data : Summary tick-box property
        if (obj == summaryCheck) {
            boolean existingValue = SummaryInfoUtils.isSummary(prop);
            // Check if the value has changed
            if (summaryCheck != null
                    && summaryCheck.getSelection() != existingValue) {
                return new RecordingCommand(
                        (TransactionalEditingDomain) getEditingDomain()) {
                    @Override
                    protected void doExecute() {
                        SummaryInfoUtils.setSummary(prop,
                                summaryCheck.getSelection());
                    }
                };
            }
        }
        return null;
    }

    @Override
    protected Control doCreateControls(Composite parent,
            XpdFormToolkit toolkit) {
        Composite root = (Composite) super.doCreateControls(parent, toolkit);

        createLabel(root, toolkit, Messages.Searchable_label);
        searchCheck = toolkit.createButton(root, /* label */null, SWT.CHECK); // $NON-NLS-1$
        searchCheck.setToolTipText(Messages.Searchable_tooltip);
        setLayoutData(searchCheck);
        manageControl(searchCheck);

        createLabel(root,
                toolkit,
                Messages.SearchableAttributeSection_Summary_label);
        summaryCheck = toolkit.createButton(root, /* label */null, SWT.CHECK); // $NON-NLS-1$
        summaryCheck.setToolTipText(
                Messages.SearchableAttributeSection_Summary_tooltip);
        setLayoutData(summaryCheck);
        manageControl(summaryCheck);
        return root;
    }

    @Override
    protected void doRefresh() {
        final Property prop = getSemanticInput();
        if (prop == null) {
            return;
        }

        // Make sure the tick box is initialised before we do anything
        if (searchCheck != null && !searchCheck.isDisposed()) {
            // If this is a case Identifier or a case state, then it should be
            // set by default and not edit-able
            if (BOMGlobalDataUtils.isCID(prop)
                    || BOMGlobalDataUtils.isCaseState(prop)) {
                searchCheck.setSelection(true);
                searchCheck.setEnabled(false);
                return;
            }

            // Make sure it is enabled
            searchCheck.setEnabled(true);
            Object value = getPropertyTaggedValue(prop,
                    StereotypeKind.SEARCHABLE,
                    BOMResourcesPlugin.ModelGlobalDataProfile_attribute_Searchable);
            boolean isSearchable = (value instanceof Boolean)
                    ? ((Boolean) value).booleanValue()
                    : false;
            searchCheck.setSelection(isSearchable);

            // Prevent setting searchable for unsupported properties (unless
            // it's already set).
            if (!isSearchSupported(prop) && !isSearchable) {
                searchCheck.setEnabled(false);
            }
        }

        // Make sure the tick box is initialised before we do anything
        if (summaryCheck != null && !summaryCheck.isDisposed()) {
            // If this is a case Identifier or a case state, then it should be
            // set by default and not edit-able
            if (BOMGlobalDataUtils.isCID(prop)
                    || BOMGlobalDataUtils.isCaseState(prop)) {
                summaryCheck.setSelection(true);
                summaryCheck.setEnabled(false);
                return;
            }

            // Make sure it is enabled
            summaryCheck.setEnabled(true);
            boolean isSummary = SummaryInfoUtils.isSummary(prop);
            summaryCheck.setSelection(isSummary);

            // Prevent setting summary for unsupported properties (unless it's
            // already set).
            if (!isSummarySupported(prop) && !isSummary) {
                summaryCheck.setEnabled(false);
            }
        }
    }

    /**
     * Retrieves the property
     * 
     * @return
     */
    private Property getSemanticInput() {
        EObject input = getInput();
        if (input instanceof Property) {
            return (Property) input;
        }
        return null;
    }

    /**
     * Check if searching is supported for this property.
     * 
     * @param prop
     *            Property to check
     * @return
     */
    private boolean isSearchSupported(Property prop) {
        // Check to see if this is a Duration type, we do not allow
        // searchable for a Duration Primitive type
        Type type = prop.getType();
        if (type instanceof PrimitiveType) {
            // Get the base type in case this is a BOM primitive type defined by
            // the user, we want to get the type of that for checking
            PrimitiveType primType =
                    PrimitivesUtil.getBasePrimitiveType((PrimitiveType) type);

            // Reject durations
            if (PrimitivesUtil.BOM_PRIMITIVE_DURATION_NAME
                    .equals(primType.getName())) {
                return false;
            }
            return true;
        } else if (type instanceof Enumeration) {
            // Enumerations in SCE are treated as Text types
            return true;
        }
        return false;
    }

    /**
     * Check if summary is supported for this property.
     * 
     * @param prop
     *            Property to check
     * @return <code>true</code> if summary is supported.
     */
    private boolean isSummarySupported(Property prop) {
        // Check to see if this is a Duration type, we do not allow
        // searchable for a Duration Primitive type
        Type type = prop.getType();
        if (type instanceof PrimitiveType) {
            // Get the base type in case this is a BOM primitive type defined by
            // the user, we want to get the type of that for checking
            PrimitiveType primType =
                    PrimitivesUtil.getBasePrimitiveType((PrimitiveType) type);

            // Reject durations
            if (PrimitivesUtil.BOM_PRIMITIVE_DURATION_NAME
                    .equals(primType.getName())) {
                return false;
            }
            return true;
        } else if (type instanceof Enumeration) {
            // Enumerations in SCE are treated as Text types
            return true;
        }
        return false;
    }

    /**
     * Gets the tagged value for the applied stereotype on the property.
     * 
     * @param prop
     *            context property.
     * @param kind
     *            the stereotype kind.
     * @param stereoAttrName
     *            the name of the stereotype property that holds the required
     *            value.
     * @return the tagged value of the stereotype property or <code>null</code>.
     */
    private Object getPropertyTaggedValue(Property prop, StereotypeKind kind,
            String stereoAttrName) {
        final Stereotype stereo =
                GlobalDataProfileManager.getInstance().getStereotype(kind);
        if (stereo != null && prop.getAppliedStereotypes().contains(stereo)) {
            return prop.getValue(stereo, stereoAttrName);
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.bom.modeler.custom.internal.propertysection.AbstractGeneralSection#shouldRefresh(java.util.List)
     */
    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        if (super.shouldRefresh(notifications)) {
            return true;
        }
        if (notifications != null) {
            for (Notification notification : notifications) {
                if (notification != null && !notification.isTouch()) {
                    Object notifier = notification.getNotifier();
                    if (notifier instanceof EObject) {
                        EObject eo = (EObject) notifier;
                        Element base = UMLUtil.getBaseElement(eo);
                        Object feature = notification.getFeature();
                        if (base instanceof Class
                                && feature instanceof EAttribute) {
                            Class aClass = (Class) base;
                            String featureName =
                                    ((EAttribute) feature).getName();
                            boolean isSummaryAttributes =
                                    BOMResourcesPlugin.ModelGlobalDataProfile_CaseClass_Summary
                                            .equals(featureName);
                            if (BOMGlobalDataUtils.isCaseClass(aClass)
                                    && isSummaryAttributes) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
