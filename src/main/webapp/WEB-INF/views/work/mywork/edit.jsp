<%@ page import="kr.co.hs.oldwork.dto.CoperationDTO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
    <link href="${_ctx}/resources/js/plugins/jstree/dist/themes/default/style.min.css" rel="stylesheet" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/bbbootstrap/libraries@main/choices.min.css">
<script src="https://cdn.jsdelivr.net/gh/bbbootstrap/libraries@main/choices.min.js"></script>

<link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
<script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
<style>
    ul.ul-emp-list {
        list-style: none;
        padding: 0;
        margin: 0;
    }
</style>
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


<div id="content" class="content">
    <script type="text/javascript" src="${_ctx}/resources/js/ckeditor/ckeditor.js"></script>
    <h3 class="page-title">
        <spring:message code='main.index.work' /> -
        <spring:message code='work.add.edit' />
        <br>
        <small>
            <spring:message code='detail.edit' />
        </small>
    </h3>
    <div class="text-right">
        <button type="button" class="btn btn-success" onclick="EditW()">
            <spring:message code='work.add.edit' />
        </button>
        <button type="button" class="btn btn-primary" onclick="back()">
            <spring:message code='work.add.back' />
        </button>
    </div>
    <br />
    <!-- html -->
    <div class="row">
        <div class="col-xl-12 ui-sortable">
            <div class="panel panel-inverse" style="">

                <div class="panel-heading ui-sortable-handle">
                    <h4 class="panel-title">
                        <spring:message code='work.add.information' />
                    </h4>
                </div>

                <div class="panel-body">
                    <form id="myform" class="form-inline" action="">
                        <table class="table mb-0">
                            <tbody>
                                <tr>
                                    <td style="background: #f0f4f6;width: 15%">
                                        <spring:message code='work.add.title' />
                                    </td>
                                    <td style="width: 35%"><input style="width: 50%;" id="title" name="title"
                                            class="form-control form-control-sm mr-1" type="text"
                                            value="${workDTO.title}"></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td style="background: #f0f4f6;width: 15%;">
                                        <spring:message code='work.detail.coperation' />
                                    </td>
                                    <td style="width: 35%">
                                        <div id="emp-list-copporation">
                                            <ul class="ul-emp-list">
                                                <c:forEach items="${lstCo}" var="list">
                                                    <li>${list.empName}(${list.positionName})</li>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td style="background: #f0f4f6;width: 15%">
                                        <spring:message code='work.add.contents' />
                                    </td>
                                    <td style="width: 100%"><textarea id="contents" name="contents"
                                            class="ckeditor form-control" style="width:100%" rows="2"
                                            cols="80">${workDTO.contents}</textarea></td>

                                </tr>
                                <tr>
                                    <td style="background: #f0f4f6;width: 15%">
                                        <spring:message code='work.add.important' />
                                    </td>
                                    <td style="width: 50%"><input style="margin-left: 5px" class="form-check-input"
                                            type="checkbox" id="important" name="important" ${workDTO.importantYn=="Y"
                                            ? "checked" : "" }>
                                    </td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td style="background: #f0f4f6;">
                                        <spring:message code='work.detail.workfunction' />
                                    </td>
                                    <td>
                                        <button class="open-AddBookDialog btn btn-primary" type="button"
                                            data-toggle="modal" href="#exampleModal">
                                            <spring:message code='work.detail.addCo' />
                                        </button>
                                    </td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td style="background: #f0f4f6;">
                                        <spring:message code='work.add.date' />
                                    </td>
                                    <td>
                                        <div style="float: left;">
                                            <span>
                                                <spring:message code='work.add.start' />:
                                            </span>
                                            <input readonly id="workstart" name="workstart"
                                                class="form-control form-control-sm mr-1" type="text"
                                                value="${workDTO.workStartDt}">
                                        </div>
                                        <div style="float: left;margin-left: 20px">
                                            <span>
                                                <spring:message code='work.add.end' />:
                                            </span>
                                            <input readonly id="workend" name="workend"
                                                class="form-control form-control-sm mr-1" type="text"
                                                value="${workDTO.workEndDt}">
                                        </div>
                                    </td>

                                </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">
                        <spring:message code='work.detail.addCo' />
                    </h5>
                    <input type="text" hidden="" value="" name="" id="giatri" />
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
                                <c:forEach items="${lstDept}" var="ltt">
                                    <option value="${ltt.deptCd}">${ltt.deptName}</option>
                                </c:forEach>

                            </select>
                        </div>
                        <div class="form-group" id="hihi">
                            <strong for="inputState">
                                <spring:message code='work.detail.empName' />
                            </strong>
                            <div id="song">
                                <div class="choices" data-type="select-multiple" role="combobox"
                                    aria-autocomplete="list" aria-haspopup="true" aria-expanded="false" dir="ltr">
                                    <div class="choices__inner"><select id="choices-multiple-remove-button"
                                            placeholder="Select upto 5 tags" multiple=""
                                            class="choices__input is-hidden" tabindex="-1" aria-hidden="true"
                                            data-choice="active"></select>
                                        <div class="choices__list choices__list--multiple"></div>
                                        <input type="text" class="choices__input choices__input--cloned"
                                            autocomplete="off" autocapitalize="off" spellcheck="false" role="textbox"
                                            aria-autocomplete="list" placeholder="Select..." style="width: 100px;">
                                    </div>
                                    <div class="choices__list choices__list--dropdown" aria-expanded="false">
                                        <div class="choices__list" dir="ltr" role="listbox" aria-multiselectable="true">
                                            <div class="choices__item choices__item--choice has-no-choices">
                                                선택할 수 있는 옵션이 없습니다.
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>

                </div>

                <div class="modal-footer">

                    <button type="button" class="btn btn-primary" onclick="addCo()" data-dismiss="modal">
                        <spring:message code='work.list.save' />
                    </button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">
                        <spring:message code='translation.close' />
                    </button>
                </div>
            </div>
        </div>
    </div>

