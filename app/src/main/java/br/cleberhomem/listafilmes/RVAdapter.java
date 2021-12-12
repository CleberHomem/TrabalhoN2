package br.cleberhomem.listafilmes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Context context;
    ArrayList<Filme> list = new ArrayList<>();
    public RVAdapter(Context ctx)
    {
        this.context = ctx;
    }
    public void setItems(ArrayList<Filme> film)
    {
        list.addAll(film);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item,parent,false);
        return new FilmeVH(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        Filme e = null;
        this.onBindViewHolder(holder,position,e);
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, Filme e)
    {
        FilmeVH vh = (FilmeVH) holder;
        Filme film = e==null? list.get(position):e;
        vh.etTitulo.setText(film.getTitulo());
        vh.etGenero.setText(film.getGenero());
        vh.etOpcao.setOnClickListener(v->
        {
            PopupMenu popupMenu =new PopupMenu(context,vh.etOpcao);
            popupMenu.inflate(R.menu.option_menu);
            popupMenu.setOnMenuItemClickListener(item->
            {
                switch (item.getItemId())
                {
                    case R.id.menu_edit:
                        Intent intent=new Intent(context,MainActivity.class);
                        intent.putExtra("EDITAR",film);
                        context.startActivity(intent);
                        break;
                    case R.id.menu_remove:
                        DAOFilme dao=new DAOFilme();
                        dao.remove(film.getKey()).addOnSuccessListener(suc->
                        {
                            Toast.makeText(context, "Registro removido", Toast.LENGTH_SHORT).show();
                            notifyItemRemoved(position);
                            list.remove(film);
                        }).addOnFailureListener(er->
                        {
                            Toast.makeText(context, ""+er.getMessage(), Toast.LENGTH_SHORT).show();
                        });

                        break;
                }
                return false;
            });
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}