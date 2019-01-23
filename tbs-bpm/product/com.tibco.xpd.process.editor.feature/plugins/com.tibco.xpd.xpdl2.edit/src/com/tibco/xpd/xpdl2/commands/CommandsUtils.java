/**
 * 
 */
package com.tibco.xpd.xpdl2.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * Set of usefull static methods related to Commands
 * 
 * @author wzurek
 */
public class CommandsUtils {



    /**
     * 
     * 
     * @param oryginalCommand
     * @param domain
     * @param owner
     * @param feature
     * @param collection
     * @param index
     * @return
     */
    public static Command checkExternalAddWrappers(Command oryginalCommand,
            EditingDomain domain, EObject owner, EStructuralFeature feature,
            Collection collection, int index) {
        Command cmd = oryginalCommand;
        AddCommandWrapper[] wrps = getWrappers(owner.getClass());
        for (int i = 0; i < wrps.length; i++) {
            cmd = wrps[i].createAddCommand(cmd, domain, owner, feature,
                    collection, index);
        }
        return cmd;
    }

    private static void addInterfaces(Set names, Class interf) {
        if (!names.contains(interf.getName())) {
            names.add(interf.getName());
            Class[] is = interf.getInterfaces();
            for (int i = 0; i < is.length; i++) {
                addInterfaces(names, is[i]);
            }
        }
    }

    private static AddCommandWrapper[] getWrappers(Class baseClass) {

        Class tmp = baseClass;
        HashSet mp = new HashSet();
        while (tmp != null) {
            mp.add(tmp.getName());
            Class[] is = tmp.getInterfaces();
            for (int i = 0; i < is.length; i++) {
                addInterfaces(mp, is[i]);
            }
            tmp = tmp.getSuperclass();
        }

        IExtensionPoint point = Platform.getExtensionRegistry()
                .getExtensionPoint("com.tibco.xpd.xpdl2.edit.addContribution"); //$NON-NLS-1$
        IExtension[] extensions = point.getExtensions();
        List result = new ArrayList(1);
        for (int i = 0; i < extensions.length; i++) {
            IConfigurationElement[] config = extensions[i]
                    .getConfigurationElements();
            for (int j = 0; j < config.length; j++) {
                if ("addContribution".equals(config[j].getName())) { //$NON-NLS-1$
                    try {
                        String str = config[j].getAttribute("ownerClass"); //$NON-NLS-1$
                        if (mp.contains(str)) {
                            Object object = config[j]
                                    .createExecutableExtension("addCommandWrapper"); //$NON-NLS-1$
                            result.add(object);
                        }
                    } catch (CoreException e1) {
                        // Can fail if initialising whilst eclipse is shutting
                        // down.
                        // No need to do anything here.
                    }
                }
            }
        }
        return (AddCommandWrapper[]) result
                .toArray(new AddCommandWrapper[result.size()]);
    }
    
    public static Command checkExternalSetWrappers(Command originalCommand,
            EditingDomain domain, EObject owner, EStructuralFeature feature,
            Object value, int index) {
        Command cmd = originalCommand;
        SetCommandWrapper[] wrps = getSetCommandWrappers(owner.getClass());
        for (int i = 0; i < wrps.length; i++) {
            cmd = wrps[i].createSetCommand(cmd, domain, owner, feature, value,
                    index);
        }
        return cmd;
    }

    public static Command checkExternalDeleteWrappers(Command originalCommand,
            EditingDomain domain, EObject owner, EStructuralFeature feature,
            Collection collection) {
        Command cmd = originalCommand;
        DeleteCommandWrapper[] wrps = getDeleteCommandWrappers(owner.getClass());
        for (int i = 0; i < wrps.length; i++) {
            cmd = wrps[i].createDeleteCommand(cmd, domain, owner, feature,
                    collection);
        }
        return cmd;
    }

    private static SetCommandWrapper[] getSetCommandWrappers(Class baseClass) {

        Class tmp = baseClass;
        HashSet mp = new HashSet();
        while (tmp != null) {
            mp.add(tmp.getName());
            Class[] is = tmp.getInterfaces();
            for (int i = 0; i < is.length; i++) {
                addInterfaces(mp, is[i]);
            }
            tmp = tmp.getSuperclass();
        }

        IExtensionPoint point = Platform.getExtensionRegistry()
                .getExtensionPoint("com.tibco.xpd.xpdl2.edit.setContribution"); //$NON-NLS-1$
        IExtension[] extensions = point.getExtensions();
        List result = new ArrayList(1);
        for (int i = 0; i < extensions.length; i++) {
            IConfigurationElement[] config = extensions[i]
                    .getConfigurationElements();
            for (int j = 0; j < config.length; j++) {
                if ("setContribution".equals(config[j].getName())) { //$NON-NLS-1$
                    try {
                        String str = config[j].getAttribute("ownerClass"); //$NON-NLS-1$
                        if (mp.contains(str)) {
                            Object object = config[j]
                                    .createExecutableExtension("setCommandWrapper"); //$NON-NLS-1$
                            result.add(object);
                        }
                    } catch (CoreException e1) {
                        // Can fail if initialising whilst eclipse is shutting
                        // down.
                        // No need to do anything here.
                    }
                }
            }
        }
        return (SetCommandWrapper[]) result
                .toArray(new SetCommandWrapper[result.size()]);
    }

    private static DeleteCommandWrapper[] getDeleteCommandWrappers(
            Class baseClass) {

        Class tmp = baseClass;
        HashSet mp = new HashSet();
        while (tmp != null) {
            mp.add(tmp.getName());
            Class[] is = tmp.getInterfaces();
            for (int i = 0; i < is.length; i++) {
                addInterfaces(mp, is[i]);
            }
            tmp = tmp.getSuperclass();
        }

        IExtensionPoint point = Platform.getExtensionRegistry()
                .getExtensionPoint(
                        "com.tibco.xpd.xpdl2.edit.deleteContribution"); //$NON-NLS-1$
        IExtension[] extensions = point.getExtensions();
        List result = new ArrayList(1);
        for (int i = 0; i < extensions.length; i++) {
            IConfigurationElement[] config = extensions[i]
                    .getConfigurationElements();
            for (int j = 0; j < config.length; j++) {
                if ("deleteContribution".equals(config[j].getName())) { //$NON-NLS-1$
                    try {
                        String str = config[j].getAttribute("ownerClass"); //$NON-NLS-1$
                        if (mp.contains(str)) {
                            Object object = config[j]
                                    .createExecutableExtension("deleteCommandWrapper"); //$NON-NLS-1$
                            result.add(object);
                        }
                    } catch (CoreException e1) {
                        // Can fail if initialising whilst eclipse is shutting
                        // down. No need to do anything here.
                    }
                }
            }
        }
        return (DeleteCommandWrapper[]) result
                .toArray(new DeleteCommandWrapper[result.size()]);
    }
}
