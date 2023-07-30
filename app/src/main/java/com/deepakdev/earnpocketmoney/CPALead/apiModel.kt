package com.deepakdev.earnpocketmoney.CPALead

data class apiModel(
    val number_offers: Int,
    val offers: List<Offer>,
    val status: String
)