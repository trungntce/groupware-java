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
<form:form modelAttribute="form">
<div id="content" class="content">
    <h3 class="page-title">
        <spring:message code='common.detail.title'/><br>
        <small><spring:message code='detail.add' /></small><br>
        <button type="button" class="btn btn-primary" onclick="javascript:history.back()"><spring:message code='work.add.back' /></button>
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
                            <td style="background: #f0f4f6; min-width: 90px; max-width: 90px;">
                                <spring:message code='approval.field.title.txt' />
                            </td>
                            <td>
                                <form:input path="title" cssClass="form-control form-control-sm mr-1 w-100"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="background: #f0f4f6;">
                                <spring:message code='approval.field.content.title' />
                            </td>
                            <td>
                                <form:textarea path="contents" cssClass="ckeditor form-control w-100"></form:textarea>
                            </td>
                        </tr>
                        <tr>
                            <td style="background: #f0f4f6;">
                                <spring:message code='file.attach'/>
                            </td>
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
                        <tr>
                            <td colspan="2">
                                <button type="submit" class="btn btn-success"><spring:message code='button.save.title'/></button>
                                <button type="button" class="btn btn-primary" onclick="history.back()"><spring:message code='work.add.back'/></button>
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
</form:form>