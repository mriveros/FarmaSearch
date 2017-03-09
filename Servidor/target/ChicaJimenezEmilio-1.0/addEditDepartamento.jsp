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
<!-- saved from url=(0042)http://admin.guiapueblo.es/noticias/create -->
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
	<script src="./js/script.js"></script>

	
	

	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
		<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	<![endif]-->

</head>
<body>
	<%@include file="include/navegacion.jsp" %>

	  <div class="container">
  <div class="row">
     
  <div class="panel panel-default">
          <div class="panel-heading">Crear Departamento</div>
                     
      <div class="panel-body">
            <form method="POST" action="ServletDepartamento" accept-charset="UTF-8" autocomplete="on" class="form-horizontal" role="form" enctype="multipart/form-data" >
             <input id="id_usuario" name="id_usuario" value="<%=id_usuario %>" type="hidden">             
            <input name="id" type="hidden" id="id_departamento">
            <div class="form-group form-group-sm">
                <label for="id_farmacia" class="control-label col-md-2">Farmacia:</label>
                <div class="col-md-4 ">
                      <select id="id_farmacia" name="id_farmacia" size="6" multiple  ></select>
                 </div>
            </div>
            <div class="form-group form-group-sm">
                <label for="nombre" class="control-label col-md-2">Nombre:</label>
                <div class="col-md-8 ">
                  <input class="form-control" name="nombre" type="text" id="nombre">
                </div>
            </div>
            <div class="form-group form-group-sm">
                <label for="descripcion" class="control-label col-md-2">Descripción:</label>
                <div class="col-md-8 ">
                  <textarea class="form-control" style="height: 150px; resize:none;" name="descripcion" cols="50" rows="10" id="descripcion"></textarea>
                </div>
            </div>
			            <div class="form-group form-group-sm">            
                <label for="imagen" class="control-label col-md-2">Imagen:</label>
                <div class="col-md-3">
                  <span class="file-input file-input-new"><div class="file-preview ">
    <div class="close fileinput-remove">x</div>
    <div class="">
    <div class="file-preview-thumbnails">
    </div>
    <div class="clearfix"></div>    <div class="file-preview-status text-center text-success"></div>
    <div class="kv-fileinput-error file-error-message" style="display: none;"></div>
    </div>
</div>
<div class="kv-upload-progress hide"></div>
<div class="input-group ">
   <div tabindex="-1" class="form-control file-caption  kv-fileinput-caption">
   <span class="file-caption-ellipsis">x</span>
   <div class="file-caption-name"></div>
</div>
   <div class="input-group-btn">
       <button type="button" title="Eliminar SelecciÃ³n" class="btn btn-default fileinput-remove fileinput-remove-button btn-sm"><i class="glyphicon glyphicon-trash"></i> Eliminar</button>
       <button type="button" title="Abort ongoing upload" class="hide btn btn-default fileinput-cancel fileinput-cancel-button"><i class="glyphicon glyphicon-ban-circle"></i> Cancelar</button>
       <div class="btn btn-default btn-file btn-sm"> <i class="glyphicon glyphicon-folder-open"></i> &nbsp;Buscar... <input id="imagen" class="file" type="file" name="imagen" multiple="true"></div>
   </div>
</div></span>   
                </div>
              
            </div>
            <img id="imagen_cargada" class="col-md-6">
           <div class="text-right">
              
                                <input class="btn btn-default btn-sm" type="submit" value="AÑADIR/EDITAR">
                
                <a class="btn btn-default btn-sm" href="javascript:window.history.back();">CANCELAR</a>
  		</div>
        </form></div>
  </div>
     

  
</div>
</div>
<br>

<script type="text/javascript">

$( document ).ready(function() {
	cargaFarmaciasSelect();
	var id = urlParam('id');
	if(id!=null)
		cargaDatos(id);
});  
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
   function urlParam(name){
	    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
	    if (results==null){
	       return null;
	    }
	    else{
	       return results[1] || 0;
	    }
	}
   function cargaDatos(id){
	   $.ajax({
				type: "GET",
				url: "rest/servicioD/Departamento/"+id,
				success: function(response){
							
					    $('#id_departamento').val(response.id);
					    $('#nombre').val(response.nombre);
					    $('#descripcion').val(response.descripcion);
					    $('#imagen_cargada').attr("src","http://localhost:8080/ChicaJimenezEmilio/imgDepartamentos/"+response.imagen);
					    $.each(response.farmacias, function(i,e){
					        $("#id_farmacia option[value='" + e.id + "']").prop("selected", true);
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