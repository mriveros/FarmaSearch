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
<!-- saved from url=(0035)http://admin.guiapueblo.es/empresas -->
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

	<script src="./bootstrap/js/bootstrap-datetimepicker.js"></script>

	<script src="./bootstrap/js/bootstrap-datetimepicker.es.js"></script>

	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
		<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	<![endif]-->

<style type="text/css"></style></head>
<body>
	<%@include file="include/navegacion.jsp" %>
<div class="container">
	<div class="row">

	
    
     <div class="panel panel-default">
        <div class="panel-heading">Reservas</div>
       
        <div class="panel-body">
						<div class="form-group form-group-sm" style="margin-bottom:20px;">
			                <label for="id_farmacia" class="control-label col-md-1"><h4>Farmacia:</h4></label>
			                <div class="col-md-10">
			                	<select id="id_farmacia" name="id_farmacia" class="form-control"><option>Seleccionar...</option></select>
			            	</div>
		            	</div>
		         <div class="col-md-12">
								<h3>Reservas</h3>
	                            <table class="table table-striped">
	                                <tbody id="filasItems">
	                                <tr>
	                                    <th align="left" scope="col" class="col-md-6">Usuario</th>
	                                    <th align="center" scope="col" style="width:20px;">Producto</th>
	                                    <th align="center" scope="col" style="width:20px;">Cantidad</th>
	                                    <th align="center" scope="col" style="width:20px;">Fecha</th>
	                                </tr>


	                            </tbody></table>
	                    
	                        </div>
	                   </div>
	                </div>
	        </div>

    </div>
</div>

	<script type="text/javascript">
	
	 
	$( document ).ready(function() {
		cargaFarmaciasSelect();
		$("#id_farmacia").change( function () {
			var idFarm=$(this).val();
			 cargaReservas(idFarm);
		   });
	});  

		
		function cargaReservas(id){
			$('#filasItems').empty();
			$('#filasItems').append('<tr>'+
                    '<th align="left" scope="col" class="col-md-6">Usuario</th>'+
                    '<th align="center" scope="col" style="width:20px;">Producto</th>'+
                    '<th align="center" scope="col" style="width:20px;">Cantidad</th>'+
                    '<th align="center" scope="col" style="width:20px;">Fecha</th>'+
                '</tr>');
			
			 $.ajax({
					type: "GET",
					url: "rest/servicioR/Reservas/"+id,
					success: function(response){
						$.each(response, function (i, item) {
							$.each(item, function (k, reserva) {
							$.each(reserva.lineaReservas, function (j, linea) {
							$('#filasItems').append(	
							'<tr id="fila_'+item.id+'">'+
							'<td>'+reserva.usuario.email+'<input id="prod_'+reserva.id+'" type="hidden" value="'+reserva.usuario.id+'"></td>'+ 
							'<td>'+linea.producto.nombre+'<input id="prod_'+linea.producto.id+'" type="hidden" value="'+linea.producto.id+'"></td>'+ 
							'<td>'+linea.cantidad+'<input id="linea_'+linea.id+'" type="hidden" value="'+linea.id+'"></td>'+ 
							'<td>'+ new Date(reserva.fecha).toISOString()+'</td>'+ 
	                        '</tr>');
							});
						});
						});
						  
					},
					error: function() {
						 alert('fail');
					}
			});
		}
	   function cargaFarmaciasSelect(){
		   $.ajax({
					type: "GET",
					url: "rest/servicioF/Farmacias/"+<%=id_usuario %>,
					async:false,
					success: function(response){
						$.each(response, function (i, item) {
							
						    $('#id_farmacia').append($('<option>', { 
						        value: item.id,
						        text : item.nombre 
						    }));
						});
						  
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