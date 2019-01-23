/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.xsdtransform.exports.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.ocl.util.CollectionUtil;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;

import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.bom.xsdtransform.Activator;
import com.tibco.xpd.bom.xsdtransform.export.progress.Bom2XsdMonitorMessages;
import com.tibco.xpd.bom.xsdtransform.internal.resources.Bom2XsdTransformContext;
import com.tibco.xpd.bom.xsdtransform.utils.NamespaceURIToJavaPackageMapper;

/**
 * Data class used in the export transformation of BOM to WSDL/XSD. This will be
 * passed to the OAW transformation.
 * 
 * @author njpatel
 * 
 */
public class ExportTransformationData {

    Bom2XsdTransformContext ctx;

    /**
     * @return the ctx
     */
    public Bom2XsdTransformContext getCtx() {
        return ctx;
    }

    private final Map<String, String> namespaceHashMap;

    private final Map<String, String> fileNameHashMap;

    private final Map<String, Object> explicitGroupValues;

    private final Map<Object, String> originalNames;

    private final Map<String, Object> topLevelComplexTypes;

    private final ArrayList<Model> modelsToParse;

    private final ArrayList<Object> parsedOAWSchemas;

    private final ArrayList<Package> ignoredPackages;

    private final Map<Object, Object> xsdSequenceExplicitGroups;

    private Set<String> allTopLevelElementsReferencedBySubstitutionGroups =
            null;

    private Set<Classifier> baseTypeTopLevelAttributes = null;

    private Set<Classifier> anonymousTopLevelAttributes = null;

    private Set<Classifier> baseTypeTopLevelElements = null;

    private Set<Classifier> anonymousTopLevelElements = null;

    private HashMap<Object, Object> packageDocRootHashMap = null;

    private IProject project = null;

    private Bom2XsdMonitorMessages monitorMessages =
            new Bom2XsdMonitorMessages();

    private IFile topLevelSourceFile = null;

    /**
     * <code>true</code> If want all the schemas returned by the transform
     * regardless of whether they were returned by a previous transform
     * performed using this Export data.
     * 
     * This is useful if you are performing multiple transforms with this data
     * BUT poutputting to separate folders (and hence need all referenced
     * schemas output also).
     */
    private boolean wantAllReferencedSchemas = false;

    /**
     * Creates a Bom2Xsd transform export data that can show progress monitor
     * messages from the transform itself on the given monitor.
     * 
     */
    public ExportTransformationData() {
        namespaceHashMap = new HashMap<String, String>();
        namespaceHashMap.put("xsd", "http://www.w3.org/2001/XMLSchema"); //$NON-NLS-1$ //$NON-NLS-2$
        fileNameHashMap = new HashMap<String, String>();
        explicitGroupValues = new HashMap<String, Object>();
        originalNames = new HashMap<Object, String>();
        modelsToParse = new ArrayList<Model>();
        parsedOAWSchemas = new ArrayList<Object>();
        ignoredPackages = new ArrayList<Package>();
        topLevelComplexTypes = new HashMap<String, Object>();
        xsdSequenceExplicitGroups = new HashMap<Object, Object>();
        packageDocRootHashMap = new HashMap<Object, Object>();

        ctx = new Bom2XsdTransformContext(this);
    }

    /**
     * @return the wantAllReferencedSchemas
     */
    public boolean isWantAllReferencedSchemas() {
        return wantAllReferencedSchemas;
    }

    /**
     * <code>true</code> If want all the schemas returned by the transform
     * regardless of whether they were returned by a previous transform
     * performed using this Export data.
     * 
     * This is useful if you are performing multiple transforms with this data
     * BUT poutputting to separate folders (and hence need all referenced
     * schemas output also).
     * 
     * @param wantAllReferencedSchemas
     *            the wantAllReferencedSchemas to set
     */
    public void setWantAllReferencedSchemasEveryTime(
            boolean wantAllReferencedSchemas) {
        this.wantAllReferencedSchemas = wantAllReferencedSchemas;
    }

