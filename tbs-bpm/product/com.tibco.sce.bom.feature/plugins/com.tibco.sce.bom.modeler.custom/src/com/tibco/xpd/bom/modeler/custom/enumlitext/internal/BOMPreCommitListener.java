/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.enumlitext.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListenerImpl;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.custom.enumlitext.DomainValue;
import com.tibco.xpd.bom.modeler.custom.enumlitext.SingleValue;
import com.tibco.xpd.bom.modeler.custom.enumlitext.util.EnumLitValueUtil;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.bom.types.PrimitivesUtil;

/**
 * Pre-commit listener that will add the Single value annotation to all
 * enumeration literals added to a model. This is used to capture the domain
 * information for the BE Domain model.
 * 
 * @author njpatel
 */
public class BOMPreCommitListener extends ResourceSetListenerImpl {

    private class ClassifierTypeChange {
        private PrimitiveType oldType;

        private PrimitiveType newType;
    }

    /**
     * @see org.eclipse.emf.transaction.ResourceSetListenerImpl#isPrecommitOnly()
     * 
     * @return
     */
    @Override
    public boolean isPrecommitOnly() {
        return true;
    }

    /**
     * @see org.eclipse.emf.transaction.ResourceSetListenerImpl#transactionAboutToCommit(org.eclipse.emf.transaction.ResourceSetChangeEvent)
     * 
     * @param event
     * @return
     * @throws RollbackException
     */
    @Override
    public Command transactionAboutToCommit(ResourceSetChangeEvent event)
            throws RollbackException {
        List<Command> commands = new ArrayList<Command>();

        for (Notification notification : event.getNotifications()) {
            if (!notification.isTouch()) {
                TransactionalEditingDomain editingDomain =
                        event.getEditingDomain();

                if (notification.getFeature() == UMLPackage.eINSTANCE
                        .getEnumeration_OwnedLiteral()) {
                    if (notification.getEventType() == Notification.ADD
                            && notification.getNewValue() instanceof EnumerationLiteral) {
                        /*
                         * Enumeration literal added - so pre-add value
                         * annotation
                         */
                        commands.add(new SetSingleValueCommand(editingDomain,
                                (EnumerationLiteral) notification.getNewValue()));
                    }
                } else if (notification.getFeature() == UMLPackage.eINSTANCE
                        .getNamedElement_Name()) {
                    if (notification.getNotifier() instanceof EnumerationLiteral
                            && notification.getEventType() == Notification.SET
                            && notification.getOldStringValue() != null) {
                        final EnumerationLiteral literal =
                                (EnumerationLiteral) notification.getNotifier();
                        /*
                         * If enumeration literal name has changed and this is a
                         * String type enumeration then update the value to
                         * match the name
                         */
                        if (literal.getEnumeration() != null
                                && PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME
                                        .equals(getBasePrimitiveType(literal
                                                .getEnumeration()).getName())) {
                            DomainValue value =
                                    EnumLitValueUtil.getDomainValue(literal);
                            if (value instanceof SingleValue) {
                                final String newValue =
                                        notification.getNewStringValue();

                                commands.add(new RecordingCommand(
                                        editingDomain, "Update Literal Value") {
                                    @Override
                                    protected void doExecute() {
                                        EnumLitValueUtil
                                                .setSingleValue(literal,
                                                        newValue);
                                    }
                                });

                            }
                        }
                    }
                }
            }
        }

        List<Command> typeChangeCommands =
                getEnumerationTypeChangeCommands(event);
        if (typeChangeCommands != null && !typeChangeCommands.isEmpty()) {
            commands.addAll(typeChangeCommands);
        }

        if (!commands.isEmpty()) {
            if (commands.size() == 1) {
                return commands.get(0);
            } else {
                return new CompoundCommand(commands);
            }
        }

        return null;
    }

