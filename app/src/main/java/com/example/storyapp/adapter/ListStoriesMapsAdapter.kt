package com.example.storyapp.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.ListStory
import com.example.storyapp.R
import com.example.storyapp.databinding.ItemRowStoryMapsBinding
import com.example.storyapp.helper.LocationConverter
import com.google.android.gms.maps.model.LatLng


class ListStoryMapsAdapter(private val listStory: List<ListStory>) :
    RecyclerView.Adapter<ListStoryMapsAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemRowStoryMapsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listStory[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listStory[holder.bindingAdapterPosition])
        }
    }

    class ListViewHolder(private var binding: ItemRowStoryMapsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListStory) {
            val latlng: LatLng? = LocationConverter.toLatlng(data.lat, data.lon)
            binding.iconLocationAvailable.visibility =
                if (latlng != null) View.VISIBLE else View.GONE
            binding.tvName.text = data.name
            Glide.with(itemView.context)
                .load(data.photoUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .fallback(R.drawable.ic_launcher_foreground)
                .into(binding.imgPhoto)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ListStory)
    }

    override fun getItemCount(): Int = listStory.size


}