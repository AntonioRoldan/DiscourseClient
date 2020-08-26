package io.keepcoding.eh_ho

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.keepcoding.eh_ho.topics.TopicsFragment
import kotlinx.android.synthetic.main.fragment_view_error_retry.*

class ErrorRetryFragment : Fragment() {

    var errorRetryInteractionListener : ErrorRetryFragment.ErrorRetryInteractionListener? = null
    var topicsInteractionListener: TopicsFragment.TopicsInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ErrorRetryFragment.ErrorRetryInteractionListener && context is TopicsFragment.TopicsInteractionListener) {
            errorRetryInteractionListener = context
            topicsInteractionListener = context
        } else
            throw IllegalArgumentException("Context doesn't implement ${ErrorRetryFragment.ErrorRetryInteractionListener::class.java.canonicalName}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_view_error_retry)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonRetry.setOnClickListener {
            this.errorRetryInteractionListener?.onRetry()
        }

        buttonCreate.setOnClickListener {
            this.topicsInteractionListener?.onCreateTopic()
        }
    }

    override fun onDetach() {
        super.onDetach()
        errorRetryInteractionListener = null
    }

    interface ErrorRetryInteractionListener {
        fun onRetry()
    }
}