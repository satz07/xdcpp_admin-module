const urlMap = {
		'Approve' : '/backoffice/disbursment/disbursementchecker/approve/',
		'Reject' : '/backoffice/disbursment/disbursementchecker/reject/',
		'Refund' : '/backoffice/disbursment/disbursementchecker/refund/'
};

function changeStatus(element, status, beneId){
		 
	if(status == "Approve"){
		var action = $(element).parents("tr").find("#actionStatus").html();
		const refId = $(element).parents("tr").find("#refId").text();
		if(action == "Disburse"){
			const convertedValue = $(element).parents("tr").find("#totalConvertedValue").html();
			const beneficiaryId = beneId;
			    
		    isAmountWithinUPILimit(beneficiaryId, convertedValue, refId, status);
	    }
	    else if(action == "Refund"){
	    	const apiUrl = urlMap['Refund']+refId; 
        	    
        	    $.ajax({
     	           type: "GET",
     	           contentType: "application/json",
     	           url: apiUrl,
     	           cache: false,
     	           timeout: 600000,
     	           success: function (data) {
     	           		alert("Transaction Refund Initiated::"+refId);
     	           		document.location.href = document.location.href; 
     	    			console.log("Transaction Refund Initiated",data,refId);
     	           },
     	           error: function (e) {
     			        alert("Error while transaction refund::"+refId);
     	    			console.log("Error: Transaction not refunded", e,refId);
     	           }
     	    });
	    }
	}
	else if(status == "Reject"){
		apiUrl = urlMap.Reject + refId;
		
		$.ajax({
	           type: "GET",
	           contentType: "application/json",
	           url: apiUrl,
	           cache: false,
	           timeout: 600000,
	           success: function (data) {
	           		alert("Transaction Approved::"+refId);
	    			console.log("Transaction approved",data,refId);
	    			document.location.href = document.location.href;
	           },
	           error: function (e) {
			        alert("Error while transaction approval::"+refId);
	    			console.log("Error: Transaction not not approved", e,refId);
	           }
	    });
	}
	
	
}

function isAmountWithinUPILimit(beneficiaryId, convertedValue, refId, status){
	 	
	$.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/backoffice/beneficiary/beneficiaryid/" + refId,
        cache: false,
        success: function(data) {
           console.log("success-isAmountWithinUPILimit");
           console.log("data - success::"+data);
           
           if(data!=null && data == 'UPI' && convertedValue > 95000){
        	   	   document.getElementById("refIdPopup").innerHTML = refId;
        		   $('#upilimitdialog').modal({show:true});        	          	   
           }else{        	   	
        	    const transferType = data;        	    
        	    const apiUrl = urlMap['Approve']+refId+"/"+transferType; 
        	    
        	    $.ajax({
     	           type: "GET",
     	           contentType: "application/json",
     	           url: apiUrl,
     	           cache: false,
     	           timeout: 600000,
     	           success: function (data) {
     	           		//alert("Transaction Approved::"+refId);
     	           		alert(data);
     	           		document.location.href = document.location.href;
     	    			console.log("Transaction approved",data,refId);
     	           },
     	           error: function (request, status, error) {
     			        alert("Error while transaction approval::"+refId +"<br>"+request.responseText);
     	    			console.log("Error: Transaction not approved", error,refId);
     	           }
     	    });
           }
        },
        error: function (e) {
        	console.log("error-isAmountWithinUPILimit");
        }
	});
}

function closeDialog() {
	$('#upilimitdialog').modal('hide');
}


