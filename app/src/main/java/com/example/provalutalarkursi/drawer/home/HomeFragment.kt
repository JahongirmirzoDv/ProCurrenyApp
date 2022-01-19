package com.example.provalutalarkursi.drawer.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.provalutalarkursi.R
import com.example.provalutalarkursi.adapters.HistoryRvAdapter
import com.example.provalutalarkursi.adapters.ViewpagerAdapter
import com.example.provalutalarkursi.databinding.FragmentHomeBinding
import com.example.provalutalarkursi.db.AppDatabase
import com.example.provalutalarkursi.models.Data
import com.example.provalutalarkursi.services.InternetConnection
import com.example.provalutalarkursi.ui.ZoomOut
import com.example.provalutalarkursi.utils.Status
import com.example.provalutalarkursi.viewmodels.AppViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.*

@DelicateCoroutinesApi
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var tittleList: List<String>
    private lateinit var dataList: List<Data>
    lateinit var db: AppDatabase
    lateinit var onpress: OnDataPass
    lateinit var historyRvAdapter: HistoryRvAdapter
    private val TAG = "HomeFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this)[AppViewModel::class.java]
        db = AppDatabase.getInstance(requireContext())

        viewModel.getData(requireContext()).observe(requireActivity(), {
            when (it.status) {
                Status.LOADING -> {
                    Log.d(TAG, "onCreate: Loading")
                }
                Status.ERROR -> {
                    Log.d(TAG, "onCreate: ${it.message}")
                }
                Status.SUCCESS -> {
                    binding.text1.visibility = View.VISIBLE
                    binding.progress.visibility = View.GONE
                    dataList = it.data!!
                    GlobalScope.launch {
                        www()
                    }
                }
            }
        })
        if (!InternetConnection(requireContext()).isNetworkConnected()) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("No network")
                .setMessage("Ckeck you internet connection")
                .setPositiveButton("Ok") { dialog, which ->
                    dialog.cancel()
                }
                .show()
        }


        return binding.root
    }

    suspend fun www() {
       GlobalScope.launch(Dispatchers.Main) {
           val viewpagerAdapter = ViewpagerAdapter(dataList, requireActivity())
           binding.viewpager2.adapter = viewpagerAdapter

           TabLayoutMediator(
               binding.tab, binding.viewpager2
           ) { tab, position ->
               tab.text = dataList[position].code
           }.attach()

           binding.viewpager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
               override fun onPageScrolled(
                   position: Int,
                   positionOffset: Float,
                   positionOffsetPixels: Int
               ) {
                   super.onPageScrolled(position, positionOffset, positionOffsetPixels)
               }

               override fun onPageSelected(position: Int) {
                   super.onPageSelected(position)
                   Log.e("Selected_Page", position.toString())
                   onpress.onDataPass(position)
                   GlobalScope.launch(Dispatchers.Main) {
                       var list = getHistory(position)
                       val historyRvAdapter = HistoryRvAdapter(list)
                       binding.historyRv.adapter = historyRvAdapter
                   }
               }

               override fun onPageScrollStateChanged(state: Int) {
                   super.onPageScrollStateChanged(state)
               }
           })

           val zoomOut = ZoomOut()
           binding.viewpager2.setPageTransformer(zoomOut)
           binding.indicator.attachToPager(binding.viewpager2)
       }
    }

    suspend fun getHistory(position: Int): List<Data> {
        var mm = ArrayList<Data>()
        withContext(Dispatchers.IO) {
            val list = db.dataDao().getList()
            list.forEach {
                if (it.code == dataList[position].code) {
                    mm.add(it)
                }
            }
        }
        return mm
    }

    @SuppressLint("ResourceAsColor")
    interface OnDataPass {
        fun onDataPass(data: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onpress = context as OnDataPass
    }

    @SuppressLint("ResourceAsColor")
    fun setTabs(list: List<Data>) {
        val tabCount = binding.tab.tabCount
        for (i in 0 until tabCount) {
            val tabview =
                LayoutInflater.from(requireContext()).inflate(R.layout.tab_item, null)
            val tab = binding.tab.getTabAt(i)
            tab?.customView = tabview
            binding.tab.getTabAt(0)?.customView?.findViewById<LinearLayout>(R.id.tab_ln)
                ?.setBackgroundResource(R.color.purple_500)
            binding.tab.getTabAt(0)?.customView?.findViewById<TextView>(R.id.tab_name)
                ?.setTextColor(R.color.white)
        }
    }
}