    /**
     * If the notifications contain a change to enumeration(s) type then its
     * literals will need the value reset. This will return the commands to do
     * so.
     * 
     * @param event
     * @return list of reset commands, <code>null</code> if no reset is
     *         required.
     */
    private List<Command> getEnumerationTypeChangeCommands(
            ResourceSetChangeEvent event) {
        final TransactionalEditingDomain editingDomain =
                event.getEditingDomain();
        Map<Enumeration, ClassifierTypeChange> typeChange =
                new HashMap<Enumeration, BOMPreCommitListener.ClassifierTypeChange>();

        // The following two maps are used to track removed generalizations
        Map<Generalization, Classifier/* General */> removedGeneralizationGeneral =
                new HashMap<Generalization, Classifier>();
        Map<Generalization, Classifier/* Specific */> removedGeneralizationSpecific =
                new HashMap<Generalization, Classifier>();

        for (Notification notification : event.getNotifications()) {
            if (!notification.isTouch()) {
                Set<Enumeration> enumerations = new HashSet<Enumeration>();
                PrimitiveType oldType = null;
                PrimitiveType newType = null;

                /*
                 * Generalization change - if the generalization heirarchy of an
                 * Enumeration is changed then its type may have changed
                 */
                if (notification.getFeature() == UMLPackage.eINSTANCE
                        .getClassifier_Generalization()
                        && notification.getNotifier() instanceof Classifier) {

                    if (notification.getNotifier() instanceof Enumeration) {
                        enumerations.add((Enumeration) notification
                                .getNotifier());
                    } else if (notification.getNotifier() instanceof PrimitiveType) {
                        getEnumerationSubTypes((PrimitiveType) notification.getNotifier(),
                                enumerations,
                                new HashSet<Classifier>());
                    }

                    if (!enumerations.isEmpty()) {
                        if (notification.getEventType() == Notification.ADD) {
                            // Generalization added
                            if (notification.getNewValue() instanceof Generalization) {
                                Classifier general =
                                        ((Generalization) notification
                                                .getNewValue()).getGeneral();
                                if (general != null) {
                                    newType = getType(editingDomain, general);
                                }
                            }
                        } else if (notification.getEventType() == Notification.REMOVE) {
                            // Generalization specific changed - by drag in the
                            // editor
                            if (notification.getOldValue() instanceof Generalization) {
                                Classifier general =
                                        ((Generalization) notification
                                                .getOldValue()).getGeneral();
                                if (general != null) {
                                    oldType = getType(editingDomain, general);
                                }
                            }
                        }
                    }

                    /*
                     * General of a generalization has changed - this could
                     * affect the type of an enumeration if the generalization
                     * affects an Enumeration.
                     */
                } else if (notification.getFeature() == UMLPackage.eINSTANCE
                        .getGeneralization_General()
                        && notification.getNotifier() instanceof Generalization) {
                    Generalization gen =
                            (Generalization) notification.getNotifier();

                    if (gen.eContainer() instanceof Enumeration) {
                        enumerations.add((Enumeration) gen.eContainer());
                    } else if (gen.eContainer() instanceof PrimitiveType) {
                        getEnumerationSubTypes((PrimitiveType) gen.eContainer(),
                                enumerations,
                                new HashSet<Classifier>());
                    }

                    if (!enumerations.isEmpty()) {
                        if (notification.getOldValue() instanceof Classifier) {
                            oldType =
                                    getType(editingDomain,
                                            (Classifier) notification
                                                    .getOldValue());
                        }

                        if (notification.getNewValue() instanceof Classifier) {
                            newType =
                                    getType(editingDomain,
                                            (Classifier) notification
                                                    .getNewValue());
                        }
                    } else {
                        // This is probably a removed generalization so capture
                        // the generalization and its general
                        if (notification.getOldValue() instanceof Classifier
                                && notification.getNewValue() == null) {
                            removedGeneralizationGeneral.put(gen,
                                    (Classifier) notification.getOldValue());
                        }
                    }
                } else if (notification.getFeature() == UMLPackage.eINSTANCE
                        .getGeneralization_Specific()
                        && notification.getNotifier() instanceof Generalization) {
                    if (notification.getOldValue() instanceof Classifier
                            && notification.getNewValue() == null) {
                        // This is probably a removed generalization so capture
                        // the generalization and its specific
                        removedGeneralizationSpecific
                                .put((Generalization) notification
                                        .getNotifier(),
                                        (Classifier) notification.getOldValue());
                    }
                }

                if (!enumerations.isEmpty()
                        && (oldType != null || newType != null)) {
                    for (Enumeration enumeration : enumerations) {
                        ClassifierTypeChange change =
                                typeChange.get(enumeration);
                        if (change == null) {
                            change = new ClassifierTypeChange();
                            typeChange.put(enumeration, change);
                        }

                        if (change.oldType == null) {
                            change.oldType = oldType;
                        }

                        if (change.newType == null) {
                            change.newType = newType;
                        }
                    }
                }
            }
        }

        /*
         * If generalizations were deleted then process any affected
         * Enumerations
         */
        if (!removedGeneralizationGeneral.isEmpty()
                && !removedGeneralizationSpecific.isEmpty()) {
            for (Entry<Generalization, Classifier> entry : removedGeneralizationSpecific
                    .entrySet()) {
                Set<Enumeration> specifics = new HashSet<Enumeration>();

                if (entry.getValue() instanceof Enumeration) {
                    specifics.add((Enumeration) entry.getValue());
                } else if (entry.getValue() instanceof PrimitiveType) {
                    getEnumerationSubTypes((PrimitiveType) entry.getValue(),
                            specifics,
                            new HashSet<Classifier>());
                }

                for (Enumeration specific : specifics) {
                    Classifier general =
                            removedGeneralizationGeneral.get(entry.getKey());

                    if (general != null) {
                        /*
                         * The effective enumeration subtype has been reset from
                         * this general type.
                         */
                        ClassifierTypeChange change = typeChange.get(specific);
                        if (change == null) {
                            change = new ClassifierTypeChange();
                            typeChange.put(specific, change);
                        }

                        if (change.oldType == null) {
                            change.oldType = getType(editingDomain, general);
                        }
                    }
                }
            }
        }

        // Generate commands for all affected enumerations
        if (!typeChange.isEmpty()) {
            List<Command> commands = new ArrayList<Command>();

            for (Entry<Enumeration, ClassifierTypeChange> entry : typeChange
                    .entrySet()) {
                PrimitiveType oldType = entry.getValue().oldType;
                PrimitiveType newType = entry.getValue().newType;

                /*
                 * If any type is null then reset to default type - TEXT.
                 */
                if (oldType == null) {
                    oldType =
                            PrimitivesUtil
                                    .getDefaultPrimitiveType(editingDomain
                                            .getResourceSet());
                }

                if (newType == null) {
                    newType =
                            PrimitivesUtil
                                    .getDefaultPrimitiveType(editingDomain
                                            .getResourceSet());
                }

                if (!oldType.equals(newType)) {
                    Set<EnumerationLiteral> allLiterals =
                            getAllLiterals(entry.getKey());
                    if (!allLiterals.isEmpty()) {
                        commands.add(new ResetEnumerationLiteralValueCommand(
                                editingDomain, allLiterals, newType));
                    }
                }
            }

            if (!commands.isEmpty()) {
                return commands;
            }
        }

        return null;
    }

