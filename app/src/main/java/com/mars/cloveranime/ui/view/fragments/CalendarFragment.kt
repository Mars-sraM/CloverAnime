package com.mars.cloveranime.ui.view.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mars.cloveranime.R
import com.mars.cloveranime.core.CheckInternet
import com.mars.cloveranime.core.Navigate
import com.mars.cloveranime.databinding.FragmentCalendarBinding
import com.mars.cloveranime.ui.view.Adapters.CalendarAdapter
import com.mars.cloveranime.ui.view.AnimeDetailActivity
import com.mars.cloveranime.ui.viewModel.AnimeCalendarViewModel
import java.io.File


class CalendarFragment : Fragment(), OnItemSelectedListener {
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private val calendarViewModel: AnimeCalendarViewModel by viewModels()
    private var adapter: CalendarAdapter? = null
    private val dayList: ArrayList<String> = arrayListOf(
        "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val internet = CheckInternet.isInternetOn(requireContext())
        initUi(internet)
    }


    private fun initUi(internet: Boolean) {
        initRecyclerView(binding.rvAnimes)
        initSpinner()

        binding.ivSearch.setOnClickListener {
            if (internet) {
                var fragment: Fragment? =
                    requireActivity().supportFragmentManager.findFragmentByTag("search_fragment")
                if (fragment == null) {
                    fragment = SearchFragment()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.ContainerPrincipal, fragment, "search_fragment")
                        .commit()
                }
            } else {
                Toast.makeText(requireContext(), "No hay acceso a internet", Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        binding.ivError.isVisible = false
        adapter = CalendarAdapter() {
            Navigate.navigateActivities(
                it, requireContext(), AnimeDetailActivity.EXTRA_ID,
                AnimeDetailActivity.EXTRA_PROVIDER, "MonosChinos"
            )
        }
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        calendarViewModel.isloading.observe(viewLifecycleOwner, Observer {
            binding.rvCalendarLoading.isVisible = !it
            binding.rvAnimes.isVisible = it
            if (adapter!!.itemCount == 0){
                binding.ivError.isVisible =  it
                binding.rvAnimes.isVisible = false
            }else{ binding.ivError.isVisible =  false }

        })
    }

    private fun initSpinner() {
        binding.spinnerDay.onItemSelectedListener = this
        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item, dayList
        )
        spinnerAdapter.setDropDownViewResource(R.layout.item_dropdown)
        binding.spinnerDay.adapter = spinnerAdapter

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p0!!.id == binding.spinnerDay.id) {
            when (p0.getItemAtPosition(p2).toString()) {
                "Lunes" -> {
                    getListAnimeCalendar(0)
                }

                "Martes" -> {
                    getListAnimeCalendar(1)
                }

                "Miércoles" -> {
                    getListAnimeCalendar(2)
                }

                "Jueves" -> {
                    getListAnimeCalendar(3)
                }

                "Viernes" -> {
                    getListAnimeCalendar(4)
                }

                "Sábado" -> {
                    getListAnimeCalendar(5)
                }

                "Domingo" -> {
                    getListAnimeCalendar(6)
                }
            }

        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun getListAnimeCalendar(day: Int) {
        calendarViewModel.addListAnimes(day)
        calendarViewModel.calendar.observe(viewLifecycleOwner, Observer { animes ->
            if (!animes.isNullOrEmpty()) {
                binding.ivError.isVisible = false
                adapter!!.updateList(animes)
            }
        })
    }

    fun clearCache() {
        Log.i(ContentValues.TAG, "Clearing Cache.")
        val dir: Array<File> = requireContext().getCacheDir().listFiles() as Array<File>
        for (f in dir) {
            f.delete()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        clearCache()
    }
}