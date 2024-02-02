package com.example.reto.ui.producdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.reto.data.database.entities.Product
import com.example.reto.databinding.FragmentProductDetailBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private val args: ProductDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val product = try {
            args.product
        } catch (e: IllegalArgumentException) {
            null
        }
        if (product != null) {
            displayProductDetails(product)
        } else {
            displayEmptyState()
        }
        binding.lblTitleCentral.text = product?.title
    }

    private fun displayEmptyState() {
        binding.apply {
            lblTitleCentral.visibility = View.GONE
            tvcodigo.visibility = View.GONE
            tvprecio.visibility = View.GONE
            tvdescripcion.visibility = View.GONE
            imageView.visibility = View.GONE
        }
    }
    private fun displayProductDetails(product: Product) {
        binding.apply {
            lblTitleCentral.text = product.title
            Picasso.get().load(product.images.get(0)).into(imageView)
            tvcodigo.text = "Código:      " + product.id.toString()
            tvprecio.text = "Precio:      " + product.price.toString()
            tvdescripcion.text = "Descripción: " + product.description
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}