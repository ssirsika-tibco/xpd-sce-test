/*
 * DO NOT EDIT: This is automatically generated code.
 * This provides an implementation of the BOM package com.example.links.LinksPackage.
 */

bpm.data.Loader.classDefiner["com.example.links.LinksPackage"] = function() {
    /** Constructor. */
    var theClass = function() {
    };
    bpm.data.Loader.currentLoader.registerPackage(theClass, "com.example.links.LinksPackage");

    // Define the enumerations declared by this package.
    theClass.OrderPhase = ["INPROGRESS", "INTRANSIT", "DELIVERED"];
    theClass.OrderPhase.INPROGRESS = "INPROGRESS";
    theClass.OrderPhase.INTRANSIT = "INTRANSIT";
    theClass.OrderPhase.DELIVERED = "DELIVERED";
    theClass.CustomerState = ["ACTIVE", "DEAD"];
    theClass.CustomerState.ACTIVE = "ACTIVE";
    theClass.CustomerState.DEAD = "DEAD";
    theClass.ProductState = ["AVAILABLE", "DISCONTINUED"];
    theClass.ProductState.AVAILABLE = "AVAILABLE";
    theClass.ProductState.DISCONTINUED = "DISCONTINUED";
};

bpm.scriptUtil._internal.checkVersionCompatibility("com.example.links.js", "11.0.0.013");
bpm.data.Loader.classDefiner["com.example.links.LinksPackage"]();

/*
 * DO NOT EDIT: This is automatically generated code.
 * This provides an implementation of the BOM factory com.example.links.LinksFactory. 
 */ 

bpm.data.Loader.classDefiner["com.example.links.LinksFactory"] = function() {
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
    bpm.data.Loader.currentLoader.registerFactory(theClass, "com.example.links.LinksFactory");

    theClass.prototype.SUPPORTED_CLASSES = [
        "com.example.links.Order",
        "com.example.links.Product",
        "com.example.links.Customer"
    ];

    theClass.prototype.checkClassName = function(className) {
        for (var i = 0; i < this.SUPPORTED_CLASSES.length; i++) {
            if (className == this.SUPPORTED_CLASSES[i])
                return;
        };
        throw "Factory com.example.links.LinksFactory does not support class \"" + className + "\"";
    };

    theClass.prototype.create = function(className, jsonData) {
        this.checkClassName(className);
        var instance = this.$loader.newInstance(className, this.context);
        if (jsonData != undefined)
            instance = instance._setValue(instance, jsonData, this.context);
        return instance;
    };

    theClass.prototype.createOrder = function(jsonData) {
        var instance = this.$loader.newInstance("com.example.links.Order", this.context);
        if (jsonData != undefined)
            instance = instance._setValue(instance, jsonData);
        return instance;
    };

    theClass.prototype.listCreateOrder = function(jsonData) {
        var instance = this.$loader.newInstance("com.example.links.Order", this.context);
        return instance._setValueList(instance, jsonData);
    };
    theClass.prototype.createProduct = function(jsonData) {
        var instance = this.$loader.newInstance("com.example.links.Product", this.context);
        if (jsonData != undefined)
            instance = instance._setValue(instance, jsonData);
        return instance;
    };

    theClass.prototype.listCreateProduct = function(jsonData) {
        var instance = this.$loader.newInstance("com.example.links.Product", this.context);
        return instance._setValueList(instance, jsonData);
    };
    theClass.prototype.createCustomer = function(jsonData) {
        var instance = this.$loader.newInstance("com.example.links.Customer", this.context);
        if (jsonData != undefined)
            instance = instance._setValue(instance, jsonData);
        return instance;
    };

    theClass.prototype.listCreateCustomer = function(jsonData) {
        var instance = this.$loader.newInstance("com.example.links.Customer", this.context);
        return instance._setValueList(instance, jsonData);
    };
    theClass.prototype.getClass = function(className) {
        this.checkClassName(className);
        return this.$loader.getClass(className);
    };
};

bpm.data.Loader.classDefiner["com.example.links.LinksFactory"]();
/*
 * This provides an implementation of the BOM class com.example.links.Customer. 
 */
