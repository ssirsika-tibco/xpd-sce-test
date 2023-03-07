/*
 * (c) 2004-2023 Cloud Software Group, Inc. 
 */
/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.datamapper.properties;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.tibco.xpd.datamapper.internal.Messages;
import com.tibco.xpd.datamapper.scripts.AbstractScriptDataMapperEditorProvider;
import com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty;
import com.tibco.xpd.processeditor.xpdl2.properties.advanced.BooleanPropertyDescriptor;
import com.tibco.xpd.processeditor.xpdl2.util.RestServiceTaskUtil;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Sid ACE-6583
 * 
 * Advanced properties for Target Mapping configuration options.
 * 
 * Do not include empty objects for optional target data. - If the target data is an optional object, within a non-array
 * parent object, then it will not be included at all if after performing all mapping assignments then it has no
 * content.
 * 
 * Do not include empty objects in input array data. - If the target data is an object in an array, then it will not be
 * included in the array at all if after performing all mapping assignments then it has no content.
 * 
 * Do not include empty arrays for optional target data. - If the target data is an array, then it will not be included
 * in the parent object/array at all if after performing all mapping assignments then it has no content.
 * 
 * @author aallway
 * @since Jan 2023
 */
public class AdvancedRestServiceInputMappingProperties {
    /**
     * Base class for all of the 3 Target Mapping properties - deals with everything except for the provision of the
     * {@link ScriptDataMapper} element (model location of which is specific to the mapping scenario) and the boolean
     * property within {@link ScriptDataMapper} that is being set.
     *
     * @author aallway
     * @since 23 Feb 2023
     */
    private abstract static class BaseBooleanScriptDataMapperProperty extends AbstractContributedAdvancedProperty {

        private AbstractScriptDataMapperEditorProvider dataMapperInfoProvider;

