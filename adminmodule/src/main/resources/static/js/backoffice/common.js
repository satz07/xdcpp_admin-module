const stagingUrlMap = {
		'Approve' : '/backoffice/staging/stagingCheckerCancelled/approve/',
		'Reject' : '/backoffice/staging/stagingCheckerCancelled/reject/',
};

function changeStatus(element, status){
	var refId = $(element).parents("tr").children()[1].innerText;
	var apiUrl = "";
	if(status == "Approve"){
		apiUrl = stagingUrlMap.Approve + refId;
	}
	else{
		apiUrl = stagingUrlMap.Reject + refId;
	}
	
	$.ajax({
	           type: "GET",
	           contentType: "application/json",
	           url: apiUrl,
	           cache: false,
	           timeout: 600000,
	           success: function (data) {
	           		alert("Transaction Cancelled from Staging Queue::"+refId);
	    			console.log("Transaction Cancelled from Staging Queue",data,refId);
	           },
	           error: function (e) {
			        alert("Error while transaction Cancellation from Staging Queue::"+refId);
	    			console.log("Error: Transaction not Cancelled from Staging Queue", e,refId);
	           }
	    });
	
}


