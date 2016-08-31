package br.edu.ifpi.ads.tep.zula;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import br.edu.ifpi.ads.tep.zula.dominio.dao.DAO;
import br.edu.ifpi.ads.tep.zula.dominio.modelo.TipoViagemEnum;
import br.edu.ifpi.ads.tep.zula.dominio.modelo.Viagem;
import br.edu.ifpi.ads.tep.zula.util.UtilsData;
import io.realm.Realm;

public class NovaViagemActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtDestino;
    private EditText edtData;
    private Spinner spnTipoViagem;
    private Button button;
    private Viagem viagem;
    private DAO dao;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_viagem);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Nova Viagem");
        actionBar.dispatchMenuVisibilityChanged(true);

        viagem = new Viagem();
        edtDestino = (EditText) findViewById(R.id.edtDestino);
        spnTipoViagem = (Spinner) findViewById(R.id.spn_tipo_viagem);
        button = (Button) findViewById(R.id.button) ;
        button.setOnClickListener(this);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, TipoViagemEnum.getTipos());
        spnTipoViagem.setAdapter(spinnerAdapter);

        edtData = (EditText) findViewById(R.id.edt_data_viagem);
        ExibeDataListener listener = new ExibeDataListener();
        edtData.setOnClickListener(listener);
        edtData.setOnFocusChangeListener(listener);
        edtData.setKeyListener(null);/*Evitar que o campo seja editado manualmente. - Retitou o teclado.*/


        String idViagem = getIntent().getStringExtra("VIAGEM");
        if(idViagem != null) {
            dao = DAO.getInstance(Realm.getDefaultInstance());
            this.viagem = dao.getViagemById(idViagem);
            preencherDados();

        }
    }

    @Override
    public void onClick(View v) {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        try {
            if (edtDestino.getText().toString().trim() != "") {
                viagem.setDestino(edtDestino.getText().toString());
            }
            else{
                /*Alert*/
            }
            if (edtData.getText().toString().trim() != ""){
                viagem.setData(UtilsData.parseForDate(edtData.getText().toString()));
            }
            else{
                /*Alert*/
            }
            viagem.setTipoViagem(TipoViagemEnum.getByDescricao((String) spnTipoViagem.getSelectedItem()));
        } catch (ParseException e) {
            Toast.makeText(this, "Erro na conversão de data", Toast.LENGTH_SHORT);
            e.printStackTrace();
            setResult(RESULT_CANCELED);
            finish();
        }
        realm.commitTransaction();
        dao = DAO.getInstance(Realm.getDefaultInstance());
        dao.saveVigem(viagem);
        finish();

    }

    private void exibiData(){

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(this, new SelecionaDataListener(), year, month, day);
        dpd.show();
    }


    private class ExibeDataListener implements View.OnClickListener, View.OnFocusChangeListener{

        @Override
        public void onClick(View v) {
            exibiData();
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus){
                exibiData();
            }
        }
    }

    private class SelecionaDataListener implements DatePickerDialog.OnDateSetListener{

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            String dataFormatada = UtilsData.getData(year, monthOfYear, dayOfMonth);
            edtData.setText(dataFormatada);

            Date data = UtilsData.getDate(year, monthOfYear, dayOfMonth);
            viagem.setData(data);
        }
    }

    public void preencherDados(){
        edtData.setText(UtilsData.getData(viagem.getData()));
        edtDestino.setText(viagem.getDestino());
    }

}
