/**
 * 
 */
package com.tibco.xpd.processeditor.xpdl2.preCommit;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.AbstractProcessPreCommitContributor;

/**
 * The pre-commit contribution to add the attribute
 * <code>xpdExt:SourcePrimitiveProperty</code> or
 * <code>xpdExt:TargetPrimitiveProperty</code>. Only either of these attributes
 * will be added to the
 * 
 * @author rsomayaj
 * @since 3.3 (18 Nov 2010)
 */
public class DatamappingPrecommitContribution extends
        AbstractProcessPreCommitContributor {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.xpdl2.resources.IProcessPreCommitContributor#contributeCommand
     * (org.eclipse.emf.transaction.ResourceSetChangeEvent,
     * java.util.Collection)
     */
    @Override
    public Command contributeCommand(ResourceSetChangeEvent event,
            Collection<ENotificationImpl> notifications)
            throws RollbackException {
        CompoundCommand cmd = null;

        Set<Package> packagesToCheck = new HashSet<Package>();

        for (ENotificationImpl notification : notifications) {
            EObject o = getTypedAncestor(notification, Package.class);
            if (o instanceof Package) {
                packagesToCheck.add((Package) o);
            }
        }

        TransactionalEditingDomain editingDomain = event.getEditingDomain();

        for (Package pkg : packagesToCheck) {
            for (Process process : pkg.getProcesses()) {

                // Validate if the targetType or sourcetype of the datamappings
                // are correct

                MappingsProcessDataPrimitiveTypeCommand mappingsProcessDataPrimitiveTypeCommand =
                        MappingsProcessDataPrimitiveTypeCommand
                                .create(editingDomain, process);
                cmd =
                        appendOrCreateCommand(cmd,
                                mappingsProcessDataPrimitiveTypeCommand);

            }
        }

        return cmd;
    }

}
