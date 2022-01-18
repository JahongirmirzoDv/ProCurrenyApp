package com.example.provalutalarkursi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.provalutalarkursi.databinding.ActivityMainBinding
import com.example.provalutalarkursi.drawer.home.HomeFragment
import com.example.provalutalarkursi.utils.Status
import com.example.provalutalarkursi.viewmodels.AppViewModel
import com.example.provalutalarkursi.viewmodels.ViewPagerViewmodel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import nl.joery.animatedbottombar.AnimatedBottomBar

open class MainActivity : AppCompatActivity(), HomeFragment.OnDataPass {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPagerViewmodel: ViewPagerViewmodel
    lateinit var appViewModel: AppViewModel
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appViewModel = ViewModelProvider(this)[AppViewModel::class.java]
        viewPagerViewmodel = ViewModelProvider(this)[ViewPagerViewmodel::class.java]

        val toolbar = binding.appBarMain.toolbar

        toolbar.setNavigationOnClickListener {
            binding.apply {
                drawerLayout.openDrawer(navView)
            }
        }

        binding.navView.setNavigationItemSelectedListener {
            if (it.title == "About") {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Currency app")
                    .setMessage("This app created by ....")
                    .setPositiveButton("Ok") { dialog, which ->
                        dialog.cancel()
                    }
                    .show()
            } else {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "Hi my friend")
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
            true
        }

        viewPagerViewmodel.get().observe(this, {
            binding.appBarMain.include2.bottomBar.tabColor = it
            binding.appBarMain.include2.bottomBar.indicatorColor = it
            binding.appBarMain.include2.bottomBar.tabColorSelected = it
        })

        binding.appBarMain.include2.bottomBar.setOnTabSelectListener(object :
            AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                when (newIndex) {
                    1 -> {
                        val findNavController =
                            findNavController(R.id.nav_host_fragment_content_main)
                        findNavController.popBackStack()
                        findNavController.navigate(R.id.currencyFragment)

                    }
                    2 -> {
                        val findNavController =
                            findNavController(R.id.nav_host_fragment_content_main)
                        findNavController.popBackStack()
                        findNavController.navigate(R.id.calculateFragment)

                    }
                    else -> {
                        val findNavController =
                            findNavController(R.id.nav_host_fragment_content_main)
                        findNavController.popBackStack()
                        findNavController.navigate(R.id.nav_home)

                    }
                }
            }
        })
    }


    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(binding.navView)) {
            binding.drawerLayout.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDataPass(data: Int) {
        Log.d(TAG, "onDataPass: $data")
    }
}