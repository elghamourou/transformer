<?xml version="1.0" encoding="UTF-8"?>
<!-- edited with XMLSPY v5 rel. 3 U (http://www.xmlspy.com) by Peter Rontey (Department of Veterans Affairs) -->
<!-- edited with XML Spy v4.0.1 U (http://www.xmlspy.com) by Peter Rontey (VHA, OI Field Office) -->
<?xml-stylesheet type="text/xsl" href="C:\Program Files\Borland\Delphi6\Projects\mwb\Lib\XSL Spec\MWBProfileRevNO.xsl"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html"/>
	<xsl:template match="/">
		<html>
			<head>
				<meta http-equiv="Content-Language" content="en-us"/>
				<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
				<meta name="GENERATOR" content="Microsoft FrontPage 4.0"/>
				<meta name="ProgId" content="FrontPage.Editor.Document"/>
				<title>HL7 Message Profile Review</title>
			</head>
			<body>
				<basefont face="Lucida Sans Unicode" size="2">
					<h2 align="center">
						<span style="background-color: #FFCC99">HL7 Message Profile Review</span>
					</h2>
					<table border="1" width="100%" cellspacing="0">
						<tr>
							<td width="20%" align="right" bordercolor="#FFFFFF">
								<font size="2" color="#FF9966">
									<b>Interface
      ID </b>
								</font>
							</td>
							<td width="80%" bordercolor="#C0C0C0">
								<font size="2" color="#FFFFFF">
									<xsl:call-template name="nbsp"/>
								</font>
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
								<font size="2" color="#FFFFFF">
									<xsl:call-template name="nbsp"/>
								</font>
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
								<font size="2" color="#FFFFFF">
									<xsl:call-template name="nbsp"/>
								</font>
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
								<font size="2" color="#FFFFFF">
									<xsl:call-template name="nbsp"/>
								</font>
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
								<font size="2" color="#FFFFFF">
									<xsl:call-template name="nbsp"/>
								</font>
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
								<font size="2" color="#FFFFFF">
									<xsl:call-template name="nbsp"/>
								</font>
								<font size="2">
									<xsl:value-of select="Specification/@ConformanceType"/>
								</font>
							</td>
						</tr>
						<tr>
							<td width="20%" align="right" bordercolor="#FFFFFF"/>
							<td width="80%" bordercolor="#C0C0C0" bgcolor="#FFCC99">
								<font size="2" color="#C0C0C0">
									<xsl:call-template name="nbsp"/>
								</font>
							</td>
						</tr>
						<xsl:apply-templates select="Specification/Message"/>
					</table>
					<h5>General Findings:</h5>
					<xsl:if test="Specification/ReviewNote">
						<xsl:apply-templates select="Specification/ReviewNote"/>
						<p/>
					</xsl:if>
					<h5>Detailed Findings:</h5>
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
				<font size="2" color="#FFFFFF">
					<xsl:call-template name="nbsp"/>
				</font>
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
				<font size="2" color="#FFFFFF">
					<xsl:call-template name="nbsp"/>
				</font>
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
				<font size="2" color="#FFFFFF">
					<xsl:call-template name="nbsp"/>
				</font>
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
				<font size="2" color="#FFFFFF">
					<xsl:call-template name="nbsp"/>
				</font>
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
					<b>
						<xsl:call-template name="nbsp"/>
					</b>
				</font>
				<td width="80%" bordercolor="#C0C0C0">
					<font size="2" color="#FFFFFF">
						<xsl:call-template name="nbsp"/>
					</font>
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
					<b>
						<xsl:call-template name="nbsp"/>
					</b>
				</font>
				<td width="80%" bordercolor="#C0C0C0">
					<font size="2" color="#FFFFFF">
						<xsl:call-template name="nbsp"/>
					</font>
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
				<font size="2" color="#FFFFFF">
					<xsl:call-template name="nbsp"/>
				</font>
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
				<font size="2" color="#FFFFFF">
					<xsl:call-template name="nbsp"/>
				</font>
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
				<font size="2" color="#FFFFFF">
					<xsl:call-template name="nbsp"/>
				</font>
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
				<font size="2" color="#FFFFFF">
					<xsl:call-template name="nbsp"/>
				</font>
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
				<font size="2" color="#FFFFFF">
					<xsl:call-template name="nbsp"/>
				</font>
				<font size="2">
					<xsl:value-of select="@Structure"/>
				</font>
			</td>
		</tr>
		<tr>
			<td width="20%" align="right" bordercolor="#FFFFFF"/>
			<td width="80%" bordercolor="#C0C0C0" bgcolor="#FFCC99">
				<font size="2" color="#C0C0C0">
					<xsl:call-template name="nbsp"/>
				</font>
			</td>
		</tr>
	</xsl:template>
	
<!-- start -->
	<xsl:template match="SegGroup">
		<p align="left"/>
		<xsl:if test="ReviewNote">
<!-- 		<xsl:choose>
		    <xsl:when test="../SegGroup">
			 <xsl:value-of select="../@Name"/>.<xsl:value-of select="@Name"/>
		     </xsl:when>
	 	     <xsl:otherwise>
	 	        <xsl:value-of select="@Name"/>
	 	      </xsl:otherwise>
		   </xsl:choose> -->
 <!--              <xsl:if test="../SegGroup">
                  <xsl:value-of select="../@Name"/>.
               </xsl:if> -->
		  
                   <xsl:value-of select="@Name"/>

			<xsl:apply-templates select="ReviewNote"/>
		</xsl:if>
		<xsl:if test="Segment">
			<xsl:apply-templates select="Segment"/>
		</xsl:if>
		<xsl:if test="SegGroup">
			<xsl:apply-templates select="SegGroup"/>
		</xsl:if>
	</xsl:template>
	<xsl:template match="Segment">
		<p align="left"/>
		<xsl:if test="ReviewNote">
	<!--	<xsl:choose>
		    <xsl:when test="../SegGroup">
			 <xsl:value-of select="../@Name"/>.<xsl:value-of select="@Name"/>
		     </xsl:when>
	 	     <xsl:otherwise>
	 	        <xsl:value-of select="@Name"/>
	 	      </xsl:otherwise>
		   </xsl:choose> -->

  <!--             <xsl:if test="../SegGroup">
                  <xsl:value-of select="../@Name"/>.
               </xsl:if>  -->
		   <xsl:value-of select="@Name"/>

	  	   <xsl:apply-templates select="ReviewNote"/>
		   <br/>
		</xsl:if>
		<xsl:apply-templates select="Field"/>
		<xsl:if test="SegGroup">
			<xsl:apply-templates select="SegGroup"/>
		</xsl:if>
	</xsl:template>
	<xsl:template match="Field">
		<xsl:if test="ReviewNote">
<!--		  <xsl:if test="../../SegGroup">
		     <xsl:value-of select="../../@Name"/>.
		  </xsl:if> -->
	      <xsl:value-of select="../@Name"/>.<xsl:value-of select="@Sequence"/> [<xsl:value-of select="@Name"/>]

			<xsl:apply-templates select="ReviewNote"/>
		      <br/>

		</xsl:if>
<xsl:apply-templates select="Component"/>
	</xsl:template>
	<xsl:template match="Component">
		<xsl:if test="ReviewNote">
<!--		  <xsl:if test="../../../SegGroup">
		     <xsl:value-of select="../../../@Name"/>.
		  </xsl:if> -->
	      <xsl:value-of select="../../@Name"/>.<xsl:value-of select="../@Sequence"/>.<xsl:value-of select="@Sequence"/> [<xsl:value-of select="@Name"/>]

			<xsl:apply-templates select="ReviewNote"/>
			<br/>

		</xsl:if>
		<xsl:apply-templates select="SubComponent"/>
	</xsl:template>
	<xsl:template match="SubComponent">
		<xsl:if test="ReviewNote">
<!--		  <xsl:if test="../../../../SegGroup">
		     <xsl:value-of select="../../../../@Name"/>.
		  </xsl:if> -->
		
	      <xsl:value-of select="../../../@Name"/>.<xsl:value-of select="../../@Sequence"/>.<xsl:value-of select="../@Sequence"/>.<xsl:value-of select="@Sequence"/> [<xsl:value-of select="@Name"/>]

		<xsl:apply-templates select="ReviewNote"/>
		<br/>

		</xsl:if>
	</xsl:template>
	<xsl:template match="ReviewNote">
		<xsl:for-each select="Paragraph">
			<font color="#FF99FF"/>
			<font size="2" color="#0000FF">
				<xsl:call-template name="nbsp"/>
				<br>
					<xsl:value-of select="translate(.,'&#160;',' ')"/>
					<!-- <xsl:value-of select="."/> 4/5  -->
				</br>
			</font>
		</xsl:for-each>
		<p/>
	</xsl:template>
	<xsl:template name="nbsp">
		<xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>
	</xsl:template>
</xsl:stylesheet>
