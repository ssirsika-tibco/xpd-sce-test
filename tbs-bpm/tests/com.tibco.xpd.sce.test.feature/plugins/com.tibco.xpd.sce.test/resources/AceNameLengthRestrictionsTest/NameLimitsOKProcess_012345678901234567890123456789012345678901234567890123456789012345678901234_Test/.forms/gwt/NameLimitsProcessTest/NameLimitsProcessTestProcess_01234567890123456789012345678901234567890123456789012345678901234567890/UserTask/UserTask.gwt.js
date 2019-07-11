

if (typeof(tibcoforms) == 'undefined') tibcoforms = new Object();
if (typeof(tibcoforms.formCode) == 'undefined') tibcoforms.formCode = new Object();
tibcoforms.formCode['_fmNHIKO-EemPhu96igMWpw'] = new Object();
tibcoforms.formCode['_fmNHIKO-EemPhu96igMWpw']['defineActions'] = function() {
var fc = tibcoforms.formCode['_fmNHIKO-EemPhu96igMWpw'];
    fc['rule_cancel'] = function(formId, context, thisObj) {
       try {
            tibco.forms.Util.handleScriptAction.call(thisObj, formId, context, thisObj, "cancel", "cancel", fc['action_cancel']);
       } catch(e) {
           tibcoforms.bridge.log_error("Rule(cancel) Error: " + e);
           throw e;
       }
    }

    fc['rule_close'] = function(formId, context, thisObj) {
       try {
            tibco.forms.Util.handleScriptAction.call(thisObj, formId, context, thisObj, "close", "close", fc['action_close']);
       } catch(e) {
           tibcoforms.bridge.log_error("Rule(close) Error: " + e);
           throw e;
       }
    }

    fc['rule_submit'] = function(formId, context, thisObj) {
       try {
            tibco.forms.Util.handleScriptAction.call(thisObj, formId, context, thisObj, "submit", "submit", fc['action_submit']);
       } catch(e) {
           tibcoforms.bridge.log_error("Rule(submit) Error: " + e);
           throw e;
       }
    }

    fc['action_cancel'] = function(context, data, pane, control, factory, pkg, f , p) {
        context.form.invokeAction('cancel');
    }

    fc['action_apply'] = function(context, data, pane, control, factory, pkg, f , p) {
        context.form.invokeAction('apply');
    }
    
    fc['action_close'] = function(context, data, pane, control, factory, pkg, f , p) {
        context.form.invokeAction('close');
    }

    fc['action_submit'] = function(context, data, pane, control, factory, pkg, f , p) {
        context.form.invokeAction('submit');
    }
    
    fc['action_validate'] = function(context, data, pane, control, factory, pkg, f , p) {
        context.form.invokeAction('validate');
    }
    
    fc['action_reset'] = function(context, data, pane, control, factory, pkg, f , p) {
        context.form.invokeAction('reset');
    }
    
    fc['generator_info'] = function() {
        return "TIBCO Forms for ACE Runtime 1.0.0 V02";
    }
};
tibcoforms.formCode['_fmNHIKO-EemPhu96igMWpw']['defineActions']();

tibcoforms.formCode['_fmNHIKO-EemPhu96igMWpw']['defineValidations'] = function() {
var fc = tibcoforms.formCode['_fmNHIKO-EemPhu96igMWpw'];
    
    
fc['validation_Field4_Field4__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Field4__length", true, true);
}
    
fc['validation_Field3_Field3__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Field3__length", true, true);
}
    
    
fc['validation_Field2_Field2__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Field2__length", true, true);
}
    
fc['validation_Field_Field__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Field__length", true, true);
}
    
    
    
    
    fc['validate_required'] = function(formId, controlName, cloneUID, listIndex) {
    var context = new Object();
    var form = tibcoforms.formCache[formId];
    var logger = tibcoforms.bridge.log_logger();
    context.control = this;
    if (listIndex == -1) {
        context.value = context.control.getValue();
        if (context.control.getStringValue)
            context.stringValue = context.control.getStringValue();
    } else {
        context.value = context.control.getValue()[listIndex];
        if (context.control.getStringValue)
            context.stringValue = context.control.getStringValue()[listIndex];
    }
    if (context.value == null)
        context.value = '';
        var controlType = context.control.getControlType();
        var strContxtControlValue = context.control.getValue();
        if (listIndex >= 0) {
           strContxtControlValue = strContxtControlValue[listIndex];
        }
        return !(context.control.getRequired() && 
                 (strContxtControlValue == null || strContxtControlValue.toString().replace(/^\s+|\s+$/gm,'').length == 0) ||
                 (("com.tibco.forms.controls.checkbox" == controlType) && 'true' != strContxtControlValue.toString().toLowerCase()));
    }
    fc['register_pkgs_and_fcts'] = function(formId) {
       var form = tibcoforms.formCache[formId];
       form.registerPackages([]);
       form.registerFactories([]);
    }
    fc['DataModel'] = function(formId) {
        if (this.form) return;
        this.form = tibcoforms.formCache[formId];

        this.getField = function(useInternal) {
            return this.form.dataMap['Field'].getValue(useInternal);
        };
        this.setField = function(value) {
            this.form.dataMap['Field'].setValue(value);
        };
        Object.defineProperty(this, 'Field', {
            get: function() {
                return this.form.dataMap['Field'].value;
            },
            set: function(value) {
                this.form.dataMap['Field'].value = value;
            },
            enumerable: true
        });

        this.getField2 = function(useInternal) {
            return this.form.dataMap['Field2'].getValue(useInternal);
        };
        this.setField2 = function(value) {
            this.form.dataMap['Field2'].setValue(value);
        };
        Object.defineProperty(this, 'Field2', {
            get: function() {
                return this.form.dataMap['Field2'].value;
            },
            set: function(value) {
                this.form.dataMap['Field2'].value = value;
            },
            enumerable: true
        });

        this.getField3 = function(useInternal) {
            return this.form.dataMap['Field3'].getValue(useInternal);
        };
        this.setField3 = function(value) {
            this.form.dataMap['Field3'].setValue(value);
        };
        Object.defineProperty(this, 'Field3', {
            get: function() {
                return this.form.dataMap['Field3'].value;
            },
            set: function(value) {
                this.form.dataMap['Field3'].value = value;
            },
            enumerable: true
        });

        this.getField4 = function(useInternal) {
            return this.form.dataMap['Field4'].getValue(useInternal);
        };
        this.setField4 = function(value) {
            this.form.dataMap['Field4'].setValue(value);
        };
        Object.defineProperty(this, 'Field4', {
            get: function() {
                return this.form.dataMap['Field4'].value;
            },
            set: function(value) {
                this.form.dataMap['Field4'].value = value;
            },
            enumerable: true
        });
    }
       
       
       
};
tibcoforms.formCode['_fmNHIKO-EemPhu96igMWpw']['defineValidations']();
