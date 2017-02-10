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
public class RecyclerAdapterone  extends RecyclerView.Adapter<RecyclerAdapterone.ViewHolder> {

    Context ctx;
    List<String> li1;
    List<Double> lat1;
    List<Double> lng1;
    List<String> li2;
    List<String> li3;

    public RecyclerAdapterone(Context ctx, List<String> li1, List<String> li2, List<Double> lat1, List<Double> lng1, List<String> li3) {

   this.ctx=ctx;
        this.li1=li1;
        this.li2=li2;
        this.lat1=lat1;
        this.lng1=lng1;
        this.li3=li3;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private int currentitem;
        public TextView tx;
        public ImageView im;
        public TextView tx1;
        public ViewHolder(View itemView) {
            super(itemView);
            tx= (TextView) itemView.findViewById(R.id.name);
            tx1= (TextView) itemView.findViewById(R.id.addr);
            //im= (ImageView) itemView.findViewById(R.id.imageone);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos=getAdapterPosition();
                    Intent i=new Intent(ctx,MapsActivity.class);
                     i.putExtra("pass","3");
                    i.putExtra("lat",lat1.get(pos));
                    i.putExtra("lng",lng1.get(pos));
                    i.putExtra("title",li3.get(pos));
                    ctx.startActivity(i);
                }
            });

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_one,parent,false);
        RecyclerView.ViewHolder v=new ViewHolder(view);



        return (ViewHolder) v;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {



        holder.tx.setText(li3.get(position));
        holder.tx1.setText(li1.get(position));
       // Picasso.with(ctx).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+li2.get(position)+"&key=AIzaSyB6xDwO_OuT02fhnuVuEivgG0uwzHwspWE").into(holder.im);
        // for (int i=1;i<list.size();i++){
        //      holder.itemTitle.setText(list.get(i));
        //  }

        //  Animation animation = AnimationUtils.loadAnimation(ctx, R.anim.bounce1);
        //  holder.itemView.startAnimation(animation);
    }


    @Override
    public int getItemCount() {
        return li1.size();
    }
}


