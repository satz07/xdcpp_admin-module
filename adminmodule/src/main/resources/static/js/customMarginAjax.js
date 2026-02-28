$(document).ready(function() {
	
	if (window.location.href.indexOf("edit") > -1) {
		 
	var countryid = window.location.href.split("/").pop()
	
	console.log(countryid)
sendAjaxRequestForEdit(countryid)
	}
	//alert("error");
    $("#sendCountryCode").change(function() {
	 // alert("alert")
        sendAjaxRequest();
    });
    
     function sendAjaxRequest() {
	 var selectedCountry = $("#sendCountryCode").val();
	$.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/margin/getMargincurrencyCode/"+selectedCountry,

            success: function (data) {
	//alert("DATArrtt:" +data)
	//  alert(data[0].currencyCode);
	/* $("#country_code").empty();*/
            	var option ="";
if(data==undefined || data==null)
{
	 option = "<option ></option>";
            $("#sendCurrencyCode").html(option);
}
	$(".selectpicker").selectpicker();
        data.forEach(function(item, i) {
           console.log(item.currencyCode);
             option += "<option value = " + item.id+ ">" + item.currencyCode +'-'+item.currencyName+"</option>";

      });
        $("#sendCurrencyCode").html(option);
        $('.selectpicker').selectpicker('refresh');
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
            url: "/getmargincurrencycodebyIdapi/"+selectedCountry,
                     
            success: function (data) {
	console.log("data"+data)
	// alert(data.countryCode);
	// $("#country_code").empty();
	var option1 = "";
	var option2 = "";
	var sendCode = "";
  var recieveCode ="";
if(data==undefined || data==null)
{
	  option1 = "<option ></option>";
	  option2 = "<option ></option>";
            $("#sendCurrencyCode").html(option1);
            
            $("#receiveCurrencyCode").html(option2);
}
	$("#sendCurrencyCode").selectpicker();
  $("#receiveCurrencyCode").selectpicker();
         
           data.forEach(function(item, i) {
        	
           console.log(item);
            option1 += "<option value = " + item.sendCurrencyId+ " selected>" + item.sendCurrencyCode +'-'+item.sendCurrencyDescription+"</option>";
             option2 += "<option value = " + item.receiveCurrencyId+ " selected>" + item.receiveCurrencyCode +'-'+item.receiveCurrencyDescription+"</option>";
              sendCode = item.sendCurrencyId;
             recieveCode = item.receiveCurrencyId;
             payCode = item.paymentCodeId;
             payrecieveCode = item.receiveCodeId;
      });
      console.log(option1);
      console.log(option2);
            $("#sendCurrencyCode").html(option1);
            $("#receiveCurrencyCode").html(option2);
 // $('select[id="sendCurrencyCode"]').val(data.currencyCode);
 //   $('select[id="receiveCurrencyCode"]').val(data.currencyCode);
        $('#sendCurrencyCode').selectpicker('refresh');
           $('#receiveCurrencyCode').selectpicker('refresh');
           $('#sendCurrencyCode').val(sendCode);
           $('#sendCurrencyCode').selectpicker('render');
           $('#receiveCurrencyCode').val(recieveCode);
           $('#receiveCurrencyCode').selectpicker('render');
           sendRecieveAjaxRecRequest(payrecieveCode);
           sendPaymentAjaxRecRequest(payCode);
          },
            error: function (e) {      
                console.log("ERROR : ", e);                
            }
        });
};

function sendPaymentAjaxRecRequest(pid) {
	 var selectedCountry = $("#sendCountryCode").val();
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
          url: "/api/fee/getFeepaymentCode/"+selectedCountry+"/"+userType,
                   
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
	$("#paymentCode").selectpicker();
      data.forEach(function(item, i) {
         console.log(item.paymentCodeMaster);
          option += "<option value = " + item.id+ ">" + item.paymentCodeMaster  + " - " +item.description+"</option>";
          
    });
      $("#paymentCode").html(option);
      $('#paymentCode').selectpicker('refresh');
     // alert(pid);
      $('#paymentCode').val(pid);
      $('#paymentCode').selectpicker('render');
        },
          error: function (e) {      
              console.log("ERROR : ", e);                
          }
      });
};


