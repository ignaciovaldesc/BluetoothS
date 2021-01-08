package com.example.servicio_1;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    String Mensaje;
    EditText Frase;

    Button Save;
    Button Conect;
    //private final BroadcastReceiver bReceiver = new BroadcastReceiver();

    private final static int REQUEST_ENABLE_BT = 1;

    private ListView listado;
    private ArrayList<String> names;
    private ArrayAdapter<String> adapter;
    private static final String TAG = "MyActivity";



    @Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    listado = (ListView) findViewById(R.id.listado);

    Frase = (EditText) findViewById(R.id.Frase);

    Save = (Button) findViewById(R.id.Save);

    //Registrar un broadcasts cuando descubre un dispositivo
    IntentFilter filtrar = new IntentFilter((BluetoothDevice.ACTION_FOUND));
    registerReceiver(receiver , filtrar);


    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    if (bluetoothAdapter == null) {
        //Dispositivo no tiene soporte bluetooth

    }
    //Dispositivo Compatible con Bluetooth
    if (!bluetoothAdapter.isEnabled()) {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }

    Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

    if (pairedDevices.size() > 0) {

        //
        names = new ArrayList<String>();
        //telefo= new ArrayList<String>();
        for (BluetoothDevice device : pairedDevices) {
            String NombreDispositivo = device.getName();
            String DireccionMAC = device.getAddress(); // Direccion MAC
            String nuevo = NombreDispositivo + " " + DireccionMAC;
            names.add(nuevo);

            adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,names);
            //showToast(nuevo);
            listado.setAdapter(adapter);
        }





        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Mensaje = Frase.getText().toString();
                showToast(Mensaje);
            }
        });

        Conect = (Button) findViewById(R.id.Conect);
        Conect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(bluetoothAdapter.isDiscovering()){
                    //El bluetooth esta en modo descubir
                    bluetoothAdapter.cancelDiscovery();
                }
                bluetoothAdapter.startDiscovery();
                //showToast(bluetoothAdapter.getAddress());

                //adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
                //adaptador1=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,);

            }

        });
    }
}
    private void showToast (String text){
        String Notificacion = "El mensaje guardado es: " + text;
        Toast.makeText(MainActivity.this, Notificacion, Toast.LENGTH_LONG).show();
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                Log.i(TAG, "Dispositivo encontrado: " + device.getName() + "; MAC " + device.getAddress());
                String nombre = deviceHardwareAddress +" "+deviceName+"holamundo";
                showToast("estoy aqui");
            }
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(receiver);
    }

}


