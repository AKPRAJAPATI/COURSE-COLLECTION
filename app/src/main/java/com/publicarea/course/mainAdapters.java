package com.publicarea.course;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.publicarea.course.Fragments.addCourseFragment;
import com.publicarea.course.databinding.ItemBinding;

import java.util.ArrayList;

public class mainAdapters extends RecyclerView.Adapter<mainAdapters.VideoViewHolder> {
    Context context;
    ArrayList<mainModel> arrayList;

    public mainAdapters(Context context, ArrayList<mainModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public void setFilterList(ArrayList<mainModel> filterList) {
        this.arrayList = filterList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {

        mainModel model = arrayList.get(position);
        holder.binding.courseTitle.setText(model.getTitle());
        holder.binding.courseDetail.setText(model.getCourse());

        holder.binding.courseTitle.setSelected(true);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", model.getCourse());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "copied", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.delete:

                                alertDialog.setTitle("PASSWORD");
                                alertDialog.setMessage("Enter Password");


                                final EditText input = new EditText(context);

                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                                input.setLayoutParams(lp);
                                alertDialog.setView(input);
                                alertDialog.setIcon(R.drawable.book);

                                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        String value = input.getText().toString();
                                        String password = "only_me";
                                        if (value.equals("")) {
                                            Toast.makeText(context, "Password is empty", Toast.LENGTH_SHORT).show();
                                        } else if (value.equals(password)) {
                                            FirebaseDatabase.getInstance().getReference().child("Course").child(model.getUniqueKey()).removeValue();
                                            Toast.makeText(context, "Course Deleted", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(context, "You are not admin", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                alertDialog.setNegativeButton("NO",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });

                                alertDialog.show();
                                break;
                            case R.id.openUrl:
                                Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(model.getCourse()));
                                context.startActivity(urlIntent);
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    static class VideoViewHolder extends RecyclerView.ViewHolder {
        ItemBinding binding;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemBinding.bind(itemView);
        }
    }
}
