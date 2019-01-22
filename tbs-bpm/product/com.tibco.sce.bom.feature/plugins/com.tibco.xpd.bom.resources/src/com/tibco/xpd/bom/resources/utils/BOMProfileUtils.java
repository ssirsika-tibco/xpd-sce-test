package com.tibco.xpd.bom.resources.utils;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Extension;
import org.eclipse.uml2.uml.Image;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;

import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * @author rgreen
 * 
 */
public class BOMProfileUtils {

    private static final String PATHMAP_GDATA_PROFILES_BOM_GLOBAL_DATA_PROFILE_UML =
            "pathmap://GLOBALDATA_PROFILES/BOMGlobalData.profile.uml";

    /**
     * 
     * Checks if the UML2 profile existing at the location specified by the
     * fileNameURI parameter, is applied to the Model.
     * 
     * @param model
     * @param fileNameURI
     * @return boolean
     */
    public static boolean isProfileAppliedToModel(Model model,
            String fileNameURI) {
        return (isProfileAppliedToPackage(model, fileNameURI));
    }

    /**
     * 
     * Checks if the UML2 profile existing at the location specified by the
     * fileNameURI parameter, is applied to the Package.
     * 
     * @param model
     * @param fileNameURI
     * @return boolean
     */
    public static boolean isProfileAppliedToPackage(Package pkg,
            String fileNameURI) {

        if (pkg == null) {
            return false;
        }

        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();

        Resource res = ed.loadResource(fileNameURI); //$NON-NLS-1$
        Profile profile = null;

        if (res != null) {
            EObject object = res.getContents().get(0);

            if (object instanceof Profile) {
                profile = (Profile) object;
            }
        }

        return pkg.isProfileApplied(profile);

    }

    /**
     * @param model
     * @return boolean
     */
    public static boolean isXSDProfileApplied(Model model) {

        if (model == null) {
            return false;
        }

        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();

        Resource res =
                ed.loadResource("pathmap://XSD_NOTATION_TYPES/XsdNotation.profile.uml"); //$NON-NLS-1$
        Profile conceptProfile = null;

        if (res != null) {
            EObject object = res.getContents().get(0);

            if (object instanceof Profile) {
                conceptProfile = (Profile) object;
            }
        }

        if (model.getAppliedProfiles().contains(conceptProfile)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * for a given bom model return the xsd notation profile if it is applied or
     * null
     * 
     * @param model
     * @return
     */
    public static Profile getXSDProfile(Model model) {
        if (null == model) {
            return null;
        }
        TransactionalEditingDomain editingDomain =
                TransactionUtil.getEditingDomain(model);
        if (editingDomain != null) {
            Resource resource =
                    editingDomain
                            .loadResource("pathmap://XSD_NOTATION_TYPES/XsdNotation.profile.uml"); //$NON-NLS-1$

            if (null != resource) {
                EObject eObject = resource.getContents().get(0);

                if (eObject instanceof Profile) {

                    Profile xsdProfile = (Profile) eObject;
                    if (model.getAppliedProfiles().contains(xsdProfile)) {
                        return xsdProfile;
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param model
     * @return
     */
    public static boolean isConceptProfileApplied(Model model) {

        if (model == null) {
            return false;
        }

        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();

        Resource res =
                ed.loadResource("pathmap://BE3_PROFILES/Concepts3.profile.uml"); //$NON-NLS-1$
        Profile conceptProfile = null;

        if (res != null && res.getContents() != null
                && !res.getContents().isEmpty()) {
            EObject object = res.getContents().get(0);

            if (object instanceof Profile) {
                conceptProfile = (Profile) object;
            }
        }

        if (model.getAppliedProfiles().contains(conceptProfile)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * @return
     */
    public static Profile getConceptsProfile() {

        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();

        Resource res =
                ed.loadResource("pathmap://BE3_PROFILES/Concepts3.profile.uml"); //$NON-NLS-1$
        Profile conceptProfile = null;

        if (res != null) {
            EObject object = res.getContents().get(0);

            if (object instanceof Profile) {
                conceptProfile = (Profile) object;
                return conceptProfile;
            }
        }

        return null;

    }

    /**
     * @return
     */
    public static Profile getGlobalDataProfile() {

        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();

        return getProfileFromURI(ed,
                URI.createURI(PATHMAP_GDATA_PROFILES_BOM_GLOBAL_DATA_PROFILE_UML, //$NON-NLS-1$
                        true));

    }

    /**
     * @param type
     * @param profile
     * @return
     */
    public static String getStereotypeIconURI(EClass type, Profile profile) {
        String imgURI = null;
        EList<Extension> ownedext = profile.getOwnedExtensions(true);

        for (Extension extension : ownedext) {
            Class cls = extension.getMetaclass();
            boolean imgFound = false;

            if (cls.getName() != null && cls.getName().equals(type.getName())) {
                // This is an extension of a Class
                EList<Image> lstIcons = extension.getStereotype().getIcons();
                if (!lstIcons.isEmpty()) {
                    imgURI = lstIcons.get(0).getLocation();
                    imgFound = true;
                    break;
                }
            }
            if (imgFound == true) {
                break;
            }
        }
        return imgURI;
    }

    /**
     * 
     * Return Profile object given its URI
     * 
     * @param TransactionalEditingDomain
     * @param URI
     * @return Profile
     */
    public static Profile getProfileFromURI(TransactionalEditingDomain ed,
            URI profileURI) {

        Profile prof = null;
        Resource res = ed.getResourceSet().getResource(profileURI, true);

        if (res != null) {
            EObject object = res.getContents().get(0);

            if (object instanceof Profile) {
                prof = (Profile) object;
            }
        }

        return prof;

    }

    /**
     * 
     * Returns true if the supplied profile is the XSD Notation profile.
     * 
     * @param profile
     * @return boolean
     */
    public static boolean isProfileXSDProfile(Profile profile) {
        if (profile == null) {
            return false;
        }

        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();

        Resource res =
                ed.loadResource("pathmap://XSD_NOTATION_TYPES/XsdNotation.profile.uml"); //$NON-NLS-1$
        Profile xsdProfile = null;

        if (res != null) {
            EObject object = res.getContents().get(0);

            if (object instanceof Profile) {
                xsdProfile = (Profile) object;
            }
        }

        if (profile == xsdProfile) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 
     * Returns true if the supplied profile is the XSD Notation profile.
     * 
     * @param profile
     * @return boolean
     */
    public static boolean isProfileGlobalDataProfile(Profile profile) {
        if (profile == null) {
            return false;
        }

        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();
        Resource res =
                ed.loadResource(PATHMAP_GDATA_PROFILES_BOM_GLOBAL_DATA_PROFILE_UML); //$NON-NLS-1$
        Profile xsdProfile = null;

        if (res != null) {
            if (res.getContents() != null) {
                EObject object = res.getContents().get(0);
                if (object instanceof Profile) {
                    xsdProfile = (Profile) object;
                    if (profile == xsdProfile) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param model
     * @return
     */
    public static boolean isGlobalDataProfileApplied(Model model) {

        boolean isProfApplied = false;

        Profile gDataProfile = getGlobalDataProfile();

        if (gDataProfile != null) {
            if (model.getAppliedProfiles().contains(gDataProfile)) {
                isProfApplied = true;
            }
        }

        return isProfApplied;
    }

}
