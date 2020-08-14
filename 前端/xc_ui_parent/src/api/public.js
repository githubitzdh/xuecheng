// 1 导入axios
import axios from 'axios'
// 1.1 导入element message组件,ajax出错时,给出友好提示
import { Message } from 'element-ui'

// 2 自定义处理
// 2.1.设置token,与后面权限校验时使用
const token = sessionStorage.getItem('token')
// 如果token存在，设置请求头
if (token) {
  axios.defaults.headers.Authorization = token
}

// 3 配置拦截器
axios.interceptors.response.use(response => {
  // 正常,放行
  // 统一异常处理
  if (response.data.success === false) {
    Message.error(response.data.message)
  }
  return response
}, error => {
  // 错误提示
  Message.error(error.message)
  return Promise.reject(error)
})

// 4 导入修改后的axios
export default axios
