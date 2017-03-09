<%@ page contentType="text/html" language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle Navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="inicio.jsp"><img src="./img/logo.png" style="height: 50px; margin-top: -15px;"></a>
			</div>

			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
											<li><a href="login.jsp">Administracion FarmaSearch</a></li>
										
				</ul>

				<ul class="nav navbar-nav navbar-right">
											<li><a href="login.jsp">Iniciar Sesión</a></li>
									</ul>
			</div>
		</div>
	</nav>
<div class="container">
	
  <div class="row">
     
  <div class="panel panel-default">
                  <div class="panel-heading">Registrarse</div>
                   
      <div class="panel-body">
                            <form method="POST" action="rest/servicioU/Usuario" accept-charset="UTF-8" autocomplete="on" class="form-horizontal" role="form">
                        
						<input type="hidden" name="tipo" value="1">

                        <div class="form-group">
                            <label class="col-md-4 control-label">Nombre</label>
                            <div class="col-md-6">
                                <input type="text" class="form-control" name="nombre" value="">
                            </div>
                        </div>
                        
                         <div class="form-group">
                            <label class="col-md-4 control-label">Apellidos</label>
                            <div class="col-md-6">
                                <input type="text" class="form-control" name="apellidos" value="">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label">E-Mail</label>
                            <div class="col-md-6">
                                <input type="email" class="form-control" name="email" value="">
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


	
	<!-- Scripts -->
	<script src="./javascript/jquery.min.js"></script>
	<script src="./bootstrap/js/bootstrap.min.js"></script>


</body></html>