module.exports = {
    devServer: {
        disableHostCheck: true,
        port: 10185, // 端口
        proxy: {
            "/api": {
                // 目标 API 地址
                target: "http://localhost:8561/",
                // 如果要代理 websockets
                ws: true,
                secure: false,
                // 将主机标头的原点更改为目标URL
                changeOrigin: true,
                pathRewrite: {"^/api": "/"}
            }
        }
    }
}
