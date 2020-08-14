package com.czxy.xuecheng.manage_course.service;

import com.alibaba.fastjson.JSONObject;
import com.czxy.xuecheng.common.exception.ExceptionCast;
import com.czxy.xuecheng.common.model.response.CommonCode;
import com.czxy.xuecheng.common.model.response.QueryResponseResult;
import com.czxy.xuecheng.common.model.response.QueryResult;
import com.czxy.xuecheng.common.model.response.ResponseResult;
import com.czxy.xuecheng.domain.cms.CmsPage;
import com.czxy.xuecheng.domain.cms.response.CmsPageResult;
import com.czxy.xuecheng.domain.cms.response.CmsPostPageResult;
import com.czxy.xuecheng.domain.course.*;
import com.czxy.xuecheng.domain.course.ext.CourseInfo;
import com.czxy.xuecheng.domain.course.ext.CourseView;
import com.czxy.xuecheng.domain.course.ext.TeachplanNode;
import com.czxy.xuecheng.domain.course.request.CourseListRequest;
import com.czxy.xuecheng.domain.course.response.CourseCode;
import com.czxy.xuecheng.domain.course.response.CoursePicResult;
import com.czxy.xuecheng.domain.course.response.CoursePublishResult;
import com.czxy.xuecheng.manage_course.dao.*;
import com.czxy.xuecheng.manage_course.feign.CmsPageFeign;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/7/22 0022
 **/
@Service
public class CourseService {
    @Resource
    private CourseMapper courseMapper;

    /**
     * 查询所有课程
     *
     * @param companyId
     * @param size
     * @param page
     * @param courseListRequest
     * @return
     */
    public QueryResponseResult<CourseInfo> findCourseList(String companyId, int size, int page, CourseListRequest courseListRequest) {
        // 1.准备参数
        if (courseListRequest == null) {
            courseListRequest = new CourseListRequest();
        }
        courseListRequest.setCompanyId(companyId);
        // 2.分页
        PageHelper.startPage(page, size);

        // 3.查询
        List<CourseInfo> list = courseMapper.findCourseListPage(courseListRequest);
        PageInfo<CourseInfo> pageInfo = new PageInfo<>(list);

        // 4.封装
        QueryResult<CourseInfo> queryResult = new QueryResult<>();
        queryResult.setList(list);
        queryResult.setTotal(pageInfo.getTotal());
        return new QueryResponseResult<>(CommonCode.SUCCESS, queryResult);

    }


    /**
     * 查询指定课程的课程计划
     */
    @Resource
    private TeachplanMapper teachplanMapper;

    public TeachplanNode findTeachplanList(String courseId) {
        return teachplanMapper.selectList(courseId);
    }

    @Resource
    private TeachplanRepository teachplanRepository;

    /**
     * 添加课程计划
     *
     * @param teachplan
     * @return
     */
    public ResponseResult addTeachplan(Teachplan teachplan) {
        //0 校验数据
        if (teachplan == null ||
                StringUtils.isBlank(teachplan.getCourseid()) ||
                StringUtils.isBlank(teachplan.getPname())) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //1 准备数据:课程,父节点
        // 1.1 获得课程id
        String courseid = teachplan.getCourseid();
        // 1.2 获得父id
        String parentid = teachplan.getParentid();
        // 获得一级节点id,如果没有,创建新的
        if (StringUtils.isBlank(parentid)) {
            //取出该课程的根结点
            parentid = getTeachplanRoot(courseid);
        }

        // 2 查询父节点(一级, 二级)
        Optional<Teachplan> optional = teachplanRepository.findById(parentid);
        Teachplan parentNode = optional.get();

        // 3 封装二级或三级的数据
        Teachplan teachplanNew = new Teachplan();
        // 3.1 将页面提交的teachplan信息拷贝到teachplanNew对象中
        BeanUtils.copyProperties(teachplan, teachplanNew);
        // 3.2 设置课程信息和父id
        teachplanNew.setParentid(parentid);
        teachplanNew.setCourseid(courseid);
        // 3.3 根据父节点的级别设置级别，最多3级
        if (parentNode.getGrade().equals("1")) {
            teachplanNew.setGrade("2");
        } else {
            teachplanNew.setGrade("3");
        }

        // 4 保存计划
        teachplanRepository.save(teachplanNew);
        return new ResponseResult(CommonCode.SUCCESS);
    }


