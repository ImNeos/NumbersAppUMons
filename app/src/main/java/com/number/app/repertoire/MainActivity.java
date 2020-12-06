package com.number.app.repertoire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.number.app.repertoire.model.model_number;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    numberListAdapter numberListAdapter;
    List<model_number> modelNumberList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();
    }

    private void Init() {


       get_data();

        recyclerView = (RecyclerView) findViewById(R.id.recycle_number);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);


        numberListAdapter = new numberListAdapter(modelNumberList, this);
        recyclerView.setAdapter(numberListAdapter);


    }

    private void get_data()
    {
        FirebaseDatabase.getInstance().getReference().child("BE").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    for (DataSnapshot child : snapshot.getChildren())
                    {
                        String name = child.child("Name").getValue().toString();
                        long number = Long.parseLong(child.child("Number").getValue().toString());
                        long last_update = Long.parseLong(child.child("Last_update").getValue().toString());
                        modelNumberList.add(new model_number(number, name,
                                last_update));
                        numberListAdapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

class numberListAdapter extends  RecyclerView.Adapter <RecyclerView.ViewHolder>
{

    List <model_number> modelNumberList = new ArrayList<>();
    Context context;

    numberListAdapter(List <model_number> model_numbers, Context context)
    {
        this.modelNumberList  = model_numbers;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.number_model, parent, false);
        return new ItemNumberHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        ((ItemNumberHolder) holder).lbl_name.setText(modelNumberList.get(position).getName());
        String number = Long.toString(modelNumberList.get(position).getNumber());
        ((ItemNumberHolder) holder).lbl_number.setText(number);


    }

    @Override
    public int getItemCount()
    {
        return modelNumberList.size();
    }
}
class ItemNumberHolder extends RecyclerView.ViewHolder {

    public TextView lbl_name, lbl_number;
    public ImageView im_color;


    public ItemNumberHolder(View itemView) {
        super(itemView);

        lbl_name = itemView.findViewById(R.id.txt_name);
       // im_color = itemView.findViewById(R.id.img_number);
        lbl_number = itemView.findViewById(R.id.txt_number);

    }
}