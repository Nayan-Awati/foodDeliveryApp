package com.nayan.food_runner.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.nayan.food_runner.R
import com.nayan.food_runner.fragments.FaqFragment
import com.nayan.food_runner.fragments.FavouritesFragment
import com.nayan.food_runner.fragments.HomeFragment
import com.nayan.food_runner.fragments.ProfileFragment

class MainActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var navigationView: NavigationView
    lateinit var frame: FrameLayout
    lateinit var drawerLayout: DrawerLayout
    var previousMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        coordinatorLayout = findViewById(R.id.coordinator)
        navigationView = findViewById(R.id.navigationView)
        frame = findViewById(R.id.frame)
        drawerLayout = findViewById(R.id.drawerLayout)


        setupToolbar()
        openHome()

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {
            if(previousMenuItem != null){
                val frag = supportFragmentManager.findFragmentById(R.id.frame)
                when(frag) {
                    is FavouritesFragment -> {
                        previousMenuItem?.setIcon(R.drawable.ic_favourite_border)
                        previousMenuItem?.isChecked = false
                    }
                    is ProfileFragment -> {
                        previousMenuItem?.setIcon(R.drawable.ic_profile_border)
                        previousMenuItem?.isChecked = false
                    }
                }
                previousMenuItem?.isChecked = false
            }
            it.isChecked = true
            it.isCheckable = true
            previousMenuItem = it

            when(it.itemId){
                R.id.home -> {
                    openHome()
                    drawerLayout.closeDrawers()
                }

                R.id.favourites ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, FavouritesFragment())
                        .commit()
                    it.setIcon(R.drawable.ic_favourite_filled)
                    supportActionBar?.title = "Favourite Restaurants"
                    drawerLayout.closeDrawers()
                }

                R.id.profile ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, ProfileFragment())
                        .commit()
                    supportActionBar?.title = "My Profile"
                    it.setIcon(R.drawable.ic_profile_filled_grey)
                    drawerLayout.closeDrawers()
                }

                R.id.faq ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, FaqFragment())
                        .commit()
                    supportActionBar?.title = "FAQs"
                    drawerLayout.closeDrawers()
                }

                R.id.logout ->{
                    Toast.makeText(this@MainActivity, "Logout", Toast.LENGTH_SHORT).show()
                    drawerLayout.closeDrawers()
                }

            }

            return@setNavigationItemSelectedListener true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)

        }
        return super.onOptionsItemSelected(item)
    }

    fun setupToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title = "All Restaurants"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun openHome(){
        val fragment = HomeFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, fragment)
        transaction.commit()
        navigationView.setCheckedItem(R.id.home)
        supportActionBar?.title = "All Restaurants"
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.frame)
        when(frag){
            is FavouritesFragment-> {
                previousMenuItem?.setIcon(R.drawable.ic_favourite_border)
                previousMenuItem?.isChecked = false
                openHome()
            }
            is ProfileFragment->{
                previousMenuItem?.setIcon(R.drawable.ic_profile_border)
                previousMenuItem?.isChecked = false
                openHome()
            }
            !is HomeFragment->{
                previousMenuItem?.isChecked = false
                openHome()
            }

            else -> super.onBackPressed()

        }
    }
}