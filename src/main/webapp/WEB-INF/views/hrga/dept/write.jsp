<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf"%>

<div id="content" class="content" style="width:50%; margin: auto;">
    <h1 class="page-header cursor-default">
		<spring:message code='emp.list.department'/> - <spring:message code='dateboard.notice.company.add'/>
        <br>
        <small>
            <spring:message code='detail.add' />
        </small>
	</h1>

    <div style="text-align: right; padding-bottom: 10px;">
        <button type="button" class="btn btn-primary do_write_btn">
            <spring:message code='dept.list.add'/>
        </button>

        <button type="button" class="btn btn-primary go_list_btn">
            <spring:message code='work.addtran.back'/>
        </button>
    </div>
    <div class="row">
        <div class="col-xl-12">
            <div class="panel panel-inverse">
                <div class="panel-heading">
                    <h4 class="panel-title"><spring:message code='emp.list.information'/></h4>
                </div>
                <div class="panel-body">
                    <form:form modelAttribute="dept" action="${_ctx}/deptcontrol/dept/write.hs" method="post" cssClass="form-inline">
                        <table class="table mb-0">
                            <tr>
                                <td style="background: #f0f4f6;"><spring:message code='dept.list.deptparent'/></td>
                                <td>
                                    <form:select path="deptParentCd" cssClass="custom-select custom-select-sm form-control form-control-sm mr-1">
                                        <option value=""><spring:message code='dateboard.notice.company.luachon'/></option>
                                        <form:options items="${getDeptNameList}" itemValue="deptCd" itemLabel="deptName"/>
                                    </form:select>
                                </td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6;"><spring:message code='dept.list.collap'/></td>
                                <td>
                                    <form:select path="collapseYn" cssClass="custom-select custom-select-sm form-control form-control-sm mr-1">
                                        <option value="N">There is not has extra part</option>
                                        <option value="Y">There is an extra part</option>
                                    </form:select>
                                </td>

                            </tr>
                            <tr>
                                <td style="background: #f0f4f6;"><spring:message code='dept.list.sort'/></td>
                                <td>
                                    <form:input type="number" min="0" max="20" path="sort" cssClass="form-control form-control col-10"/>
                                </td>
                            </tr>

                            <tr>
                                <td style="background: #f0f4f6;"><spring:message code='dept.list.dept'/></td>
                                <td>
                                    <form:input path="deptName" cssClass="form-control form-control col-10"/>
                                </td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6;"><spring:message code='dept.list.useyn'/></td>
                                <td>
                                    <form:select path="useYn" cssClass="custom-select custom-select-sm form-control form-control-sm mr-1">
                                        <option value="Y">Yes</option>
                                        <option value="N">No</option>
                                    </form:select>
                                </td>
                            </tr>
                        </table>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">

	$(document).ready(function() {

        $(".go_list_btn").click(function(){
            let deptId = $(this).attr('dept-id');
            location.href="/deptcontrol/dept/list.hs";
        });


		$(".do_write_btn").click(function(){
            let json_data = {

                    "data" : [

                        {
                            "id": "deptParentCd",
                            "msg": "<spring:message code='dateboard.notice.company.alert' />"
                        },
                        {
                            "id": "collapseYn",
                            "msg": "<spring:message code='dateboard.notice.company.alert' />"
                        },
                            {
                            "id": "sort",
                            "msg": "<spring:message code='dateboard.notice.company.alert' />"
                        },
                        {
                            "id": "deptName",
                            "msg": "<spring:message code='dateboard.notice.company.alert' />"
                        },
                        {
                            "id": "useYn",
                            "msg": "<spring:message code='dateboard.notice.company.alert' />"
                        }
                    ]
            };
            let flag = "Y";

            $.each( json_data.data, function( key, value ){
                if($("#"+value.id).val() == ""){
                    alert(value.msg);
                    $("#"+value.id).focus();
                    flag = "N";
                    return false;
                }
            });
            if(flag == "Y"){
                on();
                $("#dept").submit();
                off();
            }
        });
	});
</script>