/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.core.test.util;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * Manages the <code>compositeContributors</code> extension.
 * <p>
 * <i>Created: 10 Sept 2009</i>
 * </p>
 * 
 * @author Kamlesh Upadhyaya
 */
public class FileComparatorExtensionManager {

    private static final char PATH_SEPARATOR = '/';

    private static final String FILE_NAME = "fileName"; //$NON-NLS-1$

    private static final String FILE_INFORMATION = "fileInformation"; //$NON-NLS-1$

    /** */
    private static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$

    /** */
    private static final String EXTENSION_NAME = "fileComparator"; //$NON-NLS-1$

    private static final String FILE_EXT_SEPARATOR = "."; //$NON-NLS-1$

    /** Singleton instance. */
    private static final FileComparatorExtensionManager INSTANCE =
            new FileComparatorExtensionManager();

    private Map<String, FileComparator> fileComparators;

    /**
     * Private constructor to prevent instantiation.
     */
    private FileComparatorExtensionManager() {
    }

    public static FileComparatorExtensionManager getInstance() {
        return INSTANCE;
    }

    public FileComparator getFileComparator(String projectName,
            String goldFileName, String generatedFileName) {
        synchronized (INSTANCE) {
            if (fileComparators == null) {
                fileComparators = createFileComparatorContributors();
            }
            String goldEntryFileName = getFileName(goldFileName);
            String generatedEntryFileName = getFileName(generatedFileName);
            // jar file entries cannot match on name so commenting out
            // if (!goldEntryFileName.equals(generatedEntryFileName)) {
            // throw new IllegalStateException("For project " + projectName
            // + " Gold Entry File Name " + goldEntryFileName
            // + " generated Entry File " + generatedEntryFileName
            // + " do not match");
            // }
            FileComparator fileComparator =
                    fileComparators.get(goldEntryFileName);
            if (fileComparator == null) {
                String fileExtension = getFileExtension(goldEntryFileName);
                fileComparator = fileComparators.get("*" + fileExtension); //$NON-NLS-1$
            }
            return fileComparator;
        }
    }

    private Map<String, FileComparator> createFileComparatorContributors() {
        Map<String, FileComparator> fileComparatorMap =
                new HashMap<String, FileComparator>();
        IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
        IConfigurationElement[] configurationElements =
                extensionRegistry
                        .getConfigurationElementsFor(TestUtilPlugin.PLUGIN_ID,
                                EXTENSION_NAME);
        for (IConfigurationElement element : configurationElements) {
            try {
                Object className =
                        element.createExecutableExtension(CLASS_ATTRIBUTE);
                if (className instanceof FileComparator) {
                    FileComparator contributor = (FileComparator) className;
                    IConfigurationElement[] children =
                            element
                                    .getChildren(FileComparatorExtensionManager.FILE_INFORMATION);
                    for (int index = 0; index < children.length; index++) {
                        String value =
                                children[index]
                                        .getAttribute(FileComparatorExtensionManager.FILE_NAME);
                        fileComparatorMap.put(value, contributor);

                    }
                }
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
        return fileComparatorMap;
    }

    /**
     * Returns the file name from the path
     * 
     * @param filePath
     * @return
     */
    private String getFileName(String filePath) {
        String toReturn = null;
        if (filePath == null || filePath.length() < 1) {
            return toReturn;
        }
        int indexOf =
                filePath
                        .lastIndexOf(FileComparatorExtensionManager.PATH_SEPARATOR);
        if (indexOf != -1) {
            toReturn = filePath.substring(indexOf + 1);
        } else {
            toReturn = filePath;
        }
        return toReturn;
    }

    /**
     * 
     * @param zipEntryName
     * @return
     */
    private String getFileExtension(String fileName) {
        if (fileName == null) {
            return fileName;
        }
        String fileExtension = fileName;
        int lastIndexOf =
                fileName
                        .lastIndexOf(FileComparatorExtensionManager.FILE_EXT_SEPARATOR);
        if (lastIndexOf != -1) {
            fileExtension = fileName.substring(lastIndexOf);
        }
        return fileExtension;
    }
}
