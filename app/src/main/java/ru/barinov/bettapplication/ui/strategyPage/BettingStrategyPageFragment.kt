package ru.barinov.bettapplication.ui.strategyPage

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.barinov.bettapplication.databinding.*
import ru.barinov.bettapplication.ui.homeFragment.HomeFragment

class BettingStrategyPageFragment : Fragment() {

    private lateinit var binding: StrategyPageFragmentLayoutBinding
    private lateinit var id: String
    private val viewModel by viewModel<BettingStrategyPageViewModel> { (parametersOf(id)) }

    override fun onAttach(context: Context) {
        id = requireArguments().getString(HomeFragment.STRATEGY_ID_KEY)!!
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = StrategyPageFragmentLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadStrategy()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun loadStrategy() {
        lifecycleScope.launchWhenCreated {
            withContext(Dispatchers.Main) {
                viewModel.selectedStrategy.onEach { strategy ->
                    if (strategy != null) {
                        Glide.with(requireContext()).load(strategy.imageURL)
                            .into(binding.strategyPageImageView)
                        binding.strategyPageTitle.text = strategy.title
                        binding.strategyPageBody.text = strategy.body
                    }
                }.collect()
            }
        }
    }
}