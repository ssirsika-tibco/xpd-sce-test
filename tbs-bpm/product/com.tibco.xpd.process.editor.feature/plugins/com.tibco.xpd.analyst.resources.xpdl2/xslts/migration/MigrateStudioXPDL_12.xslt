<?xml version="1.0" encoding="UTF-8"?>
<!--
	===================================================================================================================
	XSLT:       MigrateStudioXPDL_12.xslt
	
	DESCRIPTION:

  	    This xslt will updgrade a xpdl of format version 12 (3.5.5 GA) to format version 13 (v3.5.10).

		- Presets existing processes to use the process xpdExt:FlowRoutingStyle="UncenteredOnTasks" so that their flow routing remains the same as older versions 
			of Business Studio.
		- If there is a cancelling timer event attached to an activity boundary then it will be automatically configured as the activity deadline, 
		   otherwise the first (non-cancelling) timer event will be set as the activity deadline.
		- Set the "No Interface data association required option" for throw signal events.
		- Set the default EventHandlerFlowStrategy for any event handler 
		- Set the re-offer strategy for user tasks (re-offer on close = false, reoffer on cancel = true) to preserve existing behaviour).

  	     (See XpdlMigrate.java for format version <-> Studio version equivalence).
  	     
  	     XPD-4231: v3.5.10 HF1 Patch ADDITIONAL: 
  	       - Set xpdl:Package/xpdExt:bxUseUnqualifiedPropertyNames=true attribute to indicate to Xpdl2Bpel converter that when generating 
  	         PropertyName's for the PropertyAlias's (added to BPEL copy of WSDL) for correlation mappings it should not qualify the PropertyName 
  	         (derived from correlation data name) with the process name. DEFAULT is FALSE when not present (i.e. new packages will qualify 
  	         PropertyName with process name). Only Migrated XPDL from pre-FormatVersion=13 should have this set.
  	       

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
	XPD-4231
	Set xpdl:Package/xpdExt:bxUseUnqualifiedPropertyNames=true attribute to indicate to Xpdl2Bpel converter that when generating 
  	PropertyName's for the PropertyAlias's (added to BPEL copy of WSDL) for correlation mappings it should not qualify the PropertyName 
  	(derived from correlation data name) with the process name. DEFAULT is FALSE when not present (i.e. new packages will qualify 
  	PropertyName with process name). Only Migrated XPDL from pre-FormatVersion=13 should have this set.	
  	
  	The addition of the xpdExt:BxUseUnqualifiedPropertyNames on migration will only be made if the XPDL file actually 
  	contains correlation data mappings. This is because we do not want to add this attribute to existing new package/process fragments. 
  	Currently some of these may be at pre-FormatVersion=13. However, when these are migrated we would not want them to have 
  	this attribute set (otherwise all new process packages would have it set and that is undesirable).
	
	There is only one fragment ("event handler") that actually has correlation data mappings. Therefore, if we do not add the 
	attribute unless there are correlation data mappings then existing fragments will not have the attribute added. 
	
	It is ok to only add attribute to pre-v3.5.10 packages iof they have correlation data mappings as the PropertName/Alias 
	is only added when there are correlation mappings.
  	===============================================================================
	-->
	<xsl:template match="xpdl2:Package">
		<xsl:copy>
			<xsl:if test="count(//xpdExt:CorrelationDataMappings/xpdExt:DataMapping) > 0">
				<xsl:attribute name="xpdExt:BxUseUnqualifiedPropertyNames">true</xsl:attribute>
			</xsl:if>
			
			<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>


	<!--
	===============================================================================
	Add the xpdExt:FlowRoutingStyle="UncenteredOnTasks" to processes that do not have it set.
	===============================================================================
    -->
    <xsl:template match="xpdl2:WorkflowProcess">
		<xsl:copy>
			<xsl:if test="count(@xpdExt:FlowRoutingStyle) = 0">
				<xsl:attribute name="xpdExt:FlowRoutingStyle">UncenteredOnTasks</xsl:attribute>
			</xsl:if>
			<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
    </xsl:template>

