package com.leaf.wallet.ui.setup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.leaf.wallet.R
import com.leaf.wallet.databinding.FragmentSetupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetupFragment : Fragment() {
    private val viewModel: SetupViewModel by viewModels()
    private var _binding: FragmentSetupBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSetupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.retrieveStoredBitcoinValue()

        viewModel.bitCoinValueLiveData.observe(viewLifecycleOwner) {
            binding.bitcoinValue.editText?.setText(it.toString())
        }

        binding.submitBtn.setOnClickListener {
            val action = SetupFragmentDirections.actionSetupFragmentToWalletFragment()
            val valueSupplied = binding.bitcoinValue.editText?.text.toString()
            if (viewModel.validateBitCoinsAddedIsValid(valueSupplied)) {
                viewModel.storeBitcoinValue(valueSupplied)
                viewModel.completeInitialSetup(true)
                it.findNavController().navigate(action)
            } else {
                binding.bitcoinValue.editText?.error =
                    getString(R.string.error_invalid_bitcoin_supplied)
            }
        }
    }
}