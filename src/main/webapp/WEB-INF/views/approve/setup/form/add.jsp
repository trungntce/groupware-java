<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
<script type="text/javascript" src="${_ctx}/resources/js/ckeditor/ckeditor.js"></script>

<div id="content" class="content">
    <h3 class="page-title">
        <spring:message code='approval.page.setup.form.title' />
        <small><spring:message code='detail.add' /></small>
    </h3>
    
    <div class="row">
        <div class="col-xl-12 ui-sortable">
            <div class="panel panel-inverse">
                <div class="panel-heading">
                    <h3 class="panel-title"><spring:message code='approval.tab.form.title' /></h3>
                </div>
                <div class="panel-body">
                    <form:form modelAttribute="myform" cssClass="form-inline" method="POST">
                        <table class="table mb-0">
                            <tr>
                                <td style="background: #f0f4f6; width: 90px;"><spring:message code='approval.field.title.txt' /></td>
                                <td><form:input path="formName" cssClass="form-control form-control-sm mr-1 w-100" type="text"/></td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6;"><spring:message code='approval.field.content.title' /></td>
                                <td><form:textarea path="formContents" cssClass="ckeditor form-control w-100"/></td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <button class="btn btn-success"><spring:message code='button.title.submit'/></button>
                                    <button type="button" class="btn btn-primary" onclick="history.back()"><spring:message code='button.cancle.title' /></button>
                                </td>
                            </tr>
                        </table>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>