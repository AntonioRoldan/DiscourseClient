package io.keepcoding.eh_ho.posts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import io.keepcoding.eh_ho.ErrorRetryFragment
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.Topic
import io.keepcoding.eh_ho.data.TopicsRepo
import io.keepcoding.eh_ho.isFirsTimeCreated
import kotlinx.android.synthetic.main.activity_login.*

const val EXTRA_TOPIC = "topic"
const val TRANSACTION_CREATE_POST = "create_post"

class PostsActivity : AppCompatActivity(), PostsFragment.PostsInteractionListener, ErrorRetryFragment.ErrorRetryInteractionListener {

    val errorRetryFragment : ErrorRetryFragment = ErrorRetryFragment()
    var topic : Topic? = null
    val postsFragment : PostsFragment? by lazy {
        topic?.let {
            PostsFragment.newInstance(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)
        val bundle: Bundle = intent.getBundleExtra("bundle")
        topic = bundle.getParcelable(EXTRA_TOPIC)

        if (isFirsTimeCreated(savedInstanceState))
            postsFragment?.let {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, it)
                    .commit()
            }
    }

    override fun onCreatePost() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, CreatePostFragment())
            .addToBackStack(TRANSACTION_CREATE_POST)
            .commit()
    }

    override fun postsLoading(enabled: Boolean) {
        if (enabled) {
            fragmentContainer.visibility = View.INVISIBLE
            viewLoading.visibility = View.VISIBLE
        } else {
            fragmentContainer.visibility = View.VISIBLE
            viewLoading.visibility = View.INVISIBLE
        }
    }

    override fun somethingWentWrong() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, errorRetryFragment)
            .commit()
    }

    override fun onRetry() {
        postsFragment?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, it)
                .commit()
        }
    }


}