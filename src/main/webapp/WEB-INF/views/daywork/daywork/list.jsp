<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="kr.co.hs.emp.dto.EmpDTO" %>
<%@ page import="kr.co.hs.common.security.UserDetail" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
<style>
    tr.odd {
        height: 100px;
    }

    tr.even {
        height: 100px;
    }

    /* .title-daywork {
        width: 350px;
        display: flex;
        overflow: hidden;
    } */

    .dataTables_filter {
        display: none !important;
    }
    .time-order{
        float: left;
        margin-top: 4px;
        width: 81px;
        text-align: center;
    }
</style>
<%
    String start_date = request.getParameter("start_date");
    if (start_date == null || start_date == "null") {
        start_date = java.time.LocalDate.now().toString();
    }
    String end_date = request.getParameter("end_date");
    if (end_date == null || end_date == "null") {
        end_date = java.time.LocalDate.now().toString();
    }
    String levelDeptGet = request.getParameter("levelDept");
    if (levelDeptGet == null || levelDeptGet == "null") {
        levelDeptGet = "1";
    }
    request.setAttribute("levelDeptGet", levelDeptGet);
    String translationStatusGet = request.getParameter("translationStatus");
    if (translationStatusGet == null || translationStatusGet == "null") {
        translationStatusGet = "";
    }
    request.setAttribute("translationStatusGet", translationStatusGet);
    String workStatusGet = request.getParameter("workStatus");
    if (workStatusGet == null || workStatusGet == "null") {
        workStatusGet = "";
    }
    request.setAttribute("workStatusGet", workStatusGet);
    String optionSearchGet = request.getParameter("optionSearch");
    if (optionSearchGet == null || optionSearchGet == "null") {
        optionSearchGet = "";
    }
    request.setAttribute("optionSearchGet", optionSearchGet);
    String inputSearchGet = request.getParameter("inputSearch");
    if (inputSearchGet == null || inputSearchGet == "null") {
        inputSearchGet = "";
    }
    String displayStartPra = request.getParameter("displayStartPra");
    if (displayStartPra == null || displayStartPra == "null") {
        displayStartPra = "0";
    }
%>

