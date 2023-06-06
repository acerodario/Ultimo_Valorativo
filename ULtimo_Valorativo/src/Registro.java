import java.io.Serializable;

class Registro implements Serializable {
    private String nombre;
    private String apellido;
    private String correo;
    private String carrera;
    private String gustos;

    public Registro(String nombre, String apellido, String correo, String carrera, String gustos) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.carrera = carrera;
        this.gustos = gustos;
    }

    // Getter y Setter

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getGustos() {
        return gustos;
    }

    public void setGustos(String gustos) {
        this.gustos = gustos;
    }
}