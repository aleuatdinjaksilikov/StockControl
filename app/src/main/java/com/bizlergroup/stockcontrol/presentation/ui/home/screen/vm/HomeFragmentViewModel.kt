package com.bizlergroup.stockcontrol.presentation.ui.home.screen.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bizlergroup.stockcontrol.data.models.*
import com.bizlergroup.stockcontrol.domain.usecase.category.getAllCategorires.GetAllCategoriesUseCase
import com.bizlergroup.stockcontrol.domain.usecase.getStatistics.main.GetStatisticsMainUseCase
import com.bizlergroup.stockcontrol.domain.usecase.product.getAllProducts.GetAllProductsUseCase
import com.bizlergroup.stockcontrol.domain.usecase.product.sellProduct.SellProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val sellProductUseCase: SellProductUseCase,
    private val getStatisticsMainUseCase: GetStatisticsMainUseCase
    ):ViewModel() {

    //GetAllCategories
    private val _getAllCategoriesFlow = MutableSharedFlow<List<CategoryResponseData>>()
    val getAllCategoriesFlow:SharedFlow<List<CategoryResponseData>> get() = _getAllCategoriesFlow

    private val _messageGetAllCategories = MutableSharedFlow<String>()
    val messageGetAllCategoriesFlow:SharedFlow<String> get() = _messageGetAllCategories

    private val _errorGetAllCategories = MutableSharedFlow<Throwable>()
    val errorGetAllCategoriesFlow:SharedFlow<Throwable> get() = _errorGetAllCategories


    //GetAllProducts
    private val _getAllProductsFlow = MutableSharedFlow<List<ProductResponseData>>()
    val getAllProductsFlow:SharedFlow<List<ProductResponseData>> get() = _getAllProductsFlow

    private val _messageGetAllProducts = MutableSharedFlow<String>()
    val messageGetAllProducts:SharedFlow<String> get() = _messageGetAllProducts

    private val _errorGetAllProducts = MutableSharedFlow<Throwable>()
    val errorGetAllProducts:SharedFlow<Throwable> get() = _errorGetAllProducts

    //SellProduct
    private val _sellProductFlow = MutableSharedFlow<EditProductResponseData>()
    val sellProductFlow : SharedFlow<EditProductResponseData> get() = _sellProductFlow

    private val _messageSellProductFlow = MutableSharedFlow<String>()
    val messageSellProductFlow : SharedFlow<String> get() = _messageSellProductFlow

    private val _errorSellProductFlow = MutableSharedFlow<Throwable>()
    val errorSellProductFlow : SharedFlow<Throwable> get() = _errorSellProductFlow

    //Statistika

    private val _getStatisticsFlow = MutableSharedFlow<StatisticsMainResponseData>()
    val getStatisticsFlow : SharedFlow<StatisticsMainResponseData> get() = _getStatisticsFlow

    private val _messageGetStatisticsFlow = MutableSharedFlow<String>()
    val messageGetStatisticsFlow : SharedFlow<String> get() = _messageGetStatisticsFlow

    private val _errorGetStatisticsFlow = MutableSharedFlow<Throwable>()
    val errorGetStatisticsFlow : SharedFlow<Throwable> get() = _errorGetStatisticsFlow

    fun getAllCategories(){
        getAllCategoriesUseCase.execute().onEach {
            when(it){
                is ResultData.Success ->{
                    _getAllCategoriesFlow.emit(it.data)
                }
                is ResultData.Message ->{
                    _messageGetAllCategories.emit(it.message)
                }
                is ResultData.Error ->{
                    _errorGetAllCategories.emit(it.error)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getAllProducts(){
        getAllProductsUseCase.execute().onEach {
            when(it){
                is ResultData.Success ->{
                    _getAllProductsFlow.emit(it.data)
                }
                is ResultData.Message ->{
                    _messageGetAllProducts.emit(it.message)
                }
                is ResultData.Error ->{
                    _errorGetAllProducts.emit(it.error)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun sellProduct(body:SellProductRequestData){
        sellProductUseCase.execute(body = body).onEach {
            when(it){
                is ResultData.Success ->{
                    _sellProductFlow.emit(it.data)
                }
                is ResultData.Message ->{
                    _messageSellProductFlow.emit(it.message)
                }
                is ResultData.Error ->{
                    _errorSellProductFlow.emit(it.error)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getStatistics(){
        getStatisticsMainUseCase.execute().onEach {
            when(it){
                is ResultData.Success->{
                    _getStatisticsFlow.emit(it.data)
                }
                is ResultData.Message->{
                    _messageGetStatisticsFlow.emit(it.message)
                }
                is ResultData.Error->{
                    _errorGetStatisticsFlow.emit(it.error)
                }
            }
        }.launchIn(viewModelScope)
    }
}