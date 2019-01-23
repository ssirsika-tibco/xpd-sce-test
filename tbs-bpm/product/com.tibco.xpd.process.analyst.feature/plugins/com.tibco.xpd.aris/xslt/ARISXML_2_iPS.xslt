<?xml version="1.0" encoding="UTF-8"?>
<!--
===================================================================================================================
XSLT:		ARISXML_2_iPS_xpdl.xslt

DESCRIPTION:
	This xslt will convert ARIS XML to iPS XPDL

===================================================================================================================
-->

<!-- Define constants -->
<!DOCTYPE xsl:stylesheet [
	<!ENTITY crlf							'&#xA;'>
	<!ENTITY particROLE				'ROLE'>
	<!ENTITY particHUMAN			'HUMAN'>
	<!ENTITY particOU					'ORGANIZATIONAL_UNIT'>
	<!ENTITY particSYSTEM			'SYSTEM'>
	<!ENTITY arisParticipantTypes 'OT_POS OT_PERS OT_PERS_TYPE OT_GRP OT_ORG_UNIT OT_ORG_UNIT_TYPE OT_SYS_ORG_UNIT OT_SYS_ORG_UNIT_TYPE OT_APPL_SYS OT_APPL_SYS_CLS OT_APPL_SYS_TYPE'>
	<!ENTITY true							'TRUE'>
	<!ENTITY false						'FALSE'>
	<!ENTITY arisStateBeingProcessed			'BeingProcessed'>
	<!ENTITY	arisStateBeingProcessedValue	'148'>
	<!ENTITY arisStateComplete						'Complete'>
	<!ENTITY arisStateCompleteValue				'149'>
	<!ENTITY arisStateReleased						'Released'>
	<!ENTITY arisStateReleasedValue				'150'>
	<!ENTITY arisStateLastChange					'Last Change'>
	<!ENTITY arisStateLastChangeValue			'151'>
	<!ENTITY	arisStateToBeEdited					'To Be Edited'>
	<!ENTITY arisStateToBeEditedValue			'651'>
	<!ENTITY activityHeight			'68'>
	<!ENTITY activityWidth			'90'>
]>

<xsl:stylesheet version="1.0" 
	xmlns:xpdl="http://www.wfmc.org/2002/XPDL1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:extensions="http://www.tibco.com/xpd/XpdlExtensions1.0.0" 
	xmlns:schemaLocation="xpdl http://www.wfmc.org/2002/XPDL1.0"
	>
	
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	
	<!-- This factor is applied to all X Y coordinates to scale the ARIS model to better fit in the XPD -->
	<xsl:variable name="factor">0.4</xsl:variable>
	
<!--
===================================================================================================================
	XSLT processing start point (process whole document)
===================================================================================================================
-->
	<xsl:template match="/">
	
		<xsl:apply-templates />

	</xsl:template>

<!--
===================================================================================================================
	AML = head element of the ARIS XML document.  This will add the xpdl:Package element
===================================================================================================================
-->	
	<xsl:template match="AML">

		<xpdl:Package Id="{Group/@Group.ID}" Name="{Header-Info/@DatabaseName}">
			<!-- Output the package header -->
			<xsl:apply-templates select="Header-Info" />
			<!-- Output redefinable header -->
			<xsl:apply-templates select="Database" />
			<!-- Output workflow processes -->
			<xsl:apply-templates select="Group" />

			<!-- Add the package extended attributes -->
			 <xpdl:ExtendedAttributes>
				<xpdl:ExtendedAttribute Name="CreatedBy" Value="TIBCO Business Studio 1.0.0"/>
				<xpdl:ExtendedAttribute Name="FormatVersion" Value="1"/>
			</xpdl:ExtendedAttributes>
			
		</xpdl:Package>
		
	</xsl:template>
	
<!--
===================================================================================================================
	Header-Info = this will add the xpdl:PackageHeader
===================================================================================================================
-->	
	<xsl:template match="Header-Info">
	
		<xpdl:PackageHeader>
			<xpdl:XPDLVersion>1.0</xpdl:XPDLVersion>
			<xpdl:Vendor>TIBCO</xpdl:Vendor>
			<xpdl:Created>ARIS</xpdl:Created>
			<xpdl:Description/>
			<xpdl:Documentation/>
		</xpdl:PackageHeader>
		
	</xsl:template>
	
<!--
===================================================================================================================
	Database = this will add the xpdl:RedefinableHeader
===================================================================================================================
-->	
	<xsl:template match="Database">
	
		<xpdl:RedefinableHeader PublicationStatus="UNDER_TEST">
			<xpdl:Author><xsl:value-of select="AttrDef[@AttrDef.Type = 'AT_CREATOR']/AttrValue" /></xpdl:Author>
			<xpdl:Version>1.0</xpdl:Version>
	  </xpdl:RedefinableHeader>
	  
	</xsl:template>	

<!--
===================================================================================================================
	Group = this will add the xpdl:WorkflowProcesses
===================================================================================================================
-->	
	<xsl:template match="Group">

		<!-- Add any participants -->
		<xpdl:Participants>
			<xsl:apply-templates select="//ObjDef" mode="participants" />
		</xpdl:Participants>
		
		<!-- Add any data fields -->
		<xpdl:DataFields>
			<xsl:apply-templates select="//ObjDef" mode="dataFields" />
		</xpdl:DataFields>
		
		<!-- Add processes -->
		<xpdl:WorkflowProcesses>
			<xsl:apply-templates select="//Model">
				<xsl:with-param name="objDefinitions" select="//ObjDef" />
			</xsl:apply-templates>
		</xpdl:WorkflowProcesses>
	
	</xsl:template>
	
<!--
===================================================================================================================
	ObjDef (participants) - Identify participants and add them to the xpdl
===================================================================================================================
-->	
	<xsl:template match="ObjDef" mode="participants">
	
		<!-- Get ID -->
		<xsl:variable name="objDefID" select="@ObjDef.ID" />

		<!-- Not interested in Functions, Events and Rules. -->
		<xsl:if test="@TypeNum != 'OT_FUNC' and @TypeNum != 'OT_EVT' and @TypeNum != 'OT_RULE'">  
			<!-- Only interested with objects that have an occurrence within one of the models -->
			<xsl:if test="count (..//Model/ObjOcc[@ObjDef.IdRef = $objDefID]) &gt; 0">
				<!-- Identify participants -->
				<xsl:variable name="type">
					<xsl:choose>
						<xsl:when test="@TypeNum = 'OT_POS'">
							<xsl:text>&particROLE;</xsl:text>
						</xsl:when>
						<xsl:when test="@TypeNum = 'OT_PERS' or @TypeNum = 'OT_PERS_TYPE' or @TypeNum = 'OT_GRP'">
							<xsl:text>&particHUMAN;</xsl:text>
						</xsl:when>
						<xsl:when test="@TypeNum = 'OT_ORG_UNIT' or @TypeNum = 'OT_ORG_UNIT_TYPE'">
							<xsl:text>&particOU;</xsl:text>
						</xsl:when>
						<xsl:when test="@TypeNum = 'OT_SYS_ORG_UNIT' or @TypeNum = 'OT_SYS_ORG_UNIT_TYPE' or @TypeNum = 'OT_APPL_SYS' or @TypeNum = 'OT_APPL_SYS_CLS' or @TypeNum = 'OT_APPL_SYS_TYPE'">
							<xsl:text>&particSYSTEM;</xsl:text>
						</xsl:when>
					</xsl:choose>
				</xsl:variable>
				<!-- If participant then add it -->
				<xsl:if test="$type != ''">
					 <xpdl:Participant Id="{count (preceding-sibling::ObjDef) + 1}" Name="{normalize-space(AttrDef[@AttrDef.Type = 'AT_NAME']/AttrValue)}">
						  <xpdl:ParticipantType Type="{$type}"/>
					</xpdl:Participant>
				</xsl:if>
				
			</xsl:if>
		</xsl:if>
	</xsl:template>

<!--
===================================================================================================================
	ObjDef (dataFields) - Identify data fields and add them to the xpdl
