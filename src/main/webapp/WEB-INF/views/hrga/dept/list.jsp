<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>

		<div id="content" class="content">
			<h1 class="page-header cursor-default">
				<spring:message code='emp.list.bigtitle'/> - <spring:message code='emp.list.department'/>
				<br>
				<small>
					<spring:message code='emp.list.department.detail'/>
				</small>
			</h1>
			<div class="panel panel-inverse">
				<div class="panel-heading">
					<h4 class="panel-title">
						<spring:message code='dept.list.search' />
					</h4>
				</div>
				<tr>
					<form:form modelAttribute="search" action="${_ctx}/deptcontrol/dept/list.hs" method="get"
						cssClass="form-inline">
						<table class="table mb-0">
							<tr>
								<td style="background: #f0f4f6;" style="text-align: center;"><label
										style="text-align: center; margin-top: 8px">
										<spring:message code='dept.list.searchrequest' />
									</label></td>
								<td colspan="3">
									<form:select path="type"
										cssClass="custom-select custom-select-sm form-control form-control-sm mr-1">
										<form:option value=""><spring:message code='dateboard.notice.company.luachon'/></form:option>
										<form:option value="DEPT_NAME">
											<spring:message code='dept.list.dept' />
										</form:option>
										<form:option value="DEPT_PARENT_NAME">
											<spring:message code='dept.list.deptparent' />
										</form:option>
									</form:select>
									<form:input path="keyword" cssClass="form-control form-control-sm mr-1" />
									<button type="button" class="btn btn-primary search_btn">
										<spring:message code='dept.list.search' />
									</button>
								</td>
							</tr>

						</table>
					</form:form>
				</tr>
			</div>
			<div style="text-align: right; padding-bottom: 10px;">
				<button type="button" id="go_write_btn" class="btn btn-primary">
					<spring:message code='dept.list.add' />
				</button>
			</div>
			<div class="panel panel-inverse">

				<div class="panel-heading">
					<h4 class="panel-title">
						<spring:message code='emp.list.information' />
					</h4>
				</div>
				<div class="panel-body">
					<table class="table mb-0">
						<thead>
							<tr>
								<th style="text-align: center;">
									<spring:message code='dept.list.no' />
								</th>
								<th style="text-align: center;">
									<spring:message code='dept.list.level' />
								</th>
								<th style="text-align: center; width: 30%;">
									<spring:message code='dept.list.dept' />
								</th>
								<th style="text-align: center;">
									<spring:message code='dept.list.deptparent' />
								</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${getDeptList.size() == 0}">
									<tr>
										<td class="cursor-pointer" style="text-align: center; vertical-align: inherit;" colspan="100%">
											<spring:message code='main.index.datastatus' />
										</td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach items="${getDeptList}" var="list">
										<tr class="sel1 board_view" day-work-id="${list.rownum}" style="height: 73px;">
											<td class="go_view_btn cursor-pointer" dept-id="${list.deptCd}" style="text-align: center; vertical-align: inherit;">${list.rownum}</td>
											<td class="go_view_btn cursor-pointer" dept-id="${list.deptCd}" style="text-align: center; vertical-align: inherit;">${list.deptLevel}</td>
											<td class="go_view_btn cursor-pointer" dept-id="${list.deptCd}"
												style="text-align: center;vertical-align: inherit;">${list.deptName}</td>
											<td class="go_view_btn cursor-pointer" dept-id="${list.deptCd}" style="text-align: center;vertical-align: inherit;">${list.deptParentName}</td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
							
						</tbody>
					</table>
				</div>

				<div class="row">
					<div class="col-sm-12 col-md-12">
						<div class="dataTables_paginate paging_simple_numbers" id="data-table-responsive_paginate">
							<pg:paging page="${search}" />
						</div>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			$(document).ready(function () {
				$(".go_view_btn").click(function () {
					let deptId = $(this).attr('dept-id');
					location.href = "/deptcontrol/dept/view.hs?deptCd=" + deptId;
				});

				$("#go_write_btn").click(function () {
					location.href = "/deptcontrol/dept/write.hs";
				});

				$(".search_btn").click(function () {
					$("#search").submit();
				});
			});
		</script>