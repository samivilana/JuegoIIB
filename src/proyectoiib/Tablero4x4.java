/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoiib;

import javax.swing.JOptionPane;

/**
 *
 * @author PC-DIANA
 */
public class Tablero4x4 {
     //Declaración de variables públicas
    public int dimensionSodoku = 4; //Dimensión del sudoku
    public String resultados = "";  //Cadena de resultados a mostrar
    public int numPasos = 0;    //Cantidad de pasos para resolver el sudoku
    public int dim = (int) (Math.pow(dimensionSodoku, 0.5));  //raíz del valor de dimensión
     //Creacion de matriz
    public int[][] tablero=new int[4][4];
    
    
     //Cargar valores al tablero
        
    public int[][] cargarDatos() {
       
        tablero[1][0] = 2;
        tablero[0][2] = 4;
        tablero[2][3] = 3;
        tablero[2][1] = 2;

        return tablero;
    }
    //Método que permite añadir valores
    public void tablero(int[][] tablero,int fila, int colum, int valor) {
        tablero[fila][colum]=valor;
    }
     //Método para imprimirTablero
    public String imprimirTablero(int[][] tablero) {
        //Cadena que se va a imprimirTablero
        String resultados = "";
        //recorrer las filas
        for (int i = 0; i <dimensionSodoku; i++) {
            resultados += "   ";
            //imprimir la línea de inicio
            if (i % dim == 0) {
                resultados+="------------------------\n   ";
            }
            //Recorre las columnas
            for (int j = 0; j < dimensionSodoku; j++) {
                if (j % dim == 0) {
                    resultados+=" |   ";
                }  
                //Añadir a la cadena resultados
                resultados+=tablero[i][j]+"  ";
                
                if (j == (dimensionSodoku-1)) {
                    resultados+=" |";
                }
            }
            resultados+="\n";
        }
        //retorna los resultados
        return resultados+="    ------------------------\n";
    }
    
    //Metodo para resolver usando la tecnica de backtracking
    public boolean resolver(int[][] tablero) {

        for (int i = 0; i < dimensionSodoku; i++) {

            for (int j = 0; j < dimensionSodoku; j++) {
             //Continuar en caso que no este vacío
                if (tablero[i][j] != 0) {
                    continue;
                }

                for (int k = 1; k <= dimensionSodoku; k++) {

                    if (esPosibleInsertar(tablero, i, j, k)) {
                        tablero[i][j] = k; //ingresar al tablero
                        resultados += "\n     paso " + (numPasos + 1);
                        resultados += "\n" + imprimirTablero(tablero);

                        numPasos++;

                        if (numPasos > 10000) {
                            return false;
                        }
                        // aplicar recursividad
                        boolean b = resolver(tablero);
                        if (b) {
                            return true;
                        }

                        tablero[i][j] = 0;
                    }
                }
                return false;
            }
        }
      JOptionPane.showMessageDialog(null, "Solucion encontrada");
        return true;
    }
    //Método para saber si es posible ingresar
    public boolean esPosibleInsertar(int[][] tablero, int i, int j, int valor) {
    
        for (int a = 0; a < dimensionSodoku; a++) {
            
            if (a != i && tablero[a][j] == valor) {
                return false;
            }
        }
    
        for (int a = 0; a < dimensionSodoku; a++) {
            
            if (a != j && tablero[i][a] == valor) {
                return false;
            }
        }
    
        int y = (i/dim)*dim; //para recorrer celdas de las filas del cuadrante
        int x = (j/dim)*dim; //recorrer celdas de las columnas del cuadrante
        //recorrer las filas
        for (int a = 0; a < dimensionSodoku/ dim; a++) {
            //recorrer las columnas
            for (int b = 0; b < dimensionSodoku / dim; b++) {
                if (a != i && b != j && tablero[y + a][x + b] == valor) {
                    return false;
                }
            }
        }
        return true;
    }
    
