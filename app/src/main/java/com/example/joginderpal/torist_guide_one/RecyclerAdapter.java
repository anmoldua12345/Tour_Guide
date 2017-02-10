package com.example.joginderpal.torist_guide_one;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by joginderpal on 22-01-2017.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    String loc;
List<String> li,li1,li2;
    Context ctx;

    public RecyclerAdapter(List<String> li, List<String> li1, List<String> li2, Context ctx) {

    this.li=li;
        this.li1=li1;
        this.li2=li2;
    this.ctx=ctx;

    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private int currentitem;
      public TextView tx;
        public ImageView im;

        public ViewHolder(View itemView) {
            super(itemView);
           tx= (TextView) itemView.findViewById(R.id.textone);
         im= (ImageView) itemView.findViewById(R.id.image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos=getAdapterPosition();
                /*    Intent i=new Intent(ctx,MapsActivity.class);
                    i.putExtra("text",loc);
                    i.putExtra("pass","2");
                    i.putExtra("text1",titles[pos]);
                    ctx.startActivity(i);*/
                }
            });

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        RecyclerView.ViewHolder v=new ViewHolder(view);



        return (ViewHolder) v;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        holder.tx.setText(li2.get(position));
        Picasso.with(ctx).load(li1.get(position)).into(holder.im);

        // for (int i=1;i<list.size();i++){
        //      holder.itemTitle.setText(list.get(i));
        //  }

        //  Animation animation = AnimationUtils.loadAnimation(ctx, R.anim.bounce1);
        //  holder.itemView.startAnimation(animation);
    }


    @Override
    public int getItemCount() {
        return li.size();
    }
}

