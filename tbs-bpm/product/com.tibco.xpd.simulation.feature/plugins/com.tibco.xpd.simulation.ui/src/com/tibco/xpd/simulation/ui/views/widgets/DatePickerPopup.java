/**
 * 
 */
package com.tibco.xpd.simulation.ui.views.widgets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.gface.date.DatePicker;
import com.gface.date.DatePickerCombo;
import com.gface.date.DateSelectedEvent;
import com.gface.date.DateSelectionListener;

/**
 * TODO comment me!
 * @author mmaciuki
 *
 */
public class DatePickerPopup {

    private Control datePickerControl;
    private Shell popup;
    private DatePicker datePicker;
    private List dateSelectionListeners;

    /**
     * TODO comment me!
     * @param composite2
     */
    public DatePickerPopup() {
        this.dateSelectionListeners=new ArrayList();
    }
    
    private Control createDatePicker(Composite parent) {
        Composite result=new Composite(parent,SWT.NONE);
        result.setLayout(new RowLayout(SWT.HORIZONTAL));
        
        datePicker = new DatePicker(result,SWT.FLAT | SWT.BORDER);
        datePicker.setLocale(Locale.US);
        Display display=Display.getDefault();
        datePicker.setSelectedDateBackgroud(display.getSystemColor(SWT.COLOR_BLACK));
        datePicker.setSelectedDateForeground(display.getSystemColor(SWT.COLOR_WHITE));
        datePicker.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
        datePicker.setForeground(display.getSystemColor(SWT.COLOR_BLACK));
        
        for (Iterator iter = dateSelectionListeners.iterator(); iter.hasNext();) {
            DateSelectionListener listener = (DateSelectionListener) iter.next();
            datePicker.addDateSelectionListener(listener);
        }
        
        return result;
    }
    
    private synchronized void show(int xCoord, int yCoord){
        Display display = Display.getDefault();
        Shell activeShell = display.getActiveShell();
        Rectangle shellBounds = activeShell.getBounds();
        initPopup();
        Point pickerSize = this.datePickerControl.computeSize(SWT.DEFAULT, SWT.DEFAULT, false);
        int height=pickerSize.y;
        int width=pickerSize.x;
        this.datePickerControl.setBounds(0,0,width,height);
        int yLocation=yCoord;
        if(yLocation-height<0){
            yLocation=height;
        }
        int xLocation=xCoord;
        if(xLocation-width>shellBounds.width){
            yLocation=width;
        }
        popup.setBounds(xLocation, yLocation, width, height);
        popup.setVisible(true);
        this.datePickerControl.setFocus();
    }

    /**
     * TODO comment me!
     * @param activeShell
     */
    private synchronized void initPopup() {
        Display display = Display.getDefault();
        Shell activeShell = display.getActiveShell();
        if(this.popup==null){
            popup = new Shell(activeShell, SWT.NO_TRIM | SWT.ON_TOP);
            if(this.datePickerControl==null){
                this.datePickerControl=createDatePicker(popup);
            }
        }
    }

    private synchronized void hide(){
        popup.setVisible(false);
    }
    
    private Point getAbsoluteLocation(Control c) {
        Point result = c.getLocation();
        Composite parent = c.getParent();
        if(!(parent instanceof Shell)){
            Point parentLocation=getAbsoluteLocation(parent);
            result.x+=parentLocation.x;
            result.y+=parentLocation.y;
        }else{
            Point parentLocation=parent.getLocation();
            result.x+=parentLocation.x;
            result.y+=parentLocation.y;
        }
        
        return result;
    }

    public void addDateSelectionListener(DateSelectionListener l){
        if(this.datePicker==null){
            dateSelectionListeners.add(l);
        }else{
            this.datePicker.addDateSelectionListener(l);
        }
    }
    
    public Button createButton(Composite parent){
        Button result;
        result=new Button(parent,SWT.ARROW | SWT.DOWN );
        
        DatePickerButtonListener listener= new DatePickerButtonListener(result);
        result.addMouseListener(listener);
        addDateSelectionListener(listener);
        return result;
    }


    class DatePickerButtonListener implements MouseListener, DateSelectionListener{

        boolean pickerVisible;
        private final Control relativeTo;
        
        /**
         * TODO comment me!
         * @param relativeTo 
         */
        public DatePickerButtonListener(Control relativeTo) {
            this.relativeTo = relativeTo;
            pickerVisible=false;
        }
        
        /**
         * TODO comment me!
         * @see org.eclipse.swt.events.MouseListener#mouseDoubleClick(org.eclipse.swt.events.MouseEvent)
         */
        public void mouseDoubleClick(MouseEvent e) {/* do nothing */}

        /**
         * TODO comment me!
         * @see org.eclipse.swt.events.MouseListener#mouseDown(org.eclipse.swt.events.MouseEvent)
         */
        public void mouseDown(MouseEvent e) {
            if(pickerVisible){
                hide();
                pickerVisible=false;
            }else{
                Point absoluteLocation = getAbsoluteLocation(relativeTo);
                int offset = relativeTo.computeSize(SWT.DEFAULT,SWT.DEFAULT,false).x;
                show(absoluteLocation.x+offset,absoluteLocation.y);
                pickerVisible=true;
            }
        }

        /**
         * TODO comment me!
         * @see org.eclipse.swt.events.MouseListener#mouseUp(org.eclipse.swt.events.MouseEvent)
         */
        public void mouseUp(MouseEvent e) {/* do nothing */}

        /**
         * TODO comment me!
         * @see com.gface.date.DateSelectionListener#dateSelected(com.gface.date.DateSelectedEvent)
         */
        public void dateSelected(DateSelectedEvent e) {
            if(pickerVisible){
                hide();
                pickerVisible=false;
            }
        }
    }
}
