<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<script>
$(document).ready(function() {
	//renderPageSelect();
	if($("#message_element > strong").html().length>0){
		 $('html, body').animate({ scrollTop: 0 }, 'slow'); 
		 $("#message_element").slideDown("slow"); 
		 setTimeout(function(){$("#message_element").slideUp("slow")},5000);
	 }
	changeReport();
});
function exportXLS(){
	var src = _path+"/export/init?id="+document.getElementById("reportSelect").value;
	//alert(src)
	
	var div = document.createElement("div");
    document.body.appendChild(div);
    div.innerHTML = "<iframe width='0' height='0' scrolling='no' frameborder='0' src='" + src + "'></iframe>";
}
function changeReport(){ 
	var reportSelectValue=$("#reportSelect").val();
	//alert(reportSelectValue);
	if(reportSelectValue=='1'){
		$("#reportElement").html("สรุปรายงานโทรภายในกลุ่มเดียวกันรวมทั้ง fixed line และโทรศัพท์เคลื่อนที่ โดยไม่สนใจเครือข่าย แต่สนใจเบอร์ของลูกค้า");
	}else if(reportSelectValue=='2'){
		$("#reportElement").html("สรุปรายงานโทรภายในเครือข่าย ทั้ง DTAC , Ais , True move, TOT เป็นต้น");
	}else if(reportSelectValue=='3'){
		$("#reportElement").html("สรุปรายงานการโทรภายในช่วงเวลา และนอกช่วงเวลา");
	}else{
		$("#reportElement").html("");
	} 
}
function goPrev(){
	if($("#pageNo").val()!='1'){
		var prev=parseInt($("#pageNo").val())-1;
		$("#pageNo").val(prev);
		doAction('search','0');
	}
}
function goNext(){
	var next=parseInt($("#pageNo").val());
	if(next<parseInt($("#pageCount").val())){
		next=next+1;
		$("#pageNo").val(next);
		doAction('search','0');
	}
} 
function goToPage(){ 
	$("#pageNo").val(document.getElementById("breakdownPageSelect").value);
	doAction('search','0');
}
function renderPageSelect(){
	 
	var pageStr="<select name=\"breakdownPageSelect\" id=\"breakdownPageSelect\" onchange=\"goToPage()\" style=\"width: 50px\">";
//	var pageCount=parseInt($("#pageCount").val());
	var pageCount=$("#pageCount").val();
	for(var i=1;i<=pageCount;i++){
		pageStr=pageStr+"<option value=\""+i+"\">"+i+"</option>";
	}
	pageStr=pageStr+"</select>"; 
	$("#pageElement").html(pageStr);
	document.getElementById("breakdownPageSelect").value=$("#pageNo").val();
}
function confirmDelete(mode,id){
	$( "#dialog-confirmDelete" ).dialog({
		/* height: 140, */
		modal: true,
		buttons: {
			"Yes": function() { 
				$( this ).dialog( "close" );
				doAction(mode,id);
			},
			"No": function() {
				$( this ).dialog( "close" );
				return false;
			}
		}
	});
}
function doSearch(mode,id){
	$("#pageNo").val("1");
	doAction(mode,id);
}
function doAction(mode,id){
	$("#mode").val(mode);
	if(mode=='deleteItems'){
		$("#pbdIdArray").val(id);
	}else if(mode!='search'){
		$("#pbdId").val(id);
	}else {
		$("#pbdId").val("0");
	}
	$.post("breakdown/search",$("#reportForm").serialize(), function(data) {
		  // alert(data);
		    appendContent(data);
		  // alert($("#_content").html());
		});
}
</script>
<div id="dialog-confirmDelete" title="Delete Breakdown" style="display: none;background: ('images/ui-bg_highlight-soft_75_cccccc_1x100.png') repeat-x scroll 50% 50% rgb(204, 204, 204)">
	Are you sure you want to delete Breakdown ?
</div>
  <div id="message_element" class="alert alert-${message_class}" style="display: none;padding:50 30 10 9"> 
    <button class="close" data-dismiss="alert"><span style="font-size: 12px">x</span></button>
    <strong>${message}</strong> 
  </div>
