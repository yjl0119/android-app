package uk.ac.ncl.openlab.irismsg.common

enum class MessageAttemptState {
    PENDING,
    REJECTED,
    FAILED,
    SUCCESS,
    NO_SERVICE,
    NO_SMS_DATA,
    RADIO_OFF,
    TWILIO,
    NO_RESPONSE
}