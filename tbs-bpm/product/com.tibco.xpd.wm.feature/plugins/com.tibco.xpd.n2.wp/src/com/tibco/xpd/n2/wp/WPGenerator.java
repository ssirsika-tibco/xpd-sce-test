/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.n2.wp;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceRuleFactory;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.osgi.framework.Version;

import com.tibco.n2.wp.archive.service.BusinessServiceType;
import com.tibco.n2.wp.archive.service.ChannelExtentionType;
import com.tibco.n2.wp.archive.service.ChannelType;
import com.tibco.n2.wp.archive.service.ChannelsType;
import com.tibco.n2.wp.archive.service.FormType;
import com.tibco.n2.wp.archive.service.PageActivityType;
import com.tibco.n2.wp.archive.service.PageFlowRefType;
import com.tibco.n2.wp.archive.service.PageFlowType;
import com.tibco.n2.wp.archive.service.ServiceArchiveDescriptorType;
import com.tibco.n2.wp.archive.service.WPFactory;
import com.tibco.n2.wp.archive.service.WorkTypeType;
import com.tibco.n2.wp.archive.service.util.WPResourceFactoryImpl;
import com.tibco.xpd.daa.internal.util.CompositeUtil;
import com.tibco.xpd.daa.internal.util.PluginManifestHelper;
import com.tibco.xpd.n2.bpel.utils.BPELN2Utils;
import com.tibco.xpd.n2.brm.utils.BRMUtils;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.n2.wp.internal.Messages;
import com.tibco.xpd.n2.wp.utils.WPUtil;
import com.tibco.xpd.presentation.channels.AttributeValue;
import com.tibco.xpd.presentation.channels.Channel;
import com.tibco.xpd.presentation.channels.Channels;
import com.tibco.xpd.presentation.channels.ExtendedAttribute;
import com.tibco.xpd.presentation.channels.TypeAssociation;
import com.tibco.xpd.presentation.channeltypes.Attribute;
import com.tibco.xpd.presentation.channeltypes.AttributeType;
import com.tibco.xpd.presentation.channeltypes.ChannelDestination;
import com.tibco.xpd.presentation.resources.ui.internal.util.PresentationManager;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.FormImplementation;
import com.tibco.xpd.xpdExtension.FormImplementationType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Generates N2 deployment artifacts.
 * <p>
 * <i>Created: 10 Sep 2008</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class WPGenerator {

    /**
     * Channel destination id for AMX-BPM.
     */
    private static final String AMXBPM_DESTINATION_ID = "amxbpm"; //$NON-NLS-1$

    protected static class GenerationContext {
        /** Maps channelId to Map of pageFlowId -> WPPageflow. */
        private final Map<String, Map<String, PageFlowType>> pageFlows =
                new HashMap<String, Map<String, PageFlowType>>();

        // cache for association between FormType and Activity or PageFlowType
        // and
        // PageFlow.
        private final Map<EObject, EObject> processRefMap =
                new HashMap<EObject, EObject>();

        private final List<IStatus> problems = new ArrayList<IStatus>();

        public List<IStatus> getProblems() {
            return problems;
        }

        private final String timestamp;

        private final IProject project;

        public GenerationContext(IProject project, String timestamp) {
            this.project = project;
            this.timestamp = timestamp;
        }

        public Map<String, PageFlowType> getPageFlows(String channelId) {
            Map<String, PageFlowType> channelPageFlows =
                    pageFlows.get(channelId);
            if (channelPageFlows == null) {
                channelPageFlows = new HashMap<String, PageFlowType>();
                pageFlows.put(channelId, channelPageFlows);
            }
            return channelPageFlows;
        }

        public IProject getProject() {
            return project;
        }

        public Map<EObject, EObject> getProcessRefMap() {
            return processRefMap;
        }

        public String getTimestamp() {
            return timestamp;
        }
    }

    private static final String CUSTOM_FORM_RELATIVE_PATH = "CUSTOM_FORM"; //$NON-NLS-1$

    /** */
    private static final Status PROBLEM_WARNING_STATUS = new Status(
            IStatus.WARNING, WPActivator.PLUGIN_ID,
            Messages.WPGenerator_ProblemsWithWP_message2);

    /** Forms BOM Special Folder kind. */
    private static final String FORMS_BOM_FOLDER_KIND = "formsBOM"; //$NON-NLS-1$

    /** Forms GI Special Folder kind. */
    private static final String FORMS_GI_FOLDER_KIND = "formsGI"; //$NON-NLS-1$

    /** Forms GWT Special Folder kind. */
    private static final String FORMS_GWT_FOLDER_KIND = "formsGWT"; //$NON-NLS-1$

    /** Forms Mobile Special Folder kind. */
    private static final String FORMS_MOBILE_FOLDER_KIND = "formsMobile"; //$NON-NLS-1$

    private static final String BPEL_EXTENSION = "bpel"; //$NON-NLS-1$

    private static final Logger LOG = WPActivator.getDefault().getLogger();

    /** Work model ID prefix. */
    private static final String WORK_TYPE_ID_PREFIX = "WT_"; //$NON-NLS-1$

    /** Work presentation special folder kind. */
    public static final String WP_MODULES_SPECIAL_FOLDER_KIND = "wpOutput"; //$NON-NLS-1$

    /** Work presentation special folder name. */
    public static final String WP_MODULES_SPECIAL_FILDER_NAME = ".wpModules"; //$NON-NLS-1$

    /** Business resource management special folder kind. */
    public static final String BRM_MODULES_SPECIAL_FOLDER_KIND = "brmOutput"; //$NON-NLS-1$

    /** Business resource management special folder name. */
    public static final String BRM_MODULES_SPECIAL_FOLDER_NAME = ".brmModules"; //$NON-NLS-1$

    /** Prefix (root folder) inside work presentation archive. */
    private static final String WP_SERVICE_ARCHIVE_PATH_PREFIX =
            WPUtil.WP_RESOURCES_FOLDERNAME;

    /** Name of the file containing work presentation descriptor. */
    private static final String WORK_PRESENTATION_DESCRIPTOR_NAME =
            WPUtil.SERVICE_ARCHIVE_DESC_NAME;

    /** XML file extension */
    private static final String XML_EXTENSION = "xml"; //$NON-NLS-1$

    private static final int BUFFER_SIZE = 1024;

    private static final WPGenerator INSTANCE = new WPGenerator();

    private static final String PROPERTIES_EXTENSION = "properties"; //$NON-NLS-1$

    /**
     * AMX-BPM supporeted channel destinations.
     */
    private static final BasicEList<ChannelDestination> AMX_BPM_CHANNEL_DESTINATIONS =
            new BasicEList<ChannelDestination>(
                    PresentationManager
                            .getChannelDestinationsByIds(AMXBPM_DESTINATION_ID));

    public static WPGenerator getInstance() {
        return INSTANCE;
    }

    /**
     * Private constructor. Use {@link #getInstance()} factory method instead.
     */
    private WPGenerator() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Generate work types and work presentation module from Workforce
     * Management Model without using a job.
     * 
     * @param wmModel
     *            workforce management model.
     * @param project
     *            the context project.
     * @deprecated Use {@link #generateN2Modules(IProject, IFolder, String)}
     *             instead.
     */
    @Deprecated
    public void generateN2Modules(final IProject project) {

        final IFolder wpModulesOut =
                BRMUtils.getSpecialFolder(project,
                        WP_MODULES_SPECIAL_FILDER_NAME,
                        WP_MODULES_SPECIAL_FOLDER_KIND);
        generateN2Modules(project,
                wpModulesOut,
                Long.toString(System.currentTimeMillis()));
    }

    /**
     * Generate work types and work presentation module from Workforce
     * Management Model without using a job.
     * 
     * @param wmModel
     *            workforce management model.
     * @param project
     *            the context project.
     */
    public IStatus generateN2Modules(final IProject project,
            final IFolder generationRoot, String timestamp) {
        try {
            // ProjectDAAGenerator.generateDAA() is already clearing off staging
            // area, so we should not clean the staging folder again
            // BRMUtils.cleanFolder(generationRoot);
            return buildPresentationArchive(project,
                    new FolderResourceContainer(generationRoot),
                    timestamp);
        } catch (final Exception e) {
            LOG.error(e);
        }
        return Status.OK_STATUS;
    }

    /**
     * Generate work types and work presentation module from BRM Model.
     * 
     * @param wmModel
     *            workforce management model.
     * @param project
     *            the context project.
     * @deprecated Use {@link #generateN2Modules(IProject, IFolder, String)}
     *             instead.
     */
    @Deprecated
    public Job createGenerateN2ModulesJob(final IProject project) {
        Job job =
                new WorkspaceJob(Messages.WPGenerator_GenWPArtifacts_message) {
                    @Override
                    public IStatus runInWorkspace(IProgressMonitor monitor) {
                        monitor.beginTask(Messages.WPGenerator_GenWPArtifacts_message,
                                IProgressMonitor.UNKNOWN);
                        try {
                            generateN2Modules(project);
                        } catch (final Exception e) {
                            LOG.error(e);
                            return Status.CANCEL_STATUS;
                        } finally {
                            monitor.done();
                        }
                        return Status.OK_STATUS;
                    }

                };
        IResourceRuleFactory ruleFactory =
                ResourcesPlugin.getWorkspace().getRuleFactory();
        ISchedulingRule brmCreateRule = ruleFactory.createRule(project);
        job.setRule(brmCreateRule);
        return job;
    }

    /**
     * Creates work presentation module.
     * 
     * @param timestamp
     * 
     * @param projectModel
     *            the source model.
     * @param generationRoot
     *            the generation root.
     */
    private IStatus buildPresentationArchive(final IProject project,
            ResourceContainer rContainer, String timestamp) {

        GenerationContext ctx = new GenerationContext(project, timestamp);

        ServiceArchiveDescriptorType wpDescriptor = createWPDescriptor(ctx);

        // Check if all channels are empty.
        // Don't create archive if all channels are empty.
        int allWorkTypesAssoc = 0;
        int allBusinessServices = 0;
        int allPageFlows = 0;
        for (ChannelType ch : wpDescriptor.getChannels().getChannel()) {
            allWorkTypesAssoc += ch.getWorkType().size();
            allBusinessServices += ch.getBusinessService().size();
            allPageFlows += ch.getPageFlow().size();
        }
        if (allWorkTypesAssoc + allBusinessServices + allPageFlows == 0) {
            return Status.OK_STATUS;
        }

        // Create EMF resource for descriptor.
        // Use private resource set.
        ResourceSet rs = new ResourceSetImpl();
        Resource descriptor =
                createWPDescriptorResource(wpDescriptor,
                        rContainer.getGenerationRootFolder(),
                        rs);
        if (descriptor == null) {
            return Status.OK_STATUS;
        }

        // Create archive and add all necessary artifacts in a proper layout.
        try {

            IPath descriptorPath =
                    new Path(WP_SERVICE_ARCHIVE_PATH_PREFIX)
                            .append(WORK_PRESENTATION_DESCRIPTOR_NAME);
            OutputStream entryOutStream =
                    rContainer.getEntryOutStream(descriptorPath);
            try {
                descriptor.save(entryOutStream,
                        N2Utils.getDefaultXMLSaveOptions());
            } finally {
                if (rContainer.shouldCloseEntryOutStream()
                        && entryOutStream != null) {
                    entryOutStream.close();
                    rContainer
                            .refreshPath(descriptorPath.removeLastSegments(1),
                                    IResource.DEPTH_ONE);
                }
            }

            String projectVersion =
                    PluginManifestHelper.getUpdatedBundleVersion(CompositeUtil
                            .getVersionNumber(project), timestamp);

            // FORM-4691/XPD-3480: Deployable forms artefacts are now
            // contributed by FormCompositeContributor.

            creeateChannelPropertiesEntries(project, rContainer, projectVersion);

        } catch (Exception e) {
            LOG.error(e);
            e.printStackTrace();
            return PROBLEM_WARNING_STATUS;
        } finally {
            try {
                rContainer.closeContainer();
                rContainer
                        .refreshPath(new Path(WP_SERVICE_ARCHIVE_PATH_PREFIX),
                                IResource.DEPTH_INFINITE);
            } catch (Exception e) {
                LOG.error(e);
                return PROBLEM_WARNING_STATUS;
            }
        }
        if (ctx.getProblems().isEmpty()) {
            return Status.OK_STATUS;
        } else {
            return new MultiStatus(WPActivator.PLUGIN_ID, 0, ctx.getProblems()
                    .toArray(new IStatus[ctx.getProblems().size()]),
                    Messages.WPGenerator_ProblemsWithWP_message2, null);
        }
    }

    // JA: TODO this mapping should be replaced by an extension point
    // element defined in
    // 'com.tibco.xpd.presentation.resources.ui/channelTypes' extension.
    private static Map<String, String> CHANNELTYPE_TO_SF_MAP =
            new HashMap<String, String>();

    private static Map<String, String> PRESENTATION_TO_FORM_EXTENSION_MAP =
            new HashMap<String, String>();
    static {
        // XPD-4868 : Enable the workspace EMail Channel [uses GWT]
        CHANNELTYPE_TO_SF_MAP.put("EmailGIPush", FORMS_GWT_FOLDER_KIND); //$NON-NLS-1$
        CHANNELTYPE_TO_SF_MAP.put("openspaceEmailPush", FORMS_GWT_FOLDER_KIND); //$NON-NLS-1$
        CHANNELTYPE_TO_SF_MAP.put("GIGIPull", FORMS_GI_FOLDER_KIND); //$NON-NLS-1$
        CHANNELTYPE_TO_SF_MAP.put("openspaceGWTPull", FORMS_GWT_FOLDER_KIND); //$NON-NLS-1$
        CHANNELTYPE_TO_SF_MAP.put("MobileGWTPull", FORMS_MOBILE_FOLDER_KIND); //$NON-NLS-1$
        CHANNELTYPE_TO_SF_MAP.put("GIGWTPull", FORMS_GWT_FOLDER_KIND); //$NON-NLS-1$
        // TODO - to update when forms have completed their
        // XPD-4868 : Adding support of Workspace Email channel back, given the
        // GI forms are not supported any more for AMX-BPM and Workspace Email
        // is the only GI channel supported for AMX-BPM,
        // uses GWT in background, it is feasible/required to map GI to
        // 'gwt.json'
        PRESENTATION_TO_FORM_EXTENSION_MAP.put("GI", "gwt.json"); //$NON-NLS-1$ //$NON-NLS-2$
        PRESENTATION_TO_FORM_EXTENSION_MAP.put("GWT", "gwt.json"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    private Map<String, String> getChannelTypeToSFMap() {
        return CHANNELTYPE_TO_SF_MAP;
    }

    private Map<String, String> getPresentationToFormExtensionMap() {
        return PRESENTATION_TO_FORM_EXTENSION_MAP;
    }

    private void creeateChannelPropertiesEntries(final IProject project,
            ResourceContainer rContainer, String projectVersion)
            throws Exception, IOException {
        // Generate properties files.
        Channels projectChannels =
                PresentationManager.getInstance().getChannels(project);
        if (projectChannels != null) {
            for (Channel projectChannel : projectChannels.getChannels()) {
                for (TypeAssociation typeAssoc : projectChannel
                        .getTypeAssociations(AMX_BPM_CHANNEL_DESTINATIONS)) {
                    com.tibco.xpd.presentation.channeltypes.ChannelType projectChannelType =
                            typeAssoc.getChannelType();
                    if (projectChannelType != null) {

                        String channelID = projectChannelType.getId() + "_" //$NON-NLS-1$
                                + projectChannel.getName();

                        // All attr. of type: RESOURCE will have this path
                        // prepended (if it's not empty).
                        IPath resourcesPrefixPath =
                                rContainer.getGenerationRootFolder()
                                        .getFullPath()
                                        .append(WP_SERVICE_ARCHIVE_PATH_PREFIX)
                                        .append(projectVersion)
                                        .append(channelID);

                        Properties propetries = new Properties();

                        // Attributes. Get from type and overwrite later the
                        // default values.
                        for (Attribute attr : projectChannelType
                                .getAttributes()) {
                            String name = attr.getName();
                            String value = attr.getResolvedDefaultValue();
                            if (AttributeType.RESOURCE.equals(attr.getType())
                                    && isSet(value)) {
                                value =
                                        resourcesPrefixPath
                                                .append(nullSafe(value))
                                                .toPortableString();
                            }
                            if (name != null) {
                                propetries.setProperty(name, nullSafe(value));
                            }
                        }
                        for (AttributeValue attrValue : typeAssoc
                                .getAttributeValues()) {
                            String name = attrValue.getAttributeName();
                            String value = attrValue.getResolvedValue(true);
                            if (AttributeType.RESOURCE.equals(attrValue
                                    .getType()) && isSet(value)) {
                                value =
                                        resourcesPrefixPath
                                                .append(nullSafe(value))
                                                .toPortableString();
                            }
                            if (name != null) {
                                propetries.setProperty(name, nullSafe(value));
                            }
                        }
                        // Extended Attributes.
                        for (ExtendedAttribute extAttr : typeAssoc
                                .getExtendedAttributes()) {
                            if (extAttr.getName() != null) {
                                propetries.setProperty(extAttr.getName(),
                                        nullSafe(extAttr.getValue()));
                            }
                        }

                        // Save properties to file for the channel.
                        IPath propertyPath =
                                new Path(WP_SERVICE_ARCHIVE_PATH_PREFIX)
                                        .append(projectVersion)
                                        .append(channelID).append(channelID)
                                        .addFileExtension(PROPERTIES_EXTENSION);
                        OutputStream propertiesOutStream =
                                rContainer.getEntryOutStream(propertyPath);
                        String message =
                                String.format("Properties for '%s' channel", //$NON-NLS-1$
                                        channelID);
                        try {
                            propetries.store(propertiesOutStream, message);
                        } finally {
                            if (rContainer.shouldCloseEntryOutStream()
                                    && propertiesOutStream != null) {
                                propertiesOutStream.close();
                                rContainer.refreshPath(propertyPath
                                        .removeLastSegments(1),
                                        IResource.DEPTH_ONE);
                            }
                        }
                    }
                }
            }
        }
    }

    private String nullSafe(String value) {
        return value != null ? value : ""; //$NON-NLS-1$
    }

    private boolean isSet(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /* package */interface ResourceContainer {

        IFolder getGenerationRootFolder();

        OutputStream getEntryOutStream(IPath path) throws Exception;

        boolean shouldCloseEntryOutStream();

        void addFileToContainer(IFile file, IPath path) throws Exception;

        void addFolderToContainer(IFolder folder, IPath path) throws Exception;

        void addAllMembersToContainer(IContainer parent, IPath path)
                throws Exception;

        void closeContainer() throws Exception;

        void refreshPath(IPath path, int refreshType);

    }

    /* package */static class FolderResourceContainer implements
            ResourceContainer {

        private final IFolder gentrationRoot;

        private final boolean derived;

        /**
         * @throws FileNotFoundException
         * 
         */
        public FolderResourceContainer(IFolder gentrationRoot) {
            this.gentrationRoot = gentrationRoot;
            derived = true;
        }

        /**
         * {@inheritDoc}
         */
        public IFolder getGenerationRootFolder() {
            return gentrationRoot;
        }

        /**
         * {@inheritDoc}
         * 
         * @throws CoreException
         * @throws IOException
         */
        public void addFileToContainer(IFile file, IPath path)
                throws IOException, CoreException {
            IPath outFolderPath = path.removeLastSegments(1);
            IFolder outFolder = gentrationRoot.getFolder(outFolderPath);
            ProjectUtil.createFolder(outFolder, derived, null);
            IFile destFile = gentrationRoot.getFile(path);
            if (destFile.exists()) {
                // The file will be overwritten. It shouldn't be normal
                // situation but it's not forbidden.
                LOG.warn(String
                        .format("File '%1$s' existed and was overwritten by '%2$s'.", //$NON-NLS-1$
                                destFile,
                                file));
                destFile.delete(true, null);
            }
            file.copy(destFile.getFullPath(), IResource.FORCE
                    | IResource.DERIVED, null);

        }

        /**
         * {@inheritDoc}
         * 
         * @throws CoreException
         * @throws IOException
         */
        public void addFolderToContainer(IFolder folder, IPath path)
                throws IOException, CoreException {
            IPath outFolderPath = path.removeLastSegments(1);
            IFolder outFolder = gentrationRoot.getFolder(outFolderPath);
            ProjectUtil.createFolder(outFolder, derived, null);
            IFolder destFolder = gentrationRoot.getFolder(path);
            if (!destFolder.exists()) {
                folder.copy(destFolder.getFullPath(), IResource.FORCE
                        | IResource.DERIVED, null);
            } else {
                addAllMembersToContainer(folder, path);
                if (destFolder.exists()) {
                    destFolder.setDerived(derived);
                }
            }

        }

        /**
         * {@inheritDoc}
         * 
         * @throws CoreException
         * @throws IOException
         */
        public void addAllMembersToContainer(IContainer parent, IPath path)
                throws IOException, CoreException {
            IResource[] members = parent.members();
            for (IResource member : members) {
                IPath memberPath = path.append(member.getName());
                if (member instanceof IFile) {
                    addFileToContainer((IFile) member, memberPath);
                } else if (member instanceof IFolder) {
                    addFolderToContainer((IFolder) member, memberPath);
                }
            }

        }

        /**
         * {@inheritDoc}
         * 
         * @throws IOException
         * @throws CoreException
         */
        public void closeContainer() throws IOException, CoreException {
        }

        /**
         * {@inheritDoc}
         * 
         * @throws IOException
         * @throws CoreException
         */
        public OutputStream getEntryOutStream(IPath path) throws IOException,
                CoreException {
            IPath outFolderPath = path.removeLastSegments(1);
            IFolder outFolder = gentrationRoot.getFolder(outFolderPath);
            ProjectUtil.createFolder(outFolder, derived, null);
            return new FileOutputStream(outFolder.getFile(path.lastSegment())
                    .getLocation().toPortableString());
        }

        /**
         * {@inheritDoc}
         */
        public boolean shouldCloseEntryOutStream() {
            return true;
        }

        /**
         * {@inheritDoc}
         */
        public void refreshPath(IPath path, int refreshType) {
            IResource resource = gentrationRoot.findMember(path);
            if (resource != null) {
                try {
                    resource.refreshLocal(refreshType, null);
                } catch (CoreException e) {
                    // ignore.
                }
            }
        }
    }

    /* package */static class ZipResourceContainer implements ResourceContainer {

        /** Presentation module name's post-fix. */
        private static final String PRESENTATION_ARCHIVE_NAME = "wp.war"; //$NON-NLS-1$

        private final IFolder gentrationRoot;

        private final IFile zipFile;

        private final ZipOutputStream zip;

        /**
         * @throws FileNotFoundException
         * 
         */
        public ZipResourceContainer(IFolder gentrationRoot)
                throws FileNotFoundException {
            this.gentrationRoot = gentrationRoot;
            zipFile = gentrationRoot.getFile(PRESENTATION_ARCHIVE_NAME);
            String zipLocation = zipFile.getLocation().toOSString();
            zip = new ZipOutputStream(new FileOutputStream(zipLocation));
        }

        /**
         * {@inheritDoc}
         */
        public IFolder getGenerationRootFolder() {
            return gentrationRoot;
        }

        /**
         * {@inheritDoc}
         * 
         * @throws CoreException
         * @throws IOException
         */
        public void addFileToContainer(IFile file, IPath path)
                throws IOException, CoreException {
            addFileToZip(zip, file, path.toPortableString());
        }

        /**
         * {@inheritDoc}
         * 
         * @throws CoreException
         * @throws IOException
         */
        public void addFolderToContainer(IFolder folder, IPath path)
                throws IOException, CoreException {
            addFolderToZip(zip, folder, path.toOSString());

        }

        /**
         * {@inheritDoc}
         * 
         * @throws CoreException
         * @throws IOException
         */
        public void addAllMembersToContainer(IContainer parent, IPath path)
                throws IOException, CoreException {
            IResource[] members = parent.members();
            for (IResource member : members) {
                if (member instanceof IFile) {
                    IPath memberPath = path.append(member.getName());
                    addFileToContainer((IFile) member, memberPath);
                } else if (member instanceof IFolder) {
                    addFolderToContainer((IFolder) member, path);
                }
            }

        }

        /**
         * {@inheritDoc}
         * 
         * @throws IOException
         * @throws CoreException
         */
        public void closeContainer() throws IOException, CoreException {
            zip.close();
            zipFile.refreshLocal(IResource.DEPTH_ZERO, null);
            zipFile.setDerived(true);
        }

        /**
         * {@inheritDoc}
         * 
         * @throws IOException
         */
        public OutputStream getEntryOutStream(IPath path) throws IOException {
            zip.putNextEntry(new ZipEntry(path.toPortableString()));
            return zip;
        }

        /**
         * {@inheritDoc}
         */
        public boolean shouldCloseEntryOutStream() {
            return false;
        }

        /**
         * Adds file to zipped output stream.
         * 
         * @param zip
         *            the zipped output stream.
         * @param file
         *            the file to add. File must exist.
         * @param path
         *            path of the zip entry.
         * @throws IOException
         *             if there is problem with reading file or writing to
         *             stream.
         * @throws CoreException
         *             if there is problem with eclipse resources.
         */
        private static void addFileToZip(ZipOutputStream zip, IFile file,
                String path) throws IOException, CoreException {
            addEntryToZip(zip, file.getContents(), path);
        }

        /**
         * Adds file to zipped output stream.
         * 
         * @param zip
         *            the zipped output stream.
         * @param folder
         *            the file to add. File must exist.
         * @param path
         *            path of the zip entry.
         * @throws IOException
         *             if there is problem with reading file or writing to
         *             stream.
         * @throws CoreException
         *             if there is problem with eclipse resources.
         */
        private static void addFolderToZip(ZipOutputStream zip, IFolder folder,
                String pathPrefix) throws IOException, CoreException {
            String folderPath =
                    new Path(pathPrefix).append(folder.getName())
                            .toPortableString();
            IResource[] members = folder.members();
            if (members.length == 0) {
                ZipEntry zipEntry = new ZipEntry(folderPath + '/');
                try {
                    zip.putNextEntry(zipEntry);
                } catch (ZipException e) {
                    // ignore if zip entry already exist
                }
            }
            for (IResource member : members) {
                if (member instanceof IFile) {
                    String zipPath =
                            new Path(folderPath).append(member.getName())
                                    .toPortableString();
                    addFileToZip(zip, (IFile) member, zipPath);
                } else if (member instanceof IFolder) {
                    addFolderToZip(zip, (IFolder) member, folderPath);
                }
            }
        }

        /**
         * Adds content of input stream to zipped output stream.
         * 
         * @param zip
         *            the zipped output stream.
         * @param in
         *            the input stream. Its content will be added as an entry's
         *            contents.
         * @param path
         *            path of the zip entry.
         * @throws IOException
         *             if there is problem with reading file or writing to
         *             stream.
         */
        private static void addEntryToZip(ZipOutputStream zip, InputStream in,
                String path) throws IOException {
            ZipEntry zipEntry = new ZipEntry(path);
            try {
                zip.putNextEntry(zipEntry);
                byte buffer[] = new byte[BUFFER_SIZE];
                int len;
                while ((len = in.read(buffer)) > 0) {
                    zip.write(buffer, 0, len);
                }
                in.close();
            } catch (ZipException e) {
                // ignore if zip entry already exist
            }
        }

        /**
         * {@inheritDoc}
         */
        public void refreshPath(IPath path, int refreshType) {
            // Do nothing. Do not need refresh.
        }
    }

    /**
     * Gets the platform dependent form file.
     * 
     * @param activity
     *            A User Task activity referencing a Form
     * @return The form file or null.
     */
    private IFile getUserTaskChannelFormFile(Activity activity,
            ChannelType wpChannel) {
        IFile formFile = null;
        String channelTypeId =
                PresentationManager.getInstance()
                        .findChannelTypeIdByComponents(wpChannel
                                .getTargetChannelType().getLiteral(),
                                wpChannel.getPresentationChannelType()
                                        .getLiteral(),
                                wpChannel.getImplementationType().getLiteral());

        /*
         * ChannelId contains the channel type id plus the description. We have
         * indexed the map with reference to the channel type ids. This
         * tokenizes to get the channel type id.
         */

        FormImplementation formImpl =
                TaskObjectUtil.getUserTaskFormImplementation(activity);
        IProject project = WorkingCopyUtil.getProjectFor(activity);
        String sfKind = getChannelTypeToSFMap().get(channelTypeId);
        SpecialFolder formsImplSF =
                SpecialFolderUtil.getSpecialFolderOfKind(project, sfKind);
        // Platform independent implementation.
        String formExtension =
                getPresentationToFormExtensionMap().get(wpChannel
                        .getPresentationChannelType().getLiteral());
        if (formImpl != null
                && FormImplementationType.FORM.equals(formImpl.getFormType())) {
            // User specified implementation.
            String url = formImpl.getFormURI();
            if (url != null) {
                if (url.startsWith(TaskObjectUtil.FORM_SCHEMA)) {
                    String path =
                            url.substring(TaskObjectUtil.FORM_SCHEMA.length());
                    if (formsImplSF != null && formsImplSF.getFolder() != null) {
                        String formPath =
                                (new Path(path)).removeFileExtension()
                                        .addFileExtension(formExtension)
                                        .toPortableString();
                        formFile =
                                project.getFile(formsImplSF.getFolder()
                                        .getProjectRelativePath()
                                        .append(formPath).toString());
                    }
                }
            }
        } else if (formImpl == null) {
            // Default implementation.
            Process process = activity.getProcess();
            Package pkg = process.getPackage();
            if (formsImplSF != null && formsImplSF.getFolder() != null) {
                formFile =
                        project.getFile(formsImplSF
                                .getFolder()
                                .getProjectRelativePath()
                                .append(".default") //$NON-NLS-1$
                                .append(pkg.getName())
                                .append(process.getName())
                                .append(activity.getName())
                                .append(activity.getName())
                                .addFileExtension(formExtension).toString());
            }
        }

        return formFile;
    }

    /**
     * Creates N2 specific ServiceArchiveDescriptorType based on the provided
     * model.
     * 
     * @param timestamp
     * 
     * @param wmModel
     *            the source model.
     * @return the ServiceArchiveDescriptorType based on provided model.
     */
    private ServiceArchiveDescriptorType createWPDescriptor(
            GenerationContext ctx) {

        String version =
                PluginManifestHelper
                        .getUpdatedBundleVersion(CompositeUtil
                                .getVersionNumber(ctx.getProject()), ctx
                                .getTimestamp());

        WPFactory f = WPFactory.eINSTANCE;
        ServiceArchiveDescriptorType wpDescriptor =
                f.createServiceArchiveDescriptorType();
        ChannelsType channelsType = f.createChannelsType();
        wpDescriptor.setChannels(channelsType);

        // Get project specific channel definition. Create WP descriptor
        // channels.
        List<ChannelType> wpChannels = new ArrayList<ChannelType>();
        Channels projectChannels =
                PresentationManager.getInstance().getChannels(ctx.getProject());
        if (projectChannels != null) {
            for (Channel projectChannel : projectChannels.getChannels()) {
                for (TypeAssociation typeAssoc : projectChannel
                        .getTypeAssociations(AMX_BPM_CHANNEL_DESTINATIONS)) {
                    com.tibco.xpd.presentation.channeltypes.ChannelType projectChannelType =
                            typeAssoc.getChannelType();
                    if (projectChannelType != null) {
                        ChannelType wpChannel = f.createChannelType();
                        String channelID = projectChannelType.getId() + "_" //$NON-NLS-1$
                                + projectChannel.getName();
                        wpChannel.setChannelId(channelID);
                        wpChannel.setName(projectChannel.getName());
                        wpChannel.setDescription(projectChannel.getName());
                        wpChannel
                                .setTargetChannelType(com.tibco.n2.common.channeltype.ChannelType
                                        .get(projectChannelType.getTarget()
                                                .getId()));
                        wpChannel
                                .setPresentationChannelType(com.tibco.n2.common.channeltype.PresentationType
                                        .get(projectChannelType
                                                .getPresentation().getId()));
                        wpChannel
                                .setImplementationType(com.tibco.n2.common.channeltype.ImplementationType
                                        .get(projectChannelType
                                                .getImplementation().getId()));
                        wpChannel.setDomain(""); // TODO check domain. //$NON-NLS-1$
                        wpChannel.setDefaultChannel(projectChannel.isDefault());
                        ChannelExtentionType channelExtention =
                                f.createChannelExtentionType();
                        String projectVersion =
                                PluginManifestHelper
                                        .getUpdatedBundleVersion(CompositeUtil
                                                .getVersionNumber(ctx
                                                        .getProject()), ctx
                                                .getTimestamp());
                        channelExtention.setLocation(new Path(projectVersion)
                                .append(channelID).toPortableString());
                        channelExtention.setFilename(new Path(channelID)
                                .addFileExtension(PROPERTIES_EXTENSION)
                                .toPortableString());
                        wpChannel.setExtensionConfig(channelExtention);
                        wpChannels.add(wpChannel);
                    }
                }
            }
        }
        Collection<Package> packages =
                BRMUtils.getN2ProcessPackages(ctx.getProject());
        Collection<Activity> manualN2Activities =
                BRMUtils.getN2ManualActivities(packages);
        Collection<Process> businessServices =
                BRMUtils.getBusinessServices(packages);
        Collection<Process> caseServices = BRMUtils.getCaseServices(packages);
        Collection<Process> standardPageFlows =
                BRMUtils.getStandardPageFlows(packages);

        for (ChannelType wpChannel : wpChannels) {

            // Manual activities on Process
            // Can by implemented as UserTask or Pageflow.
            for (Activity activity : manualN2Activities) {
                WorkTypeType wpWorkType = f.createWorkTypeType();

                wpWorkType.setGuid(WORK_TYPE_ID_PREFIX + activity.getId());
                wpWorkType.setName(activity.getName());
                wpWorkType.setVersion(version);

                FormImplementation userTaskFormImplementation =
                        TaskObjectUtil.getUserTaskFormImplementation(activity);
                // If userTastFormImplementation is 'null' then it means default
                // form option is selected.
                if (userTaskFormImplementation == null
                        || isFormUserTask(activity)) { // form
                    IFile formFile =
                            getUserTaskChannelFormFile(activity, wpChannel);
                    if (formFile == null) {
                        String msg =
                                String.format(Messages.WPGenerator_InvalidFormRef_message,
                                        activity.getName());
                        LOG.error(msg);
                        ctx.getProblems().add(createWarnStatus(msg));
                    }
                    if (formFile != null && !formFile.exists()) {
                        String msg =
                                String.format(Messages.WPGenerator_FormNoExist_message,
                                        formFile.getFullPath()
                                                .toPortableString(),
                                        activity.getName());
                        LOG.error(msg);
                        ctx.getProblems().add(createWarnStatus(msg));
                    }
                    FormType wpForm = f.createFormType();
                    String relativePath =
                            formFile != null ? getRelativePath(new Path(
                                    wpChannel.getChannelId()),
                                    formFile,
                                    ctx.getTimestamp()) : ""; //$NON-NLS-1$
                    wpForm.setRelativePath(relativePath);
                    wpForm.setName(formFile != null ? formFile.getName() : ""); //$NON-NLS-1$
                    wpForm.setFormIdentifier(new Path(relativePath)
                            .append(wpForm.getName()).toPortableString());

                    wpForm.setVersion(version);
                    wpWorkType.setForm(wpForm);
                    wpChannel.getWorkType().add(wpWorkType);
                    ctx.getProcessRefMap().put(wpForm, activity);

                } else if (isUserDefinedFormUserTask(activity)) { // custom form
                    FormImplementation formImplementation =
                            TaskObjectUtil
                                    .getUserTaskFormImplementation(activity);
                    FormType wpForm = f.createFormType();
                    wpForm.setName(formImplementation.getFormURI());
                    wpForm.setRelativePath(CUSTOM_FORM_RELATIVE_PATH);
                    wpForm.setFormIdentifier(formImplementation.getFormURI());
                    wpForm.setVersion(version);
                    wpWorkType.setForm(wpForm);
                    wpChannel.getWorkType().add(wpWorkType);
                    ctx.getProcessRefMap().put(wpForm, activity);

                } else if (isPageFlow(activity)) { // page flow
                    Process pageflowProcess =
                            TaskObjectUtil.getUserTaskPageflowProcess(activity);

                    if (pageflowProcess != null) {
                        // Check if page flow is in the same project.
                        WorkingCopy wc =
                                WorkingCopyUtil
                                        .getWorkingCopyFor(pageflowProcess);
                        IResource resource = wc.getEclipseResources().get(0);

                        if (resource == null
                                || !ctx.getProject()
                                        .equals(resource.getProject())) {
                            /*
                             * XPD-2608 : the code below for logging of the
                             * error message and also adding of warning message
                             * for cross project page flow reference is no
                             * longer required
                             */
                            // String message =
                            // String.format(Messages.WPGenerator_PageFlowFromOtherProject_message2,
                            // activity.getName());
                            // LOG.error(message);
                            // ctx.getProblems().add(createWarnStatus(message));
                            // continue;

                            /*
                             * XPD-2608 : add the other pageflow from cross
                             * project in the above standardPageFlows list to
                             * add the service:page-flow info in the wpModel.xml
                             */
                            IProject crossProject = resource.getProject();
                            Collection<Package> crossProjProcessPckgs =
                                    BRMUtils.getN2ProcessPackages(crossProject);
                            if (crossProjProcessPckgs.size() > 0) {
                                Collection<Process> crossProjStandardPageFlows =
                                        BRMUtils.getStandardPageFlows(crossProjProcessPckgs);
                                if (crossProjStandardPageFlows.size() > 0) {
                                    standardPageFlows
                                            .addAll(crossProjStandardPageFlows);
                                }
                            }
                        }

                        String pageFlowId = pageflowProcess.getId();
                        PageFlowType wpPageFlow =
                                ctx.getPageFlows(wpChannel.getChannelId())
                                        .get(pageFlowId);
                        if (wpPageFlow == null) {
                            wpPageFlow = f.createPageFlowType();
                            ctx.getPageFlows(wpChannel.getChannelId())
                                    .put(pageFlowId, wpPageFlow);
                            fillPageFlowType(wpPageFlow,
                                    pageflowProcess,
                                    wpChannel,
                                    ctx);
                        }
                        PageFlowRefType wpPageFlowRef =
                                f.createPageFlowRefType();
                        wpPageFlowRef.setRefId(pageFlowId);
                        wpWorkType.setPageFlowRef(wpPageFlowRef);
                        wpChannel.getWorkType().add(wpWorkType);
                    }
                }
            }// Manual activities on Process :END

            // Business services.
            for (Process businessService : businessServices) {
                BusinessServiceType wpBusinnesService =
                        f.createBusinessServiceType();
                fillPageFlowType(wpBusinnesService,
                        businessService,
                        wpChannel,
                        ctx);
                // Privileges are not added to business service. The privileges
                // to start business service are associated with the
                // "Start Business Service" model system action instead.
                wpChannel.getBusinessService().add(wpBusinnesService);
            }

            /* Add case services (CaseService is a Business Service) */
            for (Process caseService : caseServices) {

                BusinessServiceType wpCaseService =
                        f.createBusinessServiceType();
                fillPageFlowType(wpCaseService, caseService, wpChannel, ctx);
                wpChannel.getBusinessService().add(wpCaseService);
            }

            // Top level page flows.

            for (Process standardPageFlow : standardPageFlows) {
                String pageFlowId = standardPageFlow.getId();
                PageFlowType wpPageFlow =
                        ctx.getPageFlows(wpChannel.getChannelId())
                                .get(pageFlowId);
                if (wpPageFlow == null) {
                    wpPageFlow = f.createPageFlowType();
                    ctx.getPageFlows(wpChannel.getChannelId()).put(pageFlowId,
                            wpPageFlow);
                    fillPageFlowType(wpPageFlow,
                            standardPageFlow,
                            wpChannel,
                            ctx);
                }
                wpChannel.getPageFlow().add(wpPageFlow);
            }
        }
        for (ChannelType wpChannel : wpChannels) {
            if (!wpChannel.getWorkType().isEmpty()
                    || !wpChannel.getBusinessService().isEmpty()
                    || !wpChannel.getPageFlow().isEmpty()) {
                // don't add empty channels
                channelsType.getChannel().add(wpChannel);
            }
        }
        return wpDescriptor;
    }

    private void fillPageFlowType(PageFlowType wpPageFlow,
            Process pageflowProcess, ChannelType wpChannel,
            GenerationContext ctx) {
        String version =
                PluginManifestHelper
                        .getUpdatedBundleVersion(CompositeUtil
                                .getVersionNumber(ctx.getProject()), ctx
                                .getTimestamp());
        WPFactory f = WPFactory.eINSTANCE;
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(pageflowProcess);
        if (wc == null) {
            LOG.error(String
                    .format("PageFlow has to be contained inside a resource.", //$NON-NLS-1$
                            pageflowProcess.getName()));
        }
        IResource resource = wc.getEclipseResources().get(0);
        wpPageFlow.setId(pageflowProcess.getId());
        wpPageFlow.setName(pageflowProcess.getName());
        wpPageFlow.setModuleName(resource.getFullPath().toPortableString());
        /*
         * XPD-2608 : do not set time stamp or qualifier in the version if it is
         * a cross project reference
         */

        if (!ctx.getProject().equals(resource.getProject())) {
            /* XPD-2608 : remove .qualifier before setting the version */
            String pageflowProcessVersion =
                    Xpdl2ModelUtil
                            .getProcessPackageVersionNumber(pageflowProcess
                                    .getPackage());
            if (null != pageflowProcessVersion) {
                Version pageflowProjVer = new Version(pageflowProcessVersion);
                String qualifier = pageflowProjVer.getQualifier();
                if (null != qualifier && qualifier.length() > 0) {
                    String strVersion =
                            pageflowProjVer.getMajor() + "."
                                    + pageflowProjVer.getMinor() + "."
                                    + pageflowProjVer.getMicro();
                    wpPageFlow.setModuleVersion(strVersion);
                } else {
                    wpPageFlow.setModuleVersion(pageflowProcessVersion);
                }
            }
        } else {
            wpPageFlow.setModuleVersion(getModuleVersion(pageflowProcess
                    .getPackage(), ctx.getTimestamp()));
        }
        String xpdlFileName =
                WorkingCopyUtil.getWorkingCopyFor(pageflowProcess)
                        .getEclipseResources().get(0).getName();
        wpPageFlow.setUrl(new Path(resource.getProject().getName())
                .append(N2PENamingUtils.COMPOSITE_OUTPUTFOLDER_NAME)
                .append(BPELN2Utils.BPEL_ROOT_OUTPUTFOLDER_NAME)
                .append(BPELN2Utils.PF_OUTPUTFOLDER_NAME).append(xpdlFileName)
                .append(pageflowProcess.getName())
                .addFileExtension(BPEL_EXTENSION).toPortableString());

        // processRefMap.put(wpPageFlow, pageflowProcess);

        /*
         * XPD-5783 : Do not add 'page-activity' information if there is a cross
         * project dependency.
         */
        if (ctx.getProject().equals(resource.getProject())) {
            Collection<Activity> userTasks =
                    BRMUtils.getManualPageFlowActivities(pageflowProcess);
            for (Activity pfActivity : userTasks) {
                PageActivityType pageActivityType = f.createPageActivityType();
                pageActivityType.setId(pfActivity.getId());
                pageActivityType.setName(pfActivity.getName());

                FormType wpForm = f.createFormType();
                FormImplementation userTaskFormImplementation =
                        TaskObjectUtil
                                .getUserTaskFormImplementation(pfActivity);
                // If userTastFormImplementation is 'null' then it means default
                // form option is selected.
                if (userTaskFormImplementation == null
                        || isFormUserTask(pfActivity)) {
                    IFile pfActivityFormFile =
                            getUserTaskChannelFormFile(pfActivity, wpChannel);

                    if (pfActivityFormFile == null) {
                        String message =
                                String.format(Messages.WPGenerator_InvalidFormRef_message,
                                        pfActivity.getName());
                        LOG.error(message);
                        ctx.getProblems().add(createWarnStatus(message));
                    }
                    if (pfActivityFormFile != null
                            && !pfActivityFormFile.exists()) {
                        String message =
                                String.format(Messages.WPGenerator_FormNoExist_message,
                                        pfActivityFormFile.getFullPath()
                                                .toPortableString(),
                                        pfActivity.getName());
                        LOG.error(message);
                        ctx.getProblems().add(createWarnStatus(message));
                    }

                    String relativePath =
                            pfActivityFormFile != null ? getRelativePath(new Path(
                                    wpChannel.getChannelId()),
                                    pfActivityFormFile,
                                    ctx.getTimestamp())
                                    : ""; //$NON-NLS-1$
                    wpForm.setRelativePath(relativePath);
                    wpForm.setName(pfActivityFormFile != null ? pfActivityFormFile
                            .getName() : ""); //$NON-NLS-1$
                    wpForm.setVersion(version);
                    wpForm.setFormIdentifier(new Path(relativePath)
                            .append(wpForm.getName()).toPortableString());
                    pageActivityType.setPageReference(wpForm);
                    ctx.getProcessRefMap().put(wpForm, pfActivity);
                    wpPageFlow.getPageActivity().add(pageActivityType);
                } else if (isUserDefinedFormUserTask(pfActivity)) {
                    FormImplementation formImplementation =
                            TaskObjectUtil
                                    .getUserTaskFormImplementation(pfActivity);
                    wpForm.setName(formImplementation.getFormURI());
                    wpForm.setVersion(version);
                    wpForm.setRelativePath(CUSTOM_FORM_RELATIVE_PATH);
                    wpForm.setFormIdentifier(formImplementation.getFormURI());
                    pageActivityType.setPageReference(wpForm);
                    ctx.getProcessRefMap().put(wpForm, pfActivity);
                    wpPageFlow.getPageActivity().add(pageActivityType);
                }
            }
        }
    }

    /**
     * @param package1
     * @return
     */
    private String getModuleVersion(Package xpdlPackage, String timestamp) {
        String vn = Xpdl2ModelUtil.getProcessPackageVersionNumber(xpdlPackage);
        String version =
                PluginManifestHelper.getUpdatedBundleVersion(vn, timestamp);
        return version;
    }

    private boolean isFormUserTask(Activity activity) {
        FormImplementation formImplementation =
                TaskObjectUtil.getUserTaskFormImplementation(activity);
        if (formImplementation != null
                && FormImplementationType.FORM.equals(formImplementation
                        .getFormType())) {
            return true;
        }
        return false;
    }

    private boolean isUserDefinedFormUserTask(Activity activity) {
        FormImplementation formImplementation =
                TaskObjectUtil.getUserTaskFormImplementation(activity);
        if (formImplementation != null
                && FormImplementationType.USER_DEFINED
                        .equals(formImplementation.getFormType())) {
            return true;
        }
        return false;
    }

    private boolean isPageFlow(Activity activity) {
        FormImplementation formImplementation =
                TaskObjectUtil.getUserTaskFormImplementation(activity);
        if (formImplementation != null
                && FormImplementationType.PAGEFLOW.equals(formImplementation
                        .getFormType())) {
            return true;
        }
        return false;
    }

    /**
     * Generate work presentation descriptor resource in the context of the
     * provided resource set.
     * 
     * @param wmModel
     *            the source model.
     * @param generationRoot
     *            the generation resources structure root folder.
     * @param rs
     *            context resource set for generated resources <code>null</code>
     *            if resource should not be generated (all channels are empty).
     */
    private Resource createWPDescriptorResource(
            ServiceArchiveDescriptorType wpDescriptor, IFolder generationRoot,
            ResourceSet rs) {

        Map<String, Object> extensionToFactoryMap =
                Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap();
        Object previousXMLFactory = extensionToFactoryMap.get(XML_EXTENSION);
        extensionToFactoryMap.put(XML_EXTENSION, new WPResourceFactoryImpl());

        // create work presentation resource
        IPath baseWPPath = generationRoot.getFullPath();

        extensionToFactoryMap.put(XML_EXTENSION, new WPResourceFactoryImpl());
        try {
            com.tibco.n2.wp.archive.service.DocumentRoot docRoot =
                    WPFactory.eINSTANCE.createDocumentRoot();
            docRoot.setServiceArchiveDescriptor(wpDescriptor);
            String filePath =
                    baseWPPath.append(WORK_PRESENTATION_DESCRIPTOR_NAME)
                            .toPortableString();
            URI resourceURI = URI.createPlatformResourceURI(filePath, true);
            Resource workPresentationResource = rs.createResource(resourceURI);
            workPresentationResource.getContents().add(docRoot);
            return workPresentationResource;
        } finally {
            extensionToFactoryMap.put(XML_EXTENSION, previousXMLFactory);
        }
    }

    private String getRelativePath(IPath path, IFile formFile, String timestamp) {
        IPath projectRelativePath = formFile.getProjectRelativePath();
        projectRelativePath = projectRelativePath.removeLastSegments(1);
        int fragmentsToRemove = 2;
        projectRelativePath =
                projectRelativePath.removeFirstSegments(fragmentsToRemove);
        IProject project = formFile.getProject();
        String projectVersion =
                PluginManifestHelper.getUpdatedBundleVersion(CompositeUtil
                        .getVersionNumber(project), timestamp);
        projectRelativePath =
                new Path(projectVersion).append(path)
                        .append(projectRelativePath);
        String toReturn = projectRelativePath.toPortableString();
        return toReturn;
    }

    private IStatus createWarnStatus(String message) {
        return new Status(IStatus.WARNING, WPActivator.PLUGIN_ID, message);
    }

}
