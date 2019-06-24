

if (typeof(tibcoforms) == 'undefined') tibcoforms = new Object();
if (typeof(tibcoforms.formCode) == 'undefined') tibcoforms.formCode = new Object();
tibcoforms.formCode['_f8SRcJQ_EemDnPS3BM6Yfw'] = new Object();
tibcoforms.formCode['_f8SRcJQ_EemDnPS3BM6Yfw']['defineActions'] = function() {
var fc = tibcoforms.formCode['_f8SRcJQ_EemDnPS3BM6Yfw'];
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
    fc['rule_enable_Copy_Of_ComplexListField__detail'] = function(formId, context, thisObj) {
       try {
            tibco.forms.Util.handleComputationAction.call(thisObj, formId, context, thisObj, "enable_Copy_Of_ComplexListField__detail", "enable_Copy_Of_ComplexListField__detail", fc['action_enable_Copy_Of_ComplexListField__detail'], false, 'pane.Copy_Of_ComplexListField__detail');
       } catch(e) {
           tibcoforms.bridge.log_error("Rule(enable_Copy_Of_ComplexListField__detail) Error: " + e);
           throw e;
       }
    }

        fc['action_enable_Copy_Of_ComplexListField__detail'] = function(context, data, pane, control, factory, pkg, f , p) {
                if (context.form && context.form._marked4Cancel)
                    return;
                var resource = context.form.resource;
                var logger = tibcoforms.bridge.log_logger();
                var destName = 'pane.Copy_Of_ComplexListField__detail';
                    var destType = 'pane';
                    var destFeatures = new Array('enabled');
                    var isComplex = false;
                    isComplex = tibcoforms.bridge.comp_isComplexFeature(context.form.id, false, destName, context.cloneUID, 'enabled');                                                                        var tempScr = 'context.newValue != null;';
                var val = eval(tempScr);
                val = tibco.forms.Util.convertExternalValueToInternalValue.call(this, isComplex, val, destName, context);
                    if (isComplex) {
                       if (tibcoforms.FormProxy.isTIBCOFormsList(val))
                           tibcoforms.bridge.compAction_updateCOListDestination(context.form.id, 'action.enable_Copy_Of_ComplexListField__detail', destName, context.cloneUID, destType, destFeatures, val);
                       else
                           tibcoforms.bridge.compAction_updateCODestination(context.form.id, 'action.enable_Copy_Of_ComplexListField__detail', destName, context.cloneUID, destType, destFeatures, val);
                    } else {
                        tibcoforms.bridge.compAction_updateDestination(context.form.id, 'action.enable_Copy_Of_ComplexListField__detail', destName, context.cloneUID, destType, destFeatures, val);
                    }
        }
    fc['rule_enable_MergingComplexListField__detail'] = function(formId, context, thisObj) {
       try {
            tibco.forms.Util.handleComputationAction.call(thisObj, formId, context, thisObj, "enable_MergingComplexListField__detail", "enable_MergingComplexListField__detail", fc['action_enable_MergingComplexListField__detail'], false, 'pane.MergingComplexListField__detail');
       } catch(e) {
           tibcoforms.bridge.log_error("Rule(enable_MergingComplexListField__detail) Error: " + e);
           throw e;
       }
    }

        fc['action_enable_MergingComplexListField__detail'] = function(context, data, pane, control, factory, pkg, f , p) {
                if (context.form && context.form._marked4Cancel)
                    return;
                var resource = context.form.resource;
                var logger = tibcoforms.bridge.log_logger();
                var destName = 'pane.MergingComplexListField__detail';
                    var destType = 'pane';
                    var destFeatures = new Array('enabled');
                    var isComplex = false;
                    isComplex = tibcoforms.bridge.comp_isComplexFeature(context.form.id, false, destName, context.cloneUID, 'enabled');                                                                        var tempScr = 'context.newValue != null;';
                var val = eval(tempScr);
                val = tibco.forms.Util.convertExternalValueToInternalValue.call(this, isComplex, val, destName, context);
                    if (isComplex) {
                       if (tibcoforms.FormProxy.isTIBCOFormsList(val))
                           tibcoforms.bridge.compAction_updateCOListDestination(context.form.id, 'action.enable_MergingComplexListField__detail', destName, context.cloneUID, destType, destFeatures, val);
                       else
                           tibcoforms.bridge.compAction_updateCODestination(context.form.id, 'action.enable_MergingComplexListField__detail', destName, context.cloneUID, destType, destFeatures, val);
                    } else {
                        tibcoforms.bridge.compAction_updateDestination(context.form.id, 'action.enable_MergingComplexListField__detail', destName, context.cloneUID, destType, destFeatures, val);
                    }
        }
    fc['rule_enable_Copy_Of_MergingComplexListField__detail'] = function(formId, context, thisObj) {
       try {
            tibco.forms.Util.handleComputationAction.call(thisObj, formId, context, thisObj, "enable_Copy_Of_MergingComplexListField__detail", "enable_Copy_Of_MergingComplexListField__detail", fc['action_enable_Copy_Of_MergingComplexListField__detail'], false, 'pane.Copy_Of_MergingComplexListField__detail');
       } catch(e) {
           tibcoforms.bridge.log_error("Rule(enable_Copy_Of_MergingComplexListField__detail) Error: " + e);
           throw e;
       }
    }

        fc['action_enable_Copy_Of_MergingComplexListField__detail'] = function(context, data, pane, control, factory, pkg, f , p) {
                if (context.form && context.form._marked4Cancel)
                    return;
                var resource = context.form.resource;
                var logger = tibcoforms.bridge.log_logger();
                var destName = 'pane.Copy_Of_MergingComplexListField__detail';
                    var destType = 'pane';
                    var destFeatures = new Array('enabled');
                    var isComplex = false;
                    isComplex = tibcoforms.bridge.comp_isComplexFeature(context.form.id, false, destName, context.cloneUID, 'enabled');                                                                        var tempScr = 'context.newValue != null;';
                var val = eval(tempScr);
                val = tibco.forms.Util.convertExternalValueToInternalValue.call(this, isComplex, val, destName, context);
                    if (isComplex) {
                       if (tibcoforms.FormProxy.isTIBCOFormsList(val))
                           tibcoforms.bridge.compAction_updateCOListDestination(context.form.id, 'action.enable_Copy_Of_MergingComplexListField__detail', destName, context.cloneUID, destType, destFeatures, val);
                       else
                           tibcoforms.bridge.compAction_updateCODestination(context.form.id, 'action.enable_Copy_Of_MergingComplexListField__detail', destName, context.cloneUID, destType, destFeatures, val);
                    } else {
                        tibcoforms.bridge.compAction_updateDestination(context.form.id, 'action.enable_Copy_Of_MergingComplexListField__detail', destName, context.cloneUID, destType, destFeatures, val);
                    }
        }
    fc['rule_enable_Copy_Of_MergingPartialComplexListField__detail'] = function(formId, context, thisObj) {
       try {
            tibco.forms.Util.handleComputationAction.call(thisObj, formId, context, thisObj, "enable_Copy_Of_MergingPartialComplexListField__detail", "enable_Copy_Of_MergingPartialComplexListField__detail", fc['action_enable_Copy_Of_MergingPartialComplexListField__detail'], false, 'pane.Copy_Of_MergingPartialComplexListField__detail');
       } catch(e) {
           tibcoforms.bridge.log_error("Rule(enable_Copy_Of_MergingPartialComplexListField__detail) Error: " + e);
           throw e;
       }
    }

        fc['action_enable_Copy_Of_MergingPartialComplexListField__detail'] = function(context, data, pane, control, factory, pkg, f , p) {
                if (context.form && context.form._marked4Cancel)
                    return;
                var resource = context.form.resource;
                var logger = tibcoforms.bridge.log_logger();
                var destName = 'pane.Copy_Of_MergingPartialComplexListField__detail';
                    var destType = 'pane';
                    var destFeatures = new Array('enabled');
                    var isComplex = false;
                    isComplex = tibcoforms.bridge.comp_isComplexFeature(context.form.id, false, destName, context.cloneUID, 'enabled');                                                                        var tempScr = 'context.newValue != null;';
                var val = eval(tempScr);
                val = tibco.forms.Util.convertExternalValueToInternalValue.call(this, isComplex, val, destName, context);
                    if (isComplex) {
                       if (tibcoforms.FormProxy.isTIBCOFormsList(val))
                           tibcoforms.bridge.compAction_updateCOListDestination(context.form.id, 'action.enable_Copy_Of_MergingPartialComplexListField__detail', destName, context.cloneUID, destType, destFeatures, val);
                       else
                           tibcoforms.bridge.compAction_updateCODestination(context.form.id, 'action.enable_Copy_Of_MergingPartialComplexListField__detail', destName, context.cloneUID, destType, destFeatures, val);
                    } else {
                        tibcoforms.bridge.compAction_updateDestination(context.form.id, 'action.enable_Copy_Of_MergingPartialComplexListField__detail', destName, context.cloneUID, destType, destFeatures, val);
                    }
        }
    fc['rule_enable_MergingPartialComplexListField__detail'] = function(formId, context, thisObj) {
       try {
            tibco.forms.Util.handleComputationAction.call(thisObj, formId, context, thisObj, "enable_MergingPartialComplexListField__detail", "enable_MergingPartialComplexListField__detail", fc['action_enable_MergingPartialComplexListField__detail'], false, 'pane.MergingPartialComplexListField__detail');
       } catch(e) {
           tibcoforms.bridge.log_error("Rule(enable_MergingPartialComplexListField__detail) Error: " + e);
           throw e;
       }
    }

        fc['action_enable_MergingPartialComplexListField__detail'] = function(context, data, pane, control, factory, pkg, f , p) {
                if (context.form && context.form._marked4Cancel)
                    return;
                var resource = context.form.resource;
                var logger = tibcoforms.bridge.log_logger();
                var destName = 'pane.MergingPartialComplexListField__detail';
                    var destType = 'pane';
                    var destFeatures = new Array('enabled');
                    var isComplex = false;
                    isComplex = tibcoforms.bridge.comp_isComplexFeature(context.form.id, false, destName, context.cloneUID, 'enabled');                                                                        var tempScr = 'context.newValue != null;';
                var val = eval(tempScr);
                val = tibco.forms.Util.convertExternalValueToInternalValue.call(this, isComplex, val, destName, context);
                    if (isComplex) {
                       if (tibcoforms.FormProxy.isTIBCOFormsList(val))
                           tibcoforms.bridge.compAction_updateCOListDestination(context.form.id, 'action.enable_MergingPartialComplexListField__detail', destName, context.cloneUID, destType, destFeatures, val);
                       else
                           tibcoforms.bridge.compAction_updateCODestination(context.form.id, 'action.enable_MergingPartialComplexListField__detail', destName, context.cloneUID, destType, destFeatures, val);
                    } else {
                        tibcoforms.bridge.compAction_updateDestination(context.form.id, 'action.enable_MergingPartialComplexListField__detail', destName, context.cloneUID, destType, destFeatures, val);
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
tibcoforms.formCode['_f8SRcJQ_EemDnPS3BM6Yfw']['defineActions']();

tibcoforms.formCode['_f8SRcJQ_EemDnPS3BM6Yfw']['defineValidations'] = function() {
var fc = tibcoforms.formCode['_f8SRcJQ_EemDnPS3BM6Yfw'];
    
fc['validation_Copy_Of_MergingPartialComplexListField_complexList_attribute2_Copy_Of_MergingPartialComplexListField_complexList_attribute2__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingPartialComplexListField_complexList_attribute2__length", true, true);
}
    
    
    
    
    
    
    
    
    
fc['validation_Copy_Of_ComplexListField_date__master_Copy_Of_ComplexListField_date__master__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ComplexListField_date__master__datetime", true, true);
}
    
fc['validation_Copy_Of_DateField_Copy_Of_DateField__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_DateField__datetime", true, true);
}
    
fc['validation_MergingComplexListField_complexList_MergingComplexListField_complexList__multiplicity'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof tibco.forms.Util.checkMultiplicity != \'undefined\' ? tibco.forms.Util.checkMultiplicity(context.value, 0, 2147483647) ?  true : [this.getLabel(), \'0\'] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingComplexListField_complexList__multiplicity", true, false);
}
    
fc['validation_MergingComplexListField_float1_MergingComplexListField_float1__float'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\'? context.value !== \'\' && !isNaN(context.value) ? true : [context.control.getLabel()] : context.value !== \'\' && !isNaN(context.value);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingComplexListField_float1__float", true, true);
}
    
fc['validation_Copy_Of_ClassFieldtoInflate_dateTimeTz_Copy_Of_ClassFieldtoInflate_dateTimeTz__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ClassFieldtoInflate_dateTimeTz__datetime", true, true);
}
    
fc['validation_ComplexListField_complexList_attribute2_ComplexListField_complexList_attribute2__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_complexList_attribute2__length", true, true);
}
    
    
fc['validation_ComplexListField_text_ComplexListField_text__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_text__length", true, true);
}
    
fc['validation_ClassField_dateTimeTz_ClassField_dateTimeTz__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassField_dateTimeTz__datetime", true, true);
}
    
    
fc['validation_Copy_Of_ClassFieldtoInflate_complexChild_attribute1_Copy_Of_ClassFieldtoInflate_complexChild_attribute1__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ClassFieldtoInflate_complexChild_attribute1__length", true, true);
}
    
fc['validation_Copy_Of_ClassField_date_Copy_Of_ClassField_date__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ClassField_date__datetime", true, true);
}
    
fc['validation_Copy_Of_MergingPartialComplexListField_float1__master_Copy_Of_MergingPartialComplexListField_float1__master__float'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\'? context.value !== \'\' && !isNaN(context.value) ? true : [context.control.getLabel()] : context.value !== \'\' && !isNaN(context.value);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingPartialComplexListField_float1__master__float", true, true);
}
    
    
    
fc['validation_Copy_Of_ComplexListField_float1_Copy_Of_ComplexListField_float1__float'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\'? context.value !== \'\' && !isNaN(context.value) ? true : [context.control.getLabel()] : context.value !== \'\' && !isNaN(context.value);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ComplexListField_float1__float", true, true);
}
    
fc['validation_Copy_Of_MergingPartialComplexListField_dateTimeTz__master_Copy_Of_MergingPartialComplexListField_dateTimeTz__master__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingPartialComplexListField_dateTimeTz__master__datetime", true, true);
}
    
    
fc['validation_SimpleListField_SimpleListField__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "SimpleListField__length", true, true);
}
    
fc['validation_ClassFieldtoInflate_time_ClassFieldtoInflate_time__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassFieldtoInflate_time__datetime", true, true);
}
    
    
fc['validation_Copy_Of_MergingPartialComplexListField_text__master_Copy_Of_MergingPartialComplexListField_text__master__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingPartialComplexListField_text__master__length", true, true);
}
    
fc['validation_MergingComplexListField_textList__master_MergingComplexListField_textList__master__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingComplexListField_textList__master__length", true, true);
}
fc['validation_MergingComplexListField_textList__master_MergingComplexListField_textList__master__multiplicity'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof tibco.forms.Util.checkMultiplicity != \'undefined\' ? tibco.forms.Util.checkMultiplicity(context.value, 0, 2147483647) ?  true : [this.getLabel(), \'0\'] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingComplexListField_textList__master__multiplicity", true, true);
}
    
fc['validation_MergingComplexListField_URI__master_MergingComplexListField_URI__master__pattern'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'if (typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\') {\
  \n tibco.forms.Util.checkRegExp(context.value, new RegExp(\"^([a-zA-Z0-9+.\\\\-]+):(//((([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:]|%[0-9A-F]{2})*)@)?(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=]|%[0-9A-F]{2})*)(:(\\\\d*))?(/([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?|(/?([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@]|%[0-9A-F]{2})+([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?)(\\\\?(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?(#(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?$\")) ? true : [context.control.getLabel(), \'URI\'];\
  \n} else {\
  \nvar regex = new RegExp(\"^([a-zA-Z0-9+.\\-]+):(//((([a-zA-Z0-9\\-._~!$&\'()*+,;=:]|%[0-9A-F]{2})*)@)?(([a-zA-Z0-9\\-._~!$&\'()*+,;=]|%[0-9A-F]{2})*)(:(\\d*))?(/([a-zA-Z0-9\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?|(/?([a-zA-Z0-9\\-._~!$&\'()*+,;=:@]|%[0-9A-F]{2})+([a-zA-Z0-9\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?)(\\?(([a-zA-Z0-9\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?(#(([a-zA-Z0-9\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?$\");\
  \nregex.test(context.value);\
  \n}';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingComplexListField_URI__master__pattern", true, true);
}
    
    
fc['validation_Copy_Of_MergingPartialComplexListField_complexList_attribute1_Copy_Of_MergingPartialComplexListField_complexList_attribute1__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingPartialComplexListField_complexList_attribute1__length", true, true);
}
    
    
    
    
fc['validation_MergingPartialComplexListField_URI__master_MergingPartialComplexListField_URI__master__pattern'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'if (typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\') {\
  \n tibco.forms.Util.checkRegExp(context.value, new RegExp(\"^([a-zA-Z0-9+.\\\\-]+):(//((([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:]|%[0-9A-F]{2})*)@)?(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=]|%[0-9A-F]{2})*)(:(\\\\d*))?(/([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?|(/?([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@]|%[0-9A-F]{2})+([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?)(\\\\?(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?(#(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?$\")) ? true : [context.control.getLabel(), \'URI\'];\
  \n} else {\
  \nvar regex = new RegExp(\"^([a-zA-Z0-9+.\\-]+):(//((([a-zA-Z0-9\\-._~!$&\'()*+,;=:]|%[0-9A-F]{2})*)@)?(([a-zA-Z0-9\\-._~!$&\'()*+,;=]|%[0-9A-F]{2})*)(:(\\d*))?(/([a-zA-Z0-9\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?|(/?([a-zA-Z0-9\\-._~!$&\'()*+,;=:@]|%[0-9A-F]{2})+([a-zA-Z0-9\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?)(\\?(([a-zA-Z0-9\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?(#(([a-zA-Z0-9\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?$\");\
  \nregex.test(context.value);\
  \n}';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingPartialComplexListField_URI__master__pattern", true, true);
}
    
fc['validation_ComplexListField_complexList_ComplexListField_complexList__multiplicity'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof tibco.forms.Util.checkMultiplicity != \'undefined\' ? tibco.forms.Util.checkMultiplicity(context.value, 0, 2147483647) ?  true : [this.getLabel(), \'0\'] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_complexList__multiplicity", true, false);
}
    
fc['validation_Copy_Of_ComplexListField_textList__master_Copy_Of_ComplexListField_textList__master__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ComplexListField_textList__master__length", true, true);
}
fc['validation_Copy_Of_ComplexListField_textList__master_Copy_Of_ComplexListField_textList__master__multiplicity'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof tibco.forms.Util.checkMultiplicity != \'undefined\' ? tibco.forms.Util.checkMultiplicity(context.value, 0, 2147483647) ?  true : [this.getLabel(), \'0\'] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ComplexListField_textList__master__multiplicity", true, true);
}
    
    
fc['validation_Copy_Of_MergingComplexListField_float1__master_Copy_Of_MergingComplexListField_float1__master__float'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\'? context.value !== \'\' && !isNaN(context.value) ? true : [context.control.getLabel()] : context.value !== \'\' && !isNaN(context.value);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingComplexListField_float1__master__float", true, true);
}
    
fc['validation_MergingPartialComplexListField_text_MergingPartialComplexListField_text__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingPartialComplexListField_text__length", true, true);
}
    
fc['validation_Copy_Of_ComplexListField_complexList_attribute1_Copy_Of_ComplexListField_complexList_attribute1__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ComplexListField_complexList_attribute1__length", true, true);
}
    
    
    
fc['validation_Copy_Of_MergingComplexListField_text__master_Copy_Of_MergingComplexListField_text__master__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingComplexListField_text__master__length", true, true);
}
    
    
    
    
fc['validation_ComplexListField_text__master_ComplexListField_text__master__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_text__master__length", true, true);
}
    
fc['validation_ComplexListField_textList_ComplexListField_textList__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_textList__length", true, true);
}
fc['validation_ComplexListField_textList_ComplexListField_textList__multiplicity'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof tibco.forms.Util.checkMultiplicity != \'undefined\' ? tibco.forms.Util.checkMultiplicity(context.value, 0, 2147483647) ?  true : [this.getLabel(), \'0\'] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_textList__multiplicity", true, true);
}
    
fc['validation_ClassFieldtoInflate_complexList_ClassFieldtoInflate_complexList__multiplicity'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof tibco.forms.Util.checkMultiplicity != \'undefined\' ? tibco.forms.Util.checkMultiplicity(context.value, 0, 2147483647) ?  true : [this.getLabel(), \'0\'] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassFieldtoInflate_complexList__multiplicity", true, false);
}
    
    
fc['validation_MergingPartialComplexListField_dateTimeTz_MergingPartialComplexListField_dateTimeTz__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingPartialComplexListField_dateTimeTz__datetime", true, true);
}
    
    
fc['validation_MergingComplexListField_complexList_attribute1_MergingComplexListField_complexList_attribute1__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingComplexListField_complexList_attribute1__length", true, true);
}
    
    
fc['validation_ClassFieldtoInflate_float1_ClassFieldtoInflate_float1__float'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\'? context.value !== \'\' && !isNaN(context.value) ? true : [context.control.getLabel()] : context.value !== \'\' && !isNaN(context.value);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassFieldtoInflate_float1__float", true, true);
}
    
fc['validation_Copy_Of_ClassFieldtoInflate_URI_Copy_Of_ClassFieldtoInflate_URI__pattern'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'if (typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\') {\
  \n tibco.forms.Util.checkRegExp(context.value, new RegExp(\"^([a-zA-Z0-9+.\\\\-]+):(//((([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:]|%[0-9A-F]{2})*)@)?(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=]|%[0-9A-F]{2})*)(:(\\\\d*))?(/([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?|(/?([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@]|%[0-9A-F]{2})+([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?)(\\\\?(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?(#(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?$\")) ? true : [context.control.getLabel(), \'URI\'];\
  \n} else {\
  \nvar regex = new RegExp(\"^([a-zA-Z0-9+.\\-]+):(//((([a-zA-Z0-9\\-._~!$&\'()*+,;=:]|%[0-9A-F]{2})*)@)?(([a-zA-Z0-9\\-._~!$&\'()*+,;=]|%[0-9A-F]{2})*)(:(\\d*))?(/([a-zA-Z0-9\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?|(/?([a-zA-Z0-9\\-._~!$&\'()*+,;=:@]|%[0-9A-F]{2})+([a-zA-Z0-9\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?)(\\?(([a-zA-Z0-9\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?(#(([a-zA-Z0-9\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?$\");\
  \nregex.test(context.value);\
  \n}';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ClassFieldtoInflate_URI__pattern", true, true);
}
    
fc['validation_MergingComplexListField_float1__master_MergingComplexListField_float1__master__float'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\'? context.value !== \'\' && !isNaN(context.value) ? true : [context.control.getLabel()] : context.value !== \'\' && !isNaN(context.value);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingComplexListField_float1__master__float", true, true);
}
    
fc['validation_ClassField_complexList_attribute2_ClassField_complexList_attribute2__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassField_complexList_attribute2__length", true, true);
}
    
    
    
    
    
fc['validation_Copy_Of_ClassField_fixed_Copy_Of_ClassField_fixed__fixed'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkNumberConstraint(context.stringValue, 10, 2) ? true : [context.control.getLabel(),\'10\', \'2\'] : !isNaN(context.value) && this.getForm().numberFormat(context.value, 10, 2);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ClassField_fixed__fixed", true, true);
}
    
fc['validation_TextField_TextField__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "TextField__length", true, true);
}
    
    
fc['validation_ClassFieldtoInflate_complexList_attribute2_ClassFieldtoInflate_complexList_attribute2__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassFieldtoInflate_complexList_attribute2__length", true, true);
}
    
fc['validation_MergingPartialComplexListField_fixed__master_MergingPartialComplexListField_fixed__master__fixed'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkNumberConstraint(context.stringValue, 10, 2) ? true : [context.control.getLabel(),\'10\', \'2\'] : !isNaN(context.value) && this.getForm().numberFormat(context.value, 10, 2);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingPartialComplexListField_fixed__master__fixed", true, true);
}
    
    
    
fc['validation_MergingPartialComplexListField_float1__master_MergingPartialComplexListField_float1__master__float'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\'? context.value !== \'\' && !isNaN(context.value) ? true : [context.control.getLabel()] : context.value !== \'\' && !isNaN(context.value);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingPartialComplexListField_float1__master__float", true, true);
}
    
    
fc['validation_ComplexListField_date__master_ComplexListField_date__master__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_date__master__datetime", true, true);
}
    
    
    
fc['validation_ListFixedNumberTypeDecl_ListFixedNumberTypeDecl__fixed'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkNumberConstraint(context.stringValue, 10, 2) ? true : [context.control.getLabel(),\'10\', \'2\'] : !isNaN(context.value) && this.getForm().numberFormat(context.value, 10, 2);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ListFixedNumberTypeDecl__fixed", true, true);
}
    
fc['validation_Copy_Of_MergingPartialComplexListField_complexList_Copy_Of_MergingPartialComplexListField_complexList__multiplicity'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof tibco.forms.Util.checkMultiplicity != \'undefined\' ? tibco.forms.Util.checkMultiplicity(context.value, 0, 2147483647) ?  true : [this.getLabel(), \'0\'] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingPartialComplexListField_complexList__multiplicity", true, false);
}
    
fc['validation_Copy_Of_ClassFieldtoInflate_time_Copy_Of_ClassFieldtoInflate_time__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ClassFieldtoInflate_time__datetime", true, true);
}
    
fc['validation_MergingComplexListField_text__master_MergingComplexListField_text__master__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingComplexListField_text__master__length", true, true);
}
    
    
    
    
fc['validation_Copy_Of_ComplexListField_URI__master_Copy_Of_ComplexListField_URI__master__pattern'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'if (typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\') {\
  \n tibco.forms.Util.checkRegExp(context.value, new RegExp(\"^([a-zA-Z0-9+.\\\\-]+):(//((([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:]|%[0-9A-F]{2})*)@)?(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=]|%[0-9A-F]{2})*)(:(\\\\d*))?(/([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?|(/?([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@]|%[0-9A-F]{2})+([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?)(\\\\?(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?(#(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?$\")) ? true : [context.control.getLabel(), \'URI\'];\
  \n} else {\
  \nvar regex = new RegExp(\"^([a-zA-Z0-9+.\\-]+):(//((([a-zA-Z0-9\\-._~!$&\'()*+,;=:]|%[0-9A-F]{2})*)@)?(([a-zA-Z0-9\\-._~!$&\'()*+,;=]|%[0-9A-F]{2})*)(:(\\d*))?(/([a-zA-Z0-9\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?|(/?([a-zA-Z0-9\\-._~!$&\'()*+,;=:@]|%[0-9A-F]{2})+([a-zA-Z0-9\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?)(\\?(([a-zA-Z0-9\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?(#(([a-zA-Z0-9\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?$\");\
  \nregex.test(context.value);\
  \n}';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ComplexListField_URI__master__pattern", true, true);
}
    
fc['validation_Copy_Of_MergingComplexListField_date__master_Copy_Of_MergingComplexListField_date__master__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingComplexListField_date__master__datetime", true, true);
}
    
fc['validation_ClassField_complexChild_attribute1_ClassField_complexChild_attribute1__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassField_complexChild_attribute1__length", true, true);
}
    
fc['validation_Copy_Of_ClassFieldtoInflate_complexChild_attribute2_Copy_Of_ClassFieldtoInflate_complexChild_attribute2__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ClassFieldtoInflate_complexChild_attribute2__length", true, true);
}
    
fc['validation_ClassField_textList_ClassField_textList__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassField_textList__length", true, true);
}
fc['validation_ClassField_textList_ClassField_textList__multiplicity'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof tibco.forms.Util.checkMultiplicity != \'undefined\' ? tibco.forms.Util.checkMultiplicity(context.value, 0, 2147483647) ?  true : [this.getLabel(), \'0\'] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassField_textList__multiplicity", true, true);
}
    
fc['validation_Copy_Of_ClassField_time_Copy_Of_ClassField_time__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ClassField_time__datetime", true, true);
}
    
    
    
fc['validation_Copy_Of_ComplexListField_dateTimeTz_Copy_Of_ComplexListField_dateTimeTz__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ComplexListField_dateTimeTz__datetime", true, true);
}
    
fc['validation_Copy_Of_MergingPartialComplexListField_dateTimeTz_Copy_Of_MergingPartialComplexListField_dateTimeTz__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingPartialComplexListField_dateTimeTz__datetime", true, true);
}
    
fc['validation_Copy_Of_ClassFieldtoInflate_complexList_attribute1_Copy_Of_ClassFieldtoInflate_complexList_attribute1__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ClassFieldtoInflate_complexList_attribute1__length", true, true);
}
    
fc['validation_MergingPartialComplexListField_complexChild_attribute2_MergingPartialComplexListField_complexChild_attribute2__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingPartialComplexListField_complexChild_attribute2__length", true, true);
}
    
    
    
    
    
    
fc['validation_Copy_Of_MergingPartialComplexListField_textList_Copy_Of_MergingPartialComplexListField_textList__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingPartialComplexListField_textList__length", true, true);
}
fc['validation_Copy_Of_MergingPartialComplexListField_textList_Copy_Of_MergingPartialComplexListField_textList__multiplicity'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof tibco.forms.Util.checkMultiplicity != \'undefined\' ? tibco.forms.Util.checkMultiplicity(context.value, 0, 2147483647) ?  true : [this.getLabel(), \'0\'] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingPartialComplexListField_textList__multiplicity", true, true);
}
    
    
    
fc['validation_ComplexListField_time_ComplexListField_time__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_time__datetime", true, true);
}
    
fc['validation_ClassField_complexList_attribute1_ClassField_complexList_attribute1__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassField_complexList_attribute1__length", true, true);
}
    
fc['validation_Copy_Of_ComplexListField_complexList_attribute2_Copy_Of_ComplexListField_complexList_attribute2__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ComplexListField_complexList_attribute2__length", true, true);
}
    
fc['validation_Copy_Of_ClassFieldtoInflate_complexList_attribute2_Copy_Of_ClassFieldtoInflate_complexList_attribute2__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ClassFieldtoInflate_complexList_attribute2__length", true, true);
}
    
fc['validation_Copy_Of_ClassField_complexList_Copy_Of_ClassField_complexList__multiplicity'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof tibco.forms.Util.checkMultiplicity != \'undefined\' ? tibco.forms.Util.checkMultiplicity(context.value, 0, 2147483647) ?  true : [this.getLabel(), \'0\'] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ClassField_complexList__multiplicity", true, false);
}
    
    
    
    
fc['validation_ClassField_fixed_ClassField_fixed__fixed'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkNumberConstraint(context.stringValue, 10, 2) ? true : [context.control.getLabel(),\'10\', \'2\'] : !isNaN(context.value) && this.getForm().numberFormat(context.value, 10, 2);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassField_fixed__fixed", true, true);
}
    
    
fc['validation_Copy_Of_FixedNumberTypeDecl_Copy_Of_FixedNumberTypeDecl__fixed'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkNumberConstraint(context.stringValue, 10, 2) ? true : [context.control.getLabel(),\'10\', \'2\'] : !isNaN(context.value) && this.getForm().numberFormat(context.value, 10, 2);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_FixedNumberTypeDecl__fixed", true, true);
}
    
fc['validation_Copy_Of_FixedPointNumberField_Copy_Of_FixedPointNumberField__fixed'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkNumberConstraint(context.stringValue, 10, 2) ? true : [context.control.getLabel(),\'10\', \'2\'] : !isNaN(context.value) && this.getForm().numberFormat(context.value, 10, 2);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_FixedPointNumberField__fixed", true, true);
}
    
    
fc['validation_MergingComplexListField_dateTimeTz__master_MergingComplexListField_dateTimeTz__master__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingComplexListField_dateTimeTz__master__datetime", true, true);
}
    
fc['validation_Copy_Of_MergingPartialComplexListField_fixed_Copy_Of_MergingPartialComplexListField_fixed__fixed'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkNumberConstraint(context.stringValue, 10, 2) ? true : [context.control.getLabel(),\'10\', \'2\'] : !isNaN(context.value) && this.getForm().numberFormat(context.value, 10, 2);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingPartialComplexListField_fixed__fixed", true, true);
}
    
    
    
    
    
fc['validation_MergingPartialComplexListField_textList_MergingPartialComplexListField_textList__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingPartialComplexListField_textList__length", true, true);
}
fc['validation_MergingPartialComplexListField_textList_MergingPartialComplexListField_textList__multiplicity'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof tibco.forms.Util.checkMultiplicity != \'undefined\' ? tibco.forms.Util.checkMultiplicity(context.value, 0, 2147483647) ?  true : [this.getLabel(), \'0\'] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingPartialComplexListField_textList__multiplicity", true, true);
}
    
fc['validation_MergingPartialComplexListField_date__master_MergingPartialComplexListField_date__master__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingPartialComplexListField_date__master__datetime", true, true);
}
    
    
fc['validation_Copy_Of_MergingComplexListField_URI__master_Copy_Of_MergingComplexListField_URI__master__pattern'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'if (typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\') {\
  \n tibco.forms.Util.checkRegExp(context.value, new RegExp(\"^([a-zA-Z0-9+.\\\\-]+):(//((([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:]|%[0-9A-F]{2})*)@)?(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=]|%[0-9A-F]{2})*)(:(\\\\d*))?(/([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?|(/?([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@]|%[0-9A-F]{2})+([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?)(\\\\?(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?(#(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?$\")) ? true : [context.control.getLabel(), \'URI\'];\
  \n} else {\
  \nvar regex = new RegExp(\"^([a-zA-Z0-9+.\\-]+):(//((([a-zA-Z0-9\\-._~!$&\'()*+,;=:]|%[0-9A-F]{2})*)@)?(([a-zA-Z0-9\\-._~!$&\'()*+,;=]|%[0-9A-F]{2})*)(:(\\d*))?(/([a-zA-Z0-9\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?|(/?([a-zA-Z0-9\\-._~!$&\'()*+,;=:@]|%[0-9A-F]{2})+([a-zA-Z0-9\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?)(\\?(([a-zA-Z0-9\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?(#(([a-zA-Z0-9\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?$\");\
  \nregex.test(context.value);\
  \n}';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingComplexListField_URI__master__pattern", true, true);
}
    
    
    
fc['validation_Copy_Of_ComplexListField_complexList_Copy_Of_ComplexListField_complexList__multiplicity'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof tibco.forms.Util.checkMultiplicity != \'undefined\' ? tibco.forms.Util.checkMultiplicity(context.value, 0, 2147483647) ?  true : [this.getLabel(), \'0\'] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ComplexListField_complexList__multiplicity", true, false);
}
    
fc['validation_Copy_Of_ComplexListField_date_Copy_Of_ComplexListField_date__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ComplexListField_date__datetime", true, true);
}
    
    
fc['validation_MergingComplexListField_time__master_MergingComplexListField_time__master__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingComplexListField_time__master__datetime", true, true);
}
    
    
    
    
fc['validation_Copy_Of_DateTimeField_Copy_Of_DateTimeField__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_DateTimeField__datetime", true, true);
}
    
fc['validation_Copy_Of_MergingPartialComplexListField_complexChild_attribute2_Copy_Of_MergingPartialComplexListField_complexChild_attribute2__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingPartialComplexListField_complexChild_attribute2__length", true, true);
}
    
fc['validation_ComplexListField_complexChild_attribute2_ComplexListField_complexChild_attribute2__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_complexChild_attribute2__length", true, true);
}
    
fc['validation_ClassField_date_ClassField_date__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassField_date__datetime", true, true);
}
    
    
    
fc['validation_Copy_Of_MergingPartialComplexListField_URI__master_Copy_Of_MergingPartialComplexListField_URI__master__pattern'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'if (typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\') {\
  \n tibco.forms.Util.checkRegExp(context.value, new RegExp(\"^([a-zA-Z0-9+.\\\\-]+):(//((([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:]|%[0-9A-F]{2})*)@)?(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=]|%[0-9A-F]{2})*)(:(\\\\d*))?(/([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?|(/?([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@]|%[0-9A-F]{2})+([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?)(\\\\?(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?(#(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?$\")) ? true : [context.control.getLabel(), \'URI\'];\
  \n} else {\
  \nvar regex = new RegExp(\"^([a-zA-Z0-9+.\\-]+):(//((([a-zA-Z0-9\\-._~!$&\'()*+,;=:]|%[0-9A-F]{2})*)@)?(([a-zA-Z0-9\\-._~!$&\'()*+,;=]|%[0-9A-F]{2})*)(:(\\d*))?(/([a-zA-Z0-9\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?|(/?([a-zA-Z0-9\\-._~!$&\'()*+,;=:@]|%[0-9A-F]{2})+([a-zA-Z0-9\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?)(\\?(([a-zA-Z0-9\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?(#(([a-zA-Z0-9\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?$\");\
  \nregex.test(context.value);\
  \n}';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingPartialComplexListField_URI__master__pattern", true, true);
}
    
    
fc['validation_ClassFieldtoInflate_complexList_attribute1_ClassFieldtoInflate_complexList_attribute1__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassFieldtoInflate_complexList_attribute1__length", true, true);
}
    
    
fc['validation_Copy_Of_MergingPartialComplexListField_complexChild_attribute1_Copy_Of_MergingPartialComplexListField_complexChild_attribute1__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingPartialComplexListField_complexChild_attribute1__length", true, true);
}
    
    
    
    
    
    
fc['validation_MergingPartialComplexListField_complexList_attribute1_MergingPartialComplexListField_complexList_attribute1__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingPartialComplexListField_complexList_attribute1__length", true, true);
}
    
fc['validation_ComplexListField_complexChild_attribute1_ComplexListField_complexChild_attribute1__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_complexChild_attribute1__length", true, true);
}
    
    
    
fc['validation_Copy_Of_ComplexListField_fixed__master_Copy_Of_ComplexListField_fixed__master__fixed'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkNumberConstraint(context.stringValue, 10, 2) ? true : [context.control.getLabel(),\'10\', \'2\'] : !isNaN(context.value) && this.getForm().numberFormat(context.value, 10, 2);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ComplexListField_fixed__master__fixed", true, true);
}
    
fc['validation_ClassField_time_ClassField_time__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassField_time__datetime", true, true);
}
    
fc['validation_Copy_Of_ClassField_complexChild_attribute2_Copy_Of_ClassField_complexChild_attribute2__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ClassField_complexChild_attribute2__length", true, true);
}
    
fc['validation_DateField_DateField__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "DateField__datetime", true, true);
}
    
fc['validation_ClassFieldtoInflate_fixed_ClassFieldtoInflate_fixed__fixed'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkNumberConstraint(context.stringValue, 10, 2) ? true : [context.control.getLabel(),\'10\', \'2\'] : !isNaN(context.value) && this.getForm().numberFormat(context.value, 10, 2);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassFieldtoInflate_fixed__fixed", true, true);
}
    
fc['validation_Copy_Of_ComplexListField_time_Copy_Of_ComplexListField_time__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ComplexListField_time__datetime", true, true);
}
    
fc['validation_Copy_Of_ComplexListField_text_Copy_Of_ComplexListField_text__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ComplexListField_text__length", true, true);
}
    
    
fc['validation_Copy_Of_MergingPartialComplexListField_time__master_Copy_Of_MergingPartialComplexListField_time__master__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingPartialComplexListField_time__master__datetime", true, true);
}
    
fc['validation_Copy_Of_MergingComplexListField_complexList_Copy_Of_MergingComplexListField_complexList__multiplicity'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof tibco.forms.Util.checkMultiplicity != \'undefined\' ? tibco.forms.Util.checkMultiplicity(context.value, 0, 2147483647) ?  true : [this.getLabel(), \'0\'] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingComplexListField_complexList__multiplicity", true, false);
}
    
fc['validation_Copy_Of_MergingComplexListField_time__master_Copy_Of_MergingComplexListField_time__master__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingComplexListField_time__master__datetime", true, true);
}
    
fc['validation_Copy_Of_MergingPartialComplexListField_fixed__master_Copy_Of_MergingPartialComplexListField_fixed__master__fixed'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkNumberConstraint(context.stringValue, 10, 2) ? true : [context.control.getLabel(),\'10\', \'2\'] : !isNaN(context.value) && this.getForm().numberFormat(context.value, 10, 2);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingPartialComplexListField_fixed__master__fixed", true, true);
}
    
fc['validation_ClassField_complexList_ClassField_complexList__multiplicity'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof tibco.forms.Util.checkMultiplicity != \'undefined\' ? tibco.forms.Util.checkMultiplicity(context.value, 0, 2147483647) ?  true : [this.getLabel(), \'0\'] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassField_complexList__multiplicity", true, false);
}
    
    
fc['validation_MergingComplexListField_text_MergingComplexListField_text__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingComplexListField_text__length", true, true);
}
    
    
    
fc['validation_ClassField_complexChild_attribute2_ClassField_complexChild_attribute2__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassField_complexChild_attribute2__length", true, true);
}
    
    
fc['validation_Copy_Of_ClassField_complexList_attribute2_Copy_Of_ClassField_complexList_attribute2__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ClassField_complexList_attribute2__length", true, true);
}
    
    
fc['validation_Copy_Of_ComplexListField_complexChild_attribute2_Copy_Of_ComplexListField_complexChild_attribute2__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ComplexListField_complexChild_attribute2__length", true, true);
}
    
    
    
fc['validation_ClassFieldtoInflate_complexChild_attribute1_ClassFieldtoInflate_complexChild_attribute1__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassFieldtoInflate_complexChild_attribute1__length", true, true);
}
    
fc['validation_Copy_Of_ClassFieldtoInflate_float1_Copy_Of_ClassFieldtoInflate_float1__float'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\'? context.value !== \'\' && !isNaN(context.value) ? true : [context.control.getLabel()] : context.value !== \'\' && !isNaN(context.value);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ClassFieldtoInflate_float1__float", true, true);
}
    
fc['validation_Copy_Of_MergingPartialComplexListField_text_Copy_Of_MergingPartialComplexListField_text__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingPartialComplexListField_text__length", true, true);
}
    
    
fc['validation_ComplexListField_date_ComplexListField_date__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_date__datetime", true, true);
}
    
    
    
fc['validation_NumberField_NumberField__float'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\'? context.value !== \'\' && !isNaN(context.value) ? true : [context.control.getLabel()] : context.value !== \'\' && !isNaN(context.value);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "NumberField__float", true, true);
}
    
fc['validation_MergingComplexListField_fixed_MergingComplexListField_fixed__fixed'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkNumberConstraint(context.stringValue, 10, 2) ? true : [context.control.getLabel(),\'10\', \'2\'] : !isNaN(context.value) && this.getForm().numberFormat(context.value, 10, 2);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingComplexListField_fixed__fixed", true, true);
}
    
    
fc['validation_ComplexListField_textList__master_ComplexListField_textList__master__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_textList__master__length", true, true);
}
fc['validation_ComplexListField_textList__master_ComplexListField_textList__master__multiplicity'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof tibco.forms.Util.checkMultiplicity != \'undefined\' ? tibco.forms.Util.checkMultiplicity(context.value, 0, 2147483647) ?  true : [this.getLabel(), \'0\'] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_textList__master__multiplicity", true, true);
}
    
    
fc['validation_Copy_Of_ComplexListField_URI_Copy_Of_ComplexListField_URI__pattern'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'if (typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\') {\
  \n tibco.forms.Util.checkRegExp(context.value, new RegExp(\"^([a-zA-Z0-9+.\\\\-]+):(//((([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:]|%[0-9A-F]{2})*)@)?(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=]|%[0-9A-F]{2})*)(:(\\\\d*))?(/([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?|(/?([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@]|%[0-9A-F]{2})+([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?)(\\\\?(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?(#(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?$\")) ? true : [context.control.getLabel(), \'URI\'];\
  \n} else {\
  \nvar regex = new RegExp(\"^([a-zA-Z0-9+.\\-]+):(//((([a-zA-Z0-9\\-._~!$&\'()*+,;=:]|%[0-9A-F]{2})*)@)?(([a-zA-Z0-9\\-._~!$&\'()*+,;=]|%[0-9A-F]{2})*)(:(\\d*))?(/([a-zA-Z0-9\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?|(/?([a-zA-Z0-9\\-._~!$&\'()*+,;=:@]|%[0-9A-F]{2})+([a-zA-Z0-9\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?)(\\?(([a-zA-Z0-9\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?(#(([a-zA-Z0-9\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?$\");\
  \nregex.test(context.value);\
  \n}';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ComplexListField_URI__pattern", true, true);
}
    
fc['validation_Copy_Of_MergingComplexListField_fixed_Copy_Of_MergingComplexListField_fixed__fixed'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkNumberConstraint(context.stringValue, 10, 2) ? true : [context.control.getLabel(),\'10\', \'2\'] : !isNaN(context.value) && this.getForm().numberFormat(context.value, 10, 2);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingComplexListField_fixed__fixed", true, true);
}
    
fc['validation_Copy_Of_TimeField_Copy_Of_TimeField__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_TimeField__datetime", true, true);
}
    
    
fc['validation_ClassFieldtoInflate_complexChild_attribute2_ClassFieldtoInflate_complexChild_attribute2__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassFieldtoInflate_complexChild_attribute2__length", true, true);
}
    
    
    
    
    
fc['validation_MergingComplexListField_time_MergingComplexListField_time__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingComplexListField_time__datetime", true, true);
}
    
    
    
fc['validation_Copy_Of_MergingComplexListField_complexList_attribute1_Copy_Of_MergingComplexListField_complexList_attribute1__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingComplexListField_complexList_attribute1__length", true, true);
}
    
fc['validation_ClassFieldtoInflate_dateTimeTz_ClassFieldtoInflate_dateTimeTz__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassFieldtoInflate_dateTimeTz__datetime", true, true);
}
    
    
fc['validation_DateTimeField_DateTimeField__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "DateTimeField__datetime", true, true);
}
    
    
    
fc['validation_Copy_Of_MergingComplexListField_dateTimeTz__master_Copy_Of_MergingComplexListField_dateTimeTz__master__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingComplexListField_dateTimeTz__master__datetime", true, true);
}
    
fc['validation_ComplexListField_complexList_attribute1_ComplexListField_complexList_attribute1__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_complexList_attribute1__length", true, true);
}
    
fc['validation_MergingPartialComplexListField_float1_MergingPartialComplexListField_float1__float'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\'? context.value !== \'\' && !isNaN(context.value) ? true : [context.control.getLabel()] : context.value !== \'\' && !isNaN(context.value);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingPartialComplexListField_float1__float", true, true);
}
    
    
fc['validation_Copy_Of_ListFixedNumberTypeDecl_Copy_Of_ListFixedNumberTypeDecl__fixed'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkNumberConstraint(context.stringValue, 10, 2) ? true : [context.control.getLabel(),\'10\', \'2\'] : !isNaN(context.value) && this.getForm().numberFormat(context.value, 10, 2);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ListFixedNumberTypeDecl__fixed", true, true);
}
    
fc['validation_ComplexListField_dateTimeTz_ComplexListField_dateTimeTz__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_dateTimeTz__datetime", true, true);
}
    
fc['validation_Copy_Of_TextField_Copy_Of_TextField__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_TextField__length", true, true);
}
    
fc['validation_Copy_Of_MergingComplexListField_textList_Copy_Of_MergingComplexListField_textList__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingComplexListField_textList__length", true, true);
}
fc['validation_Copy_Of_MergingComplexListField_textList_Copy_Of_MergingComplexListField_textList__multiplicity'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof tibco.forms.Util.checkMultiplicity != \'undefined\' ? tibco.forms.Util.checkMultiplicity(context.value, 0, 2147483647) ?  true : [this.getLabel(), \'0\'] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingComplexListField_textList__multiplicity", true, true);
}
    
fc['validation_Copy_Of_ClassFieldtoInflate_date_Copy_Of_ClassFieldtoInflate_date__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ClassFieldtoInflate_date__datetime", true, true);
}
    
fc['validation_Copy_Of_ComplexListField_time__master_Copy_Of_ComplexListField_time__master__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ComplexListField_time__master__datetime", true, true);
}
    
fc['validation_Copy_Of_MergingComplexListField_time_Copy_Of_MergingComplexListField_time__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingComplexListField_time__datetime", true, true);
}
    
    
    
fc['validation_ClassFieldtoInflate_date_ClassFieldtoInflate_date__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassFieldtoInflate_date__datetime", true, true);
}
    
    
fc['validation_MergingComplexListField_date_MergingComplexListField_date__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingComplexListField_date__datetime", true, true);
}
    
    
    
fc['validation_MergingPartialComplexListField_fixed_MergingPartialComplexListField_fixed__fixed'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkNumberConstraint(context.stringValue, 10, 2) ? true : [context.control.getLabel(),\'10\', \'2\'] : !isNaN(context.value) && this.getForm().numberFormat(context.value, 10, 2);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingPartialComplexListField_fixed__fixed", true, true);
}
    
fc['validation_Copy_Of_ClassFieldtoInflate_fixed_Copy_Of_ClassFieldtoInflate_fixed__fixed'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkNumberConstraint(context.stringValue, 10, 2) ? true : [context.control.getLabel(),\'10\', \'2\'] : !isNaN(context.value) && this.getForm().numberFormat(context.value, 10, 2);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ClassFieldtoInflate_fixed__fixed", true, true);
}
    
    
fc['validation_MergingComplexListField_URI_MergingComplexListField_URI__pattern'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'if (typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\') {\
  \n tibco.forms.Util.checkRegExp(context.value, new RegExp(\"^([a-zA-Z0-9+.\\\\-]+):(//((([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:]|%[0-9A-F]{2})*)@)?(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=]|%[0-9A-F]{2})*)(:(\\\\d*))?(/([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?|(/?([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@]|%[0-9A-F]{2})+([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?)(\\\\?(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?(#(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?$\")) ? true : [context.control.getLabel(), \'URI\'];\
  \n} else {\
  \nvar regex = new RegExp(\"^([a-zA-Z0-9+.\\-]+):(//((([a-zA-Z0-9\\-._~!$&\'()*+,;=:]|%[0-9A-F]{2})*)@)?(([a-zA-Z0-9\\-._~!$&\'()*+,;=]|%[0-9A-F]{2})*)(:(\\d*))?(/([a-zA-Z0-9\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?|(/?([a-zA-Z0-9\\-._~!$&\'()*+,;=:@]|%[0-9A-F]{2})+([a-zA-Z0-9\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?)(\\?(([a-zA-Z0-9\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?(#(([a-zA-Z0-9\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?$\");\
  \nregex.test(context.value);\
  \n}';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingComplexListField_URI__pattern", true, true);
}
    
fc['validation_ClassFieldtoInflate_textList_ClassFieldtoInflate_textList__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassFieldtoInflate_textList__length", true, true);
}
fc['validation_ClassFieldtoInflate_textList_ClassFieldtoInflate_textList__multiplicity'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof tibco.forms.Util.checkMultiplicity != \'undefined\' ? tibco.forms.Util.checkMultiplicity(context.value, 0, 2147483647) ?  true : [this.getLabel(), \'0\'] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassFieldtoInflate_textList__multiplicity", true, true);
}
    
fc['validation_Copy_Of_ComplexListField_textList_Copy_Of_ComplexListField_textList__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ComplexListField_textList__length", true, true);
}
fc['validation_Copy_Of_ComplexListField_textList_Copy_Of_ComplexListField_textList__multiplicity'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof tibco.forms.Util.checkMultiplicity != \'undefined\' ? tibco.forms.Util.checkMultiplicity(context.value, 0, 2147483647) ?  true : [this.getLabel(), \'0\'] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ComplexListField_textList__multiplicity", true, true);
}
    
    
fc['validation_MergingComplexListField_complexChild_attribute2_MergingComplexListField_complexChild_attribute2__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingComplexListField_complexChild_attribute2__length", true, true);
}
    
    
fc['validation_ComplexListField_time__master_ComplexListField_time__master__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_time__master__datetime", true, true);
}
    
fc['validation_Copy_Of_ComplexListField_dateTimeTz__master_Copy_Of_ComplexListField_dateTimeTz__master__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ComplexListField_dateTimeTz__master__datetime", true, true);
}
    
    
fc['validation_MergingPartialComplexListField_time__master_MergingPartialComplexListField_time__master__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingPartialComplexListField_time__master__datetime", true, true);
}
    
fc['validation_ClassFieldtoInflate_text_ClassFieldtoInflate_text__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassFieldtoInflate_text__length", true, true);
}
    
fc['validation_Copy_Of_MergingPartialComplexListField_textList__master_Copy_Of_MergingPartialComplexListField_textList__master__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingPartialComplexListField_textList__master__length", true, true);
}
fc['validation_Copy_Of_MergingPartialComplexListField_textList__master_Copy_Of_MergingPartialComplexListField_textList__master__multiplicity'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof tibco.forms.Util.checkMultiplicity != \'undefined\' ? tibco.forms.Util.checkMultiplicity(context.value, 0, 2147483647) ?  true : [this.getLabel(), \'0\'] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingPartialComplexListField_textList__master__multiplicity", true, true);
}
    
fc['validation_MergingPartialComplexListField_complexChild_attribute1_MergingPartialComplexListField_complexChild_attribute1__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingPartialComplexListField_complexChild_attribute1__length", true, true);
}
    
fc['validation_Copy_Of_ClassFieldtoInflate_text_Copy_Of_ClassFieldtoInflate_text__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ClassFieldtoInflate_text__length", true, true);
}
    
    
fc['validation_Copy_Of_ComplexListField_complexChild_attribute1_Copy_Of_ComplexListField_complexChild_attribute1__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ComplexListField_complexChild_attribute1__length", true, true);
}
    
fc['validation_MergingPartialComplexListField_date_MergingPartialComplexListField_date__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingPartialComplexListField_date__datetime", true, true);
}
    
    
fc['validation_ClassField_float1_ClassField_float1__float'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\'? context.value !== \'\' && !isNaN(context.value) ? true : [context.control.getLabel()] : context.value !== \'\' && !isNaN(context.value);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassField_float1__float", true, true);
}
    
fc['validation_Copy_Of_MergingSimpleListField_Copy_Of_MergingSimpleListField__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingSimpleListField__length", true, true);
}
    
fc['validation_MergingComplexListField_dateTimeTz_MergingComplexListField_dateTimeTz__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingComplexListField_dateTimeTz__datetime", true, true);
}
    
fc['validation_Copy_Of_MergingComplexListField_dateTimeTz_Copy_Of_MergingComplexListField_dateTimeTz__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingComplexListField_dateTimeTz__datetime", true, true);
}
    
fc['validation_Copy_Of_MergingPartialComplexListField_float1_Copy_Of_MergingPartialComplexListField_float1__float'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\'? context.value !== \'\' && !isNaN(context.value) ? true : [context.control.getLabel()] : context.value !== \'\' && !isNaN(context.value);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingPartialComplexListField_float1__float", true, true);
}
    
fc['validation_Copy_Of_MergingPartialComplexListField_URI_Copy_Of_MergingPartialComplexListField_URI__pattern'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'if (typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\') {\
  \n tibco.forms.Util.checkRegExp(context.value, new RegExp(\"^([a-zA-Z0-9+.\\\\-]+):(//((([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:]|%[0-9A-F]{2})*)@)?(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=]|%[0-9A-F]{2})*)(:(\\\\d*))?(/([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?|(/?([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@]|%[0-9A-F]{2})+([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?)(\\\\?(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?(#(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?$\")) ? true : [context.control.getLabel(), \'URI\'];\
  \n} else {\
  \nvar regex = new RegExp(\"^([a-zA-Z0-9+.\\-]+):(//((([a-zA-Z0-9\\-._~!$&\'()*+,;=:]|%[0-9A-F]{2})*)@)?(([a-zA-Z0-9\\-._~!$&\'()*+,;=]|%[0-9A-F]{2})*)(:(\\d*))?(/([a-zA-Z0-9\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?|(/?([a-zA-Z0-9\\-._~!$&\'()*+,;=:@]|%[0-9A-F]{2})+([a-zA-Z0-9\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?)(\\?(([a-zA-Z0-9\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?(#(([a-zA-Z0-9\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?$\");\
  \nregex.test(context.value);\
  \n}';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingPartialComplexListField_URI__pattern", true, true);
}
    
fc['validation_FixedPointNumberField_FixedPointNumberField__fixed'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkNumberConstraint(context.stringValue, 10, 2) ? true : [context.control.getLabel(),\'10\', \'2\'] : !isNaN(context.value) && this.getForm().numberFormat(context.value, 10, 2);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "FixedPointNumberField__fixed", true, true);
}
    
fc['validation_Copy_Of_ComplexListField_text__master_Copy_Of_ComplexListField_text__master__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ComplexListField_text__master__length", true, true);
}
    
fc['validation_Copy_Of_ClassFieldtoInflate_textList_Copy_Of_ClassFieldtoInflate_textList__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ClassFieldtoInflate_textList__length", true, true);
}
fc['validation_Copy_Of_ClassFieldtoInflate_textList_Copy_Of_ClassFieldtoInflate_textList__multiplicity'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof tibco.forms.Util.checkMultiplicity != \'undefined\' ? tibco.forms.Util.checkMultiplicity(context.value, 0, 2147483647) ?  true : [this.getLabel(), \'0\'] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ClassFieldtoInflate_textList__multiplicity", true, true);
}
    
fc['validation_Copy_Of_ClassField_float1_Copy_Of_ClassField_float1__float'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\'? context.value !== \'\' && !isNaN(context.value) ? true : [context.control.getLabel()] : context.value !== \'\' && !isNaN(context.value);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ClassField_float1__float", true, true);
}
    
    
fc['validation_Copy_Of_MergingComplexListField_complexChild_attribute2_Copy_Of_MergingComplexListField_complexChild_attribute2__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingComplexListField_complexChild_attribute2__length", true, true);
}
    
fc['validation_ComplexListField_float1__master_ComplexListField_float1__master__float'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\'? context.value !== \'\' && !isNaN(context.value) ? true : [context.control.getLabel()] : context.value !== \'\' && !isNaN(context.value);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_float1__master__float", true, true);
}
    
fc['validation_ClassFieldtoInflate_URI_ClassFieldtoInflate_URI__pattern'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'if (typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\') {\
  \n tibco.forms.Util.checkRegExp(context.value, new RegExp(\"^([a-zA-Z0-9+.\\\\-]+):(//((([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:]|%[0-9A-F]{2})*)@)?(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=]|%[0-9A-F]{2})*)(:(\\\\d*))?(/([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?|(/?([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@]|%[0-9A-F]{2})+([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?)(\\\\?(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?(#(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?$\")) ? true : [context.control.getLabel(), \'URI\'];\
  \n} else {\
  \nvar regex = new RegExp(\"^([a-zA-Z0-9+.\\-]+):(//((([a-zA-Z0-9\\-._~!$&\'()*+,;=:]|%[0-9A-F]{2})*)@)?(([a-zA-Z0-9\\-._~!$&\'()*+,;=]|%[0-9A-F]{2})*)(:(\\d*))?(/([a-zA-Z0-9\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?|(/?([a-zA-Z0-9\\-._~!$&\'()*+,;=:@]|%[0-9A-F]{2})+([a-zA-Z0-9\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?)(\\?(([a-zA-Z0-9\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?(#(([a-zA-Z0-9\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?$\");\
  \nregex.test(context.value);\
  \n}';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassFieldtoInflate_URI__pattern", true, true);
}
    
fc['validation_Copy_Of_ComplexListField_fixed_Copy_Of_ComplexListField_fixed__fixed'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkNumberConstraint(context.stringValue, 10, 2) ? true : [context.control.getLabel(),\'10\', \'2\'] : !isNaN(context.value) && this.getForm().numberFormat(context.value, 10, 2);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ComplexListField_fixed__fixed", true, true);
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
    
fc['validation_Copy_Of_ComplexListField_float1__master_Copy_Of_ComplexListField_float1__master__float'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\'? context.value !== \'\' && !isNaN(context.value) ? true : [context.control.getLabel()] : context.value !== \'\' && !isNaN(context.value);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ComplexListField_float1__master__float", true, true);
}
    
    
fc['validation_Copy_Of_ClassFieldtoInflate_complexList_Copy_Of_ClassFieldtoInflate_complexList__multiplicity'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof tibco.forms.Util.checkMultiplicity != \'undefined\' ? tibco.forms.Util.checkMultiplicity(context.value, 0, 2147483647) ?  true : [this.getLabel(), \'0\'] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ClassFieldtoInflate_complexList__multiplicity", true, false);
}
    
fc['validation_MergingComplexListField_complexList_attribute2_MergingComplexListField_complexList_attribute2__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingComplexListField_complexList_attribute2__length", true, true);
}
    
fc['validation_MergingPartialComplexListField_URI_MergingPartialComplexListField_URI__pattern'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'if (typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\') {\
  \n tibco.forms.Util.checkRegExp(context.value, new RegExp(\"^([a-zA-Z0-9+.\\\\-]+):(//((([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:]|%[0-9A-F]{2})*)@)?(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=]|%[0-9A-F]{2})*)(:(\\\\d*))?(/([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?|(/?([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@]|%[0-9A-F]{2})+([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?)(\\\\?(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?(#(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?$\")) ? true : [context.control.getLabel(), \'URI\'];\
  \n} else {\
  \nvar regex = new RegExp(\"^([a-zA-Z0-9+.\\-]+):(//((([a-zA-Z0-9\\-._~!$&\'()*+,;=:]|%[0-9A-F]{2})*)@)?(([a-zA-Z0-9\\-._~!$&\'()*+,;=]|%[0-9A-F]{2})*)(:(\\d*))?(/([a-zA-Z0-9\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?|(/?([a-zA-Z0-9\\-._~!$&\'()*+,;=:@]|%[0-9A-F]{2})+([a-zA-Z0-9\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?)(\\?(([a-zA-Z0-9\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?(#(([a-zA-Z0-9\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?$\");\
  \nregex.test(context.value);\
  \n}';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingPartialComplexListField_URI__pattern", true, true);
}
    
fc['validation_ComplexListField_float1_ComplexListField_float1__float'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\'? context.value !== \'\' && !isNaN(context.value) ? true : [context.control.getLabel()] : context.value !== \'\' && !isNaN(context.value);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_float1__float", true, true);
}
    
    
    
fc['validation_ClassField_text_ClassField_text__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ClassField_text__length", true, true);
}
    
fc['validation_MergingComplexListField_date__master_MergingComplexListField_date__master__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingComplexListField_date__master__datetime", true, true);
}
    
    
    
fc['validation_Copy_Of_ClassField_complexChild_attribute1_Copy_Of_ClassField_complexChild_attribute1__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ClassField_complexChild_attribute1__length", true, true);
}
    
fc['validation_MergingComplexListField_textList_MergingComplexListField_textList__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingComplexListField_textList__length", true, true);
}
fc['validation_MergingComplexListField_textList_MergingComplexListField_textList__multiplicity'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof tibco.forms.Util.checkMultiplicity != \'undefined\' ? tibco.forms.Util.checkMultiplicity(context.value, 0, 2147483647) ?  true : [this.getLabel(), \'0\'] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingComplexListField_textList__multiplicity", true, true);
}
    
fc['validation_TextField2_TextField2__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "TextField2__length", true, true);
}
    
fc['validation_MergingComplexListField_complexChild_attribute1_MergingComplexListField_complexChild_attribute1__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingComplexListField_complexChild_attribute1__length", true, true);
}
    
fc['validation_Copy_Of_ClassField_textList_Copy_Of_ClassField_textList__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ClassField_textList__length", true, true);
}
fc['validation_Copy_Of_ClassField_textList_Copy_Of_ClassField_textList__multiplicity'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof tibco.forms.Util.checkMultiplicity != \'undefined\' ? tibco.forms.Util.checkMultiplicity(context.value, 0, 2147483647) ?  true : [this.getLabel(), \'0\'] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ClassField_textList__multiplicity", true, true);
}
    
fc['validation_MergingPartialComplexListField_dateTimeTz__master_MergingPartialComplexListField_dateTimeTz__master__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingPartialComplexListField_dateTimeTz__master__datetime", true, true);
}
    
fc['validation_Copy_Of_MergingPartialComplexListField_date_Copy_Of_MergingPartialComplexListField_date__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingPartialComplexListField_date__datetime", true, true);
}
    
fc['validation_MergingPartialComplexListField_text__master_MergingPartialComplexListField_text__master__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingPartialComplexListField_text__master__length", true, true);
}
    
fc['validation_Copy_Of_MergingComplexListField_complexChild_attribute1_Copy_Of_MergingComplexListField_complexChild_attribute1__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingComplexListField_complexChild_attribute1__length", true, true);
}
    
    
    
fc['validation_MergingSimpleListField_MergingSimpleListField__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingSimpleListField__length", true, true);
}
    
    
fc['validation_Copy_Of_ClassField_dateTimeTz_Copy_Of_ClassField_dateTimeTz__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ClassField_dateTimeTz__datetime", true, true);
}
    
    
fc['validation_Copy_Of_ClassField_complexList_attribute1_Copy_Of_ClassField_complexList_attribute1__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ClassField_complexList_attribute1__length", true, true);
}
    
    
fc['validation_ComplexListField_fixed_ComplexListField_fixed__fixed'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkNumberConstraint(context.stringValue, 10, 2) ? true : [context.control.getLabel(),\'10\', \'2\'] : !isNaN(context.value) && this.getForm().numberFormat(context.value, 10, 2);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_fixed__fixed", true, true);
}
    
fc['validation_Copy_Of_MergingPartialComplexListField_time_Copy_Of_MergingPartialComplexListField_time__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingPartialComplexListField_time__datetime", true, true);
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
    
fc['validation_Copy_Of_ClassField_URI_Copy_Of_ClassField_URI__pattern'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'if (typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\') {\
  \n tibco.forms.Util.checkRegExp(context.value, new RegExp(\"^([a-zA-Z0-9+.\\\\-]+):(//((([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:]|%[0-9A-F]{2})*)@)?(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=]|%[0-9A-F]{2})*)(:(\\\\d*))?(/([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?|(/?([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@]|%[0-9A-F]{2})+([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?)(\\\\?(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?(#(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?$\")) ? true : [context.control.getLabel(), \'URI\'];\
  \n} else {\
  \nvar regex = new RegExp(\"^([a-zA-Z0-9+.\\-]+):(//((([a-zA-Z0-9\\-._~!$&\'()*+,;=:]|%[0-9A-F]{2})*)@)?(([a-zA-Z0-9\\-._~!$&\'()*+,;=]|%[0-9A-F]{2})*)(:(\\d*))?(/([a-zA-Z0-9\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?|(/?([a-zA-Z0-9\\-._~!$&\'()*+,;=:@]|%[0-9A-F]{2})+([a-zA-Z0-9\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?)(\\?(([a-zA-Z0-9\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?(#(([a-zA-Z0-9\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?$\");\
  \nregex.test(context.value);\
  \n}';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ClassField_URI__pattern", true, true);
}
    
fc['validation_Copy_Of_MergingComplexListField_URI_Copy_Of_MergingComplexListField_URI__pattern'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'if (typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\') {\
  \n tibco.forms.Util.checkRegExp(context.value, new RegExp(\"^([a-zA-Z0-9+.\\\\-]+):(//((([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:]|%[0-9A-F]{2})*)@)?(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=]|%[0-9A-F]{2})*)(:(\\\\d*))?(/([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?|(/?([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@]|%[0-9A-F]{2})+([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?)(\\\\?(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?(#(([a-zA-Z0-9\\\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?$\")) ? true : [context.control.getLabel(), \'URI\'];\
  \n} else {\
  \nvar regex = new RegExp(\"^([a-zA-Z0-9+.\\-]+):(//((([a-zA-Z0-9\\-._~!$&\'()*+,;=:]|%[0-9A-F]{2})*)@)?(([a-zA-Z0-9\\-._~!$&\'()*+,;=]|%[0-9A-F]{2})*)(:(\\d*))?(/([a-zA-Z0-9\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?|(/?([a-zA-Z0-9\\-._~!$&\'()*+,;=:@]|%[0-9A-F]{2})+([a-zA-Z0-9\\-._~!$&\'()*+,;=:@/]|%[0-9A-F]{2})*)?)(\\?(([a-zA-Z0-9\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?(#(([a-zA-Z0-9\\-._~!$&\'()*+,;=:/?@]|%[0-9A-F]{2})*))?$\");\
  \nregex.test(context.value);\
  \n}';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingComplexListField_URI__pattern", true, true);
}
    
fc['validation_Copy_Of_NumberField_Copy_Of_NumberField__float'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\'? context.value !== \'\' && !isNaN(context.value) ? true : [context.control.getLabel()] : context.value !== \'\' && !isNaN(context.value);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_NumberField__float", true, true);
}
    
    
    
fc['validation_Copy_Of_ClassField_text_Copy_Of_ClassField_text__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_ClassField_text__length", true, true);
}
    
fc['validation_ComplexListField_fixed__master_ComplexListField_fixed__master__fixed'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkNumberConstraint(context.stringValue, 10, 2) ? true : [context.control.getLabel(),\'10\', \'2\'] : !isNaN(context.value) && this.getForm().numberFormat(context.value, 10, 2);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_fixed__master__fixed", true, true);
}
    
fc['validation_Copy_Of_MergingComplexListField_fixed__master_Copy_Of_MergingComplexListField_fixed__master__fixed'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkNumberConstraint(context.stringValue, 10, 2) ? true : [context.control.getLabel(),\'10\', \'2\'] : !isNaN(context.value) && this.getForm().numberFormat(context.value, 10, 2);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingComplexListField_fixed__master__fixed", true, true);
}
    
    
fc['validation_MergingPartialComplexListField_complexList_attribute2_MergingPartialComplexListField_complexList_attribute2__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingPartialComplexListField_complexList_attribute2__length", true, true);
}
    
    
fc['validation_Copy_Of_MergingComplexListField_text_Copy_Of_MergingComplexListField_text__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingComplexListField_text__length", true, true);
}
    
fc['validation_MergingPartialComplexListField_textList__master_MergingPartialComplexListField_textList__master__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingPartialComplexListField_textList__master__length", true, true);
}
fc['validation_MergingPartialComplexListField_textList__master_MergingPartialComplexListField_textList__master__multiplicity'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof tibco.forms.Util.checkMultiplicity != \'undefined\' ? tibco.forms.Util.checkMultiplicity(context.value, 0, 2147483647) ?  true : [this.getLabel(), \'0\'] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingPartialComplexListField_textList__master__multiplicity", true, true);
}
    
fc['validation_TimeField_TimeField__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "TimeField__datetime", true, true);
}
    
    
fc['validation_Copy_Of_MergingComplexListField_date_Copy_Of_MergingComplexListField_date__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingComplexListField_date__datetime", true, true);
}
    
fc['validation_Copy_Of_MergingComplexListField_textList__master_Copy_Of_MergingComplexListField_textList__master__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingComplexListField_textList__master__length", true, true);
}
fc['validation_Copy_Of_MergingComplexListField_textList__master_Copy_Of_MergingComplexListField_textList__master__multiplicity'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof tibco.forms.Util.checkMultiplicity != \'undefined\' ? tibco.forms.Util.checkMultiplicity(context.value, 0, 2147483647) ?  true : [this.getLabel(), \'0\'] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingComplexListField_textList__master__multiplicity", true, true);
}
    
fc['validation_MergingPartialComplexListField_time_MergingPartialComplexListField_time__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingPartialComplexListField_time__datetime", true, true);
}
    
fc['validation_Copy_Of_MergingComplexListField_float1_Copy_Of_MergingComplexListField_float1__float'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\'? context.value !== \'\' && !isNaN(context.value) ? true : [context.control.getLabel()] : context.value !== \'\' && !isNaN(context.value);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingComplexListField_float1__float", true, true);
}
    
fc['validation_ComplexListField_dateTimeTz__master_ComplexListField_dateTimeTz__master__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateTimeFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ComplexListField_dateTimeTz__master__datetime", true, true);
}
    
fc['validation_FixedNumberTypeDecl_FixedNumberTypeDecl__fixed'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkNumberConstraint(context.stringValue, 10, 2) ? true : [context.control.getLabel(),\'10\', \'2\'] : !isNaN(context.value) && this.getForm().numberFormat(context.value, 10, 2);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "FixedNumberTypeDecl__fixed", true, true);
}
    
    
fc['validation_Copy_Of_SimpleListField_Copy_Of_SimpleListField__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_SimpleListField__length", true, true);
}
    
    
    
fc['validation_MergingPartialComplexListField_complexList_MergingPartialComplexListField_complexList__multiplicity'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof tibco.forms.Util.checkMultiplicity != \'undefined\' ? tibco.forms.Util.checkMultiplicity(context.value, 0, 2147483647) ?  true : [this.getLabel(), \'0\'] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingPartialComplexListField_complexList__multiplicity", true, false);
}
    
    
fc['validation_Copy_Of_MergingComplexListField_complexList_attribute2_Copy_Of_MergingComplexListField_complexList_attribute2__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingComplexListField_complexList_attribute2__length", true, true);
}
    
    
fc['validation_MergingComplexListField_fixed__master_MergingComplexListField_fixed__master__fixed'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkNumberConstraint(context.stringValue, 10, 2) ? true : [context.control.getLabel(),\'10\', \'2\'] : !isNaN(context.value) && this.getForm().numberFormat(context.value, 10, 2);';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "MergingComplexListField_fixed__master__fixed", true, true);
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
    
    
    
    
fc['validation_Copy_Of_MergingPartialComplexListField_date__master_Copy_Of_MergingPartialComplexListField_date__master__datetime'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkDateFormat(context.stringValue) ?  true : [context.control.getLabel()] : true;';
    return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Copy_Of_MergingPartialComplexListField_date__master__datetime", true, true);
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

        this.getBooleanField = function(useInternal) {
            return this.form.dataMap['BooleanField'].getValue(useInternal);
        };
        this.setBooleanField = function(value) {
            this.form.dataMap['BooleanField'].setValue(value);
        };
        Object.defineProperty(this, 'BooleanField', {
            get: function() {
                return this.form.dataMap['BooleanField'].value;
            },
            set: function(value) {
                this.form.dataMap['BooleanField'].value = value;
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

        this.getCopy_Of_PerformerField = function(useInternal) {
            return this.form.dataMap['Copy_Of_PerformerField'].getValue(useInternal);
        };
        this.setCopy_Of_PerformerField = function(value) {
            this.form.dataMap['Copy_Of_PerformerField'].setValue(value);
        };
        Object.defineProperty(this, 'Copy_Of_PerformerField', {
            get: function() {
                return this.form.dataMap['Copy_Of_PerformerField'].value;
            },
            set: function(value) {
                this.form.dataMap['Copy_Of_PerformerField'].value = value;
            },
            enumerable: true
        });

        this.getCopy_Of_DateTimeField = function(useInternal) {
            return this.form.dataMap['Copy_Of_DateTimeField'].getValue(useInternal);
        };
        this.setCopy_Of_DateTimeField = function(value) {
            this.form.dataMap['Copy_Of_DateTimeField'].setValue(value);
        };
        Object.defineProperty(this, 'Copy_Of_DateTimeField', {
            get: function() {
                return this.form.dataMap['Copy_Of_DateTimeField'].value;
            },
            set: function(value) {
                this.form.dataMap['Copy_Of_DateTimeField'].value = value;
            },
            enumerable: true
        });

        this.getCopy_Of_ComplexListField = function() {
            return this.form.dataMap['Copy_Of_ComplexListField'].getValue();
        };
        Object.defineProperty(this, 'Copy_Of_ComplexListField', {
            get: function() {
                return this.form.dataMap['Copy_Of_ComplexListField'].value;
            },
            set: function(value) {
                throw "Cannot assign value on a multi-valued parameter/datafield.";
            },
            enumerable: true
        });

        this.getCopy_Of_ClassField = function() {
            return this.form.dataMap['Copy_Of_ClassField'].getValue();
        };
        this.setCopy_Of_ClassField = function(value) {
            this.form.dataMap['Copy_Of_ClassField'].setValue(value);
        };
        Object.defineProperty(this, 'Copy_Of_ClassField', {
            get: function() {
                return this.form.dataMap['Copy_Of_ClassField'].value;
            },
            set: function(value) {
                this.form.dataMap['Copy_Of_ClassField'].value = value;
            },
            enumerable: true
        });

        this.getCopy_Of_BooleanField = function(useInternal) {
            return this.form.dataMap['Copy_Of_BooleanField'].getValue(useInternal);
        };
        this.setCopy_Of_BooleanField = function(value) {
            this.form.dataMap['Copy_Of_BooleanField'].setValue(value);
        };
        Object.defineProperty(this, 'Copy_Of_BooleanField', {
            get: function() {
                return this.form.dataMap['Copy_Of_BooleanField'].value;
            },
            set: function(value) {
                this.form.dataMap['Copy_Of_BooleanField'].value = value;
            },
            enumerable: true
        });

        this.getCopy_Of_FixedPointNumberField = function(useInternal) {
            return this.form.dataMap['Copy_Of_FixedPointNumberField'].getValue(useInternal);
        };
        this.setCopy_Of_FixedPointNumberField = function(value) {
            this.form.dataMap['Copy_Of_FixedPointNumberField'].setValue(value);
        };
        Object.defineProperty(this, 'Copy_Of_FixedPointNumberField', {
            get: function() {
                return this.form.dataMap['Copy_Of_FixedPointNumberField'].value;
            },
            set: function(value) {
                this.form.dataMap['Copy_Of_FixedPointNumberField'].value = value;
            },
            enumerable: true
        });

        this.getCopy_Of_FixedNumberTypeDecl = function(useInternal) {
            return this.form.dataMap['Copy_Of_FixedNumberTypeDecl'].getValue(useInternal);
        };
        this.setCopy_Of_FixedNumberTypeDecl = function(value) {
            this.form.dataMap['Copy_Of_FixedNumberTypeDecl'].setValue(value);
        };
        Object.defineProperty(this, 'Copy_Of_FixedNumberTypeDecl', {
            get: function() {
                return this.form.dataMap['Copy_Of_FixedNumberTypeDecl'].value;
            },
            set: function(value) {
                this.form.dataMap['Copy_Of_FixedNumberTypeDecl'].value = value;
            },
            enumerable: true
        });

        this.getCopy_Of_SimpleListField = function(useInternal) {
            return this.form.dataMap['Copy_Of_SimpleListField'].getValue(useInternal);
        };
        this.setCopy_Of_SimpleListField = function(value) {
            this.form.dataMap['Copy_Of_SimpleListField'].setValue(value);
        };
        Object.defineProperty(this, 'Copy_Of_SimpleListField', {
            get: function() {
                return this.form.dataMap['Copy_Of_SimpleListField'].value;
            },
            set: function(value) {
                throw "Cannot assign value on a multi-valued parameter/datafield.";
            },
            enumerable: true
        });

        this.getCopy_Of_NumberField = function(useInternal) {
            return this.form.dataMap['Copy_Of_NumberField'].getValue(useInternal);
        };
        this.setCopy_Of_NumberField = function(value) {
            this.form.dataMap['Copy_Of_NumberField'].setValue(value);
        };
        Object.defineProperty(this, 'Copy_Of_NumberField', {
            get: function() {
                return this.form.dataMap['Copy_Of_NumberField'].value;
            },
            set: function(value) {
                this.form.dataMap['Copy_Of_NumberField'].value = value;
            },
            enumerable: true
        });

        this.getCopy_Of_TextField = function(useInternal) {
            return this.form.dataMap['Copy_Of_TextField'].getValue(useInternal);
        };
        this.setCopy_Of_TextField = function(value) {
            this.form.dataMap['Copy_Of_TextField'].setValue(value);
        };
        Object.defineProperty(this, 'Copy_Of_TextField', {
            get: function() {
                return this.form.dataMap['Copy_Of_TextField'].value;
            },
            set: function(value) {
                this.form.dataMap['Copy_Of_TextField'].value = value;
            },
            enumerable: true
        });

        this.getCopy_Of_ListFixedNumberTypeDecl = function(useInternal) {
            return this.form.dataMap['Copy_Of_ListFixedNumberTypeDecl'].getValue(useInternal);
        };
        this.setCopy_Of_ListFixedNumberTypeDecl = function(value) {
            this.form.dataMap['Copy_Of_ListFixedNumberTypeDecl'].setValue(value);
        };
        Object.defineProperty(this, 'Copy_Of_ListFixedNumberTypeDecl', {
            get: function() {
                return this.form.dataMap['Copy_Of_ListFixedNumberTypeDecl'].value;
            },
            set: function(value) {
                throw "Cannot assign value on a multi-valued parameter/datafield.";
            },
            enumerable: true
        });

        this.getCopy_Of_DateField = function(useInternal) {
            return this.form.dataMap['Copy_Of_DateField'].getValue(useInternal);
        };
        this.setCopy_Of_DateField = function(value) {
            this.form.dataMap['Copy_Of_DateField'].setValue(value);
        };
        Object.defineProperty(this, 'Copy_Of_DateField', {
            get: function() {
                return this.form.dataMap['Copy_Of_DateField'].value;
            },
            set: function(value) {
                this.form.dataMap['Copy_Of_DateField'].value = value;
            },
            enumerable: true
        });

        this.getCopy_Of_TimeField = function(useInternal) {
            return this.form.dataMap['Copy_Of_TimeField'].getValue(useInternal);
        };
        this.setCopy_Of_TimeField = function(value) {
            this.form.dataMap['Copy_Of_TimeField'].setValue(value);
        };
        Object.defineProperty(this, 'Copy_Of_TimeField', {
            get: function() {
                return this.form.dataMap['Copy_Of_TimeField'].value;
            },
            set: function(value) {
                this.form.dataMap['Copy_Of_TimeField'].value = value;
            },
            enumerable: true
        });

        this.getMergingSimpleListField = function(useInternal) {
            return this.form.dataMap['MergingSimpleListField'].getValue(useInternal);
        };
        this.setMergingSimpleListField = function(value) {
            this.form.dataMap['MergingSimpleListField'].setValue(value);
        };
        Object.defineProperty(this, 'MergingSimpleListField', {
            get: function() {
                return this.form.dataMap['MergingSimpleListField'].value;
            },
            set: function(value) {
                throw "Cannot assign value on a multi-valued parameter/datafield.";
            },
            enumerable: true
        });

        this.getMergingComplexListField = function() {
            return this.form.dataMap['MergingComplexListField'].getValue();
        };
        Object.defineProperty(this, 'MergingComplexListField', {
            get: function() {
                return this.form.dataMap['MergingComplexListField'].value;
            },
            set: function(value) {
                throw "Cannot assign value on a multi-valued parameter/datafield.";
            },
            enumerable: true
        });

        this.getCopy_Of_MergingComplexListField = function() {
            return this.form.dataMap['Copy_Of_MergingComplexListField'].getValue();
        };
        Object.defineProperty(this, 'Copy_Of_MergingComplexListField', {
            get: function() {
                return this.form.dataMap['Copy_Of_MergingComplexListField'].value;
            },
            set: function(value) {
                throw "Cannot assign value on a multi-valued parameter/datafield.";
            },
            enumerable: true
        });

        this.getCopy_Of_MergingSimpleListField = function(useInternal) {
            return this.form.dataMap['Copy_Of_MergingSimpleListField'].getValue(useInternal);
        };
        this.setCopy_Of_MergingSimpleListField = function(value) {
            this.form.dataMap['Copy_Of_MergingSimpleListField'].setValue(value);
        };
        Object.defineProperty(this, 'Copy_Of_MergingSimpleListField', {
            get: function() {
                return this.form.dataMap['Copy_Of_MergingSimpleListField'].value;
            },
            set: function(value) {
                throw "Cannot assign value on a multi-valued parameter/datafield.";
            },
            enumerable: true
        });

        this.getCopy_Of_MergingPartialComplexListField = function() {
            return this.form.dataMap['Copy_Of_MergingPartialComplexListField'].getValue();
        };
        Object.defineProperty(this, 'Copy_Of_MergingPartialComplexListField', {
            get: function() {
                return this.form.dataMap['Copy_Of_MergingPartialComplexListField'].value;
            },
            set: function(value) {
                throw "Cannot assign value on a multi-valued parameter/datafield.";
            },
            enumerable: true
        });

        this.getMergingPartialComplexListField = function() {
            return this.form.dataMap['MergingPartialComplexListField'].getValue();
        };
        Object.defineProperty(this, 'MergingPartialComplexListField', {
            get: function() {
                return this.form.dataMap['MergingPartialComplexListField'].value;
            },
            set: function(value) {
                throw "Cannot assign value on a multi-valued parameter/datafield.";
            },
            enumerable: true
        });

        this.getClassFieldtoInflate = function() {
            return this.form.dataMap['ClassFieldtoInflate'].getValue();
        };
        this.setClassFieldtoInflate = function(value) {
            this.form.dataMap['ClassFieldtoInflate'].setValue(value);
        };
        Object.defineProperty(this, 'ClassFieldtoInflate', {
            get: function() {
                return this.form.dataMap['ClassFieldtoInflate'].value;
            },
            set: function(value) {
                this.form.dataMap['ClassFieldtoInflate'].value = value;
            },
            enumerable: true
        });

        this.getCopy_Of_ClassFieldtoInflate = function() {
            return this.form.dataMap['Copy_Of_ClassFieldtoInflate'].getValue();
        };
        this.setCopy_Of_ClassFieldtoInflate = function(value) {
            this.form.dataMap['Copy_Of_ClassFieldtoInflate'].setValue(value);
        };
        Object.defineProperty(this, 'Copy_Of_ClassFieldtoInflate', {
            get: function() {
                return this.form.dataMap['Copy_Of_ClassFieldtoInflate'].value;
            },
            set: function(value) {
                this.form.dataMap['Copy_Of_ClassFieldtoInflate'].value = value;
            },
            enumerable: true
        });

        this.getTextField2 = function(useInternal) {
            return this.form.dataMap['TextField2'].getValue(useInternal);
        };
        this.setTextField2 = function(value) {
            this.form.dataMap['TextField2'].setValue(value);
        };
        Object.defineProperty(this, 'TextField2', {
            get: function() {
                return this.form.dataMap['TextField2'].value;
            },
            set: function(value) {
                this.form.dataMap['TextField2'].value = value;
            },
            enumerable: true
        });
    }
       
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
       
       
};
tibcoforms.formCode['_f8SRcJQ_EemDnPS3BM6Yfw']['defineValidations']();
