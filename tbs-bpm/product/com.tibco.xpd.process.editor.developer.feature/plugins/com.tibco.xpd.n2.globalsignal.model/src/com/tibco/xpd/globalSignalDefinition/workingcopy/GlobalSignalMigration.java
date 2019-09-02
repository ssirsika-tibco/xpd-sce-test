/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.globalSignalDefinition.workingcopy;

import java.util.Iterator;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Member;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.Scale;
import com.tibco.xpd.xpdl2.Xpdl2Factory;

/**
 * Change UML version in all fields to UML 5.0.0 http://www.eclipse.org/uml2/5.0.0/UML
 * 
 * Also change all integer fields to fixed point number fields with 0 decimals
 *
 * @author pwatson
 * @since 30 Aug 2019
 */
public class GlobalSignalMigration {
    private static final String UML_NAMESPACE = "http://www.eclipse.org/uml2/5.0.0/UML"; //$NON-NLS-1$

    /**
     * The entry point for testing whether a Global Signal Definition documentis to be migrated.
     * 
     * @param aFileResource
     *            the file resource from which the GSD was loaded.
     * @param aDocResource
     *            the Global Signal Definition document.
     * @return <code>true</code> if migration is required, <code>false</code> otherwise.
     */
    public boolean migrationRequired(IResource aFileResource, Resource aDocResource) {
        // look for first global signal that requires migration
        Iterator<EObject> allContents = aDocResource.getAllContents();
        while (allContents.hasNext()) {
            EObject next = allContents.next();
            if ((next instanceof GlobalSignalDefinitions) && (migrationRequired((GlobalSignalDefinitions) next))) {
                return true;
            }
        }

        return false;
    }

    /**
     * The entry point for migrating a Global Signal Definition document. Assumes that the document has already been
     * passed to {@link #migrationRequired(IResource, Resource)}, and that it returned <code>true</code>.
     * 
     * @param aFileResource
     *            the file resource from which the GSD was loaded.
     * @param aDocResource
     *            the Global Signal Definition document to be migrated.
     */
    public void migrate(IResource aFileResource, Resource aDocResource) {
        aDocResource.getAllContents().forEachRemaining(gsd -> {
            if (gsd instanceof GlobalSignalDefinitions) {
                migrate((GlobalSignalDefinitions) gsd);
            }
        });
    }

    /**
     * Tests whether any of the elements within the given GlobalSignalDefinitions require migration.
     * 
     * @param aDefinitions
     *            the GlobalSignalDefinitions to be tested.
     * @return <code>true</code> if migration is required, <code>false</code> otherwise.
     */
    private boolean migrationRequired(GlobalSignalDefinitions aDefinitions) {
        return aDefinitions.getGlobalSignals().stream().filter(signal -> migrationRequired(signal))
                .findFirst().isPresent();
    }

    /**
     * Applies all migration rules to the elements of the given GlobalSignalDefinitions.
     * 
     * @param aDefinitions
     *            the GlobalSignalDefinitions to be migrated.
     */
    private void migrate(GlobalSignalDefinitions aDefinitions) {
        aDefinitions.getGlobalSignals().forEach(signal -> migrate(signal));
    }

    /**
     * Tests whether any of the elements within the given GlobalSignal require migration.
     * 
     * @param aSignal
     *            the GlobalSignal to be tested.
     * @return <code>true</code> if migration is required, <code>false</code> otherwise.
     */
    private boolean migrationRequired(GlobalSignal aSignal) {
        return aSignal.getPayloadDataFields().stream().filter(field -> migrationRequired(field)).findFirst()
                .isPresent();
    }

    /**
     * Applies all migration rules to the elements of the given GlobalSignal.
     * 
     * @param aSignal
     *            the GlobalSignal to be migrated.
     */
    private void migrate(GlobalSignal aSignal) {
        aSignal.getPayloadDataFields().forEach(field -> migrate(field));
    }

    /**
     * Tests whether any of the elements within the given PayloadDataField require migration.
     * 
     * @param aPayloadDataField
     *            the PayloadDataField to be tested.
     * @return <code>true</code> if migration is required, <code>false</code> otherwise.
     */
    private boolean migrationRequired(PayloadDataField aPayloadDataField) {
        DataType dataType = aPayloadDataField.getDataType();
        if (dataType == null) {
            return false;
        }

        // test the namespace of all member external references
        if (dataType instanceof RecordType) {
            RecordType recordType = (RecordType) dataType;
            for (Member member : recordType.getMember()) {
                ExternalReference externalReference = member.getExternalReference();
                if ((externalReference != null)
                        && (!GlobalSignalMigration.UML_NAMESPACE.equals(externalReference.getNamespace()))) {
                    return true;
                }
            }
        }

        // test the namespace of all external references
        else if (dataType instanceof ExternalReference) {
            return !GlobalSignalMigration.UML_NAMESPACE.equals(((ExternalReference) dataType).getNamespace());
        }

        else if (dataType instanceof BasicType) {
            // integer types to be migrated to fixed-point
            BasicTypeType type = ((BasicType) dataType).getType();
            return (type == BasicTypeType.INTEGER_LITERAL);
        }

        return false;
    }

    /**
     * Applies all migration rules to the elements of the given PayloadDataField.
     * 
     * @param aPayloadDataField
     *            the PayloadDataField to be migrated.
     */
    private void migrate(PayloadDataField aPayloadDataField) {
        DataType dataType = aPayloadDataField.getDataType();
        if (dataType == null) {
            return;
        }

        // set the namespace of all member external references to UML 5.0.0
        if (dataType instanceof RecordType) {
            RecordType recordType = (RecordType) dataType;
            for (Member member : recordType.getMember()) {
                ExternalReference externalReference = member.getExternalReference();
                if (externalReference != null) {
                    externalReference.setNamespace(GlobalSignalMigration.UML_NAMESPACE);
                }
            }
        }

        // set the namespace of all external references to UML 5.0.0
        else if (dataType instanceof ExternalReference) {
            ((ExternalReference) dataType).setNamespace(GlobalSignalMigration.UML_NAMESPACE);
        }

        // set the type of all integer types to fixed-float with 0 dec-places
        else if (dataType instanceof BasicType) {
            // integer types to be migrated to fixed-point
            BasicTypeType type = ((BasicType) dataType).getType();
            if (type == BasicTypeType.INTEGER_LITERAL) {
                Scale scale = Xpdl2Factory.eINSTANCE.createScale();
                scale.setValue((short) 0);

                ((BasicType) dataType).setType(BasicTypeType.FLOAT_LITERAL);
                ((BasicType) dataType).setScale(scale);
            }
        }
    }
}
