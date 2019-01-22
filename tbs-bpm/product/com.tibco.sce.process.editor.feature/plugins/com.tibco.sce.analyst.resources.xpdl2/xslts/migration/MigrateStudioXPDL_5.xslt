<?xml version="1.0" encoding="UTF-8"?>
<!--
	===================================================================================================================
	XSLT:       MigrateStudioXPDL_5.xslt
	
	DESCRIPTION:
	This xslt will updgrade a xpdl of format version 5 to format version 6.  
	
	NOTE: that the FormatVersion between Studio v3.1 jumps from version 4 to version 6 in one go. 
			This is purely to allow the MigrateStudioXPDL_4.xslt migration to deal ONLY with changing the xpdl2 namespace from alpha to 2.1.
			As this is not a very natural thing to do in xslt it is reasonably easy to mess it up. 

			So having dealt with the tricky namespace change, all the actual migration for XPDL 2.0 elements to 2.1  equivalents  can be done much easier here.

	===================================================================================================================
-->

<xsl:stylesheet version="1.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xpdExt="http://www.tibco.com/XPD/xpdExtension1.0.0"
	xmlns:ej="http://www.tibco.com/XPD/EAIJava1.0.0"
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
		<xsl:apply-templates />
	</xsl:template>

	<xsl:template match="xpdl2:ExtendedAttribute[@Name = 'FormatVersion']">
		<xsl:element name="xpdl2:ExtendedAttribute">
			<xsl:attribute name="Name">FormatVersion</xsl:attribute>
			<xsl:attribute name="Value">6</xsl:attribute>
		</xsl:element>
	</xsl:template>

    <!--
        ===============================================================================
        Named elements - create new display name extension attribute to match.
        ===============================================================================
    -->
    <xsl:template match="@Name">
		<xsl:variable name="elementName" select="local-name(..)"/>
		<xsl:variable name="value" select="."/>

		<xsl:choose>
			<xsl:when test="$elementName = 'Package' or $elementName = 'WorkflowProcess'
						or $elementName = 'Activity' or $elementName = 'ActivitySet' or $elementName = 'Application' or $elementName = 'Artifact'
						or $elementName = 'Association' or $elementName = 'DataObject' or $elementName = 'StartMethod' or $elementName = 'IntermediateMethod'
						or $elementName = 'Lane' or $elementName = 'MessageFlow' or $elementName = 'Package' or $elementName = 'Participant'
						or $elementName = 'Pool' or $elementName = 'Process' or $elementName = 'ProcessInterface' or $elementName = 'DataField'
						or $elementName = 'FormalParameter' or $elementName = 'Transition' or $elementName = 'TypeDeclaration' ">
	
				<!-- If something (like import from other xpdl has added an exte4nded attribute "DisplayName" then use its value instaed of just copying Name -->
				<xsl:variable name="extAttrDisplayName" select="../xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'DisplayName']/@Value"/>
				
				<xsl:attribute name="xpdExt:DisplayName">
					<xsl:choose>
						<xsl:when test="../@xpdExt:DisplayName != ''">
							<xsl:value-of select="../@xpdExt:DisplayName"/>
						</xsl:when>
						<xsl:when test="$extAttrDisplayName != ''">
							<xsl:value-of select="$extAttrDisplayName"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="."/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:attribute>
	
				<xsl:choose>																
					<!-- 
						If running in java mode (i.e. not debugging in xmlspy) then convert the current @Name attribute to a token name
							EXCEPT FOR Participant (which do not have the Token Name restrictions) 
							and DataField/FormalParameter because the are referenced by name and we cannot afford just to change the name without doing FULL refactor of ref's
					-->
					<xsl:when test="function-available('java:com.tibco.xpd.xpdl2.edit.util.XsltUtils.labelNameToTokenName') 
											and $elementName != 'Participant' 
											and $elementName != 'DataField' 
											and $elementName != 'FormalParameter' 
											">
						<xsl:attribute name="Name"><xsl:value-of select="java:com.tibco.xpd.xpdl2.edit.util.XsltUtils.labelNameToTokenName(.)"/></xsl:attribute>

					</xsl:when>								
					<xsl:otherwise>
						<xsl:copy/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:otherwise>
				<xsl:copy/>
			</xsl:otherwise>
		</xsl:choose>
		
    </xsl:template>

    <!--
        ===============================================================================
        Don't carry thru DisplayName extrended attributes 
        ===============================================================================
    -->
    
	<xsl:template match="xpdl2:ExtendedAttribute[@Name = 'DisplayName']">
	</xsl:template>

    <!--
        ===============================================================================
        Add TransitionRestrictions to Activity with multiple in / out.
        ===============================================================================
    -->
	<xsl:template match="xpdl2:Activities">
	
		<xsl:variable name="transitions" select="../xpdl2:Transitions/xpdl2:Transition"/>
		
		<xpdl2:Activities>

			<xsl:for-each select="xpdl2:Activity">
				
				<xpdl2:Activity>
					<!-- 
					  Apply templates to everything that was in xpdl 2.0 before trans restrictions. 
					-->
					<xsl:apply-templates select="@*"/>

					<xsl:apply-templates select="xpdl2:Description | xpdl2:Limit | xpdl2:Route | xpdl2:Implementation | xpdl2:BlockActivity
														 | xpdl2:Event | xpdl2:Transaction | xpdl2:Performers | xpdl2:Performer | xpdl2:Priority
														 | xpdl2:Deadline | xpdl2:SimulationInformation | xpdl2:Icon | xpdl2:Documentation"/>

					<!-- 
					  If there are multiple in or out transitions (sequence flow) then output a transition restrictions element. 
					-->
					<xsl:variable name="id" select="@Id"/>
					<xsl:variable name="inTrans" select="$transitions[@To = $id]"/>
					<xsl:variable name="outTrans" select="$transitions[@From = $id]"/>
					
					<xsl:if test="count($inTrans) > 1 or count($outTrans) > 1">
						<!-- Create transition restrictions -->
						<xpdl2:TransitionRestrictions>
							<xpdl2:TransitionRestriction>
								<xsl:if test="count($inTrans) > 1">
									<!-- Multiple Incoming... create the join -->
									<xpdl2:Join>
										<xsl:call-template name="addJoinSplitTypeAttributes">
											<xsl:with-param name="splitOrJoin" select="'join'"/>
										</xsl:call-template>
									</xpdl2:Join>
								</xsl:if>

								<xsl:if test="count($outTrans) > 1">
									<!-- Multiple outgoing... create the split -->
									<xpdl2:Split>
										<xsl:call-template name="addJoinSplitTypeAttributes">
											<xsl:with-param name="splitOrJoin" select="'split'"/>
										</xsl:call-template>
										
										<xpdl2:TransitionRefs>
											<xsl:for-each select="$outTrans">
												<xpdl2:TransitionRef>
													<xsl:attribute name="Id"><xsl:value-of select="@Id"/></xsl:attribute>
												</xpdl2:TransitionRef>
											</xsl:for-each>
										</xpdl2:TransitionRefs>
									</xpdl2:Split>
								</xsl:if>
							
							</xpdl2:TransitionRestriction>
						</xpdl2:TransitionRestrictions>

					</xsl:if>
				
					<!-- 
						Finally output everything that might come after the TransitionRestriction element 
					-->
					<xsl:apply-templates select="xpdl2:ExtendedAttributes | xpdl2:DataFields | xpdl2:InputSets | xpdl2:OutputSets | xpdl2:IORules
														 | xpdl2:Loop | xpdl2:Assignments | xpdl2:Object | xpdl2:NodeGraphicsInfos | xpdl2:Extensions"/>

					<xsl:for-each select="*">
						<xsl:if test="not(starts-with(name(), 'xpdl2:'))">
							<xsl:apply-templates select="."/>
						</xsl:if>
					</xsl:for-each>

				</xpdl2:Activity>
			</xsl:for-each>
			
		</xpdl2:Activities>	
		
	</xsl:template>

    <!--
        ===============================================================================
        Add type attributes to the Join or Split we're currently outputting for the current activity
        ===============================================================================
    -->
	<xsl:template name="addJoinSplitTypeAttributes">
		<xsl:param name="splitOrJoin"/>
		
		<xsl:choose>
			<xsl:when test="xpdl2:Route/@GatewayType = 'XOR'">
				<xsl:attribute name="Type">Exclusive</xsl:attribute>
				<xsl:choose>
					<xsl:when test="xpdl2:Route/@Instantiate = 'true'">
						<xsl:attribute name="ExclusiveType">Event</xsl:attribute>
					</xsl:when>
					<xsl:otherwise>
						<xsl:attribute name="ExclusiveType">Data</xsl:attribute>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>

			<xsl:when test="xpdl2:Route/@GatewayType = 'OR'">
				<xsl:attribute name="Type">Inclusive</xsl:attribute>
			</xsl:when>
			
			<xsl:when test="xpdl2:Route/@GatewayType = 'AND'">
				<xsl:attribute name="Type">Parallel</xsl:attribute>
			</xsl:when>
			
			<xsl:when test="xpdl2:Route">
				<xsl:attribute name="Type"><xsl:value-of select="xpdl2:Route/@GatewayType"/></xsl:attribute>
			</xsl:when>
			
			<xsl:otherwise>
				<!-- Non-gateway activities are always XOR Exclusive Joins -->
				<xsl:choose>
					<xsl:when test="$splitOrJoin = 'join'">
						<xsl:attribute name="Type">Exclusive</xsl:attribute>
						<xsl:attribute name="ExclusiveType">Data</xsl:attribute>
					</xsl:when>
					<xsl:otherwise>
						<xsl:attribute name="Type">Inclusive</xsl:attribute>
					</xsl:otherwise>
				</xsl:choose>				
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

    <!--
        ===============================================================================
        Add read-only flag to read only iProcess system fields.
        ===============================================================================
    -->
    <xsl:template match="xpdl2:DataField">
		<xsl:copy>
			<xsl:apply-templates select="@*"/>

			<xsl:if test="@Name = 'SW_CASENUM' or @Name = 'SW_CASEREF' or @Name = 'SW_HOSTNAME' or 
									@Name = 'SW_NODENAME' or @Name = 'SW_PRODESC' or @Name = 'SW_PRONAME' or 
									@Name = 'SW_QRETRYCOUNT' or @Name = 'SW_STEPDESC' or @Name = 'SW_STEPNAME' ">
					<xsl:attribute name="ReadOnly">true</xsl:attribute>
			</xsl:if>

			<xsl:apply-templates select="*"/>
		</xsl:copy>
		
    </xsl:template>
    

    <!--
        ===============================================================================
        lowercase IsArray value
        ===============================================================================
    -->
    <xsl:template match="xpdl2:DataField/@IsArray">
		<xsl:attribute name="IsArray">
			<xsl:choose>
				<xsl:when test=". = 'TRUE'">true</xsl:when>
				<xsl:otherwise>false</xsl:otherwise>
			</xsl:choose>
		</xsl:attribute>
    </xsl:template>

    <xsl:template match="xpdl2:FormalParameter/@IsArray">
		<xsl:attribute name="IsArray">
			<xsl:choose>
				<xsl:when test=". = 'TRUE'">true</xsl:when>
				<xsl:otherwise>false</xsl:otherwise>
			</xsl:choose>
		</xsl:attribute>
    </xsl:template>



    <!--
        ===============================================================================
        Rename Event Trigger type Rule -> Conditional
        ===============================================================================
    -->
    <xsl:template match="xpdl2:StartEvent[@Trigger='Rule']/@Trigger">
		<xsl:attribute name="Trigger">Conditional</xsl:attribute>
	</xsl:template>

    <xsl:template match="xpdl2:IntermediateEvent[@Trigger='Rule']/@Trigger">
		<xsl:attribute name="Trigger">Conditional</xsl:attribute>
	</xsl:template>

    <!--
        ===============================================================================
        Rename StartEvent/TriggerRule and IntermediateEvent/TriggerRule -> TriggerConditional
        ===============================================================================
    -->
	<xsl:template match="xpdl2:StartEvent/xpdl2:TriggerRule">
		<xpdl2:TriggerConditional>
			<xsl:if test="@RuleName != ''">
				<xsl:attribute name="ConditionName"><xsl:value-of select="@RuleName"/></xsl:attribute>
			</xsl:if>
			
			<xsl:apply-templates select="node()"/>
			
		</xpdl2:TriggerConditional>
	</xsl:template>
	
	<xsl:template match="xpdl2:IntermediateEvent/xpdl2:TriggerRule">
		<xpdl2:TriggerConditional>
			<xsl:if test="@RuleName != ''">
				<xsl:attribute name="ConditionName"><xsl:value-of select="@RuleName"/></xsl:attribute>
			</xsl:if>
			
			<xsl:apply-templates select="node()"/>
			
		</xpdl2:TriggerConditional>
	</xsl:template>

	
	
    <!--
        ===============================================================================
        Rename EndEvent/ResultCompensation and IntermediateEvent/ResultCompensation -> TriggerResultCompensation
		AND add xpdExt:CatchThrow
        ===============================================================================
    -->
	<xsl:template match="xpdl2:IntermediateEvent/xpdl2:ResultCompensation">
		<xpdl2:TriggerResultCompensation>
			<xsl:attribute name="xpdExt:CatchThrow">
				<xsl:choose>
					<xsl:when test="../@Target != ''">CATCH</xsl:when>
					<xsl:otherwise>THROW</xsl:otherwise>
				</xsl:choose>
			</xsl:attribute>

			<xsl:apply-templates select="@* | node()"/>
			
		</xpdl2:TriggerResultCompensation>
	</xsl:template>

	<xsl:template match="xpdl2:EndEvent/xpdl2:ResultCompensation">
		<xpdl2:TriggerResultCompensation xpdExt:CatchThrow="THROW">
			<xsl:apply-templates select="@* | node()"/>
		</xpdl2:TriggerResultCompensation>
	</xsl:template>
	
    <!--
        ===============================================================================
		For TriggerMultiple , TriggerIntermediateMultiple and ResultMultiple add xpdExt:CatchThrow
        ===============================================================================
    -->
    <xsl:template match="xpdl2:StartEvent/xpdl2:TriggerMultiple">
		<xpdl2:TriggerMultiple xpdExt:CatchThrow="CATCH">
			<xsl:apply-templates select="@* | node()"/>
		</xpdl2:TriggerMultiple>
    </xsl:template>
	
    <xsl:template match="xpdl2:IntermediateEvent/xpdl2:TriggerIntermediateMultiple">
		<xpdl2:TriggerIntermediateMultiple  xpdExt:CatchThrow="CATCH">
			<xsl:apply-templates select="@* | node()"/>
		</xpdl2:TriggerIntermediateMultiple>
    </xsl:template>

    <xsl:template match="xpdl2:EndEvent/xpdl2:ResultMultiple">
		<xpdl2:ResultMultiple  xpdExt:CatchThrow="THROW">
			<xsl:apply-templates select="@* | node()"/>
		</xpdl2:ResultMultiple>
    </xsl:template>
	
    <!--
        ===============================================================================
		Change Start / End Event type link to type None (no support fr start / end link in BPMN 1.2
        ===============================================================================
    -->
    <xsl:template match="xpdl2:StartEvent/xpdl2:TriggerResultLink">
    </xsl:template>

    <xsl:template match="xpdl2:StartEvent[@Trigger = 'Link']/@Trigger">
		<xsl:attribute name="Trigger">None</xsl:attribute>
    </xsl:template>

    <xsl:template match="xpdl2:EndEvent/xpdl2:TriggerResultLink">
    </xsl:template>

    <xsl:template match="xpdl2:EndEvent[@Result = 'Link']/@Result">
		<xsl:attribute name="Result">None</xsl:attribute>
    </xsl:template>

	 <!--
        ===============================================================================
		Add Catch / Throw attribute to intermediate link events.
        ===============================================================================
    -->
    <xsl:template match="xpdl2:IntermediateEvent/xpdl2:TriggerResultLink">
		<xpdl2:TriggerResultLink>
			<xsl:choose>
				<!-- If it has a LinkId != "0" then it's definitely a source of a goto.
-->
				<xsl:when test="@LinkId and @LinkId != '0'">
					<xsl:attribute name="Name"><xsl:value-of select="@LinkId"/></xsl:attribute>
					<xsl:attribute name="CatchThrow">THROW</xsl:attribute>
				</xsl:when>
				<xsl:otherwise>
					<!-- Otherwise, if we don't have incoming transitions then it's a catch, otherwise treat it as a throw.
-->
					<xsl:variable name="trans" select="ancestor::xpdl2:WorkflowProcess/descendant::xpdl2:Transition"/>
					<xsl:variable name="actId" select="ancestor::xpdl2:Activity/@Id"/>

					<xsl:choose>
						<xsl:when test="count($trans[@To = $actId]) = 0">
							<!-- no incoming transitions, it's a Catch -->
							<xsl:attribute name="CatchThrow">CATCH</xsl:attribute>
						</xsl:when>
						<xsl:otherwise>
							<xsl:attribute name="Name"></xsl:attribute>
							<xsl:attribute name="CatchThrow">THROW</xsl:attribute>
						</xsl:otherwise>
					</xsl:choose>
					
				</xsl:otherwise>
			</xsl:choose>
			
			<xsl:apply-templates select="node()"/>
		
		</xpdl2:TriggerResultLink>
    </xsl:template>

	 <!--
        ===============================================================================
		add catch throw to trigger result message.
        ===============================================================================
    -->
    <xsl:template match="xpdl2:StartEvent/xpdl2:TriggerResultMessage">
		<xpdl2:TriggerResultMessage CatchThrow="CATCH">
			<xsl:apply-templates select="@* | node()"/>
		</xpdl2:TriggerResultMessage>
    </xsl:template>

    <xsl:template match="xpdl2:IntermediateEvent/xpdl2:TriggerResultMessage">
		<xpdl2:TriggerResultMessage CatchThrow="CATCH">
			<xsl:apply-templates select="@* | node()"/>
		</xpdl2:TriggerResultMessage>
    </xsl:template>

    <xsl:template match="xpdl2:EndEvent/xpdl2:TriggerResultMessage">
		<xpdl2:TriggerResultMessage CatchThrow="THROW">
			<xsl:apply-templates select="@* | node()"/>
		</xpdl2:TriggerResultMessage>
    </xsl:template>


	 <!--
        ===============================================================================
		In some circumstances replace Intermediate Error events with Signal Events...
			- If iProcess destination is set - all intermediate error events (catch and throw) are changed to signals.

			- Else in-flow events are changed to signal events and any attached signal with the specified code (at the same process level) is changed to signal event.
        ===============================================================================
    -->
	<xsl:template match="xpdl2:IntermediateEvent[@Trigger = 'Error']">
	
		<xsl:variable name="process" select="ancestor::xpdl2:WorkflowProcess"/>
	
		<xsl:variable name="action">
			<xsl:choose>
				<xsl:when test="@Target != ''">
					<!-- Attached to task boundary... -->
					<xsl:variable name="isIProcess"><xsl:call-template name="isIProcess"><xsl:with-param name="process" select="$process"/></xsl:call-template></xsl:variable>
						
					<xsl:choose>
						<xsl:when test="$isIProcess = 'true'">
							<!-- All iprocess error events must become signals (v3.0 iProcess were all for the 'withdraw task on signal' purpose so should all become signals).
-->
							<xsl:text>catchSignal</xsl:text>
						</xsl:when>

						<xsl:otherwise>
							<!-- 
									For Non-iProcess process, catch error event is ONLY changed to signal IF there is an in-flow intermediate event with that throws the same error code. 
							-->
							<xsl:variable name="errorCode" select="xpdl2:ResultError/@ErrorCode"/>
							
							<xsl:choose>
								<xsl:when test="$errorCode != ''">
									<!-- Get a list of all in-flow error events in the process (including embedded sub-procs) with the same errorcode.
-->
									<xsl:variable name="inflowErrors" select="$process/xpdl2:Activities/xpdl2:Activity/xpdl2:Event/xpdl2:IntermediateEvent[not(@Target) or @Target = '']/xpdl2:ResultError[@ErrorCode = $errorCode] |
																							$process/xpdl2:ActivitySets/xpdl2:ActivitySet/xpdl2:Activities/xpdl2:Activity/xpdl2:Event/xpdl2:IntermediateEvent[not(@Target) or @Target = '']/xpdl2:ResultError[@ErrorCode = $errorCode]"/>
									
									<xsl:choose>
										<xsl:when test="count($inflowErrors) > 0">
											<!-- There is an in-flow intermediate eror event that has the same code. It will be changed to a signal so ALL catchers for it must be changed to signal to.
-->
											<xsl:text>catchSignal</xsl:text>
										</xsl:when>
										<xsl:otherwise>
											<!-- leavew it alone because it does not catch an error thrown by an intermediate in-flow event
-->
											<xsl:text>leaveSame</xsl:text>
										</xsl:otherwise>
									</xsl:choose>
									
								</xsl:when>

								<xsl:otherwise>
									<!-- Catch error in non-iProcess process without ErrorCode specified - leave as is.
-->
									<xsl:text>leaveSame</xsl:text>
								</xsl:otherwise>
							</xsl:choose>
							
							
							
						</xsl:otherwise>
					</xsl:choose>
					
				</xsl:when>
				
				<xsl:otherwise>
					<!-- In-flow intermediate events are unsupported in BPMN 1.2 - So must change to signal
-->
					<xsl:text>throwSignal</xsl:text>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
	
		<!-- 
			Now we know the action to perform... 
		-->
		<xsl:choose>
			<xsl:when test="$action = 'throwSignal'">
				<!-- change the in-flow error event to a throw signal. -->
				<xpdl2:IntermediateEvent Trigger="Signal">
					<xpdl2:TriggerResultSignal CatchThrow="THROW">
						<xsl:if test="xpdl2:ResultError/@ErrorCode != ''">
							<xsl:attribute name="Name"><xsl:value-of select="xpdl2:ResultError/@ErrorCode"/></xsl:attribute>
						</xsl:if>
					</xpdl2:TriggerResultSignal>
				</xpdl2:IntermediateEvent>
			</xsl:when>
			
			<xsl:when test="$action = 'catchSignal'">
				<!-- Change task boundary error event to catch signal -->
				<xpdl2:IntermediateEvent Trigger="Signal" Target="{@Target}">
					<xpdl2:TriggerResultSignal CatchThrow="CATCH">
						<xsl:if test="xpdl2:ResultError/@ErrorCode != ''">
							<xsl:attribute name="Name"><xsl:value-of select="xpdl2:ResultError/@ErrorCode"/></xsl:attribute>
						</xsl:if>
					</xpdl2:TriggerResultSignal>
				</xpdl2:IntermediateEvent>
			</xsl:when>
			
			<xsl:otherwise>
				<!-- No changes necessary -->
				<xsl:copy>
					<xsl:apply-templates select="@* | node()"/>
				</xsl:copy>
			</xsl:otherwise>
		</xsl:choose>
	
	</xsl:template>

	 <!--
        ===============================================================================
		For iProcess, change End Error Events to End Signal Events
        ===============================================================================
	-->
	<xsl:template match="xpdl2:EndEvent[@Result = 'Error']">
		<xsl:variable name="process" select="ancestor::xpdl2:WorkflowProcess"/>
	
		<xsl:variable name="isIProcess"><xsl:call-template name="isIProcess"><xsl:with-param name="process" select="$process"/></xsl:call-template></xsl:variable>
		
		<xsl:choose>
			<xsl:when test="$isIProcess = 'true'">
				<xpdl2:EndEvent Result="Signal">
					<xpdl2:TriggerResultSignal CatchThrow="THROW">
						<xsl:if test="xpdl2:ResultError/@ErrorCode != ''">
							<xsl:attribute name="Name"><xsl:value-of select="xpdl2:ResultError/@ErrorCode"/></xsl:attribute>
						</xsl:if>
					</xpdl2:TriggerResultSignal>
				</xpdl2:EndEvent>
				
			</xsl:when>

			<xsl:otherwise>
				<!-- No changes necessary -->
				<xsl:copy>
					<xsl:apply-templates select="@* | node()"/>
				</xsl:copy>
			</xsl:otherwise>
		</xsl:choose>
		
	</xsl:template>

	<!--
        ===============================================================================
		Move Timer event TimeDate / TimeCycle attributes to elements
        ===============================================================================
    -->
	<xsl:template match="xpdl2:TriggerTimer">
		<xpdl2:TriggerTimer>
			<xsl:apply-templates select="@*[local-name() != 'TimeDate' and local-name() != 'TimeCycle']"/>
			
			<xsl:choose>
				<xsl:when test="@TimeDate != ''">
					<xpdl2:TimeDate><xsl:value-of select="@TimeDate"/></xpdl2:TimeDate>
				</xsl:when>
				<xsl:when test="@TimeCycle != ''">
					<xpdl2:TimeCycle><xsl:value-of select="@TimeCycle"/></xpdl2:TimeCycle>
				</xsl:when>
			</xsl:choose>
			
			<xsl:apply-templates select="node()"/>
		</xpdl2:TriggerTimer>
	</xsl:template>

	<!--
        ===============================================================================
		DataObject - Remove RequiredForStart and ProducedAtCompletion attributes
        ===============================================================================
    -->
	<xsl:template match="xpdl2:DataObject/@RequiredForStart">
	</xsl:template>

	<xsl:template match="xpdl2:DataObject/@ProducedAtCompletion">
	</xsl:template>

	<!--
        ===============================================================================
		FormalParameter - xpdlExt:Mandatory becomes xpdl2 Required attribute
        ===============================================================================
    -->
    <xsl:template match="xpdl2:FormalParameter/@xpdExt:Mandatory">
		<xsl:attribute name="Required"><xsl:value-of select="."/></xsl:attribute>
    </xsl:template>

	<!--
        ===============================================================================
		Gateways - various
        ===============================================================================
    -->
    <xsl:template match="xpdl2:Route">
		<xpdl2:Route>
			<xsl:choose>
				<xsl:when test="@GatewayType = 'XOR'">
					<xsl:attribute name="GatewayType">Exclusive</xsl:attribute>
					<xsl:choose>
						<xsl:when test="@Instantiate = 'true'">
							<xsl:attribute name="ExclusiveType">Event</xsl:attribute>
						</xsl:when>
						<xsl:otherwise>
							<xsl:attribute name="ExclusiveType">Data</xsl:attribute>
							<xsl:attribute name="MarkerVisible">true</xsl:attribute>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:when>

				<xsl:when test="@GatewayType = 'AND'">
					<xsl:attribute name="GatewayType">Parallel</xsl:attribute>
				</xsl:when>

				<xsl:when test="@GatewayType = 'OR'">
					<xsl:attribute name="GatewayType">Inclusive</xsl:attribute>
				</xsl:when>

				<xsl:otherwise>
					<xsl:attribute name="GatewayType"><xsl:value-of select="@GatewayType"/></xsl:attribute>
				</xsl:otherwise>
			</xsl:choose>
			
			<xsl:apply-templates select="@*[local-name() != 'GatewayType' and local-name() != 'Instantiate']"/>
			<xsl:apply-templates select="node()"/>
		</xpdl2:Route>
    </xsl:template>


	<!--
        ===============================================================================
		Replace XPDLVersion 2.0 -> 2.1
        ===============================================================================
    -->
	<xsl:template match="xpdl2:XPDLVersion">
		<xpdl2:XPDLVersion>2.1</xpdl2:XPDLVersion>
	</xsl:template>
	
	<!--
        ===============================================================================
		Move old xpdExt Loop condition stuff to newly defined places in xpdl 2.1
        ===============================================================================
    -->
	<xsl:template match="xpdl2:LoopStandard">
		<xpdl2:LoopStandard>
			<!-- Preserve JUST the LoopMaximum and TestTime attributes (NOT interested in the attributes as these are duplicated by XPDL spec
					as elements as well - and we Studio 3.1 uses the full element expression rather than the attribute.
-->
			<xsl:copy-of select="@LoopMaximum"/>
			<xsl:copy-of select="@TestTime"/>
			
			<!-- xpdl:LoopStandard now supports a FULL expression for the LoopCondition so we don't need the old xpdExt element that supported the same thing.
-->
			<xsl:if test="xpdExt:StandardLoopScript/xpdExt:LoopExpression != ''">
				<xpdl2:LoopCondition ScriptGrammar="{xpdExt:StandardLoopScript/xpdExt:LoopExpression/@ScriptGrammar}"><xsl:value-of select="xpdExt:StandardLoopScript/xpdExt:LoopExpression"/></xpdl2:LoopCondition>
			</xsl:if>
			
		</xpdl2:LoopStandard>
	</xsl:template>
	
	<xsl:template match="xpdl2:LoopMultiInstance">
		<xpdl2:LoopMultiInstance>
			<!-- Preserve JUST the MI_FlowCondition and MI_Ordering attributes (NOT interested in the attributes as these are duplicated by XPDL spec
					as elements as well - and we Studio 3.1 uses the full element expression rather than the attribute.
-->
			<xsl:copy-of select="@MI_FlowCondition"/>
			<xsl:copy-of select="@MI_Ordering"/>
			
			<!-- xpdl:LoopMultiInstance now supports a FULL expression for the MI_Condition and ComplexMI_FlowCondition so we don't need the old xpdExt element that supported the same thing.
-->
			<xsl:if test="xpdExt:MultiInstanceScripts/xpdExt:LoopExpression != ''">
				<xpdl2:MI_Condition ScriptGrammar="{xpdExt:MultiInstanceScripts/xpdExt:LoopExpression/@ScriptGrammar}"><xsl:value-of select="xpdExt:MultiInstanceScripts/xpdExt:LoopExpression"/></xpdl2:MI_Condition>
			</xsl:if>
			
			<xsl:if test="xpdExt:MultiInstanceScripts/xpdExt:ComplexExitExpression != ''">
				<xpdl2:ComplexMI_FlowCondition ScriptGrammar="{xpdExt:MultiInstanceScripts/xpdExt:ComplexExitExpression/@ScriptGrammar}"><xsl:value-of select="xpdExt:MultiInstanceScripts/xpdExt:ComplexExitExpression"/></xpdl2:ComplexMI_FlowCondition>
			</xsl:if>
			
			<xsl:if test="xpdExt:MultiInstanceScripts/xpdExt:AdditionalInstances != ''">
              <xpdExt:MultiInstanceScripts>
				  <xsl:copy-of select="xpdExt:MultiInstanceScripts/xpdExt:AdditionalInstances"/>
              </xpdExt:MultiInstanceScripts>
			</xsl:if>
			
		</xpdl2:LoopMultiInstance>

	</xsl:template>

	<!--
        ===============================================================================
        Handle destination environment changes.
        ===============================================================================
    -->
    <xsl:template match="xpdl2:WorkflowProcess/xpdl2:ExtendedAttributes | xpdExt:ProcessInterface/xpdl2:ExtendedAttributes">
		
		<xpdl2:ExtendedAttributes>
			<!-- Copy all destination environment ext attrs -->
			<xsl:for-each select="xpdl2:ExtendedAttribute">
				<xsl:if test="@Name != 'Destination'">
					<xsl:apply-templates select="."/>
				</xsl:if>
			</xsl:for-each>
		
			<xsl:if test="function-available('java:com.tibco.xpd.xpdl2.edit.util.XsltUtils.getDefaultGlobalDestination')">
				
				<xsl:variable name="isIProcess"><xsl:call-template name="isIProcess"><xsl:with-param name="process" select=".."/></xsl:call-template></xsl:variable>
			
				<xsl:if test="$isIProcess = 'true'">
					<xpdl2:ExtendedAttribute Name="Destination">
						<xsl:attribute name="Value"><xsl:value-of select="java:com.tibco.xpd.xpdl2.edit.util.XsltUtils.getDefaultGlobalDestination('com.tibco.xpd.iprocess.globalDestination')"/></xsl:attribute>
					</xpdl2:ExtendedAttribute>
				</xsl:if>
				
				<xsl:if test="count(xpdl2:ExtendedAttribute[@Name = 'Destination' and starts-with(@Value, 'simulation')]) > 0">
					<xpdl2:ExtendedAttribute Name="Destination">
						<xsl:attribute name="Value"><xsl:value-of select="java:com.tibco.xpd.xpdl2.edit.util.XsltUtils.getDefaultGlobalDestination('com.tibco.xpd.simulation.globalDestination')"/></xsl:attribute>
					</xpdl2:ExtendedAttribute>
				</xsl:if>
				
				<xsl:if test="count(xpdl2:ExtendedAttribute[@Name = 'Destination' and starts-with(@Value, 'cim')]) > 0">
					<xpdl2:ExtendedAttribute Name="Destination" Value="CIM"/>										
				</xsl:if>
				
			</xsl:if>

		</xpdl2:ExtendedAttributes>
		
    </xsl:template>

	<!--
        ===============================================================================
		Replace old xpdl2:Service/IsRemote with xpdleXt:IsRemote
        ===============================================================================
    -->
    <xsl:template match="xpdl2:Service/@IsRemote">
		<xsl:attribute name="xpdExt:IsRemote"><xsl:value-of select="."/></xsl:attribute>
    </xsl:template>
    
   	<!--
		===============================================================================
		Change the alias names for alias Ids
		===============================================================================
	-->
	<xsl:template match="xpdl2:WebServiceOperation/@xpdExt:Alias">
		<xsl:variable name="alias" select="." />
		<xsl:variable name="participantId">
			<xsl:call-template name="getParticipantId">				
				<xsl:with-param name="alias" select="." />
			</xsl:call-template>
		</xsl:variable>
		<xsl:choose>
			<xsl:when test="$participantId != 'null'">
				<xsl:attribute name="xpdExt:Alias"><xsl:value-of select="$participantId" /></xsl:attribute>
			</xsl:when>
			<xsl:otherwise>
				<xsl:attribute name="xpdExt:Alias"><xsl:value-of select="." /></xsl:attribute>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	
	<!--
        ===============================================================================
		Return the id of the given participant if is a matching name.
        ===============================================================================
    -->
	<xsl:template name="getParticipantId">
		<xsl:param name="alias" />
		<xsl:choose>
			<xsl:when
				test="$alias/ancestor::xpdl2:WorkflowProcess/xpdl2:Participants/xpdl2:Participant[@Name = $alias]/@Id != ''">
				<xsl:value-of
					select="$alias/ancestor::xpdl2:WorkflowProcess/xpdl2:Participants/xpdl2:Participant[@Name = $alias]/@Id" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:choose>
					<xsl:when
						test="$alias/ancestor::xpdl2:Package/xpdl2:Participants/xpdl2:Participant[@Name = $alias]/@Id != ''">
						<xsl:value-of
							select="$alias/ancestor::xpdl2:Package/xpdl2:Participants/xpdl2:Participant[@Name = $alias]/@Id" />
					</xsl:when>
					<xsl:otherwise>
						<xsl:variable name="performerId">
							<xsl:call-template name="getPerformerParamOrFieldId">
								<xsl:with-param name="alias" select="$alias" />
							</xsl:call-template>
						</xsl:variable>
						<xsl:value-of select="$performerId" />
					</xsl:otherwise>
				</xsl:choose>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
		<!--
        ===============================================================================
		Return the id of the given performer if is a matching name.
        ===============================================================================
    -->
	<xsl:template name="getPerformerParamOrFieldId">
		<xsl:param name="alias" />
		<xsl:choose>
			<xsl:when
				test="$alias/ancestor::xpdl2:WorkflowProcess/*/*[@Name = $alias][xpdl2:DataType/xpdl2:BasicType/@Type = 'PERFORMER']/@Id != ''">
				<xsl:value-of
					select="$alias/ancestor::xpdl2:WorkflowProcess/*/*[@Name = $alias][xpdl2:DataType/xpdl2:BasicType/@Type = 'PERFORMER']/@Id" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:choose>
					<xsl:when
						test="$alias/ancestor::xpdl2:Package/*/*[@Name = $alias][xpdl2:DataType/xpdl2:BasicType/@Type = 'PERFORMER']/@Id != ''">
						<xsl:value-of
							select="$alias/ancestor::xpdl2:Package/*/*[@Name = $alias][xpdl2:DataType/xpdl2:BasicType/@Type = 'PERFORMER']/@Id" />
					</xsl:when>
					<xsl:otherwise>
						<xsl:text>null</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<!--
        ===============================================================================
		Remove [NodeGraphicsInfo]/ToolId="XPD" - in Xpdl2.1 standardised graphics infos should not have tool id.
        ===============================================================================
    -->
    <xsl:template match="xpdl2:NodeGraphicsInfo/@ToolId">
		<!-- Keep the non standardised ones though (like attyached event border position and so on
-->
		<xsl:if test=". != 'XPD'">
			<xsl:attribute name="ToolId"><xsl:value-of select="."/></xsl:attribute>
		</xsl:if>
    </xsl:template>
    
	<!--
        ===============================================================================
		Replace [ConnectorGraphicsInfo]/ToolId="XPD" - with ToolId="XPD.ConnectionInfo"
        ===============================================================================
    -->
    <xsl:template match="xpdl2:ConnectorGraphicsInfo/@ToolId">
		<xsl:choose>
			<xsl:when test=". != 'XPD'">
				<xsl:attribute name="ToolId"><xsl:value-of select="."/></xsl:attribute>
			</xsl:when>
			<xsl:otherwise>
				<xsl:attribute name="ToolId">XPD.ConnectionInfo</xsl:attribute>
			</xsl:otherwise>
		</xsl:choose>
    
    </xsl:template>

	<!--
        ===============================================================================
		Return string "true" if the process/interface has an iprocess destination environment set.
        ===============================================================================
    -->
	<xsl:template name="isIProcess">
		<xsl:param name="process"/>
		
		<xsl:choose>
			<xsl:when test="count($process/xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'Destination' and (starts-with(@Value, 'ipm') or starts-with(@Value, 'ipe'))]) > 0">
				<xsl:text>true</xsl:text>
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>false</xsl:text>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>


    <!--
        ===============================================================================
        Add Moode to all associated parameters.
        ===============================================================================
    -->
    <xsl:template match="xpdExt:AssociatedParameter">
        <xsl:element name="xpdExt:AssociatedParameter">
	        <xsl:variable name="paramName" select="@FormalParam"/>
	        <xsl:variable name="paramMode">
	           <xsl:choose>
	               <xsl:when test="@Mode">
	                   <xsl:value-of select="@Mode"/>
	               </xsl:when>
                   <xsl:when test="../../../../xpdl2:FormalParameters/xpdl2:FormalParameter[@Name=$paramName]/@Mode">
                       <xsl:value-of select="../../../../xpdl2:FormalParameters/xpdl2:FormalParameter[@Name=$paramName]/@Mode"/>
                   </xsl:when>
                   <xsl:otherwise>INOUT</xsl:otherwise>
	           </xsl:choose>
	        </xsl:variable>
	        <xsl:attribute name="Mode"><xsl:value-of select="$paramMode"/></xsl:attribute>
            <xsl:apply-templates select="@* | text()"/>
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
