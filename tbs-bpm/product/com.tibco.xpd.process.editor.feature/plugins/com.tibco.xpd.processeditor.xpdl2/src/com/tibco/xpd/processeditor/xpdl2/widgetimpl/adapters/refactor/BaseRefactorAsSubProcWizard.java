/**
 * RefactorAsSubProcWizard.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processwidget.adapters.DiagramModelImageProvider;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * RefactorAsSubProcWizard
 * 
 * Base Refactor as sub-process (embedded / independent) wizard.
 */
public abstract class BaseRefactorAsSubProcWizard extends ProcessRefactorWizard {

    private RefactorAsSubProcWizardInfo refactorInfo;

    private EditingDomain editingDomain = null;

    private ProcessRefactorConfigurationItem multiEntryPathConfigItem;

    private ProcessRefactorConfigurationItem multiExitPathConfigItem;

    private List<EObject> multiEntryAreaObjects;

    private List<EObject> multiExitAreaObjects;

    private Collection<EObject> selectedObjsAndConns;

    private String wizardTitle;

    private String wizardDescription;

    protected static final int IMG_MARGIN = 20;

    /**
     * Construct a base refactor as sub-proces wizard.
     * 
     * @param refactorInfo
     * @param editingDomain
     * @param wizardTitle
     * @param wizardDescription
     */
    public BaseRefactorAsSubProcWizard(
            RefactorAsSubProcWizardInfo refactorInfo,
            EditingDomain editingDomain, String wizardTitle,
            String wizardDescription) {
        super(refactorInfo);

        this.editingDomain = editingDomain;

        this.wizardTitle = wizardTitle;
        this.wizardDescription = wizardDescription;

        setWindowTitle(wizardTitle);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.
     * ProcessRefactorWizard#init(java.lang.Object)
     */
    @Override
    public void init(Object inputObject) {
        super.init(inputObject);

        this.refactorInfo = (RefactorAsSubProcWizardInfo) inputObject;
        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.
     * ProcessRefactorWizard#getConfigurationItems()
     */
    @Override
    protected List<ProcessRefactorConfigurationItem> getConfigurationItems(
            Object inputObject) {

        if (refactorInfo == null) {
            // Still in super construct, make sure we set up the the refactor
            // info in our class befire we continue.
            refactorInfo = (RefactorAsSubProcWizardInfo) inputObject;
        }

        ImageRegistry ir =
                Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry();

        List<ProcessRefactorConfigurationItem> configItems =
                new ArrayList<ProcessRefactorConfigurationItem>(0);

        if ((refactorInfo.validationInfo & RefactorAsSubProcWizardInfo.SUBPROC_INDI_HAS_IMPLEMENTING_EVENTS) != 0) {
            // Cannot refactor events that implement process interface methods
            // into independent sub-process.
            ProcessRefactorConfigurationItem implEvents =
                    new ProcessRefactorConfigurationItem(
                            "ObjectsContainImplementingEvents", //$NON-NLS-1$
                            Messages.BaseRefactorAsSubProcWizard_CantRefactorImplementingEvents_label,
                            Messages.BaseRefactorAsSubProcWizard_CantRefactorImplementingEvents_longdesc2,
                            false,
                            true,
                            ir
                                    .get(ProcessEditorConstants.IMG_INTERMEDIATEMETHOD));

            implEvents.setCheckIsEnabled(false);

            configItems.add(implEvents);

        } else {

            if ((refactorInfo.validationInfo & RefactorAsSubProcWizardInfo.SUBPROC_MULTIPLE_ENTRYPATHS) != 0) {
                setupMultiEntryAreaObjects();

                multiEntryPathConfigItem =
                        new ProcessRefactorConfigurationItem(
                                "MultiEntryPaths", //$NON-NLS-1$
                                Messages.BaseRefactorAsSubProcWizard_OkToJoinEntryPath_message,
                                Messages.BaseRefactorAsSubProcWizard_OkToJoinEntryPath_longdesc,
                                false,
                                true,
                                ir
                                        .get(ProcessEditorConstants.IMG_JOIN_MULTIENTRY)) {

                            /*
                             * (non-Javadoc)
                             * 
                             * @see
                             * com.tibco.xpd.processeditor.xpdl2.widgetimpl.
                             * adapters
                             * .refactor.ProcessRefactorConfigurationItem
                             * #getLabelDecorator()
                             */
                            @Override
                            public Image getConfigItemPreviewImage(
                                    Dimension sizeHint) {
                                return createMultiEntryPathImage(sizeHint);
                            }

                        };
            }

            if ((refactorInfo.validationInfo & RefactorAsSubProcWizardInfo.SUBPROC_MULTIPLE_EXITPATHS) != 0) {
                setupMultiExitAreaObjects();

                multiExitPathConfigItem =
                        new ProcessRefactorConfigurationItem(
                                "MultiExitPaths", //$NON-NLS-1$
                                Messages.BaseRefactorAsSubProcWizard_OnToJoinExitPath_message,
                                Messages.BaseRefactorAsSubProcWizard_OkToJoinExitPath_longdesc,
                                false,
                                true,
                                ir
                                        .get(ProcessEditorConstants.IMG_JOIN_MULTIEXIT)) {

                            /*
                             * (non-Javadoc)
                             * 
                             * @see
                             * com.tibco.xpd.processeditor.xpdl2.widgetimpl.
                             * adapters
                             * .refactor.ProcessRefactorConfigurationItem
                             * #getLabelDecorator()
                             */
                            @Override
                            public Image getConfigItemPreviewImage(
                                    Dimension sizeHint) {
                                return createMultiExitPathImage(sizeHint);
                            }

                        };

            }

            if (multiEntryPathConfigItem != null
                    || multiExitPathConfigItem != null) {
                // We'll need a list of selected objects and stuff for image
                setupSelObjsAndConns();

                ProcessRefactorConfigurationItem problemPathGroup =
                        new ProcessRefactorConfigurationItem(
                                "EntryExitPathGroup", //$NON-NLS-1$
                                Messages.BaseRefactorAsSubProcWizard_MultiEntryExitPath_label,
                                Messages.BaseRefactorAsSubProcWizard_MultiEntryExitPath_longdesc,
                                false,
                                false,
                                ir
                                        .get(ProcessEditorConstants.IMG_JOIN_MULTIENTRY));

                if (multiEntryPathConfigItem != null) {
                    problemPathGroup.addChildItem(multiEntryPathConfigItem);
                }

                if (multiExitPathConfigItem != null) {
                    problemPathGroup.addChildItem(multiExitPathConfigItem);
                }

                configItems.add(problemPathGroup);

            }
        }

        return configItems;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.
     * ProcessRefactorWizard#getPostConfigPages()
     */
    @Override
    protected List<WizardPage> getPostConfigPages(Object inputObject) {
        if (refactorInfo == null) {
            // Still in super construct, make sure we set up the the refactor
            // info in our class befire we continue.
            refactorInfo = (RefactorAsSubProcWizardInfo) inputObject;
        }

        List<WizardPage> pages = new ArrayList<WizardPage>(1);

        pages.add(new MainPage());

        return pages;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.
     * ProcessRefactorWizard#isConfigurationComplete(java.util.List)
     */
    @Override
    protected boolean isConfigurationComplete(Object inputObject,
            List<ProcessRefactorConfigurationItem> configItems) {
        if (refactorInfo == null) {
            // Still in super construct, make sure we set up the the refactor
            // info in our class befire we continue.
            refactorInfo = (RefactorAsSubProcWizardInfo) inputObject;
        }

        if (multiEntryPathConfigItem != null) {
            if (!multiEntryPathConfigItem.isChecked()) {
                return false;
            }
        }

        if (multiExitPathConfigItem != null) {
            if (!multiExitPathConfigItem.isChecked()) {
                return false;
            }
        }

        if ((refactorInfo.validationInfo & RefactorAsSubProcWizardInfo.SUBPROC_INDI_HAS_IMPLEMENTING_EVENTS) != 0) {
            return false;
        }

        return true;
    }

    /**
     * @return
     */
    private Image createMultiEntryPathImage(Dimension sizeHint) {
        Image img =
                refactorInfo.imageProvider
                        .getDiagramImageFromModel(multiEntryAreaObjects,
                                selectedObjsAndConns,
                                refactorInfo.entryPathActsAndTrans,
                                sizeHint,
                                IMG_MARGIN,
                                DiagramModelImageProvider.PAINT_UNSELECTED_OBJECTS
                                        | DiagramModelImageProvider.HIGHLIGHT_CHILDREN
                                        | DiagramModelImageProvider.MAX_SCALE_1_TO_1);

        return img;
    }

    /**
     * Get the selected objects and connections between them.
     * 
     * @return
     */
    private void setupSelObjsAndConns() {

        selectedObjsAndConns =
                Xpdl2ModelUtil
                        .getAllSelectedActivitiesAndTransitions(refactorInfo.selectedObjects,
                                true,
                                true);

    }

    /**
     * Get a list of all the objects that area selected, plus the list of entry
     * path transitions + the the source objects for those transitions.
     * 
     * These defined the area of the process diagram that we want an image of.
     * 
     */

    private void setupMultiEntryAreaObjects() {
        multiEntryAreaObjects =
                new ArrayList<EObject>(refactorInfo.selectedObjects.size());

        multiEntryAreaObjects.addAll(refactorInfo.selectedObjects);

        for (EObject obj : refactorInfo.entryPathActsAndTrans) {
            if (obj instanceof Transition) {
                Transition trans = (Transition) obj;

                multiEntryAreaObjects.add(trans);

                FlowContainer container = trans.getFlowContainer();
                if (container != null) {
                    Activity sourceAct = container.getActivity(trans.getFrom());

                    if (sourceAct != null) {
                        multiEntryAreaObjects.add(sourceAct);
                    }
                }
            }
        }
    }

    /**
     * @return
     */
    private Image createMultiExitPathImage(Dimension sizeHint) {
        Image img =
                refactorInfo.imageProvider
                        .getDiagramImageFromModel(multiExitAreaObjects,
                                selectedObjsAndConns,
                                refactorInfo.exitPathActsAndTrans,
                                sizeHint,
                                IMG_MARGIN,
                                DiagramModelImageProvider.PAINT_UNSELECTED_OBJECTS
                                        | DiagramModelImageProvider.HIGHLIGHT_CHILDREN
                                        | DiagramModelImageProvider.MAX_SCALE_1_TO_1);
        return img;
    }

    /**
     * Get a list of all the objects that area selected, plus the list of entry
     * path transitions + the the source objects for those transitions.
     * 
     * These defined the area of the process diagram that we want an image of.
     * 
     */
    private void setupMultiExitAreaObjects() {
        multiExitAreaObjects =
                new ArrayList<EObject>(refactorInfo.selectedObjects.size());

        multiExitAreaObjects.addAll(refactorInfo.selectedObjects);

        for (EObject obj : refactorInfo.exitPathActsAndTrans) {
            if (obj instanceof Transition) {
                Transition trans = (Transition) obj;

                multiExitAreaObjects.add(trans);

                FlowContainer container = trans.getFlowContainer();
                if (container != null) {
                    Activity targetAct = container.getActivity(trans.getTo());

                    if (targetAct != null) {
                        multiExitAreaObjects.add(targetAct);
                    }
                }
            }
        }
    }

    /**
     * MainPage - main page for refactor as independent sub-process.
     * 
     */
    private class MainPage extends AbstractXpdWizardPage implements ModifyListener,
            SelectionListener {
        private Composite rootControl;

        private Text nameText;

        private Button createStartEvent;

        private Button createEndEvent;

        private Button isTransaction;

        public MainPage() {
            super(""); //$NON-NLS-1$

        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt
         * .widgets.Composite)
         */
        @Override
        public void createControl(Composite parent) {
            setTitle(wizardTitle);
            setDescription(wizardDescription);

            rootControl = new Composite(parent, SWT.NONE);

            setControl(rootControl);

            GridLayout gridLayout = new GridLayout(2, false);
            gridLayout.verticalSpacing = 10;
            rootControl.setLayout(gridLayout);

            // 
            // Independent sub-process name.
            Label label = new Label(rootControl, SWT.NONE);
            label
                    .setText(Messages.BaseRefactorAsSubProcWizard_NameRefactorAsSubproc_label);
            label.setLayoutData(new GridData());

            nameText = new Text(rootControl, SWT.SINGLE | SWT.BORDER);
            nameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

            nameText.setText(refactorInfo.subprocName);
            nameText.selectAll();

            nameText.addModifyListener(this);

            Composite blanker = new Composite(rootControl, SWT.NONE);
            blanker.setLayoutData(new GridData());

            Composite btnGroup = new Composite(rootControl, SWT.NONE);
            btnGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            GridLayout btnGrpLayout = new GridLayout(1, false);
            btnGrpLayout.verticalSpacing = 10;
            btnGroup.setLayout(btnGrpLayout);

            //
            // Create is transaction button
            isTransaction = new Button(btnGroup, SWT.CHECK);
            isTransaction
                    .setText(Messages.BaseRefactorAsSubProcWizard_NewSubprocIsTransRefactorAsSubproc_action);

            isTransaction.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

            if ((refactorInfo.validationInfo & RefactorAsSubProcWizardInfo.SUBPROC_IS_TRANSACTION) != 0) {
                isTransaction.setSelection(true);
            }

            isTransaction.addSelectionListener(this);

            // 
            // Create start event button.
            createStartEvent = new Button(btnGroup, SWT.CHECK);
            createStartEvent
                    .setText(Messages.BaseRefactorAsSubProcWizard_InsertStartEventRefactorAsSubproc_action);

            createStartEvent.setLayoutData(new GridData(
                    GridData.FILL_HORIZONTAL));

            if ((refactorInfo.validationInfo & RefactorAsSubProcWizardInfo.SUBPROC_HIDE_INSERT_STARTEVENT_OPTION) != 0) {
                // Do not Show Insert Start Event Option
                refactorInfo.validationInfo &=
                        ~RefactorAsSubProcWizardInfo.SUBPROC_CREATE_STARTEVENT;
                createStartEvent.setVisible(false);

            } else if ((refactorInfo.validationInfo & RefactorAsSubProcWizardInfo.SUBPROC_EXISTING_STARTEVENT) != 0) {
                // If there is an existing start event then will never create
                // one.
                refactorInfo.validationInfo &=
                        ~RefactorAsSubProcWizardInfo.SUBPROC_CREATE_STARTEVENT;
                createStartEvent.setEnabled(false);

            } else if ((refactorInfo.validationInfo & RefactorAsSubProcWizardInfo.SUBPROC_MULTIPLE_ENTRYPATHS) != 0) {
                // If there are multiple entry paths then the user must choose
                // to
                // join paths. If this is so then we will force creation of a
                // start
                // event.
                refactorInfo.validationInfo |=
                        RefactorAsSubProcWizardInfo.SUBPROC_CREATE_STARTEVENT;
                createStartEvent.setEnabled(false);

            } else {
                createStartEvent.addSelectionListener(this);
            }

            if ((refactorInfo.validationInfo & RefactorAsSubProcWizardInfo.SUBPROC_CREATE_STARTEVENT) != 0) {
                createStartEvent.setSelection(true);
            }

            // 
            // Create end event button.
            createEndEvent = new Button(btnGroup, SWT.CHECK);
            createEndEvent
                    .setText(Messages.BaseRefactorAsSubProcWizard_InsertEndEventRefactorAsSubproc_action);

            createEndEvent
                    .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

            if ((refactorInfo.validationInfo & RefactorAsSubProcWizardInfo.SUBPROC_HIDE_INSERT_ENDEVENT_OPTION) != 0) {
                // Do not Show Insert END Event Option
                refactorInfo.validationInfo &=
                        ~RefactorAsSubProcWizardInfo.SUBPROC_CREATE_ENDEVENT;
                createEndEvent.setVisible(false);

            } else if ((refactorInfo.validationInfo & RefactorAsSubProcWizardInfo.SUBPROC_EXISTING_ENDEVENT) != 0) {
                // If there is an existing end event then will never create one.
                refactorInfo.validationInfo &=
                        ~RefactorAsSubProcWizardInfo.SUBPROC_CREATE_ENDEVENT;
                createEndEvent.setEnabled(false);

            } else if ((refactorInfo.validationInfo & RefactorAsSubProcWizardInfo.SUBPROC_MULTIPLE_EXITPATHS) != 0) {
                // If there are multiple exit paths then the user must choose to
                // join paths. If this is so then we will force creation of a
                // end
                // event.
                refactorInfo.validationInfo |=
                        RefactorAsSubProcWizardInfo.SUBPROC_CREATE_ENDEVENT;
                createEndEvent.setEnabled(false);
            } else {
                createEndEvent.addSelectionListener(this);
            }

            if ((refactorInfo.validationInfo & RefactorAsSubProcWizardInfo.SUBPROC_CREATE_ENDEVENT) != 0) {
                createEndEvent.setSelection(true);
            }

            if ((refactorInfo.validationInfo & RefactorAsSubProcWizardInfo.SUBPROC_HIDE_SUBPROC_IS_TRANSACTION_OPTION) != 0) {
                // Do not Show Insert SubProcess is Transaction Event Option
                refactorInfo.validationInfo &=
                        ~RefactorAsSubProcWizardInfo.SUBPROC_IS_TRANSACTION;
                isTransaction.setVisible(false);

            }

            //
            // Disable finish until first made visible
            setPageComplete(false);

        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
         */
        @Override
        public void setVisible(boolean visible) {
            // Only allow finish when page is first made visible.
            if (visible) {
                setPageComplete(true);
            }

            super.setVisible(visible);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.
         * events.ModifyEvent)
         */
        @Override
        public void modifyText(ModifyEvent e) {
            if (e.widget == nameText) {
                refactorInfo.subprocName = nameText.getText();

                if (refactorInfo.subprocName.length() > 0) {
                    setPageComplete(true);
                } else {
                    setPageComplete(false);
                }
            }

        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org
         * .eclipse.swt.events.SelectionEvent)
         */
        @Override
        public void widgetDefaultSelected(SelectionEvent e) {
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse
         * .swt.events.SelectionEvent)
         */
        @Override
        public void widgetSelected(SelectionEvent e) {
            if (e.widget == createStartEvent) {
                if (createStartEvent.getSelection()) {
                    refactorInfo.validationInfo |=
                            RefactorAsSubProcWizardInfo.SUBPROC_CREATE_STARTEVENT;
                } else {
                    refactorInfo.validationInfo &=
                            ~RefactorAsSubProcWizardInfo.SUBPROC_CREATE_STARTEVENT;
                }

            } else if (e.widget == createEndEvent) {
                if (createEndEvent.getSelection()) {
                    refactorInfo.validationInfo |=
                            RefactorAsSubProcWizardInfo.SUBPROC_CREATE_ENDEVENT;
                } else {
                    refactorInfo.validationInfo &=
                            ~RefactorAsSubProcWizardInfo.SUBPROC_CREATE_ENDEVENT;
                }
            } else if (e.widget == isTransaction) {
                if (isTransaction.getSelection()) {
                    refactorInfo.validationInfo |=
                            RefactorAsSubProcWizardInfo.SUBPROC_IS_TRANSACTION;
                } else {
                    refactorInfo.validationInfo &=
                            ~RefactorAsSubProcWizardInfo.SUBPROC_IS_TRANSACTION;
                }
            }

        }

    }

}
