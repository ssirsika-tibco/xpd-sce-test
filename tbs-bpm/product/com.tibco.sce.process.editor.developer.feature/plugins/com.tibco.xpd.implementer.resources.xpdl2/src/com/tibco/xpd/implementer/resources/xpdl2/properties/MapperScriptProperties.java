/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.mapper.Mapper;
import com.tibco.xpd.mapper.MapperItemListener;
import com.tibco.xpd.mapper.MapperUtil;
import com.tibco.xpd.mapper.MapperViewer;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.BaseProcessScriptSection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.util.ScriptInformationUtil;
import com.tibco.xpd.script.ui.api.AbstractScriptEditorSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public abstract class MapperScriptProperties extends BaseProcessScriptSection
        implements MapperItemListener {

    /** The mapper id. */
    public static final String MAPPER_ID = "propertySectionMapper"; //$NON-NLS-1$

    private Object selected;

    private MappingDirection mappingDirection;

    private MapperViewer relatedMapperViewer;

    /**
     * Constructor.
     * 
     * @param mappingDirection
     */
    public MapperScriptProperties(MappingDirection mappingDirection) {
        super(Xpdl2Package.eINSTANCE.getActivity());
        this.mappingDirection = mappingDirection;
    }

    @Override
    public void dispose() {
        Mapper.removeMappingListener(MapperUtil
                .getMapperId(getMappingDirection(), getInput()), this);
        super.dispose();
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        // Mapper.addMappingListener(MapperUtil.getMapperId(getMappingDirection()),
        // this);

        return super.doCreateControls(parent, toolkit);
    }

    @Override
    protected Command doGetCommand(Object obj) {
        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.MapperScriptProperties_EditScriptCommand);
        Activity activity = (Activity) getInput();
        if (selected instanceof ScriptInformation && activity != null) {
            EditingDomain ed = getEditingDomain();
            ScriptInformation information = (ScriptInformation) selected;
            if (information.eContainer() == null) {
                DirectionType dir;
                if (MappingDirection.IN.equals(mappingDirection)) {
                    dir = DirectionType.IN_LITERAL;
                } else {
                    dir = DirectionType.OUT_LITERAL;
                }
                information.setName(ScriptInformationUtil
                        .getNextScriptName(activity, dir));
                cmd.append(Xpdl2ModelUtil.getAddOtherElementCommand(ed,
                        activity,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script(),
                        information));
            }
        }
        cmd.append(super.doGetCommand(obj));
        return cmd;
    }

    /**
     * @return
     * @see com.com.tibco.xpd.script.ui.internal.BaseScriptSection#
     *      getCurrentSetScriptGrammarId()
     */
    @Override
    public String getCurrentSetScriptGrammarId() {
        Activity activity = (Activity) getInput();
        String grammarId = null;
        DirectionType dir;
        if (MappingDirection.IN.equals(mappingDirection)) {
            dir = DirectionType.IN_LITERAL;
        } else {
            dir = DirectionType.OUT_LITERAL;
        }
        grammarId = ScriptGrammarFactory.getScriptGrammar(activity, dir);
        if (grammarId == null) {
            grammarId = ScriptGrammarFactory.getDefaultScriptGrammar(activity);
        }
        return grammarId;
    }

    /**
     * @param grammar
     * @param editorSection
     * @return
     * @see com.com.tibco.xpd.script.ui.internal.BaseScriptSection#
     *      getSetScriptGrammarCommand(java.lang.String,
     *      com.tibco.xpd.processeditor.xpdl2.extensions.NewOrOldScriptSectionDelegate)
     */
    @Override
    protected Command getSetScriptGrammarCommand(String grammarId,
            AbstractScriptEditorSection editorSection) {
        return UnexecutableCommand.INSTANCE;
    }

    /**
     * @param src
     *            The object generating the event.
     * @param items
     * @see com.tibco.xpd.mapper.MapperItemListener#sourceItemsSelected(java.lang.Object[])
     */
    @Override
    public void sourceItemsSelected(Object src, Object[] items) {
        /**
         * Sid XPD-???? Ignore events from mappers we are no related to.
         */
        if ((src instanceof Mapper && relatedMapperViewer != null && src
                .equals(relatedMapperViewer.getControl())) || src == this) {


            if (items.length == 1) {
                selected = items[0];
            } else {
                selected = null;
            }
            setEnabled(selected instanceof ScriptInformation);
            if (selected instanceof ScriptInformation) {
                setScriptSelectionChanged(true);
            }
            refresh();
        }
    }

    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);
        Mapper.addMappingListener(MapperUtil.getMapperId(getMappingDirection(),
                getInput()), this);
        selected = null;
    }

    /**
     * @param src
     *            The object generating the event.
     * @param items
     * @see com.tibco.xpd.mapper.MapperItemListener#targetItemsSelected(java.lang.Object[])
     */
    @Override
    public void targetItemsSelected(Object src, Object[] items) {
    }

    /**
     * @return The editor input.
     * @see com.com.tibco.xpd.script.ui.internal.BaseScriptSection#
     *      getEditorInput()
     */
    @Override
    protected Object getEditorInput() {
        return selected;
    }

    public MappingDirection getMappingDirection() {
        return mappingDirection;
    }

    /**
     * Sid XPD-???? MapperScriptProperties (the normal class for mapping script
     * editor sections) hard-wires itsself as a listener based upon the input
     * and mapping direction alone and will respond to listener events for ANY
     * mapper for the same input and direction - not just the one it is
     * associated.
     * <p>
     * In order to prevent this happening, we need to connect the
     * MapperScriptProperties to the mapperViewer for the same section (so that
     * when it gets an event it can ignore it when it's from a different
     * mapper).
     * <p>
     * This method allows AbstractEditableMappingSection to do this.
     * <p>
     * NOTE: This could be done better by NOT having static listeners (see
     * MapperActivator) and having the MapperScriptProperties listen direct to
     * mapper viewer when we set the related mapper viewer, but that is too
     * large a change at the moment.
     * 
     * @param mapperViewer
     */
    public void setRelatedMapperViewer(MapperViewer mapperViewer) {
        this.relatedMapperViewer = mapperViewer;
    }

}
