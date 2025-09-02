<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>

					<c:forEach items="${list }" var="vo" varStatus="status">
					<tr>
						<td>${vo.id}</td>
						<td style="text-align:left; padding-left:${(vo.depth-1) * 20}px">
							<c:if test="${vo.o_no >= 2 }">
							<img src="${pageContext.request.contextPath }/assets/images/reply.png">
							</c:if>
							<a href="${pageContext.request.contextPath}/board?a=view&id=${vo.id }">${vo.title }</a></td>
						<td>${vo.name }</td>
						<td>${vo.hit }</td>
						<td>${vo.regDate }</td>
						<td>
						<c:if test="${authUser.name == vo.name }">
						<a href="${pageContext.request.contextPath}/board?a=delete&id=${vo.id }" class="del"
							style='background:url("${pageContext.request.contextPath }/assets/images/recycle.png") no-repeat 0 0;'>삭제</a>
						</c:if>
						</td>
					</tr>
					</c:forEach>
				</table>

				<!-- pager 추가 -->
				<div class="pager">

					<ul>
						<c:if test="${page > 1}">
							<li><a href="${pageContext.request.contextPath }/board?page=${page - 1}">◀</a></li>
						</c:if>

						<c:forEach begin="${startPage }" end="${startPage + 4 }" step="1" var="i">
						<li class="${i == page ? 'selected' : ''}">

						<c:choose>
    						<c:when test="${i <= endPage}">
								<a href="${pageContext.request.contextPath }/board?page=${i }">${i }</a>
							</c:when>
							<c:otherwise>
								<span>${i }</span>
							</c:otherwise>
						</c:choose>
</li>
						</c:forEach>
						<c:if test="${page < endPage}">
							<li><a href="${pageContext.request.contextPath }/board?page=${page + 1}">▶</a></li>
						</c:if>
					</ul>
				</div>

				<!-- 로그인 시 글쓰기 버튼 -->
				<c:if test="${authUser != null }">
					<div class="bottom">
						<a href="${pageContext.request.contextPath}/board?a=writeform" id="new-book">글쓰기</a>
					</div>
				</c:if>

			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>