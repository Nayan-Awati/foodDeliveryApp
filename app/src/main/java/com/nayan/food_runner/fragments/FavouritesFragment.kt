package com.nayan.food_runner.fragments

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.nayan.food_runner.R
import com.nayan.food_runner.adapter.FavouriteRecyclerAdapter
import com.nayan.food_runner.database.RestaurantDatabase
import com.nayan.food_runner.database.RestaurantEntity


class FavouritesFragment : Fragment() {


    lateinit var progressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar
    lateinit var recyclerFavourite: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: FavouriteRecyclerAdapter
    var db_restoList = listOf<RestaurantEntity>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=  inflater.inflate(R.layout.fragment_favourites, container, false)
        progressLayout = view.findViewById(R.id.progressLayout)
        recyclerFavourite = view.findViewById(R.id.recyclerFavourite)
        layoutManager = LinearLayoutManager(activity)
        db_restoList = RetrieveFavourites(activity as Context).execute().get()

        if(activity != null){
            progressLayout.visibility = View.GONE
            recyclerAdapter = FavouriteRecyclerAdapter(activity as Context, ArrayList(db_restoList))
            recyclerFavourite.adapter = recyclerAdapter
            recyclerFavourite.layoutManager = layoutManager
        }


        return view
    }

    class RetrieveFavourites(val context: Context): AsyncTask<Void, Void, List<RestaurantEntity>>(){

        override fun doInBackground(vararg p0: Void?): List<RestaurantEntity> {
            val db = Room.databaseBuilder(context, RestaurantDatabase::class.java, "resto_db").build()
            return db.restoDao().getAllResto()
        }

    }


}