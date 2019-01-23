/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processwidget.adapters;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;

/**
 * When an object is drag-dropped onto the process diagram via the
 * LocalSelectionTransfer type (i.e. from navigator/explorer), the process
 * widget will ask the process widget adapter implementation for the target edit
 * part for a list of things to display in a drop-popup menu allowing the user
 * to select an action to perform (via the
 * {@link BaseProcessAdapter#getDropObjectPopupItems} method).
 * 
 * <p>
 * These actions are represented by this DropObjectPopupItem class which can
 * represent one of...
 * <li>An EMF Command that places to perform when the user chooses the popup
 * menu option (whose label is taken from the command label).</li>
 * <li>A list of diagram objects to create within drop-target container. The
 * process widget will apply this list of diagram objects as if a Paste had been
 * performed (with such inherited functionality as splicing into sequence flow,
 * resizing target container to accommodate new objects and so on. Therefore the
 * list of objects should be the same as that which the adapter implementation
 * considers to be valid for the
 * {@link ProcessDiagramAdapter#getPasteObjectsCommand(org.eclipse.emf.edit.domain.EditingDomain, Object, org.eclipse.draw2d.geometry.Point, java.util.Collection)}
 * method.
 * <li>A sub-menu containing other EMF command and create diagram object
 * entries.</li>
 * </p>
 * <p>
 * There are 3 different constructors representing these three different
 * drop-popup menu.
 * </p>
 * 
 * @author aallway
 * 
 */
public class DropObjectPopupItem {
    /** Drop popup menu item type. */
    public enum DropPopupItemType {
        EMF_COMMAND, CREATE_DIAGRAM_OBJECTS, SUB_MENU, SEPARATOR, CUSTOM_COMMAND
    }

    /**
     * This interface allows completely custom commands to be executed.
     * <p>
     * This allows the drop popup item to do something when menu item is
     * selected and return the actual popup command to execute adfter that is
     * done.
     * <p>
     * For example, in complicated scenarios (like create a joined sequence of
     * objects from drop objects, we may wish to allow user to select the actual
     * order of created objects. A DropPopupCustomCommand entry could be used to
     * popup a dialog which allows order selection. This then returns a
     * EMF_COMMAND or CREATE_DIAGRAM_OBJECTS type item to be executed.
     * 
     * @author aallway
     * 
     */
    public interface DropPopupCustomCommand {
        DropObjectPopupItem execute(Control hostControl);
    }

    private String label = null;

    private Image image = null;

    private DropPopupItemType popupItemType;

    /** Used for sub-menu entry's children. */
    private Collection<DropObjectPopupItem> subMenuItems = null;

    /** Used for an EMF Command action entry */
    private Command popupItemCommand = null;

    /** List of objects to create via the adapter paste diagram objects method */
    private Collection<? extends Object> createDiagramObjectsList = null;

    private DropPopupCustomCommand customCommand = null;

    public static final DropObjectPopupItem createSeparatorItem() {
        DropObjectPopupItem item = new DropObjectPopupItem();
        item.popupItemType = DropPopupItemType.SEPARATOR;
        return item;
    }

    /**
     * Construct a drop-popup item representing a menu separator.
     */
    private DropObjectPopupItem() {
    }

    /**
     * Construct a drop-popup item representing an EMF command to run when item
     * selected.
     * 
     * @param popupItemCommand
     * @param label
     * @param image
     */
    public static final DropObjectPopupItem createCommandItem(
            Command popupItemCommand, String label, Image image) {
        DropObjectPopupItem item = new DropObjectPopupItem();
        item.image = image;
        item.label = label;

        item.popupItemType = DropPopupItemType.EMF_COMMAND;
        item.popupItemCommand = popupItemCommand;

        return item;
    }

    /**
     * Construct a drop-popup item for wrapping the given diagram model-object
     * list into a paste style command (via the
     * {@link ProcessDiagramAdapter#getPasteObjectsCommand(org.eclipse.emf.edit.domain.EditingDomain, Object, org.eclipse.draw2d.geometry.Point, Collection)}
     * method.
     * 
     * @param createDiagramObjectsList
     *            List of objects that
     *            {@link ProcessDiagramAdapter#getPasteObjectsCommand(org.eclipse.emf.edit.domain.EditingDomain, Object, org.eclipse.draw2d.geometry.Point, Collection)}
     *            will consider valid to paste.
     * @param Label
     * @param image
     */
    public static final DropObjectPopupItem createCreateDiagramObjectsItem(
            Collection<Object> createDiagramObjectsList, String label,
            Image image) {
        DropObjectPopupItem item = new DropObjectPopupItem();
        item.image = image;
        item.label = label;

        item.popupItemType = DropPopupItemType.CREATE_DIAGRAM_OBJECTS;
        item.createDiagramObjectsList = createDiagramObjectsList;
        return item;
    }

