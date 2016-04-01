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
	
	private static int startRow = 5;
	
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
			HSSFCell celdag2 = row.getCell(convert('g'));
			
			// Buscar en F2, la cantidad total de Pycks
			Integer cantidadPycks = ((Double)celdag2.getNumericCellValue()).intValue();

			
			HSSFCell celdaa2 = row.getCell(convert('a'));
			HSSFCell celdab2 = row.getCell(convert('b'));
			HSSFCell celdac2 = row.getCell(convert('c'));
			
			
			sheet.shiftRows(4, 4 + cantidadPycks, 1);
			
			// Agregar el nuevo Pyck
			row = sheet.createRow(4);
			
			// Agregar el nuevo Pyck
			Object[] datos = new Object[] {
					"", "", pyck.getStake(), pyck.getOdds(),
					pyck.getPyck().getAbreviatura(), "PDF", partidoOP.getFecha(), partidoOP.getCountry(),
					partidoOP.getEquipos(), 
					partidoPIO.getEquipoLocal() + " - " + partidoPIO.getEquipoVisitante(), 
					partidoOP.getLeague(), "Soccer", new Integer(1)
			};
			llenarCeldas(row, datos);
			
			
			cantidadPycks = cantidadPycks + 1;
			
			double ganancias = 0.0;
			double perdidas = 0.0;
			HSSFRow rowPycks; 
			for(int i = 4; i < 4 + cantidadPycks; i++) {
				rowPycks = sheet.getRow(i);
				
				try {
					perdidas = perdidas + rowPycks.getCell(convert('a')).getNumericCellValue();
					ganancias = ganancias + rowPycks.getCell(convert('b')).getNumericCellValue();
				} catch(java.lang.IllegalStateException ex) {
					perdidas = perdidas + 0.0;
					ganancias = ganancias + 0.0;
				}
			}
			
			celdag2.setCellValue(cantidadPycks);
			celdaa2.setCellValue(perdidas);
			celdab2.setCellValue(ganancias);
			celdac2.setCellValue(ganancias + perdidas);
			
			
			
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
			Cell cellA = row.createCell(convert('a'));
			cellA.setCellValue(0.0);

			Cell cellB = row.createCell(convert('b'));
			cellB.setCellValue(0.0);
			
			Cell cellC = row.createCell(convert('c'));
			cellC.setCellValue(0.0);
			
			Cell cellG = row.createCell(convert('g'));
			cellG.setCellValue(1.0);

			
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
					partidoOP.getLeague(), "Soccer", new Integer(1)
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
	        else if(datos[i] instanceof Integer) {
	            cell.setCellValue((Integer)datos[i]);
	        }
		}
	}
	
	public List<PyckBO> getPyckPorDefinir() throws XLSException {
		return getPyckPorDefinir(false);
	}
	
	
	/**
	 * Devuelve los Pycks en el Excel que estan por definirse.
	 * @throws IOException 
	 */
	public List<PyckBO> getPyckPorDefinir(Boolean calcularMaps) throws XLSException {
		
		if(pycksPorDefinir != null && pycksPorDefinir.size() > 0 && !calcularMaps) {
			return pycksPorDefinir;
		}
		
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(RUTA_ARCHIVO + "\\Rendimiento.xls");
		} catch (FileNotFoundException e) {
			return pycksPorDefinir;
		}
		
		HSSFWorkbook workbook;
		try {
			workbook = new HSSFWorkbook(fileInputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new XLSException();
		}

		HSSFSheet worksheet = workbook.getSheet(PESTANA);
		
		HSSFRow row = worksheet.getRow(1);
		HSSFCell celdag2 = row.getCell(convert('g'));
		
		System.out.println("IMPL celdaF2 = " + celdag2.getNumericCellValue());
		
		Integer cantidadPycks;
		cantidadPycks = ((Double)celdag2.getNumericCellValue()).intValue();

		
		System.out.println("IMPL cantidadPycks = " + cantidadPycks);
		
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
		double odds;
		
		pycksPorDefinir = new ArrayList<PyckBO>();
		mapPycksPorDefinir = new HashMap<PyckBO,Integer>();
		
		System.out.println("IMPL justo antes del for " + cantidadPycks);
		
		// Leer el Excel que contiene los juegos pendientes
		// Proceder a leer cada registro
		for(int i = 0; i < cantidadPycks; i++) {
			System.out.println("Entro al for i " + i);
			
			rowPicks = worksheet.getRow(startRow+i-1);
			
			estadoPyck = rowPicks.getCell(convert('f')).getStringCellValue();
			
			System.out.println("iteracion = " + i + "  es " + estadoPyck + "   startrow+i " + (startRow+i));
			// Si el registro está por Definir
			if(estadoPyck.equals("PDF")) {
				
				resultBuscado = rowPicks.getCell(convert('e')).getStringCellValue();
			    stake = ((Double) rowPicks.getCell(convert('c')).getNumericCellValue()).intValue();
			    gameOP = rowPicks.getCell(convert('i')).getStringCellValue();
			    country = rowPicks.getCell(convert('h')).getStringCellValue();
				liga =	rowPicks.getCell(convert('k')).getStringCellValue();	
				fecha = rowPicks.getCell(convert('g')).getStringCellValue();
				odds = rowPicks.getCell(convert('d')).getNumericCellValue();
				
				System.out.println("iteracion = " + i + "  es " + estadoPyck + "   startrow+i " + (startRow+i) + "  GAMEOP = " + gameOP);
				
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
				bo.setOdds(odds);
				
			     // Agregarlo a la lista
				pycksPorDefinir.add(bo);
				Integer aux = startRow+i-1;
				mapPycksPorDefinir.put(bo, aux);
				
				System.out.println("Se inserto en el map en aux = " + aux);
				System.out.println("Se inserto en el map con bo = " + bo.toString());
				
			}
		}
		
		try {
			fileInputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
			return 0.0;
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
		if(celdaC2 == null) {
			return 0.0;
		}
		
		rendimiento = celdaC2.getNumericCellValue();
		
		return rendimiento;
	}

	public void guardarResultadosPycks(List<PyckBO> pycks) throws XLSException {
		this.getPyckPorDefinir(true);
		
		
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
			System.out.println("Se buscara el pyck = " + p.toString());
			
			//-------------------------->>>>>>>>>>>>>>>>>>>>>>>>
			//   --- TODO :: Revisar la causa por la cual el get no funciono. Se tuvo que hacer implementacion propia para salir del paso
			//lineExcel = mapPycksPorDefinir.get(p);
			lineExcel = get(mapPycksPorDefinir,p);
			
			
			
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
			
			
			} else {
				System.out.println("No fue posible encontrar bo en el MAPs");
			}
		}
		
		HSSFRow rowTotales = worksheet.getRow(1);
		HSSFCell celdag2 = rowTotales.getCell(convert('g'));
		HSSFCell celdaa2 = rowTotales.getCell(convert('a'));
		HSSFCell celdab2 = rowTotales.getCell(convert('b'));
		HSSFCell celdac2 = rowTotales.getCell(convert('c'));
		
		Integer cantidadPycks;
		cantidadPycks = ((Double)celdag2.getNumericCellValue()).intValue();
		
		double ganancias = 0.0;
		double perdidas = 0.0;
		HSSFRow rowPycks; 
		for(int i = 4; i < 4 + cantidadPycks; i++) {
			rowPycks = worksheet.getRow(i);
			
			try {
				perdidas = perdidas + rowPycks.getCell(convert('a')).getNumericCellValue();
				ganancias = ganancias + rowPycks.getCell(convert('b')).getNumericCellValue();
			} catch(java.lang.IllegalStateException ex) {
				perdidas = perdidas + 0.0;
				ganancias = ganancias + 0.0;
			}
		}
		
		celdag2.setCellValue(cantidadPycks);
		celdaa2.setCellValue(perdidas);
		celdab2.setCellValue(ganancias);
		celdac2.setCellValue(ganancias + perdidas);
		
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

	private Integer get(Map<PyckBO, Integer> mapPycksPorDefinir2, PyckBO param) {
		for(PyckBO p : mapPycksPorDefinir.keySet()) {
			System.out.println("value = " + mapPycksPorDefinir.get(p) + "; key " + p.toString());
			
			
			if(param.equals(p)) {
				System.out.println("implementacion personal TRUE");
				return mapPycksPorDefinir.get(p);
			} else {
				System.out.println("implementacion personal FALSE");
			}
		}
		return null;
	}

}
