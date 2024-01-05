package com.mars.cloveranime.ui.view.fragments


import android.app.Dialog
import android.content.ContentValues
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mars.cloveranime.R
import com.mars.cloveranime.core.CheckInternet
import com.mars.cloveranime.core.FragmentsMethods
import com.mars.cloveranime.core.Navigate
import com.mars.cloveranime.data.SharedPreferencesCA
import com.mars.cloveranime.data.SharedPreferencesCA.Companion.prefs
import com.mars.cloveranime.data.model.Server
import com.mars.cloveranime.databinding.BottomSheetDialogBinding
import com.mars.cloveranime.databinding.FragmentEmisionBinding
import com.mars.cloveranime.ui.view.Adapters.EmisionAdapter
import com.mars.cloveranime.ui.view.AnimeDetailActivity
import com.mars.cloveranime.ui.view.PlayerActivity
import com.mars.cloveranime.ui.viewModel.DetailViewModel
import com.mars.cloveranime.ui.viewModel.EpisodesEmisionViewModel
import java.io.File


class EmisionFragment : Fragment() {
    private var _binding: FragmentEmisionBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: EmisionAdapter
    private val emisionViewModel: EpisodesEmisionViewModel by viewModels()
    private val detailViewModel: DetailViewModel by viewModels()
    private var provider = ""
    private lateinit var dialog: Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEmisionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val internet = CheckInternet.isInternetOn(requireContext())
        provider = prefs.getProvider()
        initInfoProvider()
        println(provider)
        initUi(provider, internet)
        binding.ivModo.setOnClickListener {
            if (internet){
                showDialogProvider(dialog)
            }
        }
    }

    private fun initInfoProvider() {
        dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_provoder)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val rbMonosChinos = dialog.findViewById<RadioButton>(R.id.rbMonosChinos)
        val rbAnimeFlv = dialog.findViewById<RadioButton>(R.id.rbAnimeFLV)

        when (provider) {
            "MonosChinos" -> {
                rbMonosChinos.isChecked = true
                binding.ivModo.setImageResource(R.drawable.ic_monos_chinos)
            }

            "AnimeFLV" -> {
                rbAnimeFlv.isChecked = true
                binding.ivModo.setImageResource(R.drawable.ic_animeflv)
            }
        }
    }

    private fun showDialogProvider(dialog: Dialog) {
        val rbMonosChinos = dialog.findViewById<RadioButton>(R.id.rbMonosChinos)
        val rbAnimeFlv = dialog.findViewById<RadioButton>(R.id.rbAnimeFLV)
        val btnCancel: ImageView = dialog.findViewById(R.id.btnCancelar)
        
        rbMonosChinos.setOnCheckedChangeListener { _, value ->
            if (value) {
                checkOptionProvider(
                    getString(R.string.radioBottom_monoschinos),
                    R.drawable.ic_monos_chinos
                )
                dialog.dismiss()
            }
        }
        rbAnimeFlv.setOnCheckedChangeListener { _, value ->
            if (value) {
                checkOptionProvider(
                    getString(R.string.radioBottom_animeflv),
                    R.drawable.ic_animeflv
                )
                dialog.dismiss()
            }
        }
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
    private fun checkOptionProvider(option: String, drawableId: Int) {
        prefs.saveProvider(option)
        emisionViewModel.addEpisodesEmision(option)
        emisionViewModel.capEmisionModel.observe(viewLifecycleOwner, Observer { episodes ->
            if (!episodes.isNullOrEmpty()) {
                binding.ivError.isVisible = false
                adapter.updateList(episodes)
            } else {
                adapter.updateList(emptyList())
                binding.ivError.isVisible = true
            }
        })

        binding.ivModo.setImageResource(drawableId)
    }

    private fun initUi(animeProvider: String, internet: Boolean) {
        emisionViewModel.addEpisodesEmision(animeProvider)
        emisionViewModel.capEmisionModel.observe(viewLifecycleOwner, Observer { episodes ->
            if (!episodes.isNullOrEmpty()) {
                binding.ivError.isVisible = false
                adapter = EmisionAdapter(episodes,
                    onItemSelect = { showDialog(it.capUrl, it.animeUrl) },
                    onItemSelectLong = { showModalDialog(it) })
                adapter.updateList(episodes)
                binding.rvAnimes.setHasFixedSize(true)
                binding.rvAnimes.layoutManager = LinearLayoutManager(context)
                binding.rvAnimes.adapter = adapter
            }
            emisionViewModel.isloading.observe(viewLifecycleOwner, Observer {
                binding.rvAnimesLoading.isVisible = !it
                binding.rvAnimes.isVisible = it
                binding.ivError.isVisible = if (episodes.isNullOrEmpty()){ it }else{ false }
            })

        })


        binding.ivSearchIcon.setOnClickListener {
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
                Toast.makeText(requireContext(), "No hay acceso a internet", Toast.LENGTH_SHORT).show()
            }
        }


    }
    private fun showModalDialog(animeUrl: List<String>) {
        val dialog = BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme)
        dialog.setContentView(R.layout.bottom_sheet_dialog)
        initUiModalDialog(animeUrl, dialog)
        dialog.show()
    }
    private fun initUiModalDialog(animeUrl: List<String>, dialog: Dialog){
        val ivPortada:ImageView = dialog.findViewById(R.id.ivAnimeImg)
        val tvAnimeName:TextView = dialog.findViewById(R.id.tvAnimeName)
        val infoBtn:Button = dialog.findViewById(R.id.infoBtn)
        val exitBtn:ImageView = dialog.findViewById(R.id.btnExit)
        val tvType:TextView = dialog.findViewById(R.id.tvAnimeType)
        val tvSynopsis:TextView = dialog.findViewById(R.id.tvSynapsis)
        val loadingAnimeSynopsis:View = dialog.findViewById(R.id.loadingAnimeSynopsis)
        val loadingAnimeImg:View = dialog.findViewById(R.id.loadingAnimeImg)
        val loadingAnimeName:View = dialog.findViewById(R.id.loadingAnimeName)
        val animePovider = prefs.getProvider()

        detailViewModel.addDetailModalDialog(animeUrl[0], animePovider)
        detailViewModel.detailModelPart2.observe(viewLifecycleOwner, Observer { detail->
            if (detail != null) {
                Glide.with(ivPortada).load(detail.detailImg).skipMemoryCache(true)
                    .into(ivPortada)
                tvAnimeName.text = detail.detailName
                tvType.text = animeUrl[1].ifEmpty { "N/A" }
                tvSynopsis.text = detail.detailDate.ifEmpty { "Sinopsis no disponible" }

                infoBtn.setOnClickListener {
                    dialog.dismiss()
                    Navigate.navigateActivities(
                        animeUrl[0], requireContext(),
                        AnimeDetailActivity.EXTRA_ID, AnimeDetailActivity.EXTRA_PROVIDER, animePovider
                    )
                }
                exitBtn.setOnClickListener {
                    dialog.dismiss()
                }
            }
        })
        detailViewModel.isloading.observe(viewLifecycleOwner, Observer {
            ivPortada.isVisible = it
            loadingAnimeImg.isVisible = !it
            tvAnimeName.isVisible = it
            loadingAnimeName.isVisible = !it
            tvType.isVisible = it
            tvSynopsis.isVisible = it
            loadingAnimeSynopsis.isVisible = !it
            infoBtn.isVisible = it
        })
    }

    private fun showDialog(url: String, infoUrl: String) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_server_option)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val animePovider = prefs.getProvider()
        val btnVer: TextView = dialog.findViewById(R.id.btnVer)
        val btnCancel: ImageView = dialog.findViewById(R.id.btnCancelar)
        val rgOptions: RadioGroup = dialog.findViewById(R.id.rgOptions)
        val btnInfoAnime: TextView = dialog.findViewById(R.id.btnInfoAnime)

        btnInfoAnime.setOnClickListener {
            dialog.dismiss()
            Navigate.navigateActivities(
                infoUrl, requireContext(),
                AnimeDetailActivity.EXTRA_ID, AnimeDetailActivity.EXTRA_PROVIDER, animePovider
            )
        }

        detailViewModel.addVideosEpisodes(url, animePovider)
        detailViewModel.episodesVideoModel.observe(viewLifecycleOwner, Observer { servers ->
            if(servers != null){
                val size = servers.sizeServers
                showOption(size, dialog, servers.listServer)
                btnVer.setOnClickListener {
                    val selectedId = rgOptions.checkedRadioButtonId
                    val selectedRadioButton: RadioButton = rgOptions.findViewById(selectedId)
                    when (selectedRadioButton.id) {
                        R.id.rbOpcion1 -> Navigate.navigateActivityPlayer(
                            url,
                            requireContext(),
                            PlayerActivity.EXTRA_URL,
                            PlayerActivity.EXTRA_OPTION, servers.listServer[indexServer(size, 0)].linkVideo,
                            PlayerActivity.EXTRA_PROVIDER, provider
                        )

                        R.id.rbOpcion2 -> Navigate.navigateActivityPlayer(
                            url,
                            requireContext(),
                            PlayerActivity.EXTRA_URL,
                            PlayerActivity.EXTRA_OPTION, servers.listServer[indexServer(size, 1)].linkVideo,
                            PlayerActivity.EXTRA_PROVIDER, provider
                        )

                        R.id.rbOpcion3 -> Navigate.navigateActivityPlayer(
                            url,
                            requireContext(),
                            PlayerActivity.EXTRA_URL,
                            PlayerActivity.EXTRA_OPTION, servers.listServer[indexServer(size, 2)].linkVideo,
                            PlayerActivity.EXTRA_PROVIDER, provider
                        )
                        R.id.rbOpcion4 -> Navigate.navigateActivityPlayer(
                            url,
                            requireContext(),
                            PlayerActivity.EXTRA_URL,
                            PlayerActivity.EXTRA_OPTION, servers.listServer[indexServer(size, 3)].linkVideo,
                            PlayerActivity.EXTRA_PROVIDER, provider
                        )
                        R.id.rbOpcion5 -> Navigate.navigateActivityPlayer(
                            url,
                            requireContext(),
                            PlayerActivity.EXTRA_URL,
                            PlayerActivity.EXTRA_OPTION, servers.listServer[indexServer(size, 4)].linkVideo,
                            PlayerActivity.EXTRA_PROVIDER, provider
                        )
                        R.id.rbOpcion6 -> Navigate.navigateActivityPlayer(
                            url,
                            requireContext(),
                            PlayerActivity.EXTRA_URL,
                            PlayerActivity.EXTRA_OPTION, servers.listServer[indexServer(size, 5)].linkVideo,
                            PlayerActivity.EXTRA_PROVIDER, provider
                        )
                        R.id.rbOpcion7 -> Navigate.navigateActivityPlayer(
                            url,
                            requireContext(),
                            PlayerActivity.EXTRA_URL,
                            PlayerActivity.EXTRA_OPTION, servers.listServer[indexServer(size, 6)].linkVideo,
                            PlayerActivity.EXTRA_PROVIDER, provider
                        )
                        R.id.rbOpcion8 -> Navigate.navigateActivityPlayer(
                            url,
                            requireContext(),
                            PlayerActivity.EXTRA_URL,
                            PlayerActivity.EXTRA_OPTION, servers.listServer[indexServer(size, 7)].linkVideo,
                            PlayerActivity.EXTRA_PROVIDER, provider
                        )
                        R.id.rbOpcion9 -> Navigate.navigateActivityPlayer(
                            url,
                            requireContext(),
                            PlayerActivity.EXTRA_URL,
                            PlayerActivity.EXTRA_OPTION, servers.listServer[indexServer(size, 8)].linkVideo,
                            PlayerActivity.EXTRA_PROVIDER, provider
                        )
                    }
                    dialog.dismiss()
                }
            }
        })


        dialog.show()
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
    }
    private fun showOption(size: Int, dialog: Dialog, list: MutableList<Server>){
        detailViewModel.isloading.observe(viewLifecycleOwner, Observer {
            FragmentsMethods.hideOption(dialog, it)
            FragmentsMethods.serverOption(size, dialog, list, it)
        })
    }


    private fun indexServer(size: Int, index: Int): Int {
        return if (index <= size && index > 0) {
            size - (size - index)
        } else if (index == 0) {
            0
        } else {
            0
        }
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