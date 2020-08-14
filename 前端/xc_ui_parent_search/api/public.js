// 1 导入axios
import axios from 'axios'
// 1.1 导入element message组件
import { Message } from 'element-ui'

// 3 配置拦截器
axios.interceptors.response.use(response => {
  // 放行
  // 全局异常处理
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
