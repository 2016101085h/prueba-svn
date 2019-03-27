<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1" --%>
<%-- 	pageEncoding="ISO-8859-1"%> --%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>[Módulo de Consultas] - BBVA - BANCO CONTINENTAL</title>

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
					<div class="panel-heading">CONSULTA DE DOCUMENTOS EN BOVEDA</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-sm-6">
								<div class="input-group completar">
									<span class="input-group-addon estilo">CÓDIGO BANCO</span> 
									<select class="form-control form-control-sm" id="cmbOperador">
										<option value="00" >-- TODOS</option>
										<option value="11" selected>11 BBVA - BANCO CONTINENTAL</option>
									</select>
								</div>
								<br>
							</div>
							<div class="col-sm-6">
								<div class="input-group completar">
									<span class="input-group-addon estilo">FISICO</span> 
									<select class="form-control form-control-sm" id="cmbEstado">
										<option value="00" selected>-- TODOS</option>
										<option value="1" >01 SI</option>
										<option value="0" >02 NO</option>
									</select>
								</div>
								<br>
							</div>
						</div>
						
						
						<div class="row">
							<div class="col-sm-6">
								<div class="input-group completar">
									<span class="input-group-addon estilo">CÓDIGO PLANILLA</span> 
									<input class="form-control" type="text" id="codPlanilla">
								</div>
								<br>
							</div>
							<div class="col-sm-6">
								<div class="input-group completar">
									<span class="input-group-addon estilo">CÓDIGO PLAZA</span> 
									<input class="form-control" type="text" id="codPlaza">
								</div>
								<br>
							</div>
						</div>
						
						
						<div class="row">
							<div class="col-sm-6">
								<div class="input-group completar">
									<span class="input-group-addon estilo">CÓDIGO CARTERA</span> 
									<input class="form-control" type="text" id="codCartera">
								</div>
								<br>
							</div>
							<div class="col-sm-6">
								<div class="input-group completar">
									<span class="input-group-addon estilo">SERVICIO</span> 
									<select class="form-control form-control-sm" id="cmbServicio">
										<option value="00" selected>-- TODOS</option>
										<option value="01">01 COBRANZA LIBRE</option>
										<option value="04">04 COB GARANTIA - LEY DESCONT</option>
										<option value="05">05 COB GARANTIA - FACTURA</option>
										<option value="07">07 COB GARANTIA - PAGARE</option>
										<option value="08">08 COB GARANTIA - CRED. DOC</option>
										<option value="11">11 COBRANZA LETRA HIPOTECARIAS</option>
										<option value="12">12 COB GARANTIA - NE</option>
										<option value="14">14 COB GARANTIA - AVAL</option>
										<option value="20">20 COB GARANTIA - FIANZAS</option>
										<option value="29">29 CUENTA ESPECIAL PRESTAMOS</option>
										<option value="30">30 FACTORING ESPECIAL PRESTAMOS</option>
										<option value="50">50 DESCUENTOS</option>
										<option value="55">55 DESCUENTOS POR DESEMBOLSAR</option>
										<option value="60">60 FACTURAS NEGOCIABLES</option>
									</select>
								</div>
								<br>
							</div>
						</div>
						
						<div class="row">
							<div class="col-sm-6">
								<div class="input-group completar">
									<span class="input-group-addon estilo">TIPO MOVIMIENTO</span> 
									<select	class="form-control form-control-sm" id="cmbTipoMov">
										<option value="00" selected>-- TODOS</option>
										<option value="01">01 CANCELACIÓN</option>
										<option value="02">02 RENOVACIÓN</option>
										<option value="03">03 DEVUELTA</option>
										<option value="04">04 DEVUELTA PROTESTADA</option>
										<option value="05">05 DESCARGO POR ERROR</option>
										<option value="06">06 DEVUELTA POR RASTREO</option>
										<option value="07">07 CANCELACION POR RASTREO</option>
										<option value="08">08 RENOVACIÓN POR RASTREO</option>
										<option value="11">11 INGRESOS POR CANJE</option>
										<option value="13">13 INGRESOS POR ERROR</option>
										<option value="41">41 CAMBIOS A LA PENDIENTE</option>
										<option value="43">43 TRANSFERENCIA MANUAL</option>
										<option value="46">46 TRANSFERENCIA AUTOMÁTICA</option>
										<option value="49">49 MARCA PROTESTO</option>
									</select>
								</div>
								<br>
							</div>
							<div class="col-sm-6">
								<div class="input-group completar">
