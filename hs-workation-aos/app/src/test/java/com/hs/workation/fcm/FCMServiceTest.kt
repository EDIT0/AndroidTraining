package com.hs.workation.fcm

import com.hs.workation.core.common.constants.FCMConstants.FCM_TYPE_COMMON
import com.hs.workation.core.model.fcm.CommonPayload
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FCMServiceTest {

    private val fcmService = FCMService()

    private val payloadMap = HashMap<String?, String?>()

    @Before
    fun setUp() {
        payloadMap["type"] = FCM_TYPE_COMMON
        payloadMap["nick"] = "Nick"
        payloadMap["body"] = null
        payloadMap["room"] = "Room"
        payloadMap["unKnown"] = "unKnown"
    }

    @Test
    fun parserSuccess() {
        val payloadModel = fcmService.parser(data = payloadMap)

        val payload = (payloadModel as CommonPayload)

        val nick: String = payload.nick?:""
        val body: String = payload.body?:""
        val room: String = payload.room?:""

        Assert.assertEquals(nick, "Nick")
        Assert.assertNotNull(body)
        Assert.assertEquals(room, "Room")
    }

    @Test
    fun parserFailure() {
        val payloadModel = fcmService.parser(data = payloadMap)

        val payload = (payloadModel as CommonPayload)

        val nick: String = payload.nick?:""
        val body: String = payload.body?:""
        val room: String = payload.room?:""

        Assert.assertEquals(nick, "Nick")
        Assert.assertNull(body)
        Assert.assertEquals(room, "Room")
    }
}