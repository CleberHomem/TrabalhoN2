package br.cleberhomem.listafilmes;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FilmeVH extends RecyclerView.ViewHolder
{
    public TextView etTitulo,etGenero,etOpcao;
    public FilmeVH(@NonNull View itemView)
    {
        super(itemView);
        etTitulo = itemView.findViewById(R.id.edit_titulo);
        etGenero = itemView.findViewById(R.id.edit_genero);
        etOpcao = itemView.findViewById(R.id.edit_opcao);
    }
}