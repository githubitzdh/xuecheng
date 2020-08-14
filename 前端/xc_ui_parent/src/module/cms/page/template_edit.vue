<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
      <span>编辑页面</span>
    </div>
    <!-- 编辑表单start -->
       <el-form ref="form" :rules="rules" :model="pageForm" label-width="80px">
     <el-form-item label="选择站点">
      <el-select  v-model="pageForm.siteId" placeholder="请选择站点">
      <el-option  v-for="(site,index) in siteList" :key="index"    :label="site.siteName" :value="site.siteId"></el-option>
    </el-select>
  </el-form-item>
      <el-form-item label="模板名称">
        <el-input v-model="pageForm.templateName" prop="pageName"></el-input>
      </el-form-item>
           <el-form-item label="模版参数">
        <el-input v-model="pageForm.templateParameter" prop="pageName"></el-input>
      </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="editSubmit('form')" >提交</el-button>
          <el-button>返回</el-button>
        </el-form-item>
    </el-form>
      <!-- 编辑表单end -->
  </el-card>
</template>

<script>
  import { findSiteList, findTemplateById, TemplateEdit } from '@/module/cms/api/cms'
  export default {
    data() {
      return {
        pageForm: {}, // 编辑回显表单
        siteList: [], // 站点列表
        addLoading: false, // 加载效果标记
        templateId: '' // id
      }
    },
    async created() {
      // 站点列表
      this.findSiteListFn()
      // 获得页面id
      this.templateId = this.$route.params.templateId
      // 查询详情
      this.findTemplateByIdFn()
    },
    methods: {
      async findSiteListFn() { // 查询所有站点
        const { data } = await findSiteList()
        if (data.success) {
          this.siteList = data.queryResult.list
        }
      },
      async findTemplateByIdFn() {
        // 根据id查询详情
        const { data } = await findTemplateById(this.templateId)
        if (data.success) {
          this.pageForm = data.cmsTemplate
        }
      },
      editSubmit(form) {
        this.$confirm('确认提交吗？', '提示', { type: 'warning' }).then(async () => {
          // 开始加载
          this.addLoading = true
          const { data } = await TemplateEdit(this.templateId, this.pageForm)
          if (data.success) {
            this.$message.success(data.message)
            // 重置表单
            this.$refs[form].resetFields()
            // 返回列表
            this.$router.push('/cms/template/list')
          }
        })
      }
    }
  }
</script>

<style>

</style>
