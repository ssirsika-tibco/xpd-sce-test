package com.tibco.xpd.xpdl2.resolvers;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.util.LocalPackageProcessInterfaceUtil;

/**
 * Finds references to type declarations.
 * <p>
 * 
 * @author aallway
 * 
 */
public class Xpdl2TypeDeclarationReferenceResolver {

    /**
     * Finds references to data fields / formal parameters / other type
     * declarations.
     * <p>
     */
    public static Set<EObject> getReferencingObjects(
            TypeDeclaration typeDeclaration) {
        Set<EObject> refs = new HashSet<EObject>();

        if (typeDeclaration != null) {
            EObject container = typeDeclaration.eContainer();

            if (container instanceof Package) {
                addPackageReferences((Package) container, refs, typeDeclaration);

                // Add refs from stuff under process interfaces
                ProcessInterfaces procIfcs = getProcessInterfaces((Package)container);
                if (procIfcs != null) {
                    for (Iterator iter =
                            procIfcs.getProcessInterface().iterator(); iter
                            .hasNext();) {
                        ProcessInterface processIfc = (ProcessInterface) iter.next();
    
                        addProcessInterfaceReferences(processIfc, refs, typeDeclaration);
                    }
                }
    
                // Add refs from stuff under process
                for (Iterator iter =
                        ((Package) container).getProcesses().iterator(); iter
                        .hasNext();) {
                    Process process = (Process) iter.next();

                    addProcessReferences(process, refs, typeDeclaration);
                }
            }
        }

        return refs;
    }

    private static void addProcessInterfaceReferences(
            ProcessInterface processIfc, Set<EObject> refs,
            TypeDeclaration typeDeclaration) {

        // Get references from process interface formal parameters
        for (Iterator iter = processIfc.getFormalParameters().iterator(); iter
                .hasNext();) {
            FormalParameter fp = (FormalParameter) iter.next();

            if (fp.getDataType() instanceof DeclaredType) {
                String typeId =
                        ((DeclaredType) fp.getDataType())
                                .getTypeDeclarationId();
                if (typeId != null && typeId.equals(typeDeclaration.getId())) {
                    refs.add(fp);
                }
            }
        }
        
    }

    private static void addPackageReferences(Package pkg, Set<EObject> refs,
            TypeDeclaration typeDeclaration) {
        // Get references from other type declarations.
        for (Iterator iter = pkg.getTypeDeclarations().iterator(); iter
                .hasNext();) {
            TypeDeclaration td = (TypeDeclaration) iter.next();

            DeclaredType declType = td.getDeclaredType();

            if (declType != null) {
                String typeId = declType.getTypeDeclarationId();
                if (typeId != null && typeId.equals(typeDeclaration.getId())) {
                    refs.add(td);
                }

            }
        }

        // Get references from package data fields
        for (Iterator iter = pkg.getDataFields().iterator(); iter.hasNext();) {
            DataField df = (DataField) iter.next();

            if (df.getDataType() instanceof DeclaredType) {
                String typeId =
                        ((DeclaredType) df.getDataType())
                                .getTypeDeclarationId();
                if (typeId != null && typeId.equals(typeDeclaration.getId())) {
                    refs.add(df);
                }
            }
        }

    }

    private static void addProcessReferences(Process process,
            Set<EObject> refs, TypeDeclaration typeDeclaration) {

        // Get references from process formal parameters
        for (Iterator iter = process.getFormalParameters().iterator(); iter
                .hasNext();) {
            FormalParameter fp = (FormalParameter) iter.next();

            if (fp.getDataType() instanceof DeclaredType) {
                String typeId =
                        ((DeclaredType) fp.getDataType())
                                .getTypeDeclarationId();
                if (typeId != null && typeId.equals(typeDeclaration.getId())) {
                    refs.add(fp);
                }
            }
        }

        // Get references from process data fields
        for (Iterator iter = process.getDataFields().iterator(); iter.hasNext();) {
            DataField df = (DataField) iter.next();

            if (df.getDataType() instanceof DeclaredType) {
                String typeId =
                        ((DeclaredType) df.getDataType())
                                .getTypeDeclarationId();
                if (typeId != null && typeId.equals(typeDeclaration.getId())) {
                    refs.add(df);
                }
            }
        }

        return;
    }
    
    /**
     * Retrieves Process Interfaces for a given package
     * 
     * @param pkg
     * @return
     */
    private static ProcessInterfaces getProcessInterfaces(Package pkg) {
        ProcessInterfaces processInterfaces = null;
        if (pkg != null
                && pkg.getOtherElement(XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ProcessInterfaces().getName()) != null) {
            processInterfaces = (ProcessInterfaces) pkg
                    .getOtherElement(XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_ProcessInterfaces().getName());
        }
        return processInterfaces;
    }

}
