package com.tibco.xpd.registry.ui.properties;

import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.dialogs.PropertyPage;

import com.tibco.xpd.registry.Search;
import com.tibco.xpd.registry.ui.internal.Messages;
import com.tibco.xpd.registry.ui.selector.RegistrySearch;

public class SearchPropertyPage extends PropertyPage implements
        IWorkbenchPropertyPage, Listener {

    private Search search;
    private Text nameText;
    private boolean initialised;
    private Text criteriaText;
    private Button businessSearchRadio;
    private Button serviceSearchRadio;
    private RegistrySearch registrySearch;

    @Override
    protected Control createContents(Composite parent) {

        registrySearch = (RegistrySearch) (getElement())
                .getAdapter(RegistrySearch.class);
        search = registrySearch.getSearch();

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
        nameLabel.setText(Messages.SearchPropertyPage_Name_label);

        nameText = new Text(composite, SWT.SINGLE | SWT.BORDER);
        nameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        nameText.addListener(SWT.Modify, this);

        Label criteriaLabel = new Label(composite, SWT.NONE);
        criteriaLabel.setText(Messages.SearchPropertyPage_SearchCriteria_label);

        criteriaText = new Text(composite, SWT.SINGLE | SWT.BORDER);
        criteriaText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        criteriaText.addListener(SWT.Modify, this);

        new Label(composite, SWT.NONE);
        Label searchInstructionsLabel = new Label(composite, SWT.NONE);
        GridDataFactory.fillDefaults().applyTo(searchInstructionsLabel);
        searchInstructionsLabel
                .setText(Messages.SearchPropertyPage_SearchDesc_label);

        Group group = new Group(composite, SWT.NONE);
        group.setText(Messages.SearchPropertyPage_SearchType_label);
        GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(
                group);
        GridLayout groupLayout = new GridLayout(1, false);
        group.setLayout(groupLayout);

        businessSearchRadio = new Button(group, SWT.RADIO);
        businessSearchRadio
                .setText(Messages.SearchPropertyPage_SearchForBusiness_button);

        serviceSearchRadio = new Button(group, SWT.RADIO);
        serviceSearchRadio
                .setText(Messages.SearchPropertyPage_SearchForService_button);
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
        String name = nameText.getText().trim();
        if (name.length() == 0) {
            if (showMessage) {
                setErrorMessage(Messages.SearchPropertyPage_NameEmpty_message);
            }
            if (setFocus) {
                nameText.setFocus();
            }
            return false;
        }
        // Criteria
        String searchCriteria = criteriaText.getText().trim();
        if (searchCriteria.length() == 0) {
            if (showMessage) {
                setErrorMessage(Messages.SearchPropertyPage_SearchCriteriaEmpty_message);
            }
            if (setFocus) {
                criteriaText.setFocus();
            }
            return false;
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
        if (search != null) {
            nameText.setText(search.getName());
            if (search.getType() == Search.FIND_BUSINESS) {
                businessSearchRadio.setSelection(true);
            } else if (search.getType() == Search.FIND_SERVICE) {
                serviceSearchRadio.setSelection(true);
            }
            String criteria = getCriteria();
            if (criteria != null) {
                criteriaText.setText(criteria);
            }
        }
    }

    /**
     * Obtains first criteria search.
     * 
     * @return criteria form search or null if there is not criteria set yet.
     */
    private String getCriteria() {
        List<String> nameCriteria = search.getNameCriteria();
        if (nameCriteria != null && nameCriteria.size() > 0
                && nameCriteria.get(0) != null) {
            return nameCriteria.get(0);
        }
        return null;
    }

    private void updateModelFromControls() {
        if (search != null) {
            boolean update = false;
            boolean labelsOnly = false;

            String name = nameText.getText();
            if (!name.equals(search.getName())) {
                search.setName(name);
                update = true;
                labelsOnly = true;
            }

            if (businessSearchRadio.getSelection()) {
                if (search.getType() != Search.FIND_BUSINESS) {
                    search.setType(Search.FIND_BUSINESS);
                    update = true;
                    labelsOnly = false;
                }
            } else if (serviceSearchRadio.getSelection()) {
                if (search.getType() != Search.FIND_SERVICE) {
                    search.setType(Search.FIND_SERVICE);
                    update = true;
                    labelsOnly = false;
                }
            } else {
                Assert.isTrue(false, "Incorrect Search Type selection."); //$NON-NLS-1$
            }

            String criteria = criteriaText.getText().trim();
            String oldCriteria = getCriteria();
            if (oldCriteria == null
                    || (oldCriteria != null && !oldCriteria.equals(criteria))) {
                search.removeNameCriteria(oldCriteria);
                search.addNameCriteria(criteria);
                update = true;
                labelsOnly = false;
            }

            if (update) {
                com.tibco.xpd.registry.Activator.getRegistryManager()
                        .saveRegisters();
                registrySearch.update(labelsOnly);
            }
        }
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            if (validatePage(true, false)) {
                nameText.setFocus();
            }
        }
    }
}
