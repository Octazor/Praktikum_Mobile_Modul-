package com.example.catmeme

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.catmeme.databinding.FragmentDetailBinding

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    // 2. Deklarasikan ViewModel di level kelas menggunakan delegasi ini
    // Ini akan membagikan ViewModel yang sama dengan Activity induknya
    private val viewModel: CatViewModel by activityViewModels {
        CatViewModelFactory("Car") // Isi parameter string sesuai kebutuhanmu
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)

        val catId = arguments?.getInt("catId") ?: return

        // 3. Hapus baris val viewModel = ViewModelProvider... yang lama

        // 4. Langsung gunakan viewModel yang sudah dideklarasikan di atas
        val cat = viewModel.catList.value.find { it.id == catId }

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