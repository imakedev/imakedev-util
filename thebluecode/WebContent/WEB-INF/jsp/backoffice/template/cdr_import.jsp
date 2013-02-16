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
	  
	    $("button[class='close'] > span").click(function() {
		//  $('fieldset').animate({ 'padding-top': '36px' }, 'slow');
		 // alert("x clicked")
		//   $('fieldset').css("padding-top","36px");
		/*   $('fieldset').animate({ 'padding-top': 0 }, 'slow'); 
		   $("#message_element").slideUp("slow",function()  {
			 // $('fieldset').animate({ 'padding-top': '36px' }, 'slow');
				  $('fieldset').animate({ 'padding-top': '36px' }, 'slow');
	       }); */ 
		   $("#message_element").slideUp("slow"); 
	       $('fieldset').animate({ 'padding-top': '36px' }, 'slow'); 
	       
       }); 
	  if($("#message_element > strong").html().length>0){
		 $('html, body').animate({ scrollTop: 0 }, 'slow'); 
		 $('fieldset').animate({ 'padding-top': 0 }, 'slow'); 
		 
		 $("#message_element").slideDown("slow"); 
		 setTimeout(function(){$("#message_element").slideUp("slow"); $('fieldset').animate({ 'padding-top': '36px' }, 'slow'); },5000);
	 }  
	  $("#info_element").click(function()  {
		 // $('fieldset').animate({ 'padding-top': '36px' }, 'slow');
		  $('#message_info').slideUp("show");
       });
	  new AjaxUpload('cdr_file', {
	       action: 'importCdr',
			onSubmit : function(file , ext){
	           // Allow only images. You should add security check on the server-side.
	             $('#message_info').slideUp("show");
	             /* if($("#billCycle" ).val().length==0){
	            	    alert('กรุณาเลือก รอบบิล') ;
	            		return false;		
	             } */
	            /*  alert($("#provider_id").val())
	             alert(getBillCycle()) */
				if (ext && /^(xls|XLS|xlsx|XLSX)$/.test(ext)){
					/* Setting data */
					this.setData({ 
						//'provider_id': $("#provider_id").val(),
						//'billCycle':getBillCycle()
					});					
				//$('#contact_photo').attr('src', _path+"resources/images/ui-anim_basic_16x16.gif");
				//$('#contact_photo').attr('src', _path+"resources/images/loading.gif");
				} else {					
					// extension is not allowed
					alert('Error: only xls are allowed') ;
					// cancel import
					return false;				
				}		
			},
			onComplete : function(file, response){
				//alert(file+","+response);
				var objAll = jQuery.parseJSON(response);
				//alert(obj.length)
				//alert(objAll);
				if(objAll!=null && objAll.length==2){
					var obj=objAll[0];
					var obj_number=objAll[1];
				 
					if(obj[0]=='0'){ // not success 
						 $('#message_info').slideUp("show");
						 setMessageInfo(obj[1]);
						 $('#message_info').slideDown("show");
					}else if(obj[0]=='1'){ // success
						 $("#message_element").attr("class","alert alert-success"); 
						 $("#message_element > strong").html(obj[1].length+" is successfully imported  with "+obj_number[0]+" new "+((obj_number[0]>1)?"records":"record")+" ,  "+obj_number[1]+" updated "+((obj_number[1]>1)?"records":"record")+"");
						// $("#message_element > strong").html("Import success "+obj[1].length+" "+((obj[1].length>1)?"records":"record")+" !!! ");  
						 $('fieldset').animate({ 'padding-top': 0 }, 'slow'); 
					    $("#message_element").slideDown("slow"); 
					    setTimeout(function(){$("#message_element").slideUp("slow"); $('fieldset').animate({ 'padding-top': '36px' }, 'slow'); },5000);
					}else{
						 $('#message_info').slideUp("show");
						 setErrorTemplat();
							$('#message_info').slideDown("show");
					}
			 
				}else{
					 $('#message_info').slideUp("show");
					setErrorMessage();
					$('#message_info').slideDown("show");
				}
			 /* 	var path_file='getFileAttached("getfile/template/${seriesForm.missSery.msId}/'+obj.hotlink+'")'; */
				//$('#cdr_file_attached').attr('onclick',path_file);
				//$('#cdr_file_attached').html(file);
				//$('#cdr_file_attached').attr('style','cursor: pointer;');	
			}		
		});
	/*  $("#billCycle" ).datepicker({
			showOn: "button",
			buttonImage: _path+"resources/images/calendar.gif",
			buttonImageOnly: true,
			dateFormat:"dd/mm/yy" 
		}); */ 
});
function clickImport(){
	
}
function getBillCycle(){
	var billCycle=$("#billCycle").val().split("/");
	//alert(billCycle);
	return billCycle[0]+"_"+ billCycle[1]+"_"+ billCycle[2];
}
function setErrorTemplat(){
	$("#message_info_error").html("not have Template !!! ");
	$("#message_table_error").html("");
}
function setErrorMessage(){
	$("#message_info_error").html("Import Error !!! \n( ตัวอย่างเช่น เลือก Template กับ Provider ไม่ตรงกัน  , จัดรูปแบบของ Template ไม่ถูกต้อง )");
	$("#message_table_error").html("");
}
function setMessageInfo(obj){
	$("#message_info_error").html("Import Error "+obj.length+" "+((obj.length>1)?"records":"record")+" !!! ");
	var str="<table class=\"table table-hover table-striped table-bordered table-condensed\" border=\"1\" style=\"font-size: 12px;\">"+
			"<thead>"+ 
			"<tr> "+ 
			"<th width=\"10%\"><div class=\"th_class\">#</div></th>"+ 
			"<th width=\"10%\"><div class=\"th_class\">Cell</div></th> "+ 
			"<th width=\"80%\"><div class=\"th_class\">Desc</div></th> "+ 
			"</tr>"+ 
			"</thead>"+ 
			"<tbody>   "; 
			//alert(obj.length)
	for(var i=0;i<obj.length;i++){
		str=str+"<tr>"+ 
				"<td style=\"text-align: right;\">"+(i+1)+"</td>"+ 
				"<td>"+obj[i][0]+"</td> "+ 
				"<td>"+obj[i][1]+"</td> "+ 
				"</tr>";
	}
	str=str+"</tbody>"+
			"</table>";
			//alert(str)
	$("#message_table_error").html(str);
	 
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
	$.post("breakdown/search",$("#cdrImportForm").serialize(), function(data) {
		  // alert(data);
		    appendContent(data);
		  // alert($("#_content").html());
		});
}
</script> 
  <%-- <div id="message_element" class="alert alert-${message_class}" style="display: none;padding-top:10px"> --%>
  <div id="message_element" class="alert alert-${message_class}" style="display: none;padding:50 30 10 9"> 
    <!-- <button class="close" data-dismiss="alert"><span style="font-size: 12px">x</span></button> -->
    <button class="close"><span style="font-size: 12px">x</span></button>
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
         <!--    <div align="center" style="padding: 10px 60px;border:1px solid #B3D2EE;background: #F9F9F9" > -->
            <div align="center" style="padding: 10px 60px;" >
           <!-- <table style="width: 750px" border="0">
            	 <tr>
            	<td width="100%">   -->
            	 <span style="font-size: 13px">Select File :</span> 
            	<span style="padding: 20px">
            	<a  class="btn" id="cdr_file" onclick="clickImport()"><i class="icon-file"></i>&nbsp;<span style="">Upload CDR</span></a> 
            	</span> 
            		<!-- </td> -->
            		<!-- <td width="35%">
            			<span style="font-size: 13px;">Select Provider:</span> 
            	<span style="padding: 20px">
            	<select id="provider_id" style="width: 109px;">
            		<option>TOT</option>
            		<option>True</option>
    			  	<option>True Move</option> 
    			  	<option>AIS</option>
    			  	<option>DTAC</option>
    			 </select>
            	</span> 
            		</td>
            		<td width="65%">
            		<span style="font-size: 13px">รอบบิล :</span>
            		<span style="padding: 20px">
            			<input type="text" name="billCycle" id="billCycle" readonly="readonly" style="height: 30px;width: 85px"/>	
            		</span>
            			<span style="font-size: 13px">Select File :</span> 
            	<span style="padding: 20px">
            	<a  class="btn" id="cdr_file" onclick="clickImport()"><i class="icon-file"></i>&nbsp;<span style="">Upload CDR</span></a> 
            	</span> 
            		</td> -->
            <!-- 	</tr>
            </table> -->
            
            </div>
         
			</form:form>  
			  <div id="message_info" align="center" style="padding: 10px 60px;border:2px solid #B3D2EE;background: #F9F9F9;display: none">
			    <!--  <button type="button" class="close" data-dismiss="alert">&times;</button> -->
			  	  <div class="alert alert-error" >
			  	   <button id="info_element" type="button" class="close" style="top: -12px;right: -87px;">&times;</button>
			  	  <span id="message_info_error">Import Error 2 records !!!</span></div> 
			  	<!--  <span class="alert alert-error">Import Error 2 records !!!</span> -->
			  	<div id="message_table_error">
			  	 <table class="table table-hover table-striped table-bordered table-condensed" border="1" style="font-size: 12px">
        	<thead>
          		<tr> 
            		<th width="10%"><div class="th_class">#</div></th>
            		<th width="90%"><div class="th_class">Cell</div></th> 
            	<!--	<th width="45%"><div class="th_class">หมายเลขปลายทาง</div></th>  
            		<th width="14%"><div class="th_class">Target Data</div></th>
            		<th width="14%"><div class="th_class">Target Score</div></th> -->
          		</tr>
        	</thead>
        	<tbody>   
          	<!-- <tr> 
            	<td style="text-align: right;">1</td>
            	<td>848810484</td> 
          	</tr> 
          	<tr> 
            	<td style="text-align: right;">2</td>
            	<td>848810484</td> 
          	</tr>  -->
  		 </tbody>
   </table>
   </div>
			  </div>
      </fieldset> 