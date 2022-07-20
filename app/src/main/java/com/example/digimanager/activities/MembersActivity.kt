package com.example.digimanager.activities

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.digimanager.R
import com.example.digimanager.adapters.MemberListItemsAdapter
import com.example.digimanager.firestore.FirestoreClass
import com.example.digimanager.models.Board
import com.example.digimanager.models.User
import com.example.digimanager.utils.Constants
import kotlinx.android.synthetic.main.activity_members.*
import kotlinx.android.synthetic.main.dialog_search_member.*

class MembersActivity : BaseActivity() {

    // A global variable for Board Details.
    private lateinit var mBoardDetails: Board

    // A global variable for Assigned Members List.
    private lateinit var mAssignedMembersList:ArrayList<User>

    // A global variable for notifying any changes done or not in the assigned members list.
    private var anyChangesDone: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_members)

        // Get the Board Details through intent and assign it to the global variable.)
        if (intent.hasExtra(Constants.BOARD_DETAIL)) {
            mBoardDetails = intent.getParcelableExtra<Board>(Constants.BOARD_DETAIL)!!
        }
        setupActionBar()
        // Call the setup action bar function.)
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getAssignedMembersListDetails(
            this@MembersActivity,
            mBoardDetails.assignedTo
        )
    }


    /**
     * A function to setup action bar
     */
    private fun setupActionBar() {
        setSupportActionBar(toolbar_members_activity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
            actionBar.title = resources.getString(R.string.members)
        }
        toolbar_members_activity.setNavigationOnClickListener { onBackPressed() }
    }

    // Inflate the menu file for adding the member and also add the onOptionItemSelected function.)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu to use in the action bar
        menuInflater.inflate(R.menu.menu_add_member, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.action_add_member -> {
                dialogSearchMember()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    /**
     * A function to setup assigned members list into recyclerview.
     */
    fun setupMembersList(list: ArrayList<User>) {
        mAssignedMembersList = list
        hideProgressDialog()
        rv_members_list.layoutManager = LinearLayoutManager(this@MembersActivity)
        rv_members_list.setHasFixedSize(true)
        val adapter = MemberListItemsAdapter(this@MembersActivity, list)
        rv_members_list.adapter = adapter
    }

    /**
      Method is used to show the Custom Dialog.
     */
    private fun dialogSearchMember() {
        val dialog = Dialog(this)
        /*Set the screen content from a layout resource.
    The resource will be inflated, adding all top-level views to the screen.*/
        dialog.setContentView(R.layout.dialog_search_member)
        dialog.tv_add.setOnClickListener(View.OnClickListener {
            val email = dialog.et_email_search_member.text.toString()

            if (email.isNotEmpty()) {
                dialog.dismiss()
                showProgressDialog(resources.getString(R.string.please_wait))
                FirestoreClass().getMemberDetails(this@MembersActivity, email)
            } else {
                Toast.makeText(
                    this@MembersActivity,
                    "Please enter members email address.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        dialog.tv_cancel.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })
        dialog.show()
    }

    fun memberDetails(user: User) {

        mBoardDetails.assignedTo.add(user.id)
        FirestoreClass().assignMemberToBoard(this@MembersActivity, mBoardDetails, user)

    }

    override fun onBackPressed() {
        if (anyChangesDone) {
            setResult(Activity.RESULT_OK)
        }
        super.onBackPressed()

    }
        /**
     * A function to get the result of assigning the members.
     */
    fun memberAssignSuccess(user: User) {
        hideProgressDialog()
        mAssignedMembersList.add(user)
        anyChangesDone = true
        setupMembersList(mAssignedMembersList)
    }
}

