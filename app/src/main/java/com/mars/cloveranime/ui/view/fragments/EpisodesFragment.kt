package com.mars.cloveranime.ui.view.fragments



import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mars.cloveranime.R
import com.mars.cloveranime.core.FragmentsMethods
import com.mars.cloveranime.core.Navigate
import com.mars.cloveranime.databinding.FragmentEpisodesBinding
import com.mars.cloveranime.ui.view.Adapters.EpisodesAdapter
import com.mars.cloveranime.ui.view.AnimeDetailActivity
import com.mars.cloveranime.ui.view.PlayerActivity
import com.mars.cloveranime.ui.view.PlayerActivity.Companion.EXTRA_OPTION
import com.mars.cloveranime.ui.view.PlayerActivity.Companion.EXTRA_URL
import com.mars.cloveranime.ui.viewModel.DetailViewModel
import com.mars.cloveranime.ui.viewModel.EpisodesViewModel


class EpisodesFragment : Fragment() {
    lateinit var adapter: EpisodesAdapter
    private var _binding: FragmentEpisodesBinding? = null
    private val binding get() = _binding!!
    private val episodesViewModel: EpisodesViewModel by viewModels()
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        // Inflate the layout for this fragment
        addSystemUI(requireContext())
        _binding = FragmentEpisodesBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val intent: Intent = requireActivity().intent
        val url: String = intent.getStringExtra(AnimeDetailActivity.EXTRA_ID).orEmpty()
        val provider: String = intent.getStringExtra(AnimeDetailActivity.EXTRA_PROVIDER).orEmpty()

        episodesViewModel.addEpisodesPage1(url, provider)
        episodesViewModel.episodeslModel.observe(viewLifecycleOwner, Observer { episodes ->
            if (!episodes.isNullOrEmpty()) {
                adapter = EpisodesAdapter(episodes) { showDialog(it, provider) }
                binding.rvEpisodes.setHasFixedSize(true)
                val layoutManager = LinearLayoutManager(context)
                binding.rvEpisodes.layoutManager = layoutManager
                binding.rvEpisodes.adapter = adapter
                FragmentsMethods.reverseOrder(binding.ivRevers, adapter, episodes)
                binding.ivRevers.text = "Episodios: ${episodes.size}"
            }
        })

        binding.ivRegresar.setOnClickListener {
            fragmentManager?.beginTransaction()?.remove(this)?.commit()
        }
        episodesViewModel.isloading.observe(viewLifecycleOwner, Observer {
            binding.linearMensage.isVisible = it
        })
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                fragmentManager?.beginTransaction()?.remove(this@EpisodesFragment)?.commit()
            }
        })
    }
    private fun showDialog(url: String, provider: String) {
        val context = requireContext()
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_server_option)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        showOptions(url, dialog, provider)
        FragmentsMethods.hideOption(dialog)
        val btnVer: TextView = dialog.findViewById(R.id.btnVer)
        val btnCancel: TextView = dialog.findViewById(R.id.btnCancelar)
        val rgOptions: RadioGroup = dialog.findViewById(R.id.rgOptions)
        val btnInfoAnime: LinearLayout = dialog.findViewById(R.id.btnInfoAnime)

        if (context == requireContext()) {
            btnInfoAnime.isVisible = false
        }

        btnVer.setOnClickListener {
            val selectedId = rgOptions.checkedRadioButtonId
            val selectedRadioButton: RadioButton = rgOptions.findViewById(selectedId)
            when (selectedRadioButton.text) {
                getString(R.string.radioBottom_option1) -> Navigate.navigateActivityPlayer(
                    url,
                    requireContext(), EXTRA_URL, EXTRA_OPTION, 0,
                    PlayerActivity.EXTRA_PROVIDER, provider
                )

                getString(R.string.radioBottom_option2) -> Navigate.navigateActivityPlayer(
                    url,
                    requireContext(), EXTRA_URL, EXTRA_OPTION, 1,
                    PlayerActivity.EXTRA_PROVIDER, provider
                )

                getString(R.string.radioBottom_option3) -> Navigate.navigateActivityPlayer(
                    url,
                    requireContext(), EXTRA_URL, EXTRA_OPTION, 2,
                    PlayerActivity.EXTRA_PROVIDER, provider
                )
                getString(R.string.radioBottom_option4) -> Navigate.navigateActivityPlayer(
                    url,
                    requireContext(), EXTRA_URL, EXTRA_OPTION, 3,
                    PlayerActivity.EXTRA_PROVIDER, provider
                )
                getString(R.string.radioBottom_option5) -> Navigate.navigateActivityPlayer(
                    url,
                    requireContext(), EXTRA_URL, EXTRA_OPTION, 4,
                    PlayerActivity.EXTRA_PROVIDER, provider
                )
                getString(R.string.radioBottom_option6) -> Navigate.navigateActivityPlayer(
                    url,
                    requireContext(), EXTRA_URL, EXTRA_OPTION, 5,
                    PlayerActivity.EXTRA_PROVIDER, provider
                )
                getString(R.string.radioBottom_option7) -> Navigate.navigateActivityPlayer(
                    url,
                    requireContext(), EXTRA_URL, EXTRA_OPTION, 6,
                    PlayerActivity.EXTRA_PROVIDER, provider
                )
                getString(R.string.radioBottom_option8) -> Navigate.navigateActivityPlayer(
                    url,
                    requireContext(), EXTRA_URL, EXTRA_OPTION, 7,
                    PlayerActivity.EXTRA_PROVIDER, provider
                )
                getString(R.string.radioBottom_option9) -> Navigate.navigateActivityPlayer(
                    url,
                    requireContext(), EXTRA_URL, EXTRA_OPTION, 8,
                    PlayerActivity.EXTRA_PROVIDER, provider
                )
            }
            dialog.dismiss()

        }

        dialog.show()
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
    }
    private fun showOptions(url: String, dialog: Dialog, provider: String){

        detailViewModel.addSizeServers(url,provider)
        detailViewModel.sizeServersVideoModel.observe(
            viewLifecycleOwner,
            Observer { size ->
                println(size)
                FragmentsMethods.serverOption(size, dialog)
            }
        )
    }
    @SuppressLint("ResourceAsColor")
    override fun onAttach(context: Context) {
        addSystemUI(context)
        super.onAttach(context)
    }


    override fun onDetach() {
        val window: Window = requireActivity().window
        val decorView = window.decorView
        val wic = WindowInsetsControllerCompat(window, decorView)
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)
        window.statusBarColor = Color.TRANSPARENT
        wic.isAppearanceLightStatusBars = false
        super.onDetach()
    }
    private fun addSystemUI(context: Context){
        val window: Window = requireActivity().window
        val decorView = window.decorView
        val wic = WindowInsetsControllerCompat(window, decorView)
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, true)
        window.statusBarColor = ContextCompat.getColor(context,R.color.background_color_app)
        wic.isAppearanceLightStatusBars = false
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            wic.isAppearanceLightStatusBars = !resources.configuration.isNightModeActive
        }
         */
    }

}