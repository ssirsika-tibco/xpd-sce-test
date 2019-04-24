/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */
package com.tibco.xpd.xpdl2.resources;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.xml.sax.SAXException;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.resources.wc.AbstractTransactionalWorkingCopy;
import com.tibco.xpd.resources.wc.InvalidFileException;
import com.tibco.xpd.resources.wc.InvalidVersionException;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.XpdExtDataObjectAttributes;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.DataObject;
import com.tibco.xpd.xpdl2.DocumentRoot;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExternalPackage;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.LateExecuteRemoveCommand;
import com.tibco.xpd.xpdl2.provider.Xpdl2EditPlugin;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;
import com.tibco.xpd.xpdl2.util.XpdlSearchUtil;

/**
 * Working Copy of XPDL 2 EMF model
 * 
 * @author wzurek
 */
public class Xpdl2WorkingCopyImpl extends AbstractTransactionalWorkingCopy {

    public enum Xpdl2FileType {
        PROCESS, TASK_LIBRARY, DECISION_FLOW
    }

    public final static String XPDL2_SCHEMA_LOCATION =
            "http://www.wfmc.org/standards/bpmnxpdl_31.xsd"; //$NON-NLS-1$

    private boolean ignoreFormatVersion = false;

    private Xpdl2FileType xpdl2FileType;

    private String loadedFormatVersion = ""; //$NON-NLS-1$

    /**
     * The Constructor.
     * 
     * @param resource
     *            resource that contains XPDL model
     */
    public Xpdl2WorkingCopyImpl(IResource resource, Xpdl2FileType xpdl2FileType) {
        /*
         * XPD-1128: Have to be able to cope without actual resource in working
         * copy (for load from repository etc)
         */
        super((List<IResource>) (resource != null ? Collections
                .singletonList(resource) : Collections.emptyList()));
        this.xpdl2FileType = xpdl2FileType;
        setUseWriteTransactionToSave(true);
    }

    @Override
    protected EObject doLoadModel() throws InvalidFileException {
        // System.out
        //                .println("==> doLoadModel(" + getFirstResource().getName() + ")"); //$NON-NLS-1$ //$NON-NLS-2$

        if (!isExist()) {
            return null;
        }

        loadedFormatVersion = ""; //$NON-NLS-1$

        Xpdl2Package.eINSTANCE.toString();
        Package xpdlPackage = null;

        Resource res = null;
        try {
            res = loadResource(getFirstResource());
        } catch (Exception ex) {
            clearDependencies();
            /*
             * If the cause is a CoreException then this could be because the
             * model is of an older version and needs migrating. Check the
             * version manually.
             */
            if (ex.getCause() instanceof CoreException) {
                IResource resource = getFirstResource();
                File file = resource.getLocation().toFile();
                if (file.exists()) {
                    boolean isVersionProblem = false;
                    try {
                        /*
                         * Need to check that the version is greater than 0 as
                         * XPDLs that originate from other sources will not have
                         * a Studio format version and so should not be changed.
                         */
                        int version = XpdlMigrate.getFileFormatVersion(file);
                        if (version > 0
                                && version < Integer
                                        .parseInt(XpdlMigrate.FORMAT_VERSION_ATT_VALUE)) {
                            isVersionProblem = true;
                        }
                    } catch (Exception e) {
                        // Do nothing here - let it throw the invalid file
                        // exception.
                    }

                    if (isVersionProblem) {
                        throw new InvalidVersionException(ex.getCause());
                    }
                }
            }
            throw new InvalidFileException(ex.getCause());
        }

        if (res != null) {
            res.setTrackingModification(true);

            if (res instanceof ResourceImpl) {
                ((ResourceImpl) res)
                        .setIntrinsicIDToEObjectMap(new HashMap<String, EObject>());
            }
        }

        if (res != null) {
            if (res.getErrors().size() > 0) {
                createInvalidFileMarker(getFirstResource(),
                        (res.getErrors().get(0)).getMessage());
            } else if (!res.getContents().isEmpty()) {
                xpdlPackage =
                        ((DocumentRoot) res.getContents().get(0)).getPackage();
            }
        }

        // xpdlPackage == null means that it failed to load the file so unload
        // resource from resource set
        if (xpdlPackage == null) {
            // final Resource res =
            // getEditingDomain().getResourceSet().getResource(uri, false);
            if (res != null && res.isLoaded()) {
                try {
                    unloadResource(res);
                } catch (InterruptedException e) {
                    throw new InvalidFileException(e.getCause());
                }
            }
        }

        // check if this is valid version of XPDL2
        loadedFormatVersion =
                XpdlSearchUtil.findExtendedAttributeValue(xpdlPackage,
                        XpdlMigrate.FORMAT_VERSION_ATT_NAME);
        if (!XpdlMigrate.FORMAT_VERSION_ATT_VALUE.equals(loadedFormatVersion)
                && !ignoreFormatVersion) {
            // System.out
            //                    .println("  doLoadModel(" + getFirstResource().getName() + "): Old Format version found - " + loadedFormatVersion); //$NON-NLS-1$ //$NON-NLS-2$

            /*
             * If there was NO FormatVersion attribute at all then this was not
             * even a Studio XPDL file. isInvalidVersion should only be true if
             * the file is Studio XPDL file that is of previous version (because
             * only thenn can it be migrated!)
             */
            if (loadedFormatVersion != null && loadedFormatVersion.length() > 0) {
                createInvalidVersionFileMarker(getFirstResource());
            }

            xpdlPackage = null;
            // Calling clear dependencies so that the dependency indexer
            // re-indexes when the XPDL is actually migrated.
            clearDependencies();
            //
            // There is no point in returning the root element as null and still
            // retaining the EMF resource. Therefore, unloading it.

            if (res != null && res.isLoaded()) {
                try {
                    unloadResource(res);
                } catch (InterruptedException e) {
                    throw new InvalidFileException(e.getCause());
                }
            }

        } else {
            /*
             * Once file IS successfully loaded, then make sure to remove all
             * invalid file markers.
             */
            // System.out
            //                    .println("  doLoadModel(" + getFirstResource().getName() + "): Format version OK"); //$NON-NLS-1$ //$NON-NLS-2$
            deleteInvalidFileMarker();
        }

        //        System.out.println("<== doLoadModel(" + getFirstResource().getName()); //$NON-NLS-1$
        return xpdlPackage;
    }

