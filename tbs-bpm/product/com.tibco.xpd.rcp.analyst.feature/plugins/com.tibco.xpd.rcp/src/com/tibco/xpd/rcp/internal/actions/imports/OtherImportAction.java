/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.actions.imports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.wizards.IWizardDescriptor;

import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.RCPImages;
import com.tibco.xpd.rcp.internal.IOpenResourceListener;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.resources.IRCPContainer;
import com.tibco.xpd.rcp.internal.resources.ModelResource;
import com.tibco.xpd.rcp.internal.resources.RCPResourceManager;
import com.tibco.xpd.rcp.internal.utils.ImportWizardRegistry;
import com.tibco.xpd.rcp.internal.utils.ImportWizardRegistry.CustomImportProperty;

/**
 * The import action to import a resource into the project.
 * 
 * @author njpatel
 * 
 */
public class OtherImportAction extends Action {

    private final IWorkbenchWindow window;

    private Map<String, Collection<IWizardDescriptor>> wizardMap;

    private static final ImportWizardRegistry IMPORT_REGISTRY =
            ImportWizardRegistry.getInstance();

    public OtherImportAction(IWorkbenchWindow workbenchWindow) {
        super(Messages.OtherImportAction_title);
        this.window = workbenchWindow;
        setId("other-import-action"); //$NON-NLS-1$
        setToolTipText(Messages.OtherImportAction_tooltip);
        setImageDescriptor(RCPActivator.getImageDescriptor(RCPImages.IMPORT
                .getPath()));
        setDisabledImageDescriptor(RCPActivator
                .getImageDescriptor(RCPImages.IMPORT.getDisabledPath()));
        setEnabled(false);
        RCPResourceManager.addOpenListener(new IOpenResourceListener() {
            @Override
            public void opened(IRCPContainer resource) {
                // Only available when editing maa files
                if (!getFileExtToWizardsMap().isEmpty()) {
                    setEnabled(!(resource instanceof ModelResource));
                }
            }
        });
    }

    @Override
    public void run() {
        WizardDialog dlg =
                new WizardDialog(window.getShell(), new ImportWizard(
                        window.getActivePage(), getFileExtToWizardsMap()));
        dlg.open();
    }

    /**
     * Create a map that maps a file extension to import wizards available for
     * those file types.
     * 
     * @return
     */
    private Map<String, Collection<IWizardDescriptor>> getFileExtToWizardsMap() {
        if (wizardMap == null) {
            wizardMap = new HashMap<String, Collection<IWizardDescriptor>>();
            Collection<IWizardDescriptor> wizards =
                    IMPORT_REGISTRY.getImportWizards();
            for (IWizardDescriptor wizard : wizards) {
                try {
                    String value =
                            IMPORT_REGISTRY.getProperty(wizard,
                                    CustomImportProperty.INPUT_FILE_EXT);
                    if (value != null) {
                        for (String ext : value.split(",")) {//$NON-NLS-1$
                            if (ext != null && ext.length() > 0) {
                                Collection<IWizardDescriptor> descs =
                                        wizardMap.get(ext);
                                if (descs == null) {
                                    descs = new ArrayList<IWizardDescriptor>();
                                    wizardMap.put(ext, descs);
                                }
                                descs.add(wizard);
                            }
                        }
                    }
                } catch (IOException e) {
                    RCPActivator
                            .getDefault()
                            .getLogger()
                            .error(e,
                                    "Problems reading the import wizard registry."); //$NON-NLS-1$
                }
            }
        }

        return wizardMap;
    }
}
