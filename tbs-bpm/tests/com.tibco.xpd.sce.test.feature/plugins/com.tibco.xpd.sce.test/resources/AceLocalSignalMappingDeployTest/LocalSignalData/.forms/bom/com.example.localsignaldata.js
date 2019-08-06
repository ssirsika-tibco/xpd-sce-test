/*
 * DO NOT EDIT: This is automatically generated code.
 * This provides an implementation of the BOM package com.example.localsignaldata.LocalsignaldataPackage.
 */

bpm.data.Loader.classDefiner["com.example.localsignaldata.LocalsignaldataPackage"] = function() {
    /** Constructor. */
    var theClass = function() {
    };
    bpm.data.Loader.currentLoader.registerPackage(theClass, "com.example.localsignaldata.LocalsignaldataPackage");

};

bpm.data.Loader.classDefiner["com.example.localsignaldata.LocalsignaldataPackage"]();

/*
 * DO NOT EDIT: This is automatically generated code.
 * This provides an implementation of the BOM factory com.example.localsignaldata.LocalsignaldataFactory. 
 */ 

bpm.data.Loader.classDefiner["com.example.localsignaldata.LocalsignaldataFactory"] = function() {
    /** Constructor. */
    var theClass = function(form) {
        this.context = new Object();
        this.context.form = form;
        if (form.getLogger)
            this.context.logger = form.getLogger();
        this.context.item = null;
        this.context.id = null;
        this.loader = form._loader;
    };
    bpm.data.Loader.currentLoader.registerFactory(theClass, "com.example.localsignaldata.LocalsignaldataFactory");

    theClass.prototype.SUPPORTED_CLASSES = [
        "com.example.localsignaldata.Class1",
        "com.example.localsignaldata.Class2"
    ];

    theClass.prototype.checkClassName = function(className) {
        for (var i = 0; i < this.SUPPORTED_CLASSES.length; i++) {
            if (className == this.SUPPORTED_CLASSES[i])
                return;
        };
        throw "Factory com.example.localsignaldata.LocalsignaldataFactory does not support class \"" + className + "\"";
    };

    theClass.prototype.create = function(className, jsonData) {
        this.checkClassName(className);
        var instance = this.loader.newInstance(className, this.context);
        if (jsonData != undefined)
            instance = instance._setValue(instance, jsonData, this.context);
        return instance;
    };

    theClass.prototype.createClass1 = function(jsonData) {
        var instance = this.loader.newInstance("com.example.localsignaldata.Class1", this.context);
        if (jsonData != undefined)
            instance = instance._setValue(instance, jsonData);
        return instance;
    };

    theClass.prototype.listCreateClass1 = function(jsonData) {
        var instance = this.loader.newInstance("com.example.localsignaldata.Class1", this.context);
        return instance._setValueList(instance, jsonData);
    };
    theClass.prototype.createClass2 = function(jsonData) {
        var instance = this.loader.newInstance("com.example.localsignaldata.Class2", this.context);
        if (jsonData != undefined)
            instance = instance._setValue(instance, jsonData);
        return instance;
    };

    theClass.prototype.listCreateClass2 = function(jsonData) {
        var instance = this.loader.newInstance("com.example.localsignaldata.Class2", this.context);
        return instance._setValueList(instance, jsonData);
    };
    theClass.prototype.getClass = function(className) {
        this.checkClassName(className);
        return this.loader.getClass(className);
    };
};

bpm.data.Loader.classDefiner["com.example.localsignaldata.LocalsignaldataFactory"]();

/*
 * This provides an implementation of the BOM class com.example.localsignaldata.Class1. 
 */

