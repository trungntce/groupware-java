<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="kr.co.hs.emp.dto.EmpDTO" %>
<%@ page import="kr.co.hs.common.security.UserDetail" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>

<%
    String start_date1 = request.getParameter("start_date1");
    if (start_date1 == null || start_date1 == "null") {
        start_date1 = "";
    }

    String end_date1 = request.getParameter("end_date1");
    if (end_date1 == null || end_date1 == "null") {
        end_date1 = "";
    }

    String levelDeptGet1 = request.getParameter("levelDept1");
    if (levelDeptGet1 == null || levelDeptGet1 == "null") {
        levelDeptGet1 = "1";
    }
    request.setAttribute("levelDeptGet1", levelDeptGet1);

    String translationStatusGet1 = request.getParameter("translationStatus1");
    if (translationStatusGet1 == null || translationStatusGet1 == "null") {
        translationStatusGet1 = "";
    }
    request.setAttribute("translationStatusGet1", translationStatusGet1);

    String approval1 = request.getParameter("approval1");
    if (approval1 == null || approval1 == "null") {
        approval1 = "";
    }
    request.setAttribute("approval1", approval1);

    String optionSearchGet1 = request.getParameter("optionSearch1");
    if (optionSearchGet1 == null || optionSearchGet1 == "null") {
        optionSearchGet1 = "";
    }
    request.setAttribute("optionSearchGet1", optionSearchGet1);

    String inputSearchGet1 = request.getParameter("inputSearch1");
    if (inputSearchGet1 == null || inputSearchGet1 == "null") {
        inputSearchGet1 = "";
    }

    String displayStartPra1 = request.getParameter("displayStartPra1");
    if (displayStartPra1 == null || displayStartPra1 == "null") {
        displayStartPra1 = "0";
    }

    String backSearch="start_date1="+start_date1+"&end_date1="+end_date1+"&levelDept1="+levelDeptGet1+"&translationStatus1="+translationStatusGet1+"&approval1="+approval1+"&optionSearch1="+optionSearchGet1+"&inputSearch1="+inputSearchGet1+"&displayStartPra1="+displayStartPra1;

    //##########################################################################################################################

    String start_date = request.getParameter("start_date");
    if (start_date == null || start_date == "null") {
        start_date = "";
    }
    String end_date = request.getParameter("end_date");
    if (end_date == null || end_date == "null") {
        end_date = "";
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
    String dayProjectStatus = request.getParameter("dayProjectStatus");
    if (dayProjectStatus == null || dayProjectStatus == "null") {
        dayProjectStatus = "";
    }
    request.setAttribute("dayProjectStatus", dayProjectStatus);
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
<style>
    .form-inline td{
        vertical-align: inherit;
    }
    .project-function{
        float: right;
        margin-bottom: 5px;
    }
    .dataTables_filter {
        display: none !important;
    }
    .project-function button{
        margin-top: 5px;
    }
    .currency:after {
        content: ' VND';
    }

    td.details-control {
        background: url('https://www.datatables.net/examples/resources/details_open.png') no-repeat center center;
        cursor: pointer;
    }
    tr.shown td.details-control {
        background: url('https://www.datatables.net/examples/resources/details_close.png') no-repeat center center;
    }

    div#print-table {
        display: none;
    }

    @media (max-width: 1000px) {

        .page-header{
            font-size: 20px;
        }
        .priority-hide
        {
            display: none;
        }
        .titleseach{
            width: 26% !important;
        }
        #searchbtn{
            margin-top: 10px !important;
        }

        #projecttable th:nth-child(4){
            width: 30% !important;
        }
        #projecttable td:nth-child(4){
            white-space: break-spaces;
        }

        #projecttable th:nth-child(3){
            width: 20% !important;
        }

        td.detail-open {
            background: url('https://www.datatables.net/examples/resources/details_open.png') no-repeat right center;
            cursor: pointer;
        }

        tr.shown td.detail-open {
            background: url('https://www.datatables.net/examples/resources/details_close.png') no-repeat right center;
        }

    }

