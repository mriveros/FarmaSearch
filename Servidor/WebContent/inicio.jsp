<%@ page contentType="text/html" language="java" import="java.util.*" pageEncoding="UTF-8"%>
    <%
     if (session.getAttribute("id_usuario") != null
       && !session.getAttribute("id_usuario").equals("")) {

     } else {
      	response.sendRedirect("login.jsp");
     }
    %>
<!DOCTYPE html>
<html lang="en"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>FarmaSearch</title>

	<link href="./css/app.css" rel="stylesheet">
	<link href="./css/style.css" rel="stylesheet">
	<link href="./css/fileinput.css" rel="stylesheet">

	<link media="all" type="text/css" rel="stylesheet" href="./bootstrap/css/bootstrap-datetimepicker.min.css">


	<!-- Fonts -->
	<link href="./css/css" rel="stylesheet" type="text/css">
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
	<script src="./javascript/fileinput.js"></script>


</head>
<body>
	<%@include file="include/navegacion.jsp" %>

	<div class="container">
	<div class="row">
		<div class="col-md-10 col-md-offset-1">
			<div class="panel panel-default">
				<div class="panel-heading">Menú Rapido</div>

				<div class="panel-body">
					<a href="gestionFarmacia.jsp">
						<div class="col-md-2 botonInicio">
         					<img src="./img/empresa.png">
         					<br>
         					Farmacias
         				</div>
         			</a>

                    <a href="gestionDepartamento.jsp">
						<div class="col-md-2 botonInicio">
         					<img src="./img/ciudad.png">
         					<br>
         					Departamentos
         				</div>
         			</a>
         			<a href="gestionProducto.jsp">
						<div class="col-md-2 botonInicio">
         					<img src="./img/categoria.png">
         					<br>
         					Productos
         				</div>
         			</a>
         			<a href="gestionInventario.jsp">
						<div class="col-md-2 botonInicio">
         					<img src="./img/noticias.png">
         					<br>
         					Inventario
         				</div>
         			</a>
         			<a href="gestionReservas.jsp">
						<div class="col-md-2 botonInicio">
         					<img src="./img/subcategoria.png">
         					<br>
         					Reservas
         				</div>
         			</a>

                 
            	</div>
			</div>
		</div>
	</div>
</div>
	
	<!-- Scripts -->
	<script src="./javascript/jquery.min.js"></script>
	<script src="./bootstrap/js/bootstrap.min.js"></script>


</body></html>