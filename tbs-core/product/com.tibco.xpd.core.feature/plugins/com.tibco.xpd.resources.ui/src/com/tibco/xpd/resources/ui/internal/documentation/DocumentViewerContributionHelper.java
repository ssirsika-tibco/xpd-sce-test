/**
 * DocumentViewerContributionHelper.java
 *
 * 
 *
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */
package com.tibco.xpd.resources.ui.internal.documentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.ui.documentation.IDocGen;

/**
 * DocumentViewerContributionHelper
 * <p>
 * This class loads the contributions to the
 * <code>com.tibco.xpd.resources.ui.documentViewerContribution</code> extension
 * point.
 * <p>
 * 
 * @author mtorres
 * 
 */
public class DocumentViewerContributionHelper {

    /**
     * Cache all the contributions to Document Viewer.
     */
    static {
        loadAllDocumentViewerContributions();
    }

    private static List<DocumentViewerContribution> allContributions;
    
    private static Set<String> allContributedSpecialFolders;
    
    private static Set<String> allContributedFileExtensions;

    /**
     * DocumentViewerContribution
     * <p>
     * Class representing one contribution to DocumentViewerContribution
     * extension point.
     * 
     */
    private static class DocumentViewerContribution {
        
        private String specialFolders;
        
        private String fileExtensions;
        
        private Set<String> specialFoldersSet;
        
        private Set<String> fileExtensionsSet;
        
        private IDocGen documentGenerator;

        /**
         */
        public DocumentViewerContribution(String specialFolders,
                String fileExtensions, IDocGen documentGenerator) {
            this.specialFolders = specialFolders;
            this.fileExtensions = fileExtensions;
            this.documentGenerator = documentGenerator;
        }

        /**
         * @return the specialFolders
         */
        public String getSpecialFolders() {
            return specialFolders;
        }
        
        public Set<String> getSpecialFoldersSet(){
            if (specialFolders != null && specialFoldersSet == null) {
                specialFoldersSet = new HashSet<String>();
                String[] specialFolderArr =
                        specialFolders.split(",");//$NON-NLS-1$
                if (specialFolderArr != null) {
                    for (int i = 0; i < specialFolderArr.length; i++) {
                        specialFoldersSet
                                .add(specialFolderArr[i]);
                    }
                }
            }
            return specialFoldersSet;
        }

        /**
         * @return the fileExtensions
         */
        public String getFileExtensions() {
            return fileExtensions;
        }
        
        public Set<String> getFileExtensionsSet(){
            if (fileExtensions != null && fileExtensionsSet == null) {
                fileExtensionsSet = new HashSet<String>();
                String[] fileExtensionsArr =
                        fileExtensions.split(",");//$NON-NLS-1$
                if (fileExtensionsArr != null) {
                    for (int i = 0; i < fileExtensionsArr.length; i++) {
                        fileExtensionsSet
                                .add(fileExtensionsArr[i]);
                    }
                }
            }
            return fileExtensionsSet;
        }
        
        public IDocGen getDocumentGenerator() {
            return documentGenerator;
        }
        
    }

    private static final String EXT_POINT_ID = "documentViewerContribution"; //$NON-NLS-1$

    private static final String DOCUMENT_VIEWER_CONTRIBUTION_EL = "documentViewerContribution"; //$NON-NLS-1$

    private static final String SPECIAL_FOLDERS_ATTR = "specialFolders"; //$NON-NLS-1$

    private static final String FILE_EXTENSIONS_ATTR = "fileExtensions"; //$NON-NLS-1$

    private static final String DOCUMENT_GENERATOR = "documentGenerator"; //$NON-NLS-1$




    /**
     * Load the Document Viewer contributions contributed to all Views
     */
    private static void loadAllDocumentViewerContributions() {

        allContributions = new ArrayList<DocumentViewerContribution>();

        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(XpdResourcesUIActivator.ID,
                                EXT_POINT_ID);

        if (point != null) {
            //
            // Go thru each contribution to this extension point.
            //
            IExtension[] extensions = point.getExtensions();

            if (extensions != null) {
                for (IExtension ext : extensions) {
                    IConfigurationElement[] configElements =
                            ext.getConfigurationElements();

                    for (int i = 0; i < configElements.length; i++) {
                        IConfigurationElement configEl = configElements[i];

                        if (DOCUMENT_VIEWER_CONTRIBUTION_EL.equals(configEl
                                .getName())) {
                            try {
                                Object o =
                                        configEl
                                                .createExecutableExtension(DOCUMENT_GENERATOR);
                                if (o instanceof IDocGen) {
                                    allContributions
                                            .add(new DocumentViewerContribution(
                                                    configEl
                                                            .getAttribute(SPECIAL_FOLDERS_ATTR),
                                                    configEl
                                                            .getAttribute(FILE_EXTENSIONS_ATTR),(IDocGen) o));
                                }

                            } catch (Exception e) {
                                throw new RuntimeException(
                                        "Failed loading quick search contribution from: " //$NON-NLS-1$
                                                + ext.getContributor()
                                                        .getName(), e);
                            }
                        }
                    }
                }
            }
        }

        return;
    }
    
    public static Set<String> getAllContributedSpecialFolders() {
        if (allContributedSpecialFolders == null) {
            allContributedSpecialFolders = new HashSet<String>();
            if (allContributions != null) {
                for (DocumentViewerContribution documentViewerContribution : allContributions) {
                    if (documentViewerContribution != null) {
                        allContributedSpecialFolders
                                .addAll(documentViewerContribution
                                        .getSpecialFoldersSet());
                    }
                }
            }
        }
        return allContributedSpecialFolders;
    }
    
    public static Set<String> getAllContributedFileExtensions() {
        if (allContributedFileExtensions == null) {
            allContributedFileExtensions = new HashSet<String>();
            if (allContributions != null) {
                for (DocumentViewerContribution documentViewerContribution : allContributions) {
                    if (documentViewerContribution != null) {
                        allContributedFileExtensions
                                .addAll(documentViewerContribution
                                        .getFileExtensionsSet());
                    }
                }
            }
        }
        return allContributedFileExtensions;
    }
    
    public static Set<IDocGen> getDocumentGenerator(String fileExtension) {
        if (fileExtension != null && allContributions != null
                && !allContributions.isEmpty()) {
            Set<IDocGen> documentGenerators = new HashSet<IDocGen>();
            for (DocumentViewerContribution documentViewerContribution : allContributions) {
                if (documentViewerContribution != null
                        && documentViewerContribution.getFileExtensionsSet() != null) {
                    if (documentViewerContribution.getFileExtensionsSet()
                            .contains(fileExtension)
                            && documentViewerContribution
                                    .getDocumentGenerator() != null) {
                        documentGenerators.add(documentViewerContribution
                                .getDocumentGenerator());
                    }
                }
            }
            return documentGenerators;
        }
        return Collections.emptySet();
    }



}
