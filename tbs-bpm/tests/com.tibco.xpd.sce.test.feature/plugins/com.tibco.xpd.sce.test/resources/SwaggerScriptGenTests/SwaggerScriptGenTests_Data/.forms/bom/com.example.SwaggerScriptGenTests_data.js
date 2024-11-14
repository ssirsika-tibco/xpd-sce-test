/*
 * DO NOT EDIT: This is automatically generated code.
 * This provides an implementation of the BOM package com.example.SwaggerScriptGenTests_data.SwaggerScriptGenTests_dataPackage.
 */

bpm.data.Loader.classDefiner["com.example.SwaggerScriptGenTests_data.SwaggerScriptGenTests_dataPackage"] = function() {
    /** Constructor. */
    var theClass = function() {
    };
    bpm.data.Loader.currentLoader.registerPackage(theClass, "com.example.SwaggerScriptGenTests_data.SwaggerScriptGenTests_dataPackage");

};

bpm.scriptUtil._internal.checkVersionCompatibility("com.example.SwaggerScriptGenTests_data.js", "11.4.0.048");
bpm.data.Loader.classDefiner["com.example.SwaggerScriptGenTests_data.SwaggerScriptGenTests_dataPackage"]();

/*
 * DO NOT EDIT: This is automatically generated code.
 * This provides an implementation of the BOM factory com.example.SwaggerScriptGenTests_data.SwaggerScriptGenTests_dataFactory. 
 */ 

bpm.data.Loader.classDefiner["com.example.SwaggerScriptGenTests_data.SwaggerScriptGenTests_dataFactory"] = function() {
    /** Constructor. */
    var theClass = function(form) {
        this.context = {};
        this.context.form = form;
        if (form.getLogger)
            this.context.logger = form.getLogger();
        this.context.item = null;
        this.context.id = null;
        this.$loader = form._loader;
    };
    bpm.data.Loader.currentLoader.registerFactory(theClass, "com.example.SwaggerScriptGenTests_data.SwaggerScriptGenTests_dataFactory");

    theClass.prototype.SUPPORTED_CLASSES = [
        "com.example.SwaggerScriptGenTests_data.Tag"
    ];

    theClass.prototype.checkClassName = function(className) {
        for (var i = 0; i < this.SUPPORTED_CLASSES.length; i++) {
            if (className == this.SUPPORTED_CLASSES[i])
                return;
        };
        throw "Factory com.example.SwaggerScriptGenTests_data.SwaggerScriptGenTests_dataFactory does not support class \"" + className + "\"";
    };

    theClass.prototype.create = function(className, jsonData) {
        this.checkClassName(className);
        var instance = this.$loader.newInstance(className, this.context);
        if (jsonData != undefined)
            instance = instance._setValue(instance, jsonData, this.context);
        return instance;
    };

    theClass.prototype.createTag = function(jsonData) {
        var instance = this.$loader.newInstance("com.example.SwaggerScriptGenTests_data.Tag", this.context);
        if (jsonData != undefined)
            instance = instance._setValue(instance, jsonData);
        return instance;
    };

    theClass.prototype.listCreateTag = function(jsonData) {
        var instance = this.$loader.newInstance("com.example.SwaggerScriptGenTests_data.Tag", this.context);
        return instance._setValueList(instance, jsonData);
    };
    theClass.prototype.getClass = function(className) {
        this.checkClassName(className);
        return this.$loader.getClass(className);
    };
};

bpm.data.Loader.classDefiner["com.example.SwaggerScriptGenTests_data.SwaggerScriptGenTests_dataFactory"]();
/*
 * This provides an implementation of the BOM class com.example.SwaggerScriptGenTests_data.Tag. 
 */
bpm.data.Loader.classDefiner["com.example.SwaggerScriptGenTests_data.Tag"] = function() {
    /** Constructor. */
    var theClass = function(context) {
        this._init(theClass, context);
    };

    theClass.LOADER = bpm.data.Loader.currentLoader;
    theClass.LOADER.registerClass(theClass, "com.example.SwaggerScriptGenTests_data.Tag");
    bpm.data.Loader.extendClass(bpm.data.BomBase, theClass);
    theClass.ATTR_ID = "id";
    theClass.ATTR_NAME = "name";

    theClass.TYPE_ARRAY = {};
    theClass.TYPE_ARRAY[theClass.ATTR_ID] = {
        type: "BomPrimitiveTypes.Decimal",
        baseType: "BomPrimitiveTypes.Decimal",
        primitive: true,
        multivalued: false,
        upper: 1,
        required: false,
        length: 10,
        decimalPlaces: 0

    };
    theClass.TYPE_ARRAY[theClass.ATTR_NAME] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        upper: 1,
        required: false,
        length: 50
    };

    bpm.data.BomBase.defineProperties(theClass);
};

bpm.data.Loader.classDefiner["com.example.SwaggerScriptGenTests_data.Tag"]();
