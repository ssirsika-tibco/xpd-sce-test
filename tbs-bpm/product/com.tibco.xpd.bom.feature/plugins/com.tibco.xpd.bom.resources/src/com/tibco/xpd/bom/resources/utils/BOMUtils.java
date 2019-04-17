package com.tibco.xpd.bom.resources.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.ProfileApplication;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLFactory;
import org.osgi.framework.Version;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.firstclassprofiles.IFirstClassProfileExtension;
import com.tibco.xpd.bom.resources.internal.businessdata.BusinessDataBuildVersionUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Contains some common methods for various BOM operations.
 * 
 * @author glewis
 * 
 */
public class BOMUtils {

    /**
     * The BOM generated from XSD profile.
     */
    public static final String XSD_NOTATION_PROFILE = "XsdNotationProfile"; //$NON-NLS-1$

    /**
     * id of the Business Data asset.
     */
    public static final String BUSINESS_DATA_ASSET_ID =
            "com.tibco.xpd.asset.businessdata.bom"; //$NON-NLS-1$

    /*
     * XPD-3672 Disabled the creation of Persistent BOM models. This is to allow
     * users to continue using existing Persistent BOMs but not be able to
     * create new ones
     */
    public static final String CDS_EXTENSION_ID =
            "com.tibco.xpd.bom.modeler.diagram.profile.cds"; //$NON-NLS-1$

    /**
     * Get a specific applied profile
     * 
     * @param object
     * @param profileName
     * @return
     */
    public static Profile getProfileApplied(Object object, String profileName) {
        if (object instanceof org.eclipse.uml2.uml.Class) {
            object = ((org.eclipse.uml2.uml.Class) object).getPackage();
        }
        if (object instanceof Model || object instanceof Package) {
            Package umlPackage = (Package) object;

            Iterator<ProfileApplication> iter =
                    umlPackage.getAllProfileApplications().iterator();
            while (iter.hasNext()) {
                Profile appliedProfile = iter.next().getAppliedProfile();
                if (appliedProfile != null && appliedProfile.getName() != null
                        && appliedProfile.getName().equals(profileName)) {
                    return appliedProfile;
                }
            }
        }
        return null;
    }

    /**
     * Check if the given project is a Business Data project.
     * 
     * @param project
     * @return <code>true</code> if project contains any Global Data elements
     */
    public static boolean isBusinessDataProject(IProject project) {

        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        if (config != null) {

            return config.hasAssetType(BUSINESS_DATA_ASSET_ID);
        }

        return false;
    }

    /**
     * Make the given project a business data project
     * 
     * @param project
     */
    public static void setAsBusinessDataProject(IProject project) {
        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);

