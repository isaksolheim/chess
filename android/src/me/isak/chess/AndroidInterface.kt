package me.isak.chess

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import me.isak.chess.models.FirebaseGameModel

/**
 * Implements FirebaseInterface to interact with Firebase Database.
 * Provides methods to get data, push values to a specific path, and set values at the root.
 */
class AndroidInterface : FirebaseInterface {
    private val database = FirebaseDatabase.getInstance()

    override fun getData(key: String) {
        val ref = database.getReference("/").child(key)

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.value
                Log.d("FirebaseGetData", "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("FirebaseGetData", "Failed to read value.", error.toException())
            }
        })
    }

    override fun pushValue(key: String, value: FirebaseGameModel) {
        val ref = database.getReference("/games").child(key)

        ref.setValue(value)
            .addOnSuccessListener {
                Log.d("FirebaseDebug", "Data push successful")
            }
            .addOnFailureListener { exception ->
                Log.d("FirebaseDebug", "Data push failed: ${exception.message}")
            }
    }

    override fun setValue(key: String, value: FirebaseGameModel) {
        val ref = database.getReference("/")

        ref.child(key).setValue(value)
            .addOnSuccessListener {
                Log.d("FirebaseDebug", "Data set successful")
            }
            .addOnFailureListener { exception ->
                Log.d("FirebaseDebug", "Data set failed: ${exception.message}")
            }
    }
}