===================================================================================================================
-->	
	<xsl:template match="ObjDef" mode="dataFields">

		<!-- Get object ID -->
		<xsl:variable name="objDefID" select="@ObjDef.ID" />
		<!--  Only interested in data types -->
		<xsl:if test="@TypeNum = 'OT_ERM_ATTR' or @TypeNum = 'OT_TECH_TRM'">
			<!-- Only interested with objects that have an occurrence within one of the models -->
			<xsl:if test="count (..//Model/ObjOcc[@ObjDef.IdRef = $objDefID]) &gt; 0">
				<xpdl:DataField Id="{count(preceding-sibling::ObjDef) + 1}" Name="{normalize-space (AttrDef[@AttrDef.Type = 'AT_NAME']/AttrValue)}">
					<xpdl:DataType>
					<xpdl:BasicType Type="REFERENCE"/>
					</xpdl:DataType>
				</xpdl:DataField>
			</xsl:if>
		</xsl:if>
		
	</xsl:template>
	
<!--
===================================================================================================================
	Model = this will add the xpdl:WorkflowProcess
===================================================================================================================
-->	
	<xsl:template match="Model">
		<xsl:param name="objDefinitions" />

		<!-- Only deal with eEPC model types -->
		<xsl:if test="starts-with (@Model.Type, 'MT_EEPC')">
			
			<xpdl:WorkflowProcess Id="{position()}"  Name="{normalize-space(AttrDef[@AttrDef.Type = 'AT_NAME']/AttrValue)}">
				<xpdl:ProcessHeader>
					<xpdl:Created><xsl:value-of select="AttrDef[@AttrDef.Type = 'AT_CREAT_TIME_STMP']/AttrValue" /></xpdl:Created>
				</xpdl:ProcessHeader>
				<xpdl:RedefinableHeader />
				
				<!-- calculate any X or Y shift required in the model to stop it being drawn off the left or top border of the editor -->
				<xsl:variable name="shiftX">
					<xsl:call-template name="calculateXShift" />
				</xsl:variable> 

				<xsl:variable name="shiftY"> 
					<xsl:call-template name="calculateYShift" />
				</xsl:variable> 
			
				<!-- Add the activities -->
				<xpdl:Activities>
					<xsl:apply-templates select="ObjOcc">
						<xsl:with-param name="objDefinitions" select="$objDefinitions" />
						<xsl:with-param name="shiftX" select="$shiftX" />
						<xsl:with-param name="shiftY" select="$shiftY" />
					</xsl:apply-templates>
				</xpdl:Activities>
				
	
				<!-- Add the transitions -->
				<xpdl:Transitions>
					<xsl:apply-templates  select="ObjOcc/CxnOcc">
						<xsl:with-param name="objDefinitions" select="$objDefinitions" />
						<xsl:with-param name="shiftX" select="$shiftX" />
						<xsl:with-param name="shiftY" select="$shiftY" />
					</xsl:apply-templates>
				</xpdl:Transitions>
	
				<!-- Add default pool and lane -->
				<xsl:call-template name="addDefaultPool">
					<xsl:with-param name="objDefinitions" select="$objDefinitions" />
					<xsl:with-param name="objOccs" select="ObjOcc" />
					<xsl:with-param name="textOccs" select="FFTextOcc" />
					<xsl:with-param name="modelAttrDef" select="AttrDef" />
					<xsl:with-param name="shiftX" select="$shiftX" />
					<xsl:with-param name="shiftY" select="$shiftY" />
				</xsl:call-template>
				
			</xpdl:WorkflowProcess>
		</xsl:if>
		
	</xsl:template>
	
<!--
===================================================================================================================
	isActivity = this template will return 'true' if the given object will be drawn as an activity in the mode, else 'false' will be returned
===================================================================================================================
-->		
	<xsl:template name="isActivity">
		<xsl:param name="objOcc" />
		<xsl:param name="objDef" />
		<xsl:param name="allObjOccs" />
		
		<!-- 
			Determine whether this object will be drawn as an activity .  All the following objects will not be drawn as activities as they
			will either be converted to associated notes, participants or data fields:
		
				1. Participant type objects.
				2. Data type objects - OT_ERM _ATTR and OT_TECH_TRM
				3. Non-function objects that have either one in-coming connection or one out-going connection (but not both)
				4. Non-function objects that have no connections
		-->
		
		<!-- If this is a function, rule or event type then it will be placed as an activity -->
		<xsl:choose>
			<xsl:when test="$objDef/@TypeNum = 'OT_FUNC' or $objDef/@TypeNum = 'OT_RULE' or $objDef/@TypeNum = 'OT_EVT'">
				<xsl:text>&true;</xsl:text>
			</xsl:when>
			<xsl:otherwise>

				<xsl:choose>
					<xsl:when test="contains ('&arisParticipantTypes;', $objOcc/@TypeNum)">
						<xsl:text>&false;</xsl:text>
					</xsl:when>
					<xsl:when test="$objOcc/@TypeNum = 'OT_ERM_ATTR' or $objOcc/@TypeNum = 'OT_TECH_TRM'">
						<xsl:text>&false;</xsl:text>
					</xsl:when>
					<!-- only 1 out-going connection -->
					<xsl:when test="count ($objOcc/CxnOcc) = 1 and count ($allObjOccs/CxnOcc[@ToObjOcc.ID = $objOcc/@ObjOcc.ID]) = 0">
						<xsl:text>&false;</xsl:text>
					</xsl:when>
					<!-- Only 1 in-coming connection -->
					<xsl:when test="count ($objOcc/CxnOcc) = 0 and count ($allObjOccs/CxnOcc[@ToObjOcc.ID = $objOcc/@ObjOcc.ID]) = 1">
						<xsl:text>&false;</xsl:text>
					</xsl:when>
					<!-- no connections -->
					<xsl:when test="count ($objOcc/CxnOcc) = 0 and count ($allObjOccs/CxnOcc[@ToObjOcc.ID = $objOcc/@ObjOcc.ID]) = 0">
						<xsl:text>&false;</xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text>&true;</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
					
			</xsl:otherwise>
		</xsl:choose>
		
	</xsl:template>
	
<!--
===================================================================================================================
	ignoreEventOcc = If this is an event object that has ONE incoming connection and ONE outgoing connection then this event
	will not be drawn in the model.  Instead, the event name will be used as the description of the transition it sits on
	Returns '&true;' if the events will be ignored, otherwise returns '&false;'.
===================================================================================================================
-->		
	<xsl:template name="ignoreEventOcc">
		<xsl:param name="objOcc" />
		<xsl:param name="allObjOccs" />
		<xsl:param name="objDefinitions" />
		
		<!-- Get the object definition of this object -->
		<xsl:variable name="objDef" select="$objDefinitions[@ObjDef.ID = $objOcc/@ObjDef.IdRef]" />		
		
		<!-- Only applies to EVT objects -->
		<xsl:choose>
			<xsl:when test="$objDef/@TypeNum = 'OT_EVT'">
				<!-- Get number of object occs connected to this event -->
				<xsl:variable name="connectedFromObjOccs" select="$allObjOccs[CxnOcc/@ToObjOcc.IdRef = $objOcc/@ObjOcc.ID]" />
				
				<!-- The event should have ONE incoming connection and ONE outgoing connection -->
				<xsl:choose>
					<xsl:when test="count ($connectedFromObjOccs) =1 and count ($objOcc/CxnOcc) = 1">
						<xsl:text>&true;</xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text>&false;</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			
			<xsl:otherwise>
				<!-- Not an event -->
				<xsl:text>&false;</xsl:text>
			</xsl:otherwise>
			
		</xsl:choose>
		
	</xsl:template>
	
<!--
===================================================================================================================
	ObjOcc = this will add the xpdl:Activity entries
