<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
<%
    String start_date = request.getParameter("start_date");
    if (start_date == null || start_date == "null") {
        start_date = "";
    }
    String end_date = request.getParameter("end_date");
    if (end_date == null || end_date == "null") {
        end_date = "";
    }
    String levelDeptGet = request.getParameter("levelDept");
    if (levelDeptGet == null || levelDeptGet == "null") {
        levelDeptGet = "1";
    }
    String translationStatusGet = request.getParameter("translationStatus");
    if (translationStatusGet == null || translationStatusGet == "null") {
        translationStatusGet = "";
    }
    String workStatusGet = request.getParameter("workStatus");
    if (workStatusGet == null || workStatusGet == "null") {
        workStatusGet = "";
    }
    String optionSearchGet = request.getParameter("optionSearch");
    if (optionSearchGet == null || optionSearchGet == "null") {
        optionSearchGet = "";
    }
    String inputSearchGet = request.getParameter("inputSearch");
    if (inputSearchGet == null || inputSearchGet == "null") {
        inputSearchGet = "";
    }
    String displayStartPra = request.getParameter("displayStartPra");
    if (displayStartPra == null || displayStartPra == "null") {
        displayStartPra = "0";
    }
    String backSearch="start_date="+start_date+"&end_date="+end_date+"&levelDept="+levelDeptGet+"&translationStatus="+translationStatusGet+"&workStatus="+workStatusGet+"&optionSearch="+optionSearchGet+"&inputSearch="+inputSearchGet+"&displayStartPra="+displayStartPra;
%>
<style>
    #tableInside table{
        width: 100% !important;
    }
