<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
      <span>编辑页面</span>
    </div>
    <!-- 编辑表单start -->
    <el-form ref="form" :rules="rules" :model="pageForm" label-width="80px">
      <el-form-item label="所属站点" prop="siteId">
        <el-select v-model="pageForm.siteId" placeholder="请选择站点">
          <el-option v-for="(site,index) in siteList" :key="index" :label="site.siteName" :value="site.siteId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="选择模板" prop="templateId">
        <el-select v-model="pageForm.templateId" placeholder="请选择模板">
          <el-option v-for="(template,index) in templateList" :key="index" :label="template.templateName" :value="template.templateId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="页面名称" prop="pageName">
        <el-input v-model="pageForm.pageName" ></el-input>
      </el-form-item>
      <el-form-item label="别名" prop="pageAliase">
        <el-input v-model="pageForm.pageAliase" ></el-input>
      </el-form-item>
      <el-form-item label="访问路径" prop="pageWebPath">
        <el-input v-model="pageForm.pageWebPath"></el-input>
      </el-form-item>
      <el-form-item label="物理路径" prop="pagePhysicalPath">
        <el-input v-model="pageForm.pagePhysicalPath" ></el-input>
      </el-form-item>
      <el-form-item label="类型" prop="pageType">
        <el-radio-group v-model="pageForm.pageType">
          <el-radio label="0" >静态</el-radio>
          <el-radio label="1" >动态</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="创建时间" prop="pageCreateTime">
        <el-date-picker type="date" placeholder="选择日期" v-model="pageForm.pageCreateTime" style="width: 100%;"></el-date-picker>
      </el-form-item>
      <el-form-item label="数据路径" prop="dataUrl">
        <el-input v-model="pageForm.dataUrl" ></el-input>
      </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="addLoading"  @click="editSubmit">提交</el-button>
          <router-link to="/cms/page/list">
          <el-button>返回</el-button>
          </router-link>
        </el-form-item>
    </el-form>
      <!-- 编辑表单end -->
  </el-card>
</template>

<script>
  import { findSiteList, findTemplateList, findPageById, pageEdit } from '@/module/cms/api/cms'
  export default {
    data() {
      return {
        pageForm: {}, // 编辑回显表单
        rules: {
          siteId: [
            { required: true, message: '请选择站点', trigger: 'blur' }
          ],
          templateId: [
            { required: true, message: '请选择模版', trigger: 'blur' }
          ],
          pageName: [
            { required: true, message: '请输入页面名称', trigger: 'blur' }
          ],
          pageWebPath: [
            { required: true, message: '请输入访问路径', trigger: 'blur' }
          ],
          pagePhysicalPath: [
            { required: true, message: '请输入物理路径', trigger: 'blur' }
          ]
        },
        siteList: [], // 站点列表
        templateList: [], // 模板列表
        addLoading: false, // 加载效果标记
        pageId: '' // 页面id
      }
    },
    async created() {
      // 站点列表
      this.findSiteListFn()
      // 模板列表
      this.findTemplateListFn()

      // 获得页面id
      this.pageId = this.$route.params.pageId
      // 查询详情
      this.findPageByIdFn()
    },
    methods: {
      async findSiteListFn() { // 查询所有站点
        const { data } = await findSiteList()
        if (data.success) {
          this.siteList = data.queryResult.list
        }
      },
      async findTemplateListFn() { // 查询所有模板
        const { data } = await findTemplateList()
        if (data.success) {
          this.templateList = data.queryResult.list
        }
      },
      async findPageByIdFn() {
        // 根据id查询详情
        const { data } = await findPageById(this.pageId)
        if (data.success) {
          this.pageForm = data.cmsPage
        }
      },
      editSubmit() {
        // 校验表单
        this.$refs.form.validate((valid) => {
          if (valid) {
            this.$confirm('确认提交吗？', '提示', { type: 'warning' }).then(async () => {
              // 开始加载
              this.addLoading = true
              const { data } = await pageEdit(this.pageId, this.pageForm)
              if (data.success) {
                this.$message.success(data.message)
                // 重置表单
                this.$refs.form.resetFields()
                // 返回列表
                this.$router.push('/cms/page/list')
              }
            })
          }
        })
      }
    }
  }
</script>

<style>

</style>
