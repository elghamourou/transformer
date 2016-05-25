<?xml version='1.0'?>
                                                              
<xsl:stylesheet xmlns:xsl="http://www.w3.org/TR/WD-xsl">   

<xsl:template match="/">

<html>

<head>
<meta http-equiv="Content-Language" content="en-us"/>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
<meta name="GENERATOR" content="Microsoft FrontPage 4.0"/>
<meta name="ProgId" content="FrontPage.Editor.Document"/>
<title>HL7 Message Comparison</title>

</head>
<body>
<basefont face="Lucida Sans Unicode" size="2">
<h2 align="center"><span style="background-color: #FFCC99">HL7 Message Comparison</span></h2>
<table border="1" width="100%" cellspacing="0">
  <tr>
    <td width="20%" align="right" bordercolor="#FFFFFF"><font size="2" color="#FF9966"><b>Interface
      ID </b></font></td>
    <td width="80%" bordercolor="#C0C0C0"><font size="2" color="#FFFFFF">&#160;</font>
<font size="2" >
  <xsl:value-of   select="Specification/@SpecName"/>
   <xsl:if test="Specification/Differences">
    &#160;-vs-&#160;<font size="2"  color="#0000FF" >
    <xsl:value-of select="Specification/Differences/@SpecName"/></font>
   </xsl:if>
</font>
  </td>
  </tr>
  <tr>
    <td width="20%" align="right" bordercolor="#FFFFFF"><font size="2"  color="#FF9966"><b>Organization
      </b></font></td>
    <td width="80%" bordercolor="#C0C0C0"><font size="2" color="#FFFFFF">&#160;</font>
<font size="2" ><xsl:value-of   select="Specification/@OrgName"/></font>
<xsl:if test="Specification/Differences/@Orgname">
<font size="2"  color="#0000FF" >&#160;(<xsl:value-of select="Specification/Differences/@OrgName"/>)</font>
 </xsl:if>
</td>
  </tr>
  <tr>
    <td width="20%" align="right" bordercolor="#FFFFFF"><font size="2"  color="#FF9966"><b>HL7 Version
      </b></font></td>
    <td width="80%" bordercolor="#C0C0C0"><font size="2" color="#FFFFFF">&#160;</font>
<font size="2" ><xsl:value-of   select="Specification/@HL7Version"/></font>
<xsl:if test="Specification/Differences/@HL7Version">
<font size="2"  color="#0000FF" >&#160;(<xsl:value-of select="Specification/Differences/@HL7Version"/>)</font>
</xsl:if>
</td>
  </tr>
  <tr>
    <td width="20%" align="right" bordercolor="#FFFFFF"><font size="2"  color="#FF9966"><b>Spec Version
      </b></font></td>
    <td width="80%" bordercolor="#C0C0C0"><font size="2" color="#FFFFFF">&#160;</font>
<font size="2" ><xsl:value-of   select="Specification/@SpecVersion"/></font>
<xsl:if test="Specification/Differences/@SpecVersion">
<font size="2"  color="#0000FF" >&#160;(<xsl:value-of select="Specification/Differences/@SpecVersion"/>)</font>
</xsl:if>
</td>
  </tr>
  <tr>
    <td width="20%" align="right" bordercolor="#FFFFFF"><font size="2"  color="#FF9966"><b>Application Role
      </b></font></td>
    <td width="80%" bordercolor="#C0C0C0"><font size="2" color="#FFFFFF">&#160;</font>
<font size="2" ><xsl:value-of   select="Specification/@Role"/></font>
<xsl:if test="Specification/Differences/@HL7Version">
<font size="2"  color="#0000FF" >&#160;(<xsl:value-of select="Specification/Differences/@Role"/>)</font>
</xsl:if>
</td>
  </tr>

 <tr>
    <td width="20%" align="right" bordercolor="#FFFFFF"><font size="2" color="#FF9966"><b>Conformance
      Type</b></font></td>
    <td width="80%" bordercolor="#C0C0C0"><font size="2" color="#FFFFFF">&#160;</font>
