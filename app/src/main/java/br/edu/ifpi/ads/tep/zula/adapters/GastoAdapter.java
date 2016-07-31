package br.edu.ifpi.ads.tep.zula.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.edu.ifpi.ads.tep.zula.GastosViagemActivity;
import br.edu.ifpi.ads.tep.zula.MinhasViagensActivity;
import br.edu.ifpi.ads.tep.zula.NovaViagemActivity;
import br.edu.ifpi.ads.tep.zula.NovoGastoActivity;
import br.edu.ifpi.ads.tep.zula.R;
import br.edu.ifpi.ads.tep.zula.dominio.dao.DAO;
import br.edu.ifpi.ads.tep.zula.dominio.modelo.Gasto;
import br.edu.ifpi.ads.tep.zula.dominio.modelo.TipoGastoEnum;
import br.edu.ifpi.ads.tep.zula.dominio.modelo.Viagem;
import br.edu.ifpi.ads.tep.zula.util.UtilsData;

/**
 * Created by catharina on 27/07/16.
 */
public class GastoAdapter extends RecyclerView.Adapter<GastoAdapter.GastoViewHolder>{

    private Context context;
    private List<Gasto> gastos;

    public GastoAdapter(Context context, List<Gasto> gastos) {
        this.context = context;
        this.gastos = gastos;
    }

    @Override
    public GastoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_gasto, parent, false);
        GastoViewHolder viewHolder = new GastoViewHolder(context, v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(GastoViewHolder holder, int position) {

        Gasto gasto = gastos.get(position);

        holder.txtTitulo.setText(gasto.getTipoDeGasto().getDescricao());
        if(gasto.getTipoDeGasto().getDescricao().equals(TipoGastoEnum.ALIMENTACAO.getDescricao())){
            holder.icon.setImageResource(R.drawable.icon_alimentacao);/*Buscar imagens*/
        }
        else if(gasto.getTipoDeGasto().getDescricao().equals(TipoGastoEnum.TRANSPORTE.getDescricao())){
            holder.icon.setImageResource(R.drawable.icon_trem);/*Buscar imagens*/
        }
        else {
            holder.icon.setImageResource(R.drawable.compra_icon);/*Buscar imagens*/
        }

        holder.txtData.setText(UtilsData.getData(gasto.getData()));
        holder.txtValorViagem.setText("R$ "+gasto.getValor().toString());

    }

    @Override
    public int getItemCount() {
        return  gastos.size();
    }

    public void remove(List<Gasto> gastosSelecionados){
        for(Gasto gasto :gastosSelecionados){
            int position = gastos.indexOf(gasto);
            gastos.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void setGastos(List<Gasto> gastos) {
        this.gastos = gastos;
    }

    public class GastoViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        Context context;
        ImageView icon;
        TextView txtTitulo;
        TextView txtValorViagem;
        TextView txtData;
        CheckBox checkBox;

        public GastoViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            this.icon = (ImageView) itemView.findViewById(R.id.icon);
            this.txtTitulo = (TextView) itemView.findViewById(R.id.txt_titulo);
            this.txtValorViagem = (TextView) itemView.findViewById(R.id.txt_valor_viagem);
            this.txtData = (TextView) itemView.findViewById(R.id.txt_data);
            this.checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            itemView.setOnLongClickListener(this);
            itemView.setLongClickable(true);
        }

        @Override
        public boolean onLongClick(View v) {

            int position = this.getAdapterPosition();
            Gasto gasto1 = gastos.get(position);
            Intent intent = new Intent(itemView.getContext(), NovoGastoActivity.class);
            intent.putExtra("GASTO", gasto1);
            intent.putExtra("VIAGEM", gasto1.getViagem().getId());
            if(itemView.getContext() instanceof GastosViagemActivity)
                ((GastosViagemActivity)itemView.getContext()).startActivityForResult(intent, 0);


            return true;
        }
    }

}