package com.kasimkartal866.mybookmedia.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.kasimkartal866.mybookmedia.db.Book;
import com.kasimkartal866.mybookmedia.activities.MainPageActivity;
import com.kasimkartal866.mybookmedia.R;
import com.kasimkartal866.mybookmedia.common.Utilities;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    private ArrayList<Book> data = new ArrayList<>();
    private Context context;
    public Adapter() {
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBookName, tvAuthLbl, tvExp;
        ImageView ivPic;
        ConstraintLayout cl;
        public ViewHolder(View itemView) {
            super(itemView);
            tvBookName = itemView.findViewById(R.id.tvBookName);
            tvAuthLbl = itemView.findViewById(R.id.tvAuthLbl);
            tvExp = itemView.findViewById(R.id.tvExp);
            ivPic = itemView.findViewById(R.id.ivPic);
            cl = itemView.findViewById(R.id.cl);
        }
    }

    public void submitList(List<Book> books) {
        this.data.clear();
        this.data.addAll(books);
        this.notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Book book = data.get(position);
        holder.tvBookName.setText(book.getBookName());
        holder.tvAuthLbl.setText(book.getAuthorName());
        holder.tvExp.setText(book.getExplanation());

        Utilities.loadImage(context, book.getImageAddress(), holder.ivPic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), MainPageActivity.class);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
}
