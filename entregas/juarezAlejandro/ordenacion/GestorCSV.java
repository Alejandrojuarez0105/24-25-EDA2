import java.util.Scanner;

public class GestorCSV {
    private String[][] datos;
    private String[] cabeceras;
    private int filas;
    private int columnas;
    private Indice[] indices;
    private boolean[] columnaIndexada;

    public GestorCSV(int capacidadMaxima, int numColumnas) {
        datos = new String[capacidadMaxima][numColumnas];
        cabeceras = new String[numColumnas];
        indices = new Indice[numColumnas];
        columnaIndexada = new boolean[numColumnas];
        filas = 0;
        columnas = numColumnas;
    }

    public void cargarDatos(String[] cabeceras, String[][] datosEntrada) {
        this.cabeceras = cabeceras;

        filas = datosEntrada.length;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                datos[i][j] = datosEntrada[i][j];
            }
        }
        System.out.println("> Datos cargados");
    }

    public void crearIndice(String nombreColumna) {
        int indiceColumna = obtenerIndiceColumna(nombreColumna);
        if (indiceColumna == -1) {
            System.out.println("Columna no encontrada: " + nombreColumna);
            return;
        }

        indices[indiceColumna] = new Indice(filas);
        columnaIndexada[indiceColumna] = true;

        for (int i = 0; i < filas; i++) {
            indices[indiceColumna].agregar(datos[i][indiceColumna], i);
        }

        System.out.println("> Índice creado para la columna: " + nombreColumna);
    }

    public String[][] buscarPorIndice(String nombreColumna, String valor) {
        int indiceColumna = obtenerIndiceColumna(nombreColumna);
        if (indiceColumna == -1 || !columnaIndexada[indiceColumna]) {
            System.out.println("La columna no está indexada: " + nombreColumna);
            return new String[0][0];
        }

        int[] posiciones = indices[indiceColumna].buscar(valor);

        String[][] resultado = new String[posiciones.length][columnas];
        for (int i = 0; i < posiciones.length; i++) {
            for (int j = 0; j < columnas; j++) {
                resultado[i][j] = datos[posiciones[i]][j];
            }
        }

        return resultado;
    }

    private int obtenerIndiceColumna(String nombreColumna) {
        for (int i = 0; i < cabeceras.length; i++) {
            if (cabeceras[i].equals(nombreColumna)) {
                return i;
            }
        }
        return -1;
    }

    public boolean estaIndexada(String nombreColumna) {
        int indiceColumna = obtenerIndiceColumna(nombreColumna);
        if (indiceColumna == -1) {
            return false;
        }
        return columnaIndexada[indiceColumna];
    }

    public String[] obtenerValoresUnicos(String nombreColumna) {
        int indiceColumna = obtenerIndiceColumna(nombreColumna);
        if (indiceColumna == -1 || !columnaIndexada[indiceColumna]) {
            System.out.println("La columna no está indexada: " + nombreColumna);
            return new String[0];
        }

        return indices[indiceColumna].obtenerTodos();
    }

    public void imprimirDatos() {
        for (int i = 0; i < cabeceras.length; i++) {
            System.out.printf("%-25.20s", cabeceras[i]);
        }
        System.out.println();
        System.out.println("=".repeat(60));

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.printf("%-25.20s", datos[i][j]);
            }
            System.out.println();
        }
        System.out.println("=".repeat(60));
    }

    // --------------------------------------------------------------------------------------------------
    public void imprimirDatosOrdenadosPorIDInsercion() {
        String[][] datosOrdenados = new String[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                datosOrdenados[i][j] = datos[i][j];
            }
        }
        for (int i = 1; i < filas; i++) {
            String[] filaActual = datosOrdenados[i];
            String idActual = filaActual[0];
            int j = i - 1;

            while (j >= 0 && compararIDs(datosOrdenados[j][0], idActual) > 0) {
                datosOrdenados[j + 1] = datosOrdenados[j];
                j--;
            }
            datosOrdenados[j + 1] = filaActual;
        }

        for (int i = 0; i < cabeceras.length; i++) {
            System.out.printf("%-25.20s", cabeceras[i]);
        }
        System.out.println();
        System.out.println("=".repeat(60));

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.printf("%-25.20s", datosOrdenados[i][j]);
            }
            System.out.println();
        }
        System.out.println("=".repeat(60));
    }
    // --------------------------------------------------------------------------------------------------

    // --------------------------------------------------------------------------------------------------
    public void imprimirDatosOrdenadosPorIDBubbleSort() {
        String[][] datosOrdenados = new String[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                datosOrdenados[i][j] = datos[i][j];
            }
        }

        for (int i = 0; i < filas - 1; i++) {
            for (int j = 0; j < filas - i - 1; j++) {
                if (compararIDs(datosOrdenados[j][0], datosOrdenados[j + 1][0]) > 0) {
                    String[] temp = datosOrdenados[j];
                    datosOrdenados[j] = datosOrdenados[j + 1];
                    datosOrdenados[j + 1] = temp;
                }
            }
        }
        for (int i = 0; i < cabeceras.length; i++) {
            System.out.printf("%-25.20s", cabeceras[i]);
        }
        System.out.println();
        System.out.println("=".repeat(60));

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.printf("%-25.20s", datosOrdenados[i][j]);
            }
            System.out.println();
        }
        System.out.println("=".repeat(60));
    }
    // --------------------------------------------------------------------------------------------------

    // --------------------------------------------------------------------------------------------------
    public void imprimirDatosOrdenadosPorIDInsercionPasoaPaso() {
        Scanner scanner = new Scanner(System.in);
        String[][] datosOrdenados = new String[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                datosOrdenados[i][j] = datos[i][j];
            }
        }

        System.out.println("\nIniciando ordenamiento por inserción interactivo...");
        System.out.println("Presiona ENTER para avanzar paso a paso\n");

        for (int i = 1; i < filas; i++) {
            String[] filaActual = datosOrdenados[i];
            String idActual = filaActual[0];
            int j = i - 1;

            System.out.println("Paso " + i + ": Comparando ID " + idActual +
                    " con elementos anteriores");

            while (j >= 0 && compararIDs(datosOrdenados[j][0], idActual) > 0) {
                System.out.println("  - Moviendo ID " + datosOrdenados[j][0] +
                        " una posición adelante");
                datosOrdenados[j + 1] = datosOrdenados[j];
                j--;

                imprimirEstadoParcial(datosOrdenados, i, j);
                scanner.nextLine();
            }

            if (j + 1 != i) {
                System.out.println("  - Insertando ID " + idActual + " en posición " + (j +
                        1));
                datosOrdenados[j + 1] = filaActual;
                imprimirEstadoParcial(datosOrdenados, i, j);
                scanner.nextLine();
            } else {
                System.out.println("  - ID " + idActual +
                        " ya está en la posición correcta");
            }
        }

        System.out.println("\nOrdenamiento completado!");
        imprimirDatosOrdenados(datosOrdenados);
        scanner.close();
    }

    // --------------------------------------------------------------------------------------------------

    // --------------------------------------------------------------------------------------------------
    public void imprimirDatosOrdenadosPorIDBubbleSortPasoaPaso() {
        Scanner scanner = new Scanner(System.in);
        String[][] datosOrdenados = new String[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                datosOrdenados[i][j] = datos[i][j];
            }
        }
        System.out.println("Presiona ENTER para comenzar");
        scanner.nextLine();

        for (int i = 0; i < filas - 1; i++) {
            System.out.println("\n=== Pasada número " + (i + 1) + " ===");
            System.out.println("Comparando elementos...");

            for (int j = 0; j < filas - i - 1; j++) {
                System.out.println("\nComparando IDs: " + datosOrdenados[j][0] + " y " +
                        datosOrdenados[j + 1][0]);

                if (compararIDs(datosOrdenados[j][0], datosOrdenados[j + 1][0]) > 0) {
                    System.out.println(
                            "  - Intercambiando: " + datosOrdenados[j][0] + " <-> " + datosOrdenados[j +
                                    1][0]);

                    String[] temp = datosOrdenados[j];
                    datosOrdenados[j] = datosOrdenados[j + 1];
                    datosOrdenados[j + 1] = temp;

                    imprimirEstadoParcial(datosOrdenados, i, j);
                } else {
                    System.out.println("  - No se necesita intercambio");
                    imprimirEstadoParcial(datosOrdenados, i, j);
                }

                System.out.println("Presiona ENTER para continuar...");
                scanner.nextLine();
            }

            System.out.println("\nEstado después de la pasada " + (i + 1) + ":");
            imprimirEstadoParcial(datosOrdenados, i, -1);
            System.out.println("Presiona ENTER para continuar a la siguiente pasada...");
            scanner.nextLine();
        }

        System.out.println("\n¡Ordenamiento completado!");
        imprimirDatosOrdenados(datosOrdenados);
        scanner.close();
    }
    // --------------------------------------------------------------------------------------------------

    // --------------------------------------------------------------------------------------------------
    private int compararIDs(String id1, String id2) {
        if (id1.length() != id2.length()) {
            return id1.length() < id2.length() ? -1 : 1;
        }
        for (int i = 0; i < id1.length(); i++) {
            char c1 = id1.charAt(i);
            char c2 = id2.charAt(i);
            if (c1 != c2) {
                return c1 < c2 ? -1 : 1;
            }
        }

        return 0;
    }

    // --------------------------------------------------------------------------------------------------
    private void imprimirEstadoParcial(String[][] datosOrdenados, int pasoActual, int posicionActual) {
        System.out.println("\nEstado actual (Paso " + pasoActual + "):");
        for (int i = 0; i < cabeceras.length; i++) {
            System.out.printf("%-25.20s", cabeceras[i]);
        }
        System.out.println();
        System.out.println("=".repeat(60));

        for (int i = 0; i < filas; i++) {
            if (i == posicionActual + 1) {
                System.out.print(">>> ");
            } else {
                System.out.print("    ");
            }

            for (int j = 0; j < columnas; j++) {
                System.out.printf("%-25.20s", datosOrdenados[i][j]);
            }
            System.out.println();
        }
        System.out.println("=".repeat(60));
    }

    // --------------------------------------------------------------------------------------------------
    private void imprimirDatosOrdenados(String[][] datosOrdenados) {
        System.out.println("\nResultado final ordenado:");
        for (int i = 0; i < cabeceras.length; i++) {
            System.out.printf("%-25.20s", cabeceras[i]);
        }
        System.out.println();
        System.out.println("=".repeat(60));

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.printf("%-25.20s", datosOrdenados[i][j]);
            }
            System.out.println();
        }
        System.out.println("=".repeat(60));
    }
}