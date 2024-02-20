<%@ page import="org.springframework.web.context.request.ServletRequestAttributes" %>
<%@ page import="org.springframework.web.context.request.RequestContextHolder" %>
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
    String backSearch="start_date="+start_date+"&end_date="+end_date+"&levelDept="+levelDeptGet+"&translationStatus="+translationStatusGet+"&dayProjectStatus="+dayProjectStatus+"&optionSearch="+optionSearchGet+"&inputSearch="+inputSearchGet+"&displayStartPra="+displayStartPra+"&start_date1="+start_date1+"&end_date1="+end_date1+"&levelDept1="+levelDeptGet1+"&translationStatus1="+translationStatusGet1+"&approval1="+approval1+"&optionSearch1="+optionSearchGet1+"&inputSearch1="+inputSearchGet1+"&displayStartPra1="+displayStartPra1;

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

    .datos{
        table-layout: fixed;
    }
    .modal-header,.modal-body,.modal-footer{
        cursor: move;
    }

    td.detail-open {
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
        .hidden-item{
            display: none;
        }
        .title-info{
            width: 20% !important;
        }
        .page-header{
            font-size: 20px;
        }
        .priority-hide
        {
            display: none;
        }
        #projecttable th:nth-child(4){
            width: 25% !important;
        }
        #projecttable td:nth-child(4){
            white-space: break-spaces;
        }

        .responsive {
            width: 310px;
            max-width: 310px;
            height:auto;
        }

        .print-header h3 {
                font-size: 24px;
                margin: 10px 0px 10px 0px;
            }

        td.detail-open {
            background: url('https://www.datatables.net/examples/resources/details_open.png') no-repeat right center;
            cursor: pointer;
        }

        tr.shown td.detail-open {
            background: url('https://www.datatables.net/examples/resources/details_close.png') no-repeat right center;
        }
        #titlefile{
            width: 50%;
        }



    }

