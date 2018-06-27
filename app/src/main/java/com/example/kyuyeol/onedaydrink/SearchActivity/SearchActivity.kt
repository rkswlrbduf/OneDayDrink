package com.example.kyuyeol.onedaydrink.SearchActivity

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.balysv.materialmenu.MaterialMenuDrawable
import com.example.kyuyeol.onedaydrink.R
import com.tsengvn.typekit.TypekitContextWrapper
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import kotlinx.android.synthetic.main.activity_search.*
import android.app.SharedElementCallback
import android.os.Build
import android.support.v7.widget.GridLayoutManager
import android.transition.AutoTransition
import android.transition.ChangeBounds
import android.transition.ChangeTransform
import android.transition.Fade
import android.view.View
import android.widget.Filter
import android.widget.Filterable
import com.yarolegovich.slidingrootnav.SlidingRootNav

class SearchActivity : AppCompatActivity(), Filterable {

    lateinit var slidingRootNav: SlidingRootNav

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val materialMenu = MaterialMenuDrawable(this, Color.GRAY, MaterialMenuDrawable.Stroke.THIN)
        main_menu.setImageDrawable(materialMenu)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val transition = AutoTransition()
            transition.duration = 150
            transition.excludeTarget(android.R.id.statusBarBackground, true)
            window.sharedElementEnterTransition = transition
            window.sharedElementExitTransition = transition
            window.enterTransition = transition
            window.exitTransition = transition
        }

        main_menu.setOnClickListener(View.OnClickListener {
            if (!slidingRootNav.isMenuOpened) {
                slidingRootNav.openMenu()
            } else {
                slidingRootNav.closeMenu()
            }
        })

        search_recyclerview.layoutManager = GridLayoutManager(this, 2)
        search_recyclerview.adapter = SearchRecyclerViewAdapter(this)

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase))
    }

    override fun getFilter(): Filter {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}