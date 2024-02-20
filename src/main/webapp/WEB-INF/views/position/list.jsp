<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>

        <style>
            .position-wrapper{
                width: 100%;
                height: 50px;
            }
            .button-position{
                float: right;
                margin-left: 5px;
            }
        </style>

            <div id="content" class="content">
                <h1 class="page-header cursor-default">
                    <spring:message code='position.list.title'/> - <spring:message code='emp.list.bigtitle'/>
                    <br>
                    <small>
                        <spring:message code='position.list.titledetail'/>
                    </small>
                </h1>
                <div class="position-wrapper">
                    <button type="button" class="btn btn-primary button-position" onclick="addPo()">
                        <spring:message code='position.list.addnew' />
                    </button>
                    <button type="button" class="open-AddBookDialog btn btn-info button-position" onclick="deletePo()">Delete
                    </button>
                </div>
                
                <!-- html -->
                <table id="tbPo" class="table table-striped table-bordered" style="width:100%;text-align: center">
                    <colgroup>
                        <col width="4%">
                        <col width="32%">
                        <col width="32%">
                        <col width="32%">
                    </colgroup>
                    <thead>
                        <tr style="background-color: #1c5691;color: white">
                            <th style="text-align: center; vertical-align: inherit;">
                                <input type="checkbox" name="all" id="checkall" />
                            </th>
                            <th>
                                <spring:message code='position.list.level' />
                            </th>
                            <th>
                                <spring:message code='position.list.poname' />
                            </th>
                            <th>
                                <spring:message code='position.list.function' />
                            </th>
                            <!-- <th>
                                <spring:message code='position.list.useyn' />
                            </th> -->
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${lstPo}" var="lst">
                            <tr>
                                <td>
                                    <input class="cb-element" name="xoa" type="checkbox" value="${lst.positionCd}" />
                                </td>
                                <td>${lst.positionLevel}</td>
                                <td>${lst.positionName}</td>
                                <td>
                                    <button class="btn btn-warning" onclick="editPo('${lst.positionCd}')">
                                        <spring:message code='position.list.edit' />
                                    </button>
                                </td>
                                <!-- <td>
                                    <c:if test="${lst.useYn == 'Y'}">
                                        <spring:message code='position.list.usey' />
                                    </c:if>
                                    <c:if test="${lst.useYn == 'N'}">
                                        <spring:message code='position.list.usen' />
                                    </c:if>
                                    
                                </td> -->
                            </tr>
                        </c:forEach>
                    </tbody>

                </table>
                <!-- Modal -->
                <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
                    aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">Are you sure
                                    delete this Board ?</h5>
                                <input type="text" hidden="" value="" name="" id="giatri" />
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-primary" onclick="deletePo()">Delete
                                </button>
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- script -->
            <script>
                $(document).ready(function () {
                    $('#checkall').change(function () {
                        $('.cb-element').prop('checked', this.checked);
                    });
                    var lang_dt;
                    if ('${_lang}' == 'ko') {
                        lang_dt = lang_kor;
                    } else if ('${_lang}' == 'en') {
                        lang_dt = lang_en;
                    } else {
                        lang_dt = lang_vt;
                    }
                    $('#tbPo').DataTable({
                        "order": [[1, "asc"]],
                        "responsive": true, "lengthChange": false, "autoWidth": false,
                        "language": lang_dt,
                        "aoColumnDefs": [
                            {
                                "orderable": false,
                                "targets": 0
                            }
                        ],
                    });
                    if ('${title}' != '') {
                        if ('${title}' == 'success') {
                            //toastr['${title}']('${message}');
                            toastr.success('', "<spring:message code='notification.index.success' />", { timeOut: 1000 });
                        } else {
                            toastr.error('', "<spring:message code='notification.index.error' />", { timeOut: 1000 });
                        }
                    }
                });

                function deletePo() {

                    var lstChecked = "";
                    var myTable = $('#tbPo').dataTable();
                    var rowcollection = myTable.$('input[name="xoa"]:checked', { "page": "all" });
                    rowcollection.each(function (index, elem) {
                        var checkbox_value = $(elem).val();
                        lstChecked += checkbox_value + ",";
                    });

                    if (lstChecked.trim() == "") {
                        toastr['error']('Delete error !');
                    } else {
                        $.ajax({
                            type: "POST",
                            url: "/position/deletePo",
                            data: {
                                'lstChecked': lstChecked,
                            },
                            success: function (response) {
                                location.href = "/position/list.hs?title=success&message=Delete sucess";
                            },
                            error: function (error) {
                                toastr['error']('Delete error !');
                            }
                        })
                    }
                }
                function addPo() {
                    location.href = "/position/addPo.hs";
                }
                function editPo(id) {
                    location.href = "/position/editPo.hs?id=" + id;
                }

            </script>