</style>
<div class="content" id="content">

    <h1 class="page-header cursor-default">
        <spring:message code='projectdetail.list.index'/><br>
        <small>
            <spring:message code='projectdetail.list.smalltitle'/>
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
                    <h3 class="panel-title"><spring:message code='work.add.information' /></h3>
                </div>
                <div class="panel-body">
                    <form id="myform" class="form-inline" action="">
                        <table class="table mb-0">
                            <tbody>
                            <tr>
                                <td style="background: #f0f4f6;font-weight: bold" class="hidden-item"><spring:message code='dayproject.dayproject.now' /></td>
                                <td class="currency hidden-item">${infoProject.nowPrice}</td>
                                <td class="title-info" style="background: #f0f4f6;font-weight: bold"><spring:message code='dayproject.dayproject.remain' /></td>
                                <td class="currency">${infoProject.remain}</td>
                                <td class="title-info" style="background: #f0f4f6;font-weight: bold"><spring:message code='dayprojectitem.detail.dayspent' /></td>
                                <td class="currency">${sumMoneyItem}</td>
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
            <c:if test="${projectLeader==emp.empCd || emp.adminYn=='Y'}">
                <button type="button" class="btn btn-primary function-button" onclick="addNew()" ${disable} ${InfoDayProjectByID.dayProjectStatus =="Y"?"disabled":""}><spring:message code='dayprojectitem.detail.addcategory'/></button>
                <button type="button" class="btn btn-primary function-button" onclick="edit()" ${disable} ${InfoDayProjectByID.dayProjectStatus =="Y"?"disabled":""}><spring:message code='dayprojectitem.detail.editcategory'/></button>
                <button type="button" class="btn btn-primary function-button" onclick="openModel()" ${disable} ${InfoDayProjectByID.dayProjectStatus =="Y"?"disabled":""}><spring:message code='dayprojectitem.detail.deletecategory'/></button>
            </c:if>
            <c:if test="${accountingLeader==emp.empCd || emp.adminYn=='Y'}">
                <button type="button" class="btn btn-primary function-button hidden-item" onclick="approve()" ${InfoDayProjectByID.dayProjectStatus =="Y"?"disabled":""}><spring:message code='dayprojectitem.detail.approve'/></button>
            </c:if>
        </c:if>
        <button type="button" class="btn btn-info function-button hidden-item" onclick="printtag('print-table')"><spring:message code='dayprojectitem.detail.print'/></button>
        <button type="button" class="btn btn-warning function-button hidden-item" onclick="exportExcel()"><spring:message code='dayprojectitem.detail.exportexcel'/></button>
    </div>
    <div class="m-table">
        <table id="projecttable" class="table table-striped table-bordered w-100 text-center">
            <thead>
            <tr style="background-color: #1c5691;color: white">
                <th style="vertical-align: inherit;"><input type="checkbox" name="all" id="checkall"/></th>
                <th style="vertical-align: inherit;" class="desktop"><spring:message code='work.list.stt'/></th>
                <th style="vertical-align: inherit;"><spring:message code='dayprojectitem.detail.spentstatus'/></th>
                <th style="vertical-align: inherit;"><spring:message code='dayprojectitem.detail.namecategory'/></th>
                <th style="vertical-align: inherit;" class="desktop"><spring:message code='dayprojectitem.detail.dept'/></th>
                <th style="vertical-align: inherit;" class="desktop"><spring:message code='dayprojectitem.detail.position'/></th>
                <th style="vertical-align: inherit;" class="desktop"><spring:message code='dayprojectitem.detail.pic'/></th>
                <th style="vertical-align: inherit;"><spring:message code='dayprojectitem.detail.price'/></th>
                <th style="vertical-align: inherit;"><spring:message code='dayprojectitem.detail.ea'/></th>
                <th style="vertical-align: inherit;" class="desktop"><spring:message code='dayprojectitem.detail.amount'/></th>
                <th style="vertical-align: inherit;" class="desktop"><spring:message code='dayprojectitem.detail.approve'/></th>
                <th style="vertical-align: inherit;" class="desktop"><spring:message code='dayprojectitem.detail.translation'/></th>
                <th style="vertical-align: inherit;" class="desktop"><spring:message code='dayprojectitem.detail.picture'/></th>
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
                    <input type="text" hidden="" value="" name="" id="giatri"/>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code='translation.close' /></button>
                    <button type="button" class="btn btn-primary" onclick="xoaW()"><spring:message code='work.list.delete' /></button>
                </div>
            </div>
        </div>
    </div>

    <br>
    <div>
        <h2 class="page-title" style="float: left" id="titlefile">
            <small><spring:message code='dayproject.file.total' /> : <span id="titlefileSmall"></span> MB (<spring:message code='dayproject.file.maximum' />: 100MB)</small>
        </h2>
        <button style="margin-bottom: 5px;float: right" class="btn btn-primary" onclick="addNewFile()" ${disable} ${InfoDayProjectByID.dayProjectStatus =="Y"?"disabled":""}>
            <spring:message code='dayprojectitem.detail.filescontrol' />
        </button>

    </div>
    <div style="clear: both"></div>
    <div class="panel panel-inverse">
        <div style="height:350px;overflow: auto; ">
            <div class="m-table">
                <table id="tbFile" class="table table-striped table-bordered" style="width:100%;text-align: center">
                    <thead>
                    <tr style="background-color: #1c5691;color: white">
                        <th style="width:10%; text-align: center;vertical-align: inherit;"><spring:message code='work.list.stt' /></th>
                        <th style="width:20%; text-align: center;vertical-align: inherit;"><spring:message code='file.attach' /></th>
                        <th style="width:20%; text-align: center;vertical-align: inherit;"><spring:message code='file.size' /></th>
                        <th style="width:20%; text-align: center;vertical-align: inherit;"><spring:message code='search.title.regisdate' /></th>
                        <th style="width:20%; text-align: center;vertical-align: inherit;"><spring:message code='file.download' /></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:choose>
                        <c:when test="${lstFile.size()==0}">
                            <tr>
                                <td class="cursor-pointer"
                                    style="text-align: center; vertical-align: inherit;" colspan="100%">
                                    <spring:message code='main.index.datastatus' />
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${lstFile}" var="lst">
                                <tr>
                                    <td>${lst.rownum}</td>
                                    <td>${lst.fileName}</td>
                                    <td>${lst.fileSize}</td>
                                    <td>${lst.regDt}</td>
                                    <td>
                                        <a href="${lst.filePath}${lst.fileHashName}" class="btn btn-secondary view_file_download"
                                           download="${lst.fileName}">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-download" viewBox="0 0 16 16">
                                                <path d="M.5 9.9a.5.5 0 0 1 .5.5v2.5a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1v-2.5a.5.5 0 0 1 1 0v2.5a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2v-2.5a.5.5 0 0 1 .5-.5z"></path>
                                                <path d="M7.646 11.854a.5.5 0 0 0 .708 0l3-3a.5.5 0 0 0-.708-.708L8.5 10.293V1.5a.5.5 0 0 0-1 0v8.793L5.354 8.146a.5.5 0 1 0-.708.708l3 3z"></path>
                                            </svg>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                    </tbody>

                </table>
            </div>
        </div>
    </div>

    <div id="print-table">
        <div class="fixed-header">
            <div class="print-header">
                <h3 id="printHeaderTitle"><spring:message code='project.list.project'/></h3>
            </div>
            <div class="print-title-bottom">
                <h6 id="printHeaderBottom"><spring:message code='project.list.processing'/></h6>
            </div>
        </div>
        <table id="printTable" class="table table-striped table-bordered" style="width:100%;text-align: center; border: none;">

            <thead>
                <td class="child-td-table-print"></td>
                <tr class="tr-print-table text-center">
                    <th class="attributes-hide" style="vertical-align: inherit;"><spring:message code='work.list.stt'/></th>
                    <th style="vertical-align: inherit;"><spring:message code='work.list.stt'/></th>
                    <th style="vertical-align: inherit;"><spring:message code='dayprojectitem.detail.spentstatus'/></th>
                    <th style="vertical-align: inherit;width: 13%;"><spring:message code='dayprojectitem.detail.namecategory'/></th>
                    <th style="vertical-align: inherit;"><spring:message code='dayprojectitem.detail.price'/></th>
                    <th style="vertical-align: inherit;"><spring:message code='dayprojectitem.detail.ea'/></th>
                    <th style="vertical-align: inherit;"><spring:message code='dayprojectitem.detail.amount'/></th>
                    <th style="vertical-align: inherit;"><spring:message code='dayprojectitem.detail.approve'/></th>
                    <th style="vertical-align: inherit;"><spring:message code='dayprojectitem.detail.translation'/></th>
                    <th style="vertical-align: inherit;" class="priority-hide"><spring:message code='dayprojectitem.detail.picture'/></th>
                </tr>
            </thead>
        </table>
    </div>

    <!-- Modal -->
    <div style="padding-right: 12% !important;" class="modal fade" id="exampleModal1" tabindex="-1"
         role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content" style="width: max-content;">
                <div class="modal-header">
                    <h5 class="modal-title">
                        <spring:message code='emp.list.information' />
                    </h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body table-wrapper-scroll-y my-custom-scrollbar" id="viewpicture">
                    <div class="datos"></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>

