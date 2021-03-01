package com.google.notemaker.model;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.notemaker.note.NoteDetails;
import com.google.notemaker.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    List<String> titles;
    List<String> content;
    public Adapter(List<String> titles,List<String> content){
        this.titles = titles;
        //this here points to above created list of titles
        this.content = content;
        //this here points to above created list of content

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_view_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //it will extract the title from list and assign it to view layout
        holder.noteTitle.setText(titles.get(position));
        //it will extract content from list and assign it to content text view of note_view_layout
        holder.noteContent.setText(content.get(position));
        holder.mCardView.setCardBackgroundColor(getRandomColor());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), NoteDetails.class);
                i.putExtra("title",titles.get(position));
                i.putExtra("content",content.get(position));
                v.getContext().startActivity(i);

            }
        });
    }

    private int getRandomColor() {
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.blue);
        colorCode.add(R.color.skyblue);
        colorCode.add(R.color.lightPurple);
        colorCode.add(R.color.colorAccent);
        colorCode.add(R.color.yellow);
        colorCode.add(R.color.colorPrimary);
        colorCode.add(R.color.gray);
        colorCode.add(R.color.greenlight);
        colorCode.add(R.color.lightGreen);
        colorCode.add(R.color.notgreen);
        colorCode.add(R.color.pink);
        colorCode.add(R.color.red);


        Random randomcolor = new Random();
        int number = randomcolor.nextInt(colorCode.size());
        return colorCode.get(number);

    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView noteTitle,noteContent;
        View view;
        CardView mCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.titles);
            noteContent = itemView.findViewById(R.id.content);
            mCardView = itemView.findViewById(R.id.noteCard );
            view = itemView;
        }
    }
}
