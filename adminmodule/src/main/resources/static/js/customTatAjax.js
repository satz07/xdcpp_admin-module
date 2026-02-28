$(document).ready(function() {
	//alert(window.location.href.split("/").slice(-2)[0])
	//alert(window.location.href.indexOf("tat"));
	
	if (window.location.href.indexOf("tat") > -2) {
		 
		var countryid = window.location.href.split("/").slice(-2)[0]
		
		//alert(countryid)
	sendAjaxRequestForEdit(countryid);
		
		
		function sendAjaxRequestForEdit(selectedCountry) {
			 //var selectedCountry = $("#country").val();
			$.ajax({
		            type: "GET",
		            contentType: "application/json",
		            url: "/tat/gettatpaymentcodebyIdapi/"+selectedCountry,
		                     
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
		            option1 += "<option value = " + item.sendCurrencyId+ " selected>" + item.sendCurrencyCode +'-'+item.sendCurrencyName+"</option>";
		             option2 += "<option value = " + item.receiveCurrencyId+ " selected>" + item.receiveCurrencyCode +'-'+item.receiveCountryName+"</option>";
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
		}
	
	
	//alert("error");
    $("#originCCM").change(function() {
	 // alert("alert")
        sendRecieveTatAjaxRequest();
    });
    
     function sendRecieveTatAjaxRequest() {
	 var selectedCountry = $("#originCCM").val();
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
            url: "/tat/api/gettatpaymentCode/"+selectedCountry+"/"+userType,
                     
            success: function (data) {
	/*alert("DATArrtt:" +data)
	  alert(data[0].currencyCode);
	 $("#country_code").empty();*/
            	 var option="";
if(data==undefined || data==null)
{
	  option = "<option ></option>";
            $("#paymentMode").html(option);
}
	$(".selectpicker").selectpicker();
        data.forEach(function(item, i) {
           console.log(item.paymentCodeMaster);
            option += "<option value = " + item.id+ ">" + item.paymentCodeMaster  + " - " +item.description+"</option>";
            
      });
        $("#paymentMode").html(option);
        $('.selectpicker').selectpicker('refresh');
          },
            error: function (e) {      
                console.log("ERROR : ", e);                
            }
        });
};

function sendPaymentAjaxRecRequest(pid) {
	 var selectedCountry = $("#originCCM").val();
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
         url: "/tat/api/gettatpaymentCode/"+selectedCountry+"/"+userType,
                  
         success: function (data) {
	/*alert("DATArrtt:" +data)
	  alert(data[0].currencyCode);
	 $("#country_code").empty();*/
         	 var option="";
if(data==undefined || data==null)
{
	  option = "<option ></option>";
         $("#paymentMode").html(option);
}
	$("#paymentCode").selectpicker();
     data.forEach(function(item, i) {
        console.log(item.paymentCodeMaster);
         option += "<option value = " + item.id+ ">" + item.paymentCodeMaster  + " - " +item.description+"</option>";
         
   });
     $("#paymentMode").html(option);
     $('#paymentMode').selectpicker('refresh');
     //alert(pid);
     $('#paymentMode').val(pid);
     $('#paymentMode').selectpicker('render');
       },
         error: function (e) {      
             console.log("ERROR : ", e);                
         }
     });
};


function sendRecieveAjaxRecRequest(rid) {
	 var selectedCountry = $("#disburseCCM").val();
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
         url: "/tat/api/gettatrecieveCode/"+selectedCountry+"/"+userType,
                  
         success: function (data) {
	/*alert("DATArrtt:" +data)
	  alert(data[0].currencyCode);
	 $("#country_code").empty();*/
         	 var option="";
if(data==undefined || data==null)
{
	  option = "<option ></option>";
         $("#receiveMode").html(option);
}
	$("#receiveMode").selectpicker();
     data.forEach(function(item, i) {
        console.log(item.paymentCodeMaster);
         option += "<option value = " + item.id+ ">" + item.paymentCodeMaster  + " - " +item.description+"</option>";
         
   });
     $("#receiveMode").html(option);
     $('#receiveMode').selectpicker('refresh');
     //alert(rid);
     $('#receiveMode').val(rid);
     $('#receiveMode').selectpicker('render');
     
       },
         error: function (e) {      
             console.log("ERROR : ", e);                
         }
     });
};



	

});
$(document).ready(function() {
	
	
	
	//alert("error");
    $("#disburseCCM").change(function() {
	 // alert("alert")
        sendRecieveTatAjaxRequest();
    });
    
     function sendRecieveTatAjaxRequest() {
	 var selectedCountry = $("#disburseCCM").val();
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
            url: "/tat/api/gettatrecieveCode/"+selectedCountry+"/"+userType,
                     
            success: function (data) {
	/*alert("DATArrtt:" +data)
	  alert(data[0].currencyCode);
	 $("#country_code").empty();*/
            	 var option="";
if(data==undefined || data==null)
{
	  option = "<option ></option>";
            $("#receiveMode").html(option);
}
	$(".selectpicker").selectpicker();
        data.forEach(function(item, i) {
           console.log(item.paymentCodeMaster);
            option += "<option value = " + item.id+ ">" + item.paymentCodeMaster  + " - " +item.description+"</option>";
            
      });
        $("#receiveMode").html(option);
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
    var selectedCountry = $("#disburseCCM").val();
    var selectedCountryOr = $("#originCCM").val();
    if(selectedCountry != ""){
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
            url: "/tat/api/gettatpaymentCode/"+selectedCountryOr+"/"+userType,

            success: function (data) {
                /*alert("DATArrtt:" +data)
                  alert(data[0].currencyCode);
                 $("#country_code").empty();*/
                var option="";
                if(data==undefined || data==null)
                {
                    option = "<option ></option>";
                    $("#paymentMode").html(option);
                }
                $(".selectpicker").selectpicker();
                var tatPaymentMode = null;
                data.forEach(function(item, i) {
                    console.log(item.paymentCodeMaster);
                    option += "<option value = " + item.id+ ">" + item.paymentCodeMaster  + " - " +item.description+"</option>";
                    tatPaymentMode=item.id;
                });
                $("#paymentMode").html(option);
                $('.selectpicker').selectpicker('refresh');
                // $("#paymentCode").val(tatPaymentMode);
                // $("#paymentCode").selectpicker('render');
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });


        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/tat/api/gettatrecieveCode/"+selectedCountry+"/"+userType,

            success: function (data) {
                /*alert("DATArrtt:" +data)
                  alert(data[0].currencyCode);
                 $("#country_code").empty();*/
                var option="";
                if(data==undefined || data==null)
                {
                    option = "<option ></option>";
                    $("#receiveMode").html(option);
                }
                $(".selectpicker").selectpicker();
                var tatReceiveModeID=null;
                data.forEach(function(item, i) {
                    console.log(item.paymentCodeMaster);
                    option += "<option value = " + item.id+ ">" + item.paymentCodeMaster  + " - " +item.description+"</option>";
                    tatReceiveModeID = item.id;
                });
                $("#receiveMode").html(option);
                $('.selectpicker').selectpicker('refresh');
                // $("#receiveCode").val(tatReceiveModeID);
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

function redPersonal() {
    $("#personal").prop("checked", true);
    var selectedCountry = $("#disburseCCM").val();
    var selectedCountryOr = $("#originCCM").val();
    if(selectedCountry != ""){
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
            url: "/tat/api/gettatpaymentCode/"+selectedCountryOr+"/"+userType,

            success: function (data) {
                /*alert("DATArrtt:" +data)
                  alert(data[0].currencyCode);
                 $("#country_code").empty();*/
                var option="";
                if(data==undefined || data==null)
                {
                    option = "<option ></option>";
                    $("#paymentMode").html(option);
                }
                $(".selectpicker").selectpicker();
                var tatPaymentMode = null;
                data.forEach(function(item, i) {
                    console.log(item.paymentCodeMaster);
                    option += "<option value = " + item.id+ ">" + item.paymentCodeMaster  + " - " +item.description+"</option>";
                    tatPaymentMode = item.id;
                });
                $("#paymentMode").html(option);
                $('.selectpicker').selectpicker('refresh');
                // $("#paymentCode").val(tatPaymentMode);
                // $("#paymentCode").selectpicker('render');
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });


        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/tat/api/gettatrecieveCode/"+selectedCountry+"/"+userType,

            success: function (data) {
                /*alert("DATArrtt:" +data)
                  alert(data[0].currencyCode);
                 $("#country_code").empty();*/
                var option="";
                if(data==undefined || data==null)
                {
                    option = "<option ></option>";
                    $("#receiveMode").html(option);
                }
                $(".selectpicker").selectpicker();
                var tatReceiveModeId=null;
                data.forEach(function(item, i) {
                    console.log(item.paymentCodeMaster);
                    option += "<option value = " + item.id+ ">" + item.paymentCodeMaster  + " - " +item.description+"</option>";
                    tatReceiveModeId=item.id;
                });
                $("#receiveMode").html(option);
                $('.selectpicker').selectpicker('refresh');
                // $("#receiveCode").val(tatReceiveModeId);
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