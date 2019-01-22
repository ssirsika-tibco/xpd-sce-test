/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.n2.daa.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.wst.wsdl.PortType;
import org.osgi.framework.Version;

import com.tibco.amf.dependency.osgi.ImportPackage;
import com.tibco.amf.dependency.osgi.OsgiFactory;
import com.tibco.amf.dependency.osgi.VersionRange;
import com.tibco.amf.sca.model.componenttype.BaseReference;
import com.tibco.amf.sca.model.componenttype.BaseService;
import com.tibco.amf.sca.model.composite.ComponentReference;
import com.tibco.amf.sca.model.composite.PromotedReference;
import com.tibco.amf.sca.model.extensionpoints.Wsdl11Interface;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.SharedResourceUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.wsdlgen.transform.XtendTransformerXpdl2Wsdl;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

/**
 * @author mtorres
 * 
 */
public class N2PECompositeUtil {

    /**
     * Imported by all BDS components.
     */
    private static final String BDS_PACKAGE_IMPORT =
            "com.tibco.bds.core.deployment"; //$NON-NLS-1$

    /** HttpClient resource type. */
    public static final QName HTTP_CLIENT_RES_TYPE = new QName(
            "http://xsd.tns.tibco.com/amf/models/sharedresource/httpclient", //$NON-NLS-1$
            "HttpClientConfiguration"); //$NON-NLS-1$

    /** JDBC resource type. */
    public static final QName JDBC_RES_TYPE = new QName(
            "http://xsd.tns.tibco.com/amf/models/sharedresource/jdbc", //$NON-NLS-1$
            "JdbcDataSource"); //$NON-NLS-1$

    /** SMTP resource type. */
    public static final QName SMTP_RES_TYPE = new QName(
            "http://xsd.tns.tibco.com/amf/models/sharedresource/smtp", //$NON-NLS-1$
            "SmtpConfiguration"); //$NON-NLS-1$

    /** String resource type. */
    public static final QName STRING_RES_TYPE = new QName(
            "http://www.w3.org/2001/XMLSchema", //$NON-NLS-1$
            "string"); //$NON-NLS-1$

    /** JMS connection factory resource type. */
    public static final QName JMS_CONNECTION_FACTORY_RES_TYPE = new QName(
            "http://xsd.tns.tibco.com/amf/models/sharedresource/jms", //$NON-NLS-1$
            "JMSConnectionFactory"); //$NON-NLS-1$

    /** JMS connection factory configuration resource type. */
    public static final QName JMS_CONNECTION_FACTORY_CONFIGURATION_RES_TYPE =
            new QName("http://xsd.tns.tibco.com/amf/models/sharedresource/jms", //$NON-NLS-1$
                    "JMSConnectionFactoryConfiguration"); //$NON-NLS-1$

    /** JMS destination resource type. */
    public static final QName JMS_DESTINATION_RES_TYPE = new QName(
            "http://xsd.tns.tibco.com/amf/models/sharedresource/jms", //$NON-NLS-1$
            "JMSDestination"); //$NON-NLS-1$

    /** JMS destination configuration resource type. */
    public static final QName JMS_DESTINATION_CONFIGURATION_RES_TYPE =
            new QName("http://xsd.tns.tibco.com/amf/models/sharedresource/jms", //$NON-NLS-1$
                    "JMSDestinationConfiguration"); //$NON-NLS-1$

    public static final Pattern SVAR_PATTERN = Pattern.compile("^%%.*%%$"); //$NON-NLS-1$

    /** Collection of resource type that should be substituted. */
    public static final Collection<QName> SUBSTITUABLE_PROPERTY_TYPES =
            Collections.unmodifiableCollection(Arrays
                    .asList(HTTP_CLIENT_RES_TYPE,
                            JDBC_RES_TYPE,
                            SMTP_RES_TYPE,
                            STRING_RES_TYPE,
                            JMS_CONNECTION_FACTORY_RES_TYPE,
                            JMS_CONNECTION_FACTORY_CONFIGURATION_RES_TYPE,
                            JMS_DESTINATION_RES_TYPE,
                            JMS_DESTINATION_CONFIGURATION_RES_TYPE));

    public static final String COMMON_MODEL__PACKAGE_ID =
            "com.tibco.n2.model.common"; //$NON-NLS-1$

    private static final String SEPARATOR = "."; //$NON-NLS-1$

    public static String getLowerRangeFromVersion(String strVersion) {
        Version version = new Version(strVersion);
        strVersion = strVersion.toLowerCase();
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(version.getMajor());
        strBuilder.append(N2PECompositeUtil.SEPARATOR);
        strBuilder.append(version.getMinor());
        strBuilder.append(N2PECompositeUtil.SEPARATOR);
        strBuilder.append(version.getMicro());
        return strBuilder.toString();
    }

