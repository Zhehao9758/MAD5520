package edu.northeastern.numad23sp_zhehaoxu.link_collector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.numad23sp_zhehaoxu.R;

public class LinkAdapter extends RecyclerView.Adapter<LinkViewHolder> {

    private final List<Link> links;
    private final Context context;

    public LinkAdapter(List<Link> links,Context context){
        this.context=context;
        this.links=links;
    }


    @NonNull
    @Override
    public LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new LinkViewHolder(LayoutInflater.from(context).inflate(R.layout.link_card,null),links,this);
    }

    @Override
    public void onBindViewHolder(@NonNull LinkViewHolder holder, int position){
        holder.bindThisData(links.get(position));
    }



    @Override
    public int getItemCount(){
        return links.size();
    }

}
