package com.leaf.wallet.ui.fluctuations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.leaf.wallet.R
import com.leaf.wallet.databinding.FragmentFluctuationsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FluctuationsFragment : Fragment() {
    private val viewModel: FluctuationsViewModel by viewModels()
    private var _binding: FragmentFluctuationsBinding? = null
    private val binding get() = _binding!!
    val args: FluctuationsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFluctuationsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.message.text = getString(R.string.message_loading)
        viewModel.getFluctuationRate(args.rate)

        setupObserver()
    }

    private fun setupObserver() {
        viewModel.currencyFluctuationsLiveData.observe(viewLifecycleOwner) {
            showLayout()
            binding.walletValue.text = getString(R.string.bitcoin_rate) + it.endRate
            binding.rateIcon.setImageResource(it.imageIndicator)
            binding.rateValue.text = it.convertedCurrency
            binding.startDateValue.text = it.startDate
            binding.endDateValue.text = it.endDate
            binding.startRateValue.text = it.startRate
            binding.endRateValue.text = it.endRate
            binding.changeValue.text = it.change
            binding.changePercentageValue.text = it.changePercentage
        }

        viewModel.showErrorMessageLiveData.observe(viewLifecycleOwner){
            showError()
        }
    }

    private fun showLayout() {
        binding.loadingLayout.visibility = View.GONE
        binding.fluctuationsLayout.visibility = View.VISIBLE
    }

    private fun showError() {
        binding.loadingLayout.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
        binding.message.text = getString(R.string.message_error)
    }
}