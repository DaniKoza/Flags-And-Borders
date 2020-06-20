package com.example.firstexercise.Adapters;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.example.firstexercise.Classes.State;
import com.example.firstexercise.R;
import com.example.firstexercise.SvgHelpers.SvgDecoder;
import com.example.firstexercise.SvgHelpers.SvgDrawableTranscoder;
import com.example.firstexercise.SvgHelpers.SvgSoftwareLayerSetter;

import java.io.InputStream;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.MyViewHolder> {
    private ArrayList<State> myStates;
    private Context mContext;

    public StateAdapter(Context mContext, ArrayList<State> myStates) {
        this.myStates = myStates;
        this.mContext = mContext;
    }

    public ArrayList<State> getMyStates() {
        return myStates;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.state_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.countryName.setText(myStates.get(position).getName());
        holder.nativeName.setText(myStates.get(position).getNativeName());

        GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder = Glide.with(mContext)
                .using(Glide.buildStreamModelLoader(Uri.class, mContext), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .listener(new SvgSoftwareLayerSetter<Uri>());

        requestBuilder.diskCacheStrategy(DiskCacheStrategy.NONE)
                .load(Uri.parse(myStates.get(position).getFlag()))
                .into(holder.flag);
    }

    @Override
    public int getItemCount() {
        return myStates.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView countryName;
        private TextView nativeName;
        private ImageView flag;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.countryName = itemView.findViewById(R.id.textView_country_name);
            this.nativeName = itemView.findViewById(R.id.textView_native_name);
            this.flag = itemView.findViewById(R.id.imageView_flag);
        }
    }


    public ArrayList<State> costumeFilter(ArrayList<State> input, String word) // for search edit text - filter function
    {
        ArrayList<State> arr = new ArrayList<>();

        for (State s : input) {
            if (s.getName().toLowerCase().contains(word) || s.getNativeName().toLowerCase().contains(word)) {
                arr.add(s);
            }
        }
        return arr;
    }

}
