/**
 * 
 */
package com.tibco.xpd.processeditor.xpdl2.preCommit;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.WebServiceOperationUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Add xpdExt:SourcePrimitiveType="true" and xpdExt:TargetPrimitiveType="true"
 * to Web service Xpdl2:DataMappings whose process-side data is an attribute in
 * a complex type or other primitive type object.
 * 
 * @author rsomayaj
 * @since 3.5.0 (18 Nov 2010)
 */
public class MappingsProcessDataPrimitiveTypeCommand extends CompoundCommand {

    private final Process process;

    private final EditingDomain editingDomain;

    private final boolean alwaysReset;

    /**
     * 
     */
    private MappingsProcessDataPrimitiveTypeCommand(EditingDomain ed,
            Process process, boolean alwaysReset) {
        this.editingDomain = ed;
        this.process = process;
        this.alwaysReset = alwaysReset;
    }

    public static MappingsProcessDataPrimitiveTypeCommand create(
            EditingDomain editingDomain, Process process) {
        return new MappingsProcessDataPrimitiveTypeCommand(editingDomain,
                process, true);
    }

    /**
     * @param editingDomain
     * @param process
     * @return Command that sets the source/target primitivetype attribtue of ws
     *         activity data mappings ONLY WHEN they are not already set (for
     *         purpose of migration).
     */
    public static MappingsProcessDataPrimitiveTypeCommand createForMigration(
            EditingDomain editingDomain, Process process) {
        return new MappingsProcessDataPrimitiveTypeCommand(editingDomain,
                process, false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.CompoundCommand#execute()
     */
    @Override
    public void execute() {
        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);
        for (Activity act : allActivitiesInProc) {
            boolean isInterestedActivity = isInterestedWebServiceActivity(act);
            if (isInterestedActivity) {
                if (isInterestedActivity) {
                    List<DataMapping> inDataMappings =
                            Xpdl2ModelUtil.getDataMappings(act,
                                    DirectionType.IN_LITERAL);
                    // For direction IN - process data is source/script
                    for (DataMapping dataMapping : inDataMappings) {

                        /*
                         * For migration only need to set the attribute if it
                         * isn't already set. For normal pre commit then the BOM
                         * type may have changed etc so always need to reset.
                         */
                        Object currentAttr =
                                Xpdl2ModelUtil
                                        .getOtherAttribute(dataMapping,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_SourcePrimitiveProperty());

                        if (alwaysReset || currentAttr == null) {
                            if (!DataMappingUtil.isScripted(dataMapping)) {
                                String src =
                                        DataMappingUtil.getScript(dataMapping);
                                appendAndExecute(Xpdl2ModelUtil
                                        .getSetOtherAttributeCommand(editingDomain,
                                                dataMapping,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_SourcePrimitiveProperty(),
                                                isPrimitiveType(src, act)));

                                appendAndExecute(Xpdl2ModelUtil
                                        .getSetOtherAttributeCommand(editingDomain,
                                                dataMapping,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_TargetPrimitiveProperty(),
                                                null));
                            } else {
                                // If it mapped to a script then don't add any
                                // attribute
                                // -
                                appendAndExecute(Xpdl2ModelUtil
                                        .getSetOtherAttributeCommand(editingDomain,
                                                dataMapping,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_SourcePrimitiveProperty(),
                                                null));

                                appendAndExecute(Xpdl2ModelUtil
                                        .getSetOtherAttributeCommand(editingDomain,
                                                dataMapping,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_TargetPrimitiveProperty(),
                                                null));
                            }
                        }
                    }

                    // -------
                    // For mappings with direction OUT - the process data is on
                    // the
                    // target side.
                    List<DataMapping> outDatamappings =
                            Xpdl2ModelUtil.getDataMappings(act,
                                    DirectionType.OUT_LITERAL);
                    // For direction OUT - process data is target
                    for (DataMapping dataMapping : outDatamappings) {
                        /*
                         * For migration only need to set the attribute if it
                         * isn't already set. For normal pre commit then the BOM
                         * type may have changed etc so always need to reset.
                         */
                        Object currentAttr =
                                Xpdl2ModelUtil
                                        .getOtherAttribute(dataMapping,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_TargetPrimitiveProperty());

                        if (alwaysReset || currentAttr == null) {
                            if (!DataMappingUtil.isScripted(dataMapping)) {
                                String src =
                                        DataMappingUtil.getTarget(dataMapping);
                                appendAndExecute(Xpdl2ModelUtil
                                        .getSetOtherAttributeCommand(editingDomain,
                                                dataMapping,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_SourcePrimitiveProperty(),
                                                null));

                                appendAndExecute(Xpdl2ModelUtil
                                        .getSetOtherAttributeCommand(editingDomain,
                                                dataMapping,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_TargetPrimitiveProperty(),
                                                isPrimitiveType(src, act)));
                            } else {
                                // If it mapped to a script then don't add any
                                // attribute
                                // -
                                appendAndExecute(Xpdl2ModelUtil
                                        .getSetOtherAttributeCommand(editingDomain,
                                                dataMapping,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_SourcePrimitiveProperty(),
                                                null));

                                appendAndExecute(Xpdl2ModelUtil
                                        .getSetOtherAttributeCommand(editingDomain,
                                                dataMapping,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_TargetPrimitiveProperty(),
                                                null));
                            }
                        }
                    }

                }
            }
        }

    }

    /**
     * @param act
     * @return
     */
    private boolean isInterestedWebServiceActivity(Activity act) {
        return WebServiceOperationUtil.isWebServiceImplementationType(act);
    }

    /**
     * Method to check if a given mapping string refers to a primitive type or a
     * complex type.
     * 
     * If the string path refers to the process data and if the process data is
     * of basic type or type declaration of basic type then this method return
     * true, else false.
     * 
     * If the string path refers to a bom reference, then it returns what is
     * evaluated by {@link ConceptPath#isAttribute()}
     * 
     * @param processDataPath
     * 
     * @param owner
     * @return
     */
    private boolean isPrimitiveType(String processDataPath, EObject owner) {
        if (owner instanceof Activity) {
            Activity activity = (Activity) owner;
            ConceptPath cp =
                    ConceptUtil.resolveConceptPath(activity, processDataPath);
            if (cp != null) {
                ProcessRelevantData parameter =
                        ConceptUtil.resolveParameter(activity, cp.getPath());
                if (parameter != null) {
                    BasicType basicType =
                            BasicTypeConverterFactory.INSTANCE
                                    .getBasicType(parameter);
                    if (basicType != null) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    // Else if parameter is null i.e the mapping is to either a
                    // BOM attribute or a BOM class.
                    return cp.isAttribute();
                }
            }

        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.AbstractCommand#canExecute()
     */
    @Override
    public boolean canExecute() {
        return true;
    }
}
