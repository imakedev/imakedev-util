<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<script>
$(document).ready(function() {
	renderPageSelect();
	if($("#message_element > strong").html().length>0){
		 $('html, body').animate({ scrollTop: 0 }, 'slow'); 
		 $("#message_element").slideDown("slow"); 
		 setTimeout(function(){$("#message_element").slideUp("slow")},5000);
	 }
});
function goBackEmployee(){
	 
	  $.ajax({
		  type: "get",
		  url: "employeeWorkMapping/init",
		  cache: false
		 // data: { name: "John", location: "Boston" }
		}).done(function( data ) {
			if(data!=null){
				 appendContent(data);
				// $("#tabs-3").html(data);
			  }
		});
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
	$("#pageNo").val(document.getElementById("employeePageSelect").value);
	doAction('search','0');
}
function renderPageSelect(){
	 
	var pageStr="<select name=\"employeePageSelect\" id=\"employeePageSelect\" onchange=\"goToPage()\" style=\"width: 50px\">";
//	var pageCount=parseInt($("#pageCount").val());
	var pageCount=$("#pageCount").val();
	for(var i=1;i<=pageCount;i++){
		pageStr=pageStr+"<option value=\""+i+"\">"+i+"</option>";
	}
	pageStr=pageStr+"</select>"; 
	$("#pageElement").html(pageStr);
	document.getElementById("employeePageSelect").value=$("#pageNo").val();
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
		$("#peIdArray").val(id);
	}else if(mode!='search'){
		$("#peId").val(id);
	}else {
		$("#peId").val("0");
	}
	$.post("employee/search",$("#employeeForm").serialize(), function(data) {
		  // alert(data);
		    appendContent(data);
		  // alert($("#_content").html());
		});
}
</script>
<div id="dialog-confirmDelete" title="Delete Employee Status" style="display: none;background: ('images/ui-bg_highlight-soft_75_cccccc_1x100.png') repeat-x scroll 50% 50% rgb(204, 204, 204)">
	Are you sure you want to delete Employee Status ?
</div>
  <div id="message_element" class="alert alert-${message_class}" style="display: none;padding-top:10px">
    <button class="close" data-dismiss="alert"><span style="font-size: 12px">x</span></button>
    <strong>${message}</strong> 
  </div>
<fieldset style="font-family: sans-serif;padding-top:5px">
	         
           <!-- <legend  style="font-size: 13px">Criteria</legend> -->
           <!-- <div style="position:relative;right:-94%;">  </div> --> 
          
            <form:form id="employeeForm" name="employeeForm" modelAttribute="employeeForm"  cssClass="well" cssStyle="border:2px solid #B3D2EE;background: #F9F9F9" action="" method="post">
            <form:hidden path="mode"/>
            <form:hidden path="peIdArray"/>
             <form:hidden path="pstEmployee.peId" id="peId"/>
             <form:hidden path="paging.pageNo" id="pageNo"/>
              <form:hidden path="paging.pageSize" id="pageSize"/>
              <form:hidden path="pageCount"/>
            <div align="left">
            <strong>Employee</strong>
            </div> 
            <div align="center" style="padding: 10px 10px">
            	<span style="font-size: 13px;">รหัส</span> 
            	<span style="padding: 20px">
            	<form:input path="pstEmployee.peUid" cssStyle="height: 30;width:80px"/>
            	<!-- <input type="text" style="height: 30;width:80px">  -->
            	</span>  
	    		<span style="font-size: 13px;">ชื่อ</span> 
            	<span style="padding: 20px">
            	<form:input path="pstEmployee.peFirstName" cssStyle="height: 30;width:100px"/>
            	<!-- <input type="text" style="height: 30;">  -->
            	</span> 
            	<span style="font-size: 13px;">นามสกุล</span> 
            	<span style="padding: 20px">
            	<form:input path="pstEmployee.peLastName" cssStyle="height: 30;width:100px"/>
            	<!-- <input type="text" style="height: 30;">  -->
            	</span>  
            	<span style="font-size: 13px;">ตำแหน่ง</span> 
            	<span style="padding: 20px"> 
            		<form:select path="ppId">
	    						      <form:option value="-1">-- Select Position --</form:option>
	    					      	  <form:options itemValue="ppId" itemLabel="ppName" items="${positions}"/>
	    					     
	    			 </form:select>	
            	</span>  
            </div> 
			</form:form> 
			
	    					<table border="0" width="100%" style="font-size: 13px">
	    					<tbody><tr>
	    					<td align="left" width="50%">
	    					
	    					<a class="btn btn-primary" onclick="loadDynamicPage('employee/new')"><i class="icon-plus-sign icon-white"></i>&nbsp;Create</a>&nbsp;
	    					<!-- <a class="btn btn-danger" onclick="doDeleteItems()"><i class="icon-trash icon-white"></i>&nbsp;Delete</a> -->
	    					</td>
	    					<td align="right" width="50%">  
	    					<a onclick="goPrev()">Prev</a>&nbsp;|&nbsp;
	    					<span id="pageElement">
	    					<select name="employeePageSelect" id="employeePageSelect" onchange="goToPage()" style="width: 50px"><option value="1">1</option></select>
	    					</span>&nbsp;|&nbsp;<a onclick="goNext()">Next</a>&nbsp;<a class="btn btn-primary" onclick="doSearch('search','0')"><i class="icon-search icon-white"></i>&nbsp;Search</a></td>
	    					</tr>
	    					</tbody></table>
		<table class="table table-striped table-bordered table-condensed" border="1" style="font-size: 12px">
        	<thead>
          		<tr> 
            		<th width="10%"><div class="th_class">รหัส</div></th>
            		<th width="52%"><div class="th_class">ชื่อ-นามสกุล</div></th>
            		<th width="15%"><div class="th_class">ตำแหน่ง</div></th>
            		<th width="15%"><div class="th_class">ค่าแรง</div></th>
            		<th width="8%"><div class="th_class">Action</div></th> 
          		</tr>
        	</thead>
        	<tbody>  
        	<c:if test="${not empty pstEmployees}"> 
        	 <c:forEach items="${pstEmployees}" var="pstEmployee" varStatus="loop"> 
          	<tr> 
            	<td>${pstEmployee.peUid}</td>
            	<td>${pstEmployee.peFirstName}&nbsp;${pstEmployee.peLastName}</td>
            	<td>${pstEmployee.pstPosition.ppName}</td>
            	<td>${pstEmployee.peWage}</td>    
            	<td style="text-align: center;"> 
            	 <i title="Edit" onclick="loadDynamicPage('employee/item/${pstEmployee.peId}')" style="cursor: pointer;" class="icon-edit"></i>&nbsp;&nbsp;
            	 <i title="Delete" onclick="confirmDelete('delete','${pstEmployee.peId}')" style="cursor: pointer;" class="icon-trash"></i>
            	</td>
          	</tr> 
          	</c:forEach>
          	</c:if>
          	<c:if test="${empty pstEmployees}"> 
          	<tr>
          		<td colspan="5" style="text-align: center;">&nbsp;Not Found&nbsp;</td>
          	</tr>
          </c:if>
        	</tbody>
      </table> 
      <div align="left">
			<a class="btn btn-info"  onclick="goBackEmployee()"><i class="icon-chevron-left icon-white"></i>&nbsp;<span style="color: white;font-weight: bold;">Back</span></a>
    			
	 </div>
      </fieldset> 