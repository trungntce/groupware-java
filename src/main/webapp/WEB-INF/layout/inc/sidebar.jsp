<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<tiles:importAttribute name="firstMenu"/>
<tiles:importAttribute name="secondMenu"/>
<tiles:importAttribute name="thirdMenu"/>
<div id="sidebar" class="sidebar">
	<div data-scrollbar="true" data-height="100%">
		<ul class="nav">
			<li class="nav-profile">
				<a class="cursor-pointer" data-toggle="nav-profile">
					<div class="cover with-shadow"></div>
					<div class="info">
						<b class="caret pull-right"></b>
						VGM
						<small>System</small>
					</div>
				</a>
			</li>
			<li>
				<ul class="nav nav-profile">
					<li>
						<a href="${_ctx}/emp/mypage.hs">
							<i class="fa fa-user-circle fa-2x"></i>
							MyPage
						</a>
						<a href="${_ctx}/logout.hs">
							<i class="fa fa-sign-out-alt"></i>
							Log Out
						</a>
					</li>

				</ul>
			</li>
			<li class="nav-header">Navigation</li>

				<c:forEach var="first_menu" items="${firstMenu}">
				<li class="has-sub">
					<c:choose>
						<c:when test="${first_menu.menuType eq 'P' && first_menu.collapseYn eq 'Y'}">
							<a href="javascript:;" id="${first_menu.menuUrlId}" class="menu-link ">
							<b class="caret"></b>
						</c:when>
						<c:when test="${first_menu.menuType eq 'P' && first_menu.collapseYn eq 'N'}">
							<a href="${first_menu.menuUrl}" id="${first_menu.menuUrlId}" class="menu-link">
						</c:when>
						<c:otherwise>
							<a href="javascript:moveLink('${first_menu.menuUrl}');" id="${first_menu.menuUrlId}" class="menu-link">
						</c:otherwise>
					</c:choose>
					<i class="fa fa-table"></i>
						${first_menu.menuName}
					</a>											
					<c:if test="${first_menu.collapseYn eq 'Y'}">
						<ul class="sub-menu">
							<c:forEach var="second_menu" items="${secondMenu}">
							<c:if test="${first_menu.menuCd eq second_menu.menuParentCd}">
							<li class="has-sub">
								<c:choose>
									<c:when test="${second_menu.menuType eq 'P' && second_menu.collapseYn eq 'Y'}">
										<a href="javascript:;" id="mn-${second_menu.menuCd}" class="menu-link">
										<b class="caret"></b>
									</c:when>
									<c:when test="${second_menu.menuType eq 'P' && second_menu.collapseYn eq 'N'}">
										<a href="${second_menu.menuUrl}" id="${second_menu.menuUrlId}" class="menu-link">
									</c:when>
									<c:otherwise>
										<a href="javascript:moveLink('${second_menu.menuUrl}');" id="${second_menu.menuUrlId}" class="menu-link">
									</c:otherwise>
								</c:choose>
								${second_menu.menuName}
								</a>
								<c:if test="${second_menu.collapseYn eq 'Y'}">
								<ul class="sub-menu">
									<c:forEach var="third_menu" items="${thirdMenu}">
										<c:if test="${second_menu.menuCd eq third_menu.menuParentCd}">
										<li class="has-sub">
											<c:choose>
												<c:when test="${third_menu.menuType eq 'P' && third_menu.collapseYn eq 'Y'}">
													<a href="javascript:;" id="${third_menu.menuUrlId}" class="menu-link" />
													<b class="caret"></b>
												</c:when>
												<c:when test="${third_menu.menuType eq 'P' && third_menu.collapseYn eq 'N'}">
													<a href="${third_menu.menuUrl}" id="${third_menu.menuUrlId}" class="menu-link" />
												</c:when>
												<c:otherwise>
													<a href="javascript:moveLink('${third_menu.menuUrl}');" id="${third_menu.menuUrlId}" class="menu-link" />
												</c:otherwise>
											</c:choose>
											${third_menu.menuName}
											</a>
										</li>
										</c:if>
									</c:forEach>
								</ul>
								</c:if>
							</li>
							</c:if>
							</c:forEach>
						</ul>
					</c:if>
				</a>
			</li>
			</c:forEach>
		</ul>
	</div>
	<input type="hidden" class="countApproval" value="${countApprovalPending}" for="approvalwaitpending">
	<input type="hidden" class="countApproval" value="${countApprovalWaiting}" for="approvalwaitwaiting">
	<input type="hidden" class="countApproval" value="${countApprovalProcess}" for="approvalwaitprocess">
	<input type="hidden" class="countApprovalTranslate" value="${countApprovalTranslateWait}" for="approvaltranslatewait">
	<input type="hidden" class="countApprovalTranslate" value="${countApprovalTranslateConfirm}" for="approvaltranslateconfirm">
</div>

<div class="sidebar-bg"></div>
<script>
	String.prototype.replaceAll = function(org, dest) {
		return this.split(org).join(dest);
	}

	$(document).ready(function () {
		activeMenu(location.pathname);
		var count = 0
		$(".countApproval").each(function(){
			var val = $(this).val()
			var _for = $(this).attr("for")
			count += val*1
			$(`#`+_for).append(`(`+val+`)`)
		})
		$("#mn-53").append(`(`+count+`)`)

		$(".countApprovalTranslate").each(function(){
			var val = $(this).val()
			var _for = $(this).attr("for")
			count += val*1
			$(`#`+_for).append(`(`+val+`)`)
		})
		$("#mn-59").append(`(`+count+`)`)
	})

	function activeMenu(pathname) {
		if(pathname != "/emp/mypage.hs"){
			const array = pathname.replace(".hs","").split("/");
			// 마지막 배열 제거[페이지이름제거]
			array.pop();
			const id = $("#"+array.join().replaceAll(",",""));

			id.parent().addClass("active");
			id.parent().parent().parent().addClass("active");
			id.parent().parent().parent().parent().parent().addClass("active");
		}
	}

	function moveLink(url){
		if(confirm('<spring:message code='lang.common.sitemove.msg'/>'))
		window.open(url);
	}
</script>