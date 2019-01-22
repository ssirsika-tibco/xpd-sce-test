/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.dialogs.SelectionStatusDialog;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.implementer.nativeservices.java.JavaActivator;
import com.tibco.xpd.implementer.nativeservices.java.internal.Messages;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ClassType;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EAIJava;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EaijavaFactory;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EaijavaPackage;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.MethodType;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ParameterType;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ParametersType;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.utils.EAIJavaModelUtil;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.utils.JavaModelUtil;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.SetProjectReferenceCommand;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.util.MessageDialogUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * The Java service section of the task service properties.
 * 
 * @author njpatel
 */
public class JavaServiceSection extends AbstractFilteredTransactionalSection {

    private static final int WIDTH_HINT = 20;

    private Text projectText;

    private final PojoSection pojoSection;

    private final FactorySection factorySection;

    private Logger log;

    /**
     * @param eClass
     */
    public JavaServiceSection() {
        super(null);
        log = JavaActivator.getDefault().getLogger();

        pojoSection = new PojoSection();
        factorySection = new FactorySection();
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        GridLayout gLayout;
        GridData data;

        Composite root = toolkit.createComposite(parent);

        gLayout = new GridLayout(2, false);
        gLayout.marginHeight = 0;
        gLayout.marginWidth = 0;
        root.setLayout(gLayout);

        createLabel(toolkit, root, Messages.JavaServiceSection_Project);
        projectText = createReadOnlyText(toolkit, root);

        /*
         * Java class
         */
        Composite pojoComposite = pojoSection.createControls(toolkit, root);
        data = new GridData(GridData.FILL_HORIZONTAL);
        data.horizontalSpan = 2;
        pojoComposite.setLayoutData(data);

        /*
         * Separator
         */
        Label separator = toolkit.createSeparator(root, SWT.HORIZONTAL);
        data = new GridData(GridData.FILL_HORIZONTAL);
        data.horizontalSpan = 2;
        data.heightHint = 5;
        separator.setLayoutData(data);

        /*
         * Factory class
         */
        Label msg =
                createLabel(toolkit,
                        root,
                        Messages.JavaServiceSection_FactorySelect);
        data = new GridData(GridData.FILL_HORIZONTAL);
        data.horizontalSpan = 2;
        msg.setLayoutData(data);

        Composite factComposite = factorySection.createControls(toolkit, root);
        data = new GridData(GridData.FILL_HORIZONTAL);
        data.horizontalSpan = 2;
        factComposite.setLayoutData(data);

        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        EditingDomain ed = getEditingDomain();
        EAIJava eai = getEAIJava();
        CompoundCommand cmd = new CompoundCommand();

        if (ed != null) {
            // If eai object hasn't been found then create it
            if (eai == null) {
                eai =
                        EAIJavaModelUtil.createEAIJavaObj(ed,
                                cmd,
                                getTaskServiceInput());
            }

            if (eai != null) {
                // Update pojo section, if that does not process then pass to
                // factory
                try {
                    IType selectedType = null;
                    if (!pojoSection.getCommand(ed, eai, cmd, obj)) {
                        if (factorySection.getCommand(ed, eai, cmd, obj)) {
                            selectedType = factorySection.getSelectedClass();
                        }
                    } else {
                        selectedType = pojoSection.getSelectedClass();
                    }

                    // Set the project reference if required
                    if (selectedType != null
                            && selectedType.getJavaProject() != null) {
                        IProject refProject =
                                selectedType.getJavaProject().getProject();
                        IProject project = getProject();

                        if (project != null && refProject != null
                                && refProject.exists()) {
                            boolean alreadyReferenced = false;
                            // If the project is not already referenced then add
                            // the reference
                            for (IProject ref : project.getReferencedProjects()) {
                                if (alreadyReferenced = ref.equals(refProject)) {
                                    break;
                                }
                            }
                            if (!alreadyReferenced) {
                                if (canSetProjectReference(getSite().getShell(),
                                        String
                                                .format(Messages.JavaServiceSection_projectReference_dialog_message,
                                                        refProject.getName()))) {
                                    cmd.append(new SetProjectReferenceCommand(
                                            project, refProject));
                                } else {
                                    /*
                                     * User didn't want the project reference to
                                     * be set so cannot set the java
                                     * class/factory
                                     */
                                    return null;
                                }
                            }
                        }
                    }
                } catch (JavaModelException e) {
                    JavaActivator.getDefault().getLogger().error(e);
                } catch (CoreException e) {
                    JavaActivator.getDefault().getLogger().error(e);
                }
            }
        }

        return cmd;
    }

    @Override
    protected void doRefresh() {
        getSite().getShell().getDisplay().asyncExec(new Runnable() {
            public void run() {
                TaskService taskService = getTaskServiceInput();
                EAIJava eai = EAIJavaModelUtil.getEAIJavaObj(taskService);

                // Update all sections
                try {
                    pojoSection.refresh(eai);
                    factorySection.refresh(eai);
                } catch (JavaModelException e) {
                    JavaActivator.getDefault().getLogger().error(e,
                            Messages.JavaServiceSection_exception_message);
                }
                // Update the project
                updateText(projectText, eai == null ? "" : eai.getProject()); //$NON-NLS-1$
            }
        });
    }

