package com.tibco.xpd.validation.bpmn.developer.resolution;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.implementer.script.NamespacePrefixMapUtil;
import com.tibco.xpd.implementer.script.NamespacePrefixMapUtil.CantAccessWSDLException;
import com.tibco.xpd.implementer.script.NamespacePrefixMapUtil.DifferentPrefixesForNamespaceException;
import com.tibco.xpd.implementer.script.NamespacePrefixMapUtil.DuplicateNamespacePrefixException;
import com.tibco.xpd.implementer.script.WsdlPathFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.validation.bpmn.developer.internal.Messages;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.NamespaceMapEntry;
import com.tibco.xpd.xpdExtension.NamespacePrefixMap;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public class RegenerateNamespaceMappingResolution extends
        AbstractWorkingCopyResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     * @param target
     * @param marker
     * @return
     * @throws ResolutionException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        if (!(target instanceof Activity)) {
            return null;
        }

        Activity activity = (Activity) target;

        CompoundCommand compoundCmd =
                new CompoundCommand(
                        Messages.RegenerateNamespaceMappingResolution_Resynch_menu);

        // obtain prefix-ns maps from resources
        Map<String, String> xpdlMap =
                NamespacePrefixMapUtil.getNamespacesFromXpdl(activity);

        Map<String, String> wsdlMap;
        try {
            wsdlMap =
                    NamespacePrefixMapUtil.getWSDLNamespacePrefixMap(activity);
        } catch (DuplicateNamespacePrefixException e) {
            return null;
        } catch (CantAccessWSDLException e) {
            return null;
        } catch (DifferentPrefixesForNamespaceException e) {
            return null;
        }

        /*
         * Create a map of preix-in-activity-prefix-map to prefix-in-wsdl for
         * each namespace.
         * 
         * The prefix in the activity prefix map will be the one used in its
         * mapping paths, so then we can swap that for the new equivalent in
         * prefix wsd to fix the mappings.
         */
        Map<String, String> nsPrefixMap =
                createNsPrefixMapping(getOrderedSwitchedKeyValueMap(xpdlMap),
                        getOrderedSwitchedKeyValueMap(wsdlMap));

        // update the xpdl mapping
        NamespacePrefixMap namespacePrefixMap =
                XpdExtensionFactory.eINSTANCE.createNamespacePrefixMap();

        for (String entry : wsdlMap.keySet()) {
            NamespaceMapEntry mapEntry =
                    XpdExtensionFactory.eINSTANCE.createNamespaceMapEntry();
            mapEntry.setPrefix(entry);
            mapEntry.setNamespace(wsdlMap.get(entry));

            namespacePrefixMap.getNamespaceEntries().add(mapEntry);
        }

        Command cmd =
                NamespacePrefixMapUtil
                        .getSetNamespacePrefixMapCommand(editingDomain,
                                activity,
                                namespacePrefixMap);
        compoundCmd.append(cmd);

        // apply to all XPath mappings on activity
        if (ScriptGrammarFactory.XPATH.equals(ScriptGrammarFactory
                .getScriptGrammar(activity, DirectionType.IN_LITERAL))) {
            List<DataMapping> inMapping =
                    Xpdl2ModelUtil.getDataMappings(activity,
                            DirectionType.IN_LITERAL);
            for (DataMapping mapping : inMapping) {
                String formal = mapping.getFormal();
                if (formal != null) {
                    String xPathesqPath =
                            WsdlPathFactory
                                    .updateNamespacesOnXPathesqPaths(formal,
                                            nsPrefixMap);
                    if (!formal.equals(xPathesqPath)) {
                        compoundCmd.append(SetCommand.create(editingDomain,
                                mapping,
                                Xpdl2Package.eINSTANCE.getDataMapping_Formal(),
                                xPathesqPath));
                    }
                }
            }
        }

        if (ScriptGrammarFactory.XPATH.equals(ScriptGrammarFactory
                .getScriptGrammar(activity, DirectionType.OUT_LITERAL))) {
            List<DataMapping> outMapping =
                    Xpdl2ModelUtil.getDataMappings(activity,
                            DirectionType.OUT_LITERAL);
            for (DataMapping mapping : outMapping) {
                String formal = mapping.getFormal();
                if (formal != null) {
                    String xPathesqPath =
                            WsdlPathFactory
                                    .updateNamespacesOnXPathesqPaths(formal,
                                            nsPrefixMap);
                    if (!formal.equals(xPathesqPath)) {
                        compoundCmd.append(SetCommand.create(editingDomain,
                                mapping,
                                Xpdl2Package.eINSTANCE.getDataMapping_Formal(),
                                xPathesqPath));
                    }
                }
            }
        }

        return compoundCmd;
    }

    /**
     * @param xpdlMap
     * @param wsdlMap
     * @return A map of Activity prefix map extension model's prefix to the
     *         CURRENT WSDL prefix for each given namespace.
     */
    private Map<String, String> createNsPrefixMapping(
            Map<String, String> xpdlMap, Map<String, String> wsdlMap) {

        Map<String, String> diffMap = new HashMap<String, String>();

        // consider all namespaces mentioned in the xpdl mapping first
        for (String ns : xpdlMap.keySet()) {
            String xpdlPrefix = xpdlMap.get(ns);
            String wsdlPrefix = wsdlMap.get(ns);
            if (wsdlPrefix != null) {
                /*
                 * Map the activity's prefix map prefix for the namespace to the
                 * Wsdl's version.
                 */
                diffMap.put(xpdlPrefix, wsdlPrefix);
            } else {
                /*
                 * if the namespace in activity prefix map no longer exists in
                 * wsdl then need to 'spoil' the prefixes in the mapping (in
                 * case the old prefix clashes with a prefix for a different
                 * namespce
                 */
                diffMap.put(xpdlPrefix, xpdlPrefix + "_unknown"); // add a spoiled namespace //$NON-NLS-1$
            }
        }

        return diffMap;
    }

    /**
     * @param xpdlMap
     * @return
     */
    private Map<String, String> getOrderedSwitchedKeyValueMap(
            Map<String, String> map) {
        /*
         * SortedSet<Map.Entry<String, String>> sortedEntries = new
         * TreeSet<Map.Entry<String, String>>( new Comparator<Map.Entry<String,
         * String>>() {
         * 
         * @Override public int compare(Map.Entry<String, String> e1,
         * Map.Entry<String, String> e2) { return
         * e1.getValue().compareTo(e2.getValue()); } });
         * sortedEntries.addAll(map.entrySet());
         */
        Map<String, String> ret = new TreeMap<String, String>();

        try {
            for (Map.Entry<String, String> mapEntry : map.entrySet()) {
                ret.put(mapEntry.getValue(), mapEntry.getKey());
            }
        } catch (NullPointerException e) {
            // log error
            return Collections.<String, String> emptyMap();
        }

        return ret;
    }

}
