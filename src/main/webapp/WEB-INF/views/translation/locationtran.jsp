<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>

        <div id="content" class="content">
            <h3 class="page-title">
                <spring:message code='translation.bigtitle' />
                <br>
                <small>
                    <spring:message code='detail.message' />
                </small>
            </h3>

            </button>
            <!-- html -->
            <table id="loca" class="table table-striped table-bordered" style="width:100%;text-align: center">
                <thead>
                    <tr style="background-color: #1c5691;color: white">
                        <th>
                            <spring:message code='translation.stt' />
                        </th>
                        <th>
                            <spring:message code='work.add.department' />
                        </th>
                        <th>
                            <spring:message code='translation.employee' />
                        </th>
                        <th>
                            <spring:message code='emp.list.phone' />
                        </th>
                        <th>
                            <spring:message code='translation.location' />
                        </th>
                        <th>
                            <spring:message code='gallery.datetime' />
                        </th>
                        <th>
                            <spring:message code='author.list.status' />
                        </th>
                        <th>
                            <spring:message code='author.list.function' />
                        </th>

                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${lst}" var="lst">
                        <tr style="${lst.styleStrOpen}">
                            <td>${lst.empCd}</td>
                            <td>${lst.deptName}</td>
                            <td>${lst.empName}</td>
                            <td>${lst.phone}</td>
                            <td>${lst.locationTran}</td>
                            <td>${lst.locationDate}</td>
                            <td>${lst.locationStatus}</td>
                            <c:choose>
                                <c:when test="${lst.empCd == currentEmp || CAdmin=='Y' }">
                                    <td>
                                        <button class="open-AddBookDialog btn btn-primary" type="button"
                                            data-toggle="modal" data-id="${lst.empCd}" href="#exampleModal">
                                            <spring:message code='dragdrop.save' />
                                        </button>
                                    </td>
                                </c:when>
                                <c:otherwise>
                                    <td></td>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                    </c:forEach>

                </tbody>
            </table>
            <!-- Modal -->
            <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
                aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">
                                <spring:message code='author.list.update' />
                            </h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <form id="addform" name="addform" method="post">
                                <input type="text" value="" hidden id="empCd" name="empCd" />
                                <div class="form-group">
                                    <strong for="inputState">
                                        <spring:message code='dateboard.notice.company.contents' />
                                    </strong>
                                    <textarea name="location_tran" id="location_tran" class="form-control"></textarea>
                                    <select id="location-status" style="margin-top: 10px;width: 150px;height: 30px;">
                                        <option value="">
                                            <spring:message code='work.detail.status' />
                                        </option>
                                        <c:forEach items="${typeCodeList}" var="list">
                                            <option value="${list.CCodeValue}">${list.CCodeName}</option>
                                        </c:forEach>
                                    </select>

                                </div>

                            </form>


                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" onclick="updateTr()">
                                <spring:message code='author.list.update' />
                            </button>
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                <spring:message code='translation.close' />
                            </button>

                        </div>
                    </div>
                </div>
            </div>


        </div>
        <!-- script -->

        <script>
            $(document).ready(function () {

                $(document).on("click", ".open-AddBookDialog", function () {
                    var myBookId = $(this).data('id');
                    $("#empCd").val(myBookId);

                });
                var lang_dt;
                if ('${_lang}' == 'ko') {
                    lang_dt = lang_kor;
                } else if ('${_lang}' == 'en') {
                    lang_dt = lang_en;
                } else {
                    lang_dt = lang_vt;
                }
                $('#loca').DataTable({
                    "order": [[6, 'asc'], [5, 'desc']],
                    'iDisplayLength': 10,
                    "responsive": true, "lengthChange": false, "autoWidth": false,
                    "language": lang_dt,
                });

                if ('${title}' != '') {
                    if ('${title}' == 'success') {
                        //toastr['${title}']('${message}');
                        toastr.success('', "<spring:message code='notification.index.success' />", { timeOut: 1000 });
                    } else {
                        toastr.error('', "<spring:message code='notification.index.error' />", { timeOut: 1000 });
                    }
                }
                $('#location-status').change(function () {
                    var valLocationStatus=$('#location-status').val();
                    if(valLocationStatus ==1){
                        $("#location_tran").val("회사");
                        $("#location_tran").prop('disabled', true);
                    }else{
                        $("#location_tran").val("");
                        $("#location_tran").prop('disabled', false);
                    }
                });
            });

            function updateTr() {
                if ($('#location_tran').val().trim() != "" && $('#location-status').val().trim() != "") {
                    on();
                    console.log($('#empCd').val());
                    console.log($('#location_tran').val().trim())
                    console.log($('#location-status').val().trim())
                    var settings = {
                        "url": "/translation/updateTranlocatoion",
                        "method": "POST",
                        "headers": {
                            "Content-Type": "application/json"
                        },
                        "data": JSON.stringify({
                            "empCd": $('#empCd').val(),
                            "locationTran": $('#location_tran').val().trim(),
                            "locationStatus": $('#location-status').val().trim()
                        }),
                    };

                    $.ajax(settings).done(function (response) {
                        location.href = "/translation/locationtran.hs";
                        off();
                    });
                } else {
                    toastr['error']('Input reason and location!');
                }
            }

        </script>