===================================================================================================================
-->	
	<xsl:template match="ObjOcc">
		<xsl:param name="objDefinitions" />
		<xsl:param name="shiftX" />
		<xsl:param name="shiftY" />
		
		<!-- Get this objects ID -->
		<xsl:variable name="thisObjOcc" select="." />
		<xsl:variable name="objOccID" select="@ObjOcc.ID" />
		<!-- Get the object definition of this occurrence -->
		<xsl:variable name="objDefId" select="@ObjDef.IdRef" />
		<xsl:variable name="objDef" select="$objDefinitions[@ObjDef.ID = $objDefId]" />
		
		<!-- Check if this object will be drawn as an activity -->
		<xsl:variable name="isActivity">
			<xsl:call-template name="isActivity">
				<xsl:with-param name="objOcc" select="." />
				<xsl:with-param name="objDef" select="$objDef" />
				<xsl:with-param name="allObjOccs" select="../ObjOcc" />
			</xsl:call-template>
		</xsl:variable>
		
		<!-- If this is an intermediate event object  then omit it as it will become the name of the transition it sits on -->
		<xsl:variable name="ignoreEventOcc">
			<xsl:call-template name="ignoreEventOcc">
				<xsl:with-param name="objOcc" select="." />
				<xsl:with-param name="allObjOccs" select="../ObjOcc" />
				<xsl:with-param name="objDefinitions" select="$objDefinitions" />
			</xsl:call-template>
		</xsl:variable>
		
		<!-- If this is an activity and not an event that will be omitted then draw it -->
		<xsl:if test="$isActivity = '&true;' and $ignoreEventOcc = '&false;'">
		
			<!-- If this is an operator then get type -->
			<xsl:variable name="operator">
				<xsl:if test="$objDef/@TypeNum = 'OT_RULE'">
					<xsl:choose>
						<!-- OR operator -->
						<xsl:when test="@SymbolNum = 'ST_OPR_OR_1' or @SymbolNum = 'ST_OPR_OR_2' or @SymbolNum = 'ST_OPR_OR_3' or @SymbolNum = 'ST_OR'">
							<xsl:text>OR</xsl:text>
						</xsl:when>
						<!-- XOR operator -->
						<xsl:when test="@SymbolNum = 'ST_OPR_XOR_1' or @SymbolNum = 'ST_OPR_XOR_2' or @SymbolNum = 'ST_OPR_XOR_3' or @SymbolNum = 'ST_XOR'">
							<xsl:text>XOR</xsl:text>
						</xsl:when>
						<!-- AND operator -->
						<xsl:when test="@SymbolNum = 'ST_OPR_AND_1' or @SymbolNum = 'ST_OPR_AND_2' or @SymbolNum = 'ST_OPR_AND_3' or @SymbolNum = 'ST_AND'">
							<xsl:text>AND</xsl:text>
						</xsl:when>
					</xsl:choose>
				</xsl:if>
			</xsl:variable>
		
			<xpdl:Activity Id="{position()}" Name="{normalize-space ($objDef/AttrDef[@AttrDef.Type = 'AT_NAME']/AttrValue)}">
				<!-- If there is a description then add it -->
				<xsl:if test="$objDef/AttrDef[@AttrDef.Type = 'AT_DESC']/AttrValue">
					<xpdl:Description>
						<xsl:value-of select="$objDef/AttrDef[@AttrDef.Type = 'AT_DESC']/AttrValue"/>
					</xpdl:Description>
				</xsl:if>
				
				<!-- Check if this is a subflow call -->
				<xsl:variable name="subFlow">
					<xsl:if test="$objDef/@LinkedModels.IdRefs">
						<!-- Get the linked model ID -->
						<xsl:variable name="modelID" select="$objDef/@LinkedModels.IdRefs" />
						<!-- Get the model -->
						<xsl:variable name="model" select="/AML/Group//Model[@Model.ID = $modelID]" />
						<!--If this model is part of this group and it's an eEPC model then add link to the model -->
						<xsl:if test="$model and starts-with ($model/@Model.Type, 'MT_EEPC')">		
							<xsl:value-of select="count($model/preceding-sibling::Model) + 1" />
						</xsl:if>
					</xsl:if>
				</xsl:variable>
				
				<!-- Insert activity type -->
				<xsl:choose>
					<!-- Gateway -->
					<xsl:when test="$objDef/@TypeNum = 'OT_RULE'">
						<xpdl:Route />
					</xsl:when>
					
					<!-- Implementation -->
					<xsl:otherwise>
						<xpdl:Implementation>
						
							<xsl:choose>
								<!-- Event -->
								<xsl:when test="$objDef/@TypeNum = 'OT_EVT'">
									<xpdl:No />
								</xsl:when>
								
								<!-- subflow call -->
								<xsl:when test="$subFlow != ''">
									<xpdl:SubFlow Execution="SYNCHR" Id="{$subFlow}">
										<!-- Add parameters if any -->
										<xsl:apply-templates select="." mode="parameters">
											<xsl:with-param name="objDefinitions" select="$objDefinitions" />
										</xsl:apply-templates>
									</xpdl:SubFlow>
								</xsl:when>
								
								<!-- user/system task -->
								<xsl:otherwise>
									<xpdl:Tool Id="0" Type="APPLICATION">
										<!-- Add parameters if any -->
										<xsl:apply-templates select="." mode="parameters">
											<xsl:with-param name="objDefinitions" select="$objDefinitions" />
										</xsl:apply-templates>
									</xpdl:Tool>
								</xsl:otherwise>
								
							</xsl:choose>
							
						</xpdl:Implementation>
					</xsl:otherwise>
					
				</xsl:choose>
					
				<!-- Add performer if any -->
				<xsl:apply-templates select="." mode="performer">
					<xsl:with-param name="objDefinitions" select="$objDefinitions" />
				</xsl:apply-templates>
				
				<!-- If OT_FUNC object and not a subflow then set task type -->
				<xsl:if test="$objDef/@TypeNum = 'OT_FUNC' and not($objDef/@LinkedModels.IdRefs)">
					<xsl:choose>
						<!-- If system function then set activity to system task -->
						<xsl:when test="starts-with ($thisObjOcc/@SymbolNum, 'ST_SYS')">
							<xpdl:StartMode>
								<xpdl:Automatic/>
							</xpdl:StartMode>
							<xpdl:FinishMode>
								<xpdl:Automatic/>
							</xpdl:FinishMode>				
						</xsl:when>
						<!-- Otherwise set to user task -->
						<xsl:otherwise>
							<xpdl:StartMode>
								<xpdl:Manual/>
							</xpdl:StartMode>
							<xpdl:FinishMode>
								<xpdl:Manual/>
							</xpdl:FinishMode>				
						</xsl:otherwise>
					</xsl:choose>
				</xsl:if>
				
				<!-- If this is an AND operator then insert transition restriction -->
				<xsl:if test="$operator = 'AND'">
					<xpdl:TransitionRestrictions>
						<xpdl:TransitionRestriction>
							<xpdl:Join Type="AND" />
							<xpdl:Split Type="AND" />
						</xpdl:TransitionRestriction>
					</xpdl:TransitionRestrictions>
				</xsl:if>
				
				<!-- 
					Get the center position of object and scale using factor given
					Add the shift to the position to make sure the model is not off the page 
				-->
				<xsl:variable name="xVal">
					<xsl:value-of select="round ((Position/@Pos.X + round (Size/@Size.dX div 2) + $shiftX) * $factor)" />
				</xsl:variable>
				
				<xsl:variable name="yVal">
					<xsl:value-of select="round ((Position/@Pos.Y + round (Size/@Size.dY div 2) +  $shiftY) * $factor)" />
				</xsl:variable>
					
				<xpdl:ExtendedAttributes>
					<xpdl:ExtendedAttribute Name="XPD">
						<extensions:Activity LaneId="0">
	
							<xsl:choose>
								<!-- Gateway -->
								<xsl:when test="$objDef/@TypeNum = 'OT_RULE'">
									<extensions:Location>
										<extensions:XOffset><xsl:value-of select="$xVal" /></extensions:XOffset>
										<extensions:YOffset><xsl:value-of select="$yVal" /></extensions:YOffset>
									</extensions:Location>
									<extensions:ActivityType>GATEWAY</extensions:ActivityType>
									<!-- Output gateway type -->
									<xsl:choose>
										<!-- OR gateway -->
										<xsl:when test="$operator = 'OR'">
											<extensions:GatewayType>OR</extensions:GatewayType>
										</xsl:when>
										<!-- XOR gateway -->
										<xsl:when test="$operator = 'XOR'">
											<extensions:GatewayType>XOR_DATA</extensions:GatewayType>
										</xsl:when>
										<!-- AND gateway -->
										<xsl:when test="$operator = 'AND'">
											<extensions:GatewayType>PARALLEL</extensions:GatewayType>
										</xsl:when>
										<!-- If not a supported rule type then default to XOR -->
										<xsl:otherwise>
											<extensions:GatewayType>XOR_DATA</extensions:GatewayType>
										</xsl:otherwise>
									</xsl:choose>
								</xsl:when>
	
								<!-- Start event (includes any events that have an out-going connection) -->
								<xsl:when test="$objDef/@TypeNum = 'OT_EVT' and count (CxnOcc) &gt; 0">
									<extensions:Location>
										<extensions:XOffset><xsl:value-of select="$xVal" /></extensions:XOffset>
										<extensions:YOffset><xsl:value-of select="$yVal" /></extensions:YOffset>
									</extensions:Location>
									<extensions:ActivityType>EVENT</extensions:ActivityType>
									<extensions:EventFlowType>START</extensions:EventFlowType>
								</xsl:when>
								
								<!-- Finish event (includes any events with no out-going connections) -->
								<xsl:when test="$objDef/@TypeNum = 'OT_EVT' and count (CxnOcc) = 0">
									<extensions:Location>
										<extensions:XOffset><xsl:value-of select="$xVal" /></extensions:XOffset>
										<extensions:YOffset><xsl:value-of select="$yVal" /></extensions:YOffset>
									</extensions:Location>
									<extensions:ActivityType>EVENT</extensions:ActivityType>
									<extensions:EventFlowType>END</extensions:EventFlowType>
								</xsl:when>
								
								<xsl:otherwise>					
									<extensions:Location>
										<extensions:XOffset><xsl:value-of select="$xVal - round (&activityWidth; div 2)" /></extensions:XOffset>
										<extensions:YOffset><xsl:value-of select="$yVal - round (&activityHeight; div 2)" /></extensions:YOffset>
										<extensions:Width>&activityWidth;</extensions:Width>
										<extensions:Height>&activityHeight;</extensions:Height>
									</extensions:Location>
								</xsl:otherwise>
								
							</xsl:choose>
							
						</extensions:Activity>
					</xpdl:ExtendedAttribute>
			</xpdl:ExtendedAttributes>
				
			</xpdl:Activity>
		
		</xsl:if>  
		
	</xsl:template>

