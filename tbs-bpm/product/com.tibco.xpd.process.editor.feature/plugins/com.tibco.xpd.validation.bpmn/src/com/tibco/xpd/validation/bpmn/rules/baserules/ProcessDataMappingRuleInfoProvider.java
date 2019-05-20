/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules.baserules;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.ChoiceConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.CorrelationDataFolder;
import com.tibco.xpd.processeditor.xpdl2.properties.InitialValue;
import com.tibco.xpd.processeditor.xpdl2.util.SubProcUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;

/**
 * Mapping rule info provider for process data mapping content represented as
 * {@link ConceptPath}.
 * 
 * @author aallway
 * @since 3.3 (16 Jun 2010)
 */
public class ProcessDataMappingRuleInfoProvider
        extends MappingRuleContentInfoProvider {

    /**
     * @param contentProvider
     */
    public ProcessDataMappingRuleInfoProvider(
            ITreeContentProvider contentProvider) {
        super(contentProvider);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.
     * MappingRuleContentInfoProvider #getObjectPath(java.lang.Object)
     */
    @Override
    public String getObjectPath(Object objectFromMappingOrContent) {
        String path = null;

        if (objectFromMappingOrContent != null) {
            if (objectFromMappingOrContent instanceof ConceptPath) {
                path = ((ConceptPath) objectFromMappingOrContent).getPath();

            } else if (objectFromMappingOrContent instanceof ScriptInformation) {
                path = ((ScriptInformation) objectFromMappingOrContent)
                        .getName();
            } else if (!(objectFromMappingOrContent instanceof CorrelationDataFolder)
                    && !(objectFromMappingOrContent instanceof InitialValue)) {
                log("Unexpected content type: " //$NON-NLS-1$
                        + objectFromMappingOrContent.getClass());
            }
        }

        return path != null ? path : ""; //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.
     * MappingRuleContentInfoProvider
     * #getObjectPathDescription(java.lang.Object)
     */
    @Override
    public String getObjectPathDescription(Object objectFromMappingOrContent) {
        String path = null;

        if (objectFromMappingOrContent != null) {
            if (objectFromMappingOrContent instanceof ConceptPath) {
                path = ((ConceptPath) objectFromMappingOrContent).toString();
            } else if (objectFromMappingOrContent instanceof ScriptInformation) {
                path = ((ScriptInformation) objectFromMappingOrContent)
                        .getName();
            } else if (!(objectFromMappingOrContent instanceof CorrelationDataFolder)
                    && !(objectFromMappingOrContent instanceof InitialValue)) {
                log("Unexpected content type: " //$NON-NLS-1$
                        + objectFromMappingOrContent.getClass());
            }
        }
        return path != null ? path : ""; //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.
     * MappingRuleContentInfoProvider #isReadOnly(java.lang.Object)
     */
    @Override
    public boolean isReadOnlyTarget(Object objectInTree) {
        if (objectInTree != null) {
            if (objectInTree instanceof ConceptPath) {
                ProcessRelevantData data = null;

                Object originalTargetItem =
                        ((ConceptPath) objectInTree).getItem();
                if (originalTargetItem instanceof ProcessRelevantData) {
                    data = (ProcessRelevantData) originalTargetItem;
                } else {
                    /*
                     * any content of a process data item that is read only is
                     * also read only.
                     * 
                     * Note for later merge to trunk. On Trunk XPD-4793 app;lied
                     * a change to deal with read-only here. The same change was
                     * made (along with handling of fixed value properties) in a
                     * different way on post-3.6 branch - see XPD-4019 comment
                     * below)
                     */
                    ConceptPath root = ((ConceptPath) objectInTree).getRoot();
                    if (root != null) {
                        Object targetRootItem = root.getItem();
                        if (targetRootItem instanceof ProcessRelevantData) {
                            data = (ProcessRelevantData) targetRootItem;
                        }
                    }
                }

                if (data != null) {
                    if (data.isReadOnly()) {

                        if (!shouldIgnoreReadOnlyTarget(data)) {
                            return true;
                        }
                    }
                }

                /*
                 * Maybe an individual BOM property that is read only
                 */
                if (originalTargetItem instanceof Property) {
                    /*
                     * XPD-4019: If object in tree is instance of Property then
                     * check if it has "xsdFixed" value set.
                     */
                    /*
                     * Sid ACE-194 - we don't support XSD based BOMs in ACE
                     */
                    return ((Property) originalTargetItem).isReadOnly();
                }

            } else if (objectInTree instanceof CorrelationDataFolder) {
                return true;

            } else if (!(objectInTree instanceof ScriptInformation)
                    && !(objectInTree instanceof CorrelationDataFolder)
                    && !(objectInTree instanceof InitialValue)) {
                log("Unexpected content type: " //$NON-NLS-1$
                        + objectInTree.getClass());
            }
        }

        return false;
    }

    /**
     * Gives a chance to the sub-classes to decide if they want to ignore the
     * Read-Only Config on the target.
     * 
     * @param data
     *            the process relavent data.
     * @return <code>true</code> if the ReadOnly config should be ignored; else
     *         return <code>false</code>
     */
    protected boolean shouldIgnoreReadOnlyTarget(ProcessRelevantData data) {
        /*
         * When mapping from to correlation data in an incoming request activity
         * then we should ignore Read-Only flag (which is the default for
         * correlation data encourage user to initialise correlation data using
         * correlation mappings AND mapping to correlation data in "correlate"
         * mode is not actually going to set the data so shouldn't be checked in
         * that circumstance anyway).
         * 
         * XPD-5713: Saket: We should ignore Read-Only flag when we are mapping
         * to input parameter in a reusable subprocess as well.
         */
        return isCorrelationTargetInRequestActivity(data)
                || isInputParameterTargetInSubProcess(data);
    }

    /**
     * Check whether the data is correlation data field AND whether the current
     * activity being validated is an incoming request activity.
     * 
     * @param data
     * 
     * @return <code>true</code> if the data is correlation data field AND
     *         whether the current activity being validated is an incoming
     *         request activity.
     */
    private boolean isCorrelationTargetInRequestActivity(
            ProcessRelevantData data) {
        if (data instanceof DataField) {
            if (((DataField) data).isCorrelation()) {
                Activity contextActivity = getContextActivity();
                if (contextActivity != null) {
                    if (ReplyActivityUtil
                            .isIncomingRequestActivity(contextActivity)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * XPD-5713: Saket: Check whether the data is an input parameter AND whether
     * the current activity being validated is a reusable sub process.
     * 
     * @param data
     * 
     * @return <code>true</code> if the data is input parameter AND the current
     *         activity being validated is a Reusable Sub Process.
     */
    private boolean isInputParameterTargetInSubProcess(
            ProcessRelevantData data) {
        if (data instanceof FormalParameter) {
            if (ModeType.IN_LITERAL
                    .equals(((FormalParameter) data).getMode())) {
                Activity contextActivity = getContextActivity();
                if (contextActivity != null) {
                    if (SubProcUtil.isSubProcessActivity(contextActivity)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.
     * MappingRuleContentInfoProvider #isMultiInstance(java.lang.Object)
     */
    @Override
    public boolean isMultiInstance(Object objectInTree) {
        if (objectInTree != null) {
            if (objectInTree instanceof ConceptPath) {
                /*
                 * Check for isArrayheader() NOT isArray() because isArray()
                 * returns true for individual single instance elements within
                 * the array
                 */
                return ((ConceptPath) objectInTree).isArrayHeader();

            } else if (objectInTree instanceof IScriptRelevantData) {
                // XPD-7352: If source is a List it should be treated as an
                // array.
                IScriptRelevantData srd = (IScriptRelevantData) objectInTree;
                String type = srd.getName();
                if (JsConsts.ARRAY.equals(type)) {
                    return true;
                }
                return ((IScriptRelevantData) objectInTree).isArray();
            } else if (!(objectInTree instanceof ScriptInformation)
                    && !(objectInTree instanceof CorrelationDataFolder)
                    && !(objectInTree instanceof InitialValue)) {
                log("Unexpected content type: " //$NON-NLS-1$
                        + objectInTree.getClass());
            }
        }

        return false;
    }

    /**
     * Public version of {@link #isMultiInstance(Object)}
     * 
     * @param objectInTree
     * @return <code>true</code> if content object is multi-instance or not.
     */
    public boolean isMultiInstancePublic(Object objectInTree) {
        return isMultiInstance(objectInTree);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.
     * MappingRuleContentInfoProvider #getMinimumInstances(java.lang.Object)
     */
    @Override
    public int getMinimumInstances(Object objectInTree) {
        if (objectInTree != null) {
            if (objectInTree instanceof ChoiceConceptPath) {
                return ((ChoiceConceptPath) objectInTree).getMinimumInstances();

            } else if (objectInTree instanceof ConceptPath) {
                ConceptPath conceptPath = (ConceptPath) objectInTree;

                Object item = conceptPath.getItem();
                if (item instanceof ProcessRelevantData) {
                    /*
                     * All sub-class to decide whether process relevant data
                     * items are required or not. otherwise if this is a
                     * correlation data field on an incoming request activity
                     * then it is always required.
                     */
                    if (areProcessRelevantDataItemsMandatory()
                            || isCorrelationTargetInRequestActivity(
                                    (ProcessRelevantData) item)) {
                        return 1;
                    }

                } else if (item instanceof Property) {
                    Property property = (Property) item;

                    /*
                     * XPD-4019: If object in tree has "xsdFixed" value set ,
                     * then it means that the object has fixed value and should
                     * not be mapped as it is not mandatory and hence return
                     * minimum instances as 0.
                     */
                    /*
                     * Sid ACE-194 - we don't support XSD based BOMs in ACE
                     */

                    return property.getLower();

                }

            } else if (objectInTree instanceof CorrelationDataFolder) {
                /*
                 * correlation Data Folder only appears for incoming request
                 * activities - we will class it as "mandatory" so that it's
                 * children (correlation fields) get checked. We will also
                 * specify that it is an artificial object so that it itself
                 * will not be issued as a unmapped required target
                 */
                return 1;

            } else if (!(objectInTree instanceof ScriptInformation)
                    && !(objectInTree instanceof InitialValue)) {
                log("Unexpected content type: " //$NON-NLS-1$
                        + objectInTree.getClass());
            }
        }
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.
     * MappingRuleContentInfoProvider #getMaximumInstances(java.lang.Object)
     */
    @Override
    public int getMaximumInstances(Object objectInTree) {
        if (objectInTree != null) {
            if (objectInTree instanceof ChoiceConceptPath) {
                return ((ChoiceConceptPath) objectInTree).getMaximumInstances();

            } else if (objectInTree instanceof ConceptPath) {
                /*
                 * Check for isArrayheader() NOT isArray() because isArray()
                 * returns true for individual single instance elements within
                 * the array
                 */
                ConceptPath conceptPath = (ConceptPath) objectInTree;
                if (conceptPath.isArrayHeader() || conceptPath.isArrayItem()) {
                    Object item = conceptPath.getItem();
                    if (item instanceof ProcessRelevantData) {
                        return -1; /* No maximum for array field. */

                    } else if (item instanceof Property) {
                        Property property = (Property) item;

                        if (property.getUpper() == -1) {
                            return -1; /* Unbounded. */
                        } else {
                            return property.getUpper();
                        }
                    }
                }

            } else if (!(objectInTree instanceof ScriptInformation)
                    && !(objectInTree instanceof CorrelationDataFolder)
                    && !(objectInTree instanceof InitialValue)) {
                log("Unexpected content type: " //$NON-NLS-1$
                        + objectInTree.getClass());
            }
        }

        return 1; /* Not an array or list so 1's the max. */
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.
     * MappingRuleContentInfoProvider #getInstanceIndex(java.lang.Object)
     */
    @Override
    public int getInstanceIndex(Object objectInTree) {
        if (objectInTree != null) {
            if (objectInTree instanceof ConceptPath) {
                ConceptPath conceptPath = (ConceptPath) objectInTree;

                return conceptPath.getIndex();

            } else if (!(objectInTree instanceof ScriptInformation)
                    && !(objectInTree instanceof CorrelationDataFolder)
                    && !(objectInTree instanceof InitialValue)) {
                log("Unexpected content type: " //$NON-NLS-1$
                        + objectInTree.getClass());
            }
        }

        return -1;
    }

    /**
     * For this default implementation, the process data field / param elements
     * themselves are never mandatory.
     * <p>
     * This is because, generally, the process data info provider will be used
     * for source content (which is never asked whether it is mandatory anyway)
     * OR used for target content on "return data to process from some invoked
     * application" in which case the top level data is never required because
     * it's up to the user whether to ignore the output data from app or not.
     * 
     * @return <code>true</code> if data fields/ params should be considered to
     *         be required.
     */
    protected boolean areProcessRelevantDataItemsMandatory() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.
     * MappingRuleContentInfoProvider #isSimpleTypeContent(java.lang.Object)
     */
    @Override
    public boolean isSimpleTypeContent(Object objectInTree) {
        if (objectInTree != null) {
            if (objectInTree instanceof ConceptPath) {
                ConceptPath cp = (ConceptPath) objectInTree;

                if (cp.getItem() != null && !cp.isArrayHeader()) {
                    /*
                     * Any process data object or (for complex process data BOM
                     * content) any descendant that can be converted to process
                     * data basic type should be counted as simple content.
                     */
                    BasicType basicType = BasicTypeConverterFactory.INSTANCE
                            .getBasicType(cp.getItem());
                    if (basicType != null) {
                        return true;
                    }
                }
            } else if (!(objectInTree instanceof ScriptInformation)
                    && !(objectInTree instanceof CorrelationDataFolder)
                    && !(objectInTree instanceof InitialValue)) {
                log("Unexpected content type: " //$NON-NLS-1$
                        + objectInTree.getClass());
            }
        }

        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider#isNullSimpleContentAllowed(java.lang.Object)
     * 
     * @param objectInTree
     * @return
     */
    @Override
    public boolean isNullSimpleContentAllowed(Object objectInTree) {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.
     * MappingRuleContentInfoProvider #isArtificialObject(java.lang.Object)
     */
    @Override
    public boolean isArtificialObject(Object objectInTree) {
        if (objectInTree instanceof CorrelationDataFolder) {
            /*
             * Content of correlation data folder is required but we don't want
             * to raise the issue on folder itself.
             */
            return true;

        } else if (objectInTree instanceof ConceptPath) {
            /*
             * XPD-7078 - used to check speciifcally for ChoiceConceptPath BUT
             * there is a more generic method in underlying ConceptPath which
             * 
             * ChiceConceptPath returns true for - so use underlting method so
             * that this works for othe stuff too. Content of choice group maybe
             * required but we don't want to raise the issue on folder itself.
             */
            return ((ConceptPath) objectInTree).isArtificialConceptPath();

        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.
     * MappingRuleContentInfoProvider #isChoiceObject(java.lang.Object)
     */
    @Override
    public boolean isChoiceObject(Object objectFromMappingOrContent) {
        return objectFromMappingOrContent instanceof ChoiceConceptPath;
    }

    /**
     * @param msg
     */
    private void log(String msg) {
        XpdResourcesPlugin.getDefault().getLogger().error(msg);
        // throw new RuntimeException(msg);
    }

}
