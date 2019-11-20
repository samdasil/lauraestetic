package com.example.lauraestetic.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lauraestetic.R;
import com.example.lauraestetic.classes.Servico;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

        private List<Servico> servicos;

        public ItemAdapter(List<Servico> listaServicos) {
            this.servicos = listaServicos;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_servico, parent,false);

            return new MyViewHolder( itemLista );
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Servico servico = servicos.get( position );

            holder.codigo.setText(":: "+String.valueOf(servico.getCodigo()));
            holder.data.setText(servico.getData());
            holder.cliente.setText(servico.getCliente());
            holder.servico.setText(servico.getDescricao());
            holder.valor.setText("R$ "+servico.getValor().toString());
        }

        @Override
        public int getItemCount() {
            return servicos.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView codigo;
            private TextView data;
            private TextView cliente;
            private TextView servico;
            private TextView valor;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                codigo      = itemView.findViewById(R.id.textCodigo);
                data        = itemView.findViewById(R.id.textData);
                cliente     = itemView.findViewById(R.id.textCliente);
                servico     = itemView.findViewById(R.id.textDescServico);
                valor       = itemView.findViewById(R.id.textValor);

            }
        }

    }