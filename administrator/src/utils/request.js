import axios from 'axios'
import store from '@/store'
import { notification } from 'ant-design-vue'

// 创建 axios 实例
const request = axios.create({
    baseURL: 'http://127.0.0.1:8001', // 后端地址
    timeout: 10000
})

/**
 * 请求拦截器
 */
request.interceptors.request.use(
    config => {
        const token = store.getters.getToken
        if (token) {
            config.headers.Authorization = token
        }
        return config
    },
    error => Promise.reject(error)
)

/**
 * 响应拦截器
 */
request.interceptors.response.use(
    response => {
        const res = response.data

        // 如果你后端是 CommonResp 结构
        if (res && res.success === false) {
            notification.error({
                message: '请求失败',
                description: res.message || '系统异常'
            })
            return Promise.reject(res)
        }

        return res
    },
    error => {
        notification.error({
            message: '网络异常',
            description: error.message || '无法连接服务器'
        })
        return Promise.reject(error)
    }
)

export default request
