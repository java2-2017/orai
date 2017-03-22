<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="mik" uri="/WEB-INF/Example.tld" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Hello!</title>
</head>
<body>
	<h1>Hello World!</h1>
	
	<%@ page import="java.io.*, java.util.*, javax.servlet.*" %>
	<% Date date = new Date(); %>
	<h2><%= date.toString() %></h2>
	
	<% request.setAttribute("names", new String[] {"Béla","Tamás","Gábor"}); %>
	<c:forEach items="${names}" var="name">
		<mik:sayHello>${name}</mik:sayHello>
	</c:forEach>
	
	<a href="book_list">Könyvek listája</a>
</body>
</html>