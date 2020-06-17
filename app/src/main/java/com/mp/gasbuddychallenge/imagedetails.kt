package com.mp.gasbuddychallenge

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_imagedetails.*

//This activity shows image details and it populates data received from the intent object and reads it from a bundle
class imagedetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imagedetails)


        var bundle = intent.extras

        val actionbar = supportActionBar
        actionbar!!.title = "${bundle!!.getString("Tags")}"
        actionbar.setDisplayHomeAsUpEnabled(true)


        viewsCount.setText(bundle!!.getString("Views").toString())
        likesCount.setText(bundle!!.getString("Likes").toString())
        commentCount.setText(bundle!!.getString("Comment").toString())
        downloadCount.setText(bundle!!.getString("Downloads").toString())

        var url = bundle!!.getString("Image").toString()
        var uri = Uri.parse(url)

        var url2 = bundle!!.getString("UserImage").toString()
        var uri2 = Uri.parse(url2)

        Glide.with(this)
            .load(uri)
            .into(imageView)

        Glide.with(this)
            .load(uri2)
            .into(userimg)

        detailsupload.setText("By - ${bundle!!.getString("user").toString()}")

        Toast.makeText(this, "Likes, Views, Comments, Downloads", Toast.LENGTH_SHORT).show()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        finish()
        return true
    }
}
