package ru.barinov.bettapplication.ui.homeFragment

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.koin.androidx.viewmodel.ext.android.*
import org.koin.java.KoinJavaComponent
import ru.barinov.bettapplication.R
import ru.barinov.bettapplication.core.scaledImageFromBitmap
import ru.barinov.bettapplication.ui.RecyclerViewAdapter
import ru.barinov.bettapplication.databinding.HomeFragmentLayoutBinding

private const val FIRST_LAUNCH_KEY = "fistLaunchKey"
private const val imageScaleBase = 75

class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentLayoutBinding
    private val adapter = RecyclerViewAdapter()
    private lateinit var recyclerView: RecyclerView
    private val sharedPreferences = KoinJavaComponent.inject<SharedPreferences>(SharedPreferences::class.java)
    private val viewModel by viewModel<HomeFragmentViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentLayoutBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (sharedPreferences.value.getBoolean(FIRST_LAUNCH_KEY, true)) {

            fillDataBase()
        }

        initToolBar()
        initRecyclerView()
        registeredForListeners()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun registeredForListeners() {
        lifecycleScope.launchWhenCreated {
            withContext(Dispatchers.Main) {
                viewModel.onStrategyChecked.onEach { event ->
                    event.getContentIfNotHandled()?.let { id ->
                        if (id.isNotEmpty()) {
                            findNavController().navigate(
                                R.id.action_homeFragment_to_bettingStrategyPageFragment,
                                bundleOf(STRATEGY_ID_KEY to id)
                            )
                        }
                    }
                }.collect()
            }
        }
    }

    private fun initToolBar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.homeFragmentsToolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (!menu.hasVisibleItems()) {
            inflater.inflate(R.menu.toolbar_menu, menu)
            super.onCreateOptionsMenu(menu, inflater)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorites_menu_button -> {
                item.isChecked = !item.isChecked
                val isChecked = item.isChecked
                viewModel.onFavoriteClicked(isChecked)
                if (isChecked) {
                    item.setIcon(R.drawable.ic_favourites_selected_star)
                } else {
                    item.setIcon(R.drawable.ic_favourites_black_star)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRecyclerView() {

        recyclerView = binding.homeFragmentsRecyclerView
        recyclerView.adapter = adapter

        lifecycleScope.launchWhenCreated {
            viewModel.profilesData.onEach { list ->
                list.onEach { item ->
                    withContext(Dispatchers.IO) {
                        Glide.with(requireContext()).asBitmap().load(item.imageURL)
                            .into(object : CustomTarget<Bitmap>() {
                                override fun onResourceReady(
                                    resource: Bitmap, transition: Transition<in Bitmap>?
                                ) {
                                    item.bitmap = resource.scaledImageFromBitmap(
                                        resource,
                                        imageScaleBase,
                                        requireContext().resources.displayMetrics.density
                                    )
                                }

                                override fun onLoadCleared(placeholder: Drawable?) {
                                    item.bitmap = placeholder!!.toBitmap()

                                }
                            })
                    }

                }
                withContext(Dispatchers.Main) {
                    adapter.setList(list)
                }

            }.collect()
        }
    }

    private fun fillDataBase() {

        //В инных случаях , я бы предзаполнил бы Базу Данных, но нехватило времени

        lifecycleScope.launchWhenCreated {
            val titles = arrayOf(
                resources.getString(R.string.first_strategy_title),
                resources.getString(R.string.second_strategy_title),
                resources.getString(R.string.third_strategy_title),
                resources.getString(R.string.fourth_strategy_title),
                resources.getString(R.string.fifth_strategy_title),
                resources.getString(R.string.sixth_strategy_title),

                )
            val bodies = arrayOf(
                resources.getString(R.string.first_strategy_body),
                resources.getString(R.string.second_strategy_body),
                resources.getString(R.string.third_strategy_body),
                resources.getString(R.string.fourth_strategy_body),
                resources.getString(R.string.fifth_strategy_body),
                resources.getString(R.string.sixth_strategy_body),
            )
            val urls = arrayOf(
                resources.getString(R.string.first_strategy_url),
                resources.getString(R.string.second_strategy_url),
                resources.getString(R.string.third_strategy_url),
                resources.getString(R.string.fourth_strategy_url),
                resources.getString(R.string.fifth_strategy_url),
                resources.getString(R.string.sixth_strategy_url),
            )
            viewModel.initialDataBase(titles, bodies, urls)

        }
        val editor: SharedPreferences.Editor = sharedPreferences.value.edit()
        editor.putBoolean(FIRST_LAUNCH_KEY, false).apply()
    }

    companion object {

        const val STRATEGY_ID_KEY = "strategyIdKey"
    }
}