    @Resource
    private CourseBaseRepository courseBaseRepository;

    /**
     * 获得一级节点,如果不存在,创建新的
     *
     * @param courseId
     * @return
     */
    private String getTeachplanRoot(String courseId) {
        // 1. 查询课程
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if (!optional.isPresent()) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        // 2 获取
        CourseBase courseBase = optional.get();

        // 3 通过课程和父id进行查询
        List<Teachplan> list = teachplanRepository.findByCourseidAndParentid(courseId, "0");
        // 3.1 如果不存在创建新的
        if (list == null || list.size() <= 0) {
            Teachplan teachplan = new Teachplan();
            teachplan.setPname(courseBase.getName());
            teachplan.setParentid("0");
            teachplan.setGrade("1");
            teachplan.setCourseid(courseId);
            teachplan.setStatus("0");

            teachplanRepository.save(teachplan);
            return teachplan.getId();
        }

        // 3.2 如果存在,返回id
        return list.get(0).getId();
    }


    @Resource
    private CourserPicRepository coursePicRepository;

    /**
     * 添加课程图片,只允许添加一张图片,如果图片存在,将更新图片
     *
     * @param courseId
     * @param pic
     * @return
     */
    public ResponseResult addCoursePic(String courseId, String pic) {
        // 1. 课程图片信息
        CoursePic coursePic = null;
        //查询课程图片
        Optional<CoursePic> Optional = coursePicRepository.findById(courseId);
        if (Optional.isPresent()) {
            coursePic = Optional.get();
        } else {
            coursePic = new CoursePic();
        }
        // 2. 更新图片信息
        coursePic.setPic(pic);
        coursePic.setCourseid(courseId);
        // 3.保存操作
        coursePicRepository.save(coursePic);
        return new ResponseResult(CommonCode.SUCCESS);
    }


    /**
     * 查询课程图片
     *
     * @param courseId
     * @return
     */
    public CoursePicResult findCoursePic(String courseId) {
        // 1.查询课程图片
        Optional<CoursePic> optional = coursePicRepository.findById(courseId);
        if (optional.isPresent()) {
            CoursePic coursePic = optional.get();
            return new CoursePicResult(CommonCode.SUCCESS, coursePic);
        }
        // 2.封装
        return new CoursePicResult(CommonCode.FAIL, null);
    }

    /**
     * 删除课程图片
     *
     * @param courseId
     * @return
     */
    public ResponseResult deleteCoursePic(String courseId) {
        try {
            coursePicRepository.deleteById(courseId);
            return new ResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(CommonCode.FAIL);
        }

    }


    @Resource
    private CourseMarketRepository courseMarketRepository;

    /**
     * 查询课程详情数据
     *
     * @param courseId
     * @return
     */
    public CourseView getCoruseView(String courseId) {
        //1 创建封装对象
        CourseView courseView = new CourseView();

        // 2.1 查询课程基本信息
        Optional<CourseBase> courseBaseOptional = courseBaseRepository.findById(courseId);
        if (courseBaseOptional.isPresent()) {
            CourseBase courseBase = courseBaseOptional.get();
            courseView.setCourseBase(courseBase);
        }
        // 2.2 查询课程图片
        Optional<CoursePic> picOptional = coursePicRepository.findById(courseId);
        if (picOptional.isPresent()) {
            CoursePic coursePic = picOptional.get();
            courseView.setCoursePic(coursePic);
        }
        // 2.3 课程营销信息
        Optional<CourseMarket> marketOptional = courseMarketRepository.findById(courseId);
        if (marketOptional.isPresent()) {
            CourseMarket courseMarket = marketOptional.get();
            courseView.setCourseMarket(courseMarket);
        }
        // 2.4 课程计划信息
        TeachplanNode teachplanNode = teachplanMapper.selectList(courseId);
        courseView.setTeachplanNode(teachplanNode);
        // 返回
        return courseView;
    }

    @Resource
    private CmsPageFeign cmsPageFeign;

    @Value("${course-publish.siteId}")
    private String publish_siteId;

    @Value("${course-publish.templateId}")
    private String publish_templateId;

