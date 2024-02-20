<%@ page import="java.text.SimpleDateFormat" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
	<%
        String start_date = request.getParameter("start_date");
        if (start_date == null || start_date == "null") {
            start_date = "";
			start_date = java.time.LocalDate.now().minusDays(7).toString();
        }
        String end_date = request.getParameter("end_date");
        if (end_date == null || end_date == "null") {
            end_date = "";
			end_date = java.time.LocalDate.now().toString();
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
        String backSearch="start_date="+start_date+"&end_date="+end_date+"&levelDept="+levelDeptGet+"&optionSearch="+optionSearchGet+"&inputSearch="+inputSearchGet+"&displayStartPra="+displayStartPra;
    %>
		<style>
			.text_color_red {
				color: red
			}

			.dataTables_filter {
				display: none !important;
			}
			table#tbnotice td {
    			height: 80px;
			}
			.firt-custom.sorting_desc:after{
				display: none !important;
				padding: 0px;
			}
			.button-notification{
				width: 110px;
				margin-left: 5px;
			}
		</style>
		<div id="content" class="content">
			<h3 class="page-title">
				<spring:message code='notice.smalltitle' />
				<spring:message code='notice.bigtitle' />

			</h3>

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
												<td style="background: #f0f4f6;width: 15%;font-weight: bold">
													<spring:message code='search.title.regisdate' />
												</td>
												<td style="width: 35%">
													<input readonly id="start_date" style="background-color: white;"
														name="start_date" class="form-control form-control-sm mr-1"
														type="text" value="<%=start_date%>">~
													<input readonly id="end_date" name="end_date"
														style="background-color: white;"
														class="form-control form-control-sm mr-1" type="text"
														value="<%=end_date%>">
													<br><br>
													<a class="hihi btn-default form-control-sm mr-1" href="#"

														onclick="DateChange('today')">
														<spring:message code='search.button.daychoose.inday' />
													</a>
													<a class="hihi btn-default form-control-sm mr-1" href="#"
														 onclick="DateChange('oneweek')">
														<spring:message code='search.button.daychoose.inweek' />
													</a>
													<a class="hihi btn-default form-control-sm mr-1" href="#"

														onclick="DateChange('onemonth')">
														<spring:message code='search.button.daychoose.inmonth' />
													</a>
													<a class="hihi btn-default form-control-sm mr-1" href="#"

														onclick="DateChange('threemonth')">
														<spring:message code='search.button.daychoose.in3month' />
													</a>
													<a class="hihi btn-default form-control-sm mr-1" href="#"

														onclick="DateChange('sixmonth')">
														<spring:message code='search.button.daychoose.in6month' />
													</a>
													<a class="hihi btn-default form-control-sm mr-1" href="#"
														 onclick="DateChange('oneyear')">
														<spring:message code='search.button.daychoose.in1year' />
													</a>
													<a class="hihi btn-default form-control-sm mr-1" href="#"
														 onclick="DateChange('all')">
														<spring:message code='search.button.daychoose.inall' />
													</a>
												</td>
												<td></td>
												<td></td>
											</tr>
											<input type="hidden" id="levelDept" name="levelDept" value="">

											<tr>
												<td style="background: #f0f4f6;width: 15%;font-weight: bold">
													<spring:message code='dateboard.notice.company.timkiem' />
												</td>
												<td style="width: 35%">
													<select style="width: 30%;" id="optionSearch"
														name="optionSearch"
														class="custom-select custom-select-sm form-control form-control-sm mr-1">
														<option value="emp_name"
															${optionSearchGet.equals("emp_name") ? "selected" : ""
															}>
															<spring:message
																code='dateboard.notice.company.empname' />
														</option>
														<option value="position_name"
															${optionSearchGet.equals("position_name") ? "selected"
															: "" }>
															<spring:message code='work.list.position' />
														</option>
													</select>
													<input style="width: 50%;" id="inputSearch" name="inputSearch"
														class="form-control form-control-sm mr-1" type="text"
														value="<%=inputSearchGet%>">
													<button type="button" class="btn btn-primary"
														onclick="searchMethod()">
														<spring:message code='dateboard.notice.company.timkiem' />
													</button>
												</td>
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
			</div>
			<!-- html -->

			<div class="panel panel-inverse">

				<div class="panel-heading">
					<h4 class="panel-title">
						<spring:message code='emp.list.information' />
					</h4>
				</div>
				<div class="panel-body">
					<button class="open-AddBookDialog btn btn-primary button-notification" type="button" data-toggle="modal"
						style="float: right;" href="#" onclick="readAll()">
						<spring:message code='work.list.read' />
					</button>
					<button class="open-AddBookDialog btn btn-info button-notification" type="button" data-toggle="modal"
						style="float: right;" href="#" onclick="realAllOnPage()">
						<spring:message code='notice.readinpage' />
					</button>
					<!-- <input type="checkbox" name="all" id="checkall" /><spring:message code='work.list.readLable' /> -->
					<div class="table-notice" style="margin-top: 40px;">
						<table id="tbnotice" class="table table-striped table-bordered"
							style="width:100%;text-align: center; margin-top: 50px;">
							<thead>
								<tr>
									<th style="width:28px; vertical-align: inherit;" class="firt-custom"
										data-orderable="false">
										<input type="checkbox" name="all" id="checkall" />
									</th>
									<th style="text-align: center;width:168px;">
										<spring:message code='emp.list.department'/>
									</th>
									<th style="text-align: center;width:70px;">
										<spring:message code='notice.from' />
									</th>
									<th style="text-align: center;width:85px;">
										<spring:message code='position.list.poname' />
									</th>
									<th style="text-align: center;width:68px;">
										<spring:message code='work.detail.status' />
									</th>
									<th style="text-align: center; width:700px;">
										<spring:message code='notice.title' />
									</th>
									<th style="text-align: center;width:60px;">
										<spring:message code='notice.type' />
									</th>
									<th style="text-align: center;width:110px;">
										<spring:message code='work.list.registerDate' />
									</th>
								</tr>
							</thead>
						</table>
					</div>
					<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
						aria-labelledby="exampleModalLabel" aria-hidden="true">
						<div class="modal-dialog" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="exampleModalLabel">
										<spring:message code='work.detail.contents.change.confirm' />
									</h5>
									<input type="text" hidden="" value="" name="" id="giatri" />
									<button type="button" class="close" data-dismiss="modal" aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>

								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-secondary" data-dismiss="modal">
										<spring:message code='translation.close' />
									</button>
									<button type="button" class="btn btn-primary" onclick="realNotice()">
										<spring:message code='author.list.update' />
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			function realAllOnPage() {
				var lstChecked = "";
				var myTable = $('#tbnotice').dataTable();
				var rowcollection = myTable.$('input[name="checkAll"]:checked', { "page": "all" });
				rowcollection.each(function (index, elem) {
					var checkbox_value = $(elem).val();
					lstChecked += checkbox_value + ",";
				});
				if (lstChecked.trim() == "") {
					alert("<spring:message code='notice.select.message' />");
				} else {
					$("#exampleModal").modal('show');
				}
			}
			$(document).ready(function () {
				var start_date = $("#start_date").datepicker({dateFormat: 'yy-mm-dd'});
        		var end_date = $("#end_date").datepicker({dateFormat: 'yy-mm-dd'});
				$(".go_view_btn").click(function () {
					let deptId = $(this).attr('dept-id');
					console.log(deptId)
					location.href = deptId;
				});

				var lang_dt;
				if ('${_lang}' == 'ko') {
					lang_dt = lang_kor;
				} else if ('${_lang}' == 'en') {
					lang_dt = lang_en;
				} else {
					lang_dt = lang_vt;
				}
				var table = $('#tbnotice').DataTable({
					'displayStart': '<%=displayStartPra%>',
					"dom": '<"toolbar">frtip',
					'iDisplayLength': 10,
					"order": [[0, "desc"]],
					"responsive": true, "lengthChange": false, "autoWidth": false,
					"language": lang_dt,
					"bProcessing": true,
					"bServerSide": true,
					"bStateSave": false,
					"iDisplayStart": 0,
					"fnDrawCallback": function () {
					},
					"sAjaxSource": "/springPaginationDataTablesNotice?start_date=" + $('#start_date').val() + "&end_date=" + $('#end_date').val() + "&levelDept=" + $("#levelDept").val() + "&optionSearch=" + $('#optionSearch').val() + "&inputSearch=" + $('#inputSearch').val(),
					"columns": [
						{
							"data": "eventNoticeId",
							render: function (data, type, full) {
								return '<input class="cb-element" name="checkAll" type="checkbox" value="'
									+ data + '" >';
							}
						},
						{
							"data": "deptLevelName"
						},
						{
							"data": "empName"
						},{
							"data": "positionName"
						},
						{
							"data": "htmlStrOpen"
						},
						{
							"data": "content"
						},
						{
							"data": "type"
						},
						{
							"data": "timeUp"
						}
					],
					"fnRowCallback": function (nRow, aData, iDisplayIndex) {
						if (aData["htmlStrOpen"] == "Y") {
							$("td:eq(4)", nRow).html('<form><i class="fa fa-check" style="font-size: 18px; color: rgb(170, 169, 169);"></i></form>');
						} if (aData["content"] != null) {
							let strType = "'" + aData["type"] + "'";
							let strLink = "'" + aData["link"] + aData["noticeCd"] + "&deptLevel=1&txtSeach=&displayStartPra=0'";
							$("td:eq(5)", nRow).html('<div class="go_view_btn cursor-pointer cover-content" dept-id="' + aData["link"] + '' + aData["noticeCd"] + ' " onclick="readNoticeList(' + aData["noticeCd"] + ', ' + strType + ',' + strLink + ')" style=" width:100%;font-weight: bold;border:none;">' + aData["content"] + '</div>');
						}
						if (aData["htmlStrOpen"] == "N") {
							$("td:eq(4)", nRow).html('<form><i class="fa fa-star" style="font-size: 18px; color: #e0dc58;"></i></form>');
						}
						return nRow;
					}, "aoColumnDefs": [
						{"orderable": false,"targets": 0}
					],
				});

				$('#checkall').change(function () {
					$('.cb-element').prop('checked', this.checked);
				});

				$('.cb-element').change(function () {
					if ($('.cb-element:checked').length == $('.cb-element').length) {
						$('#checkall').prop('checked', true);
					}
					else {
						$('#checkall').prop('checked', false);
					}
				});

			});

			function readNoticeList(id, str, link) {

				if (str === 'TRANS' || str === 'PROJECT' || str === 'DAYPROJECT' || str === 'DAYPROJECTITEM') {
					var settings = {
						"url": "/api/readnotice/trans",
						"method": "POST",
						"headers": {
							"Content-Type": "application/json"
						},
						"data": JSON.stringify({
							"noticeCd": id,
							"type": str
						}),
					};
					$.ajax(settings).done(function (response) {
						if(str === 'TRANS'){
							location.href = "/translation/locationtran.hs";
						}else{
							location.href = "/project/project/dayproject.hs?pjId=" + id;
						}
						
					});
				} else {
					location.href = link;
				}
			}

			
			function realNotice() {
				var displayStartPra = $('#tbnotice').DataTable().page.info().page * 10;
				var lstChecked = "";
				var myTable = $('#tbnotice').dataTable();
				var rowcollection = myTable.$('input[name="checkAll"]:checked', { "page": "all" });
				rowcollection.each(function (index, elem) {
					var checkbox_value = $(elem).val();
					lstChecked += checkbox_value + ",";
				});

				lstChecked = lstChecked.substring(0,lstChecked.length-1)

				if (lstChecked.trim() == "") {
					toastr['error']('Read updata error !');
				} else {
					$.ajax({
						type: "POST",
						url: "/board/notice/detele",
						data: {
							'lstChecked': lstChecked,
						},
						success: function (response) {
							location.href = "/board/notice/list.hs?title=success&message=Delete sucess" + "&start_date=" + $('#start_date').val() + "&end_date=" + $('#end_date').val() + "&levelDept=" + $("#levelDept").val() + "&optionSearch=" + $('#optionSearch').val() + "&inputSearch=" + $('#inputSearch').val() + "&displayStartPra=" + displayStartPra;
						},
						error: function (error) {
							toastr['error']('Function error !');
						}
					})
				}
			}

			function readAll(){
				if(confirm("<spring:message code='work.detail.contents.change.confirm' />")){
					console.log("DXD")
					$.ajax({
						type:"POST",
						url:"/board/notice/readall",
						data:{},
						success: function (response){
							location.href = "/board/notice/list.hs";
						},
						error: function (err){
							toastr[err]('Function error !');
						}
					})
				}
			}
			
			function searchMethod() {
				console.log($("#inputSearch").val())
				$('#tbnotice').DataTable().ajax.url("/springPaginationDataTablesNotice?start_date=" + $('#start_date').val() + "&end_date=" + $('#end_date').val() + "&levelDept=" + $("#levelDept").val() + "&optionSearch=" + $('#optionSearch').val() + "&inputSearch=" + $('#inputSearch').val()).load();
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
		</script>