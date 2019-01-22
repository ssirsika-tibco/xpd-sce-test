/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.implementer.script;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.xsd.XSDForm;
import org.eclipse.xsd.XSDSchema;

import com.tibco.xpd.bom.wsdltransform.api.WSDLTransformUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.NamespaceMapEntry;
import com.tibco.xpd.xpdExtension.NamespacePrefixMap;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utilities for handling the various aspects of the xpdExt:NamespacePrefixMap
 * extension element (for web-service activity).
 * 
 * @author aallway
 * @since 29 May 2012
 */
public class NamespacePrefixMapUtil {

    private static Logger logger = Activator.getDefault().getLogger();

    public enum MappingStatus {
        /**
         * Prefix to namespace mapping is defined for activity and is in-synch
         * with the prefixes/namespaces in the actual WSDL (and xsds)
         */
        OK,
        /**
         * The prefix to namespace map is not set in the extension model for
         * this activity.
         */
        NONE_EXISTS,
        /**
         * The prefix to namespace map is set on the activity but differs from
         * that in the WSDL (and XSDs)
         */
        INCONSISTENT,
        /**
         * When calculating the prefix to namespace map for the activity's
         * referenced WSDL it was discovered that the same prefix is used for
         * multiple different namespaces.
         */
        SAME_PREFIX_FOR_DIFFERENT_NAMESPACES,
        /**
         * When calculating the prefix to namespace map for the activity's
         * referenced WSDL it was discovered that the different prefixes are
         * used for the same namespace
         */
        DIFFERENT_PREFIXES_FOR_SAME_NAMESPACE,
        /**
         * The WSDL for the activity is not accessible
         */
        WSDL_INACCESSIBLE,
        /**
         * The User has previously elected to disable prefix mapping validation
         * problems (like same prefix different namspaces etc.)
         */
        DISABLED,
        /**
         * The User has previously elected to disable prefix mapping validation
         * problems BUT due to constraints from referenced schemas disabling
         * should not be allowed
         */
        NS_MAPPING_DISBALED_BUT_REQUIRED,
        /**
         * Due to constraints from referenced schemas additional namespace
         * prefixes need to be set
         */
        ADDITIONAL_XSD_NS_PREFIXES_REQUIRED;

        private String msg = null;

        public void setMessage(String msg) {
            this.msg = msg;
        }

        public String getMessage() {
            return msg;
        }
    };

    /**
     * @param activity
     * @return <code>true</code> if the given activity is web-service related
     *         with input or output mappings using XPath grammar.
     */
    public static boolean isXPathWebServiceActivity(Activity activity) {

        String inputGrammar =
                ScriptGrammarFactory.getScriptGrammar(activity,
                        DirectionType.IN_LITERAL);

        if (inputGrammar == null) {
            inputGrammar =
                    ScriptGrammarFactory.getDefaultScriptGrammar(activity);
        }

        if (ScriptGrammarFactory.XPATH.equals(inputGrammar)) {
            return true;
        }

        String outputGrammar =
                ScriptGrammarFactory.getScriptGrammar(activity,
                        DirectionType.OUT_LITERAL);

        if (outputGrammar == null) {
            outputGrammar =
                    ScriptGrammarFactory.getDefaultScriptGrammar(activity);
        }

        if (ScriptGrammarFactory.XPATH.equals(outputGrammar)) {
            return true;
        }

        /*
         * Sid: For Reply Immediate (which is forced to be XPath) with mappings
         * created yet, the grammar will be returned as JavaScript (as it's only
         * the section that forces mapping Xpath) so we need to default
         * similarly.
         */
        if (ReplyActivityUtil.isStartMessageWithReplyImmediate(activity)) {
            return true;
        }

        return false;
    }

    /**
     * @param activity
     * @return <code>true</code> if this is a web-service related activity.
     */
    public static boolean isWebServiceActivity(Activity activity) {
        /*
         * Using getMessageProvider() should guarantee that this is a
         * web-service activity of some kind (even those partially related such
         * as catch / throw error etc
         */

        ActivityMessageProvider messageProvider =
                ActivityMessageProviderFactory.INSTANCE
                        .getMessageProvider(activity);
        if (messageProvider != null) {
            return true;
        }
        return false;
    }

