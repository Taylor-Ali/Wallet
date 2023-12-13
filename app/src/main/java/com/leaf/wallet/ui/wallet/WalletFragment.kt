package com.leaf.wallet.ui.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.leaf.wallet.R
import com.leaf.wallet.databinding.FragmentWalletBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletFragment : Fragment(), WalletAdapter.RateItemListener {
    private val viewModel: WalletViewModel by viewModels()
    private var _binding: FragmentWalletBinding? = null
    private val binding get() = _binding!!
    private lateinit var walletAdapter: WalletAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWalletBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObserver()
        binding.message.text = getString(R.string.message_loading)
        binding.fabAdd.setOnClickListener {
            val action = WalletFragmentDirections.actionWalletFragmentToSetupFragment()
            it.findNavController().navigate(action)
        }

        viewModel.getBitcoinValueFromStorage()
        viewModel.getForeignExchangeRates()
    }


    private fun setupRecyclerView() {
        binding.rateList.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )

        walletAdapter = WalletAdapter(this)
        binding.rateList.adapter = walletAdapter
    }

    private fun setupObserver() {
        viewModel.bitCoinValueLiveData.observe(viewLifecycleOwner) {
            binding.bitcoinValue.text = getString(R.string.bitcoin_symbol) + it
        }

        viewModel.conversionLiveData.observe(viewLifecycleOwner) {
            showRateLayout(it)
        }

        viewModel.showErrorMessageLiveData.observe(viewLifecycleOwner){
            showError()
        }
    }

    private fun showRateLayout(it: ConversionRate) {
        binding.loadingLayout.visibility = View.GONE
        walletAdapter.setItems(it.rates)
        binding.rateList.visibility = View.VISIBLE
    }

    private fun showError() {
        binding.loadingLayout.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
        binding.message.text = getString(R.string.message_error)
    }

    override fun onClickedRate(rate: Rate) {
        val action = WalletFragmentDirections.actionWalletFragmentToFluctuationsFragment(rate)
        findNavController().navigate(action)
    }
}
