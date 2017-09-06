package frsf.isi.grupojf.lab01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    int cantDias=0;
    int importe=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String mensajeGanancia = getString(R.string.textoResultado, 0.0);
        TextView mensajeGananciaText = (TextView) findViewById(R.id.resultado);
        mensajeGananciaText.setText(mensajeGanancia);
        TextView successText = (TextView) findViewById(R.id.textoMensajeFinal);
        successText.setVisibility(View.INVISIBLE);

        SeekBar seekBarDia = (SeekBar) findViewById(R.id.seekBar);
        seekBarDia.setOnSeekBarChangeListener(this);

        Button botonPlazo = (Button) findViewById(R.id.boton);
        botonPlazo.setOnClickListener(this);
    }


    //EVENTOS
    @Override
    public void onClick(View view) {

        double valor = mostrarGanancias();
        Log.v("valor", "valor: " + valor);
        valor += importe;
        String mensaje = getString(R.string.mensajeFinal, valor);
        TextView successText = (TextView) findViewById(R.id.textoMensajeFinal);
        successText.setText(mensaje);
        successText.setTextColor(getResources().getColor(R.color.colorSuccess,null));
        successText.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        cantDias = i;
        TextView diasText = (TextView) findViewById(R.id.cantidadDias);
        diasText.setText(String.valueOf(cantDias));
        mostrarGanancias();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    //CALCULO DE GANANCIA
    private double mostrarGanancias(){
        EditText campoImporte = (EditText) findViewById(R.id.campoImporte);
        try {
            importe = Integer.parseInt(campoImporte.getText().toString());
        }
        catch (NumberFormatException e){
            importe = 0;
        }
        double valor = calcularImporte(importe,cantDias);
        TextView gananciaTexto = (TextView) findViewById(R.id.resultado);
        String mensajeGananciaNum = getString(R.string.textoResultado, valor);
        gananciaTexto.setText(mensajeGananciaNum);
        gananciaTexto.setVisibility(View.VISIBLE);
        TextView successText = (TextView) findViewById(R.id.textoMensajeFinal);
        successText.setVisibility(View.INVISIBLE);
        return valor;

    }

    private double calcularImporte(int importe, int cantDias) {
        int techo1 = Integer.parseInt(getResources().getString(R.string.techo1));
        int techo2 = Integer.parseInt(getResources().getString(R.string.techo2));
        int dias = Integer.parseInt(getResources().getString(R.string.dias));
        float tasa1 = Float.parseFloat(getResources().getString(R.string.tasa1));
        float tasa2 = Float.parseFloat(getResources().getString(R.string.tasa2));
        float tasa3 = Float.parseFloat(getResources().getString(R.string.tasa3));
        float tasa4 = Float.parseFloat(getResources().getString(R.string.tasa4));
        float tasa5 = Float.parseFloat(getResources().getString(R.string.tasa5));
        float tasa6 = Float.parseFloat(getResources().getString(R.string.tasa6));

        if (importe<techo1){
            if(cantDias<dias){
                return calcularImporteAux(tasa1,importe,cantDias);
            }
            else{
                return calcularImporteAux(tasa2,importe,cantDias);
            }
        }
        else if (importe>techo1 && importe<techo2){
            if(cantDias<dias){
                return calcularImporteAux(tasa3,importe,cantDias);
            }
            else{
                return calcularImporteAux(tasa4,importe,cantDias);
            }
        }
        else {
            if(cantDias<dias){
                return calcularImporteAux(tasa5,importe,cantDias);
            }
            else{
                return calcularImporteAux(tasa6,importe,cantDias);
            }
        }
    }

    private double calcularImporteAux(float tasa, int importe, int cantDias) {
        return importe*((Math.pow(1+(tasa/100.0),(cantDias/360.0))-1));
    }


}