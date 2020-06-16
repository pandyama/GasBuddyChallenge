package com.mp.gasbuddychallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var glm: GridLayoutManager
    var context = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        glm = GridLayoutManager(this,3)
        recyclerView.layoutManager = glm



        val imageSearch: EditText = search
        Toast.makeText(applicationContext, "Enter something to look pics for and press enter", Toast.LENGTH_LONG).show()

        //below code is for listening to a click event and looking for ENTER key being pressed
        imageSearch.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            try {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                    if (search.text.toString() != "") {
                        fetchImage()
                    } else {
                        Toast.makeText(applicationContext, "Please enter something", Toast.LENGTH_LONG).show()
                    }
                    return@OnKeyListener true
                } else {
                    return@OnKeyListener false
                }
            } catch (e: Exception) {
                return@OnKeyListener false
            } finally {
                return@OnKeyListener false
            }
        })

        //toggle between Linear layout view and Grid layout view when user switches the toggle button
        toggle.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener{button, isChecked ->
            if(isChecked){
                println("Nothing")
                toggle.setText("Linear View")
                setLinear()
            }
            else{
                //onResume()
                toggle.setText("Grid View")
                setGrid()
                println("nothing")
            }
        })

    }

    private fun setLinear(){
        recyclerView.layoutManager = LinearLayoutManager(context) //call recyclerview and change its layout manager to Linear layour
    }

    private fun setGrid(){
        recyclerView.layoutManager = GridLayoutManager(context, 3)  //call recyclerview and change its layout to Gridlayout with 3 columns
    }


    //fetchImage makes API call to pixabay source and retrieves images found from the user entered keyword
    private fun fetchImage() {
        var search = search.text.toString()                                                             //user entered keyword

        val url = "https://pixabay.com/api/?key=17028607-1eb3c33d1b6253c843777324f&q=${search}&per_page=25"    //url for API
        val request = Request.Builder().url(url).build()                                             //Request builder using the URL
        val client = OkHttpClient()                                                                           //OkHttpClient that will make the API call using request object from above
        val call = client.newCall(request)                                                              //call object

        call.enqueue(object : Callback {                                                                     //enqueue method schedules the request
            override fun onFailure(call: Call, e: IOException) {                                            //If request fails callback goes here
                Toast.makeText(applicationContext, "Request failed, check your internet connection", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call, response: Response) {                             //If request is successfull callback goes here
                var body = response.body()?.string()                                      //response object stored as string
                val gson = GsonBuilder().create()                                          //GSON helps with converting response data to class variables
                val imageFeed = gson.fromJson(body, Image::class.java)                    //conversion happens here where "body" is converted to list of images from data class Image below


                runOnUiThread {                                                                   //runOnUiThread needs to be called in order to show changes on the main UI
                    recyclerView.adapter = RecyclerAdapter(imageFeed.hits, context)              //recycler adapter instantiated here and sending list of images and current context
                }
            }

        })
    }
}


//Two data classes used to capture data from Pixabay API

data class Image(
    var total: Number,
    var hits: List<imageInfo>
)

data class imageInfo(
    var previewURL: String?,
    var likes: Number?,
    var downloads: Number?,
    var comments: Number?,
    var views: Number?,
    var user: String?
)

// Sample Response object from Pixabay API
//        {
//            "total": 13330,
//            "totalHits": 500,
//            "hits": [
//            {
//                "id": 690293,
//                "pageURL": "https://pixabay.com/photos/sky-clouds-sunlight-dark-690293/",
//                "type": "photo",
//                "tags": "sky, clouds, sunlight",
//                "previewURL": "https://cdn.pixabay.com/photo/2015/03/26/09/47/sky-690293_150.jpg",
//                "previewWidth": 150,
//                "previewHeight": 84,
//                "webformatURL": "https://pixabay.com/get/50e9d5414351b10ff3d8992cc62f377c1d39dee24e507440772d7dd59f45c6_640.jpg",
//                "webformatWidth": 640,
//                "webformatHeight": 360,
//                "largeImageURL": "https://pixabay.com/get/50e9d5414351b108f5d084609629307e1737d8e5544c704c7c2d79d29645cd5b_1280.jpg",
//                "imageWidth": 4474,
//                "imageHeight": 2517,
//                "imageSize": 2662228,
//                "views": 936385,
//                "downloads": 407871,
//                "favorites": 3047,
//                "likes": 3455,
//                "comments": 543,
//                "user_id": 242387,
//                "user": "Free-Photos",
//                "userImageURL": "https://cdn.pixabay.com/user/2014/05/07/00-10-34-2_250x250.jpg"
//            }
//         }