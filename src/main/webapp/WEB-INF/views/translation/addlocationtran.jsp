<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>

            <div id="content" class="content">
                <h3 class="page-title">
                    <spring:message code='translation.add' />
                    <spring:message code='translation.bigtitle' />
                    <spring:message code='translation.smalltitle' />
                </h3>
                <div class="row">
                    <div class="col-xl-12 ui-sortable">
                        <div class="panel panel-inverse" style="">
                            <div class="panel-heading ui-sortable-handle">
                                <h4 class="panel-title">
                                    <spring:message code='translation.Information' />
                                </h4>
                            </div>
                            <div class="panel-body">
                                <form id="myform" class="form-inline" action="/hrm/emp/write.hs" method="post">

                                    <table class="table mb-0">
                                        <tbody>

                                            <tr>

                                                <td style="background: #f0f4f6;width: 15%">*
                                                    <spring:message code='translation.workstatus' />
                                                </td>
                                                <td style="width: 35%"><select style="width: 30%;" id="workStatus"
                                                        name="workStatus"
                                                        class="custom-select custom-select-sm form-control form-control-sm mr-1">
                                                        <option value="">--Choose--</option>
                                                        <option value="1">
                                                            <spring:message code='translation.working' />
                                                        </option>
                                                        <option value="0">
                                                            <spring:message code='translation.absent' />
                                                        </option>

                                                    </select></td>


                                            </tr>
                                            <tr>
                                                <td style="background: #f0f4f6;width: 15%">*
                                                    <spring:message code='' />
                                                </td>
                                                <td style="width: 35%">
                                                    <input style="width: 40%;" id="LocationT" name="LocationT"
                                                        class="form-control form-control-sm mr-1" type="text" value="">
                                                </td>
                                            </tr>

                                            <tr>
                                                <td style="background: #f0f4f6;width: 15%">*
                                                    <spring:message code='translation.reason' />
                                                </td>
                                                <td style="width: 35%">
                                                    <input style="width: 40%;" id="noteT" name="noteT"
                                                        class="form-control form-control-sm mr-1" type="text" value="">
                                                </td>
                                            </tr>
                                            <tr>

                                                <td style="background: #f0f4f6;width: 15%">*
                                                    <spring:message code='translation.locationstatus' />
                                                </td>
                                                <td style="width: 35%"><select style="width: 30%;" id="locaStatus"
                                                        name="locaStatus"
                                                        class="custom-select custom-select-sm form-control form-control-sm mr-1">
                                                        <option value="">--Choose--</option>
                                                        <option value="Work">
                                                            <spring:message code='translation.work' />
                                                        </option>
                                                        <option value="OutSide">
                                                            <spring:message code='translation.outside' />
                                                        </option>
                                                        <option value="Patrol">
                                                            <spring:message code='translation.partrol' />
                                                        </option>

                                                    </select></td>

                                            </tr>

                                            <tr>
                                                <td colspan="3">
                                                    <button type="button" value="" onclick="them()"
                                                        class="btn btn-success">
                                                        <spring:message code='translation.add' />
                                                    </button>
                                                    <button type="button" value="" onclick="back()"
                                                        class="btn btn-primary">Close
                                                    </button>
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
                            workStatus: {
                                required: true,
                            },
                            LocationT: {
                                required: true,
                                minlength: 3,
                                maxlength: 500,

                            },
                            noteT: {
                                required: true,
                                minlength: 3,
                                maxlength: 1000,

                            },
                            locaStatus: {
                                required: true,
                            },


                        },
                        messages: {
                            workStatus: {
                                required: "Please input workStatus ",
                            },
                            LocationT: {
                                required: "Please input Location ",
                                minlength: "Min Length is 3 character",
                                maxlength: "Max Length is 500 character",

                            },
                            noteT: {
                                required: "Please input note ",
                                minlength: "Min Length is 3 character",
                                maxlength: "Max Length is 1000 character",

                            },
                            locaStatus: {
                                required: "Please input location Status ",
                            }


                        },

                    });


                });

                function them() {
                    on();
                    var isValid = $('#myform').valid();
                    if (isValid == true) {

                        var settings = {
                            "url": "/translation/themTran",
                            "method": "POST",
                            "headers": {
                                "Content-Type": "application/json"
                            },
                            "data": JSON.stringify({
                                "workStatus": $("#workStatus").val(),
                                "location": $("#LocationT").val(),
                                "note": $("#noteT").val(),
                                "locationStatus": $("#locaStatus").val()

                            }),
                        };

                        $.ajax(settings).done(function (response) {
                            location.href = "/translation/locationtran.hs";
                            off()
                        });

                    }
                    else {
                        off();
                    }

                }


                function back() {
                    location.href = "/translation/locationtran.hs";
                }

            </script>