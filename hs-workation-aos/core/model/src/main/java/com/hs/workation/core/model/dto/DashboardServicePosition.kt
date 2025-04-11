package com.hs.workation.core.model.dto

data class DashboardServicePosition(
    var workcation: List<Workcation>? = null,
    var spot: List<Spot>? = null,
    var autoDriving: List<Position>? = null,
    var routeBased: List<Position>? = null,
    var drt: List<Position>? = null,
    var evCarShort: List<Position>? = null,
    var evMotorcycleShort: List<Position>? = null,
    var evClassicShort: String? = null,
    var workcationId: String? = null,
    var lat: String? = null,
    var lon: String? = null
) {
    data class Workcation(
        var workcationId: String? = null,
        var totalCount: Int? = null,
        var usageCount: Int? = null,
        var lat: String? = null,
        var lon: String? = null
    )
    data class Spot(
        var workcationId: String? = null,
        var isUsage: Boolean? = null,
        var lat: String? = null,
        var lon: String? = null
    )
    data class Position(
        var workcationId: String? = null,
        var isUsage: Boolean? = null,
        var lat: String? = null,
        var lon: String? = null
    )
}