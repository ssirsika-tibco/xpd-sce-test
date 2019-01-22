/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry.ui.wizard;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.registry.ui.Activator;
import com.tibco.xpd.registry.ui.internal.Messages;

/**
 * A wizard for importing a wsdl file from a source accessible through the file
 * system
 */
public class WsdlFileImportWizard extends WsdlImportBaseWizard {

    public WsdlFileImportWizard() {
        super(new WsdlSelectionPage(), Messages.WsdlFileImportWizard_title);
    }

    /**
     * The page while provides the navigation to the source file
     */
    private static class WsdlSelectionPage extends WsdlSourceBaseWizardPage {
        /** The number of columns in the file section. */
        private static final int FILE_SECTION_COLUMN_COUNT = 3;

        /** File names separator */
        private static final String FILE_SEPARATOR = File.pathSeparator;

        /** Text control for the file location. */
        private Text fileText;

        List<String> selectedFilesName = new LinkedList<String>();

        /** Listener for text modification events. */
        private final ModifyListener modifyListener;

        private boolean initialised;

        protected WsdlSelectionPage() {
            super(Messages.WsdlFileImportWizard_Selection_title);
            setDescription(Messages.WsdlFileImportWizard_Selection_longdesc);

            modifyListener = new ModifyListener() {
                @Override
                public void modifyText(ModifyEvent e) {
                    selectedFilesName.clear();
                    String[] files =
                            fileText.getText().trim().split(FILE_SEPARATOR);
                    for (String file : files) {
                        selectedFilesName.add(file);
                    }
                    updateTarget(getWsdlImportMappings());
                    setPageComplete(validatePage(false, true));
                }
            };
        }

        /**
         * @param parent
         *            The parent to add the controls to.
         * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
         */
        @Override
        public void createControl(Composite parent) {
            Composite control = new Composite(parent, SWT.NONE);
            GridLayout layout =
                    new GridLayout(FILE_SECTION_COLUMN_COUNT, false);
            control.setLayout(layout);

            Label fileLabel = new Label(control, SWT.NONE);
            fileLabel.setText(Messages.WsdlFileImportWizard_Location_label);
            fileText = new Text(control, SWT.BORDER | SWT.FLAT);
            fileText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
            Button fileBrowse = new Button(control, SWT.PUSH);
            fileBrowse.setText(Messages.WsdlFileImportWizard_Browse_button);
            fileBrowse.addSelectionListener(new SelectionListener() {
                @Override
                public void widgetDefaultSelected(SelectionEvent e) {
                }

                @Override
                public void widgetSelected(SelectionEvent e) {
                    //XDP-2931- changes to support multiple WSDLs import 
                    FileDialog dialog = new FileDialog(getShell(), SWT.MULTI);
                    selectedFilesName.clear();
                    dialog.setFilterPath(fileText.getText());
                    dialog.setFilterExtensions(new String[] { "*.wsdl", "*.*" }); //$NON-NLS-1$ //$NON-NLS-2$

                    StringBuffer filename = new StringBuffer();
                    dialog.open();

                    String[] files = dialog.getFileNames();
                    if (files != null) {
                        for (int i = 0; i < files.length; i++) {
                            // list of files to be imported
                            selectedFilesName.add(dialog.getFilterPath()
                                    + File.separator
                                    + files[i]);
                            // file names string to be shown in the dialog
                            filename.append(dialog.getFilterPath()
                                    + File.separator
                                    + files[i]);
                            if ((i + 1) < files.length && files[i + 1] != null) {
                                // add separator only if there is another file
                                // name to be appended
                                filename.append(FILE_SEPARATOR);
                            }
                        }
                    }

                    if (filename != null) {
                        fileText.setText(filename.toString());
                    }
                }
            });

            fileText.addModifyListener(modifyListener);
            setControl(control);
            initialised = true;
            setPageComplete(validatePage(true, false));
        }

        /**
         * Gets the WSDL files selected in the wizard.
         * 
         * @return The input stream for the WSDL files.
         */
        public InputStream[] getWsdlInputStreams() {
            List<InputStream> inputStreams = new LinkedList<InputStream>();
            InputStream is = null;
            for (String filename : selectedFilesName) {
                try {
                    is = new BufferedInputStream(new FileInputStream(filename));
                } catch (IOException exception) {
                    Activator.getDefault().getLogger().error(exception);
                }
                inputStreams.add(is);
            }
            return inputStreams.toArray(new InputStream[inputStreams.size()]);
        }

        /**
         * Gets the WSDL file selected in the wizard.
         * 
         * @return The input stream for the WSDL file.
         */
        public InputStream getWsdlInputStream() {
            InputStream is = null;
            try {
                String filename = fileText.getText().trim();
                is = new BufferedInputStream(new FileInputStream(filename));
            } catch (IOException exception) {
                Activator.getDefault().getLogger().error(exception);
            }
            return is;
        }

        /**
         * @return true if ready to proceed
         */
        private boolean validatePage(boolean setFocus, boolean showMessage) {
            if (!initialised) {
                return false;
            }
            setMessage(null);
            setErrorMessage(null);

            if (fileText.getText().trim().length() == 0) {
                if (showMessage) {
                    setErrorMessage(Messages.WsdlFileImportWizard_LocationCannotBeEmpty_longdesc);
                }
                if (setFocus) {
                    fileText.setFocus();
                }
                return false;

            }
            if (!fileExists()) {
                if (showMessage) {
                    setErrorMessage(Messages.WsdlFileImportWizard_FileNotExists_longdesc);
                }
                if (setFocus) {
                    fileText.setFocus();
                }
                return false;
            }
            return true;
        }

        /**
         * @return true if the specified file exists.
         */
        private boolean fileExists() {            
            boolean exists = true;
            //XDP-2931- changes to support multiple WSDLs import 
            for (String filename : selectedFilesName) {
                if (filename != null) {
                    File wsdlFile = new File(filename);
                    if (!wsdlFile.exists() || !wsdlFile.isFile()) {
                        exists = false;
                    }
                }
            }

            return exists;
        }

        @Override
        WsdlImportMapping[] getWsdlImportMappings() {
            //XDP-2931- changes to support multiple WSDLs import 
            List<WsdlImportMapping> wsdlImportMappings =
                    new LinkedList<WsdlImportMapping>();

            for (String filename : selectedFilesName) {
                try {
                    String fileNameGuess = guessFileName(filename);
                    if (fileNameGuess.length() != 0) {
                        String fileURI = URI.createFileURI(filename).toString();
                        wsdlImportMappings.add(new WsdlImportMapping(fileURI,
                                fileNameGuess));
                    }

                } catch (IllegalArgumentException e) {
                    return new WsdlImportMapping[] {};
                }
            }
            return wsdlImportMappings
                    .toArray(new WsdlImportMapping[wsdlImportMappings.size()]);
        }

        /**
         * @return A guess at the WSDL file name, or an empty string.
         */
        String guessFileName(String filename) {
            String guess = ""; //$NON-NLS-1$
            File file = new File(filename);
            guess = file.getName();
            return applyWsdlExtension(guess);
        }

        /**
         * @return A guess at the WSDL file name, or an empty string.
         */
        String guessFileName() {
            String guess = ""; //$NON-NLS-1$
            File filename = new File(fileText.getText().trim());
            guess = filename.getName();
            return applyWsdlExtension(guess);
        }
    }

}
