package es.uniovi.eii.mainantojitos.modelo;

public class Reseña {

    private String id;
    private String email;
    private String reseña;
    private float rating;

    public Reseña(String id, String email, String reseña, float rating) {
        this.id = id;
        this.email = email;
        this.reseña = reseña;
        this.rating = rating;
    }

    public Reseña() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReseña() {
        return reseña;
    }

    public void setReseña(String reseña) {
        this.reseña = reseña;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
