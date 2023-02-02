package edu.northeastern.numad23sp_zhehaoxu.link_collector;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


import edu.northeastern.numad23sp_zhehaoxu.R;

public class LinkCollector extends Activity {

    private RecyclerView linkRecyclerView;
    private LinkAdapter linkAdapter;
    private List<Link> links;
    private FloatingActionButton addLinks;
    private EditText addName;
    private EditText addUrl;
    private Button confirm;
    private Button cancel;

    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";
    private static final String NUMBER_OF_LINKS = "NUMBER_OF_LINKS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.link_collector);
        links=new ArrayList<>();
        init(savedInstanceState);
        addName=findViewById(R.id.input_name);
        addUrl=findViewById(R.id.input_url);
        confirm=findViewById(R.id.confirm_add);
        cancel=findViewById(R.id.cancel_add);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Toast.makeText(LinkCollector.this, "Deleted", Toast.LENGTH_SHORT).show();
                int position = viewHolder.getLayoutPosition();
                links.remove(position);
                linkAdapter.notifyItemRemoved(position);

            }

        });
        itemTouchHelper.attachToRecyclerView(linkRecyclerView);


        addLinks=findViewById(R.id.add_links);
        addLinks.setOnClickListener(view -> {
            addName.setVisibility(View.VISIBLE);
            addUrl.setVisibility(View.VISIBLE);
            confirm.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
            linkRecyclerView.setVisibility(View.GONE);
            addLinks.setVisibility(View.GONE);
        });

        confirm.setOnClickListener(view->{
            String name=addName.getText().toString();
            String url=addUrl.getText().toString();
            if(name.equals("") || url.equals("")){
                Toast.makeText(this,"Invalid Inputs", Toast.LENGTH_SHORT).show();
            }
            else{
                Link linkToAdd=new Link(name,url);
                links.add(linkToAdd);
                linkAdapter.notifyDataSetChanged();
                String message = "Link successfully added";
                Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
                snackbar.setAction("Undo", v -> {
                    links.remove(links.size()-1);
                    linkAdapter.notifyDataSetChanged();
                });
                snackbar.show();
            }
        });

        cancel.setOnClickListener(view->{
            addName.setText("");
            addUrl.setText("");
            addName.setVisibility(View.GONE);
            addUrl.setVisibility(View.GONE);
            confirm.setVisibility(View.GONE);
            cancel.setVisibility(View.GONE);
            linkRecyclerView.setVisibility(View.VISIBLE);
            addLinks.setVisibility(View.VISIBLE);
        });
    }

    //save instance
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        int size = links == null ? 0 : links.size();
        outState.putInt(NUMBER_OF_LINKS, size);
        // Need to generate unique key for each item
        // This is only a possible way to do, please find your own way to generate the key
        for (int i = 0; i < size; i++) {
            // put image information id into instance
            outState.putString(KEY_OF_INSTANCE + i + "0", links.get(i).getName());
            // put itemName information into instance
            outState.putString(KEY_OF_INSTANCE + i + "1", links.get(i).getUrl());
        }
        super.onSaveInstanceState(outState);
    }

    private void init(Bundle savedInstanceState) {

        initialItemData(savedInstanceState);
        createRecyclerView();
    }

    private void initialItemData(Bundle savedInstanceState) {

        // Not the first time to open this Activity
        if (savedInstanceState != null && savedInstanceState.containsKey(NUMBER_OF_LINKS)) {
            if (links == null || links.size() == 0) {

                int size = savedInstanceState.getInt(NUMBER_OF_LINKS);
                // Retrieve keys we stored in the instance
                for (int i = 0; i < size; i++) {
                    String name = savedInstanceState.getString(KEY_OF_INSTANCE + i + "0");
                    String url = savedInstanceState.getString(KEY_OF_INSTANCE + i + "1");

                    // We need to make sure names such as "XXX(checked)" will not duplicate
                    // Use a tricky way to solve this problem, not the best though

                    Link itemCard = new Link(name, url);
                    links.add(itemCard);
                }
            }
        }
    }


    private void createRecyclerView() {


        RecyclerView.LayoutManager rLayoutManger = new LinearLayoutManager(this);
        linkRecyclerView = findViewById(R.id.link_recyclerView);
        linkRecyclerView.setHasFixedSize(true);
        linkAdapter = new LinkAdapter(links,this);
        linkRecyclerView.setAdapter(linkAdapter);
        linkRecyclerView.setLayoutManager(rLayoutManger);


    }



}
