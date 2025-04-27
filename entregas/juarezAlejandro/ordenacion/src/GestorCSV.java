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

    public void imprimirDatosOrdenadosPorIDBucketSort() {

        String[][] datosOrdenados = new String[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                datosOrdenados[i][j] = datos[i][j];
            }
        }

        String minID = datosOrdenados[0][0];
        String maxID = datosOrdenados[0][0];

        for (int i = 1; i < filas; i++) {
            if (compararIDs(datosOrdenados[i][0], minID) < 0) {
                minID = datosOrdenados[i][0];
            }
            if (compararIDs(datosOrdenados[i][0], maxID) > 0) {
                maxID = datosOrdenados[i][0];
            }
        }

        int numBuckets = 10;

        String[][][] buckets = new String[numBuckets][filas][columnas];

        int[] bucketSizes = new int[numBuckets];

        for (int i = 0; i < filas; i++) {
            int diferenciaLongitud = datosOrdenados[i][0].length() - minID.length();

            int bucketIndex = 0;
            if (diferenciaLongitud > 0) {
                bucketIndex = 9;
            } else if (diferenciaLongitud < 0) {
                bucketIndex = 0;
            } else {
                char primerDigito = datosOrdenados[i][0].charAt(0);
                char minPrimerDigito = minID.charAt(0);
                char maxPrimerDigito = maxID.charAt(0);

                int rangoDigitos = maxPrimerDigito - minPrimerDigito + 1;

                bucketIndex = ((primerDigito - minPrimerDigito) * numBuckets) / rangoDigitos;
                bucketIndex = Math.min(bucketIndex, numBuckets - 1);
            }

            for (int j = 0; j < columnas; j++) {
                buckets[bucketIndex][bucketSizes[bucketIndex]][j] = datosOrdenados[i][j];
            }
            bucketSizes[bucketIndex]++;
        }

        for (int b = 0; b < numBuckets; b++) {
            if (bucketSizes[b] > 0) {
                for (int i = 1; i < bucketSizes[b]; i++) {
                    String[] filaActual = new String[columnas];
                    for (int j = 0; j < columnas; j++) {
                        filaActual[j] = buckets[b][i][j];
                    }

                    String idActual = filaActual[0];
                    int j = i - 1;

                    while (j >= 0 && compararIDs(buckets[b][j][0], idActual) > 0) {
                        for (int k = 0; k < columnas; k++) {
                            buckets[b][j + 1][k] = buckets[b][j][k];
                        }
                        j--;
                    }

                    for (int k = 0; k < columnas; k++) {
                        buckets[b][j + 1][k] = filaActual[k];
                    }
                }
            }
        }

        int index = 0;

        for (int b = 0; b < numBuckets; b++) {
            for (int i = 0; i < bucketSizes[b]; i++) {
                for (int j = 0; j < columnas; j++) {
                    datosOrdenados[index][j] = buckets[b][i][j];
                }
                index++;
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

    public void imprimirDatosOrdenadosPorIDBucketSortPasoaPaso() {
        Scanner scanner = new Scanner(System.in);
        String[][] datosOrdenados = new String[filas][columnas];
        System.out.println("Paso 1: Copiando datos originales...");

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                datosOrdenados[i][j] = datos[i][j];
            }
        }
        imprimirEstadoActual(datosOrdenados, "Datos copiados");
        scanner.nextLine();

        System.out.println("\nPaso 2: Buscando ID mínimo y máximo...");
        String minID = datosOrdenados[0][0];
        String maxID = datosOrdenados[0][0];

        for (int i = 1; i < filas; i++) {
            if (compararIDs(datosOrdenados[i][0], minID) < 0) {
                minID = datosOrdenados[i][0];
                System.out.println("  - Nuevo mínimo encontrado: " + minID);
            }
            if (compararIDs(datosOrdenados[i][0], maxID) > 0) {
                maxID = datosOrdenados[i][0];
                System.out.println("  - Nuevo máximo encontrado: " + maxID);
            }
        }
        System.out.println("  - ID Mínimo: " + minID);
        System.out.println("  - ID Máximo: " + maxID);
        scanner.nextLine();

        System.out.println("\nPaso 3: Creando 10 cubetas (buckets)...");
        int numBuckets = 10;
        String[][][] buckets = new String[numBuckets][filas][columnas];
        int[] bucketSizes = new int[numBuckets];
        scanner.nextLine();

        System.out.println("\nPaso 4: Distribuyendo elementos en cubetas...");
        for (int i = 0; i < filas; i++) {
            String idActual = datosOrdenados[i][0];

            int bucketIndex = 0;
            if (idActual.length() > minID.length()) {
                bucketIndex = 9;
            } else if (idActual.length() < minID.length()) {
                bucketIndex = 0;
            } else {
                char primerDigito = idActual.charAt(0);
                char minPrimerDigito = minID.charAt(0);
                bucketIndex = (primerDigito - minPrimerDigito) % numBuckets;
            }

            System.out.println("  - ID " + idActual + " asignado a cubeta " +
                    bucketIndex);

            for (int j = 0; j < columnas; j++) {
                buckets[bucketIndex][bucketSizes[bucketIndex]][j] = datosOrdenados[i][j];
            }
            bucketSizes[bucketIndex]++;

            imprimirBuckets(buckets, bucketSizes, numBuckets);
            scanner.nextLine();
        }

        System.out.println("\nPaso 5: Ordenando cada cubeta con InsertionSort...");
        for (int b = 0; b < numBuckets; b++) {
            if (bucketSizes[b] > 0) {
                System.out.println("\nOrdenando cubeta " + b + " (" + bucketSizes[b] +
                        " elementos)...");
                scanner.nextLine();

                for (int i = 1; i < bucketSizes[b]; i++) {
                    String[] filaActual = new String[columnas];
                    for (int j = 0; j < columnas; j++) {
                        filaActual[j] = buckets[b][i][j];
                    }

                    String idActual = filaActual[0];
                    int j = i - 1;

                    System.out.println("  - Comparando ID " + idActual + " en posición " + i);

                    while (j >= 0 && compararIDs(buckets[b][j][0], idActual) > 0) {
                        System.out.println("    - Moviendo ID " + buckets[b][j][0] + " a posición " +
                                (j + 1));

                        for (int k = 0; k < columnas; k++) {
                            buckets[b][j + 1][k] = buckets[b][j][k];
                        }
                        j--;

                        imprimirBucket(buckets[b], bucketSizes[b], "Estado parcial de cubeta " + b);
                        scanner.nextLine();
                    }

                    System.out.println("    - Insertando ID " + idActual + " en posición " + (j +
                            1));
                    for (int k = 0; k < columnas; k++) {
                        buckets[b][j + 1][k] = filaActual[k];
                    }

                    imprimirBucket(buckets[b], bucketSizes[b], "Cubeta " + b +
                            " después de inserción");
                    scanner.nextLine();
                }
            }
        }

        System.out.println("\nPaso 6: Combinando cubetas ordenadas...");
        int index = 0;
        for (int b = 0; b < numBuckets; b++) {
            if (bucketSizes[b] > 0) {
                System.out.println("  - Copiando cubeta " + b + " (" + bucketSizes[b] +
                        " elementos)");
                for (int i = 0; i < bucketSizes[b]; i++) {
                    for (int j = 0; j < columnas; j++) {
                        datosOrdenados[index][j] = buckets[b][i][j];
                    }
                    index++;
                }
                imprimirEstadoActual(datosOrdenados, "Estado después de combinar cubeta " +
                        b);
                scanner.nextLine();
            }
        }

        System.out.println("\n¡Ordenamiento completado!");
        imprimirDatosOrdenados(datosOrdenados);
        scanner.close();
    }

    private void imprimirBuckets(String[][][] buckets, int[] bucketSizes, int numBuckets) {
        System.out.println("\nEstado de cubetas:");
        for (int b = 0; b < numBuckets; b++) {
            if (bucketSizes[b] > 0) {
                System.out.println("Cubeta " + b + " (" + bucketSizes[b] + " elementos):");
                for (int i = 0; i < bucketSizes[b]; i++) {
                    System.out.print("  " + buckets[b][i][0]);
                }
                System.out.println();
            }
        }
    }

    private void imprimirBucket(String[][] bucket, int size, String titulo) {
        System.out.println("\n" + titulo + ":");
        for (int i = 0; i < size; i++) {
            System.out.print("  [" + i + "]: ");
            for (int j = 0; j < columnas; j++) {
                System.out.print(bucket[i][j] + " ");
            }
            System.out.println();
        }
    }

    // --------------------------------------------------------------------------------------------------

    // -------------------------------------------------------------------------------------------------

    public void imprimirDatosOrdenadosPorIDCountingSort() {
        String[][] datosOrdenados = new String[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                datosOrdenados[i][j] = datos[i][j];
            }
        }

        String minID = datosOrdenados[0][0];
        String maxID = datosOrdenados[0][0];
        for (int i = 1; i < filas; i++) {
            if (compararIDs(datosOrdenados[i][0], minID) < 0) {
                minID = datosOrdenados[i][0];
            }
            if (compararIDs(datosOrdenados[i][0], maxID) > 0) {
                maxID = datosOrdenados[i][0];
            }
        }

        int min = convertirIDaValor(minID);
        int max = convertirIDaValor(maxID);
        int rango = max - min + 1;

        int[] conteo = new int[rango];

        for (int i = 0; i < filas; i++) {
            int idValor = convertirIDaValor(datosOrdenados[i][0]);
            conteo[idValor - min]++;
        }

        for (int i = 1; i < conteo.length; i++) {
            conteo[i] += conteo[i - 1];
        }

        String[][] salida = new String[filas][columnas];

        for (int i = filas - 1; i >= 0; i--) {
            int idValor = convertirIDaValor(datosOrdenados[i][0]);
            int posicion = conteo[idValor - min] - 1;

            for (int j = 0; j < columnas; j++) {
                salida[posicion][j] = datosOrdenados[i][j];
            }

            conteo[idValor - min]--;
        }

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                datosOrdenados[i][j] = salida[i][j];
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

    public void imprimirDatosOrdenadosPorIDCountingSortPasoaPaso() {
        Scanner scanner = new Scanner(System.in);
        String[][] datosOrdenados = new String[filas][columnas];

        System.out.println("Paso 1: Copiando datos originales...");
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                datosOrdenados[i][j] = datos[i][j];
            }
        }
        imprimirEstadoActual(datosOrdenados, "Datos copiados");
        scanner.nextLine();

        System.out.println("\nPaso 2: Buscando ID mínimo y máximo...");
        String minID = datosOrdenados[0][0];
        String maxID = datosOrdenados[0][0];

        for (int i = 1; i < filas; i++) {
            if (compararIDs(datosOrdenados[i][0], minID) < 0) {
                minID = datosOrdenados[i][0];
                System.out.println("  - Nuevo mínimo encontrado: " + minID);
            }
            if (compararIDs(datosOrdenados[i][0], maxID) > 0) {
                maxID = datosOrdenados[i][0];
                System.out.println("  - Nuevo máximo encontrado: " + maxID);
            }
        }
        System.out.println("  - ID Mínimo: " + minID);
        System.out.println("  - ID Máximo: " + maxID);
        scanner.nextLine();

        System.out.println("\nPaso 3: Convirtiendo IDs a valores numéricos...");
        System.out.println("  (Sumaremos los valores ASCII de cada caracter del ID)");

        int min = convertirIDaValor(minID);
        int max = convertirIDaValor(maxID);
        int rango = max - min + 1;

        System.out.println("  - Valor mínimo: " + min);
        System.out.println("  - Valor máximo: " + max);
        System.out.println("  - Rango de conteo: " + rango);
        scanner.nextLine();

        System.out.println("\nPaso 4: Creando arreglo de conteo de tamaño " + rango);
        int[] conteo = new int[rango];
        imprimirArregloConteo(conteo);
        scanner.nextLine();

        System.out.println("\nPaso 5: Contando frecuencia de cada ID...");
        for (int i = 0; i < filas; i++) {
            int idValor = convertirIDaValor(datosOrdenados[i][0]);
            conteo[idValor - min]++;

            System.out.println("  - ID " + datosOrdenados[i][0] + " → Valor: " + idValor
                    +
                    " → Incrementando conteo[" + (idValor - min) + "]");
            imprimirArregloConteo(conteo);
            scanner.nextLine();
        }

        System.out.println("\nPaso 6: Calculando posiciones acumuladas...");
        for (int i = 1; i < conteo.length; i++) {
            conteo[i] += conteo[i - 1];
            System.out.println("  - conteo[" + i + "] += conteo[" + (i - 1) + "] → " +
                    conteo[i]);
            imprimirArregloConteo(conteo);
            scanner.nextLine();
        }

        System.out.println("\nPaso 7: Creando arreglo de salida...");
        String[][] salida = new String[filas][columnas];

        System.out.println("\nPaso 8: Colocando elementos en orden...");
        for (int i = filas - 1; i >= 0; i--) {
            String idActual = datosOrdenados[i][0];
            int idValor = convertirIDaValor(idActual);
            int posicion = conteo[idValor - min] - 1;

            System.out.println("  - ID " + idActual + " → Valor: " + idValor +
                    " → Posición final: " + posicion);

            for (int j = 0; j < columnas; j++) {
                salida[posicion][j] = datosOrdenados[i][j];
            }

            conteo[idValor - min]--;
            imprimirArregloConteo(conteo);
            imprimirEstadoActual(salida, "Arreglo de salida parcial");
            scanner.nextLine();
        }

        System.out.println("\nPaso 9: Copiando resultado ordenado...");
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                datosOrdenados[i][j] = salida[i][j];
            }
        }

        System.out.println("\n¡Ordenamiento completado!");
        imprimirDatosOrdenados(datosOrdenados);
        scanner.close();
    }

    private void imprimirArregloConteo(int[] conteo) {
        System.out.print("Conteo: [");
        for (int i = 0; i < conteo.length; i++) {
            System.out.print(conteo[i]);
            if (i < conteo.length - 1)
                System.out.print(", ");
        }
        System.out.println("]");
    }

    // --------------------------------------------------------------------------------------------------

    // --------------------------------------------------------------------------------------------------

    public void imprimirDatosOrdenadosPorIDHeapSort() {
        String[][] datosOrdenados = new String[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                datosOrdenados[i][j] = datos[i][j];
            }
        }

        for (int i = filas / 2 - 1; i >= 0; i--) {
            heapify(datosOrdenados, filas, i);
        }

        for (int i = filas - 1; i > 0; i--) {
            String[] temp = datosOrdenados[0];
            datosOrdenados[0] = datosOrdenados[i];
            datosOrdenados[i] = temp;

            heapify(datosOrdenados, i, 0);
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

    private void heapify(String[][] arr, int n, int i) {
        int mayor = i;
        int izquierdo = 2 * i + 1;
        int derecho = 2 * i + 2;

        if (izquierdo < n && compararIDs(arr[izquierdo][0], arr[mayor][0]) > 0) {
            mayor = izquierdo;
        }

        if (derecho < n && compararIDs(arr[derecho][0], arr[mayor][0]) > 0) {
            mayor = derecho;
        }

        if (mayor != i) {
            String[] swap = arr[i];
            arr[i] = arr[mayor];
            arr[mayor] = swap;

            heapify(arr, n, mayor);
        }
    }

    // --------------------------------------------------------------------------------------------------

    // --------------------------------------------------------------------------------------------------

    public void imprimirDatosOrdenadosPorIDHeapSortPasoaPaso() {
        Scanner scanner = new Scanner(System.in);
        String[][] datosOrdenados = new String[filas][columnas];

        System.out.println("Paso 1: Copiando datos originales...");
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                datosOrdenados[i][j] = datos[i][j];
            }
        }
        imprimirEstadoActual(datosOrdenados, "Datos copiados");
        scanner.nextLine();

        System.out.println("\nPaso 2: Construyendo el heap...");
        for (int i = filas / 2 - 1; i >= 0; i--) {
            System.out.println("  - Aplicando heapify en índice " + i + " (ID: " +
                    datosOrdenados[i][0] + ")");
            heapifyPasoaPaso(datosOrdenados, filas, i, scanner);
            imprimirEstadoActual(datosOrdenados, "Estado después de heapify en índice " +
                    i);
            scanner.nextLine();
        }

        System.out.println("\nPaso 3: Extrayendo elementos del heap...");
        for (int i = filas - 1; i > 0; i--) {
            System.out.println(
                    "  - Moviendo raíz actual (ID: " + datosOrdenados[0][0] +
                            ") al final (posición " + i + ")");

            String[] temp = datosOrdenados[0];
            datosOrdenados[0] = datosOrdenados[i];
            datosOrdenados[i] = temp;

            imprimirEstadoActual(datosOrdenados, "Después del intercambio");
            scanner.nextLine();

            System.out.println("  - Reconstruyendo heap en el arreglo reducido (tamaño "
                    + i + ")");
            heapifyPasoaPaso(datosOrdenados, i, 0, scanner);
            imprimirEstadoActual(datosOrdenados, "Heap reconstruido");
            scanner.nextLine();
        }

        System.out.println("\n¡Ordenamiento completado!");
        imprimirDatosOrdenados(datosOrdenados);
        scanner.close();
    }

    private void heapifyPasoaPaso(String[][] arr, int n, int i, Scanner scanner) {
        int mayor = i;
        int izquierdo = 2 * i + 1;
        int derecho = 2 * i + 2;

        System.out.println("    - Comparando raíz (ID: " + arr[i][0] +
                ") con hijos...");

        if (izquierdo < n) {
            System.out.println("      * Hijo izquierdo: ID " + arr[izquierdo][0]);
            if (compararIDs(arr[izquierdo][0], arr[mayor][0]) > 0) {
                mayor = izquierdo;
                System.out.println("      -> Hijo izquierdo es mayor");
            }
        }

        if (derecho < n) {
            System.out.println("      * Hijo derecho: ID " + arr[derecho][0]);
            if (compararIDs(arr[derecho][0], arr[mayor][0]) > 0) {
                mayor = derecho;
                System.out.println("      -> Hijo derecho es mayor");
            }
        }

        if (mayor != i) {
            System.out.println("    - Intercambiando " + arr[i][0] + " con " +
                    arr[mayor][0]);

            String[] swap = arr[i];
            arr[i] = arr[mayor];
            arr[mayor] = swap;

            imprimirEstadoActual(arr, "Después del intercambio en heapify");
            scanner.nextLine();

            System.out.println("    - Continuando con el subárbol afectado...");
            heapifyPasoaPaso(arr, n, mayor, scanner);
        } else {
            System.out.println("    - El heap está correcto en este nivel");
        }
    }

    // --------------------------------------------------------------------------------------------------

    // --------------------------------------------------------------------------------------------------

    public void imprimirDatosOrdenadosPorIDIntroSort() {
        String[][] datosOrdenados = new String[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                datosOrdenados[i][j] = datos[i][j];
            }
        }

        int profundidadMaxima = (int) (2 * Math.log(filas) / Math.log(2));

        introSortRecursivo(datosOrdenados, 0, filas - 1, profundidadMaxima);

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

    private void introSortRecursivo(String[][] arr, int inicio, int fin, int profundidad) {
        if (fin - inicio < 16) {
            insertionSort(arr, inicio, fin);
            return;
        }

        if (profundidad == 0) {
            heapSort(arr, inicio, fin);
            return;
        }

        int p = particionQuickSort(arr, inicio, fin);
        introSortRecursivo(arr, inicio, p - 1, profundidad - 1);
        introSortRecursivo(arr, p + 1, fin, profundidad - 1);
    }

    private void heapSort(String[][] arr, int inicio, int fin) {
        int n = fin - inicio + 1;

        for (int i = inicio + n / 2 - 1; i >= inicio; i--) {
            heapify(arr, n, inicio, i);
        }

        for (int i = fin; i > inicio; i--) {
            String[] temp = arr[inicio];
            arr[inicio] = arr[i];
            arr[i] = temp;

            heapify(arr, i - inicio, inicio, inicio);
        }
    }

    private void heapify(String[][] arr, int n, int inicio, int i) {
        int mayor = i;
        int izquierdo = 2 * (i - inicio) + 1 + inicio;
        int derecho = 2 * (i - inicio) + 2 + inicio;

        if (izquierdo < inicio + n && compararIDs(arr[izquierdo][0], arr[mayor][0]) > 0) {
            mayor = izquierdo;
        }

        if (derecho < inicio + n && compararIDs(arr[derecho][0], arr[mayor][0]) > 0) {
            mayor = derecho;
        }

        if (mayor != i) {
            String[] swap = arr[i];
            arr[i] = arr[mayor];
            arr[mayor] = swap;

            heapify(arr, n, inicio, mayor);
        }
    }

    private int particionQuickSort(String[][] arr, int inicio, int fin) {
        String[] pivote = arr[inicio + (fin - inicio) / 2];
        String idPivote = pivote[0];

        int i = inicio - 1;
        int j = fin + 1;

        while (true) {
            do {
                i++;
            } while (compararIDs(arr[i][0], idPivote) < 0);

            do {
                j--;
            } while (compararIDs(arr[j][0], idPivote) > 0);

            if (i >= j) {
                return j;
            }

            String[] temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    // --------------------------------------------------------------------------------------------------

    // --------------------------------------------------------------------------------------------------

    public void imprimirDatosOrdenadosPorIDIntroSortPasoaPaso() {
        Scanner scanner = new Scanner(System.in);
        String[][] datosOrdenados = new String[filas][columnas];

        System.out.println("Paso 1: Copiando datos originales...");
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                datosOrdenados[i][j] = datos[i][j];
            }
        }
        imprimirEstadoActual(datosOrdenados, "Datos copiados");
        scanner.nextLine();

        int profundidadMaxima = (int) (2 * Math.log(filas) / Math.log(2));
        System.out.println("\nPaso 2: Calculando profundidad máxima (" +
                profundidadMaxima + ")");
        scanner.nextLine();

        System.out.println("\nPaso 3: Iniciando ordenamiento IntroSort...");
        introSortRecursivoPasoaPaso(datosOrdenados, 0, filas - 1, profundidadMaxima,
                scanner);

        System.out.println("\n¡Ordenamiento completado!");
        imprimirDatosOrdenados(datosOrdenados);
        scanner.close();
    }

    private void introSortRecursivoPasoaPaso(String[][] arr, int inicio, int fin,
            int profundidad, Scanner scanner) {
        System.out.println("\nProcesando partición desde " + inicio + " hasta " +
                fin);
        System.out.println("Profundidad restante: " + profundidad);
        imprimirParticion(arr, inicio, fin);
        scanner.nextLine();

        if (fin - inicio < 16) {
            System.out.println("  - Partición pequeña (" + (fin - inicio + 1) +
                    " elementos), usando InsertionSort");
            insertionSortPasoaPaso(arr, inicio, fin, scanner);
            return;
        }

        if (profundidad == 0) {
            System.out.println("  - Profundidad máxima alcanzada, usando HeapSort");
            heapSortPasoaPaso(arr, inicio, fin, scanner);
            return;
        }

        System.out.println("  - Usando QuickSort para particionar");
        int p = particionQuickSortPasoaPaso(arr, inicio, fin, scanner);

        System.out.println("  - Pivote en posición: " + p);
        imprimirParticion(arr, inicio, fin);
        scanner.nextLine();

        System.out.println("  - Ordenando subpartición izquierda (" + inicio + " a "
                + (p - 1) + ")");
        introSortRecursivoPasoaPaso(arr, inicio, p - 1, profundidad - 1, scanner);

        System.out.println("  - Ordenando subpartición derecha (" + (p + 1) + " a " +
                fin + ")");
        introSortRecursivoPasoaPaso(arr, p + 1, fin, profundidad - 1, scanner);
    }

    private void heapSortPasoaPaso(String[][] arr, int inicio, int fin, Scanner scanner) {
        System.out.println("    Iniciando HeapSort...");
        int n = fin - inicio + 1;

        System.out.println("    - Construyendo heap...");
        for (int i = inicio + n / 2 - 1; i >= inicio; i--) {
            System.out.println("      * Heapify en índice " + i + " (ID: " + arr[i][0] +
                    ")");
            heapifyPasoaPaso(arr, n, inicio, i, scanner);
            imprimirParticion(arr, inicio, fin);
            scanner.nextLine();
        }

        System.out.println("    - Extrayendo elementos del heap...");
        for (int i = fin; i > inicio; i--) {
            System.out.println("      * Moviendo raíz (ID: " + arr[inicio][0] +
                    ") a posición " + i);

            String[] temp = arr[inicio];
            arr[inicio] = arr[i];
            arr[i] = temp;

            imprimirParticion(arr, inicio, fin);
            scanner.nextLine();

            System.out.println("      * Heapify en heap reducido");
            heapifyPasoaPaso(arr, i - inicio, inicio, inicio, scanner);
            imprimirParticion(arr, inicio, fin);
            scanner.nextLine();
        }
    }

    private void heapifyPasoaPaso(String[][] arr, int n, int inicio, int i,
            Scanner scanner) {
        int mayor = i;
        int izquierdo = 2 * (i - inicio) + 1 + inicio;
        int derecho = 2 * (i - inicio) + 2 + inicio;

        System.out.println("        - Comparando raíz (ID: " + arr[i][0] +
                ") con hijos...");

        if (izquierdo < inicio + n) {
            System.out.println("          * Hijo izquierdo (ID: " + arr[izquierdo][0] +
                    ")");
        }
        if (derecho < inicio + n) {
            System.out.println("          * Hijo derecho (ID: " + arr[derecho][0] + ")");
        }

        if (izquierdo < inicio + n && compararIDs(arr[izquierdo][0], arr[mayor][0]) > 0) {
            mayor = izquierdo;
            System.out.println("        - Hijo izquierdo es mayor");
        }

        if (derecho < inicio + n && compararIDs(arr[derecho][0], arr[mayor][0]) > 0) {
            mayor = derecho;
            System.out.println("        - Hijo derecho es mayor");
        }

        if (mayor != i) {
            System.out.println("        - Intercambiando " + arr[i][0] + " con " +
                    arr[mayor][0]);
            String[] swap = arr[i];
            arr[i] = arr[mayor];
            arr[mayor] = swap;

            imprimirParticion(arr, inicio, inicio + n - 1);
            scanner.nextLine();

            System.out.println("        - Continuando con el subárbol...");
            heapifyPasoaPaso(arr, n, inicio, mayor, scanner);
        } else {
            System.out.println("        - El heap ya está correcto en este nivel");
        }
    }

    private int particionQuickSortPasoaPaso(String[][] arr, int inicio, int fin,
            Scanner scanner) {
        String[] pivote = arr[inicio + (fin - inicio) / 2];
        String idPivote = pivote[0];
        System.out.println("    - Pivote seleccionado: " + idPivote + " en posición "
                + (inicio + (fin - inicio) / 2));

        int i = inicio - 1;
        int j = fin + 1;

        while (true) {
            do {
                i++;
                System.out.println("      * Avanzando índice izquierdo a " + i + " (ID: " +
                        arr[i][0] + ")");
            } while (compararIDs(arr[i][0], idPivote) < 0);

            do {
                j--;
                System.out.println("      * Retrocediendo índice derecho a " + j + " (ID: " +
                        arr[j][0] + ")");
            } while (compararIDs(arr[j][0], idPivote) > 0);

            if (i >= j) {
                System.out.println("    - Partición completada en posición " + j);
                return j;
            }

            System.out.println("    - Intercambiando elementos en " + i + " y " + j);
            System.out.println("      * " + arr[i][0] + " ↔ " + arr[j][0]);

            String[] temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;

            imprimirParticion(arr, inicio, fin);
            scanner.nextLine();
        }
    }

    // --------------------------------------------------------------------------------------------------

    // --------------------------------------------------------------------------------------------------

    public void imprimirDatosOrdenadosPorIDMergeSort() {
        String[][] datosOrdenados = new String[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                datosOrdenados[i][j] = datos[i][j];
            }
        }

        mergeSort(datosOrdenados, 0, filas - 1);

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

    private void mergeSort(String[][] arr, int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;

            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);

            merge(arr, l, m, r);
        }
    }

    private void merge(String[][] arr, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        String[][] L = new String[n1][columnas];
        String[][] R = new String[n2][columnas];

        for (int i = 0; i < n1; i++) {
            for (int j = 0; j < columnas; j++) {
                L[i][j] = arr[l + i][j];
            }
        }
        for (int i = 0; i < n2; i++) {
            for (int j = 0; j < columnas; j++) {
                R[i][j] = arr[m + 1 + i][j];
            }
        }

        int i = 0, j = 0, k = l;
        while (i < n1 && j < n2) {
            if (compararIDs(L[i][0], R[j][0]) <= 0) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    // --------------------------------------------------------------------------------------------------

    // --------------------------------------------------------------------------------------------------

    public void imprimirDatosOrdenadosPorIDMergeSortPasoaPaso() {
        Scanner scanner = new Scanner(System.in);
        String[][] datosOrdenados = new String[filas][columnas];

        System.out.println("Paso 1: Copiando datos originales...");
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                datosOrdenados[i][j] = datos[i][j];
            }
        }
        imprimirEstadoActual(datosOrdenados, "Datos copiados");
        scanner.nextLine();

        System.out.println("\nPaso 2: Iniciando ordenamiento MergeSort...");
        mergeSortPasoaPaso(datosOrdenados, 0, filas - 1, scanner);

        System.out.println("\n¡Ordenamiento completado!");
        imprimirDatosOrdenados(datosOrdenados);
        scanner.close();
    }

    private void mergeSortPasoaPaso(String[][] arr, int l, int r, Scanner scanner) {
        System.out.println("\nProcesando subarreglo desde " + l + " hasta " + r);
        imprimirParticion(arr, l, r);
        scanner.nextLine();

        if (l < r) {
            int m = l + (r - l) / 2;
            System.out.println("  - Punto medio: " + m);

            System.out.println("  - Ordenando primera mitad (" + l + " a " + m + ")");
            mergeSortPasoaPaso(arr, l, m, scanner);

            System.out.println("  - Ordenando segunda mitad (" + (m + 1) + " a " + r +
                    ")");
            mergeSortPasoaPaso(arr, m + 1, r, scanner);

            System.out.println("  - Fusionando mitades ordenadas (" + l + "-" + m +
                    ") y (" + (m + 1) + "-" + r + ")");
            mergePasoaPaso(arr, l, m, r, scanner);
        } else {
            System.out.println("  - Subarreglo de un elemento, ya está ordenado");
        }
    }

    private void mergePasoaPaso(String[][] arr, int l, int m, int r, Scanner scanner) {
        System.out.println("    Iniciando fusión...");

        int n1 = m - l + 1;
        int n2 = r - m;
        System.out.println("    - Tamaño mitad izquierda: " + n1);
        System.out.println("    - Tamaño mitad derecha: " + n2);

        String[][] L = new String[n1][columnas];
        String[][] R = new String[n2][columnas];

        System.out.println("    - Copiando mitad izquierda a temporal");
        for (int i = 0; i < n1; i++) {
            for (int j = 0; j < columnas; j++) {
                L[i][j] = arr[l + i][j];
            }
        }

        System.out.println("    - Copiando mitad derecha a temporal");
        for (int i = 0; i < n2; i++) {
            for (int j = 0; j < columnas; j++) {
                R[i][j] = arr[m + 1 + i][j];
            }
        }

        System.out.println("    - Fusionando...");
        int i = 0, j = 0, k = l;

        while (i < n1 && j < n2) {
            System.out.println("      * Comparando " + L[i][0] + " (izq) con " + R[j][0]
                    + " (der)");

            if (compararIDs(L[i][0], R[j][0]) <= 0) {
                System.out.println("        - Tomando " + L[i][0] + " de la izquierda");
                arr[k] = L[i];
                i++;
            } else {
                System.out.println("        - Tomando " + R[j][0] + " de la derecha");
                arr[k] = R[j];
                j++;
            }
            k++;

            imprimirParticion(arr, l, r);
            scanner.nextLine();
        }

        System.out.println("    - Copiando elementos restantes...");
        while (i < n1) {
            System.out.println("      * Copiando " + L[i][0] + " restante de izquierda");
            arr[k] = L[i];
            i++;
            k++;

            imprimirParticion(arr, l, r);
            scanner.nextLine();
        }

        while (j < n2) {
            System.out.println("      * Copiando " + R[j][0] + " restante de derecha");
            arr[k] = R[j];
            j++;
            k++;

            imprimirParticion(arr, l, r);
            scanner.nextLine();
        }

        System.out.println("    - Fusión completada");
        imprimirParticion(arr, l, r);
        scanner.nextLine();
    }

    // --------------------------------------------------------------------------------------------------

    // --------------------------------------------------------------------------------------------------

    public void imprimirDatosOrdenadosPorIDQuickSort() {
        String[][] datosOrdenados = new String[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                datosOrdenados[i][j] = datos[i][j];
            }
        }

        quickSort(datosOrdenados, 0, filas - 1);

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

    private void quickSort(String[][] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = particion(arr, low, high);

            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    private int particion(String[][] arr, int low, int high) {
        String[] pivote = arr[high];
        String idPivote = pivote[0];

        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (compararIDs(arr[j][0], idPivote) <= 0) {
                i++;

                String[] temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        String[] temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    // --------------------------------------------------------------------------------------------------

    // --------------------------------------------------------------------------------------------------

    public void imprimirDatosOrdenadosPorIDQuickSortPasoaPaso() {
        Scanner scanner = new Scanner(System.in);
        String[][] datosOrdenados = new String[filas][columnas];

        System.out.println("Paso 1: Copiando datos originales...");
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                datosOrdenados[i][j] = datos[i][j];
            }
        }
        imprimirEstadoActual(datosOrdenados, "Datos copiados");
        scanner.nextLine();

        System.out.println("\nPaso 2: Iniciando ordenamiento QuickSort...");
        quickSortPasoaPaso(datosOrdenados, 0, filas - 1, scanner);

        System.out.println("\n¡Ordenamiento completado!");
        imprimirDatosOrdenados(datosOrdenados);
        scanner.close();
    }

    private void quickSortPasoaPaso(String[][] arr, int low, int high, Scanner scanner) {
        System.out.println("\nProcesando subarreglo desde " + low + " hasta " +
                high);
        imprimirParticion(arr, low, high);
        scanner.nextLine();

        if (low < high) {
            System.out.println("  - Realizando partición...");
            int pivotIndex = particionPasoaPaso(arr, low, high, scanner);

            System.out.println("  - Pivote en posición correcta: " + pivotIndex +
                    " (ID: " + arr[pivotIndex][0] + ")");
            imprimirParticion(arr, low, high);
            scanner.nextLine();

            System.out.println("  - Ordenando subarreglo izquierdo (" + low + " a " +
                    (pivotIndex - 1) + ")");
            quickSortPasoaPaso(arr, low, pivotIndex - 1, scanner);

            System.out.println("  - Ordenando subarreglo derecho (" + (pivotIndex + 1) +
                    " a " + high + ")");
            quickSortPasoaPaso(arr, pivotIndex + 1, high, scanner);
        } else {
            System.out.println("  - Subarreglo de un elemento o vacío, ya está ordenado");
        }
    }

    private int particionPasoaPaso(String[][] arr, int low, int high, Scanner scanner) {
        String[] pivote = arr[high];
        String idPivote = pivote[0];
        System.out.println("    - Pivote seleccionado: " + idPivote + " (posición " +
                high + ")");

        int i = low - 1;
        System.out.println("    - Índice i inicial: " + i);

        for (int j = low; j < high; j++) {
            System.out.println("    - Comparando elemento " + j + " (ID: " + arr[j][0] +
                    ") con pivote");

            if (compararIDs(arr[j][0], idPivote) <= 0) {
                i++;
                System.out.println("      * Elemento menor o igual al pivote, incrementando i a " + i);

                System.out.println("      * Intercambiando posiciones " + i + " y " + j);
                System.out.println("        " + arr[i][0] + " ↔ " + arr[j][0]);

                String[] temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;

                imprimirParticion(arr, low, high);
                scanner.nextLine();
            } else {
                System.out.println("      * Elemento mayor al pivote, no se mueve");
            }
        }

        System.out.println("    - Colocando pivote en posición correcta: " + (i +
                1));
        System.out.println("      * Intercambiando posiciones " + (i + 1) + " y " +
                high);
        System.out.println("        " + arr[i + 1][0] + " ↔ " + arr[high][0]);

        String[] temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        imprimirParticion(arr, low, high);
        scanner.nextLine();

        return i + 1;
    }

    // --------------------------------------------------------------------------------------------------

    // --------------------------------------------------------------------------------------------------

    public void imprimirDatosOrdenadosPorIDRadixSort() {
        String[][] datosOrdenados = new String[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                datosOrdenados[i][j] = datos[i][j];
            }
        }

        int maxLength = 0;
        for (int i = 0; i < filas; i++) {
            if (datosOrdenados[i][0].length() > maxLength) {
                maxLength = datosOrdenados[i][0].length();
            }
        }

        for (int d = 0; d < maxLength; d++) {
            countingSortPorDigito(datosOrdenados, maxLength - 1 - d);
        }

        imprimirResultados(datosOrdenados);
    }

    private void countingSortPorDigito(String[][] arr, int posicionDigito) {
        final int RADIX = 10;
        int n = arr.length;
        String[][] salida = new String[n][columnas];
        int[] conteo = new int[RADIX];

        for (int i = 0; i < n; i++) {
            int digito = obtenerDigitoEnPosicionIzqaDer(arr[i][0], posicionDigito);
            conteo[digito]++;
        }

        for (int i = 1; i < RADIX; i++) {
            conteo[i] += conteo[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            int digito = obtenerDigitoEnPosicionIzqaDer(arr[i][0], posicionDigito);
            salida[conteo[digito] - 1] = arr[i];
            conteo[digito]--;
        }

        for (int i = 0; i < n; i++) {
            arr[i] = salida[i];
        }
    }

    private int obtenerDigitoEnPosicionIzqaDer(String id, int posicion) {
        if (posicion >= id.length()) {
            return 0;
        }

        char c = id.charAt(posicion);

        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        return 0;
    }

    // --------------------------------------------------------------------------------------------------

    // --------------------------------------------------------------------------------------------------

    public void imprimirDatosOrdenadosPorIDRadixSortPasoaPaso() {
        Scanner scanner = new Scanner(System.in);
        String[][] datosOrdenados = new String[filas][columnas];

        System.out.println("Paso 1: Copiando datos originales...");
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                datosOrdenados[i][j] = datos[i][j];
            }
        }
        imprimirEstadoActual(datosOrdenados, "Datos copiados");
        scanner.nextLine();

        System.out.println("\nPaso 2: Buscando ID con más dígitos...");
        int maxLength = 0;
        for (int i = 0; i < filas; i++) {
            if (datosOrdenados[i][0].length() > maxLength) {
                maxLength = datosOrdenados[i][0].length();
                System.out.println("  - Nuevo máximo encontrado: " + maxLength + " (ID: " +
                        datosOrdenados[i][0] + ")");
            }
        }
        System.out.println("  - Máxima longitud de ID: " + maxLength);
        scanner.nextLine();

        System.out.println("\nPaso 3: Ordenando por cada dígito (de derecha a izquierda)...");
        for (int d = 0; d < maxLength; d++) {
            System.out.println("\nOrdenando por dígito en posición " + d +
                    " (desde el final)");
            countingSortPorDigitoPasoaPaso(datosOrdenados, d, scanner);

            System.out.println("\nEstado después de ordenar por dígito " + d + ":");
            imprimirEstadoActual(datosOrdenados, "Parcial");
            scanner.nextLine();
        }

        System.out.println("\n¡Ordenamiento completado!");
        imprimirDatosOrdenados(datosOrdenados);
        scanner.close();
    }

    private void countingSortPorDigitoPasoaPaso(String[][] arr, int posicionDigito, Scanner scanner) {
        final int RADIX = 10;
        int n = arr.length;
        String[][] salida = new String[n][columnas];
        int[] conteo = new int[RADIX];

        System.out.println("  - Contando frecuencia de cada dígito en posición " +
                posicionDigito);

        for (int i = 0; i < n; i++) {
            int digito = obtenerDigitoEnPosicionDeraIzq(arr[i][0], posicionDigito);
            System.out.println("    * ID " + arr[i][0] + " → dígito: " + digito);
            conteo[digito]++;

            System.out.print("      Conteo: [");
            for (int k = 0; k < RADIX; k++) {
                System.out.print(conteo[k] + (k < RADIX - 1 ? ", " : ""));
            }
            System.out.println("]");
            scanner.nextLine();
        }

        System.out.println("  - Calculando posiciones acumuladas...");
        for (int i = 1; i < RADIX; i++) {
            conteo[i] += conteo[i - 1];
            System.out.println("    * conteo[" + i + "] = " + conteo[i]);
            scanner.nextLine();
        }

        System.out.println("  - Construyendo arreglo ordenado...");
        for (int i = n - 1; i >= 0; i--) {
            int digito = obtenerDigitoEnPosicionDeraIzq(arr[i][0], posicionDigito);
            System.out.println("    * ID " + arr[i][0] + " → dígito: " + digito +
                    " → posición: " + (conteo[digito] - 1));

            salida[conteo[digito] - 1] = arr[i];
            conteo[digito]--;

            imprimirEstadoActual(salida, "Arreglo parcial");
            scanner.nextLine();
        }

        System.out.println("  - Copiando arreglo ordenado...");
        for (int i = 0; i < n; i++) {
            arr[i] = salida[i];
        }
    }

    private int obtenerDigitoEnPosicionDeraIzq(String id, int posicion) {
        if (posicion >= id.length()) {
            return 0;
        }
        char c = id.charAt(id.length() - 1 - posicion);
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        return 0;
    }

    // --------------------------------------------------------------------------------------------------

    // --------------------------------------------------------------------------------------------------

    public void imprimirDatosOrdenadosPorIDSelectionSort() {
        String[][] datosOrdenados = new String[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                datosOrdenados[i][j] = datos[i][j];
            }
        }

        for (int i = 0; i < filas - 1; i++) {
            int indiceMinimo = i;
            for (int j = i + 1; j < filas; j++) {
                if (compararIDs(datosOrdenados[j][0], datosOrdenados[indiceMinimo][0]) < 0) {
                    indiceMinimo = j;
                }
            }

            if (indiceMinimo != i) {
                String[] temp = datosOrdenados[indiceMinimo];
                datosOrdenados[indiceMinimo] = datosOrdenados[i];
                datosOrdenados[i] = temp;
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

    public void imprimirDatosOrdenadosPorIDSelectionSortPasoaPaso() {
        Scanner scanner = new Scanner(System.in);
        String[][] datosOrdenados = new String[filas][columnas];

        System.out.println("Paso 1: Copiando datos originales...");
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                datosOrdenados[i][j] = datos[i][j];
            }
        }
        imprimirEstadoActual(datosOrdenados, "Datos copiados");
        scanner.nextLine();

        for (int i = 0; i < filas - 1; i++) {
            System.out.println("\nIteración " + (i + 1) +
                    ": Buscando elemento mínimo desde posición " + i);

            int indiceMinimo = i;
            System.out.println(
                    "  - Mínimo inicial: posición " + indiceMinimo + " (ID: " +
                            datosOrdenados[indiceMinimo][0] + ")");

            for (int j = i + 1; j < filas; j++) {
                System.out.println("  - Comparando con posición " + j + " (ID: " +
                        datosOrdenados[j][0] + ")");

                if (compararIDs(datosOrdenados[j][0], datosOrdenados[indiceMinimo][0]) < 0) {
                    System.out.println("    * Nuevo mínimo encontrado en posición " + j);
                    indiceMinimo = j;
                }
                scanner.nextLine();
            }

            if (indiceMinimo != i) {
                System.out.println("  - Intercambiando posiciones " + i + " y " +
                        indiceMinimo);
                System.out.println("    * " + datosOrdenados[i][0] + " ↔ " +
                        datosOrdenados[indiceMinimo][0]);

                String[] temp = datosOrdenados[indiceMinimo];
                datosOrdenados[indiceMinimo] = datosOrdenados[i];
                datosOrdenados[i] = temp;

                imprimirEstadoActual(datosOrdenados, "Después del intercambio");
            } else {
                System.out.println("  - El elemento ya está en la posición correcta");
            }
            scanner.nextLine();
        }

        System.out.println("\n¡Ordenamiento completado!");
        imprimirDatosOrdenados(datosOrdenados);
        scanner.close();
    }

    // --------------------------------------------------------------------------------------------------

    // --------------------------------------------------------------------------------------------------

    public void imprimirDatosOrdenadosPorIDTimSort() {
        String[][] datosOrdenados = new String[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                datosOrdenados[i][j] = datos[i][j];
            }
        }

        int MIN_MERGE = 32;

        for (int i = 0; i < filas; i += MIN_MERGE) {
            int fin = Math.min(i + MIN_MERGE - 1, filas - 1);
            insertionSort(datosOrdenados, i, fin);
        }

        for (int size = MIN_MERGE; size < filas; size = 2 * size) {
            for (int izq = 0; izq < filas; izq += 2 * size) {
                int medio = izq + size - 1;
                int der = Math.min(izq + 2 * size - 1, filas - 1);

                if (medio < der) {
                    mergeParaTim(datosOrdenados, izq, medio, der);
                }
            }
        }

        imprimirResultados(datosOrdenados);
    }

    private void mergeParaTim(String[][] arr, int l, int m, int r) {
        int len1 = m - l + 1, len2 = r - m;
        String[][] izq = new String[len1][columnas];
        String[][] der = new String[len2][columnas];

        for (int i = 0; i < len1; i++) {
            izq[i] = arr[l + i];
        }
        for (int i = 0; i < len2; i++) {
            der[i] = arr[m + 1 + i];
        }

        int i = 0, j = 0, k = l;
        while (i < len1 && j < len2) {
            if (compararIDs(izq[i][0], der[j][0]) <= 0) {
                arr[k++] = izq[i++];
            } else {
                arr[k++] = der[j++];
            }
        }

        while (i < len1) {
            arr[k++] = izq[i++];
        }
        while (j < len2) {
            arr[k++] = der[j++];
        }
    }

    // --------------------------------------------------------------------------------------------------

    // --------------------------------------------------------------------------------------------------

    public void imprimirDatosOrdenadosPorIDTimSortPasoaPaso() {
        Scanner scanner = new Scanner(System.in);
        String[][] datosOrdenados = new String[filas][columnas];

        System.out.println("Paso 1: Copiando datos originales...");
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                datosOrdenados[i][j] = datos[i][j];
            }
        }
        imprimirEstadoActual(datosOrdenados, "Datos copiados");
        scanner.nextLine();

        int MIN_MERGE = 32;
        System.out.println("\nPaso 2: Tamaño mínimo de run (sección): " + MIN_MERGE);
        scanner.nextLine();

        System.out.println("\nPaso 3: Ordenando runs individuales con InsertionSort...");
        for (int i = 0; i < filas; i += MIN_MERGE) {
            int fin = Math.min(i + MIN_MERGE - 1, filas - 1);
            System.out.println("  - Ordenando run desde " + i + " hasta " + fin);
            insertionSortPasoaPaso(datosOrdenados, i, fin, scanner);
        }

        System.out.println("\nPaso 4: Fusionando runs ordenadas...");
        for (int size = MIN_MERGE; size < filas; size = 2 * size) {
            System.out.println("\nTamaño actual de run: " + size);
            for (int izq = 0; izq < filas; izq += 2 * size) {
                int medio = izq + size - 1;
                int der = Math.min(izq + 2 * size - 1, filas - 1);

                if (medio < der) {
                    System.out.println(
                            "  - Fusionando desde " + izq + " a " + medio + " con " + (medio + 1) + " a " + der);
                    mergePasoaPasoParaTim(datosOrdenados, izq, medio, der, scanner);
                } else {
                    System.out.println("  - Run demasiado pequeña para fusionar: " + izq + " a " + der);
                }
                scanner.nextLine();
            }
        }

        System.out.println("\n¡Ordenamiento completado!");
        imprimirDatosOrdenados(datosOrdenados);
        scanner.close();
    }

    private void mergePasoaPasoParaTim(String[][] arr, int l, int m, int r, Scanner scanner) {
        System.out.println("    Iniciando fusión...");

        int len1 = m - l + 1, len2 = r - m;
        String[][] izq = new String[len1][columnas];
        String[][] der = new String[len2][columnas];

        System.out.println("    - Copiando mitad izquierda (" + len1 + " elementos)");
        for (int i = 0; i < len1; i++) {
            izq[i] = arr[l + i];
        }

        System.out.println("    - Copiando mitad derecha (" + len2 + " elementos)");
        for (int i = 0; i < len2; i++) {
            der[i] = arr[m + 1 + i];
        }

        System.out.println("    - Fusionando...");
        int i = 0, j = 0, k = l;

        while (i < len1 && j < len2) {
            System.out.println("      * Comparando " + izq[i][0] + " (izq) con " + der[j][0] + " (der)");

            if (compararIDs(izq[i][0], der[j][0]) <= 0) {
                System.out.println("        - Tomando " + izq[i][0] + " de la izquierda");
                arr[k++] = izq[i++];
            } else {
                System.out.println("        - Tomando " + der[j][0] + " de la derecha");
                arr[k++] = der[j++];
            }

            imprimirParticion(arr, l, r);
            scanner.nextLine();
        }

        System.out.println("    - Copiando elementos restantes...");
        while (i < len1) {
            System.out.println("      * Copiando " + izq[i][0] + " restante de izquierda");
            arr[k++] = izq[i++];

            imprimirParticion(arr, l, r);
            scanner.nextLine();
        }

        while (j < len2) {
            System.out.println("      * Copiando " + der[j][0] + " restante de derecha");
            arr[k++] = der[j++];

            imprimirParticion(arr, l, r);
            scanner.nextLine();
        }

        System.out.println("    - Fusión completada");
    }

    // --------------------------------------------------------------------------------------------------

    // --------------------------------------------------------------------------------------------------

    // Desde acá inician los métodos que se usan en varios métodos de ordenamiento

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

    // --------------------------------------------------------------------------------------------------

    private int convertirIDaValor(String id) {
        int valor = 0;
        for (int i = 0; i < id.length(); i++) {
            valor += id.charAt(i);
        }
        return valor;
    }

    // --------------------------------------------------------------------------------------------------

    private void imprimirEstadoActual(String[][] datos, String mensaje) {
        System.out.println("\n" + mensaje + ":");
        for (int i = 0; i < cabeceras.length; i++) {
            System.out.printf("%-15s", cabeceras[i]);
        }
        System.out.println("\n" + "-".repeat(60));

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.printf("%-15s", datos[i][j] != null ? datos[i][j] : "");
            }
            System.out.println();
        }
    }

    // --------------------------------------------------------------------------------------------------

    private void imprimirParticion(String[][] arr, int inicio, int fin) {
        System.out.print("Partición actual [");
        for (int i = inicio; i <= fin; i++) {
            System.out.print(arr[i][0]);
            if (i < fin)
                System.out.print(", ");
        }
        System.out.println("]");
    }

    // --------------------------------------------------------------------------------------------------

    private void imprimirResultados(String[][] datosOrdenados) {
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

    private void insertionSort(String[][] arr, int inicio, int fin) {
        for (int i = inicio + 1; i <= fin; i++) {
            String[] filaActual = arr[i];
            String idActual = filaActual[0];
            int j = i - 1;

            while (j >= inicio && compararIDs(arr[j][0], idActual) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = filaActual;
        }
    }

    // --------------------------------------------------------------------------------------------------

    private void insertionSortPasoaPaso(String[][] arr, int inicio, int fin, Scanner scanner) {
        System.out.println("    Iniciando InsertionSort...");
        for (int i = inicio + 1; i <= fin; i++) {
            String[] filaActual = arr[i];
            String idActual = filaActual[0];
            int j = i - 1;

            System.out.println("    - Insertando elemento " + i + " (ID: " + idActual +
                    ")");

            while (j >= inicio && compararIDs(arr[j][0], idActual) > 0) {
                System.out.println("      * Moviendo elemento " + j + " (ID: " + arr[j][0] +
                        ") a posición " + (j + 1));
                arr[j + 1] = arr[j];
                j--;

                imprimirParticion(arr, inicio, fin);
                scanner.nextLine();
            }

            System.out.println("    - Insertando en posición " + (j + 1));
            arr[j + 1] = filaActual;

            imprimirParticion(arr, inicio, fin);
            scanner.nextLine();
        }
    }
}