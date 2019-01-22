package com.tibco.bds.designtime.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.tibco.bds.designtime.generator.internal.Messages;
import com.tibco.xpd.bom.gen.biz.GenerationException;

/**
 * A generated model bundle may depend on any number of previously-generated
 * model bundles (due to cross-references between BOMs). This class provides a
 * map from namespace URI (String) to entries containing various assets relating
 * to that namespace.
 * 
 * @author smorgan
 * 
 */
public class ForeignPackageMap extends HashMap<String, ForeignPackageMap.Entry> {

    private ResourceSet resourceSet;

    private static final long serialVersionUID = -2306430810295883829L;

    static class Entry {
        private GenPackage genPackage;

        // EPackage resource URI, not the NS URI
        private URI uri;

        private IProject project;

        private Resource resource;

        private boolean isUsed;

        public Entry(GenPackage genPackage, URI uri, IProject project) {
            this.genPackage = genPackage;
            this.uri = uri;
            this.project = project;
        }

        public GenPackage getGenPackage() {
            return genPackage;
        }

        public URI getURI() {
            return uri;
        }

        public IProject getProject() {
            return project;
        }

        private void setIsUsed(boolean value) {
            isUsed = value;
        }

        private boolean isUsed() {
            return isUsed;
        }

        public Resource getResource(ResourceSet resourceSet) throws IOException {
            // If resource hasn't been loaded, load it now
            // (done lazily to avoid wasting time loading if not needed)
            if (resource == null) {
                resource = resourceSet.createResource(uri);
                resource.load(Collections.EMPTY_MAP);
            }
            return resource;
        }
    }

    public ForeignPackageMap() {
        resourceSet = new ResourceSetImpl();
    }

    public ResourceSet getResourceSet() {
        return resourceSet;
    }

    public void populateFromProjects(Collection<IProject> projects)
            throws GenerationException {
        // For each foreign project...
        for (IProject project : projects) {
            Collection<IResource> resources;
            try {
                // Search for genmodel files in the foreign project, and
                // for each of them...
                resources =
                        findResourcesByNamePattern(project, ".*\\.genmodel");
                if (!resources.isEmpty()) {
                    for (IResource res : resources) {
                        // Open up the genmodel
                        Resource r =
                                getResourceSet().createResource(URI
                                        .createPlatformResourceURI(res.getFullPath()
                                                .makeAbsolute().toString(), true));
                        r.load(Collections.EMPTY_MAP);
                        for (EObject eObj : r.getContents()) {
                            if (eObj instanceof GenModel) {
                                GenModel genModel = (GenModel) eObj;
                                for (GenPackage pkg : genModel.getGenPackages()) {
                                    // Record mappings from NS URI to
                                    // the gen package itself and the
                                    // ecore file to which it refers.
                                    put(pkg.getNSURI(),
                                            new ForeignPackageMap.Entry(pkg,
                                                    pkg.getEcorePackage()
                                                            .eResource()
                                                            .getURI(), project));
                                }
                            }
                        }
                    }
                }
            } catch (CoreException e) {
                throw new GenerationException(Messages
                        .getString("OawTeneoGenerator_foreignGenModelFail"), //$NON-NLS-1$
                        e);
            } catch (IOException e) {
                throw new GenerationException(Messages
                        .getString("OawTeneoGenerator_foreignGenModelFail"), //$NON-NLS-1$
                        e);
            }
        }
    }

    public void replacePackages(List<EPackage> packages) throws IOException {
        for (EPackage pkg : packages) {
            String nsURI = pkg.getNsURI();
            if (containsKey(nsURI)) {
                Resource res = get(nsURI).getResource(resourceSet);
                EList<EObject> existingContents = res.getContents();
                for (int i = 0; i < existingContents.size(); i++) {
                    EObject obj = existingContents.get(i);
                    if (obj instanceof EPackage) {
                        if (((EPackage) obj).getNsURI().equals(pkg.getNsURI())) {
                            // Replace previously-generated EPackage with
                            // the one we've just generated, but maintaining
                            // the index within the file. We've no intention
                            // of saving this permanently, we just want to
                            // ensure that local packages that reference
                            // this foreign package will use the correct
                            // full path to entities within the foreign
                            // ecore, even when that ecore contains
                            // multiple packages (i.e. is XMI format so
                            // needs to include an index number).
                            existingContents.set(i, pkg);
                            get(nsURI).setIsUsed(true);
                        }
                    }
                }
            }
        }
    }

    // TODO Consider that order may be different to before
    public List<IProject> getUsedProjects() {
        List<IProject> result = new ArrayList<IProject>();
        for (Entry entry : values()) {
            IProject project = entry.getProject();
            if (entry.isUsed() && !result.contains(project)) {
                result.add(project);
            }
        }
        return result;
    }

    public List<GenPackage> getUsedGenPackages() {
        List<GenPackage> result = new ArrayList<GenPackage>();
        List<String> nsURIs = new ArrayList<String>();
        nsURIs.addAll(keySet());
        // Sort to ensure list is in a consistent order. This makes
        // no functional difference, but a change in order would
        // later change the declaration order in a .genmodel file,
        // thus breaking unit test diff'ing.
        Collections.sort(nsURIs);
        for (String nsURI : nsURIs) {
            Entry entry = get(nsURI);
            if (entry.isUsed()) {
                result.add(get(nsURI).getGenPackage());
            }
        }
        return result;
    }

    public List<EPackage> filterToUnknownPackages(List<EPackage> packages) {
        List<EPackage> result = new ArrayList<EPackage>();
        for (EPackage pkg : packages) {
            String nsURI = pkg.getNsURI();
            if (nsURI != null) {
                if (!containsKey(nsURI)) {
                    result.add(pkg);
                }
            }
        }
        return result;
    }

    private static Collection<IResource> findResourcesByNamePattern(
            IResource p, String pattern) throws CoreException {

        Collection<IResource> result = new ArrayList<IResource>();
        String name = p.getName();
        if (Pattern.matches(pattern, name)) {
            result.add(p);
        }
        if (p instanceof IContainer) {
            for (IResource a : ((IContainer) p).members()) {
                result.addAll(findResourcesByNamePattern(a, pattern));
            }
        }
        return result;
    }
}