<!--
===================================================================================================================
	ObjOcc (performer) = Identify any connected participants - if there is only one then add as a performer.  If multiple
		participants are found then they will be added as notes
===================================================================================================================
-->	
	<xsl:template match="ObjOcc" mode="performer">
		<xsl:param name="objDefinitions" />
		
		<!-- Get this occurence ID -->
		<xsl:variable name="objOccID" select="@ObjOcc.ID" />
	
		<!-- Get all object occs connected to this object -->
		<xsl:variable name="connectedOccs" select="../ObjOcc[CxnOcc/@ToObjOcc.IdRef = $objOccID]" />
		<!-- Get object definitions of connected objects -->
		<xsl:variable name="connectedObjs" select="$objDefinitions[@ObjDef.ID = $connectedOccs/@ObjDef.IdRef]" />
		<!-- Get all participant objects from the connected list -->
		<xsl:variable name="participantObjs" select="$connectedObjs[@TypeNum = 'OT_POS'] | $connectedObjs[@TypeNum = 'OT_PERS'] | $connectedObjs[@TypeNum = 'OT_PERS_TYPE'] | $connectedObjs[@TypeNum = 'OT_GRP'] | $connectedObjs[@TypeNum = 'OT_ORG_UNIT'] | $connectedObjs[@TypeNum = 'OT_ORG_UNIT_TYPE'] | $connectedObjs[@TypeNum = 'OT_SYS_ORG_UNIT'] | $connectedObjs[@TypeNum = 'OT_SYS_ORG_UNIT_TYPE'] | $connectedObjs[@TypeNum = 'OT_APPL_SYS'] | $connectedObjs[@TypeNum = 'OT_APPL_SYS_CLS'] | $connectedObjs[@TypeNum = 'OT_APPL_SYS_TYPE']" />
		
		<!-- If there is only one participant then add it as the performer, otherwise leave it as multiple participants will be added as a note  -->
		<xsl:if test="count ($participantObjs) = 1">
			<xpdl:Performer>
				<xsl:value-of select="count ($participantObjs/preceding-sibling::ObjDef) + 1" />
			</xpdl:Performer>
		</xsl:if>
		
	</xsl:template>
	
<!--
===================================================================================================================
	ObjOcc (parameters) = Identify any connected technical terms or ERM attributes - this will become parameters of this activity
===================================================================================================================
-->	
	<xsl:template match="ObjOcc" mode="parameters">
		<xsl:param name="objDefinitions" />

		<!-- Get the ID of this object -->
		<xsl:variable name="objOccID" select="@ObjOcc.ID" />
		<!-- Get all connections -->
		<xsl:variable name="cxn" select="CxnOcc" />
		
		<!-- Get all connected (both income and outgoing) object occurences  to this object -->
		<xsl:variable name="connectedObjOccs" select="../ObjOcc[@ObjOcc.ID = $cxn/@ToObjOcc.IdRef] | ../ObjOcc[CxnOcc/@ToObjOcc.IdRef = $objOccID]" />
		<!-- Get object definition for these object occurrences -->
		<xsl:variable name="connectedObjDefs" select="$objDefinitions[@ObjDef.ID = $connectedObjOccs/@ObjDef.IdRef]" />
		
		<!-- Get all technical types and ERM types -->
		<xsl:variable name="dtypes" select="$connectedObjDefs[@TypeNum = 'OT_TECH_TRM'] | $connectedObjDefs[@TypeNum = 'OT_ERM_ATTR']" />
	
		<xsl:if test="$dtypes">
			<xpdl:ActualParameters>
			<xsl:for-each select="$dtypes">
				<xpdl:ActualParameter><xsl:value-of select="count(preceding-sibling::ObjDef) + 1" /></xpdl:ActualParameter>
			</xsl:for-each>
			</xpdl:ActualParameters>
		</xsl:if>
	
		
	</xsl:template>
	
<!--
===================================================================================================================
	ObjOcc (transitions) = this will add the xpdl:Transition entries
