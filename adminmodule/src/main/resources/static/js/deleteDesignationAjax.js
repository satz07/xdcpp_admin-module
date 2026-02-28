function deletecountrysAjaxRequest(id) {
	
	
	$.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/designation/deletedesignationapi/"+id,
                     
            success: function (data) {
	//alert(data)
	 // alert(data[0].currencyCode);
	//$("#country_code").empty();
   window.location.href="/designation/list";
	
          },
            error: function (e) {      
                console.log("ERROR : ", e);                
            }
        });
}