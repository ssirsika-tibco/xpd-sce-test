/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.core.createtestwizards.classapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.Bundle;

import com.tibco.xpd.core.createtestwizards.CreateTestWizardsConstants;
import com.tibco.xpd.core.createtestwizards.CreateTestWizardsPlugin;
import com.tibco.xpd.core.test.util.classapi.AbstractApiClassTest;
import com.tibco.xpd.ui.properties.XpdWizardToolkit;

/**
 * CreateClassApiTestPage
 * 
 * 
 * @author aallway
 * @since 3.3 (14 Oct 2009)
 */
public class CreateClassApiTestPage extends WizardPage {

    private Text cApiClassParentPlugin;

    private Text cApiClassName;

    private TreeViewer cApiClassList;

    private String apiClassParentPluginId = "";

    private Bundle apiClassBundle = null;

    private Object selectedApiClass = null;

    private CLabel cApiBundleLabel;

    private CLabel cApiClassLabel;

    private Composite root;

    /**
     * @param pageName
     */
    protected CreateClassApiTestPage() {
        super("Select Class");

        super.setDescription("Select the class you wish to test the API of.");
    }

    /**
     * @return the apiClassParentPluginId
     */
    public String getApiClassParentPluginId() {
        return apiClassParentPluginId;
    }

    /**
     * @return the selectedClassOrPackage
     */
    public Object getSelectedClassOrPackage() {
        return selectedApiClass;
    }

    public void createControl(Composite parent) {
        XpdWizardToolkit toolkit = new XpdWizardToolkit(parent);

        root = toolkit.createComposite(parent, SWT.NONE);
        root.setLayout(new GridLayout(2, false));

        cApiBundleLabel =
                toolkit.createCLabel(root, "API Class Plug-in Id: ", SWT.NONE);
        cApiBundleLabel.setLayoutData(new GridData());

        cApiClassParentPlugin = toolkit.createText(root, "", SWT.NONE);
        cApiClassParentPlugin.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));

        cApiClassLabel =
                toolkit.createCLabel(root,
                        "Class (fully qualified): ",
                        SWT.NONE);
        cApiClassLabel.setLayoutData(new GridData());

        cApiClassName = toolkit.createText(root, "", SWT.NONE);
        cApiClassName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Composite filler = toolkit.createComposite(root, SWT.NONE);
        GridData gd = new GridData();
        gd.heightHint = 1;
        filler.setLayoutData(gd);

        cApiClassList = new TreeViewer(root, SWT.BORDER);
        cApiClassList.setContentProvider(new ApiClassListContentProvider());
        cApiClassList.setLabelProvider(new ApiClassListLabelProvider());

        gd = new GridData(GridData.FILL_BOTH);
        gd.widthHint = 700;
        cApiClassList.getTree().setLayoutData(gd);
        cApiClassList.setInput(this);

        addControlListeners();

        setControl(root);

        validatePage();

        return;
    }

    /**
     * 
     */
    private void addControlListeners() {
        cApiClassParentPlugin.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                apiClassParentPluginId = "";
                apiClassBundle = null;

                String value = cApiClassParentPlugin.getText();

                if (value != null && value.length() > 0) {
                    apiClassBundle = Platform.getBundle(value);

                    if (apiClassBundle != null) {
                        apiClassParentPluginId = value;
                    }
                }

                validatePage();

            }
        });

        cApiClassName.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                selectedApiClass = null;

                if (apiClassBundle != null) {

                    String value = cApiClassName.getText();

                    if (value != null) {
                        try {
                            selectedApiClass = apiClassBundle.loadClass(value);
                        } catch (ClassNotFoundException e1) {

                        }

                    }

                    cApiClassList.refresh();

                    cApiClassList.expandToLevel(2);

                    validatePage();

                }

                return;
            }
        });
    }

    private void validatePage() {

        setPageComplete(apiClassBundle != null && selectedApiClass != null);

        if (apiClassBundle == null) {
            setErrorMessage("You must enter a valid class bundle plug-in Id.");

            cApiBundleLabel.setImage(CreateTestWizardsPlugin.getDefault()
                    .getImageRegistry()
                    .get(CreateTestWizardsConstants.IMG_PROBLEM));

            cApiClassLabel.setImage(CreateTestWizardsPlugin.getDefault()
                    .getImageRegistry()
                    .get(CreateTestWizardsConstants.IMG_PROBLEM));

        } else if (selectedApiClass == null) {
            setErrorMessage("You must enter a valid fully qualified Java Class or Package name.");

            cApiBundleLabel.setImage(CreateTestWizardsPlugin.getDefault()
                    .getImageRegistry().get(CreateTestWizardsConstants.IMG_OK));
            cApiClassLabel.setImage(CreateTestWizardsPlugin.getDefault()
                    .getImageRegistry()
                    .get(CreateTestWizardsConstants.IMG_PROBLEM));

        } else {
            setErrorMessage(null);
            cApiBundleLabel.setImage(CreateTestWizardsPlugin.getDefault()
                    .getImageRegistry().get(CreateTestWizardsConstants.IMG_OK));
            cApiClassLabel.setImage(CreateTestWizardsPlugin.getDefault()
                    .getImageRegistry().get(CreateTestWizardsConstants.IMG_OK));
        }

        cApiBundleLabel.setLayoutData(new GridData());
        cApiClassLabel.setLayoutData(new GridData());
        root.layout(true);

        return;
    }

    @Override
    public boolean canFlipToNextPage() {
        return super.canFlipToNextPage();
    }

    private static class ApiClassListLabelProvider extends LabelProvider {
        @Override
        public String getText(Object element) {
            if (element instanceof Class) {
                Class<?> clazz = (Class<?>) element;
                return clazz.getName();
            }

            return super.getText(element);
        }

        @Override
        public Image getImage(Object element) {
            if (element instanceof Class) {
                return CreateTestWizardsPlugin.getDefault().getImageRegistry()
                        .get(CreateTestWizardsConstants.IMG_CLASS);
            }
            return super.getImage(element);
        }
    }

    private static class ApiClassListContentProvider implements
            ITreeContentProvider {

        public Object[] getChildren(Object parentElement) {
            List<Object> elements = new ArrayList<Object>();
            if (parentElement instanceof Class) {
                Class<?> clazz = (Class<?>) parentElement;

                Set<Class<?>> nested =
                        AbstractApiClassTest.getApiNestedClasses(clazz);
                elements.addAll(nested);
            }

            return elements.toArray();
        }

        public Object getParent(Object element) {
            if (element instanceof Class) {
                Class<?> clazz = (Class<?>) element;

                Class<?> parent = clazz.getEnclosingClass();
                if (parent != null) {
                    return parent;
                }
            }
            return null;
        }

        public boolean hasChildren(Object element) {
            if (element instanceof Class) {
                Class<?> clazz = (Class<?>) element;

                Set<String> apiNestedClasses =
                        AbstractApiClassTest
                                .getApiNestedClassDescriptions(clazz);
                return !apiNestedClasses.isEmpty();

            }

            return false;
        }

        public Object[] getElements(Object inputElement) {
            if (inputElement instanceof CreateClassApiTestPage) {
                if (((CreateClassApiTestPage) inputElement).selectedApiClass != null) {
                    return new Object[] { ((CreateClassApiTestPage) inputElement).selectedApiClass };
                }
            }
            return new Object[0];
        }

        public void dispose() {
        }

        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }

    }

}