</style>
<link href="${_ctx}/resources/js/plugins/jstree/dist/themes/default/style.min.css" rel="stylesheet" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/bbbootstrap/libraries@main/choices.min.css">
<link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/gh/bbbootstrap/libraries@main/choices.min.js"></script>
<script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
                <div id="content" class="content">
                    <h3 class="page-title">
                        <spring:message code='main.index.work'/> - <spring:message code='work.detail.tieude' />
                        <br>
                        <small>
                            <spring:message code='detail.message' />
                        </small>
                    </h3>
                    <div>
                        <div style="float: right">
                            <h4 class="panel-title">
                                <button type="button" class="btn btn-purple" onclick="editW()"><spring:message code='work.detail.edit' /></button>
                                <button type="button" class="btn btn-primary" onclick="back()"><spring:message code='work.detail.back' /></button>
                            </h4>
                        </div>
                    </div>
                    <script>
                        $(document).ready(function () {
                            $('input[type="checkbox"]').change(function () {
                                var typeCheck = this.id.split("_")[0];
                                var id = this.id.split("_")[1];
                                var trangthai = "";
                                if (typeCheck == "status") {
                                    trangthai = "0";
                                    if ($('#' + this.id).is(":checked")) {
                                        trangthai = "1";
                                    }
                                }
                                if (typeCheck == "approve") {
                                    trangthai = "N";
                                    if ($('#' + this.id).is(":checked")) {
                                        trangthai = "Y";
                                    }
                                }

                                $.ajax({
                                    type: "POST",
                                    url: "/work/work/updateInfor",
                                    data: {
                                        typeCheck: typeCheck,
                                        id: id,
                                        trangthai: trangthai
                                    },
                                    success: function (response) {
                                        toastr['success']('update sucess !');
                                    },
                                    error: function (error) {
                                        toastr['error']('error !');
                                    }
                                });
                            })
                        });
                    </script>

                    <div style="clear: both"></div>
                    <div class="row">
                        <div class="col-xl-12">
                            <div class="panel panel-inverse">
                                <div class="panel-heading">
                                    <h4 class="panel-title">
                                        <spring:message code='work.detail.information' />
                                    </h4>
                                </div>

                                <div class="panel-body">
                                    <table class="table mb-0">
                                        <tr>
                                            <td style="background: #f0f4f6;vertical-align: inherit; min-width: 90px; max-width: 90px;"><spring:message code='work.detail.empName' /></td>
                                            <td>${detailDTO.empName}</td>
                                        </tr>

                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='work.detail.dept' /></td>
                                            <td>${detailDTO.deptName}</td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='work.detail.position' /></td>
                                            <td>${detailDTO.positionName}</td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='work.detail.worksatus' /></td>
                                            <td>${detailDTO.workStatus}</td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='work.detail.title' /></td>
                                            <td>${detailDTO.title}</td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='work.detail.contents' /></td>
                                            <td>${detailDTO.contents}</td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='work.detail.important' /></td>
                                            <td>${detailDTO.importantYn}</td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='work.detail.workstart' /></td>
                                            <td>${detailDTO.workStartDt}</td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='work.detail.workend' /></td>
                                            <td>${detailDTO.workEndDt}</td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='work.detail.registerDate' /></td>
                                            <td>${detailDTO.regDt}</td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='work.detail.workfunction' /></td>
                                            <td><button type="button" class="btn btn-primary" onclick="addtran('${detailDTO.workId}')"><spring:message code='work.detail.addTran' /></button></td>
                                        </tr>

                                        <tr>
                                            <td style="background: #f0f4f6;font-weight: bold;">
                                                <spring:message code='work.detail.translation' />
                                            </td>
                                            <td>
                                                <div id="aTran">
                                                    <table id="tbTran" class="table mb-auto">
                                                        <thead>
                                                            <tr>
                                                                <th class="all"><spring:message code='work.detail.stt'/></th>
                                                                <th class="min-tablet"><spring:message code='work.detail.language' /></th>
                                                                <th><spring:message code='work.detail.empName' /></th>
                                                                <th class="min-tablet"><spring:message code='work.detail.title' /></th>
                                                                <th><spring:message code='work.detail.contents' /></th>
                                                                <th class="min-tablet"><spring:message code='work.detail.registerDate' /></th>
                                                                <th class="min-tablet"><spring:message code='work.detail.status' /></th>
                                                                <th class="min-tablet"><spring:message code='work.add.edit' /></th>
                                                                <th class="min-tablet"><spring:message code='work.detail.workfunction' /></th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach items="${detailTran}" var="ltk">
                                                                <tr>
                                                                    <td>${ltk.rownum}</td>
                                                                    <td>${ltk.langName}</td>
                                                                    <td>${ltk.empName}</td>
                                                                    <td>${ltk.title}</td>
                                                                    <td id="tableInside">${ltk.contents}</td>
                                                                    <td>${ltk.regDt}</td>
                                                                    <td>
                                                                        <input id="status_${ltk.translationId}"
                                                                            type="checkbox" ${ltk.translationStatus=="1"
                                                                            ?"checked":"" } data-toggle="toggle"
                                                                            data-on="<spring:message code='work.detail.button.translate.finish'/>"
                                                                            data-off="<spring:message code='work.detail.button.translate.notfinish'/>"
                                                                            data-onstyle="success"
                                                                            data-offstyle="danger">
                                                                    </td>
                                                                    <c:choose>
                                                                        <c:when test='${empDTO.empCd == ltk.empCd}'>
                                                                            <td>
                                                                                <button type="button" class="btn btn-danger text-center" onclick="editTrans('${ltk.translationId}','${detailDTO.workId}')"><spring:message code='work.detail.edit' /></button>
                                                                            </td>
                                                                            <td>
                                                                                <button type="button" class="btn btn-danger" onclick="delTran(${ltk.translationId})"><spring:message code='work.detail.delete'/></button>
                                                                            </td>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <td>
                                                                                <button type="button" class="btn btn-danger text-center" disabled><spring:message code='work.detail.edit'/></button>
                                                                            </td>
                                                                            <td>
                                                                                <button type="button" disabled class="btn btn-danger"><spring:message code='work.detail.delete' /></button>
                                                                            </td>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </tr>
                                                            </c:forEach>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;font-weight: bold;"><spring:message code='work.detail.coperation' /></td>
                                            <td>
                                                <div id="aco">
                                                    <table id="tbCo" class="table mb-auto">
                                                        <thead>
                                                            <tr>
                                                                <th class="all"><spring:message code='work.detail.stt' /></th>
                                                                <th><spring:message code='work.detail.empName' /></th>
                                                                <th class="min-tablet"><spring:message code='work.detail.dept' /></th>
                                                                <th><spring:message code='work.detail.position' /></th>
                                                                <th class="min-tablet"><spring:message code='work.detail.registerDate' /></th>
                                                                <th class="min-tablet"><spring:message code='work.detail.approve' /></th>
                                                                <th class="min-tablet"><spring:message code='work.detail.datecheck' /></th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach items="${lstCo}" var="lco">
                                                                <tr>
                                                                    <td>${lco.rownum}</td>
                                                                    <td>${lco.empName}</td>
                                                                    <td>${lco.deptName}</td>
                                                                    <td>${lco.positionName}</td>
                                                                    <td>${lco.regDt}</td>
                                                                    <td>
                                                                        <c:choose>
                                                                            <c:when test="${lco.empCd == empGui}">
                                                                                <input id="approve_${lco.workCooperationId}" type="checkbox" ${lco.workCheckYn=="Y" ?"checked":"" } data-toggle="toggle" data-on="<spring:message code='work.detail.approve'/>" data-off="<spring:message code='work.detail.notyet'/>" data-onstyle="success" data-offstyle="danger">
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <p style="color: #00cc66;font-weight: bold">
                                                                                    <c:choose>
                                                                                        <c:when test="${lco.workCheckYn == 'Y'}">
                                                                                            <spring:message code='work.detail.approve' />
                                                                                        </c:when>
                                                                                        <c:otherwise>
                                                                                            <spring:message code='work.detail.waiting' />
                                                                                        </c:otherwise>
                                                                                    </c:choose>
                                                                                </p>

                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </td>
                                                                    <td>${lco.workCheckDt}</td>
                                                                </tr>
                                                            </c:forEach>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                <!-- Modal -->

                <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel"><spring:message code='work.detail.addCo' /></h5>
                                <input type="text" hidden="" name="" id="giatri" />
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <form id="addform" name="addform" method="post">
                                    <div class="form-group">
                                        <strong for="inputState">
                                            <spring:message code='work.detail.dept' />
                                        </strong>
                                        <select id="department" class="form-control" name="department">
                                            <option value="0"><spring:message code='search.selectoption.first'/></option>
                                            <c:forEach items="${lstDept}" var="ltt">
                                                <option value="${ltt.deptCd}">${ltt.deptName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group" id="hihi">
                                        <strong for="inputState"><spring:message code='work.detail.empName' /></strong>
                                        <div id="song">
                                            <div class="choices" data-type="select-multiple" role="combobox" aria-autocomplete="list" aria-haspopup="true" aria-expanded="false" dir="ltr">
                                                <div class="choices__inner"><select id="choices-multiple-remove-button" placeholder="Select upto 5 tags" multiple="" class="choices__input is-hidden" tabindex="-1" aria-hidden="true" data-choice="active"></select>
                                                    <div class="choices__list choices__list--multiple"></div>
                                                    <input type="text" class="choices__input choices__input--cloned w-100" autocomplete="off" autocapitalize="off" spellcheck="false" role="textbox" aria-autocomplete="list" placeholder="Select...">
                                                </div>
                                                <div class="choices__list choices__list--dropdown" aria-expanded="false">
                                                    <div class="choices__list" dir="ltr" role="listbox" aria-multiselectable="true">
                                                        <div class="choices__item choices__item--choice has-no-choices">선택할 수 있는 옵션이 없습니다.</div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </form>

                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-primary" onclick="addCo()"><spring:message code='work.list.save' /></button>
                                <button type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code='translation.close' /></button>
                            </div>
                        </div>
                    </div>
                </div>
                </div>

                <script type="text/javascript">
                    $(document).ready(function () {

                        var preCheck = '${detailDTO.useYn}';
                        if(preCheck === 'N'){
                            $("#content").html("<spring:message code='main.index.datastatus' />");
                        }
                        
                        var lang_dt;
                        if ('${_lang}' == 'ko') {
                            lang_dt = lang_kor;
                        } else if ('${_lang}' == 'en') {
                            lang_dt = lang_en;
                        } else {
                            lang_dt = lang_vt;
                        }


                        var myTable = $('#tbCo').dataTable({ "responsive": true, "lengthChange": false, "autoWidth": false, "language": lang_dt });
                        var t = $('#tbTran').dataTable({ "responsive": true, "lengthChange": false, "autoWidth": false, "language": lang_dt });


                        if ('${title}' != '') {
                            if ('${title}' == 'success') {
                                //toastr['${title}']('${message}');
                                toastr.success('', "<spring:message code='notification.index.success' />", { timeOut: 1000 });
                            } else {
                                toastr.error('', "<spring:message code='notification.index.error' />", { timeOut: 1000 });
                            }
                        }
                    });

                    function addtran(id) {
                        location.href = "/work/mywork/addTran.hs?id=" + id + "&loai=mywork&<%=backSearch%>";
                    }

                    function back() {
                        location.href = "/work/mywork/list.hs?<%=backSearch%>";
                    }

                    function xoaCo(id) {
                        $.ajax({
                            type: "POST",
                            url: "/work/work/xoaCo",
                            data: {
                                id: id,
                            },
                            success: function (response) {
                                location.href = "/work/mywork/detail.hs?id=${detailDTO.workId}&title=success&message=Delete sucess&<%=backSearch%>";
                            },
                            error: function (error) {
                                toastr['error']('error !');
                            }
                        });
                    }

                    function delTran(id) {
                        $.ajax({
                            type: "POST",
                            url: "/work/work/xoaTran",
                            data: {
                                id: id,
                            },
                            success: function (response) {
                                location.href = "/work/mywork/detail.hs?id=${detailDTO.workId}&title=success&message=Delete sucess&<%=backSearch%>";
                            },
                            error: function (error) {
                                toastr['error']('error !');
                            }
                        });
                    }

                    function editW() {
                        location.href = "/work/mywork/editW.hs?workId=${detailDTO.workId}&<%=backSearch%>";
                    }

                    function editTrans(id, dw) {
                        location.href = "/work/mywork/editworktrans.hs?transId=" + id + "&WorkId=" + dw + "&loai=mywork&<%=backSearch%>";
                    }

                </script>