    /**
     * Ask user if the project reference can be set. Dialog asking user will be
     * shown if the user did not ask to set preference without asking.
     * 
     * @param shell
     *            <code>Shell</code>
     * @param message
     *            message to show in the dialog
     * @return <code>true</code> if project reference can be set,
     *         <code>false</code> otherwise.
     */
    private boolean canSetProjectReference(Shell shell, String msg) {
        boolean setReference = true;
        IPreferenceStore store =
                Xpdl2ResourcesPlugin.getDefault().getPreferenceStore();
        if (store != null) {
            boolean canShowDialog =
                    !store
                            .getBoolean(Xpdl2ResourcesConsts.PREF_DONT_ASK_AGAIN_FOR_PROJECT_REFERENCE);

            if (canShowDialog) {
                int ret =
                        MessageDialogUtil
                                .openYesNoQuestion(shell,
                                        Messages.JavaServiceSection_projectReference_dialog_title,
                                        msg,
                                        Messages.JavaServiceSection_setReferenceWithoutAsking_check_longdesc,
                                        false,
                                        Xpdl2ResourcesConsts.PREF_DONT_ASK_AGAIN_FOR_PROJECT_REFERENCE,
                                        store);
                setReference = ret == IDialogConstants.YES_ID;
            }
        }

        return setReference;
    }

    /**
     * Get the <code>EAIJava</code> object.
     * 
     * @return
     */
    private EAIJava getEAIJava() {
        TaskService taskService = getTaskServiceInput();
        EAIJava eai = null;

        if (taskService != null) {
            eai = EAIJavaModelUtil.getEAIJavaObj(taskService);
        }

        return eai;
    }

    /**
     * Get the <code>TaskService</code> input
     * 
     * @return
     */
    private TaskService getTaskServiceInput() {
        TaskService service = null;
        EObject input = getInput();

        if (input != null && input instanceof Activity) {
            Activity activity = (Activity) input;
            // To be checked for task type before casting it, as it can be a
            // SubFlow.
            if (activity.getImplementation() instanceof Task) {
                Task task = (Task) activity.getImplementation();

                if (task != null) {
                    service = task.getTaskService();
                }
            }
        }
        return service;
    }

    @Override
    public void setInput(Collection items) {
        super.setInput(items);

        // (Nick 13/8/2008)
        // Code below commented out. We're already in the postcommit
        // of one command so we can't execute another command.

        // // Set the eaijava extended model in the TaskService if one doesn't
        // // already exists
        // TaskService taskService = getTaskServiceInput();
        //
        // if (taskService != null) {
        // EAIJava eai = EAIJavaModelUtil.getEAIJavaObj(taskService);
        //
        // if (eai == null) {
        // EditingDomain ed = getEditingDomain();
        //
        // if (ed != null) {
        // CompoundCommand cmd = new CompoundCommand();
        // EAIJavaModelUtil.createEAIJavaObj(ed, cmd, taskService);
        //
        // // Don't execute the command in the editing domain's command
        // // stack as this should be undo-able
        // // (Nick 1/8/2008) We have to execute it against the command
        // // stack now as it has to be in a transaction. Maybe this
        // // should be done elsewhere?
        // if (cmd.canExecute()) {
        // ed.getCommandStack().execute(cmd);
        // //cmd.execute();
        // }
        // }
        // }
        // }
    }

    @Override
    protected boolean updateCCombo(CCombo combo, String selectedString) {
        boolean ret = super.updateCCombo(combo, selectedString);

        if (ret) {
            combo.setSelection(new Point(0, 0));
            combo.setToolTipText(selectedString);
        }

        return ret;
    }

    /**
     * Create a <code>Label</code> with the given <i>toolkit</i> and set to
     * standard label width.
     * 
     * @param toolkit
     * @param parent
     * @param title
     * @return
     */
    private Label createLabel(XpdFormToolkit toolkit, Composite parent,
            String title) {
        Label lbl = toolkit.createLabel(parent, title, SWT.WRAP);
        GridData gData = new GridData();
        gData.widthHint = STANDARD_LABEL_WIDTH;
        lbl.setLayoutData(gData);

        return lbl;
    }

    /**
     * Create a <code>Text</code> control using the <i>toolkit</i> with a set
     * width hint and filling the horizontal space available in the layout.
     * 
     * @param toolkit
     * @param parent
     * @param style
     * @return
     */
    private Text createReadOnlyText(XpdFormToolkit toolkit, Composite parent) {
        Text text = toolkit.createText(parent, "", SWT.READ_ONLY); //$NON-NLS-1$
        GridData gData = new GridData(GridData.FILL_HORIZONTAL);
        gData.widthHint = WIDTH_HINT;
        text.setLayoutData(gData);

        return text;
    }

    /**
     * Create a <code>CCombo</code> control using the <i>toolkit</i> with a set
     * width hint and filling the horizontal space available.
     * 
     * @param toolkit
     * @param parent
     * @return
     */
    private CCombo createCCombo(XpdFormToolkit toolkit, Composite parent) {
        CCombo combo = toolkit.createCCombo(parent);
        GridData gData = new GridData(GridData.FILL_HORIZONTAL);
        gData.widthHint = WIDTH_HINT;
        combo.setLayoutData(gData);

        return combo;
    }

