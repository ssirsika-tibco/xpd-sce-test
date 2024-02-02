<?xml version="1.0" encoding="UTF-8"?>
<!--
	===================================================================================================================
	XSLT:       MigrateStudioXPDL_999.xslt
	
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

		- Convert (as much as possible) JavaScript data mappings to datamapper mappings on sub-process, global signal, local signal, catch error  

		- Remove current configuration information from REST and WEB service system participants (as in SCE the configuration UI 
		  and model will be different).
	
		- Remove Allowed Values configuration from all non-text process  parameters (to put it on an even footing with 
		  BOM/CM property capabilities, I don't think this is widely used anyway - UI should provide min/max values for process data instead)
		  
		- Convert Integer data fields and parameters to Decimals with num decimals = 0
		
		- Remove process package version (we only use project version now).
		
		- Remove ReceiveTask web-service configuration
		
		- Sid ACE-7608 Remove xpdExt:BxUseUnqualifiedPropertyNames and <xpdExt:BpmRuntimeConfiguration /> 
		
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
	Remove current configuration information from WEB service system participants 
	(as in SCE the configuration UI and model will be different).
	
	Sid ACE-7117 Do not remove whole JDBC participant - just the JdbcProfileName attribute
	Sid ACE-479: Do not remove the entire xpdExt:RestServie element as it is this that 
	             defines the participant as REST type (instead, the next template will 
	             remove all of the configuration within it.
	
	===============================================================================
    -->
	<xsl:template match="xpdl2:Participant/xpdExt:ParticipantSharedResource[xpdExt:WebService]">
		<!-- Do nothing (e.g. do not output the element)-->
	</xsl:template>
	
	
	<!-- 
	================================================================================================================
	ACE-7188 :  On migration from 4.x convert all SOAP-service consumer participants to REST service consumer participants.
	================================================================================================================
	 -->
    <xsl:template match="xpdl2:Participant/xpdExt:ParticipantSharedResource[xpdExt:WebService/xpdExt:Outbound]">
		<!-- Just output the bare element -->
		 <xpdExt:ParticipantSharedResource>
		 	<xpdExt:RestService>
	             <xsl:if test="xpdExt:WebService/xpdExt:Outbound/xpdExt:SoapHttpBinding/@HttpClientInstanceName">
	                <xsl:attribute name="resourceName"><xsl:value-of select="xpdExt:WebService/xpdExt:Outbound/xpdExt:SoapHttpBinding/@HttpClientInstanceName"/></xsl:attribute>
	             </xsl:if>
		 	</xpdExt:RestService>
		 </xpdExt:ParticipantSharedResource>
	</xsl:template>

	<xsl:template match="xpdl2:Participant/xpdExt:ParticipantSharedResource/xpdExt:Jdbc">
		<xpdExt:Jdbc>
			<xsl:if test="@InstanceName">
				<xsl:attribute name="InstanceName"><xsl:value-of select="@InstanceName"/></xsl:attribute>
			</xsl:if>
		</xpdExt:Jdbc>
	</xsl:template>

	<xsl:template match="xpdl2:Participant/xpdExt:ParticipantSharedResource/xpdExt:RestService">
		<!-- Just output the bare element -->
		<xpdExt:RestService>
		
			<!-- 
			================================================================================================================
			ACE-7329 : On migration from 4.x a REST service system participant's shared resource name must be preserved.
					   i.e. @HttpClientInstanceName must be preserved into @resourceName attribute for <xpdExt:RestService>
			================================================================================================================		   
			 -->
			<xsl:choose>
		        <xsl:when test="@HttpClientInstanceName">
		        		<xsl:attribute name="resourceName"><xsl:value-of select="@HttpClientInstanceName"/></xsl:attribute>
		        </xsl:when>
		       <!-- In case it is already migrated (originally we did that format version 23 before changeing to 1000 copy the new ACE attrib values  -->
 		        <xsl:when test="@resourceName">
						<xsl:attribute name="resourceName"><xsl:value-of select="@resourceName"/></xsl:attribute>
				</xsl:when>
	       </xsl:choose>
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
	Convert JavaScript data mappings to DataMapper mappings...
	SUB-PROCESS INVOCATION
	===============================================================================
    -->
    <xsl:template match="xpdl2:SubFlow">
    
    	<xsl:copy> <!--  xpdl2:Subflow -->
    	
    		<!-- Copy / remove etc most of the content according to other templates -->
    		<xsl:apply-templates select="@* | node() | text()"/>
    		
    		<!--  
    			INPUT mappings... 
    			 If there are old javaScript input data mappings convert them to DataMapper mappings 
    			 (ensuring we don't also already have DataMapper input mappings just in case) 
    		-->
    		<xsl:if test="xpdl2:DataMappings/xpdl2:DataMapping[@Direction = 'IN'] and not(xpdExt:InputMappings)">
    			
    			<xpdExt:InputMappings MapperContext="ProcessToSubProcess" MappingDirection="IN">
    				<xpdExt:DataMappings>
    				
    					<xsl:for-each select="xpdl2:DataMappings/xpdl2:DataMapping[@Direction = 'IN']">
    						<xsl:choose>
    							<xsl:when test="xpdExt:ScriptInformation">
    								<!-- Input Script mapping - almost the same EXCEPT in JavaScript mapping the script is in xpdl2:Actual -->
									<xpdExt:DataMapping xpdExt:TargetContributorId="ProcessToSubProcess.DataMapperContent" 
														Direction="IN" 
														Formal="{@Formal}">
										<xpdExt:ScriptInformation Id="{xpdExt:ScriptInformation/@Id}" Name="{xpdExt:ScriptInformation/@Name}">
											<xpdExt:Expression ScriptGrammar="JavaScript"><xsl:value-of select="xpdl2:Actual"/></xpdExt:Expression>
										</xpdExt:ScriptInformation>
										
										<!--  And xpdl2:Actual is hard coded... -->
										<xpdl2:Actual ScriptGrammar="JavaScript">__SCRIPT__</xpdl2:Actual>
									</xpdExt:DataMapping>
    							</xsl:when>
    							<xsl:otherwise>
    								<!-- Standard non-scripted mapping -->
		                  			<xpdExt:DataMapping xpdExt:SourceContributorId="ActivityInterface.DataMapperContent" 
		                  								xpdExt:TargetContributorId="ProcessToSubProcess.DataMapperContent" 
		                  								Direction="IN" 
		                  								Formal="{@Formal}">
		                    			<xsl:apply-templates select="xpdl2:Actual"/>
		                  			</xpdExt:DataMapping>    						
    							</xsl:otherwise>
    						</xsl:choose>
    					
    					</xsl:for-each>
    					
    				</xpdExt:DataMappings>
    				
    				<!--
    					(As yet) Unmapped script mappings (move from xpdl2:activity to xpdExt:UnmappedScripts  
    				 -->
    				<xsl:if test="count(ancestor::xpdl2:Activity/xpdExt:ScriptInformation[@Name != '' and (not(@Direction) or @Direction != 'OUT')]) > 0">
    					<xpdExt:UnmappedScripts>
    						<xsl:copy-of select="ancestor::xpdl2:Activity/xpdExt:ScriptInformation[@Name != '' and (not(@Direction) or @Direction != 'OUT')]"/>
    					</xpdExt:UnmappedScripts>
    				</xsl:if>
    				
    			</xpdExt:InputMappings>
    		
    		</xsl:if> <!--  INPUT mappings -->

    		<!-- 
    			OUPUT mappings... 
    			 If there are old javaScript output data mappings convert them to DataMapper mappings 
    			 (ensuring we don't also already have DataMapper output mappings just in case) 
    		-->
    		<xsl:if test="xpdl2:DataMappings/xpdl2:DataMapping[@Direction = 'OUT'] and not(xpdExt:OutputMappings)">
    			
    			<xpdExt:OutputMappings MapperContext="SubProcessToProcess" MappingDirection="OUT">
    				<xpdExt:DataMappings>
    				
    					<xsl:for-each select="xpdl2:DataMappings/xpdl2:DataMapping[@Direction = 'OUT']">
    						<xsl:choose>
    							<xsl:when test="xpdExt:ScriptInformation">
    								<!-- Output Script mapping (@Formal comes from old xpdl2:Actual)-->
									<xpdExt:DataMapping xpdExt:TargetContributorId="ActivityInterface.DataMapperContent" 
														Direction="IN" 
														Formal="{xpdl2:Actual/text()}">
										<!-- And ScriptInformation is identical for output mappings -->
										<xsl:copy-of select="xpdExt:ScriptInformation"/>
										
										<!--  And xpdl2:Actual is hard coded... -->
										<xpdl2:Actual ScriptGrammar="JavaScript">__SCRIPT__</xpdl2:Actual>
									</xpdExt:DataMapping>
    							</xsl:when>
    							<xsl:otherwise>
    								<!-- Standard non-scripted mapping (@Formal swaps with xpdl2:Actual)-->
		                  			<xpdExt:DataMapping xpdExt:SourceContributorId="SubProcessToProcess.DataMapperContent" 
		                  								xpdExt:TargetContributorId="ActivityInterface.DataMapperContent" 
		                  								Direction="IN" 
		                  								Formal="{xpdl2:Actual/text()}">
		                    			<xpdl2:Actual ScriptGrammar="JavaScript"><xsl:value-of select="@Formal"/></xpdl2:Actual>
		                  			</xpdExt:DataMapping>    						
    							</xsl:otherwise>
    						</xsl:choose>
    					
    					</xsl:for-each>

    				</xpdExt:DataMappings>

    				<!--
    					(As yet) Unmapped script mappings (move from xpdl2:activity to xpdExt:UnmappedScripts  
    				 -->
    				<xsl:if test="count(ancestor::xpdl2:Activity/xpdExt:ScriptInformation[@Name != '' and @Direction = 'OUT']) > 0">
    					<xpdExt:UnmappedScripts>
    						<xsl:copy-of select="ancestor::xpdl2:Activity/xpdExt:ScriptInformation[@Name != '' and @Direction = 'OUT']"/>
    					</xpdExt:UnmappedScripts>
    				</xsl:if>
    				
    			</xpdExt:OutputMappings>
    		
    		</xsl:if> <!-- OUTPUT mappings -->

    		
    	</xsl:copy> <!-- xpdl2:Subflow -->
    
    </xsl:template>
    
    <!--
	===============================================================================
	Convert JavaScript data mappings to DataMapper mappings...
	CATCH ERROR MAPPINGS
	===============================================================================
    -->
    <xsl:template match="xpdl2:ResultError/xpdExt:CatchErrorMappings/xpdExt:Message">
    
        <xsl:copy> <!-- xpdl2:Message -->    
    		<!-- apply templates to get existing content copied / removed as required before converting mappings if necessary. -->
    		<xsl:apply-templates select="@* | node() | text()"/>
    		

    		<!-- Sid ACE-3045 Track the activity that this error event is attached to. -->    		
    		<xsl:variable name="errorThrowerActivityId" select="ancestor::xpdl2:IntermediateEvent/@Target"/>
    		<xsl:variable name="errorThrowerActivity" select="ancestor::xpdl2:ActivitySet/xpdl2:Activities/xpdl2:Activity[@Id = $errorThrowerActivityId] 
    														| ancestor::xpdl2:WorkflowProcess/xpdl2:Activities/xpdl2:Activity[@Id = $errorThrowerActivityId]"/>
    		
    		<!-- And its type -->
			<xsl:variable name="errorThrowerType">
				<xsl:choose>
					<!--  CatchAll takes priority as it may be used regardless of specific error thrower activity type -->
					<xsl:when test="not(ancestor::xpdl2:ResultError/xpdExt:ErrorThrowerInfo)">CatchAll</xsl:when>
					<xsl:when test="$errorThrowerActivity/xpdl2:Implementation/xpdl2:SubFlow">SubProcess</xsl:when>
					<xsl:when test="$errorThrowerActivity/xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService[@xpdExt:ImplementationType = 'GlobalData']">GlobalData</xsl:when>
					<xsl:otherwise>Unknown</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			

    		<!-- 
    			OUPUT mappings... 
    			 If there are old javaScript output data mappings convert them to DataMapper mappings 
    			 (ensuring we don't also already have DataMapper output mappings just in case) 
    		-->
    		<xsl:if test="xpdl2:DataMappings/xpdl2:DataMapping[@Direction = 'OUT'] and not(xpdExt:OutputMappings)">
    			
    			<xpdExt:OutputMappings MappingDirection="OUT">
    				<!-- Sid ACE-3045 - A difference between CATCH all and CATCH SPECIFIC SUB-PROCESS (or REST service or Case Data Task) is the mapper context -->
    				<xsl:attribute name="MapperContext">
						<xsl:choose>
							<xsl:when test="$errorThrowerType = 'SubProcess'">CatchSubProcessError</xsl:when>
							<xsl:when test="$errorThrowerType = 'GlobalData'">GDFaultCatch</xsl:when>
							<xsl:otherwise>CatchAll</xsl:otherwise>
						</xsl:choose>
    				</xsl:attribute>
    			
    				<xpdExt:DataMappings>
    				
    					<xsl:for-each select="xpdl2:DataMappings/xpdl2:DataMapping[@Direction = 'OUT']">

				    		<!-- Sid ACE-3045 A difference between CATCH all and CATCH SPECIFIC TASK is the source contributor id. -->
							<xsl:variable name="srcContribId">
								<xsl:choose>
									<xsl:when test="$errorThrowerType = 'SubProcess' and starts-with(@Formal, '$ERROR')">CatchSubProcessError.DataMapperContent</xsl:when>
									<xsl:when test="$errorThrowerType = 'SubProcess'">CatchSubProcessErrorProcessData.DataMapperContent</xsl:when>
									<xsl:when test="$errorThrowerType = 'GlobalData'">GDFaultCatch.DataMapperContent</xsl:when>
									<xsl:when test="$errorThrowerType = 'CatchAll'">CatchAll.DataMapperContent</xsl:when>
									<xsl:otherwise>Unknown.DataMapperContent</xsl:otherwise>
								</xsl:choose>
							</xsl:variable>

    						<xsl:choose>
    							<xsl:when test="xpdExt:ScriptInformation">
    								<!-- Output Script mapping (@Formal comes from old xpdl2:Actual)-->
									<xpdExt:DataMapping xpdExt:TargetContributorId="ActivityInterface.DataMapperContent" 
														Direction="IN" 
														Formal="{xpdl2:Actual/text()}">
										<!-- And ScriptInformation is identical for output mappings -->
										<xsl:copy-of select="xpdExt:ScriptInformation"/>
										
										<!--  And xpdl2:Actual is hard coded... -->
										<xpdl2:Actual ScriptGrammar="JavaScript">__SCRIPT__</xpdl2:Actual>
									</xpdExt:DataMapping>
    							</xsl:when>
    							<xsl:otherwise>
    								<!-- Standard non-scripted mapping (@Formal swaps with xpdl2:Actual)-->
		                  			<xpdExt:DataMapping xpdExt:SourceContributorId="{$srcContribId}" 
		                  								xpdExt:TargetContributorId="ActivityInterface.DataMapperContent" 
		                  								Direction="IN" 
		                  								Formal="{xpdl2:Actual/text()}">
		                    			<xpdl2:Actual ScriptGrammar="JavaScript"><xsl:value-of select="@Formal"/></xpdl2:Actual>
		                  			</xpdExt:DataMapping>    						
    							</xsl:otherwise>
    						</xsl:choose>
    					
    					</xsl:for-each>

    				</xpdExt:DataMappings>

    				<!--
    					(As yet) Unmapped script mappings (move from xpdl2:activity to xpdExt:UnmappedScripts  
    				 -->
    				<xsl:if test="count(ancestor::xpdl2:Activity/xpdExt:ScriptInformation[@Name != '' and @Direction = 'OUT']) > 0">
    					<xpdExt:UnmappedScripts>
    						<xsl:copy-of select="ancestor::xpdl2:Activity/xpdExt:ScriptInformation[@Name != '' and @Direction = 'OUT']"/>
    					</xpdExt:UnmappedScripts>
    				</xsl:if>
    				
    			</xpdExt:OutputMappings>
    		
    		</xsl:if> <!-- OUTPUT mappings -->

    		
    	</xsl:copy> <!-- xpdl2:Message -->    
    </xsl:template>
    
    
    
	<!--
	===============================================================================
	Convert JavaScript data mappings to DataMapper mappings...
	THROW GLOBAL SIGNAL
	===============================================================================
    -->
    <xsl:template match="xpdl2:TriggerResultSignal[@CatchThrow = 'THROW']/xpdExt:SignalData">
	
		<xsl:copy>  <!-- xpdExt:SignalData -->
			<!-- apply templates to get existing content copied / removed as required before converting mappings if necessary. -->
    		<xsl:apply-templates select="@* | node() | text()"/>

    		<!-- Only do anything if not DataMapper grammar already (just in case there is some early ACE dev xpdl's with these in but not on latest FormatVersion 1000) -->
    		<xsl:if test="not(xpdExt:InputScriptDataMapper)">
			
				<xpdExt:InputScriptDataMapper MapperContext="GlobalSignalThrow" MappingDirection="IN">
					<xpdExt:DataMappings>
						<!-- Convert correlation and normal mappings the ONLY difference is in the xpdExt:TargetContributorId -->
						<xsl:for-each select="xpdExt:CorrelationMappings/xpdExt:DataMapping | xpdExt:DataMappings/xpdExt:DataMapping">
						
							<xsl:variable name="targetContributorId">
								<xsl:choose>
									<xsl:when test="ancestor::xpdExt:CorrelationMappings">MapToGlobalSignalCorrelation.DataMapperContent</xsl:when>
									<xsl:otherwise>MapToGlobalSignal.DataMapperContent</xsl:otherwise>
								</xsl:choose>
							</xsl:variable>
	
	  						<xsl:choose>
	  							<xsl:when test="xpdExt:ScriptInformation">
	  								<!-- Script mapping - almost the same EXCEPT in JavaScript mapping the script is in xpdl2:Actual -->
									<xpdExt:DataMapping xpdExt:TargetContributorId="{$targetContributorId}" Direction="IN" Formal="{@Formal}">
	
										<xpdExt:ScriptInformation Id="{xpdExt:ScriptInformation/@Id}" Name="{xpdExt:ScriptInformation/@Name}">
											<xpdExt:Expression ScriptGrammar="JavaScript"><xsl:value-of select="xpdl2:Actual"/></xpdExt:Expression>
										</xpdExt:ScriptInformation>
										
										<!--  And xpdl2:Actual is hard coded... -->
										<xpdl2:Actual ScriptGrammar="JavaScript">__SCRIPT__</xpdl2:Actual>
	
									</xpdExt:DataMapping>
	
								</xsl:when>
								<xsl:otherwise>
	   								<!-- Standard non-scripted mapping -->
									<xpdExt:DataMapping xpdExt:SourceContributorId="ActivityInterface.DataMapperContent" xpdExt:TargetContributorId="{$targetContributorId}" 
											Direction="IN" Formal="{@Formal}">
										<xsl:apply-templates select="xpdl2:Actual"/>
									</xpdExt:DataMapping>
						
								</xsl:otherwise>
							</xsl:choose>	
							
						</xsl:for-each>
						
						<!--
	    					(As yet) Unmapped script mappings (move from xpdl2:activity to xpdExt:UnmappedScripts  
	    				 -->
	    				<xsl:if test="count(ancestor::xpdl2:Activity/xpdExt:ScriptInformation[@Name != '']) > 0">
	    					<xpdExt:UnmappedScripts>
	    						<xsl:copy-of select="ancestor::xpdl2:Activity/xpdExt:ScriptInformation[@Name != '']"/>
	    					</xpdExt:UnmappedScripts>
	    				</xsl:if>
						
					</xpdExt:DataMappings>
				</xpdExt:InputScriptDataMapper>

    		</xsl:if> <!-- Not got datamapper mappings already -->
			
		</xsl:copy> <!-- xpdExt:SignalData -->

	</xsl:template>
	
	<!--
	===============================================================================
	Convert JavaScript data mappings to DataMapper mappings...
	CATCH GLOBAL SIGNAL and 
	CATCH LOCAL SIGNAL
	===============================================================================
    -->
    <xsl:template match="xpdl2:TriggerResultSignal[@CatchThrow = 'CATCH']/xpdExt:SignalData">
	
		<xsl:copy>  <!-- xpdExt:SignalData -->
    		<!-- apply templates to get existing content copied / removed as required before converting mappings if necessary. -->
    		<xsl:apply-templates select="@* | node() | text()"/>
    		
    		<!-- Only do anything if not DataMapper grammar already (just in case there is some early ACE dev xpdl's with these in but not on latest FormatVersion 1000) -->
    		<xsl:if test="not(xpdExt:OutputScriptDataMapper)">
			
				<!-- Only difference between local and global is the Mapper Context and source/target contributor id's -->
				<xsl:variable name="mapperContext">
					<xsl:choose>
						<xsl:when test="../@xpdExt:SignalType = 'Global'">GlobalSignalCatch</xsl:when>
						<xsl:otherwise>LocalSignalCatch</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
	
				<xsl:variable name="srcContribId">
					<xsl:choose>
						<xsl:when test="../@xpdExt:SignalType = 'Global'">MapFromGlobalSignal.DataMapperContent</xsl:when>
						<xsl:otherwise>MapFromLocalSignal.DataMapperContent</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
			
				<xsl:variable name="tgtNonCorrelationContribId">
					<xsl:choose>
						<xsl:when test="../@xpdExt:SignalType = 'Global'">MapFromGlobalSignalTarget.DataMapperContent</xsl:when>
						<xsl:otherwise>MapFromLocalSignalTarget.DataMapperContent</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
			
				<xpdExt:OutputScriptDataMapper MapperContext="{$mapperContext}" MappingDirection="IN">
					<xpdExt:DataMappings>
						<!--  Convert correlation(GLOBAL CATCH ONLY) and normal mappings the ONLY difference is in the xpdExt:TargetContributorId -->
						<xsl:for-each select="xpdExt:CorrelationMappings/xpdExt:DataMapping | xpdExt:DataMappings/xpdExt:DataMapping">
						
							<xsl:variable name="targetContributorId">
								<xsl:choose>
									<xsl:when test="ancestor::xpdExt:CorrelationMappings">MapFromGlobalSignalCorrelation.DataMapperContent</xsl:when>
									<xsl:otherwise><xsl:value-of select="$tgtNonCorrelationContribId"/></xsl:otherwise>
								</xsl:choose>
							</xsl:variable>
	
	  						<xsl:choose>
	  							<xsl:when test="xpdExt:ScriptInformation">
	   								<!-- Output Script mapping (@Formal comes from old xpdl2:Actual)-->
									<xpdExt:DataMapping xpdExt:TargetContributorId="{$targetContributorId}" 
														Direction="IN" 
														Formal="{xpdl2:Actual/text()}">
										<!-- And ScriptInformation is identical for output mappings -->
										<xsl:copy-of select="xpdExt:ScriptInformation"/>
										
										<!--  And xpdl2:Actual is hard coded... -->
										<xpdl2:Actual ScriptGrammar="JavaScript">__SCRIPT__</xpdl2:Actual>
									</xpdExt:DataMapping>
	
								</xsl:when>
								<xsl:otherwise>
	   								<!-- Standard non-scripted mapping (@Formal and xpdl2:Actual are swapped around) -->
									<xpdExt:DataMapping xpdExt:SourceContributorId="{$srcContribId}" xpdExt:TargetContributorId="{$targetContributorId}" 
												Direction="IN" 
												Formal="{xpdl2:Actual/text()}">
			                    		<xpdl2:Actual ScriptGrammar="JavaScript"><xsl:value-of select="@Formal"/></xpdl2:Actual>
									</xpdExt:DataMapping>
						
								</xsl:otherwise>
							</xsl:choose>	
							
						</xsl:for-each>
						
						<!--
	    					(As yet) Unmapped script mappings (move from xpdl2:activity to xpdExt:UnmappedScripts  
	    				 -->
	    				<xsl:if test="count(ancestor::xpdl2:Activity/xpdExt:ScriptInformation[@Name != '']) > 0">
	    					<xpdExt:UnmappedScripts>
	    						<xsl:copy-of select="ancestor::xpdl2:Activity/xpdExt:ScriptInformation[@Name != '']"/>
	    					</xpdExt:UnmappedScripts>
	    				</xsl:if>
						
						
					</xpdExt:DataMappings>
				</xpdExt:OutputScriptDataMapper>

    		</xsl:if> <!-- Not got datamapper mappings already -->
			
		</xsl:copy> <!-- xpdExt:SignalData -->

	</xsl:template>
	
	<!--
	===============================================================================
	Remove ReceiveTask web-service configuration 
	===============================================================================
    -->
    <xsl:template match="xpdl2:Activity[xpdl2:Implementation/xpdl2:Task/xpdl2:TaskReceive]/xpdExt:CorrelationTimeout">
	</xsl:template>
    

	<xsl:template match="xpdl2:TaskReceive/@xpdExt:ImplementationType">
		<xsl:attribute name="xpdExt:ImplementationType">Unspecified</xsl:attribute>
	</xsl:template>

	<xsl:template match="xpdl2:TaskReceive/@Implementation">
		<xsl:attribute name="Implementation">Unspecified</xsl:attribute>
	</xsl:template>
	

	<xsl:template match="xpdl2:TaskReceive/@xpdExt:Generated">
	</xsl:template>

	<xsl:template match="xpdl2:TaskReceive/@xpdExt:CorrelateImmediately">
	</xsl:template>
		
	<xsl:template match="xpdl2:TaskReceive/xpdl2:WebServiceOperation">
	</xsl:template>
 	
	<xsl:template match="xpdl2:TaskReceive/xpdExt:PortTypeOperation">
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
	Sid ACE-7608 Remove xpdExt:BxUseUnqualifiedPropertyNames and <xpdExt:BpmRuntimeConfiguration /> 
	===============================================================================
    -->
	<xsl:template match="@xpdExt:BxUseUnqualifiedPropertyNames">
		<!-- Do nothing (e.g. do not output attribute)-->
	</xsl:template>

	<xsl:template match="xpdExt:BpmRuntimeConfiguration">
		<!-- Do nothing (e.g. do not output the element)-->
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
