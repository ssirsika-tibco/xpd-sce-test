

if (typeof(tibcoforms) == 'undefined') tibcoforms = new Object();
if (typeof(tibcoforms.formCode) == 'undefined') tibcoforms.formCode = new Object();
tibcoforms.formCode['__C63sNMIEemVCPWsM5Nvzg'] = new Object();
tibcoforms.formCode['__C63sNMIEemVCPWsM5Nvzg']['defineActions'] = function() {
var fc = tibcoforms.formCode['__C63sNMIEemVCPWsM5Nvzg'];
    fc['rule_cancel'] = function(formId, context, thisObj) {
       try {
            bpm.forms.Util.handleScriptAction.call(thisObj, formId, context, thisObj, "cancel", "cancel", fc['action_cancel']);
       } catch(e) {
           tibcoforms.bridge.log_error("Rule(cancel) Error: " + e);
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
        return "TIBCO Forms for ACE Runtime 11.0.0.015";
    }
};
tibcoforms.formCode['__C63sNMIEemVCPWsM5Nvzg']['defineActions']();

tibcoforms.formCode['__C63sNMIEemVCPWsM5Nvzg']['defineValidations'] = function() {
var fc = tibcoforms.formCode['__C63sNMIEemVCPWsM5Nvzg'];
    
    
    
    
    
fc['validation_Case1_caseIdentifier1_Case1_caseIdentifier1__length'] = function(formId, controlName, cloneUID, listIndex) {
    var valScr = 'typeof context.stringValue != \'undefined\' && typeof bpm.forms.Util != \'undefined\' ? bpm.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.label, \'50\'] : context.value.length <= 50;';
    return bpm.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Case1_caseIdentifier1__length", true, true);
}
    
    
    
    fc['validate_required'] = function(formId, controlName, cloneUID, listIndex) {
        return bpm.forms.Util.handleRequiredValidation.call(this, formId, controlName, cloneUID, listIndex);
    }
    fc['register_pkgs_and_fcts'] = function(formId) {
       var form = tibcoforms.formCache[formId];
       form.registerPackages(['com.example.wprasctestdataproject.WprasctestdataprojectPackage']);
       form.registerFactories(['com.example.wprasctestdataproject.WprasctestdataprojectFactory']);
    }
    fc['DataModel'] = function(formId) {
        if (this.form) return;
        this.form = tibcoforms.formCache[formId];
        Object.defineProperty(this, 'Case1', {
            get: function() {
                return this.form.dataMap['Case1'].value;
            },
            set: function(value) {
                this.form.dataMap['Case1'].value = value;
            },
            enumerable: true
        });
    }
       
            
       
       
};
tibcoforms.formCode['__C63sNMIEemVCPWsM5Nvzg']['defineValidations']();
