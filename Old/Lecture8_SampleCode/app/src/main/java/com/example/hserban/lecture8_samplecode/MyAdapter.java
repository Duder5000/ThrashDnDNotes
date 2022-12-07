package com.example.hserban.lecture8_samplecode;

import android.content.Context;
import android.content.Intent; //INTENT!!!
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import androidx.recyclerview.widget.RecyclerView;
import static com.example.hserban.lecture8_samplecode.R.layout.row;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public ArrayList<String> list;
    public String xP, yP, n, iR;

    public MyAdapter(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(row,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
        String[]  results = (list.get(position).toString()).split(",");

        xP = results[0];
        yP = results[1];
        n = results[2];
        iR = results[3];

        holder.xPosTextView.setText(xP);
        holder.yPosTextView.setText(yP);
        holder.noteTextView.setText(n);
        holder.imgRefTextView.setText(iR);
        holder.hiddenTextView.setText(iR);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView xPosTextView;
        public TextView yPosTextView;
        public TextView noteTextView;
        public TextView imgRefTextView;
        public TextView hiddenTextView;
        public LinearLayout myLayout;

        Context context;

        public MyViewHolder(View itemView) {
            super(itemView);
            myLayout = (LinearLayout) itemView;

            xPosTextView = itemView.findViewById(R.id.xPosEditText);
            yPosTextView = itemView.findViewById(R.id.yPosEditText);
            noteTextView = itemView.findViewById(R.id.noteEditText);
            imgRefTextView = itemView.findViewById(R.id.imgRefEditText);
            hiddenTextView = itemView.findViewById(R.id.hidden);

            itemView.setOnClickListener(this);
            context = itemView.getContext();
        }

        @Override
        public void onClick(View view) {
            Intent noteEditIntent = new Intent(context, ReadNote.class);
            noteEditIntent.putExtra("xPosExtra", xPosTextView.getText().toString());
            noteEditIntent.putExtra("yPosExtra", yPosTextView.getText().toString());
            noteEditIntent.putExtra("noteExtra", noteTextView.getText().toString());
            noteEditIntent.putExtra("imgRefExtra", hiddenTextView.getText().toString());

            context.startActivity(noteEditIntent);
        }
    }

}
