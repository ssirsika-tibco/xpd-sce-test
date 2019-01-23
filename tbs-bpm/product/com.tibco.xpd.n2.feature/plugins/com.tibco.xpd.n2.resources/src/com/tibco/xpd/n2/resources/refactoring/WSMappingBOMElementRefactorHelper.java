/*
 * Copyright (c) TIBCO Software Inc. 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.n2.resources.refactoring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.NamedElement;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * Helper class for the Refactoring of WebService Data Mappings
 * 
 * @author mtorres
 * 
 */
public class WSMappingBOMElementRefactorHelper {

    /**
     * Returns the ws activities that can possibly reference the
     * modified Object
     * 
     * @param eObject
     * @return
     */
    public static List<EObject> getReferencingWSContainerCandidates(
            EObject eObject) {
        if (eObject != null) {
            IProject project = WorkingCopyUtil.getProjectFor(eObject);
            if (project != null) {
                IPath relativePath =
                        SpecialFolderUtil.getSpecialFolderRelativePath(eObject);
                Set<String> referencingProcesses =
                        ProcessUIUtil.queryReferencingXpdls(project,
                                relativePath.toString(),
                                false);
                if (referencingProcesses != null) {
                    List<EObject> referencingMappingContainerCandidates =
                            new ArrayList<EObject>();
                    for (String referencingProcess : referencingProcesses) {
                        URI uri = URI.createURI(referencingProcess);
                        String xpdlPath = null;
                        if (uri != null) {
                            if (uri.isFile()) {
                                xpdlPath = uri.toFileString();
                            } else {
                                xpdlPath = uri.toPlatformString(true);
                            }
                        }
                        if (uri != null) {
                            IFile file =
                                    ResourcesPlugin.getWorkspace().getRoot()
                                            .getFile(new Path(xpdlPath));
                            if (file != null && file.exists()) {
                                WorkingCopy workingCopy =
                                        WorkingCopyUtil.getWorkingCopy(file);
                                if (workingCopy != null) {
                                    EObject rootElement =
                                            workingCopy.getRootElement();
                                    if (rootElement instanceof Package) {
                                        Package xpdlPackage =
                                                (Package) rootElement;
                                        EList<Process> processes =
                                                xpdlPackage.getProcesses();
                                        if (processes != null
                                                && !processes.isEmpty()) {
                                            for (Process process : processes) {
                                                if (process != null) {
                                                    List<Activity> activities =
                                                            ProcessScriptUtil
                                                                    .getWebServiceActivities(process);
                                                    if (activities != null
                                                            && !activities
                                                                    .isEmpty()) {
                                                        referencingMappingContainerCandidates
                                                                .addAll(activities);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return referencingMappingContainerCandidates;
                }
            }
        }
        return Collections.emptyList();
    }
    
    
    /**
     * Returns if the data mapping is affected by the refactoring process
     * 
     * @param dataMapping
     * @param element
     * @return
     */
    public static boolean isAffectedMapping(DataMapping dataMapping, EObject element) {
        if (dataMapping != null && element != null) {
            Expression actual = dataMapping.getActual();
            if (actual != null) {
                String actualText = actual.getText();
                String formalText = dataMapping.getFormal();
                if (actualText != null && formalText != null) {
                    ConceptPath resolveActualConceptPath =
                            ConceptUtil
                                    .resolveConceptPath(Xpdl2ModelUtil
                                            .getParentActivity(dataMapping),
                                            actualText);
                    ConceptPath resolveFormalConceptPath =
                            ConceptUtil
                                    .resolveConceptPath(Xpdl2ModelUtil
                                            .getParentActivity(dataMapping),
                                            formalText);
                    if (resolveActualConceptPath != null
                            && isAffectedConceptPath(resolveActualConceptPath,
                                    element)) {
                        return true;
                    }
                    if (resolveFormalConceptPath != null
                            && isAffectedConceptPath(resolveFormalConceptPath,
                                    element)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns if the concept path is affected by the refactoring process
     * 
     * @param conceptPath
     * @param element
     * @return
     */
    private static boolean isAffectedConceptPath(ConceptPath conceptPath,
            EObject element) {
        if (conceptPath != null && element != null) {
            List<ConceptPath> parentList = new ArrayList<ConceptPath>();
            List<ConceptPath> conceptParentHierarchy =
                    ConceptUtil.getConceptParentHierarchy(conceptPath,
                            parentList);
            if (conceptParentHierarchy != null
                    && !conceptParentHierarchy.isEmpty()) {
                for (ConceptPath hierarchyConceptPath : conceptParentHierarchy) {
                    if (hierarchyConceptPath != null
                            && hierarchyConceptPath.getItem() != null
                            && hierarchyConceptPath.getItem().equals(element)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * Performs the Data Mapping Refactoring 
     * 
     * @param newName
     * @param dataMapping
     * @param element
     * @return
     */
    public static DataMappingChange performDataMappingRefactoring(
            String newName, DataMapping dataMapping, EObject element) {
        String newActual = null;
        String newFormal = null;
        if (newName != null && dataMapping != null && element != null) {
            Expression actual = dataMapping.getActual();
            if (actual != null) {
                String actualText = actual.getText();
                String formalText = dataMapping.getFormal();
                if (actualText != null && formalText != null) {
                    ConceptPath resolveActualConceptPath =
                            ConceptUtil
                                    .resolveConceptPath(Xpdl2ModelUtil
                                            .getParentActivity(dataMapping),
                                            actualText);
                    ConceptPath resolveFormalConceptPath =
                            ConceptUtil
                                    .resolveConceptPath(Xpdl2ModelUtil
                                            .getParentActivity(dataMapping),
                                            formalText);
                    if (resolveActualConceptPath != null
                            && isAffectedConceptPath(resolveActualConceptPath,
                                    element)) {
                        newActual = refactorConceptPath(newName, resolveActualConceptPath, element);
                    }
                    if (resolveFormalConceptPath != null
                            && isAffectedConceptPath(resolveFormalConceptPath,
                                    element)) {
                        newFormal = refactorConceptPath(newName, resolveFormalConceptPath, element);
                    }
                }
            }
        }
        return new WSMappingBOMElementRefactorHelper().new DataMappingChange(
                newActual, newFormal);
    }
    
    /**
     * Refactors the Concept Path
     * 
     * @param newName
     * @param conceptPath
     * @param element
     * @return
     */
    private static String refactorConceptPath(String newName,
            ConceptPath conceptPath, EObject element) {
        if (conceptPath != null && conceptPath.getItem() != null
                && element instanceof NamedElement) {
            String oldName = ((NamedElement) element).getName();
            String path = conceptPath.getPath();
            if (conceptPath.getItem().equals(element)) {
                // The renamed element is the concept path
                int lastIndexOf = path.lastIndexOf(oldName);
                if (lastIndexOf != -1) {
                    String newBeginingPath =
                            path.substring(0, lastIndexOf) + newName;
                    String oldBeginingPath =
                            path.substring(0, lastIndexOf + oldName.length());
                    return path.replace(oldBeginingPath, newBeginingPath);
                }
            } else {
                // Have to find the element in parents
                ConceptPath parent = conceptPath.getParent();
                while (parent != null) {
                    if (parent.getItem() != null
                            && parent.getItem().equals(element)) {
                        String parentPath = parent.getPath();
                        if (parentPath != null) {
                            int lastIndexOf = parentPath.lastIndexOf(oldName);
                            if (lastIndexOf != -1) {
                                String newBeginingParentPath =
                                        parentPath.substring(0, lastIndexOf)
                                                + newName;
                                String oldBeginingParentPath =
                                        parentPath.substring(0, lastIndexOf + oldName.length());
                                if (parentPath != null) {
                                    return path.replaceFirst(oldBeginingParentPath,
                                            newBeginingParentPath);
                                }
                            }
                        }
                    } else {
                        parent = parent.getParent();
                    }
                }
            }
        }
        return null;
    }
    
    
    public class DataMappingChange {
        String actual;

        String formal;

        public DataMappingChange(String newActual, String newFormal) {
            this.actual = newActual;
            this.formal = newFormal;
        }

        public String getActual() {
            return actual;
        }

        public String getFormal() {
            return formal;
        }
    }
}
