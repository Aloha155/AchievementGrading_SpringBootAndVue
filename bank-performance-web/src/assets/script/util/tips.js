import ElementUI from 'element-ui';



export default {
    async success(message, title = "成功", offset = 100, showClose = false) {
        return new Promise((resolve) => {
            ElementUI.Notification.success({
                title,
                message,
                offset,
                showClose,
                onClose: resolve
            });
        })
    },
    async warn(message, title = '警告', offset = 100, showClose = false) {
        return new Promise((resolve) => {
            ElementUI.Notification.warning({
                title,
                message,
                offset,
                showClose,
                onClose: resolve
            });
        })
    },
    async error(message, title = "错误", offset = 100, showClose = false) {
        return new Promise((resolve) => {
            ElementUI.Notification.error({
                title,
                message,
                offset,
                showClose,
                onClose: resolve
            });
        })
    },
    async info(message, title = "普通", offset = 100, showClose = false) {
        return new Promise((resolve) => {
            ElementUI.Notification.info({
                title,
                message,
                offset,
                showClose,
                onClose: resolve
            });
        })
    },
}




