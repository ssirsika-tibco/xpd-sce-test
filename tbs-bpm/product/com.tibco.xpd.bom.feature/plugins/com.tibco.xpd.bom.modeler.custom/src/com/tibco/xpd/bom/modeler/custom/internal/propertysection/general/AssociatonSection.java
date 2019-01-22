/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.propertysection.general;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.custom.commands.ModifyAssociationTypeCommand;
import com.tibco.xpd.bom.modeler.custom.commands.ModifyAssociationTypeCommand.AssociationType;
import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.AbstractGeneralSection;
import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * BOM property section for an {@link Association}. This will allow the setting
 * of the navigability and aggregation kind.
 * 
 * @author njpatel
 * 
 */
public class AssociatonSection extends AbstractGeneralSection {

    /**
     * Navigability options.
     */
    private enum NavigabilityDirection {
        BIDIRECTIONAL, SRC2TARGET, TARGET2SRC;
    }

    private Button navBtns[];

    private Button aggKindBtns[];

    private AggregationKind currentAssocType = null;

    @Override
    protected boolean shouldDisplay(EObject eo) {
        // For association class the Class section should be shown
        return eo instanceof Association && !(eo instanceof AssociationClass);
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = (Composite) super.doCreateControls(parent, toolkit);

        createLabel(root,
                toolkit,
                Messages.AssociatonSection_navigability_label);
        Composite navSection = createNavigabilitySection(root, toolkit);
        setLayoutData(navSection);

        createLabel(root,
                toolkit,
                Messages.AssociatonSection_aggregationKind_label);
        Composite section = createKindSection(root, toolkit);
        setLayoutData(section);

        return root;
    }

