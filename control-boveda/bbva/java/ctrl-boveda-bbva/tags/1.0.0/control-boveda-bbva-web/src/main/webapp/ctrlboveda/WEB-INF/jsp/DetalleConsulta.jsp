<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1" --%>
<%-- 	pageEncoding="ISO-8859-1"%> --%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>[Detalle Consulta] - BBVA - BANCO CONTINENTAL</title>

<meta charset="utf-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.15/css/dataTables.bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.datatables.net/responsive/2.1.1/css/responsive.bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker.min.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css" />

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.10.15/js/dataTables.bootstrap.min.js"></script>
<script src="https://cdn.datatables.net/responsive/2.1.1/js/dataTables.responsive.min.js"></script>
<script src="https://cdn.datatables.net/responsive/2.1.1/js/responsive.bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.min.js"></script>

</head>
<style type="text/css">
fieldset.scheduler-border {
	border: 1px groove #fff !important;
	padding: 0.625em 0.625em 0.625em 0.625em !important;
	margin: 0 0 0 0 !important;
	-webkit-box-shadow: 0px 0px 0px 0px #000;
	box-shadow: 0px 0px 0px 0px #000;
}

legend.style {
	font-size: 14px;
	line-height: 1;
	color: #555;
	border: 1px solid #ccc;
	background-color: #eee;
	border-radius: 4px;
	padding: 6px 6px 12px 12px;
	width: 100%;
    margin-bottom: 7px;
"
}

span.estilo {
	width: 189px;
}

div.completar {
	width: 100%;
}
</style>
<body>
	<form>
		<div class="container">
			<div class="panel-group">
				<div class="panel panel-primary">
					<div class="panel-heading">CONSULTA DE MOVIMIENTO DEL DOCUMENTO</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-sm-6">
								<div class="input-group completar">
									<span class="input-group-addon estilo">CÓDIGO DE BANCO</span>
									<input type='text' class="form-control" id="txtOperador" disabled/>  
								</div>
								<br>
							</div>
							<div class="col-sm-6">
								<div class="input-group completar">
									<span class="input-group-addon estilo">CÓDIGO JULIANO</span>
									<input type='text' class="form-control" id="txtCodJuliano" disabled />  
								</div>
								<br>
							</div>
						</div>

						<div class="row">
							<div class="col-sm-6">
								<div class="input-group completar">
									<span class="input-group-addon estilo">CÓDIGO AGENCIA</span> 
									<input type='text' class="form-control" id="txtAgencia" disabled/>  
								</div>
								<br>
							</div>
							<div class="col-sm-6">
								<div class="input-group completar">
									<span class="input-group-addon estilo">AGENCIA</span> 
									<input type='text' class="form-control" id="txtDesAgencia" disabled/>  
								</div>
								<br>
							</div>
						</div>

						<div class="row">
							<div class="col-sm-6">
								<div class="input-group completar">
									<span class="input-group-addon estilo">CLASE DE DOCUMENTO</span> 
									<input type='text' class="form-control" id="txtClasDocu" disabled/>  
								</div>
								<br>
							</div>
							<div class="col-sm-6">
								<div class="input-group completar">
									<span class="input-group-addon estilo">TIPO DE SERVICIO</span> 
									<input type='text' class="form-control" id="txtServicio" disabled/>  
								</div>
								<br>
							</div>
						</div>
						
						<div class="row">
							<div class="col-sm-6">
								<div class="input-group completar">
									<span class="input-group-addon estilo">NOMBRE</span> 
									<input type='text' class="form-control" id="txtNombre" disabled/>  
								</div>
								<br>
							</div>
							<div class="col-sm-6">
								<div class="input-group completar">
									<span class="input-group-addon estilo">DIRECCIÓN</span> 
									<input type='text' class="form-control" id="txtDireccion" disabled/>  
								</div>
								<br>
							</div>
						</div>

						<div class="row">
							<div class="col-sm-6">
								<div class="input-group completar">
									<span class="input-group-addon estilo">ESTADO ACTUAL</span> 
									<input type='text' class="form-control" id="txtEstado" disabled/>  
								</div>
								<br>
							</div>
							<div class="col-sm-6">
								<br>
							</div>
						</div>

						<div class="row">
							<div class="col-sm-6"></div>
							<div class="col-sm-2"></div>
							<div class="col-sm-2">
								<input type="button" class="btn btn-primary" value="EXPORTAR" id="btnExportar" onclick="generarReporte()">
<!-- 								<a id="btnExportar" role="button" class="btn btn-primary"  -->
<!-- 									href="/ctrl-boveda-bbva/consulta.htm?accion=generarReporteMovimientos&juliano="juliano>EXPORTAR -->
<!-- 								</a> -->
							</div>
							<div class="col-sm-2">
								<a id="btnCerrar" role="button" class="btn btn-primary" 
									href="/ctrl-boveda-bbva/consulta.htm?accion=formConsultaEspecifica">RETORNAR
								</a>
<!-- 								<input type="button" class="btn btn-primary" value="RETORNAR" id="btnCerrar" onclick="retornar()"> -->
							</div>
						</div>
						<br>
						<div id="divdtConsulta" class="row">

							<table id="dtConsultaEspecifica" class="table  table-bordered dt-responsive " 
								cellspacing="0" width="100%">
								<thead>
									<tr>
