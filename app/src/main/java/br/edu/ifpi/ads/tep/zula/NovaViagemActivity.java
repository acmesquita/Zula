package br.edu.ifpi.ads.tep.zula;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;

import br.edu.ifpi.ads.tep.zula.dominio.modelo.TipoViagemEnum;
import br.edu.ifpi.ads.tep.zula.dominio.modelo.Viagem;
import br.edu.ifpi.ads.tep.zula.util.UtilsData;

public class NovaViagemActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtDestino;
    private EditText edtData;
    private Spinner spnTipoViagem;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_viagem);

//        ActionBar actionBar = getActionBar();
//        actionBar.setTitle("Nova Viagem");
//        actionBar.setDisplayShowHomeEnabled(true);

        spnTipoViagem = (Spinner) findViewById(R.id.spn_tipo_viagem);
        button = (Button) findViewById(R.id.button) ;
        button.setOnClickListener(this);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, TipoViagemEnum.getTipos());
        spnTipoViagem.setAdapter(spinnerAdapter);

    }

    @Override
    public void onClick(View v) {
        Viagem viagem = new Viagem();
        edtData = (EditText) findViewById(R.id.edt_data_viagem);
        edtDestino = (EditText) findViewById(R.id.edtDestino);

        try {
            viagem.setData(UtilsData.parseForDate(edtData.getText().toString()));
            viagem.setDestino(edtDestino.getText().toString());
            viagem.setTipoViagem(TipoViagemEnum.valueOf((String) spnTipoViagem.getSelectedItem()));
        } catch (ParseException e) {
            Toast.makeText(this, "Erro na conversão de data", Toast.LENGTH_SHORT);
            e.printStackTrace();
            setResult(RESULT_CANCELED);
            finish();
        }

        Intent intent = new Intent();
        intent.putExtra("VIAGEM", viagem);
        setResult(RESULT_OK, intent);
        finish();

    }
}
