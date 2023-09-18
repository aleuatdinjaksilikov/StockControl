package com.example.market.presentation.ui.dialogs.editProduct.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.market.R
import com.example.market.data.models.CategoryResponseData
import com.example.market.data.models.EditProductRequestData
import com.example.market.databinding.DialogEditProductBinding
import com.example.market.presentation.ui.dialogs.add.vm.AddProductDialogViewModel
import com.example.market.presentation.ui.dialogs.editProduct.vm.EditProductDialogViewModel
import com.example.market.utils.EditProductClick
import com.example.market.utils.makeToast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditProductDialog:BottomSheetDialogFragment() {
    private lateinit var binding: DialogEditProductBinding
    private val viewModel : AddProductDialogViewModel by viewModels()
    private val viewModelEdit : EditProductDialogViewModel by viewModels()
    private val args : EditProductDialogArgs by navArgs()

    private var selectedCategoryId = 1
    private var imageUrl = ""
    private var type = ""
    private var category = ""

    private var isListAdded = false
    private val list = mutableListOf<CategoryResponseData>()
    val listType = listOf("KG","M","L")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL,R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_edit_product,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogEditProductBinding.bind(view)

        lifecycleScope.launch {
            viewModelEdit.getProductByName(name = args.name)
            viewModel.getAllCategories()
        }


        initObservables()
        initListeners()

        binding.dropdownCategory.setOnItemClickListener { adapterView, view, i, l ->
            val item = adapterView.getItemAtPosition(i).toString()
            category = list[i].name
            imageUrl = list[i].imageUrl
//            selectedCategoryId = i
            Toast.makeText(requireContext(),"Item $item",Toast.LENGTH_SHORT).show()
        }

        val adapterType = ArrayAdapter(requireContext(),R.layout.list_item_dropdown_menu,listType)
        binding.dropdownTypeProduct.setAdapter(adapterType)
        binding.dropdownTypeProduct.setOnItemClickListener { adapterView, view, i, l ->
            val item = adapterView.getItemAtPosition(i).toString()
            type = listType[i]
            Toast.makeText(requireContext(),"Item $item",Toast.LENGTH_SHORT).show()
        }

    }

    private fun initListeners() {

        binding.btnEditProduct.setOnClickListener {
            val name = binding.etProductName.text.toString()
            val amount = binding.etProductAmount.text.toString()
            val price = binding.etProductPrice.text.toString()
            val sizeProduct = binding.etProductSize.text.toString()

            if (name!=="" && amount!="" && price!="" && type!="" && category!="" && imageUrl!="" && sizeProduct!=""){
                lifecycleScope.launch{
                    viewModelEdit.editProduct(
                        args.id,
                        EditProductRequestData(
                            amount = amount.toInt(),
                            category = category,
                            imageUrl = imageUrl,
                            name = name,
                            price = price.toInt(),
                            unit = type,
                            size = sizeProduct
                        )
                    )
                }
            }else{
                makeToast("Заполните все поля!!!")
            }
        }
    }

    private fun initObservables() {

        viewModelEdit.getProductFlow.onEach {
            binding.apply {
                etProductName.setText(it.name)
                etProductAmount.setText(it.amount.toString())
                etProductPrice.setText(it.price.toString())
                etProductSize.setText(it.size)

            }
        }.launchIn(lifecycleScope)

        viewModelEdit.editProductFlow.onEach {
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            if (it.statusCode == 200){
                EditProductClick.buttonAddCategoryClick(true)
            }
            Log.d("YYY",it.message)
            dismiss()
        }.launchIn(lifecycleScope)

        viewModel.getCategoriesFlow.onEach {

            list.clear()
            list.addAll(it)

            val adapterCategory =
                ArrayAdapter(requireContext(), R.layout.list_item_dropdown_menu, list.map { it.name })
            binding.dropdownCategory.setAdapter(adapterCategory)
            adapterCategory.notifyDataSetChanged()
        }.launchIn(lifecycleScope)


    }
}