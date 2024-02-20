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
        <div id="content" class="content">
            <script type="text/javascript" src="${_ctx}/resources/js/ckeditor/ckeditor.js"></script>

            <h3 class="page-title">
                <spring:message code='main.index.work' /> - <spring:message code='work.add.tieude' />
                <br>
                <small><spring:message code='detail.add' /></small>
            </h3>
            <!-- html -->
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
                                <table class="table mb-0">
                                    <tbody>
                                        <tr>
                                            <td style="background: #f0f4f6; min-width: 90px; max-width: 90px;"><spring:message code='work.add.title' /></td>
                                            <td><input id="title" name="title" class="form-control form-control-sm mr-1" type="text" value=""></td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='work.detail.coperation' /></td>
                                            <td><div id="emp-list-copporation"></div></td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='work.add.contents' /></td>
                                            <td><textarea id="contents" name="contents" class="ckeditor form-control" style="width:100%" rows="2"></textarea></td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='work.add.important' /></td>
                                            <td><input style="margin-left: 5px" class="form-check-input" type="checkbox" id="important" name="important"></td>
                                        </tr>

                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='work.detail.workfunction' /></td>
                                            <td>
                                                <button class="open-AddBookDialog btn btn-primary" type="button" data-toggle="modal" href="#exampleModal"><spring:message code='work.detail.addCo' /></button>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='work.add.date' /></td>
                                            <td>
                                                <div style="float: left;">
                                                    <span><spring:message code='work.add.start' />:</span>
                                                    <input readonly id="workstart" name="workstart" class="form-control form-control-sm mr-1" type="text" value="">
                                                </div>
                                                <div style="float: left;margin-left: 20px">
                                                    <span><spring:message code='work.add.end' />:</span>
                                                    <input readonly id="workend" name="workend" class="form-control form-control-sm mr-1" type="text" value="">
                                                    <button type="button" onclick="resetdate()" class="btn btn-primary">
                                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-bootstrap-reboot" viewBox="0 0 16 16">
                                                            <path
                                                                d="M1.161 8a6.84 6.84 0 1 0 6.842-6.84.58.58 0 1 1 0-1.16 8 8 0 1 1-6.556 3.412l-.663-.577a.58.58 0 0 1 .227-.997l2.52-.69a.58.58 0 0 1 .728.633l-.332 2.592a.58.58 0 0 1-.956.364l-.643-.56A6.812 6.812 0 0 0 1.16 8z" />
                                                            <path
                                                                d="M6.641 11.671V8.843h1.57l1.498 2.828h1.314L9.377 8.665c.897-.3 1.427-1.106 1.427-2.1 0-1.37-.943-2.246-2.456-2.246H5.5v7.352h1.141zm0-3.75V5.277h1.57c.881 0 1.416.499 1.416 1.32 0 .84-.504 1.324-1.386 1.324h-1.6z" />
                                                        </svg>
                                                    </button>
                                                </div>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td>
                                                <button type="button" class="btn btn-success" onclick="addW()">
                                                    <spring:message code='work.add.addnew' />
                                                </button>
                                                <button type="button" class="btn btn-primary" onclick="back()">
                                                    <spring:message code='work.add.back' />
                                                </button>
                                            </td>
                                            <td></td>
                                            <td></td>
                                            <td></td>

                                        </tr>

                                    </tbody>
                                </table>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
                aria-hidden="true">
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
                                        <option value="0">
                                            <spring:message code='search.selectoption.first' />
                                        </option>
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
                                            aria-autocomplete="list" aria-haspopup="true" aria-expanded="false"
                                            dir="ltr">
                                            <div class="choices__inner"><select id="choices-multiple-remove-button"
                                                    placeholder="Select upto 5 tags" multiple=""
                                                    class="choices__input is-hidden" tabindex="-1" aria-hidden="true"
                                                    data-choice="active"></select>
                                                <div class="choices__list choices__list--multiple"></div>
                                                <input type="text" class="choices__input choices__input--cloned"
                                                    autocomplete="off" autocapitalize="off" spellcheck="false"
                                                    role="textbox" aria-autocomplete="list" placeholder="Select..."
                                                    style="width: 100px;">
                                            </div>
                                            <div class="choices__list choices__list--dropdown" aria-expanded="false">
                                                <div class="choices__list" dir="ltr" role="listbox"
                                                    aria-multiselectable="true">
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
        <!-- script -->
        <script>
            $(document).ready(function () {

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
                                strHtmlDiv += "id='aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa'";
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
                $("#workstart").datepicker({ dateFormat: "yy-mm-dd" });
                $("#workend").datepicker({ dateFormat: "yy-mm-dd" });
                $("#workstart").on("change", function () {
                    document.forms["myform"]["workstart"].focus();
                });
                $("#workend").on("change", function () {
                    document.forms["myform"]["workend"].focus();
                });

                if ('${title}' != '') {
                    if ('${title}' == 'success') {
                        //toastr['${title}']('${message}');
                        toastr.success('', "<spring:message code='notification.index.success' />", { timeOut: 1000 });
                    } else {
                        toastr.error('', "<spring:message code='notification.index.error' />", { timeOut: 1000 });
                    }
                }

                let lang = '${_lang}';

                if (lang == 'vt') {
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
                                required: "Vui lòng nhập thông tin tiêu đề",
                                minlength: "Độ dài tối thiểu là 3 ký tự",
                                maxlength: "Độ dài tối đa 100 ký tự",
                            },
                            contents: {
                                required: "Vui lòng nhập thông tin nội dung",
                            },

                            workstart: {
                                required: "Vui lòng nhập thời gian bắt đầu công việc",
                            },
                            workend: {
                                required: "Vui lòng nhập thời gian kết thúc công việc",
                            }
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
                };
                if (lang == 'ko') {
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
                                required: "제목을 입력해주세요",
                                minlength: "최소 길이는 3자입니다.",
                                maxlength: "최대 길이는 100자입니다.",
                            },
                            contents: {
                                required: "내용을 입력하세요",
                            },

                            workstart: {
                                required: "종료일을 입력하세요",
                            },
                            workend: {
                                required: "시작일을 입력하세요",
                            }
                        },
                    });
                };
            });

            var empList = "";

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

            function resetdate() {
                $("#workend").val('');
                $("#workstart").val('');
                $("#workend").focus();
                $("#workstart").focus();
            }

            function addW() {
                on();
                var isValid = $('#myform').valid();
                var important = "N";
                if (isValid == true) {
                    if ($('#important').is(":checked")) {
                        important = 'Y';
                    }
                    var settings = {
                        "url": "/work/work/addW",
                        "method": "POST",
                        "headers": {
                            "Content-Type": "application/json"
                        },
                        "data": JSON.stringify({
                            "empCd": '${empCd}',
                            "title": $("#title").val(),
                            "contents": CKEDITOR.instances['contents'].getData() == ''?'<p>' : CKEDITOR.instances['contents'].getData(),
                            "importantYn": important,
                            "workStartDt": $("#workstart").val(),
                            "workEndDt": $("#workend").val(),
                            "empList": empList,
                        }),
                    };
                    $.ajax(settings).done(function (response) {
                        location.href = "/work/work/list.hs?title=success&message=Add new sucess";
                        off();
                    });
                } else {
                    off();
                }
            }
            function back() {
                location.href = "/work/work/list.hs";
            }

        </script>