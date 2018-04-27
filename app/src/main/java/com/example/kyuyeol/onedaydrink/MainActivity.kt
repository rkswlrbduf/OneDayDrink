package com.example.kyuyeol.onedaydrink

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_tabview.view.*
import java.util.zip.Inflater

class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tablayout.addTab(tablayout.newTab().setCustomView(R.layout.fragment_tabview))
        tablayout.addTab(tablayout.newTab().setCustomView(R.layout.fragment_tabview))
        tablayout.addTab(tablayout.newTab().setCustomView(R.layout.fragment_tabview))
        tablayout.addTab(tablayout.newTab().setCustomView(R.layout.fragment_tabview))

        CustomTab(0)
        CustomTab(1)
        CustomTab(2)
        CustomTab(3)

        val adapter = TabPagerAdapter(supportFragmentManager, tablayout.tabCount)

        viewpager.adapter = adapter

        viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tablayout))
        tablayout.addOnTabSelectedListener(this)

    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        viewpager.currentItem = tab.position
    }

    fun CustomTab(position: Int) {
        val view: View? = tablayout.getTabAt(position)?.customView
        when (position) {
            0 ->view?.tab_text?.text = "Test1"
            1 -> view?.tab_text?.text = "Test2"
            2 -> view?.tab_text?.text = "Test3"
            3 -> view?.tab_text?.text = "Test4"
        }
    }

}
