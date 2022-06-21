package com.example.wikipediasearchapp.ui.articlesearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.wikipediasearchapp.R
import com.example.wikipediasearchapp.databinding.FragmentArticleSearchBinding
import com.example.wikipediasearchapp.ui.articleresult.ArticleResultFragment
import kotlinx.coroutines.launch

class ArticleSearchFragment : Fragment() {

    private var _binding: FragmentArticleSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ArticleSearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiStateFlow.collect { uiState ->
                    updateLoadingStatus(uiState.isLoading)
                    handleNavigationIfResultReady(uiState.isResultReady)
                    handleErrorMessages(uiState.errorMessages)
                }
            }
        }
        binding.searchButton.setOnClickListener {
            val topic = binding.searchEditText.text.toString()
            viewModel.articleSearch(topic)
        }
        requireActivity().title = "Search Wikipedia"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateLoadingStatus(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.searchEditText.visibility = if (!isLoading) View.VISIBLE else View.GONE
        binding.searchButton.visibility = if (!isLoading) View.VISIBLE else View.GONE
    }

    private fun handleNavigationIfResultReady(isResultReady: Boolean) {
        if (!isResultReady) return
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, ArticleResultFragment())
            .addToBackStack(null)
            .commit()
        viewModel.setResultHandled()
    }

    private fun handleErrorMessages(errorMessages: List<StringResource?>) {
        val errorMessage = errorMessages.firstOrNull() ?: return
        Toast.makeText(requireContext(), errorMessage.resolve(requireContext()), Toast.LENGTH_SHORT).show()
        viewModel.setErrorMessageShown(errorMessage)
    }
}