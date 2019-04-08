/**
 * DO NOT EDIT: This is an automatically generated file.
 * This provides an implementation of the factory class org.example.NewWSDLFile1.NewWSDLFile1Factory. 
 */ 

com.tibco.data.Loader.classDefiner["org.example.NewWSDLFile1.NewWSDLFile1Factory"] = function() {

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

com.tibco.data.Loader.currentLoader.registerClass(theClass, "org.example.NewWSDLFile1.NewWSDLFile1Factory");

theClass.prototype.SUPPORTED_CLASSES = ["org.example.NewWSDLFile1.NewOperationType", "org.example.NewWSDLFile1.NewOperationResponseType", "org.example.NewWSDLFile1.NewWSDLFile1"];

theClass.prototype.checkClassName = function(className) {
    for(i = 0; i < this.SUPPORTED_CLASSES.length; i++) {
        if (className == this.SUPPORTED_CLASSES[i]) return;
    }
    throw("Factory org.example.NewWSDLFile1.NewWSDLFile1Factory does not support class \"" + className + "\"");
};

theClass.prototype.create = function(className, jsonData) {
    this.checkClassName(className);
    var classObject = this.loader.newInstance(className, this.context);
    if(jsonData != undefined)
        classObject = classObject._setValue(classObject, jsonData, this.context);
    return classObject;
};

theClass.prototype.createNewOperationType = function(jsonData) {
    var classObject = this.loader.newInstance("org.example.NewWSDLFile1.NewOperationType", this.context);
    if(jsonData != undefined)
        classObject = classObject._setValue(classObject, jsonData);
    return classObject;
};

theClass.prototype.listCreateNewOperationType = function(jsonData) {
    var classObject = this.loader.newInstance("org.example.NewWSDLFile1.NewOperationType", this.context);
    var classList;
    classList = classObject._setValueList(classObject, jsonData);
    return classList;
};
    
theClass.prototype.createNewOperationResponseType = function(jsonData) {
    var classObject = this.loader.newInstance("org.example.NewWSDLFile1.NewOperationResponseType", this.context);
    if(jsonData != undefined)
        classObject = classObject._setValue(classObject, jsonData);
    return classObject;
};

theClass.prototype.listCreateNewOperationResponseType = function(jsonData) {
    var classObject = this.loader.newInstance("org.example.NewWSDLFile1.NewOperationResponseType", this.context);
    var classList;
    classList = classObject._setValueList(classObject, jsonData);
    return classList;
};
    
theClass.prototype.createNewWSDLFile1 = function(jsonData) {
    var classObject = this.loader.newInstance("org.example.NewWSDLFile1.NewWSDLFile1", this.context);
    if(jsonData != undefined)
        classObject = classObject._setValue(classObject, jsonData);
    return classObject;
};

theClass.prototype.listCreateNewWSDLFile1 = function(jsonData) {
    var classObject = this.loader.newInstance("org.example.NewWSDLFile1.NewWSDLFile1", this.context);
    var classList;
    classList = classObject._setValueList(classObject, jsonData);
    return classList;
};
    
theClass.prototype.getClass = function(className) {
    this.checkClassName(className);
    return this.loader.getClass(className);
};
};

com.tibco.data.Loader.classDefiner["org.example.NewWSDLFile1.NewWSDLFile1Factory"]();
