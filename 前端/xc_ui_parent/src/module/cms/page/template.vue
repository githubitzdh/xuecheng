<template>
  <div>
    <h1>模板页面</h1>
    <!-- 搜索表单start -->
    <el-form :inline="true" :model="queryParams" class="demo-form-inline">
  <el-form-item>
      <el-select  v-model="queryParams.siteId" placeholder="请选择站点">
      <el-option  v-for="(site,index) in siteList" :key="index"    :label="site.siteName" :value="site.siteId"></el-option>
    </el-select>
  </el-form-item>
  <el-form-item >

      <el-input v-model="queryParams.templateName" placeholder="请输入模板名称"></el-input>
  </el-form-item>
  <el-form-item>
    <el-button type="primary" @click.native="pageListFn" >查询</el-button>
  <router-link to="/cms/template/add">
          <el-button type="primary"  >
            添加模块
          </el-button>
        </router-link>
  </el-form-item>
</el-form>

    <!-- 搜索表单end -->
    <!-- 列表展示start        height="250"-->
    <el-table
      :data="pageQueryResult.list"
      style="width: 100%">
      <el-table-column prop="templateName" label="模版名称" fixed width="120"></el-table-column>
      <el-table-column prop="templateParameter" label="模版参数" width="200"></el-table-column>
      <el-table-column label="编辑">
        <template slot-scope="scope">
          <el-button
            size="mini"
             @click="edit(scope.row.templateId)">编辑</el-button>
        </template>
      </el-table-column>
      <el-table-column label="删除">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="del(scope.row.templateId)">删除</el-button>
        </template>
      </el-table-column>
      <el-table-column label="静态化">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.row)">静态化</el-button>
        </template>
      </el-table-column>
      <el-table-column label="发布">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.row)">发布</el-button>
        </template>
      </el-table-column>
    </el-table>
   <!-- 列表展示end -->

    <!-- 分页条start -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pageParams.page"
      :page-sizes="[5, 10, 15, 20]"
      :page-size="pageParams.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pageQueryResult.total">
    </el-pagination>
  <!-- 分页条end -->
  </div>
</template>

<script>
  // 解构需要方法
  import { pageTemplateList, findSiteList, TemplateDelete } from '@/module/cms/api/cms'
  export default {
    data() {
      return {
        pageQueryResult: {}, // 查询结果
        pageParams: { // 分页条件
          page: 1, // 第几页
          size: 5 // 每页显示个数
        },
        queryParams: {}, // 查询条件
        siteList: [], // 所有站点
        templateList: [] // 所有模板
      }
    },
    created() {
      // 页面加载完成时,查询所有
      this.pageListFn()
      // 查询所有站点
      this.findSiteListFn()
    },
    methods: {
      async pageListFn() {
        const { data } = await pageTemplateList(this.pageParams, this.queryParams)
        // if (data.success) {
        if (data.code === 10000) {
          this.pageQueryResult = data.queryResult
        }
      },
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`)
        this.pageParams.size = val
        this.pageParams.page = 1
        this.pageListFn()
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`)
        this.pageParams.page = val
        this.pageListFn()
      },
      async findSiteListFn() {
        const { data } = await findSiteList()
        if (data.success) {
          this.siteList = data.queryResult.list
        }
      },
      del(templateId) {
        this.$confirm('您确定要删除吗？', '删除提示', { type: 'error' }).then(async () => {
          // 删除操作
          const { data } = await TemplateDelete(templateId)
          if (data.success) {
            this.$message.success(data.message)
            // 刷新
            this.pageListFn()
          } else {
            this.$message.error(data.message)
          }
        })
      },
      edit (id) {
        // 路由跳转
        this.$router.push('/cms/template/edit/' + id)
      }
    }
  }
</script>

<style>

</style>
