package br.edu.ifpi.ads.tep.zula;

import android.app.DatePickerDialog;
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

public class NovoGastoActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText valor;
    private Spinner spn_viagem;
    private Spinner spn_tipo_gasto;
    private Button btn_salvar;
    private EditText data;
    private Gasto gasto ;
    private Viagem viagemEscolhida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_gasto);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Novo Gastos");
        actionBar.dispatchMenuVisibilityChanged(true);


        int position = (int) getIntent().getIntExtra("VIAGEM", -1);

        spn_viagem = (Spinner) findViewById(R.id.spn_viagem);
        List<String> nomeViagens = new ArrayList<>();

        for(Viagem viagem : DAO.getViagens()){
            if(position != -1){
                if(viagem.getId() == position){
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
        for(Viagem viagem1 : DAO.getViagens()){
            if(viagem1.getDestino().equals(viagem)){
                viagemEscolhida = viagem1;
                break;
            }
        }
        gasto = (Gasto) getIntent().getSerializableExtra("GASTO");
        if(gasto != null){
            preencherGasto();
        }
        else{
            gasto = new Gasto();
        }

    }

    private void preencherGasto() {
        data.setText(UtilsData.getData(gasto.getData()));
        valor.setText(gasto.getValor().toString());
    }


    @Override
    public void onClick(View v) {

        String tipo_gasto = (String) spn_tipo_gasto.getSelectedItem();

        gasto.setTipoDeGasto(TipoGastoEnum.getTipoByDesc(tipo_gasto) );

        if(valor.getText().toString().trim() != ""){
            gasto.setValor(new BigDecimal(valor.getText().toString()));
            gasto.setViagem(viagemEscolhida);
            if(gasto.getId() != -1){
                Gasto gastoEscolhido= viagemEscolhida.getGastos().get(gasto.getId());
                viagemEscolhida.getGastos().remove(gastoEscolhido);
                viagemEscolhida.getGastos().add(gasto);
            }
            else{
                gasto.setId(viagemEscolhida.getGastos().size());
                viagemEscolhida.getGastos().add(gasto);
            }

            finish();
        }
        else{
            AlertDialog.Builder dig = new AlertDialog.Builder(this);
            dig.setTitle("Erro");
            dig.setMessage("Insira um valor.");
            dig.setNegativeButton("Ok",null);
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
            gasto.setData(data);
        }
    }
}
