package br.edu.ifpi.ads.tep.zula;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpi.ads.tep.zula.adapters.GastoAdapter;
import br.edu.ifpi.ads.tep.zula.dominio.dao.DAO;
import br.edu.ifpi.ads.tep.zula.dominio.modelo.Gasto;
import br.edu.ifpi.ads.tep.zula.dominio.modelo.Viagem;

public class GastosViagemActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Gasto> gastos;
    private GastoAdapter adapter;
    private Viagem viagemEscolhida;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastos_viagem);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Gastos");
        actionBar.dispatchMenuVisibilityChanged(true);

        /*Receber a viagem*/
        /*Receber o pamâmetro*/
        int position = (int) getIntent().getIntExtra("VIAGEM", -1);
        if(position != -1){
            viagemEscolhida = DAO.getViagemById(position);
            gastos = viagemEscolhida.getGastos();
            adapter = new GastoAdapter(this,gastos);

            recyclerView = (RecyclerView) findViewById(R.id.recycler_view_gastos);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_meus_gastos, menu);
        MenuItem item = menu.findItem(R.id.lixeira);


        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id ==  R.id.lixeira){
            AlertDialog.Builder dig = new AlertDialog.Builder(this);
            dig.setTitle("Excluir");
            dig.setMessage("Deseja realmente excluir essa gasto?");
            dig.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    excluirGasto();
                }
            });
            dig.setNegativeButton("Cancela", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (int i = 0; i < recyclerView.getChildCount(); i++) {
                        CheckBox checkBox = (CheckBox) recyclerView.getChildAt(i).findViewById(R.id.checkBox);
                        if (checkBox.isChecked()) {
                            checkBox.setChecked(false);
                        }
                    }
                }
            });
            dig.show();


            return true;
        }
        else if(id == R.id.adicionar_gasto){
            Intent intent = new Intent(this, NovoGastoActivity.class);
            intent.putExtra("VIAGEM", viagemEscolhida.getId());
            startActivityForResult(intent, 0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void excluirGasto(){
        List<Gasto> gastoSelecionados = new ArrayList<>();
        if(recyclerView.getAdapter() != null) {
            for (int i = 0; i < recyclerView.getChildCount(); i++) {
                CheckBox checkBox = (CheckBox) recyclerView.getChildAt(i).findViewById(R.id.checkBox);
                if (checkBox.isChecked()) {
                    Gasto gasto = gastos.get(i);
                    gastoSelecionados.add(gasto);
                }
            }
            ;
            adapter.remove(gastoSelecionados);
        }
        else{
            Toast.makeText(this, "Selecione pelo menos um item.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        adapter.setGastos(viagemEscolhida.getGastos());
        recyclerView.setAdapter(adapter);
        super.onActivityResult(requestCode, resultCode, data);
    }
}

