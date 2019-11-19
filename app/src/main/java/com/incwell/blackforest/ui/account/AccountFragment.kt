package com.incwell.blackforest.ui.account


import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.incwell.blackforest.R
import com.incwell.blackforest.data.model.*
import com.incwell.blackforest.data.storage.SharedPref
import com.incwell.blackforest.util.dropDown
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.fragment_account.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class AccountFragment : Fragment() {

    private val accountViewModel: AccountViewModel by viewModel()

    private lateinit var myCity: HashMap<String, Int>
    private lateinit var orderRecyclerView: RecyclerView

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

        orderRecyclerView = root.rv_order_history

        accountViewModel.getProfile()
        accountViewModel.profile.observe(this, Observer {
            root.accountFullName.setText(it.full_name)
            root.accountUserName.setText(it.username)
            root.accountEmail.setText(it.email)
            root.accountPhoneNumber.setText(it.phone)
            root.accountJoinedDate.setText(it.date_joined)
        })

        root.iv_show_hide1.setOnClickListener {
            if (root.cv_login_security_content.visibility == View.VISIBLE) {
                TransitionManager.beginDelayedTransition(root.cv_login_security, AutoTransition())
                root.cv_login_security_content.visibility = View.GONE
            } else {
                TransitionManager.beginDelayedTransition(root.cv_login_security, AutoTransition())
                root.cv_login_security_content.visibility = View.VISIBLE
            }
        }
        root.accountPhoneNumber.setOnClickListener {
            openDialogToChangePhoneNumber(root)
        }

        root.accountPassword.setOnClickListener {
            openDialogToChangePassword(root)
        }

        accountViewModel.getAddress()
        accountViewModel.address.observe(this, Observer {
            root.accountCity.setText(it.city)
            root.accountAddress.setText(it.address)
        })

        root.iv_show_hide2.setOnClickListener {
            if (root.cv_delivery_address_content.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(root.cv_delivery_address, AutoTransition())
                root.cv_delivery_address_content.visibility = View.VISIBLE
            } else {
                TransitionManager.beginDelayedTransition(root.cv_delivery_address, AutoTransition())
                root.cv_delivery_address_content.visibility = View.GONE
            }
        }

        root.iv_show_hide3.setOnClickListener {
            if (root.cv_order_history_content.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(root.cv_order_history, AutoTransition())
                root.cv_order_history_content.visibility = View.VISIBLE
            } else {
                TransitionManager.beginDelayedTransition(root.cv_order_history, AutoTransition())
                root.cv_order_history_content.visibility = View.GONE
            }
        }

        root.accountCity.setOnClickListener {
            openDialogToChangeCity(root)
        }

        root.accountAddress.setOnClickListener {
            openDialogToChangeAddress(root)
        }

        accountViewModel.getHistory()
        accountViewModel.orderHistory.observe(this, Observer {
            val adapter = OrderHistoryRecyclerAdapter(requireContext(), it!!)
            orderRecyclerView.adapter = adapter
        })

        return root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }

    private fun openDialogToChangePhoneNumber(root: View) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        val customView = layoutInflater.inflate(R.layout.custom_dialog_change_phone_number, null)
        val newPhoneNumber = customView.findViewById<TextInputEditText>(R.id.newPhoneNum)
        val cancelBtn = customView.findViewById<MaterialButton>(R.id.cancel)
        val changeBtn = customView.findViewById<MaterialButton>(R.id.changePhoneNumberBtn)
        val progressBar = customView.findViewById<ProgressBar>(R.id.pb_changePhoneNumber)

        alertDialogBuilder.setView(customView)
        val dialog = alertDialogBuilder.create()
        dialog.show()

        changeBtn.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val phoneNumber = PhoneNumber(phone_number = newPhoneNumber.text.toString())
            accountViewModel.changePhoneNumber(phoneNumber)
            accountViewModel.status.observe(this, Observer { status ->
                progressBar.visibility = View.GONE
                if (status) {
                    dialog.dismiss()
                    root.accountPhoneNumber.setText(newPhoneNumber.text.toString())
                } else {
                    dialog.dismiss()
                }
            })
        }
        cancelBtn.setOnClickListener {
            dialog.dismiss()
            // TODO: Display necessary stuffs
        }
    }

    private fun openDialogToChangePassword(root: View) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        val customView = layoutInflater.inflate(R.layout.custom_dialog_change_password, null)
        val oldPassword = customView.findViewById<TextInputEditText>(R.id.oldPassword)
        val newPassword = customView.findViewById<TextInputEditText>(R.id.newPassword)
        val confirmPassword = customView.findViewById<TextInputEditText>(R.id.confirmPassword)
        val cancelBtn = customView.findViewById<MaterialButton>(R.id.cancelPasswordChangeBtn)
        val changeBtn = customView.findViewById<MaterialButton>(R.id.changePasswordBtn)
        val progressBar = customView.findViewById<ProgressBar>(R.id.pb_changePassword)

        alertDialogBuilder.setView(customView)
        val dialog = alertDialogBuilder.create()
        dialog.show()

        changeBtn.setOnClickListener {
            if (newPassword == confirmPassword) {
                progressBar.visibility = View.VISIBLE
                val password = NewPassword(
                    old_password = oldPassword.text.toString(),
                    new_password = newPassword.text.toString(),
                    confirm_password = confirmPassword.text.toString()
                )
                accountViewModel.changePassword(password)
                accountViewModel.status.observe(this, Observer { status ->
                    progressBar.visibility = View.GONE
                    if (status) {
                        dialog.dismiss()
                        root.accountPassword.setText(newPassword.text.toString())
                    } else {
                        dialog.dismiss()
                    }
                })
            } else {
                // TODO: Display necessary stuffs
            }
        }

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

    }

    private fun openDialogToChangeCity(root: View) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        val customView = layoutInflater.inflate(R.layout.custom_dialog_change_city, null)
        val newCity = customView.findViewById<AutoCompleteTextView>(R.id.newCity)
        val cancelBtn = customView.findViewById<MaterialButton>(R.id.cancelCityChangeBtn)
        val changeBtn = customView.findViewById<MaterialButton>(R.id.changeCityBtn)
        val progressBar = customView.findViewById<ProgressBar>(R.id.pb_changeCity)

        alertDialogBuilder.setView(customView)
        val dialog = alertDialogBuilder.create()
        dialog.show()

        myCity = HashMap()
        for (i in 0 until SharedPref.getCity().size) {
            myCity[SharedPref.getCity()[i].city] = SharedPref.getCity()[i].id
        }
        dropDown(context!!, myCity.keys.toTypedArray(), newCity)

        changeBtn.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val city = NewCity(city = myCity[newCity.text.toString()]!!)
            accountViewModel.changeCity(city)
            accountViewModel.status.observe(this, Observer { status ->
                progressBar.visibility = View.GONE
                if (status) {
                    dialog.dismiss()
                    root.accountCity.setText(newCity.text.toString())
                } else {
                    dialog.dismiss()
                    // TODO: Display necessary stuffs
                }
            })
        }

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun openDialogToChangeAddress(root: View) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        val customView = layoutInflater.inflate(R.layout.custom_dialog_change_address, null)
        val newAddress = customView.findViewById<TextInputEditText>(R.id.newAddress)
        val cancelBtn = customView.findViewById<MaterialButton>(R.id.cancelAddressChangeBtn)
        val changeBtn = customView.findViewById<MaterialButton>(R.id.changeAddressBtn)
        val progressBar = customView.findViewById<ProgressBar>(R.id.pb_changeAddress)

        alertDialogBuilder.setView(customView)
        val dialog = alertDialogBuilder.create()
        dialog.show()

        changeBtn.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val address = Address(address = newAddress.text.toString())
            accountViewModel.changeAddress(address)
            accountViewModel.status.observe(this, Observer { status ->
                progressBar.visibility = View.GONE
                if (status) {
                    dialog.dismiss()
                    root.accountAddress.setText(newAddress.text.toString())
                } else {
                    dialog.dismiss()
                    // TODO: Display necessary stuffs
                }
            })
        }

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
    }

}
