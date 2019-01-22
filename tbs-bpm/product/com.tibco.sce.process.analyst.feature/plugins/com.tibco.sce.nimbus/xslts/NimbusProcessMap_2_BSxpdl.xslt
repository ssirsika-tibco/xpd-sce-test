<?xml version="1.0" encoding="UTF-8"?>
<!-- 
===================================================================================================
(Preliminary prototype). TIBCO Nimbus Control-Model to TIBCO Business Studio AMX BPM Process XPDL model transformation 
	

From:	Nimbus Process Map in Simplified XML export format						8.1	

To:		Business Studio Nimbus Import Intermediate XPDL FormatVersion	13

				The output is only loosley based upon Studio XPDL for Business Studio v3.5.10.
				Many of the difficult things are post-processed in java in XPDL once the basic data has been imported (such as BPMN layout etc). 
				

Schemas: 
	Input: Simplified.xsd (Nimbus Process Map) default namespace.
	
	Output: xpdl2			WFMC Extended Process Definition Language "http://www.wfmc.org/2008/XPDL2.1"
				xpdExt			TIBCO Business process extensions to XPDL "http://www.tibco.com/XPD/xpdExtension1.0.0"


Author: Sid.

Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
===================================================================================================
-->
<xsl:stylesheet 
	version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	xmlns:xpdExt="http://www.tibco.com/XPD/xpdExtension1.0.0" 
	xmlns:xpdl2="http://www.wfmc.org/2008/XPDL2.1" 
	xsi:schemaLocation="http://www.wfmc.org/2008/XPDL2.1 http://www.wfmc.org/standards/bpmnxpdl_31.xsd"

	xmlns:java="http://xml.apache.org/xslt/java"
>
<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>

<!-- Control factor for choosing to do one lane per resource or one per diagram.  -->
<xsl:param name="oneLanePerResource">no</xsl:param>

<!-- 
===============================================================
Create instance of import Process Map utility class that will do some of the more complex
stuff that is not suited to xslt.
===============================================================
-->
<xsl:variable name="util" select="java:com.tibco.xpd.nimbus.xsltutil.ImportNimbusProcessMapXsltUtil.new()"/>
<!-- And the internationalisation message bundle, use the following to access string: java:get($msgs, 'Message_key') -->
<xsl:variable name="msgs" select="java:com.tibco.xpd.nimbus.xsltutil.ImportNimbusProcessMapXsltMessages.new()"/>

<xsl:variable name="packageId" select="java:createUniqueId($util)"/>

<xsl:variable name="laneHeight" select="'160'"/> <!-- Pixels -->

