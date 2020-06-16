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

        val actionbar = supportActionBar
        actionbar!!.title = "Image details"
        actionbar.setDisplayHomeAsUpEnabled(true)

        var bundle = intent.extras

        viewsCount.setText(bundle!!.getString("Views").toString())
        likesCount.setText(bundle!!.getString("Likes").toString())
        commentCount.setText(bundle!!.getString("Comment").toString())
        downloadCount.setText(bundle!!.getString("Downloads").toString())

        var url = bundle!!.getString("Image").toString()
        var uri = Uri.parse(url)

        Glide.with(this)
            .load(uri)
            .into(imageView)

        Toast.makeText(this, "Likes, Views, Comments, Downloads", Toast.LENGTH_LONG).show()

        imageURL.setOnClickListener {
            var i = Intent(Intent.ACTION_VIEW)
            i.setData(uri)
            startActivity(i)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        finish()
        return true
    }
}
