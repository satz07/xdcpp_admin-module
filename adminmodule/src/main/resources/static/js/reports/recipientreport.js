$(document).ready(function(){
	var currentPageNumber = document.getElementById("pageNum").value;
	console.log("currentPageNumber::"+currentPageNumber);
	
	var totalPage = document.getElementById("totalPage").value;
	console.log("totalPage::"+totalPage);
	
	if((currentPageNumber == 0 && totalPage == 0) || (currentPageNumber == totalPage)){
		$("#loadmore").prop("disabled", true);
	}
});

function OnSearchButtonClick(){
	$("#pageNum").val("0");
	var currentPageNumber = document.getElementById("pageNum").value;
	console.log("Page Number on search:::"+currentPageNumber);
	
	$('#search').submit();
}