function sendRecieveAjaxRecRequest(rid) {
	 var selectedCountry = $("#receiveCountryCode").val();
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
          url: "/api/fee/getFeerecieveCode/"+selectedCountry+"/"+userType,
                   
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
	$("#receiveCode").selectpicker();
      data.forEach(function(item, i) {
         console.log(item.paymentCodeMaster);
          option += "<option value = " + item.id+ ">" + item.paymentCodeMaster  + " - " +item.description+"</option>";
          
    });
      $("#receiveCode").html(option);
      $('#receiveCode').selectpicker('refresh');
    //  alert(rid);
      $('#receiveCode').val(rid);
      $('#receiveCode').selectpicker('render');
      
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
	//  alert("alert")
        sendAjaxRecRequest();
    });
    
     function sendAjaxRecRequest() {
	 var selectedCountry = $("#receiveCountryCode").val();
	$.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/margin/getMargincurrencyCode/"+selectedCountry,
                     
            success: function (data) {
	/*alert("DATArrtt:" +data)
	  alert(data[0].currencyCode);
	 $("#country_code").empty();*/
            	var option ="";
if(data==undefined || data==null)
{
	  option = "<option ></option>";
            $("#receiveCurrencyCode").html(option);
}
	$(".selectpicker").selectpicker();
        data.forEach(function(item, i) {
           console.log(item.currencyCode);
             option += "<option value = " + item.id+ ">" + item.currencyCode +'-'+item.currencyName+"</option>";
            
      });
        $("#receiveCurrencyCode").html(option);
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
    $("#sendCountryCode").change(function() {
	 // alert("alert")
        sendPaymentAjaxRecRequest();
    });
    
     function sendPaymentAjaxRecRequest() {
	 var selectedCountry = $("#sendCountryCode").val();
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
            url: "/api/margin/getMarginpaymentCode/"+selectedCountry+"/"+userType,
                     
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
        sendRecieveMarginAjaxRecRequest();
    });
    
     function sendRecieveMarginAjaxRecRequest() {
	 var selectedCountry = $("#receiveCountryCode").val();
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
            url: "/api/margin/getMarginrecieveCode/"+selectedCountry+"/"+userType,
                     
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

function redBusiness() {
    $("#business").prop("checked", true);

    var selectedCountry = $("#sendCountryCode").val();
    var selectedRecCountry = $("#receiveCountryCode").val();

    if (selectedCountry != "" && selectedRecCountry != "") {

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
            url: "/api/margin/getMargincurrencyCode/"+selectedCountry,

            success: function (data) {
                //alert("DATArrtt:" +data)
                //  alert(data[0].currencyCode);
                /* $("#country_code").empty();*/
                var option ="";
                if(data==undefined || data==null)
                {
                    option = "<option ></option>";
                    $("#sendCurrencyCode").html(option);
                }
                $(".selectpicker").selectpicker();
                data.forEach(function(item, i) {
                    console.log(item.currencyCode);
                    option += "<option value = " + item.id+ ">" + item.currencyCode +'-'+item.currencyName+"</option>";

                });
                $("#sendCurrencyCode").html(option);
                $('.selectpicker').selectpicker('refresh');
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });

        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/margin/getMargincurrencyCode/"+selectedRecCountry,

            success: function (data) {
                /*alert("DATArrtt:" +data)
                  alert(data[0].currencyCode);
                 $("#country_code").empty();*/
                var option ="";
                if(data==undefined || data==null)
                {
                    option = "<option ></option>";
                    $("#receiveCurrencyCode").html(option);
                }
                $(".selectpicker").selectpicker();
                data.forEach(function(item, i) {
                    console.log(item.currencyCode);
                    option += "<option value = " + item.id+ ">" + item.currencyCode +'-'+item.currencyName+"</option>";

                });
                $("#receiveCurrencyCode").html(option);
                $('.selectpicker').selectpicker('refresh');
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });

        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/margin/getMarginpaymentCode/"+selectedCountry+"/"+userType,

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
                var marginPaymentCodeID=null;
                data.forEach(function(item, i) {
                    console.log(item.paymentCodeMaster);
                    option += "<option value = " + item.id+ ">" + item.paymentCodeMaster  + " - " +item.description+"</option>";
                    marginPaymentCodeID = item.id;
                });
                $("#paymentCode").html(option);
                $('.selectpicker').selectpicker('refresh');
                // $("#paymentCode").val(marginPaymentCodeID);
                // $("#paymentCode").selectpicker('render');
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });

        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/margin/getMarginrecieveCode/"+selectedRecCountry+"/"+userType,

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
                var marginReceiveCodeId=null;
                data.forEach(function(item, i) {
                    console.log(item.paymentCodeMaster);
                    option += "<option value = " + item.id+ ">" + item.paymentCodeMaster  + " - " +item.description+"</option>";
                    marginReceiveCodeId=item.id;
                });
                $("#receiveCode").html(option);
                $('.selectpicker').selectpicker('refresh');
                // $("#receiveCode").val(marginReceiveCodeId);
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

    var selectedCountry = $("#sendCountryCode").val();
    var selectedRecCountry = $("#receiveCountryCode").val();

    if (selectedCountry != "" && selectedRecCountry != "") {

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
            url: "/api/margin/getMargincurrencyCode/"+selectedCountry,

            success: function (data) {
                //alert("DATArrtt:" +data)
                //  alert(data[0].currencyCode);
                /* $("#country_code").empty();*/
                var option ="";
                if(data==undefined || data==null)
                {
                    option = "<option ></option>";
                    $("#sendCurrencyCode").html(option);
                }
                $(".selectpicker").selectpicker();
                data.forEach(function(item, i) {
                    console.log(item.currencyCode);
                    option += "<option value = " + item.id+ ">" + item.currencyCode +'-'+item.currencyName+"</option>";

                });
                $("#sendCurrencyCode").html(option);
                $('.selectpicker').selectpicker('refresh');
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });

        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/margin/getMargincurrencyCode/"+selectedRecCountry,

            success: function (data) {
                /*alert("DATArrtt:" +data)
                  alert(data[0].currencyCode);
                 $("#country_code").empty();*/
                var option ="";
                if(data==undefined || data==null)
                {
                    option = "<option ></option>";
                    $("#receiveCurrencyCode").html(option);
                }
                $(".selectpicker").selectpicker();
                data.forEach(function(item, i) {
                    console.log(item.currencyCode);
                    option += "<option value = " + item.id+ ">" + item.currencyCode +'-'+item.currencyName+"</option>";

                });
                $("#receiveCurrencyCode").html(option);
                $('.selectpicker').selectpicker('refresh');
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });

        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/margin/getMarginpaymentCode/"+selectedCountry+"/"+userType,

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
                var marginPaymentCodeID=null;
                data.forEach(function(item, i) {
                    console.log(item.paymentCodeMaster);
                    option += "<option value = " + item.id+ ">" + item.paymentCodeMaster  + " - " +item.description+"</option>";
                    marginPaymentCodeID=item.id;
                });
                $("#paymentCode").html(option);
                $('.selectpicker').selectpicker('refresh');
                // $("#paymentCode").val(marginPaymentCodeID);
                // $("#paymentCode").selectpicker('render');
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });


        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/margin/getMarginrecieveCode/"+selectedRecCountry+"/"+userType,

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
                var marginReceiveCodeId=null;
                data.forEach(function(item, i) {
                    console.log(item.paymentCodeMaster);
                    option += "<option value = " + item.id+ ">" + item.paymentCodeMaster  + " - " +item.description+"</option>";

                });
                $("#receiveCode").html(option);
                $('.selectpicker').selectpicker('refresh');
                // $("#receiveCode").val(marginReceiveCodeId);
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