<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
	<head>
		<base href="${pageContext.request.contextPath}/" />
		<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0" charset="UTF-8">
		<title><%= request.getParameter("pageTitle") != null ? request.getParameter("pageTitle") + " | " : "" %>Music Pro</title>       
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css">
		<link rel="stylesheet" type="text/css" href="assets/css/style.css" />
	</head>
	<body>
       <header>
            <nav>
                <a href="${pageContext.request.contextPath}/" class="logo"><img alt="logo" src="assets/images/logo.png"></a>
                <a href="${pageContext.request.contextPath}/">All Artists</a>
                <a href="search">Search</a>
                <a href="favorites">My Favorites</a>
            </nav>
        </header>
        <% if(request.getAttribute("breadcrumb") != null) { %> 
		<div class='breadcrumb'>
			<%= request.getAttribute("breadcrumb") %> 
		</div>
		<% } %>
        <main <%= request.getAttribute("breadcrumb") == null ? "style='margin-top: 100px;'" : "" %>> 
     		<%= request.getParameter("pageTitle") != null ? "<h1 class='pageTitle'>" + request.getParameter("pageTitle") + "</h1>" : "" %>       
	        <%= request.getParameter("pageSubtitle") != null ? "<h2 class='pageSubtitle'>" + request.getParameter("pageSubtitle") + "</h2>" : "" %>
