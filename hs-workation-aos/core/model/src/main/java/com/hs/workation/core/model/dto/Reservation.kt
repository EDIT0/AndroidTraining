package com.hs.workation.core.model.dto

data class Reservation(
    var id: String? = null,
    var member: Member? = null,
    var payments: List<Payment>? = null,
    var targetId: String? = null,
    var status: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var reason: String? = null,
    var remark: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null,
    var category: Category? = null,
    var workcationId: String? = null // TODO:
) {
    data class Member(
        var id: String? = null,
        var name: String? = null,
        var email: String? = null
    )
    data class Payment(
        var id: String? = null,
        var paymentCardId: String? = null,
        var reservationId: String? = null,
        var tid: String? = null,
        var bid: String? = null,
        var status: String? = null,
        var originalAmount: Int? = null,
        var savingsAmount: Int? = null,
        var finalAmount: Int? = null,
        var outstandingAmount: Int? = null,
        var remark: String? = null,
        var reason: String? = null
    )
}