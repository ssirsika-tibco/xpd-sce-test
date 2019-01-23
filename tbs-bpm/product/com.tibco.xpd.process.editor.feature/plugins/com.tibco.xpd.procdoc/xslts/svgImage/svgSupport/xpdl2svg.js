/*
 * NOTE: PLEASE KEEP THIS IN SYNC WITH xpdl2svg.xslt outputScripts template. 
 */
var svgdoc;

function onLoad() {
	// Save a pointer to the svgDocument object so that we can use it to
	// retrieve various named elements in the svg document.
	svgdoc = document;
}

var mouseOverOrigStrokeWidth;
var mouseOverExtraStokeWidthObjectId;

function objectMouseOver(evt) {
	var shape = getMainShape(evt);

	if (shape != null) {
		id = shape.getAttribute("id");

		if (mouseOverExtraStokeWidthObjectId != id) {
			if (mouseOverExtraStokeWidthObjectId != null) {
				shape.setAttribute("stroke-width", mouseOverOrigStrokeWidth);
				mouseOverExtraStokeWidthObjectId = null;
			}

			mouseOverExtraStokeWidthObjectId = id;
			mouseOverOrigStrokeWidth = shape.getAttribute("stroke-width");
			shape.setAttribute("stroke-width",
					Number(mouseOverOrigStrokeWidth) * 2.5);

			evt.stopPropagation();
		}
	}
}

function objectMouseOut(evt) {
	var shape = getMainShape(evt);
	if (shape != null) {
		id = shape.getAttribute("id");

		if (mouseOverExtraStokeWidthObjectId == id) {
			shape.setAttribute("stroke-width", mouseOverOrigStrokeWidth);
			mouseOverExtraStokeWidthObjectId = null;

			evt.stopPropagation();
		}
	}
}

function objectMouseClick(evt) {
	diagramObject = getDiagramObject(evt.target);
	if (diagramObject != null) {
		debugMessage("Click: " + diagramObject.getAttribute("id"));

		if (parent != null && parent.document != null) {
			newLocation = parent.document.location;

			newLocation.hash = diagramObject.getAttribute("id");

			parent.location.href = newLocation.href;

			// We have nested elements all with onclick, make sure we don't get
			// one for each.
			evt.stopPropagation();
		}
	}
}

// 
// Given the Id of an object (the &amp;lt;g&amp;gt; group element for an object
// is, as standard, just the XPDL id)
// Return the element that represents the main shape of the object.
//
function getMainShape(evt) {
	diagramObject = getDiagramObject(evt.target);

	var id = diagramObject.getAttribute("id");

	mainId = "MainShape_" + id;
	mainShape = svgdoc.getElementById(mainId);
	return mainShape;
}

//
// Given an event calculate the diagram object DOM element in the svg
//
function getDiagramObject(object) {
	while (object != null) {
		classattr = object.getAttribute("class");

		if (classattr != null && classattr.indexOf("DiagramObject") >= 0) {
			return object;
		}

		object = object.parentNode;
	}

	return null;
}

// 
// Given the Id of an object (the &amp;lt;g&amp;gt; group element for an object
// is, as standard, just the XPDL id)
// Return the element that represents the label of the object (if any exists)
//
function getLabelElement(evt) {
	var id = evt.currentTarget.getAttribute("id");

	return svgdoc.getElementById("Label_" + id);
}

function debugMessage(msg) {
	debug = svgdoc.getElementById("_DEBUG_");
	if (debug != null) {
		debug.firstChild.nodeValue = msg;
	}
}
