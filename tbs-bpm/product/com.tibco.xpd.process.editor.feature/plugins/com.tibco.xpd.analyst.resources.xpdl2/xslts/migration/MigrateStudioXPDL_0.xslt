<?xml version="1.0" encoding="UTF-8"?>

<!--
===================================================================================================================
XSLT:		MigrateStudioXPDL_0.xslt

DESCRIPTION:
	This xslt will updgrade a xpdl of format version 0 to format version 1.  This includes (summary):
		1. Upgrading the extensions and simulations namespace to version 1.0.0.
		2. Adding of FormatVersion to the extended attributes of the package.  Also update the created by.
		3. Adding a unique ID to notes.
		4. Association for notes use the note ID and activity ID.
		5. Destination Environment has it's own extended attribute.
		6. Simulation has some attribute name changes.
		7. Default gateway types are now converted to XOR_DATA (no default option in format version 1).

===================================================================================================================
-->

<!-- 
	DON'T USE <xsl:copy> for source doc's extensions:elements !!!!!  THIS FORCES ANY MISSING namespace declarations from old document onto attributes of the copied element.

	When we created the <xpdl:Package> root element we forced swap of extensions: to be namespace 1.0.0

	If we create an xsl:element then xslt engine will just assume it's for the output doc's idea of "extensions" namespace.

	If we use xsl:copy the xsl engine assumes the element is from the source document' s extensions namespace.

	Any extensions: elements output from this xslt will have to have the new namespace provided, otherwise it will carry the old namespace to the output file
-->

<!-- Define constants -->
<!DOCTYPE xsl:stylesheet [
	<!ENTITY extensionsNS				'http://www.tibco.com/xpd/XpdlExtensions1.0.0'>
	<!ENTITY simulationNS				'http://www.tibco.com/xpd/Simulation1.0.0'>
	<!ENTITY createdBy					'TIBCO Business Studio 1.0.0'>
	<!ENTITY formatVersion				'FormatVersion'>
	<!ENTITY formatVersionValue	'1'>
	<!ENTITY noteIDPrefix					'n'>
]>


<xsl:stylesheet version="1.0" 
	xmlns = "http://www.wfmc.org/2002/XPDL1.0"
	xmlns:xpdl = "http://www.wfmc.org/2002/XPDL1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:extensions="http://www.tibco.com/xpd/XpdlExtensions"
	xmlns:simulation="http://www.tibco.com/xpd/Simulation"
>
<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>

<!--
===================================================================================================================
	XSLT processing start point (process whole document)
===================================================================================================================
-->
<xsl:template match="/">

	<xsl:apply-templates />
	
</xsl:template>

<xsl:template match="xpdl:Package">

	<!-- Create our own package element so that we can override the xmlns:extensions to reference 1.0.0 namespace -->
	<xpdl:Package 
		xmlns:xpdl="http://www.wfmc.org/2002/XPDL1.0" 
		xmlns:extensions="&extensionsNS;"
		xmlns:simulation="&simulationNS;"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" >

		<xsl:apply-templates select="@* | node()" />

	</xpdl:Package>

</xsl:template>

<!--
===================================================================================================================
	xpdl:Package/xpdl:ExtendedAttributes = add the format version to the extended attributes
===================================================================================================================
-->
<xsl:template match="xpdl:Package/xpdl:ExtendedAttributes">
	
	<xsl:element name="{name()}">
		<!-- Copy all extended attributes -->
		<xsl:apply-templates />
		<!-- Add the format version extended attribute attribute -->
		<xsl:element name="xpdl:ExtendedAttribute">
			<xsl:attribute name="Name">
				<xsl:text>&formatVersion;</xsl:text>
			</xsl:attribute>
			<xsl:attribute name="Value">
				<xsl:text>&formatVersionValue;</xsl:text>
			</xsl:attribute>
		</xsl:element>
	</xsl:element>
	
</xsl:template>

<!--
===================================================================================================================
	xpdl:Package/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute = update the CreatedBy
