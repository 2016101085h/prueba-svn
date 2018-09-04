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
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker.min.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css" />


<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.min.js"></script>

</head>
<style type="text/css">
input.borderless{
	border: none;
	width: 90%;
    text-align: right;
}

div.espacio{
	margin-bottom: 7px;
    margin-top: 7px;
}

div.principal{
	margin-bottom: 20px;
    margin-top: 15px;
}

div.sinPadding{
    padding-left: 5px;
    padding-right: 5px;
}

</style>
<body>
	<form>
		<div class="container" style="margin-top: 20px;">
			<div class="panel-group">
				<div class="panel panel-primary">
					<div class="panel-heading" style="text-align: center;">CONSULTA DE DOCUMENTOS - CARTERA</div>
					<div class="panel-body">
<!-- 						<div class="row principal"> -->
<!-- 							<div class="col-sm-2"><br></div> -->
<!-- 							<div class="col-sm-7"> -->
<!-- 					            <div class="input-group input-append date" > -->
<!-- 					            	<span class="input-group-addon">Desde</span> -->
<!-- 					                <input type="text" class="form-control" id="fecProcDesde" name="fecProcDesde" placeholder="(dd/mm/yyyy)" /> -->
<!-- 					                <span class="input-group-addon add-on"> -->
<!-- 					                	<span class="glyphicon glyphicon-calendar"> -->
<!-- 					                	</span> -->
<!-- 					                </span> -->
<!-- 					                <span class="input-group-addon">Hasta</span> -->
<!-- 					                <input type="text" class="form-control" id="fecProcHasta" name="fecProcHasta" placeholder="(dd/mm/yyyy)" /> -->
<!-- 					                <span class="input-group-addon add-on"> -->
<!-- 					                	<span class="glyphicon glyphicon-calendar"> -->
<!-- 					                	</span> -->
<!-- 					                </span> -->
<!-- 					            </div> -->
<!-- 							</div> -->
<!-- 							<div class="col-sm-1"> -->
<!-- 								<input type="button" class="btn btn-primary" id="btnConsultar" -->
<!-- 										value="Consultar" onClick="consultar()"> -->
<!-- 							</div> -->
<!-- 							<div class="col-sm-1"> -->
<!-- 								<input type="button" class="btn btn-primary" id="btnExportar" -->
<!-- 										value="Exportar" onClick="exportar()"> -->
<!-- 							</div> -->
<!-- 							<div class="col-sm-1"><br></div> -->
<!-- 						</div> -->
						
						<div class="row espacio">
							<div class="col-xs-1"><br></div>
							<div class="col-xs-2"><label>RESUMEN DOCUMENTOS</label></div>
							<div class="col-xs-1" style="text-align: right;"><label>CANTIDAD</label></div>
							<div class="col-xs-2" style="text-align: center;"><label>IMPORTE S/.</label></div>
							<div class="col-xs-1" style="text-align: right;"><label>CANTIDAD</label></div>
							<div class="col-xs-2" style="text-align: center;"><label>IMPORTE $</label></div>
							<div class="col-xs-1" style="text-align: right;"><label>TOTAL DOCUMENTOS</label></div>
							<div class="col-xs-1"><br></div>
						</div>

						<div class="row espacio">
							<div class="col-xs-1"><br></div>
							<div class="col-xs-2"><label>Total documentos ingresados:</label></div>
							<div class="col-xs-1 sinPadding"><input class="borderless" id="cantSolesIng" type="text"></input></div>
							<div class="col-xs-2"><input class="borderless" id="txtSolIng" type="text"></input></div>
							<div class="col-xs-1 sinPadding"><input class="borderless" id="cantDolaresIng" type="text"></input></div>
							<div class="col-xs-2"><input class="borderless" id="txtDolIng" type="text"></input></div>
							<div class="col-xs-1 sinPadding"><input class="borderless" id="txtDocIng" type="text"></input></div>
							<div class="col-xs-1"><br></div>
						</div>
						
						<div class="row espacio">
							<div class="col-xs-1"><br></div>
							<div class="col-xs-2"><label>Total documentos cancelados:</label></div>
							<div class="col-xs-1 sinPadding"><input class="borderless" id="cantSolesSal" type="text"></input></div>
							<div class="col-xs-2"><input class="borderless" id="txtSolSal" type="text"></input></div>
							<div class="col-xs-1 sinPadding"><input class="borderless" id="cantDolaresSal" type="text"></input></div>
							<div class="col-xs-2"><input class="borderless" id="txtDolSal" type="text"></input></div>
							<div class="col-xs-1 sinPadding"><input class="borderless" id="txtDocSal" type="text"></input></div>
							<div class="col-xs-1"><br></div>
						</div>
						
						<div class="row espacio">
							<div class="col-xs-1"><br></div>
							<div class="col-xs-2"><label>Total documentos vigentes sin físico:</label></div>
							<div class="col-xs-1 sinPadding"><input class="borderless" id="cantSolesVig" type="text"></input></div>
							<div class="col-xs-2"><input class="borderless" id="txtSolVig" type="text"></input></div>
							<div class="col-xs-1 sinPadding"><input class="borderless" id="cantDolaresVig" type="text"></input></div>
							<div class="col-xs-2"><input class="borderless" id="txtDolVig" type="text"></input></div>
							<div class="col-xs-1 sinPadding"><input class="borderless" id="txtDocVig" type="text"></input></div>
							<div class="col-xs-1"><br></div>
						</div>
						
						<div class="row">
							<div class="col-sm-1"><br></div>
							<div class="col-sm-10">
								<hr style="color: #337ab7;border-color: #337ab7;border-width: 4px;" />
								</div>
							<div class="col-sm-1"><br></div>
						</div>
						
						<div class="row espacio">
							<div class="col-xs-1"><br></div>
							<div class="col-xs-2"><label>Total documentos en custodia:</label></div>
							<div class="col-xs-1 sinPadding"><input class="borderless" id="cantSolTot" type="text"></input></div>
							<div class="col-xs-2"><input class="borderless" id="txtSolTot" type="text"></input></div>
							<div class="col-xs-1 sinPadding"><input class="borderless" id="cantDolTot" type="text"></input></div>
							<div class="col-xs-2"><input class="borderless" id="txtDolTot" type="text"></input></div>
							<div class="col-xs-1 sinPadding"><input class="borderless" id="txtDocTot" type="text"></input></div>
							<div class="col-xs-1"><br></div>
						</div>
						
						<div class="row espacio">
							<div class="col-sm-5"><br></div>
							<div class="col-sm-2">
								<div>
