function deletecountrysAjaxRequest(id) {
	
	
	$.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/deliverypartner/deleteapi/"+id,
                     
            success: function (data) {
	alert(data)
	 // alert(data[0].currencyCode);
	//$("#country_code").empty();
   window.location.href="/deliverypartner/list/";
	
          },
            error: function (e) {      
                console.log("ERROR : ", e);                
            }
        });
}
