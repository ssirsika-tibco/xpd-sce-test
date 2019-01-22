/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.resources.ui.internal.navigator.actions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.ui.action.CreateChildAction;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.emf.type.core.ClientContextManager;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.resources.firstclassprofiles.FirstClassProfileManager;
import com.tibco.xpd.bom.resources.firstclassprofiles.IFirstClassProfileExtension;
import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.bom.resources.ui.internal.properties.ActionDelegateCommandWrapper;
import com.tibco.xpd.bom.resources.utils.BOMProfileUtils;
import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Action provider to add a child (package, class or attribute) in the project
 * explorer. For the main <code>Package</code> (model) a
 * <code>PrimitiveType</code>, <code>Package</code> or <code>Class</code> can be
 * added. For a <code>Package</code> a <code>Package</code> or
 * <code>Class</code> can be added, and for a <code>Class</code> an
 * <code>Attribute</code> can be added.
 * 
 * @author njpatel
 */
public class BOMAddChildActionProvider extends CommonActionProvider {

    private final IWorkbenchPart activePart;

    /**
     * Default constructor.
     */
    public BOMAddChildActionProvider() {
        activePart =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage().getActivePart();
    }

    @Override
    public void init(ICommonActionExtensionSite site) {

        super.init(site);
    }

    @Override
    public void fillContextMenu(IMenuManager menu) {

        MenuManager subMenu =
                new MenuManager(
                        Messages.BOMAddChildActionProvider_addChild_menu);
        WorkingCopy wc = null;
        if (getContext() != null
                && getContext().getSelection() instanceof IStructuredSelection) {

            IStructuredSelection selection =
                    (IStructuredSelection) getContext().getSelection();

            if (selection.size() == 1) {

                Object sel = selection.getFirstElement();

                if (sel instanceof EObject) {

                    wc = WorkingCopyUtil.getWorkingCopyFor((EObject) sel);
                }

                AdapterFactory af =
                        XpdResourcesPlugin.getDefault().getAdapterFactory();
                IEditingDomainItemProvider provider =
                        (IEditingDomainItemProvider) af.adapt(sel,
                                IEditingDomainItemProvider.class);

                if (provider != null) {

                    Collection<?> descriptors =
                            provider.getNewChildDescriptors(sel,
                                    XpdResourcesPlugin.getDefault()
                                            .getEditingDomain(),
                                    null);
                    if (descriptors != null) {

                        for (Object descriptor : descriptors) {

                            NewChildAction childAct =
                                    new NewChildAction(activePart, selection,
                                            descriptor);

                            if (descriptor instanceof CommandParameter) {
                                CommandParameter cp =
                                        (CommandParameter) descriptor;

                                if (cp.getFeature() instanceof IElementType) {
                                    IElementType feature =
                                            (IElementType) cp.getFeature();

                                    /*
                                     * Make sure a Property is displayed as
                                     * "Attribute"
                                     */
                                    if (feature
                                            .getEClass()
                                            .equals(UMLPackage.Literals.PROPERTY)) {

                                        childAct.setText(Messages.BOMAddChildActionProvider_AddChildAttribute_menu);
                                    }

                                    /*
                                     * Sort out what to display for a Concept
                                     * class
                                     */
                                    if ((feature.getEClass()
                                            .equals(UMLPackage.Literals.CLASS))
                                            && (sel instanceof Package)) {

                                        Package pkg = (Package) sel;
                                        if (FirstClassProfileManager
                                                .getInstance()
                                                .isFirstClassProfileApplied(pkg.getModel())) {

                                            setFirstClassLabelAndIcon(childAct,
                                                    pkg.getModel());
                                        }
                                    }
                                }
                            }

                            subMenu.add(childAct);

                            /*
                             * Working copy is read only if the project is
                             * pre-compiled. Then we want to disable this action
                             */
                            boolean isWorkingCopyReadOnly = false;

                            if (null != wc) {

                                isWorkingCopyReadOnly = wc.isReadOnly();
                            }
                            if (isWorkingCopyReadOnly) {

                                childAct.setEnabled(false);
                            } else {

                                childAct.setEnabled(true);
                            }
                        }
                    }

                    /*
                     * Need to also see if the Global Data menu options need to
                     * be added
                     */
                    addGlobalDataChildActions(sel, selection, subMenu);
                }
            }
            if (!subMenu.isEmpty()) {

                menu.appendToGroup(ICommonMenuConstants.GROUP_NEW, subMenu);
            }
        }
    }