bpm.data.Loader.classDefiner["com.example.localsignaldata.Class1"] = function() {
    /** Constructor. */
    var theClass = function(context) {
        this._init(theClass, context);
    };

    theClass.LOADER = bpm.data.Loader.currentLoader;
    theClass.LOADER.registerClass(theClass, "com.example.localsignaldata.Class1");
    bpm.data.Loader.extendClass(bpm.data.BomBase, theClass);

    theClass.ATTR_CLASS2 = "class2";
    theClass.ATTR_ATTRIBUTE1 = "attribute1";
    theClass.ATTR_ATTRIBUTE2 = "attribute2";

    theClass.TYPE_ARRAY = new Object();
    theClass.TYPE_ARRAY[theClass.ATTR_CLASS2] = {
        type: "com.example.localsignaldata.Class2",
        baseType: "com.example.localsignaldata.Class2",
        primitive: false,
        multivalued: false,
        required: true,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_ATTRIBUTE1] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        required: false,
        defaultValue: "",
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_ATTRIBUTE2] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        required: false,
        defaultValue: "",
        length: 50
    };

    theClass.PRIMITIVE_ATTRIBUTE_NAMES = [
        theClass.ATTR_ATTRIBUTE1,
        theClass.ATTR_ATTRIBUTE2
    ];
    theClass.COMPOSITE_ATTRIBUTE_NAMES = [
        theClass.ATTR_CLASS2
    ];
    theClass.ATTRIBUTE_NAMES = [
        theClass.ATTR_CLASS2,
        theClass.ATTR_ATTRIBUTE1,
        theClass.ATTR_ATTRIBUTE2
    ];

    theClass.getName = function() {
        return "com.example.localsignaldata.Class1";
    };


    Object.defineProperty(theClass.prototype, 'class2', {
        get: function() {
            return this._getComplexAttribute(this.loader.getClass("com.example.localsignaldata.Class1").ATTR_CLASS2);
        },
        set: function(class2) {
            var classRef = this.loader.getClass("com.example.localsignaldata.Class1");
            var attrRef = classRef.ATTR_CLASS2;
            var attrType = classRef._getTypeDef(attrRef);
            if (eval("class2 instanceof this.loader.getClass(attrType.type)")) {
                this._setComplexAttribute(attrRef, class2);
            } else {
                throw "Wrong input object type.";
            }
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'attribute1', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("com.example.localsignaldata.Class1").ATTR_ATTRIBUTE1);
        },
        set: function(attribute1) {
            this._setPrimitiveAttribute(this.loader.getClass("com.example.localsignaldata.Class1").ATTR_ATTRIBUTE1, attribute1);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'attribute2', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("com.example.localsignaldata.Class1").ATTR_ATTRIBUTE2);
        },
        set: function(attribute2) {
            this._setPrimitiveAttribute(this.loader.getClass("com.example.localsignaldata.Class1").ATTR_ATTRIBUTE2, attribute2);
        },
        enumerable: true
    });

};

bpm.data.Loader.classDefiner["com.example.localsignaldata.Class1"]();

/*
 * This provides an implementation of the BOM class com.example.localsignaldata.Class2. 
 */

bpm.data.Loader.classDefiner["com.example.localsignaldata.Class2"] = function() {
    /** Constructor. */
    var theClass = function(context) {
        this._init(theClass, context);
    };

    theClass.LOADER = bpm.data.Loader.currentLoader;
    theClass.LOADER.registerClass(theClass, "com.example.localsignaldata.Class2");
    bpm.data.Loader.extendClass(bpm.data.BomBase, theClass);

    theClass.ATTR_ATTRIBUTE1 = "attribute1";
    theClass.ATTR_ATTRIBUTE2 = "attribute2";

    theClass.TYPE_ARRAY = new Object();
    theClass.TYPE_ARRAY[theClass.ATTR_ATTRIBUTE1] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        required: false,
        defaultValue: "",
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_ATTRIBUTE2] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        required: false,
        defaultValue: "",
        length: 50
    };

    theClass.PRIMITIVE_ATTRIBUTE_NAMES = [
        theClass.ATTR_ATTRIBUTE1,
        theClass.ATTR_ATTRIBUTE2
    ];
    theClass.COMPOSITE_ATTRIBUTE_NAMES = [

    ];
    theClass.ATTRIBUTE_NAMES = [
        theClass.ATTR_ATTRIBUTE1,
        theClass.ATTR_ATTRIBUTE2
    ];

    theClass.getName = function() {
        return "com.example.localsignaldata.Class2";
    };


    Object.defineProperty(theClass.prototype, 'attribute1', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("com.example.localsignaldata.Class2").ATTR_ATTRIBUTE1);
        },
        set: function(attribute1) {
            this._setPrimitiveAttribute(this.loader.getClass("com.example.localsignaldata.Class2").ATTR_ATTRIBUTE1, attribute1);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'attribute2', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("com.example.localsignaldata.Class2").ATTR_ATTRIBUTE2);
        },
        set: function(attribute2) {
            this._setPrimitiveAttribute(this.loader.getClass("com.example.localsignaldata.Class2").ATTR_ATTRIBUTE2, attribute2);
        },
        enumerable: true
    });

};

bpm.data.Loader.classDefiner["com.example.localsignaldata.Class2"]();
