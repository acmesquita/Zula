package br.edu.ifpi.ads.tep.zula;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_gasto);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Novo Gastos");
        actionBar.dispatchMenuVisibilityChanged(true);

        spn_viagem = (Spinner) findViewById(R.id.spn_viagem);
        List<String> nomeViagens = new ArrayList<>();
        for(Viagem viagem : DAO.getViagens()){
            nomeViagens.add(viagem.getDestino());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nomeViagens);
        spn_viagem.setAdapter(adapter);

        spn_tipo_gasto = (Spinner) findViewById(R.id.snp_tipo_gasto);
        ArrayAdapter<String> adapterGastos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, TipoGastoEnum.getTipos());
        spn_tipo_gasto.setAdapter(adapterGastos);

        btn_salvar = (Button) findViewById(R.id.btn_salvar);
        btn_salvar.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Gasto gasto = new Gasto();
        String viagem = (String) spn_viagem.getSelectedItem();
        String tipo_gasto = (String) spn_tipo_gasto.getSelectedItem();
        valor = (EditText) findViewById(R.id.edt_valor);
        Viagem viagemEscolhida = null;
        for(Viagem viagem1 : DAO.getViagens()){
            if(viagem1.getDestino().equals(viagem)){
                viagemEscolhida = viagem1;
                break;
            }
        }

        Date date = null;
        data = (EditText) findViewById(R.id.edtDataGasto);
        try {
            date = UtilsData.parseForDate(data.getText().toString());
        } catch (ParseException e) {
            Toast.makeText(this, "Data incorrenta", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        gasto.setData(date);
        gasto.setTipoDeGasto(TipoGastoEnum.getTipoByDesc(tipo_gasto) );
        viagemEscolhida.getGastos().add(gasto);
        if(valor.getText().toString().trim() == ""){
            gasto.setValor(new BigDecimal(valor.getText().toString()));
        }
        else{
            Toast.makeText(this, "Insira um valor", Toast.LENGTH_SHORT).show();
        }

        gasto.setViagem(viagemEscolhida);

        finish();
    }
}
