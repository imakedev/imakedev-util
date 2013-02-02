<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<style>
img[class="ui-datepicker-trigger"]{
	 cursor: pointer;
    position: relative;
    top: -2px;
    left: 2px;
} 
</style>
<script>
$(document).ready(function() {
	//renderPageSelect();
	
	if($("#message_element > strong").html().length>0){
		 $('html, body').animate({ scrollTop: 0 }, 'slow'); 
		 $("#message_element").slideDown("slow"); 
		 setTimeout(function(){$("#message_element").slideUp("slow")},5000);
	 }
	
	/*  $("#billCycle" ).datepicker({
			showOn: "button",
			buttonImage: _path+"resources/images/calendar.gif",
			buttonImageOnly: true,
			dateFormat:"dd/mm/yy" 
		});  */
	 listmaster('1');
	//changeReport();
});
function getCompany(){
	if($("#tgName").val()!='-1'){
		$.post("report/listCompany",$("#reportForm").serialize(), function(data) {
			if(data!=null){ 
				 str="<select name=\"tcId\" id=\"tcId\"  style=\"width: 300px;\">";
					// alert(data.length)
					if(data.length>0){
						for(var i=0;i<data.length;i++){
							str=str+"<option value=\""+data[i].tcId+"\">"+data[i].tcName+"</option>";
						}
					}else{
						str=str+"<option value=\"-1\">Select Company</option>";
					}
					str=str+"</select>"; 
				//	alert(str)
					$("#tcIdSelect").html(str);
					getBillCycle();
			} 
		 });
	};
}
function getBillCycle(){
	if($("#tcId").val()!='-1'){
		$.post("report/listBillCycle",$("#reportForm").serialize(), function(data) {
			if(data!=null){ 
				 str="<select name=\"billCycle\" id=\"billCycle\"  style=\"width: 130px;\">";
					// alert(data.length)
					if(data.length>0){
						for(var i=0;i<data.length;i++){
							str=str+"<option value=\""+data[i][0]+"\">"+data[i][1]+"</option>";
						}
					}else{
						str=str+"<option value=\"-1\">---</option>";
					}
					str=str+"</select>"; 
				//	alert(str)
					$("#billCycleSelect").html(str);
			} 
		 });
	};
}
function listmaster(type){
	$.ajax({
		  type: "get",
		  url:"report/listmaster?type="+type,
		  cache: false
		}).done(function( data ) {
			if(data!=null){
				//alert(data[0].tgName)
				var str="";
				if(type=='1'){// set Group
					 str="<select name=\"tgName\" id=\"tgName\"  onchange=\"getCompany()\" style=\"width: 300px;\">";
					// alert(data.length)
					if(data.length>0){
						for(var i=0;i<data.length;i++){
							str=str+"<option value=\""+data[i].tgName+"\">"+data[i].tgName+"</option>";
						}
					}else{
						str=str+"<option value=\"-1\">Select Group</option>";
					}
					str=str+"</select>"; 
				//	alert(str)
					$("#tgNameSelect").html(str);
					getCompany();
				}
			}
		});
	//}); 
//}
	/*  $.post("report/listmaster?type="+type,$("#reportForm").serialize(), function(data) {
		   alert(data);
		   // appendContent(data);
		  // alert($("#_content").html());
		}); */
}
function exportXLS(){
	//var src = _path+"/export/init?id="+document.getElementById("reportSelect").value;
	//| $("#tcId").val()=='-1') ||  $("#tgName").val()=='-1'
	//alert($("#billCycle").val().length);
	
	//if($("#billCycle").val().length==0 || $("#tcId").val()=='-1' ||  $("#tgName").val()=='-1'){
	if($("#billCycle").val()=='-1' || $("#tcId").val()=='-1' ||  $("#tgName").val()=='-1'){
		$( "#dialog-message" ).dialog({ 
			modal: true,
			buttons: {
				"Ok": function() { 
					$( this ).dialog( "close" ); 
					return false;
				} 
			 }
		});
		return false;
	} 
	/*
	$.post(_path+"/export/all2",$("#reportForm").serialize());//{
		    //alert(data);
		   // appendContent(data);
		   var div = document.createElement("div");
		    document.body.appendChild(div);
		    div.innerHTML = "<iframe width='0' height='0' scrolling='no' frameborder='0' src='" + data + "'></iframe>";  
		  // alert($("#_content").html());
		//});
		*/
	//alert($("#billCycle").val());
		//return false;
	//alert($("#tcId").val())
	//var billCycle=$("#billCycle").val().split("/");
	//21/01/2013 
	//@RequestMapping(value={"/all/{billCycle}/{tcId}"}
	//var src= _path+"/export/all/"+billCycle[0]+"_"+billCycle[1]+"_"+billCycle[2]+"/"+$("#tcId").val();
	var src= _path+"/export/all/"+$("#billCycle").val()+"/"+$("#tcId").val();
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
		} );
}
</script>
<div id="dialog-message" title="Message" style="display: none;background: ('images/ui-bg_highlight-soft_75_cccccc_1x100.png') repeat-x scroll 50% 50% rgb(204, 204, 204)">
	กรุณาเลือก Criteria ให้ครบครับ !!! 
</div>
  <div id="message_element" class="alert alert-${message_class}" style="display: none;padding:50 30 10 9"> 
    <button class="close" data-dismiss="alert"><span style="font-size: 12px">x</span></button>
    <strong>${message}</strong> 
  </div>
<fieldset style="font-family: sans-serif;padding-top:36px"> 
            <form:form id="reportForm" name="reportForm" modelAttribute="reportForm"  cssClass="well" cssStyle="border:2px solid #B3D2EE;background: #F9F9F9;height;100px" action="" method="post">
       
            <div align="left">
            <strong>Report</strong>
            </div>
            <table border="0"	style="width:650px;">
            	<tr valign="top">
            		<td>
            			<span style="font-size: 13px;">Group :</span> 
            			<span style="padding: 20px" id="tgNameSelect"> 
            			<form:select path="tgName" cssStyle="width: 300px;" > 
            			</form:select>  
            			</span>
            		</td>
            		<td>  
            			
            		</td>
            		<td> </td>
            	</tr>
            	<tr valign="top">
            		<td>
            				 <span style="font-size: 13px;">Company :</span> 
            	<span style="padding: 0px" id="tcIdSelect">   
            	<form:select path="tcId" cssStyle="width: 300px;" > 
            	<option value="-1">Select Company</option> 
            	</form:select>
            	&nbsp;&nbsp;  
            	</span>  
            		</td>
            		<td></td>
            		<td></td>
            	</tr>
            	<tr valign="top">
            		<td>
            	 <span style="font-size: 13px;">รอบบิล :</span> 
            	<span style="padding: 16px" id="billCycleSelect">   
            	<form:select path="billCycle" cssStyle="width: 300px;" > 
            	<option value="-1">Select รอบบิล</option> 
            	</form:select>
            	&nbsp;&nbsp;  
            	</span>  
            		</td>
            		<td></td>
            		<td></td>
            	</tr>
            	<tr valign="top">
            		<td>
            	<span  style="padding: 0px 0px 0px 0px;position: absolute;"><a  class="btn btn-primary" onclick="exportXLS()">&nbsp;Export XLS</a>
					</span>
            		</td>
            		<td></td>
            		<td></td>
            	</tr>
            	
            </table> 
            <div id="reportElement">&nbsp;</div> 
			</form:form>  
      
      </fieldset> 