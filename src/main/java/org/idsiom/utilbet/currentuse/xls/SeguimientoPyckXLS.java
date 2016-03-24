package org.idsiom.utilbet.currentuse.xls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.idsiom.utilbet.currentuse.ISeguimientoPyckPersistencia;
import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;
import org.idsiom.utilbet.currentuse.bo.EstadoPyck;
import org.idsiom.utilbet.currentuse.bo.PartidoPyckioBO;
import org.idsiom.utilbet.currentuse.bo.PyckBO;


import org.idsiom.utilbet.currentuse.bo.ResultadoPartidoBO;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import static org.idsiom.utilbet.currentuse.constantes.ConstantesCurrent.RUTA_ARCHIVO;

public class SeguimientoPyckXLS implements ISeguimientoPyckPersistencia {

	private static final String PATH_FILE_RENDIMIENTO = RUTA_ARCHIVO + "\\Rendimiento.xls";
	private static final String PESTANA = "Seguimiento";
	
	private static List<PyckBO> pycksPorDefinir = null;
	private static Map<PyckBO,Integer> mapPycksPorDefinir = null;
	private static Double rendimiento = null;
	
	private static int startRow = 6;
	
	/**
	 * Metodo utilzado, luego de montar un Pyck, que debe guardarse en el Excel.
	 * @throws Exception 
	 */
	public void guardarApuesta(CurrentPOddsPortal partidoOP, PartidoPyckioBO partidoPIO, PyckBO pyck) throws XLSException {
		File f = new File(PATH_FILE_RENDIMIENTO);
		HSSFWorkbook workbook = null;
		HSSFSheet sheet = null;
		
		//--------------------->>>>>>>>>>>>>
		// Si el archivo existe, leer el contenido
		if(f.exists()) {
		
			FileInputStream fileInputStream;
			try {
				fileInputStream = new FileInputStream(PATH_FILE_RENDIMIENTO);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new XLSException("No fue posible guardar Apuesta. Archivo no existe");
			}
			
			try {
				workbook = new HSSFWorkbook(fileInputStream);
			} catch (IOException e) {
				e.printStackTrace();
				throw new XLSException();
			}
			
			// Mover los pycks existentes hacia abajo
			sheet = workbook.getSheet(PESTANA);

			HSSFRow row = sheet.getRow(1);
			HSSFCell celdaF2 = row.getCell(convert('g'));
			
			// Buscar en F2, la cantidad total de Pycks
			Integer cantidadPycks = ((Double)celdaF2.getNumericCellValue()).intValue();

			
			
			sheet.shiftRows(startRow, startRow + cantidadPycks - 1, 1);
			
			// Agregar el nuevo Pyck
			row = sheet.createRow(startRow);
			
			// Agregar el nuevo Pyck
			Object[] datos = new Object[] {
					"", "", pyck.getStake(), pyck.getOdds(),
					pyck.getPyck().getAbreviatura(), "PDF", partidoOP.getFecha(), partidoOP.getCountry(),
					partidoOP.getEquipos(), 
					partidoPIO.getEquipoLocal() + " - " + partidoPIO.getEquipoVisitante(), 
					partidoOP.getLeague(), "Soccer"
			};
			llenarCeldas(row, datos);
			
			
		} else {
			//--------------------->>>>>>>>>>>>>
			// Si el archivo no existe, crearlo
			workbook = new HSSFWorkbook();
			sheet = workbook.createSheet(PESTANA);
			
			int numberRow = 0;
			Row row = sheet.createRow(numberRow++);
			
			Object[] datos = new Object[] {
					"Perdidas","Ganancias","Total"
					,null,null,null,
					"TotalPycks",null,"Estado ::",
					"(PDF = PorDefinir;"," FIN = Finalizado;"," SUS = Suspendido)"
				};
			
			llenarCeldas(row, datos);
			
			row = sheet.createRow(numberRow++);
			
			// Se agregan las formulas para el calculo del rendimiento
			Cell cellA = row.createCell('a');
			cellA.setCellFormula("SUM(A" + startRow + ":A100)");

			Cell cellB = row.createCell('b');
			cellB.setCellFormula("SUM(B" + startRow + ":B100)");
			
			Cell cellC = row.createCell('c');
			cellC.setCellFormula("A2 + B2");

			
			//Dejar linea en Blanco
			row = sheet.createRow(numberRow++);
			
			//Agregar linea para titulos
			row = sheet.createRow(numberRow++);
			
			datos = new Object[] {
					"Lose",	"Win",	"Stake",	"Odds",
					"Pick",	"Estado",	"Date", 	"Country",
					"MatchOP",	"MatchPIO",	"Liga",	"Sport"		
			};
			
			llenarCeldas(row, datos);
			
			
			// Agregar el nuevo Pyck
			row = sheet.createRow(numberRow++);
			datos = new Object[] {
					"", "", pyck.getStake(), pyck.getOdds(),
					pyck.getPyck().getAbreviatura(), "PDF", partidoOP.getFecha(), partidoOP.getCountry(),
					partidoOP.getEquipos(), 
					partidoPIO.getEquipoLocal() + " - " + partidoPIO.getEquipoVisitante(), 
					partidoOP.getLeague(), "Soccer"
			};
			llenarCeldas(row, datos);
			
			
						

		} // fin if - else Archivo Existe

		// Pone cada Columna AutoSize
		for(int i = 1; i <= 12; i++) {
			sheet.autoSizeColumn(i);
		}
		
		agregarStaticsObjs(pyck);
		
		FileOutputStream out;
		try {
		    out = new FileOutputStream(new File(PATH_FILE_RENDIMIENTO));
		    workbook.write(out);
		    out.close();
		    System.out.println("Excel written successfully..");
		     
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		    throw new XLSException();
		} catch (IOException e) {
		    e.printStackTrace();
		    throw new XLSException();
		}
		
	}

