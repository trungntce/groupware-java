<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Modal -->
<div class="modal fade" id="modal-approval-form" tabindex="-1" role="dialog" aria-labelledby="modal-approval-form-label" aria-hidden="true">
  <div class="modal-dialog modal-xl" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="modal-approval-form-label"><spring:message code="approval.modal.approval.form.title" /></h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <div class="row">
            <c:forEach items="${forms}" var="item" varStatus="loop">
            <div class="col-2">
                <a href="/approval/signWrite.hs?formId=${item.formId}">
                    <div class="card">
                        <div class="card-body">
                            <i class="fas fa-book-open mr-2"></i>
                            ${item.formName}
                        </div>
                    </div>
                </a>
            </div>
            </c:forEach>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code="button.close.title"/></button>
        <button type="button" class="btn btn-primary" onclick="window.location.href='/approval/signWrite.hs'"><spring:message code="button.create.title"/></button>
      </div>
    </div>
  </div>
</div>
