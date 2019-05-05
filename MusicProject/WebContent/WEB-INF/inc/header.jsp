<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
	<head>
		<base href="${pageContext.request.contextPath}/" />
		<title><%= request.getParameter("pageTitle") != null ? request.getParameter("pageTitle") + " | " : "" %>Music Pro</title>       
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/style.css" />
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
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
        <main> 
     		<%= request.getParameter("pageTitle") != null ? "<h1 class='pageTitle'>" + request.getParameter("pageTitle") + "</h1>" : "" %>       
	        <%= request.getParameter("pageSubtitle") != null ? "<h2 class='pageSubtitle'>" + request.getParameter("pageSubtitle") + "</h2>" : "" %>
