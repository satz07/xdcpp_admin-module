let telInput = document.querySelector('#mob_num');
telInput ? telInput.addEventListener('keyup', checkNumb) : '';
let numDecimal = document.querySelectorAll('.numbdecimal');
numDecimal ? numDecimal.forEach(nu => nu.addEventListener('keyup', NumberDecimal)) : '' ;
let onlyChar = document.querySelectorAll('.onlychar');
onlyChar ? onlyChar.forEach(element => element.addEventListener('keyup', OnlyCharValues)) : '';
let accrelatedfileup = document.querySelector('#subtype');
accrelatedfileup = accrelatedfileup ? accrelatedfileup.addEventListener('change', Changefiletxt) : '';


function Changefiletxt(){
    this.value == 'Account Related' ? document.querySelector('.filuptxt').innerText = "User Id" : document.querySelector('.filuptxt').innerText = "Reference No."
}

// Add more group and role for mapping
let mappChild =  document.querySelector('.mapping');
let addMoreButtun = document.querySelector('.addMoreMapping');
let clonecount = 1;
addMoreButtun = addMoreButtun ? addMoreButtun.addEventListener('click', CloneMappElements) : '';
let deletemapping = document.querySelector('.mapparent');
deletemapping = deletemapping ? deletemapping.addEventListener('click', delMapping) : '';
let deletelist = document.querySelector('.viewtable');
deletelist = deletelist ? deletelist.addEventListener('click', delMapping) : '';
let selectalltable = document.querySelector('#selectall');
selectalltable = selectalltable ? selectalltable.addEventListener('click', selectAllCheckbox) : '';
let complainceuserstatus = document.querySelector('.compliancetable');
complainceuserstatus = complainceuserstatus ? complainceuserstatus.addEventListener('click', runfun) : '';
function runfun(){
    function delegate(el, evt, sel, handler) {
        el.addEventListener(evt, function(event) {
            var t = event.target;
            while (t && t !== this) {
                if (t.matches(sel)) {
                    handler.call(t, event);
                }
                t = t.parentNode;
            }
        });
    }
    
    delegate(document, "click", ".status", function(event) {
        event.target.innerText == "User Action" ?
            event.target.parentElement.parentElement.parentElement.parentElement.children[1].children[0].classList.remove('d-none') : 
            event.target.parentElement.parentElement.parentElement.parentElement.children[1].children[0].classList.add('d-none');
    
    });
}


let checkBox = document.querySelectorAll('.checktog');
checkBox = checkBox ? checkBox.forEach(ele => ele.addEventListener('click', SelectVieww)) : '';

function complainceUserAction(e){
    console.log('ok');
    e.target.innerText == "User Action" ? 
        console.log(e.target) : '' ;
        // e.target.parentElement.parentElement.parentElement.parentElement.children[8].children[0].classList.remove('d-none') :
        // e.target.parentElement.parentElement.parentElement.parentElement.children[8].children[0].classList.add('d-none');
    // dataTableAdjust();
}

function SelectVieww(){
    this.checked ? document.getElementById('view').checked = true : '';
}
function selectAllCheckbox(){
    this.checked ? document.querySelectorAll('input[type=checkbox]').forEach(check => check.checked = true) : document.querySelectorAll('input[type=checkbox]').forEach(check => check.checked = false)
}

