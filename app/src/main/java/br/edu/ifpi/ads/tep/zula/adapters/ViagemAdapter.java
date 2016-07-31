package br.edu.ifpi.ads.tep.zula.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.List;

import br.edu.ifpi.ads.tep.zula.GastosViagemActivity;
import br.edu.ifpi.ads.tep.zula.MinhasViagensActivity;
import br.edu.ifpi.ads.tep.zula.NovaViagemActivity;
import br.edu.ifpi.ads.tep.zula.R;
import br.edu.ifpi.ads.tep.zula.dominio.dao.DAO;
import br.edu.ifpi.ads.tep.zula.dominio.modelo.Gasto;
import br.edu.ifpi.ads.tep.zula.dominio.modelo.TipoViagemEnum;
import br.edu.ifpi.ads.tep.zula.dominio.modelo.Viagem;
import br.edu.ifpi.ads.tep.zula.util.UtilsData;

/**
 * Created by catharina on 26/07/16.
 */
public class ViagemAdapter extends RecyclerView.Adapter<ViagemAdapter.ViagemViewHolder>{

    private Context context;
    private List<Viagem> viagens;
    private Viagem viagem;

    public ViagemAdapter(Context context, List viagens) {
        this.context = context;
        this.viagens = viagens;
    }

    public void setViagens(List<Viagem> viagens){
        this.viagens = viagens;
        notifyItemInserted(viagens.size());
    }

    @Override
    public ViagemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_viagem, parent, false);
        ViagemViewHolder viewHolder = new ViagemViewHolder(context, v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViagemViewHolder holder, int position) {
        viagem = viagens.get(position);
        /*Adicionar os componentes de Viagem que será exibido*/
        holder.txtTitulo.setText(viagem.getDestino());
        if(TipoViagemEnum.LAZER.getDescricao().equals(viagem.getTipoViagem().getDescricao())){
            holder.icon.setImageResource(R.drawable.icone_gd4);
        }
        else{
            holder.icon.setImageResource(R.drawable.aperto_maoes);
        }
        BigDecimal valorTotal = BigDecimal.ZERO;
        for(Gasto gasto : viagem.getGastos()){
            if(gasto.getValor() != null){
                valorTotal = valorTotal.add( gasto.getValor());
            }
        }

        holder.txtValorViagem.setText("R$ "+valorTotal.toString());
        holder.txtData.setText(UtilsData.getData(viagem.getData()));
    }

    public void remove(Viagem viagensSelecionadas){
        int position = viagens.indexOf(viagensSelecionadas);
        viagens.remove(viagem);
        notifyItemRemoved(position);
    }

    public void remove(List<Viagem> viagensSelecionadas){
        for(Viagem viagem:viagensSelecionadas){
            int position = viagens.indexOf(viagem);
            viagens.remove(viagem);
            notifyItemRemoved(position);
        }
    }
    @Override
    public int getItemCount() {
        return viagens.size();
    }

    public List<Viagem> getViagens() {
        return viagens;
    }

    public void atualizar(Viagem viagem) {
        int i = viagens.indexOf(viagem);
        viagens.remove(i);
        viagens.add(viagem);
    }


    public class ViagemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        Context context;
        ImageView icon;
        TextView txtTitulo;
        TextView txtValorViagem;
        TextView txtData;

        public ViagemViewHolder(Context context ,View itemView) {
            super(itemView);
            this.context = context;
            this.icon = (ImageView) itemView.findViewById(R.id.icon);
            this.txtTitulo = (TextView) itemView.findViewById(R.id.txt_titulo);
            this.txtValorViagem = (TextView) itemView.findViewById(R.id.txt_valor_viagem);
            this.txtData = (TextView) itemView.findViewById(R.id.txt_data);
            itemView.setOnClickListener(this);
            itemView.setLongClickable(true);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(itemView.getContext(), GastosViagemActivity.class);
            intent.putExtra("VIAGEM", this.getAdapterPosition());
            itemView.getContext().startActivity(intent);

        }


        @Override
        public boolean onLongClick(View v) {

            int i = this.getAdapterPosition();

            Intent intent = new Intent(itemView.getContext(), NovaViagemActivity.class);
            intent.putExtra("VIAGEM", i);
            if(itemView.getContext() instanceof MinhasViagensActivity){
                ((MinhasViagensActivity)itemView.getContext()).startActivityForResult(intent, 0);}

            return true;
        }
    }
}
