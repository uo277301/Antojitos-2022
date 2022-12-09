package es.uniovi.eii.mainantojitos.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Restaurante implements Parcelable {

    String nombre;
    String telefono;
    Categoria categoria;
    String descripcion;
    String carta;
    String urlFoto;
    int drawImg;
    int imagenCarta;

//    public Restaurante(String nombre, String telefono, Categoria categoria, String descripcion, String carta,
//                       String urlFoto) {
//        this.nombre = nombre;
//        this.telefono = telefono;
//        this.categoria = categoria;
//        this.descripcion = descripcion;
//        this.carta = carta;
//        this.urlFoto = urlFoto;
//    }

//    public Restaurante(String nombre, String telefono, Categoria categoria, String descripcion, String carta,int imagen) {
//        this.nombre = nombre;
//        this.telefono = telefono;
//        this.categoria = categoria;
//        this.descripcion = descripcion;
//        this.carta = carta;
//        this.drawImg = imagen;
//    }

//    public Restaurante(String nombre, String telefono, String descripcion, String carta,int imagen) {
//        this.nombre = nombre;
//        this.telefono = telefono;
//        this.descripcion = descripcion;
//        this.carta = carta;
//        this.drawImg = imagen;
//        this.categoria = null;
//
//    }

//    public Restaurante(String nombre, String telefono, Categoria categoria, String descripcion, String carta,int imagen, String urlFoto) {
//        this.nombre = nombre;
//        this.telefono = telefono;
//        this.categoria = categoria;
//        this.descripcion = descripcion;
//        this.carta = carta;
//        this.drawImg = imagen;
//        this.urlFoto = urlFoto;
//    }

    protected Restaurante(Parcel in){
        this.nombre = in.readString();
        this.telefono = in.readString();
//        this.categoria = in.readParcelable(Categoria.class.getClassLoader());
        this.descripcion = in.readString();
        this.carta = in.readString();
        this.drawImg = in.readInt();
        this.imagenCarta = in.readInt();
    }

//    public Restaurante(String nombre, String telefono, String descripcion, String carta,
//                       int imagen, String urlFoto, int imagenCarta) {
//        this.nombre = nombre;
//        this.telefono = telefono;
//        this.categoria = categoria;
//        this.descripcion = descripcion;
//        this.imagenCarta = imagenCarta;
//        this.drawImg = imagen;
//        this.urlFoto = urlFoto;
//    }

    public Restaurante(String nombre, String telefono, String cat, String descripcion, int imagen, int imagenCarta) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.descripcion = descripcion;
        this.imagenCarta = imagenCarta;
        this.drawImg = imagen;

    }


//    public Restaurante(String nombre, String telefono, Categoria categoria, String descripcion, String s) {
//        this.nombre = nombre;
//        this.telefono = telefono;
//        this.categoria = categoria;
//        this.descripcion = descripcion;
//    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCarta() {
        return carta;
    }

    public void setCarta(String carta) {
        this.carta = carta;
    }

    public int getImagenCarta() {
        return imagenCarta;
    }

    public void setImagenCarta(int imagenCarta) {
        this.imagenCarta = imagenCarta;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(telefono);
        dest.writeString(descripcion);
//        dest.writeParcelable(categoria, flags);
        dest.writeString(carta);
        dest.writeInt(drawImg);
        dest.writeInt(imagenCarta);
    }

    public static final Creator<Restaurante> CREATOR = new Creator<Restaurante>() {
        @Override
        public Restaurante createFromParcel(Parcel in) {
            return new Restaurante(in);
        }

        @Override
        public Restaurante[] newArray(int size) {
            return new Restaurante[size];
        }
    };

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public int getDrawImg() {
        return drawImg;
    }

    public void setDrawImg(int drawImg) {
        this.drawImg = drawImg;
    }

    @Override
    public String toString() {
        return "Restaurante{" +
                "nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", categoria=" + categoria +
                ", descripcion='" + descripcion + '\'' +
                ", Imagencarta='" + imagenCarta + '\'' +
                '}';
    }
}
