<%@ page import="kr.co.hs.board.dto.BoardControlDTO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
<% 
	String start_date=request.getParameter("start_date"); if (start_date==null || start_date=="null" ) {
	start_date="" ; } String end_date=request.getParameter("end_date"); if (end_date==null || end_date=="null" )
	{ end_date="" ; } String levelDeptGet=request.getParameter("levelDept"); if (levelDeptGet==null ||
	levelDeptGet=="null" ) { levelDeptGet="1" ; } request.setAttribute("levelDeptGet", levelDeptGet); String
	optionSearchGet=request.getParameter("optionSearch"); if (optionSearchGet==null || optionSearchGet=="null" )
	{ optionSearchGet="" ; } request.setAttribute("optionSearchGet", optionSearchGet); String
	inputSearchGet=request.getParameter("inputSearch"); if (inputSearchGet==null || inputSearchGet=="null" ) {
	inputSearchGet="" ; } String displayStartPra=request.getParameter("displayStartPra"); if
	(displayStartPra==null || displayStartPra=="null" ) { displayStartPra="0" ; }
	String translationStatusGet = request.getParameter("translationStatus");
	if (translationStatusGet == null || translationStatusGet == "null") {
		translationStatusGet = "";
	}
	request.setAttribute("translationStatusGet", translationStatusGet);
%>
<style>
	.text_color_red {
		color: red
	}

	.dataTables_filter {
		display: none !important;
	}
	.time-order{
	    float: left;
	    margin-top: 4px;
	    width: 81px;
	    text-align: center;
	}