<!-- 									<input type="button" class="btn btn-primary" id="btnCerrar"  -->
<!-- 										value="Cerrar" onClick="cerrar()"> -->
									<a id="btnCerrar" role="button" class="btn btn-primary" 
									href="/ctrl-boveda-bbva/consulta.htm?accion=mostrarFormConsulta">Cerrar
									</a>
								</div>
								<br>
							</div>
							<div class="col-sm-5"><br></div>
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
	
		$(document).ready(function() {
			
// 			$('#fecProcDesde')
// 	        .datepicker({
// 	            format: 'dd/mm/yyyy',
// 	            autoclose: true,
// 	            language: 'es',
// 	            startDate: '01/01/2000',
// 	            endDate: '12/30/2040'
// 	        });
			
// 			$('#fecProcHasta')
// 	        .datepicker({
// 	            format: 'dd/mm/yyyy',
// 	            autoclose: true,
// 	            language: 'es',
// 	            startDate: '01/01/2000',
// 	            endDate: '12/30/2040'
// 	        });
// 			waitingDialog.show("Cargando ...");
			consultar();
	        
		});
		
		function enviarMensaje( mensaje ) {
            document.getElementById("txtmodalMensaje").innerHTML = mensaje;
            $("#modalMensaje").modal('show');
        }
		
		function posicionarseFechaInicial(){
            $('#fecProcDesde').focus()
        }
		
		function posicionarseFechaFinal(){
            $('#fecProcHasta').focus()
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
		
		function validarFechas(){
			var valFechaIni  = $('#fecProcDesde').val();
            var valFechaFin  = $('#fecProcHasta').val();
            
            if(valFechaIni != null && valFechaIni !== ""){
            	if(valFechaFin != null && valFechaFin !== "" ){
            		if (valFechaIni == "" && valFechaFin == "") {
    					$('#btnModal').attr("onclick", "posicionarseFechaInicial()");
    					//waitingDialog.hide();
    					enviarMensaje('Favor de ingresar las Fechas Correctamente.');
    					return false;
    				}

    				/* -1: err, 1: f1>f2, 2: f1<f2, 0: f1=f2 */
    				var resultado = compararfechas(valFechaIni, valFechaFin)

    				if (resultado == -1) {
    					$('#btnModal').attr("onclick", "posicionarseFechaInicial()");
//     					waitingDialog.hide();
    					enviarMensaje('Favor ingresar fecha inicial correctamente.');
    					return false;
    				} else if (resultado == -2) {
    					$('#btnModal').attr("onclick", "posicionarseFechaFinal()");
//     					waitingDialog.hide();
    					enviarMensaje('Favor ingresar fecha final correctamente.');
    					return false;
    				} else if (resultado == 1) {
    					$('#btnModal').attr("onclick", "posicionarseFechaFinal()");
//     					waitingDialog.hide();
    					enviarMensaje('La fecha inicial debe ser menor a la fecha final.');
    					return false;
    				}
            	}else{
            		$('#btnModal').attr("onclick", "posicionarseFechaFinal()");
//             		waitingDialog.hide();
    				enviarMensaje('Debe ingresar una fecha inicial válida.');
    				return false;
            	}
            }else{
            	$('#btnModal').attr("onclick", "posicionarseFechaInicial()");
//             	waitingDialog.hide();
				enviarMensaje('Debe ingresar una fecha final válida.');
				return false;
            }
            return true;
		}

		function consultar() {
			waitingDialog.show("Procesando ...");
// 			if(validarFechas()){
				
// 				var valFechaIni  = $('#fecProcDesde').val();
// 	            var valFechaFin  = $('#fecProcHasta').val();

				$.ajax({
					url : '${pageContext.request.contextPath}/consulta.htm?accion=consultaGeneral',
// 					data : {
// 						"valFechaIni" : valFechaIni,
// 						"valFechaFin" : valFechaFin
// 					},
					cache : false,
					async : true,
					type : 'GET',
					dataType : 'json',
					success : function(response) {
						waitingDialog.show("Procesando ...");
						$('#txtDocIng').val(response.dataJSON.dataJson.contIng);
						$('#txtSolIng').val(response.dataJSON.dataJson.contIngSol);
						$('#txtDolIng').val(response.dataJSON.dataJson.contIngDol);
						$('#cantSolesIng').val(response.dataJSON.dataJson.cantSolesIng);
						$('#cantDolaresIng').val(response.dataJSON.dataJson.cantDolaresIng);
						
						$('#txtDocSal').val(response.dataJSON.dataJson.contSal);
						$('#txtSolSal').val(response.dataJSON.dataJson.contSalSol);
						$('#txtDolSal').val(response.dataJSON.dataJson.contSalDol);
						$('#cantSolesSal').val(response.dataJSON.dataJson.cantSolesSal);
						$('#cantDolaresSal').val(response.dataJSON.dataJson.cantDolaresSal);
						
						$('#txtDocVig').val(response.dataJSON.dataJson.contVig);
						$('#txtSolVig').val(response.dataJSON.dataJson.contVigSol);
						$('#txtDolVig').val(response.dataJSON.dataJson.contVigDol);
						$('#cantSolesVig').val(response.dataJSON.dataJson.cantSolesVig);
						$('#cantDolaresVig').val(response.dataJSON.dataJson.cantDolaresVig);
						
						$('#txtDocTot').val(response.dataJSON.dataJson.contTot);
						$('#txtSolTot').val(response.dataJSON.dataJson.contTotSol);
						$('#txtDolTot').val(response.dataJSON.dataJson.contTotDol);
						$('#cantSolTot').val(response.dataJSON.dataJson.cantSolTot);
						$('#cantDolTot').val(response.dataJSON.dataJson.cantDolTot);
						console.log("ocultar");
						waitingDialog.hide();
					},
					error : function(err) {
						console.log(err);
						console.log("ocultar error");
						waitingDialog.hide();
					}
				});
// 			}else{
// 				waitingDialog.hide();
// 			}
		}
		
		function exportar(){
			var url = "${pageContext.request.contextPath}/consulta.htm?accion=generarReportePorAnhio"; 
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
				/**
				 * Opens our dialog
				 * @param message Custom message
				 * @param options Custom options:
				 * 				  options.dialogSize - bootstrap postfix for dialog size, e.g. "sm", "m";
				 * 				  options.progressType - bootstrap postfix for progress bar type, e.g. "success", "warning".
				 */
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