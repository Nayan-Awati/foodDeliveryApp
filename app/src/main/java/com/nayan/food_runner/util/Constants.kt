package com.nayan.food_runner.util

class Constants{

    companion object {
        const val NETWORK_IP = "http://13.235.250.119/v2/"
        const val REGISTER = "${Companion.NETWORK_IP}/register/fetch_result"
        const val LOGIN = "${Companion.NETWORK_IP}/login/fetch_result"
        const val FORGOT_PASSWORD = "${Companion.NETWORK_IP}/forgot_password/fetch_result"
        const val RESET_PASSWORD = "${Companion.NETWORK_IP}/reset_password/fetch_result"
        const val FETCH_RESTAURANTS = "${Companion.NETWORK_IP}/restaurants/fetch_result"
        const val PLACE_ORDER = "${Companion.NETWORK_IP}/place_order/fetch_result"
        const val FETCH_PREVIOUS_ORDERS = "${Companion.NETWORK_IP}/orders/fetch_result/"
    }
}