    public static String getUpperRangeFromVersion(String strVersion) {
        Version version = new Version(strVersion);
        strVersion = strVersion.toLowerCase();
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(version.getMajor() + 1);
        strBuilder.append(N2PECompositeUtil.SEPARATOR);
        strBuilder.append(version.getMinor());
        strBuilder.append(N2PECompositeUtil.SEPARATOR);
        strBuilder.append(version.getMicro());
        return strBuilder.toString();
    }

    public static ImportPackage getCommonImportPackage() {
        ImportPackage ip = OsgiFactory.eINSTANCE.createImportPackage();
        ip.setName(N2PECompositeUtil.COMMON_MODEL__PACKAGE_ID);
        VersionRange vr = OsgiFactory.eINSTANCE.createVersionRange();
        vr.setLower("2.0.0"); //$NON-NLS-1$
        vr.setLowerIncluded(true);
        vr.setUpper("3.0.0"); //$NON-NLS-1$
        vr.setUpperIncluded(false);
        ip.setRange(vr);
        return ip;
    }

    public static ImportPackage getBDSImportPackage() {
        ImportPackage ip = OsgiFactory.eINSTANCE.createImportPackage();
        ip.setName(BDS_PACKAGE_IMPORT);
        VersionRange vr = OsgiFactory.eINSTANCE.createVersionRange();
        vr.setLower("1.3.0"); //$NON-NLS-1$
        vr.setLowerIncluded(true);
        vr.setUpper("2.0.0"); //$NON-NLS-1$
        vr.setUpperIncluded(false);
        ip.setRange(vr);
        return ip;
    }

    public static int getMajorVersionComponent(String version,
            int defaultVersion) {
        if (version != null) {
            try {
                return (new Version(version)).getMajor();
            } catch (Exception e) {
                // ignore
            }
        }
        return defaultVersion;
    }

    /**
     * 
     * @param project
     * @param service
     * @return Get the Process EndPoint participant for the given COmposite
     *         Service (gets first in project BUT this shouldn't matter because
     *         if more than one participant for same end point, validation
     *         ensures that all are same)
     */
    public static Participant getParticipantForService(IProject project,
            BaseService service) {

        PortType pt = ((Wsdl11Interface) service.getInterface()).getPortType();
        return getParticipantForPortType(project, pt, service.getName(), true);
    }

    /**
     * 
     * @param project
     * @param reference
     * @return Get the Process EndPoint participant for the given composite
     *         Service reference (gets first in project BUT this shouldn't
     *         matter because if more than one participant for same end point,
     *         validation ensures that all are same)
     */
    public static Participant getParticipantForReference(IProject project,
            BaseReference reference) {

        PortType pt =
                ((Wsdl11Interface) reference.getInterface()).getPortType();

        String componentReferenceName = reference.getName();

        if (reference instanceof PromotedReference) {
            /*
             * In case of promoted reference the name is different then
             * participant. But it should be one-to-one match for component
             * reference. The assumption is that there cannot be two
             * participants for same port type and transport within the project
             * OR if there are they are exact copies.
             */
            PromotedReference promotedReference = (PromotedReference) reference;
            for (ComponentReference cr : promotedReference.getPromotions()) {
                componentReferenceName = cr.getName();
            }
        }

        /*
         * Sid XPD-7543: If the target port type is REST pass thru WSDL look for
         * REST consumer participant
         */
        if (isPassThroughRestPortType(pt)) {
            return getRESTConsumerParticipant(project, componentReferenceName);

        } else {
            return getParticipantForPortType(project,
                    pt,
                    componentReferenceName,
                    false);
        }
    }

    /**
     * Sid XPD-7543: Find the XPDL participant matching the given
     * {@link ComponentReference} name.
     * <p>
     * {@link ComponentReference}'s added to composite by PE deploy code should
     * be given name = "<Name><Id>" from the REST service consumer participant
     * 
     * @param project
     * @param componentReferenceName
     * 
     * @return XPDL participant for given component reference name.
     */
    private static Participant getRESTConsumerParticipant(IProject project,
            String componentReferenceName) {

        ArrayList<IResource> xpdlFiles =
                SpecialFolderUtil.getResourcesInSpecialFolderOfKind(project,
                        Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND,
                        Xpdl2ResourcesConsts.XPDL_EXTENSION);

        for (IResource xpdlFile : xpdlFiles) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(xpdlFile);

            if (wc instanceof Xpdl2WorkingCopyImpl) {
                /* Check package participants. */
                Package pkg = (Package) wc.getRootElement();

                Participant participant =
                        getRESTConsumerParticipant(pkg.getParticipants(),
                                componentReferenceName);

                if (participant != null) {
                    return participant;
                }

                /* Check process participants. */
                for (Process process : pkg.getProcesses()) {
                    participant =
                            getRESTConsumerParticipant(process.getParticipants(),
                                    componentReferenceName);

                    if (participant != null) {
                        return participant;
                    }
                }

            }
        }

