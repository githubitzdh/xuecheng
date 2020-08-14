<template>
  <div>
    <h1>列表页面</h1>
    <!-- 搜索表单start -->
    <el-form :inline="true" :model="queryParams" class="demo-form-inline">
  <el-form-item>
      <el-select  v-model="queryParams.siteId" placeholder="请选择站点">
      <el-option  v-for="(site,index) in siteList" :key="index"    :label="site.siteName" :value="site.siteId"></el-option>
    </el-select>
  </el-form-item>
    <el-form-item label="选择模板">
        <el-select v-model="queryParams.templateId" placeholder="请选择模板">
          <el-option  v-for="(te,index) in templateList" :key="index" :label="te.templateName" :value="te.templateId"></el-option>
        </el-select>
      </el-form-item>
  <el-form-item >

      <el-input v-model="queryParams.pageAliase" placeholder="请输入页面名称"></el-input>
  </el-form-item>
  <el-form-item>
    <el-button type="primary" @click.native="pageListFn" >查询</el-button>
  <router-link to="/cms/page/add">
          <el-button type="primary"  >
            添加页面
          </el-button>
        </router-link>
  </el-form-item>
</el-form>

    <!-- 搜索表单end -->
    <!-- 列表展示start        height="250"-->
    <el-table
      :data="pageQueryResult.list"
      style="width: 100%">
      <el-table-column prop="pageAliase" label="别名" fixed width="120"></el-table-column>
      <el-table-column prop="pageName" label="页面名称" width="200"></el-table-column>
      <el-table-column prop="pageType" label="类型（静态、动态）" width="150">
        <template slot-scope="scope">
          {{scope.row.pageType == 1 ? '动态' : '静态'}}
        </template>
      </el-table-column>
      <el-table-column prop="pageWebPath" label="访问路径" width="200"></el-table-column>
      <el-table-column prop="pagePhysicalPath" label="物理路径" width="300"></el-table-column>
      <el-table-column prop="pageCreateTime" label="创建时间" :formatter="dateFormat" width="180"></el-table-column>
      <el-table-column label="编辑">
        <template slot-scope="scope">
          <el-button
            size="mini"
             @click="edit(scope.row.pageId)">编辑</el-button>
        </template>
      </el-table-column>
      <el-table-column label="删除">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="del(scope.row.pageId)">删除</el-button>
        </template>
      </el-table-column>
       <el-table-column label="静态化">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="preview(scope.row.pageId)">静态化</el-button>
        </template>
      </el-table-column>
      <el-table-column label="发布">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="postPageFn(scope.row.pageId)">发布</el-button>
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
  import { pageList, findSiteList, findTemplateList, pageDelete, postPage } from '@/module/cms/api/cms'
  // 导入日期格式化
  import moment from 'moment'
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
      this.findTemplateListFn()
    },
    methods: {
      async pageListFn() {
        const { data } = await pageList(this.pageParams, this.queryParams)
        // if (data.success) {
        if (data.code === 10000) {
          this.pageQueryResult = data.queryResult
        }
      },
      dateFormat(row, column) {
        // 通过column确定列名,从row获得对应日期
        var date = row[column.property]
        if (date == null) { return '' }
        // 对日期进行格式化,并返回
        return moment(date).format('YYYY-MM-DD HH:mm:ss')
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
      async findTemplateListFn() {
        const { data } = await findTemplateList()
        if (data.success) {
          this.templateList = data.queryResult.list
        }
      },
      del(pageId) {
        this.$confirm('您确定要删除吗？', '删除提示', { type: 'error' }).then(async () => {
          // 删除操作
          const { data } = await pageDelete(pageId)
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
        this.$router.push('/cms/page/edit/' + id)
      },
      preview(pageId) {
        window.open(`/api/cms/preview/${pageId}`)
      },
      postPageFn(pageId) {
        // 确认框
        this.$confirm('您确定要发布页面吗？', '发布提示', { type: 'error' }).then(async () => {
          // 发布操作
          const { data } = await postPage(pageId)
          if (data.success) {
            this.$message.success(data.message)
          } else {
            this.$message.error(data.message)
          }
        })
      }
    }
  }
</script>

<style>

</style>