    private class ClassSection implements SelectionListener {
        private final EReference modelRef;

        protected IType selectedClass;

        protected Text classText;

        protected CCombo methodCmb;

        protected Button selectClassBtn;

        protected Button clearClassBtn;

        protected Map<String, IMethod> methodMap =
                new HashMap<String, IMethod>();

        private IMethod currentSelectedMethod;

        private String currentReturnType;

        public ClassSection(EReference modelRef) {
            this.modelRef = modelRef;

        }

        public IType getSelectedClass() {
            return selectedClass;
        }

        public Composite createControls(XpdFormToolkit toolkit, Composite parent) {
            Composite container = null;

            if (toolkit != null && parent != null) {
                container = toolkit.createComposite(parent);
                GridLayout layout = new GridLayout(2, false);
                layout.marginWidth = 0;
                layout.marginHeight = 0;
                container.setLayout(layout);

                createLabel(toolkit,
                        container,
                        Messages.JavaServiceSection_Class);
                classText = createReadOnlyText(toolkit, container);
                createLabel(toolkit,
                        container,
                        Messages.JavaServiceSection_Method);
                methodCmb = createCCombo(toolkit, container);
                methodCmb.addSelectionListener(this);

                Control btnControl = createButtonControls(toolkit, container);
                if (btnControl != null) {
                    GridData data = new GridData(GridData.HORIZONTAL_ALIGN_END);
                    data.horizontalSpan = 2;
                    btnControl.setLayoutData(data);
                }
            }
            return container;
        }

        protected Control createButtonControls(XpdFormToolkit toolkit,
                Composite parent) {

            if (toolkit != null && parent != null) {
                Composite container = toolkit.createComposite(parent);
                GridLayout layout = new GridLayout(2, false);
                layout.marginHeight = 0;
                layout.marginWidth = 0;
                container.setLayout(layout);

                clearClassBtn =
                        toolkit.createButton(container,
                                Messages.JavaServiceSection_clearClass_button,
                                SWT.NONE);
                manageControl(clearClassBtn);

                selectClassBtn =
                        toolkit.createButton(container,
                                Messages.JavaServiceSection_ClassSelect,
                                SWT.NONE);
                manageControl(selectClassBtn);

                return container;
            }
            return null;
        }

        /**
         * Refresh the section.
         * 
         * @param eai
         * @throws JavaModelException
         */
        public void refresh(EAIJava eai) throws JavaModelException {

            try {
                boolean clearAll = false;
                if (eai == null) {
                    clearAll = true;
                } else if (classText != null && !classText.isDisposed()) {
                    Color foreClr = classText.getForeground();
                    // Disable buttons
                    enableButtons(false);

                    ClassType classModel = (ClassType) eai.eGet(modelRef);

                    if (classModel != null) {
                        String projectName = eai.getProject();
                        String className = classModel.getName();

                        if (projectName != null && className != null) {

                            /*
                             * If there is no selected IType, or is not current
                             * then load it
                             */
                            if (selectedClass == null
                                    || !(selectedClass.getJavaProject()
                                            .getElementName()
                                            .equals(projectName) && selectedClass
                                            .getFullyQualifiedName()
                                            .equals(className))) {

                                classText.setForeground(ColorConstants.gray);
                                classText
                                        .setText(Messages.JavaServiceSection_LoadingClass);
                                classText.update();

                                // Load the IType from JDT
                                selectedClass =
                                        EAIJavaModelUtil
                                                .loadClassFromModel(eai,
                                                        modelRef);
                            }

                            if (classText != null) {
                                // Check incase widget is disposed
                                if (classText != null
                                        && !classText.isDisposed()) {
                                    classText.setForeground(foreClr);

                                    if (selectedClass != null) {
                                        updateText(projectText, selectedClass
                                                .getJavaProject()
                                                .getElementName());
                                        updateText(classText, selectedClass
                                                .getFullyQualifiedName());

                                        /*
                                         * Update the method map and combo
                                         */
                                        methodCmb.removeAll();
                                        methodMap.clear();
                                        List<IMethod> methods =
                                                getMethods(selectedClass);

                                        if (methods != null
                                                && !methods.isEmpty()) {
                                            // Update the combo and map
                                            for (IMethod method : methods) {
                                                String sig =
                                                        getMethodSignature(method);
                                                methodCmb.add(sig);
                                                methodMap.put(sig, method);
                                            }
                                        }
                                        /*
                                         * If a method has been set then select
                                         * it
                                         */
                                        if (classModel.getMethod() != null) {
                                            MethodType methodType =
                                                    classModel.getMethod();

                                            IMethod method =
                                                    selectedClass
                                                            .getMethod(methodType
                                                                    .getName(),
                                                                    EAIJavaModelUtil
                                                                            .getMethodParameterSignatures(methodType));

                                            if (method != null) {
                                                /*
                                                 * XPD-1396: get the method's
                                                 * return type from model. get
                                                 * the method's return type from
                                                 * pojo project. if they are
                                                 * same, only then update the
                                                 * combo
                                                 */
                                                ParameterType modelParamType =
                                                        classModel.getMethod()
                                                                .getReturn();
                                                String retType =
                                                        modelParamType
                                                                .getSignature();
                                                String modelReturnType =
                                                        Signature
                                                                .getSignatureSimpleName(retType);
                                                String pojoType =
                                                        method.getReturnType();
                                                String pojoMethodReturnType =
                                                        Signature
                                                                .getSignatureSimpleName(pojoType);
                                                if (modelReturnType
                                                        .equalsIgnoreCase(pojoMethodReturnType)) {
                                                    updateCCombo(methodCmb,
                                                            getMethodSignature(method));
                                                    currentSelectedMethod =
                                                            method;
                                                    currentReturnType =
                                                            pojoType;
                                                }
                                            }
                                        }
                                    } else {
                                        // The class set in the model is not
                                        // found so update the UI from the
                                        // model
                                        updateText(classText, className);
                                        // Don't use updateCombo as this
                                        // value
                                        // does not exist in the method
                                        // combo
                                        methodCmb
                                                .setText(getMethodSignature(classModel
                                                        .getMethod()));
                                    }
                                } else {
                                    // Clear loading class message
                                    updateText(classText, null);
                                }
                            } else {
                                clearAll = true;
                            }
                        }
                    } else {
                        clearAll = true;
                    }
                }

                if (clearAll && !classText.isDisposed()) {
                    // Clear the cached
                    selectedClass = null;
                    // Clear all inputs
                    updateText(classText, null);
                    methodCmb.removeAll();
                    methodMap.clear();
                }
                if (clearClassBtn != null && !clearClassBtn.isDisposed()) {
                    clearClassBtn.setEnabled(!clearAll);
                }
            } catch (JavaModelException e) {
                // Ignore
            } finally {

                // Enable the buttons
                enableButtons(true);
            }
        }

