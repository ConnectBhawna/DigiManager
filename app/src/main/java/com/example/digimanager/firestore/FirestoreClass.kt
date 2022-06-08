package com.example.digimanager.firestore

import android.annotation.SuppressLint
import android.util.Log
import com.example.digimanager.activities.SignInActivity
import com.example.digimanager.activities.SignUpScreen
import com.example.digimanager.models.User
import com.example.digimanager.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions


class FirestoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()

    /**
     * A function to make an entry of the registered user in the firestore database.
     */
    fun registerUser(activity: SignUpScreen, userInfo: User) {
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegisteredSuccess()
            }
            .addOnFailureListener { e ->
                Log.e(
                    activity.javaClass.simpleName,
                    "Error writing document",
                    e
                )
            }

    }


    fun signInUser(activity: SignInActivity){
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->
                val loggedInUser = document.toObject(User::class.java)
                if(loggedInUser != null)
                    activity.signInSuccess(loggedInUser)
            }
            .addOnFailureListener { e ->
                Log.e(
                    "SignInUser",
                    "Error writing document",
                    e
                )
            }

    }

    fun getCurrentUserId() : String{

        return FirebaseAuth.getInstance().currentUser!!.uid
    }
}