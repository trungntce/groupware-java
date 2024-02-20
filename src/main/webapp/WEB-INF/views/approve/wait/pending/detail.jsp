<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
<c:set var="_userInfo" value="${sessionScope._user}" scope="session"/>
<script type="text/javascript" src="${_ctx}/resources/js/ckeditor/ckeditor.js"></script>
<link rel="stylesheet" type="text/css" href="${_ctx}/resources/js/treeview/treeview.min.css">
<link rel="stylesheet" type="text/css" href="${_ctx}/resources/css/common.css">
<style>
    #frmPrint table{
        width: 100% !important;
    }
</style>
<div id="content" class="content">
    <h3 class="page-title">
        <spring:message code='common.detail.title'/><br>
        <small><spring:message code='detail.add' /></small><br>
        <button type="button" class="btn btn-primary" onclick="javascript:history.back()"><spring:message code='work.add.back' /></button>
        <button type="button" class="btn btn-info function-button priority-hide" onclick="fnPrint()"><spring:message code='project.list.print'/></button>
    </h3>
    <div class="row">
        <div class="col-xl-8 ui-sortable">
            <div class="panel panel-inverse">
                <div class="panel-heading">
                    <h3 class="panel-title"><spring:message code='approval.field.content.title'/></h3>
                </div>
                <div class="panel-body">
                    <table class="table mb-0" id="frmPrint">
                        <tr>
                            <td>${form.title}</td>
                        </tr>
                        <tr>
                            <td>${form.contents}</td>
                        </tr>
                        <tr>
                            <td>
                                <ul class="list-group list-group-horizontal ml-1" id="list-attach-file">
                                    <c:forEach items="${approvalFiles}" var="item" varStatus="loop">
                                        <li class="list-group-item position-relative float-left">
                                            <div class="text-center"><i class="fas fa-link text-secondary h3"></i></div>
                                            <a class="approval-attr-file" href="${_ctx}/upload/${item.filePath}/${item.fileHashName}"><small>${item.fileName}</small></a>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>

        <div class="col-xl-4 ui-sortable">
            <!-- Sign sequence -->
            <div class="panel panel-inverse">
                <div class="panel-heading">
                    <h3 class="panel-title"><spring:message code='approval.tab.sequence.title'/></h3>
                </div>
                <div class="panel-body">
                    <table class="table table-bordered mt-2 mb-0">
                        <thead>
                            <tr>
                                <th class="td-approval-translation"><spring:message code='approval.field.name.title'/></th>
                                <th class="td-approval-translation"><spring:message code='approval.field.role.title'/></th>
                                <th class="td-approval-translation"><spring:message code='approval.field.approval.status.title'/></th>
                                <th class="td-approval-translation"><spring:message code='approval.field.memo.title'/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:set var="isSign" value="0" />
                            <c:set var="idSignLine" value="0" />
                            <c:if test="${empSign.size() > 0}">
                                <c:forEach items="${empSign}" var="lst" varStatus="loop">                                       
                                    <tr>
                                        <td>${lst.empId}</td>
                                        <td>${lst.approvalRoleName}</td>
                                        <td>${lst.approvalStatusName}</td>
                                        <td>${lst.memo}</td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empSign.size() == 0}">
                                <td class="text-center" colspan="3"><i class="fas fa-inbox"></i> <spring:message code="main.index.datastatus" /></td>                        
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
            <!-- * Sign sequence -->

        </div>
    </div>
</div>
<script>

    function funcConfirmApproval(valConfirm) {

        $('#approvalStatus').val(valConfirm);
        $('#memo').val($('#memmoApproval').val());
        $('#contents').val($('#ckContents').val());
        $('#frm-confirm-approval').submit();
    }
    function fnPrint() {
        var divToPrint=document.getElementById("frmPrint");
        newWin= window.open("");
        newWin.document.write(divToPrint.outerHTML);
        newWin.print();
        newWin.close();
    }
    function fnAddTranslation(){
        $('#cke_1_contents').css("height","600px");
    }
    function fnSubmitTranslation() {
        $('#frmAddTranslation').submit();
    }
      function fnSubmitCommentTranslation() {
        $('#frmAddCommentTranslation').submit();
    }

</script>