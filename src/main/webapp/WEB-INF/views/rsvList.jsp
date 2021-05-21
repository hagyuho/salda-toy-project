<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>rsvList</title>
</head>
<body>
	<div class="container" style="text-align: center;">
		<h1>살다 이사 예약페이지</h1>
		<h2>나의 이사예약목록</h2>
		<table class="table table-hover" style="text-align: center;">
			<tr>
				<th>예약번호</th>
				<th>동</th>
				<th>호</th>
				<th>이사일자</th>
				<th>예약일자</th>
				<th>예약전화번호</th>
				<th>예약진행여부</th>
				<th>예약취소</th>

			</tr>
			<c:forEach var="l" items="${list}">
				<tr>
					<td>${l.rsvId}</td>
					<td>${l.dong}</td>
					<td>${l.ho}</td>
					<td>${l.moveDttm}</td>
					<td>${l.rsvDttm}</td>
					<td>${l.hpNumber}</td>
					<td>${l.rsvYn}</td>
					<td>
						<button class="btn btn-primary"
							onclick="cancel_btn_onclick('${l.hpNumber}')">예약취소하기</button>
					</td>
				</tr>
			</c:forEach>

		</table>
		<button class="btn btn-primary" onclick="rsv_btn_onclick()">예약하기</button>

	</div>


	<%@ include file="bootstrap.jsp"%>
</body>
<script type="text/javascript">
	//예약취소
	function cancel_btn_onclick(hpNumber) {
		$.ajax({
			url : 'http://localhost:8080/salda/v1/rsvlist',
			type : 'put',
			async : true,
			dataType : "json",
			data : JSON.stringify({
				'hpNumber' : hpNumber
			}),
			contentType : "application/json",
			success : function(data) {

			},
			error : function(err) {
				alert(err.responseText);
				location.reload(true);
			}
		});
	}

	//예약페이지로 이동
	function rsv_btn_onclick() {
		location.href = 'http://localhost:8080/rsv';
	}
</script>
</html>