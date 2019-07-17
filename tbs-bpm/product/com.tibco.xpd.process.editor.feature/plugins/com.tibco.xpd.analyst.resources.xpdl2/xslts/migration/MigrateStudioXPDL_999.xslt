<?xml version="1.0" encoding="UTF-8"?>
<!--
	===================================================================================================================
	XSLT:       MigrateStudioXPDL_21.xslt
	
	DESCRIPTION:

  	    This xslt will updgrade a xpdl of UP TO format version 999 (4.3.0 V09) to format version 1000 (Studio Container Edition 5.0.0 (V95)
  	    (marks the transition between BPMN and SCE Studio)).

		- Replace BPM destination with CE destination in process files.

		- Removal of simulation namespace and elements and attributes (this will need to be done in migration XSLT as the simulation 
		  model has been removed from code base)
		
		- Removal of iProcess, eaijava and database extension namespace and elements and attributes
		
		- Remove 'Publish as REST Service' option from business processes and the hidden derived pageflows from XPDL files.

		- Remove all javascript/XPath mappings for all WSDL related activities Service task, receive task, send tasks, message events, 
		  catch WSDL fault events. (in this case we will not unset the types, so that users can see where there PAAS entry points used 
		  to be before changing them to event type none etc)

		- Remove JavaScript data mappings from non-WSDL related event / task types (sub-process, signals, catch error)
	
		- Remove current configuration information from REST and WEB service system participants (as in SCE the configuration UI 
		  and model will be different).
	
		- Remove Allowed Values configuration from all non-text process  parameters (to put it on an even footing with 
		  BOM/CM property capabilities, I don't think this is widely used anyway - UI should provide min/max values for process data instead)
		  
		- Convert Integer data fields and parameters to Decimals with num decimals = 0
		
		- Remove process package version (we only use project version now).
		
  	    (See XpdlMigrate.java for format version <-> Studio version equivalence).
  	     
	===================================================================================================================
-->
<xsl:stylesheet version="1.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xpdExt="http://www.tibco.com/XPD/xpdExtension1.0.0"
	xmlns:eaijava="http://www.tibco.com/XPD/EAIJava1.0.0" 
	xmlns:simulation="http://www.tibco.com/xpd/Simulation1.0.1"
	xmlns:database="http://www.tibco.com/XPD/database1.0.0"
	xmlns:email="http://www.tibco.com/XPD/email1.0.0"
	xmlns:iProcessExt="http://www.tibco.com/XPD/iProcessExt1.0.0"	
	xmlns:java="http://xml.apache.org/xslt/java"

	xmlns:xpdl2="http://www.wfmc.org/2008/XPDL2.1"
	
	exclude-result-prefixes="java"
	>

	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" />

	<!--
	===============================================================================
	XSLT processing start point (process whole document)
	===============================================================================
	-->
	<xsl:template match="/">
		<xsl:apply-templates/>
	</xsl:template>

	<!--
	===============================================================================
	Convert Integer data fields and parameters to Decimals with num decimals = 0
	===============================================================================
    -->
    <xsl:template match="xpdl2:BasicType[@Type = 'INTEGER']">
		<xpdl2:BasicType Type="FLOAT">
			<xsl:copy-of select="./xpdl2:Precision"/>
			<xpdl2:Scale>0</xpdl2:Scale>
		</xpdl2:BasicType>
	</xsl:template>


	<!--
	===============================================================================
	Remove Allowed Values configuration from all non-text process  parameters (to put it on an even footing with 
	BOM/CM property capabilities, I don't think this is widely used anyway - UI should provide min/max values for process data instead)
	===============================================================================
    -->
	<xsl:template match="xpdl2:FormalParameter[xpdl2:DataType and xpdl2:DataType/xpdl2:BasicType and xpdl2:DataType/xpdl2:BasicType/@Type != 'STRING']/xpdExt:InitialValues">
		<!-- Do nothing (e.g. do not output the element)-->
	</xsl:template>
	
	<!--
	===============================================================================
	Remove current configuration information from WEB and JDBC service system participants 
	(as in SCE the configuration UI and model will be different).
	
	Sid ACE-479: Remvoe JBCD participant type info as well.
	Sid ACE-479: Do not remove the entire xpdExt:RestServie element as it is this that 
	             defines the participant as REST type (instead, the next template will 
	             remove all of the configuration within it.
	===============================================================================
    -->
	<xsl:template match="xpdl2:Participant/xpdExt:ParticipantSharedResource[xpdExt:WebService or xpdExt:Jdbc]">
		<!-- Do nothing (e.g. do not output the element)-->
	</xsl:template>

	<xsl:template match="xpdl2:Participant/xpdExt:ParticipantSharedResource/xpdExt:RestService">
		<!-- Just output the bare element -->
		<xpdExt:RestService>
			<!-- In case it is already migrated (originally we did that format version 23 before changeing to 1000 copy the new ACE attrib values  -->
			<xsl:if test="@resourceName">
				<xsl:attribute name="resourceName"><xsl:value-of select="@resourceName"/></xsl:attribute>
			</xsl:if>
			<xsl:if test="@description">
				<xsl:attribute name="description"><xsl:value-of select="@description"/></xsl:attribute>
			</xsl:if>

		</xpdExt:RestService>

	</xsl:template>

	<!--
	===============================================================================
	Remove All JavaScript or XPath mapping groups that are not part of DataMapper elements.
	===============================================================================
    -->
    <!-- Global and Local, Throw and Catch Signal event mappings -->
	<xsl:template match="xpdExt:SignalData/xpdExt:DataMappings[xpdExt:DataMapping/xpdl2:Actual/@ScriptGrammar = 'JavaScript']">
		<!-- Do nothing (e.g. do not output xpdExt:SignalData/xpdExt:DataMappings when has javaScript mappings inside)-->
	</xsl:template>
	
    <!-- Global Signal correlation mappings  -->
	<xsl:template match="xpdExt:CorrelationMappings[xpdExt:DataMapping/xpdl2:Actual/@ScriptGrammar = 'JavaScript']">
		<!-- Do nothing (e.g. do not output xpdExt:CorrelationMappings when has JavaScript / XPath mappings inside-->
	</xsl:template>

    <!-- All other normal data mappings (all DataMapper mappings are encapsulated in nodes other than xpdl2:DataMappings) -->
	<xsl:template match="xpdl2:DataMappings[xpdl2:DataMapping/xpdl2:Actual/@ScriptGrammar = 'JavaScript' or xpdl2:DataMapping/xpdl2:Actual/@ScriptGrammar = 'XPath']">
		<!-- Do nothing (e.g. do not output xpdl2:DataMappings when has JavaScript / XPath mappings inside-->
	</xsl:template>

    <!-- All other correlation mappings (for DataMapper, correlation mappings are integral to the approach so no separate items for these (so don't need to worry about deleting DataMapper grammar ones here) -->
	<xsl:template match="xpdExt:CorrelationDataMappings[xpdExt:DataMapping/xpdl2:Actual/@ScriptGrammar = 'JavaScript' or xpdExt:DataMapping/xpdl2:Actual/@ScriptGrammar = 'XPath']">
		<!-- Do nothing (e.g. do not output xpdExt:CorrelationDataMappings when has JavaScript / XPath mappings inside-->
	</xsl:template>
	
    <!-- Unmapped script mappings and script-mapping used to designated that the activity's mappings grammar is JavaScript or XPath (this approach not used for DataMapper)-->
	<xsl:template match="xpdl2:Activity/xpdExt:ScriptInformation[xpdExt:Expression/@ScriptGrammar = 'JavaScript' or xpdExt:Expression/@ScriptGrammar = 'XPath']">
		<!-- Do nothing (e.g. do not output xpdExt:CorrelationMapping when has JavaScript / XPath mappings inside-->
	</xsl:template>

	<!--
	===============================================================================
	Remove Publish as REST service attributes and the hidden pageflow that supports the service API
	===============================================================================
    -->
	<xsl:template match="@xpdExt:publishAsRestService">
		<!-- Do nothing (e.g. do not output attribute)-->
	</xsl:template>
	
	<xsl:template match="xpdl2:WorkflowProcess/xpdExt:RESTServices">
		<!-- Do nothing (e.g. do not output the element)-->
	</xsl:template>
	
	<!--
	===============================================================================
	Remove all references to simulation schema elements (the extension model contribution is
	no longer part of the SCE feature set so have to remove it). 
	===============================================================================
    -->
	<xsl:template match="@simulation:* | simulation:*">
		<!-- Do nothing (e.g. do not output the attribute / element)-->
	</xsl:template>

	<xsl:template match="xpdl2:ExtendedAttribute[@Name = 'ParticipantSimulationData' or @Name = 'StartSimulationData' or @Name = 'ActivitySimulationData' or @Name = 'WorkflowProcessSimulationData']">
		<!-- Do nothing (e.g. do not output the extended attribute / element)-->
	</xsl:template>

	<!--
	===============================================================================
	Remove all references to eaijava schema elements (the extension model contribution is
	no longer part of the SCE feature set so have to remove it). 
	===============================================================================
    -->
	<xsl:template match="@eaijava:* | eaijava:*">
		<!-- Do nothing (e.g. do not output the attribute / element)-->
	</xsl:template>
	
	<!--
	===============================================================================
	Remove all references to database schema elements (the extension model contribution is
	no longer part of the SCE feature set so have to remove it). 
	===============================================================================
    -->
	<xsl:template match="@database:* | database:*">
		<!-- Do nothing (e.g. do not output the attribute / element)-->
	</xsl:template>

	
	<!--
	===============================================================================
	Remove all references to iProcessExt schema elements (the extension model contribution is
	no longer part of the SCE feature set so have to remove it). 
	===============================================================================
    -->
	<xsl:template match="@iProcessExt:* | iProcessExt:*">
		<!-- Do nothing (e.g. do not output the attribute / element)-->
	</xsl:template>

	<!--
	===============================================================================
	Remove all destination environments
	===============================================================================
    -->
	<xsl:template match="xpdl2:WorkflowProcess/xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'Destination'] | xpdExt:ProcessInterface/xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'Destination']">
		<!-- Do nothing (e.g. do not output existing destination attribute)-->
	</xsl:template>
    
	<!--
	===============================================================================
	Add the single new ACE "CE" one
	===============================================================================
    -->
	<xsl:template match="xpdl2:WorkflowProcess/xpdl2:ExtendedAttributes | xpdExt:ProcessInterface/xpdl2:ExtendedAttributes">
		<xsl:copy>
			<xsl:apply-templates select="@* | node() | text()"/>

			<xpdl2:ExtendedAttribute Name="Destination" Value="CE"/>
		</xsl:copy>
	</xsl:template>
	
	<!--  For process / process interface that does not have ExtendedAttributes at all it means we have 
		Grab ab process / inteface level, copy, apply templates to all content and then add the CE destination -->
	<xsl:template match="xpdl2:WorkflowProcess | xpdExt:ProcessInteface">
	

		<xsl:copy>
			<xsl:apply-templates select="@* | node() | text()"/>
			
			<xsl:if test="not(./xpdl2:ExtendedAttributes)">
				<xpdl2:ExtendedAttributes>
					<xpdl2:ExtendedAttribute Name="Destination" Value="CE"/>
				</xpdl2:ExtendedAttributes>
			</xsl:if>
		</xsl:copy>
	</xsl:template>


	<!--
	===============================================================================
	Remove process package version
	===============================================================================
    -->
	<xsl:template match="xpdl2:RedefinableHeader/xpdl2:Version">
		<!--  Not outputting the version element to target will effectively remove it -->
	</xsl:template>
	
	<!--
	===============================================================================
	Update the format version.
	===============================================================================
    -->
	<xsl:template match="xpdl2:ExtendedAttribute[@Name = 'FormatVersion']">
		<xsl:element name="xpdl2:ExtendedAttribute">
			<xsl:attribute name="Name">FormatVersion</xsl:attribute>
			<xsl:attribute name="Value">1000</xsl:attribute>
		</xsl:element>
	</xsl:template>
	
	<!--
	===============================================================================
	Default copy all template.
	===============================================================================
    -->
	<xsl:template match="node()" priority="-9">
		<xsl:if test="name() != ''">
			<xsl:copy>
				<xsl:apply-templates select="@* | node() | text()"/>
			</xsl:copy>
		</xsl:if>
	</xsl:template>
		
	<xsl:template match="@* | text()" priority="-9">
		<!-- Copy input attribute to output. -->
		<xsl:copy>
			<xsl:apply-templates select="@* | text()"/>
		</xsl:copy>
	</xsl:template>

</xsl:stylesheet>