<!-- 									<span class="input-group-addon estilo">FORMA PAGO</span>  -->
<!-- 									<select	class="form-control form-control-sm" id="cmbFormaPago"> -->
<!-- 										<option value="00" selected>-- TODOS</option> -->
<!-- 									</select> -->
									<span class="input-group-addon estilo">CÓDIGO JULIANO</span> 
									<input class="form-control" type="text" id="codJuliDesde"> 
								</div>
								<br>
							</div>
						</div>
						
						<div class="row">
							<div class="col-sm-6">
								<div class="input-group completar">
									<span class="input-group-addon estilo">AGENCIA</span> 
									<select class="form-control form-control-sm" id="cmbAgencia">
										<option value="00" selected>-- TODOS</option>
<!-- 										<option value="50">050 BANCA EMPRESAS CALLAO</option> -->
<!-- 										<option value="002">002 PANDO</option> -->
<!-- 										<option value="003">003 MCDO MAYORISTAS SANTA ANITA</option> -->
									</select>
								</div>
								<br>
							</div>
							<div class="col-sm-6">
								<div class="input-group completar">
									<span class="input-group-addon estilo">INSTRUCCION COBRO</span> 
									<select class="form-control form-control-sm" id="cmbInstCobro">
										<option value="00" selected>-- TODOS</option>
										<option value="01">01 PROTESTAR POR FALTA DE PAGO</option>
										<option value="02">02 NO PROTESTAR</option>
										<option value="03">03 PROTESTAR POR FALTA DE ACEPTACION Y PAGO</option>
										<option value="04">04 RETENIDO</option>
									</select>
								</div>
								<br>
							</div>
						</div>

						<div class="row">
							<div class="col-sm-6">
								<div class="input-group completar">
									<span class="input-group-addon estilo">TIPO MONEDA</span> 
									<select	class="form-control form-control-sm" id="cmbTipoMoneda">
										<option value="00" selected>-- TODOS</option>
										<option value="1">1 SOLES</option>
										<option value="2">2 DÓLARES</option>
									</select>
								</div>
								<br>
							</div>
							<div class="col-sm-6">
								<div class="input-group input-append date" >
									<span class="input-group-addon estilo">AÑO VENCIMIENTO</span>
					                <input type="text" class="form-control" id="fecAnhioVenc" name="fecAnhioVenc" placeholder="Formato(yyyy)" />
					                <span class="input-group-addon add-on">
					                	<span class="glyphicon glyphicon-calendar"></span>
					                </span>
					            </div>
								<br>
							</div>
						</div>
						
						<div class="row">
							<div class="col-sm-6">
								<div class="input-group completar">
									<span class="input-group-addon estilo">TIPO DOCUMENTO</span> 
									<select	class="form-control form-control-sm" id="cmbTipoDocumento">
										<option value="00" selected>-- TODOS</option>
										<option value="01">01 LETRA POR ACEPTAR</option>
										<option value="02">02 LETRA ACEPTADA</option>
										<option value="03">03 FACTURA COMERCIAL FÍSICA</option>
										<option value="04">04 FACTURA NEGOCIABLE FÍSICA</option>
										<option value="14">14 FACTURA NEGOCIABLE ELECTRÓNICA</option>
									</select>
								</div>
								<br>
							</div>
							<div class="col-sm-6">
								<div class="input-group completar">
									<span class="input-group-addon estilo">RESUMEN DE DOCUMENTOS</span> 
									<select	class="form-control form-control-sm" id="cmbResumen">
										<option value="00" selected>-- SELECCIONE</option>
										<option value="0">0 DOCUMENTOS CANCELADOS</option>
										<option value="1">1 DOCUMENTOS INGRESADOS</option>
										<option value="2">2 DOCUMENTOS VIGENTES SIN FÍSICO</option>
									</select>
								</div>
								<br>
							</div>
						</div>
						
						<div class="row">
							<div class="col-sm-6">
								<div class="input-group completar">
									<span class="input-group-addon estilo">FORMA PAGO</span> 
									<select	class="form-control form-control-sm" id="cmbFormaPago">
										<option value="00" selected>-- TODOS</option>
										<option value="01" >01 EFECTIVO</option>
										<option value="02" >02 CHEQUE PROPIO BANCO</option>
										<option value="03" >03 CHEQUE OTRO BANCO</option>
										<option value="04" >04 CARGO EN CUENTA</option>
										<option value="05" >05 CHEQUE P/B Y EFECTIVO</option>
										<option value="06" >06 CHEQUE O/B EFECTIVO</option>
										<option value="07" >07 CARGO EN CUENTA Y EFECTIVO</option>
										<option value="08" >08 REFINANCIACION</option>
										<option value="09" >09 TRANSACCION</option>
									</select>
								</div>
								<br>
							</div>
						</div>
						
						<div class="row">
							<div class="col-sm-12">
								<div class="input-group input-append date" >
									<span class="input-group-addon estilo">FECHA MOVIMIENTO</span>
					                <input type="text" class="form-control" id="fecProcDesde" name="fecProcDesde" placeholder="Desde (dd/mm/yyyy)" />
					                <span class="input-group-addon add-on">
					                	<span class="glyphicon glyphicon-calendar">
					                	</span>
					                </span>
					                <span class="input-group-addon">-</span>
					                <input type="text" class="form-control" id="fecProcHasta" name="fecProcHasta" placeholder="Hasta (dd/mm/yyyy)" />
					                <span class="input-group-addon add-on">
					                	<span class="glyphicon glyphicon-calendar">
					                	</span>
					                </span>
					            </div>
								<br>
							</div>
						</div>
						
