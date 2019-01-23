package com.tibco.xpd.registry.ui.properties;

import java.util.HashSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.dialogs.PropertyPage;

import com.tibco.xpd.registry.Registry;
import com.tibco.xpd.registry.ui.internal.Messages;

public class RegistryPropertyPage extends PropertyPage implements
        IWorkbenchPropertyPage, Listener {

    private Registry registry;
    private Text nameText;
    private Text queryUrlText;
    private Text publishUrlText;
    private boolean initialised;
    private HashSet<String> currentNames;

    @Override
    protected Control createContents(Composite parent) {
        registry = (Registry) (getElement()).getAdapter(Registry.class);
        currentNames = new HashSet<String>();
        for (Registry r : com.tibco.xpd.registry.Activator.getRegistryManager()
                .getRegistries()) {
            String name = r.getName();
            if (!registry.getName().equals(name)) {
                currentNames.add(name);
            }
        }
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        composite.setLayout(layout);
        GridData data = new GridData(GridData.FILL);
        data.grabExcessHorizontalSpace = true;
        composite.setLayoutData(data);

        // create sections
        addFirstSection(composite);

        fillControlsFormModel();
        nameText.setFocus();
        nameText.selectAll();
        initialised = true;
        setValid(validatePage(true, false));
        return composite;
    }

    private void addFirstSection(Composite parent) {
        Composite composite = createDefaultComposite(parent);

        Label nameLabel = new Label(composite, SWT.NONE);
        nameLabel.setText(Messages.RegistryPropertyPage_Name_label);

        nameText = new Text(composite, SWT.SINGLE | SWT.BORDER);
        nameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        nameText.addListener(SWT.Modify, this);

        Label quryUrlLabel = new Label(composite, SWT.NONE);
        quryUrlLabel.setText(Messages.RegistryPropertyPage_InquiryUrl_label);

        queryUrlText = new Text(composite, SWT.SINGLE | SWT.BORDER);
        queryUrlText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        queryUrlText.addListener(SWT.Modify, this);

        Label publishUrlLabel = new Label(composite, SWT.NONE);
        publishUrlLabel.setText(Messages.RegistryPropertyPage_PublishUrl_label);

        publishUrlText = new Text(composite, SWT.SINGLE | SWT.BORDER);
        publishUrlText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        publishUrlText.addListener(SWT.Modify, this);
    }

    private Composite createDefaultComposite(Composite parent) {
        Composite composite = new Composite(parent, SWT.NULL);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        composite.setLayout(layout);

        GridData data = new GridData();
        data.verticalAlignment = GridData.FILL;
        data.horizontalAlignment = GridData.FILL;
        data.grabExcessHorizontalSpace = true;
        composite.setLayoutData(data);

        return composite;
    }

    public void handleEvent(Event event) {
        if (event.type == SWT.Modify) {
            setValid(validatePage(false, true));
        }
    }

    private boolean validatePage(boolean setFocus, boolean showMessage) {
        if (!initialised) {
            return false;
        }
        setMessage(null);
        setErrorMessage(null);

        // Name
        String name = nameText.getText();
        if (name.length() == 0) {
            if (showMessage) {
                setErrorMessage(Messages.NewRegistryWizard_EmptyName_longdesc);
            }
            if (setFocus) {
                nameText.setFocus();
            }
            return false;
        } else if (currentNames.contains(name)) {
            if (showMessage) {
                setErrorMessage(Messages.NewRegistryWizard_RegistryAlreadyExists_longdesc);
            }
            if (setFocus) {
                nameText.setFocus();
            }
            return false;
        }

        // Inquiry URL
        if (queryUrlText.getText().length() == 0) {
            if (showMessage) {
                setErrorMessage(Messages.NewRegistryWizard_EmptyInuiryUrl_longdesc);
            }
            if (setFocus) {
                queryUrlText.setFocus();
            }
            return false;
        }
        // Publish URL
        if (publishUrlText.getText().length() == 0) {
            if (showMessage) {
                setMessage(Messages.NewRegistryWizard_EmptyPublishUrl_longdesc,
                        WARNING);
            }
            if (setFocus) {
                publishUrlText.setFocus();
            }
            return true;
        }
        return true;
    }

    @Override
    protected void performApply() {
        updateModelFromControls();
    }

    @Override
    public boolean performOk() {
        performApply();
        return true;
    }

    @Override
    protected void performDefaults() {
        fillControlsFormModel();
    }

    private void fillControlsFormModel() {
        if (registry != null) {
            nameText.setText(registry.getName());
            queryUrlText.setText(registry.getQueryManagerUrl());
            publishUrlText.setText(registry.getLifeCycleManagerUrl());
        }
    }

    private void updateModelFromControls() {
        if (registry != null) {
            boolean update = false;

            String name = nameText.getText();
            if (!name.equals(registry.getName())) {
                registry.setName(name);
                update = true;
            }

            String queryUrl = queryUrlText.getText().trim();
            if (!queryUrl.equals(registry.getQueryManagerUrl())) {
                registry.setQueryManagerUrl(queryUrl);
                update = true;
            }

            String publishUrl = publishUrlText.getText().trim();
            if (!publishUrl.equals(registry.getLifeCycleManagerUrl())) {
                registry.setLifeCycleManagerUrl(publishUrl);
                update = true;
            }
            if (update) {
                com.tibco.xpd.registry.Activator.getRegistryManager()
                        .saveRegisters();
                com.tibco.xpd.registry.Activator.getRegistryManager()
                        .updateRegistry(registry);
            }
        }
    }

}
