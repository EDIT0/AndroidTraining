package com.edit.alarmexample1

enum class AlarmType {
    REPEAT_ALARM_PER_DAY {
        @Override
        override fun toString(): String {
            return "REPEAT_ALARM_PER_DAY"
        }
    },
    ONE_TIME_ALARM_PER_TIME {
        @Override
        override fun toString(): String {
            return "ONE_TIME_ALARM_PER_TIME"
        }
    },
    ONE_TIME_ALARM_PER_DAY {
        @Override
        override fun toString(): String {
            return "ONE_TIME_ALARM_PER_DAY"
        }
    }
}