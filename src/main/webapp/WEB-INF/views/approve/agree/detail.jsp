<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
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
            <div class="panel panel-inverse">
                <div class="panel-heading">
                    <h3 class="panel-title"><spring:message code='approval.tab.sequence.title'/></h3>
                    <c:if test="${empDTO.translationYn =='Y' || empDTO.adminYn =='Y' }" >
                        <button type="button" data-toggle="modal" data-target="#translationCommentModal" class="btn btn-warning" ><i class="fas fa-cogs"></i> <spring:message code='button.add.translate.title'/></button>
                    </c:if>
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
            <!-- lstTransComment -->
            <!-- lstTranslation -->
        </div>
    </div>
    <br>
</div>
<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-M">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel"><spring:message code="approval.modal.approval.form.title" /></h5>
                <button type="button" class="btn-close" data-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body bg-light">
                <div class="row">
                    <form id="frm-confirm-approval" method="post" action="/approval/request/update.hs">
                        <input type="label" id="signLineId" name="signLineId" hidden="true">
                        <input type="label" id="approvalId" name="approvalId" hidden="true" value="${form.approvalId}">
                        <input type="label" id="empCd" name="empCd" hidden="true" value="${form.empCd}">
                        <input type="label" id="approvalStatus" name="approvalStatus" hidden="true">
                        <input type="label" id="contents" name="contents" hidden="true">
                        <input type="label" id="memo" name="memo" hidden="true">
                    </form>
                    <div class="col-xl-5">
                        <div class="panel panel-inverse">
                            <div class="panel-heading">
                                <h3 class="panel-title">
                                    <spring:message code="approval.tab.approval.confirm.title" />
                                </h3>
                            </div>
                            <div class="panel-body">
                                <label class="container-radio">
                                    <spring:message code="approval.value.approval.approval.title" />
                                    <input type="radio" checked="checked" name="radio" value="3">
                                    <span class="checkmark"></span>
                                </label>
                                <label class="container-radio">
                                    <spring:message code="approval.value.approval.cancel.title" />
                                    <input type="radio" name="radio" value="4">
                                    <span class="checkmark"></span>
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="col-xl-7">
                        <div class="panel panel-inverse">
                            <div class="panel-heading">
                                <h3 class="panel-title"><spring:message code="approval.field.memo.title" /></h3>
                            </div>
                            <div class="panel-body">
                                <div id="modal-sequence-emp">
                                     <textarea id="memmoApproval" name="" rows="3" cols="30"></textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code="button.close.title" /></button>
                <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="funcConfirmApproval()"><spring:message code="button.save.title" /></button>
            </div>
        </div>
    </div>
    <form id="frmUpdate" method="post" action="">
        <input type="label" hidden name="approvalId" value="${form.approvalId}">
        <input type="label" hidden name="approvalStatus" value="1">
    </form>
</div>

<!-- Translation modal -->
<div class="modal fade" id="translationModal" tabindex="-1" aria-labelledby="translationModalLabel" aria-hidden="true">
    <form:form modelAttribute="form" id="frmAddTranslation" cssClass="form-inline" method="POST" action="${_ctx}/approvalTranslation/add.hs">
        <input type="label" name="approvalId" value="${form.approvalId}" hidden>
        <input type="label" name="title" value="${form.title}" hidden>
        <div class="modal-dialog modal-M modal-xl">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="translationModalLabel"><spring:message code="approval.modal.translation.form.title"/></h5>
                    <button type="button" class="btn-close" data-dismiss="modal" aria-label="Close"></button>
                </div>

                <div class="modal-body bg-light">
                    <form:textarea path="contents" cssClass="ckeditor form-control w-100"></form:textarea>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code="button.close.title"/></button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="fnSubmitTranslation()" ><spring:message code="button.add.translate.title"/></button>
                </div>
            </div>
        </div>
    </form:form>
</div>
<!-- Translation comment modal -->
<div class="modal fade" id="translationCommentModal" tabindex="-1" aria-labelledby="translationCommentModalLabel" aria-hidden="true">
    <form:form modelAttribute="form" id ="frmAddCommentTranslation"  method="POST" action="${_ctx}/approvalTranslation/commentAdd.hs">
        <input type="label" name="title" value="${form.title}" hidden>
        <input type="label" hidden name="approvalId" value="${form.approvalId}">
        <div class="modal-dialog modal-M modal-xl">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="translationCommentModalLabel"><spring:message code="approval.modal.translation.form.title"/></h5>
                    <button type="button" class="btn-close" data-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body bg-light">
                    <!-- <div class="panel-body"> -->
                        <c:choose>
                            <c:when test="${empSign.size() > 0}">
                                <table class="table table-bordered mt-2 mb-0">
                                    <thead>
                                        <tr>
                                            <th class="td-approval-translation">#</th>
                                            <th class="td-approval-translation"><spring:message code="approval.field.language.title" /></th>
                                            <th class="td-approval-translation"><spring:message code="approval.field.name.title" /></th>
                                            <th class="td-approval-translation"><spring:message code="approval.field.memo.title" /></th>
                                            <th class="td-approval-translation"><spring:message code="approval.field.content.title" /></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${empSign}" var="lst" varStatus="loop">
                                        <input type="label" name="arrSignLineId" value="${lst.signLineId}" hidden>
                                        <tr>
                                            <td width="5%">${loop.index +1}</td>
                                            <td width="15%">
                                                <select id="langCd" name="langCd" class="custom-select custom-select-sm form-control form-control-sm mr-1">
                                                    <c:forEach items="${lstLang}" var="lstL">
                                                        <option value="${lstL.langCd}">${lstL.langName}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td width="15%">${lst.empId}</td>
                                            <td width="35%">${lst.memo}</td>
                                            <td width="35%"><textarea id="memmoApproval" name="contents" rows="3" cols="50%"></textarea></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                                </c:when>
                            <c:otherwise>
                                <td class="text-center" colspan="14"><i class="fas fa-inbox"></i> <spring:message code="main.index.datastatus" /></td>
                            </c:otherwise>
                        </c:choose>
                    <!-- </div> -->
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code="button.close.title" /></button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="fnSubmitCommentTranslation()" ><spring:message code="button.add.translate.title" /></button>
                </div>
            </div>
        </div>
    </form:form>
</div>
<script>

    function funcConfirmApproval() {

        $('#signLineId').val($('#approvalSignLineId').val());
        $('#approvalStatus').val($("input[name='radio']:checked").val());
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
    function fnUpdate() {
        $('#frmUpdate').submit();
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