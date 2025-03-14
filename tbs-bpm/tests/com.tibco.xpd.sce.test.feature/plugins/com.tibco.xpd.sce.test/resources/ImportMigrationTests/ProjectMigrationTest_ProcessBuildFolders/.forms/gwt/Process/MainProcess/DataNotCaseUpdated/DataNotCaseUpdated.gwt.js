

if (typeof(tibcoforms) == 'undefined') tibcoforms = new Object();
if (typeof(tibcoforms.formCode) == 'undefined') tibcoforms.formCode = new Object();
tibcoforms.formCode['_xNiEEFnZEemxUMp1MP_2QQ'] = new Object();
tibcoforms.formCode['_xNiEEFnZEemxUMp1MP_2QQ']['defineActions'] = function() {
var fc = tibcoforms.formCode['_xNiEEFnZEemxUMp1MP_2QQ'];
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
        return "TIBCO Forms 4.1.0 V17 compliant";
    }
};
tibcoforms.formCode['_xNiEEFnZEemxUMp1MP_2QQ']['defineActions']();

tibcoforms.formCode['_xNiEEFnZEemxUMp1MP_2QQ']['defineValidations'] = function() {
var fc = tibcoforms.formCode['_xNiEEFnZEemxUMp1MP_2QQ'];
	
	
fc['validation_LoginDetailsFinal_address_postcode_LoginDetailsFinal_address_postcode__length'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
	// return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsFinal_address_postcode: LoginDetailsFinal_address_postcode__length", true, true);
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsFinal_address_postcode__length", true, true);
}
	
fc['validation_Parameter_text_Parameter_text__length'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 250) ? true : [context.control.getLabel(), \'250\'] : context.value.length <= 250;';
	// return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Parameter_text: Parameter_text__length", true, true);
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Parameter_text__length", true, true);
}
	
fc['validation_LoginDetailsFinal_address_line1_LoginDetailsFinal_address_line1__length'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
	// return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsFinal_address_line1: LoginDetailsFinal_address_line1__length", true, true);
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsFinal_address_line1__length", true, true);
}
	
	
fc['validation_LoginDetailsFinal_loginId_LoginDetailsFinal_loginId__length'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
	// return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsFinal_loginId: LoginDetailsFinal_loginId__length", true, true);
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsFinal_loginId__length", true, true);
}
	
fc['validation_LoginDetailsCaseReference_address_line2_LoginDetailsCaseReference_address_line2__length'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
	// return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsCaseReference_address_line2: LoginDetailsCaseReference_address_line2__length", true, true);
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsCaseReference_address_line2__length", true, true);
}
	
	
fc['validation_LoginDetailsCaseReference_address_postcode_LoginDetailsCaseReference_address_postcode__length'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
	// return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsCaseReference_address_postcode: LoginDetailsCaseReference_address_postcode__length", true, true);
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsCaseReference_address_postcode__length", true, true);
}
	
fc['validation_LoginDetailsTemp_loginId_LoginDetailsTemp_loginId__length'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
	// return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsTemp_loginId: LoginDetailsTemp_loginId__length", true, true);
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsTemp_loginId__length", true, true);
}
	
fc['validation_LoginDetailsOriginal_address_line2_LoginDetailsOriginal_address_line2__length'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
	// return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsOriginal_address_line2: LoginDetailsOriginal_address_line2__length", true, true);
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsOriginal_address_line2__length", true, true);
}
	
	
fc['validation_Parameter_integer_Parameter_integer__integer'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'if (typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\') {\
  \ntibco.forms.Util.checkInteger(typeof context.stringValue == \'undefined\' ? context.value : context.stringValue) ? true :  [context.control.getLabel()]} else {\
  \nvar regex = new RegExp(\"^-?[0-9]+$\");\
  \nregex.test(typeof context.stringValue == \'undefined\' ? context.value : context.stringValue);}';
	// return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Parameter_integer: Parameter_integer__integer", true, true);
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Parameter_integer__integer", true, true);
}
fc['validation_Parameter_integer_Parameter_integer__lowerLimit'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkLowerLimit(context.stringValue, \'-2147483648\', true) ? true : [context.control.getLabel(), \'-2147483648\'] : isNaN(context.value) || context.value.valueOf() >= -2147483648;';
	// return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Parameter_integer: Parameter_integer__lowerLimit", true, true);
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Parameter_integer__lowerLimit", true, true);
}
fc['validation_Parameter_integer_Parameter_integer__upperLimit'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkUpperLimit(context.stringValue, \'2147483647\', true) ? true : [context.control.getLabel(), \'2147483647\'] : isNaN(context.value) || context.value.valueOf() <= 2147483647;';
	// return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Parameter_integer: Parameter_integer__upperLimit", true, true);
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "Parameter_integer__upperLimit", true, true);
}
	
	
	
	
fc['validation_LoginDetailsCaseReference_password_LoginDetailsCaseReference_password__length'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
	// return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsCaseReference_password: LoginDetailsCaseReference_password__length", true, true);
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsCaseReference_password__length", true, true);
}
	
fc['validation_LoginDetailsOriginal_address_town_LoginDetailsOriginal_address_town__length'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
	// return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsOriginal_address_town: LoginDetailsOriginal_address_town__length", true, true);
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsOriginal_address_town__length", true, true);
}
	
	
	
fc['validation_LoginDetailsOriginal_address_line1_LoginDetailsOriginal_address_line1__length'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
	// return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsOriginal_address_line1: LoginDetailsOriginal_address_line1__length", true, true);
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsOriginal_address_line1__length", true, true);
}
	
fc['validation_LoginDetailsTemp_address_town_LoginDetailsTemp_address_town__length'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
	// return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsTemp_address_town: LoginDetailsTemp_address_town__length", true, true);
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsTemp_address_town__length", true, true);
}
	
fc['validation_LoginDetailsTemp_address_line2_LoginDetailsTemp_address_line2__length'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
	// return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsTemp_address_line2: LoginDetailsTemp_address_line2__length", true, true);
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsTemp_address_line2__length", true, true);
}
	
fc['validation_LoginDetailsTemp_address_line1_LoginDetailsTemp_address_line1__length'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
	// return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsTemp_address_line1: LoginDetailsTemp_address_line1__length", true, true);
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsTemp_address_line1__length", true, true);
}
	
fc['validation_LoginDetailsTemp_address_postcode_LoginDetailsTemp_address_postcode__length'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
	// return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsTemp_address_postcode: LoginDetailsTemp_address_postcode__length", true, true);
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsTemp_address_postcode__length", true, true);
}
	
	
	
fc['validation_LoginDetailsCaseReference_address_line1_LoginDetailsCaseReference_address_line1__length'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
	// return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsCaseReference_address_line1: LoginDetailsCaseReference_address_line1__length", true, true);
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsCaseReference_address_line1__length", true, true);
}
	
fc['validation_LoginId_LoginId__length'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 250) ? true : [context.control.getLabel(), \'250\'] : context.value.length <= 250;';
	// return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginId: LoginId__length", true, true);
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginId__length", true, true);
}
	
	
fc['validation_LoginDetailsFinal_address_town_LoginDetailsFinal_address_town__length'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
	// return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsFinal_address_town: LoginDetailsFinal_address_town__length", true, true);
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsFinal_address_town__length", true, true);
}
	
fc['validation_LoginDetailsTemp_password_LoginDetailsTemp_password__length'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
	// return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsTemp_password: LoginDetailsTemp_password__length", true, true);
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsTemp_password__length", true, true);
}
	
fc['validation_LoginDetailsFinal_address_line2_LoginDetailsFinal_address_line2__length'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
	// return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsFinal_address_line2: LoginDetailsFinal_address_line2__length", true, true);
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsFinal_address_line2__length", true, true);
}
	
fc['validation_LoginDetailsCaseReference_loginId_LoginDetailsCaseReference_loginId__length'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
	// return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsCaseReference_loginId: LoginDetailsCaseReference_loginId__length", true, true);
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsCaseReference_loginId__length", true, true);
}
	
	
fc['validation_ErrorCode_ErrorCode__length'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 250) ? true : [context.control.getLabel(), \'250\'] : context.value.length <= 250;';
	// return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ErrorCode: ErrorCode__length", true, true);
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "ErrorCode__length", true, true);
}
	
fc['validation_LoginDetailsOriginal_address_postcode_LoginDetailsOriginal_address_postcode__length'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
	// return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsOriginal_address_postcode: LoginDetailsOriginal_address_postcode__length", true, true);
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsOriginal_address_postcode__length", true, true);
}
	
fc['validation_LoginDetailsFinal_password_LoginDetailsFinal_password__length'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
	// return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsFinal_password: LoginDetailsFinal_password__length", true, true);
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsFinal_password__length", true, true);
}
	
fc['validation_LoginDetailsOriginal_loginId_LoginDetailsOriginal_loginId__length'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
	// return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsOriginal_loginId: LoginDetailsOriginal_loginId__length", true, true);
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsOriginal_loginId__length", true, true);
}
	
	
	
fc['validation_LoginDetailsOriginal_password_LoginDetailsOriginal_password__length'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
	// return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsOriginal_password: LoginDetailsOriginal_password__length", true, true);
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsOriginal_password__length", true, true);
}
	
fc['validation_LoginDetailsCaseReference_address_town_LoginDetailsCaseReference_address_town__length'] = function(formId, controlName, cloneUID, listIndex) {
	var valScr = 'typeof context.stringValue != \'undefined\' && typeof tibco.forms.Util != \'undefined\' ? tibco.forms.Util.checkTextLength(context.stringValue, 50) ? true : [context.control.getLabel(), \'50\'] : context.value.length <= 50;';
	// return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsCaseReference_address_town: LoginDetailsCaseReference_address_town__length", true, true);
	return tibco.forms.Util.handleInlineValidation.call(this, formId, this, cloneUID, listIndex, valScr, "LoginDetailsCaseReference_address_town__length", true, true);
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
	   form.registerPackages(['com.example.dataclass.DataclassPackage', 'com.example.businessdataproject.BusinessdataprojectPackage']);
       form.registerFactories(['com.example.dataclass.DataclassFactory', 'com.example.businessdataproject.BusinessdataprojectFactory']);
	}
	fc['DataModel']=function(formId) {
		this.form = tibcoforms.formCache[formId];
		this.getParameter = function() {
			return this.form.dataMap['Parameter'].getValue();
		};
		this.setParameter = function(value) {
			return this.form.dataMap['Parameter'].setValue(value);
		};
		this.getLoginId = function(useInternal) {
			return this.form.dataMap['LoginId'].getValue(useInternal);
		};
		this.setLoginId = function(value) {
			return this.form.dataMap['LoginId'].setValue(value);
		};
		this.getErrorCode = function(useInternal) {
			return this.form.dataMap['ErrorCode'].getValue(useInternal);
		};
		this.setErrorCode = function(value) {
			return this.form.dataMap['ErrorCode'].setValue(value);
		};
		this.getErrorDetail = function(useInternal) {
			return this.form.dataMap['ErrorDetail'].getValue(useInternal);
		};
		this.setErrorDetail = function(value) {
			return this.form.dataMap['ErrorDetail'].setValue(value);
		};
		this.getLoginDetailsCaseReference = function() {
			return this.form.dataMap['LoginDetailsCaseReference'].getValue();
		};
		this.setLoginDetailsCaseReference = function(value) {
			return this.form.dataMap['LoginDetailsCaseReference'].setValue(value);
		};
		this.getLoginDetailsOriginal = function() {
			return this.form.dataMap['LoginDetailsOriginal'].getValue();
		};
		this.setLoginDetailsOriginal = function(value) {
			return this.form.dataMap['LoginDetailsOriginal'].setValue(value);
		};
		this.getLoginDetailsTemp = function() {
			return this.form.dataMap['LoginDetailsTemp'].getValue();
		};
		this.setLoginDetailsTemp = function(value) {
			return this.form.dataMap['LoginDetailsTemp'].setValue(value);
		};
		this.getLoginDetailsFinal = function() {
			return this.form.dataMap['LoginDetailsFinal'].getValue();
		};
		this.setLoginDetailsFinal = function(value) {
			return this.form.dataMap['LoginDetailsFinal'].setValue(value);
		};
	}
	   
            
            
            
            
            
            
            
            
            
	   
	   
};
tibcoforms.formCode['_xNiEEFnZEemxUMp1MP_2QQ']['defineValidations']();
