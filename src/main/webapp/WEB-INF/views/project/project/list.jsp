<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="kr.co.hs.emp.dto.EmpDTO" %>
<%@ page import="kr.co.hs.common.security.UserDetail" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

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
%>
<style>
    .table .odd,
    .even {
        height: 75px;
    }
    
    .form-inline td {
        vertical-align: inherit;
    }
    
    .project-function {
        float: right;
        margin-bottom: 5px;
    }
    
    .dataTables_filter {
        display: none !important;
    }
    
    .project-function button {
        margin-top: 5px;
    }
    
    .my-custom-scrollbar {
        position: relative;
        height: auto;
        overflow: auto;
    }
    
    .tr-print-table {
        background-color: #1c5691;
        color: white
    }
    
    #printTable thead tr th.sorting_desc:after {
        display: none !important;
    }
    
    #print-table {
        display: none;
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
    /*the container must be positioned relative:*/
    
    .autocomplete {
        position: relative;
        display: inline-block;
    }
    
    input[type=text] {
        width: 100%;
    }
    
    .autocomplete-items {
        position: absolute;
        border: 1px solid #d4d4d4;
        border-bottom: none;
        border-top: none;
        z-index: 99;
        /*position the autocomplete items to be the same width as the container:*/
        top: 100%;
        left: 0;
        right: 0;
    }
    
    .autocomplete-items div {
        padding: 10px;
        cursor: pointer;
        background-color: #fff;
        border-top: 1px solid #d4d4d4;
        border-bottom: 1px solid #d4d4d4;
    }
    /*when hovering an item:*/
    
    .autocomplete-items div:hover {
        background-color: #e9e9e9;
    }
    /*when navigating through the items using the arrow keys:*/
    
    .autocomplete-active {
        background-color: DodgerBlue !important;
        color: #ffffff;
    }
    
    #text,
    #textAccounting {
        display: none;
        color: red
    }
    
    @media (max-width: 1000px) {
        .page-header {
            font-size: 20px;
        }
        .priority-hide {
            display: none;
        }
        .titleseach {
            width: 26% !important;
        }
        #projecttable th:nth-child(3) {
            width: 30% !important;
        }
        #projecttable th:nth-child(9) {
            width: 20% !important;
        }
    }
