$(document).ready(function(){

 $('select#bank').change(bankListMapping);



$(document).on("click", "#viewmakertransactionlink", function () {

      var refid = $(this).data('id');
      viewTransactionList(refid);

 });

 $(document).on("click", "#viewtransactionlink", function () {

      var refid = $(this).data('id');
      viewTransactionList(refid);

 });

 $('button#checkerapprove').on('click', function () {
           var refid = document.querySelector('#checkerreference_id').value;
           window.location = "/reconciliation/matchedentry/checkerapproved/"+refid;

     });

$('button#checkerreject').on('click', function () {

           var refid = document.querySelector('#checkerreference_id').value;

           window.location = "/reconciliation/matchedentry/checkerrejected/"+refid;

     });




});


function viewTransactionList(refId){

                     $.ajax({
                              type: "GET",
                              contentType: "application/json",
                              url: "/reconciliation/matchedentry/viewtransaction/"+refId,
                              cache: false,
                              timeout: 30000,
                              success: function (data) {

                               console.log(JSON.stringify(data));


                               $('#stmtvaluedate').html(data.valueDate);
                               $('#stmtaccounttype').html(data.transactionType);
                               $('#stmtcurrencyamount').html(data.amountReceived);
                               $('#stmttransid').html(data.refId);
                               $('#stmtbankrefid').html(data.bankRefNo);
                               $('#stmtaccname').html(data.senderName);
                               $('#stmtclass').html('Fund Confirmation');


                              $('#transuseracctype').html(data.userType);
                              $('#transemail').html(data.email);
                              $('#transsendername').html(data.senderName);
                              $('#transtransid').html(data.refId);
                              $('#transbookeddate').html(data.bookingDate);
                              $('#transbookedtime').html(data.time);
                              $('#transfromcurrency').html(data.fromCurrencyCode);
                              $('#transfromamt').html(data.transferAmount);
                              $('#transdisbCurrency').html(data.fromCurrencyCode);
                              $('#transdisbamt').html(data.amountReceived);
                              $('#transpaymode').html(data.paymentCode);
                              $('#transrecmode').html(data.receiveCode);
                              $('#transstatus').html(data.status);
                              $('#checkerreference_id').val(data.refId);

                              },
                              error: function (e) {

                              }

                });
 }





function bankListMapping(){

                    bankId = document.querySelector('#bank').value;
                     console.log(bankId);

                     $.ajax({
                              type: "GET",
                              contentType: "application/json",
                              url: "/fileupload/uploadfile/"+bankId,
                              cache: false,
                              timeout: 3000,
                              success: function (data) {

                               console.log(JSON.stringify(data));

                                 $('#bankaccno').find('option').remove().end();

                                 jQuery(data).each(function(i, item){

                                          $('#bankaccno')
                                           .append($('<option></option>')
                                           .attr("value", item.accountNo)
                                           .text(item.accountNo));

                                 })
                                 $('#bankaccno').selectpicker('refresh');



                              },
                                                  error: function (e) {
                                                            alert("Failure");
                                                  }

                });
 }
