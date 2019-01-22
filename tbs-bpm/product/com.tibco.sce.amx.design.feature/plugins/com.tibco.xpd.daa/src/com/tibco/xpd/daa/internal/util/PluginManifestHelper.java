/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.daa.internal.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.service.resolver.BundleDescription;
import org.eclipse.osgi.service.resolver.BundleSpecification;
import org.eclipse.osgi.service.resolver.State;
import org.eclipse.osgi.service.resolver.StateObjectFactory;
import org.eclipse.osgi.service.resolver.VersionRange;
import org.eclipse.osgi.util.ManifestElement;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;
import org.eclipse.uml2.uml.Package;
import org.osgi.framework.BundleException;
import org.osgi.framework.Version;

import com.tibco.amf.tools.packager.util.PackagerUtils;
import com.tibco.xpd.bom.resources.utils.BOMIndexerService;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.daa.DaaActivator;
import com.tibco.xpd.daa.internal.IChangeRecorder;
import com.tibco.xpd.daa.internal.ProjectInfo;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * @author kupadhya
 * 
 */
public class PluginManifestHelper {
    private static final Logger LOG = DaaActivator.getDefault().getLogger();

    private static final String MANIFEST_PATH = "META-INF/MANIFEST.MF"; //$NON-NLS-1$

    private static final String MF_REQUIRE_BUNDLE = "Require-Bundle"; //$NON-NLS-1$

    public static final String PROPERTY_QUALIFIER = "qualifier"; //$NON-NLS-1$

    private static final String BUNDLE_VERSION = "Bundle-Version"; //$NON-NLS-1$

    private static final String BUNDLE_CLASSPATH = "Bundle-ClassPath"; //$NON-NLS-1$

    private static final String EXPORT_PACKAGE = "Export-Package"; //$NON-NLS-1$    

    private static final String IMPORT_PACKAGE = "Import-Package"; //$NON-NLS-1$

    private static final String REQUIRE_BUNDLE_SEPARATOR = ","; //$NON-NLS-1$

    private static final String qualifierWithDollar = PROPERTY_QUALIFIER + "$"; //$NON-NLS-1$

    private static final String BundleSymbolicName = "Bundle-SymbolicName"; //$NON-NLS-1$

    public static IFile getManifestFile(IProject project) {
        IResource resource = project.findMember(MANIFEST_PATH);
        IFile file = (IFile) resource;
        return file;
    }

    /**
     * 
     * @param project
     * @param newQualifier
     * @param recordChanges
     *            whether the changes need to be recorded using thread local
     *            variable
     */
    public static void replaceBundleManifestQualifierWithTS(IProject project,
            String newQualifier, Boolean recordChanges,
            IChangeRecorder changeRecorder) {
        replaceBundleManifestQualifierWithTS(project,
                newQualifier,
                recordChanges,
                changeRecorder,
                false);
    }

