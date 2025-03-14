/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.n2.resources.postimport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Extension;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLFactory;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.modeler.custom.enumlitext.util.EnumLitValueUtil;
import com.tibco.xpd.bom.modeler.custom.terminalstates.TerminalStateProperties;
import com.tibco.xpd.bom.modeler.diagram.part.custom.utils.ClassHeaderColourUtil;
import com.tibco.xpd.bom.modeler.diagram.util.BOMDiagramModelUtil;
import com.tibco.xpd.bom.modeler.diagram.util.BOMDiagramModelUtil.BOMDiagramViewModelType;
import com.tibco.xpd.bom.resources.migration.IBOMMigration;
import com.tibco.xpd.bom.resources.ui.bomnotation.ShapeGradientStyle;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.n2.resources.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Performs migration changes to BOMs imported from AMX BPM Studio
 * 
 * This is run for FORMAT VERSION 1000 (which marks the transition from last AMX BPM (5) before first ACE (1000)
 * version.
 *
 * @author aallway
 * @since 9 Apr 2019
 */
public class Bpm2CeBomMigration implements IBOMMigration {

    public Bpm2CeBomMigration() {
    }

    @Override
    public Command getMigrationCommand(TransactionalEditingDomain domain,
            Model model) {

        CompoundCommand cmd = new CompoundCommand();

        /*
         * Remove XSD stereotype.
         */
        Command c = removeXsdStereoType(domain, model);
        if (c != null) {
            cmd.append(c);
        }

        /*
         * Switch integer attributes to Decimals with 0 max decimals.
         */
        c = refactorNumberAttributes(domain, model);
        if (c != null) {
            cmd.append(c);
        }

        /*
         * Sid ACE-4002 Add missing aggregation="composite" to non-association linkl attributes (these are missing from
         * very old BOMs)
         */
        c = refactorMissingAttributeAggregation(domain, model);
        if (c != null) {
            cmd.append(c);
        }

        /*
         * Add the global data profile - all BOMs in ACE should be in Biz Data
         * projects and hence should have global data profile set (even if
         * they're not currently in biz data projects they'll have to be moved
         * there).
         */
        c = addGlobalDataProfile(domain, model);
        if (c != null) {
            cmd.append(c);
        }

        /*
         * Remove class operations.
         */
        c = removeClassOperations(domain, model);
        if (c != null) {
            cmd.append(c);
        }

        /*
         * Convert any Global Classes to Local Classes
         */
        c = convertGlobalClassesToLocal(domain, model);
        if (c != null) {
            cmd.append(c);
        }

		/*
		 * ACE-7093: Remove the generalization
		 */
		c = removeGeneralization(domain, model);
		if (c != null)
		{
			cmd.append(c);
		}

		/*
		 * Sid ACE-8369 make all case-id attributes Mandatory
		 */
		c = makeCaseIdsMandatory(domain, model);

		if (c != null)
		{
			cmd.append(c);
		}

		/*
		 * Sid ACE-8366 / Sid ACE-8371 Check for Case Classes with missing case-state attribute and add a case state and
		 * case-states enumeration for each.
		 */
		c = addMissingCaseStates(domain, model);

		if (c != null)
		{
			cmd.append(c);
		}

        return !cmd.isEmpty() ? cmd : null;
    }

    /**
	 * Sid ACE-8366 / Sid ACE-8371 Check for Case Classes with missing case-state attribute and add a case state and
	 * case-states enumeration for each.
	 * 
	 * Also sets existing 4.x case-state attributes to mandatory.
	 * 
	 * @param domain
	 * @param model
	 * 
	 * @return Command to add missing case-states or <code>null</code> if nothing to do.
	 */
	private Command addMissingCaseStates(TransactionalEditingDomain domain, Model model)
	{
		CompoundCommand cmd = new CompoundCommand();

		EList<Element> allOwnedElements = model.allOwnedElements();

		for (Element element : allOwnedElements)
		{
			if (element instanceof org.eclipse.uml2.uml.Class)
			{
				org.eclipse.uml2.uml.Class clazz = (Class) element;
				if (BOMGlobalDataUtils.isCaseClass(clazz))
				{
					Property caseStateProperty = null;

					for (Property property : clazz.getOwnedAttributes())
					{
						if (BOMGlobalDataUtils.isCaseState(property))
						{
							caseStateProperty = property;
							break;
						}
					}

					if (caseStateProperty == null)
					{
						/* Add missing case state and and a case-states enumeration for this case class. */
						cmd.append(addCaseStateForClass(domain, clazz));
					}
					else
					{
						/* Else set the case state to mandatory for v5.x */
						cmd.append(getSetPropertyMandatoryCommand(domain, caseStateProperty));
					}
				}
			}
		}

		return !cmd.isEmpty() ? cmd : null;
	}

	/**
	 * Sid ACE-8366 / Sid ACE-8371
	 * 
	 * Create a "Case Name Case States" and add a case state attribute of that type to the given case class. Then
	 * finally set the terminal state to 'Complete'
	 * 
	 * @param domain
	 * @param caseClazz
	 */
	private Command addCaseStateForClass(TransactionalEditingDomain domain, final Class caseClazz)
	{
		RecordingCommand cmd = new RecordingCommand(domain)
		{
			@Override
			protected void doExecute()
			{
				/*
				 * Sid ACE-8366 Create Case States enumeration for this case class.
				 */
				Enumeration caseStatesEnum = UMLFactory.eINSTANCE.createEnumeration();
				caseClazz.getPackage().getPackagedElements().add(caseStatesEnum);

				caseStatesEnum.setName(caseClazz.getName() + "CaseStates"); //$NON-NLS-1$
				PrimitivesUtil.setDisplayLabel(caseStatesEnum,
						PrimitivesUtil.getDisplayLabel(caseClazz) + " " //$NON-NLS-1$
								+ Messages.Bpm2CeBomMigration_CaseStatesSuffix_label);

				EnumerationLiteral runningLiteral = UMLFactory.eINSTANCE.createEnumerationLiteral();
				caseStatesEnum.getOwnedLiterals().add(runningLiteral);
				runningLiteral.setName("RUNNING"); //$NON-NLS-1$
				PrimitivesUtil.setDisplayLabel(runningLiteral, "RUNNING"); //$NON-NLS-1$
				EnumLitValueUtil.setSingleValue(runningLiteral, runningLiteral.getName());

				EnumerationLiteral completeLiteral = UMLFactory.eINSTANCE.createEnumerationLiteral();
				caseStatesEnum.getOwnedLiterals().add(completeLiteral);
				completeLiteral.setName("COMPLETE"); //$NON-NLS-1$
				PrimitivesUtil.setDisplayLabel(completeLiteral, "COMPLETE"); //$NON-NLS-1$
				EnumLitValueUtil.setSingleValue(completeLiteral, completeLiteral.getName());

				/*
				 * Add diagram visual element for new Enumeration so we can position it
				 */
				View clazzViewModel = BOMDiagramModelUtil.getDiagramElementFromModel(domain, caseClazz);
				
				if (clazzViewModel instanceof Node)
				{
					Node enumDiagramNode = BOMDiagramModelUtil.createDiagramNode((View) clazzViewModel.eContainer(),
							caseStatesEnum,
							BOMDiagramViewModelType.enumeration);

					if (((Node) clazzViewModel).getLayoutConstraint() instanceof Bounds)
					{
						Bounds caseClazzBounds = (Bounds) ((Node) clazzViewModel).getLayoutConstraint();

						Bounds enumBounds = (Bounds) enumDiagramNode
								.createLayoutConstraint(NotationPackage.eINSTANCE.getBounds());

						enumBounds.setX(caseClazzBounds.getX());
						enumBounds.setY(caseClazzBounds.getY() - 75);
					}
				}

				/*
				 * Sid ACE-8371 Create case state property
				 */
				Property caseStateProperty = UMLFactory.eINSTANCE.createProperty();
				caseClazz.getOwnedAttributes().add(caseStateProperty);

				/*
				 * Sid ACE-8588 must set aggregation type as well, otherwise Bom->Cdm model transformation doesn't pick
				 * up the attribute (and manually created attributes get this set, so we should do the same here of
				 * coursed)
				 */
				caseStateProperty.setAggregation(AggregationKind.COMPOSITE_LITERAL);

				caseStateProperty.setType(caseStatesEnum);
				caseStateProperty.setName(caseClazz.getName() + "caseState"); //$NON-NLS-1$
				caseStateProperty.setLower(1);
				caseStateProperty.setUpper(1);
				PrimitivesUtil.setDisplayLabel(caseStateProperty, Messages.Bpm2CeBomMigration_CaseStateSuffix_label);

				/* Set as a case-state property... */
				if (GlobalDataProfileManager.getInstance().add(caseStateProperty, StereotypeKind.CASE_STATE))
				{

					/* And then add the "Complete" state as a terminate state... */
					Stereotype caseStateStereotype = BOMGlobalDataUtils.getCaseStateStereotype();

					if (caseStateStereotype != null)
					{
						caseStateProperty.setValue(caseStateStereotype, TerminalStateProperties.BOM_TERMINAL_STATES,
								java.util.Collections.singletonList(completeLiteral));
					}
				}
			}
		};

		return cmd;
	}

	/**
	 * Switch any Case Identifier attributes in the BOM to Mandatory if they are not already.
	 * 
	 * @param domain
	 * @param model
	 * 
	 * @return Command or <code>null</code> if no changes necessary.
	 */
	private Command makeCaseIdsMandatory(TransactionalEditingDomain domain, Model model)
	{
		CompoundCommand cmd = new CompoundCommand();

		EList<Element> allOwnedElements = model.allOwnedElements();

		for (Element element : allOwnedElements)
		{
			/*
			 * Switch integer properties to FixedPoint with zero decimals.
			 */
			if (element instanceof Property)
			{
				final Property property = (Property) element;

				if (BOMGlobalDataUtils.isCID(property))
				{
					if (property.getLower() != 1 || property.getUpper() != 1) {
						cmd.append(getSetPropertyMandatoryCommand(domain, property));
					}
				}
			}
		}

		return !cmd.isEmpty() ? cmd : null;
	}

	/**
	 * Set the given property to single instance, Mandatory
	 * 
	 * @param domain
	 * @param property
	 * 
	 * @return Command to set the given property to single instance, Mandatory
	 */
	private RecordingCommand getSetPropertyMandatoryCommand(TransactionalEditingDomain domain, Property property)
	{
		RecordingCommand c = new RecordingCommand(domain)
		{

			@Override
			protected void doExecute()
			{
				property.setLower(1);
				property.setUpper(1);

			}
		};
		return c;
	}

	/**
	 * Create command to remove the XSD Notation Profile (for boms generated from WSDL/XSD).
	 * 
	 * @param domain
	 * @param model
	 * @return Command or <code>null</code> is XsdNotationProfile not applied to this BOM.
	 */
    private Command removeXsdStereoType(TransactionalEditingDomain domain,
            final Model model) {
        Command result = null;

        final Profile xsdNotationProfile =
                model.getAppliedProfile(BOMUtils.XSD_NOTATION_PROFILE);

        if (xsdNotationProfile != null) {
            result = new RecordingCommand(domain,
                    Messages.Bpm2CeBomMigration_RemoveXSDNotationProfile_cmd) {
                @Override
                protected void doExecute() {
                    model.unapplyProfile(xsdNotationProfile);
                }
            };

        }

        return result;
    }

    /**
     * Switch integer attributes/primitive-types to Decimal, Fixed Point with 0
     * max decimals.
     * 
     * @param domain
     * @param model
     * @return Command or <code>null</code> if no changes to make.
     */
    private Command refactorNumberAttributes(TransactionalEditingDomain domain,
            Model model) {
        CompoundCommand cmd = new CompoundCommand();

        /* Get the base Decimal primitive type. */
        ResourceSet rs = XpdResourcesPlugin.getDefault().getEditingDomain()
                .getResourceSet();

        PrimitiveType decimalType =
                PrimitivesUtil.getStandardPrimitiveTypeByName(rs,
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);

        PrimitiveType integerType =
                PrimitivesUtil.getStandardPrimitiveTypeByName(rs,
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);

        EList<Element> allOwnedElements = model.allOwnedElements();

        for (Element element : allOwnedElements) {
            /*
             * Switch integer properties to FixedPoint with zero decimals.
             */
            if (element instanceof Property) {
                final Property property = (Property) element;

                if (property.getType() != null
                        && PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME
                                .equals(property.getType().getName())) {

                    /*
                     * Switch Integer property type to Decimal, Fixed Point, 0
                     * decimals.
                     */
                    Command c = new RecordingCommand(domain) {

                        @Override
                        protected void doExecute() {
                            property.setType(decimalType);

                            PrimitivesUtil.setFacetPropertyValue(decimalType,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE,
                                    PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT,
                                    property);

                            PrimitivesUtil.setFacetPropertyValue(decimalType,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES,
                                    0,
                                    property);

                            /*
                             * Move the old integer constraints to their decimal
                             * equivalents
                             */
                            moveConstraint(property,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_DEFAULT_VALUE,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_DEFAULT_VALUE,
                                    decimalType);

                            moveConstraint(property,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LOWER,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER,
                                    decimalType);

                            moveConstraint(property,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_UPPER,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER,
                                    decimalType);
                        }

                        /**
                         * Move the srcConstraint to the tgtConstraint
                         * 
                         * @param property
                         * @param srcConstraint
                         * @param tgtConstraint
                         * @param propertyType
                         */
                        private void moveConstraint(Property property,
                                String srcConstraint, String tgtConstraint,
                                PrimitiveType propertyType) {
                            Object srcValue =
                                    PrimitivesUtil.getFacetPropertyValue(
                                            (PrimitiveType) property.getType(),
                                            srcConstraint,
                                            property);

                            if (srcValue != null) {
                                PrimitivesUtil.setFacetPropertyValue(
                                        propertyType,
                                        tgtConstraint,
                                        srcValue,
                                        property);

                                PrimitivesUtil.setFacetPropertyValue(
                                        propertyType,
                                        srcConstraint,
                                        null,
                                        property);
                            }
                        }
                    };

                    cmd.append(c);
                }

            }
            /*
             * Switch integer properties to FixedPoint with zero decimals.
             */
            else if (element instanceof PrimitiveType) {
                final PrimitiveType primitiveType = (PrimitiveType) element;

                /*
                 * If this is a primitive type that directly generalizes the
                 * base Integer primitive type then switch it to the base
                 * Decimal Fixed Point type and set decimals to zero.
                 */
                EList<Classifier> generals = primitiveType.getGenerals();

                if (generals != null && generals.size() == 1
                        && integerType.equals(generals.get(0))) {
                    /*
                     * Switch Integer property type to Decimal, Fixed Point, 0
                     * decimals.
                     */
                    Command c = new RecordingCommand(domain) {

                        @Override
                        protected void doExecute() {
                            primitiveType.getGenerals().clear();
                            primitiveType.getGenerals().add(decimalType);

                            PrimitivesUtil.setFacetPropertyValue(primitiveType,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE,
                                    PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT);

                            PrimitivesUtil.setFacetPropertyValue(primitiveType,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES,
                                    0);
                            /*
                             * Move the old integer constraints to their decimal
                             * equivalents
                             */
                            moveConstraint(primitiveType,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_DEFAULT_VALUE,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_DEFAULT_VALUE,
                                    decimalType);

                            moveConstraint(primitiveType,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LOWER,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER,
                                    decimalType);

                            moveConstraint(primitiveType,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_UPPER,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER,
                                    decimalType);
                        }

                        /**
                         * Move the srcConstraint to the tgtConstraint
                         * 
                         * @param primitiveType
                         * @param srcConstraint
                         * @param tgtConstraint
                         * @param decimalType
                         */
                        private void moveConstraint(PrimitiveType primitiveType,
                                String srcConstraint, String tgtConstraint,
                                PrimitiveType decimalType) {
                            Object srcValue = PrimitivesUtil
                                    .getFacetPropertyValue(primitiveType,
                                            srcConstraint);

                            if (srcValue != null) {
                                PrimitivesUtil.setFacetPropertyValue(
                                        primitiveType,
                                        tgtConstraint,
                                        srcValue);

                                PrimitivesUtil.setFacetPropertyValue(
                                        primitiveType,
                                        srcConstraint,
                                        null);
                            }
                        }
                    };

                    cmd.append(c);
                }

            }

        }

        return !cmd.isEmpty() ? cmd : null;
    }

    /**
     * Add the global data profile - all BOMs in ACE should be in Biz Data
     * projects and hence should have global data profile set (even if they're
     * not currently in biz data projects they'll have to be moved there).
     * 
     * @param domain
     * @param model
     * 
     * @return Command to add the global data profile or null if already set
     */
    private Command addGlobalDataProfile(TransactionalEditingDomain domain,
            Model model) {
        if (!GlobalDataProfileManager.getInstance().isGlobalDataBOM(model)) {
            return new RecordingCommand(domain) {

                @Override
                protected void doExecute() {
                    GlobalDataProfileManager.getInstance()
                            .applyGlobalDataProfile(model);
                }
            };
        }
        return null;
    }

    /**
     * Remove all operations from (usually ex-generated) BOM classes
     * 
     * @param domain
     * @param model
     * 
     * @return Command to remove the operations from classes in the model
     */
    private Command removeClassOperations(TransactionalEditingDomain domain,
            Model model) {
        return new RecordingCommand(domain) {

            @Override
            protected void doExecute() {
                EList<Element> allOwnedElements = model.allOwnedElements();

                for (Element element : allOwnedElements) {
                    if (element instanceof org.eclipse.uml2.uml.Class) {
                        org.eclipse.uml2.uml.Class clazz =
                                (org.eclipse.uml2.uml.Class) element;

                        clazz.getOwnedOperations().clear();
                    }
                }
            }
        };
    }

    /**
     * Converts Global Classes to Local Classes
     * 
     * @param domain
     *            The editing domain.
     * @param model
     *            The BOM UML model.
     * @return The command to convert the classes.
     */
    private Command convertGlobalClassesToLocal(
            TransactionalEditingDomain domain, Model model) {
        return new RecordingCommand(domain) {
            @Override
            protected void doExecute() {
                EList<Element> allOwnedElements = model.allOwnedElements();
                EList<EObject> contents = model.eResource().getContents();
                Diagram diagram = null;
                for (EObject content : contents) {
                    if (content instanceof Diagram) {
                        diagram = (Diagram) content;
                    }
                }

                for (Element element : allOwnedElements) {
                    if (element instanceof org.eclipse.uml2.uml.Class) {
                        org.eclipse.uml2.uml.Class clazz =
                                (org.eclipse.uml2.uml.Class) element;
                        GlobalDataProfileManager pm =
                                GlobalDataProfileManager.getInstance();
                        if (pm.isGlobal(clazz)) {
                            ClassHeaderColourUtil chcu =
                                    new ClassHeaderColourUtil();
                            ShapeGradientStyle sgs =
                                    chcu.getShapeGradientStyleForClass(clazz,
                                            diagram);
                            boolean resetColour =
                                    chcu.isDefaultColour(clazz, sgs);
                            pm.remove(clazz, StereotypeKind.GLOBAL);
                            if (resetColour) {
                                sgs.setGradStartColor(
                                        FigureUtilities.RGBToInteger(
                                                chcu.getDefaultColour(clazz)));
                            }
                        }
                    }
                }
            }
        };
    }

    /**
     * Some older BOM models (pre V4.0 Studio I think) did not set aggregation="composite" on standard attributes (non
     * association). This messes up bom->cdm & bom-js transforms and is inconsistent with attributes created in
     * current/later version.
     * 
     * So this function looks for ownedAttribute's that have aggregation=none and no association - these should be
     * compositions.
     * 
     * @param domain
     * @param model
     * @return Command or <code>null</code> if no changes to make.
     */
    private Command refactorMissingAttributeAggregation(TransactionalEditingDomain domain, Model model) {
        CompoundCommand cmd = new CompoundCommand();

        /* Get the base Decimal primitive type. */
        ResourceSet rs = XpdResourcesPlugin.getDefault().getEditingDomain().getResourceSet();

        PrimitiveType decimalType =
                PrimitivesUtil.getStandardPrimitiveTypeByName(rs, PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);

        PrimitiveType integerType =
                PrimitivesUtil.getStandardPrimitiveTypeByName(rs, PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);

        EList<Element> allOwnedElements = model.allOwnedElements();

        for (Element element : allOwnedElements) {
            /*
             * Switch integer properties to FixedPoint with zero decimals.
             */
            if (element instanceof Property) {
                final Property property = (Property) element;

                if (AggregationKind.NONE_LITERAL.equals(property.getAggregation())
                        && property.getAssociation() == null) {
                    /*
                     * Aggregation=none and NOT a case class association so we need to set the plain old attribute as a
                     * composition.
                     */
                    cmd.append(new RecordingCommand(domain) {
                        @Override
                        protected void doExecute() {
                            property.setAggregation(AggregationKind.COMPOSITE_LITERAL);
                        }
                    });
                }
            }
        }

        return !cmd.isEmpty() ? cmd : null;
    }

	/**
	 * ACE-7093: Remove the generalization.
	 * 
	 * Before removing the generalization relationship, all the derived attributes from general (super) class should be
	 * copied to generalized (special) class. After coping the derived attributes, delete the generalization
	 * relationship between the classes. While coping the attributes, copy all the stereotypes applied on the source
	 * attribute to destination.
	 * 
	 * @param domain
	 *            The editing domain.
	 * @param model
	 *            The BOM UML model.
	 * @return The command to convert the classes.
	 */
	private Command removeGeneralization(TransactionalEditingDomain domain, Model model)
	{

		return new RecordingCommand(domain)
		{
			// Map to cache class<-->Attribute info object information.
			Map<org.eclipse.uml2.uml.Class, Set<AttributeCopier>> newAttributesInfoMap = new HashMap<>();

			@Override
			protected void doExecute()
			{
				/*
				 * Search through all the classes to create map of additional attributes which need to be created before
				 * removing the generalization.
				 */

				EList<Element> allOwnedElements = model.allOwnedElements();

				List<org.eclipse.uml2.uml.Class> classes = new ArrayList<>();

				for (Element element : allOwnedElements)
				{
					if (element instanceof org.eclipse.uml2.uml.Class)
					{
						org.eclipse.uml2.uml.Class clazz = (org.eclipse.uml2.uml.Class) element;
						classes.add(clazz);

						// Get all the inherited members from super classes hierarchies.
						EList<NamedElement> inheritedMembers = clazz.getInheritedMembers();

						if (!inheritedMembers.isEmpty())
						{
							Set<AttributeCopier> attributeCopiers = new HashSet<>();
							for (NamedElement srcProperty : inheritedMembers)
							{
								if (srcProperty instanceof Property)
								{
									attributeCopiers.add(new AttributeCopier((Property) srcProperty));
								}
							}
							newAttributesInfoMap.put(clazz, attributeCopiers);
						}
					}
				}

				// Remove all the generalization
				for (org.eclipse.uml2.uml.Class clazz : classes)
				{
					clazz.getGeneralizations().clear();
				}

				// Now iterate over the map to copy attributes with stereotypes.
				for (Entry<org.eclipse.uml2.uml.Class, Set<AttributeCopier>> entry : newAttributesInfoMap.entrySet())
				{
					for (AttributeCopier attributeCopier : entry.getValue())
					{
						attributeCopier.copyToClass(entry.getKey());
					}
				}
			}
		};
	}

	/**
	 * Final class to provide functionality to copy the attribute to destination class.
	 *
	 *
	 * @author ssirsika
	 * @since 01-Nov-2023
	 */
	private final class AttributeCopier
	{
		/**
		 * Source attribute
		 */
		private Property srcAttribute;

		public AttributeCopier(Property aSrcAttribute)
		{
			this.srcAttribute = aSrcAttribute;
		}

		/**
		 * Copy the belonging attribute to passed target class.
		 * 
		 * @param aClass
		 *            Target class to which attribute need to be copied.
		 */
		public void copyToClass(org.eclipse.uml2.uml.Class aClass)
		{
			// Create a copy of an attribute
			Property newAttribute = EcoreUtil.copy(this.srcAttribute);

			// Set the new attribute to class
			aClass.getOwnedAttributes().add(newAttribute);

			/*
			 * Get the all the applied stereotypes on source attribute and apply them on the new (destination)
			 * attribute.
			 */
			EList<Stereotype> srcAppliedStereotypes = srcAttribute.getAppliedStereotypes();
			for (Stereotype srcStereotype : srcAppliedStereotypes)
			{
				/*
				 * Check the existence of matching applicable stereotype present in the new (destination) attribute.
				 */
				Stereotype applicableStereotype = newAttribute
						.getApplicableStereotype(srcStereotype.getQualifiedName());

				if (applicableStereotype != null)
				{

					// apply stereotype to new attribute
					newAttribute.applyStereotype(applicableStereotype);

					/*
					 * Copy all the facet properties values (except mataclass properties) from source stereotype and set
					 * them to the new attribute's stereotype.
					 */
					EList<Property> srcFacetProperties = srcStereotype.getOwnedAttributes();
					for (Property srcFacetAttribute : srcFacetProperties)
					{
						String name = srcFacetAttribute.getName();
						/*
						 * We need to skip the properties like 'base_Property' or 'base_NamedElement' (i.e. metaclass
						 * properties ) as those properties will be correctly set once the stereotype is applied to the
						 * new (destination) attribute.
						 */
						if (!name.startsWith(Extension.METACLASS_ROLE_PREFIX))
						{
							Object value = srcAttribute.getValue(srcStereotype, name);
							newAttribute.setValue(applicableStereotype, name, value);
						}
					}
				}
			}
		}
	}
}
