package com.example.codeiiest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.codeiiest.auth.Login
import com.example.codeiiest.fragments.*
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        nav_view.setNavigationItemSelectedListener(this)

        setToolbarTitle("Home")
        changeFragment(HomeFragment())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logoutBtn){
            Firebase.auth.signOut()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            return true
        }
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        when(item.itemId) {
            R.id.home -> {
                setToolbarTitle("Home")
                changeFragment(HomeFragment())
            }
            R.id.about -> {
                setToolbarTitle("About")
                changeFragment(AboutFragment())
            }
            R.id.chapters -> {
                setToolbarTitle("Chapters")
                changeFragment(ChaptersFragment())
            }
            R.id.contact -> {
                setToolbarTitle("Contact Us")
                changeFragment(ContactFragment())
            }
            R.id.general -> {
                setToolbarTitle("General")
                changeFragment(GeneralChat())
            }
            R.id.cp -> {
                setToolbarTitle("Competitive Coding")
                changeFragment(CP())
            }
            R.id.dev -> {
                setToolbarTitle("Dev & Sec")
                changeFragment(DevSec())
            }
            R.id.ml -> {
                setToolbarTitle("Machine Learning")
                changeFragment(MachineLearning())
            }
            R.id.offTopic -> {
                setToolbarTitle("Off Topic")
                changeFragment(OffTopic())
            }
        }
        return true
    }

    private fun setToolbarTitle(Title: String){
        supportActionBar?.title = Title
    }

    private fun changeFragment(frag: Fragment){
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.fragment_container, frag).commit()
    }
}