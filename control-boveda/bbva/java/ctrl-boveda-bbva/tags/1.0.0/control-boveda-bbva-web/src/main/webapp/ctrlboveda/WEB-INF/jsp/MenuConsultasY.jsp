<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1" --%>
<%-- 	pageEncoding="ISO-8859-1"%> --%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<title>[Menu Principal] - BBVA - BANCO CONTINENTAL</title>

<meta charset="utf-8">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker.min.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css" />

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.min.js"></script>

</head>
<style type="text/css">
.dropdown-submenu {
	position: relative;
}

.dropdown-submenu>.dropdown-menu {
	top: 0;
	left: 100%;
	margin-top: -6px;
	margin-left: -1px;
	-webkit-border-radius: 0 6px 6px 6px;
	-moz-border-radius: 0 6px 6px;
	border-radius: 0 6px 6px 6px;
}

.dropdown-submenu:hover>.dropdown-menu {
	display: block;
}

.dropdown-submenu>a:after {
	display: block;
	content: " ";
	float: right;
	width: 0;
	height: 0;
	border-color: transparent;
	border-style: solid;
	border-width: 5px 0 5px 5px;
	border-left-color: #ccc;
	margin-top: 5px;
	margin-right: -10px;
}

.dropdown-submenu:hover>a:after {
	border-left-color: #fff;
}

.dropdown-submenu.pull-left {
	float: none;
}

.dropdown-submenu.pull-left>.dropdown-menu {
	left: -100%;
	margin-left: 10px;
	-webkit-border-radius: 6px 0 6px 6px;
	-moz-border-radius: 6px 0 6px 6px;
	border-radius: 6px 0 6px 6px;
}
</style>
<body>
	<form id="frmMenu" name="frmMenu">
		<div class="container">
			<div class="row">
				<h2>Menu Principal - BBVA CONTINENTAL</h2>
				<hr>
				<div class="dropdown">
					<a id="dLabel" role="button" data-toggle="dropdown"
						class="btn btn-primary"> Consultas <span class="caret"></span>
					</a>
					<ul class="dropdown-menu multi-level" role="menu"
						aria-labelledby="dropdownMenu">
						<li><a  href="/ctrl-boveda-bbva/consulta.htm?accion=formConsultaGeneral">
						Consulta general de documentos</a></li>
						<li><a href="/ctrl-boveda-bbva/consulta.htm?accion=formConsultaEspecifica">
						Consulta detallada de documentos</a></li>
						
						
						<!-- BORRAR DESPUES DE CREAR EL SCHEDULER -->
						<li class="divider"></li>
			              <li class="dropdown-submenu">
			                <a tabindex="-1" href="#">Ejecución Manual de Procesos</a>
			                <ul class="dropdown-menu">
			                  <li><a href="/ctrl-boveda-bbva/consulta.htm?accion=procesarCarga">Carga Inicial</a></li>
			                  <li><a href="/ctrl-boveda-bbva/consulta.htm?accion=procesarIngresos">Procesar Ingresos</a></li>
			                  <li><a href="/ctrl-boveda-bbva/consulta.htm?accion=procesarSalidas">Procesar Salidas</a></li>
			                  <li><a href="/ctrl-boveda-bbva/consulta.htm?accion=procesarRenovados">Procesar Renovaciones</a></li>
			                  <li><a href="/ctrl-boveda-bbva/consulta.htm?accion=procesarProtestados">Procesar Protestados</a></li>
			                </ul>
			              </li>
						<!-- BORRAR DESPUES DE CREAR EL SCHEDULER -->
						
					</ul>
				</div>
				
				
				<div class="dropdown">
					<a id="dLabel" role="button" data-toggle="dropdown"
						class="btn btn-primary"> Reportes <span class="caret"></span>
					</a>
					<ul class="dropdown-menu multi-level" role="menu" aria-labelledby="dropdownMenu">
						<li><a  href="/ctrl-boveda-bbva/consulta.htm?accion=resumenDocumentos&tipo=1">
						Resumen documentos ingresados</a></li>
						<li><a href="/ctrl-boveda-bbva/consulta.htm?accion=resumenDocumentos&tipo=2">
						Resumen documentos cancelados</a></li>
						<li><a  href="/ctrl-boveda-bbva/consulta.htm?accion=resumenDocumentos&tipo=3">
						Resumen documentos vigentes sin fisico</a></li>
						<li><a href="/ctrl-boveda-bbva/consulta.htm?accion=resumenDocumentos&tipo=4">
						Total documentos en custodia</a></li>
					</ul>
				</div>
				
			</div>
		</div>
	</form>
	<script>
		function irGeneral() {
			var url = "${pageContext.request.contextPath}/consulta.htm?accion=formConsultaGeneral"; 
			windows.location.href = url;
		}

		function irEspecifica() {
			var url = "${pageContext.request.contextPath}/consulta.htm?accion=formConsultaEspecifica"; 
			windows.location.href = url;
		}
	</script>
</body>
</html>