public class App {
    public static void main(String[] args) {
        Alumno[] grupo = {
                new Alumno("B101", "Alejandro", "Juárez", "EDA2", 7.8),
                new Alumno("B102", "Andrés", "Gatica", "Biología", 9.2)
        };

        Registro original = new Registro(grupo);
        System.out.println("Identificador inicial: " + original.obtenerIdentificador());

        grupo[0] = new Alumno("B101", "Alejandro", "Juárez", "EDA2", 10.0);

        Registro modificado = new Registro(grupo);
        System.out.println("Identificador actualizado: " + modificado.obtenerIdentificador());

        if (!original.obtenerIdentificador().equals(modificado.obtenerIdentificador())) {
            System.out.println("¡El registro fue alterado!");
        } else {
            System.out.println("No se detectaron cambios en el registro.");
        }
    }
}
