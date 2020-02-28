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

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

/***
 * Main Activity for the Material Me app, a mock sports news application.
 */
public class MainActivity extends AppCompatActivity {

    // Member variables.
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private ArrayList<Sport> mSportsData;
    private SportsAdapter mAdapter;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.mapFragment_container)
    FrameLayout mapLayout;
    private Boolean mapStatus = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Initialize the RecyclerView.
//        mRecyclerView = findViewById(R.id.recyclerView);
//        fab = findViewById(R.id.fab);
//        mapLayout = findViewById(R.id.mapFragment_container);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                MapFragment mapFragment = (MapFragment) fragmentManager.findFragmentByTag("map");

                if (mapStatus) {
                    Log.d("PANTEQ", "Removing fragment");
                    fab.setImageResource(R.drawable.ic_open);

                    if (mapFragment != null) {

                        fragmentTransaction.remove(mapFragment).commit();
                    }

//                    FragmentManager fragmentManager = getSupportFragmentManager();
//                    MapFragment mapFragment1 = fragmentManager.findFragmentById(R.id.mapFragment_container);
//
//                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                    mapStatus = !mapStatus;
                } else {
                    fab.setImageResource(R.drawable.ic_close);

                    mapFragment = new MapFragment();
//                    FragmentManager fragmentManager = getSupportFragmentManager();
//
//                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.mapFragment_container, mapFragment, "map").commit();


                    mapStatus = !mapStatus;
                }


            }
        });

        // Set the Layout Manager.
        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnCount));

        // Initialize the ArrayList that will contain the data.
        mSportsData = new ArrayList<>();

        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new SportsAdapter(this, mSportsData);
        mRecyclerView.setAdapter(mAdapter);

        // Get the data.
        initializeData();

        // Helper class for creating swipe to dismiss and drag and drop
        // functionality.

        int swipeDirs;
        if (gridColumnCount > 1) {
            swipeDirs = 0;
        } else {
            swipeDirs = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        }

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT |
                ItemTouchHelper.RIGHT |
                ItemTouchHelper.DOWN | ItemTouchHelper.UP,
                swipeDirs) {
            /**
             * Defines the drag and drop functionality.
             *
             * @param recyclerView The RecyclerView that contains the list items
             * @param viewHolder The SportsViewHolder that is being moved
             * @param target The SportsViewHolder that you are switching the
             *               original one with.
             * @return true if the item was moved, false otherwise
             */
            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                // Get the from and to positions.
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();

                // Swap the items and notify the adapter.
                Collections.swap(mSportsData, from, to);
                mAdapter.notifyItemMoved(from, to);
                return true;
            }

            /**
             * Defines the swipe to dismiss functionality.
             *
             * @param viewHolder The viewholder being swiped.
             * @param direction The direction it is swiped in.
             */
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                 int direction) {
                // Remove the item from the dataset.
                mSportsData.remove(viewHolder.getAdapterPosition());
                // Notify the adapter.
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });

        // Attach the helper to the RecyclerView.
        helper.attachToRecyclerView(mRecyclerView);
    }

    /**
     * Initialize the sports data from resources.
     */
    private void initializeData() {
        // Get the resources from the XML file.
        String[] sportsList = getResources()
                .getStringArray(R.array.sports_titles);
        String[] sportsInfo = getResources()
                .getStringArray(R.array.sports_info);
        TypedArray sportsImageResources = getResources()
                .obtainTypedArray(R.array.sports_images);

        // Clear the existing data (to avoid duplication).
        mSportsData.clear();

        // Create the ArrayList of Sports objects with the titles and
        // information about each sport
        for (int i = 0; i < sportsList.length; i++) {
            mSportsData.add(new Sport(sportsList[i], sportsInfo[i],
                    sportsImageResources.getResourceId(i, 0)));
        }

        // Recycle the typed array.
        sportsImageResources.recycle();

        // Notify the adapter of the change.
        mAdapter.notifyDataSetChanged();
    }

    /**
     * onClick method for th FAB that resets the data.
     *
     * @param view The button view that was clicked.
     */
//    public void resetSports(View view) {
//        initializeData();
//    }
}