===================================================================================================================
-->
<xsl:template match="xpdl:Package/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'CreatedBy']">
	<xsl:element name="{name()}">
		<xsl:for-each select="@*">
			<xsl:choose>
				<xsl:when test="name() = 'Value'">
					<xsl:attribute name="Value">
						<xsl:text>&createdBy;</xsl:text>
					</xsl:attribute>
				</xsl:when>
				<xsl:otherwise>
					<xsl:apply-templates select="." />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>
	</xsl:element>
</xsl:template>

<!--
===================================================================================================================
	xpdl:Activity/../extensions:Activity = Default gateway types should be changed to XOR Data types
===================================================================================================================
-->
<xsl:template match="xpdl:Activity/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'XPD']/extensions:Activity[extensions:ActivityType = 'GATEWAY']">
	<xsl:element name="{name()}" namespace="&extensionsNS;">
		<xsl:apply-templates />
		<!-- If no gateway type specified then set to XOR Data -->
		<xsl:if test="not (extensions:GatewayType)">
			<xsl:element name="extensions:GatewayType" namespace="&extensionsNS;">
				<xsl:text>XOR_DATA</xsl:text>
			</xsl:element>
		</xsl:if>
	</xsl:element>
</xsl:template>

<!--
===================================================================================================================
	extensions:GatewayType = If gateway type is DEFAULT then change to XOR_DATA (no default gateway in format version 1).
===================================================================================================================
-->
<xsl:template match="extensions:GatewayType">
	<xsl:element name="{name()}" namespace="&extensionsNS;">
		<xsl:choose>
			<xsl:when test="text() = 'DEFAULT'">
				<xsl:text>XOR_DATA</xsl:text>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="." />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:element>
</xsl:template>

<!--
===================================================================================================================
	extensions:TargetAssociations in Activity = Ignore this as the note association is done using the note ID.
===================================================================================================================
-->
<xsl:template match="xpdl:Activity/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'XPD']/extensions:Activity/extensions:TargetAssociations">
	<!-- Do nothing as this element is redundant now. -->
</xsl:template>

<!--
===================================================================================================================
	extensions:Note = Process the notes - add a unique ID for each note
===================================================================================================================
-->
<xsl:template match="extensions:Note">
	<xsl:variable name="thisNote" select="." />
	
	<!-- Get all notes -->
	<xsl:variable name="notes" select="//extensions:Note" />
	
	<xsl:element name="{name()}" namespace="&extensionsNS;">
		<xsl:attribute name="Id">
			<!-- Get the unique id for this node -->
			<xsl:apply-templates select="." mode="getNoteID" />
		</xsl:attribute>
		
		<!-- Copy all other attributes and nodes -->
		<xsl:apply-templates  select="@* | node()" />
		
	</xsl:element>
</xsl:template>

<!--
===================================================================================================================
	extensions:Note (getNoedID) = This template will return an unique ID for this note
===================================================================================================================
-->
<xsl:template match="extensions:Note" mode="getNoteID">
	<!-- Get the count of all notes that appear in the preceding lanes to this one -->
	<xsl:variable name="prevLanesNotesCount" select="count (../preceding-sibling::extensions:Lane/extensions:Note)" />
	<!-- Get count of all notes before this one -->
	<xsl:variable name="prevNotesCount" select="count (preceding-sibling::extensions:Note)" />

	<!-- 
		Unique ID can be calculated by adding all previous notes in previous lanes, previous notes in this lane 
		and 1 (to make it 1-based) - precede the ID with an 'n'
	 -->
	 <xsl:text>&noteIDPrefix;</xsl:text>
	 <xsl:value-of select="$prevLanesNotesCount + $prevNotesCount + 1" />
	
</xsl:template>

<!--
===================================================================================================================
	extensions:Note/extensions:Text = Multi-line notes have extra carriage returns between each line - this needs to be stripped
===================================================================================================================
-->
<xsl:template match="extensions:Note/extensions:Text">
	<!-- Remove extra new line from the text -->
	<xsl:element name="{name()}" namespace="&extensionsNS;">
		<xsl:call-template name="stripExtraCR">
			<xsl:with-param name="string" select="text()" />
		</xsl:call-template>
	</xsl:element>
</xsl:template>

<!--
===================================================================================================================
	extensions:Note/extensions:SourceAssociation = Ignore this as the note association is done using the note ID.