    /**
     * Get the all the Enumeration subtypes of the given PrimitiveType. This
     * will only return the first Enumeration subtype in the hierarchy it comes
     * across.
     * 
     * @param type
     * @param enumerations
     *            set of Enumerations if in the sub-type hierarchies, empty set
     *            if none found.
     * @param alreadyProcessed
     *            temporary cache to record all processed classifiers, to avoid
     *            cyclic processing.
     */
    private void getEnumerationSubTypes(PrimitiveType type,
            Set<Enumeration> enumerations, Set<Classifier> alreadyProcessed) {
        if (type != null) {
            Collection<Setting> inverseReferences = getInverseReferences(type);

            if (inverseReferences != null) {
                for (Setting ref : inverseReferences) {
                    if (ref.getEObject() instanceof Generalization) {
                        Classifier specific =
                                ((Generalization) ref.getEObject())
                                        .getSpecific();

                        if (!alreadyProcessed.contains(specific)) {
                            alreadyProcessed.add(specific);
                            if (specific instanceof Enumeration) {
                                enumerations.add((Enumeration) specific);
                            } else if (specific instanceof PrimitiveType) {
                                getEnumerationSubTypes((PrimitiveType) specific,
                                        enumerations,
                                        alreadyProcessed);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Get the inverse references to the given EObject.
     * 
     * @param eo
     * @return collections of references if any, empty or <code>null</code>
     *         otherwise.
     */
    private Collection<Setting> getInverseReferences(EObject eo) {

        ECrossReferenceAdapter adapter =
                ECrossReferenceAdapter.getCrossReferenceAdapter(eo);
        if (adapter != null) {
            return adapter.getInverseReferences(eo);
        }

        return null;
    }

    /**
     * Collect all the literals of the given Enumeration and any of it's "sub"
     * enumeration types.
     * 
     * @param enumeration
     * @param literals
     *            all literals will be added to this set.
     */
    private Set<EnumerationLiteral> getAllLiterals(Enumeration enumeration) {
        Set<EnumerationLiteral> literals =
                new LinkedHashSet<EnumerationLiteral>();
        Set<Enumeration> alreadyProcessed = new HashSet<Enumeration>();

        if (null != enumeration) {
            alreadyProcessed.add(enumeration);
            literals.addAll(enumeration.getOwnedLiterals());
        }

        List<Classifier> directSubEnums =
                BOMUtils.getDirectSubClassifiers(enumeration);
        for (Classifier classifier : directSubEnums) {
            enumeration =
                    (Enumeration) (classifier instanceof Enumeration ? classifier
                            : null);
            if (null != enumeration && !alreadyProcessed.contains(enumeration)) {
                alreadyProcessed.add(enumeration);
                literals.addAll(enumeration.getOwnedLiterals());
            }
        }

        return literals;
    }

    /**
     * Given a Classifier (superclass of the Enumeration) determine the base
     * type of the Enumeration. Used to determine type change when
     * Generalization changes.
     * 
     * @param ed
     * @param classifier
     *            can be {@link Enumeration} or {@link PrimitiveType}.
     * @return base primitive type, if the Classifier is not an Enumeration or
     *         PrimitiveType then default type will be returned.
     */
    private PrimitiveType getType(TransactionalEditingDomain ed,
            Classifier classifier) {
        PrimitiveType type = null;

        if (classifier instanceof Enumeration) {
            type = getBasePrimitiveType((Enumeration) classifier);
        } else if (classifier instanceof PrimitiveType) {
            type = getBasePrimitiveType((PrimitiveType) classifier);
        }

        return type != null ? type : PrimitivesUtil.getDefaultPrimitiveType(ed
                .getResourceSet());
    }

    /**
     * Get the base primitive type of the given type.
     * 
     * @param type
     * @return base primitive type if set, <code>null</code> otherwise.
     */
    private PrimitiveType getBasePrimitiveType(PrimitiveType type) {
        PrimitiveType baseType = PrimitivesUtil.getBasePrimitiveType(type);

        /*
         * If the user defined type has no base type then return null
         */
        if (baseType != null && baseType.eResource() != null
                && !baseType.eResource().getURI().isPlatformResource()) {
            return baseType;
        }

        return null;
    }

    /**
     * Get the base primitive type of the given Enumeration.
     * 
     * @param enumeration
     * @return base type if set, <code>null</code> otherwise.
     */
    private PrimitiveType getBasePrimitiveType(Enumeration enumeration) {
        return EnumLitValueUtil.getBaseType(enumeration);
    }

    /**
     * Add the domain extension to the enumeration literal.
     * 
     */
    private class SetSingleValueCommand extends RecordingCommand {

        private final EnumerationLiteral literal;

        public SetSingleValueCommand(TransactionalEditingDomain domain,
                EnumerationLiteral literal) {
            super(domain);
            this.literal = literal;
        }

        @Override
        protected void doExecute() {
            PrimitiveType type = getBasePrimitiveType(literal.getEnumeration());

            String value = ""; //$NON-NLS-1$
            /*
             * If the Enumeration type is Text then set the default value to be
             * the same as the name of the literal, otherwise set to empty
             * string.
             */
            if (PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME.equals(type.getName())) {
                value = literal.getName();
            } else if (PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME.equals(type
                    .getName())) {
                if (literal.getEnumeration().getOwnedLiterals().size() == 1) {
                    // Default to True
                    value = Boolean.TRUE.toString();
                } else {
                    boolean hasTrue = false;
                    for (EnumerationLiteral lit : literal.getEnumeration()
                            .getOwnedLiterals()) {
                        DomainValue dvalue =
                                EnumLitValueUtil.getDomainValue(lit);
                        if (dvalue != null
                                && Boolean.parseBoolean(dvalue.getValue())) {
                            hasTrue = true;
                            break;
                        }
                    }
                    value =
                            hasTrue ? Boolean.FALSE.toString() : Boolean.TRUE
                                    .toString();
                }
            }

            EnumLitValueUtil.setSingleValue(literal, value);
        }
    }

    /**
     * Reset the values of all the EnumerationLiterals of the given Enumeration.
     * 
     */
    private class ResetEnumerationLiteralValueCommand extends AbstractCommand {

        private final Set<EnumerationLiteral> literals;

        private final PrimitiveType newType;

        /**
         * Register the current domain value of these literals before resetting
         * so that this can be reverted on an undo/redo.
         */
        private final Map<EnumerationLiteral, DomainValue> prevValues;

        public ResetEnumerationLiteralValueCommand(
                TransactionalEditingDomain domain,
                Set<EnumerationLiteral> literals, PrimitiveType newType) {
            this.literals = literals;
            this.newType = newType;
            prevValues = new HashMap<EnumerationLiteral, DomainValue>();
        }

        /**
         * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
         * 
         * @return
         */
        @Override
        protected boolean prepare() {

            // Store the current values of these literals
            for (EnumerationLiteral literal : literals) {
                // Take a copy of the enumeration value and save it, we need to
                // do a copy as the object itself will change to the new value,
                // so we would lose the old value
                DomainValue domainValue =
                        EcoreUtil
                                .copy(EnumLitValueUtil.getDomainValue(literal));
                prevValues.put(literal, domainValue);
            }

            return true;
        }

        /**
         * @see org.eclipse.emf.common.command.Command#execute()
         * 
         */
        @Override
        public void execute() {
            boolean isBooleanType =
                    PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME.equals(newType
                            .getName());
            boolean isTextType =
                    PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME.equals(newType
                            .getName());

            boolean setTrue = true;

            for (EnumerationLiteral literal : literals) {
                String value = ""; //$NON-NLS-1$
                if (isBooleanType) {
                    // If the enumeration is of Boolean type then set the
                    // appropriate value
                    value =
                            setTrue ? Boolean.TRUE.toString() : Boolean.FALSE
                                    .toString();
                    setTrue = false;
                } else if (isTextType) {
                    // Match the Value to the name of the literal
                    value = literal.getName();
                }
                EnumLitValueUtil.setSingleValue(literal, value);
            }
        }

        /**
         * @see org.eclipse.emf.common.command.AbstractCommand#undo()
         * 
         */
        @Override
        public void undo() {
            reapplyPreviousValues();
        }

        /**
         * @see org.eclipse.emf.common.command.Command#redo()
         * 
         */
        @Override
        public void redo() {
            reapplyPreviousValues();
        }

        /**
         * Re-apply the previous values set in the literals.
         */
        private void reapplyPreviousValues() {
            // Save off the previous values that were used as we will soon have
            // new previous values
            Map<EnumerationLiteral, DomainValue> values =
                    new HashMap<EnumerationLiteral, DomainValue>(prevValues);

            prevValues.clear();

            // Reset to previous values
            for (Entry<EnumerationLiteral, DomainValue> entry : values
                    .entrySet()) {
                EnumerationLiteral literal = entry.getKey();

                if (!literal.eIsProxy()) {
                    // Take a copy of the old value as this will now be the
                    // value that we revert to
                    DomainValue oldDomainValue =
                            EcoreUtil.copy(EnumLitValueUtil
                                    .getDomainValue(literal));
                    prevValues.put(literal, oldDomainValue);
                    EnumLitValueUtil.setDomainValue(literal, entry.getValue());
                }
            }
        }

    }
}
