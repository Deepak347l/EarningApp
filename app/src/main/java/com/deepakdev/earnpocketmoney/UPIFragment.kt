package com.deepakdev.earnpocketmoney

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.annotations.Nullable


class UPIFragment : Fragment() {
    private var notesRV: RecyclerView? = null
    private lateinit var loadingPB: ProgressBar
    private lateinit var  notesRVModalArrayList: ArrayList<UPIModel>
    private lateinit var notesRVAdapter: UPIAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_u_p_i, container, false
        )
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //data retrive for rcv
        notesRV = view.findViewById(R.id.rcbUPI)
        loadingPB = view.findViewById(R.id.progress_small1XXXUPI)
        // mAuth = FirebaseAuth.getInstance()
        notesRVModalArrayList = ArrayList()
        // on below line initializing our adapter class.
        // on below line initializing our adapter class.
        notesRVAdapter =  UPIAdapter(notesRVModalArrayList, context!!)
        // setting layout malinger to recycler view on below line.
        // setting layout malinger to recycler view on below line.
        notesRV!!.layoutManager = LinearLayoutManager(context!!)
        LinearLayoutManager(context!!).reverseLayout = true
        LinearLayoutManager(context!!).stackFromEnd = true
        // setting adapter to recycler view on below line.
        // setting adapter to recycler view on below line.
        notesRV!!.adapter = notesRVAdapter
        // on below line calling a method to fetch courses from database.
        // on below line calling a method to fetch courses from database.
        getNotes()
    }

    private fun getNotes() {
        notesRVModalArrayList.clear()
        // on below line we are calling add child event listener method to read the data.
        // on below line we are calling add child event listener method to read the data.
        FirebaseDatabase.getInstance().getReference("Payment").child("UPI")
            .child(FirebaseAuth.getInstance().currentUser?.uid.toString()).addChildEventListener(object :
            ChildEventListener {
            override fun onChildAdded(
                snapshot: DataSnapshot,
                @Nullable previousChildName: String?
            ) {
                // on below line we are hiding our progress bar.
                loadingPB.visibility = View.GONE
                // adding snapshot to our array list on below line.
                notesRVModalArrayList.add(snapshot.getValue(UPIModel::class.java)!!)
                // notifying our adapter that data has changed.
                notesRVAdapter.notifyDataSetChanged()
            }

            override fun onChildChanged(
                snapshot: DataSnapshot,
                @Nullable previousChildName: String?
            ) {
                // this method is called when new child is added
                // we are notifying our adapter and making progress bar
                // visibility as gone.
                loadingPB.visibility = View.GONE
                notesRVAdapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                // notifying our adapter when child is removed.
                notesRVAdapter.notifyDataSetChanged()
                loadingPB.visibility = View.GONE
            }

            override fun onChildMoved(
                snapshot: DataSnapshot,
                @Nullable previousChildName: String?
            ) {
                // notifying our adapter when child is moved.
                notesRVAdapter.notifyDataSetChanged()
                loadingPB.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                loadingPB.visibility = View.GONE
                Toast.makeText(
                    context,
                    "Fail to get the data" + error.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}

