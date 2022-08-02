package com.example.digimanager.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.digimanager.R
import com.example.digimanager.adapters.BoardItemsAdapter
import com.example.digimanager.firestore.FirestoreClass
import com.example.digimanager.models.Board
import com.example.digimanager.models.User
import com.example.digimanager.utils.Constants
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.getInstance
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.*


class MainActivity : BaseActivity(),NavigationView.OnNavigationItemSelectedListener {

    companion object{
        const val MY_PROFILE_REQUEST_CODE : Int = 11
        const val CREATE_BOARD_REQUEST_CODE: Int = 12
    }

    private lateinit var mUserName: String
    // A global variable for SharedPreference
    private lateinit var mSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //sETTING ACTION BAR
        setupActionBar()
        nav_view.setNavigationItemSelectedListener(this@MainActivity)
        FirestoreClass().loadUserData(this, true)

        //Initialize the mSharedPreferences variable.
        mSharedPreferences =
            this.getSharedPreferences(Constants.DIGIMANAGER_PREFERENCES,
                MODE_PRIVATE
            )

        // Variable is used get the value either token is updated in the database or not.
        val tokenUpdated = mSharedPreferences.getBoolean(Constants.FCM_TOKEN_UPDATED, false)

        if (tokenUpdated) {
            // Get the current logged in user details.
            // Show the progress dialog.
            showProgressDialog(resources.getString(R.string.please_wait))
            FirestoreClass().loadUserData(this@MainActivity, true)
        }
        else{

        }

        //On clicking on floating point button
        fab_create_board.setOnClickListener{
            val intent = (Intent(this,
                CreateBoardActivity::class.java))
            intent.putExtra(Constants.NAME,mUserName)
            startActivityForResult(intent, CREATE_BOARD_REQUEST_CODE)

        }
    }

    fun populateBoardsListToUI(boardsList: ArrayList<Board>){
        hideProgressDialog()

        if(boardsList.size > 0){
            rv_boards_list.visibility = View.VISIBLE
            tv_no_boards_available.visibility = View.GONE

            rv_boards_list.layoutManager = LinearLayoutManager(this)
            rv_boards_list.setHasFixedSize(true)

            val adapter = BoardItemsAdapter(this,boardsList)
            rv_boards_list.adapter = adapter

            adapter.setOnClickListener(object :
                BoardItemsAdapter.OnClickListener {
                override fun onClick(position: Int, model: Board) {
                    val intent = Intent(this@MainActivity, TaskListActivity::class.java)
                    intent.putExtra(Constants.DOCUMENT_ID, model.documentId)
                    startActivity(intent)
                }
            })


        }else{
            rv_boards_list.visibility = View.GONE
            tv_no_boards_available.visibility = View.VISIBLE
        }
    }

    private fun setupActionBar(){
        setSupportActionBar(toolbar_main_activity)
        toolbar_main_activity.setNavigationIcon(R.drawable.ic_action_navigation_menu)

        toolbar_main_activity.setNavigationOnClickListener{
            //Toggle drawer
            toggleDrawer()

        }
    }

    private fun toggleDrawer(){
        if(drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawer(GravityCompat.START)
        }
        else{
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }

    //aDDING BACK FUNCTIONALITY ON TOOLBAR ICON
    override fun onBackPressed() {
        if(drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawer(GravityCompat.START)
        }
        else{
            doubleBackToExit()
        }
    }

    fun updateNavigationUserDetails(user: User, readBoardsList: Boolean){

        mUserName = user.name

        Glide
            .with(this)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.ic_user_place_holder)
            .into(iv_user_image);

        tv_username.text = user.name

        if(readBoardsList){
            showProgressDialog(resources.getString(R.string.please_wait))
            FirestoreClass().getBoardsList(this)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK
            && requestCode == MY_PROFILE_REQUEST_CODE){
              FirestoreClass().loadUserData(this)
        }
        else if(resultCode == RESULT_OK && requestCode == CREATE_BOARD_REQUEST_CODE){
            FirestoreClass().getBoardsList(this)

        }
        else{
            Log.e("Cancelled","Cancelled")
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.nav_my_profile -> {
                startActivityForResult(
                    Intent(this@MainActivity, MyProfileActivity::class.java),
                    MY_PROFILE_REQUEST_CODE
                )
            }
            R.id.nav_sign_out -> {
                // Here sign outs the user from firebase in this device.
                FirebaseAuth.getInstance().signOut()
                mSharedPreferences.edit().clear().apply()
                // Send the user to the intro screen of the application.
                val intent = Intent(this, IntroActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    /**
     * A function to notify the token is updated successfully in the database.
     */
    fun tokenUpdateSuccess() {

        hideProgressDialog()
        val editor: SharedPreferences.Editor = mSharedPreferences.edit()
        editor.putBoolean(Constants.FCM_TOKEN_UPDATED, true)
        editor.apply()

        // Get the current logged in user details.
        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().loadUserData(this@MainActivity, true)
    }

    /**
     * A function to update the user's FCM token into the database.
     */
    private fun updateFCMToken(token: String) {

        val userHashMap = HashMap<String, Any>()
        userHashMap[Constants.FCM_TOKEN] = token
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().updateUserProfileData(this@MainActivity, userHashMap)
    }

}