<font size="2" ><xsl:value-of   select="Specification/@ConformanceType"/></font>
    <xsl:if test="Specification/Differences/@ConformanceType">
      <font size="2"  color="#0000FF" >&#160;(<xsl:value-of select="Specification/Differences/@ConformanceType"/>)</font>
    </xsl:if>
 </td>
  </tr>


<xsl:apply-templates select="Specification/Message"/>

</table>


<h3 align="center"><span style="background-color: #FFCC99">Segments</span></h3>
<TABLE width="100%" border="0">
<TBODY>
<TR>
<TD width="35%" valign="top"><font size="1">Optionality Codes:</font>
  <ul>
    <li><font size="1">R - required</font></li>
    <li><font size="1">RE - required or empty</font></li>
    <li><font size="1">C - conditional</font></li>
    <li><font size="1">CE - conditional or empty</font></li>
    <li><font size="1">O - optional</font></li>
    <li><font size="1">NS - not supported</font></li>
    <li><font size="1">U - unknown</font></li>
  </ul>
</TD>
<TD width="30%" valign="top"><font size="1">Abbreviations:</font>
  <ul>
    <li><font size="1">seq - sequence</font></li>
    <li><font size="1">DT - datatype</font></li>
    <li><font size="1">Len - length</font></li>
    <li><font size="1">Opt - optionality</font></li>
    <li><font size="1">Rep - repeatable</font></li>
    <li><font size="1">Min - quantity min</font></li>
    <li><font size="1">Max - quantity max</font></li>
    <li><font size="1">Tbl - table</font></li>
    <li><font size="1">(np) - not present</font></li>
  </ul>
</TD>
<TD width="35%" valign="top"><font size="1">Color codes:</font>
  <ul>
    <li><FONT size="1">Fields</FONT></li>
    <li><font color="#0000ff" size="1">Components</font></li>
    <li><FONT color="#ff00ff" size="1">Sub Components</FONT></li>
    <li><font size="1"><span style=" background-color:#00FFFF"> difference note</span></font></li>
  </ul>
</TD></TR>

</TBODY></TABLE>

  <xsl:if test="Specification/Message/Segment">
     <xsl:apply-templates select="Specification/Message/Segment"/>
  </xsl:if>
  <xsl:if test="Specification/Message/SegGroup">
     <xsl:apply-templates select="Specification/Message/SegGroup"/>
  </xsl:if>

<p/>
<p/>
</basefont>
</body>
</html>
</xsl:template>



<xsl:template match="Message">


   <tr>
    <td width="20%" align="right" bordercolor="#FFFFFF"></td>
    <td width="80%" bordercolor="#C0C0C0" bgcolor="#FFCC99"><font size="2" color="#C0C0C0">&#160;</font></td>
  </tr>
  <tr>
    <td width="20%" align="right" bordercolor="#FFFFFF"><font size="2"  color="#FF9966"><b>Message
      Type</b></font></td>
    <td width="80%" bordercolor="#C0C0C0"><font size="2" color="#FFFFFF">&#160;</font>
<font size="2" ><xsl:value-of   select="@MsgType"/></font>
    <xsl:if test="Differences/@MsgType">
      <font size="2"  color="#0000FF" >&#160;(<xsl:value-of select="Differences/@MsgType"/>)</font>
    </xsl:if>
</td>
  </tr>
  <tr>
    <td width="20%" align="right" bordercolor="#FFFFFF"><font size="2"  color="#FF9966"><b>Event
      Type</b></font></td>
    <td width="80%" bordercolor="#C0C0C0"><font size="2" color="#FFFFFF">&#160;</font>
