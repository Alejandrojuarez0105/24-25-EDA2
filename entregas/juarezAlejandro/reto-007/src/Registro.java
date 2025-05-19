public class Registro {
    private Alumno[] listaAlumnos;
    private String identificador;

    public Registro(Alumno[] listaAlumnos) {
        this.listaAlumnos = listaAlumnos;
        this.identificador = crearHashUnico();
    }

    private String crearHashUnico() {
        long acumulador = 11;
        for (Alumno alumno : listaAlumnos) {
            String datos = alumno.getMatricula() + alumno.getNombreCompleto() + alumno.getCursoAsignado()
                    + alumno.getCalificacion();
            for (int i = 0; i < datos.length(); i++) {
                acumulador = acumulador * 37 + datos.charAt(i);
            }
        }
        return Long.toHexString(acumulador);
    }

    public String obtenerIdentificador() {
        return identificador;
    }
}
