$(document).ready(function () {
    $('#checkall').change(function () {
        $('.cb-element').prop('checked', this.checked);
    });
    var start_date = $("#start_date").datepicker({ dateFormat: 'yy-mm-dd' });
    var end_date = $("#end_date").datepicker({ dateFormat: 'yy-mm-dd' });
    var lang_dt;
    if ('${_lang}' == 'ko') {
        lang_dt = lang_kor;
    } else if ('${_lang}' == 'en') {
        lang_dt = lang_en;
    } else {
        lang_dt = lang_vt;
    }
    var table = $("#projecttable").DataTable({
        'displayStart': '<%=displayStartPra%>',
        "dom": '<"toolbar">frtip',
        "order": [[1, "desc"]],
        'iDisplayLength': 10,
        "responsive": true, "lengthChange": false, "autoWidth": false,
        "language": lang_dt,
        "bProcessing": true,
        "bServerSide": true,
        "bStateSave": false,
        "iDisplayStart": 0,
        "fnDrawCallback": function () {
        },
        "sAjaxSource": "/API/project/main?start_date=" + $('#start_date').val() + "&end_date=" + $('#end_date').val() + "&levelDept=" + $("#levelDept").val() + "&translationStatus=" + $('#translationStatus').val() + "&approval=" + $('#approval').val() + "&optionSearch=" + $('#optionSearch').val() + "&inputSearch=" + $('#inputSearch').val() + "&type=1",
        "columns": [
            {
                "data": "pjId",
                render: function (data, type, full) {
                    return '<input class="cb-element" name="checkBox" type="checkbox" value="'
                        + data + '" >';
                }
            },
            {
                "data": "rownum"
            },
            {
                "data": "title"
            },
            {
                "data": "deptName"
            },
            {
                "data": "positionName"
            },
            {
                "data": "leaderProjectName"
            },
            {
                "data": "advanceAmount"
            },
            {
                "data": "useAmount"
            },
            {
                "data": "remainAmount"
            },
            {
                "data": "approval"
            },
            {
                "data": "translationStatus"
            },
            {
                "data": "projectStatus"
            }
        ],
        "fnRowCallback": function (nRow, aData, iDisplayIndex) {
            if (aData["projectStatus"] == 'Y') {
                $("td:eq(11)", nRow).html('<spring:message code="work.detail.finish" />');
            } else {
                $("td:eq(11)", nRow).html('<spring:message code="work.detail.notfinish" />');
            }

            if (aData["translationStatus"] > 0) {
                $("td:eq(10)", nRow).html('<spring:message code="work.detail.finish" />');
            } else {
                $("td:eq(10)", nRow).html('<spring:message code="work.detail.notfinish" />');
            }


        },
        "aoColumnDefs": [
            {
                "aTargets": [12],
                "mData": "pjId",
                "mRender": function (data, type, full) {
                    return '<button type="button" onclick="detail('
                        + data
                        + ')" class="btn btn-success"  ><spring:message code="work.list.viewdetail" /></button>';
                }
            },
            { "orderable": false, "targets": 0 },
            { "orderable": false, "targets": 12 },
        ],
    });



    $('#projecttable').on('page.dt', function () {
        var displayStartPra = $('#projecttable').DataTable().page.info().page * 10;
        console.log("dxd " + displayStartPra)
    });
});

function createProject() {
    location.href = "/project/project/add.hs"
}

function DateChange(createDate) {
    if (createDate == "today") {
        var today = GetOutDate(0);
        $("#start_date").val(today);
        $("#end_date").val(today);
    } else if (createDate == "oneweek") {
        var today = GetOutDate(0);
        var out_date = GetOutDate(7);
        $("#start_date").val(out_date);
        $("#end_date").val(today);
    } else if (createDate == "onemonth") {
        var today = GetOutDate(0);
        var out_date = GetOutDate(30);
        $("#start_date").val(out_date);
        $("#end_date").val(today);
    } else if (createDate == "threemonth") {
        var today = GetOutDate(0);
        var out_date = GetOutDate(90);
        $("#start_date").val(out_date);
        $("#end_date").val(today);
    } else if (createDate == "sixmonth") {
        var today = GetOutDate(0);
        var out_date = GetOutDate(180);
        $("#start_date").val(out_date);
        $("#end_date").val(today);
    } else if (createDate == "oneyear") {
        var today = GetOutDate(0);
        var out_date = GetOutDate(360);
        $("#start_date").val(out_date);
        $("#end_date").val(today);
    } else if (createDate == "all") {
        var today = GetOutDate(0);
        var out_date = GetOutDate(365);
        $("#start_date").val("");
        $("#end_date").val("");
    }
}