===================================================================================================================
-->
<xsl:template match="extensions:Note/extensions:SourceAssociations">
	<!-- Do nothing as this is redundant now -->
</xsl:template>

<!--
===================================================================================================================
	xpdl:WorkflowProcess/xpdl:ExtendedAttributes = Move the Destination Environment extended attibute out of the XPD attributes and
	into DestinationEnvironments attribute
===================================================================================================================
-->
<xsl:template match="xpdl:WorkflowProcess/xpdl:ExtendedAttributes">

	<xsl:element name="{name()}">
		<!-- Copy any attributes -->
		<xsl:apply-templates select="@*" />
		
		<!-- If destination environment set then move it into it's own extended attribute -->
		<xsl:if test="xpdl:ExtendedAttribute[@Name = 'XPD']/extensions:DestinationEnvironments">
			<xsl:element name="xpdl:ExtendedAttribute">
				<xsl:attribute name="Name">
					<xsl:value-of select="local-name (xpdl:ExtendedAttribute[@Name = 'XPD']/extensions:DestinationEnvironments)" />
				</xsl:attribute>

				<xsl:element name="extensions:DestinationEnvironments" namespace="&extensionsNS;">

					<xsl:for-each select="xpdl:ExtendedAttribute[@Name = 'XPD']/extensions:DestinationEnvironments/*">
					
						<xsl:element name="extensions:DestinationEnvironment" namespace="&extensionsNS;">
							<xsl:apply-templates select="@* | node()"/>
						</xsl:element>
					
					</xsl:for-each>

				</xsl:element>

			</xsl:element>
		</xsl:if>
		
		<!-- Copy the rest of the nodes -->
		<xsl:apply-templates select="node()" />
		
	</xsl:element>

</xsl:template>

<!--
===================================================================================================================
	extensions:DestinationEnvironments = Ignore this extended attribute as it has been relocated (see template above)
===================================================================================================================
-->
<xsl:template match="extensions:DestinationEnvironments">
	<!-- Do nothing -->
</xsl:template>

<!--
===================================================================================================================
	extensions:Diagram = Replace the old-style note associations with the new style
===================================================================================================================
-->
<xsl:template match="extensions:Diagram">
	<xsl:element name="{name()}" namespace="&extensionsNS;">
	
		<!-- Copy all attributes -->
		<xsl:apply-templates select="@*" />
		
		<!-- Copy all nodes except the associations -->
		<xsl:for-each select="node()">
			<xsl:if test="local-name () != 'Association'">
				<xsl:apply-templates select="." />
			</xsl:if>
		</xsl:for-each>
		
		<!-- Add the new style associations -->
		<xsl:call-template name="addNoteAssociations" />
		
	</xsl:element>
</xsl:template>

<!--
===================================================================================================================
	addNoteAssociations = Add the new style note associations
===================================================================================================================
-->
<xsl:template name="addNoteAssociations">
	<!-- Get all notes -->
	<xsl:variable name="notes" select="//extensions:Note" />
	<!-- Get all notes with associations -->
	<xsl:variable name="assocNotes" select="$notes[extensions:SourceAssociations]" />

	<!-- Process each associated note -->
	<xsl:for-each select="$assocNotes">
	
		<!-- Get the unique ID of this note -->
		<xsl:variable name="noteID">
			<xsl:apply-templates select="." mode="getNoteID" />
		</xsl:variable>
		
		<!-- For each association in this note write out a new format association -->
		<xsl:for-each select="extensions:SourceAssociations">
			<xsl:variable name="srcAssoc" select="." />
			<!-- Get the activity that is associated with this link -->
			<xsl:variable name="activity" select="//xpdl:Activity[xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'XPD']/extensions:Activity/extensions:TargetAssociations = $srcAssoc]" />
			
			<xsl:element name="extensions:Association" namespace="&extensionsNS;">
				<xsl:element name="extensions:Source" namespace="&extensionsNS;">
					 <xsl:value-of select="$noteID" />
				</xsl:element>
				<xsl:element name="extensions:Target" namespace="&extensionsNS;">
					  <xsl:value-of select="$activity/@Id" />
				</xsl:element>
			</xsl:element>

		</xsl:for-each>
		
	</xsl:for-each>
