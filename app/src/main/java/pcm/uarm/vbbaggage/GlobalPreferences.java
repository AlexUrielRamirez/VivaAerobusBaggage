package pcm.uarm.vbbaggage;

public class GlobalPreferences {
    public static class ModelBag{
        public String Id;
        public String NumeroVuelo;
        public String Nombre;
        public String Status;

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getNumeroVuelo() {
            return NumeroVuelo;
        }

        public void setNumeroVuelo(String numeroVuelo) {
            NumeroVuelo = numeroVuelo;
        }

        public String getNombre() {
            return Nombre;
        }

        public void setNombre(String nombre) {
            Nombre = nombre;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }
    }

    public static ModelBag model;

}