<font size="2" ><xsl:value-of   select="@EventType"/></font>
    <xsl:if test="Differences/@EventType">
      <font size="2"  color="#0000FF" >&#160;(<xsl:value-of select="Differences/@EventType"/>)</font>
    </xsl:if>

</td>
  </tr>
  <tr>
    <td width="20%" align="right" bordercolor="#FFFFFF"><font size="2"  color="#FF9966"><b>Order
      Control Code</b></font></td>
    <td width="80%" bordercolor="#C0C0C0"><font size="2" color="#FFFFFF">&#160;</font>
<font size="2" ><xsl:value-of   select="@OrderControl"/></font>
    <xsl:if test="Differences/@OrderControl">
      <font size="2"  color="#0000FF" >&#160;(<xsl:value-of select="Differences/@OrderControl"/>)</font>
    </xsl:if>

</td>
  </tr>
  <tr>
    <td width="20%" align="right" bordercolor="#FFFFFF"><font size="2"  color="#FF9966"><b>Structure
      Type</b></font></td>
    <td width="80%" bordercolor="#C0C0C0"><font size="2" color="#FFFFFF">&#160;</font>
    <font size="2" ><xsl:value-of   select="@MsgStructId"/></font>
    <xsl:if test="Differences/@MsgStructId">
      <font size="2"  color="#0000FF" >&#160;(<xsl:value-of select="Differences/@MsgStructId"/>)</font>
    </xsl:if>


    </td>
  </tr>
  <tr>
    <td width="20%" align="right" bordercolor="#FFFFFF"><font size="2"  color="#FF9966"><b>Event
      Description</b></font></td>
    <td width="80%" bordercolor="#C0C0C0"><font size="2" color="#FFFFFF">&#160;</font>
<font size="2" ><xsl:value-of   select="Event/@Description"/></font>
    <xsl:if test="Differences/@EventDesc">
      <font size="2"  color="#0000FF" ><br>&#160;(<xsl:value-of select="Differences/@EventDesc"/>)</br></font>
    </xsl:if>

</td>
  </tr>
  <tr>
    <td width="20%" align="right" bordercolor="#FFFFFF"><font size="2"  color="#FF9966"><b>Message
      Structure</b></font></td>
    <td width="80%" bordercolor="#C0C0C0"><font size="2" color="#FFFFFF">&#160;</font>
<font size="2" ><xsl:value-of   select="Structure/@MsgStruct"/></font>

     <xsl:if test="Differences/@MsgStruct">
      <font size="2"  color="#0000FF" ><br>&#160;(<xsl:value-of select="Differences/@MsgStruct"/>)</br></font>
    </xsl:if>

</td>
  </tr>
  <tr>
    <td width="20%" align="right" bordercolor="#FFFFFF"><font size="2"  color="#FF9966"><b>Accept
      Ack</b></font></td>
    <td width="80%" bordercolor="#C0C0C0"><font size="2" color="#FFFFFF">&#160;</font>
<font size="2" ><xsl:value-of   select="Conformance/@AccAck"/></font>
     <xsl:if test="Differences/@AccAck">
      <font size="2"  color="#0000FF" >&#160;(<xsl:value-of select="Differences/@AccAck"/>)</font>
    </xsl:if>

</td>
  </tr>
  <tr>
    <td width="20%" align="right" bordercolor="#FFFFFF"><b><font size="2" color="#FF9966"><font size="2"  >Application
      </font><font size="2"  >Ack</font></font></b></td>
    <td width="80%" bordercolor="#C0C0C0"><font size="2" color="#FFFFFF">&#160;</font>
<font size="2" ><xsl:value-of   select="Conformance/@AppAck"/></font>
     <xsl:if test="Differences/@AppAck">
      <font size="2"  color="#0000FF" >&#160;(<xsl:value-of select="Differences/@AppAck"/>)</font>
    </xsl:if>