<!-- 						<div class="row"> -->
<!-- 							<div class="col-sm-12"> -->
<!-- 								<div class="input-group completar"> -->
<!-- 									<span class="input-group-addon estilo">CÓDIGO JULIANO</span>  -->
<!-- 									<input class="form-control" type="text" id="codJuliDesde" -->
<!-- 										placeholder="Desde"> <span class="input-group-addon">-</span> -->
<!-- 									<input class="form-control" type="text" id="codJuliHasta" -->
<!-- 										placeholder="Hasta"> -->
<!-- 								</div> -->
<!-- 								<br> -->
<!-- 							</div> -->
<!-- 						</div> -->
						
						<div class="row">
							<div class="col-sm-12">
								<div class="input-group completar">
									<span class="input-group-addon estilo">NÚMERO LETRA</span> 
									<input class="form-control" type="text" id="numLetraDesde"
										placeholder="Desde"> <span class="input-group-addon">-</span>
									<input class="form-control" type="text" id="numLetraHasta"
										placeholder="Hasta">
								</div>
								<br>
							</div>
						</div>
						

						<div class="row">
							<div class="col-sm-6"></div>
							<div class="col-sm-2">
								<input type="button" class="btn btn-primary" id="btnConsultar"
									value="CONSULTAR" onClick="consultar()">
							</div>
							<div class="col-sm-2">
								<input type="button" class="btn btn-primary" value="EXPORTAR" onclick="generarReporteMasivo()">
							</div>
							<div class="col-sm-2">
