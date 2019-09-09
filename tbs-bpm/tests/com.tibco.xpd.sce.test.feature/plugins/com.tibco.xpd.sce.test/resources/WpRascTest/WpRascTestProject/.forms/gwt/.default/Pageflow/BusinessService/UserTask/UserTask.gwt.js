

if (typeof(tibcoforms) == 'undefined') tibcoforms = new Object();
if (typeof(tibcoforms.formCode) == 'undefined') tibcoforms.formCode = new Object();
tibcoforms.formCode['__DPn0NMIEemVCPWsM5Nvzg'] = new Object();
tibcoforms.formCode['__DPn0NMIEemVCPWsM5Nvzg']['defineActions'] = function() {
var fc = tibcoforms.formCode['__DPn0NMIEemVCPWsM5Nvzg'];
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
tibcoforms.formCode['__DPn0NMIEemVCPWsM5Nvzg']['defineActions']();

tibcoforms.formCode['__DPn0NMIEemVCPWsM5Nvzg']['defineValidations'] = function() {
var fc = tibcoforms.formCode['__DPn0NMIEemVCPWsM5Nvzg'];
    
    
    
    
    
    fc['validate_required'] = function(formId, controlName, cloneUID, listIndex) {
        return bpm.forms.Util.handleRequiredValidation.call(this, formId, controlName, cloneUID, listIndex);
    }
    fc['register_pkgs_and_fcts'] = function(formId) {
       var form = tibcoforms.formCache[formId];
       form.registerPackages([]);
       form.registerFactories([]);
    }
    fc['DataModel'] = function(formId) {
        if (this.form) return;
        this.form = tibcoforms.formCache[formId];
    }
       
       
       
};
tibcoforms.formCode['__DPn0NMIEemVCPWsM5Nvzg']['defineValidations']();