    /**
     * 
     * @param project
     * @param newQualifier
     * @param recordChanges
     *            whether the changes need to be recorded using thread local
     *            variable
     * @param doingRevert
     *            true if doing a revert of timestamp back
     */
    private static void replaceBundleManifestQualifierWithTS(IProject project,
            String newQualifier, Boolean recordChanges,
            IChangeRecorder changeRecorder, boolean doingRevert) {

        if (newQualifier == null || newQualifier.trim().length() < 1) {
            return;
        }
        IFile manifestFile = getManifestFile(project);
        if (manifestFile == null || !manifestFile.isAccessible()) {
            return;
        }
        ByteArrayOutputStream stream = null;
        ByteArrayInputStream byteArrayInputStream = null;
        InputStream is = null;
        boolean bundleVersionChanged = false;
        boolean requiredBundleVersionChanged = false;
        boolean exportPackageVersionChanged = false;
        boolean importPackageVersionChanged = false;
        String bundleId = null;
        try {
            is = manifestFile.getContents();
            Manifest manifest = new Manifest(is);
            Attributes attrs = manifest.getMainAttributes();
            bundleId = attrs.getValue(BundleSymbolicName);
            int semiColonIndex = bundleId.indexOf(";"); //$NON-NLS-1$
            if (semiColonIndex != -1) {
                // handling Bundle-SymbolicName:
                // com.example.businessobjectmodel1;singleton:=false
                bundleId = bundleId.substring(0, semiColonIndex);
            }
            // replacing bundle version
            String bundleVersion = attrs.getValue(BUNDLE_VERSION);
            String updatedBundleVersion =
                    getUpdatedBundleVersion(bundleVersion, newQualifier);
            if (!bundleVersion.equals(updatedBundleVersion)) {
                attrs.putValue(BUNDLE_VERSION, updatedBundleVersion);
                if (recordChanges) {
                    changeRecorder
                            .recordProjectVersionChange(project.getName(),
                                    bundleVersion);
                }
                bundleVersionChanged = true;
            }
            // replacing export package version
            String exportPackageValue = attrs.getValue(EXPORT_PACKAGE);
            if (exportPackageValue != null
                    && exportPackageValue.trim().length() > 0) {
                String updatedExportPackageValue =
                        getUpdatedImportExportPackageVersion(project,
                                exportPackageValue,
                                newQualifier);
                if (!exportPackageValue.trim()
                        .equals(updatedExportPackageValue.trim())) {
                    attrs.putValue(EXPORT_PACKAGE, updatedExportPackageValue);
                    exportPackageVersionChanged = true;
                }
            }
            // replacing import package version
            String importPackageValue = attrs.getValue(IMPORT_PACKAGE);
            if (importPackageValue != null
                    && importPackageValue.trim().length() > 0) {
                String updatedImportPackageValue =
                        getUpdatedImportExportPackageVersion(project,
                                importPackageValue,
                                newQualifier);
                if (!importPackageValue.trim()
                        .equals(updatedImportPackageValue.trim())) {
                    attrs.putValue(IMPORT_PACKAGE, updatedImportPackageValue);
                    importPackageVersionChanged = true;
                }
            }
            // replacing require bundle version
            String requiredBundleValue = attrs.getValue(MF_REQUIRE_BUNDLE);
            if (requiredBundleValue != null
                    && requiredBundleValue.trim().length() > 0) {
                String updatedRequiredBundleVersion =
                        getUpdatedRequiredBundleVersion2(project,
                                requiredBundleValue,
                                newQualifier,
                                recordChanges,
                                changeRecorder,
                                doingRevert);
                if (!requiredBundleValue.trim()
                        .equals(updatedRequiredBundleVersion.trim())) {
                    attrs.putValue(MF_REQUIRE_BUNDLE,
                            updatedRequiredBundleVersion);
                    requiredBundleVersionChanged = true;
                }
            }

            if (bundleVersionChanged || requiredBundleVersionChanged
                    || exportPackageVersionChanged
                    || importPackageVersionChanged) {
                stream = new ByteArrayOutputStream();
                manifest.write(stream);
                byte[] array = stream.toByteArray();
                byteArrayInputStream = new ByteArrayInputStream(array);
                manifestFile.setContents(byteArrayInputStream,
                        true,
                        false,
                        null);
            }
        } catch (CoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (byteArrayInputStream != null) {
                    byteArrayInputStream.close();
                }
                if (stream != null) {
                    stream.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (bundleVersionChanged || requiredBundleVersionChanged
                || exportPackageVersionChanged || importPackageVersionChanged) {
            replaceCachedBundleQualifierWithTS(project,
                    newQualifier,
                    bundleId,
                    doingRevert);
        }
    }

    /**
     * updates bundle description & update the containing site
     * 
     * @param project
     * @param timeStamp
     * @param bundleId
     * @param doingRevert
     *            true if doing a revert of timestamp back
     */
    private static void replaceCachedBundleQualifierWithTS(IProject project,
            String timeStamp, String bundleId, boolean doingRevert) {
        List<IPluginModelBase> modelsFor = getWorkspaceModelsFor(bundleId);
        if (modelsFor.size() < 1) {
            return;
        }
        IPluginModelBase pluginModelBase = modelsFor.get(0);
        BundleDescription bundleDescription =
                pluginModelBase.getBundleDescription();

        IFile manifestFile = getManifestFile(project);
        if (manifestFile == null || !manifestFile.isAccessible()) {
            return;
        }
        InputStream contents = null;
        try {
            contents = manifestFile.getContents();
            Map parseBundleManifest =
                    ManifestElement.parseBundleManifest(contents, null);
            Dictionary<String, String> headerProperties =
                    new Hashtable<String, String>();
            Set keySet = parseBundleManifest.keySet();
            for (Object keyObj : keySet) {
                headerProperties.put((String) keyObj,
                        (String) parseBundleManifest.get(keyObj));
            }
            State containingState = bundleDescription.getContainingState();
            BundleDescription updatedBD =
                    StateObjectFactory.defaultFactory
                            .createBundleDescription(containingState,
                                    headerProperties,
                                    bundleDescription.getLocation(),
                                    bundleDescription.getBundleId());
            pluginModelBase.setBundleDescription(updatedBD);
            containingState.updateBundle(updatedBD);
        } catch (CoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BundleException e) {
            e.printStackTrace();
        } finally {
            try {
                if (contents != null) {
                    contents.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //private final static String str = "(.*?)\"(.*?)\""; //$NON-NLS-1$
    private final static String str = "(.*?);(.*?)\"(.*?)\""; //$NON-NLS-1$

    private static final Pattern pattern = Pattern.compile(str);

    private static String getUpdatedRequiredBundleVersion(IProject project,
            String requiredBundleValue, String newQualifier,
            boolean recordChanges, IChangeRecorder changeRecorder) {
        String updatedRequiredBundleValue = requiredBundleValue;
        List<String> bundleList = new ArrayList<String>();
        String[] split = requiredBundleValue.split(REQUIRE_BUNDLE_SEPARATOR);
        for (int index = 0; index < split.length; index++) {
            String eachBundle = split[index];
            Matcher matcher = pattern.matcher(eachBundle);
            if (matcher.matches()) {
                String bundleId = matcher.group(1);
                String rqdBundleStr = matcher.group(2);
                String oldVersionStr = matcher.group(3);
                IProject workspaceProject = getWorkspaceProject(bundleId);
                if (workspaceProject == null) {
                    bundleList.add(eachBundle);
                    continue;
                }
                String newVersionStr =
                        createNewVersion(oldVersionStr, newQualifier);
                if (!oldVersionStr.equals(newVersionStr)) {
                    String newBundleId = bundleId + ";" + rqdBundleStr + "\"" //$NON-NLS-1$ //$NON-NLS-2$
                            + newVersionStr.trim() + "\""; //$NON-NLS-1$
                    bundleList.add(newBundleId);
                } else {
                    bundleList.add(eachBundle);
                }
                PluginManifestHelper
                        .replaceBundleManifestQualifierWithTS(workspaceProject,
                                newQualifier,
                                recordChanges,
                                changeRecorder);
            } else {
                // it could be a workspace bundle without version value
                bundleList.add(eachBundle);
                IProject workspaceProject = getWorkspaceProject(eachBundle);
                if (workspaceProject != null && workspaceProject.isAccessible()) {
                    // replace qualifier of the required bundle
                    PluginManifestHelper
                            .replaceBundleManifestQualifierWithTS(workspaceProject,
                                    newQualifier,
                                    recordChanges,
                                    changeRecorder);
                }

            }
        }
        String newValue = ""; //$NON-NLS-1$
        for (int index = 0; index < bundleList.size(); index++) {
            String string = bundleList.get(index);
            newValue = newValue + string + REQUIRE_BUNDLE_SEPARATOR;
        }
        if (newValue.endsWith(REQUIRE_BUNDLE_SEPARATOR)) {
            String substring = newValue.substring(0, newValue.length() - 1);
            return substring;
        }
        return updatedRequiredBundleValue;
    }

    static final Pattern pluginPattern3 =
            Pattern.compile("((\\w+.*?)(?:(;bundle-version\\s*=\\s*\")([\\[(]?.*?[\\])]?)(\"))?),");

    private static final String BDS_BUNDLE_SUFFIX = ".bds"; //$NON-NLS-1$

    /**
     * org.eclipse.ui.navigator;bundle-version="3.3.100",
     * com.tibco.amf.tools.packager,
     * com.tibco.xpd.analyst.resources.xpdl2;bundle-version="[3.5.1,3.5.1]",
     * com.tibco.xpd.analyst.resources.xpdl2;bundle-version="(3.5.1,3.5.1]",
     * com.tibco.xpd.analyst.resources.xpdl2;bundle-version="[3.5.1,3.5.1)",
     * com.tibco.xpd.analyst.resources.xpdl3;bundle-version=
     * "[3.5.1.qualifier,3.5.1.qualifier]",
     * com.tibco.xpd.analyst.resources.xpdl4
     * ;bundle-version="(3.5.1.qualifier,3.5.1.qualifier]",
     * com.tibco.xpd.analyst
     * .resources.xpdl4;bundle-version="[3.5.1.qualifier,3.5.1.qualifier)"
     * 
     * @param project
     * @param requiredBundleValue
     * @param newQualifier
     * @param recordChanges
     * @param doingRevert
     *            true if reverting timsestamp qualifier with original
     *            qualifier.
     * 
     * @return
     */
    private static String getUpdatedRequiredBundleVersion2(IProject project,
            String requiredBundleValue, String newQualifier,
            boolean recordChanges, IChangeRecorder changeRecorder,
            boolean doingRevert) {
        String updatedRequiredBundleValue = requiredBundleValue;
        updatedRequiredBundleValue =
                updatedRequiredBundleValue + REQUIRE_BUNDLE_SEPARATOR;
        Matcher matcher = pluginPattern3.matcher(updatedRequiredBundleValue);
        List<String> bundleList = new ArrayList<String>();
        while (matcher.find()) {
            String eachEntry = matcher.group(1);
            String bundleId = matcher.group(2);
            String middlePart = matcher.group(3);
            String versionOrRange = matcher.group(4);
            String lastQuotes = matcher.group(5);

            if (middlePart == null) {
                // case of com.tibco.amf.tools.packager,
                bundleList.add(bundleId);
            }
            if (versionOrRange != null && versionOrRange.trim().length() > 0) {
                /**
                 * If we are dealing with a requires bundle for a business data
                 * project, then we need to use its build version as qualifier.
                 */
                String overrideVersionForBizDataBds = null;

                if (bundleId.endsWith(BDS_BUNDLE_SUFFIX)) {
                    String bdsBundleId =
                            bundleId.substring(0, bundleId.length()
                                    - BDS_BUNDLE_SUFFIX.length());

                    IProject bomProject =
                            getSourceProjectFromBundleId(bdsBundleId);

                    if (bomProject != null) {
                        if (BOMUtils.isBusinessDataProject(bomProject)) {

                            if (doingRevert) {

                                /*
                                 * Revert back to original version for biz data
                                 * project (which is the project version itself).
                                 */
                                overrideVersionForBizDataBds =
                                        ProjectUtil
                                                .getProjectVersion(bomProject);
                            } else {

                                /* Get the project's current build version */
                                overrideVersionForBizDataBds =
                                        BOMUtils.getBusinessDataBuildVersion(bomProject);
                            }

                        }
                    }
                }

                boolean isRange =
                        versionOrRange.startsWith("(")
                                || versionOrRange.startsWith("[");
                if (isRange) {
                    // is range e.g.
                    // com.tibco.xpd.analyst.resources.xpdl2;bundle-version="[3.5.1.qualifier,3.5.1.qualifier)",
                    VersionRange versionRange =
                            new VersionRange(versionOrRange);
                    boolean isMinIncluded = versionRange.getIncludeMinimum();
                    boolean isMaxIncluded = versionRange.getIncludeMaximum();
                    Version minVersion = versionRange.getMinimum();
                    Version maxVersion = versionRange.getMaximum();
                    String minVersionString = minVersion.toString();
                    String maxVersionString = maxVersion.toString();

                    /**
                     * Use the Biz Data Project version if this is a biz data
                     * project else use the DAA generation time stamp passed in.
                     */

                    String newMinVersionStr =
                            overrideVersionForBizDataBds != null ? overrideVersionForBizDataBds
                                    : createNewVersion(minVersionString,
                                            newQualifier);

                    String newMaxVersionStr =
                            overrideVersionForBizDataBds != null ? overrideVersionForBizDataBds
                                    : createNewVersion(maxVersionString,
                                            newQualifier);

                    if (!(minVersionString.equals(newMinVersionStr) || maxVersionString
                            .equals(newMaxVersionStr))) {
                        String eachNewEntry =
                                bundleId
                                        + middlePart
                                        + new VersionRange(new Version(
                                                newMinVersionStr),
                                                isMinIncluded, new Version(
                                                        newMaxVersionStr),
                                                isMaxIncluded).toString()
                                        + lastQuotes;
                        bundleList.add(eachNewEntry);
                    } else {
                        bundleList.add(eachEntry);
                    }

                } else {
                    // not a range,
                    // org.eclipse.ui.navigator;bundle-version="3.3.100.qualifier",
                    /**
                     * Use the Biz Data Project version if this is a biz data
                     * project else use the DAA generation time stamp passed in.
                     */
                    String newVersionStr =
                            overrideVersionForBizDataBds != null ? overrideVersionForBizDataBds
                                    : createNewVersion(versionOrRange,
                                            newQualifier);

                    if (!versionOrRange.equals(newVersionStr)) {
                        String eachNewEntry =
                                bundleId + middlePart + newVersionStr.trim()
                                        + lastQuotes;
                        bundleList.add(eachNewEntry);
                    } else {
                        bundleList.add(eachEntry);
                    }
                }
            }
        }
        String newValue = ""; //$NON-NLS-1$
        for (int index = 0; index < bundleList.size(); index++) {
            String string = bundleList.get(index);
            newValue = newValue + string + REQUIRE_BUNDLE_SEPARATOR;
        }
        if (newValue.endsWith(REQUIRE_BUNDLE_SEPARATOR)) {
            String substring = newValue.substring(0, newValue.length() - 1);
            return substring;
        }
        return updatedRequiredBundleValue;
    }

    /**
     * Get the source XPD project (if there is one) for the given bundle's id IF
     * the bundleId is a BOM package id.
     * 
     * @param bundleId
     * @return The project with the given project id or <code>null</code> if not
     *         found or bundleId a BOM package id of a BOM in an XPD project.
     */
    private static IProject getSourceProjectFromBundleId(String bundleId) {

        Collection<Package> packageById =
                BOMIndexerService.getInstance().getPackageById(bundleId);

        if (packageById.size() > 1) {

            throw new RuntimeException(
                    "Detected more than one bundle with same Id: " + bundleId); //$NON-NLS-1$

        } else if (packageById.size() == 1) {

            Package pkg = packageById.iterator().next();
            return WorkingCopyUtil.getProjectFor(pkg);
        }

        return null;
    }

    private static IProject getWorkspaceProject(String bundleId) {
        String pluginProjectName = getPluginProjectName(bundleId);
        if (pluginProjectName != null && pluginProjectName.trim().length() > 0) {
            // looks like it is a workspace bundle
            IProject project = getProjectWithName(pluginProjectName);
            if (project != null && project.isAccessible()) {
                return project;
            }
        }
        return null;
    }

    private static String getUpdatedImportExportPackageVersion(
            IProject project, String exportedPackageValue, String newQualifier) {
        String updatedExportedPackageValue = exportedPackageValue;
        List<String> packageList = new ArrayList<String>();
        String[] split = exportedPackageValue.split(REQUIRE_BUNDLE_SEPARATOR);
        for (int index = 0; index < split.length; index++) {
            String eachPackage = split[index];
            Matcher matcher = pattern.matcher(eachPackage);
            if (matcher.matches()) {
                String packageId = matcher.group(1);
                String versionStr = matcher.group(2);
                String oldVersionStr = matcher.group(3);
                String newVersionStr =
                        createNewVersion(oldVersionStr, newQualifier);
                if (!oldVersionStr.equals(newVersionStr)) {
                    String newPackageId = packageId + ";" + versionStr + "\"" //$NON-NLS-1$ //$NON-NLS-2$
                            + newVersionStr.trim() + "\""; //$NON-NLS-1$
                    packageList.add(newPackageId);
                } else {
                    /*
                     * XPD-3207 if old and new qualifiers are same, package
                     * version should be taken as it is.
                     */
                    packageList.add(eachPackage);
                }
            } else {
                packageList.add(eachPackage);
            }
        }
        String newValue = ""; //$NON-NLS-1$
        for (int index = 0; index < packageList.size(); index++) {
            String string = packageList.get(index);
            newValue = newValue + string + REQUIRE_BUNDLE_SEPARATOR;
        }
        if (newValue.endsWith(REQUIRE_BUNDLE_SEPARATOR)) {
            String substring = newValue.substring(0, newValue.length() - 1);
            return substring;
        }
        return updatedExportedPackageValue;
    }

    /**
     * Replaces old qualifier with the new qualifier
     * 
     * New version should only be created when we want to replace the
     * 'qualifier' in the existing version with a time stamp or we want to
     * replace the timestamp in the existing version with a 'qualifier'
     * 
     * @param oldVersionStr
     * @param newQualifier
     * @return
     */
    private static String createNewVersion(String oldVersionStr,
            String newQualifier) {
        String newVersionStr = oldVersionStr;
        try {
            Version parseVersion = parseVersion(oldVersionStr);
            String originalQualifier = parseVersion.getQualifier();
            if (originalQualifier != null
                    && originalQualifier.trim().length() > 0) {
                boolean createNewVersion = false;
                if (PROPERTY_QUALIFIER.equalsIgnoreCase(originalQualifier)) {
                    createNewVersion = true;
                } else if (PROPERTY_QUALIFIER.equalsIgnoreCase(newQualifier)) {
                    boolean autogeneratedQualifier =
                            DAANamingUtils
                                    .isAutogeneratedQualifier(originalQualifier);
                    if (autogeneratedQualifier) {
                        createNewVersion = true;
                    }

                }
                if (createNewVersion) {
                    Version newVersion;
                    newVersion =
                            new Version(parseVersion.getMajor(),
                                    parseVersion.getMinor(),
                                    parseVersion.getMicro(), newQualifier);
                    newVersionStr = newVersion.toString();
                }
            }
        } catch (IllegalArgumentException e) {
            String message =
                    String.format("Version parse exception: oldVersionStr: [%1$s], newQualifier: [%2$s] ", //$NON-NLS-1$
                            oldVersionStr,
                            newQualifier);
            LOG.error(e, message);
            throw e;
        }
        return newVersionStr;
    }

    /**
     * 
     * 
     * @param qualifier
     * @return
     */
    private static boolean isOldTimeStamp(String qualifier) {
        boolean isOldTS = false;
        if (qualifier != null && qualifier.length() > 0) {
            isOldTS = DAANamingUtils.isAutogeneratedQualifier(qualifier);
        }
        return isOldTS;
    }

    public static String getUpdatedBundleVersion(String bundleVersion,
            String timeStamp) {
        String createNewVersion = bundleVersion;
        if (timeStamp != null && timeStamp.trim().length() > 0) {
            createNewVersion = createNewVersion(bundleVersion, timeStamp);
        }
        return createNewVersion;
    }

    public static String getQualifierReplacer(EObject impl) {
        String qualifier = PackagerUtils.getQualifier(impl);
        if (PROPERTY_QUALIFIER.equals(qualifier)) {
            return null;
        }
        return qualifier;
    }

    public static String replaceProjectVersionQualifierWithQualifierReplacer(
            IProject project, String qualifierReplacer) {
        String projectVersion = ProjectUtil.getProjectVersion(project);
        String projectVersionWithTimeStamp =
                replaceProjectVersionQualifierWithQualifierReplacer(projectVersion,
                        qualifierReplacer);
        return projectVersionWithTimeStamp;
    }

    public static String replaceProjectVersionQualifierWithQualifierReplacer(
            String projectVersion, String qualifierReplacer) {
        if (qualifierReplacer == null) {
            return projectVersion;
        }
        String projectVersionWithTimeStamp =
                projectVersion.replaceFirst(qualifierWithDollar,
                        qualifierReplacer);
        return projectVersionWithTimeStamp;
    }

    public static String replaceProjectVersionQualifierWithUpperQualifierReplacer(
            String projectVersion, String qualifierReplacer) {
        if (qualifierReplacer == null) {
            return projectVersion;
        }
        try {
            Long versionL = new Long(qualifierReplacer);
            versionL = versionL + 1;
            qualifierReplacer = versionL.toString();
        } catch (NumberFormatException nfe) {
        }
        String projectVersionWithTimeStamp =
                getUpdatedBundleVersion(projectVersion, qualifierReplacer);
        return projectVersionWithTimeStamp;
    }

    public static String getFeatureUpperRangeValue(String projectVersion,
            String qualifierReplacer) {
        if (qualifierReplacer == null || qualifierReplacer.length() < 1) {
            return projectVersion;
        }
        String updatedProjectVersion = projectVersion;
        try {
            String strLastDigit =
                    qualifierReplacer.substring(qualifierReplacer.length() - 1);
            String startBit =
                    qualifierReplacer.substring(0,
                            qualifierReplacer.length() - 1);
            Long versionL = new Long(strLastDigit);
            // versionL = versionL + 1;
            updatedProjectVersion = startBit + versionL.toString();
        } catch (NumberFormatException nfe) {
        }
        String projectVersionWithTimeStamp =
                getUpdatedBundleVersion(projectVersion, updatedProjectVersion);
        return projectVersionWithTimeStamp;
    }

    public static List<IPluginModelBase> getWorkspaceModelsFor(String id) {
        List<IPluginModelBase> retVal = new ArrayList<IPluginModelBase>();
        List<IPluginModelBase> buf = getWorkspaceBundles().get(id);
        if (buf != null) {
            retVal.addAll(buf);
        }
        return retVal;
    }

    private static Map<String, List<IPluginModelBase>> getWorkspaceBundles() {
        Map<String, List<IPluginModelBase>> workSpaceBundles =
                new HashMap<String, List<IPluginModelBase>>();
        IPluginModelBase[] workspaceModels =
                PluginRegistry.getWorkspaceModels();

        for (IPluginModelBase pm : workspaceModels) {
            BundleDescription bundleDescription = pm.getBundleDescription();
            if (bundleDescription != null) {
                String symbolicName = bundleDescription.getSymbolicName();

                List<IPluginModelBase> list =
                        workSpaceBundles.get(symbolicName);
                if (list == null) {
                    list = new ArrayList<IPluginModelBase>();
                    workSpaceBundles.put(symbolicName, list);
                }
                list.add(pm);
            }
        }
        return workSpaceBundles;
    }

    /**
     * At end of deploy revert changed timestamps back to the original version
     * listed in project info.
     * 
     * @param eachProjectInfo
     * @param changeRecorder
     */
    public static void revertBundleManifestTImestamptToOriginal(
            ProjectInfo eachProjectInfo, IChangeRecorder changeRecorder) {
        String projectName = eachProjectInfo.getProjectName();
        IProject project =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(projectName);
        if (project == null || !project.isAccessible()) {
            return;
        }
        IFile manifestFile = getManifestFile(project);
        if (manifestFile == null || !manifestFile.isAccessible()) {
            return;
        }
        String originalProjectVersion =
                eachProjectInfo.getOriginalProjectVersion();
        Version originalVersion = parseVersion(originalProjectVersion);
        String originalQualifier = originalVersion.getQualifier();
        // revert changes made to manifest file & Plugin registry
        replaceBundleManifestQualifierWithTS(project,
                originalQualifier,
                Boolean.FALSE,
                changeRecorder,
                true);
    }

    private final static String strBundleId = "(.*?)\"(.*?)\""; //$NON-NLS-1$

    private static final Pattern dependencyBundlePattern = Pattern
            .compile(strBundleId);

    public static PluginProjectDetails getPluginProjectDetails(IProject project) {
        PluginManifestHelper.PluginProjectDetails pluginProjectDetails =
                new PluginManifestHelper.PluginProjectDetails();

        if (project == null || !project.isAccessible()) {
            return pluginProjectDetails;
        }
        String projectName = project.getName();
        pluginProjectDetails.setProjectName(projectName);
        pluginProjectDetails.setBundleId(projectName);
        IFile manifestFile = getManifestFile(project);
        if (manifestFile == null || !manifestFile.isAccessible()) {
            return pluginProjectDetails;
        }
        InputStream contents = null;
        try {
            contents = manifestFile.getContents();
            Map parseBundleManifest =
                    ManifestElement.parseBundleManifest(contents, null);
            String bundleId =
                    (String) parseBundleManifest.get(BundleSymbolicName);
            if (bundleId != null && bundleId.trim().length() > 0) {
                int semiColonIndex = bundleId.indexOf(";"); //$NON-NLS-1$
                if (semiColonIndex != -1) {
                    // handling Bundle-SymbolicName:
                    // com.example.businessobjectmodel1;singleton:=false
                    bundleId = bundleId.substring(0, semiColonIndex);
                }
                pluginProjectDetails.setBundleId(bundleId);
            }
            String bundleVersion =
                    (String) parseBundleManifest.get(BUNDLE_VERSION);
            if (bundleVersion != null && bundleVersion.trim().length() > 0) {
                pluginProjectDetails.setBundleVersion(bundleVersion);
            }
            // exported packages
            String exportPackageValue =
                    (String) parseBundleManifest.get(EXPORT_PACKAGE);
            if (exportPackageValue != null
                    && exportPackageValue.trim().length() > 0) {
                String[] split =
                        exportPackageValue.split(REQUIRE_BUNDLE_SEPARATOR);
                for (int index = 0; index < split.length; index++) {
                    String eachPackage = split[index];
                    Matcher matcher = pattern.matcher(eachPackage);
                    if (matcher.matches()) {
                        String packageId = matcher.group(1);
                        pluginProjectDetails.getExportedPackages()
                                .add(packageId);
                    }
                }
            }
            // exported packages
            // work out a list of require bundles
            if (bundleId != null && bundleId.trim().length() > 0) {
                List<IPluginModelBase> workspaceModelsFor =
                        getWorkspaceModelsFor(bundleId);
                IPluginModelBase pluginModelBase = workspaceModelsFor.get(0);
                BundleDescription bundleDescription =
                        pluginModelBase.getBundleDescription();
                BundleSpecification[] requiredBundles =
                        bundleDescription.getRequiredBundles();
                for (int index = 0; index < requiredBundles.length; index++) {
                    BundleSpecification eachBS = requiredBundles[index];
                    String symbolicName = eachBS.getName();
                    String eachProjectName = getPluginProjectName(symbolicName);
                    if (eachProjectName == null) {
                        continue;
                    }
                    IProject eachProject = getProjectWithName(eachProjectName);
                    if (eachProject == null || !eachProject.isAccessible()) {
                        continue;
                    }
                    PluginProjectDetails eachPluginProjectDetails =
                            getPluginProjectDetails(eachProject);
                    pluginProjectDetails.getProjectDependencyList()
                            .add(eachPluginProjectDetails);
                }
            }
            // work out a list of require bundles
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BundleException e) {
            e.printStackTrace();
        } catch (CoreException e) {
            e.printStackTrace();
        } finally {
            try {
                if (contents != null) {
                    contents.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return pluginProjectDetails;
    }

    /**
     * Returns a project name for a given plugin id
     * 
     * @param plugId
     * @return
     */
    public static String getPluginProjectName(String plugId) {
        if (plugId == null) {
            return plugId;
        }
        List<IPluginModelBase> workspaceModelsFor =
                PluginManifestHelper.getWorkspaceModelsFor(plugId);
        if (workspaceModelsFor.isEmpty()) {
            return null;
        }
        IPluginModelBase pluginModelBase = workspaceModelsFor.get(0);
        if (pluginModelBase == null) {
            return null;
        }
        String projectName =
                pluginModelBase.getUnderlyingResource().getProject().getName();
        return projectName;
    }

    public static IProject getProjectWithName(String projectName) {
        if (projectName == null || projectName.trim().length() < 1) {
            return null;
        }
        IProject project =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(projectName);
        return project;
    }

    public static class PluginProjectDetails {
        private String bundleId;

        private String projectName;

        private String bundleVersion;

        /**
         * represent a list of bundles on which said bundle depends upon
         */
        private List<PluginProjectDetails> projectDependencyList =
                new ArrayList<PluginProjectDetails>();

        private List<String> exportedPackages = new ArrayList<String>();

        /**
         * @return the projectDependencyList
         */
        public List<PluginProjectDetails> getProjectDependencyList() {

            return projectDependencyList;
        }

        /**
         * @return the projectId
         */
        public String getBundleId() {
            return bundleId;
        }

        /**
         * @param bundleId
         *            the projectId to set
         */
        public void setBundleId(String bundleId) {
            this.bundleId = bundleId;
        }

        /**
         * @return the projectName
         */
        public String getProjectName() {
            return projectName;
        }

        /**
         * @param projectName
         *            the projectName to set
         */
        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        /**
         * @return the projectVersion
         */
        public String getBundleVersion() {
            return bundleVersion;
        }

        /**
         * @param bundleVersion
         *            the projectVersion to set
         */
        public void setBundleVersion(String bundleVersion) {
            this.bundleVersion = bundleVersion;
        }

        public List<String> getExportedPackages() {
            return exportedPackages;
        }

    }

    /**
     * Starting from the passed PluginProjectDetails, works out a flat list
     * 
     * @param projectDetails
     * @param flatList
     */
    public static void flattenProjectDetailsList(
            PluginProjectDetails projectDetails,
            Set<PluginProjectDetails> flatList) {
        if (projectDetails == null) {
            return;
        }
        flatList.add(projectDetails);
        List<PluginProjectDetails> projectDependencyList =
                projectDetails.getProjectDependencyList();
        if (projectDependencyList != null) {
            for (PluginProjectDetails eachPluginProjectDetails : projectDependencyList) {
                flattenProjectDetailsList(eachPluginProjectDetails, flatList);
            }
        }
    }

    /**
     * Returns a list of exported packages
     * 
     * @param projectName
     * @return
     */
    public static List<String> getExportedPackages(IProject project) {
        List<String> exportedPackages = new ArrayList<String>();
        if (project == null || !project.isAccessible()) {
            return null;
        }
        IFile manifestFile = getManifestFile(project);
        if (manifestFile == null || !manifestFile.isAccessible()) {
            return null;
        }
        InputStream is = null;
        try {
            is = manifestFile.getContents();
            Manifest manifest = new Manifest(is);
            Attributes attrs = manifest.getMainAttributes();
            String exportPackageValue = attrs.getValue(EXPORT_PACKAGE);
            if (exportPackageValue != null
                    && exportPackageValue.trim().length() > 0) {
                String[] split =
                        exportPackageValue.split(REQUIRE_BUNDLE_SEPARATOR);
                for (int index = 0; index < split.length; index++) {
                    String eachPackage = split[index];
                    Matcher matcher = pattern.matcher(eachPackage);
                    if (matcher.matches()) {
                        String packageId = matcher.group(1);
                        exportedPackages.add(packageId);
                    }
                }
            }
        } catch (CoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return exportedPackages;
    }

    /**
     * Updates the references of the host project to include the generated
     * project.
     * 
     * @param hostProject
     * @param refProject
     *            the bom file's generated project
     * @throws CoreException
     */
    private static void addProjectReference(IProject hostProject,
            IProject refProject) throws CoreException {
        if (hostProject != null && hostProject.exists() && refProject != null) {
            IProjectDescription description = hostProject.getDescription();
            if (description != null) {
                IProject[] referencedProjects =
                        description.getReferencedProjects();
                boolean alreadyReferenced = false;
                for (IProject ref : referencedProjects) {
                    if (ref.equals(refProject)) {
                        alreadyReferenced = true;
                        break;
                    }
                }
                if (!alreadyReferenced) {
                    IProject[] newList =
                            new IProject[referencedProjects.length + 1];
                    System.arraycopy(referencedProjects,
                            0,
                            newList,
                            0,
                            referencedProjects.length);
                    newList[referencedProjects.length] = refProject;
                    description.setReferencedProjects(newList);
                    hostProject.setDescription(description,
                            new NullProgressMonitor());
                }
            }
        }
    }

    /**
     * Updates the references of the host project to exclude the generated
     * project.
     * 
     * @param hostProject
     * @param refProject
     *            the be generated project
     * @throws CoreException
     */
    private static void removeProjectReference(IProject hostProject,
            IProject refProject) throws CoreException {
        if (hostProject != null && hostProject.exists() && refProject != null) {
            IProjectDescription description = hostProject.getDescription();
            if (description != null) {
                IProject[] referencedProjects =
                        description.getReferencedProjects();
                boolean referenced = false;
                for (IProject ref : referencedProjects) {
                    if (ref.equals(refProject)) {
                        referenced = true;
                        break;
                    }
                }
                if (referenced) {
                    List<IProject> newList = new ArrayList<IProject>();

                    for (IProject project : referencedProjects) {
                        if (!project.equals(refProject)) {
                            newList.add(project);
                        }
                    }

                    description.setReferencedProjects(newList
                            .toArray(new IProject[newList.size()]));
                    hostProject.setDescription(description,
                            new NullProgressMonitor());
                }
            }
        }
    }

    /**
     * @param version
     * @return
     */
    private static Version parseVersion(String version) {
        Version parseVersion = Version.parseVersion("1.0.0.qualifier");
        try {
            parseVersion = Version.parseVersion(version);
        } catch (IllegalArgumentException e) {
            // sometimes the version comes in concatenated like
            // 1.0.0.qualifier";version="1.0.0.qualifier so we need to account
            // for such cases
            // TODO fix why this is so in first place...
            String[] concatVersions = version.split(";");
            if (concatVersions.length > 1) {
                parseVersion =
                        Version.parseVersion(concatVersions[0]
                                .replace("\"", "")); //$NON-NLS-1$ //$NON-NLS-2$ 
            }
        }
        return parseVersion;
    }

    /**
     * Replaces all occurrences of "qualifier" in manifest's relevant versions.
     * 
     * @param manifest
     *            the manifest to modify.
     * @param newQualifier
     *            a new qualifier value to replace.
     * 
     * @param doingRevert
     *            true if doing a revert of timestamp back
     * 
     */
    private static void replaceQualifier(Manifest manifest,
            String newQualifier, boolean doingRevert) {
        Attributes attrs = manifest.getMainAttributes();
        String bundleId = attrs.getValue(BundleSymbolicName);
        int semiColonIndex = bundleId.indexOf(";"); //$NON-NLS-1$
        if (semiColonIndex != -1) {
            // handling Bundle-SymbolicName:
            // com.example.businessobjectmodel1;singleton:=false
            bundleId = bundleId.substring(0, semiColonIndex);
        }
        // replacing bundle version
        String bundleVersion = attrs.getValue(BUNDLE_VERSION);
        String updatedBundleVersion =
                getUpdatedBundleVersion(bundleVersion, newQualifier);
        if (!bundleVersion.equals(updatedBundleVersion)) {
            attrs.putValue(BUNDLE_VERSION, updatedBundleVersion);
        }
        // replacing export package version
        String exportPackageValue = attrs.getValue(EXPORT_PACKAGE);
        if (exportPackageValue != null
                && exportPackageValue.trim().length() > 0) {
            String updatedExportPackageValue =
                    getUpdatedImportExportPackageVersion(null,
                            exportPackageValue,
                            newQualifier);
            if (!exportPackageValue.trim()
                    .equals(updatedExportPackageValue.trim())) {
                attrs.putValue(EXPORT_PACKAGE, updatedExportPackageValue);
            }
        }
        // replacing import package version
        String importPackageValue = attrs.getValue(IMPORT_PACKAGE);
        if (importPackageValue != null
                && importPackageValue.trim().length() > 0) {
            String updatedImportPackageValue =
                    getUpdatedImportExportPackageVersion(null,
                            importPackageValue,
                            newQualifier);
            if (!importPackageValue.trim()
                    .equals(updatedImportPackageValue.trim())) {
                attrs.putValue(IMPORT_PACKAGE, updatedImportPackageValue);
            }
        }
        // replacing require bundle version
        String requiredBundleValue = attrs.getValue(MF_REQUIRE_BUNDLE);
        if (requiredBundleValue != null
                && requiredBundleValue.trim().length() > 0) {
            String updatedRequiredBundleVersion =
                    getUpdatedRequiredBundleVersion2(null,
                            requiredBundleValue,
                            newQualifier,
                            false,
                            null,
                            doingRevert);
            if (!requiredBundleValue.trim()
                    .equals(updatedRequiredBundleVersion.trim())) {
                attrs.putValue(MF_REQUIRE_BUNDLE, updatedRequiredBundleVersion);
            }
        }
    }

    /**
     * Replaces "qualifier" in jar's manifest during copy of a source jar to a
     * destination jar.
     * 
     * @param pluginSrcFile
     *            source jar file.
     * @param pluginDstFile
     *            destination jar file (can exist and will be overwritten).
     * @param qualifier
     *            new qualifier value.
     * @throws IOException
     * @param doingRevert
     *            true if doing a revert of timestamp back
     * 
     */
    public static void replaceQualifierInPluginJar(File pluginSrcFile,
            File pluginDstFile, String qualifier, boolean doingRevert)
            throws IOException {

        byte[] buffer = new byte[4096];
        JarFile jarFile = null;
        JarOutputStream jarOutputStream = null;

        try {
            jarFile = new JarFile(pluginSrcFile);
            Manifest manifest = jarFile.getManifest();
            replaceQualifier(manifest, qualifier, doingRevert);
            Enumeration<JarEntry> entries = jarFile.entries();
            jarOutputStream =
                    new JarOutputStream(new FileOutputStream(pluginDstFile),
                            manifest);
            // copy all current entries into the new jar
            while (entries.hasMoreElements()) {
                JarEntry nextEntry = entries.nextElement();
                // skip the entry named jarEntryName
                if (!MANIFEST_PATH.equals(nextEntry.getName())) {
                    // the next 3 lines of code are a work around for
                    // bug 4682202 in the java.sun.com bug parade, see:
                    // http://developer.java.sun.com/developer/bugParade/bugs/4682202.html
                    JarEntry entryCopy = new JarEntry(nextEntry);
                    entryCopy.setCompressedSize(-1);
                    jarOutputStream.putNextEntry(entryCopy);

                    InputStream intputStream =
                            jarFile.getInputStream(nextEntry);
                    // write the data
                    int bytesRead;
                    while ((bytesRead = intputStream.read(buffer)) != -1) {
                        jarOutputStream.write(buffer, 0, bytesRead);
                    }
                    jarOutputStream.closeEntry();
                }
            }
        } finally {
            if (jarFile != null) {
                jarFile.close();
            }
            if (jarOutputStream != null) {
                jarOutputStream.close();
            }
        }

    }

    /**
     * Load the user selected test plugin's manifest file.
     * 
     * @param project
     * 
     * @return The Manifest or null if cannot be accessed.
     */
    public static Manifest getManifest(IProject project) {
        Manifest manifest = null;

        IFile manifestFile = getManifestFile(project);
        if (manifestFile.exists()) {
            InputStream manifestStream = null;
            try {
                manifestStream = manifestFile.getContents();

                manifest = new Manifest(manifestStream);

            } catch (CoreException e) {
                LOG.error(e, "Failed to access project Manifest for project: "
                        + project.getName());
            } catch (IOException e) {
                LOG.error(e, "Failed to access project Manifest for project: "
                        + project.getName());
            } finally {
                if (manifestStream != null) {
                    try {
                        manifestStream.close();
                    } catch (IOException e) {
                    }
                }
            }

        }

        return manifest;
    }

}
