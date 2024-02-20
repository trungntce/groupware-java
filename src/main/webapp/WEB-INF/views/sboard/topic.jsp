<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
<div id="content" class="content">
    <h3 class="page-title"><spring:message code='topic.bigtitle'/> <small><spring:message code='topic.smalltitle'/> </small></h3>
    <!-- html -->
    <div class="row">
        <div class="col-xl-12 ui-sortable">
            <div class="panel panel-inverse" style="">
                <div class="panel-heading ui-sortable-handle">
                    <h4 class="panel-title"><spring:message code='emp.list.information'/></h4>
                </div>
                <div class="panel-body">
                    <form id="myform" class="form-inline" action="">
                        <table class="table mb-0">
                            <tbody>
                            <tr>
                                <td style="background: #f0f4f6;width: 15%"><spring:message code='topic.boardtype'/></td>
                                <td style="width: 35%"><select style="width: 80%;" id="boardType" name="boardType"
                                                               class="custom-select custom-select-sm form-control form-control-sm mr-1">

                                    <option value="">--Choose--</option>

                                    <c:forEach items="${lstBoardType}" var="lst">
                                        <option value="${lst.id}">${lst.nameBoardType}</option>
                                    </c:forEach>

                                </select></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6;width: 15%"><spring:message code='topic.topicname'/></td>
                                <td style="width: 35%"><input style="width: 100%;" id="tpName" name="tpName"
                                                              class="form-control form-control-sm mr-1"
                                                              type="text"
                                                              value=""></td>
                                <td></td>
                                <td></td>
                            </tr>


                            <tr>
                                <td style="width: 15%">
                                    <button type="button" class="btn btn-success" onclick="addT()"><spring:message code='topic.bigtitle'/></button>

                                </td>
                                <td style="width: 35%"></td>
                                <td style="width: 15%"></td>
                                <td style="width: 35%"></td>

                            </tr>

                            </tbody>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>

<script>
    $(document).ready(function () {
        $('#myform').validate({
            rules: {
                boardType: {
                    required: true,
                },
                tpName: {
                    required: true,
                    minlength: 10,
                    maxlength: 500,
                },

            },

            messages: {
                boardType: {
                    required: "Please input boardType ",
                },
                tpName: {
                    required: "Please input Topic name ",
                    minlength: "Min Length is 10 character",
                    maxlength: "Max Length is 500 character",
                },
            },

        });

    });

    function addT() {

        var isValid = $('#myform').valid();
        if (isValid == true) {
            if ("${str}".indexOf('null') > -1) {
                toastr['error'](' you dont have this group !');
            } else {
                var settings = {
                    "url": "/board/addBoardType",
                    "method": "POST",
                    "headers": {
                        "Content-Type": "application/json"
                    },
                    "data": JSON.stringify({
                        "idBoardType": $("#boardType").val(),
                        "nameDmt": $("#tpName").val(),
                        "str": "${str}",
                    }),
                };
                $.ajax(settings).done(function (response) {
                    alert("Success");
                    location.reload();
                });
            }
        }

    }

</script>
