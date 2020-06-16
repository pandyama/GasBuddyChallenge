package com.mp.gasbuddychallenge

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.imagecard.view.*


//Recycler Adapter class is responsible for populating the recycler view with images in the form of cards from imagecard.xml layout

class RecyclerAdapter(private var images: List<imageInfo>, val context: Context) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.imagecard, parent, false)
        return ViewHolder(v, context)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val img: imageInfo = images[position]
        holder.bindImages(img)
    }

    class ViewHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView) {

        fun bindImages(items: imageInfo) {
            var url = items.previewURL
            var uri = Uri.parse(url)

            itemView.by.text = "By - ${items.user.toString()}"

            itemView.image.layout(0, 0, 0, 0)

            Glide.with(context)
                .load(uri)
                .into(itemView.image)

            //below onclick listener redirects user to image details
            itemView.setOnClickListener {
                var intent = Intent(itemView.context, imagedetails::class.java)
                intent.putExtra("Image", items.previewURL)
                intent.putExtra("Views", items.views.toString())
                intent.putExtra("Likes", items.likes.toString())
                intent.putExtra("Comment", items.comments.toString())
                intent.putExtra("Downloads", items.downloads.toString())
                itemView.context.startActivity(intent)
            }


        }
    }
}