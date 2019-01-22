<?xml version="1.0" encoding="UTF-8"?>
<!--
	===================================================================================================================
	XSLT:       iProcessAfterMigrateToV8.xslt
	
	DESCRIPTION:

		This xslt is injected into the Studio Migration xslt sequence at the point that the Studio XPDL has just been converted to Studio FormatVersion 8 
			(that is, just after MigrateStudioXPDL_7.xslt has executed).

		It's purpose is to pickup extra ExtendedAttribtue that have been added to the XPDL by IPM_2_IPS_xpdl.xslt during an import.
		AND anything else that is easier/safer to do after initial import.

	===================================================================================================================
-->
<xsl:stylesheet version="1.0" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:xpdExt="http://www.tibco.com/XPD/xpdExtension1.0.0" 
	xmlns:iProcessExt="http://www.tibco.com/XPD/iProcessExt1.0.0" 
	xmlns:xpdl2="http://www.wfmc.org/2008/XPDL2.1" 
	xmlns:database="http://www.tibco.com/XPD/database1.0.0" 

	xmlns:java="http://xml.apache.org/xslt/java" 
	
	exclude-result-prefixes="java"
>
<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>

<!--
===============================================================================
XSLT processing start point (process whole document)
===============================================================================
-->
<xsl:template match="/">
	<xsl:choose>
		<!-- IPM_2_IPS_xpdl.xslt Should have left us a hint that this xpdl is migrating after import from iprocess xpdl, only do anything if that's the case. -->
		<xsl:when test="/xpdl2:Package/iProcessExt:MigratedFromiProcess">
			<!-- Run all templates normally ONLY if this is a migrate after import from iProcess XPDL. -->
			<xsl:apply-templates/>
		</xsl:when>
		<xsl:otherwise>
			<!-- Otherwise just copy everything as-is (so that we don't add iprocess stuff unnecessarily). -->
			<xsl:copy-of select="/xpdl2:Package"/>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template match="iProcessExt:MigratedFromiProcess">
	<!-- Ignore this temporary tag element (which doesn't really exist in iProcessExt schema -->
</xsl:template>

<!--
===============================================================================
Add FIXME annotations for activities that have iProcessV8_FixMe extrension attribute left by iProcess Import.
===============================================================================
-->
<xsl:template match="xpdl2:Artifacts">
	<xsl:copy>
		<xsl:apply-templates select="@* | node()"/>
		
		<xsl:call-template name="OutputFixMeAnnotations"/>

		<xsl:call-template name="OutputConversionInfoAnnotations"/>
		
	</xsl:copy>

</xsl:template>

<!--
===============================================================================
Add associations between the compensation events and eai withdraw action tasks that we will
  create for any iProcess EAI task that had EAI withdraw action defined.

And add associations to FIXME annotations
===============================================================================
-->
<xsl:template match="xpdl2:Associations">
	<xsl:copy>
		<xsl:apply-templates select="@* | node()"/>
		
		<xsl:call-template name="OutputEAIWithdrawAssociations"/>
		
		<xsl:call-template name="OutputFixMeAssociations"/>

		<xsl:call-template name="OutputConversionInfoAssociations"/>
		
	</xsl:copy>

</xsl:template>

<xsl:template match="xpdl2:WorkflowProcesses">
	<!-- 
		We may need to add EAI withdraw associations but have no idea whether the assocaitions element already exists, so how's this for a trick?
		- If we get as far as Package/WorkflowProcesses (which there must be of there are any EAI withdraw compensation associaitons anyhow)
			Then we will add associations if necessary.
		- Otherwise if there are package/associations then just llet standard template match.

		It is safe to do this because xpdl2:Associations should appear JUST before xpdl2:WorkflowProcesses.
	-->
	<xsl:variable name="fixMes" select="xpdl2:WorkflowProcess//xpdl2:Activity[xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'iProcessV8_FixMe']]"/>
	<xsl:variable name="conversionInfo" select="xpdl2:WorkflowProcess//xpdl2:Activity[xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'iProcessV8_ConversionInfo']]"/>

	<xsl:if test="not(/xpdl2:Package/xpdl2:Associations)">
	
		<xsl:variable name="taskWithdraws" select="xpdl2:WorkflowProcess//xpdl2:Activity[xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'iProcessV8_EAIWithdrawData']]"/>
		
		<xsl:if test="$taskWithdraws or $fixMes or $conversionInfo">
			<xpdl2:Associations>
				<xsl:call-template name="OutputEAIWithdrawAssociations"/>
				<xsl:call-template name="OutputFixMeAssociations"/>
				<xsl:call-template name="OutputConversionInfoAssociations"/>
			</xpdl2:Associations>
		</xsl:if>

	</xsl:if>
	
	<!--
		And then the same for Artifacts (to add ixMe annotations).
	-->
	<xsl:if test="not(/xpdl2:Package/xpdl2:Artifacts)">
		<xsl:if test="$fixMes or $conversionInfo">
			<xpdl2:Artifacts>
				<xsl:call-template name="OutputFixMeAnnotations"/>		
				<xsl:call-template name="OutputConversionInfoAnnotations"/>
			</xpdl2:Artifacts>
		</xsl:if>
	</xsl:if>
	
	<!-- Then apply templates to the rest -->
	<xsl:copy>
		<xsl:apply-templates select="@* | node()"/>
	</xsl:copy>
	
</xsl:template>	

<!--XPD-6514 Set Flow Routing Style for the Process.
This is done here to apply this to only the processes from IPM import.
Migration-12 by default sets the Flow Routing Style to UncenteredOnTasks, hence for IPM Import it should be set here. -->

<xsl:template match="xpdl2:WorkflowProcess">
	<xsl:copy>
		<xsl:attribute name="xpdExt:FlowRoutingStyle">MultiEntryExit</xsl:attribute>
		<xsl:apply-templates select="@* | node()"/>
	</xsl:copy>

</xsl:template>


<!--
===============================================================================
Output compensation associations between compensation event and service task we should
  have created for iProcess EAI tasks with withdraw action defined.
===============================================================================
-->
<xsl:template name="OutputEAIWithdrawAssociations">

	<xsl:for-each select="/xpdl2:Package/xpdl2:WorkflowProcesses/xpdl2:WorkflowProcess">

		<xsl:variable name="taskWithdraws" select="descendant::xpdl2:Activity[xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'iProcessV8_EAIWithdrawData']]"/>
		
		<xsl:if test="$taskWithdraws">
			
			<xsl:for-each select="$taskWithdraws">
				<xsl:variable name="act" select="."/>
			
				<xpdl2:Association Id="{$act/@Id}_EAIWithdrawAssociation" Name="" AssociationDirection="To" Source="{$act/@Id}_EAIWithdrawCompensation" Target="{$act/@Id}_EAIWithdrawTask">
					<xpdl2:Object Id="{$act/@Id}_EAIWithdrawAssociationObject"/>
					<xpdl2:ConnectorGraphicsInfos>
						<xpdl2:ConnectorGraphicsInfo BorderColor="0,0,128" ToolId="XPD.ConnectionInfo"/>
					</xpdl2:ConnectorGraphicsInfos>
				</xpdl2:Association>				
			</xsl:for-each>
		</xsl:if>
		
	</xsl:for-each>
	
</xsl:template>

<!--
===============================================================================
Output associations to any annotaiotn we will have created for Activities that IPM Import  left an 
iProcessV8_FixMe extended attribute on.
===============================================================================
-->
<xsl:template name="OutputFixMeAssociations">
	<xsl:for-each select="/xpdl2:Package/xpdl2:WorkflowProcesses/xpdl2:WorkflowProcess">

		<xsl:for-each select="descendant::xpdl2:Activity">
			<xsl:variable name="act" select="."/>

			<xsl:variable name="fixMe" select="xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'iProcessV8_FixMe']/text()"/>

			<xsl:if test="$fixMe != ''">
				<xpdl2:Association Id="{$act/@Id}_FixMeAssociation" Name="" AssociationDirection="To" Source="{$act/@Id}_FixMeAnnotation" Target="{$act/@Id}">
					<xpdl2:Object Id="{$act/@Id}_FixMeAssociationObject"/>
					<xpdl2:ConnectorGraphicsInfos>
						<xpdl2:ConnectorGraphicsInfo BorderColor="0,0,128" ToolId="XPD.ConnectionInfo"/>
					</xpdl2:ConnectorGraphicsInfos>
				</xpdl2:Association>				
			</xsl:if>
			
		</xsl:for-each>
	</xsl:for-each>
	
</xsl:template>

<!--
===============================================================================
Output annotaiton artifacts for Activities that IPM Import  left an iProcessV8_FixMe extended attribute on.
===============================================================================
-->
<xsl:template name="OutputFixMeAnnotations">
	<xsl:for-each select="/xpdl2:Package/xpdl2:WorkflowProcesses/xpdl2:WorkflowProcess">

		<xsl:for-each select="descendant::xpdl2:Activity">
			<xsl:variable name="act" select="."/>
			<xsl:variable name="fixMe" select="xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'iProcessV8_FixMe']/text()"/>

			<xsl:if test="$fixMe != ''">
	
				<xpdl2:Artifact Id="{$act/@Id}_FixMeAnnotation" ArtifactType="Annotation" TextAnnotation="{$fixMe}">
					<xpdl2:NodeGraphicsInfos>
						<xsl:variable name="line1">
							<xsl:choose>
								<xsl:when test="contains($fixMe, '&#10;')"><xsl:value-of select="substring-before($fixMe, '&#10;')"/></xsl:when>
								<xsl:otherwise><xsl:value-of select="$fixMe"/></xsl:otherwise>
							</xsl:choose>
						</xsl:variable>
					
						<xpdl2:NodeGraphicsInfo BorderColor="0,0,128" Height="10.0" LaneId="{$act/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo/@LaneId}" >

							<xsl:variable name="taskX" select="$act/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo/xpdl2:Coordinates/@XCoordinate"/>
							<xsl:variable name="taskY" select="$act/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo/xpdl2:Coordinates/@YCoordinate"/>
							<xsl:variable name="width" select="$act/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo/@Width"/>
							<xsl:variable name="height" select="$act/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo/@Height"/>
						
							<!-- XPD-6650: Noticed that FIXME annotations sometimes get put out of reach above top of diagram - so make sure they're in range. -->
							<xsl:variable name="prefY" select="$taskY - ($height div 2) - 50"/>
							<xsl:variable name="actualY">
								<xsl:choose>
									<xsl:when test="$prefY > '10'"><xsl:value-of select="$prefY"/></xsl:when>
									<xsl:otherwise>10</xsl:otherwise>
								</xsl:choose>
							</xsl:variable>
							<xpdl2:Coordinates XCoordinate="{$taskX - ($width div 2)}" YCoordinate="{$actualY}"/>
							
						</xpdl2:NodeGraphicsInfo>
					</xpdl2:NodeGraphicsInfos>
				</xpdl2:Artifact>

			</xsl:if>
			
		</xsl:for-each>
	</xsl:for-each>
</xsl:template>

<!--
===============================================================================
Output associations to any annotation we will have created for Activities IPM Import  left an 
iProcessV8_ConversionInfo extended attribute on.
===============================================================================
-->
<xsl:template name="OutputConversionInfoAssociations">
	<xsl:for-each select="/xpdl2:Package/xpdl2:WorkflowProcesses/xpdl2:WorkflowProcess">

		<xsl:for-each select="descendant::xpdl2:Activity">
			<xsl:variable name="act" select="."/>

			<xsl:variable name="infoText" select="xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'iProcessV8_ConversionInfo']/text()"/>

			<xsl:if test="$infoText != ''">
				<xpdl2:Association Id="{$act/@Id}_CnvInfoAssociation" Name="" AssociationDirection="To" Source="{$act/@Id}_CnvInfoAnnotation" Target="{$act/@Id}">
					<xpdl2:Object Id="{$act/@Id}_CnvInfoAssociationObject"/>
					<xpdl2:ConnectorGraphicsInfos>
						<xpdl2:ConnectorGraphicsInfo BorderColor="237,117,2" ToolId="XPD.ConnectionInfo"/>
					</xpdl2:ConnectorGraphicsInfos>
				</xpdl2:Association>				
			</xsl:if>
			
		</xsl:for-each>
	</xsl:for-each>
	
</xsl:template>

<!--
===============================================================================
Output annotation artifacts for Activities that IPM Import  left an 
iProcessV8_ConversionInfo extended attribute on.
===============================================================================
-->
<xsl:template name="OutputConversionInfoAnnotations">
	<xsl:for-each select="/xpdl2:Package/xpdl2:WorkflowProcesses/xpdl2:WorkflowProcess">

		<xsl:for-each select="descendant::xpdl2:Activity">
			<xsl:variable name="act" select="."/>
			<xsl:variable name="infoText" select="xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'iProcessV8_ConversionInfo']/text()"/>

			<xsl:if test="$infoText != ''">
	
				<xpdl2:Artifact Id="{$act/@Id}_CnvInfoAnnotation" ArtifactType="Annotation" TextAnnotation="{$infoText}">
					<xpdl2:NodeGraphicsInfos>
					
						<xpdl2:NodeGraphicsInfo BorderColor="237,117,2" Height="10.0" LaneId="{$act/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo/@LaneId}" >

							<xsl:variable name="taskX" select="$act/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo/xpdl2:Coordinates/@XCoordinate"/>
							<xsl:variable name="taskY" select="$act/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo/xpdl2:Coordinates/@YCoordinate"/>
							<xsl:variable name="width" select="$act/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo/@Width"/>
							<xsl:variable name="height" select="$act/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo/@Height"/>
						
							<xpdl2:Coordinates XCoordinate="{$taskX - ($width div 2)}" YCoordinate="{$taskY - ($height div 2) - 50}"/>
							
						</xpdl2:NodeGraphicsInfo>
					</xpdl2:NodeGraphicsInfos>
				</xpdl2:Artifact>

			</xsl:if>
			
		</xsl:for-each>
	</xsl:for-each>
</xsl:template>

<!--
===============================================================================
Handle xpd2:Activities updates
===============================================================================
-->
<xsl:template match="xpdl2:Activities">
	<xsl:copy>
		<xsl:apply-templates select="@* | node()"/>

		<xsl:call-template name="OutputAttachedEventEndEvents"/>
		
		<xsl:call-template name="OutputEAIWithdrawCompensations"/>
	</xsl:copy>
	
</xsl:template>

<!--
===============================================================================
Add End Event activities for task-attached events that do not have outgoing flow (not necessary for
iProcess valdiation but clears up BPMN ones so less confusing for the user).

Context: xpdl2:Activities  (within WorkflowProces or (potentially) ActivitySet)
===============================================================================
-->
<xsl:template name="OutputAttachedEventEndEvents">
		<!-- 
			Currently we import iProcess withdraw links as Throw/Catch signal pairs (IPM_2_IPS creates in-line error events and some 
			extendedAttributes on the catchers, Migrate_2 creates attached events from these extended attributes and a later migrate xslt 
			converts them to signals (as they should be in BPMN 2.0 
			However, no outgoing flow from catch event to and end event is created so the attached signals get BPMN errors taht whilst not
			specifically a problem for export, makes it look like there is a problem to the user.
	
			So add an end event for _any_ event catchers (there may be deadline events with no outgoing flow also) that do not have outgoing flow.
		-->
		
		<xsl:variable name="attachedEvents" select="xpdl2:Activity[xpdl2:Event/xpdl2:IntermediateEvent/@Target != '']"/>

		<xsl:if test="$attachedEvents">
			<xsl:variable name="transitions" select="../xpdl2:Transitions/xpdl2:Transition"/>

			<xsl:for-each select="$attachedEvents">
				<xsl:variable name="eventId" select="@Id"/>

				<xsl:if test="not($transitions[@From = $eventId])">
					<!-- attached event with no outgoing flow. Create an end event for it. -->
					<xsl:variable name="targetTaskId" select="xpdl2:Event/xpdl2:IntermediateEvent/@Target"/>
					<xsl:variable name="targetTask" select="../xpdl2:Activity[@Id = $targetTaskId]"/>
					
					<xsl:if test="$targetTask">

						<xpdl2:Activity Id="{@Id}_EndEvent" Name="" xpdExt:Visibility="Private" xpdExt:DisplayName="">
							<xpdl2:Event>
								<xpdl2:EndEvent Result="None"/>
							</xpdl2:Event>
							<xpdl2:NodeGraphicsInfos>
								<xpdl2:NodeGraphicsInfo BorderColor="0,0,128" FillColor="255,219,74" Height="27.0" Width="27.0">
									<xsl:attribute name="LaneId"><xsl:value-of select="$targetTask/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo/@LaneId"/></xsl:attribute>
									
									<xsl:variable name="taskX" select="$targetTask/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo/xpdl2:Coordinates/@XCoordinate"/>
									<xsl:variable name="taskY" select="$targetTask/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo/xpdl2:Coordinates/@YCoordinate"/>
									<xsl:variable name="width" select="$targetTask/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo/@Width"/>
									<xsl:variable name="height" select="$targetTask/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo/@Height"/>
									
									<xsl:variable name="xFudge">
										<xsl:choose>
											<xsl:when test="xpdl2:Event/xpdl2:IntermediateEvent/@Trigger = 'Timer'">-2</xsl:when>
											<xsl:when test="xpdl2:Event/xpdl2:IntermediateEvent/@Trigger = 'Signal'">30</xsl:when>
											<xsl:otherwise>0</xsl:otherwise>
										</xsl:choose>
									</xsl:variable>
									
									<xpdl2:Coordinates>
										<xsl:attribute name="XCoordinate"><xsl:value-of select="$taskX + $xFudge"/></xsl:attribute>
										<xsl:attribute name="YCoordinate"><xsl:value-of select="$taskY + ($height div 2) + 50"/></xsl:attribute>
									</xpdl2:Coordinates>
								</xpdl2:NodeGraphicsInfo>
							</xpdl2:NodeGraphicsInfos>
						</xpdl2:Activity>

					</xsl:if>
				</xsl:if>
				
			</xsl:for-each> <!-- Next attached event -->
		
		</xsl:if>
</xsl:template>

<!--
===============================================================================
Add End Event activities for task-attached events that do not have outgoing flow (not necessary for
iProcess valdiation but clears up BPMN ones so less confusing for the user).

Context: xpdl2:Activities  (within WorkflowProces or (potentially) ActivitySet)
===============================================================================
-->
<xsl:template name="OutputEAIWithdrawCompensations">

	<xsl:variable name="taskWithdraws" select="..//xpdl2:Activity[xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'iProcessV8_EAIWithdrawData']]"/>

	<!--
		Create an attached compensation event and Service Task for each task with a withdraw data defined. 
	-->
	<xsl:for-each select="$taskWithdraws">
		<xsl:variable name="act" select="."/> <!-- The main service task activity. -->
		<xsl:variable name="eaiWithdraw" select="xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'iProcessV8_EAIWithdrawData']"/>

		<!-- 
			Create the compensation event 
		-->
        <xpdl2:Activity Id="{$act/@Id}_EAIWithdrawCompensation" Name="" xpdExt:Visibility="Private" xpdExt:DisplayName="">
			<xpdl2:Event>
				<xpdl2:IntermediateEvent Target="{$act/@Id}" Trigger="Compensation">
					<xpdl2:TriggerResultCompensation xpdExt:CatchThrow="CATCH"/>
				</xpdl2:IntermediateEvent>
			</xpdl2:Event>

			<xpdl2:NodeGraphicsInfos>
				<xpdl2:NodeGraphicsInfo BorderColor="0,0,128" FillColor="255,219,74" Height="27.0" LaneId="{$act/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo/@LaneId}" Width="27.0">
					<xpdl2:Coordinates XCoordinate="0.0" YCoordinate="0.0"/>
				</xpdl2:NodeGraphicsInfo>
				<xpdl2:NodeGraphicsInfo ToolId="XPD.BorderEventPosition">
					<xpdl2:Coordinates XCoordinate="20" YCoordinate="0.0"/>
				</xpdl2:NodeGraphicsInfo>
			</xpdl2:NodeGraphicsInfos>
		</xpdl2:Activity>

		<!-- 
			Create the withdraw action service task 
		-->
		<xpdl2:Activity Id="{$act/@Id}_EAIWithdrawTask" Name="WithdrawCompensation_{$act/@Name}" IsATransaction="false" IsForCompensation="true" 
															xpdExt:Visibility="Private" xpdExt:RequireNewTransaction="false" xpdExt:DisplayName="Withdraw Compensation ({$act/@xpdExt:DisplayName})">
			<xpdl2:Implementation>
				<xpdl2:Task>

					<!-- Create the task service part (which will be different for different EAI types that can have withdraw actions. -->
					<xsl:choose>
						<xsl:when test="$eaiWithdraw/xpdl2:DatabaseWithdraw">
							<!--
								Database Task
							-->
							<xpdl2:TaskService xpdExt:ImplementationType="Database" Implementation="Other">
								<xpdl2:MessageIn Id="{$act/@Id}_EAIWithdrawTask_msgIn"/>
								<xpdl2:MessageOut Id="{$act/@Id}_EAIWithdrawTask_msgOut"/>
								<database:Database>
									<database:Operation database:Type="StoredProc">
										<database:Sql><xsl:value-of select="$eaiWithdraw/xpdl2:DatabaseWithdraw/xpdl2:StoredProcedure"/></database:Sql>
									</database:Operation>
								</database:Database>
							</xpdl2:TaskService>
							
						</xsl:when>

						<xsl:when test="$eaiWithdraw/xpdl2:WebServiceWithdraw">
							<!--
								Web service task.
							-->
							<xsl:call-template name="CopyAndConvertPrefix">
								<xsl:with-param name="input" select="$eaiWithdraw/xpdl2:WebServiceWithdraw/xpdl2:xpdl2_TaskService"/>
							</xsl:call-template>
							
						</xsl:when>

					</xsl:choose>
					
				</xpdl2:Task>
			</xpdl2:Implementation>
			
			<!-- 
				Set the task position relative to the host task. 
			-->
			<xpdl2:NodeGraphicsInfos>
				<xpdl2:NodeGraphicsInfo BorderColor="0,0,128" FillColor="255,219,74" Height="64.0" LaneId="{$act/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo/@LaneId}" Width="96.0">
					<xsl:variable name="taskX" select="$act/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo/xpdl2:Coordinates/@XCoordinate"/>
					<xsl:variable name="taskY" select="$act/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo/xpdl2:Coordinates/@YCoordinate"/>
					<xsl:variable name="width" select="$act/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo/@Width"/>
					<xsl:variable name="height" select="$act/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo/@Height"/>
					
					<xpdl2:Coordinates>
						<xsl:attribute name="XCoordinate"><xsl:value-of select="$taskX + ($width div 2) +35"/></xsl:attribute>
						<xsl:attribute name="YCoordinate"><xsl:value-of select="$taskY + ($height div 2) + 70"/></xsl:attribute>
					</xpdl2:Coordinates>
				</xpdl2:NodeGraphicsInfo>
			</xpdl2:NodeGraphicsInfos>
			
			<xpdl2:Extensions/>
			
			<xpdExt:ActivityResourcePatterns>
				<xpdExt:AllocationStrategy xpdExt:Strategy="SYSTEM_DETERMINED"/>
			</xpdExt:ActivityResourcePatterns>

			<iProcessExt:TaskProperties NoDeleteOnWithdraw="false" IgnoreCaseSuspend="false">
				<iProcessExt:Prediction UseDeadlineForDuration="false" DontIncludeAsFutureWorkItem="false"/>
				
				<!-- Set any extra task type spoecific properties -->
				<xsl:choose>
					<xsl:when test="$eaiWithdraw/xpdl2:DatabaseWithdraw">
						<iProcessExt:DatabaseTask Server="{$eaiWithdraw/xpdl2:DatabaseWithdraw/xpdl2:Server}" Schema="{$eaiWithdraw/xpdl2:DatabaseWithdraw/xpdl2:Database}" DelayedReleaseType="Never"/>
					</xsl:when>

					<xsl:when test="$eaiWithdraw/xpdl2:WebServiceWithdraw">
						<iProcessExt:WebServiceTask InvocationStyle="AutoDelayedRelease"/>
					</xsl:when>
				</xsl:choose>
				
			</iProcessExt:TaskProperties>

		</xpdl2:Activity>
		
	</xsl:for-each>

</xsl:template>

	
<!--
===============================================================================
Handle activity updates
===============================================================================
-->
<xsl:template match="xpdl2:Activity">
	<xsl:variable name="act" select="."/>
	
	<!-- Copy and apply templates (copy children) by default. -->
	<xsl:copy>
		<xsl:call-template name="AddActivityXpdExtAttributes"/>
		
		<xsl:apply-templates select="@* | node()"/>

		<!-- Add extra task properties if necessary (if iProcessExt:TaskProperties already exists it is handled in matching template below. -->
		<xsl:if test="not(iProcessExt:TaskProperties)">
			<iProcessExt:TaskProperties>
				<xsl:call-template name="OutputAdditionalTaskProperties">
					<xsl:with-param name="activity" select="."/>
				</xsl:call-template>
			</iProcessExt:TaskProperties>
		</xsl:if>
	</xsl:copy>
	
	<!-- 
		If this is an XOR split with multiple condition / default outgoing then this is not legal BPMN. So we need to insert a parallel split for the default flow. 
	-->
	<xsl:if test="xpdl2:Route/@GatewayType = 'Exclusive'">
		<xsl:variable name="defaultOutTransitions" select="ancestor::xpdl2:WorkflowProcess/xpdl2:Transitions/xpdl2:Transition[@From = $act/@Id and xpdl2:Condition/@Type = 'OTHERWISE']"/>
	
		<xsl:if test="count($defaultOutTransitions) > 1">
			<!-- Create an extra parallel split for the multiple default outputs. -->
			<xsl:call-template name="OutputAdditionalParallelSplit">
				<xsl:with-param name="act" select="$act"/>
				<xsl:with-param name="relativeToAct" select="$act"/>
				<xsl:with-param name="below" select="true()"/>
				<xsl:with-param name="transitions" select="$defaultOutTransitions"/>
			</xsl:call-template>
		</xsl:if>
		
		<xsl:variable name="conditionTransitions" select="ancestor::xpdl2:WorkflowProcess/xpdl2:Transitions/xpdl2:Transition[@From = $act/@Id and xpdl2:Condition/@Type = 'CONDITION']"/>

		<xsl:if test="count($conditionTransitions) > 1">
			<!-- Create an extra parallel split for the multiple condition outputs. This is required because for export iProcess Studio XPDL XOR gateway can only have one conditional out.
				It is ok to combine the condition transitions into one because when imported from iProcess XPDL there can onyl be ONE condition per conditional split (iprocess defines 
				condition on the gateway no the flow) -->
			<xsl:call-template name="OutputAdditionalParallelSplit">
				<xsl:with-param name="act" select="$act"/>
				<xsl:with-param name="relativeToAct" select="$act"/>
				<xsl:with-param name="below" select="false()"/>
				<xsl:with-param name="transitions" select="$conditionTransitions"/>
			</xsl:call-template>
		</xsl:if>
	</xsl:if>
	
	<!-- 
		If this is a throw signal event inserted in place of a withdraw link and it has multple outgoing then we need to add a split here also.
	-->
	<xsl:if test="xpdl2:Event/xpdl2:IntermediateEvent[@Trigger = 'Signal' and xpdl2:TriggerResultSignal/@CatchThrow='THROW']">
		<xsl:variable name="outTransitions" select="ancestor::xpdl2:WorkflowProcess/xpdl2:Transitions/xpdl2:Transition[@From = $act/@Id]"/>
		
		<xsl:if test="count($outTransitions) > 1">
			<!-- Create an extra parallel split for the multiple outputs from throw signal event.  -->
			<xsl:call-template name="OutputAdditionalParallelSplit">
				<xsl:with-param name="act" select="$act"/>
				<xsl:with-param name="relativeToAct" select="$act"/>
				<xsl:with-param name="below" select="false()"/>
				<xsl:with-param name="transitions" select="$outTransitions"/>
			</xsl:call-template>
		</xsl:if>
	</xsl:if>

	<!-- 
		If this is a boundary timer event with muliple outgoing transitions then it will require an additional parallel split to be inserted.
	-->
	<xsl:if test="$act/xpdl2:Event/xpdl2:IntermediateEvent[@Trigger = 'Timer' and @Target != '']">
		<xsl:variable name="outTransitions" select="ancestor::xpdl2:WorkflowProcess/xpdl2:Transitions/xpdl2:Transition[@From = $act/@Id]"/>
		
		<xsl:if test="count($outTransitions) > 1">
			<!-- Create an extra parallel split for the multiple outputs from throw signal event.  -->
			<xsl:call-template name="OutputAdditionalParallelSplit">
				<xsl:with-param name="act" select="$act"/>
				<xsl:with-param name="relativeToAct" select="../xpdl2:Activity[@Id = $act/xpdl2:Event/xpdl2:IntermediateEvent/@Target]"/>
				<xsl:with-param name="below" select="true()"/>
				<xsl:with-param name="transitions" select="$outTransitions"/>
			</xsl:call-template>
		</xsl:if>
	</xsl:if>

</xsl:template>

<!--
===============================================================================
Output additional split for the given activity.
===============================================================================
-->
<xsl:template name="OutputAdditionalParallelSplit">
	<xsl:param name="act"/>
	<xsl:param name="relativeToAct"/>
	<xsl:param name="below" select="false()"/>
	<xsl:param name="transitions"/>

	<xpdl2:Activity Id="{$act/@Id}_AddSplit">
	  <xpdl2:Route GatewayType="Parallel"/>
	  <xpdl2:TransitionRestrictions>
		<xpdl2:TransitionRestriction>
		  <xpdl2:Split Type="Parallel">
			<xpdl2:TransitionRefs>
				<xsl:for-each select="$transitions">
					<xpdl2:TransitionRef Id="{@Id}"/>
				</xsl:for-each>
			</xpdl2:TransitionRefs>
		  </xpdl2:Split>
		</xpdl2:TransitionRestriction>
	  </xpdl2:TransitionRestrictions>
	  <xpdl2:NodeGraphicsInfos>

		<xsl:variable name="gInfo" select="$relativeToAct/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo"/>
		
		<xpdl2:NodeGraphicsInfo BorderColor="0,0,128" FillColor="255,219,74" Height="43.0" LaneId="{$gInfo/@LaneId}" Width="41.0">
			<xsl:variable name="offset">
				<xsl:choose>
					<xsl:when test="$relativeToAct/xpdl2:Event or $relativeToAct/xpdl2:Route">65</xsl:when>
					<xsl:when test="not($below)">85</xsl:when> <!-- right of task -->
					<xsl:otherwise >80</xsl:otherwise> <!-- below task /event attached to task. -->
				</xsl:choose>
			</xsl:variable>
		
			<xsl:choose>
				<xsl:when test="$below">
				  <xpdl2:Coordinates XCoordinate="{$gInfo/xpdl2:Coordinates/@XCoordinate}" YCoordinate="{$gInfo/xpdl2:Coordinates/@YCoordinate + $offset}"/>
				</xsl:when>
				<xsl:otherwise>
				  <xpdl2:Coordinates XCoordinate="{$gInfo/xpdl2:Coordinates/@XCoordinate + $offset}" YCoordinate="{$gInfo/xpdl2:Coordinates/@YCoordinate}"/>
				</xsl:otherwise>
			</xsl:choose>
		</xpdl2:NodeGraphicsInfo>
	  </xpdl2:NodeGraphicsInfos>
	</xpdl2:Activity>
	
</xsl:template>


<!--
===============================================================================
Handle xpdl2:Transitions updates.
===============================================================================
-->
<xsl:template match="xpdl2:Transitions">
	<xsl:copy>
		<xsl:apply-templates select="@* | node()"/>
		
		<xsl:call-template name="OutputAttachedEventEndEventFlows"/>
	</xsl:copy>
</xsl:template>

<!--
===============================================================================
Handle transition
===============================================================================
-->
<xsl:template match="xpdl2:Transition">
	<xsl:variable name="transition" select="."/>

	<xsl:variable name="sourceAct" select="../../xpdl2:Activities/xpdl2:Activity[@Id = $transition/@From]"/>
	
	<xsl:variable name="defaultOutTransitions" select="../xpdl2:Transition[@From = $sourceAct/@Id and xpdl2:Condition/@Type = 'OTHERWISE']"/>
	<xsl:variable name="conditionTransitions" select="../xpdl2:Transition[@From = $sourceAct/@Id and xpdl2:Condition/@Type = 'CONDITION']"/>
	<xsl:variable name="allTransitions" select="../xpdl2:Transition[@From = $sourceAct/@Id]"/>

	<xsl:choose>
			<!-- 
				For multiple default flows from conditional split (which is not leagal in bpmn) we will have added a parallel split activity.
				So we need to redirect the original flows to be sourced from this activity and create a new default flow from the xor split to the parallel split. 
			-->
			<xsl:when test="$transition/xpdl2:Condition/@Type = 'OTHERWISE' and $sourceAct/xpdl2:Route/@GatewayType = 'Exclusive' and count($defaultOutTransitions) > 1">
				<xsl:variable name="addedSplitId"><xsl:value-of select="$sourceAct/@Id"/>_AddSplit</xsl:variable>
				
				<xpdl2:Transition From="{$addedSplitId}">
					<xsl:apply-templates select="@*[local-name() != 'From']"/>
					
					<!-- Copy everything else except the condition=default element. (the new transition between xor gate and additional split will now be the condition default transition -->
					<xsl:apply-templates select="*[not(self::xpdl2:Condition)]"/>
				</xpdl2:Transition>
				
				<!-- Add in the extra transition between the orignal xor split gateway and added split. (ONLY ONCE!!)-->
				<xsl:if test="generate-id($transition) = generate-id($defaultOutTransitions[1])">
					<xpdl2:Transition Id="{$addedSplitId}_Flow" From="{$sourceAct/@Id}" To="{$addedSplitId}">
						<xpdl2:Condition Type="OTHERWISE"/>
						<xpdl2:ConnectorGraphicsInfos>
							<xpdl2:ConnectorGraphicsInfo BorderColor="0,0,128" ToolId="XPD.ConnectionInfo"/>
						</xpdl2:ConnectorGraphicsInfos>
					</xpdl2:Transition>
				</xsl:if>
			</xsl:when>
			
			<!-- 
				For multiple default flows from conditional split (which is not leagal in bpmn) we will have added a parallel split activity.
				So we need to redirect the original flows to be sourced from this activity and create a new default flow from the xor split to the parallel split. 
			-->
			<xsl:when test="$transition/xpdl2:Condition/@Type = 'CONDITION' and $sourceAct/xpdl2:Route/@GatewayType = 'Exclusive' and count($conditionTransitions) > 1">
				<xsl:variable name="addedSplitId"><xsl:value-of select="$sourceAct/@Id"/>_AddSplit</xsl:variable>
				
				<xpdl2:Transition From="{$addedSplitId}">
					<xsl:apply-templates select="@*[local-name() != 'From']"/>
					
					<!-- Copy everything else except the condition element. (the new transition between xor gate and additional split will now be the condition transition -->
					<xsl:apply-templates select="*[not(self::xpdl2:Condition)]"/>
				</xpdl2:Transition>
				
				<!-- Add in the extra transition between the orignal xor split gateway and added split. (ONLY ONCE!!)-->
				<xsl:if test="generate-id($transition) = generate-id($conditionTransitions[1])">
					<xpdl2:Transition Id="{$addedSplitId}_Flow" From="{$sourceAct/@Id}" To="{$addedSplitId}">
						<xsl:copy-of select="$transition/xpdl2:Condition"/>
						<xpdl2:ConnectorGraphicsInfos>
							<xpdl2:ConnectorGraphicsInfo BorderColor="0,0,128" ToolId="XPD.ConnectionInfo"/>
						</xpdl2:ConnectorGraphicsInfos>
					</xpdl2:Transition>
				</xsl:if>
			</xsl:when>
			
			<!-- 
				If source activity is a throw signal event inserted in place of a withdraw link and it has multple outgoing then we added a parallel split here also.
				So we need to redirect the original flow to come from the added split and create a new flow between event and added split.
			-->
			<xsl:when test="$sourceAct/xpdl2:Event/xpdl2:IntermediateEvent[@Trigger = 'Signal' and xpdl2:TriggerResultSignal/@CatchThrow='THROW'] and count($allTransitions) > 1">
				<xsl:variable name="addedSplitId"><xsl:value-of select="$sourceAct/@Id"/>_AddSplit</xsl:variable>
				
				<xpdl2:Transition From="{$addedSplitId}">
					<xsl:apply-templates select="@*[local-name() != 'From']"/>
					<xsl:apply-templates select="*"/>
				</xpdl2:Transition>
				
				<!-- Add in the extra uncontrolled flow between the orignal event and added split. (ONLY ONCE!!)-->
				<xsl:if test="generate-id($transition) = generate-id($allTransitions[1])">
					<xpdl2:Transition Id="{$addedSplitId}_Flow" From="{$sourceAct/@Id}" To="{$addedSplitId}">
						<xpdl2:ConnectorGraphicsInfos>
							<xpdl2:ConnectorGraphicsInfo BorderColor="0,0,128" ToolId="XPD.ConnectionInfo"/>
						</xpdl2:ConnectorGraphicsInfos>
					</xpdl2:Transition>
				</xsl:if>
			</xsl:when>
			
			<!-- 
				If this is a boundary timer event with muliple outgoing transitions then it will require an additional parallel split to be inserted.
			-->
			<xsl:when test="$sourceAct/xpdl2:Event/xpdl2:IntermediateEvent[@Trigger = 'Timer' and @Target != ''] and count($allTransitions) > 1">
				<xsl:variable name="addedSplitId"><xsl:value-of select="$sourceAct/@Id"/>_AddSplit</xsl:variable>
				
				<xpdl2:Transition From="{$addedSplitId}">
					<xsl:apply-templates select="@*[local-name() != 'From']"/>
					<xsl:apply-templates select="*"/>
				</xpdl2:Transition>
				
				<!-- Add in the extra uncontrolled flow between the orignal event and added split. (ONLY ONCE!!)-->
				<xsl:if test="generate-id($transition) = generate-id($allTransitions[1])">
					<xpdl2:Transition Id="{$addedSplitId}_Flow" From="{$sourceAct/@Id}" To="{$addedSplitId}">
						<xpdl2:ConnectorGraphicsInfos>
							<xpdl2:ConnectorGraphicsInfo BorderColor="0,0,128" ToolId="XPD.ConnectionInfo"/>
						</xpdl2:ConnectorGraphicsInfos>
					</xpdl2:Transition>
				</xsl:if>
			
			</xsl:when>
			
			<!--
				Nothing special about this flow, just copy it. 
			-->
			<xsl:otherwise>
				<xsl:copy>
					<xsl:apply-templates select="@* | node()"/>
				</xsl:copy>
			</xsl:otherwise>
	</xsl:choose>

	
</xsl:template>

<!--
===============================================================================
If we created extra end events for task-attached events that had no outgoing flow otherwise, 
then connect them up.

Context: xpdl2:Transitions
===============================================================================
-->
<xsl:template name="OutputAttachedEventEndEventFlows">

	<xsl:variable name="attachedEvents" select="../xpdl2:Activities/xpdl2:Activity[xpdl2:Event/xpdl2:IntermediateEvent/@Target != '']"/>

	<xsl:if test="$attachedEvents">
		<xsl:variable name="transitions" select="../xpdl2:Transitions/xpdl2:Transition"/>

		<xsl:for-each select="$attachedEvents">
			<xsl:variable name="eventId" select="@Id"/>

			<xsl:if test="not($transitions[@From = $eventId])">
				<!-- Found an attached event that does not have outgoing flow. -->
				<xpdl2:Transition Id="{$eventId}_Connect_EndEvent" xpdExt:DisplayName="" Name="" From="{$eventId}" To="{$eventId}_EndEvent">
					<xpdl2:ConnectorGraphicsInfos>
						<xpdl2:ConnectorGraphicsInfo BorderColor="0,0,128" ToolId="XPD.ConnectionInfo"/>
					</xpdl2:ConnectorGraphicsInfos>
				</xpdl2:Transition>
				
			</xsl:if>
		</xsl:for-each>
	</xsl:if>
	
</xsl:template>


<!--
===============================================================================
Add UserTaskScripts to xpdl2:TaskUser
===============================================================================
-->
<xsl:template match="xpdl2:TaskUser">
	<xsl:copy>
		<xsl:apply-templates select="@* | node()"/>
			
		<xsl:variable name="openScript" select="ancestor::xpdl2:Activity/xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'iProcessV8_UserTaskOpenScript']/@Value"/>
		<xsl:variable name="closeScript" select="ancestor::xpdl2:Activity/xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'iProcessV8_UserTaskCloseScript']/@Value"/>
		<xsl:variable name="submitScript" select="ancestor::xpdl2:Activity/xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'iProcessV8_UserTaskSubmitScript']/@Value"/>
		
		<xsl:if test="not(xpdExt:UserTaskScripts) and ($openScript != '' or  $closeScript != '' or $submitScript != '')">
			<xpdExt:UserTaskScripts>
				<xsl:if test="$openScript">
	                  <xpdExt:OpenScript ScriptGrammar="iProcessScript"><xsl:value-of select="$openScript"/></xpdExt:OpenScript>
				</xsl:if>
				<xsl:if test="$closeScript">
	                  <xpdExt:CloseScript ScriptGrammar="iProcessScript"><xsl:value-of select="$closeScript"/></xpdExt:CloseScript>
				</xsl:if>
				<xsl:if test="$submitScript">
	                  <xpdExt:SubmitScript ScriptGrammar="iProcessScript"><xsl:value-of select="$submitScript"/></xpdExt:SubmitScript>
				</xsl:if>
			</xpdExt:UserTaskScripts>
		</xsl:if>
	</xsl:copy>
</xsl:template>

	
<!--
===============================================================================
FillColor in the NodeGraphicsInfo for thigns that need color changed.
===============================================================================
-->
<xsl:template match="xpdl2:NodeGraphicsInfo/@FillColor">
	<xsl:variable name="act" select="ancestor::xpdl2:Activity"/>
	
	<xsl:choose>
		<!-- Start Events of implementing events be different than that of the default. -->
		<xsl:when test="$act/xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name='iProcessV8_ImplementsStart']">
			<xsl:attribute name="FillColor">192,192,192</xsl:attribute>
		</xsl:when>

		<!-- Change FixMe activities to Red -->
		<xsl:when test="$act/xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name='iProcessV8_FixMe']">
			<xsl:attribute name="FillColor">255,0,0</xsl:attribute>
		</xsl:when>
		
		<!-- Keep default -->
		<xsl:otherwise>
			<xsl:copy-of select="."/>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!--
===============================================================================
Add Xpd Extended attributes to Activity

Context: xpdl2:Activity
===============================================================================
-->
<xsl:template name="AddActivityXpdExtAttributes">
	<!-- for every activity that has the extended attribute - iProcessV8_ImplementsStart add the implements tag -->
	<xsl:if test="xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name='iProcessV8_ImplementsStart']">
		<!-- If the extended attribute exists on the start event-->
		<xsl:variable name="procIfcId" select="../../xpdExt:ImplementedInterface/@xpdExt:ProcessInterfaceId"/>
		<xsl:attribute name="xpdExt:Implements"><xsl:value-of select="concat($procIfcId, '_StartEvent')"/></xsl:attribute>
	</xsl:if>
</xsl:template>


<!--
===============================================================================
iProcessExt:TaskProperties and OutputAdditionalTaskProperties - output any additional iProcessExt:TaskProperty

Context: xpdl2:Activity
===============================================================================
-->
<xsl:template match="iProcessExt:TaskProperties">
	<!-- Copy existing task properties then add any additional. -->
	<xsl:copy>
		<xsl:apply-templates select="@* | node()"/>
		
		<xsl:call-template name="OutputAdditionalTaskProperties">
			<xsl:with-param name="activity" select="ancestor::xpdl2:Activity"/>
		</xsl:call-template>
	</xsl:copy>
</xsl:template>

<!--
===============================================================================
iProcessExt:TaskProperties and OutputAdditionalTaskProperties - output any additional iProcessExt:TaskProperty
===============================================================================
-->
<xsl:template name="OutputAdditionalTaskProperties">
	<xsl:param name="activity"/>
	
	<!--
		Handle additional properties to events attached to task boundary (where hint is left by IPM import in the attributes of the task it's attached to 
	-->
	<xsl:variable name="catchEventTaskId" select="$activity/xpdl2:Event/xpdl2:IntermediateEvent/@Target"/>

	<xsl:if test="$catchEventTaskId != ''">
		<xsl:variable name="taskAct" select="ancestor::xpdl2:WorkflowProcess//xpdl2:Activity[@Id = $catchEventTaskId]"/>

		<xsl:if test="$taskAct">
			<xsl:if test="$activity/xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerTimer">
				<!-- 
				Output deadline conditions to timer events: hint is an extendedAttribute iProcessV8_DeadCond on the attached-to-task 
				(because the timer event did not exist at that point). 
				-->
				<xsl:variable name="deadConds" select="$taskAct/xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'iProcessV8_DeadCond']"/>

				<xsl:if test="$deadConds[1]/@Value != ''">
					<iProcessExt:DeadlineCondition1 ScriptGrammar="iProcessScript">
						<xsl:value-of select="$deadConds[1]/@Value"/>
					</iProcessExt:DeadlineCondition1>

					<xsl:if test="$deadConds[2]/@Value != ''">
						<iProcessExt:DeadlineCondition2 ScriptGrammar="iProcessScript">
							<xsl:value-of select="$deadConds[2]/@Value"/>
						</iProcessExt:DeadlineCondition2>
					</xsl:if>
				</xsl:if>
			</xsl:if>
		</xsl:if>
	</xsl:if>
	
	<!-- 
		Handle Sub-Process call task's "start at sub-process task' definition .
	-->
	<xsl:variable name="subProcStartStep" select="$activity/xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'iProcessV8_SubProcessStartStep']/@Value"/>
	<xsl:if test="$subProcStartStep != ''">

		<xsl:variable name="subProcessId" select="$activity/xpdl2:Implementation/xpdl2:SubFlow/@Id"/>
		<xsl:if test="$subProcessId != ''">
			<xsl:variable name="startStepId">
				<xsl:variable name="subProcess" select="/xpdl2:Package/xpdl2:WorkflowProcesses/xpdl2:WorkflowProcess[@Id = $subProcessId]"/>
				<xsl:choose>
					<xsl:when test="$subProcess">
						<!-- if the sub-process is in local package then pull the activity id from there. -->
						<xsl:value-of select="$subProcess//xpdl2:Activity[@Name = $subProcStartStep]/@Id"/>
					</xsl:when>
					<xsl:otherwise>
						<!-- Otherwise see if there's a sub-process already on the system with this id and get start step from that. -->
						<xsl:value-of select="java:com.tibco.xpd.xpdl2.resources.XpdlMigrateXsltUtil.getActivityIdByName($subProcessId, $subProcStartStep)"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			
			<xsl:if test="$startStepId != ''">
				<iProcessExt:SubProcessTask StartStepId="{$startStepId}"/>
			</xsl:if>
		</xsl:if>
		
	</xsl:if>
	
</xsl:template>

<!--
===============================================================================
Set runtime identifier field for sub-process invokes referencing interfaces (dynamic sub-process/graft step)
===============================================================================
-->
<xsl:template match="xpdl2:SubFlow">
	<xsl:copy>
		<!-- Set runtime identifier field name from iProcess sub-process name array. -->
		<xsl:variable name="subProcNameArray" select="ancestor::xpdl2:Activity/xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'iProcessV8_SubProcNameArray']/@Value"/>
		<xsl:if test="$subProcNameArray != ''">
			<xsl:attribute name="xpdExt:ProcessIdentifierField"><xsl:value-of select="$subProcNameArray"/></xsl:attribute>
		</xsl:if>
		
		<xsl:apply-templates select="@* | node()"/>
		
	</xsl:copy>

</xsl:template>

<!--
===============================================================================
Remove sub-process data mappings to formal parameters that do not exist (iProcess Modeler environment
does not remove mappings to sub-process I/O parameters automcatically when the parameter is removed
so we can do it here).
===============================================================================
-->
<xsl:template match="xpdl2:SubFlow/xpdl2:DataMappings/xpdl2:DataMapping">
	<!-- Only output the  datamapping if the target formal parameter exists. -->
	<xsl:variable name="subProcessId" select="ancestor::xpdl2:Activity/xpdl2:Implementation/xpdl2:SubFlow/@Id"/>
	<xsl:variable name="paramName" select="@Formal"/>
	
	<xsl:variable name="subProcess" select="/xpdl2:Package/xpdl2:WorkflowProcesses/xpdl2:WorkflowProcess[@Id = $subProcessId]"/>
	<xsl:variable name="interface" select="/xpdl2:Package/xpdExt:ProcessInterfaces/xpdExt:ProcessInterface[@Id = $subProcessId]"/>

	<!-- "EXISTS" if the sub-process is found and parameter exists.
						"NOT_EXISTS" if the sub-process is found but the parameter does NOT exist. 
						"SUBPROC_NOT_EXISTS" if the sub-process/process interface doesn't exists (hasn't been imported yet probably).  

		We ONLY want to remove the mapping if we FOUND the sub-process/interface and then found param didn't exist (no if we simply couldn't find the 
			sub-process/inteface becuase (for instance) user is importing in the wrong order or something. User can always fix the problem marker in Studio
	-->
	<xsl:variable name="exists">
		<xsl:choose>
			<xsl:when test="$subProcess">
				<xsl:choose>
					<!-- If it looks like an inteface param (TPARI/O_xxx) - look for parameter in implemented interface -->
					<xsl:when test="starts-with($paramName, 'TPAR')">
						<!-- <xsl:message>Interface Id: <xsl:value-of select="$subProcess/xpdExt:ImplementedInterface/@xpdExt:ProcessInterfaceId"/></xsl:message> -->
						<xsl:variable name="implementedInterface" select="/xpdl2:Package/xpdExt:ProcessInterfaces/xpdExt:ProcessInterface[@Id = $subProcess/xpdExt:ImplementedInterface/@xpdExt:ProcessInterfaceId]"/>
						<xsl:choose>
							<xsl:when test="$implementedInterface">
								<xsl:choose>
									<xsl:when test="count($implementedInterface/xpdl2:FormalParameters/xpdl2:FormalParameter[@Name = $paramName]) > 0">
										<xsl:text>EXISTS</xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<!-- If we found the inteface and its an interface parameter and we didn't find itn then we know definitively that it doesn't exist -->
										<xsl:text>NOT_EXISTS</xsl:text>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:when>
							<xsl:otherwise>
								<!-- if interface not found in local package we may find it already imported so don't set variable. -->
								<xsl:value-of select="java:com.tibco.xpd.xpdl2.resources.XpdlMigrateXsltUtil.subProcessParamExists($subProcess/xpdExt:ImplementedInterface/@xpdExt:ProcessInterfaceId, $paramName)"/>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
					<!-- Subprocess is local, see if the parameter is defined in the sub-process itself. -->
					<xsl:when test="count($subProcess/xpdl2:FormalParameters/xpdl2:FormalParameter[@Name = $paramName]) > 0">
						<xsl:text>EXISTS</xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<!-- If it's not found in sub-process in local package params and not dealt with above for interface param then we know definitely it doesn't exist. -->
						<xsl:text>NOT_EXISTS</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:when test="$interface">
				<!-- If invoked interface is in local package then we have a definitive answer one way or the other -->
				<xsl:choose>
					<xsl:when test="count($interface/xpdl2:FormalParameters/xpdl2:FormalParameter[@Name = $paramName]) > 0">
						<xsl:text>EXISTS</xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text>NOT_EXISTS</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:otherwise>
				<!-- if sub-process / interface not found in local package we may find it already imported so don't set variable. -->
				<xsl:value-of select="java:com.tibco.xpd.xpdl2.resources.XpdlMigrateXsltUtil.subProcessParamExists($subProcessId, $paramName)"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable> 

	<xsl:choose>
		<xsl:when test="$exists = 'EXISTS' or $exists = 'SUBPROC_NOT_EXISTS'">
			<xsl:copy>
				<xsl:apply-templates select="@* | node()"/>
			</xsl:copy>
		</xsl:when>
		<xsl:otherwise>
			<!-- Ignore mapping to param we cannot find in a process/inteface we could find.. -->
		</xsl:otherwise>
	</xsl:choose>

</xsl:template>

<!--
===============================================================================
	Given input nodes that have xxx_ namespace prefixing rather than xxx: convert the xxx_ to xxx:
		This allows the provided of the source xml to 'fake' extra bits into xpdl 1 ext attrs (for instance) that
		it wants us to place directly in xpdl2
===============================================================================
-->
<xsl:template name="CopyAndConvertPrefix">
	<xsl:param name="input"/>

	<xsl:for-each select="$input">
		
		<xsl:variable name="name">
			<xsl:call-template name="ConvertPrefix"/>
		</xsl:variable>

		<xsl:element name="{$name}">
		
			<xsl:for-each select="@*">
				<xsl:variable name="attrName">
					<xsl:call-template name="ConvertPrefix"/>
				</xsl:variable>
				
				<xsl:attribute name="{$attrName}"><xsl:value-of select="."/></xsl:attribute>

			</xsl:for-each>
			
			<!-- if there are sub-elements then add a newline and recurs process them -->
			<xsl:choose>
				<xsl:when test="*">
					<xsl:call-template name="CopyAndConvertPrefix">
						<xsl:with-param name="input" select="*"/>
					</xsl:call-template>
				</xsl:when>
				<xsl:otherwise>
					<xsl:variable name="textVal"><xsl:value-of select="text()"/></xsl:variable>
					<xsl:if test="$textVal != ''">
						<xsl:value-of select="$textVal"/>
					</xsl:if>	
				</xsl:otherwise>
				
			</xsl:choose>

			<!-- and then append any text -->	
		
		</xsl:element>

	</xsl:for-each>
	
</xsl:template>


<!--
===============================================================================
	Return proper ns prefix for name in format xxx_Name
===============================================================================
-->
<xsl:template name="ConvertPrefix">
	<xsl:variable name="prefix">
		<xsl:choose>
			<xsl:when test="contains(local-name(.), '_')"><xsl:value-of select="substring-before(local-name(.), '_')"/></xsl:when>
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:variable>

	<xsl:variable name="shortName">
		<xsl:choose>
			<xsl:when test="contains(local-name(.), '_')"><xsl:value-of select="substring-after(local-name(.), '_')"/></xsl:when>
			<xsl:otherwise><xsl:value-of select="local-name(.)"/></xsl:otherwise>
		</xsl:choose>
	</xsl:variable>

	<xsl:choose>
		<xsl:when test="$prefix != ''"><xsl:value-of select="$prefix"/>:<xsl:value-of select="$shortName"/></xsl:when>
		<xsl:otherwise><xsl:value-of select="$shortName"/></xsl:otherwise>
	</xsl:choose>

</xsl:template>


<!--
===============================================================================
Ignore extended attributes put in XPDL for us to pick up in this xslt.
===============================================================================
-->
<xsl:template match="xpdl2:ExtendedAttribute[starts-with(@Name,  'iProcessV8')]">
</xsl:template>

<!--
===============================================================================
Default copy all template.
===============================================================================
-->
<xsl:template match="@* | node()">
	<xsl:copy>
		<xsl:apply-templates select="@* | node()"/>
	</xsl:copy>
</xsl:template>

</xsl:stylesheet>