</td>
  </tr>
  <tr>
    <td width="20%" align="right" bordercolor="#FFFFFF"><b><font size="2" color="#FF9966">Ack Mode</font></b></td>
    <td width="80%" bordercolor="#C0C0C0"><font size="2" color="#FFFFFF">&#160;</font>
<font size="2" ><xsl:value-of   select="Conformance/@MsgAckMode"/></font>
     <xsl:if test="Differences/@MsgAckMode">
      <font size="2"  color="#0000FF" ><br>&#160;(<xsl:value-of select="Differences/@MsgAckMode"/>)</br></font>
    </xsl:if>

</td>
  </tr>

  <tr>
    <td width="20%" align="right" bordercolor="#FFFFFF"></td>
    <td width="80%" bordercolor="#C0C0C0" bgcolor="#FFCC99"><font size="2" color="#C0C0C0">&#160;</font></td>
  </tr>
  <tr>
    <td width="20%" align="right" bordercolor="#FFFFFF"><font size="2"  color="#FF9966"><b>Static
      Profile ID</b><b>&#160;</b></font>
    <td width="80%" bordercolor="#C0C0C0"><font size="2" color="#FFFFFF">&#160;</font>
     <font size="1" ><xsl:value-of   select="Conformance/@Static"/></font>
     <xsl:if test="Differences/@Static">
      <font size="1"  color="#0000FF" ><br>&#160;(<xsl:value-of select="Differences/@Static"/>)</br></font>
    </xsl:if>

</td>
</td>
  </tr>
  <tr>
    <td width="20%" align="right" bordercolor="#FFFFFF"><font size="2"  color="#FF9966"><b>Dynamic
      Profile ID</b><b>&#160;</b></font>
    <td width="80%" bordercolor="#C0C0C0"><font size="2" color="#FFFFFF">&#160;</font>
<font size="1" ><xsl:value-of   select="Conformance/@Dynamic"/></font>
     <xsl:if test="Differences/@Dynamic">
      <font size="1"  color="#0000FF" ><br>&#160;(<xsl:value-of select="Differences/@Dynamic"/>)</br></font>
    </xsl:if>

</td>
</td>
  </tr>


</xsl:template>


<xsl:template match="SegGroup">
<p align="left"/>
<table border="0" width="102%">
  <tr>
    <td width="14%"><font color="#FF9966" size="2"><b>Seg Group</b></font></td>
    <td width="70%"><font color="#FF9966" size="2"><b>Description</b></font></td>
    <td width="5%"><font color="#FF9966" size="2"><b>Opt</b></font></td>
    <td width="5%"><font color="#FF9966" size="2"><b>Rep</b></font></td>
    <td width="4%"><font color="#FF9966" size="2"><b>Min</b></font></td>
    <td width="5%"><font color="#FF9966" size="2"><b>Max</b></font></td>
  </tr>
  <tr>
    <td width="14%"><font size="2"><b><xsl:value-of  select="@Name"/></b></font></td>
    <td width="70%"><font size="2"><b><xsl:value-of  select="@Description"/></b></font></td>
    <td width="5%"><b><font size="2"><xsl:value-of   select="@Optionality"/></font></b></td>
    <td width="5%"><b><font size="2"><xsl:value-of   select="@Repeatable"/></font></b></td>
    <xsl:if test="@Repeatable[.='true']">
     <td width="4%"><b><font size="2"><xsl:value-of   select="@Min"/></font></b></td>
     <td width="5%"><b><font size="2"><xsl:value-of   select="@Max"/></font></b></td>
    </xsl:if>
  </tr>

  <xsl:if test="Differences">
    <xsl:apply-templates select="SegDifferences"/>
  </xsl:if>

</table>

 <xsl:if test = "Segment">
   <xsl:apply-templates select = "Segment"/>
 </xsl:if>
 <xsl:if test = "SegGroup">
   <xsl:apply-templates select = "SegGroup"/>
 </xsl:if>


</xsl:template>


