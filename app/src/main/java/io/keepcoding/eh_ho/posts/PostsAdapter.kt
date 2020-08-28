package io.keepcoding.eh_ho.posts

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.Post
import io.keepcoding.eh_ho.inflate
import kotlinx.android.synthetic.main.item_post.view.*
import java.lang.IllegalArgumentException

class PostsAdapter(val postClickListener: ((Post) -> Unit)? = null) :
    RecyclerView.Adapter<PostsAdapter.PostHolder>() {

    private val posts = mutableListOf<Post>()

    private val listener: ((View) -> Unit) = {
        if (it.tag is Post) {
            postClickListener?.invoke(it.tag as Post)
        } else {
            throw IllegalArgumentException("Post item view has not a Post Data as a tag")
        }

    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onCreateViewHolder(list: ViewGroup, viewType: Int): PostHolder {
        val view = list.inflate(R.layout.item_post)
        return PostHolder(view)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val post = posts[position]
        holder.post = post
        holder.itemView.setOnClickListener(listener)
    }

    fun setPosts(post: List<Post>) {
        this.posts.clear()
        this.posts.addAll(post)
        notifyDataSetChanged()
    }

    inner class PostHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var post: Post? = null
            set(value) {
                field = value
                itemView.tag = field

                field?.let {
                    itemView.labelAuthor.text = it.author
                    itemView.labelContent.text = it.content.toString()
                    itemView.labelDate.text = it.date.toString()
                }
            }
    }

}
