// 导入页面
import Home from '@/module/home/page/home.vue'

// 声明路由
export default [
  {
    path: '/',
    component: Home,
    name: '系统管理首页',
    hidden: true // 是否隐藏（不显示成菜单）;是否作为作为一级菜单显示
  }
]
