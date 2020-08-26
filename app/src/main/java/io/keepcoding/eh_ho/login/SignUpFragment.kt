package io.keepcoding.eh_ho.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.SignUpModel
import io.keepcoding.eh_ho.inflate
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.inputPassword
import kotlinx.android.synthetic.main.fragment_sign_up.inputUsername
import kotlinx.android.synthetic.main.fragment_sign_up.labelCreateAccount

class SignUpFragment : Fragment() {

    var signUpInteractionListener: SignUpInteractionListener?
        = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is SignUpInteractionListener)
            signUpInteractionListener = context
        else
            throw IllegalArgumentException("Context doesn't implement ${SignUpInteractionListener::class.java.canonicalName}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_sign_up)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonSignUp.setOnClickListener {
            val model = SignUpModel(
                inputUsername.text.toString(),
                inputEmail.text.toString(),
                inputPassword.text.toString()
            )

            signUpInteractionListener?.onSignUp(model) {
                checkIsError()
            }
        }

        labelCreateAccount.setOnClickListener {
            signUpInteractionListener?.onGoToSignIn()
        }
    }

    override fun onDetach() {
        super.onDetach()
        this.signUpInteractionListener = null
    }

    private fun checkIsError(): Boolean {
        if(!isFormValid()) {
            showErrors()
            return true
        }
        return false
    }

    private fun isFormValid(): Boolean {
        return inputUsername.text.isNotEmpty() && inputEmail.text.isNotEmpty() && inputPassword.text.isNotEmpty() && inputConfirmPassword.text.isNotEmpty() && inputConfirmPassword.text == inputPassword.text
    }

    private fun showErrors() {
        if(inputUsername.text.isEmpty())
            inputUsername.error = "Field must be filled"
        if(inputPassword.text.isEmpty())
            inputPassword.error = "You must provide a valid password"
        if(inputEmail.text.isEmpty())
            inputEmail.error = "Field must be filled"
        if(inputConfirmPassword.text.isEmpty())
            inputConfirmPassword.error = "Field must be filled"
        if(inputConfirmPassword.text != inputPassword.text && inputConfirmPassword.text.isNotEmpty() && inputPassword.text.isNotEmpty())
            inputConfirmPassword.error = "Passwords don't match"
    }

    interface SignUpInteractionListener {
        fun onGoToSignIn()
        fun onSignUp(signUpModel: SignUpModel, checkIsError: () -> Boolean)
    }
}