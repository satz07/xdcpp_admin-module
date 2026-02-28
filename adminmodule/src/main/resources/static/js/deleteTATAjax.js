function deleteTATAjaxRequest(id) {
	
	
	$.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/tat/api/deleteapi/"+id,
                     
            success: function (data) {
	//alert(data)
	 // alert(data[0].currencyCode);
	//$("#country_code").empty();
 setTimeout(function(){  window.location.href="/tat/list/"; }, 500);
	
          },
            error: function (e) {      
                console.log("ERROR : ", e);                
            }
        });
}
