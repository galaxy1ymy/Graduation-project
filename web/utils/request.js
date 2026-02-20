import axios from 'axios'
import store from '@/store'
import { notification } from 'ant-design-vue'

const request = axios.create({
    baseURL: '/',   // ✅ 改成 /
    timeout: 10000
})

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

request.interceptors.response.use(
    response => {
        const res = response.data

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