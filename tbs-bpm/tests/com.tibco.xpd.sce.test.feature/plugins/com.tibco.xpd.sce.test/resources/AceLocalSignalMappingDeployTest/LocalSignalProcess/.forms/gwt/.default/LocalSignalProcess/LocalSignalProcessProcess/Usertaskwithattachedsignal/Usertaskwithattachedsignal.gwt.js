

if (typeof(tibcoforms) == 'undefined') tibcoforms = new Object();
if (typeof(tibcoforms.formCode) == 'undefined') tibcoforms.formCode = new Object();
tibcoforms.formCode['_koZGsLRpEemTPpnCykNv-g'] = new Object();
tibcoforms.formCode['_koZGsLRpEemTPpnCykNv-g']['defineActions'] = function() {
var fc = tibcoforms.formCode['_koZGsLRpEemTPpnCykNv-g'];
    fc['rule_enable_ComplexArrayField__detail'] = function(formId, context, thisObj) {
       try {
            bpm.forms.Util.handleComputationAction.call(thisObj, formId, context, thisObj, "enable_ComplexArrayField__detail", "enable_ComplexArrayField__detail", fc['action_enable_ComplexArrayField__detail'], false, 'pane.ComplexArrayField__detail');
       } catch(e) {
           tibcoforms.bridge.log_error("Rule(enable_ComplexArrayField__detail) Error: " + e);
           throw e;
       }
    }

        fc['action_enable_ComplexArrayField__detail'] = function(context, data, pane, control, factory, pkg, f , p) {
                if (context.form && context.form._marked4Cancel)
                    return;
                var resource = context.form.resource;
                var logger = tibcoforms.bridge.log_logger();
                var destName = 'pane.ComplexArrayField__detail';
                    var destType = 'pane';
                    var destFeatures = new Array('enabled');
                    var isComplex = false;
                    isComplex = tibcoforms.bridge.comp_isComplexFeature(context.form.id, false, destName, context.cloneUID, 'enabled');                                                                        var tempScr = 'context.newValue != null;';
                var val = eval(tempScr);
                val = bpm.forms.Util.convertExternalValueToInternalValue.call(this, isComplex, val, destName, context);
                    if (isComplex) {
                       if (tibcoforms.FormProxy.isTIBCOFormsList(val))
                           tibcoforms.bridge.compAction_updateCOListDestination(context.form.id, 'action.enable_ComplexArrayField__detail', destName, context.cloneUID, destType, destFeatures, val);
                       else
                           tibcoforms.bridge.compAction_updateCODestination(context.form.id, 'action.enable_ComplexArrayField__detail', destName, context.cloneUID, destType, destFeatures, val);
                    } else {
                        tibcoforms.bridge.compAction_updateDestination(context.form.id, 'action.enable_ComplexArrayField__detail', destName, context.cloneUID, destType, destFeatures, val);
                    }
        }
    fc['rule_cancel'] = function(formId, context, thisObj) {
       try {
            bpm.forms.Util.handleScriptAction.call(thisObj, formId, context, thisObj, "cancel", "cancel", fc['action_cancel']);
       } catch(e) {
           tibcoforms.bridge.log_error("Rule(cancel) Error: " + e);
           throw e;
       }
    }

    fc['rule_close'] = function(formId, context, thisObj) {
       try {
            bpm.forms.Util.handleScriptAction.call(thisObj, formId, context, thisObj, "close", "close", fc['action_close']);
       } catch(e) {
           tibcoforms.bridge.log_error("Rule(close) Error: " + e);
           throw e;
       }
    }

    fc['rule_submit'] = function(formId, context, thisObj) {
       try {
            bpm.forms.Util.handleScriptAction.call(thisObj, formId, context, thisObj, "submit", "submit", fc['action_submit']);
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
tibcoforms.formCode['_koZGsLRpEemTPpnCykNv-g']['defineActions']();

tibcoforms.formCode['_koZGsLRpEemTPpnCykNv-g']['defineValidations'] = function() {
var fc = tibcoforms.formCode['_koZGsLRpEemTPpnCykNv-g'];
    
    
fc['validation_ComplexArrayField_attribute1__master_ComplexArrayField_attribute1__master__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof bpm.forms.Util != \'undefined\' ? bpm.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return bpm.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexArrayField_attribute1__master__length", true, true);
}
    
    
fc['validation_ComplexArrayField_class2_attribute1_ComplexArrayField_class2_attribute1__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof bpm.forms.Util != \'undefined\' ? bpm.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return bpm.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexArrayField_class2_attribute1__length", true, true);
}
    
fc['validation_ComplexField_class2_attribute2_ComplexField_class2_attribute2__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof bpm.forms.Util != \'undefined\' ? bpm.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return bpm.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexField_class2_attribute2__length", true, true);
}
    
    
fc['validation_ComplexArrayField_attribute2__master_ComplexArrayField_attribute2__master__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof bpm.forms.Util != \'undefined\' ? bpm.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return bpm.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexArrayField_attribute2__master__length", true, true);
}
    
    
    
fc['validation_ComplexField_attribute2_ComplexField_attribute2__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof bpm.forms.Util != \'undefined\' ? bpm.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return bpm.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexField_attribute2__length", true, true);
}
    
    
fc['validation_ComplexField_attribute1_ComplexField_attribute1__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof bpm.forms.Util != \'undefined\' ? bpm.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return bpm.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexField_attribute1__length", true, true);
}
    
fc['validation_ComplexArrayField_attribute1_ComplexArrayField_attribute1__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof bpm.forms.Util != \'undefined\' ? bpm.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return bpm.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexArrayField_attribute1__length", true, true);
}
    
    
fc['validation_ComplexField_class2_attribute1_ComplexField_class2_attribute1__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof bpm.forms.Util != \'undefined\' ? bpm.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return bpm.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexField_class2_attribute1__length", true, true);
}
    
fc['validation_TextField_TextField__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof bpm.forms.Util != \'undefined\' ? bpm.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return bpm.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "TextField__length", true, true);
}
    
    
    
    
fc['validation_ComplexArrayField_attribute2_ComplexArrayField_attribute2__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof bpm.forms.Util != \'undefined\' ? bpm.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return bpm.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexArrayField_attribute2__length", true, true);
}
    
    
    
    
    
fc['validation_ComplexArrayField_class2_attribute2_ComplexArrayField_class2_attribute2__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof bpm.forms.Util != \'undefined\' ? bpm.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return bpm.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexArrayField_class2_attribute2__length", true, true);
}
    
fc['validation_TextArrayField_TextArrayField__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof bpm.forms.Util != \'undefined\' ? bpm.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return bpm.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "TextArrayField__length", true, true);
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
       form.registerPackages(['com.example.localsignaldata.LocalsignaldataPackage']);
       form.registerFactories(['com.example.localsignaldata.LocalsignaldataFactory']);
    }
    fc['DataModel'] = function(formId) {
        if (this.form) return;
        this.form = tibcoforms.formCache[formId];

        this.getTextField = function(useInternal) {
            return this.form.dataMap['TextField'].getValue(useInternal);
        };
        this.setTextField = function(value) {
            this.form.dataMap['TextField'].setValue(value);
        };
        Object.defineProperty(this, 'TextField', {
            get: function() {
                return this.form.dataMap['TextField'].value;
            },
            set: function(value) {
                this.form.dataMap['TextField'].value = value;
            },
            enumerable: true
        });

        this.getTextArrayField = function(useInternal) {
            return this.form.dataMap['TextArrayField'].getValue(useInternal);
        };
        this.setTextArrayField = function(value) {
            this.form.dataMap['TextArrayField'].setValue(value);
        };
        Object.defineProperty(this, 'TextArrayField', {
            get: function() {
                return this.form.dataMap['TextArrayField'].value;
            },
            set: function(value) {
                throw "Cannot assign value on a multi-valued parameter/datafield.";
            },
            enumerable: true
        });

        this.getComplexField = function() {
            return this.form.dataMap['ComplexField'].getValue();
        };
        this.setComplexField = function(value) {
            this.form.dataMap['ComplexField'].setValue(value);
        };
        Object.defineProperty(this, 'ComplexField', {
            get: function() {
                return this.form.dataMap['ComplexField'].value;
            },
            set: function(value) {
                this.form.dataMap['ComplexField'].value = value;
            },
            enumerable: true
        });

        this.getComplexArrayField = function() {
            return this.form.dataMap['ComplexArrayField'].getValue();
        };
        Object.defineProperty(this, 'ComplexArrayField', {
            get: function() {
                return this.form.dataMap['ComplexArrayField'].value;
            },
            set: function(value) {
                throw "Cannot assign value on a multi-valued parameter/datafield.";
            },
            enumerable: true
        });
    }
       
            
            
            
            
            
            
            
       
       
};
tibcoforms.formCode['_koZGsLRpEemTPpnCykNv-g']['defineValidations']();