	private void agregarStaticsObjs(PyckBO pyck) {
		if(pycksPorDefinir == null) {
			pycksPorDefinir = new ArrayList<PyckBO>();
		}
		
		pycksPorDefinir.add(pyck);
		
	}
	
	private void actualizarRendimiento(Boolean acierto, int stake, Double odds) {
		if(rendimiento == null) {
			rendimiento = 0.0;
		}
		
		if(acierto) {
			rendimiento = rendimiento + ((odds - 1) * stake );
		} else {
			rendimiento = rendimiento - stake;
		}
		
	}

	private void llenarCeldas(Row row, Object[] datos) {
		int cellnum = 0;
		
		// Columnas para datos de los partidos
		for (int i=0; i < datos.length; i++) {

			Cell cell = row.createCell(cellnum++);
			
			if(datos[i] == null) {
	            cell.setCellValue("");
	        }
			else if(datos[i] instanceof Date) {
	            cell.setCellValue((Date)datos[i]);
	        }
	        else if(datos[i] instanceof Boolean) {
	            cell.setCellValue((Boolean)datos[i]);
	        }
	        else if(datos[i] instanceof String) {
	            cell.setCellValue((String)datos[i]);
	        }
	        else if(datos[i] instanceof Character) {
	            cell.setCellValue(String.valueOf(datos[i]));
	        }
	        else if(datos[i] instanceof Double) {
	            cell.setCellValue((Double)datos[i]);
	        }
		}
	}
	
	public List<PyckBO> getPyckPorDefinir() throws IOException {
		return getPyckPorDefinir(false);
	}
	
	
	/**
	 * Devuelve los Pycks en el Excel que estan por definirse.
	 * @throws IOException 
	 */
	public List<PyckBO> getPyckPorDefinir(Boolean calcularMaps) throws IOException {
		
		if(pycksPorDefinir != null && pycksPorDefinir.size() > 0 && !calcularMaps) {
			return pycksPorDefinir;
		}
		
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(RUTA_ARCHIVO + "\\Rendimiento.xls");
		} catch (FileNotFoundException e) {
			return pycksPorDefinir;
		}
		
		HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
		HSSFSheet worksheet = workbook.getSheet(PESTANA);
		
		HSSFRow row = worksheet.getRow(1);
		HSSFCell celdaF2 = row.getCell(convert('g'));
		
		// Buscar en F2, la cantidad total de Pycks
		Integer cantidadPycks = ((Double)celdaF2.getNumericCellValue()).intValue();

		
		HSSFCell celdaC2 = row.getCell(convert('c'));
		
		// Buscar en F2, la cantidad total de Pycks
		rendimiento = celdaC2.getNumericCellValue();

		
		String estadoPyck;
		String resultBuscado;
		PyckBO bo;
		ResultadoPartidoBO resulP;
		CurrentPOddsPortal parOP;
		HSSFRow rowPicks;
		int stake;
		String gameOP;
		String country;
		String liga;
		String fecha;
		