    /**
     * Get the xpdExt:NamespacePrefixMap extension element for the given
     * activity.
     * 
     * @param activity
     * @return he xpdExt:NamespacePrefixMap extension element or null if not
     *         defined.
     */
    public static NamespacePrefixMap getNamespacePrefixMap(Activity activity) {
        if (activity != null) {
            NamespacePrefixMap prefixMap =
                    (NamespacePrefixMap) Xpdl2ModelUtil
                            .getOtherElement(activity,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_NamespacePrefixMap());

            /* <tracing against plug-in's DEBUG category> */
            if (Platform.inDebugMode()) {

                String newLn = System.getProperty("line.separator"); //$NON-NLS-1$

                StringBuffer sb = new StringBuffer();
                sb.append("Model PrefixMap For Activity: ") //$NON-NLS-1$
                        .append(activity.getName())
                        .append(newLn)
                        .append("======================================================") //$NON-NLS-1$
                        .append(newLn);

                if (prefixMap != null) {
                    for (NamespaceMapEntry entry : prefixMap
                            .getNamespaceEntries()) {
                        String nsEntry =
                                String.format("  %s = %s", entry.getPrefix(), entry.getNamespace()); //$NON-NLS-1$
                        sb.append(nsEntry).append(newLn);
                    }
                } else {
                    sb.append("   ** NOT DEFINED **") //$NON-NLS-1$
                            .append(newLn);
                }
                sb.append("===============================================") //$NON-NLS-1$
                        .append(newLn).append(newLn);

                logger.trace(Activator.DEBUG, sb.toString());
            }
            /* </tracing against plug-in's DEBUG category> */

            return prefixMap;

        }
        return null;
    }

    /**
     * @param activity
     * 
     * @return A new namespace prefix map complete with entries appropriate to
     *         the WSDL referenced by the given activity or <code>null</code> if
     *         that cannot be ascertained.
     * 
     * @throws DuplicateNamespacePrefixException
     *             If there are duplicate prefix names referring to different
     *             namespaces.
     * @throws CantAccessWSDLException
     *             If the WSDL cannot be accessed for this activity.
     * @throws DifferentPrefixesForNamespaceException
     *             If there are differnt prefixes for the same namespace.
     */
    public static NamespacePrefixMap createNamespacePrefixMap(Activity activity)
            throws DuplicateNamespacePrefixException, CantAccessWSDLException,
            DifferentPrefixesForNamespaceException {

        Map<String, String> wsdlNamespacePrefixMap =
                getWSDLNamespacePrefixMap(activity);

        NamespacePrefixMap namespacePrefixMap =
                XpdExtensionFactory.eINSTANCE.createNamespacePrefixMap();

        for (Entry<String, String> entry : wsdlNamespacePrefixMap.entrySet()) {

            NamespaceMapEntry mapEntry =
                    XpdExtensionFactory.eINSTANCE.createNamespaceMapEntry();
            mapEntry.setPrefix(entry.getKey());
            mapEntry.setNamespace(entry.getValue());

            namespacePrefixMap.getNamespaceEntries().add(mapEntry);
        }

        return namespacePrefixMap;
    }

    /**
     * @param editingDomain
     * @param activity
     * @param namespacePrefixMap
     * 
     * @return The command to set (or remove if <code>null</code>) the given
     *         namespacePrefixMap on the given activity.
     */
    public static Command getSetNamespacePrefixMapCommand(
            EditingDomain editingDomain, Activity activity,
            NamespacePrefixMap namespacePrefixMap) {
        return Xpdl2ModelUtil.getSetOtherElementCommand(editingDomain,
                activity,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_NamespacePrefixMap(),
                namespacePrefixMap);
    }

