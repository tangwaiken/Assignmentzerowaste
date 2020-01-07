package com.example.assignment


import android.content.Intent
import android.os.Bundle

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.google.firebase.database.*

import java.util.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class Recycler : Fragment() {

    private var mTextViewEmpty: TextView? = null
    private var mProgressBarLoading: ProgressBar? = null
    private var mImageViewEmpty: ImageView? = null
    private var mRecyclerView: RecyclerView? = null
    private var mListadapter: ListAdapter? = null
    private val data = ArrayList<FoodEntity>()
    lateinit var ref: DatabaseReference
    lateinit var food: FoodEntity


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recycler, container, false)



        mRecyclerView = view.findViewById<View>(R.id.recyclerView) as RecyclerView
        mTextViewEmpty = view.findViewById<View>(R.id.textViewEmpty) as TextView
        mImageViewEmpty = view.findViewById<View>(R.id.imageViewEmpty) as ImageView
        mProgressBarLoading = view.findViewById<View>(R.id.progressBarLoading) as ProgressBar

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mRecyclerView!!.layoutManager = layoutManager

        ref = FirebaseDatabase.getInstance().getReference("Food")

        ref.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0!!.exists()){
                    data.clear()
                    for(h in p0.children){
                        val food = h.getValue(FoodEntity::class.java)
                        data.add(food!!)
                    }
                    mListadapter = ListAdapter(data)
                    mRecyclerView!!.adapter = mListadapter
                }
            }

        })



        return view
    }

    inner class ListAdapter(private val dataList: ArrayList<FoodEntity>) :
        RecyclerView.Adapter<ListAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            internal var textViewText: TextView
            internal var textViewComment: TextView
            internal var textViewDate: TextView

            init {
                this.textViewText = itemView.findViewById<View>(R.id.text) as TextView
                this.textViewComment = itemView.findViewById<View>(R.id.comment) as TextView
                this.textViewDate = itemView.findViewById<View>(R.id.date) as TextView
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false)

            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
            holder.textViewText.text = dataList[position].itemName
            holder.textViewComment.text = dataList[position].description
            holder.textViewDate.text = dataList[position].owner

            holder.itemView.setOnClickListener {v->
                val i = Intent(v.context, Details::class.java)
                i.putExtra("img", data[position].itemImage)
                i.putExtra("owner", data[position].owner)
                i.putExtra("item", data[position].itemName)
                i.putExtra("description", data[position].description)
                i.putExtra("pickuptime", data[position].pickupTime)
                i.putExtra("location", data[position].location)
                v.context.startActivity(i)
            }
        }

        override fun getItemCount(): Int {
            return dataList.size
        }
    }

}// Required empty public constructor
