<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Könyvek listája</title>
</head>
<body>
	<span>Üdvözlöm <c:out value="${username}"></c:out>.
	<a href="${pageContext.request.contextPath}/logout">Kilépés</a></span>

	<table border= "1">
		<tr>
			<th>Azonosító</th>
			<th>Könyv címe</th>
			<th>Szerző</th>
			<th>Leírás</th>
			<th>Kiadás éve</th>
			<th></th>
		</tr>
		<c:forEach items="${books}" var="book">
			<tr>
				<td><c:out value="${book.id}" /></td>
				<td><c:out value="${book.title}" /></td>
				<td><c:out value="${book.author}" /></td>
				<td><c:out value="${book.description}" /></td>
				<td><c:out value="${book.pubYear}" /></td>
				<td>
				<a href="book_details?bookId=${book.id}" >Megtekintés</a>
<%-- 				<c:if test="${isEditor}"> --%>
					<a href="book_edit?bookId=${book.id}" >Szerkesztés</a>
					<a href="book_delete?bookId=${book.id}" >Törlés</a>
<%-- 				</c:if> --%>
				</td>
			</tr>
		</c:forEach>
	</table>
	<a href="book_edit">Könyv felvitele</a>
	<c:if test="${empty books}">Nincs felvéve könyv</c:if>
</body>
</html>