    /**
     * CLear the data that must be cleared between transforms of different files
     * using the same ExportInformationData (does not throw away already
     * transformed schemas.
     */
    public void clearBetweenTransforms() {
        /*
         * SID XPD-1605 - Do not clear the namespace to file location map for
         * each resource (the filename is only added when target schema is
         * created and that is now only done once per build - BUT clear is
         * called once per source!
         * 
         * // fileNameHashMap.clear();
         */

        explicitGroupValues.clear();
        originalNames.clear();
        modelsToParse.clear();
        project = null;
    }

    public void clearIgnoredPackages() {
        ignoredPackages.clear();
    }

    public void addIgnoredPackages(Package pkg) {
        if (pkg != null) {
            ignoredPackages.add(pkg);
        }
    }

    public void addParsedOAWSchema(Object schema) {
        parsedOAWSchemas.add(schema);
    }

    public void addOriginalName(Object key, String name) {
        originalNames.put(key, name);
    }

    public void addTopLevelComplexType(String key, Object compexType) {
        topLevelComplexTypes.put(key, compexType);
    }

    public String getOriginalName(Object key) {
        return originalNames.get(key);
    }

    public Object getComplexForName(String key) {
        return topLevelComplexTypes.get(key);
    }

    public ArrayList<Model> getModelsToParse() {
        return modelsToParse;
    }

    public ArrayList<Package> getIgnoredPackages() {
        return ignoredPackages;
    }

    public void addSeqExplicitGroup(Object seq, Object explicitGroup) {
        xsdSequenceExplicitGroups.put(seq, explicitGroup);
    }

    public Object getExplicitGroupForSeq(Object seq) {
        return xsdSequenceExplicitGroups.get(seq);
    }

    public List<Package> stripDuplicatePackages(List<Package> packages) {
        LinkedHashSet<Package> asOrderedSet =
                CollectionUtil.asOrderedSet(packages);

        packages.clear();
        packages.addAll(asOrderedSet);

        return packages;
    }

    public ArrayList<Object> getParsedOAWSchemas() {
        return parsedOAWSchemas;
    }

    /**
     * Add the filename (based on the package qualified name) for the given
     * schema namespace.
     * 
     * @param namespaceKey
     * @param packageQName
     */
    public void addFileName(String namespaceKey, String packageQName) {
        if (namespaceKey != null && packageQName != null) {
            fileNameHashMap.put(namespaceKey,
                    BOMWorkingCopy.getQualifiedName(packageQName) + ".xsd"); //$NON-NLS-1$
        }
    }

    public String getFileLocation(String namespaceURI) {
        String javaPackageNameFromNamespaceURI = null;
        if (namespaceURI != null) {
            javaPackageNameFromNamespaceURI =
                    NamespaceURIToJavaPackageMapper
                            .getJavaPackageNameFromNamespaceURI(getProject(),
                                    namespaceURI);
        }
        return BOMWorkingCopy.getQualifiedName(javaPackageNameFromNamespaceURI)
                + ".xsd";
    }

    public void debugPoint(String point) {
        // System.out.println(this.getClass().getSimpleName() + ": " + point);
    }

    public void debugAddImport(String prefix, String namespace) {
        debugPoint(this.getClass().getSimpleName()
                + String.format(".addImport(sourcFile=%s): '%s:%s'",
                        topLevelSourceFile != null ? topLevelSourceFile
                                .getName() : "??",
                        prefix,
                        namespace));

        // Just debug the unexpected stuff.
        if (prefix != null) {
            String bomNamespace = null;
            if (namespace.startsWith("http")) {
                bomNamespace =
                        NamespaceURIToJavaPackageMapper
                                .getJavaPackageNameFromNamespaceURI(project,
                                        namespace);
            } else {
                bomNamespace = namespace;
            }

            String currentNamespaceForPrefix = namespaceHashMap.get(prefix);
            if (currentNamespaceForPrefix != null) {
                if (!currentNamespaceForPrefix.equals(bomNamespace)) {
                    String warnStr =
                            String.format("Adding a different namespace for prefix: '%s' \n    Old='%s'\n    New='%s'",
                                    prefix,
                                    currentNamespaceForPrefix,
                                    bomNamespace);
                    debugPoint(warnStr);
                    Activator.getDefault().getLogger().warn(warnStr);
                }
            }
        }

    }

