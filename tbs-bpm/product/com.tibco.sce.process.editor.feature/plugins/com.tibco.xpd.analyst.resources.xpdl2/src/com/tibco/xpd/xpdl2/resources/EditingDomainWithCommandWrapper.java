/**
 * EditingDomainWithCommandWrapper.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2008
 */
package com.tibco.xpd.xpdl2.resources;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;

/**
 * EditingDomainWithCommandWrapper
 * <p>
 * Editing domain that allows additional commands to be added to Add/Set/Remove
 * commands via the com.tibco.xpd.analyst.resources.xpdl2.genericCommandWrapper
 * extension point.
 * </p>
 * <p>
 * THIS IS DIFFERENT from the
 * com.tibco.xpd.xpdl2.edit.add/set/deleteContribution extension point in that
 * individual xpdl2 EMF object ItemProviders DO NOT have to have their
 * createAddCommand/createRemoveCommand/createSetCommand methods overridden in
 * order for it to work.
 * </p>
 * <p>
 * However, care should be taken in contributing to the genericCommandWrapper
 * extension point to be as efficient as possible in deciding whether to wrap up
 * a command. This is because you wrapper will be called for ANY add/set/remove
 * on ANY EObject.
 * 
 * @deprecated This class is no longer used since moving to Transactional
 *             Editing Domains
 */
public class EditingDomainWithCommandWrapper extends
        EditingDomainWithSystemClipboard {

    private static final String EXTPOINT_ID = "genericCommandWrapper"; //$NON-NLS-1$

    private static final String COMMANDWRAPPER_EL = "CommandWrapper"; //$NON-NLS-1$

    private static final String CLASS_ATTR = "class"; //$NON-NLS-1$

    private List<IXpdl2CommandWrapper> contributions =
            new ArrayList<IXpdl2CommandWrapper>();

    public EditingDomainWithCommandWrapper(AdapterFactory factory,
            CommandStack stack) {
        super(factory, stack);

        loadContributions();
    }

    @Override
    public Command createCommand(Class commandClass,
            CommandParameter commandParameter) {

        Command originalCommand =
                super.createCommand(commandClass, commandParameter);
        Command finalCommand = originalCommand;

        if (commandParameter.getEOwner() != null) {
            if (commandClass == AddCommand.class) {
                for (IXpdl2CommandWrapper wrapper : contributions) {
                    Command wrappedCommand =
                            wrapper.wrapAddCommand(finalCommand,
                                    this,
                                    commandParameter.getEOwner(),
                                    commandParameter.getEStructuralFeature(),
                                    commandParameter.getCollection(),
                                    commandParameter.getIndex());
                    if (wrappedCommand != null
                            && wrappedCommand != finalCommand) {
                        finalCommand = wrappedCommand;
                    }
                }

            } else if (commandClass == SetCommand.class) {
                for (IXpdl2CommandWrapper wrapper : contributions) {
                    Command wrappedCommand =
                            wrapper.wrapSetCommand(finalCommand,
                                    this,
                                    commandParameter.getEOwner(),
                                    commandParameter.getEStructuralFeature(),
                                    commandParameter.getValue(),
                                    commandParameter.getIndex());
                    if (wrappedCommand != null
                            && wrappedCommand != finalCommand) {
                        finalCommand = wrappedCommand;
                    }
                }

            } else if (commandClass == RemoveCommand.class) {
                for (IXpdl2CommandWrapper wrapper : contributions) {
                    Command wrappedCommand =
                            wrapper.wrapDeleteCommand(finalCommand,
                                    this,
                                    commandParameter.getEOwner(),
                                    commandParameter.getEStructuralFeature(),
                                    commandParameter.getCollection());
                    if (wrappedCommand != null
                            && wrappedCommand != finalCommand) {
                        finalCommand = wrappedCommand;
                    }
                }

            }

        }

        return finalCommand;
    }

    private void loadContributions() {

        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(Xpdl2ResourcesPlugin.PLUGIN_ID,
                                EXTPOINT_ID);
        if (point != null) {
            IExtension[] extensions = point.getExtensions();

            if (extensions != null) {
                for (IExtension ext : extensions) {
                    IConfigurationElement[] elements =
                            ext.getConfigurationElements();

                    if (elements != null) {
                        for (IConfigurationElement elem : elements) {
                            if (COMMANDWRAPPER_EL.equals(elem.getName())) {
                                try {
                                    Object clazz =
                                            elem
                                                    .createExecutableExtension(CLASS_ATTR);
                                    if (clazz instanceof IXpdl2CommandWrapper) {
                                        contributions
                                                .add((IXpdl2CommandWrapper) clazz);
                                    }
                                } catch (CoreException e) {
                                }
                            }
                        }
                    }
                }
            }
        }
        return;
    }

}
