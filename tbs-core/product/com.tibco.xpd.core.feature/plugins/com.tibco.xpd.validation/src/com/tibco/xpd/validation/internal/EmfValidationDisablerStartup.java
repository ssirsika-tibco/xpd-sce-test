package com.tibco.xpd.validation.internal;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.validation.preferences.EMFModelValidationPreferences;
import org.eclipse.emf.validation.service.ModelValidationService;
import org.eclipse.ui.IStartup;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import com.tibco.xpd.validation.ValidationActivator;

/**
 * Early startup class to disable some of SDS EMF validations which are not
 * relevant to BPM and prevent DAA export.<br/>
 * The validations will only be disabled once (usually for a new workspace) and
 * then user choice will be preserved if he/she decides to change it later.
 * 
 * @author jarciuch
 * @since 7 Apr 2014
 */
public class EmfValidationDisablerStartup implements IStartup {

    private static final String INITIALIZED_DEFAULT_DISABLED_EMF_CONSTRAINTS =
            "InitializedDefaulDisabledtEmfConstraints"; //$NON-NLS-1$

    public void earlyStartup() {
        if (!isInitialised()) {
            /*
             * Ensure that the statically declared (in XML) constraints are
             * available.
             */
            ModelValidationService.getInstance()
                    .loadXmlConstraintDeclarations();

            /*
             * Validates that 'GlobalManagedTransaction' policy is applied
             * together with 'TransactedOneWay'
             */
            EMFModelValidationPreferences
                    .setConstraintDisabled("com.tibco.amf.binding.soap.ui.constraint.TxOneWayAndTxGlobal", /* disabled */true); //$NON-NLS-1$

            EMFModelValidationPreferences.save();
            setInitialised(true);
        }
    }

    private boolean isInitialised() {
        Preferences preferences =
                InstanceScope.INSTANCE.getNode(ValidationActivator.PLUGIN_ID);
        return preferences
                .getBoolean(INITIALIZED_DEFAULT_DISABLED_EMF_CONSTRAINTS, false);
    }

    private void setInitialised(boolean value) {
        try {
            Preferences preferences =
                    InstanceScope.INSTANCE
                            .getNode(ValidationActivator.PLUGIN_ID);
            preferences
                    .putBoolean(INITIALIZED_DEFAULT_DISABLED_EMF_CONSTRAINTS,
                            value);
            preferences.flush();
        } catch (BackingStoreException e) {
            ValidationActivator
                    .getDefault()
                    .getLogger()
                    .error(e,
                            "Error setting 'InitializedDefaulDisabledtEmfConstraints' preference."); //$NON-NLS-1$
        }
    }
}