    /**
     * @see com.tibco.xpd.resources.wc.AbstractTransactionalWorkingCopy#logErrorMessage(org.eclipse.core.runtime.IStatus)
     * 
     * 
     *      Errors returned from load resource method get fixed during
     *      migration, but they are logged before the migration takes place.
     *      After the migration the error messages are not really errors, but
     *      since they are logged they might confuse the users.
     * 
     *      So overriding this method to ignore some error messages.
     * 
     * @param status
     *            - {@link Status} object that has the error message to log
     */
    @Override
    protected void logErrorMessage(IStatus status) {

        IResource resource = getFirstResource();
        File file = resource.getLocation().toFile();
        if (file.exists()) {

            int version = 0;
            try {

                version = XpdlMigrate.getFileFormatVersion(file);
            } catch (SAXException | IOException | ParserConfigurationException e) {

                e.printStackTrace();
            }
            if (version > 0
                    && version < Integer
                            .parseInt(XpdlMigrate.FORMAT_VERSION_ATT_VALUE)) {
                boolean isVersionProblem = true;
                /*
                 * if it is not a format version problem, then hand-over to
                 * super class to log the error message.
                 */
                if (!isVersionProblem) {

                    super.logErrorMessage(status);
                } else {

                    /*
                     * if it is a format version problem, then log the info
                     * message
                     */
                    String msg =
                            String.format(Messages.Migration_LoadResource_Info_message,
                                    file.getName());
                    Status status2 =
                            new Status(IStatus.INFO,
                                    XpdResourcesPlugin.ID_PLUGIN, msg);
                    XpdResourcesPlugin.getDefault().getLogger().log(status2);
                }
            }
        }
    }

