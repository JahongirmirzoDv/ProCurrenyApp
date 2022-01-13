package com.example.provalutalarkursi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.provalutalarkursi.R
import com.example.provalutalarkursi.adapters.CurrencyAdapter
import com.example.provalutalarkursi.databinding.FragmentCurrencyBinding
import com.example.provalutalarkursi.models.Data
import com.example.provalutalarkursi.viewmodels.AppViewModel
import com.example.provalutalarkursi.viewmodels.ViewPagerViewmodel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CurrencyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CurrencyFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentCurrencyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCurrencyBinding.inflate(inflater, container, false)
        var viewmodel = ViewModelProvider(this).get(AppViewModel::class.java)

        viewmodel.getData().observe(requireActivity(), {
            val currencyAdapter = CurrencyAdapter(it, object : CurrencyAdapter.onPress {
                override fun onclick(data: Data, position: Int) {

                }
            })
            binding.currencyRv.adapter = currencyAdapter
        })

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CurrencyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CurrencyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}