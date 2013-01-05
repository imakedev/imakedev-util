<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<script>
$(document).ready(function() {
	//renderPageSelect();
	  $("button[class='close'] > span").click(function() {
		 // $('fieldset').animate({ 'padding-top': '36px' }, 'slow');
		  $('fieldset').css("padding-top","36px");
   });
	  if($("#message_element > strong").html().length>0){
		 $('html, body').animate({ scrollTop: 0 }, 'slow'); 
		 $('fieldset').animate({ 'padding-top': 0 }, 'slow'); 
		 
		 $("#message_element").slideDown("slow"); 
		 setTimeout(function(){$("#message_element").slideUp("slow"); $('fieldset').animate({ 'padding-top': '36px' }, 'slow'); },5000);
	 }  
	new AjaxUpload('cdr_file', {
	       action: 'upload/cdr/1',
			onSubmit : function(file , ext){
	           // Allow only images. You should add security check on the server-side.
				if (ext && /^(xls|XLS)$/.test(ext)){
					/* Setting data */
					this.setData({
					});					
				//$('#contact_photo').attr('src', _path+"resources/images/ui-anim_basic_16x16.gif");
				//$('#contact_photo').attr('src', _path+"resources/images/loading.gif");
				} else {					
					// extension is not allowed
					alert('Error: only xls are allowed') ;
					// cancel upload
					return false;				
				}		
			},
			onComplete : function(file, response){
				//alert(file+","+response);
				var obj = jQuery.parseJSON(response);
				if(obj.hotlink!=null && obj.hotlink.length>0){
					 $("#message_element").attr("class","alert alert-success"); 
					 $("#message_element > strong").html("Upload success !!! ");  
					 $('fieldset').animate({ 'padding-top': 0 }, 'slow'); 
				    $("#message_element").slideDown("slow"); 
					 setTimeout(function(){$("#message_element").slideUp("slow"); $('fieldset').animate({ 'padding-top': '36px' }, 'slow'); },5000);
			 
				} 
			 /* 	var path_file='getFileAttached("getfile/template/${seriesForm.missSery.msId}/'+obj.hotlink+'")'; */
				//$('#cdr_file_attached').attr('onclick',path_file);
				//$('#cdr_file_attached').html(file);
				//$('#cdr_file_attached').attr('style','cursor: pointer;');	
			}		
		});
});
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
	$.post("breakdown/search",$("#cdrImportForm").serialize(), function(data) {
		  // alert(data);
		    appendContent(data);
		  // alert($("#_content").html());
		});
}
</script> 
  <%-- <div id="message_element" class="alert alert-${message_class}" style="display: none;padding-top:10px"> --%>
  <div id="message_element" class="alert alert-${message_class}" style="display: none;padding:50 30 10 9"> 
    <button class="close" data-dismiss="alert"><span style="font-size: 12px">x</span></button>
    <strong>${message}</strong> 
  </div>

<fieldset style="font-family: sans-serif;padding-top:36px">
	         
           <!-- <legend  style="font-size: 13px">Criteria</legend> -->
           <!-- <div style="position:relative;right:-94%;">  </div> --> 
           
             
            <form:form id="cdrImportForm" name="cdrImportForm" modelAttribute="cdrImportForm"  cssClass="well" cssStyle="border:2px solid #B3D2EE;background: #F9F9F9" action="" method="post">
          <%--   <form:hidden path="mode"/>
            <form:hidden path="pbdIdArray"/>
             <form:hidden path="pstBreakDown.pbdId" id="pbdId"/>
             <form:hidden path="paging.pageNo" id="pageNo"/>
              <form:hidden path="paging.pageSize" id="pageSize"/>
              <form:hidden path="pageCount"/> --%>
            <div align="left">
            <strong>Import CDR</strong>
            </div>
            <div align="center" style="padding: 10px 60px">
            	<span style="font-size: 13px;">Select File :</span> 
            	<span style="padding: 20px">
            	<a class="btn" id="cdr_file"><i class="icon-file"></i>&nbsp;<span style="">Upload CDR</span></a> 
            	</span>  
	    		<%-- <span style="font-size: 13px;">รายละเอียด</span> 
            	<span style="padding: 20px">
            	<form:input path="pstBreakDown.pbdName" cssStyle="height: 30;"/>
            	 
            	</span> --%>  
            </div>
			</form:form>  
      </fieldset> 