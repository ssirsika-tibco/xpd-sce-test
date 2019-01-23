/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.n2.daa.test.junit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.xsd.ecore.XSDEcoreBuilder;

import com.tibco.xpd.n2.daa.test.Activator;

/**
 * Helper class to read the user configuration.
 * 
 * @author njpatel
 * 
 */
/* public */class JunitConfiguration {

    private static final URI SCHEMA_URI =
            URI.createPlatformPluginURI(Activator.PLUGIN_ID
                    + "/schema/studio_junits.xsd", true); //$NON-NLS-1$

    private String projectsLocation = null;

    private String daaOutputLocation = null;

    private List<ProjectType> includeProjects;

    public JunitConfiguration(URI configFileUri) throws CoreException,
            IOException {

        if (configFileUri != null) {
            XSDEcoreBuilder xsdEcoreBuilder = new XSDEcoreBuilder();
            ResourceSet resourceSet = new ResourceSetImpl();
            Collection<EObject> eCorePackages =
                    xsdEcoreBuilder.generate(SCHEMA_URI);

            for (EObject element : eCorePackages) {
                resourceSet.getPackageRegistry().put(((EPackage) element)
                        .getNsURI(),
                        element);
            }

            Resource resource = resourceSet.createResource(configFileUri);
            resource.load(Collections
                    .singletonMap(XMLResource.OPTION_EXTENDED_META_DATA,
                            Boolean.TRUE));
            readConfiguration(resource);
        }
    }

    public String getProjectsLocation() {
        return projectsLocation;
    }

    public String getDaaOutputLocation() {
        return daaOutputLocation;
    }

    public List<ProjectType> getIncludeProjects() {
        return includeProjects != null ? includeProjects
                : new ArrayList<ProjectType>(0);
    }

    /**
     * Read the configuration model.
     * 
     * @param resource
     */
    private void readConfiguration(Resource resource) {
        EList<EObject> contents = resource.getContents();
        if (!contents.isEmpty()) {
            EObject eo = contents.get(0);
            if (eo != null) {
                readConfigurationType(eo);
            }
        }
    }

    /**
     * Read the top ConfigurationType object.
     * 
     * @param eo
     */
    private void readConfigurationType(EObject eo) {
        Object value =
                eo.eGet(eo.eClass().getEStructuralFeature("configuration")); //$NON-NLS-1$
        if (value instanceof EObject) {
            value =
                    ((EObject) value).eGet(((EObject) value).eClass()
                            .getEStructuralFeature("projects")); //$NON-NLS-1$

            if (value instanceof EObject) {
                readProjectsSettings((EObject) value);
            }
        }
    }

    /**
     * Read the ProjectsSettings element.
     * 
     * @param eo
     */
    private void readProjectsSettings(EObject eo) {
        projectsLocation = getStringValue(eo, "location"); //$NON-NLS-1$
        daaOutputLocation = getStringValue(eo, "daaOutputLocation"); //$NON-NLS-1$
        includeProjects = readIncludeProjects(eo);
    }

    /**
     * Read the projects to include.
     * 
     * @param eo
     * @return
     */
    private List<ProjectType> readIncludeProjects(EObject eo) {
        List<ProjectType> values = new ArrayList<ProjectType>();
        Object value = eo.eGet(eo.eClass().getEStructuralFeature("include")); //$NON-NLS-1$
        if (value instanceof Collection<?>) {
            for (Object next : ((Collection<?>) value)) {
                if (next instanceof EObject) {
                    EObject include = (EObject) next;
                    String name = null;
                    int iterations = 1;
                    int startIndex = 0;
                    Object nameValue =
                            include.eGet(include.eClass()
                                    .getEStructuralFeature("name")); //$NON-NLS-1$
                    if (nameValue instanceof String) {
                        name = (String) nameValue;
                    }
                    Object iterationsValue =
                            include.eGet(include.eClass()
                                    .getEStructuralFeature("iterations")); //$NON-NLS-1$
                    if (iterationsValue instanceof Integer) {
                        iterations = ((Integer) iterationsValue).intValue();
                    }
                    if (iterations == 0) {
                        iterations = 1;
                    }
                    Object startIndexValue =
                            include.eGet(include.eClass()
                                    .getEStructuralFeature("startIndex")); //$NON-NLS-1$
                    if (startIndexValue instanceof Integer) {
                        startIndex = ((Integer) startIndexValue).intValue();
                    }
                    if (name != null) {
                        values
                                .add(new ProjectType(name, iterations,
                                        startIndex));
                    }
                }
            }
        }
        return values;
    }

    /**
     * Read the given attribute value from the EObject.
     * 
     * @param eo
     * @param attrName
     * @return
     */
    private String getStringValue(EObject eo, String attrName) {
        Object value = eo.eGet(eo.eClass().getEStructuralFeature(attrName));
        if (value instanceof String) {
            return (String) value;
        }
        return null;
    }

    public class ProjectType {
        private String name;

        private int iterations;

        private int startIndex;

        public ProjectType(String name, int iterations, int startIndex) {
            this.name = name;
            this.iterations = iterations;
            this.startIndex = startIndex;
        }

        public String getName() {
            return name;
        }

        public int getIterations() {
            return iterations;
        }

        public int getStartIndex() {
            return startIndex;
        }
    }
}