<div id="content" class="content">
    <h1 class="page-header cursor-default">
        <spring:message code='main.index.work'/> - <spring:message code='tieude.dayworkbig'/> <br>
        <small>
            <spring:message code='tieude.dayworksmall'/>
        </small>
    </h1>
    <div class="row">
        <div class="col-xl-12 ui-sortable">
            <div class="panel panel-inverse" style="">
                <div class="panel-heading">
                    <h3 class="panel-title"><spring:message code='search.title' /></h3>
                </div>
                <div class="panel-body">
                    <form id="myform" class="form-inline" action="">
                        <table class="table mb-0">
                            <tbody>
                            <tr>
                                <td style="background: #f0f4f6; font-weight: bold; width: 135px;"><spring:message code='search.title.regisdate'/></td>
                                <td>
                                    <input readonly id="start_date" name="start_date" class="form-control form-control-sm mr-1 bg-white" type="text" value="<%=start_date%>">~
                                    <input readonly id="end_date" name="end_date" class="form-control form-control-sm mr-1 bg-white" type="text" value="<%=end_date%>">
                                    <br>
                                    <a class="time-order btn-default form-control-sm mr-1" href="#" onclick="DateChange('today')"><spring:message code='search.button.daychoose.inday'/></a>
                                    <a class="time-order btn-default form-control-sm mr-1" href="#" onclick="DateChange('oneweek')"><spring:message code='search.button.daychoose.inweek'/></a>
                                    <a class="time-order btn-default form-control-sm mr-1" href="#" onclick="DateChange('onemonth')"><spring:message code='search.button.daychoose.inmonth'/></a>
                                    <a class="time-order btn-default form-control-sm mr-1" href="#" onclick="DateChange('threemonth')"><spring:message code='search.button.daychoose.in3month'/></a>
                                    <a class="time-order btn-default form-control-sm mr-1" href="#" onclick="DateChange('sixmonth')"><spring:message code='search.button.daychoose.in6month'/></a>
                                    <a class="time-order btn-default form-control-sm mr-1" href="#" onclick="DateChange('oneyear')"><spring:message code='search.button.daychoose.in1year'/></a>
                                    <a class="time-order btn-default form-control-sm mr-1" href="#" onclick="DateChange('all')"><spring:message code='search.button.daychoose.inall'/></a>
                                </td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6; font-weight: bold"><spring:message code='work.list.dept'/></td>
                                <td class="row m-0">
                                    <select id="levelDept" name="levelDept" class="form-control col-sm-12 col-md-6 col-xl-3">
                                        <c:forEach items="${lstDeptLevel}" var="lst">
                                            <option value="${lst.deptCd}" ${lst.deptCd==levelDeptGet ? "selected" : "" }>${lst.deptName}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6; font-weight: bold">
                                    <spring:message code='work.list.translationstatus'/>
                                </td>
                                <td class="row m-0">
                                    <select id="translationStatus" name="translationStatus"class="custom-select custom-select-sm form-control form-control-sm mr-1 col-sm-12 col-md-6 col-xl-3">
                                        <option value=""><spring:message code='search.selectoption.first'/></option>
                                        <option value="Y" ${translationStatusGet.equals("Y")? "selected" : "" }><spring:message code='work.detail.finish'/></option>
                                        <option value="N" ${translationStatusGet.equals("N")? "selected" : "" }><spring:message code='work.detail.notfinish'/></option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6; font-weight: bold">
                                    <spring:message code='search.title.work'/>
                                </td>
                                <td class="row m-0">
                                    <select id="workStatus" name="workStatus" class="custom-select custom-select-sm form-control form-control-sm mr-1 col-sm-12 col-md-6 col-xl-3">
                                        <option value=""><spring:message code='search.selectoption.first'/></option>
                                        <option value="NOTYET" ${workStatusGet.equals("NOTYET")? "selected" : "" }><spring:message code='search.workstatus.notyet'/></option>
                                        <option value="WORKING" ${workStatusGet.equals("WORKING")? "selected" : "" }><spring:message code='search.workstatus.working'/></option>
                                        <option value="FINISH" ${workStatusGet.equals("FINISH")? "selected" : "" }><spring:message code='search.workstatus.done'/></option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6; font-weight: bold">
                                    <spring:message code='dateboard.notice.company.timkiem'/>
                                </td>
                                <td>
                                    <select id="optionSearch" name="optionSearch" class="custom-select custom-select-sm form-control form-control-sm m-2">
                                        <option value="emp_name" ${optionSearchGet.equals("emp_name") ? "selected" : "" }><spring:message code='dateboard.notice.company.empname'/></option>
                                        <option value="position_name" ${optionSearchGet.equals("position_name") ? "selected" : "" }><spring:message code='work.list.position'/></option>
                                        <option value="title" ${optionSearchGet.equals("title") ? "selected" : "" }><spring:message code='work.list.title'/></option>
                                    </select>
                                    <input id="inputSearch" name="inputSearch" class="form-control form-control-sm m-2" type="text" value="<%=inputSearchGet%>" />
                                    <button type="button" class="btn btn-sm btn-primary m-2" onclick="searchMethod()"><spring:message code='dateboard.notice.company.timkiem'/></button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div style="float: right">
        <%
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
            if(empDTO.getAdminYn().equals("Y")){
                System.out.println(empDTO.getAdminYn());
        %>
        <button class="open-AddBookDialog btn btn-info mb-1" type="button" data-toggle="modal" href="#" onclick="openModel()"><spring:message code='work.list.delete'/></button>
        <%
            }
        %>
        <button class="btn btn-primary mb-1" onclick="addW()"><spring:message code='work.list.addnew'/></button>
    </div>
    <div style="clear: both"></div>
    <!-- html -->
    <div class="m-table" style="position: relative;">
        <table id="tbwork" class="table table-striped table-bordered w-100 text-center">
            <thead>
                <tr style="background-color: #1c5691;color: white">
                    <th style="vertical-align: inherit;" class="all"><input type="checkbox" name="all" id="checkall"/></th>
                    <th style="vertical-align: inherit;"><spring:message code='work.list.stt'/></th>
                    <th style="vertical-align: inherit;"><spring:message code='work.list.empname'/></th>
                    <th style="vertical-align: inherit;" class="min-tablet"><spring:message code='work.list.dept'/></th>
                    <th style="vertical-align: inherit;" class="min-tablet"><spring:message code='work.list.position'/></th>
                    <th style="vertical-align: inherit;"><spring:message code='work.list.title'/></th>
                    <th style="vertical-align: inherit;" class="min-tablet"><spring:message code='work.list.trangthai'/></th>
                    <th style="vertical-align: inherit;"><spring:message code='work.list.registerDate'/></th>
                    <th style="vertical-align: inherit;"><spring:message code='work.list.translationstatus'/></th>
                    <th style="vertical-align: inherit;"><spring:message code='work.list.function'/></th>
                </tr>
            </thead>
        </table>
    </div>
    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel"><spring:message code='confirm.delete' /></h5>
                    <input type="text" hidden="" value="" name="" id="giatri"/>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code='translation.close' /></button>
                    <button type="button" class="btn btn-primary" onclick="xoaW()"><spring:message code='work.list.delete' /></button>
                </div>
            </div>
        </div>
    </div>

