<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf"%>
<link href="${_ctx}/resources/js/plugins/jstree/dist/themes/default/style.min.css" rel="stylesheet" />
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
    String translationStatusGet = request.getParameter("translationStatus");
    if (translationStatusGet == null || translationStatusGet == "null") {
        translationStatusGet = "";
    }
    String backSearch="start_date="+start_date+"&end_date="+end_date+"&levelDept="+levelDeptGet+"&optionSearch="+optionSearchGet+"&inputSearch="+inputSearchGet+"&displayStartPra="+displayStartPra+"&translationStatus="+translationStatusGet;
    String linkEdit = "${_ctx}/board/notice/company/edit.hs?"+backSearch;
%>
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
    <script type="text/javascript" src="${_ctx}/resources/js/ckeditor/ckeditor.js"></script>
    <h1 class="page-header cursor-default">
        <spring:message code='dateboard.notice.company.nhom' /> - <spring:message code='dateboard.notice.company.edit' />
        <br>
        <small><spring:message code='detail.edit' /></small>
    </h1>

    <div class="text-right pb-2">
        <button type="button" class="btn btn-primary do_update_btn" board-id="${boardDTO.boardId}"><spring:message code='dateboard.notice.company.edit'/></button>
        <button type="button" class="btn btn-primary go_list_btn"><spring:message code='work.addtran.back'/></button>
    </div>
    <div class="row">
        <div class="col-xl-12">
            <div class="panel panel-inverse">
                <div class="panel-heading">
                    <h4 class="panel-title"><spring:message code='emp.list.information'/></h4>
                </div>
                <div class="panel-body">
                    <form:form modelAttribute="board" action="${_ctx}/board/notice/group/edit.hs" method="post" cssClass="form-inline">
                        <table class="table mb-0">

                            <form:hidden path="boardId" value="${boardDTO.boardId}"/>
                            <tr>
                                <td style="background: #f0f4f6; width: 110px;"><span style="color:red;">*</span><spring:message code='dateboard.notice.company.boardtype'/></td> 
                                <td><spring:message code='dateboard.notice.company.nhom' /> </td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6;"><span style="color:red;">*</span><spring:message code='dateboard.notice.company.lang'/></td>
                                <td>
                                    <form:select path="langCd" cssClass="custom-select custom-select-sm form-control form-control-sm mr-1">
                                        <option><spring:message code='dateboard.notice.company.luachon'/></option>
                                        <option value="ko" ${boardDTO.langCd=="ko"? "selected" : ""}><spring:message code='lang.select.lang.ko' /></option>
                                        <option value="en" ${boardDTO.langCd=="en"? "selected" : ""}><spring:message code='lang.select.lang.en' /></option>
                                        <option value="vt" ${boardDTO.langCd=="vt"? "selected" : ""}><spring:message code='lang.select.lang.vt' /></option>
                                    </form:select>
                                </td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6;"><spring:message code='dateboard.notice.company.title'/></td>
                                <td><form:input path="title" value="${boardDTO.title}" cssClass="form-control form-control-sm mr-1"/></td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6;"><spring:message code='dateboard.notice.company.notiyn'/></td>
                                <td>
                                    <form:select path="notiYn" cssClass="custom-select custom-select-sm form-control form-control-sm mr-1">
                                        <option><spring:message code='dateboard.notice.company.luachon'/></option>
                                        <option value="Y" ${"Y"== boardDTO.notiYn? "selected" : ""}><spring:message code='option.yes' /></option>
                                        <option value="N" ${"N"== boardDTO.notiYn? "selected" : ""}><spring:message code='option.no' /></option>
                                    </form:select>
                                </td>
                            </tr>

                            <!-- notification time -->
                            <tr id="controlDate">
                                <td style="background: #f0f4f6;"><spring:message code='dateboard.notice.company.starttime'/>:</td> <!-- notification time start -->
                                <td><form:input readonly="true" value="${boardDTO.notiStartDt.split(' ')[0]}" path="notiStartDt" cssClass="form-control form-control-sm mr-1"/></td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6;"><spring:message code='dateboard.notice.company.endtime'/></td> <!-- notification time end -->
                                <td><form:input readonly="true" value="${boardDTO.notiEndDt.split(' ')[0]}" path="notiEndDt" cssClass="form-control form-control-sm mr-1"/></td>
                            </tr>

                            <!-- contents -->
                            <tr>
                                <td style="background: #f0f4f6;"><spring:message code='dateboard.notice.company.contents'/></td>
                                <td><textarea id="contents" name="contents" class="ckeditor form-control" rows="5" cols="80">${boardDTO.contents}</textarea></td>
                            </tr>
                            <input type="hidden" name="txtSearch" value="<%=backSearch%>">
                        </table>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
    <div>
        <h1 class="page-title float-left"><small><spring:message code='file.attach' /></small></h1>
        <button style="margin-bottom: 5px;" class="btn btn-primary float-right" onclick="addNewRow()"><spring:message code='button.rowadd' /></button>
    </div>
    <div style="clear: both"></div>
    <div class="panel panel-inverse">
        <div style="height:350px;overflow: auto; ">
        <div class="m-table">
            <table id="tbFile" class="table table-striped table-bordered w-100 text-center">
                <thead>
                    <tr style="background-color: #1c5691;color: white">
                        <th><spring:message code='work.list.stt' /></th>
                        <th><spring:message code='file.attach' /></th>
                        <th><spring:message code='work.list.function'/></th>
                    </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${lstFile.size() >0}">
                        <c:forEach items="${lstFile}" var="lst">
                        <tr>
                            <td>${lst.rownum}</td>
                            <td><p style="text-align: left" >${lst.fileName}, ${lst.fileSize}</p></td>
                            <td><button type="button" onclick="deleteOldRow(this,'${lst.fileId}')" class="btn btn-info">X</button></td>
                        </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr id="nodata">
                            <td colspan="3"><spring:message code='main.index.datastatus'/></td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>

            </table>
        </div>
    </div>
    </div>
