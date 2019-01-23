/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry.ui.wizard;

import java.util.ArrayList;
import java.util.TreeSet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardSelectionPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

import com.tibco.xpd.registry.ui.Activator;
import com.tibco.xpd.registry.ui.internal.Messages;
import com.tibco.xpd.ui.dialogs.AbstractXpdSelectionWizardPage;

/**
 * Import a WSDL file from a local file, URL or JAX-R provider.
 * 
 * @author nwilson
 */
public class WsdlImportWizard extends Wizard implements IImportWizard {

    public interface IImportProjectProvider {
        IProject getProject();
        void setProject(IProject project);
    }
    
    /** The dialog title identifier. */
    private static final String WSDL_TITLE = Messages.WsdlImportWizard_title;

    /** The dialog title identifier. */
    private static final String SELECTION_TITLE = Messages.WsdlImportWizard_Selection_title;

    /** The dialog message identifier. */
    private static final String SELECTION_DESCRIPTION = Messages.WsdlImportWizard_Selection_longdesc;

    /** The WSDL selection page. */
    private final WsdlSelectionPage selectionPage;
    
    /**
     * The operation picker page
     */
    private WebServiceOperationPickerPage webServiceOperationPickerPage = null;
    
    /**
     * Determines whether we want the wizard nodes to have operation picker page at the end or not 
     */
    private boolean isWithOperationPicker = false;

    /**
     * Determines which wsdl type we want to select from in the operation picker (only applicable if
     * isWithOperationPicker is true)
     */
    private int wsdlType = 0;
    
    /**
     * The project used for web service operation picker to get its contents
     */
    private IProject project = null;
    
    /**
     * Ids of wizards that should be disabled (Only applicable if isWithOperationPicker is true)
     */
    private ArrayList<String> disabledSubWizards = null;
    
    /**
     * Constructor.
     */
    public WsdlImportWizard() {
        super();
        setWindowTitle(WSDL_TITLE);
        selectionPage = new WsdlSelectionPage();
    }
    
    /**
     * Constructor.
     * @param isWithOperationPicker Determines whether we want the wizard nodes to have operation picker page at the end or not
     * @param wsdlType Determines which wsdl type we want to select from in the operation picker
     * @param project used for web service operation picker to get its contents
     * @param disabledSubWizards containing ids of the wizard nodes that should be disabled
     */
    public WsdlImportWizard(boolean isWithOperationPicker, int wsdlType, IProject project, ArrayList<String> disabledSubWizards) {
        super();
        setWindowTitle(WSDL_TITLE);
        this.project = project;
        selectionPage = new WsdlSelectionPage();       
        this.isWithOperationPicker = isWithOperationPicker;
        this.wsdlType = wsdlType;
        this.disabledSubWizards = disabledSubWizards;
    }

    /**
     * @param workbench
     *            The workbench for this wizard.
     * @param selection
     *            The currently selected workbench items.
     * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
     *      org.eclipse.jface.viewers.IStructuredSelection)
     */
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        selectionPage.init(workbench, selection);
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     */
    @Override
    public void addPages() {
        addPage(selectionPage);        
    }

    /**
     * @return true if the finish was successful, otherwise false.
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    @Override
    public boolean performFinish() {

        // The chosen nested wizard result will only be taken into
        // consideration, so it doesn't mater if this method returns true or
        // false.
        return false;
    }

    /**
     * @author nwilson
     */
    class WsdlSelectionPage extends AbstractXpdSelectionWizardPage {

        /** The manager to control radio buttons and Control enablement. */
        private final SelectionGroupManager sgm;

        /** Listener for selection events. */
        private final SelectionListener selection;

        /** The set of import wizards. */
        private final TreeSet<WizardNodeInfo> wizards;

        /** The currently selected button. */
        private Button selected;