function checkNumb(){
    if (/\D/g.test(this.value)) {
        // Filter non-digits from input value.
        this.value = this.value.replace(/\D/g,'');
    }
}
function NumberDecimal(){
    this.value = this.value.replace(/[^0-9.%]/g, '').replace(/(\.\%.*)\./g, '$1');
}
function OnlyCharValues(){
    
    if (/[^a-zA-Z]+/g.test(this.value)) {
        // Filter non-digits from input value.
        this.value = this.value.replace(/[^a-zA-Z]+/g,'');
    }
}
function CloneMappElements(){
    let clone = mappChild.cloneNode(true);
    $(clone).removeClass('mapping');
    $(clone).find('.bootstrap-select').replaceWith(function() { return $('select', this); });
    $('select').find('.bs-title-option').remove();
    $(clone).find('select').removeClass('selectpicker');
    document.querySelectorAll('.mapchild .bs-title-option').forEach(remopt => remopt.remove());
    document.querySelectorAll('.dropdown-menu.inner.show li .text').forEach(function(e){ if(e.textContent == '') {e.remove()}});
    $(clone).find('select').selectpicker('deselectAll');
    let  divEleClassName = ('mapclass'+clonecount);
    const divEle = document.createElement("div");
    divEle.className = divEleClassName;
    clonecount++;
    divEle.append(clone);
    document.querySelector('.mapchild').appendChild(divEle);
}
function delMapping(e){
    
    if( e.target.className == 'mdi mdi-delete'){
        $('#confirm-delete').modal('show')
        document.querySelector('.confirm-deletemap').addEventListener('click', function(){
            e.target.parentElement.parentElement.parentElement.parentElement.parentElement.remove();
           // $('#success-alert-modal').modal('show')
        });
    }else if( e.target.className == 'mdi mdi-delete dellist'){
        $('#confirm-delete').modal('show')
        document.querySelector('.confirm-deletemap').addEventListener('click', function(){
            e.target.parentElement.parentElement.parentElement.remove();
          //  $('#success-alert-modal').modal('show');
            // $('#scroll-horizontal-datatable').DataTable().ajax.reload( null, false );
        });
    }
    return false;
}

function dataTableAdjust(){
    var table = $('#scroll-horizontal-datatable').DataTable();
    table.columns.adjust().draw();
}
//Filter table for statement type
let stmt = document.querySelector('#stmt_type');
stmt = stmt ? stmt.addEventListener('change', filterWithStmt) : '';
let actyp = document.querySelector('#acc_type'); 
actyp = actyp ? actyp.addEventListener('change', filterWithAcc) : '';

function filterWithStmt(){
    let stmtvalue = this.value;
    if(stmtvalue == 'Interim'){
        document.querySelectorAll('.final').forEach(element => element.classList.add('d-none'));
        document.querySelectorAll('.'+stmtvalue.toLowerCase()).forEach(element => element.classList.remove('d-none'));
        dataTableAdjust();
    }
    else{
        document.querySelectorAll('.interim').forEach(element => element.classList.add('d-none'));
        document.querySelectorAll('.'+stmtvalue.toLowerCase()).forEach(element => element.classList.remove('d-none'));
        dataTableAdjust();
    }
}
function filterWithAcc(){
    let accvalue = this.value;
    console.log(accvalue);
    if(accvalue == 'Collection'){
        document.querySelectorAll('.disbursement').forEach(element => element.classList.add('d-none'));
        document.querySelectorAll('.'+accvalue.toLowerCase()).forEach(element => element.classList.remove('d-none'));
        dataTableAdjust();
    }
    else{
        document.querySelectorAll('.collection').forEach(element => element.classList.add('d-none'));
        document.querySelectorAll('.'+accvalue.toLowerCase()).forEach(element => element.classList.remove('d-none'));
        dataTableAdjust();
    }
}

$(window).on("resize",function(e){
    dataTableAdjust();
});

$(function(){
    $('input[type="text"]').change(function(){
        this.value = $.trim(this.value);
    });
});

$(function(){
    $('textarea[type="text"]').change(function(){
        this.value = $.trim(this.value);
    });
});

// WEB3 migration changes - Web2/Web3 Toggle Functionality
// Initialize toggle state on page load
$(document).ready(function() {
    // Get current mode from server session
    $.get('/api/web3/mode', function(response) {
        const web3Mode = response.web3Mode || false;
        const toggle = document.getElementById('web3Toggle');
        if (toggle) {
            toggle.checked = web3Mode;
            updateWeb3Mode(web3Mode);
        }
        // Initialize table name indicator
        updateTableNameIndicator();
    }).fail(function() {
        // Fallback to localStorage if API fails
        const web3Mode = localStorage.getItem('web3Mode') === 'true';
        const toggle = document.getElementById('web3Toggle');
        if (toggle) {
            toggle.checked = web3Mode;
            updateWeb3Mode(web3Mode);
        }
        // Initialize table name indicator even on fail
        updateTableNameIndicator();
    });
});