function GetOutDate(minusDay) {
    var d = new Date();
    d.setDate(d.getDate() - minusDay);
    var month = d.getMonth() + 1;
    var day = d.getDate();
    var output = d.getFullYear() + '-' + (month < 10 ? '0' : '') + month + '-' + (day < 10 ? '0' : '') + day;
    return output;
}
function searchMethod() {
    var displayStartPra = 0;
    location.href = "/project/project/list.hs?start_date=" + $('#start_date').val() + "&end_date=" + $('#end_date').val() + "&levelDept=" + $("#levelDept").val() + "&translationStatus=" + $('#translationStatus').val() + "&approval=" + $('#approval').val() + "&optionSearch=" + $('#optionSearch').val() + "&inputSearch=" + $('#inputSearch').val() + "&displayStartPra=" + displayStartPra;
}
function detail(id) {
    location.href = "/project/project/dayproject.hs?pjId=" + id;
}
function editProject() {

    var i = 0;
    var lstChecked = "";
    var myTable = $('#projecttable').dataTable();
    var rowcollection = myTable.$('input[name="checkBox"]:checked', { "page": "all" });
    rowcollection.each(function (index, elem) {
        var checkbox_value = $(elem).val();
        lstChecked += checkbox_value + ",";
        i++;
    });
    lstChecked = lstChecked.substring(0, lstChecked.length - 1);
    if (lstChecked.trim() == "") {
        alert("<spring:message code='notice.select.message' />");
    } else if (lstChecked.trim() != "" && i > 1) {
        alert("Only 1 cell can be selected");
    } else {
        location.href = "/project/project/edit.hs?id=" + lstChecked;
    }
}

function deleteProject() {
    var lstChecked = "";
    var myTable = $('#projecttable').dataTable();
    var rowcollection = myTable.$('input[name="checkBox"]:checked', { "page": "all" });
    rowcollection.each(function (index, elem) {
        var checkbox_value = $(elem).val();
        lstChecked += checkbox_value + ",";
    });

    if (lstChecked.trim() == "") {
        toastr['error']('Delete error !');
    } else {
        $.ajax({
            type: "POST",
            url: "/API/mainproject/delete",
            data: {
                'lstChecked': lstChecked,
            },
            success: function (response) {
                location.href = "/project/project/list.hs?title=success&message=Delete sucess";
            },
            error: function (error) {
                toastr['error']('Delete error !');
            }
        })
    }
}

function approvalMainProject() {
    var lstChecked = "";
    var myTable = $('#projecttable').dataTable();
    var rowcollection = myTable.$('input[name="checkBox"]:checked', { "page": "all" });
    rowcollection.each(function (index, elem) {
        var checkbox_value = $(elem).val();
        lstChecked += checkbox_value + ",";
    });

    if (lstChecked.trim() == "") {
        toastr['error']('Delete error !');
    } else {
        $.ajax({
            type: "POST",
            url: "/API/mainproject/approval",
            data: {
                'lstChecked': lstChecked,
            },
            success: function (response) {
                location.href = "/project/project/list.hs?title=success&message=Approval sucess";
            },
            error: function (error) {
                toastr['error']('Delete error !');
            }
        })
    }
}

