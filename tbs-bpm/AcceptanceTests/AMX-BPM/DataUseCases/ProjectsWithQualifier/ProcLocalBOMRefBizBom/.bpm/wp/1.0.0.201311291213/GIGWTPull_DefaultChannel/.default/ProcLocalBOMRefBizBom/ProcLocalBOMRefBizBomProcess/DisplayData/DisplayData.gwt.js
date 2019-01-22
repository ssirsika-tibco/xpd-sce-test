

if (typeof(tibcoforms) == 'undefined') tibcoforms = new Object();
if (typeof(tibcoforms.formCode) == 'undefined') tibcoforms.formCode = new Object();
tibcoforms.formCode['_S_NCkFjnEeOujN6NvEd9_A'] = new Object();
tibcoforms.formCode['_S_NCkFjnEeOujN6NvEd9_A']['defineActions'] = function() {
var fc = tibcoforms.formCode['_S_NCkFjnEeOujN6NvEd9_A'];
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
        return "TIBCO Forms 2.6.0 V5 Compliant";
    }
};
tibcoforms.formCode['_S_NCkFjnEeOujN6NvEd9_A']['defineActions']();

tibcoforms.formCode['_S_NCkFjnEeOujN6NvEd9_A']['defineValidations'] = function() {
var fc = tibcoforms.formCode['_S_NCkFjnEeOujN6NvEd9_A'];
	
	
fc['validation_Field_attrCase_autoCaseIdentifier1_Field_attrCase_autoCaseIdentifier1__length'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkNumberConstraint(context.stringValue, 19, 0) ? true : [context.control.getLabel(), \'19\'] : !isNaN(context.value) && this.getForm().numberFormat(context.value,19, 0);';
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Field_attrCase_autoCaseIdentifier1: Field_attrCase_autoCaseIdentifier1__length", true, true);
}
	
fc['validation_Field_attrCase_attr1InCase_Field_attrCase_attr1InCase__length'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Field_attrCase_attr1InCase: Field_attrCase_attr1InCase__length", true, true);
}
	
fc['validation_Field_attrCase_attrGlobal_attr1InGlobal_Field_attrCase_attrGlobal_attr1InGlobal__length'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Field_attrCase_attrGlobal_attr1InGlobal: Field_attrCase_attrGlobal_attr1InGlobal__length", true, true);
}
	
	
	
	
	
fc['validation_Field_localAttr1_Field_localAttr1__length'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Field_localAttr1: Field_localAttr1__length", true, true);
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
                 (strContxtControlValue == null || strContxtControlValue.toString().length == 0) ||
                 (("com.tibco.forms.controls.checkbox" == controlType) && 'true' != strContxtControlValue.toString().toLowerCase()));
	}
	fc['register_pkgs_and_fcts'] = function(formId) {
	   var form = tibcoforms.formCache[formId];
	   form.registerPackages(['com.example.proclocalbomrefbizbom.ProclocalbomrefbizbomPackage', 'com.example.bizdataqualifiertest.BizdataqualifiertestPackage']);
       form.registerFactories(['com.example.proclocalbomrefbizbom.ProclocalbomrefbizbomFactory', 'com.example.bizdataqualifiertest.BizdataqualifiertestFactory']);
	}
	fc['DataModel']=function(formId) {
		this.form = tibcoforms.formCache[formId];
		this.getField = function() {
			return this.form.dataMap['Field'].getValue();
		};
		this.setField = function(value) {
			return this.form.dataMap['Field'].setValue(value);
		};
	}
	   
            
            
            
	   
	   
};
tibcoforms.formCode['_S_NCkFjnEeOujN6NvEd9_A']['defineValidations']();
