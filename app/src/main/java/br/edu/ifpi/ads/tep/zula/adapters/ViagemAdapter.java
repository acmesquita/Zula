package br.edu.ifpi.ads.tep.zula.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.List;

import br.edu.ifpi.ads.tep.zula.R;
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

    public ViagemAdapter(Context context, List viagens) {
        this.context = context;
        this.viagens = viagens;
    }

    @Override
    public ViagemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_viagem, parent, false);
        ViagemViewHolder viewHolder = new ViagemViewHolder(context, v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViagemViewHolder holder, int position) {
        Viagem viagem = viagens.get(position);
        /*Adicionar os componentes de Viagem que será exibido*/
        holder.txtTitulo.setText(viagem.getDestino());
        if(TipoViagemEnum.LAZER.getDescricao().equals(viagem.getTipoViagem().getDescricao())){
            holder.icon.setImageResource(R.drawable.lazer);
        }
        else{
            holder.icon.setImageResource(R.drawable.negocios);
        }
        BigDecimal valorTotal = BigDecimal.ZERO;
        for(Gasto gasto : viagem.getGastos()){
            valorTotal = valorTotal.add( gasto.getValor());
        }

        holder.txtValorViagem.setText("R$ "+valorTotal.toString());
        holder.txtData.setText(UtilsData.getData(viagem.getData()));
    }

    @Override
    public int getItemCount() {
        return viagens.size();
    }

    public class ViagemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

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

        }

        @Override
        public void onClick(View v) {

        }
    }
}
