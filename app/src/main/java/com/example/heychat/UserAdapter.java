package com.example.heychat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewholder> {
    MainActivity mainActivity;
    ArrayList<UserModel> userList;
    public UserAdapter(MainActivity mainActivity, ArrayList<UserModel> userList) {
        this.mainActivity = mainActivity;
        this. userList = userList;
    }

    @NonNull
    @Override
    public UserAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mainActivity).inflate(R.layout.user_layout, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.viewholder holder, int position) {
        UserModel user = userList.get(position);
        holder.UserName.setText(user.getUsername());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{

        TextView UserName;
        TextView UserStatus;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            UserName = itemView.findViewById(R.id.UserName);
            //UserStatus = itemView.findViewById(R.id.UserStatus);
        }
    }
}
