package com.edit.alarmexample1

enum class AlarmType {
    REPEAT_ALARM {
        @Override
        override fun toString(): String {
            return "REPEAT_ALARM"
        }
    },
    ONE_TIME_ALARM {
        @Override
        override fun toString(): String {
            return "ONE_TIME_ALARM"
        }
    }
}