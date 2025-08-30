package com.example.appimc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ImcAdapter extends ArrayAdapter<Pessoa> {
    private int resource;

    public ImcAdapter (@NonNull Context context, int resource, @NonNull List<Pessoa> pessoas) {
        super(context, resource, pessoas);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater)
                    getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(this.resource, parent, false);
        }

        TextView tvNomePessoa, tvSexoPessoa, tvPesoDt, tvAlturaDt, tvImcDt, tvCondicaoDt;
        Button btExpandir;
        LinearLayout lvDetalhes;

        // referenciando itens do layout pelo id
        tvNomePessoa = convertView.findViewById(R.id.tvNomePessoa);
        tvSexoPessoa = convertView.findViewById(R.id.tvSexoPessoa);
        tvPesoDt = convertView.findViewById(R.id.tvPesoDt);
        tvAlturaDt = convertView.findViewById(R.id.tvAlturaDt);
        tvImcDt = convertView.findViewById(R.id.tvImcDt);
        tvCondicaoDt = convertView.findViewById(R.id.tvCondicaoDt);
        btExpandir = convertView.findViewById(R.id.btExpandir);
        lvDetalhes = convertView.findViewById(R.id.lvDetalhes);

        tvNomePessoa.setText(getItem(position).getNome());
        tvSexoPessoa.setText("("+getItem(position).getSexo()+")");
        tvPesoDt.setText(String.format("%.1f", getItem(position).getPeso()));
        tvAlturaDt.setText(String.format("%.2f", getItem(position).getAltura()));
        tvImcDt.setText(String.format("%.1f", getItem(position).getImc().getImc()));
        tvCondicaoDt.setText(getItem(position).getImc().getCondicao());

        btExpandir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pessoa p = getItem(position);
                if (p.isExb()) {
                    p.setExb(false);
                    lvDetalhes.setVisibility(View.GONE);
                }
                else {
                    p.setExb(true);
                    lvDetalhes.setVisibility(View.VISIBLE);
                }
            }
        });

        return convertView;
    }
}
