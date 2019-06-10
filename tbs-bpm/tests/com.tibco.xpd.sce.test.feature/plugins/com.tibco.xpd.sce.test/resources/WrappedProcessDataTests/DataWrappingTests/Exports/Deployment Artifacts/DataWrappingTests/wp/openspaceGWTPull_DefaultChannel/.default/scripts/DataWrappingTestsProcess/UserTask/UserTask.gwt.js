

if (typeof(tibcoforms) == 'undefined') tibcoforms = new Object();
if (typeof(tibcoforms.formCode) == 'undefined') tibcoforms.formCode = new Object();
tibcoforms.formCode['_0wYbkIhDEemt37zmJf_N4g'] = new Object();
tibcoforms.formCode['_0wYbkIhDEemt37zmJf_N4g']['defineActions'] = function() {
var fc = tibcoforms.formCode['_0wYbkIhDEemt37zmJf_N4g'];
    fc['rule_enable_ComplexListField__detail'] = function(formId, context, thisObj) {
       try {
            tibco.forms.Util.handleComputationAction.call(thisObj, formId, context, thisObj, "enable_ComplexListField__detail", "enable_ComplexListField__detail", fc['action_enable_ComplexListField__detail'], false, 'pane.ComplexListField__detail');
       } catch(e) {
           tibcoforms.bridge.log_error("Rule(enable_ComplexListField__detail) Error: " + e);
           throw e;
       }
    }

        fc['action_enable_ComplexListField__detail'] = function(context, data, pane, control, factory, pkg, f , p) {
                if (context.form && context.form._marked4Cancel)
                    return;
                var resource = context.form.resource;
                var logger = tibcoforms.bridge.log_logger();
                var destName = 'pane.ComplexListField__detail';
                    var destType = 'pane';
                    var destFeatures = new Array('enabled');
                    var isComplex = false;
                    isComplex = tibcoforms.bridge.comp_isComplexFeature(context.form.id, false, destName, context.cloneUID, 'enabled');                                                                        var tempScr = 'context.newValue != null;';
                var val = eval(tempScr);
                val = tibco.forms.Util.convertExternalValueToInternalValue.call(this, isComplex, val, destName, context);
                    if (isComplex) {
                       if (tibcoforms.FormProxy.isTIBCOFormsList(val))
                           tibcoforms.bridge.compAction_updateCOListDestination(context.form.id, 'action.enable_ComplexListField__detail', destName, context.cloneUID, destType, destFeatures, val);
                       else
                           tibcoforms.bridge.compAction_updateCODestination(context.form.id, 'action.enable_ComplexListField__detail', destName, context.cloneUID, destType, destFeatures, val);
                    } else {
                        tibcoforms.bridge.compAction_updateDestination(context.form.id, 'action.enable_ComplexListField__detail', destName, context.cloneUID, destType, destFeatures, val);
                    }
        }
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
tibcoforms.formCode['_0wYbkIhDEemt37zmJf_N4g']['defineActions']();

tibcoforms.formCode['_0wYbkIhDEemt37zmJf_N4g']['defineValidations'] = function() {
var fc = tibcoforms.formCode['_0wYbkIhDEemt37zmJf_N4g'];
    
fc['validation_DateTimeField_DateTimeField__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "DateTimeField__datetime", true, true);
}
    
fc['validation_ComplexListField_complexChild_attribute2_ComplexListField_complexChild_attribute2__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_complexChild_attribute2__length", true, true);
}
    
    
fc['validation_TimeField_TimeField__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "TimeField__datetime", true, true);
}
    
fc['validation_ComplexListField_complexList_ComplexListField_complexList__multiplicity'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof tibco.forms.Util.checkMultiplicity != \'undefined\' ? tibco.forms.Util.checkMultiplicity(context.value, 0, 2147483647) ?  true : [this.getLabel(), \'0\'] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_complexList__multiplicity", true, false);
}
    
fc['validation_ComplexListField_float1__master_ComplexListField_float1__master__float'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\'? context.value !== \'\' && !isNaN(context.value) ? true : [context.control.getLabel()] : context.value !== \'\' && !isNaN(context.value);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_float1__master__float", true, true);
}
    
fc['validation_ClassField_complexList_attribute2_ClassField_complexList_attribute2__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassField_complexList_attribute2__length", true, true);
}
    
fc['validation_ClassField_complexList_attribute1_ClassField_complexList_attribute1__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassField_complexList_attribute1__length", true, true);
}
    
fc['validation_ComplexListField_textList_ComplexListField_textList__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_textList__length", true, true);
}
fc['validation_ComplexListField_textList_ComplexListField_textList__multiplicity'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof tibco.forms.Util.checkMultiplicity != \'undefined\' ? tibco.forms.Util.checkMultiplicity(context.value, 0, 2147483647) ?  true : [this.getLabel(), \'0\'] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_textList__multiplicity", true, true);
}
    
fc['validation_ComplexListField_date__master_ComplexListField_date__master__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_date__master__datetime", true, true);
}
    
fc['validation_ComplexListField_text_ComplexListField_text__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_text__length", true, true);
}
    
fc['validation_ClassField_dateTimeTz_ClassField_dateTimeTz__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassField_dateTimeTz__datetime", true, true);
}
    
    
    
fc['validation_ComplexListField_URI__master_ComplexListField_URI__master__pattern'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'if (typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\') {\
  \n tibco.forms.Util.checkRegExp(context.value, new RegExp(\"^([a-zA-Z0-9+.\\\\-]+):(//((([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:]|%[0-9A-F]{2})*)@)?(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=]|%[0-9A-F]{2})*)(:(\\\\d*))?(/([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?|(/?([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@]|%[0-9A-F]{2})+([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?)(\\\\?(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?(#(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?$\")) ? true : [context.control.getLabel(), \'URI\'];\
  \n} else {\
  \nvar regex = new RegExp(\"^([a-zA-Z0-9+.\\-]+):(//((([a-zA-Z0-9\\-._~!$&\'()*+,;=:]|%[0-9A-F]{2})*)@)?(([a-zA-Z0-9\\-._~!$&\'()*+,;=]|%[0-9A-F]{2})*)(:(\\d*))?(/([a-zA-Z0-9\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?|(/?([a-zA-Z0-9\\-._~!$&\'()*+,;=:@]|%[0-9A-F]{2})+([a-zA-Z0-9\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?)(\\?(([a-zA-Z0-9\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?(#(([a-zA-Z0-9\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?$\");\
  \nregex.test(context.value);\
  \n}';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_URI__master__pattern", true, true);
}
    
fc['validation_ComplexListField_date_ComplexListField_date__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_date__datetime", true, true);
}
    
fc['validation_ClassField_float1_ClassField_float1__float'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\'? context.value !== \'\' && !isNaN(context.value) ? true : [context.control.getLabel()] : context.value !== \'\' && !isNaN(context.value);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassField_float1__float", true, true);
}
    
fc['validation_ComplexListField_fixed__master_ComplexListField_fixed__master__fixed'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkNumberConstraint(context.stringValue, 10, 2) ? true : [context.control.getLabel(),\'10\', \'2\'] : !isNaN(context.value) && this.getForm().numberFormat(context.value, 10, 2);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_fixed__master__fixed", true, true);
}
    
fc['validation_ComplexListField_time__master_ComplexListField_time__master__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_time__master__datetime", true, true);
}
    
fc['validation_ClassField_fixed_ClassField_fixed__fixed'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkNumberConstraint(context.stringValue, 10, 2) ? true : [context.control.getLabel(),\'10\', \'2\'] : !isNaN(context.value) && this.getForm().numberFormat(context.value, 10, 2);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassField_fixed__fixed", true, true);
}
    
    
fc['validation_ComplexListField_fixed_ComplexListField_fixed__fixed'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkNumberConstraint(context.stringValue, 10, 2) ? true : [context.control.getLabel(),\'10\', \'2\'] : !isNaN(context.value) && this.getForm().numberFormat(context.value, 10, 2);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_fixed__fixed", true, true);
}
    
    
    
    
    
    
fc['validation_ComplexListField_URI_ComplexListField_URI__pattern'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'if (typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\') {\
  \n tibco.forms.Util.checkRegExp(context.value, new RegExp(\"^([a-zA-Z0-9+.\\\\-]+):(//((([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:]|%[0-9A-F]{2})*)@)?(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=]|%[0-9A-F]{2})*)(:(\\\\d*))?(/([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?|(/?([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@]|%[0-9A-F]{2})+([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?)(\\\\?(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?(#(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?$\")) ? true : [context.control.getLabel(), \'URI\'];\
  \n} else {\
  \nvar regex = new RegExp(\"^([a-zA-Z0-9+.\\-]+):(//((([a-zA-Z0-9\\-._~!$&\'()*+,;=:]|%[0-9A-F]{2})*)@)?(([a-zA-Z0-9\\-._~!$&\'()*+,;=]|%[0-9A-F]{2})*)(:(\\d*))?(/([a-zA-Z0-9\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?|(/?([a-zA-Z0-9\\-._~!$&\'()*+,;=:@]|%[0-9A-F]{2})+([a-zA-Z0-9\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?)(\\?(([a-zA-Z0-9\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?(#(([a-zA-Z0-9\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?$\");\
  \nregex.test(context.value);\
  \n}';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_URI__pattern", true, true);
}
    
    
    
fc['validation_NumberField_NumberField__float'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\'? context.value !== \'\' && !isNaN(context.value) ? true : [context.control.getLabel()] : context.value !== \'\' && !isNaN(context.value);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "NumberField__float", true, true);
}
    
    
fc['validation_ClassField_URI_ClassField_URI__pattern'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'if (typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\') {\
  \n tibco.forms.Util.checkRegExp(context.value, new RegExp(\"^([a-zA-Z0-9+.\\\\-]+):(//((([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:]|%[0-9A-F]{2})*)@)?(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=]|%[0-9A-F]{2})*)(:(\\\\d*))?(/([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?|(/?([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@]|%[0-9A-F]{2})+([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?)(\\\\?(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?(#(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?$\")) ? true : [context.control.getLabel(), \'URI\'];\
  \n} else {\
  \nvar regex = new RegExp(\"^([a-zA-Z0-9+.\\-]+):(//((([a-zA-Z0-9\\-._~!$&\'()*+,;=:]|%[0-9A-F]{2})*)@)?(([a-zA-Z0-9\\-._~!$&\'()*+,;=]|%[0-9A-F]{2})*)(:(\\d*))?(/([a-zA-Z0-9\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?|(/?([a-zA-Z0-9\\-._~!$&\'()*+,;=:@]|%[0-9A-F]{2})+([a-zA-Z0-9\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?)(\\?(([a-zA-Z0-9\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?(#(([a-zA-Z0-9\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?$\");\
  \nregex.test(context.value);\
  \n}';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassField_URI__pattern", true, true);
}
    
    
    
fc['validation_ClassField_complexChild_attribute2_ClassField_complexChild_attribute2__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassField_complexChild_attribute2__length", true, true);
}
    
fc['validation_ClassField_text_ClassField_text__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassField_text__length", true, true);
}
    
    
fc['validation_ListFixedNumberTypeDecl_ListFixedNumberTypeDecl__fixed'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkNumberConstraint(context.stringValue, 10, 2) ? true : [context.control.getLabel(),\'10\', \'2\'] : !isNaN(context.value) && this.getForm().numberFormat(context.value, 10, 2);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ListFixedNumberTypeDecl__fixed", true, true);
}
    
    
    
fc['validation_ClassField_textList_ClassField_textList__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassField_textList__length", true, true);
}
fc['validation_ClassField_textList_ClassField_textList__multiplicity'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof tibco.forms.Util.checkMultiplicity != \'undefined\' ? tibco.forms.Util.checkMultiplicity(context.value, 0, 2147483647) ?  true : [this.getLabel(), \'0\'] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassField_textList__multiplicity", true, true);
}
    
    
fc['validation_ComplexListField_float1_ComplexListField_float1__float'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\'? context.value !== \'\' && !isNaN(context.value) ? true : [context.control.getLabel()] : context.value !== \'\' && !isNaN(context.value);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_float1__float", true, true);
}
    
    
fc['validation_FixedPointNumberField_FixedPointNumberField__fixed'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkNumberConstraint(context.stringValue, 10, 2) ? true : [context.control.getLabel(),\'10\', \'2\'] : !isNaN(context.value) && this.getForm().numberFormat(context.value, 10, 2);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "FixedPointNumberField__fixed", true, true);
}
    
fc['validation_ComplexListField_complexChild_attribute1_ComplexListField_complexChild_attribute1__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_complexChild_attribute1__length", true, true);
}
    
fc['validation_ComplexListField_dateTimeTz_ComplexListField_dateTimeTz__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_dateTimeTz__datetime", true, true);
}
    
fc['validation_ClassField_complexList_ClassField_complexList__multiplicity'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof tibco.forms.Util.checkMultiplicity != \'undefined\' ? tibco.forms.Util.checkMultiplicity(context.value, 0, 2147483647) ?  true : [this.getLabel(), \'0\'] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassField_complexList__multiplicity", true, false);
}
    
fc['validation_SimpleListField_SimpleListField__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "SimpleListField__length", true, true);
}
    
fc['validation_FixedNumberTypeDecl_FixedNumberTypeDecl__fixed'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkNumberConstraint(context.stringValue, 10, 2) ? true : [context.control.getLabel(),\'10\', \'2\'] : !isNaN(context.value) && this.getForm().numberFormat(context.value, 10, 2);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "FixedNumberTypeDecl__fixed", true, true);
}
    
fc['validation_ComplexListField_complexList_attribute2_ComplexListField_complexList_attribute2__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_complexList_attribute2__length", true, true);
}
    
    
fc['validation_ClassField_date_ClassField_date__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassField_date__datetime", true, true);
}
    
    
    
fc['validation_TextField_TextField__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "TextField__length", true, true);
}
    
fc['validation_ComplexListField_text__master_ComplexListField_text__master__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_text__master__length", true, true);
}
    
fc['validation_ComplexListField_dateTimeTz__master_ComplexListField_dateTimeTz__master__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_dateTimeTz__master__datetime", true, true);
}
    
fc['validation_ClassField_complexChild_attribute1_ClassField_complexChild_attribute1__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassField_complexChild_attribute1__length", true, true);
}
    
fc['validation_ComplexListField_textList__master_ComplexListField_textList__master__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_textList__master__length", true, true);
}
fc['validation_ComplexListField_textList__master_ComplexListField_textList__master__multiplicity'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof tibco.forms.Util.checkMultiplicity != \'undefined\' ? tibco.forms.Util.checkMultiplicity(context.value, 0, 2147483647) ?  true : [this.getLabel(), \'0\'] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_textList__master__multiplicity", true, true);
}
    
fc['validation_DateField_DateField__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "DateField__datetime", true, true);
}
    
    
    
    
fc['validation_ComplexListField_time_ComplexListField_time__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_time__datetime", true, true);
}
    
fc['validation_ClassField_time_ClassField_time__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassField_time__datetime", true, true);
}
    
fc['validation_ComplexListField_complexList_attribute1_ComplexListField_complexList_attribute1__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_complexList_attribute1__length", true, true);
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
       form.registerPackages(['com.example.data.DataPackage']);
       form.registerFactories(['com.example.data.DataFactory']);
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

        this.getNumberField = function(useInternal) {
            return this.form.dataMap['NumberField'].getValue(useInternal);
        };
        this.setNumberField = function(value) {
            this.form.dataMap['NumberField'].setValue(value);
        };
        Object.defineProperty(this, 'NumberField', {
            get: function() {
                return this.form.dataMap['NumberField'].value;
            },
            set: function(value) {
                this.form.dataMap['NumberField'].value = value;
            },
            enumerable: true
        });

        this.getComplexListField = function() {
            return this.form.dataMap['ComplexListField'].getValue();
        };
        Object.defineProperty(this, 'ComplexListField', {
            get: function() {
                return this.form.dataMap['ComplexListField'].value;
            },
            set: function(value) {
                throw "Cannot assign value on a multi-valued parameter/datafield.";
            },
            enumerable: true
        });

        this.getFixedNumberTypeDecl = function(useInternal) {
            return this.form.dataMap['FixedNumberTypeDecl'].getValue(useInternal);
        };
        this.setFixedNumberTypeDecl = function(value) {
            this.form.dataMap['FixedNumberTypeDecl'].setValue(value);
        };
        Object.defineProperty(this, 'FixedNumberTypeDecl', {
            get: function() {
                return this.form.dataMap['FixedNumberTypeDecl'].value;
            },
            set: function(value) {
                this.form.dataMap['FixedNumberTypeDecl'].value = value;
            },
            enumerable: true
        });

        this.getSimpleListField = function(useInternal) {
            return this.form.dataMap['SimpleListField'].getValue(useInternal);
        };
        this.setSimpleListField = function(value) {
            this.form.dataMap['SimpleListField'].setValue(value);
        };
        Object.defineProperty(this, 'SimpleListField', {
            get: function() {
                return this.form.dataMap['SimpleListField'].value;
            },
            set: function(value) {
                throw "Cannot assign value on a multi-valued parameter/datafield.";
            },
            enumerable: true
        });

        this.getClassField = function() {
            return this.form.dataMap['ClassField'].getValue();
        };
        this.setClassField = function(value) {
            this.form.dataMap['ClassField'].setValue(value);
        };
        Object.defineProperty(this, 'ClassField', {
            get: function() {
                return this.form.dataMap['ClassField'].value;
            },
            set: function(value) {
                this.form.dataMap['ClassField'].value = value;
            },
            enumerable: true
        });

        this.getPerformerField = function(useInternal) {
            return this.form.dataMap['PerformerField'].getValue(useInternal);
        };
        this.setPerformerField = function(value) {
            this.form.dataMap['PerformerField'].setValue(value);
        };
        Object.defineProperty(this, 'PerformerField', {
            get: function() {
                return this.form.dataMap['PerformerField'].value;
            },
            set: function(value) {
                this.form.dataMap['PerformerField'].value = value;
            },
            enumerable: true
        });

        this.getTimeField = function(useInternal) {
            return this.form.dataMap['TimeField'].getValue(useInternal);
        };
        this.setTimeField = function(value) {
            this.form.dataMap['TimeField'].setValue(value);
        };
        Object.defineProperty(this, 'TimeField', {
            get: function() {
                return this.form.dataMap['TimeField'].value;
            },
            set: function(value) {
                this.form.dataMap['TimeField'].value = value;
            },
            enumerable: true
        });

        this.getDateField = function(useInternal) {
            return this.form.dataMap['DateField'].getValue(useInternal);
        };
        this.setDateField = function(value) {
            this.form.dataMap['DateField'].setValue(value);
        };
        Object.defineProperty(this, 'DateField', {
            get: function() {
                return this.form.dataMap['DateField'].value;
            },
            set: function(value) {
                this.form.dataMap['DateField'].value = value;
            },
            enumerable: true
        });

        this.getFixedPointNumberField = function(useInternal) {
            return this.form.dataMap['FixedPointNumberField'].getValue(useInternal);
        };
        this.setFixedPointNumberField = function(value) {
            this.form.dataMap['FixedPointNumberField'].setValue(value);
        };
        Object.defineProperty(this, 'FixedPointNumberField', {
            get: function() {
                return this.form.dataMap['FixedPointNumberField'].value;
            },
            set: function(value) {
                this.form.dataMap['FixedPointNumberField'].value = value;
            },
            enumerable: true
        });

        this.getDateTimeField = function(useInternal) {
            return this.form.dataMap['DateTimeField'].getValue(useInternal);
        };
        this.setDateTimeField = function(value) {
            this.form.dataMap['DateTimeField'].setValue(value);
        };
        Object.defineProperty(this, 'DateTimeField', {
            get: function() {
                return this.form.dataMap['DateTimeField'].value;
            },
            set: function(value) {
                this.form.dataMap['DateTimeField'].value = value;
            },
            enumerable: true
        });

        this.getListFixedNumberTypeDecl = function(useInternal) {
            return this.form.dataMap['ListFixedNumberTypeDecl'].getValue(useInternal);
        };
        this.setListFixedNumberTypeDecl = function(value) {
            this.form.dataMap['ListFixedNumberTypeDecl'].setValue(value);
        };
        Object.defineProperty(this, 'ListFixedNumberTypeDecl', {
            get: function() {
                return this.form.dataMap['ListFixedNumberTypeDecl'].value;
            },
            set: function(value) {
                throw "Cannot assign value on a multi-valued parameter/datafield.";
            },
            enumerable: true
        });
    }
       
            
            
            
            
            
            
            
            
            
            
            
       
       
};
tibcoforms.formCode['_0wYbkIhDEemt37zmJf_N4g']['defineValidations']();
