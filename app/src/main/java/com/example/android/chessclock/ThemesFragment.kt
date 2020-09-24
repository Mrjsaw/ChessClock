package com.example.android.chessclock

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatDelegate
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.fragment_themes.*

class ThemesFragment : Fragment() {

    private lateinit var listView: ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_themes, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        listView = themes_list_view
        val listItems = resources.getStringArray(R.array.themesArrayList);
        val adapter = ArrayAdapter(activity, R.layout.themes_list_item,listItems)
        listView.adapter = adapter
        listView.setOnItemClickListener { adapterView, view, position: Int, id: Long ->
            //do smth
        }
        super.onActivityCreated(savedInstanceState)
    }

    companion object {
        fun newInstance(): ThemesFragment {
            return ThemesFragment()
        }
    }
}