package com.example.ecom.data.network

import com.example.ecom.domain.models.Product
import com.example.ecom.domain.models.Rating

fun ProductDto.toProduct(): Product {
    return Product(
        id = id,
        category = category,
        description = description,
        image = image,
        price = price,
        rating = rating.toRating(),
        title = title
    )
}

fun RatingDto.toRating(): Rating {
    return Rating(
        count = count,
        rate = rate
    )
}



