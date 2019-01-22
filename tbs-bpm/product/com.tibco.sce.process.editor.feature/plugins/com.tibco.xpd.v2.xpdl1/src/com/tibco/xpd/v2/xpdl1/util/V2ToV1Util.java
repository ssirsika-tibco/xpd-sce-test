/**
 * V2ToV1Util.java
 *
 * 
 *
 * @author aallway
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.v2.xpdl1.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * V2ToV1Util
 * 
 */
public class V2ToV1Util {

    /**
     * Map of process package id to package DOM NodeList (load on demand cache)
     */
    private HashMap<String, NodeList> pkgIdToPackageNodeListCache =
            new HashMap<String, NodeList>();

    /**
     * Map of process / process interface id to parent package's DOM NodeList
     * (load on demand cache)
     */
    private HashMap<String, NodeList> procIdToPackageNodeListCache =
            new HashMap<String, NodeList>();

    /**
     * The original v2 package that xslt is transformaing to v1.
     */
    private Map<String, Package> pkgIdToPackageCache =
            new HashMap<String, Package>();

    /**
     * Cached Map of PackageId to <Map of Id to
     * DataField/FormalParameter/TypeDeclaration>
     */
    private Map<String, Map<String, UniqueIdElement>> pkgIdToDataAndTypesCache =
            new HashMap<String, Map<String, UniqueIdElement>>();

    public V2ToV1Util() {
    }

    /**
     * Get the package that contains the process interface that the given
     * 
     * @param interfaceId
     * @return
     */
    public NodeList getInterfacePackage(String interfaceId) {
        NodeList nodes = EMPTY_NODELIST;

        if (interfaceId != null && interfaceId.length() > 0) {
            // First check if we have already cached the package DOM nodelist
            NodeList cache = procIdToPackageNodeListCache.get(interfaceId);
            if (cache != null) {
                // We've already loaded package for this interface.
                nodes = cache;

            } else {
                // Go grab the interface definition from workspace.
                ProcessInterface procIfc =
                        Xpdl2WorkingCopyImpl
                                .locateProcessInterface(interfaceId);
                if (procIfc != null) {
                    // Found it.
                    Package ifcPkg = Xpdl2ModelUtil.getPackage(procIfc);

                    if (ifcPkg != null) {
                        // Check if we have the already have the parent package
                        // cached.
                        cache = pkgIdToPackageNodeListCache.get(ifcPkg.getId());
                        if (cache != null) {
                            // We have the package cached already BUT no entry
                            // in the procId to NodeList cache so add one (which
                            // will save doing a locateProcessInterface next
                            // time).
                            nodes = cache;
                            procIdToPackageNodeListCache
                                    .put(interfaceId, cache);
                        } else {
                            // Don't have pkg nodelist cached yet, load it.
                            Document doc = getDocument(ifcPkg);
                            nodes = doc.getChildNodes();

                            // And then cache the result in our pkgId to
                            // NodeList and our procId to NodeList maps.
                            pkgIdToPackageNodeListCache.put(ifcPkg.getId(),
                                    nodes);
                            procIdToPackageNodeListCache
                                    .put(interfaceId, nodes);
                        }
                    }

                }
            }
        }
        return nodes;
    }