    @Value("${course-publish.dataUrlPre}")
    private String publish_dataUrlPre;

    @Value("${course-publish.pagePhysicalPath}")
    private String publish_page_physicalpath;

    @Value("${course-publish.previewUrl}")
    private String previewUrl;
    @Value("${course-publish.pageWebPath}")
    private String publish_page_webPath;

    /**
     * 课程预览
     *
     * @param courseId
     * @return
     */
    public CoursePublishResult preview(String courseId) {
        //1 查询课程
        CourseBase courseBaseById = this.findCourseBaseById(courseId);

        //2 封装cmsPage信息(一个课程,对应一个页面)
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId(publish_siteId);  //站点id
        cmsPage.setTemplateId(publish_templateId);//页面模板id
        cmsPage.setDataUrl(publish_dataUrlPre + courseId);    //数据模型url
        cmsPage.setPageName(courseId + ".html");    //页面名称
        cmsPage.setPageAliase(courseBaseById.getName());//页面别名，就是课程名称
        cmsPage.setPagePhysicalPath(publish_page_physicalpath); //页面物理路径
        cmsPage.setPageWebPath(publish_page_webPath);   //web路径

        //3 远程调用,保存页面,获得页面id
        CmsPageResult cmsPageResult = cmsPageFeign.save(cmsPage);
        if (!cmsPageResult.isSuccess()) {
            return new CoursePublishResult(CommonCode.FAIL, null);
        }

        //4 拼凑预览url, 并返回
        CmsPage findCmsPage = cmsPageResult.getCmsPage();
        String pageId = findCmsPage.getPageId();
        //拼装页面预览的url
        String url = previewUrl + pageId;
        //返回CoursePublishResult对象（当中包含了页面预览的url）
        return new CoursePublishResult(CommonCode.SUCCESS, url);
    }


    /**
     * 根据id查询课程基本信息
     *
     * @param courseId
     * @return
     */
    public CourseBase findCourseBaseById(String courseId) {
        Optional<CourseBase> baseOptional = courseBaseRepository.findById(courseId);
        if (baseOptional.isPresent()) {
            return baseOptional.get();
        }
        ExceptionCast.cast(CourseCode.COURSE_GET_NOTEXISTS);
        return null;
    }
    /**
     * 课程预览
     * 方式二
     * @param courseId
     * @return
     */
  /*  public CoursePublishResult preview(String courseId) {
        // 1.查询课程
        Optional<CourseBase> courseBaseOptional = courseBaseRepository.findById(courseId);
        if(! courseBaseOptional.isPresent()) {
            ExceptionCast.cast(CourseCode.COURSE_GET_NOTEXISTS);
        }
        CourseBase courseBase = courseBaseOptional.get();
        // 2.封装页面(一个课程,对应一个页面)
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId(publish_siteId); // 站点id
         cmsPage.setTemplateId(publish_templateId); // 页面模板id
         cmsPage.setDataUrl(publish_dataUrlPre + courseId); // 数据模型url
         cmsPage.setPageName(courseId + ".html");// 页面名称
        cmsPage.setPageAliase(courseBase.getName()); // 页面别名,就是课程名称
        cmsPage.setPagePhysicalPath(publish_page_physicalpath); //页面物理路径
         // 3.远程调用,保存页面,获得页面id
        CmsPageResult cmsPageResult = cmsPageFeign.save(cmsPage);
        System.out.println(cmsPageResult);
        CmsPage findCmsPage = cmsPageResult.getCmsPage();
        if(findCmsPage == null) {
         ExceptionCast.cast(CommonCode.FAIL);
        }
        String pageId = findCmsPage.getPageId();
        // 4.拼凑预览url, 并返回
        //http://localhost:8080/api/cms/preview/5f0969039e762f3d2c527608
        //http://www.xuecheng.com/cms/preview/5f0969039e762f3d2c527608
       String url = previewUrl + pageId;
        return new CoursePublishResult(CommonCode.SUCCESS, url);
    }*/


