import Vue from 'vue'
import VueRouter from 'vue-router'
// 导入路由规则
import HomeRouter from '@/module/home/router'
import CmsRouter from '@/module/cms/router'

Vue.use(VueRouter)

// 定义路由配置
let routes = []
const concat = (router) => {
  routes = routes.concat(router)
}

// 合并路由规则
// 1) 合并首页路由
 concat(HomeRouter)
 // 2) 合并cms路由
 concat(CmsRouter)

 // 声明路由
const router = new VueRouter({
  // mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 给路由设置拦截器，设置页面标题
router.beforeEach((to, from, next) => {
  // 修改标题
  if (to.meta.title) {
    document.title = to.meta.title
  } else {
    document.title = '学成'
  }

  // 放行
  next()
})

export default router
