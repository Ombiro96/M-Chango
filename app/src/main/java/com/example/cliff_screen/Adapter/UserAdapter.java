package com.example.cliff_screen.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cliff_screen.Model.User;
import com.example.cliff_screen.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

    private Context context;
    private List<User> userList;

    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.user_display_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User user= userList.get(position);

        holder.type.setText(user.getType());
        if(user.getType().equals("Donor")){
            holder.emailNow.setVisibility(View.VISIBLE);
        }
        holder.userEmail.setText(user.getEmail());
        holder.phoneNumber.setText(user.getPhoneNumber());
        holder.userName.setText(user.getName());

        Glide.with(context).load(user.getProfile_picture_url()).into(holder.userProfileImage);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

            public CircleImageView userProfileImage;
            public TextView type,userName,userEmail, phoneNumber;
            public Button emailNow;

            public ViewHolder(@NonNull View itemView){
                super(itemView);

                userProfileImage=itemView.findViewById(R.id.userProfileImage);
                type=itemView.findViewById(R.id.type);
                userName=itemView.findViewById(R.id.userName);
                userEmail=itemView.findViewById(R.id.userEmail);
                phoneNumber=itemView.findViewById(R.id.phoneNumber);
                emailNow=itemView.findViewById(R.id.emailNow);

            }
        }
    }