    @Override
    protected void doSave() throws IOException {
        // Ensure that we have set up the schemaLocation for xpdl2

        Object o = getRootElement().eContainer();
        if (o instanceof DocumentRoot) {
            DocumentRoot docRoot = (DocumentRoot) o;

            EMap<String, String> scLoc = docRoot.getXSISchemaLocation();

            final Map<String, String> map = scLoc.map();
            final EMap<String, String> nsMap = docRoot.getXMLNSPrefixMap();

            /*
             * Sid ACE-467 - Need to remove simulation namespace, so if it's
             * there then run the command to reset it.
             * 
             * Sid ACE-475 - Same goes for the eaijava and database namespaces
             * (for which we also have removed the model contribution from the
             * product)
             */

            if (!map.containsKey(Xpdl2Package.eNS_URI)
                    || nsMap.containsKey("simulation") //$NON-NLS-1$
                    || nsMap.containsKey("eaijava") //$NON-NLS-1$
                    || nsMap.containsKey("database")) { //$NON-NLS-1$

                WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(docRoot);

                if (wc != null) {
                    EditingDomain ed = wc.getEditingDomain();
                    if (ed instanceof TransactionalEditingDomain) {
                        TransactionalEditingDomain ted =
                                (TransactionalEditingDomain) ed;
                        RecordingCommand cmd = new RecordingCommand(ted) {

                            @Override
                            protected void doExecute() {
                                map.put(Xpdl2Package.eNS_URI,
                                        XPDL2_SCHEMA_LOCATION);
                                // rsomayaj - inserted this to overcome
                                // IllegalStateException thrown because the
                                // namespaces were written in a non-write
                                // transaction.

                                /*
                                 * Sid ACE-467: Don't need or want the
                                 * simulation namespace any more (migration
                                 * should remove all simulation elements.
                                 */
                                nsMap.removeKey("simulation"); //$NON-NLS-1$
                                /* Sid ACE-475 - same for eaijava & database */
                                nsMap.removeKey("eaijava"); //$NON-NLS-1$
                                nsMap.removeKey("database"); //$NON-NLS-1$

                                nsMap.put("iProcessExt", "http://www.tibco.com/XPD/iProcessExt1.0.0"); //$NON-NLS-1$ //$NON-NLS-2$
                                nsMap.put("xpdExt", "http://www.tibco.com/XPD/xpdExtension1.0.0"); //$NON-NLS-1$ //$NON-NLS-2$
                                nsMap.put("email", "http://www.tibco.com/XPD/email1.0.0"); //$NON-NLS-1$ //$NON-NLS-2$

                                nsMap.put("orchestrator", "http://www.tibco.com/XPD/orchestrator1.0.0"); //$NON-NLS-1$ //$NON-NLS-2$
                                nsMap.put("order", "http://www.tibco.com/XPD/order1.0.0"); //$NON-NLS-1$ //$NON-NLS-2$

                            }

                        };
                        cmd.setLabel(Messages.Xpdl2WorkingCopyImpl_SetSchemaLocationCommand);
                        if (cmd.canExecute()) {
                            ted.getCommandStack().execute(cmd);
                        }
                    }
                }
            }
        }

        super.doSave();
    }

    @Override
    public EPackage getWorkingCopyEPackage() {
        return Xpdl2Package.eINSTANCE;
    }

    /**
     * name of extended attribute of external package element that holds
     * location of the referenced file.
     */
    public static final String ATTR_LOCATION = "location"; //$NON-NLS-1$

    /**
     * Name of cached attribute that holds coma-separated list of processed ids.
     */
    public static final String PROCESS_IDS_CACHED_ATT = "ProcessesIds";//$NON-NLS-1$

    /**
     * spearator of IDs in the {@link #PROCESS_IDS_CACHED_ATT} attribute.
     */
    public static final String PROCESS_ID_SEPARATOR = ",";//$NON-NLS-1$

    /**
     * Create an ExternalPackage reference element for the package controleld by
     * the given working copy and add it to this WorkingCopy's list of ext
     * packages if it does not already exist.
     * 
     * Search for ExternalPackage reference to specified external working copy,
     * return reference name, and if not found it append creation comand to the
     * procided compound comand.
     * 
     * @param wc
     * @param externalWc
     * @param cmd
     * @return
     */
    public String appendCreateReferenceCommand(WorkingCopy externalWc,
            CompoundCommand cmd) {

        ExternalPackage newExtPkg = createExternalPackage(externalWc);
        if (newExtPkg == null) {
            return ""; //$NON-NLS-1$
        }

        return appendAddReferenceCommand(getEditingDomain(),
                cmd,
                newExtPkg,
                ((Package) getRootElement()));
    }

