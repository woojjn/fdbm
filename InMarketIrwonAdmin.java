package org.foodbankmarket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class InMarketIrwonAdmin extends AppCompatActivity {

    RecyclerView recyclerView;
    BoardRecyclerViewAdapter boardRecyclerViewAdapter;

    List<ImageDTOIrwon> imageDTOIrwons = new ArrayList<>();
    List<String> uidLists = new ArrayList<>();

    FirebaseStorage storage;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e2_in_market);

        recyclerView = findViewById(R.id.viewpager);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        boardRecyclerViewAdapter = new BoardRecyclerViewAdapter();
        recyclerView.setAdapter(boardRecyclerViewAdapter);

        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

        database.getReference().child("irwon").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                imageDTOIrwons.clear();
                uidLists.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ImageDTOIrwon imageDTOIrwon = snapshot.getValue(ImageDTOIrwon.class);
                    imageDTOIrwons.add(imageDTOIrwon);
                    String uidkey = snapshot.getKey();
                    uidLists.add(uidkey);
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

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.board_item_admin, parent, false);

            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
            GlideApp.with(holder.itemView.getContext()).load(imageDTOIrwons.get(position).imageUrl).into(((CustomViewHolder)holder).imageView);
            ((CustomViewHolder)holder).description.setText(imageDTOIrwons.get(position).description);

            ((CustomViewHolder)holder).delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete_content(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return imageDTOIrwons.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {
            PhotoView imageView;
            ImageView delete;
            TextView description;

            CustomViewHolder(View view) {
                super(view);
                imageView = view.findViewById(R.id.item_imageview);
                description = view.findViewById(R.id.description);
                delete = view.findViewById(R.id.delete);
            }
        }
    }

    public void delete_content(final int position) {
        storage.getReference().child("irwon").child(imageDTOIrwons.get(position).imageName).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                database.getReference().child("irwon").child(uidLists.get(position)).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "데이터베이스 삭제 실패...", Toast.LENGTH_SHORT).show();
                    }
                });
                Toast.makeText(getApplicationContext(), "삭제 완료!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                database.getReference().child("irwona").child(uidLists.get(position)).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "데이터베이스 삭제 실패...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}