<!--
	===============================================================================
	Deal with activities...
	===============================================================================
    -->
    <xsl:template match="xpdl2:Activity">
    	<xsl:variable name="actId" select="@Id" />
    	
		<xsl:copy>
			<!-- 
				Add the xpdExt:ActivityDeadlineEventId to an activity that has a timer event attached to its boundary.
				============================================================================
				If there is a cancelling timer event then set it (the first one) to be the activity deadline, 
					otherwise select the first (non-cancelling) timer event to be the activity deadline 
			-->
			<xsl:variable name="allTimerEvents" select="../xpdl2:Activity[xpdl2:Event/xpdl2:IntermediateEvent/@Target = $actId and xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerTimer]"/>
			
			<xsl:variable name="cancellingEvents" select="$allTimerEvents[not(xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerTimer/@xpdExt:ContinueOnTimeout='true')]"/>
			

			<xsl:choose>
			  	<xsl:when test="count($cancellingEvents)>0">
			    	<xsl:attribute name="xpdExt:ActivityDeadlineEventId"><xsl:value-of select="$cancellingEvents[1]/@Id"/></xsl:attribute>
			  	</xsl:when>
			  	<xsl:when test="count($allTimerEvents)>0">
			    	<xsl:attribute name="xpdExt:ActivityDeadlineEventId"><xsl:value-of select="$allTimerEvents[1]/@Id"/></xsl:attribute>
			  	</xsl:when>
			</xsl:choose>

			<!--
				Copy/work on the other attributes and elements.
				============================================================================
			-->
			<xsl:apply-templates select="@* | node()"/>

			<!-- 
				Set the "No Interface data association required option" for throw signal events.
				============================================================================
				Pre FormatVersion 13 it was not possible in UI to add associated data to throw signal, but just in case we
				will only add the flag if the associated parameters are not defined.
			-->
			<xsl:if test="xpdl2:Event/*/xpdl2:TriggerResultSignal/@CatchThrow='THROW'">
				<xsl:if test="count(xpdExt:AssociatedParameters/xpdExt:AssociatedParameter) = 0">
				
					<xpdExt:AssociatedParameters DisableImplicitAssociation="true"/>

				</xsl:if>
			</xsl:if>
			
		</xsl:copy>
    </xsl:template>

	<!--
	===============================================================================
	Set the default EventHandlerFlowStrategy for any catch message event handler activity 
		(these aren't supported in previous version, but may have been modelled that way).
	===============================================================================
    -->
	<xsl:template match="xpdl2:TriggerResultMessage">
		<xsl:variable name="act" select="ancestor::xpdl2:Activity"/>
		<xsl:variable name="actId" select="$act/@Id"/>
		<xsl:variable name="incomingTrans" select="$act/../../xpdl2:Transitions/xpdl2:Transition[@To = $actId]"/>
		
		<xsl:choose>
			<xsl:when test="@CatchThrow != 'THROW' and count($incomingTrans) = 0 and $act/xpdl2:Event/xpdl2:IntermediateEvent">
				<!-- 
					this is an intermediate catch message event with no incoming flow, then it is an event handler. 
				-->
				<xsl:copy>
					<xsl:attribute name="xpdExt:EventHandlerFlowStrategy">SerializeConcurrent</xsl:attribute>
					<xsl:apply-templates select="@* | node()"/>
				</xsl:copy>
				
			</xsl:when>
			<xsl:otherwise>
				<!-- 
					Just a normal message event. 
				-->
				<xsl:copy>
					<xsl:apply-templates select="@* | node()"/>
				</xsl:copy>
				
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!--
	===========================================================================================================
	Set the default transport for all process api activities to service virtualisation in case of abstract operations (both generated and user defined wsdls)
	(this was soap/http or soap/jms in the previous versions).
	===========================================================================================================
    -->
	<xsl:template match="@xpdExt:Transport">
	
		<!--all start events are api -->
		<xsl:variable name="isStart" select="count(ancestor::xpdl2:StartEvent) > 0" />
		<!-- all intermediate catch events are api -->
		<xsl:variable name="isIntermediateCatch" select="count(ancestor::xpdl2:IntermediateEvent) > 0 and 
																							ancestor::xpdl2:TriggerResultMessage/@CatchThrow != 'THROW'" />
		<!--	all reply events are api -->
		<xsl:variable name="isReply" select="string-length(ancestor::xpdl2:TriggerResultMessage/@xpdExt:ReplyToActivityId) != 0 or 
																	string-length(ancestor::xpdl2:TaskSend/@xpdExt:ReplyToActivityId) != 0 " />
		<!-- all receive tasks are api -->
		<xsl:variable name="isReceive" select="count(ancestor::xpdl2:TaskReceive) > 0" />
		<!-- select all abstract operations -->
		<xsl:variable name="isAbstract" select="string-length(../xpdl2:Service/@ServiceName) = 0" />

		<xsl:choose>
			<xsl:when test=" $isAbstract  and ($isStart or $isIntermediateCatch or $isReceive or $isReply)">				
				<xsl:attribute name="xpdExt:Transport">http://www.tibco.com/service_virtualisation</xsl:attribute> 
			</xsl:when>
			<xsl:otherwise>				
				<xsl:copy-of select="." />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>


	<!--
	===============================================================================
	Explicitly add the new ReOfferOnClose / Cancel flags = false to existing user task xpdExt:AllocationStrategy
		 with offer-to-all or offer-to-one allocation strategy.
	===============================================================================
    -->
	<xsl:template match="xpdExt:AllocationStrategy[count(ancestor::xpdl2:Activity/xpdl2:Implementation/xpdl2:Task/xpdl2:TaskUser) > 0 and
																				(@xpdExt:Offer = 'OfferOne' or @xpdExt:Offer = 'OfferAll')]"> 
		<xpdExt:AllocationStrategy xpdExt:ReOfferOnClose="false" xpdExt:ReOfferOnCancel="true">
			<xsl:apply-templates select="@* | node()"/>
		</xpdExt:AllocationStrategy>
	</xsl:template>

	<!--
	===============================================================================
	Update the format version.
	===============================================================================
    -->
	<xsl:template match="xpdl2:ExtendedAttribute[@Name = 'FormatVersion']">
		<xsl:element name="xpdl2:ExtendedAttribute">
			<xsl:attribute name="Name">FormatVersion</xsl:attribute>
			<xsl:attribute name="Value">13</xsl:attribute>
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
