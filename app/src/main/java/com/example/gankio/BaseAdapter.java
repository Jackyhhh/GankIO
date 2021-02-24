package com.example.gankio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gankio.data.*;
import com.orhanobut.logger.Logger;

import java.util.List;

public class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.BaseViewHolder> {
    Context context;
    private List<Picture.DataDTO> mDataDTOS;
    public BaseAdapter(Context context, List<Picture.DataDTO> mDataDTOS) {
        this.mDataDTOS = mDataDTOS;
        this.context = context;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pics_item,parent,false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        Picture.DataDTO mDataDTO = mDataDTOS.get(position);
        Glide.with(context).load(mDataDTO.getUrl()).into(holder.getImageView());
//        holder.getImageView().setImageResource();
        holder.getTextView().setText(mDataDTO.getDesc());
        Logger.d(mDataDTO.getDesc());
    }



    @Override
    public int getItemCount() {
        return mDataDTOS.size();
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageview);
            textView = (TextView) itemView.findViewById(R.id.textview);
        }

        public ImageView getImageView() {
            return imageView;
        }
        public TextView getTextView() {
            return textView;
        }
    }
}
