/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * AbstractActivityMappingProblemMarkerHandlingSection
 * <p>
 * Mapping section with standard provision of data mapping problem marker uri's
 * for most activities.
 * 
 * @author aallway
 * @since 3.3 (15 Dec 2009)
 */
public abstract class AbstractActivityMappingProblemMarkerHandlingSection
        extends AbstractItemProviderMappingSection {

    /**
     * @param direction
     */
    public AbstractActivityMappingProblemMarkerHandlingSection(
            MappingDirection direction) {
        super(direction);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getProblemMarkerDataMappingSourcePath(java.lang.Object)
     * 
     * @param source
     * @return
     */
    @Override
    protected String getProblemMarkerDataMappingSourcePath(Object source) {
        /*
         * Sid: As getProblemMarkerDataMappingURIs() below needs to be able to
         * handle source paths from script mappings then we may as well handle
         * return of paths for script mappings at this level of class hierarchy
         * too.
         */
        if (source instanceof ScriptInformation) {
            return ((ScriptInformation) source).getName();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#
     * getProblemMarkerDataMappingURIs(java.lang.String)
     */
    @Override
    protected Collection<String> getProblemMarkerDataMappingURIs(
            String dataMappingPath, boolean isSourcePath) {
        Collection<String> uris = new ArrayList<String>();

        if (getInput() instanceof Activity) {
            Activity activity = (Activity) getInput();

            if (MappingDirection.OUT.equals(getDirection())) {
                /*
                 * Standard handling for OUT mappings.
                 */

                /*
                 * Not sure why getting in / out mappings uris should be
                 * different but they were in Nick's original multi-million
                 * different sections all doing almost the same thing, so when I
                 * refactored all this stuff I preserved the differentces here.
                 * 
                 * Think it's probably because you can have multi-mappings TO
                 * same target?
                 */
                List<DataMapping> dataMappings =
                        Xpdl2ModelUtil.getDataMappings(activity,
                                DirectionType.OUT_LITERAL);

                for (DataMapping outMapping : dataMappings) {
                    String pathFromMapping = null;

                    if (isSourcePath) {
                        /*
                         * Sid: For script mappings use script name as path
                         * (which is what (most/all) validation rules seem to
                         * do..)
                         */
                        ScriptInformation script =
                                DataMappingUtil.getMappingScript(outMapping);
                        if (script != null) {
                            pathFromMapping = script.getName();
                        } else {
                            pathFromMapping = outMapping.getFormal();
                        }

                    } else {
                        Expression assignmentTarget = outMapping.getActual();
                        if (assignmentTarget != null) {
                            pathFromMapping = assignmentTarget.getText();
                        }
                    }

                    if (dataMappingPath.equals(pathFromMapping)) {
                        addMappingUris(outMapping, uris);
                    }
                }

            } else {
                /*
                 * Standard handling for in mappings.
                 */
                List<DataMapping> dataMappings =
                        Xpdl2ModelUtil.getDataMappings(activity,
                                DirectionType.IN_LITERAL);
                for (DataMapping inMapping : dataMappings) {
                    String pathFromMapping = null;

                    if (isSourcePath) {
                        /*
                         * Sid: For script mappings use script name as path
                         * (which is what (most/all) validaiton rules seem to
                         * do..
                         */
                        ScriptInformation script =
                                DataMappingUtil.getMappingScript(inMapping);
                        if (script != null) {
                            pathFromMapping = script.getName();
                        } else {
                            Expression assignmentTarget = inMapping.getActual();
                            if (assignmentTarget != null) {
                                pathFromMapping = assignmentTarget.getText();
                            }
                        }
                    } else {
                        pathFromMapping = inMapping.getFormal();
                    }

                    if (dataMappingPath.equals(pathFromMapping)) {
                        addMappingUris(inMapping, uris);
                    }
                }
            }
        }

        return uris;
    }

    /**
     * Add the uri for the mapping and the script info (if it's a scrtipt
     * mapping) to the given list.
     * 
     * @param mapping
     * @param uris
     */
    protected void addMappingUris(DataMapping mapping, Collection<String> uris) {
        Resource resource = mapping.eResource();
        if (resource != null) {
            String uri = resource.getURIFragment(mapping);
            uris.add(uri);

            Object other =
                    Xpdl2ModelUtil.getOtherElement(mapping,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Script());
            if (other instanceof ScriptInformation) {
                ScriptInformation information = (ScriptInformation) other;
                uri = resource.getURIFragment(information);
                uris.add(uri);
            }
        }
    }

}
