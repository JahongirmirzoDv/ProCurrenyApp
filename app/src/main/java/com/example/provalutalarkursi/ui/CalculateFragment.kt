package com.example.provalutalarkursi.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.provalutalarkursi.R
import com.example.provalutalarkursi.adapters.CurrencyAdapter
import com.example.provalutalarkursi.adapters.SpinnerAdapter
import com.example.provalutalarkursi.databinding.FragmentCAlculateBinding
import com.example.provalutalarkursi.models.Data
import com.example.provalutalarkursi.viewmodels.AppViewModel
import com.example.provalutalarkursi.viewmodels.ViewPagerViewmodel
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CAlculateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CAlculateFragment : Fragment() {
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

    lateinit var binding: FragmentCAlculateBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCAlculateBinding.inflate(inflater, container, false)

        var viewmodel = ViewModelProvider(this).get(AppViewModel::class.java)

        viewmodel.getData().observe(requireActivity(), {
            val shuffled = it.shuffled()
            val spinnerAdapter = SpinnerAdapter(it)
            val spinnerAdapter2 = SpinnerAdapter(shuffled)
            binding.title.adapter = spinnerAdapter
            binding.title2.adapter = spinnerAdapter2

            binding.title.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Picasso
                        .get()
                        .load(
                            "https://flagcdn.com/160x120/${
                                it[position].code?.substring(0, 2)?.toLowerCase()
                            }.png"
                        )
                        .into(binding.image)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }
            binding.title2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Picasso
                        .get()
                        .load(
                            "https://flagcdn.com/160x120/${
                                shuffled[position].code?.substring(0, 2)?.toLowerCase()
                            }.png"
                        )
                        .into(binding.image2)
                    binding.cardSell.text =
                        if (it[position].nbu_cell_price!!.length > 2) "${it[position].nbu_cell_price} UZS" else "${it[position].cb_price} UZS"
                    binding.cardBuy.text =
                        if (it[position].nbu_buy_price!!.length > 2) "${it[position].nbu_buy_price} UZS" else "${it[position].cb_price} UZS"
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
            binding.edit.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                @SuppressLint("SetTextI18n")
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s.toString() != "") {
                        val toInt = s.toString().toFloat()
                        val spn1 = binding.title.selectedItemPosition
                        val spn2 = binding.title2.selectedItemPosition
                        val price1 = it[spn1].cb_price!!.toFloat()
                        val price2 = it[spn2].cb_price!!.toFloat()

                        var r = (price1 * toInt)
                        var value = (r / price2)
                        binding.result.text = "$value ${shuffled[spn2].code}"
                    } else binding.result.text = ""
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })
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
         * @return A new instance of fragment CAlculateFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CAlculateFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}