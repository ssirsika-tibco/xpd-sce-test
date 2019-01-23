/**
 * DO NOT EDIT: This is an automatically generated file.
 * This provides an implementation of the factory class com.example.uc7g_doclit_soaphttp_processprovider.Uc7g_doclit_soaphttp_processproviderFactory. 
 */ 

com.tibco.data.Loader.classDefiner["com.example.uc7g_doclit_soaphttp_processprovider.Uc7g_doclit_soaphttp_processproviderFactory"] = function() {

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

com.tibco.data.Loader.currentLoader.registerClass(theClass, "com.example.uc7g_doclit_soaphttp_processprovider.Uc7g_doclit_soaphttp_processproviderFactory");

theClass.prototype.SUPPORTED_CLASSES = ["com.example.uc7g_doclit_soaphttp_processprovider.FaultClass", "com.example.uc7g_doclit_soaphttp_processprovider.InputClass", "com.example.uc7g_doclit_soaphttp_processprovider.OutputClass"];

theClass.prototype.checkClassName = function(className) {
    for(i = 0; i < this.SUPPORTED_CLASSES.length; i++) {
        if (className == this.SUPPORTED_CLASSES[i]) return;
    }
    throw("Factory com.example.uc7g_doclit_soaphttp_processprovider.Uc7g_doclit_soaphttp_processproviderFactory does not support class \"" + className + "\"");
};

theClass.prototype.create = function(className, jsonData) {
    this.checkClassName(className);
    var classObject = this.loader.newInstance(className, this.context);
    if(jsonData != undefined)
        classObject = classObject._setValue(classObject, jsonData, this.context);
    return classObject;
};

theClass.prototype.createFaultClass = function(jsonData) {
    var classObject = this.loader.newInstance("com.example.uc7g_doclit_soaphttp_processprovider.FaultClass", this.context);
    if(jsonData != undefined)
        classObject = classObject._setValue(classObject, jsonData);
    return classObject;
};

theClass.prototype.listCreateFaultClass = function(jsonData) {
    var classObject = this.loader.newInstance("com.example.uc7g_doclit_soaphttp_processprovider.FaultClass", this.context);
    var classList;
    classList = classObject._setValueList(classObject, jsonData);
    return classList;
};
    
theClass.prototype.createInputClass = function(jsonData) {
    var classObject = this.loader.newInstance("com.example.uc7g_doclit_soaphttp_processprovider.InputClass", this.context);
    if(jsonData != undefined)
        classObject = classObject._setValue(classObject, jsonData);
    return classObject;
};

theClass.prototype.listCreateInputClass = function(jsonData) {
    var classObject = this.loader.newInstance("com.example.uc7g_doclit_soaphttp_processprovider.InputClass", this.context);
    var classList;
    classList = classObject._setValueList(classObject, jsonData);
    return classList;
};
    
theClass.prototype.createOutputClass = function(jsonData) {
    var classObject = this.loader.newInstance("com.example.uc7g_doclit_soaphttp_processprovider.OutputClass", this.context);
    if(jsonData != undefined)
        classObject = classObject._setValue(classObject, jsonData);
    return classObject;
};

theClass.prototype.listCreateOutputClass = function(jsonData) {
    var classObject = this.loader.newInstance("com.example.uc7g_doclit_soaphttp_processprovider.OutputClass", this.context);
    var classList;
    classList = classObject._setValueList(classObject, jsonData);
    return classList;
};
    
theClass.prototype.getClass = function(className) {
    this.checkClassName(className);
    return this.loader.getClass(className);
};
};

com.tibco.data.Loader.classDefiner["com.example.uc7g_doclit_soaphttp_processprovider.Uc7g_doclit_soaphttp_processproviderFactory"]();
