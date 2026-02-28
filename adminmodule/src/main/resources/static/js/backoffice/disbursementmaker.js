const urlMap = {
		'Disburse' : '/backoffice/disbursment/disbursementmaker/disburse/',
		'Refund' : '/backoffice/disbursment/disbursementmaker/refund/'
};

$("#submit").click(function(){
    
    const status = $('#tranStatus').val();
    const refId = $('#refId').text();	
    const url = urlMap[status]+refId;  
    
    var form = $('#actionForm');
    
    $('#actionForm').attr('action', url);
    $('#actionForm').submit();
});

function changeStatus(element){
	var refId = $(element).parents("tr").find("#refId").html();
	var selectedMode = $(element).parents("tr").find("#tranStatus option:selected").val();
	var apiUrl = "";
	 
	if(selectedMode == "Disburse"){
		apiUrl = urlMap.Disburse + refId;
	}
	else if(selectedMode == "Refund"){
		apiUrl = urlMap.Refund + refId;
	}
	else{
		alert('Please select valid transaction status - Disburse / Refund');
		return;
	}
	
	$.ajax({
           type: "GET",
           contentType: "application/json",
           url: apiUrl,
           cache: false,
           timeout: 600000,
           success: function (data) {
	           	document.location.href = document.location.href;
    			console.log("Transaction status changed",data,refId);
           },
           error: function (e) {
    			console.log("Error: Transaction not moved to disbursement checker", e,refId);
           }
    });
}

function resetDropdown(element){
	$(element).parents("tr").find("#tranStatus option:selected").val("0");
}