package com.publicarea.course.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.publicarea.course.R;
import com.publicarea.course.databinding.FragmentAddCourseBinding;

import java.util.HashMap;

public class addCourseFragment extends Fragment {
    private FragmentAddCourseBinding binding;
    private DatabaseReference databaseReference;

    public addCourseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddCourseBinding.inflate(inflater, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        binding.addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.writeTitleCourse.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Title is empty", Toast.LENGTH_SHORT).show();
                } else if (binding.writeYourCourse.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Course is empty", Toast.LENGTH_SHORT).show();
                } else {
                    addData(binding.writeTitleCourse.getText().toString(), binding.writeYourCourse.getText().toString());
                }
            }
        });
        return binding.getRoot();
    }

    private void addData(String title, String course) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("title",title);
        hashMap.put("course",course);
        databaseReference.child("Course").push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    binding.writeTitleCourse.setText("");
                    binding.writeYourCourse.setText("");
                    Toast.makeText(getContext(), "Course uploaded Success", Toast.LENGTH_SHORT).show();

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, new CourseFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });
    }
}