        public boolean getCommand(EditingDomain ed, EAIJava eai,
                CompoundCommand cmd, Object source) throws JavaModelException {
            boolean ret = false;

            if (source.equals(selectClassBtn)) {
                SelectionStatusDialog dlg =
                        getClassSelectionDialog(getSite().getShell());

                if (dlg != null && dlg.open() == SelectionStatusDialog.OK) {
                    Object result = dlg.getFirstResult();

                    if (result instanceof IType) {
                        IType type = (IType) result;

                        if (!type.equals(selectedClass)) {
                            selectedClass = type;
                            // Update the model with the new selected class type
                            updateClassType(ed, eai, cmd, selectedClass);
                        }
                    }
                }

                ret = true;
            } else if (source.equals(methodCmb)) {
                if (methodMap != null) {
                    IMethod method = methodMap.get(methodCmb.getText());

                    if (method != null) {
                        /*
                         * XPD-1396: check for current pojo return type. if it
                         * doesnt match with model return type, then update the
                         * model
                         */
                        if (!method.equals(currentSelectedMethod)
                                || !method.getReturnType()
                                        .equals(currentReturnType)) {
                            updateMethodType(ed, eai, cmd, method);
                            currentSelectedMethod = method;
                            currentReturnType = method.getReturnType();
                        }
                    }
                }
                ret = true;
            } else if (source.equals(clearClassBtn)) {
                if (eai.getPojo() != null) {
                    // Clear the java class
                    cmd.append(SetCommand.create(ed,
                            eai,
                            EaijavaPackage.eINSTANCE.getEAIJava_Pojo(),
                            SetCommand.UNSET_VALUE));
                    // Clear the project
                    if (eai.getProject() != null) {
                        cmd.append(SetCommand.create(ed,
                                eai,
                                EaijavaPackage.eINSTANCE.getEAIJava_Project(),
                                SetCommand.UNSET_VALUE));
                    }
                    // Clear the factory
                    if (eai.getFactory() != null) {
                        cmd.append(SetCommand.create(ed,
                                eai,
                                EaijavaPackage.eINSTANCE.getEAIJava_Factory(),
                                SetCommand.UNSET_VALUE));
                    }
                }
            }

            return ret;
        }

        /**
         * Update the model with the selected method. Default implementation
         * does nothing, subclasses should override to set the method.
         * 
         * @param ed
         * @param eai
         * @param cmd
         * @param method
         * @throws JavaModelException
         */
        protected void updateMethodType(EditingDomain ed, EAIJava eai,
                CompoundCommand cmd, IMethod method) throws JavaModelException {
            // Do nothing. Subclass should extend to set the method selection
            return;

        }

        protected SelectionStatusDialog getClassSelectionDialog(Shell shell) {
            if (shell != null) {
                // Include classes and interfaces
                return JavaModelUtil.getClassSearchDialog(shell,
                        IJavaSearchConstants.CLASS_AND_INTERFACE,
                        null);
            }
            return null;
        }

        /**
         * Get the methods of the given class. The default implementation
         * returns <code>null</code>. Subclasses should override to return the
         * appropriate methods.
         * 
         * @param type
         * @return
         * @throws JavaModelException
         */
        protected List<IMethod> getMethods(IType type)
                throws JavaModelException {
            return null;
        }

