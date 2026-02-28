function deletePaymentAjaxRequest(id) {
	
	
	$.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/payment/deletepaymentapi/"+id,
                     
            success: function (data) {
	//alert(data)
	 // alert(data[0].currencyCode);
	//$("#country_code").empty();
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
	setTimeout(function(){ window.location.href="/payment/list"; }, 5000);
	
          },
            error: function (e) {      
                console.log("ERROR : ", e);                
            }
        });
}
function deleteRecieveAjaxRequest(id) {
	
	
	$.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/receive/deleterecieveapi/"+id,
                     
            success: function (data) {
	//alert(data)
	 // alert(data[0].currencyCode);
	//$("#country_code").empty();
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
	setTimeout(function(){  window.location.href="/receive/list"; }, 5000);
  
	
          },
            error: function (e) {      
                console.log("ERROR : ", e);                
            }
        });
}