<!-- 								<input type="button" class="btn btn-primary" value="CERRAR"> -->
								<a id="btnCerrar" role="button" class="btn btn-primary" 
									href="/ctrl-boveda-bbva/consulta.htm?accion=mostrarFormConsulta">CERRAR
								</a>
							</div>
						</div>
						<br>
						<div id="divdtConsulta" class="row">

							<table id="dtConsultaEspecifica" class="table  table-bordered dt-responsive " 
								cellspacing="0" width="100%">
								<thead>
									<tr>
										<th >JULIANO</th>
										<th >PLANILLA</th>
										<th >COD PLAZA</th>
										<th >COD AGENCIA</th>
										<th >SERV.</th>
										<th >INSTRUC. COBRO</th>
										<th >FEC. VENC.</th>
										<th >NOMBRE ACEPTANTE</th>
										<th >MONEDA</th>
										<th >IMPORTE</th>
										
										<th >FEC. MOV.</th>
										<th >TIP. MOV.</th>
										<th >FORMA PAGO</th>
										<th >NUEVO IMP</th>
										<th >NUEVA FEC VENC</th>
										<th >FISICO</th>
										<th >DETALLE</th>
									</tr>
								</thead>
							</table>
						</div>
						<br>
						<div class="row">
							<div class="col-sm-6"></div>
							<div class="col-sm-6">
								<div class="input-group">
									<span class="input-group-addon">Total S/.</span><input class="form-control" type="text" id="txtSoles"> 
									<span class="input-group-addon">Total $</span><input class="form-control" type="text" id="txtDolares">
								</div>
							</div>
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
	
	var gDtConsultaEspecifica = ${listaDocumentos};	
	var soles = ${soles};	
	var dolares = ${dolares};
	var listAgencias = ${listaAgencias};
	
		$(document).ready(function() {
			
			$('#fecProcDesde')
	        .datepicker({
	            format: 'dd/mm/yyyy',
	            autoclose: true,
	            startDate: '01/01/2000',
	            endDate: '12/30/2040'
	        });
			
			$('#fecProcHasta')
	        .datepicker({
	            format: 'dd/mm/yyyy',
	            autoclose: true,
	            startDate: '01/01/2000',
	            endDate: '12/30/2040'
	        });
			
			$('#fecAnhioVenc')
	        .datepicker({
	            format: 'yyyy',
	            autoclose: true,
	            viewMode: 'years', 
	            minViewMode: 'years'
	        });
			
			$.each(listAgencias, function(i, value) {
	            $('#cmbAgencia').append($('<option>').text(value.codAgencia + ' - '+value.desAgencia).attr('value', value.codAgencia));
	        });

			
	        mostrarConsulta(0);
	        
	        $('#txtSoles').val(soles);
            $('#txtDolares').val(dolares);
            
		});
		
		function mostrarConsulta(cantReg) {
		
            if (gDtConsultaEspecifica!=null || gDtConsultaEspecifica.size()>0){
            	console.log("gdtConsulta: " + gDtConsultaEspecifica.length);
                    $('#dtConsultaEspecifica').dataTable({
                        "data": gDtConsultaEspecifica,
                        "searching" : false,
						"ordering" : false,
						"bLengthChange" : false,
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
						"infoCallback": function(settings, start, end, max, total, pre) {
							   return 'Mostrando '+start+' de '+ end + ' de ' + cantReg + ' registros';
							},
                        "columns": [{"data": "cod_juliano", "class":"center"}, 
                                    {"data": "cod_planilla", "class":"center"},
                                    {"data": "cod_plaza", "class":"center"}, 
                                    {"data": "cod_agencia", "class":"center"}, 
                                    {"data": "cod_servicio", "class":"center"}, 
                                    {"data": "ind_instruccion_cobro", "class":"center"}, 
                                    {"data": "fec_vencimiento", "class":"center"}, 
                                    {"data": "des_nombre", "class":"center"}, 
                                    {"data": "cod_tipo_moneda", "class":"center"}, 
                                    {"data": "imp_importe", "class":"center"},
                                    
                                    {"data": "fec_movimiento", "class":"center"},
                                    {"data": "cod_tipo_movimiento", "class":"center"}, 
                                    {"data": "cod_forma_pago", "class":"center"},
                                    {"data": "imp_nuevo_importe", "class":"center"},
                                    {"data": "fec_nuevo_vencimiento", "class":"center"},
                                    {"data": "ind_ingreso_salida", "class":"center"}, 
                                    {"data": "ind_ingreso_salida", "class":"center"}],
	                     "columnDefs" : [ {
	                     "targets" : 16,
	                     "render" : function(data, type, row) {
	                         if (row != null && typeof row != 'undefined') {
	                        	 	return '<input type="button" class="btn btn-primary" id="btnDetalle" value="Ver Movimientos" onClick="verDetalle('+row.cod_juliano+')">';
	                         		}
	                     		}
	                 		}
	                 	]
                    });
            }
        }
		
		function enviarMensaje( mensaje ) {
            document.getElementById("txtmodalMensaje").innerHTML = mensaje;
            $("#modalMensaje").modal('show');
        }
		
		function compararfechas(fecha1, fecha2){
            if(!checkfecha(fecha1)) return -1;
            if(!checkfecha(fecha2)) return -2;
            dia = fecha1.substring(0,2)
            mes = fecha1.substring(3,5)
            anho = fecha1.substring(6,10)
            fecha1x = anho + mes + dia
            dia = fecha2.substring(0,2)
            mes = fecha2.substring(3,5)
            anho = fecha2.substring(6,10)
            fecha2x = anho + mes + dia
            return (fecha1x>fecha2x?1:(fecha1x<fecha2x?2:0));
        }
		
		function checkfecha(fecha){
            var err=0
            if ( fecha.length != 10) err=1
            dia = fecha.substring(0,2)
            slash1 = fecha.substring(2,3)
            mes = fecha.substring(3,5)
            slash2 = fecha.substring(5,6)
            anho = fecha.substring(6,10)
            if ( dia<1 || dia>31) err = 1
            if ( slash1 != '/' ) err = 1
            if ( mes<1 || mes>12) err = 1
            if ( slash1 == '/' && slash2 != '/' ) err = 1
            if ( anho < 1900 || anho > 2200 ) err = 1
            if ( mes == 4 || mes == 6 || mes == 9 || mes == 11 ){
              if (dia==31) err=1
            }
            if (mes == 2){
              var g = parseInt(anho/4)
              if (isNaN(g)){
                err = 1
              }
              if (dia >29) err =1
              if (dia ==29 && ((anho/4)!=parseInt(anho/4))) err=1
            }
            return (!(err==1));
        }
		
		function comparaRango(fecha1, fecha2){
			var formFec1 = fecha1.substring(3,5)+"/"+fecha1.substring(0,2)+"/"+fecha1.substring(6,10);
			var formFec2 = fecha2.substring(3,5)+"/"+fecha2.substring(0,2)+"/"+fecha2.substring(6,10);
			var date1 = new Date(formFec1);
			var date2 = new Date(formFec1);
			date1.setMonth(date1.getMonth() + 3);
			if(date1>date2){
				return true;
			}else{
				return false;
			}
		}
		
		function posicionarseOperador(){
            $('#cmbOperador').focus()
        }
		
		function posicionarseFechaInicial(){
            $('#fecProcDesde').focus()
        }
		
		function posicionarseFechaFinal(){
            $('#fecProcHasta').focus()
        }
		
		function validarCampos(){
			var valOperador  = $('#cmbOperador').val();
			var valFecProcIni  = $('#fecProcDesde').val();
			var valFecProcFin  = $('#fecProcHasta').val();
			var valAgencia  = $('#cmbAgencia').val();
			var valJuliIni  = $('#codJuliDesde').val();
			var valJuliFin  = $('#codJuliHasta').val();
			var valClaseDoc  = $('#cmbClaseDoc').val();
			var valLetraIni  = $('#numLetraDesde').val();
			var valLetraFin  = $('#numLetraHasta').val();
			var valTipoMov  = $('#cmbTipoMov').val();
			var valTipoMone  = $('#cmbTipoMoneda').val();
			
			//validar Operador
			if(valOperador=="00"){
				$('#btnModal').attr("onclick", "posicionarseOperador()");
				enviarMensaje('Debe seleccionar al menos un Operador.');
				return false;
			}
			
			//validar fecha de proceso
			if(valFecProcIni!="" && valFecProcFin==""){
				$('#btnModal').attr("onclick", "posicionarseFechaFinal()");
				enviarMensaje('Debe ingresar una Fecha de Proceso Final.');
				return false;
			}
			
			if(valFecProcIni=="" && valFecProcFin!=""){
				$('#btnModal').attr("onclick", "posicionarseFechaInicial()");
				enviarMensaje('Debe ingresar una Fecha de Proceso Inicial.');
				return false;
			}
			
			if(valFecProcIni!="" && valFecProcFin!=""){
				var resultado = compararfechas(valFecProcIni, valFecProcFin)

				if (resultado == -1) {
					$('#btnModal').attr("onclick", "posicionarseFechaInicial()");
					enviarMensaje('Favor ingresar fecha inicial correctamente.');
					return false;
				} else if (resultado == -2) {
					$('#btnModal').attr("onclick", "posicionarseFechaFinal()");
					enviarMensaje('Favor ingresar fecha final correctamente.');
					return false;
				} else if (resultado == 1) {
					$('#btnModal').attr("onclick", "posicionarseFechaFinal()");
					enviarMensaje('La fecha inicial debe ser menor a la fecha final.');
					return false;
				}
				
				//validar rango no mayor a 3 meses
// 				if(comparaRango(valFecProcIni, valFecProcFin)){
// 					$('#btnModal').attr("onclick", "posicionarseFechaFinal()");
// 					enviarMensaje('El rango máximo de fechas para la consulta es de 3 meses.');
// 					return false;
// 				}
			}
			return true;
		}
		
		function consultar() {
			if(validarCampos()){
				waitingDialog.show("Cargando ...");
				var valOperador  = $('#cmbOperador').val();
				var valFecProcIni  = $('#fecProcDesde').val();
				var valFecProcFin  = $('#fecProcHasta').val();
				var valAgencia  = $('#cmbAgencia').val();
				var valJuliIni  = $('#codJuliDesde').val();
				var valJuliFin  = $('#codJuliHasta').val();
				var valLetraIni  = $('#numLetraDesde').val();
				var valLetraFin  = $('#numLetraHasta').val();
				var valTipoMov  = $('#cmbTipoMov').val();
				var valTipoMone  = $('#cmbTipoMoneda').val();
				
				var valEstado  = $('#cmbEstado').val();
				var valPlanilla  = $('#codPlanilla').val();
				var valPlaza  = $('#codPlaza').val();
				var valCartera  = $('#codCartera').val();
				var valServicio  = $('#cmbServicio').val();
				var valFormaPago  = $('#cmbFormaPago').val();
				var valInsCobro  = $('#cmbInstCobro').val();
				var valAnhioVenc  = $('#fecAnhioVenc').val();
				var valTipoDocu = $('#cmbTipoDocumento').val();
				var valResumen = $('#cmbResumen').val();
				$.ajax({
						url : '${pageContext.request.contextPath}/consulta.htm?accion=consultarForm',
						data : {
	 							"valOperador" : valOperador,
	 							"valFecProcIni" : valFecProcIni,
	 							"valFecProcFin" : valFecProcFin,
	 							"valAgencia" : valAgencia,
	 							"valJuliIni" : valJuliIni,
	 							"valJuliFin" : valJuliFin,
	 							"valLetraIni" : valLetraIni,
	 							"valLetraFin" : valLetraFin,
	 							"valTipoMov" : valTipoMov,
	 							"valTipoMone" : valTipoMone,
	 							
	 							"valEstado"  	 :	valEstado,
								"valPlanilla"  :	valPlanilla,
								"valPlaza"  	 :	valPlaza,
								"valCartera"   :	valCartera,
								"valServicio"  :	valServicio,
								"valFormaPago" : 	valFormaPago,
								"valInsCobro"  :	valInsCobro,
								"valAnhioVenc"  :	valAnhioVenc,
								"valTipoDocu" 	:	valTipoDocu,
								"valResumen"	:	valResumen
			 							
						},
						cache : false,
						async : true,
						type : 'GET',
						dataType : 'json',
						success : function(data) {
                            if ( data.listaDocumentos != null ) {
                            	
                            		var listaDocumentos = JSON.parse(data.listaDocumentos);
                                    gDtConsultaEspecifica = listaDocumentos;
                                    var cantidad = data.resultado.cantidad;
                                    var table = $('#dtConsultaEspecifica').DataTable();
                                    table.destroy();
                                    console.log("destroy datatable");
                                    table.clear();
                                    console.log("clear datatable");
                                    table.draw();
                                    console.log("draw datatable");
                                    mostrarConsulta(cantidad);
                                    console.log("fin imprimir lineas");
                                    table = null;
                                    listaDocumentos = null;
                                    $('#txtSoles').val(data.resultado.soles);
                                    $('#txtDolares').val(data.resultado.dolares);
                                    
								limpiarCache();
                                waitingDialog.hide();
                            }
						},
						error : function(err) {
							console.log(err);
							waitingDialog.hide();
						}
					});
				}
			}
		
		function verDetalle(id){
			console.log("id:" + id);
			
			$.ajax({
				url : '${pageContext.request.contextPath}/consulta.htm?accion=verDetalle',
				data : {
							"cod_juliano" : id
				},
				cache : false,
				async : true,
				type : 'GET',
				dataType : 'json',
				success : function(data) {
					var rpta = data.dataJSON;
                    if ( rpta != null ) {
						var url = "/ctrl-boveda-bbva/consulta.htm?accion=mostrarListaDetalle";
                        console.log(url);
                        location.href=url;
                    }
				},
				error : function(err) {
					console.log(err);
				}
			});
		}
		
		function limpiarCache(){
			$.ajax({
				url : '${pageContext.request.contextPath}/consulta.htm?accion=limpiarCache',
				cache : false,
				async : true,
				type : 'GET',
				dataType : 'json',
				success : function(data) {
					var rpta = data.dataJSON;
                    console.log("Se limpió los datos")
				},
				error : function(err) {
					console.log(err);
				}
			});
		}
		
		function generarReporteMasivo(){
			var url = "${pageContext.request.contextPath}/consulta.htm?accion=generarReporteMasivo"; 
			document.location.target = "_blank";
            document.location.href = url;
		}
		
		var waitingDialog = waitingDialog || (function ($) {
		    'use strict';

			// Creating modal dialog's DOM
			var $dialog = $(
				'<div class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true" style="padding-top:15%; overflow-y:visible;">' +
				'<div class="modal-dialog modal-m">' +
				'<div class="modal-content">' +
					'<div class="modal-header"><h3 style="margin:0;"></h3></div>' +
					'<div class="modal-body">' +
						'<div class="progress progress-striped active" style="margin-bottom:0;"><div class="progress-bar" style="width: 100%"></div></div>' +
					'</div>' +
				'</div></div></div>');

			return {
				show: function (message, options) {
					// Assigning defaults
					if (typeof options === 'undefined') {
						options = {};
					}
					if (typeof message === 'undefined') {
						message = 'Loading';
					}
					var settings = $.extend({
						dialogSize: 'm',
						progressType: '',
						onHide: null // This callback runs after the dialog was hidden
					}, options);

					// Configuring dialog
					$dialog.find('.modal-dialog').attr('class', 'modal-dialog').addClass('modal-' + settings.dialogSize);
					$dialog.find('.progress-bar').attr('class', 'progress-bar');
					if (settings.progressType) {
						$dialog.find('.progress-bar').addClass('progress-bar-' + settings.progressType);
					}
					$dialog.find('h3').text(message);
					// Adding callbacks
					if (typeof settings.onHide === 'function') {
						$dialog.off('hidden.bs.modal').on('hidden.bs.modal', function (e) {
							settings.onHide.call($dialog);
						});
					}
					// Opening dialog
					$dialog.modal();
				},
				/**
				 * Closes dialog
				 */
				hide: function () {
					$dialog.modal('hide');
				}
			};

		})(jQuery);
		
	</script>
</body>
</html>