<xsl:template match="Segment">
<p align="left"/>
<table border="0" width="102%">
  <tr>
    <td width="14%"><font color="#FF9966" size="2"><b>Segment</b></font></td>
    <td width="70%"><font color="#FF9966" size="2"><b>Description</b></font></td>
    <td width="5%"><font color="#FF9966" size="2"><b>Opt</b></font></td>
    <td width="5%"><font color="#FF9966" size="2"><b>Rep</b></font></td>
    <td width="4%"><font color="#FF9966" size="2"><b>Min</b></font></td>
    <td width="5%"><font color="#FF9966" size="2"><b>Max</b></font></td>
  </tr>
  <tr>
    <td width="14%"><font size="2"><b><xsl:value-of  select="@Name"/></b></font></td>
    <td width="70%"><font size="2"><b><xsl:value-of  select="@Description"/></b></font></td>
    <td width="5%"><b><font size="2"><xsl:value-of   select="@Optionality"/></font></b></td>
    <td width="5%"><b><font size="2"><xsl:value-of   select="@Repeatable"/></font></b></td>
    <xsl:if test="@Repeatable[.='Y']">
     <td width="4%"><b><font size="2"><xsl:value-of   select="@Min"/></font></b></td>
     <td width="5%"><b><font size="2"><xsl:value-of   select="@Max"/></font></b></td>
    </xsl:if>
  </tr>


  <xsl:if test="Differences">
    <xsl:apply-templates select="SegDifferences"/>
  </xsl:if>

</table>

<P align="center"><b><font size="2"><span style="background-color: #FFCC99">Fields</span></font></b></P>

<table border="1" width="100%" cellspacing="1">
  <tr bordercolor="#FFFFFF">
    <td width="30%"><font size="2"  color="#FF9966"><b>Name</b></font></td>
    <td width="5%"><font size="2" color="#FF9966"><b>Seq</b></font></td>
    <td width="5%"><font size="2" color="#FF9966"><b>DT</b></font></td>
    <td width="5%"><font size="2"  color="#FF9966"><b>Len</b></font></td>
    <td width="5%"><font size="2" color="#FF9966"><b>Opt</b></font></td>
    <td width="5%"><font size="2" color="#FF9966"><b>Rep</b></font></td>
    <td width="5%"><font size="2" color="#FF9966"><b>Min</b></font></td>
    <td width="5%"><font size="2" color="#FF9966"><b>Max</b></font></td>
    <td width="5%"><font size="2" color="#FF9966"><b>Tbl</b></font></td>
  </tr>

 <xsl:apply-templates select="Field"/>

 </table>

 <xsl:if test="SegGroup">
  <xsl:apply-templates select="SegGroup"/>
 </xsl:if>

</xsl:template>


<xsl:template match="Field">

  <tr>

    <td width="30%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" ><xsl:value-of   select="@Name"/></font></td>
    <td width="5%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" ><xsl:value-of   select="@Sequence"/></font></td>
    <td width="5%"><font size="2" color="#FFFFFF">&#160;</font><font size="2"><xsl:value-of   select="@Datatype"/></font></td>
    <td width="5%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" ><xsl:value-of   select="@Length"/></font></td>
    <td width="5%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" ><xsl:value-of   select="@Optionality"/></font></td>
    <td width="5%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" ><xsl:value-of   select="@Repeatable"/></font></td>
    <td width="5%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" ><xsl:value-of   select="@Min"/></font></td>
    <td width="5%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" ><xsl:value-of   select="@Max"/></font></td>
    <td width="5%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" ><xsl:value-of   select="@Table"/></font></td>
  </tr>
  
  <xsl:if test="./Differences">
    <xsl:apply-templates select="Differences"/>
  </xsl:if>


  <xsl:apply-templates select="Component"/>

</xsl:template>


