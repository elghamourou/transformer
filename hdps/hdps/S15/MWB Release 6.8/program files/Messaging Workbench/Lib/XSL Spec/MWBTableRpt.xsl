<?xml version="1.0" encoding="UTF-8"?>
<!-- edited with XML Spy v4.0.1 U (http://www.xmlspy.com) by Peter Rontey (VHA, OI Field Office) -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html"/>
	<xsl:template match="/">
		<html>
			<head>
				<meta http-equiv="Content-Language" content="en-us"/>
				<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
				<meta name="GENERATOR" content="Microsoft FrontPage 4.0"/>
				<meta name="ProgId" content="FrontPage.Editor.Document"/>
				<title>HL7 Message Profile</title>
			</head>
			<body>
				<basefont face="Lucida Sans Unicode" size="2">
					<h2 align="center">
						<span style="background-color: #FFCC99">HL7 Message Profile</span>
					</h2>
					<table border="1" width="100%" cellspacing="0">
						<xsl:if test="Specification/ImpNote">
							<xsl:apply-templates select="Specification/ImpNote"/>
						</xsl:if>
						<tr>
							<td width="20%" align="right" bordercolor="#FFFFFF">
								<font size="2" color="#FF9966">
									<b>Interface
      ID </b>
								</font>
							</td>
							<td width="80%" bordercolor="#C0C0C0">
								<font size="2" color="#FFFFFF">&#194;&#160;</font>
								<font size="2">
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
								<font size="2" color="#FFFFFF">&#194;&#160;</font>
								<font size="2">
									<xsl:value-of select="Specification/@OrgName"/>
								</font>
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
								<font size="2" color="#FFFFFF">&#194;&#160;</font>
								<font size="2">
									<xsl:value-of select="Specification/@HL7Version"/>
								</font>
							</td>
						</tr>
						<tr>
							<td width="20%" align="right" bordercolor="#FFFFFF">
								<font size="2" color="#FF9966">
									<b>Spec Version
      </b>
								</font>
							</td>
							<td width="80%" bordercolor="#C0C0C0">
								<font size="2" color="#FFFFFF">&#194;&#160;</font>
								<font size="2">
									<xsl:value-of select="Specification/@SpecVersion"/>
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
								<font size="2" color="#FFFFFF">&#194;&#160;</font>
								<font size="2">
									<xsl:value-of select="Specification/@Role"/>
								</font>
							</td>
						</tr>
						<tr>
							<td width="20%" align="right" bordercolor="#FFFFFF">
								<font size="2" color="#FF9966">
									<b>Conformance
      Type</b>
								</font>
							</td>
							<td width="80%" bordercolor="#C0C0C0">
								<font size="2" color="#FFFFFF">&#194;&#160;</font>
								<font size="2">
									<xsl:value-of select="Specification/@ConformanceType"/>
								</font>
							</td>
						</tr>
						<xsl:apply-templates select="Specification/Encodings"/>
						<tr>
							<td width="20%" align="right" bordercolor="#FFFFFF"/>
							<td width="80%" bordercolor="#C0C0C0" bgcolor="#FFCC99">
								<font size="2" color="#C0C0C0">&#194;&#160;</font>
							</td>
						</tr>
						<xsl:apply-templates select="Specification/Message"/>
						<xsl:apply-templates select="Specification/Conformance"/>
					</table>
					<xsl:apply-templates select="Specification/UseCase"/>
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
											<font size="1">NS - not supported</font>
										</li>
										<li>
											<font size="1">U - unknown</font>
										</li>
										<xsl:apply-templates select="Specification/Conformance/UsageExtensions"/>
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
												<span style="background-color: #C0C0C0">NS Element</span>
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
					<xsl:if test="Specification/Message/Segment">
						<xsl:apply-templates select="Specification/Message/Segment"/>
					</xsl:if>
					<xsl:if test="Specification/Message/SegGroup">
						<xsl:apply-templates select="Specification/Message/SegGroup"/>
					</xsl:if>
					<xsl:if test="Specification/hl7tables">
						<xsl:apply-templates select="Specification/hl7tables"/>
					</xsl:if>
					<p/>
					<p/>
				</basefont>
			</body>
		</html>
	</xsl:template>
	<xsl:template match="Conformance">
		<tr>
			<td width="20%" align="right" bordercolor="#FFFFFF">
				<font size="2" color="#FF9966">
					<b>Structure
      Type</b>
				</font>
			</td>
			<td width="80%" bordercolor="#C0C0C0">
				<font size="2" color="#FFFFFF">&#194;&#160;</font>
				<font size="2">
					<xsl:value-of select="/Specification/Message/@MsgStructID"/>
				</font>
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
				<font size="2" color="#FFFFFF">&#194;&#160;</font>
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
				<font size="2" color="#FFFFFF">&#194;&#160;</font>
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
				<font size="2" color="#FFFFFF">&#194;&#160;</font>
				<font size="2">
					<xsl:value-of select="@MsgAckMode"/>
				</font>
			</td>
		</tr>
		<tr>
			<td width="20%" align="right" bordercolor="#FFFFFF">
				<font size="2" color="#FF9966">
					<b>Static
      Profile ID</b>
					<b>&#194;&#160;</b>
				</font>
				<td width="80%" bordercolor="#C0C0C0">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="1">
						<xsl:value-of select="@StaticID"/>
					</font>
				</td>
			</td>
		</tr>
		<tr>
			<td width="20%" align="right" bordercolor="#FFFFFF">
				<font size="2" color="#FF9966">
					<b>Dynamic
      Profile ID</b>
					<b>&#194;&#160;</b>
				</font>
				<td width="80%" bordercolor="#C0C0C0">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="1">
						<xsl:value-of select="@DynamicID"/>
					</font>
				</td>
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="Message">
		<tr>
			<td width="20%" align="right" bordercolor="#FFFFFF">
				<font size="2" color="#FF9966">
					<b>Event
      Description</b>
				</font>
			</td>
			<td width="80%" bordercolor="#C0C0C0">
				<font size="2" color="#FFFFFF">&#194;&#160;</font>
				<font size="2">
					<xsl:value-of select="@EventDesc"/>
				</font>
			</td>
		</tr>
		<tr>
			<td width="20%" align="right" bordercolor="#FFFFFF">
				<font size="2" color="#FF9966">
					<b>Message
      Type</b>
				</font>
			</td>
			<td width="80%" bordercolor="#C0C0C0">
				<font size="2" color="#FFFFFF">&#194;&#160;</font>
				<font size="2">
					<xsl:value-of select="@MsgType"/>
				</font>
			</td>
		</tr>
		<tr>
			<td width="20%" align="right" bordercolor="#FFFFFF">
				<font size="2" color="#FF9966">
					<b>Event
      Type</b>
				</font>
			</td>
			<td width="80%" bordercolor="#C0C0C0">
				<font size="2" color="#FFFFFF">&#194;&#160;</font>
				<font size="2">
					<xsl:value-of select="@EventType"/>
				</font>
			</td>
		</tr>
		<tr>
			<td width="20%" align="right" bordercolor="#FFFFFF">
				<font size="2" color="#FF9966">
					<b>Order
      Control Code</b>
				</font>
			</td>
			<td width="80%" bordercolor="#C0C0C0">
				<font size="2" color="#FFFFFF">&#194;&#160;</font>
				<font size="2">
					<xsl:value-of select="@OrderControl"/>
				</font>
			</td>
		</tr>
		<tr>
			<td width="20%" align="right" bordercolor="#FFFFFF">
				<font size="2" color="#FF9966">
					<b>Message
      Structure</b>
				</font>
			</td>
			<td width="80%" bordercolor="#C0C0C0">
				<font size="2" color="#FFFFFF">&#194;&#160;</font>
				<font size="2">
					<xsl:value-of select="@Structure"/>
				</font>
			</td>
		</tr>
		<tr>
			<td width="20%" align="right" bordercolor="#FFFFFF"/>
			<td width="80%" bordercolor="#C0C0C0" bgcolor="#FFCC99">
				<font size="2" color="#C0C0C0">&#194;&#160;</font>
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="SegGroup">
		<p align="left"/>
		<table border="0" width="102%">
			<xsl:if test="@Optionality != 'NS'">
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
					<td width="5%">
						<font color="#FF9966" size="2">
							<b>Rep</b>
						</font>
					</td>
					<td width="4%">
						<font color="#FF9966" size="2">
							<b>Min</b>
						</font>
					</td>
					<td width="5%">
						<font color="#FF9966" size="2">
							<b>Max</b>
						</font>
					</td>
					<td width="5%">
						<font color="#FF9966" size="2">
							<b>Reference</b>
						</font>
					</td>
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
								<xsl:value-of select="@Description"/>
							</b>
						</font>
					</td>
					<td width="5%">
						<b>
							<font size="2">
								<xsl:value-of select="@Optionality"/>
							</font>
						</b>
					</td>
					<td width="5%">
						<b>
							<font size="2">
								<xsl:value-of select="@Repeatable"/>
							</font>
						</b>
					</td>
					<xsl:if test="@Repeatable[.='True']">
						<td width="4%">
							<b>
								<font size="2">
									<xsl:value-of select="@Min"/>
								</font>
							</b>
						</td>
						<td width="5%">
							<b>
								<font size="2">
									<xsl:value-of select="@Max"/>
								</font>
							</b>
						</td>
					</xsl:if>
					<xsl:if test="@Repeatable[.!='Y']">
						<td width="4%">
							<b>
								<font size="2">&#194;&#160;</font>
							</b>
						</td>
						<td width="5%">
							<b>
								<font size="2">&#194;&#160;</font>
							</b>
						</td>
					</xsl:if>
					<td width="5%">
						<b>
							<font size="2">
								<xsl:value-of select="@Reference"/>
							</font>
						</b>
					</td>
				</tr>
				<xsl:if test="ImpNote">
					<xsl:apply-templates select="ImpNote"/>
				</xsl:if>
				<xsl:if test="HL7Definition">
					<xsl:apply-templates select="HL7Definition"/>
				</xsl:if>
			</xsl:if>
			<!-- Optionality !='NS' -->
			<xsl:if test="@Optionality = 'NS'">
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
					<td width="5%">
						<font color="#FF9966" size="2">
							<b>Rep</b>
						</font>
					</td>
					<td width="4%">
						<font color="#FF9966" size="2">
							<b>Min</b>
						</font>
					</td>
					<td width="5%">
						<font color="#FF9966" size="2">
							<b>Max</b>
						</font>
					</td>
					<td width="5%">
						<font color="#FF9966" size="2">
							<b>Reference</b>
						</font>
					</td>
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
								<xsl:value-of select="@Description"/>
							</b>
						</font>
					</td>
					<td width="5%">
						<b>
							<font size="2">
								<xsl:value-of select="@Optionality"/>
							</font>
						</b>
					</td>
					<td width="5%">
						<b>
							<font size="2">
								<xsl:value-of select="@Repeatable"/>
							</font>
						</b>
					</td>
					<xsl:if test="@Repeatable[.='True']">
						<td width="4%">
							<b>
								<font size="2">
									<xsl:value-of select="@Min"/>
								</font>
							</b>
						</td>
						<td width="5%">
							<b>
								<font size="2">
									<xsl:value-of select="@Max"/>
								</font>
							</b>
						</td>
					</xsl:if>
					<xsl:if test="@Repeatable[.!='Y']">
						<td width="4%">
							<b>
								<font size="2">&#194;&#160;</font>
							</b>
						</td>
						<td width="5%">
							<b>
								<font size="2">&#194;&#160;</font>
							</b>
						</td>
					</xsl:if>
					<td width="5%">
						<b>
							<font size="2">
								<xsl:value-of select="@Reference"/>
							</font>
						</b>
					</td>
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
		<xsl:if test="@Optionality != 'NS'">
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
				<td width="5%">
					<font color="#FF9966" size="2">
						<b>Rep</b>
					</font>
				</td>
				<td width="4%">
					<font color="#FF9966" size="2">
						<b>Min</b>
					</font>
				</td>
				<td width="5%">
					<font color="#FF9966" size="2">
						<b>Max</b>
					</font>
				</td>
				<td width="5%">
					<font color="#FF9966" size="2">
						<b>Reference</b>
					</font>
				</td>
			</tr>
			<xsl:if test="@Optionality != 'NS'">
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
								<xsl:value-of select="@Description"/>
							</b>
						</font>
					</td>
					<td width="5%">
						<b>
							<font size="2">
								<xsl:value-of select="@Optionality"/>
							</font>
						</b>
					</td>
					<td width="5%">
						<b>
							<font size="2">
								<xsl:value-of select="@Repeatable"/>
							</font>
						</b>
					</td>
					<xsl:if test="@Repeatable[.='Y']">
						<td width="4%">
							<b>
								<font size="2">
									<xsl:value-of select="@Min"/>
								</font>
							</b>
						</td>
						<td width="5%">
							<b>
								<font size="2">
									<xsl:value-of select="@Max"/>
								</font>
							</b>
						</td>
					</xsl:if>
					<xsl:if test="@Repeatable[.!='Y']">
						<td width="4%">
							<b>
								<font size="2">&#194;&#160;</font>
							</b>
						</td>
						<td width="5%">
							<b>
								<font size="2">&#194;&#160;</font>
							</b>
						</td>
					</xsl:if>
					<td width="5%">
						<b>
							<font size="2">
								<xsl:value-of select="@Reference"/>
							</font>
						</b>
					</td>
				</tr>
				<xsl:if test="ImpNote">
					<xsl:apply-templates select="ImpNote"/>
				</xsl:if>
				<xsl:if test="HL7Definition">
					<xsl:apply-templates select="HL7Definition"/>
				</xsl:if>
			</xsl:if>
			<xsl:if test="@Optionality = 'NS'">
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
								<xsl:value-of select="@Description"/>
							</b>
						</font>
					</td>
					<td width="5%">
						<b>
							<font size="2">
								<xsl:value-of select="@Optionality"/>
							</font>
						</b>
					</td>
					<td width="5%">
						<b>
							<font size="2">
								<xsl:value-of select="@Repeatable"/>
							</font>
						</b>
					</td>
					<xsl:if test="@Repeatable[.='Y']">
						<td width="4%">
							<b>
								<font size="2">
									<xsl:value-of select="@Min"/>
								</font>
							</b>
						</td>
						<td width="5%">
							<b>
								<font size="2">
									<xsl:value-of select="@Max"/>
								</font>
							</b>
						</td>
					</xsl:if>
					<xsl:if test="@Repeatable[.!='Y']">
						<td width="4%">
							<b>
								<font size="2">&#194;&#160;</font>
							</b>
						</td>
						<td width="5%">
							<b>
								<font size="2">&#194;&#160;</font>
							</b>
						</td>
					</xsl:if>
					<td width="5%">
						<b>
							<font size="2">
								<xsl:value-of select="@Reference"/>
							</font>
						</b>
					</td>
				</tr>
				<xsl:if test="ImpNote">
					<xsl:apply-templates select="ImpNote"/>
				</xsl:if>
				<xsl:if test="HL7Definition">
					<xsl:apply-templates select="HL7Definition"/>
				</xsl:if>
			</xsl:if>
		</table>
		<xsl:if test="@Optionality != 'NS'">
			<P align="center">
				<b>
					<font size="2">
						<span style="background-color: #FFCC99">Fields</span>
					</font>
				</b>
			</P>
			<table border="1" width="100%" cellspacing="1">
				<tr bordercolor="#FFFFFF">
					<td width="25%">
						<font size="2" color="#FF9966">
							<b>Name</b>
						</font>
					</td>
					<td width="5%">
						<font size="2" color="#FF9966">
							<b>Seq</b>
						</font>
					</td>
					<td width="5%">
						<font size="2" color="#FF9966">
							<b>DT</b>
						</font>
					</td>
					<td width="5%">
						<font size="2" color="#FF9966">
							<b>Len</b>
						</font>
					</td>
					<td width="5%">
						<font size="2" color="#FF9966">
							<b>Opt</b>
						</font>
					</td>
					<td width="5%">
						<font size="2" color="#FF9966">
							<b>Rep</b>
						</font>
					</td>
					<td width="5%">
						<font size="2" color="#FF9966">
							<b>Min</b>
						</font>
					</td>
					<td width="5%">
						<font size="2" color="#FF9966">
							<b>Max</b>
						</font>
					</td>
					<td width="5%">
						<font size="2" color="#FF9966">
							<b>Tbl</b>
						</font>
					</td>
					<td width="10%">
						<font size="2" color="#FF9966">
							<b>Predicate</b>
						</font>
					</td>
					<td width="10%">
						<font size="2" color="#FF9966">
							<b>Fixed Val</b>
						</font>
					</td>
					<td width="10%">
						<font size="2" color="#FF9966">
							<b>Ex Val</b>
						</font>
					</td>
					<td width="5%">
						<font size="2" color="#FF9966">
							<b>Reference</b>
						</font>
					</td>
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
		<xsl:if test="@Optionality[.!='NS']">
			<tr>
				<td width="25%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Name"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Sequence"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
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
				<td width="5%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Length"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Optionality"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Repeatable"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Min"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Max"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Table"/>
					</font>
				</td>
				<td width="10%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Predicate"/>
					</font>
				</td>
				<xsl:choose>
					<xsl:when test="DataValues">
						<xsl:apply-templates select="DataValues"/>
					</xsl:when>
					<xsl:otherwise>
						<td width="10%">
							<font size="1" color="FFFFFF">&#194;&#160;</font>
							<font size="2" color="FFFFFF">&#194;&#160;</font>
						</td>
						<td width="10%">
							<font size="1" color="FFFFFF">&#194;&#160;</font>
							<font size="2" color="FFFFFF">&#194;&#160;</font>
						</td>
					</xsl:otherwise>
				</xsl:choose>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Reference"/>
					</font>
				</td>
			</tr>
			<xsl:if test="ImpNote">
				<xsl:apply-templates select="ImpNote"/>
			</xsl:if>
			<xsl:if test="HL7Definition">
				<xsl:apply-templates select="HL7Definition"/>
			</xsl:if>
			<xsl:apply-templates select="Component"/>
		</xsl:if>
		<xsl:if test="@Optionality[.='NS']">
			<tr bgColor="C0C0C0">
				<td width="25%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Name"/>
					</font>
				</td>
				<td width="5%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Sequence"/>
					</font>
				</td>
				<td width="5%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Datatype"/>
					</font>
				</td>
				<td width="5%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Length"/>
					</font>
				</td>
				<td width="5%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Optionality"/>
					</font>
				</td>
				<td width="5%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Repeatable"/>
					</font>
				</td>
				<td width="5%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Min"/>
					</font>
				</td>
				<td width="5%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Max"/>
					</font>
				</td>
				<td width="5%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Table"/>
					</font>
				</td>
				<td width="10%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2" color="C0C0C0">&#194;&#160;</font>
				</td>
				<td width="10%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2" color="C0C0C0">&#194;&#160;</font>
				</td>
				<td width="10%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2" color="C0C0C0">&#194;&#160;</font>
				</td>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Reference"/>
					</font>
				</td>
			</tr>
		</xsl:if>
	</xsl:template>
	<xsl:template match="Component">
		<xsl:if test="@Optionality[.!='NS']">
			<tr>
				<td width="25%">
					<font size="2" color="#FFFFFF">&#194;&#160;&#194;&#160;</font>
					<font size="2" color="#0000FF">
						<xsl:value-of select="@Name"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="2" color="#0000FF">
						<xsl:value-of select="@Sequence"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
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
				<td width="5%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Length"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Optionality"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Repeatable"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Min"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Max"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Table"/>
					</font>
				</td>
				<td width="10%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Predicate"/>
					</font>
				</td>
				<xsl:choose>
					<xsl:when test="DataValues">
						<xsl:apply-templates select="DataValues"/>
					</xsl:when>
					<xsl:otherwise>
						<td width="10%">
							<font size="1" color="FFFFFF">&#194;&#160;</font>
							<font size="2" color="FFFFFF">&#194;&#160;</font>
						</td>
						<td width="10%">
							<font size="1" color="FFFFFF">&#194;&#160;</font>
							<font size="2" color="FFFFFF">&#194;&#160;</font>
						</td>
					</xsl:otherwise>
				</xsl:choose>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Reference"/>
					</font>
				</td>
			</tr>
			<xsl:if test="ImpNote">
				<xsl:apply-templates select="ImpNote"/>
			</xsl:if>
			<xsl:if test="HL7Definition">
				<xsl:apply-templates select="HL7Definition"/>
			</xsl:if>
			<xsl:apply-templates select="SubComponent"/>
		</xsl:if>
		<xsl:if test="@Optionality[.='NS']">
			<tr bgColor="C0C0C0">
				<td width="25%">
					<font size="1" color="C0C0C0">&#194;&#160;&#194;&#160;</font>
					<font size="2" color="#0000FF">
						<xsl:value-of select="@Name"/>
					</font>
				</td>
				<td width="5%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2" color="#0000FF">
						<xsl:value-of select="@Sequence"/>
					</font>
				</td>
				<td width="5%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Datatype"/>
					</font>
				</td>
				<td width="5%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Length"/>
					</font>
				</td>
				<td width="5%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Optionality"/>
					</font>
				</td>
				<td width="5%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Repeatable"/>
					</font>
				</td>
				<td width="5%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Min"/>
					</font>
				</td>
				<td width="5%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Max"/>
					</font>
				</td>
				<td width="5%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Table"/>
					</font>
				</td>
				<td width="10%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2" color="C0C0C0">&#194;&#160;</font>
				</td>
				<td width="10%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2" color="C0C0C0">&#194;&#160;</font>
				</td>
				<td width="10%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2" color="C0C0C0">&#194;&#160;</font>
				</td>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Reference"/>
					</font>
				</td>
			</tr>
		</xsl:if>
	</xsl:template>
	<xsl:template match="SubComponent">
		<xsl:if test="@Optionality[.!='NS']">
			<tr>
				<td width="25%">
					<font size="2" color="#FFFFFF">&#194;&#160;&#194;&#160;&#194;&#194;&#160;</font>
					<font size="2" color="#FF00FF">
						<xsl:value-of select="@Name"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="2" color="#FF00FF">
						<xsl:value-of select="@Sequence"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Datatype"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Length"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Optionality"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Repeatable"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Min"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Max"/>
					</font>
				</td>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Table"/>
					</font>
				</td>
				<td width="10%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Predicate"/>
					</font>
				</td>
				<xsl:choose>
					<xsl:when test="DataValues">
						<xsl:apply-templates select="DataValues"/>
					</xsl:when>
					<xsl:otherwise>
						<td width="10%">
							<font size="1" color="FFFFFF">&#194;&#160;</font>
							<font size="2" color="FFFFFF">&#194;&#160;</font>
						</td>
						<td width="10%">
							<font size="1" color="FFFFFF">&#194;&#160;</font>
							<font size="2" color="FFFFFF">&#194;&#160;</font>
						</td>
					</xsl:otherwise>
				</xsl:choose>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Reference"/>
					</font>
				</td>
			</tr>
			<xsl:if test="ImpNote">
				<xsl:apply-templates select="ImpNote"/>
			</xsl:if>
			<xsl:if test="HL7Definition">
				<xsl:apply-templates select="HL7Definition"/>
			</xsl:if>
		</xsl:if>
		<xsl:if test="@Optionality[.='NS']">
			<tr bgColor="C0C0C0">
				<td width="25%">
					<font size="1" color="C0C0C0">&#194;&#160;&#194;&#160;&#194;&#194;&#160;</font>
					<font size="2" color="#FF00FF">
						<xsl:value-of select="@Name"/>
					</font>
				</td>
				<td width="5%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2" color="#FF00FF">
						<xsl:value-of select="@Sequence"/>
					</font>
				</td>
				<td width="5%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Datatype"/>
					</font>
				</td>
				<td width="5%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Length"/>
					</font>
				</td>
				<td width="5%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Optionality"/>
					</font>
				</td>
				<td width="5%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Repeatable"/>
					</font>
				</td>
				<td width="5%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Min"/>
					</font>
				</td>
				<td width="5%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Max"/>
					</font>
				</td>
				<td width="5%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Table"/>
					</font>
				</td>
				<td width="10%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2" color="C0C0C0">&#194;&#160;</font>
				</td>
				<td width="10%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2" color="C0C0C0">&#194;&#160;</font>
				</td>
				<td width="10%">
					<font size="1" color="C0C0C0">&#194;&#160;</font>
					<font size="2" color="C0C0C0">&#194;&#160;</font>
				</td>
				<td width="5%">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
					<font size="2">
						<xsl:value-of select="@Reference"/>
					</font>
				</td>
			</tr>
		</xsl:if>
	</xsl:template>
	<xsl:template match="DataValues">
		<td width="10%">
			<font size="2" color="#FFFFFF">&#194;&#160;</font>
			<font size="2">
				<xsl:if test="@Fixed[.='True']">
					<xsl:value-of select="@Fixed"/>
				</xsl:if>
			</font>
		</td>
		<td width="10%">
			<font size="2" color="#FFFFFF">&#194;&#160;</font>
			<font size="2">
				<xsl:value-of select="@ExValue"/>
			</font>
		</td>
	</xsl:template>
	<xsl:template match="Actor">
		<tr>
			<td width="28%">
				<font size="2" color="#FFFFFF">&#194;&#160;</font>
				<font size="2">
					<xsl:value-of select="@Name"/>
				</font>
			</td>
			<td width="72%">
				<font size="2" color="#FFFFFF">&#194;&#160;</font>
				<font size="2">
					<xsl:value-of select="@Value"/>
				</font>
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="PreCondition">
		<tr>
			<td width="4%">
				<font size="2" color="#FFFFFF">&#194;&#160;</font>
				<font size="2">
					<xsl:value-of select="@Name"/>
				</font>
			</td>
			<td width="96%">
				<font size="2" color="#FFFFFF">&#194;&#160;</font>
				<font size="2">
					<xsl:value-of select="@Value"/>
				</font>
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="EventFlow">
		<tr>
			<td width="4%">
				<font size="2" color="#FFFFFF">&#194;&#160;</font>
				<font size="2">
					<xsl:value-of select="@Name"/>
				</font>
			</td>
			<td width="96%">
				<font size="2" color="#FFFFFF">&#194;&#160;</font>
				<font size="2">
					<xsl:value-of select="@Value"/>
				</font>
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="PostCondition">
		<tr>
			<td width="4%">
				<font size="2" color="#FFFFFF">&#194;&#160;</font>
				<font size="2">
					<xsl:value-of select="@Name"/>
				</font>
			</td>
			<td width="96%">
				<font size="2" color="#FFFFFF">&#194;&#160;</font>
				<font size="2">
					<xsl:value-of select="@Value"/>
				</font>
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="DerivativeEvent">
		<tr>
			<td width="4%">
				<font size="2" color="#FFFFFF">&#194;&#160;</font>
				<font size="2">
					<xsl:value-of select="@Name"/>
				</font>
			</td>
			<td width="96%">
				<font size="2" color="#FFFFFF">&#194;&#160;</font>
				<font size="2">
					<xsl:value-of select="@Value"/>
				</font>
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="ImpNote">
		<tr bgcolor="#00CC66" Height="5" style="border:none">
			<td width="100%" colspan="11" style="border:none">
				<font size="1">Implementation Note: </font>
			</td>
		</tr>
		<xsl:for-each select="Paragraph">
			<tr bgcolor="#00CC66" Height="5" style="border:none">
				<td width="100%" colspan="11" style="width:95.4%;border:none;background:#CCFFCC;
  padding:.75pt .75pt .75pt .75pt;height:3.75pt">
					<font color="#00CC66">&#194;&#160;</font>
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
					<font color="#00FFFF">&#194;&#160;</font>
					<font size="1" color="#0000FF">
						<xsl:value-of select="."/>
					</font>
				</td>
			</tr>
		</xsl:for-each>
	</xsl:template>
	<xsl:template match="UseCase">
		<h3 align="center">
			<span style="background-color: #FFCC99">Use Case Parameters</span>
		</h3>
		<table border="1" width="100%" cellspacing="1">
			<tr bordercolor="#FFFFFF">
				<td width="28%">
					<font size="2" color="#FF9966">
						<b>Actors</b>
					</font>
				</td>
				<td width="72%">
					<font size="2" color="#FF9966">
						<b>Descriptions</b>
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
						<b>Derivative Events</b>
					</font>
				</td>
			</tr>
			<xsl:apply-templates select="DerivativeEvent"/>
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
	</xsl:template>
	<xsl:template match="Encodings">
		<tr>
			<td width="20%" align="right" bordercolor="#FFFFFF">
				<font size="2" color="#FF9966">
					<b> Encodings  </b>
				</font>
			</td>
			<td width="80%" bordercolor="#C0C0C0">
				<font size="2" color="#FFFFFF">&#194;&#160;</font>
			</td>
		</tr>
		<xsl:for-each select="Encoding">
			<tr>
				<td width="20%" align="right" bordercolor="#C0C0C0"/>
				<td width="80%" bordercolor="#C0C0C0">
					<font size="2" color="#FFFFFF">&#194;&#160;</font>
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
					<xsl:value-of select="@Code"/>&#194;&#160;-&#194;&#160;
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
		<xsl:apply-templates select="TableNotes"/>
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
				<font size="2">&#194;&#160;
					<xsl:value-of select="@order"/>
				</font>
			</td>
			<td width="3%">
				<font size="2">&#194;&#160;
					<xsl:value-of select="@code"/>
				</font>
			</td>
			<td width="50%">
				<font size="2">&#194;&#160;
					<xsl:value-of select="@description"/>
				</font>
			</td>
			<td width="3%">
				<font size="2">&#194;&#160;
					<xsl:value-of select="@source"/>
				</font>
			</td>
			<td width="2%">
				<font size="2">&#194;&#160;
					<xsl:value-of select="@usage"/>
				</font>
			</td>
			<td width="15%">
				<font size="2">&#194;&#160;
					<xsl:value-of select="@displayName"/>
				</font>
			</td>
			<td width="25%">
				<font size="2">&#194;&#160;
					<xsl:value-of select="@instruction"/>
				</font>
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="TableNotes">
	<table width="100%" cellspacing="1">
		<tbody>
		<tr width="100%" bgcolor="#00CC66" Height="5" style="border:none">
			<td width="100%" colspan="11" style="border:none">
				<font size="1">Table Notes: </font>
			</td>
		</tr>
		<xsl:for-each select="para">
			<tr bgcolor="#00CC66" Height="5" style="border:none">
				<td width="100%" colspan="100" style="width:95.4%;border:none;background:#CCFFCC;
  padding:.75pt .75pt .75pt .75pt;height:3.75pt">
					<font color="#00CC66">&#194;&#160;</font>
					<font size="1" color="#0000FF">
						<xsl:value-of select="."/>
					</font>
				</td>
			</tr>
		</xsl:for-each>
	</tbody>
	</table>
	</xsl:template>
</xsl:stylesheet>
