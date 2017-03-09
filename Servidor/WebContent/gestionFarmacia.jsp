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
        <div class="panel-heading">Farmacias</div>
       
        <div class="panel-body">

                <div class="col-md-12">

                			<table class="headerIndex">
                                <tbody><tr> 
                                    <td class="botonNuevo">
                                    	<a href="addEditFarmacia.jsp" class="btn btn-default btn-sm">Crear Farmacia</a>
                                    </td>
                                    <td class="busquedaIndex" style="width:30%;">
                                        <form action="#" method="GET">
                                            <input id="ordenar" class="hidden" name="ordenar" type="text" value="farmacia.nombre">
                                            <input id="valor" class="hidden" name="valor" type="text" value="desc">
                                            <div class="input-group input-group-sm">
                                                <input id="clave" class="form-control" name="clave" type="text" value="">
                                                <span class="input-group-btn">
                                                    <input id="buscar" name="buscar" class="btn btn-default" type="submit" value="Buscar">
                                                </span>
                                            </div>
                                    </form></td>
                                </tr>
                            </tbody></table>

                            <table class="table table-striped">
                                <tbody id="filasFarmacias">
                                <tr>
                                    <th align="left" scope="col"><input type="submit" class="btn btn-link" value="Farmacia" id="ordNombre" name="ordNombre"></th>
                                    <th align="center" scope="col" style="width:50px;">Editar</th>
                                    <th align="center" scope="col" style="width:50px;">Eliminar</th>
                                </tr>

                            </tbody></table>
                        </div>
                </div>
        </div>

    </div>
</div>
	<script type="text/javascript">

$( document ).ready(function() {
	cargaDatos();
	$('button').click(function(){
		var idBoton = $(this).attr("id");
		var elementos = idBoton.split("_");
		eliminaFarmacia(elementos[1]);
	});
});
   function cargaDatos(){
	   $.ajax({
				type: "GET",
				url: "rest/servicioF/Farmacias/"+<%=id_usuario %>,
				async:false,
				success: function(response){
					$.each(response, function (i, item) {
						$('#filasFarmacias').append('<tr id="fila_'+item.id+'">'+
						'<td><a href="addEditFarmacia.jsp?id='+ item.id +'">'+item.nombre+'</a></td>'+ 
						'<td align="center">'+
                                '<a title="Editar" href="addEditFarmacia.jsp?id='+ item.id +'">'+
                                '<img src="./img/icono_editar.png" alt="Editar Farmacia" style="width:25px;"></a>'+
                            '</td>'+
                            '<td align="right">'+
                        '<button id="boton_'+item.id+'" class="btn btn-link btn-xs" title="Eliminar" onclick="if(!confirm(&quot;Â¿Desea realmente eliminar el registro?&quot;)){return false;};"><img src="./img/icono_eliminar.png" alt="Eliminar Farmacia" style="width:25px;"></button>'+
                        '</form>'+
                    '</td></tr>');
					});
					
					  
				},
				error: function() {
					 alert('fail');
				}
		});
   }
   function eliminaFarmacia(id){
	   $.ajax({
	       type: "DELETE",
	       url: "rest/servicioF/Farmacia/"+id,
	       success: function(response){
	       	alert("Eliminado");
	       	$('#fila_'+id).remove();
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