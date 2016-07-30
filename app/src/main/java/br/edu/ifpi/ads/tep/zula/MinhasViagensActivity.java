package br.edu.ifpi.ads.tep.zula;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.SearchView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpi.ads.tep.zula.adapters.ViagemAdapter;
import br.edu.ifpi.ads.tep.zula.dominio.dao.DAO;
import br.edu.ifpi.ads.tep.zula.dominio.modelo.Gasto;
import br.edu.ifpi.ads.tep.zula.dominio.modelo.Viagem;
import br.edu.ifpi.ads.tep.zula.util.UtilsData;

public class MinhasViagensActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Viagem> viagens;
    private ViagemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_viagens);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Minhas Viagens");
        actionBar.dispatchMenuVisibilityChanged(true);

        viagens =  DAO.getViagens();
        adapter = new ViagemAdapter(this,viagens);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_minhas_viagens, menu);
        MenuItem item = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(onSearch());
        return super.onCreateOptionsMenu(menu);
    }

    private SearchView.OnQueryTextListener onSearch() {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        };
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id ==  R.id.action_search){
            return true;
        }
        else if (id == R.id.action_share) {
            /*Criar lista e criar o compartilhar varias viagens*/
            List<Viagem> viagensSelecionadas = new ArrayList<>();
            if(recyclerView.getAdapter() != null) {
                for (int i = 0; i < recyclerView.getChildCount(); i++) {
                    CheckBox checkBox = (CheckBox) recyclerView.getChildAt(i).findViewById(R.id.checkViagem);
                    if (checkBox.isChecked()) {
                        Viagem viagem = viagens.get(i);
                        viagensSelecionadas.add(viagem);
                    }
                }
                ;
                compartilharViagem(viagensSelecionadas);
            }
            else{
                Toast.makeText(this, "Selecione pelo menos um item.", Toast.LENGTH_SHORT).show();
            }

            return true;
        }
        else if(id == R.id.lixeira){
            AlertDialog.Builder dig = new AlertDialog.Builder(this);
            dig.setTitle("Excluir");
            dig.setMessage("Deseja realmente excluir essa viagem?");
            dig.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    excluirViagem();
                }
            });
            dig.setNegativeButton("Cancela", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (int i = 0; i < recyclerView.getChildCount(); i++) {
                        CheckBox checkBox = (CheckBox) recyclerView.getChildAt(i).findViewById(R.id.checkViagem);
                        if (checkBox.isChecked()) {
                            checkBox.setChecked(false);
                        }
                    }
                }
            });
            dig.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void compartilharViagem(List<Viagem> viagensSelecionadas) {
        String msg = "";
        for(Viagem v : viagensSelecionadas){
            msg += montarMensagem(v) + "\n\n";
        }

        Intent minhaIntent = new Intent();
        minhaIntent.setAction(Intent.ACTION_SEND);
        minhaIntent.putExtra(Intent.EXTRA_SUBJECT, "Suas Viagens pelo Zula app");
        minhaIntent.putExtra(Intent.EXTRA_TEXT, msg);
        minhaIntent.setType("text/plain");
        startActivity(minhaIntent);

    }

    private String montarMensagem(Viagem viagem) {
        String msg = "Viagem: "+viagem.getDestino()+"\n"+
                "Periodo: "+UtilsData.getData(viagem.getData())+"\n";
        for (Gasto gasto: viagem.getGastos()){
            msg += gasto.getTipoDeGasto().getDescricao() + " - R$ "+gasto.getValor()+"\n";
        }
        msg+="Compartilhado por: Zula app";
        return msg;
    }

    private void excluirViagem() {
        List<Viagem> viagemsSelecionadas = new ArrayList<>();
        if(recyclerView.getAdapter() != null) {
            for (int i = 0; i < recyclerView.getChildCount(); i++) {
                CheckBox checkBox = (CheckBox) recyclerView.getChildAt(i).findViewById(R.id.checkViagem);
                if (checkBox.isChecked()) {
                    Viagem viagem = viagens.get(i);
                    viagemsSelecionadas.add(viagem);
                }
            }
            ;
            adapter.remove(viagemsSelecionadas);
        }
        else{
            Toast.makeText(this, "Selecione pelo menos um item.", Toast.LENGTH_SHORT).show();
        }

    }


    public void adicionarViagem(View view) {
        Intent intent = new Intent(this, NovaViagemActivity.class);
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0){
            Bundle extras = data.getExtras();
            if(extras != null){
                Viagem viagem = (Viagem) extras.getSerializable("VIAGEM");
                List<Viagem> viagens = DAO.getViagens();
                viagem.setId(viagens.size());
                viagens.add(viagem);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
