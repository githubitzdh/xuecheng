<template>
<!-- 卡片start -->
  <el-card class="box-card">
    <div slot="header" class="clearfix">
      <span>添加页面</span>
    </div>

  <!-- 添加表单start -->
      <el-form ref="form" :rules="rules" :model="pageForm" label-width="80px">
     <el-form-item label="选择站点">
      <el-select  v-model="pageForm.siteId" placeholder="请选择站点">
      <el-option  v-for="(site,index) in siteList" :key="index"    :label="site.siteName" :value="site.siteId"></el-option>
    </el-select>
  </el-form-item>
    <el-form-item label="选择模板">
        <el-select v-model="pageForm.templateId" placeholder="请选择模板">
          <el-option  v-for="(template,index) in templateList" :key="index" :label="template.templateName" :value="template.templateId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="页面名称">
        <el-input v-model="pageForm.pageName" prop="pageName"></el-input>
      </el-form-item>
      <el-form-item label="别名">
        <el-input v-model="pageForm.pageAliase"></el-input>
      </el-form-item>
      <el-form-item label="访问路径" prop="pageWebPath">
        <el-input v-model="pageForm.pageWebPath"></el-input>
      </el-form-item>
      <el-form-item label="物理路径" prop="pagePhysicalPath">
        <el-input v-model="pageForm.pagePhysicalPath" ></el-input>
      </el-form-item>
      <el-form-item label="类型">
        <el-radio-group v-model="pageForm.pageType">
          <el-radio label="0">静态</el-radio>
          <el-radio label="1">动态</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker type="date" placeholder="选择日期" v-model="pageForm.pageCreateTime" style="width: 100%;"></el-date-picker>
      </el-form-item>
      <el-form-item label="数据路径" prop="dataUrl">
        <el-input v-model="pageForm.dataUrl" ></el-input>
      </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addSubmit('form')" >提交</el-button>
          <el-button>返回</el-button>
        </el-form-item>
    </el-form>
  <!-- 添加表单end -->
  </el-card>
  <!-- 卡片end -->
</template>

<script>
  import { findSiteList, findTemplateList, pageAdd } from '@/module/cms/api/cms'
  export default {
    data() {
      return {
        pageForm: {}, // 表单数据
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
        templateList: [] // 模板列表
      }
    },
    created() {
      // 站点列表
      this.findSiteListFn()
      // 模板列表
      this.findTemplateListFn()
    },
    methods: {
      async findSiteListFn() {
        const { data } = await findSiteList()
        if (data.success) {
          this.siteList = data.queryResult.list
        }
      },
      async findTemplateListFn() {
        const { data } = await findTemplateList()
        if (data.success) {
          this.templateList = data.queryResult.list
        }
      },
      addSubmit(form) {
        // 校验表单
        this.$refs.form.validate((valid) => {
          if (valid) {
            // 弹框
            this.$confirm('您确定要添加吗?', '提示', { type: 'warning' })
              .then(async() => {
                // 成功回调
                // ajax添加
                const { data } = await pageAdd(this.pageForm)
                if (data.success) {
                  this.$message.success(data.message)
                  // 表单重置
                  // this.$refs.form.resetFields()
                  this.$refs[form].resetFields()
                  // 跳转列表
                  this.$router.push('/cms/page/list')
                }
              }).catch(() => {
                // 失败回调
                this.$message({
                  type: 'info',
                  message: '已取消删除'
                })
              })
          }
        })
      }
    }
  }
</script>

<style>

</style>
