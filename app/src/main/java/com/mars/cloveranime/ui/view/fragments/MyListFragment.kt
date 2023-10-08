package com.mars.cloveranime.ui.view.fragments


import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.mars.cloveranime.R
import com.mars.cloveranime.core.CheckInternet
import com.mars.cloveranime.core.RefactorDataClass.toFavorite
import com.mars.cloveranime.core.RefactorDataClass.toFinished
import com.mars.cloveranime.core.RefactorDataClass.toPending
import com.mars.cloveranime.data.SharedPreferencesCA.Companion.prefs
import com.mars.cloveranime.data.model.AnimeFavorite
import com.mars.cloveranime.databinding.FragmentMyListBinding
import com.mars.cloveranime.ui.view.Adapters.MylistAdapter
import com.mars.cloveranime.ui.view.Adapters.ViewPagerAdapter
import com.mars.cloveranime.ui.viewModel.AnimeFavoriteModel
import com.mars.cloveranime.ui.viewModel.AnimesFinishedViewModel
import com.mars.cloveranime.ui.viewModel.AnimesPendingViewModel
import com.mars.cloveranime.ui.viewModel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Arrays


@AndroidEntryPoint
class MyListFragment : Fragment() {
    private val TAG = "MainActivity"
    private val REQUEST_CODE_SIGN_IN = 1
    private val db = FirebaseFirestore.getInstance()
    private var _binding: FragmentMyListBinding? = null
    private val binding get() = _binding!!
    lateinit var viewPagerAdapter: ViewPagerAdapter
    private var accountName: String? = null
    private var accountEmail: String? = null
    private var accountImage: String? = null
    private var animesPending: List<AnimeFavorite> = emptyList()
    private var animesFavorite: List<AnimeFavorite> = emptyList()
    private var animesFinished: List<AnimeFavorite> = emptyList()
    private val detailViewModel: DetailViewModel by viewModels()
    private val pendingViewModel: AnimesPendingViewModel by viewModels()
    private val favoritesViewModel: AnimeFavoriteModel by viewModels()
    private val finishedViewModel: AnimesFinishedViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val internet = CheckInternet.isInternetOn(requireContext())
        viewPagerAdapter = ViewPagerAdapter(this)
        binding.viwPager.adapter = viewPagerAdapter
        binding.tapLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let {
                    binding.viwPager.currentItem = it

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        val viewPager: ViewPager2 = requireView().findViewById(R.id.viwPager)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tapLayout.getTabAt(position)?.select()
            }
        })

        binding.ivAccount.setOnClickListener {
            if (internet) {
                singIng()
            } else {
                Toast.makeText(requireContext(), "No hay acceso a internet", Toast.LENGTH_SHORT).show()
            }

        }

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
                Toast.makeText(requireContext(), "No hay acceso a internet", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun singIng() {
        val infoUser = prefs.getUserInfo()
        if (infoUser[0] == "no") {
            requestSignIn()
        } else {
            showDialogAccount()
        }
    }

    private fun showDialogAccount() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_account_user)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        pendingViewModel.getAnimes(requireContext())
        pendingViewModel.animesPending.observe(viewLifecycleOwner, Observer { pending ->
            animesPending = pending
        })
        favoritesViewModel.getAnimes(requireContext())
        favoritesViewModel.animesFavorites.observe(viewLifecycleOwner, Observer { favorites ->
            animesFavorite = favorites
        })
        finishedViewModel.getAnimes(requireContext())
        finishedViewModel.animesFinished.observe(viewLifecycleOwner, Observer { finished ->
            animesFinished = finished
        })

        val name: TextView = dialog.findViewById(R.id.tvAccountName)
        val email: TextView = dialog.findViewById(R.id.tvAccountEmail)
        val tvClose: TextView = dialog.findViewById(R.id.tvClose)
        val btnSingOut: ImageView = dialog.findViewById(R.id.ivSingOut)
        val btnSave: Button = dialog.findViewById(R.id.btnGuardar)
        val btnCargar: Button = dialog.findViewById(R.id.btnCargar)


        btnSingOut.setOnClickListener {
            singOut()
            dialog.dismiss()
        }
        btnSave.setOnClickListener {
            addDataFireStore(animesPending, animesFavorite, animesFinished)
        }
        btnCargar.setOnClickListener {
            getDataFireStore()

        }
        tvClose.setOnClickListener { dialog.dismiss() }
        setInfoUser(name, email)

        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_SIGN_IN -> if (resultCode === AppCompatActivity.RESULT_OK && data != null) {
                handleSignInResult(data)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun requestSignIn() {
        Log.d(TAG, "Requesting sign-in")
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        //posible error
        val client = GoogleSignIn.getClient(requireActivity(), signInOptions)

        // El resultado de la intención de inicio de sesión se maneja en onActivityResult.
        startActivityForResult(client.signInIntent, REQUEST_CODE_SIGN_IN)


    }

    private fun singOut() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        mGoogleSignInClient.signOut()
        Toast.makeText(context, "${accountName}, ha cerrado sesion", Toast.LENGTH_SHORT).show()
        prefs.wipeUserInfo()

    }

    private fun handleSignInResult(result: Intent) {
        GoogleSignIn.getSignedInAccountFromIntent(result)
            .addOnSuccessListener { googleAccount: GoogleSignInAccount ->
                Log.d(TAG, "Signed in as " + googleAccount.email)
                prefs.saveUserInfo(
                    googleAccount.displayName.toString(),
                    googleAccount.email.toString()
                )
                accountImage = googleAccount.photoUrl.toString()

            }
            .addOnFailureListener { exception: Exception? ->
                Log.e(
                    TAG,
                    "Unable to sign in.",
                    exception
                )
            }
    }


    private fun setInfoUser(name: TextView, email: TextView) {
        val infoUser = prefs.getUserInfo()
        accountName = infoUser[0]
        accountEmail = infoUser[1]
        name.text = accountName
        email.text = accountEmail
        //Handler().postDelayed({ cargaSegundaImagen() }, 1000)
    }

    private fun addDataFireStore(
        pList: List<AnimeFavorite>,
        faList: List<AnimeFavorite>,
        fiList: List<AnimeFavorite>
    ) {
        if (accountName != "no") {
            db.collection("favoritos").document(accountEmail!!).set(
                hashMapOf(
                    "favoritos" to faList,
                    "pendientes" to pList,
                    "terminados" to fiList
                )
            )
            Toast.makeText(context, "Los animes se han guardado", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Por favor, inicie sesión", Toast.LENGTH_SHORT).show()
        }


    }

    private fun getDataFireStore() {
        if (accountName != "no") {
            db.collection("favoritos").document(accountEmail!!).get().addOnSuccessListener {
                val favorite = it.get("favoritos") as List<*>
                val pending = it.get("pendientes") as List<*>
                val finished = it.get("terminados") as List<*>

                val faList = refactorValue(favorite.toMutableList())
                val pList = refactorValue(pending.toMutableList())
                val fiList = refactorValue(finished.toMutableList())

                updateFavorites(faList, pList, fiList)

                favoritesViewModel.addAllAnimes(faList.map { it.toFavorite() })
                pendingViewModel.addAllAnimes(pList.map { it.toPending() })
                finishedViewModel.addAllAnimes(fiList.map { it.toFinished() })

            }

        } else {
            Toast.makeText(context, "Por favor, inicie sesión", Toast.LENGTH_SHORT).show()
        }
    }


    private fun refactorValue(list: MutableList<*>): List<AnimeFavorite> {
        var row: MutableList<MutableList<Any>> = mutableListOf<MutableList<Any>>()
        val favoriteList: MutableList<AnimeFavorite> = mutableListOf()
        for (i in 0 until list.size) {
            val cell1 = list[i].toString()
            val cell1b = cell1.replace("{", "")
            val cell1c = cell1b.replace(" animeName=", "")
            val cell1d = cell1c.replace("animeUrl=", "")
            val cell1e = cell1d.replace(" animeimg=", "")
            val cell1f = cell1e.replace("}", "")
            val cell1g = cell1f.replace("]", "")
            val myList: List<String> =
                ArrayList(Arrays.asList(*cell1g.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()))
            row.add(myList.toMutableList())
        }

        for (i in 0 until row.size) {
            favoriteList.add(
                AnimeFavorite(
                    row[i][2].toString(),
                    row[i][0].toString(),
                    row[i][1].toString()
                )
            )
        }
        return favoriteList.toList()
    }

    private fun updateFavorites(
        favorites: List<AnimeFavorite>,
        pending: List<AnimeFavorite>,
        finished: List<AnimeFavorite>
    ) {
        when (binding.viwPager.currentItem) {
            0 -> {
                val recyclerView =
                    requireActivity().findViewById<RecyclerView>(R.id.rvAnimesFavorites)
                val adapter: MylistAdapter? = recyclerView.adapter as MylistAdapter?
                adapter!!.updateList(favorites)
            }

            1 -> {
                val recyclerView =
                    requireActivity().findViewById<RecyclerView>(R.id.rvAnimesPending)
                val adapter: MylistAdapter? = recyclerView.adapter as MylistAdapter?
                adapter!!.updateList(pending)
            }

            2 -> {
                val recyclerView =
                    requireActivity().findViewById<RecyclerView>(R.id.rvAnimesFinished)
                val adapter: MylistAdapter? = recyclerView.adapter as MylistAdapter?
                adapter!!.updateList(finished)
            }
        }
    }
}