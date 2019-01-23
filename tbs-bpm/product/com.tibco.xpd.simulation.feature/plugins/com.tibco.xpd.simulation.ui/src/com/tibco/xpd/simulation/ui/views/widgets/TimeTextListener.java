/**
 * 
 */
package com.tibco.xpd.simulation.ui.views.widgets;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

/**
 * TODO comment me!
 * @author mmaciuki
 *
 */
public class TimeTextListener implements Listener {
    
    private final Text text;
    private boolean ignore;
    private final String blank;
    private final DateFormat dateFormat;
    private Date selectedTime;
    private final DateTimeSelectComposite parent;
    
    /**
     * TODO comment me!
     * @param dateFomat 
     * @param composite 
     */
    public TimeTextListener(Text text, DateFormat dateFomat, String blank, DateTimeSelectComposite parent) {
        this.text = text;
        this.dateFormat = dateFomat;
        this.blank=blank;
        this.parent = parent;
    }
    
    /**
     * TODO comment me!
     * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
     */
    public void handleEvent(Event event) {
        if (ignore) return;
        
        event.doit = false;
        
        if(event.start>=blank.length()){
            return;
        }
        
        int skipPoint = blank.indexOf(':',event.end);
        int newCursorPosition;
        StringBuffer buffer = new StringBuffer(text.getText());
        // if characters deleted
        if (event.character == '\b') {
            String blankFiller = blank.substring(event.start,event.end);
            buffer.replace(event.start,event.end,blankFiller);
            newCursorPosition=event.end-1;
            if(newCursorPosition==(skipPoint+1)){
                newCursorPosition-=1;
            }
        } else {
            // 
            buffer.replace(event.start,event.end+1,event.text);
            // check if after modification text is stil valid date format
            try {
                selectedTime = dateFormat.parse(buffer.toString());
            } catch (ParseException e) {
                return;
            }
            newCursorPosition=event.end+1;
            if(newCursorPosition==(skipPoint)){
                newCursorPosition+=1;
            }
        }
        ignore = true;
        text.setText(buffer.toString());
        ignore = false;
        text.setSelection(newCursorPosition, newCursorPosition);
        parent.notifyListeners();
    }
    
    public Date getSelectedTime(){
        return this.selectedTime;
    }
}
