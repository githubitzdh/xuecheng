import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import router from '@/router' // src/router.js 或  src/router/index.js
import store from '@/store' // src/store.js 或  src/store/index.js
import APP from './APP'

import '@/scss/global.scss'

// 关闭控制台console一个提示
Vue.config.productionTip = false
// 将element注册vue中
Vue.use(ElementUI)


/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  render: h => <APP />
})