        return null;
    }

    /**
     * Sid XPD-7543: Find the XPDL participant matching the given
     * {@link ComponentReference} name. in the set of participants.
     * 
     * @param participants
     * @param componentReferenceName
     * 
     * @return XPDL participant for given component reference name or
     *         <code>null</code> if not contained in set.
     */
    private static Participant getRESTConsumerParticipant(
            Collection<Participant> participants, String componentReferenceName) {

        for (Participant participant : participants) {
            if (SharedResourceUtil.isRestConsumer(participant)) {
                String derivedName = participant.getId();

                if (componentReferenceName.equals(derivedName)) {
                    return participant;
                }
            }
        }
        return null;
    }

    /**
     * Sid XPD-7543: Check if this is the REST BT pass through port type.
     * 
     * @param pt
     *            port type to check.
     * @return 'true' if this is a REST BT pass through port type.
     */
    private static boolean isPassThroughRestPortType(PortType pt) {
        return pt != null && pt.getQName() != null
                && "http://www.tibco.com/rsbtPassThrough/".equals(pt.getQName() //$NON-NLS-1$
                        .getNamespaceURI()) && "RESTPassThroughService" //$NON-NLS-1$
                .equals(pt.getQName().getLocalPart());
    }

    /**
     * 
     * @param project
     * @param pt
     * @param nameDerivedFromParticipant
     * @param isService
     *            true if this method is called for promoted services
     * @return Get the Process EndPoint participant for the given WSDL port type
     *         reference (gets first in project BUT this shouldn't matter
     *         because if more than one participant for same end point,
     *         validation ensures that all are same)
     */
    private static Participant getParticipantForPortType(IProject project,
            PortType pt, String nameDerivedFromParticipant, boolean isService) {

        String portNsURI = pt.getQName().getNamespaceURI();
        String portLocalName = pt.getQName().getLocalPart();
        IndexerItemImpl queryItem = new IndexerItemImpl();

        /*
         * to find a participant for a given promoted service in the composite,
         * we were using port_type_name and wsdl_namespace to query from the
         * indexer. this information was not sufficient because if the same port
         * type from the same wsdl (in ProjectA) is used in a different project
         * ProjectB (referencing ProjectA) then the participant from ProjectA if
         * it is found first in the indexer would be used for generating service
         * bindings for ProjectB. This is not desirable if the participant in
         * ProjectB has added additional bindings (i.e the
         * ParticipantSharedResource for participant in ProjectB is different
         * from ProjectA). So decided to use project name in the indexer query
         * to get the appropriate participant to generate corresponding bindings
         */
        queryItem.set(ProcessUIUtil.WEBSERVICE_REF_COLUMN_PORT_TYPE_NAMESPACE,
                portNsURI);
        queryItem.set(ProcessUIUtil.WEBSERVICE_REF_COLUMN_PORT_TYPE_NAME,
                portLocalName);
        queryItem.set(ProcessUIUtil.INTERNAL_PROJECT, project.getName());

        Participant indexerParticipant =
                getIndexerParticipant(queryItem, nameDerivedFromParticipant);

        if (indexerParticipant == null && isService) {
            /*
             * XPD-1829 no result found & is Service we need to strip out
             * checksum from the namespace URI when this method is called for
             * promoted services. The reason being,
             * ActivityWebServiceReferenceIndexProvider runs when WSDL might not
             * be present & at that time guesses the WSDL namespace URI
             */
            portNsURI =
                    XtendTransformerXpdl2Wsdl
                            .getNameSpaceWithoutCheckSum(portNsURI);
            queryItem
                    .set(ProcessUIUtil.WEBSERVICE_REF_COLUMN_PORT_TYPE_NAMESPACE,
                            portNsURI);
            indexerParticipant =
                    getIndexerParticipant(queryItem, nameDerivedFromParticipant);
        }
        return indexerParticipant;
    }

    /**
     * 
     * @param queryItem
     * @param nameDerivedFromParticipant
     * @return Find index entry for named participant
     */
    private static Participant getIndexerParticipant(IndexerItemImpl queryItem,
            String nameDerivedFromParticipant) {

        Collection<IndexerItem> result =
                XpdResourcesPlugin
                        .getDefault()
                        .getIndexerService()
                        .query("com.tibco.xpd.analyst.resources.xpdl2.indexing.webServiceReference", //$NON-NLS-1$
                                queryItem);

        for (IndexerItem item : result) {

            URI uri = URI.createURI(item.getURI());
            String participantId =
                    item.get(ProcessUIUtil.WEBSERVICE_REF_COLUMN_ENDPOINT_PARTICIPANT_ID);
            URI participantURI =
                    uri.trimFragment().appendFragment(participantId);
            Participant participant =
                    (Participant) XpdResourcesPlugin.getDefault()
                            .getEditingDomain().getResourceSet()
                            .getEObject(participantURI, true);
            if (nameDerivedFromParticipant.equals(participant.getName())) {

                return participant;
            }
        }
        return null;
    }

}
