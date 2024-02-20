<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
<link href="${_ctx}/resources/js/plugins/jstree/dist/themes/default/style.min.css" rel="stylesheet" />
<script type="text/javascript" src="${_ctx}/resources/js/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="${_ctx}/resources/js/plugins/file-upload/js/file_upload.js"></script>
<style>
    input#notiStartDt {
        width: 50%;
    }
    input#title {
        width: 100%;
    }
</style>
<script>
    var i =0;
    <c:choose>
    <c:when test="${lstFile.size() >0}">
    i=${lstFile.size()};
    </c:when>
    </c:choose>
    var langCd='${_lang}';
    var urls="/board/notice/group/";
</script>
<div id="content" class="content">
    <h1 class="page-header cursor-default">
        <spring:message code='dateboard.notice.company.cty' /> - <spring:message code='dateboard.notice.company.add' />
        <br>
        <small><spring:message code='detail.add' /></small>
    </h1>

    <div style="text-align: right; padding-bottom: 10px;">
        <button type="button" class="btn btn-primary do_write_btn"><spring:message code='dateboard.notice.company.add' /></button>
        <button type="button" class="btn btn-primary go_list_btn"><spring:message code='work.addtran.back' /></button>
    </div>
    <div class="row">
        <div class="col-xl-12">
            <div class="panel panel-inverse">
                <div class="panel-heading">
                    <h4 class="panel-title"><spring:message code='emp.list.information' /></h4>
                </div>
                <div class="panel-body">
                    <form:form modelAttribute="board" action="${_ctx}/board/notice/company/write.hs" method="post" cssClass="form-inline">
                        <table class="table mb-0 w-100">
                            <tr>
                                <td style="background: #f0f4f6; width: 120px;"><span style="color:red;">&nbsp;*&nbsp;</span><spring:message code='dateboard.notice.company.boardtype' /></td>
                                <td>
                                    <c:if test="${rules <= 7}">
                                        <form:select path="deptCd" cssClass="custom-select custom-select-sm form-control form-control-sm mr-1 w-100">
                                            <option><spring:message code='dateboard.notice.company.luachon' /></option>
                                            <form:options items="${selectCompanyList}" itemValue="deptCd" itemLabel="deptName" />
                                        </form:select>
                                    </c:if>
                                    <c:if test="${rules > 7}">
                                        Company
                                    </c:if>
                                </td>
                            </tr>

                            <tr>
                                <td style="background: #f0f4f6;"><span style="color:red;">&nbsp;*&nbsp;</span><spring:message code='dateboard.notice.company.lang' /></td>
                                <td>
                                    <form:select path="langCd" cssClass="custom-select custom-select-sm form-control form-control-sm mr-1 w-100">
                                        <option value=""><spring:message code='dateboard.notice.company.luachon' /></option>
                                        <option value="ko"><spring:message code='lang.select.lang.ko' /></option>
                                        <option value="en"><spring:message code='lang.select.lang.en' /></option>
                                        <option value="vt"><spring:message code='lang.select.lang.vt' /></option>
                                    </form:select>
                                </td>
                            </tr>

                            <tr>
                                <td style="background: #f0f4f6;"> <span style="color:red;">&nbsp;*&nbsp;</span><spring:message code='dateboard.notice.company.title' /></td>
                                <td><form:input path="title" cssClass="form-control form-control-sm mr-1" /></td>
                            </tr>

                            <tr>
                                <td style="background: #f0f4f6;"> <span style="color:red;">&nbsp;*&nbsp;</span><spring:message code='dateboard.notice.company.notiyn' /></td>
                                <td>
                                    <form:select path="notiYn" cssClass="custom-select custom-select-sm form-control form-control-sm mr-1 w-100">
                                        <option value=""><spring:message code='dateboard.notice.company.luachon' /></option>
                                        <option value="Y"><spring:message code='option.yes' /></option>
                                        <option value="N"><spring:message code='option.no' /></option>
                                    </form:select>
                                </td>
                            </tr>

                            <!-- notification time -->
                            <tr id="controlDate" hidden>
                                <td style="background: #f0f4f6;"> <span style="color:red;">&nbsp;*&nbsp;</span><spring:message code='dateboard.notice.company.starttime' /></td>
                                <td><form:input type="text" readonly="true" path="notiStartDt" cssClass="form-control form-control-sm mr-1" /></td>
                            </tr>

                            <tr>
                                <td style="background: #f0f4f6;"> <span style="color:red;">&nbsp;*&nbsp;</span><spring:message code='dateboard.notice.company.endtime' /></td>
                                <td>
                                    <form:input type="text" readonly="true" path="notiEndDt" cssClass="form-control form-control-sm mr-1 w-75 float-left" />
                                    <button type="button" onclick="resetdate()" class="btn btn-primary float-left">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-bootstrap-reboot" viewBox="0 0 16 16">
                                            <path
                                                d="M1.161 8a6.84 6.84 0 1 0 6.842-6.84.58.58 0 1 1 0-1.16 8 8 0 1 1-6.556 3.412l-.663-.577a.58.58 0 0 1 .227-.997l2.52-.69a.58.58 0 0 1 .728.633l-.332 2.592a.58.58 0 0 1-.956.364l-.643-.56A6.812 6.812 0 0 0 1.16 8z" />
                                            <path
                                                d="M6.641 11.671V8.843h1.57l1.498 2.828h1.314L9.377 8.665c.897-.3 1.427-1.106 1.427-2.1 0-1.37-.943-2.246-2.456-2.246H5.5v7.352h1.141zm0-3.75V5.277h1.57c.881 0 1.416.499 1.416 1.32 0 .84-.504 1.324-1.386 1.324h-1.6z" />
                                        </svg>
                                    </button>
                                </td>
                            </tr>

                            <!-- contents -->
                            <tr>
                                <td style="background: #f0f4f6;"> <span style="color:red;">&nbsp;*&nbsp;</span><spring:message code='dateboard.notice.company.contents' /></td>
                                <td colspan="3"><textarea id="contents" name="contents" class="ckeditor form-control w-100" rows="5" cols="80"></textarea></td>
                            </tr>
                        </table>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
    <div>
        <h1 class="page-title"><small><spring:message code='file.attach' /></small></h1>
        <button class="btn btn-primary mb-2" onclick="addNewRow()"><spring:message code='button.rowadd' /></button>
    </div>

    <div class="panel panel-inverse">
        <div style="height:350px;overflow: auto; ">
            <div class="m-table">
                <table id="tbFile" class="table table-striped table-bordered w-100 text-center">
                    <thead>
                        <tr style="background-color: #1c5691;color: white">
                            <th class="text-align"><spring:message code='work.list.stt' /></th>
                            <th class="text-align"><spring:message code='file.attach' /></th>
                            <th class="text-align"><spring:message code='work.list.function'/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr id="nodata">
                            <td colspan="3"><spring:message code='main.index.datastatus'/></td>
                        </tr>
                    </tbody>

                </table>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $('#notiStartDt').datepicker({

            dateFormat: "yy-mm-dd",             // 날짜의 형식
            changeMonth: true,                  // 월을 이동하기 위한 선택상자 표시여부
            minDate: 0,                       // 선택할수있는 최소날짜, ( 0 : 오늘 이전 날짜 선택 불가)
            onClose: function (selectedDate) {
                // 시작일(fromDate) datepicker가 닫힐때
                // 종료일(toDate)의 선택할수있는 최소 날짜(minDate)를 선택한 시작일로 지정
                $("#notiEndDt").datepicker("option", "minDate", selectedDate);
            }
        });

        //종료일
        $('#notiEndDt').datepicker({

            dateFormat: "yy-mm-dd",
            changeMonth: true,
            //minDate: 0, // 오늘 이전 날짜 선택 불가
            onClose: function (selectedDate) {
                // 종료일(toDate) datepicker가 닫힐때
                // 시작일(fromDate)의 선택할수있는 최대 날짜(maxDate)를 선택한 종료일로 지정
                $("#notiStartDt").datepicker("option", "maxDate", selectedDate);
            }
        });

        $(".do_write_btn").click(function () {
            $(window).unbind('beforeunload');
            let json_data = {

                "data": [

                    {
                        "id": "deptCd",
                        "msg": "<spring:message code='dateboard.notice.company.alert' />"
                    },
                    {
                        "id": "title",
                        "msg": "<spring:message code='dateboard.notice.company.alert' />"
                    },
                    {
                        "id": "contents",
                        "msg": "<spring:message code='dateboard.notice.company.alert' />"
                    },
                    {
                        "id": "boardTypeCd",
                        "msg": "<spring:message code='dateboard.notice.company.alert' />"
                    },
                    {
                        "id": "langCd",
                        "msg": "<spring:message code='dateboard.notice.company.alert' />"
                    },
                    {
                        "id": "notiYn",
                        "msg": "<spring:message code='dateboard.notice.company.alert' />"
                    }
                ]
            };
            if($('#notiYn').val()=="Y"){
                json_data.data.push(
                    {
                        "id": "notiStartDt",
                        "msg": "<spring:message code='dateboard.notice.company.alert' />"
                    },
                    {
                        "id": "notiEndDt",
                        "msg": "<spring:message code='dateboard.notice.company.alert' />"
                    }
                );
            }

            let flag = "Y";

            $.each(json_data.data, function (key, value) {
                if ($("#" + value.id).val()== "" && value.id !="contents") {
                    alert(value.msg);
                    $("#" + value.id).focus();
                    flag = "N";
                    return false;
                }else if (CKEDITOR.instances['contents'].getData()=="" && value.id =="contents") {
                    alert(value.msg);
                    CKEDITOR.instances['contents'].focus();
                    flag = "N";
                    return false;
                }
            });
            if (flag == "Y") {
                on();
                $("#board").submit();
                off();
            }
        });

        $(".go_list_btn").click(function () {
            location.href = "/board/notice/company/list.hs";
        });
        $('#notiYn').change(function () {
            if($('#notiYn').val()=="Y"){
                $('#controlDate').prop('hidden', false);
                resetdate();
            }else{
                $("#notiEndDt").val('');
                $("#notiStartDt").val('');
                $('#controlDate').prop('hidden', true);
            }

        });

    });
    function resetdate() {
        $("#notiEndDt").val('');
        $("#notiStartDt").val('');
        $("#notiEndDt").focus();
        $("#notiStartDt").focus();
    }
</script>