    /**
     * @param editingDomain
     * @param activity
     * @param namespacePrefixMap
     * 
     * @return The command to set namespace prefix mapping as disabled for the
     *         given activity
     */
    public static Command getSetNamespacePrefixMapStatusCommand(
            EditingDomain editingDomain, Activity activity, boolean disabled) {

        NamespacePrefixMap namespacePrefixMap =
                XpdExtensionFactory.eINSTANCE.createNamespacePrefixMap();

        namespacePrefixMap.setPrefixMappingDisabled(disabled);

        return Xpdl2ModelUtil.getSetOtherElementCommand(editingDomain,
                activity,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_NamespacePrefixMap(),
                namespacePrefixMap);
    }

    /**
     * For the WSDL referenced from the given activity get a map of prefixes to
     * the corresponding namespaces in the WSDL and all in-lined/underlying
     * schemas.
     * 
     * @param definition
     * 
     * @return A map for prefix->namespace for the web-service activity's
     *         referenced WSDL and it's underlying schemas.
     * 
     * @throws DuplicateNamespacePrefixException
     *             If there are duplicate prefix names referring to different
     *             namespaces.
     * @throws CantAccessWSDLException
     *             If the WSDL cannot be accessed for this activity.
     * @throws DifferentPrefixesForNamespaceException
     *             If there are differnt prefixes for the same namespace.
     */
    public static Map<String, String> getWSDLNamespacePrefixMap(
            Activity activity) throws DuplicateNamespacePrefixException,
            CantAccessWSDLException, DifferentPrefixesForNamespaceException {

        Definition wsdlDefinition = getWsdlDefinitionforActivity(activity);

        if (wsdlDefinition != null) {

            Map<String, String> prefixMap =
                    getNamespacePrefixMap(wsdlDefinition);

            /* <tracing against plug-in's DEBUG category> */
            if (Platform.inDebugMode()) {

                String newLn = System.getProperty("line.separator"); //$NON-NLS-1$

                StringBuffer sb = new StringBuffer();
                sb.append("WSDL PrefixMap For Activity: ") //$NON-NLS-1$
                        .append(activity.getName())
                        .append(newLn)
                        .append("======================================================") //$NON-NLS-1$
                        .append(newLn);

                Set<Entry<String, String>> entrySet = prefixMap.entrySet();
                for (Entry<String, String> entry : entrySet) {
                    String nsEntry =
                            String.format("  %s = %s", entry.getKey(), entry.getValue()); //$NON-NLS-1$
                    sb.append(nsEntry).append(newLn);
                }

                sb.append("===============================================") //$NON-NLS-1$
                        .append(newLn).append(newLn);

                logger.trace(Activator.DEBUG, sb.toString());
            }
            /* </tracing against plug-in's DEBUG category> */

            return prefixMap;
        } else {
            throw new CantAccessWSDLException(activity);
        }

    }

    /**
     * Get the WSDL {@link Definition} associated with a given {@link Activity}.
     * If the Activity is not associated with a WSDL file then <code>null</code>
     * will be returned.
     * 
     * @param activity
     * @return WSDL Definition for the specified Activity. <code>null</code> if
     *         Definition cannot be obtained.
     */
    private static Definition getWsdlDefinitionforActivity(Activity activity) {

        Definition wsdlDefinition = null;

        IFile wsdlFile = Xpdl2WsdlUtil.getWsdlFile(activity);
        if (wsdlFile != null && wsdlFile.exists()) {
            WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(wsdlFile);

            if (workingCopy != null) {
                if (workingCopy.getRootElement() instanceof Definition) {
                    wsdlDefinition = (Definition) workingCopy.getRootElement();
                }
            }
        }

        return wsdlDefinition;
    }

