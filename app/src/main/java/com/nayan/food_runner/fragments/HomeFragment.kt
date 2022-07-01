package com.nayan.food_runner.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.nayan.food_runner.R
import com.nayan.food_runner.adapter.HomeRecyclerAdapter
import com.nayan.food_runner.models.Restaurants
import com.nayan.food_runner.util.ConnectionManager
import org.json.JSONException


class HomeFragment : Fragment() {

    lateinit var recyclerHome: RecyclerView
    lateinit var progressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: HomeRecyclerAdapter
    val resList = arrayListOf<Restaurants>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)

        progressLayout = view.findViewById(R.id.progressLayout)

        val queue = Volley.newRequestQueue(activity as Context)
        val url = "http://13.235.250.119/v2/restaurants/fetch_result/"

        if(ConnectionManager().checkConnectivity(activity as Context)){
            val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener{
                try {
                    progressLayout.visibility = View.GONE
                    val data = it.getJSONObject("data")

                    val success = data.getBoolean("success")
                    if (success) {
                        val resArray = data.getJSONArray("data")
                        for(i in 0 until resArray.length()){
                            val resJsonObject = resArray.getJSONObject(i)
                            val resObject = Restaurants(
                                resJsonObject.getString("id"),
                                resJsonObject.getString("name"),
                                resJsonObject.getString("rating"),
                                resJsonObject.getString("cost_for_one"),
                                resJsonObject.getString("image_url")
                            )
                            resList.add(resObject)


                        }
                        recyclerHome = view.findViewById(R.id.recyclerHome)
                        recyclerAdapter =
                            HomeRecyclerAdapter(activity as Context, resList)
                        layoutManager = LinearLayoutManager(activity)

                        recyclerHome.adapter = recyclerAdapter
                        recyclerHome.layoutManager = layoutManager
                    }else{
                        Toast.makeText(context,"Some unexpected error", Toast.LENGTH_SHORT).show()
                    }
                }catch (e: JSONException){
                    Toast.makeText(context,"Some unexpected error catch block", Toast.LENGTH_SHORT).show()
                }
            } , Response.ErrorListener {
                    if(activity != null)
                        Toast.makeText(context, "Volley error occured!!", Toast.LENGTH_SHORT).show()
                }){
                override fun getHeaders(): MutableMap<String, String> {
                    val header = HashMap<String, String>()
                    header["Content-type"] = "application/json"
                    header["token"] = "4b564f6ea2296c"
                    return header;
                }
            }
            queue.add(jsonObjectRequest)
        }else{
            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("Success")
            dialog.setMessage("Internet Connection Found")
            dialog.setPositiveButton("Open Settings") { text, listner ->
                val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(intent)
                activity?.finish()
            }
            dialog.setNegativeButton("Exit") { text, listner ->
                ActivityCompat.finishAffinity(activity as Activity)
            }
            dialog.create()
            dialog.show()
        }

        return view
    }


}