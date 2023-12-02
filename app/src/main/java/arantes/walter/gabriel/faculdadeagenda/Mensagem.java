package arantes.walter.gabriel.faculdadeagenda;

import android.app.Activity;

import androidx.appcompat.app.AlertDialog;

public class Mensagem {

    public static void mostrar(String txt, Activity act) {// Método de msg de alerta
        AlertDialog.Builder adb= new AlertDialog.Builder(act);
        adb.setMessage(txt);
        adb.setNeutralButton("Ok", null);
        adb.show();
    }
}
