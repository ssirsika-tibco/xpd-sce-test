<?xml version="1.0" encoding="UTF-8"?>
<!--
	===================================================================================================================
	XSLT:       MigrateStudioXPDL_8.xslt
	
	DESCRIPTION:

  	    This xslt will updgrade a xpdl of format version 8 (v3.3.0) to format version 9 (3.5.0 build V16)
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
		<xsl:apply-templates />
	</xsl:template>


	<!--
	===============================================================================
	Swap xpdl2:WorkflowProcess/xpdExt:Pageflow="true" for xpdExt:XpdModelType="PageFlow"
	And xpdExt:IsTaskLibrary="true" for xpdExt:XpdModelType="TaskLibrary"
	===============================================================================
    -->
    <xsl:template match="xpdl2:WorkflowProcess/@xpdExt:Pageflow">
		<xsl:if test=". = true()">
			<xsl:attribute name="xpdExt:XpdModelType">PageFlow</xsl:attribute>
		</xsl:if>
    </xsl:template>
	
    <xsl:template match="xpdl2:WorkflowProcess/@xpdExt:IsTaskLibrary">
		<xsl:if test=". = true()">
			<xsl:attribute name="xpdExt:XpdModelType">TaskLibrary</xsl:attribute>
		</xsl:if>
    </xsl:template>


	<!--
	===============================================================================
	Update advanced properties on system participants.
	===============================================================================
    -->
    <xsl:template match="xpdl2:Participant[xpdl2:ParticipantType/@Type = 'SYSTEM']">
		<xsl:variable name="particId" select="@Id"/>

		<!-- 
			Decide whether it is an Inbound participant (process api participant) or outbound (service invocaiton participant) 
			If it's reference as a process api participant then it's inbbound.
		-->
		<xsl:variable name="apiProcess" select="/xpdl2:Package/xpdl2:WorkflowProcesses/xpdl2:WorkflowProcess[@xpdExt:ApiEndPointParticipant = $particId]"/>
	
		<!-- Deal with the original participant. -->
		<xsl:copy>
			<xsl:apply-templates select="@*"/>
			
			<xsl:apply-templates select="*[not(self::xpdExt:SharedResource)]"/>
			
			<xsl:if test="not(xpdExt:ParticipantSharedResource)">
				<xsl:choose>
					<!-- Database resource configuration. -->
					<xsl:when test="xpdExt:SharedResource/@Type = 'Database'">
					  <xpdExt:ParticipantSharedResource>
						<xpdExt:Jdbc InstanceName="{xpdExt:SharedResource/@Name}" JdbcProfileName="{xpdExt:SharedResource/@Name}"/>
					  </xpdExt:ParticipantSharedResource>
					</xsl:when>
	
					<!-- EMail resource configuration. -->
					<xsl:when test="xpdExt:SharedResource/@Type = 'Email'">
					  <xpdExt:ParticipantSharedResource>
						<xpdExt:Email InstanceName="{xpdExt:SharedResource/@Name}"/>
					  </xpdExt:ParticipantSharedResource>
					</xsl:when>
						
					<!-- WebService resource configuration. -->
					<xsl:when test="xpdExt:SharedResource/@Type = 'WebService'">
					  <xpdExt:ParticipantSharedResource>
						  
						  <xpdExt:WebService>
							  
								<xsl:choose>
									<xsl:when test="$apiProcess">
										<!-- 
											Inbound (process API) Participant 
										-->
									  <xpdExt:Inbound>
										  <xpdExt:VirtualBinding Name="Virtualization"/>
										  
										<xpdExt:SoapHttpBinding>
											<xsl:attribute name="Name">SoapOverHttp</xsl:attribute>
											<xsl:attribute name="SoapVersion">1.1</xsl:attribute>
											<xsl:attribute name="EndpointUrlPath"><xsl:value-of select="xpdExt:SharedResource/@URI"/></xsl:attribute>
											
											<xsl:attribute name="HttpConnectorInstanceName">
												<xsl:choose>
													<xsl:when test="xpdExt:SharedResource/@Name != ''">
														<xsl:value-of select="xpdExt:SharedResource/@Name"/>
													</xsl:when>
													<xsl:otherwise>
														<xsl:text>httpConnector</xsl:text>
													</xsl:otherwise>
												</xsl:choose>
											</xsl:attribute>
										
											<xsl:variable name="bindingStyle" select="$apiProcess/xpdExt:WsdlGeneration/@xpdExt:BindingType"/>
											<xsl:attribute name="BindingStyle">
												<xsl:choose>
													<xsl:when test="$bindingStyle = 'DocumentLiteral'">
														<xsl:text>DocumentLiteral</xsl:text>
													</xsl:when>
													<xsl:when test="$bindingStyle = 'RPCLiteral' or $bindingStyle = 'RPCEncoded'">
														<xsl:text>RpcLiteral</xsl:text>
													</xsl:when>
													<xsl:otherwise>
														<xsl:text>RpcLiteral</xsl:text>
													</xsl:otherwise>
												</xsl:choose>
											</xsl:attribute>
										</xpdExt:SoapHttpBinding>
	
									  </xpdExt:Inbound>
	
									</xsl:when>
									<xsl:otherwise>
										<!-- 
											Outbound (Service invocation) Participant 
										-->
										<xpdExt:Outbound>
											<!-- We need to know whether this is usewd for abstract WSDL operation invocation or concrete. 
													This is defiend by the invocation activity it is used in - so seafch for 1st webserivceoperation 
													that uses this as the alias-->
											<xsl:variable name="WSO" select="//xpdl2:WebServiceOperation[@xpdExt:Alias = $particId][1]"/>
											
											<!-- If we found a use of this participant in WS activity then it's transport - default to virtualisation if it isn't used. -->
											<xsl:choose>
												<xsl:when test="$WSO/@xpdExt:Transport = 'http://schemas.xmlsoap.org/soap/http'">
													<!-- SOAP Over HTTP -->
													<xpdExt:SoapHttpBinding Name="SoapOverHttp" HttpClientInstanceName="{xpdExt:SharedResource/@Name}">
	
														<xsl:variable name="securityType" select="xpdExt:SharedResource/xpdExt:SecurityPolicyProperties/@Type"/>
														<xsl:variable name="sharedResInst" select="xpdExt:SharedResource/xpdExt:SecurityPolicyProperties/xpdExt:SharedResourceInstance"/>
	
														<xpdExt:OutboundSecurity>
															<xsl:choose>
																<xsl:when test="$securityType = 'SAML'">
																	<xpdExt:SecurityPolicy GovernanceApplicationName="{$sharedResInst}" Type="SamlToken"/>
																</xsl:when>
																<xsl:when test="$securityType = 'X509'">
																	<xpdExt:SecurityPolicy GovernanceApplicationName="{$sharedResInst}" Type="X509Token"/>
																</xsl:when>
																<xsl:when test="$securityType = 'USERNAME_TOKEN'">
																	<xpdExt:SecurityPolicy GovernanceApplicationName="{$sharedResInst}" Type="UsernameToken"/>
																</xsl:when>
															</xsl:choose>
														
														</xpdExt:OutboundSecurity>
													</xpdExt:SoapHttpBinding>
													
												</xsl:when>
												<xsl:otherwise>
													<!-- Service Virtualisation -->
													<xpdExt:VirtualBinding Name="Virtualization"/>
												</xsl:otherwise>
											</xsl:choose>
											
										</xpdExt:Outbound>
									</xsl:otherwise>
									
								</xsl:choose>
									
						  </xpdExt:WebService>
					  
					  </xpdExt:ParticipantSharedResource>
					</xsl:when>
				</xsl:choose>

			</xsl:if>
			
		</xsl:copy>
    
		<!--
			If this participant is used for process API and InvokeBusinessprocess TaskSend then create a copy that is a consumer rather than provider.
		-->
		<xsl:if test="$apiProcess">
			<xsl:variable name="invokeBizProcTasksUsingPartic" select="//xpdl2:TaskSend[@xpdExt:ImplementationType = 'InvokeBusinessProcess' and xpdl2:WebServiceOperation/@xpdExt:Alias = $particId]"/>
			<xsl:if test="count($invokeBizProcTasksUsingPartic) != 0">
				<xsl:copy>
					<xsl:attribute name="Id"><xsl:value-of select="@Id"/><xsl:text>_consumer</xsl:text></xsl:attribute>
					<xsl:attribute name="Name"><xsl:value-of select="@Name"/><xsl:text>_consumer</xsl:text></xsl:attribute>
					<xsl:attribute name="xpdExt:DisplayName"><xsl:value-of select="@xpdExt:DisplayName"/><xsl:text>_consumer</xsl:text></xsl:attribute>

					<!-- Apply templates to everythign EXCEPT the Name,DisplayName and Id attributes. 
						Note that we say "../@Name because the bit in [ ] is run in the context of each found attribute NOT in the context of the participant element. -->
					<xsl:apply-templates select="@*[not(../@Name) and not(../@Id) and not(../@xpdExt:DisplayName)]"/>

					<xsl:apply-templates select="*[not(self::xpdExt:SharedResource)]"/>
					
					<xpdExt:ParticipantSharedResource>
						<xpdExt:WebService>
							<xpdExt:Outbound>
								<xpdExt:VirtualBinding Name="Virtualization"/>
							</xpdExt:Outbound>
						</xpdExt:WebService>
					</xpdExt:ParticipantSharedResource>
					
				</xsl:copy>
			</xsl:if>
		</xsl:if>
    
    </xsl:template>

	<!--
	===============================================================================
	Set performer on InvokeBusinessProcess SendTask activity in same package as it's invoked process to newly created
	 xxx_consumer version.
	===============================================================================
    -->
	<xsl:template match="xpdl2:Activity[xpdl2:Implementation/xpdl2:Task/xpdl2:TaskSend/@xpdExt:ImplementationType = 'InvokeBusinessProcess']/xpdl2:Performers/xpdl2:Performer">
	
		<!-- Any xpdl2:Performer for invoke biz process that ref's a participant used as a process api endpoint in this package should be swapped to the newly created xxx_consumer version. -->
		<xsl:variable name="particId" select="."/>
		
		<xsl:variable name="apiProcess" select="/xpdl2:Package/xpdl2:WorkflowProcesses/xpdl2:WorkflowProcess[@xpdExt:ApiEndPointParticipant = $particId]"/>
		<xsl:copy>
			<xsl:choose>
				<xsl:when test="$apiProcess">
					<xsl:value-of select="."/><xsl:text>_consumer</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="."/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:copy>
		
	</xsl:template>
	
	<!--
	===============================================================================
	Set the FillColor of Business Service activities to "205,158,233"
	===============================================================================
    -->
    <xsl:template match="xpdl2:Activity/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo/@FillColor">
    	<xsl:choose>
	    	<xsl:when test="ancestor::xpdl2:WorkflowProcess[@xpdExt:PublishAsBusinessService = 'true']">
	    		<!--  Reset the fill color to new default if current value = old default -->
	    		<xsl:choose>
	    			<xsl:when test=". = '143,191,251'">
	    				<xsl:attribute name="FillColor">205,158,233</xsl:attribute>
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
	Set xpdExt:Alias on InvokeBusinessProcess SendTask activity in same package as it's invoked process to newly created
	 xxx_consumer version.
	===============================================================================
    -->
	<xsl:template match="xpdl2:Activity/xpdl2:Implementation/xpdl2:Task/xpdl2:TaskSend[@xpdExt:ImplementationType = 'InvokeBusinessProcess']/xpdl2:WebServiceOperation/@xpdExt:Alias">
	
		<!-- Any xpdl2:Performer for invoke biz process that ref's a participant used as a process api endpoint in this package should be swapped to the newly created xxx_consumer version. -->
		<xsl:variable name="particId" select="."/>
		
		<xsl:variable name="apiProcess" select="/xpdl2:Package/xpdl2:WorkflowProcesses/xpdl2:WorkflowProcess[@xpdExt:ApiEndPointParticipant = $particId]"/>

		<xsl:attribute name="xpdExt:Alias">
			<xsl:choose>
				<xsl:when test="$apiProcess">
					<xsl:value-of select="."/><xsl:text>_consumer</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="."/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:attribute>
		
	</xsl:template>


	<!--
	===============================================================================
	Don't need xpdl2:WorkflowProcess/xpdExt:WsdlGeneration any more (bindingtype info is now stored in api participant.
	===============================================================================
    -->
	<xsl:template match="xpdl2:WorkflowProcess/xpdExt:WsdlGeneration">
	</xsl:template>

	<!--
	===============================================================================
	ProcessInterface/xpdExt:WsdlGeneration/xpdExt:BindingType -> xpdExt:SoapBindingStyle
	===============================================================================
    -->
	<xsl:template match="xpdExt:ProcessInterface">
		<xpdExt:ProcessInterface>
			<xsl:apply-templates select="@*"/>
			
			<xsl:apply-templates select="*[not(self::xpdExt:WsdlGeneration)]"/>
			
			<!-- Add xpdExt:WsdlGeneration if not present. -->
			<xpdExt:WsdlGeneration>
				<xsl:attribute name="xpdExt:SoapBindingStyle">
					<xsl:variable name="bindingType" select="xpdExt:WsdlGeneration/@xpdExt:BindingType"/>
					
					<xsl:choose>
						<xsl:when test="$bindingType = 'DocumentLiteral'">
							<xsl:text>DocumentLiteral</xsl:text>
						</xsl:when>
						<xsl:otherwise>
							<xsl:text>RpcLiteral</xsl:text>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:attribute>
				
			</xpdExt:WsdlGeneration>
		</xpdExt:ProcessInterface>
	</xsl:template>

	<!--
	===============================================================================
	Update the format version.
	===============================================================================
    -->
	<xsl:template match="xpdl2:ExtendedAttribute[@Name = 'FormatVersion']">
		<xsl:element name="xpdl2:ExtendedAttribute">
			<xsl:attribute name="Name">FormatVersion</xsl:attribute>
			<xsl:attribute name="Value">9</xsl:attribute>
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
