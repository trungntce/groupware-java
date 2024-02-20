<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
<c:set var="_userInfo" value="${sessionScope._user}" scope="session"/>
<div id="content" class="content">
    <h3 class="page-title">
        <spring:message code='approval.page.register.title'/><br>
        <small><spring:message code='detail.add'/></small>
    </h3>
    <div class="row">
        <!-- Tab search -->
        <div class="col-xl-12 ui-sortable">
            <div class="panel panel-inverse">
                <div class="panel-heading">
                    <h3 class="panel-title"><spring:message code='search.title' /></h3>
                </div>
                <div class="panel-body">
                    <form id="frmSearch" name="search"  class="form-inline">
                        <input type="label" id="approvalStatus" name="approvalStatus" hidden="true">
                        <input type="label" id="dateFlag" name="dateFlag" hidden="true">
                        <table class="table mb-0">
                            <tbody>
                                <tr>
                                    <td style="background: #f0f4f6;font-weight: bold"><spring:message code='dateboard.notice.company.timkiem'/></td>
                                    <td>
                                        <select id="keywordType" name="keywordType" class="custom-select custom-select-sm form-control form-control-sm m-2">
                                            <option value="create_id">
                                                <spring:message code='approval.field.emp.id.title'/>
                                            </option>
                                            <option value="form_name" ${searchDTO.keywordType == 'form_name' ? "selected" : "" }>
                                                <spring:message code='approval.field.name.title'/>
                                            </option>
                                        </select>
                                        <input id="keyword" name="keyword" class="form-control form-control-sm m-2" type="text" value="${searchDTO.keyword}"/>
                                        <button type="submit" class="btn btn-sm btn-primary m-2"><spring:message code='dateboard.notice.company.timkiem'/></button>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
            </div>
        </div>
        <!-- * Tab search -->
        
        <!-- Tab List -->
        <div class="col-xl-12 ui-sortable">
            <div class="panel panel-inverse">
                <div class="panel-heading"><h3 class="panel-title"><spring:message code='common.list.title' /></h3></div>
                <div class="panel-body">
                    <table class="table table-striped table-bordered" style="width:100%;text-align: center">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th><spring:message code='approval.field.name.title'/></th>
                                <th><spring:message code='approval.field.emp.id.title'/></th>
                                <th><spring:message code='approval.field.created.date.title'/></th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:if test="${list.size() > 0}">
                                <c:forEach items="${list}" var="item" varStatus="loop">
                                <tr>
                                    <td>${loop.index + 1}</td>
                                    <td>${item.formName}</td>
                                    <td>${item.createId}</td>
                                    <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${item.createDate}"/></td>
                                    <td><a href="${_ctx}/approval/register/create.hs?formId=${item.formId}" class="btn btn-primary"><i class="fas fa-edit"></i></a></td>
                                </tr>
                                </c:forEach>
                            </c:if>
                            <c:if test="${list.size() == 0}">
                                <tr>
                                    <td colspan="5"><spring:message code="main.index.datastatus" /></td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                    <div class="mt-10px w-100">
                      <%-- 추후 필요에 따라 사용 --%>
                      <pg:paging page="${searchDTO}"/>
                    </div>
                </div>
            </div>
        </div>
        <!-- * Tab List -->
    </div>
</div>