        /**
         * @param dataMapperInfoProvider
         */
        public BaseBooleanScriptDataMapperProperty(AbstractScriptDataMapperEditorProvider dataMapperInfoProvider) {
            super();
            this.dataMapperInfoProvider = dataMapperInfoProvider;
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty#createPropertyDescriptor(java.lang.String,
         *      java.lang.String)
         * 
         * @param descriptorId
         * @param displayName
         * @return
         */
        @Override
        public PropertyDescriptor createPropertyDescriptor(String descriptorId, String displayName) {
            return new BooleanPropertyDescriptor(descriptorId, displayName);
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty#isApplicable(org.eclipse.emf.ecore.EObject)
         * 
         * @param input
         * @return
         */
        @Override
        public boolean isApplicable(EObject input) {
            /*
             * Applicable to REST service invocation tasks with DataMapper Target Mappings (by default or explicitly
             * defined)
             */
            if (input instanceof Activity) {
                if (RestServiceTaskUtil.isRESTServiceActivity((Activity) input)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty#getSequenceCount(org.eclipse.emf.ecore.EObject)
         * 
         * @param input
         * @return
         */
        @Override
        public int getSequenceCount(EObject input) {
            return 1;
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty#getValue(org.eclipse.emf.ecore.EObject,
         *      int)
         * 
         * @param input
         * @param sequenceIndex
         * @return
         */
        @Override
        public Object getValue(EObject input, int sequenceIndex) {
            if (input != null) {
                ScriptDataMapper scriptDataMapper = dataMapperInfoProvider.getScriptDataMapper(input);

                if (scriptDataMapper != null) {
                    Boolean ret = getPropertyValue(scriptDataMapper);

                    if (ret != null) {
                        return ret;
                    }
                }
            }

            return Boolean.FALSE;
        }

        /**
         * Get the value of the specific property handled by the sub-class.
         * 
         * @param scriptDataMapper
         * 
         * @return value or <code>null</code> if not set.
         */
        protected abstract Boolean getPropertyValue(ScriptDataMapper scriptDataMapper);

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty#getDefaultValue(org.eclipse.emf.ecore.EObject)
         * 
         * @param input
         * @return
         */
        @Override
        public Object getDefaultValue(EObject input) {
            return Boolean.FALSE;
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty#getSetValueCommand(org.eclipse.emf.edit.domain.EditingDomain,
         *      org.eclipse.emf.ecore.EObject, java.lang.Object, int)
         * 
         * @param editingDomain
         * @param input
         * @param value
         * @param sequenceIndex
         * @return
         */
        @Override
        public Command getSetValueCommand(EditingDomain editingDomain, EObject input, Object value, int sequenceIndex) {
            if (value instanceof Boolean && input != null) {
                CompoundCommand cmd = new CompoundCommand(
                        Messages.AdvancedRestServiceInputMappingProperties_SetInputMappingExclusions_cmd);

                ScriptDataMapper scriptDataMapper =
                        dataMapperInfoProvider.getOrCreateScriptDataMapper(input, editingDomain, cmd);

                cmd.append(getSetPropertyValueCommand(editingDomain, scriptDataMapper, (Boolean) value));

                return cmd;
            }

            return null;
        }

        /**
         * @param editingDomain TODO
         * @param scriptDataMapper
         * @return The command to set the sub-class specific property
         */
        protected abstract Command getSetPropertyValueCommand(EditingDomain editingDomain, ScriptDataMapper scriptDataMapper, Boolean value);

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty#isSet(org.eclipse.emf.ecore.EObject)
         * 
         * @param input
         * @return
         */
        @Override
        public boolean isSet(EObject input) {
            /*
             * Use the sub-class get ACTUAL value rather than our own getValue() as our getValue() returns default FALSE
             * in order to show the correct default value in UI even without property set.
             */
            if (input != null) {
                ScriptDataMapper scriptDataMapper = dataMapperInfoProvider.getScriptDataMapper(input);

                if (scriptDataMapper != null) {
                    if (getPropertyValue(scriptDataMapper) != null) {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty#getRemoveValueCommand(org.eclipse.emf.edit.domain.EditingDomain,
         *      org.eclipse.emf.ecore.EObject)
         * 
         * @param editingDomain
         * @param input
         * @return
         */
        @Override
        public Command getRemoveValueCommand(EditingDomain editingDomain, EObject input) {
            if (input != null) {
                ScriptDataMapper scriptDataMapper = dataMapperInfoProvider.getScriptDataMapper(input);

                if (scriptDataMapper != null) {
                    CompoundCommand cmd = new CompoundCommand(
                            Messages.AdvancedRestServiceInputMappingProperties_SetInputMappingExclusions_cmd);

                    cmd.append(getSetPropertyValueCommand(editingDomain, scriptDataMapper, null));
                    return cmd;
                }
            }

            return null;
        }

    }

    /**
     * Advanced properties for Target Mapping configuration options...
     * 
     * Do not include empty objects for optional target data.
     * 
     * - If the target data is an optional object, within a non-array parent object, then it will not be included at all
     * if after performing all mapping assignments then it has no content.
     */
    public abstract static class AbstractExcludeEmptyOptionalObjectsProperty
            extends BaseBooleanScriptDataMapperProperty {

        /**
         * @param dataMapperInfoProvider
         */
        public AbstractExcludeEmptyOptionalObjectsProperty(
                AbstractScriptDataMapperEditorProvider dataMapperInfoProvider) {
            super(dataMapperInfoProvider);
        }

        /**
         * @see com.tibco.xpd.datamapper.properties.AdvancedRestServiceInputMappingProperties.BaseBooleanScriptDataMapperProperty#getPropertyValue(com.tibco.xpd.xpdExtension.ScriptDataMapper)
         *
         * @param scriptDataMapper
         * @return
         */
        @Override
        protected Boolean getPropertyValue(ScriptDataMapper scriptDataMapper) {
            return scriptDataMapper.isExcludeEmptyOptionalObjects();
        }

        /**
         * @param scriptDataMapper
         * @param value
         * @see com.tibco.xpd.datamapper.properties.AdvancedRestServiceInputMappingProperties.BaseBooleanScriptDataMapperProperty#getSetPropertyValueCommand(EditingDomain,
         *      com.tibco.xpd.xpdExtension.ScriptDataMapper, java.lang.Boolean)
         *
         * @return
         */
        @Override
        protected Command getSetPropertyValueCommand(EditingDomain editingDomain, ScriptDataMapper scriptDataMapper,
                Boolean value) {
            return SetCommand.create(editingDomain,
                    scriptDataMapper,
                    XpdExtensionPackage.eINSTANCE.getScriptDataMapper_ExcludeEmptyOptionalObjects(),
                    value);
        }
    }

    /**
     * Advanced properties for Target Mapping configuration options...
     * 
     * Do not include empty objects from arrays data - If the target data is an object in an array, then it will not be
     * included in the array at all if after performing all mapping assignments then it has no content.
     *
     * Sub-class just supplies the AbstractScriptDataMapperEditorProvider which is used to locate/create the
     * {@link ScriptDataMapper} model for a specific mapping scenario.
     */
    public abstract static class AbstractExcludeEmptyObjectsFromArraysProperty
            extends BaseBooleanScriptDataMapperProperty {

        /**
         * @param dataMapperInfoProvider
         */
        public AbstractExcludeEmptyObjectsFromArraysProperty(
                AbstractScriptDataMapperEditorProvider dataMapperInfoProvider) {
            super(dataMapperInfoProvider);
        }

        /**
         * @see com.tibco.xpd.datamapper.properties.AdvancedRestServiceInputMappingProperties.BaseBooleanScriptDataMapperProperty#getPropertyValue(com.tibco.xpd.xpdExtension.ScriptDataMapper)
         *
         * @param scriptDataMapper
         * @return
         */
        @Override
        protected Boolean getPropertyValue(ScriptDataMapper scriptDataMapper) {
            return scriptDataMapper.isExcludeEmptyObjectsFromArrays();
        }

        /**
         * @param scriptDataMapper
         * @param value
         * @see com.tibco.xpd.datamapper.properties.AdvancedRestServiceInputMappingProperties.BaseBooleanScriptDataMapperProperty#getSetPropertyValueCommand(EditingDomain,
         *      com.tibco.xpd.xpdExtension.ScriptDataMapper, java.lang.Boolean)
         *
         * @return
         */
        @Override
        protected Command getSetPropertyValueCommand(EditingDomain editingDomain, ScriptDataMapper scriptDataMapper,
                Boolean value) {
            return SetCommand.create(editingDomain,
                    scriptDataMapper,
                    XpdExtensionPackage.eINSTANCE.getScriptDataMapper_ExcludeEmptyObjectsFromArrays(),
                    value);
        }
    }

    /**
     * Advanced properties for Target Mapping configuration options...
     * 
     * Do not include empty arrays for optional target data - If the target data is an array, then it will not be
     * included in the parent object/array at all if after performing all mapping assignments then it has no content.
     *
     * Sub-class just supplies the AbstractScriptDataMapperEditorProvider which is used to locate/create the
     * {@link ScriptDataMapper} model for a specific mapping scenario.
     */
    public abstract static class AbstractExcludeEmptyOptionalArraysProperty
            extends BaseBooleanScriptDataMapperProperty {

        /**
         * @param dataMapperInfoProvider
         */
        public AbstractExcludeEmptyOptionalArraysProperty(AbstractScriptDataMapperEditorProvider dataMapperInfoProvider) {
            super(dataMapperInfoProvider);
        }

        /**
         * @see com.tibco.xpd.datamapper.properties.AdvancedRestServiceInputMappingProperties.BaseBooleanScriptDataMapperProperty#getPropertyValue(com.tibco.xpd.xpdExtension.ScriptDataMapper)
         *
         * @param scriptDataMapper
         * @return
         */
        @Override
        protected Boolean getPropertyValue(ScriptDataMapper scriptDataMapper) {
            return scriptDataMapper.isExcludeEmptyOptionalArrays();
        }

        /**
         * @param scriptDataMapper
         * @param value
         * @see com.tibco.xpd.datamapper.properties.AdvancedRestServiceInputMappingProperties.BaseBooleanScriptDataMapperProperty#getSetPropertyValueCommand(EditingDomain,
         *      com.tibco.xpd.xpdExtension.ScriptDataMapper, java.lang.Boolean)
         *
         * @return
         */
        @Override
        protected Command getSetPropertyValueCommand(EditingDomain editingDomain, ScriptDataMapper scriptDataMapper, Boolean value) {
            return SetCommand.create(editingDomain,
                    scriptDataMapper,
                    XpdExtensionPackage.eINSTANCE.getScriptDataMapper_ExcludeEmptyOptionalArrays(),
                    value);
        }
    }

}
