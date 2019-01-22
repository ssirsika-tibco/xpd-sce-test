/**
 * DO NOT EDIT: This is an automatically generated file.
 * This provides an implementation of the factory class com.tibco.bs3._0._sMdHsM0aEeKydowN7ZzKfA.UC7G_DocLit_SoapHttp_ProcessProviderProcess.UC7G_DocLit_SoapHttp_ProcessProviderProcess.UC7G_DocLit_SoapHttp_ProcessProviderProcessFactory. 
 */ 

com.tibco.data.Loader.classDefiner["com.tibco.bs3._0._sMdHsM0aEeKydowN7ZzKfA.UC7G_DocLit_SoapHttp_ProcessProviderProcess.UC7G_DocLit_SoapHttp_ProcessProviderProcess.UC7G_DocLit_SoapHttp_ProcessProviderProcessFactory"] = function() {

/**
 * Constructor.
 */
var theClass = function(form) {
    this.context = new Object();
    this.context.form = form;
    this.context.dataStore = form.getDataStore();
    this.context.logger = form.getLogger();
    this.context.item = null;
    this.context.id = null;
    this.loader = form._loader;
};

com.tibco.data.Loader.currentLoader.registerClass(theClass, "com.tibco.bs3._0._sMdHsM0aEeKydowN7ZzKfA.UC7G_DocLit_SoapHttp_ProcessProviderProcess.UC7G_DocLit_SoapHttp_ProcessProviderProcess.UC7G_DocLit_SoapHttp_ProcessProviderProcessFactory");

theClass.prototype.SUPPORTED_CLASSES = ["com.tibco.bs3._0._sMdHsM0aEeKydowN7ZzKfA.UC7G_DocLit_SoapHttp_ProcessProviderProcess.UC7G_DocLit_SoapHttp_ProcessProviderProcess.UC7G_DocLit_SoapHttp_ProcessProviderProcess"];

theClass.prototype.checkClassName = function(className) {
    for(i = 0; i < this.SUPPORTED_CLASSES.length; i++) {
        if (className == this.SUPPORTED_CLASSES[i]) return;
    }
    throw("Factory com.tibco.bs3._0._sMdHsM0aEeKydowN7ZzKfA.UC7G_DocLit_SoapHttp_ProcessProviderProcess.UC7G_DocLit_SoapHttp_ProcessProviderProcess.UC7G_DocLit_SoapHttp_ProcessProviderProcessFactory does not support class \"" + className + "\"");
};

theClass.prototype.create = function(className, jsonData) {
    this.checkClassName(className);
    var classObject = this.loader.newInstance(className, this.context);
    if(jsonData != undefined)
        classObject = classObject._setValue(classObject, jsonData, this.context);
    return classObject;
};

theClass.prototype.createUC7G_DocLit_SoapHttp_ProcessProviderProcess = function(jsonData) {
    var classObject = this.loader.newInstance("com.tibco.bs3._0._sMdHsM0aEeKydowN7ZzKfA.UC7G_DocLit_SoapHttp_ProcessProviderProcess.UC7G_DocLit_SoapHttp_ProcessProviderProcess.UC7G_DocLit_SoapHttp_ProcessProviderProcess", this.context);
    if(jsonData != undefined)
        classObject = classObject._setValue(classObject, jsonData);
    return classObject;
};

theClass.prototype.listCreateUC7G_DocLit_SoapHttp_ProcessProviderProcess = function(jsonData) {
    var classObject = this.loader.newInstance("com.tibco.bs3._0._sMdHsM0aEeKydowN7ZzKfA.UC7G_DocLit_SoapHttp_ProcessProviderProcess.UC7G_DocLit_SoapHttp_ProcessProviderProcess.UC7G_DocLit_SoapHttp_ProcessProviderProcess", this.context);
    var classList;
    classList = classObject._setValueList(classObject, jsonData);
    return classList;
};
    
theClass.prototype.getClass = function(className) {
    this.checkClassName(className);
    return this.loader.getClass(className);
};
};

com.tibco.data.Loader.classDefiner["com.tibco.bs3._0._sMdHsM0aEeKydowN7ZzKfA.UC7G_DocLit_SoapHttp_ProcessProviderProcess.UC7G_DocLit_SoapHttp_ProcessProviderProcess.UC7G_DocLit_SoapHttp_ProcessProviderProcessFactory"]();
