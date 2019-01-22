/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.brm.component;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.osgi.service.resolver.VersionRange;
import org.osgi.framework.Version;

import com.tibco.amf.sca.model.composite.Component;
import com.tibco.amf.tools.packager.IPackagerParticipant;
import com.tibco.amf.tools.packager.util.PackagerUtils;
import com.tibco.bx.amx.model.service.BxServiceImplementation;
import com.tibco.bx.model.service.ServiceImplementation;
import com.tibco.n2.ut.configuration.builder.ProcessUtils;
import com.tibco.xpd.daa.internal.util.PluginManifestHelper;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Package;

/**
 * @author kupadhya
 * 
 */
public class N2PEPackagerParticipant implements IPackagerParticipant {

    private static final String LINE_SEPARATOR = "line.separator";

    private static final String workModelVersionRange =
            "workModelVersionRange=\"";

    private static final String workModelVersionRangeRegex =
            workModelVersionRange + "(.*?)\"";

    private static final String BPEL_FILE_ENCODING = "UTF-8";

    /**
     * @see com.tibco.amf.tools.packager.IPackagerParticipant#done(java.lang.Object)
     * 
     * @param arg0
     */
    public void done(Object arg0) {
        // TODO Auto-generated method stub

    }

    /**
     * @see com.tibco.amf.tools.packager.IPackagerParticipant#prepareArtifact(java.lang.Object,
     *      org.eclipse.core.runtime.IPath)
     * 
     * @param contextObjecxt
     * @param arg1
     * @return
     */
    public File prepareArtifact(Object contextObject, IPath iPath) {
        if (!(contextObject instanceof Component)) {
            return null;
        }
        boolean isBpelFile = isBpelFile(iPath);
        if (!isBpelFile) {
            return null;
        }
        Component bxComponent = (Component) contextObject;
        String timeStamp = PackagerUtils.getQualifier(bxComponent);
        BxServiceImplementation bxImplementation =
                (BxServiceImplementation) bxComponent.getImplementation();
        ServiceImplementation serviceModel = bxImplementation.getServiceModel();
        String xpdlFilePath = serviceModel.getModuleName();
        IFile xpdlFile =
                ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(
                        xpdlFilePath));
        WorkingCopy xpdlWC = WorkingCopyUtil.getWorkingCopy(xpdlFile);
        Package xpdlPackage = (Package) xpdlWC.getRootElement();
        String processVersionRange =
                ProcessUtils.getProcessVersionRange(xpdlPackage);
        VersionRange versionRange = new VersionRange(processVersionRange);
        String upperVersion =
                PluginManifestHelper
                        .replaceProjectVersionQualifierWithQualifierReplacer(versionRange
                                .getMaximum().toString().toString(),
                                timeStamp);
        String lowerVersion =
                PluginManifestHelper
                        .replaceProjectVersionQualifierWithQualifierReplacer(versionRange
                                .getMinimum().toString().toString(),
                                timeStamp);
        VersionRange upadatedVR =
                new VersionRange(new Version(lowerVersion), versionRange
                        .getIncludeMinimum(), new Version(upperVersion),
                        versionRange.getIncludeMaximum());
        String strUpdatedVR = upadatedVR.toString();
        String newStr = workModelVersionRange + strUpdatedVR + "\"";
        System.out.println("File passed is " + iPath);
        IFile bpelFile =
                ResourcesPlugin.getWorkspace().getRoot().getFile(iPath);
        replaceUserTaskModelWithIO(bpelFile, newStr);
        return null;
    }

    /**
     * @see com.tibco.amf.tools.packager.IPackagerParticipant#started(java.lang.Object)
     * 
     * @param arg0
     */
    public void started(Object arg0) {
        // TODO Auto-generated method stub
    }

    private boolean isBpelFile(IPath path) {
        String fileExtension = path.getFileExtension();
        if ("bpel".equals(fileExtension)) {
            return true;
        }
        return false;
    }

    private void replaceUserTaskModelWithIO(IFile bpelFile, String timeStamp) {
        StringBuilder strBuilder = new StringBuilder();
        InputStream contentsIS = null;
        InputStreamReader isReader = null;
        BufferedReader bufferedReader = null;
        try {
            contentsIS = bpelFile.getContents();
            isReader =
                    new InputStreamReader(contentsIS, Charset
                            .forName(BPEL_FILE_ENCODING));
            bufferedReader = new BufferedReader(isReader);
            String expLine = null;
            while ((expLine = bufferedReader.readLine()) != null) {
                strBuilder.append(expLine);
                strBuilder.append(System.getProperty(LINE_SEPARATOR));
            }
        } catch (CoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isReader != null) {
                try {
                    isReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (contentsIS != null) {
                try {
                    contentsIS.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String origBpelContents = strBuilder.toString();
        String changedBpelContents =
                origBpelContents.replaceAll(workModelVersionRangeRegex,
                        timeStamp);
        byte[] bytes =
                changedBpelContents.getBytes(Charset
                        .forName(BPEL_FILE_ENCODING));
        ByteArrayInputStream byteIS = null;
        try {
            byteIS = new ByteArrayInputStream(bytes);
            bpelFile
                    .setContents(byteIS, IFile.FORCE, new NullProgressMonitor());
        } catch (CoreException e) {
            e.printStackTrace();
        } finally {
            if (byteIS != null) {
                try {
                    byteIS.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}