    /**
     * 
     * Given a child action this method will set the icon and label to be
     * relevant to the concept stereotype
     * 
     * @param childAct
     * @param mod
     */
    private void setFirstClassLabelAndIcon(CreateChildAction childAct, Model mod) {

        /* Set the Label */
        childAct.setText(Messages.BOMAddChildActionProvider_AddChildClass_menu);

        /* Then the icon */
        String imgURI = null;

        IFirstClassProfileExtension ext =
                FirstClassProfileManager.getInstance()
                        .getAppliedFirstClassProfile(mod);
        if (ext != null) {
            Profile profile = ext.getProfile();
            if (profile != null) {
                EClass type = UMLPackage.eINSTANCE.getClass_();

                imgURI = BOMProfileUtils.getStereotypeIconURI(type, profile);

                if (imgURI != null) {
                    ImageDescriptor desc =
                            Activator.getDefault().getImageRegistry()
                                    .getDescriptor(imgURI);

                    if (desc == null) {
                        URL url;
                        try {
                            url = new URL(imgURI);
                            desc = ImageDescriptor.createFromURL(url);

                        } catch (MalformedURLException e) {
                            Activator.getDefault().getLogger().error(e);
                        }

                        if (desc != null) {
                            Activator.getDefault().getImageRegistry()
                                    .put(imgURI, desc);
                        }
                    }

                    if (desc != null) {
                        childAct.setImageDescriptor(desc);
                    }
                }
            }
        }
    }

