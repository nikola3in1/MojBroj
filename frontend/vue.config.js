module.exports = {
    devServer: {
        proxy: {
            '/api': {
                target: 'http://localhost:3030/',
                ws: true,
                changeOrigin: true
            }
        }
    }
}