</xsl:template>

<!--
===================================================================================================================
	simulation:WorkflowProcessSimulationData/simulation:ParameterDistribution = 
	Convert the workflow process simulation data to new format
===================================================================================================================
-->
<xsl:template match="simulation:WorkflowProcessSimulationData/simulation:ParameterDistribution">

	<xsl:element name="{name()}" namespace="&simulationNS;">
		<!-- Attribbute Name changed to ParameterId -->
		<xsl:if test="@Name">
			<xsl:attribute name="ParameterId">
				<xsl:value-of select="@Name" />
			</xsl:attribute>
		</xsl:if>
		<!-- Copy all other nodes -->
		<xsl:apply-templates select="node()" />
	</xsl:element>
	
</xsl:template>

<!--
===================================================================================================================
	simulation:WorkflowProcessSimulationData/simulation:ParameterDistribution/simulation:EnumerationValue = 
	Convert the enumeration value to the new format
===================================================================================================================
-->
<xsl:template match="simulation:WorkflowProcessSimulationData/simulation:ParameterDistribution/simulation:EnumerationValue">

	<!-- Percentage attribute needs to be change to WeightingFactor -->
	<xsl:element name="{name()}" namespace="&simulationNS;">
		<xsl:for-each select="@*">
			<xsl:choose>
				<xsl:when test="local-name() = 'Percentage'">
					<xsl:attribute name="WeightingFactor">
						<xsl:value-of select="." />
					</xsl:attribute>
				</xsl:when>
				<xsl:otherwise>
					<xsl:copy />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>		
	</xsl:element>

</xsl:template>

<!--
===================================================================================================================
	stripExtraCR = Removes extra carriage returns from the given text.  This template is called recursively.
===================================================================================================================
-->
<xsl:template name="stripExtraCR">
	<xsl:param name="string" />
	
	<!-- Get the substring before the double carriage returns -->
	<xsl:variable name="substr">
		<xsl:value-of select="substring-before ($string, '&#10;&#10;')" />
	</xsl:variable>
	
	<xsl:choose>
		<!-- If double carriage returns are found then process the string -->
		<xsl:when test="$substr != ''">
				<xsl:value-of select="$substr" />
				<!-- Insert only a single carriage return -->
				<xsl:text>&#10;</xsl:text>
				<!-- Process the rest of the string -->
				<xsl:call-template name="stripExtraCR">
					<xsl:with-param name="string" select="substring-after ($string, '&#10;&#10;')" />
				</xsl:call-template>
				
		</xsl:when>
		<!-- If no double carriage returns found then just print the string -->
		<xsl:otherwise>
			<xsl:value-of select="$string" />			
		</xsl:otherwise>
		
	</xsl:choose>
	
</xsl:template>


<!--
===================================================================================================================
	Elements in the extensions namespace - redirect to the new namespace 1.0.0
===================================================================================================================
-->
<xsl:template match="extensions:*">

	<xsl:element name="{name()}" namespace="&extensionsNS;">		
		<xsl:apply-templates select="@* | node()"/>   
	</xsl:element>

</xsl:template>

<!--
===================================================================================================================
	Elements in the simulation namespace - redirect to the new namespace 1.0.0
===================================================================================================================
-->
<xsl:template match="simulation:*">
	<xsl:element name="{name()}" namespace="&simulationNS;">
		<xsl:apply-templates select="@* | node()" />
	</xsl:element>
</xsl:template>

<!--
===================================================================================================================
	General template to copy all nodes
===================================================================================================================
-->
<xsl:template match="node()">

	<xsl:element name="{name()}">
		<xsl:apply-templates select="@* | node()" />
	</xsl:element>
	
</xsl:template>


<!--
===================================================================================================================
	General template to copy all attributes and text of an element
===================================================================================================================
-->
<xsl:template match="@* | text()">
	<!-- Copy input attribute to output. -->
	<xsl:copy>
		<xsl:apply-templates select="@* | text()" />
	</xsl:copy>
</xsl:template>


</xsl:stylesheet>
