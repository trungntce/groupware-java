<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>

        <div id="content" class="content">
            <script type="text/javascript" src="${_ctx}/resources/js/ckeditor/ckeditor.js"></script>
            <h3 class="page-title">
                <spring:message code='main.index.work'/> - <spring:message code='work.add.tieude' /><br>
                <small><spring:message code='detail.add' /></small>
            </h3>
            <div class="text-right mb-2">
                <button type="button" class="btn btn-success" onclick="goEvent()"><spring:message code='work.list.save' /></button>
                <button type="button" class="btn btn-primary" onclick="back()"><spring:message code='work.add.back' /></button>
            </div>
            <div class="row">
                <div class="col-xl-12 ui-sortable">
                    <div class="panel panel-inverse">
                        <div class="panel-heading ui-sortable-handle">
                            <h4 class="panel-title">
                                <spring:message code='work.add.information' />
                            </h4>
                        </div>
                        <div class="panel-body">
                            <form id="myform" class="form-inline" action="">
                                <table class="table mb-0 w-100">
                                    <tbody>
                                        <tr>
                                            <td style="background: #f0f4f6; width: 90px;"><spring:message code='work.add.title' /></td>
                                            <td><input id="title" name="title" class="form-control form-control-sm mr-1 w-100" type="text"></td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='work.add.contents' /></td>
                                            <td>
                                                <table class="w-100">
                                                    <thead>
                                                        <tr>
                                                            <th><spring:message code="work.detail.contents" /></th>
                                                            <th style="width: 60px"><spring:message code='work.list.delete' /></th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr>
                                                            <td><input id="idcontent1" name="idcontent1" class="form-control form-control-sm mr-1 w-100" type="text"></td>
                                                            <td><button class="btn btn-success" type="button" onclick="addMore()"><i class="fas fa-plus"></i></button></td>
                                                        </tr>

                                                        <tr id="idTrContent2">
                                                            <td><input id="idcontent2" name="idcontent1" class="form-control form-control-sm mr-1 w-100" type="text"></td>
                                                            <td><button class="btn btn-success" onclick="removeRow('idTrContent2')"><i class="fas fa-minus"></i></button></td>
                                                        </tr>

                                                        <tr id="idTrContent3">
                                                            <td><input id="idcontent3" name="idcontent1" class="form-control form-control-sm mr-1 w-100" type="text"></td>
                                                            <td><button class="btn btn-success" onclick="removeRow('idTrContent3')"><i class="fas fa-minus"></i></button></td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                                <div id="test-work">
                                                </div>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </form>
                        </div>
                    </div>
                </div>
                <input id="idcontent" name="" type="hidden">

            </div>
        </div>


        <!-- script -->
        <script>
            
            $(document).ready(function () {      
                
                enterCall();

                if ('${title}' != '') {
                    toastr['${title}']('${message}');
                }

                $(".open_slide_btn").click(function () {
                    $(this).hide();
                    $(this).next().show();
                    $(this).parent().parent().next().slideDown();
                });

                var lang = '${_lang}';

                if (lang == 'vt') {
                    $('#myform').validate({
                        rules: {
                            title: {
                                required: true,
                                minlength: 3,
                                maxlength: 100,
                            },
                            idcontent1: {
                                required: true,
                            },
                        },
                        messages: {

                            title: {
                                required: "Vui lòng nhập thông tin tiêu đề",
                                minlength: "Độ dài tối thiểu là 3 ký tự",
                                maxlength: "Độ dài tối đa 100 ký tự",
                            },
                            idcontent1: {
                                required: "Vui lòng nhập thông tin nội dung",
                            },
                        },
                    });
                };

                if (lang == 'ko') {
                    $('#myform').validate({
                        rules: {
                            title: {
                                required: true,
                                minlength: 3,
                                maxlength: 100,
                            },
                            idcontent1: {
                                required: true,
                            },
                        },
                        messages: {

                            title: {
                                required: "제목을 입력해주세요",
                                minlength: "최소 길이는 3자입니다.",
                                maxlength: "최대 길이는 100자입니다.",
                            },
                            idcontent1: {
                                required: "내용을 입력하세요",
                            },
                        },
                    });
                };

                if (lang == 'en') {
                    $('#myform').validate({
                        rules: {
                            title: {
                                required: true,
                                minlength: 3,
                                maxlength: 100,
                            },
                            idcontent1: {
                                required: true,
                            },
                        },
                        messages: {

                            title: {
                                required: "Please input title ",
                                minlength: "Min Length is 3 character",
                                maxlength: "Max Length is 100 character",
                            },
                            idcontent1: {
                                required: "Please input contents ",
                            },
                        },
                    });
                };
            });

            var number = 3;
            function addMore() {
                number = number + 1;
                var strDiv = 'test-work'
                var strPlus = '#test-work'
                var preStr = strDiv + number;
                var idContent = 'idcontent' + number;
                var valueStr = "'idTrContent" + number + "'";
                var subStr = '<div id="' + preStr + '"></div>'
                var str = '<table class="w-100"><tbody><tr id="idTrContent' + number + '"><td><input id="' + idContent + '" name="idcontent1" class="form-control form-control-sm mr-1 w-100" type="text"></td><td style="width: 60px"><button class="btn btn-success" onclick="removeRow(' + valueStr + ')"><i class="fas fa-minus"></i></button></td></tr></tbody></table>' + subStr;
                if (number === 4) {
                    $(strPlus).html(str);
                }
                else {
                    var lastStr = strPlus + (number - 1);
                    $(lastStr).html(str);
                }

            }

            var cldt = "";

            function goEvent() {

                on();
                var arrayInput = [];

                for (var i = 1; i <= number; i++) {
                    cldt = '#idcontent' + i;
                    strSave = '';

                    if ($(cldt).val() != '' && $(cldt).val() != undefined) {
                        arrayInput.push({ "contents": $(cldt).val() });
                    }
                }

                arrayInput.push({ "title": $("#title").val() });

                console.log(arrayInput)
                var isValid = $('#myform').valid();
                if (isValid == true) {
                    var settings = {
                        "url": "/work/daywork/addW",
                        "method": "POST",
                        "headers": {
                            "Content-Type": "application/json"
                        },
                        "data": JSON.stringify(arrayInput),
                    };
                    $.ajax(settings).done(function (response) {
                        location.href = "/work/daywork/list.hs?title=success&message=Add new sucess";
                        off();
                    });
                }
                else{
                    off();
                }
            }

            function back() {
                location.href = "/work/daywork/list.hs";
            }
            function removeRow(str) {
                var linkStr = '#' + str
                console.log(linkStr)
                $(linkStr).html('');
            }

        </script>