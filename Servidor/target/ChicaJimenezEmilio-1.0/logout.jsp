<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
session.removeAttribute("usuario");
session.invalidate();
response.sendRedirect("inicio.jsp");
%>