<xsl:template match="Component">

  <tr>
    <td width="30%"><font size="2" color="#FFFFFF">&#160;&#160;&#160;&#160;</font><font size="2" color="#0000FF"><xsl:value-of   select="@Name"/></font></td>
    <td width="5%"><font size="2" color="#FFFFFF">&#160;</font><font size="2"  color="#0000FF" ><xsl:value-of   select="@Sequence"/></font></td>
    <td width="5%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" ><xsl:value-of   select="@Datatype"/></font></td>
    <td width="5%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" ><xsl:value-of   select="@Length"/></font></td>
    <td width="5%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" ><xsl:value-of   select="@Optionality"/></font></td>
    <td width="5%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" ><xsl:value-of   select="@Repeatable"/></font></td>
    <td width="5%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" ><xsl:value-of   select="@Min"/></font></td>
    <td width="5%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" ><xsl:value-of   select="@Max"/></font></td>
    <td width="5%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" ><xsl:value-of   select="@Table"/></font></td>

 </tr>

  <xsl:if test="Differences">
    <xsl:apply-templates select="Differences"/>
  </xsl:if>


 <xsl:apply-templates select="SubComponent"/>



</xsl:template>



<xsl:template match="SubComponent">

  <tr>
    <td width="30%"><font size="2" color="#FFFFFF">&#160;&#160;&#160;&#160;&#160;&#160;&#160;</font><font size="2" color="#FF00FF"><xsl:value-of   select="@Name"/></font></td>
    <td width="5%"><font size="2" color="#FFFFFF">&#160;</font><font size="2"  color="#FF00FF"><xsl:value-of   select="@Sequence"/></font></td>
    <td width="5%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" ><xsl:value-of   select="@Datatype"/></font></td>
    <td width="5%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" ><xsl:value-of   select="@Length"/></font></td>
    <td width="5%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" ><xsl:value-of   select="@Optionality"/></font></td>
    <td width="5%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" ><xsl:value-of   select="@Repeatable"/></font></td>
    <td width="5%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" ><xsl:value-of   select="@Min"/></font></td>
    <td width="5%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" ><xsl:value-of   select="@Max"/></font></td>
    <td width="5%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" ><xsl:value-of   select="@Table"/></font></td>

  </tr>


  <xsl:if test="Differences">
    <xsl:apply-templates select="Differences"/>
  </xsl:if>

</xsl:template>


<xsl:template match="DataValues">
<td width="10%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" >
  <xsl:value-of   select="@ConstantValue"/>
</font></td>
<td width="10%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" >
  <xsl:value-of   select="@ExValue"/>
</font></td>

</xsl:template>



<xsl:template match="Actor">
  <tr>
    <td width="28%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" ><xsl:value-of select="@Name"/></font></td>
    <td width="72%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" ><xsl:value-of select="@Value"/></font></td>
  </tr>
</xsl:template>

<xsl:template match="PreCondition">
  <tr>
    <td width="4%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" ><xsl:value-of select="@Name"/></font></td>
    <td width="96%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" ><xsl:value-of select="@Value"/></font></td>
  </tr>
</xsl:template>

 <xsl:template match="EventFlow">  <tr>
    <td width="4%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" ><xsl:value-of select="@Name"/></font></td>
    <td width="96%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" ><xsl:value-of select="@Value"/></font></td>
  </tr>
</xsl:template>


 <xsl:template match="PostCondition">  <tr>
    <td width="4%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" ><xsl:value-of select="@Name"/></font></td>
    <td width="96%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" ><xsl:value-of select="@Value"/></font></td>
  </tr>
</xsl:template>

 <xsl:template match="DerivativeEvent">  <tr>
    <td width="4%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" ><xsl:value-of select="@Name"/></font></td>
    <td width="96%"><font size="2" color="#FFFFFF">&#160;</font><font size="2" ><xsl:value-of select="@Value"/></font></td>
  </tr>
</xsl:template>


