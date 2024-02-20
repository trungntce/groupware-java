<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
<link rel="stylesheet"
      href="https://cdn.jsdelivr.net/gh/bbbootstrap/libraries@main/choices.min.css">
<script src="https://cdn.jsdelivr.net/gh/bbbootstrap/libraries@main/choices.min.js"></script>

<link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css"
      rel="stylesheet">
<script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
<c:set var="test" value="${detailDTO.regDt}" />

<%
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
    String optionSearchGet = request.getParameter("optionSearch");
    if (optionSearchGet == null || optionSearchGet == "null") {
        optionSearchGet = "";
    }
    String inputSearchGet = request.getParameter("inputSearch");
    if (inputSearchGet == null || inputSearchGet == "null") {
        inputSearchGet = "";
    }
    String displayStartPra = request.getParameter("displayStartPra");
    if (displayStartPra == null || displayStartPra == "null") {
        displayStartPra = "0";
    }
    String translationStatusGet = request.getParameter("translationStatus");
    if (translationStatusGet == null || translationStatusGet == "null") {
        translationStatusGet = "";
    }
    String backSearch="start_date="+start_date+"&end_date="+end_date+"&levelDept="+levelDeptGet+"&optionSearch="+optionSearchGet+"&inputSearch="+inputSearchGet+"&displayStartPra="+displayStartPra+"&translationStatus="+translationStatusGet;
%>
<link href="${_ctx}/resources/js/plugins/jstree/dist/themes/default/style.min.css" rel="stylesheet" />
<div id="content" class="content">
    <h1 class="page-header cursor-default">
        <spring:message code='dateboard.notice.company.nhom' /> - <spring:message code='author.list.bigtitle' />
        <br>
        <small><spring:message code='detail.message' /></small>
    </h1>
    <div class="text-right pb-2">
        <c:choose>
            <c:when test="${Permission=='Y' || empCd==boardDTO.empCd}">
                <button type="button" id="go_edit_btn" class="btn btn-purple" board-id="${boardId}"><spring:message code='emp.list.edit' /></button>
            </c:when>
        </c:choose>
        <button type="button" class="btn btn-primary" onclick="back()"><spring:message code='work.detail.back' /></button>
    </div>
    <div class="row">
        <div class="col-xl-12">
            <div class="panel panel-inverse">
                <div class="panel-heading">
                    <h4 class="panel-title"><spring:message code='emp.list.information' /></h4>
                </div>
                <div class="panel-body">
                    <table class="table mb-0">
                        <tr>
                            <td style="background: #f0f4f6; width: 110px;"><font color="#555555"><spring:message code='dateboard.notice.company.title' /></font></td>
                            <td>${boardDTO.title}</td>
                        </tr>
                        <tr>
                            <td style="background: #f0f4f6;"><font color="#555555"><spring:message code='dateboard.notice.company.empname' /></font></td>
                            <td>${boardDTO.empName}</td>
                        </tr>
                        <tr>
                            <td style="background: #f0f4f6;"><font color="#555555"><spring:message code='dateboard.notice.company.position' /></font></td>
                            <td>${boardDTO.positionName}</td>
                        </tr>
                        <tr>
                            <td style="background: #f0f4f6;"><font color="#555555"><spring:message code='dateboard.notice.company.boardtype' /></font></td>
                            <td>${boardDTO.boardTypeCd}</td>
                        <tr>
                            <td style="background: #f0f4f6;"><font color="#555555"><spring:message code='dateboard.notice.company.lang' /></font></td>
                            <td>${boardDTO.langName}</td>
                        </tr>
                        <tr>
                            <td style="background: #f0f4f6;"><font color="#555555"><spring:message code='dateboard.notice.company.starttime' /></font></td>
                            <td>${boardDTO.notiStartDt}</td>
                        </tr>
                        <tr>
                            <td style="background: #f0f4f6;"><font color="#555555"><spring:message code='dateboard.notice.company.endtime' /></font></td>
                            <td>${boardDTO.notiEndDt}</td>
                        </tr>
                        <tr>
                            <td style="background: #f0f4f6;"><font color="#555555"><spring:message code='dateboard.notice.company.contents' /></font></td>
                            <td>${boardDTO.contents}</td>
                        </tr>
                        <tr>
                            <td style="background: #f0f4f6;"><spring:message code='work.detail.workfunction' /></td>
                            <td><button type="button" class="btn btn-primary"onclick="addtran('${boardDTO.boardId}')"><spring:message code='work.detail.addTran' /></button></td>
                        </tr>
                        <tr>
                            <td style="background: #f0f4f6;font-weight: bold;"><spring:message code='work.detail.translation' /></td>
                            <td>
                                <div id="aTran">
                                    <table id="tbTran" class="table mb-auto">
                                        <thead>
                                            <tr>
                                                <th class="min-tablet"><spring:message code='work.detail.language' /></th>
                                                <th class="all"><spring:message code='work.detail.empName' /></th>
                                                <th><spring:message code='work.detail.title' /></th>
                                                <th><spring:message code='work.detail.contents' /></th>
                                                <th class="min-tablet"><spring:message code='work.detail.registerDate' /></th>
                                                <th class="min-tablet"><spring:message code='work.add.edit' /></th>
                                                <th class="min-tablet"><spring:message code='work.detail.workfunction' /></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:choose>
                                                <c:when test="${detailTran.size()==0}">
                                                    <td class="cursor-pointer text-center" board-id="${list.boardId}" colspan="100%"><spring:message code='main.index.datastatus' /></td>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:forEach items="${detailTran}" var="ltk">
                                                        <tr>
                                                            <td>${ltk.langName}</td>
                                                            <td>${ltk.empName}</td>
                                                            <td>${ltk.title}</td>
                                                            <td>${ltk.contents}</td>
                                                            <td>${ltk.regDt}</td>
                                                            <td>
                                                                <button type="button" class="btn btn-danger text-center" onclick="editTrans('${ltk.translationId}','${boardDTO.boardId}')">
                                                                    <spring:message code='work.detail.edit' />
                                                                </button>
                                                            </td>
                                                            <td>
                                                                <button type="button" class="btn btn-danger" onclick="delTran(${ltk.translationId})"><spring:message code='work.detail.delete' /></button>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </c:otherwise>
                                            </c:choose>
                                        </tbody>
                                    </table>
                                </div>
                            </td>
                        </tr>

                    </table>
                </div>
            </div>
        </div>
    </div>
    <h1 class="page-title">
        <small><spring:message code='file.attach' /></small>
    </h1>

    <div class="panel panel-inverse">
        <div style="height:350px;overflow: auto; ">
            <div class="m-table">
                <table id="tbFile" class="table table-striped table-bordered w-100 text-center">
                    <thead>
                        <tr class="text-center text-white" style="background-color: #1c5691;">
                            <th><spring:message code='work.list.stt' /></th>
                            <th><spring:message code='file.attach' /></th>
                            <th><spring:message code='file.size' /></th>
                            <th><spring:message code='search.title.regisdate' /></th>
                            <th><spring:message code='file.download' /></th>
                        </tr>
                    </thead>

                    <tbody>
                    <c:choose>
                        <c:when test="${lstFile.size()==0}">
                            <tr>
                                <td class="cursor-pointer text-center" colspan="100%"><spring:message code='main.index.datastatus' /></td>
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
                                        <a href="${lst.filePath}${lst.fileHashName}" class="btn btn-secondary view_file_download" download="${lst.fileName}">
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