</style>
<div class="content" id="content">
    <h1 class="page-header cursor-default">
        <spring:message code='projectdate.list.index'/><br>
        <small>
            <spring:message code='projectdate.list.smalltitle'/>
        </small>
    </h1>
    <button style="float: right; margin-bottom: 5px;" type="button" class="btn btn-primary" onclick="back()">
        <spring:message code='work.detail.back' />
    </button>
    <div style="clear: both"></div>
    <div class="row">
        <div class="col-xl-12 ui-sortable">
            <div class="panel panel-inverse" style="">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <spring:message code='search.title' />
                    </h3>
                </div>
                <div class="panel-body">
                    <form id="myform" class="form-inline" action="">
                        <table class="table mb-0">
                            <tbody>
                            <tr>
                                <td style="background: #f0f4f6;width: 10%;font-weight: bold" class="priority-hide">
                                    <spring:message code='project.search.time'/>
                                </td>
                                <td style="width: 40%" class="priority-hide">
                                    <input readonly id="start_date" style="background-color: white;"
                                           name="start_date" class="form-control form-control-sm mr-1"
                                           type="text" value="<%=start_date%>">~
                                    <input readonly id="end_date" name="end_date"
                                           style="background-color: white;"
                                           class="form-control form-control-sm mr-1" type="text"
                                           value="<%=end_date%>">
                                    <br><br>
                                    <a class="time-order btn-default form-control-sm mr-1" href="#"
                                       onclick="DateChange('today')">
                                        <spring:message code='search.button.daychoose.inday'/>
                                    </a>
                                    <a class="time-order btn-default form-control-sm mr-1" href="#"
                                       onclick="DateChange('oneweek')">
                                        <spring:message code='search.button.daychoose.inweek'/>
                                    </a>
                                    <a class="time-order btn-default form-control-sm mr-1" href="#"

                                       onclick="DateChange('onemonth')">
                                        <spring:message code='search.button.daychoose.inmonth'/>
                                    </a>
                                    <a class="time-order btn-default form-control-sm mr-1" href="#"

                                       onclick="DateChange('threemonth')">
                                        <spring:message code='search.button.daychoose.in3month'/>
                                    </a>
                                    <a class="time-order btn-default form-control-sm mr-1" href="#"

                                       onclick="DateChange('sixmonth')">
                                        <spring:message code='search.button.daychoose.in6month'/>
                                    </a>
                                    <a class="time-order btn-default form-control-sm mr-1" href="#"
                                       onclick="DateChange('oneyear')">
                                        <spring:message code='search.button.daychoose.in1year'/>
                                    </a>
                                    <a class="time-order btn-default form-control-sm mr-1" href="#"
                                       onclick="DateChange('all')">
                                        <spring:message code='search.button.daychoose.inall'/>
                                    </a>
                                </td>

                                <td style="background: #f0f4f6;width: 10%;font-weight: bold" class="priority-hide">
                                    <spring:message code='work.list.translationstatus'/>
                                </td>
                                <td style="width: 35%" class="priority-hide">
                                    <select id="translationStatus" name="translationStatus" class="custom-select custom-select-sm form-control form-control-sm mr-1">
                                        <option><spring:message code='search.selectoption.first'/></option>
                                        <option value="Y" ${translationStatusGet.equals("Y")? "selected" : "" }><spring:message code='work.detail.finish'/></option>
                                        <option value="N" ${translationStatusGet.equals("N")? "selected" : "" }><spring:message code='work.detail.notfinish'/></option>

                                    </select>
                                </td>
                                
                            </tr>
                            <tr class="priority-hide">
                                <td style="background: #f0f4f6;width: 10%;font-weight: bold">
                                    <spring:message code='project.search.approve'/>
                                </td>
                                <td colspan="3">
                                    <select id="dayProjectStatus" name="dayProjectStatus" class="custom-select custom-select-sm form-control form-control-sm mr-1">
                                        <option><spring:message code='search.selectoption.first'/></option>
                                        <option value="Y" ${dayProjectStatus.equals("Y") ? "selected" : "" }><spring:message code='project.search.approveY'/> </option>
                                        <option value="N" ${dayProjectStatus.equals("N") ? "selected" : "" }><spring:message code='project.search.approveN'/> </option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6;width: 10%;font-weight: bold" class="titleseach">
                                    <spring:message code='project.search.keysearch'/>
                                </td>
                                <td colspan="3">
                                    <select id="optionSearch" name="optionSearch" class="custom-select custom-select-sm form-control form-control-sm mr-1">
                                        <option value="title" ${optionSearchGet.equals("title") ? "selected" : "" }><spring:message code='dayproject.dayproject.daytitle'/></option>
                                        <option value="emp_name" ${optionSearchGet.equals("emp_name") ? "selected" : "" }><spring:message code='dateboard.notice.company.empname'/></option>
                                        <option value="position_name" ${optionSearchGet.equals("position_name") ? "selected" : "" }> <spring:message code='work.list.position'/> </option>
                                        <option value="project_name" ${optionSearchGet.equals("project_name")? "selected" : "" }><spring:message code='dayproject.dayproject.projectname'/></option>
                                    </select>
                                    <input id="inputSearch" name="inputSearch" class="form-control form-control-sm mr-1" type="text" value="<%=inputSearchGet%>">
                                    <button id="searchbtn" type="button" class="btn btn-primary btn-sm" onclick="searchMethod()"> <spring:message code='dateboard.notice.company.timkiem'/> </button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-xl-12 ui-sortable">
            <div class="panel panel-inverse" style="">
                <div class="panel-heading">
                    <h3 class="panel-title"><spring:message code='work.add.information' /></h3>
                </div>
                <div class="panel-body">
                    <form id="myform" class="form-inline" action="">
                        <table class="table mb-0">
                            <tbody>
                                <tr>
                                    <th style="background: #f0f4f6;"><spring:message code='dayproject.dayproject.now'/></th>
                                    <td class="currency">${infoProject.nowPrice}</td>
                                    <th style="background: #f0f4f6;" class="priority-hide"><spring:message code='dayproject.dayproject.first'/></th>
                                    <td class="currency priority-hide">${infoProject.firstPrice}</td>
                                    <th style="background: #f0f4f6;" class="priority-hide"><spring:message code='dayproject.dayproject.other'/></th>
                                    <td class="currency priority-hide">${infoProject.addSum}</td>
                                    <th style="background: #f0f4f6;" class="priority-hide"><spring:message code='dayproject.dayproject.spent'/></th>
                                    <td class="currency priority-hide">${infoProject.payment}</td>
                                    <th style="background: #f0f4f6;"><spring:message code='dayproject.dayproject.remain'/></th>
                                    <td class="currency">${infoProject.remain}</td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="project-function">
        <c:if test="${projectMainDTO.projectStatus=='N'}">
            <c:if test="${emp.adminYn=='Y'}">
                <button type="button" class="btn btn-primary function-button" onclick="openModel()"><spring:message code='dayproject.dayproject.deletespent'/></button>
                <button type="button" class="btn btn-primary function-button" onclick="addDayProject()"><spring:message code='dayproject.dayproject.registerspent'/></button>
                <button type="button" class="btn btn-primary function-button priority-hide" onclick="editDayProject()"><spring:message code='dayproject.dayproject.editspent'/></button>
            </c:if>
            <c:if test="${projectLeader==emp.empCd || emp.adminYn=='Y'}">
            </c:if>
            <c:if test="${accountingLeader==emp.empCd || emp.adminYn=='Y'}">
                <button type="button" class="btn btn-primary function-button priority-hide" onclick="addDepositDayProject()"><spring:message code='dayproject.dayproject.addbudget'/></button>
                <button type="button" class="btn btn-primary function-button priority-hide" onclick="approve()"><spring:message code='dayproject.dayproject.finalapprove'/></button>
            </c:if>
        </c:if>
        
        <button type="button" class="btn btn-info function-button priority-hide" onclick="printtag('print-table')"><spring:message code='dayproject.dayproject.print'/></button>
        <button type="button" class="btn btn-warning function-button priority-hide" onclick="exportExcel()"><spring:message code='dayproject.dayproject.exportexcel'/></button>
    </div>
    <div class="m-table">
        <table id="projecttable" class="table table-striped table-bordered w-100 text-center">
            <thead>
                <tr style="background-color: #1c5691;color: white">
                    <th style="vertical-align: inherit;"><input type="checkbox" name="all" id="checkall"/></th>
                    <th style="vertical-align: inherit;" class="desktop"><spring:message code='work.list.stt'/></th>
                    <th style="vertical-align: inherit;"><spring:message code='dayproject.dayproject.spentstatus'/></th>
                    <th style="vertical-align: inherit;width: 13%;"><spring:message code='dayproject.dayproject.projectname'/></th>
                    <th style="vertical-align: inherit;"><spring:message code='dayproject.dayproject.daytitle'/></th>
                    <th style="vertical-align: inherit;" class="desktop"><spring:message code='dayproject.dayproject.dept'/></th>
                    <th style="vertical-align: inherit;" class="desktop"><spring:message code='dayproject.dayproject.position'/></th>
                    <th style="vertical-align: inherit;"><spring:message code='dayproject.dayproject.pic'/></th>
                    <th  style="vertical-align: inherit;" class="desktop"><spring:message code='dayproject.dayproject.advanceamount'/></th>
                    <th style="vertical-align: inherit;" class="desktop"><spring:message code='dayproject.dayproject.itemnumber'/></th>
                    <th style="vertical-align: inherit;" class="desktop"><spring:message code='dayproject.dayproject.projectprice'/></th>
                    <th style="vertical-align: inherit;" class="desktop"><spring:message code='dayproject.dayproject.remainamount'/></th>
                    <th style="width: 5%;vertical-align: inherit;" class="desktop"><spring:message code='dayproject.dayproject.createdate'/></th>
                    <th style="vertical-align: inherit;" class="desktop"><spring:message code='dayproject.dayproject.approve'/></th>
                    <th style="vertical-align: inherit; width: 69px;" class="desktop"><spring:message code='dayproject.dayproject.translation'/></th>
                    <th style="vertical-align: inherit;"><spring:message code='dayproject.dayproject.function'/></th>
                </tr>
            </thead>
        </table>
    </div>

    <div id="print-table">
        <div class="fixed-header">
            <div class="print-header">
                <h3 id="printHeaderTitle"><spring:message code='project.list.project'/></h3>
            </div>
            <div class="print-time-body">
                <h6 id="printTimeBody"></h6>
            </div>
            <div class="print-title-bottom">
                <h6 id="printHeaderBottom"><spring:message code='project.list.processing'/></h6>
            </div>
        </div>
        
        <table id="printTable" class="table table-striped w-100 text-center">
            <thead>
                <td class="child-td-table-print"></td>
                <tr class="tr-print-table">
                    <th class="attributes-hide" style="vertical-align: inherit;"><spring:message code='work.list.stt'/></th>
                    <th style="vertical-align: inherit;"><spring:message code='work.list.stt'/></th>
                    <th style="width: 8%;vertical-align: inherit;"><spring:message code='dayproject.dayproject.spentstatus'/></th>
                    <th style="vertical-align: inherit; width: 13%;"><spring:message code='dayproject.dayproject.daytitle'/></th>
                    <th style="vertical-align: inherit;"><spring:message code='dayproject.dayproject.advanceamount'/></th>
                    <th style="vertical-align: inherit;"><spring:message code='dayproject.dayproject.itemnumber'/></th>
                    <th style="vertical-align: inherit;"><spring:message code='dayproject.dayproject.projectprice'/></th>
                    <th style="vertical-align: inherit;"><spring:message code='dayproject.dayproject.remainamount'/></th>
                    <th style="width: 9%;vertical-align: inherit;"><spring:message code='dayproject.dayproject.createdate'/></th>
                    <th style="vertical-align: inherit;"><spring:message code='dayproject.dayproject.approve'/></th>
                    <th style="vertical-align: inherit;"><spring:message code='dayproject.dayproject.translation'/></th>
                </tr>
            </thead>
        </table>
    </div>

    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel"><spring:message code='confirm.delete' /></h5>
                    <input type="text" hidden="" name="" id="giatri"/>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code='translation.close' />
                    </button>
                    <button type="button" class="btn btn-primary" onclick="xoaW()"><spring:message code='work.list.delete' />
                    </button>
                </div>
            </div>
        </div>
    </div>

