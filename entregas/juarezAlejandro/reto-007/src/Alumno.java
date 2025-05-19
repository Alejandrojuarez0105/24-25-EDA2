public class Alumno {
    private String matricula;
    private String nombre;
    private String apellido;
    private String curso;
    private double calificacion;

    public Alumno(String matricula, String nombre, String apellido, String curso, double calificacion) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.curso = curso;
        this.calificacion = calificacion;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    public String getCursoAsignado() {
        return curso;
    }

    public double getCalificacion() {
        return calificacion;
    }
}
