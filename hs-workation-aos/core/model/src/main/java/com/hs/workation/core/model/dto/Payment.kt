package com.hs.workation.core.model.dto

data class Payment(
    var id: String? = null,
    var reservationId: String? = null,
    var member: Member? = null,
    var billings: List<Billing>? = null,
    var paymentCard: Paymentcard? = null,
    var tid: String? = null,
    var bid: String? = null,
    var status: String? = null,
    var originalAmount: Int? = null,
    var savingsAmount: Int? = null,
    var finalAmount: Int? = null,
    var outstandingAmount: Int? = null,
    var remark: String? = null,
    var reason: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null,
    var categoryId: String? = null
) {
    data class Member(
        var id: String? = null,
        var name: String? = null,
        var email: String? = null,
        var phoneNumber: String? = null
    )
    data class Billing(
        var billingId: String? = null
    )
    data class Paymentcard(
        var id: String? = null,
        var memberId: String? = null,
        var cardNumber: String? = null,
        var billingKey: String? = null,
        var vendor: String? = null
    )
}