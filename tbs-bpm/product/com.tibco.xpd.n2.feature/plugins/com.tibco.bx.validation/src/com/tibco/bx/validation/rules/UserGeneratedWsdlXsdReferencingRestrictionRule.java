package com.tibco.bx.validation.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Import;
import org.eclipse.wst.wsdl.Types;
import org.eclipse.xsd.XSDImport;
import org.eclipse.xsd.XSDInclude;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSchemaContent;
import org.eclipse.xsd.XSDSchemaDirective;

import com.tibco.xpd.destinations.GlobalDestinationUtil;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.engine.WorkspaceResourceValidator;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.wsdl.Activator;
import com.tibco.xpd.wsdl.ui.WsdlWorkingCopy;

/**
 * BPM-specific rule to check non-Studio-generated resources (.xsd or .wsdl
 * files) do not appear to be referencing - either by use of imports or includes
 * - Studio-generated resources. The following scenarios are covered: 1) wsdl
 * import of wsdl 2) wsdl import of xsd 3) wsdl include of xsd 4) xsd import of
 * xsd 5) xsd include of xsd
 * 
 * Resources that "appear" to be Studio-generated, here, are taken to any which
 * are located within an Eclipse workspace hidden folder (i.e. '.' prefixed).
 * 
 * @author patkinso
 * @since 16 Sep 2012
 */