    /**
     * Create the navigability options section.
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    private Composite createNavigabilitySection(Composite parent,
            XpdFormToolkit toolkit) {
        Group root = toolkit.createGroup(parent, ""); //$NON-NLS-1$
        RowLayout layout = new RowLayout();
        layout.spacing = 20;
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        root.setLayout(layout);
        navBtns = new Button[3];

        navBtns[0] =
                toolkit.createButton(root,
                        Messages.AssociatonSection_biDirectional_radio,
                        SWT.RADIO);
        navBtns[0].setData(NavigabilityDirection.BIDIRECTIONAL);
        manageControl(navBtns[0]);
        navBtns[1] = toolkit.createButton(root, "", SWT.RADIO); //$NON-NLS-1$
        navBtns[1].setData(NavigabilityDirection.SRC2TARGET);
        manageControl(navBtns[1]);
        navBtns[2] = toolkit.createButton(root, "", SWT.RADIO); //$NON-NLS-1$
        navBtns[2].setData(NavigabilityDirection.TARGET2SRC);
        manageControl(navBtns[2]);

        return root;
    }

    /**
     * Create the Aggregation kind selection section.
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    private Composite createKindSection(Composite parent, XpdFormToolkit toolkit) {
        Group root = toolkit.createGroup(parent, ""); //$NON-NLS-1$
        RowLayout layout = new RowLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        layout.spacing = 20;
        root.setLayout(layout);
        aggKindBtns = new Button[3];

        aggKindBtns[0] =
                toolkit.createButton(root,
                        Messages.AssociatonSection_none_radio,
                        SWT.RADIO);
        aggKindBtns[0].setData(AssociationType.NORMAL);
        manageControl(aggKindBtns[0]);
        aggKindBtns[1] =
                toolkit.createButton(root,
                        Messages.AssociatonSection_composition_radio,
                        SWT.RADIO);
        aggKindBtns[1].setData(AssociationType.COMPOSITION_SRC);
        manageControl(aggKindBtns[1]);
        aggKindBtns[2] =
                toolkit.createButton(root,
                        Messages.AssociatonSection_aggregation_radio,
                        SWT.RADIO);
        aggKindBtns[2].setData(AssociationType.AGGREGATION_SRC);
        manageControl(aggKindBtns[2]);

        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        Command cmd = null;
        EObject input = getInput();
        if (input instanceof Association
                && ((Association) input).getMemberEnds().size() == 2) {
            Association assoc = (Association) input;

            if (obj instanceof Button) {
                Object data = ((Button) obj).getData();

                if (data instanceof NavigabilityDirection) {
                    // Direction changed
                    switch ((NavigabilityDirection) data) {
                    case BIDIRECTIONAL:
                        cmd = getBiDirectionalNavigabilityCommand(assoc);
                        break;
                    case SRC2TARGET:
                        cmd =
                                getDirectionalNavigabilityCommand(assoc, assoc
                                        .getMemberEnds().get(0), assoc
                                        .getMemberEnds().get(1));
                        break;
                    case TARGET2SRC:
                        cmd =
                                getDirectionalNavigabilityCommand(assoc, assoc
                                        .getMemberEnds().get(1), assoc
                                        .getMemberEnds().get(0));
                        break;
                    }
                } else if (data instanceof AssociationType) {
                    cmd =
                            new ModifyAssociationTypeCommand(
                                    (TransactionalEditingDomain) getEditingDomain(),
                                    (AssociationType) data, assoc);
                }
            }
        }
        return cmd;
    }

    @Override
    protected void doRefresh() {
        EObject input = getInput();
        if (input instanceof Association) {
            updateNavigabilityOptions((Association) input, navBtns);
            updateAggregationKind((Association) input);

            // Need to support things like the fetching section
            // changing based off of the association type changing
            AggregationKind newAssocKind =
                    UML2ModelUtil.getAggregationType((Association) input);
            if (currentAssocType == null) {
                currentAssocType = newAssocKind;
            } else {
                if (currentAssocType != newAssocKind) {
                    currentAssocType = newAssocKind;
                    refreshTabs();
                }
            }
        }
    }

    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        boolean refresh = false;
        EObject input = getInput();

        // Refresh if there are changes to Navigability or AggregationKind
        if (input instanceof Association) {
            for (Notification notification : notifications) {
                Object feature = notification.getFeature();

                if (UMLPackage.eINSTANCE.getAssociation_OwnedEnd()
                        .equals(feature)) {
                    refresh = true;
                } else if (UMLPackage.eINSTANCE.getProperty_Aggregation()
                        .equals(feature)) {
                    refresh = true;
                }
            }

            if (!refresh) {
                // Also listen to member ends for changes
                EList<Property> ends = ((Association) input).getMemberEnds();
                if (!ends.isEmpty()) {
                    for (Notification notification : notifications) {
                        if (notification.getNotifier() != null
                                && ends.contains(notification.getNotifier())) {
                            /*
                             * Aggregation changed so "force" the update of the
                             * property view's header to update the title
                             */
                            refreshHeader();
                            return true;
                        }
                    }
                }
            }

        }

        return refresh;
    }

    /**
     * Update the Aggregation kind selection from the association.
     * 
     * @param assoc
     */
    private void updateAggregationKind(Association assoc) {
        switch (UML2ModelUtil.getAggregationType(assoc)) {
        case COMPOSITE_LITERAL:
            setAggregationKind(AssociationType.COMPOSITION_SRC);
            break;
        case SHARED_LITERAL:
            setAggregationKind(AssociationType.AGGREGATION_SRC);
            break;
        default:
            setAggregationKind(AssociationType.NORMAL);
            break;
        }
    }

    /**
     * Set the Aggregation Kind selection.
     * 
     * @param type
     */
    private void setAggregationKind(AssociationType type) {
        if (aggKindBtns != null) {
            for (Button btn : aggKindBtns) {
                if (type.equals(btn.getData())) {
                    btn.setSelection(true);
                } else {
                    btn.setSelection(false);
                }
            }
        }
    }

    /**
     * Hide the given Aggregation Kind option.
     * 
     * @param type
     */
    private void hideAggregationKind(AssociationType type) {
        if (type != null) {
            for (Button btn : aggKindBtns) {
                if (type.equals(btn.getData())) {
                    btn.setVisible(false);
                } else {
                    btn.setVisible(true);
                }
            }
        }
    }

    /**
     * Update the options in the Navigability combo.
     * 
     * @param assoc
     * @param cmb
     */
    private void updateNavigabilityOptions(Association assoc, Button[] options) {
        if (assoc != null && options != null && !options[0].isDisposed()) {
            NavigabilityDirection direction =
                    NavigabilityDirection.BIDIRECTIONAL;
            EList<Property> ends = assoc.getMemberEnds();
            if (ends.size() == 2) {
                String end1Name = null;
                String end2Name = null;
                Type type = ends.get(0).getType();
                if (type != null) {
                    end1Name = type.getName();
                }
                type = ends.get(1).getType();
                if (type != null) {
                    end2Name = type.getName();
                }

                // Hide the options for now and show only when text is set
                setNavigabilityVisible(options,
                        NavigabilityDirection.SRC2TARGET,
                        false);
                setNavigabilityVisible(options,
                        NavigabilityDirection.TARGET2SRC,
                        false);
                if (end1Name != null && end1Name.length() > 0
                        && end2Name != null && end2Name.length() > 0) {
                    setNavigabilityLabel(options,
                            NavigabilityDirection.SRC2TARGET,
                            String.format(Messages.AssociatonSection_navigabilityDirection_label,
                                    end1Name,
                                    end2Name));
                    setNavigabilityVisible(options,
                            NavigabilityDirection.SRC2TARGET,
                            true);
                    if (!end1Name.equals(end2Name)) {
                        setNavigabilityLabel(options,
                                NavigabilityDirection.TARGET2SRC,
                                String.format(Messages.AssociatonSection_navigabilityDirection_label,
                                        end2Name,
                                        end1Name));
                        setNavigabilityVisible(options,
                                NavigabilityDirection.TARGET2SRC,
                                true);
                    }
                    EList<Property> ownedEnds = assoc.getOwnedEnds();

                    // Now select the set option
                    if (!ownedEnds.isEmpty()) {
                        if (ownedEnds.contains(assoc.getMemberEnds().get(0))) {
                            direction = NavigabilityDirection.SRC2TARGET;
                        } else {
                            direction = NavigabilityDirection.TARGET2SRC;
                        }
                    }
                }
            }
            // Select the navigability option
            for (Button btn : options) {
                btn.setSelection(direction.equals(btn.getData()));
            }
        }
    }

    /**
     * Set the navigability label of the given direction option.
     * 
     * @param options
     * @param direction
     * @param label
     */
    private void setNavigabilityLabel(Button[] options,
            NavigabilityDirection direction, String label) {
        if (options != null && direction != null && label != null) {
            for (Button btn : options) {
                if (direction.equals(btn.getData())) {
                    btn.setText(label);
                    break;
                }
            }
        }
    }

    /**
     * Set the visibility of the given navigability direction option.
     * 
     * @param options
     * @param direction
     */
    private void setNavigabilityVisible(Button[] options,
            NavigabilityDirection direction, boolean visible) {
        if (options != null && !options[0].isDisposed() && direction != null) {
            for (Button btn : options) {
                if (direction.equals(btn.getData())) {
                    btn.setVisible(visible);
                }
            }
        }
    }

    /**
     * Get command to set to bi-directional.
     * 
     * @param assoc
     * @return
     */
    private Command getBiDirectionalNavigabilityCommand(Association assoc) {
        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.AssociatonSection_setNavigability_command_label);
        if (assoc != null && assoc.getOwnedEnds().size() == 1
                && assoc.getMemberEnds().size() == 2) {
            Property src = assoc.getMemberEnds().get(0);
            Property tgt = assoc.getMemberEnds().get(1);

            if (src != null && tgt != null) {
                Property ownedProp = assoc.getOwnedEnds().get(0);
                if (ownedProp != null) {
                    // Update aggregation kind
                    if (ownedProp == tgt) {
                        // If composition or aggregation then swap to source
                        AggregationKind kind = src.getAggregation();

                        if (kind != AggregationKind.NONE_LITERAL) {
                            cmd.append(SetCommand.create(getEditingDomain(),
                                    tgt,
                                    UMLPackage.eINSTANCE
                                            .getProperty_Aggregation(),
                                    kind));
                            cmd.append(SetCommand.create(getEditingDomain(),
                                    src,
                                    UMLPackage.eINSTANCE
                                            .getProperty_Aggregation(),
                                    AggregationKind.NONE_LITERAL));
                        }
                    }

                    // Remove property from ownends
                    cmd.append(RemoveCommand.create(getEditingDomain(),
                            assoc,
                            UMLPackage.eINSTANCE.getAssociation_OwnedEnd(),
                            ownedProp));

                    Type addTo = null;
                    if (ownedProp == src) {
                        addTo = tgt.getType();
                    } else if (ownedProp == tgt) {
                        addTo = src.getType();
                    }

                    if (addTo != null) {
                        cmd.append(AddCommand
                                .create(getEditingDomain(),
                                        addTo,
                                        UMLPackage.eINSTANCE
                                                .getStructuredClassifier_OwnedAttribute(),
                                        ownedProp));
                    }
                }
            }
        }
        return cmd;
    }

    /**
     * Get command to set directional navigability, from source to target.
     * 
     * @param assoc
     * @param src
     * @param tgt
     * @return
     */
    private Command getDirectionalNavigabilityCommand(Association assoc,
            Property src, Property tgt) {
        CompoundCommand cmd = new CompoundCommand("Set Navigability"); //$NON-NLS-1$
        if (assoc != null && src != null && tgt != null) {
            EditingDomain ed = getEditingDomain();
            if (assoc.getOwnedMembers().isEmpty()) {
                // Changing from bi-directional
                if (src != null && src.eContainer() instanceof Class) {
                    cmd.append(RemoveCommand.create(ed,
                            src.eContainer(),
                            UMLPackage.eINSTANCE
                                    .getStructuredClassifier_OwnedAttribute(),
                            src));
                    cmd.append(AddCommand.create(ed,
                            assoc,
                            UMLPackage.eINSTANCE.getAssociation_OwnedEnd(),
                            src));

                }
            } else if (assoc.getOwnedMembers().contains(tgt)) {
                // Need to swap from target to source
                cmd.append(RemoveCommand.create(ed,
                        assoc,
                        UMLPackage.eINSTANCE.getAssociation_OwnedEnd(),
                        tgt));
                cmd.append(AddCommand.create(ed,
                        src.getType(),
                        UMLPackage.eINSTANCE
                                .getStructuredClassifier_OwnedAttribute(),
                        tgt));
                cmd.append(RemoveCommand.create(ed,
                        tgt.getType(),
                        UMLPackage.eINSTANCE
                                .getStructuredClassifier_OwnedAttribute(),
                        src));
                cmd.append(AddCommand.create(ed,
                        assoc,
                        UMLPackage.eINSTANCE.getAssociation_OwnedEnd(),
                        src));
            }
            // If source is Composite or Aggregate then swap to target
            AggregationKind kind = src.getAggregation();
            if (kind != AggregationKind.NONE_LITERAL) {
                cmd.append(SetCommand.create(ed,
                        src,
                        UMLPackage.eINSTANCE.getProperty_Aggregation(),
                        AggregationKind.NONE_LITERAL));
                cmd.append(SetCommand.create(ed,
                        tgt,
                        UMLPackage.eINSTANCE.getProperty_Aggregation(),
                        kind));
            }
        }
        return cmd;
    }

}