<!-- 
===============================================================
Root template: Create xpdl package from Process Map.
===============================================================
-->
<xsl:template match="/">
	
	<xpdl2:Package Id="{$packageId}" xpdExt:DisplayName="{/XMI/@name}" Name="{java:getTokenName($util, /XMI/@name)}">

		<!-- Package Header info (we'll use first actual diagram in file for package header info provision. -->
		<xsl:call-template name="addPackageHeader"><xsl:with-param name="diagram" select="/XMI/XMI.content/Diagram[1]"/></xsl:call-template>

		<!-- All diagrams that have activities -->
		<xsl:variable name="diagrams" select="/XMI/XMI.content/Diagram[count(Activity) > 0]"/>
		
		<!-- Add package participant for the set of uniquely named resources. -->
		<xsl:call-template name="addParticipants"><xsl:with-param name="diagrams" select="$diagrams"/></xsl:call-template>
		
		<!-- Add a pool and lane for each diagram. -->
		<xsl:call-template name="addPools"><xsl:with-param name="diagrams" select="$diagrams"/></xsl:call-template>
		
		<!-- Add Task Annotations for each activity notes bubble -->
		<xsl:call-template name="addTaskAnnotations">
			<xsl:with-param name="diagrams" select="$diagrams"/>
		</xsl:call-template>
		
		<!-- Add the WorkflowProcess for each diagram. -->
		<xsl:call-template name="addProcesses">
			<xsl:with-param name="diagrams" select="$diagrams"/>
		</xsl:call-template>
		
		<!-- 
			Extended attributes.
		-->
		<xsl:call-template name="addPackageExtendedAttributes"/>
	
	</xpdl2:Package>
</xsl:template>

<!-- 
===============================================================
Output a xpdl2:WorkflowProcess for each diagram.
===============================================================
-->
<xsl:template name="addProcesses">
	<xsl:param name="diagrams"/>
	
	<xpdl2:WorkflowProcesses>

		<xsl:for-each select="$diagrams">
			<xsl:variable name="diagram" select="."/>
			<xsl:variable name="processId"><xsl:call-template name="getProcessId"><xsl:with-param name="diagramId" select="$diagram/GUID"/></xsl:call-template></xsl:variable>
			
			<xpdl2:WorkflowProcess Id="{$processId}">
				<xsl:attribute name="xpdExt:DisplayName"><xsl:value-of select="$diagram/Level"/>: <xsl:value-of select="java:normaliseSpace($util, $diagram/Title)"/></xsl:attribute>
				
				<xsl:variable name="labelForName"><xsl:call-template name="getUniqueProcessLabel"><xsl:with-param name="diagram" select="$diagram"/></xsl:call-template></xsl:variable>
				<xsl:attribute name="Name"><xsl:value-of select="java:getTokenName($util, $labelForName)"/></xsl:attribute>
				
				<xpdl2:ProcessHeader>
					<xpdl2:Description xpdExt:DocumentationURL="{$diagram/URL}"></xpdl2:Description>
				</xpdl2:ProcessHeader>

				<!-- Add process data - one for each unique connection (input / output) label name -->
				<xsl:call-template name="addProcessData">
					<xsl:with-param name="diagram" select="$diagram"/>
				</xsl:call-template>

				<!-- Add the tasks / activities for diagram activities. -->
				<xsl:call-template name="addActivities">
					<xsl:with-param name="diagram" select="$diagram"/>
				</xsl:call-template>

				<!-- Add the sequence flows for diagram activities. -->
				<xsl:call-template name="addSequenceFlows">
					<xsl:with-param name="diagram" select="$diagram"/>
				</xsl:call-template>
				
			</xpdl2:WorkflowProcess>

		</xsl:for-each>
	</xpdl2:WorkflowProcesses>

</xsl:template>

<!-- 
===============================================================
Output Text annotation and association for each nimbus activity 
notes bubble
===============================================================
-->
<xsl:template name="addTaskAnnotations">
	<xsl:param name="diagrams"/>
	
	<xsl:variable name="notes" select="$diagrams/Activity/Notes"/>
	
	<xsl:if test="$notes/text() != ''">
	
		<xpdl2:Associations>
		
			<!--  Create association connections to the text annotations we will create below. -->
			<xsl:for-each select="$notes">
				<xsl:variable name="note" select="."/>

				<xsl:if test="$note/text() != ''">								

					<xsl:variable name="activityId">
						<xsl:call-template name="getActivityId"><xsl:with-param name="activity" select="$note/.."/></xsl:call-template>
					</xsl:variable>
									
				    <xpdl2:Association Id="_Assoc_{$activityId}" Name="" AssociationDirection="None" Source="{$activityId}" Target="_Annot_{$activityId}">
				      <xpdl2:Object Id="{java:createUniqueId($util)}"/>
				      
				      <xpdl2:ConnectorGraphicsInfos>
				        <xpdl2:ConnectorGraphicsInfo BorderColor="0,0,128" ToolId="XPD.ConnectionInfo"/>
				        <xpdl2:ConnectorGraphicsInfo ToolId="XPD.StartAnchorPosition"/>
				      </xpdl2:ConnectorGraphicsInfos>
				    </xpdl2:Association>

				</xsl:if>
			</xsl:for-each>
			
		</xpdl2:Associations>
		
		<xpdl2:Artifacts>
			<!--  Create association connections to the text annotations we will create below. -->
			<xsl:for-each select="$notes">
				<xsl:variable name="note" select="."/>
								
				<xsl:if test="$note/text() != ''">								

					<xsl:variable name="activityId">
						<xsl:call-template name="getActivityId"><xsl:with-param name="activity" select="$note/.."/></xsl:call-template>
					</xsl:variable>
					
					<xpdl2:Artifact Id="_Annot_{$activityId}" ArtifactType="Annotation" TextAnnotation="{$note/text()}">
					
						<xsl:variable name="laneId">
							<xsl:call-template name="getLaneIdForActivity"><xsl:with-param name="activity" select="$note/.."></xsl:with-param></xsl:call-template>
						</xsl:variable>
						
						<xpdl2:NodeGraphicsInfos>
							<xpdl2:NodeGraphicsInfo BorderColor="0,0,128" Height="19.0" LaneId="{$laneId}" >
								<xpdl2:Coordinates XCoordinate="30.0" YCoordinate="30.0"/>
							</xpdl2:NodeGraphicsInfo>
						</xpdl2:NodeGraphicsInfos>
					</xpdl2:Artifact>

				</xsl:if>		
			</xsl:for-each>
					
		</xpdl2:Artifacts>
	</xsl:if>
	
</xsl:template>


<!-- 
===============================================================
Output xpdl2:Transitions for the diagram connections between activities.

Note that there may be multiple connections between the same activity in the same 
direction (because in Nimbus diagram the connections suggest input data / triggers
for the activity not necessarily just the flow. In this situation we collapse all  
into one sequence flow
===============================================================
-->
<xsl:template name="addSequenceFlows">
	<xsl:param name="diagram"/>
	
	<!-- 
		In the Simplified.xsd source, a connection appears in an Activity Input and/or and Activity Output element with the same guid. 
		So connections between activities will appear once as an Input and once as an Output.
		Start connections will appear only as Input's and end connections will appear only as Ouput's

		So the simplest way to process this is to create a sequence flow for every input connection.
		That will deal with all the connections between activities and the start connections (which we will connect to the start event we created).

		Then all we need to do is handle the Output's that are not connected to anything and connect them to end event.
	-->
	<xpdl2:Transitions>

		<!-- 
			Start off by handling each start connection (the first Input connection into any Activity with no source activity in this diagram) 
		-->
		<xsl:for-each select="$diagram/Activity/Input[count(LinkedToActivity) = 0 or LinkedToActivity = '' or LinkedToDiagram != $diagram/GUID][1]">
			<xsl:variable name="inputConnection" select="."/>

			<xsl:variable name="sourceActivityId"><xsl:call-template name="getStartEventActivityId"><xsl:with-param name="diagram" select="$diagram"/></xsl:call-template></xsl:variable>
			<xsl:variable name="targetActivityId"><xsl:call-template name="getActivityId"><xsl:with-param name="activity" select="$inputConnection/ancestor::Activity"/></xsl:call-template></xsl:variable>
				
			<!-- Finally, we have everything we need to output the sequence flow. -->
			<xsl:call-template name="addSequenceFlow">
				<xsl:with-param name="sourceActivityId" select="$sourceActivityId"/>
				<xsl:with-param name="targetActivityId" select="$targetActivityId"/>
				<xsl:with-param name="commentaries" select="$diagram/Activity[ID = $inputConnection/ancestor::Activity/ID]/Input[(count(LinkedToActivity) = 0 or LinkedToActivity = '' or LinkedToDiagram != $diagram/GUID)]/Commentary"/>
			</xsl:call-template>
		
		</xsl:for-each>

		<!-- It's not standard practice in Nimbus diagram, but it's possible there are activities with no incoming connection, these should be connected to start event. -->
		<xsl:for-each select="$diagram/Activity[count(Input) = 0]">
			<xsl:variable name="sourceActivityId"><xsl:call-template name="getStartEventActivityId"><xsl:with-param name="diagram" select="$diagram"/></xsl:call-template></xsl:variable>
			<xsl:variable name="targetActivityId"><xsl:call-template name="getActivityId"><xsl:with-param name="activity" select="."/></xsl:call-template></xsl:variable>
			
			<xsl:call-template name="addSequenceFlow"><xsl:with-param name="sourceActivityId" select="$sourceActivityId"/><xsl:with-param name="targetActivityId" select="$targetActivityId"/></xsl:call-template>
		</xsl:for-each>

		<!-- 
			Then handle all input connections that have a source activity in this diagram ( = "" or this diagram id)
		-->
		<xsl:for-each select="$diagram/Activity/Input[LinkedToActivity != '' and (LinkedToDiagram = '' or LinkedToDiagram = $diagram/GUID)]">
			<xsl:variable name="inputConnection" select="."/>

			<xsl:variable name="targetActivity" select="$inputConnection/ancestor::Activity"/>
			<xsl:variable name="sourceActivity" select="$diagram/Activity[ID = $inputConnection/LinkedToActivity]"/>
			
			<xsl:if test="count($sourceActivity) > 0">
				<!-- 
					Handle potential multiple connections between same activity by only processing first connection to the targetActivity from the source activity...
				-->
				<!-- Get all the Input elements whose source activity ID is the same as the Input we're dealing with. -->
				<xsl:variable name="inputsFromSameSource" select="$targetActivity/Input[LinkedToActivity = $sourceActivity/ID]"/>

				<xsl:variable name="isFirstFromSource">
					
					<!-- Then only process the Input into a transition if the curent one is first in list (by checking current one's id with first in list from same source). -->
					<xsl:choose>
						<xsl:when test="$inputConnection/ID = $inputsFromSameSource[1]/ID"><xsl:text>true</xsl:text></xsl:when>
						<xsl:otherwise><xsl:text>false</xsl:text></xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				
				<!-- Then only process the Input into a transition if the curent one is first in list (by checking current one's id with first in list from same source). -->
				<xsl:if test="$isFirstFromSource = 'true'">
					<xsl:variable name="sourceActivityId"><xsl:call-template name="getActivityId"><xsl:with-param name="activity" select="$sourceActivity"/></xsl:call-template></xsl:variable>
					<xsl:variable name="targetActivityId"><xsl:call-template name="getActivityId"><xsl:with-param name="activity" select="$targetActivity"/></xsl:call-template></xsl:variable>
					
					<!-- Finally, we have everything we need to output the sequence flow. -->
					<xsl:call-template name="addSequenceFlow">
						<xsl:with-param name="sourceActivityId" select="$sourceActivityId"/>
						<xsl:with-param name="targetActivityId" select="$targetActivityId"/>
						<xsl:with-param name="commentaries" select="$inputsFromSameSource/Commentary"/>
					</xsl:call-template>
		
				</xsl:if>
			</xsl:if>

		</xsl:for-each> <!-- Next $diagram/Activity/Input -->

		<!-- Then finally deal with all end connections by connecting each to the single end event. 
				So we just need to get the first Output connections from each activity that is not linked to an activity in this diagram
		-->
		<xsl:for-each select="$diagram/Activity/Output[count(LinkedToActivity) = 0 or LinkedToActivity = '' or LinkedToDiagram != $diagram/GUID][1]">
			<xsl:variable name="outputConnection" select="."/>

			<xsl:variable name="sourceActivityId"><xsl:call-template name="getActivityId"><xsl:with-param name="activity" select="$outputConnection/ancestor::Activity"/></xsl:call-template></xsl:variable>
			<xsl:variable name="targetActivityId"><xsl:call-template name="getEndEventActivityId"><xsl:with-param name="diagram" select="$diagram"/></xsl:call-template></xsl:variable>
				
			<!-- Finally, we have everything we need to output the sequence flow. -->
			<xsl:call-template name="addSequenceFlow">
				<xsl:with-param name="sourceActivityId" select="$sourceActivityId"/>
				<xsl:with-param name="targetActivityId" select="$targetActivityId"/>
				<xsl:with-param name="commentaries" select="$diagram/Activity[ID = $outputConnection/ancestor::Activity/ID]/Output[(count(LinkedToActivity) = 0 or LinkedToActivity = '' or LinkedToDiagram != $diagram/GUID)]/Commentary"/>
			</xsl:call-template>
		
		</xsl:for-each>

		<!-- It's not standard practice in Nimbus diagram, but it's possible there are activities with no outgoing connection, these should be connected to end event. -->
		<xsl:for-each select="$diagram/Activity[count(Output) = 0]">
			<xsl:variable name="sourceActivityId"><xsl:call-template name="getActivityId"><xsl:with-param name="activity" select="."/></xsl:call-template></xsl:variable>
			<xsl:variable name="targetActivityId"><xsl:call-template name="getEndEventActivityId"><xsl:with-param name="diagram" select="$diagram"/></xsl:call-template></xsl:variable>
			
			<xsl:call-template name="addSequenceFlow"><xsl:with-param name="sourceActivityId" select="$sourceActivityId"/><xsl:with-param name="targetActivityId" select="$targetActivityId"/></xsl:call-template>
		</xsl:for-each>
		
	</xpdl2:Transitions>
	
</xsl:template>


<!-- 
===============================================================
Output a xpdl2:Transition with given source and target.
===============================================================
-->
<xsl:template name="addSequenceFlow">
	<xsl:param name="sourceActivityId"/>
	<xsl:param name="targetActivityId"/>
	<xsl:param name="commentaries"/>
	
	<xpdl2:Transition Id="{java:createUniqueId($util)}" From="{$sourceActivityId}" To="{$targetActivityId}">
		<xsl:if test="$commentaries != ''">
			<xsl:call-template name="addDescriptionFromCommentaries"><xsl:with-param name="commentaries" select="$commentaries"/></xsl:call-template>
		</xsl:if>
		
		<xpdl2:ConnectorGraphicsInfos>
			<xpdl2:ConnectorGraphicsInfo BorderColor="0,0,128" ToolId="XPD.ConnectionInfo"/>
		</xpdl2:ConnectorGraphicsInfos>
	</xpdl2:Transition>	
	
</xsl:template>


<!-- 
===============================================================
Output a xpdl2:Activity for each diagram activity
===============================================================
-->
<xsl:template name="addActivities">
	<xsl:param name="diagram"/>
	
	<!-- list of unique resoruce definitions in daigram(their will be a lane for each one. -->
	<xsl:variable name="laneResources" select="java:getUniqueTextElementSet($util, $diagram/Activity/Resource)"/>
	
	<xpdl2:Activities>
		<!-- Add the single start event activity -->
		<xsl:call-template name="addStartEvent"><xsl:with-param name="diagram" select="$diagram"/></xsl:call-template>

		<!-- Add the single end event activity -->
		<xsl:call-template name="addEndEvent"><xsl:with-param name="diagram" select="$diagram"/></xsl:call-template>
			
		<!-- Create an activity for each daigram activity. -->
		<xsl:for-each select="$diagram/Activity">
			<xsl:variable name="activityIdx" select="position()"/>
			<xsl:variable name="activity" select="."/>

			<xsl:variable name="activityId"><xsl:call-template name="getActivityId"><xsl:with-param name="activity" select="$activity"/></xsl:call-template></xsl:variable>
			
			<xpdl2:Activity Id="{$activityId}" xpdExt:Visibility="Private">
				<xsl:call-template name="addLabelAndName"><xsl:with-param name="label" select="$activity/Text"/></xsl:call-template>
				
				<!-- Gather info and add to description. -->
				<xsl:call-template name="addActivityDescription">
					<xsl:with-param name="diagram" select="$diagram"/>
					<xsl:with-param name="activity" select="$activity"/>
				</xsl:call-template>
				
				<!-- Add task implement type. -->
				<xsl:variable name="taskType"><xsl:call-template name="getTaskType"><xsl:with-param name="activity" select="$activity"/></xsl:call-template></xsl:variable>
				
				<xsl:call-template name="addActivityImplementationDetail">
					<xsl:with-param name="diagram" select="$diagram"/>
					<xsl:with-param name="activity" select="$activity"/>
					<xsl:with-param name="taskType" select="$taskType"/>
				</xsl:call-template>

				<!-- Add participants for resources -->
				<xsl:variable name="resources" select="$activity/Resource"/>
				
				<xsl:if test="count($resources) > 0 and $taskType != 'SubFlow'">
					<xpdl2:Performers>
						<xsl:for-each select="$resources">
							<xpdl2:Performer>
								<xsl:call-template name="getParticipantId"><xsl:with-param name="resourceName" select="./text()"/></xsl:call-template>
							</xpdl2:Performer>
						</xsl:for-each>
					</xpdl2:Performers>
				</xsl:if>

				<!-- Output graphical info. -->
				<xsl:call-template name="addActivityGraphicalInfo">
					<xsl:with-param name="diagram" select="$diagram"/>
					<xsl:with-param name="activity" select="$activity"/>
					<xsl:with-param name="activityIdx" select="$activityIdx"/>
				</xsl:call-template>

				<xpdl2:Extensions/>

				<!-- For user tasks add resource pattern default config's -->
				<xsl:if test="$taskType = 'User'">
					<xpdExt:ActivityResourcePatterns>
						<xpdExt:AllocationStrategy xpdExt:Offer="OfferAll" xpdExt:Strategy="SYSTEM_DETERMINED" xpdExt:ReOfferOnClose="true" xpdExt:ReOfferOnCancel="true"/>
					<xpdExt:WorkItemPriority InitialPriority="50"/>
					</xpdExt:ActivityResourcePatterns>
				</xsl:if>
				
				<!-- Add interface data assocations for incoming / outgoing data (except to sub-process tasks). -->
				<xsl:if test="$taskType != 'SubFlow'">
					<xsl:call-template name="addActivityInterfaceData">
						<xsl:with-param name="diagram" select="$diagram"/>
						<xsl:with-param name="incomingConnections" select="$activity/Input"/>
						<xsl:with-param name="outgoingConnections" select="$activity/Output"/>
					 </xsl:call-template>
				</xsl:if>
				
				
				
			</xpdl2:Activity>

				
		</xsl:for-each>
	</xpdl2:Activities>
	
</xsl:template>

<!-- 
===============================================================
Add description detail to activity.
===============================================================
-->
<xsl:template name="addActivityDescription">
	<xsl:param name="diagram"/>
	<xsl:param name="activity"/>
	
	<!-- Add the diagram URL to all activities. This might seem a bitsuperfluous initially but it is anticipated
			that eventually we may string together activities from multiple linked-together Nimbus diagrams and
			therefore it will be useful to refer to the originating diagram. -->
	<xpdl2:Description xpdExt:DocumentationURL="{$diagram/URL}">
		<xsl:value-of select="$activity/Commentary"/>
		
		<xsl:if test="count($activity/StatementLink) > 0">
			<xsl:if test="$activity/Commentary/text() != ''">
				<xsl:text>&#10;</xsl:text>
			</xsl:if>
				
			<!-- Group all by statement set name. The set name may be something liek "ISO-9000 Support" and individual statements be goals within that set. -->
			<xsl:variable name="statementSets" select="java:getUniqueTextElementSet($util, ($activity/StatementLink/Set))"/>

			<xsl:for-each select="$statementSets">
				<xsl:variable name="statementSet" select="."/>
				<xsl:variable name="statementLinks" select="$activity/StatementLink[Set = $statementSet]"/>
				
				<xsl:text>&#10;</xsl:text>
				<xsl:value-of select="java:format($msgs, 'StatementSet_label', $statementSet/text())"/><xsl:text>&#10;</xsl:text>
				<xsl:text>====================================================================================================</xsl:text><xsl:text>&#10;&#10;</xsl:text>
				
				<xsl:for-each select="$statementLinks">
					<xsl:variable name="itemIdx" select="position()"/>
					
					<xsl:value-of select="java:format($msgs, 'Statement_label',  Statement/text(), Status/text())"/><xsl:text>&#10;</xsl:text>

					<xsl:if test="Description/text() != ''">
						<xsl:text>------------------------------------------------------------------------------------------------------------------------</xsl:text><xsl:text>&#10;</xsl:text>
						<xsl:value-of select="Description/text()"/><xsl:text>&#10;</xsl:text>

						<xsl:choose>
							<xsl:when test="$itemIdx = count($statementLinks)">
								<xsl:text>====================================================================================================</xsl:text><xsl:text>&#10;&#10;</xsl:text>
							</xsl:when>
							<xsl:otherwise>
								<xsl:text>------------------------------------------------------------------------------------------------------------------------</xsl:text><xsl:text>&#10;&#10;</xsl:text>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:if>

				</xsl:for-each> <!--  Next statementLink -->

				
			</xsl:for-each> <!-- Next Statement Link Set -->
		
		</xsl:if>
	</xpdl2:Description>
	
	
</xsl:template>

<!-- 
===============================================================
Add xpdl2:Activity implementation for given Nimbus diagram activity.
===============================================================
-->
<xsl:template name="addActivityImplementationDetail">
	<xsl:param name="diagram"/>
	<xsl:param name="activity"/>
	<xsl:param name="taskType"/>
	
	<!-- Decide what type of BPMN activity is appropriate for Nimbus activity. -->
	<xsl:choose>
		<!-- For drill down activity create reusable sub-process task. -->
		<xsl:when test="$taskType = 'SubFlow'">

			<xpdl2:Implementation>
				<xsl:variable name="subProcessId">
					<xsl:call-template name="getProcessId"><xsl:with-param name="diagramId" select="$activity/DrillDown/DrillDownGUID"/></xsl:call-template>
				</xsl:variable>
					
				<xpdl2:SubFlow Id="{$subProcessId}" xpdExt:StartStrategy="StartImmediately" xpdExt:SuspendResumeWithParent="true"/>
			</xpdl2:Implementation>
		
		</xsl:when>

		<!-- XPD-6565: TaskType can now be set in Nimbus process map.  -->

		<xsl:when test="$taskType = 'User'">
			<xpdl2:Implementation>
				<xpdl2:Task>
					<xpdl2:TaskUser Implementation="Unspecified">
						<xpdl2:Performers>
							<xpdl2:Performer>-defined-in-Activity-Performer-</xpdl2:Performer>
						</xpdl2:Performers>
						<xpdl2:MessageIn Id="{java:createUniqueId($util)}"/>
						<xpdl2:MessageOut Id="{java:createUniqueId($util)}"/>
					</xpdl2:TaskUser>
				</xpdl2:Task>
			</xpdl2:Implementation>
		</xsl:when>
		
		<xsl:when test="$taskType = 'Manual'">
			<xpdl2:Implementation>
				<xpdl2:Task>
					<xpdl2:TaskManual>
						<xpdl2:Performers>
							<xpdl2:Performer>-defined-in-Activity-Performer-</xpdl2:Performer>
						</xpdl2:Performers>
					</xpdl2:TaskManual>
				</xpdl2:Task>
			</xpdl2:Implementation>
		</xsl:when>
		
		<xsl:when test="$taskType = 'Service'">
			<xpdl2:Implementation>
				<xpdl2:Task>
					<xpdl2:TaskService Implementation="Unspecified">
						<xpdl2:MessageIn Id="{java:createUniqueId($util)}"/>
						<xpdl2:MessageOut Id="{java:createUniqueId($util)}"/>
					</xpdl2:TaskService>
				</xpdl2:Task>
			</xpdl2:Implementation>
		</xsl:when>
		
		<xsl:when test="$taskType = 'Receive'">
			<xpdl2:Implementation>
				<xpdl2:Task>
					<xpdl2:TaskReceive xpdExt:ImplementationType="Unspecified" Implementation="Unspecified">
						<xpdl2:Message Id="{java:createUniqueId($util)}"/>
					</xpdl2:TaskReceive>
				</xpdl2:Task>
			</xpdl2:Implementation>
		</xsl:when>

		<!-- Default is always none -->
		<xsl:otherwise>
			<xpdl2:Implementation>
				<xpdl2:No/>
			</xpdl2:Implementation>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!-- 
===============================================================
Add xpdl2:Activity graphical info for given Nimbus diagram activity.
===============================================================
-->
<xsl:template name="addActivityGraphicalInfo">
	<xsl:param name="diagram"/>
	<xsl:param name="activity"/>
	<xsl:param name="activityIdx"/>
	
	<!-- 
		Output graphical info. 
		NOTE: Proper layout will be performed as a post-import task (in JAVA operating on the XPDL that we output from this XPDL
					So any such positioning info etc here is just temporary. 
	-->

	<!-- Place the activity in the appropriate lane for the resource (or defaulty if no resource or multiple resources. -->
	<xsl:variable name="laneId"><xsl:call-template name="getLaneIdForActivity"><xsl:with-param name="activity" select="$activity"/></xsl:call-template></xsl:variable>
	
	<xpdl2:NodeGraphicsInfos>
		<xpdl2:NodeGraphicsInfo BorderColor="0,0,128" FillColor="255,219,74" Height="80.0" LaneId="{$laneId}" Width="120.0">
			<xpdl2:Coordinates XCoordinate="{($activityIdx * 176) + 20}" YCoordinate="{($laneHeight div 2)}"/>
		</xpdl2:NodeGraphicsInfo>
	</xpdl2:NodeGraphicsInfos>
	
</xsl:template>


<!-- 
===============================================================
Output the start event for the given diagram.
===============================================================
-->
<xsl:template name="addStartEvent">
	<xsl:param name="diagram"/>
	
	<xsl:variable name="startEventActivityId"><xsl:call-template name="getStartEventActivityId"><xsl:with-param name="diagram" select="$diagram"/></xsl:call-template></xsl:variable>
	<xsl:variable name="startEventLabel" select="java:format($msgs, 'StartEvent_label', $diagram/Title)"/>
	<xsl:variable name="laneId"><xsl:call-template name="getDefaultLaneId"><xsl:with-param name="diagram" select="$diagram"/></xsl:call-template></xsl:variable>

	<!-- Get start connections (Input with no LinkedToActivity in this diagrm) and end connections (output with no LinkedToActivity or linked to other diagram) -->
	<xsl:variable name="startConnections" select="$diagram/Activity/Input[count(LinkedToActivity) = 0 or LinkedToActivity = '' or LinkedToDiagram != $diagram/GUID]"/>
	<xsl:variable name="unconnectedStartActivities" select="$diagram/Activity[count(Input) = 0]"/>
	<xsl:variable name="endConnections" select="$diagram/Activity/Output[count(LinkedToActivity) = 0 or LinkedToActivity = '' or LinkedToDiagram != $diagram/GUID]"/>

	<!--  Only add start activity if there are any start connections or unconnected start activities to connect too (for instance in a closed look situation) -->
	<xsl:if test="count($startConnections) > 0 or count($unconnectedStartActivities) > 0">
		<xpdl2:Activity Id="{$startEventActivityId}" xpdExt:Visibility="Private">
			<xsl:call-template name="addLabelAndName"><xsl:with-param name="label" select="$startEventLabel"/></xsl:call-template>
	
			<xpdl2:Event>
				<xpdl2:StartEvent Trigger="None"/>
			</xpdl2:Event>
	
			<!-- Approximate location only (expect auto layout in post-import processing). -->
			<xpdl2:NodeGraphicsInfos>
				<xpdl2:NodeGraphicsInfo BorderColor="0,0,128" FillColor="255,219,74" Height="27.0" LaneId="{$laneId}" Width="27.0">
					<xpdl2:Coordinates XCoordinate="55" YCoordinate="{($laneHeight div 2)}"/>
				</xpdl2:NodeGraphicsInfo>
			</xpdl2:NodeGraphicsInfos>
	
			<xpdl2:Extensions/>
	
			<!-- 
				Associated data that was created for start connections and end connections (even end connection outgoing data is associated with start event
				because the start event defines the whole interface. 
			-->
			
			<xsl:call-template name="addActivityInterfaceData">
				<xsl:with-param name="diagram" select="$diagram"/>
				<xsl:with-param name="incomingConnections" select="$startConnections"/>
				<xsl:with-param name="outgoingConnections" select="$endConnections"/>
			 </xsl:call-template>
		
		</xpdl2:Activity>
	</xsl:if>			
</xsl:template>

<!-- 
===============================================================
Output the end event for the given diagram.
===============================================================
-->
<xsl:template name="addEndEvent">
	<xsl:param name="diagram"/>

	<xsl:variable name="endEventActivityId"><xsl:call-template name="getEndEventActivityId"><xsl:with-param name="diagram" select="$diagram"/></xsl:call-template></xsl:variable>
	<xsl:variable name="endEventLabel" select="java:format($msgs, 'EndEvent_label', $diagram/Title)"/>
	<xsl:variable name="laneId"><xsl:call-template name="getDefaultLaneId"><xsl:with-param name="diagram" select="$diagram"/></xsl:call-template></xsl:variable>

	<xsl:variable name="endConnections" select="$diagram/Activity/Output[count(LinkedToActivity) = 0 or LinkedToActivity = '' or LinkedToDiagram != $diagram/GUID]"/>
	<xsl:variable name="unconnectedEndActivities" select="$diagram/Activity[count(Output) = 0]"/>
	
	<!--  Only output end event if there are specific end connections or unconnected end activities (for instance in a closed look situation). -->
	<xsl:if test="count($endConnections) > 0 or count($unconnectedEndActivities) > 0">
		<xpdl2:Activity Id="{$endEventActivityId}" xpdExt:Visibility="Private">
		<xsl:call-template name="addLabelAndName"><xsl:with-param name="label" select="$endEventLabel"/></xsl:call-template>

		<xpdl2:Event>
			<xpdl2:EndEvent Result="None"/>
		</xpdl2:Event>

		<!-- Approximate location only (expect auto layout in post-import processing). -->
		<xpdl2:NodeGraphicsInfos>
			<xpdl2:NodeGraphicsInfo BorderColor="0,0,128" FillColor="255,219,74" Height="27.0" LaneId="{$laneId}" Width="27.0">
				<xpdl2:Coordinates XCoordinate="{20 + (count($diagram/Activity) * 176) + 140}" YCoordinate="{($laneHeight div 2)}"/>
			</xpdl2:NodeGraphicsInfo>
		</xpdl2:NodeGraphicsInfos>

		<xpdl2:Extensions/>
	</xpdl2:Activity>
	</xsl:if>
	
</xsl:template>

<!-- 
===============================================================
Add interface data associations to the given activity.
  
We will add an associatedParameter for each data created for the label of  
   connection into and out of the given activity.

We will set the associated parameter mode from whether the connection is input, output or both.
===============================================================
-->
<xsl:template name="addActivityInterfaceData">
	<xsl:param name="diagram"/>
	<xsl:param name="incomingConnections"/>
	<xsl:param name="outgoingConnections"/>

	<xsl:if test="count($incomingConnections) > 0 or count($outgoingConnections) > 0">

		<!-- get unique set of start connection labels and end connection labels. -->
		<xsl:variable name="uniqueInOutConnectionLabels" select="java:getUniqueTextElementSet($util, ($incomingConnections/Text | $outgoingConnections/Text))"/>

		<!-- create association for each of the set of unique labels for the start/end connections. -->
		<xpdExt:AssociatedParameters DisableImplicitAssociation="false">
			<xsl:for-each select="$uniqueInOutConnectionLabels">
				<xsl:variable name="connectionLabel" select="."/>

				<!-- Calculate the parameter mode depending on whether the connection label from which data is created  appears in start and / or end connections list -->
				<xsl:variable name="paramMode">
					<xsl:choose>
						<xsl:when test="count($incomingConnections[java:isEquivalentDataName($util, Text/text(), $connectionLabel)]) > 0 and count($outgoingConnections[java:isEquivalentDataName($util, Text/text(), $connectionLabel)]) > 0">
							<xsl:text>INOUT</xsl:text>
						</xsl:when>
						<xsl:when test="count($incomingConnections[java:isEquivalentDataName($util, Text/text(), $connectionLabel)]) > 0">
							<xsl:text>IN</xsl:text>
						</xsl:when>
						<xsl:when test="count($outgoingConnections[java:isEquivalentDataName($util, Text/text(), $connectionLabel)]) > 0">
							<xsl:text>OUT</xsl:text>
						</xsl:when>
					</xsl:choose>
				</xsl:variable>
		
				<xsl:if test="$paramMode != ''">
					<!-- Make sure we consiustently use the character case of dat aname by always using first occurence of connection -->
					<xsl:variable name="defactoConnectionLabel">
						<xsl:call-template name="getDefactoConnectionLabel">
							<xsl:with-param name="diagram" select="$diagram"/>
							<xsl:with-param name="anyConnectionLabel" select="$connectionLabel"/>
						</xsl:call-template>
					</xsl:variable>
					
					<xsl:variable name="dataName" select="java:getTokenName($util, $defactoConnectionLabel)"/>

					<xpdExt:AssociatedParameter FormalParam="{$dataName}" Mode="{$paramMode}" Mandatory="true"/>
				</xsl:if>
				
			</xsl:for-each>
			
		</xpdExt:AssociatedParameters>
	</xsl:if>
	
</xsl:template>

<!-- 
===============================================================
Output a xpdl2:FormalParameter / DataField for the incoming-data / outgoing-data
	that is implied by the text on Nimbus diagram connections.
===============================================================
-->
<xsl:template name="addProcessData">
	<xsl:param name="diagram"/>

	<!-- Reduce the input output connections labels to a unique named set and create data for each one. -->
	<xsl:variable name="uniqueConnectionLabels" select="java:getUniqueTextElementSet($util, ($diagram/Activity/Input/Text | $diagram/Activity/Output/Text))"/>

	<xsl:variable name="processId"><xsl:call-template name="getProcessId"><xsl:with-param name="diagramId" select="$diagram/GUID"/></xsl:call-template></xsl:variable>

	<!-- 
		Create formal parameters for each unique label that appears on a begin / end connection 
	-->
	<xpdl2:FormalParameters>
		<xsl:for-each select="$uniqueConnectionLabels">
			<!-- Ensure that when referencing connection label we ALWAYS pick the first one with the equivalent label in this whole diagram (because we will be creating references for assoc parameters etc so need to be consistent) -->
			<xsl:variable name="connectionLabel" select="."/>
			
			<!-- A start connection is an Input who's LinkedToAcivity is not defined or not linked to an activity in this diagram. -->
			<xsl:variable name="startConnectionsForLabel" select="$diagram/Activity/Input[java:isEquivalentDataName($util, Text/text(), $connectionLabel) and 
																(count(LinkedToActivity) = 0 or LinkedToActivity = '' or LinkedToDiagram != $diagram/GUID)]"/>

			<!-- An end connection is an Output who's LinkedToAcivity is not defined or not linekd to activity in this diagram. -->
			<xsl:variable name="endConnectionsForLabel" select="$diagram/Activity/Output[java:isEquivalentDataName($util, Text/text(), $connectionLabel) and 
																(count(LinkedToActivity) = 0 or LinkedToActivity = '' or LinkedToDiagram != $diagram/GUID)]"/>
			
			<xsl:variable name="paramMode">
				<xsl:choose>
					<xsl:when test="count($startConnectionsForLabel) > 0 and count($endConnectionsForLabel) > 0">
						<xsl:text>INOUT</xsl:text>
					</xsl:when>
					<xsl:when test="count($startConnectionsForLabel) > 0">
						<xsl:text>IN</xsl:text>
					</xsl:when>
					<xsl:when test="count($endConnectionsForLabel) > 0">
						<xsl:text>OUT</xsl:text>
					</xsl:when>
				</xsl:choose>
			</xsl:variable>
			
			<xsl:if test="$paramMode != ''">
				<!-- Make sure we consiustently use the character case of dat aname by always using first occurence of connection -->
				<xsl:variable name="defactoConnectionLabel">
					<xsl:call-template name="getDefactoConnectionLabel">
						<xsl:with-param name="diagram" select="$diagram"/>
						<xsl:with-param name="anyConnectionLabel" select="$connectionLabel"/>
					</xsl:call-template>
				</xsl:variable>
				
				<xsl:variable name="dataName" select="java:getTokenName($util, $defactoConnectionLabel)"/>
				
				<xpdl2:FormalParameter Id="{java:createUniqueId($util)}" ReadOnly="false" Mode="{$paramMode}" Required="true">
					<xsl:call-template name="addLabelAndName"><xsl:with-param name="label" select="$defactoConnectionLabel"/></xsl:call-template>
					
					<xpdl2:DataType>
						<xpdl2:BasicType Type="STRING"/>
					</xpdl2:DataType>
					
					<!-- Add commentaries to description. -->
					<xsl:variable name="commentaries" select="$startConnectionsForLabel/Commentary
																						| $diagram/Activity/Output[java:isEquivalentDataName($util, Text/text(), $connectionLabel)]/Commentary"/>
					<xsl:call-template name="addDescriptionFromCommentaries"><xsl:with-param name="commentaries" select="$commentaries"/></xsl:call-template>
					
				</xpdl2:FormalParameter>
				
			</xsl:if>
		
		</xsl:for-each>
	</xpdl2:FormalParameters>

	<!-- 
		Create Data fields for each unique label that appears on a line between activities.
	-->
	<xpdl2:DataFields>
		<xsl:for-each select="$uniqueConnectionLabels">
			<xsl:variable name="connectionLabel" select="."/>
			
			<!-- A start connection is an Input who's LinkedToAcivity is not defined or not linked to an activity in this diagram. -->
			<xsl:variable name="startConnectionsForLabel" select="$diagram/Activity/Input[java:isEquivalentDataName($util, Text/text(), $connectionLabel) and 
																(count(LinkedToActivity) = 0 or LinkedToActivity = '' or LinkedToDiagram != $diagram/GUID)]"/>

			<!-- An end connection is an Output who's LinkedToAcivity is not defined or not linekd to activity in this diagram. -->
			<xsl:variable name="endConnectionsForLabel" select="$diagram/Activity/Output[java:isEquivalentDataName($util, Text/text(), $connectionLabel) and 
																(count(LinkedToActivity) = 0 or LinkedToActivity = '' or LinkedToDiagram != $diagram/GUID)]"/>
			
			<xsl:if test="count($startConnectionsForLabel) = 0 and count($endConnectionsForLabel) = 0">

				<!-- Make sure we consiustently use the character case of dat aname by always using first occurence of connection -->
				<xsl:variable name="defactoConnectionLabel">
					<xsl:call-template name="getDefactoConnectionLabel">
						<xsl:with-param name="diagram" select="$diagram"/>
						<xsl:with-param name="anyConnectionLabel" select="$connectionLabel"/>
					</xsl:call-template>
				</xsl:variable>

				<xsl:variable name="dataName" select="java:getTokenName($util, $defactoConnectionLabel)"/>

				<xpdl2:DataField Id="{java:createUniqueId($util)}" ReadOnly="false">
					<xsl:call-template name="addLabelAndName"><xsl:with-param name="label" select="$defactoConnectionLabel"/></xsl:call-template>
					
					<xpdl2:DataType>
						<xpdl2:BasicType Type="STRING"/>
					</xpdl2:DataType>
					<xpdl2:InitialValue/>
					
					<!-- Add commentaries to description. -->
					<xsl:variable name="commentaries" select="$startConnectionsForLabel/Commentary
																						| $diagram/Activity/Output[java:isEquivalentDataName($util, Text/text(), $connectionLabel)]/Commentary"/>
					<xsl:call-template name="addDescriptionFromCommentaries"><xsl:with-param name="commentaries" select="$commentaries"/></xsl:call-template>

				</xpdl2:DataField>

			</xsl:if>
			
		</xsl:for-each>
	</xpdl2:DataFields>
		
</xsl:template>

<!-- 
===============================================================
Output a xpdl2:Description for all the given commentaries
===============================================================
-->
<xsl:template name="addDescriptionFromCommentaries">
	<xsl:param name="commentaries"/>
	
	<xsl:if test="$commentaries/text() != ''">
		<xpdl2:Description>
			<xsl:for-each select="$commentaries[text() != '']">
				<xsl:value-of select="./text()"/><xsl:text>&#10;</xsl:text>

				<xsl:if test="count($commentaries[text() != '']) > 1">
					<xsl:text>------------------------------------------------------------------------------------------------------------------------</xsl:text><xsl:text>&#10;</xsl:text><xsl:text>&#10;</xsl:text>
				</xsl:if>

			</xsl:for-each>
		</xpdl2:Description>
	</xsl:if>
	
</xsl:template>

<!-- 
===============================================================
Output a xpdl2:Pool for each diagram.
===============================================================
-->
<xsl:template name="addPools">
	<xsl:param name="diagrams"/>
	
	<xpdl2:Pools>
		
		<!-- Create a Pool for each diagram. -->
		<xsl:for-each select="$diagrams">
			<xsl:variable name="diagram" select="."/>
			<xsl:variable name="processId"><xsl:call-template name="getProcessId"><xsl:with-param name="diagramId" select="$diagram/GUID"/></xsl:call-template></xsl:variable>

			<xpdl2:Pool Id="{$processId}_Pool" BoundaryVisible="true" Process="{$processId}">
				<xsl:call-template name="addLabelAndName"><xsl:with-param name="label" select="java:get($msgs, 'DefaultPool_label')"/></xsl:call-template>
			
				<xpdl2:Lanes>
					
					<xsl:if test="$oneLanePerResource = 'yes'">
						<!-- Get the unique set (by name) of activity resources (i.e. the participant in BPMN terms). -->
						<xsl:variable name="uniqueResources" select="java:getUniqueTextElementSet($util, $diagram/Activity/Resource)"/>

						<!-- Create a lane for each unique resource in lane. -->
						<xsl:for-each select="$uniqueResources">
							<xsl:variable name="laneId"><xsl:call-template name="getLaneId"><xsl:with-param name="diagram" select="$diagram"/><xsl:with-param name="resourceName" select="./text()"/></xsl:call-template></xsl:variable>
	
							<xpdl2:Lane Id="{$laneId}" >
	
								<xsl:call-template name="addLabelAndName"><xsl:with-param name="label" select="./text()"/></xsl:call-template>
	
								<xpdl2:NodeGraphicsInfos>
									<xpdl2:NodeGraphicsInfo Height="{$laneHeight}"/>
								</xpdl2:NodeGraphicsInfos>
						
							</xpdl2:Lane>	
						</xsl:for-each>
					</xsl:if>

					<!-- Add a single default lane for any activity thatdoes not have a resource set.
						TODO Ensure we remove this lane during post-processing if no activities assigned to it. -->
					<xsl:variable name="defaultLaneId"><xsl:call-template name="getDefaultLaneId"><xsl:with-param name="diagram" select="$diagram"/></xsl:call-template></xsl:variable>

					<xpdl2:Lane Id="{$defaultLaneId}">
				
						<xsl:call-template name="addLabelAndName"><xsl:with-param name="label" select="java:get($msgs, 'DefaultLane_label')"/></xsl:call-template>

						<xpdl2:NodeGraphicsInfos>
							<xpdl2:NodeGraphicsInfo Height="{$laneHeight}"/>
						</xpdl2:NodeGraphicsInfos>
					</xpdl2:Lane>
					
				</xpdl2:Lanes>

				<xpdl2:NodeGraphicsInfos>
					<xpdl2:NodeGraphicsInfo BorderColor="0,0,128" FillColor="223,179,0" IsVisible="true"/>
				</xpdl2:NodeGraphicsInfos>
			</xpdl2:Pool>
			
		</xsl:for-each>
	</xpdl2:Pools>
	
</xsl:template>

<!-- 
===============================================================
Output a xpdl2:Participant for each uniquely named activity resource in diagram.
===============================================================
-->
<xsl:template name="addParticipants">
	<xsl:param name="diagrams"/>
	
	<xpdl2:Participants>
		<!-- Get the unique set (by name) of activity resources (i.e. the participant in BPMN terms) from ALL diagrams -->
		<xsl:variable name="uniqueResources" select="java:getUniqueTextElementSet($util, $diagrams/Activity/Resource)"/>
		
		<!-- Create participant for each unique resource. -->
		<xsl:for-each select="$uniqueResources">
			<xsl:variable name="resource" select="."/>

			<xpdl2:Participant>

				<xsl:attribute name="Id">
					<xsl:call-template name="getParticipantId"><xsl:with-param name="resourceName" select="$resource/text()"/></xsl:call-template>
				</xsl:attribute>

				<xsl:call-template name="addLabelAndName"><xsl:with-param name="label" select="$resource/text()"/></xsl:call-template>

				<xpdl2:ParticipantType Type="ROLE"/>

			</xpdl2:Participant>
		</xsl:for-each>
		
	</xpdl2:Participants>

</xsl:template>

<!-- 
===============================================================
Output the xpdl2:PackageHeader
===============================================================
-->
<xsl:template name="addPackageHeader">
	<!-- TODO If Nimbus change to multi-diagram per package then we should change this to use  whatever package level info is available. -->
	<xsl:param name="diagram"/>
	
	<xpdl2:PackageHeader xpdExt:Language="en_GB">
		<xpdl2:XPDLVersion>2.1</xpdl2:XPDLVersion>
		<xpdl2:Vendor><xsl:value-of select="java:get($msgs, 'Vendor_label')"/></xpdl2:Vendor>
		<xpdl2:Created><xsl:value-of select="java:getCurrentDate($util)"/></xpdl2:Created>
		<xpdl2:Description><xsl:value-of select="$diagram/Title"/></xpdl2:Description>
		<xpdl2:Documentation></xpdl2:Documentation>
		<xpdl2:CostUnit>GBP</xpdl2:CostUnit>
	</xpdl2:PackageHeader>

	<xpdl2:RedefinableHeader PublicationStatus="UNDER_REVISION">
		<xpdl2:Author><xsl:value-of select="$diagram/Author"/></xpdl2:Author>
		<xpdl2:Version>1.0.0.qualifier</xpdl2:Version>
	</xpdl2:RedefinableHeader>

</xsl:template>


<!-- 
===============================================================
Output the target FormatVersion (13 = Business Studio v3.5.10)
===============================================================
-->
<xsl:template name="addPackageExtendedAttributes">
	<xpdl2:ExtendedAttributes>
		<xpdl2:ExtendedAttribute Name="CreatedBy" Value="{java:get($msgs, 'CreatedBy_label')}"/>
		<xpdl2:ExtendedAttribute Name="FormatVersion" Value="13"/>
	</xpdl2:ExtendedAttributes>
</xsl:template>


<!-- 
===============================================================
Output the WorkflowProcess/Id for the given Nimbus Diagram
===============================================================
-->
<xsl:template name="getProcessId">
	<xsl:param name="diagramId"/>
	
	<!--  This method now just uses nimbus diagram GUID (for fear of overrunning some max length in runtime database -->
	<xsl:value-of select="$diagramId"/>
</xsl:template>

<!-- 
===============================================================
Output the lane id for the given activity.
===============================================================
-->
<xsl:template name="getLaneIdForActivity">
	<xsl:param name="activity"/>
	
	<!-- Place the activity in the appropriate lane for the resource (or defaulty if no resource or multiple resources. -->
	<xsl:variable name="resources" select="$activity/Resource"/>

	<xsl:choose>
		<xsl:when test="count($resources) = 1 and $oneLanePerResource = 'yes'">
			<xsl:call-template name="getLaneId">
				<xsl:with-param name="diagram" select="$activity/ancestor::Diagram"/>
				<xsl:with-param name="resourceName" select="$resources[1]/text()"/>
			</xsl:call-template>
		</xsl:when>
		<xsl:otherwise>
			<xsl:call-template name="getDefaultLaneId"><xsl:with-param name="diagram" select="$activity/ancestor::Diagram"/></xsl:call-template>
		</xsl:otherwise>
	</xsl:choose>

</xsl:template>

<!-- 
===============================================================
Output the unique lane id for the given resource (participant) in the given diagram (process)
===============================================================
-->
<xsl:template name="getLaneId">
	<xsl:param name="diagram"/>
	<xsl:param name="resourceName"/>
	
	<xsl:call-template name="getProcessId"><xsl:with-param name="diagramId" select="$diagram/GUID"/></xsl:call-template>
	<xsl:text>_Lane_</xsl:text>
	<xsl:value-of select="java:getTokenName($util, $resourceName)"/>
	
</xsl:template>

<!-- 
===============================================================
Output the Defaultlane id for the given resource (participant) in the given diagram (process)
===============================================================
-->
<xsl:template name="getDefaultLaneId">
	<xsl:param name="diagram"/>

	<xsl:call-template name="getLaneId"><xsl:with-param name="diagram" select="$diagram"/><xsl:with-param name="resourceName" select="'__DEFAULT'"/></xsl:call-template>

</xsl:template>

<!-- 
===============================================================
Output the unique activity id for the given activity
===============================================================
-->
<xsl:template name="getActivityId">
	<xsl:param name="activity"/>

	<xsl:variable name="diagram" select="$activity/ancestor::Diagram"/>

	<xsl:value-of select="java:getActivityId($util, $diagram/GUID, $activity/ID)"/>
	
</xsl:template>

<!-- 
===============================================================
Output the Start event activity id for the given diagram.
===============================================================
-->
<xsl:template name="getStartEventActivityId">
	<xsl:param name="diagram"/>

	<xsl:value-of select="java:getActivityId($util, $diagram/GUID, '_$START$_')"/>
	
</xsl:template>

<!-- 
===============================================================
Output the End event activity id for the given diagram.
===============================================================
-->
<xsl:template name="getEndEventActivityId">
	<xsl:param name="diagram"/>

	<xsl:value-of select="java:getActivityId($util, $diagram/GUID, '_$END$_')"/>
	
</xsl:template>

<!-- 
===============================================================
Output target task type string for given Nimbus activity.
	- SubFlow
	- None
===============================================================
-->
<xsl:template name="getTaskType">
	<xsl:param name="activity"/>
	
		<xsl:choose>
			<xsl:when test="$activity/DrillDown/DrillDownGUID != ''">
				<xsl:text>SubFlow</xsl:text>
			</xsl:when>

			<!-- XPD-6565: If new TaskType element available in source then prefer it. -->
			<xsl:when test="$activity/Automation/TaskType != ''">
				<xsl:value-of select="$activity/Automation/TaskType"></xsl:value-of>
			</xsl:when>
			
			<xsl:when test="count($activity/Resource) > 0">
				<xsl:text>User</xsl:text>
			</xsl:when>
			
			<!-- Default is always none -->
			<xsl:otherwise>
				<xsl:text>None</xsl:text>
			</xsl:otherwise>
		</xsl:choose>

</xsl:template>

<!-- 
===============================================================
Output the unique participant id for the given resource (participant) 
===============================================================
-->
<xsl:template name="getParticipantId">
	<xsl:param name="resourceName"/>

	<xsl:variable name="resourceId" select="java:getTokenName($util, $resourceName)"/>
	
	<xsl:value-of select="java:getParticipantId($util, $resourceId)"/>
	
</xsl:template>

<!-- 
===============================================================
Get unique process name for diagram, if more than one diagram has same name then 
suffix with the diagram level.
===============================================================
-->
<xsl:template name="getUniqueProcessLabel"> 
	<xsl:param name="diagram"/>
	
	<!-- Check if name in it's own right is unique. -->
	<xsl:variable name="name" select="java:normaliseSpace($util, $diagram/Title/text())"/>
	
	<xsl:choose>
		<xsl:when test="count($diagram/../Diagram[java:normaliseSpace($util, $diagram/Title/text()) = $name]) > 1">
			<xsl:value-of select="$name"/><xsl:text> (</xsl:text><xsl:value-of select="$diagram/Level/text()"/><xsl:text>)</xsl:text>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="$name"/>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!-- 
===============================================================
Get the defacto connection label for data name  for the given Input/Output connection name.

Because we treat all connections with the same tokenised-name-for-label as being the same process data item 
we must ensure that when we create the data and/or reference it that we consistently use the same name.

We do this by always using the label of the __first__ diagram connection with a label 
with the equivalent token name for label.  
===============================================================
-->
<xsl:template name="getDefactoConnectionLabel"> 
	<xsl:param name="diagram"/>
	<xsl:param name="anyConnectionLabel"/>

	<xsl:variable name="matchingConnections" select="$diagram/Activity/Input[java:isEquivalentDataName($util, $anyConnectionLabel, Text/text())]
																				 | $diagram/Activity/Output[java:isEquivalentDataName($util, $anyConnectionLabel, Text/text())]"/>

	<!-- output the FIRST of the set of connections that match given data name (ignoring case and white space. -->
	<xsl:value-of select="$matchingConnections[1]/Text/text()"/>
	
</xsl:template>

<!-- 
===============================================================
Output the unique lane id for the given resource (participant) in the given diagram (process)
===============================================================
-->
<xsl:template name="addLabelAndName"> 
	<xsl:param name="label"/>
	
	<xsl:attribute name="xpdExt:DisplayName"><xsl:value-of select="java:normaliseSpace($util, $label)"/></xsl:attribute>
	<xsl:attribute name="Name"><xsl:value-of select="java:getTokenName($util, $label)"/></xsl:attribute>
</xsl:template>

<!-- 
===============================================================
Output a debug message to Studio error  log if debug logging is enabeld there.
===============================================================
-->
<xsl:template name="debug">
	<xsl:param name="message"/>

	<xsl:value-of select="java:debug($util, $message)"/>
</xsl:template>

<!-- 
===============================================================
Default template - do nothing - do not want to pick up anything from Nimbus model by default.
===============================================================
-->
<xsl:template match="@* | node()">
</xsl:template>


</xsl:stylesheet>
