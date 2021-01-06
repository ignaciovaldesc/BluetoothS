package com.example.servicio_1;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    String Mensaje;
    EditText Frase;

    Button Save;
    Button Conect;
    private final static int REQUEST_ENABLE_BT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Frase = (EditText)findViewById(R.id.Frase);

        Save = (Button)findViewById(R.id.Save);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Mensaje = Frase.getText().toString();
                showToast(Mensaje);
            }
        });

        Conect = (Button)findViewById(R.id.Conect);
        Conect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if(bluetoothAdapter == null){
                    //Dispositivo no tiene soporte bluetooth

                }
                //Dispositivo Compatible con Bluetooth
                if(!bluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }
                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

                if (pairedDevices.size() > 0) {

                    //

                    for(BluetoothDevice device : pairedDevices){
                        String NombreDispositivo = device.getName();
                        String DireccionMAC = device.getAddress(); // Direccion MAC
                        String nuevo = NombreDispositivo+" "+DireccionMAC;
                        showToast(nuevo);
                    }

                }
            }
        });
    }

    private void showToast(String text){
        String Notificacion = "El mensaje guardado es: "+text;
        Toast.makeText(MainActivity.this, Notificacion, Toast.LENGTH_LONG).show();
    }
}


