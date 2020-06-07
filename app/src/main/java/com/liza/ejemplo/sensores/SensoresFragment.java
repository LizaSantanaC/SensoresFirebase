package com.liza.ejemplo.sensores;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import mysensor.MySensor;

public class SensoresFragment extends Fragment {


  private static final String TITULO = "titulo";

  public static SensoresFragment newInstance(String text) {
    SensoresFragment fragment = new SensoresFragment();

    Bundle args = new Bundle();
    args.putString(TITULO, text);
    fragment.setArguments(args);

    return fragment;
  }

  //Declaracion de las variables
  private TextView txtAcelerometro;
  private TextView txtNivelLuz;
  private TextView txtGravedad;
  private TextView txtProximidad;
  //Grafica
  private TextView txtNivelLuzGrafica;
  private CircularProgressBar grafica;

  //Instancia de la base de datos
  private DatabaseReference mDatabase;

  //SensorManager
  private SensorManager sensorManager;

  //Formato
  DecimalFormat formato= new DecimalFormat(",#,##");
  DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

  public static final String BASE_DE_DATOS= "Registro_sensores";

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.sensores_fragment, container, false);
    mDatabase = FirebaseDatabase.getInstance().getReference();
    configSensores();
    configViews(root);
    return root;
  }

  private void configSensores(){
    sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
    sensorAcelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    sensorGravedad=  sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
    sensorluz= sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    sensorproximidad= sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
  }

  private void configViews(View root){
    txtAcelerometro= root.findViewById(R.id.txtAcelerometro);
    txtNivelLuz= root.findViewById(R.id.txtNivelLuz);
    txtGravedad= root.findViewById(R.id.txtGravedad);
    txtProximidad= root.findViewById(R.id.txtProximidad);
    //Grafica
    txtNivelLuzGrafica= root.findViewById(R.id.txtNivelLuzGrafica);
    grafica= root.findViewById(R.id.progressBar);
  }

  //Sensor de acelerometro
  private Sensor sensorAcelerometro;
  private SensorEventListener oyenteAcelerometro= new SensorEventListener() {

    @Override
    public void onSensorChanged(SensorEvent event) {
      float x=event.values[0];
      float y=event.values[1];
      float z=event.values[2];

      MySensor mySensor = new MySensor();
      mySensor.setNombre("Sensor acelerometro");
      mySensor.setId(mySensor.getNombre());
      mySensor.setValor(formato.format(x)+" m/s, " + formato.format(y) +" m/s, " + formato.format(z) +" m/s ");

      txtAcelerometro.setText(mySensor.getValor());
      mDatabase.child(BASE_DE_DATOS).child(mySensor.getId()).setValue(mySensor);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
  };

  //Sensor de nivel de luz
  private Sensor sensorluz;
  private SensorEventListener oyenteLuz= new SensorEventListener() {

    @Override
    public void onSensorChanged(SensorEvent event) {
      float luz= event.values[0];
      MySensor mySensor = new MySensor();
      mySensor.setNombre("Sensor de luz");
      mySensor.setId(mySensor.getNombre());
      mySensor.setValor(luz+" lx");
      //Valor de grafica
      grafica.setProgressWithAnimation(luz, (long) 2000);
      txtNivelLuzGrafica.setText(mySensor.getValor());
      txtNivelLuz.setText(mySensor.getValor());
      mDatabase.child(BASE_DE_DATOS).child(mySensor.getId()).setValue(mySensor);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
  };

  //Sensor de gravedad
  private Sensor sensorGravedad;
  private SensorEventListener oyenteGravedad= new SensorEventListener() {

    @Override
    public void onSensorChanged(SensorEvent event) {
      float x=event.values[0];
      float y=event.values[1];
      float z=event.values[2];

      MySensor mySensor = new MySensor();
      mySensor.setNombre("Sensor de gravedad");
      mySensor.setId(mySensor.getNombre());
      mySensor.setValor(formato.format(x)+" m/s, " + formato.format(y) +" m/s, " + formato.format(z) +" m/s ");

      txtGravedad.setText(mySensor.getValor());
      mDatabase.child(BASE_DE_DATOS).child(mySensor.getId()).setValue(mySensor);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
  };

  //Sensor de nivel de proximidad
  private Sensor sensorproximidad;
  private SensorEventListener oyenteProximidad= new SensorEventListener() {

    @Override
    public void onSensorChanged(SensorEvent event) {
      float distancia= event.values[0];
      MySensor mySensor = new MySensor();
      mySensor.setNombre("Sensor de proximidad");
      mySensor.setId(mySensor.getNombre());
      mySensor.setValor(distancia + "cm");

      txtProximidad.setText(mySensor.getValor());
      mDatabase.child(BASE_DE_DATOS).child(mySensor.getId()).setValue(mySensor);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
  };

  @Override
  public void onResume() {
    // Register a listener for the sensor.
    super.onResume();
    sensorManager.registerListener(oyenteAcelerometro, sensorAcelerometro, SensorManager.SENSOR_DELAY_NORMAL);
    sensorManager.registerListener(oyenteLuz, sensorluz, SensorManager.SENSOR_DELAY_NORMAL);
    sensorManager.registerListener(oyenteGravedad, sensorGravedad, SensorManager.SENSOR_DELAY_NORMAL);
    sensorManager.registerListener(oyenteProximidad, sensorproximidad, SensorManager.SENSOR_DELAY_NORMAL);
  }

  @Override
  public void onPause() {
    // Be sure to unregister the sensor when the activity pauses.
    super.onPause();
    sensorManager.unregisterListener(oyenteAcelerometro);
    sensorManager.unregisterListener(oyenteLuz);
    sensorManager.unregisterListener(oyenteGravedad);
    sensorManager.unregisterListener(oyenteProximidad);

  }
}

