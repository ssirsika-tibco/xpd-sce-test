<?xml version="1.0" encoding="UTF-8"?>
<!--
	===================================================================================================================
	XSLT:       MigrateStudioXPDL_14.xslt
	
	DESCRIPTION:

  	    This xslt will updgrade a xpdl of format version 14 (3.5.20 GA) to format version 15 (v3.6.0).

		- Add request activity id attribute to Invoke Business process send tasks.

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
	Add request activity id attribute to Invoke Business process send tasks
	===============================================================================
	-->
	<xsl:template match="xpdl2:TaskSend/xpdExt:BusinessProcess">
		<xsl:element name="xpdExt:BusinessProcess">
			<xsl:apply-templates select="@*"/>

			<!-- Add activity id if we can ascertain it (i.e. only if in local package process) -->
			<xsl:variable name="businessProcessId" select="@BusinessProcessId"/>
			<xsl:variable name="businessProcess" select="/xpdl2:Package/xpdl2:WorkflowProcesses/xpdl2:WorkflowProcess[@Id = $businessProcessId]"/>
			
			<xsl:if test="count(@PackageRef) = 0 and count($businessProcess) > 0">
				<!-- It's a local business process, find the catch message start activity and add it's id to our reference. -->
								
				<xsl:variable name="msgStartEvents" select="$businessProcess//xpdl2:Activity[count(xpdl2:Event/xpdl2:StartEvent/xpdl2:TriggerResultMessage) > 0]"/>
				
				<xsl:if test="count($msgStartEvents) = 1">
					<xsl:attribute name="ActivityId"><xsl:value-of select="$msgStartEvents/@Id"></xsl:value-of></xsl:attribute>
				</xsl:if>
				
			</xsl:if>			
			
			<xsl:apply-templates select="node() | text()"/>
		
		</xsl:element>		

	</xsl:template>
	
	<!--
	===============================================================================
	Update the api end point participant.
	===============================================================================
    -->
	<xsl:template match="xpdl2:WorkflowProcess">
	
		<!--all  activities -->
		<xsl:variable name="activities" select="xpdl2:Activities/xpdl2:Activity | xpdl2:ActivitySets/xpdl2:ActivitySet/xpdl2:Activities/xpdl2:Activity"/>
		
		<!-- All generated WebServiceOperation elements under catch event TriggerResultMessage or Receive Task (i.e. all WSO's for generated WSDL incoming message activities -->
		<xsl:variable name="incomingWSO" select="$activities/xpdl2:Event//xpdl2:TriggerResultMessage[@CatchThrow = 'CATCH' and @xpdExt:Generated = 'true']/xpdl2:WebServiceOperation
													| $activities/xpdl2:Implementation/xpdl2:Task/xpdl2:TaskReceive[@xpdExt:Generated = 'true']/xpdl2:WebServiceOperation"/>

		<!--  xpdExt:Alias of first WSO that has one! -->
		<xsl:variable name="participantId" select="$incomingWSO[string-length(@xpdExt:Alias) > 0][1]/@xpdExt:Alias"/>
		
		<!--  Copy the process -->
		<xsl:copy>
			<xsl:apply-templates select="@*"/>

			<xsl:if test="$participantId != '' and string-length(@xpdExt:ApiEndPointParticipant) = 0">

				<xsl:attribute name="xpdExt:ApiEndPointParticipant"><xsl:value-of select="$participantId"></xsl:value-of></xsl:attribute>

			</xsl:if>

			<xsl:apply-templates select="*"/>
		
		</xsl:copy>
		
	</xsl:template>
	
			
	<!--
	===============================================================================
	Update the format version.
	===============================================================================
    -->
	<xsl:template match="xpdl2:ExtendedAttribute[@Name = 'FormatVersion']">
		<xsl:element name="xpdl2:ExtendedAttribute">
			<xsl:attribute name="Name">FormatVersion</xsl:attribute>
			<xsl:attribute name="Value">15</xsl:attribute>
		</xsl:element>
	</xsl:template>
	
	<!--
	===============================================================================
	Update the UML namespace to latest for ExternalReference.
	===============================================================================
    -->
	<xsl:template match="xpdl2:DataType//xpdl2:ExternalReference[@namespace = 'http://www.eclipse.org/uml2/2.1.0/UML']">
		<xsl:element name="xpdl2:ExternalReference">			
			<xsl:attribute name="location"><xsl:value-of select="@location"></xsl:value-of></xsl:attribute>
			<xsl:attribute name="namespace">http://www.eclipse.org/uml2/3.0.0/UML</xsl:attribute>
			<xsl:attribute name="xref"><xsl:value-of select="@xref"></xsl:value-of></xsl:attribute>
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