===================================================================================================================
-->	
	<xsl:template match="CxnOcc">
		<xsl:param name="objDefinitions" />
		<xsl:param name="shiftX" />
		<xsl:param name="shiftY" />
	
		<!-- Get this object occurrence -->
		<xsl:variable name="thisObjOcc" select="../." />
		<!-- Get the object definition of this occurrence -->
		<xsl:variable name="objDef" select="$objDefinitions[@ObjDef.ID = $thisObjOcc/@ObjDef.IdRef]" />
		<!-- Get all object occurrence of this model -->
		<xsl:variable name="objOccs" select="../../ObjOcc" />
		
		<!-- Check if this object will be drawn as an activity.  If not then don't draw it's transitions -->
		<xsl:variable name="isActivity">
			<xsl:call-template name="isActivity">
				<xsl:with-param name="objOcc" select="$thisObjOcc" />
				<xsl:with-param name="objDef" select="$objDef" />
				<xsl:with-param name="allObjOccs" select="$objOccs" />
			</xsl:call-template>
		</xsl:variable>
		
		<!-- If this is an intermediate event object then ignore it -->
		<xsl:variable name="ignoreEventOcc">
			<xsl:call-template name="ignoreEventOcc">
				<xsl:with-param name="objOcc" select="$thisObjOcc" />
				<xsl:with-param name="allObjOccs" select="$objOccs" />
				<xsl:with-param name="objDefinitions" select="$objDefinitions" />
			</xsl:call-template>
		</xsl:variable>
		
		<xsl:if test="$isActivity = '&true;' and $ignoreEventOcc = '&false;'">	
			<!-- Get the id of the source of this transition -->
			<xsl:variable name="from" select="count ($thisObjOcc/preceding-sibling::ObjOcc) + 1" />
			
			<!-- If this is an OR or XOR operator object then get type -->
			<xsl:variable name="operator">
				<xsl:if test="$objDef/@TypeNum = 'OT_RULE'">
					<xsl:choose>
						<!-- OR operator -->
						<xsl:when test="$thisObjOcc/@SymbolNum = 'ST_OPR_OR_1' or $thisObjOcc/@SymbolNum = 'ST_OPR_OR_2' or $thisObjOcc/@SymbolNum = 'ST_OPR_OR_3' or $thisObjOcc/@SymbolNum = 'ST_OR'">
							<xsl:text>OR</xsl:text>
						</xsl:when>
						<!-- XOR operator -->
						<xsl:when test="$thisObjOcc/@SymbolNum = 'ST_OPR_XOR_1' or $thisObjOcc/@SymbolNum = 'ST_OPR_XOR_2' or $thisObjOcc/@SymbolNum = 'ST_OPR_XOR_3' or $thisObjOcc/@SymbolNum = 'ST_XOR'">
							<xsl:text>XOR</xsl:text>
						</xsl:when>
					</xsl:choose>
				</xsl:if>
			</xsl:variable>
			
			<!-- Get the target object occurrence ID -->
			<xsl:variable name="toObjOccId" select="@ToObjOcc.IdRef" />
			<!-- Get the to object occurrence -->
			<xsl:variable name="toObjOcc" select="$objOccs[@ObjOcc.ID = $toObjOccId]" />
			<!-- Get the to object definition -->
			<xsl:variable name="toObjDef" select="$objDefinitions[@ObjDef.ID = $toObjOcc/@ObjDef.IdRef]" />
			
			<!-- 
				If the object connected to will not be drawn as an activity then this transition should not be drawn.
			-->
			<xsl:variable name="isConnectedOccActivity">
				<xsl:call-template name="isActivity">
					<xsl:with-param name="objOcc" select="$toObjOcc" />
					<xsl:with-param name="objDef" select="$toObjDef" />
					<xsl:with-param name="allObjOccs" select="$objOccs" />
				</xsl:call-template>
			</xsl:variable>
			
			<xsl:if test="$isConnectedOccActivity = '&true;'">
			
				<xpdl:Transition Id="{position()}" From="{$from}">
					<!-- 
						If the following (connected) object is an event that has
						only ONE incoming and ONE outgoing connection then it will be omitted
					-->
					<xsl:variable name="bypassEvent">
						<!-- Only interested if the connected object is an event -->
						<xsl:if test="$toObjDef/@TypeNum = 'OT_EVT'">
							<!-- Get all objects connected to this event object -->
							<xsl:variable name="connectedToEvtObjOccs" select="$objOccs[CxnOcc/@ToObjOcc.IdRef = $toObjOcc/@ObjOcc.ID]" />
							<!-- This event should only have ONE incoming and ONE outgoing connection -->
							<xsl:if test="count ($connectedToEvtObjOccs) = 1 and count ($toObjOcc/CxnOcc) = 1">
								<xsl:text>&true;</xsl:text>
							</xsl:if>
						</xsl:if>
					</xsl:variable>
					
					<xsl:choose>
						<!-- If the following object is an event then this transition will connect to the target of this event.  The event will become the description of this transition -->
						<xsl:when test="$bypassEvent = '&true;'">
								<!-- Get the ID of the target object -->
								<xsl:attribute name="To"><xsl:value-of select="count ($objOccs[@ObjOcc.ID = $toObjOcc/CxnOcc/@ToObjOcc.IdRef]/preceding-sibling::ObjOcc) + 1" /></xsl:attribute>
								<xsl:attribute name="Name"><xsl:value-of select="normalize-space ($toObjDef/AttrDef[@AttrDef.Type = 'AT_NAME']/AttrValue)" /></xsl:attribute>
						</xsl:when>
						<xsl:otherwise>
							<xsl:variable name="cxnDefID" select="@CxnDef.IdRef" />
							<!-- Get the ID of the target object -->
							<xsl:attribute name="To"><xsl:value-of select="count ($toObjOcc/preceding-sibling::ObjOcc) + 1" /></xsl:attribute>
							<xsl:attribute name="Name"><xsl:value-of select="$objDef/CxnDef[@CxnDef.ID = $cxnDefID]/@CxnDef.Type" /></xsl:attribute>							
						</xsl:otherwise>
					</xsl:choose>
				
					<!-- If this is a transition from an OR or XOR operator then make transition conditional -->
					<xsl:if test="$operator = 'OR' or $operator = 'XOR'">
						<xpdl:Condition Type="CONDITION" />
					</xsl:if>
					
					<!-- 
						Insert bendpoints if any.  ARIS has atleast 2 points for each connection - the start and end points of the connection.
						Any other points are the bend points
					-->
					<xsl:if test="count (Position) &gt; 2 or ($bypassEvent = '&true;' and count ($toObjOcc/CxnOcc/Position) &gt; 2)">
											
						<xpdl:ExtendedAttributes>
								<xpdl:ExtendedAttribute Name="XPD">
									<extensions:Transition>
										<extensions:Router>
												<!-- Insert bendpoints for every bend coordinate in the connection -->
											<xsl:for-each select="Position">
												<xsl:if test="position() != 1 and position() != last()">
													<!--
														Calculate any deviation of this bend point from the previous and next bend point.  This is to identify if this point deviates slightly from a straight line 
														path, so as to avoid having unnecessary bend points in the transformed model.
													-->
													<xsl:variable name="diffX">
														<xsl:variable name="value" select="round (((@Pos.X - preceding-sibling::Position/@Pos.X) + (following-sibling::Position/@Pos.X - @Pos.X)) div 2)" />
														<!-- Return absolute value -->
														<xsl:choose>
															<xsl:when test="$value &lt; 0"><xsl:value-of select="-$value" /></xsl:when>
															<xsl:otherwise><xsl:value-of select="$value" /></xsl:otherwise>
														</xsl:choose>
													</xsl:variable>
													<xsl:variable name="diffY">
														<xsl:variable name="value" select="round (((@Pos.Y - preceding-sibling::Position/@Pos.Y) + (following-sibling::Position/@Pos.Y - @Pos.Y)) div 2)" />
														<!-- Return absolute value -->
														<xsl:choose>
															<xsl:when test="$value &lt; 0"><xsl:value-of select="-$value" /></xsl:when>
															<xsl:otherwise><xsl:value-of select="$value" /></xsl:otherwise>
														</xsl:choose>
													</xsl:variable>
													
													<!-- If no deviation identified then add the bendpoint - 30 units is an arbitrary value -->
													<xsl:if test="$diffX &gt; 30 and $diffY &gt; 30">
														<extensions:BendPoints>
															<extensions:Location>
																<extensions:XOffset><xsl:value-of select="round ((@Pos.X - $thisObjOcc/Position/@Pos.X - round ($thisObjOcc/Size/@Size.dX div 2)) * $factor)" /></extensions:XOffset>
																<extensions:YOffset><xsl:value-of select="round ((@Pos.Y - $thisObjOcc/Position/@Pos.Y - round ($thisObjOcc/Size/@Size.dY div 2)) * $factor)" /></extensions:YOffset>
																<xsl:choose>
																	<!-- If the target object in this connection is an event that will be omitted then the target of this transition should be the object connected to this event -->
																	<xsl:when test="$bypassEvent = '&true;'">
																		<xsl:variable name="target" select="$objOccs[@ObjOcc.ID = $toObjOcc/CxnOcc/@ToObjOcc.IdRef]" />
																		<extensions:Width><xsl:value-of select="round ((@Pos.X - $target/Position/@Pos.X - round ($target/Size/@Size.dX div 2)) * $factor)" /></extensions:Width>
																		<extensions:Height><xsl:value-of select="round ((@Pos.Y - $target/Position/@Pos.Y - round ($target/Size/@Size.dY div 2)) * $factor)" /></extensions:Height>
																	</xsl:when>
																	<xsl:otherwise>
																		<extensions:Width><xsl:value-of select="round ((@Pos.X - $toObjOcc/Position/@Pos.X - round ($toObjOcc/Size/@Size.dX div 2)) * $factor)" /></extensions:Width>
																		<extensions:Height><xsl:value-of select="round ((@Pos.Y - $toObjOcc/Position/@Pos.Y - round ($toObjOcc/Size/@Size.dY div 2)) * $factor)" /></extensions:Height>
																	</xsl:otherwise>
																</xsl:choose>
																
															</extensions:Location>
															<extensions:Weight>0.5</extensions:Weight>
														</extensions:BendPoints>														
													</xsl:if>																															

												</xsl:if>
											</xsl:for-each>
											<!-- 
												If the connected object is an event that will be omitted then the object the event connects to will become the target of this transition.  Add any bends from the transition
												between the event and it's target
											-->
											<xsl:if test="$bypassEvent = '&true;' and count ($toObjOcc/CxnOcc/Position) &gt; 2">
												<xsl:variable name="target" select="$objOccs[@ObjOcc.ID = $toObjOcc/CxnOcc/@ToObjOcc.IdRef]" />
												<xsl:for-each select="$toObjOcc/CxnOcc/Position">
													<xsl:if test="position() != 1 and position() != last()">
														<!--
															Calculate any deviation of this bend point from the previous and next bend point.  This is to identify if this point deviates slightly from a straight line 
															path, so as to avoid having unnecessary bend points in the transformed model.
														-->
														<xsl:variable name="diffX">
															<xsl:variable name="value" select="round (((@Pos.X - preceding-sibling::Position/@Pos.X) + (following-sibling::Position/@Pos.X - @Pos.X)) div 2)" />
															<!-- Return absolute value -->
															<xsl:choose>
																<xsl:when test="$value &lt; 0"><xsl:value-of select="-$value" /></xsl:when>
																<xsl:otherwise><xsl:value-of select="$value" /></xsl:otherwise>
															</xsl:choose>
														</xsl:variable>
														<xsl:variable name="diffY">
															<xsl:variable name="value" select="round (((@Pos.Y - preceding-sibling::Position/@Pos.Y) + (following-sibling::Position/@Pos.Y - @Pos.Y)) div 2)" />
															<!-- Return absolute value -->
															<xsl:choose>
																<xsl:when test="$value &lt; 0"><xsl:value-of select="-$value" /></xsl:when>
																<xsl:otherwise><xsl:value-of select="$value" /></xsl:otherwise>
															</xsl:choose>
														</xsl:variable>

														<!-- If no deviation identified then add the bendpoint -->
														<xsl:if test="$diffX &gt; 30 and $diffY &gt; 30">		
															<extensions:BendPoints>
																<extensions:Location>
																	<extensions:XOffset><xsl:value-of select="round ((@Pos.X - $thisObjOcc/Position/@Pos.X - round ($thisObjOcc/Size/@Size.dX div 2)) * $factor)" /></extensions:XOffset>
																	<extensions:YOffset><xsl:value-of select="round ((@Pos.Y - $thisObjOcc/Position/@Pos.Y - round ($thisObjOcc/Size/@Size.dY div 2)) * $factor)" /></extensions:YOffset>
																	<extensions:Width><xsl:value-of select="round ((@Pos.X - $target/Position/@Pos.X - round ($target/Size/@Size.dX div 2)) * $factor)" /></extensions:Width>
																	<extensions:Height><xsl:value-of select="round ((@Pos.Y - $target/Position/@Pos.Y - round ($target/Size/@Size.dY div 2)) * $factor)" /></extensions:Height>
																</extensions:Location>
																<extensions:Weight>0.5</extensions:Weight>
															</extensions:BendPoints>		
														</xsl:if>
														
													</xsl:if>
												</xsl:for-each>
											</xsl:if>
											
										</extensions:Router>
									</extensions:Transition>
								</xpdl:ExtendedAttribute>
							</xpdl:ExtendedAttributes>									
					</xsl:if>
	
				</xpdl:Transition>
			</xsl:if>
				
		</xsl:if>
		
	</xsl:template>
	
