package es.uniovi.eii.mainantojitos.modelo;

public class Reseña {

    private String id;
    private String email;
    private String reseña;
    private double rating;

    public Reseña(String id, String email, String reseña, double rating) {
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
