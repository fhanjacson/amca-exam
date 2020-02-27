/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.tp045027;

import android.content.Context;
import android.content.Intent;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/***
 * The adapter class for the RecyclerView, contains the sports data.
 */
class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.ViewHolder>  {

    // Member variables.
//    private ArrayList<Sport> mSportsData;
    private Context mContext;
    private ArrayList<Location> locations;

    SportsAdapter(Context context) {
//        this.mSportsData = sportsData;
        this.locations = Repository.getInstance().getListData();
        this.mContext = context;
    }


    /**
     * Required method for creating the viewholder objects.
     *
     * @param parent The ViewGroup into which the new View will be added
     *               after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return The newly created ViewHolder.
     */
    @Override
    public SportsAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.list_item, parent, false));
    }

    /**
     * Required method that binds the data to the viewholder.
     *
     * @param holder The viewholder into which the data should be put.
     * @param position The adapter position.
     */
    @Override
    public void onBindViewHolder(SportsAdapter.ViewHolder holder,
                                 int position) {
        // Get current sport.
//        Sport currentSport = loca.get(position);

        // Populate the textviews with data.
        holder.bindTo(locations.get(position));
        holder.cardView.setOnClickListener(view -> {
            Location currentLocation = locations.get(position);

            Bundle bundle = new Bundle();
            bundle.putSerializable("location", currentLocation);

            Intent detailIntent = new Intent(mContext, DetailActivity.class);
            detailIntent.putExtras(bundle);

            mContext.startActivity(detailIntent);
        });
    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return locations.size();
    }


    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        // Member Variables for the TextViews
        private TextView mTitleText;
        private TextView mInfoText;
        private ImageView mSportsImage;
        private CardView cardView;

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item.xml layout file.
         */
        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            mTitleText = itemView.findViewById(R.id.title);
            mInfoText = itemView.findViewById(R.id.subTitle);
            mSportsImage = itemView.findViewById(R.id.sportsImage);
            cardView = itemView.findViewById(R.id.cardView);

            // Set the OnClickListener to the entire view.
//            itemView.setOnClickListener(this);
        }

//        void bindTo(Sport currentSport){
//            // Populate the textviews with data.
//            mTitleText.setText(currentSport.getTitle());
//            mInfoText.setText(currentSport.getInfo());
//
//            // Load the images into the ImageView using the Glide library.
//            Glide.with(mContext).load(
//                    currentSport.getImageResource()).into(mSportsImage);
//        }

        void bindTo(Location currentLocation){
            // Populate the textviews with data.
            mTitleText.setText(currentLocation.getName());

//            mTitleText.setText("LOLOL");

//            mInfoText.setText("lat: " + currentLocation.getCoordinate().getLattitude() + " long: " + currentLocation.getCoordinate().getLongitude());

            // Load the images into the ImageView using the Glide library.
//            Glide.with(mContext).load(
//                    currentSport.getImageResource()).into(mSportsImage);
        }

        /**
         * Handle click to show DetailActivity.
         *
         * @param view View that is clicked.
         */
//        @Override
//        public void onClick(View view) {
//            Sport currentSport = mSportsData.get(getAdapterPosition());
//            Intent detailIntent = new Intent(mContext, DetailActivity.class);
//            detailIntent.putExtra("title", currentSport.getTitle());
//            detailIntent.putExtra("image_resource",
//                    currentSport.getImageResource());
//            mContext.startActivity(detailIntent);
//        }
    }

    public void setData(ArrayList<Location> list)
    {
        locations = list;
        notifyDataSetChanged();
    }
}