<!--
===================================================================================================================
	Template addDefaultPool = add the default pool and lane to the workflow process.  The max Y offset is calculated by sorting the Y offsets
		of the activities in the process and getting the highest value - this will make the size value of the lane.
===================================================================================================================
-->	
	<xsl:template name="addDefaultPool">
		<xsl:param name="objDefinitions" />
		<xsl:param name="objOccs" />
		<xsl:param name="textOccs" />
		<xsl:param name="modelAttrDef" />
		<xsl:param name="shiftX" />
		<xsl:param name="shiftY" />
		
		<!-- Work out the max Y offset to adjust the size of the default lane -->
		<xsl:variable name="maxYOffset"> 
			<xsl:choose>
				<xsl:when test="$objOccs">
					<xsl:for-each select="$objOccs">
						<xsl:sort data-type="number" order="descending" select="Position/@Pos.Y" /> 
						<!-- Get the first item in the sorted list - this should be the max Y offset -->		
						<xsl:if test="position() = 1">
							<xsl:value-of select="round (Position/@Pos.Y * $factor)" />
						</xsl:if>	
					</xsl:for-each>
				</xsl:when>
				<xsl:otherwise>
					<!-- No model so set lane size to 0 -->
					<xsl:text>0</xsl:text>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable> 
		
		<xpdl:ExtendedAttributes>
        <xpdl:ExtendedAttribute Name="XPD"><extensions:Diagram>
            <extensions:ZoomLevel>1.0</extensions:ZoomLevel>
            <extensions:Pool Id="0">
              <extensions:Name>Default Pool</extensions:Name>
              <extensions:IsVisible>true</extensions:IsVisible>
              <extensions:IsDefault>true</extensions:IsDefault>
              <extensions:Lane Id="0">
                <extensions:Name>Default Lane</extensions:Name>
				
				<!-- Add any notes -->
				<xsl:call-template name="addNotes">
					<xsl:with-param name="objDefinitions" select="$objDefinitions" />
					<xsl:with-param name="objOccs" select="$objOccs" />
					<xsl:with-param name="textOccs" select="$textOccs" />
					<xsl:with-param name="modelAttrDef" select="$modelAttrDef" />
					<xsl:with-param name="shiftX" select="$shiftX" />
					<xsl:with-param name="shiftY" select="$shiftY" />
				</xsl:call-template>
                
                <extensions:IsDefault>true</extensions:IsDefault>
                <!-- Add arbitrary value to show the lowest activity in the list without it overlapping the lane boundary -->
                <extensions:Size><xsl:value-of select="$maxYOffset + 90" /></extensions:Size>
              </extensions:Lane>
            </extensions:Pool>
            <!-- Add the note associations -->
            <xsl:call-template name="addNoteAssociations">
				<xsl:with-param name="objDefinitions" select="$objDefinitions" />
				<xsl:with-param name="objOccs" select="$objOccs" />
				<xsl:with-param name="textOccs" select="$textOccs" />
            </xsl:call-template>
            
          </extensions:Diagram></xpdl:ExtendedAttribute>
      </xpdl:ExtendedAttributes>
	
	</xsl:template>
	
<!--
===================================================================================================================
	|s  = Add any object with either no connections or one outgoing connection as a note.  The object with the outgoing connection will