    /**
     * Construct a drop-popup item for wrapping the given diagram model-object
     * list into a paste style command (via the
     * {@link ProcessDiagramAdapter#getPasteObjectsCommand(org.eclipse.emf.edit.domain.EditingDomain, Object, org.eclipse.draw2d.geometry.Point, Collection)}
     * method <b>AND</b> run wrap up an extra command after the passed command.
     * 
     * @param createDiagramObjectsList
     *            List of objects that
     *            {@link ProcessDiagramAdapter#getPasteObjectsCommand(org.eclipse.emf.edit.domain.EditingDomain, Object, org.eclipse.draw2d.geometry.Point, Collection)}
     *            will consider valid to paste.
     * @param Label
     * @param image
     */
    public static final DropObjectPopupItem createCreateDiagramObjectsItem(
            Collection<? extends Object> createDiagramObjectsList,
            Command appendCmd, String label, Image image) {
        DropObjectPopupItem item = new DropObjectPopupItem();
        item.image = image;
        item.label = label;

        item.popupItemType = DropPopupItemType.CREATE_DIAGRAM_OBJECTS;
        item.createDiagramObjectsList = createDiagramObjectsList;
        item.popupItemCommand = appendCmd;
        return item;
    }

    /**
     * Construct a drop-popup sub menu item containing the given items, with the
     * given icon.
     * 
     * @param subMenuItems
     * @param label
     * @param image
     */
    public static final DropObjectPopupItem createSubMenu(
            Collection<DropObjectPopupItem> subMenuItems, String label,
            Image image) {
        DropObjectPopupItem item = new DropObjectPopupItem();
        item.label = label;
        item.image = image;

        item.popupItemType = DropPopupItemType.SUB_MENU;
        item.subMenuItems = subMenuItems;

        return item;
    }

    /**
     * Construct a drop-popup sub menu item containing the given items
     * 
     * @param subMenuItems
     * @param label
     * @param image
     */
    public static final DropObjectPopupItem createSubMenu(
            Collection<DropObjectPopupItem> subMenuItems, String label) {
        DropObjectPopupItem item = new DropObjectPopupItem();
        item.label = label;

        item.popupItemType = DropPopupItemType.SUB_MENU;
        item.subMenuItems = subMenuItems;

        return item;
    }

    /**
     * Create a drop popup item that is an arbitrary, caller defined, command.
     * <p>
     * The command can do (almost) anything it wants via its
     * {@link DropPopupCustomCommand#execute(Control)} method. It can then
     * return null or a further DropPopupItemCommand (other than sub-menu) which
     * will be executed immediately.
     * 
     * @param customCommand
     * @param label
     * @param image
     * @return
     */
    public static final DropObjectPopupItem createCustomCommand(
            DropPopupCustomCommand customCommand, String label, Image image) {
        DropObjectPopupItem item = new DropObjectPopupItem();
        item.label = label;
        item.image = image;
        item.popupItemType = DropPopupItemType.CUSTOM_COMMAND;
        item.customCommand = customCommand;
        return item;
    }

    /**
     * Get the label for the popup menu item.
     * 
     * @return Text label for popup menu item.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Get the icon image for the popup menu item.
     * 
     * @return Image for popup menu item or null if none.
     */
    public Image getImage() {
        return image;
    }

    /**
     * Get the popup item type.
     * 
     * @return popup item type.
     */
    public DropPopupItemType getPopupItemType() {
        return popupItemType;
    }

    /**
     * @return the subMenuItems
     */
    public Collection<DropObjectPopupItem> getSubMenuItems() {
        return subMenuItems;
    }

    /**
     * @return the popupItemCommand
     */
    public Command getPopupItemCommand() {
        return popupItemCommand;
    }

    /**
     * @return the createDiagramObjectsList
     */
    public Collection<? extends Object> getCreateDiagramObjectsList() {
        return createDiagramObjectsList;
    }

    /**
     * @return the customCommand
     */
    public DropPopupCustomCommand getCustomCommand() {
        return customCommand;
    }

}
