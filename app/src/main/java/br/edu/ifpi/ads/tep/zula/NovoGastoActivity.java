package br.edu.ifpi.ads.tep.zula;

import android.app.DatePickerDialog;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.edu.ifpi.ads.tep.zula.dominio.dao.DAO;
import br.edu.ifpi.ads.tep.zula.dominio.modelo.Gasto;
import br.edu.ifpi.ads.tep.zula.dominio.modelo.TipoGastoEnum;
import br.edu.ifpi.ads.tep.zula.dominio.modelo.Viagem;
import br.edu.ifpi.ads.tep.zula.util.UtilsData;
import io.realm.Realm;

public class NovoGastoActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText valor;
    private Spinner spn_viagem;
    private Spinner spn_tipo_gasto;
    private Button btn_salvar;
    private EditText data;
    private Gasto gasto ;
    private Viagem viagemEscolhida;
    private DAO dao;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_gasto);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Novo Gastos");
        actionBar.dispatchMenuVisibilityChanged(true);


        String viagem2 = getIntent().getStringExtra("VIAGEM");

        spn_viagem = (Spinner) findViewById(R.id.spn_viagem);
        List<String> nomeViagens = new ArrayList<>();
        dao = DAO.getInstance(Realm.getDefaultInstance());
        for(Viagem viagem : dao.getViagemAll()){
            if(viagem2 != null){
                if(viagem.getId().equalsIgnoreCase(viagem2)){
                    nomeViagens.add(viagem.getDestino());
                    break;
                }
            }
            else{
                nomeViagens.add(viagem.getDestino());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nomeViagens);
        spn_viagem.setAdapter(adapter);

        spn_tipo_gasto = (Spinner) findViewById(R.id.snp_tipo_gasto);
        ArrayAdapter<String> adapterGastos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, TipoGastoEnum.getTipos());
        spn_tipo_gasto.setAdapter(adapterGastos);

        btn_salvar = (Button) findViewById(R.id.btn_salvar);
        btn_salvar.setOnClickListener(this);

        data = (EditText) findViewById(R.id.edtDataGasto);
        ExibeDataListener listener = new ExibeDataListener();
        data.setOnClickListener(listener);
        data.setOnFocusChangeListener(listener);
        data.setKeyListener(null);/*Evitar que o campo seja editado manualmente. - Retitou o teclado.*/

        valor = (EditText) findViewById(R.id.edt_valor);

        String viagem = (String) spn_viagem.getSelectedItem();
        realm = Realm.getDefaultInstance();
        dao = DAO.getInstance(realm);
        for(Viagem viagem1 : dao.getViagemAll()){
            if(viagem1.getDestino().equals(viagem)){
                viagemEscolhida = viagem1;
                break;
            }
        }
        String idgasto = getIntent().getStringExtra("GASTO");
        dao = DAO.getInstance(Realm.getDefaultInstance());
        gasto = dao.getGastoById(idgasto);
        if(this.gasto != null){
            preencherGasto();
        }
        else{
            this.gasto = new Gasto();
        }

    }

    private void preencherGasto() {
        data.setText(UtilsData.getData(gasto.getData()));
        valor.setText(gasto.getValor()+"");
    }


    @Override
    public void onClick(View v) {
        realm.beginTransaction();
        String tipo_gasto = (String) spn_tipo_gasto.getSelectedItem();
        gasto.setTipoDeGasto(TipoGastoEnum.getTipoByDesc(tipo_gasto));

        if (valor.getText().toString().trim() != "") {
            gasto.setValor(new BigDecimal(valor.getText().toString()));
            if (viagemEscolhida == null) {
                viagemEscolhida = dao.getViagemByDestino((String) spn_viagem.getSelectedItem()).first();
            }
            gasto.setViagem(viagemEscolhida);
            if(!viagemEscolhida.getGastos().contains(gasto)){viagemEscolhida.getGastos().add(gasto);}
            realm.commitTransaction();
            finish();
        } else {
            AlertDialog.Builder dig = new AlertDialog.Builder(NovoGastoActivity.this);
            dig.setTitle("Erro");
            dig.setMessage("Insira um valor.");
            dig.setNegativeButton("Ok", null);
            dig.show();
        }
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
            data.setText(dataFormatada);

            Date data = UtilsData.getDate(year, monthOfYear, dayOfMonth);
            realm.beginTransaction();
            gasto.setData(data);
            realm.commitTransaction();
        }
    }
}
