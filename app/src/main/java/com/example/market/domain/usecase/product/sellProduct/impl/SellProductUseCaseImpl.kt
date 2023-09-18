package com.example.market.domain.usecase.product.sellProduct.impl

import com.example.market.data.models.EditProductResponseData
import com.example.market.data.models.ResultData
import com.example.market.data.models.SellProductRequestData
import com.example.market.domain.repository.main.MainRepository
import com.example.market.domain.usecase.product.sellProduct.SellProductUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SellProductUseCaseImpl @Inject constructor(private val mainRepository: MainRepository):
    SellProductUseCase {
    override fun execute(body: SellProductRequestData): Flow<ResultData<EditProductResponseData>>
        = mainRepository.sellProduct(body = body)
}