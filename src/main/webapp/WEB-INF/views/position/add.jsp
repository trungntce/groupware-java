<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>

            <div id="content" class="content">
                <h3 class="page-title">
                    <spring:message code='position.list.title'/> - <spring:message code='position.list.addnew' />
                    <br>
                    <small>
                        <spring:message code='detail.add' />
                    </small>
                </h3>
                <div class="row">
                    <div class="col-xl-12 ui-sortable">
                        <div class="panel panel-inverse" style="">
                            <div class="panel-heading ui-sortable-handle">
                                <h4 class="panel-title">
                                    <spring:message code='position.list.Information' />
                                </h4>
                            </div>
                            <div class="panel-body">
                                <form id="myform" class="form-inline" action="/hrm/emp/write.hs" method="post">

                                    <table class="table mb-0">
                                        <tbody>

                                            <tr>

                                                <td style="background: #f0f4f6;width: 15%">*
                                                    <spring:message code='position.list.poname' />
                                                </td>
                                                <td style="width: 35%">
                                                    <input style="width: 40%;" id="poName" name="poName"
                                                        class="form-control form-control-lg mr-1" type="text" value="">
                                                </td>


                                            </tr>
                                            <tr>
                                                <td style="background: #f0f4f6;width: 15%">*
                                                    <spring:message code='position.list.level' />
                                                </td>
                                                <td style="width: 35%">
                                                    <input style="width: 40%;" id="level" name="level"
                                                        class="form-control form-control-sm mr-1" type="number"
                                                        value="">
                                                </td>
                                            </tr>

                                            <tr>
                                                <td colspan="3">
                                                    <button type="button" value="" onclick="them()"
                                                        class="btn btn-success">
                                                        <spring:message code='position.list.addnew' />
                                                    </button>
                                                    <button type="button" value="" onclick="back()"
                                                        class="btn btn-primary">Close</button>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- script -->

            <script>
                $(document).ready(function () {

                    $('#myform').validate({
                        rules: {

                            poName: {
                                required: true,
                                minlength: 3,
                                maxlength: 100,

                            },
                            level: {
                                required: true,


                            }

                        },
                        messages: {
                            poName: {
                                required: "Please input Position Name ",
                                minlength: "Min Length is 3 character",
                                maxlength: "Max Length is 100 character",

                            },
                            level: {
                                required: "Please input level ",



                            }


                        },

                    });


                });

                function them() {
                    var isValid = $('#myform').valid();
                    if (isValid == true) {
                        on();
                        var settings = {
                            "url": "/position/themPo",
                            "method": "POST",
                            "headers": {
                                "Content-Type": "application/json"
                            },
                            "data": JSON.stringify({
                                "positionName": $("#poName").val(),
                                "positionLevel": $("#level").val()

                            }),
                        };

                        $.ajax(settings).done(function (response) {
                            location.href = "/position/list.hs?title=success&message=Add new sucess";
                            off();
                        });

                    } else {
                        off();
                    }
                }


                function back() {
                    location.href = "/position/list.hs";
                }

            </script>