function historyLeaderShow() {
    var i = 0;
    var lstChecked = "";
    var myTable = $('#projecttable').dataTable();
    var rowcollection = myTable.$('input[name="checkBox"]:checked', { "page": "all" });
    rowcollection.each(function (index, elem) {
        var checkbox_value = $(elem).val();
        lstChecked += checkbox_value + ",";
        i++;
    });
    lstChecked = lstChecked.substring(0, lstChecked.length - 1);

    if (lstChecked.trim() == "") {
        alert("<spring:message code='notice.select.message' />");
    } else if (lstChecked.trim() != "" && i > 1) {
        alert("Only 1 cell can be selected");
    } else {
        $('#exampleModal').modal('show')
        $.ajax({
            type: "GET",
            url: "/API/mainproject/leaderlist",
            data: {
                pjId: lstChecked,
            },
            success: function (response) {
                var str = "";
                for (var i = 0; i < response.length; i++) {

                    str += "<tr><td>" + response[i]["pjId"] + "</td>";
                    str += "<td>" + response[i]["leaderType"] + "</td>";
                    str += "<td>" + response[i]["empName"] + "</td>";
                    str += "<td>" + response[i]["deptName"] + "</td>";
                    str += "<td>" + response[i]["createDate"] + "</td>";
                    str += "<td>" + response[i]["createBy"] + "</td></tr>";
                }

                $("#rsatt tbody").html(str);
                location.href = "#exampleModal";
            },
            error: function (error) {
                toastr['error']('error !');
            }
        })
    }
}

function exportExcel() {
    // var settings = {
    //     "url": "/API/mainproject/exportexcel?start_date="+$('#start_date').val()+"&end_date="+$('#end_date').val()+"&levelDept="+$("#levelDept").val()+"&translationStatus="+$('#translationStatus').val()+"&approval="+$('#approval').val()+"&optionSearch="+$('#optionSearch').val()+"emp_name&inputSearch="+$('#inputSearch').val()+"&iDisplayStart=0&iDisplayLength=100000&sSearch=&iSortCol_0=&sSortDir_0=desc",
    //     "method": "POST",
    //     "timeout": 0,
    //     "data": JSON.stringify(),
    // };
    // $.ajax(settings).done(function (response) {
    //     console.log("DXDDDD")
    // });

    $.ajax({
        type: "GET",
        url: "/API/mainproject/exportexcel?start_date=" + $('#start_date').val() + "&end_date=" + $('#end_date').val() + "&levelDept=" + $("#levelDept").val() + "&translationStatus=" + $('#translationStatus').val() + "&approval=" + $('#approval').val() + "&optionSearch=" + $('#optionSearch').val() + "emp_name&inputSearch=" + $('#inputSearch').val() + "&iDisplayStart=0&iDisplayLength=100000&sSearch=&iSortCol_0=&sSortDir_0=desc",
        data: JSON.stringify(),
        success: function (response) {
            location.href = "/resources/Upload/excel_file/" + response;
        },
        error: function (error) {
            alert(error)
        }
    })
}

function printtag(tagid) {
    var start_date = $("#start_date").datepicker({ dateFormat: 'yy-mm-dd' });
    var end_date = $("#end_date").datepicker({ dateFormat: 'yy-mm-dd' });
    var lang_dt;
    if ('${_lang}' == 'ko') {
        lang_dt = lang_kor;
    } else if ('${_lang}' == 'en') {
        lang_dt = lang_en;
    } else {
        lang_dt = lang_vt;
    }
    var table = $("#printTable").DataTable({
        'displayStart': '<%=displayStartPra%>',
        "dom": '<"toolbar">frtip',
        "order": [[0, "desc"]],
        'iDisplayLength': 10000000,
        "responsive": true, "lengthChange": false, "autoWidth": false,
        "language": lang_dt,
        "bProcessing": true,
        "bServerSide": true,
        "bStateSave": false,
        "iDisplayStart": 0,
        "fnDrawCallback": function () {
        },
        "sAjaxSource": "/API/project/main?start_date=" + $('#start_date').val() + "&end_date=" + $('#end_date').val() + "&levelDept=" + $("#levelDept").val() + "&translationStatus=" + $('#translationStatus').val() + "&approval=" + $('#approval').val() + "&optionSearch=" + $('#optionSearch').val() + "&inputSearch=" + $('#inputSearch').val() + "&type=1",
        "columns": [
            {
                "data": "rownum"
            },
            {
                "data": "title"
            },
            {
                "data": "deptName"
            },
            {
                "data": "positionName"
            },
            {
                "data": "leaderProjectName"
            },
            {
                "data": "advanceAmount"
            },
            {
                "data": "useAmount"
            },
            {
                "data": "remainAmount"
            },
            {
                "data": "approval"
            },
            {
                "data": "translationStatus"
            },
            {
                "data": "projectStatus"
            }
        ],
        "fnRowCallback": function (nRow, aData, iDisplayIndex) {
            if (aData["translationStatus"] > 0) {
                $("td:eq(9)", nRow).html('<spring:message code="work.detail.finish" />');
            } else {
                $("td:eq(9)", nRow).html('<spring:message code="work.detail.notfinish" />');
            }


        },
        "aoColumnDefs": [

            { "orderable": false, "targets": 0 },
            { "orderable": false, "targets": 1 },
            { "orderable": false, "targets": 2 },
            { "orderable": false, "targets": 3 },
            { "orderable": false, "targets": 4 },
            { "orderable": false, "targets": 5 },
            { "orderable": false, "targets": 6 },
            { "orderable": false, "targets": 7 },
            { "orderable": false, "targets": 8 },
            { "orderable": false, "targets": 9 },
            { "orderable": false, "targets": 10 },

        ]
    });
    table.on('draw', function () {
        mainPrinTable(tagid)
    });
}