public class UserGeneratedWsdlXsdReferencingRestrictionRule implements
        WorkspaceResourceValidator {

    private static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    private static final String ILLEGALLY_REFERENCED_RESOURCE_ISSUE_ID =
            "bx.userGeneratedXpdWsdlIllegalResourceReference"; //$NON-NLS-1$

    private static final String SPECIAL_FOLDER_KIND_TO_CONSIDER =
            Activator.WSDL_SPECIALFOLDER_KIND;

    protected IProject project;

    public UserGeneratedWsdlXsdReferencingRestrictionRule() {
    }

    public void validate(IValidationScope scope, IResource resource) {

        /*
         * only concerned with those resources that are - (i) not user-generated
         * (i.e. not derived artifacts) (ii) reside in wsdl special folders
         */

        boolean isN2Destination =
                GlobalDestinationUtil.isGlobalDestinationEnabled(project,
                        N2Utils.N2_GLOBAL_DESTINATION_ID);

        if (isN2Destination && !isStudioGenerated(resource)) {

            if (inSpecialFolderToValidate(resource)) {
                /*
                 * validate that wsdls/xsds imported/included by the resource
                 * are not located in hidden folders (i.e. assumed to be
                 * generated/derived).
                 */
                Set<URI> illegallyReferencedResources = new HashSet<URI>();
                Set<IResource> alreadyReferencedResources =
                        new HashSet<IResource>();

                addURIsOfIllegalResourceReferences(resource,
                        illegallyReferencedResources,
                        alreadyReferencedResources);

                if (!illegallyReferencedResources.isEmpty()) {

                    String pathsOfGeneratedResources =
                            getListOfResourcePathsForDisplay(illegallyReferencedResources);

                    scope.createIssue(ILLEGALLY_REFERENCED_RESOURCE_ISSUE_ID,
                            resource.getName(),
                            resource.getProjectRelativePath().toString(),
                            Arrays.asList(new String[] { resource.getName(),
                                    pathsOfGeneratedResources }));
                }
            }
        }
    }

    /**
     * @param resource
     * @return
     */
    private boolean inSpecialFolderToValidate(IResource resource) {

        SpecialFolder rootSpecialFolder =
                SpecialFolderUtil.getRootSpecialFolder(resource);
        if (rootSpecialFolder != null) {

            String isGenerated = rootSpecialFolder.getGenerated();
            if ((isGenerated == null) || (isGenerated.trim().isEmpty())) {
                String kind =
                        SpecialFolderUtil
                                .getSpecialFolderKind(rootSpecialFolder
                                        .getFolder());
                return ((kind != null) && (kind
                        .equalsIgnoreCase(SPECIAL_FOLDER_KIND_TO_CONSIDER)));
            }
        }

        return false;
    }

    /**
     * @param resource
     * @return
     */
    private boolean isStudioGenerated(IResource resource) {

        // wsdl/xsd
        boolean isStudioGenerated = resource.isDerived();

        // wsdl
        if (!isStudioGenerated) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(resource);

            if (wc instanceof WsdlWorkingCopy) {
                isStudioGenerated =
                        ((WsdlWorkingCopy) wc).isWsdlStudioGenerated();

                // assert: expected wsdl not to be Studio-generated as was not
                // marked as derived!
                if (isStudioGenerated) {
                    String msg =
                            "Wsdl %1$s is not marked as derived but appears to be Studio-generated"; //$NON-NLS-1$                
                    LOG.warn(String.format(msg, resource.getName()));
                }
            }
        }

        return isStudioGenerated;
    }

    /**
     * @param resourceURIs
     *            platform-relative URIs
     * @return Text suitable for display, containing a separated list of
     *         workspace-relative paths for the passed in resources
     */
    private String getListOfResourcePathsForDisplay(Set<URI> resourceURIs) {

        String ret = null;

        if (!resourceURIs.isEmpty()) {

            final String pathSeparator = "; "; //$NON-NLS-1$
            StringBuilder pathToDisplay = new StringBuilder();

            for (URI platformUri : resourceURIs) {

                // only expecting workspace resources so ...
                if (platformUri.isPlatformResource()) {
                    pathToDisplay.append(platformUri.toPlatformString(true));
                    pathToDisplay.append(pathSeparator);
                }
            }

            int endIndex = pathToDisplay.lastIndexOf(pathSeparator);
            pathToDisplay.setLength(endIndex);

            ret = pathToDisplay.toString();
        }

        return ret;
    }

    /**
     * Adds URIs of all illegally referenced resources (i.e. imported/included
     * schemas that are considered to be Studio-generated)
     * 
     * @param resource
     * @param generatedResources
     * @param alreadyReferencedResources
     */
    private void addURIsOfIllegalResourceReferences(IResource resource,
            Set<URI> generatedResources,
            Set<IResource> alreadyReferencedResources) {

        URI referencingURI =
                URI.createPlatformResourceURI(resource.getFullPath().toString(),
                        true);

        if (referencingURI != null) {
            alreadyReferencedResources.add(resource);
            for (EObject schemaImport : getReferencedSchemas(resource)) {

                // resolve schema import to a URI
                String importedSchemaLocation = null;
                if ((schemaImport instanceof XSDImport) // XSD import
                        || (schemaImport instanceof XSDInclude)) { // XSD
                                                                   // include
                    importedSchemaLocation =
                            ((XSDSchemaDirective) schemaImport)
                                    .getSchemaLocation();
                } else if (schemaImport instanceof Import) { // WSLD import
                    importedSchemaLocation =
                            ((Import) schemaImport).getLocationURI();
                }

                if (importedSchemaLocation != null) {

                    /*
                     * if schema1.xsd is under SD/com/xyz and if it references a
                     * schema under SD/com/xyz/sub/Schema3.xsd, then the valid
                     * schema location in schema1.xsd must be sub/Schema3.xsd.
                     * but if the user specifies the schema location as
                     * /sub/Schema3.xsd, then this validation rule fails to get
                     * the resource for such invalid schema location
                     * 
                     * The safe fix I have thought of is not to validate for
                     * invalid schema location paths
                     */
                    if (!importedSchemaLocation.startsWith("/")) { //$NON-NLS-1$

                        URI importedSchemaURI =
                                URI.createURI(importedSchemaLocation, true)
                                        .resolve(referencingURI);
                        if (isHiddenFolder(importedSchemaURI)) {
                            generatedResources.add(importedSchemaURI);
                        } else {
                            // do recursive search on imports
                            IResource referencedResource =
                                    getResource(importedSchemaURI);
                            if (referencedResource != null
                                    && !alreadyReferencedResources
                                            .contains(referencedResource)) {
                                addURIsOfIllegalResourceReferences(referencedResource,
                                        generatedResources,
                                        alreadyReferencedResources);
                            }
                        }
                    }
                }
            }
        }

    }

    private boolean isHiddenFolder(URI uri) {
        if (uri != null) {
            String platformStr = uri.toPlatformString(true);
            if (platformStr != null) {

                Path path = new Path(platformStr);
                for (String segment : path.segments()) {
                    if (segment.startsWith(".")) { //$NON-NLS-1$
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param schemaURI
     *            Import/included URI of schema
     * @return resource handle corresponding to passed in URI
     */
    private IResource getResource(URI schemaURI) {

        if (schemaURI != null) {
            IResource resource = null;
            String platformString = schemaURI.toPlatformString(true);

            if (platformString != null) {
                resource =
                        ResourcesPlugin.getWorkspace().getRoot()
                                .getFile(new Path(platformString));
            }

            if (resource != null && resource.exists()) {
                return resource;
            }
        }

        return null;
    }

    /**
     * @param resource
     * @return All schema imports (wsdl/xsd) and includes (xsd) for a given
     *         resource
     */
    private EObject[] getReferencedSchemas(IResource resource) {

        List<EObject> schemaImports = new ArrayList<EObject>();

        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(resource);
        if (wc != null) {
            EObject root = wc.getRootElement();

            if (root instanceof Definition) {
                // WSDL: Imports
                EList<?> eImports = ((Definition) root).getEImports();
                for (Object impt : eImports) {
                    if (impt instanceof Import) {
                        schemaImports.add((EObject) impt);
                    }
                }

                // WSDL: in-line schemas
                Types types = ((Definition) root).getETypes();
                if (types != null) {
                    for (Object schema : types.getSchemas()) {
                        if (schema instanceof XSDSchema) {
                            addReferencedXSDSchemas((XSDSchema) schema,
                                    schemaImports);
                        }
                    }
                }
            } else if (root instanceof XSDSchema) {
                // XSD: includes/imports
                addReferencedXSDSchemas((XSDSchema) root, schemaImports);
            }

        }

        return schemaImports.toArray(new EObject[schemaImports.size()]);
    }

    /**
     * @param schemaReferences
     *            Collection on which referenced XSD schemas should be added
     */
    private void addReferencedXSDSchemas(XSDSchema schema,
            List<EObject> schemaReferences) {

        for (XSDSchemaContent schemaRef : schema.getContents()) {
            if ((schemaRef instanceof XSDInclude)
                    || (schemaRef instanceof XSDImport)) {
                schemaReferences.add(schemaRef);
            }
        }
    }

    public void setProject(IProject project) {
        this.project = project;
    }

    public IProject getProject() {
        return project;
    }

}
