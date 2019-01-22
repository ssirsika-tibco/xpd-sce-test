/**
 * DO NOT EDIT: This is an automatically generated file.
 * This provides an implementation of the factory class com.example.byrefproctobizdata.ByrefproctobizdataFactory. 
 */ 

com.tibco.data.Loader.classDefiner["com.example.byrefproctobizdata.ByrefproctobizdataFactory"] = function() {

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

com.tibco.data.Loader.currentLoader.registerClass(theClass, "com.example.byrefproctobizdata.ByrefproctobizdataFactory");

theClass.prototype.SUPPORTED_CLASSES = ["com.example.byrefproctobizdata.LocalClass"];

theClass.prototype.checkClassName = function(className) {
    for(i = 0; i < this.SUPPORTED_CLASSES.length; i++) {
        if (className == this.SUPPORTED_CLASSES[i]) return;
    }
    throw("Factory com.example.byrefproctobizdata.ByrefproctobizdataFactory does not support class \"" + className + "\"");
};

theClass.prototype.create = function(className) {
    this.checkClassName(className);
    return this.loader.newInstance(className, this.context);
};

theClass.prototype.createLocalClass = function() {
    return this.loader.newInstance("com.example.byrefproctobizdata.LocalClass", this.context);
};
    
theClass.prototype.getClass = function(className) {
    this.checkClassName(className);
    return this.loader.getClass(className);
};
};

com.tibco.data.Loader.classDefiner["com.example.byrefproctobizdata.ByrefproctobizdataFactory"]();
