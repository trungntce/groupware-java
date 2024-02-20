<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf"%>

<div id="content" class="content">
    <h1 class="page-header cursor-default">
		<spring:message code='emp.list.department'/> - <spring:message code='dateboard.notice.company.edit' />
        <br>
        <small>
            <spring:message code='detail.edit' />
        </small>
	</h1>

    <div style="text-align: right; padding-bottom: 10px;">
        <button type="button" class="btn btn-primary do_update_btn" dept-cd="${getDeptCd}">
            <spring:message code='dept.list.edit'/>
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
                    <form:form modelAttribute="getDeptUpdate" action="${_ctx}/deptcontrol/dept/update.hs" method="post" cssClass="form-inline">
                        <table class="table mb-0">
                            <tr>
                                <td style="background: #f0f4f6;width: 30%; border-right: 1px solid #e4e7ea;"><spring:message code='dept.list.deptparent'/></td>
                                <td>
                                    ${getDeptDTO.deptParentName}
                                </td>
                            </tr>
                            <form:hidden path="deptCd" value="${getDeptCd}"/>
                            <tr>
                                <td style="background: #f0f4f6;width: 30%; border-right: 1px solid #e4e7ea;"><spring:message code='dept.list.collap'/></td>
                                <td>
                                    <form:select path="collapseYn" cssClass="custom-select custom-select-sm form-control form-control-sm mr-1">
                                        <option value="N">There is not has extra part</option>
                                        <option value="Y">There is an extra part</option>
                                    </form:select>
                                </td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6;width: 30%; border-right: 1px solid #e4e7ea;"><spring:message code='dept.list.sort'/></td>
                                <td>
                                    <form:input type="number" min="0" max="20" path="sort" value="${getDeptDTO.sort}" cssClass="form-control form-control col-10"/>
                                </td>
                            </tr>

                            <tr>
                                <td style="background: #f0f4f6;width: 30%; border-right: 1px solid #e4e7ea;"><spring:message code='dept.list.dept'/></td>
                                <td>
                                    <form:input style="width: 350px;" path="deptName" cssClass="form-control form-control col-10" value="${getDeptDTO.deptName}"/>
                                </td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6;width: 30%; border-right: 1px solid #e4e7ea;"><spring:message code='dept.list.useyn'/></td>
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

		$(".do_update_btn").click(function(){
            let json_data = {

                    "data" : [
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
                $("#getDeptUpdate").submit();
            }
        });
	});
</script>