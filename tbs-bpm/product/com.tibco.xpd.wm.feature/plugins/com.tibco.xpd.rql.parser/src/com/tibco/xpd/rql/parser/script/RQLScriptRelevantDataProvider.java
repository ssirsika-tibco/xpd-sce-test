/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.rql.parser.script;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.om.core.om.Capability;
import com.tibco.xpd.om.core.om.Group;
import com.tibco.xpd.om.core.om.Location;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.Privilege;
import com.tibco.xpd.om.core.om.Resource;
import com.tibco.xpd.om.resources.wc.OMWorkingCopy;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rql.parser.util.RQLParserUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.internal.client.IModelScriptRelevantData;
import com.tibco.xpd.script.ui.internal.AbstractScriptRelevantDataProvider;

/**
 * 
 * @author mtorres
 * 
 */
public class RQLScriptRelevantDataProvider extends
        AbstractScriptRelevantDataProvider {

    @Override
    public List<IScriptRelevantData> getScriptRelevantDataList() {
        if (getProject() != null) {
            List<IResource> referencedOMFiles =
                    RQLParserUtil.getReferencedOMFiles(getProject());
            if (referencedOMFiles != null && !referencedOMFiles.isEmpty()) {
                List<IScriptRelevantData> scriptRelevantDataList =
                        new ArrayList<IScriptRelevantData>();
                for (IResource omFile : referencedOMFiles) {
                    if (omFile != null) {
                        WorkingCopy workingCopy =
                                XpdResourcesPlugin.getDefault()
                                        .getWorkingCopy(omFile);
                        if (workingCopy instanceof OMWorkingCopy) {
                            OMWorkingCopy omWc = (OMWorkingCopy) workingCopy;
                            EObject rootElement = omWc.getRootElement();
                            if (rootElement instanceof OrgModel) {
                                OrgModel orgModel = (OrgModel) rootElement;
                                scriptRelevantDataList
                                        .addAll(getPrivileges(orgModel));
                                scriptRelevantDataList
                                        .addAll(getCapabilities(orgModel));
                                scriptRelevantDataList
                                        .addAll(getOrganizations(orgModel));
                                scriptRelevantDataList
                                        .addAll(getResources(orgModel));
                                scriptRelevantDataList
                                        .addAll(getGroups(orgModel));
                                scriptRelevantDataList
                                        .addAll(getLocations(orgModel));
                            }
                        }
                    }
                }
                return scriptRelevantDataList;
            }
        }
        return Collections.emptyList();
    }

    private List<IModelScriptRelevantData> getPrivileges(OrgModel orgModel) {
        if (orgModel.getPrivileges() != null) {
            List<IModelScriptRelevantData> relevantData =
                    new ArrayList<IModelScriptRelevantData>();
            for (Privilege privilege : orgModel.getPrivileges()) {
                IModelScriptRelevantData iModelScriptRelevantData =
                        new RQLScriptRelevantData(privilege);
                relevantData.add(iModelScriptRelevantData);
            }
            return relevantData;
        }
        return Collections.emptyList();
    }

    private List<IModelScriptRelevantData> getCapabilities(OrgModel orgModel) {
        if (orgModel.getCapabilities() != null) {
            List<IModelScriptRelevantData> relevantData =
                    new ArrayList<IModelScriptRelevantData>();
            for (Capability capability : orgModel.getCapabilities()) {
                IModelScriptRelevantData iModelScriptRelevantData =
                        new RQLScriptRelevantData(capability);
                relevantData.add(iModelScriptRelevantData);
            }
            return relevantData;
        }
        return Collections.emptyList();
    }

    private List<IModelScriptRelevantData> getOrganizations(OrgModel orgModel) {
        if (orgModel.getOrganizations() != null) {
            List<IModelScriptRelevantData> relevantData =
                    new ArrayList<IModelScriptRelevantData>();
            for (Organization organization : orgModel.getOrganizations()) {
                IModelScriptRelevantData iModelScriptRelevantData =
                        new RQLScriptRelevantData(organization);
                relevantData.add(iModelScriptRelevantData);
                relevantData.addAll(getOrgUnits(organization));
            }
            return relevantData;
        }
        return Collections.emptyList();
    }

    private List<IModelScriptRelevantData> getOrgUnits(Organization organization) {
        if (organization.getUnits() != null) {
            List<IModelScriptRelevantData> relevantData =
                    new ArrayList<IModelScriptRelevantData>();
            for (OrgUnit orgUnit : organization.getUnits()) {
                IModelScriptRelevantData iModelScriptRelevantData =
                        new RQLScriptRelevantData(orgUnit);
                relevantData.add(iModelScriptRelevantData);
                relevantData.addAll(getPositions(orgUnit));
            }
            return relevantData;
        }
        return Collections.emptyList();
    }

    private List<IModelScriptRelevantData> getPositions(OrgUnit orgUnit) {
        if (orgUnit.getPositions() != null) {
            List<IModelScriptRelevantData> relevantData =
                    new ArrayList<IModelScriptRelevantData>();
            for (Position position : orgUnit.getPositions()) {
                IModelScriptRelevantData iModelScriptRelevantData =
                        new RQLScriptRelevantData(position);
                relevantData.add(iModelScriptRelevantData);
            }
            return relevantData;
        }
        return Collections.emptyList();
    }

    private List<IModelScriptRelevantData> getGroups(OrgModel orgModel) {
        if (orgModel.getGroups() != null) {
            List<IModelScriptRelevantData> relevantData =
                    new ArrayList<IModelScriptRelevantData>();
            for (Group group : orgModel.getGroups()) {
                IModelScriptRelevantData iModelScriptRelevantData =
                        new RQLScriptRelevantData(group);
                relevantData.add(iModelScriptRelevantData);
                getSubGroups(group, relevantData);
            }
            return relevantData;
        }
        return Collections.emptyList();
    }
    
    private List<IModelScriptRelevantData> getSubGroups(Group group,
            List<IModelScriptRelevantData> relevantData) {
        if (group != null && group.getSubGroups() != null
                && !group.getSubGroups().isEmpty()) {
            for (Group subGroup : group.getSubGroups()) {
                IModelScriptRelevantData iModelScriptRelevantData =
                        new RQLScriptRelevantData(subGroup);
                relevantData.add(iModelScriptRelevantData);
                getSubGroups(subGroup, relevantData);
            }
        }
        return relevantData;
    }

    private List<IModelScriptRelevantData> getLocations(OrgModel orgModel) {
        if (orgModel.getLocations() != null) {
            List<IModelScriptRelevantData> relevantData =
                    new ArrayList<IModelScriptRelevantData>();
            for (Location location : orgModel.getLocations()) {
                IModelScriptRelevantData iModelScriptRelevantData =
                        new RQLScriptRelevantData(location);
                relevantData.add(iModelScriptRelevantData);
            }
            return relevantData;
        }
        return Collections.emptyList();
    }

    private List<IModelScriptRelevantData> getResources(OrgModel orgModel) {
        if (orgModel.getResources() != null) {
            List<IModelScriptRelevantData> relevantData =
                    new ArrayList<IModelScriptRelevantData>();
            for (Resource resource : orgModel.getResources()) {
                IModelScriptRelevantData iModelScriptRelevantData =
                        new RQLScriptRelevantData(resource);
                relevantData.add(iModelScriptRelevantData);
            }
            return relevantData;
        }
        return Collections.emptyList();
    }

    public Image getIcon() {
        // Image image = CDSActivator.getDefault().getImageRegistry().get(
        // CdsConsts.CDS_FACTORY);
        // return image;
        return null;
    }

    @Override
    public List getComplexScriptRelevantDataList() {
        return null;
    }

    private IProject getProject() {
        if (getContext() instanceof EObject) {
            return WorkingCopyUtil.getProjectFor((EObject) getContext());
        }
        return null;
    }

}