<xsl:template match="ImpNote">
 <tr bgcolor="#CCFFCC" Height="5"  bordercolorlight="#CCFFCC" bordercolordark="#CCFFCC">
  <td width="100%" colspan="11" bordercolorlight="#CCFFCC" bordercolordark="#CCFFCC"><font size="1">Implementation Note: </font></td>
 </tr>
 <xsl:for-each select="Paragraph">
  <tr bgcolor="#CCFFCC" Height="5" bordercolorlight="#CCFFCC" bordercolordark="#CCFFCC">
    <td width="100%" colspan="11" bordercolorlight="#CCFFCC" bordercolordark="#CCFFCC">
    <font color="#CCFFCC">&#160;&#160;</font><font size="1" color="#0000FF">
     <xsl:value-of select="."/> </font>
    </td>
  </tr>
</xsl:for-each>


</xsl:template>

<xsl:template match="Differences">
  <tr>
    <td width="30%"  bgcolor="#00FFFF"><font size="2" color="#FFFFFF">&#160;</font><font size="2" color="#008000"><b><xsl:value-of  select="@Name"/></b></font></td>
    <td width="5%" bgcolor="#00FFFF"><font size="2" color="#FFFFFF">&#160;</font><font size="2"  color="#008000"><b><xsl:value-of  select="@Sequence"/></b></font></td>
    <td width="5%" bgcolor="#00FFFF"><font size="2" color="#FFFFFF">&#160;</font><font size="2" color="#008000"><b><xsl:value-of   select="@Datatype"/></b></font></td>
    <td width="5%" bgcolor="#00FFFF"><font size="2" color="#FFFFFF">&#160;</font><font size="2" color="#008000"><b><xsl:value-of   select="@Length"/></b></font></td>
    <td width="5%" bgcolor="#00FFFF"><font size="2" color="#FFFFFF">&#160;</font><font size="2" color="#008000"><b><xsl:value-of   select="@Optionality"/></b></font></td>
    <td width="5%" bgcolor="#00FFFF"><font size="2" color="#FFFFFF">&#160;</font><font size="2" color="#008000"><b><xsl:value-of   select="@Repeatable"/></b></font></td>
    <td width="5%" bgcolor="#00FFFF"><font size="2" color="#FFFFFF">&#160;</font><font size="2" color="#008000"><b><xsl:value-of   select="@Max"/></b></font></td>
    <td width="5%" bgcolor="#00FFFF"><font size="2" color="#FFFFFF">&#160;</font><font size="2" color="#008000"><b><xsl:value-of   select="@Min"/></b></font></td>
    <td width="5%" bgcolor="#00FFFF"><font size="2" color="#FFFFFF">&#160;</font><font size="2" color="#008000"><b><xsl:value-of   select="@Table"/></b></font></td>
  </tr>

  <tr>
    <td width="100%" ><font size="2" color="#FFFFFF">&#160;</font></td>
  </tr>


</xsl:template>

<xsl:template match="SegDifferences">
  <tr>
    <td width="14%" bgcolor="#00FFFF"><font size="2"><b><xsl:value-of  select="@Name"/></b></font></td>
    <td width="70%" bgcolor="#00FFFF"><font size="2"><b><xsl:value-of  select="@Description"/></b></font></td>
    <td width="5%" bgcolor="#00FFFF"><b><font size="2"><xsl:value-of   select="@Optionality"/></font></b></td>
    <td width="5%" bgcolor="#00FFFF"><b><font size="2"><xsl:value-of   select="@Repeatable"/></font></b></td>
    <xsl:if test="@Repeatable[.='Y']">
     <td width="4%" bgcolor="#00FFFF"><b><font size="2"><xsl:value-of   select="@Max"/></font></b></td>
     <td width="5%" bgcolor="#00FFFF"><b><font size="2"><xsl:value-of   select="@Min"/></font></b></td>
    </xsl:if>
  </tr>


</xsl:template>





</xsl:stylesheet>




