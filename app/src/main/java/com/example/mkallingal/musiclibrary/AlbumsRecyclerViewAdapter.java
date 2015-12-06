package com.example.mkallingal.musiclibrary;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mkallingal on 12/6/2015.
 */
public class AlbumsRecyclerViewAdapter extends RecyclerView.Adapter<AlbumsRecyclerViewAdapter.AlbumViewHolder> {
    private  List<Album> _Albums;
    private Context _Context;
    private ArrayList<Integer> ImagesList;
    public AlbumsRecyclerViewAdapter(Context _context, List<Album> _albums){
        this._Context= _context;
        this._Albums=_albums;

        /*I'm gonna create an array of Album arts i have in my drawables directory.
        * Please remove this code, after you bring in the AlbumArt in our model as part of the exercise
        * */
        ImagesList=new ArrayList<Integer>();
        ImagesList.add(R.drawable.cover_1);
        ImagesList.add(R.drawable.cover_2);
        ImagesList.add(R.drawable.cover_3);
        ImagesList.add(R.drawable.cover_4);
        ImagesList.add(R.drawable.cover_5);

    }
    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*this event is fired every time android wants to create a new column to our RecyclerView. We have to tell Android what layout we
         * need to inflate for that column
        */
        View _Item = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycer_view_item_layout, parent, false);
        return new AlbumViewHolder(_Item);
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        Album _Album= this._Albums.get(position);
        holder.AlbumTitle.setText(_Album.AlbumTitle);
        holder.AlbumGenre.setText(_Album.AlbumGenre);
        holder.AlbumPrice.setText("$"+String.valueOf(_Album.AlbumPrice));
        holder.AlbumAvailability.setText(_Album.Availability?"Available":"Out of stock");

        /* often time when we want to get access to our colors or drawables, we need to get to getResources(), this method can always be
         * found on the View or Context classs
        */

        if(_Album.Availability){
            holder.AlbumAvailability.setTextColor( holder.itemView.getResources().getColor(R.color.darkgreen));
        }else{
            holder.AlbumAvailability.setTextColor( holder.itemView.getResources().getColor(R.color.red));
        }
        Random random = new Random();
        int index = random.nextInt(5);

        /*
         * i wanted to get a random image from my images array to display it as the album art for that item
        */

        holder.AlbumCover.setImageDrawable( holder.itemView.getResources().getDrawable(ImagesList.get(index)));

    }

    @Override
    public int getItemCount() {
        return this._Albums.size();
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView AlbumTitle;
        public TextView AlbumGenre;
        public TextView AlbumPrice;
        public TextView AlbumAvailability;
        public ImageView AlbumCover;
        public AlbumViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            AlbumTitle = (TextView) itemView.findViewById(R.id.lblAlbumTitle);
            AlbumGenre = (TextView) itemView.findViewById(R.id.lblAlbumGenre);
            AlbumAvailability= (TextView) itemView.findViewById(R.id.lblAlbumAvailability);
            AlbumPrice= (TextView) itemView.findViewById(R.id.lblAlbmPrice);
            AlbumCover=(ImageView) itemView.findViewById(R.id.imgAlbumCover);
        }
        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();
        }
    }
}