import http from '~/api/public'
import { apiURL, staticURL } from '~/config/sysConfig'
import qs from 'qs'

/*搜索*/
export const search_course = (page,size,params) => {
  //let loginRequest = querystring.stringify(params)
  let querys = qs.stringify(params);
  //return http.get(apiURL+"/search/course/list/"+page+"/"+size+"?"+querys);
  
  return http.get(`${apiURL}/search/course/list/${page}/${size}?${querys}`);
}
/*获取分类*/
export const sysres_category = () => {
  return http.get(staticURL+"/category/category.json");
}
