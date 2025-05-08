package com.hs.workation.core.util

import org.json.JSONObject
import java.util.Base64

object JwtUtil {

    fun decodeJWT(jwt: String?): Map<String, Any?> {
        val result = mutableMapOf<String, Any?>()

        try {
            if (jwt.isNullOrBlank()) {
                result["error"] = "JWT is null or empty"
                return result
            }

            val parts = jwt.split(".")
            if (parts.size != 3) {
                result["error"] = "Invalid JWT format"
                return result
            }

            val payload = parts[1]

            val decodedBytes = try {
                Base64.getUrlDecoder().decode(payload)
            } catch (e: IllegalArgumentException) {
                result["error"] = "Base64 decoding failed: ${e.message}"
                return result
            }

            val decodedString = String(decodedBytes, Charsets.UTF_8)

            val jsonObject = try {
                JSONObject(decodedString)
            } catch (e: Exception) {
                result["error"] = "JSON parsing failed: ${e.message}"
                return result
            }

            jsonObject.keys().forEach {
                result[it] = jsonObject.opt(it)
            }

        } catch (e: Exception) {
            result["error"] = "Unexpected error: ${e.message}"
        }

        return result
    }

}