package io.keepcoding.eh_ho.posts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.keepcoding.eh_ho.ErrorRetryFragment
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.Topic
import io.keepcoding.eh_ho.data.TopicsRepo
import io.keepcoding.eh_ho.isFirsTimeCreated

const val EXTRA_TOPIC_ID = "TOPIC_ID"

class PostsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        val postsFragment : PostsFragment = PostsFragment()
        val errorRetryFragment : ErrorRetryFragment = ErrorRetryFragment()

        val topicId: String = intent.getStringExtra(EXTRA_TOPIC_ID) ?: ""
        val topic: Topic? = TopicsRepo.getTopic(topicId)

        if (isFirsTimeCreated(savedInstanceState))
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, postsFragment)
                .commit()
    }



}