</style>
<div id="content" class="content">
	<h1 class="page-header cursor-default">
		<spring:message code='dateboard.title' /> - <spring:message code='dateboard.notice.company.list' />
		<br>
		<small>
			<spring:message code='dateboard.notice.general.detail' />
		</small>
	</h1>

	<div class="panel panel-inverse">
		<div class="panel-heading">
			<h4 class="panel-title">
				<spring:message code='dateboard.notice.company.timkiem' />
			</h4>
		</div>
		<div class="row">
			<div class="col-xl-12 ui-sortable">
				<div class="panel panel-inverse" style="">
					<div class="panel-body">
						<form id="myform" class="form-inline" action="">
							<table class="table mb-0">
								<tbody>
									<tr>
										<td style="background: #f0f4f6;width: 120px;font-weight: bold"><spring:message code='search.title.regisdate' /></td>
										<td>
											<input readonly id="start_date" name="start_date" class="form-control form-control-sm mr-1 bg-white" type="text" value="<%=start_date%>">~
											<input readonly id="end_date" name="end_date" class="form-control form-control-sm mr-1 bg-white" type="text" value="<%=end_date%>">
											<br>
											<a class="time-order btn-default form-control-sm mr-1" href="#" onclick="DateChange('today')"><spring:message code='search.button.daychoose.inday'/></a>
											<a class="time-order btn-default form-control-sm mr-1" href="#" onclick="DateChange('oneweek')"><spring:message code='search.button.daychoose.inweek'/></a>
											<a class="time-order btn-default form-control-sm mr-1" href="#" onclick="DateChange('onemonth')"><spring:message code='search.button.daychoose.inmonth'/></a>
											<a class="time-order btn-default form-control-sm mr-1" href="#" onclick="DateChange('threemonth')"><spring:message code='search.button.daychoose.in3month'/></a>
											<a class="time-order btn-default form-control-sm mr-1" href="#" onclick="DateChange('sixmonth')"><spring:message code='search.button.daychoose.in6month'/></a>
											<a class="time-order btn-default form-control-sm mr-1" href="#" onclick="DateChange('oneyear')"><spring:message code='search.button.daychoose.in1year'/></a>
											<a class="time-order btn-default form-control-sm mr-1" href="#" onclick="DateChange('all')"><spring:message code='search.button.daychoose.inall'/></a>
										</td>
									</tr>
									<tr>
										<td style="background: #f0f4f6;font-weight: bold"><spring:message code='work.list.translationstatus'/></td>
										<td class="row m-0">
											<select id="translationStatus" name="translationStatus" class="custom-select custom-select-sm form-control form-control-sm col-sm-12 col-md-6 col-xl-3">
												<option value=""><spring:message code='search.selectoption.first'/></option>
												<option value="Y" ${translationStatusGet.equals("Y") ? "selected" : "" }><spring:message code='work.detail.finish'/></option>
												<option value="N" ${translationStatusGet.equals("N") ? "selected" : "" }><spring:message code='work.detail.notfinish'/></option>
											</select>
										</td>
									</tr>
									<input type="hidden" id="levelDept" name="levelDept" value="">

									<tr>
										<td style="background: #f0f4f6;font-weight: bold"><spring:message code='dateboard.notice.company.timkiem' /></td>
										<td>
											<select id="optionSearch" name="optionSearch" class="custom-select custom-select-sm form-control form-control-sm m-2">
                                                <option value="emp_name" ${optionSearchGet.equals("emp_name") ? "selected" : "" }><spring:message code='dateboard.notice.company.empname'/></option>
                                                <option value="position_name" ${optionSearchGet.equals("position_name") ? "selected" : "" }><spring:message code='work.list.position'/></option>
                                                <option value="title" ${optionSearchGet.equals("title") ? "selected" : "" }><spring:message code='work.list.title'/></option>
                                            </select>
                                            <input id="inputSearch" name="inputSearch" class="form-control form-control-sm m-2" type="text" value="<%=inputSearchGet%>" />
                                            <button type="button" class="btn btn-sm btn-primary m-2" onclick="searchMethod()"><spring:message code='dateboard.notice.company.timkiem'/></button>
                                        </td>
									</tr>
								</tbody>
							</table>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div>
		<c:choose>
			<c:when test="${admin=='Y'}">
				<button class="open-AddBookDialog btn btn-info mb-2" type="button" onclick="openModel()"><spring:message code='work.list.delete' /></button>
				<button class="btn btn-primary mb-2" onclick="addW()"><spring:message code='dateboard.notice.company.add' /></button>
			</c:when>
		</c:choose>
	</div>
	<div class="panel panel-inverse">
		<div class="panel-heading">
			<h4 class="panel-title"><spring:message code='emp.list.information' /></h4>
		</div>
		<div class="panel-body">
			<table id="tbwork" class="table table-striped table-bordered w-100 text-center">
				<thead>
					<tr>
						<th class="all"><input type="checkbox" name="all" id="checkall" /></th>
						<th><spring:message code='dateboard.notice.company.no' /></th>
						<th class="min-tablet"><spring:message code='dateboard.notice.company.phong' /></th>
						<th><spring:message code='work.list.empname' /></th>
						<th class="min-tablet"><spring:message code='work.list.position'/></th>
						<th class="min-tablet"><spring:message code='dateboard.notice.company.title' /></th>
						<th class="min-tablet"><spring:message code='work.list.registerDate' /></th>
						<th><spring:message code='work.list.translationstatus'/></th>
						<th class="min-tablet"><spring:message code='work.list.function' /></th>
					</tr>
				</thead>
			</table>

		</div>
	</div>
	<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel"><spring:message code='confirm.delete' /></h5>
					<input type="text" hidden="" value="" name="" id="giatri" />
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code='translation.close' /></button>
					<button type="button" class="btn btn-primary" onclick="deletBoard()"><spring:message code='work.list.delete' /></button>
				</div>
			</div>
		</div>
	</div>
</div>
<%
	String langCd = LocaleContextHolder.getLocale().toString();
	String ViewDetail="";
	if(langCd.equals("vt")){ViewDetail="Chi tiết";}else if(langCd.equals("en")){ViewDetail="View detail";}else{ViewDetail="상세";}
	String str="";
	ArrayList<BoardControlDTO> lst = (ArrayList<BoardControlDTO>) request.getAttribute("selectTopByNoticeYn");
	if (lst != null) {
		for (BoardControlDTO item : lst) {
			str+="<tr style='background-color:lightgray;'><td><input class='cb-element' id='ck' name='xoa' type='checkbox' value='"+item.getBoardId()+"' /></td><td>"+item.getNotice()+"</td><td>"+item.getDeptName()+"</td><td>"+item.getEmpName()+"</td><td>"+item.getPositionName()+"</td><td>"+item.getTitle()+"</td><td>"+item.getRegDt()+"</td><td>"+item.getTranslationStatus()+"</td><td><button type='button' onclick='detail("+item.getBoardId()+")' class='btn btn-success' >"+ViewDetail+"</button></td></tr>";
		}
	}
