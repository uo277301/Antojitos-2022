package es.uniovi.eii.mainantojitos.conexion;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Conexion {

    private Context mContexto;

    public Conexion(Context mContext){
        mContexto = mContext;
    }

    public boolean CompruebaConexion(){
        boolean conectado =false;

        ConnectivityManager connectivityManager = (ConnectivityManager) mContexto.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activateNetwork = connectivityManager.getActiveNetworkInfo();
        conectado = activateNetwork != null && activateNetwork.isConnectedOrConnecting();

        return conectado;
    }
}