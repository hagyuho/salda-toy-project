<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>살다예약</title>
</head>

<body>
	<div class="container" style="text-align: center;">
		<h1>살다 이사 예약페이지</h1>
		<h2>예약정보 입력</h2>
		<form id=form>
			<div class="form-group">
				<label for="dong">동</label> <input type="text" class="form-control"
					id="dong" name="dong" placeholder="이사 하시는 동을 입력하세요.">
			</div>
			<div class="form-group">
				<label for="ho">호</label> <input type="text" class="form-control"
					id="ho" name="ho" placeholder="이사 하시는 호수를 입력하세요.">
			</div>
			<div class="form-group">
				<label for=hpNumber">휴대폰번호</label> <input type="text"
					class="form-control" id="hpNumber" name="hpNumber"
					placeholder="-는 제외하고 입력해주세요 (ex) 01012345678">
			</div>
			<div class="form-group">
				<label for="moveDttm">이사일자</label> <input type="text"
					class="form-control" id="moveDttm" name="moveDttm"
					placeholder="이사일자를 입력해주세요">
			</div>
			<button type="button" class="btn btn-primary"
				onclick="rsv_btn_onclick()">예약</button>
		</form>
	</div>
	<%@ include file="bootstrap.jsp"%>
</body>

<script type="text/javascript">
	function rsv_btn_onclick() {

		var formSerializeArray = $('#form').serializeArray();
		var object = {};
		for (var i = 0; i < formSerializeArray.length; i++) {
			object[formSerializeArray[i]['name']] = formSerializeArray[i]['value'];
		}

		var json = JSON.stringify(object);
		$.ajax({
			url : 'http://localhost:8080/salda/v1/rsvlist',
			type : 'post',
			async : true,
			data : json,
			dataType : "json",
			contentType : "application/json",
			error : function(err) {
				if ("200" == err.status) {
					alert(err.responseText);
					location.href = "http://localhost:8080/enterProc?hpNumber="
							+ $('input#jbInput').val() + "'";
				} else {
					alert(err.responseJSON.message);
				}
			}
		});
	}

	function moveRsvListPage() {

	}
</script>
</html>