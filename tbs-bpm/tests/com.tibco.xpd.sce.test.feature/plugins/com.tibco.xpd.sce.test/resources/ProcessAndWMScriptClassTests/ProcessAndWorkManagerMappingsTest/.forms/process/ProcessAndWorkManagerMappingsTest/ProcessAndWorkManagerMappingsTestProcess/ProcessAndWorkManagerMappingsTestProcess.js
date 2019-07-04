
/*
 * This provides an implementation of the BOM class $Process.Data. 
 */

com.tibco.data.Loader.classDefiner["$Process.Data"] = function() {
    /** Constructor. */
    var theClass = function(context) {
        this._init(theClass, context);
    };

    theClass.LOADER = com.tibco.data.Loader.currentLoader;
    theClass.LOADER.registerClass(theClass, "$Process.Data");
    com.tibco.data.Loader.extendClass(com.tibco.data.BomBase, theClass);

    theClass.ATTR_FROMPROCESSSTARTTIME = "FromProcessStartTime";
    theClass.ATTR_TOPROCESSPRIORITY = "ToProcessPriority";
    theClass.ATTR_FROMPROCESSPRIORITY = "FromProcessPriority";
    theClass.ATTR_TOWORKITEMPRIORITY = "ToWorkItemPriority";
    theClass.ATTR_TOWORKITEMCANCEL = "ToWorkItemCancel";
    theClass.ATTR_TOWORKITEMTEXTATTR = "ToWorkItemTextAttr";
    theClass.ATTR_TOWORKITEMNUMBERATTR = "ToWorkItemNumberAttr";
    theClass.ATTR_TOWORKITEMINTEGERATTR = "ToWorkItemIntegerAttr";
    theClass.ATTR_TOWORKITEMDATETIMEATTR = "ToWorkitemDateTimeAttr";
    theClass.ATTR_FROMWORKITEMINTEGERATTR = "FromWorkItemIntegerAttr";
    theClass.ATTR_FROMWORKITEMDATETIMEATTR = "FromWorkitemDateTimeAttr";
    theClass.ATTR_FROMWORKITEMTEXTATTR = "FromWorkItemTextAttr";
    theClass.ATTR_FROMWORKITEMCANCEL = "FromWorkItemCancel";
    theClass.ATTR_FROMWORKITEMPRIORITY = "FromWorkItemPriority";
    theClass.ATTR_FROMWORKITEMNUMBERATTR = "FromWorkItemNumberAttr";

    theClass.TYPE_ARRAY = new Object();
    theClass.TYPE_ARRAY[theClass.ATTR_FROMPROCESSSTARTTIME] = {
        type: "BomPrimitiveTypes.DateTime",
        baseType: "BomPrimitiveTypes.DateTime",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_TOPROCESSPRIORITY] = {
        type: "BomPrimitiveTypes.Decimal",
        baseType: "BomPrimitiveTypes.Decimal",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: 15,
        decimalPlaces: 0

    };
    theClass.TYPE_ARRAY[theClass.ATTR_FROMPROCESSPRIORITY] = {
        type: "BomPrimitiveTypes.Decimal",
        baseType: "BomPrimitiveTypes.Decimal",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: 15,
        decimalPlaces: 0

    };
    theClass.TYPE_ARRAY[theClass.ATTR_TOWORKITEMPRIORITY] = {
        type: "BomPrimitiveTypes.Decimal",
        baseType: "BomPrimitiveTypes.Decimal",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: 15,
        decimalPlaces: 0

    };
    theClass.TYPE_ARRAY[theClass.ATTR_TOWORKITEMCANCEL] = {
        type: "BomPrimitiveTypes.Boolean",
        baseType: "BomPrimitiveTypes.Boolean",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_TOWORKITEMTEXTATTR] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_TOWORKITEMNUMBERATTR] = {
        type: "BomPrimitiveTypes.Decimal",
        baseType: "BomPrimitiveTypes.Decimal",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: 10,
        decimalPlaces: 2

    };
    theClass.TYPE_ARRAY[theClass.ATTR_TOWORKITEMINTEGERATTR] = {
        type: "BomPrimitiveTypes.Decimal",
        baseType: "BomPrimitiveTypes.Decimal",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: 15,
        decimalPlaces: 0

    };
    theClass.TYPE_ARRAY[theClass.ATTR_TOWORKITEMDATETIMEATTR] = {
        type: "BomPrimitiveTypes.DateTime",
        baseType: "BomPrimitiveTypes.DateTime",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_FROMWORKITEMINTEGERATTR] = {
        type: "BomPrimitiveTypes.Decimal",
        baseType: "BomPrimitiveTypes.Decimal",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: 15,
        decimalPlaces: 0

    };
    theClass.TYPE_ARRAY[theClass.ATTR_FROMWORKITEMDATETIMEATTR] = {
        type: "BomPrimitiveTypes.DateTime",
        baseType: "BomPrimitiveTypes.DateTime",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_FROMWORKITEMTEXTATTR] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_FROMWORKITEMCANCEL] = {
        type: "BomPrimitiveTypes.Boolean",
        baseType: "BomPrimitiveTypes.Boolean",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_FROMWORKITEMPRIORITY] = {
        type: "BomPrimitiveTypes.Decimal",
        baseType: "BomPrimitiveTypes.Decimal",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: 15,
        decimalPlaces: 0

    };
    theClass.TYPE_ARRAY[theClass.ATTR_FROMWORKITEMNUMBERATTR] = {
        type: "BomPrimitiveTypes.Decimal",
        baseType: "BomPrimitiveTypes.Decimal",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: 10,
        decimalPlaces: 2

    };

    theClass.PRIMITIVE_ATTRIBUTE_NAMES = [
        theClass.ATTR_FROMPROCESSSTARTTIME,
        theClass.ATTR_TOPROCESSPRIORITY,
        theClass.ATTR_FROMPROCESSPRIORITY,
        theClass.ATTR_TOWORKITEMPRIORITY,
        theClass.ATTR_TOWORKITEMCANCEL,
        theClass.ATTR_TOWORKITEMTEXTATTR,
        theClass.ATTR_TOWORKITEMNUMBERATTR,
        theClass.ATTR_TOWORKITEMINTEGERATTR,
        theClass.ATTR_TOWORKITEMDATETIMEATTR,
        theClass.ATTR_FROMWORKITEMINTEGERATTR,
        theClass.ATTR_FROMWORKITEMDATETIMEATTR,
        theClass.ATTR_FROMWORKITEMTEXTATTR,
        theClass.ATTR_FROMWORKITEMCANCEL,
        theClass.ATTR_FROMWORKITEMPRIORITY,
        theClass.ATTR_FROMWORKITEMNUMBERATTR
    ];
    theClass.COMPOSITE_ATTRIBUTE_NAMES = [

    ];
    theClass.ATTRIBUTE_NAMES = [
        theClass.ATTR_FROMPROCESSSTARTTIME,
        theClass.ATTR_TOPROCESSPRIORITY,
        theClass.ATTR_FROMPROCESSPRIORITY,
        theClass.ATTR_TOWORKITEMPRIORITY,
        theClass.ATTR_TOWORKITEMCANCEL,
        theClass.ATTR_TOWORKITEMTEXTATTR,
        theClass.ATTR_TOWORKITEMNUMBERATTR,
        theClass.ATTR_TOWORKITEMINTEGERATTR,
        theClass.ATTR_TOWORKITEMDATETIMEATTR,
        theClass.ATTR_FROMWORKITEMINTEGERATTR,
        theClass.ATTR_FROMWORKITEMDATETIMEATTR,
        theClass.ATTR_FROMWORKITEMTEXTATTR,
        theClass.ATTR_FROMWORKITEMCANCEL,
        theClass.ATTR_FROMWORKITEMPRIORITY,
        theClass.ATTR_FROMWORKITEMNUMBERATTR
    ];

    theClass.getName = function() {
        return "$Process.Data";
    };


    Object.defineProperty(theClass.prototype, 'FromProcessStartTime', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_FROMPROCESSSTARTTIME);
        },
        set: function(FromProcessStartTime) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_FROMPROCESSSTARTTIME, FromProcessStartTime);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'ToProcessPriority', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_TOPROCESSPRIORITY);
        },
        set: function(ToProcessPriority) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_TOPROCESSPRIORITY, ToProcessPriority);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'FromProcessPriority', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_FROMPROCESSPRIORITY);
        },
        set: function(FromProcessPriority) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_FROMPROCESSPRIORITY, FromProcessPriority);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'ToWorkItemPriority', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_TOWORKITEMPRIORITY);
        },
        set: function(ToWorkItemPriority) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_TOWORKITEMPRIORITY, ToWorkItemPriority);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'ToWorkItemCancel', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_TOWORKITEMCANCEL);
        },
        set: function(ToWorkItemCancel) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_TOWORKITEMCANCEL, ToWorkItemCancel);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'ToWorkItemTextAttr', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_TOWORKITEMTEXTATTR);
        },
        set: function(ToWorkItemTextAttr) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_TOWORKITEMTEXTATTR, ToWorkItemTextAttr);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'ToWorkItemNumberAttr', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_TOWORKITEMNUMBERATTR);
        },
        set: function(ToWorkItemNumberAttr) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_TOWORKITEMNUMBERATTR, ToWorkItemNumberAttr);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'ToWorkItemIntegerAttr', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_TOWORKITEMINTEGERATTR);
        },
        set: function(ToWorkItemIntegerAttr) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_TOWORKITEMINTEGERATTR, ToWorkItemIntegerAttr);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'ToWorkitemDateTimeAttr', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_TOWORKITEMDATETIMEATTR);
        },
        set: function(ToWorkitemDateTimeAttr) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_TOWORKITEMDATETIMEATTR, ToWorkitemDateTimeAttr);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'FromWorkItemIntegerAttr', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_FROMWORKITEMINTEGERATTR);
        },
        set: function(FromWorkItemIntegerAttr) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_FROMWORKITEMINTEGERATTR, FromWorkItemIntegerAttr);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'FromWorkitemDateTimeAttr', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_FROMWORKITEMDATETIMEATTR);
        },
        set: function(FromWorkitemDateTimeAttr) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_FROMWORKITEMDATETIMEATTR, FromWorkitemDateTimeAttr);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'FromWorkItemTextAttr', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_FROMWORKITEMTEXTATTR);
        },
        set: function(FromWorkItemTextAttr) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_FROMWORKITEMTEXTATTR, FromWorkItemTextAttr);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'FromWorkItemCancel', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_FROMWORKITEMCANCEL);
        },
        set: function(FromWorkItemCancel) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_FROMWORKITEMCANCEL, FromWorkItemCancel);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'FromWorkItemPriority', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_FROMWORKITEMPRIORITY);
        },
        set: function(FromWorkItemPriority) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_FROMWORKITEMPRIORITY, FromWorkItemPriority);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'FromWorkItemNumberAttr', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_FROMWORKITEMNUMBERATTR);
        },
        set: function(FromWorkItemNumberAttr) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_FROMWORKITEMNUMBERATTR, FromWorkItemNumberAttr);
        },
        enumerable: true
    });

};

com.tibco.data.Loader.classDefiner["$Process.Data"]();
