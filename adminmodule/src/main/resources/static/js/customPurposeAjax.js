$(document).ready(function() {
	
	if (window.location.href.indexOf("edit") > -1) {
		 
	var countryid = window.location.href.split("/").pop()
	
	console.log(countryid);
sendAjaxRequestForEdit(countryid);


	}
	//alert("error");
    $("#countryCode").change(function() {
	 // alert("alert")
        sendAjaxRequest();
    });
    
     function sendAjaxRequest() {
	 var selectedCountry = $("#countryCode").val();
	$.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/purpose/getpurposecurrencyCode/"+selectedCountry,
                     
            success: function (data) {
	//alert("DATArrtt:" +data)
	  //alert(data[0].currencyCode);
	/* $("#country_code").empty();*/
            	var option="";
            	
if(data==undefined || data==null)
{
	  option = "<option ></option>";
            $("#currencyCode").html(option);
}
	$(".selectpicker").selectpicker();
        data.forEach(function(item, i) {
           console.log(item.currencyCode);
            option += "<option value = " + item.id+ ">" + item.currencyCode +'-'+item.currencyName+"</option>";
                         sendCode = item.id;
           
      });
        $("#currencyCode").html(option);
        //$('.selectpicker').selectpicker('refresh');
           $('#currencyCode').selectpicker('refresh');
          $('#currencyCode').val(sendCode);
           $('#currencyCode').selectpicker('render');
       
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
            url: "/getpurposecurrencycodebyIdapi/"+selectedCountry,
                     
            success: function (data) {
	console.log("data"+data)
	// alert(data.countryCode);
	// $("#country_code").empty();
	var option = "";
	var sendCode = "";
if(data==undefined || data==null)
{
	  option = "<option ></option>";
            $("#currencyCode").html(option);
}
	$("#countryCode").selectpicker();
  
         
           data.forEach(function(item, i) {
        	
           console.log(item.currencyCode);
            option += "<option value = " + item.id+ " selected>" + item.currencyCode +'-'+item.currencyName+"</option>";
             sendCode = item.id;
             payrecieveCode = item.receiveCodeId;
      });
                $("#currencyCode").html(option);
                $('select[id="currencyCode"]').val(data.currencyCode);
                $('#currencyCode').selectpicker('refresh');
                $('#currencyCode').val(sendCode);
                $('#currencyCode').selectpicker('render');
                sendRecieveAjaxRecRequest(payrecieveCode);
          },
            error: function (e) {      
                console.log("ERROR : ", e);                
            }
        });
};

	//alert("error");
    $("#countryCode").change(function() {
	 // alert("alert")
        sendRecieveAjaxRecRequestOnCountryCode();
    });
    
     function sendRecieveAjaxRecRequest(id) {
	 var selectedCountry = $("#countryCode").val();
         var userType;
         if(document.getElementById('business').checked) {
             //Male radio button is checked
             userType=$('#business').val();
         }else if(document.getElementById('personal').checked) {
             userType=$('#personal').val();
         }
	$.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/purpose/getpurposeRecieveCode/"+selectedCountry+"/"+userType,
                     
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
        var puposeReceiveCodeID=null;
        data.forEach(function(item, i) {
           console.log(item.paymentCodeMaster);
            option += "<option value = " + item.id+ ">" + item.paymentCodeMaster  + " - " +item.description+"</option>";
            puposeReceiveCodeID = item.id;
      });
        $("#receiveCode").html(option);
        $('.selectpicker').selectpicker('refresh');
        $("#receiveCode").val(id);
        $("#receiveCode").selectpicker('render');
          },
            error: function (e) {      
                console.log("ERROR : ", e);                
            }
        });
};

    function sendRecieveAjaxRecRequestOnCountryCode() {
        var selectedCountry = $("#countryCode").val();
        var userType;
        if(document.getElementById('business').checked) {
            //Male radio button is checked
            userType=$('#business').val();
        }else if(document.getElementById('personal').checked) {
            userType=$('#personal').val();
        }
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/purpose/getpurposeRecieveCode/"+selectedCountry+"/"+userType,

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
                var puposeReceiveCodeID=null;
                data.forEach(function(item, i) {
                    console.log(item.paymentCodeMaster);
                    option += "<option value = " + item.id+ ">" + item.paymentCodeMaster  + " - " +item.description+"</option>";
                    puposeReceiveCodeID = item.id;
                });
                $("#receiveCode").html(option);
                $('.selectpicker').selectpicker('refresh');
                // $("#receiveCode").val(id);
                // $("#receiveCode").selectpicker('render');
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    };

});