function mainPrinTable(tagid) {
    var hashid = "#" + tagid;
    var tagname = $(hashid).prop("tagName").toLowerCase();
    var attributes = "";
    var attrs = document.getElementById(tagid).attributes;
    $.each(attrs, function (i, elem) {
        attributes += " " + elem.name + " ='" + elem.value + "' ";
    })
    var divToPrint = $(hashid).html();
    var head = "<html><head>" + $("head").html() + "</head>";
    head += '' +
        '<style type="text/css">' +
        '.tr-print-table {' +
        'background-color: #1c5691;' +
        'border:1px solid #000;' +
        'padding;0.5em;' +
        '}' +

        'table#printTable {' +
        '    font-size: 11px;' +
        '}' +

        '#printTable thead tr th.sorting_desc:after{' +
        '    display: none !important;' +
        '}' +

        '.table .odd, .even {' +
        '    height: 75px;' +
        '}' +

        '.child-td-table-print{' +
        '    border: none !important;' +
        '}' +

        'table.table.mb-0.header-print-table {' +
        '    position: fixed;' +
        '}' +

        'td.child-td-table-print {' +
        '    border: none !important;' +
        '    height: 70px;' +
        '}' +

        'body {' +
        '    background: none !important;' +
        '}' +

        'div#printTable_info {' +
        '    display: none;' +
        '}' +

        'div#printTable_paginate {' +
        '    display: none;' +
        '}' +

        'div#printTable_processing {' +
        '    display: none !important;' +
        '}' +
        '</style>';

    var allcontent = head + "<body  onload='window.print()' >" + "<" + tagname + attributes + ">" + divToPrint + "</" + tagname + ">" + "</body></html>";
    var newWin = window.open('', 'Print-Window');
    //newWin.document.open();
    newWin.document.write(allcontent);
    newWin.document.close();
    //setTimeout(function(){newWin.close();},10);
}

function printData() {
    var divToPrint = document.getElementById("printTable");
    newWin = window.open("");
    newWin.document.write(divToPrint.outerHTML);
    newWin.print();
    newWin.close();
}

function printFunc() {
    var divToPrint = document.getElementById('print-table');
    var htmlToPrint = '' +
        '<style type="text/css">' +
        '.tr-print-table {' +
        'background-color: #1c5691;' +
        'border:1px solid #000;' +
        'padding;0.5em;' +
        '}' +

        'table#printTable {' +
        '    font-size: 11px;' +
        '}' +

        '#printTable thead tr th.sorting_desc:after{' +
        '    display: none !important;' +
        '}' +

        '.table .odd, .even {' +
        '    height: 75px;' +
        '}' +

        '.child-td-table-print{' +
        '    border: none !important;' +
        '    margin-bottom: 100px' +
        '}' +

        '</style>';
    htmlToPrint += divToPrint.outerHTML;
    newWin = window.open("");
    newWin.document.write("<h3 align='center'>Print Page</h3>");
    newWin.document.write(htmlToPrint);
    newWin.print();
    newWin.close();
}