===================================================================================================================
-->	
<xsl:template name="addNotes">
	<xsl:param name="objDefinitions" />
	<xsl:param name="objOccs" />
	<xsl:param name="textOccs" />
	<xsl:param name="modelAttrDef" />
	<xsl:param name="shiftX" />
	<xsl:param name="shiftY" />
	
	<xsl:for-each select="$objOccs">
		<!-- Get the object definition ID of this occurrence -->
		<xsl:variable name="objDefID" select="@ObjDef.IdRef" />
		<!-- Get the object definition of this occurrence -->
		<xsl:variable name="objDef" select="$objDefinitions[@ObjDef.ID = $objDefID]" />
		
		<!-- Check if this object has been added as an activity already -->
		<xsl:variable name="addedAsActivity">
			<xsl:call-template name="isActivity">
				<xsl:with-param name="objOcc" select="." />
				<xsl:with-param name="objDef" select="$objDef" />
				<xsl:with-param name="allObjOccs" select="$objOccs" />
			</xsl:call-template>
		</xsl:variable>
	
		<!-- If this hasn't been drawn as an activity, and is not a data type then it's potentially a note in the process -->
		<xsl:if test="$addedAsActivity = '&false;' and $objDef/@TypeNum != 'OT_ERM_ATTR' and $objDef/@TypeNum != 'OT_TECH_TRM'">
			<!-- Get the note text -->
			<xsl:variable name="noteText">
				<!-- 
					If this is a participant object then check that it's the only one connected to the target object.  If it is then ignore it as it will have been added as the target object's participant.  
					Otherwise, as the target object has multiple participants add all of them as a single note
				-->
				<xsl:choose>
					<xsl:when test="contains ('&arisParticipantTypes;', $objDef/@TypeNum)">
					
						<!-- If this participant object has no outgoing connections then add it as a note -->
						<xsl:choose>
							<xsl:when test="count (CxnOcc) = 0">
								<xsl:value-of select="normalize-space ($objDef/AttrDef[@AttrDef.Type = 'AT_NAME']/AttrValue)" />
							</xsl:when>
							<!-- Participant object has out-going connections -->
							<xsl:otherwise>						
								<!-- Get the object occ connected to this participant object -->							
								<xsl:variable name="connectedToId" select="CxnOcc/@ToObjOcc.IdRef" />
								<xsl:variable name="objConnectedToThisObj" select="$objDefinitions[@ObjDef.ID = $objOccs[CxnOcc/@ToObjOcc.IdRef = $connectedToId]/@ObjDef.IdRef]" />					
								<!-- Get all peer participant objects -->
								<xsl:variable name="participantObjs" select="$objConnectedToThisObj[@TypeNum = 'OT_POS'] | $objConnectedToThisObj[@TypeNum = 'OT_PERS'] | $objConnectedToThisObj[@TypeNum = 'OT_PERS_TYPE'] | $objConnectedToThisObj[@TypeNum = 'OT_GRP'] | $objConnectedToThisObj[@TypeNum = 'OT_ORG_UNIT'] | $objConnectedToThisObj[@TypeNum = 'OT_ORG_UNIT_TYPE'] | $objConnectedToThisObj[@TypeNum = 'OT_SYS_ORG_UNIT'] | $objConnectedToThisObj[@TypeNum = 'OT_SYS_ORG_UNIT_TYPE'] | $objConnectedToThisObj[@TypeNum = 'OT_APPL_SYS'] | $objConnectedToThisObj[@TypeNum = 'OT_APPL_SYS_CLS'] | $objConnectedToThisObj[@TypeNum = 'OT_APPL_SYS_TYPE']" />
		
								<!-- If there are more than 1 participant and this is the first participant entry for the 'parent' object then print all participants together at this location -->
								<xsl:if test="count ($participantObjs) &gt; 1 and $participantObjs[1] = $objDef">
											<xsl:text>Participants:</xsl:text>
											<xsl:for-each select="$participantObjs">
												<xsl:text>&crlf;</xsl:text>
												<xsl:text>  </xsl:text><xsl:value-of select="normalize-space (AttrDef[@AttrDef.Type = 'AT_NAME']/AttrValue)" />
											</xsl:for-each>
								</xsl:if>			
							</xsl:otherwise>
						</xsl:choose>
						
					</xsl:when>
					<xsl:otherwise>						
						<!-- Add note -->
						<xsl:value-of select="$objDef/AttrDef[@AttrDef.Type = 'AT_NAME']/AttrValue" />
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
		
			<!-- Add the note if required -->
			<xsl:if test="$noteText != ''">
				<extensions:Note Id="n{count (preceding-sibling::ObjOcc | preceding-sibling::FFTextOcc) + 1}">
					<extensions:Location>
						<extensions:XOffset><xsl:value-of select="round ((Position/@Pos.X + round (Size/@Size.dX div 2) + $shiftX) * $factor)" /></extensions:XOffset>
						<extensions:YOffset><xsl:value-of select="round ((Position/@Pos.Y + round (Size/@Size.dY div 2) + $shiftY) * $factor)" /></extensions:YOffset>
						<extensions:Width><xsl:value-of select="round (Size/@Size.dX * $factor)" /></extensions:Width>
						<extensions:Height><xsl:value-of select="round (Size/@Size.dY * $factor)" /></extensions:Height>
					</extensions:Location>
					<extensions:Text>
						<xsl:value-of select="$noteText" />
					</extensions:Text>
				</extensions:Note>							
			</xsl:if>
		
		</xsl:if>
		
	</xsl:for-each>
	
	<!-- Get all annotations in the model and place them as notes -->
		<xsl:for-each select="$textOccs"> 
			<!-- Get text definition ID -->
			<xsl:variable name="textDefID" select="@FFTextDef.IdRef" />
			<extensions:Note Id="n{count (preceding-sibling::ObjOcc | preceding-sibling::FFTextOcc) + 1}">
				<extensions:Location>
					<extensions:XOffset><xsl:value-of select="round ((Position/@Pos.X + $shiftX) * $factor)" /></extensions:XOffset>
					<extensions:YOffset><xsl:value-of select="round ((Position/@Pos.Y+ $shiftY) * $factor)" /></extensions:YOffset>
				</extensions:Location>
				<extensions:Text>
					<xsl:choose>
						<!-- annotation -->
						<xsl:when test="/AML/FFTextDef[@FFTextDef.ID = $textDefID]/@IsModelAttr = 'TEXT'">
							<xsl:value-of select="/AML/FFTextDef[@FFTextDef.ID = $textDefID]/AttrDef[@AttrDef.Type = 'AT_NAME']/AttrValue" />
						</xsl:when>
						<!-- Model attribute -->
						<xsl:when test="/AML/FFTextDef[@FFTextDef.ID = $textDefID]/@IsModelAttr = 'MODELATTR'">
							<!-- Get attribute type -->
							<xsl:variable name="attrType" select="//AML/FFTextDef[@FFTextDef.ID = $textDefID]/AttrDef[@AttrDef.Type = 'AT_MODEL_AT']/AttrValue" />
							<!-- If attribute type needs to be output then add it to the note -->
							<xsl:if test="@SymbolFlag = 'ATTRNAME_AND_POSTIT'">
								<xsl:value-of select="$attrType" /><xsl:text>: </xsl:text>
							</xsl:if>
							<xsl:choose>
								<!-- If the status of the model is being printed then need to translate the numeric code to status -->
								<xsl:when test="$attrType = 'AT_STATE_1'">
									<xsl:variable name="attrValue" select="$modelAttrDef[@AttrDef.Type = $attrType]/AttrValue" />
									<xsl:choose>
										<xsl:when test="$attrValue = '&arisStateBeingProcessedValue;'">
											<!-- Being processed -->
											<xsl:text>&arisStateBeingProcessed;</xsl:text>
										</xsl:when>
										<xsl:when test="$attrValue = '&arisStateCompleteValue;'">
											<!-- Complete -->
											<xsl:text>&arisStateComplete;</xsl:text>
										</xsl:when>
										<xsl:when test="$attrValue = '&arisStateReleasedValue;'">
											<!-- Released -->
											<xsl:text>&arisStateReleased;</xsl:text>
										</xsl:when>
										<xsl:when test="$attrValue = '&arisStateLastChangeValue;'">
											<!-- Last Change -->
											<xsl:text>&arisStateLastChange;</xsl:text>
										</xsl:when>
										<xsl:when test="$attrValue = '&arisStateToBeEditedValue;'">
											<!-- To be edited -->
											<xsl:text>&arisStateToBeEdited;</xsl:text>
										</xsl:when>
										<xsl:otherwise>
											<xsl:value-of select="$attrValue" />
										</xsl:otherwise>
									</xsl:choose>
								</xsl:when>
								<xsl:otherwise>
									<!-- Get the value of the model attribute -->
									<xsl:value-of select="$modelAttrDef[@AttrDef.Type = $attrType]/AttrValue" />
								</xsl:otherwise>
							</xsl:choose>
							
						</xsl:when>
					</xsl:choose>
				</extensions:Text>
			</extensions:Note>
			
		</xsl:for-each>
	
