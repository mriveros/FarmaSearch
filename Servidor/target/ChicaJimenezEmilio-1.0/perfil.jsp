<%@ page contentType="text/html" language="java" import="java.util.*,com.chicajimenez.emilio.modelo.*" pageEncoding="UTF-8"%>
    <%
     if (session.getAttribute("id_usuario") != null
       && !session.getAttribute("id_usuario").equals("")) {%>
    	 <%!long id_usuario;%>
    	 <% id_usuario = (Long)session.getAttribute("id_usuario");
     } else {
      	response.sendRedirect("login.jsp");
     }
%>
<!DOCTYPE html>
<!-- saved from url=(0045)http://admin.guiapueblo.es/comerciales/create -->
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
     
  <div class="panel panel-default">
                  <div class="panel-heading">Mi perfil</div>
                   
      <div class="panel-body">
                            <form method="POST" action="rest/servicioU/Usuario" accept-charset="UTF-8" autocomplete="on" class="form-horizontal" role="form">
            
						<input name="id" type="hidden" id="id_usuario">
						<input type="hidden" name="tipo" value="1">

                        <div class="form-group">
                            <label class="col-md-4 control-label">Nombre</label>
                            <div class="col-md-6">
                                <input type="text" class="form-control" id="nombre" name="nombre" value="">
                            </div>
                        </div>
                         <div class="form-group">
                            <label class="col-md-4 control-label">Apellidos</label>
                            <div class="col-md-6">
                                <input type="text" class="form-control" id="apellidos" name="apellidos" value="">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label">E-Mail</label>
                            <div class="col-md-6">
                                <input type="email" class="form-control" id="email" name="email" value="">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label">Contraseña</label>
                            <div class="col-md-6">
                                <input type="password" class="form-control" name="password">
                            </div>
                        </div>
                 <div class="text-right">
              
                                <input class="btn btn-default btn-sm" type="submit" value="Registrarse">
                
                				<a class="btn btn-default btn-sm" href="javascript:window.history.back();">CANCELAR</a>
  				</div>
                
                </form></div>
            </div> 
        </div>
       
  </div>
     
  
  


<br>


	<script type="text/javascript">
	$( document ).ready(function() {
		cargaDatos();
	});  
	function cargaDatos(){
		   $.ajax({
				type: "GET",
				url: "rest/servicioU/Usuario/"+<%=id_usuario%>,
				success: function(response){	
					$('#id_usuario').val(response.id);
					$('#nombre').val(response.nombre);
					$('#apellidos').val(response.apellidos);
					$('#email').val(response.email);
				},
				error: function() {
					 alert('fail');
				}
		   });
	}
	</script>
	<!-- Scripts -->
	<script src="./javascript/jquery.min.js"></script>
	<script src="./bootstrap/js/bootstrap.min.js"></script>


</body></html>