package com.tibco.xpd.processeditor.xpdl2.properties.advanced;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.advanced.AdvancedPropertiesExtPointHelper.ContributionsForDestinationGroup;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.resources.IPreCommandExecutionWrapper;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * AdvancedPropertyCommandWrapper
 * <p>
 * This is a pre Execution command wrapper contributed to
 * preCommandExecutionWrapper that allows Advanced proeprties contributors the
 * opportuinity to add/delete advanced properties fro the model when they become
 * applicable / no longer applicable.
 * 
 */
public class AdvancedPropertyCommandWrapper implements
        IPreCommandExecutionWrapper {

    public Command wrapCommandBeforeExecution(Xpdl2WorkingCopyImpl workingCopy,
            EditingDomain editingDomain, Command cmd) {
        // Always wrap the command to be executed. We will suss out what to do
        // after it is actually
        AdvPropPreExecutionCommand preExecCmd =
                new AdvPropPreExecutionCommand(editingDomain, workingCopy);
        preExecCmd.setLabel(cmd.getLabel());
        preExecCmd.append(cmd);

        return preExecCmd;
    }

    private NamedElement getNamedElementAncestor(EObject owner) {
        EObject parent = owner;

        while (parent != null && !(parent instanceof NamedElement)) {
            parent = parent.eContainer();
        }

        if (parent instanceof NamedElement) {
            return (NamedElement) parent;
        }
        return null;
    }

    /**
     * Command that when executed will append extra commands contributed by
     * advanced property contributors for any NamedElement object that's
     * affected by the commands that have been appended to it.
     * AdvPropPreExecutionCommand
     * 
     */
    private class AdvPropPreExecutionCommand extends CompoundCommand {
        private EditingDomain editingDomain;

        private Xpdl2WorkingCopyImpl workingCopy;

        public AdvPropPreExecutionCommand(EditingDomain editingDomain,
                Xpdl2WorkingCopyImpl workingCopy) {
            super();
            this.editingDomain = editingDomain;
            this.workingCopy = workingCopy;
        }

        private Package getPackage() {
            EObject root = workingCopy.getRootElement();

            if (root instanceof Package) {
                return (Package) root;
            } else {
                for (Iterator iterator = root.eContents().iterator(); iterator
                        .hasNext();) {
                    EObject obj = (EObject) iterator.next();

                    if (obj instanceof Package) {
                        return (Package) obj;
                    }
                }
            }
            return null;
        }

        @Override
        public void execute() {
            Package pkg = getPackage();

            // Execute the original command that would have been executed on the
            // cmd stack.
            super.execute();

            // Check the objects affected by the command, if any one of them is
            // an ExtendedAttribute with name = "Destination" then someone has
            // set a destintation environment ON (we don't care about them being
            // turned off because we don't remove advanced properties when that
            // happens).
            boolean destinationsAdded = false;

            Collection affectedObjs = this.getAffectedObjects();
            for (Object o : affectedObjs) {
                if (o instanceof ExtendedAttribute) {
                    ExtendedAttribute ext = (ExtendedAttribute) o;

                    if (DestinationUtil.DESTINATION_ATTRIBUTE
                            .equalsIgnoreCase(ext.getName())) {
                        destinationsAdded = true;
                    }
                }
            }

            //
            // If any destinations were added then need to give all Advanced
            // Property Contributors to set default values for properties of all
            // NamedElements - we do ALL in package because (a) they will be
            // added IF their particular dest env(s) are set and (b) their may
            // be Package level elements to assigned properties to.
            //
            if (destinationsAdded) {
                TreeIterator<Object> allContents =
                        EcoreUtil
                                .getAllContents(Collections.singletonList(pkg));
                for (Iterator iterator = allContents; iterator.hasNext();) {
                    Object eo = iterator.next();

                    if (eo instanceof NamedElement) {
                        this
                                .appendAndExecute(new SetDefaultOrRemoveAdvPropContributionCommand(
                                        editingDomain, (NamedElement) eo));
                    }
                }

            } else {
                // If we didn't add a dest env then we'll look at the objects
                // affected by the original command and allow the adv prop
                // contributor to set default values or remove properties that
                // don't apply any more..

                // We'll do this for any NamedElement ancestor that is still
                // part of the package (i.e. wasn't an affected object because
                // it had been removed.
                // 

                // System.out.println("WRAPPING COMMAND: " + this.getLabel());

                if (affectedObjs != null && !affectedObjs.isEmpty()) {
                    Set<NamedElement> affectedNamedElements =
                            new HashSet<NamedElement>();

                    for (Object o : affectedObjs) {
                        if (o instanceof EObject) {
                            // Find the NamedElement ancestor of the affected
                            // object
                            NamedElement el =
                                    getNamedElementAncestor((EObject) o);
                            if (el != null) {

                                // Only add to affected set of named elements if
                                // it is the process package itself OR it has
                                // our package in its ancestors (i.e. not
                                // deleted.
                                if (el == pkg
                                        || Xpdl2ModelUtil.getPackage(el) == pkg) {

                                    affectedNamedElements.add(el);

                                }

                                // Add parent of object if that is a named
                                // element - this is to cover the fact that
                                // affected objects does not include the parent
                                // of an object that's added to a sequence.
                                EObject parent = el.eContainer();
                                el = getNamedElementAncestor(parent);
                                if (el != null) {
                                    if (el == pkg
                                            || Xpdl2ModelUtil.getPackage(el) == pkg) {

                                        affectedNamedElements.add(el);
                                    }
                                }
                            }

                        }
                    }

                    // Go thru the set of affected named elements, giving adv
                    // prop contributors a chance to contribute.
                    for (NamedElement el : affectedNamedElements) {
                        // System.out.println(" Create Wrapping for: " + el);
                        this
                                .appendAndExecute(new SetDefaultOrRemoveAdvPropContributionCommand(
                                        editingDomain, el));
                    }
                }

                // System.out
                // .println(
                // "=======================================================\n\n"
                // );
            }

        }

    }

    /**
     * AdvancedPropertyCleanupCommand
     * <p>
     * This command waits until execution and then provides advanced property
     * contributors the chance to contribute commands to remove advanced
     * properties that no longer apply to a given object.
     * <p>
     * We do things this way because quite often EMF commands are created
     * without ever being executed (this is fairly standard practice in GEF/GMF
     * editors. Therefore we simply add an AdvancedPropertyCleanupCommand to any
     * Add/Set/Remove command and wait til execution to trawl thru advanced
     * properties contributions to check for anything extra to do.
     * 
     */
    private class SetDefaultOrRemoveAdvPropContributionCommand extends
            AbstractCommand {
        CompoundCommand delegateCmd = null;

        private EditingDomain editingDomain;

        private EObject owner;
        
        private boolean logging = false;

        public SetDefaultOrRemoveAdvPropContributionCommand(
                EditingDomain editingDomain, EObject owner) {
            super();
            this.editingDomain = editingDomain;
            this.owner = owner;

        }

        public void execute() {
            delegateCmd = null;

            try {
                //
                // Get a list of all the advanced properties contributions.
                List<AdvancedPropertyContribution> propertyContributions =
                        AdvancedPropertiesExtPointHelper.getDefault()
                                .getPropertyContributions();

                CompoundCommand cmd = new CompoundCommand();

                //
                // Go thru property contributions asking each whether the
                // property value is currently set in the model for the owner
                // that the original add/set/remove command was being performed
                // on.
                //
                for (AdvancedPropertyContribution contr : propertyContributions) {
                    if (contr.isSet(owner)) {
                        //
                        // If the property value is currently set in the model,
                        // ask the contributor if the property is still
                        // applicable to the given owner object in it's current
                        // state.
                        if (!contr.isApplicable(owner)) {
                            // 
                            // If the property is no longer applicable then get
                            // the command to remove it.
                            Command c =
                                    contr.getRemoveValueCommand(editingDomain,
                                            owner);
                            if (c != null) {
                                cmd.appendAndExecute(c);

                                if (logging) {
                                    System.out.println(" Removing Property '" //$NON-NLS-1$
                                            + contr.getName() + "' from: " //$NON-NLS-1$
                                            + owner);
                                }

                            }
                        }
                    }
                }

                //
                // Give the contributors the opportunity to set default values
                // for properties.
                // 

                // Get the enabled destinations for the context of the object in
                // question (if the owner is a child of process or interface
                // then the destinations for that will be used) otherwise (if
                // the owner object child of package but not process) then we
                // apply all destinations of all processes in package.
                Set<String> enabledDestinations =
                        DestinationUtil.getEnabledGlobalDestinationTypes(owner);

                // Always include "bpmn1" as a base destination environment for
                // all processes.
                enabledDestinations
                        .add(Xpdl2AdvancedPropertySourceProvider.BPMN_DESTINATION);

                // Go thru the contributions looking for any that apply to
                // enabled destinations.
                if (enabledDestinations.size() > 0) {
                    for (ContributionsForDestinationGroup destGroup : AdvancedPropertiesExtPointHelper
                            .getDefault().getDestinationCategories()) {

                        // Are any of the destinations for this destination
                        // group contribution enabled?
                        boolean enabled = false;
                        for (String dest : destGroup.destinations) {
                            if (enabledDestinations.contains(dest)) {
                                enabled = true;
                                break;
                            }
                        }

                        if (enabled) {
                            // If the property group is applicable to one of the
                            // enabled destinations then given the contribution
                            // chance to provide a default value for properties.
                            for (AdvancedPropertyContribution contr : destGroup.allDestinationGroupPropertyContributions) {
                                if (contr.isApplicable(owner)) {
                                    // The property is applicable to this
                                    // object.
                                    if (!contr.isSet(owner)) {
                                        // The property is not set, get the
                                        // setDefault value command.
                                        Object defaultValue =
                                                contr.getDefaultValue(owner);
                                        if (defaultValue != null) {
                                            Command c =
                                                    contr
                                                            .getSetValueCommand(editingDomain,
                                                                    owner,
                                                                    defaultValue);
                                            if (c != null) {
                                                cmd.appendAndExecute(c);

                                                if (logging) {
                                                    System.out
                                                            .println(" Adding Default For Property '" //$NON-NLS-1$
                                                                    + contr
                                                                            .getName()
                                                                    + "' to: " //$NON-NLS-1$
                                                                    + owner);
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        }
                    }
                }

                // If we have any extra contributed commands and executed then
                // setup the delegate commands for later redo/undo etc.
                if (!cmd.isEmpty()) {

                    delegateCmd = cmd;
                }

            } catch (Exception e) {
                throw new RuntimeException(e);

            } finally {
            }

            return;
        }

        public boolean canExecute() {
            return true;
        }

        public boolean canUndo() {
            return true;
        }

        public Command chain(Command command) {
            if (delegateCmd == null) {
                return command;
            }
            return delegateCmd.chain(command);
        }

        public void dispose() {
            if (delegateCmd != null) {
                delegateCmd.dispose();
            }
        }

        public Collection<?> getAffectedObjects() {
            if (delegateCmd == null) {
                return Collections.EMPTY_LIST;
            }
            return delegateCmd.getAffectedObjects();

        }

        public String getDescription() {
            if (delegateCmd == null) {
                return ""; //$NON-NLS-1$
            }
            return delegateCmd.getDescription();
        }

        public String getLabel() {
            if (delegateCmd == null) {
                return ""; //$NON-NLS-1$
            }
            return delegateCmd.getLabel();
        }

        public Collection<?> getResult() {
            if (delegateCmd == null) {
                return Collections.EMPTY_LIST;
            }
            return delegateCmd.getResult();
        }

        public void redo() {
            if (delegateCmd != null) {
                delegateCmd.redo();
            }
        }

        public void setDescription(String description) {
            if (delegateCmd != null) {
                delegateCmd.setDescription(description);
            }
        }

        public void setLabel(String label) {
            if (delegateCmd != null) {
                delegateCmd.setLabel(label);
            }
        }

        public void undo() {
            if (delegateCmd != null) {
                delegateCmd.undo();
            }
        }

    }

}