		pycksPorDefinir = new ArrayList<PyckBO>();
		mapPycksPorDefinir = new HashMap<PyckBO,Integer>();
		
		
		// Leer el Excel que contiene los juegos pendientes
		// Proceder a leer cada registro
		for(int i = 0; i < cantidadPycks; i++) {
			rowPicks = worksheet.getRow(4+i);
			
			estadoPyck = rowPicks.getCell(convert('f')).getStringCellValue();
			
			// Si el registro está por Definir
			if(estadoPyck.equals("PDF")) {
				
				resultBuscado = rowPicks.getCell(convert('e')).getStringCellValue();
			    stake = ((Double) rowPicks.getCell(convert('c')).getNumericCellValue()).intValue();
			    gameOP = rowPicks.getCell(convert('i')).getStringCellValue();
			    country = rowPicks.getCell(convert('h')).getStringCellValue();
				liga =	rowPicks.getCell(convert('k')).getStringCellValue();	
				fecha = rowPicks.getCell(convert('g')).getStringCellValue();
				
				// Crear un objeto PyckBO equivalente
				bo = new PyckBO();
				parOP = new CurrentPOddsPortal();
				
				parOP.setEquipos(gameOP);
				parOP.setCountry(country);
				parOP.setLeague(liga);
				parOP.setFecha(fecha);
				
				resulP = ResultadoPartidoBO.getObjFromAbrev(resultBuscado);
				
				bo.setAcierto(null);
				bo.setPyck(resulP);
				bo.setStake(stake);
				bo.setPartido(parOP);
				
			     // Agregarlo a la lista
				pycksPorDefinir.add(bo);
				mapPycksPorDefinir.put(bo, 4+i);
				
			}
		}
		
		fileInputStream.close();
		
		// Devolver la lista
		return pycksPorDefinir;
	}

	private int convert(char ch) {
		int pos = ch - 'a';
		return pos;
	}
	
	public double getRendimientoAcumulado() throws XLSException {
		if(rendimiento != null && rendimiento != 0.0) {
			return rendimiento;
		}
		
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(RUTA_ARCHIVO + "\\Rendimiento.xls");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new XLSException();
		}
		
		HSSFWorkbook workbook;
		try {
			workbook = new HSSFWorkbook(fileInputStream);
		} catch (IOException e) {
			e.printStackTrace();
			throw new XLSException();
		}
		
		HSSFSheet worksheet = workbook.getSheet(PESTANA);
		
		HSSFRow row = worksheet.getRow(1);
		HSSFCell celdaC2 = row.getCell(convert('c'));
		
		// Buscar en F2, la cantidad total de Pycks
		rendimiento = celdaC2.getNumericCellValue();
		
		return rendimiento;
	}

	public void guardarResultadosPycks(List<PyckBO> pycks) throws XLSException {
		try {
			this.getPyckPorDefinir(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new XLSException();
		}
		
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(RUTA_ARCHIVO + "\\Rendimiento.xls");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new XLSException();
		}
		
		HSSFWorkbook workbook;
		try {
			workbook = new HSSFWorkbook(fileInputStream);
		} catch (IOException e) {
			e.printStackTrace();
			throw new XLSException();
		}
		
		HSSFSheet worksheet = workbook.getSheet(PESTANA);
		
		HSSFRow row;
		
		Integer lineExcel;
		for(PyckBO p : pycks) {
			lineExcel = mapPycksPorDefinir.get(p);
			
			if(lineExcel != null) {
				row = worksheet.getRow(lineExcel);
				
				if(p.getEstado() == null) {
					// No hacer nada
				} else if(p.getEstado().equals(EstadoPyck.FINALIZADO)) {
					
					row.getCell(convert('f')).setCellValue("FIN");
					
					if(p.getAcierto()) {
						row.getCell(convert('a')).setCellValue(0.0);
						row.getCell(convert('b')).setCellValue( (p.getOdds()-1 )*p.getStake() );
					} else {
						row.getCell(convert('a')).setCellValue(-1.0 * p.getStake() );
						row.getCell(convert('b')).setCellValue( 0.0 );
						
					}
					
					actualizarRendimiento(p.getAcierto(), p.getStake(), p.getOdds());
					
					
				} else if(p.getEstado().equals(EstadoPyck.SUSPENDIDO)) {
					row.getCell(convert('f')).setCellValue("SUS");
				} else { // POR_DEFINIR
					// No hacer nada
				}
			
			
			}
		}
		
	}

}
