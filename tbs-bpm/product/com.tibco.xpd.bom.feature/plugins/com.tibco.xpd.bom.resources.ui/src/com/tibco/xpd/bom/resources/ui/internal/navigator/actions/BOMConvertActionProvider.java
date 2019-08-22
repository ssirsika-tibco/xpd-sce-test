/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.resources.ui.internal.navigator.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.ui.action.CommandActionHandler;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.emf.type.core.ClientContextManager;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.globaldata.commands.GlobalDataPropertyStereotypeCommand;
import com.tibco.xpd.bom.globaldata.commands.GlobalDataStereotypeCommand;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.BOMImages;
import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.GovernanceStateService;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Convert action provider for the UML objects in the BOM file.
 * 
 */
public class BOMConvertActionProvider extends CommonActionProvider {

    /**
     * Default constructor.
     */
    public BOMConvertActionProvider() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.navigator.CommonActionProvider#init(org.eclipse.ui.navigator
     * .ICommonActionExtensionSite)
     */
    @Override
    public void init(ICommonActionExtensionSite site) {
        super.init(site);
    }

    @Override
    public void fillContextMenu(IMenuManager menu) {
        MenuManager subMenu =
                new MenuManager(
                        Messages.BOMAddChildActionProvider_convertTo_menu);

        if (getContext() != null
                && getContext().getSelection() instanceof IStructuredSelection) {
            IStructuredSelection selection =
                    (IStructuredSelection) getContext().getSelection();

            // Make sure all the values are the same if there is a multiple
            // selection
            List<Class> classes = getClasses(selection);
            if ((classes != null) && !classes.isEmpty()) {
                Object sel = selection.getFirstElement();

                AdapterFactory af =
                        XpdResourcesPlugin.getDefault().getAdapterFactory();
                IEditingDomainItemProvider provider =
                        (IEditingDomainItemProvider) af.adapt(sel,
                                IEditingDomainItemProvider.class);

                if (provider != null) {

                    // Need to also see if the Global Data menu options need to
                    // be added
                    addGlobalDataChildActions(sel, subMenu, classes);
                }
            } else {
                List<Property> properties = getProperties(selection);
                if ((properties != null) && !properties.isEmpty()) {
                    Object sel = selection.getFirstElement();

                    AdapterFactory af =
                            XpdResourcesPlugin.getDefault().getAdapterFactory();
                    IEditingDomainItemProvider provider =
                            (IEditingDomainItemProvider) af.adapt(sel,
                                    IEditingDomainItemProvider.class);

                    if (provider != null) {
                        // Need to also see if the Global Data menu options need
                        // to be added
                        addGlobalDataPropertyActions(sel, subMenu, properties);
                    }
                }
            }
            if (!subMenu.isEmpty()) {
                menu.appendToGroup(ICommonMenuConstants.GROUP_NEW, subMenu);
            }
        }
    }

    /**
     * Get all the classes in the selection, returns null if there is a
     * selection made where several different types have been selected
     * 
     * @param selection
     * @return
     */
    private List<Class> getClasses(IStructuredSelection selection) {
        List<Class> classes = new ArrayList<Class>();

        if (selection != null && !selection.isEmpty()) {
            EObject firstItem = (EObject) selection.getFirstElement();
            StereotypeKind firstType = null;
            if (firstItem instanceof Class) {
                if (BOMGlobalDataUtils.isCaseClass((Class) firstItem)) {
                    firstType = StereotypeKind.CASE;
                } else if (BOMGlobalDataUtils.isGlobalClass((Class) firstItem)) {
                    firstType = StereotypeKind.GLOBAL;
                }
            } else {
                return null;
            }

            for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
                EObject nextClass = (EObject) iter.next();
                // Make sure they have the same base type
                if (!(nextClass instanceof Class)) {
                    return null;
                }
                // Mow make sure it is of the correct class type
                if (BOMGlobalDataUtils.isCaseClass((Class) nextClass)
                        && (firstType != StereotypeKind.CASE)) {
                    return null;
                }
                if (BOMGlobalDataUtils.isGlobalClass((Class) nextClass)
                        && (firstType != StereotypeKind.GLOBAL)) {
                    return null;
                }
                if (BOMGlobalDataUtils.isLocal((Class) nextClass)
                        && (firstType != null)) {
                    return null;
                }
                // Must be the same type so add it to the list
                classes.add((Class) nextClass);
            }
        }

        return classes;
    }

    /**
     * Get all the properties in the selection, returns null if there is a
     * selection made where several different types have been selected
     * 
     * @param selection
     * @return
     */
    private List<Property> getProperties(IStructuredSelection selection) {
        List<Property> properties = new ArrayList<Property>();

        if (selection != null && !selection.isEmpty()) {
            EObject firstItem = (EObject) selection.getFirstElement();
            StereotypeKind firstType = null;
            if (firstItem instanceof Property) {
                if (BOMGlobalDataUtils.isAutoCID((Property) firstItem)
                        || BOMGlobalDataUtils.isCID((Property) firstItem)
                        || BOMGlobalDataUtils
                                .isCompositeCID((Property) firstItem)) {
                    firstType = StereotypeKind.AUTO_CASE_IDENTIFIER;
                } else if (BOMGlobalDataUtils.isCaseState((Property) firstItem)) {
                    firstType = StereotypeKind.CASE_STATE;
                }
            } else {
                return null;
            }

            for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
                EObject nextProp = (EObject) iter.next();
                // Make sure they have the same base type
                if (!(nextProp instanceof Property)) {
                    return null;
                }

                // Mow make sure it is of the correct class type
                if (BOMGlobalDataUtils.isAutoCID((Property) nextProp)
                        || BOMGlobalDataUtils.isCID((Property) nextProp)
                        || BOMGlobalDataUtils
                                .isCompositeCID((Property) nextProp)) {
                    if (firstType != StereotypeKind.AUTO_CASE_IDENTIFIER) {
                        return null;
                    }
                } else if (BOMGlobalDataUtils.isCaseState((Property) nextProp)) {
                    if (firstType != StereotypeKind.CASE_STATE) {
                        return null;
                    }
                    // Must be just a normal attribute
                } else if (firstType != null) {
                    return null;
                }
                // Must be the same type so add it to the list
                properties.add((Property) nextProp);
            }
        }

        return properties;
    }

    /**
     * Add all the global data menu items to the Convert list
     * 
     * @param sel
     * @param subMenu
     */
    private void addGlobalDataChildActions(Object sel, MenuManager subMenu,
            List<Class> classes) {

        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();
        WorkingCopy wc = null;

        if (sel instanceof Class) {

            Class clazz = ((Class) sel);
            Model model = clazz.getModel();
            wc = WorkingCopyUtil.getWorkingCopyFor(model);

            if (model != null) {
                /*
                 * Have a global data BOM and we are dealing with a class, so
                 * need to add any Global Data conversions that are valid for
                 * classes to the add menu
                 */
                if (BOMGlobalDataUtils.isGlobalDataBOM(model)) {

                    if (BOMGlobalDataUtils.isLocal(clazz)) {

                        IClientContext cc =
                                ClientContextManager.getInstance()
                                        .getClientContextFor(clazz);

                        CommandParameter commandParameter =
                                new CommandParameter(
                                        null,
                                        ElementTypeRegistry
                                                .getInstance()
                                                .getElementType(UMLPackage.Literals.PROPERTY,
                                                        cc), null);

                        BOMConvertClassAction childAct1 =
                                new BOMConvertClassAction(ed, commandParameter,
                                        StereotypeKind.CASE, classes);
                        childAct1.setText(Messages.Case_label);
                        subMenu.add(childAct1);

                        BOMConvertClassAction childAct2 =
                                new BOMConvertClassAction(ed, commandParameter,
                                        StereotypeKind.GLOBAL, classes);
                        childAct2.setText(Messages.Global_label);
                        subMenu.add(childAct2);

                        /*
                         * Working copy is read only if the project is
                         * pre-compiled. Then we want to disable this action
                         */
                        boolean isWorkingCopyReadOnly = false;
                        if (null != wc) {

                            isWorkingCopyReadOnly = wc.isReadOnly();
                        }
                        if (isWorkingCopyReadOnly) {

                            childAct1.setEnabled(false);
                            childAct2.setEnabled(false);
                        } else {

                            childAct1.setEnabled(true);
                            childAct2.setEnabled(true);
                        }

                    } else if (BOMGlobalDataUtils.isCaseClass(clazz)) {

                        IClientContext cc =
                                ClientContextManager.getInstance()
                                        .getClientContextFor(clazz);

                        CommandParameter commandParameter =
                                new CommandParameter(
                                        null,
                                        ElementTypeRegistry
                                                .getInstance()
                                                .getElementType(UMLPackage.Literals.PROPERTY,
                                                        cc), null);

                        BOMConvertClassAction childAct1 =
                                new BOMConvertClassAction(ed, commandParameter,
                                        null, classes);
                        childAct1.setText(Messages.Local_label);
                        subMenu.add(childAct1);

                        BOMConvertClassAction childAct2 =
                                new BOMConvertClassAction(ed, commandParameter,
                                        StereotypeKind.GLOBAL, classes);
                        childAct2.setText(Messages.Global_label);
                        subMenu.add(childAct2);

                        /*
                         * Working copy is read only if the project is
                         * pre-compiled. Then we want to disable this action
                         */
                        boolean isWorkingCopyReadOnly = false;
                        if (null != wc) {

                            isWorkingCopyReadOnly = wc.isReadOnly();
                        }
                        if (isWorkingCopyReadOnly) {

                            childAct1.setEnabled(false);
                            childAct2.setEnabled(false);
                        } else {

                            childAct1.setEnabled(true);
                            childAct2.setEnabled(true);
                        }

                    } else if (BOMGlobalDataUtils.isGlobalClass(clazz)) {

                        IClientContext cc =
                                ClientContextManager.getInstance()
                                        .getClientContextFor(clazz);

                        CommandParameter commandParameter =
                                new CommandParameter(
                                        null,
                                        ElementTypeRegistry
                                                .getInstance()
                                                .getElementType(UMLPackage.Literals.PROPERTY,
                                                        cc), null);

                        BOMConvertClassAction childAct1 =
                                new BOMConvertClassAction(ed, commandParameter,
                                        null, classes);
                        childAct1.setText(Messages.Local_label);
                        subMenu.add(childAct1);

                        BOMConvertClassAction childAct2 =
                                new BOMConvertClassAction(ed, commandParameter,
                                        StereotypeKind.CASE, classes);
                        childAct2.setText(Messages.Case_label);
                        subMenu.add(childAct2);

                        /*
                         * Working copy is read only if the project is
                         * pre-compiled. Then we want to disable this action
                         */
                        boolean isWorkingCopyReadOnly = false;
                        if (null != wc) {

                            isWorkingCopyReadOnly = wc.isReadOnly();
                        }

                        /*
                         * ACE-2473: Saket: Actions should be disabled for a
                         * locked application.
                         */
                        if (sel instanceof EObject) {
                            isWorkingCopyReadOnly = isWorkingCopyReadOnly
                                    || (new GovernanceStateService()).isLockedForProduction((EObject) (sel));
                        }

                        if (isWorkingCopyReadOnly) {

                            childAct1.setEnabled(false);
                            childAct2.setEnabled(false);
                        } else {

                            childAct1.setEnabled(true);
                            childAct2.setEnabled(true);
                        }
                    }
                }
            }
        }
    }

    /**
     * Add all the global data property menu items to the Convert list
     * 
     * @param sel
     * @param subMenu
     */
    private void addGlobalDataPropertyActions(Object sel, MenuManager subMenu,
            List<Property> properties) {

        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();
        WorkingCopy wc = null;

        if (sel instanceof Property) {

            Property prop = ((Property) sel);
            Model model = prop.getModel();
            if (model != null) {

                wc = WorkingCopyUtil.getWorkingCopyFor(model);
                /*
                 * Check to make sure it is a property of a case class,
                 * otherwise we do not allow changes
                 */
                Class clazz = prop.getClass_();
                if (clazz == null) {

                    return;
                }
                /*
                 * Have a global data BOM and we are dealing with a property, so
                 * need to add any Global Data conversions that are valid for
                 * properties to the add menu
                 */
                if (BOMGlobalDataUtils.isGlobalDataBOM(model)) {

                    /*
                     * Add the options when dealing with any form of case
                     * identifier
                     */
                    if (GlobalDataProfileManager.getInstance().isCID(prop)
                            || GlobalDataProfileManager.getInstance()
                                    .isAutoCaseIdentifier(prop)
                            || GlobalDataProfileManager.getInstance()
                                    .isCompositeCaseIdentifier(prop)) {

                        IClientContext cc =
                                ClientContextManager.getInstance()
                                        .getClientContextFor(prop);

                        CommandParameter commandParameter =
                                new CommandParameter(
                                        null,
                                        ElementTypeRegistry
                                                .getInstance()
                                                .getElementType(UMLPackage.Literals.PROPERTY,
                                                        cc), null);

                        BOMConvertPropertyAction childAct1 =
                                new BOMConvertPropertyAction(ed,
                                        commandParameter, null, properties);
                        childAct1
                                .setText(Messages.BOMAddChildActionProvider_AddChildAttribute_menu);
                        subMenu.add(childAct1);

                        /*
                         * Working copy is read only if the project is
                         * pre-compiled. Then we want to disable this action
                         */
                        boolean isWCReadOnly = false;
                        if (null != wc) {

                            isWCReadOnly = wc.isReadOnly();
                        }
                        if (isWCReadOnly) {

                            childAct1.setEnabled(false);
                        } else {

                            childAct1.setEnabled(true);
                        }

                    } else if (BOMGlobalDataUtils.isCaseState(prop)) {

                        /* Allow convert from case state to attribute */
                        IClientContext cc =
                                ClientContextManager.getInstance()
                                        .getClientContextFor(prop);

                        CommandParameter commandParameter =
                                new CommandParameter(
                                        null,
                                        ElementTypeRegistry
                                                .getInstance()
                                                .getElementType(UMLPackage.Literals.PROPERTY,
                                                        cc), null);

                        BOMConvertPropertyAction childAct1 =
                                new BOMConvertPropertyAction(ed,
                                        commandParameter, null, properties);
                        childAct1
                                .setText(Messages.BOMAddChildActionProvider_AddChildAttribute_menu);
                        subMenu.add(childAct1);

                        /*
                         * Working copy is read only if the project is
                         * pre-compiled. Then we want to disable this action
                         */
                        boolean isWCReadOnly = false;
                        if (null != wc) {

                            isWCReadOnly = wc.isReadOnly();
                        }
                        if (isWCReadOnly) {

                            childAct1.setEnabled(false);
                        } else {

                            childAct1.setEnabled(true);
                        }

                    } else if (BOMGlobalDataUtils.isCaseClass(clazz)) {

                        /*
                         * This must be an attribute, so allow convert to any of
                         * the special global data types
                         */
                        IClientContext cc =
                                ClientContextManager.getInstance()
                                        .getClientContextFor(prop);

                        CommandParameter commandParameter =
                                new CommandParameter(
                                        null,
                                        ElementTypeRegistry
                                                .getInstance()
                                                .getElementType(UMLPackage.Literals.PROPERTY,
                                                        cc), null);

                        BOMConvertPropertyAction childAct1 =
                                new BOMConvertPropertyAction(ed,
                                        commandParameter,
                                        StereotypeKind.AUTO_CASE_IDENTIFIER,
                                        properties);
                        childAct1.setText(Messages.CaseIdentifier_label);
                        subMenu.add(childAct1);

                        BOMConvertPropertyAction childAct2 =
                                new BOMConvertPropertyAction(ed,
                                        commandParameter,
                                        StereotypeKind.CASE_STATE, properties);
                        childAct2.setText(Messages.CaseState_label);
                        subMenu.add(childAct2);

                        /*
                         * Working copy is read only if the project is
                         * pre-compiled. Then we want to disable this action
                         */
                        boolean isWCReadOnly = false;
                        if (null != wc) {

                            isWCReadOnly = wc.isReadOnly();
                        }

                        /*
                         * ACE-2473: Saket: Actions should be disabled for a
                         * locked application.
                         */
                        if (sel instanceof EObject) {
                            isWCReadOnly = isWCReadOnly
                                    || (new GovernanceStateService()).isLockedForProduction((EObject) (sel));
                        }

                        if (isWCReadOnly) {

                            childAct1.setEnabled(false);
                            childAct2.setEnabled(false);
                        } else {

                            childAct1.setEnabled(true);
                            childAct2.setEnabled(true);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void fillActionBars(IActionBars actionBars) {
        super.fillActionBars(actionBars);
    }

    /**
     * Action class that will do the convert for a class
     * 
     */
    public class BOMConvertClassAction extends CommandActionHandler {

        private StereotypeKind stereotype = null;

        private List<Class> classes = null;

        protected GlobalDataProfileManager gdManager = GlobalDataProfileManager
                .getInstance();

        public BOMConvertClassAction(EditingDomain domain) {
            super(domain);
        }

        public BOMConvertClassAction(TransactionalEditingDomain ed,
                CommandParameter commandParameter,
                StereotypeKind stereoTypeKind, List<Class> classes) {
            super(ed);
            this.stereotype = stereoTypeKind;
            this.classes = classes;

            if (stereoTypeKind != null) {
                Stereotype stereotypeRaw =
                        GlobalDataProfileManager.getInstance()
                                .getStereotype(stereoTypeKind);

                // Set the icon to use on the menu based off of the stereotype
                ImageDescriptor stereoImageDesc =
                        UML2ModelUtil
                                .getStereotypeImageDescriptor(stereotypeRaw);

                if (stereoImageDesc != null) {
                    setImageDescriptor(stereoImageDesc);
                }
            } else {
                Image img =
                        Activator.getDefault().getImageRegistry()
                                .get(BOMImages.CLASS);
                if (img != null) {
                    setImageDescriptor(ImageDescriptor.createFromImage(img));
                }
            }
        }

        @Override
        public Command createCommand(Collection<?> selection) {
            if ((classes == null) || classes.isEmpty()) {
                return UnexecutableCommand.INSTANCE;
            }
            CompoundCommand cmds = new CompoundCommand();
            for (Class clazz : classes) {
                // Not having a stereotype means converting to Local Class
                if (stereotype == null) {
                    // For local we just remove the stereotype for either
                    // the Class or Global
                    if (gdManager.isCase(clazz)) {
                        cmds.append(new GlobalDataStereotypeCommand.Unset(
                                getEditingDomain(), clazz, StereotypeKind.CASE));
                    } else if (gdManager.isGlobal(clazz)) {
                        cmds.append(new GlobalDataStereotypeCommand.Unset(
                                getEditingDomain(), clazz,
                                StereotypeKind.GLOBAL));
                    }
                } else {
                    if (stereotype != null) {
                        // check to ensure the stereotype is not already set
                        if (!gdManager.getAppliedStereotypeKinds(clazz)
                                .contains(stereotype)) {
                            TransactionalEditingDomain editingDomain =
                                    getEditingDomain();
                            cmds.append(new GlobalDataStereotypeCommand.Set(
                                    editingDomain, clazz, stereotype));
                        }
                    }
                }
            }

            if (!cmds.isEmpty()) {
                return cmds;
            }

            return UnexecutableCommand.INSTANCE;
        }

        @Override
        public void run() {
            // Recalculate the command as, if the selection hasn't changed then,
            // the previous command may get executed again
            command = createCommand(getStructuredSelection().toList());
            super.run();
        }

        @Override
        public TransactionalEditingDomain getEditingDomain() {
            // We actually saved this, so we know it's actually a
            // TransactionalEditingDomain
            return (TransactionalEditingDomain) super.getEditingDomain();
        }
    }

    /**
     * Action class that will do the convert for a property
     * 
     */
    public class BOMConvertPropertyAction extends CommandActionHandler {

        private StereotypeKind stereotype = null;

        private List<Property> properties = null;

        protected GlobalDataProfileManager gdManager = GlobalDataProfileManager
                .getInstance();

        public BOMConvertPropertyAction(EditingDomain domain) {
            super(domain);
        }

        public BOMConvertPropertyAction(TransactionalEditingDomain ed,
                CommandParameter commandParameter,
                StereotypeKind stereoTypeKind, List<Property> properties) {
            super(ed);
            this.stereotype = stereoTypeKind;
            this.properties = properties;

            if (stereoTypeKind != null) {
                Stereotype stereotypeRaw =
                        GlobalDataProfileManager.getInstance()
                                .getStereotype(stereoTypeKind);

                // Set the icon to use on the menu based off of the stereotype
                ImageDescriptor stereoImageDesc =
                        UML2ModelUtil
                                .getStereotypeImageDescriptor(stereotypeRaw);

                if (stereoImageDesc != null) {
                    setImageDescriptor(stereoImageDesc);
                }
            } else {
                Image img =
                        Activator.getDefault().getImageRegistry()
                                .get(BOMImages.PROPERTY);
                if (img != null) {
                    setImageDescriptor(ImageDescriptor.createFromImage(img));
                }
            }
        }

        @Override
        public Command createCommand(Collection<?> selection) {
            if ((properties == null) || properties.isEmpty()) {
                return UnexecutableCommand.INSTANCE;
            }
            CompoundCommand cmds = new CompoundCommand();
            for (Property prop : properties) {
                // Not having a stereotype means converting to Attribute
                if (stereotype == null) {
                    // For attribute we just remove the stereotype for any case
                    // identifier type of case state
                    if (gdManager.isAutoCaseIdentifier(prop)) {
                        cmds.append(new GlobalDataPropertyStereotypeCommand.Unset(
                                getEditingDomain(), prop,
                                StereotypeKind.AUTO_CASE_IDENTIFIER));
                    } else if (gdManager.isCID(prop)) {
                        cmds.append(new GlobalDataPropertyStereotypeCommand.Unset(
                                getEditingDomain(), prop, StereotypeKind.CID));
                    } else if (gdManager.isCompositeCaseIdentifier(prop)) {
                        cmds.append(new GlobalDataPropertyStereotypeCommand.Unset(
                                getEditingDomain(), prop,
                                StereotypeKind.COMPOSITE_CASE_IDENTIFIER));
                    } else if (gdManager.isCaseState(prop)) {
                        cmds.append(new GlobalDataPropertyStereotypeCommand.Unset(
                                getEditingDomain(), prop,
                                StereotypeKind.CASE_STATE));
                    }
                } else {
                    if (stereotype != null) {
                        // check to ensure the stereotype is not already set
                        if (!gdManager.getAppliedStereotypeKinds(prop)
                                .contains(stereotype)) {
                            TransactionalEditingDomain editingDomain =
                                    getEditingDomain();
                            cmds.append(new GlobalDataPropertyStereotypeCommand.Set(
                                    editingDomain, prop, stereotype));
                        }
                    }
                }
            }

            if (!cmds.isEmpty()) {
                return cmds;
            }

            return UnexecutableCommand.INSTANCE;
        }

        @Override
        public void run() {
            // Recalculate the command as, if the selection hasn't changed then,
            // the previous command may get executed again
            command = createCommand(getStructuredSelection().toList());
            super.run();
        }

        @Override
        public TransactionalEditingDomain getEditingDomain() {
            // We actually saved this, so we know it's actually a
            // TransactionalEditingDomain
            return (TransactionalEditingDomain) super.getEditingDomain();
        }
    }
}
