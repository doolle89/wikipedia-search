package com.example.wikipediasearchapp.ui.articleresult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.wikipediasearchapp.R
import com.example.wikipediasearchapp.databinding.FragmentArticleResultBinding
import kotlinx.coroutines.launch

class ArticleResultFragment : Fragment() {

    private var _binding: FragmentArticleResultBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ArticleResultViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiStateFlow.collect { uiState ->
                    if (uiState.articleResultUiState == null) {
                        handleArticleResultError()
                        return@collect
                    }
                    setupTitle(uiState.articleResultUiState.title)
                    displayArticle(uiState.articleResultUiState.body)
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupTitle(title: String) {
        requireActivity().title = title
    }

    private fun displayArticle(body: String) {
        // disable user clicks in the webView
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                return true
            }
        }
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadDataWithBaseURL(
            "https://en.wikipedia.org/",
            body,
            "text/html",
            "UTF-8",
            null)
    }

    private fun handleArticleResultError() {
        Toast.makeText(requireContext(), getString(R.string.error_empty_body), Toast.LENGTH_SHORT).show()
        parentFragmentManager.popBackStack()
    }
}