        config.addAssetTask(BOMUtils.BUSINESS_DATA_ASSET_ID);
        config.saveWorkingCopy();
    }

    /**
     * 
     * for a business data project the composite/app name will have project id
     * suffixed with "-"+major version of the project
     * 
     * bharti: This was being done in ProjectDAAGenerator. As this is required
     * for daa junit tests (please look at
     * com.tibco.xpd.n2.daa.test.util.DAAUtil.getDAABuildQualifier), moved it to
     * the utils so that we have the construction of app name in one place.
     * 
     * @param project
     * @param projectIdOrCompositeName
     * @return composite/app name suffixed with "-" + <major version of the
     *         project>
     */
    public static String getAppName(IProject project,
            String projectIdOrCompositeName) {

        String versionStr = ProjectUtil.getProjectVersion(project);
        Version version = Version.parseVersion(versionStr);
        int major = version.getMajor();
        String globalDataFormatedName = projectIdOrCompositeName + "-" + major; //$NON-NLS-1$
        return globalDataFormatedName;
    }

    /**
     * Get the current build version of the given Business Data project. This
     * will get the project lifecycle version and replace the qualifier (if set)
     * with the last change time stamp from the preference. If "qualifier" is
     * set by the user then then lifecycle version will be returned as is.
     * 
     * @param project
     * @return version or <code>null</code> if no project doesn't have a
     *         version.
     * @throws IllegalArgumentException
     *             if the project is not a Business Data project.
     */
    public static String getBusinessDataBuildVersion(IProject project) {
        return BusinessDataBuildVersionUtil
                .getBusinessDataBuildVersion(project);
    }

    /**
     * Get the current build version of the given Business Data project from the
     * preference.
     * 
     * @param project
     * @return
     * 
     * @throws IllegalArgumentException
     *             if the project is not a Business Data project.
     */
    public static String getBuildVersionTimeStamp(IProject project) {

        return BusinessDataBuildVersionUtil.getBuildVersionTimeStamp(project);
    }

    /**
     * Get a specific stereotype (from a certain profile) applied to an Object
     * 
     * @param object
     * @param stereotypeName
     * @param profileName
     * @return
     */
    public static Stereotype getStereotype(Object object,
            String stereotypeName, String profileName) {
        Profile profile = getProfileApplied(object, profileName);
        if (profile != null) {
            Iterator<Stereotype> iter = null;
            if (object instanceof Model || object instanceof Package) {
                iter = ((Package) object).getAppliedStereotypes().iterator();
            } else if (object instanceof org.eclipse.uml2.uml.Class) {
                iter =
                        ((org.eclipse.uml2.uml.Class) object)
                                .getAppliedStereotypes().iterator();
            }
            if (iter != null) {
                while (iter.hasNext()) {
                    Stereotype tempStereotype = iter.next();
                    if (tempStereotype.getName().equals(stereotypeName)) {
                        return tempStereotype;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Fetches a particular property value from a given stereotype
     * 
     * @param object
     * @param stereotype
     * @param propertyName
     * @return
     */
    public static Object getProperty(Object object, Stereotype stereotype,
            String propertyName) {
        if (stereotype != null && object instanceof Classifier) {
            Classifier umlClassifier = (Classifier) object;
            if (umlClassifier.hasValue(stereotype, propertyName)) {
                return umlClassifier.getValue(stereotype, propertyName);
            }
        } else if (object instanceof Package) {
            Package umlPackage = (Package) object;
            if (umlPackage.hasValue(stereotype, propertyName)) {
                return umlPackage.getValue(stereotype, propertyName);
            }
        }
        return null;
    }

    /**
     * Method to extract a namespace uri from a given package. This should be
     * the default method to call if you are unsure of functionality
     * 
     * @param pkg
     * @return
     */
    public static String getNamespace(Package pkg) {
        return getNamespace(pkg, true);
    }

    /**
     * Method to extract a namespace uri from a given package. Checks to see if
     * it can be got from a particular stereotype first and if not it uses its
     * own algorithm to work it out.
     * 
     * @param pkg
     * @param keepParsing
     *            - indicates whether we want to recursively use other container
     *            packages to work out namesapce
     * @return
     */
    public static String getNamespace(Package pkg, boolean keepParsing) {
        if (pkg != null) {
            String currentPackageName = pkg.getName();
            Stereotype xsdBasedModelStereotype =
                    getStereotype(pkg, "XsdBasedModel", XSD_NOTATION_PROFILE); //$NON-NLS-1$ //$NON-NLS-2$
            Object property = null;
            if (xsdBasedModelStereotype != null) {
                property =
                        getProperty(pkg,
                                xsdBasedModelStereotype,
                                "xsdTargetNamespace"); //$NON-NLS-1$
            }
            if (xsdBasedModelStereotype != null && property != null
                    && property.toString().trim().length() > 0) {
                return property.toString().trim();
            } else {
                if (currentPackageName.endsWith(".")) { //$NON-NLS-1$
                    return getNamespace(pkg,
                            currentPackageName.substring(0,
                                    currentPackageName.length() - 1),
                            keepParsing);
                } else {
                    return getNamespace(pkg, currentPackageName, keepParsing);
                }
            }
        } else {
            return ""; //$NON-NLS-1$
        }
    }

    /**
     * Method to extract a namespace uri from a given package. Checks to see if
     * it can be got from a particular stereotype first and if not it uses its
     * own algorithm to work it out.
     * 
     * @param pkg
     * @param namespace
     * @param keepParsing
     *            - indicates whether we want to recursively use other container
     *            packages to work out namesapce
     * @return
     */
    public static String getNamespace(Package pkg, String namespace,
            Boolean keepParsing) {
        if (pkg != null && namespace != null) {
            if (namespace.indexOf(".") == -1) { //$NON-NLS-1$
                String returnedNamespace = "http://" + namespace; //$NON-NLS-1$
                if (keepParsing == false) {
                    return returnedNamespace;
                } else {
                    return getFullNamespace(pkg, returnedNamespace);
                }
            } else {
                ArrayList<String> arrList = new ArrayList<String>();
                String[] splitArr = namespace.split("\\."); //$NON-NLS-1$
                String postfix = ""; //$NON-NLS-1$
                for (int i = splitArr.length - 1; i >= 0; i--) {
                    arrList.add(splitArr[i]);
                }
                postfix = arrList.get(0);
                ArrayList<String> tmpList = new ArrayList<String>();
                int index = 0;
                String prefix = ""; //$NON-NLS-1$
                for (String tmpString : arrList) {
                    if (index > 0) {
                        prefix += tmpString;
                    }
                    tmpList.add(tmpString);
                    if (index > 0 && index < arrList.size() - 1)
                        prefix += "."; //$NON-NLS-1$
                    index++;
                }
                if (keepParsing == false) {
                    return ("http://" + prefix + "/" + postfix); //$NON-NLS-1$ //$NON-NLS-2$
                } else {
                    return getFullNamespace(pkg,
                            ("http://" + prefix + "/" + postfix)); //$NON-NLS-1$ //$NON-NLS-2$
                }

            }
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * If package has container package then this is recursively called until it
     * reaches root model to construct namespace
     * 
     * @param pkg
     * @param currentNamespace
     * @return
     */
    public static String getFullNamespace(Package pkg, String currentNamespace) {
        if (pkg != null) {
            if (pkg.eContainer() != null
                    && pkg.eContainer() instanceof Package == true) {
                String tempNamespace =
                        currentNamespace.replaceFirst("http://", "/"); //$NON-NLS-1$ //$NON-NLS-2$
                String resultNamespace =
                        getNamespace((Package) pkg.eContainer(), true)
                                + tempNamespace;
                return resultNamespace;
            } else {
                String resultNamespace = getNamespace(pkg, false);
                return resultNamespace;
            }
        } else {
            return ""; //$NON-NLS-1$
        }
    }

    /**
     * Checks if the NamedElement should be exported to XML Schema with a Top
     * Level Element
     * 
     * @param element
     *            NamedElement
     * @return boolean
     */
    public static boolean isExportAsTLE(Classifier element) {
        boolean notationProfileApplied =
                BOMProfileUtils.isProfileAppliedToPackage(element.getPackage(),
                        "pathmap://XSD_NOTATION_TYPES/XsdNotation.profile.uml"); //$NON-NLS-1$

        if (!notationProfileApplied) {
            // We are dealing with a user-defined BOM that has no XSD profile
            // applied.
            EAnnotation annotation =
                    element.getEAnnotation(BOMResourcesPlugin.ElementEannotationMetaSource);

            if (annotation == null) {
                // Return the default value of true
                return true;
            } else {
                String isTLE =
                        annotation
                                .getDetails()
                                .get(BOMResourcesPlugin.ElementEannotationMetaSource_TopLevelElement);

                if (isTLE == null
                        || isTLE.equals(BOMResourcesPlugin.BOM_TRUE_FLAG)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;

    }

    /**
     * 
     * Sets the Top Level Element Eannotation (boolean) flag on the
     * PackagedElement.
     * 
     * @param packageableElement
     *            PackageableElement
     * @param state
     *            boolean
     */
    public static void setTLEFlag(PackageableElement packageableElement,
            boolean state) {

        EAnnotation annotation =
                packageableElement
                        .getEAnnotation(BOMResourcesPlugin.ElementEannotationMetaSource);

        if (annotation == null) {
            annotation =
                    packageableElement
                            .createEAnnotation(BOMResourcesPlugin.ElementEannotationMetaSource);
        }

        if (state) {
            annotation
                    .getDetails()
                    .put(BOMResourcesPlugin.ElementEannotationMetaSource_TopLevelElement,
                            BOMResourcesPlugin.BOM_TRUE_FLAG);

        } else {
            annotation
                    .getDetails()
                    .put(BOMResourcesPlugin.ElementEannotationMetaSource_TopLevelElement,
                            BOMResourcesPlugin.BOM_FALSE_FLAG);
        }

    }

    /**
     * 
     * Sets the BOM version which is stored as an eAnnotation at Model level.
     * 
     * @param model
     * @param version
     */
    public static void setBOMVersion(Model model, String version) {

        EAnnotation annotation =
                model.getEAnnotation(BOMResourcesPlugin.ModelEannotationMetaSource);

        if (annotation != null) {

            annotation.getDetails()
                    .put(BOMResourcesPlugin.ModelEannotationMetaSource_version,
                            version);

        }

    }

    /**
     * @param model
     * @return checksum value stored inside the model.metadata annotation in the
     *         given model or null
     */
    public static String getBOMCheckSum(Model model) {
        if (model != null && model.getEAnnotations() != null) {
            EAnnotation eAnnotation =
                    model.getEAnnotation(BOMResourcesPlugin.ModelEannotationMetaSource);
            if (eAnnotation != null) {
                return eAnnotation
                        .getDetails()
                        .get(BOMResourcesPlugin.ModelEannotationMetaSource_checksum);
            }
        }
        return null;
    }

    /**
     * 
     * Gets the BOM version which is stored as an eAnnotation at the Model
     * level.
     * 
     * @param model
     * @param version
     * @return String version
     */
    public static String getBOMVersion(Model model, String version) {

        EAnnotation annotation =
                model.getEAnnotation(BOMResourcesPlugin.ModelEannotationMetaSource);

        if (annotation != null) {

            return annotation.getDetails()
                    .get(BOMResourcesPlugin.ModelEannotationMetaSource_version);

        } else {
            return null;
        }

    }

    /**
     * Sets universal initial parameters required for ALL Business Object
     * Models.
     * 
     * The following are NOT set and need to be initialized elsewhere:
     * 
     * <ul>
     * <li>Model Name</li>
     * <li>Set a Viewpoint if BOM represents a first class profile</li>
     * </ul>
     * 
     * 
     * @return Model
     */
    public static Model createInitialModel() {

        Model model = UMLFactory.eINSTANCE.createModel();

        return model;

    }

    /**
     * 
     * Returns a list of Classes that directly extend from (generalize) the
     * passed in Class. Note that this accounts for only one level of sub-class.
     * This method should be called recursively if further levels of subclass
     * are required.
     * 
     * @param Class
     * @return List&ltClass&gt
     */
    public static List<Class> getDirectSubClasses(Class cl) {
        List<Class> lstClasses = new ArrayList<Class>();
        for (Classifier clasifier : getDirectSubClassifiers(cl)) {
            if (clasifier instanceof Class) {
                lstClasses.add((Class) clasifier);
            }
        }
        return lstClasses;
    }

    public static List<Classifier> getDirectSubClassifiers(Classifier classifier) {
        List<Classifier> lstClassifiers = new ArrayList<Classifier>();
        // Get all edges associated with the properties
        ECrossReferenceAdapter adapter =
                ECrossReferenceAdapter.getCrossReferenceAdapter(classifier);
        Collection<Setting> references =
                adapter.getInverseReferences(classifier);

        for (Setting ref : references) {
            if (ref.getEObject() instanceof Generalization) {

                Generalization gen = (Generalization) ref.getEObject();

                Classifier specific = gen.getSpecific();
                Classifier general = gen.getGeneral();

                if (general == classifier) {
                    // Collect all subclasses
                    if (specific instanceof Classifier) {
                        lstClassifiers.add(specific);
                    }

                }
            }
        }
        return lstClassifiers;
    }

    /**
     * @param c1
     * @param c2
     * @return <code>true</code> if passed in classifiers come from the same BOM
     *         resource and participate in a common association relationship.
     *         <code>false</code> otherwise or if any of the classifier's
     *         resources cannot be determined.
     */
    public static boolean classesOfIntraBomAssociation(final Classifier c1,
            final Classifier c2) {

        if (hasSameBomResource(c1, c2)) {

            // attempt detection of associative relationship involving the 2
            // classes
            List<Association> associations = new ArrayList<Association>();
            associations.addAll(c1.getAssociations());
            associations.addAll(c2.getAssociations());

            for (Association association : associations) {
                List<Class> associationEnds =
                        UML2ModelUtil.getAssociationEnds(association);
                if (associationEnds.contains(c1)
                        && associationEnds.contains(c2)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * @param c1
     * @param c2
     * @return <code>true</code> if passed in classifiers come from the same BOM
     *         resource and participate in a common generalisation relationship.
     *         <code>false</code> otherwise or if any of the classifier's
     *         resources cannot be determined.
     */
    public static boolean classesOfIntraBomGeneralisation(final Classifier c1,
            final Classifier c2) {

        if (hasSameBomResource(c1, c2)) {

            // attempt detection of generalisation relationship involving the 2
            // classes
            List<Generalization> generalisations =
                    new ArrayList<Generalization>();
            generalisations.addAll(c1.getGeneralizations());
            generalisations.addAll(c2.getGeneralizations());

            for (Generalization generalisation : generalisations) {
                Classifier specific = generalisation.getSpecific();
                Classifier general = generalisation.getGeneral();
                if ((c1.equals(specific) && c2.equals(general))
                        || (c1.equals(general) && c2.equals(specific))) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * @param classifiers
     * @return <code>true</code> if all passed in classifiers come from the same
     *         BOM resource. <code>false</code> otherwise or if any of the
     *         classifier's resources cannot be determined.
     */
    public static boolean hasSameBomResource(final Classifier... classifiers) {

        Iterator<Classifier> it = Arrays.asList(classifiers).iterator();

        if (it.hasNext()) {
            for (Classifier c = null, prev = it.next(); it.hasNext(); prev = c) {
                c = it.next();
                if (c == null || prev == null) {
                    return false;
                } else if (!isSameResource(prev.eResource(), c.eResource())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Compares two elements and returns if they are in the same project
     * 
     * @param whole
     * @param part
     * @return <code>true</code> if the passed in classifiers come from the same
     *         project.
     */
    public static boolean hasSameProject(EModelElement whole, EModelElement part) {
        // In the case where there is a reference to a closed project the
        // following methods will return null, so we will check for null, but if
        // they are we will return false as they can not be in the same project,
        // in the case of doing operations, at least one must be open
        IProject wholeProject = WorkingCopyUtil.getProjectFor(whole);
        IProject partProject = WorkingCopyUtil.getProjectFor(part);

        if ((wholeProject != null) && (partProject != null)) {
            if (wholeProject.getName().compareTo(partProject.getName()) == 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param generalization
     * @return <code>true</code> if relationship is found to involve
     *         {@link Class}es residing in separate BOMs.
     */
    public static boolean isIntraBomRelationship(Generalization generalization) {

        Resource genResource = generalization.getGeneral().eResource();
        Resource specificResource = generalization.getSpecific().eResource();

        return isSameResource(genResource, specificResource);
    }

    /**
     * @param association
     * @return <code>true</code> if relationship is found to involve
     *         {@link Class}es residing in different BOMs.
     */
    public static boolean isIntraBomRelationship(Association association) {

        List<Class> associationEnds =
                UML2ModelUtil.getAssociationEnds(association);

        if (associationEnds.size() >= 2) {
            Resource end0_resource = associationEnds.get(0).eResource();
            Resource end1_resource = associationEnds.get(1).eResource();
            return isSameResource(end0_resource, end1_resource);
        }

        return false;
    }

    /**
     * @param r0
     * @param r1
     * @return
     */
    private static boolean isSameResource(Resource r0, Resource r1) {
        if (r0 != null && r1 != null) {
            URI r0uri = r0.getURI();
            URI r1uri = r1.getURI();
            if (r0uri != null && r1uri != null) {
                return r0uri.equals(r1uri);
            }
        }
        return false;
    }

    /**
     * Find a unique model name based on the name given. If the given name is
     * not unique then an numeric suffix will be added to make it unique.
     * 
     * @param modelName
     * @return
     * @since 3.5
     */
    public static String createUniqueModelName(String modelName) {
        if (modelName != null) {
            BOMIndexerService service =
                    BOMResourcesPlugin.getDefault().getIndexerService();
            if (service != null) {
                IndexerItem[] items = service.getAllModels();
                if (items.length > 0) {
                    Set<String> names = new HashSet<String>();
                    for (IndexerItem item : items) {
                        names.add(item.getName());
                    }
                    if (names.contains(modelName)) {
                        // Name is not unique so make it unique
                        int idx = 1;
                        String origName = modelName;
                        while (names.contains(modelName)) {
                            modelName = origName + String.valueOf(idx++);
                        }
                    }
                }
            }
        }

        return modelName;
    }

    /**
     * Get the {@link Model} from the BOM file given.
     * 
     * @param bomFile
     * @return <code>Model</code> or <code>null</code> if this is not a BOM file
     *         or does not contain a {@link Model} as the top-level element.
     * @since 3.5.2
     */
    public static Model getModel(IFile bomFile) {
        Model model = null;

        if (bomFile != null) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(bomFile);
            if (wc != null && wc.getRootElement() instanceof Model) {
                model = (Model) wc.getRootElement();
            }
        }

        return model;
    }

    /**
     * @param profileExts
     * @param excludedExts
     * @return a sorted list of profile extensions excluding those specified
     */
    public static List<IFirstClassProfileExtension> getCreatableProfileExtensions(
            List<IFirstClassProfileExtension> profileExts,
            Set<String> excludedExts) {

        List<IFirstClassProfileExtension> ret =
                new ArrayList<IFirstClassProfileExtension>();

        if (profileExts != null) {
            for (IFirstClassProfileExtension profileExt : profileExts) {
                if (!excludedExts.contains(profileExt.getId())) {
                    ret.add(profileExt);
                }
            }

            // Sort the first-class profile extensions - by label
            Collections.sort(ret,
                    new Comparator<IFirstClassProfileExtension>() {
                        @Override
                        public int compare(IFirstClassProfileExtension o1,
                                IFirstClassProfileExtension o2) {
                            return o1.getLabel().compareTo(o2.getLabel());
                        }
                    });
        }

        return ret;
    }

}