    /**
     * Add all the global data menu items to the add list
     * 
     * @param sel
     * @param selection
     * @param subMenu
     */
    private void addGlobalDataChildActions(Object sel,
            IStructuredSelection selection, MenuManager subMenu) {

        WorkingCopy wc = null;
        if (sel instanceof Package) {

            Model model = ((Package) sel).getModel();
            wc = WorkingCopyUtil.getWorkingCopyFor(model);
            if (BOMGlobalDataUtils.isGlobalDataBOM(model)) {
                /*
                 * Have a global data BOM and we are dealing with a a model, so
                 * need to add any global data objects that can be added to a
                 * model
                 */
                IClientContext cc =
                        ClientContextManager.getInstance()
                                .getClientContextFor((EObject) sel);

                Stereotype stereotype =
                        GlobalDataProfileManager.getInstance()
                                .getStereotype(StereotypeKind.CASE);

                CommandParameter commandParameter =
                        new CommandParameter(null, ElementTypeRegistry
                                .getInstance()
                                .getElementType(UMLPackage.Literals.CLASS, cc),
                                null);

                NewChildAction childAct2 =
                        new NewChildAction(activePart, selection,
                                commandParameter, stereotype);
                childAct2.setText("Case");
                subMenu.add(childAct2);

                /* Also add the global class */
                Stereotype globalStereotype =
                        GlobalDataProfileManager.getInstance()
                                .getStereotype(StereotypeKind.GLOBAL);
                NewChildAction childAct3 =
                        new NewChildAction(activePart, selection,
                                commandParameter, globalStereotype);
                childAct3.setText("Global");
                subMenu.add(childAct3);

                /*
                 * Working copy is read only if the project is pre-compiled.
                 * Then we want to disable this action
                 */
                boolean isWorkingCopyReadOnly = false;
                if (null != wc) {

                    isWorkingCopyReadOnly = wc.isReadOnly();
                }
                if (isWorkingCopyReadOnly) {

                    childAct2.setEnabled(false);
                    childAct3.setEnabled(false);
                } else {

                    childAct2.setEnabled(true);
                    childAct3.setEnabled(true);
                }
            }
        }

        if (sel instanceof Class) {

            Model model = ((Class) sel).getModel();
            wc = WorkingCopyUtil.getWorkingCopyFor(model);
            if (model != null) {

                /*
                 * Have a global data BOM and we are dealing with a class, so
                 * need to add any Global Data properties to the add menu, in
                 * our case this is Case State and auto-case-identifier, so only
                 * add to the Case Class
                 */
                if (BOMGlobalDataUtils.isGlobalDataBOM(model)
                        && BOMGlobalDataUtils.isCaseClass((Class) sel)) {

                    IClientContext cc =
                            ClientContextManager.getInstance()
                                    .getClientContextFor((EObject) sel);

                    Stereotype stereotypeCID =
                            GlobalDataProfileManager
                                    .getInstance()
                                    .getStereotype(StereotypeKind.AUTO_CASE_IDENTIFIER);

                    CommandParameter commandParameter =
                            new CommandParameter(
                                    null,
                                    ElementTypeRegistry
                                            .getInstance()
                                            .getElementType(UMLPackage.Literals.PROPERTY,
                                                    cc), null);

                    NewChildAction childAct2 =
                            new NewChildAction(activePart, selection,
                                    commandParameter, stereotypeCID);
                    childAct2.setText(Messages.CaseIdentifier_label);
                    subMenu.add(childAct2);

                    Stereotype stereotypeCaseState =
                            GlobalDataProfileManager.getInstance()
                                    .getStereotype(StereotypeKind.CASE_STATE);
                    NewChildAction childAct3 =
                            new NewChildAction(activePart, selection,
                                    commandParameter, stereotypeCaseState);
                    childAct3.setText(Messages.CaseState_label);
                    subMenu.add(childAct3);

                    /*
                     * Working copy is read only if the project is pre-compiled.
                     * Then we want to disable this action
                     */
                    boolean isWCReadOnly = false;
                    if (null != wc) {

                        isWCReadOnly = wc.isReadOnly();
                    }
                    if (isWCReadOnly) {

                        childAct2.setEnabled(false);
                        childAct3.setEnabled(false);
                    } else {

                        childAct2.setEnabled(true);
                        childAct3.setEnabled(true);
                    }
                }
            }
        }

    }

    /**
     * Create child action that will create the requested child and select it in
     * the Project Explorer.
     * 
     * @author njpatel
     * 
     */
    private class NewChildAction extends CreateChildAction {

        private Stereotype stereotype = null;

        public NewChildAction(IWorkbenchPart workbenchPart,
                ISelection selection, Object descriptor) {
            super(workbenchPart, selection, descriptor);
            stereotype = null;
        }

        public NewChildAction(IWorkbenchPart workbenchPart,
                ISelection selection, Object descriptor, Stereotype stereotype) {
            super(workbenchPart, selection, descriptor);
            this.stereotype = stereotype;

            if (stereotype != null) {
                // Set the icon to use on the menu based off of the stereotype
                ImageDescriptor stereoImageDesc =
                        UML2ModelUtil.getStereotypeImageDescriptor(stereotype);

                if (stereoImageDesc != null) {
                    setImageDescriptor(stereoImageDesc);
                }
            }
        }