%>
<script type="text/javascript">
	function openModel() {
		var lstChecked = "";
		var myTable = $('#tbwork').dataTable();
		var rowcollection = myTable.$('input[name="xoa"]:checked', {"page": "all"});
		rowcollection.each(function (index, elem) {
			var checkbox_value = $(elem).val();
			lstChecked += checkbox_value + ",";
		});
		$('#tbwork tr td #ck').each(function() {
			if (this.checked) {
				lstChecked+=this.value+ ",";
			}
		});
		if (lstChecked.trim() == "") {
			alert("<spring:message code='notice.select.message' />");
		} else {
			$("#exampleModal").modal('show');
		}
	}
	$(document).ready(function () {
		// $("#go_write_btn").click(function(){
		// 	location.href="/board/notice/${boardType}/";
		// });

		$(".search_btn").click(function () {
			$("#search").submit();
		});

		if ('${_lang}' == 'ko') {
			lang_dt = lang_kor;
		} else if ('${_lang}' == 'en') {
			lang_dt = lang_en;
		} else {
			lang_dt = lang_vt;
		}
		$('#checkall').change(function () {
			$('.cb-element').prop('checked', this.checked);
		});
		$('#checkall').change(function () {
			$('.cb-element').prop('checked', this.checked);
		});
		var start_date = $("#start_date").datepicker({ dateFormat: 'yy-mm-dd' });
		var end_date = $("#end_date").datepicker({ dateFormat: 'yy-mm-dd' });

		var table = $('#tbwork').DataTable({
			'displayStart': <%=displayStartPra%>,
			"dom": '<"toolbar">frtip',
			"order": [[1, "desc"]],
			'iDisplayLength': 10,
			"responsive": true, "lengthChange": false, "autoWidth": false,
			"language": lang_dt,
			"bProcessing": true,
			"bServerSide": true,
			"bStateSave": false,
			"iDisplayStart": 0,
			"fnDrawCallback": function () { },
			"sAjaxSource": "/springPaginationDataTablesBoardFree?start_date=" + $('#start_date').val() + "&end_date=" + $('#end_date').val() + "&levelDept=" + $("#levelDept").val() + "&optionSearch=" + $('#optionSearch').val() + "&inputSearch=" + $('#inputSearch').val()+"&translationStatus=" + $('#translationStatus').val(),
			"columns": [
				{
					"data": "boardId",
					render: function (data, type, full) {
						return '<input class="cb-element" name="xoa" type="checkbox" value="'+ data + '" >';
					}
				},
				{ "data": "rownum" },
				{ "data": "deptName" },
				{ "data": "empName" },
				{ "data": "positionName" },
				{ "data": "title" },
				{ "data": "regDt" },
				{ "data": "translationStatus" }
			],
			"aoColumnDefs": [
				{
					"aTargets": [8],
					"mData": "boardId",
					"mRender": function (data, type, full) {
						return '<button type="button" onclick="detail('
							+ data
							+ ')" class="btn btn-success"><spring:message code="work.list.viewdetail" /></button>';
					}
				},
				{
					"orderable": false,
					"targets": 0
				}
			]
		})

		$('#tbwork').on( 'page.dt', function () {
			var displayStartPra = $('#tbwork').DataTable().page.info().page * 10;
			location.href = "/board/notice/free/list.hs?start_date=" + $('#start_date').val() + "&end_date=" + $('#end_date').val() + "&levelDept=" + $("#levelDept").val() + "&optionSearch=" + $('#optionSearch').val() + "&inputSearch=" + $('#inputSearch').val() + "&translationStatus=" + $('#translationStatus').val()+"&displayStartPra=" + displayStartPra;
		});
	});
	function addW() {
			location.href = "/board/notice/free/write.hs";
		};
		function searchMethod() {
			var displayStartPra = 0;
			// $('#tbwork').DataTable().ajax.url("/springPaginationDataTablesBoardFree?start_date=" + $('#start_date').val() + "&end_date=" + $('#end_date').val() + "&levelDept=" + $("#levelDept").val() + "&optionSearch=" + $('#optionSearch').val() + "&inputSearch=" + $('#inputSearch').val()+"&translationStatus=" + $('#translationStatus').val()).load();
			location.href = "/board/notice/free/list.hs?start_date=" + $('#start_date').val() + "&end_date=" + $('#end_date').val() + "&levelDept=" + $("#levelDept").val() + "&optionSearch=" + $('#optionSearch').val() + "&inputSearch=" + $('#inputSearch').val() + "&translationStatus=" + $('#translationStatus').val()+"&displayStartPra=" + displayStartPra;
		}

		function DateChange(createDate) {
			if (createDate == "today") {
				var today = GetOutDate(0);
				$("#start_date").val(today);
				$("#end_date").val(today);
			} else if (createDate == "oneweek") {
				var today = GetOutDate(0);
				var out_date = GetOutDate(7);
				$("#start_date").val(out_date);
				$("#end_date").val(today);
			} else if (createDate == "onemonth") {
				var today = GetOutDate(0);
				var out_date = GetOutDate(30);
				$("#start_date").val(out_date);
				$("#end_date").val(today);
			} else if (createDate == "threemonth") {
				var today = GetOutDate(0);
				var out_date = GetOutDate(90);
				$("#start_date").val(out_date);
				$("#end_date").val(today);
			} else if (createDate == "sixmonth") {
				var today = GetOutDate(0);
				var out_date = GetOutDate(180);
				$("#start_date").val(out_date);
				$("#end_date").val(today);
			} else if (createDate == "oneyear") {
				var today = GetOutDate(0);
				var out_date = GetOutDate(360);
				$("#start_date").val(out_date);
				$("#end_date").val(today);
			} else if (createDate == "all") {
				var today = GetOutDate(0);
				var out_date = GetOutDate(365);
				$("#start_date").val("");
				$("#end_date").val("");
			}
		}
		function GetOutDate(minusDay) {
			var d = new Date();
			d.setDate(d.getDate() - minusDay);
			var month = d.getMonth() + 1;
			var day = d.getDate();
			var output = d.getFullYear() + '-' + (month < 10 ? '0' : '') + month + '-' + (day < 10 ? '0' : '') + day;
			return output;
		}

		function deletBoard() {

			var lstChecked = "";
			var myTable = $('#tbwork').dataTable();
			var rowcollection = myTable.$('input[name="xoa"]:checked', { "page": "all" });
			rowcollection.each(function (index, elem) {
				var checkbox_value = $(elem).val();
				lstChecked += checkbox_value + ",";
			});
			$('#tbwork tr td #ck').each(function() {
				if (this.checked) {
					lstChecked+=this.value+ ",";
				}
			});

			if (lstChecked.trim() == "") {
				toastr['error']('Delete error !');
			} else {
				$.ajax({
					type: "POST",
					url: "/board/delete",
					data: {
						'lstChecked': lstChecked,
					},
					success: function (response) {
						location.href = "/board/notice/free/list.hs?title=success&message=Delete sucess";
					},
					error: function (error) {
						toastr['error']('Delete error !');
					}
				})
			}
		}

		function detail(id) {
			var displayStartPra = $('#tbwork').DataTable().page.info().page * 10;
			location.href = "/board/notice/free/view.hs?boardId=" + id+ "&start_date=" + $('#start_date').val() + "&end_date=" + $('#end_date').val() + "&levelDept=" + $("#levelDept").val() + "&optionSearch=" + $('#optionSearch').val() + "&inputSearch=" + $('#inputSearch').val() + "&translationStatus=" + $('#translationStatus').val()+"&displayStartPra=" + displayStartPra;
		}
</script>