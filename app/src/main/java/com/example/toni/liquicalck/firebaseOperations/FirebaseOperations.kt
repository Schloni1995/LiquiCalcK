package com.example.toni.liquicalck.firebaseOperations

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FirebaseOperations {
    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.getReference("message")
    fun test() {
        myRef.setValue("Hello, World!")
        Log.d("Firebase", "Value inserted")


        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue<String>(String::class.java)
                Log.d("Firebase", "Value is: " + value!!)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("Firebase", "Failed to read value.", error.toException())
            }
        })
    }

}