    /**
     * 课程发布
     *
     * @param courseId
     * @return
     */
    public CoursePublishResult publish(String courseId) {
        //1. 查询课程
        CourseBase courseBase = this.findCourseBaseById(courseId);

        //2. 准备页面信息CmsPage
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId(publish_siteId);  //站点id
        cmsPage.setTemplateId(publish_templateId);//页面模板id
        cmsPage.setDataUrl(publish_dataUrlPre + courseId);    //数据模型url
        cmsPage.setPageName(courseId + ".html");    //页面名称
        cmsPage.setPageAliase(courseBase.getName());//页面别名，就是课程名称
        cmsPage.setPagePhysicalPath(publish_page_physicalpath); //页面物理路径
        cmsPage.setPageWebPath(publish_page_webPath);  //页面webpath

        //3. 一键发布
        CmsPostPageResult cmsPostPageResult = cmsPageFeign.postPageQuick(cmsPage);
        if (!cmsPostPageResult.isSuccess()) {
            ExceptionCast.cast(CommonCode.FAIL);
        }

        //4. 更新课程状态为“已发布” 202002
        CourseBase courseBaseState = this.saveCoursePubState(courseId, "202002"); // 已发布,数据字典中的信息

        // 5.保存发布信息
        // 5.1.封装信息
        CoursePub coursePub = createCoursePub(courseId);
        // 5.2.
        saveCoursePub(courseId, coursePub);

        //5. 封装返回结果
        String pageUrl = cmsPostPageResult.getPageUrl();

        return new CoursePublishResult(CommonCode.SUCCESS, pageUrl);
    }


    /**
     * 修改课程状态
     *
     * @param courseId
     * @param state
     * @return
     */
    public CourseBase saveCoursePubState(String courseId, String state) {
        // 1.查询
        CourseBase courseBaseById = this.findCourseBaseById(courseId);
        // 2.更新
        courseBaseById.setStatus(state);
        // 3.保存
        courseBaseRepository.save(courseBaseById);
        // 4.返回
        return courseBaseById;
    }

    /**
     * 课程发布信息的封装
     *
     * @param courseId
     * @return
     */
    private CoursePub createCoursePub(String courseId) {
        // 1.创建
        CoursePub coursePub = new CoursePub();
        // 2.封装数据
        // 2.1.课程基本表
        Optional<CourseBase> courseBaseOptional = courseBaseRepository.findById(courseId);
        if (courseBaseOptional.isPresent()) {
            CourseBase courseBase = courseBaseOptional.get();
            // 通过工具封装数据 (封装更快,不需要一步步填充)
            BeanUtils.copyProperties(courseBase, coursePub);
        }

        // 2.2.课程图片表
        Optional<CoursePic> coursePicOptional = coursePicRepository.findById(courseId);
        if (coursePicOptional.isPresent()) {
            CoursePic coursePic = coursePicOptional.get();
            // 通过工具封装数据
            BeanUtils.copyProperties(coursePic, coursePub);
        }
        // 2.3.课程营销表
        Optional<CourseMarket> courseMarketOptional = courseMarketRepository.findById(courseId);
        if (courseMarketOptional.isPresent()) {
            CourseMarket courseMarket = courseMarketOptional.get();
            // 通过工具封装数据 (封装更快,不需要一步步填充)
            BeanUtils.copyProperties(courseMarket, coursePub);
        }
        // 2.4.课程计划
        List<Teachplan> list = teachplanRepository.findByCourseidAndParentid(courseId, "0");
        if (list != null && list.size() > 0) {
            // 将课程计划转换json,并存放
            String jsonStr = JSONObject.toJSONString(list);
            coursePub.setTeachplan(jsonStr);
        }
        // 3.返回
        return coursePub;
    }


    @Resource
    private CoursePubRepository coursePubRepository;

    /**
     * 封装保存信息
     *
     * @param courseId
     * @param coursePub
     */
    private void saveCoursePub(String courseId, CoursePub coursePub) {
        // 1.准备数据(处理数据)
        // 1.1.设置id
        coursePub.setId(courseId);
        // 1.2.需要设置时间戳数据,给logstash使用
        coursePub.setTimestamp(new Date());
        // 1.3.记录发布时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataStr = simpleDateFormat.format(new Date());
        coursePub.setPubTime(dataStr);
        //coursePub.setPubTime(new Date().toLocaleString()); //方式二,

        // 2.保存
        coursePubRepository.save(coursePub);
    }


}
