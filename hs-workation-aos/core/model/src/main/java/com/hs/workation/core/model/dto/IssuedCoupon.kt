package com.hs.workation.core.model.dto

data class IssuedCoupon(
    var id: String? = null,
    var status: String? = null,
    var code: String? = null,
    var issueDate: String? = null,
    var expirationDate: String? = null,
    var isUsed: Boolean? = null,
    var issuedType: String? = null,
    var issuedBy: String? = null,
    var usageDate: String? = null,
    var categories: List<UpdateCategory>? = null,
    var couponProfileId: String? = null,
    var coupon: Coupon? = null,
    var memberId: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
) {

    data class Coupon(
        var id: String? = null,
        var name: String? = null,
        var startDate: String? = null,
        var endDate: String? = null,
        var description: String? = null,
        var status: String? = null,
        var issuedCount: Int? = null,
        var couponProfileId: String? = null
    )
}