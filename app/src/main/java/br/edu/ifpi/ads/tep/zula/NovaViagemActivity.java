package br.edu.ifpi.ads.tep.zula;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import br.edu.ifpi.ads.tep.zula.dominio.modelo.TipoViagemEnum;

public class NovaViagemActivity extends AppCompatActivity {

    private EditText edtDestino;
    private EditText edtData;
    private Spinner spnTipoViagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_viagem);

//        ActionBar actionBar = getActionBar();
//        actionBar.setTitle("Nova Viagem");
//        actionBar.setDisplayShowHomeEnabled(true);

        spnTipoViagem = (Spinner) findViewById(R.id.spn_tipo_viagem);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, TipoViagemEnum.getTipos());
        spnTipoViagem.setAdapter(spinnerAdapter);

    }
}
