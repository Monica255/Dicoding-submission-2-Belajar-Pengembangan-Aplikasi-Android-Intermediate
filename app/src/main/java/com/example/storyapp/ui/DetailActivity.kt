package com.example.storyapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.storyapp.ListStoryPaging
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityDetailBinding
import com.example.storyapp.helper.DateConverter
import com.example.storyapp.helper.LocationConverter

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val story = intent.getParcelableExtra<ListStoryPaging>(EXTRA_STORY) as ListStoryPaging
        setActionBar(story.name.toString())
        setStory(story)

    }

    private fun setStory(story: ListStoryPaging) {
        binding.apply {
            tvName.text = story.name
            tvDate.text = resources.getString(
                R.string.date,
                DateConverter.mouth(story.createdAt),
                DateConverter.day(story.createdAt),
                DateConverter.year(story.createdAt)
            )
            etDes.text = story.description
        }
        binding.tvLocation.text = LocationConverter.getStringAddress(
            LocationConverter.toLatlng(story.lat, story.lon),
            this
        )
        Glide.with(this)
            .load(story.photoUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(binding.imgPhoto)
    }

    @SuppressLint("RestrictedApi")
    private fun setActionBar(story: String) {
        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.detail_title, story)
        actionBar?.setDefaultDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_STORY = "extra_story"
    }
}