/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.mapper.IMappingTransferValidator;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class SubProcMappingTransferValidator implements
        IMappingTransferValidator {

    private AbstractMappingSection section;

    private MappingDirection direction;

    /**
     * @param direction
     */
    public SubProcMappingTransferValidator(AbstractMappingSection section,
            MappingDirection direction) {
        this.section = section;
        this.direction = direction;
    }

    /**
     * @param target
     * @return
     * @see com.tibco.xpd.mapper.IMappingTransferValidator#
     *      canAcceptMultipleInputs(java.lang.Object)
     */
    public boolean canAcceptMultipleInputs(Object target) {
        return true;
    }

    /**
     * @param source
     * @param target
     * @return
     * @see com.tibco.xpd.mapper.IMappingTransferValidator#isValidTransfer(java.lang.Object,
     *      java.lang.Object)
     */
    public boolean isValidTransfer(Object source, Object target) {
        boolean valid = true;
        DirectionType dir =
                MappingDirection.IN.equals(direction) ? DirectionType.IN_LITERAL
                        : DirectionType.OUT_LITERAL;
        // Not a valid transfer if either the source or the target is a
        // ChoiceConceptPath
        if (source instanceof ChoiceConceptPath
                || target instanceof ChoiceConceptPath) {
            return false;
        }
        if (source instanceof Collection) {
            for (Object next : (Collection<?>) source) {
                if (next instanceof ScriptInformation
                        || next instanceof InitialValue) {
                    valid = false;
                    break;
                }
            }
        } else if (source instanceof InitialValue) {
            InitialValue initial = (InitialValue) source;
            valid = false;
            if (target instanceof ConceptPath) {
                Object item = ((ConceptPath) target).getItem();
                if (item instanceof FormalParameter) {
                    FormalParameter formal = (FormalParameter) item;
                    if (formal.equals(initial.getFormal())) {
                        List<?> others =
                                Xpdl2ModelUtil
                                        .getOtherElementList(formal,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_InitialValues());
                        if (others.size() > 0) {
                            boolean hasMapping = false;
                            EObject input = section.getInput();
                            if (input instanceof Activity) {
                                Activity activity = (Activity) input;
                                List<DataMapping> mappings =
                                        Xpdl2ModelUtil
                                                .getDataMappings(activity, dir);
                                for (DataMapping mapping : mappings) {
                                    String name =
                                            DataMappingUtil.getTarget(mapping);
                                    if (name != null
                                            && name.equals(getName(target))) {
                                        hasMapping = true;
                                        break;
                                    }
                                }
                            }
                            if (!hasMapping) {
                                valid = true;
                            }
                        }
                    }
                }
            }
        } else {
            EObject input = section.getInput();
            if (input instanceof Activity) {
                Activity activity = (Activity) input;
                List<DataMapping> mappings =
                        Xpdl2ModelUtil.getDataMappings(activity, dir);
                for (DataMapping mapping : mappings) {
                    if (dir.equals(mapping.getDirection())) {
                        String name = DataMappingUtil.getTarget(mapping);
                        if (name != null && name.equals(getName(target))) {
                            valid = false;
                            boolean isInitialValueMapping = false;
                            Object other =
                                    Xpdl2ModelUtil
                                            .getOtherAttribute(mapping,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_InitialValueMapping());
                            if (other instanceof Boolean) {
                                isInitialValueMapping =
                                        ((Boolean) other).booleanValue();
                            }
                            if (isInitialValueMapping) {
                                valid = false;
                            } else {
                                valid = isValidSource(mapping, source);
                            }
                        }
                    }
                }
            }
        }
        return valid;
    }

    /**
     * @param mapping
     * @param source
     * @return
     */
    private boolean isValidSource(DataMapping mapping, Object source) {
        boolean valid = false;
        ScriptInformation information =
                (ScriptInformation) Xpdl2ModelUtil.getOtherElement(mapping,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script());
        if (information == null && !(source instanceof ScriptInformation)) {
            valid = true;
        }
        return valid;
    }

    /**
     * @param item
     *            The item to get the name for.
     * @return The name.
     */
    private String getName(Object item) {
        String name = null;
        if (item instanceof NamedElement) {
            name = ((NamedElement) item).getName();
        } else if (item instanceof ConceptPath) {
            name = ((ConceptPath) item).getPath();
        }
        return name;
    }

}
