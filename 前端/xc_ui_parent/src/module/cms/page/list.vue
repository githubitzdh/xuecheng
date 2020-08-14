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
      <el-form-item label="模板名称">
        <el-input v-model="pageForm.templateName" prop="pageName"></el-input>
      </el-form-item>
           <el-form-item label="模版参数">
        <el-input v-model="pageForm.templateParameter" prop="pageName"></el-input>
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
  import { findSiteList, TemplateAdd } from '@/module/cms/api/cms'
  export default {
    data() {
      return {
        pageForm: {}, // 表单数据
        siteList: [] // 站点列表
      }
    },
    created() {
      // 站点列表
      this.findSiteListFn()
    },
    methods: {
      async findSiteListFn() {
        const { data } = await findSiteList()
        if (data.success) {
          this.siteList = data.queryResult.list
        }
      },
      addSubmit(form) {
        // 校验表单
        // 弹框
        this.$confirm('您确定要添加吗?', '提示', { type: 'warning' })
          .then(async() => {
            // 成功回调
            // ajax添加
            const { data } = await TemplateAdd(this.pageForm)
            if (data.success) {
              this.$message.success(data.message)
              // 表单重置
              // this.$refs.form.resetFields()
              this.$refs[form].resetFields()
              // 跳转列表
              this.$router.push('/cms/template/list')
            }
          }).catch(() => {
            // 失败回调
            this.$message({
              type: 'info',
              message: '已取消删除'
            })
          })
      }
    }
  }
</script>

<style>

</style>