    /**
     * For the given WSDL definition get a map of prefixes to the corresponding
     * namespaces in the WSLD and all in-lined/underlying schemas.
     * 
     * @param wsdlDefinition
     * 
     * @return A map for prefix->namespace for the given WSDL and it's
     *         underlying schemas.
     * 
     * @throws DuplicateNamespacePrefixException
     *             If there are duplicate prefix names referring to different
     *             namespaces.
     * @throws DifferentPrefixesForNamespaceException
     *             If there are differnt prefixes for the same namespace.
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> getNamespacePrefixMap(
            Definition wsdlDefinition)
            throws DuplicateNamespacePrefixException,
            DifferentPrefixesForNamespaceException {

        Map<String, String> prefixMap = new HashMap<String, String>();

        /* Get all the namespaces defined in WSDL. */
        Map wsdlNamespaces = wsdlDefinition.getNamespaces();
        if (wsdlNamespaces != null) {
            for (Iterator iterator = wsdlNamespaces.entrySet().iterator(); iterator
                    .hasNext();) {
                Entry<String, String> entry =
                        (Entry<String, String>) iterator.next();

                addPrefixNamespace(prefixMap, entry.getKey(), entry.getValue());
            }
        }

        /* Get any namespaces defined in referenced schemas. */
        Set<XSDSchema> xsdSchemasForWSDL = getXSDSchemasForWSDL(wsdlDefinition);

        for (XSDSchema xsdSchema : xsdSchemasForWSDL) {
            Map<String, String> schemaNamespaces =
                    xsdSchema.getQNamePrefixToNamespaceMap();

            for (Entry<String, String> entry : schemaNamespaces.entrySet()) {
                addPrefixNamespace(prefixMap, entry.getKey(), entry.getValue());
            }
        }