</div>
<script>
    function format(d) {
        var disable='';
        var count=0;
        var dpiId=d.dpiId;
        var str='';
        var empCd='${empCd}';
        var translationYn='${translationYn}';
        var settings = {
            "url": "/API/dayProjectItem/getTranDayProjectItem",
            "method": "POST",
            "async": false,
            "headers": {
                "Content-Type": "application/json"
            },
            "data": JSON.stringify({
                dpiId: dpiId
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
                    '<td><button type="button" class="btn btn-danger" '+enableTran+' onclick="editTran('+dpiId+','+response[i].translationId+')" ><spring:message code="work.detail.edit" /></button></td>' +
                    '<td><button type="button" class="btn btn-danger" '+enableTran+' onclick="delTran('+response[i].translationId+')"><spring:message code="work.detail.delete" /></button></td>' +
                    '</tr>';
                count++;
            }

        });
        if(count>0){disable='display: none;';}
        if(translationYn =="N"){disable='display: none;';}
        return (
            '<button type="button" class="btn btn-primary" ${InfoDayProjectByID.dayProjectStatus =="Y"?"disabled":""} onclick="addTran('+dpiId+')" style="float: left;'+disable+'"><spring:message code="work.detail.addTran" /></button><div style="clear: both"></div>'+
            '<table id="translationtb'+dpiId+'" cellpadding="5" cellspacing="0" border="0" class="table table-aqua table-booking-body" style="width: 100%;">' +
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
    function formatDetail(d) {
        // `d` is the original data object for the row
        var data=d.imgPath;
        var picture='';
        if(data !="" && data !=null){
            picture= '<a class="open-AddBookDialog" data-toggle="modal" onclick="viewpicture(\''+data+'\')" href="#exampleModal1" style="text-decoration: underline;"><img src="${_ctx}'+data+'"  height="50px" width="50px"></a>';
        }else{
            data="/resources/Upload/images/no-image.jpg";
            picture= '<a class="open-AddBookDialog" data-toggle="modal" onclick="viewpicture(\''+data+'\')" href="#exampleModal1" style="text-decoration: underline;"><img src="${_ctx}'+data+'"  height="50px" width="50px"></a>';
        }
        return (
            '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">' +
            '<tr>' +
            '<td><spring:message code="dayprojectitem.detail.amount"/>:</td>' +
            '<td class="currency">' +
            Math.abs(d.amount) +
            '</td>' +
            '</tr>' +
            '<tr>' +
            '<td><spring:message code="dayprojectitem.detail.picture"/>:</td>' +
            '<td>' +
            picture +
            '</td>' +
            '</tr>' +
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
        var table = $('#projecttable').DataTable({
            "dom": '<"toolbar">frtip',
            "order": [[1, "desc"]],
            'iDisplayLength': 10,
            "lengthChange": false, "autoWidth": false,
            "language": lang_dt,
            "bProcessing": true,
            "bServerSide": true,
            "bStateSave": false,
            "iDisplayStart": 0,
            "fnDrawCallback": function () {
            },
            "sAjaxSource": "/API/project/getDayProjectItemDatatables?dpId=${dpId}"+ "&type=1",
            "columns": [
                {
                    "data": "dpiId",
                    render: function (data, type, full) {
                        return ' <input class="cb-element" name="xoa" type="checkbox" value="'
                            + data + '" >';
                    }
                },
                {   "className":'priority-hide',
                    "data": "rownum"
                }
                , {
                    "data": "spentType"
                }, {
                    "data": "productName"
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
                    "data": "price"
                }, {
                    "className":'detail-open',
                    "data": "ea"
                },
                {
                    "className":'priority-hide',
                    "data": "amount"
                },
                {
                    "className":'priority-hide',
                    "data": "checkStatus"
                },
                {
                    "className":'details-control priority-hide',
                    "data": "translationStatus"
                },{
                    "className":'priority-hide',
                    defaultContent: 'NA',
                    "data": "imgPath"
                }
            ],
            "fnRowCallback": function (nRow, aData, iDisplayIndex) {
                if (aData["translationStatus"] >0) {
                    $("td:eq(11)", nRow).html('<br><br><spring:message code="work.detail.finish" />');
                } else {
                    $("td:eq(11)", nRow).html('<br><br><spring:message code="work.detail.notfinish" />');
                }

                if (aData["checkStatus"] == 'Y') {
                    $("td:eq(10)", nRow).html('<spring:message code="project.type.approved" />');
                } else {
                    $("td:eq(10)", nRow).html('<spring:message code="project.type.not.approve" />');
                }

            },
            "aoColumnDefs": [
                {
                    "aTargets": [12],
                    "mData": "imgPath",
                    "mRender": function (data, type, full) {
                        if(data !="" && data !=null){
                            return '<a class="open-AddBookDialog" data-toggle="modal" onclick="viewpicture(\''+data+'\')" href="#exampleModal1" style="text-decoration: underline;"><img src="${_ctx}'+data+'"  height="50px" width="50px"></a>';
                        }else{
                            data="/resources/Upload/images/no-image.jpg";
                            return '<a class="open-AddBookDialog" data-toggle="modal" onclick="viewpicture(\''+data+'\')" href="#exampleModal1" style="text-decoration: underline;"><img src="${_ctx}'+data+'"  height="50px" width="50px"></a>';
                        }
                    }
                },
                {"orderable": false, "targets": 0},
                {"orderable": false, "targets": 12},
                {
                    "aTargets": [7],
                    "mData": "price",
                    render: $.fn.dataTable.render.number( ',', '.', 0)
                },
                {
                    "aTargets": [9],
                    "mData": "amount",
                    "mRender": function (data, type, full) {
                        return $.fn.dataTable.render.number( ',', '.', 0).display(Math.abs(data));
                    }
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
                var id1=row.data().dpiId;
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
        <%
            String userAgent =request.getHeader("User-Agent").toUpperCase();
            if(userAgent.indexOf("MOBILE") > -1) {
        %>
        $('#projecttable tbody').on('click', 'td.detail-open', function () {
        

    
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
                 row.child( formatDetail(row.data()) ).show();
                tr.addClass('shown');
                // enable "close all expanded rows" btn
                $(".close-child-rows").removeClass("disable-button");
            }

        });

        <% } %>

        sumFileSize();

       
    });
  
    function addNew(){
        location.href="/project/project/detailProjectAdd.hs?pjId=${pjId}&dpId=${dpId}&<%=backSearch%>";
    }

    function edit(){
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
            location.href="/project/project/detailProjectEdit.hs?pjId=${pjId}&dpId=${dpId}&dpiId="+lstChecked+"&<%=backSearch%>";
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
                "url": "/API/dayProjectItem/deleteDayProjectItem",
                "method": "POST",
                "headers": {
                    "Content-Type": "application/json"
                },
                "data": JSON.stringify({
                    inputSearchSql: lstChecked
                }),
            };
            $.ajax(settings).done(function (response) {
                location.href = "/project/project/detail.hs?pjId=${pjId}&dpId=${dpId}&title=success&message=delete sucess&<%=backSearch%>";
            });
        }
    }

    function approve(){
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
            if(confirm('<spring:message code='project.list.confirm' />')){
                var settings = {
                    "url": "/API/dayProjectItem/approveDayProjectItem",
                    "method": "POST",
                    "headers": {
                        "Content-Type": "application/json"
                    },
                    "data": JSON.stringify({
                        inputSearchSql: lstChecked
                    }),
                };
                $.ajax(settings).done(function (response) {
                    location.href = "/project/project/detail.hs?pjId=${pjId}&dpId=${dpId}&title=success&message=update sucess&<%=backSearch%>";
                });
            }
        }
    }

    function back() {
        location.href = "/project/project/dayproject.hs?pjId=${pjId}&<%=backSearch%>";
    }

    function addNewFile(){
        location.href = "/project/project/AddFileDayProject.hs?pjId=${pjId}&dpId=${dpId}&<%=backSearch%>";
    }
    function viewpicture(imgPath){
        var str='<img src="${_ctx}'+imgPath+'" class="responsive"  height="700px" width="700px">';
        $("#viewpicture div").html(str);
    }
    $(function(){
        $('#exampleModal1').draggable({
            handle: ".modal-header,.modal-body,.modal-footer"
        });
    });

    function addTran(dpiId){
        location.href="/project/project/addTran.hs?id="+dpiId+"&type=dpi"+"&pjId=${pjId}&dpId=${dpId}&<%=backSearch%>";
    }

    function editTran(dpiId,transId){
        location.href="/project/project/editTran.hs?id="+dpiId+"&type=dpi"+"&pjId=${pjId}&dpId=${dpId}&translationId="+transId+"&<%=backSearch%>";
    }

    function delTran(id) {
        $.ajax({
            type: "POST",
            url: "/work/work/xoaTran",
            data: {
                id: id,
            },
            success: function (response) {
                location.href = "/project/project/detail.hs?pjId=${pjId}&dpId=${dpId}&title=success&message=Delete sucess&<%=backSearch%>";
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
            url: "/API/project/getDayProjectItemExportExcel?dpId=${dpId}&pjId=${pjId}&iDisplayStart=0&iDisplayLength=100000&sSearch=&iSortCol_0=&sSortDir_0=desc",
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
        var lang_dt;
        if ('${_lang}' == 'ko') {
            lang_dt = lang_kor;
        } else if ('${_lang}' == 'en') {
            lang_dt = lang_en;
        } else {
            lang_dt = lang_vt;
        }

        var startTime = '${dayProjectDTO1RegDate}';

        var headerTitle = startTime.substring(0, 4) + " <spring:message code='project.list.year'/> " + startTime.substring(5, 7) + " <spring:message code='project.list.month'/> " + startTime.substring(8, 10) + " <spring:message code='project.list.day'/> " + "(" + '${dayProjectDTO1Title}' + ")" + " <spring:message code='projectdetail.list.exceltile'/> ";

        var bottomTitle = " <spring:message code='project.add.pic'/> " + ": " + '${projectMainDTOProject}' + " - " + " <spring:message code='project.list.pic.accounting'/> " + ": " + '${projectMainDTOAccounting}';

        document.getElementById("printHeaderTitle").innerHTML = headerTitle;
        document.getElementById("printHeaderBottom").innerHTML = bottomTitle;

        var table = $("#printTable").DataTable({
            
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
            "sAjaxSource": "/API/project/getDayProjectItemDatatables?dpId=${dpId}"+ "&type=2",
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
                    "data": "productName"
                }, {
                    "data": "price"
                }, {
                    "data": "ea"
                },
                {
                    "data": "amount"
                },
                {
                    "data": "checkStatus"
                },
                {
                    "data": "translationStatus"
                },{
                    "className":'priority-hide',
                    defaultContent: 'NA',
                    "data": "imgPath"
                }
            ],

            "fnRowCallback": function (nRow, aData, iDisplayIndex) {
                if (aData["translationStatus"] >0) {
                    $("td:eq(8)", nRow).html('<spring:message code="work.detail.finish" />');
                } else {
                    $("td:eq(8)", nRow).html('<spring:message code="work.detail.notfinish" />');
                }

                if (aData["checkStatus"] == 'Y') {
                    $("td:eq(7)", nRow).html('<spring:message code="project.type.approved" />');
                } else {
                    $("td:eq(7)", nRow).html('<spring:message code="project.type.not.approve" />');
                }

                if (aData["type"] == 'translate') {
                    $("td:eq(1)", nRow).html('<spring:message code="emp.profile.translation" />');
                    $("td:eq(4)", nRow).html('');
                    $("td:eq(5)", nRow).html('');
                    $("td:eq(6)", nRow).html('');
                    $("td:eq(7)", nRow).html('');
                    $("td:eq(8)", nRow).html('');
                    $("td:eq(9)", nRow).html('');
                }

            },
            "aoColumnDefs": [
                {
                    "aTargets": [9],
                    "mData": "imgPath",
                    "mRender": function (data, type, full) {
                        if(data !="" && data !=null){
                            return '<a class="open-AddBookDialog" data-toggle="modal" onclick="viewpicture(\''+data+'\')" href="#exampleModal1" style="text-decoration: underline;"><img src="${_ctx}'+data+'"  height="50px" width="50px"></a>';
                        }else{
                            data="/resources/Upload/images/no-image.jpg";
                            return '<a class="open-AddBookDialog" data-toggle="modal" onclick="viewpicture(\''+data+'\')" href="#exampleModal1" style="text-decoration: underline;"><img src="${_ctx}'+data+'"  height="50px" width="50px"></a>';
                        }
                    }
                },
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
                {
                    "aTargets": [4],
                    "mData": "price",
                    render: $.fn.dataTable.render.number( ',', '.', 0)
                },
                {
                    "aTargets": [6],
                    "mData": "amount",
                    "mRender": function (data, type, full) {
                        return $.fn.dataTable.render.number( ',', '.', 0).display(Math.abs(data));
                    }
                }

            ]
        });
        table.on( 'draw', function () {
            mainPrinTable(tagid);
            location.href = "/project/project/detail.hs?pjId=${pjId}&dpId=${dpId}&<%=backSearch%>";
        } );
    }

    function mainPrinTable (tagid){
        var hashid = "#"+ tagid;
        var tagname =  $(hashid).prop("tagName").toLowerCase();
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
            head += '<link href="${_ctx}/resources/css/project/item-day-project.css" rel="stylesheet" />';

        var allcontent = head + "<body  onload='window.print()' >" + "<" + tagname + attributes + ">" +  divToPrint + "</" + tagname + ">" +  "</body></html>"  ;
        var newWin= window.open("");
        newWin.document.write(allcontent);
        //newWin.print();
        newWin.document.close();
        setTimeout(function(){newWin.close();},50);
    }

    function sumFileSize(){
        var totalFileSize=0;
        <c:forEach items="${lstFile}" var="lst">
        var fileSizeInit='${lst.fileSize}';
        var fileSize=parseFloat(fileSizeInit.split(' ')[0]);
        var fileExtend=fileSizeInit.split(' ')[1];
        switch(fileExtend) {
            case 'Bytes':
                fileSize=(fileSize/1024)/1024;
                break;
            case 'KB':
                fileSize=fileSize/1024;
                break;
            case 'GB':
                fileSize=fileSize*1024;
                break;
            default:
                fileSize=fileSize;
        }
        totalFileSize += fileSize;
        </c:forEach>
        $("#titlefileSmall").html(totalFileSize.toFixed(3));
    }
</script>