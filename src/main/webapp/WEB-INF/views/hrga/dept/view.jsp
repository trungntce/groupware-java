<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf"%>

<div id="content" class="content">
    <h1 class="page-header cursor-default">
		<spring:message code='emp.list.department'/> - <spring:message code='author.list.bigtitle'/>
        <br>
        <small>
            <spring:message code='detail.message' />
        </small>

	</h1>
    <div style="text-align: right; padding-bottom: 10px;">
        <button type="button" class="btn btn-primary go_update_btn" dept-cd="${getDeptView.deptCd}">
            <spring:message code='dept.list.edit'/>
        </button>

        <button type="button" class="btn btn-primary go_list_btn">
            <spring:message code='work.addtran.back'/>
        </button>
    </div>
    <div class="panel panel-inverse">
        <div class="panel-heading">
            <h4 class="panel-title"><spring:message code='emp.list.information'/></h4>
        </div>
        <div class="panel-body">
            <table class="table mb-0">
                <tr>
                    <td style="background: #f0f4f6;width: 30%; border-right: 1px solid #e4e7ea;"><font color="#555555"> <spring:message code='dept.list.deptparent'/></font></td>
                    <td>
                        ${getDeptView.deptParentName}
                    </td>
                </tr>
                <tr>
                    <td style="background: #f0f4f6;width: 30%; border-right: 1px solid #e4e7ea;"><font color="#555555"><spring:message code='dept.list.dept'/></font></td>
                    <td>
                        ${getDeptView.deptName}
                    </td>
                </tr>
                <tr>
                    <td style="background: #f0f4f6;width: 30%; border-right: 1px solid #e4e7ea;"><font color="#555555"><spring:message code='dept.list.level'/></font></td>
                    <td>
                        ${getDeptView.deptLevel}
                    </td>
                </tr>
                <tr>
                    <td style="background: #f0f4f6;width: 30%; border-right: 1px solid #e4e7ea;"><font color="#555555"><spring:message code='dept.list.collocation'/></font></td>
                    <td>
                        ${getDeptView.sort}
                    </td>
                </tr>
                <tr>
                    <td style="background: #f0f4f6;width: 30%; border-right: 1px solid #e4e7ea;"><font color="#555555"><spring:message code='dept.list.lowergrade'/></font></td>
                    <td>
                        <c:choose>
                            <c:when test="${getDeptView.collapseYn == 'Y'}">
                                there are subdivisions
                            </c:when>
                            <c:otherwise>
                                no subdivision
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <td style="background: #f0f4f6;width: 30%; border-right: 1px solid #e4e7ea;border-bottom: 1px solid #e4e7ea;"><font color="#555555"><spring:message code='dept.list.useyn'/></font></td>
                    <td style="border-bottom: 1px solid #e4e7ea;">
                        <c:choose>
                            <c:when test="${getDeptView.useYn eq 'Y'}">
                                Use
                            </c:when>
                            <c:otherwise>
                                Not Use
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        $(".go_list_btn").click(function(){
            let deptId = $(this).attr('dept-id');
            location.href="/deptcontrol/dept/list.hs";
        });
    });
    $(".go_update_btn").click(function(){
        let deptCd = $(this).attr('dept-cd');
        location.href="/deptcontrol/dept/update.hs?deptCd="+deptCd;
    });
</script>