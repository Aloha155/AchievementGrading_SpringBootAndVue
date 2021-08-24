import {Message} from 'element-ui';

export default {
    messageSuccessAlert(message, duration = 1000) {
        Message({
            message,
            duration,
            type: 'success'
        })
    },
    messageWarnAlert(message, duration = 1000) {
        Message({
            message,
            duration,
            type: 'warning'
        })
    },
    messageErrorAlert(message, duration = 1000) {
        Message({
            message,
            duration,
            type: 'error'
        })
    },
    messageInfoAlert(message, duration = 1000) {
        Message({
            message,
            duration,
        })
    }
}