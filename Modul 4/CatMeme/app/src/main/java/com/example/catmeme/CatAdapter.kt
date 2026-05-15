package com.example.catmeme

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.catmeme.databinding.ItemCatBinding
import timber.log.Timber

class CatAdapter(
    private var catList: List<Cat> = emptyList(),
    private val onDetailClick: (Cat) -> Unit
) : RecyclerView.Adapter<CatAdapter.CatViewHolder>() {

    fun submitList(newList: List<Cat>) {
        catList = newList
        notifyDataSetChanged()
    }

    inner class CatViewHolder(private val binding: ItemCatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cat: Cat) {
            binding.tvName.text = cat.name
            binding.tvRas.text = cat.ras
            binding.tvDescription.text = cat.description
            binding.ivCat.setImageResource(cat.imageResId)

            binding.btnInst.setOnClickListener {
                Timber.d("Tombol Explicit Intent ditekan")
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(cat.instagram))
                it.context.startActivity(intent)
            }

            binding.btnDetail.setOnClickListener {
                onDetailClick(cat)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val binding = ItemCatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatViewHolder(binding)
    }

    override fun getItemCount(): Int = catList.size

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(catList[position])
    }
}
