<template>
<!-- 卡片start -->
  <el-card class="box-card">
    <div slot="header" class="clearfix">
      <span>添加模板</span>
    </div>

  <!-- 添加表单start -->
      <el-form ref="form" :rules="rules" :model="templateForm" label-width="80px">
     <el-form-item label="选择站点">
      <el-select  v-model="templateForm.siteId" placeholder="请选择站点">
      <el-option  v-for="(site,index) in siteList" :key="index"    :label="site.siteName" :value="site.siteId"></el-option>
    </el-select>
  </el-form-item>
      <el-form-item label="模板名称" prop="templateName">
        <el-input v-model="templateForm.templateName" ></el-input>
      </el-form-item>
           <el-form-item label="模版参数" prop="templateParameter">
        <el-input v-model="templateForm.templateParameter" ></el-input>
      </el-form-item>
       <el-form-item label="模版文件" prop="templateFileid">
          <!-- 上传组件start-->
              <el-upload
      class="upload-demo"
      action="/api/cms/template/upload"
      :on-success="uploadSuccess"
       :on-remove="removeFile"
      :before-upload="beforeAvatarUpload"
      :limit="1"
      :on-exceed="handleExceed"
      :file-list="fileList">
      <el-button size="small" type="primary">请上传模板</el-button>
      <div slot="tip" class="el-upload__tip">只能上传fml文件</div>
    </el-upload>
          <!-- 上传组件end-->
 </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addSubmit('form')" >提交</el-button>
          <el-button>返回</el-button>
        </el-form-item>
    </el-form> {{templateForm}}
  <!-- 添加表单end -->
  </el-card>
  <!-- 卡片end -->
</template>

<script>
  import { findSiteList, TemplateAdd } from '@/module/cms/api/cms'
  export default {
    data() {
      return {
        templateForm: {
          templateFileId: ''
        }, // 模板数据
        siteList: [], // 站点列表
        fileList: [] // 上传文件列表
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
      beforeAvatarUpload(file) { // 文件类型的校验函数
        const isFtl = file.name.endsWith('.ftl')
        if (!isFtl) {
          this.$message.error('上传文件只能是ftl格式!')
        }
        return isFtl
      },
      uploadSuccess(response, file, fileList) { // 上传成功的处理函数
        // 上传成功后,将获得fileId保存templateForm对象中
        this.templateForm.templateFileId = response.cmsTemplate.templateFileId
        console.info(this.templateForm)
      },
      handleExceed(files, fileList) { // 文件个数限定函数
        this.$message.warning('只能选择一个文件，若继续操作，请删除之前文件')
      },
      removeFile() {
        delete this.templateForm.templateFileId
      },
      addSubmit(form) {
        // 弹框
        this.$confirm('您确定要添加吗?', '提示', { type: 'warning' })
          .then(async() => {
            // 成功回调
            // ajax添加
            const { data } = await TemplateAdd(this.templateForm)
            if (data.success) {
              this.$message.success(data.message)
              // 表单重置
              // this.$refs.form.resetFields()
              this.$refs[form].resetFields()
              this.fileList = []
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