<fieldset style="font-family: sans-serif;padding-top:36px">
	         
           <!-- <legend  style="font-size: 13px">Criteria</legend> -->
           <!-- <div style="position:relative;right:-94%;">  </div> --> 
           
             
            <form:form id="reportForm" name="reportForm" modelAttribute="reportForm"  cssClass="well" cssStyle="border:2px solid #B3D2EE;background: #F9F9F9" action="" method="post">
          <%--   <form:hidden path="mode"/>
            <form:hidden path="pbdIdArray"/>
             <form:hidden path="pstBreakDown.pbdId" id="pbdId"/>
             <form:hidden path="paging.pageNo" id="pageNo"/>
              <form:hidden path="paging.pageSize" id="pageSize"/>
              <form:hidden path="pageCount"/> --%>
            <!-- <input id="mode" name="mode" type="hidden" value="">
            <input id="mcaId" name="missCandidate.mcaId" type="hidden" value="">
            <input id="mcaIdArray" name="mcaIdArray" type="hidden" value="">
            <input id="pageNo" name="paging.pageNo" type="hidden" value="1">
            <input id="pageSize" name="paging.pageSize" type="hidden" value="20"> 
            <input id="pageCount" name="pageCount" type="hidden" value="8">  -->
            <div align="left">
            <strong>Report</strong>
            </div>
            <div align="center" style="padding: 10px 60px">
            	<span style="font-size: 13px;">Select :</span> 
            	<span style="padding: 20px"> 
            	<select name="reportSelect" id="reportSelect" onchange="changeReport()"  style="width: 100px"> 
            		<option value="1">Report 1</option>
            		<option value="2">Report 2</option>
            		<option value="3">Report 3</option>
            		<option value="4">Report 4</option>
            		<option value="5">Report 5</option>
            	</select>
            	&nbsp;&nbsp;  
            	</span>  
            	<span  style="padding: 0px 0px 0px 0px;position: absolute;"><a  class="btn btn-primary" onclick="exportXLS()">&nbsp;Export XLS</a>
				</span>
            </div>
            <div id="reportElement"></div> 
			</form:form> 
	    					<%-- <table border="0" width="100%" style="font-size: 13px">
	    					<tbody><tr>
	    					<td align="left" width="50%">
	    					
	    					<!-- <a class="btn btn-primary" onclick="loadDynamicPage('breakdown/new')"><i class="icon-plus-sign icon-white"></i>&nbsp;Create</a>&nbsp; -->
	    					<!-- <a class="btn btn-danger" onclick="doDeleteItems()"><i class="icon-trash icon-white"></i>&nbsp;Delete</a> -->
	    					</td>
	    					<td align="right" width="50%">  
	    					<a onclick="goPrev()">Prev</a>&nbsp;|&nbsp;
	    					<span id="pageElement">
	    					<select name="breakdownPageSelect" id="breakdownPageSelect" onchange="goToPage()" style="width: 50px"><option value="1">1</option></select>
	    					</span>&nbsp;|&nbsp;<a onclick="goNext()">Next</a>
	    					<!-- &nbsp;<a class="btn btn-primary" onclick="doSearch('search','0')"><i class="icon-search icon-white"></i>&nbsp;Search</a> -->
	    					</td>
	    					</tr>
	    					</tbody></table>
		<table class="table table-striped table-bordered table-condensed" border="1" style="font-size: 12px">
        	<thead>
          		<tr> 
            		<th width="10%"><div class="th_class">รหัสรายการ</div></th>
            		<th width="82%"><div class="th_class">รายละเอียด</div></th> 
            		<th width="8%"><div class="th_class">Action</div></th> 
          		</tr>
        	</thead>
        	<tbody> 
        	<c:if test="${not empty pstBreakDowns}"> 
        	 <c:forEach items="${pstBreakDowns}" var="pstBreakDown" varStatus="loop"> 
          	<tr> 
            	<td>${pstBreakDown.pbdUid}</td>
            	<td>${pstBreakDown.pbdName}</td> 
            	<td style="text-align: center;"> 
            	 <i title="Edit" onclick="loadDynamicPage('breakdown/item/${pstBreakDown.pbdId}')" style="cursor: pointer;" class="icon-edit"></i>&nbsp;&nbsp;
            	 <i title="Delete" onclick="confirmDelete('delete','${pstBreakDown.pbdId}')" style="cursor: pointer;" class="icon-trash"></i>
            	</td>
          	</tr> 
          	</c:forEach>
          	</c:if>
          	<c:if test="${empty pstBreakDowns}"> 
          	<tr>
          		<td colspan="3" style="text-align: center;">&nbsp;Not Found&nbsp;</td>
          	</tr>
          </c:if>
        	</tbody>
      </table>  --%>
      
      </fieldset> 