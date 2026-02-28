$(document).ready(function() {
	
	//alert("error");
    $("#sendCountryCode").change(function() {
	//  alert("alert")
        sendAjaxRequest();
    });
    
     function sendAjaxRequest() {
	 var selectedCountry = $("#sendCountryCode").val();
	$.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/service/charge/getservicecurrencyCode/"+selectedCountry,
                     
            success: function (data) {
	/*alert("DATArrtt:" +data)
	  alert(data[0].currencyCode);
	 $("#country_code").empty();*/
            	var option="";
if(data==undefined || data==null)
{
	 option = "<option ></option>";
            $("#sendCurrencyCode").html(option);
}
	$(".selectpicker").selectpicker();
        data.forEach(function(item, i) {
           console.log(item.currencyCode);
            option += "<option value = "+ item.id+ ">" + item.currencyCode+ ">" + item.currencyCode +'-'+item.currencyName+"</option>";
             sendCode = item.id;
            
      });
        $("#sendCurrencyCode").html(option);
        //$('.selectpicker').selectpicker('refresh');
         $('#sendCurrencyCode').selectpicker('refresh');
	  $('#sendCurrencyCode').val(sendCode);
           $('#sendCurrencyCode').selectpicker('render');
          },
            error: function (e) {      
                console.log("ERROR : ", e);                
            }
        });
};
$(document).ready(function() {
	
	//alert("error");
    $("#receiveCountryCode").change(function() {
	//  alert("alert")
        sendAjaxRecRequest();
    });
    
     function sendAjaxRecRequest() {
	 var selectedCountry = $("#receiveCountryCode").val();
	$.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/service/charge/getservicecurrencyCode/"+selectedCountry,
                     
            success: function (data) {
	/*alert("DATArrtt:" +data)
	  alert(data[0].currencyCode);
	$("#country_code").empty();*/
            	var option = "";
if(data==undefined || data==null)
{
	  option = "<option ></option>";
            $("#receiveCurrencyCode").html(option);
}
	$(".selectpicker").selectpicker();
        data.forEach(function(item, i) {
           console.log(item.currencyCode);
            option += "<option value = "+ item.id+ ">" + item.currencyCode+ ">" + item.currencyCode +'-'+item.currencyName+"</option>";
            recieveCode = item.id;
           
      });
        $("#receiveCurrencyCode").html(option);
        //$('.selectpicker').selectpicker('refresh');
         $('#receiveCurrencyCode').selectpicker('refresh');
  $('#receiveCurrencyCode').val(recieveCode);
   $('#receiveCurrencyCode').selectpicker('render');
          },
            error: function (e) {      
                console.log("ERROR : ", e);                
            }
        });
};
});


});
$(document).ready(function() {
	
	
	//alert("error");
    $("#sendCountryCode").change(function() {
	 // alert("alert")
        sendPaymentAjaxRecRequest();
    });
    
     function sendPaymentAjaxRecRequest() {
	 var selectedCountry = $("#sendCountryCode").val();
	$.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/service/charge/getServicepaymentCode/"+selectedCountry,
                     
            success: function (data) {
	/*alert("DATArrtt:" +data)
	  alert(data[0].currencyCode);
	 $("#country_code").empty();*/
            	 var option="";
if(data==undefined || data==null)
{
	  option = "<option ></option>";
            $("#paymentCode").html(option);
}
	$(".selectpicker").selectpicker();
        data.forEach(function(item, i) {
           console.log(item.paymentCodeMaster);
            option += "<option value = " + item.id+ ">" + item.paymentCodeMaster  + " - " +item.description+"</option>";
            
      });
        $("#paymentCode").html(option);
        $('.selectpicker').selectpicker('refresh');
          },
            error: function (e) {      
                console.log("ERROR : ", e);                
            }
        });
};

});
$(document).ready(function() {
	
	
	//alert("error");
    $("#receiveCountryCode").change(function() {
	 // alert("alert")
        sendRecievetServiceAjaxRecRequest();
    });
    
     function sendRecievetServiceAjaxRecRequest() {
	 var selectedCountry = $("#receiveCountryCode").val();
	$.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/service/charge/getServiceRecieveCode/"+selectedCountry,
                     
            success: function (data) {
	/*alert("DATArrtt:" +data)
	  alert(data[0].currencyCode);
	 $("#country_code").empty();*/
            	 var option="";
if(data==undefined || data==null)
{
	  option = "<option ></option>";
            $("#receiveCode").html(option);
}
	$(".selectpicker").selectpicker();
        data.forEach(function(item, i) {
           console.log(item.paymentCodeMaster);
            option += "<option value = " + item.id+ ">" + item.paymentCodeMaster  + " - " +item.description+"</option>";
            
      });
        $("#receiveCode").html(option);
        $('.selectpicker').selectpicker('refresh');
          },
            error: function (e) {      
                console.log("ERROR : ", e);                
            }
        });
};

});

