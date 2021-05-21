<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert Form</title>
</head>
<body>
<div class="container" content="text/html; charset=UTF-8">
<h1>살다 이사 예약페이지</h1> 
<h2>시작화면 </h2>
    <form action="/enterProc" method="get">
      <div class="form-group">
        <label for="hpNumber">핸드폰 번호 입력</label>
        <input type="text" class="form-control" id="hpNumber" name="hpNumber" placeholder="-는 제외하고 입력해주세요. (ex) 01012345678.">
      </div>
       <button type="submit" class="btn btn-primary">입장</button>
    </form>
</div>
<%@ include file="bootstrap.jsp" %>
</body>
</html>