        /**
         * Constructor.
         */
        protected WsdlSelectionPage() {
            super(SELECTION_TITLE);
            setTitle(SELECTION_TITLE);
            setDescription(SELECTION_DESCRIPTION);
            setForcePreviousAndNextButtons(true);
            sgm = new SelectionGroupManager();
            selection = new SelectionListener() {
                public void widgetDefaultSelected(SelectionEvent e) {
                }

                public void widgetSelected(SelectionEvent e) {
                    if (e.widget instanceof Button) {
                        selected = (Button) e.widget;
                    }
                    updatePageComplete();
                }
            };
            sgm.addSelectionListener(selection);

            wizards = new TreeSet<WizardNodeInfo>();
            IExtensionPoint extensionPoint = Platform.getExtensionRegistry()
                    .getExtensionPoint(Activator.PLUGIN_ID,
                            "wsdlImportProvider"); //$NON-NLS-1$
            IConfigurationElement[] configs = extensionPoint
                    .getConfigurationElements();
            for (IConfigurationElement config : configs) {
                String id = config.getAttribute("id"); //$NON-NLS-1$
                String label = config.getAttribute("label"); //$NON-NLS-1$
                try {
                    Object o = config.createExecutableExtension("wizard"); //$NON-NLS-1$
                    if (o instanceof IWizard) {
                        IWizard wizard = (IWizard) o;
                        WizardNodeInfo info = new WizardNodeInfo(id, label,
                                wizard);
                        wizards.add(info);
                        
                        if (o instanceof IImportProjectProvider) {
                            ((IImportProjectProvider)o).setProject(project);
                        }
                    }
                } catch (CoreException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * @param workbench
         *            The workbench.
         * @param selection
         *            The current workbench selection.
         */
        public void init(IWorkbench workbench, IStructuredSelection selection) {
            for (WizardNodeInfo info : wizards) {
                IWizard wizard = info.getWizard();
                if (wizard instanceof IWorkbenchWizard) {
                    IImportWizardWithOperationPicker ww = (IImportWizardWithOperationPicker) wizard;
                    ww.init(workbench, selection);
                    
                    // if this wizard was called from an action button we add the operation picker page to all wizard nodes
                    if (isWithOperationPicker){            
                        WebServiceOperationPickerPage pickerPage;
                        switch(wsdlType){
                            // only show web services
                            case 0:
                                pickerPage = new WebServiceOperationPickerPage(com.tibco.xpd.registry.ui.wizard.WebServiceOperationPickerPage.WSDL_TYPE.STANDARD);
                                break;
                            case 1: // only show bw services
                                pickerPage = new WebServiceOperationPickerPage(com.tibco.xpd.registry.ui.wizard.WebServiceOperationPickerPage.WSDL_TYPE.BW);
                                break;
                            default:  // show all
                                pickerPage = new WebServiceOperationPickerPage(com.tibco.xpd.registry.ui.wizard.WebServiceOperationPickerPage.WSDL_TYPE.ANY);
                                break;
                        }
                        
                        // set the operation page with default project for exploring in
                        pickerPage.setInput(project);
                        ww.setOperationPickerPage(pickerPage);
                    }
                }
            }
        }

        /**
         * @param parent
         *            The parent to add the controls to.
         * @see org.eclipse.jface.dialogs.IDialogPage#createControl(
         *      org.eclipse.swt.widgets.Composite)
         */
        public void createControl(Composite parent) {
            Composite control = new Composite(parent, SWT.NONE);
            GridLayout layout = new GridLayout();
            control.setLayout(layout);
            boolean isFirstWizard = true;
            for (WizardNodeInfo info : wizards) {
                if ((!isWithOperationPicker)
                        || (isWithOperationPicker && !disabledSubWizards
                                .contains(info.getId()))) {
                    Button button = info.createButton(control);
                    sgm.addButton(button);
                    if (isFirstWizard && button != null) {
                        button.setEnabled(true);
                    }
                    isFirstWizard = false;
                }
            }

            setControl(control);
            updatePageComplete();
        }

        /**
         * Sets the page complete status based on user input.
         */
        private void updatePageComplete() {
            if (selected != null) {
                Object data = selected.getData();
                if (data instanceof IWizardNode) {
                    IWizardNode node = (IWizardNode) data;
                    setSelectedNode(node);
                    if (isWithOperationPicker && node.getWizard() instanceof IWorkbenchWizard) {                        
                        IImportWizardWithOperationPicker ww = (IImportWizardWithOperationPicker) node.getWizard();                        
                        webServiceOperationPickerPage = (WebServiceOperationPickerPage)ww.getOperationPickerPage();
                    }
                }
            }
        }
    }

    /**
     * @author nwilson
     */
    class WizardNodeInfo implements Comparable<WizardNodeInfo> {

        /** The wizard ID. */
        private final String id;
        /** The wizard label. */
        private final String label;
        /** The wizard class. */
        private final IWizard wizard;
        /** The wizard node. */
        private final IWizardNode node;

        /**
         * @param id
         *            The wizard ID.
         * @param label
         *            The wizard label.
         * @param wizard
         *            The wizard class.
         */
        public WizardNodeInfo(String id, String label, IWizard wizard) {
            this.id = id;
            this.label = label;
            this.wizard = wizard;
            node = new WsdlImportWizardNode(wizard);
        }

        /**
         * @return The wizard ID.
         */
        public String getId() {
            return id;
        }

        /**
         * @return The wizard label.
         */
        public String getLabel() {
            return label;
        }

        /**
         * @return The wizard class.
         */
        public IWizard getWizard() {
            return wizard;
        }

        /**
         * @param parent
         *            The parent component.
         * @return The new button for this wizard.
         */
        public Button createButton(Composite parent) {
            Button button = new Button(parent, SWT.RADIO);
            button.setText(label);
            button.setData(node);
            return button;
        }

        /**
         * @param obj
         *            The object to compare to.
         * @return true if the labels match.
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            boolean equal = false;
            if (obj instanceof WizardNodeInfo) {
                WizardNodeInfo other = (WizardNodeInfo) obj;
                if (label.equals(other.label)) {
                    equal = true;
                }
            }
            return equal;
        }

        /**
         * @return The label hashcode.
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            return label.hashCode();
        }

        /**
         * @param other
         *            The WizardNodeInfo to compare to.
         * @return The comparison result.
         * @see java.lang.Comparable#compareTo(java.lang.Object)
         */
        public int compareTo(WizardNodeInfo other) {
            return label.compareToIgnoreCase(other.label);
        }

    }

    /**
     * @return
     */
    public WebServiceOperationPickerPage getWebServiceOperationPickerPage() {
        return webServiceOperationPickerPage;
    }
}