        return prefixMap;
    }

    /**
     * Adds the given prefix / namespace pair to the given map if not already
     * there.
     * 
     * @param prefixMap
     * @param prefix
     * @param namespace
     * @throws DuplicateNamespacePrefixException
     *             If there are duplicate prefix names referring to different
     *             namespaces.
     * @throws DifferentPrefixesForNamespaceException
     *             If there are differnt prefixes for the same namespace.
     */
    private static void addPrefixNamespace(Map<String, String> prefixMap,
            String prefix, String namespace)
            throws DuplicateNamespacePrefixException,
            DifferentPrefixesForNamespaceException {

        if (prefix != null && namespace != null) {

            for (Entry<String, String> entry : prefixMap.entrySet()) {
                String existingPrefix = entry.getKey();
                String existingNamespace = entry.getValue();

                if (prefix.equals(entry.getKey())) {
                    /*
                     * If we find the same prefix it must reference the same
                     * namespace, if not throw an exception.
                     */

                    if (!namespace.equals(existingNamespace)) {
                        throw new DuplicateNamespacePrefixException(prefix,
                                existingNamespace, namespace);
                    }

                    /*
                     * Otherwise we can just return as we have an identical
                     * entry for prefix & namespace already.
                     */
                    return;
                }

                if (namespace.equals(existingNamespace)) {
                    /*
                     * If we find the same namespace then it must have the same
                     * prefix.
                     */
                    if (!prefix.equals(existingPrefix)) {
                        throw new DifferentPrefixesForNamespaceException(
                                existingNamespace, existingPrefix, prefix);
                    }

                    /*
                     * Otherwise we can just return as we have an identical
                     * entry for prefix & namespace already.
                     */
                    return;
                }

            }

            if (prefixMap.get(prefix) == null) {
                prefixMap.put(prefix, namespace);
            }
        }
    }

    /**
     * Get all Schema's in and imported/included by the given WSDL (including
     * nested schemas).
     * 
     * @definition
     * @return The set of schemas in or referenced by wsdl
     */
    public static Set<XSDSchema> getXSDSchemasForWSDL(Definition definition) {

        Set<XSDSchema> xsdSchemas =
                WSDLTransformUtil.getReferencedSchemas(definition);

        return xsdSchemas;
    }

    /**
     * When building prefix->namespace map for a WSDL and it's referenced
     * schemas, this exception is thrown if the same prefix is used for
     * different namespaces.
     * 
     * @author aallway
     * @since 31 May 2012
     */
    public static class DuplicateNamespacePrefixException extends Exception {
        private static final long serialVersionUID = -7388046313872252505L;

        public DuplicateNamespacePrefixException(String prefix,
                String firstNamespace, String secondNamespace) {
            super(
                    String.format(Messages.NamespacePrefixMapUtil_DuplicatenamespacesForPrefix_message,
                            prefix,
                            firstNamespace,
                            secondNamespace));
        }
    }

    /**
     * When building prefix->namespace map for a WSDL and it's referenced
     * schemas, this exception is thrown if different prefixes are used for the
     * same namespace.
     * 
     * @author aallway
     * @since 01 Jun 2012
     */
    public static class DifferentPrefixesForNamespaceException extends
            Exception {

        private static final long serialVersionUID = 5577834356735408009L;

        public DifferentPrefixesForNamespaceException(String namespace,
                String firstPrefix, String secondPrefix) {
            super(
                    String.format(Messages.NamespacePrefixMapUtil_DifferentPrefixesForNamespace_message,
                            namespace,
                            firstPrefix,
                            secondPrefix));
        }
    }

    /**
     * Exception thrown when the WSDL definition element cannot be access for a
     * given activity (for whatever reason).
     * 
     * @author aallway
     * @since 31 May 2012
     */
    public static class CantAccessWSDLException extends Exception {

        public CantAccessWSDLException(Activity activity) {
            super(
                    String.format(Messages.NamespacePrefixMapUtil_CantAccessActivityWSDL_message,
                            Xpdl2ModelUtil.getDisplayNameOrName(activity)));
        }
    }

    /**
     * @param activity
     * @return null if no xpdl map exists
     */
    public static Map<String, String> getNamespacesFromXpdl(Activity activity) {

        Map<String, String> ret = null;

        NamespacePrefixMap nsMap = getNamespacePrefixMap(activity);
        if (nsMap != null) {
            ret = new TreeMap<String, String>();
            for (NamespaceMapEntry entry : nsMap.getNamespaceEntries()) {
                ret.put(entry.getPrefix(), entry.getNamespace());
            }
        }

        return ret;
    }

    /**
     * @param activity
     * @return <code>true</code> If the user has previously set activity
     *         extension model to disable prefix to namespace mapping
     *         validations.
     */
    public static boolean isPrefixNamespaceMappingDisabled(Activity activity) {
        NamespacePrefixMap map = getNamespacePrefixMap(activity);
        if (map != null) {
            return map.isPrefixMappingDisabled();
        }
        return false;
    }

    /**
     * Namespace prefix mapping status for the given activity.
     * 
     * @param activity
     * @return The prefix mapping status
     */
    public static MappingStatus getMappingStatus(Activity activity) {

        MappingStatus ret = null;

        if (activity != null) {

            if (NamespacePrefixMapUtil
                    .isPrefixNamespaceMappingDisabled(activity)) {

                if (isReferencedXSDsRequiringNsPrefixing(activity)) {
                    ret = MappingStatus.NS_MAPPING_DISBALED_BUT_REQUIRED;
                } else {
                    ret = MappingStatus.DISABLED;
                }

            } else {

                try {
                    /*
                     * obtain current, implicit, wsdl mapping. Do this first so
                     * that if there are duplicate prefixes for different
                     * namespaces and so on we complain about that rather than
                     * saying "there's no prefixmap in activity model".
                     * Otherwise the quick fix for the latter to add prefix map
                     * will just fail.
                     */
                    Map<String, String> wsdlMap =
                            getWSDLNamespacePrefixMap(activity);

                    String lstOfXSDsRequiringNsPrefixing =
                            getLstOfXSDsRequiringNsPrefixing(activity);
                    if (lstOfXSDsRequiringNsPrefixing != null
                            && !lstOfXSDsRequiringNsPrefixing.isEmpty()) {
                        ret = MappingStatus.ADDITIONAL_XSD_NS_PREFIXES_REQUIRED;
                        ret.setMessage(lstOfXSDsRequiringNsPrefixing);
                        return ret;
                    }

                    // obtain xpdl mapping
                    Map<String, String> xpdlMap =
                            getNamespacesFromXpdl(activity);
                    if (xpdlMap == null) {
                        ret = MappingStatus.NONE_EXISTS;

                    } else {

                        // compare the 2 for discrepancies
                        if (xpdlMap.size() == wsdlMap.size()) {
                            /*
                             * Assume is ok until we find namespace for a prefix
                             * is different in wsdl and activity extension
                             * model.
                             */
                            ret = MappingStatus.OK;

                            for (String key : xpdlMap.keySet()) {
                                if (!xpdlMap.get(key).equals(wsdlMap.get(key))) {
                                    ret = MappingStatus.INCONSISTENT;
                                    break;
                                }
                            }

                        } else {
                            ret = MappingStatus.INCONSISTENT;
                        }
                    }
                } catch (DuplicateNamespacePrefixException e) {
                    ret = MappingStatus.SAME_PREFIX_FOR_DIFFERENT_NAMESPACES;
                    ret.setMessage(e.getMessage());
                } catch (DifferentPrefixesForNamespaceException e) {
                    ret = MappingStatus.DIFFERENT_PREFIXES_FOR_SAME_NAMESPACE;
                    ret.setMessage(e.getMessage());
                } catch (CantAccessWSDLException e) {
                    ret = MappingStatus.WSDL_INACCESSIBLE;
                    ret.setMessage(e.getMessage());
                }
            }

        }

        return ret;
    }

    /**
     * @param activity
     * @return
     */
    private static String getLstOfXSDsRequiringNsPrefixing(Activity activity) {

        Set<String> namespaces = getXsdsRequiringNsPrefixes(activity);

        if (!namespaces.isEmpty()) {
            final String separator = ", "; //$NON-NLS-1$

            StringBuilder sb = new StringBuilder();
            for (String s : namespaces) {
                sb.append(s).append(separator);
            }
            return sb.substring(0, sb.length() - separator.length());
        }

        return null;
    }

    /**
     * @param activity
     * @return
     */
    private static boolean isReferencedXSDsRequiringNsPrefixing(
            Activity activity) {

        Set<String> namespaces = getXsdsRequiringNsPrefixes(activity);
        return !namespaces.isEmpty();
    }

    /**
     * @param activity
     * @return Collection of XSD file names with namespace prefixes missing but
     *         required
     */
    private static Set<String> getXsdsRequiringNsPrefixes(Activity activity) {

        Set<String> ret = new HashSet<String>();

        Definition wsdlDefinition = getWsdlDefinitionforActivity(activity);

        /* Get any namespaces defined in referenced schemas. */
        Set<XSDSchema> xsdSchemasForWSDL = getXSDSchemasForWSDL(wsdlDefinition);

        for (XSDSchema xsdSchema : xsdSchemasForWSDL) {
            Map<String, String> schemaNamespaces =
                    xsdSchema.getQNamePrefixToNamespaceMap();
            if (XSDForm.QUALIFIED_LITERAL.equals(xsdSchema
                    .getElementFormDefault())) {
                for (Entry<String, String> entry : schemaNamespaces.entrySet()) {
                    String nsPrefix = entry.getKey();
                    if (nsPrefix == null) {

                        /*
                         * XPD-5481 - Do not complain about missing prefix for
                         * default namespace.
                         * 
                         * If there is a default namespace used in the schema
                         * then it will appear in map as prefix=null. However,
                         * this is OK provided that there is a separate entry
                         * that _does_ have a prefix.
                         */
                        boolean foundFullPrefixEntry = false;

                        for (Entry<String, String> tempEntry : schemaNamespaces
                                .entrySet()) {
                            if (tempEntry.getValue() != null
                                    && tempEntry.getValue()
                                            .equals(entry.getValue())) {
                                foundFullPrefixEntry = true;
                                break;
                            }
                        }

                        if (!foundFullPrefixEntry) {
                            IPath path =
                                    new Path(xsdSchema.getSchemaLocation());
                            ret.add(path.lastSegment());
                            break;
                        }
                    }
                }
            }
        }

        return ret;
    }

}