    //Metódo para iniciar el sudoku
    public int esPosibleIniciar(int[][] tablero) {
        int flag = 0;

        for (int i = 0; i < dimensionSodoku; i += dim) {

            for (int j = 0; j < dimensionSodoku; j += dim) {
                boolean flag2 = false;

                for (int a = 0; a < dimensionSodoku / dim; a++) {
                    for (int b = 0; b < dimensionSodoku / dim; b++) {
                         //Si el valor no esta vacío
                        if (tablero[i + a][j + b] != 0) {
                            flag++;

                            flag2 = true; //se encuentra un valor
                            break;
                        }
                    }
                    if (flag2 == true) { //en caso de encontrar un valor 
                        break;
                    }
                }
            }
        }
        return flag;
    }
     //Método para vaciar el tablero
    public void vaciarTabla(int[][] tablero){
        //Recorrer el tablero
        for (int i = 0; i < dimensionSodoku; i++) {
            for (int j = 0; j < dimensionSodoku; j++) {                
                tablero[i][j]=0;//volver todo a 0                      
            }
        }
    }
    
     // METODO PARA COMPROBAR LOS VALORES.

    public boolean comprobar_valor(String valor1) {

        if (String.valueOf(valor1).equalsIgnoreCase("")) {
            return false;
        } else {
            int valor = Integer.valueOf(valor1);
            if (valor >= 0 && valor < 5) {
                return true;
            } else {
                return false;
            }
        }

    }
    
    //Metodo para comprobar filas.

    public boolean existe_fila(int numero, int fila) {

        boolean resultado = false;
       
        for (int i = 0; i < tablero.length; i++) {
            if (numero == 0) {
            } else {
                if (tablero[fila][i] == numero) {
                    resultado = true;
                    break;
                }
            }
        }
        return resultado;

    }
    
    //Metodo para comprobar columnas.
    
    public boolean existe_columna(int numero, int columna) {

        boolean resultado = false;
        //int a = matriz[7][0];
        for (int i = 0; i < tablero.length; i++) {
            if (tablero[i][columna] == numero) {
                resultado = true;
                break;
            }
        }
        return resultado;
    }
    
    //Metodo para comprobar si esta llena una caja
    
    public boolean existe_caja(int valor, int fila, int columna) {

        //VARIABLES.
        int minimo_fila;
        int maximo_fila;
        int minimo_columna;
        int maximo_columna;
        boolean resultado = false;

        //DETERMINAMOS LAS FILAS DE LA CAJA.
        if (fila >= 0 && fila < 3) {
            minimo_fila = 0;
            maximo_fila = 3;
        } else if (fila >= 3 && fila < 6) {
            minimo_fila = 3;
            maximo_fila = 5;
        } else {
            minimo_fila = 6;
            maximo_fila = 8;
        }

        //DETERMINAMOS LAS COLUMNAS DE LA CAJA.
        if (columna >= 0 && columna < 3) {
            minimo_columna = 0;
            maximo_columna = 2;
        } else if (columna >= 3 && columna < 6) {
            minimo_columna = 3;
            maximo_columna = 5;
        } else {
            minimo_columna = 6;
            maximo_columna = 8;
        }

        //RECORREMOS EL RANGO DE LA CAJA, Y BUSCAMOS EL VALOR.
        for (int f = minimo_fila; f <= maximo_fila; f++) {
            for (int c = minimo_columna; c <= maximo_columna; c++) {
                if (valor == 0) {
                } else {
                    if (tablero[f][c] == valor) {
                        resultado = true;
                        break;

                    }
                }
            }
        }
        //REGRESAMOS EL VALOR BOOLEANO.
        return resultado;

    }
    //Método para valores correctos
    public boolean valoresCorrectos(int[][] tablero){      
        for (int i = 0; i < dimensionSodoku; i++) {
            for (int j = 0; j < dimensionSodoku; j++) {                
                if((tablero[i][j])>dimensionSodoku)
                    return false;//el valor es mayor a la dimensión
            }
        }
        return true;
    }

     //METODO PARA SABER SI  EL JUEGO FUE TERMINADO.
	public static boolean terminado( int[][] matriz ){

		boolean resultado = true;

		for ( int f = 0; f < matriz.length; f ++)
			for ( int c = 0; c < matriz[0].length; c ++)
				if ( matriz[f][c] == 0 )
					resultado = false;

                              JOptionPane.showMessageDialog(null, "terminado");
                              return resultado;

	}

    
}
