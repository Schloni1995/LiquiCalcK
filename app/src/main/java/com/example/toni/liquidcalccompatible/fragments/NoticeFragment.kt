package com.example.toni.liquidcalccompatible.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.toni.liquidcalccompatible.R

/**
 * A simple [Fragment] subclass.
 */
class NoticeFragment : Fragment() {
    internal var view: View


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        view = inflater!!.inflate(R.layout.fragment_notice, container, false)
        init()
        return view
    }

    private fun init() {
        //        NonScrollListView nonScrollListView = getActivity().findViewById(R.id.listView);
    }

}// Required empty public constructor
