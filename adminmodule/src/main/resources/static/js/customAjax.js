


$(document).ready(function() {
	
	if (window.location.href.indexOf("edit") > -1) {
		 
	var countryid = window.location.href.split("/").pop()
	console.log(countryid)
sendAjaxRequestForEdit(countryid)
	}


	//alert("error");
    $("#country").change(function() {
	 // alert("alert")
        sendAjaxRequest();
    });
    
     function sendAjaxRequest() {
	 var selectedCountry = $("#country").val();
	$.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/currency/getcountryCodeapi/"+selectedCountry,
                     
            success: function (data) {
	// alert(data.countryCode);
	 $("#country_code").empty();
	$(".selectpicker").selectpicker();
  
         
            var option = "<option value = " + data.countryCode+ ">" + data.countryCode +"</option>";
            $("#country_code").html(option);
    
       // $('.selectpicker').selectpicker('refresh');
         $('#country_code').selectpicker('refresh');
         $('#country_code').val(data.countryCode);
           $('#country_code').selectpicker('render');
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
            url: "/getcountrycodebyIdapi/"+selectedCountry,
                     
            success: function (data) {
	// alert(data.countryCode);
	// $("#country_code").empty();

	$("#country_code").selectpicker();
  
         
            var option = "<option value = " + data.countryCode+ " selected>"+ data.countryCode +"</option>";
            $("#country_code").html(option);
  $('select[id="country_code"]').val(data.countryCode);
        $('#country_code').selectpicker('refresh');
         $('#country_code').val(data.countryCode);
           $('#country_code').selectpicker('render');
          },
            error: function (e) {      
                console.log("ERROR : ", e);                
            }
        });
};
});