    /**
     * @param namespaceKey
     * @return
     */
    public String getFileName(String namespaceKey) {
        String fileName = null;
        if (namespaceKey != null) {
            fileName = fileNameHashMap.get(namespaceKey);

            if (fileName == null || fileName.length() == 0) {
                /* This is tpo check for regressions */
                Activator
                        .getDefault()
                        .getLogger()
                        .error(this.getClass().getName()
                                + ".getFileName("
                                + namespaceKey
                                + "): No file location cached for namespace key.");

                /*
                 * Having no location at all slows everything right down because
                 * oaw/wst run off and try to locate schema from the namespaces
                 * available.
                 */
                fileName = "\\unknown\\"; //$NON-NLS-1$
            }
        }

        return fileName;
    }

    /**
     * @param key
     * @param value
     */
    public void addToExplicitGroupValues(String key, Object value) {
        if (key != null) {
            explicitGroupValues.put(key, value);
        }
    }

    /**
     * @param key
     * @return
     */
    public Object getExplicitGroupDetail(String key) {
        return explicitGroupValues.get(key);
    }

    /**
     * This will generate and return the prefix for the given namespace.
     * 
     * @param bomNamespace
     * @return
     */
    public String generatePrefix(String bomNamespace) {
        if (bomNamespace != null && !bomNamespace.equals("BomPrimitiveTypes")) { //$NON-NLS-1$

            /*
             * DO NOT use getPrefixForNameSpace() to check for existing
             * namespace's prefix. That function expects an XSD URL __NOT__ the
             * bom namespace we've been passed
             * 
             * We always store the BOM namespace passed in the hashmap AS IS so
             * we should look for existing prefix on that basis
             */
            String prefix = null;
            for (Entry<String, String> entry : namespaceHashMap.entrySet()) {
                if (bomNamespace.equals(entry.getValue())) {
                    prefix = entry.getKey();
                    break;
                }
            }

            if (prefix == null || prefix.length() == 0) {
                prefix = "tns" + namespaceHashMap.size(); //$NON-NLS-1$        
                namespaceHashMap.put(prefix, bomNamespace);
            }

            String curnamespace = namespaceHashMap.get(prefix);
            if (!bomNamespace.equals(curnamespace)) {
                Activator
                        .getDefault()
                        .getLogger()
                        .warn(String.format("Found prefix for namespace BUIT namespace doesn't match: '%s' \n    Old='%s'\n    New='%s'",
                                prefix,
                                curnamespace,
                                bomNamespace));
            }

            return prefix;
        } else {
            return ""; //$NON-NLS-1$
        }
    }

    /**
     * @param prefix
     * @return
     */
    public String getNamespaceForPrefix(String prefix) {
        if (prefix.indexOf(":") >= 0) {
            prefix = prefix.substring(0, prefix.indexOf(":"));
        }
        String ns = namespaceHashMap.get(prefix);

        if (ns == null || ns.length() == 0) {
            return ""; //$NON-NLS-1$
        }
        return ns;
    }

    public String getJavaPackageNameFromURI(String uri) {
        return NamespaceURIToJavaPackageMapper
                .getJavaPackageNameFromNamespaceURI(project, uri);
    }

