/**
 * 
 */
package com.tibco.xpd.bom.globaldata.api;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLFactory;

import com.tibco.xpd.bom.globaldata.GlobalDataActivator;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;

/**
 * 
 * Utility methods for Global Data related operations on BOM UML2 model.
 * 
 * @author rgreen
 * 
 */
public class BOMGlobalDataUtils {

    private final static Logger LOG =
            GlobalDataActivator.getDefault().getLogger();

    // The GlobalData profile manager.
    private static GlobalDataProfileManager GD_PROFILE_MANAGER =
            GlobalDataProfileManager.getInstance();

    private static String[] DISALLOWED_CID_PRIMITIVE_TYPE_NAMES =
            { PrimitivesUtil.BOM_PRIMITIVE_ATTACHMENT_NAME,
                    PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME,
                    PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME,
                    PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME,
                    PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME,
                    PrimitivesUtil.BOM_PRIMITIVE_DURATION_NAME,
                    PrimitivesUtil.BOM_PRIMITIVE_ID_NAME,
                    PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME,
                    PrimitivesUtil.BOM_PRIMITIVE_URI_NAME };

    /**
     * This checks whether the BOM has any global data element. To have a global
     * data element the GD profile must have been applied to the model
     * 
     * @param model
     * @return <code>true</code> if the model is found to contain any
     *         GD-specific entities
     * 
     * @param model
     * @return
     */
    public static boolean isGlobalDataBOM(Model model) {

        if (model != null) {
            // Only checks for global data elements if global profile is applied
            // to the BOM
            if (hasGlobalDataProfile(model)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param typeName
     * @return <code>true</code> if supplied type and name are disallowed
     */
    public static boolean isDisallowedCaseIdTypeName(String name) {
        HashSet<String> disallowedNames = new HashSet<String>(
                Arrays.asList(DISALLOWED_CID_PRIMITIVE_TYPE_NAMES));
        return disallowedNames.contains(name);
    }

    /**
     * Returns <code>true</code> if the Global Data profile is applied to the
     * supplied Model
     * 
     * @param model
     * @return boolean
     */
    public static boolean hasGlobalDataProfile(Model model) {
        boolean isProfApplied = false;
        /*
         * get Global Data profile
         */
        Profile gDataProfile = GD_PROFILE_MANAGER.getProfile();
        if (gDataProfile != null) {
            if (model != null && model.getAppliedProfiles() != null
                    && model.getAppliedProfiles().contains(gDataProfile)) {
                isProfApplied = true;
            }
        }
        return isProfApplied;
    }

    /**
     * Returns <code>true</code> if the Global Data profile is applied to the
     * supplied Model
     * 
     * @param bomResource
     * @return
     */
    public static boolean hasGlobalDataProfile(IResource bomResource) {

        WorkingCopy wc =
                XpdResourcesPlugin.getDefault().getWorkingCopy(bomResource);
        if (wc != null) {
            EObject rootElement = wc.getRootElement();
            if (rootElement instanceof Model) {
                return hasGlobalDataProfile((Model) rootElement);
            }
        }
        return false;
    }

    /**
     * checks if the given bom resource has any global data elements (basically
     * checks if the model in the resource has any case/global classes)
     * 
     * @param bomResource
     * @return <code>true</code> if there are any case/global classes in the
     *         given bom resource
     */
    public static boolean hasGlobalDataElements(IResource bomResource) {

        if (null != bomResource) {

            String fileExt = bomResource.getFileExtension();
            if (null != fileExt && BOMResourcesPlugin.BOM_FILE_EXTENSION
                    .equalsIgnoreCase(fileExt)) {

                WorkingCopy wc = XpdResourcesPlugin.getDefault()
                        .getWorkingCopy(bomResource);
                EObject rootElement = wc.getRootElement();
                if (rootElement instanceof Model) {

                    Model model = (Model) rootElement;
                    if (hasGlobalDataElements(model)) {

                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * checks if the given bom model has any global data elements (basically
     * checks if the model has any case/global classes)
     * 
     * @param model
     * @return <code>true</code> if there are any case/global classes in the bom
     */
    public static boolean hasGlobalDataElements(Model model) {

        if (hasGlobalDataElements(model.getPackagedElements())) {

            return true;
        }
        return false;
    }

    /**
     * Recursive method to ensure sub-packages are searched for Global Data
     * entities.
     * 
     * @param packageableElement
     */
    private static boolean hasGlobalDataElements(
            EList<PackageableElement> pElems) {

        for (PackageableElement packageableElement : pElems) {

            if (packageableElement instanceof Package) {

                Package pkg = ((Package) packageableElement);
                if (hasGlobalDataElements(pkg.getPackagedElements())) {

                    return true;
                }
            } else if (packageableElement instanceof Class) {

                if (!isLocal((Class) packageableElement)) {

                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks if a given package has a Case Class in it
     * 
     * @param pElems
     * @return
     */
    private static boolean hasGlobalCaseClass(
            EList<PackageableElement> pElems) {

        for (PackageableElement packageableElement : pElems) {
            if (packageableElement instanceof Package) {
                Package pkg = ((Package) packageableElement);
                if (hasGlobalCaseClass(pkg.getPackagedElements())) {
                    return true;
                }
            } else if (packageableElement instanceof Class) {
                if (isCaseClass((Class) packageableElement)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * @param bomResource
     * @return <code>true</code> if a Global Data entity has been detected in
     *         resource
     */
    public static boolean isGlobalDataBOM(IResource bomResource) {

        if (bomResource != null) {
            String fileExt = bomResource.getFileExtension();
            if (fileExt != null && fileExt
                    .equalsIgnoreCase(BOMResourcesPlugin.BOM_FILE_EXTENSION)) {
                WorkingCopy wc = XpdResourcesPlugin.getDefault()
                        .getWorkingCopy(bomResource);
                EObject rootElement = wc.getRootElement();
                if (rootElement instanceof Model) {
                    Model model = (Model) rootElement;
                    if (isGlobalDataBOM(model)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * @param bomResource
     * @return <code>true</code> if a Global Data entity has been detected in
     *         resource
     */
    public static boolean hasGlobalCaseClass(IResource bomResource) {

        if (bomResource != null) {
            String fileExt = bomResource.getFileExtension();
            if (fileExt != null && fileExt
                    .equalsIgnoreCase(BOMResourcesPlugin.BOM_FILE_EXTENSION)) {
                WorkingCopy wc = XpdResourcesPlugin.getDefault()
                        .getWorkingCopy(bomResource);
                EObject rootElement = wc.getRootElement();
                if (rootElement instanceof Model) {
                    Model model = (Model) rootElement;
                    if (model != null) {
                        // Only checks for global data elements if global
                        // profile is applied to the BOM
                        if (hasGlobalDataProfile(model)) {
                            if (hasGlobalCaseClass(
                                    model.getPackagedElements())) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * @param clazz
     * @return
     */
    public static boolean isCaseClass(Class clazz) {
        return GD_PROFILE_MANAGER.isCase(clazz);
    }

    /**
     * @param clazz
     * @return
     */
    public static boolean isGlobalClass(Class clazz) {
        return GD_PROFILE_MANAGER.isGlobal(clazz);
    }

    /**
     * @param clazz
     * @return
     */
    public static boolean isLocal(Class clazz) {
        return GD_PROFILE_MANAGER.isLocal(clazz);
    }

    /**
     * @param clazz
     * @return
     */
    public static boolean isCaseDocumentationEnabled(Class clazz) {
        return GD_PROFILE_MANAGER.isCaseDocumentationEnabled(clazz);
    }

    /**
     * @param property
     * @return
     */
    public static boolean isCID(Property property) {
        return isCustomCID(property) || isAutoCID(property)
                || isCompositeCID(property);
    }

    /**
     * @param property
     * @return
     */
    public static boolean isSearchable(Property property) {
        return GD_PROFILE_MANAGER.isSearchable(property);
    }

    /**
     * Checks whether global data is enabled using command line arg
     * "-Dglobaldata.development"
     * 
     * This check is expected to only be temporary; whilst Global Data is in
     * development.
     */
    public static boolean isGlobalDataDevelopmentEnabled() {
        return Boolean.parseBoolean(
                System.getProperty("globaldata.development", "false")); //$NON-NLS-1$//$NON-NLS-2$
    }

    /**
     * Returns true if the given property is a Custom CaseIdentifier, false
     * otherwise.
     * 
     * @param property
     * @return
     */
    public static boolean isCustomCID(Property property) {
        return GD_PROFILE_MANAGER.isCID(property);
    }

    /**
     * Returns true if the given property is an AutoGenerated CaseIdentifier,
     * false otherwise.
     * 
     * @param property
     * @return
     */
    public static boolean isAutoCID(Property property) {
        return GD_PROFILE_MANAGER.isAutoCaseIdentifier(property);
    }

    /**
     * Returns true if the given property is a Composite CaseIdentifier, false
     * otherwise.
     * 
     * @param property
     * @return
     */
    public static boolean isCompositeCID(Property property) {
        return GD_PROFILE_MANAGER.isCompositeCaseIdentifier(property);
    }

    /**
     * Returns true if the given property is a Case State, false otherwise.
     * 
     * @param property
     * @return
     */
    public static boolean isCaseState(Property property) {
        return GD_PROFILE_MANAGER.isCaseState(property);
    }

    /**
     * Get the CaseState stereotype.
     * 
     * @return the CaseState stereotype.
     */
    public static Stereotype getCaseStateStereotype() {
        return GD_PROFILE_MANAGER.getStereotype(StereotypeKind.CASE_STATE);
    }

    /**
     * Returns the tag value for the Model, returns blank "" string when the tag
     * is not set or model without a GlobalData profile.
     * 
     * @param model
     * @return
     */
    public static String getPackageTag(Package pkg) {
        String tag = ""; //$NON-NLS-1$

        Model model = null;
        // Check the case for a sub-packages, in this case we need a tag
        // to be displayed
        if (pkg instanceof Model) {
            model = (Model) pkg;
        } else {
            model = pkg.getModel();
        }

        if (BOMGlobalDataUtils.hasGlobalDataProfile(model)) {
            if (pkg.hasValue(
                    GD_PROFILE_MANAGER.getStereotype(StereotypeKind.TAG),
                    BOMResourcesPlugin.ModelGlobalDataProfile_attribute_Tag)) {
                Object value = pkg.getValue(
                        GD_PROFILE_MANAGER.getStereotype(StereotypeKind.TAG),
                        BOMResourcesPlugin.ModelGlobalDataProfile_attribute_Tag);
                if (value instanceof String) {
                    tag = ((String) value).trim();
                }
            }
        }
        return tag;
    }

    /**
     * Returns the batch size setting for the NamedElement or null if there is
     * no batch size set
     * 
     * @param namedElement
     * @return
     */
    public static Integer getFetchingBatchSize(NamedElement namedElement) {
        Integer batchSize = null;

        if (BOMGlobalDataUtils.hasGlobalDataProfile(namedElement.getModel())) {
            // Check this element for any batch size setting
            batchSize = GD_PROFILE_MANAGER.getFetchingBatchSize(namedElement);

            // If not value found yet - try any associations
            if ((batchSize == null) || (batchSize.intValue() == 0)) {
                if (namedElement instanceof Property) {
                    Association association =
                            ((Property) namedElement).getAssociation();
                    if (association != null) {
                        batchSize = GD_PROFILE_MANAGER
                                .getFetchingBatchSize(association);
                    }
                }
            }
        }
        return batchSize;
    }

    /**
     * Sets all the values on the property for an auton case identifier
     * 
     * @param newElement
     * @param rset
     */
    public static void setAutoCaseIdentifierRestrictions(Property newElement,
            ResourceSet rset) {
        /*
         * XPD-4793 Global data: AutoCaseIdentifier will be of a fixed type of
         * “Fixed Length Integer” with a “Number Length” set to 19 – These
         * values should not be possible for the user to change via the
         * properties window.
         */
        PrimitiveType primType =
                PrimitivesUtil.getStandardPrimitiveTypeByName(rset,
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        newElement.setType(primType);
        EnumerationLiteral createEnumerationLiteral =
                UMLFactory.eINSTANCE.createEnumerationLiteral();
        createEnumerationLiteral
                .setName(PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH); // $NON-NLS-1$
        // Fixed length sub type
        PrimitivesUtil.setFacetPropertyValue(primType,
                PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_SUBTYPE, // $NON-NLS-1$
                createEnumerationLiteral,
                newElement);
        // set fixed length value
        PrimitivesUtil.setFacetPropertyValue(primType,
                PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH,
                GlobalDataProfileManager.LENGTH_AUTO_CASE_IDENTIFIER,
                newElement);
        newElement.setIsReadOnly(true);
    }

    /**
     * @param project
     * @return <code>true</code> if project contains any Global Data elements
     */
    public static boolean isGlobalDataBOMProject(IProject project) {

        List<SpecialFolder> bomSpecialFolders =
                SpecialFolderUtil.getAllSpecialFoldersOfKind(project,
                        BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);

        if (bomSpecialFolders != null) {
            for (SpecialFolder sf : bomSpecialFolders) {
                try {
                    return hasGlobalDataBom(sf.getFolder());
                } catch (CoreException e) {
                    String fmtErr =
                            "Failed to determine whether project '%s' contains Global Data BOMs."; //$NON-NLS-1$
                    LOG.error(e, String.format(fmtErr, project.getName()));
                }
            }
        }

        return false;
    }

    /**
     * 
     * XPD-5525: check for bom resource(s) with case class(es) in the given
     * project
     * 
     * @param project
     * @return <code>true</code> if the given project has bom(s) with atleast
     *         one case class; <code>false</code> otherwise
     */
    public static boolean hasCaseDataInProject(IProject project) {

        List<SpecialFolder> bomSpecialFolders =
                SpecialFolderUtil.getAllSpecialFoldersOfKind(project,
                        BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);

        if (bomSpecialFolders != null) {
            for (SpecialFolder specialFolder : bomSpecialFolders) {

                try {
                    return hasCaseDataBOM(specialFolder.getFolder());
                } catch (CoreException e) {
                    String fmtErr =
                            "Failed to determine whether project '%s' contains Case Classes"; //$NON-NLS-1$
                    LOG.error(e, String.format(fmtErr, project.getName()));
                }
            }
        }

        return false;
    }

    /**
     * Check if the given BOM folder has Case classes
     * 
     * @param container
     * @return
     * @throws CoreException
     */
    public static boolean hasCaseDataBOM(IContainer container)
            throws CoreException {

        if (null != container) {

            for (IResource res : container.members()) {

                if (res instanceof IFile) {

                    if (BOMGlobalDataUtils.hasGlobalCaseClass(res)) {

                        return true;
                    }
                } else if (res instanceof IFolder) {

                    if (hasCaseDataBOM((IContainer) res)) {

                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check if the given BOM folder has Global Data BOMs
     * 
     * @param container
     * @return <code>true</code> if Global Data BOM detected
     * @throws CoreException
     */
    public static boolean hasGlobalDataBom(IContainer container)
            throws CoreException {

        if (container != null) {
            for (IResource res : container.members()) {
                if (res instanceof IFile) {
                    if (BOMGlobalDataUtils.isGlobalDataBOM(res)) {
                        return true;
                    }
                } else if (res instanceof IFolder) {
                    if (hasGlobalDataBom((IContainer) res)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * TODO: move to a core util class (com.tibco.xpd.resources.*)
     * 
     * Check if the given container (or any subfolders) has a file with one of
     * the given file extensions.
     * 
     * @param container
     * @param fileExt
     * @return <code>true</code> if container contains a file with an extension
     *         matching one of those supplied in fileExt
     * @throws CoreException
     */
    public static boolean hasFileWithExtension(IContainer container,
            Set<String> fileExts) {

        if (container != null && fileExts != null) {
            try {
                for (IResource res : container.members()) {
                    if (res instanceof IFile) {
                        String fileExtension = res.getFileExtension();

                        if (fileExtension != null
                                && fileExts.contains(fileExtension)) {
                            return true;
                        }
                    } else if (res instanceof IFolder) {
                        if (hasFileWithExtension((IContainer) res, fileExts)) {
                            return true;
                        }
                    }
                }
            } catch (CoreException e) {
                XpdResourcesPlugin.getDefault().getLogger().error(e,
                        "Problem accessing resources of supplied container."); //$NON-NLS-1$
            }
        }

        return false;
    }

}