<!-- 										<th >CÓDIGO DE PLANILLA</th> -->
										<th >CARTERA</th>
										<th >FEC. MOV.</th>
										<th >FEC. VENC.</th>
										<th >TIPO MOV.</th>
<!-- 										<th >INS. DE COBRO</th> -->
										<th >FORMA PAGO</th>
										<th >PLAZA COBRO</th>
										<th >NUEVA FEC. VENC.</th>
										<th >IMPORTE</th>
										<th >NUEVO IMPORTE</th>
										<th >OBS/UBIC</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
	<div id="modalMensaje" class="modal fade" data-backdrop="static"
            data-keyboard="false">
            <div class="modal-dialog">
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        Mensaje
                    </div>
                    <div class="panel-body">
                        <div class="text-center">
                            <div class="modal-body" id="txtmodalMensaje"></div>
                            <button id="btnModal" type="button" class="btn btn-default" data-dismiss="modal">Aceptar</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
	<script>
	
		var gDtConsultaEspecifica = ${listDetalle};	
		
		var operador = ${operador};	
		var juliano = ${juliano};	
		var agencia = ${agencia};	
		var claseDocu = ${claseDocu};	
		var nombre = ${nombre};	
		var direccion = ${direccion};	
		var estado = ${estado};
		var desAgencia = ${desAgencia};
		var servicio = ${servicio};
	
		$(document).ready(function() {
			mostrarConsulta();
			
			$("txtOperador").prop('disabled', true);
			$("txtCodJuliano").prop('disabled', true);
			$("txtAgencia").prop('disabled', true);
			$("txtDesAgencia").prop('disabled', true);
			$("txtNombre").prop('disabled', true);
			$("txtClasDocu").prop('disabled', true);
			$("txtDireccion").prop('disabled', true);
			$("txtEstado").prop('disabled', true);
			$("txtServicio").prop('disabled', true);
			
			$("#txtOperador").val(operador);
			$("#txtCodJuliano").val(juliano);
			$("#txtAgencia").val(agencia);
			$("#txtDesAgencia").val(desAgencia);
			$("#txtNombre").val(nombre);
			$("#txtClasDocu").val(claseDocu);
			$("#txtDireccion").val(direccion);
			$("#txtEstado").val(estado);
			$("#txtServicio").val(servicio);
			
		});

		function mostrarConsulta() {
		
            if (gDtConsultaEspecifica!=null ){

                    $('#dtConsultaEspecifica').dataTable({
                        "data": gDtConsultaEspecifica,
                        "searching" : false,
						"ordering" : false,
						"bLengthChange" : false, //used to hide the property
						"language" : {
							"sProcessing" : "Procesando...",
							"sLengthMenu" : "Mostrar _MENU_ registros",
							"sZeroRecords" : "No se encontraron resultados",
							"sEmptyTable" : "Ningún dato disponible en esta tabla",
							"sInfo" : "Mostrando _START_ de _END_ de _TOTAL_ registros",
							"sInfoEmpty" : "Mostrando 0 de 0 de 0 registros",
							"sInfoFiltered" : "(filtrado de un total de _MAX_ registros)",
							"sInfoPostFix" : "",
							"sSearch" : "Buscar:",
							"sUrl" : "",
							"sInfoThousands" : ",",
							"sLoadingRecords" : "Cargando...",
							"oPaginate" : {
								"sFirst" : "Primero",
								"sLast" : "Último",
								"sNext" : "Siguiente",
								"sPrevious" : "Anterior"
							},
							"oAria" : {
								"sSortAscending" : ": Activar para ordenar la columna de manera ascendente",
								"sSortDescending" : ": Activar para ordenar la columna de manera descendente"
							}
						},
                        "columns": [
//                                     {"data": "cod_planilla", "class":"center"}, 
                                    {"data": "cod_cartera", "class":"center"},
                                    {"data": "fec_movimiento", "class":"center"}, 
                                    {"data": "fec_vencimiento", "class":"center"}, 
                                    {"data": "cod_tipo_movimiento", "class":"center"},
//                                     {"data": "ind_instruccion_cobro", "class":"center"}, 
									{"data": "cod_forma_pago", "class":"center"}, 
                                    {"data": "cod_plaza_cobro", "class":"center"}, 
                                    {"data": "fec_nuevo_vencimiento", "class":"center"}, 
                                    {"data": "imp_importe", "class":"center"}, 
                                    {"data": "imp_nuevo_importe", "class":"center"}, 
                                    {"data": "cod_observacion_ubica", "class":"center"}
                                    ]
                    });
            }
        }
		
		function enviarMensaje( mensaje ) {
            document.getElementById("txtmodalMensaje").innerHTML = mensaje;
            $("#modalMensaje").modal('show');
        }
		
		function generarReporte() {
			var codJuliano = juliano;
// 			$.ajax({
// 					url : '${pageContext.request.contextPath}/consulta.htm?accion=generarReporteMovimientos',
// 					data : {
//  							"juliano" : codJuliano
// 					},
// 					cache : false,
// 					async : true,
// 					type : 'GET',
// 					dataType : 'json',
// 					success : function(data) {
// 						var rpta = data.dataJSON;

// 					},
// 					error : function(err) {
// 						console.log(err);
// 					}
// 				});
			var url = "${pageContext.request.contextPath}/consulta.htm?accion=generarReporteMovimientos&juliano=" + codJuliano; 
			document.location.target = "_blank";
            document.location.href = url;
		}
			
	</script>
</body>
</html>