/**
 * DO NOT EDIT: This is an automatically generated file.
 * This provides an implementation of the factory class org.example.NewWSDLFile.NewWSDLFileFactory. 
 */ 

com.tibco.data.Loader.classDefiner["org.example.NewWSDLFile.NewWSDLFileFactory"] = function() {

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

com.tibco.data.Loader.currentLoader.registerClass(theClass, "org.example.NewWSDLFile.NewWSDLFileFactory");

theClass.prototype.SUPPORTED_CLASSES = ["org.example.NewWSDLFile.NewOperationType", "org.example.NewWSDLFile.NewOperationResponseType", "org.example.NewWSDLFile.NewWSDLFile"];

theClass.prototype.checkClassName = function(className) {
    for(i = 0; i < this.SUPPORTED_CLASSES.length; i++) {
        if (className == this.SUPPORTED_CLASSES[i]) return;
    }
    throw("Factory org.example.NewWSDLFile.NewWSDLFileFactory does not support class \"" + className + "\"");
};

theClass.prototype.create = function(className, jsonData) {
    this.checkClassName(className);
    var classObject = this.loader.newInstance(className, this.context);
    if(jsonData != undefined)
        classObject = classObject._setValue(classObject, jsonData, this.context);
    return classObject;
};

theClass.prototype.createNewOperationType = function(jsonData) {
    var classObject = this.loader.newInstance("org.example.NewWSDLFile.NewOperationType", this.context);
    if(jsonData != undefined)
        classObject = classObject._setValue(classObject, jsonData);
    return classObject;
};

theClass.prototype.listCreateNewOperationType = function(jsonData) {
    var classObject = this.loader.newInstance("org.example.NewWSDLFile.NewOperationType", this.context);
    var classList;
    classList = classObject._setValueList(classObject, jsonData);
    return classList;
};
    
theClass.prototype.createNewOperationResponseType = function(jsonData) {
    var classObject = this.loader.newInstance("org.example.NewWSDLFile.NewOperationResponseType", this.context);
    if(jsonData != undefined)
        classObject = classObject._setValue(classObject, jsonData);
    return classObject;
};

theClass.prototype.listCreateNewOperationResponseType = function(jsonData) {
    var classObject = this.loader.newInstance("org.example.NewWSDLFile.NewOperationResponseType", this.context);
    var classList;
    classList = classObject._setValueList(classObject, jsonData);
    return classList;
};
    
theClass.prototype.createNewWSDLFile = function(jsonData) {
    var classObject = this.loader.newInstance("org.example.NewWSDLFile.NewWSDLFile", this.context);
    if(jsonData != undefined)
        classObject = classObject._setValue(classObject, jsonData);
    return classObject;
};

theClass.prototype.listCreateNewWSDLFile = function(jsonData) {
    var classObject = this.loader.newInstance("org.example.NewWSDLFile.NewWSDLFile", this.context);
    var classList;
    classList = classObject._setValueList(classObject, jsonData);
    return classList;
};
    
theClass.prototype.getClass = function(className) {
    this.checkClassName(className);
    return this.loader.getClass(className);
};
};

com.tibco.data.Loader.classDefiner["org.example.NewWSDLFile.NewWSDLFileFactory"]();