bpm.data.Loader.classDefiner["com.example.links.Customer"] = function() {
    /** Constructor. */
    var theClass = function(context) {
        this._init(theClass, context);
    };

    theClass.LOADER = bpm.data.Loader.currentLoader;
    theClass.LOADER.registerClass(theClass, "com.example.links.Customer");
    bpm.data.Loader.extendClass(bpm.data.BomBase, theClass);
    theClass.ATTR_ID = "id";
    theClass.ATTR_NAME = "name";
    theClass.ATTR_STATUS = "status";

    theClass.TYPE_ARRAY = {};
    theClass.TYPE_ARRAY[theClass.ATTR_ID] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_NAME] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        required: false,
        defaultValue: "",
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_STATUS] = {
        type: "com.example.links.CustomerState",
        baseType: "com.example.links.CustomerState",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: ""
    };

    theClass.PRIMITIVE_ATTRIBUTE_NAMES = [
        theClass.ATTR_ID,
        theClass.ATTR_NAME,
        theClass.ATTR_STATUS
    ];
    theClass.COMPOSITE_ATTRIBUTE_NAMES = [

    ];
    theClass.ATTRIBUTE_NAMES = [
        theClass.ATTR_ID,
        theClass.ATTR_NAME,
        theClass.ATTR_STATUS
    ];

    theClass.getName = function() {
        return "com.example.links.Customer";
    };


    Object.defineProperty(theClass.prototype, 'id', {
        get: function() {
            return this._getPrimitiveAttribute(this.$loader.getClass("com.example.links.Customer").ATTR_ID);
        },
        set: function(id) {
            this._setPrimitiveAttribute(this.$loader.getClass("com.example.links.Customer").ATTR_ID, id);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'name', {
        get: function() {
            return this._getPrimitiveAttribute(this.$loader.getClass("com.example.links.Customer").ATTR_NAME);
        },
        set: function(name) {
            this._setPrimitiveAttribute(this.$loader.getClass("com.example.links.Customer").ATTR_NAME, name);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'status', {
        get: function() {
            return this._getPrimitiveAttribute(this.$loader.getClass("com.example.links.Customer").ATTR_STATUS);
        },
        set: function(status) {
            this._setPrimitiveAttribute(this.$loader.getClass("com.example.links.Customer").ATTR_STATUS, status);
        },
        enumerable: true
    });

};

bpm.data.Loader.classDefiner["com.example.links.Customer"]();
/*
 * This provides an implementation of the BOM class com.example.links.Order. 
 */
bpm.data.Loader.classDefiner["com.example.links.Order"] = function() {
    /** Constructor. */
    var theClass = function(context) {
        this._init(theClass, context);
    };

    theClass.LOADER = bpm.data.Loader.currentLoader;
    theClass.LOADER.registerClass(theClass, "com.example.links.Order");
    bpm.data.Loader.extendClass(bpm.data.BomBase, theClass);
    theClass.ATTR_ID = "id";
    theClass.ATTR_PHASE = "phase";

    theClass.TYPE_ARRAY = {};
    theClass.TYPE_ARRAY[theClass.ATTR_ID] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_PHASE] = {
        type: "com.example.links.OrderPhase",
        baseType: "com.example.links.OrderPhase",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: ""
    };

    theClass.PRIMITIVE_ATTRIBUTE_NAMES = [
        theClass.ATTR_ID,
        theClass.ATTR_PHASE
    ];
    theClass.COMPOSITE_ATTRIBUTE_NAMES = [

    ];
    theClass.ATTRIBUTE_NAMES = [
        theClass.ATTR_ID,
        theClass.ATTR_PHASE
    ];

    theClass.getName = function() {
        return "com.example.links.Order";
    };


    Object.defineProperty(theClass.prototype, 'id', {
        get: function() {
            return this._getPrimitiveAttribute(this.$loader.getClass("com.example.links.Order").ATTR_ID);
        },
        set: function(id) {
            this._setPrimitiveAttribute(this.$loader.getClass("com.example.links.Order").ATTR_ID, id);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'phase', {
        get: function() {
            return this._getPrimitiveAttribute(this.$loader.getClass("com.example.links.Order").ATTR_PHASE);
        },
        set: function(phase) {
            this._setPrimitiveAttribute(this.$loader.getClass("com.example.links.Order").ATTR_PHASE, phase);
        },
        enumerable: true
    });

};

bpm.data.Loader.classDefiner["com.example.links.Order"]();
/*
 * This provides an implementation of the BOM class com.example.links.Product. 
 */
bpm.data.Loader.classDefiner["com.example.links.Product"] = function() {
    /** Constructor. */
    var theClass = function(context) {
        this._init(theClass, context);
    };

    theClass.LOADER = bpm.data.Loader.currentLoader;
    theClass.LOADER.registerClass(theClass, "com.example.links.Product");
    bpm.data.Loader.extendClass(bpm.data.BomBase, theClass);
    theClass.ATTR_ID = "id";
    theClass.ATTR_NAME = "name";
    theClass.ATTR_STATE = "state";

    theClass.TYPE_ARRAY = {};
    theClass.TYPE_ARRAY[theClass.ATTR_ID] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_NAME] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        required: false,
        defaultValue: "",
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_STATE] = {
        type: "com.example.links.ProductState",
        baseType: "com.example.links.ProductState",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: ""
    };

    theClass.PRIMITIVE_ATTRIBUTE_NAMES = [
        theClass.ATTR_ID,
        theClass.ATTR_NAME,
        theClass.ATTR_STATE
    ];
    theClass.COMPOSITE_ATTRIBUTE_NAMES = [

    ];
    theClass.ATTRIBUTE_NAMES = [
        theClass.ATTR_ID,
        theClass.ATTR_NAME,
        theClass.ATTR_STATE
    ];

    theClass.getName = function() {
        return "com.example.links.Product";
    };


    Object.defineProperty(theClass.prototype, 'id', {
        get: function() {
            return this._getPrimitiveAttribute(this.$loader.getClass("com.example.links.Product").ATTR_ID);
        },
        set: function(id) {
            this._setPrimitiveAttribute(this.$loader.getClass("com.example.links.Product").ATTR_ID, id);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'name', {
        get: function() {
            return this._getPrimitiveAttribute(this.$loader.getClass("com.example.links.Product").ATTR_NAME);
        },
        set: function(name) {
            this._setPrimitiveAttribute(this.$loader.getClass("com.example.links.Product").ATTR_NAME, name);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'state', {
        get: function() {
            return this._getPrimitiveAttribute(this.$loader.getClass("com.example.links.Product").ATTR_STATE);
        },
        set: function(state) {
            this._setPrimitiveAttribute(this.$loader.getClass("com.example.links.Product").ATTR_STATE, state);
        },
        enumerable: true
    });

};

bpm.data.Loader.classDefiner["com.example.links.Product"]();