</xsl:template>


<!--
===================================================================================================================
	addNoteAssociations  = Add the association between the notes and their respective activities
===================================================================================================================
-->	
<xsl:template name="addNoteAssociations">
	<xsl:param name="objDefinitions" />
	<xsl:param name="objOccs" />
	<xsl:param name="textOccs" />
	
	<xsl:for-each select="$objOccs">
		<!-- Get the object definition ID of this occurrence -->
		<xsl:variable name="objDefID" select="@ObjDef.IdRef" />
		<!-- Get the object definition of this occurrence -->
		<xsl:variable name="objDef" select="$objDefinitions[@ObjDef.ID = $objDefID]" />
		
		<!-- Check if this object has been added as an activity already -->
		<xsl:variable name="addedAsActivity">
			<xsl:call-template name="isActivity">
				<xsl:with-param name="objOcc" select="." />
				<xsl:with-param name="objDef" select="$objDef" />
				<xsl:with-param name="allObjOccs" select="$objOccs" />
			</xsl:call-template>
		</xsl:variable>
	
		<!-- If this hasn't been drawn as an activity, and is not a data type then it's potentially a note in the process -->
		<xsl:if test="$addedAsActivity = '&false;' and $objDef/@TypeNum != 'OT_ERM_ATTR' and $objDef/@TypeNum != 'OT_TECH_TRM'">
	
			<!-- 
				If this is a participant object then check that it's the only one connected to the target object.  If it is then ignore it as it will have been added as the target object's participant.  
				Otherwise, as the target object has multiple participants add all of them as a single note
			-->
			<xsl:choose>
				<xsl:when test="contains ('&arisParticipantTypes;', $objDef/@TypeNum)">
				
					<!-- If this participant object has outgoing connections then add the association -->
					<xsl:if test="count (CxnOcc) &gt; 0">
						<!-- Get the object occ connected to this participant object -->							
						<xsl:variable name="connectedToId" select="CxnOcc/@ToObjOcc.IdRef" />
						<xsl:variable name="objConnectedToThisObj" select="$objDefinitions[@ObjDef.ID = $objOccs[CxnOcc/@ToObjOcc.IdRef = $connectedToId]/@ObjDef.IdRef]" />					
						<!-- Get all peer participant objects -->
						<xsl:variable name="participantObjs" select="$objConnectedToThisObj[@TypeNum = 'OT_POS'] | $objConnectedToThisObj[@TypeNum = 'OT_PERS'] | $objConnectedToThisObj[@TypeNum = 'OT_PERS_TYPE'] | $objConnectedToThisObj[@TypeNum = 'OT_GRP'] | $objConnectedToThisObj[@TypeNum = 'OT_ORG_UNIT'] | $objConnectedToThisObj[@TypeNum = 'OT_ORG_UNIT_TYPE'] | $objConnectedToThisObj[@TypeNum = 'OT_SYS_ORG_UNIT'] | $objConnectedToThisObj[@TypeNum = 'OT_SYS_ORG_UNIT_TYPE'] | $objConnectedToThisObj[@TypeNum = 'OT_APPL_SYS'] | $objConnectedToThisObj[@TypeNum = 'OT_APPL_SYS_CLS'] | $objConnectedToThisObj[@TypeNum = 'OT_APPL_SYS_TYPE']" />

						<!-- If there are more than 1 participant and this is the first participant entry for the 'parent' object then print all participants together at this location -->
						<xsl:if test="count ($participantObjs) &gt; 1 and $participantObjs[1] = $objDef">
							<extensions:Association>
							  <extensions:Source>n<xsl:value-of select="count (preceding-sibling::ObjOcc | preceding-sibling::FFTextOcc) + 1" /></extensions:Source>
							  <extensions:Target><xsl:value-of select="count ($objOccs[@ObjOcc.ID = $connectedToId]/preceding-sibling::ObjOcc) + 1" /></extensions:Target>
							</extensions:Association>								
						</xsl:if>			
					</xsl:if>
					
				</xsl:when>
				<xsl:otherwise>						
					<!-- Add association -->
					<!-- Get ID of this occ -->
					<xsl:variable name="objOccID" select="@ObjOcc.ID" />
					<xsl:variable name="connectedToId" select="CxnOcc/@ToObjOcc.IdRef" />
					<!-- get the target activity -->
					<xsl:variable name="target" select="$objOccs[@ObjOcc.ID = $connectedToId] | $objOccs[CxnOcc/@ToObjOcc.IdRef = $objOccID]" />
					<xsl:if test="count ($target) = 1">
						<extensions:Association>
							  <extensions:Source>n<xsl:value-of select="count (preceding-sibling::ObjOcc | preceding-sibling::FFTextOcc) + 1" /></extensions:Source>
							  <extensions:Target><xsl:value-of select="count ($target/preceding-sibling::ObjOcc) + 1" /></extensions:Target>
						</extensions:Association>
					</xsl:if>
				</xsl:otherwise>
			</xsl:choose>
	
		</xsl:if>
		
	</xsl:for-each>
	
</xsl:template>


<!--
===================================================================================================================
	calculateXShift = Calculates the X shift required to reposition the model so that no part of the model appears outside the left-side of the lane
===================================================================================================================
-->
	<xsl:template name="calculateXShift">
		<!-- 
			Function objects will have to be centered (in XPD terms) so calculate the min X position after
			centering the objects (x + (width/2) - (XPD activity height / 2))
		-->
		<xsl:variable name="minX">
			<xsl:for-each select="ObjOcc">
				<xsl:sort data-type="number" order="ascending" select="Position/@Pos.X + round (Size/@Size.dX div 2) - round (&activityWidth; div 2)" />
				<!-- Return the first item as this will be the min value -->
				<xsl:if test="position () = 1">
					<xsl:value-of select="Position/@Pos.X + round (Size/@Size.dX div 2) - round (&activityWidth; div 2)" />
				</xsl:if>				
			</xsl:for-each>
		</xsl:variable>
		
		<!-- If the min value is less than 0 units then calculate the difference - this will be the shift required -->
		<xsl:choose>
			<xsl:when test="$minX &lt; 0">
				<xsl:value-of select="-$minX" /> 
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>0</xsl:text>
			</xsl:otherwise>
		</xsl:choose>
		
	</xsl:template>
	
<!--
===================================================================================================================
	calculateYShift = Calculates the Y shift required to reposition the model so that no part of the model appears outside the top-edge of the lane
===================================================================================================================
-->
	<xsl:template name="calculateYShift">
			<!-- 
			Function objects will have to be centered so calculate the min Y position after
			centering the objects (y - (height/2) - (XPD Activity height / 2))
		-->
		<xsl:variable name="minY">
			<xsl:for-each select="ObjOcc">
				<xsl:sort data-type="number" order="ascending" select="Position/@Pos.Y + round (Size/@Size.dY div 2) - round (&activityHeight; div 2)" />
				<!-- Return the first item as this will be the min value -->
				<xsl:if test="position () = 1">
					<xsl:value-of select="Position/@Pos.Y + round (Size/@Size.dY div 2) - round (&activityHeight; div 2)" />
				</xsl:if>				
			</xsl:for-each>
		</xsl:variable>
		
		<!-- If the min value is less than 0 units then calculate the difference - this will be the shift required -->
		<xsl:choose>
			<xsl:when test="$minY &lt; 0">
				<xsl:value-of select="-$minY" /> 
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>0</xsl:text>
			</xsl:otherwise>
		</xsl:choose>
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
