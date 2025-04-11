package com.hs.workation.core.model.dto

data class CarContract(
    var driver: Driver? = null,
    var secondaryDriver: Driver? = null,
    var vehicle: Vehicle? = null,
    var reservation: Reservation? = null,
    var usage: Usage? = null,
    var payment: Payment? = null,
    var company: Company? = null
) {
    data class Driver(
        var id: String? = null,
        var name: String? = null,
        var licenseNumber: String? = null,
        var phoneNumber: String? = null,
        var birthday: String? = null,
        var address1: String? = null,
        var address2: String? = null,
        var signUrl: String? = null
    )

    data class Vehicle(
        var id: String? = null,
        var model: String? = null,
        var numberPlate: String? = null,
        var fuelType: String? = null
    )

    data class Reservation(
        var startDate: String? = null,
        var endDate: String? = null,
        var rentalZoneId: String? = null,
        var rentalZoneName: String? = null,
        var returnZoneId: String? = null,
        var returnZoneName: String? = null
    )

    data class Usage(
        var startDate: String? = null,
        var endDate: String? = null,
        var distance: Int? = null
    )

    data class Payment(
        var rentalFee: Int? = null,
        var extendFee: Int? = null,
        var penalty: Int? = null,
        var deposit: Int? = null,
        var totalFee: Int? = null,
        var issuedCouponId: String? = null,
        var issuedCouponName: String? = null
    )

    data class Company(
        var id: String? = null,
        var name: String? = null,
        var address1: String? = null,
        var address2: String? = null,
        var managerName: String? = null,
        var businessNumber: String? = null,
        var signUrl: String? = null,
        var workcationId: String? = null,
        var workcationName: String? = null,
        var associatedServiceId: String? = null,
        var associatedServiceName: String? = null
    )
}