</div>
<script>
    function format(d) {
        var disable='';
        var count=0;
        var dpId=d.dpId;
        var str='';
        var empCd='${empCd}';
        var translationYn='${translationYn}';
        var settings = {
            "url": "/API/dayProject/getTranDayProject",
            "method": "POST",
            "async": false,
            "headers": {
                "Content-Type": "application/json"
            },
            "data": JSON.stringify({
                dpId: dpId
            }),
        };
        $.ajax(settings).done(function (response) {
            for(var i=0;i<response.length;i++) {
                var enableTran='disabled';
                if(empCd==response[i].empCd){
                    enableTran='';
                }
                str +='<tr>' +
                    '<td>'+response[i].rownum+'</td>' +
                    '<td>'+response[i].langName+'</td>' +
                    '<td>'+response[i].empName+'</td>' +
                    '<td>'+response[i].title+'</td>' +
                    '<td>'+response[i].contents+'</td>' +
                    '<td>'+response[i].regDt+'</td>' +
                    '<td><button type="button" class="btn btn-danger" '+enableTran+' onclick="editTran('+dpId+','+response[i].translationId+')" ><spring:message code="work.detail.edit" /></button></td>' +
                    '<td><button type="button" class="btn btn-danger" '+enableTran+' onclick="delTran('+response[i].translationId+')"><spring:message code="work.detail.delete" /></button></td>' +
                    '</tr>';
                count++;
            }

        });
        if(count>0){disable='display: none;';}
        if(translationYn =="N"){disable='display: none;';}
        return (
            '<button type="button" class="btn btn-primary" onclick="addTran('+dpId+')" style="float: left;'+disable+'"><spring:message code="work.detail.addTran" /></button><div style="clear: both"></div>'+
            '<table id="translationtb'+dpId+'" cellpadding="5" cellspacing="0" border="0" class="table table-aqua table-booking-body" style="width: 100%;">' +
            '<thead>' +
            '<tr style="background-color:yellowgreen;">' +
            '<th><spring:message code="work.detail.stt" /></th>' +
            '<th><spring:message code="work.detail.language" /></th>' +
            '<th><spring:message code="work.detail.empName" /></th>' +
            '<th><spring:message code="work.detail.title" /></th>' +
            '<th><spring:message code="work.detail.contents" /></th>' +
            '<th><spring:message code="work.detail.registerDate" /></th>' +
            '<th><spring:message code="work.add.edit" /></th>' +
            '<th><spring:message code="work.detail.workfunction" /></th>' +
            '</tr>' +
            '</thead>' +
            '<tbody>' +
            str+
            '</tbody>' +
            '</table>'
        );
    }
    $(document).ready(function (){
        var lang_dt;
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
        var table = $('#projecttable').DataTable({
            'displayStart': '<%=displayStartPra%>',
            "dom": '<"toolbar">frtip',
            "order": [[1, "desc"]],
            'iDisplayLength': 10,
            "lengthChange": false, "autoWidth": false,
            "language": lang_dt,
            "bProcessing": true,
            "bServerSide": true,
            "bStateSave": false,
            "responsive": true,
            "iDisplayStart": 0,
            "fnDrawCallback": function () {
            },
            "sAjaxSource": "/API/project/getDayProjectDatatables?pjId=${pjId}&start_date=" + $('#start_date').val() + "&end_date=" + $('#end_date').val() + "&levelDept=" + $("#levelDept").val() + "&translationStatus=" + $('#translationStatus').val() + "&dayProjectStatus=" + $('#dayProjectStatus').val() + "&optionSearch=" + $('#optionSearch').val() + "&inputSearch=" + $('#inputSearch').val() + "&type=1",
            "columns": [
                {
                    "data": "dpId",
                    render: function (data, type, full) {
                        return ' <input class="cb-element" name="xoa" type="checkbox" value="'
                            + data + '" >';
                    }
                },
                {
                    "className":'priority-hide',
                    "data": "rownum"
                }
                , {
                    "data": "spentType"
                }, {
                    "data": "projectName"
                }, {
                    "data": "title"
                }, {
                    "className":'priority-hide',
                    "data": "deptName"
                }, {
                    "className":'priority-hide',
                    "data": "positionName"
                }, {
                    "className":'priority-hide',
                    "data": "empName"
                }, {
                    "className":'priority-hide',
                    "data": "advanceAmount"
                },
                {
                    "className":'priority-hide',
                    "data": "numOfItem"
                },
                {
                    "className":'priority-hide',
                    "data": "projectPrice"
                },
                {
                    "className":'priority-hide',
                    "data": "remainAmount"
                },{
                    "className":'priority-hide',
                    "data": "regDt"
                },{
                    "className":'priority-hide',
                    "data": "dayProjectStatus"
                },{

                    "className":'details-control priority-hide',
                    "data": "translationStatus"
                }
            ],
            "fnRowCallback": function (nRow, aData, iDisplayIndex) {
                if (aData["translationStatus"] >0) {
                    $("td:eq(14)", nRow).html('<br><br><spring:message code="work.detail.finish" />');
                } else {
                    $("td:eq(14)", nRow).html('<br><br><spring:message code="work.detail.notfinish" />');
                }

                if (aData["dayProjectStatus"] == 'Y') {
                    $("td:eq(13)", nRow).html('<spring:message code="project.type.approved" />');
                } else {
                    $("td:eq(13)", nRow).html('<spring:message code="project.type.not.approve" />');
                }
            },
            "aoColumnDefs": [
                {
                    "aTargets": [15],
                    "mData": "dpId",
                    "mRender": function (data, type, full) {
                        if(full.spentType =="spent" || full.spentType =="예산지출" || full.spentType =="Chi tiêu"){
                            return '<button type="button" onclick="detail(' + data + ')" class="btn btn-success"  ><spring:message code="work.list.viewdetail" /></button>';
                        }else{
                            return '<i class="fa fa-star" style="font-size: 18px; color: #e0dc58;" onclick="detail(' + data + ')"></i>';
                        }
                    }
                },
                {"orderable": false, "targets": 0},
                {
                    "aTargets": [8],
                    "mData": "advanceAmount",
                    render: $.fn.dataTable.render.number( ',', '.', 0)
                },
                {
                    "aTargets": [10],
                    "mData": "projectPrice",
                    render: $.fn.dataTable.render.number( ',', '.', 0)
                },
                {
                    "aTargets": [11],
                    "mData": "remainAmount",
                    render: $.fn.dataTable.render.number( ',', '.', 0)
                }
            ]
        });

        if ('${title}' != '') {
            if ('${title}' == 'success') {
                toastr.success('', "<spring:message code='notification.index.success' />", {timeOut: 1000});
            } else {
                toastr.error('', "<spring:message code='notification.index.error' />", {timeOut: 1000});
            }
        }

        $('#projecttable tbody').on('click', 'td.details-control', function () {
            var tr = $(this).closest('tr');
            var row = table.row( tr );

            if ( row.child.isShown() ) {
                // this row is already open - close it
                row.child.hide();
                tr.removeClass('shown');
                var shownRowsCount = table.rows( '.shown' ).count();
                if (shownRowsCount) {
                    $(".close-child-rows").removeClass("disable-button");
                } else {
                    $(".close-child-rows").addClass("disable-button");
                }
            } else {
                // open this row
                row.child( format(row.data()) ).show();
                var id1=row.data().dpId;
                tr.addClass('shown');
                $("#translationtb"+id1).dataTable({
                    "bLengthChange": false,
                    "bFilter": false,
                    "bInfo": false,
                    "language": lang_dt,
                });
                // enable "close all expanded rows" btn
                $(".close-child-rows").removeClass("disable-button");
            }

        });


        $('#projecttable').on( 'page.dt', function () {
            var str=paramSearch();
            location.href = "/project/project/dayproject.hs?pjId=${pjId}&"+str+"&<%=backSearch%>";
        } );

    });

    function detail(dpId) {
        var str=paramSearch();
        location.href = "/project/project/detail.hs?pjId=${pjId}&dpId="+dpId+"&"+str+"&<%=backSearch%>";
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
        location.href = "/project/project/dayproject.hs?pjId=${pjId}&start_date=" + $('#start_date').val() + "&end_date=" + $('#end_date').val() + "&levelDept=" + $("#levelDept").val() + "&translationStatus=" + $('#translationStatus').val() + "&dayProjectStatus=" + $('#dayProjectStatus').val() + "&optionSearch=" + $('#optionSearch').val() + "&inputSearch=" + $('#inputSearch').val() + "&displayStartPra=" + displayStartPra+"&<%=backSearch%>";
    }
    function back() {
        location.href = "/project/project/list.hs?<%=backSearch%>";
    }
    function addDayProject(){
        var rs="${countDefault}";
        if(parseInt(rs)<=0){
            alert("<spring:message code='project.alert.addbudgetfirst' /> !");
        }else{
            var str=paramSearch();
            location.href="/project/project/dayProjectAdd.hs?pjId=${pjId}&"+str+"&<%=backSearch%>";
        }

    }

    function addDepositDayProject(){
        var str=paramSearch();
        location.href="/project/project/dayProjectAddDeposit.hs?pjId=${pjId}&"+str+"&<%=backSearch%>";
    }

    function editDayProject(){
        var i=0;
        var lstChecked = "";
        var myTable = $('#projecttable').dataTable();
        var rowcollection = myTable.$('input[name="xoa"]:checked', {"page": "all"});
        rowcollection.each(function (index, elem) {
            var checkbox_value = $(elem).val();
            lstChecked += checkbox_value + ",";
            i++;
        });
        lstChecked=lstChecked.substring(0, lstChecked.length - 1);
        if (lstChecked.trim() == "") {
            alert("<spring:message code='notice.select.message' />");
        }else if(lstChecked.trim() != "" && i>1){
            alert("<spring:message code='project.alert.selectonecell' />");
        } else {
            var settings = {
                "url": "/API/dayproject/selectInfoDayProjectByID",
                "method": "POST",
                "headers": {
                    "Content-Type": "application/json"
                },
                "data": JSON.stringify({
                    dpId: lstChecked
                }),
            };
            $.ajax(settings).done(function (response) {
                if(response.spentType =="spent" || response.spentType =="예산지출" || response.spentType =="Chi tiêu"){
                    //spent
                    var str=paramSearch();
                    location.href="/project/project/dayProjectEditSpent.hs?pjId=${pjId}&dpId="+lstChecked+"&"+str+"&<%=backSearch%>";
                }else{
                    //default & deposit
                    var str=paramSearch();
                    location.href="/project/project/dayProjectEditDeposit.hs?pjId=${pjId}&dpId="+lstChecked+"&"+str+"&<%=backSearch%>";
                }
            });

        }
    }

    function openModel() {
        var lstChecked = "";
        var myTable = $('#projecttable').dataTable();
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
    };
    function xoaW() {
        var lstChecked = "";
        var myTable = $('#projecttable').dataTable();
        var rowcollection = myTable.$('input[name="xoa"]:checked', {"page": "all"});
        rowcollection.each(function (index, elem) {
            var checkbox_value = $(elem).val();
            lstChecked += checkbox_value + ",";
        });

        if (lstChecked.trim() == "") {
            toastr['error']('Delete error !');
        } else {
            var settings = {
                "url": "/API/dayproject/deleteDayProject",
                "method": "POST",
                "async":false,
                "headers": {
                    "Content-Type": "application/json"
                },
                "data": JSON.stringify({
                    inputSearchSql: lstChecked
                }),
            };
            $.ajax(settings).done(function (response) {
                console.log(response)
                if(response == '5')
                {
                    document.getElementById("exampleModal").style.display = "none";
                    location.href = "/project/project/dayproject.hs?pjId=${pjId}& sucess&<%=backSearch%>";
                    alert("<spring:message code='dayproject.delete.role' />");
                }
                else{
                    location.href = "/project/project/dayproject.hs?pjId=${pjId}&title=success&message=delete sucess&<%=backSearch%>";
                }
            });
        }
    }

    function approve() {
        var lstChecked = "";
        var myTable = $('#projecttable').dataTable();
        var rowcollection = myTable.$('input[name="xoa"]:checked', {"page": "all"});
        rowcollection.each(function (index, elem) {
            var checkbox_value = $(elem).val();
            lstChecked += checkbox_value + ",";
        });

        if (lstChecked.trim() == "") {
            alert("<spring:message code='notice.select.message' />");
        } else {
            if(confirm('<spring:message code="project.list.confirm" />')){
                var settings = {
                    "url": "/API/dayproject/approveDayProject",
                    "method": "POST",
                    "headers": {
                        "Content-Type": "application/json"
                    },
                    "data": JSON.stringify({
                        inputSearchSql: lstChecked
                    }),
                };
                $.ajax(settings).done(function (response) {
                    location.href = "/project/project/dayproject.hs?pjId=${pjId}&title=success&message=sucess&<%=backSearch%>";
                });
            }
        }
    }

    function addTran(dpId){
        var str=paramSearch();
        location.href="/project/project/addTran.hs?id="+dpId+"&type=dp"+"&pjId=${pjId}&"+str+"&<%=backSearch%>";
    }
    function editTran(dpId,transId){
        var str=paramSearch();
        location.href="/project/project/editTran.hs?id="+dpId+"&type=dp"+"&pjId=${pjId}&translationId="+transId+"&"+str+"&<%=backSearch%>";
    }

    function delTran(id) {
        $.ajax({
            type: "POST",
            url: "/work/work/xoaTran",
            data: {
                id: id,
            },
            success: function (response) {
                var str=paramSearch();
                location.href = "/project/project/dayproject.hs?pjId=${pjId}&title=success&message=Delete sucess&"+str+"&<%=backSearch%>";
            },
            error: function (error) {
                toastr['error']('error !');
            }
        });
    }

    let x = document.querySelectorAll(".currency");
    for (let i = 0, len = x.length; i < len; i++) {
        let num = Number(x[i].innerHTML).toLocaleString('en');
        x[i].innerHTML = num;
        x[i].classList.add("currSign");
    }

    function exportExcel(){
        $.ajax({
            type: "GET",
            url: "/API/dayproject/exportexcel?start_date="+$('#start_date').val()+"&end_date="+$('#end_date').val()+"&levelDept="+$("#levelDept").val()+"&translationStatus="+$('#translationStatus').val() + "&optionSearch="+$('#optionSearch').val()+"&inputSearch="+$('#inputSearch').val()+ "&pjId=${pjId}" + "&dayProjectStatus=" + $('#dayProjectStatus').val() +"&iDisplayStart=0&iDisplayLength=100000&sSearch=&iSortCol_0=&sSortDir_0=desc",
            data: JSON.stringify(),
            success: function (response) {
                location.href = "/resources/Upload/excel_file/"+response;
            },
            error: function (error) {
                alert(error)
            }
        })
    }

    function printtag(tagid){
        var start_date = $("#start_date").datepicker({dateFormat: 'yy-mm-dd'});
        var end_date = $("#end_date").datepicker({dateFormat: 'yy-mm-dd'});
        var lang_dt;
        if ('${_lang}' == 'ko') {
            lang_dt = lang_kor;
        } else if ('${_lang}' == 'en') {
            lang_dt = lang_en;
        } else {
            lang_dt = lang_vt;
        }

        var startTime = $("#start_date").val();
        var endTime = $("#end_date").val();
        if (startTime == "") {
            startTime = '2022-01-01';
            endTime = '${timeNow}';
        }

        var strTimeBody = "<spring:message code='project.list.search'/>" + ": " + startTime.substring(0, 4) + " <spring:message code='project.list.year'/> "+
        +  startTime.substring(5, 7) + " <spring:message code='project.list.month'/> " + startTime.substring(8, 10) + " <spring:message code='project.list.day'/> ~ " + endTime.substring(0, 4) + " <spring:message code='project.list.year'/> " + endTime.substring(5, 7) +  " <spring:message code='project.list.month'/> " + endTime.substring(8, 10) + " <spring:message code='project.list.day'/> ";

        var nameProject = "(" + '${nameProject}'+ ")" + " <spring:message code='dayproject.dayproject.exceltitle'/> ";

        var nameLeader = " <spring:message code='project.add.pic'/> " + ": " + '${leaderProjectname}' + " - " + " <spring:message code='project.list.pic.accounting'/> " + ": " + '${leaderAccountingName}';

        document.getElementById("printTimeBody").innerHTML = strTimeBody;
        document.getElementById("printHeaderTitle").innerHTML = nameProject;
        document.getElementById("printHeaderBottom").innerHTML = nameLeader;
        
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
            "sAjaxSource": "/API/project/getDayProjectDatatables?pjId=${pjId}&start_date=" + $('#start_date').val() + "&end_date=" + $('#end_date').val() + "&levelDept=" + $("#levelDept").val() + "&translationStatus=" + $('#translationStatus').val() + "&dayProjectStatus=" + $('#dayProjectStatus').val() + "&optionSearch=" + $('#optionSearch').val() + "&inputSearch=" + $('#inputSearch').val() + "&type=2",
            "columns": [
                {
                    "className":'attributes-hide',
                    "data": "rownum"
                }
                ,{
                    "data": "type"
                }
                , {
                    "data": "spentType"
                }, {
                    "data": "title"
                }, {
                    "data": "advanceAmount"
                },
                {
                    "data": "numOfItem"
                },
                {
                    "data": "projectPrice"
                },
                {
                    "data": "remainAmount"
                },{
                    "data": "regDt",
                    render: function(data, type, full){
                        return data.slice(0,10);
                    }
                },{
                    "data": "dayProjectStatus"
                },{
                    "data": "translationStatus"
                }
            ],
            
            "fnRowCallback": function (nRow, aData, iDisplayIndex) {
                if (aData["translationStatus"] >0) {
                    $("td:eq(10)", nRow).html('<spring:message code="work.detail.finish" />');
                } else {
                    $("td:eq(10)", nRow).html('<spring:message code="work.detail.notfinish" />');
                }

                if (aData["dayProjectStatus"] == 'Y') {
                    $("td:eq(9)", nRow).html('<spring:message code="project.type.approved" />');
                } else {
                    $("td:eq(9)", nRow).html('<spring:message code="project.type.not.approve" />');
                }

                if (aData["type"] == 'translate') {
                    $("td:eq(1)", nRow).html('<spring:message code="emp.profile.translation" />');
                    $("td:eq(4)", nRow).html('');
                    $("td:eq(5)", nRow).html('');
                    $("td:eq(6)", nRow).html('');
                    $("td:eq(7)", nRow).html('');
                    $("td:eq(8)", nRow).html('');
                    $("td:eq(9)", nRow).html('');
                    $("td:eq(10)", nRow).html('');
                }
            },
            "aoColumnDefs": [
                    
                {"orderable": false, "targets": 0},
                {"orderable": false, "targets": 1},
                {"orderable": false, "targets": 2},
                {"orderable": false, "targets": 3},
                {"orderable": false, "targets": 4},
                {"orderable": false, "targets": 5},
                {"orderable": false, "targets": 6},
                {"orderable": false, "targets": 7},
                {"orderable": false, "targets": 8},
                {"orderable": false, "targets": 9},
                {"orderable": false, "targets": 10},
                {
                    "aTargets": [4],
                    "mData": "advanceAmount",
                    render: $.fn.dataTable.render.number( ',', '.', 0)
                },
                {
                    "aTargets": [6],
                    "mData": "projectPrice",
                    render: $.fn.dataTable.render.number( ',', '.', 0)
                },
                {
                    "aTargets": [7],
                    "mData": "remainAmount",
                    render: $.fn.dataTable.render.number( ',', '.', 0)
                }

            ]
        });
        table.on( 'draw', function () {
            mainPrinTable(tagid);
            var str=paramSearch();
            location.href = "/project/project/dayproject.hs?pjId=${pjId}&"+str+"&<%=backSearch%>";
        } );
    }

    function mainPrinTable (tagid){
        var hashid = "#"+ tagid;
        var tagname =  $(hashid).prop("tagName").toLowerCase() ;
        var attributes = ""; 
        var attrs = document.getElementById(tagid).attributes;
        $.each(attrs,function(i,elem){
            attributes +=  " "+  elem.name+" ='"+elem.value+"' " ;
        })
        var divToPrint= $(hashid).html() ;
        var head = '<html>';
            head += '<link href="https://fonts.googleapis.com/css?family=Roboto:100,300,400,500,700,900" rel="stylesheet" />';
            head += '<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" />';
            head += '<link href="${_ctx}/resources/css/default/styles.css" rel="stylesheet" />';
            head += '<link href="${_ctx}/resources/css/common.css" rel="stylesheet" />';
            head += '<link href="${_ctx}/resources/css/project/day-project.css" rel="stylesheet" />';

        var allcontent = head + "<body  onload='window.print()' >" + "<" + tagname + attributes + ">" +  divToPrint + "</" + tagname + ">" +  "</body></html>"  ;
        var newWin= window.open('','Print-Window');
        //newWin.document.open();
        newWin.document.write(allcontent);
        newWin.document.close();
        //setTimeout(function(){newWin.close();},10);
    }

    function paramSearch(){
        var displayStartPra = $('#projecttable').DataTable().page.info().page * 10;
        var str="start_date=" + $('#start_date').val() + "&end_date=" + $('#end_date').val() + "&levelDept=" + $("#levelDept").val() + "&translationStatus=" + $('#translationStatus').val() + "&approval=" + $('#approval').val() + "&optionSearch=" + $('#optionSearch').val() + "&inputSearch=" + $('#inputSearch').val() + "&displayStartPra=" + displayStartPra;
        return str;
    }
</script>