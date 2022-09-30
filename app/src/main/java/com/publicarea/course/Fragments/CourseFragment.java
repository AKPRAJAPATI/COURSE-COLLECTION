package com.publicarea.course.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.publicarea.course.MainActivity;
import com.publicarea.course.databinding.FragmentCourseBinding;
import com.publicarea.course.mainAdapters;
import com.publicarea.course.mainModel;

import java.util.ArrayList;


public class CourseFragment extends Fragment {
    private FragmentCourseBinding binding;
    private mainAdapters adapters;
    private DatabaseReference database;
private ArrayList<mainModel> arrayList;
    public CourseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCourseBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance().getReference();

        binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerview.setHasFixedSize(true);
         arrayList = new ArrayList<>();
        database.child("Course").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    mainModel model = dataSnapshot.getValue(mainModel.class);
                    model.setUniqueKey(dataSnapshot.getKey());
                    arrayList.add(model);
                }
                adapters.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapters = new mainAdapters(getContext(), arrayList);
        binding.recyclerview.setAdapter(adapters);
        return binding.getRoot();
    }

    private void filterList(String newText) {
    ArrayList<mainModel> filter = new ArrayList<>();
    for (mainModel model :  arrayList){
        if (model.getTitle().toLowerCase().contains(newText.toLowerCase())){
            filter.add(model);
        }
    }
    if (filter.isEmpty())
    {
        Toast.makeText(getContext(), "NO data found", Toast.LENGTH_SHORT).show();
    }else {
        adapters.setFilterList(filter);
    }
    }
}