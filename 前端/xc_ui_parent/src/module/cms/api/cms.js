import http from '@/api/public'
import { xcApiUrlPre } from '@/../config/sysConfig'

// 定义具体的方法

// 查询页面列表 (params: key和value相同可以省略,只需要写一个params即可)
export function pageList(pageParams, params) {
  // return http.get(xcApiUrlPre + '/cms/page/list/1/10?siteId=123')
  return http.get(`${xcApiUrlPre}/cms/page/list/${pageParams.page}/${pageParams.size}`, {
    params
  })
}

// 查询所有站点
export function findSiteList() {
  return http.get(`${xcApiUrlPre}/cms/site`)
}

// 查询所有模板
export function findTemplateList() {
  return http.get(`${xcApiUrlPre}/cms/template`)
}

// 添加页面
export function pageAdd(params) {
  return http.post(`${xcApiUrlPre}/cms/page/add`, params)
}


// 根据id查询详情
export function findPageById(id) {
  return http.get(`${xcApiUrlPre}/cms/page/get/${id}`)
}

// 页面修改
export function pageEdit (id, params) {
  return http.put(`${xcApiUrlPre}/cms/page/edit/${id}`, params)
}
// 页面删除
export function pageDelete(id) {
  return http.delete(`${xcApiUrlPre}/cms/page/del/${id}`)
}


// 模板------------
// 模板分页
export function pageTemplateList(pageParams, params) {
  // return http.get(xcApiUrlPre + '/cms/page/list/1/10?siteId=123')
  return http.get(`${xcApiUrlPre}/cms/template/list/${pageParams.page}/${pageParams.size}`, {
    params
  })
}

// 添加模板
export function TemplateAdd(params) {
  return http.post(`${xcApiUrlPre}/cms/template/add`, params)
}

// 根据id模板查询
export function findTemplateById(id) {
  return http.get(`${xcApiUrlPre}/cms/template/get/${id}`)
}

// 模板修改
export function TemplateEdit (id, params) {
  return http.put(`${xcApiUrlPre}/cms/template/edit/${id}`, params)
}

// 模板删除
export function TemplateDelete(id) {
  return http.delete(`${xcApiUrlPre}/cms/template/del/${id}`)
}

// 发布页面
export function postPage (id) {
  return http.post(`${xcApiUrlPre}/cms/page/postPage/${id}`)
}
