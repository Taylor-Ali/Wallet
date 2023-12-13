package com.leaf.wallet.ui.wallet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leaf.wallet.R
import com.leaf.wallet.databinding.ListItemForeignExchangeBinding
import com.leaf.wallet.utils.Utils

class WalletAdapter(
    private val selectedItemListener: RateItemListener
) : RecyclerView.Adapter<WalletAdapter.ViewHolder>() {
    private val rates = ArrayList<Rate>()

    fun setItems(items: List<Rate>) {
        this.rates.clear()
        this.rates.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_foreign_exchange, parent, false), selectedItemListener
        )
    }

    override fun onBindViewHolder(holder: WalletAdapter.ViewHolder, position: Int) {
        holder.update(rates[position])
    }

    override fun getItemCount(): Int {
        return rates.size
    }
    interface RateItemListener {
        fun onClickedRate(rate: Rate)
    }

    inner class ViewHolder(
        itemView: View,
        private val rateItemListener: RateItemListener = selectedItemListener
    ) : RecyclerView.ViewHolder(itemView) {
        private val binding = ListItemForeignExchangeBinding.bind(itemView)
        fun update(rate: Rate) {
            binding.header.text = rate.currencyName
            binding.summary.text = Utils.formatAmountIntoCurrency(rate.countryLocale,rate.conversionRateAmount)

            binding.rateItem.setOnClickListener {
                rateItemListener.onClickedRate(rate)
            }
        }
    }
}