</style>
    <tiles:importAttribute name="empAuthList" />
    <div class="content" id="content">
        <h1 class="page-header cursor-default">
            <spring:message code='project.index' /> -
            <spring:message code='project.title' /> <br>
            <small><spring:message code='project.smalltitle'/></small>
        </h1>
        <div class="row">
            <div class="col-xl-12 ui-sortable">
                <div class="panel panel-inverse">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            <spring:message code='search.title' />
                        </h3>
                    </div>
                    <div class="panel-body">
                        <form id="myform" class="form-inline" action="">
                            <table class="table mb-0">
                                <tbody>

                                    <tr class="priority-hide">
                                        <td style="background: #f0f4f6;width: 10%;font-weight: bold">
                                            <spring:message code='project.search.time' />
                                        </td>

                                        <td style="width: 40%" class="priority-hide">
                                            <input readonly id="start_date" name="start_date" class="form-control form-control-sm mr-1 bg-white" type="text" value="<%=start_date1%>">~
                                            <input readonly id="end_date" name="end_date" class="form-control form-control-sm mr-1 bg-white" type="text" value="<%=end_date1%>">
                                            <br><br>
                                            <a class="time-order btn-default form-control-sm mr-1" href="#" onclick="DateChange('today')">
                                                <spring:message code='search.button.daychoose.inday' />
                                            </a>
                                            <a class="time-order btn-default form-control-sm mr-1" href="#" onclick="DateChange('oneweek')">
                                                <spring:message code='search.button.daychoose.inweek' />
                                            </a>
                                            <a class="time-order btn-default form-control-sm mr-1" href="#" onclick="DateChange('onemonth')">
                                                <spring:message code='search.button.daychoose.inmonth' />
                                            </a>
                                            <a class="time-order btn-default form-control-sm mr-1" href="#" onclick="DateChange('threemonth')">
                                                <spring:message code='search.button.daychoose.in3month' />
                                            </a>
                                            <a class="time-order btn-default form-control-sm mr-1" href="#" onclick="DateChange('sixmonth')">
                                                <spring:message code='search.button.daychoose.in6month' />
                                            </a>
                                            <a class="time-order btn-default form-control-sm mr-1" href="#" onclick="DateChange('oneyear')">
                                                <spring:message code='search.button.daychoose.in1year' />
                                            </a>
                                            <a class="time-order btn-default form-control-sm mr-1" href="#" onclick="DateChange('all')">
                                                <spring:message code='search.button.daychoose.inall' />
                                            </a>
                                        </td>

                                        <td style="background: #f0f4f6;width: 10%;font-weight: bold" class="priority-hide">
                                            <spring:message code='work.list.dept' />
                                        </td>
                                        <td>
                                            <select style="width: 80%;" id="levelDept" name="levelDept" class="form-control">
                                                <c:forEach items="${lstDeptLevel}" var="lst">
                                                    <option value="${lst.deptCd}" ${lst.deptCd==levelDeptGet1 ? "selected" : "" }>${lst.deptName}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                    </tr>

                                    <tr class="priority-hide">
                                        <td style="background: #f0f4f6;width: 10%;font-weight: bold">
                                            <spring:message code='project.search.approve' />
                                        </td>
                                        <td style="width: 35%">
                                            <select id="approval" name="approval" class="custom-select custom-select-sm form-control form-control-sm mr-1">
                                                <option><spring:message code='search.selectoption.first'/></option>
                                                <option value="Y" ${approval1.equals("Y") ? "selected" : "" }><spring:message code='project.search.approveY'/></option>
                                                <option value="N" ${approval1.equals("N") ? "selected" : "" }><spring:message code='project.search.approveN'/></option>
                                        </select>
                                        </td>
                                        <td style="background: #f0f4f6;width: 10%;font-weight: bold">
                                            <spring:message code='work.list.translationstatus' />
                                        </td>
                                        <td style="width: 35%">
                                            <select id="translationStatus" name="translationStatus" class="custom-select custom-select-sm form-control form-control-sm mr-1">
                                                <option><spring:message code='search.selectoption.first'/></option>
                                                <option value="Y" ${translationStatusGet1.equals("Y")? "selected" : "" }><spring:message code='work.detail.finish'/></option>
                                                <option value="N" ${translationStatusGet1.equals("N")? "selected" : "" }><spring:message code='work.detail.notfinish'/></option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="background: #f0f4f6;width: 10%;font-weight: bold" class="titleseach">
                                            <spring:message code='project.search.keysearch' />
                                        </td>
                                        <td colspan="3">
                                            <select id="optionSearch" name="optionSearch" class="custom-select custom-select-sm form-control form-control-sm mr-1">
                                                <option value="title" ${optionSearchGet1.equals("title") ? "selected" : "" }><spring:message code='project.list.nameproject'/></option>
                                                <option value="emp_name" ${optionSearchGet1.equals("emp_name") ? "selected" : "" }><spring:message code='dateboard.notice.company.empname'/></option>
                                                <option value="position_name" ${optionSearchGet1.equals("position_name") ? "selected" : "" }><spring:message code='work.list.position'/></option>
                                            </select>
                                            <input id="inputSearch" name="inputSearch" class="form-control form-control-sm mr-1" type="text" value="<%=inputSearchGet1%>">

                                            <button type="button" class="btn btn-primary btn-sm" onclick="searchMethod()"><spring:message code='dateboard.notice.company.timkiem'/></button>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div class="project-function">
            <c:choose>
                <c:when test="${empDTO.adminYn=='Y'}">
                    <button type="button" class="priority-hide btn btn-primary function-button" onclick="addProjectAuth()"><spring:message code='project.list.autho'/></button>
                </c:when>
            </c:choose>
            <button type="button" class="btn btn-primary function-button" onclick="createProject()"><spring:message code='project.list.registerproject'/></button>
            <button type="button" class="btn btn-primary function-button priority-hide" onclick="deleteProject()"><spring:message code='project.list.deleteproject'/></button>
            <c:if test="${approvalPermision==1}">
                <button type="button" class="btn btn-primary function-button priority-hide" onclick="approvalMainProject()"><spring:message code='project.list.finalconfirm'/></button>
            </c:if>
            <button type="button" class="priority-hide btn btn-primary function-button" onclick="historyLeaderShow()"><spring:message code='project.list.history'/></button>

            <button type="button" class="btn btn-info function-button priority-hide" onclick="printtag('print-table')"><spring:message code='project.list.print'/></button>
            <button type="button" class="btn btn-warning function-button priority-hide" onclick="exportExcel()"><spring:message code='project.list.exportexcel'/></button>
        </div>
        
        <div class="m-table">

            <table id="projecttable" class="table table-striped table-bordered w-100 text-center responsive ">

                <thead>
                    <tr class="text-center" style="background-color: #1c5691;color: white">
                        <th style="vertical-align: inherit;"><input type="checkbox" name="all" id="checkall" /></th>
                        <th style="vertical-align: inherit;" class="desktop"><spring:message code='work.list.stt' /></th>
                        <th style="vertical-align: inherit; width: 13%;"><spring:message code='project.list.nameproject' /></th>
                        <th style="vertical-align: inherit;" class="desktop"><spring:message code='work.list.dept' /></th>
                        <th style="vertical-align: inherit;"><spring:message code='project.list.pic' /></th>
                        <th style="vertical-align: inherit;"><spring:message code='project.add.picaccounting' /></th>
                        <th style="vertical-align: inherit;" class="desktop"><spring:message code='project.list.advancemoney' /></th>
                        <th style="vertical-align: inherit;" class="desktop"><spring:message code='project.list.usagemoney' /></th>
                        <th style="vertical-align: inherit;"><spring:message code='project.list.cashinreturn' /></th>
                        <th style="vertical-align: inherit;" class="desktop"><spring:message code='project.list.approve' /></th>
                        <th style="vertical-align: inherit;" class="desktop"><spring:message code='dayproject.dayproject.translation' /></th>
                        <th style="vertical-align: inherit;" class="desktop"><spring:message code='project.list.status' /></th>
                        <th style="vertical-align: inherit;"><spring:message code='work.add.date' /></th>
                        <th style="vertical-align: inherit; width: 82px;"><spring:message code='work.list.function' /></th>
                    </tr>
                </thead>
            </table>
        </div>

        <div id="print-table">
            <div class="fixed-header">
                <div class="print-header">
                    <h3><spring:message code='project.list.project' /></h3>
                </div>
                <div class="print-time-body">
                    <h6 id="printTimeBody"></h6>
                </div>
            </div>
            <table id="printTable" class="table table-striped w-100 text-center">
                <thead>
                    <td class="child-td-table-print p-0" colspan="16">
                        <h6><p><spring:message code='project.list.processing' /></p></h6>
                    </td>
                    <tr class="tr-print-table text-center">
                        <th class="attributes-hide" style="vertical-align: inherit;"><spring:message code='work.list.stt' /></th>
                        <th style="vertical-align: inherit;"><spring:message code='work.list.stt' /></th>
                        <th style="vertical-align: inherit; width: 8%;"><spring:message code='project.list.nameproject' /></th>
                        <th style="vertical-align: inherit;"><spring:message code='work.list.dept' /></th>
                        <th style="vertical-align: inherit;"><spring:message code='work.list.position' /></th>
                        <th style="vertical-align: inherit;"><spring:message code='project.list.pic' /></th>
                        <th style="vertical-align: inherit;"><spring:message code='work.list.dept' /></th>
                        <th style="vertical-align: inherit;"><spring:message code='work.list.position' /></th>
                        <th style="vertical-align: inherit;"><spring:message code='project.list.pic' /></th>
                        <th style="vertical-align: inherit;"><spring:message code='project.list.advancemoney' /></th>
                        <th style="vertical-align: inherit;"><spring:message code='project.list.usagemoney' /></th>
                        <th style="vertical-align: inherit;"><spring:message code='project.list.cashinreturn' /></th>
                        <th style="vertical-align: inherit;"><spring:message code='project.list.approve' /></th>
                        <th style="vertical-align: inherit;"><spring:message code='work.list.translationstatus' /></th>
                        <th style="vertical-align: inherit;"><spring:message code='project.list.status' /></th>
                        <th style="vertical-align: inherit;" class="priority-hide"><spring:message code='project.list.startdate' /></th>
                        <th style="vertical-align: inherit;" class="priority-hide"><spring:message code='project.list.enddate' /></th>
                    </tr>
                </thead>
            </table>
            <table id="printTableBottom" class="table table-striped w-100 text-center">
                <thead>
                    <td class="child-td-table-print p-0" colspan="16">
                        <h6><p><spring:message code='project.list.finished' /></p></h6>
                    </td>
                    <tr class="tr-print-table text-center">
                        <th class="attributes-hide" style="vertical-align: inherit;"><spring:message code='work.list.stt' /></th>
                        <th style="vertical-align: inherit;"><spring:message code='work.list.stt' /></th>
                        <th style="vertical-align: inherit; width: 8%;"><spring:message code='project.list.nameproject' /></th>
                        <th style="vertical-align: inherit;"><spring:message code='work.list.dept' /></th>
                        <th style="vertical-align: inherit;"><spring:message code='work.list.position' /></th>
                        <th style="vertical-align: inherit;"><spring:message code='project.list.pic' /></th>
                        <th style="vertical-align: inherit;"><spring:message code='work.list.dept' /></th>
                        <th style="vertical-align: inherit;"><spring:message code='work.list.position' /></th>
                        <th style="vertical-align: inherit;"><spring:message code='project.list.pic' /></th>
                        <th style="vertical-align: inherit;"><spring:message code='project.list.advancemoney' /></th>
                        <th style="vertical-align: inherit;"><spring:message code='project.list.usagemoney' /></th>
                        <th style="vertical-align: inherit;"><spring:message code='project.list.cashinreturn' /></th>
                        <th style="vertical-align: inherit;"><spring:message code='project.list.approve' /></th>
                        <th style="vertical-align: inherit;"><spring:message code='work.list.translationstatus' /></th>
                        <th style="vertical-align: inherit;"><spring:message code='project.list.status' /></th>
                        <th style="vertical-align: inherit;" class="priority-hide"><spring:message code='project.list.startdate' /></th>
                        <th style="vertical-align: inherit;" class="priority-hide"><spring:message code='project.list.enddate' /></th>
                    </tr>
                </thead>
            </table>
        </div>
        <div style="padding-right: 12% !important;" class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content" style="width: max-content;">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">
                            <spring:message code='emp.list.information' />
                        </h5>
                        <input type="text" hidden="" name="" id="giatri" />
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body table-wrapper-scroll-y my-custom-scrollbar">
                        <table id="rsatt" class="table table-striped table-bordered text-center">
                            <thead>
                                <tr style="background-color: #1c5691;color: white">
                                    <th><spring:message code='attendance.no' /></th>
                                    <th><spring:message code='project.list.typepic' /></th>
                                    <th><spring:message code='project.list.pic' /></th>
                                    <th><spring:message code='dayproject.dayproject.dept' /></th>
                                    <th><spring:message code='dayproject.dayproject.createdate' /></th>
                                    <th><spring:message code='project.list.createby' /></th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>1</td>
                                    <td>Kế toán</td>
                                    <td>admin</td>
                                    <td>GROUP-GW</td>
                                    <td>2022-02-28 11:11:08</td>
                                    <td>dat</td>
                                </tr>
                            </tbody>

                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code='translation.close'/>
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <div style="padding-right: 12% !important;" class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content" style="width: max-content;">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">
                            <spring:message code='emp.list.information' />
                        </h5>
                        <input type="text" hidden="" name="" id="giatri" />
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body table-wrapper-scroll-y my-custom-scrollbar">
                        <table id="rsatt" class="table table-striped table-bordered text-center">
                            <thead>
                                <tr style="background-color: #1c5691;color: white">
                                    <th><spring:message code='attendance.no' /></th>
                                    <th><spring:message code='project.list.typepic' /></th>
                                    <th><spring:message code='project.list.pic' /></th>
                                    <th><spring:message code='dayproject.dayproject.dept' /></th>
                                    <th><spring:message code='dayproject.dayproject.createdate' /></th>
                                    <th><spring:message code='project.list.createby' /></th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>1</td>
                                    <td>Kế toán</td>
                                    <td>admin</td>
                                    <td>GROUP-GW</td>
                                    <td>2022-02-28 11:11:08</td>
                                    <td>dat</td>
                                </tr>
                            </tbody>

                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code='translation.close'/>
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <div style="padding-right: 12% !important;" class="modal fade" id="modalAddAuth" tabindex="-1" role="dialog" aria-labelledby="modalAddAuthLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content" style="width: 600px; max-height: 700px;">
                    <div class="modal-header">
                        <h5 class="modal-title" id="modalAddAuthLabel">
                            <spring:message code='emp.list.information' />
                        </h5>
                        <input type="text" hidden="" name="" id="projectAuth" />
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>

                    <div class="modal-body table-wrapper-scroll-y my-custom-scrollbar">
                        <div class="header-project-auth">
                            <form autocomplete="off" id="myform" class="form-inline" action="">
                                <div class="autocomplete" style="width: 100%;">
                                    <input style="width: 100%;" class="form-control form-control-sm mr-1" maxlength="200" id="empAuth" type="text" name="empAuth" placeholder="<spring:message code='emp.list.empname' />">
                                    <p id="textAccounting">WARNING! no data</p>
                                </div>
                                <button style="margin-top: 10px; margin-bottom: 10px;" type="button" class="btn btn-success" onclick="addPersonAuth()">
                                    <spring:message code='work.add.addnew' />
                                </button>
                            </form>
                        </div>

                        <table id="projectAuthTable" class="table table-striped table-bordered" style="text-align: center">
                            <thead>
                                <tr style="background-color: #1c5691;color: white">
                                    <th><spring:message code='attendance.no' /></th>
                                    <th><spring:message code='emp.list.name' /></th>
                                    <th><spring:message code='emp.list.department' /></th>
                                    <th><spring:message code='emp.list.position' /></th>
                                    <th><spring:message code='work.list.function' /></th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>1</td>
                                    <td>Kế toán</td>
                                    <td>admin</td>
                                    <td>GROUP-GW</td>
                                    <td>GROUP-GW</td>
                                </tr>
                            </tbody>

                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code='translation.close'/>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
