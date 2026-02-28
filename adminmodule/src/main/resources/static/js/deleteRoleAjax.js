function deleteRoleAjaxRequest(id) {


    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/role/deleteRole/" + id,
        async: false,

        success: function (data) {

            if (data.includes("0")) {
                //	alert("0");
                $("#modalMsg").html("");
                $("#modalMsg").html("Success");
                $("#modalMsg2").html("");
                $("#modalMsg2").html("Deleted !");
                $('#success-alert-modal').modal('show');
                //
            } else if (data.includes("1")) {
                //	alert("1");
                $("#modalMsg").html("");
                $("#modalMsg").html("Mapping Found");
                $("#modalMsg2").html("");
                $("#modalMsg2").html("Not Deleted !");
                $('#success-alert-modal').modal('show');

            }
        },
        error: function (e) {
            console.log("ERROR : ", e);
            window.location.href = "/role/list";
        }
    });
}

function closeDeletePopup() {
    window.location.href = "/role/list";
}