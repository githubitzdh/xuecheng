import Home from '@/module/home/page/home.vue'
import PageList from '@/module/cms/page/page_list.vue'
import PageAdd from '@/module/cms/page/page_add.vue'
import pageEdit from '@/module/cms/page/page_edit.vue'
import Template from '@/module/cms/page/template.vue'
import TemplateAdd from '@/module/cms/page/template_add.vue'
import TemplateEdit from '@/module/cms/page/template_edit.vue'

// 其他模块都是home子路由
export default [{
    path: '/cms',
    component: Home,
    name: 'CMS',
    hidden: false,
    children: [ // 二级菜单
      {
        path: '/cms/page/list',
        name: '页面列表',
        component: PageList,
        hidden: false // 自定义数据,是否隐藏二级菜单
       },
      {
        path: '/cms/page/add',
        name: '添加页面',
        component: PageAdd,
        hidden: true
      },
      {
        path: '/cms/page/edit/:pageId',
        name: '页面编辑',
        component: pageEdit,
        hidden: true
      },
      {
        path: '/cms/template/list',
        name: '模板页面',
        component: Template,
        hidden: false
      },
      {
        path: '/cms/template/add',
        name: '模板添加页面',
        component: TemplateAdd,
        hidden: true
      },
      {
        path: '/cms/template/edit/:templateId',
        name: '页面编辑',
        component: TemplateEdit,
        hidden: true
      }
    ]
  }
]