<script>
    function format(d) {
        var disable = '';
        var count = 0;
        var pjId = d.pjId;
        var str = '';
        var empCd = '${empCd}';
        var translationYn='${translationYn}';
        var settings = {
            "url": "/API/Project/getTranProject",
            "method": "POST",
            "async": false,
            "headers": {
                "Content-Type": "application/json"
            },
            "data": JSON.stringify({
                pjId: pjId
            }),
        };
        $.ajax(settings).done(function(response) {
            for (var i = 0; i < response.length; i++) {
                var enableTran = 'disabled';
                if (empCd == response[i].empCd) {
                    enableTran = '';
                }
                str += '<tr>' +
                    '<td>' + response[i].rownum + '</td>' +
                    '<td>' + response[i].langName + '</td>' +
                    '<td>' + response[i].empName + '</td>' +
                    '<td>' + response[i].title + '</td>' +
                    '<td>' + response[i].contents + '</td>' +
                    '<td>' + response[i].regDt + '</td>' +
                    '<td><button type="button" class="btn btn-danger" ' + enableTran + ' onclick="editTran(' + pjId + ',' + response[i].translationId + ')" ><spring:message code="work.detail.edit" /></button></td>' +
                    '<td><button type="button" class="btn btn-danger" ' + enableTran + ' onclick="delTran(' + response[i].translationId + ')"><spring:message code="work.detail.delete" /></button></td>' +
                    '</tr>';
                count++;
            }

        });
        if (count > 0) {
            disable = 'display: none;';
        }
        if(translationYn =="N"){disable='display: none;';}
        return (
            '<button type="button" class="btn btn-primary" onclick="addTran(' + pjId + ')" style="float: left;' + disable + '"><spring:message code="work.detail.addTran" /></button><div style="clear: both"></div>' +
            '<table id="translationtb' + pjId + '" cellpadding="5" cellspacing="0" border="0" class="table table-aqua table-booking-body" style="width: 100%;">' +
            '<thead>' +
            '<tr style="background-color:yellowgreen;">' +
            '<th class="all"><spring:message code="work.detail.stt" /></th>' +
            '<th><spring:message code="work.detail.language" /></th>' +
            '<th><spring:message code="work.detail.empName" /></th>' +
            '<th><spring:message code="work.detail.title" /></th>' +
            '<th><spring:message code="work.detail.contents" /></th>' +
            '<th class="min-tablet"><spring:message code="work.detail.registerDate" /></th>' +
            '<th class="min-tablet"><spring:message code="work.add.edit" /></th>' +
            '<th class="min-tablet"><spring:message code="work.detail.workfunction" /></th>' +
            '</tr>' +
            '</thead>' +
            '<tbody>' +
            str +
            '</tbody>' +
            '</table>'
        );
    }
    $(document).ready(function() {
        $('#checkall').change(function() {
            $('.cb-element').prop('checked', this.checked);
        });
        var start_date = $("#start_date").datepicker({
            dateFormat: 'yy-mm-dd'
        });
        var end_date = $("#end_date").datepicker({
            dateFormat: 'yy-mm-dd'
        });
        var lang_dt;
        if ('${_lang}' == 'ko') {
            lang_dt = lang_kor;
        } else if ('${_lang}' == 'en') {
            lang_dt = lang_en;
        } else {
            lang_dt = lang_vt;
        }
        var table = $("#projecttable").DataTable({
            'displayStart': '<%=displayStartPra1%>',
            "dom": '<"toolbar">frtip',
            "order": [
                [1, "desc"]
            ],
            'iDisplayLength': 10,
            "lengthChange": false,
            "autoWidth": false,
            "language": lang_dt,
            "bProcessing": true,
            "bServerSide": true,
            "bStateSave": false,
            "responsive": true,
            "iDisplayStart": 0,
            "fnDrawCallback": function() {},
            "sAjaxSource": "/API/project/main?start_date=" + $('#start_date').val() + "&end_date=" + $('#end_date').val() + "&levelDept=" + $("#levelDept").val() + "&translationStatus=" + $('#translationStatus').val() + "&approval=" + $('#approval').val() + "&optionSearch=" + $('#optionSearch').val() + "&inputSearch=" + $('#inputSearch').val() + "&type=1",
            "columns": [{
                "data": "pjId",
                render: function(data, type, full) {
                    return '<input class="cb-element" name="checkBox" type="checkbox" value="' +
                        data + '" >';
                }
            }, {
                "className": 'priority-hide',
                "data": "rownum"
            }, {
                "data": "title"
            }, {
                "className": 'priority-hide',
                "data": "deptName"
            }, {
                "className": 'priority-hide',
                "data": "positionName"
            }, {
                "data": "leaderProjectName"
            }, {
                "className": 'priority-hide',
                "data": "advanceAmount"
            }, {
                "className": 'priority-hide',
                "data": "useAmount"
            }, {
                "data": "remainAmount"
            }, {
                "className": 'priority-hide',
                "data": "approval"
            }, {
                "className": 'details-control priority-hide',
                "data": "translationStatus"
            }, {
                "className": 'priority-hide',
                "data": "projectStatus"
            }, {
                "className": 'priority-hide',
                "data": "projectStartDate",
                render: function(data, type, full) {
                    return data.slice(0, 10);
                }
            }],
            "fnRowCallback": function(nRow, aData, iDisplayIndex) {
                if (aData["projectStatus"] == 'Y') {
                    $("td:eq(11)", nRow).html('<spring:message code="project.type.approved" />');
                } else {
                    $("td:eq(11)", nRow).html('<spring:message code="project.type.not.approve" />');
                }

                if (aData["translationStatus"] > 0) {
                    $("td:eq(10)", nRow).html('<br><br><spring:message code="work.detail.finish" />');
                } else {
                    $("td:eq(10)", nRow).html('<br><br><spring:message code="work.detail.notfinish" />');
                }

                $("td:eq(12)", nRow).html(aData["projectStartDate"].slice(0, 10) + "~" + aData["projectEndDate"].slice(0, 10));

                $("td:eq(4)", nRow).html(aData["empName"] + "<br>" + "[" + aData["positionName"] + "]");
                $("td:eq(5)", nRow).html(aData["leaderAccountingName"] + "<br>" + "[" + aData["positionAccountingName"] + "]");
            },
            "aoColumnDefs": [{
                "aTargets": [13],
                "mData": "pjId",
                "mRender": function(data, type, full) {
                    return '<button style="margin: 1px; width: 38px;" type="button" class="btn btn-primary function-button" onclick="editProject(' + data
                         + ')"><i class="fas fa-edit"></i></button>' + ' ' + '<button style="width: 38px;" type="button" onclick="detail(' +
                        data +
                        ')" class="btn btn-success"><i class="fa fa-info-circle"></i></button>';
                }
            }, {
                "orderable": false,
                "targets": 0
            }, {
                "aTargets": [6],
                "mData": "advanceAmount",
                render: $.fn.dataTable.render.number(',', '.', 0)
            }, {
                "aTargets": [7],
                "mData": "useAmount",
                render: $.fn.dataTable.render.number(',', '.', 0)
            }, {
                "aTargets": [8],
                "mData": "remainAmount",
                render: $.fn.dataTable.render.number(',', '.', 0)
            }],
        });

        $('#projecttable').on('page.dt', function() {
            var str = paramSearch();
            location.href = "/project/project/list.hs?" + str;
        });

        $('#projecttable tbody').on('click', 'td.details-control', function() {
            var tr = $(this).closest('tr');
            var row = table.row(tr);

            if (row.child.isShown()) {
                // this row is already open - close it
                row.child.hide();
                tr.removeClass('shown');
                var shownRowsCount = table.rows('.shown').count();
                if (shownRowsCount) {
                    $(".close-child-rows").removeClass("disable-button");
                } else {
                    $(".close-child-rows").addClass("disable-button");
                }
            } else {
                // open this row
                row.child(format(row.data())).show();
                var id1 = row.data().pjId;
                tr.addClass('shown');
                $("#translationtb" + id1).dataTable({
                    "bLengthChange": false,
                    "bFilter": false,
                    "bInfo": false,
                    "language": lang_dt,
                });
                // enable "close all expanded rows" btn
                $(".close-child-rows").removeClass("disable-button");
            }

        });

        $.ajax({
            type: 'GET',
            url: '/API/selectEmpWithDept',
            cache: false,
            success: function(res) {
                autocomplete(document.getElementById("empAuth"), res.result, 'a');
                arrayInput = res.result;
            },
            failure: function(error) {
                alert("Faillllllll");
            }
        });

        $('#myform').validate({
            rules: {
                empAuth: {
                    required: true,
                },
            },
            messages: {
                empAuth: {
                    required: "Please input content",
                },
            },
        });

    });

    function addTran(pjId) {
        var str = paramSearch();
        location.href = "/project/project/addTran.hs?id=" + pjId + "&type=p&" + str;
    }

    function editTran(pjId, transId) {
        var str = paramSearch();
        location.href = "/project/project/editTran.hs?id=" + pjId + "&type=p&translationId=" + transId + "&" + str;
    }

    function delTran(id) {
        $.ajax({
            type: "POST",
            url: "/work/work/xoaTran",
            data: {
                id: id,
            },
            success: function(response) {
                var str = paramSearch();
                location.href = "/project/project/list.hs?title=success&message=Delete sucess&" + str;
            },
            error: function(error) {
                toastr['error']('error !');
            }
        });
    }

    function createProject() {
        var str = paramSearch();
        location.href = "/project/project/add.hs?" + str;
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
        location.href = "/project/project/list.hs?start_date1=" + $('#start_date').val() + "&end_date1=" + $('#end_date').val() + "&levelDept1=" + $("#levelDept").val() + "&translationStatus1=" + $('#translationStatus').val() + "&approval1=" + $('#approval').val() + "&optionSearch1=" + $('#optionSearch').val() + "&inputSearch1=" + $('#inputSearch').val() + "&displayStartPra1=" + displayStartPra;
    }

    function detail(id) {
        var str = paramSearch();
        location.href = "/project/project/dayproject.hs?pjId=" + id + "&" + str;
    }

    function editProject(id) {
        var str = paramSearch();
        location.href = "/project/project/edit.hs?id=" + id + "&" + str;
    }

    function deleteProject() {
        var lstChecked = "";
        var myTable = $('#projecttable').dataTable();
        var rowcollection = myTable.$('input[name="checkBox"]:checked', {
            "page": "all"
        });
        rowcollection.each(function(index, elem) {
            var checkbox_value = $(elem).val();
            lstChecked += checkbox_value + ",";
        });

        if (lstChecked.trim() == "") {
            toastr['error']('Delete error !');
        } else {
            if (confirm("<spring:message code='project.list.confirm' />")) {
                $.ajax({
                    type: "POST",
                    url: "/API/mainproject/delete",
                    data: {
                        'lstChecked': lstChecked,
                    },
                    success: function(response) {
                        location.href = "/project/project/list.hs?title=success&message=Delete sucess";
                    },
                    error: function(error) {
                        toastr['error']('Delete error !');
                    }
                })
            }
        }
    }

    function approvalMainProject() {
        var lstChecked = "";
        var myTable = $('#projecttable').dataTable();
        var rowcollection = myTable.$('input[name="checkBox"]:checked', {
            "page": "all"
        });
        rowcollection.each(function(index, elem) {
            var checkbox_value = $(elem).val();
            lstChecked += checkbox_value + ",";
        });

        if (lstChecked.trim() == "") {
            toastr['error']('Delete error !');
        } else {
            if (confirm("<spring:message code='project.list.confirm' />")) {
                $.ajax({
                    type: "POST",
                    url: "/API/mainproject/approval",
                    data: {
                        'lstChecked': lstChecked,
                    },
                    success: function(response) {
                        location.href = "/project/project/list.hs?title=success&message=Delete sucess";
                    },
                    error: function(error) {
                        toastr['error']('Delete error !');
                    }
                })
            }
        }
    }

    //start add
    function addProjectAuth() {
        $('#empAuth').val('');
        text.style.display = "none";

        $.ajax({
            type: "GET",
            url: "/API/mainproject/projectauth",
            data: {},
            success: function(response) {
                var str = "";
                for (var i = 0; i < response.length; i++) {

                    str += "<tr style=" + "'height:89px'" + "><td>" + response[i]["rownum"] + "</td>";
                    str += "<td>" + response[i]["empName"] + "</td>";
                    str += "<td>" + response[i]["deptName"] + "</td>";
                    str += "<td>" + response[i]["positionName"] + "</td>";
                    str += "<td>" + "<button type='button' onclick='deleteProjectAuth(" + +response[i]["projectAuthId"] + ")'" + " class='btn btn-delete'><i class='fas fa-minus'></i></button>" + "</td>";
                }

                $("#projectAuthTable tbody").html(str);
                $('#modalAddAuth').modal('show');
            },
            error: function(error) {
                toastr['error']('error !');
            }
        });
    }

    function historyLeaderShow() {
        var i = 0;
        var lstChecked = "";
        var myTable = $('#projecttable').dataTable();
        var rowcollection = myTable.$('input[name="checkBox"]:checked', {
            "page": "all"
        });
        rowcollection.each(function(index, elem) {
            var checkbox_value = $(elem).val();
            lstChecked += checkbox_value + ",";
            i++;
        });
        lstChecked = lstChecked.substring(0, lstChecked.length - 1);

        if (lstChecked.trim() == "") {
            alert("<spring:message code='notice.select.message' />");
        } else if (lstChecked.trim() != "" && i > 1) {
            alert("<spring:message code='project.alert.selectonecell' />");
        } else {
            $('#exampleModal').modal('show')
            $.ajax({
                type: "GET",
                url: "/API/mainproject/leaderlist",
                data: {
                    pjId: lstChecked,
                },
                success: function(response) {
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
                error: function(error) {
                    toastr['error']('error !');
                }
            })
        }
    }

    function exportExcel() {

        $.ajax({
            type: "GET",
            url: "/API/mainproject/exportexcel?start_date=" + $('#start_date').val() + "&end_date=" + $('#end_date').val() + "&levelDept=" + $("#levelDept").val() + "&translationStatus=" + $('#translationStatus').val() + "&approval=" + $('#approval').val() + "&optionSearch=" + $('#optionSearch').val() + "&inputSearch=" + $('#inputSearch').val() + "&iDisplayStart=0&iDisplayLength=100000&sSearch=&iSortCol_0=&sSortDir_0=desc",
            data: JSON.stringify(),
            success: function(response) {
                location.href = "/resources/Upload/excel_file/" + response;
            },
            error: function(error) {
                alert(error)
            }
        })
    }

    function printtag(tagid) {
        var start_date = $("#start_date").datepicker({
            dateFormat: 'yy-mm-dd'
        });
        var end_date = $("#end_date").datepicker({
            dateFormat: 'yy-mm-dd'
        });
        var startTime = $("#start_date").val();
        var endTime = $("#end_date").val();
        if (startTime == "") {
            startTime = '2022-01-01';
            endTime = '${timeNow}';
        }

        var strTimeBody = "<spring:message code='project.list.search'/>" + ": " + startTime.substring(0, 4) + " <spring:message code='project.list.year'/> " +
            +startTime.substring(5, 7) + " <spring:message code='project.list.month'/> " + startTime.substring(8, 10) + " <spring:message code='project.list.day'/> ~ " + endTime.substring(0, 4) + " <spring:message code='project.list.year'/> " + endTime.substring(5, 7) + " <spring:message code='project.list.month'/> " + endTime.substring(8, 10) + " <spring:message code='project.list.day'/> ";

        document.getElementById("printTimeBody").innerHTML = strTimeBody;

        var lang_dt;
        if ('${_lang}' == 'ko') {
            lang_dt = lang_kor;
        } else if ('${_lang}' == 'en') {
            lang_dt = lang_en;
        } else {
            lang_dt = lang_vt;
        }
        var table = $("#printTableBottom").DataTable({
            'displayStart': '<%=displayStartPra1%>',
            "dom": '<"toolbar">frtip',
            "order": [
                [0, "desc"]
            ],
            'iDisplayLength': 10000000,
            "responsive": true,
            "lengthChange": false,
            "autoWidth": false,
            "language": lang_dt,
            "bProcessing": true,
            "bServerSide": true,
            "bStateSave": false,
            "iDisplayStart": 0,
            "fnDrawCallback": function() {},
            "sAjaxSource": "/API/project/main?start_date=" + $('#start_date').val() + "&end_date=" + $('#end_date').val() + "&levelDept=" + $("#levelDept").val() + "&translationStatus=" + $('#translationStatus').val() + "&approval=" + $('#approval').val() + "&optionSearch=" + $('#optionSearch').val() + "&inputSearch=" + $('#inputSearch').val() + "&type=3",
            "columns": [{
                "className": 'attributes-hide',
                "data": "rownum"
            }, {
                "data": "type"
            }, {
                "data": "title"
            }, {
                "data": "deptName"
            }, {
                "data": "positionName"
            }, {
                "data": "leaderProjectName"
            }, {
                "data": "deptAccountingName"
            }, {
                "data": "positionAccountingName"
            }, {
                "data": "leaderAccountingName"
            }, {
                "data": "advanceAmount"
            }, {
                "data": "useAmount"
            }, {
                "data": "remainAmount"
            }, {
                "data": "approval"
            }, {
                "data": "translationStatus"
            }, {
                "data": "projectStatus"
            }, {
                "data": "projectStartDate",
                render: function(data, type, full) {
                    return data.slice(0, 10);
                }
            }, {
                "data": "projectEndDate",
                render: function(data, type, full) {
                    return data.slice(0, 10);
                }
            }],
            "fnRowCallback": function(nRow, aData, iDisplayIndex) {
                if (aData["translationStatus"] > 0) {
                    $("td:eq(13)", nRow).html('<spring:message code="work.detail.finish" />');
                } else {
                    $("td:eq(13)", nRow).html('<spring:message code="work.detail.notfinish" />');
                }

                if (aData["projectStatus"] == 'Y') {
                    $("td:eq(14)", nRow).html('<spring:message code="project.type.approved" />');
                } else {
                    $("td:eq(14)", nRow).html('<spring:message code="project.type.not.approve" />');
                }

                if (aData["type"] == 'translate') {
                    $("td:eq(1)", nRow).html('<spring:message code="emp.profile.translation" />');
                    $("td:eq(3)", nRow).html('');
                    $("td:eq(4)", nRow).html('');
                    $("td:eq(5)", nRow).html('');
                    $("td:eq(6)", nRow).html('');
                    $("td:eq(7)", nRow).html('');
                    $("td:eq(8)", nRow).html('');
                    $("td:eq(9)", nRow).html('');
                    $("td:eq(10)", nRow).html('');
                    $("td:eq(11)", nRow).html('');
                    $("td:eq(12)", nRow).html('');
                    $("td:eq(13)", nRow).html('');
                    $("td:eq(14)", nRow).html('');
                    $("td:eq(15)", nRow).html('');
                    $("td:eq(16)", nRow).html('');
                }
            },
            "aoColumnDefs": [

                {
                    "orderable": false,
                    "targets": 0
                }, {
                    "orderable": false,
                    "targets": 1
                }, {
                    "orderable": false,
                    "targets": 2
                }, {
                    "orderable": false,
                    "targets": 3
                }, {
                    "orderable": false,
                    "targets": 4
                }, {
                    "orderable": false,
                    "targets": 5
                }, {
                    "orderable": false,
                    "targets": 6
                }, {
                    "orderable": false,
                    "targets": 7
                }, {
                    "orderable": false,
                    "targets": 8
                }, {
                    "orderable": false,
                    "targets": 9
                }, {
                    "orderable": false,
                    "targets": 10
                }, {
                    "orderable": false,
                    "targets": 11
                }, {
                    "orderable": false,
                    "targets": 12
                }, {
                    "orderable": false,
                    "targets": 13
                }, {
                    "orderable": false,
                    "targets": 14
                }, {
                    "orderable": false,
                    "targets": 15
                }, {
                    "orderable": false,
                    "targets": 16
                }, {
                    "aTargets": [10],
                    "mData": "advanceAmount",
                    render: $.fn.dataTable.render.number(',', '.', 0)
                }, {
                    "aTargets": [11],
                    "mData": "useAmount",
                    render: $.fn.dataTable.render.number(',', '.', 0)
                }, {
                    "aTargets": [9],
                    "mData": "remainAmount",
                    render: $.fn.dataTable.render.number(',', '.', 0)
                }

            ]
        });
        var table = $("#printTable").DataTable({
            'displayStart': '<%=displayStartPra1%>',
            "dom": '<"toolbar">frtip',
            "order": [
                [0, "desc"]
            ],
            'iDisplayLength': 10000000,
            "responsive": true,
            "lengthChange": false,
            "autoWidth": false,
            "language": lang_dt,
            "bProcessing": true,
            "bServerSide": true,
            "bStateSave": false,
            "iDisplayStart": 0,
            "fnDrawCallback": function() {},
            "sAjaxSource": "/API/project/main?start_date=" + $('#start_date').val() + "&end_date=" + $('#end_date').val() + "&levelDept=" + $("#levelDept").val() + "&translationStatus=" + $('#translationStatus').val() + "&approval=" + $('#approval').val() + "&optionSearch=" + $('#optionSearch').val() + "&inputSearch=" + $('#inputSearch').val() + "&type=2",
            "columns": [{
                "className": 'attributes-hide',
                "data": "rownum"
            }, {
                "data": "type"
            }, {
                "data": "title"
            }, {
                "data": "deptName"
            }, {
                "data": "positionName"
            }, {
                "data": "leaderProjectName"
            }, {
                "data": "deptAccountingName"
            }, {
                "data": "positionAccountingName"
            }, {
                "data": "leaderAccountingName"
            }, {
                "data": "advanceAmount"
            }, {
                "data": "useAmount"
            }, {
                "data": "remainAmount"
            }, {
                "data": "approval"
            }, {
                "data": "translationStatus"
            }, {
                "data": "projectStatus"
            }, {
                "data": "projectStartDate",
                render: function(data, type, full) {
                    return data.slice(0, 10);
                }
            }, {
                "data": "projectEndDate",
                render: function(data, type, full) {
                    return data.slice(0, 10);
                }
            }],
            "fnRowCallback": function(nRow, aData, iDisplayIndex) {
                if (aData["translationStatus"] > 0) {
                    $("td:eq(13)", nRow).html('<spring:message code="work.detail.finish" />');
                } else {
                    $("td:eq(13)", nRow).html('<spring:message code="work.detail.notfinish" />');
                }

                if (aData["projectStatus"] == 'Y') {
                    $("td:eq(14)", nRow).html('<spring:message code="project.type.approved" />');
                } else {
                    $("td:eq(14)", nRow).html('<spring:message code="project.type.not.approve" />');
                }

                if (aData["type"] == 'translate') {
                    $("td:eq(1)", nRow).html('<spring:message code="emp.profile.translation" />');
                    $("td:eq(3)", nRow).html('');
                    $("td:eq(4)", nRow).html('');
                    $("td:eq(5)", nRow).html('');
                    $("td:eq(6)", nRow).html('');
                    $("td:eq(7)", nRow).html('');
                    $("td:eq(8)", nRow).html('');
                    $("td:eq(9)", nRow).html('');
                    $("td:eq(10)", nRow).html('');
                    $("td:eq(11)", nRow).html('');
                    $("td:eq(12)", nRow).html('');
                    $("td:eq(13)", nRow).html('');
                    $("td:eq(14)", nRow).html('');
                    $("td:eq(15)", nRow).html('');
                    $("td:eq(16)", nRow).html('');
                }
            },
            "aoColumnDefs": [
                {
                    "orderable": false,
                    "targets": 0
                }, {
                    "orderable": false,
                    "targets": 1
                }, {
                    "orderable": false,
                    "targets": 2
                }, {
                    "orderable": false,
                    "targets": 3
                }, {
                    "orderable": false,
                    "targets": 4
                }, {
                    "orderable": false,
                    "targets": 5
                }, {
                    "orderable": false,
                    "targets": 6
                }, {
                    "orderable": false,
                    "targets": 7
                }, {
                    "orderable": false,
                    "targets": 8
                }, {
                    "orderable": false,
                    "targets": 9
                }, {
                    "orderable": false,
                    "targets": 10
                }, {
                    "orderable": false,
                    "targets": 11
                }, {
                    "orderable": false,
                    "targets": 12
                }, {
                    "orderable": false,
                    "targets": 13
                }, {
                    "orderable": false,
                    "targets": 14
                }, {
                    "orderable": false,
                    "targets": 15
                }, {
                    "orderable": false,
                    "targets": 16
                }, {
                    "aTargets": [10],
                    "mData": "advanceAmount",
                    render: $.fn.dataTable.render.number(',', '.', 0)
                }, {
                    "aTargets": [11],
                    "mData": "useAmount",
                    render: $.fn.dataTable.render.number(',', '.', 0)
                }, {
                    "aTargets": [9],
                    "mData": "remainAmount",
                    render: $.fn.dataTable.render.number(',', '.', 0)
                }
            ]
        });

        table.on('draw', function() {
            mainPrinTable(tagid);
            var str = paramSearch();
            location.href = "/project/project/list.hs?" + str;
        });
    }

    function mainPrinTable(tagid) {
        var hashid = "#" + tagid;
        var tagname = $(hashid).prop("tagName").toLowerCase();
        var attributes = "";
        var attrs = document.getElementById(tagid).attributes;
        $.each(attrs, function(i, elem) {
            attributes += " " + elem.name + " ='" + elem.value + "' ";
        })
        var divToPrint = $(hashid).html();
        var head = '<html> ';
        head += '<link href="https://fonts.googleapis.com/css?family=Roboto:100,300,400,500,700,900" rel="stylesheet" />';
        head += '<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" />';
        head += '<link href="${_ctx}/resources/css/default/styles.css" rel="stylesheet" />';
        head += '<link href="${_ctx}/resources/css/common.css" rel="stylesheet" />';
        head += '<link href="${_ctx}/resources/css/project/main-project.css" rel="stylesheet" />';

        var allcontent = head + "<body  onload='window.print()' >" + "<" + tagname + attributes + ">" + divToPrint + "</" + tagname + ">" + "</body></html>";
        var newWin = window.open('', 'Print-Window');
        //newWin.document.open();
        newWin.document.write(allcontent);
        newWin.document.close();
        //setTimeout(function(){newWin.close();},10);
    }

    let x = document.querySelectorAll(".currency");
    for (let i = 0, len = x.length; i < len; i++) {
        let num = Number(x[i].innerHTML).toLocaleString('en');
        x[i].innerHTML = num;
        x[i].classList.add("currSign");
    }

    function paramSearch() {
        var displayStartPra = $('#projecttable').DataTable().page.info().page * 10;
        var str = "start_date1=" + $('#start_date').val() + "&end_date1=" + $('#end_date').val() + "&levelDept1=" + $("#levelDept").val() + "&translationStatus1=" + $('#translationStatus').val() + "&approval1=" + $('#approval').val() + "&optionSearch1=" + $('#optionSearch').val() + "&inputSearch1=" + $('#inputSearch').val() + "&displayStartPra1=" + displayStartPra;
        return str;
    }

    //add pic
    function findArray(str, key) {
        var number = str.indexOf(key);
        return number;
    }

    var text = document.getElementById("text");
    var textAccounting = document.getElementById("textAccounting");
    var arrayInput;
    var checkItem = 0;

    function autocomplete(inp, arr, type) {

        var currentFocus;

        inp.addEventListener("click", function(e) {

            this.value = ' ';
            var a, b, i, val = this.value;
            closeAllLists();
            if (!val) {
                return false;
            }
            currentFocus = -1;
            a = document.createElement("DIV");
            a.setAttribute("id", this.id + "autocomplete-list");
            a.setAttribute("class", "autocomplete-items");
            this.parentNode.appendChild(a);
            var checkItem = 0;
            for (i = 0; i < arr.length; i++) {
                if (findArray(arr[i].toUpperCase(), val.toUpperCase()) >= 0) {
                    b = document.createElement("DIV");
                    var numberFirt = arr[i].toUpperCase().indexOf(val.toUpperCase());
                    b.innerHTML = arr[i].substr(0, numberFirt);
                    b.innerHTML += "<strong>" + arr[i].substr(numberFirt, val.length) + "</strong>";
                    b.innerHTML += arr[i].substr((numberFirt + val.length));

                    b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";
                    b.addEventListener("click", function(e) {
                        inp.value = this.getElementsByTagName("input")[0].value;
                        closeAllLists();
                    });
                    a.appendChild(b);
                    checkItem = 1;
                }
            }
            if (type == 'p') {
                if (checkItem == 0) {
                    text.style.display = "block";
                } else {
                    text.style.display = "none"
                }
            } else {
                if (checkItem == 0) {
                    textAccounting.style.display = "block";
                } else {
                    textAccounting.style.display = "none"
                }
            }
            console.log(this.id)
            $('#' + this.id).val('');
        });

        inp.addEventListener("input", function(e) {
            var a, b, i, val = this.value;
            closeAllLists();
            if (!val) {
                return false;
            }
            currentFocus = -1;
            a = document.createElement("DIV");
            a.setAttribute("id", this.id + "autocomplete-list");
            a.setAttribute("class", "autocomplete-items");
            this.parentNode.appendChild(a);
            var checkItem = 0;
            for (i = 0; i < arr.length; i++) {
                if (findArray(arr[i].toUpperCase(), val.toUpperCase()) >= 0) {
                    b = document.createElement("DIV");
                    var numberFirt = arr[i].toUpperCase().indexOf(val.toUpperCase());
                    b.innerHTML = arr[i].substr(0, numberFirt);
                    b.innerHTML += "<strong>" + arr[i].substr(numberFirt, val.length) + "</strong>";
                    b.innerHTML += arr[i].substr((numberFirt + val.length));

                    b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";
                    b.addEventListener("click", function(e) {
                        inp.value = this.getElementsByTagName("input")[0].value;
                        closeAllLists();
                    });
                    a.appendChild(b);
                    checkItem = 1;
                }
            }
            if (type == 'p') {
                if (checkItem == 0) {
                    text.style.display = "block";
                } else {
                    text.style.display = "none"
                }
            } else {
                if (checkItem == 0) {
                    textAccounting.style.display = "block";
                } else {
                    textAccounting.style.display = "none"
                }
            }

        });
        inp.addEventListener("keydown", function(e) {
            var x = document.getElementById(this.id + "autocomplete-list");
            if (x) x = x.getElementsByTagName("div");
            if (e.keyCode == 40) {
                currentFocus++;
                addActive(x);
            } else if (e.keyCode == 38) {
                currentFocus--;
                addActive(x);
            } else if (e.keyCode == 13) {
                e.preventDefault();
                if (currentFocus > -1) {
                    if (x) x[currentFocus].click();
                }
            }
        });

        function addActive(x) {
            if (!x) return false;
            removeActive(x);
            if (currentFocus >= x.length) currentFocus = 0;
            if (currentFocus < 0) currentFocus = (x.length - 1);
            x[currentFocus].classList.add("autocomplete-active");
        }

        function removeActive(x) {
            for (var i = 0; i < x.length; i++) {
                x[i].classList.remove("autocomplete-active");
            }
        }

        function closeAllLists(elmnt) {
            var x = document.getElementsByClassName("autocomplete-items");
            for (var i = 0; i < x.length; i++) {
                if (elmnt != x[i] && elmnt != inp) {
                    x[i].parentNode.removeChild(x[i]);
                }
            }
        }
        document.addEventListener("click", function(e) {
            if(e.path !== undefined){
                var valGet = e.path[0]["id"];
                if (valGet != 'myInput' && valGet != 'myInputAccount') {
                    closeAllLists(e.target);
                }
            }
        });
    }
    //end add pic
    var text = document.getElementById("textAccounting");

    function addPersonAuth() {
        var empExitCheck = $('#empAuth').val();
        //var empExitCheckAccounting = $('#myInputAccount').val();
        checkItem = 0;

        for (var i = 0; i < arrayInput.length; i++) {
            if (arrayInput[i].toUpperCase() == empExitCheck.toUpperCase()) {
                checkItem = 2;
                text.style.display = "none";
            }
        }

        if (checkItem != 2) {
            text.style.display = "block"
        }

        var isValid = $('#myform').valid();
        if (isValid == true && checkItem == 2) {
            if (confirm('<spring:message code="project.list.confirm" />')) {
                var settings = {
                    "url": "/API/mainproject/addprojectauth",
                    "method": "POST",
                    "data": ({
                        "inputName": $("#empAuth").val(),
                    }),
                };
                $.ajax(settings).done(function(response) {
                    addProjectAuth();
                    console.log(response);
                });
            }
        } else {}
    }

    function deleteProjectAuth(id) {
        if (confirm('<spring:message code="project.list.confirm" />')) {
            var settings = {
                "url": "/API/mainproject/deleteprojectauth",
                "method": "POST",
                "data": ({
                    "id": id,
                }),
            };
            $.ajax(settings).done(function(response) {
                addProjectAuth();
                console.log(response);
            });
        }

    }
</script>