package es.uniovi.eii.mainantojitos.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Categoria implements Parcelable {

    String nombre;
    String descripcionBasica;

    public Categoria(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcionBasica = descripcion;
    }

    protected Categoria(Parcel in) {
        nombre = in.readString();
        descripcionBasica = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(descripcionBasica);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Categoria> CREATOR = new Creator<Categoria>() {
        @Override
        public Categoria createFromParcel(Parcel in) {
            return new Categoria(in);
        }

        @Override
        public Categoria[] newArray(int size) {
            return new Categoria[size];
        }
    };

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcionBasica() {
        return descripcionBasica;
    }

    public void setDescripcionBasica(String descripcionBasica) {
        this.descripcionBasica = descripcionBasica;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "nombre='" + nombre + '\'' +
                ", descripcionBasica='" + descripcionBasica + '\'' +
                '}';
    }
}
