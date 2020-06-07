package com.liza.ejemplo.sensores;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import mysensor.MySensor;

public class HistorialFragment extends Fragment implements ValueEventListener {

    //Instancia de la base de datos
    private DatabaseReference mDatabase;

    //Declaracion de las variables
    private TextView txtAcelerometro;
    private TextView txtNivelLuz;
    private TextView txtGravedad;
    private TextView txtProximidad;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.historial_fragment, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //Referencia a la base de fatos de firebase
        Query mQuery= mDatabase.child(SensoresFragment.BASE_DE_DATOS);
        mQuery.addValueEventListener(this); //Hace refrencia a la clase
        configViews(root);
        return root;
    }

    private void configViews(View root) {
        txtAcelerometro= root.findViewById(R.id.txtAcelerometroHistorial);
        txtNivelLuz= root.findViewById(R.id.txtNivelLuzHistorial);
        txtGravedad= root.findViewById(R.id.txtGravedadHistorial);
        txtProximidad= root.findViewById(R.id.txtProximidadHistorial);
    }

    @Override
    //Cada que haya cambios en la base de datos Registro sensores, se mandara a llamar este metodo
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        for (DataSnapshot registro: dataSnapshot.getChildren()){
            //Se mandan los regisstros d ela base de datos y se recorre cada elemento o sea el sensor
            MySensor sensor= registro.getValue(MySensor.class);

            //COmpara el id
            switch(sensor.getId()){
                case"Sensor acelerometro":
                    txtAcelerometro.setText(sensor.getValor());
                    break;
                case"Sensor de luz":
                    txtNivelLuz.setText(sensor.getValor());
                    break;
                case"Sensor de gravedad":
                    txtGravedad.setText(sensor.getValor());
                    break;
                case"Sensor de proximidad":
                    txtProximidad.setText(sensor.getValor());
                    break;
            }
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}