</div>

<script type="text/javascript">
    $(document).ready(function () {
        $("#tbTran").dataTable({"responsive": true})
        var preCheck = '${boardDTO.useYn}';
        if(preCheck === 'N'){
            $("#content").html("<spring:message code='main.index.datastatus' />");
        }
        $("#go_edit_btn").click(function () {
            let boardId = $(this).attr('board-id');
            location.href = "/board/notice/group/edit.hs?boardId=" + boardId+"&<%=backSearch%>";
        });

        $("#go_list_btn").click(function () {
            location.href = "/board/notice/group/list.hs&<%=backSearch%>";
        });

    });
    function addtran(id) {
        location.href = "/board/notice/group/addTran.hs?id=" + id + "&loai=gboardtrans&type=group&<%=backSearch%>";
    }
    function delTran(id) {
        $.ajax({
            type: "POST",
            url: "/work/work/xoaTran",
            data: {
                id: id,
            },
            success: function (response) {
                location.href = "/board/notice/group/view.hs?boardId=${boardDTO.boardId}&<%=backSearch%>";
            },
            error: function (error) {
                toastr['error']('error !');
            }
        });
    }
    function editTrans(id, dw) {
        location.href = "/board/notice/group/edittrans.hs?transId=" + id + "&boardid=" + dw + "&loai=group&<%=backSearch%>";
    }
    function back() {
        location.href = "/board/notice/group/list.hs?<%=backSearch%>";
    }
</script>