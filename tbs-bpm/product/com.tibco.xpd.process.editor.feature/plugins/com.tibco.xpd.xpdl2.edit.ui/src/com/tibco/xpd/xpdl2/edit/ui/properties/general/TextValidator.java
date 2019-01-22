/**
 *
 */
package com.tibco.xpd.xpdl2.edit.ui.properties.general;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashSet;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.xpdl2.edit.ui.internal.Messages;

public class TextValidator implements ModifyListener, PropertyChangeListener {
    
    interface ValidFormat {
        /**
         * @param text
         *            string to be validated
         * @return true is text is valid according to validation rule
         */
        boolean validate(String text);

        String getErrorMessage();
    }

    enum ValidFormatImpl implements ValidFormat {
        NONE {
            public boolean validate(String text) {
                return true;
            }

            public String getErrorMessage() {
                return ""; //$NON-NLS-1$
            }
        },
        DATE_TIME {
            private final DateFormat dFormat = DateFormat.getDateTimeInstance(
                    DateFormat.SHORT, DateFormat.SHORT);

            public boolean validate(String text) {
                boolean result = true;
                try {
                    dFormat.parse(text);
                } catch (ParseException e) {
                    result = false;
                }
                return result;
            }

            public String getErrorMessage() {
                return Messages.TextValidator_Invalid_ExtDate_message;
            }
        },
        DATE {
            private final DateFormat dFormat = DateFormat.getDateInstance(
                    DateFormat.SHORT);

            public boolean validate(String text) {
                boolean result = true;
                try {
                    dFormat.parse(text);
                } catch (ParseException e) {
                    result = false;
                }
                return result;
            }

            public String getErrorMessage() {
                return Messages.TextValidator_Invalid_Date_message;
            }
        },
        BOOLEAN {
            public boolean validate(String text) {
                boolean result = false;
                String value = text.toLowerCase();
                if (value.equals(Boolean.TRUE.toString())
                        || value.equals(Boolean.FALSE.toString())) {
                    result = true;
                }
                return result;
            }

            public String getErrorMessage() {
                return Messages.TextValidator_Invalid_Boolean_message;
            }
        };
    }

    private ValidFormat validationStrategy;
    private final Text textWidget;
    private boolean isValid;
    private final PropertyChangeSupport support;
    private boolean ignoreEmptyString;
    private final Collection<ValidationListener> listeners;
    private String errorMessage;
    private boolean isArrayValidation;

    public TextValidator(Text textWidget) {
        isValid = true;
        this.textWidget = textWidget;
        textWidget.addModifyListener(this);
        validationStrategy = ValidFormatImpl.NONE;
        support = new PropertyChangeSupport(this);
        ignoreEmptyString = true;
        support.addPropertyChangeListener(this);
        listeners = new HashSet<ValidationListener>();
        isArrayValidation = false;
    }

    void setIgnoreEmptyString(boolean shouldIgnore) {
        ignoreEmptyString = shouldIgnore;
    }

    void setValidFormat(TextValidator.ValidFormat validationStrategy) {
        this.validationStrategy = validationStrategy;
    }
    
    /**
     * Splits the comma or space delimited text and validates each one accordingly
     * @param text
     */
    private boolean checkValidText(String text){
        boolean isValidNow = true;        
        String validationText = null;
        String[] textEntries = null;
        
        // as been as this can be space or comma delimiter we pick one to deal with
        validationText = text.replace(" ",",");  //$NON-NLS-1$  //$NON-NLS-2$
        textEntries = validationText.split(",");  //$NON-NLS-1$
          
        // if we have date entries we need to deal with them differently as they can contain spaces for one entry
        // so we don't wanna split these
        boolean dateCheck = false;
        int numberOfEntries = textEntries.length;
        if (validationStrategy.equals(ValidFormatImpl.DATE_TIME)){
            dateCheck = true;
            // if not even number size then we know there is an invalid date entry somewhere so don't need to go any further - its invalid
            if (numberOfEntries % 2 > 0){
                isValidNow = false;
            }
        }
                
        if (isValidNow){
            isValidNow = false;
            for (int i=1;i<=numberOfEntries;i++){
                validationText = textEntries[i-1].trim();
                            
                if (validationText.length() > 0 ){
                    
                    // if date then we need to ensure that we only perform validation every 2 cycles 
                    //i.e. we concatenate the two date and time entries as one validation!
                    if (dateCheck && i%2 == 0){
                        validationText = textEntries[i-2] + " " + validationText;  //$NON-NLS-1$
                    }else if (dateCheck && i%2 > 0){
                        continue;
                    }
                    
                    // validate the text
                    isValidNow = validationStrategy.validate(validationText);
                    if (!isValidNow){
                        break;
                    }
                }
            }
        }
        
        return isValidNow;
    }

    public void modifyText(ModifyEvent e) {
        boolean isValidNow = false;
        String text = textWidget.getText();
        if (ignoreEmptyString && text.trim().length() == 0) {
            isValidNow = true;
        } else {
            if (isArrayValidation){
                isValidNow = checkValidText(text);
            }else{
                isValidNow = validationStrategy.validate(text);
            }
        }
        if (isValidNow) {
            errorMessage = ""; //$NON-NLS-1$
        } else {
            errorMessage = validationStrategy.getErrorMessage();
        }
        support.firePropertyChange("textValue", isValid, isValidNow); //$NON-NLS-1$
        isValid = isValidNow;
    }

    public boolean validate() {
        if (isArrayValidation){
            isValid = checkValidText(textWidget.getText());
        }else{
            isValid = validationStrategy.validate(textWidget.getText());
        }
        
        return isValid;
    }

    public void dispose() {
        textWidget.removeModifyListener(this);
    }

    public void addValidationListener(ValidationListener listener) {
        listeners.add(listener);
    }

    public void removeValidationListener(ValidationListener listener) {
        listeners.remove(listener);
    }
    
    public void setArrayValidation(boolean isArrayValidation) {
        this.isArrayValidation = isArrayValidation;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        ValidationEvent event = new ValidationEvent(textWidget, (Boolean) evt
                .getNewValue(), errorMessage);
        for (ValidationListener listener : listeners) {
            listener.event(event);
        }
    }

    public interface ValidationListener {
        void event(ValidationEvent event);
    }

    public class ValidationEvent {
        private final boolean isValid;
        private final String errorMessage;
        private final Text widget;

        private ValidationEvent(Text widget, boolean isValid,
                String errorMessage) {
            this.widget = widget;
            this.isValid = isValid;
            this.errorMessage = errorMessage;
        }

        public Text getWidget() {
            return widget;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public boolean isValid() {
            return isValid;
        }
    }

  

}