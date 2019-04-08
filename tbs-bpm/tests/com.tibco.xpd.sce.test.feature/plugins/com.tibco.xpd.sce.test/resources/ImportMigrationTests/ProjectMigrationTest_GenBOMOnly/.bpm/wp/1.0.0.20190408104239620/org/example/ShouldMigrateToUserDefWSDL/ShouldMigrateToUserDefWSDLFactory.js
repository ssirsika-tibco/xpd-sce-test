/**
 * DO NOT EDIT: This is an automatically generated file.
 * This provides an implementation of the factory class org.example.ShouldMigrateToUserDefWSDL.ShouldMigrateToUserDefWSDLFactory. 
 */ 

com.tibco.data.Loader.classDefiner["org.example.ShouldMigrateToUserDefWSDL.ShouldMigrateToUserDefWSDLFactory"] = function() {

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

com.tibco.data.Loader.currentLoader.registerClass(theClass, "org.example.ShouldMigrateToUserDefWSDL.ShouldMigrateToUserDefWSDLFactory");

theClass.prototype.SUPPORTED_CLASSES = ["org.example.ShouldMigrateToUserDefWSDL.NewOperationType", "org.example.ShouldMigrateToUserDefWSDL.NewOperationResponseType", "org.example.ShouldMigrateToUserDefWSDL.ShouldMigrateToUserDefWSDL"];

theClass.prototype.checkClassName = function(className) {
    for(i = 0; i < this.SUPPORTED_CLASSES.length; i++) {
        if (className == this.SUPPORTED_CLASSES[i]) return;
    }
    throw("Factory org.example.ShouldMigrateToUserDefWSDL.ShouldMigrateToUserDefWSDLFactory does not support class \"" + className + "\"");
};

theClass.prototype.create = function(className, jsonData) {
    this.checkClassName(className);
    var classObject = this.loader.newInstance(className, this.context);
    if(jsonData != undefined)
        classObject = classObject._setValue(classObject, jsonData, this.context);
    return classObject;
};

theClass.prototype.createNewOperationType = function(jsonData) {
    var classObject = this.loader.newInstance("org.example.ShouldMigrateToUserDefWSDL.NewOperationType", this.context);
    if(jsonData != undefined)
        classObject = classObject._setValue(classObject, jsonData);
    return classObject;
};

theClass.prototype.listCreateNewOperationType = function(jsonData) {
    var classObject = this.loader.newInstance("org.example.ShouldMigrateToUserDefWSDL.NewOperationType", this.context);
    var classList;
    classList = classObject._setValueList(classObject, jsonData);
    return classList;
};
    
theClass.prototype.createNewOperationResponseType = function(jsonData) {
    var classObject = this.loader.newInstance("org.example.ShouldMigrateToUserDefWSDL.NewOperationResponseType", this.context);
    if(jsonData != undefined)
        classObject = classObject._setValue(classObject, jsonData);
    return classObject;
};

theClass.prototype.listCreateNewOperationResponseType = function(jsonData) {
    var classObject = this.loader.newInstance("org.example.ShouldMigrateToUserDefWSDL.NewOperationResponseType", this.context);
    var classList;
    classList = classObject._setValueList(classObject, jsonData);
    return classList;
};
    
theClass.prototype.createShouldMigrateToUserDefWSDL = function(jsonData) {
    var classObject = this.loader.newInstance("org.example.ShouldMigrateToUserDefWSDL.ShouldMigrateToUserDefWSDL", this.context);
    if(jsonData != undefined)
        classObject = classObject._setValue(classObject, jsonData);
    return classObject;
};

theClass.prototype.listCreateShouldMigrateToUserDefWSDL = function(jsonData) {
    var classObject = this.loader.newInstance("org.example.ShouldMigrateToUserDefWSDL.ShouldMigrateToUserDefWSDL", this.context);
    var classList;
    classList = classObject._setValueList(classObject, jsonData);
    return classList;
};
    
theClass.prototype.getClass = function(className) {
    this.checkClassName(className);
    return this.loader.getClass(className);
};
};

com.tibco.data.Loader.classDefiner["org.example.ShouldMigrateToUserDefWSDL.ShouldMigrateToUserDefWSDLFactory"]();
