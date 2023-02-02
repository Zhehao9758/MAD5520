package edu.northeastern.numad23sp_zhehaoxu.link_collector;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.numad23sp_zhehaoxu.R;

public class LinkViewHolder extends RecyclerView.ViewHolder {

    private final TextView nameTV;
    private final TextView urlTV;

    public LinkViewHolder(@NonNull View itemView, List<Link> links,LinkAdapter parent) {
        super(itemView);
        this.nameTV=itemView.findViewById(R.id.link_name);
        this.urlTV=itemView.findViewById(R.id.link_url);

        View.OnLongClickListener edit= view -> {
            final LinearLayout linearLayout=new LinearLayout(view.getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            final EditText inputName = new EditText(view.getContext());
            final EditText inputUrl = new EditText(view.getContext());
            inputName.setText(nameTV.getText());
            inputUrl.setText(urlTV.getText());
            linearLayout.addView(inputName);
            linearLayout.addView(inputUrl);
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Edit Link")
                    .setView(inputName)
                    .setView(linearLayout)
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            nameTV.setText(inputName.getText());
                            urlTV.setText(inputUrl.getText());
                            int position=getLayoutPosition();
                            links.get(position).setName(inputName.getText().toString());
                            links.get(position).setUrl(inputUrl.getText().toString());
                            parent.notifyItemChanged(position);
                        }
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            builder.create().show();
            return true;
        };
        nameTV.setOnLongClickListener(edit);
        urlTV.setOnLongClickListener(edit);
        urlTV.setOnClickListener(view->{
            int position=getAdapterPosition();
            if(position!=RecyclerView.NO_POSITION){
                String url=urlTV.getText().toString();
                if (!url.startsWith("https://") && !url.startsWith("http://")){
                    url = "http://" + url;
                }
                try {
                    view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }catch (ActivityNotFoundException e){
                    Toast.makeText(view.getContext(), "No app found to open this url", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void bindThisData(Link theLinkToBind){
        nameTV.setText(theLinkToBind.getName());
        urlTV.setText(theLinkToBind.getUrl());
    }
}