</div>


<%
String str="";
ArrayList<CoperationDTO> lst = (ArrayList<CoperationDTO>) request.getAttribute("lstCo");
if (lst != null) {
    for (CoperationDTO item : lst) {
        str+="<option value='"+item.getEmpCd()+"'  selected  >"+item.getEmpName()+ "(" + item.getPositionName() + ")" + "</option>";
    }
}
%>
<!-- script -->
<script>
    $(document).ready(function () {
        $("#choices-multiple-remove-button").html("<%=str%>");
        $("#department").change(function (event) {
            $.ajax({
                type: "POST",
                url: "/work/work/getEmp",
                data: {
                    deptCd: $('#department').val(),
                },
                success: function (response) {
                    var strHtmlOption = "";
                    var strHtmlDiv = "";

                    var ori_data = $("#choices-multiple-remove-button").html();
                    console.log(ori_data);

                    $.each(response, function (key, value) {
                        strHtmlOption += "<option value='" + value.empCd + "'>" + value.empName + "</option>";
                        strHtmlDiv += "<div class='choices__item choices__item--choice choices__item--selectable'";
                        strHtmlDiv += "data-select-text='Press to select' ";
                        strHtmlDiv += "data-choice='' ";
                        strHtmlDiv += "data-id='" + key + "' ";
                        strHtmlDiv += "data-value='" + value.empCd + "' ";
                        strHtmlDiv += "data-choice-selectable='' ";
                        strHtmlDiv += "id='choices--choices-multiple-remove-button-item-choice-" + key + "' ";
                        strHtmlDiv += "role='option'>";
                        strHtmlDiv += value.empName;
                        strHtmlDiv += "</div>";
                    });

                    $('#song').html("<select id='choices-multiple-remove-button' placeholder='...' multiple=''></select>");
                    $("#choices-multiple-remove-button").html(ori_data + strHtmlOption + strHtmlDiv);
                    // $("#choices-multiple-remove-button").html(response[0]);


                    var multipleCancelButton = new Choices('#choices-multiple-remove-button', {
                        removeItemButton: true,
                        maxItemCount: 100,
                        searchResultLimit: 100,
                        renderChoiceLimit: 100
                    });
                },
                error: function (error) {
                    toastr['error']('error !');
                }
            })
        });

        $.ajax({
                type: "POST",
                url: "/work/work/getEmp",
                data: {
                    deptCd: $('#department').val(),
                },
                success: function (response) {
                    var strHtmlOption = "";
                    var strHtmlDiv = "";

                    var ori_data = $("#choices-multiple-remove-button").html();
                    console.log(ori_data);

                    $.each(response, function (key, value) {
                        strHtmlOption += "<option value='" + value.empCd + "'>" + value.empName + "</option>";
                        strHtmlDiv += "<div class='choices__item choices__item--choice choices__item--selectable'";
                        strHtmlDiv += "data-select-text='Press to select' ";
                        strHtmlDiv += "data-choice='' ";
                        strHtmlDiv += "data-id='" + key + "' ";
                        strHtmlDiv += "data-value='" + value.empCd + "' ";
                        strHtmlDiv += "data-choice-selectable='' ";
                        strHtmlDiv += "id='choices--choices-multiple-remove-button-item-choice-" + key + "' ";
                        strHtmlDiv += "role='option'>";
                        strHtmlDiv += value.empName;
                        strHtmlDiv += "</div>";
                    });

                    $('#song').html("<select id='choices-multiple-remove-button' placeholder='...' multiple=''></select>");
                    $("#choices-multiple-remove-button").html(ori_data + strHtmlOption + strHtmlDiv);
                    // $("#choices-multiple-remove-button").html(response[0]);


                    var multipleCancelButton = new Choices('#choices-multiple-remove-button', {
                        removeItemButton: true,
                        maxItemCount: 100,
                        searchResultLimit: 100,
                        renderChoiceLimit: 100
                    });
                },
                error: function (error) {
                    toastr['error']('error !');
                }
            });

        var array = $('#choices-multiple-remove-button').val();
            var processingArray = array.reduce(function (accumulator, element) {
                if (accumulator.indexOf(element) === -1) {
                    accumulator.push(element)
                }
                return accumulator
            }, [])
            var lstCo = "";
            for (var k = 0; k < processingArray.length; k++) {
                var j = 0;
                for (var i = 0; i < processingArray.length; i++) {
                    if (processingArray[k] === processingArray[i]) {
                        j += 1;
                    }
                }
                if (j == 1) {
                    lstCo += processingArray[k] + ",";
                }
            }
            lstCo = lstCo.substring(0, lstCo.length - 1);

            empList = lstCo;

        var workstart = $("#workstart").datepicker({
            dateFormat: "yy-mm-dd",
            onSelect: function (dateString, instance) {
                let date = workstart.datepicker('getDate');
                // date.setDate(date.getDate() + 1)
                date.setDate(date.getDate())
                workend.datepicker('option', 'minDate', date);
            }
        });
        var workend = $("#workend").datepicker({
            dateFormat: "yy-mm-dd"
        });
        if ('${title}' != '') {
            if ('${title}' == 'success') {
                //toastr['${title}']('${message}');
                toastr.success('', "<spring:message code='notification.index.success' />", { timeOut: 1000 });
            } else {
                toastr.error('', "<spring:message code='notification.index.error' />", { timeOut: 1000 });
            }
        }

        $('#myform').validate({
            rules: {
                title: {
                    required: true,
                    minlength: 3,
                    maxlength: 100,
                },
                contents: {
                    required: true,

                },

                workstart: {
                    required: true,

                },
                workend: {
                    required: true,
                }
            },

            messages: {

                title: {
                    required: "Please input title ",
                    minlength: "Min Length is 3 character",
                    maxlength: "Max Length is 100 character",
                },
                contents: {
                    required: "Please input contents ",

                },

                workstart: {
                    required: "Please input workstart ",

                },
                workend: {
                    required: "Please input workend ",

                }

            },

        });

    });

    var empList;

    function addCo() {
        var array = $('#choices-multiple-remove-button').val();
        var processingArray = array.reduce(function (accumulator, element) {
            if (accumulator.indexOf(element) === -1) {
                accumulator.push(element)
            }
            return accumulator
        }, [])
        var lstCo = "";
        for (var k = 0; k < processingArray.length; k++) {
            var j = 0;
            for (var i = 0; i < processingArray.length; i++) {
                if (processingArray[k] === processingArray[i]) {
                    j += 1;
                }
            }
            if (j == 1) {
                lstCo += processingArray[k] + ",";
            }
        }
        lstCo = lstCo.substring(0, lstCo.length - 1);

        empList = lstCo;
        
        var generateHtml = '<ul class="ul-emp-list">';
        $.ajax({
            type: 'POST',
            url: '/emp/getemplistbycustomid',
            data: {
                empList: lstCo,
            },
            success: function (response) {
                $.each(response, function (key, value) {
                    generateHtml += '<li>'+  value.empName + '</li>';
                    
                });
                generateHtml += '</ul>';
                console.log(generateHtml);
                $("#emp-list-copporation").html(generateHtml);
            },
            error: function (error) {
                toastr['error']('error !');
            }
        });
    }

    function EditW() {
        on();
        var isValid = $('#myform').valid();
        var important = "N";
        if (isValid == true) {
            if ($('#important').is(":checked")) {
                important = 'Y';
            }
            var settings = {
                "url": "/work/work/editW",
                "method": "POST",
                "headers": {
                    "Content-Type": "application/json"
                },
                "data": JSON.stringify({
                    "workId": '${workDTO.workId}',
                    "empCd": '${workDTO.empCd}',
                    "title": $("#title").val(),
                    "contents": CKEDITOR.instances['contents'].getData(),
                    "importantYn": important,
                    "workStartDt": $("#workstart").val(),
                    "workEndDt": $("#workend").val(),
                    "empList": empList,
                }),
            };
            $.ajax(settings).done(function (response) {
                location.href = "/work/mywork/detail.hs?id=${workDTO.workId}&title=success&message=Edit sucess&<%=backSearch%>";
                off();
            });
        } else {
            off();
        }
    }
    function back() {
        location.href = "/work/mywork/detail.hs?id=${workDTO.workId}&<%=backSearch%>";
    }

</script>