</div>
<!-- script -->

<script>
    function openModel() {
        
        var lstChecked = "";
        var myTable = $('#tbwork').dataTable();
        var rowcollection = myTable.$('input[name="xoa"]:checked', {"page": "all"});
        rowcollection.each(function (index, elem) {
            var checkbox_value = $(elem).val();
            lstChecked += checkbox_value + ",";
        });
        if (lstChecked.trim() == "") {
            alert("<spring:message code='notice.select.message' />");
        } else {
            $("#exampleModal").modal('show');
        }
    }
    $(document).ready(function () {
        if ('${_lang}' == 'ko') {
            lang_dt = lang_kor;
        } else if ('${_lang}' == 'en') {
            lang_dt = lang_en;
        } else {
            lang_dt = lang_vt;
        }
        $('#checkall').change(function () {
            $('.cb-element').prop('checked', this.checked);
        });
        var start_date = $("#start_date").datepicker({dateFormat: 'yy-mm-dd'});
        var end_date = $("#end_date").datepicker({dateFormat: 'yy-mm-dd'});
        var table = $('#tbwork').DataTable({
            'displayStart': <%=displayStartPra%>,
            "dom": '<"toolbar">frtip',
            "order": [[1, "desc"]],
            'iDisplayLength': 10,
            "responsive": true, "lengthChange": false, "autoWidth": false,
            "language": lang_dt,
            "bProcessing": true,
            "bServerSide": true,
            "bStateSave": false,
            "iDisplayStart": 0,
            "fnDrawCallback": function () { },
            "sAjaxSource": "/springPaginationDataTables?start_date=" + $('#start_date').val() + "&end_date=" + $('#end_date').val() + "&levelDept=" + $("#levelDept").val() + "&translationStatus=" + $('#translationStatus').val() + "&workStatus=" + $('#workStatus').val() + "&optionSearch=" + $('#optionSearch').val() + "&inputSearch=" + $('#inputSearch').val(),
            "columns": [
                {
                    "data": "dayWorkId",
                    render: function (data, type, full) {
                        return '<input class="cb-element" name="xoa" type="checkbox" value="'+ data +'" >';
                    }
                }, 
                { "data": "rownum" },
                { "data": "empName" },
                { "data": "deptName" },
                { "data": "positionName" },
                { "data": "title" },
                { "data": "status" },
                { "data": "regDt" },
                { "data": "translationStatus" }
            ],"fnRowCallback": function (nRow, aData, iDisplayIndex) {
                if (aData["translationStatus"] >0) {
                    $("td:eq(8)", nRow).html('<spring:message code="work.detail.finish" />');
                } else {
                    $("td:eq(8)", nRow).html('<spring:message code="work.detail.notfinish" />');
                }
            },
            "aoColumnDefs": [{
                    "aTargets": [5],
                    "mData": "title",
                    "mRender": function (data, type, full) {
                        return '<div class = "title-daywork">' + data + '</div>';
                    }
                }, {
                    "aTargets": [9],
                    "mData": "dayWorkId",
                    "mRender": function (data, type, full) {
                        return '<button type="button" onclick="detail('
                            + data
                            + ')" class="btn btn-success btn-sm"  ><spring:message code="work.list.viewdetail" /></button>';
                    }
                },
                {"orderable": false, "targets": 0},
                {"orderable": false, "targets": 6},

            ]
        });

        if ('${title}' != '') {
            if ('${title}' == 'success') {
                //toastr['${title}']('${message}');
                toastr.success('', "<spring:message code='notification.index.success' />", {timeOut: 1000});
            } else {
                toastr.error('', "<spring:message code='notification.index.error' />", {timeOut: 1000});
            }
        }

        $('#tbwork').on( 'page.dt', function () {
            var displayStartPra = $('#tbwork').DataTable().page.info().page * 10;
            location.href = "/work/daywork/list.hs?start_date=" + $('#start_date').val() + "&end_date=" + $('#end_date').val() + "&levelDept=" + $("#levelDept").val() + "&translationStatus=" + $('#translationStatus').val() + "&workStatus=" + $('#workStatus').val() + "&optionSearch=" + $('#optionSearch').val() + "&inputSearch=" + $('#inputSearch').val() + "&displayStartPra=" + displayStartPra;
        });

    });

    function detail(id) {
        var displayStartPra = $('#tbwork').DataTable().page.info().page * 10;

        location.href = "/work/daywork/detail.hs?id=" + id + "&start_date=" + $('#start_date').val() + "&end_date=" + $('#end_date').val() + "&levelDept=" + $("#levelDept").val() + "&translationStatus=" + $('#translationStatus').val() + "&workStatus=" + $('#workStatus').val() + "&optionSearch=" + $('#optionSearch').val() + "&inputSearch=" + $('#inputSearch').val() + "&displayStartPra=" + displayStartPra;
    }

    // function getCreateDateHighlight(){
    //     var createDate="";
    //     var elements = document.getElementsByName( 'ngaytao' );
    //     for(var i=0;i<elements.length;i++){
    //         var id = elements[i].getAttribute( 'id' );
    //         var mau = $('#'+id).css( "background-color" );
    //         if(mau=="rgb(144, 238, 144)"){
    //             createDate=id;
    //         }
    //     }
    //     return createDate;
    // }

    function addW() {
        location.href = "/work/daywork/addW.hs";
    }

    function xoaW() {

        var lstChecked = "";
        var myTable = $('#tbwork').dataTable();
        var rowcollection = myTable.$('input[name="xoa"]:checked', {"page": "all"});
        rowcollection.each(function (index, elem) {
            var checkbox_value = $(elem).val();
            lstChecked += checkbox_value + ",";
        });

        if (lstChecked.trim() == "") {
            toastr['error']('Delete error !');
        } else {
            $.ajax({
                type: "POST",
                url: "/work/daywork/xoaW",
                data: {
                    'lstChecked': lstChecked,
                },
                success: function (response) {
                    location.href = "/work/daywork/list.hs?title=success&message=Delete sucess";
                },
                error: function (error) {
                    toastr['error']('Delete error !');
                }
            })
        }
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
        location.href = "/work/daywork/list.hs?start_date=" + $('#start_date').val() + "&end_date=" + $('#end_date').val() + "&levelDept=" + $("#levelDept").val() + "&translationStatus=" + $('#translationStatus').val() + "&workStatus=" + $('#workStatus').val() + "&optionSearch=" + $('#optionSearch').val() + "&inputSearch=" + $('#inputSearch').val() + "&displayStartPra=" + displayStartPra;
    }
</script>