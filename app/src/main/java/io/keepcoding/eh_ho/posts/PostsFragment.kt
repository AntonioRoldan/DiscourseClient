package io.keepcoding.eh_ho.posts

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.PostsRepo
import io.keepcoding.eh_ho.data.Topic
import io.keepcoding.eh_ho.inflate
import io.keepcoding.eh_ho.posts.PostsAdapter
import kotlinx.android.synthetic.main.fragment_posts.*


const val ARG_TOPIC = "topic"

class PostsFragment : Fragment() {


    var topic: Topic? = null
    var postsInteractionListener: PostsFragment.PostsInteractionListener? = null

    private val postsAdapter: PostsAdapter by lazy {
        val adapter = PostsAdapter {
            //TODO: Reply to post
        }
        adapter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        topic = arguments?.getParcelable<Topic>(ARG_TOPIC)
        if (context is PostsFragment.PostsInteractionListener)
            postsInteractionListener = context
        else
            throw IllegalArgumentException("Context doesn't implement ${PostsFragment.PostsInteractionListener::class.java.canonicalName}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_posts)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        postsAdapter.setPosts(PostsRepo.posts)

        listPosts.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listPosts.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        listPosts.adapter = postsAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_posts, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onResume() {
        super.onResume()

        loadPosts()
    }

    private fun loadPosts() {
        postsInteractionListener?.postsLoading(true)
        context?.let { it ->
            topic?.id?.let { topicId ->

                PostsRepo
                    .getPosts(it.applicationContext,
                        topicId,
                        {posts ->
                            postsAdapter.setPosts(posts)
                            postsInteractionListener?.postsLoading(false)
                        },
                        {
                            postsInteractionListener?.postsLoading(false)
                            postsInteractionListener?.somethingWentWrong()
                        }
                    )
            }
        }
    }

    companion object {
        fun newInstance(topic: Topic): PostsFragment? {
            val fragment = PostsFragment()
            val args = Bundle()
            args.putParcelable(ARG_TOPIC, topic)

            fragment.arguments = args

            return fragment
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_create_post -> this.postsInteractionListener?.onCreatePost()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDetach() {
        super.onDetach()
        postsInteractionListener = null
    }

    interface PostsInteractionListener {
        fun onCreatePost()
        fun postsLoading(enabled: Boolean)
        fun somethingWentWrong()
    }
}