        @Override
        protected Command createActionCommand(EditingDomain editingDomain,
                Collection<?> collection) {
            // Get the default command, this will perform the creation of the
            // required item
            Command createCommand =
                    super.createActionCommand(editingDomain, collection);

            // Create a new command to set the stereo type
            RecordingCommand stereoCommand =
                    new RecordingCommand(
                            (TransactionalEditingDomain) editingDomain) {

                        @Override
                        protected void doExecute() {
                            // Check if there is a stereotype to be applied
                            if (stereotype != null) {
                                Collection<?> result = command.getResult();
                                if (result != null && !result.isEmpty()) {
                                    for (Object object : result) {
                                        if (object instanceof Element) {
                                            ((Element) object)
                                                    .applyStereotype(stereotype);
                                        }
                                        // If it's a property and the stereo
                                        // type is set then we know that it is
                                        // an auto-case identifier, and we need
                                        // to set the correct restrictions
                                        if (object instanceof Property) {
                                            Property prop = (Property) object;
                                            if (BOMGlobalDataUtils
                                                    .isAutoCID(prop)) {
                                                BOMGlobalDataUtils
                                                        .setAutoCaseIdentifierRestrictions(prop,
                                                                XpdResourcesPlugin
                                                                        .getDefault()
                                                                        .getEditingDomain()
                                                                        .getResourceSet());
                                            }

                                            // Make sure first character is
                                            // lower case
                                            String stereoName =
                                                    stereotype.getName();
                                            String defaultName =
                                                    stereoName
                                                            .replace(stereoName
                                                                    .charAt(0),
                                                                    Character
                                                                            .toLowerCase(stereoName
                                                                                    .charAt(0)));

                                            defaultName =
                                                    UML2ModelUtil
                                                            .createUniquePropertyName(prop
                                                                    .getClass_(),
                                                                    defaultName);
                                            // "autoCaseIdentifier");
                                            prop.setName(defaultName);
                                            // Set the display label
                                            PrimitivesUtil
                                                    .setDisplayLabel(prop,
                                                            defaultName,
                                                            false);
                                        }

                                        // If we have a Case or Global class
                                        // then we need to check to ensure that
                                        // we set the correct default name
                                        if (object instanceof Class) {
                                            Class theClass = ((Class) object);
                                            String defaultName =
                                                    UML2ModelUtil
                                                            .createUniqueElementName(theClass
                                                                    .getPackage(),
                                                                    stereotype
                                                                            .getLabel());
                                            theClass.setName(defaultName);
                                            // Set the display label
                                            PrimitivesUtil
                                                    .setDisplayLabel(theClass,
                                                            defaultName,
                                                            false);
                                        }
                                    }
                                }
                            }
                        }
                    };

            // Build up a compound command with the create and then the
            // stereotype added
            CompoundCommand cmd = new CompoundCommand();
            cmd.append(createCommand);
            cmd.append(stereoCommand);

            if (!(createCommand instanceof ActionDelegateCommandWrapper)) {
                // createCommand should always be of this type, otherwise menu
                // items will not get icons or text, this check is just to make
                // sure, and have a fallback
                return cmd;
            }
            // Need to create the wrapper, this is because it is the Command
            // wrapper that assigned both the Image Icon and the Text Label
            ActionDelegateCommandWrapper singleCmdWrapper =
                    (ActionDelegateCommandWrapper) createCommand;
            ActionDelegateCommandWrapper wrapper =
                    new ActionDelegateCommandWrapper(cmd);
            wrapper.setImage(singleCmdWrapper.getImage());
            wrapper.setText(singleCmdWrapper.getText());

            return wrapper;
        }

        @Override
        public void run() {
            StructuredViewer viewer = getActionSite().getStructuredViewer();
            IStructuredSelection oldSel =
                    (IStructuredSelection) viewer.getSelection();
            super.run();

            // Select the first new element in the project explorer
            Collection<?> result = command.getResult();
            if (result != null && !result.isEmpty()) {
                Object newElement = result.iterator().next();
                if (newElement != null) {
                    IStructuredSelection newSelection =
                            new StructuredSelection(newElement);
                    if (oldSel != null && !oldSel.isEmpty()) {
                        viewer.refresh(oldSel.getFirstElement());
                    }
                    viewer.setSelection(newSelection, true);
                }
            }
        }
    }

}