    /**
     * Add extenal package reference it to this WorkingCopy's list of ext
     * packages if it does not already exist.
     * 
     * @param domain
     * 
     * @param cmd
     * @param newExtPkg
     * @param pkg
     * @param loc
     * @return
     */
    public static String appendAddReferenceCommand(EditingDomain editingDomain,
            CompoundCommand cmd, ExternalPackage newExtPkg, Package pkg) {
        String loc = getExternalPackageLocation(newExtPkg);
        if (loc != null) {
            EList<ExternalPackage> eps = pkg.getExternalPackages();

            for (ExternalPackage ep : eps) {
                String extLoc = getExternalPackageLocation(ep);
                if (loc.equals(extLoc)) {
                    return ep.getHref();
                }
            }

            cmd.append(AddCommand.create(editingDomain,
                    pkg,
                    Xpdl2Package.eINSTANCE.getPackage_ExternalPackages(),
                    newExtPkg));

            return newExtPkg.getHref();
        }

        return ""; //$NON-NLS-1$
    }

    /**
     * Adds a command to remove the External Package with the given
     * externalPackageHref, if it exists.This method does not check if the
     * external package is referenced by any element or not.The calling method
     * should take care of this , if required.
     * 
     * @param domain
     * 
     * @param cmd
     * @param wc
     * @param extPackageToRemove
     * @param pkg
     * @param loc
     * @return
     */
    public static void appendRemoveReferenceCommand(
            EditingDomain editingDomain, CompoundCommand cmd,
            String externalPackageHref, Xpdl2WorkingCopyImpl wc) {
        if (externalPackageHref != null && externalPackageHref.length() > 0) {
            EList<ExternalPackage> eps =
                    ((Package) wc.getRootElement()).getExternalPackages();
            ExternalPackage externalPackageFound = null;
            for (ExternalPackage externalPackage : eps) {
                if (externalPackageHref.equalsIgnoreCase(externalPackage
                        .getHref())) {
                    externalPackageFound = externalPackage;
                    break;
                }
            }
            if (externalPackageFound != null) {
                cmd.append(LateExecuteRemoveCommand.create(editingDomain,
                        externalPackageFound));
            }

        }

    }

    /**
     * Create an external package reference to the package for the given working
     * copy.
     * 
     * @param externalWc
     * @return ExtemalPackage or null if cannot be located.
     * 
     */
    public static ExternalPackage createExternalPackage(WorkingCopy externalWc) {
        /*
         * XPD-1128: May not have an actual resource backing a working copy
         * created from temporary input stream (for things like history
         * revisions)
         */
        if (externalWc.getEclipseResources().isEmpty()) {
            return null;
        }

        if (externalWc == null) {
            return null;
        }

        IResource externalRes = externalWc.getEclipseResources().get(0);

        return createExternalPackage(externalRes);
    }