// Toggle Web2/Web3 mode
function toggleWeb3(checkbox) {
    const isWeb3 = checkbox.checked;
    
    // Save to localStorage as backup
    localStorage.setItem('web3Mode', isWeb3);
    
    // Send to server to update session
    $.ajax({
        url: '/api/web3/mode',
        type: 'POST',
        data: { enabled: isWeb3 },
        dataType: 'json',
        success: function(response) {
            if (response && response.success) {
                updateWeb3Mode(isWeb3);
                // Reload page to apply table changes
                setTimeout(function() {
                    location.reload();
                }, 100);
            } else {
                console.warn('Web3 mode update response:', response);
                updateWeb3Mode(isWeb3);
            }
        },
        error: function(xhr, status, error) {
            console.error('Failed to update Web3 mode on server:', status, error);
            // Still update UI locally and try to continue
            updateWeb3Mode(isWeb3);
            // Try to set in session via alternative method
            console.log('Web3 mode set locally. Please refresh page to apply changes.');
        }
    });
}

// Update UI based on Web2/Web3 mode
function updateWeb3Mode(isWeb3) {
    // Add/remove class to body for styling purposes
    if (isWeb3) {
        document.body.classList.add('web3-mode');
        document.body.classList.remove('web2-mode');
        console.log('Web3 mode enabled - Using Web3 tables where mappings exist');
    } else {
        document.body.classList.add('web2-mode');
        document.body.classList.remove('web3-mode');
        console.log('Web2 mode enabled - Using Web2 tables');
    }
    // Update table name indicator
    updateTableNameIndicator();
}

// WEB3 migration changes - Update table name indicator in header
function updateTableNameIndicator() {
    // Try to get table name from page data attribute or URL
    const tableName = getCurrentPageTableName();
    
    if (!tableName) {
        $('#currentTableName').text('-');
        return;
    }
    
    // Fetch resolved table name from API
    $.get('/api/web3/table-name', { tableName: tableName })
        .done(function(response) {
            if (response && response.resolvedTableName) {
                const mode = response.web3Mode ? 'Web3' : 'Web2';
                const displayText = `${mode}: ${response.resolvedTableName}`;
                $('#currentTableName').text(displayText);
                $('#currentTableName').attr('title', `Original: ${response.originalTableName}\nResolved: ${response.resolvedTableName}`);
            }
        })
        .fail(function() {
            $('#currentTableName').text('Table: ' + tableName);
        });
}

// WEB3 migration changes - Get current page's table name from URL or data attributes
function getCurrentPageTableName() {
    // Check if page has data attribute for table name (check both body and wrapper)
    const pageTableName = $('body').data('table-name') || $('#wrapper').data('table-name');
    if (pageTableName) {
        return pageTableName;
    }
    
    // Try to infer from URL path
    const path = window.location.pathname;
    const pathParts = path.split('/').filter(p => p);
    
    // Common patterns:
    // /country/list -> admin_countries
    // /currency/list -> admin_currencies
    // /user/list -> admin_users
    // /product/list -> admin_products
    
    if (pathParts.length >= 1) {
        const entity = pathParts[0];
        // Map common entity names to table names
        const entityToTableMap = {
            'country': 'admin_countries',
            'countries': 'admin_countries',
            'currency': 'admin_currencies',
            'currencies': 'admin_currencies',
            'user': 'admin_users',
            'users': 'admin_users',
            'product': 'admin_products',
            'products': 'admin_products',
            'partner': 'admin_partner',
            'partners': 'admin_partner',
            'location': 'admin_location',
            'locations': 'admin_location',
            'status': 'admin_status',
            'purpose': 'admin_purpose',
            'holiday': 'admin_holiday_calendar',
        };
        
        const tableName = entityToTableMap[entity.toLowerCase()];
        if (tableName) {
            return tableName;
        }
        
        // Fallback: try to construct table name
        return 'admin_' + entity.toLowerCase();
    }
    
    return null;
}   