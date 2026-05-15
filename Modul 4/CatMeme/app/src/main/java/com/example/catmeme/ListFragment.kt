package com.example.catmeme

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catmeme.databinding.FragmentListBinding
import kotlinx.coroutines.launch

class ListFragment : Fragment(R.layout.fragment_list) {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentListBinding.bind(view)

        val factory = CatViewModelFactory("Car")
        val viewModel = ViewModelProvider(this, factory)[CatViewModel::class.java]

        val adapter = CatAdapter { cat ->
            viewModel.onDetailClicked(cat)
        }

        binding.rvHorizontal.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvHorizontal.adapter = adapter

        binding.rvVertical.layoutManager = LinearLayoutManager(requireContext())
        binding.rvVertical.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.catList.collect { cats ->
                        adapter.submitList(cats)
                    }
                }
                launch {
                    viewModel.navigateToDetail.collect { cat ->
                        cat?.let {
                            val bundle = Bundle().apply { putInt("catId", it.id) }
                            findNavController().navigate(R.id.action_list_to_detail, bundle)
                            viewModel.onDetailNavigated()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