function redBusiness() {
    $("#business").prop("checked", true);

    var selectedCountry = $("#countryCode").val();
    if(selectedCountry != ""){

        var userType;
        if (document.getElementById('business').checked) {
            //Male radio button is checked
            userType = $('#business').val();
        } else if (document.getElementById('personal').checked) {
            userType = $('#personal').val();
        }

        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/purpose/getpurposecurrencyCode/"+selectedCountry,

            success: function (data) {
                //alert("DATArrtt:" +data)
                //alert(data[0].currencyCode);
                /* $("#country_code").empty();*/
                var option="";

                if(data==undefined || data==null)
                {
                    option = "<option ></option>";
                    $("#currencyCode").html(option);
                }
                $(".selectpicker").selectpicker();
                data.forEach(function(item, i) {
                    console.log(item.currencyCode);
                    option += "<option value = " + item.id+ ">" + item.currencyCode +'-'+item.currencyName+"</option>";
                    sendCode = item.id;

                });
                $("#currencyCode").html(option);
                //$('.selectpicker').selectpicker('refresh');
                $('#currencyCode').selectpicker('refresh');
                // $('#currencyCode').val(sendCode);
                // $('#currencyCode').selectpicker('render');

            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });

        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/purpose/getpurposeRecieveCode/"+selectedCountry+"/"+userType,

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
                var purposeReceiveCodeid=null;
                data.forEach(function(item, i) {
                    console.log(item.paymentCodeMaster);
                    option += "<option value = " + item.id+ ">" + item.paymentCodeMaster  + " - " +item.description+"</option>";
                    purposeReceiveCodeid=item.id;
                });
                $("#receiveCode").html(option);
                $('.selectpicker').selectpicker('refresh');
                // $("#receiveCode").val(purposeReceiveCodeid);
                // $("#receiveCode").selectpicker('render');
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });

    }else{
        $("#business").prop("checked", false);
        $("#personal").prop("checked", true);
        alert("please Select Send Country Code And Receive Country Code")
    }
}

function redPersonal(){
    $("#personal").prop("checked", true);

    var selectedCountry = $("#countryCode").val();
    if(selectedCountry != ""){

        var userType;
        if (document.getElementById('business').checked) {
            //Male radio button is checked
            userType = $('#business').val();
        } else if (document.getElementById('personal').checked) {
            userType = $('#personal').val();
        }

        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/purpose/getpurposecurrencyCode/"+selectedCountry,

            success: function (data) {
                //alert("DATArrtt:" +data)
                //alert(data[0].currencyCode);
                /* $("#country_code").empty();*/
                var option="";

                if(data==undefined || data==null)
                {
                    option = "<option ></option>";
                    $("#currencyCode").html(option);
                }
                $(".selectpicker").selectpicker();
                data.forEach(function(item, i) {
                    console.log(item.currencyCode);
                    option += "<option value = " + item.id+ ">" + item.currencyCode +'-'+item.currencyName+"</option>";
                    sendCode = item.id;

                });
                $("#currencyCode").html(option);
                //$('.selectpicker').selectpicker('refresh');
                $('#currencyCode').selectpicker('refresh');
                // $('#currencyCode').val(sendCode);
                // $('#currencyCode').selectpicker('render');

            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });

        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/purpose/getpurposeRecieveCode/"+selectedCountry+"/"+userType,

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
                var perposeReceiveCodeId=null;
                data.forEach(function(item, i) {
                    console.log(item.paymentCodeMaster);
                    option += "<option value = " + item.id+ ">" + item.paymentCodeMaster  + " - " +item.description+"</option>";
                    perposeReceiveCodeId = item.id;
                });
                $("#receiveCode").html(option);
                $('.selectpicker').selectpicker('refresh');
                // $("#receiveCode").val(perposeReceiveCodeId);
                // $("#receiveCode").selectpicker('render');
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });

    }else{
        $("#personal").prop("checked", false);
        $("#business").prop("checked", true);
        alert("please Select Send Country Code And Receive Country Code")
    }
}
