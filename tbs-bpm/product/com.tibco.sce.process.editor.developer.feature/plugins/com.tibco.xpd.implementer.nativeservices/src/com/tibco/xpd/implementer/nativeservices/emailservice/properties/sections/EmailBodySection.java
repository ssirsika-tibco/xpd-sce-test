/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.emailservice.properties.sections;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker.DataPickerType;
import com.tibco.xpd.implementer.nativeservices.NativeServicesActivator;
import com.tibco.xpd.implementer.nativeservices.NativeServicesConsts;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType;
import com.tibco.xpd.implementer.nativeservices.emailservice.properties.AbstractEmailSection;
import com.tibco.xpd.implementer.nativeservices.emailservice.utilities.EmailExtUtil;
import com.tibco.xpd.implementer.nativeservices.internal.Messages;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TaskService;

/**
 * Email tab's body section
 * 
 * @author njpatel
 * 
 */
public class EmailBodySection extends AbstractEmailSection implements
        SelectionListener {

    private Text message;

    private Browser previewBrowser;

    private CTabFolder messageTabFolder;

    private CTabItem srcTab, previewTab;

    private Shell shell = null;

    private ToolItem dataItem, loadItem;

    public EmailBodySection(EClass eclass) {
        super(eclass);
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        GridData gData = null;

        shell = parent.getShell();

        Composite container = toolkit.createComposite(parent);

        container.setLayout(new GridLayout());

        // Create the tab folder
        messageTabFolder = toolkit.createTabFolder(container, SWT.FLAT);
        messageTabFolder.setTabPosition(SWT.BOTTOM);
        messageTabFolder.setBorderVisible(true);
        messageTabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));

        // Add toolbar
        ToolBar bar = new ToolBar(messageTabFolder, SWT.FLAT | SWT.RIGHT);
        bar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
                | GridData.HORIZONTAL_ALIGN_END));
        // Add toolbar options
        addToolbarItems(bar);
        messageTabFolder.setTopRight(bar);
        // Compute the height of the tabs based on the preferred size of the
        // toolbar
        Point point = bar.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        messageTabFolder.setTabHeight(point.y);

        // Add the src tab
        srcTab = createSrcTab(messageTabFolder, toolkit);
        // Add the preview tab
        previewTab = createPreviewTab(messageTabFolder, toolkit);

        messageTabFolder.setSelection(srcTab);

        messageTabFolder.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                // Update the preview tab if showing
                if (messageTabFolder.getSelection().equals(previewTab)) {
                    previewBrowser.setText(message.getText());

                    // Disable the data field picker
                    dataItem.setEnabled(false);
                } else {
                    // Enable the data field picker
                    dataItem.setEnabled(true);
                }
            }
        });

        gData = new GridData(GridData.FILL_BOTH);
        gData.widthHint = 10;
        gData.heightHint = 10;
        messageTabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));

        messageTabFolder.pack();

        return container;
    }

    @Override
    public Command doGetCommand(Object obj) {
        CompoundCommand cmd = null;
        EditingDomain ed = getEditingDomain();

        if (message.equals(obj)) {
            TaskService service = getTaskServiceInput();

            if (service != null) {
                cmd = new CompoundCommand();
                EmailType email = EmailExtUtil.getEmailExtension(service);

                // If no email extension specified then create it
                if (email == null) {
                    email = EmailExtUtil.createEmailExtension(ed, cmd, service);
                }

                if (email != null) {
                    // Set the body
                    cmd.append(SetCommand.create(ed, email,
                            EmailPackage.eINSTANCE.getEmailType_Body(), message
                                    .getText()));
                }
            }

        }

        return cmd;
    }

    @Override
    protected void doRefresh() {
        TaskService service = getTaskServiceInput();
        String message = null;

        if (service != null) {
            EmailType email = EmailExtUtil.getEmailExtension(service);

            if (email != null) {
                message = email.getBody();
            }
        }

        updateMessage(message);
    }

    public void widgetDefaultSelected(SelectionEvent e) {
        // Do nothing
    }

    public void widgetSelected(SelectionEvent e) {
        // TODO Auto-generated method stub
        Object source = e.getSource();

        if (source != null) {
            if (source.equals(loadItem)) {
                // Load file
                FileDialog dialog = new FileDialog(shell, SWT.OPEN);
                String fileName = dialog.open();
                StringBuffer buffer = loadFile(fileName);

                if (buffer != null) {
                    updateMessage(buffer.toString());
                }
            } else if (source.equals(dataItem)) {
                // Show data picker
                ProcessRelevantData[] dataFields = getDataFields();

                if (dataFields != null) {
                    // Combine all data fields into a single string delimeted
                    // with space
                    String fieldStr = ""; //$NON-NLS-1$

                    for (ProcessRelevantData field : dataFields) {
                        if (fieldStr.length() > 0) {
                            fieldStr += " "; //$NON-NLS-1$
                        }

                        fieldStr += NativeServicesConsts.PROCESS_FIELD_DELIM
                                + field.getName()
                                + NativeServicesConsts.PROCESS_FIELD_DELIM;
                    }

                    if (fieldStr != "") { //$NON-NLS-1$
                        message.insert(fieldStr);
                    }
                }
            }
        }
    }

    /**
     * Add toolbar item options
     * 
     * @param bar
     */
    private void addToolbarItems(ToolBar bar) {
        final ImageRegistry imageRegistry = NativeServicesActivator
                .getDefault().getImageRegistry();

        // Add datafield picker button to the toolbar
        dataItem = new ToolItem(bar, SWT.PUSH);
        dataItem
                .setImage(imageRegistry.get(NativeServicesConsts.IMG_DATAFIELD));
        dataItem
                .setToolTipText(Messages.EmailBodySection_DataFieldPickerToolTip);
        dataItem.addSelectionListener(this);

        // Add load from file button to the toolbar
        loadItem = new ToolItem(bar, SWT.PUSH);
        loadItem.setImage(imageRegistry.get(NativeServicesConsts.IMG_FOLDER));
        loadItem.setToolTipText(Messages.EmailBodySection_LoadFromFileToolTip);
        loadItem.addSelectionListener(this);
    }

    private CTabItem createSrcTab(CTabFolder tabFolder, XpdFormToolkit toolkit) {
        // Add the src tab
        CTabItem tabItem = toolkit.createTabItem(tabFolder, SWT.NONE);
        tabItem.setText(Messages.EmailBodySection_SourceTabLabel);

        ScrolledComposite srcComposite = toolkit.createScrolledComposite(
                tabFolder, SWT.V_SCROLL | SWT.H_SCROLL);
        srcComposite.setExpandHorizontal(true);
        srcComposite.setExpandVertical(true);
        srcComposite.setLayout(new FillLayout());

        message = toolkit.createText(srcComposite,
                "", SWT.MULTI | SWT.WRAP | SWT.V_SCROLL //$NON-NLS-1$
                        | SWT.H_SCROLL);
        srcComposite.setContent(message);

        tabItem.setControl(srcComposite);
        tabItem.setImage(NativeServicesActivator.getDefault()
                .getImageRegistry().get(NativeServicesConsts.IMG_SRC));
        manageControl(message);

        srcComposite.setMinSize(message.computeSize(SWT.DEFAULT, SWT.DEFAULT));

        return tabItem;
    }

    private CTabItem createPreviewTab(CTabFolder tabFolder,
            XpdFormToolkit toolkit) {
        CTabItem tabItem = toolkit.createTabItem(tabFolder, SWT.NONE);
        tabItem.setText(Messages.EmailBodySection_PreviewTabLabel);
        previewBrowser = new Browser(tabFolder, SWT.NONE);

        tabItem.setControl(previewBrowser);
        tabItem.setImage(NativeServicesActivator.getDefault()
                .getImageRegistry().get(NativeServicesConsts.IMG_BROWSER));

        return tabItem;
    }

    /**
     * Update the message body with the given string
     * 
     * @param msg
     */
    private void updateMessage(String msg) {
        // Update the message body
        updateText(message, msg);

        // If the preview tab is selected then update that
        if (messageTabFolder.getSelection().equals(previewTab)) {
            String messageToSet = msg != null ? msg : ""; //$NON-NLS-1$
            previewBrowser.setText(messageToSet);
        }
    }

    /**
     * Load the given file and return the contents
     * 
     * @param fileName
     * @return <code>StringBuffer</code> containing the contents of the file. If
     *         the file was not read then <b>null</b> will be returned.
     */
    private StringBuffer loadFile(String fileName) {
        StringBuffer buffer = null;
        if (fileName != null && fileName.length() > 0) {
            File file = new File(fileName);

            if (file != null && file.exists() && file.canRead()) {
                FileReader reader = null;
                BufferedReader bufferedReader = null;

                try {
                    reader = new FileReader(file);

                    if (reader != null) {
                        bufferedReader = new BufferedReader(reader);

                        if (bufferedReader != null) {
                            String line = null;
                            buffer = new StringBuffer();

                            // Read contents of the file into the buffer
                            while ((line = bufferedReader.readLine()) != null) {
                                buffer.append(line);
                            }
                        }
                    }

                } catch (FileNotFoundException e) {
                    String msg = MessageFormat.format(
                            Messages.EmailBodySection_FileNotFound, fileName);
                    MessageDialog.openError(shell,
                            Messages.EmailBodySection_FileNotFoundGeneric, msg);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    // Close all input streams
                    try {
                        if (bufferedReader != null)
                            bufferedReader.close();

                        if (reader != null)
                            reader.close();
                    } catch (IOException e) {
                        // Do nothing
                    }
                }
            }
        }

        return buffer;
    }

    private ProcessRelevantData[] getDataFields() {
        DataFilterPicker dataPicker = null;
        Activity activity = getActivityInput();
        if (activity != null) {
            dataPicker = new DataFilterPicker(shell,
                    DataPickerType.DATAFIELD_FORMALPARAMETER, true);
            dataPicker.setScope(activity);
        }
        if (dataPicker != null && dataPicker.open() == DataPicker.OK) {
            Object[] results = dataPicker.getResult();

            if (results != null && results.length > 0) {
                List<ProcessRelevantData> dataFields = new ArrayList<ProcessRelevantData>();

                for (Object item : results) {
                    if (item instanceof ProcessRelevantData) {
                        dataFields.add((ProcessRelevantData) item);
                    }
                }

                return (ProcessRelevantData[]) dataFields
                        .toArray(new ProcessRelevantData[dataFields.size()]);
            }
        }
        return null;
    }

}
