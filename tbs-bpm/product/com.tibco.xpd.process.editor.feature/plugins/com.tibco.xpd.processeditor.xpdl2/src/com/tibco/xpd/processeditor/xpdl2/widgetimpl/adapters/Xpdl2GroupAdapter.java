/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.BaseGraphicalNodeAdapter;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;
import com.tibco.xpd.processwidget.adapters.GroupAdapter;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author wzurek
 */
public class Xpdl2GroupAdapter extends Xpdl2BaseArtifactAdapter implements
        GroupAdapter {

    private static class DeleteGroupCommand extends AbstractCommand {

        private final Artifact note;

        private CompoundCommand command;

        private final EditingDomain editingDomain;

        private final AdapterFactory adapterFactory;

        private Command removeCommand;

        public DeleteGroupCommand(EditingDomain editingDomain, Artifact note,
                AdapterFactory adapterFactory) {
            this.editingDomain = editingDomain;
            this.note = note;
            this.adapterFactory = adapterFactory;
            setLabel(Messages.Xpdl2GroupAdapter_DeleteGroup_menu);
        }

        @Override
        protected boolean prepare() {
            removeCommand = RemoveCommand.create(editingDomain, note);
            command = new CompoundCommand();
            return removeCommand.canExecute();
        }

        public void execute() {
            BaseGraphicalNodeAdapter fo =
                    (BaseGraphicalNodeAdapter) adapterFactory.adapt(note,
                            ProcessWidgetConstants.ADAPTER_TYPE);

            addAndExecute(fo.getSourceAssociations(), command);
            addAndExecute(fo.getTargetAssociations(), command);

            command.appendAndExecute(RemoveCommand.create(editingDomain, note));
        }

        private void addAndExecute(List list, CompoundCommand cmd) {
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                EObject eo = (EObject) iter.next();
                BaseProcessAdapter ad =
                        (BaseProcessAdapter) adapterFactory.adapt(eo,
                                ProcessWidgetConstants.ADAPTER_TYPE);
                cmd.appendAndExecute(ad.getDeleteCommand(editingDomain));
            }
        }

        public void redo() {
            command.redo();
        }

        @Override
        public void undo() {
            command.undo();
        }
    }

    public Command getDeleteCommand(EditingDomain editingDomain) {
        Artifact note = getGroup();
        return new DeleteGroupCommand(editingDomain, note, getAdapterFactory());
    }

    public String getId() {
        return getGroup().getId();
    }

    public String getName() {
        return getGroup().getName();
    }

    public String getTokenName() {
        return getName();
    }

    public Command getSetIdCommand(EditingDomain editingDomain, String newId) {
        return UnexecutableCommand.INSTANCE;
    }

    public Command getSetNameCommand(EditingDomain editingDomain, String newName) {
        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(Messages.Xpdl2GroupAdapter_SetTextGroup_menu);
        if (newName != null) {
            cmd.append(SetCommand.create(editingDomain,
                    getGroup(),
                    Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                    newName));
        }
        return cmd;
    }

    public String getFillColor() {
        return null;
    }

    public Command getSetFillColorCommand(EditingDomain editingDomain,
            String newColor) {
        return UnexecutableCommand.INSTANCE;
    }

    public String getLineColor() {
        NodeGraphicsInfo gi = getGraphicalInfo(getGroup());
        if (gi != null) {
            return gi.getBorderColor();
        }
        return null;
    }

    public Command getSetLineColorCommand(EditingDomain editingDomain,
            String newColor) {
        CompoundCommand cmd = new CompoundCommand();
        GraphicalNode act = getGraphicalNode();

        NodeGraphicsInfo gInfo =
                Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(act,
                        editingDomain,
                        cmd);

        cmd.append(SetCommand.create(editingDomain,
                gInfo,
                Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_BorderColor(),
                newColor));

        cmd.setLabel(Messages.Xpdl2GroupAdapter_SetLineColorGroup_menu);
        return cmd;
    }

    /**
     * @return
     */
    private Artifact getGroup() {
        return (Artifact) getTarget();
    }

}