</div>
<script type="text/javascript" src="${_ctx}/resources/js/plugins/file-upload/js/file_upload.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        if($('#notiYn').val()=="N"){
            $("#notiEndDt").val('');
            $("#notiStartDt").val('');
            $('#controlDate').prop('hidden', true);
        }
        $('#notiStartDt').datepicker({
            dateFormat: "yy-mm-dd",
            changeMonth: true,
            minDate: 0,
            onClose: function (selectedDate) {
                $("#notiEndDt").datepicker("option", "minDate", selectedDate);
            }
        });

        //종료일
        $('#notiEndDt').datepicker({

            dateFormat: "yy-mm-dd",
            changeMonth: true,
            onClose: function (selectedDate) {
                $("#notiStartDt").datepicker("option", "maxDate", selectedDate);
            }
        });


        $(".do_update_btn").click(function(){
            $(window).unbind('beforeunload');
            let json_data = {

                    "data" : [

                        {
                            "id": "rownum",
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
                if ($("#" + value.id).val() == "" && value.id !="contents") {
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
            if(flag == "Y"){
                $("#board").submit();
            }
        });

        $(".go_list_btn").click(function(){
            location.href="/board/notice/group/list.hs?<%=backSearch%>";
        });

        // 영문 숫자만 아이디로 사용가능
        $('#empId').keyup(function() {
            var inputVal = $(this).val();
            $(this).val((inputVal.replace(/[ㄱ-힣~!@#$%^&*()_+|<>?:{}= ]/g,'')));
        });

        $(".overlap_btn").click(function(){
             let empId = $("#empId");

             if(empId.val() == ""){
                 alert('아이디를 입력하세요.');
                 empId.focus();
             } else {
                 $.get( "/management/emp/overlap_check.json", { empId: empId.val()}, function( data ) {
                     if(data > 0){
                         alert('중복된 아이디 입니다.');
                     } else {
                         alert('등록가능합니다.');
                     }
                 });
             }
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