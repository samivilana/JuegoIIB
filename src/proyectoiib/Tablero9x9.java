package proyectoiib;

import javax.swing.JOptionPane;

public class Tablero9x9 {

    //Declaracion de variables
    public int dimSudoku = 9;
    public String resultados = "";
    public int numPasos = 0;
    public int dim = (int) (Math.pow(dimSudoku, 0.5));

      

    //Creacion de matriz
    public int[][] tablero = new int[9][9];

    //Cargar valores al tablero
    public int[][] cargarDatos() {
        
        tablero[0][3] = 5;
        tablero[0][4] = 6;
        tablero[0][8] = 2;

        tablero[1][0] = 5;
        tablero[1][1] = 9;
        tablero[1][4] = 2;
        tablero[1][5] = 4;

        tablero[2][0] = 4;
        tablero[2][2] = 2;
        tablero[2][6] = 7;
        tablero[2][8] = 5;
        tablero[3][0] = 2;
        tablero[3][1] = 7;
        tablero[3][4] = 8;
        tablero[3][6] = 6;
        tablero[4][1] = 8;
        tablero[4][2] = 1;
        tablero[4][3] = 2;
        tablero[4][5] = 5;
        tablero[4][8] = 3;
        tablero[5][0] = 9;
        tablero[5][8] = 8;
        tablero[6][0] = 8;
        tablero[6][1] = 3;
        tablero[6][2] = 6;
        tablero[6][3] = 4;
        tablero[6][4] = 7;
        tablero[6][5] = 2;
        tablero[7][5] = 6;
        tablero[7][6] = 8;
        tablero[7][7] = 3;
        tablero[8][5] = 8;

        return tablero;
    }

    //Metodo que permitir agregar valores
    public void tablero(int[][] tablero, int fila, int colum, int valor) {
        tablero[fila][colum] = valor;
    }

    //Metodo que permitira imprimir el tablero
    public String imprimirTablero(int[][] tablero) {

        String resultados = "";

        for (int i = 0; i < dimSudoku; i++) {
            resultados += "   ";

            if (i % dim == 0) {
                resultados += "-------------------------------------------\n   ";
            }

            for (int j = 0; j < dimSudoku; j++) {
                if (j % dim == 0) {
                    resultados += " |   ";
                }

                resultados += tablero[i][j] + "  ";

                if (j == (dimSudoku - 1)) {
                    resultados += " |";
                }
            }
            resultados += "\n";
        }

        return resultados += "    -------------------------------------------\n";
    }

    //Metodo para resolver usando la tecnica de backtracking
    public boolean resolver(int[][] tablero) {

        for (int i = 0; i < dimSudoku; i++) {

            for (int j = 0; j < dimSudoku; j++) {

                if (tablero[i][j] != 0) {
                    continue;
                }

                for (int k = 1; k <= dimSudoku; k++) {

                    if (esPosibleInsertar(tablero, i, j, k)) {
                        tablero[i][j] = k; //ingresar al tablero
                        resultados += "\n     paso " + (numPasos + 1);
                        resultados += "\n" + imprimirTablero(tablero);

                        numPasos++;

                        if (numPasos > 10000) {
                            return false;
                        }

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
        JOptionPane.showMessageDialog(null, "Solución encontrada");
        return true;
    }

    //Método para saber si es posible ingresar
    public boolean esPosibleInsertar(int[][] tablero, int i, int j, int valor) {

        for (int a = 0; a < dimSudoku; a++) {

            if (a != i && tablero[a][j] == valor) {
                return false;
            }
        }

        for (int a = 0; a < dimSudoku; a++) {

            if (a != j && tablero[i][a] == valor) {
                return false;
            }
        }

        int y = (i / dim) * dim;
        int x = (j / dim) * dim;

        for (int a = 0; a < dimSudoku / dim; a++) {

            for (int b = 0; b < dimSudoku / dim; b++) {
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

        for (int i = 0; i < dimSudoku; i += dim) {

            for (int j = 0; j < dimSudoku; j += dim) {
                boolean flag2 = false;

                for (int a = 0; a < dimSudoku / dim; a++) {
                    for (int b = 0; b < dimSudoku / dim; b++) {

                        if (tablero[i + a][j + b] != 0) {
                            flag++;

                            flag2 = true;
                            break;
                        }
                    }
                    if (flag2 == true) {
                        break;
                    }
                }
            }
        }
        return flag;
    }

    //Metodo para comprobar los valores
    public boolean comprobar_valor(String valor1) {

        if (String.valueOf(valor1).equalsIgnoreCase("")) {
            return false;
        } else {
            int valor = Integer.valueOf(valor1);
            if (valor >= 0 && valor < 10) {
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
            maximo_fila = 2;
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

    //Método para vaciar el tablero
    public void vaciarTabla(int[][] tablero) {
        //Recorrer el tablero
        for (int i = 0; i < dimSudoku; i++) {
            for (int j = 0; j < dimSudoku; j++) {
                tablero[i][j] = 0;//volver todo a 0                      
            }
        }
    }

    public boolean valoresCorrectos(int[][] tablero) {
        for (int i = 0; i < dimSudoku; i++) {
            for (int j = 0; j < dimSudoku; j++) {
                if ((tablero[i][j]) > dimSudoku) {
                    return false;//el valor es mayor a la dimensión
                }
            }
        }
        return true;
    }
}
