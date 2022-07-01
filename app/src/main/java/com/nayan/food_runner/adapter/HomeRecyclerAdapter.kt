package com.nayan.food_runner.adapter

import android.content.Context
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.nayan.food_runner.R
import com.nayan.food_runner.database.RestaurantDatabase
import com.nayan.food_runner.database.RestaurantEntity
import com.nayan.food_runner.models.Restaurants
import com.squareup.picasso.Picasso

class HomeRecyclerAdapter(val context: Context, val restaurantList: ArrayList<Restaurants>):
    RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder>() {

    class HomeViewHolder(view: View): RecyclerView.ViewHolder(view){
        val txtResName: TextView = view.findViewById(R.id.txtResName)
        val txtResPrice: TextView = view.findViewById(R.id.txtResPrice)
        val imgResImage : ImageView = view.findViewById(R.id.imgResImage)
        val imgFavImage : ImageView = view.findViewById(R.id.imgSelectFavourite)
        val txtRating: TextView = view.findViewById(R.id.txtResRatings)
        val llContent : LinearLayout = view.findViewById(R.id.llContent)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_home_single_row,parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val res = restaurantList[position]
        holder.txtResName.text = res.resName
        holder.txtResPrice.text = res.costForOne+"/person"
        holder.txtRating.text = res.resRatings
        Picasso.get().load(res.resImage).error(R.drawable.ic_launcher_background).into(holder.imgResImage)
        //new code
        val restoEntity = RestaurantEntity(
            res.resId,
            res.resName,
            res.resRatings,
            res.costForOne,
            res.resImage
        )

        val checkFav = DBAsyncTask(context, restoEntity, 1).execute()
        val isFav = checkFav.get()


        if(isFav){
            holder.imgFavImage.setImageResource(R.drawable.ic_favourite_filled)
        }else{
            holder.imgFavImage.setImageResource(R.drawable.ic_favourite_border_red)

        }
        //new code finish

        holder.llContent.setOnClickListener {
//            val intent = Intent(context, DescriptionActivity::class.java)
//            intent.putExtra("book_id", book.bookId)
//            context.startActivity(intent)
            Toast.makeText(context, "${holder.txtResName.text} clicked", Toast.LENGTH_SHORT).show()

        }
        holder.imgFavImage.setOnClickListener {



            if(!DBAsyncTask(context, restoEntity, 1).execute().get()){


                val async = DBAsyncTask(context, restoEntity, 2).execute()
                val result = async.get()
                if(result){

                    holder.imgFavImage.setImageResource(R.drawable.ic_favourite_filled)

                }else{
                    Toast.makeText(context,
                        "Some Error Occurred",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else{


                val async = DBAsyncTask(context, restoEntity, 3).execute()
                val result = async.get()


                if(result){
                    holder.imgFavImage.setImageResource(R.drawable.ic_favourite_border_red)

                }else{
                    Toast.makeText(context,
                        "Some Error Occurred",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }


    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    class DBAsyncTask(val context: Context, val restoEntity: RestaurantEntity, val mode: Int): AsyncTask<Void, Void, Boolean>(){

        /*

            Mode1 = check if the book is in favourite or not
            Mode2 = save in favourites
            Mode3 = remove from favourites

         */

        val db = Room.databaseBuilder(context, RestaurantDatabase::class.java, "resto_db").build()
        override fun doInBackground(vararg p0: Void?): Boolean {

            when(mode) {
                1 -> {
//                    check DB if the book is in favourite or not
                    val restoEntity:RestaurantEntity? = db.restoDao().getRestoById(restoEntity.resId)
                    db.close()
                    return restoEntity !=null
                }
                2 -> {
//                    save in favourites
                    db.restoDao().insertResto(restoEntity)
                    db.close()
                    return true
                }
                3 -> {
//                    remove from fav
                    db.restoDao().deleteResto(restoEntity)
                    db.close()
                    return true
                }
            }
            return false
        }

    }


}