    /**
     * Create an external package reference to the package in the given
     * resource.
     * 
     * @param externalRes
     *            external package resource.
     * @return ExtemalPackage or null if cannot be located.
     * 
     */
    public static ExternalPackage createExternalPackage(IResource externalRes) {

        if (externalRes != null) {
            // Get special folder container of the resource if there is one
            ProjectConfig config =
                    XpdResourcesPlugin.getDefault()
                            .getProjectConfig(externalRes.getProject());
            SpecialFolder sFolder = null;

            if (config != null && config.getSpecialFolders() != null) {
                sFolder =
                        config.getSpecialFolders()
                                .getFolderContainer(externalRes);
            }

            if (sFolder == null) {
                return null;
            }
            IFolder folder = sFolder.getFolder();

            if (folder == null) {
                return null;
            }

            IPath path =
                    externalRes
                            .getFullPath()
                            .removeFirstSegments(folder.getFullPath()
                                    .segmentCount()).makeAbsolute();

            String loc = path.toPortableString();

            // there is no existing reference, create new
            ExternalPackage ep = Xpdl2Factory.eINSTANCE.createExternalPackage();
            String ref;

            if (loc.lastIndexOf(".") > 0) { //$NON-NLS-1$
                ref = loc.substring(0, loc.lastIndexOf(".")); //$NON-NLS-1$
            } else {
                ref = loc;
            }
            if (ref.startsWith("/") && loc.length() > 1) { //$NON-NLS-1$
                ref = ref.substring(1);
            }

            ep.setHref(ref);
            ExtendedAttribute ea =
                    Xpdl2Factory.eINSTANCE.createExtendedAttribute();
            ea.setName(ATTR_LOCATION);
            ea.setValue(loc);
            ep.getExtendedAttributes().add(ea);

            return ep;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public String getExternalPackageLocation(String href) {
        if (href != null) {
            Package pkg = (Package) getRootElement();
            // Get the external packages
            EList externalPackages = pkg.getExternalPackages();
            for (Iterator iter = externalPackages.iterator(); iter.hasNext();) {
                ExternalPackage ePkg = (ExternalPackage) iter.next();
                // find package with proper href
                if (ePkg.getHref().equals(href)) {
                    return getExternalPackageLocation(ePkg);
                }
            }
        }
        return null;
    }

    /**
     * Little utility to get the package path location from ExternalPackage
     * element.
     * 
     * @param extPkg
     * @return location or null on error.
     */
    public static String getExternalPackageLocation(ExternalPackage extPkg) {
        String loc = null;

        for (ExtendedAttribute ea : extPkg.getExtendedAttributes()) {
            if (ATTR_LOCATION.equals(ea.getName())) {
                loc = ea.getValue();
            }
        }
        return loc;
    }

    @Override
    protected AdapterFactory createAdapterFactory() {
        registerResourceProvider(Xpdl2Package.eINSTANCE,
                Xpdl2EditPlugin.getPlugin());
        registerResourceProvider(XpdExtensionPackage.eINSTANCE,
                Xpdl2EditPlugin.getPlugin());
        return Xpdl2WorkingCopyFactory.getAdapterFactory();
    }

    // @Override
    // protected AdapterFactoryEditingDomain createEditingDomain() {
    // AdapterFactory af = getAdapterFactory();
    // AdapterFactoryEditingDomain result =
    // new EditingDomainWithCommandWrapper(af, new CmdStackFix());
    //
    // return result;
    // }
    //

    @Override
    protected void doMigrateToLatestVersion() throws CoreException {
        /*
         * XPD-1128: May not have an actual resource backing a working copy
         * created from temporary input stream (for things like history
         * revisions)
         */
        if (getFirstResource() == null) {
            return;
        }

        try {
            /*
             * Delete the invalid file markers up front - if they are still
             * there on first getRootElement after migration then getRootElement
             * won't bother to do anything!
             * 
             * So it wouldn't load the new migrated version of resource.
             */
            List<IMarker> invalidFileMarkers = getInvalidFileMarkers();
            for (IMarker m : invalidFileMarkers) {
                m.delete();
            }

            XpdlMigrate.migrate(getFirstResource());

        } catch (Exception e) {
            throw new CoreException(
                    new Status(
                            IStatus.ERROR,
                            Xpdl2ResourcesPlugin.PLUGIN_ID,
                            String.format(Messages.Xpdl2WorkingCopyImpl_migrationProblem_error_message,
                                    getFirstResource().getFullPath().toString()),
                            e));
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.wc.AbstractWorkingCopy#computeDependenciesFromModel
     * ()
     */
    @SuppressWarnings("unchecked")
    @Override
    protected List<IResource> computeDependenciesFromModel() {
        List<IResource> result = super.computeDependenciesFromModel();

        /*
         * XPD-1128: May not have an actual resource backing a working copy
         * created from temporary input stream (for things like history
         * revisions)
         */
        if (getFirstResource() != null) {
            Package pck = (Package) getRootElement();
            IProject project = getFirstResource().getProject();

            if (pck != null) {
                List<ExternalPackage> packages = pck.getExternalPackages();

                XpdProjectResourceFactory projResFactory =
                        XpdResourcesPlugin.getDefault()
                                .getXpdProjectResourceFactory(project);
                for (ExternalPackage epck : packages) {
                    String ref = getExternalPackageLocation(epck.getHref());
                    /*
                     * XPD-4532: Resolve package locations in referenced
                     * projects
                     */
                    IResource resource =
                            SpecialFolderUtil
                                    .resolveSpecialFolderRelativePath(project,
                                            Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND,
                                            ref,
                                            true);

                    if (resource == null) {
                        // Check for Task Library special folders too.
                        resource =
                                projResFactory
                                        .resolveResourceReference(getFirstResource(),
                                                ref,
                                                Xpdl2ResourcesConsts.TASK_LIBRARY_SPECIAL_FOLDER_KIND);
                    }

                    if (resource != null) {
                        result.add(resource);
                    }
                }

                /*
                 * Add any external references in data objects
                 */
                if (pck.getArtifacts() != null) {
                    EList artifacts = pck.getArtifacts();

                    for (Iterator<?> iter = artifacts.iterator(); iter
                            .hasNext();) {
                        Artifact artifact = (Artifact) iter.next();

                        if (artifact != null
                                && artifact.getDataObject() != null) {
                            DataObject dataObject = artifact.getDataObject();

                            EObject element =
                                    dataObject
                                            .getOtherElement(XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_DataObjectAttributes()
                                                    .getName());

                            if (element instanceof XpdExtDataObjectAttributes) {
                                XpdExtDataObjectAttributes extAttr =
                                        (XpdExtDataObjectAttributes) element;
                                String extRef = extAttr.getExternalReference();

                                if (extRef != null) {
                                    extRef = extRef.trim();

                                    if (extRef.length() > 0) {
                                        IResource resource =
                                                project.findMember(extRef);

                                        // Only include file references
                                        if (resource != null
                                                && (resource instanceof IFile)) {

                                            result.add(resource);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * Extend dependecy with the dependency from error markers. (It is addition
     * to the normal dependecy stored in the model)
     */
    @Override
    public List<IResource> getDependency() {
        ArrayList<IResource> result =
                new ArrayList<IResource>(super.getDependency());

        /*
         * XPD-1128: May not have an actual resource backing a working copy
         * created from temporary input stream (for things like history
         * revisions)
         */
        if (!getEclipseResources().isEmpty()) {
            ValidationActivator va = ValidationActivator.getDefault();
            try {
                IResource baseRes = getEclipseResources().get(0);
                if (baseRes == null || !baseRes.exists()) {
                    return Collections.emptyList();
                }
                IMarker[] markers =
                        baseRes.findMarkers(XpdConsts.VALIDATION_MARKER_TYPE,
                                true,
                                IResource.DEPTH_ZERO);
                IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
                for (IMarker marker : markers) {
                    Map<String, String> map = va.getAdditionalInfoMap(marker);
                    String ress = map.get("VALIDATION_DEPENDENCY"); //$NON-NLS-1$
                    if (ress != null && ress.length() > 0) {
                        StringTokenizer tokenizer =
                                new StringTokenizer(ress, "\n"); //$NON-NLS-1$
                        while (tokenizer.hasMoreTokens()) {
                            IResource res =
                                    root.findMember(tokenizer.nextToken());
                            if (res != null && !result.contains(res)) {
                                result.add(res);
                            }
                        }
                    }
                }
            } catch (CoreException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * Locate process with given processID in the workspace. This usually do not
     * require loading of all models to the memory, and can cache parts of the
     * results.<br/>
     * Note: only process packages special folders are searched.
     * 
     * @param packageId
     * @return package with the ID or null if not found.
     */
    public static Package locatePackage(String packageId) {
        List<EObject> pkgs = ProcessUIUtil.getAllProcessPackages(packageId);

        if (pkgs != null && pkgs.size() > 0 && pkgs.get(0) instanceof Package) {
            return (Package) pkgs.get(0);
        }

        return null;
    }

    /**
     * Locate process with given processID in the workspace. This usually do not
     * require loading of all models to the memory, and can cache parts of the
     * results.<br/>
     * Note: only process packages special folders are searched.
     * 
     * @param packageId
     * @return package with the ID or empty list if not found.
     */
    public static List<Package> locatePackages(String packageId) {
        List<EObject> objs = ProcessUIUtil.getAllProcessPackages(packageId);

        List<Package> pkgs = new ArrayList<Package>();
        if (objs != null) {
            for (EObject eo : objs) {
                if (eo instanceof Package) {
                    pkgs.add((Package) eo);
                }
            }
        }

        return pkgs;
    }

    /**
     * Locate process with given processID in the workspace. This usually do not
     * require loading of all models to the memory, and can cache parts of the
     * results.<br/>
     * Note: only process packages special folders are searched.
     * 
     * @param processId
     * @return process with the ID or null if not found.
     */
    public static Process locateProcess(String processId) {
        List<EObject> processes = ProcessUIUtil.getAllElements(processId);

        if (processes != null && processes.size() > 0
                && processes.get(0) instanceof Process) {
            return (Process) processes.get(0);
        }

        return null;
    }

    /**
     * Locate process interface with given interfaceId in the workspace. This
     * usually do not require loading of all models to the memory, and can cache
     * parts of the results.<br/>
     * Note: only process packages special folders are searched.
     * 
     * @param interfaceId
     * @return process with the ID or null if not found.
     */
    public static ProcessInterface locateProcessInterface(String interfaceId) {
        List<EObject> interfaces = ProcessUIUtil.getAllElements(interfaceId);

        if (interfaces != null && interfaces.size() > 0
                && interfaces.get(0) instanceof ProcessInterface) {
            return (ProcessInterface) interfaces.get(0);
        }

        return null;
    }

    /**
     * @param taskLibraryId
     * @return The xpdl Process for the given task library.
     */
    public static Process locateTaskLibrary(String taskLibraryId) {
        IndexerItem idxItem =
                ProcessUIUtil.getTaskLibraryIndexItem(taskLibraryId);
        if (idxItem != null) {
            String struri = idxItem.getURI();
            URI uri = URI.createURI(struri);
            if (uri != null) {
                EObject eo = ProcessUIUtil.getEObjectFrom(uri);
                if (eo instanceof Process) {
                    return (Process) eo;
                }
            }
        }

        return null;
    }

    /**
     * @param taskLibraryTaskId
     * @return The xpdl Activity for the given task library task.
     */
    public static Activity locateTaskLibraryTask(String taskLibraryTaskId) {
        IndexerItem idxItem =
                ProcessUIUtil.getTaskLibraryTaskIndexItem(taskLibraryTaskId);

        if (idxItem != null) {
            String struri = idxItem.getURI();
            URI uri = URI.createURI(struri);
            if (uri != null) {
                EObject eo = ProcessUIUtil.getEObjectFrom(uri);
                if (eo instanceof Activity) {
                    return (Activity) eo;
                }
            }
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.resources.wc.AbstractWorkingCopy#computeCachedAttributeValue(java.lang.String)
     */
    @Override
    protected String computeCachedAttributeValue(String id) {
        if (PROCESS_IDS_CACHED_ATT.equals(id)) {
            StringBuilder builder = new StringBuilder();
            if (getRootElement() != null) {

                // Put the package id in the cache too.
                builder.append(((Package) getRootElement()).getId());

                List<Process> procs =
                        ((Package) getRootElement()).getProcesses();
                for (Process proc : procs) {
                    if (builder.length() > 0) {
                        builder.append(PROCESS_ID_SEPARATOR);
                    }
                    builder.append(proc.getId());
                }

                // Include Process Itefaces in cached Id's.
                ProcessInterfaces ifcs =
                        (ProcessInterfaces) Xpdl2ModelUtil
                                .getOtherElement((Package) getRootElement(),
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_ProcessInterfaces());
                if (ifcs != null) {
                    for (ProcessInterface ifc : ifcs.getProcessInterface()) {
                        if (builder.length() > 0) {
                            builder.append(PROCESS_ID_SEPARATOR);
                        }
                        builder.append(ifc.getId());

                    }
                }
                return builder.toString();
            }
        }
        return super.computeCachedAttributeValue(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.wc.AbstractWorkingCopy#getCachedAttributesIds()
     */
    @Override
    protected List<String> getCachedAttributesIds() {
        return Arrays.asList(new String[] { PROCESS_IDS_CACHED_ATT });
    }

    public boolean isIgnoreFormatVersion() {
        return ignoreFormatVersion;
    }

    public void setIgnoreFormatVersion(boolean ignoreFormatVersion) {
        this.ignoreFormatVersion = ignoreFormatVersion;
    }

    public Xpdl2FileType getXpdl2FileType() {
        return xpdl2FileType;
    }

    public void forceCleanup() {
        cleanup();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.wc.AbstractWorkingCopy#getMetaText(org.eclipse
     * .emf.ecore.EObject)
     */
    @Override
    public String getMetaText(EObject eo) {
        /*
         * XPD-1140: This was originally placed here as a workaround for
         * "bx emulation's contributed navigatorCOntent label provider was overriding the Bpm one"
         * , however in retrospect this is actually the right place to
         * distinguish element names for Business Process, PageflowProcess and
         * Task Library.
         * 
         * And also for Service Process Interface?? TODO: Confirm
         */
        if (eo instanceof Process) {
            Process process = (Process) eo;
            if (Xpdl2ModelUtil.isCaseService(process)) {
                return Messages.BpmContentLabelProvider_CaseServicePageflowProcess_label;
            } else if (Xpdl2ModelUtil.isPageflowBusinessService(process)) {
                return Messages.BpmContentLabelProvider_BusinessServicePageflowProcess_label;
            } else if (Xpdl2ModelUtil.isPageflow(process)) {
                return Messages.BpmContentLabelProvider_PageflowProcess_label;
            } else if (Xpdl2ModelUtil.isTaskLibrary(process)) {
                return Messages.BpmContentLabelProvider_TaskLibrary_label;
            } else if (DecisionFlowUtil.isDecisionFlow(process)) {
                return Messages.BpmContentLabelProvider_DecisionFlow_label;
            } else if (Xpdl2ModelUtil.isServiceProcess(process)) {
                return Messages.BpmContentLabelProvider_ServiceProcess_label;
            } else {
                return Messages.BpmContentLabelProvider_BusinessProcess_label;
            }
        } else if (eo instanceof ProcessInterface) {

            if (Xpdl2ModelUtil.isServiceProcessInterface((ProcessInterface) eo)) {

                return Messages.BpmContentLabelProvider_ServiceProcessInterface_label;
            }
        }
        return super.getMetaText(eo);
    }

    /**
     * XPD-1128: Allow set of root Element. This maybe could move down to a new
     * level above AbstractWorkingCopy that also allows creation from
     * InputStream.
     * 
     * @param newRootElement
     */
    public void setRootElement(EObject newRootElement) {
        // TODO XPD-1128: Allow set of root Element. This maybe could move down
        // to a new level above AbstractWorkingCopy that also allows creation
        // from InputStream.
        /*
         * Xpdl2 working copy has package as it's theoretical root element BUT
         * as DocumentRootImpl as it's actual root element so we actually need
         * to replace the package element in the DocumentRoot element.
         */

        Resource resource = getRootElement().eResource();

        DocumentRoot documentRoot =
                (DocumentRoot) resource.getContents().get(0);

        documentRoot.eSet(Xpdl2Package.eINSTANCE.getDocumentRoot_Package(),
                newRootElement);

        rootElement = newRootElement;

        return;
    }

    /**
     * @see com.tibco.xpd.resources.wc.AbstractWorkingCopy#fireWCModelChanged(java.util.EventObject)
     * 
     * @param event
     */
    @Override
    protected void fireWCModelChanged(EventObject event) {
        resetResourceIdElementCache();
        super.fireWCModelChanged(event);
    }

    /**
     * @see com.tibco.xpd.resources.wc.AbstractTransactionalWorkingCopy#cleanup()
     * 
     */
    @Override
    protected void cleanup() {
        resetResourceIdElementCache();
        super.cleanup();
    }

    /**
     * Sid: XPD-4150 reset the emf resource id (or uri) to element map. This
     * prevents anything that does a "find object by uri/id in whole xpdl
     * package from iterating thru the entire model every time.
     * <p>
     * We reset the cache whenever the model is loaded, changed or cleaned up.
     * Then next calls to resouce.getEObject(String uri)
     */
    private void resetResourceIdElementCache() {
        Collection<Resource> resources = getResources();

        if (resources != null) {
            for (Resource resource : resources) {
                if (resource instanceof ResourceImpl) {
                    ((ResourceImpl) resource)
                            .setIntrinsicIDToEObjectMap(new HashMap<String, EObject>());

                }
            }
        }
    }

}
