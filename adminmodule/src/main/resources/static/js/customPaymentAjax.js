$(document).ready(function() {
	
	if (window.location.href.indexOf("edit") > -1) {
		 
	var countryid = window.location.href.split("/").pop()
	
	console.log(countryid)
sendAjaxRequestForEdit(countryid)
	}

	//alert("error");
    $("#countries").change(function() {
	 // alert("alert")
        sendAjaxRequest();
    });
    
     function sendAjaxRequest() {
	 var selectedCountry = $("#countries").val();
	$.ajax({
            type: "GET",
            contentType: "application/json",
            url: "api/payment/getcurrencyCode/"+selectedCountry,
                     
    
            success: function (data) {
	console.log(JSON.stringify(data))
	/*alert("DATArrtt:" +data)
	
	  alert(data[0].currencyCode);
	$("#country_code").empty();*/
            	 var option = "";
if(data==undefined || data==null)
{
	  option = "<option ></option>";
            $("#currencies").html(option);
}
	$(".selectpicker").selectpicker();
	
        data.forEach(function(item, i) {
        	
           console.log(item.currencyCode);
            option += "<option value = " + item.id+ ">" + item.currencyCode +'-'+item.countryName+"</option>";
            sendCode = item.id;
            
      });
        console.log(option);
        $("#currencies").html(option);
        //$('.selectpicker').selectpicker('refresh');
                $('#currencies').selectpicker('refresh');
         $('#currencies').val(sendCode);
           $('#currencies').selectpicker('render');
          },
            error: function (e) {      
                console.log("ERROR : ", e);                
            }
        });
};

function sendAjaxRequestForEdit(selectedCountry) {
	 //var selectedCountry = $("#country").val();
	$.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/getpaymentcurrencycodebyIdapi/"+selectedCountry,
                     
            success: function (data) {
	console.log("data"+data)
	// alert(data.countryCode);
	// $("#country_code").empty();
	var option = "";
	var sendCode = "";
if(data==undefined || data==null)
{
	  option = "<option ></option>";
            $("#currencies").html(option);
}
	$("#country_code").selectpicker();
  
         
           data.forEach(function(item, i) {
        	
           console.log(item.currencyCode);
            option += "<option value = " + item.id+ " selected>" + item.currencyCode +'-'+item.currencyName+"</option>";
             sendCode = item.id;
      });
            $("#currencies").html(option);
  $('select[id="currencies"]').val(data.currencyCode);
  
        $('#currencies').selectpicker('refresh');
         $('#currencies').val(sendCode);
           $('#currencies').selectpicker('render');
          },
            error: function (e) {      
                console.log("ERROR : ", e);                
            }
        });
};
});


