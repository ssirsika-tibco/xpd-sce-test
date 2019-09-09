/*
 * This provides an implementation of the BOM class com.example.process.PRocess.PRocessProcess. 
 */
bpm.data.Loader.classDefiner["com.example.process.PRocess.PRocessProcess"] = function() {
    /** Constructor. */
    var theClass = function(context) {
        this._init(theClass, context);
    };

    theClass.LOADER = bpm.data.Loader.currentLoader;
    theClass.LOADER.registerClass(theClass, "com.example.process.PRocess.PRocessProcess");
    bpm.data.Loader.extendClass(bpm.data.BomBase, theClass);

    theClass.TYPE_ARRAY = {};

    theClass.PRIMITIVE_ATTRIBUTE_NAMES = [

    ];
    theClass.COMPOSITE_ATTRIBUTE_NAMES = [

    ];
    theClass.ATTRIBUTE_NAMES = [

    ];

    theClass.getName = function() {
        return "com.example.process.PRocess.PRocessProcess";
    };

};

bpm.data.Loader.classDefiner["com.example.process.PRocess.PRocessProcess"]();
