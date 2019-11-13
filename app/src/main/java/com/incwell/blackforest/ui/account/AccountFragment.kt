package com.incwell.blackforest.ui.account


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.incwell.blackforest.R
import kotlinx.android.synthetic.main.fragment_account.view.*

class AccountFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_account, container, false)

        root.iv_show_hide1.setOnClickListener {
            if (root.cv_login_security_content.visibility == View.VISIBLE) {
                TransitionManager.beginDelayedTransition(root.cv_login_security, AutoTransition())
                root.cv_login_security_content.visibility = View.GONE
            } else {
                TransitionManager.beginDelayedTransition(root.cv_login_security, AutoTransition())
                root.cv_login_security_content.visibility = View.VISIBLE
            }
        }

        root.iv_show_hide2.setOnClickListener {
            if (root.cv_delivery_address_content.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(root.cv_delivery_address, AutoTransition())
                root.cv_delivery_address_content.visibility = View.VISIBLE
            } else {
                TransitionManager.beginDelayedTransition(root.cv_delivery_address, AutoTransition())
                root.cv_delivery_address_content.visibility = View.GONE
            }
        }
        return root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }
}