    /**
     * @param namespaceURI
     * @return
     */
    public String getPrefixForNamespace(String namespaceURI) {
        /*
         * TODO: This is a bad but necessary KLudge for now - we are sometimes
         * called with a BOM package name NOT a http URI namespace - so before
         * ascertaining the bom namespace from URI check if the actual namespace
         * as given is in the has map - SOME BOm namespaces pass thru
         * getJavaPackagename... method below and some do not - some get the end
         * removed and that can then match another name space!
         */
        if (namespaceURI != null && !namespaceURI.equals("BomPrimitiveTypes")) { //$NON-NLS-1$
            String prefix = null;
            for (Entry<String, String> entry : namespaceHashMap.entrySet()) {
                if (namespaceURI.equals(entry.getValue())) {
                    prefix = entry.getKey();
                    break;
                }
            }

            if (prefix != null) {
                return prefix;
            }
        }

        if (namespaceURI != null && !namespaceURI.equals("BomPrimitiveTypes")) { //$NON-NLS-1$
            String javaPackageNameFromNamespaceURI =
                    NamespaceURIToJavaPackageMapper
                            .getJavaPackageNameFromNamespaceURI(project,
                                    namespaceURI);

            for (String key : namespaceHashMap.keySet()) {
                String tempNamespace = namespaceHashMap.get(key);
                if (javaPackageNameFromNamespaceURI
                        .equalsIgnoreCase(tempNamespace)) {
                    return key;
                }
            }

            //        String[] parts = namespaceURI.split("/"); //$NON-NLS-1$
            // if (parts.length > 0) {
            // for (String key : namespaceHashMap.keySet()) {
            // String tempNamespace = namespaceHashMap.get(key);
            // if (tempNamespace.equalsIgnoreCase(parts[parts.length - 1])) {
            // return key;
            // }
            // }
            // }

            /*
             * for (String key : namespaceHashMap.keySet()) { String
             * tempNamespace = namespaceHashMap.get(key); if
             * (tempNamespace.toLowerCase().indexOf(parts[parts.length - 1]
             * .toLowerCase()) != -1) { return key; } }
             */
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * The top level source file that we are about to transform.
     * 
     * @param topLevelSourceFile
     *            the topLevelSourceName to set
     */
    public void setTopLevelSourceFile(IFile topLevelSourceFile) {
        this.topLevelSourceFile = topLevelSourceFile;
        if (monitorMessages != null) {
            monitorMessages.setTopLevelSourceInTransform(topLevelSourceFile);
        }
    }

    /**
     * @return the topLevelSourceFile
     */
    public IFile getTopLevelSourceFile() {
        return topLevelSourceFile;
    }

    /**
     * @return the monitorMessages
     */
    public Bom2XsdMonitorMessages getMonitor() {
        return monitorMessages;
    }

    /**
     * @param tmpSet
     */
    public void setAllTopLevelElementsReferencedBySubstitutionGroups(
            Set<String> tmpSet) {
        allTopLevelElementsReferencedBySubstitutionGroups = tmpSet;
    }

    /**
     * @return
     */
    public Set<String> getAllTopLevelElementsReferencedBySubstitutionGroups() {
        return allTopLevelElementsReferencedBySubstitutionGroups;
    }

    /**
     * @param tmpMap
     */
    public void setBaseTypeTopLevelAttributes(Set<Classifier> tmpSet) {
        baseTypeTopLevelAttributes = tmpSet;
    }

    /**
     * @return
     */
    public Set<Classifier> getBaseTypeTopLevelAttributes() {
        return baseTypeTopLevelAttributes;
    }

    /**
     * @param tmpMap
     */
    public void setAnonymousTopLevelAttributes(Set<Classifier> tmpSet) {
        anonymousTopLevelAttributes = tmpSet;
    }

    /**
     * @return
     */
    public Set<Classifier> getAnonymousTopLevelAttributes() {
        return anonymousTopLevelAttributes;
    }

    /**
     * @param tmpMap
     */
    public void setBaseTypeTopLevelElements(Set<Classifier> tmpSet) {
        baseTypeTopLevelElements = tmpSet;
    }

    /**
     * @return
     */
    public Set<Classifier> getBaseTypeTopLevelElements() {
        return baseTypeTopLevelElements;
    }

    /**
     * @param tmpMap
     */
    public void setAnonymousTopLevelElements(Set<Classifier> tmpSet) {
        anonymousTopLevelElements = tmpSet;
    }

    /**
     * @return
     */
    public Set<Classifier> getAnonymousTopLevelElements() {
        return anonymousTopLevelElements;
    }

    public IProject getProject() {
        return project;
    }

    public void setProject(IProject project) {
        this.project = project;
    }

    public void addPackageForDocRoot(Object ebj, Object pkg) {
        packageDocRootHashMap.put(ebj, pkg);
    }

    public Package getPackageForDocRoot(Object ebj) {
        Object object = packageDocRootHashMap.get(ebj);
        if (object != null && object instanceof Package) {
            return (Package) object;
        }
        return null;
    }
}
