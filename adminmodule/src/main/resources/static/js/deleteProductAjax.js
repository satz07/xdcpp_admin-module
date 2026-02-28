function deletecountrysAjaxRequest(id) {
	
	
	$.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/product/deleteproductapi/"+id,
                     
            success: function (data) {
	//alert(data)
	 // alert(data[0].currencyCode);
	//$("#country_code").empty();
	//alert(data + data.includes("0"));
	// $('#success-alert-modal').modal('hide');
	if(data.includes("0")){
	//	alert("0");
		$("#modalMsg").html("");
		$("#modalMsg").html("Success");
		$("#modalMsg2").html("");
		$("#modalMsg2").html("Deleted !");
	 $('#success-alert-modal').modal('show');
	 //
	}else if(data.includes("1")){
	//	alert("1");
		$("#modalMsg").html("");
		$("#modalMsg").html("Mapping Found");
		$("#modalMsg2").html("");
		$("#modalMsg2").html("Not Deleted !");
	 $('#success-alert-modal').modal('show');
	// window.location.href="/product/list";
	}
  
	setTimeout(function(){
				window.location.href="/product/list";
	 }, 5000);
          },
            error: function (e) {      
                console.log("ERROR : ", e);      
                window.location.href="/product/list";
            }
        });
}