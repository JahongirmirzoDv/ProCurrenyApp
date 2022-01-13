package com.example.provalutalarkursi.drawer.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.provalutalarkursi.adapters.HistoryRvAdapter
import com.example.provalutalarkursi.adapters.ViewpagerAdapter
import com.example.provalutalarkursi.databinding.FragmentHomeBinding
import com.example.provalutalarkursi.db.AppDatabase
import com.example.provalutalarkursi.models.Data
import com.example.provalutalarkursi.services.InternetConnection
import com.example.provalutalarkursi.ui.ZoomOut
import com.example.provalutalarkursi.viewmodels.AppViewModel
import com.example.provalutalarkursi.viewmodels.ViewPagerViewmodel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var tittleList: List<String>
    private lateinit var dataList: ArrayList<Data>
    lateinit var db: AppDatabase
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

        val list = db.dataDao().getList()

        if (!InternetConnection(requireContext()).isNetworkConnected()) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("No network")
                .setMessage("Ckeck you internet connection")
                .setPositiveButton("Ok") { dialog, which ->
                    dialog.cancel()
                }
                .show()
        }
        if (list.isEmpty()) {
            dataList = ArrayList()
            viewModel.getData().observe(requireActivity(), {
                for (i in it.indices) {
                    val split = it[i].date!!.split(" ")
                    val data = Data(
                        it[i].cb_price,
                        it[i].code,
                        it[i].nbu_buy_price,
                        it[i].nbu_cell_price,
                        it[i].title,
                        split[0],
                        split[1]
                    )
                    dataList.add(data)
                }
                viewPager()
            })
        } else {
            dataList = ArrayList()
            for (i in list.indices) {
                val split = list[i].date!!.split(" ")
                val data = Data(
                    list[i].cb_price,
                    list[i].code,
                    list[i].nbu_buy_price,
                    list[i].nbu_cell_price,
                    list[i].title,
                    split[0],
                    split[1]
                )
                dataList.add(data)
            }
            viewPager()
        }

        return binding.root
    }

    fun viewPager() {
        val viewPagerViewmodel = ViewModelProvider(this)[ViewPagerViewmodel::class.java]
        historyRvAdapter = HistoryRvAdapter(dataList)
        binding.historyRv.adapter = historyRvAdapter

        val viewpagerAdapter = ViewpagerAdapter(dataList, requireActivity())
        binding.viewpager2.adapter = viewpagerAdapter
        binding.text1.visibility = View.VISIBLE
        binding.progress.visibility = View.GONE

        TabLayoutMediator(binding.tab, binding.viewpager2) { tab, position ->
            tab.text = dataList[position].code
        }.attach()

        viewPagerViewmodel.get().observe(requireActivity(), {
            Log.d(TAG, "onCreateView: $it")
            Toast.makeText(requireContext(), "$it", Toast.LENGTH_LONG).show()
//            for (i in 0 until binding.tab.tabCount) {
//                val inflate = LayoutInflater.from(requireContext()).inflate(R.layout.tab_item, null)
//                binding.tab.getTabAt(i)?.customView = inflate
//            }
        })

        val zoomOut = ZoomOut()
        binding.viewpager2.setPageTransformer(zoomOut)
        binding.indicator.attachToPager(binding.viewpager2)
    }
}