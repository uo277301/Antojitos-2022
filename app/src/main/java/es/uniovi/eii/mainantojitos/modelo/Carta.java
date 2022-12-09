package es.uniovi.eii.mainantojitos.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Carta implements Parcelable {

    private ArrayList<String> productos;

    public Carta(ArrayList<String> productos) {
        this.productos = productos;
    }

    protected Carta(Parcel in) {
        productos = in.createStringArrayList();
    }

    public static final Creator<Carta> CREATOR = new Creator<Carta>() {
        @Override
        public Carta createFromParcel(Parcel in) {
            return new Carta(in);
        }

        @Override
        public Carta[] newArray(int size) {
            return new Carta[size];
        }
    };

    public ArrayList<String> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<String> productos) {
        this.productos = productos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
       for(String prod:productos)
           dest.writeString(prod);
    }
}
