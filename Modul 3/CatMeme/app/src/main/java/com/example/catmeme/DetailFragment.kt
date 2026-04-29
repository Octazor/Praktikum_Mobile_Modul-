package com.example.catmeme

import com.example.catmeme.R
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.catmeme.databinding.FragmentDetailBinding

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)

        val catId = arguments?.getInt("catId") ?: return

        val viewModel = ViewModelProvider(requireActivity())[CatViewModel::class.java]
        val cat = viewModel.catList.find { it.id == catId }

        if (cat != null) {
            binding.ivDetailImage.setImageResource(cat.imageResId)
            binding.tvDetailName.text = cat.name
            binding.tvDetailRas.text = cat.ras
            binding.tvDetailDescription.text = cat.description
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}