package org.foodbankmarket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class InMarketIrwon extends AppCompatActivity {

    RecyclerView recyclerView;
    BoardRecyclerViewAdapter boardRecyclerViewAdapter;

    List<ImageDTOIrwon> imageDTOIrwons = new ArrayList<>();

    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e2_in_market);

        recyclerView = findViewById(R.id.viewpager);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        boardRecyclerViewAdapter = new BoardRecyclerViewAdapter();
        recyclerView.setAdapter(boardRecyclerViewAdapter);
        database = FirebaseDatabase.getInstance();

        database.getReference().child("irwon").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                imageDTOIrwons.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ImageDTOIrwon imageDTOIrwon = snapshot.getValue(ImageDTOIrwon.class);
                    imageDTOIrwons.add(0, imageDTOIrwon);
                }
                boardRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void home_view(View view) {
        onBackPressed();
    }

    class BoardRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.board_item, parent, false);

            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            GlideApp.with(holder.itemView.getContext()).load(imageDTOIrwons.get(position).imageUrl).into(((CustomViewHolder)holder).imageView);
            ((CustomViewHolder)holder).description.setText(imageDTOIrwons.get(position).description);
        }

        @Override
        public int getItemCount() {
            return imageDTOIrwons.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {
            PhotoView imageView;
            TextView description;

            public CustomViewHolder(View view) {
                super(view);
                imageView = view.findViewById(R.id.item_imageview);
                description = view.findViewById(R.id.description);
            }
        }
    }
}