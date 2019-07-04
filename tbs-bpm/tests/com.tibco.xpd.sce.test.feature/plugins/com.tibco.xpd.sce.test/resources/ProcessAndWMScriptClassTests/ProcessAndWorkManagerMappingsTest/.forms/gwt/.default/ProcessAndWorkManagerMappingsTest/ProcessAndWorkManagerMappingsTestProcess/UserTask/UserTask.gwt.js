

if (typeof(tibcoforms) == 'undefined') tibcoforms = new Object();
if (typeof(tibcoforms.formCode) == 'undefined') tibcoforms.formCode = new Object();
tibcoforms.formCode['_8hoWEJ5XEemc8sVAXQvGcA'] = new Object();
tibcoforms.formCode['_8hoWEJ5XEemc8sVAXQvGcA']['defineActions'] = function() {
var fc = tibcoforms.formCode['_8hoWEJ5XEemc8sVAXQvGcA'];
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
tibcoforms.formCode['_8hoWEJ5XEemc8sVAXQvGcA']['defineActions']();

tibcoforms.formCode['_8hoWEJ5XEemc8sVAXQvGcA']['defineValidations'] = function() {
var fc = tibcoforms.formCode['_8hoWEJ5XEemc8sVAXQvGcA'];
    
    
    
    
    
    
    
fc['validation_FromWorkItemTextAttr_FromWorkItemTextAttr__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "FromWorkItemTextAttr__length", true, true);
}
    
fc['validation_ToWorkItemNumberAttr_ToWorkItemNumberAttr__fixed'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkNumberConstraint(context.stringValue, 10, 2) ? true : [context.control.getLabel(),\'10\', \'2\'] : !isNaN(context.value) && this.getForm().numberFormat(context.value, 10, 2);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ToWorkItemNumberAttr__fixed", true, true);
}
    
    
    
fc['validation_FromWorkItemNumberAttr_FromWorkItemNumberAttr__fixed'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkNumberConstraint(context.stringValue, 10, 2) ? true : [context.control.getLabel(),\'10\', \'2\'] : !isNaN(context.value) && this.getForm().numberFormat(context.value, 10, 2);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "FromWorkItemNumberAttr__fixed", true, true);
}
    
fc['validation_ToWorkItemTextAttr_ToWorkItemTextAttr__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ToWorkItemTextAttr__length", true, true);
}
    
fc['validation_FromProcessStartTime_FromProcessStartTime__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "FromProcessStartTime__datetime", true, true);
}
    
fc['validation_ToWorkitemDateTimeAttr_ToWorkitemDateTimeAttr__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ToWorkitemDateTimeAttr__datetime", true, true);
}
    
    
    
    
fc['validation_FromWorkitemDateTimeAttr_FromWorkitemDateTimeAttr__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "FromWorkitemDateTimeAttr__datetime", true, true);
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

        this.getFromProcessStartTime = function(useInternal) {
            return this.form.dataMap['FromProcessStartTime'].getValue(useInternal);
        };
        this.setFromProcessStartTime = function(value) {
            this.form.dataMap['FromProcessStartTime'].setValue(value);
        };
        Object.defineProperty(this, 'FromProcessStartTime', {
            get: function() {
                return this.form.dataMap['FromProcessStartTime'].value;
            },
            set: function(value) {
                this.form.dataMap['FromProcessStartTime'].value = value;
            },
            enumerable: true
        });

        this.getToProcessPriority = function(useInternal) {
            return this.form.dataMap['ToProcessPriority'].getValue(useInternal);
        };
        this.setToProcessPriority = function(value) {
            this.form.dataMap['ToProcessPriority'].setValue(value);
        };
        Object.defineProperty(this, 'ToProcessPriority', {
            get: function() {
                return this.form.dataMap['ToProcessPriority'].value;
            },
            set: function(value) {
                this.form.dataMap['ToProcessPriority'].value = value;
            },
            enumerable: true
        });

        this.getFromProcessPriority = function(useInternal) {
            return this.form.dataMap['FromProcessPriority'].getValue(useInternal);
        };
        this.setFromProcessPriority = function(value) {
            this.form.dataMap['FromProcessPriority'].setValue(value);
        };
        Object.defineProperty(this, 'FromProcessPriority', {
            get: function() {
                return this.form.dataMap['FromProcessPriority'].value;
            },
            set: function(value) {
                this.form.dataMap['FromProcessPriority'].value = value;
            },
            enumerable: true
        });

        this.getToWorkItemPriority = function(useInternal) {
            return this.form.dataMap['ToWorkItemPriority'].getValue(useInternal);
        };
        this.setToWorkItemPriority = function(value) {
            this.form.dataMap['ToWorkItemPriority'].setValue(value);
        };
        Object.defineProperty(this, 'ToWorkItemPriority', {
            get: function() {
                return this.form.dataMap['ToWorkItemPriority'].value;
            },
            set: function(value) {
                this.form.dataMap['ToWorkItemPriority'].value = value;
            },
            enumerable: true
        });

        this.getToWorkItemCancel = function(useInternal) {
            return this.form.dataMap['ToWorkItemCancel'].getValue(useInternal);
        };
        this.setToWorkItemCancel = function(value) {
            this.form.dataMap['ToWorkItemCancel'].setValue(value);
        };
        Object.defineProperty(this, 'ToWorkItemCancel', {
            get: function() {
                return this.form.dataMap['ToWorkItemCancel'].value;
            },
            set: function(value) {
                this.form.dataMap['ToWorkItemCancel'].value = value;
            },
            enumerable: true
        });

        this.getToWorkItemTextAttr = function(useInternal) {
            return this.form.dataMap['ToWorkItemTextAttr'].getValue(useInternal);
        };
        this.setToWorkItemTextAttr = function(value) {
            this.form.dataMap['ToWorkItemTextAttr'].setValue(value);
        };
        Object.defineProperty(this, 'ToWorkItemTextAttr', {
            get: function() {
                return this.form.dataMap['ToWorkItemTextAttr'].value;
            },
            set: function(value) {
                this.form.dataMap['ToWorkItemTextAttr'].value = value;
            },
            enumerable: true
        });

        this.getToWorkItemNumberAttr = function(useInternal) {
            return this.form.dataMap['ToWorkItemNumberAttr'].getValue(useInternal);
        };
        this.setToWorkItemNumberAttr = function(value) {
            this.form.dataMap['ToWorkItemNumberAttr'].setValue(value);
        };
        Object.defineProperty(this, 'ToWorkItemNumberAttr', {
            get: function() {
                return this.form.dataMap['ToWorkItemNumberAttr'].value;
            },
            set: function(value) {
                this.form.dataMap['ToWorkItemNumberAttr'].value = value;
            },
            enumerable: true
        });

        this.getToWorkItemIntegerAttr = function(useInternal) {
            return this.form.dataMap['ToWorkItemIntegerAttr'].getValue(useInternal);
        };
        this.setToWorkItemIntegerAttr = function(value) {
            this.form.dataMap['ToWorkItemIntegerAttr'].setValue(value);
        };
        Object.defineProperty(this, 'ToWorkItemIntegerAttr', {
            get: function() {
                return this.form.dataMap['ToWorkItemIntegerAttr'].value;
            },
            set: function(value) {
                this.form.dataMap['ToWorkItemIntegerAttr'].value = value;
            },
            enumerable: true
        });

        this.getToWorkitemDateTimeAttr = function(useInternal) {
            return this.form.dataMap['ToWorkitemDateTimeAttr'].getValue(useInternal);
        };
        this.setToWorkitemDateTimeAttr = function(value) {
            this.form.dataMap['ToWorkitemDateTimeAttr'].setValue(value);
        };
        Object.defineProperty(this, 'ToWorkitemDateTimeAttr', {
            get: function() {
                return this.form.dataMap['ToWorkitemDateTimeAttr'].value;
            },
            set: function(value) {
                this.form.dataMap['ToWorkitemDateTimeAttr'].value = value;
            },
            enumerable: true
        });

        this.getFromWorkItemIntegerAttr = function(useInternal) {
            return this.form.dataMap['FromWorkItemIntegerAttr'].getValue(useInternal);
        };
        this.setFromWorkItemIntegerAttr = function(value) {
            this.form.dataMap['FromWorkItemIntegerAttr'].setValue(value);
        };
        Object.defineProperty(this, 'FromWorkItemIntegerAttr', {
            get: function() {
                return this.form.dataMap['FromWorkItemIntegerAttr'].value;
            },
            set: function(value) {
                this.form.dataMap['FromWorkItemIntegerAttr'].value = value;
            },
            enumerable: true
        });

        this.getFromWorkitemDateTimeAttr = function(useInternal) {
            return this.form.dataMap['FromWorkitemDateTimeAttr'].getValue(useInternal);
        };
        this.setFromWorkitemDateTimeAttr = function(value) {
            this.form.dataMap['FromWorkitemDateTimeAttr'].setValue(value);
        };
        Object.defineProperty(this, 'FromWorkitemDateTimeAttr', {
            get: function() {
                return this.form.dataMap['FromWorkitemDateTimeAttr'].value;
            },
            set: function(value) {
                this.form.dataMap['FromWorkitemDateTimeAttr'].value = value;
            },
            enumerable: true
        });

        this.getFromWorkItemTextAttr = function(useInternal) {
            return this.form.dataMap['FromWorkItemTextAttr'].getValue(useInternal);
        };
        this.setFromWorkItemTextAttr = function(value) {
            this.form.dataMap['FromWorkItemTextAttr'].setValue(value);
        };
        Object.defineProperty(this, 'FromWorkItemTextAttr', {
            get: function() {
                return this.form.dataMap['FromWorkItemTextAttr'].value;
            },
            set: function(value) {
                this.form.dataMap['FromWorkItemTextAttr'].value = value;
            },
            enumerable: true
        });

        this.getFromWorkItemCancel = function(useInternal) {
            return this.form.dataMap['FromWorkItemCancel'].getValue(useInternal);
        };
        this.setFromWorkItemCancel = function(value) {
            this.form.dataMap['FromWorkItemCancel'].setValue(value);
        };
        Object.defineProperty(this, 'FromWorkItemCancel', {
            get: function() {
                return this.form.dataMap['FromWorkItemCancel'].value;
            },
            set: function(value) {
                this.form.dataMap['FromWorkItemCancel'].value = value;
            },
            enumerable: true
        });

        this.getFromWorkItemPriority = function(useInternal) {
            return this.form.dataMap['FromWorkItemPriority'].getValue(useInternal);
        };
        this.setFromWorkItemPriority = function(value) {
            this.form.dataMap['FromWorkItemPriority'].setValue(value);
        };
        Object.defineProperty(this, 'FromWorkItemPriority', {
            get: function() {
                return this.form.dataMap['FromWorkItemPriority'].value;
            },
            set: function(value) {
                this.form.dataMap['FromWorkItemPriority'].value = value;
            },
            enumerable: true
        });

        this.getFromWorkItemNumberAttr = function(useInternal) {
            return this.form.dataMap['FromWorkItemNumberAttr'].getValue(useInternal);
        };
        this.setFromWorkItemNumberAttr = function(value) {
            this.form.dataMap['FromWorkItemNumberAttr'].setValue(value);
        };
        Object.defineProperty(this, 'FromWorkItemNumberAttr', {
            get: function() {
                return this.form.dataMap['FromWorkItemNumberAttr'].value;
            },
            set: function(value) {
                this.form.dataMap['FromWorkItemNumberAttr'].value = value;
            },
            enumerable: true
        });
    }
       
       
       
};
tibcoforms.formCode['_8hoWEJ5XEemc8sVAXQvGcA']['defineValidations']();
