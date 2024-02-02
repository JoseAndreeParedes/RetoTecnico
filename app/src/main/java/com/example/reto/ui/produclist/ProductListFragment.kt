package com.example.reto.ui.productlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reto.R
import com.example.reto.data.database.entities.Product
import com.example.reto.databinding.FragmentProductListBinding
import com.example.reto.ui.adapter.ProductAdapter
import com.example.reto.ui.produclist.ProductListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : Fragment(), ProductAdapter.OnProductClickListener {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductListViewModel by viewModels()
    private lateinit var productListAdapter: ProductAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        productListAdapter = ProductAdapter(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearchView()
        observeProducts()
        setupSortButton()
        observeSortOrder()
    }

    private fun setupSortButton() {
        binding.sortButton.setOnClickListener { view ->
            showSortMenu(view)
        }
    }

    private fun observeSortOrder() {
        lifecycleScope.launchWhenStarted {
            viewModel.currentSortOrder.collect { sortOrder ->
                val iconRes = when (sortOrder) {
                    ProductListViewModel.SortOrder.ASCENDING -> R.drawable.ascendente
                    ProductListViewModel.SortOrder.DESCENDING -> R.drawable.decendente
                }
                binding.sortButton.setImageResource(iconRes)
            }
        }
    }


    private fun showSortMenu(view: View) {
        val popup = PopupMenu(requireContext(), view)
        popup.menuInflater.inflate(R.menu.product_list_menu, popup.menu)
        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_sort_ascending -> viewModel.sortProductsAscending()
                R.id.action_sort_descending -> viewModel.sortProductsDescending()
                else -> return@setOnMenuItemClickListener false
            }
            true
        }
        popup.show()
    }



    private fun setupRecyclerView() {
        binding.rvProducts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productListAdapter
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filterProducts(newText ?: "")
                return true
            }
        })
    }

    private fun observeProducts() {
        lifecycleScope.launchWhenStarted {
            viewModel.filteredProducts.collect { filteredProducts ->
                productListAdapter.submitList(filteredProducts)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.shouldScrollToTop.collect { shouldScroll ->
                if (shouldScroll) {
                    binding.rvProducts.post {
                        binding.rvProducts.scrollToPosition(0)
                        viewModel.onScrollToTopHandled()
                    }
                }
            }
        }
    }


    override fun onProductClicked(product: Product) {
        val action =
            ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(product)
        findNavController(this).navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}