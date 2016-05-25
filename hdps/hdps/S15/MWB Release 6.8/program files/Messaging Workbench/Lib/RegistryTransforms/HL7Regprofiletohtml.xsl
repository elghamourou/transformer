<?xml version="1.0" encoding="UTF-8"?>
<!-- edited with XML Spy v4.0.1 U (http://www.xmlspy.com) by Peter Rontey (VHA, OI Field Office) -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" cdata-section-elements="ImpNote Reference Predicate Actor PreCondition
                                                            PostCondition DerivedEvent EventFlow UseCase"/>
		<xsl:strip-space elements="*"/>

  <xsl:include href="params.xsl"/>

	<xsl:template match="/">
		<html>
			<head>
				<title>
				<xsl:value-of select="$DocName"/>
				</title>
			</head>
			<xsl:if test="$Banner!=''">
			    <img border="0" >
			    <xsl:attribute name="src">
			    		<xsl:value-of select="$Banner"/>
			   </xsl:attribute>
		        <xsl:if test="$AltBanner!=''">
			      	<xsl:attribute name="alt">
			      		<xsl:value-of select="$AltBanner"/>
			      	</xsl:attribute>
			    </xsl:if>
			    </img>
			</xsl:if>
			<body>
				<basefont face="Lucida Sans Unicode" size="2">
					<h2 align="center">
						<span style="background-color: #FFCC99">
				            <xsl:value-of select="$DocName"/>
						</span>
					</h2>
					<table border="1" width="100%" cellspacing="0">
						<xsl:if test="HL7v2xConformanceProfile/ImpNote ">
							<xsl:apply-templates select="HL7v2xConformanceProfile/ImpNote"/>
						</xsl:if>
						<xsl:if test="Specification/ImpNote ">
							<xsl:apply-templates select="Specification/ImpNote"/>
						</xsl:if>
						<tr>
							<td width="20%" align="right" bordercolor="#FFFFFF"/>
							<td width="80%" align="center" bordercolor="#C0C0C0" bgcolor="#FFCC99">
								<font size="2">Message Profile</font>
							</td>
						</tr>
						<tr>
							<td width="20%" align="right" bordercolor="#FFFFFF">
								<font size="2" color="#FF9966">
									<b> HL7 Version
      </b>
								</font>
							</td>
							<td width="80%" bordercolor="#C0C0C0">
								<font size="2" color="#FFFFFF">&#160;</font>
								<font size="2">
									<xsl:value-of select="HL7v2xConformanceProfile/@HL7Version"/>
									<xsl:value-of select="Specification/@HL7Version"/>
								</font>
							</td>
						</tr>
						<tr>
							<td width="20%" align="right" bordercolor="#FFFFFF">
								<font size="2" color="#FF9966">
									<b>Profile Type</b>
								</font>
							</td>
							<td width="80%" bordercolor="#C0C0C0">
								<font size="2" color="#FFFFFF">&#160;</font>
								<font size="2">
									<xsl:value-of select="HL7v2xConformanceProfile/@ProfileType"/>
									<xsl:value-of select="Specification/@ConformanceType"/>
								</font>
							</td>
						</tr>
						<tr>
							<td width="20%" align="right" bordercolor="#FFFFFF">
								<font size="2" color="#FF9966">
									<b>Identifier</b>
								</font>
							</td>
							<td width="80%" bordercolor="#C0C0C0">
								<font size="2" color="#FFFFFF">&#160;</font>
								<font size="2">
									<xsl:value-of select="HL7v2xConformanceProfile/@Identifier"/>
									<xsl:value-of select="Specification/@HL7OID"/>
								</font>
							</td>
						</tr>						
						<xsl:apply-templates select="HL7v2xConformanceProfile/Encodings"/>
						<xsl:apply-templates select="Specification/Encodings"/>

						    <tr>
							<td width="20%" align="right" bordercolor="#FFFFFF"/>
							<td width="80%" align="center" bordercolor="#C0C0C0" bgcolor="#FFCC99">
								<font size="2">Message Profile Meta Data</font>
							</td>
						    </tr>
						    <xsl:choose>
								<xsl:when test="HL7v2xConformanceProfile/MetaData">
						    			<xsl:apply-templates select="HL7v2xConformanceProfile/MetaData"/>
								</xsl:when>
								<xsl:otherwise>
									<xsl:apply-templates select="MetaData"/>
								</xsl:otherwise>
						    </xsl:choose>

						<xsl:apply-templates select="HL7v2xConformanceProfile/DynamicDef"/>

						<xsl:apply-templates select="HL7v2xConformanceProfile/HL7v2xStaticDef"/>
					</table>
					<xsl:apply-templates select="HL7v2xConformanceProfile/UseCase"/>
					<h3 align="center">
						<span style="background-color: #FFCC99">Segments</span>
					</h3>
					<TABLE width="100%" border="0">
						<TBODY>
							<TR>
								<TD width="35%" valign="top">
									<font size="1">Optionality Codes:</font>
									<ul>
										<li>
											<font size="1">R - required</font>
										</li>
										<li>
											<font size="1">RE - required or empty</font>
										</li>
										<li>
											<font size="1">C - conditional</font>
										</li>
										<li>
											<font size="1">CE - conditional or empty</font>
										</li>
										<li>
											<font size="1">O - optional</font>
										</li>
										<li>
											<font size="1">NS or X - not supported</font>
										</li>
										<li>
											<font size="1">U - unknown</font>
										</li>
										<xsl:apply-templates select="HL7v2xConformanceProfile/DynamicDef/UsageExtensions"/>
									</ul>
								</TD>
								<TD width="30%" valign="top">
									<font size="1">Abbreviations:</font>
									<ul>
										<li>
											<font size="1">seq - sequence</font>
										</li>
										<li>
											<font size="1">DT - datatype</font>
										</li>
										<li>
											<font size="1">Len - length</font>
										</li>
										<li>
											<font size="1">Opt - optionality</font>
										</li>
										<li>
											<font size="1">Rep - repeatable</font>
										</li>
										<li>
											<font size="1">Min - quantity min</font>
										</li>
										<li>
											<font size="1">Max - quantity max</font>
										</li>
										<li>
											<font size="1">Tbl - table</font>
										</li>
									</ul>
								</TD>
								<TD width="35%" valign="top">
									<font size="1">Color codes:</font>
									<ul>
										<li>
											<FONT size="1">Fields</FONT>
										</li>
										<li>
											<font color="#0000ff" size="1">Components</font>
										</li>
										<li>
											<FONT color="#ff00ff" size="1">Sub Components</FONT>
										</li>
										<li>
											<font size="1">
												<span style="background-color: #CCFFCC">impl. note</span>
											</font>
										</li>
										<li>
											<font size="1">
												<span style="background-color: #C0C0C0">Not Supported</span>
											</font>
										</li>
										<li>
											<font size="1">
												<font color="#FF0000">CM </font>Datatype</font>
										</li>
									</ul>
								</TD>
							</TR>
						</TBODY>
					</TABLE>
					<xsl:if test="HL7v2xConformanceProfile/HL7v2xStaticDef/Segment">
						<xsl:apply-templates select="HL7v2xConformanceProfile/HL7v2xStaticDef/Segment"/>
					</xsl:if>
					<xsl:if test="Specification/Message/Segment">
						<xsl:apply-templates select="Specification/Message/Segment"/>
					</xsl:if>
					<xsl:if test="HL7v2xConformanceProfile/HL7v2xStaticDef/SegGroup">
						<xsl:apply-templates select="HL7v2xConformanceProfile/HL7v2xStaticDef/SegGroup"/>
					</xsl:if>
					<xsl:if test="Specification/Message/SegGroup">
						<xsl:apply-templates select="Specification/Message/SegGroup"/>
					</xsl:if>
					<xsl:if test="HL7v2xConformanceProfile/hl7tables">
						<xsl:apply-templates select="HL7v2xConformanceProfile/hl7tables"/>
					</xsl:if>
					<p/>
					<p/>
				</basefont>
			</body>
		</html>
	</xsl:template>
	<xsl:template match="DynamicDef">
						<tr>
							<td width="20%" align="right" bordercolor="#FFFFFF"/>
							<td width="80%" align="center" bordercolor="#C0C0C0" bgcolor="#FFCC99">
								<font size="2">Dynamic  Definition</font>
							</td>
						</tr>
		<tr>
			<td width="20%" align="right" bordercolor="#FFFFFF">
				<font size="2" color="#FF9966">
					<b>Accept
      Ack</b>
				</font>
			</td>
			<td width="80%" bordercolor="#C0C0C0">
				<font size="2" color="#FFFFFF">&#160;</font>
				<font size="2">
					<xsl:value-of select="@AccAck"/>
				</font>
			</td>
		</tr>
		<tr>
			<td width="20%" align="right" bordercolor="#FFFFFF">
				<b>
					<font size="2" color="#FF9966">
						<font size="2">Application
      </font>
						<font size="2">Ack</font>
					</font>
				</b>
			</td>
			<td width="80%" bordercolor="#C0C0C0">
				<font size="2" color="#FFFFFF">&#160;</font>
				<font size="2">
					<xsl:value-of select="@AppAck"/>
				</font>
			</td>
		</tr>
		<tr>
			<td width="20%" align="right" bordercolor="#FFFFFF">
				<b>
					<font size="2" color="#FF9966">Ack Mode</font>
				</b>
			</td>
			<td width="80%" bordercolor="#C0C0C0">
				<font size="2" color="#FFFFFF">&#160;</font>
				<font size="2">
					<xsl:value-of select="@MsgAckMode"/>
				</font>
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="HL7v2xStaticDef">
						<tr>
							<td width="20%" align="right" bordercolor="#FFFFFF"/>
							<td width="80%" align="center" bordercolor="#C0C0C0" bgcolor="#FFCC99">
								<font size="2">Static Definition</font>
							</td>
						</tr>
		<tr>
			<td width="20%" align="right" bordercolor="#FFFFFF">
				<font size="2" color="#FF9966">
					<b>Event
      Description</b>
				</font>
			</td>
			<td width="80%" bordercolor="#C0C0C0">
				<font size="2" color="#FFFFFF">&#160;</font>
				<font size="2">
					<xsl:value-of select="@EventDesc"/>
				</font>
			</td>
		</tr>						<tr>
							<td width="20%" align="right" bordercolor="#FFFFFF">
								<font size="2" color="#FF9966">
									<b>Message Type</b>
								</font>
							</td>
							<td width="80%" bordercolor="#C0C0C0">
								<font size="2" color="#FFFFFF">&#160;</font>
								<font size="2">
									<xsl:value-of select="@MsgType"/>
								</font>
							</td>
						</tr>
						<tr>
							<td width="20%" align="right" bordercolor="#FFFFFF">
								<font size="2" color="#FF9966">
									<b>Event Type</b>
								</font>
							</td>
							<td width="80%" bordercolor="#C0C0C0">
								<font size="2" color="#FFFFFF">&#160;</font>
								<font size="2">
									<xsl:value-of select="@EventType"/>
								</font>
							</td>
						</tr>
		<tr>
			<td width="20%" align="right" bordercolor="#FFFFFF">
				<font size="2" color="#FF9966">
					<b>Order Control Code</b>
				</font>
			</td>
			<td width="80%" bordercolor="#C0C0C0">
				<font size="2" color="#FFFFFF">&#160;</font>
				<font size="2">
					<xsl:value-of select="@OrderControl"/>
				</font>
			</td>
		</tr>
						<tr>
							<td width="20%" align="right" bordercolor="#FFFFFF">
								<font size="2" color="#FF9966">
									<b>Message Structure ID</b>
								</font>
							</td>
							<td width="80%" bordercolor="#C0C0C0">
								<font size="2" color="#FFFFFF">&#160;</font>
								<font size="2">
									<xsl:value-of select="@MsgStructID"/>
								</font>
							</td>
						</tr>
		<tr>
			<td width="20%" align="right" bordercolor="#FFFFFF">
				<font size="2" color="#FF9966">
					<b>Message Structure</b>
				</font>
			</td>
			<td width="80%" bordercolor="#C0C0C0">
				<font size="2" color="#FFFFFF">&#160;</font>
				<font size="2">
					<xsl:value-of select="HL7v2xConformanceProfile/HL7v2xStaticDef/@MsgStructID"/>
                         <xsl:apply-templates select="/HL7v2xConformanceProfile/HL7v2xStaticDef" mode="HL7Grammar"/>
				</font>
			</td>
		</tr>

		<tr>
			<td width="20%" align="right" bordercolor="#FFFFFF">
				<font size="2" color="#FF9966">
					<b>Identifier</b>
				</font>
			</td>
			<td width="80%" bordercolor="#C0C0C0">
				<font size="2" color="#FFFFFF">&#160;</font>
				<font size="2">
					<xsl:value-of select="@Identifier"/>
				</font>
			</td>
		</tr>
						<tr>
							<td width="20%" align="right" bordercolor="#FFFFFF">
								<font size="2" color="#FF9966">
									<b>Application
      Role</b>
								</font>
							</td>
							<td width="80%" bordercolor="#C0C0C0">
								<font size="2" color="#FFFFFF">&#160;</font>
								<font size="2">
									<xsl:value-of select="@Role"/>
								</font>
							</td>
						</tr>
		<xsl:if test="MetaData">
						<tr>
							<td width="20%" align="right" bordercolor="#FFFFFF"/>
							<td width="80%" align="center" bordercolor="#C0C0C0" bgcolor="#FFCC99">
								<font size="2">Static Definition Meta Data</font>
							</td>
						</tr>
						<xsl:apply-templates select="MetaData"/>
		</xsl:if>

		<tr>
			<td width="20%" align="right" bordercolor="#FFFFFF"/>
			<td width="80%" bordercolor="#C0C0C0" bgcolor="#FFCC99">
				<font size="2" color="#C0C0C0">&#160;</font>
			</td>
		</tr>
	</xsl:template>

	<xsl:template match="MetaData">
						<tr>
							<td width="20%" align="right" bordercolor="#FFFFFF">
								<font size="2"  color="#FF9966">
									<b>Name</b>
								</font>
							</td>
							<td width="80%" bordercolor="#C0C0C0">
								<font size="2" color="#FFFFFF">&#160;</font>
								<font size="2">
									<xsl:value-of select="@Name"/>
									<xsl:value-of select="Specification/@SpecName"/>
								</font>
							</td>
						</tr>
						<tr>
							<td width="20%" align="right" bordercolor="#FFFFFF">
								<font size="2" color="#FF9966">
									<b>Organization
      </b>
								</font>
							</td>
							<td width="80%" bordercolor="#C0C0C0">
								<font size="2" color="#FFFFFF">&#160;</font>
								<font size="2">
									<xsl:value-of select="@OrgName"/>
								</font>
							</td>
						</tr>
						<xsl:if test="@Version!=''">
						  <tr>
							<td width="20%" align="right" bordercolor="#FFFFFF">
								<font size="2" color="#FF9966">
									<b>Spec Version
      </b>
								</font>
							</td>
							<td width="80%" bordercolor="#C0C0C0">
								<font size="2" color="#FFFFFF">&#160;</font>
								<font size="2">
									<xsl:value-of select="@Version"/>
								</font>
							</td>
						  </tr>
						</xsl:if>
						<tr>
							<td width="20%" align="right" bordercolor="#FFFFFF">
								<font size="2" color="#FF9966">
									<b>Status
      </b>
								</font>
							</td>
							<td width="80%" bordercolor="#C0C0C0">
								<font size="2" color="#FFFFFF">&#160;</font>
								<font size="2">
									<xsl:value-of select="@Status"/>
								</font>
							</td>
						</tr>
						<tr>
							<td width="20%" align="right" bordercolor="#FFFFFF">
								<font size="2" color="#FF9966">
									<b>Topics
      </b>
								</font>
							</td>
							<td width="80%" bordercolor="#C0C0C0">
								<font size="2" color="#FFFFFF">&#160;</font>
								<font size="2">
									<xsl:value-of select="@Topics"/>
								</font>
							</td>
						</tr>
	</xsl:template>

	<xsl:template match="SegGroup">
		<p align="left"/>
		<table border="0" width="102%">
			<xsl:if test="@Usage != 'NS' and @Usage != 'X'">
				<tr>
					<td width="14%">
						<font color="#FF9966" size="2">
							<b>Seg Group</b>
						</font>
					</td>
					<td width="65%">
						<font color="#FF9966" size="2">
							<b>Description</b>
						</font>
					</td>
					<td width="5%">
						<font color="#FF9966" size="2">
							<b>Opt</b>
						</font>
					</td>
					<td width="4%">
						<font color="#FF9966" size="2">
							<b>Card.</b>
						</font>
					</td>
		               <xsl:if test="$DisplayReference='true'">
					  <td width="5%">
						<font color="#FF9966" size="2">
							<b>Reference</b>
						</font>
					  </td>
					</xsl:if>
				</tr>
				<tr>
					<td width="14%">
						<font size="2">
							<b>
								<xsl:value-of select="@Name"/>
							</b>
						</font>
					</td>
					<td width="65%">
						<font size="2">
							<b>
								<xsl:value-of select="@LongName"/>
							</b>
						</font>
					</td>
					<td width="5%">
						<b>
							<font size="2">
								<xsl:value-of select="@Usage"/>
							</font>
						</b>
					</td>
						<td width="4%">
							<b>
								<font size="2">
        <xsl:call-template name="find-cardinality">
          <xsl:with-param name="optionality">
            <xsl:value-of select="@Usage"/>
          </xsl:with-param>
          <xsl:with-param name="min">
            <xsl:value-of select="@Min"/>
          </xsl:with-param>
          <xsl:with-param name="max">
            <xsl:value-of select="@Max"/>
          </xsl:with-param>
        </xsl:call-template>
								</font>
							</b>
						</td>
		               <xsl:if test="$DisplayReference='true'">	
					  <td width="5%">
						<b>
							<font size="2">
								<xsl:value-of select="@Reference"/>
								<xsl:value-of select="Reference"/>
							</font>
						</b>
					  </td>
					</xsl:if>
				</tr>
				<xsl:if test="ImpNote">
					<xsl:apply-templates select="ImpNote"/>
				</xsl:if>
				<xsl:if test="HL7Definition">
					<xsl:apply-templates select="HL7Definition"/>
				</xsl:if>
			</xsl:if>
			<!-- Optionality !='NS' -->
			<xsl:if test="@Usage = 'NS' or @Usage = 'X'">
				<tr>
					<td width="14%">
						<font color="#FF9966" size="2">
							<b>Seg Group</b>
						</font>
					</td>
					<td width="65%">
						<font color="#FF9966" size="2">
							<b>Description</b>
						</font>
					</td>
					<td width="5%">
						<font color="#FF9966" size="2">
							<b>Opt</b>
						</font>
					</td>
					<td width="4%">
						<font color="#FF9966" size="2">
							<b>Card.</b>
						</font>
					</td>
		               <xsl:if test="$DisplayReference='true'">
					  <td width="5%">
						<font color="#FF9966" size="2">
							<b>Reference</b>
						</font>
					  </td>
					</xsl:if>
				</tr>
				<tr bgColor="C0C0C0">
					<td width="14%">
						<font size="2">
							<b>
								<xsl:value-of select="@Name"/>
							</b>
						</font>
					</td>
					<td width="65%">
						<font size="2">
							<b>
								<xsl:value-of select="Description"/>
								<xsl:value-of select="@Description"/>
							</b>
						</font>
					</td>
					<td width="5%">
						<b>
							<font size="2">
								<xsl:value-of select="@Usage"/>
							</font>
						</b>
					</td>
		               <xsl:if test="$DisplayReference='true'">
						<td width="4%">
							<b>
					<font size="2" color="#FFFFFF">&#160;</font>
							</b>
						</td>
				 	</xsl:if>
		               <xsl:if test="$DisplayReference='true'">
					<td width="5%">
						<b>
							<font size="2">
					<font size="2" color="#FFFFFF">&#160;</font>
							</font>
						</b>
					</td>
				 	</xsl:if>
				</tr>
				<xsl:if test="ImpNote">
					<xsl:apply-templates select="ImpNote"/>
				</xsl:if>
				<xsl:if test="HL7Definition">
					<xsl:apply-templates select="HL7Definition"/>
				</xsl:if>
			</xsl:if>
			<!-- Optionality 'NS' -->
		</table>
		<xsl:if test="@Usage != 'NS' and @Usage != 'X'">
			<xsl:if test="Segment">
				<xsl:apply-templates select="Segment"/>
			</xsl:if>
			<xsl:if test="SegGroup">
				<xsl:apply-templates select="SegGroup"/>
			</xsl:if>
		</xsl:if>
	</xsl:template>
	<xsl:template match="Segment">
		<p align="left"/>
		<table border="0" width="102%">
			<tr>
				<td width="14%">
					<font color="#FF9966" size="2">
						<b>Segment</b>
					</font>
				</td>
				<td width="65%">
					<font color="#FF9966" size="2">
						<b>Description</b>
					</font>
				</td>
				<td width="5%">
					<font color="#FF9966" size="2">
						<b>Opt</b>
					</font>
				</td>
				<td width="4%">
					<font color="#FF9966" size="2">
						<b>Card.</b>
					</font>
				</td>
		          <xsl:if test="$DisplayReference='true'">
				  <td width="5%">
					<font color="#FF9966" size="2">
						<b>Reference</b>
					</font>
				  </td>
				</xsl:if>
			</tr>
			<xsl:if test="@Usage != 'NS' and @Usage!='X'">
				<tr>
					<td width="14%">
						<font size="2">
							<b>
								<xsl:value-of select="@Name"/>
							</b>
						</font>
					</td>
					<td width="65%">
						<font size="2">
							<b>
								<xsl:value-of select="@LongName"/>
							</b>
						</font>
					</td>
					<td width="5%">
						<b>
							<font size="2">
								<xsl:value-of select="@Usage"/>
							</font>
						</b>
					</td>
						<td width="4%">
							<b>
								<font size="2">
        <xsl:call-template name="find-cardinality">
          <xsl:with-param name="optionality">
            <xsl:value-of select="@Usage"/>
          </xsl:with-param>
          <xsl:with-param name="min">
            <xsl:value-of select="@Min"/>
          </xsl:with-param>
          <xsl:with-param name="max">
            <xsl:value-of select="@Max"/>
          </xsl:with-param>
        </xsl:call-template>
								</font>
							</b>
						</td>
		               <xsl:if test="$DisplayReference='true'">
					  <td width="5%">
						<b>
							<font size="2">
								<xsl:value-of select="@Reference"/>
								<xsl:value-of select="Reference"/>
							</font>
						</b>
					  </td>
					</xsl:if>
				</tr>
				<xsl:if test="ImpNote">
					<xsl:apply-templates select="ImpNote"/>
				</xsl:if>
				<xsl:if test="HL7Definition">
					<xsl:apply-templates select="HL7Definition"/>
				</xsl:if>
			</xsl:if>
			<xsl:if test="@Usage = 'NS' or  @Usage='X'">
				<tr bgColor="C0C0C0">
					<td width="14%">
						<font size="2">
							<b>
								<xsl:value-of select="@Name"/>
							</b>
						</font>
					</td>
					<td width="65%">
						<font size="2">
							<b>
								<xsl:value-of select="Description"/>
								<xsl:value-of select="@Description"/>
							</b>
						</font>
					</td>
					  <td width="5%">
						<b>
							<font size="2">
								<xsl:value-of select="@Usage"/>
							</font>
						</b>
					  </td>
						<td width="4%">
							<b>
					          <font size="2" color="#FFFFFF">&#160;</font>
							</b>
						</td>
				    <xsl:if test="$DisplayReference='true'">
					  <td width="5%">
						<b>
							<font size="2">
					          <font size="2" color="#FFFFFF">&#160;</font>
							</font>
						</b>
					  </td>
				    </xsl:if>
				</tr>
				<xsl:if test="ImpNote">
					<xsl:apply-templates select="ImpNote"/>
				</xsl:if>
				<xsl:if test="HL7Definition">
					<xsl:apply-templates select="HL7Definition"/>
				</xsl:if>
			</xsl:if>
		</table>
		<xsl:if test="@Usage != 'NS' and @Usage != 'X'">
			<P align="center">
				<b>
					<font size="2">
						<span style="background-color: #FFCC99">Fields</span>
					</font>
				</b>
			</P>
			<table border="1" width="100%" cellspacing="1">
				<tr bordercolor="#FFFFFF">
					<th width="25%">
						<font size="2" color="#FF9966">
							<b>Name</b>
						</font>
					</th>
					<th width="5%">
						<font size="2" color="#FF9966">
							<b>Seq</b>
						</font>
					</th>
					<th width="5%">
						<font size="2" color="#FF9966">
							<b>DT</b>
						</font>
					</th>
					<th width="5%">
						<font size="2" color="#FF9966">
							<b>Len</b>
						</font>
					</th>
					<th width="5%">
						<font size="2" color="#FF9966">
							<b>Opt</b>
						</font>
					</th>
					<th width="5%">
						<font size="2" color="#FF9966">
							<b>Card.</b>
						</font>
					</th>
					<th width="5%">
						<font size="2" color="#FF9966">
							<b>Tbl</b>
						</font>
					</th>
		               <xsl:if test="$DisplayPredicate='true'">
					  <th width="10%">
						<font size="2" color="#FF9966">
							<b>Predicate</b>
						</font>
					  </th>
					</xsl:if>
		               <xsl:if test="$DisplayValues='true'">
					  <th width="10%">
						<font size="2" color="#FF9966">
							<b>Fixed Val</b>
						</font>
					  </th>
					  <th width="10%">
						<font size="2" color="#FF9966">
							<b>Ex Val</b>
						</font>
					  </th>
					</xsl:if>
		               <xsl:if test="$DisplayReference='true'">
					  <th width="5%">
						<font size="2" color="#FF9966">
							<b>Reference</b>
						</font>
					  </th>
					</xsl:if>
				</tr>
				<xsl:apply-templates select="Field"/>
			</table>
			<xsl:if test="SegGroup">
				<xsl:apply-templates select="SegGroup"/>
			</xsl:if>
		</xsl:if>
		<!-- optionality !='NS' -->
	</xsl:template>
	<xsl:template match="Field">
		<xsl:if test="@Usage[.!='NS'] and @Usage[.!='X']">
			<tr>
				<td width="25%">
					<font size="2" color="#FFFFFF">&#160;</font>
					<font size="2">
						<xsl:value-of select="@Name"/>
					</font>
				</td>
				<td width="5%" align="left" nowrap="NOWRAP" valign="center">
					<font size="2" color="#FFFFFF">&#160;</font>
					<font size="2">
					    <xsl:number level="multiple" 
                            count="*[self::Field or self::Component or self::SubComponent]"/>
					</font>
				</td>
				<td width="5%" nowrap="NOWRAP" valign="center">
					<font size="2" color="#FFFFFF">&#160;</font>
					<xsl:if test="(substring(@Datatype,1,2))='CM'">
						<font size="2" color="FF0000">
							<xsl:value-of select="@Datatype"/>
						</font>
					</xsl:if>
					<xsl:if test="(substring(@Datatype,1,2))!='CM'">
						<font size="2">
							<xsl:value-of select="@Datatype"/>
						</font>
					</xsl:if>
				</td>
				<td width="5%" nowrap="NOWRAP" valign="center">
					<font size="2" color="#FFFFFF">&#160;</font>
					<font size="2">
						<xsl:value-of select="@Length"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#160;</font>
					<font size="2">
						<xsl:value-of select="@Usage"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#160;</font>
					<font size="2">
        <xsl:call-template name="find-cardinality">
          <xsl:with-param name="optionality">
            <xsl:value-of select="@Usage"/>
          </xsl:with-param>
          <xsl:with-param name="min">
            <xsl:value-of select="@Min"/>
          </xsl:with-param>
          <xsl:with-param name="max">
            <xsl:value-of select="@Max"/>
          </xsl:with-param>
        </xsl:call-template>
					</font>
				</td>
				<td width="5%" nowrap="NOWRAP" valign="center">
					<font size="2" color="#FFFFFF">&#160;</font>
					<font size="2">
            <xsl:if test="@Table">
              <xsl:if test="(substring(@Table,1,1))=0">
                <xsl:text>HL7</xsl:text>
              </xsl:if>
            </xsl:if>
            <xsl:value-of select="@Table"/>
					</font>
				</td>
		          <xsl:if test="$DisplayPredicate='true'">
				  <td width="10%" valign="top">
					<font size="2" color="#FFFFFF">&#160;</font>
					<font size="2">
					    <xsl:if test="@Predicate!=''">
						  <xsl:value-of select="@Predicate"/>
						</xsl:if>
					   <xsl:if test="Predicate!=''">
						 <xsl:value-of select="Predicate"/>
						</xsl:if>
					</font>
				  </td>
				</xsl:if>
		          <xsl:if test="$DisplayValues='true'">
				  <xsl:choose>
					<xsl:when test="DataValues">
						<xsl:apply-templates select="DataValues"/>
					</xsl:when>
					<xsl:otherwise>
						<td width="10%">
							<font size="1" color="FFFFFF">&#160;</font>
							<font size="2" color="FFFFFF">&#160;</font>
						</td>
						<td width="10%">
							<font size="1" color="FFFFFF">&#160;</font>
							<font size="2" color="FFFFFF">&#160;</font>
						</td>
					</xsl:otherwise>
				  </xsl:choose>
				</xsl:if>
		          <xsl:if test="$DisplayReference='true'">
				  <td width="5%">
					<font size="2" color="#FFFFFF">&#160;</font>
					<font size="2">
						<xsl:value-of select="@Reference"/>
						<xsl:value-of select="Reference"/>
					</font>
				  </td>
				</xsl:if>
			</tr>
			<xsl:if test="ImpNote">
				<xsl:apply-templates select="ImpNote"/>
			</xsl:if>
			<xsl:if test="HL7Definition">
				<xsl:apply-templates select="HL7Definition"/>
			</xsl:if>
			<xsl:apply-templates select="Component"/>
		</xsl:if>

		<xsl:if test="@Usage[.='NS'] or @Usage[.='X']">
		  <xsl:if test="$IncludeNotSupported='true'">
			<tr bgColor="C0C0C0">
				<td width="25%">
					<font size="1" color="C0C0C0">&#160;</font>
					<font size="2">
						<xsl:value-of select="@Name"/>
					</font>
				</td>
				<td width="5%" align="left" nowrap="NOWRAP" valign="center">
					<font size="2" color="C0C0C0">&#160;</font>
					<font size="2">
					    <xsl:number level="multiple" 
                            count="*[self::Field or self::Component or self::SubComponent]"/>
					</font>
				</td>
				<td width="5%" nowrap="NOWRAP" valign="center">
					<font size="1" color="C0C0C0">&#160;</font>
					<font size="2">
						<xsl:value-of select="@Datatype"/>
					</font>
				</td>
				<td width="5%" nowrap="NOWRAP" valign="center">
					<font size="2" color="C0C0C0">&#160;</font>
					<font size="2">
						<xsl:value-of select="@Length"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="C0C0C0">&#160;</font>
					<font size="2">
						<xsl:value-of select="@Usage"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="C0C0C0">&#160;</font>
				</td>
				<td width="5%">
					<font size="2" color="C0C0C0">&#160;</font>
				</td>
		          <xsl:if test="$DisplayPredicate='true'">
				  <td width="10%">
					<font size="2" color="C0C0C0">&#160;</font>
				  </td>
				</xsl:if>
		          <xsl:if test="$DisplayValues='true'">
				  <td width="10%">
					<font size="2" color="C0C0C0">&#160;</font>
				  </td>
				  <td width="10%">
					<font size="2" color="C0C0C0">&#160;</font>
				  </td>
				</xsl:if>
				<xsl:if test="$DisplayReference='true'">
				  <td width="5%">
					<font size="2" color="#FFFFFF">&#160;</font>
				  </td>
				</xsl:if>
			</tr>
		  </xsl:if>
		</xsl:if>
	</xsl:template>
	<xsl:template match="Component">
	  <xsl:if test="$FieldsOnly!='true'">
		<xsl:if test="@Usage[.!='NS'] and @Usage[.!='X']">
			<tr>
				<td width="25%">
					<font size="2" color="#FFFFFF">&#160;&#160;&#160;&#160;</font>
					<font size="2" color="#0000FF">
						<xsl:value-of select="@Name"/>
					</font>
				</td>
				<td width="5%" align="left" nowrap="NOWRAP" valign="center">
					<font size="2" color="#FFFFFF">&#160;</font>
					<font size="2" color="#0000FF">
					    <xsl:number level="multiple" 
                            count="*[self::Field or self::Component or self::SubComponent]"/>
					</font>
				</td>
				<td width="5%" nowrap="NOWRAP" valign="center">
					<font size="2" color="#FFFFFF">&#160;</font>
					<xsl:if test="@Datatype[.='CM']">
						<font size="2" color="FF0000">
							<xsl:value-of select="@Datatype"/>
						</font>
					</xsl:if>
					<xsl:if test="@Datatype[.!='CM']">
						<font size="2">
							<xsl:value-of select="@Datatype"/>
						</font>
					</xsl:if>
				</td>
				<td width="5%" nowrap="NOWRAP" valign="center">
					<font size="2" color="#FFFFFF">&#160;</font>
					<font size="2">
						<xsl:value-of select="@Length"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#160;</font>
					<font size="2">
						<xsl:value-of select="@Usage"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#160;</font>
					<font size="2">
            <xsl:call-template name="find-cardinality">
              <xsl:with-param name="optionality">
                <xsl:value-of select="@Usage"/>
              </xsl:with-param>
              <xsl:with-param name="min">
                <xsl:value-of select="@Min"/>
              </xsl:with-param>
              <xsl:with-param name="max">
                <xsl:value-of select="@Max"/>
              </xsl:with-param>
            </xsl:call-template>
					</font>
				</td>
				<td width="5%" nowrap="NOWRAP" valign="center">
					<font size="2" color="#FFFFFF">&#160;</font>
					<font size="2">
            <xsl:if test="@Table">
              <xsl:if test="(substring(@Table,1,1))=0">
                <xsl:text>HL7</xsl:text>
              </xsl:if>
            </xsl:if>
            <xsl:value-of select="@Table"/>
					</font>
				</td>
		          <xsl:if test="$DisplayPredicate='true'">
				  <td width="10%"  valign="top">
					<font size="2" color="#FFFFFF">&#160;</font>
					<font size="2">
					    <xsl:if test="@Predicate!=''">
						  <xsl:value-of select="@Predicate"/>
						</xsl:if>
					   <xsl:if test="Predicate!=''">
						 <xsl:value-of select="Predicate"/>
						</xsl:if>
					</font>
				  </td>
				</xsl:if>
		          <xsl:if test="$DisplayValues='true'">
				  <xsl:choose>
					<xsl:when test="DataValues">
						<xsl:apply-templates select="DataValues"/>
					</xsl:when>
					<xsl:otherwise>
						<td width="10%">
							<font size="2" color="FFFFFF">&#160;</font>
							<font size="2" color="FFFFFF">&#160;</font>
						</td>
						<td width="10%">
							<font size="2" color="FFFFFF">&#160;</font>
							<font size="2" color="FFFFFF">&#160;</font>
						</td>
					</xsl:otherwise>
				  </xsl:choose>
				</xsl:if>
		          <xsl:if test="$DisplayReference='true'">
				  <td width="5%">
					<font size="2" color="#FFFFFF">&#160;</font>
					<font size="2">
						<xsl:value-of select="@Reference"/>
						<xsl:value-of select="Reference"/>
					</font>
				  </td>
				</xsl:if>
			</tr>
			<xsl:if test="ImpNote">
				<xsl:apply-templates select="ImpNote"/>
			</xsl:if>
			<xsl:if test="HL7Definition">
				<xsl:apply-templates select="HL7Definition"/>
			</xsl:if>
			<xsl:apply-templates select="SubComponent"/>
		</xsl:if>
		<xsl:if test="@Usage[.='NS'] or @Usage[.='X']">
		  <xsl:if test="$IncludeNotSupported='true'">
			<tr bgColor="C0C0C0">
				<td width="25%">
					<font size="2" color="C0C0C0">&#160;&#160;&#160;&#160;</font>
					<font size="2" color="#0000FF">
						<xsl:value-of select="@Name"/>
					</font>
				</td>
				<td width="5%" align="left" nowrap="NOWRAP" valign="center">
					<font size="2" color="C0C0C0">&#160;</font>
					<font size="2" color="#0000FF">
					    <xsl:number level="multiple" 
                            count="*[self::Field or self::Component or self::SubComponent]"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="C0C0C0">&#160;</font>
					<font size="2">
						<xsl:value-of select="@Datatype"/>
					</font>
				</td>
				<td width="5%" nowrap="NOWRAP" valign="center">
					<font size="2" color="C0C0C0">&#160;</font>
					<font size="2">
						<xsl:value-of select="@Length"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="C0C0C0">&#160;</font>
					<font size="2">
						<xsl:value-of select="@Usage"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="C0C0C0">&#160;</font>
				</td>
				<td width="5%">
					<font size="2" color="C0C0C0">&#160;</font>
				</td>
				<xsl:if test="$DisplayPredicate='true'">
				  <td width="10%">
					<font size="2" color="C0C0C0">&#160;</font>
				  </td>
				</xsl:if>
				<xsl:if test="$DisplayValues='true'">
				  <td width="10%">
					<font size="2" color="C0C0C0">&#160;</font>
				  </td>
				  <td width="10%">
					<font size="2" color="C0C0C0">&#160;</font>
				  </td>
				</xsl:if>
				<xsl:if test="$DisplayReference='true'">
				  <td width="5%">
					<font size="2" color="#FFFFFF">&#160;</font>
				  </td>
				</xsl:if>
			</tr>
		  </xsl:if>
		</xsl:if>
	  </xsl:if>
	</xsl:template>
	<xsl:template match="SubComponent">
		<xsl:if test="@Usage[.!='NS'] and @Usage[.!='X']">
			<tr>
				<td width="25%">
					<font size="2" color="#FFFFFF">&#160;&#160;&#160;&#160;&#160;&#160;&#160;</font>
					<font size="2" color="#FF00FF">
						<xsl:value-of select="@Name"/>
					</font>
				</td>
				<td width="5%" align="left" nowrap="NOWRAP" valign="center">
					<font size="2" color="#FFFFFF">&#160;</font>
					<font size="2" color="#FF00FF">
						<xsl:number level="multiple" 
                            count="*[self::Field or self::Component or self::SubComponent]"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#160;</font>
					<font size="2">
						<xsl:value-of select="@Datatype"/>
					</font>
				</td>
				<td width="5%" nowrap="NOWRAP" valign="center">
					<font size="2" color="#FFFFFF">&#160;</font>
					<font size="2">
						<xsl:value-of select="@Length"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#160;</font>
					<font size="2">
						<xsl:value-of select="@Usage"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#160;</font>
				</td>
				<td width="5%" nowrap="NOWRAP" valign="center">
					<font size="2" color="#FFFFFF">&#160;</font>
					<font size="2">
            <xsl:if test="@Table">
              <xsl:if test="(substring(@Table,1,1))=0">
                <xsl:text>HL7</xsl:text>
              </xsl:if>
            </xsl:if>
            <xsl:value-of select="@Table"/>
					</font>
				</td>
		          <xsl:if test="$DisplayPredicate='true'">
				  <td width="10%" valign="top">
					<font size="2" color="#FFFFFF">&#160;</font>
					<font size="2">
					    <xsl:if test="@Predicate!=''">
						  <xsl:value-of select="@Predicate"/>
						</xsl:if>
					   <xsl:if test="Predicate!=''">
						 <xsl:value-of select="Predicate"/>
						</xsl:if>
					</font>
				  </td>
				</xsl:if>
		          <xsl:if test="$DisplayValues='true'">
				  <xsl:choose>
					<xsl:when test="@Primitive[.='Y']">
						<xsl:apply-templates select="DataValues"/>
					</xsl:when>
					<xsl:otherwise>
						<td width="10%">
							<font size="2" color="FFFFFF">&#160;</font>
							<font size="2" color="FFFFFF">&#160;</font>
						</td>
						<td width="10%">
							<font size="2" color="FFFFFF">&#160;</font>
							<font size="2" color="FFFFFF">&#160;</font>
						</td>
					</xsl:otherwise>
				  </xsl:choose>
				</xsl:if>
		          <xsl:if test="$DisplayReference='true'">
				  <td width="5%">
					<font size="2" color="#FFFFFF">&#160;</font>
					<font size="2">
						<xsl:value-of select="@Reference"/>
						<xsl:value-of select="Reference"/>
					</font>
				  </td>
				</xsl:if>
			</tr>
			<xsl:if test="ImpNote">
				<xsl:apply-templates select="ImpNote"/>
			</xsl:if>
			<xsl:if test="HL7Definition">
				<xsl:apply-templates select="HL7Definition"/>
			</xsl:if>
		</xsl:if>
		<xsl:if test="@Usage[.='NS'] or @Usage[.='X']">
		  <xsl:if test="$IncludeNotSupported='true'">
			<tr bgColor="C0C0C0">
				<td width="25%">
					<font size="2" color="C0C0C0">&#160;&#160;&#160;&#160;&#160;&#160;&#160;</font>
					<font size="2" color="#FF00FF">
						<xsl:value-of select="@Name"/>
					</font>
				</td>
				<td width="5%" align="left" nowrap="NOWRAP" valign="center">
					<font size="2" color="C0C0C0">&#160;</font>
					<font size="2" color="#FF00FF">
						<xsl:number level="multiple" 
                            count="*[self::Field or self::Component or self::SubComponent]"/>
					</font>
				</td>
				<td width="5%" nowrap="NOWRAP" valign="center">
					<font size="2" color="C0C0C0">&#160;</font>
					<font size="2">
						<xsl:value-of select="@Datatype"/>
					</font>
				</td>
				<td width="5%" nowrap="NOWRAP" valign="center">
					<font size="2" color="C0C0C0">&#160;</font>
					<font size="2">
						<xsl:value-of select="@Length"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="C0C0C0">&#160;</font>
					<font size="2">
						<xsl:value-of select="@Usage"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="C0C0C0">&#160;</font>
				</td>
				<td width="5%">
					<font size="2" color="C0C0C0">&#160;</font>
				</td>
		          <xsl:if test="$DisplayPredicate='true'">
				  <td width="10%">
					<font size="2" color="C0C0C0">&#160;</font>
				  </td>
				</xsl:if>
		          <xsl:if test="$DisplayValues='true'">
				  <td width="10%">
					<font size="2" color="C0C0C0">&#160;</font>
				  </td>
				  <td width="10%">
					<font size="2" color="C0C0C0">&#160;</font>
				  </td>
				</xsl:if>
				<xsl:if test="$DisplayReference='true'">
				  <td width="5%">
					<font size="2" color="#FFFFFF">&#160;</font>
				  </td>
				</xsl:if>
			</tr>
		  </xsl:if>
		</xsl:if>
	</xsl:template>
	<xsl:template match="DataValues">
          <xsl:if test="$DisplayValues='true'">
		  <td width="10%">
			<font size="2" color="#FFFFFF">&#160;</font>
			<font size="2">
				<xsl:if test="@Fixed[.='True']">
					<xsl:value-of select="@Fixed"/>
				</xsl:if>
			</font>
		</td>
		<td width="10%">
			<font size="2" color="#FFFFFF">&#160;</font>
			<font size="2">
				<xsl:value-of select="@ExValue"/>
			</font>
		  </td>
		</xsl:if>
	</xsl:template>
	<xsl:template match="Actor">
		<tr>
			<td width="28%">
				<font size="2" color="#FFFFFF">&#160;</font>
				<font size="2">
					<xsl:value-of select="@Name"/>
				</font>
			</td>
			<td width="72%">
				<font size="2" color="#FFFFFF">&#160;</font>
				<font size="2">
					<xsl:value-of select="."/>
					<xsl:value-of select="@Value"/>
				</font>
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="PreCondition">
		<tr>
			<td width="4%">
				<font size="2" color="#FFFFFF">&#160;</font>
				<font size="2">
					<xsl:value-of select="@Name"/>
				</font>
			</td>
			<td width="96%">
				<font size="2" color="#FFFFFF">&#160;</font>
				<font size="2">
					<xsl:value-of select="."/>
					<xsl:value-of select="@Value"/>
				</font>
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="EventFlow">
		<tr>
			<td width="4%">
				<font size="2" color="#FFFFFF">&#160;</font>
				<font size="2">
					<xsl:value-of select="@Name"/>
				</font>
			</td>
			<td width="96%">
				<font size="2" color="#FFFFFF">&#160;</font>
				<font size="2">
					<xsl:value-of select="."/>
					<xsl:value-of select="@Value"/>
				</font>
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="PostCondition">
		<tr>
			<td width="4%">
				<font size="2" color="#FFFFFF">&#160;</font>
				<font size="2">
					<xsl:value-of select="@Name"/>
				</font>
			</td>
			<td width="96%">
				<font size="2" color="#FFFFFF">&#160;</font>
				<font size="2">
					<xsl:value-of select="."/>
					<xsl:value-of select="@Value"/>
				</font>
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="DerivedEvent">
		<tr>
			<td width="4%">
				<font size="2" color="#FFFFFF">&#160;</font>
				<font size="2">
					<xsl:value-of select="@Name"/>
				</font>
			</td>
			<td width="96%">
				<font size="2" color="#FFFFFF">&#160;</font>
				<font size="2">
					<xsl:value-of select="."/>
					<xsl:value-of select="@Value"/>
				</font>
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="ImpNote">
			<tr bgcolor="#00CC66" Height="5" style="border:none">
				<td width="100%" colspan="11" style="width:95.4%;border:none;background:#CCFFCC;
  padding:.75pt .75pt .75pt .75pt;height:3.75pt">
					<font size="1" color="#0000FF">&#160;&#160;<b>Implentation Note:</b> </font>
				</td>
			</tr>
		    <xsl:if test="child::text()!=''">
			<tr bgcolor="#00CC66" Height="5" style="border:none">
				<td width="100%" colspan="11" style="width:95.4%;border:none;background:#CCFFCC;
  padding:.75pt .75pt .75pt .75pt;height:3.75pt">
					<font color="#00CC66">&#160;&#160;</font>
					<font size="1" color="#0000FF">
		<xsl:value-of select="child::text()"/>
					</font>
				</td>
			</tr>
			</xsl:if>
		<xsl:for-each select="Paragraph">
			<tr bgcolor="#00CC66" Height="5" style="border:none">
				<td width="100%" colspan="11" style="width:95.4%;border:none;background:#CCFFCC;
  padding:.75pt .75pt .75pt .75pt;height:3.75pt">
					<font color="#00CC66">&#160;&#160;</font>
					<font size="1" color="#0000FF">
						<xsl:value-of select="."/>
					</font>
				</td>
			</tr>
		</xsl:for-each>
	</xsl:template>
	<xsl:template match="HL7Definition">
		<tr bgcolor="#33CCCC" Height="5" style="border:none">
			<td width="100%" colspan="11" style="border:none">
				<font size="1">HL7 Definition: </font>
			</td>
		</tr>
		<xsl:for-each select="Paragraph">
			<tr bgcolor="#33CCCCC" Height="5" style="border:none">
				<td width="100%" colspan="11" style="width:95.4%;border:none;background:#CCFFCC;
  padding:.75pt .75pt .75pt .75pt;height:3.75pt">
					<font color="#00FFFF">&#160;&#160;</font>
					<font size="1" color="#0000FF">
						<xsl:value-of select="."/>
					</font>
				</td>
			</tr>
		</xsl:for-each>
	</xsl:template>
	<xsl:template match="UseCase">
		<h3 align="center">
			<span style="background-color: #FFCC99">Use Case</span>
		</h3>
		<table border="1" width="100%" cellspacing="1">
		
		<!-- Jenni added Use Case name and narrative  -->
		   <tr>
			<td width="28%">
				<font size="2" color="#FF9966" bordercolor="#FFFFFF">&#160;</font>
				<font size="2">
					<b>Name</b>
				</font>
			</td>
			<td width="72%">
				<font size="2" color="#FFFFFF">&#160;</font>
				<font size="2">
					<xsl:value-of select="@Name"/>
				</font>
			</td>
		    </tr>
		   <tr>
			<td width="28%">
				<font size="2" color="#FF9966" bordercolor="#FFFFFF">&#160;</font>
				<font size="2">
					<b>Narrative</b>
				</font>
			</td>
			<td width="72%">
				<font size="2" color="#FFFFFF">&#160;</font>
				<font size="2">
					<xsl:value-of select="Description"/>
				</font>
			</td>
		    </tr>
		</table>
		<p/>
		<table border="1" width="100%" cellspacing="1">
			<tr bordercolor="#FFFFFF">
				<td width="28%">
					<font size="2" color="#FF9966">
						<b>Actors</b>
					</font>
				</td>
				<td width="72%">
					<font size="2" color="#FF9966">
						<b>Description</b>
					</font>
				</td>
			</tr>
			<xsl:apply-templates select="Actor"/>
		</table>
		<p/>
		<table border="1" width="100%" cellspacing="1">
			<tr bordercolor="#FFFFFF">
				<td width="5%">
					<font size="2" color="#FF9966">
						<b>#</b>
					</font>
				</td>
				<td width="95%">
					<font size="2" color="#FF9966">
						<b>Pre Conditions</b>
					</font>
				</td>
			</tr>
			<xsl:apply-templates select="PreCondition"/>
		</table>
		<p/>
		<table border="1" width="100%" cellspacing="1">
			<tr bordercolor="#FFFFFF">
				<td width="5%">
					<font size="2" color="#FF9966">
						<b>#</b>
					</font>
				</td>
				<td width="95%">
					<font size="2" color="#FF9966">
						<b>Event Flows</b>
					</font>
				</td>
			</tr>
			<xsl:apply-templates select="EventFlow"/>
		</table>
		<p/>
		<table border="1" width="100%" cellspacing="1">
			<tr bordercolor="#FFFFFF">
				<td width="5%">
					<font size="2" color="#FF9966">
						<b>#</b>
					</font>
				</td>
				<td width="95%">
					<font size="2" color="#FF9966">
						<b>Post Conditions</b>
					</font>
				</td>
			</tr>
			<xsl:apply-templates select="PostCondition"/>
		</table>
		<p/>
		<table border="1" width="100%" cellspacing="1">
			<tr bordercolor="#FFFFFF">
				<td width="5%">
					<font size="2" color="#FF9966">
						<b>#</b>
					</font>
				</td>
				<td width="95%">
					<font size="2" color="#FF9966">
						<b>Derived Events</b>
					</font>
				</td>
			</tr>
			<xsl:apply-templates select="DerivedEvent"/>
		</table>
		<p/>
	</xsl:template>
	<xsl:template match="Encodings">
		<tr>
			<td width="20%" align="right" bordercolor="#FFFFFF">
				<font size="2" color="#FF9966">
					<b> Encodings  </b>
				</font>
			</td>
			<td width="80%" bordercolor="#C0C0C0">
				<font size="2" color="#FFFFFF">&#160;</font>
			</td>
		</tr>
		<xsl:for-each select="Encoding">
			<tr>
				<td width="20%" align="right" bordercolor="#C0C0C0"/>
				<td width="80%" bordercolor="#C0C0C0">
					<font size="2" color="#FFFFFF">&#160;</font>
					<font size="2">
						<xsl:value-of select="."/>
					</font>
				</td>
			</tr>
		</xsl:for-each>
	</xsl:template>
	<xsl:template match="UsageExtensions">
		<xsl:if test="UsageExt">
			<br>
				<b>
					<font size="1"> Usage/optionality Extentions</font>
				</b>
			</br>
			<xsl:for-each select="UsageExt">
				<li>
					<font size="1">
				      XXX
					<xsl:value-of select="@Code"/>&#160;-&#160;
					<xsl:value-of select="@Description"/>
					</font>
				</li>
			</xsl:for-each>
		</xsl:if>
	</xsl:template>
	<xsl:template match="hl7tables">
		<hr/>
		<p/>
		<p/>
		<h3 align="center">
			<span style="background-color: #FFCC99">Tables</span>
		</h3>
		<p/>
		<xsl:apply-templates select="hl7table"/>
		<p/>
	</xsl:template>
	<xsl:template match="hl7table">
		<p/>
		<b>
			<font color="#FF9966" size="3"> ID:</font>
			<font size="3">
				<xsl:value-of select="@id"/>
			</font>
			<font color="#FF9966" size="3"> Name: </font>
			<font size="3">
				<xsl:value-of select="@name"/>
			</font>
		</b>
		<font color="#FF9966" size="2"> Type: </font>
		<font size="2">
			<xsl:value-of select="@type"/>
		</font>
		<font color="#FF9966" size="2"> Coding Sys: </font>
		<font size="2">
			<xsl:value-of select="@codesys"/>
		</font>
		<table border="1" width="100%" cellspacing="1">
			<tr bordercolor="#FFFFFF">
				<td width="2%">
					<font size="2" color="#FF9966">
						<b>Order</b>
					</font>
				</td>
				<td width="3%">
					<font size="2" color="#FF9966">
						<b>Code</b>
					</font>
				</td>
				<td width="50%">
					<font size="2" color="#FF9966">
						<b>Description</b>
					</font>
				</td>
				<td width="3%">
					<font size="2" color="#FF9966">
						<b>Source</b>
					</font>
				</td>
				<td width="2%">
					<font size="2" color="#FF9966">
						<b>Usage</b>
					</font>
				</td>
				<td width="15%">
					<font size="2" color="#FF9966">
						<b>Display Name</b>
					</font>
				</td>
				<td width="25%">
					<font size="2" color="#FF9966">
						<b>Instructions</b>
					</font>
				</td>
			</tr>
			<xsl:apply-templates select="tableElement"/>
		</table>
	</xsl:template>
	<xsl:template match="tableElement">
		<tr>
			<td width="2%">
				<font size="2">&#160;
					<xsl:value-of select="@order"/>
				</font>
			</td>
			<td width="3%">
				<font size="2">&#160;
					<xsl:value-of select="@code"/>
				</font>
			</td>
			<td width="50%">
				<font size="2">&#160;
					<xsl:value-of select="@description"/>
				</font>
			</td>
			<td width="3%">
				<font size="2">&#160;
					<xsl:value-of select="@source"/>
				</font>
			</td>
			<td width="2%">
				<font size="2">&#160;
					<xsl:value-of select="@usage"/>
				</font>
			</td>
			<td width="15%">
				<font size="2">&#160;
					<xsl:value-of select="@displayName"/>
				</font>
			</td>
			<td width="25%">
				<font size="2">&#160;
					<xsl:value-of select="@instruction"/>
				</font>
			</td>
		</tr>
	</xsl:template>
  <xsl:template name="find-cardinality">
    <xsl:param name="optionality"/>
    <xsl:param name="min"/>
    <xsl:param name="max"/>
    <xsl:choose>
        <xsl:when test="../@Usage='X'">
                 
        </xsl:when>
        <xsl:when test="$optionality='X'">
                 
        </xsl:when>
        <xsl:when test="$optionality='R' and $max=''">
        1..1
        </xsl:when>
        <xsl:when test="($optionality='RE' or $optionality='O' or $optionality='C') and $max='1'">
        0..1
        </xsl:when>
        <xsl:when test="($optionality='RE' or $optionality='O' or $optionality='C') and ($max=0)">
          0...*
        </xsl:when>
        <xsl:when test="($optionality='RE' or $optionality='O' or $optionality='C') and ($max!='0' and $max!='1')">
             <xsl:text>0..</xsl:text>
             <xsl:value-of select="$max"/>
        </xsl:when>
        <xsl:when test="($optionality='R') and ($max=0)">
          <xsl:text>1..*</xsl:text>
        </xsl:when>
        <xsl:when test="($optionality='R') and ($max!=0)">
          <xsl:text>1..</xsl:text>
          <xsl:value-of select="$max"/>
        </xsl:when>
        <xsl:otherwise>
             <xsl:value-of select="$min"/>
             <xsl:text>..</xsl:text>
             <xsl:value-of select="$max"/>
          </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template match="SegGroup" mode="HL7Grammar">
    <xsl:if test="@Usage!='R'">
      <xsl:text>[</xsl:text>
    </xsl:if>
    <xsl:if test="@Max!='1' or not(@Max)">
      <xsl:text>{</xsl:text>
    </xsl:if>
    <xsl:text> </xsl:text>

    <xsl:apply-templates select="descendant::*[self::SegGroup or self::Segment]" mode="HL7Grammar"/>

    <xsl:if test="@Max!='1' or not(@Max)">
      <xsl:text>}</xsl:text>
    </xsl:if>
    <xsl:if test="@Usage!='R'">
      <xsl:text>]</xsl:text>
    </xsl:if>
    <xsl:text> </xsl:text>
  </xsl:template>

  <xsl:template match="Segment" mode="HL7Grammar">
    <xsl:if test="@Usage!='R'">
      <xsl:text>[</xsl:text>
    </xsl:if>
    <xsl:if test="@Max!='1' or not(@Max)">
      <xsl:text>{</xsl:text>
    </xsl:if>
    <xsl:value-of select="@Name"/>
    <xsl:if test="@Max!='1' or not(@Max)">
      <xsl:text>}</xsl:text>
    </xsl:if>
    <xsl:if test="@Usage!='R'">
      <xsl:text>]</xsl:text>
    </xsl:if>
    <xsl:text> </xsl:text>
  </xsl:template>

</xsl:stylesheet>
