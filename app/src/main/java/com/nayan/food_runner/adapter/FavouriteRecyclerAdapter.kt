package com.nayan.food_runner.adapter

import android.content.Context
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.nayan.food_runner.R
import com.nayan.food_runner.database.RestaurantDatabase
import com.nayan.food_runner.database.RestaurantEntity
import com.squareup.picasso.Picasso

class FavouriteRecyclerAdapter(val context: Context,  val restoList: ArrayList<RestaurantEntity>): RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteViewHolder>(){

    class FavouriteViewHolder(view: View): RecyclerView.ViewHolder(view){
        val txtResName: TextView = view.findViewById(R.id.txtResNameFav)
        val txtResPrice: TextView = view.findViewById(R.id.txtResPriceFav)
        val imgResImage : ImageView = view.findViewById(R.id.imgResImageFav)
        val imgFavImage : ImageView = view.findViewById(R.id.imgSelectFavouriteFav)
        val txtRating: TextView = view.findViewById(R.id.txtResRatingsFav)
        val llContent : LinearLayout = view.findViewById(R.id.llContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_favourite_single_row, parent, false)
        return FavouriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val res = this.restoList[position]

        holder.txtResName.text = res.resName
        holder.txtResPrice.text = res.resCost+"/person"
        holder.txtRating.text = res.resRating
        Picasso.get().load(res.resImage).error(R.drawable.ic_launcher_background).into(holder.imgResImage)

        holder.llContent.setOnClickListener {
//            val intent = Intent(context, DescriptionActivity::class.java)
//            intent.putExtra("book_id", book.bookId)
//            context.startActivity(intent)
            Toast.makeText(context, "${holder.txtResName.text} clicked", Toast.LENGTH_SHORT).show()

        }

        holder.imgFavImage.setOnClickListener {
            val async = DBAsyncTask(context, res, 3).execute()
            val result = async.get()
            restoList.removeAt(position)
            notifyDataSetChanged()

            if(!result){
                Toast.makeText(context,
                    "Some Error Occurred",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return restoList.size
    }

    class DBAsyncTask(val context: Context, val restoEntity: RestaurantEntity, val mode: Int): AsyncTask<Void, Void, Boolean>(){



        val db = Room.databaseBuilder(context, RestaurantDatabase::class.java, "resto_db").build()
        override fun doInBackground(vararg p0: Void?): Boolean {

            when(mode) {
                3 -> {
//                    remove from fav
                    db.restaurantDao().deleteRestaurant(restoEntity)
                    db.close()
                    return true
                }
            }
            return false
        }

    }


}