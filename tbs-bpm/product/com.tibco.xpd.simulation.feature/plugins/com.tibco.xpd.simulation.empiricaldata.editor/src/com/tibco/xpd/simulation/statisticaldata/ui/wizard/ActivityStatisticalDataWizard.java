/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.statisticaldata.ui.wizard;

import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.simulation.ActivitySimulationDataType;
import com.tibco.xpd.simulation.EmpircalDataEditorPlugin;
import com.tibco.xpd.simulation.NormalRealDistribution;
import com.tibco.xpd.simulation.ParameterBasedDistribution;
import com.tibco.xpd.simulation.SimulationFactory;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.SimulationRealDistributionType;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;

/**
 * This is a simple wizard for creating a new model file. <!-- begin-user-doc
 * --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class ActivityStatisticalDataWizard extends Wizard implements INewWizard {

    private static final String XLS_IMPORT_TYPE = "XlsImportType";

    private static final String ACTIVITY_MAPPING_PAGE = "ActivityMappingPage";

    private static final String PARAMETERS_MAPPING_PAGE = "ParametersMappingPage";

    private static final String DATA_PREVIEW_PAGE = "DataPreviewPage";

    /**
     */
    public static final String copyright = "TIBCO Software Inc.";

    private ActivityStatisticalDataModelImporter modelImporter;

    /**
     * This is the page with xsl import properties.
     */
    private XlsImportPropertiesPage xslImportPropertiesPage;

    /**
     * This is the page with choosing parameters from imported.
     */
    private ActivitiesMappingPage activityMappingPage;

    /**
     * Remember the workbench during initialization.
     */
    protected IWorkbench workbench;

    private ProcessSelectionPage processSelectionPage;

    private ParametersMappingPage parametersMappingPage;

    private DataPreviewPage dataPreviewPage;

    private IStructuredSelection selection;

    public ActivityStatisticalDataWizard() {
        modelImporter = new ActivityStatisticalDataModelImporter();
    }

    /**
     * This just records the information. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.workbench = workbench;
        this.selection = selection;
        setWindowTitle(EmpircalDataEditorPlugin.INSTANCE
                .getString("_UI_Wizard_label"));
        setDefaultPageImageDescriptor(ExtendedImageRegistry.INSTANCE
                .getImageDescriptor(EmpircalDataEditorPlugin.INSTANCE
                        .getImage("full/wizban/NewEmpiricalData")));
    }

    /**
     * Do the work after everything is specified.
     */
    public boolean performFinish() {
        try {
            // Remember the file.
            //
            // final IFile modelFile = getModelFile();

            final Process process = getSelectedProcess();
            final EditingDomain ed = WorkingCopyUtil.getEditingDomain(process);

            // Do the work within an operation.
            //
            WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {
                protected void execute(IProgressMonitor progressMonitor) {
                    try {
                        CompoundCommand compoundCmd = new CompoundCommand(
                                "Set activity simulation data.");
                        Command cmd = null;
                        Map activityMap = modelImporter
                                .getActivityParameterMap();
                        SimulationFactory simFactory = SimulationFactory.eINSTANCE;
                        SimulationPackage simPackage = SimulationPackage.eINSTANCE;
                        for (Iterator iter = activityMap.entrySet().iterator(); iter
                                .hasNext();) {
                            Map.Entry entry = (Map.Entry) iter.next();
                            ActivityStatisticalDataModelImporter.ActivityDataEntry actDataEntry = (ActivityStatisticalDataModelImporter.ActivityDataEntry) entry
                                    .getKey();
                            if (actDataEntry.isSelected()) {
                                Activity activity = actDataEntry.getActivity();
                                String importedActName = actDataEntry
                                        .getImportedActivityName();
                                Integer selectedParameterIndex = (Integer) entry
                                        .getValue();
                                if (!ActivityStatisticalDataModelImporter.IGNORED_VALUE
                                        .equals(selectedParameterIndex)) {
                                    ActivityStatisticalDataModelImporter.ParameterDataEntry parameterDataEntry = (ActivityStatisticalDataModelImporter.ParameterDataEntry) modelImporter
                                            .getSelectedParameterList().get(
                                                    selectedParameterIndex
                                                            .intValue());
                                    String processParamId = parameterDataEntry
                                            .getParameter().getName();
                                    String paramColumnName = parameterDataEntry
                                            .getParameterColumnName();

                                    Map enumerationStatisticalMap = null;
                                    if (actDataEntry.getDistributionMap() != null) {
                                        enumerationStatisticalMap = actDataEntry
                                                .getDistributionMap();
                                    } else {
                                        enumerationStatisticalMap = modelImporter
                                                .createEnumerationValuesStaticticalMap(
                                                        importedActName,
                                                        paramColumnName,
                                                        processParamId);
                                    }
                                    ParameterBasedDistribution paramBasedDist = modelImporter
                                            .createParameterBasedDistribution(
                                                    processParamId,
                                                    enumerationStatisticalMap);

                                    ActivitySimulationDataType actSimData = SimulationXpdlUtils
                                            .getActivitySimulationData(activity);
                                    if (actSimData == null) {
                                        actSimData = simFactory
                                                .createActivitySimulationDataType();
                                        actSimData
                                                .setDisplayTimeUnit(SimulationXpdlUtils.DEFAULT_TIME_UNIT);
                                        cmd = SimulationXpdlUtils
                                                .getActivitySimulationDataAddCommand(
                                                        ed, activity,
                                                        actSimData);
                                        compoundCmd.append(cmd);
                                    }
                                    SimulationRealDistributionType simDist = simFactory
                                            .createSimulationRealDistributionType();
                                    simDist
                                            .setParameterBasedDistribution(paramBasedDist);
                                    cmd = SetCommand
                                            .create(
                                                    ed,
                                                    actSimData,
                                                    simPackage
                                                            .getActivitySimulationDataType_Duration(),
                                                    simDist);
                                    compoundCmd.append(cmd);
                                } else {
                                    Map enumerationStatisticalMap = null;
                                    if (actDataEntry.getDistributionMap() != null) {
                                        enumerationStatisticalMap = actDataEntry
                                                .getDistributionMap();
                                    } else {
                                        enumerationStatisticalMap = modelImporter
                                                .createEnumerationValuesStaticticalMap(
                                                        importedActName, null,
                                                        null);
                                    }
                                    NormalRealDistribution normalDistribution = modelImporter
                                            .createNormalDistribution(enumerationStatisticalMap);

                                    ActivitySimulationDataType actSimData = SimulationXpdlUtils
                                            .getActivitySimulationData(activity);
                                    if (actSimData == null) {
                                        actSimData = simFactory
                                                .createActivitySimulationDataType();
                                        actSimData
                                                .setDisplayTimeUnit(SimulationXpdlUtils.DEFAULT_TIME_UNIT);
                                        cmd = SimulationXpdlUtils
                                                .getActivitySimulationDataAddCommand(
                                                        ed, activity,
                                                        actSimData);
                                        compoundCmd.append(cmd);
                                    }
                                    SimulationRealDistributionType simDist = simFactory
                                            .createSimulationRealDistributionType();
                                    simDist
                                            .setBasicDistribution(normalDistribution);
                                    cmd = SetCommand
                                            .create(
                                                    ed,
                                                    actSimData,
                                                    simPackage
                                                            .getActivitySimulationDataType_Duration(),
                                                    simDist);
                                    compoundCmd.append(cmd);

                                }
                            }
                        }// for

                        if (compoundCmd.getCommandList().size() > 0) {
                            ed.getCommandStack().execute(compoundCmd);
                        }
                    } catch (Exception exception) {
                        EmpircalDataEditorPlugin.INSTANCE.log(exception);
                    } finally {
                        progressMonitor.done();
                    }

                }
            };

            getContainer().run(false, false, operation);
            return true;
        } catch (Exception exception) {
            EmpircalDataEditorPlugin.INSTANCE.log(exception);
            return false;
        }
    }

    /**
     * The framework calls this to create the contents of the wizard.
     */
    public void addPages() {

        createProcessSelectionPage();
        addPage(processSelectionPage);

        createXslImportPropertiesPage();
        addPage(xslImportPropertiesPage);

        createChooseParametersPage();
        addPage(activityMappingPage);

        createParametersMappingPage();
        addPage(parametersMappingPage);

        createDataPreviewPage();
        addPage(dataPreviewPage);

    }

    private void createProcessSelectionPage() {
        if (processSelectionPage == null) {
            processSelectionPage = new ProcessSelectionPage();

            processSelectionPage
                    .setTitle(EmpircalDataEditorPlugin.INSTANCE
                            .getString("_UI_RealDataModelWizard_ProcessSelection_label")); //$NON-NLS-1$
            processSelectionPage
                    .setDescription(EmpircalDataEditorPlugin.INSTANCE
                            .getString("_UI_RealDataModelWizard_ProcessSelection_description")); //$NON-NLS-1$
        }
        processSelectionPage.init(selection);
    }

    private void createChooseParametersPage() {
        if (activityMappingPage == null) {
            activityMappingPage = new ActivitiesMappingPage(modelImporter,
                    ACTIVITY_MAPPING_PAGE);
            activityMappingPage
                    .setTitle(EmpircalDataEditorPlugin.INSTANCE
                            .getString("_UI_RealDataModelWizard_ChooseParameters_label")); //$NON-NLS-1$
            activityMappingPage
                    .setDescription(EmpircalDataEditorPlugin.INSTANCE
                            .getString("_UI_RealDataModelWizard_ChooseParameters_description")); //$NON-NLS-1$
        }
    }

    private void createParametersMappingPage() {
        if (parametersMappingPage == null) {
            parametersMappingPage = new ParametersMappingPage(modelImporter,
                    PARAMETERS_MAPPING_PAGE);
            parametersMappingPage
                    .setTitle(EmpircalDataEditorPlugin.INSTANCE
                            .getString("_UI_RealDataModelWizard_ParametersMapping_label")); //$NON-NLS-1$
            parametersMappingPage
                    .setDescription(EmpircalDataEditorPlugin.INSTANCE
                            .getString("_UI_RealDataModelWizard_ParametersMapping_description")); //$NON-NLS-1$
        }
    }

    private void createDataPreviewPage() {
        if (dataPreviewPage == null) {
            dataPreviewPage = new DataPreviewPage(modelImporter,
                    DATA_PREVIEW_PAGE);
            dataPreviewPage.setTitle(EmpircalDataEditorPlugin.INSTANCE
                    .getString("_UI_RealDataModelWizard_Preview_label")); //$NON-NLS-1$
            dataPreviewPage.setDescription(EmpircalDataEditorPlugin.INSTANCE
                    .getString("_UI_RealDataModelWizard_Preview_description")); //$NON-NLS-1$
        }
    }

    private void createXslImportPropertiesPage() {
        if (xslImportPropertiesPage == null) {
            xslImportPropertiesPage = new XlsImportPropertiesPage(
                    modelImporter, XLS_IMPORT_TYPE);
            xslImportPropertiesPage
                    .setTitle(EmpircalDataEditorPlugin.INSTANCE
                            .getString("_UI_RealDataModelWizard_XlsImportProperties_label"));
            xslImportPropertiesPage
                    .setDescription(EmpircalDataEditorPlugin.INSTANCE
                            .getString("_UI_RealDataModelWizard_XlsImportProperties_description"));
        }
    }

    public Process getSelectedProcess() {
        return (Process) processSelectionPage.getEContainer();
    }
}