        /**
         * Update the model with the selected class type, and also set the
         * method to the first in the list by default. Default implementation
         * does nothing, subclasses should override.
         * 
         * @param ed
         * @param eai
         * @param cmd
         * @param selectedType
         * 
         * @throws JavaModelException
         */
        protected void updateClassType(EditingDomain ed, EAIJava eai,
                CompoundCommand cmd, IType selectedType)
                throws JavaModelException {
            // Do nothing, subclass will overwrite
        }

        /**
         * Check if the the given <code>IType</code> <i>classToCheck</i>
         * implements the given interface <i>intfc</i>.
         * 
         * @param classToCheck
         * @param intfc
         * @return
         * @throws JavaModelException
         */
        protected boolean implementsInterface(IType classToCheck, IType intfc)
                throws JavaModelException {
            boolean ret = false;

            if (classToCheck != null && intfc != null) {
                if (intfc.isInterface()) {
                    String[] signatures =
                            classToCheck.getSuperInterfaceTypeSignatures();

                    if (signatures != null) {
                        IJavaProject project = classToCheck.getJavaProject();
                        for (String signature : signatures) {
                            String[][] resolvedClasses =
                                    JavaModelUtil.resolveType(classToCheck,
                                            signature);

                            if (resolvedClasses != null) {
                                for (String[] className : resolvedClasses) {
                                    IType type =
                                            project.findType(className[0],
                                                    className[1]);

                                    if (type.equals(intfc)) {
                                        ret = true;
                                        break;
                                    } else {
                                        // Check the type for it's interface
                                        // implementations
                                        if (ret =
                                                implementsInterface(type, intfc)) {
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    throw new IllegalArgumentException(
                            "intfc is not an interface."); //$NON-NLS-1$
                }
            }

            return ret;
        }

        /**
         * Enable/Disable the buttons.
         * 
         * @param enabled
         */
        protected void enableButtons(boolean enabled) {
            if (!selectClassBtn.isDisposed()) {
                selectClassBtn.setEnabled(enabled);
            }
        }

        /**
         * Get the method signature to add to the method combo from the
         * <code>MethodType</code> given.
         * 
         * @param methodType
         * @return
         */
        private String getMethodSignature(MethodType methodType) {
            String sig = ""; //$NON-NLS-1$

            if (methodType != null) {
                sig = methodType.getName() + " ("; //$NON-NLS-1$

                if (methodType.getParameters() != null) {
                    ParametersType parameters = methodType.getParameters();

                    if (parameters.getParameter() != null
                            && !parameters.getParameter().isEmpty()) {
                        EList paramList = parameters.getParameter();
                        int count = paramList.size();

                        for (int idx = 0; idx < count; idx++) {
                            ParameterType param =
                                    (ParameterType) paramList.get(idx);

                            // If this is not the first param then add separator
                            if (idx > 0) {
                                sig += ", "; //$NON-NLS-1$
                            }

                            sig +=
                                    Signature.getSignatureSimpleName(param
                                            .getSignature());
                        }
                    }
                }

                sig += ")"; //$NON-NLS-1$

                if (methodType.getReturn() != null) {
                    sig +=
                            "- " //$NON-NLS-1$
                                    + Signature
                                            .getSignatureSimpleName(methodType
                                                    .getReturn().getSignature());
                }
            }

            return sig;
        }

        /**
         * Get the method signature to add to the method combo from the
         * <code>IMethod</code> given.
         * 
         * @param method
         * @return
         * @throws JavaModelException
         */
        private String getMethodSignature(final IMethod method)
                throws JavaModelException {
            String sig = ""; //$NON-NLS-1$

            if (method != null) {
                sig = method.getElementName() + " ("; //$NON-NLS-1$

                // Add parameters to the method signature
                String[] parameterTypes = method.getParameterTypes();

                if (parameterTypes != null) {
                    int count = parameterTypes.length;

                    for (int idx = 0; idx < count; idx++) {
                        // If this is not the first parameter
                        // then add separator
                        if (idx > 0) {
                            sig += ", "; //$NON-NLS-1$
                        }

                        sig +=
                                Signature
                                        .getSignatureSimpleName(parameterTypes[idx]);
                    }
                }

                sig += ")"; //$NON-NLS-1$

                // Add return type
                sig +=
                        " - " //$NON-NLS-1$
                                + Signature.getSignatureSimpleName(method
                                        .getReturnType());
            } else {
                throw new NullPointerException("Method is null."); //$NON-NLS-1$
            }

            return sig;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org
         * .eclipse.swt.events.SelectionEvent)
         */
        public void widgetDefaultSelected(SelectionEvent e) {
            // Do nothing
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse
         * .swt.events.SelectionEvent)
         */
        public void widgetSelected(SelectionEvent e) {
            /*
             * Combo controls are not handled using manageControl as they will
             * otherwise call doGetCommand on every change to the combo,
             * including updating the list of items. Therefore, a selection
             * listener is used so that the model is only updated when a genuine
             * change of choice occurs.
             */
            EditingDomain ed = getEditingDomain();
            EAIJava eai = getEAIJava();
            CompoundCommand cmd = new CompoundCommand();
            Object source = e.getSource();

            if (ed != null && eai != null && source instanceof Widget) {
                Widget widget = (Widget) source;
                if (!widget.isDisposed()) {
                    try {
                        getCommand(ed, eai, cmd, widget);

                        if (cmd.canExecute()) {
                            ed.getCommandStack().execute(cmd);
                        }

                    } catch (JavaModelException e1) {
                        log.error(e1);
                    }
                }
            }
        }

    }

    private class PojoSection extends ClassSection {

        public PojoSection() {
            super(EaijavaPackage.eINSTANCE.getEAIJava_Pojo());
        }

        @Override
        protected void updateClassType(EditingDomain ed, EAIJava eai,
                CompoundCommand cmd, IType selectedType)
                throws JavaModelException {
            /*
             * Update pojo section. If a factory has been defined then check
             * it's methods list to see if one returns an object of this class
             * type. If not then clear the factory section.
             */
            if (ed != null && eai != null && cmd != null
                    && selectedType != null) {
                // Update the project
                cmd.append(SetCommand.create(ed, eai, EaijavaPackage.eINSTANCE
                        .getEAIJava_Project(), selectedType.getJavaProject()
                        .getElementName()));

                setClassType(ed, eai, cmd, selectedType);

                IType factoryType = null;

                if (eai.getFactory() != null) {
                    factoryType = factorySection.getSelectedClass();
                }

                // If a factory is selected then check the methods list
                if (factoryType != null && eai.getFactory() != null) {
                    ClassType factory = eai.getFactory();

                    IMethod method =
                            factorySection.getMethodWithReturnType(factoryType,
                                    selectedType);

                    if (method != null) {
                        MethodType methodType =
                                EAIJavaModelUtil.createMethodType(factoryType,
                                        method);
                        if (methodType != null) {
                            // Update the factory method
                            cmd.append(SetCommand.create(ed,
                                    factory,
                                    EaijavaPackage.eINSTANCE
                                            .getClassType_Method(),
                                    methodType));
                        }
                    } else {
                        // Clear out the factory
                        cmd.append(SetCommand.create(ed,
                                eai,
                                EaijavaPackage.eINSTANCE.getEAIJava_Factory(),
                                SetCommand.UNSET_VALUE));
                    }
                }
            }
        }

        @Override
        protected void updateMethodType(EditingDomain ed, EAIJava eai,
                CompoundCommand cmd, IMethod method) throws JavaModelException {
            if (ed != null && eai != null && cmd != null && method != null) {
                // Update the model with the method
                MethodType methodType =
                        EAIJavaModelUtil
                                .createMethodType(selectedClass, method);

                if (methodType != null) {
                    cmd.append(SetCommand.create(ed,
                            eai.getPojo(),
                            EaijavaPackage.eINSTANCE.getClassType_Method(),
                            methodType));

                    TaskService taskService = getTaskServiceInput();

                    if (taskService != null) {

                        // Clear all input mappings
                        if (taskService.getMessageIn() != null
                                && taskService.getMessageIn().getDataMappings() != null) {

                            cmd.append(RemoveCommand.create(ed, taskService
                                    .getMessageIn(), Xpdl2Package.eINSTANCE
                                    .getMessage_DataMappings(), taskService
                                    .getMessageIn().getDataMappings()));

                            // cmd.append(SetCommand.create(ed, taskService
                            // .getMessageIn().getDataMappings(),
                            // Xpdl2Package.eINSTANCE
                            // .getMessage_DataMappings(),
                            // SetCommand.UNSET_VALUE));
                        }
                        // Clear all output mappings
                        if (taskService.getMessageOut() != null
                                && taskService.getMessageOut()
                                        .getDataMappings() != null) {

                            cmd.append(RemoveCommand.create(ed, taskService
                                    .getMessageOut(), Xpdl2Package.eINSTANCE
                                    .getMessage_DataMappings(), taskService
                                    .getMessageOut().getDataMappings()));

                        }

                        EObject object = taskService.eContainer();

                        if (object instanceof Task) {
                            Task task = (Task) object;

                            if (task.getActivity() != null) {
                                // Clear assignments (output mappings)
                                cmd.append(SetCommand.create(ed,
                                        task.getActivity(),
                                        Xpdl2Package.eINSTANCE
                                                .getActivity_Assignments(),
                                        SetCommand.UNSET_VALUE));
                            }
                        }
                    }
                }
            }
        }

        /**
         * Update the model with the selected class type.
         * 
         * @param ed
         * @param eai
         * @param cmd
         * @param selectedType
         * @throws JavaModelException
         */
        protected void setClassType(EditingDomain ed, EAIJava eai,
                CompoundCommand cmd, IType selectedType)
                throws JavaModelException {
            // Update the pojo
            ClassType pojo = EaijavaFactory.eINSTANCE.createClassType();
            pojo.setName(selectedType.getFullyQualifiedName());

            // Get the methods and set the first as the selected method
            List<IMethod> methods = getMethods(selectedType);

            if (methods != null && !methods.isEmpty()) {
                pojo.setMethod(EAIJavaModelUtil.createMethodType(selectedType,
                        methods.get(0)));
            }

            // Set the pojo
            cmd.append(SetCommand.create(ed, eai, EaijavaPackage.eINSTANCE
                    .getEAIJava_Pojo(), pojo));
        }

        /**
         * Get the public methods of the given <i>type</i>.
         * 
         * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.JavaServiceSection.ClassSection#getMethods(org.eclipse.jdt.core.IType)
         */
        protected List<IMethod> getMethods(IType type)
                throws JavaModelException {
            List<IMethod> methods = null;

            if (type != null) {
                methods = JavaModelUtil.getMethods(type, Flags.AccPublic);
            }

            return methods;
        }

    }

    private class FactorySection extends ClassSection {

        private Button clearSelectionBtn;

        public FactorySection() {
            super(EaijavaPackage.eINSTANCE.getEAIJava_Factory());
        }

        @Override
        public void refresh(EAIJava eai) throws JavaModelException {
            super.refresh(eai);

            if (clearSelectionBtn != null && !clearSelectionBtn.isDisposed()) {
                clearSelectionBtn.setEnabled(eai != null
                        && eai.getFactory() != null);
            }
        }

        /**
         * Get the method with the given return type from the currently selected
         * factory.
         * 
         * @param classTypeToCheck
         *            Check the methods of this class
         * @param retType
         * @return <code>IMethod</code> that returns the given return type. If
         *         no factory is selected or no method returns the return type
         *         then <code>null</code> will be returned.
         * @throws JavaModelException
         */
        public IMethod getMethodWithReturnType(IType classTypeToCheck,
                IType retType) throws JavaModelException {
            IMethod ret = null;

            if (classTypeToCheck != null && retType != null) {
                List<IMethod> methods = getMethods(classTypeToCheck);
                IJavaProject javaProject = classTypeToCheck.getJavaProject();

                if (methods != null) {
                    for (IMethod method : methods) {
                        String returnType = method.getReturnType();

                        if (returnType != null
                                && Signature.getTypeSignatureKind(returnType) == Signature.CLASS_TYPE_SIGNATURE) {
                            String[][] resolvedClasses =
                                    JavaModelUtil.resolveType(classTypeToCheck,
                                            returnType);

                            if (resolvedClasses != null) {
                                for (String[] className : resolvedClasses) {
                                    IType type =
                                            javaProject.findType(className[0],
                                                    className[1]);

                                    if ((retType.isInterface() && implementsInterface(type,
                                            retType))
                                            || type.equals(retType)) {
                                        ret = method;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return ret;
        }

        @Override
        public boolean getCommand(EditingDomain ed, EAIJava eai,
                CompoundCommand cmd, Object source) throws JavaModelException {

            boolean ret = super.getCommand(ed, eai, cmd, source);

            if (!ret && ed != null && eai != null && cmd != null) {
                // Clear factory
                if (clearSelectionBtn.equals(source)) {
                    // Clear the factory
                    cmd.append(SetCommand.create(ed,
                            eai,
                            EaijavaPackage.eINSTANCE.getEAIJava_Factory(),
                            SetCommand.UNSET_VALUE));

                    // If no pojo is defined then clear the factory too
                    if (eai.getPojo() == null) {
                        cmd.append(SetCommand.create(ed,
                                eai,
                                EaijavaPackage.eINSTANCE.getEAIJava_Project(),
                                SetCommand.UNSET_VALUE));
                    }

                    ret = true;
                }
            }

            return ret;
        }

        @Override
        protected void updateClassType(EditingDomain ed, EAIJava eai,
                CompoundCommand cmd, IType selectedType)
                throws JavaModelException {

            /*
             * Update the factory section. If a pojo class has been selected
             * then check if a method exists in this factory to create this
             * pojo. If it does then select the class, otherwise select the
             * first method and set the pojo to the return type of this method.
             */
            if (ed != null && eai != null && cmd != null
                    && selectedType != null) {
                // Update the project
                cmd.append(SetCommand.create(ed, eai, EaijavaPackage.eINSTANCE
                        .getEAIJava_Project(), selectedType.getJavaProject()
                        .getElementName()));

                // Create the factory class
                ClassType factoryType =
                        EaijavaFactory.eINSTANCE.createClassType();
                factoryType.setName(selectedType.getFullyQualifiedName());

                // Update the method
                IMethod method =
                        getMethodWithReturnType(selectedType, pojoSection
                                .getSelectedClass());

                /*
                 * If no method found, or pojo not set then select the first
                 * method of this factory and update the pojo
                 */
                if (method == null) {
                    List<IMethod> methods = getMethods(selectedType);

                    if (methods != null && !methods.isEmpty()) {
                        method = methods.get(0);
                        IJavaProject project = selectedType.getJavaProject();

                        // Update pojo
                        // Resolve the return type
                        String[][] resolvedClasses =
                                JavaModelUtil.resolveType(selectedType, method
                                        .getReturnType());

                        if (resolvedClasses != null
                                && resolvedClasses.length > 0) {
                            IType pojo = null;

                            for (String[] classNames : resolvedClasses) {
                                pojo =
                                        project.findType(classNames[0],
                                                classNames[1]);

                                if (pojo != null) {
                                    break;
                                }
                            }

                            if (pojo != null) {
                                // Update the pojo class
                                pojoSection.setClassType(ed, eai, cmd, pojo);
                            }
                        }
                    } else {
                        // If there is a pojo selected then clear it
                        if (eai.getPojo() != null) {
                            cmd.append(SetCommand.create(ed,
                                    eai,
                                    EaijavaPackage.eINSTANCE.getEAIJava_Pojo(),
                                    SetCommand.UNSET_VALUE));
                        }
                    }
                }

                if (method != null) {
                    // Update the method in the model
                    factoryType.setMethod(EAIJavaModelUtil
                            .createMethodType(selectedType, method));
                }

                // Update the model
                cmd.append(SetCommand.create(ed, eai, EaijavaPackage.eINSTANCE
                        .getEAIJava_Factory(), factoryType));
            }
        }

        /*
         * 1. update method 2. if method return type = selected pojo, do nothing
         * 3. if method return type != selected pojo, update pojo 4. If method
         * return type unknown, clear pojo
         */

        @Override
        protected void updateMethodType(EditingDomain ed, EAIJava eai,
                CompoundCommand cmd, IMethod method) throws JavaModelException {
            if (ed != null && eai != null && cmd != null && method != null) {
                // Update the factory method type
                MethodType methodType =
                        EAIJavaModelUtil
                                .createMethodType(selectedClass, method);

                if (methodType != null) {
                    cmd.append(SetCommand.create(ed,
                            eai.getFactory(),
                            EaijavaPackage.eINSTANCE.getClassType_Method(),
                            methodType));
                }

                // If the selected class is not of the type returned by the
                // selected method then clear it
                IType pojoClass = null;

                if (eai.getPojo() != null) {
                    pojoClass = pojoSection.getSelectedClass();
                }
                boolean pojoAlreadySelected = false;
                // Get the return type of the method
                String[][] resolvedClasses =
                        JavaModelUtil.resolveType(selectedClass, method
                                .getReturnType());
                IJavaProject project = selectedClass.getJavaProject();

                if (resolvedClasses != null || resolvedClasses.length == 0) {
                    List<IType> resolvedClassesList = new ArrayList<IType>();

                    if (resolvedClasses != null) {
                        for (String[] className : resolvedClasses) {
                            IType type =
                                    project
                                            .findType(className[0],
                                                    className[1]);
                            resolvedClassesList.add(type);

                            /*
                             * If no pojo has been specified then quit here -
                             * this way we have the first resolved class which
                             * will be used to set the pojo
                             */
                            if (pojoClass == null) {
                                break;
                            }

                            if ((pojoClass.isInterface() && implementsInterface(type,
                                    pojoClass))
                                    || type.equals(pojoClass)) {
                                pojoAlreadySelected = true;
                                break;
                            }
                        }
                    }

                    if (!pojoAlreadySelected && !resolvedClassesList.isEmpty()) {
                        /*
                         * Update pojo to be the return type of the selected
                         * factory method
                         */
                        pojoSection.setClassType(ed,
                                eai,
                                cmd,
                                resolvedClassesList.get(0));
                    }
                } else {
                    // If pojo class selected then remove it
                    if (pojoSection.getSelectedClass() != null) {
                        cmd.append(SetCommand.create(ed,
                                eai,
                                EaijavaPackage.eINSTANCE.getEAIJava_Pojo(),
                                SetCommand.UNSET_VALUE));
                    }
                }
            }
        }

        @Override
        protected Control createButtonControls(XpdFormToolkit toolkit,
                Composite parent) {
            Composite container = null;

            if (toolkit != null && parent != null) {
                container = toolkit.createComposite(parent);
                GridLayout layout = new GridLayout(2, true);
                layout.marginHeight = 0;
                layout.marginWidth = 0;
                container.setLayout(layout);

                clearSelectionBtn =
                        toolkit.createButton(container,
                                Messages.JavaServiceSection_ClearFactory,
                                SWT.NONE);
                manageControl(clearSelectionBtn);
                selectClassBtn =
                        toolkit.createButton(container,
                                Messages.JavaServiceSection_SelectFactory,
                                SWT.NONE);
                manageControl(selectClassBtn);
            }

            return container;
        }

        @Override
        protected void enableButtons(boolean enabled) {
            if (!clearSelectionBtn.isDisposed()) {
                clearSelectionBtn.setEnabled(enabled);
            }

            super.enableButtons(enabled);
        }

        /**
         * Get the public static methods that have no parameters of the given
         * <i>type</i>.
         * 
         * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.JavaServiceSection.ClassSection#getMethods(org.eclipse.jdt.core.IType)
         */
        protected List<IMethod> getMethods(IType type)
                throws JavaModelException {
            List<IMethod> methods = null;

            if (type != null) {
                methods =
                        JavaModelUtil.getMethods(type, Flags.AccPublic
                                | Flags.AccStatic);

                // Remove any methods that have parameters
                if (methods != null) {
                    for (Iterator<IMethod> iter = methods.iterator(); iter
                            .hasNext();) {
                        if (iter.next().getNumberOfParameters() != 0) {
                            iter.remove();
                        }
                    }
                }
            }

            return methods;
        }
    }
}