    /**
     * @param subProc
     * @return
     */
    private Document getDocument(EObject subProcOrIfc) {
        Document doc = null;
        IFile xpdlFile = WorkingCopyUtil.getFile(subProcOrIfc);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(xpdlFile.getLocation().toFile());
        } catch (ParserConfigurationException e) {
        } catch (SAXException e) {
        } catch (IOException e) {
        }
        return doc;
    }

    public static String startTime() {
        return Long.toString(System.currentTimeMillis());
    }

    public static String endTime(String startTime, String indentNum,
            String msg, String threshholdStr) {
        long tim = System.currentTimeMillis() - Long.valueOf(startTime);

        String str = ""; //$NON-NLS-1$
        String indentStr = ""; //$NON-NLS-1$
        boolean threshholdOnly = false;

        int indent = Integer.parseInt(indentNum);

        for (int i = 0; i < indent; i++) {
            indentStr += "    "; //$NON-NLS-1$
        }

        long threshhold = Integer.parseInt(threshholdStr);

        if (threshhold > 0 && tim > threshhold) {
            str = "** EXCEEDED THRESHHOLD(" + threshhold + ") ** " + msg //$NON-NLS-1$ //$NON-NLS-2$
                    + "  Took: " + Double.toString((double) tim / 1000); //$NON-NLS-1$
            System.out.println(str);
        } else if (!threshholdOnly) {
            str = msg + "  Took: " + Double.toString((double) tim / 1000); //$NON-NLS-1$
        } else {
            return ""; //$NON-NLS-1$
        }

        return indentStr + str;
    }

    /**
     * Given the id of a data field, formal parameter, or type declaration,
     * return the resolved basic type of the field (even if it is a field that's
     * a declared type thats an external reference to a BOM primitive type.
     * 
     * @param pkgId
     *            Owning package of the given field/param/type
     * @param id
     *            id of field, param or type declaration
     * 
     * @return Xpdl2 Type info in string format "TYPE;length;precision;scale"
     */
    public String resolveBasicTypeInfo(String pkgId, String id) {
        String ret = null;

        Package pkg = getPackage(pkgId);
        if (pkg != null) {
            Map<String, UniqueIdElement> hostPkgDataAndTypes =
                    pkgIdToDataAndTypesCache.get(pkgId);

            if (hostPkgDataAndTypes == null) {
                // Cache the data fields and types for the given package.
                hostPkgDataAndTypes = new HashMap<String, UniqueIdElement>();
                pkgIdToDataAndTypesCache.put(pkgId, hostPkgDataAndTypes);

                addToIdMap(hostPkgDataAndTypes, pkg.getTypeDeclarations());
                addToIdMap(hostPkgDataAndTypes, pkg.getDataFields());

                for (Process process : pkg.getProcesses()) {
                    addToIdMap(hostPkgDataAndTypes, process
                            .getFormalParameters());
                    addToIdMap(hostPkgDataAndTypes, process.getDataFields());
                }

                ProcessInterfaces interfaces =
                        ProcessInterfaceUtil.getProcessInterfaces(pkg);
                if (interfaces != null) {
                    for (ProcessInterface ifc : interfaces
                            .getProcessInterface()) {
                        addToIdMap(hostPkgDataAndTypes, ifc
                                .getFormalParameters());
                    }
                }
            }

            // 
            // Having got the cache of id's to data look it up
            //
            UniqueIdElement data = hostPkgDataAndTypes.get(id);
            BasicType bt = BasicTypeConverterFactory.INSTANCE.getBasicType(data);
         
            // 
            // Convert the basic type to a string "BasicTypeType:len:precision:scale"
            //
            if (bt != null) {
                String length = ""; //$NON-NLS-1$
                if (bt.getLength() != null) {
                    if (bt.getLength().getValue() != null) {
                        length = bt.getLength().getValue();
                    }
                }

                String precision = ""; //$NON-NLS-1$
                if (bt.getPrecision() != null) {
                    precision = "" + bt.getPrecision().getValue(); //$NON-NLS-1$
                }

                String scale = ""; //$NON-NLS-1$
                if (bt.getScale() != null) {
                    scale = "" + bt.getScale().getValue(); //$NON-NLS-1$
                }

                ret =
                    bt.getType().getLiteral()
                            + ";" + length + ";" + precision + ";" + scale; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
            } else if (data instanceof ProcessRelevantData
                    && isExternalReference((ProcessRelevantData) data)) {
                ret = "STRING;0;0;0"; //$NON-NLS-1$
            }
        } 

        if (ret == null || ret.length() == 0) {
            System.err
                    .println(this.getClass().getSimpleName()
                            + ".resolveBasicTypeInfo(" //$NON-NLS-1$
                            + pkgId
                            + ", " //$NON-NLS-1$
                            + id
                            + "): Cannot locate field/param/type declaration with this id."); //$NON-NLS-1$
            ret = "STRING;1;0;0"; //$NON-NLS-1$
        }
        return ret;
    }


    private void addToIdMap(Map<String, UniqueIdElement> map,
            EList<? extends UniqueIdElement> elementList) {
        for (UniqueIdElement el : elementList) {
            map.put(el.getId(), el);
        }
        return;
    }
    
    private boolean isExternalReference(ProcessRelevantData prd) {
        if (prd != null && prd.getDataType() instanceof ExternalReference) {
            return true;
        }
        return false;
    }

    /**
     * Get package from id.
     * 
     * @param pkgId
     * @return
     */
    private Package getPackage(String pkgId) {
        Package pkg = pkgIdToPackageCache.get(pkgId);
        if (pkg == null) {
            pkg = Xpdl2WorkingCopyImpl.locatePackage(pkgId);
            if (pkg != null) {
                pkgIdToPackageCache.put(pkgId, pkg);
            }
        }

        return pkg;
    }

    private static NodeList EMPTY_NODELIST = new NodeList() {
        public int getLength() {
            return 0;
        }

        public Node item(int index) {
            return null;
        }
    };

}
