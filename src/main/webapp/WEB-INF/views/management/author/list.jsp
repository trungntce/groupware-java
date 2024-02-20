<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
        <style>
            .div-button {
                width: 100%;
                height: 50px;
            }
        </style>
        <div id="content" class="content">
            <h1 class="page-header cursor-default">
                <spring:message code='author.list.smalltitle'/> - <spring:message code='emp.list.bigtitle'/>
                <br>
                <small>
                    <spring:message code='author.list.smalltitledetail'/>
                </small>
            </h1>

            <div class="panel panel-inverse">
                <div class="panel-heading">
                    <h4 class="panel-title"><spring:message code='author.list.search' /></h4>
                </div>

                <tr>
                    <form:form modelAttribute="author" method="get" cssClass="form-inline">
                        <table class="table mb-0">
                            <tr>
                                <td style="background: #f0f4f6;"><spring:message code='author.list.searchrequest' /></td>
                                <td colspan="3">
                                    <form:select path="type" cssClass="custom-select custom-select-sm form-control form-control-sm mr-1">
                                        <form:option value=""><spring:message code='search.selectoption.first' /></form:option>
                                        <form:option value="EMP_CD"><spring:message code='author.list.empid' /></form:option>
                                        <form:option value="EMP_NAME"><spring:message code='author.list.fullname' /></form:option>
                                        <form:option value="EMP_ID"><spring:message code='author.list.username' /></form:option>
                                    </form:select>
                                    <form:input path="keyword" cssClass="form-control form-control-sm mr-1" />
                                    <button type="button" class="btn btn-primary search_btn">
                                        <spring:message code='author.list.search' />
                                    </button>
                                </td>
                            </tr>

                        </table>
                    </form:form>
                </tr>
            </div>

            <div class="div-button">
                <button type="button" style="float: right;" class="btn btn-primary" onclick="transUpdate()">
                    <spring:message code='author.list.buttontrans' />
                </button>
            </div>

            <div class="panel panel-inverse">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <spring:message code='emp.list.information' />
                    </h4>
                </div>
                <div class="panel-body">
                    <div class="panel-body">
                        <table id="tbauthor" class="table mb-0 display nowrap w-100">
                            <thead>
                                <tr class="text-center">
                                    <th><spring:message code='search.selectoption.first' /></th>
                                    <th><spring:message code='author.list.no' /></th>
                                    <th><spring:message code='author.list.empid' /></th>
                                    <th><spring:message code='author.list.username' /></th>
                                    <th><spring:message code='author.list.fullname' /></th>
                                    <th><spring:message code='author.list.dept' /></th>
                                    <th><spring:message code='author.list.position' /></th>
                                    <th><spring:message code='author.list.setau' /></th>
                                    <th><spring:message code='author.list.function' /></th>
                                    <th><spring:message code='work.list.function' /></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${authorEMPDTOList.size() == 0}">
                                        <tr>
                                            <td class="cursor-pointer" style="text-align: center; vertical-align: inherit;" colspan="100%">
                                                <spring:message code='main.index.datastatus' />
                                            </td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach items="${authorEMPDTOList}" var="list">
                                            <tr class="sel1 board_view" day-work-id="${list.rownum}" style="height: 90px; border-bottom: 1px solid #e4e7ea;">
                                                <form:form modelAttribute="author" action="${_ctx}/management/author/update.hs" method="post" cssClass="form-inline">
                                                    <td style="text-align: center; vertical-align: inherit;" value=${list.rownum}><input class="cb-element" name="xoa" type="checkbox"value="${list.empCd}"></td>
                                                    <td style="text-align: center; vertical-align: inherit;" value=${list.rownum}>${list.rownum}</td>
                                                    <td style="text-align: center; vertical-align: inherit;" value=${list.rownum}>${list.empCd}</td>
                                                    <td class="go_view_btn cursor-pointer" emp-id="${list.empCd}"style="text-align: center; vertical-align: inherit;">${list.empId}</td>
                                                    <td style="text-align: center; vertical-align: inherit;">${list.empName}</td>
                                                    <td style="text-align: center; vertical-align: inherit;">${list.deptName}</td>
                                                    <td style="text-align: center; vertical-align: inherit;">${list.positionName}</td>
                                                    <td style="text-align: center; vertical-align: inherit;">
                                                        <form:select path="" id="${list.empCd}" style="width:auto" cssClass="custom-select custom-select-sm form-control form-control-sm mr-1">
                                                            <option><spring:message code='dateboard.notice.company.luachon' /></option>
                                                            <c:forEach items="${typeCodeList}" var="typeCodeList">
                                                                <form:option value="${typeCodeList.positionCd}">
                                                                    <c:out value="${typeCodeList.positionName}" />
                                                                </form:option>
                                                            </c:forEach>
                                                        </form:select>
                                                    </td>

                                                    <td style="text-align: center; vertical-align: inherit;">
                                                        <button type="button" emp-id="${list.empCd}" class="btn btn-primary update_btn">
                                                            <spring:message code='author.list.update' />
                                                        </button>
                                                    </td>
                                                    <td style="text-align: center; vertical-align: inherit;">
                                                        <button type="button" onclick="detail('${list.empCd}')" class="btn btn-success">
                                                            <spring:message code="work.list.viewdetail" />
                                                        </button>
                                                    </td>
                                                </form:form>
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
                                <pg:paging page="${author}" />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript">
            $(document).ready(function () {

                $("#tbauthor").DataTable({
                    responsive: true
                });
             

                $(".search_btn").click(function () {
                    $("#author").submit();
                });

                $(".manual_btn").click(function () {
                    window.location.href = "/management/author/custom.hs";
                });

                $(".update_btn").click(function () {
                    let empCd = $(this).attr('emp-id');
                    let getempCd = '#' + empCd;
                    let positionCd = $(getempCd).val();
                    submitFormUpdate(empCd, positionCd);
                });

                $(".go_view_btn").click(function () {
                    let empCd = $(this).attr('emp-id');
                    location.href = "/management/author/view.hs?empCd=" + empCd;
                });
            });

            var urlRemember = window.location.search.substring(1, 100);

            function submitFormUpdate(empCd, positionCd) {
                if (confirm("<spring:message code='work.detail.contents.change.confirm' />") == true) {
                    var form = {
                        "empCd": empCd,
                        "positionCd": positionCd
                    }
                    $.ajax({
                        type: 'POST',
                        url: "${_ctx}/management/author/update.hs",
                        cache: false,
                        data: JSON.stringify(form),
                        contentType: "application/json; charset=utf-8",

                        success: function (resp) {
                            window.location.href = "/management/author/view.hs?empCd=" + empCd + "&" + urlRemember;
                        }
                    })
                }

            }
            function detail(id) {
                location.href = "/management/author/view.hs?empCd=" + id + "&" + urlRemember;
            }
            function transUpdate() {
                var lstChecked = "";
                var myTable = $('#tbauthor').dataTable();
                var rowcollection = myTable.$('input[name="xoa"]:checked', { "page": "all" });
                rowcollection.each(function (index, elem) {
                    var checkbox_value = $(elem).val();
                    lstChecked += checkbox_value + ",";
                });
                if (lstChecked.trim() == "") {
                    toastr['error']('Delete error !');
                } else {
                    console.log(lstChecked);
                    $.ajax({
                        type: "POST",
                        url: "/management/author/transaction",
                        data: {
                            'lstChecked': lstChecked,
                        },
                        success: function (response) {
                            location.href = "/management/author/list.hs";
                            alert('Update finish!');
                        },
                        error: function (error) {
                            toastr['error']("Update error");
                        }
                    })
                }
            }
        </script>